
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
import com.cleanwise.view.forms.GenericReportForm;
//import jxl.*;
//import jxl.write.*;
import java.text.SimpleDateFormat;



/**
 * <code>AnalyticReportLogic</code> implements the logic needed to
 * manipulate order records.
 *
 * @author durval
 */
public class GenericReportLogic {

    /*
    private static final String sDATE = "DATE";
    private static final String sBEG_DATE = "BEG_DATE";
    private static final String sEND_DATE = "END_DATE";
    private static final String sACCOUNT = "ACCOUNT";
    private static final String sACCOUNT_OPT = "ACCOUNT_OPT";
    private static final String sAMOUNT = "AMOUNT";
    private static final String sACCOUNT_LIST= "ACCOUNT_LIST";
    private static final String sCONTRACT= "CONTRACT";
    private static final String sDISTRIBUTOR = "DISTRIBUTOR";
    private static final String sSITE = "SITE";
    private static final String sSITE_LIST= "SITE_LIST";
    private static final String sMANUFACTURER = "MANUFACTURER";
    private static final String sMANUFACTURER_LIST = "MANUFACTURER_LIST";
    private static final String sITEM = "ITEM";
    private static final String sITEM_OPT = "ITEM_OPT";
    private static final String  sITEM_LIST ="ITEM_LIST";
    private static final String sINTERVAL = "INTERVAL";
    private static final String sDATE_TYPE_GROUPING = "DATE_TYPE_GROUPING";
    private static final String sSHIPMENT = "SHIPMENT";
    private static final String sINCLUSIVE = "INCLUSIVE";
    private static final String sSTATE_OPT = "STATE_OPT";
    private static final String sCOUNTY_OPT = "COUNTY_OPT";
    private static final String sDISTRIBUTOR_OPT = "DISTRIBUTOR_OPT";
    private static final String sCATALOG = "CATALOG";
    */
    private static final int SHOW_FILTER_NUM = 50;

    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request,
    GenericReportForm pForm)
    throws Exception {
      //Generic report categories
      initFields(request, pForm);
       APIAccess factory = new APIAccess();
       Report reportEjb = factory.getReportAPI();
       User userEjb = factory.getUserAPI();
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
       int userId = appUser.getUser().getUserId();
       GenericReportViewVector reports =
                   reportEjb.getAllReports();
       reports = sortByCategoryName(reports);
       pForm.setReports(reports);
       ArrayList categories = new ArrayList();
       String curCategory = "";
       for(int ii=0; ii<reports.size(); ii++) {
         GenericReportView grVw = (GenericReportView) reports.get(ii);
         String category = grVw.getReportCategory();
         if(category==null) continue;
         if(curCategory.equals(category)) continue;
         categories.add(category);
         curCategory = category;
       }
       pForm.setCategories(categories);
       if(reports.size()>SHOW_FILTER_NUM) {
         pForm.setShowFiltesFl(true);
       } else {
         pForm.setShowFiltesFl(false);
       }
       setFilter(request,pForm);

    }
   //--------------------------------------------------------------------------
    public static void sort(HttpServletRequest request,
    GenericReportForm pForm)
    throws Exception {
      String sortField = request.getParameter("field");
      GenericReportViewVector reports = pForm.getReports();
      if("category".equals(sortField)) {
        reports = sortByCategoryName(reports);
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


    public static void initFields(HttpServletRequest request,
                                            GenericReportForm pForm)
    throws Exception {
        pForm.setReportId(0);
        //pForm.setCategoryFilter("");
        //pForm.setReportFilter("");
        //pForm.setReports(new GenericReportViewVector());
        //pForm.setFilteredReports(new GenericReportViewVector());
        return;
    }

    //---------------------------------------------------------------------------------------
    public static void setFilter(HttpServletRequest request,
                                            GenericReportForm pForm)
    throws Exception {
      String categoryFilter = pForm.getCategoryFilter();
      String reportFilter = pForm.getReportFilter();
      String reportDescFilter = pForm.getReportDescFilter();
      if(!Utility.isSet(categoryFilter) &&
         !Utility.isSet(reportFilter)&&
         !Utility.isSet(reportDescFilter)){
        pForm.setFilteredReports(pForm.getReports());
        return;
      }
      if(Utility.isSet(reportFilter)) reportFilter = reportFilter.toLowerCase();
      if(Utility.isSet(reportDescFilter)) reportDescFilter = reportDescFilter.toLowerCase();

      GenericReportViewVector filteredReports = new GenericReportViewVector();
      GenericReportViewVector reports = pForm.getReports();
      for(int ii=0; ii<reports.size(); ii++) {
        GenericReportView grVw = (GenericReportView) reports.get(ii);
        if(Utility.isSet(categoryFilter)) {
          String category = grVw.getReportCategory();
          if(category==null) continue;
          if(!category.equals(categoryFilter)) continue;
        }
        if(Utility.isSet(reportFilter)) {
           String report = grVw.getReportName();
           if(report==null) continue;
           report = report.toLowerCase();
           int pos = report.indexOf(reportFilter);
           if(pos<0) continue;
        }
        if(Utility.isSet(reportDescFilter)) {
           String longDesc = grVw.getLongDesc();
           if(longDesc==null) continue;
           longDesc = longDesc.toLowerCase();
           int pos = longDesc.indexOf(reportDescFilter);
           if(pos<0) continue;
        }
        filteredReports.add(grVw);
      }
      pForm.setFilteredReports(filteredReports);
    }

    //---------------------------------------------------------------------------------------
    public static void clearFilter(HttpServletRequest request,
                                            GenericReportForm pForm)
    throws Exception {
      pForm.setCategoryFilter("");
      pForm.setReportFilter("");
      pForm.setReportDescFilter("");
      setFilter(request,pForm);
    }

    //---------------------------------------------------------------------------------------
    public static ActionErrors loadReport(HttpServletRequest request, GenericReportForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      String reportIdS = request.getParameter("id");
      int reportId = Integer.parseInt(reportIdS);
      errors = loadReport(request, pForm, reportId);
      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }

    private static ActionErrors loadReport(HttpServletRequest request, GenericReportForm pForm, int pReportId)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      pForm.setReportId(pReportId);
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      GenericReportData reportD = reportEjb.getGenericReport(pReportId);
      pForm.setReport(reportD);
      String[] sql = reportEjb.getGenericReportSql(pReportId);
      pForm.setSqlText(sql[0]);
      pForm.setScriptText(sql[1]);
      String repUserTypes= reportD.getUserTypes();
      String[] repUserTypesList = null;
      if (Utility.isSet(repUserTypes )){
         repUserTypesList = repUserTypes.split(",");
         pForm.setTypeSelected(repUserTypesList);
      }
      ArrayList allTypes = new ArrayList();
      String repUserTypesAutorized ="";
      Object worker = reportEjb.getInstanceReport(reportD);
      if (worker!= null && worker instanceof ReportInterf ){
        repUserTypesAutorized = ((ReportInterf)worker).getUserTypesAutorized();
      }
      if (Utility.isSet(repUserTypesAutorized)){
        String[] listOfTypes = repUserTypesAutorized.split(",");
        for (int i = 0; i < listOfTypes.length; i++) {
          allTypes.add(listOfTypes[i]);
        }
      } else {
        allTypes.add(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR);
        allTypes.add(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR);
        allTypes.add(RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR);
        allTypes.add(RefCodeNames.USER_TYPE_CD.CUSTOMER);
        allTypes.add(RefCodeNames.USER_TYPE_CD.MSB);
        allTypes.add(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR);
        allTypes.add(RefCodeNames.USER_TYPE_CD.REPORTING_USER);
        allTypes.add(RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER);
      }
      pForm.setUserTypes(allTypes);
      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }

    //---------------------------------------------------------------------------------------
    public static ActionErrors saveReport(HttpServletRequest request, GenericReportForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      GenericReportData reportD = pForm.getReport();
      String reportName = reportD.getName();
      if(!Utility.isSet(reportName)) {
        String errorMess = "Report name cannot be empty";
        errors.add("error",new ActionError("error.simpleGenericError",errorMess));
        return errors;
      }
      reportD.setName(reportName.trim());
      String sql = pForm.getSqlText();
      String script = pForm.getScriptText();
      String[] selectedUserTypes = pForm.getTypeSelected();
      String repUserTypes = (selectedUserTypes.length>0) ? selectedUserTypes[0] : "";
      for (int i = 1; i < selectedUserTypes.length; i++) {
        repUserTypes += ","+ selectedUserTypes[i];
      }
      reportD.setUserTypes(repUserTypes);
      reportEjb.saveGenericReport(reportD, sql, script, appUser.getUser().getUserName());
      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }

    //---------------------------------------------------------------------------------------
    public static ActionErrors cloneReport(HttpServletRequest request, GenericReportForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      GenericReportData reportD = pForm.getReport();
      reportD.setGenericReportId(0);
      String name = Utility.strNN(reportD.getName());
      name = "Clone of "+name;
      if(name.length()>50) name = name.substring(0,50);
      reportD.setName(name);
      String sql = pForm.getSqlText();
      String script = pForm.getScriptText();
      int reportId = reportEjb.saveGenericReport(reportD, sql, script, appUser.getUser().getUserName());
      init(request,pForm);
      errors = loadReport(request,pForm,reportId);
      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }
    //---------------------------------------------------------------------------------------
    public static ActionErrors deleteReport(HttpServletRequest request, GenericReportForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      GenericReportData reportD = pForm.getReport();
      String name = Utility.strNN(reportD.getName());
      if(name.indexOf("@@@")!=0) {
        String errorMess = "Report name must start with @@@ to be deleted";
        errors.add("error",new ActionError("error.simpleGenericError",errorMess));
        return errors;
      }
      reportEjb.deleteGenericReport(reportD.getGenericReportId());
      pForm.setReportId(0);
      init(request,pForm);
      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }

    private static Object stringToObject (String pStr) {
      Object obj = null;
      char[] charArr = pStr.toCharArray();
      byte[] byteArr = new byte[charArr.length];
      for(int ii=0; ii<byteArr.length; ii++) {
        byteArr[ii] = (byte) charArr[ii];
      }
      java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream(byteArr);
      try {
        java.io.ObjectInputStream is = new java.io.ObjectInputStream(iStream);
        obj =  is.readObject();
        is.close();
        iStream.close();
      } catch(Exception exc) {
        exc.printStackTrace();
      }
      return obj;
    }

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

    /*
    private int mGenericReportId;// SQL type:NUMBER, not null
    private String mAddBy;// SQL type:VARCHAR2
    private String mCategory;// SQL type:VARCHAR2, not null
    private String mClassname;// SQL type:VARCHAR2
    private String mGenericReportType;// SQL type:VARCHAR2
    private String mInterfaceTable;// SQL type:VARCHAR2
    private String mLongDesc;// SQL type:VARCHAR2
    private String mModBy;// SQL type:VARCHAR2
    private String mName;// SQL type:VARCHAR2, not null
    private String mParameterToken;// SQL type:VARCHAR2
    private String mReportSchemaCd;// SQL type:VARCHAR2
    private String mRuntimeEnabled;// SQL type:VARCHAR2
    private Clob mScriptText;// SQL type:CLOB
    private Clob mSqlText;// SQL type:CLOB
    private String mSupplementaryControls;// SQL type:VARCHAR2
     */
}




