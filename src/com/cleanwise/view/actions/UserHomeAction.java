
package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.logic.*;
import com.cleanwise.view.utils.*;
import org.apache.struts.action.ActionMessages;


/**
 *  Implementation of <strong>Action</strong> that processes
 *  user related actions.
 *
 *@author     dvieira
 *@created    October 17, 2001
 */

public final class UserHomeAction extends ActionSuper {

    // ------------------------------------------------------------ Public Methods

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

        String action = (String) request.getParameter("action");
        MessageResources mr = getResources(request);
        String regStr = getMessage(mr,request,"user.button.register");

        if (action == null || action.compareTo("") == 0) {
            action = "init";
        }

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        //some actions may come from the public site, so we don't care if they are logged on
        if(action.equals("send_contactus_msg") ||
           action.equals("send_uspscontactus_msg") ||
           action.equals("send_sales_contactus_msg")){
              //do nothing
        }else{
           if ( st.checkSession() == false ) {
              return mapping.findForward("/userportal/logon");
           }
        }

        action = action.toLowerCase();
        try {
            if (action.compareTo("init") == 0) {
    UserMgrLogic.initSessionVariables(request);

                if ( st.getUserData().isaMSB() || st.getUserData().isaCustomer() ) {
                    return mapping.findForward("msbhome");
                }
                if ( st.getUserData().isaRegistrationUser() ) {
                    return mapping.findForward("reg_init");
                }
                return mapping.findForward("display");
            }
            else if (action.compareToIgnoreCase(regStr) == 0) {
                ActionErrors ae = UserMgrLogic.registerUser
        (request, form,getResources(request));
                if (ae != null && ae.size() == 0 ) {
                    return (mapping.findForward("regconfirm"));
                }
                if (ae != null && ae.size() > 0 ) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("display"));
            }
            else if (action.compareTo("order_status") == 0) {
                if ( st.getUserData().isaMSB() || st.getUserData().isaCustomer() ) {
                    return mapping.findForward("msbhome");
                }
                return mapping.findForward("display");
            }
            else if (action.compareTo("compose_email_contact_msg") == 0) {
                //check logon
                String uids
                    = (String) request.getSession().getAttribute(Constants.USER_ID);
                int iuser = 0;
                if (null != uids) {
                    iuser = Integer.parseInt(uids);
                }
                UserMgrLogic.getUserDetailById(request, iuser);
                if ( st.getUserData().isaMSB()  || st.getUserData().isaCustomer() ) {
                    return mapping.findForward("msbhome");
                }
                return mapping.findForward("display");
            }
            else if (action.compareTo("send_email_contact_msg") == 0) {
                //check logon
                ActionErrors ae = EmailLogic.processEmailForm(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return mapping.findForward("contactfailure");
                }
                return mapping.findForward("sent_email");
            }
            else if (action.compareTo("send_contactus_msg") == 0) {
                //don't check logon
                ActionErrors ae = ContactUsLogic.processContactUsForm(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return mapping.findForward("contactfailure");
                }

                return mapping.findForward("sent_email");
            }
            else if (action.compareTo("send_uspscontactus_msg") == 0) {
                //don't check logon
                ActionErrors ae = ContactUsLogic.processUspsContactUsForm(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return mapping.findForward("contactfailure");
                }
                return mapping.findForward("sent_email");
            }
            else if (action.compareTo("send_sales_contactus_msg") == 0) {
                //don't check logon
                ActionErrors ae = ContactUsLogic.processContactUsForm(request, form);

                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return mapping.findForward("tourfailure");
                }
                return mapping.findForward("sent_sales_email");
            }else if ("changestore".equals(action) && (st.getUserData().isaCustServiceRep() || st.getUserData().isaAdmin())){
                ActionMessages ae = new ActionMessages();
                int storeId = 0;
                try{
                    storeId = Integer.parseInt(request.getParameter("id"));
                }catch(Exception e){
                    e.printStackTrace();
                    ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionError("error.badRequest2"));
                }
                if(ae == null || ae.size() == 0){
                    ae = LogOnLogic.switchUserStore(storeId,request);
                }
                if (ae != null && ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return mapping.findForward("display");
            }else {
                if ( st.getUserData().isaMSB()  || st.getUserData().isaCustomer() ) {
                    return mapping.findForward("msbhome");
                }
                if ( st.getUserData().isaRegistrationUser() ) {
                    return mapping.findForward("reg_init");
                }
                return mapping.findForward("display");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            return mapping.findForward("error");
        }

    }

}

