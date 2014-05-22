package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.apps.quartz.EventJob;
import com.cleanwise.service.apps.quartz.EventSysJob;
import com.cleanwise.service.api.value.EventProcessView;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Event;
import org.apache.log4j.Logger;

import org.quartz.JobDetail;

/**
 * Title:        EventJobOperations
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class EventJobOperations {

    private static final Logger log = Logger.getLogger(EventJobOperations.class);

    public void jobStart(String jobFullName, JobDetail jobDetail) throws Exception {
        log.info("jobStart => BEGIN.jobFullName:" + jobFullName);
        log.info(jobDetail.getJobClass());
        EventJob job = (EventJob) jobDetail.getJobClass().newInstance();
        job.setEventPriority(jobDetail.getJobDataMap());
        job.execute(jobDetail);
        log.info("jobStart => END.jobFullName:" + jobFullName);
    }

    public void sendJobEvent(String jobFullName, JobDetail jobDetail) throws Exception {
        log.info("sendJobEvent => BEGIN.jobFullName:" + jobFullName);
        log.info(jobDetail.getJobClass());
        EventSysJob eventJob = new EventSysJob();
        eventJob.sendEvent(jobFullName, jobDetail);
        log.info("sendJobEvent => END.jobFullName:" + jobFullName);
    }

    public void sendEvent(EventProcessView event) throws Exception {
        sendEvent(event, this.getClass().getName());
    }

    public void sendEvent(EventProcessView event, String pUser) throws Exception {
        if (event != null && event.getEventData() != null) {
            Event eventEjb = APIAccess.getAPIAccess().getEventAPI();
            eventEjb.addEventProcess(event, pUser);
        }
    }
}
