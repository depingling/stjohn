/**
 *@author     YKupershmidt 
 *@created    September 11, 2003
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

/**
 *  Form bean for the user manager page.
 *
 */
public final class CostLocateMgrForm extends ActionForm {
   private String _feedBackFieldName = "";
   private int _accountId;
   private String _accountDesc;
   private int _skuNum = 0;
   private String _itemDesc = "";
   private int _itemId = 0;
   private String _distIdS = "";
   private String _distErpNum = "";
   private String _distDesc = "";
   private int _distId = 0;
   private ItemContractCostViewVector _itemCostVector = new ItemContractCostViewVector();
   
   public String getFeedBackFieldName() {return _feedBackFieldName;}
   public void setFeedBackFieldName(String pValue) { _feedBackFieldName=pValue;}

   public int getAccountId() {return _accountId;}
   public void setAccountId(int pValue) { _accountId=pValue;}

   public String getAccountDesc() {return _accountDesc;}
   public void setAccountDesc(String pValue) { _accountDesc=pValue;}

   public int getSkuNum() {return _skuNum;}
   public void setSkuNum(int pValue) { _skuNum=pValue;}

   public String getItemDesc() {return _itemDesc;}
   public void setItemDesc(String pValue) { _itemDesc=pValue;}

   public int getItemId() {return _itemId;}
   public void setItemId(int pValue) {_itemId=pValue;}

   public String getDistIdS() {return _distIdS;}
   public void setDistIdS(String pValue) { _distIdS=pValue;}

   public String getDistErpNum() {return _distErpNum;}
   public void setDistErpNum(String pValue) { _distErpNum=pValue;}

   public String getDistDesc() {return _distDesc;}
   public void setDistDesc(String pValue) { _distDesc=pValue;}

   public int getDistId() {return _distId;}
   public void setDistId(int pValue) { _distId=pValue;}

   public ItemContractCostViewVector getItemCostVector() {return _itemCostVector;}
   public void setItemCostVector(ItemContractCostViewVector pValue) { _itemCostVector=pValue;}
   



    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }


    /**
     *  <code>validate</code> method is a stub.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     *@return          an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {
        // No validation necessary.
        return null;
    }

}

