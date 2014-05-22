package com.cleanwise.view.logic;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cleanwise.view.forms.StoreAccountMgrDetailForm;
import com.cleanwise.view.forms.AccountMgrServiceFeeForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.service.api.APIAccess;

import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class AccountMgrServiceFeeLogic {

    public static ActionMessages init(HttpServletRequest request,
            AccountMgrServiceFeeForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        HttpSession session = request.getSession();
        StoreAccountMgrDetailForm accountForm = (StoreAccountMgrDetailForm) session.getAttribute("ACCOUNT_DETAIL_FORM");
        String accountIdS = accountForm.getId();
        int accountId = 0;
        try {
            accountId = Integer.parseInt(accountIdS);
        } catch (NumberFormatException exc) {
            ae.add("error", new ActionMessage("error.systemError",
                    "Wrong account number format: " + accountIdS));
            return ae;
        }
        pForm.setAccountId(accountId);
        pForm.setAccountName(accountForm.getAccountData().getBusEntity().getShortDesc());
        APIAccess factory = new APIAccess();
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }

        ShoppingServices shopServEjb = factory.getShoppingServicesAPI();


        PriceRuleDescViewVector priceRuleVec = shopServEjb.getServiceFeeDescVector(accountId);

        if (priceRuleVec == null) {
            priceRuleVec = new PriceRuleDescViewVector();
        }

        PriceRuleDescView priceRuleView = PriceRuleDescView.createValue();
        priceRuleView.setPriceRule(PriceRuleData.createValue());
        priceRuleView.setPriceRuleDetails(new PriceRuleDetailDataVector());
        priceRuleVec.add(priceRuleView);

        priceRuleView = PriceRuleDescView.createValue();
        priceRuleView.setPriceRule(PriceRuleData.createValue());
        priceRuleView.setPriceRuleDetails(new PriceRuleDetailDataVector());
        priceRuleVec.add(priceRuleView);

        pForm.setPriceRuleDescV(priceRuleVec);
        return ae;
    }

    public static ActionMessages update(HttpServletRequest request,
            AccountMgrServiceFeeForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }
        SessionTool st = new SessionTool(request);
        CleanwiseUser userData = st.getUserData();
        String userName = userData.getUser().getUserName();
        ShoppingServices shopServEjb = factory.getShoppingServicesAPI();

        PriceRuleDescViewVector priceRuleVec = pForm.getPriceRuleDescV();

        Iterator i = priceRuleVec.iterator();
        while (i.hasNext()) {
            PriceRuleDescView priceRuleDesc = (PriceRuleDescView)i.next();

            PriceRuleDetailDataVector details =  priceRuleDesc.getPriceRuleDetails();
            boolean toUpdate = true;
            Iterator j = details.iterator();
            while (j.hasNext()) {
               PriceRuleDetailData detail = (PriceRuleDetailData)j.next();
               if (detail.getParamValue() == null || detail.getParamValue().length() == 0) {
                 toUpdate = false;
                 break;
               }
            }
            if (!toUpdate) {
                continue;
            }

            PriceRuleData priceRuleD = priceRuleDesc.getPriceRule();

            if (priceRuleD.getPriceRuleId() == 0) { // new price rule
                priceRuleD.setShortDesc("ServiceFee");
                priceRuleD.setPriceRuleStatusCd(RefCodeNames.STATUS_CD.ACTIVE);
                priceRuleD.setPriceRuleTypeCd(RefCodeNames.PRICE_RULE_TYPE_CD.SERVICE_FEE);
                priceRuleD.setBusEntityId(pForm.getAccountId());
            }

            priceRuleDesc = shopServEjb.updatePriceRuleDesc(priceRuleDesc, userName);
            priceRuleD = priceRuleDesc.getPriceRule();

        }

        return init(request, pForm);
    }

}
