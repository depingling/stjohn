package com.cleanwise.service.api.session;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.ProcessDataAccess;
import com.cleanwise.service.api.dao.TaskDataAccess;
import com.cleanwise.service.api.dao.TaskPropertyDataAccess;
import com.cleanwise.service.api.dao.TaskRefDataAccess;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.process.TaskActiveInterface;
import com.cleanwise.service.api.process.TaskCommon;
import com.cleanwise.service.api.process.TaskWorkFlow;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import javax.ejb.CreateException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Date;

/**
 * Title:        TaskBean
 * Description:  Bean implementation for Task Session Bean
 * Purpose:      Ejb for scheduled task management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         05.04.2007
 * Time:         14:40:49
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class TaskBean extends ApplicationServicesAPI {
    private static final String className = "ApplicationServicesAPI";

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @throws javax.ejb.CreateException if an error occurs
     * @throws java.rmi.RemoteException  if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException { }


    /**
     * gets next tasks
     *
     * @param processId         activeidentifier
     * @param taskId            current task identifier
     * @return task collection
     * @throws RemoteException if an errors
     */
    public TaskDataVector getNextTasksByTemplateTask(int processId, int taskId) throws RemoteException {
        Connection conn = null;
        DBCriteria dbCrit;
        try {
            conn = getConnection();
            dbCrit = new DBCriteria();

            if (taskId > 0) {
                dbCrit.addEqualTo(TaskRefDataAccess.TASK_ID1, taskId);
            } else {
                dbCrit.addIsNull(TaskRefDataAccess.TASK_ID1);
            }
            dbCrit.addEqualTo(TaskRefDataAccess.PROCESS_ID, processId);
            String taskRefIdsSql = TaskRefDataAccess.getSqlSelectIdOnly(TaskRefDataAccess.TASK_ID2, dbCrit);

            dbCrit = new DBCriteria();
            dbCrit.addOneOf(TaskDataAccess.TASK_ID, taskRefIdsSql);
            return TaskDataAccess.select(conn, dbCrit);
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    /**
     * gets next tasks
     *
     * @param taskId    current task identifier
     * @param processId process id
     * @return task collection
     * @throws RemoteException if an errors
     */
    public TaskDataVector getNextTasksByActiveTask(int taskId, int processId) throws RemoteException {
        Connection conn = null;
        DBCriteria dbCrit;
        String taskRefIdsSql;
        try {

            conn = getConnection();
            if (taskId > 0) {

                TaskData taskData = TaskDataAccess.select(conn, taskId);
                //ProcessData processData = ProcessDataAccess.select(conn, taskData.getProcessId());

                dbCrit = new DBCriteria();
                dbCrit.addEqualTo(TaskRefDataAccess.TASK_ID1, taskData.getTaskTemplateId());
                dbCrit.addEqualTo(TaskRefDataAccess.PROCESS_ID, processId);
                taskRefIdsSql = TaskRefDataAccess.getSqlSelectIdOnly(TaskRefDataAccess.TASK_ID2, dbCrit);

            } else {

                dbCrit = new DBCriteria();
                dbCrit.addIsNull(TaskRefDataAccess.TASK_ID1);
                dbCrit.addEqualTo(TaskRefDataAccess.PROCESS_ID, processId);
                taskRefIdsSql = TaskRefDataAccess.getSqlSelectIdOnly(TaskRefDataAccess.TASK_ID2, dbCrit);

            }

            dbCrit = new DBCriteria();
            dbCrit.addOneOf(TaskDataAccess.TASK_ID, taskRefIdsSql);

            TaskDataVector tasks;
            tasks = TaskDataAccess.select(conn, dbCrit);
            return tasks;
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
    }

    /**
     * gets Task Property
     *
     * @param taskId task indentifier;
     * @return task proprty collection
     * @throws RemoteException if an errros
     */
    public TaskPropertyDataVector getTaskProperty(int taskId) throws RemoteException {
        Connection conn = null;
        DBCriteria dbCrit;
        try {
            conn = getConnection();
            dbCrit = new DBCriteria();
            dbCrit.addEqualTo(TaskPropertyDataAccess.TASK_ID, taskId);
            return TaskPropertyDataAccess.select(conn, dbCrit);
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
    }

    public boolean checkReady(int processId, int processTemplateId, int taskId) throws RemoteException {
        Connection conn = null;
        DBCriteria dbCrit;
        try {
            conn = getConnection();

            dbCrit = new DBCriteria();
            dbCrit.addEqualTo(TaskRefDataAccess.TASK_ID2, taskId);
            dbCrit.addEqualTo(TaskRefDataAccess.PROCESS_ID, processId);
            String taskRefIdsSql = TaskRefDataAccess.getSqlSelectIdOnly(TaskRefDataAccess.TASK_ID1, dbCrit);
            dbCrit = new DBCriteria();
            dbCrit.addOneOf(TaskDataAccess.TASK_TEMPLATE_ID, taskRefIdsSql);
            dbCrit.addEqualTo(TaskDataAccess.PROCESS_ID, processId);
            dbCrit.addEqualTo(TaskDataAccess.TASK_STATUS_CD, RefCodeNames.TASK_STATUS_CD.FINISHED);
            dbCrit.addEqualTo(TaskDataAccess.TASK_TYPE_CD, RefCodeNames.TASK_TYPE_CD.ACTIVE);

            TaskDataVector finishedTasks = TaskDataAccess.select(conn, dbCrit);

            dbCrit = new DBCriteria();
            dbCrit.addOneOf(TaskDataAccess.TASK_ID, taskRefIdsSql);
            dbCrit.addEqualTo(TaskDataAccess.PROCESS_ID, processTemplateId);
            dbCrit.addEqualTo(TaskDataAccess.TASK_TYPE_CD, RefCodeNames.TASK_TYPE_CD.TEMPLATE);
            dbCrit.addEqualTo(TaskDataAccess.TASK_STATUS_CD, RefCodeNames.TASK_STATUS_CD.ACTIVE);

            TaskDataVector requredTasks = TaskDataAccess.select(conn, dbCrit);

            return finishedTasks != null && requredTasks != null && finishedTasks.size() == requredTasks.size();
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
    }

    public TaskData updateTaskData(TaskData taskData) throws RemoteException {
        Connection conn = null;
        DBCriteria dbCrit;
        try {
            conn = getConnection();
            return updateTaskData(conn, taskData);
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
    }

    public TaskData updateTaskData(Connection conn, TaskData taskData) throws RemoteException, SQLException {

        if (taskData != null) {
            if (taskData.getTaskId() > 0) {
                TaskDataAccess.update(conn, taskData);
            } else {
                taskData = TaskDataAccess.insert(conn, taskData);
            }
        }
        return taskData;
    }

    /**
     * gets next tasks
     *
     * @param processId         activeidentifier
     * @param taskId            current task identifier
     * @return task collection
     * @throws RemoteException if an errors
     */
    public TaskDataVector getFastNextTasksByActiveTask(int processId, int taskId) throws RemoteException {
        Connection conn = null;
        DBCriteria dbCrit;
        try {
            conn = getConnection();

            String taskRefIdsSql;
            if (taskId > 0) {
                dbCrit = new DBCriteria();
                dbCrit.addEqualTo(TaskDataAccess.TASK_ID, taskId);
                dbCrit.addEqualTo(TaskDataAccess.PROCESS_ID, processId);
                String taskSql = TaskDataAccess.getSqlSelectIdOnly(TaskDataAccess.TASK_TEMPLATE_ID, dbCrit);


                dbCrit = new DBCriteria();
                dbCrit.addOneOf(TaskRefDataAccess.TASK_ID1, taskSql);
                dbCrit.addEqualTo(TaskRefDataAccess.PROCESS_ID, processId);
                taskRefIdsSql = TaskRefDataAccess.getSqlSelectIdOnly(TaskRefDataAccess.TASK_ID2, dbCrit);

            } else {
                dbCrit = new DBCriteria();
                dbCrit.addIsNull(TaskRefDataAccess.TASK_ID1);
                dbCrit.addEqualTo(TaskRefDataAccess.PROCESS_ID, processId);
                taskRefIdsSql = TaskRefDataAccess.getSqlSelectIdOnly(TaskRefDataAccess.TASK_ID2, dbCrit);

            }

            dbCrit = new DBCriteria();
            dbCrit.addOneOf(TaskDataAccess.TASK_ID, taskRefIdsSql);

            TaskDataVector tasks = new TaskDataVector();
            try {

                tasks = TaskDataAccess.select(conn, dbCrit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return tasks;
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
    }

    /**
     * gets failed tasks
     *
     * @param processId process identifier
     * @return tasks where status is FAILED
     */
    public TaskDataVector getFailedTaskForProcess(int processId) throws RemoteException {
        Connection conn = null;
        DBCriteria dbCrit;
        try {
            conn = getConnection();
            dbCrit = new DBCriteria();
            dbCrit.addEqualTo(TaskDataAccess.PROCESS_ID, processId);
            dbCrit.addEqualTo(TaskDataAccess.TASK_TYPE_CD, RefCodeNames.TASK_TYPE_CD.ACTIVE);
            dbCrit.addEqualTo(TaskDataAccess.TASK_STATUS_CD, RefCodeNames.TASK_STATUS_CD.FAILED);
            return TaskDataAccess.select(conn, dbCrit);
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
    }


    public TaskActiveInterface createActiveTask(int processId, TaskData taskData, String userName) throws RemoteException {

        TaskActiveInterface task = null;

        try {
            TaskData taskActiveData = createTaskWithStatusActive(taskData, processId, userName);
            TaskPropertyDataVector propertyTask = getTaskProperty(taskData.getTaskId());

            //create task depens on the task operation type
            if(RefCodeNames.TASK_OPERATION_TYPE_CD.COMMON.equals(taskData.getOperationTypeCd())){
                task = new TaskCommon(taskActiveData, propertyTask);
            }
            else if(RefCodeNames.TASK_OPERATION_TYPE_CD.WF.equals(taskData.getOperationTypeCd())){
                task = new TaskWorkFlow(taskActiveData, propertyTask);
            }
            else {
                task = new TaskCommon(taskActiveData, propertyTask);
            }

            return task;

        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    public com.cleanwise.service.api.process.TaskActive populateActiveTask(int processId, TaskData taskData) throws RemoteException {
        try {
            TaskPropertyDataVector propertyTask = getTaskProperty(taskData.getTaskTemplateId());
            return new com.cleanwise.service.api.process.TaskActive(taskData, propertyTask);
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    private TaskData createTaskWithStatusActive(TaskData templateTaskData, int processId, String userName) throws RemoteException {

        TaskData taskData = TaskData.createValue();

        //Create values for task
        taskData.setProcessId(processId);
        taskData.setTaskTemplateId(templateTaskData.getTaskId());
        taskData.setTaskName(templateTaskData.getTaskName());
        taskData.setVarClass(templateTaskData.getVarClass());
        taskData.setTaskTypeCd(RefCodeNames.TASK_TYPE_CD.ACTIVE);
        taskData.setTaskStatusCd(RefCodeNames.TASK_STATUS_CD.READY);
        taskData.setMethod(templateTaskData.getMethod());
        taskData.setAddBy(userName);
        taskData.setModBy(userName);

        updateTaskData(taskData);

        return taskData;
    }

    public TaskData setTaskStatus(TaskData taskData, String status) throws RemoteException {
        Connection conn = null;
        DBCriteria dbCrit;
        try {
            conn = getConnection();
            if (taskData != null && taskData.getTaskId() > 0) {
                taskData.setTaskStatusCd(status);
                taskData = updateTaskData(conn, taskData);
            }
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
        return taskData;
    }

    public TaskDataVector getTasks(TaskSearchCriteria criteria) throws RemoteException {
        Connection conn = null;
        DBCriteria dbCrit = convertToDBCriteria(criteria);
        try {
            conn = getConnection();
            return TaskDataAccess.select(conn, dbCrit);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public TaskDetailViewVector getTaskDetailViewVector(TaskSearchCriteria criteria) throws RemoteException {
        Connection conn = null;
        DBCriteria dbCrit = convertToDBCriteria(criteria);
        try {
            conn = getConnection();
            TaskDataVector tasks = TaskDataAccess.select(conn, dbCrit);
            return getTaskDetailViewVector(tasks);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    private TaskDetailViewVector getTaskDetailViewVector(TaskDataVector tasks) throws Exception {
        TaskDetailViewVector taskDetails = new TaskDetailViewVector();
        Process processEjb = APIAccess.getAPIAccess().getProcessAPI();
        if (!tasks.isEmpty()) {
            Iterator it = tasks.iterator();
            while (it.hasNext()) {
                TaskDetailView detail = TaskDetailView.createValue();
                TaskData task = (TaskData) it.next();
                TaskPropertyDataVector taskProps = getTaskProperty(task.getTaskId());
                ProcessData process = processEjb.getProcess(task.getProcessId());
                ProcessPropertyDataVector processProps = processEjb.getProcessProperty(task.getProcessId());
                detail.setTaskData(task);
                detail.setTaskPropertyDV(taskProps);
                detail.setProcessData(process);
                detail.setProcessPropertyDV(processProps);
                taskDetails.add(detail);
            }
        }
        return taskDetails;
    }

    private DBCriteria convertToDBCriteria(TaskSearchCriteria criteria) {

        DBCriteria dbCrit = new DBCriteria();
        DBCriteria processCrit = new DBCriteria();

        boolean processCritAddFlag = false;
        if (criteria.getTaskIds() != null
                && !criteria.getTaskIds().isEmpty()) {

            if (criteria.getTaskIds().size() == 1) {
                dbCrit.addEqualTo(TaskDataAccess.TASK_ID, ((Integer) criteria.getTaskIds().get(0)).intValue());
            } else {
                dbCrit.addOneOf(TaskDataAccess.TASK_ID, criteria.getTaskIds());
            }
        }

        if (criteria.getTaskNames() != null
                && criteria.getTaskNames().length > 0) {
            if (criteria.getTaskNames().length == 1) {
                dbCrit.addEqualTo(TaskDataAccess.TASK_NAME, (criteria.getTaskNames()[0]));
            } else {
                ArrayList list = new ArrayList();
                for (int i = 0; i < criteria.getTaskNames().length; i++) {
                    list.add(criteria.getTaskNames());
                }
                dbCrit.addOneOf(TaskDataAccess.TASK_NAME, list);
            }
        }

        if (criteria.getTaskStatusCds() != null
                && criteria.getTaskStatusCds().length > 0) {
            if (criteria.getTaskStatusCds().length == 1) {
                dbCrit.addEqualTo(TaskDataAccess.TASK_STATUS_CD, (criteria.getTaskStatusCds()[0]));
            } else {
                ArrayList list = new ArrayList();
                for (int i = 0; i < criteria.getTaskStatusCds().length; i++) {
                    list.add(criteria.getTaskStatusCds());
                }
                dbCrit.addOneOf(TaskDataAccess.TASK_STATUS_CD, list);
            }
        }

        if (criteria.getTaskTypeCd() != null) {
            dbCrit.addEqualTo(TaskDataAccess.TASK_TYPE_CD, criteria.getTaskTypeCd());
        }

        if (criteria.getProcessIds() != null && !criteria.getProcessIds().isEmpty()) {
            if (criteria.getProcessIds().size() == 1) {
                dbCrit.addEqualTo(TaskDataAccess.PROCESS_ID, ((Integer) criteria.getProcessIds().get(0)).intValue());
            } else {
                dbCrit.addOneOf(TaskDataAccess.PROCESS_ID, criteria.getProcessIds());
            }
        }

        if (criteria.getProcessNames() != null
                && criteria.getProcessNames().length > 0) {
            if (criteria.getProcessNames().length == 1) {
                processCrit.addEqualTo(ProcessDataAccess.PROCESS_NAME, criteria.getProcessNames()[0]);
            } else {
                ArrayList list = new ArrayList();
                for (int i = 0; i < criteria.getProcessNames().length; i++) {
                    list.add(criteria.getProcessNames());
                }
                processCrit.addOneOf(ProcessDataAccess.PROCESS_NAME, list);
            }
            processCritAddFlag = true;
        }

        if (criteria.getProcessStatusCds() != null
                && criteria.getProcessStatusCds().length > 0) {
            if (criteria.getProcessStatusCds().length == 1) {
                processCrit.addEqualTo(ProcessDataAccess.PROCESS_STATUS_CD, criteria.getProcessStatusCds()[0]);
            } else {
                ArrayList list = new ArrayList();
                for (int i = 0; i < criteria.getProcessStatusCds().length; i++) {
                    list.add(criteria.getProcessStatusCds());
                }
                processCrit.addOneOf(ProcessDataAccess.PROCESS_STATUS_CD, list);
            }
            processCritAddFlag = true;
        }

        if (criteria.getProcessTypeCd() != null) {
            processCrit.addEqualTo(ProcessDataAccess.PROCESS_TYPE_CD, criteria.getProcessTypeCd());
            processCritAddFlag = true;
        }

        if (processCritAddFlag) {

            if (criteria.getProcessIds() != null && !criteria.getProcessIds().isEmpty()) {
                if (criteria.getProcessIds().size() == 1) {
                    processCrit.addEqualTo(ProcessDataAccess.PROCESS_ID, ((Integer) criteria.getProcessIds().get(0)).intValue());
                } else {
                    processCrit.addOneOf(ProcessDataAccess.PROCESS_ID, criteria.getProcessIds());
                }
            }

            String processSql = ProcessDataAccess.getSqlSelectIdOnly(ProcessDataAccess.PROCESS_ID, processCrit);
            String processTable = "(" + processSql + ") process";
            dbCrit.addJoinTable(processTable);
            dbCrit.addCondition(TaskDataAccess.PROCESS_ID + "=process." + ProcessDataAccess.PROCESS_ID);
        }

        return dbCrit;
    }

    public TaskRefDataVector getTaskRefs(int processId) throws RemoteException {
        Connection conn = null;
        DBCriteria dbCrit;
        try {
            conn = getConnection();
            dbCrit = new DBCriteria();
            dbCrit.addEqualTo(TaskRefDataAccess.PROCESS_ID, processId);
            return TaskRefDataAccess.select(conn, dbCrit);

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public TaskPropertyDataVector updateTaskProperty(int taskId, TaskPropertyDataVector taskPropertyDV) throws RemoteException {
        Connection conn = null;
        try {
            TaskPropertyDataVector oldProperty = getTaskProperty(taskId);
            HashSet oldPropSet = getIdsSet(oldProperty);
            conn = getConnection();
            if (taskPropertyDV != null && !taskPropertyDV.isEmpty()) {

                Iterator it = taskPropertyDV.iterator();
                while (it.hasNext()) {
                    TaskPropertyData prop = (TaskPropertyData) it.next();
                    if (prop.getTaskId() <= 0) {
                        prop.setTaskId(taskId);
                    }
                    if (prop.getTaskPropertyId() > 0) {
                        TaskPropertyDataAccess.update(conn, prop);
                        if (oldPropSet.contains(new Integer(prop.getTaskPropertyId()))) {
                            oldPropSet.remove(new Integer(prop.getTaskPropertyId()));
                        }
                    } else {
                        prop = TaskPropertyDataAccess.insert(conn, prop);
                    }
                }
            }

            DBCriteria crit = new DBCriteria();
            Iterator it = oldPropSet.iterator();
            IdVector ids = new IdVector();
            while (it.hasNext()) {
                ids.add(it.next());
            }

            crit.addOneOf(TaskPropertyDataAccess.TASK_PROPERTY_ID, ids);
            TaskPropertyDataAccess.remove(conn, crit);

            return taskPropertyDV;

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    private HashSet getIdsSet(TaskPropertyDataVector oldProperty) {
        HashSet ids = new HashSet();
        Iterator it = oldProperty.iterator();
        while (it.hasNext()) {
            ids.add(new Integer(((TaskPropertyData) it.next()).getTaskPropertyId()));
        }
        return ids;
    }

    public TaskRefDataVector updateRefs(int processId, TaskRefDataVector refs) throws RemoteException {
        Connection conn = null;
        try {
            TaskRefDataVector oldRefs = getTaskRefs(processId);
            HashSet oldRefsidsSet = getIdsSet(oldRefs);
            conn = getConnection();

            if (refs != null && !refs.isEmpty()) {
                Iterator it = refs.iterator();
                while (it.hasNext()) {
                    TaskRefData ref = (TaskRefData) it.next();
                    ref.setProcessId(processId);
                    if (ref.getTaskRefId() > 0) {
                        TaskRefDataAccess.update(conn, ref);
                        if (oldRefsidsSet.contains(new Integer(ref.getTaskRefId()))) {
                            oldRefsidsSet.remove(new Integer(ref.getTaskRefId()));
                        }
                    } else {
                        ref = TaskRefDataAccess.insert(conn, ref);
                    }
                }

            }

            DBCriteria crit = new DBCriteria();
            Iterator it = oldRefsidsSet.iterator();
            IdVector ids = new IdVector();
            while (it.hasNext()) {
                ids.add(it.next());
            }
            crit.addOneOf(TaskRefDataAccess.TASK_REF_ID, ids);
            TaskRefDataAccess.remove(conn, crit);

            return refs;

        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public TaskRefDataVector updateTaskRefs(int processId, TaskRefDataVector refs) throws RemoteException {
        Connection conn = null;
        Date modDate = new Date(System.currentTimeMillis());
        try {
            conn = getConnection();
            IdVector updatedIds = new IdVector();
            if (refs != null && !refs.isEmpty()) {
                Iterator it = refs.iterator();
                while (it.hasNext()) {
                    TaskRefData ref = (TaskRefData) it.next();
                    DBCriteria crit= new DBCriteria();
                    crit.addEqualTo(TaskRefDataAccess.PROCESS_ID, processId);
                    if (ref.getTaskId1() == 0)
                    	crit.addIsNull(TaskRefDataAccess.TASK_ID1);
                    else
                    	crit.addEqualTo(TaskRefDataAccess.TASK_ID1, ref.getTaskId1());
                    if (ref.getTaskId2() == 0)
                    	crit.addIsNull(TaskRefDataAccess.TASK_ID2);
                    else
                    	crit.addEqualTo(TaskRefDataAccess.TASK_ID2, ref.getTaskId2());
                    crit.addEqualTo(TaskRefDataAccess.TASK_REF_STATUS_CD, RefCodeNames.TASK_REF_STATUS_CD.ACTIVE);
                    if (updatedIds.size() > 0)
                    	crit.addNotOneOf(TaskRefDataAccess.TASK_REF_ID, updatedIds);
                    TaskRefDataVector rfs = TaskRefDataAccess.select(conn,crit);
                    if (!rfs.isEmpty()) {
                        Iterator iter = rfs.iterator();
                        if (iter.hasNext()) {
                            TaskRefData refData = (TaskRefData) iter.next();
                            updatedIds.add(refData.getTaskRefId());
                        }
                    } else {
                        crit= new DBCriteria();
                        crit.addEqualTo(TaskRefDataAccess.PROCESS_ID, processId);
                        crit.addEqualTo(TaskRefDataAccess.TASK_REF_STATUS_CD, RefCodeNames.TASK_REF_STATUS_CD.ACTIVE);
                        if (updatedIds.size() > 0)
                        	crit.addNotOneOf(TaskRefDataAccess.TASK_REF_ID, updatedIds);
                        rfs = TaskRefDataAccess.select(conn,crit);
                        if (!rfs.isEmpty()) {
                            Iterator iter = rfs.iterator();
                            if (iter.hasNext()) {
                                TaskRefData refData = (TaskRefData) iter.next();
                                refData.setTaskId1(ref.getTaskId1());
                                refData.setTaskId2(ref.getTaskId2());
                                refData.setModBy(ref.getModBy());
                                refData.setModDate(modDate);
                                TaskRefDataAccess.update(conn, refData);
                                updatedIds.add(refData.getTaskRefId());
                            }
                        } else {
                            ref.setProcessId(processId);
                            ref.setAddDate(modDate);
                            ref.setModDate(modDate);
                            ref = TaskRefDataAccess.insert(conn, ref);
                            updatedIds.add(ref.getTaskRefId());
                        }
                    }
                }
            }
            DBCriteria crit= new DBCriteria();
            crit.addEqualTo(TaskRefDataAccess.PROCESS_ID, processId);
            crit.addEqualTo(TaskRefDataAccess.TASK_REF_STATUS_CD, RefCodeNames.TASK_REF_STATUS_CD.ACTIVE);
            crit.addNotOneOf(TaskRefDataAccess.TASK_REF_ID, updatedIds);
            TaskRefDataVector rfs = TaskRefDataAccess.select(conn,crit);
            
            if (!rfs.isEmpty()) {
                Iterator iter = rfs.iterator();
                if (iter.hasNext()) {
                    TaskRefData refData = (TaskRefData) iter.next();
                    refData.setTaskRefStatusCd(RefCodeNames.TASK_REF_STATUS_CD.INACTIVE);
                    refData.setModBy("ProcessLoader");
                    refData.setModDate(modDate);
                    TaskRefDataAccess.update(conn, refData);
                }
            }
            return refs;

        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    private HashSet getIdsSet(TaskRefDataVector oldRefs) {
        HashSet ids = new HashSet();
        Iterator it = oldRefs.iterator();
        while (it.hasNext()) {
            ids.add(new Integer(((TaskRefData) it.next()).getTaskRefId()));
        }
        return ids;
    }

    public TaskData getTemplateTask(int templateProcessId, com.cleanwise.service.api.value.TaskView taskInfo) throws RemoteException, DataNotFoundException {
        Connection conn = null;

        try {
            conn = getConnection();
            return getTemplateTask(conn, templateProcessId, taskInfo);
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    private TaskData getTemplateTask(Connection conn, int templateProcessId, TaskView taskInfo) throws Exception {

        DBCriteria dbCriteria = new DBCriteria();

        dbCriteria.addEqualTo(TaskDataAccess.PROCESS_ID, templateProcessId);
        dbCriteria.addEqualTo(TaskDataAccess.TASK_TYPE_CD, RefCodeNames.TASK_TYPE_CD.TEMPLATE);

        dbCriteria.addEqualTo(TaskDataAccess.VAR_CLASS, taskInfo.getOpClass());
        dbCriteria.addEqualTo(TaskDataAccess.METHOD, taskInfo.getOpMethod());

        if (taskInfo.getOutParams().length > 0 || taskInfo.getInParams().length > 0) {

            for (int i = 0; i < taskInfo.getInParams().length; i++) {
                DBCriteria outParamCrit = new DBCriteria();
                outParamCrit.addEqualTo(TaskPropertyDataAccess.VAR_TYPE, (String) taskInfo.getInParams()[i]);
                outParamCrit.addEqualTo(TaskPropertyDataAccess.POSITION, i + 1);
                outParamCrit.addCondition(TaskPropertyDataAccess.TASK_ID +"="+(TaskDataAccess.CLW_TASK+"."+TaskDataAccess.TASK_ID));

                ArrayList typeList = new ArrayList();
                typeList.add(RefCodeNames.TASK_PROPERTY_TYPE_CD.INPUT);
                typeList.add(RefCodeNames.TASK_PROPERTY_TYPE_CD.MANDATORY);
                outParamCrit.addOneOf(TaskPropertyDataAccess.PROPERTY_TYPE_CD, typeList);

                dbCriteria.addCondition("exists (" + TaskPropertyDataAccess.getSqlSelectIdOnly(TaskPropertyDataAccess.TASK_PROPERTY_ID, outParamCrit) + ")");

            }

            for (int i = 0; i < taskInfo.getOutParams().length; i++) {
                DBCriteria inParamCrit = new DBCriteria();
                inParamCrit.addEqualTo(TaskPropertyDataAccess.VAR_TYPE, (String) taskInfo.getOutParams()[i]);
                inParamCrit.addEqualTo(TaskPropertyDataAccess.POSITION, i + 1);
                inParamCrit.addEqualTo(TaskPropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.TASK_PROPERTY_TYPE_CD.OUTPUT);
                inParamCrit.addCondition(TaskPropertyDataAccess.TASK_ID + "=" + (TaskDataAccess.CLW_TASK+"."+TaskDataAccess.TASK_ID));

                dbCriteria.addCondition("exists (" + TaskPropertyDataAccess.getSqlSelectIdOnly(TaskPropertyDataAccess.TASK_PROPERTY_ID, inParamCrit) + ")");

            }
        }

        TaskDataVector tasks = TaskDataAccess.select(conn, dbCriteria);

        if (tasks.size() > 1) {
            throw new Exception("Multiple task");
        }

        if (tasks.size() == 0) {
            throw new DataNotFoundException("Data not found");
        }

        return (TaskData) tasks.get(0);

    }

    public TaskRefDataVector createActiveReferences(ProcessData process) throws RemoteException {
        try {

            TaskRefDataVector taskRefs = getTaskRefs(process.getProcessTemplateId());

            if (taskRefs.isEmpty()) {
                throw new Exception("No template task references");
            }

            Iterator it = taskRefs.iterator();
            while (it.hasNext()) {
                TaskRefData ref = ((TaskRefData) it.next());
                ref.setProcessId(process.getProcessId());
                ref.setTaskRefId(0);
            }

            return updateRefs(process.getProcessId(), taskRefs);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

}
