
package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.RelatedLogic;
import com.cleanwise.view.forms.RelatedForm;
import com.cleanwise.view.utils.Constants;
import javax.servlet.http.HttpSession;
import com.cleanwise.view.utils.*;


/**
 *  Description of the Class
 *
 *@author     dvieira
 *@created    August 27, 2001
 */
public final class RelatedAction extends ActionSuper {
    /**
     *  Description of the Method
     *
     *@param  mapping               Description of Parameter
     *@param  form                  Description of Parameter
     *@param  request               Description of Parameter
     *@param  response              Description of Parameter
     *@return                       Description of the Returned Value
     *@exception  IOException       Description of Exception
     *@exception  ServletException  Description of Exception
     */
    public ActionForward performSub(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
             throws IOException, ServletException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }

	// Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward("/userportal/logon");
        }   

 
        RelatedForm relForm = (RelatedForm) form;
        String type = relForm.getSearchForType();
        String name = relForm.getSearchForName();
        request.getSession().setAttribute("Related.searchForType", type);
        request.getSession().setAttribute("Related.searchForName", name);
        
        MessageResources mr = getResources(request);
        String viewAll = relForm.getViewAll();
        String search = "";
        if (viewAll.equals(getMessage(mr,request,"admin.button.viewall"))) {

	    relForm.setSearchType("viewall");
            search = "viewall";
	}
        else if(viewAll.equals(getMessage(mr,request,"global.action.label.search"))) {

            search = "search";
        }
      
        try{
            if(action.equals("account")){
                RelatedLogic.initForAccount(request);

                if (type.length() > 0 && search.length() > 0) {
               
                    RelatedLogic.findAccountInfo(relForm, request);
                   
                }
                return mapping.findForward("account");
            }
            else if(action.equals("site")){
                RelatedLogic.initForSite(request);

                if (type.length() > 0 && search.length() > 0) {
               
                    RelatedLogic.findSiteInfo(relForm, request);
                   
                }
                return mapping.findForward("site");
            }
            else if(action.equals("catalog")){
                RelatedLogic.initForCatalog(request);

                if (type.length() > 0 && search.length() > 0) {
               
                    RelatedLogic.findCatalogInfo(relForm, request);
                   
                }
                return mapping.findForward("catalog");
            }
            else if(action.equals("store")){
                RelatedLogic.initForStore(request);

                if (type.length() > 0 && search.length() > 0) {
               
                    RelatedLogic.findStoreInfo(relForm, request);
                   
                }
                return mapping.findForward("store");
            }
            else if(action.equals("contract")){
                RelatedLogic.initForContract(request);

                if (type.length() > 0 && search.length() > 0) {
               
                    RelatedLogic.findContractInfo(relForm, request);
                   
                }
                return mapping.findForward("contract");
            }
            else if(action.equals("user")){
                RelatedLogic.initForUser(request);

                if (type.length() > 0 && search.length() > 0) {
               
                    ActionErrors ae = RelatedLogic.findUserInfo(relForm, request);
                    if(ae.size()>0) {
                       saveErrors(request,ae);
                    }
                   
                }
                return mapping.findForward("user");
            }
            else if(action.equals("orderguide")){
                RelatedLogic.initForOrderGuide(request);

                if (type.length() > 0 && search.length() > 0) {
               
                    RelatedLogic.findOrderGuideInfo(relForm, request);
                   
                }
                return mapping.findForward("orderguide");
            }
            else if(action.equals("group")){
                RelatedLogic.initForGroups(request);
                if (type.length() > 0 && search.length() > 0) {
                    RelatedLogic.findGroupInfo(relForm, request);
                }
                return mapping.findForward("group");
            }
            else if(action.equals("profiling")){
                RelatedLogic.initForProfiling(request);
                if (type.length() > 0 && search.length() > 0) {
                    RelatedLogic.findProfilingInfo(relForm, request);
                }
                return mapping.findForward("profiling");
            }
            else if(action.equals("sort")){
                RelatedLogic.doSort(request, relForm);
                return mapping.findForward("success");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward("failure"));
        }        
        return (mapping.findForward("failure"));
        
    }
}

