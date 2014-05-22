package com.cleanwise.view.forms;

import java.util.ArrayList;

import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.value.DistItemViewVector;
import com.cleanwise.service.api.value.ItemViewVector;
import com.cleanwise.service.api.value.ShoppingControlItemViewVector;

/**
 * @author Alexander Chikin
 * Date: 15.08.2006
 * Time: 2:21:11
 * .
 */
public class StoreAccountShoppingControlForm extends ShoppingControlForm {

    private int mAccountId;
    public void setAccountId(String pAccountId) {
	mAccountId = Integer.parseInt(pAccountId);
    }
    public void setAccountId(int pAccountId) { mAccountId = pAccountId; }
    public int getAccountId() { return mAccountId; }

    ArrayList shoppingControlItemViewList;
	public ArrayList getShoppingControlItemViewList() {
		return shoppingControlItemViewList;
	}
	public void setShoppingControlItemViewList(ArrayList shoppingControlItemViewList) {
		this.shoppingControlItemViewList = shoppingControlItemViewList;
	}

    ArrayList siteShoppingControlItemViewList;
	public ArrayList getSiteShoppingControlItemViewList() {
		return siteShoppingControlItemViewList;
	}
	public void setSiteShoppingControlItemViewList(ArrayList siteShoppingControlItemViewList) {
		this.siteShoppingControlItemViewList = siteShoppingControlItemViewList;
	}
	
	private String _searchType="";
    private String _searchNumType = "nameBegins";
    private String _skuTempl="";
    private String _skuType = SearchCriteria.STORE_SKU_NUMBER;
    private String _shortDescTempl="";
    private String _longDescTempl="";
    private String _manuId="";
    private String _manuNameTempl="";
    private String _distId="";
    private String _distNameTempl="";
    private String _categoryTempl="";
    private String _sizeTempl="";
    private String _itemPropertyName="";
    private String _itemPropertyTempl="";
    
    public void setSearchType(String pValue) {_searchType=pValue;}
    public String getSearchType(){return _searchType;}

    public void setSkuTempl(String pValue) {_skuTempl=pValue;}
    public String getSkuTempl(){return _skuTempl;}

    public void setSkuType(String pValue) {_skuType=pValue;}
    public String getSkuType(){return _skuType;}
    
    public void setSearchNumType(String pValue) {_searchNumType=pValue;}
    public String getSearchNumType(){return _searchNumType;}

    public void setShortDescTempl(String pValue) {_shortDescTempl=pValue;}
    public String getShortDescTempl(){return _shortDescTempl;}

    public void setLongDescTempl(String pValue) {_longDescTempl=pValue;}
    public String getLongDescTempl(){return _longDescTempl;}

    public void setCategoryTempl(String pValue) {_categoryTempl=pValue;}
    public String getCategoryTempl(){return _categoryTempl;}

    public void setSizeTempl(String pValue) {_sizeTempl=pValue;}
    public String getSizeTempl(){return _sizeTempl;}

    public void setItemPropertyName(String pValue) {_itemPropertyName=pValue;}
    public String getItemPropertyName(){return _itemPropertyName;}

    public void setItemPropertyTempl(String pValue) {_itemPropertyTempl=pValue;}
    public String getItemPropertyTempl(){return _itemPropertyTempl;}

    public void setManuId(String pValue) {
      _manuId = pValue;
      if(pValue==null || pValue.trim().length()==0) {
        _manuNameTempl = "";
      }
    }
    public String getManuId(){return _manuId;}

    public void setManuNameTempl(String pValue) {_manuNameTempl = pValue;}
    public String getManuNameTempl(){return _manuNameTempl;}

    public void setDistId(String pValue) {
      _distId=pValue;
      if(pValue==null || pValue.trim().length()==0) {
        _distNameTempl="";
      }
    }
    public String getDistId(){return _distId;}

    public void setDistNameTempl(String pValue) {_distNameTempl = pValue;}
    public String getDistNameTempl(){return _distNameTempl;}

}

