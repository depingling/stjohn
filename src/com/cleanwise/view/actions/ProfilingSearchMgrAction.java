/*
 * ProfilingAction.java
 *
 * Created on May 15, 2003, 3:52 PM
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
import com.cleanwise.view.logic.ProfilingMgrLogic;
/**
 *
 * @author  bstevens
 */
public class ProfilingSearchMgrAction extends ActionSuper {
    
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
        String initStr = "init";
        String searchStr = getResources(request).getMessage("global.action.label.search");
        String createStr = getResources(request).getMessage("admin.button.create");
        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        if (action == null) action = initStr;
        
        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward("/userportal/logon");
        }
        
        // Process the action
        try {
            ActionErrors ae = null;
            if (action.equals(initStr)) {
                ProfilingMgrLogic.init(request, form);
            }else if (action.equals(searchStr)) {
                ae = ProfilingMgrLogic.search(request, form);
            }else if (action.equals(createStr)) {
                return (mapping.findForward("create"));
            }else {
                ProfilingMgrLogic.init(request, form);
            }
            
            if (ae!=null && ae.size() > 0) {
                saveErrors(request, ae);
                return (mapping.findForward("failure"));
            }
            return (mapping.findForward("success"));
        }catch (Exception e) {
            e.printStackTrace();
            ActionErrors ae = new ActionErrors();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
            saveErrors(request, ae);
            return (mapping.findForward("failure"));
        }
    }
}
