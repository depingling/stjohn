package com.cleanwise.view.utils;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

import com.cleanwise.view.i18n.ClwI18nUtil;

import javax.servlet.http.HttpServletRequest;


public class ValidateActionMessage {

    private static final Logger log = Logger.getLogger(ValidateActionMessage.class);

    ActionMessages mWarnings;
    ActionMessages mErrors;
    ActionMessages mSystemErrors;

    public static final String CLW_SYSTEM_ERROR_M_KEY = "error.systemError";
    public static final String CLW_WARNING_M_KEY = "warning.genericWarning";
    public static final String CLW_ERROR_M_KEY = "error.simpleGenericError";

    public static final String CLW_SYSTEM_ERROR = "systemError";
    public static final String CLW_WARNING = "warning";
    public static final String CLW_ERROR = "error";


    public ValidateActionMessage() {
        this.mWarnings = new ActionMessages();
        this.mErrors = new ActionMessages();
        this.mSystemErrors = new ActionMessages();
    }

    public ValidateActionMessage(ActionMessages pSystemErrors, ActionMessages pErrors, ActionMessages pWarnings) {
        this.mErrors = pErrors;
        this.mSystemErrors = pSystemErrors;
        this.mWarnings = pWarnings;
    }


    public void putSystemError(String pSystemError) {
        log.warn("putError()=> pSystemError: " + pSystemError);
        mSystemErrors.add(CLW_SYSTEM_ERROR, new ActionMessage(CLW_SYSTEM_ERROR_M_KEY, pSystemError));
    }

    public void putError(HttpServletRequest pRequest, String pKey, Object... pParams) {
        String err = ClwI18nUtil.getMessage(pRequest, pKey, pParams);
        putError(err);
    }

    public void putError(String pError) {
        mErrors.add(CLW_ERROR, new ActionMessage(CLW_ERROR_M_KEY, pError));
    }

    public void putWarning(String pWarning) {
        mWarnings.add(CLW_WARNING, new ActionMessage(CLW_WARNING_M_KEY, pWarning));
    }

    public void putWarning(HttpServletRequest pRequest, String pKey, Object... pParams) {
        String wrn = ClwI18nUtil.getMessage(pRequest, pKey, pParams);
        putWarning(wrn);
    }


    public ActionErrors getActionSystemErrors() {
        ActionErrors ae = new ActionErrors();
        ae.add(mSystemErrors);
        return ae;
    }

    public ActionErrors getActionErrors() {
        ActionErrors ae = new ActionErrors();
        ae.add(mErrors);
        return ae;
    }

    public ActionErrors getActionWarnings() {
        ActionErrors ae = new ActionErrors();
        ae.add(mWarnings);
        return ae;
    }

    public List<String> getErrors() {
        return getMessages(this.mErrors, CLW_SYSTEM_ERROR);
    }

    public List<String> getWarnings() {
        return getMessages(this.mWarnings, CLW_WARNING);
    }

    public List<String> getSystemErrors() {
        return getMessages(this.mSystemErrors, CLW_ERROR);
    }


    public boolean hasSystemErrors() {
        return !mSystemErrors.isEmpty();
    }

    public boolean hasErrors() {
        return !mErrors.isEmpty();
    }

    public boolean hasWarnings() {
        return !mWarnings.isEmpty();
    }


    public void reset() {
        this.mWarnings = new ActionMessages();
        this.mErrors = new ActionMessages();
        this.mSystemErrors = new ActionMessages();
    }

    public void addActionErrors(ActionErrors ae) {
        Iterator it = ae.get("error");
        while (it.hasNext()) {
            ActionMessage am = (ActionMessage) it.next();
            mErrors.add("error", am);
        }
    }

    public ArrayList<String> getMessages(ActionMessages pActionMessages, String pMsgCode) {

         ArrayList<String> messages = new ArrayList<String>();
        Iterator it = pActionMessages.get(pMsgCode);
        while (it.hasNext()) {
            ActionMessage am = (ActionMessage) it.next();
            Object[] values = am.getValues();
            for (Object value : values) {
                if (value instanceof String) {
                    messages.add((String) value);
                }
            }
        }

        return messages;
    }
}
