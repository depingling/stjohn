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
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.forms.StoreStoreMgrDetailForm;
import com.cleanwise.view.forms.StoreStoreMgrSearchForm;
import com.cleanwise.view.forms.StoreUIConfigForm;
import com.cleanwise.view.logic.StoreItemMgrLogic;
import com.cleanwise.view.logic.StoreStoreMgrLogic;
import com.cleanwise.view.logic.StoreUIConfigLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.SessionTool;

/**
 *
 * @author Alexander Chikin
 * Date: 28.09.2006
 * Time: 1:54:53
 * Implementation of <strong>Action</strong> that processes the Store manager
 * page.
 */
public class StoreStoreMgrAction extends ActionSuper {
    private static final Logger log = Logger.getLogger(StoreStoreMgrAction.class);

  private static final String SUCCESS = "success";
  private static final String FAILURE = "failure";
  private static final String MAIN = "main";
  private static final String DETAIL = "detail";
  // ----------------------------------------------------- Public Methods

  /**
   *  Process the specified HTTP request, and create the corresponding HTTP
   *  response (or forward to another web component that will create it).
   *  Return an <code>ActionForward</code> instance describing where and how
   *  control should be forwarded, or <code>null</code> if the response has
   *  already been completed.
   *
   *@param  mapping               The ActionMapping used to select this
   *      instance
   *@param  request               The HTTP request we are processing
   *@param  response              The HTTP response we are creating
   *@param  form                  Description of Parameter
   *@return                       Description of the Returned Value
   *@exception  java.io.IOException       if an input/output error occurs
   *@exception  javax.servlet.ServletException  if a servlet exception occurs
   */
  public ActionForward performSub(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if (st.checkSession() == false) {
      return mapping.findForward(st.getLogonMapping());
    }

    // Determine the store manager action to be performed
    String action = request.getParameter("action");
    if (action == null) {
      CleanwiseUser appUser = st.getUserData();
      String userType = appUser.getUser().getUserTypeCd();
      if (userType.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR)) {
        action = "init";
      } else {
        action = "storedetail";
      }
    }

    try {
      if (action.equals("itemDocumentFromE3Storage")) {
        StoreItemMgrLogic.showItemDocumentFromE3Storage(request, response); 
        return null;  
      } else if (action.equals("itemDocumentFromDb")) {
    	StoreItemMgrLogic.showItemDocumentFromDb(request, response); 
        return null; 
      }
    } catch (Exception e) {
        e.printStackTrace();
        return mapping.findForward(FAILURE);
    }
    
    try {
      ActionForward actionForward = callHandlerForm(action, form, request, mapping);
      return actionForward;
    } catch (Exception e) {
      e.printStackTrace();
      return mapping.findForward(FAILURE);
    }

  }

  private ActionForward callHandlerForm(String action, ActionForm form, HttpServletRequest request, ActionMapping mapping) throws
    Exception {

    log.info("Run action method.....");
    String forward_page = "main";
    if (form instanceof StoreStoreMgrSearchForm) {
      forward_page = searchStoreAction(action, form, request);
    } else if (form instanceof StoreStoreMgrDetailForm) {
      forward_page = detailStoreAction(action, form, request);
    } else if (form instanceof StoreUIConfigForm) {
      forward_page = storeUIConfigAction(action, form, request);
    }
    return mapping.findForward(forward_page);
  }

  private String storeUIConfigAction(String action, ActionForm form, HttpServletRequest request) throws Exception {

    MessageResources mr = getResources(request);
    // Get the form buttons as specified in the properties file.
    String saveStr = getMessage(mr, request, "global.action.label.save");
    String cancelStr = getMessage(mr, request, "global.action.label.cancel");
    String previewStr = getMessage(mr, request, "admin.button.preview");
    // Process the action

    if (action.equals("localeChange")) {
      StoreUIConfigLogic.fetchUiSettings(request, form);
    }

    else if (action.equals(cancelStr)) {
      StoreUIConfigLogic.fetchUiSettings(request, form);
    } else if (action.equals(previewStr)) {
      StoreUIConfigLogic.previewUiSettings(request, form);
      return "preview";
    } else if (action.equals(saveStr)) {
      StoreUIConfigLogic.saveUiSettings(request, form);
    /***
    } else if (action.equals("itemDocumentFromE3Storage")) {
        StoreItemMgrLogic.showItemDocumentFromE3Storage(request, response); 
        return null;  
    } else if (action.equals("itemDocumentFromDb")) {
    	StoreItemMgrLogic.showItemDocumentFromDb(request, response); 
        return null; 
    ***/
    } else {
      StoreUIConfigLogic.init(request, form);
    }

    return SUCCESS;
  }

  private String detailStoreAction(String action, ActionForm form, HttpServletRequest request) throws Exception {

    MessageResources mr = getResources(request);

    String saveStr = getMessage(mr, request, "global.action.label.save");
    String deleteStr = getMessage(mr, request, "global.action.label.delete");
    String synchronizeStr = getMessage(mr, request, "admin.button.synchronize");

    if (action.equals("updatestore") || action.equals(saveStr)) {
      ActionErrors ae = StoreStoreMgrLogic.updateStore(request, form);
      if (ae.size() > 0) {
        saveErrors(request, ae);
        return FAILURE;
      }
      return DETAIL;
    } else if (action.equals("storedetail")) {
      StoreStoreMgrLogic.getDetail(request, form);
      return SUCCESS;
    } else if (action.equals(deleteStr)) {
      ActionErrors ae = StoreStoreMgrLogic.delete(request, form);
      if (ae.size() > 0) {
        saveErrors(request, ae);
        return FAILURE;
      }
      return MAIN;
    } else if (action.equals(synchronizeStr)) {
      ActionErrors ae = StoreStoreMgrLogic.synchronizeStore(request, form);
      if (ae.size() > 0) {
        saveErrors(request, ae);
        return FAILURE;
      }
      return DETAIL;
    } else if (action.equals("initAccountFields")) {
      StoreStoreMgrLogic.fetchAccountFields(request, form);
      return SUCCESS;
    } else if (action.equals("initMasterItemFields")) {
      StoreStoreMgrLogic.fetchMasterItemFields(request, form);
      return SUCCESS;
    } else if (action.equals("saveAccountFields")) {
      StoreStoreMgrLogic.saveAccountFields(request, form);
      return SUCCESS;
    } else if (action.equals("saveMasterItemFields")) {
      StoreStoreMgrLogic.saveMasterItemFields(request, form);
      return SUCCESS;
    } else if (action.equals("initResourceConfig")) {
      ActionErrors ae = StoreStoreMgrLogic.initResourceConfig(request, form);
      if (ae.size() > 0) {
        saveErrors(request, ae);
        return FAILURE;
      }
    } else if (action.equals("saveMessageResources")) {
      StoreStoreMgrLogic.saveReasourceConfig(request, form);
    } else if (action.equals("searchMessageResources")) {
      ActionErrors ae = StoreStoreMgrLogic.searchMessageResources(request, form);
      if (ae.size() > 0) {
        saveErrors(request, ae);
        return FAILURE;
      }
    }else if (action.equals("initStoreProfile")) {
    	ActionErrors ae = StoreStoreMgrLogic.initStoreProfile(request, form);
    	if (ae.size() > 0) {
    		saveErrors(request, ae);
    		return FAILURE;
    	}
    }else if (action.equals("saveStoreProfile")) {
    	ActionErrors ae = StoreStoreMgrLogic.saveStoreProfile(request, form);
    	if (ae.size() > 0) {
    		saveErrors(request, ae);
    		return FAILURE;
    	}
    } else {
      StoreStoreMgrLogic.init(request, form);
    }
    return SUCCESS;
  }

  private String searchStoreAction(String action, ActionForm form, HttpServletRequest request) throws Exception {

    MessageResources mr = getResources(request);
    String searchStr = getMessage(mr, request, "global.action.label.search");
    String createStr = getMessage(mr, request, "admin.button.create");

    if (action.equals("init")) {
      StoreStoreMgrLogic.init(request, form);
      return SUCCESS;
    } else if (action.equals(searchStr)) {
      ActionErrors ae = StoreStoreMgrLogic.search(request, form);
      if (ae.size() > 0) {
        saveErrors(request, ae);
        return FAILURE;
      }
      return SUCCESS;
    } else if (action.equals("storedetail")) {
      StoreStoreMgrLogic.getDetail(request, form);
      return DETAIL;
    } else if (action.equals(createStr)) {

      StoreStoreMgrLogic.addStore(request, form);
      return DETAIL;
    } else {
      StoreStoreMgrLogic.init(request, form);
    }

    return SUCCESS;
  }
}


