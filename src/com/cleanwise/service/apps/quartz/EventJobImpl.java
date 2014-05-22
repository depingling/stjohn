package com.cleanwise.service.apps.quartz;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.Utility;


public abstract class EventJobImpl implements EventJob {
	int priorityOverride = 0;
	int subProcessPriority = 0;

  public void execute(JobExecutionContext context) throws JobExecutionException {
	  JobDetail jobDetail = context.getJobDetail();
	  setEventPriority(jobDetail.getJobDataMap());
	  execute(jobDetail);
  }
  
  public void setEventPriority(JobDataMap jobDataMap){
	  String priorityOverrideStr = (String) jobDataMap.get(Event.PRIORITY_OVERRIDE);
      if (Utility.isSet(priorityOverrideStr)){
      	priorityOverride = new Integer(priorityOverrideStr).intValue();
      }
      priorityOverrideStr = (String) jobDataMap.get(Event.SUBPROCESS_PRIORITY);
      if (Utility.isSet(priorityOverrideStr)){
    	  subProcessPriority = new Integer(priorityOverrideStr).intValue();
      }
  }

}
