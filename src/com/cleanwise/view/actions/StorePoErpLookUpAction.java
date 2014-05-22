package com.cleanwise.view.actions;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.logic.StorePoErpLookUpLogic;
import com.cleanwise.view.utils.*;
/**
 * 
 * Implementation of <strong>Action</strong> that saves a new
 * 
 * order note or updates an existing order note.
 * 
 */
public final class StorePoErpLookUpAction extends ActionBase {
    // ------------------------------------------------------------ Public
    // Methods
    /**
     * 
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     * 
     * 
     * 
     * @param mapping
     *            The ActionMapping used to select this instance
     * @param actionForm
     *            The optional ActionForm bean for this request (if any)
     * @param request
     *            The HTTP request we are processing
     * @param response
     *            The HTTP response we are creating
     * 
     * 
     * @exception IOException
     *                if an input/output error occurs
     * @exception ServletException
     *                if a servlet exception occurs
     */
    public ActionForward performAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // Get the action and the freightTableId from the request.
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }
        MessageResources mr = getResources(request);
        String searchStr = getMessage(mr, request, "global.action.label.search");
        if (action.equals(searchStr)) {
            StorePoErpLookUpLogic.search(request, form);
        }
        return (mapping.findForward("success"));
    }
}
