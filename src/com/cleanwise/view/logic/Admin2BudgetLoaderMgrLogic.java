package com.cleanwise.view.logic;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Budget;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.BudgetUtil;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountSearchResultView;
import com.cleanwise.service.api.value.AccountSearchResultViewVector;
import com.cleanwise.service.api.value.AccountView;
import com.cleanwise.service.api.value.AccountViewVector;
import com.cleanwise.service.api.value.BudgetDetailData;
import com.cleanwise.service.api.value.BudgetDetailDataVector;
import com.cleanwise.service.api.value.BudgetView;
import com.cleanwise.service.api.value.BudgetViewVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.FiscalCalendarInfo;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.view.forms.Admin2BudgetLoaderMgrForm;
import com.cleanwise.view.forms.LocateStoreAccountForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ReportWritter;


/**
 *
 * @author veronika
 */
public class Admin2BudgetLoaderMgrLogic {

  private static final Logger log = Logger.getLogger(Admin2BudgetLoaderMgrLogic.class);

  public final static String VERSION_NUMBER = "1";  

  public Admin2BudgetLoaderMgrLogic() {
  }

    public static void init(ActionForm form) throws Exception {
      Admin2BudgetLoaderMgrForm pForm = (Admin2BudgetLoaderMgrForm) form;
      pForm.init();
    }


  public static ActionErrors uploadFile(HttpServletRequest request,
                                        ActionForm form) throws Exception {
    ActionErrors ae = new ActionErrors();
    Admin2BudgetLoaderMgrForm pForm = (Admin2BudgetLoaderMgrForm) form;
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
        if (rowQty < 2) {
          String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.emptySheet",new String[]{uploadFileName});
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
        if (!(colQty == pForm.getColumnNum() || colQty == pForm.getColumnNum()-1)) {
          String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.wrongColumnNum",null);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }

      // parsing table & init sites cache
      String[][] sourceTable = new String[rowQty][colQty];
      
      final Map<Integer,TreeMap<Integer,BusEntityData>> sitesById = new TreeMap<Integer, TreeMap<Integer,BusEntityData>>();
      
      Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
      Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
      int siteId ;
      int fiscalYear;
      
      List<String> inValidFCalYearSiteIdList = new ArrayList<String>();
      List<String> noFCalYearDefinedSiteIdList = new ArrayList<String>();
      List<String> pastFCalSiteIdList = new ArrayList<String>();
      List<String> noFCalFoundSiteIdList = new ArrayList<String>();
      Map<Integer, BusEntityData> acctBusBySiteIdMap = new HashMap<Integer, BusEntityData>();
      Map<Integer, BusEntityData> siteBusBySiteIdMap = new HashMap<Integer, BusEntityData>();
      boolean isError = false;
      
      for (int jj = 0; jj < rowQty; jj++) {
         // String[] row = new String[colQty];
    	  String[] row = new String[pForm.getColumnNum()]; // There are 20 columns with fiscal year column
          siteId = -1;
          fiscalYear = -1;
          int accountId = -1;
          for (int ii = 0; ii < colQty; ii++) {
              Cell cell = sheet.getCell(ii, jj);
              String valS = null;
              if (cell != null) {
                  valS = cell.getContents();
              }
              if (jj!= 0 && ii == 1) {
            	  siteId = Utility.parseInt(valS);
            	  if (siteId > 0) {       
            		  BusEntityData accountEntity = acctBusBySiteIdMap.get(siteId);
            		  if (accountEntity == null){
            			  BusEntityData siteEntity = getSiteBusEntity(siteId, siteEjb);
            			  if (siteEntity != null){
            				  siteBusBySiteIdMap.put(siteId, siteEntity);                    			  
    	        			  accountEntity = getAccountBusEntity(siteId, accountEjb);
    	        			  acctBusBySiteIdMap.put(siteId, accountEntity);
            			  }
                	  }
                  }
            	  BusEntityData accountEntity = acctBusBySiteIdMap.get(siteId);
            	  if (accountEntity != null)
            		  accountId = accountEntity.getBusEntityId();
              } 
              if(colQty==20) {
	              if(jj!= 0 && ii==colQty-1) { //Fiscal Year is the last column in the file.
	            	  try {
	            		  fiscalYear = Integer.parseInt(valS);
	            	  }catch(Exception e) {
	            		  isError = true;
	            		  if(!inValidFCalYearSiteIdList.contains(String.valueOf(siteId)))
	            			  inValidFCalYearSiteIdList.add(String.valueOf(siteId));
	            		  break;
	            	  }
	            	  if(fiscalYear==0 && siteId>0) {
	            		  isError = true;
	            		  if(!inValidFCalYearSiteIdList.contains(String.valueOf(siteId)))
	            			  inValidFCalYearSiteIdList.add(String.valueOf(siteId));
	            	  } else if(fiscalYear<0) {
	            		  isError = true;
	            		  if(!noFCalYearDefinedSiteIdList.contains(String.valueOf(siteId)))
	            			  noFCalYearDefinedSiteIdList.add(String.valueOf(siteId));
	            	  } else {
	            		  if (accountId > 0){         		  
		            		  FiscalCalenderData currentfiscalCal = accountEjb.getCurrentFiscalCalender(accountId);
	            			  if(currentfiscalCal!=null && currentfiscalCal.getFiscalYear()>fiscalYear) {
	            				  isError = true;
	            				  if(!pastFCalSiteIdList.contains(String.valueOf(siteId)))
	            					  pastFCalSiteIdList.add(String.valueOf(siteId));
	            			  }else {
			            		  FiscalCalenderData fCalData = accountEjb.getFiscalCalenderForYear(accountId, fiscalYear);
			            		  if(fCalData==null) {
			            			  isError = true;
			            			  if(!noFCalFoundSiteIdList.contains(String.valueOf(siteId)))
			            				  noFCalFoundSiteIdList.add(String.valueOf(siteId));
			            		  } 
		            		  }
	            		  }
	            	  }
	              }
              } else if (colQty==19 && fiscalYear==-1){  //support excel files without fiscal year column.
              	  if(jj!= 0 && ii==colQty-1) {
              		if (accountId > 0){   
	          		    fiscalYear = getCurrentFiscalYear(accountId);
	          		    if(fiscalYear<0) {
	          		    	 isError = true;
	          		    	 noFCalFoundSiteIdList.add(String.valueOf(siteId));
	          		    } else if(fiscalYear==0 && siteId>0) {
	          		    	isError = true;
	          		    	inValidFCalYearSiteIdList.add(String.valueOf(siteId));
	          		    }else {
	          		    //Add current fiscal year value to support import functionality for old template.
	          		    	row[colQty] = String.valueOf(fiscalYear); 
	          		    }
              		}
              	  }
                }          
              row[ii] = valS;
          }     
          if (siteId > 0 && !isError) {
        	  TreeMap<Integer,BusEntityData> siteByBudgetYear;
        	  boolean doGetSite = true;
        	  if(sitesById.containsKey(siteId)) {
        		  siteByBudgetYear = sitesById.get(siteId);
        		  if(siteByBudgetYear.containsKey(fiscalYear)) {
        			  doGetSite = false;
        		  } 
        	  } else {
        		  siteByBudgetYear = new TreeMap<Integer,BusEntityData>();
        	  }
        	  
        	  if(doGetSite) {
    			  BusEntityData siteEntity = siteBusBySiteIdMap.get(siteId);
    			  if (siteEntity != null){
        			  siteByBudgetYear.put(fiscalYear, siteEntity); 
                	  sitesById.put(siteId, siteByBudgetYear);
    			  }
        	  }
          }
          sourceTable[jj] = row;
      }
      
      if(isError && inValidFCalYearSiteIdList.size()>0) {
    	  String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.inValidFiscalYear",null);
    	  errorMess = errorMess +" "+inValidFCalYearSiteIdList +". \n";
	    	  ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      } 
      if(isError && noFCalYearDefinedSiteIdList.size()>0) {
    	  String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.noFiscalCalendarYearDefined",null);
    	  errorMess = errorMess +" "+noFCalYearDefinedSiteIdList +". \n";
	    	  ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      }
      if(isError && pastFCalSiteIdList.size()>0) {
    	  String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.FiscalYearLessThanCurrentFiscalYear",null);
    	  errorMess = errorMess +" "+pastFCalSiteIdList +". \n";
	      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      }
      if(isError && noFCalFoundSiteIdList.size()>0){
    	  String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.noFiscalCalendarIsFound",null);
    	  errorMess = errorMess +" "+noFCalFoundSiteIdList +". \n";
	      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      }
      
      if(isError) {
    	  return ae;
      }
      
      boolean isErrors = false;
      TreeMap<Integer,BusEntityData> sitesByBudgetYear;
      String[] rowErrors = new String[colQty];
      int idOfSite = 0;
      for (int jj = 1; jj < rowQty; jj++) {
        String[] row = sourceTable[jj];
        String siteIdS = row[1];
        try {
        	idOfSite = 0;
        	idOfSite = Integer.parseInt(siteIdS);
        }catch(Exception e) {
        }
        sitesByBudgetYear = sitesById.get(idOfSite);
        if (sitesByBudgetYear == null){
        	rowErrors = new String[colQty];
        	rowErrors[1] = ClwI18nUtil.getMessage(request,"accAdmin.loaders.siteNotFound", new String[]{siteIdS});
        	pForm.addErrors(rowErrors);
        	isErrors = true;
        	continue;
        }
        for( BusEntityData siteEntity: sitesByBudgetYear.values() ) {
        	 rowErrors = new String[colQty];
        	 for (int ii = 0; ii < colQty; ii++) {
                 rowErrors[ii] = null;
                 if (jj > 0) {  // skip header
                     // check mandatory fields
                     String valS = row[ii];
                     if (!Utility.isSet(valS)) {
                       if (pForm.isMandatoryField(ii)) {
                           String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.noValue", new String[]{pForm.getBudgetLoadFieldName(ii)});
                           rowErrors[ii] = errorMess;
                       }
                       continue;
                     }
                     valS = valS.trim();
                     row[ii] = valS;
                     if (ii == 0) {
                       rowErrors[ii] = checkVersionNumber(valS, request);
                     }
                     if (ii == 1) {
                         rowErrors[ii] = checkSiteId(valS, request, siteEntity);
                     }
                     if (ii == 2) {
                         rowErrors[ii] = checkSiteName(siteIdS, request, siteEntity, valS);
                     }
                     if (ii >= 6) {
                         rowErrors[ii] = checkPeriodValue(valS, request, ii);
                     }
                     if (rowErrors[ii] != null) {
                         isErrors = true;
                     }
                 }
                }
        }
        pForm.addErrors(rowErrors);
      }

      pForm.setSourceTable(sourceTable);
      pForm.setDownloadErrorButton(false);

     // return when errors
     if (isErrors) {
    	 pForm.setDownloadErrorButton(true);
     }

     return loadBudgets(pForm, request, sitesById, acctBusBySiteIdMap, isErrors);
    } catch (Exception e) {
    	e.printStackTrace();
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

    private static String checkSiteId(String pSiteIdS, HttpServletRequest request, BusEntityData siteEntity) {
        try {
          int siteId = (new Integer(pSiteIdS)).intValue();
          if (siteEntity == null) {
              return ClwI18nUtil.getMessage(request,"accAdmin.loaders.siteNotFound", new String[]{siteId+""});
          }
        } catch (Exception e) {
          return ClwI18nUtil.getMessage(request,"accAdmin.loaders.wrongSiteId", null);
        }
        return null;
    }

    private static String checkSiteName(String pSiteIdS, HttpServletRequest request, BusEntityData siteEntity, String pSiteName) {
        try {
          int siteId = (new Integer(pSiteIdS)).intValue();
          if (siteEntity != null && siteEntity.getShortDesc().trim().equals(pSiteName) == false) {
              return ClwI18nUtil.getMessage(request,"accAdmin.loaders.wrongSiteName", new String[]{pSiteName,siteId+""});
          }
        } catch (Exception e) {
        }
        return null;
    }

    private static String checkPeriodValue(String pPeriodValue, HttpServletRequest request, int pRowNum) {
        try {
            toDecimal(pPeriodValue, pRowNum, request);
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    private static ActionErrors loadBudgets(Admin2BudgetLoaderMgrForm pForm, HttpServletRequest request,
    		Map<Integer,TreeMap<Integer,BusEntityData>> pSitesById, Map<Integer, BusEntityData> siteToAccountMap, boolean pWasErrors) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String cuser = (String) session.getAttribute(Constants.USER_NAME);

        String[][] sourceTable = pForm.getSourceTable();

        APIAccess factory = new APIAccess();
        Budget budBean = factory.getBudgetAPI();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        AccountViewVector storeAccounts = new AccountViewVector();
        if (appUser.isaAccountAdmin()) {
            AccountData acc = appUser.getUserAccount();
            AccountView accV = AccountView.createValue();
            accV.setAcctId(acc.getAccountId());
            storeAccounts.add(accV);
        } else {
            Account accountEjb = factory.getAccountAPI();
            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            IdVector storesIdV = new IdVector();
            storesIdV.add(new Integer(appUser.getUserStore().getStoreId()));
            crit.setStoreBusEntityIds(storesIdV);
            storeAccounts = accountEjb.getAccountsViewList(crit);
        }
        try {

            Map<String, CostCenterData> costCenterByName = new TreeMap<String, CostCenterData>();
            Map<String, String> errors = new TreeMap<String, String>();
            Map<Integer,TreeMap<Integer,FiscalCalenderView>> fiscalCalViewMap = new HashMap<Integer,TreeMap<Integer,FiscalCalenderView>>();
            BudgetViewVector budgetViewV = new BudgetViewVector();
            TreeMap<Integer,BusEntityData> sitesByBudgetYear;
            int fiscalYear = -1;
            
            for (int i = 1; i < sourceTable.length; i++) {
                String[] row = sourceTable[i];
                int siteId = 0;
                try {
                  siteId = (new Integer(row[1])).intValue();
                  fiscalYear = Utility.parseInt(row[19]);
                } catch (Exception e) {}

                try {
                	sitesByBudgetYear = pSitesById.get(siteId);
                	BusEntityData siteEntity = null;
                	if(sitesByBudgetYear!=null) {
                		siteEntity = sitesByBudgetYear.get(fiscalYear);
                	}
                	BusEntityData accountEntity = siteToAccountMap.get(siteId);
                	BudgetView budgetV = parseBudgetData(row,
                            siteId,
                            siteEntity.getShortDesc(),                            
                            request,
                            pForm,
                            i,
                            appUser,
                            storeAccounts,
                            fiscalCalViewMap,
                            accountEntity.getBusEntityId(),accountEntity.getShortDesc(),
                            costCenterByName,
                            errors,
                            fiscalYear);

                    budgetViewV.add(budgetV);
                    
                } catch (Exception e) {
                    pWasErrors = true;
                }
            }
            if (!pWasErrors) {
                budBean.updateBudgets(budgetViewV, cuser);
                pForm.setSuccessfullyLoaded(true);
                pForm.setDownloadErrorButton(false);
            } else {
                pForm.setSuccessfullyLoaded(false);
                pForm.setDownloadErrorButton(true);
            }
        } catch (Exception e) {
           pForm.setSuccessfullyLoaded(false);
           pForm.setDownloadErrorButton(true);
        }
        return ae;
    }


    private static BigDecimal toDecimal(String v, int colNum, HttpServletRequest request) throws Exception {
        BigDecimal result = null;
        if (Utility.isSet(v)) {
            try {
                result = new BigDecimal(v);
            } catch (Exception e) {
                String periodNum = (colNum - 5) + "";
                String mess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.wrongPeriodValue", new String[]{v, periodNum});
                throw new Exception(mess);
            }
        }
        return result;
    }

    private static CostCenterData getCostCenterByName(String pCostCenterName,
            int pAccountId, String pAccountName, HttpServletRequest pRequest,
            Admin2BudgetLoaderMgrForm pForm, int pLineNum,
            Map<String, CostCenterData> pCostCenterByName,
            Map<String, String> pErrors) throws Exception {
        String key = pAccountId + "_" + pCostCenterName;
        if (pCostCenterByName.containsKey(key) == false) {
            Account accEjb = APIAccess.getAPIAccess().getAccountAPI();
            CostCenterDataVector costCenterDV = accEjb.getCostCenterByName(pCostCenterName, pAccountId, Account.EXACT_MATCH, Account.ORDER_BY_NAME);
            if (costCenterDV != null && costCenterDV.size() > 0) {
                pCostCenterByName.put(key, (CostCenterData) costCenterDV.get(0));
            } else {
                pCostCenterByName.put(key, null);
                String mess = "";
                CatalogData catalogD = accEjb.getAccountCatalog(pAccountId);
                 if (catalogD == null) {
                        mess = ClwI18nUtil.getMessage(pRequest, "accAdmin.loaders.catalogNotFound", new String[] {
                                "" + pAccountId, pAccountName });
                } else {
                    Store storeEjb = APIAccess.getAPIAccess().getStoreAPI();
                    Budget budgetEjb = APIAccess.getAPIAccess().getBudgetAPI();
                    int storeId = storeEjb.getStoreIdByAccount(pAccountId);

                    costCenterDV = budgetEjb.getCostCenters(storeId, pCostCenterName, "costCenterNameStarts", null, true);
                    boolean costCenterExist = false;
                    for (int i = 0; costCenterDV != null
                            && i < costCenterDV.size(); i++) {
                        CostCenterData costCenterD = (CostCenterData) costCenterDV.get(i);
                        if (costCenterD.getShortDesc().equals(pCostCenterName) == true) {
                            costCenterExist = true;
                            break;
                        }
                    }
                    if (costCenterExist) {
                        mess = ClwI18nUtil.getMessage(pRequest, "accAdmin.loaders.costCenterIsntConfigurated", new String[] {
                                pCostCenterName, "" + catalogD.getCatalogId(),
                                catalogD.getShortDesc() });
                    } else {
                        mess = ClwI18nUtil.getMessage(pRequest, "accAdmin.loaders.costCenterDoesntExist", new String[] { pCostCenterName });
                    }
                }
                pErrors.put(key, mess);
            }
        }
        CostCenterData costCenterD = pCostCenterByName.get(key);
        if (costCenterD == null) {
            String mess = pErrors.get(key);
            pForm.addError(mess, pLineNum, 5);
            throw new Exception(mess);
        } else {
            return costCenterD;
        }
    }

    private static BudgetView parseBudgetData(String[] pRow, int siteId, String siteName, HttpServletRequest request,
                                              Admin2BudgetLoaderMgrForm pForm, int lineNum,
                                              CleanwiseUser appUser, AccountViewVector storeAccounts,
                                              Map<Integer,TreeMap<Integer,FiscalCalenderView>> pFiscalCalViewMap,
                                              int accountId, String accountName,
                                              Map<String, CostCenterData> pCostCenterByName,
                                              Map<String, String> pErrors,int fiscalYear) throws Exception {
        BudgetView budgetV = null;
        Date runDate = new Date();
        
        // check site for account
        int userId = appUser.getUser().getUserId();
        boolean found = false;
        AccountView storeAccount = null;
        for (int i=0; i<storeAccounts.size(); i++) {
            storeAccount = (AccountView)storeAccounts.get(i);
            if (storeAccount.getAcctId() == accountId) {
                found = true;
                break;
            }
        }

        if (!found) {
            String mess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.siteNotFound", new String[]{siteId+""});
            pForm.addError(mess, lineNum, 2);
            throw new Exception(mess);
        }

        FiscalCalenderView fiscalCalendarView = null;
        FiscalCalenderData fiscalCalendar = null;
        TreeMap<Integer,FiscalCalenderView> fCalViewByFiscalYear = pFiscalCalViewMap.get(accountId);
        if(fCalViewByFiscalYear!=null) {
        	fiscalCalendarView = fCalViewByFiscalYear.get(fiscalYear);
        } else {
        	fCalViewByFiscalYear = new TreeMap<Integer,FiscalCalenderView>();
        	pFiscalCalViewMap.put(accountId, fCalViewByFiscalYear);
        }
        
        
        int numOfFiscalPeriods = 0;
        if (fiscalCalendarView == null) {
        	try {
        		Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
        		fiscalCalendarView = accountEjb.getFiscalCalenderVForYear(accountId,fiscalYear);
        		fiscalCalendar = fiscalCalendarView.getFiscalCalender();
        	}catch(Exception e) {
        	}
        	 if (fiscalCalendar == null) {
                 String mess = ClwI18nUtil.getMessage(request,
                         "accAdmin.loaders.fiscalCalendarNotFound",
                         new String[]{"" + accountId, accountName});
                 pForm.addError(mess, lineNum, 0);
                 throw new Exception(mess);
             } else {
            	 fCalViewByFiscalYear.put(fiscalYear, fiscalCalendarView);
             }
        } else {
        	fiscalCalendar = fiscalCalendarView.getFiscalCalender();
        }
        
        FiscalCalendarInfo fci = new FiscalCalendarInfo(fiscalCalendarView);
     	numOfFiscalPeriods  = fci.getBudgetPeriodsAsHashMap().size();

        CostCenterData costCenterD = getCostCenterByName(pRow[4], accountId,
        		accountName, request, pForm, lineNum,
                pCostCenterByName, pErrors);
        Budget budgetEjb = APIAccess.getAPIAccess().getBudgetAPI();
        
        BudgetViewVector budgets = budgetEjb.getBudgetsForSite(accountId, siteId, costCenterD.getCostCenterId(), fiscalCalendar.getFiscalYear());
        if (budgets.size() > 0) {
            budgetV = (BudgetView) budgets.get(0);
        }

        if (budgetV == null ) {
            budgetV = BudgetUtil.createBudgetView();
            budgetV.getBudgetData().setAddDate(runDate);
            budgetV.getBudgetData().setBudgetTypeCd(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET);
            budgetV.getBudgetData().setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
            budgetV.getBudgetData().setBusEntityId(siteId);
            budgetV.getBudgetData().setShortDesc(siteName);
            budgetV.getBudgetData().setCostCenterId(costCenterD.getCostCenterId());
            budgetV.getBudgetData().setBudgetYear(fiscalCalendar.getFiscalYear());
        }
        BudgetDetailDataVector budgetDetails = budgetV.getDetails();
        for (int i = 6, period = 1; i <= 18; i++, period++) {
        	try{
	            BudgetDetailData bdd = BudgetUtil.getBudgetDetail(budgetDetails, period);
	            if (bdd == null) {
	                bdd = BudgetDetailData.createValue();
	                bdd.setPeriod(period);
	                budgetDetails.add(bdd);
	            }
	            if(period<=numOfFiscalPeriods) {
	            	bdd.setAmount(toDecimal(pRow[i], i, request));
	            }else {
	            	bdd.setAmount(null);
	            }
	            
	        }catch(Exception e){
        		String mess = e.getMessage();
        	    pForm.addError(mess, lineNum, i);
                throw new Exception(mess);
            }
        }
        return budgetV;
    }


    public static ActionErrors downloadErrors(HttpServletRequest request, HttpServletResponse response ,
                                        ActionForm form) throws Exception  {
        ActionErrors ae = new ActionErrors();
        Admin2BudgetLoaderMgrForm pForm = (Admin2BudgetLoaderMgrForm) form;
        String fileName  = pForm.getFileName();

        GenericReportResultViewVector excel = new GenericReportResultViewVector();
        excel.add(generateExcelRows(request, pForm));

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
            throw new Exception(e.getMessage());
        }
        response.flushBuffer();
    }

    public static ActionErrors exportBudgets(HttpServletRequest request, HttpServletResponse response,
                                        ActionForm form) throws Exception  {
        ActionErrors ae = new ActionErrors();
        Admin2BudgetLoaderMgrForm pForm = (Admin2BudgetLoaderMgrForm) form;
        String fileName = "budgetsOfAccount.xls";

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int accountId = 0;

        if (appUser.isaAccountAdmin()) {
            accountId = appUser.getUserAccount().getAccountId();
        } else {
            IdVector accountIds = Utility.toIdVector(pForm.getLocateStoreAccountForm().getAccountsToReturn());
            try {
                accountId = ((Integer)accountIds.get(0)).intValue();
            } catch (Exception e) {
                String mess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.wrongAccountId", null);
                throw new Exception(mess);
            }
        }
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        
        int fiscalYear = -1;
        fiscalYear = getSelectedFiscalYear(pForm,accountId);
        
        FiscalCalenderData fCalendarData = accountBean.getFiscalCalenderForYear(accountId,fiscalYear);
        
        if(!(fCalendarData!=null && fiscalYear==fCalendarData.getFiscalYear())) {
        	String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.noFiscalCalendarFound",new String[]{String.valueOf(fiscalYear),
        			String.valueOf(accountId)});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        	
        CostCenterDataVector costCenters =
            accountBean.getAllCostCenters(accountId, Account.ORDER_BY_NAME);
        SiteDataVector sites = getSitesForAccount(pForm, accountId, factory);
        GenericReportResultViewVector excel = new GenericReportResultViewVector();
        excel.add(generateExcelRows(sites, costCenters, request, pForm,accountId));

        printExcelTable(fileName, excel, request, response);

        return ae;
    }


    public static ActionErrors exportTemplate(HttpServletRequest request, HttpServletResponse response,
                                        ActionForm form) throws Exception  {
        ActionErrors ae = new ActionErrors();
        Admin2BudgetLoaderMgrForm pForm = (Admin2BudgetLoaderMgrForm) form;
        String fileName = "siteBudgetTemplate.xls";

        GenericReportResultViewVector excel = new GenericReportResultViewVector();
        excel.add(generateHeader(GenericReportResultView.createValue(), pForm, false));

        printExcelTable(fileName, excel, request, response);

        return ae;
    }


    private static SiteDataVector getSitesForAccount(Admin2BudgetLoaderMgrForm pForm, int pAccountId, APIAccess factory) throws Exception {
        Site siteBean = factory.getSiteAPI();
        QueryRequest qr = new QueryRequest();
        IdVector accountIds = new IdVector();
        accountIds.add(pAccountId);
        qr.filterByAccountIds(accountIds);
        ArrayList<String> statusList = new ArrayList<String>();
        statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.LIMITED);
        qr.filterBySiteStatusCdList(statusList);
        int pBudgetYear = getSelectedFiscalYear(pForm,pAccountId);
        return  siteBean.getSitesForAccountWithBudgets(qr, pAccountId, pBudgetYear);
    }

    private static GenericReportResultView generateExcelRows(SiteDataVector sites,
                                                             CostCenterDataVector costCenters,
                                                             HttpServletRequest request,
                                                             Admin2BudgetLoaderMgrForm pForm, int pAccountId) throws Exception {
        GenericReportResultView xlog = GenericReportResultView.createValue();

        try {
            ArrayList  restable = new ArrayList();
            xlog.setTable(restable);
            Iterator i = sites.iterator();
            int fiscalCalendarPeriods;
            while (i.hasNext()) {
                SiteData site = (SiteData)i.next();
                BudgetViewVector budgets = site.getBudgets();
                for (int c=0; c<costCenters.size(); c++) {
                    CostCenterData cCenter = (CostCenterData)costCenters.get(c);
                    ArrayList r = new ArrayList();
                    r.add(VERSION_NUMBER);          // VERSION
                    r.add(site.getSiteId());        // SITE_ID
                    r.add(site.getBusEntity().getShortDesc());          //SITE_NAME
                    PropertyData prop = site.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
                    r.add(prop!=null && Utility.isSet(prop.getValue())?prop.getValue():""); //REF_NUM

                    String costCenterName = cCenter.getShortDesc();
                    String costCenterCode = cCenter.getCostCenterCode();
                    r.add(costCenterName);  // COST_CENTER_NAME
                    r.add(costCenterCode); // COST_CENTER_CODE
                    fiscalCalendarPeriods = 0;
                    fiscalCalendarPeriods = site.getBudgetPeriods().size();
                    for (int b=0; b<budgets.size(); b++) {
                        BudgetView budget = (BudgetView)budgets.get(b);
                        if (cCenter.getCostCenterId() != budget.getBudgetData().getCostCenterId()) {
                            continue;
                        }
                        ArrayList rb = new ArrayList();
                        rb.addAll(r);
                        
                         
                        // periods
                        HashMap<Integer, BigDecimal> amounts = BudgetUtil.getAmounts(budget.getDetails());
                        for(int amtInd=1; amtInd<=13; amtInd++) {
                        	 if(amtInd<=fiscalCalendarPeriods) {
                        		 rb.add(amounts.get(amtInd));
                        	 } else {
                        		 rb.add("");
                        	 }
                        }
                        /*rb.add(amounts.get(1));
                        rb.add(amounts.get(2));
                        rb.add(amounts.get(3));
                        rb.add(amounts.get(4));
                        rb.add(amounts.get(5));
                        rb.add(amounts.get(6));
                        rb.add(amounts.get(7));
                        rb.add(amounts.get(8));
                        rb.add(amounts.get(9));
                        rb.add(amounts.get(10));
                        rb.add(amounts.get(11));
                        rb.add(amounts.get(12));
                        rb.add(amounts.get(13));*/
                        
                        //Fiscal year (Added as part of bug # 4601)
                        int fiscalYear;
                        if(budget.getBudgetData()!=null) {
                        	fiscalYear = budget.getBudgetData().getBudgetYear();
                        } else {
                            fiscalYear = getSelectedFiscalYear(pForm,pAccountId);
                        }                        
                        rb.add(fiscalYear);

                        restable.add(rb);

                    }
                    if (budgets.size() == 0) {
                   	 for(int amtInd=1; amtInd<=13; amtInd++) {
                   		 r.add("");
                        }
                   	 r.add(getSelectedFiscalYear(pForm,pAccountId));
                        restable.add(r);
                   }
                }
            }
            xlog.setTable(restable);
            generateHeader(xlog, pForm, false);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return xlog;

    }

    private static GenericReportResultView
        generateHeader(GenericReportResultView xlog, Admin2BudgetLoaderMgrForm pForm, boolean addErrorColumn)
            throws Exception
    {
        try {
            GenericReportColumnViewVector header = new GenericReportColumnViewVector();
            String[] headers = pForm.getBudgetLoadFields();
            for (int h=0; h<headers.length; h++) {
                header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",headers[h],0,255,"VARCHAR2"));
            }
            if (addErrorColumn) {
                header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Error",0,255,"VARCHAR2"));
            }
            xlog.setHeader(header);
            xlog.setColumnCount(header.size());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return xlog;
    }




    private static
        GenericReportResultView generateExcelRows(HttpServletRequest request, Admin2BudgetLoaderMgrForm pForm)
            throws Exception
    {

        GenericReportResultView xlog = GenericReportResultView.createValue();
        try {
            ArrayList r = null;
            ArrayList  restable = new ArrayList();
            String[][] sourceTable = pForm.getSourceTable();
            for (int i = 1; i<sourceTable.length; i++) {
                r = new ArrayList();
                for (int j=0; j<sourceTable[i].length; j++) {
                    r.add(sourceTable[i][j]);
                }
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
                restable.add(r);
                r = null;
            }
            xlog.setTable(restable);
            generateHeader(xlog, pForm, true);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return xlog;
    }

    /**
     * Gets the current fiscal year for an account.
     */
    private static int getCurrentFiscalYear(int pAccountId) {
    	int currentFiscalYear = -1;
    	try {
    		APIAccess factory = new APIAccess();
			Account accountBean = factory.getAccountAPI();
			FiscalCalenderData currentFiscalCalendar = accountBean.getCurrentFiscalCalender(pAccountId);
			if(currentFiscalCalendar!=null) {
				currentFiscalYear = currentFiscalCalendar.getFiscalYear();
			}
		} catch (Exception e) {
			log.info("Admin2BudgetLoaderMgrLogic >> An exception has occured while getting current fiscal calendar - " + e.getMessage());
		} 
    	return currentFiscalYear;
    }
    
    /**
     * Gets the selected fiscal year for an account.
     * @param pForm
     * @param pAccountId
     * @return
     */
    private static int getSelectedFiscalYear(Admin2BudgetLoaderMgrForm pForm, int pAccountId) {
    	int fiscalYear = -1;
    	AccountSearchResultView accuntSearchResulstView = getAccountSearchResultView(pForm,pAccountId);
    	if(accuntSearchResulstView!=null) {
    		fiscalYear = accuntSearchResulstView.getSelectedFiscalYear();
    	}
        return fiscalYear;
    }
    
    /**
     * Sets selected fiscal year for an account.
     */
    public static void setSelectedFiscalYear(Admin2BudgetLoaderMgrForm pForm, int pAccountId,int selectedYear) {
    	AccountSearchResultView accuntSearchResulstView = getAccountSearchResultView(pForm,pAccountId);
    	if(accuntSearchResulstView!=null) {
    		accuntSearchResulstView.setSelectedFiscalYear(selectedYear);
    	}
    }
    
    private static AccountSearchResultView getAccountSearchResultView(Admin2BudgetLoaderMgrForm pForm, int pAccountId) {
    	AccountSearchResultView accuntSearchResulstView = null;
    	try {
        	LocateStoreAccountForm locateAccountForm = pForm.getLocateStoreAccountForm();
            if(locateAccountForm!=null) {
            	AccountSearchResultViewVector searchResultsVector = locateAccountForm.getAccountSearchResult();
            	if(searchResultsVector!=null) {
	    	        int accountId;
	    		    for(int ind=0; searchResultsVector!=null && ind<searchResultsVector.size(); ind++) {
	    		    	accuntSearchResulstView = (AccountSearchResultView)searchResultsVector.get(ind);
	    		    	accountId = accuntSearchResulstView.getAccountId();
	    		    	if(pAccountId==accountId) {
	    		    		break;
	    		    	}
	    		    }
            	}
            }
    	}catch(Exception e) {
    		log.info("Admin2BudgetLoaderMgrLogic >>> Could not found fiscal year.");
    	}
        return accuntSearchResulstView;
    }
    
    private static BusEntityData getSiteBusEntity(int siteId, Site siteEjb) throws Exception {
    	DBCriteria crit = new DBCriteria();
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, siteId);
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
        BusEntityDataVector siteBusV = siteEjb.getSiteBusEntityByCriteria(crit);
        if (siteBusV.size() > 0)
        	return (BusEntityData) siteBusV.get(0);
        else
        	return null;
    }
    
    private static BusEntityData getAccountBusEntity(int siteId, Account accountEjb) throws Exception {
    	DBCriteria crit = new DBCriteria();
        crit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY1_ID,  siteId);
        crit.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID,
        		BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY2_ID);
        crit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, 
        		BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,  RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
        
        BusEntityDataVector accountBusV = accountEjb.getAccountBusEntByCriteria(crit);
        if (accountBusV.size() > 0)
        	return (BusEntityData) accountBusV.get(0);
        else
        	return null;
    }
    
}
