/*
 * BlanketPoNumAction.java
 *
 * Created on February 9, 2005, 11:44 AM
 */

package com.cleanwise.view.actions;

import com.cleanwise.view.logic.StoreVendorInvoiceLogic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
/**
 *Handles the view logic around distributor invoices
 * @author bstevens
 */
public class StoreVendorInvoiceSearchAction extends ActionBase{


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
        String searchPOsStr = getMessage(mr,request,"admin.button.search.pos");
        String searchInvoicesStr = getMessage(mr,request,"admin.button.search.invoices");
        String searchInvoicesExcpStr = getMessage(mr,request,"admin.button.search.invoices.exception");

        if(searchStr.equals(action) || searchInvoicesStr.equals(action) || searchPOsStr.equals(action)){
            ae = StoreVendorInvoiceLogic.searchInvoiceDist(request,form);
            if(searchPOsStr.equals(action)){
            	request.setAttribute("poDisplay","true");
            }else{
            	request.setAttribute("poDisplay","false");
            }
        }else if(searchExcpStr.equals(action) || searchInvoicesExcpStr.equals(action)){
            ae = StoreVendorInvoiceLogic.searchInvoiceDistException(request,form);
        }else if(printListStr.equals(action)){
            ae = StoreVendorInvoiceLogic.printInvoiceDistList(response, request, form);
            //action should not be forwarded as we are directly writing out to throws stream when printing pdfs.
            if(ae==null || (ae.size() == 0)) {
                return null;
            }
        }else{
            StoreVendorInvoiceLogic.init(request,form);
        }
        if(ae!=null && !(ae.size() == 0)) {
            saveErrors(request,ae);
        }
        return (mapping.findForward(forward));
    }

}
