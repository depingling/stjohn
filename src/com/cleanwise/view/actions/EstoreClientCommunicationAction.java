/*
 * EstoreClientCommunicationAction.java
 *
 * Created on October 11, 2002, 4:47 PM
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
import com.cleanwise.view.utils.*;
import com.cleanwise.view.logic.EstoreClientLogic;
import com.cleanwise.view.forms.EstoreClientCommunicationForm;
/**
 *
 * @author  bstevens
 */
public class EstoreClientCommunicationAction  extends ActionSuper {
    
    /**
     *  Process the specified HTTP request, and create the corresponding HTTP
     *  response (or forward to another web component that will create it).
     *  Return an <code>ActionForward</code> instance describing where and how
     *  control should be forwarded, or <code>null</code> if the response has
     *  already been completed.
     *
     *@param  mapping               The ActionMapping used to select this
     *      instance
     *@param  request               The HTTP request we are processing
     *@param  response              The HTTP response we are creating
     *@param  form                  Description of Parameter
     *@return                       Description of the Returned Value
     *@exception  IOException       if an input/output error occurs
     *@exception  ServletException  if a servlet exception occurs
     */
    public ActionForward performSub(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {
        
        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }
        action.trim();
        
        
        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward("/userportal/logon");
        }
        
        MessageResources mr = getResources(request);
        try {
            ActionErrors errors = null;
            if (action.equals("init")) {
                {
                    errors = EstoreClientLogic.initRequest(response,request,form);
                }
                return (mapping.findForward("display"));
            }else if (action.equals("download_orders")) {
                errors = EstoreClientLogic.downloadOrders(response,request,form);
                if (errors == null){
                    return null;
                }
            }else if (action.equals("download_file")){
                errors = EstoreClientLogic.downloadFile(response,request,form);
                if (errors == null){
                    return null;
                }
            }else if (action.equals("acknowledge_transaction")){
                errors = EstoreClientLogic.acknowledgeTransaction(response,request,form);
            }else if (action.equals("run_loader")){
                errors = EstoreClientLogic.processRunLoaderRequest(response,request,form);
            }else if (action.equals("Load_File")){
                EstoreClientCommunicationForm sForm = (EstoreClientCommunicationForm) form;
                if (sForm.getEncrypted().equalsIgnoreCase("false")){
                    errors = EstoreClientLogic.processUnencryptedUpload(response,request,form);
                }else{
                    errors = EstoreClientLogic.processEncryptedUpload(response,request,form);
                }
            }
            if (errors.size() > 0){
                EstoreClientLogic.initRequest(response,request,form);
                saveErrors(request, errors);
            }else{
                errors = EstoreClientLogic.initRequest(response,request,form);
                if (errors.size() > 0){
                    saveErrors(request, errors);
                }
            }
            return (mapping.findForward("display"));
        }catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward("failure"));
        }
    }
    
}
