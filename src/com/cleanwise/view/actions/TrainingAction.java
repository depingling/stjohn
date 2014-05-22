
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
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.forms.TrainingForm;
import com.cleanwise.view.logic.TrainingLogic;
import java.util.Iterator;
import java.util.Enumeration;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.RefCodeNames;

/**
 *
 * @author YKupershmidt
 */
public final class TrainingAction extends ActionSuper {
    
    // ---------------------------------------------------- Public Methods
    
    
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form an <code>ActionForm</code> value
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @return an <code>ActionForward</code> value
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    public ActionForward performSub(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {

       String mappingAddress = "success";
        try {
            
            // Is there a currently logged on user?
            SessionTool st = new SessionTool(request);
            if ( st.checkSession() == false ) {
                return mapping.findForward("/userportal/logon");
            }
            
            String action = (String) request.getParameter("action");
            TrainingForm theForm = (TrainingForm) form;
            if (action==null || action.trim().length()==0) action = "init";
            
            if ( action.equals("init"))  {
                ActionErrors ae = TrainingLogic.init(request,theForm);
                if(ae.size()>0) {
                    saveErrors(request,ae);
                    mappingAddress = "failure";
                }
            }
            else if ( action.equals("readNote"))  {
                ActionErrors ae = TrainingLogic.readNote(request,theForm);
                if(ae.size()>0) {
                    saveErrors(request,ae);
                    mappingAddress = "failure";
                }
            }
            else if ( action.equals("downloadAttachment"))  {
                ActionErrors ae = TrainingLogic.readAttachment(request,response, theForm);
//                ActionErrors ae = TrainingLogic.downloadAttachment(request,response, theForm);                
                if(ae.size()>0) {
                    saveErrors(request,ae);
                    mappingAddress = "error";
                }
//                return mapping.findForward(mappingAddress);
//                return null;
            }
        }
        catch ( Exception e ) {
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            e.printStackTrace();
            mappingAddress = "error";
        }
        
        
        return mapping.findForward(mappingAddress);
    }
    
}
