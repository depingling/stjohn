package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import java.util.Iterator;

public final class AccountMgrServiceFeeForm extends ActionForm {
    private int accountId = 0;
    private String accountName = "";

    private PriceRuleDescViewVector priceRuleDescV;


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setPriceRuleDescV(PriceRuleDescViewVector v) {
        priceRuleDescV = v;
    }

    public PriceRuleDescViewVector getPriceRuleDescV() {
        return priceRuleDescV;
    }

    public String getServiceFeeCode(int i) {
        return  getPriceRuleDetailParamValue(i, RefCodeNames.PRICE_RULE_DETAIL_TYPE_CD.SERVICE_FEE_CODE);
    }

    public String getServiceFeeItemNum(int i) {
       return  getPriceRuleDetailParamValue(i, RefCodeNames.PRICE_RULE_DETAIL_TYPE_CD.SERVICE_FEE_ITEM_NUM);
    }

    public String getServiceFeeAmount(int i) {
       return  getPriceRuleDetailParamValue(i, RefCodeNames.PRICE_RULE_DETAIL_TYPE_CD.SERVICE_FEE_AMOUNT);
    }


    public void setServiceFeeCode(int i, String v) {
      setPriceRuleDetailParamValue(i, RefCodeNames.PRICE_RULE_DETAIL_TYPE_CD.SERVICE_FEE_CODE, v);
    }

    public void setServiceFeeItemNum(int i, String v) {
      setPriceRuleDetailParamValue(i, RefCodeNames.PRICE_RULE_DETAIL_TYPE_CD.SERVICE_FEE_ITEM_NUM, v);
    }

    public void setServiceFeeAmount(int i, String v) {
      setPriceRuleDetailParamValue(i, RefCodeNames.PRICE_RULE_DETAIL_TYPE_CD.SERVICE_FEE_AMOUNT, v);
    }


    public String getPriceRuleDetailParamValue(int i, String pParamName)  {
      PriceRuleDetailData param = getPriceRuleDetailParam(i, pParamName);
      if (param != null) {
          return param.getParamValue();
      } else {
          return "";
      }
    }

    public PriceRuleDetailData getPriceRuleDetailParam(int i, String pParamName) {
        if (priceRuleDescV == null || priceRuleDescV.size() <= i) {
            return null;
        }
        PriceRuleDescView priceRuleView = (PriceRuleDescView)priceRuleDescV.get(i);
        PriceRuleDetailDataVector props = priceRuleView.getPriceRuleDetails();
        if (props == null || props.size() == 0) {
            return null;
        }
        Iterator j = props.iterator();
        while (j.hasNext()) {
            PriceRuleDetailData priceRuleDetailD = (PriceRuleDetailData)j.next();
            if (priceRuleDetailD.getParamName().equals(pParamName)) {
                return priceRuleDetailD;
            }
        }
        return null;
    }




   public void setPriceRuleDetailParamValue(int i, String pParamName, String pParamValue) {
       if (priceRuleDescV == null) {
          priceRuleDescV = new PriceRuleDescViewVector();
       }
       if (priceRuleDescV.size() <= i) {
           for (int j=0; j<i; j++) {
               PriceRuleDescView priceRuleView = PriceRuleDescView.createValue();
               priceRuleView.setPriceRule(PriceRuleData.createValue());
               priceRuleView.setPriceRuleDetails(new PriceRuleDetailDataVector());
               priceRuleDescV.add(priceRuleView);
           }
       }

       PriceRuleDetailData prop = getPriceRuleDetailParam(i, pParamName);
       if (prop != null) {
        prop.setParamValue(pParamValue);
       } else {
           // create param
           prop = PriceRuleDetailData.createValue();
           prop.setParamName(pParamName);
           prop.setParamValue(pParamValue);

           // add param
           PriceRuleDescView prRuleDescView = (PriceRuleDescView)priceRuleDescV.get(i);
           PriceRuleDetailDataVector details = prRuleDescView.getPriceRuleDetails();
           details.add(prop);

       }
   }

}
