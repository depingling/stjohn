
package com.cleanwise.view.logic;

import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.math.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.OrderOpSearchForm;
import com.cleanwise.view.forms.OrderOpDetailForm;
import com.cleanwise.view.forms.OrderOpItemDetailForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.view.forms.ReportLocateForm;
import java.text.SimpleDateFormat;


/**
 * <code>AnalyticReportLogic</code> implements the logic needed to
 * locate generic rports
 *
 * @author Yuriy Kupershmidt
 */
public class ReportLocateLogic {
    
   
    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request, ReportLocateForm pForm)
    throws Exception {
      //Generic report categories
       APIAccess factory = new APIAccess();
       Report reportEjb = factory.getReportAPI();
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
       int userId = appUser.getUser().getUserId();
       GenericReportViewVector reports =
                reportEjb.getReportList(userId,appUser.getUser().getUserTypeCd());
       reports = sortByCategoryName(reports);
       pForm.setReports(reports);
       setFilter(request,pForm);
       
    }
   //--------------------------------------------------------------------------
    public static void sort(HttpServletRequest request,
    ReportLocateForm pForm)
    throws Exception {
      String sortField = request.getParameter("field");
      GenericReportViewVector reports = pForm.getReports();
      if("category".equals(sortField)) {
        reports = sortByCategoryName(reports);
      } else if("id".equals(sortField)) {
        reports.sort("GenericReportId");
      } else if("report".equals(sortField)) {
        reports.sort("ReportName");
      }
      pForm.setReports(reports);
      setFilter(request, pForm);
      return;  
    }
    
    
    private static GenericReportViewVector 
                     sortByCategoryName(GenericReportViewVector reports) {
      if(reports.size()<=1) return  reports;
      Object[] reportA = reports.toArray();
      
      for(int ii=0; ii<reportA.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<reportA.length-ii-1; jj++) {
           GenericReportView grVw1 = (GenericReportView) reportA[jj];
           GenericReportView grVw2 = (GenericReportView) reportA[jj+1];
           String cat1 = grVw1.getReportCategory();
           String cat2 = grVw2.getReportCategory();
           if(cat1==null) cat1="";
           if(cat2==null) cat2="";
           int comp = cat1.compareTo(cat2);
           if(comp>0) {
             reportA[jj] = grVw2;
             reportA[jj+1] = grVw1;
             exitFl = false;
             continue;
           }
           if(comp==0) {
             String name1 = grVw1.getReportName();
             String name2 = grVw2.getReportName();
             if(name1==null) name1="";
             if(name2==null) name2="";
             comp = name1.compareTo(name2);
             if(comp>0) {
               reportA[jj] = grVw2;
               reportA[jj+1] = grVw1;
               exitFl = false;
               continue;
             }
           }
        }
        if(exitFl) break;
      }
      reports = new GenericReportViewVector();
      for(int ii=0; ii<reportA.length; ii++) {
        reports.add((GenericReportView) reportA[ii]);
      }
      return reports;
    }
    
    
    //---------------------------------------------------------------------------------------
    public static void setFilter(HttpServletRequest request, 
                                            ReportLocateForm pForm)
    throws Exception {
      String categoryFilter = pForm.getCategoryFilter();
      String reportFilter = pForm.getReportFilter();
      if(!Utility.isSet(categoryFilter) && !Utility.isSet(reportFilter)){
        pForm.setFilteredReports(pForm.getReports());
        return;
  }
      if(Utility.isSet(categoryFilter)) categoryFilter = categoryFilter.toLowerCase();
      if(Utility.isSet(reportFilter)) reportFilter = reportFilter.toLowerCase();  

      GenericReportViewVector filteredReports = new GenericReportViewVector();
      GenericReportViewVector reports = pForm.getReports();
      for(int ii=0; ii<reports.size(); ii++) {
        GenericReportView grVw = (GenericReportView) reports.get(ii);
        if(Utility.isSet(categoryFilter)) {
          String category = grVw.getReportCategory();
          if(category==null) continue;
          category = category.toLowerCase();
          int pos = category.indexOf(categoryFilter);
          if(pos<0) continue;
        }
        if(Utility.isSet(reportFilter)) {
           String report = grVw.getReportName();
           if(report==null) continue;
           report = report.toLowerCase();
           int pos = report.indexOf(reportFilter);
           if(pos<0) continue;
        }
        filteredReports.add(grVw);
      }
      pForm.setFilteredReports(filteredReports);
    }

    //---------------------------------------------------------------------------------------
    public static void clearFilter(HttpServletRequest request, 
                                            ReportLocateForm pForm)
    throws Exception {
      pForm.setCategoryFilter("");
      pForm.setReportFilter("");
      setFilter(request,pForm);
    }

   /*    
    public static boolean isDate(String pDate) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            df.parse(pDate);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    
    public static boolean isInt(String pInt) {
        try {
            Integer.parseInt(pInt);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    
    public static boolean isNumber(String pDouble) {
        try {
            Double.parseDouble(pDouble);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    */
}
    
    
    
    
