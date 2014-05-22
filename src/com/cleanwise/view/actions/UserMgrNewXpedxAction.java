package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.UserMgrLogic;
import com.cleanwise.view.logic.SiteMgrLogic;
import com.cleanwise.view.forms.UserMgrDetailForm;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.LdapItemData;

public final class UserMgrNewXpedxAction extends ActionSuper {
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     * 
     * @param mapping
     *            The ActionMapping used to select this instance
     * @param form
     *            an <code>ActionForm</code> value
     * @param request
     *            The HTTP request we are processing
     * @param response
     *            The HTTP response we are creating
     * 
     * @return an <code>ActionForward</code> value
     * @exception IOException
     *                if an input/output error occurs
     * @exception ServletException
     *                if a servlet exception occurs
     */
    public ActionForward performSub(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if (st.checkSession() == false) {
            return mapping.findForward("/userportal/logon");
        }
        String action = request.getParameter("action");
        MessageResources mr = getResources(request);
        UserMgrDetailForm bForm = (UserMgrDetailForm) form;
        String successMessage = "";
        try {
            if ("updatePassword".equals(action)) {
                ActionErrors ae = checkPassword(request, bForm.getDetail()
                        .getUserData().getUserName(), bForm.getOldPassword(),
                        bForm.getPassword(), bForm.getConfirmPassword());
                if (ae.size() == 0) {
                    ae = UserMgrLogic.updatePassword(request, form);
                }
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    request.setAttribute("changePasswordError", "1");
                    return (mapping.findForward("success"));
                }
                request.setAttribute("successMessage", ClwI18nUtil.getMessage(
                        request,
                        "newxpdex.userProfile.success.passwordChanged", null));
            } else if ("updateProfile".equals(action)) {
                ActionErrors ae = bForm.validate(mapping, request);
                if (ae != null && ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("success"));
                }
                ae = UserMgrLogic.updateUser(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("success"));
                }
                request.setAttribute("successMessage", ClwI18nUtil.getMessage(
                        request, "newxpdex.userProfile.success.profileUpdated",
                        null));

            } else if ("cancelPassword".equals(action)) {
                request
                        .setAttribute("successMessage", ClwI18nUtil.getMessage(
                                request,
                                "newxpdex.userProfile.success.noChanges", null));
            }
            int uid = Utility.parseInt((String) request.getSession()
                    .getAttribute(Constants.USER_ID));
            UserMgrLogic.getUserDetailById(request, uid);
            SiteMgrLogic.lookupInventoryData(request, null);
            SiteMgrLogic.lookupShoppingControls(request, null);
            bForm.setOldPassword("");
            bForm.setPassword("");
            bForm.setConfirmPassword("");
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            ActionErrors ae = new ActionErrors();
            ae
                    .add("user", new ActionError("error.systemError", e
                            .getMessage()));
            saveErrors(request, ae);
            return (mapping.findForward("failure"));
        }
    }

    public static ActionErrors checkPassword(HttpServletRequest request,
            String userName, String oldPassword, String newPassword,
            String confirmNewPassword) throws Exception {
        ActionErrors errors = new ActionErrors();
        String errorMess = "";
        if (oldPassword.length() == 0 || newPassword.length() == 0
                || confirmNewPassword.length() == 0) {
            errorMess = ClwI18nUtil
                    .getMessage(
                            request,
                            "newxpdex.userProfile.errors.changePassword.needEnterRequiredFields",
                            null);

            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
            return errors;
        }
        
        if (newPassword.equals(oldPassword)) {
            errorMess = ClwI18nUtil.getMessage(request,"shop.userProfile.error.newPasswordMustDifferentThanCurrPassword", null);
            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
            return errors;
        }
        LdapItemData ldapItemData = new LdapItemData();
        ldapItemData.setUserName(userName);
        ldapItemData.setPassword(oldPassword);
        User userAPI = APIAccess.getAPIAccess().getUserAPI();
        try {
            userAPI.login(ldapItemData, "");
            if (newPassword.equals(confirmNewPassword) == false) {
                errorMess = ClwI18nUtil
                        .getMessage(
                                request,
                                "newxpdex.userProfile.errors.changePassword.confirmPasswordIncorrect",
                                null);
                errors.add("error", new ActionError("error.simpleGenericError",
                        errorMess));
            }
        } catch (InvalidLoginException ile) {
            errorMess = ClwI18nUtil
                    .getMessage(
                            request,
                            "newxpdex.userProfile.errors.changePassword.oldPasswordIncorrect",
                            null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return errors;
    }
}
