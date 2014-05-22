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
import com.cleanwise.view.forms.*;
import com.cleanwise.view.logic.UserWarrantyMgrLogic;


/**
 * Title:        UserWarrantyMgrAction
 * Description:  Actions manager for the awarranty processing in the USERPORTAL .
 * Purpose:      Class for calling logic methods  which  process the request
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         25.09.2007
 * Time:         14:13:32
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc..
 */
public class UserWarrantyMgrAction extends ActionSuper {

    private static final String FAILURE = "failure";
    private static final String MAIN    = "main";
    private static final String SUCCESS = "success";
    private static final String DISPLAY = "display";
    private static final String ERROR   = "error";
    private static final String DETAIL  = "detail";
    private static final String SEARCH  = "search";

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
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }

        SessionTool st = new SessionTool(request);
        if (!st.checkSession()) {
            logm("session timeout " + st.paramDebugString());
            return mapping.findForward(st.getLogonMapping());
        }

        ActionForward actionForward;
        try {
            actionForward = runWarrantyWorkerForm(action, form, request, response, mapping);
        } catch (Exception e) {
            e.printStackTrace();
            actionForward = mapping.findForward(FAILURE);
        }

        navigateBreadCrumb(request, actionForward);

        return actionForward;
    }

    private ActionForward runWarrantyWorkerForm(String action, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMapping mapping) {

        String forward_page = MAIN;
        logm("action : " + action + " form : " + form + " mapping attribute : " + mapping.getAttribute());
        try {
            if (form instanceof UserWarrantyMgrForm) forward_page = userWarrantyMgrFormWorker(action, form, request);
            else if (form instanceof UserWarrantyDetailMgrForm) forward_page = userWarrantyDetailMgrFormWorker(action, form, request, response);
            else if (form instanceof UserWarrantyConfigMgrForm) forward_page = userWarrantyConfigFormWorker(action, form, request);
            else if (form instanceof UserWarrantyNoteMgrForm) forward_page = userNoteMgrFormWorker(action, form, request);
            else if (form instanceof UserWarrantyContentMgrForm) forward_page = userContentMgrFormWorker(action, form, request,response);
            else logm("The worker of the form can't be found.Unknown form : " + form);
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            return mapping.findForward(ERROR);
        }
        logm("Forward page :" + mapping.findForward(forward_page).getPath());
        return mapping.findForward(forward_page);
    }

    private String userNoteMgrFormWorker(String action, ActionForm form, HttpServletRequest request) throws Exception {

        ActionErrors ae;
        MessageResources mr = getResources(request);

        String deleteStr    = getMessage(mr, request, "global.action.label.delete");
        String saveStr      = getMessage(mr, request, "global.action.label.save");
        String detail       = "detail";
        String createNewStr = "create";
        String initSearch   = "initSearch";

        if (initSearch.equals(action)) {
            ae = UserWarrantyMgrLogic.init(request, (UserWarrantyNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (detail.equals(action)) {
            ae = UserWarrantyMgrLogic.getNoteDetail(request, (UserWarrantyNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (deleteStr.equals(action)) {
            ae = UserWarrantyMgrLogic.removeWarrantyNote(request, (UserWarrantyNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (saveStr.equals(action)) {
            ae = UserWarrantyMgrLogic.updateWarrantyNote(request, (UserWarrantyNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (createNewStr.equals(action)) {
            ae = UserWarrantyMgrLogic.createNewWarrantyNote(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return SUCCESS;
    }

    private String userContentMgrFormWorker(String action, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionErrors ae;
        MessageResources mr = getResources(request);

        String createNew  = getMessage(mr, request, "global.action.label.create");
        String saveStr    = getMessage(mr, request, "global.action.label.save");
        String removeStr  = getMessage(mr, request, "global.action.label.delete");
        String init       = "init";
        String detail     = "detail";
        String readDocs   = "readDocs";

        if (init.equals(action)) {
            ae = UserWarrantyMgrLogic.init(request, (UserWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (detail.equals(action)) {
            ae = UserWarrantyMgrLogic.getContentDetail(request, (UserWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (createNew.equals(action)) {
            ae = UserWarrantyMgrLogic.createNewContent(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (saveStr.equals(action)) {
            ae = UserWarrantyMgrLogic.updateContentData(request, (UserWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return DETAIL;
            }
            return DETAIL;
        } else if (removeStr.equals(action)) {
            ae = UserWarrantyMgrLogic.removeWarrantyContent(request, (UserWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (readDocs.equals(action)) {
            ae = UserWarrantyMgrLogic.readDocument(request, response, (UserWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return SUCCESS;
    }


    private String userWarrantyConfigFormWorker(String action, ActionForm form, HttpServletRequest request) throws Exception {

        ActionErrors ae;
        MessageResources mr = getResources(request);

        String saveStr                    = getMessage(mr, request, "global.action.label.save");
        String changeActiveAssetCategory  = "changeActiveAssetCategory";
        String assetWarrantyConfigDetails = "assetWarrantyConfigDetails";
        String configStr                  = "config";
        String initConfig                 = "initConfig";

        if (action.equals(configStr)) {
            ae = UserWarrantyMgrLogic.getWarrantyConfiguration(request, (UserWarrantyConfigMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (initConfig.equals(action)) {
            ae = UserWarrantyMgrLogic.init(request, (UserWarrantyConfigMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (assetWarrantyConfigDetails.equals(action)) {
            ae = UserWarrantyMgrLogic.getAssetWarrantyDetails(request, null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (saveStr.equals(action)) {
            ae = UserWarrantyMgrLogic.updateAssetWarrantyConfig(request, (UserWarrantyConfigMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (changeActiveAssetCategory.equals(action)) {
            ae = UserWarrantyMgrLogic.getWarrantyConfiguration(request, (UserWarrantyConfigMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return SUCCESS;
    }

    private String userWarrantyMgrFormWorker(String action, ActionForm form, HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);

        String searchStr = getMessage(mr, request, "global.action.label.search");
        String createStr = getMessage(mr, request, "global.action.label.create");
        String init      = "init";

        if (action.equals(searchStr)) {
            ActionErrors ae = UserWarrantyMgrLogic.search(request, (UserWarrantyMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(init)) {
            ActionErrors ae = UserWarrantyMgrLogic.init(request, (UserWarrantyMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(createStr)) {
            ActionErrors ae = UserWarrantyMgrLogic.createNewWarranty(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        return DISPLAY;
    }

    private String userWarrantyDetailMgrFormWorker(String action, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        MessageResources mr = getResources(request);

        String saveStr   = getMessage(mr,   request, "global.action.label.save");
        String createStr = getMessage(mr, request, "global.action.label.create");
        String assetWarrantyInit   = "assetWarrantyInit";
        String assetWarrantyDetail = "assetWarrantyDetail";
        String warrantyDocsInit    = "warrantyDocsInit";
        String detail              = "detail";
        String createNoteStr       = "createNote";
        String createContentStr    = "createContent";
        String initConfig          = "initConfig";

        if (action.equals(assetWarrantyInit)) {
            return SUCCESS;
        } else if (action.equals(warrantyDocsInit)) {
            return SUCCESS;
        } else if (action.equals(detail)) {
            ActionErrors ae = UserWarrantyMgrLogic.getDetail(request, (UserWarrantyDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }else if (action.equals(createNoteStr)) {
            ActionErrors ae = UserWarrantyMgrLogic.createNewWarrantyNote(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(createContentStr)) {
            ActionErrors ae = UserWarrantyMgrLogic.createNewContent(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (initConfig.equals(action)) {
            ActionErrors ae = UserWarrantyMgrLogic.init(request, (UserWarrantyConfigMgrForm) null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(saveStr)) {
            ActionErrors ae = UserWarrantyMgrLogic.updateWarranty(request, (UserWarrantyDetailMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(createStr)) {
            ActionErrors ae = UserWarrantyMgrLogic.createNewWarranty(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return DISPLAY;
    }
}
