/**
 * Title:        GenericReportForm
 * Description:  This is the Struts ActionForm class to 
 * administrate CLW_GENERIC_REPORT table
 * Purpose:      Create, modify and delete generic reports
 * Copyright:    Copyright (c) 2005
 * Company:      CleanWise, Inc.
 * @author       YKupershmidt
 */

package com.cleanwise.view.forms;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cleanwise.service.api.value.*;
import java.util.ArrayList;

/**
 * Form bean for the user manager page.
 *
 * @author Tim Besser
 */
public final class GenericReportForm extends ActionForm {
    //Report list
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

    //Report to edit
    private int mReportId = 0;
    private GenericReportData mReport = null;
    private String mSqlText = "";
    private String mScriptText = "";
    
       
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
    //------------------------------------------------------------------------------
    public int getReportId(){return mReportId;}
    public void setReportId(int pReportId) {mReportId = pReportId;}
    
    public GenericReportData getReport(){return mReport;}
    public void setReport(GenericReportData pReport) {mReport = pReport;}
        
    public String[] getResultSelected(){return mResultSelected;}
    public void setResultSelected(String[] pResultSelected) {mResultSelected = pResultSelected;}

    public String getSqlText(){return mSqlText;}
    public void setSqlText(String pSqlText) {mSqlText = pSqlText;}

    public String getScriptText(){return mScriptText;}
    public void setScriptText(String pScriptText) {mScriptText = pScriptText;}
    /**
     * <code>reset</code> method
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
      mResultSelected = new String[0];
      mRunForAccounts = new String[0];
      typeSelected = new String[0];
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
  private String[] typeSelected;
  private ArrayList userTypes;
  public String[] getRunForAccounts() {
        return this.mRunForAccounts;
    }
    
    public void setRunForAccounts(String[] pRunForAccounts) {
        this.mRunForAccounts = pRunForAccounts;
    }

  public void setTypeSelected(String[] typeSelected) {
    this.typeSelected = typeSelected;
  }

  public void setUserTypes(ArrayList userTypes) {
    this.userTypes = userTypes;
  }

  public String[] getTypeSelected() {
    return typeSelected;
  }

  public ArrayList getUserTypes() {
    return userTypes;
  }

}
