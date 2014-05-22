
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
import com.cleanwise.view.logic.StoreMgrLogic;
import com.cleanwise.view.utils.*;


/**
 *  Implementation of <strong>Action</strong> that processes the Store manager
 *  page.
 *
 *@author     tbesser
 *@created    August 23, 2001
 */
public final class StoreMgrAction extends ActionSuper {

    // ----------------------------------------------------- Public Methods

    /**
     *  Process the specified HTTP request, and create the corresponding HTTP
     *  response (or forward to another web component that will create it).
     *  Return an <code>ActionForward</code> instance describing where and how
     *  control should be forwarded, or <code>null</code> if the response has
     *  already been completed.
     *
     *@param  mapping               The ActionMapping used to select this
     *      instance
     *@param  request               The HTTP request we are processing
     *@param  response              The HTTP response we are creating
     *@param  form                  Description of Parameter
     *@return                       Description of the Returned Value
     *@exception  IOException       if an input/output error occurs
     *@exception  ServletException  if a servlet exception occurs
     */
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
        if ( st.checkSession() == false ) {
           return mapping.findForward("/userportal/logon");
        }   


        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String deleteStr = getMessage(mr,request,"global.action.label.delete");
	String searchStr = getMessage(mr,request,"global.action.label.search");
	String viewallStr = getMessage(mr,request,"admin.button.viewall");
	String createStr = getMessage(mr,request,"admin.button.create");

        // Process the action
        try {

            if (action.equals("init")) {
                StoreMgrLogic.init(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals(searchStr)) {
                StoreMgrLogic.search(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals(viewallStr)) {
                StoreMgrLogic.getAll(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals(createStr)) {
                StoreMgrLogic.addStore(request, form);
                return (mapping.findForward("storedetail"));
            }
            else if (action.equals("updatestore") ||
                    action.equals(saveStr)) {
                ActionErrors ae = StoreMgrLogic.updateStore(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("storedetail"));
            }
            else if (action.equals("storedetail")) {
                StoreMgrLogic.getDetail(request, form);
                return (mapping.findForward("storedetail"));
            }
            else if (action.equals(deleteStr)) {
                ActionErrors ae = StoreMgrLogic.delete(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
		    return (mapping.findForward("storedetail"));
                }
		return (mapping.findForward("main"));
            }
            else if (action.equals("sort")) {
                StoreMgrLogic.sort(request, form);
                return (mapping.findForward("success"));
            }
            else {
                StoreMgrLogic.init(request, form);
                return (mapping.findForward("success"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward("failure"));
        }

    }

}

