package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

public final class StoreServiceProviderMgrDetailForm extends Base2DetailForm {

	  private String _searchField = "";
	  private String _searchType = "nameBegins";
	  private boolean _searchShowInactiveFl = false;
	  private int _searchAccountId = 0;

	  private String _mFirstName = new String("");
	  private String _mLastName = new String("");
	  private String _mServiceProviderType[] = new String[0];

	  private int _selectedId = 0;
	  /**
	   * Holds value of property searchStoreId.
	   */
	  private String searchStoreId;
  private String storeId;
  private String mServiceProviderCd = "";
  private ServiceProviderData _detail = ServiceProviderData.createValue();
  
  private String mConfFunction = "";
  
  /**
   *  <code>reset</code> method, set the search fiels to null.
   *
   *@param  mapping  an <code>ActionMapping</code> value
   *@param  request  a <code>HttpServletRequest</code> value
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
	    mShowAllAcctFl = false;
	    mConifiguredOnlyFl = false;
	    mRemoveSiteAssocFl = false;
	    _searchShowInactiveFl = false;
	      mConfSelectIds = new String[0];
	      mConfDisplayIds = new String[0];
              //_mServiceProviderType = new String[0];

//	    if ( null == _detail.getUserData().getUserTypeCd()) {
//	      _detail.getUserData().setUserTypeCd
//	              (RefCodeNames.USER_TYPE_CD.CUSTOMER);
//	    }

//	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
//	    Date effDate = new Date();
//	    _effDate = simpleDateFormat.format(effDate);
//	    if ( null == _detail.getUserData().getUserStatusCd() ) {
//	      _detail.getUserData().setUserStatusCd
//	              (RefCodeNames.USER_STATUS_CD.LIMITED);
//	    }

//	    if ( null == _detail.getUserData().getPrefLocaleCd() ) {
//	      _detail.getUserData().setPrefLocaleCd("en_US");
//	    }


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
    // Validation happens in the logic bean.
    return null;
  }

  /**
   * Getter for property storeId.
   * @return Value of property storeId.
   */
  public String getStoreId() {
    return this.storeId;
  }

  /**
   * Setter for property storeId.
   * @param storeId New value of property storeId.
   */
  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }
  
  public String getServiceProviderCd() {
		if ( null == mServiceProviderCd) {
		    return "";
		}
	        return (this.mServiceProviderCd);
  }
  
  public void setServiceProviderCd(String pVal) {
      this.mServiceProviderCd = pVal;
  }


/**
 * @return Returns the _searchField.
 */
public String getSearchField() {
	return _searchField;
}


/**
 * @param field The _searchField to set.
 */
public void setSearchField(String field) {
	_searchField = field;
}


/**
 * @return Returns the _searchType.
 */
public String getSearchType() {
	return _searchType;
}


/**
 * @param type The _searchType to set.
 */
public void setSearchType(String type) {
	_searchType = type;
}


/**
 * @return Returns the _searchAccountId.
 */
public int getSearchAccountId() {
	return _searchAccountId;
}


/**
 * @param accountId The _searchAccountId to set.
 */
public void setSearchAccountId(int accountId) {
	_searchAccountId = accountId;
}


/**
 * @return Returns the _selectedId.
 */
public int getSelectedId() {
	return _selectedId;
}


/**
 * @param id The _selectedId to set.
 */
public void setSelectedId(int id) {
	_selectedId = id;
}

/**
 * Return the user detail object
 */
public ServiceProviderData getDetail() {
//  if ( null != _detail ) {
//    if ( null == _detail.getEmailData() ) {
//      _detail.setEmailData(EmailData.createValue());
//    }
//    if ( null == _detail.getEmailData().getEmailAddress() ) {
//      _detail.getEmailData().setEmailAddress("");
//    }
//
//  }
  return (this._detail);
}

public void setDetail(ServiceProviderData detail) {
//    mBaseUserForm.resetPermissions();
//    if ( _detail.getUserData() != null ) {
//      setId(String.valueOf(_detail.getUserData().getUserId()));
//    }
    this._detail = detail;


  }


/**
 * @return Returns the _mServiceProviderType.
 */
public String[] getServiceProviderType() {
	return _mServiceProviderType;
}


/**
 * @param serviceProviderType The _mServiceProviderType to set.
 */
public void setServiceProviderType(String[] serviceProviderType) {
	_mServiceProviderType = serviceProviderType;
}


/**
 * @return Returns the mConfFunction.
 */
public String getConfFunction() {
	return mConfFunction;
}


/**
 * @param confFunction The mConfFunction to set.
 */
public void setConfFunction(String confFunction) {
	mConfFunction = confFunction;
}

//=================== Configuration ==============================
//private String mConfFunction = "";
private boolean mConifiguredOnlyFl = false;
private boolean mRemoveSiteAssocFl = false;
private String mConfSearchField = "";
private String mConfSearchType = "nameBegins";
private String mConfCity = "";
private String mConfState = "";
private IdVector mConfPermAccountIds = null;
private boolean mConfPermAcctFilterFl = false;
private boolean mShowAllAcctFl = false;
private boolean mMergeAccountPermFl = true;
private LinkedList mConfPermKeys = null;
private HashMap mConfPermissions = null;
private HashMap mConfPermAccounts = null;
//private String mConfAccountName;
private String[] mConfAsocSiteIds = null;
private String[] mConfSelectIds = null;
private String[] mConfDisplayIds = null;
private String mSearchRefNum = "";
private String mSearchRefNumType = "nameBegins";

//public String getConfFunction() {return mConfFunction;}
//public void setConfFunction(String pVal) {mConfFunction = pVal; }

public boolean getConifiguredOnlyFl() {return mConifiguredOnlyFl;}
public void setConifiguredOnlyFl(boolean pVal) {mConifiguredOnlyFl = pVal; }

public boolean getRemoveSiteAssocFl() {return mRemoveSiteAssocFl;}
public void setRemoveSiteAssocFl(boolean pVal) {mRemoveSiteAssocFl = pVal; }

public String getConfSearchField() {return mConfSearchField;}
public void setConfSearchField(String pVal) {mConfSearchField = pVal; }

public String getConfSearchType() {return mConfSearchType;}
public void setConfSearchType(String pVal) {mConfSearchType = pVal; }

public String getConfCity() {return mConfCity;}
public void setConfCity(String pVal) {mConfCity = pVal; }

public String getConfState() {return mConfState;}
public void setConfState(String pVal) {mConfState = pVal; }

//public int getConfAccountId() {return mConfAccountId;}
//public void setConfAccountId(int pVal) {mConfAccountId = pVal; }

//public String getConfAccountName() {return mConfAccountName;}
//public void setConfAccountName(String pVal) {mConfAccountName = pVal; }

public IdVector getConfPermAccountIds() {return mConfPermAccountIds;}
public void setConfPermAccountIds(IdVector pVal) {mConfPermAccountIds = pVal; }

public boolean getConfPermAcctFilterFl() {return mConfPermAcctFilterFl;}
public void setConfPermAcctFilterFl(boolean pVal) {mConfPermAcctFilterFl = pVal; }

public boolean getShowAllAcctFl() {return mShowAllAcctFl;}
public void setShowAllAcctFl(boolean pVal) {mShowAllAcctFl = pVal; }

public boolean getMergeAccountPermFl() {return mMergeAccountPermFl;}
public void setMergeAccountPermFl(boolean pVal) {mMergeAccountPermFl = pVal; }

public LinkedList getConfPermKeys() {return mConfPermKeys;}
public void setConfPermKeys(LinkedList pVal) {mConfPermKeys = pVal; }

public HashMap getConfPermissions() {return mConfPermissions;}
public void setConfPermissions(HashMap pVal) {mConfPermissions = pVal; }
public void setConfPermForm(UserRightsForm pUrf, int pInd) {
  if(pInd!=0) {
    mConfPermissions.put(new Integer(pInd),pUrf);
  }
}
//public UserRightsForm getConfPermForm(int pInd) {
//  if(pInd==0) {
//    return mBaseUserForm;
//  }
//  UserRightsForm urf =
//          (UserRightsForm)  mConfPermissions.get(new Integer(pInd));
//  return urf;
//}

public HashMap getConfPermAccounts() {return mConfPermAccounts;}
public void setConfPermAccounts(HashMap pVal) {mConfPermAccounts = pVal; }

public String[] getConfAsocSiteIds() {return mConfAsocSiteIds;}
public void setConfAsocSiteIds(String[] pVal) {mConfAsocSiteIds = pVal; }

public String[] getConfSelectIds() {return mConfSelectIds;}
public void setConfSelectIds(String[] pVal) {mConfSelectIds = pVal; }

public String[] getConfDisplayIds() {return mConfDisplayIds;}
public void setConfDisplayIds(String[] pVal) {mConfDisplayIds = pVal; }

public String getSearchRefNum() {
    return mSearchRefNum;
}
public void setSearchRefNum(String searchRefNum) {
    this.mSearchRefNum = searchRefNum;
}
public String getSearchRefNumType() {
    return mSearchRefNumType;
}
public void setSearchRefNumType(String searchRefNumType) {
    this.mSearchRefNumType = searchRefNumType;
}


}


