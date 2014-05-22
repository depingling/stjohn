package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.*;
import com.cleanwise.service.api.process.workflow.WorkfolowProcessingData;
import com.cleanwise.service.api.session.Task;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.TaskData;
import org.apache.log4j.Logger;

/**
 * Title:        Class presents operations for work with tasks
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         20.03.2008
 *
 * @author Evgeny Vlasov, TrinitySoft, Inc.
 */
public class TaskOperations {

    private final static Logger log = Logger.getLogger(TaskOperations.class);

    public static int executeTask(ProcessActive processActive, TaskData taskData, String userName) throws Exception {

        log.info("executeTask()[" + taskData.getTaskName() + "] => BEGIN");

        Task taskProvider = TaskOperations.getTaskProvider();
        TaskActiveInterface task;

        task = taskProvider.createActiveTask(
                processActive.getProcessActiveData().getProcessId(), //Active Process Id
                taskData,                                            //task template Id
                userName                                             //Active Process which initiated creation
        );

        //create task and run it depens on the task type
        if(RefCodeNames.TASK_OPERATION_TYPE_CD.COMMON.equals(taskData.getOperationTypeCd())){
            log.info("executeTask() [" + taskData.getTaskName() + "] => Will be started execution for COMMON task");

            TaskCommon tc = (TaskCommon) task;
            tc.setName(taskData.getTaskName());
            tc.executeTask();

        }
        else if(RefCodeNames.TASK_OPERATION_TYPE_CD.WF.equals(taskData.getOperationTypeCd())){
            log.info("executeTask() [" + taskData.getTaskName() + "] => Will be started execution for WF task");

            TaskWorkFlow tw = (TaskWorkFlow) task;
            tw.setName(taskData.getTaskName());
            tw.executeTask();

            log.info("executeTask() [" + taskData.getTaskName() + "] => WF has returned - " + tw.getWorkfolowProcessingData().getWhatNext());
            if (!WorkfolowProcessingData.STATUS_PASSED.equals(tw.getWorkfolowProcessingData().getWhatNext())) {
                if (!WorkfolowProcessingData.STOP_AND_RETURN.equals(tw.getWorkfolowProcessingData().getWhatNext())) {
                    throw new TaskException(TaskActiveInterface.EXCEPTION_CODE_PROCESS_SHOULD_BE_WF_STOPED, tw.getWorkfolowProcessingData());
                }
            }

        } else{
            log.info("executeTask() [" + taskData.getTaskName() + "] => Will be started execution for COMMON task");

            TaskCommon tc = (TaskCommon) task;
            tc.setName(taskData.getTaskName());
            tc.executeTask();
        }

        log.info("executeTask() [" + taskData.getTaskName() + "] => END");

        return task.getTaskId();
    }

    public static Task getTaskProvider() throws Exception {
        APIAccess factory = new APIAccess();
        return factory.getTaskAPI();
    }

}
