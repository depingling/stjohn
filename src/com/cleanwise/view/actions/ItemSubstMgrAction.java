
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
import com.cleanwise.view.logic.ItemSubstMgrLogic;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.ItemSubstMgrForm;
import com.cleanwise.view.forms.ContractMgrDetailForm;

import java.util.Enumeration;
/**
 * Implementation of <strong>Action</strong> that processes item substitutions.
 */
public final class ItemSubstMgrAction extends ActionSuper {


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
    ItemSubstMgrForm sForm = (ItemSubstMgrForm) form;

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }
    ContractMgrDetailForm detailForm =
      (ContractMgrDetailForm) session.getAttribute("CONTRACT_DETAIL_FORM");
    if(detailForm == null || detailForm.getDetail() == null ||
      detailForm.getDetail().getCatalogId()==0) {
      ActionErrors ae = new ActionErrors();
  	  ae.add("error",new ActionError("catalog.structure.no_catalog_loaded"));
	  saveErrors(request, ae);
	  return (mapping.findForward("init"));
    }

    String outService = sForm.getOutServiceName();
    if (action == null && outService!=null &&
	    outService.equalsIgnoreCase("lookupClosed")) {
        action = "addFromLookup";
        sForm.setOutServiceName("");
    }

    int detailFormContractId = detailForm.getDetail().getContractId();
    int thisFormContractId = sForm.getContractId();
    //Init substution page
    if(action==null || thisFormContractId!=detailFormContractId) {
      ActionErrors ae = ItemSubstMgrLogic.init(request,sForm);
      if(ae.size()>0) {
        saveErrors(request,ae);
      }
      return (mapping.findForward("display"));
    }
    //Add substitutions
    if(action.equals("Add Substitutions")) {
      ActionErrors ae = ItemSubstMgrLogic.addSubstitutions(request,sForm);
      if(ae.size()>0) {
        saveErrors(request,ae);
      }
      return (mapping.findForward("display"));
    }
    //Remove substitutions
    if(action.equals("Remove Substitutions")) {
      ActionErrors ae = ItemSubstMgrLogic.removeSubstitutions(request,sForm);
      if(ae.size()>0) {
        saveErrors(request,ae);
      }
      return (mapping.findForward("display"));
    }
    //Remove substitutions
    if(action.equals("sortitems")) {
      ActionErrors ae = ItemSubstMgrLogic.sortItems(request,sForm);
      if(ae.size()>0) {
        saveErrors(request,ae);
      }
      return (mapping.findForward("display"));
    }

    //Scip all conditions if null
    if(action==null) {
      return (mapping.findForward("display"));
    //Search for Items
    } else if(action.equals("Search")) {
      ActionErrors ae = ItemSubstMgrLogic.searchForItem(request,sForm);
      if(ae.size()>0) {
        saveErrors(request,ae);
      }
      return (mapping.findForward("display"));
    }
    //Add new substitutions
    else if(action.equals("addFromLookup")) {
      ActionErrors ae=ItemSubstMgrLogic.addFromLookup(request,sForm);
	  if (ae.size() > 0) {
        saveErrors(request, ae);
      }
      return (mapping.findForward("display"));
    }
    return (mapping.findForward("display"));

  }


}
