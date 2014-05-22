package com.cleanwise.service.api.process;

import com.cleanwise.service.api.APIServiceAccessException;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Title:        Class implements common task interface
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         20.03.2008
 *
 * @author Evgeny Vlasov, TrinitySoft, Inc.
 */
public interface TaskActiveInterface extends Serializable {

    public static final int EXCEPTION_CODE_PROCESS_SHOULD_BE_WF_STOPED = 1;

    /**
     * Execute task
     */
    public void executeTask() throws Exception;

    public int getTaskId();
}
