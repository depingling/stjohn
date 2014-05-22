/*
 * GroupMgrAction.java
 *
 * Created on January 16, 2003, 3:06 PM
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
import com.cleanwise.view.logic.GroupMgrLogic;
import org.apache.struts.util.MessageResources;
/**
 *
 * @author  bstevens
 */
public class GroupMgrConfigAction extends ActionSuper {
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
        
        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward("/userportal/logon");
        }
        
        MessageResources mr = getResources(request);
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String updateStr = getMessage(mr,request,"admin.button.submitUpdates");
        String successForward = "success";
        String failForward = "failure";
        
        // Process the action
        try {
            ActionErrors errors = new ActionErrors();
            if(action.equals(updateStr)){
                errors = GroupMgrLogic.updateGroupConfig(request, form);
            }else if(action.equals(searchStr)){
                errors = GroupMgrLogic.searchGroupConfig(request, form);
            }
            GroupMgrLogic.init(response, request, form);
            if(errors.size() > 0){
                saveErrors(request,errors);
                return (mapping.findForward(successForward));
            }else{
                return (mapping.findForward(failForward));
            }
        }catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward(failForward));
        }
        
    }
    
}
