package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.view.forms.StoreMsgMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.StoreMsgMgrLogic;

public class StoreMsgConfigMgrAction extends ActionSuper {
    public ActionForward performSub(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }
        String saveStr = ClwI18nUtil.getMessage(request, "global.action.label.save");
        String configAllStr = ClwI18nUtil.getMessage(request,
                "storemessages.text.configureAllAccounts");
        String searchStr = ClwI18nUtil.getMessage(request,
                "global.action.label.search");
        StoreMsgMgrForm bForm = (StoreMsgMgrForm) form;
        try {
            if (action.equals(searchStr)) {
                ActionErrors ae = StoreMsgMgrLogic.searchConfigAccounts(
                        request, bForm);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            } else if (action.equals(saveStr) || action.equals(configAllStr)) {
                ActionErrors ae = StoreMsgMgrLogic.updateConfigAccounts(
                        request, bForm, action.equals(configAllStr));
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                } else {
                    if (bForm.getConfigAccounts() != null) {
                        ae = StoreMsgMgrLogic.searchConfigAccounts(request,
                                bForm);
                        if (ae.size() > 0) {
                            saveErrors(request, ae);
                        }
                    }
                }
                return (mapping.findForward("success"));
            } else {
                StoreMsgMgrLogic.init(request, bForm);
                return (mapping.findForward("success"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ActionErrors ae = new ActionErrors();
            ae.add("error",
                    new ActionError("error.systemError", e.getMessage()));
            saveErrors(request, ae);
            return (mapping.findForward("failure"));
        }
    }
}
