
package com.cleanwise.service.api.pipeline;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.ShoppingDAO;
import com.cleanwise.service.api.dao.ProductDAO.DistItemInfo;

import java.sql.Connection;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Logger;


/**
 *Sets the distributor infomration.  This will setup any of the distributor item information based off the distrbutor.  So if the
 *distributor infomration can be changed somewhere in the ppeline this class needs to come afte those that can change it and it will
 *make all of the adjustments necessary.  Some of the data is already set in various other places in the pipeline, and that will be overitten
 *here and can safely be removed.  Any necessary exceptions will be handled in this class (missing dist sku etc).
 *This class does NOT set the cost as this is variabled based on how the distibutor was arived uppon.  It only deals with those attributes
 *contained in the clw_item_mapping table.
 *
 *Currently this is implemented as a helper class instead of it's own pipeline as the method of determining the distributor id is depandant
 *upon various criteria.  For example if the order is being routed then the distributor is determined by the routing logic, in the most basic
 *form the distributor is set via the site configuration.  It is possilbe that the contract id could just be used, but the order routing
 *seems to not enforce that the contract belong to the catalog, so there is no way to go from the contract to the catalog to the disributor.
 */
class UpdateDistributorItemInfo {

    private static final Logger log = Logger.getLogger(UpdateDistributorItemInfo.class);

    private HashMap distCache = new HashMap();

    private BusEntityData getDistBusEnt(Connection pCon, int pDistributorId) throws SQLException, DataNotFoundException{
        Integer key = new Integer(pDistributorId);
        BusEntityData dist;
        if(distCache.containsKey(key)){
            dist = (BusEntityData) distCache.get(key);
        }else{
            dist = BusEntityDataAccess.select(pCon,pDistributorId);
            distCache.put(key,dist);
        }
        return dist;
    }

    /**
     *Performs the actual action of setting the distributor item information
     */
    public void process(OrderItemData oiD, int pContractId, int pDistributorId, OrderPipelineBaton pBaton,Connection pCon) throws PipelineException {
    	process(oiD, pContractId, pDistributorId,  pBaton, pCon, true);
    }

    /**
     *Performs the actual action of setting the distributor item information
     */
    public void process(OrderItemData oiD, int pContractId, int pDistributorId, OrderPipelineBaton pBaton,Connection pCon, boolean validateCost) throws PipelineException {
        try{
            BusEntityData dist = null;

            try{
                dist = getDistBusEnt(pCon,pDistributorId);
            }catch(DataNotFoundException e){}

            if (null != dist) {
                oiD.setDistErpNum(dist.getErpNum());
                oiD.setDistItemShortDesc(dist.getShortDesc());
                //now go get the other bits of info for this item
                DistItemInfo distInfo = ProductDAO.getDistInfo(pCon,oiD.getItemId(), pDistributorId, pContractId);
                if (distInfo != null) {
                    process(pCon, oiD, distInfo, pBaton, validateCost);
                }
            }else {
                oiD.setDistErpNum("0000");
                String messKey = "pipeline.message.noCatalogDist";
                String mess = "No catalog distributor for sku: "+oiD.getItemSkuNum();
                pBaton.addError(pCon, OrderPipelineBaton.NO_DISTRIBUTOR_FOR_SKU,null,
                                RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW,oiD.getOrderLineNum(), 0,
                                messKey,
                                ""+oiD.getItemSkuNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
            }

        } catch(Exception exc) {
            throw new PipelineException(exc.getMessage());
        }
    }

    public void _process(Connection pCon,
                        int pStoreId,
                        int pSiteId,
                        OrderItemData pOrderItem,
                        ProductData pProductData,
                        int pContractId,
                        int pDistributorId,
                        OrderPipelineBaton pBaton) throws PipelineException {
        _process(pCon, pStoreId, pSiteId, pOrderItem, pProductData, pContractId, pDistributorId, pBaton, true);
    }

    public void _process(Connection pCon,
                        int pStoreId,
                        int pSiteId,
                        OrderItemData pOrderItem,
                        ProductData pProductData,
                        int pContractId,
                        int pDistributorId,
                        OrderPipelineBaton pBaton,
                        boolean pValidateCost) throws PipelineException {

        log.info("process()=> BEGIN");

        try{
            BusEntityData dist = null;

            try{
                dist = getDistBusEnt(pCon,pDistributorId);
                log.info("process()=> DistID: "+dist.getBusEntityId());
            }catch(DataNotFoundException e){
                log.info("process()=> ERROR : "+e.getMessage());
            }

            if (null != dist) {

                pOrderItem.setDistErpNum(dist.getErpNum());
                pOrderItem.setDistItemShortDesc(dist.getShortDesc());

                //now go get the other bits of info for this item

                PropertyUtil pu = new PropertyUtil(pCon);
                String storeType = pu.fetchValueIgnoreMissing(0, pStoreId, RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);

                log.info("process()=> storeType : "+storeType);

                DistItemInfo distInfo = ProductDAO.getDistInfo(pCon, storeType, pSiteId, pContractId, pProductData);
                log.info("process()=> distInfo : "+distInfo);
                if (distInfo != null) {
                    process(pCon, pOrderItem, distInfo, pBaton,  pValidateCost);
                }
            }else {
                pOrderItem.setDistErpNum("0000");
                String messKey = "pipeline.message.noCatalogDist";
                String mess = "No catalog distributor for sku: "+pOrderItem.getItemSkuNum();
                log.info("process()=> error : "+mess);
                pBaton.addError(pCon, OrderPipelineBaton.NO_DISTRIBUTOR_FOR_SKU,null,
                                RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW,pOrderItem.getOrderLineNum(), 0,
                                messKey,
                                ""+pOrderItem.getItemSkuNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
            }

        } catch(Exception exc) {
            throw new PipelineException(exc.getMessage());
        }
    }

   private void process(Connection pCon, OrderItemData pOrderItem, DistItemInfo pDistInfo, OrderPipelineBaton pBaton, boolean pValidateCost) {

        String finalOrderStatus = null;
        String errorCode = null;
        String note = "";
        String messKey = "";
        if (pDistInfo.mDistCost == null && pValidateCost) {
            pDistInfo.mDistCost = new BigDecimal("0");
            finalOrderStatus = RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW;
            messKey = "pipeline.message.noDistCost";
            note += "\n No Distributor cost. ";
            errorCode = OrderPipelineBaton.MISSING_DIST_COST;
        }

        if (pDistInfo.mSku == null ||
                pDistInfo.mSku.length() == 0) {
            pDistInfo.mSku = "";
            finalOrderStatus = RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW;
            messKey = "pipeline.message.noDistSku";
            note += "\n No Distributor SKU. ";
            errorCode = OrderPipelineBaton.MISSING_DIST_SKU;
        }

        /*if (pDistInfo.mUOM == null ||
                pDistInfo.mUOM.length() == 0) {
            pDistInfo.mUOM = "";
            finalOrderStatus = RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW;
            note += "\n No Distributor UOM. ";
            errorCode = OrderPipelineBaton.MISSING_DIST_UOM;
        }*/

        /*if (pDistInfo.mPack == null ||
          pDistInfo.mPack.length() == 0) {
              pDistInfo.mPack = "";
              finalOrderStatus = RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW;
              note += "\n No Distributor Pack. ";
        }*/
        pOrderItem.setDistItemSkuNum(pDistInfo.mSku);
        pOrderItem.setDistItemPack(pDistInfo.mPack);
        pOrderItem.setDistItemUom(pDistInfo.mUOM);
        pOrderItem.setDistItemCost(pDistInfo.mDistCost);
        boolean isStandardProductList = pDistInfo.mOnStdProductList;
        OrderItemMetaDataVector oimdv = pBaton.getOrderItemMetaDataVector(pOrderItem.getOrderLineNum());
        if (oimdv == null) {
            oimdv = new OrderItemMetaDataVector();
            pBaton.addOrderItemMetaDataVector(pOrderItem.getOrderLineNum(), oimdv);
        }
        OrderItemMetaData oimD = getOrderItemMetaDataForSPL(oimdv);

        if (isStandardProductList) {
            oimD.setValue(Boolean.TRUE.toString());
        } else {
            oimD.setValue(Boolean.FALSE.toString());
        }
        oimdv.add(oimD);
        if (RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equals(finalOrderStatus)) {
            pBaton.addError(pCon, errorCode, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,
                    pOrderItem.getOrderLineNum(), 0,
                    messKey);
            OrderDAO.updateOrderStatusCd(pCon, pOrderItem.getOrderId(), finalOrderStatus);
            pBaton.getOrderData().setOrderStatusCd(finalOrderStatus);
        }
    }


    private OrderItemMetaData getOrderItemMetaDataForSPL(OrderItemMetaDataVector oimdv){
        Iterator it = oimdv.iterator();
        while (it.hasNext()){
            OrderItemMetaData oim = (OrderItemMetaData)it.next();
            if(OrderItemJoinData._propertyNames[OrderItemJoinData.STANDARD_PRODUCT_LIST].equals(oim.getName())){
                return oim;
            }
        }
        OrderItemMetaData oimD = OrderItemMetaData.createValue();
        oimD.setAddBy("System");
        oimD.setModBy("System");
        oimD.setName(OrderItemJoinData._propertyNames[OrderItemJoinData.STANDARD_PRODUCT_LIST]);
        return oimD;
    }


    public void process(Connection pCon,
                         int pStoreId,
                         int pSiteId,
                         OrderItemData pOrderItem,
                         ProductData pProductData,
                         int pContractId,
                         int pCatalogDistId,
                         OrderPipelineBaton pBaton,
                         boolean pValidateCost) throws PipelineException {

        log.info("process()=> BEGIN " +
                ", \n pStoreId: " + pStoreId +
                ", \n pSiteId: " + pSiteId +
                ", \n pOrderItemId: " + pOrderItem.getOrderItemId() +
                ", \n pProductData: " + pProductData.getProductId() +
                ", \n pContractId: " + pContractId +
                ", \n  pCatalogDistId: " + pCatalogDistId +
                ", \n pValidateCost: " + pCatalogDistId);

        String productBundleValue = ShoppingDAO.getProductBundleValue(pCon, pSiteId);
        log.info("process()=> productBundleValue: " + productBundleValue);
        if (Utility.isSet(productBundleValue)) {
             log.info("process()=> exec new process");
            _process(pCon, pStoreId, pSiteId, pOrderItem, pProductData, pContractId, pCatalogDistId, pBaton, pValidateCost);
        } else {
           log.info("process()=> exec standart process");
           process(pOrderItem, pContractId, pCatalogDistId, pBaton, pCon, pValidateCost);
        }
    }

}
