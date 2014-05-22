
package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.logic.DistMgrLogic;
import com.cleanwise.view.utils.*;

/**
 *
 *@author     dvieira
 *@created    August 9, 2001
 */
public final class DistMgrLocateShipFromAction extends ActionSuper {

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
	action.trim();

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
	    return mapping.findForward("/userportal/logon");
        }   

	try {
	    if (action.equals("sortShipFrom")) {
		DistMgrLogic.sortShipFrom(request, form);
	    } 
	    else if (! action.equals("init")) {
		DistMgrLogic.locateShipFrom(request, form, action);
	    }
	}
	catch (Exception e) {
            e.printStackTrace();
	    System.err.println("DistMgrLocateShipFromAction: " + e);
	}

	return (mapping.findForward("success"));

    }

}

