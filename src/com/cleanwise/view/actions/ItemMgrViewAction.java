package com.cleanwise.view.actions;

import com.cleanwise.view.logic.ItemMgrSearchLogic;
import com.cleanwise.view.logic.LogOnLogic;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.utils.Constants;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public final class ItemMgrViewAction
    extends ActionSuper {
    public ActionForward performSub(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response)
                          throws IOException, ServletException {

        int accountId = 0;
        LogOnLogic.processFakeLogOn(request, accountId);
        ItemMgrSearchLogic.getExternalProductInfo(request);
        return (mapping.findForward("display"));
    }
}
