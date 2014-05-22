
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.forms.ReportScheduleForm;
import com.cleanwise.view.logic.ReportScheduleLogic;
import com.cleanwise.view.utils.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import com.cleanwise.view.logic.AnalyticReportLogic;
import com.cleanwise.service.api.util.Utility;



/**
 */
public final class ReportScheduleAction extends ActionSuper {


    // ----------------------------------------------------- Public Methods


    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param actionForm The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    public ActionForward performSub(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {

        // Determine the store manager f_action to be performed
        ReportScheduleForm pForm = (ReportScheduleForm) form;

        String action = request.getParameter("action");
        String setFilter = request.getParameter("setFilter");

        String command = request.getParameter("command");
        if (action != null && action.equals("BBBBBB")) {
          if (command != null) {
            action = command;
          }
        }

        if (action == null) {
          if(pForm.getNewScheduleReportId()>0) {
            action = "addSchedule";
          } else if(pForm.getUserToAdd()>0) {
            action = "addUser";
          } else if(pForm.getGroupToAdd()>0) {
            action = "addGroup";
          } else if("setScheduleFilter".equals(setFilter)) {
            action = "Set Schedule Filter";
          } else if("clearScheduleFilter".equals(setFilter)) {
            action = "Clear Schedule Filter";
          }else {
            action = "init";
          }
        }
        pForm.setLastAction(action);
        request.setAttribute("action", action);

        // Process the f_action
        try {
            if (action.equals("init")){
                ReportScheduleLogic.init(request, pForm);
            } else if ("Return Selected".equals(action)) {
                String submitFormIdent = request.getParameter("jspSubmitIdent");
                if (("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_USER_FORM).equals(submitFormIdent)) {
                  ReportScheduleLogic.addUsers(request,form);
                } else if (("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_GROUP_FORM).equals(submitFormIdent)) {
                  ReportScheduleLogic.addGroups(request,form);
                }
                return (mapping.findForward("success"));
            } else if(action.equals("Set Schedule Filter")) {
                ReportScheduleLogic.init(request, pForm);
            } else if(action.equals("Clear Schedule Filter")) {
               ReportScheduleLogic.clearFilter(request, pForm);
            } else if(action.equals("sort")) {
                ReportScheduleLogic.sort(request, pForm);
            } else if(action.equals("detail")) {
               if (Utility.isSet(request.getParameter("id"))){
                 ActionErrors ae = ReportScheduleLogic.getSchedule(request,
                     pForm);
                 if (ae.size() > 0) {
                   makeMessageString(request, ae);
                   return mapping.findForward("success");
                 }
               } else {
                ActionErrors ae =  ReportScheduleLogic.addSchedule(request, pForm);
                if(ae.size()>0) {
                  makeMessageString(request,ae);
                  return mapping.findForward("success");
                }
               }
            } else if(action.equals("addSchedule")) {
               ActionErrors ae =  ReportScheduleLogic.addSchedule(request, pForm);
               if(ae.size()>0) {
                 makeMessageString(request,ae);
                 return mapping.findForward("success");
               }
            } else if(action.equals("Delete Selected Schedules")) {
               ActionErrors ae =  ReportScheduleLogic.delSchedules(request, pForm);
               if(ae.size()>0) {
                 makeMessageString(request,ae);
                 return mapping.findForward("success");
               }
            } else if(action.equals("addUser")) {
               ActionErrors ae =  ReportScheduleLogic.addUser(request, pForm);
               if(ae.size()>0) {
                 makeMessageString(request,ae);
                 return mapping.findForward("success");
               }
            } else if(action.equals("addGroup")) {
               ActionErrors ae =  ReportScheduleLogic.addGroup(request, pForm);
               if(ae.size()>0) {
                 makeMessageString(request,ae);
                 //saveErrors(request,ae);
                 return mapping.findForward("success");
               }
            } else if(action.equals("Remove Selected Users")) {
               ActionErrors ae =  ReportScheduleLogic.delUsers(request, pForm);
               if(ae.size()>0) {
                 makeMessageString(request,ae);
                 //saveErrors(request,ae);
                 return mapping.findForward("success");
               }
            } else if(action.equals("Remove Selected Groups")) {
               ActionErrors ae =  ReportScheduleLogic.delGroups(request, pForm);
               if(ae.size()>0) {
                 makeMessageString(request,ae);
                 //saveErrors(request,ae);
                 return mapping.findForward("success");
               }
            } else if(action.equals("Save Schedule")) {
               ActionErrors ae =  ReportScheduleLogic.saveSchedule(request, pForm);
               if(ae.size()>0) {
                 makeMessageString(request,ae);
                 //saveErrors(request,ae);
                 return mapping.findForward("success");
               }
            } else if(action.equals("Save & Run Report")) {
               ActionErrors ae =  ReportScheduleLogic.saveAndRun(request, response, pForm);
               if(ae.size()>0) {
                 makeMessageString(request,ae);
                 //saveErrors(request,ae);
                 return mapping.findForward("success");
               }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            //return (mapping.findForward("failure"));
            throw new ServletException(e.getMessage());
        }
        return (mapping.findForward("success"));
    }

    String makeMessageString(HttpServletRequest request, ActionErrors ae) {
      org.apache.struts.util.MessageResources mr = getResources(request);
      String errorMessage = "";
      java.util.Iterator iter = ae.get();
      while (iter.hasNext()) {
        ActionError err = (ActionError) iter.next();
        String mess = getMessage(mr,request,err.getKey(),err.getValues());
        if(errorMessage.length()>0) errorMessage += "   "; //Character.LINE_SEPARATOR;
        errorMessage += mess;
      }
      request.setAttribute("errorMessage", errorMessage);
      return errorMessage;
    }
}
