/**
 * Title:        ReportLocateForm
 * Description:  This is the Struts ActionForm class for
 * report search
 * Purpose:      Strut support to search for reports.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       YKupershmidt
 */

package com.cleanwise.view.forms;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.service.api.value.*;

public final class ReportLocateForm extends ActionForm {
    //Report list
    private String mCategoryFilter = "";
    private String mReportFilter = "";
    private GenericReportViewVector mReports = new GenericReportViewVector();
    private GenericReportViewVector mFilteredReports = new GenericReportViewVector();

    public String getCategoryFilter(){return mCategoryFilter;}
    public void setCategoryFilter(String pCategoryFilter) {mCategoryFilter = pCategoryFilter;}

    public String getReportFilter(){return mReportFilter;}
    public void setReportFilter(String pReportFilter) {mReportFilter = pReportFilter;}
    
    public GenericReportViewVector getReports(){return mReports;}
    public void setReports(GenericReportViewVector pReports) {
        mReports = pReports;
    }
    
    public GenericReportViewVector getFilteredReports(){return mFilteredReports;}
    public void setFilteredReports(GenericReportViewVector pFilteredReports) {
        mFilteredReports = pFilteredReports;
    }
    

    /**
     * <code>reset</code> method
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
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
    
    
    
    
}
