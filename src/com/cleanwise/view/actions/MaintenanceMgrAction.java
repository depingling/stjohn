package com.cleanwise.view.actions;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionAttributes;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.logic.MaintenanceMgrLogic;


/**
 * Title:        MaintenanceMgrAction
 * Description:  Actions manager for the asset processing in the USERPORTAL .
 * Purpose:      Class for calling logic methods  which  process the request
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         02.01.2006
 * Time:         12:15:33
 *
 * @author Veronika Denega, TrinitySoft, Inc.
 */
public class MaintenanceMgrAction extends ActionSuper {
    private static final String SUCCESS = "success";
    private static final String DETAIL  = "detail";
    private static final String FAILURE = "failure";
    private static final String DISPLAY = "display";
    private static final String INIT    = "init";
    private static final String PREVIEW = "preview";


    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @param form     Description of Parameter
     * @return Description of the Returned Value
     * @throws java.io.IOException            if an input/output error occurs
     * @throws javax.servlet.ServletException if a servlet exception occurs
     */
    public ActionForward performSub(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException, ServletException {

        // Determine the store manager action to be performed
        ActionErrors ae;

        String action = request.getParameter("action");
        UiFrameForm uiFF = null;
        try {
            uiFF = (UiFrameForm) form;
            String iECurseAction =  uiFF.getDispatch();
            if (action == null) {
                if ((iECurseAction != null) && (!iECurseAction.equals("")) && (!iECurseAction.equals("error"))) {
                    action = iECurseAction;
                } else {
                    action = INIT;
                }
                uiFF.setDispatch("");
            }
        } catch (Exception ex) {

        }

        SessionTool st = new SessionTool(request);
        if (!st.checkSession()) {
            logm("session timeout " + st.paramDebugString());
            return mapping.findForward(st.getLogonMapping());
        }
        String forward = SUCCESS;
        MessageResources mr = getResources(request);
        String saveStr      = getMessage(mr, request, "global.action.label.save");
        String init         = INIT;
        String detail       = DETAIL;
        String createNew    = getMessage(mr, request, "template.xpedx.button.createNew"); /* "Create New" */
        String createNewByTemplate = getMessage(mr, request, "template.xpedx.button.continue"); /* "Continue"; */
        String previewTemplate      = getMessage(mr, request, "template.xpedx.button.preview");
        String addNewArticle    = getMessage(mr, request, "news.admin.button.addNewArticle"); /* "Add New Article" */
        String deleteSelected = getMessage(mr, request, "news.admin.button.deleteSelected"); /* "Delete Selected"; */
        String previewNewsTopic      = getMessage(mr, request, "news.admin.button.preview");
        String cancel    = getMessage(mr, request, "global.action.label.cancel");
        String saveNewsTopic = getMessage(mr, request, "global.action.label.save");
        String editArticle = "Edit Article";
        String initFaq = "initFAQ";
        String addNewFaq = "addNewFAQ";
        String saveFaq = "saveFAQ";
        String previewFaq      = getMessage(mr, request, "faq.admin.button.preview");
        String deleteSelectedFaq = "deleteSelectedFAQ";
        try {
            if (init.equals(action)) {
//                ae = MaintenanceMgrLogic.init(request, form);
                ae = MaintenanceMgrLogic.initNews(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    forward = FAILURE;
                }
                forward = SUCCESS;
            } else if (detail.equals(action)) {
                ae = MaintenanceMgrLogic.getFrameDetail(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    forward = FAILURE;
                }
                forward = DETAIL;
            } else if (createNew.equals(action) || action.equalsIgnoreCase("Create New")) {
                ae = MaintenanceMgrLogic.createNew(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    forward = FAILURE;
                }
                forward = SUCCESS;
            } else if (createNewByTemplate.equals(action) || action.equalsIgnoreCase("Continue")) {
                ae = MaintenanceMgrLogic.createNewBySelectedTemplate(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    forward = FAILURE;
                }
                forward = DETAIL;
            } else if ( (saveStr.equals(action) || action.equalsIgnoreCase("save")) && (uiFF == null || !(uiFF.getMode2().equals("editArticle") || uiFF.getMode2().equals("previewArticle")))) {
                ae = MaintenanceMgrLogic.update(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    forward = DETAIL;
                }
                forward = SUCCESS;
            } else if (saveNewsTopic.equals(action) && ((uiFF.getMode2().equals("editArticle")) || (uiFF.getMode2().equals("previewArticle")))) {
                ae = MaintenanceMgrLogic.saveArticle(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    forward = FAILURE;
                } else {
                    forward = SUCCESS;
                }
            } else if (addNewArticle.equals(action)) {
                ae = MaintenanceMgrLogic.addNews(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    forward = FAILURE;
                } else {
                    forward = SUCCESS;
                }
            } else if (editArticle.equals(action)) {
                ae = MaintenanceMgrLogic.editArticle(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    forward = FAILURE;
                } else {
                    forward = SUCCESS;
                }
            } else if (previewNewsTopic.equals(action) || previewFaq.equals(action)) {
                ae = MaintenanceMgrLogic.previewArticle(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    forward = FAILURE;
                } else {
                    forward = SUCCESS;
                }
            } else if (cancel.equals(action)) {
                ae = MaintenanceMgrLogic.cancelEditing(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    forward = FAILURE;
                } else {
                    forward = SUCCESS;
                }
            } else if (deleteSelected.equals(action)) {
                ae = MaintenanceMgrLogic.deleteNews(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    forward = FAILURE;
                } else {
                    forward = SUCCESS;
                }
                request.setAttribute("gotoAnchor","true");
            } else if (previewTemplate.equals(action) || action.equals("Preview Template")) {
                ae = MaintenanceMgrLogic.preview(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    forward = FAILURE;
                } else {
                    forward = PREVIEW;
                }
              } else if (initFaq.equals(action)) {
                ae = MaintenanceMgrLogic.initFAQ(request, form);
                if (ae.size() > 0) {
                  saveErrors(request, ae);
                  forward = FAILURE;
                }
                forward = SUCCESS;
              } else if (addNewFaq.equals(action)) {
                  ae = MaintenanceMgrLogic.addFAQ(request, form);
                  if (ae.size() > 0) {
                      saveErrors(request, ae);
                      forward = FAILURE;
                  } else {
                      forward = SUCCESS;
                  }
              } else if (deleteSelectedFaq.equals(action)) {
                  ae = MaintenanceMgrLogic.deleteFAQ(request, form);
                  if (ae.size() > 0) {
                      saveErrors(request, ae);
                      forward = FAILURE;
                  } else {
                      forward = SUCCESS;
                  }
                  request.setAttribute("gotoAnchor","true");
              } else if (saveFaq.equals(action)) {
                  ae = MaintenanceMgrLogic.saveFAQ(request, form);
                  if (ae.size() > 0) {
                      saveErrors(request, ae);
                      forward = FAILURE;
                  } else {
                      forward = SUCCESS;
                  }
           }
        } catch (Exception e) {
            e.printStackTrace();
	    }
	    return (mapping.findForward(forward));
    }



}
