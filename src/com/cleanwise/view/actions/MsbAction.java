
package com.cleanwise.view.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.cleanwise.view.logic.MsbLogic;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;

/**
 * Implementation of <strong>Action</strong> that processes user related
 *  actions.
 *
 * @author     dvieira
 * @created    October 17, 2001
 */
public final class MsbAction extends ActionSuper {
    private static final Logger log = Logger.getLogger(MsbAction.class);

    // ---------------------------------------------- Public Methods

    /**
     *  Process the specified HTTP request, and create the
     *  corresponding HTTP response (or forward to another web
     *  component that will create it).  Return an
     *  <code>ActionForward</code> instance describing where and how
     *  control should be forwarded, or <code>null</code> if the
     *  response has already been completed.
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

        String action = (String) request.getParameter("action");
        MessageResources mr = getResources(request);
        String addStr = getMessage(mr,request,"button.addCustomerShipto"),
        updStr = getMessage(mr,request,"button.updateCustomerShipto"),
        rmStr = getMessage(mr,request,"button.removeCustomerShipto");

        if (action == null || action.compareTo("") == 0) {
            action = "init";
        }
        action = action.toLowerCase();

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
           return mapping.findForward("/userportal/logon");
        }

        log.info("MsbAction-action=" + action);

        try {
            if (action.equals("init")) {
                // logic here for this page
                MsbLogic.init(request, form);
                return mapping.findForward("display");
            }
            else if (action.compareToIgnoreCase(addStr) == 0) {
                ActionErrors ae = MsbLogic.addCustomerShipTo(request, form);
                if ( ae.size() > 0 ) {
                    saveErrors(request, ae);
                }
                return mapping.findForward("display");
            }
            else if (action.compareToIgnoreCase(updStr) == 0) {
                ActionErrors ae = MsbLogic.updateCustomerShipTo(request, form);
                if ( ae.size() > 0 ) {
                    saveErrors(request, ae);
                }
                return mapping.findForward("display");
            }
            else if (action.compareToIgnoreCase(rmStr) == 0) {
                ActionErrors ae = MsbLogic.removeCustomerShipTo(request, form);
                if ( ae.size() > 0 ) {
                    saveErrors(request, ae);
                }
                return mapping.findForward("home");
            }
            else if (action.equals("search")) {
                MsbLogic.findSites(request, form);
                return mapping.findForward("display");
            }
            else if (action.equals("gohome")) {
                MsbLogic.homePageInit(request, form);
                return mapping.findForward("display");
            }
            else if (action.equals("search2")) {
                MsbLogic.findSites(request, form);
                return mapping.findForward("display2");
            }
            else if (action.equals("only_site_search")) {
                MsbLogic.findOnlySites(request, form);
                return mapping.findForward("display2");
            }
            else if (action.equals("only_site_search2")) {
                MsbLogic.findOnlySites(request, form);
                return mapping.findForward("display");
            }
            else if (action.equals("sort_sites")) {
                MsbLogic.sortSites(request, form);
                return mapping.findForward("display");
            }
            else if (action.equals("x_sort_sites")) {
                MsbLogic.sortSites(request, form);
                return mapping.findForward("display2");
            }
            else if (action.equals("sort_ogsites")) {
                MsbLogic.sortOrderGuideSites(request, form);
                return mapping.findForward("display");
            }
            else if (action.equals("sort_order_guides")) {
                MsbLogic.sortOrderGuides(request, form);
                return mapping.findForward("display");
            }
            else if (action.equals("shop_order_guide")) {
                MsbLogic.getOrderGuideInfo(request, form);
                return mapping.findForward("display");

            }
            else if (action.equals("shop_og")) {
                ActionErrors ae = MsbLogic.orderGuideShop(request, form);
                if ( ae.size() > 0 ) {
                    saveErrors(request, ae);
                }
                return mapping.findForward("display");
            }
            else if (action.equals("shop_og2")) {
                ActionErrors ae = MsbLogic.setupOrder(request, form);
                if ( ae.size() > 0 ) {
                    saveErrors(request, ae);
                }
                return mapping.findForward("display");
            }
            else if(action.equals("shop_default_og")){

                if (MsbLogic.siteShop(request, form)) {


                    //return mapping.findForward("shop_for_site");

                    return mapping.findForward("shop_default_order_guide");

                }
                return mapping.findForward("shop_default_order_guide");
                //return mapping.findForward("shop_for_site");
            }
            else if (action.equals("shop_site")) {
                if (MsbLogic.siteShop(request, form)) {
                    String startPoint = (String)
                            request.getParameter("start_point");

                    if ( null == startPoint ) {
                        return mapping.findForward("shop_for_site");
                    }

                    if ( startPoint.equals("shop_quick_order") ) {
                        return mapping.findForward(startPoint);
                    }

                    return mapping.findForward("shop_for_site");
                }
                ActionErrors ae = new ActionErrors();
                ae.add("error", new ActionError
                        ("error.systemError",
                                "The Site is not ready for purchases"));
                saveErrors(request, ae);
                return mapping.findForward("error");
            }
            else if (action.equals("shop_site2")){
            	if (MsbLogic.siteShop(request, form)) {
                    String startPoint = (String)
                            request.getParameter("start_point");

                    if ( null == startPoint ) {
                        return mapping.findForward("shop_for_site2");
                    }

                    if ( startPoint.equals("shop_quick_order") ) {
                        return mapping.findForward(startPoint);
                    }

                    return mapping.findForward("shop_for_site2");
                }
                ActionErrors ae = new ActionErrors();
                ae.add("error", new ActionError
                        ("error.systemError",
                                "The Site is not ready for purchases"));
                saveErrors(request, ae);
                return mapping.findForward("error");
            }
            else if (action.equals("reorder")) {
                return mapping.findForward("reorder");
            }
            else if (action.equals("search to reorder")) {
                MsbLogic.findSites(request, form);
                return mapping.findForward("reorder");
            }
            else if (action.equals("sort_sites_reorder")) {
                MsbLogic.sortSites(request, form);
                return mapping.findForward("reorder");
            }
            else if (action.equals("read_note")) {

                MsbLogic.readNote(request, form, response);
                if("ajax".equals(request.getParameter("actionType"))){
                    return null;
                } else{
                    return mapping.findForward("display");
                }
            }
            else if (action.equals("read_note2")) {
                MsbLogic.readNote(request, response);
                return null;
            }
            else if (action.equals("shop_site_reorder")) {
                ActionErrors ae = MsbLogic.prepareReorder(request);
                if ( ae.size() > 0 ) {
                    saveErrors(request, ae);
                    return mapping.findForward("reorder");
                }
                return mapping.findForward("reorderCheckout");
            } else if (action.equalsIgnoreCase("initLocationCriteria")) {

                ActionErrors ae;
                ae = MsbLogic.initLocationCriteria(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }

                return mapping.findForward("display2");

            } else if (action.equalsIgnoreCase("changeCountry")) {

                ActionErrors ae;
                ae = MsbLogic.changeCountry(request, form, response);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }

                return null;

            } else if (action.equalsIgnoreCase("changeState")) {

                ActionErrors ae;
                ae = MsbLogic.changeState(request, form, response);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }

                return null;

            } else if (action.equalsIgnoreCase("changeSite")) {

                if (MsbLogic.changeSite(request, form)) {
                    return mapping.findForward("shop_for_site2");
                }

                ActionErrors ae = new ActionErrors();
                ae.add("error", new ActionError("error.systemError",
                        "The Site is not ready for purchases"));
                saveErrors(request, ae);
                return mapping.findForward("error");
            }

            else {
                return mapping.findForward("display");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            return mapping.findForward("error");
        }

    }

}

