package com.cleanwise.service.api.process;

import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.value.TaskData;
import com.cleanwise.service.api.value.TaskPropertyDataVector;

import java.rmi.RemoteException;
import java.io.Serializable;

/**
 * Title:        Class for work with Common Tasks
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         20.03.2008
 *
 * @author Evgeny Vlasov, TrinitySoft, Inc.
 */

public class TaskCommon extends TaskActive{

    public TaskCommon(TaskData taskActiveData, TaskPropertyDataVector propertyTask) {
        super(taskActiveData, propertyTask);
    }

    public void executeTask() throws Exception, RemoteException, APIServiceAccessException {
        super.executeTask();
    }
}
