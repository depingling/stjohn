package com.cleanwise.view.logic;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.quartz.*;
import org.quartz.impl.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.forms.QuartzAdmMgrForm.*;
import com.cleanwise.view.utils.FormArrayElement;
import com.cleanwise.service.api.util.BeanComparator;
import com.cleanwise.service.api.util.Utility;
import org.apache.log4j.Logger;

public class QuartzAdmMgrLogic {
    private static final Logger log = Logger.getLogger(QuartzAdmMgrLogic.class);

	private static final String className = "QuartzAdmMgrLogic";
    public static final String STARTED = "Started";
    public static final String STOPPED = "Stopped";
    public static final String PAUSED = "Paused";
    private static Scheduler scheduler;

    public static final String TRIGGER_PAUSED = "Paused";
    public static final String TRIGGER_ACTIVE = "Active";

    private  static Scheduler getSchedule() throws SchedulerException{
      if (scheduler == null) {
          SchedulerFactory schedulerFactory = new StdSchedulerFactory();
          scheduler = schedulerFactory.getScheduler();
      }
      return scheduler;
    }


    public static ActionErrors pauseScheduler(HttpServletRequest request, ActionForm form)
            throws Exception {
          ActionErrors errors = new ActionErrors();
          try {
            Scheduler sched = getSchedule();
            if (!sched.isInStandbyMode() && !sched.isShutdown()) {
              sched.standby();
            }
          } catch (SchedulerException e) {
            errors.add("error",
                    new ActionError("error.simpleGenericError", e.getMessage()));

          }
          return errors;
        }

        public static ActionErrors hardStopScheduler(HttpServletRequest request, ActionForm form)
            throws Exception {
          ActionErrors errors = new ActionErrors();
          try {
            Scheduler sched = getSchedule();
            if (!sched.isShutdown()) {
              sched.shutdown(false);
            }
          } catch (SchedulerException e) {
            errors.add("error",
                    new ActionError("error.simpleGenericError", e.getMessage()));

          }
          return errors;
        }

        public static ActionErrors softStopScheduler(HttpServletRequest request, ActionForm form)
            throws Exception {
          ActionErrors errors = new ActionErrors();
          try {
            Scheduler sched = getSchedule();
            if (!sched.isShutdown()) {
              sched.shutdown(true);
            }
          } catch (SchedulerException e) {
            errors.add("error",
                    new ActionError("error.simpleGenericError", e.getMessage()));

          }
          return errors;
        }

        public static ActionErrors addParameter(HttpServletRequest request, ActionForm form)
            throws Exception {
          ActionErrors errors = new ActionErrors();
          try {
            QuartzAdmMgrForm quartzForm = (QuartzAdmMgrForm)form;
            ArrayList pars = quartzForm.getParVals();
            if(pars==null) pars = new ArrayList();
            ParValData parVal = new ParValData("","");
            pars.add(parVal);
            quartzForm.setParVals(pars);
          } catch (Exception e) {
            errors.add("error",
                    new ActionError("error.simpleGenericError", e.getMessage()));

          }
          return errors;
        }


        public static ActionErrors startScheduler(HttpServletRequest request, ActionForm form)
            throws Exception {
          ActionErrors errors = new ActionErrors();
          try {
            Scheduler sched = getSchedule();
            if (sched.isInStandbyMode()) {
              sched.start();
            }
          } catch (SchedulerException e) {
            errors.add("error",
                    new ActionError("error.simpleGenericError", e.getMessage()));

          }
          return errors;
        }

        public static ActionErrors jobStart(HttpServletRequest request, ActionForm form)
                throws Exception {
              ActionErrors errors = new ActionErrors();
              try {
                Scheduler sched = getSchedule();
                String fullName = request.getParameter("fullName");
                StringTokenizer tok = new StringTokenizer(fullName, ".");
                if (tok.countTokens() >= 2) {
                  String group = tok.nextToken();
                  String name = tok.nextToken();
                  sched.triggerJob(name, group, null);
                }
              } catch (SchedulerException e) {
                errors.add("error",
                        new ActionError("error.simpleGenericError", e.getMessage()));

              }
              return errors;
            }

            public static ActionErrors jobDelete(HttpServletRequest request, ActionForm form)
                    throws Exception {
                  ActionErrors errors = new ActionErrors();
                  try {
                    Scheduler sched = getSchedule();
                    String fullName = request.getParameter("fullName");
                    StringTokenizer tok = new StringTokenizer(fullName, ".");
                    if (tok.countTokens() >= 2) {
                      String group = tok.nextToken();
                      String name = tok.nextToken();
                      sched.deleteJob(name, group);
                    }
                  } catch (SchedulerException e) {
                    errors.add("error",
                            new ActionError("error.simpleGenericError", e.getMessage()));

                  }
                  return errors;
                }

            public static ActionErrors jobDetailDelete(HttpServletRequest request, ActionForm form)
	            throws Exception {
	          ActionErrors errors = new ActionErrors();
              QuartzAdmMgrForm quartzForm = (QuartzAdmMgrForm) form;
              try {
	            Scheduler sched = getSchedule();
	            String fullName = quartzForm.getJobFullName();
	            if (Utility.isSet(fullName)) {
		            StringTokenizer tok = new StringTokenizer(fullName, ".");
		            if (tok.countTokens() >= 2) {
		              String group = tok.nextToken();
		              String name = tok.nextToken();
		              sched.deleteJob(name, group);
		            }
              	} else {
              		errors.add("error", new ActionError("error.simpleGenericError", "Cannot delete Job because full name is null." ) ); 
              	}	
	          } catch (SchedulerException e) {
	            errors.add("error",
	                    new ActionError("error.simpleGenericError", e.getMessage()));
	
	          }
	          return errors;
	        }

                public static ActionErrors jobPause(HttpServletRequest request, ActionForm form)
                        throws Exception {
                      ActionErrors errors = new ActionErrors();
                      try {
                        Scheduler sched = getSchedule();
                        String fullName = request.getParameter("fullName");
                        StringTokenizer tok = new StringTokenizer(fullName, ".");
                        if (tok.countTokens() >= 2) {
                          String group = tok.nextToken();
                          String name = tok.nextToken();
                          sched.pauseJob(name, group);
                        }
                      } catch (SchedulerException e) {
                        errors.add("error",
                                new ActionError("error.simpleGenericError", e.getMessage()));

                      }
                      return errors;
                    }

                    public static ActionErrors jobUnPause(HttpServletRequest request, ActionForm form)
                            throws Exception {
                          ActionErrors errors = new ActionErrors();
                          try {
                            Scheduler sched = getSchedule();
                            String fullName = request.getParameter("fullName");
                            StringTokenizer tok = new StringTokenizer(fullName, ".");
                            if (tok.countTokens() >= 2) {
                              String group = tok.nextToken();
                              String name = tok.nextToken();
                              sched.resumeJob(name, group);
                            }
                          } catch (SchedulerException e) {
                            errors.add("error",
                                    new ActionError("error.simpleGenericError", e.getMessage()));

                          }
                          return errors;
                        }

                public static ActionErrors triggerDelete(HttpServletRequest request, ActionForm form)
                        throws Exception {
                      ActionErrors errors = new ActionErrors();
                      try {
                        Scheduler sched = getSchedule();
                        String fullName = request.getParameter("fullName");
                        StringTokenizer tok = new StringTokenizer(fullName, ".");
                        if (tok.countTokens() >= 2) {
                          String group = tok.nextToken();
                          String name = tok.nextToken();
                          sched.unscheduleJob(name, group);
                        }
                      } catch (SchedulerException e) {
                        errors.add("error",
                                new ActionError("error.simpleGenericError", e.getMessage()));

                      }
                      return errors;
                    }
                public static ActionErrors initJobEdit(HttpServletRequest request, ActionForm form) throws Exception {
                	return initJobEdit( request,  form, false);
				}
                
				public static ActionErrors initJobEdit(HttpServletRequest request, ActionForm form, boolean isJobCopy)
                        throws Exception {
                      QuartzAdmMgrForm quartzForm = (QuartzAdmMgrForm) form;
                      ActionErrors errors = new ActionErrors();
                      ArrayList triggerDV = new ArrayList();
                      ArrayList pars = new ArrayList();
                      quartzForm.setSearchTriggers(triggerDV);
                      boolean isCreate = !Utility.isSet(request.getParameter("fullName"));
                      
                      try {
                        Scheduler sched = getSchedule();
                        String fullName = request.getParameter("fullName");
//                        if (null != fullName) {
                        if (!isCreate && !isJobCopy ) {
                          StringTokenizer tok = new StringTokenizer(fullName, ".");
                          if (tok.countTokens() >= 2) {
                            String group = tok.nextToken();
                            String name = tok.nextToken();
                            JobDetail jobDetail = sched.getJobDetail(name, group);
                            quartzForm.setJobDetail(jobDetail);

                            JobDataMap dataMap = jobDetail.getJobDataMap();
                            if (dataMap != null && dataMap.size() != 0) {
                              String[] keys = dataMap.getKeys();
                              for (int i = 0; i < keys.length; i++) {
                                ParValData parVal = new ParValData(keys[i],dataMap.getString(keys[i]));
                                pars.add(parVal);
                              }
                            }

                            quartzForm.setExists(true);
                            quartzForm.setJobClassName(jobDetail.getJobClass().getName());
                            Trigger[] triggers = sched.getTriggersOfJob(name,group);
                            for (int j = 0; j < triggers.length; j++) {
                              triggerDV.add(triggers[j]);
                            }
                            quartzForm.setSearchTriggers(triggerDV);
                          }
                        } else if (isCreate && isJobCopy) {
                            JobDetail jobDetail = new JobDetail();
                            fullName = quartzForm.getJobFullName();
                            log.info("initJobEdit()=====> fullName="+ fullName);
                            StringTokenizer tok = new StringTokenizer(fullName, ".");
                            if (tok.countTokens() >= 2) {
                              String group = tok.nextToken();
                              String name = tok.nextToken();
                              JobDetail jobDetailOrig = sched.getJobDetail(name, group);
                              String jobClassName = jobDetailOrig.getJobClass().getName();
                              String jobDescription = jobDetailOrig.getDescription();
                              
                              JobDataMap dataMap = jobDetailOrig.getJobDataMap();
                              if (dataMap != null && dataMap.size() != 0) {
                                String[] keys = dataMap.getKeys();
                                for (int i = 0; i < keys.length; i++) {
                                  ParValData parVal = new ParValData(keys[i],dataMap.getString(keys[i]));
                                  pars.add(parVal);
                                }
                              }
//                              Trigger[] triggersOrig = sched.getTriggersOfJob(name,group);
//                              for (int j = 0; j < triggersOrig.length; j++) {
//                                  triggerDV.add(triggers[j]);
//                                }

                              quartzForm.setExists(false);
                              jobDetail.setGroup(group);
                              jobDetail.setName("Clone of "+ name);
                              jobDetail.setDescription(jobDescription);
                              quartzForm.setJobDetail(jobDetail);
                              quartzForm.setJobClassName(jobClassName);
                              quartzForm.setJobFullName("");
 //                             quartzForm.setSearchTriggers(triggerDV);
                            }
                        	
                        } else {
                          JobDetail jobDetail = new JobDetail();
                          quartzForm.setJobDetail(jobDetail);
                          quartzForm.setJobFullName("");
                          quartzForm.setJobClassName("");
                          quartzForm.setExists(false);
                        }
                        quartzForm.setParVals(pars);
                      } catch (SchedulerException e) {
                        errors.add("error",
                                new ActionError("error.simpleGenericError", e.getMessage()));

                      }
                      return errors;
                    }

                    private static SimpleDateFormat dateFormatter = new SimpleDateFormat ("M/d/yyyy");
                    private static SimpleDateFormat timeFormatter = new SimpleDateFormat ("HH:mm:ss");
                    public static ActionErrors initTriggerEdit(HttpServletRequest request, ActionForm form)
                            throws Exception {
                          ActionErrors errors = new ActionErrors();
                          try {
                            QuartzAdmMgrForm quartzForm = (QuartzAdmMgrForm) form;
                            Scheduler sched = getSchedule();
                            String jobFullName = quartzForm.getJobFullName();
                            String triggerFullName = request.getParameter("fullName");
                            Trigger trigger = new CronTrigger();
                            if (triggerFullName != null && !"".equals(triggerFullName)) {
                              StringTokenizer tok = new StringTokenizer(triggerFullName, ".");
                              if (tok.countTokens() >= 2) {
                                String group = tok.nextToken();
                                String name = tok.nextToken();
                                trigger = sched.getTrigger(name, group);
                                if (trigger == null) {
                                  trigger = new CronTrigger();
                                }
                                quartzForm.setCronTrigger( (CronTrigger)trigger);
                                Date startTime = quartzForm.getCronTrigger().getStartTime();
                                if (startTime != null) {
                                  quartzForm.setTriggerStartDate(dateFormatter.format(startTime));
                                  quartzForm.setTriggerStartTime(timeFormatter.format(startTime));
                                }
                                else {
                                  quartzForm.setTriggerStartDate(null);
                                  quartzForm.setTriggerStartTime(null);
                                }
                                Date endTime = quartzForm.getCronTrigger().getEndTime();
                                if (endTime != null) {
                                  quartzForm.setTriggerEndDate(dateFormatter.format(endTime));
                                  quartzForm.setTriggerEndTime(timeFormatter.format(endTime));
                                }
                                else {
                                  quartzForm.setTriggerEndDate(null);
                                  quartzForm.setTriggerEndTime(null);
                                }
                                quartzForm.setExists(true);
                              }
                            } else if (jobFullName != null && !"".equals(jobFullName)) {
                              StringTokenizer tok = new StringTokenizer(jobFullName, ".");
                              if (tok.countTokens() >= 2) {
                                String group = tok.nextToken();
                                String name = tok.nextToken();
                                trigger.setJobGroup(group);
                                trigger.setJobName(name);
                              }
                              quartzForm.setTriggerStartDate("");
                              quartzForm.setTriggerStartTime("");
                              quartzForm.setTriggerEndDate("");
                              quartzForm.setTriggerEndTime("");
                              quartzForm.setExists(false);
                            } else {
                              quartzForm.setTriggerStartDate("");
                              quartzForm.setTriggerStartTime("");
                              quartzForm.setTriggerEndDate("");
                              quartzForm.setTriggerEndTime("");
                              quartzForm.setExists(false);
                            }
                            quartzForm.setCronTrigger( (CronTrigger)trigger);
                          } catch (SchedulerException e) {
                            e.printStackTrace();
                            errors.add("error",
                                    new ActionError("error.simpleGenericError", e.getMessage()));

                          }
                          return errors;
                        }

                        public static ActionErrors jobSave(HttpServletRequest request, ActionForm form)
                                throws Exception {
                              ActionErrors errors = new ActionErrors();
                              QuartzAdmMgrForm quartzForm = (QuartzAdmMgrForm) form;
                              try {
                                Scheduler sched = getSchedule();
                                JobDetail jobDetail = quartzForm.getJobDetail();

                                String jobClassName = quartzForm.getJobClassNameSelect();
                                if (!Utility.isSet(jobClassName))
                                	jobClassName = quartzForm.getJobClassName();
                                Class jobClass = Class.forName(jobClassName);

                                ArrayList pars = quartzForm.getParVals();
                                JobDataMap dataMap = new JobDataMap();
                                if (pars != null) {
                                  for (int i = 0; i < pars.size(); i++) {
                                    ParValData parVal = (ParValData)pars.get(i);
                                    if (parVal.getName() != null && !"".equals(parVal.getName())) {
                                      dataMap.put(parVal.getName(), parVal.getValue());
                                    }
                                  }
                                }
                                jobDetail.setDurability(true);
                                jobDetail.setJobClass(jobClass);
                                jobDetail.setJobDataMap(dataMap);
                                jobDetail.validate();
                                sched.addJob(jobDetail, quartzForm.isExists());
                                
                                updateJobClassNameList(request, jobClassName);
                                
                                if (quartzForm.isExists()) {
                                    String jobFullName = quartzForm.getJobFullName();
                                    if (jobFullName != null && !"".equals(jobFullName)) {
                                        StringTokenizer tok = new StringTokenizer(jobFullName, ".");
                                        if (tok.countTokens() >= 2) {
                                            String group = tok.nextToken();
                                            String name = tok.nextToken();
                                            if (!name.equals(jobDetail.getName()) || !group.equals(jobDetail.getGroup())) {
                                                Trigger[] triggers = sched.getTriggersOfJob(name,group);
                                                boolean active = false;
                                                for (int j = 0; j < triggers.length; j++) {
                                                    if(Trigger.STATE_NORMAL ==
                                                         sched.getTriggerState(triggers[j].getName(), triggers[j].getGroup())) {
                                                      active = true;
                                                    }
                                                    triggers[j] = (Trigger)triggers[j].clone();
                                                    triggers[j].setJobName(jobDetail.getName());
                                                    triggers[j].setJobGroup(jobDetail.getGroup());
                                                }
                                                sched.deleteJob(name, group);
                                                for (int j = 0; j < triggers.length; j++) {
                                                    sched.scheduleJob(triggers[j]);
                                                }
                                                if (!active) {
                                                    sched.pauseJob(jobDetail.getName(), jobDetail.getGroup());
                                                }
                                            }
                                        }
                                    }
                                }
                              } catch (SchedulerException e) {
                                errors.add("error",
                                        new ActionError("error.simpleGenericError", e.getMessage()));
                              } catch (ClassNotFoundException e) {
                                errors.add("error",
                                        new ActionError("error.simpleGenericError", e.getMessage() + " class is not found"));
                              }
                              return errors;
                            }


						private static void updateJobClassNameList(
								HttpServletRequest request, String jobClassName) {
							HttpSession session = request.getSession();
							List<FormArrayElement> jobClassNames = (List<FormArrayElement>) session.getAttribute("jobClassNames");
							String className = jobClassName.substring(jobClassName.lastIndexOf('.')+1);
							FormArrayElement jobElm = new FormArrayElement(className,jobClassName);
							if (!jobClassNames.contains(jobElm)){
								jobClassNames.add(jobElm);
								BeanComparator comparator = new BeanComparator(new String[]{"getLabel"});
								Collections.sort(jobClassNames,comparator);
							}
						}

                    private static Date getDate(String dateS, String timeS) throws Exception {
                      Date day = dateFormatter.parse(dateS);
                      GregorianCalendar gday = new GregorianCalendar();
                      gday.setTime(day);
                      Date time = timeFormatter.parse(timeS);
                      GregorianCalendar gtime = new GregorianCalendar();
                      gtime.setTime(time);
                      gday.set(java.util.Calendar.HOUR_OF_DAY,gtime.get(java.util.Calendar.HOUR_OF_DAY));
                      gday.set(java.util.Calendar.MINUTE,gtime.get(java.util.Calendar.MINUTE));
                      gday.set(java.util.Calendar.SECOND,gtime.get(java.util.Calendar.SECOND));
                      return gday.getTime();
                    }

                    public static ActionErrors triggerSave(HttpServletRequest request, ActionForm form)
                            throws Exception {
                          ActionErrors errors = new ActionErrors();
                          QuartzAdmMgrForm quartzForm = (QuartzAdmMgrForm) form;
                          try {
                            Scheduler sched = getSchedule();
                            CronTrigger trigger = quartzForm.getCronTrigger();
                            if (quartzForm.getTriggerStartDate() != null && !"".equals(quartzForm.getTriggerStartDate())) {
                              if (quartzForm.getTriggerStartTime() != null && !"".equals(quartzForm.getTriggerStartTime())) {
                                trigger.setStartTime(getDate(quartzForm.getTriggerStartDate(), quartzForm.getTriggerStartTime()));
                              } else {
                                trigger.setStartTime(getDate(quartzForm.getTriggerStartDate(), "00:00:00"));
                              }
                            }
                            if (quartzForm.getTriggerEndDate() != null && !"".equals(quartzForm.getTriggerEndDate())) {
                              if (quartzForm.getTriggerEndTime() != null && !"".equals(quartzForm.getTriggerEndTime())) {
                                trigger.setEndTime(getDate(quartzForm.getTriggerEndDate(), quartzForm.getTriggerEndTime()));
                              } else {
                                trigger.setEndTime(getDate(quartzForm.getTriggerEndDate(), "23:59:59"));
                              }
                            } else {
                              trigger.setEndTime(null);
                            }
                            Trigger old = sched.getTrigger(trigger.getName(), trigger.getGroup());
                            if (old == null) {
                              sched.scheduleJob(trigger);
                            } else {
                              sched.rescheduleJob(trigger.getName(), trigger.getGroup(), trigger);
                            }
                          } catch (SchedulerException e) {
                            errors.add("error",
                                    new ActionError("error.simpleGenericError", e.getMessage()));
                          } catch (ParseException e) {
                            errors.add("error",
                                    new ActionError("error.simpleGenericError", e.getMessage()));
                          } catch (UnsupportedOperationException e) {
                              errors.add("error",
                                      new ActionError("error.simpleGenericError", e.getMessage()));
                          }
                          return errors;
                        }



    public static ActionErrors initScheduler(HttpServletRequest request, ActionForm form)
            throws Exception {
          ActionErrors errors = new ActionErrors();
          QuartzAdmMgrForm quartzForm = (QuartzAdmMgrForm) form;
          try {
            Scheduler sched = getSchedule();
            quartzForm.setSchedulerName(sched.getSchedulerName());
            quartzForm.setNumJobsExecuted(String.valueOf(sched.getMetaData().numJobsExecuted()));
            quartzForm.setRunningSince(String.valueOf(sched.getMetaData().runningSince()));
            if (sched.isShutdown()) {
              quartzForm.setSchedulerState(STOPPED);
            }
            else if (sched.isPaused()) {
              quartzForm.setSchedulerState(PAUSED);
            }
            else {
              quartzForm.setSchedulerState(STARTED);
            }

            quartzForm.setThreadPoolSize(String.valueOf(sched.getMetaData(). getThreadPoolSize()));
            quartzForm.setVersion(sched.getMetaData().getVersion());
          } catch (SchedulerException e) {
            errors.add("error",
                    new ActionError("error.simpleGenericError", e.getMessage()));

          }
          return errors;
        }

    public static ActionErrors jobSearch(HttpServletRequest request, ActionForm form)
            throws Exception {
          ActionErrors errors = new ActionErrors();
          QuartzAdmMgrForm quartzForm = (QuartzAdmMgrForm) form;
          try {
            Scheduler sched = getSchedule();
            ArrayList jobDV = new ArrayList();
            ArrayList stateDV = new ArrayList();
            ArrayList parDV = new ArrayList();
            if (!sched.isShutdown()) {
              String searchJobGroup = quartzForm.getSearchJobGroup();
              String searchJobName = quartzForm.getSearchJobName();
              String searchJobClass = quartzForm.getSearchJobClassSelect();
              if (!Utility.isSet(searchJobClass))
            	  searchJobClass = quartzForm.getSearchJobClass();
              String searchJobParName = quartzForm.getSearchJobParName();
              String searchJobParValue = quartzForm.getSearchJobParValue();
              String[] jobGroups = sched.getJobGroupNames();
              ArrayList addedJobs = new ArrayList(jobGroups.length);
              for (int i = 0; i < jobGroups.length; i++) {
                String groupName = jobGroups[i];
                  if (null == searchJobGroup || "".equals(searchJobGroup) ||
                      !"".equals(searchJobGroup) &&
                      groupName.toUpperCase().indexOf(searchJobGroup.toUpperCase()) >= 0) {
                    String[] jobs = sched.getJobNames(groupName);
                    for (int j = 0; j < jobs.length; j++) {
                      String job = jobs[j];
                      if (null == searchJobName || "".equals(searchJobName) ||
                          !"".equals(searchJobName) &&
                          job.toUpperCase().indexOf(searchJobName.toUpperCase()) >= 0) {
                        JobDetail jobDetail = sched.getJobDetail(job, groupName);
                        String key = job + groupName;
                        if (null == searchJobClass || "".equals(searchJobClass) ||
                            !"".equals(searchJobClass) &&
                            jobDetail.getJobClass().getName().toUpperCase().indexOf(searchJobClass.toUpperCase()) >= 0) {

                          if (null != searchJobParName && !"".equals(searchJobParName) ||
                              null != searchJobParValue && !"".equals(searchJobParValue)) {
                            String[] keys = jobDetail.getJobDataMap().getKeys();
                            for (int k = 0; k < keys.length; k++) {
                              if ((null == searchJobParName || "".equals(searchJobParName) ||
                                  null != searchJobParName && !"".equals(searchJobParName) &&
                                  keys[k].toUpperCase().indexOf(searchJobParName.toUpperCase()) >= 0) &&
                                  (null == searchJobParValue || "".equals(searchJobParValue) ||
                                  null != searchJobParValue && !"".equals(searchJobParValue) &&
                                  jobDetail.getJobDataMap().getString(keys[k]).toUpperCase().indexOf(searchJobParValue.toUpperCase()) >= 0 &&
                                  !"password".equalsIgnoreCase(keys[k]))) {
                                if (!addedJobs.contains(key + keys[k] + jobDetail.getJobDataMap().getString(keys[k]))) {
                                  jobDV.add(jobDetail);
                                  addedJobs.add(key + keys[k] + jobDetail.getJobDataMap().getString(keys[k]));
                                  String value = jobDetail.getJobDataMap().getString(keys[k]);
                                  if ("password".equalsIgnoreCase(keys[k])) {
                                    value = value.replaceAll(".","*");
                                  }
                                  parDV.add(keys[k] + "=" + value);
                                  Trigger[] triggers = sched.getTriggersOfJob(jobDetail.getName(),jobDetail.getGroup());
                                  boolean paused = false;
                                  boolean active = false;
                                  for (int m = 0; m < triggers.length; m++) {
                                    if(Trigger.STATE_PAUSED ==
                                       sched.getTriggerState(triggers[m].getName(), triggers[m].getGroup())) {
                                      paused = true;
                                    } else if(Trigger.STATE_NORMAL ==
                                         sched.getTriggerState(triggers[m].getName(), triggers[m].getGroup())) {
                                      active = true;
                                    }
                                  }
                                  if (active) {
                                    stateDV.add(TRIGGER_ACTIVE);
                                  } else if (paused) {
                                    stateDV.add(TRIGGER_PAUSED);
                                  } else {
                                    stateDV.add("");
                                  }
                                }
                              }
                            }
                          } else {
                            if (!addedJobs.contains(key)) {
                              jobDV.add(jobDetail);
                              addedJobs.add(key);
                              parDV.add("");
                              Trigger[] triggers = sched.getTriggersOfJob(jobDetail.getName(),jobDetail.getGroup());
                              boolean paused = false;
                              boolean active = false;
                              for (int m = 0; m < triggers.length; m++) {
                                if(Trigger.STATE_PAUSED ==
                                   sched.getTriggerState(triggers[m].getName(), triggers[m].getGroup())) {
                                  paused = true;
                                } else if(Trigger.STATE_NORMAL ==
                                     sched.getTriggerState(triggers[m].getName(), triggers[m].getGroup())) {
                                  active = true;
                                }
                              }
                              if (active) {
                                stateDV.add(TRIGGER_ACTIVE);
                              } else if (paused) {
                                stateDV.add(TRIGGER_PAUSED);
                              } else {
                                stateDV.add("");
                              }
                            }
                          }
                        }
                      }
                    }
                  }
              }
            }
            quartzForm.setSearchJobs(jobDV);
            quartzForm.setSearchJobStates(stateDV);
            quartzForm.setSearchJobPars(parDV);
            BeanComparator comparator = new BeanComparator(new String[]{"getName"});
    		Collections.sort(jobDV,comparator);
          } catch (SchedulerException e) {
            errors.add("error",
                    new ActionError("error.simpleGenericError", e.getMessage()));

          }
          return errors;

        }

        public static ActionErrors triggerSearch(HttpServletRequest request, ActionForm form)
                throws Exception {
              ActionErrors errors = new ActionErrors();
              try {
                QuartzAdmMgrForm quartzForm = (QuartzAdmMgrForm) form;
                ArrayList triggerDV = new ArrayList();
                String searchTriggerJobGroup = quartzForm.getSearchTriggerJobGroup();
                String searchTriggerJobName = quartzForm.getSearchTriggerJobName();
                String searchTriggerGroup = quartzForm.getSearchTriggerGroup();
                String searchTriggerName = quartzForm.getSearchTriggerName();
                Scheduler sched = getSchedule();
                if (!sched.isShutdown()) {
                  String[] triggerGroups = sched.getTriggerGroupNames();
                  for (int i = 0; i < triggerGroups.length; i++) {
                    String groupName = triggerGroups[i];
                    if (null == searchTriggerGroup || "".equals(searchTriggerGroup) ||
                        !"".equals(searchTriggerGroup) &&
                        groupName.toUpperCase().indexOf(searchTriggerGroup.toUpperCase()) >= 0) {
                      String[] triggerNames = sched.getTriggerNames(groupName);
                      for (int j = 0; j < triggerNames.length; j++) {
                        String triggerName = triggerNames[j];
                        if (null == searchTriggerName || "".equals(searchTriggerName) ||
                            !"".equals(searchTriggerName) &&
                            triggerName.toUpperCase().indexOf(searchTriggerName.toUpperCase()) >= 0) {
                          Trigger trigger =
                              sched.getTrigger(triggerName, groupName);
                          if ((null == searchTriggerJobName || "".equals(searchTriggerJobName) || !"".equals(searchTriggerJobName) &&
                              trigger.getJobName().toUpperCase().indexOf(searchTriggerJobName.toUpperCase()) >= 0) &&
                              (null == searchTriggerJobGroup || "".equals(searchTriggerJobGroup) || !"".equals(searchTriggerJobGroup) &&
                              trigger.getJobGroup().toUpperCase().indexOf(searchTriggerJobGroup.toUpperCase()) >= 0))                              {
                            triggerDV.add(trigger);
                          }
                        }
                      }
                    }
                  }
                }
                quartzForm.setSearchTriggers(triggerDV);
              } catch (SchedulerException e) {
                errors.add("error",
                        new ActionError("error.simpleGenericError", e.getMessage()));

              }
              return errors;

            }
        
    public static List<FormArrayElement> getJobClassNameList() throws Exception{
    	List<String> jobClassNames = new ArrayList<String>();
    	List<FormArrayElement> returnList = new ArrayList<FormArrayElement>();
        
    
    	Scheduler sched = getSchedule();   
    	String[] jobGroups = sched.getJobGroupNames();
        for (int i = 0; i < jobGroups.length; i++) {
        	String groupName = jobGroups[i];
        	String[] jobs = sched.getJobNames(groupName);
            for (int j = 0; j < jobs.length; j++) {
              JobDetail jobDetail = sched.getJobDetail(jobs[j], groupName);
              String className = jobDetail.getJobClass().getName();
              className = className.substring(className.lastIndexOf('.')+1);
              if (!jobClassNames.contains(className)) {
            	  jobClassNames.add(className);
            	  returnList.add(new FormArrayElement(className,jobDetail.getJobClass().getName()));
              }
            }
        }
        BeanComparator comparator = new BeanComparator(new String[]{"getLabel"});
		Collections.sort(returnList,comparator);
        return returnList;
    }


	public static ActionErrors sort(HttpServletRequest request, ActionForm form) {
		ActionErrors ae = new ActionErrors();

		String fieldName = (String) request.getParameter("sortField");
		if (fieldName == null) {
			ae.add("error", new ActionError("error.systemError",
					"No sort field name found"));
			return ae;
		}
		QuartzAdmMgrForm quartzForm = (QuartzAdmMgrForm) form;
		BeanComparator comparator = new BeanComparator(new String[]{fieldName});
		Collections.sort(quartzForm.getSearchJobs(),comparator);
		
		return ae;
	}

}
