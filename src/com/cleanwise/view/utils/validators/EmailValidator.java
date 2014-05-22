package com.cleanwise.view.utils.validators;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.forms.StoreAccountMgrDetailForm;
import com.cleanwise.view.forms.StoreStoreMgrDetailForm;
import com.cleanwise.view.i18n.ClwI18nUtil;

public class EmailValidator {
    private final static String DEFAULT_MESSAGE_KEY = "error.invalidEmailFormat";
    private final static String DEFAULT_EMAIL_LABEL = "userWorkOrder.text.email";

    public static void validateEmail(HttpServletRequest pRequest,
            ActionErrors pErrors, String pFieldValue) {
        validateEmail(pRequest, pErrors, DEFAULT_EMAIL_LABEL, null, pFieldValue);
    }

    public static void validateEmail(HttpServletRequest pRequest,
            ActionErrors pErrors, String pFieldName, String pFieldValue) {
        validateEmail(pRequest, pErrors, null, pFieldName, pFieldValue);
    }

    public static void validateEmail(HttpServletRequest pRequest,
            ActionErrors pErrors, String pLabelKey, String pFieldName,
            String pFieldValue) {
        if (Utility.isSet(pFieldValue) == true) {
            if (Utility.isValidEmailAddress(pFieldValue) == false) {
                if (pLabelKey != null) {
                    String pFieldNameNew = pFieldName = ClwI18nUtil.getMessage(
                            pRequest, pLabelKey, null);
                    if (pFieldNameNew != null) {
                        pFieldName = pFieldNameNew;
                    }
                }
                if (pFieldName == null) {
                    pFieldName = ClwI18nUtil.getMessage(pRequest,
                            DEFAULT_EMAIL_LABEL, null);
                }
                String msg = ClwI18nUtil.getMessage(pRequest,
                        DEFAULT_MESSAGE_KEY, new Object[] { pFieldName,
                                pFieldValue });
                pErrors.add("emailAddress", new ActionError(
                        "error.simpleGenericError", msg));
            }
        }
    }

    public static void validateForm(StoreStoreMgrDetailForm pForm,
            HttpServletRequest pRequest, ActionErrors pErrors) {
        validateEmail(pRequest, pErrors, "Customer Service Email", pForm
                .getCustomerEmail());
        validateEmail(pRequest, pErrors, "Contact Us Email", pForm
                .getContactEmail());
        validateEmail(pRequest, pErrors, "Default Email", pForm
                .getDefaultEmail());
        validateEmail(pRequest, pErrors, "Email", pForm.getEmailAddress());
        validateEmail(pRequest, pErrors, "Work Order Email Address", pForm
                .getWorkOrderEmailAddress());
    }

    public static void validateForm(StoreAccountMgrDetailForm pForm,
            HttpServletRequest pRequest, ActionErrors pErrors) {
        validateEmail(pRequest, pErrors, "Customer Service Email", pForm
                .getCustomerEmail());
        validateEmail(pRequest, pErrors, "Contact Us CC Email", pForm
                .getContactUsCCEmail());
        validateEmail(pRequest, pErrors, "Default Email", pForm
                .getDefaultEmail());
        validateEmail(pRequest, pErrors, "Email", pForm.getEmailAddress());
        validateEmail(pRequest, pErrors, "Substitution Manager eMails", pForm
                .getOrderManagerEmails());
    }
}
