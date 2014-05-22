/*
 * ActionBase.java
 *
 * Created on February 27, 2004, 11:47 AM
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
import com.cleanwise.view.utils.*;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.logic.AggregateItemLogic;
import com.cleanwise.view.forms.AggregateItemMgrForm;

/**
 * Overidding classes do not need to worry about checking the status of the user (wheather or not they
 * are logged in) and any uncaught exception should be left for this class to deal with.
 * @author  bstevens
 */
public abstract class ActionBase extends ActionSuper{
    
    
    
    
    
    /**
     *Overide if action should not check to make sure user is logged in
     */
    protected boolean getIsPrivatePage(){
        return true;
    }
    
    /**
     *Processes an error and forwards user to the value of the failForward property
     */
    private ActionForward processError(ActionMapping mapping,HttpServletRequest request, Exception e){
        e.printStackTrace();
        if(request != null){
            request.setAttribute("errorobject", e);
            ActionErrors ae = new ActionErrors();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.genericError",e.getMessage()));
            saveErrors(request, ae);
        }
        return (mapping.findForward(getFailForward()));
    }    
    
    
    /**
     *Takes the place of the preform method whcih would normally need to be overridden.  This method
     *handles checking the logon status of the user, and deals with any unchaught errors in a uniform
     *manner.
     */
    public ActionForward performSub(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {
        // Is there a currently logged on user?
        if(getIsPrivatePage()){
            SessionTool st = new SessionTool(request);
            if ( st.checkSession() == false ) {
                return mapping.findForward("/userportal/logon");
            }
        }
        
        try{
            return performAction(mapping, form, request, response);
        }catch(Exception e){
            return processError(mapping, request, e);
        }
    }
    
    /**
     *Overidding classes should not worry about checking wheather the user is logged in or not and 
     *should not catch any un-caught exception (Logic class problem).  They should however set the 
     *failForward property if it should be anything other than <i>failure</i>.
     */
    public abstract ActionForward performAction(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception;
}
