package com.cleanwise.view.actions;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.view.forms.StoreAdjustmentLedgerForm;
import com.cleanwise.view.logic.StoreAdjustmentLedgerLogic;
import com.cleanwise.view.utils.SessionTool;

/**
 * @author Alexander Chikin
 * Date: 09.10.2006
 * Time: 12:41:38
 */
public class StoreAdjustmentLedgerAction extends ActionSuper{
    private static final Logger log = Logger.getLogger(StoreAdjustmentLedgerAction.class);
   private static final String SUCCESS ="success" ;
    private static final String FAILURE = "failure";
    private static final String MAIN ="main" ;
    private static final String DETAIL="detail";
    // ----------------------------------------------------- Public Methods

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
            if (!st.checkSession() ) {
                return mapping.findForward(st.getLogonMapping());
            }


            try {
                ActionForward  actionForward= callHandlerForm(action,form,request,mapping);
                return actionForward;
            } catch(Exception e) {
                e.printStackTrace();
                return mapping.findForward(FAILURE);
            }


}

    private ActionForward callHandlerForm(String action, ActionForm form, HttpServletRequest request, ActionMapping mapping) throws Exception {

        log.info("Run action method.....");
        String forward_page="main";
        if(form instanceof StoreAdjustmentLedgerForm ) forward_page=storeAdjustmentLedgerAction(action,form,request);
        return mapping.findForward(forward_page);
    }

    private String storeAdjustmentLedgerAction(String action, ActionForm form, HttpServletRequest request) throws NamingException, APIServiceAccessException , Exception {

       if(action.equals("init"))
       {
         ActionErrors
                 ae= StoreAdjustmentLedgerLogic.init(request,form) ;
         if(ae.size()>0)
         {
          saveErrors(request,ae);
         return FAILURE;
         }
       }
       else if(action.equals("changeYear")) 
       {
        ActionErrors
                 ae= StoreAdjustmentLedgerLogic.changeBudgetYears(request,form) ;
         if(ae.size()>0)
         {
          saveErrors(request,ae);
          return FAILURE;
         }
       }
        else if(action.equals("changeBudgetPeriod")||action.equals("changeCostCenter"))
       {
        ActionErrors
                 ae= StoreAdjustmentLedgerLogic.getDetail(request,form) ;
         if(ae.size()>0)
         {
          saveErrors(request,ae);
          return FAILURE;
         }
       }
       else if(action.equals("Set Adjustment"))
       {
        ActionErrors
                 ae= StoreAdjustmentLedgerLogic.addAdjustment(request,form) ;
         if(ae.size()>0)
         {
          saveErrors(request,ae);
          return FAILURE;
         }
       }
       else if(action.equals("Save Adjustments"))
       {
        ActionErrors
                 ae= StoreAdjustmentLedgerLogic.saveAdjustments(request,form) ;
         if(ae.size()>0)
         {
          saveErrors(request,ae);
          return FAILURE;
         }
       }
        return SUCCESS;
    }
}
