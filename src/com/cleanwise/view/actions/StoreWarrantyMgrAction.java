package com.cleanwise.view.actions;

import com.cleanwise.view.forms.*;
import com.cleanwise.view.logic.StoreWarrantyMgrLogic;
import com.cleanwise.view.utils.SessionTool;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Title:        StoreWarrantyMgrAction
 * Description:  Actions manager for the warranty processing.
 * Purpose:      Class for calling logic methods  which  process the request
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         25.09.2007
 * Time:         16:43:01
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */

public class StoreWarrantyMgrAction extends ActionSuper {

    private static final String FAILURE = "failure";
    private static final String MAIN    = "main";
    private static final String SUCCESS = "success";
    private static final String DETAIL  = "detail";
    private static final String SEARCH  = "search";
    private static final String RET_CD  = "retCd";
    private static final String AWC_DETAIL = "awcDetail";
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

        try {
            ActionForward actionForward;
            actionForward = runWorkerForm(action, form, request,response, mapping);
            return actionForward;
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward(FAILURE);
        }

    }

    private ActionForward runWorkerForm(String action, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMapping mapping) throws Exception {

        String forward_page = MAIN;
        logm("action : " + action + " form : " + form + " mapping attribute : " + mapping.getAttribute());
        if (form instanceof StoreWarrantyMgrForm) forward_page = storeWarrantyMgrFormWorker(action, form, request);
        else if(form instanceof StoreWarrantyDetailForm) forward_page=storeWarrantyDetailFormWorker(action, form, request);
        else if(form instanceof StoreWarrantyConfigForm) forward_page=storeWarrantyConfigFormWorker(action, form, request);
        else if(form instanceof StoreWarrantyNoteMgrForm) forward_page=storeNoteMgrFormWorker(action, form, request);
        else if(form instanceof StoreWarrantyContentMgrForm) forward_page=storeWarrantyContentMgrFormWorker(action, form, request,response);
        else if(form instanceof StoreAssetWarrantyConfigDetailForm) forward_page=storeAWConfigDetailMgrFormWorker(action, form, request,response);
        else logm("The worker of the form can't be found.Unknown form : " + form);

        logm("Forward page :" + mapping.findForward(forward_page).getPath());
        return findForward(request,mapping,forward_page);
    }

    private String storeAWConfigDetailMgrFormWorker(String action, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ActionErrors ae;
        MessageResources mr = getResources(request);
        String init   = "init";
        String detail = "detail";
        String createNew = getMessage(mr, request, "admin.button.create");
        String saveStr   = getMessage(mr, request, "global.action.label.save");
        String deleteStr = getMessage(mr, request, "global.action.label.delete");

        if(init.equals(action)) {
            ae=StoreWarrantyMgrLogic.init(request, (StoreAssetWarrantyConfigDetailForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if(createNew.equals(action)) {
            ae=StoreWarrantyMgrLogic.createNewAssetWarrantyLink(request, (StoreAssetWarrantyConfigDetailForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if(detail.equals(action)) {
            ae=StoreWarrantyMgrLogic.getAssetWarrantyDetails(request, null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if(saveStr.equals(action)) {
            ae=StoreWarrantyMgrLogic.updateAssetWarrantyLink(request, (StoreAssetWarrantyConfigDetailForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }  else if(deleteStr.equals(action)) {
            ae=StoreWarrantyMgrLogic.removeAssetWarrantyLink(request, (StoreAssetWarrantyConfigDetailForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        return SUCCESS;
    }

    private ActionForward findForward(HttpServletRequest request, ActionMapping mapping, String forward_page) {
        if(request.getParameter(RET_CD)!=null){
            return mapping.findForward(request.getParameter(RET_CD));
        } else{
            return mapping.findForward(forward_page);
        }
    }

    private String storeWarrantyContentMgrFormWorker(String action, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionErrors ae;
        MessageResources mr = getResources(request);
        String createNew    = getMessage(mr, request, "admin.button.create");
        String saveStr      = "save";
        String init         = "init";
        String initSearch   = "initSearch";
        String findContent  = "findContent";
        String detail       = "detail";
        String readDocs     = "readDocs";
        String removeStr    = "remove";

        if (init.equals(action)) {
            ae = StoreWarrantyMgrLogic.init(request, (StoreWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if (detail.equals(action)) {
            ae = StoreWarrantyMgrLogic.getContentDetail(request, (StoreWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (createNew.equals(action)) {
            ae = StoreWarrantyMgrLogic.creteNewContent(request, (StoreWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (findContent.equals(action)) {
            ae = StoreWarrantyMgrLogic.findContent(request, (StoreWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if (initSearch.equals(action)) {
            ae = StoreWarrantyMgrLogic.findContent(request, (StoreWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if (saveStr.equals(action)) {
            ae = StoreWarrantyMgrLogic.updateContentData(request, (StoreWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (removeStr.equals(action)) {
            ae = StoreWarrantyMgrLogic.removeWarrantyContent(request, (StoreWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (readDocs.equals(action)) {
            ae = StoreWarrantyMgrLogic.readDocument(request,response, (StoreWarrantyContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return SUCCESS;
    }

    private String storeNoteMgrFormWorker(String action, ActionForm form, HttpServletRequest request) {

        ActionErrors ae;
        MessageResources mr = getResources(request);
        String searchStr    = getMessage(mr, request, "global.action.label.search");
        String deleteStr    = getMessage(mr, request, "global.action.label.delete");
        String saveStr      = getMessage(mr, request, "global.action.label.save");
        String detail       = "detail";
        String createNewStr = "create";
        String initSearch   = "initSearch";

        if (initSearch.equals(action)) {
            ae = StoreWarrantyMgrLogic.init(request, (StoreWarrantyNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if (searchStr.equals(action)) {
            ae = StoreWarrantyMgrLogic.noteSearch(request, (StoreWarrantyNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if (detail.equals(action)) {
            ae = StoreWarrantyMgrLogic.getNoteDetail(request, (StoreWarrantyNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (deleteStr.equals(action)) {
            ae = StoreWarrantyMgrLogic.removeWarrantyNote(request, (StoreWarrantyNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SEARCH;
        } else if (saveStr.equals(action)) {
            ae = StoreWarrantyMgrLogic.updateWarrantyNote(request, (StoreWarrantyNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (createNewStr.equals(action)) {
            ae = StoreWarrantyMgrLogic.createNewWarrantyNote(request, (StoreWarrantyNoteMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return SUCCESS;
    }

    private String storeWarrantyConfigFormWorker(String action, ActionForm form, HttpServletRequest request) {

        ActionErrors ae;
        MessageResources mr = getResources(request);
        String changeActiveAssetCategory    = "changeActiveAssetCategory";
        String configStr    = "config";
        String saveStr      = getMessage(mr, request, "global.action.label.save");
        String initConfig   = "initConfig";
        String assetWarrantyConfigDetails = "assetWarrantyConfigDetails";
        if (action.equals(configStr)) {
            ae = StoreWarrantyMgrLogic.getWarrantyConfiguration(request, (StoreWarrantyConfigForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if(initConfig.equals(action)) {
            ae=StoreWarrantyMgrLogic.init(request, (StoreWarrantyConfigForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if(assetWarrantyConfigDetails.equals(action)) {
            ae=StoreWarrantyMgrLogic.getAssetWarrantyDetails(request, null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return AWC_DETAIL;
        } else if(saveStr.equals(action)) {
            ae=StoreWarrantyMgrLogic.updateAssetWarrantyConfig(request, (StoreWarrantyConfigForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if(changeActiveAssetCategory.equals(action)) {
            ae=StoreWarrantyMgrLogic.getWarrantyConfiguration(request, (StoreWarrantyConfigForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return SUCCESS;
    }

    private String storeWarrantyDetailFormWorker(String action, ActionForm form, HttpServletRequest request) {

        ActionErrors ae;
        MessageResources mr = getResources(request);
        String saveStr = getMessage(mr, request, "global.action.label.save");
        String detail  = "detail";

        if(action.equals(saveStr)) {
            ae=StoreWarrantyMgrLogic.updateWarranty(request, (StoreWarrantyDetailForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if(action.equals(detail)) {
            ae=StoreWarrantyMgrLogic.getDetail(request, (StoreWarrantyDetailForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else {
            ae=StoreWarrantyMgrLogic.init(request, (StoreWarrantyDetailForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
    }

    private String storeWarrantyMgrFormWorker(String action, ActionForm form, HttpServletRequest request) throws Exception {

        ActionErrors ae;
        MessageResources mr = getResources(request);
        String searchStr = getMessage(mr, request, "global.action.label.search");
        String createStr = getMessage(mr, request, "admin.button.create");
        String init = "init";

        if (action.equals(searchStr)) {
            ae = StoreWarrantyMgrLogic.search(request, (StoreWarrantyMgrForm)form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(createStr)) {
            ae = StoreWarrantyMgrLogic.createNewWarranty(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(init)) {
            ae = StoreWarrantyMgrLogic.init(request, (StoreWarrantyMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }

        return SUCCESS;
    }
}

