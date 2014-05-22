
package com.cleanwise.view.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import com.cleanwise.view.forms.AnalyticReportForm;
import com.cleanwise.view.logic.AnalyticReportLogic;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.value.GenericReportData;
import java.util.Enumeration;
import com.cleanwise.service.api.value.GenericReportControlViewVector;
import com.cleanwise.service.api.value.GenericReportControlView;
import java.util.HashMap;


/**
 * Implementation of <strong>Action</strong> that processes the
 * OrderOp manager page.
 */

public final class AnalyticReportAction extends ActionSuper {
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
   throws IOException, ServletException
   {
               ActionForward af =
                       performSubSynch(mapping, form, request, response);
               return af;

   }

    public  ActionForward performSubSynch(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {

        // Determine the store manager f_action to be performed
        AnalyticReportForm pForm = (AnalyticReportForm) form;

        GenericReportData report = pForm.getReport();
        String action = request.getParameter("action");
        String command = request.getParameter("command");




        if (action != null && action.equals("BBBBBB")) {
          if (command != null) {
            action = command;
          }
        }
        if (isClearRequired(action)) {
            pForm.setReportResults(null);
        }


        /*
        ActionForward af = performLocateAction(form, request, mapping, action);
        if (af != null) {
            return af;
        }
        */
        String setFilter = request.getParameter("setFilter");
        if (action == null) {
          if("clearFilter".equals(setFilter)) {
            action = "Clear Filter";
          } else if("setArchiveFilter".equals(setFilter)) {
            action = "Set Archive Filter";
          } else if("clearArchiveFilter".equals(setFilter)) {
            action = "Clear Archive Filter";
          } else {
            action = "init";
          }

        }
        request.setAttribute("action", action);
        if(!action.equals("Set Archive Filter") &&
           !action.equals("Clear Archive Filter")) {
          pForm.setMyReportsFl(pForm.getMyReportsFlBase());
        } else {
          pForm.setMyReportsFlBase(pForm.getMyReportsFl());
        }
        // Process the f_action
        boolean tokenMatchFl = tokenRequest(request, pForm);
        ActionErrors ae = new ActionErrors();
        try {
           if (action.equals("init")){

                AnalyticReportLogic.init(request, pForm);
//            } else if (action1!=null && action1.equals("saveSelectedStore")) {
            } else if (action.equals("saveSelectedStore")) {
                AnalyticReportLogic.changeStoreFilter(request, pForm, "STORE_SELECT");
                return mapping.findForward("success");
//            } else if (action1!=null && action1.equals("saveSelectedStoreDW")) {
            } else if (action.equals("saveSelectedStoreDW")) {
                AnalyticReportLogic.changeStoreFilter(request, pForm, "DW_STORE_SELECT");
                return mapping.findForward("success");
//            } else if (action1!=null && action1.equals("saveSelectedUserDW")) {
            } else if (action.equals("saveSelectedUserDW")) {
                AnalyticReportLogic.changeUserFilter(request, pForm, "DW_USER_SELECT");
                return mapping.findForward("success");
            } else if(action.equals("Set Filter")) {
                AnalyticReportLogic.setFilter(request, pForm);
            } else if(action.equals("Clear Filter")) {
                AnalyticReportLogic.clearFilter(request, pForm);
            } else if(action.equals("sort")) {
                AnalyticReportLogic.sort(request, pForm);
            } else if(action.equals("report")) {
            	AnalyticReportLogic.reportRequest(request, pForm);
            } else if(action.equals("protect")) {
               ae =   AnalyticReportLogic.reportProtection(request, pForm);
               if(ae.size()>0) {
                 makeMessageString(request,ae);
                 //saveErrors(request,ae);
                 return mapping.findForward("success");
               }
             } else if(action.equals("Run Report")) {
               if(tokenMatchFl) {
//               String lockedParams ="'"+  pForm.getReport().getName()+ "' with parameters: ["+ mkReportParamString(request, pForm)+"]";
                 String lockedParams = "'" + pForm.getReport().getName() + "'";
                 if (lockAction(request, Constants.lockName /*+reportId*/, lockedParams,
                                ae, Constants.lockMessage)) {
                   ae = AnalyticReportLogic.runReport(request, response, pForm);
                   unLockAction(request, Constants.lockName /*+reportId*/);
                 }
                 if (ae.size() > 0) {
                   makeMessageString(request, ae);
                   //saveErrors(request,ae);
                   return mapping.findForward("success");
                 }
                 return null;
               }
             } else if(action.equals("View Report")) {
//               String lockedParams = "'"+ pForm.getReport().getName()+ "' with parameters: [ "+ mkReportParamString(request, pForm)+" ]";
//               if(tokenMatchFl) {
                 String lockedParams = "'" + pForm.getReport().getName() + "'";
                 if (lockAction(request, Constants.lockName /*+reportId*/, lockedParams, ae, Constants.lockMessage)) {
                   ae = AnalyticReportLogic.viewReport(request, response, pForm);
                   unLockAction(request, Constants.lockName /*+reportId*/);
                 }
                 if (ae.size() > 0) {
                   makeMessageString(request, ae);
                   //saveErrors(request,ae);
                   return mapping.findForward("success");
                 }
                 if (response.getContentType().startsWith("application/pdf"))
                	 return null;
                 return mapping.findForward("success");
 //              }
             } else if(action.equals("Download Report")) {
//               String lockedParams = "'"+ pForm.getReport().getName()+ "' with parameters: [ "+ mkReportParamString(request, pForm)+" ]";
  //             if(tokenMatchFl) {
                 String lockedParams = "'" + pForm.getReport().getName() + "'";
                 if (lockAction(request, Constants.lockName /*+reportId*/, lockedParams, ae, Constants.lockMessage)) {
                   ae = AnalyticReportLogic.runReport(request, response, pForm);
                   unLockAction(request, Constants.lockName /*+reportId*/);
                 }
                 if (ae.size() > 0) {
                   makeMessageString(request, ae);
                   //saveErrors(request,ae);
                   return mapping.findForward("success");
                 }
                 return null;
  //             }
             } else if(action.equals("falseSubmit")) {
 //               return null;
                return mapping.findForward("success");
            } else if(action.equals("checkReportState")) {
              AnalyticReportLogic.getReportState(request, response, Constants.lockName, Constants.lockMessage);
              return null;
           }
             else if(action.equals("autosuggestRegion")) {
                ae = AnalyticReportLogic.autosuggestRegion(request, response, pForm);
               if(ae.size()>0) {
                 makeMessageString(request,ae);
                 //saveErrors(request,ae);
                 return mapping.findForward("success");
               }
               return null;

            }  else if(action.equals("autosuggestDSR")) {
               ae = AnalyticReportLogic.autosuggestDSR(request, response, pForm);
               if(ae.size()>0) {
                 makeMessageString(request,ae);
                 //saveErrors(request,ae);
                 return mapping.findForward("success");
               }
               return null;

            } else if (action.equals("autosuggestDWCategory")) {
                ae = AnalyticReportLogic.autosuggestDWCategory(request, response, pForm);
                if (ae.size() > 0) {
                    makeMessageString(request, ae);
                    //saveErrors(request,ae);
                    return mapping.findForward("success");
                }
                return null;

            }  else if(action.equals("prepared")) {
                String idS = request.getParameter("id");
                String field = request.getParameter("field");
                String format = request.getParameter("format");
                if (idS != null) {
                    AnalyticReportLogic.downloadPreparedReport(request, response, pForm, idS, null);
                    if (Constants.REPORT_FORMAT.HTML.equals(format)) {
                        return mapping.findForward("success");
                    } else {
                        return null;
                    }
                } else if(field!=null) {
                  AnalyticReportLogic.sortPreparedReports(request, pForm, field);
                } else {
                  AnalyticReportLogic.getPreparedReports(request, response, pForm);
                }
            } else if (action.equals("nextSheet")) {
                return mapping.findForward("success");
            } else if(action.equals("Set Archive Filter")) {
               AnalyticReportLogic.getPreparedReports(request, response, pForm);
            } else if(action.equals("Clear Archive Filter")) {
               AnalyticReportLogic.clearPreparedReportFilter(request, response, pForm);
               AnalyticReportLogic.getPreparedReports(request, response, pForm);
            } else if(action.equals("Delete Selected Results")) {
               ae =  AnalyticReportLogic.deletePreparedReports(request, response, pForm);
               if(ae.size()>0) {
                 makeMessageString(request,ae);
                 //saveErrors(request,ae);
                 return mapping.findForward("success");
               }
               AnalyticReportLogic.getPreparedReports(request, response, pForm);
            }
        }
        catch (Exception e) {
            unLockAction(request, Constants.lockName/*+report.getGenericReportId()*/);
            e.printStackTrace();
            //return (mapping.findForward("failure"));
            throw new ServletException(e.getMessage());
        }
        return (mapping.findForward("success"));
    }

    private boolean isClearRequired(String action) {
     boolean b = true;
     if ("nextSheet".equals(action) ||
         "falseSubmit".equals(action) ||
         "checkReportState".equals(action)
//         "View Report".equals(action)
         ) {
       b= false;
     }
        return b;
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

    private String mkReportParamString(HttpServletRequest request, AnalyticReportForm pForm) {
      Enumeration paramNames = request.getParameterNames();
      String rp = "";

      HashMap parmMap = new HashMap();
      GenericReportControlViewVector grcVV = pForm.getGenericControls();
      if (grcVV != null) {
        for (int j = 0; j < grcVV.size(); j++) {
          GenericReportControlView grc = (GenericReportControlView) grcVV.get(j);
          String name = grc.getName();
          String controlName = "genericControlValue["+j+"]";
          parmMap.put(controlName, name );
        }

      }

      while (paramNames.hasMoreElements()) {
        String paramName = (String) paramNames.nextElement();
        if (paramName.indexOf("genericControlValue")>=0 ){

          rp += ((rp.length()== 0) ? " ": "; ")  + parmMap.get(paramName);
          String[] paramValues = request.getParameterValues(paramName);
          if (paramValues.length == 1) {
              String paramValue = paramValues[0];
              if (paramValue.length() == 0)
                  rp += "=null";
              else
                  rp += "=" + paramValue;
          } else {
              rp += " (multi) = ";
              for (int i = 0; i < paramValues.length; i++) {
                  rp += " " + paramValues[i];
              }
          }
        }
      }
      return rp;
   }

  protected  boolean tokenRequest(HttpServletRequest request, AnalyticReportForm pForm) {
//    synchronized (this) {
      //Token  Request.  Begin
      String requestTokenSubmit = request.getParameter("requestTokenSubmit");
      int requestToken = pForm.getRequestToken();
      pForm.setRequestToken(requestToken+1);
      requestToken = pForm.getRequestToken();
      boolean tokenMatchFl = (  requestTokenSubmit==null ||
           ((requestToken -1) == Integer.parseInt(requestTokenSubmit)))?
            true:false;
      //Token  Request. End
//       }
       return tokenMatchFl;
   }

 }
