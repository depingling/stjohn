/*
 * CreditCardLogic.java
 *
 * Created on October 29, 2004, 4:29 PM
 */

package com.cleanwise.view.logic;
import java.util.Date;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.APIAccess;
/**
 * Encapsulates the credit card logic as the application knows it.  The app does not
 * know how to decrypt a credit card, so mainly you are dealing with encrypting, or moving
 * encrypted data around, and viewing some of the credit card information (name, type, etc).
 * @author  bstevens
 */
public class CreditCardLogic {
    /**
     *Inits the form for viewing
     */
    public static ActionErrors initOrderCreditCard(HttpServletRequest request, ActionForm form){
        ActionErrors ae = new ActionErrors();
        OrderCreditCardForm sForm = (OrderCreditCardForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        sForm.setNewCreditCardNumber(null);
        try {
            if (null == factory) {
                throw new Exception("Without APIAccess.");
            }
            Order orderEjb = factory.getOrderAPI();
            String orderCreditCardIdS = (String)request.getParameter("orderCreditCardId");
            int orderCreditCardId = Integer.parseInt(orderCreditCardIdS);
            OrderCreditCardDescData occDesc = orderEjb.getOrderCreditCardDesc(orderCreditCardId);
            sForm.setOrderCreditCardDescData(occDesc);
            InvoiceCreditCardTransViewVector invCCTransList = 
            	orderEjb.getInvoiceCCTransList(orderCreditCardId);
            sForm.setInvCCTransList(invCCTransList);
            
        }catch(Exception e){
            e.printStackTrace();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
        }
        return ae;
    }

    /**
     *Inits the form for viewing
     */
    public static ActionErrors initNewOrderCreditCard(HttpServletRequest request, ActionForm form){
        ActionErrors ae = new ActionErrors();
        OrderCreditCardForm sForm = (OrderCreditCardForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        sForm.setNewCreditCardNumber(null);
        try {
            if (null == factory) {
                throw new Exception("Without APIAccess.");
            }
            Order orderEjb = factory.getOrderAPI();
            String orderIdS = (String)request.getParameter("orderIdS");
            int orderId = Integer.parseInt(orderIdS);
            OrderCreditCardDescData occDesc = new OrderCreditCardDescData();
            occDesc.setOrderData(orderEjb.getOrderStatus(orderId));
            OrderCreditCardData cc = OrderCreditCardData.createValue();
            cc.setOrderId(orderId);
            occDesc.setOrderCreditCardData(cc);
            sForm.setOrderCreditCardDescData(occDesc);
        }catch(Exception e){
            e.printStackTrace();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
        }
        return ae;
    }

    /**
     *saves and validates a credit card.
     */
    public static ActionErrors saveOrderCreditCard(HttpServletRequest request, ActionForm form){
       HttpSession session = request.getSession();
       APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
       CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        ActionErrors ae = new ActionErrors();
        OrderCreditCardForm sForm = (OrderCreditCardForm) form;
        OrderCreditCardData ocd = sForm.getOrderCreditCardDescData().getOrderCreditCardData();
        CreditCardUtil ccU = new CreditCardUtil(sForm.getNewCreditCardNumber(),ocd, true);
        if(!ccU.isValid()){
            if(ccU.getValidationErrorField() != null){
                ae.add(ActionErrors.GLOBAL_ERROR,new ActionError(ccU.getValidationMessageReasourceErrorMessage(),ccU.getValidationErrorField()));
            }else{
                ae.add(ActionErrors.GLOBAL_ERROR,new ActionError(ccU.getValidationMessageReasourceErrorMessage()));
            }
            return ae;
        }
        //okay valid credit card save it.

        try {
            if (null == factory) {
                throw new Exception("Without APIAccess.");
            }
            Order orderEjb = factory.getOrderAPI();
            ocd = orderEjb.saveOrderCreditCard(sForm.getNewCreditCardNumber(),ocd);
            sForm.getOrderCreditCardDescData().setOrderCreditCardData(ocd);
            sForm.setNewCreditCardNumber(null);
        }catch(Exception e){
            e.printStackTrace();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
        }
        return ae;
    }

    /**
     *revalidates a credit card.
     */
    public static ActionErrors reauthorizeOrderCreditCard(HttpServletRequest request, ActionForm form){
        ActionErrors ae = new ActionErrors();
        OrderCreditCardForm sForm = (OrderCreditCardForm) form;
        OrderCreditCardData ocd = sForm.getOrderCreditCardDescData().getOrderCreditCardData();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        ocd.setAuthStatusCd(RefCodeNames.CREDIT_CARD_AUTH_STATUS.PENDING);
        ocd.setModBy(appUser.getUser().getUserName());
        try {
            if (null == factory) {
                throw new Exception("Without APIAccess.");
            }
            Order orderEjb = factory.getOrderAPI();
            ocd = orderEjb.saveOrderCreditCard(ocd);
            sForm.getOrderCreditCardDescData().setOrderCreditCardData(ocd);
        }catch(Exception e){
            e.printStackTrace();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
        }
        return ae;
    }
    
    /*
     * Updates RefCodeNames.INVOICE_STATUS_CD of table clw_invoice_cust to ERP_RELEASED.
     */
    public static ActionErrors resubmit(HttpServletRequest request, ActionForm form){
        ActionErrors ae = new ActionErrors();
        OrderCreditCardForm sForm = (OrderCreditCardForm) form;
        int invCustID = sForm.getInvCustIDToProcess();
        
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        try {
            if (null == factory) {
                throw new Exception("Without APIAccess.");
            }
            Order orderEjb = factory.getOrderAPI();
            InvoiceCustData invCustData = orderEjb.getInvoiceCust(invCustID);
            invCustData.setModBy(appUser.getUser().getUserName());
            invCustData.setModDate(new Date());
            invCustData.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED);
            orderEjb.updateInvoiceCust(invCustData);
        }catch(Exception e){
            e.printStackTrace();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
        }
        return ae;
    }
}
