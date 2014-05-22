/*
 * StoreItemMgrAction.java
 *
 * Created on March 17, 2005, 10:43 AM
 */
package com.cleanwise.view.actions;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.StoreItemMgrLogic;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.CommonsMultipartRequestHandler;

import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.StoreItemMgrForm;

/**
 *
 * @author Ykupershmidt
 */

public final class StoreItemMgrAction extends ActionSuper {


  // ------------------------------------------------------------ Public Methods


  /**
   * Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or <code>null</code> if the response has
   * already been completed.
   *
   * @param mapping
   *  The ActionMapping used to select this instance
   * @param form
   *  The optional ActionForm bean for this request (if any)
   * @param request
   *  The HTTP request we are processing
   * @param response
   *  The HTTP response we are creating
   *
   * @exception IOException
   *  if an input/output error occurs
   * @exception ServletException
   *  if a servlet exception occurs
   */
  public ActionForward performSub(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    StoreItemMgrForm sForm = (StoreItemMgrForm) form;
    // Get the action and the catalogId from the request.
    String action = request.getParameter("action");
    sForm.setAction(action);

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if (st.checkSession() == false) {
      return mapping.findForward("/userportal/logon");
    }

    String mappingAction = "success";
    ActionErrors ae = new ActionErrors();

    try {

        //"CreateNotMatchedItem";
        //"StagedCreateAll";
        
      //main form
      boolean isUseItemsLinksBetweenStores = StoreItemMgrLogic.getUseItemsLinksBetweenStores(request);
      if (action == null) {
        ae = StoreItemMgrLogic.init(request, sForm);
      } else if (action.equals("Search")) {
        ae = StoreItemMgrLogic.searchForStoreItems(request, sForm);
      } else if (action.equals("Load")) {
          ae = StoreItemMgrLogic.loadStoreItems(request, sForm);
      } else if (action.equals("SearchStagedMasterItem")) {
            ae = StoreItemMgrLogic.searchStaged(request, sForm);
      } else if (action.equals("MasterItemMatch")) {
            ae = StoreItemMgrLogic.chooseItemToMatch(request, sForm);
            if (ae.size() > 0) {
                return mapping.findForward("failure");
            }
            return mapping.findForward("master_item_match");
      } else if (action.equals("BackAssetMatch")) {
            //return mapping.findForward("success");
      } else if (action.equals("SearchStoreItemMatch")) {
            ae = StoreItemMgrLogic.searchForStoreItems(request, sForm);
            if (ae.size() > 0) {
                return mapping.findForward("failure_master_item_match");
            }
            return mapping.findForward("master_item_match");
      } else if (action.equals("MatchStagedItem")) {
            ae = StoreItemMgrLogic.matchStagedItem(request, sForm);
      } else if (action.equals("UnmatchMatchedItem")) {
            ae = StoreItemMgrLogic.unmatchMatchedItem(request, sForm);
      } else if (action.equals("CreateNotMatchedItem")) {
            ae = StoreItemMgrLogic.createNotMatchedItem(request, sForm);
      } else if (action.equals("StagedCreateAll")) {
            ae = StoreItemMgrLogic.createAllNotMatchedItems(request, sForm);
      } else if (action.equals("sort")) {
        ae = StoreItemMgrLogic.sort(request, sForm);
      } else if (action.equals("sort_staged")) {
        ae = StoreItemMgrLogic.sortStaged(request, sForm);
      } else if (action.equals("sort_match")) {
        ae = StoreItemMgrLogic.sortMatch(request, sForm);
        if (ae.size() > 0) {
            return mapping.findForward("failure_master_item_match");
        }
        return mapping.findForward("master_item_match");
      }else if (action.equals("edit")) {
    	  ae = StoreItemMgrLogic.initItem(request, sForm);
    	  if (isUseItemsLinksBetweenStores) {
    		  sForm.setRetMapping("crossStoreItemLinkSearch");
    	  }
    	  else {
    		  sForm.setRetMapping("searchItems");
    	  }
      } else if (action.equals("Save Item")) {
    	  ae = StoreItemMgrLogic.saveStoreItem(request, sForm);
      } else if (action.equals("Save Distributor Item Information")) {
        ae = StoreItemMgrLogic.saveDistItemInfo(request, sForm);
      } else if (action.equals("Save Pricing")) {
        ae = StoreItemMgrLogic.savePricing(request, sForm);
      } else if (action.equals("Create New")) {
        ae = StoreItemMgrLogic.initNew(request, sForm);
        sForm.setRetMapping("searchItems");
      } else if (action.equals("editImp")) {
        ae = StoreItemMgrLogic.checkMissingData(request, sForm);
        sForm.setRetMapping("importItems");
      } else if (action.equals("clone")) {
        ae = StoreItemMgrLogic.initClone(request, sForm);
      } else if (action.equals("editMjCategory")) {
        ae = StoreItemMgrLogic.initMjCategory(request, sForm);
      } else if (action.equals("New Major Category")) {
        ae = StoreItemMgrLogic.initNewMjCategory(request, sForm);
      } else if (action.equals("Save Major Category")) {
        ae = StoreItemMgrLogic.saveMjCategory(request, sForm);
      } else if (action.equals("Delete Major Category")) {
        ae = StoreItemMgrLogic.deleteMjCategory(request, sForm);
      } else if (action.equals("editCategory")) {
        ae = StoreItemMgrLogic.initCategory(request, sForm);
      } else if (action.equals("New Category")) {
        ae = StoreItemMgrLogic.initNewCategory(request, sForm);
      } else if (action.equals("Save Category")) {
        ae = StoreItemMgrLogic.saveCategory(request, sForm);
      } else if (action.equals("Delete Category")) {
        ae = StoreItemMgrLogic.deleteCategory(request, sForm);
      } else if (action.equals("Move To")) {
    	  if ("Confirm Move Category".equals(request.getParameter("command"))){
    		  sForm.setSelectedId(new Integer(request.getParameter("selectedId")));
    		  StoreItemMgrLogic.confirmMoveCategory(request, response, sForm);
    		  return null;
    	  }else{
    		  ae = StoreItemMgrLogic.moveCategory(request, sForm);
    	  }
      } else if (action.equals("To Top")) {
          ae = StoreItemMgrLogic.moveCategoryToTop(request, sForm);   
      } else if (action.equals("editMultiproduct")) {
          ae = StoreItemMgrLogic.initMultiproduct(request, sForm);
      } else if (action.equals("New Multi product")) {
          ae = StoreItemMgrLogic.initNewMultiproduct(request, sForm);
      } else if (action.equals("Save Multi product")) {
          ae = StoreItemMgrLogic.saveMultiproduct(request, sForm);
      } else if (action.equals("Delete Multi product")) {
          ae = StoreItemMgrLogic.deleteMultiproduct(request, sForm);          
      } else if (action.equals("Back")) {
        String retMapping = sForm.getRetMapping();
        if (retMapping != null) {
          return (mapping.findForward(retMapping));
        }
      } else if (action.equals("Delete Image")) {
        ae = StoreItemMgrLogic.deleteImage(request, sForm);
      } else if (action.equals("Delete Thumbnail")) {
        ae = StoreItemMgrLogic.deleteThumbnail(request, sForm);
      } else if (action.equals("Delete MSDS") || "Use Manufacturer MSDS Plug-in".equals(action)) {
        ae = StoreItemMgrLogic.deleteMsds(request, sForm);
      } else if (action.equals("Delete DED")) {
        ae = StoreItemMgrLogic.deleteDed(request, sForm);
      } else if (action.equals("Delete Product Spec")) {
        ae = StoreItemMgrLogic.deleteProductSpec(request, sForm);
      } else if (action.equals("itemDocumentFromE3Storage")) {
        ae = StoreItemMgrLogic.showItemDocumentFromE3Storage(request, response); 
        return null;
      } else if (action.equals("itemDocumentFromDb")) {
        ae = StoreItemMgrLogic.showItemDocumentFromDb(request, response); 
        return null;
      } else if (action.equals("Import Items")) {
        ae = StoreItemMgrLogic.initItemImport(request, sForm);
      } else if (action.equals("Return Selected")) {
            //intercept the action from the acotion super
    	  	String submitFormIdent = request.getParameter("jspSubmitIdent");
    	  	if (submitFormIdent != null &&
                submitFormIdent.indexOf("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_ITEM_FORM) == 0) {
            	if (isUseItemsLinksBetweenStores) {
            		request.setAttribute("hideLocateStoreItemScreen", "true");
            		if (StoreItemMgrLogic.getItemLinkManagedBetweenStoresFl(request, sForm)) {
            			ae = StoreItemMgrLogic.linkManagedItems(request, sForm);
                        if (ae.size() == 0) {
                        	StoreItemMgrLogic.closeItemLinkManagedBetweenStores(request,sForm);
                            return (mapping.findForward("crossStoreItemLinkEdit"));
                        } else {
                            return (mapping.findForward("failure"));
                        }
                    }
            	}
            	else {
            		if (StoreItemMgrLogic.getItemLinkManagedFl(request, sForm)) {
                        ae = StoreItemMgrLogic.linkManagedItems(request, sForm);
                        if (ae.size() == 0) {
                            StoreItemMgrLogic.closeItemLinkManaged(request,sForm);
                            return (mapping.findForward("item-detail"));
                        } else {
                            return (mapping.findForward("failure"));
                        }
                    }
                    ae=StoreItemMgrLogic.getImportedItems(request, sForm);
            	}
            }
      } else if (action.equals("Search Items")) {
    	  if (isUseItemsLinksBetweenStores) {
    		  if (StoreItemMgrLogic.getItemLinkManagedBetweenStoresFl(request, sForm)) {
    			  return (mapping.findForward("crossStoreItemLinkEdit"));
    		  }
    	  }
    	  else {
    		  if (StoreItemMgrLogic.getItemLinkManagedFl(request, sForm)) {
    			  return (mapping.findForward("item-detail"));
    		  }
    	  }
      } else if (action.equals("Cancel")) {
            //intercept the action from the acotion super
    	  	if (isUseItemsLinksBetweenStores) {
    	  		request.setAttribute("hideLocateStoreItemScreen", "true");
    	  	}
    	  	String submitFormIdent = request.getParameter("jspSubmitIdent");
            if (submitFormIdent != null &&
                submitFormIdent.equals("#" +SessionAttributes.SEARCH_FORM.LOCATE_STORE_ITEM_FORM)) {
            	if (isUseItemsLinksBetweenStores) {
            		if (StoreItemMgrLogic.getItemLinkManagedBetweenStoresFl(request, sForm)) {
	                	StoreItemMgrLogic.closeItemLinkManagedBetweenStores(request,sForm);
	                	return (mapping.findForward("crossStoreItemLinkEdit"));
	                }
                }
                else {
                	if (StoreItemMgrLogic.getItemLinkManagedFl(request, sForm)) {
                		StoreItemMgrLogic.closeItemLinkManaged(request,sForm);
	                	return (mapping.findForward("item-detail"));
	                }
                }
            }
       } else if (action.equals("cloneImported")) {
        ae = StoreItemMgrLogic.impItem(request, sForm);
        if (sForm.getProduct() != null) {
          return (mapping.findForward("item-edit"));
        }
      } else if (action.equals("editUpload")) {
        ae = StoreItemMgrLogic.editUpload(request, sForm);
        sForm.setRetMapping("uploadpage");
        if (sForm.getProduct() != null) {
          return (mapping.findForward("success"));
        }
      } else if (action.equalsIgnoreCase("Get Linked Items")) {
        ae = StoreItemMgrLogic.getLinkedItems(request, sForm);
      } else if (action.equalsIgnoreCase("Update Linked Items")) {
        ae = StoreItemMgrLogic.updateLinkedItems(request, sForm);
      } else if (action.equalsIgnoreCase("Break Managed Item Link")) {
        ae = StoreItemMgrLogic.breakManagedItemLink(request, sForm);
      } else if (action.equalsIgnoreCase("Find Items To Link")) {
    	  ae = StoreItemMgrLogic.initLinkManagedItems(request, sForm);
      } else if (action.equalsIgnoreCase("Link Items")) {
        ae = StoreItemMgrLogic.linkManagedItems(request, sForm);
        if (ae.size() == 0) {
          return (mapping.findForward("item-detail"));
        } else {
          return (mapping.findForward("failure"));
        }
      }
      	else if (action.equalsIgnoreCase("Unlink Items")) {
        	ae = StoreItemMgrLogic.unlinkManagedItem(request, sForm);
        	if (isUseItemsLinksBetweenStores) {
        		if (ae.size() == 0) {
        			return (mapping.findForward("crossStoreItemLinkEdit"));
        		} 
        		else {
        			return (mapping.findForward("failure"));
        		}
        	}
        	else {
        		if (ae.size() == 0) {
        			return (mapping.findForward("item-detail"));
        		} 
        		else {
        			return (mapping.findForward("failure"));
        		}
        	}
      	} else if (action.equalsIgnoreCase("QRcode")) {
            ae = StoreItemMgrLogic.getQRcode(request, response, sForm);
            if (ae.size() > 0) {
                return (mapping.findForward("failure"));
            } else {
                return null;
            }
        } else {
        }
    } catch (Exception e) {
    	request.setAttribute(Constants.EXCEPTION_OBJECT, e);
        e.printStackTrace();        
        return mapping.findForward("error");
    }

    if (ae.size() > 0) {
      saveErrors(request, ae);
      return (mapping.findForward("failure"));
    }
    return (mapping.findForward(mappingAction));

  }
}
