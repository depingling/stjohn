package com.cleanwise.view.actions;

import com.cleanwise.view.forms.StoreAssetMgrForm;
import com.cleanwise.view.forms.StoreAssetDetailForm;
import com.cleanwise.view.forms.StoreAssetConfigForm;
import com.cleanwise.view.forms.StoreAssetContentMgrForm;
import com.cleanwise.view.logic.StoreAssetMgrLogic;
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
 * Title:        StoreAssetMgrAction
 * Description:  Actions manager for the asset processing.
 * Purpose:      Class for calling logic methods  which  process the request
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         20.11.2006
 * Time:         9:01:01
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class StoreAssetMgrAction extends ActionSuper {
    private static final String FAILURE = "failure";
    private static final String MAIN = "main";
    private static final String SUCCESS = "success";
    private static final String DETAIL = "detail";

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
    public ActionForward performSub(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
                                                            throws IOException, ServletException {

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
            actionForward = runWorkerForm(action, form, request, mapping,response);
            return actionForward;
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward(FAILURE);
        }


    }

    private ActionForward runWorkerForm(String action, ActionForm form, HttpServletRequest request, ActionMapping mapping,HttpServletResponse response) throws Exception {

        String forward_page = MAIN;
        logm("action : " + action + " form : " + form + " mapping attribute : " + mapping.getAttribute());
        if (form instanceof StoreAssetMgrForm) forward_page = storeAssetMgrFormWorker(action, form, request);
        else if(form instanceof StoreAssetDetailForm) forward_page=storeAssetDetailFormWorker(action, form, request);
        else if(form instanceof StoreAssetConfigForm) forward_page=storeAssetConfigFormWorker(action, form, request);
        else if(form instanceof StoreAssetContentMgrForm) forward_page=storeAssetContentFormWorker(action, form, request,response);

        else logm("The worker of the form can't be found.Unknown form : " + form);

        logm("Forward page :" + mapping.findForward(forward_page).getPath());
        return mapping.findForward(forward_page);
    }

    private String storeAssetContentFormWorker(String action, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionErrors ae;
        MessageResources mr = getResources(request);
        String saveStr      = "save";
        String init         = "init";
        String detail       = "detail";
        String readDocs     = "readDoc";
        String createNew    = "Create New";
        String remove        = "remove";

        if (init.equals(action)) {
            ae = StoreAssetMgrLogic.init(request, (StoreAssetContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (detail.equals(action)) {
            ae = StoreAssetMgrLogic.getContentDetail(request, (StoreAssetContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (createNew.equals(action)) {
            ae = StoreAssetMgrLogic.creteNewContent(request, (StoreAssetContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (saveStr.equals(action)) {
            ae = StoreAssetMgrLogic.updateContentData(request, (StoreAssetContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (readDocs.equals(action)) {
            ae = StoreAssetMgrLogic.readDocument(request,response, (StoreAssetContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (remove.equals(action)) {
            ae = StoreAssetMgrLogic.removeAssetContent(request, (StoreAssetContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        return DETAIL;
    }

    private String storeAssetConfigFormWorker(String action, ActionForm form, HttpServletRequest request) throws Exception {

        ActionErrors ae;
        MessageResources mr = getResources(request);
        String searchStr = getMessage(mr, request, "global.action.label.search");
        String initConfig = "initConfig";
        String detail="assetdetail";
        String congigUpdate="Configuration Update";
            if (action.equals(searchStr)) {

            ae = StoreAssetMgrLogic.getAssetConfiguration(request, (StoreAssetConfigForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;

        }  else if(action.equals(detail))
        {
            ae=StoreAssetMgrLogic.getDetail(request, form);
              if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
          return SUCCESS;
        }
        else if (action.equals(initConfig)){

            ae = StoreAssetMgrLogic.init(request, (StoreAssetConfigForm) null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        else if(action.equals(congigUpdate))
            {
                       ae = StoreAssetMgrLogic.updateAssetConfiguration(request,form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
            }
        return SUCCESS;
    }

    private String storeAssetDetailFormWorker(String action, ActionForm form, HttpServletRequest request) throws Exception {
        ActionErrors ae;
        MessageResources mr = getResources(request);
        String saveStr = getMessage(mr, request, "global.action.label.save");
        String init = "init";
        String uploadImageFile="uploadImageFile";
        String changeAssetType="changeAssetType";
        String detail="assetdetail";
        String changeAssetStatus="changeAssetStatus";

        if(action.equals(saveStr))
        {
            ae=StoreAssetMgrLogic.saveAsset(request, (StoreAssetDetailForm) form);
              if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
          return SUCCESS;
        } else if(action.equals(detail))
        {
            ae=StoreAssetMgrLogic.getDetail(request, (StoreAssetDetailForm) form);
              if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
          return SUCCESS;
        }
        else if(action.equals(uploadImageFile))
        {
            ae=StoreAssetMgrLogic.uploadImageFile(request, (StoreAssetDetailForm) form);
              if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
          return SUCCESS;
        }else if(action.equals(changeAssetStatus))
        {
            ae=StoreAssetMgrLogic.changeAssetStatus(request, (StoreAssetDetailForm) form);
              if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
          return SUCCESS;
        }
          else if(action.equals(changeAssetType))
        {
            ae=StoreAssetMgrLogic.changeAssetType(request, (StoreAssetDetailForm) form);
              if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
          return SUCCESS;
        }
        else
        {
          ae=StoreAssetMgrLogic.init(request, (StoreAssetDetailForm) form);
              if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
          return SUCCESS;
        }
    }

    private String storeAssetMgrFormWorker(String action, ActionForm form, HttpServletRequest request) throws Exception {

        ActionErrors ae;
        MessageResources mr = getResources(request);
        String searchStr = getMessage(mr, request, "global.action.label.search");
        String createStr = getMessage(mr, request, "admin.button.create");
        String init = "init";
        String detail = "assetdetail";
        String locateSearch = "locateSearch";
        if (action.equals(searchStr)) {

            ae = StoreAssetMgrLogic.search(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;

        } else if (action.equals(locateSearch)) {
            ae = StoreAssetMgrLogic.locate(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(detail)) {
            ae = StoreAssetMgrLogic.getDetail(request, (StoreAssetDetailForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(createStr)) {
            ae = StoreAssetMgrLogic.createNewAsset(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(init)) {

            ae = StoreAssetMgrLogic.init(request, (StoreAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }

        return SUCCESS;
    }
}
