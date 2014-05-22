/**
 * Title:        AnalyticReportForm
 * Description:  This is the Struts ActionForm class for
 * ayalytic report pages
 * Purpose:      Strut support to search for orders.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       YKupershmidt
 */

package com.cleanwise.view.forms;

import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cleanwise.service.api.value.*;

/**
 * Form bean for the user manager page.
 *
 * @author Tim Besser
 */
public final class AnalyticReportForm extends StorePortalBaseForm {
    //Report list
    private int mRequestToken = 0;
    private BusEntityDataVector mUserAccounts = null;
    private boolean mShowFiltesFl = true;
    private boolean mShowCateogryFl = true;
    private String mCategoryFilter = "";
    private String mReportFilter = "";
    private String mReportDescFilter = "";
    private String mArchCategoryFilter = "";
    private String mArchReportFilter = "";
    private String mArchMinDateFilter = "";
    private String mArchMaxDateFilter = "";
    private GenericReportViewVector mReports = new GenericReportViewVector();
    private GenericReportViewVector mFilteredReports = new GenericReportViewVector();
    private ArrayList mCategories = new ArrayList();
    private String[] mResultSelected = new String[0];
    //private boolean mAllUserReportsFl = true;
    private boolean mMyReportsFl = true;
    private boolean mMyReportsFlBase = true;
    
    private String mInvoiceStatus      = "";

    //Report to run
    private int mReportId = 0;
    private GenericReportData mReport = null;
    private GenericReportControlViewVector mGenericControls;
    private boolean mSavePrevVersion = false;
    //Prepared reports
    private PreparedReportViewVector mPreparedReports =
                                                new PreparedReportViewVector();
    //Filter
    SiteViewVector mSiteFilter=new SiteViewVector();

    private AccountUIViewVector mAccountFilter = new AccountUIViewVector();
    private GenericReportResultViewVector mReportResults = new GenericReportResultViewVector();
    private int mGenericReportPageNum = 0;
    private int selectedStoreDimId = 0;
    private String mFormat;

     //Initialization
    String initialMinDate() {
      Date date = new Date();
      GregorianCalendar greCal = new GregorianCalendar();
      greCal.setTime(date);
      greCal.add(GregorianCalendar.DATE, -7);
      date = greCal.getTime();
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      return sdf.format(date);
    }
    //---------------------------------------------------------------------------
    public int getRequestToken(){return mRequestToken;}
    public void setRequestToken(int pValue) {mRequestToken=pValue;}
    //---------------------------------------------------------------------------
    public BusEntityDataVector getUserAccounts(){return mUserAccounts;}
    public void setUserAccounts(BusEntityDataVector pUserAccounts) {mUserAccounts = pUserAccounts;}

    public boolean getShowFiltesFl(){return mShowFiltesFl;}
    public void setShowFiltesFl(boolean pShowFiltesFl) {mShowFiltesFl = pShowFiltesFl;}

    public boolean getShowCateogryFl(){return mShowCateogryFl;}
    public void setShowCateogryFl(boolean pShowCateogryFl) {mShowCateogryFl = pShowCateogryFl;}

    public String getCategoryFilter(){return mCategoryFilter;}
    public void setCategoryFilter(String pCategoryFilter) {mCategoryFilter = pCategoryFilter;}

    public String getReportFilter(){return mReportFilter;}
    public void setReportFilter(String pReportFilter) {mReportFilter = pReportFilter;}

    public String getReportDescFilter(){return mReportDescFilter;}
    public void setReportDescFilter(String pReportDescFilter) {mReportDescFilter = pReportDescFilter;}

    public String getArchCategoryFilter(){return mArchCategoryFilter;}
    public void setArchCategoryFilter(String pArchCategoryFilter) {mArchCategoryFilter = pArchCategoryFilter;}

    public String getArchReportFilter(){return mArchReportFilter;}
    public void setArchReportFilter(String pArchReportFilter) {mArchReportFilter = pArchReportFilter;}

    public String getArchMinDateFilter(){return mArchMinDateFilter;}
    public void setArchMinDateFilter(String pArchMinDateFilter) {mArchMinDateFilter = pArchMinDateFilter;}

    public String getArchMaxDateFilter(){return mArchMaxDateFilter;}
    public void setArchMaxDateFilter(String pArchMaxDateFilter) {mArchMaxDateFilter = pArchMaxDateFilter;}

    public GenericReportViewVector getReports(){return mReports;}
    public void setReports(GenericReportViewVector pReports) {
        mReports = pReports;
    }

    public ArrayList getCategories(){return mCategories;}
    public void setCategories(ArrayList pCategories) {
        mCategories = pCategories;
    }


    public GenericReportViewVector getFilteredReports(){return mFilteredReports;}
    public void setFilteredReports(GenericReportViewVector pFilteredReports) {
        mFilteredReports = pFilteredReports;
    }
    
    /**
     * <code>getInvoiceStatus</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getInvoiceStatus() {
        return (this.mInvoiceStatus);
    }

    /**
     * <code>setInvoiceStatus</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setInvoiceStatus(String pVal) {
        this.mInvoiceStatus = pVal;
    }
    /*
    public boolean getAllUserReportsFl(){return mAllUserReportsFl;}
    public void setAllUserReportsFl(boolean pAllUserReportsFl) {
        mAllUserReportsFl = pAllUserReportsFl;
    }
     */
    public boolean getMyReportsFl(){return mMyReportsFl;}
    public void setMyReportsFl(boolean pMyReportsFl) {mMyReportsFl = pMyReportsFl;}

    public boolean getMyReportsFlBase(){return mMyReportsFlBase;}
    public void setMyReportsFlBase(boolean pMyReportsFlBase)
                                   {mMyReportsFlBase = pMyReportsFlBase;}
    //------------------------------------------------------------------------------
    public int getReportId(){return mReportId;}
    public void setReportId(int pReportId) {mReportId = pReportId;}

    public GenericReportData getReport(){return mReport;}
    public void setReport(GenericReportData pReport) {mReport = pReport;}

    public boolean getSavePrevVersion(){return mSavePrevVersion;}
    public void setSavePrevVersion(boolean pSavePrevVersion) {mSavePrevVersion = pSavePrevVersion;}

    public GenericReportControlViewVector getGenericControls() {return mGenericControls;}
    public void setGenericControls(GenericReportControlViewVector genericControls) {
        mGenericControls = genericControls;
    }

    //Generic controls individual access
    public String getGenericControlValue(int index) {
        if(mGenericControls==null || index>=mGenericControls.size()) {
            return null;
        }
        GenericReportControlView grcVw =
                           (GenericReportControlView) mGenericControls.get(index);
        String value = grcVw.getValue();
        return value ;
    }

    public void setGenericControlValue(int index, String value) {
        if(mGenericControls==null || index>=mGenericControls.size()) {
            return;
        }
        GenericReportControlView grcVw =
                         (GenericReportControlView) mGenericControls.get(index);
        grcVw.setValue(value);

    }
    //--------------------------------------------------------------------------
    public PreparedReportViewVector getPreparedReports(){return mPreparedReports;}
    public void setPreparedReports(PreparedReportViewVector pPreparedReports) {mPreparedReports = pPreparedReports;}

    public String[] getResultSelected(){return mResultSelected;}
    public void setResultSelected(String[] pResultSelected) {mResultSelected = pResultSelected;}

    /**
     * <code>reset</code> method
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
      mMyReportsFl = false;
      mSavePrevVersion = false;
      mResultSelected = new String[0];
      mRunForAccounts = new String[0];
      if (mGenericControls != null ){
        for (int i = 0; i < mGenericControls.size(); i++) {
          GenericReportControlView grc = (GenericReportControlView)mGenericControls.get(i);
          if (grc.getName().contains("DW_CONNECT_CUST")){
            grc.setValue(null);
          }
        }
      }
    }

    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
    HttpServletRequest request) {

        // Perform the run validation.
        ActionErrors errors = new ActionErrors();
        return errors;
    }


    private String[] mRunForAccounts;
  private ManufacturerDataVector manufFilter;
  private DistributorDataVector distFilter;
  private ItemViewVector itemFilter;
  private UserDataVector storeUsers;
  private boolean mScheduleFl = false;
  private boolean specialFl;
  private boolean showOnlyDownloadButtonFl;
  private CatalogDataVector catalogFilter;

  public String[] getRunForAccounts() {
        return this.mRunForAccounts;
    }

    public void setRunForAccounts(String[] pRunForAccounts) {
        this.mRunForAccounts = pRunForAccounts;
    }

    public SiteViewVector getSiteFilter() {
        return mSiteFilter;
    }

    public void setSiteFilter(SiteViewVector mSiteFilter) {
        this.mSiteFilter = mSiteFilter;
    }

    public AccountUIViewVector getAccountFilter() {
        return mAccountFilter;
    }

    public void setAccountFilter(AccountUIViewVector accountFilter) {
    	mAccountFilter = accountFilter;
    }

  public void setManufFilter(ManufacturerDataVector manufFilter) {
    this.manufFilter = manufFilter;
  }

  public void setDistFilter(DistributorDataVector distFilter) {
    this.distFilter = distFilter;
  }

  public void setItemFilter(ItemViewVector itemFilter) {
    this.itemFilter = itemFilter;
  }

  public void setStoreUsers(UserDataVector storeUsers) {
    this.storeUsers = storeUsers;
  }

  public ManufacturerDataVector getManufFilter() {
    return manufFilter;
  }

  public DistributorDataVector getDistFilter() {
    return distFilter;
  }

  public ItemViewVector getItemFilter() {
    return itemFilter;
  }

  public UserDataVector getStoreUsers() {
    return storeUsers;
  }

  public GenericReportResultViewVector getReportResults() {
    return mReportResults;
  }

  public void setReportResults(GenericReportResultViewVector v) {
    this.mReportResults = v;
  }

  public int getGenericReportPageNum() {
    return mGenericReportPageNum;
  }

  public void setGenericReportPageNum(int v) {
    this.mGenericReportPageNum = v;
  }

    public void setSelectedStoreDimId(int selectedStoreDimId) {
        this.selectedStoreDimId = selectedStoreDimId;
    }

  public void setSpecialFl(boolean specialFl) {
    this.specialFl = specialFl;
  }

  public void setScheduleFl(boolean pScheduleFl) {
    this.mScheduleFl = pScheduleFl;
  }

  public void setShowOnlyDownloadButtonFl(boolean v) {
      this.showOnlyDownloadButtonFl = v;
  }

  public int getSelectedStoreDimId() {
        return selectedStoreDimId;
    }

  public boolean isSpecialFl() {
    return specialFl;
  }

  public boolean getScheduleFl() {
    return mScheduleFl;
  }

  public boolean isShowOnlyDownloadButtonFl() {
      return showOnlyDownloadButtonFl;
  }

  public String getFormat() {
        return mFormat;
    }

    public void setFormat(String format) {
        this.mFormat = format;
    }
	public void setCatalogFilter(CatalogDataVector catalogFilter) {
		this.catalogFilter = catalogFilter;
	}
	public CatalogDataVector getCatalogFilter() {
		return catalogFilter;
	}
    
}
