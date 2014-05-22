package com.cleanwise.view.logic;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.session.Account;
//import com.cleanwise.service.api.session.AddressValidator;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountSettingsData;
import com.cleanwise.service.api.value.AccountView;
import com.cleanwise.service.api.value.AccountViewVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.ShoppingControlDataVector;
import com.cleanwise.service.api.value.ShoppingControlItemView;
import com.cleanwise.service.api.value.ShoppingControlItemViewVector;
import com.cleanwise.view.forms.Admin2ShoppingControlLoaderMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.Admin2ShoppingControlLoaderMgrLogic.UploadFieldsUtil.UploadFields;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ReportWritter;
import com.cleanwise.view.utils.SessionTool;


/**
 *
 * @author veronika
 */
public class Admin2ShoppingControlLoaderMgrLogic {

  private static final Logger log = Logger.getLogger(Admin2ShoppingControlLoaderMgrLogic.class);

  public final static String VERSION_NUMBER = "1";
  	public static final int
      		VERSION = 0,
      		ACCOUNT_ID = 1,
      		SITE_ID = 3,
      		STORE_SKU =5,
      		MAX_ORDER_QTY = 10, 
      		RESTRICTION_DAYS = 11;

  	public Admin2ShoppingControlLoaderMgrLogic() {
  	}

    public static void init(ActionForm form) throws Exception {
      Admin2ShoppingControlLoaderMgrForm pForm = (Admin2ShoppingControlLoaderMgrForm) form;
      pForm.init();
    }

    public static ActionErrors uploadFile(HttpServletRequest request,
    		ActionForm form) throws Exception {
    	ActionErrors ae = new ActionErrors();
    	Admin2ShoppingControlLoaderMgrForm pForm = (Admin2ShoppingControlLoaderMgrForm) form;
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

    		HttpSession session = request.getSession();
    		Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
    		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    		AccountViewVector storeAccounts = new AccountViewVector();
    		List validAccountIdList = new ArrayList();
    		if (appUser.isaAdmin()) {    			
    			BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
    			IdVector storesIdV = new IdVector();
    			storesIdV.add(new Integer(appUser.getUserStore().getStoreId()));
    			crit.setStoreBusEntityIds(storesIdV);
    			storeAccounts = accountEjb.getAccountsViewList(crit);
    			for (int i = 0; i < storeAccounts.size(); i++){
    				validAccountIdList.add(((AccountView)storeAccounts.get(i)).getAcctId());
    			}
    		} else if (appUser.isaAccountAdmin()) {
    			validAccountIdList.add(appUser.getUserAccount().getAccountId());
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
    		if (!(colQty == pForm.getColumnNum() || colQty == pForm.getColumnNum()+1)) {
    			String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.wrongColumnNum",null);
    			ae.add("error", new ActionError("error.simpleGenericError", errorMess));
    			return ae;
    		}    		  		
    		
    		// parsing table
    		String[][] sourceTable = new String[rowQty][colQty];
    		ArrayList errors = new ArrayList();
    		List accountIdSList = new ArrayList();
    		
    		boolean accountError = false;
    		
    		for (int i = 0; i < rowQty; i++) {
    			String[] row = new String[colQty];

    			for (int j = 0; j < colQty; j++) {
    				row[j] = null;
    				Cell cell = sheet.getCell(j, i);
    				String valS = null;
    				if (cell != null) {
    					valS = cell.getContents();
    				}
    				row[j] = valS;
    			}
    			sourceTable[i] = row;
    			if (i == 0)
    				continue;
    			
    			String[] rowErrors = new String[colQty];
				errors.add(rowErrors);   
    			String accountIdS = row[ACCOUNT_ID]; 
    			rowErrors[VERSION] = checkVersionNumber(row[VERSION], request);
				rowErrors[ACCOUNT_ID] = checkAccountId(request, validAccountIdList, row[ACCOUNT_ID]);
				rowErrors[MAX_ORDER_QTY] = checkMaxOrderQtyOrRestrictionDays(pForm.getShoppingCtrlLoadFieldName(MAX_ORDER_QTY), row[MAX_ORDER_QTY], request);
	    		rowErrors[RESTRICTION_DAYS] = checkMaxOrderQtyOrRestrictionDays(pForm.getShoppingCtrlLoadFieldName(RESTRICTION_DAYS), row[RESTRICTION_DAYS], request);
	    		
	    		if (Utility.isSet(rowErrors[ACCOUNT_ID]))
	    			accountError = true;
    			
    			if (Utility.isSet(accountIdS) &&!accountIdSList.contains(accountIdS))
    				accountIdSList.add(accountIdS);    			
    		}   
    		
    		if (accountIdSList.size() > 1){
    			String errorMess = ClwI18nUtil.getMessage(request,"accAdmin.loaders.multipleAccountNotSupport",null);
    			ae.add("error", new ActionError("error.simpleGenericError", errorMess));
    			return ae;
    		}
    		
    		pForm.setSourceTable(sourceTable);
    		pForm.setErrors(errors);    		
    		
    		if (accountError){
    			pForm.setDownloadErrorButton(true);
    			return ae;
    		}
    		
    		
    		int accountId = Integer.parseInt((String) accountIdSList.get(0));			            
            Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
    		IdVector sitesInAcccount = siteEjb.getAllSiteIds(accountId);
    		Map accountSkuItemIdMap = getAccountSkuItemIdMap(accountId);   
    		
    		/** get account shopping controls.
    		 * if SHOPPING_CONTROL_ID=0, it means item in account catalog but not in CLW_SHOPPING_CONTROL table.
    		 * 		the max order qty and restriction days default to 0 and show as blank
    		 * if SHOPPING_CONTROL_ID>0, it means item in account catalog also in CLW_SHOPPING_CONTROL table.
    		 * 		the max order qty and restriction days default to -1 and show as '*'
    		 * */
    		AccountData accountData = accountEjb.getAccount(accountId, 0);
    		ShoppingControlDataVector ctrl = accountData.getShoppingControls();    		
    		Map accountShoppingCtrls = new HashMap();
    		for ( int j = 0; null != ctrl && j < ctrl.size(); j++) {
    		    ShoppingControlData ctrld = (ShoppingControlData)ctrl.get(j);
    		    accountShoppingCtrls.put(ctrld.getItemId(),ctrld);
    		}
    		
    		Admin2ShoppingControlLoaderMgrLogic logic = new Admin2ShoppingControlLoaderMgrLogic();
    		UploadFieldsUtil uploadUtil = logic.new UploadFieldsUtil(accountId, sitesInAcccount, accountSkuItemIdMap, accountShoppingCtrls, colQty, request, pForm, siteEjb);
    				
    		Map<String, UploadFields> uploadFieldsMap = new HashMap<String,UploadFields>();
    		for (int i = 0; i < sourceTable.length; i++){
    			String[] row = sourceTable[i];
    			if (i > 0){    				 				
    				String[] rowErrors = (String[]) errors.get(i-1);
					UploadFields uploadField = uploadUtil.addUploadField(accountId, row[SITE_ID], row[STORE_SKU], row[MAX_ORDER_QTY], row[RESTRICTION_DAYS], rowErrors);
					String key = uploadField.siteId+":"+uploadField.sku;
					uploadFieldsMap.put(key, uploadField); 
				}
    		}    		
    		
    		// validate the account shopping control exist for a site shopping control
    		checkAccountControlExistsForSiteControls(accountEjb, uploadFieldsMap, accountShoppingCtrls);   		
    		

    		// return when errors
    		Iterator iter = uploadFieldsMap.values().iterator();
    		while (iter.hasNext()){
    			UploadFields uploadField = (UploadFields) iter.next();
	    		if (uploadField.isErrors) {
	    			pForm.setDownloadErrorButton(true);
	    			return ae;
	    		}
    		}
    		pForm.setSuccessfullyLoaded(true);
            pForm.setDownloadErrorButton(false);
            
    		ae = loadShoppingControl(pForm, request, new ArrayList(uploadFieldsMap.values()), accountId, accountShoppingCtrls);
    		return ae;
            
    	} finally {
    		file.destroy();
    	}
    }
	
	private static String checkAccountId(HttpServletRequest request, List validAccountIdList, String accountIdS) {
		if (!Utility.isSet(accountIdS)){
			return ClwI18nUtil.getMessage(request,"accAdmin.loaders.noAccountId", null);
		}
		
		int accountId = 0;
		try {
			accountId = Integer.parseInt((String) accountIdS);
		} catch (Exception e) {
			return ClwI18nUtil.getMessage(request,"accAdmin.loaders.InvalidNumberFormat",new String[]{accountIdS}) + " for account id";
		}
		
		if (!validAccountIdList.contains(accountId)){
			return ClwI18nUtil.getMessage(request,"accAdmin.loaders.wrongAccountId",null)+": " + accountId;
		}
		return null;
	}

	private static void checkAccountControlExistsForSiteControls(Account accountEjb, 
			Map<String, UploadFields> uploadFieldsMap, Map accountShoppingCtrls) throws RemoteException,
			DataNotFoundException {
		
		Iterator iter = uploadFieldsMap.values().iterator();
		while (iter.hasNext()){
			UploadFields uploadField = (UploadFields) iter.next();
			if (!uploadField.isErrors && uploadField.siteId > 0){
				String keyForAccountCtrol = 0+":"+uploadField.sku;
				UploadFields uploadFieldsForAccount = (UploadFields)uploadFieldsMap.get(keyForAccountCtrol);
				ShoppingControlData accountCtrlD = (ShoppingControlData)accountShoppingCtrls.get(uploadField.itemId);
				
				if (accountCtrlD.getShoppingControlId() == 0 && (uploadFieldsForAccount == null || (uploadFieldsForAccount.maxQty == 0 && uploadFieldsForAccount.resDays == 0))){
					uploadField.rowErrors[SITE_ID] = "Cannot add a Site Level shopping control for this item.  This item must first have an Account Level shopping control";
					uploadField.isErrors = true;
				}
			}
		}
	}

	private static Map<String, Integer> getAccountSkuItemIdMap(int accountId) throws Exception  {
    	Account acct = APIAccess.getAPIAccess().getAccountAPI();
        CatalogData cat = acct.getAccountCatalog(accountId);
        CatalogInformation catInfo = APIAccess.getAPIAccess().getCatalogInformationAPI();
        ItemDataVector itemDataDV = catInfo.getCatalogItemCollection(cat.getCatalogId());
        
        Map<String, Integer> skuItemIdMap = new HashMap<String, Integer>();
        for (int i = 0; i < itemDataDV.size(); i++){
        	ItemData itemD = (ItemData) itemDataDV.get(i);
        	if (itemD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.PRODUCT))
        		skuItemIdMap.put(itemD.getSkuNum()+"", itemD.getItemId());
        }
		return skuItemIdMap;
	}
    

	private static String checkVersionNumber(String pVersion, HttpServletRequest request) {
        if (!pVersion.equals(VERSION_NUMBER)) {
            return ClwI18nUtil.getMessage(request,"accAdmin.loaders.versionNumberError", new String[]{VERSION_NUMBER});
        } else {
            return null;
        }
    }

    private static String checkSkuInCatalog(String pSiteIdS, String sku, HttpServletRequest request, 
		Map<String, Integer> skuListInAccountCatalog, IdVector siteIds) throws Exception {
    	int siteId = Utility.isSet(pSiteIdS) ? (new Integer(pSiteIdS)).intValue() : 0;
    	Integer itemId = skuListInAccountCatalog.get(sku);
    	
    	if (itemId != null){
    		if (siteId > 0){
        		Site site = APIAccess.getAPIAccess().getSiteAPI();
        		IdVector itemIds = new IdVector();
  		      	itemIds.add(itemId);
        		Map sitesForItemsMap = site.getSitesForItems(itemIds, siteIds);
  		      	Set<Integer> siteIdList = (Set<Integer>) sitesForItemsMap.get(itemId);
  		      	boolean inCatalog = (siteIdList != null && siteIds.contains(siteId));
  		      	if (!inCatalog)
  		      		return ClwI18nUtil.getMessage(request,"accAdmin.loaders.skuNotExistInSiteCatalog", null);
    		}
    	}else{
    		return ClwI18nUtil.getMessage(request,"accAdmin.loaders.skuNotExistInAccountCatalog", null);
    	} 
    	return null;
    }
    
    private static String checkMaxOrderQtyOrRestrictionDays(String pColumnName, String pValue, HttpServletRequest request) {
        if (Utility.isSet(pValue)) {
        	try {
        		if ("*".equals(pValue))
        			return null;
                if (pValue != null && pValue.trim().length() > 0) {
                    int value = Integer.parseInt(pValue);
                    if(value < 0){
                    	return ClwI18nUtil.getMessage(request, "accAdmin.loaders.notPositiveNum", new String[] {pValue, pColumnName });
                    }
                }
            } catch (Exception e) {
            	return ClwI18nUtil.getMessage(request, "accAdmin.loaders.InvalidNumberFormat", new String[] { pColumnName });
            }
        }
        return null;
    }		

    private static ActionErrors loadShoppingControl(Admin2ShoppingControlLoaderMgrForm pForm, HttpServletRequest request, 
    		List uploadFieldsList, int accountId, Map accountShoppingCtrls) throws Exception {
        ActionErrors ae = new ActionErrors();
        APIAccess factory = APIAccess.getAPIAccess();
        Account accountEjb = factory.getAccountAPI();
        Site siteEjb = factory.getSiteAPI();
       // AddressValidator validator = factory.getAddressValidatorAPI();
        
        String[][] sourceTable = pForm.getSourceTable();
        ShoppingControlDataVector scDV =  new ShoppingControlDataVector();
        ShoppingControlDataVector siteScDV =  new ShoppingControlDataVector();
               
        
        boolean errors = false;
        String cuser = "shoppingControlLoader";
        
        // will not update the account shopping control if max qty and res days not changed.
        // site shopping control will be checking for change in Account Bean.
        for (int i = 0; i < uploadFieldsList.size(); i++) {
        	UploadFields rowFields = (UploadFields) uploadFieldsList.get(i);
        	rowFields.maxQty = getMaxQtyOrResDays(rowFields.maxQtyS);
        	rowFields.resDays = getMaxQtyOrResDays(rowFields.resDaysS);
        	ShoppingControlData scDExist = rowFields.siteSCD;
        	if (rowFields.siteId == 0){ 
        		if (scDExist.getShoppingControlId() == 0 && !Utility.isSet(rowFields.maxQtyS) && !Utility.isSet(rowFields.resDaysS)){
        			continue;
        		}        		
        	}
        	if (scDExist.getMaxOrderQty() == rowFields.maxQty && scDExist.getRestrictionDays() == rowFields.resDays)
        		continue;// no update
        	
	        if (rowFields.siteId > 0){
	        	siteScDV.add(scDExist);
	        }else{
	        	scDV.add(scDExist);
	        } 	        
	        
	        scDExist.setControlStatusCd(RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
	        scDExist.setMaxOrderQty(rowFields.maxQty);
	        scDExist.setRestrictionDays(rowFields.resDays);
	        scDExist.setHistoryOrderQty(-1);
	        scDExist.setActualMaxQty(-1);
	        scDExist.setExpDate(null);
	        if (scDExist.getShoppingControlId() == 0)
	        	scDExist.setAddBy(cuser);
	        scDExist.setModBy(cuser);
        }
        
        if (scDV.size() > 0  || siteScDV.size() > 0)
        	accountEjb.updateShoppingControls(scDV, siteScDV);
        
        return ae;
    }
    private static int getMaxQtyOrResDays(String qtyS) {
		if (Utility.isSet(qtyS)){
			if ("*".equals(qtyS))
				return -1;
			else
				return Integer.parseInt(qtyS);
		}
		return -1;
	}	

    public static ActionErrors downloadErrors(HttpServletRequest request, HttpServletResponse response ,
                                        ActionForm form) throws Exception  {
        ActionErrors ae = new ActionErrors();
        Admin2ShoppingControlLoaderMgrForm pForm = (Admin2ShoppingControlLoaderMgrForm) form;
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

    public static ActionErrors exportShoppingControls(HttpServletRequest request, HttpServletResponse response,
    		ActionForm form) throws Exception  {
    	ActionErrors ae = new ActionErrors();
    	Admin2ShoppingControlLoaderMgrForm pForm = (Admin2ShoppingControlLoaderMgrForm) form;
    	String fileName = "shoppingControlOfAccount.xls";
    	GenericReportResultViewVector excel = new GenericReportResultViewVector();        

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
    	Account acct = factory.getAccountAPI();
    	Site site = factory.getSiteAPI();
    	ShoppingServices ssvc = factory.getShoppingServicesAPI();
    	AccountData acctData = acct.getAccount(accountId, 0);
    	ShoppingControlDataVector acctCtrlDV = acctData.getShoppingControls();
    	CatalogData cat = acct.getAccountCatalog(accountId);
    	ProductDataVector productDV = factory.getProductInformationAPI().getProductsCollectionByCatalog(cat.getCatalogId());
    	if (null != acctCtrlDV) {
    		ShoppingControlItemViewVector controlItemViewDV = new ShoppingControlItemViewVector();
    		Map productMap = new HashMap();
    		for (int i = 0; i < productDV.size(); i++){
    			ProductData productD = (ProductData) productDV.get(i);
    			productMap.put(productD.getItemData().getItemId(), productD);
    		}

    		for (int i = 0; i < acctCtrlDV.size(); i++){
    			ShoppingControlData scd = (ShoppingControlData) acctCtrlDV.get(i);
    			ShoppingControlItemView controlView = ShoppingControlItemView.createValue();
    			ProductData pd = (ProductData) productMap.get(scd.getItemId());
    			controlView.setItemId(scd.getItemId());
    			controlView.setSkuNum(String.valueOf(pd.getSkuNum()));
    			controlView.setShortDesc(pd.getShortDesc());
    			controlView.setUOM(pd.getUom());
    			controlView.setSize(pd.getSize());
    			controlView.setPack(pd.getPack());  
    			controlView.setShoppingControlData(scd);
    			controlItemViewDV.add(controlView);
    		}

    		//get ALL Site Controls for this Account      
    		IdVector sites = site.getAllSiteIds(accountId);

    		Map<Integer, String> siteNameByIdMap = new HashMap<Integer,String>();
    		log.info("lookupShoppingControls(): sites.size()=" + sites.size());
    		for(int i=0; i<sites.size(); i++){
    			int siteId = ((Integer)sites.get(i)).intValue();
    			siteNameByIdMap.put((Integer)sites.get(i), site.getSiteNameById(siteId));
    		}

    		excel.add(generateExcelRows(controlItemViewDV, siteNameByIdMap, request, pForm,accountId, acctData.getBusEntity().getShortDesc()));
    	}


    	printExcelTable(fileName, excel, request, response);
    	return ae;
    }

    private static GenericReportResultView generateExcelRows(ShoppingControlItemViewVector shoppingControls,
    		Map<Integer, String> siteNameMap,
            HttpServletRequest request,
            Admin2ShoppingControlLoaderMgrForm pForm, int pAccountId, String pAccountName) throws Exception {
        GenericReportResultView xlog = GenericReportResultView.createValue();
        APIAccess factory = new APIAccess();
        Site site = factory.getSiteAPI();
        try {
            ArrayList  restable = new ArrayList();
            xlog.setTable(restable);
            Iterator iter = shoppingControls.iterator();
            while (iter.hasNext()) {
            	ShoppingControlItemView itemControlView = (ShoppingControlItemView)iter.next();
                setShoppingControlRowData(pAccountId, pAccountName, "", "", itemControlView, itemControlView.getShoppingControlData(), restable);
                ShoppingControlDataVector siteControlsByItemId = site.lookupSiteShoppingControlsByItemId(pAccountId, itemControlView.getItemId());
                if (siteControlsByItemId != null){
                	for (int i = 0; i < siteControlsByItemId.size(); i++){
                    	ShoppingControlData scd = (ShoppingControlData) siteControlsByItemId.get(i);
                    	int siteId = scd.getSiteId();
                    	setShoppingControlRowData(pAccountId, pAccountName, siteId+"", siteNameMap.get(siteId), itemControlView, scd, restable);
                    }
                }                
            }
            xlog.setTable(restable);
            generateHeader(xlog, pForm, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xlog;

    }

	private static ArrayList setShoppingControlRowData(int accountId, String accountName, 
			String siteId, String siteName, ShoppingControlItemView itemControlView, ShoppingControlData scd, ArrayList  restable) {
		ArrayList r;
		r = new ArrayList();
		r.add(VERSION_NUMBER);	//VERSION
		r.add(accountId);     	//ACCOUNT_ID
		r.add(accountName);   	//ACCOUNT_NAME
		r.add(siteId);          //SITE_ID
		r.add(siteName);        //SITE_NAME
		r.add(itemControlView.getSkuNum());				//STORE_SKU
		r.add(itemControlView.getShortDesc());			//DESCRIPTION
		r.add(itemControlView.getSize());				//ITEM_SIZE
		r.add(itemControlView.getUOM());				//UOM
		r.add(itemControlView.getPack());				//PACK		
		
		int maxqty = scd.getMaxOrderQty();
		int restDays = scd.getRestrictionDays();
		String maxqtyS = (maxqty < 0) ? "*" : (maxqty > 0)? String.valueOf(maxqty) : (scd.getShoppingControlId() > 0) ? "0" : "";
		String restDaysS = (restDays < 0) ? "*" : (restDays > 0) ? String.valueOf(restDays) : (scd.getShoppingControlId() > 0) ? "0" : "";
		r.add(maxqtyS);		//MAX_ORDER_QTY
		r.add(restDaysS);	//RESTRICTION_DAYS
		restable.add(r);
		return r;
	}
    
    private static GenericReportResultView
        generateHeader(GenericReportResultView xlog, Admin2ShoppingControlLoaderMgrForm pForm, boolean addErrorColumn)
            throws Exception
    {
        try {
            GenericReportColumnViewVector header = new GenericReportColumnViewVector();
            String[] headers = pForm.getShoppingCtrlLoadFields();
            for (int h=0; h<headers.length; h++) {
                header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",headers[h],0,255,"VARCHAR2"));
            }
            if (addErrorColumn) {
                header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Error",0,255,"VARCHAR2"));
            }
            xlog.setHeader(header);
            xlog.setColumnCount(header.size());
            xlog.setName("Shopping Control");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return xlog;
    }




    private static
        GenericReportResultView generateExcelRows(HttpServletRequest request, Admin2ShoppingControlLoaderMgrForm pForm)
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
    
    class UploadFieldsUtil {
    	int accountId;
    	IdVector sitesInAcccount;
    	Map accountSkuItemIdMap;
    	Map accountShoppingCtrls;
    	int colQty;
    	HttpServletRequest request;
    	Admin2ShoppingControlLoaderMgrForm form;
    	Site siteBean;
    	
    	UploadFieldsUtil(int accountId, IdVector sitesInAcccount, Map accountSkuItemIdMap, Map accountShoppingCtrls, int colQty, 
    			HttpServletRequest request, Admin2ShoppingControlLoaderMgrForm form, Site siteBean){
    		this.accountId = accountId;
    		this.sitesInAcccount = sitesInAcccount;
    		this.accountSkuItemIdMap = accountSkuItemIdMap;
    		this.accountShoppingCtrls = accountShoppingCtrls;
    		this.colQty = colQty;
    		this.request = request;
    		this.form = form;
    		this.siteBean = siteBean;
    	}
    	public UploadFields addUploadField(int accountId, String siteIdS, String sku, String maxQtyS, String resDaysS, String[] rowErrors) throws Exception{
    		return new UploadFields(accountId, siteIdS, sku, maxQtyS, resDaysS, rowErrors);
    	}    	
    	
		public class UploadFields {
	    	int siteId = 0;
	    	String sku = null;
	    	String maxQtyS = null;
	    	String resDaysS = null;
	    	int maxQty = 0;
	    	int resDays = 0;
	    	int itemId = 0;
	    	ShoppingControlData siteSCD = null;
	    	String[] rowErrors;
	    	boolean isErrors;
	    	UploadFields(int accountId, String siteIdS, String sku, String maxQtyS, String resDaysS, String[] rowErrors) throws Exception{
	    		this.rowErrors = rowErrors;
	    		rowErrors[SITE_ID] = checkSiteId(siteIdS);
	    		if (Utility.isSet(siteIdS) && rowErrors[SITE_ID] == null)
	    			siteId = Integer.parseInt(siteIdS);
	    		
	    		rowErrors[STORE_SKU] = checkStoreSku(sku, siteId, Utility.isSet(rowErrors[SITE_ID]));
	    		
	    		isErrors = Utility.isSet(rowErrors[VERSION]) || Utility.isSet(rowErrors[ACCOUNT_ID]) || Utility.isSet(rowErrors[SITE_ID]) 
	    			|| Utility.isSet(rowErrors[STORE_SKU]) || Utility.isSet(rowErrors[MAX_ORDER_QTY]) || Utility.isSet(rowErrors[RESTRICTION_DAYS]);
	    		if (!isErrors){
	    			this.sku = sku;	
	    			this.maxQtyS = maxQtyS;
	    			this.resDaysS = resDaysS;
	    			itemId = (Integer) accountSkuItemIdMap.get(sku);
	    		}  			
	    	}
	    		    	
	    	private String checkSiteId(String siteIdS) {
	    		if (Utility.isSet(siteIdS)){
	    			try {
	    				int siteId = Integer.parseInt(siteIdS);
	    				if (!sitesInAcccount.contains(siteId)){
	    					return ClwI18nUtil.getMessage(request,"accAdmin.loaders.siteIdNotValidForAccount", null);
	    				}
	    		    } catch (Exception e) {
	    		    	return ClwI18nUtil.getMessage(request, "accAdmin.loaders.InvalidNumberFormat", 
	    		    				new String[] { form.getShoppingCtrlLoadFieldName(SITE_ID)});
	    		    }
	    		}
	    		return null;
	    	}
	    	
	    	private String checkStoreSku(String sku, int siteId, boolean siteIdError) 
	    	throws Exception {
	    		if (!Utility.isSet(sku)){
	    			return "No " + Admin2ShoppingControlLoaderMgrForm.STORE_SKU;
	    		} else if (!accountSkuItemIdMap.containsKey(sku)){
	    			return ClwI18nUtil.getMessage(request,"accAdmin.loaders.skuNotExistInAccountCatalog", null);
	    		}else {
	    			int itemId = (Integer) accountSkuItemIdMap.get(sku);
	    			if (siteId == 0){
	    				siteSCD = (ShoppingControlData)accountShoppingCtrls.get(itemId);
	    			}else if (siteId > 0 && !siteIdError){
	    				IdVector itemIds = new IdVector();
	    			    itemIds.add(itemId);
	    				Map sitesForItemsMap = siteBean.getSitesForItems(itemIds, sitesInAcccount);
    			      	Set siteIdList = (Set) sitesForItemsMap.get(itemId);
    			      	boolean inCatalog = (siteIdList != null && siteIdList.contains(siteId));
    			      	if (!inCatalog){
    			      		return ClwI18nUtil.getMessage(request,"accAdmin.loaders.skuNotExistInSiteCatalog", null);
    			      	}else{// check site shopping control exists
    			      		IdVector siteIds = new IdVector();
    			      		siteIds.add(siteId);
    			      		ShoppingControlDataVector scDV = siteBean.getSiteShoppingControlsForItem(siteIds, itemId);
    			      		if (scDV == null || scDV.size() == 0){
    			      			return "Cannot create Site Shopping Control. To create site shopping control use the Shopping Controls feature in the Buyer's Portal.  " +
    			      					"This loader only performs UPDATES to an existing Site Shopping Control";
    			      		}else{
    			      			siteSCD =((ShoppingControlData)scDV.get(0));
    			      		}
    			      	}
	    			}
	    		}
	    		return null;
	    	}	    		    	
    	}
    }    
}
