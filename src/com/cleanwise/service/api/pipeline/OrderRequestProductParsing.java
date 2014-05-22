package com.cleanwise.service.api.pipeline;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Category;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.cachecos.CacheKey;
import com.cleanwise.service.api.cachecos.Cachecos;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderItemActionDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.ShoppingDAO;
import com.cleanwise.service.api.dao.TradingPartnerDataAccess;
import com.cleanwise.service.api.meta.ProductDataMeta;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.FreightTable;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.util.BeanComparator;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.ContractItemDataVector;
import com.cleanwise.service.api.value.FreightTableCriteriaData;
import com.cleanwise.service.api.value.FreightTableCriteriaDataVector;
import com.cleanwise.service.api.value.FreightTableData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderHandlingItemView;
import com.cleanwise.service.api.value.OrderHandlingItemViewVector;
import com.cleanwise.service.api.value.OrderHandlingView;
import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemJoinData;
import com.cleanwise.service.api.value.OrderItemMetaData;
import com.cleanwise.service.api.value.OrderItemMetaDataVector;
import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.PreOrderData;
import com.cleanwise.service.api.value.PreOrderItemData;
import com.cleanwise.service.api.value.PreOrderItemDataVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.TradingPartnerInfo;


/**
 *
 * Description: Pipeline class. Does initial order item processing note :
 * OrderRequestProductParsing was moved from OrderRequestItemParsing
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class OrderRequestProductParsing implements OrderPipeline {
	private static final Category log = Category.getInstance(OrderRequestProductParsing.class);

    class DistributorCharges {
        private Integer    _distributorId;
        private BigDecimal _itemsAmount;
        private BigDecimal _freightAmt;
        private BigDecimal _handlingAmt;
        private BigDecimal _fuelSurchargeAmt;
        private BigDecimal _smallOrderFeeAmt;
        private BigDecimal _discountAmt;

        public DistributorCharges(Integer distributorId) {
            _distributorId = distributorId;
            _itemsAmount = new BigDecimal(0);
            _freightAmt = new BigDecimal(0);
            _handlingAmt = new BigDecimal(0);
            _fuelSurchargeAmt = new BigDecimal(0);
            _smallOrderFeeAmt = new BigDecimal(0);
            _discountAmt = new BigDecimal(0);
        }

        public Integer getDistributorId() {
            return _distributorId;
        }
        public void setDistributorId(int distributorId) {
            _distributorId = new Integer(distributorId);
        }

        public BigDecimal getItemsAmount() {
            return _itemsAmount;
        }
        public void setItemsAmount(BigDecimal itemsAmount) {
            _itemsAmount = (itemsAmount == null) ? new BigDecimal(0) : new BigDecimal(itemsAmount.doubleValue());
        }

        public BigDecimal getFreightAmt() {
            return _freightAmt;
        }
        public void setFreightAmt(BigDecimal freightAmt) {
            _freightAmt = (freightAmt == null) ? new BigDecimal(0) : new BigDecimal(freightAmt.doubleValue());
        }

        public BigDecimal getHandlingAmt() {
            return _handlingAmt;
        }
        public void setHandlingAmt(BigDecimal handlingAmt) {
            _handlingAmt = (handlingAmt == null) ? new BigDecimal(0) : new BigDecimal(handlingAmt.doubleValue());
        }

        public BigDecimal getFuelSurchargeAmt() {
            return _fuelSurchargeAmt;
        }
        public void setFuelSurchargeAmt(BigDecimal fuelSurchargeAmt) {
            _fuelSurchargeAmt = (fuelSurchargeAmt == null) ? new BigDecimal(0) : new BigDecimal(fuelSurchargeAmt.doubleValue());
        }

        public BigDecimal getSmallOrderFeeAmt() {
            return _smallOrderFeeAmt;
        }
        public void setSmallOrderFeeAmt(BigDecimal smallOrderFeeAmt) {
            _smallOrderFeeAmt = (smallOrderFeeAmt == null) ? new BigDecimal(0) : new BigDecimal(smallOrderFeeAmt.doubleValue());
        }

        public BigDecimal getDiscountAmt() {
            return _discountAmt;
        }
        public void setDiscountAmt(BigDecimal discountAmt) {
            if (discountAmt == null) {
                _discountAmt = new BigDecimal(0);
            } else {
                double discount = discountAmt.doubleValue();
                if (discount > 0.0) {
                    _discountAmt = new BigDecimal(-discount);
                } else {
                    _discountAmt = new BigDecimal(discount);
                }
            }
        }
    }

	/**
	 * Process this pipeline.
	 *
	 * @param OrderRequestData
	 *            the order request object to act upon
	 * @param Connection
	 *            a active database connection
	 * @param APIAccess
	 *
	 */
	public OrderPipelineBaton process(OrderPipelineBaton pBaton, OrderPipelineActor pActor, Connection pCon, APIAccess pFactory) throws PipelineException {
		try {

			pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);

			if (pBaton.getSimpleServiceOrderFl()) {
				log.info("CANCELED");
				return pBaton;
			}

            CachecosManager cacheManager = Cachecos.getCachecosManager();

            PreOrderData preOrderD = pBaton.getPreOrderData();
			String orderSourceCd = preOrderD.getOrderSourceCd();

			OrderData orderD = pBaton.getOrderData();

            int storeId = (orderD == null) ? 0 : orderD.getStoreId();

            PropertyUtil propUtil = new PropertyUtil(pCon);
            boolean priceIsSameAsCost = Utility.isTrue(propUtil.fetchValueIgnoreMissing(0, storeId, 
            		RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE));

			boolean historicalOrder = pBaton.isHistoricalOrderPreOrderProps();
			log.info("Historical Order Set to: " + historicalOrder);

			String skuType = preOrderD.getSkuTypeCd();
			String userName = pBaton.getUserName();
			int siteId = orderD.getSiteId();
			// Get contract
			int contractId = orderD.getContractId();
			ContractItemDataVector contractItemDV = getContractItems(contractId, pFactory);
			// Get catalog
			CatalogData catalogD = getCatalog(pBaton, pFactory, siteId, pCon);
			int catalogId = (catalogD == null) ? 0 : catalogD.getCatalogId();
			boolean getItemIdFl = true;
			if (RefCodeNames.SKU_TYPE_CD.CUSTOMER.equals(skuType) && catalogId <= 0) {
				getItemIdFl = false; // needs catalog id to getermine item id
			}
			// determine if we will be validateing the customer price
			boolean checkContractPrice = true;
			if (historicalOrder) {
				checkContractPrice = false;
			} else if (RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderSourceCd)) {
				if (!RefCodeNames.ORDER_TYPE_CD.BATCH_ORDER.equals(orderD.getOrderTypeCd())){
					
				int tpid = preOrderD.getTradingPartnerId();
				try {
					TradingPartner tradingPartnerEjb = pFactory.getTradingPartnerAPI();
					TradingPartnerInfo tpInfo = tradingPartnerEjb.getTradingPartnerInfo(tpid);
					TradingPartnerData tpd =tpInfo.getTradingPartnerData();
					if (tpd != null) {
						if (Utility.isSet(tpd.getValidateContractPrice()) && !Utility.isTrue(tpd.getValidateContractPrice())) {
							checkContractPrice = false;
						}
					}
					
					boolean useInboundAmountForCostAndPrice = tpInfo.isUseInboundAmountForCostAndPrice();
					if (useInboundAmountForCostAndPrice){
						priceIsSameAsCost = true;
					}	

				} catch (Exception e) {
					checkContractPrice = true;
					log.error("Error Looking up Trading Partner: " + tpid);
				}
			}
			}

			PreOrderItemDataVector reqItems = pBaton.getPreOrderItemDataVector();
			BeanComparator comparator = new BeanComparator(new String[]{"getLineNumber","getCustomerSku"});
			Collections.sort(reqItems,comparator);
			
			/*Object[] reqItemA = reqItems.toArray();
			for (int ii = 0; ii < reqItemA.length - 1; ii++) {
				boolean exitFl = true;
				for (int jj = 0; jj < reqItemA.length - ii - jj; jj++) {
					PreOrderItemData poiD1 = (PreOrderItemData) reqItemA[jj];
					PreOrderItemData poiD2 = (PreOrderItemData) reqItemA[jj + 1];
					if (poiD1.getLineNumber() > poiD2.getLineNumber()) {
						reqItemA[jj] = poiD2;
						reqItemA[jj + 1] = poiD1;
						exitFl = false;
					} else if (poiD1.getLineNumber() == poiD2.getLineNumber()) {
						String custSku1 = poiD1.getCustomerSku();
						if (custSku1 == null)
							custSku1 = "";
						String custSku2 = poiD2.getCustomerSku();
						if (custSku2 == null)
							custSku2 = "";
						int comp = custSku1.compareTo(custSku2);
						if (comp > 0) {
							reqItemA[jj] = poiD2;
							reqItemA[jj + 1] = poiD1;
							exitFl = false;
						}
					}
				}
				if (exitFl)
					break;
			}*/
			UpdateDistributorItemInfo updateDistributorItemInfoImpl = new UpdateDistributorItemInfo();
			OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
			comparator = new BeanComparator(new String[]{"getCustLineNum","getCustItemSkuNum"});
			Collections.sort(orderItemDV,comparator);
			/*Object[] orderItemA = orderItemDV.toArray();
			for (int ii = 0; ii < orderItemA.length - 1; ii++) {
				boolean exitFl = true;
				for (int jj = 0; jj < orderItemA.length - ii - jj; jj++) {
					OrderItemData oiD1 = (OrderItemData) orderItemA[jj];
					OrderItemData oiD2 = (OrderItemData) orderItemA[jj + 1];
					if (oiD1.getCustLineNum() > oiD2.getCustLineNum()) {
						orderItemA[jj] = oiD2;
						orderItemA[jj + 1] = oiD1;
						exitFl = false;
					} else if (oiD1.getCustLineNum() == oiD2.getCustLineNum()) {
						String custSku1 = oiD1.getCustItemSkuNum();
						if (custSku1 == null)
							custSku1 = "";
						String custSku2 = oiD2.getCustItemSkuNum();
						if (custSku2 == null)
							custSku2 = "";
						int comp = custSku1.compareTo(custSku2);
						if (comp > 0) {
							orderItemA[jj] = oiD2;
							orderItemA[jj + 1] = oiD1;
							exitFl = false;
						}
					}
				}
				if (exitFl)
					break;
			}*/

            ///
            TreeMap<Integer, DistributorCharges> distrChargesMap =
                new TreeMap<Integer, DistributorCharges>();

            ///
			BigDecimal sumPrice = new BigDecimal(0);
			IdVector cwItemlist = new IdVector();
			for (int ii = 0, jj = 0; ii < orderItemDV.size(); ii++) {
				OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
				oiD.setOrderLineNum(ii + 1);
				int custLineNum = oiD.getCustLineNum();
				String custSkuNum = oiD.getCustItemSkuNum();
				String custUom = oiD.getCustItemUom();
				BigDecimal serviceFee = oiD.getServiceFee();
				if (custSkuNum == null)
					custSkuNum = "";
				for (; jj < reqItems.size(); jj++) {
					PreOrderItemData poiD = (PreOrderItemData) reqItems.get(jj);
					int cln = poiD.getLineNumber();
					String csn = poiD.getCustomerSku();
					if (custLineNum == cln && custSkuNum.equals(csn)) {
						// Price info
						BigDecimal price = poiD.getPrice();
                        BigDecimal thisItemsPrice = new BigDecimal(0);
						if (price == null) {
							pBaton.addError(pCon, OrderPipelineBaton.INVALID_SKU_PRICE, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0, "pipeline.message.invalidSkuPrice", custSkuNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);

						} else {
							if(price.compareTo(new BigDecimal(0.0)) > 0){
								oiD.setCustContractPrice(price.setScale(10, BigDecimal.ROUND_HALF_UP));
							}else{
								oiD.setCustContractPrice(price);
							}
							
							int qty = poiD.getQuantity();
							oiD.setTotalQuantityOrdered(qty);
							/*
							 * Add the service fee to the cust_contract_price.Currently only for xpedx 
							 * so that the reports/budgets have the correct price.
							 * We need to have this in a general add_on_charge table for future.
							 */
					         
							
							BigDecimal newCustPrice = oiD.getCustContractPrice();
					        if(serviceFee!=null && serviceFee.compareTo(new BigDecimal(0.0))>0){
								newCustPrice = newCustPrice.add(serviceFee);
								oiD.setCustContractPrice(newCustPrice);
								thisItemsPrice = newCustPrice.multiply(new BigDecimal(qty));
							}else{
								thisItemsPrice = price.multiply(new BigDecimal(qty));
							}
							sumPrice = sumPrice.add(thisItemsPrice);
						}
                        // set customer item information.
						oiD.setCustItemShortDesc(poiD.getCustomerProductDesc());
						oiD.setCustItemUom(poiD.getCustomerUom());
						oiD.setCustItemPack(poiD.getCustomerPack());

						int itemId = poiD.getItemId();
						if (getItemIdFl && itemId <= 0) {
							try {
                                                            //  New lines. Bug 4556. YR
                                                            if (storeId == 1) {
                                                                itemId = ItemSkuMapping.mapToItemId(pCon,
                                                                                                    custSkuNum,
                                                                                                    custUom,
                                                                                                    RefCodeNames.SKU_TYPE_CD.CUSTOMER,
                                                                                                    4);
                                                            } else {
                                                            //  End new lines. Bug 4556. YR
                                                                itemId = ItemSkuMapping.mapToItemId(pCon,
                                                                                                    custSkuNum,
                                                                                                    custUom,
                                                                                                    skuType,
                                                                                                    catalogId);
                                                            }
 							} catch (Exception e) {
								pBaton.addError(pCon, OrderPipelineBaton.NO_ITEM_FOUND, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0, "pipeline.message.noItemFound", custSkuNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING, skuType, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
							}
						}
						if (itemId > 0) {
							oiD.setItemId(itemId);
							cwItemlist.add(new Integer(itemId));
						}
						
						//get tax info from pre order item if exists
						if(Utility.isSet(poiD.getTaxExempt())){
							
							String isTaxExempt = poiD.getTaxExempt();
							if(isTaxExempt.equals("F")){
								oiD.setTaxExempt("false");
								
								if(Utility.isSet(poiD.getTaxAmount())){
									oiD.setTaxAmount(poiD.getTaxAmount());
								}
								
							}else if(isTaxExempt.equals("T")){
								oiD.setTaxExempt("true");
							}
						}

						// Record the inventory level at the time of purchase.
						boolean inventoryItemFl = ("T".equals(poiD.getAsInventoryItem())) ? true : false;
						if (inventoryItemFl) {
							oiD.setInventoryParValue(poiD.getInventoryParValue());
							oiD.setInventoryQtyOnHand(poiD.getInventoryQtyOnHand());
						}

						if (Utility.isSet(poiD.getOrderItemActionCd())) {
							log.debug("adding order item action!");
							OrderItemActionData ackAction = OrderItemActionData.createValue();
							ackAction.setOrderId(oiD.getOrderId());
							ackAction.setOrderItemId(oiD.getOrderItemId());
							ackAction.setQuantity(poiD.getQuantity());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							if (Utility.isSet(preOrderD.getCustomerOrderDate())){
								java.util.Date orderDate = sdf.parse(preOrderD.getCustomerOrderDate());
								ackAction.setActionDate(orderDate);
								ackAction.setActionTime(orderDate);
							}else{
								ackAction.setActionDate(orderD.getOriginalOrderDate());
								ackAction.setActionTime(orderD.getOriginalOrderDate());
							}
							ackAction.setActionCd(poiD.getOrderItemActionCd());
							ackAction.setAddBy("system 9");
							OrderItemActionDataAccess.insert(pCon, ackAction);
						}

						// Product info
						if ((catalogId > 0 || historicalOrder) && itemId > 0) {
							ProductData pd = null;
							CatalogInformation catInfoEjb;
							try {
                                catInfoEjb = pFactory.getCatalogInformationAPI();
                                if (cacheManager != null && cacheManager.isStarted()) {
                                    CacheKey key = ProductDAO.getCacheKey(catalogId, itemId);
                                    try {
                                        pd = (ProductData) cacheManager.get(key);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (pd == null) {
                                        pd = catInfoEjb.getCatalogClwProduct(catalogId, itemId, 0,siteId, pBaton.getCategToCCView());
                                        try {
                                            cacheManager.put(key, pd, new ProductDataMeta(pd));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    pd = catInfoEjb.getCatalogClwProduct(catalogId, itemId, 0,siteId, pBaton.getCategToCCView());
                                }
							} catch (Exception e) {}
							if (pd == null && historicalOrder) {
								try {
									catInfoEjb = pFactory.getCatalogInformationAPI();
									pd = catInfoEjb.getProduct(itemId, pBaton.getCategToCCView());
								} catch (Exception e) {}
							}

							if (pd == null) {
								pBaton.addError(pCon, OrderPipelineBaton.ERROR_GETTING_PRODUCT_DATA, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0, "pipeline.message.errorGettingProductData", "" + itemId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING, "" + catalogId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
							}
							if (pd != null) {
								
								//bug STJ-3855
								if (historicalOrder && poiD.getDistributorId() != 0 &&
										pd.getCatalogDistrMapping() == null) {
							        DBCriteria dbc = new DBCriteria();
						            dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, poiD.getDistributorId());
						            dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, itemId);
						            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
						            ItemMappingDataVector mappingVector = ItemMappingDataAccess.select(pCon, dbc);
						            if (mappingVector.size() == 1) {
						            	pd.setCatalogDistrMapping((ItemMappingData) mappingVector.get(0));
						            }
								}
								
								// If the product has another customer SKU
								// configured, then save it in the orders.
								String custSku = pd.getActualCustomerSkuNum();
								if (Utility.isSet(custSku)) {
									// This is needed in the event someone
									// orders an item over the web for
									// which we must send out an EDI document.
									oiD.setCustItemSkuNum(custSku);
								} else {
									// The customer sku the is passed in with
									// the request
									// actually the system sku. So to make sure
									// that the
									// system sku is not displayed at the wrong
									// times we set
									// this to null if there is no customer sku.
									if (null != pd.getCatalogDistributor()) {// if item is not in the catalog, do not override the cust item sku
										oiD.setCustItemSkuNum(null);
									}
								}
								if (oiD.getCustItemShortDesc() == null) {
									String prodName = pd.getCustomerProductShortDesc();
									if (!Utility.isSet(prodName)) {
										prodName = pd.getCatalogProductShortDesc();
									}
									oiD.setCustItemShortDesc(prodName);
								}
								String manufSku = pd.getManufacturerSku();
								if (!Utility.isSet(manufSku)) {
									pBaton.addError(pCon, OrderPipelineBaton.NO_MANUFACTURER_SKU, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0, "pipeline.message.noManufSku", "" + itemId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
								}
								oiD.setManuItemSkuNum(manufSku);
								oiD.setManuItemShortDesc(pd.getManufacturerName());
								oiD.setManuItemMsrp(new BigDecimal(pd.getListPrice()));
								oiD.setManuItemUpcNum(pd.getUpc());
								oiD.setManuPackUpcNum(pd.getPkgUpc());

								log.debug("Setting itemSkuNum = "+pd.getSkuNum());
								oiD.setItemSkuNum(pd.getSkuNum());
								oiD.setItemShortDesc(pd.getShortDesc());
								oiD.setItemUom(pd.getUom());
								oiD.setItemPack(pd.getPack());
								oiD.setItemSize(pd.getSize());
								oiD.setCostCenterId(pd.getCostCenterId());

								// Save product properties
								OrderItemMetaDataVector oimDV = new OrderItemMetaDataVector();

								/*
								 * veronika. Only category name and spl should
								 * be saved //size String size = pd.getSize();
								 * if(Utility.isSet(size)) { OrderItemMetaData
								 * oimD = OrderItemMetaData.createValue();
								 * oimD.setAddBy(userName);
								 * oimD.setModBy(userName);
								 * oimD.setName(OrderItemJoinData._propertyNames[OrderItemJoinData.SIZE_IND]);
								 * oimD.setValue(size); oimDV.add(oimD); }
								 *
								 * //pack String pack = pd.getPack();
								 * if(Utility.isSet(pack)) { OrderItemMetaData
								 * oimD = OrderItemMetaData.createValue();
								 * oimD.setAddBy(userName);
								 * oimD.setModBy(userName);
								 * oimD.setName(OrderItemJoinData._propertyNames[OrderItemJoinData.PACK_IND]);
								 * oimD.setValue(pack); oimDV.add(oimD); }
								 *
								 * //pack String uom = pd.getUom();
								 * if(Utility.isSet(uom)) { OrderItemMetaData
								 * oimD = OrderItemMetaData.createValue();
								 * oimD.setAddBy(userName);
								 * oimD.setModBy(userName);
								 * oimD.setName(OrderItemJoinData._propertyNames[OrderItemJoinData.UOM_IND]);
								 * oimD.setValue(uom); oimDV.add(oimD); }
								 *
								 * //color String color = pd.getColor();
								 * if(Utility.isSet(color)) { OrderItemMetaData
								 * oimD = OrderItemMetaData.createValue();
								 * oimD.setAddBy(userName);
								 * oimD.setModBy(userName);
								 * oimD.setName(OrderItemJoinData._propertyNames[OrderItemJoinData.COLOR_IND]);
								 * oimD.setValue(color); oimDV.add(oimD); }
								 */

								// category name
								CatalogCategoryDataVector catDV = pd.getCatalogCategories();
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
								pBaton.addOrderItemMetaDataVector(ii + 1, oimDV);

								/*
								 * Add the service fee to the dist_price.Currently only for xpedx
								 * so that the reports/budgets have the correct price.
								 * We need to have this in a general add_on_charge table for future.
								 */

								if (historicalOrder){
									if (poiD.getDistributorId() > 0) {
										log.info("Trying to set the distributor information with historical order, historicalOrder: "+historicalOrder);
										//updateDistributorItemInfoImpl.process(oiD, 0, poiD.getDistributorId(), pBaton, pCon, false);
                                        updateDistributorItemInfoImpl.process(pCon,
                                                storeId,
                                                siteId,
                                                oiD,
                                                pd,
                                                0,
                                                poiD.getDistributorId(),
                                                pBaton,
                                                false);
                                        //oiD.setDistItemCost(poiD.getPrice());
                                        BigDecimal newDistPrice = null;
                                        //check if set cost price equal flag is set
                                        if(priceIsSameAsCost){
                                        	newDistPrice = oiD.getCustContractPrice();
                                        }else{
                                        	newDistPrice = poiD.getPrice();
                                        }
										
										if(serviceFee!=null && serviceFee.compareTo(new BigDecimal(0.0))>0){
											newDistPrice = newDistPrice.add(serviceFee);
										}
										oiD.setDistItemCost(newDistPrice);
										log.info("Setting dist item cost to: "+poiD.getPrice()+"::"+oiD.getDistItemCost());
									}
								} else {
									// distributor item information.
									int catalogDistId = 0;
									if (null != pd.getCatalogDistributor()) {
										catalogDistId = pd.getCatalogDistributor().getBusEntityId();
									}
                                    // overide the catalog information with what
                                    // is set int the
                                    // pre order item
                                    // this value is set in an 855 comming in
                                    log.info("Trying to set the distributor information with historical order");
                                    updateDistributorItemInfoImpl.process(pCon,
                                            storeId,
                                            siteId,
                                            oiD,
                                            pd,
                                            contractId,
                                            catalogDistId,
                                            pBaton,
                                            true);

                                    //check if price and cost equal flag is set
                                    if(priceIsSameAsCost){
                                    	oiD.setDistItemCost(oiD.getCustContractPrice());
                                    }

                                    BigDecimal cost = null;
									ContractItemData contractItemD = null;
									for (int kk = 0; kk < contractItemDV.size(); kk++) {
										ContractItemData ciD = (ContractItemData) contractItemDV.get(kk);
										if (ciD.getItemId() == itemId) {
											contractItemD = ciD;
											break;
										}
									}

                                    String productBundleValue = ShoppingDAO.getProductBundleValue(pCon, siteId);

                                    log.info("process()=> productBundleValue: " + productBundleValue);

                                    if (Utility.isSet(productBundleValue)) {
                                        cost = oiD.getDistItemCost();
                                        BigDecimal newDistCost = oiD.getDistBaseCost();
                                        if (serviceFee != null && serviceFee.compareTo(new BigDecimal(0.0)) > 0) {
                                            newDistCost = newDistCost.add(serviceFee);
                                            log.info("process()=> add service fee: " + serviceFee);
                                            cost = cost.add(serviceFee);
                                        }
                                        log.info("process()=> cost : " + cost );
                                        log.info("process()=> newDistCost : " + newDistCost);
                                         oiD.setDistBaseCost(newDistCost);
                                        // Set item cost
                                        if (cost != null && cost.doubleValue() > 0.0) {
                                            oiD.setDistItemCost(cost.setScale(8, BigDecimal.ROUND_HALF_UP));
                                        } else {
                                            oiD.setDistItemCost(new BigDecimal(0));
                                        }
                                    } else {

                                        if (contractItemD != null) {
                                        	
                                        	if(priceIsSameAsCost){
                                        		cost = oiD.getCustContractPrice();
                                        	}else{
                                        		cost = contractItemD.getDistCost();
                                        	}
                                            BigDecimal newDistCost = contractItemD.getDistBaseCost();
                                            if (serviceFee != null && serviceFee.compareTo(new BigDecimal(0.0)) > 0) {
                                                newDistCost = newDistCost.add(serviceFee);
                                                cost = cost.add(serviceFee);
                                            }
                                            //oiD.setDistBaseCost(contractItemD.getDistBaseCost());
                                            oiD.setDistBaseCost(newDistCost);
                                        }
                                        // Set item cost
                                        if (cost != null && cost.doubleValue() > 0.0) {
                                            oiD.setDistItemCost(cost.setScale(8, BigDecimal.ROUND_HALF_UP));
                                        } else {
                                            oiD.setDistItemCost(new BigDecimal(0));
                                        }

                                    }
									// Check item price if edi order
									if (RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderD.getOrderSourceCd())) {
										if (contractItemD == null) {
											pBaton.addError(pCon, OrderPipelineBaton.NO_CONTRACT_SKU_PRICE, null, RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW, oiD.getOrderLineNum(), 0, "pipeline.message.noContractItemInfo", "" + pd.getSkuNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING, "" + contractId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
										} else {
                                            BigDecimal contractPrice = Utility.isSet(productBundleValue)
                                                    ? oiD.getCustContractPrice()
                                                    : contractItemD.getAmount();
                                            if (contractPrice == null) {
												pBaton.addError(pCon, OrderPipelineBaton.NO_CONTRACT_SKU_PRICE, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0, "pipeline.message.noContractItemSkuPrice", "" + pd.getSkuNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING, "" + contractId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
											} else {
												if (price != null) {
													if (price.subtract(contractPrice).abs().doubleValue() > 0.005) {
														String messKey = "pipeline.message.contractItemDoesNotMatchPrice";
														//STJ-5604
														String mess = PipelineUtil.translateMessage(messKey, orderD.getLocaleCd(), new Integer(pd.getSkuNum()), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.INTEGER, new Integer(contractId), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.INTEGER, null, null, null, null);
														if (checkContractPrice) {
															pBaton.addError(pCon, OrderPipelineBaton.NO_CONTRACT_SKU_PRICE, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0, messKey, "" + pd.getSkuNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING, "" + contractId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
														} else {
															if (pBaton.getOrderData().getOrderId() > 0) {
																// log a property as the order has been entered
																OrderDAO.enterOrderProperty(pCon, RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE, "Contract Price", mess, pBaton.getOrderData().getOrderId(), 0, 0, 0, 0, 0, 0, pBaton.getUserName(), messKey, "" + pd.getSkuNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING, "" + contractId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
															} else {
																OrderPropertyData opd = OrderPropertyData.createValue();
																opd.setShortDesc("Contract Price");
																opd.setAddBy(pBaton.getUserName());
																opd.setModBy(pBaton.getUserName());
																opd.setValue(mess);
																opd.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
																opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
																opd.setMessageKey(messKey);
																opd.setArg0("" + pd.getSkuNum());
																opd.setArg0TypeCd(RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
																opd.setArg1("" + contractId);
																opd.setArg0TypeCd(RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
																pBaton.addOrderPropertyData(opd);
															}
														}
													}
												}// price != null

											}// else
										}// check if EDI order
									}

                                    ///
                                    Integer distributorIdObj = null;
                                    DistributorCharges distributorCharges = null;
                                    if (pd.getCatalogDistributor() != null) {
                                        distributorIdObj = new Integer(pd.getCatalogDistributor().getBusEntityId());
                                    }
                                    if (distributorIdObj != null) {
                                        if (distrChargesMap.containsKey(distributorIdObj)) {
                                            distributorCharges = (DistributorCharges)distrChargesMap.get(distributorIdObj);
                                        } else {
                                            distributorCharges = new DistributorCharges(distributorIdObj);
                                            distrChargesMap.put(distributorIdObj, distributorCharges);
                                        }
                                    }

                                    ///
                                    if (distributorCharges != null) {
                                        BigDecimal oldDistrAmount = distributorCharges.getItemsAmount();
                                        BigDecimal newDistrAmount = oldDistrAmount.add(thisItemsPrice);
                                        distributorCharges.setItemsAmount(newDistrAmount);

                                    }
								}
							} else { // No product invo
								oiD.setDistErpNum("0000");
								pBaton.addError(pCon, OrderPipelineBaton.NO_DISTRIBUTOR_FOR_SKU, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oiD.getOrderLineNum(), 0, "pipeline.message.noCatalogInfoForSku", "" + oiD.getItemSkuNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
							}
						}
						jj++;
						break;
					}
				} // for (; jj < reqItemA.length; jj++)
			}

			pBaton.setOrderItemDataVector(orderItemDV);

			//sumPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
			orderD.setTotalPrice(sumPrice);
			orderD.setOriginalAmount(sumPrice);
			// Additional charges for EDI orders
			if (RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderD.getOrderSourceCd()) && 
					!RefCodeNames.ORDER_TYPE_CD.BATCH_ORDER.equals(orderD.getOrderTypeCd())) {
				EDI_calculateOrderCharges(storeId, catalogId, pBaton, pFactory, distrChargesMap);
			}

			// Return
			pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
			return pBaton;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PipelineException(e.getMessage());
		} finally {
		}
	}

	// ---------------------------------------------------------------------------
	private CatalogData getCatalog(OrderPipelineBaton pBaton, APIAccess pFactory, int pSiteId, Connection pCon) throws Exception {
		CatalogInformation catInfEjb = null;
		try {
			catInfEjb = pFactory.getCatalogInformationAPI();
		} catch (Exception exc) {
			String msg = "EJB problem. ";
			throw new Exception(msg + exc.getMessage());
		}
		CatalogData catalogD = catInfEjb.getSiteCatalog(pSiteId);
		if (catalogD == null) {
			pBaton.addError(pCon, OrderPipelineBaton.NO_ACTIVE_CATALOG_FOUND, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0, "pipeline.message.noActiveCatalogFound", "" + pSiteId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
		}
		return catalogD;
	}

	// --------------------------------------------------------------------------
	private ContractItemDataVector getContractItems(int pContractId, APIAccess pFactory) throws Exception {
		if (pContractId <= 0)
			return new ContractItemDataVector();
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
		} catch (Exception exc) {
			String mess = "Error getting contract items for the contract. Contract id = " + pContractId + "  " + exc.getMessage();
			throw new Exception(mess);
		}
	}

	// --------------------------------------------------------------------------
	private void EDI_calculateOrderCharges(int storeId, int catalogId, OrderPipelineBaton pBaton,
        APIAccess pFactory, TreeMap<Integer, DistributorCharges> distrChargesMap) throws Exception {

        OrderData orderD = pBaton.getOrderData();
		int contractId = orderD.getContractId();
		if (contractId < 0)
			return;
		ShoppingServices shoppingServEjb = pFactory.getShoppingServicesAPI();
        FreightTable freightTableEjb = pFactory.getFreightTableAPI();

        /// Create map to catch the proper 'Freight Table' for every distributor
        TreeMap<Integer, FreightTableData> distFreightTables =
            new TreeMap<Integer, FreightTableData>();
        /// Create map to catch the proper 'Discount Table' for every distributor
        TreeMap<Integer, FreightTableData> distDiscountTables =
            new TreeMap<Integer, FreightTableData>();

        ///
        if (distrChargesMap != null && distrChargesMap.size() > 0) {
            Set<Integer> keys = distrChargesMap.keySet();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                Integer distributorId = (Integer)iterator.next();
                distFreightTables.put(distributorId, null);
                distDiscountTables.put(distributorId, null);
            }
        }

		// Calculate the misc and handling costs
		// according to the contract.
		// BigDecimal weightval = new BigDecimal(0);
		// BigDecimal freightAmt =
		// shoppingServEjb.getFreightAmt(contractId,orderD.getOriginalAmount(),weightval);

		// freightAmt = (freightAmt==null)? new BigDecimal(0):
		// freightAmt.setScale(2,BigDecimal.ROUND_HALF_UP);
		// BigDecimal miscAmt =
		// shoppingServEjb.getHandlingAmt(contractId,orderD.getOriginalAmount(),weightval);
		// miscAmt =(miscAmt==null)? new BigDecimal(0):
		// miscAmt.setScale(2,BigDecimal.ROUND_HALF_UP);

		OrderHandlingView orderHandlingVw = OrderHandlingView.createValue();
		orderHandlingVw.setTotalHandling(new BigDecimal(0));
		orderHandlingVw.setTotalFreight(new BigDecimal(0));
		orderHandlingVw.setContractId(contractId);
		orderHandlingVw.setSiteId(orderD.getSiteId());
		orderHandlingVw.setAccountId(orderD.getAccountId());
		orderHandlingVw.setAmount(orderD.getOriginalAmount());
		orderHandlingVw.setWeight(new BigDecimal(0));

		OrderHandlingItemViewVector orderHandlingItemVwV = new OrderHandlingItemViewVector();
		orderHandlingVw.setItems(orderHandlingItemVwV);
		OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
		for (Iterator iter = orderItemDV.iterator(); iter.hasNext();) {
			OrderItemData oiD = (OrderItemData) iter.next();
			OrderHandlingItemView ohiVw = OrderHandlingItemView.createValue();
			ohiVw.setItemId(oiD.getItemId());
			ohiVw.setPrice(oiD.getCustContractPrice());
			ohiVw.setWeight(new BigDecimal(0));
			ohiVw.setQty(oiD.getTotalQuantityOrdered());
			orderHandlingItemVwV.add(ohiVw);
		}

        ///
        /// Filling of the maps 'distFreightTables' and 'distDiscountTables' by 'Freight Tables' and by 'Discount Tables'
        calculateFreightTablesForDistributors(freightTableEjb, storeId, catalogId, distFreightTables);
        calculateDiscountTablesForDistributors(freightTableEjb, storeId, catalogId, distDiscountTables);

        /// Search any not empty 'Freight Table' in the map 'distFreightTables'
        boolean existsFreightTableForDistributors = false;
        if (distFreightTables.size() > 0) {
            Collection<FreightTableData> values = distFreightTables.values();
            Iterator<FreightTableData> iterator = values.iterator();
            while (iterator.hasNext()) {
                FreightTableData table = (FreightTableData)iterator.next();
                if (table != null) {
                    existsFreightTableForDistributors = true;
                    break;
                }
            }
        }
        /// Search any not empty 'Discount Table' in the map 'distDiscountTables'
        boolean existsDiscountTableForDistributors = false;
        if (distDiscountTables.size() > 0) {
            Collection<FreightTableData> values = distDiscountTables.values();
            Iterator<FreightTableData> iterator = values.iterator();
            while (iterator.hasNext()) {
                FreightTableData table = (FreightTableData)iterator.next();
                if (table != null) {
                    existsDiscountTableForDistributors = true;
                    break;
                }
            }
        }

        ///
        boolean useCatalogForCharges = true;
        if (existsFreightTableForDistributors || existsDiscountTableForDistributors) {
            useCatalogForCharges = false;
        }

        ///
        BigDecimal freightAmt = new BigDecimal(0);
        BigDecimal handlingAmt = new BigDecimal(0);
        BigDecimal fuelSurchargeAmt = new BigDecimal(0);
        BigDecimal smallOrderFeeAmt = new BigDecimal(0);
        BigDecimal discountAmt = new BigDecimal(0);

        ///
        if (useCatalogForCharges) {
            orderHandlingVw = shoppingServEjb.calcTotalFreightAndHandlingAmount(orderHandlingVw);
            freightAmt = orderHandlingVw.getTotalFreight();
            handlingAmt = orderHandlingVw.getTotalHandling();
            fuelSurchargeAmt = shoppingServEjb.getChargeAmtByCode(contractId, orderD.getOriginalAmount(), orderHandlingVw, RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);
            smallOrderFeeAmt = shoppingServEjb.getChargeAmtByCode(contractId, orderD.getOriginalAmount(), orderHandlingVw, RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);
            discountAmt = shoppingServEjb.getDiscountAmt(contractId, orderD.getOriginalAmount(), orderHandlingVw);

        } else {
            ///
            calculateChargesByDistributors(freightTableEjb, distFreightTables,
                distDiscountTables, distrChargesMap);

            ///
            if (distrChargesMap.size() > 0) {
                Collection<DistributorCharges> values = distrChargesMap.values();
                Iterator<DistributorCharges> iterator = values.iterator();
                while (iterator.hasNext()) {
                    DistributorCharges charges = (DistributorCharges)iterator.next();
                    if (charges != null) {
                        freightAmt = freightAmt.add(charges.getFreightAmt());
                        handlingAmt = handlingAmt.add(charges.getHandlingAmt());
                        fuelSurchargeAmt = fuelSurchargeAmt.add(charges.getFuelSurchargeAmt());
                        smallOrderFeeAmt = smallOrderFeeAmt.add(charges.getSmallOrderFeeAmt());
                        double discount = charges.getDiscountAmt().doubleValue();
                        if (discount > 0.0) {
                            discountAmt = discountAmt.add(new BigDecimal(-discount));
                        } else {
                            discountAmt = discountAmt.add(new BigDecimal(discount));
                        }

                    }
                }
            }

        }

        ///
        if (freightAmt == null) {
			freightAmt = new BigDecimal(0);
        }
        if (handlingAmt == null) {
			handlingAmt = new BigDecimal(0);
        }
        if (fuelSurchargeAmt == null) {
			fuelSurchargeAmt = new BigDecimal(0);
        }
        if (smallOrderFeeAmt == null) {
			smallOrderFeeAmt = new BigDecimal(0);
        }
        if (discountAmt == null) {
			discountAmt = new BigDecimal(0);
        }
        BigDecimal zeroValue = new BigDecimal(0);
	    if (discountAmt.compareTo(zeroValue) > 0) {
	    	discountAmt = discountAmt.negate();
	    }
        freightAmt = freightAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        handlingAmt = handlingAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        fuelSurchargeAmt = fuelSurchargeAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        smallOrderFeeAmt = smallOrderFeeAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        discountAmt = discountAmt.setScale(2, BigDecimal.ROUND_HALF_UP);

        ///
        orderD.setTotalFreightCost(freightAmt);
        orderD.setTotalMiscCost(handlingAmt);

	    log.info("***************SVC: discountAmt to store in the clw_order_meta table = " + discountAmt);
	    //Here I should put discount in the pBaton object, from which it will be
	    //later saved in the clw_order_meta table (in one of the next classes of the pipeline)
		String userName = pBaton.getUserName();
	    OrderMetaDataVector orderMetaDV = new OrderMetaDataVector();

	    //pBaton.setOrderMetaDataVector(orderMetaDV); // this was already done in the EdiOrderRequestParsing.java class

        if (Utility.isSet(fuelSurchargeAmt)) {
            OrderMetaData orderMetaD = OrderMetaData.createValue();
            orderMetaD.setAddBy(userName);
            orderMetaD.setName(RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);
            orderMetaD.setValue(fuelSurchargeAmt.toString());
            orderMetaDV.add(orderMetaD);
        }

        if (Utility.isSet(smallOrderFeeAmt)) {
            OrderMetaData orderMetaD = OrderMetaData.createValue();
            orderMetaD.setAddBy(userName);
            orderMetaD.setName(RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);
            orderMetaD.setValue(smallOrderFeeAmt.toString());
            orderMetaDV.add(orderMetaD);
        }

	    if (Utility.isSet(discountAmt)) {
	       OrderMetaData orderMetaD = OrderMetaData.createValue();
	       orderMetaD.setAddBy(userName); // should I put userName here or it will be done automatically somewhere in one of the next classes of the pipeline ?
	       orderMetaD.setName(RefCodeNames.CHARGE_CD.DISCOUNT);
	       orderMetaD.setValue(discountAmt.toString());
	       orderMetaDV.add(orderMetaD);
	    }

        /// Add charges to the pBaton object
        if (orderMetaDV.size() > 0) {
            pBaton.addOrderMetaDataVector(orderMetaDV);
        }

        log.info("*************SVC: pBaton.getOrderMetaDataVector() = " + pBaton.getOrderMetaDataVector());
	}

    private static void calculateFreightTablesForDistributors(FreightTable freightTableEjb,
        int storeId, int catalogId, TreeMap<Integer, FreightTableData> distFreightTables) throws Exception {
        if (distFreightTables == null) {
            return;
        }
        if (distFreightTables.isEmpty()) {
            return;
        }
        Set<Integer> keys = distFreightTables.keySet();
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            Integer key = (Integer)iterator.next();
            FreightTableData freightTable =
                freightTableEjb.getFreightTableByDistributorAndCatalog(storeId, catalogId, key.intValue());
            distFreightTables.put(key, freightTable);
        }
    }

    private static void calculateDiscountTablesForDistributors(FreightTable freightTableEjb,
        int storeId, int catalogId, TreeMap<Integer, FreightTableData> distDiscountTables) throws Exception {
        if (distDiscountTables == null) {
            return;
        }
        if (distDiscountTables.isEmpty()) {
            return;
        }
        Set<Integer> keys = distDiscountTables.keySet();
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            Integer key = (Integer)iterator.next();
            FreightTableData freightTable =
                freightTableEjb.getDiscountTableByDistributorAndCatalog(storeId, catalogId, key.intValue());
            distDiscountTables.put(key, freightTable);
        }
    }

    private static void calculateChargesByDistributors(FreightTable freightTableEjb,
        TreeMap<Integer, FreightTableData> distFreightTables,
        TreeMap<Integer, FreightTableData> distDiscountTables,
        TreeMap<Integer, DistributorCharges> chargesMap) throws Exception {

        if (chargesMap == null || chargesMap.size() == 0) {
            return;
        }

        Collection<DistributorCharges> values = chargesMap.values();
        Iterator<DistributorCharges> iterator = values.iterator();
        while (iterator.hasNext()) {
            DistributorCharges charges = (DistributorCharges)iterator.next();
            if (charges == null) {
                continue;
            }
            if (charges.getItemsAmount() == null) {
                continue;
            }
            double itemsAmount = charges.getItemsAmount().doubleValue();
            Integer distributorId = charges.getDistributorId();
            FreightTableData freightTable = (FreightTableData)distFreightTables.get(distributorId);
            FreightTableData discountTable = (FreightTableData)distDiscountTables.get(distributorId);
            if (freightTable == null && discountTable == null) {
                continue;
            }

            FreightTableCriteriaDataVector freightCriterias = null;
            FreightTableCriteriaDataVector fuelSurchargeCriterias = null;
            FreightTableCriteriaDataVector smallOrderFeeCriterias = null;
            FreightTableCriteriaDataVector discountCriterias = null;

            if (freightTable != null) {
                freightCriterias = freightTableEjb.getFreightTableCriteriasByChargeCd(
                    freightTable.getFreightTableId(), null);

                fuelSurchargeCriterias = freightTableEjb.getFreightTableCriteriasByChargeCd(
                    freightTable.getFreightTableId(), RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);

                smallOrderFeeCriterias = freightTableEjb.getFreightTableCriteriasByChargeCd(
                    freightTable.getFreightTableId(), RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);
            }
            if (discountTable != null) {
                discountCriterias = freightTableEjb.getFreightTableCriteriasByChargeCd(
                    discountTable.getFreightTableId(), RefCodeNames.CHARGE_CD.DISCOUNT);
            }

            if (freightCriterias != null) {
                String tableType = freightTable.getFreightTableTypeCd();
                charges.setFreightAmt(calculateFreightAmount(tableType, freightCriterias, itemsAmount));
            }
            if (freightCriterias != null) {
                String tableType = freightTable.getFreightTableTypeCd();
                charges.setHandlingAmt(calculateHandlingAmount(tableType, freightCriterias, itemsAmount));
            }
            if (fuelSurchargeCriterias != null) {
                String tableType = freightTable.getFreightTableTypeCd();
                charges.setFuelSurchargeAmt(calculateHandlingAmount(tableType, fuelSurchargeCriterias, itemsAmount));
            }
            if (smallOrderFeeCriterias != null) {
                String tableType = freightTable.getFreightTableTypeCd();
                charges.setSmallOrderFeeAmt(calculateHandlingAmount(tableType, smallOrderFeeCriterias, itemsAmount));
            }
            if (discountCriterias != null) {
                String tableType = freightTable.getFreightTableTypeCd();
                charges.setDiscountAmt(calculateDiscountAmount(tableType, discountCriterias, itemsAmount));
            }
        }
    }

    private static BigDecimal calculateFreightAmount(String tableType,
        FreightTableCriteriaDataVector criterias, double compareValue) {
        if (tableType == null) {
            return new BigDecimal(0);
        }
        if (criterias == null || criterias.size() == 0) {
            return new BigDecimal(0);
        }
        if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.POUND.equals(tableType) ||
            RefCodeNames.FREIGHT_TABLE_TYPE_CD.KILOGRAMME.equals(tableType)) {
            return new BigDecimal(0);
        }
        BigDecimal resultAmt = new BigDecimal(0);
        for (int i = 0; i < criterias.size(); ++i) {
            FreightTableCriteriaData crit = (FreightTableCriteriaData)criterias.get(i);
            if (crit == null) {
                continue;
            }
            if (!RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.IMPLIED.equalsIgnoreCase(crit.getRuntimeTypeCd())) {
                continue;
            }
            if (!RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equalsIgnoreCase(crit.getFreightCriteriaTypeCd())) {
                BigDecimal freightAmt = crit.getFreightAmount();
                if (freightAmt == null) {
                    continue;
                }
                BigDecimal lowerAmt = crit.getLowerAmount();
                BigDecimal higherAmt = crit.getHigherAmount();
                if (null == lowerAmt || compareValue >= lowerAmt.doubleValue()) {
                    if (null == higherAmt || compareValue <= higherAmt.doubleValue()) {
                        resultAmt = resultAmt.add(freightAmt);
                    }
                }
            }
        }
        return resultAmt;
    }

    private static BigDecimal calculateHandlingAmount(String tableType,
        FreightTableCriteriaDataVector criterias, double compareValue) {
        if (tableType == null) {
            return new BigDecimal(0);
        }
        if (criterias == null || criterias.size() == 0) {
            return new BigDecimal(0);
        }
        if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.POUND.equals(tableType) ||
            RefCodeNames.FREIGHT_TABLE_TYPE_CD.KILOGRAMME.equals(tableType)) {
            return new BigDecimal(0);
        }
        BigDecimal resultAmt = new BigDecimal(0);
        for (int i = 0; i < criterias.size(); ++i) {
            FreightTableCriteriaData crit = (FreightTableCriteriaData)criterias.get(i);
            if (crit == null) {
                continue;
            }
            if (!RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.IMPLIED.equalsIgnoreCase(crit.getRuntimeTypeCd())) {
                continue;
            }
            if (!RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equalsIgnoreCase(crit.getFreightCriteriaTypeCd())) {
                BigDecimal handlingAmt = crit.getHandlingAmount();
                if (handlingAmt == null) {
                    continue;
                }
                BigDecimal lowerAmt = crit.getLowerAmount();
                BigDecimal higherAmt = crit.getHigherAmount();
                if (null == lowerAmt || compareValue >= lowerAmt.doubleValue()) {
                    if (null == higherAmt || compareValue <= higherAmt.doubleValue()) {
                        resultAmt = resultAmt.add(handlingAmt);
                    }
                }
            }
        }
        return resultAmt;
    }

    private static BigDecimal calculateDiscountAmount(String tableType,
        FreightTableCriteriaDataVector criterias, double compareValue) {
        if (tableType == null) {
            return new BigDecimal(0);
        }
        if (criterias == null || criterias.size() == 0) {
            return new BigDecimal(0);
        }
        if (!RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS.equals(tableType) &&
            !RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE.equals(tableType)) {
            return new BigDecimal(0);
        }
        BigDecimal resultAmt = new BigDecimal(0);
        for (int i = 0; i < criterias.size(); ++i) {
            FreightTableCriteriaData crit = (FreightTableCriteriaData)criterias.get(i);
            if (crit == null) {
                continue;
            }
            if (!RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.IMPLIED.equalsIgnoreCase(crit.getRuntimeTypeCd())) {
                continue;
            }
            if (!RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equalsIgnoreCase(crit.getFreightCriteriaTypeCd())) {
                BigDecimal discountAmt = crit.getDiscount();
                if (discountAmt == null) {
                    continue;
                }
                if (discountAmt.doubleValue() > 0.0) {
                    discountAmt = new BigDecimal(-discountAmt.doubleValue());
                }
                BigDecimal lowerAmt = crit.getLowerAmount();
                BigDecimal higherAmt = crit.getHigherAmount();
                if (null == lowerAmt || compareValue >= lowerAmt.doubleValue()) {
                    if (null == higherAmt || compareValue <= higherAmt.doubleValue()) {
                        if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE.equals(tableType)) {
                            discountAmt = new BigDecimal(compareValue * discountAmt.doubleValue() / 100.00);
                        }
                        resultAmt = resultAmt.add(discountAmt);
                    }
                }
            }
        }
        return resultAmt;
    }

}
