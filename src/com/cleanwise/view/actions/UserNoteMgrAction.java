package com.cleanwise.view.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.logic.UserNoteMgrLogic;
import com.cleanwise.view.forms.UserNoteMgrForm;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         04.12.2007
 * Time:         11:35:59
 *
 * @author Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserNoteMgrAction extends ActionSuper {

    // ---------------------------------------------- Public Methods

    /**
     * Process the specified HTTP request, and create the
     * corresponding HTTP response (or forward to another web
     * component that will create it).  Return an
     * <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the
     * response has already been completed.
     *
     * @param mapping  The ActionMapping used to select this
     *                 instance
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @param form     Description of Parameter
     * @return Description of the Returned Value
     * @throws java.io.IOException            if an input/output error occurs
     * @throws javax.servlet.ServletException if a servlet exception occurs
     */
    public ActionForward performSub(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException, ServletException {

        String action = (String) request.getParameter("action");
        MessageResources mr = getResources(request);
        String read         = getMessage(mr,request,"msbNotes.text.read");

        if (action == null || action.compareTo("") == 0) {
            action = "init";
        }

        action = action.toLowerCase();

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if (st.checkSession() == false) {
            return mapping.findForward("/userportal/logon");
        }

        try {
            if (action.equals("init")) {
                // logic here for this page
                UserNoteMgrLogic.init(request, (UserNoteMgrForm) form);
                return mapping.findForward("display");
            } else if("search".equals(action)) {
                UserNoteMgrLogic.search(request, (UserNoteMgrForm) form);
                return mapping.findForward("display");
            }else if(action.equals(read.toLowerCase())) {
                UserNoteMgrLogic.initRead(request, (UserNoteMgrForm) form);
                return mapping.findForward("detail");
            }else if(action.equals("nextnote")) {
                UserNoteMgrLogic.nextRead(request, (UserNoteMgrForm) form,response);
                return null;
            }else if(action.equals("prevnote")) {
                UserNoteMgrLogic.prevRead(request, (UserNoteMgrForm) form,response);
                return null;
            }else {
                return mapping.findForward("display");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            return mapping.findForward("error");
        }
    }
}
