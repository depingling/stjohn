package com.cleanwise.service.apps.quartz;

import org.quartz.*;

/**
 * Title:        EventJob
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * @author       Alexander Chickin, TrinitySoft, Inc.
 *
 */

public interface EventJob extends Job {
    public void execute(JobDetail jobDetail) throws JobExecutionException;
    public void setEventPriority(JobDataMap jobDataMap);
}
