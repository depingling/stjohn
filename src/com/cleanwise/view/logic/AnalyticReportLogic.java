
package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.reporting.TradingPartnerReport;
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.session.Report;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.tree.ReportFactory;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.tree.ReportTransformer;
import com.cleanwise.service.api.tree.transformer.HttpRequestReportTransformer;
import com.cleanwise.service.api.tree.xml.ReportXmlFactory;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.AnalyticReportForm;
import com.cleanwise.view.forms.LocateReportAccountForm;
import com.cleanwise.view.utils.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.log4j.Logger;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.XMLSerializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.cleanwise.view.forms.ReportingLocateStoreSiteForm;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.session.DWOperation;

import java.text.*;

import com.cleanwise.view.i18n.ClwI18nUtil;
import org.w3c.dom.*;
import javax.xml.parsers.*;


import org.owasp.esapi.ESAPI;
import com.cleanwise.service.api.util.*;


/**
 * <code>AnalyticReportLogic</code> implements the logic needed to
 * manipulate order records.
 *
 * @author durval
 */
public class AnalyticReportLogic {

    private static String className = "AnalyticReportLogic";

    private static final Logger log = Logger.getLogger(AnalyticReportLogic.class);
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
    private static final int MONTH = 0;
    private static final int DAY = 1;
    private static final int YEAR = 2;
    private static final int INITIAL_YEAR = 1999;
    private static final int SHOW_FILTER_NUM = 5;//50;


    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request,
    AnalyticReportForm pForm)
    throws Exception {
      //Generic report categories
       initFields(request, pForm);
       APIAccess factory = new APIAccess();
       Report reportEjb = factory.getReportAPI();
       User userEjb = factory.getUserAPI();
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
       int userId = appUser.getUser().getUserId();
       BusEntityDataVector userAccounts = userEjb.getUserSiteAccounts(userId);
       pForm.setUserAccounts(userAccounts);
       //init storeId
       int storeId;
       if(appUser.getUserStore() != null){
           storeId = appUser.getUserStore().getStoreId();
       }else if(appUser.getStores() != null && ! appUser.getStores().isEmpty()){
           storeId = ((BusEntityData)appUser.getStores().get(0)).getBusEntityId();
       }else{
           storeId = 0;
       }
       log.info("TTTT storeId = " + storeId);
       //init reporting users for store
       UserDataVector storeUsers = getStoreReportingUsers(request, storeId);
       pForm.setStoreUsers(storeUsers);

       GenericReportViewVector reports =
                   reportEjb.getReportList(userId, appUser.getUser().getUserTypeCd());

       ReportingUtils.restrictNotSupportedReports(reports);
 
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

       if (session.getAttribute("REPORT_CONTROL_FILTER_MAP") == null) {
           session.setAttribute("REPORT_CONTROL_FILTER_MAP", new HashMap());
       }else {
         //try to populate Generic Controls values from session Filter
         GenericReportControlViewVector grcVV = pForm.getGenericControls();
         if (grcVV != null) {
           for (int j = 0; j < grcVV.size(); j++) {
             GenericReportControlView grc = (GenericReportControlView) grcVV.get(j);
             String name = grc.getName();
             Object value = getControlFilter(request, name);
             if (value != null && value instanceof String) {
               grc.setValue( (String) getControlFilter(request, name));
             }
           }
         }
         //

       }
    }
   //--------------------------------------------------------------------------
    public static void sort(HttpServletRequest request,
    AnalyticReportForm pForm)
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

    /**
     *sorts the generic reports by category and then name
     */
    public static GenericReportViewVector
                     sortByCategoryName(GenericReportViewVector reports) {
      if(reports.size()<=1) return  reports;
      Collections.sort(reports, ClwComparatorFactory.getReportCategoryNameComparator());
      return reports;
    }


    public static void initFields(HttpServletRequest request,
                                            AnalyticReportForm pForm)
    throws Exception {
        pForm.setReportId(0);
        pForm.setGenericControls(null);


        pForm.setMyReportsFl(true);
        //pForm.setCategoryFilter("");
        //pForm.setReportFilter("");
        //pForm.setReports(new GenericReportViewVector());
        //pForm.setFilteredReports(new GenericReportViewVector());
        return;
    }

    //---------------------------------------------------------------------------------------
    public static void setFilter(HttpServletRequest request,
                                            AnalyticReportForm pForm)
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
                                            AnalyticReportForm pForm)
    throws Exception {
      pForm.setCategoryFilter("");
      pForm.setReportFilter("");
      pForm.setReportDescFilter("");
      setFilter(request,pForm);
    }

    //---------------------------------------------------------------------------------------
    public static ActionErrors reportRequest(HttpServletRequest request, AnalyticReportForm pForm)
    throws Exception
    {
      try {
      ActionErrors errors = new ActionErrors();
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      String reportIdS = request.getParameter("id");
      int reportId = Integer.parseInt(reportIdS);
      pForm.setReportId(reportId);
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      GenericReportControlViewVector grcVwV = reportEjb.getGenericReportControls(reportId);

      initControls(request,grcVwV);

      pForm.setGenericControls(grcVwV);
      GenericReportData reportD = reportEjb.getGenericReport(reportId);
      pForm.setReport(reportD);
      Object worker = reportEjb.getInstanceReport(reportD) ;
      pForm.setSpecialFl(worker!= null && worker instanceof ReportInterf  && ((ReportInterf)worker).isSpecial());
      pForm.setShowOnlyDownloadButtonFl(worker!=null && worker instanceof ReportShowProperty && ((ReportShowProperty)worker).showOnlyDownloadReportButton());

      return errors;
      } catch (Exception exc) {
        exc.printStackTrace();
        throw exc;
      }
    }


    private static void initControls(HttpServletRequest request, GenericReportControlViewVector grcVwV) throws Exception {
        if (!grcVwV.isEmpty()) {
            Iterator it = grcVwV.iterator();
            GenericReportControlFactory controlFactory = new GenericReportControlFactory();
            while (it.hasNext()) {
                GenericReportControlView control = (GenericReportControlView) it.next();
                log.info("initControls(): control = " + control);
                try {
                    controlFactory.initControl(request, control);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //---------------------------------------------------------------------------------------
    public static ActionErrors viewReport(HttpServletRequest request,
                                         HttpServletResponse res,
                                         AnalyticReportForm pForm)
    throws Exception
    {
        return runReport(request, res, pForm, false);
    }



    //----------------------------------------------------------------------------------------
    public static ActionErrors runReport(HttpServletRequest request,
            HttpServletResponse res, AnalyticReportForm pForm) throws Exception {
        return runReport(request, res, pForm, true);
    }
    public static ActionErrors runReport(HttpServletRequest request,
                                         HttpServletResponse res,
                                         AnalyticReportForm pForm,
                                         boolean isDownload)
    throws Exception
    {
        ActionErrors errors = new ActionErrors();
        try {
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            String userName = appUser.getUser().getUserName();
            int userId = appUser.getUser().getUserId();
            APIAccess factory = new APIAccess();
            Report reportEjb = factory.getReportAPI();
            GenericReportData reportD = pForm.getReport();
            Map params = new HashMap();
            Map controls = new HashMap();
            String rType = reportD.getCategory();
            String rName = reportD.getName();
            String rAuthorizedTypes = reportD.getUserTypes();
            Object sessionFilterValue = null;
            params.put("REPORT_NAME", rName );
            //check if they are authorized,  this report should not be displayed to user,
            //so this really shouldn't happen
            if(!(appUser.isAuthorizedForReport(rName))){
                errors.add(rName, new ActionError("error.systemError", "NOT AUTHORIZED"));
                return errors;
            }
            String pstr = "Report parameters--";
            GenericReportControlViewVector grcVV = pForm.getGenericControls();

            //GenericReportViewVector reports =
            //    reportEjb.getReportList(userId, appUser.getUser().getUserTypeCd());

            String dateFmt = getDateFmt(grcVV); // get Input Date Format, if it was passed as a parameter from the data entry screen

            boolean inputDateFormatFl = true; // true => Date Format was passed as a parameter from the data entry screen
            // get user Locale input Date Format
            String inputDateFormat = ClwI18nUtil.getCountryDateFormat(request);
            log.info("***NNN_11042009: inputDateFormat = " + inputDateFormat);
            if (!Utility.isSet(inputDateFormat)) { // single Input Date Format is NOT predefined for the user's Locale
                String mesg = "Input Date Format is not defined for the user Locale. Call Customer Service";
                errors.add("Error", new ActionError("error.simpleGenericError", mesg));
    			return errors;
            } else { // single Input Date Format is predefined for the user's Locale
         	   if (Utility.isSet(dateFmt) && !dateFmt.toLowerCase().trim().equals(inputDateFormat.toLowerCase().trim())) {
                    String mesg = "Input Date Format " + dateFmt + " is DIFFERENT from user Locale Date Format = " + inputDateFormat + "."
                                  + " Enter user Locale Date Format.";
                    errors.add("Error", new ActionError("error.simpleGenericError", mesg));
    				return errors;
         	   }
        	   if (!Utility.isSet(dateFmt)) { // is DATE_FMT parameter provided (does it have value) ?
            	   // DATE_FMT parameter value is NOT provided =>
        		   // set up Date Format = user Locale Date Format
        		   dateFmt = inputDateFormat;
                   inputDateFormatFl = false; // Date Format was NOT passed as a parameter from the data entry screen
        	   } else {
        		   dateFmt = inputDateFormat; // Example: user will be able to enter Date Format mm/dd/yyyy
        		                              // instead of MM/dd/yyyy
        	   }
            }
            /***
            // check the correctness of the date format(DATE_FMT), passed from the screen as a parameter
            if (Utility.isSet(dateFmt) ){
              errors = checkDateFmt("DATE_FMT", dateFmt, errors);
              if (errors.size() > 0) {
                return errors;
              }
            }
            ***/

            for(int ii=0; ii<grcVV.size(); ii++) {
                GenericReportControlView grc =
                    (GenericReportControlView) grcVV.get(ii);
                String name = grc.getName();
                String label = grc.getLabel();
                if(!Utility.isSet(label)) label = name;
                String mf = grc.getMandatoryFl();
                if(mf!=null) mf = mf.toUpperCase();
                boolean mandatoryFl = true;
                if("N".equals(mf) || "NO".equals(mf) ||
                   "0".equals(mf) ||"F".equals(mf) ||
                   "FALSE".equals(mf)) {
                    mandatoryFl = false;
                }
                if(name!=null && name.trim().toUpperCase().endsWith("_OPT")) {
                    mandatoryFl = false;
                    name = name.trim();
                    name = name.substring(0, name.length() - 4);
                }

                String value = grc.getValue();
                if(value != null){
                        value = value.trim();
                        sessionFilterValue = value;
                }

                if("CUSTOMER".equalsIgnoreCase(name)) {
                    mandatoryFl = false;
                    if(value==null ||value.trim().length()==0) {
                      value = ""+appUser.getUser().getUserId();
                    }
                }

                if(name.endsWith("_MULTI")){
                    String type = name.substring(0, name.length() - 6);
                    StringTokenizer tok = new StringTokenizer(value,",");
                    while(tok.hasMoreTokens()){
                    	String value2 = tok.nextToken();
                    	checkParamSecurity(type,errors,value2,factory,appUser, grc, rAuthorizedTypes);
                    }
                }else {
                	checkParamSecurity(name,errors,value,factory,appUser, grc, rAuthorizedTypes);
                }

//                if (name.contains("DW_LOCATE_MANUFACTURER")){
                if (name.contains("LOCATE_MANUFACTURER")){
                  if (pForm.getLocateStoreManufForm() != null){
                    //ManufacturerDataVector manufV = pForm.getLocateStoreManufForm().getManufacturersToReturn();
                    ManufacturerDataVector manufV = pForm.getManufFilter();
                    String strManufIds = "";
                    value = "";
                    if (manufV != null && manufV.size() > 0) {
                      for (int indexM = 0; indexM < manufV.size(); indexM++) {
                        ManufacturerData manuf = (ManufacturerData) manufV.get(
                            indexM);
                        String strId = Integer.toString(manuf.getBusEntity().
                            getBusEntityId());
                        if (indexM > 0) {
                          strManufIds += ",";
                        }
                        strManufIds += strId;
                      }
                      value = strManufIds;
                      sessionFilterValue = value;
                    }
                    grc.setValue(value);
                  }
                }

                if(name.contains("INVOICE_STATUS_SELECT")) {
				   log.info("********************AAAAAAAA: INVOICE_STATUS_SELECT_OPT parameter found");
				   log.info("********************BBBBBBBB: value of INVOICE_STATUS_SELECT_OPT parameter is " + pForm.getInvoiceStatus());
                    mandatoryFl = false;
                    if (pForm.getInvoiceStatus() != null){
                       if(pForm.getInvoiceStatus().trim().equals("-Select-")) {
                          value = "";
                       } else {
                    	   value = pForm.getInvoiceStatus();
                       }
   					   log.info("********************CCCCCCCC: INVOICE_STATUS_SELECT_OPT value = " + value);
					   grc.setValue(value);
                    }					
                }
                if (name.contains("DW_LOCATE_DISTRIBUTOR")){
                  if (pForm.getLocateStoreDistForm() != null){
//                    DistributorDataVector distV = pForm.getLocateStoreDistForm().getDistributorsToReturn();
                    DistributorDataVector distV = pForm.getDistFilter();
                    String strDistIds = "";
                    value = "";
                    if (distV != null && distV.size() > 0) {
                      for (int indexD = 0; indexD < distV.size(); indexD++) {
                        DistributorData dist = (DistributorData) distV.get(indexD);
                        String strId = Integer.toString(dist.getBusEntity().getBusEntityId());
                        if (indexD > 0) {
                          strDistIds += ",";
                        }
                        strDistIds += strId;
                      }
                      value = strDistIds;
                      sessionFilterValue = value;
                    }
                    grc.setValue(value);
                  }
                }
//                if (name.contains("DW_LOCATE_ITEM")){
                if (name.contains("LOCATE_ITEM")){
                   ItemViewVector itemV = pForm.getItemFilter();
                     String strItemIds = "";
                     value = "";
                     if (itemV != null && itemV.size() > 0) {
                       for (int indexI = 0; indexI < itemV.size(); indexI++) {
                         ItemView item = (ItemView) itemV.get(indexI);
                         String strId = Integer.toString(item.getItemId());
                         if (indexI > 0) {
                           strItemIds += ",";
                         }
                         strItemIds += strId;
                       }
                       value = strItemIds;
                       sessionFilterValue = value;
                     }
                     grc.setValue(value);
                 }

//                if ("LOCATE_ACCOUNT_MULTI".equalsIgnoreCase(name)) {
                if (name.contains("LOCATE_ACCOUNT_MULTI")) {
                    AccountUIViewVector accounts = pForm.getAccountFilter();
                    String strAccountIds = "";
                    value = "";
                    if (  accounts != null && accounts.size() > 0) {
                        for (int accountIndex = 0; accountIndex < accounts.size(); ++accountIndex) {
                            AccountUIView account = (AccountUIView) accounts.get(accountIndex);
//                            String strAccountId = Integer.toString(account.getBusEntity().getBusEntityId());
                            String strAccountId ="";
                            if (account.getAccountDimIds() != null && account.getBusEntity().getBusEntityId() < 0) {
                              strAccountId = account.getAccountDimIds(); // For DataWerehouse search
                            } else{
                              strAccountId = Integer.toString(account.getBusEntity().getBusEntityId());
                            }
                            //don't need to check that selected account associated with user's store
                            //checkParamSecurity("ACCOUNT", errors, strAccountId, factory, appUser, grc);
                            if (accountIndex > 0) {
                                strAccountIds += ",";
                            }
                            strAccountIds += strAccountId;
                        }
                        value = strAccountIds;
                        sessionFilterValue = null; // filter is already set as LinkedList object
                    }
                    grc.setValue(strAccountIds);

                }
//                if ("LOCATE_SITE_MULTI".equalsIgnoreCase(name)) {
                if (name.contains("LOCATE_SITE_MULTI")) {
                    SiteViewVector sites = pForm.getSiteFilter();
                    String strSiteIds = "";
                    value = "";
                    if (  sites != null && sites.size() > 0) {
                        for (int siteIndex = 0; siteIndex < sites.size(); ++siteIndex) {
                            SiteView site = (SiteView) sites.get(siteIndex);
                            String strSiteId = Integer.toString(site.getId());
                            //don't need to check that selected site associated with user's store
                            //checkParamSecurity("SITE", errors, strSiteId, factory, appUser, grc);
                            if (siteIndex > 0) {
                                strSiteIds += ",";
                            }
                            strSiteIds += strSiteId;
                        }
                        value = strSiteIds;
                        sessionFilterValue = value;
                    }
                    grc.setValue(strSiteIds);
                }
                
                if (name.contains("LOCATE_CATALOG_MULTI")) {
                    CatalogDataVector catalogs = pForm.getCatalogFilter();
                    String strCatalogIds = "";
                    value = "";
                    if (  catalogs != null && catalogs.size() > 0) {
                        for (int catalogIndex = 0; catalogIndex < catalogs.size(); ++catalogIndex) {
                        	CatalogData catalog = (CatalogData) catalogs.get(catalogIndex);
                            String strCatalogId =Integer.toString(catalog.getCatalogId());
                            
                            if (catalogIndex > 0) {
                                strCatalogIds += ",";
                            }
                            strCatalogIds += strCatalogId;
                        }
                        value = strCatalogIds;
                        sessionFilterValue = null; // filter is already set as LinkedList object
                    }
                    grc.setValue(strCatalogIds);

                }

                if ("DW_USER_SELECT".equalsIgnoreCase(name)){
                  IdVector userIds = getUserSelectedFilter(request);
                  value ="";
                  if (userIds != null){
                    Integer userIdFilter = (Integer)userIds.get(0);
                    DWOperation dwBean = factory.getDWOperationAPI();
                    value = dwBean.getUserFilterForAccounts(userIdFilter.intValue()) ;
                    grc.setValue(userIdFilter.toString());
                  }
                  mandatoryFl = false;
                }

                if(mandatoryFl && (value==null || value.trim().length()==0)) {
                	//errors.add("beginDate", new ActionError ("variable.empty.error",name));
                        errors.add(rName, new ActionError ("variable.empty.error",label));
                }

                //value = Utility.customizeDateParam(grc.getName(),value); //SVC: currently customizeDateParam() method
                                                                           //processes ONLY "MM/dd/yyyy" Date Format
                String type = grc.getType();
                if(type!=null && value!=null && value.trim().length()>0) {
                    if("DATE".equalsIgnoreCase(type) ||
					   "BEG_DATE".equalsIgnoreCase(name) ||
					   "END_DATE".equalsIgnoreCase(name) ||
					   "DW_BEGIN_DATE".equalsIgnoreCase(name) ||
					   "DW_END_DATE".equalsIgnoreCase(name)
                    ) {
					   String ll = grc.getLabel();
					   if(ll==null) ll=name;
		               // check Input Date: does it correspond to the inputDateFormat ?
					   /***
		               if (inputDateFormatFl) {
		             	   // DATE_FMT value was passed as a parameter from the data entry screen
		            	   errors = checkDateBasedOnDateFormat(value, ll, errors, dateFmt); //check input Date against Date Format
		               } else {
		                   // DATE_FMT value was NOT passed as a parameter from the data entry screen =>
		            	   errors = checkDateBasedOnDateFormat(value, ll, errors, inputDateFormat); //check input Date against Date Format of the user Locale
		                   // convert value(Date) to the default US Date Format, which is hard coded in some of the report(s) as "mm/dd/yyyy"
		            	   value = convertDateToAnotherFormat(value, ll, errors, inputDateFormat, Constants.SIMPLE_DATE_PATTERN);
		               }
		               if(errors.size()>0) {
		                   return errors;
		               }
		               ***/
					   Object errorMessage=
			                reportEjb.checkInputDate(value, ll, inputDateFormat);
					   String eMessage = errorMessage.toString();
					   log.info("********************SVC: eMessage = " + eMessage);
					   if (!eMessage.equals("")) {
						   //errors.add(ll, new ActionError("error.badDateFormat", value));
						   errors.add(name, new ActionError("error.simpleGenericError", eMessage));
						   return errors;
					   }
		               if (!inputDateFormatFl) {
		            	   value = convertDateToAnotherFormat(value, ll, errors, inputDateFormat, Constants.SIMPLE_DATE_PATTERN);
		               }
		               /***
					   if (dateFmt.toLowerCase().equals("mm/dd/yyyy") || dateFmt.equals("")) { // one of North American Date Formats
					      errors=checkDate(value,ll,errors); // leave it as it is => North American Date Format
					   } else { // other date format (now - only dd/MM/yyyy)
						  //errors=checkDateBasedOnDateFormat(value, ll, errors, dateFmt); //SVC
						   log.info("********************SVC: Date to test is " + value);
						   log.info("********************SVC: parameter is " + name);
                                             Object errorMessage=
				                reportEjb.checkInputDate(value, ll, dateFmt); // SVC

						   String eMessage = errorMessage.toString();
						   log.info("********************SVC: eMessage = " + eMessage);
						   if (!eMessage.equals("")) {
							   //errors.add(ll, new ActionError("error.badDateFormat", value));
							   errors.add(name, new ActionError("error.simpleGenericError", eMessage));
							   return errors;
						   }
					   }
					   ***/
                     } else if("INT".equalsIgnoreCase(type) ||
                    "ACCOUNT".equalsIgnoreCase(name) || "ACCOUNT_OPT" .equalsIgnoreCase(name) ||
                    "CONTRACT".equalsIgnoreCase(name) || "DISTRIBUTOR".equalsIgnoreCase(name) ||
                    "MANUFACTURER".equalsIgnoreCase(name) ||"ITEM".equalsIgnoreCase(name) ||
                    "CATALOG".equalsIgnoreCase(name) ||"ITEM_OPT".equalsIgnoreCase(name) ||
                    "STORE".equalsIgnoreCase(name) ||"STORE_OPT".equalsIgnoreCase(name)||
                    "GROUP".equalsIgnoreCase(name) || "USER".equalsIgnoreCase(name) ||
                    "CUSTOMER".equalsIgnoreCase(name) || "TRADING_PARTNER_ID".equalsIgnoreCase(name))
                    {
                    	 if(!isInt(value)) {
                            errors.add(grc.getName(), new ActionError("variable.integer.format.error", label));
                        }
                    } else if("NUMBER".equalsIgnoreCase(type)) {
                    	if(!isNumber(value)) {
                            errors.add(grc.getName(), new ActionError("error.invalidNumberAmount", label));
                        }
                    }
                }
                if ("DW_STORE_SELECT".equalsIgnoreCase(name)){
                  DWOperation dwBean = factory.getDWOperationAPI();
                  value =Integer.toString( dwBean.getStoreDimId(value));
                  // don't store this Values as Generic Report Control value!
                }
                if (name.contains("DW_CONNECT_CUST")){
                  value = (Utility.isSet(value) ? value : "No");
                }
 //               value = Utility.customizeDateParam(grc.getName(),value);

                pstr += "\n" + grc.getSrcString() + "=" + value;

                if(errors.size()==0) {
                	//params.put(grc.getSrcString(), value);
                        controls.put(grc.getName(), grc);
			params.put(grc.getName(), value);
                        if (sessionFilterValue != null) {
                          setControlFilter(request, pForm, grc.getName(), sessionFilterValue);
                        }
                }
            }  ////End of for
            params.put("-CONTROL_INFO-", controls);

            String [] runForAccounts = pForm.getRunForAccounts();
            if ( null != runForAccounts && runForAccounts.length > 0 ) {
                String s = "";
                for ( int i = 0; i < runForAccounts.length; i++ ) {
                    if ( s.length() > 0 ) s+= ", ";
                    s += runForAccounts[i];
                }
                params.put("runForAccounts", s);
                pstr += "\nrunForAccounts=" + s;
            }

            if(errors.size()>0) {
            	return errors;
            }

            SimpleDateFormat df;
            if (!inputDateFormatFl) { // Date Format was NOT passed as a parameter from the data entry screen
            	df = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
            	params.put("DATE_FMT", Constants.SIMPLE_DATE_PATTERN);
            } else {
                df = new SimpleDateFormat(dateFmt);
            }

            checkInpuDate(params, "BEG_DATE", "END_DATE", df, request, errors);
            checkInpuDate(params, "BEG_DATE", "END_DATE_OPT", df, request, errors);
            checkInpuDate(params, "BEG_DATE_OPT", "END_DATE_OPT", df, request,
                    errors);
            checkInpuDate(params, "DW_BEGIN_DATE", "DW_END_DATE", df, request,
                    errors);
            checkInpuDate(params, "BEG_DATE_ACTUAL_OPT", "END_DATE_ACTUAL_OPT", df, request,
                    errors);
            checkInpuDate(params, "BEG_DATE_ESTIMATE_OPT", "END_DATE_ESTIMATE_OPT", df, request,
                    errors);
            checkInpuDate(params, "RECEIVED_DATE_FROM_OPT", "RECEIVED_DATE_TO_OPT", df, request,
                    errors);
                        
            
            checkInpuDate(params, "BEG_DATE_ACTUAL_OPT", df, request, errors, "userWorkOrder.text.actualBeginDate");
            checkInpuDate(params, "END_DATE_ACTUAL_OPT", df, request, errors, "userWorkOrder.text.actualEndDate");
            checkInpuDate(params, "BEG_DATE_ESTIMATE_OPT", df, request, errors, "userWorkOrder.text.estimateBeginDate");
            checkInpuDate(params, "END_DATE_ESTIMATE_OPT", df, request, errors, "userWorkOrder.text.estimateEndDate");
            checkInpuDate(params, "RECEIVED_DATE_FROM_OPT", df, request, errors, "userWorkOrder.text.receivedDateFrom");
            checkInpuDate(params, "RECEIVED_DATE_TO_OPT", df, request, errors, "userWorkOrder.text.receivedDateTo");
            if (errors.size() > 0) {
                return errors;
            }

            boolean savePrevVersion = pForm.getSavePrevVersion();
            ReportResultData reportResultD = null;
            try {
            	Serializable ser = reportEjb.checkAnalyticReport(pForm.getReportId(),params,
                        userId, userName, !savePrevVersion);
            	if (ser instanceof ReportItem) {
                    errors = downloadPreparedReport(request, res,
                            (ReportItem) ser, params);
                    return errors;
                } else if (ser instanceof GenericReportResultViewVector) { // special report.
                  errors = downloadSpecialReport(request, res, pForm.getReportId(),
                          (GenericReportResultViewVector) ser, params);
                  return errors;
                } else if (ser instanceof ReportResultData) {   // if report result has been saved into DB
                    reportResultD = (ReportResultData) ser;
                } else {
                    throw new Exception("Incorrect report's type:" + ser);
                }
//                reportResultD =  reportEjb.processAnalyticReport
//                    (pForm.getReportId(),params,
//                     userId, userName, !savePrevVersion);
//

            }catch (Exception exc) {
              String errorMess = exc.getMessage();
              String repMarker = "^clwKey^";
              if (errorMess != null) {
                int pos = errorMess.indexOf(repMarker);
                if (pos >= 0) {
                  String err = ClwI18nUtil.formatEjbError(request, errorMess);
                  err = ESAPI.encoder().decodeForHTML(err);// preventing double encoding
                  
                  errors.add("Error", new ActionError("error.simpleGenericError", err));
                  return errors;
                }
              }
              errorMess = exc.toString();
              int messIndBeg = errorMess.indexOf("^clw^");
              int messIndEnd = 0;
              if(messIndBeg>=0) {
                messIndEnd = errorMess.indexOf("^clw^",messIndBeg+1);
              }
              if(messIndBeg>=0 && messIndEnd>messIndBeg) {
                String userMess = errorMess.substring(messIndBeg+5,messIndEnd);
                errors.add("error",
                         new ActionError("error.simpleGenericError",userMess));
                return errors;
              } else {
                ArrayList errorAL = new ArrayList();
                for(int ii=0; ii<100 && errorMess.length()>4000; ii++) {
                  errorAL.add(errorMess.substring(0,4000));
                  errorMess = errorMess.substring(4000);
                }
                if(errorMess.length()>0)  errorAL.add(errorMess);
                ReportResultData errD = reportEjb.
                   saveAnalyticReportError(pForm.getReportId(),0, params, userId, userName, errorAL);
                reportEjb.sendReportErrorNotification(errD,  errorAL);
                throw exc;
              }
            }

            int reportResultId = reportResultD.getReportResultId();
            errors = downloadPreparedReport(request, res, pForm,""+reportResultId, reportResultD,
                    isDownload);
        }catch (Exception exc) {
            exc.printStackTrace();
            throw exc;
        }

        return errors;
    }

    /**
     * Checks some security constraints around the entered parameters and makes sure that the
     * user is not trying to report off something they do not have access to.  Current implementation
     * only checks at the store level.  The current known controls are:
     * Catalog, account, store, manufacturer, and distributor.
     * @param name Name of the control
     * @param errors error messages to add any validaion problems to
     * @param value the value of the input *ASSUMED TO BE NUMBERIC IF ID*
     * @param factory APIAccess to EJB Layer
     * @param appUser the currently logged in user
     * @param grc the generic report control view
     * @throws APIServiceAccessException  when talking to the EJB Layer
     * @throws RemoteException  when talking to the EJB Layers
     */
    private static void checkParamSecurity(String name, ActionErrors errors, String value, APIAccess factory, CleanwiseUser appUser,GenericReportControlView grc, String authorizedTypes) throws APIServiceAccessException,RemoteException{
    	//do some security validation
		if("CATALOG".equalsIgnoreCase(name)){
			if(value!=null &&value.trim().length()>0) {
              try{
				int catalogId = Integer.parseInt(value);
				BusEntityDataVector stores = factory.getCatalogInformationAPI().getStoreCollection(catalogId);
				if(stores == null || stores.size() == 0){
					errors.add(grc.getName(),new ActionError("user.bad.catalog"));
				}else{
					boolean found = false;
					Iterator it = stores.iterator();
					while(it.hasNext()){
						BusEntityData storeC = (BusEntityData) it.next();
						if(appUser.getUserStoreAsIdVector().contains(new Integer(storeC.getBusEntityId()))){
							found = true;
						}
					}
					if(!found){
						errors.add(grc.getName(),new ActionError("user.bad.catalog"));
					}
				}
			  }catch(NumberFormatException e){/*delt with later*/}
            }
		}
		if("STORE".equalsIgnoreCase(name)){
			try{
				if( !appUser.getUserStoreAsIdVector().contains(new Integer(value))){
					errors.add(grc.getName(),new ActionError("user.bad.store"));
				}
			}catch(NumberFormatException e){
				//delt with later
			}
		}
		if("ACCOUNT".equalsIgnoreCase(name)){
			try{
				IdVector accountIds = new IdVector();
				accountIds.add(new Integer(value));
				IdVector storeIds = factory.getAccountAPI().getAccountAssocCollection(accountIds, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
				if(storeIds.size() == 0 || !appUser.getUserStoreAsIdVector().contains((Integer) storeIds.get(0))){
					errors.add(grc.getName(),new ActionError("user.bad.account"));
				}
			}catch(NumberFormatException e){
				//delt with later
			}
		}
		if("DISTRIBUTOR".equalsIgnoreCase(name)){
			try{
				DistributorData dist = factory.getDistributorAPI().getDistributor(Integer.parseInt(value));
				if(!appUser.getUserStoreAsIdVector().contains(new Integer(dist.getStoreId()))){
					errors.add(grc.getName(),new ActionError("user.bad.distributor"));
				}
			}catch(NumberFormatException e){
				//delt with later
			}catch(DataNotFoundException e){
				errors.add(grc.getName(),new ActionError("user.bad.distributor"));
			}
		}
		if ("GROUP".equalsIgnoreCase(name)) {
            try {
                GroupData group = factory.getGroupAPI().getGroupDetail(Integer.parseInt(value));
                BusEntityDataVector stores = factory.getGroupAPI().getStoresForGroup(group.getGroupId(), null);
                IdVector storeIds = new IdVector();
                for (int i = 0; stores != null && i < stores.size(); i++) {
                    BusEntityData store = (BusEntityData) stores.get(i);
                    storeIds.add(store.getBusEntityId());
                }
                StoreData storeData = appUser.getUserStore();
                if (storeData == null
                        || storeIds.contains(storeData.getStoreId()) == false) {
                    errors.add(grc.getName(), new ActionError("user.bad.group"));
                }
            } catch (NumberFormatException e) {
                // delt with later
            } catch (DataNotFoundException e) {
                errors.add(grc.getName(), new ActionError("user.bad.group"));
            }
        }
		if("MANUFACTURER".equalsIgnoreCase(name)){
			try{
				ManufacturerData man = factory.getManufacturerAPI().getManufacturer(Integer.parseInt(value));
				if(!appUser.getUserStoreAsIdVector().contains(new Integer(man.getStoreId()))){
					errors.add(grc.getName(),new ActionError("user.bad.manufacturer"));
				}
			}catch(NumberFormatException e){
				//delt with later
			}catch(DataNotFoundException e){
				errors.add(grc.getName(),new ActionError("user.bad.manufacturer"));
			}
		}
		if("CUSTOMER".equalsIgnoreCase(name) || "USER".equalsIgnoreCase(name)){
 			if(appUser.isaStoreAdmin()){
				UserDataVector uv = new UserDataVector();
				User userBean = factory.getUserAPI();
				UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();
                                if(!isInt(value)) {
                                  return; //this error will be catched later
                                }

                                searchCriteria.setUserId(value);

				//searchCriteria.setUserTypeCd(RefCodeNames.USER_TYPE_CD.MSB);
                                LinkedList userTypeNames = new LinkedList();
                                if (!Utility.isSet(authorizedTypes )){
                                  userTypeNames.add(RefCodeNames.USER_TYPE_CD.MSB);
                                  userTypeNames.add(RefCodeNames.USER_TYPE_CD.CUSTOMER);
                                  searchCriteria.setUserTypes(userTypeNames);
                                }
				searchCriteria.setStoreId(appUser.getUserStore().getBusEntity().getBusEntityId());
				uv = userBean.getUsersCollectionByCriteria(searchCriteria);
                                if (uv.size() < 1 ){
                                    errors.add(grc.getName(),new ActionError("user.bad.user"));
                                } else {
                                  String userTypeCd = ((UserData)uv.get(0)).getUserTypeCd();
                                  if (!Utility.isUserAutorizedForReport(userTypeCd, authorizedTypes)){
                                    // report is not authoriset for user <name>
                                    errors.add(grc.getName() ,new ActionError("report.text.userNotAuthorized"));
                                  }
                                }
			}

		}
    }


    //---------------------------------------------------------------------------------------
    public static ActionErrors getPreparedReports(HttpServletRequest request,
                                         HttpServletResponse res,
                                         AnalyticReportForm pForm)
    throws Exception
    {
        ActionErrors errors = new ActionErrors();
        try {
          CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
          String userName = appUser.getUser().getUserName();
          int userId = appUser.getUser().getUserId();
          APIAccess factory = new APIAccess();
          Report reportEjb = factory.getReportAPI();
          Map filter = new HashMap();
          String categoryFilter = pForm.getArchCategoryFilter();
          if(Utility.isSet(categoryFilter)){
            filter.put("REPORT_CATEGORY",categoryFilter);
          }
          String nameFilter = pForm.getArchReportFilter();
          if(Utility.isSet(nameFilter)){
            filter.put("REPORT_NAME",nameFilter);
          }
          String minDateFilterS = pForm.getArchMinDateFilter();
          if(Utility.isSet(minDateFilterS)){
             Date minDateFilter = Utility.parseDate(minDateFilterS);
             if(minDateFilter==null) {
               String errorMess = "Wrong Min Date Filter Format: "+minDateFilterS;
               errors.add("error",
                         new ActionError("error.simpleGenericError",errorMess));
               return errors;
             }
             filter.put("MIN_REPORT_DATE", minDateFilter);
          }
          String maxDateFilterS = pForm.getArchMaxDateFilter();
          if(Utility.isSet(maxDateFilterS)){
             Date maxDateFilter = Utility.parseDate(maxDateFilterS);
             if(maxDateFilter==null) {
               String errorMess = "Wrong Max Date Filter Format: "+maxDateFilterS;
               errors.add("error",
                         new ActionError("error.simpleGenericError",errorMess));
               return errors;
             }
             filter.put("MAX_REPORT_DATE", maxDateFilter);
          }
          if(pForm.getMyReportsFl()) {
            filter.put("THE_USER_REPORTS_ONLY", "true");
          }
          PreparedReportViewVector preparedReports = reportEjb.getAnalyticReportArchive(filter,userId);
          pForm.setPreparedReports(preparedReports);
          sortPreparedReportsByDate(request, pForm);
        }catch (Exception exc) {
            exc.printStackTrace();
            throw exc;
            //errors.add("system error", new ActionError("error.systemError", exc.getMessage()));
        }
        return errors;
    }

    public static void sortPreparedReports(HttpServletRequest request,
                                         AnalyticReportForm pForm,
                                         String pField)
    {
        if("Category".equals(pField)) {
            sortPreparedReportsByCategory(request,pForm);
        }else if("Report".equals(pField)) {
            sortPreparedReportsByName(request,pForm);
        }else if("Date".equals(pField)) {
            sortPreparedReportsByDate(request,pForm);
        }else if("User".equals(pField)) {
            sortPreparedReportsByUser(request,pForm);
        }else if("Status".equals(pField)) {
            sortPreparedReportsByStatus(request,pForm);
        }
    }

    public static void sortPreparedReportsByDate(HttpServletRequest request,
                                         AnalyticReportForm pForm) {
      PreparedReportViewVector preparedReports = pForm.getPreparedReports();
      if(preparedReports.size()<=1) return;
      Object[] reportA = preparedReports.toArray();

      for(int ii=0; ii<reportA.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<reportA.length-ii-1; jj++) {
           PreparedReportView prVw1 = (PreparedReportView) reportA[jj];
           PreparedReportView prVw2 = (PreparedReportView) reportA[jj+1];
           Date date1 = prVw1.getReportDate();
           Date date2 = prVw2.getReportDate();
           long date1long = (date1!=null)?date1.getTime():0;
           long date2long = (date2!=null)?date2.getTime():0;
           long comp = date1long-date2long;
           if(comp<0) {
             reportA[jj] = prVw2;
             reportA[jj+1] = prVw1;
             exitFl = false;
             continue;
           }
           if(comp==0) {
             String name1 = prVw1.getReportName();
             String name2 = prVw2.getReportName();
             if(name1==null) name1="";
             if(name2==null) name2="";
             comp = name1.compareTo(name2);
             if(comp>0) {
               reportA[jj] = prVw2;
               reportA[jj+1] = prVw1;
               exitFl = false;
               continue;
             }
             if(comp==0) {
               String cat1 = prVw1.getReportCategory();
               String cat2 = prVw2.getReportCategory();
               if(cat1==null) cat1="";
               if(cat2==null) cat2="";
               comp = cat1.compareTo(cat2);
               if(comp>0) {
                 reportA[jj] = prVw2;
                 reportA[jj+1] = prVw1;
                 exitFl = false;
                 continue;
               }
             }
           }
        }
        if(exitFl) break;
      }
      preparedReports = new PreparedReportViewVector();
      for(int ii=0; ii<reportA.length; ii++) {
        preparedReports.add((PreparedReportView) reportA[ii]);
      }
      pForm.setPreparedReports(preparedReports);
    }

    public static void sortPreparedReportsByUser(HttpServletRequest request,
                                         AnalyticReportForm pForm) {
      PreparedReportViewVector preparedReports = pForm.getPreparedReports();
      if(preparedReports.size()<=1) return;
      Object[] reportA = preparedReports.toArray();

      for(int ii=0; ii<reportA.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<reportA.length-ii-1; jj++) {
           PreparedReportView prVw1 = (PreparedReportView) reportA[jj];
           PreparedReportView prVw2 = (PreparedReportView) reportA[jj+1];
           String user1 = prVw1.getRequesterName();
           String user2 = prVw2.getRequesterName();
           if(user1==null) user1 = "";
           if(user2==null) user2 = "";
           long comp = user1.compareTo(user2);
           if(comp>0) {
             reportA[jj] = prVw2;
             reportA[jj+1] = prVw1;
             exitFl = false;
             continue;
           }
           if(comp==0) {
             Date date1 = prVw1.getReportDate();
             Date date2 = prVw2.getReportDate();
             long date1long = (date1!=null)?date1.getTime():0;
             long date2long = (date2!=null)?date2.getTime():0;
             comp = date1long-date2long;
             if(comp<0) {
               reportA[jj] = prVw2;
               reportA[jj+1] = prVw1;
               exitFl = false;
               continue;
             }
             if(comp==0) {
               String name1 = prVw1.getReportName();
               String name2 = prVw2.getReportName();
               if(name1==null) name1="";
               if(name2==null) name2="";
               comp = name1.compareTo(name2);
               if(comp>0) {
                 reportA[jj] = prVw2;
                 reportA[jj+1] = prVw1;
                 exitFl = false;
                 continue;
               }
               if(comp==0) {
                 String cat1 = prVw1.getReportCategory();
                 String cat2 = prVw2.getReportCategory();
                 if(cat1==null) cat1="";
                 if(cat2==null) cat2="";
                 comp = cat1.compareTo(cat2);
                 if(comp>0) {
                   reportA[jj] = prVw2;
                   reportA[jj+1] = prVw1;
                   exitFl = false;
                   continue;
                 }
               }
             }
          }
        }
        if(exitFl) break;
      }
      preparedReports = new PreparedReportViewVector();
      for(int ii=0; ii<reportA.length; ii++) {
        preparedReports.add((PreparedReportView) reportA[ii]);
      }
      pForm.setPreparedReports(preparedReports);
    }

    public static void sortPreparedReportsByStatus(HttpServletRequest request,
                                         AnalyticReportForm pForm) {
      PreparedReportViewVector preparedReports = pForm.getPreparedReports();
      if(preparedReports.size()<=1) return;
      Object[] reportA = preparedReports.toArray();

      for(int ii=0; ii<reportA.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<reportA.length-ii-1; jj++) {
           PreparedReportView prVw1 = (PreparedReportView) reportA[jj];
           PreparedReportView prVw2 = (PreparedReportView) reportA[jj+1];
           boolean readFl1 = prVw1.getReadFl();
           boolean readFl2 = prVw2.getReadFl();
           long comp = 0;
           if(readFl1 & !readFl2) comp = 1;
           if(!readFl1 & readFl2) comp = -1;
           if(comp>0) {
             reportA[jj] = prVw2;
             reportA[jj+1] = prVw1;
             exitFl = false;
             continue;
           }
           if(comp==0) {
             Date date1 = prVw1.getReportDate();
             Date date2 = prVw2.getReportDate();
             long date1long = (date1!=null)?date1.getTime():0;
             long date2long = (date2!=null)?date2.getTime():0;
             comp = date1long-date2long;
             if(comp<0) {
               reportA[jj] = prVw2;
               reportA[jj+1] = prVw1;
               exitFl = false;
               continue;
             }
             if(comp==0) {
               String name1 = prVw1.getReportName();
               String name2 = prVw2.getReportName();
               if(name1==null) name1="";
               if(name2==null) name2="";
               comp = name1.compareTo(name2);
               if(comp>0) {
                 reportA[jj] = prVw2;
                 reportA[jj+1] = prVw1;
                 exitFl = false;
                 continue;
               }
               if(comp==0) {
                 String cat1 = prVw1.getReportCategory();
                 String cat2 = prVw2.getReportCategory();
                 if(cat1==null) cat1="";
                 if(cat2==null) cat2="";
                 comp = cat1.compareTo(cat2);
                 if(comp>0) {
                   reportA[jj] = prVw2;
                   reportA[jj+1] = prVw1;
                   exitFl = false;
                   continue;
                 }
               }
             }
          }
        }
        if(exitFl) break;
      }
      preparedReports = new PreparedReportViewVector();
      for(int ii=0; ii<reportA.length; ii++) {
        preparedReports.add((PreparedReportView) reportA[ii]);
      }
      pForm.setPreparedReports(preparedReports);
    }


    public static void sortPreparedReportsByName(HttpServletRequest request,
                                         AnalyticReportForm pForm) {
      PreparedReportViewVector preparedReports = pForm.getPreparedReports();
      if(preparedReports.size()<=1) return;
      Object[] reportA = preparedReports.toArray();

      for(int ii=0; ii<reportA.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<reportA.length-ii-1; jj++) {
           PreparedReportView prVw1 = (PreparedReportView) reportA[jj];
           PreparedReportView prVw2 = (PreparedReportView) reportA[jj+1];
           String name1 = prVw1.getReportName();
           String name2 = prVw2.getReportName();
           if(name1==null) name1="";
           if(name2==null) name2="";
           long comp = name1.compareTo(name2);
           if(comp>0) {
             reportA[jj] = prVw2;
             reportA[jj+1] = prVw1;
             exitFl = false;
             continue;
           }
           if(comp==0) {
             String cat1 = prVw1.getReportCategory();
             String cat2 = prVw2.getReportCategory();
             if(cat1==null) cat1="";
             if(cat2==null) cat2="";
             comp = cat1.compareTo(cat2);
             if(comp>0) {
               reportA[jj] = prVw2;
               reportA[jj+1] = prVw1;
               exitFl = false;
               continue;
             }
             if(comp==0) {
               Date date1 = prVw1.getReportDate();
               Date date2 = prVw2.getReportDate();
               long date1long = (date1!=null)?date1.getTime():0;
               long date2long = (date2!=null)?date2.getTime():0;
               comp = date1long-date2long;
               if(comp<0) {
                 reportA[jj] = prVw2;
                 reportA[jj+1] = prVw1;
                 exitFl = false;
                 continue;
               }
             }
           }
        }
        if(exitFl) break;
      }
      preparedReports = new PreparedReportViewVector();
      for(int ii=0; ii<reportA.length; ii++) {
        preparedReports.add((PreparedReportView) reportA[ii]);
      }
      pForm.setPreparedReports(preparedReports);
    }


    public static void sortPreparedReportsByCategory(HttpServletRequest request,
                                         AnalyticReportForm pForm) {
      PreparedReportViewVector preparedReports = pForm.getPreparedReports();
      if(preparedReports.size()<=1) return;
      Object[] reportA = preparedReports.toArray();

      for(int ii=0; ii<reportA.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<reportA.length-ii-1; jj++) {
           PreparedReportView prVw1 = (PreparedReportView) reportA[jj];
           PreparedReportView prVw2 = (PreparedReportView) reportA[jj+1];
           String cat1 = prVw1.getReportCategory();
           String cat2 = prVw2.getReportCategory();
           if(cat1==null) cat1="";
           if(cat2==null) cat2="";
           long comp = cat1.compareTo(cat2);
           if(comp>0) {
             reportA[jj] = prVw2;
             reportA[jj+1] = prVw1;
             exitFl = false;
             continue;
           }
           if(comp==0) {
             String name1 = prVw1.getReportName();
             String name2 = prVw2.getReportName();
             if(name1==null) name1="";
             if(name2==null) name2="";
             comp = name1.compareTo(name2);
             if(comp>0) {
               reportA[jj] = prVw2;
               reportA[jj+1] = prVw1;
               exitFl = false;
               continue;
             }
             if(comp==0) {
               Date date1 = prVw1.getReportDate();
               Date date2 = prVw2.getReportDate();
               long date1long = (date1!=null)?date1.getTime():0;
               long date2long = (date2!=null)?date2.getTime():0;
               comp = date1long-date2long;
               if(comp<0) {
                 reportA[jj] = prVw2;
                 reportA[jj+1] = prVw1;
                 exitFl = false;
                 continue;
               }
             }
           }
        }
        if(exitFl) break;
      }
      preparedReports = new PreparedReportViewVector();
      for(int ii=0; ii<reportA.length; ii++) {
        preparedReports.add((PreparedReportView) reportA[ii]);
      }
      pForm.setPreparedReports(preparedReports);
    }


    public static void clearPreparedReportFilter(HttpServletRequest request,
                                         HttpServletResponse res,
                                         AnalyticReportForm pForm)
    {
       pForm.setArchCategoryFilter("");
       pForm.setArchReportFilter("");
       pForm.setArchMaxDateFilter("");
       pForm.setArchMinDateFilter("");
       pForm.setMyReportsFl(false);
    }

    //---------------------------------------------------------------------------------------
    static final String FILE_EXT     = "FILE_EXT_OPT";
    static final String FILE_NAME    = "FILE_NAME_OPT";
    public static ActionErrors downloadSpecialReport(
            HttpServletRequest request, HttpServletResponse res, int pReportId,
            GenericReportResultViewVector results, Map params) throws Exception {

        ActionErrors errors = new ActionErrors();
        APIAccess factory = new APIAccess();
        Report reportEjb = factory.getReportAPI();
        try {
          GenericReportData report = reportEjb.getGenericReport(pReportId);
          Object worker = reportEjb.getInstanceReport(report);
          ReportInterf specRep = (ReportInterf)worker;
          Date repDate = new Date();
          SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
          String repDateS = "";//sdf.format(repDate);
          String repName = specRep.getFileName();
          String repExt  = specRep.getExt();
          if (params.containsKey(FILE_NAME) && Utility.isSet( (String)( params.get(FILE_NAME)))) {
            repName = (String) params.get(FILE_NAME);
          }
          if (params.containsKey(FILE_EXT) && Utility.isSet( (String)( params.get(FILE_EXT)))) {
            repExt = (String) params.get(FILE_EXT);
          }
          String fileName = repName +repDateS+ "." + repExt;
          res.setContentType("application/octet-stream");
          res.setHeader("extension", repExt);
          res.setHeader("Content-disposition", "attachment; filename="+fileName);
          GenericReportResultView result = (GenericReportResultView) results.get(0);
          res.getOutputStream().write(result.getRawOutput());
          res.flushBuffer();
        } catch (Exception ioe) {
            ioe.printStackTrace();
            errors.add("error", new ActionMessage("system error", ioe));
        }

        return errors;
    }

    //---------------------------------------------------------------------------------------
    public static ActionErrors downloadPreparedReport(
            HttpServletRequest request, HttpServletResponse res,
            ReportItem reportItem, Map params) {
        ActionErrors errors = new ActionErrors();
        ReportFactory factory = new ReportXmlFactory();
        CleanwiseUser appUser = (CleanwiseUser) request.getSession()
                .getAttribute(Constants.APP_USER);
//        ReportTransformer reportTransformer = new ExtendedReportTransformer(
//                "com.cleanwise.service.api.tree.test.ReportFactoryTest",
//                appUser.getUserLocaleCode(Locale.getDefault()));
        ReportTransformer reportTransformer = new HttpRequestReportTransformer(
                request);
        String fileName = "TradingPartnerInfo_"
                + params.get(TradingPartnerReport.TRADING_PARTNER_PARAM)
                + ".xml";
        res.setContentType("text/xml");
        res.setHeader("extension", "xml");
        res.setHeader("Content-disposition", "attachment; filename="
                        + fileName);
        OutputStream out = null;
        try {
            out = res.getOutputStream();
            String result = factory.transform(reportItem, reportTransformer);
            out.write(result.getBytes());
            out.flush();
            res.flushBuffer();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            errors.add("error", new ActionMessage("system error", ioe));
        }
        return errors;
    }

    public static ActionErrors downloadPreparedReport(
            HttpServletRequest request, HttpServletResponse res,
            AnalyticReportForm pForm, String pReportResultIdS,
            ReportResultData reportResultD) throws Exception {
        return downloadPreparedReport(request, res, pForm, pReportResultIdS,
                reportResultD, true);
    }

    public static ActionErrors downloadPreparedReport(HttpServletRequest request,
                HttpServletResponse res,
                AnalyticReportForm pForm,
                String pReportResultIdS,
                ReportResultData reportResultD, boolean isDownload)
        throws Exception
        {
        ActionErrors errors = new ActionErrors();
        try {
          CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
          String userName = appUser.getUser().getUserName();
          int userId = appUser.getUser().getUserId();
          APIAccess factory = new APIAccess();
          Report reportEjb = factory.getReportAPI();
          String reportResultIdS = pReportResultIdS;
          int reportResultId = 0;
          try{
             reportResultId = Integer.parseInt(reportResultIdS);
          } catch (Exception exc) {
            String errorMess = "Wrong report result id: "+reportResultIdS;
            errors.add("error", new ActionError("system error",errorMess));
            return errors;
          }

          /*

          ReportResultLineDataVector reportLines =
                       reportEjb.readArchiveReport(reportResultId, 0, 0);
          //Create report result object
          GenericReportResultViewVector results = new GenericReportResultViewVector();
          GenericReportResultView repRes = null;
          ArrayList table = null;
          for(int ii=0; ii<reportLines.size(); ii++) {
            ReportResultLineData rrlD = (ReportResultLineData) reportLines.get(ii);
              String lineS = rrlD.getLineValue();
              String lineS1 = rrlD.getLineValue1();
              if(lineS1!=null) lineS += lineS1;
            if(RefCodeNames.REPORT_RESULT_LINE_CD.HEADER.
                                           equals(rrlD.getReportResultLineCd())){
              GenericReportColumnViewVector header =
                        (GenericReportColumnViewVector) stringToObject(lineS);
              repRes = GenericReportResultView.createValue();
              results.add(repRes);
              table = new ArrayList();
              repRes.setHeader(header);
              repRes.setColumnCount(header.size());
              repRes.setTable(table);

            } else if (RefCodeNames.REPORT_RESULT_LINE_CD.REPORT_LINE.
                                           equals(rrlD.getReportResultLineCd())){
              Object line =  stringToObject(lineS);
              table.add(line);
            }
          }
          */
      //Create report result object
      int maxRows = 0;
      GenericReportResultViewVector results = reportEjb.readArchiveReport(reportResultId);
      boolean hasRawData = false;
      boolean isInvoiceListing = false;
      for (int i = 0; results != null && i < results.size(); i++) {
    	  GenericReportResultView reportResult = (GenericReportResultView) results.get(i);
    	  if (RefCodeNames.CUSTOMER_REPORT_TYPE_CD.INVOICE_LISTING.equals(reportResult.getName())){
    		  isInvoiceListing = true;
    		  CustAcctMgtReportLogic.downloadInvoicePDF(request, res, results, pForm.getGenericControls());
    		  break;
    	  }
          if(reportResult.getRawOutput() != null && reportResult.getRawOutput().length > 0){
              hasRawData = true;
          }
          List data = reportResult.getTable();
          int rows = (data == null) ? 0 : data.size();
          maxRows = Math.max(maxRows, rows);
      }

      if (isInvoiceListing || (hasRawData == false && isDownload == false && maxRows <= CustAcctMgtReportLogic.MAX_HTML_ROWS)) {
          pForm.setReportResults(results);
      } else {
          //GetReportName
          PreparedReportViewVector preparedReports = pForm.getPreparedReports();
          GenericReportData report = pForm.getReport();

          String fileName = "report";
          int genericReportId = 0;
          if(reportResultD!=null) {
            Date repDate = reportResultD.getAddDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            String repDateS = sdf.format(repDate);
            fileName = reportResultD.getReportName()+repDateS;
            genericReportId = reportResultD.getGenericReportId();
          } else {
            for(int ii=0; ii<preparedReports.size(); ii++) {
              PreparedReportView prVw = (PreparedReportView) preparedReports.get(ii);
              int id = prVw.getReportResultId();
              if(id==reportResultId) {
                Date repDate = prVw.getReportDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                String repDateS = sdf.format(repDate);
                fileName = prVw.getReportName()+repDateS;
                genericReportId = prVw.getGenericReportId();
              }
            }
          }
          if (report == null){
            report = reportEjb.getGenericReport(genericReportId);
          }

          if(hasRawData){
        	  fileName+=".data";
              res.setContentType("application/octet-stream");
              res.setHeader("extension", "data");
              res.setHeader("Content-disposition", "attachment; filename="+fileName);
              GenericReportResultView result = (GenericReportResultView) results.get(0);
              res.getOutputStream().write(result.getRawOutput());
              res.flushBuffer();
      	  } else {

                if (Constants.REPORT_FORMAT.PDF.equalsIgnoreCase(pForm.getFormat())) {
                  fileName += ".pdf";
                  res.setContentType("application/pdf");
                  String browser = (String)  request.getHeader("User-Agent");
                  boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
                  if (!isMSIE6){
                        res.setHeader("extension", "pdf");
                        res.setHeader("Content-disposition", "attachment; filename=" + fileName);
                  }
                  ReportWritter.writePDFReportMulti(results,res.getOutputStream(), null, request);
                  res.flushBuffer();
                } else if (Constants.REPORT_FORMAT.HTML.equalsIgnoreCase(pForm.getFormat())) {
                  pForm.setReportResults(results);
                  if (genericReportId > 0) {
                      GenericReportData reportD = reportEjb.getGenericReport(genericReportId);
                      pForm.setReport(reportD);
                  } else {
                      pForm.setReport(null);
                  }
                }  else {
                    fileName += ".xls";
                    res.setContentType("application/x-excel");
                    String browser = (String)  request.getHeader("User-Agent");
                    boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
                    if (!isMSIE6){
                            res.setHeader("extension", "xls");
 //                           res.sendRedirect(request.getRequestURI());
                    }
                    if(isMSIE6){
                            res.setHeader("Pragma", "public");
                            res.setHeader("Expires", "0");
                            res.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                            res.setHeader("Cache-Control", "public");
 //                           res.sendRedirect(request.getRequestURI());
                    }
                    res.setHeader("Content-disposition", "attachment; filename=" + fileName);
                    ReportWritter.writeExcelReportMulti(results, res.getOutputStream());
                    res.flushBuffer();
                }

          }
        }
        try {
          reportEjb.markReportAsRead(reportResultId,userId);
        }catch(Exception exc) { //Dump the exception (doesn't affect download)
          exc.printStackTrace();
        }

      }catch (Exception exc) {
          exc.printStackTrace();
          throw exc;
          //errors.add("system error", new ActionError("error.systemError", exc.getMessage()));
      }
      return errors;
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

    /**
     * Checks  the date where format MM/dd/yyyy
     * @param pDate  Date for check
     * @param name   field name which contains date value
     * @param errors Object ActionErrors
     * @return errors
     */
    public static ActionErrors checkDate(String pDate, String name, ActionErrors errors) {


        int day = -1, month = -1, year = -1;
        String delim = "/";
        StringTokenizer st = new StringTokenizer(pDate, delim);
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        int currentYear = Integer.parseInt(df.format(new Date()));
        int quantityDaysInMonth[] = new int[]{
                31, currentYear % 4 == 0 ? 29 : 28, 31, 30,/* jan feb mar apr */
                31, 30, 31, 31,/* may jun jul aug */
                30, 31, 30, 31 /* sep oct nov dec */
        };

        //checks format
        df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            df.parse(pDate);
        }
        catch (Exception e) {
            errors.add(name, new ActionError("error.badDateFormat", pDate));
            return errors;
        }
        //gets the  year , month , day
        int i = 0;
        while (st.hasMoreElements()) {
            int intValue = -1;
            String strValue = (String) st.nextElement();
            try {
                intValue = Integer.parseInt(strValue);
            } catch (NumberFormatException e) {
                errors.add(name, new ActionError("error.badDateFormat", pDate));
                return errors;
            }
            switch (i) {
                case DAY:
                    day = intValue;
                    break;
                case MONTH:
                    month = intValue;
                    break;
                case YEAR:
                    year = intValue;
                    break;
                default:
            }
            i++;
        }

        //checks the year
        if (year < INITIAL_YEAR || year > currentYear) {
            String mesg = "[ " + name + " ] : " + "Not a valid year ( " + year + " ).Year must be between " + INITIAL_YEAR + " and current year ";
            errors.add(name, new ActionError("error.simpleGenericError", mesg));
        }
       //checks the month
       if (month < 1 || month > 12) {
            String mesg = "[ " + name + " ] : " + "Not a valid month ( " + month + " ). Month must be between 1 and 12";
            errors.add(name, new ActionError("error.simpleGenericError", mesg));
            return errors;
        }
        //checks  the  day of month
        if (day > quantityDaysInMonth[month - 1]) {
            String mesg = "[ " + name + " ] : " + "Not a valid day ( " + day + " ). Day of month must be between 1 and last day of month";
            errors.add(name, new ActionError("error.simpleGenericError", mesg));
        }
        return errors;
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

    //---------------------------------------------------------------------------------------
    public static ActionErrors reportProtection(HttpServletRequest request,
    AnalyticReportForm pForm)
    throws Exception {
      ActionErrors errors = new ActionErrors();
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      String userName = appUser.getUser().getUserName();
      String reportResultIdS = request.getParameter("id");
      int reportResultId = 0;
      try{
        reportResultId = Integer.parseInt(reportResultIdS);
      }catch(Exception exc) {
        String errorMess = "Wrong report result id: "+reportResultIdS;
        errors.add("error",
                         new ActionError("error.simpleGenericError",errorMess));
        return errors;
      }
      PreparedReportViewVector prVwV = pForm.getPreparedReports();
      boolean foundFl = false;
      PreparedReportView prVw = null;
      for(int ii=0; ii<prVwV.size(); ii++) {
        prVw = (PreparedReportView) prVwV.get(ii);
        int id = prVw.getReportResultId();
        if(id == reportResultId) {
          foundFl = true;
          break;
        }
      }
      if(!foundFl) {
        String errorMess = "No generated report found. Probably old page used";
        errors.add("error",
                         new ActionError("error.simpleGenericError",errorMess));
        return errors;
      }
      String protectValue = request.getParameter("val");
      if(!("Y".equals(protectValue)||"N".equals(protectValue))){
        String errorMess = "Wrong protected value: "+protectValue;
        errors.add("error",
                         new ActionError("error.simpleGenericError",errorMess));
        return errors;
      }
      int resId = reportEjb.setReportResultProtection(reportResultId, protectValue, userName);
      if(resId==reportResultId) {
        prVw.setProtectedFl(protectValue);
      }

      return errors;
    }
    //---------------------------------------------------------------------------------------
    public static ActionErrors deletePreparedReports(HttpServletRequest request,
                                         HttpServletResponse res,
                                         AnalyticReportForm pForm)
    throws Exception
    {
      ActionErrors errors = new ActionErrors();
      try {
          CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
          String userName = appUser.getUser().getUserName();
          int userId = appUser.getUser().getUserId();
          APIAccess factory = new APIAccess();
          Report reportEjb = factory.getReportAPI();
          String[] selectedResA = pForm.getResultSelected();
          if(selectedResA==null || selectedResA.length==0) {
            String errorMess = "Nothing selected";
            errors.add("error",
                     new ActionError("error.simpleGenericError",errorMess));
            return errors;
          }
          IdVector reportResultIdV = new IdVector();
          for(int ii=0; ii<selectedResA.length; ii++) {
            String reportResultIdS = selectedResA[ii];
            int reportResultId = 0;
            try{
              reportResultId = Integer.parseInt(reportResultIdS);
            } catch (Exception exc) {
              String errorMess = "Wrong report result id: "+reportResultIdS;
              errors.add("error", new ActionError("system error",errorMess));
              return errors;
            }
            reportResultIdV.add(new Integer(reportResultId));
          }
          reportEjb.deletePreparedReports(reportResultIdV, userName);
        }catch (Exception exc) {
            exc.printStackTrace();
            throw exc;
        }
        return errors;
    }

    public static void changeStoreFilter (HttpServletRequest request, AnalyticReportForm pForm, String controlName)  throws Exception {

      clearForm( pForm);

      LocateReportAccountForm acctForm = pForm.getLocateReportAccountForm();
      if (acctForm != null){
        LocateReportAccountLogic.returnNoValue(request, acctForm);
      }
      LocateReportAccountLogic.clearFilter(request, pForm);

      ReportingLocateStoreSiteForm siteForm = pForm.getReportingLocateStoreSiteForm();
      if (siteForm != null){
        ReportingLocateStoreSiteLogic.returnNoValue(request, siteForm);
      }
      ReportingLocateStoreSiteLogic.clearFilter(request, pForm);

      setControlFilter(request, pForm, controlName);
      initUserFilter(request, pForm, "DW_USER_SELECT" );
      if(Constants.DW_STORE_FILTER_NAME.equals(controlName)) {
         changeDwStoreDimId(request, pForm);
      }
    }

    public static void changeDwStoreDimId(HttpServletRequest request, AnalyticReportForm pForm) throws Exception {

      Integer storeId = null;
      Object elValue= getControlFilter(request, Constants.DW_STORE_FILTER_NAME);
      if (elValue != null){
        if (elValue instanceof Integer ){
          storeId = (Integer)elValue;
        }else{
          storeId = Integer.valueOf(elValue.toString());
        }
      }

       if (storeId != null) {
           APIAccess factory = APIAccess.getAPIAccess();
           DWOperation dwBean = factory.getDWOperationAPI();
           int storeDimId = dwBean.getStoreDimId(String.valueOf(storeId));
           pForm.setSelectedStoreDimId(storeDimId);
       }
    }


    public static ActionErrors initUserFilter(HttpServletRequest request, AnalyticReportForm pForm, String controlName) throws Exception {

      //      Integer storeId = (Integer)getControlFilter(request, "DW_STORE_SELECT");
      Integer storeId = null;
      Object elValue= getControlFilter(request, Constants.DW_STORE_FILTER_NAME);
      if (elValue != null){
        if (elValue instanceof Integer ){
          storeId = (Integer)elValue;
        }else{
          storeId = Integer.valueOf(elValue.toString());
        }
      }


       if (storeId != null) {
         UserDataVector dv = getStoreReportingUsers(request, storeId.intValue());
         pForm.setStoreUsers(dv);
       }
       CleanwiseUser appUser = (CleanwiseUser)(request.getSession()).getAttribute(Constants.APP_USER);
       setControlFilter (request, pForm, controlName, appUser.getUser());
//       int appUserId = appUser.getUser().getUserId();
//       setControlFilter (request, pForm, controlName, new Integer(appUserId));
       return new ActionErrors();
   }

   private static UserDataVector getStoreReportingUsers (HttpServletRequest request, int pStoreId ) throws Exception {
     APIAccess factory = new APIAccess();
     User userBean = factory.getUserAPI();
     CleanwiseUser appUser = (CleanwiseUser)(request.getSession()).getAttribute(Constants.APP_USER);
     DBCriteria crit = new DBCriteria();
     crit.addEqualToIgnoreCase(UserDataAccess.USER_TYPE_CD,  RefCodeNames.USER_TYPE_CD.REPORTING_USER);
     crit.addEqualTo(UserDataAccess.USER_STATUS_CD,RefCodeNames.USER_STATUS_CD.ACTIVE);
     crit.addCondition(UserDataAccess.USER_ROLE_CD + " NOT LIKE '%RepM^%'");
     DBCriteria subCrit = new DBCriteria();
     subCrit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,pStoreId);
     crit.addOneOf(UserDataAccess.USER_ID,UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.USER_ID,subCrit));
     crit.addOrderBy(UserDataAccess.USER_NAME);
     UserDataVector dv = userBean.getUsersCollectionByCriteria(crit);

     if(dv == null){
       dv = new UserDataVector();
     }
     UserData userD=appUser.getUser();
     dv.add(userD);

     return dv;
   }


   private static void setUserControlFilter(HttpServletRequest request, AnalyticReportForm pForm, String controlName) throws Exception {
     UserDataVector userDV = pForm.getStoreUsers();
     UserData userD = null;
     String controlValue = genericControlValue(request, pForm, controlName);
     if (controlValue != null && userDV != null  ) {
         for (int i = 0; i < userDV.size(); i++) {
           userD = (UserData)userDV.get(i);
           if (String.valueOf(userD.getUserId()).equals(controlValue) ) {
             setControlFilter(request, pForm, controlName, userD);
             break;
           }
        }
     }
   }

   private static String genericControlValue (HttpServletRequest request, AnalyticReportForm pForm, String controlName ){
     String controlValue = null;
     GenericReportControlViewVector controlsVector = pForm.getGenericControls();
     if (controlsVector != null) {
       GenericReportControlView control;
       Iterator controlsIterator = controlsVector.iterator();
       for (int i = 0; controlsIterator.hasNext(); i++) {
         control = (GenericReportControlView) controlsIterator.next();
         if (control.getName().equals(controlName)) {
           controlValue = request.getParameter("genericControlValue[" + i + "]");
           break;
         }
       }
     }
     return controlValue;
   }

    public static void changeUserFilter (HttpServletRequest request, AnalyticReportForm pForm, String controlName)  throws Exception {

       clearForm( pForm);

       LocateReportAccountForm acctForm = pForm.getLocateReportAccountForm();
       if (acctForm != null){
         LocateReportAccountLogic.returnNoValue(request, acctForm);
       }
       LocateReportAccountLogic.clearFilter(request, pForm);

       ReportingLocateStoreSiteForm siteForm = pForm.getReportingLocateStoreSiteForm();
       if (siteForm != null){
         ReportingLocateStoreSiteLogic.returnNoValue(request, siteForm);
       }
       ReportingLocateStoreSiteLogic.clearFilter(request, pForm);

       setUserControlFilter(request, pForm, controlName);

    }

    public static ActionErrors clearForm( AnalyticReportForm pForm ) throws Exception {
      pForm.setAccountFilter(new AccountUIViewVector());
      pForm.setSiteFilter(new SiteViewVector());

      LocateReportAccountForm acctForm = pForm.getLocateReportAccountForm();
      if (acctForm != null) {
          acctForm.setAccounts(new AccountUIViewVector());
          acctForm.setSearchField("");
          acctForm.setSearchType(LocatePropertyNames.NAME_BEGINS_SEARCH_TYPE);
          acctForm.setSearchGroupId(0);
      }
      ReportingLocateStoreSiteForm siteForm = pForm.getReportingLocateStoreSiteForm();
      if (siteForm != null) {
        siteForm.setSites(new SiteViewVector());
        siteForm.setCity("");
        siteForm.setStateOrProv("");
        siteForm.setSearchField("");
        siteForm.setSearchType(LocatePropertyNames.NAME_BEGINS_SEARCH_TYPE);
      }

       return new ActionErrors();
    }

    public static void setControlFilter (HttpServletRequest request, AnalyticReportForm pForm, String controlName)  throws Exception {
      setControlFilter(request,  pForm,  controlName, null);
    }

    public static void setControlFilter (HttpServletRequest request, AnalyticReportForm pForm, String controlName, Object controlValue)  throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("REPORT_CONTROL_FILTER_MAP") == null) {
          session.setAttribute("REPORT_CONTROL_FILTER_MAP", new HashMap());
        }
        HashMap controlFiltersMap =(HashMap)session.getAttribute("REPORT_CONTROL_FILTER_MAP");

        GenericReportControlViewVector controlsVector = pForm.getGenericControls();
        if (controlsVector != null) {
            GenericReportControlView control;
            Iterator controlsIterator = controlsVector.iterator();
            for(int i = 0; controlsIterator.hasNext(); i++) {
                control = (GenericReportControlView)controlsIterator.next();
                if (control.getName().equals(controlName)) {
                    Object newControlValue = (controlValue!=null) ? controlValue : Integer.valueOf(request.getParameter("genericControlValue[" + i +"]"));
                    controlFiltersMap.put(controlName, newControlValue);
                }
            }
        }
    }

    public static void setControlFilter (HttpServletRequest request,  String controlName, Object controlValue)  throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("REPORT_CONTROL_FILTER_MAP") == null) {
          session.setAttribute("REPORT_CONTROL_FILTER_MAP", new HashMap());
        }
        HashMap controlFiltersMap =(HashMap)session.getAttribute("REPORT_CONTROL_FILTER_MAP");
        controlFiltersMap.put(controlName, controlValue);
    }

    public static Object getControlFilter (HttpServletRequest request,  String controlName)  throws Exception {
        HashMap controlFiltersMap = getReportControlFilterMap (request);
        Object controlVal = null;
        if ( controlFiltersMap != null){
          controlVal = controlFiltersMap.get(controlName);
        }

        return controlVal;
    }

    public static HashMap getReportControlFilterMap(HttpServletRequest request) {
        HttpSession session = request.getSession();
        HashMap controlFiltersMap =(HashMap)session.getAttribute("REPORT_CONTROL_FILTER_MAP");
        return controlFiltersMap;
     }

    public static IdVector getStoreSelectedFilter (HttpServletRequest request)  throws Exception {
      return getStoreSelectedFilter ( request, Constants.STORE_FILTER_NAME ) ;
    }

    public static IdVector getStoreSelectedFilter (HttpServletRequest request, String filterName  )  throws Exception {
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
       Object obj = AnalyticReportLogic.getControlFilter(request, filterName);
       Integer selectedStoreId = null;
       if (obj != null &&  obj instanceof Integer){
         selectedStoreId = (Integer) obj;
       } else if (obj != null &&  obj instanceof String){
         selectedStoreId = Integer.valueOf(obj.toString());
       }
       IdVector ids = null;
       if (selectedStoreId != null && selectedStoreId.intValue() > 0) {
         ids = new IdVector();
         ids.add(selectedStoreId);
       }
       else {
         ids = appUser.getUserStoreAsIdVector();
       }
       return ids;

    }
    public static IdVector getAccountSelectedFilter (HttpServletRequest request )  throws Exception {
       return  getAccountSelectedFilter (request, Constants.ACCOUNT_FILTER_NAME );
    }

    public static IdVector getAccountSelectedFilter (HttpServletRequest request, String filterName )  throws Exception {
      LinkedList accountList = null;
      Object obj = AnalyticReportLogic.getControlFilter(request, filterName);
      if (obj != null && obj instanceof LinkedList) {
        accountList = (LinkedList) obj;
      }
      IdVector accountIds = null;
      if (accountList != null ){
        accountIds = new IdVector();
        for (int i = 0; i < accountList.size(); i++) {
            accountIds.add(accountList.get(i));
        }
      }
      return accountIds;
    }
    public static IdVector getUserSelectedFilter (HttpServletRequest request)  throws Exception {
      return getUserSelectedFilter ( request, Constants.USER_FILTER_NAME);
    }
    public static IdVector getUserSelectedFilter (HttpServletRequest request, String filterName)  throws Exception {
       HttpSession session = request.getSession();
       CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
       UserData selectedUser = null;
       Object obj = AnalyticReportLogic.getControlFilter(request, filterName);

       if (obj != null && obj instanceof UserData) {
         selectedUser = (UserData) obj;
       }
       if (selectedUser == null) {
         selectedUser = appUser.getUser();
       }
       if (selectedUser.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR)||
           selectedUser.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR)||
           selectedUser.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR)||
           selectedUser.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR) ||
           (selectedUser.getUserTypeCd().equals(RefCodeNames.USER_TYPE_CD.REPORTING_USER) &&
             selectedUser.getUserRoleCd().contains("RepOA^")) ){
         return null;
       }
       Integer selectedUserId = new Integer(selectedUser.getUserId());

       IdVector ids = null;
       ids = new IdVector();
       ids.add(selectedUserId);
       return null;  // temporary !!! Only for DEMO !
//       return ids;

    }

    public static ActionErrors autosuggestRegion(HttpServletRequest request, HttpServletResponse response, AnalyticReportForm pForm) throws Exception {

        log.info("autosuggestRegion => BEGIN.");

        ActionErrors ae = new ActionErrors();

        try {

            int storeId = pForm.getSelectedStoreDimId();

            log.info("autosuggestRegion => storeId: " + storeId);

            String value = getGenericControlValue(pForm, "DW_REGION_AUTOSUGGEST_OPT");
            log.info("autosuggestRegion => value: " + value);

            String jsonStr = "{items:[";
            if (storeId > 0 && value != null) {

                APIAccess factory = APIAccess.getAPIAccess();
                DWOperation dwOpEjb = factory.getDWOperationAPI();

                List<String> regions = dwOpEjb.getRegionNameStartWith(storeId, value, Constants.AUTOSAGGEST_REQUEST_ROWS);

                log.info("autosuggestRegion => regions : " + regions.size());

                int i = 0;
                for (String dwRegName : regions) {
                    if (i > 0) {
                        jsonStr += ",";
                    }
                    jsonStr += "{name:\"" + dwRegName + "\"}";
                    i++;
                }
            }
            jsonStr += "]}";

            response.setContentType("json-comment-filtered");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonStr);

            log.info("autosuggestRegion => jsonStr : " + jsonStr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("autosuggestRegion => END.");

        return ae;

    }

    public static ActionErrors autosuggestDSR(HttpServletRequest request, HttpServletResponse response, AnalyticReportForm pForm) throws Exception {

        log.info("autosuggestDSR => BEGIN.");

        ActionErrors ae = new ActionErrors();

        try {

            int storeId = pForm.getSelectedStoreDimId();

            log.info("autosuggestDSR => storeId: " + storeId);

            String value = getGenericControlValue(pForm, "DW_DSR_AUTOSUGGEST_OPT");
            log.info("autosuggestDSR => value: " + value);

            String jsonStr = "{items:[";
            if (storeId > 0 && value != null) {

                APIAccess factory = APIAccess.getAPIAccess();
                DWOperation dwOpEjb = factory.getDWOperationAPI();

                List<String> repNameVector = dwOpEjb.getRepNameStartWith(storeId, value, Constants.AUTOSAGGEST_REQUEST_ROWS);

                log.info("autosuggestDSR => salesrep size : " + repNameVector.size());

                int i = 0;
                for (String dwRepName : repNameVector) {
                    if (i > 0) {
                        jsonStr += ",";
                    }
                    jsonStr += "{name:\"" + dwRepName + "\"}";
                    i++;
                }
            }
            jsonStr += "]}";

            response.setContentType("json-comment-filtered");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonStr);

            log.info("autosuggestDSR => jsonStr : " + jsonStr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("autosuggestSalesRep => END.");

        return ae;

    }

    public static ActionErrors autosuggestDWCategory(HttpServletRequest request, HttpServletResponse response, AnalyticReportForm pForm) throws Exception {

        log.info("autosuggestDWCategory => BEGIN.");

        ActionErrors ae = new ActionErrors();

        try {

            int storeId = pForm.getSelectedStoreDimId();

            log.info("autosuggestDWCategory => storeId: " + storeId);

            String value = getGenericControlValue(pForm, "DW_CATEGORY_AUTOSUGGEST_OPT");
            log.info("autosuggestDWCategory => value: " + value);

            String jsonStr = "{items:[";
            if (storeId > 0 && value != null) {

                APIAccess factory = APIAccess.getAPIAccess();
                DWOperation dwOpEjb = factory.getDWOperationAPI();

                List<String> categNameVector = dwOpEjb.getCategoryNameStartWith(storeId, value, Constants.AUTOSAGGEST_REQUEST_ROWS);

                log.info("autosuggestDWCategory => categ.size : " + categNameVector.size());

                int i = 0;
                for (String dwCategoryName : categNameVector) {
                    if (i > 0) {
                        jsonStr += ",";
                    }
                    jsonStr += "{name:\"" + dwCategoryName + "\"}";
                    i++;
                }
            }
            jsonStr += "]}";

            response.setContentType("json-comment-filtered");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonStr);

            log.info("autosuggestDWCategory => jsonStr : " + jsonStr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("autosuggestDWCategory => END.");

        return ae;
    }

    private static String getGenericControlValue(AnalyticReportForm pForm, String controlName) {
        GenericReportControlViewVector controls = pForm.getGenericControls();
        if (controls != null && Utility.isSet(controlName)) {
            int i = 0;
            for (Object oControl : controls) {
                GenericReportControlView grc = (GenericReportControlView) oControl;
                if (controlName.equals(grc.getName())) {
                    return pForm.getGenericControlValue(i);
                }
                i++;
            }
        }
        return null;
    }

    // method getDateFmt() finds the value of the DATE_FMT parameter
    private static String getDateFmt(GenericReportControlViewVector controlViewVector) {
    	String value = "";
    	for(int j=0; j<controlViewVector.size(); j++) {
        	GenericReportControlView genericControls =
                (GenericReportControlView) controlViewVector.get(j);
        	String genericControlName = genericControls.getName().trim();
        	if (genericControlName.equals("DATE_FMT")) {
                    value = genericControls.getValue();
        		//stringDateFMT = genericControlName;
        		break;
        	}
        }
        //String value = grc.getValue();
    	//return stringDateFMT;

    	/***
        if (!Utility.isSet(value)) { // DATE_FMT parameter is not provided by the individual running the report
           value = Constants.SIMPLE_DATE_PATTERN; //American Date Format
        }
        ***/
    	log.info("Passed Date Format = " + value);
    	return value;
    }

    private static ActionErrors checkDateFmt(String pName, String pDateFmt, ActionErrors errors) {

    	log.info("************SVC: pDateFmt = " + pDateFmt);

    	String cleanDateFmt = pDateFmt.toLowerCase().trim();
    	log.info("cleanDateFmt = " + cleanDateFmt);
    	if (cleanDateFmt.equals("mm/dd/yyyy")) {

    	} else if (cleanDateFmt.equals("dd/mm/yyyy")) {

    	} else {
    		String mesg = "Date Format " + pDateFmt + " is wrong. Two Date Formats are currently available: MM/dd/yyyy and dd/MM/yyyy.";
            errors.add(pName, new ActionError("error.simpleGenericError", mesg));
            log.info("***************SVC: error message = " + mesg);
    	}
        return errors;
    }

    public static ActionErrors checkDateBasedOnDateFormat(String pDate, String pName, ActionErrors errors, String pDateFmt) {

        DateFormat df = new SimpleDateFormat(pDateFmt);
        df.setLenient(false);
        //ParsePosition parsePosition;
        //parsePosition = new ParsePosition(0);

        try

        {
           Date datePassedDate = df.parse(pDate);
           log.info("datePassedDate = " + datePassedDate.toString());
           // Now check the ParsePosition index
           //log.info("parsePosition.getIndex() = " + parsePosition.getIndex());
           log.info("pDate.length() = " + pDate.length());
           if(pDateFmt.trim().length() == pDate.trim().length()){
              // Parsing successful
           }
           else {
               // Error occurred
       	       log.info("Date format " + pDate + " is wrong");
       	       errors.add(pName, new ActionError("error.badDateFormat", pDate));
               return errors;
           }


        //} catch (ParseException e)
        } catch (Exception e) {
             log.info("Date format " + pDate + " is wrong");
  	         errors.add(pName, new ActionError("error.badDateFormat", pDate));
             return errors;
        }
    	return errors;
    }

    //method convertDateToAnotherFormat() converts Date from one Format to another Format
    public static String convertDateToAnotherFormat(String pDate, String pName, ActionErrors errors, String pInDateFmt, String pOutDateFmt) {
    	SimpleDateFormat inFmt = new SimpleDateFormat(pInDateFmt);
    	SimpleDateFormat outFmt = new SimpleDateFormat(pOutDateFmt);
    	String out = "";
    	try {
    	     out = outFmt.format(inFmt.parse(pDate));
    	} catch(Exception e) {
             log.info("Error converting " + pDate + " from " + pInDateFmt + " Date Format to " + pOutDateFmt + " Date Format.");
             String mesg = "Date " + pDate + " is wrong.";
             errors.add(pName, new ActionError("error.simpleGenericError", mesg));
    	}
    	return out;
    }
    public static void getReportState(HttpServletRequest request, HttpServletResponse response, String sessionAttrName, String errorMessageKey) {
      final String unLocked = "0";
      String errorMess = null;
      HttpSession session = request.getSession();

      try {
        synchronized (session) {
          String lockValue = (String) session.getAttribute(sessionAttrName);
          if (lockValue != null && !lockValue.equals(unLocked)) {
            errorMess = ClwI18nUtil.getMessage(request, errorMessageKey,
                                               new String[] {lockValue});
	        errorMess = ESAPI.encoder().decodeForHTML(errorMess);
          }
        }

        Element rootEl = createXmlResponse(request, errorMess);
        response.setContentType("application/xml");
        response.setHeader("Cache-Control", "no-cache");
        //response.getWriter().write(rootEl.toString());
        OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
        XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
        serializer.serialize(rootEl);

      }
      catch (IOException ex) {
       	ex.printStackTrace();
      }
    }
    private static Element createXmlResponse(HttpServletRequest request, String errMessage){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {


        }
        Document doc = docBuilder.getDOMImplementation().createDocument("", "State", null);

        Element root = doc.getDocumentElement();
        String key = ((errMessage != null) ? "@in progress@" : "");
        root.setAttribute("Error", key+Utility.strNN(errMessage));
        return root;
    }
    
    private static void checkInpuDate(Map params, String pBegDateParam,
            String pEndDateParam, SimpleDateFormat df,
            HttpServletRequest request, ActionErrors errors)
            throws ParseException {
        String bDate = (String) (params.get(pBegDateParam));
        String eDate = (String) (params.get(pEndDateParam));
        log.info(pBegDateParam + "/" + pEndDateParam);
        Date beginDate = null;
        Date endDate = null;
        if (bDate != null && !bDate.equals("")) {
            beginDate = df.parse((String) (params.get(pBegDateParam)));
        }
        if (eDate != null && !eDate.equals("")) {
            endDate = df.parse((String) (params.get(pEndDateParam)));
        }
        log.info("beginDate = " + beginDate + "   endDate = " + endDate);
        if (beginDate != null && !beginDate.equals("") && endDate != null
                && !endDate.equals("")) {
            if (endDate.before(beginDate)) {
                String errorMess = ClwI18nUtil.getMessage(request,
                        "report.text.beginDateGreaterEndDate", null);
                errors.add("Error", new ActionError("error.simpleGenericError",
                        errorMess));
            }
        }
    }
    
    private static void checkInpuDate(Map params, String dateKey, SimpleDateFormat df, 
            HttpServletRequest request, ActionErrors errors, String dateLabelKey)
            throws ParseException {
        String dateString = (String) (params.get(dateKey));
        if (Utility.isSet(dateString)){
	        if (!Utility.isValidDate(dateString, df)){ 
	        	String dateLabel = ClwI18nUtil.getMessage(request, dateLabelKey, null);
	        	
	        	String errorMess = ClwI18nUtil.getMessage(request,
	                    "error.invalidDate", new String[]{dateLabel, dateString});
	        	errorMess = ESAPI.encoder().decodeForHTML(errorMess);
	            errors.add("Error", new ActionError("error.simpleGenericError", errorMess));
	        }
        }
    }
}
