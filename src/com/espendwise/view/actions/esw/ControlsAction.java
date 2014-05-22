/**
 * Title: ControlsAction
 * Description: This is the Struts Action class handling the ESW controls functionality.
 */
package com.espendwise.view.actions.esw;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.ControlsForm;
import com.espendwise.view.logic.esw.ControlsLogic;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class ControlsAction extends EswAction {
    private static final Logger logger = Logger.getLogger(ControlsAction.class);
    private static final String MAPPING_CONTROLS_SHOW_PRODUCT_LIMITS = "controlsShowProductLimits";
    private static final String MAPPING_CONTROLS_EDIT_PRODUCT_LIMITS = "controlsEditProductLimits";
    private static final String MAPPING_CONTROLS_SHOW_PAR_VALUES = "controlsShowParValues";

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     * 
     * @param mapping
     *            the ActionMapping used to select this instance.
     * @param form
     *            the ActionForm containing the data.
     * @param request
     *            the HTTP request we are processing.
     * @param response
     *            the HTTP response we are creating.
     * @return an ActionForward describing the component that should receive
     *         control.
     */
    public ActionForward performAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        // If there isn't a currently logged on user then go to the login page
        if (!new SessionTool(request).checkSession()) {
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
        }
        ActionForward returnValue = null;
        ControlsForm theForm = (ControlsForm) form;
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        CleanwiseUser user = ShopTool.getCurrentUser(request);
        boolean canViewParValues = user
                .isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.VIEW_SITE_PAR_VALUES);
        boolean canEditParValues = user
                .isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_PAR_VALUES)
                && canViewParValues;
        boolean canViewProdLimits = user
                .isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.VIEW_SHOPPING_CONTROLS);
        boolean canEditProdLimits = user
                .isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_SHOPPING_CONTROLS)
                && canViewProdLimits;

        sessionDataUtil.setSelectedSubTab(Constants.TAB_CONTROLS);
        sessionDataUtil.setPreviousShoppingModuleTab(Constants.TAB_CONTROLS);

        String operation = theForm.getOperation();
        if (!Utility.isSet(operation)) {
            operation = Constants.PARAMETER_OPERATION_VALUE_SHOW_PRODUCT_LIMITS;
        }
        if (Utility.isSet(operation)) {
            operation = operation.trim();
        }
        if (Constants.PARAMETER_OPERATION_VALUE_SHOW_PRODUCT_LIMITS
                .equalsIgnoreCase(operation)
                && canViewProdLimits) {
            returnValue = handleShowProductLimitsRequest(request, response,
                    theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_EDIT_PRODUCT_LIMITS
                .equalsIgnoreCase(operation)
                && canEditProdLimits) {
            returnValue = handleEditProductLimitsRequest(request, response,
                    theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_UPDATE_PRODUCT_LIMITS
                .equalsIgnoreCase(operation)
                && canEditProdLimits) {
            returnValue = handleUpdateProductLimitsRequest(request, response,
                    theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_PAR_VALUES
                .equalsIgnoreCase(operation)
                && canViewParValues) {
            returnValue = handleShowParValuesRequest(request, response,
                    theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_UPDATE_PAR_VALUES
                .equalsIgnoreCase(operation)
                && canEditParValues) {
            returnValue = handleUpdateParValuesRequest(request, response,
                    theForm, mapping);
        } else {
            returnValue = handleUnknownOperation(request, response, theForm,
                    mapping);
        }
        return returnValue;
    }

    private ActionForward handleShowProductLimitsRequest(
            HttpServletRequest request, HttpServletResponse response,
            ControlsForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        ActionMessages am = new ActionMessages();
        if (isLocationSelected(ae, request)) {
            ae = ControlsLogic.showProductLimits(request, form);
            am = ControlsLogic.sortProductLimits(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            if (am.size() > 0) {
                saveMessages(request, am);
            }
        }
        return mapping.findForward(MAPPING_CONTROLS_SHOW_PRODUCT_LIMITS);
    }

    private ActionForward handleEditProductLimitsRequest(
            HttpServletRequest request, HttpServletResponse response,
            ControlsForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        ActionMessages am = new ActionMessages();
        if (isLocationSelected(ae, request)) {
            ae = ControlsLogic.showProductLimits(request, form);
            am = ControlsLogic.sortProductLimits(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            if (am.size() > 0) {
                saveMessages(request, am);
            }
        }
        return mapping.findForward(MAPPING_CONTROLS_EDIT_PRODUCT_LIMITS);
    }

    private ActionForward handleEditProductLimitsRequestWithKeepData(
            HttpServletRequest request, HttpServletResponse response,
            ControlsForm form, ActionMapping mapping, ActionErrors parentErrors) {
        
        ActionErrors ae = new ActionErrors();
        ActionMessages am = new ActionMessages();
        if (isLocationSelected(ae, request)) {
            ae = ControlsLogic.showProductLimits(request, form);
            am = ControlsLogic.sortProductLimits(request, form);
            if (ae.size() > 0) {
                parentErrors.add(ae);
            }
            if (parentErrors.size() > 0) {
                saveErrors(request, parentErrors);
            }
            if (am.size() > 0) {
                saveMessages(request, am);
            }
        }
        Map<String, String> buffer = new HashMap<String, String>();
        for (Map.Entry<String, String> e : form.getItemIdMaxAllowedEntries()) {
            buffer.put(e.getKey(), e.getValue());
        }
        for (Map.Entry<String, String> e : buffer.entrySet()) {
            form.setItemIdMaxAllowed(e.getKey(), e.getValue());
        }
        
        buffer = new HashMap<String, String>();
        for (Map.Entry<String, String> e : form.getItemIdRestrictionDaysEntries()) {
            buffer.put(e.getKey(), e.getValue());
        }
        
        for (Map.Entry<String, String> e : buffer.entrySet()) {
            form.setItemIdRestrictionDays(e.getKey(), e.getValue());
        }
        return mapping.findForward(MAPPING_CONTROLS_EDIT_PRODUCT_LIMITS);
    }

    private ActionForward handleUpdateProductLimitsRequest(
            HttpServletRequest request, HttpServletResponse response,
            ControlsForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        if (isLocationSelected(ae, request)) {
            ae = ControlsLogic.updateProductLimits(request, form);
            if (ae.size() > 0) {
                return handleEditProductLimitsRequestWithKeepData(request,
                        response, form, mapping, ae);
            } else {
                String message = ClwI18nUtil.getMessage(request,
                        "userportal.esw.text.updateWasSuccessful", null);
                ActionMessages messages = new ActionMessages();
                messages.add("message", new ActionMessage("error.simpleError",
                        message));
                saveMessages(request, messages);
                return handleEditProductLimitsRequest(request, response, form,
                        mapping);
            }
        }
        return mapping.findForward(MAPPING_CONTROLS_EDIT_PRODUCT_LIMITS);
    }

    private ActionForward handleShowParValuesRequest(
            HttpServletRequest request, HttpServletResponse response,
            ControlsForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        ActionMessages am = new ActionMessages();
        if (isLocationSelected(ae, request)) {
            ae = ControlsLogic.showParValues(request, form);
            am = ControlsLogic.sortParValues(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            if (am.size() > 0) {
                saveMessages(request, am);
            }
        }
        return mapping.findForward(MAPPING_CONTROLS_SHOW_PAR_VALUES);
    }

    private ActionForward handleUpdateParValuesRequest(
            HttpServletRequest request, HttpServletResponse response,
            ControlsForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        if (isLocationSelected(ae, request)) {
            ae = ControlsLogic.updateParValues(request, form);
            if (ae.size() > 0) {
                ControlsLogic.sortParValues(request, form);
                saveErrors(request, ae);
            } else {
                handleShowParValuesRequest(request, response, form, mapping);
            }
        }
        return mapping.findForward(MAPPING_CONTROLS_SHOW_PAR_VALUES);
    }

    private boolean isLocationSelected(ActionErrors errors,
            HttpServletRequest request) {
        SiteData currentLocation = ShopTool.getCurrentSite(request);
        if (currentLocation == null) {
            String errorMess = ClwI18nUtil.getMessage(request,
                    "error.noLocationSelected", null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
            saveErrors(request, errors);
            return false;
        }
        return true;
    }
}