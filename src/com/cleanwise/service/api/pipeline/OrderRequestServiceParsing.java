package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.sql.Connection;

/**
 * Title:
 * Description:  Pipeline class. Does initial order item processing
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         02.02.2007
 * Time:         17:59:24
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class OrderRequestServiceParsing  implements OrderPipeline {

    /**
     * Process this pipeline
     * @param pBaton OrderPipelineBaton
     * @param pActor OrderPipelineActor
     * @param pCon   a active database connection
     * @param pFactory  factory
     * @return pBaton
     * @throws PipelineException
     */
        public OrderPipelineBaton process(OrderPipelineBaton pBaton,
                    OrderPipelineActor pActor,
                    Connection pCon,
                    APIAccess pFactory)
        throws PipelineException
        {
        try{
          pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);

          if (!pBaton.getSimpleServiceOrderFl()) {
                return pBaton;
          }

          PreOrderData preOrderD = pBaton.getPreOrderData();
          String orderSourceCd = preOrderD.getOrderSourceCd();

          OrderData orderD = pBaton.getOrderData();
          
          int storeId = (orderD == null) ? 0 : orderD.getStoreId();
          PropertyUtil propUtil = new PropertyUtil(pCon);
          boolean priceIsSameAsCost = Utility.isTrue(propUtil.fetchValueIgnoreMissing(0, storeId, 
          		RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE));
          
          String orderStatusCd = orderD.getOrderStatusCd();
          String skuType = preOrderD.getSkuTypeCd();
          String userName = pBaton.getUserName();
          int siteId = orderD.getSiteId();
          //Get contract
          int contractId = orderD.getContractId();
          ContractItemDataVector contractItemDV =
                getContractItems(contractId,pFactory);
          //Get catalog
          CatalogData catalogD = getCatalog(pBaton,pFactory,siteId,pCon);
          int catalogId = (catalogD==null)?0:catalogD.getCatalogId();
          boolean getItemIdFl = true;
          if(RefCodeNames.SKU_TYPE_CD.CUSTOMER.equals(skuType) && catalogId<=0){
            getItemIdFl = false;  //needs catalog id to getermine item id
          }

          //determine if we will be validateing the customer price
          boolean checkContractPrice = true;

          PreOrderItemDataVector reqItems = pBaton.getPreOrderItemDataVector();
          Object[] reqItemA = reqItems.toArray();
          for(int ii=0; ii<reqItemA.length-1; ii++){
            boolean exitFl = true;
            for(int jj=0; jj<reqItemA.length-ii-jj; jj++){
              PreOrderItemData poiD1 = (PreOrderItemData) reqItemA[jj];
              PreOrderItemData poiD2 = (PreOrderItemData) reqItemA[jj+1];
              if(poiD1.getLineNumber()>poiD2.getLineNumber()){
                reqItemA[jj] = poiD2;
                reqItemA[jj+1] = poiD1;
                exitFl = false;
              } else if(poiD1.getLineNumber()==poiD2.getLineNumber()){
                String custSku1 = poiD1.getCustomerSku();
                if(custSku1==null) custSku1 = "";
                String custSku2 = poiD2.getCustomerSku();
                if(custSku2==null) custSku2 = "";
                int comp = custSku1.compareTo(custSku2);
                if(comp>0) {
                  reqItemA[jj] = poiD2;
                  reqItemA[jj+1] = poiD1;
                  exitFl = false;
                }
              }
            }
            if(exitFl) break;
          }
          UpdateDistributorItemInfo updateDistributorItemInfo = new UpdateDistributorItemInfo();
          OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
          Object[] orderItemA = orderItemDV.toArray();
          for(int ii=0; ii<orderItemA.length-1; ii++){
            boolean exitFl = true;
            for(int jj=0; jj<orderItemA.length-ii-jj; jj++){
              OrderItemData oiD1 = (OrderItemData) orderItemA[jj];
              OrderItemData oiD2 = (OrderItemData) orderItemA[jj+1];
              if(oiD1.getCustLineNum()>oiD2.getCustLineNum()){
                orderItemA[jj] = oiD2;
                orderItemA[jj+1] = oiD1;
                exitFl = false;
              } else if(oiD1.getCustLineNum()==oiD2.getCustLineNum()){
                String custSku1 = oiD1.getCustItemSkuNum();
                if(custSku1==null) custSku1 = "";
                String custSku2 = oiD2.getCustItemSkuNum();
                if(custSku2==null) custSku2 = "";
                int comp = custSku1.compareTo(custSku2);
                if(comp>0) {
                  orderItemA[jj] = oiD2;
                  orderItemA[jj+1] = oiD1;
                  exitFl = false;
                }
              }
            }
            if(exitFl) break;
          }
          BigDecimal sumPrice = new BigDecimal(0);
          IdVector cwItemlist = new IdVector();
          for(int ii=0,jj=0; ii<orderItemA.length; ii++){
            OrderItemData oiD = (OrderItemData) orderItemA[ii];
            oiD.setOrderLineNum(ii+1);
            int custLineNum = oiD.getCustLineNum();
            String custSkuNum = oiD.getCustItemSkuNum();
            if(custSkuNum==null) custSkuNum = "";
            for(; jj<reqItemA.length;jj++) {
              PreOrderItemData poiD = (PreOrderItemData) reqItemA[jj];
              int cln = poiD.getLineNumber();
              String csn = poiD.getCustomerSku();
              if(custLineNum==cln && custSkuNum.equals(csn)) {
                //Price info
                BigDecimal price = poiD.getPrice();
                if(price==null) {
                  pBaton.addError(pCon, OrderPipelineBaton.INVALID_SKU_PRICE,
                                  null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0,
                                  "pipeline.message.invalidSkuPrice",
                                  custSkuNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                } else {
                  oiD.setCustContractPrice (price.setScale(8,BigDecimal.ROUND_HALF_UP));
                  int qty = poiD.getQuantity();
                  oiD.setTotalQuantityOrdered(qty);
                  BigDecimal thisItemsPrice = new BigDecimal(0);
                  thisItemsPrice = price.multiply(new BigDecimal(qty));
                  sumPrice = sumPrice.add(thisItemsPrice);
                }

                // set customer item information.
                oiD.setCustItemShortDesc(poiD.getCustomerProductDesc());
                oiD.setCustItemUom(poiD.getCustomerUom());
                oiD.setCustItemPack(poiD.getCustomerPack());

                int itemId = poiD.getItemId();
                int assetId=poiD.getAssetId();


                if(getItemIdFl && itemId<=0) {
                  try {
                    itemId = ItemSkuMapping.mapToItemId(pCon, custSkuNum, skuType, catalogId);
                  } catch (Exception e) {
                    pBaton.addError(pCon, OrderPipelineBaton.NO_ITEM_FOUND, null,
                                    RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0,
                                    "pipeline.message.noItemFound",
                                    custSkuNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                                    skuType, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                  }
                }

                if(assetId<=0)
                {
                  try{}
                  catch(Exception e)
                  {
                    pBaton.addError(pCon, OrderPipelineBaton.ASSET_NOT_SET, null,
                                    RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0,
                                    "pipeline.message.assetNotSet",
                                    "" + oiD.getOrderLineNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                  }
                }
                if(itemId > 0) {
                  oiD.setItemId(itemId);
                  cwItemlist.add(new Integer(itemId));
                }
                if(assetId>0)
                {
                    oiD.setAssetId(assetId);
                }

                // Record the inventory level at the time of purchase.
                boolean inventoryItemFl = ("T".equals(poiD.getAsInventoryItem()))?true:false;
                if (inventoryItemFl) {
                  oiD.setInventoryParValue(poiD.getInventoryParValue());
                  oiD.setInventoryQtyOnHand(poiD.getInventoryQtyOnHand());
                }

                //Service info
                if(catalogId>0 && itemId>0) {
                  ServiceData serviceData = null;
                  try {
                    CatalogInformation catInfoEjb = pFactory.getCatalogInformationAPI();
                    serviceData = catInfoEjb.getServiceData(itemId,catalogId);
                  } catch (Exception e) {
                    pBaton.addError(pCon, OrderPipelineBaton.ERROR_GETTING_PRODUCT_DATA, null,
                                    RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0,
                                    "pipeline.message.errorGettingProductData",
                                    "" + itemId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                                    "" + catalogId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                  }
                  if(serviceData!=null) {
                   oiD.setCustItemSkuNum(null);

                   if (oiD.getCustItemShortDesc()==null) {
                       String prodName=null;
                       if(serviceData.getCatalogStructureData()!=null)
                       {
                           prodName = serviceData.getCatalogStructureData().getShortDesc();
                       }
                     if(!Utility.isSet(prodName)) {
                        prodName = serviceData.getItemData().getShortDesc();
                     }
                     oiD.setCustItemShortDesc(prodName);
                    }
                    String manufSku = "no";
                    if(!Utility.isSet(manufSku)) {
                      pBaton.addError(pCon, OrderPipelineBaton.NO_MANUFACTURER_SKU, null,
                                      RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0,
                                      "pipeline.message.noManufSku",
                                      "" + itemId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                    }


                    oiD.setItemSkuNum(serviceData.getItemData().getSkuNum());
                    oiD.setItemShortDesc(serviceData.getItemData().getShortDesc());
                    oiD.setCostCenterId(serviceData.getCostCenterId());

                    //Save service properties
                    OrderItemMetaDataVector oimDV = new OrderItemMetaDataVector();

                    //category name
                    CatalogCategoryDataVector catDV = serviceData.getCatalogCategoryDV();
                    if (catDV != null && catDV.size() > 0) {
                      CatalogCategoryData catD = (CatalogCategoryData) catDV.get(0);
                      String catName = catD.getCatalogCategoryShortDesc();
                      if (Utility.isSet(catName)) {
                        OrderItemMetaData oimD = OrderItemMetaData.createValue();
                        oimD.setAddBy(userName);
                        oimD.setModBy(userName);
                        oimD.setName(OrderItemJoinData._propertyNames[OrderItemJoinData.CATEGORY_NAME]);
                        oimD.setValue(catName);
                        oimD.setValueNum(catD.getCatalogCategoryId());
                        oimDV.add(oimD);
                      }
                    }
                    pBaton.addOrderItemMetaDataVector(ii+1, oimDV);

                    // distributor item information.
                    int catalogDistId = 0;
                    if (null != serviceData.getCatalogDistributor()) {
                      catalogDistId = serviceData.getCatalogDistributor().getBusEntityId();
                    }
                    updateDistributorItemInfo.process(oiD, contractId, catalogDistId, pBaton, pCon);
                    BigDecimal cost = null;
                    ContractItemData contractItemD = null;
                    for(int kk=0; kk<contractItemDV.size(); kk++) {
                      ContractItemData ciD = (ContractItemData) contractItemDV.get(kk);
                      if(ciD.getItemId()==itemId){
                        contractItemD = ciD;
                        break;
                      }
                    }
                    if (contractItemD != null) {
                    	
                    	if(priceIsSameAsCost){
                    		cost = oiD.getCustContractPrice();
                    	}else{
                    		cost = contractItemD.getDistCost();
                    	}
                       
                       oiD.setDistBaseCost(contractItemD.getDistBaseCost());
                    }
                    //Set item cost
                    if(cost!=null && cost.doubleValue() > 0.0) {
                       oiD.setDistItemCost
                            (cost.setScale(8,BigDecimal.ROUND_HALF_UP));
                    } else {
                       oiD.setDistItemCost(new BigDecimal(0));
                    }
                    //Check item price if edi order
                    if(RefCodeNames.ORDER_SOURCE_CD.EDI_850.
                                   equals(orderD.getOrderSourceCd())) {
                      if(contractItemD==null) {
                        pBaton.addError(pCon, OrderPipelineBaton.NO_CONTRACT_SKU_PRICE,
                                        null, RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW, oiD.getOrderLineNum(), 0,
                                        "pipeline.message.noContractItemInfo",
                                        "" + serviceData.getItemData().getSkuNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                                        "" + contractId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                      } else {
                        BigDecimal contractPrice = contractItemD.getAmount();
                        if(contractPrice==null) {
                          pBaton.addError(pCon, OrderPipelineBaton.NO_CONTRACT_SKU_PRICE,
                                          null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0,
                                          "pipeline.message.noContractItemSkuPrice",
                                          "" + serviceData.getItemData().getSkuNum(),
                                          RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                                          "" + contractId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                        } else {
                          if(price!=null) {
                            if(price.subtract(contractPrice).abs().doubleValue()>0.005) {
                              //String mess = "Contract item does not match requested price. Sku number = " +
                              //              serviceData.getItemData().getSkuNum() + ". Contract id = " + contractId;
                              String messKey = "pipeline.message.contractItemDoesNotMatchPrice";
                            //STJ-5604
                              String mess = PipelineUtil.translateMessage(messKey, pBaton.getOrderData().getLocaleCd(),
                                new Integer(serviceData.getItemData().getSkuNum()), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.INTEGER,
                                new Integer(contractId), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.INTEGER, null, null, null, null);
                                if(checkContractPrice){
                                  pBaton.addError(pCon, OrderPipelineBaton.NO_CONTRACT_SKU_PRICE,
                                                  null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0,
                                                  messKey,
                                                  "" + serviceData.getItemData().getSkuNum(),
                                                  RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                                                  "" + contractId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                                }else{
                                    if(pBaton.getOrderData().getOrderId() > 0){
                                      //log a property as the order has been entered
                                      OrderDAO.enterOrderProperty(pCon,
                                          RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
                                          "Contract Price",
                                          mess, pBaton.getOrderData().getOrderId(),
                                          0, 0, 0, 0, 0, 0, pBaton.getUserName(),
                                          messKey,
                                          "" + serviceData.getItemData().getSkuNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                                          "" + contractId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                                    }else{
                                      OrderPropertyData opd = OrderPropertyData.createValue();
                                      opd.setShortDesc("Contract Price");
                                      opd.setAddBy(pBaton.getUserName());
                                      opd.setModBy(pBaton.getUserName());
                                      opd.setValue(mess);
                                      opd.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                                      opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
                                      opd.setMessageKey(messKey);
                                      opd.setArg0("" + serviceData.getItemData().getSkuNum());
                                      opd.setArg0TypeCd(RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                                      opd.setArg1("" + contractId);
                                      opd.setArg0TypeCd(RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                                      pBaton.addOrderPropertyData(opd);
                                    }
                                }
                            }
                          }//price != null

                        }//else

                      }
                    }

                  } else { //No service invo
                    oiD.setDistErpNum("0000");
                    pBaton.addError(pCon, OrderPipelineBaton.NO_DISTRIBUTOR_FOR_SKU, null,
                                    RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0,
                                    "pipeline.message.noCatalogInfoForSku",
                                    "" + oiD.getItemSkuNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                  }
                }

                  //Asset info
                  if(assetId>0)
                  {
                   checkAssetInfo(pBaton,oiD.getOrderLineNum(),assetId,itemId,pFactory,pCon);
                  }
                jj++;
                break;
              }
            }
          }

          pBaton.setOrderItemDataVector(orderItemDV);


          //sumPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
          orderD.setTotalPrice(sumPrice);
          orderD.setOriginalAmount(sumPrice);
         pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
          return pBaton;
        }catch(Exception e){
           e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }finally{
        }
        }

    private void checkAssetInfo(OrderPipelineBaton pBaton,int line, int assetId,int serviceId, APIAccess pFactory, Connection pCon) {
        Asset assetEjb = null;
        try {
            assetEjb = pFactory.getAssetAPI();
            AssetSearchCriteria assetCrit=new AssetSearchCriteria();
            IdVector serviceIds = new IdVector();
            serviceIds.add(new Integer(serviceId));
            assetCrit.setAssetId(assetId);
            AssetDataVector result = assetEjb.getAssetDataByCriteria(assetCrit);
            if(result==null||(result!=null&&result.size()!=1)) throw new Exception();
        }
        catch (Exception e) {
          pBaton.addError(pCon, OrderPipelineBaton.NO_ASSET_INFO, null,
                          RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, line, 0,
                          "pipeline.message.noAssetInfo",
                          "" + line, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
        }
    }

    //---------------------------------------------------------------------------
        private CatalogData getCatalog(OrderPipelineBaton pBaton,APIAccess pFactory, int pSiteId, Connection pCon)
        throws Exception
        {
          CatalogInformation catInfEjb = null;
          try {
            catInfEjb = pFactory.getCatalogInformationAPI();
          } catch (Exception exc) {
              String msg = "EJB problem. ";
              throw new Exception(msg+exc.getMessage());
          }
          CatalogData catalogD = catInfEjb.getSiteCatalog(pSiteId);
          if(catalogD==null){
            pBaton.addError(pCon, OrderPipelineBaton.NO_ACTIVE_CATALOG_FOUND, null,
                            RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                            "pipeline.message.noActiveCatalogFound",
                            "" + pSiteId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
          }
          return catalogD;
        }
        //--------------------------------------------------------------------------
        private ContractItemDataVector getContractItems(int pContractId,APIAccess pFactory)
        throws Exception {
          if(pContractId<=0) return new ContractItemDataVector();
          Contract contractEjb = null;
          try {
            contractEjb = pFactory.getContractAPI();
          } catch (Exception exc) {
            String mess = "No contract Ejb access. " + exc.getMessage();
            throw new Exception(mess);
          }
          try {
            ContractItemDataVector contractItemDV = contractEjb.getItems(pContractId);
            return contractItemDV;
          }
          catch (Exception exc) {
            String mess = "Error getting contract items for the contract. Contract id = "+
              pContractId+ "  "+exc.getMessage();
            throw new Exception(mess);
          }
        }
      //--------------------------------------------------------------------------


    }

