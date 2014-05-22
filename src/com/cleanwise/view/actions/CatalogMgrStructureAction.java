
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
import com.cleanwise.view.logic.CatalogMgrStructureLogic;
import com.cleanwise.view.utils.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.forms.CatalogMgrStructureForm;
import com.cleanwise.view.forms.CatalogMgrDetailForm;
import com.cleanwise.service.api.util.RefCodeNames;


import java.util.Enumeration;
/**
 * Implementation of <strong>Action</strong> that saves a new
 * catalog detail or updates an existing catalog detail.
 */
public final class CatalogMgrStructureAction extends ActionSuper {


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

    CatalogMgrDetailForm detailForm = (CatalogMgrDetailForm) session.getAttribute("CATALOG_DETAIL_FORM");
    if(detailForm == null || detailForm.getDetail() == null || detailForm.getDetail().getCatalogId()==0) {
      ActionErrors ae = new ActionErrors();
      ae.add("error",new ActionError("catalog.structure.no_catalog_loaded"));
      saveErrors(request, ae);
      return (mapping.findForward("init"));
    }

    int detailFormCatalogId = detailForm.getDetail().getCatalogId();

    CatalogMgrStructureForm sForm = (CatalogMgrStructureForm) form;
    int thisFormCatalogId = sForm.getCatalogId();
    if(action==null) {
      action = "init";
    }

//    CatalogMgrStructureLogic.setNewFrontBox(form);
    try {
      //First start (init)
      if("init".equals(action)||thisFormCatalogId!=detailFormCatalogId) {
        sForm.setCatalog(detailForm.getDetail());
        sForm.setStoreId(detailForm.getStoreId());
        sForm.setStoreName(detailForm.getStoreName());

        //Pickup top level categories and products
        CatalogMgrStructureLogic.initCatalogTree(request,form);
        CatalogMgrStructureLogic.clearBackBox(form);
        sForm.setWrkField("");
        //Set loaded form flag
        sForm.setCatalogId(detailFormCatalogId);
      }

      //expand or collapse node
      if (action.equals("node")) {
        String[] sss;
        sss=request.getParameterValues("nodeId");
        if(sss.length==0) {
        } else {
          int treeIndex=Integer.parseInt(sss[0]);
          CatalogMgrStructureLogic.nodeExpCol(request, form, treeIndex);
        }
        sForm.setWrkField("");
        CatalogMgrStructureLogic.clearBackBox(form);
      }

      //delete subtree
      if (action.equals("Delete")) {
        ActionErrors ae = CatalogMgrStructureLogic.deleteSubTree(request, form);
		if (ae.size() > 0 ) {
  	      saveErrors(request, ae);
		}
        sForm.setWrkField("");
        CatalogMgrStructureLogic.clearBackBox(form);
      }

      //add category
      if (action.equals("Add")) {
        ActionErrors ae = CatalogMgrStructureLogic.addCategory(request, form);
		if (ae.size() > 0 ) {
  	      saveErrors(request, ae);
		} else {
        sForm.setWrkField("");
        sForm.setMajorCategoryId(0);
                }
      }

      //prepare category to edit
      if (action.equals("Edit")) {
        ActionErrors ae = CatalogMgrStructureLogic.prepareToEdit(request, form);
		if (ae.size() > 0 ) {
  	      saveErrors(request, ae);
		}
        if(sForm.getForwardParam()!=null) {
          ActionForward af =
            (sForm.getCatalog().getCatalogTypeCd().equals(RefCodeNames.CATALOG_TYPE_CD.SYSTEM))?
            mapping.findForward("itemmasterEdit"):
            mapping.findForward("itemcatalogEdit");
          String adr=af.getPath();
          adr+="&retaction=catalogstructure&"+sForm.getForwardParam();
          ActionForward af1=new ActionForward (adr);
          af1.setRedirect(af.getRedirect());
          return (af1);
        }
        CatalogMgrStructureLogic.clearBackBox(form);
      }
      //replace category
      if (action.equals("Replace")) {
        ActionErrors ae = CatalogMgrStructureLogic.replaceCategory(request, form);
		if (ae.size() > 0 ) {
  	      saveErrors(request, ae);
		} else {
        sForm.setWrkField("");
        sForm.setMajorCategoryId(0);
                }
        CatalogMgrStructureLogic.clearBackBox(form);
      }

      //copy subtree
      if (action.equals("Copy")) {
        ActionErrors ae = CatalogMgrStructureLogic.copySubTree(request, form);
		if (ae.size() > 0 ) {
  	      saveErrors(request, ae);
		}
        sForm.setWrkField("");
        sForm.setMajorCategoryId(0);
        CatalogMgrStructureLogic.clearBackBox(form);
      }

      //move subtree
      if (action.equals("Move")) {
        ActionErrors ae = CatalogMgrStructureLogic.moveSubTree(request, form);
		if (ae.size() > 0 ) {
  	      saveErrors(request, ae);
		}
        sForm.setWrkField("");
        sForm.setMajorCategoryId(0);
        CatalogMgrStructureLogic.clearBackBox(form);
      }
      //find item
      if (action.equals("Find Item")) {
        return (mapping.findForward("itemsearch"));
      }

      //create new master item
      if (action.equals("Create Item")) {
        ActionForward af = mapping.findForward("itemmasterNew");
        String adr=af.getPath();
        adr+="&retaction=catalogstructure";
        ActionForward af1=new ActionForward (adr);
        af1.setRedirect(af.getRedirect());
        return (af1);
      }

      //Expand all branches of the tree
      if (action.equals("Expand/Collapse Tree")) {
	  String cState = (String)request.getSession().
	      getAttribute("tree.state");
	  if ( null == cState || cState.equals("collapsed")) {
	      CatalogMgrStructureLogic.expandTree(request,sForm);
	      request.getSession().setAttribute("tree.state", "expanded");
	  }
	  else {
	      CatalogMgrStructureLogic.initCatalogTree(request,form);
	      CatalogMgrStructureLogic.clearBackBox(form);
	      request.getSession().setAttribute("tree.state", "collapsed");
	      sForm.setWrkField("");

	  }
      }

      //tie new product to category if newly created
      if(action.equals("masterItemSaved")) {
        ActionErrors ae = CatalogMgrStructureLogic.tieNewItem(request, form);
		if (ae.size() > 0 ) {
  	      saveErrors(request, ae);
		}
      }

      if(action.equals("showOnTree")) {
         CatalogMgrStructureLogic.expandForProduct(request, form);
      }


      if(action.equals("refresh")) {
        CatalogMgrStructureLogic.refresh(request, form);
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
