/*

 * CustAcctMgtInvoiceDetailAction.java

 *

 * Created on Feb 16, 2008, 3:00 PM

 */



package com.cleanwise.view.actions;



import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.logic.CustAcctMgtInvoiceLogic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.forms.CustAcctMgtInvoiceDetailForm;
import com.cleanwise.view.logic.StoreVendorInvoiceLogic;


/**
 * @author nguschina
 */

public class CustAcctMgtInvoiceDetailAction extends ActionBase{
    public ActionForward performAction(ActionMapping mapping,ActionForm pForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String action = request.getParameter("action");
        String next = request.getParameter("next");
        String prev = request.getParameter("prev");
        ActionMessages ae = null;
        String forward = "success";
        MessageResources mr = getResources(request);

        String printStr = "print";
        String nextStr = this.getMessage(mr,request, "global.action.label.next");
        String prevStr = this.getMessage(mr,request, "global.action.label.previous");
        String update = this.getMessage(mr,request, "invoice.button.update");

        CustAcctMgtInvoiceDetailForm form = (CustAcctMgtInvoiceDetailForm) pForm;


        if(next != null){

                 ae =  CustAcctMgtInvoiceLogic.getNextInvoice(request, form);

        }else if(prev != null ){

                 ae = CustAcctMgtInvoiceLogic.getPrevInvoice(request, form);


        }else if(printStr.equals(action)){

            ae = CustAcctMgtInvoiceLogic.printInvoiceDist(response, request, form);
            //action should not be forwarded as we are directly writing out to throws stream when printing pdfs.
            if (ae == null || (ae.size() == 0)) {
              return null;
            }
        }else if("view".equals(action)){

              ae = CustAcctMgtInvoiceLogic.fetchInvoiceDistDetail(request, form, 0);
        } else if (action.equals("sort")) {
              CustAcctMgtInvoiceLogic.sortItems(request, form);
//              return (mapping.findForward("display"));

              if (ae.size() > 0) {
                saveErrors(request, ae);
              }
              else {
                return null;
              }

        } else if (action.startsWith("pdfPrint") ||
                     action.startsWith("excelPrint")) {

              ae = CustAcctMgtInvoiceLogic.printDetail(response, request, form,
                  action, getResources(request));
        } else if (action.equals(update)) {
            ae = CustAcctMgtInvoiceLogic.updateInvoice(request, form);
        }
         if (ae != null && ! (ae.size() == 0)) {

            saveErrors(request,ae);

        }

        return (mapping.findForward(forward));

    }

}

