package com.cleanwise.service.api.reporting;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.ShoppingDAO;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CurrencyData;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.SiteData;

public class JanitorsClosetReport  implements GenericReport {
	

	public GenericReportResultView process(com.cleanwise.service.api.util.ConnectionContainer pCons, com.cleanwise.service.api.value.GenericReportData pReportData, java.util.Map pParams) throws Exception {
		Connection con = pCons.getDefaultConnection();
		String userS = (String)ReportingUtils.getParam(pParams,"CUSTOMER");
		String storeS = (String)ReportingUtils.getParam(pParams,"STORE");
		String siteS = (String)ReportingUtils.getParam(pParams,"SITE");
		
		int user=0;
		int site=0;
		int store=0;
		try{
			user = Integer.parseInt(userS);
		}catch(Exception e){
			throw new Exception("^clw^CUSTOMER requires an integer value^clw^");
		}
		try{
			site = Integer.parseInt(siteS);
		}catch(Exception e){
			throw new Exception("^clw^SITE requires an integer value^clw^");
		}
		
		if(!Utility.isSet(storeS)){
			store = BusEntityDAO.getStoreForAccount(con,BusEntityDAO.getAccountForSite(con,site));
			if(store == 0){
				throw new Exception("^clw^STORE could not be determined^clw^");
			}
		}
		
		try{
			if(store == 0){
				store = Integer.parseInt(storeS);
			}
		}catch(Exception e){
			throw new Exception("^clw^STORE requires an integer value^clw^");
		}
		if(!ReportingUtils.isUserAuthorizedForSite(con,site,user)){
			return null;
		}
		//no need to validate store, as it is only used for some UI layouts
		//if someone wants to forge that it is not a security hazard
		
		PropertyUtil pru = new PropertyUtil(con);
		String storeType = pru.fetchValue(0,store,RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
		
		SiteData siteData = BusEntityDAO.getSiteData(con,site);
		UserRightsTool urt = new UserRightsTool(UserDataAccess.select(con,user));
		
		ShoppingCartItemDataVector items =ShoppingDAO.
            getJanitorCloset(storeType,siteData.getContractData().getCatalogId(),siteData.getContractData().getContractId(),
                    urt.isUserOnContract(),0,site,con);
		String currency = "";
		CurrencyData currD = I18nUtil.getCurrency(siteData.getContractData().getLocaleCd()); 
          if(currD!=null) {
            currency = "rowInfo_currencyCd=" + currD.getGlobalCode();
          }
		
		ArrayList tableData = new ArrayList();
		
		Iterator it = items.iterator();
		int columnCount = 1;
		while(it.hasNext()){
			ShoppingCartItemData item = (ShoppingCartItemData) it.next();
			ArrayList aRow = new ArrayList();
			if(item.getContractFlag()){
				aRow.add(new Integer(item.getShoppingHistory().getLastQty()));
			}else{
				aRow.add("Discontinued");
			}
			aRow.add(item.getActualSkuNum());
			aRow.add(item.getProduct().getCatalogProductShortDesc());
			aRow.add(item.getCategoryName());
			aRow.add(item.getProduct().getSize());
			aRow.add(item.getProduct().getManufacturerSku());
			aRow.add(item.getProduct().getManufacturerName());
			
			
			if(urt.getShowPrice()){
				aRow.add(new BigDecimal(item.getPrice()));
			}
			aRow.add(new BigDecimal(item.getShoppingHistory().getLastAmt()));
			aRow.add(item.getShoppingHistory().getLastDate());
			aRow.add(new Integer(item.getShoppingHistory().getY2dQty()));
			aRow.add(new BigDecimal(item.getShoppingHistory().getY2dAmt()));
			aRow.add(currency);
			tableData.add(aRow);
			if(columnCount == 1){
				columnCount = aRow.size();
			}
		}
		
		GenericReportResultView toReturn = GenericReportResultView.createValue();
		toReturn.setTable(tableData);
		toReturn.setHeader(getReportHeader());
		toReturn.setColumnCount(columnCount);
		toReturn.setName(ReportingUtils.DEFAULT_REPORT_NAME);
		return toReturn;
	}
	
	
	/**
     *Generates the detail header for this reports
     */
    private GenericReportColumnViewVector getReportHeader(){
        ArrayList header = new ArrayList();
        header.add(new SimpleHeaderDef("Last Order Qty","Integer"));
        header.add(new SimpleHeaderDef("Sku","String"));
        header.add(new SimpleHeaderDef("Item Desc","String"));
        header.add(new SimpleHeaderDef("Category","String"));
        header.add(new SimpleHeaderDef("Item_Size","String"));
        header.add(new SimpleHeaderDef("Manu Sku","String"));
        header.add(new SimpleHeaderDef("Manufacturer","String"));
        header.add(new SimpleHeaderDef("Unit Price_money","BigDecimal")); //$?
        header.add(new SimpleHeaderDef("Last Amount_money","BigDecimal")); //$?
        header.add(new SimpleHeaderDef("Last Order","Date"));
        header.add(new SimpleHeaderDef("Years Quantity","Integer"));
        header.add(new SimpleHeaderDef("Years Amount_money","BigDecimal")); //$?
        header.add(new SimpleHeaderDef("rowInfo_Currency Code","String"));
        return ReportingUtils.createGenericReportColumnView(header);
    }
	
	
}
