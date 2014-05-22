package com.cleanwise.view.forms;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.quartz.*;

public class QuartzAdmMgrForm extends ActionForm {

    private String userAction;
    //scheduler attributes
    private String schedulerName;
    private String numJobsExecuted;
    private String runningSince;
    private String schedulerState;
    private String threadPoolSize;
    private String version;

    //search attributes
    private String searchJobName;
    private String searchJobGroup;
    private String searchJobClass;
    private String searchJobDesc;
    private String searchJobParName;
    private String searchJobParValue;
    private ArrayList searchJobs = new ArrayList();
    private ArrayList searchJobStates;
    private ArrayList searchJobPars;

    private String searchTriggerName;
    private String searchTriggerGroup;
    private String searchTriggerJobName;
    private String searchTriggerJobGroup;
    private String searchTriggerDesc;
    private ArrayList searchTriggers = new ArrayList();

    private JobDetail jobDetail = new JobDetail();
    private boolean exists = false;
    private String jobClassName;
    private String jobState;

    private CronTrigger cronTrigger = new CronTrigger();
    private String triggerStartDate;
    private String triggerEndDate;
    private String triggerStartTime;
    private String triggerEndTime;

    private String jobFullName;
    private String searchJobClassSelect;
    private String jobClassNameSelect;
    
    

    private ArrayList parVals = new ArrayList();

    //------------------------------------------------------------------------

    public ArrayList getParVals() {
      return this.parVals;
    }
    public void setParVals(ArrayList v) {
      this.parVals = v;
    }

    public String getParName(int ind){
     return ((ParValData) this.parVals.get(ind)).getName();
    }
    public void setParName(int ind, String pName) {
    	if(parVals == null){
    		parVals = new ArrayList();
    	}
    	while(this.parVals.size() <= ind){
    		this.parVals.add(new ParValData());
    	}
      ((ParValData) this.parVals.get(ind)).setName(pName);
    }

    public String getParValue(int ind){
     return ((ParValData) this.parVals.get(ind)).getValue();
    }
    public void setParValue(int ind, String pValue) {
    	if(parVals == null){
    		parVals = new ArrayList();
    	}
    	while(this.parVals.size() <= ind){
    		this.parVals.add(new ParValData());
    	}
      ((ParValData) this.parVals.get(ind)).setValue(pValue);
    }

    public String getUserAction() {
        return this.userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }

    public CronTrigger getCronTrigger() {
        return cronTrigger;
    }

    public void setCronTrigger(CronTrigger cronTrigger) {
        this.cronTrigger = cronTrigger;
    }

    public String getTriggerStartDate() {
        return this.triggerStartDate;
    }

    public void setTriggerStartDate(String triggerStartDate) {
        this.triggerStartDate = triggerStartDate;
    }

    public String getTriggerStartTime() {
        return this.triggerStartTime;
    }

    public void setTriggerStartTime(String triggerStartTime) {
        this.triggerStartTime = triggerStartTime;
    }

    public String getTriggerEndDate() {
        return this.triggerEndDate;
    }

    public void setTriggerEndDate(String triggerEndDate) {
        this.triggerEndDate = triggerEndDate;
    }

    public String getTriggerEndTime() {
        return this.triggerEndTime;
    }

    public void setTriggerEndTime(String triggerEndTime) {
        this.triggerEndTime = triggerEndTime;
    }

    public boolean isExists() {
        return this.exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getJobClassName() {
//        return this.jobDetail.getJobClass().getName();
        return this.jobClassName;
    }

    public void setJobClassName(String name) {
        this.jobClassName = name;
    }

    public boolean getRequestsRecovery() {
      return this.jobDetail.requestsRecovery();
    }

    public void setRequestsRecovery(boolean requestsRecovery) {
      this.jobDetail.setRequestsRecovery(requestsRecovery);
    }


    public String getSearchJobName() {
        return searchJobName;
    }

    public void setSearchJobName(String searchJobName) {
        this.searchJobName = searchJobName;
    }

    public String getSearchJobGroup() {
        return searchJobGroup;
    }

    public void setSearchJobGroup(String searchJobGroup) {
        this.searchJobGroup = searchJobGroup;
    }

    public String getSearchJobClass() {
        return searchJobClass;
    }

    public void setSearchJobClass(String searchJobClass) {
        this.searchJobClass = searchJobClass;
    }

    public String getSearchJobDesc() {
        return searchJobDesc;
    }

    public void setSearchJobDesc(String searchJobDesc) {
        this.searchJobDesc = searchJobDesc;
    }

    public String getSearchJobParName() {
        return searchJobParName;
    }

    public void setSearchJobParName(String searchJobParName) {
        this.searchJobParName = searchJobParName;
    }

    public String getSearchJobParValue() {
        return searchJobParValue;
    }

    public void setSearchJobParValue(String searchJobParValue) {
        this.searchJobParValue = searchJobParValue;
    }

    public ArrayList getSearchJobs() {
        return searchJobs;
    }

    public void setSearchJobs(ArrayList searchJobs) {
        this.searchJobs = searchJobs;
    }

    public ArrayList getSearchJobStates() {
        return searchJobStates;
    }

    public void setSearchJobStates(ArrayList searchJobStates) {
        this.searchJobStates = searchJobStates;
    }

    public ArrayList getSearchJobPars() {
        return searchJobPars;
    }

    public void setSearchJobPars(ArrayList searchJobPars) {
        this.searchJobPars = searchJobPars;
    }

    public String getSearchTriggerName() {
        return searchTriggerName;
    }

    public void setSearchTriggerName(String searchTriggerName) {
        this.searchTriggerName = searchTriggerName;
    }

    public String getSearchTriggerGroup() {
        return searchTriggerGroup;
    }

    public void setSearchTriggerGroup(String searchTriggerGroup) {
        this.searchTriggerGroup = searchTriggerGroup;
    }

    public String getSearchTriggerJobName() {
        return searchTriggerJobName;
    }

    public void setSearchTriggerJobName(String searchTriggerJobName) {
        this.searchTriggerJobName = searchTriggerJobName;
    }

    public String getSearchTriggerJobGroup() {
        return searchTriggerJobGroup;
    }

    public void setSearchTriggerJobGroup(String searchTriggerJobGroup) {
        this.searchTriggerJobGroup = searchTriggerJobGroup;
    }

    public String getSearchTriggerDesc() {
        return searchTriggerDesc;
    }

    public void setSearchTriggerDesc(String searchTriggerDesc) {
        this.searchTriggerDesc = searchTriggerDesc;
    }

    public ArrayList getSearchTriggers() {
        return searchTriggers;
    }

    public void setSearchTriggers(ArrayList searchTriggers) {
        this.searchTriggers = searchTriggers;
    }

    public String getSchedulerName() {
        return schedulerName;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public String getNumJobsExecuted() {
        return numJobsExecuted;
    }

    public void setNumJobsExecuted(String numJobsExecuted) {
        this.numJobsExecuted = numJobsExecuted;
    }

    public String getRunningSince() {
        return runningSince;
    }

    public void setRunningSince(String runningSince) {
        this.runningSince = runningSince;
    }

    public String getSchedulerState() {
        return schedulerState;
    }

    public void setSchedulerState(String schedulerState) {
        this.schedulerState = schedulerState;
    }

    public String getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(String threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJobFullName() {
        return jobFullName;
    }

    public void setJobFullName(String jobFullName) {
        this.jobFullName = jobFullName;
    }

	public void setSearchJobClassSelect(String searchJobClassSelect) {
		this.searchJobClassSelect = searchJobClassSelect;
	}
	public String getSearchJobClassSelect() {
		return searchJobClassSelect;
	}

	public void setJobClassNameSelect(String jobClassNameSelect) {
		this.jobClassNameSelect = jobClassNameSelect;
	}
	public String getJobClassNameSelect() {
		return jobClassNameSelect;
	}

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        //this.searchJobs = new ArrayList();
        this.jobClassName = "";
        this.jobClassNameSelect = "";
        if (this.jobDetail != null) {
          this.jobDetail.setRequestsRecovery(false);
        }
    }
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {
        return null;
    }

	public static class ParValData {
      private String name;
      private String value;

      public ParValData(){
        name = "";
        value = "";
      }
      public ParValData(String pName, String pValue){
        name = pName;
        value = pValue;
      }
      public String getName() {
        return name;
      }
      public void setName(String pName) {
        this.name = pName;
      }
      public String getValue() {
        return value;
      }
      public void setValue(String pValue) {
        this.value = pValue;
      }
    }


}
