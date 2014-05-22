
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.forms.CustAcctMgtReportForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.CustAcctMgtReportLogic;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.GenericReportResultView;



/**
 * Implementation of <strong>Action</strong> that processes the
 * OrderOp manager page.
 */
public final class CustAcctMgtReportResultAction extends ActionSuper {


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

      // Determine the store manager action to be performed
      String action = request.getParameter("action");
      if (action == null) {
		  action = "init";
      }
      // Is there a currently logged on user?
      SessionTool st = new SessionTool(request);

      if ( st.checkSession() == false ) {
         return mapping.findForward("/userportal/logon");
      }

      MessageResources mr = getResources(request);
      String submitStr = getMessage(mr,request,"global.action.label.submit");
      String dateFmt = ClwI18nUtil.getDatePattern(request);
      ReportRequest rr = new ReportRequest(st.getUserData(),mr,dateFmt);


      // Process the action
      try {
          CustAcctMgtReportForm sForm = (CustAcctMgtReportForm) form;
          String reportTypeCd = sForm.getReportTypeCd();

	  if (action.equals("init") || action.equals("get_delivery_sched")) {
              ActionErrors ae = new ActionErrors();
              String lockedParams = "'" + reportTypeCd + "'";
              if (lockAction(request, Constants.lockName /*+reportId*/, lockedParams, ae, Constants.lockMessage)) {
                ae = CustAcctMgtReportLogic.getCustomerReport(request, response, form, rr);
                unLockAction(request, Constants.lockName /*+reportId*/);
             }
              if (ae.size() > 0) {
                  saveErrors(request, ae);
                  return (mapping.findForward("failure2"));
              }
              else {
                 if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.DELIVERY_SCHEDULE.equals(reportTypeCd) || action.equals("get_delivery_sched") ) {
                      return null;
                 }
                 
                 GenericReportResultView reportResult = (GenericReportResultView) sForm.getReportResults().get(0);
             	 if (reportResult.getName().equals(RefCodeNames.CUSTOMER_REPORT_TYPE_CD.INVOICE_LISTING)){ 
             		CustAcctMgtReportLogic.downloadInvoicePDF(request, response, sForm.getReportResults(), sForm.getReportControls());
             		return null;
             	 }
             	 
                 return (mapping.findForward("success"));
              }
	  }
         else  if (action.equals("result") ) {
            ActionErrors ae = new ActionErrors();
            String lockedParams = "'" + reportTypeCd + "'";
            if (lockAction(request, Constants.lockName /*+reportId*/, lockedParams, ae, Constants.lockMessage)) {
              ae = CustAcctMgtReportLogic.getCustomerReport(request, response, form, rr);
              unLockAction(request, Constants.lockName /*+reportId*/);
            }
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return (mapping.findForward("display"));
            }else {
                return (mapping.findForward("success"));
            }
          }
          else  if (action.equals("Download Report") ) {
	      // send the message resource info
	      CustAcctMgtReportLogic.downloadGenericReport
		  (request,response,form, rr);
	      return null;
          } else if ("Download to Excel".equals(action)) {
              CustAcctMgtReportLogic.downloadGenericReport(request,response,form, rr);
              return null;
          } else if ("Download to PDF".equals(action)) {
              CustAcctMgtReportLogic.downloadPDFReport(request,response,form, rr);
              return null;
          }

          // Old mechanism to export the current report to xcel.
          else  if (action.indexOf("xport") == 1 ) {
            ActionErrors ae = CustAcctMgtReportLogic.exportOrderReport(request, response, (CustAcctMgtReportForm)form);
            if (ae.size() > 0) {
              saveErrors(request, ae);
              return (mapping.findForward("display"));
            }
            else {
              return null;
            }
         }
	  else {
              //try generic report page select
              ActionErrors ae = CustAcctMgtReportLogic.getGenericReportPage(request,form,action);
              if (ae.size() > 0) {
                  saveErrors(request, ae);
                  return (mapping.findForward("display"));
              }
              return (mapping.findForward("display"));
	  }
      }
      catch (Exception e) {
	  e.printStackTrace();
	  return (mapping.findForward("failure"));
      }
  }

}
