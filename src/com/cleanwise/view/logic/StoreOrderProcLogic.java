package com.cleanwise.view.logic;

import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;

import com.cleanwise.view.forms.AccountMgrDetailForm;
import com.cleanwise.view.forms.StoreAccountMgrDetailForm;
import com.cleanwise.view.forms.LocateForm;

/**
 * 
 * @author Alexander Chikin
 * Date: 13.08.2006
 * Time: 13:34:30
 *
 */
public class StoreOrderProcLogic {



    public static void setStatusLocateContract(HttpServletRequest request, ActionForm form,String page, String status) throws Exception {
        StoreAccountMgrDetailForm amdf = (StoreAccountMgrDetailForm) request.getSession().getAttribute("STORE_ACCOUNT_DETAIL_FORM");
        if(amdf==null)  throw new Exception("Not found in scope StoreAccountMgrDetailForm");
        else  (amdf.getLocateForm(page)).getLocateContract().setStatusLocate(status);
    }

    public static void setSelectContract(HttpServletRequest request, ActionForm form,String page) throws Exception {
        StoreAccountMgrDetailForm amdf = (StoreAccountMgrDetailForm) request.getSession().getAttribute("STORE_ACCOUNT_DETAIL_FORM");
        if(amdf==null)  throw new Exception("Not found in scope StoreAccountMgrDetailForm");
        else
        {
            (amdf.getLocateForm(page)).getLocateContract().setSelect(request.getParameter("select"));
            (amdf.getLocateForm(page)).getLocateContract().setStatusLocate(LocateForm.HIDE_DISPLAY);
        }
    }

    public static void setSelectFreightHandlers(HttpServletRequest request, ActionForm form,String page) throws Exception {
        StoreAccountMgrDetailForm amdf = (StoreAccountMgrDetailForm) request.getSession().getAttribute("STORE_ACCOUNT_DETAIL_FORM");
        if(amdf==null)  throw new Exception("Not found in scope StoreAccountMgrDetailForm");
        else
        {
            (amdf.getLocateForm(page)).getLocateFreightHandler().setSelect(request.getParameter("select"));
            (amdf.getLocateForm(page)).getLocateFreightHandler().setStatusLocate(LocateForm.HIDE_DISPLAY);
        }
    }

    public static void setStatusLocateFreightHandlers(HttpServletRequest request, ActionForm form,String page,String status) throws Exception {
        StoreAccountMgrDetailForm amdf = (StoreAccountMgrDetailForm) request.getSession().getAttribute("STORE_ACCOUNT_DETAIL_FORM");

        if(amdf==null)  throw new Exception("Not found in scope StoreAccountMgrDetailForm");
        else  (amdf.getLocateForm(page)).getLocateFreightHandler().setStatusLocate(status);
    }

    public static void setSelectDistributor(HttpServletRequest request, ActionForm form,String page) throws Exception {
        StoreAccountMgrDetailForm amdf = (StoreAccountMgrDetailForm) request.getSession().getAttribute("STORE_ACCOUNT_DETAIL_FORM");
        if(amdf==null)  throw new Exception("Not found in scope StoreAccountMgrDetailForm");
        else
        {
            (amdf.getLocateForm(page)).getLocateDistributor().setSelect(request.getParameter("select"));
            (amdf.getLocateForm(page)).getLocateDistributor().setStatusLocate(LocateForm.HIDE_DISPLAY);
        }
    }

    public static void setStatusLocateDistributor(HttpServletRequest request, ActionForm form,String page,String status) throws Exception {
        StoreAccountMgrDetailForm amdf = (StoreAccountMgrDetailForm) request.getSession().getAttribute("STORE_ACCOUNT_DETAIL_FORM");
        if(amdf==null)  throw new Exception("Not found in scope StoreAccountMgrDetailForm");
        else  (amdf.getLocateForm(page)).getLocateDistributor().setStatusLocate(status);
    }


}
