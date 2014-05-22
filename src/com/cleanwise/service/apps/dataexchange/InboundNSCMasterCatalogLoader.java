/**
 * 
 */
package com.cleanwise.service.apps.dataexchange;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.*;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;

import com.cleanwise.service.apps.loaders.TabFileParser;

import java.rmi.RemoteException;
import javax.naming.NamingException;
import java.io.InputStream;
import java.util.*;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

/**
 * @author ssharma
 *
 */
public class InboundNSCMasterCatalogLoader extends InboundFlatFile {

	private static final Logger log = Logger.getLogger(InboundNSCMasterCatalogLoader.class);
    private Account accountEjb;
    private CatalogInformation catalogInfoEjb;
    private Catalog catalogEjb;
    private Distributor distrEjb;
    private Manufacturer manufacturerEjb;    
    
    private String mUser = "MasterCatalogLoader";
    private ArrayList items = new ArrayList();
    private int storeId = -1;
    private int distributorId = -1;
    private int storeCatalogId = -1;
    private HashMap<String, BusEntityData> manufBeHM  = new HashMap<String, BusEntityData>();
    private HashMap<String, CatalogData> catalogHM = new HashMap<String, CatalogData>();
    private HashMap<String,CatalogCategoryData> categoryHM = new HashMap<String,CatalogCategoryData>();
    private HashMap<String,Integer> acctIdByCompany = new HashMap<String,Integer>();
    private HashMap<Integer,ProductDataVector> productsByAcct = new HashMap<Integer,ProductDataVector>();
    
    HashSet categoryHS = new HashSet();
    HashSet missingCompanyHS = new HashSet();
    HashSet missingManufNameHS = new HashSet();
    HashSet emptyManufNameHS = new HashSet();
    HashSet missingCategoryHS = new HashSet();
    HashSet missingUomHS = new HashSet();
    HashSet missingLongDescHS = new HashSet();
    HashSet missingShortDesc1HS = new HashSet();
    HashSet missingPriceHS = new HashSet();
    HashSet missingManufSkuHS = new HashSet();
    HashSet missingDistSkuHS = new HashSet();
    HashSet missingPackHS = new HashSet();
    HashSet missingUpcCodeHS = new HashSet();
    HashSet fuelSurchargeHS = new HashSet();
    
    public void doPreProcessing() throws Exception {
        
    	accountEjb = APIAccess.getAPIAccess().getAccountAPI();
        catalogInfoEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
        catalogEjb = APIAccess.getAPIAccess().getCatalogAPI();
        distrEjb = APIAccess.getAPIAccess().getDistributorAPI();
        manufacturerEjb = APIAccess.getAPIAccess().getManufacturerAPI();            

        
        TradingPartnerData partner = translator.getPartner();
        if (partner == null) {
            throw new IllegalArgumentException("Trading Partner ID cann't be determined");
        }
        //Find storeId
        int tradingPartnerId = partner.getTradingPartnerId();
        log.info("Found Trading Partner id = " + tradingPartnerId);
        if (partner.getTradingPartnerTypeCd().equals(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE)) {
            int[] storeIds;
            storeIds = translator.getTradingPartnerBusEntityIds(tradingPartnerId,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
            if (storeIds == null || storeIds.length == 0) {
                throw new IllegalArgumentException("Trading partner with type STORE is not associated with any store. " +
                        "Trading parther id: " + tradingPartnerId);
            } else if (storeIds.length > 1) {
                throw new IllegalArgumentException("Trading partner with type STORE associated with multiple stores. " +
                        "Trading parther id: " + tradingPartnerId);
            }

            storeId = storeIds[0];
            log.info("Inbount Item Txt STORE  id= " + storeId);
                        
        } else if (partner.getTradingPartnerTypeCd().equals(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT)) {
            storeId = APIAccess.getAPIAccess().getStoreAPI().getStoreIdByAccount(tradingPartnerId);
        } else {
            throw new IllegalArgumentException(
                    "Trading partner must have type of ACCOUNT or STORE. Trading parther id: " + tradingPartnerId);
        }
        //Get store catalog id
        storeCatalogId = catalogInfoEjb.getStoreCatalogId(storeId);
        
        //Get distributor
        BusEntityDataVector beDV = distrEjb.getNscStoreDistributor(storeId);
        if(beDV.size()==0) {
            throw new IllegalArgumentException(
                    "No disributor found for the store. Store id : " + storeId);
        }
        if(beDV.size()>1) {
            throw new IllegalArgumentException(
                    "Multiple disributors found for the store (should be one. Store id : " + storeId);
        }
        BusEntityData beD = (BusEntityData) beDV.get(0);
        distributorId = beD.getBusEntityId();
        
        BusEntitySearchCriteria manufCrit = new BusEntitySearchCriteria();
        IdVector storeIdV = new IdVector();
        storeIdV.add(new Integer(storeId));
        manufCrit.setStoreBusEntityIds(storeIdV);
        BusEntityDataVector manufBeDV = manufacturerEjb.getManufacturerBusEntitiesByCriteria(manufCrit);
        for(Iterator iter = manufBeDV.iterator(); iter.hasNext();) {
            BusEntityData manufBeD = (BusEntityData) iter.next();
            manufBeHM.put(manufBeD.getShortDesc(), manufBeD);
        }
        
        EntitySearchCriteria catalogCrit = new EntitySearchCriteria();
        catalogCrit.setStoreBusEntityIds(storeIdV);
        ArrayList catalogTypes = new ArrayList();
        catalogTypes.add(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
        catalogCrit.setStoreBusEntityIds(storeIdV);
        CatalogDataVector catalogDV = catalogInfoEjb.getCatalogsByCrit(catalogCrit);        
        for(Iterator iter = catalogDV.iterator(); iter.hasNext();) {
            CatalogData catalogD = (CatalogData) iter.next();
            catalogHM.put(catalogD.getShortDesc(), catalogD);
        }       

    }
    
    /**
     * Called when the object has successfully been parsed
     */
     protected void processParsedObject(Object pParsedObject)  throws Exception{
         if (!(pParsedObject instanceof NscPcView)){            
             throw new IllegalArgumentException("Parsed object has a wrong type "+ 
                     pParsedObject.getClass().getName()+ 
                     " (should be NscPcView) ");
         }
         items.add(pParsedObject);
     }
     
     protected void doPostProcessing() throws Exception {
    	 log.info("doPostProcessing started. Items qty: "+items.size());
    	 
    	 List compCodes = new ArrayList();
    	 int lineNum = 1;
         for(Iterator iter = items.iterator(); iter.hasNext(); lineNum++) {
             Integer lineNumI = new Integer(lineNum);
             NscPcView nscProdVw = (NscPcView) iter.next();
             
             String companyCode = nscProdVw.getCompany();
             if(!Utility.isSet(companyCode)) {
                 missingCompanyHS.add(lineNumI);
             }else{
            	 if(!compCodes.contains(companyCode)){
            		 compCodes.add(companyCode);
            	 }
             }
    	 
             String distSku = nscProdVw.getNscItemNumber();
             if(!Utility.isSet(distSku)) {
                 missingDistSkuHS.add(lineNumI);
             }
             
             String shortDesc1 = nscProdVw.getItemDescription1();
             if(!Utility.isSet(shortDesc1)) {
                missingShortDesc1HS.add(lineNumI);
             }
             if(shortDesc1.trim().toUpperCase().startsWith("DELETED")) {
                 iter.remove();
                 continue;
             }
             if(shortDesc1.trim().toUpperCase().startsWith("FUEL SURCHARGE")) {
                 fuelSurchargeHS.add(nscProdVw);
                 iter.remove();
                 continue;
             }
    	 
             String longDesc = nscProdVw.getExtendetItemDescription();
             if(!Utility.isSet(longDesc)) {
                 missingLongDescHS.add(lineNumI);
             }
             
             String manufName = Utility.strNN(nscProdVw.getManufacturer()).trim();
             if(!Utility.isSet(manufName)) {
            	 emptyManufNameHS.add(lineNumI);
             }else if(!manufBeHM.containsKey(manufName) && 
                !missingManufNameHS.contains(manufName)) {
                missingManufNameHS.add(manufName);
             }
    	 
             String manufSku = nscProdVw.getMfgSku();
             if(!Utility.isSet(manufSku)) {
                 missingManufSkuHS.add(lineNumI);
             }
    	 
             String uom = nscProdVw.getDefaultUom();
             if(!Utility.isSet(uom)) {
                 missingUomHS.add(lineNumI);
             }
             
             String pack = nscProdVw.getStandartPack();
             if(!Utility.isSet(pack)) {
                 missingPackHS.add(lineNumI);
             }
    	 
             String price = nscProdVw.getListPrice1();
             if(!Utility.isSet(price)) {
                 missingPriceHS.add(lineNumI);
             }
             try {
                 Double.parseDouble(price);
             } catch (Exception exc) {
                 missingPriceHS.add(lineNumI);
             }
             
             String category = nscProdVw.getCategory();
             if(!Utility.isSet(category)) {
                 missingCategoryHS.add(lineNumI);
             }
             if(!categoryHS.contains(category)) {
                 categoryHS.add(category);
             }
    	 
             String upcCode = nscProdVw.getUpc();
             if(!Utility.isSet(upcCode)) {
                 missingUpcCodeHS.add(lineNumI);
             }
	 
         }
         
         //Creat missing manufacturers 
         if(missingManufNameHS.size()>0) {
        	 for(Iterator iter=missingManufNameHS.iterator(); iter.hasNext();) {
        		 String manufName = (String) iter.next();
        		 if(!manufBeHM.containsKey(manufName)) {
        			 log.info("Creating manufacturer: "+ manufName);            
        			 BusEntityData mBeD = new BusEntityData();
                     mBeD.setShortDesc(manufName);
                     mBeD.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
                     mBeD.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
                     mBeD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
                     mBeD.setLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
                     mBeD.setAddBy(mUser);
                     mBeD.setModBy(mUser);
                     ManufacturerData mD = new ManufacturerData(
                         mBeD,
                         storeId,
                         null,
                         null,
                         null,
                         null,
                         null, // pBusinessClass,
                         null, // PropertyData pWomanOwnedBusiness,
                         null, // PropertyData pMinorityOwnedBusiness,
                         null, // PropertyData pJWOD,
                         null, // PropertyData pOtherBusiness,
                         null, // PropertyDataVector pSpecializations,
                         null, // PropertyDataVector pMiscProperties, 
                         null // PropertyData pOtherNames
                         );
                     mD = manufacturerEjb.addManufacturer(mD);   
                     manufBeHM.put(manufName,mD.getBusEntity());
        		 }
        	 }
         }
         
         if(missingCategoryHS.size()>0 ||
                 missingUomHS.size()>0 ||
                 missingLongDescHS.size()>0 ||
                 missingShortDesc1HS.size()>0 ||
                 missingPriceHS.size()>0 ||
                 emptyManufNameHS.size()>0 ||
                 missingManufSkuHS.size()>0 ||
                 missingDistSkuHS.size()>0 ||
                 missingPackHS.size()>0 ||
                 missingUpcCodeHS.size()>0 ||
                 missingCompanyHS.size()>0
                 ) {
                  String errorMess = "Error(s): ";
                  
                  if(missingCompanyHS.size()>0) {
                      errorMess += "\r\n Empty company property. Line numbers: ";            
                      for(Iterator iter=missingCompanyHS.iterator(); iter.hasNext();) {
                          errorMess += "<"+iter.next()+"> ";
                      }
                  }
                  
                  if(missingCategoryHS.size()>0) {
                      errorMess += "\r\n Empty category property. Line numbers: ";            
                      for(Iterator iter=missingCategoryHS.iterator(); iter.hasNext();) {
                          errorMess += "<"+iter.next()+"> ";
                      }
                  }
                  if(missingUomHS.size()>0) {
                      errorMess += "\r\n Empty uom property. Line numbers: ";            
                      for(Iterator iter=missingUomHS.iterator(); iter.hasNext();) {
                          errorMess += "<"+iter.next()+"> ";
                      }
                  }
                  if(missingLongDescHS.size()>0 ) {
                      errorMess += "\r\n Empty long description property. Line numbers: ";            
                      for(Iterator iter=missingLongDescHS.iterator(); iter.hasNext();) {
                          errorMess += "<"+iter.next()+"> ";
                      }
                  }
                  if(missingShortDesc1HS.size()>0) {
                      errorMess += "\r\n Empty descrition 1 property. Line numbers: ";            
                      for(Iterator iter=missingShortDesc1HS.iterator(); iter.hasNext();) {
                          errorMess += "<"+iter.next()+"> ";
                      }
                  }
                  if(missingPriceHS.size()>0) {
                      errorMess += "\r\n Empty or invalid price. Line numbers: ";            
                      for(Iterator iter=missingPriceHS.iterator(); iter.hasNext();) {
                          errorMess += "<"+iter.next()+"> ";
                      }
                  }
                  if(emptyManufNameHS.size()>0) {
                      errorMess += "\r\n Empty manufacturer name property. Line numbers: ";            
                      for(Iterator iter=emptyManufNameHS.iterator(); iter.hasNext();) {
                          errorMess += "<"+iter.next()+"> ";
                      }
                  }                  
                  if(missingManufSkuHS.size()>0) {
                      errorMess += "\r\n Empty manufacturer Sku property. Line numbers: ";            
                      for(Iterator iter=missingManufSkuHS.iterator(); iter.hasNext();) {
                          errorMess += "<"+iter.next()+"> ";
                      }
                  }
                  if(missingDistSkuHS.size()>0) {
                      errorMess += "\r\n Empty NSC item number. Line numbers: ";            
                      for(Iterator iter=missingDistSkuHS.iterator(); iter.hasNext();) {
                          errorMess += "<"+iter.next()+"> ";
                      }
                  }
                  if(missingPackHS.size()>0) {
                      errorMess += "\r\n Empty pack property. Line numbers: ";            
                      for(Iterator iter=missingPackHS.iterator(); iter.hasNext();) {
                          errorMess += "<"+iter.next()+"> ";
                      }
                  }
                  if(missingUpcCodeHS.size()>0) { 
                      errorMess += "\r\n Empty upc code property. Line numbers: ";            
                      for(Iterator iter=missingUpcCodeHS.iterator(); iter.hasNext();) {
                          errorMess += "<"+iter.next()+"> ";
                      }
                  }
                  log.info("BBBBBBBBBB"+errorMess);
                  Logger log = Logger.getLogger(this.getClass());
                  log.info("AAAAAAAAAAAA "+errorMess);

                  throw new Exception(errorMess);
              }
         
         //get acct id from company code
         Iterator it = compCodes.iterator();
         while(it.hasNext()){
        	 String cc = (String)it.next();
        	 int acctId =accountEjb.getAccountIdForCompany(cc);
        	 if(!acctIdByCompany.containsKey(cc)){
        		 acctIdByCompany.put(cc, new Integer(acctId));
        	 }
         }
         
         //Request-create categories
         categoryHM = catalogEjb.createOrRequestStoreCategories(storeCatalogId,categoryHS,mUser );
         
         ProductDataVector productDV = new ProductDataVector();
         SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
         Date effDate = sdf.parse(sdf.format(new Date()));
         for(Iterator iter = items.iterator(); iter.hasNext(); ) {
             NscPcView nscProdVw = (NscPcView) iter.next();
             ProductData pD = processItemRecord(nscProdVw, storeId, effDate);
             productDV.add(pD);
         } 
         
         //productsByAcct [acctId={pd1, pd2....}
         //productDV={pd1,pd2...}
         
         for(Map.Entry<Integer, ProductDataVector> en: productsByAcct.entrySet()){
        	 
        	 int acctId = ((Integer)en.getKey()).intValue();
        	 ProductDataVector products = en.getValue();
        	 catalogEjb.createOrUpdateNscMasterProducts(storeId, acctId, storeCatalogId, distributorId,
        			 products,mUser);
         }
         
         
         
     }
     
     public void translate(InputStream pIn, String pStreamType) throws Exception {   
         doPreProcessing();
         TabFileParser parser = new TabFileParser();
         parser.parse(pIn);
         parser.cleanUpResult();
         parser.processParsedStrings(this);
         doPostProcessing();
     }
     
     public ProductData processItemRecord(NscPcView nscProdVw, int storeId, Date effDate)
     throws Exception {
   
    	 String companyCode = Utility.strNN(nscProdVw.getCompany()).trim();
    	 Integer acctId = acctIdByCompany.get(companyCode);
    	 
    	 String manufName = Utility.strNN(nscProdVw.getManufacturer()).trim();
    	 String category = Utility.strNN(nscProdVw.getCategory()).trim();
    	 String custItemNum = Utility.strNN(nscProdVw.getCustomerItemNumber()).trim();
    	 String uom = Utility.strNN(nscProdVw.getDefaultUom()).trim();
    	 String longDesc = Utility.strNN(nscProdVw.getExtendetItemDescription()).trim();
    	 String shortDesc1 = Utility.strNN(nscProdVw.getItemDescription1()).trim();
    	 //String shortDesc2 = Utility.strNN(nscProdVw.getItemDescription2()).trim();
    	 String price = Utility.strNN(nscProdVw.getListPrice1()).trim();
    	 String manufSku = Utility.strNN(nscProdVw.getMfgSku()).trim();
    	 String distSku = Utility.strNN(nscProdVw.getNscItemNumber()).trim();
    	 //String nscSku = Utility.strNN(nscProdVw.getNscSku()).trim();
    	 String pack = Utility.strNN(nscProdVw.getStandartPack()).trim();
    	 String upcCode = Utility.strNN(nscProdVw.getUpc()).trim();
     
    	 ProductData productD = new ProductData();      
         
    	 ItemData itemD = productD.getItemData();
    	 itemD.setAddBy(mUser);
    	 itemD.setModBy(mUser);
     
    	 itemD.setShortDesc(shortDesc1);   
    	 itemD.setLongDesc(longDesc);
    	 itemD.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
    	 itemD.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.PRODUCT);
    	 BusEntityData manufBeD = (BusEntityData) manufBeHM.get(manufName);
    	 if(manufBeD==null) {
    		 String errorMess = "Manufacturer not found. Manuf name: "+manufName+" product short desc: "+shortDesc1;
    		 log.error("BBBBBBBBBB"+errorMess);
    	 }
    	 productD.setManufacturer(manufBeD);
    	 productD.setManuMapping(manufBeD, manufSku);         
    	 productD.setDistributorPack(distributorId, pack);
    	 productD.setDistributorUom(distributorId, uom);
    	 productD.setDistributorSku(distributorId, distSku);
    	 productD.setEffDate(effDate);
       
    	 productD.setPack(pack);
    	 productD.setUom(uom);                    
    	 productD.setUpc(upcCode);
    	 double priceDb = Double.parseDouble(price);
    	 productD.setListPrice(priceDb);
    	 productD.setCostPrice(priceDb);
    	 productD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);                                               

    	 if(Utility.isSet(custItemNum)) {
    		 productD.setCustomerSkuNum(custItemNum);
    	 }
     
    	 CatalogCategoryDataVector categoryDV = new CatalogCategoryDataVector(); 
    	 CatalogCategoryData categoryD = (CatalogCategoryData) categoryHM.get(category);
    	 categoryDV.add(categoryD);
    	 productD.setCatalogCategories(categoryDV);
    	 
    	 if(productsByAcct.containsKey(acctId)){
    		 ProductDataVector prods = productsByAcct.get(acctId);
    		 prods.add(productD);
    	 }else{
    		 ProductDataVector prods = new ProductDataVector();
    		 prods.add(productD);
    		 productsByAcct.put(acctId, prods);
    	 }
    	 
    	 return productD;
     }
            
     public static IdVector getItemIdsByUpc(String upcNum) throws NamingException, APIServiceAccessException{
         IdVector itemIdsV = null;
         ItemDataVector upcItemsDV = null;
         
         try {
             upcItemsDV = APIAccess.getAPIAccess().getItemInformationAPI().
                                                 getItemsCollectionByUPC(upcNum);
         } catch (RemoteException e) {
             log.info("Error while getting items with UPC number: " + upcNum);
         }

         if (upcItemsDV != null) {
             int upcItemsDVsize = upcItemsDV.size();
             if (upcItemsDVsize > 0) {
             	 itemIdsV = new IdVector();
                 for (int i = 0; i < upcItemsDVsize; i++) {
                     itemIdsV.add(new Integer(((ItemData)upcItemsDV.get(i)).getItemId()));
                 }
             }
         }        
         return itemIdsV;
     }
}
