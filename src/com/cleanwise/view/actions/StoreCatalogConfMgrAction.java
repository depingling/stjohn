/*
 * StoreCatalogConfMgrAction.java
 *
 * Created on July 19, 2005, 4:34 PM
 */
package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.logic.StoreCatalogConfMgrLogic;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.logic.LocateStoreCatalogLogic;
import com.cleanwise.view.forms.LocateStoreCatalogForm;
import com.cleanwise.view.forms.StoreCatalogMgrForm;


/**
 *
 * @author Ykupershmidt
 */
public final class StoreCatalogConfMgrAction  extends ActionSuper {
  
    public ActionForward performSub(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
             throws IOException, ServletException {

        ActionErrors ae = new ActionErrors();

        StoreCatalogMgrForm sForm = (StoreCatalogMgrForm) form;
 
        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        sForm.setAction(action);
        if (action == null) {
            action = "init";
        }

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
          return mapping.findForward("/userportal/logon");
        }   

        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
       	String searchStr = getMessage(mr,request,"global.action.label.search");
      	String viewallStr = getMessage(mr,request,"admin.button.viewall");
        String assignDistStr = getMessage(mr,request,"admin.button.assigndistributor");
        // Process the action
        try {

            if("Locate Catalog".equals(action)) {
              //sForm.setLastLocateAction(action);
              
              return (mapping.findForward("success"));
            }        
            else if("Return Selected".equals(action)){
              //String submitFormIdent = request.getParameter("jspSubmitIdent");
              //if(submitFormIdent!=null &&  
              //   submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_CATALOG_FORM)==0){
              //  ae = StoreItemCatalogMgrLogic.setCatalogFilter(request,sForm);
              //  if(ae.size() > 0){
              //    saveErrors(request, ae);
              //    return (mapping.findForward("failure"));
              //  }
              //}
              return (mapping.findForward("success"));
            }
            else if (action.equals("init")) {
		            StoreCatalogConfMgrLogic.init(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals(searchStr) || action.equals(viewallStr)) {
		           StoreCatalogConfMgrLogic.search(request, form);
               return (mapping.findForward("success"));
            }
            else if (action.equals(viewallStr)) {
                StoreCatalogConfMgrLogic.search(request, form);          		            
                return (mapping.findForward("success"));
            }
            else if (action.equals(saveStr)) {
		            ae = StoreCatalogConfMgrLogic.update(request, form); 
                if(ae.size() > 0){
                  saveErrors(request, ae);
                  return (mapping.findForward("failure"));
                }
		           return (mapping.findForward("success"));
            }
            else if (action.equals("sort")) {
		           StoreCatalogConfMgrLogic.sort(request, form);
               return (mapping.findForward("success"));
            }
            
            //else if (action.equals(assignDistStr)) {
		        //    StoreCatalogConfMgrLogic.updateItemDist(request, form);
            //    return (mapping.findForward("success"));
            //}
            else {
                StoreCatalogConfMgrLogic.init(request, form);
                return (mapping.findForward("success"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward("failure"));
        }

    }

}


