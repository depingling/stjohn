package com.cleanwise.view.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.logic.*;
import com.cleanwise.view.utils.*;


public class QuartzAdmMgrAction extends ActionSuper {
	
    public ActionForward performSub(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {

      // Is there a currently logged on user?
      SessionTool st = new SessionTool(request);
      if ( st.checkSession() == false ) {
          return mapping.findForward("/userportal/logon");
      }
        QuartzAdmMgrForm quartzForm = (QuartzAdmMgrForm)form;
        String action = request.getParameter("action");
        String func = request.getParameter("func");
        String userAction = ((QuartzAdmMgrForm)form).getUserAction();
        String successForward = "init";
        String failForward = "init";
        if (action == null) {
            action = "init";
        }
        try {
            ActionErrors errors = new ActionErrors();
            HttpSession session = request.getSession();
            if (session.getAttribute("jobClassNames") == null){
            	session.setAttribute("jobClassNames", QuartzAdmMgrLogic.getJobClassNameList());
            }
          if("addTrigger".equals(userAction)){
            quartzForm.setUserAction("");
            successForward = "cronTriggerAddEdit";
            failForward = "cronTriggerAddEdit";
            errors = QuartzAdmMgrLogic.initTriggerEdit(request, form);
          } else if ("addParameter".equals(userAction)){
            quartzForm.setUserAction("");
            successForward = "jobAddEdit";
            failForward = "jobAddEdit";
            errors.add(QuartzAdmMgrLogic.addParameter(request, form));
          }else if("jobCopyAddEdit".equals(userAction)){
              quartzForm.setUserAction("");
              successForward = "jobAddEdit";
              failForward = "jobAddEdit";
              errors = QuartzAdmMgrLogic.initJobEdit(request, form, true);
          }else if("jobDelete".equals(userAction)){
              quartzForm.setUserAction("");
              successForward = "jobSearch";
              failForward = "jobAddEdit";
              errors = QuartzAdmMgrLogic.jobDetailDelete(request, form);  
              errors.add(QuartzAdmMgrLogic.jobSearch(request, form));
          } else if ("saveJob".equals(userAction)){
            quartzForm.setUserAction("");
            successForward = "jobSearch";
            failForward = "jobAddEdit";
            errors.add(QuartzAdmMgrLogic.jobSave(request, form));
            errors.add(QuartzAdmMgrLogic.jobSearch(request, form));
          } else if ("jobSearch".equals(userAction)){
            quartzForm.setUserAction("");
            successForward = "jobSearch";
            failForward = "jobSearch";
            errors.add(QuartzAdmMgrLogic.jobSearch(request, form));
          } else if ("triggerSearch".equals(userAction)){
            quartzForm.setUserAction("");
            successForward = "triggerSearch";
            failForward = "triggerSearch";
            errors.add(QuartzAdmMgrLogic.triggerSearch(request, form));
          } else if ("saveTrigger".equals(userAction)){
            quartzForm.setUserAction("");
            successForward = "triggerSearch";
            failForward = "cronTriggerAddEdit";
            errors.add(QuartzAdmMgrLogic.triggerSave(request, form));
          } else if (action.equals("jobSearch")){
        	  boolean initSearch = (func==null);
            successForward = "jobSearch";
            failForward = "jobSearch";
            errors = new ActionErrors();
            if ("start".equals(func)) {
              errors.add(QuartzAdmMgrLogic.jobStart(request, form));
            } else if ("delete".equals(func)) {
              errors.add(QuartzAdmMgrLogic.jobDelete(request, form));
            } else if ("pause".equals(func)) {
              errors.add(QuartzAdmMgrLogic.jobPause(request, form));
            } else if ("unpause".equals(func)) {
              errors.add(QuartzAdmMgrLogic.jobUnPause(request, form));
            }
            if (!initSearch)
            	errors.add(QuartzAdmMgrLogic.jobSearch(request, form));
            else
            	quartzForm.setSearchJobs(new ArrayList());
          }else if(action.equals("triggerSearch")){
            successForward = "triggerSearch";
            failForward = "triggerSearch";
            errors = new ActionErrors();
            if ("delete".equals(func)) {
              errors.add(QuartzAdmMgrLogic.triggerDelete(request, form));
              errors.add(QuartzAdmMgrLogic.triggerSearch(request, form));
            }
          }else if(action.equals("Pause")){
            errors = new ActionErrors();
            errors.add(QuartzAdmMgrLogic.pauseScheduler(request, form));
            errors.add(QuartzAdmMgrLogic.initScheduler(request, form));
          }else if(action.equals("Start")){
            errors = new ActionErrors();
            errors.add(QuartzAdmMgrLogic.startScheduler(request, form));
            errors.add(QuartzAdmMgrLogic.initScheduler(request, form));
          }else if(action.equals("Hard Stop")){
            errors = new ActionErrors();
            errors.add(QuartzAdmMgrLogic.hardStopScheduler(request, form));
            errors.add(QuartzAdmMgrLogic.initScheduler(request, form));
          }else if(action.equals("Soft Stop")){
            errors = new ActionErrors();
            errors.add(QuartzAdmMgrLogic.softStopScheduler(request, form));
            errors.add(QuartzAdmMgrLogic.initScheduler(request, form));
          }else if(action.equals("jobAddEdit")){
            successForward = "jobAddEdit";
            failForward = "jobAddEdit";
            if ("delete".equals(func)) {
              errors.add(QuartzAdmMgrLogic.triggerDelete(request, form));
            }
            errors = QuartzAdmMgrLogic.initJobEdit(request, form);
          }else if(action.equals("cronTriggerAddEdit")){
            successForward = "cronTriggerAddEdit";
            failForward = "cronTriggerAddEdit";
            errors = QuartzAdmMgrLogic.initTriggerEdit(request, form);
          }else if(action.equals("init")){
            errors = QuartzAdmMgrLogic.initScheduler(request, form);
          }else if(action.equals("sort")){
              errors = QuartzAdmMgrLogic.sort(request, form);
              successForward = "jobSearch";
          }
          if(errors.size() > 0){
            saveErrors(request, errors);
            return (mapping.findForward(failForward));
          }else{
            return (mapping.findForward(successForward));
          }
        }catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward(failForward));
        }

    }

}
