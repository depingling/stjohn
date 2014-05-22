/*
 * AggregateItemMgrAction.java
 *
 * Created on December 1, 2003, 5:03 PM
 */

package com.cleanwise.view.actions;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.logic.AggregateItemLogic;
import com.cleanwise.view.forms.AggregateItemMgrForm;
/**
 *
 * @author  bstevens
 */
public class AggregateItemMgrAction extends ActionSuper {
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
        if (action == null) action = "init";
        
        
        
        MessageResources mr = getResources(request);
        String catUpdStr = getMessage(mr,request,"admin.button.preformCategoryMod");
        String updateStr = getMessage(mr,request,"admin.button.addItems");
        String removeStr = getMessage(mr,request,"admin.button.removeSelected");
        String backStr = getMessage(mr,request,"admin.button.back");
        String fetchDetailStr = "fetch";
            
        String failForward = "failure";
        String successForward = "success";
        
        // Process the action
        try {
            ActionErrors errors = new ActionErrors();
            
            if(action.equals(updateStr)){
                errors = AggregateItemLogic.updateAggregateItems(request, form);
            }else if(action.equals(catUpdStr)){
                errors = AggregateItemLogic.preformCategoryModification(request, form);
            }else if(action.equals(removeStr)){
                errors = AggregateItemLogic.removeAggregateItems(request, form);
            }else if(action.equals(fetchDetailStr)){
                errors = AggregateItemLogic.fetchAggregateItems(request, form);
            }else if(action.equals(backStr)){
                ActionForward  af = mapping.findForward("back");
                return (af);
            }
            
            if(errors.size() > 0){
                saveErrors(request,errors);
                return (mapping.findForward(failForward));
            }else{
                return (mapping.findForward(successForward));
            }
        }catch (Exception e) {
            request.setAttribute("errorobject", e);
            ActionErrors ae = new ActionErrors();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.genericError",e.getMessage()));
            saveErrors(request, ae);
            e.printStackTrace();
            return (mapping.findForward(failForward));
        }
    }
}
