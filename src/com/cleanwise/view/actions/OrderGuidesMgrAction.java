
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.OrderGuidesMgrLogic;
import com.cleanwise.view.utils.*;

/**
* Implementation of <strong>Action</strong> that processes the
* OrderGuides manager page.
*/
public final class OrderGuidesMgrAction extends ActionSuper {
    
    
    // -------------------------------------------------- Public Methods
    
    
    /**
     * Process the specified HTTP request, and create the corresponding
     * HTTP response (or forward to another web component that will
     * create it).  Return an <code>ActionForward</code> instance
     * describing where and how control should be forwarded, or
     * <code>null</code> if the response has already been completed.
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
        
        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
	String searchStr = getMessage(mr,request,"global.action.label.search");
	String viewallStr = getMessage(mr,request,"admin.button.viewall");
	String createStr = getMessage(mr,request,"admin.button.create");
        String deleteStr = getMessage(mr,request,"global.action.label.delete");
        String removeStr = getMessage(mr,request,"admin.button.remove");
        String updateQtyStr = getMessage(mr,request,"admin.button.updateQty");
        String findItemsStr = getMessage(mr,request,"admin.button.findItems");
        String addItemsStr = getMessage(mr,request,"admin.button.chose");
        String viewbycatalogStr = getMessage(mr,request,"admin.button.viewbycatalog");

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
           return mapping.findForward("/userportal/logon");
        }   

        // Process the action
        try {
            if (action.equals("init")) {
                OrderGuidesMgrLogic.init(request, form);
                OrderGuidesMgrLogic.getDetail(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals(searchStr)) {
                OrderGuidesMgrLogic.search(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals("detail")) {
                OrderGuidesMgrLogic.getDetail(request, form);
                return (mapping.findForward("detail"));
            }
            else if (action.equals(createStr)) {
                OrderGuidesMgrLogic.addOrderGuide(request, form);
                return (mapping.findForward("new"));
            }
	    else if (action.equals("createorderguide")) {
                ActionErrors ae = OrderGuidesMgrLogic.checkData
                    (request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
                OrderGuidesMgrLogic.create(request, form);
                return mapping.findForward("detail");
            }
            else if (action.equals(saveStr)) {
                ActionErrors ae = OrderGuidesMgrLogic.update
                    (request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("detail"));
            }
            else if (action.equals(viewallStr)) {
                OrderGuidesMgrLogic.getAll(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals(deleteStr)) {
                OrderGuidesMgrLogic.delete(request, form);
                return (mapping.findForward("main"));
            }

            else if (action.equals(removeStr)) {
                OrderGuidesMgrLogic.removeItems(request, form);
                return (mapping.findForward("detail"));
            }

            else if (action.equals(findItemsStr)) {
                OrderGuidesMgrLogic.findItems(request, form);
                return (mapping.findForward("finditems"));
            }

            else if (action.equals(addItemsStr)) {
                OrderGuidesMgrLogic.addItems(request, form);
                return (mapping.findForward("detail"));
            }

            else if (action.equals(updateQtyStr)) {
                OrderGuidesMgrLogic.updateItems(request, form);
                return (mapping.findForward("detail"));
            }

            else if (action.equals("sort")) {
                OrderGuidesMgrLogic.sort(request, form);
                return (mapping.findForward("success"));
            }

            else if (action.equals("sortitems")) {
                OrderGuidesMgrLogic.sortItems(request, form);
                return (mapping.findForward("success"));
            }

            else if (action.equals("sortfinditems")) {
                OrderGuidesMgrLogic.sortItems(request, form);
                return (mapping.findForward("finditems"));
            }

            else if (action.equals(viewbycatalogStr)) {
                OrderGuidesMgrLogic.getDetail(request, form);
                return (mapping.findForward("detail"));
            }

            else if (action.equals("viewbycontract")) {
                OrderGuidesMgrLogic.getDetailByContract(request, form);
                return (mapping.findForward("detail"));
            }
            
            else {
                OrderGuidesMgrLogic.init(request, form);
                return (mapping.findForward("success"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward("failure"));
        }
        
    }
    
}
