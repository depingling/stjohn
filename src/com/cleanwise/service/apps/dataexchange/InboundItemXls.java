package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class InboundItemXls extends InboundFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
    public static String DATE_FORMAT = "MM/dd/yy";

    private CatalogInformation catalogInfoEjb;
    private Catalog catalogEjb;
    private PropertyService property;
    // Document's header processing params
    private int lineNumber = 0;
    private String agentId; // Uniq site identifier
    private boolean toProcess = false;
    /**
     *Overides to look for a set of text to ignore
     */
    public void parseLine(List pParsedLine) throws Exception{
      // Document's header processing
      lineNumber ++;
      if(lineNumber == 6){ // assume it's site agentId
        agentId = (String)pParsedLine.get(0);
        int pos = agentId.indexOf(" ");
        if(pos > 0)
          agentId = agentId.substring(pos);
          log.info("Agent_id  = " + agentId);
          return;
      }else if(lineNumber > 8){
        toProcess = true; // Item data are ready
      }else
        return; // If header should returns

      HashMap columnMap = getColumMap();
      if(pParsedLine == null)
        return;
      //Verifing that minimum number of mandatory values are present
      int parsedSize = pParsedLine.size();
      for (int i = 0; i < columnMap.size(); i++){
        TradingPropertyMapData map = (TradingPropertyMapData) columnMap.get(new Integer(i));
        if(map != null && Utility.isTrue(map.getMandatory())){
          if(parsedSize < i)
            return;
          Object value = pParsedLine.get(i);
          if(value == null)
                throw new IllegalArgumentException("Error parsing column: "+ i + " it is mandority property");
        }
      }
      super.parseLine(pParsedLine);
    }


    /**
     *Intercept the requests as we will controll what gets added
     */
    protected void processParsedObject(Object pParsedObject) throws Exception{
      if(pParsedObject == null)
        throw new IllegalArgumentException("No parsed site object present");

      if(!toProcess){
        return;
      }
      int accountId = -1;

      if (pParsedObject.getClass().getName() == ItemXlsData.class.getName()){
        ItemXlsData xlsData = (ItemXlsData)pParsedObject;


        if(!Utility.isTrue(xlsData.getChanged())){
          log.info("Item data was not changed... Row number = " + lineNumber);
          return;
        }
        /*Searching account ID with CUST_MAJ and Trading Partner associated
        Getting trading partner id*/
        TradingPartnerData partner = translator.getPartner();
        if(partner == null)
          throw new IllegalArgumentException("Trading Partner ID cann't be determined");

        int tradingPartnerId = partner.getTradingPartnerId();
        boolean found = false;
        String custMaj = xlsData.getCust(); //parameter identify account in CLW_PROPERTY
        if(custMaj != null){
          custMaj = custMaj.trim();
          int pos = custMaj.indexOf(".");
          if (pos > 0)
            custMaj = custMaj.substring(0, pos);
        } else {
          return;
        }
        try{
          property = translator.getPropertyServiceEjb();
          //Getting all accounts by cust_maj property (CLW_PROPERTY)
          ArrayList accountByCustMaj = null;
          try {
            accountByCustMaj = property.getBusEntityVector(RefCodeNames.
                PROPERTY_TYPE_CD.CUST_MAJ, custMaj);
          }
          catch (DataNotFoundException dex) {
            log.info("Account  not found for CUST_MAJ = " + custMaj);
          }
          catch (RemoteException rex) {
            log.info(
                "Remote Exception occured during getting account ids by CUST_MAJ");
          }
          if (accountByCustMaj == null || accountByCustMaj.size() < 1)
            throw new IllegalArgumentException("No accounts found with CUST_MAJ = " +
                                               custMaj);
          int[] accountIds;
          if (partner.getTradingPartnerTypeCd().equals("ACCOUNT")) {
            //Getting all accounts associated with trading partner, if it has type ACCOUNT
            accountIds = translator.getTradingPartnerBusEntityIds(tradingPartnerId,
                "ACCOUNT");
            if (accountIds == null || accountIds.length < 1)
              throw new IllegalArgumentException(
                  "No uniq. accound id present for current trading partner id = " +
                  tradingPartnerId);
          }
          else if (partner.getTradingPartnerTypeCd().equals("STORE")) {
            //Getting all accounts from CLW_BUS_ENTITY_ASSOC if trading partner has type STORE
            accountIds = translator.getTradingPartnerBusEntityIds(tradingPartnerId,
                "STORE");
            if (accountIds == null || accountIds.length != 1)
              throw new IllegalArgumentException(
                  "Trading partner with type STORE should be associated with one store");

            log.info("STORE id= " + accountIds[0] + " Trading Partner id= " + tradingPartnerId);            
            Account mAccBean = translator.getAccountBean();           
            ArrayList accounts = mAccBean.getAccountsForStore(accountIds[0]);
            if (accounts == null || accounts.size() < 1)
              throw new IllegalArgumentException("No account found for store id=" +
                                                 accountIds[0]);
            accountIds = new int[accounts.size()];
            for (int j = 0; j < accounts.size(); j++) {
              accountIds[j] = ((Integer)accounts.get(j)).intValue();
            }
          }
          else {
            throw new IllegalArgumentException(
                "Trading partner should has type of ACCOUNT or STORE");
          }

          for (int i = 0; i < accountByCustMaj.size(); i++) {
            for (int j = 0; j < accountIds.length; j++) {
              if (((Integer) accountByCustMaj.get(i)).intValue() == accountIds[j]) {
                accountId = accountIds[j];
                log.info("Found account id = " + accountId);
                found = true;
                break;
              }
            }
          }

          if (!found)
            throw new IllegalArgumentException(" Account ID with CUST MAJ=" + custMaj
                                               + "  not found for trading partner id=" +
                                               tradingPartnerId);

          processItemRecord(xlsData, accountId);
        }
        catch (DataNotFoundException e) {
        	log.error(e.getMessage(),e);
        }catch(APIServiceAccessException sex){
        	log.error(sex.getMessage(),sex);
        }catch(RemoteException rex){
        	log.error(rex.getMessage(),rex);
        }
      } else {
        log.info("Wrong type of object we are wait for parsing");
      }
    }

    public void processItemRecord(ItemXlsData beanToSave, int accountId)
        throws Exception {

      ProductData productD;
      ItemData itemD;

      Manufacturer manufactureEjb =  APIAccess.getAPIAccess().getManufacturerAPI();
      catalogInfoEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
      catalogEjb = APIAccess.getAPIAccess().getCatalogAPI();

      productD = new ProductData();
      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
      Date runDate = new Date();
      try {
          runDate = sdf.parse(sdf.format(new Date()));
      } catch(ParseException e) {
          log.info("Error while parsing the date. " + e.toString());
      }
      String cuser = "itemLoader";

      //Searching of STORE for specified ACCOUNT
      Store storeEjb = APIAccess.getAPIAccess().getStoreAPI();
      int storeId = storeEjb.getStoreIdByAccount(accountId);      
      
      itemD = productD.getItemData();
      itemD.setAddBy(cuser);
      itemD.setModBy(cuser);
      itemD.setAddDate(runDate);
      itemD.setModDate(runDate);
      itemD.setEffDate(runDate);
      itemD.setShortDesc(beanToSave.getDescription().trim());
      itemD.setLongDesc(beanToSave.getDescription().trim());
      itemD.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
      itemD.setItemTypeCd("PRODUCT");
      
      try {
          //Verify that manufacturer is valid for current STORE
          String manufacturerName = beanToSave.getSupplierName();
          if(manufacturerName == null)
            throw new DataNotFoundException("No manufacturer found while saving item.");

          manufacturerName = manufacturerName.trim();
          BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
          IdVector storeIds = new IdVector();
          storeIds.add(new Integer(storeId));
          crit.setStoreBusEntityIds(storeIds);
          crit.setSearchNameType(EntitySearchCriteria.EXACT_MATCH);
          crit.setSearchName(manufacturerName);

          ManufacturerDataVector manufacturerDV = manufactureEjb.getManufacturerByCriteria(crit);
          ManufacturerData manufacturerD = null;
          if (manufacturerDV.size() > 0) {
              manufacturerD = (ManufacturerData) manufacturerDV.get(0);
          }
          if (manufacturerD == null) {
              throw new DataNotFoundException("No manufacturer found while saving item.");
          }
          productD.setManufacturer(manufacturerD.getBusEntity());
          String mfgNumber = beanToSave.getMfgNumber().trim();
          int pos;
          if(mfgNumber != null){
            mfgNumber = mfgNumber.trim();
            pos = mfgNumber.indexOf(".");
            if(pos > 0) {
              mfgNumber = mfgNumber.substring(0, pos);
            }
          }
          productD.setManuMapping(manufacturerD.getBusEntity(), mfgNumber);         

          int shoppingCatalogId = getSiteCatalog(accountId);
          int distributorId = getDistributorId(shoppingCatalogId);                             

          String casePack = beanToSave.getCasePack().trim();
          pos = casePack.indexOf(".");
          if(pos > 0)
            casePack = casePack.substring(0, pos);
          productD.setDistributorPack(distributorId, casePack);
          productD.setDistributorUom(distributorId, beanToSave.getUom().trim());

          String NcsNumber = beanToSave.getNscNumber();
          if(NcsNumber == null || NcsNumber.trim().length() < 1)
            throw new IllegalArgumentException("Ncs number was not defined");
          pos = NcsNumber.indexOf(".");
          if(pos > 0)
            NcsNumber = NcsNumber.substring(0, pos);           
          productD.setDistributorSku(distributorId, NcsNumber);
          productD.setEffDate(runDate);

          String custPartNumber = beanToSave.getCustomerPartNumber();
          if(custPartNumber != null && custPartNumber.trim().length() > 0){
            pos = custPartNumber.indexOf(".");
            if (pos > 0)
              custPartNumber = custPartNumber.substring(0, pos);
            productD.setCustomerSkuNum(custPartNumber);
          }
          productD.setPack(casePack.trim());
          productD.setUom(beanToSave.getUom().trim());
          
          //Due to excel cell format it doesn't account leading zeroes
          String upc = new Float(beanToSave.getUpc().trim()).toString();
          productD.setUpc(upc);//Item property UPC_NUM (clw_item_meta). Uniquely identifies the item.
          
          double price = new Double(beanToSave.getSellingPrice()).doubleValue();
          productD.setListPrice(price);
          productD.setCostPrice(price);
          productD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);                                               

          //Find STORE catalog and verify categories
          CatalogDataVector catalogDV = catalogInfoEjb.getCatalogsCollectionByBusEntity(storeId,
                                              RefCodeNames.CATALOG_TYPE_CD.STORE);
          if(catalogDV == null || catalogDV.size()==0){
            return;
          }
          CatalogData catalogData = null;
          for (Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
              catalogData = (CatalogData) iter.next();
              if (RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(catalogData.getCatalogStatusCd())) {
                  break;
              }
          }
          if (catalogData == null) {
              log.info("No store catalog found for storeId: " + storeId);
              return;
          }
          int storeCatalogId = catalogData.getCatalogId();          
          String categoryName = beanToSave.getGeneralDescription().trim();
          CatalogCategoryDataVector categoryDV = getCatalogCategories(storeCatalogId, categoryName, cuser);
          productD.setCatalogCategories(categoryDV);
          productD.setCatalogStructure(storeCatalogId);
          productD = catalogEjb.saveStoreCatalogProduct(storeId, storeCatalogId, productD, cuser);

          // Find ACCOUNT catalog and verify categories
          Account accEjb = translator.getAccountBean();
          CatalogData accCatalog = accEjb.getAccountCatalog(accountId);
          if(accCatalog != null){
            int accountCatalogId = accCatalog.getCatalogId();            
            productD.setCatalogStructure(accountCatalogId);
            catalogEjb.saveCatalogProduct(accountCatalogId, productD, cuser);
          }

          // Find SHOPPING catalog and verify categories
          if(shoppingCatalogId > 0){            
            productD.setCatalogStructure(shoppingCatalogId);             
            catalogEjb.saveCatalogProduct(shoppingCatalogId, productD, cuser);
          }
      } catch (RemoteException e) {
          log.info(e.toString());
      } catch (DataNotFoundException e) {
          log.info(e.toString());
      }
    }

    /*
    *Method returns catalog's categories for store catalog
    *and add category if it doesn't exist
    */
    private CatalogCategoryDataVector getCatalogCategories(int catalogId, String categoryName, String cuser)
                                                                                     throws RemoteException{
      CatalogCategoryDataVector categoryDV = catalogInfoEjb.getStoreCatalogCategories(catalogId);
      if (categoryDV.size() > 1) {
        Object[] categoryA = categoryDV.toArray();
        for (int ii = 0; ii < categoryA.length - 1; ii++) {
          boolean exitFl = true;
          for (int jj = 0; jj < categoryA.length - ii - 1; jj++) {
            CatalogCategoryData ccD1 = (CatalogCategoryData) categoryA[jj];
            CatalogCategoryData ccD2 = (CatalogCategoryData) categoryA[jj + 1];
            ItemData iD1 = ccD1.getItemData();
            ItemData iD2 = ccD2.getItemData();
            if (iD1.getShortDesc().compareToIgnoreCase(iD2.getShortDesc()) > 0) {
                categoryA[jj] = ccD2;
                categoryA[jj + 1] = ccD1;
                exitFl = false;
            }
          }
          if (exitFl) break;
        }
        categoryDV = new CatalogCategoryDataVector();
        for (int ii = 0; ii < categoryA.length; ii++) {
          CatalogCategoryData ccD1 = (CatalogCategoryData) categoryA[ii];
          categoryDV.add(ccD1);
        }
      }
      int size = categoryDV.size();
      boolean found = false;
      CatalogCategoryData category = null;
      for (int i = 0; i < size; i++) {
          category = (CatalogCategoryData) categoryDV.get(i);
          ItemData currentItem = category.getItemData();
          String categoryShortDesc = currentItem.getShortDesc();
          String categoryLongDesc = currentItem.getLongDesc();
          if ((categoryShortDesc != null && categoryShortDesc.equalsIgnoreCase(categoryName)) ||
               (categoryLongDesc != null && categoryLongDesc.equalsIgnoreCase(categoryName))) {
            found = true;
            break;
          }
      }
      if (!found) { // add Category
        category = catalogEjb.saveStoreCategory(catalogId,
                                                      0,
                                                      categoryName,
                                                      0,
                                                      cuser);
      }
      categoryDV = new CatalogCategoryDataVector();
      categoryDV.add(category);
      return categoryDV;
    }

    /*
     *The function returns true if SHOPPING catalog exist with specified site Agent_Id
     */
     private int getSiteCatalog(int accountId)throws Exception{

       if(agentId == null || agentId.trim().length() == 0)
         return -1;
       //Find all sites with specified Agent_Id
       try{
         ArrayList siteIds = property.getBusEntityVector(RefCodeNames.
             PROPERTY_TYPE_CD.AGENT_ID, agentId);

        Site siteEjb = null;
        siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        IdVector siteIdV = siteEjb.getSiteIdsOnlyForAccount(accountId);
        Iterator siteIt = siteIds.iterator();
        Iterator accSiteIt = siteIdV.iterator();
        int siteId = -1;
        while(siteIt.hasNext()){
          int site = ((Integer)siteIt.next()).intValue();
          while(accSiteIt.hasNext()){
            int accSite = ((Integer)accSiteIt.next()).intValue();
            if(site == accSite){
              siteId = site;
              break;
            }
          }
          if(siteId > 0) {
            CatalogData catalog = catalogInfoEjb.getSiteCatalog(siteId);
            return catalog.getCatalogId();
          }
        }
       }catch(APIServiceAccessException sex){
         sex.printStackTrace();
       }catch(DataNotFoundException dex){
         dex.printStackTrace();
       }

       return -1;
     }

     private int getDistributorId(int shoppingCatalog)throws RemoteException{
       //For network distributors every shopping catalog has only one distributor configured
       CatalogAssocDataVector catalogAssocDV = catalogInfoEjb.
           getCatalogAssoc(shoppingCatalog, 0, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);
       if(catalogAssocDV != null && catalogAssocDV.size() > 0){
         CatalogAssocData catalogData = (CatalogAssocData)catalogAssocDV.get(0);
         return catalogData.getBusEntityId();
       }else
         return -1;
     }
}
