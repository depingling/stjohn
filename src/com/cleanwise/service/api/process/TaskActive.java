package com.cleanwise.service.api.process;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Task;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import javax.naming.NamingException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         12.04.2007
 * Time:         13:08:11
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */

public class TaskActive implements TaskActiveInterface{

    protected Logger log = Logger.getLogger(this.getClass());

    private final static String className = "TaskActive";

    public int                     taskId;
    private String                 name;
    protected TaskData               taskData;
    protected TaskPropertyDataVector property;

    public static final Comparator TASK_POSITION = new Comparator() {
        public int compare(Object o1, Object o2)
        {
            int pos1 = ((TaskPropertyData)o1).getPosition();
            int pos2 = ((TaskPropertyData)o2).getPosition();
            return pos1-pos2;
        }
    };

    public TaskActive(TaskData taskActiveData, TaskPropertyDataVector propertyTask) {
        this.taskData = taskActiveData;
        this.property = propertyTask;
        this.taskId=taskData.getTaskId();
    }

    public void updateVarrable(int processId, PairViewVector pairProcessVariable)
            throws NamingException, APIServiceAccessException, RemoteException {

        APIAccess factory = new APIAccess();
        com.cleanwise.service.api.session.Process processEjb = factory.getProcessAPI();
        processEjb.updateProcessPropertyDataPN(processId,pairProcessVariable);

    }


    /**
     * Execute task
     */
    public void executeTask() throws Exception{

        log.info("executeTask() => Begin");

        setTaskStatus(RefCodeNames.TASK_STATUS_CD.IN_PROGRESS);

        try{
        	TaskPropertyDataVector inParams = getTaskProperty(property, RefCodeNames.TASK_PROPERTY_TYPE_CD.INPUT);
        	TaskPropertyDataVector mandatoryParams = getTaskProperty(property, RefCodeNames.TASK_PROPERTY_TYPE_CD.MANDATORY);
                TaskPropertyDataVector optionalParams = getTaskProperty(property, RefCodeNames.TASK_PROPERTY_TYPE_CD.OPTIONAL);

        	TaskPropertyDataVector outParams = getTaskProperty(property, RefCodeNames.TASK_PROPERTY_TYPE_CD.OUTPUT);
        	Iterator<TaskPropertyData> it = outParams.iterator();
        	//this is specifically to get pParentEventId
        	while(it.hasNext()){
        		TaskPropertyData td = (TaskPropertyData)it.next();
        		if(td.getVarName().equalsIgnoreCase("pParentEventId")){
        			td.setPropertyTypeCd("PARENT_"+td.getPropertyTypeCd());
        			inParams.add(td);
        			it.remove();
        		}
        	}

        	inParams.addAll(mandatoryParams);
                inParams.addAll(optionalParams);

        	sortByPosition(inParams);

            Object[] inParamsObj = getParamsObjects(taskData.getProcessId(), inParams);
            Class[] argTypes = getArgTypes(inParams);

            Object resultObj = invokeMethod(taskData.getVarClass(), taskData.getMethod(), argTypes, inParamsObj);

            sortByPosition(inParams);
            /*TaskPropertyDataVector outParams = getTaskProperty(property, RefCodeNames.TASK_PROPERTY_TYPE_CD.OUTPUT);
			*/
            PairViewVector resultParams = new PairViewVector();

            if (outParams.size() > 0) {
                resultParams.add(new PairView(((TaskPropertyData) outParams.get(0)).getVarName(), resultObj));
            }

            updateVarrable(taskData.getProcessId(), resultParams);
            setTaskStatus(RefCodeNames.TASK_STATUS_CD.FINISHED);

        } catch (Exception e) {
            setTaskStatus(RefCodeNames.TASK_STATUS_CD.FAILED);
            throw new TaskException("Task failed : " + taskData.getTaskId(), e);
        }
    }

    private void setTaskStatus(String status) throws Exception {
        this.taskData=getTaskProvider().setTaskStatus(this.taskData,status);
    }


    public Object invokeMethod(String className, String methodName, Class[] argTypes, Object[] inParams)
            throws
            ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InstantiationException,
            InvocationTargetException,
            NamingException,
            APIServiceAccessException, RemoteException, Exception {

        Class _class = Class.forName(className);
        Method worker = _class.getMethod(methodName, argTypes);
        com.cleanwise.service.api.session.ProcessEngine engine = new APIAccess().getProcessEngineAPI();
        return engine.invoke(worker, _class.newInstance(), inParams);

    }

    public Class[] getArgTypes(TaskPropertyDataVector inParams) throws ClassNotFoundException {
        Class[] argTypes = new Class[0];
        if (inParams != null) {
            argTypes = new Class[inParams.size()];
            Iterator it = inParams.iterator();
            for (int i = 0; i < inParams.size(); i++) {
                TaskPropertyData taskProp = (TaskPropertyData) inParams.get(i);
                String varType = taskProp.getVarType();
                Class varClass = Class.forName(varType);
                argTypes[i] = varClass;
            }

        }

        return argTypes;
    }

    public  void updateTaskData(TaskData td) throws NamingException, APIServiceAccessException, RemoteException {
        APIAccess factory= new APIAccess();
        com.cleanwise.service.api.session.Task taskEjb=factory.getTaskAPI();
        taskEjb.updateTaskData(td);
    }

    public  Object[] getParamsObjects(int processId, TaskPropertyDataVector inParams) throws NamingException, APIServiceAccessException, RemoteException {

        Object[] inParamObj = new Object[0];
        if (inParams != null) {
            inParamObj = new Object[inParams.size()];
            APIAccess factory = new APIAccess();
            com.cleanwise.service.api.session.Process processEjb = factory.getProcessAPI();
            Iterator it = inParams.iterator();
            for (int i = 0; i < inParams.size(); i++) {
                TaskPropertyData taskProp = (TaskPropertyData) inParams.get(i);
                String varName = taskProp.getVarName();

                Object varObject = processEjb.getVariableValue(processId, varName);
                inParamObj[i] = varObject;
            }

        }
        return inParamObj;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }


    public void sortByPosition(TaskPropertyDataVector inParams) {

        Collections.sort(inParams, TASK_POSITION);

    }

    public TaskPropertyDataVector getTaskProperty(TaskPropertyDataVector property, String typeCd) {

        TaskPropertyDataVector result = new TaskPropertyDataVector();

        if (property != null) {

            Iterator it = property.iterator();
            while (it.hasNext()) {

                TaskPropertyData taskPropData = (TaskPropertyData) it.next();
                if (typeCd != null) {
                    if (typeCd.equals(taskPropData.getPropertyTypeCd())) {
                        result.add(taskPropData);
                    }
                }
            }
        }

        return result;
    }

    private Task getTaskProvider() throws Exception {
        APIAccess factory = new APIAccess();
        return factory.getTaskAPI();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
