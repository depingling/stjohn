package com.cleanwise.view.logic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import com.cleanwise.view.forms.StorePoForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
public class StorePoErpLookUpLogic {
    public static void search(HttpServletRequest request, ActionForm form)
            throws Exception {
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        PurchaseOrder purchaseOrderBean = factory.getPurchaseOrderAPI();
        StorePoForm pForm = (StorePoForm) form;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getStoreId();
        // Reset the session variables.
        RealPurchaseOrderNumViewVector realPOVwV = new RealPurchaseOrderNumViewVector();
        String outboundPoNum = pForm.getOutboundPoNum();
        realPOVwV = purchaseOrderBean.getRealPurchaseOrderNum(storeId, outboundPoNum);
        session.setAttribute("PurchaseOrder.found.vector", realPOVwV);
        session.setAttribute("PurchaseOrder.found.total", "0");
    }
}
