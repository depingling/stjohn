/*
 * CustAcctMgtSurveyAction.java
 *
 * Created on May 15, 2003, 3:52 PM
 */

package com.cleanwise.view.actions;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.ProfileData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.logic.ProfilingMgrLogic;
import com.cleanwise.view.forms.*;
/**
 *
 * @author  bstevens
 */
public class CustAcctMgtSurveyAction extends ActionSuper {
    
   /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param actionForm The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    public ActionForward performSub(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward("/userportal/logon");
        }
        
        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        String initStr = "init";
        String viewStr = "view";
        String detailStr = "detail";
        String searchStr = "search";
        String updateStr = getResources(request).getMessage("admin.button.submitUpdates");
        if (action == null) action = initStr;
        // Process the action
        try {
            ActionErrors ae = null;
            if (action.equals(initStr)) {
                ProfilingMgrLogic.init(request,form);
            }else if (action.equals(detailStr)) {
            	ae = ProfilingMgrLogic.getSurveyDetail(request,form,true,true);
            	if (ae!=null && ae.size() > 0) {
                    saveErrors(request, ae);
                }
            	return (mapping.findForward("detail"));
            }else if (action.equals(viewStr)) {
            	/**
            	 * If there is only one survey then we want to go directly to the detail page.
            	 */
            	
            	
            	if(((java.util.List)request.getSession().getAttribute(ProfilingMgrLogic.PROFILE_FOUND_VECTOR)).size()==1){
                    
                    ProfileData profile = (ProfileData) ((java.util.List)request.getSession().getAttribute(ProfilingMgrLogic.PROFILE_FOUND_VECTOR)).get(0);
                    
                    request.getSession().setAttribute("profileId", Integer.toString(profile.getProfileId()));
                    request.setAttribute("profileId", Integer.toString(profile.getProfileId()));
                    //handled by redirect!
                    //ae = ProfilingMgrLogic.getSurveyDetail(request,form,true,true);
                    //skip right to the detail page
                    if (ae!=null && ae.size() > 0) {
                        saveErrors(request, ae);
                    }
                    return (mapping.findForward("detail"));
                }
                
            }else if (action.equals(updateStr)) {
                //ae = ProfilingMgrLogic.updateSurvey(request,form);
                ae = ProfilingMgrLogic.updateSurveyForAdminSite(request,form,getResources(request),true);
            }else if (action.equals(searchStr)) {
                //this (should be) populated ahead of time for customers because we need to know wheather to
                //show the to or not for the user interface
                if(request.getSession().getAttribute(ProfilingMgrLogic.PROFILE_FOUND_VECTOR) == null){
                    ProfilingMgrLogic.getProfilesForSite(request);
                }
                if(((java.util.List)request.getSession().getAttribute(ProfilingMgrLogic.PROFILE_FOUND_VECTOR)).size()==1){
                    ProfileData profile = (ProfileData) ((java.util.List)request.getSession().getAttribute(ProfilingMgrLogic.PROFILE_FOUND_VECTOR)).get(0);
                    request.getSession().setAttribute("profileId", Integer.toString(profile.getProfileId()));
                    request.setAttribute("profileId", Integer.toString(profile.getProfileId()));
                    return (mapping.findForward("detail"));
                }
            }else{
                ProfilingMgrLogic.init(request,form);
            }
            
            if (ae!=null && ae.size() > 0) {
                saveErrors(request, ae);
                return (mapping.findForward("failure"));
            }
            return (mapping.findForward("success"));
        }catch (Exception e) {
            e.printStackTrace();
            ActionErrors ae = new ActionErrors();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.systemError",e.getMessage()));
            saveErrors(request, ae);
            return (mapping.findForward("failure"));
        }
    }
}
