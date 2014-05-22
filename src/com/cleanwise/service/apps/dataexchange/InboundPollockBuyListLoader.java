package com.cleanwise.service.apps.dataexchange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.log4j.Logger;


//import com.cleanwise.service.api.value.OrderGuideData;
//import com.cleanwise.service.api.value.OrderGuideLoadRequestData;
//import com.cleanwise.service.apps.loaders.TabFileParser;
import com.cleanwise.service.apps.loaders.PipeFileParser;
import java.io.InputStream;
import java.util.HashSet;
import java.util.HashMap;
import java.math.BigDecimal;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.util.RefCodeNames;

import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.value.BuyListView;
import com.cleanwise.service.api.value.BuyListViewVector;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.CatalogAssocDataVector;
import com.cleanwise.service.api.value.EntitySearchCriteria;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.value.CatalogData;
import java.util.Map;
import java.io.Serializable;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.session.Distributor;
import java.util.*;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.value.ItemMappingData;

public class InboundPollockBuyListLoader extends InboundNSCSapLoader {//InboundFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
	ArrayList parsedObjects = new ArrayList();
        int storeNum = 0;
        //int distributorId = 0;
        Map allShoppingCatalogMap = null;
        HashSet nonUniqCatalogNames =  new HashSet();
        String loader = "BuyListLoader";

        public void translate(InputStream pIn, String pStreamType) throws Exception {

          PipeFileParser parser = new PipeFileParser();
          parser.parse(pIn);
         // parser.cleanUpResult();
          parser.processParsedStrings(this);
          storeNum = getStoreIdFromTradingPartner();
           doPostProcessing();
        }

        protected void processParsedObject(Object pParsedObject) throws Exception {
           if (!(pParsedObject instanceof InboundBuyListData)){
               throw new IllegalArgumentException("Parsed object has a wrong type "+
                       pParsedObject.getClass().getName()+
                       " (should be InboundBuyListData) ");
             }
             parsedObjects.add(pParsedObject);
       }

	protected void doPostProcessing() {
               log.info("doPostProcessing() => BEGIN. ");
               long startTime = System.currentTimeMillis();
               try {
                 allShoppingCatalogMap = this.createAllShoppingCatalogMap();
               } catch (Exception ex ){
                 setFail(true);
                 log.info("ERROR :" + Utility.getInitErrorMsg(ex));
                 log.info("doPostProcessing() => FAILED.Process time at : "
                   + (System.currentTimeMillis() - startTime) + " ms" ) ;
                 return;
               }
		//loop through
		String lastKey=null;

		BuyListViewVector blvV = new BuyListViewVector();
                log.info("doPostProcessing() =>  :" + parsedObjects.size());
		Iterator it = parsedObjects.iterator();

                int line =0;
                HashSet listNames = new HashSet();
		while(it.hasNext() && !isErrorLimit()){
			InboundBuyListData buyList =(InboundBuyListData) it.next();
                        if (isValid(buyList, line)){
                          BuyListView blv = createBuyListView(buyList);
                          listNames.add(buyList.getListName());
                          blvV.add(blv);
                        }
                        line++;
                }


 //               if (this.getErrorMsgs() != null && this.getErrorMsgs().size()>0 ){
                if (errorLines.size() > 0){
                  processErrorMsgs();
                  setFail(true);
                  log.error("ERROR(s) :" + this.getFormatedErrorMsgs());

                } else if ( blvV.size() > 0) {
                  try {
                    int distributorId = this.getDistributorId();
                    // check: duplicate rows (different price)
                    checkUniqRowsForBuyList(blvV);
                    checkUniqCatalogNames(listNames);
                    checkAllItemsExistAndUniq(blvV, distributorId);

                    IntegrationServices isEjb = APIAccess.getAPIAccess().getIntegrationServicesAPI();
                    int total = isEjb.processBuyList(blvV, storeNum, distributorId, loader );
                    log.info("doPostProcessing() => END. Processed: " + blvV.size()+" records. ");
                  } catch (Exception ex ){
                    setFail(true);
                    log.info("ERROR :" + Utility.getInitErrorMsg(ex));
                    log.info("doPostProcessing() => FAILED.Process time at : "
                    + (System.currentTimeMillis() - startTime) + " ms" ) ;

                  }
                } else {
                  log.info("doPostProcessing() => End. NO RECORDS to process!" ) ;
                }

	}

	private BuyListView createBuyListView(InboundBuyListData buyList){
		BuyListView blv = BuyListView.createValue();

  //              pv.setStoreId(new Integer(pricing.getStoreId()).intValue());
                blv.setListName(buyList.getListName());
                blv.setDistSku(buyList.getDistSku());
                blv.setDistUom(buyList.getDistUom());
                blv.setListPrice(new BigDecimal(buyList.getListPrice()));
                int catalogId = 0;
                if (allShoppingCatalogMap.containsKey(buyList.getListName())){
                  catalogId = ((Integer)allShoppingCatalogMap.get(buyList.getListName())).intValue();
                }
                blv.setCatalogId(catalogId);
 //               blv.setDistId(distributorId);
                return blv;
	}

	private boolean isValid(InboundBuyListData buyList, int line){
          boolean valid = true;
          boolean ok = true;
          //------
          String version = buyList.getVersionNum();
          ok = checkType (version, "Version Number", TYPE.TEXT,true,line);
          if (ok && !version.trim().equals("1") ){
            addError("Version Number:"+ version +". Should be 1.", line);
            ok = false;
          }
          valid &= ok;
          //------
          String listName = buyList.getListName();
          ok = checkRequired(listName, "List Name", line);
          if (ok && listName.trim().length() > 30) {
            addError("List Name: '" + listName + "' is too long. Max length is 30 char.", line);
            ok = false;
          }
          valid &= ok;
          //-------
          valid &= checkRequired(buyList.getDistSku(), "Dist SKU", line);
          //-------
          valid &= checkRequired(buyList.getDistUom(), "Dist UOM", line);
          //-------
          String listPrice = buyList.getListPrice();
          ok = checkType(listPrice, "List Price", TYPE.BIG_DECIMAL, true, line);
          if (ok && (new BigDecimal(listPrice)).doubleValue() < 0){
            addError("List Price: '" + listPrice + "' is negative.", line);
            ok = false;
          }
          valid &= ok;
          //--------
          return valid;
        }

        private Map createAllShoppingCatalogMap () throws Exception{
          CatalogInformation catInfoEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();

          EntitySearchCriteria  esc= new  EntitySearchCriteria();
          ArrayList types = new ArrayList();
          types.add(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
          IdVector beIds = new IdVector();
          beIds.add(new Integer(storeNum));

          esc.setSearchTypeCds(types);
          esc.setSearchId(0);
          esc.setStoreBusEntityIds(beIds);
          CatalogDataVector allShoppingCatalogs = catInfoEjb.getCatalogsByCritAndIds(esc, null) ;
          HashMap map = new HashMap();

          if(allShoppingCatalogs!=null){
            for (int i = 0; i < allShoppingCatalogs.size(); i++) {
              CatalogData catalogD = (CatalogData)allShoppingCatalogs.get(i);
              if (!map.containsKey(catalogD.getShortDesc())) {
                map.put(catalogD.getShortDesc(),new Integer(catalogD.getCatalogId()));
              } else {
                nonUniqCatalogNames.add(catalogD.getShortDesc());
              }
            }

          }
          return map;
        }

        private int getDistributorId() throws Exception {
          //Get distributor
          Distributor distrEjb = APIAccess.getAPIAccess().getDistributorAPI();
      //    BusEntityDataVector beDV = distrEjb.getNscStoreDistributor(storeNum);
          IdVector beDV = distrEjb.getDistributorIdsForStore(storeNum, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
          if (beDV.size() == 0) {
            throw new IllegalArgumentException(
                "^clw^No active disributor found for the store. Store id : " + storeNum+"^clw^");
          }
          if (beDV.size() > 1) {
            throw new IllegalArgumentException(
                "^clw^There are more then one active distributor in the store. Store id : " +
                storeNum+"^clw^");
          }
          Integer idI = (Integer) beDV.get(0);
          int id = idI.intValue();
          return id;
        }

        private void checkUniqCatalogNames(HashSet listNames) throws Exception{
          HashSet nonUniqListNames =  new HashSet();
          for (Iterator iter = listNames.iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            if (nonUniqCatalogNames.contains(name)){
              nonUniqListNames.add(name);
            }
          }
          if(nonUniqListNames.size() > 0){
            throw new Exception(
                 "^clw^There are more than one catalog having name equal List Name in the store. List Name(s) :" +  nonUniqListNames.toString()+"^clw^");
          }
        }

        private void checkAllItemsExistAndUniq (BuyListViewVector buyListVector, int distId) throws Exception {
          ItemInformation itemInfoEjb = APIAccess.getAPIAccess().getItemInformationAPI();
          DBCriteria dbc = new DBCriteria();
          dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, distId);
          dbc.addIsNotNull(ItemMappingDataAccess.ITEM_NUM);
          dbc.addIsNotNull(ItemMappingDataAccess.ITEM_UOM);
          dbc.addJoinTable(ItemDataAccess.CLW_ITEM);
          dbc.addJoinCondition(ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_ID, ItemMappingDataAccess.CLW_ITEM_MAPPING,  ItemMappingDataAccess.ITEM_ID);
          dbc.addJoinTableEqualTo(ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
          ItemMappingDataVector imDV = itemInfoEjb.getItemMappingsCollection(dbc);
          if (imDV == null || imDV.size() == 0){
            throw new Exception("^clw^There are no active items for distributor. Distributor Id :" + distId +"^clw^");
          }
          HashSet allNonUniqItems = new HashSet();
          HashMap allDistrItemsMap = new HashMap();
          for (int i = 0; imDV != null && i < imDV.size(); i++) {
            ItemMappingData imD = (ItemMappingData)imDV.get(i);
            String key = storeNum+"/"+distId+"/"+imD.getItemNum()+"/"+imD.getItemUom();
            if (!allDistrItemsMap.containsKey(key)){
              allDistrItemsMap.put(key, new Integer(imD.getItemId()));
            } else {
              allNonUniqItems.add(key);
            }
          }
          HashSet nonUniqItems = new HashSet();
          HashSet undefinedItems = new HashSet();
          for (int i = 0; i < buyListVector.size(); i++) {
            BuyListView blV = (BuyListView)buyListVector.get(i);
            String blKey = storeNum+"/"+distId+"/"+blV.getDistSku()+"/"+blV.getDistUom() ;
            if (allDistrItemsMap.containsKey(blKey)) {
              blV.setItemId(((Integer)allDistrItemsMap.get(blKey)).intValue());
 //             log.info("checkAllItemsExistAndUniq() item found for key= " + blKey + " ===> itemId= " + blV.getItemId());
            } else if (allNonUniqItems.contains(blKey)){
              nonUniqItems.add(blKey);
            } else {
              undefinedItems.add(blKey);
            }
          }
          if (undefinedItems.size()>0){
            throw new Exception("^clw^There is no item in the store defined by key(store,distributor,dist SKU,dist UOM):" + undefinedItems.toString()+"^clw^");
          }
          if (nonUniqItems.size()>0){
            throw new Exception("^clw^There are more than one item defined by key(store,distributor,dist SKU,dist UOM):" + nonUniqItems.toString()+"^clw^");
          }

        }

        private void checkUniqRowsForBuyList (BuyListViewVector inboundVV) throws Exception {
//         log.info("check: is there duplicate rows with Buy list name and the same dist sku.");
         HashSet nonUniqRows =  new HashSet();
         HashSet uniqRows =  new HashSet();
         for (int i = 0; i < inboundVV.size(); i++) {
           BuyListView inboundV = (BuyListView) inboundVV.get(i);
           String key = "BuyList="+ inboundV.getListName() + ", DistSku="+inboundV.getDistSku() + ", DistUOM=" + inboundV.getDistUom();
           if (uniqRows.contains(key)){
             nonUniqRows.add(key);
           } else {
            uniqRows.add(key);
           }
         }
         if(nonUniqRows.size() > 0){
            throw new Exception(
                 "^clw^Item with the same Dist Sku and UOM already exists for Buy List: " +  nonUniqRows.toString()+"^clw^");
          }
       }

        public static class InboundBuyListData implements Serializable{
        String versionNum;
        String listName;
        String distSku;
        String distUom;
        String listPrice;

        public String getVersionNum() {
                return versionNum;
        }
        public void setVersionNum(String versionNum) {
                this.versionNum = versionNum;
        }

        public String getListName() {
                return listName;
        }
        public void setListName(String listName) {
                this.listName = listName;
        }

        public String getDistSku() {
                return distSku;
        }
        public void setDistSku(String distSku) {
                this.distSku = distSku;
        }

        public String getDistUom() {
                return distUom;
        }
        public void setDistUom(String distUom) {
                this.distUom = distUom;
        }

        public String getListPrice() {
                return listPrice;
        }
        public void setListPrice(String listPrice) {
                this.listPrice = listPrice;
        }

        public String toString(){
                return "InboundBuyListData: "+
                versionNum +
                ',' + listName +
                ',' + distSku +
                ',' + distUom +
                ',' + listPrice ;
        }

}


}
