
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
import com.cleanwise.view.logic.SiteMgrLogic;
import com.cleanwise.view.logic.ProfilingMgrLogic;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.SiteShoppingControlForm;
import com.cleanwise.view.i18n.ClwI18nUtil;


public final class SiteMgrNewXpedxAction extends ActionSuper {

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

        SessionTool st = new SessionTool(request);

        if ( st.checkSession() == false ) {
           return mapping.findForward("/userportal/logon");
        }

        try {
            request.removeAttribute("maxQtySuccessMessage");
            request.removeAttribute("surveySuccessMessage");
            request.setAttribute("lastAction", action);
            if (action.equals("update_site_controls") ) {
                //ActionErrors ae = SiteMgrLogic.updateShoppingControls // old method - works WRONG!!!
            	ActionErrors ae = SiteMgrLogic.updateShoppingRestrictionsNewXpedx
                    (request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                } else {
                    request.setAttribute("maxQtySuccessMessage", ClwI18nUtil.getMessage(
                        request,
                        "newxpdex.storeProfile.success.maxOrderQtyChanged", null));
                }
                request.setAttribute("gotoAnchor","true");

                if (ae.size() > 0) {
                    return (mapping.findForward("success"));
                }

            } else if (action.equals("update_surveys")) {
                //ActionErrors ae = ProfilingMgrLogic.updateSurvey(request,form);
                ActionErrors ae = ProfilingMgrLogic.updateSurveyForAdminSite(request,form,getResources(request),true);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                } else {
                    request.setAttribute("surveySuccessMessage", ClwI18nUtil.getMessage(
                        request,
                        "newxpdex.storeProfile.success.specialItemsChanged", null));
                }
            }
            ProfilingMgrLogic.init(request,form);
            ActionErrors ae = ProfilingMgrLogic.getSurveyDetail(request,form,true,true);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }

            SiteMgrLogic.init(request, form);
            SiteMgrLogic.lookupShoppingControls(request, null);
            //SiteMgrLogic.populateShoppingControlData(request,(SiteShoppingControlForm)form);
            SiteMgrLogic.populateShoppingControlDataNewXpedx(request,(SiteShoppingControlForm)form);


            return (mapping.findForward("success"));

        }
        catch (Exception e) {
            e.printStackTrace();
            ActionErrors ae = new ActionErrors();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.genericError","Uncaught Exception: "+e.getMessage()));
            saveErrors(request,ae);
            return (mapping.findForward("failure"));
        }

    }

}

