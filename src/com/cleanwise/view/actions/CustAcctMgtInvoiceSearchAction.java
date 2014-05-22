/*
 * CustAcctMgtInvoiceSearchAction.java
 *
 * Created on February 16, 2008, 11:44 AM
 */

package com.cleanwise.view.actions;

import com.cleanwise.view.logic.CustAcctMgtInvoiceLogic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
/**
 *Handles the view logic around distributor invoices
 * @author nguschina
 */
public class CustAcctMgtInvoiceSearchAction extends ActionBase{


    public ActionForward performAction(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        ActionMessages ae = null;
        String forward = "success";
        MessageResources mr = getResources(request);
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String searchExcpStr = getMessage(mr,request,"admin.button.search.exception");
        String printListStr = "printList";

        if(searchStr.equals(action)){
            ae = CustAcctMgtInvoiceLogic.searchInvoiceDist(request,form);
        }else if(printListStr.equals(action)){
            ae = CustAcctMgtInvoiceLogic.printInvoiceDistList(response, request, form);
            //action should not be forwarded as we are directly writing out to throws stream when printing pdfs.
            if(ae==null || (ae.size() == 0)) {
                return null;
            }
        }else{
            CustAcctMgtInvoiceLogic.init(request,form);
        }
        if(ae!=null && !(ae.size() == 0)) {
            saveErrors(request,ae);
        }
        return (mapping.findForward(forward));
    }

}
