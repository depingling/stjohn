package com.cleanwise.view.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.Admin2SiteLoaderMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

import java.util.*;
import java.io.*;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import jxl.*;


/**
 *
 * @author veronika
 */
public class Admin2SiteLoaderMgrLogic {
  public final static String VERSION_NUMBER = "1";

  public Admin2SiteLoaderMgrLogic() {
  }

    public static void init(ActionForm form) throws Exception {
      Admin2SiteLoaderMgrForm pForm = (Admin2SiteLoaderMgrForm) form;
      pForm.init();
    }


  public static ActionErrors uploadFile(HttpServletRequest request,
                                        ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    Admin2SiteLoaderMgrForm pForm = (Admin2SiteLoaderMgrForm) form;
    pForm.init();
    FormFile file = pForm.getXlsFile();
    if (file == null) {
      String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.fileNotFound",null);
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    try {
      long fileSize = file.getFileSize();
      if (fileSize == 0) {
        String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.bad_upload_file",new String[]{file.getFileName()});
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
      }
      if (fileSize > 0xFFFFFF) {
        String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.fileTooLong",new String[]{fileSize+""});
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
      }
      String uploadFileName = file.getFileName();
      InputStream stream = null;
      try {
        stream = file.getInputStream();
      } catch (Exception exc) {
        String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.upload_file_error",new String[]{exc.getMessage()});
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
      }
      String fileName = uploadFileName;
      int ind = fileName.lastIndexOf('/');
      int ind1 = fileName.lastIndexOf('\\');
      if (ind1 > ind) ind = ind1;
      if (ind > 0) fileName = fileName.substring(ind + 1);
      pForm.setFileName(fileName);


      WorkbookSettings ws = new WorkbookSettings();
      ws.setEncoding("ISO-8859-1");
      Workbook workbook = Workbook.getWorkbook(stream,ws);
      Sheet sheet = workbook.getSheet(0);
      int colQty = sheet.getColumns();

      boolean[] emptyColumnFl = new boolean[colQty];
      for (int ii = 0; ii < colQty; ii++) {
        emptyColumnFl[ii] = true;
      }
      int rowQty = sheet.getRows();
      if (rowQty < 1) {
        String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.emptySheet",new String[]{uploadFileName});
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
      }
      if (colQty < pForm.getColumnNum()) {
        String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.wrongColumnNum",null);
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
      }

      if (colQty >pForm.getColumnNum()){
        colQty = pForm.getColumnNum();
      }

      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      AccountViewVector storeAccounts = new AccountViewVector();
      if (appUser.isaAdmin()) {
          Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
          BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
          IdVector storesIdV = new IdVector();
          storesIdV.add(new Integer(appUser.getUserStore().getStoreId()));
          crit.setStoreBusEntityIds(storesIdV);
          storeAccounts = accountEjb.getAccountsViewList(crit);
      }
      Country countryEjb = APIAccess.getAPIAccess().getCountryAPI();
      CountryDataVector countryDV = countryEjb.getAllCountries();
//      Set<String> countryNames = new TreeSet<String>();
      Map countryMap = new HashMap();
      for (int i = 0; countryDV != null && i < countryDV.size(); i++) {
          CountryData countryD = (CountryData) countryDV.get(i);
          countryMap.put(countryD.getShortDesc().toUpperCase(), countryD);
      }
      // parsing table
      String[][] sourceTable = new String[rowQty][colQty];
      boolean isErrors = false;
      for (int jj = 0; jj < rowQty; jj++) {
        String[] row = new String[colQty];
        String[] rowErrors = new String[colQty];

        for (int ii = 0; ii < colQty; ii++) {
          row[ii] = null;
          rowErrors[ii] = null;
          Cell cell = sheet.getCell(ii, jj);
          String valS = null;
          if (cell != null) {
              valS = cell.getContents();
          }
          if (jj > 0) {  // skip header
              // check mandatory fields
              if (!Utility.isSet(valS)) {
                if (pForm.isMandatoryField(ii)) {
                    String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.noValue", new String[]{pForm.getSiteLoadFieldName(ii)});
                    rowErrors[ii] = errorMess;
                    isErrors = true;
                }
                continue;
              }
              valS = valS.trim();
              if (ii == 0) {
                rowErrors[ii] = checkVersionNumber(valS, request);
              }
              if (ii == 1) {
                Cell accountNameCell = sheet.getCell(ii + 1, jj);
                String accountName = accountNameCell.getContents();
                rowErrors[ii] = checkAccountId(valS, accountName, appUser, storeAccounts, request);
              }
              if (ii == 5) {
                  if (valS != null) valS = valS.toUpperCase();
                  rowErrors[ii] = checkBooleanValue(request, pForm.getSiteLoadFieldName(ii), valS);
              }
              if (ii == 6) {
                  if (valS != null) valS = valS.toUpperCase();
                  rowErrors[ii] = checkBooleanValue(request, pForm.getSiteLoadFieldName(ii), valS);
              }
              if (ii == 7) {
                  if (valS != null) valS = valS.toUpperCase();
                  rowErrors[ii] = checkBooleanValue(request, pForm.getSiteLoadFieldName(ii), valS);
              }
              if (ii == 17) {
                  valS = valS.toUpperCase();
                  rowErrors[ii] = checkCountry(valS, countryMap.keySet(), request);
              }
              if (rowErrors[ii] != null) {
                  isErrors = true;
              }

          }
          row[ii] = valS;
          }
         if (jj > 0) {  // skip header
           // check state field
            CountryData siteCountry = (CountryData)countryMap.get(row[17]);
            rowErrors[15] = checkState(row[15], siteCountry, request);
           if (rowErrors[15] != null) {
             isErrors = true;
           }
         }
         sourceTable[jj] = row;
         pForm.addErrors(rowErrors);
      }
      pForm.setSourceTable(sourceTable);

     // return when errors
     if (isErrors) {
         pForm.setDownloadErrorButton(true);
         return ae;
     }

     return loadSites(pForm, request);

    } catch (Exception e) {
        String errorMess = Utility.getUiErrorMess(e.getMessage());
        if (errorMess == null) errorMess = e.getMessage();
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
    } finally {
      file.destroy();
    }
  }

    private static String checkVersionNumber(String pVersion, HttpServletRequest request) {
        if (!pVersion.equals(VERSION_NUMBER)) {
            return ClwI18nUtil.getMessage(request,"accAdmin.loaders.versionNumberError", new String[]{VERSION_NUMBER});
        } else {
            return null;
        }
    }

    private static String checkAccountId(String pAccountIdS, String pAccountName, CleanwiseUser appUser,
                                         AccountViewVector storeAccounts, HttpServletRequest request) {
        int accountId = 0;
        try {
          accountId = (new Integer(pAccountIdS)).intValue();
        } catch (Exception e) {
          return ClwI18nUtil.getMessage(request,"accAdmin.loaders.wrongAccountId", null);
        }
        String realAccountName = "";
        if (appUser.isaAdmin()) {
            // check that accountId is a current store account
            boolean found = false;
            for (int i=0; i<storeAccounts.size();i++) {
                AccountView accountView = (AccountView)storeAccounts.get(i);
                if (accountView.getAcctId() == accountId) {
                    found = true;
                    realAccountName = accountView.getAcctName();
                    break;
                }
            }
            if (!found) {
                return ClwI18nUtil.getMessage(request,"accAdmin.loaders.accountNotAllowed", new String[]{accountId+""});
            }
        } else if (appUser.isaAccountAdmin()) {
            // check that accountId is a current account
            if (accountId != appUser.getUserAccount().getAccountId()) {
                return ClwI18nUtil.getMessage(request,"accAdmin.loaders.accountNotAllowed", new String[]{accountId+""});
            }
            realAccountName = appUser.getUserAccount().getBusEntity().getShortDesc();
        }
        if (realAccountName == null) {
            realAccountName = "";
        }
        if (realAccountName.equals(pAccountName) == false) {
            return ClwI18nUtil.getMessage(request,"accAdmin.loaders.wrongAccountName", new String[]{pAccountName, accountId+""});
        }
        return null;
    }

    private static String checkBooleanValue(HttpServletRequest pRequest,
            String pColumnName, String pValue) {
        if (Utility.isSet(pValue)
                && ("true".equalsIgnoreCase(pValue) == true
                        || "false".equalsIgnoreCase(pValue) == true
                        || "t".equalsIgnoreCase(pValue) || "f".equalsIgnoreCase(pValue))) {
            return null;
        }
        return ClwI18nUtil.getMessage(pRequest, "accAdmin.loaders.wrongBooleanValue", new String[] { pColumnName });
    }

    private static String checkState(String pStateValue, CountryData pCountryD, HttpServletRequest request) throws Exception {
        String errorMessage = null;
        if (pCountryD != null) {
          APIAccess factory = new APIAccess();
          Country countryEjb = factory.getCountryAPI();
          CountryPropertyData countryProp = countryEjb.getCountryPropertyData(pCountryD.getCountryId(), RefCodeNames.COUNTRY_PROPERTY.USES_STATE);
          boolean isStateProvinceRequired = countryProp != null && countryProp.getValue().equalsIgnoreCase("true");
          if (isStateProvinceRequired && !Utility.isSet(pStateValue)) {
            errorMessage = ClwI18nUtil.getMessage(request,"accAdmin.loaders.empty.error", new String[]{Admin2SiteLoaderMgrForm.STATE, pCountryD.getShortDesc() });
          }
          if (!isStateProvinceRequired && Utility.isSet(pStateValue)) {
            errorMessage = ClwI18nUtil.getMessage(request,"accAdmin.loaders.must.be.empty.error", new String[]{Admin2SiteLoaderMgrForm.STATE, pCountryD.getShortDesc() });
          }
        }
        return errorMessage;
    }

    private static String checkCountry(String pCountryName, Set<String> pCountryNames, HttpServletRequest pRequest) {
        if (pCountryNames.contains(pCountryName) == false) {
            return ClwI18nUtil.getMessage(pRequest, "accAdmin.loaders.countryDoesntExist", new String[]{pCountryName});
        }
        return null;
    }

    private static ActionErrors loadSites(Admin2SiteLoaderMgrForm pForm, HttpServletRequest request) throws Exception {
        ActionErrors ae = new ActionErrors();
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
       // AddressValidator validator = APIAccess.getAPIAccess().getAddressValidatorAPI();
        String[][] sourceTable = pForm.getSourceTable();
        SiteDataVector sites =  new SiteDataVector();
        boolean errors = false;
        for (int i = 1; i < sourceTable.length; i++) {
            String[] row = sourceTable[i];
            int accountId = 0;
            try {
              accountId = (new Integer(row[1])).intValue();
            } catch (Exception e) {
            }
            try {
                SiteData siteD = parseSiteData(row, accountId, request);
                sites.add(siteD);
            } catch (Exception e) {
            	String errorMsg = e.getMessage()==null ? e.toString() : e.getMessage();
                pForm.addError(errorMsg, i, 16);
                errors = true;
            }
        }
        if (!errors) {
            SiteDataVector resultSites = siteEjb.addSites(sites);
            pForm.setSuccessfullyLoaded(true);
            pForm.setDownloadErrorButton(false);
            // set site ids to result tabl
            for (int i=0; i<resultSites.size(); i++) {
                SiteData site = (SiteData)resultSites.get(i);
                String[] row = sourceTable[i+1];
                row[20] = ""+site.getSiteId();
            }
        } else {
            pForm.setSuccessfullyLoaded(false);
            pForm.setDownloadErrorButton(true);
        }
        return ae;
    }



    private static SiteData parseSiteData(String[] pRow, int accountId, HttpServletRequest request) throws Exception {
        SiteData siteD = null;
        Date runDate = new Date();
        String siteRefNum = pRow[4];
        int siteId = 0;
        String siteIdS = pRow[20];
        if (siteIdS != null) {
            try {
                siteId = (new Integer(siteIdS)).intValue();
            } catch (Exception e) {
                String errorMessage = ClwI18nUtil.getMessage(request,"accAdmin.loaders.wrongSiteId", null);
                throw new Exception(errorMessage);
            }
        }
        if (siteId > 0) {
            siteD = getSiteIfExistBySiteId(siteId, accountId);
            if (siteD == null) {
                String errorMessage = ClwI18nUtil.getMessage(request,"accAdmin.loaders.siteIdNotValidForAccount", null);
                throw new Exception(errorMessage);
            }
        } else {
            siteD = getSiteIfExistByRefNum(siteRefNum, accountId);
        }
        boolean isNew = false;
        if (siteD == null) {
          siteD = SiteData.createValue();
          isNew = true;
        }

        String cuser = "siteLoader";
        // account
        siteD.getAccountBusEntity().setBusEntityId(accountId);

        // getting address info
        AddressData siteAddressD = siteD.getSiteAddress();
        siteAddressD.setAddress1(pRow[10]);
        siteAddressD.setAddress2(pRow[11]);
        siteAddressD.setAddress3(pRow[12]);
        siteAddressD.setAddress4(pRow[13]);
        siteAddressD.setCity(pRow[14]);
        siteAddressD.setStateProvinceCd(pRow[15]);
        siteAddressD.setPostalCode(pRow[16]);
        siteAddressD.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE); 
        siteAddressD.setCountryCd(pRow[17]);
        siteAddressD.setModBy(cuser);
        siteAddressD.setModDate(runDate);
        if (isNew) {
          siteAddressD.setAddBy(cuser);
          siteAddressD.setAddDate(runDate);
          siteAddressD.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
          siteAddressD.setPrimaryInd(true);
        }
       // AddressValidator validator = APIAccess.getAPIAccess().getAddressValidatorAPI();
      //  if (!validator.validateAddress(siteAddressD)) {
       //     String errorMessage = ClwI18nUtil.getMessage(request,"error.addressValidation", null);
       //     throw new Exception(errorMessage);
       // }

        siteD.setSiteAddress(siteAddressD);

        // getting short description
        BusEntityData busEntityD = siteD.getBusEntity();
        busEntityD.setShortDesc(pRow[3]);

        busEntityD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
        busEntityD.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);

        busEntityD.setEffDate(runDate);
        busEntityD.setModDate(runDate);
        busEntityD.setModBy(cuser);
        if (isNew) {
          busEntityD.setAddBy(cuser);
          busEntityD.setAddDate(runDate);
          busEntityD.setLocaleCd("unk");
        }

        // set properties
        setProperty(siteD, RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER, siteRefNum);

        // taxable property
        PropertyData taxableIndicator = siteD.getTaxableIndicator();
        taxableIndicator.setValue(pRow[5]);

        // allow corporate scheduled order property
        PropertyData allowCorpSchedOrder = siteD.getAllowCorpSchedOrder();
        if(allowCorpSchedOrder == null){
      	  allowCorpSchedOrder=PropertyData.createValue();
      	  siteD.setAllowCorpSchedOrder(allowCorpSchedOrder);
    	}
        allowCorpSchedOrder.setValue(pRow[6]);

        setProperty(siteD, RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES, pRow[7]);
        PropertyData propD = siteD.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
        propD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
        setProperty(siteD, RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG, pRow[18]);
        setProperty(siteD, RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS, pRow[19]);

        return siteD;
    }

    private static void setProperty(SiteData pSiteData, String pPropName, String pPropValue ) {
        PropertyDataVector props = pSiteData.getMiscProperties();
        PropertyData nprop = pSiteData.getMiscProp(pPropName);
        if (nprop == null) {
            nprop = PropertyData.createValue();
            nprop.setShortDesc(pPropName);
            props.add(nprop);
        }
        nprop.setValue(pPropValue);
    }


    private static SiteData getSiteIfExistByName(String pSiteName, int pAccountId) throws Exception {
        QueryRequest qr = new QueryRequest();
        IdVector accountIds = new IdVector();
        accountIds.add(new Integer(pAccountId));
        qr.filterByAccountIds(accountIds);
        qr.filterByOnlySiteName(pSiteName, QueryRequest.EXACT);
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        SiteDataVector sites =  siteEjb.getSitesForAccount(qr);
        if (sites.size() > 0) {
            return (SiteData)sites.get(0);
        } else {
            return null;
        }
    }

    private static SiteData getSiteIfExistBySiteId(int pSiteId, int pAccountId) throws Exception {
        QueryRequest qr = new QueryRequest();
        IdVector accountIds = new IdVector();
        accountIds.add(new Integer(pAccountId));
        qr.filterByAccountIds(accountIds);
        qr.filterBySiteId(pSiteId);
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        SiteDataVector sites =  siteEjb.getSitesForAccount(qr);
        if (sites.size() > 0) {
            return (SiteData)sites.get(0);
        } else {
            return null;
        }
    }

    private static SiteData getSiteIfExistByRefNum(String pRefNum, int pAccountId) throws Exception {
        QueryRequest qr = new QueryRequest();
        qr.filterByRefNum(pRefNum, QueryRequest.EXACT);
        IdVector accountIds = new IdVector();
        accountIds.add(new Integer(pAccountId));
        qr.filterByAccountIds(accountIds);
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        SiteDataVector sites =  siteEjb.getSitesForAccount(qr);
        if (sites.size() > 0) {
            return (SiteData)sites.get(0);
        } else {
            return null;
        }
    }



    public static ActionErrors downloadErrors(HttpServletRequest request, HttpServletResponse response ,
                                        ActionForm form) throws Exception  {
        ActionErrors ae = new ActionErrors();
        Admin2SiteLoaderMgrForm pForm = (Admin2SiteLoaderMgrForm) form;
        String fileName  = pForm.getFileName();

        GenericReportResultViewVector excel = new GenericReportResultViewVector();
        excel.add(generateExcelRows(request, pForm, true));

        printExcelTable(fileName, excel, request, response);
        return ae;
    }

    public static ActionErrors exportResults(HttpServletRequest request, HttpServletResponse response ,
                                        ActionForm form) throws Exception  {
        ActionErrors ae = new ActionErrors();
        Admin2SiteLoaderMgrForm pForm = (Admin2SiteLoaderMgrForm) form;
        String fileName  = pForm.getFileName();
        fileName = fileName.replaceAll("\\.", "-res.");

        GenericReportResultViewVector excel = new GenericReportResultViewVector();
        excel.add(generateExcelRows(request, pForm, false));

        printExcelTable(fileName, excel, request, response);
        return ae;
    }

    private static void printExcelTable(String fileName,
                                        GenericReportResultViewVector excel,
                                        HttpServletRequest request,
                                        HttpServletResponse response)
                                                       throws Exception {
        response.setContentType("application/x-excel");
        String browser = (String)request.getHeader("User-Agent");
        boolean isMSIE6 = browser!=null && browser.indexOf("MSIE 6")>=0;
        if (!isMSIE6){
            response.setHeader("extension", "xls");
        }
        if(isMSIE6){
            response.setHeader("Pragma", "public");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Cache-Control", "public");
        }
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);

        try {
            ReportWritter.writeExcelReportMulti(excel, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.flushBuffer();



    }

    public static ActionErrors exportSites(HttpServletRequest request, HttpServletResponse response,
                                        ActionForm form) throws Exception  {
        ActionErrors ae = new ActionErrors();
        Admin2SiteLoaderMgrForm pForm = (Admin2SiteLoaderMgrForm) form;

        String fileName = "sitesOfAccount.xls";
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        SiteDataVector sites = getSitesForAccount(pForm, appUser);
        GenericReportResultViewVector excel = new GenericReportResultViewVector();
        excel.add(generateExcelRows(sites, request, pForm));

        printExcelTable(fileName, excel, request, response);

        return ae;
    }

    public static ActionErrors exportTemplate(HttpServletRequest request, HttpServletResponse response,
                                        ActionForm form) throws Exception  {
        ActionErrors ae = new ActionErrors();
        Admin2SiteLoaderMgrForm pForm = (Admin2SiteLoaderMgrForm) form;
        String fileName = "sitesOfAccountTemplate.xls";

        GenericReportResultViewVector excel = new GenericReportResultViewVector();
        excel.add(generateHeader(GenericReportResultView.createValue(), pForm, false));

        printExcelTable(fileName, excel, request, response);

        return ae;
    }

    private static SiteDataVector getSitesForAccount(Admin2SiteLoaderMgrForm pForm, CleanwiseUser appUser) throws Exception {
        IdVector accountIds;
        if (appUser.isaAccountAdmin()) {
          int accountId = appUser.getUserAccount().getAccountId();
          accountIds = new IdVector();
          accountIds.add(new Integer(accountId));
        } else {
            accountIds = Utility.toIdVector(pForm.getLocateStoreAccountForm().getAccountsToReturn());
            try {
                int accountId = ((Integer)accountIds.get(0)).intValue();
            } catch (Exception e) {
                throw new Exception("Wrong Account Id");
            }
        }
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();

        QueryRequest qr = new QueryRequest();
        qr.filterByAccountIds(accountIds);
        ArrayList<String> statusList = new ArrayList<String>();
        statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.LIMITED);
        qr.filterBySiteStatusCdList(statusList);

        return  siteBean.getSitesForAccount(qr);
    }

    private static GenericReportResultView generateExcelRows(SiteDataVector sites, HttpServletRequest request, Admin2SiteLoaderMgrForm pForm) {
        GenericReportResultView xlog = GenericReportResultView.createValue();
        try {
            ArrayList r = null;
            ArrayList  restable = new ArrayList();
            xlog.setTable(restable);
            Iterator i = sites.iterator();
            while (i.hasNext()) {
                SiteData site = (SiteData)i.next();
                r = new ArrayList();
                r.add(VERSION_NUMBER);          //VERSION
                r.add(site.getAccountId());     //ACCOUNT_ID
                r.add(site.getAccountBusEntity().getShortDesc());   //ACCOUNT_NAME
                r.add(site.getBusEntity().getShortDesc());          //SITE_NAME
                PropertyData prop = site.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
                r.add(prop!=null && Utility.isSet(prop.getValue())?prop.getValue():""); //REF_NUM
                String taxable = site.getTaxableIndicator().getValue();
                r.add(Utility.isTrue(taxable)?"TRUE":"FALSE");       //TAXABLE
                r.add(site.isAllowCorpSchedOrder()?"TRUE":"FALSE"); //ALLOW_CORP_SCHED_ORDER
                prop = site.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
                r.add(prop!=null && Utility.isTrue(prop.getValue())?"TRUE":"FALSE");   //SHARE_BUYER_OG
                r.add(""); //FIRST_NAME
                r.add(""); //LAST_NAME
                AddressData address = site.getSiteAddress();
                r.add(address.getAddress1()); //ADDRESS_1
                r.add(address.getAddress2());//ADDRESS_2
                r.add(address.getAddress3()); //ADDRESS_3
                r.add(address.getAddress4()); //ADDRESS_4
                r.add(address.getCity());//CITY
                r.add(address.getStateProvinceCd());//STATE
                r.add(address.getPostalCode());//POSTAL_CODE
                r.add(address.getCountryCd());//COUNTRY
                prop = site.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
                r.add(prop != null && Utility.isSet(prop.getValue())?prop.getValue():""); // SHIPPING_MESSAGE
                prop = site.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS);
                r.add(prop != null && Utility.isSet(prop.getValue())?prop.getValue():""); // OG_COMMENTS
                r.add(site.getSiteId()); // SITE_ID

                restable.add(r);
                r = null;
            }
            xlog.setTable(restable);
            generateHeader(xlog, pForm, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return xlog;

    }

    private static GenericReportResultView
        generateHeader(GenericReportResultView xlog, Admin2SiteLoaderMgrForm pForm, boolean addErrorColumn) throws Exception {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        String[] headers = pForm.getSiteLoadFields();
        for (int h=0; h<headers.length; h++) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",headers[h],0,255,"VARCHAR2"));
        }
        if (addErrorColumn) {
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Error",0,255,"VARCHAR2"));
        }
        xlog.setHeader(header);
        xlog.setColumnCount(header.size());
        return xlog;
    }



    private static GenericReportResultView
        generateExcelRows(HttpServletRequest request, Admin2SiteLoaderMgrForm pForm, boolean pAddErrorColumn)
    throws Exception {

        GenericReportResultView xlog = GenericReportResultView.createValue();
        ArrayList  restable = new ArrayList();
        String[][] sourceTable = pForm.getSourceTable();
        for (int i = 1; i<sourceTable.length; i++) {
            ArrayList r = new ArrayList();
            for (int j=0; j<sourceTable[i].length; j++) {
                r.add(sourceTable[i][j]);
            }
            if (pAddErrorColumn) {
                // adds errors for row if exists
                String[] rowErrors = pForm.getRowErrors(i);
                if (rowErrors.length > 0) {
                    StringBuffer rowError = new StringBuffer();
                    for (int k=0; k<rowErrors.length; k++) {
                        if (Utility.isSet(rowErrors[k])) {
                            rowError.append(rowErrors[k]);
                            rowError.append("; ");
                        }
                    }
                    if (rowError.length() > 0) {
                        rowError.delete(rowError.length()-2, rowError.length());
                    }
                    r.add(rowError.toString());
                }
            }
            restable.add(r);
        }
        xlog.setTable(restable);
        generateHeader(xlog, pForm, pAddErrorColumn);

        return xlog;
    }


}
