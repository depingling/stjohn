package com.cleanwise.view.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.cleanwise.view.forms.UiAccountMgrForm;
import com.cleanwise.view.logic.UiAccountMgrLogic;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;

public class UiAccountMgrAction extends ActionSuper {

	private static final Logger log = Logger.getLogger(UiAccountMgrAction.class);
	private static final String ERROR = "error", 
								SUCCESS = "success",
								INIT = "init", 
								DETAIL = "detail",
								SAVE_PAGE = "Save Page Interface";

	public ActionForward performSub(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String action = request.getParameter("action");

		// Determine the ui manager action to be performed
		if (action == null) action = "init";

		SessionTool st = new SessionTool(request);
		if (!st.checkSession())
			return mapping.findForward(st.getLogonMapping());

		ActionForward actionForward;
		try {
			actionForward = processAction(action, form, request, mapping,
					response);
		} catch (Exception e) {
			request.setAttribute(Constants.EXCEPTION_OBJECT, e);
			return mapping.findForward(ERROR);
		}
		return actionForward;
	}

	private ActionForward processAction(String action, ActionForm form,
			HttpServletRequest request, ActionMapping mapping,
			HttpServletResponse response) throws Exception {
		ActionErrors ae;
		if (INIT.equals(action)) {
			ae = UiAccountMgrLogic.init((UiAccountMgrForm) form, request);
			if (ae.size() > 0) {
				saveErrors(request, ae);
				return mapping.findForward(ERROR);
			}
			ae = UiAccountMgrLogic.detail((UiAccountMgrForm) form, request);
			if (ae.size() > 0) {
				saveErrors(request, ae);
				return mapping.findForward(ERROR);
			}
			return mapping.findForward(DETAIL);
		} else if (DETAIL.equals(action)) {
			ae = UiAccountMgrLogic.detail((UiAccountMgrForm) form, request);
			if (ae.size() > 0) {
				saveErrors(request, ae);
				return mapping.findForward(ERROR);
			}
			return mapping.findForward(DETAIL);
		} else if (SAVE_PAGE.equals(action)) {
			ae = UiAccountMgrLogic.savePage((UiAccountMgrForm) form, request);
			if (ae.size() > 0) {
				saveErrors(request, ae);
				return mapping.findForward(ERROR);
			}
			return mapping.findForward(DETAIL);
		}
		return mapping.findForward(SUCCESS);
	}

}
