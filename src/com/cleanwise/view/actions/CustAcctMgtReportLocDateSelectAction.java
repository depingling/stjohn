
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.XMLSerializer;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.forms.CustAcctMgtReportForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.CustAcctMgtReportLogic;
import com.cleanwise.view.utils.*;
import org.w3c.dom.*;



/**
 * Implementation of <strong>Action</strong> that processes the
 * OrderOp manager page.
 */
public final class CustAcctMgtReportLocDateSelectAction extends ActionSuper {

    public final static String className = "CustAcctMgtReportLocDateSelectAction";
    // ----------------------------------------------------- Public Methods


    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
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

        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        String command = request.getParameter("command");
        if(action==null) {
            if(command!=null) {
                action = command;
            } else {
                action = "init";
            }
        }

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if (!st.checkSession()) {
            return mapping.findForward("/userportal/logon");
        }

        MessageResources mr = getResources(request);
        String submitStr = getMessage(mr,request,"global.action.label.submit");
        String dateFmt = ClwI18nUtil.getDatePattern(request);
        ReportRequest rr = new ReportRequest(st.getUserData(),mr,dateFmt);

        // Process the action
        try {

            if (action.equals("init")) {
                ActionErrors ae = CustAcctMgtReportLogic.initLocDateSelect(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                else {
                    return (mapping.findForward("success"));
                }
            }
            else if (action.equals("next")) {
                ActionErrors ae = CustAcctMgtReportLogic.checkLocationSelect(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                else {
                    String target = CustAcctMgtReportLogic.getNextMappingOfLocDateSelect(request, form);
                    return (mapping.findForward(target));
                }
            }else if("DownloadToExcel".equals(action)){
            	String reportTypeCd = request.getParameter("reportTypeCd");

      		  if(form instanceof CustAcctMgtReportForm){
      			  ((CustAcctMgtReportForm)form).setReportTypeCd(reportTypeCd);
      		  }
			  String dateF = ClwI18nUtil.getDatePattern(request);
			  ReportRequest rr2 = new ReportRequest(st.getUserData(),mr,dateF);
			  ActionErrors ae = CustAcctMgtReportLogic.getCustomerReport
			  			(request, response, form, rr2);
			  if (ae.size() > 0) {
				  saveErrors(request, ae);
				  return (mapping.findForward("display"));
			  }
			  else {
				  if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE.equals(reportTypeCd) || action.equals("get_delivery_sched") ) {
					  return null;
				  }
				  //return (mapping.findForward("success"));
				  CustAcctMgtReportLogic.downloadGenericReport(request,response,form, rr2);
				  return null;
			  }

		  }else if ("DownloadToPDF".equals(action)) {
			  String reportTypeCd = request.getParameter("reportTypeCd");

			  if(form instanceof CustAcctMgtReportForm){
				  ((CustAcctMgtReportForm)form).setReportTypeCd(reportTypeCd);
			  }
			  String dateF = ClwI18nUtil.getDatePattern(request);
			  ReportRequest rr1 = new ReportRequest(st.getUserData(),mr,dateF);
			  ActionErrors ae = CustAcctMgtReportLogic.getCustomerReport
			  (request, response, form, rr1);
			  if (ae.size() > 0) {
				  saveErrors(request, ae);
				  return (mapping.findForward("display"));
			  }else {
				  if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE.equals(reportTypeCd) || action.equals("get_delivery_sched") ) {
					  return null;
				  }
				  //return (mapping.findForward("success"));
				  CustAcctMgtReportLogic.downloadPDFReport(request,response,form, rr1);
				  return null;
			  }

		  }
            else if (action.equals("Locate Account")) {
                return (mapping.findForward("success"));
            }
            else if (action.equals("Locate Site")) {
                return (mapping.findForward("success"));
            }
            else if (action.equals("Locate Distributor")) {
                return (mapping.findForward("success"));
            }
            else if (action.equals("Locate Item")) {
                return (mapping.findForward("success"));
            }
            else if (action.equals("Cancel")) {
                return (mapping.findForward("success"));
            }
            else if (action.equals("result")) {
                return (mapping.findForward(action));
            }
            else if (action.equals("Return Selected")) {
                ActionErrors ae = CustAcctMgtReportLogic.returnSelected(request, form);
                if(ae.size()>0) {
                    saveErrors(request,ae);
                    return mapping.findForward("failure");
                }
                return (mapping.findForward("success"));
            }
            else if ("Clear Sites".equals(action)) {
                ActionErrors ae = CustAcctMgtReportLogic.clearSites(request, form);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return mapping.findForward("failure");
                }
                return (mapping.findForward("success"));
            }
            else if ("Clear Distributors".equals(action)) {
                ActionErrors ae = CustAcctMgtReportLogic.clearDistributors(request, form);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return mapping.findForward("failure");
                }
                return (mapping.findForward("success"));
            }
            else if ("Clear Accounts".equals(action)) {
               ActionErrors ae = CustAcctMgtReportLogic.clearAccounts(request, form);
               if(ae.size()>0) {
                   saveErrors(request, ae);
                   return mapping.findForward("failure");
               }
               return (mapping.findForward("success"));
           }

            else if ("Clear Items".equals(action)) {
               ActionErrors ae = CustAcctMgtReportLogic.clearItems(request, form);
               if(ae.size()>0) {
                   saveErrors(request, ae);
                   return mapping.findForward("failure");
               }
               return (mapping.findForward("success"));
           }
            else  if (action.equals("generateReport") ) {

                ActionErrors ae = CustAcctMgtReportLogic.getCustomerReport(request, response, form, rr);

                response.setContentType("application/xml");
                response.setHeader("Cache-Control", "no-cache");

               // response.getWriter().write(CustAcctMgtReportLogic.generateReportXmlResponse(request,ae,form));
                Element rootEl = CustAcctMgtReportLogic.generateReportXmlResponse(request,ae,form);

                OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                serializer.serialize(rootEl);

                return null;

            } else if (action.equals("goToResult")) {
                ActionErrors ae = CustAcctMgtReportLogic.checkLocationSelect(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                else {
                    String target = CustAcctMgtReportLogic.getNextMappingOfLocDateSelect(request, form);
                    if("result".equals(target)) {
                        target = "goToResult";
                    }
                    return (mapping.findForward(target));
                }
            }
            else {
                CustAcctMgtReportLogic.init(request, form);
                return (mapping.findForward("success"));
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward("failure"));
        }
    }

}
