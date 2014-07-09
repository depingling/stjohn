package com.cleanwise.service.apps.quartz;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.eventsys.EventDataVector;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventProcessView;

import org.apache.log4j.Logger;

import org.quartz.*;

import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 * Title:        EventSysJob
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * @author       Alexander Chickin, TrinitySoft, Inc.
 *
 */

public class EventSysJob extends EventJobImpl {

    private static final Logger log = Logger.getLogger(EventSysJob.class);

    private static final String EVENT_JOB = "EVENT_JOB";

    /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public EventSysJob() {
    }

    /**
     * <p/>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a
     * <code>{@link org.quartz.Trigger}</code> fires that is associated with
     * the <code>Job</code>.
     * </p>
     *
     * @throws org.quartz.JobExecutionException
     *          if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {    	
    	try {
    		JobDataMap currJobDataMap = context.getJobDetail().getJobDataMap();
    		setEventPriority(currJobDataMap);
    		JobDetail eJobDetail = getEventJobDetail(context);
    		JobDataMap newJobDataMap = eJobDetail.getJobDataMap();
    		
            if (newJobDataMap.get(Event.PRIORITY_OVERRIDE) == null && currJobDataMap.get(Event.PRIORITY_OVERRIDE) != null){
            	newJobDataMap.put(Event.PRIORITY_OVERRIDE, currJobDataMap.get(Event.PRIORITY_OVERRIDE));
            }
            if (newJobDataMap.get(Event.SUBPROCESS_PRIORITY) == null && currJobDataMap.get(Event.SUBPROCESS_PRIORITY) != null){
            	newJobDataMap.put(Event.SUBPROCESS_PRIORITY, currJobDataMap.get(Event.SUBPROCESS_PRIORITY));
            }
            sendEvent(eJobDetail.getFullName(), eJobDetail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JobExecutionException(e.getMessage(), e);
        }
    }
    
    @Override
	public void execute(JobDetail jobDetail) throws JobExecutionException {
		// do nothing.
	}

    public void sendEvent(String eFullName, JobDetail eJobDetail) throws Exception {
        try {
        	int processId = getProcessId();
            if (eventSysReadyToAcceptJob(processId, "jobFullName", "STRING_VALUE", eJobDetail.getFullName())) {

                log.info("execute => event system is ready to accept job.JobFullName:" + eJobDetail.getFullName());
                Event eventEjb = APIAccess.getAPIAccess().getEventAPI();

                EventProcessView epv = Utility.createEventProcessView(processId, priorityOverride, subProcessPriority);
                
                epv.getProperties().add(Utility.createEventPropertyData("jobFullName",
                        Event.PROCESS_VARIABLE,
                        eFullName,
                        2));
                epv.getProperties().add(Utility.createEventPropertyData("jobDetail",
                        Event.PROCESS_VARIABLE,
                        eJobDetail,
                        3));
                eventEjb.addEventProcess(epv, "EventSysJob");
            }  else {
                log.info("execute => event system contains job already.JobFullName:"+eJobDetail.getFullName());
            }

            log.info("execute => END");

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage(), e);
        }
    }

    private int getProcessId() throws NamingException, APIServiceAccessException, DataNotFoundException, RemoteException {
        Process processEjb = APIAccess.getAPIAccess().getProcessAPI();
        return processEjb.getProcessByName(RefCodeNames.PROCESS_NAMES.EVENT_SYS_JOB).getProcessId();
    }

    private JobDetail getEventJobDetail(JobExecutionContext context) throws Exception {

        String jobName;
        JobDetail eJobDetail;
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        jobName = dataMap.getString(EVENT_JOB);

        if (Utility.isSet(jobName)) {
            StringTokenizer tok = new StringTokenizer(jobName, ".");
            if (tok.countTokens() >= 2) {
                String group = tok.nextToken();
                String name = tok.nextToken();
                JobDetail tJobDetail = context.getScheduler().getJobDetail(name, group);
                if (tJobDetail.getJobClass().newInstance() instanceof EventJob) {
                    eJobDetail = tJobDetail;
                } else {
                    throw new Exception(" Class " + tJobDetail.getClass() + " must implement EventJob interface.");
                }
            } else {
                throw new IllegalArgumentException("Illegal jobName argument.jobName: "+jobName);
            }
        } else {
            throw new Exception("jobName argument not found.");
        }

        return eJobDetail;
    }

}

