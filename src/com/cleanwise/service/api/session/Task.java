package com.cleanwise.service.api.session;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.process.TaskActiveInterface;

import java.rmi.RemoteException;

/**
 * Title:        Task
 * Description:  Remote Interface for Task Stateless Session Bean
 * Purpose:      Provides access to the methods
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         05.04.2007
 * Time:         14:39:42
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface Task extends javax.ejb.EJBObject {

    /**
     * gets next tasks
     *
     * @param processId         activeidentifier
     * @param taskId            current task identifier
     * @return task collection
     * @throws RemoteException if an errors
     */
    public TaskDataVector getNextTasksByTemplateTask(int processId, int taskId) throws RemoteException;

    /**
     * gets Task Property
     *
     * @param taskId task indentifier;
     * @return task proprty collection
     * @throws RemoteException if an errros
     */
    public TaskPropertyDataVector getTaskProperty(int taskId) throws RemoteException;

    public TaskDataVector getNextTasksByActiveTask(int taskId,int processId) throws RemoteException;

    public boolean checkReady(int processId, int taskId, int id) throws RemoteException;

    public TaskData updateTaskData(TaskData taskData) throws RemoteException;

    /**
     * gets next tasks
     *
     * @param processId         activeidentifier
     * @param taskId            current task identifier
     * @return task collection
     * @throws RemoteException if an errors
     */
    public TaskDataVector getFastNextTasksByActiveTask(int processId, int taskId) throws RemoteException;

    /**
     * gets failed tasks
     *
     * @param processId process identifier
     * @return tasks where status is FAILED
     */
    public TaskDataVector getFailedTaskForProcess(int processId) throws RemoteException;

    public TaskActiveInterface createActiveTask(int processId, TaskData taskData, String userName) throws RemoteException;

    public TaskData setTaskStatus(TaskData taskData, String status) throws RemoteException;

    public  com.cleanwise.service.api.process.TaskActive populateActiveTask(int processId, TaskData taskData) throws RemoteException;

    public TaskDataVector getTasks(TaskSearchCriteria criteria) throws RemoteException;

    public TaskDetailViewVector getTaskDetailViewVector(TaskSearchCriteria criteria) throws RemoteException;

    public TaskRefDataVector getTaskRefs(int processId) throws RemoteException;

    public TaskPropertyDataVector updateTaskProperty(int taskId,TaskPropertyDataVector taskPropertyDV) throws RemoteException;

    public TaskRefDataVector updateRefs(int processId,TaskRefDataVector refs) throws RemoteException;

    public TaskRefDataVector updateTaskRefs(int processId, TaskRefDataVector refs) throws RemoteException;

    public TaskData getTemplateTask(int templateProcessId, com.cleanwise.service.api.value.TaskView taskInfo) throws RemoteException, DataNotFoundException;

    public TaskRefDataVector createActiveReferences(ProcessData process) throws RemoteException;
}
