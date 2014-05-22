package com.cleanwise.service.api.session;

import com.cleanwise.service.api.process.ProcessActive;
import com.cleanwise.service.api.process.ProcessSchema;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.*;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * Title:        Process
 * Description:  Remote Interface for Process Stateless Session Bean
 * Purpose:      Provides access to the methods
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         05.04.2007
 * Time:         14:31:08
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface Process extends javax.ejb.EJBObject {

    /**
     * gets process
     *
     * @param id identifier
     * @return process data
     * @throws RemoteException if an errors
     */
    public ProcessData getProcess(int id) throws RemoteException;

    /**
     * initialize process variables
     *
     * @param processId process identifier
     * @throws RemoteException if an errors
     */
    public void initProcessVariable(int processId) throws RemoteException;

    /**
     * updates clw_process table
     *
     * @param process data
     * @return updated data
     * @throws RemoteException if an errors
     */
    public ProcessData updateProcessData(ProcessData process) throws RemoteException;

    /**
     * gets task property
     *
     * @param templateProcessId process template identifier
     * @return task property data collection
     * @throws RemoteException if an errors
     */
    public TaskPropertyDataVector getTaskPropertyByTemplateProcessId(int templateProcessId) throws RemoteException;

    /**
     * gets process property
     *
     * @param processId process identifier
     * @return process property data collection
     * @throws RemoteException if an errors
     */
    public ProcessPropertyDataVector getProcessProperty(int processId) throws RemoteException;

    /**
     * updates clw_process_property
     *
     * @param processVariableObj
     * @return succes flag
     * @throws RemoteException if an errors
     */
    public boolean updateProcessPropertyData(PairViewVector processVariableObj) throws RemoteException;

    /**
     * gets variable  value for process
     *
     * @param processId process identifier
     * @param varName   varible name
     * @return variable value
     * @throws RemoteException if an errors
     */
    public Object getVariableValue(int processId, String varName) throws RemoteException;

    /**
     * @param pairProcessVariable
     */
    public boolean updateProcessPropertyDataPN(int processId, PairViewVector pairProcessVariable) throws RemoteException;


    /**
     * Creates the Process according the Process template
     *
     * @param templateId - Id of the Process which has to be running
     * @param schema
     * @param userName   - user name which creates this process @return ProcessActive
     * @return process process schema
     * @throws java.rmi.RemoteException if an errors
     */
    public ProcessActive createActiveProcess(int templateId, ProcessSchema schema, String userName) throws RemoteException;

    public ProcessData setProcessStatus(ProcessData processData, String status) throws RemoteException;

    public int getActiveTemplateProcessId(String name) throws RemoteException;

    public ProcessDataVector getAllTemplateProcesses() throws RemoteException;

    public TaskTemplateDetailViewVector updateTemplateProcessDetailData(TaskTemplateDetailViewVector taskTemplateDetails) throws RemoteException;

    public void updateInactiveTemplate(IdVector updatedIds) throws RemoteException;

    /**
     * Get process by its name.
     *
     * @param processName
     *            The name of process.
     * @return ProcessData
     * @throws RemoteException
     */
    public ProcessData getProcessByName(String processName)
            throws RemoteException, DataNotFoundException;

    public Map getAllTaskProperties() throws RemoteException;

    public ProcessDataVector getProcessesByEventId(int eventId) throws RemoteException;
    public IdVector getEventsByProcessIds(IdVector processIds) throws RemoteException ;
    public IdVector getSimilarProcessesRunning(int pProcessId) throws RemoteException ;
}
