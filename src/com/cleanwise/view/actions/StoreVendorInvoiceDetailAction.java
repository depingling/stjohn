/*
 * StoreVendorInvoiceDetailAction.java
 *
 * Created on July 6, 2005, 3:00 PM
 */

package com.cleanwise.view.actions;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.forms.StoreVendorInvoiceDetailForm;
import com.cleanwise.view.logic.StoreVendorInvoiceLogic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author bstevens
 */
public class StoreVendorInvoiceDetailAction extends ActionBase{

    public ActionForward performAction(ActionMapping mapping,ActionForm pForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        ActionMessages ae = null;
        String forward = "success";
        MessageResources mr = getResources(request);
        String submitStr = this.getMessage(mr, request, "admin.button.submitUpdates");
        String backStr = this.getMessage(mr, request, "admin.button.back");
        String newPoStr = this.getMessage(mr,request, "invoice.button.lookupPo");
        String addLineStr = this.getMessage(mr,request, "invoice.button.addLine");
        String reassignStr = this.getMessage(mr,request, "invoice.button.assignNewPo");
        String addNoteStr = this.getMessage(mr,request, "admin.button.addNote");
        String manResolvStr = this.getMessage(mr,request, "invoice.button.manuallyResolved");
        String rejectStr = this.getMessage(mr,request, "invoice.button.reject");
        String resetStatusStr = this.getMessage(mr,request, "invoice.button.resetStatus");
        String recalcStr = this.getMessage(mr,request, "button.recalculate");

        String nextStr = this.getMessage(mr,request, "global.action.label.next");
        String prevStr = this.getMessage(mr,request, "global.action.label.previous");

        String printStr = "print";
        String reassignLineStr = "reassignLine";
        String delLineStr = "delLine";
        StoreVendorInvoiceDetailForm form = (StoreVendorInvoiceDetailForm) pForm;
        if(Utility.isSet(form.getActionOveride())){
            action = form.getActionOveride();
            form.setActionOveride(null);
        }
        if("view".equals(action)){
            ae = StoreVendorInvoiceLogic.fetchInvoiceDistDetail(request, form, 0);
            StoreVendorInvoiceLogic.updateCalculatedSalesTax(ae,request, form);
            StoreVendorInvoiceLogic.updateCalculatedTotal(ae,request,form);
            StoreVendorInvoiceLogic.setPoScreenAttr(form,false);
        }else if("create".equals(action)){
            ae = StoreVendorInvoiceLogic.initNewInvoiceDistFromId(request, form);
            StoreVendorInvoiceLogic.updateCalculatedSalesTax(ae,request, form);
            StoreVendorInvoiceLogic.updateCalculatedTotal(ae,request,form);
            StoreVendorInvoiceLogic.setPoScreenAttr(form,true);
        }else if(submitStr.equals(action)){
            //validation only setup for the submiting of updates
            ae = form.validate(mapping, request);
            if(ae == null || ae.size() == 0){
                ae = StoreVendorInvoiceLogic.saveInvoiceDist(request, form);
                //save the note
                if(ae == null || ae.size() == 0){
                    ae = StoreVendorInvoiceLogic.addInvoiceDistNote(request, form, false);
                }
                StoreVendorInvoiceLogic.updateCalculatedSalesTax(ae,request, form);
                StoreVendorInvoiceLogic.updateCalculatedTotal(ae,request,form);
            }
        }else if(newPoStr.equals(action)){
            ae = StoreVendorInvoiceLogic.initNewInvoiceDistFromFormPo(request, form);
            StoreVendorInvoiceLogic.updateCalculatedSalesTax(ae,request, form);
        }else if(addLineStr.equals(action)){
            ae = StoreVendorInvoiceLogic.addLineToInvoiceDist(request, form);
        }else if(delLineStr.equals(action)){
            ae = StoreVendorInvoiceLogic.deleteLineFromInvoiceDist(request, form);
            StoreVendorInvoiceLogic.updateCalculatedSalesTax(ae,request, form);
        }else if(reassignLineStr.equals(action)){
            StoreVendorInvoiceLogic.refreshObjectsInvoiceDistLeaveUserEntry(request, form);
        }else if(backStr.equals(action)){
            forward = "back";
        }else if(reassignStr.equals(action)){
            ae = StoreVendorInvoiceLogic.reAssignInvoiceDistToPo(request, form);
            StoreVendorInvoiceLogic.updateCalculatedSalesTax(ae,request, form);
            StoreVendorInvoiceLogic.updateCalculatedTotal(ae,request,form);
        }else if(addNoteStr.equals(action)){
            ae = StoreVendorInvoiceLogic.addInvoiceDistNote(request, form, true);
        }else if(rejectStr.equals(action)){
            ae = StoreVendorInvoiceLogic.rejectInvoiceDist(request, form);
            if(ae == null || ae.size() == 0){
                ae = StoreVendorInvoiceLogic.fetchInvoiceDistDetail(request, form, form.getInvoice().getInvoiceDist().getInvoiceDistId());
            }
        }else if(resetStatusStr.equals(action)){
        	ae = StoreVendorInvoiceLogic.updateInvoiceDistToDistShipped(request, form);
        }else if(manResolvStr.equals(action)){
            ae = StoreVendorInvoiceLogic.updateInvoiceDistToManuallyResolved(request, form);
        }else if(printStr.equals(action)){
            ae = StoreVendorInvoiceLogic.printInvoiceDist(response, request, form);
            //action should not be forwarded as we are directly writing out to throws stream when printing pdfs.
            if(ae==null || (ae.size() == 0)) {
                return null;
            }
        }else if(recalcStr.equals(action)){
        	StoreVendorInvoiceLogic.refreshObjectsInvoiceDistLeaveUserEntry(request, form);
        	StoreVendorInvoiceLogic.updateCalculatedTotal(ae,request, form);
        	StoreVendorInvoiceLogic.updateCalculatedSalesTax(ae,request, form);
        }else if(nextStr.equals(action)){
        	StoreVendorInvoiceLogic.getNextInvoice(request, form);
        }else if(prevStr.equals(action)){
        	StoreVendorInvoiceLogic.getPrevInvoice(request, form);
        }else{
            StoreVendorInvoiceLogic.refreshObjectsInvoiceDist(request, form);
        }
        if(ae!=null && !(ae.size() == 0)) {
            saveErrors(request,ae);
        }
        return (mapping.findForward(forward));
    }
}
