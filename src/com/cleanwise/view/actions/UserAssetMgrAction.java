package com.cleanwise.view.actions;

import com.cleanwise.service.api.util.RefCodeNames;
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
import com.cleanwise.view.logic.UserAssetMgrLogic;


/**
 * Title:        UserAssetMgrAction
 * Description:  Actions manager for the asset processing in the USERPORTAL .
 * Purpose:      Class for calling logic methods  which  process the request
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         02.01.2006
 * Time:         12:15:33
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class UserAssetMgrAction extends ActionSuper {
    private static final String FAILURE    = "failure";
    private static final String MAIN       = "main";
    private static final String SUCCESS    = "success";
    private static final String DISPLAY    = "display";
    private static final String ERROR      = "error";
    private static final String DETAIL     = "detail";
    private static final String CONTENT_DETAIL  = "content_detail";
    private static final String WARRANTY_DETAIL = "warranty_detail";
    private static final String WO_DETAIL = "wo_detail";
    private static final String WARRANTY_CONFIG = "warranty_config";
    private static final String MASTER_ASSET = "master_asset";
    private static final String CATEGORY = "category";
    private static final String MASTER_ASSET_MATCH = "master_asset_match";

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
            actionForward = runWorkerForm(action, form, request, mapping, response);
        } catch (Exception e) {
            e.printStackTrace();
            actionForward = mapping.findForward(FAILURE);
        }

        navigateBreadCrumb(request, actionForward);

        return actionForward;
    }

    private ActionForward runWorkerForm(String action,
                                        ActionForm form,
                                        HttpServletRequest request,
                                        ActionMapping mapping,
                                        HttpServletResponse response) {

        String forward_page = MAIN;
        logm("action : " + action + " form : " + form + " mapping attribute : " + mapping.getAttribute());
        try {
            if (form instanceof UserAssetMgrForm) forward_page = userAssetMgrFormWorker(action, form, request);
            else if (form instanceof UserAssetProfileMgrForm) forward_page = profileFormWorker(action, form, request, response);
            else if (form instanceof UserAssetContentMgrForm) forward_page = userAssetContentFormWorker(action, form, request, response);
            else logm("The worker of the form can't be found.Unknown form : " + form);
        }
        catch (Exception e) {
            logm("ERROR");
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            return mapping.findForward(ERROR);
        }
        if (forward_page == null) {
            return null;
        }
        logm("Forward page :" + mapping.findForward(forward_page).getPath());

        return mapping.findForward(forward_page);

    }


    private String userAssetContentFormWorker(String action, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionErrors ae;
        MessageResources mr = getResources(request);
        String saveStr      = getMessage(mr, request, "global.action.label.save");
        String remove       = getMessage(mr, request, "global.action.label.delete");
        String init         = "init";
        String detail       = "detail";
        String readDocs     = "readDocs";
        String createNew    = getMessage(mr, request, "global.label.addContent");

        if (init.equals(action)) {
            ae = UserAssetMgrLogic.init(request, (UserAssetContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (detail.equals(action)) {
            ae = UserAssetMgrLogic.getContentDetail(request, (UserAssetContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (createNew.equals(action)) {
            ae = UserAssetMgrLogic.creteNewContent(request, (UserAssetContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (saveStr.equals(action)) {
            ae = UserAssetMgrLogic.updateContentData(request, (UserAssetContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (readDocs.equals(action)) {
            ae = UserAssetMgrLogic.readDocument(request,response, (UserAssetContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (remove.equals(action)) {
            ae = UserAssetMgrLogic.removeAssetContent(request, (UserAssetContentMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return DETAIL;
    }

    private String profileFormWorker(String action,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

        ActionErrors ae;
        MessageResources mr = getResources(request);

        String saveStr       = getMessage(mr, request, "global.action.label.save");
        String init          = "init";
        String detail        = "assetdetail";
        String createContent = getMessage(mr, request, "global.label.addContent");
        String searchStr     = "Search";
        String retSelected   = "Return Selected";
        String createNewWarranty  = "createNewWarranty";
        String configAssetWarranty = "configAssetWarranty";
        String createNewWorkOrder = "createNewWorkOrder";
        String saveAssetWarrantyConfig = "saveAssetWarrantyConfig";
        String saveMasterAsset = "saveMasterAsset";
        String saveStoreMasterAsset = "saveStoreMasterAsset";
        String masterAssetDetail = "masterAssetDetail";
        String saveAssetCategory = "saveAssetCategory";
        String deleteAssetCategory = "deleteAssetCategory";
        String assetCategoryDetail = "assetCategoryDetail";
        String storeMasterAssetDetail = "storeMasterAssetDetail";
        String storeMasterAssetCategoryDetail = "storeMasterAssetCategoryDetail";

        if (action.equals(detail)) {
            ae = UserAssetMgrLogic.getAssetProfile(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(masterAssetDetail)) {
            ae = UserAssetMgrLogic.getAssetProfile(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return MASTER_ASSET;
        } else if (action.equals(storeMasterAssetDetail)) {
            ae = UserAssetMgrLogic.getStoreMasterAssetProfile(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(storeMasterAssetCategoryDetail)) {
            ae = UserAssetMgrLogic.getStoreMasterAssetProfile(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(assetCategoryDetail)) {
            ae = UserAssetMgrLogic.getAssetProfile(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return CATEGORY;
        } else if (action.equals(createContent)) {
            ae = UserAssetMgrLogic.creteNewContent(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return CONTENT_DETAIL;
        } else if (action.equals(searchStr) || action.equals(retSelected)) {
            String submitFormIdent = request.getParameter("jspSubmitIdent");
            if (("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_SITE_FORM).equals(submitFormIdent)) {
                if (action.equals(searchStr)) {
                    ae = UserAssetMgrLogic.locateSite(request, (UserAssetProfileMgrForm) form, response);
                    if (ae.size() > 0) {
                        saveErrors(request, ae);
                    }
                } else if (action.equals(retSelected)) {
                    ae = UserAssetMgrLogic.updateLocation(request, (UserAssetProfileMgrForm) form, response);
                    if (ae.size() > 0) {
                        saveErrors(request, ae);
                    }
                }
                return null;
            }
        } else if (action.equals(createNewWarranty)) {
            ae = UserAssetMgrLogic.createNewWarranty(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return WARRANTY_DETAIL;
        } else if (action.equals(configAssetWarranty)) {
            return WARRANTY_CONFIG;
        } else if (action.equals(saveAssetWarrantyConfig)) {
            ae = UserAssetMgrLogic.saveAssetWarrantyConfig(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(createNewWorkOrder)) {
            ae = UserAssetMgrLogic.createNewWorkOrder(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return WO_DETAIL;
        } else if (action.equals(saveStr)) {
            ae = UserAssetMgrLogic.saveAsset(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(saveMasterAsset)) {
            ae = UserAssetMgrLogic.saveMasterAsset(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return MASTER_ASSET;
            }
            return MASTER_ASSET;
        } else if (action.equals(saveAssetCategory)) {
            ae = UserAssetMgrLogic.saveAssetCategory(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return CATEGORY;
            }
            return CATEGORY;
        } else if (action.equals(deleteAssetCategory)) {
            ae = UserAssetMgrLogic.deleteAssetCategory(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return CATEGORY;
            }
            return SUCCESS;
        } else if (action.equals(saveStoreMasterAsset)) {
            ae = UserAssetMgrLogic.saveStoreMasterAsset(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return MASTER_ASSET;
            }
            return MASTER_ASSET;
        } else if (action.equals(init)) {
            ae = UserAssetMgrLogic.init(request, (UserAssetProfileMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return DISPLAY;
    }

    private String userAssetMgrFormWorker(String action, ActionForm form, HttpServletRequest request) throws Exception {

        ActionErrors ae;
        MessageResources mr             = getResources(request);
        String searchStr                = "SearchAsset";
        String searchStoreAssetStr      = "SearchStoreAsset";
        String assetCreate              = "AssetCreate";
        String masterAssetCreate        = "MasterAssetCreate";
        String categoryCreate           = "CategoryCreate";
        String managedAssetCreate       = "ManagedAssetCreate";
        String init                     = "init";
        String sort_assets              = "sort_assets";
        String sort_assets_match        = "sort_assets_match";
        String sort_staged_assets       = "sort_staged_assets";
        String searchStagedStr          = "SearchStagedMasterAsset";
        String masterAssetMatchStr      = "MasterAssetMatch";
        String searchStoreAssetMatchStr = "SearchStoreAssetMatch";
        String backAssetMatchStr        = "BackAssetMatch";
        String createNotMatchedAssetStr = "CreateNotMatchedAsset";
        String unmatchMatchedAssetStr   = "UnmatchMatchedAsset";
        String matchStagedAssetStr      = "MatchStagedAsset";
        String stagedCreateAllStr       = "StagedCreateAll";
        String loadMasterAssetStr       = "LoadMasterAsset";
        String loadPhysicalAssetStr     = "LoadPhysicalAsset";

        if (action.equals(searchStr)) {
            ae = UserAssetMgrLogic.search(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(searchStoreAssetStr)) {
            ae = UserAssetMgrLogic.storePortalSearch(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(loadMasterAssetStr)) {
            ae = UserAssetMgrLogic.loadMasterAsset(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }  else if (action.equals(loadPhysicalAssetStr)) {
            ae = UserAssetMgrLogic.loadPhysicalAsset(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(searchStoreAssetMatchStr)) {
            ae = UserAssetMgrLogic.storePortalSearch(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return MASTER_ASSET_MATCH;
        } else if (action.equals(searchStagedStr)) {
            ae = UserAssetMgrLogic.searchStaged(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(init)) {
            ae = UserAssetMgrLogic.init(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(assetCreate)) {
            ((UserAssetMgrForm) form).setAssetType(RefCodeNames.ASSET_TYPE_CD.ASSET);
            ae = UserAssetMgrLogic.createNewAsset(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(masterAssetCreate)) {
            //((UserAssetMgrForm) form).setAssetType(RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET);
            ae = UserAssetMgrLogic.createNewAsset(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return MASTER_ASSET;
        } else if (action.equals(categoryCreate)) {
            //((UserAssetMgrForm) form).setAssetType(RefCodeNames.ASSET_TYPE_CD.CATEGORY);
            ae = UserAssetMgrLogic.createNewAsset(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return CATEGORY;
        } else if (action.equals(managedAssetCreate)) {
            ae = UserAssetMgrLogic.createNewManagedAsset(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (action.equals(masterAssetMatchStr)) {
            ae = UserAssetMgrLogic.chooseAssetToMatch(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return MASTER_ASSET_MATCH;
        } else if (action.equals(backAssetMatchStr)) {
            return SUCCESS;
        } else if (action.equals(createNotMatchedAssetStr)) {
            ae = UserAssetMgrLogic.createNotMatchedAsset(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(stagedCreateAllStr)) {
            ae = UserAssetMgrLogic.createAllNotMatchedAssets(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(unmatchMatchedAssetStr)) {
            ae = UserAssetMgrLogic.unmatchMatchedAsset(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(matchStagedAssetStr)) {
            ae = UserAssetMgrLogic.matchStagedAsset(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(sort_assets)) {
            ae = UserAssetMgrLogic.sort(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(sort_staged_assets)) {
            ae = UserAssetMgrLogic.sortStaged(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(sort_assets_match)) {
            ae = UserAssetMgrLogic.sortMatch(request, (UserAssetMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return MASTER_ASSET_MATCH;
        }
        return DISPLAY;
    }
}
