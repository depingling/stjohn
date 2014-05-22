package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.apps.loaders.TabFileParser;
import org.apache.log4j.Logger;


import javax.naming.NamingException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;

public class InboundItemTxt extends InboundFlatFile {
    private static final Logger log = Logger.getLogger(InboundItemTxt.class);

    private Account accountEjb;
    protected CatalogInformation catalogInfoEjb;
    protected Catalog catalogEjb;
    protected Distributor distrEjb;
    protected Manufacturer manufacturerEjb;

    private HashMap accountMap = new HashMap();
    protected int storeId = -1;
    protected int distributorId = -1;
    protected int storeCatalogId = -1;
    protected HashMap manufBeHM  = new HashMap();
    protected HashMap catalogHM = new HashMap();
    protected ArrayList items = new ArrayList();

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

    protected HashSet categoryHS = new HashSet();
    protected HashMap categoryHM = null;
    protected HashSet missingCatalogNameHS = new HashSet();
    protected HashSet missingManufNameHS = new HashSet();
    protected HashSet emptyManufNameHS = new HashSet();
    protected HashSet missingCategoryHS = new HashSet();
    protected HashSet missingUomHS = new HashSet();
    protected HashSet missingLongDescHS = new HashSet();
    protected HashSet missingShortDesc1HS = new HashSet();
    protected HashSet missingPriceHS = new HashSet();
    protected HashSet missingManufSkuHS = new HashSet();
    protected HashSet missingDistSkuHS = new HashSet();
    protected HashSet missingPackHS = new HashSet();
    protected HashSet missingUpcCodeHS = new HashSet();
    protected HashSet fuelSurchargeHS = new HashSet();

     protected void doPostProcessing() throws Exception {
log.info("doPostProcessing started. Items qty: "+items.size());
        int lineNum = 1;
        for(Iterator iter = items.iterator(); iter.hasNext(); lineNum++) {
            Integer lineNumI = new Integer(lineNum);
            NscPcView nscProdVw = (NscPcView) iter.next();
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


            String catalogName = Utility.strNN(nscProdVw.getCatalogName()).trim();
            if(!catalogHM.containsKey(catalogName) &&
               !missingCatalogNameHS.contains(catalogName)) {
               missingCatalogNameHS.add("'"+catalogName+"' ("+lineNumI+")");
            }
            String manufName = Utility.strNN(nscProdVw.getManufacturer()).trim();
            if(!Utility.isSet(manufName)) {
           	 emptyManufNameHS.add(lineNumI);
            }else if(!manufBeHM.containsKey(manufName) && 
               !missingManufNameHS.contains(manufName)) {
               missingManufNameHS.add(manufName);
            }
            String category = nscProdVw.getCategory();
            if(!Utility.isSet(category)) {
                missingCategoryHS.add(lineNumI);
            }
            if(!categoryHS.contains(category)) {
                categoryHS.add(category);
            }
            String uom = nscProdVw.getDefaultUom();
            if(!Utility.isSet(uom)) {
                missingUomHS.add(lineNumI);
            }
            String longDesc = nscProdVw.getExtendetItemDescription();
            if(!Utility.isSet(longDesc)) {
                missingLongDescHS.add(lineNumI);
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
            String manufSku = nscProdVw.getMfgSku();
            if(!Utility.isSet(manufSku)) {
                missingManufSkuHS.add(lineNumI);
            }
            String distSku = nscProdVw.getNscItemNumber();
            if(!Utility.isSet(distSku)) {
                missingDistSkuHS.add(lineNumI);
            }
            String pack = nscProdVw.getStandartPack();
            if(!Utility.isSet(pack)) {
                missingPackHS.add(lineNumI);
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
                    mBeD.setAddBy("Nsc Item Loader");
                    mBeD.setModBy("Nsc Item Loader");
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

        if(missingCatalogNameHS.size()>0 ||
           missingCategoryHS.size()>0 ||
           missingUomHS.size()>0 ||
           missingLongDescHS.size()>0 ||
           missingShortDesc1HS.size()>0 ||
           missingPriceHS.size()>0 ||
           emptyManufNameHS.size()>0 ||
           missingManufSkuHS.size()>0 ||
           missingDistSkuHS.size()>0 ||
           missingPackHS.size()>0 ||
           missingUpcCodeHS.size()>0
           ) {
            String errorMess = "Error(s): ";
            if(missingCatalogNameHS.size()>0) {
                errorMess += "\r\n Not found catalogs: ";
                for(Iterator iter=missingCatalogNameHS.iterator(); iter.hasNext();) {
                    errorMess += "<"+iter.next()+"> ";
                }
            }

            if(missingCategoryHS.size()>0) {
                errorMess += "\r\n Empty categoriy property. Line numbers: ";
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
                errorMess += "\r\n Empty manufacturer name property. Line numbers: ";
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
        //Request-create categories
        categoryHM = catalogEjb.createOrRequestStoreCategories(storeCatalogId,categoryHS,"Item Loader" );


        /*
        */
        ProductDataVector productDV = new ProductDataVector();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date effDate = sdf.parse(sdf.format(new Date()));
        for(Iterator iter = items.iterator(); iter.hasNext(); ) {
            NscPcView nscProdVw = (NscPcView) iter.next();
            ProductData pD = processItemRecord(nscProdVw, storeId, effDate);
            productDV.add(pD);
        }

        //Create or udpate items, adjust catalogs
        catalogEjb.createOrUpdateNscProducts(storeId, storeCatalogId, distributorId,productDV,"Item Loader");
        catalogEjb.updateProductCategory(productDV, "Item Loader");
        
    }

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        doPreProcessing();
        TabFileParser parser = new TabFileParser();
        parser.parse(pIn);
        // comment out following line to not ignore suspecious lines
        //parser.cleanUpResult();
        parser.processParsedStrings(this);
        doPostProcessing();
    }




    public ProductData processItemRecord(NscPcView nscProdVw, int storeId, Date effDate)
        throws Exception {

//ignore if name starts wtih DELETED
//FUEL SURCHARGE
        //String company = Utility.strNN(nscProdVw.getCompany()).trim();
        String catalogName = Utility.strNN(nscProdVw.getCatalogName()).trim();
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

        String userName = "Item Loader";
        ProductData productD = new ProductData();

        ItemData itemD = productD.getItemData();
        itemD.setAddBy(userName);
        itemD.setModBy(userName);

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

        CatalogData catD = (CatalogData) catalogHM.get(catalogName);
        int shoppingCatalogId = catD.getCatalogId();
        productD.setCatalogStructure(shoppingCatalogId);
        productD.getCatalogStructure().setBusEntityId(distributorId);

        if(Utility.isSet(custItemNum)) {
            productD.setCustomerSkuNum(custItemNum);
        }

        CatalogCategoryDataVector categoryDV = new CatalogCategoryDataVector();
        CatalogCategoryData categoryD = (CatalogCategoryData) categoryHM.get(category);
        categoryDV.add(categoryD);
        productD.setCatalogCategories(categoryDV);
        return productD;
    }
    /*
          if(beanToSave.getUpc() != null && beanToSave.getUpc().trim().length() > 0){
              //Get item ids if they already exist with this UPC code
              IdVector itemIds = getItemIdsByUpc(beanToSave.getUpc().trim());
              if(itemIds != null && itemIds.size() > 0)
                productD = UpdateCatalogProduct(itemIds, storeCatalogId, productD);
          }

          productD = catalogEjb.saveStoreCatalogProduct(storeId, storeCatalogId, productD, cuser);
          log.info("STORE catalog saved... catalog id= " + storeCatalogId);

          //Find ACCOUNT catalog and verify categories
          int accountId = -1;
          if(accountMap.containsKey(new Integer(shoppingCatalogId)))
            accountId = ((Integer)accountMap.get(new Integer(shoppingCatalogId))).intValue();
          else {
            accountId = getAccountIdByShoppingCatalog(shoppingCatalogId);
            accountMap.put(new Integer(shoppingCatalogId), new Integer(accountId));
          }
          if(accountId > 0){
              Account accEjb = APIAccess.getAPIAccess().getAccountAPI();
              CatalogData accCatalog = accEjb.getAccountCatalog(accountId);
              if(accCatalog != null){
                int accountCatalogId = accCatalog.getCatalogId();
                productD.setCatalogStructure(accountCatalogId);
                catalogEjb.saveCatalogProduct(accountCatalogId, productD, cuser);
                log.info("ACCOUNT catalog saved... catalog id = " + accountCatalogId +
                                                    " account id= " + accountId);
              }
          }

          // Find SHOPPING catalog and verify categories
          if(shoppingCatalogId > 0){
            productD.setCatalogStructure(shoppingCatalogId);
            catalogEjb.saveCatalogProduct(shoppingCatalogId, productD, cuser);
            log.info("SHOPPING catalog saved... catalog id = " + shoppingCatalogId);
          }
      } catch (RemoteException e) {
          log.error(e.toString(),e);
      } catch (DataNotFoundException e) {
          log.error(e.toString(),e);
      }
    }
    */
    private int getAccountIdByShoppingCatalog(int shoppingCatalog) throws RemoteException{
        CatalogAssocDataVector accountVector = catalogInfoEjb.getCatalogAssoc(
                shoppingCatalog, 0, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
        if(accountVector == null || accountVector.size() < 1)
            return -1;
        CatalogAssocData account = (CatalogAssocData)accountVector.get(0);

        return account.getBusEntityId();
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

    // Update ItemData information in the ProductData if product already exist in the catalog
    public static ProductData UpdateCatalogProduct(IdVector itemIds, int catalogId, ProductData productData)
                                                    throws NamingException, APIServiceAccessException{
        try {
            IdVector catalogItems = APIAccess.getAPIAccess().getCatalogInformationAPI().
                                                       searchCatalogProducts(catalogId);
            if (catalogItems != null && catalogItems.size() > 0) {
                if (itemIds != null) {
                    int size = itemIds.size();
                    for (int i = 0; i < size; i++) {
                        Integer upcItem = (Integer) itemIds.get(i);
                        if (catalogItems.contains(upcItem)) {
                            ProductData existenProduct = APIAccess.getAPIAccess().
                                    getProductInformationAPI().getProduct(upcItem.intValue());
                            ItemData existenItem = existenProduct.getItemData();

                            ItemData itemData = productData.getItemData();
                            itemData.setItemId(existenItem.getItemId());
                            itemData.setSkuNum(existenItem.getSkuNum());
                            itemData.setItemOrderNum(existenItem.getItemOrderNum());
                        }
                    }
                }
            }
        } catch (RemoteException e) {
            log.info(e.toString());
        }
        return productData;
    }
}
