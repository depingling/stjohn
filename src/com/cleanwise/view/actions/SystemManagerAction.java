
package com.cleanwise.view.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.view.logic.SystemManagerLogic;
import com.cleanwise.view.utils.SessionTool;

public final class SystemManagerAction extends ActionSuper {
    private static final Logger log = Logger.getLogger(SystemManagerAction.class);

    public ActionForward performSub(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }
        log.info("action = " + action);

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if (st.checkSession() == false ) {
           return mapping.findForward("/userportal/logon");
        }   
        
        // Process the action
        try {
            ActionErrors ae = null;
            if (action.equals("view")) {
                ae = SystemManagerLogic.getFailedData(request);
            }
            else if (action.equals("freeMemory")) {
                SystemManagerLogic.freeMemory();
                ae = SystemManagerLogic.getFailedData(request);
            }
            else if (action.equals("verifyDao")) {
                ae = SystemManagerLogic.verifyDaoLayer(request);
	        }
            else if (action.equals("refresh_content")){
	            ae = SystemManagerLogic.refreshContent(request);
	        } 
            
            if (ae != null && ae.size() > 0) {
            	saveErrors(request, ae);
            }
            
            if (action.equalsIgnoreCase("browseDatabase")) {
            	return mapping.findForward("browseDatabase");
            }
            else {
            	return mapping.findForward("success");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("failure");
        }
    }
}

