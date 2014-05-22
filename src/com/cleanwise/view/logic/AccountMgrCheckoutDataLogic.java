package com.cleanwise.view.logic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cleanwise.view.forms.StoreAccountMgrDetailForm;
import com.cleanwise.view.forms.AccountMgrCheckoutDataForm;
import com.cleanwise.service.api.APIAccess;

import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class AccountMgrCheckoutDataLogic {

    public static ActionMessages init(HttpServletRequest request,
            AccountMgrCheckoutDataForm pForm) throws Exception {
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

        PropertyService psvcBean = factory.getPropertyServiceAPI();
        BusEntityFieldsData sfd = psvcBean.fetchCheckoutFieldsData(accountId);
        pForm.setConfig(sfd);
        
        return ae;
    }

    public static ActionMessages saveCheckoutFields(HttpServletRequest request,
            AccountMgrCheckoutDataForm pForm) throws Exception {

    	HttpSession session = request.getSession();

        BusEntityFieldsData siteFieldsData = pForm.getConfig();
        resetCheckBoxAndSet(request,siteFieldsData);

        int accountId = pForm.getAccountId();
        APIAccess factory = new APIAccess();
        PropertyService psvcBean = factory.getPropertyServiceAPI();
        psvcBean.updateCheckoutFieldsData(accountId, siteFieldsData);

        return init(request, pForm);
    }
    
    private static void resetCheckBoxAndSet(HttpServletRequest request,BusEntityFieldsData siteFieldsData) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {


        siteFieldsData.reset_cb_data();
        Enumeration paramsEnum = request.getParameterNames();
        Hashtable paramsTable=new Hashtable();
        while(paramsEnum.hasMoreElements())
        {
            String param=(String) paramsEnum.nextElement();
            paramsTable.put(param,request.getParameter(param));
        }
        if(siteFieldsData!=null){
        ArrayList checkBoxNames = siteFieldsData.getCheckBoxNames();
        Class resetClass=BusEntityFieldsData.class;
        Class[] argTypes={Boolean.TYPE};
        Object[] theData={Boolean.TRUE};
        for(int i=0;i<checkBoxNames.size();i++){
              if(paramsTable.containsKey(checkBoxNames.get(i))){
                  String nMethod= "setF"+((String) checkBoxNames.get(i)).substring(8);
                  Method method=resetClass.getMethod(nMethod,argTypes);
                  method.invoke(siteFieldsData,theData);
              }
        }
        }
     }

}
