
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.logic.UserMgrLocateLogic;
import com.cleanwise.view.utils.*;
import javax.servlet.http.HttpSession;


/**
 * Implementation of <strong>Action</strong> that processes the
 * store manager page.
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public final class UserMgrLocateAction extends ActionSuper {
    
    
    // ------------------------------------------------------------ Public Methods
    
    
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form an <code>ActionForm</code> value
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @return an <code>ActionForward</code> value
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    public ActionForward performSub(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {
        

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
           return mapping.findForward("/userportal/logon");
        }        
        
        // Determine the user manager action to be performed
        String action = request.getParameter("action");
        String sortBy = request.getParameter("sortBy");
        if(Utility.isSet(sortBy)){
        	action = "sort";
        }
       
        if (action == null) {
	    action = "init";
        }
        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.
	String searchStr = getMessage(mr,request,"global.action.label.search");
	String viewallStr = getMessage(mr,request,"admin.button.viewall");

        // Process the action
        try {
            
            if (action.equals("init")) {
		UserMgrLocateLogic.init(request, form);
                return (mapping.findForward("success"));
            }
            
            else if (action.equals(searchStr)) {
                // general search
		UserMgrLocateLogic.search(request, form);
                return (mapping.findForward("success"));
            }
                        
            else if (action.equals(viewallStr)) {
		UserMgrLocateLogic.getAll(request, form);
                return (mapping.findForward("success"));
            }
            
            else if (action.equals("sort")) {
		UserMgrLocateLogic.sort(request, form);
                return (mapping.findForward("success"));
            }
            
            else {
                UserMgrLocateLogic.init(request, form);
                return (mapping.findForward("success"));
            }
            
        }
        
        catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward("failure"));
        }
        
    }
    
    
}
