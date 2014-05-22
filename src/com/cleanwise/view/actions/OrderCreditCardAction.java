/*
 * OrderCreditCardAction.java
 *
 * Created on October 29, 2004, 3:48 PM
 */

package com.cleanwise.view.actions;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.logic.CreditCardLogic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author  bstevens
 */
public class OrderCreditCardAction extends ActionBase{
    /**
     *Require confidential when using credit cards
     */
    protected boolean isRequiresConfidentialConnection(){
        return true;
    }
    
    public ActionForward performAction(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        
        MessageResources mr = getResources(request);
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String reauthorizeStr = getMessage(mr,request,"admin.button.reauthorize");
        String resubmitStr = getMessage(mr,request,"admin.button.resubmit");
                
        ActionErrors ae = null;
        String forward = "display";
        if (action == null) action = "init";
        // Process the action
        if (action.equals("init")) {
            ae = CreditCardLogic.initOrderCreditCard(request, form);
        }else if(action.equals("create")){
            ae = CreditCardLogic.initNewOrderCreditCard(request, form);
        }else if(action.equals(saveStr)){
            ae = CreditCardLogic.saveOrderCreditCard(request, form);
        }else if(action.equals(reauthorizeStr)){
            ae = CreditCardLogic.reauthorizeOrderCreditCard(request, form);
        }else if(action.equals(resubmitStr)){
            ae = CreditCardLogic.resubmit(request, form);
        }
        if(ae!=null && !(ae.size() == 0)) {
            saveErrors(request,ae);
        }
        return (mapping.findForward(forward));
    }
}
