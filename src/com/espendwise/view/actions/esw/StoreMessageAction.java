/**
 * Title: ReportingAction 
 * Description: This is the Struts Action class handling the ESW reporting functionality.
 */

package com.espendwise.view.actions.esw;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.espendwise.view.forms.esw.StoreMessageForm;
import com.espendwise.view.logic.esw.StoreMessageLogic;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class StoreMessageAction extends EswAction {
    private static final Logger log = Logger.getLogger(StoreMessageAction.class);
    
    //constants to hold the various action mappings that can be returned by this action class.
    private static final String MAPPING_MESSAGE_LIST = "messageList";
    private static final String MAPPING_MESSAGE_DETAIL = "messageDetail";
    private static final String MAPPING_MESSAGE_PREVIEW = "messagePreview";

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     * @param  mapping      the ActionMapping used to select this instance.
     * @param  form         the ActionForm containing the data.
     * @param  request      the HTTP request we are processing.
     * @param  response     the HTTP response we are creating.
     * @return              an ActionForward describing the component that should receive control.
     */
    public ActionForward performAction(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response) {

        //If there isn't a currently logged on user then go to the login page
        if (!new SessionTool(request).checkSession()) {
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
        }
        
        ActionForward returnValue = null;
        StoreMessageForm theForm = (StoreMessageForm)form;
        
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        //make sure the user has the ability to access maintenance tab - if not return an error
        HttpSession session = request.getSession();
        CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);        
        if (!user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_MESSAGES)) {
            ActionErrors errors = new ActionErrors();
            String errorMess = ClwI18nUtil.getMessage(request, "error.unauthorized", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            saveErrors(request, errors);
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }
        
        sessionDataUtil.setSelectedSubTab(Constants.TAB_CONTROLS);
        sessionDataUtil.setPreviousShoppingModuleTab(Constants.TAB_CONTROLS);
        
        //determine what action to perform
        //If an operation has been specified use it.
        String operation = (String) request.getParameter("operation");
        
        if (!Utility.isSet(operation)) {
            operation = Constants.PARAMETER_OPERATION_VALUE_SHOW_MESSAGES;
        }
                
        if (Constants.PARAMETER_OPERATION_VALUE_SHOW_MESSAGES
                .equalsIgnoreCase(operation)) {
            returnValue = handleShowMessagesRequest(request, response,
                    theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_SORT_MESSAGES
                .equalsIgnoreCase(operation)) {
            returnValue = handleSortMessagesRequest(request, response,
                theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_MESSAGE_DETAIL
                .equalsIgnoreCase(operation)) {
            returnValue = handleShowMessageDetailRequest(request, response,
                theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_CREATE_MESSAGE
                .equalsIgnoreCase(operation)) {
            returnValue = handleCreateMessageRequest(request, response,
                theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_SAVE_MESSAGE
                .equalsIgnoreCase(operation)) {
            returnValue = handleSaveMessageRequest(request, response,
                theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_DELETE_MESSAGE
                .equalsIgnoreCase(operation)) {
            returnValue = handleDeleteMessageRequest(request, response,
                theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_PREVIEW_MESSAGE
                .equalsIgnoreCase(operation)) {
            returnValue = mapping.findForward(MAPPING_MESSAGE_DETAIL);
        } else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_MESSAGE
                .equalsIgnoreCase(operation)) {
            returnValue = handlePreviewMessageRequest(request, response,
                    theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_ADD_TRANSLATION
                .equalsIgnoreCase(operation)) {
            returnValue = handleAddTranslationRequest(request, response,
                    theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_DELETE_TRANSLATION
                .equalsIgnoreCase(operation)) {
            returnValue = handleDeleteTranslationRequest(request, response,
                    theForm, mapping);
        } else if (Constants.PARAMETER_OPERATION_VALUE_PUBLISH_MESSAGE
                .equalsIgnoreCase(operation)) {
            returnValue = handlePublishMessageRequest(request, response,
                    theForm, mapping);
        } else{
            returnValue = handleUnknownOperation(request, response, theForm, mapping);
        }
        
        return returnValue;    
    }
    
    private ActionForward handleShowMessagesRequest(
            HttpServletRequest request, HttpServletResponse response,
            StoreMessageForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        try {
            ae = StoreMessageLogic.getStoreMessages(request, form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ae.size() > 0) {
            saveErrors(request, ae);
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }
        return mapping.findForward(MAPPING_MESSAGE_LIST);
    }
    
    private ActionForward handleSortMessagesRequest(
            HttpServletRequest request, HttpServletResponse response,
            StoreMessageForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        StoreMessageLogic.sortStoreMessage(request, form);
        return mapping.findForward(MAPPING_MESSAGE_LIST);
    }
    
    private ActionForward handleCreateMessageRequest(
            HttpServletRequest request, HttpServletResponse response,
            StoreMessageForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        try {
            ae = StoreMessageLogic.createStoreMessage(request, form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ae.size() > 0) {
            saveErrors(request, ae);
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }
        return mapping.findForward(MAPPING_MESSAGE_DETAIL);
    }
    
    private ActionForward handleShowMessageDetailRequest(
            HttpServletRequest request, HttpServletResponse response,
            StoreMessageForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        try {
            ae = StoreMessageLogic.getStoreMessageDetail(request, form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ae.size() > 0) {
            saveErrors(request, ae);
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }
        return mapping.findForward(MAPPING_MESSAGE_DETAIL);
    }
    
    private ActionForward handleDeleteMessageRequest(
            HttpServletRequest request, HttpServletResponse response,
            StoreMessageForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        try {
            ae = StoreMessageLogic.deleteStoreMessage(request, form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ae.size() > 0) {
            saveErrors(request, ae);
        }else{
            ActionMessages result = new ActionMessages();
            String message = ClwI18nUtil.getMessage(request, "message.successMessage", null);
            result.add("message", new ActionMessage("message.simpleMessage", message));
            saveMessages(request, result);
        }
        return mapping.findForward(MAPPING_MESSAGE_LIST);
    }
    private ActionForward handleSaveMessageRequest(
            HttpServletRequest request, HttpServletResponse response,
            StoreMessageForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        try {
            ae = StoreMessageLogic.saveStoreMessage(request, form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ae.size() > 0) {
            saveErrors(request, ae);
        }else{
            ActionMessages result = new ActionMessages();
            String message = ClwI18nUtil.getMessage(request, "userportal.esw.text.messageSaved", null);
            result.add("message", new ActionMessage("message.simpleMessage", message));
            saveMessages(request, result);
        }
        return mapping.findForward(MAPPING_MESSAGE_DETAIL);
    }
    
    private ActionForward handlePreviewMessageRequest(
            HttpServletRequest request, HttpServletResponse response,
            StoreMessageForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        try {
            ae = StoreMessageLogic.previewStoreMessage(request, form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ae.size() > 0) {
            saveErrors(request, ae);
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }
        return mapping.findForward(MAPPING_MESSAGE_PREVIEW);
    }
    
    private ActionForward handleAddTranslationRequest(
            HttpServletRequest request, HttpServletResponse response,
            StoreMessageForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        try {
            ae = StoreMessageLogic.addMessageTranslation(request, form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ae.size() > 0) {
            saveErrors(request, ae);
        }
        return mapping.findForward(MAPPING_MESSAGE_DETAIL);
    }
    
    private ActionForward handleDeleteTranslationRequest(
            HttpServletRequest request, HttpServletResponse response,
            StoreMessageForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        try {
            ae = StoreMessageLogic.deleteMessageTranslation(request, form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ae.size() > 0) {
            saveErrors(request, ae);
        }else{
            ActionMessages result = new ActionMessages();
            String message = ClwI18nUtil.getMessage(request, "message.successMessage", null);
            result.add("message", new ActionMessage("message.simpleMessage", message));
            saveMessages(request, result);
        }
        return mapping.findForward(MAPPING_MESSAGE_DETAIL);
    }
    
    private ActionForward handlePublishMessageRequest(
            HttpServletRequest request, HttpServletResponse response,
            StoreMessageForm form, ActionMapping mapping) {
        ActionErrors ae = new ActionErrors();
        try {
            ae = StoreMessageLogic.saveStoreMessage(request, form, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ae.size() > 0) {
            saveErrors(request, ae);
        }else{
            ActionMessages result = new ActionMessages();
            String message = ClwI18nUtil.getMessage(request, "userportal.esw.text.messageSavedAndPublished", null);
            result.add("message", new ActionMessage("message.simpleMessage", message));
            saveMessages(request, result);
        }
        return mapping.findForward(MAPPING_MESSAGE_DETAIL);
    }
    
}

