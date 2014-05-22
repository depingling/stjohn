
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
import java.util.Map;
import java.io.Serializable;
import java.util.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.framework.ValueObject;

import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.PricingListView;
import com.cleanwise.service.api.value.PricingListViewVector;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.PriceListData;
import com.cleanwise.service.api.value.PriceListDataVector;

import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.dao.PriceListDataAccess;
import com.cleanwise.service.api.dao.PriceListAssocDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


public class InboundPollockPricingListLoader extends InboundNSCSapLoader {//InboundFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
	ArrayList parsedObjects = new ArrayList();
        int storeNum = 0;
        //int distributorId = 0;
        Map allPricingListsMap = null;
        HashSet nonUniqDBPricingListNames =  new HashSet();
        HashMap pricingListToRankMap = new  HashMap();
        HashMap siteToPriceListsMap = new HashMap();

        String loader = "PricingListLoader";
        String listType = RefCodeNames.PRICE_LIST_TYPE_CD.PRICE_LIST;
        String storeAssocCd = RefCodeNames.PRICE_LIST_ASSOC_CD.PRICE_LIST_STORE;
        Connection conn = null;

        public void translate(InputStream pIn, String pStreamType) throws Exception {

          PipeFileParser parser = new PipeFileParser();
          parser.parse(pIn);
          //parser.cleanUpResult();
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
                 log.info("doPostProcessing() => FAILED.Process time at : " + (System.currentTimeMillis() - startTime) + " ms" ) ;
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
                    // check: duplicate rows (different price)
                    checkUniqRowsForPricingList(plvV);
                   // check: is there more than 1 Pricing list having name equal List Name
                   checkUniqPricingListNames(listNames);
                   // check: Rank is the same for all items in Price List
                   checkUniqueRankAcrossPricingList();
                    // check: is there another price list having rank equals new rank from inbound file
                    checkPricingListWithSameRank (plvV);
                    // check: all items exist for Distributor, dist SKU and dist UOM
                    checkAllItemsExistAndUniq(plvV, distributorId);

                    IntegrationServices isEjb = APIAccess.getAPIAccess().getIntegrationServicesAPI();
                    int total = isEjb.processPricingList(plvV, storeNum, distributorId, RefCodeNames.PRICE_LIST_TYPE_CD.PRICE_LIST, loader );
                    log.info("doPostProcessing() => END. Processed: " + plvV.size()+" records. ");
                  } catch (Exception ex ){
                    setFail(true);
                    log.info("ERROR :" + Utility.getInitErrorMsg(ex));
                    log.info("doPostProcessing() => FAILED.Process time at : "
                    + (System.currentTimeMillis() - startTime) + " ms" ) ;
                  }  finally {
                    closeConnection(conn);
                  }

                } else {
                  log.info("doPostProcessing() => End. NO RECORDS to process!" ) ;
                }

	}


	private PricingListView createPricingListView(InboundPricingListData priceList ){


                PricingListView plv = PricingListView.createValue();

                plv.setListName(priceList.getListName());
                plv.setRank((new Integer(priceList.getRank())).intValue());
                plv.setDistSku(priceList.getDistSku());
                plv.setDistUom(priceList.getDistUom());
                plv.setPrice(new BigDecimal(priceList.getPrice()));
                plv.setCustSku(priceList.getCustSku());
                int listId = 0;
                if (allPricingListsMap.containsKey(priceList.getListName())){
                  listId = ((Integer)allPricingListsMap.get(priceList.getListName())).intValue();
                }
                plv.setListId(listId);
                return plv;
	}

        private void checkUniqueRankAcrossPricingList () throws Exception{
          log.info("check: Rank is the same for all items in Price List") ;
          //  pricingListToRankMap has been populated in isValid() method
          if (this.pricingListToRankMap != null){
             Set keys = pricingListToRankMap.keySet();
             for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
               String listName = (String) iter.next();
               HashSet ranks = (HashSet)pricingListToRankMap.get(listName);
               if (ranks != null && ranks.size()> 1){
                 throw new Exception(
                      "^clw^There are records with different Rank :"+ranks.toString()+" with the same List Name: " + listName+"^clw^");
               }
             }
           }
        }

        private void checkPricingListWithSameRank (PricingListViewVector inboundVV) throws Exception {
          log.info("check: is there another price list having rank equals new rank from inbound file.");
          for (int i = 0; i < inboundVV.size(); i++) {
            PricingListView inboundV = (PricingListView)inboundVV.get(i);
            Set siteIds = siteToPriceListsMap.keySet();
            for (Iterator iter = siteIds.iterator(); iter.hasNext(); ) {
              Integer siteIdI = (Integer) iter.next();
              PriceListDataVector plDV = (PriceListDataVector)siteToPriceListsMap.get(siteIdI);
              for (int j = 0; j < plDV.size(); j++) {
                PriceListData plD =(PriceListData)plDV.get(j);
                if (inboundV.getRank()==plD.getRank() && inboundV.getListName()==plD.getShortDesc()){
                  throw new Exception(
                      "^clw^"+"One of related site already has Price List with new Rank. SiteId ="+siteIdI.toString()+"; Inbound List Name =" + inboundV.getListName()+ ", Rank =" +inboundV.getRank()+"^clw^");
                }
              }
            }
          }
        }

        private void checkUniqRowsForPricingList (PricingListViewVector inboundVV) throws Exception {
         log.info("check: is there duplicate rows with price list name and the same dist sku.");
         HashSet nonUniqRows =  new HashSet();
         HashSet uniqRows =  new HashSet();
         for (int i = 0; i < inboundVV.size(); i++) {
           PricingListView inboundV = (PricingListView) inboundVV.get(i);
           String key = "PriceList="+ inboundV.getListName() +", Rank="+inboundV.getRank()+ ", DistSku="+inboundV.getDistSku() + ", DistUOM=" + inboundV.getDistUom();
           if (uniqRows.contains(key)){
             nonUniqRows.add(key);
           } else {
            uniqRows.add(key);
           }
         }
         if(nonUniqRows.size() > 0){
            throw new Exception(
                 "^clw^Item with the same Dist Sku and UOM already exists for Price List: " +  nonUniqRows.toString()+"^clw^");
          }
       }

	private boolean isValid(InboundPricingListData pricingList, int line){
          boolean valid = true;
          boolean ok = true;
          //------
//          valid &= checkRequired(pricingList.getVersionNum(), "Version Num", line);
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
          String rank = pricingList.getRank();
          ok &= checkType(rank, "Rank",TYPE.INTEGER, true, line);
          if (ok && (rank.trim().length()>1 || !"12".contains(rank.trim()))){
              addError("Incorrect Rank =" + rank+" . Should be 1 or 2. " , line);
              ok = false;
          }
          if (ok){
            HashSet rankSet = null;
            if (pricingListToRankMap.containsKey(listName)){
              rankSet = (HashSet)pricingListToRankMap.get(listName);
            } else {
              rankSet = new HashSet();
              pricingListToRankMap.put(listName, rankSet);
            }
            rankSet.add(rank);
          }

          valid &=ok;
          //-------
          valid &= checkRequired(pricingList.getDistSku(), "Dist SKU", line);
          //-------
          valid &= checkRequired(pricingList.getDistUom(), "Dist UOM", line);
          //-------
          String price = pricingList.getPrice();
          ok = checkType(price, "Price", TYPE.BIG_DECIMAL, true, line);
          if (ok && (new BigDecimal(price)).doubleValue() < 0){
            addError("Price: '" + price + "' is negative.", line);
            ok = false;
          }
          valid &= ok;
          //-------
           String custSku =pricingList.getCustSku();
           ok &= checkType(custSku, "Customer SKU", TYPE.TEXT, false,line);
           if (ok && custSku.trim().length() > 255) {
               addError("Customer SKU: '" + custSku + "' is too long. Max length is 255 char.", line);
               ok = false;
           }
           valid &= ok;
          //--------
          return valid;
        }

      private Map getAllPricingListsMap (Connection conn) throws Exception{
       PreparedStatement pstmt =null;
       ResultSet rs = null;
       HashMap priceListMap = new HashMap();
       try {
        DBCriteria dbc = new DBCriteria();
         // get all sites for store
         String sql =  "select bus_entity_id , short_desc, rank, pl.price_list_id from clw_price_list pl , clw_price_list_assoc pla where  " +
             " pl.price_list_id = pla.price_list_id "+
             " and price_list_type_cd ='"+listType+ "' "+
             " and price_list_assoc_cd ='"+storeAssocCd + "' " +
             " and bus_entity_id =  " + storeNum ;

/*            "(select BUS_ENTITY_ID from clw_bus_entity be , clw_bus_entity_assoc acc , clw_bus_entity_assoc site " +
             "where   ACC.BUS_ENTITY2_ID =" + storeNum +
             "  and ACC.BUS_ENTITY1_ID = SITE.BUS_ENTITY2_ID "+
             "  and ACC.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+ "' "+
             "  and SITE.BUS_ENTITY_ASSOC_CD = '" +RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+ "' "+
             "  and BE.BUS_ENTITY_ID = SITE.BUS_ENTITY1_ID "+
             "  and BE.BUS_ENTITY_TYPE_CD = '"+RefCodeNames.BUS_ENTITY_TYPE_CD.SITE+"') ";
*/
         // get all pricing lists for these sites
         log.info("getAllPricingListsMap() ===> sql =" + sql);
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         PriceListDataVector allPriceListsV = new PriceListDataVector();
         while (rs.next()) {
           Integer siteIdI = new Integer(rs.getInt(1));
           PriceListData plD = PriceListData.createValue();
           plD.setShortDesc(rs.getString(2));
           plD.setRank(rs.getInt(3));
           plD.setPriceListId(rs.getInt(4));
           if (!priceListMap.containsKey(plD.getShortDesc())){
             allPriceListsV.add(plD);
             priceListMap.put(plD.getShortDesc(),new Integer(plD.getPriceListId()));
             if (!siteToPriceListsMap.containsKey(siteIdI)) {
               siteToPriceListsMap.put(siteIdI, allPriceListsV);
             }
           } else {
             nonUniqDBPricingListNames.add(plD.getShortDesc());
           }
         }
        } catch (Exception ex){
          throw new Exception("^clw^"+"getAllPricingListsMap() ==>  Exception : " + ex.getMessage()+"^clw^");
        } finally{
          pstmt.close();
          rs.close();
        }
         return priceListMap;
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
          log.info("check: is there more than 1 Pricing list having name equal List Name.");
          HashSet nonUniqListNames =  new HashSet();
          for (Iterator iter = listNames.iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            if (nonUniqDBPricingListNames.contains(name)){
              nonUniqListNames.add(name);
            }
          }
          if(nonUniqListNames.size() > 0){
            throw new Exception(
                 "^clw^There are more than one Price List having name equal List Name in the store. List Name(s) :" +  nonUniqListNames.toString()+"^clw^");
          }
        }

        private void checkAllItemsExistAndUniq (PricingListViewVector priceListVector, int distId) throws Exception {
          log.info("check: all items exist for Distributor, dist SKU and dist UOM.");
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

        public static class InboundPricingListData implements Serializable{
        String versionNum;
        String listName;
        String rank;
        String distSku;
        String distUom;
        String price;
        String custSku;

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
        public String getRank() {
                 return rank;
         }
         public void setRank(String rank) {
                 this.rank = rank;
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

        public String getPrice() {
                return price;
        }
        public void setPrice(String price) {
                this.price = price;
        }
        public void setCustSku(String custSku) {
                 this.custSku = custSku;
         }

         public String getCustSku() {
                 return custSku;
         }

        public String toString(){
                return "InboundPriceListData: "+
                versionNum +
                ',' + listName +
                ',' + rank +
                ',' + distSku +
                ',' + distUom +
                ',' + price +
                ',' + custSku ;
        }

}


}
