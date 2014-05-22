package com.cleanwise.service.api.process;

import com.cleanwise.service.api.value.TaskData;
import com.cleanwise.service.api.value.TaskPropertyDataVector;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.process.workflow.WOProcessingData;
import com.cleanwise.service.api.process.workflow.WorkfolowProcessingData;

import java.rmi.RemoteException;

/**
 * Title:        Class for work with Work Flow Tasks 
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         20.03.2008
 *
 * @author Evgeny Vlasov, TrinitySoft, Inc.
 */
public class TaskWorkFlow extends TaskActive{
    private WorkfolowProcessingData workfolowProcessingData;

    public TaskWorkFlow(TaskData taskActiveData, TaskPropertyDataVector propertyTask) {
        super(taskActiveData, propertyTask);
    }

    public void executeTask() throws Exception {
        super.executeTask();

        TaskPropertyDataVector outParams = getTaskProperty(property, RefCodeNames.TASK_PROPERTY_TYPE_CD.OUTPUT);
        Object[] inParamsObj = getParamsObjects(taskData.getProcessId(), outParams);
        workfolowProcessingData = (WorkfolowProcessingData) inParamsObj[0];
    }

    public WorkfolowProcessingData getWorkfolowProcessingData() {
        return workfolowProcessingData;
    }
}
