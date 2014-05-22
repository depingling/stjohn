package com.cleanwise.service.api.process;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.process.TaskActive;
import com.cleanwise.service.api.process.operations.TaskOperations;
import com.cleanwise.service.api.value.*;
import oracle.sql.BLOB;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.io.Serializable;

import org.apache.log4j.Logger;

public class ProcessActive implements Serializable {

    protected Logger log = Logger.getLogger(this.getClass());

    private final String className = "ProcessActive";

    private ProcessData   processActiveData;
    private String        userName;
    private Task          taskProvider;

    public ProcessActive(ProcessData processData) throws Exception {
        this.processActiveData = processData;
        this.taskProvider      = TaskOperations.getTaskProvider();
        this.userName          = className + " " + processData.getProcessName() + " " + processData.getProcessId();
    }

    /**
     * Run the Active Process according the Template
     * @throws ProcessException special type of exception, thrown by Process
     */
    public void executeProcess() throws ProcessException {

        try {
            Process processProvider = getProcessProvider();

            //get process status and check if it is failed than run failed tasks at first
            if (processActiveData.getProcessStatusCd().equals(RefCodeNames.PROCESS_STATUS_CD.FAILED)) {

                TaskDataVector failedTasks = getFailedTasks();

                if (!failedTasks.isEmpty()) {
                    setProcessStatus(RefCodeNames.PROCESS_STATUS_CD.IN_PROGRESS);
                    runFailedTasks(failedTasks);
                }
                else {
                    setProcessStatus(RefCodeNames.PROCESS_STATUS_CD.IN_PROGRESS);
                    nextTasks(0);
                }
            } else {
                setProcessStatus(RefCodeNames.PROCESS_STATUS_CD.IN_PROGRESS);
                nextTasks(0);
            }

            setProcessStatus(RefCodeNames.PROCESS_STATUS_CD.FINISHED);

        } catch(TaskException te){
            if (TaskActiveInterface.EXCEPTION_CODE_PROCESS_SHOULD_BE_WF_STOPED == te.getCode()){
                try {
                    setProcessStatus(RefCodeNames.PROCESS_STATUS_CD.WF_STOPPED);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                try {
                    setProcessStatus(RefCodeNames.PROCESS_STATUS_CD.FAILED);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                throw new ProcessException("Process id = " + this.processActiveData.getProcessId() + " is failed. ", te);
            }
        } catch (Exception e) {
            try {
                setProcessStatus(RefCodeNames.PROCESS_STATUS_CD.FAILED);
            } catch (Exception e1) {
                e1.printStackTrace();

            }
            throw new ProcessException("Process id = " + this.processActiveData.getProcessId() + " is failed. ", e);
        }
    }

    private void setProcessStatus(String status) throws Exception {
        this.processActiveData = getProcessProvider().setProcessStatus(this.processActiveData, status);

    }

    /**
     * Run all from TaskDataVector
     * @param tasks
     */
    public void runTasks(TaskDataVector tasks) throws Exception {

        int activeExecutedTasks[] = new int[tasks.size()];

        //Execute current tasks
        for (int i = 0; tasks.size() > i; i++) {
            int taskId;
            if (checkReady(processActiveData.getProcessId(),
                    processActiveData.getProcessTemplateId(),
                    ((TaskData) tasks.get(i)).getTaskId())) {

                //create task and run depens on the task type
                taskId = TaskOperations.executeTask(
                        this,                       //current active process
                        ((TaskData) tasks.get(i)),  //current task data
                        this.userName               //the name of thread which initiated
                );

                //save each executed task
                activeExecutedTasks[i] = taskId;

            }
        }

        //Execute all next tasks for executed tasks
        for (int i = 0; i<activeExecutedTasks.length; i++){
            nextTasks(activeExecutedTasks[0]);
        }

    }

        /**
     * Run all from TaskDataVector
     * @param tasks
     */
    public void runFailedTasks(TaskDataVector tasks) throws Exception {

      for (int i = 0; tasks.size() > i; i++) {

        if (checkReady(processActiveData.getProcessId(),
                       processActiveData.getProcessTemplateId(),
                       ((TaskData) tasks.get(i)).getTaskId())) {

          //create task and run
          TaskActive ta = taskProvider.populateActiveTask(processActiveData.getProcessId(),
                                                          (TaskData) tasks.get(i));
          ta.executeTask();
          nextTasks(ta.getTaskId());
        }
      }
    }

    public void nextTasks(int taskId) throws Exception {
        log.info("nextTasks(). start");

        if (processActiveData == null) {
            throw new ProcessException("Process is not created, processActiveDatais null, Task id=" + taskId + "can't be run.");
        }
        //Get all tasks followed after the current task
        TaskDataVector tasks = getNextTasks(taskId);
        //Run all next tasks
        if(!tasks.isEmpty()){
            runTasks(tasks);
        }
    }

    private boolean checkReady(int processId, int templateProcessid, int taskId) throws RemoteException,Exception {
        return this.taskProvider.checkReady(processId, templateProcessid, taskId);
    }

    private TaskDataVector getNextTasks(int finishedTaskId) throws RemoteException,Exception {
        return this.taskProvider.getNextTasksByActiveTask(finishedTaskId,processActiveData.getProcessId());
    }

    /**
     * Itialization the input parameters for the Process before running
     * @param params      PairViewVector
     * @throws Exception
     */
    public void initProcessVariable(PairViewVector params) throws Exception {

        if (processActiveData == null){
            throw new ProcessException("Active Process was not created");
        }

        com.cleanwise.service.api.session.Process processProvider = getProcessProvider();
        int templateProcessId = processActiveData.getProcessTemplateId();

        TaskPropertyDataVector taskProperty = processProvider.getTaskPropertyByTemplateProcessId(templateProcessId);
        PairViewVector pairProcessVariable = new PairViewVector();
        TaskPropertyDataVector mandatoryVar = getTaskProperty(taskProperty, RefCodeNames.TASK_PROPERTY_TYPE_CD.MANDATORY);
        TaskPropertyDataVector optionalVar = getTaskProperty(taskProperty, RefCodeNames.TASK_PROPERTY_TYPE_CD.OPTIONAL);
        TaskPropertyDataVector useVar = getTaskProperty(taskProperty, RefCodeNames.TASK_PROPERTY_TYPE_CD.OUTPUT);

        mandatoryVar.addAll(optionalVar);
        Iterator it = mandatoryVar.iterator();

        //control multiple record
        HashSet rVar = new HashSet();
        while (it.hasNext()) {
            TaskPropertyData taskVar = (TaskPropertyData) it.next();
            if (!rVar.contains(taskVar.getVarName())) {
                Object paramValue = getParamValue(params, taskVar.getVarName());
                pairProcessVariable.add(new PairView(createProcessVariable(taskVar, RefCodeNames.PROCESS_PROPERTY_TYPE_CD.MANDATORY), paramValue));
                rVar.add(taskVar.getVarName());
            }
        }

        Iterator it1 = useVar.iterator();
        while (it1.hasNext()) {
            TaskPropertyData taskVar = (TaskPropertyData) it1.next();
            if (!rVar.contains(taskVar.getVarName())) {
                Object paramValue = getParamValue(params, taskVar.getVarName());
                pairProcessVariable.add(new PairView(createProcessVariable(taskVar, RefCodeNames.PROCESS_PROPERTY_TYPE_CD.USE), paramValue));
                if (!rVar.contains(taskVar.getVarName())) {
                    rVar.add(taskVar.getVarName());
                }
            }
        }

        processProvider.updateProcessPropertyData(pairProcessVariable);

    }

    private Object getParamValue(PairViewVector params, String varName) {
        if (params != null) {
            Iterator it = params.iterator();
            while (it.hasNext()) {
                PairView param = (PairView) it.next();
                if (param.getObject1().equals(varName)) {
                    return param.getObject2();
                }
            }
        }
        return null;
    }

    private ProcessPropertyData createProcessVariable(TaskPropertyData taskVar, String propertyTypeCd) throws Exception, SQLException {

        ProcessPropertyData processVar = ProcessPropertyData.createValue();

        if (propertyTypeCd == null) {
            propertyTypeCd = taskVar.getPropertyTypeCd();
        }

        processVar.setPropertyTypeCd(propertyTypeCd);
        processVar.setTaskVarName(taskVar.getVarName());
        processVar.setVarValue(new byte[0]);
        processVar.setProcessId(processActiveData.getProcessId());
        processVar.setVarClass(taskVar.getVarType());
        processVar.setAddBy(className);
        processVar.setModBy(className);

        return processVar;
    }

    /**
     * Get all failed tasks
     * @return TaskDataVector
     */
    public TaskDataVector getFailedTasks() throws Exception, APIServiceAccessException {
        TaskDataVector taskDataVector;
        taskDataVector = this.taskProvider.getFailedTaskForProcess(processActiveData.getProcessId());

        return taskDataVector;

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

    public ProcessData getProcessActiveData() {
        return processActiveData;
    }

    private com.cleanwise.service.api.session.Process getProcessProvider() throws Exception {
        APIAccess factory = new APIAccess();
        return factory.getProcessAPI();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Task getTaskProvider() {
        return taskProvider;
    }

}
