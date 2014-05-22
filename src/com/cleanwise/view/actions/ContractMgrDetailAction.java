
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
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.logic.ContractMgrLogic;
import com.cleanwise.view.utils.*;

/**
 * Implementation of <strong>Action</strong> that saves a new
 * contract detail or updates an existing contract detail.
 */
public final class ContractMgrDetailAction extends ActionSuper {


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

    // Get the action and the contractId from the request.
    String action  = request.getParameter("action");
    if (action == null) action = "add";

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }      
        
    MessageResources mr = getResources(request);
        
    // Get the form buttons as specified in the properties file.
    
    String saveStr = getMessage(mr,request,"global.action.label.save");
    String deleteStr = getMessage(mr,request,"global.action.label.delete");
    String removeStr = getMessage(mr,request,"admin.button.remove");
    String updatePriceStr = getMessage(mr,request,"admin.button.updatePrice");
    String findItemsStr = getMessage(mr,request,"admin.button.findItems");
    String addItemsStr = getMessage(mr,request,"admin.button.chose");
    String createFreightTableStr = getMessage(mr,request,"admin.button.createFreightTable");
        
    String contractId = request.getParameter("id");
    if (contractId == null) contractId = "";
    HttpSession session = request.getSession();
     
    if("edit".equals(action) && ("".equals(contractId) || "0".equals(contractId))) {
        contractId = (String) session.getAttribute("Contract.id");
        if(null == contractId || "".equals(contractId) || "0".equals(contractId)) {
            action = "add";
        }
    } 
    else if ( action.equals(deleteStr)) {
        contractId = (String) session.getAttribute("Contract.id");
    }
    
    try {

      // Add a new advisor.
      if (action.equals("add")) {
        ContractMgrLogic.addContract(request, form);
        return (mapping.findForward("display"));
      }

      // Edit an existing contract.
      else if (action.equals("edit")) {
        ContractMgrLogic.editContract(request, form, contractId);
        return (mapping.findForward("display"));
      }
      
      // Save the new or updated contract.
      else if (action.equals(saveStr)) {

        ActionErrors ae = ContractMgrLogic.saveContract(request, form);
        if (ae.size() > 0 ) {
            saveErrors(request, ae);
            return (mapping.findForward("display"));            
        }
        return (mapping.findForward("save"));
      }      
       
      else if (action.equals(removeStr)) {
        ContractMgrLogic.removeItems(request, form);
        return (mapping.findForward("detail"));
      }      

      else if (action.equals(findItemsStr)) {
        ContractMgrLogic.findItems(request, form);
        return (mapping.findForward("finditems"));
      }

      else if (action.equals(addItemsStr)) {
        ContractMgrLogic.addItems(request, form);
        return (mapping.findForward("detail"));
      }

      else if (action.equals(deleteStr)) {
	  ContractMgrLogic.removeContract(request, form);
        return (mapping.findForward("back_to_list"));
      }

      else if (action.equals(updatePriceStr)) {

        ActionErrors ae = ContractMgrLogic.updateItems(request, form);
        if (ae.size() > 0 ) {
            saveErrors(request, ae);
            return (mapping.findForward("display"));
        }
        return (mapping.findForward("detail"));
      }

      else if (action.equals("sortitems")) {
        ContractMgrLogic.sortItems(request, form);
        return (mapping.findForward("display"));
      }

      else if (action.equals("sortfinditems")) {
        ContractMgrLogic.sortCatalogItems(request, form);
        return (mapping.findForward("finditems"));
      }

      else if (action.equals(createFreightTableStr)) {
        return (mapping.findForward("createfreighttable"));
      }
      else if (action.equals("Non Catalog Items")) {
        return (mapping.findForward("noncatalogitems"));
      }
      else if (action.equals("Remove Items Selected")) {
        ActionErrors ae = ContractMgrLogic.removeNonCatalogItems(request,form);
        if(ae.size()>0) {
            saveErrors(request, ae);
        }
        return (mapping.findForward("noncatalogitems"));
      }
      // Users should never get here, but if they do, perform an add.
      else {
        ContractMgrLogic.addContract(request, form);
        return (mapping.findForward("display"));
      }

    }

    // Catch all exceptions here.
    catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("errorobject", e);
      return (mapping.findForward("error"));
    }

  }


}
