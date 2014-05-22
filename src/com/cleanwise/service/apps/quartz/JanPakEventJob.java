package com.cleanwise.service.apps.quartz;

import com.cleanwise.service.api.value.EventProcessView;
import org.quartz.JobDetail;


public interface JanPakEventJob extends EventJob {
    public EventProcessView createEvent(JobDetail pJobDetail, Object... pParams) throws Exception;
}
