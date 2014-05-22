
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.logic.ItemMgrSearchLogic;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.ItemMgrSearchForm;
import com.cleanwise.view.forms.CatalogMgrDetailForm;

import java.util.Enumeration;
/**
 * Implementation of <strong>Action</strong> that saves a new
 * catalog detail or updates an existing catalog detail.
 */
public final class ItemMgrSearchAction extends ActionSuper {


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
   * @param actionForm
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
                 HttpServletResponse response)
                 throws IOException, ServletException {

    // Get the action and the catalogId from the request.
    String action  = request.getParameter("action");
    HttpSession session = request.getSession();

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }   
    

    CatalogMgrDetailForm detailForm = 
	(CatalogMgrDetailForm) session.getAttribute("CATALOG_DETAIL_FORM");
    if(detailForm == null || detailForm.getDetail() == null || 
       detailForm.getDetail().getCatalogId()==0) {
	ActionErrors ae = new ActionErrors();
	ae.add("error",new ActionError("catalog.structure.no_catalog_loaded"));
	saveErrors(request, ae);
	return (mapping.findForward("init"));
    }

    int detailFormCatalogId = detailForm.getDetail().getCatalogId();

    ItemMgrSearchForm sForm = (ItemMgrSearchForm) form;
    int thisFormCatalogId = sForm.getCatalogId();

    String outService = sForm.getOutServiceName();
    if (action == null && outService!=null && 
	outService.equalsIgnoreCase("distributorAssign")) {
        action = "addFromLookup";
        sForm.setOutServiceName("");
    }

    try {
      if(action==null || thisFormCatalogId!=detailFormCatalogId) {
        ItemMgrSearchLogic.init(request,sForm);
      }

      if(action==null) {

      } 
      else if(action.equals("Add category to item(s).")) {
	  ItemMgrSearchLogic.addCategoryToItem(request,sForm);
      } 
      else if(action.indexOf("to Distributor 0") >= 0) {
	  ItemMgrSearchLogic.resetItemDistributor(request,sForm);
      } 
      else if(action.equals("rm_item_category")) {
	  ItemMgrSearchLogic.removeCategoryFromItem(request,sForm);
      } 
      else if(action.equals("Search")) {
	  //Search for Items
	  ActionErrors ae = ItemMgrSearchLogic.searchForItem(request,sForm);
          if(ae.size()>0) {
             saveErrors(request, ae);
          }
          
      }

      //Refresh the list
      else if(action.equals("masterItemDone")) {
        ItemMgrSearchLogic.refreshListOfItems(request,sForm);
      }
      //Return to catalog structure
      else if(action.equals("Return")) {
         return (mapping.findForward("return"));
      }
      //Show selected item on the catalog tree
      else if(action.equals("Find In Catalog")) {
        ActionErrors ae=ItemMgrSearchLogic.prepareToShow(request,sForm);
		if (ae.size() > 0) {
          saveErrors(request, ae); 
	  return new ActionForward(mapping.getInput());
        }
        return (mapping.findForward("show"));
      }

      //Remove from catalog
      else if(action.equals("Remove From Catalog")) {
        ActionErrors ae=ItemMgrSearchLogic.removeFromCatalog(request,sForm);
		if (ae.size() > 0) {
          saveErrors(request, ae); 
	  return new ActionForward(mapping.getInput());
        }
      }
      //Create new item
      else if (action.equals("Create New")) {
        ActionForward af = mapping.findForward("itemmasterNew");
        String adr=af.getPath();
        adr+="&retaction=itemsearch";
        ActionForward af1=new ActionForward (adr);
        af1.setRedirect(af.getRedirect());
        return (af1);
      }

      //Add to catalog
      else if(action.equals("addFromLookup")) {
        ActionErrors ae=ItemMgrSearchLogic.addFromLookup(request,sForm);
		if (ae.size() > 0) {
          saveErrors(request, ae); 
        return (mapping.findForward("init"));
        }
      }

      //Add to catalog
      else if(action.equals("masterItemSaved")) {
        ActionErrors ae=ItemMgrSearchLogic.tieNewItem(request,sForm);
		if (ae.size() > 0) {
          saveErrors(request, ae); 
	  return new ActionForward(mapping.getInput());
        }
      }

      // Sort the item results
      else if (action.equals("sort")) {
	  ItemMgrSearchLogic.sort(request, sForm);
      }

      //Default action
      else {
        session.setAttribute("item.show",sForm.getSelectedProductIds());
        return (mapping.findForward("show"));
      }



    // Catch all exceptions here.
    } catch (Exception e) {
      request.setAttribute("errorobject", e);
      e.printStackTrace();
      return (mapping.findForward("error"));
    }

    return (mapping.findForward("init"));

  }


}
