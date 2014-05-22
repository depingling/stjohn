package com.cleanwise.view.actions;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.logic.ShoppingCartLogic;

import java.io.IOException;

/**
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class InventoryShoppingCartAction extends ActionSuper {

    private static final String SUCCESS = "success" ;
    private static final String PHYSICAL_INV_CONFIRMATION = "physicalConfrimation" ;
    private static final String FAILURE = "failure";
    private static final String ERROR   = "error";
    private static final String DISPLAY = "display" ;
    private static final String INIT    = "init";
    private static final String AUTO_DISTRO = "displayAutoDistro";
    private static final String VIEW_BUTTON_SECTION = "viewButtonSection" ;
    private static final String SHOW_AUTO_DISTRO = "showAutoDistro";

    // ----------------------------------------------------- Public Methods

    /**
     *  Process the specified HTTP request, and create the corresponding HTTP
     *  response (or forward to another web component that will create it).
     *  Return an <code>ActionForward</code> instance describing where and how
     *  control should be forwarded, or <code>null</code> if the response has
     *  already been completed.
     *
     *@param  mapping               The ActionMapping used to select this instance
     *@param  request               The HTTP request we are processing
     *@param  response              The HTTP response we are creating
     *@param  form                  Description of Parameter
     *@return                       Description of the Returned Value
     *@exception java.io.IOException       if an input/output error occurs
     *@exception javax.servlet.ServletException  if a servlet exception occurs
     */
    public ActionForward performSub(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
                                    throws IOException, ServletException {

        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
            action = INIT;
        }

        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward(st.getLogonMapping());
        }

        try {
            ActionForward  actionForward = callHandlerForm(action,form,request,mapping,response);
            return actionForward;
        } catch(Exception e) {
            e.printStackTrace();
            return mapping.findForward(ERROR);
        }
    }


    private ActionForward  callHandlerForm(String action,ActionForm form,HttpServletRequest request,ActionMapping mapping,HttpServletResponse response)  throws Exception
    {
        String forward_page=DISPLAY;
        if(form instanceof ShoppingCartForm) forward_page= shoppingCartAction(action,request,(ShoppingCartForm)form,response);

        if(forward_page==null){
            return null;
        }

        return mapping.findForward(forward_page);
    }

    private String shoppingCartAction(String action, HttpServletRequest request, ShoppingCartForm form, HttpServletResponse response) throws Exception {
        form.setConfirmMessage(null);
        if ("showScheduledCart".equals(action) || INIT.equals(action)) {
            ActionErrors ae = ShoppingCartLogic.inventoryInit(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        } else if ("showPhysicalCart".equals(action) || INIT.equals(action)) {
            ActionErrors ae = ShoppingCartLogic.inventoryInit(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        }else if("showAutoDistro".equals(action)){
        	ActionErrors ae = ShoppingCartLogic.inventoryInit(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        	return AUTO_DISTRO;
        } else if ("removeInvSelected".equals(action) || "removeSelected".equals(action) ) {
            ActionErrors ae = ShoppingCartLogic.removeInvSelected(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            request.setAttribute("gotoAnchor","true");
            return SUCCESS;
            //return VIEW_BUTTON_SECTION;
        } else if ("removeInvSelectedNewxpedx".equals(action)) {
            ActionErrors ae = ShoppingCartLogic.removeInvSelectedNewXpedx(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            request.setAttribute("gotoAnchor","true");
            return SUCCESS;
        } else if ("removePhysSelectedNewxpedx".equals(action)) {
            ActionErrors ae = ShoppingCartLogic.removeInvSelectedNewXpedx(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            request.setAttribute("gotoAnchor","true");
            return SUCCESS;
        } else if ("removeInvAll".equals(action)) {
            ActionErrors ae = ShoppingCartLogic.removeInvAll(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        } else if ("removePhysAll".equals(action)) {
            ActionErrors ae = ShoppingCartLogic.removeInvAll(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        } else if ("calcInvOrderQty".equals(action)) {
            ActionErrors ae = ShoppingCartLogic.calcInvOrderQty(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        } else if ("calcPhysOrderQty".equals(action)) {
            ActionErrors ae = ShoppingCartLogic.calcInvOrderQty(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        } else if ("saveInventoryCart".equals(action)) {
            ActionErrors ae = ShoppingCartLogic.updateInvShoppingCart(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        } else if ("savePhysicalCart".equals(action)) {
            ActionErrors ae = ShoppingCartLogic.updateInvShoppingCart(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        } else if ("saveJcpInventoryCart".equals(action)) {
            ActionMessages am = ShoppingCartLogic.updateJcpInvShoppingCart(request, form);
            if (am.size() > 0) {
                saveErrorsAndMessages(request, am);
            }
            return SUCCESS;
        } else if ("saveJcpPhysicalCart".equals(action)) {
            ActionMessages am = ShoppingCartLogic.updateJcpInvShoppingCart(request, form);
            if (am.size() > 0) {
                saveErrorsAndMessages(request, am);
            }
            return SUCCESS;
        } else if(action.equals("updateCart")) {
            ActionErrors ae = ShoppingCartLogic.updateInvShoppingCart(request, form);
                        
            if (ae.size() > 0) {
                saveErrors(request,ae);
                request.setAttribute("gotoAnchor","true");
                return SUCCESS;
             }
            if(ShopTool.isPhysicalCartAvailable(request)){
            	return PHYSICAL_INV_CONFIRMATION;
            }else{
            	request.setAttribute("gotoAnchor","true");
            	return SUCCESS;
            }
           
         } else if(action.equals("updateCartPhysical")){
        	 ActionErrors ae = ShoppingCartLogic.updateInvShoppingCart(request, form);
             
             if (ae.size() > 0) {
                 saveErrors(request,ae);
                 request.setAttribute("gotoAnchor","true");
                 return SUCCESS;
              }
             return PHYSICAL_INV_CONFIRMATION;
         } else if(action.equals("autoUpdateCart")) {
            ShoppingCartLogic.updateInvShoppingCartLight(request, form,response);
            request.setAttribute("gotoAnchor","true");
            return null;
         } else if(action.equals("updateAutoDistro")) {

             ActionErrors ae = ShoppingCartLogic.updateAutoDistro(request, form);
             if (ae.size() > 0) {
                saveErrors(request,ae);
             }
             request.setAttribute("gotoAnchor","true");
            return SHOW_AUTO_DISTRO;

        } else {
            ActionErrors ae = new ActionErrors();
            ae.add("error", new ActionError("error.systemError", "Unknown action: ["+Utility.encodeForHTML(action)+"]"));
            saveErrors(request, ae);
            return DISPLAY;
        }
    }

}
