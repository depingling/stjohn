package com.cleanwise.view.actions;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.logic.*;
import com.cleanwise.view.utils.*;


public class CompassAdmMgrAction extends ActionSuper {

    public ActionForward performSub(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {

      // Is there a currently logged on user?
      SessionTool st = new SessionTool(request);
      if ( st.checkSession() == false ) {
          return mapping.findForward("/userportal/logon");
      }
        CompassAdmMgrForm quartzForm = (CompassAdmMgrForm)form;
        String action = request.getParameter("action");
        String func = request.getParameter("func");
        String successForward = "init";
        String failForward = "init";
        if (action == null) {
            action = "init";
        }
        try {
            ActionErrors errors = new ActionErrors();
          if(action.equals("Pause")){
            errors = new ActionErrors();
            errors.add(CompassAdmMgrLogic.startIndexing(request, form));
            errors.add(CompassAdmMgrLogic.init(request, form));
          }else if(action.equals("Start Indexing")){
            errors = new ActionErrors();
            errors.add(CompassAdmMgrLogic.startIndexing(request, form));
            errors.add(CompassAdmMgrLogic.init(request, form));
          }else if(action.equals("Stop Indexing")){
            errors = new ActionErrors();
            errors.add(CompassAdmMgrLogic.stopIndexing(request, form));
            errors.add(CompassAdmMgrLogic.init(request, form));
          }else if(action.equals("Start Gps")){
            errors = new ActionErrors();
            errors.add(CompassAdmMgrLogic.startGps(request, form));
            errors.add(CompassAdmMgrLogic.init(request, form));
          }else if(action.equals("Stop Gps")){
            errors = new ActionErrors();
            errors.add(CompassAdmMgrLogic.stopGps(request, form));
            errors.add(CompassAdmMgrLogic.init(request, form));
          }else if(action.equals("Enable Mirroring")){
            errors = new ActionErrors();
            errors.add(CompassAdmMgrLogic.enableMirroring(request, form));
            errors.add(CompassAdmMgrLogic.init(request, form));
          }else if(action.equals("Disable Mirroring")){
            errors = new ActionErrors();
            errors.add(CompassAdmMgrLogic.disableMirroring(request, form));
            errors.add(CompassAdmMgrLogic.init(request, form));
          }else if(action.equals("search")){
            errors = new ActionErrors();
            successForward = "search";
            failForward = "search";
          }else if(action.equals("Search")){
            errors = new ActionErrors();
            successForward = "search";
            failForward = "search";
            errors.add(CompassAdmMgrLogic.search(request, form));
          }else if(action.equals("init") || action.equals("Refresh")){
            errors.add(CompassAdmMgrLogic.init(request, form));
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
