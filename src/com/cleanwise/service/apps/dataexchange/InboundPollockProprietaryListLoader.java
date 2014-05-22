package com.cleanwise.service.apps.dataexchange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.log4j.Logger;


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
//import com.cleanwise.service.api.value.PricingListView;
//import com.cleanwise.service.api.value.PricingListViewVector;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
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
import com.cleanwise.service.api.dao.PriceListAssocDataAccess;
import com.cleanwise.service.api.dao.PriceListDataAccess;
import com.cleanwise.service.api.value.*;
import java.sql.Connection;

public class InboundPollockProprietaryListLoader extends InboundNSCSapLoader {//InboundFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
	ArrayList parsedObjects = new ArrayList();
        int storeNum = 0;
        //int distributorId = 0;
        Map allPricingListsMap = null;
        HashSet nonUniqDBPricingListNames =  new HashSet();
        Connection conn = null;
        String loader = "ProprietaryListLoader";
        String listType = RefCodeNames.PRICE_LIST_TYPE_CD.PROPRIETARY_LIST;
        String storeAssocCd = RefCodeNames.PRICE_LIST_ASSOC_CD.PROPRIETARY_LIST_STORE;


        public void translate(InputStream pIn, String pStreamType) throws Exception {

          PipeFileParser parser = new PipeFileParser();
          parser.parse(pIn);
       //   parser.cleanUpResult();
          parser.processParsedStrings(this);
          storeNum = getStoreIdFromTradingPartner();
           doPostProcessing();
        }

        protected void processParsedObject(Object pParsedObject) throws Exception {
           if (!(pParsedObject instanceof InboundPricingListData)){
               throw new IllegalArgumentException("Parsed object has a wrong type "+
                       pParsedObject.getClass().getName()+
                       " (should be InboundPricingListData) ");
             }
             parsedObjects.add(pParsedObject);
       }

	protected void doPostProcessing() {
               log.info("doPostProcessing() => BEGIN. ");

               long startTime = System.currentTimeMillis();
               try {
                 conn = getConnection();
                 allPricingListsMap = this.getAllPricingListsMap(conn);
               } catch (Exception ex ){
                 setFail(true);
                 ex.printStackTrace();
                 log.info("ERROR :" + Utility.getInitErrorMsg(ex));
                 log.info("doPostProcessing() => FAILED.Process time at : "
                   + (System.currentTimeMillis() - startTime) + " ms" ) ;
                 closeConnection(conn);
                 return;
               }
		//loop through
		String lastKey=null;

		PricingListViewVector plvV = new PricingListViewVector();
                log.info("doPostProcessing() =>  :" + parsedObjects.size());
		Iterator it = parsedObjects.iterator();

                int line =0;
                HashSet listNames = new HashSet();
		while(it.hasNext() && !isErrorLimit()){
			InboundPricingListData pricingList =(InboundPricingListData) it.next();
                        if (isValid(pricingList, line)){
                          PricingListView plv = createPricingListView(pricingList);
                          listNames.add(pricingList.getListName());
                          plvV.add(plv);
                        }
                        line++;
                }

 //               if (this.getErrorMsgs() != null && this.getErrorMsgs().size()>0 ){
                if (errorLines.size() > 0){
                  processErrorMsgs();
                  setFail(true);
                  log.error("ERROR(s) :" + this.getFormatedErrorMsgs());

                } else if ( plvV.size() > 0) {
                  try {
                    int distributorId = this.getDistributorId();
                    // check: duplicate rows
                    checkUniqRowsForPricingList(plvV);
                    checkUniqPricingListNames(listNames);
                    checkAllItemsExistAndUniq(plvV, distributorId);

                    IntegrationServices isEjb = APIAccess.getAPIAccess().getIntegrationServicesAPI();
                    int total = isEjb.processPricingList(plvV, storeNum, distributorId, listType, loader );
                    log.info("doPostProcessing() => END. Processed: " + plvV.size()+" records. ");
                  } catch (Exception ex ){
                    setFail(true);
                    log.info("ERROR :" + Utility.getInitErrorMsg(ex));
                    log.info("doPostProcessing() => FAILED.Process time at : "
                    + (System.currentTimeMillis() - startTime) + " ms" ) ;

                  } finally {
                    closeConnection(conn);
                  }

                } else {
                  log.info("doPostProcessing() => End. NO RECORDS to process!" ) ;
                }

	}


	private PricingListView createPricingListView(InboundPricingListData priceList ){


                PricingListView plv = PricingListView.createValue();

                plv.setListName(priceList.getListName());
                plv.setDistSku(priceList.getDistSku());
                plv.setDistUom(priceList.getDistUom());
                int listId = 0;
                if (allPricingListsMap.containsKey(priceList.getListName())){
                  listId = ((Integer)allPricingListsMap.get(priceList.getListName())).intValue();
                }
                plv.setListId(listId);
                return plv;
	}


	private boolean isValid(InboundPricingListData pricingList, int line){
          boolean valid = true;
          boolean ok = true;
          //------
          String version = pricingList.getVersionNum();
          ok = checkType (version, "Version Number", TYPE.TEXT,true,line);
          if (ok && !version.trim().equals("1") ){
            addError("Version Number:"+ version +". Should be 1.", line);
            ok = false;
          }
          valid &= ok;
          //------
          String listName = pricingList.getListName();
          ok = checkRequired(listName, "List Name", line);
          if (ok && listName.trim().length() > 30) {
            addError("List Name: '" + listName + "' is too long. Max length is 30 char.", line);
            ok = false;
          }
          valid &= ok;
          //-------
          valid &= checkRequired(pricingList.getDistSku(), "Dist SKU", line);
          //-------
          valid &= checkRequired(pricingList.getDistUom(), "Dist UOM", line);
          //--------
          return valid;
        }

        private Map getAllPricingListsMap (Connection conn) throws Exception{
          HashMap map = new HashMap();
          try {
          DBCriteria dbc = new DBCriteria();
          // get all sites for store
/*          String sql = "select BUS_ENTITY_ID from clw_bus_entity be , clw_bus_entity_assoc acc , clw_bus_entity_assoc site " +
              "where   ACC.BUS_ENTITY2_ID =" + storeNum +
              "  and ACC.BUS_ENTITY1_ID = SITE.BUS_ENTITY2_ID "+
              "  and ACC.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+ "' "+
              "  and SITE.BUS_ENTITY_ASSOC_CD = '" +RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+ "' "+
              "  and BE.BUS_ENTITY_ID = SITE.BUS_ENTITY1_ID "+
              "  and BE.BUS_ENTITY_TYPE_CD = '"+RefCodeNames.BUS_ENTITY_TYPE_CD.SITE+"' ";
          log.info("getAllPricingListsMap() ===> sql =" + sql);
*/
          // get all pricing lists for these sites
          dbc.addJoinTable(PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC);
          dbc.addJoinCondition(PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC,PriceListAssocDataAccess.PRICE_LIST_ID, PriceListDataAccess.CLW_PRICE_LIST, PriceListDataAccess.PRICE_LIST_ID );
          dbc.addJoinTableEqualTo(PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC, PriceListAssocDataAccess.BUS_ENTITY_ID, storeNum);
          dbc.addJoinTableEqualTo(PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC, PriceListAssocDataAccess.PRICE_LIST_ASSOC_CD, storeAssocCd);
          dbc.addEqualTo(PriceListDataAccess.PRICE_LIST_TYPE_CD, listType);

///         dbc.addJoinTableOneOf(PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC, PriceListAssocDataAccess.BUS_ENTITY_ID, sql);
/*         DBCriteria dbc1 = new DBCriteria();
          dbc1.addOneOf(PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC+"."+ PriceListAssocDataAccess.BUS_ENTITY_ID, sql);
          dbc.addIsolatedCriterita(dbc1);
*/
         PriceListDataVector allPriceLists = PriceListDataAccess.select(conn, dbc);
          if(allPriceLists!=null){
            for (int i = 0; i < allPriceLists.size(); i++) {
              PriceListData priceListD = (PriceListData)allPriceLists.get(i);
              if (!map.containsKey(priceListD.getShortDesc())) {
                map.put(priceListD.getShortDesc(),new Integer(priceListD.getPriceListId()));
              } else {
                nonUniqDBPricingListNames.add(priceListD.getShortDesc());
              }
            }

          }
          } catch (Exception ex) {
            throw new Exception("^clw^"+"getAllPricingListsMap() ==>  Exception : " + ex.getMessage()+"^clw^");
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


        private void checkUniqPricingListNames(HashSet listNames) throws Exception{
          HashSet nonUniqListNames =  new HashSet();
          for (Iterator iter = listNames.iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            if (nonUniqDBPricingListNames.contains(name)){
              nonUniqListNames.add(name);
            }
          }
          if(nonUniqListNames.size() > 0){
            throw new Exception(
                 "^clw^There are more than one Proprietary List having name equal List Name in the store. List Name(s) :" +  nonUniqListNames.toString()+"^clw^");
          }
        }

        private void checkAllItemsExistAndUniq (PricingListViewVector priceListVector, int distId) throws Exception {
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
          for (int i = 0; i < priceListVector.size(); i++) {
            PricingListView blV = (PricingListView)priceListVector.get(i);
            String blKey = storeNum+"/"+distId+"/"+blV.getDistSku()+"/"+blV.getDistUom() ;
            if (allDistrItemsMap.containsKey(blKey)) {
              blV.setItemId(((Integer)allDistrItemsMap.get(blKey)).intValue());
              log.debug("checkAllItemsExistAndUniq() item found for key= " + blKey + " ===> itemId= " + blV.getItemId());
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
        private void checkUniqRowsForPricingList (PricingListViewVector inboundVV) throws Exception {
          log.info("check: is there duplicate rows with proprietary list name and the same dist sku.");
          HashSet nonUniqRows =  new HashSet();
          HashSet uniqRows =  new HashSet();
          for (int i = 0; i < inboundVV.size(); i++) {
            PricingListView inboundV = (PricingListView) inboundVV.get(i);
            String key = "ProprietaryList="+ inboundV.getListName() + ", DistSku="+inboundV.getDistSku() + ", DistUOM=" + inboundV.getDistUom();
            if (uniqRows.contains(key)){
              nonUniqRows.add(key);
            } else {
             uniqRows.add(key);
            }
          }
          if(nonUniqRows.size() > 0){
             throw new Exception(
                  "^clw^Item with the same Dist Sku and UOM already exists for Proprietary List: " +  nonUniqRows.toString()+"^clw^");
           }
        }

        public static class InboundPricingListData implements Serializable{
        String versionNum;
        String listName;
        String distSku;
        String distUom;

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


        public String toString(){
                return "InboundPriceListData: "+
                versionNum +
                ',' + listName +
                ',' + distSku +
                ',' + distUom ;
        }

}


}
