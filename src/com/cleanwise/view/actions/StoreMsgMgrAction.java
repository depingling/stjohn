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

public class StoreMsgMgrAction extends ActionSuper {
    public ActionForward performSub(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }
        String saveStr = ClwI18nUtil.getMessage(request, "global.action.label.save");
        String searchStr = ClwI18nUtil.getMessage(request,
                "global.action.label.search");
        String createNewStr = ClwI18nUtil.getMessage(request,
                "global.label.createNew");
        String previewStr = ClwI18nUtil.getMessage(request,
                "global.label.preview");
        String cloneStr = ClwI18nUtil.getMessage(request, "global.label.clone");
        String publishStr = ClwI18nUtil.getMessage(request,
                "global.label.publish");
        StoreMsgMgrForm bForm = (StoreMsgMgrForm) form;
        try {
            if (action.equals("init")) {
                StoreMsgMgrLogic.init(request, bForm);
                ActionErrors ae = StoreMsgMgrLogic.getDetail(request, bForm);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            } else if (action.equals(searchStr)) {
                ActionErrors ae = StoreMsgMgrLogic.searchMessages(request,
                        bForm);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            } else if (action.equals(createNewStr)) {
                StoreMsgMgrLogic.createMessage(request, bForm);
                return (mapping.findForward("msgdetail"));
            } else if (action.equals(saveStr)) {
                ActionErrors ae = StoreMsgMgrLogic.updateMessage(request,
                        bForm, false);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            } else if (action.equals(publishStr)) {
                ActionErrors ae = StoreMsgMgrLogic.updateMessage(request,
                        bForm, true);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            } else if (action.equals(cloneStr)) {
                ActionErrors ae = StoreMsgMgrLogic.cloneMessage(request, bForm);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            } else if (action.equals(previewStr)) {
                ActionErrors ae = StoreMsgMgrLogic.previewMessage(request,
                        bForm);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
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
