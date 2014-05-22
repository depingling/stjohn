package com.cleanwise.service.api.util;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.CostCenterDataAccess;
import com.cleanwise.service.api.dao.InventoryLevelDAO;
import com.cleanwise.service.api.dao.InventoryLevelDataAccess;
import com.cleanwise.service.api.dao.InventoryOrderQtyDataAccess;
import com.cleanwise.service.api.dao.OrderGuideStructureDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.dao.ShoppingDAO;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CorpOrderCacheView;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CustomerOrderRequestData;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.InventoryItemsData;
import com.cleanwise.service.api.value.InventoryLevelData;
import com.cleanwise.service.api.value.InventoryLevelDataVector;
import com.cleanwise.service.api.value.InventoryLevelSearchCriteria;
import com.cleanwise.service.api.value.InventoryLevelView;
import com.cleanwise.service.api.value.InventoryLevelViewVector;
import com.cleanwise.service.api.value.InventoryOrderQtyData;
import com.cleanwise.service.api.value.InventoryOrderQtyDataVector;
import com.cleanwise.service.api.value.OrderGuideStructureData;
import com.cleanwise.service.api.value.OrderGuideStructureDataVector;
import com.cleanwise.service.api.value.OrderHandlingItemView;
import com.cleanwise.service.api.value.OrderHandlingItemViewVector;
import com.cleanwise.service.api.value.OrderHandlingView;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.ProcessOrderResultData;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteInventoryConfigView;
import com.cleanwise.service.api.value.SiteInventoryConfigViewVector;
import com.cleanwise.service.api.value.SiteInventoryInfoView;
import com.cleanwise.service.api.value.SiteInventoryInfoViewVector;
import com.cleanwise.service.api.value.UserData;

 public class  CorporateOrderManager {
 
 private static final Logger log = Logger.getLogger(CorporateOrderManager.class);
///////////////////////////////////////// CORPORATE ORDER MANAGER ///////////////////
	    private Connection mConn;
		private Boolean mSpecificDatesFl;
		public Date mCutoffDate;
		public Integer mInventoryPeriod;
		public String mStoreType;		
		public AccountData mAccount;
		private Integer mAccountId;
		private FiscalCalenderView mFiscalCalendar;

		public Boolean mUsePhysicalInventoryCart;
		public BigDecimal mAutoOrderFactor;
        public SiteData mSite;
        public UserData mUser;
        public InventoryOrderQtyLogOperator mOrderQtyLog;
		public Integer mCatalogId;
		public Integer mContractId;
		public Integer mOrderGuideId;
		public Integer mStoreId;
		public HashMap mAccountInventoryItems;
		public HashMap mCatalogProducts;
		//
		
		public InventoryLevelViewVector mInventoryLevel;
        public ShoppingCartData mCart;
		public String mLogStr = null;
		
        

        
        private static final String LAST_CUTOFF_DATE = "LAST_CUTOFF_DATE";

        public  static final int DATE_RANGE = 0; // day
        public  static final int BEGIN_NEXT_PROCESS_DAY = 6;// hour
        private static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
        private static final String TIME_FORMAT = "hh:mm a";
        private static final String Y = "Y";
        private static final String N = "N";
		

	public CorporateOrderManager(){
	}
		
     public void init(Connection pConn, CorpOrderCacheView pCorpOrderCache)
	 throws Exception
		{

	        mConn = pConn;
			mSpecificDatesFl = pCorpOrderCache.getSpecificDatesFl();
			mCutoffDate = pCorpOrderCache.getCutoffDate();
			mInventoryPeriod = pCorpOrderCache.getInventoryPeriod();
			mStoreType = pCorpOrderCache.getStoreType();		
			mAccount = pCorpOrderCache.getAccount();
			mAccountId = mAccount.getBusEntity().getBusEntityId();
			mFiscalCalendar = pCorpOrderCache.getFiscalCalendar();
			mAutoOrderFactor = pCorpOrderCache.getAutoOrderFactor();
			mUsePhysicalInventoryCart = pCorpOrderCache.getUsePhysicalInventoryCart();

			Integer siteIdI = pCorpOrderCache.getSiteId();
			APIAccess factory = new APIAccess();
			Site siteEjb = factory.getSiteAPI();
			mSite = siteEjb.getSite(siteIdI.intValue());

			mOrderGuideId = pCorpOrderCache.getOrderGuideId();
			mUser = pCorpOrderCache.getUser();
			mCatalogId = mSite.getContractData().getCatalogId();
			mContractId = mSite.getContractData().getContractId();
			mStoreId = pCorpOrderCache.getStoreId();
			mAccountInventoryItems = pCorpOrderCache.getAccountInventoryItems();
			mCatalogProducts = pCorpOrderCache.getCatalogProducts();


            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		    addLog("###### Site "+mSite.getBusEntity().getShortDesc()+ " ("+
			    mSite.getBusEntity().getBusEntityId()+")" + " Cutoff Date =" + sdf.format(mCutoffDate));
		
		}

	private void addLog(String mess) {
		log.info("ADD TO LOG: "+mess);	
		if(mLogStr!=null) {
		   mLogStr += "\n\r"+mess;
		} else {
		   mLogStr = mess;
		}
		
    }	
	public String getLog() {
		return mLogStr;
	}
	
//999999999999999999999999999999999999999999999999999
    public String placeCorporateOrder()
        throws Exception {
		
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        //String processLog = "";
		//processLog = "Placing Corporate Order: siteId=" + mSite.getSiteId();
		//processLog += " OrdercutoffDate =" + sdf.format(mCutoffDate);
		//addLog(processLog);
		Date currDate = new Date();
		if(currDate.before(mCutoffDate)) {
			addLog("Error. Attempt to process future schedule. Cutoff Date is after current date");
			return mLogStr;
		}
		boolean existFl = doesOrderExist(); 
		if (!existFl) {
            InventoryLevelSearchCriteria ilSearchCriteria = new InventoryLevelSearchCriteria();
            ilSearchCriteria.setSiteIds(Utility.toIdVector(mSite.getSiteId()));
            ilSearchCriteria.setNumPeriods(FiscalCalendarUtility.getNumberOfBudgetPeriods(mFiscalCalendar));       
            //ilSearchCriteria.setItems(itemIds);			
			mInventoryLevel = InventoryLevelDAO.getInvLevelViewCollections(mConn, ilSearchCriteria); 
			mCart = getInvShoppingCart();
			InventoryOrderQtyDataVector inventoryOrderQtyDV = constructQtyLogCollection();
			mOrderQtyLog = new InventoryOrderQtyLogOperator(inventoryOrderQtyDV);
			setupAutoOrder();
			
			placeOrder();              
		}
        return mLogStr;
    }

    private boolean doesOrderExist() throws Exception {
            Calendar cal = Calendar.getInstance();
            cal.setTime(mCutoffDate);
//            cal.add(Calendar.HOUR, -18);
//            Calendar begTimeRange = ((Calendar) cal.clone());
//            Calendar endTimeRange = ((Calendar) cal.clone());
//            begTimeRange.add(Calendar.HOUR, -18);
//            endTimeRange.add(Calendar.HOUR, 48); 
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String sql = "SELECT order_id, add_date FROM CLW_ORDER o WHERE SITE_ID = " + mSite.getSiteId() +
                              " AND ADD_DATE >= TO_DATE('" + sdf.format(cal.getTime()) + "','YYYY-MM-DD HH24:MI') " +
//                              " AND ADD_DATE <= TO_DATE('" + sdf.format(endTimeRange.getTime()) + "','YYYY-MM-DD HH24:MI') " +
                              " AND (ORDER_SOURCE_CD='" + RefCodeNames.ORDER_SOURCE_CD.INVENTORY + "'" +
                              " OR  exists(select 1 from clw_pre_order pro  where" +
                              " o.pre_order_id = pro.pre_order_id" +
                              " and pro.ORDER_SOURCE_CD='" + RefCodeNames.ORDER_SOURCE_CD.INVENTORY + "'))";
            log.info("SQL:"+sql);
            Statement stmt = mConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
			String retStr = null;
			boolean existFl = false;
            while (rs.next()) {
                int orderId = rs.getInt(1);
				Date addDate = rs.getDate(2);
				existFl = true;
				if(retStr==null) {
					retStr = "Order(s) already exists. Order id: "+orderId+ "("+sdf.format(addDate)+")";
				} else {
					retStr += "; Order id: "+orderId+ "("+sdf.format(addDate)+")";
				}
				
            }
			addLog(retStr);
            rs.close();
            stmt.close();
            return existFl;

        }
		

        public void setupAutoOrder() throws DataNotFoundException, Exception {
            HashMap hashInvCartItems = null; 
            for (Iterator iter=mInventoryLevel.iterator(); iter.hasNext();) {
                InventoryLevelView ilVw = (InventoryLevelView) iter.next();
                Integer id = ilVw.getInventoryLevelData().getItemId();
				if(!Utility.isSet(ilVw.getInventoryLevelData().getQtyOnHand()) &&
				   !Utility.isSet(ilVw.getInventoryLevelData().getOrderQty())) {
					
					if(hashInvCartItems == null) {
						hashInvCartItems = getHashMap(mCart.getInventoryItemsOnly());
					}
                    ShoppingCartItemData cartItemD = (ShoppingCartItemData) hashInvCartItems.get(ilVw.getInventoryLevelData().getItemId());
					if (cartItemD == null) {
						log.info("Item is not in the cart. Item id: "+ilVw.getInventoryLevelData().getItemId()+" Site id:" + mSite.getSiteId());
						continue;
					}
					log.info("Inventory Item Belongs to the cart. Item id: "+ilVw.getInventoryLevelData().getItemId()+" Site id:" + mSite.getSiteId());
					if (cartItemD.getAutoOrderEnable()) {
						mOrderQtyLog.addTo(cartItemD.getItemId(),InventoryOrderQtyDataAccess.AUTO_ORDER_APPLIED,Y);
						int quantity = -1;
						BigDecimal parVal = new BigDecimal(cartItemD.getInventoryParValue());
						mOrderQtyLog.addTo(cartItemD.getItemId(),
                            InventoryOrderQtyDataAccess.AUTO_ORDER_FACTOR,
                            cartItemD.getAutoOrderFactor());

						if (parVal.intValue() > 0) {
							if (cartItemD.getAutoOrderFactor() != null) {
								//The following logic rounds half up!
								//adding the .500001 will increase the value over a whole number:
								//.5 + .500001 = 1.000001
								//casting that to an int truncates (not rounds) the .000001...so
								//0 + .500001 = .500001 which truncates to 0.
								//.25 + .500001 = .75000001 which truncates to 0.

								double quantityDb =  (parVal.doubleValue() * cartItemD.getAutoOrderFactor().doubleValue());
								quantity = (int) (quantityDb + 0.500001); //rounding to upper integer
								cartItemD.setInventoryQtyOnHand((int) (parVal.doubleValue() - quantity));
								cartItemD.setInventoryQtyOnHandString(String.valueOf(cartItemD.getInventoryQtyOnHand()));
								cartItemD.setInventoryQtyIsSet(true);
								cartItemD.setQuantity(cartItemD.getInventoryOrderQty());
								cartItemD.setQuantityString(String.valueOf(cartItemD.getQuantity()));
							} else {
								// This is a site set up for orders for
								// a specified weekly interval.  If the on hand
								// value is not set, then set it to zero to
								// force an order for the par value configured
								// for the current order cycle.
								quantity = 0;
								cartItemD.setQuantity(quantity);
							}
						}
					}
				}
            }
            return;
        }
		
        private HashMap getHashMap(ShoppingCartItemDataVector cartItems) throws Exception {
            HashMap<Integer,ShoppingCartItemData> hashData = new HashMap<Integer,ShoppingCartItemData>();
            if (cartItems != null) {
                Iterator it = cartItems.iterator();
                while (it.hasNext()) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) it.next();
                    hashData.put(cartItem.getItemId(), cartItem);
                }
            }
            return hashData;
        }
		
/////////////////////////////		
						 
        private InventoryOrderQtyDataVector constructQtyLogCollection() {
		    ShoppingCartItemDataVector items = mCart.getItems();
			
            InventoryOrderQtyDataVector result  = new InventoryOrderQtyDataVector();
            if (items != null) {
                Iterator it = items.iterator();
                while (it.hasNext()) {
                    ShoppingCartItemData item = (ShoppingCartItemData) it.next();
                    InventoryOrderQtyData log = InventoryOrderQtyData.createValue();
                    log.setItemId(item.getItemId());
                    log.setAutoOrderApplied(N);
                    log.setItemType(item.getIsaInventoryItem() ?
                            RefCodeNames.PRODUCT_TYPE_CD.INVENTORY :
                            RefCodeNames.PRODUCT_TYPE_CD.REGULAR);
                    log.setBusEntityId(mSite.getBusEntity().getBusEntityId());
                    if (item.getIsaInventoryItem()) {
                        log.setEnableAutoOrder(item.getAutoOrderEnable()?Y:N);
                        log.setPar(item.getInventoryParValue());
                        log.setQtyOnHand(item.getInventoryQtyOnHandString());
                        log.setInventoryQty(item.getMonthlyOrderQtyString());
                        log.setPrice(new BigDecimal(item.getPrice()));
                        log.setCategory(item.getCategoryName());
                        log.setCostCenter(item.getProduct().getCostCenterName());
                        if(item.getProduct().getCatalogDistrMapping() != null){
                        	log.setDistItemNum(item.getProduct().getCatalogDistrMapping().getItemNum());
                        }
                    }
                    result.add(log);
                }
            }
            return result;
        }

 /*
        public boolean checkInventoryLog(Connection pCon, SiteData pSite) throws Exception, ParseException {



            DBCriteria dbc = new DBCriteria();



            dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pSite.getSiteId());

            dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LOG);

            dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, ScheduledOrderManager.LAST_CUTOFF_DATE);

            dbc.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

            dbc.addIsNotNull(PropertyDataAccess.CLW_VALUE);



            PropertyDataVector props = PropertyDataAccess.select(pCon, dbc);

            if (props.size() == 1) {



                PropertyData property = (PropertyData) props.get(0);

                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);



                Date cuttofDate = sdf.parse(property.getValue());



                GregorianCalendar propCutoffCal = new GregorianCalendar();

                GregorianCalendar currCutoffCal = new GregorianCalendar();

                propCutoffCal.setTime(cuttofDate);

                propCutoffCal = setBeginDay(propCutoffCal);

                currCutoffCal.setTime(pSite.getNextOrdercutoffDate());

                currCutoffCal = setBeginDay(currCutoffCal);


                return propCutoffCal.getTime().equals(currCutoffCal.getTime());

            }

            else if (props.size() > 1)           {

                throw new Exception("Multiple property("+RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LOG+") for site_id :"+pSite.getSiteId());

            }

            log.info("not exists data at inventory log");

            return false;

        }



        public GregorianCalendar getRunDateCalforScheduleProcess(Date pRunDate) throws Exception{
            GregorianCalendar runForDateCal = new GregorianCalendar();
            runForDateCal.setTime(pRunDate);
            return runForDateCal;

        }

*/



 
//############################## Inventory Shopping Cart#############################################

    private ShoppingCartData getInvShoppingCart() 
	throws Exception {

        ShoppingCartData invLevelCart = getInventoryCartIL();
        ShoppingCartData invOgCart    = getInventoryCartOG();
        ShoppingCartData resultCart   = mergeCart(invLevelCart, invOgCart);

        log.info("getInventoryShoppingCart => resultCart: " + resultCart);

        return resultCart;

    }

    public ShoppingCartData getInventoryCartIL()
    throws Exception {

        APIAccess factory = APIAccess.getAPIAccess();

        ShoppingServices shoppingServicesEjb = factory.getShoppingServicesAPI();
        ShoppingCartData inventoryCart = new ShoppingCartData();
		int siteId = mSite.getBusEntity().getBusEntityId();
		//Get inventory items		
        inventoryCart.setUser(mUser);
        inventoryCart.setSite(mSite);
        inventoryCart.setContractId(mContractId.intValue());
		inventoryCart.setCatalogId(mCatalogId.intValue());
		inventoryCart.setStoreType(mStoreType);

		HashMap invInfoItemsMap;
		SiteInventoryInfoViewVector inventoryItems = new SiteInventoryInfoViewVector();

        SiteInventoryConfigViewVector invConfItems = new SiteInventoryConfigViewVector();
		 //Get Site Inventory Items
		InventoryLevelSearchCriteria ilSearchCriteria = new InventoryLevelSearchCriteria();
		ilSearchCriteria.setSiteIds(Utility.toIdVector(siteId));
		ilSearchCriteria.setNumPeriods(FiscalCalendarUtility.getNumberOfBudgetPeriods(mFiscalCalendar));

		String sss = "";
		for (Iterator iter = mInventoryLevel.iterator(); iter.hasNext();) {
			InventoryLevelView ilv = (InventoryLevelView) iter.next();
			Integer itemIdI = ilv.getInventoryLevelData().getItemId();
			InventoryItemsData iid = (InventoryItemsData) mAccountInventoryItems.get(itemIdI);
			if(iid == null){				    
				if(sss.isEmpty()) {
					sss = " Some site inventory items do not belong to account inventory. Item ids: ";
				} else {
					sss += ", ";
				}
				sss += itemIdI;
				iter.remove();
				continue;
			}
			
			SiteInventoryConfigView siiv = SiteInventoryConfigView.createValue();
			siiv.setItemId(itemIdI.intValue());
			siiv.setSiteId(siteId);
			siiv.setAutoOrderItem(iid.getEnableAutoOrder());
			
			siiv.setParValues(Utility.getParValueMap(ilv.getParValues()));
			siiv.setQtyOnHand(ilv.getInventoryLevelData().getQtyOnHand());
			siiv.setOrderQty(ilv.getInventoryLevelData().getOrderQty());
			int sumOfPars = 0;
			for (Object oVal : siiv.getParValues().values()) {
				sumOfPars += Utility.intNN((Integer) oVal);
			}
			siiv.setSumOfAllParValues(sumOfPars);
			siiv.setModBy(ilv.getInventoryLevelData().getParsModBy());
			siiv.setModDate(ilv.getInventoryLevelData().getParsModDate());

			log.info("Site: "+mSite.getSiteId()+ " itemId: "+itemIdI+" => Order Quantity " + siiv.getOrderQty());
			log.info("Site: "+mSite.getSiteId()+ " itemId: "+itemIdI+" => OnHandQuantity " + siiv.getQtyOnHand());
			log.info("Site: "+mSite.getSiteId()+ " itemId: "+itemIdI+" => ParValue " + siiv.getParValues());
			
			invConfItems.add(siiv);				
		}

		addLog(sss);
			
		if (invConfItems.isEmpty()) {
			inventoryCart.setItems(new ShoppingCartItemDataVector());
			return inventoryCart;
		}
		
		//Set Product Info
		sss = "";
        for(Iterator iter = invConfItems.iterator(); iter.hasNext();){
			SiteInventoryConfigView sinv = (SiteInventoryConfigView) iter.next();
			int iId = sinv.getItemId();
			ProductData pd = (ProductData) mCatalogProducts.get(iId);
			if(pd == null) {
				if(sss.isEmpty()) {
					sss = " Requested inventory item(s) was not found in the catalog "+
						 mCatalogId +" item id(s): ";
				} else {
					sss += ", ";
				}
				sss += iId;
				continue;
			}
			sinv.setItemSku(pd.getSkuNum());
			sinv.setActualSku(getActualSkuNumber(pd, mStoreType));
			sinv.setItemDesc(pd.getShortDesc());
			sinv.setItemUom(pd.getUom());
			sinv.setItemPack(pd.getPack());
            //ProductData pdClone = (ProductData)pd.clone();
			sinv.setProductData(pd);

			///////////////////// Inventory Items ////////////////
			SiteInventoryInfoView sii = SiteInventoryInfoView.createValue();
			sii.setItemId(sinv.getItemId());
			sii.setSiteId(mSite.getSiteId());
			sii.setItemSku(sinv.getItemSku());
			sii.setItemDesc(sinv.getItemDesc());
			sii.setItemUom(sinv.getItemUom());
			sii.setItemPack(sinv.getItemPack());
			sii.setQtyOnHand(sinv.getQtyOnHand());
			sii.setOrderQty(sinv.getOrderQty());
			sii.setAutoOrderItem(sinv.getAutoOrderItem());
			sii.setProductData(sinv.getProductData());
			sii.setParValue(Utility.getIntValueNN(sinv.getParValues(),mInventoryPeriod.intValue()));
			sii.setSumOfAllParValues(sinv.getSumOfAllParValues());
			inventoryItems.add(sii);
		}
		invInfoItemsMap = Utility.toMapByItemId(inventoryItems);
		
		ArrayList<ProductData> invProductDataAL = new ArrayList<ProductData>();
		for(Iterator iter=inventoryItems.iterator(); iter.hasNext();) {
		    SiteInventoryInfoView sii = (SiteInventoryInfoView) iter.next();
			invProductDataAL.add(sii.getProductData());
		}

		ShoppingCartItemDataVector invShoppingItems = 
			shoppingServicesEjb.prepareShoppingItems(
				mStoreType,
				mSite,
				mCatalogId.intValue(),
				mContractId.intValue(),
				invProductDataAL,null);


		invShoppingItems = setupModernInventoryInfo(invShoppingItems, invInfoItemsMap);
		invShoppingItems = shoppingServicesEjb.setupMaxOrderQtyValues(mSite, invShoppingItems);

		inventoryCart.setItems(invShoppingItems);
		inventoryCart.setShoppingInfo(shoppingServicesEjb.updateShoppingInfo(siteId, 0, null));
		//List prodl = removedItemInfo(mConn, inventoryCart);
		List prodl = new ArrayList(); //looks like we do not need it for corporate orders
		inventoryCart.setRemovedProductInfo(prodl);

        
        return inventoryCart;
    }

    private ShoppingCartItemDataVector setupModernInventoryInfo(ShoppingCartItemDataVector invShoppingItems,                                                                
	      HashMap invInfoItemsMap) 
	throws Exception {

        if (invShoppingItems != null) {
            Iterator it = invShoppingItems.iterator();
            while (it.hasNext()) {
                ShoppingCartItemData shoppingItem = (ShoppingCartItemData) it.next();
                SiteInventoryInfoView invInfoData = (SiteInventoryInfoView) invInfoItemsMap.get(new Integer(shoppingItem.getItemId()));
                if (invInfoData != null) {

                    shoppingItem.setIsaInventoryItem(true);
                    shoppingItem.setInventoryParValue(invInfoData.getParValue());
                    shoppingItem.setInventoryParValuesSum(invInfoData.getSumOfAllParValues());
                    shoppingItem.setMonthlyOrderQtyString(invInfoData.getOrderQty());

                    String qtyOnHandStr = Utility.strNN(invInfoData.getQtyOnHand());
                    String orderQtyStr  = Utility.strNN(invInfoData.getOrderQty());
                    qtyOnHandStr = qtyOnHandStr.trim();
                    orderQtyStr  = orderQtyStr.trim();

                    //log.info("setupModernInventoryInfo => orderQtyStr: " + orderQtyStr + " qtyOnHandStr " + qtyOnHandStr);

                    int qtyOnHandInt = 0;
                    int orderQtyInt = 0;
                    boolean orderQtyIsSet = false;
                    boolean onHandQtyIsSet = false;

                    try {
                        qtyOnHandInt = Integer.parseInt(qtyOnHandStr);
                        shoppingItem.setInventoryQtyOnHand(qtyOnHandInt);
                        shoppingItem.setInventoryQtyOnHandString(qtyOnHandStr);
                        onHandQtyIsSet = true;
                    } catch (NumberFormatException exc) {
                        shoppingItem.setInventoryQtyOnHand(0);
                        shoppingItem.setInventoryQtyOnHandString(qtyOnHandStr);
                        onHandQtyIsSet = false;
                    }

                    try {
                        orderQtyInt = Integer.parseInt(orderQtyStr);
                        shoppingItem.setQuantity(orderQtyInt);
                        shoppingItem.setQuantityString(orderQtyStr);
                        orderQtyIsSet = true;
                    } catch (NumberFormatException exc) {
                        shoppingItem.setQuantity(0);
                        shoppingItem.setQuantityString(orderQtyStr);
                        orderQtyIsSet = false;
                    }

                    shoppingItem.setAutoOrderFactor(mAutoOrderFactor);
                    shoppingItem.setAutoOrderEnable(Utility.isTrue(invInfoData.getAutoOrderItem()));

                    if (orderQtyIsSet) {
                        shoppingItem.setInventoryQtyIsSet(true);
                    } else if (onHandQtyIsSet) {
                        shoppingItem.setInventoryQtyIsSet(true);
                    } else {
                        shoppingItem.setInventoryQtyIsSet(false);
                    }
                    shoppingItem.setQtySourceCd(RefCodeNames.ORDER_ITEM_QTY_SOURCE.INVENTORY);
                }
            }
        }
        return invShoppingItems;
    }
    

    public ShoppingCartData getInventoryCartOG() throws Exception {

        APIAccess factory = APIAccess.getAPIAccess();
        ShoppingServices shoppingServicesEjb = factory.getShoppingServicesAPI();

        ShoppingCartData shoppingCartD = new ShoppingCartData();		
        shoppingCartD.setUser(mUser);
        shoppingCartD.setSite(mSite);
        shoppingCartD.setContractId(mContractId.intValue());
        shoppingCartD.setCatalogId(mCatalogId.intValue());
        shoppingCartD.setStoreType(mStoreType);
        shoppingCartD.setOrderGuideId(mOrderGuideId.intValue());
        shoppingCartD.setModBy(mUser.getUserName());
        shoppingCartD.setModDate(new Date());

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, mOrderGuideId.intValue());
        OrderGuideStructureDataVector ogsDV = OrderGuideStructureDataAccess.select(mConn, dbc);
		ProductDataVector pDV = new ProductDataVector();
		HashMap<Integer,OrderGuideStructureData> ogsHM = new HashMap<Integer,OrderGuideStructureData>();
		String sss = "";
		for(Iterator iter = ogsDV.iterator(); iter.hasNext(); ) {
			OrderGuideStructureData ogsD = (OrderGuideStructureData) iter.next();
			int itemId = ogsD.getItemId();
			ProductData pd = (ProductData) mCatalogProducts.get(itemId);
			if(pd == null) {
				if(sss.isEmpty()) {
					sss = " Requested order guide item(s) was not found in the catalog "+
						 mCatalogId +" item id(s): ";
				} else {
					sss += ", ";
				}
				sss += itemId;
				continue;
			}
//			pDV.add((ProductData)pd.clone());
			pDV.add(pd);
			ogsHM.put(itemId,ogsD);
		}
		if(pDV.size()==0) {
			shoppingCartD.setItems(new ShoppingCartItemDataVector());
			return shoppingCartD;
		}

		ShoppingCartItemDataVector ogShoppingItems = 
			shoppingServicesEjb.prepareShoppingItems(
				mStoreType,
				mSite,
				mCatalogId.intValue(),
				mContractId.intValue(),
				pDV,null);

        ogShoppingItems = ShoppingDAO.setActualSkus(mStoreType, ogShoppingItems);
		
		for(Iterator iter = ogShoppingItems.iterator(); iter.hasNext();) {
		    ShoppingCartItemData sciD = (ShoppingCartItemData) iter.next();
			int itemId = sciD.getItemId();
			OrderGuideStructureData ogsD = (OrderGuideStructureData) ogsHM.get(itemId);
			sciD.setQuantity(ogsD.getQuantity());
			if (ogsD.getQuantity() > 0) {
				sciD.setQuantityString(String.valueOf(ogsD.getQuantity()));
			} else {
				sciD.setQuantityString("");
			}
			
			sciD.setQtySourceCd(RefCodeNames.ORDER_ITEM_QTY_SOURCE.SHOPPING_CART);
			CatalogCategoryDataVector ccDV = sciD.getProduct().getCatalogCategories();
			if (ccDV.size() == 0) {
				sciD.setCategory(null);
			} else {
				CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
				sciD.setCategory(ccD);
			}						
		}

		ogShoppingItems = shoppingServicesEjb.setupMaxOrderQtyValues(mSite, ogShoppingItems);
        shoppingCartD.setItems(ogShoppingItems);

		shoppingCartD.setShoppingInfo(shoppingServicesEjb.updateShoppingInfo(mSite.getSiteId(), 0, null));
		//List prodl = removedItemInfo(mConn, shoppingCartD);
		List prodl = new ArrayList(); //looks like we do not need it for corporate orders
		shoppingCartD.setRemovedProductInfo(prodl);
        return shoppingCartD;
    }
	
    private ShoppingCartData mergeCart(ShoppingCartData invLevelCart, ShoppingCartData invOgCart) 
	throws Exception {

        ShoppingCartData resultCart = new ShoppingCartData();

		if (invLevelCart != null && invOgCart != null) {

			resultCart = invLevelCart;
			resultCart.addItems(invOgCart.getItems());
			resultCart.setCustomerComments(invOgCart.getCustomerComments());
			resultCart.addRemovedProductInfo(invOgCart.getRemovedProductInfo());

		} else if (invLevelCart != null) {
			resultCart = invLevelCart;
		} else if (invOgCart != null) {
			resultCart = invOgCart;
		}
        return resultCart;
    }

	private CustomerOrderRequestData prepareOrderRequest() 
	throws Exception {
		ShoppingServices scartEjb = APIAccess.getAPIAccess().getShoppingServicesAPI();
		User userEjb = APIAccess.getAPIAccess().getUserAPI();
		Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();

		CustomerOrderRequestData orderReq = new CustomerOrderRequestData();
		ShoppingCartItemDataVector cartItems = mCart.getItems();
		BigDecimal freightAmt;
		BigDecimal handlingAmt;

		//Freight
		OrderHandlingView frOrder = prepareFreight(scartEjb);
		freightAmt  = frOrder.getTotalFreight();
		handlingAmt = frOrder.getTotalHandling();
		freightAmt  = freightAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
		handlingAmt = handlingAmt.setScale(2, BigDecimal.ROUND_HALF_UP);

		orderReq.setContractId(mContractId.intValue());
		orderReq.setUserName(mUser.getUserName());
		orderReq.setUserId(mUser.getUserId());
		orderReq.setSiteId(mSite.getSiteId());
		orderReq.setAccountId(mAccountId.intValue());
		orderReq.setOrderSourceCd(RefCodeNames.SYS_ORDER_SOURCE_CD.INVENTORY);
		orderReq.setFreightCharge(freightAmt.doubleValue());
		orderReq.setHandlingCharge(handlingAmt.doubleValue());

		for (int ii = 0; ii < cartItems.size(); ii++) {
			ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(ii);
			int itemId = cartItem.getProduct().getItemData().getItemId();
			int clw_skunum = cartItem.getProduct().getItemData().getSkuNum();
			int qty = 0;
			if (cartItem.getIsaInventoryItem()) {
				String t2 = cartItem.getInventoryQtyOnHandString();
				if ((t2 == null || t2.length() == 0)
						&& cartItem.getInventoryParValue() > 0
						&& !Utility.isSet(cartItem.getQuantityString())) {
					// This item has been designated as an
					// inventory item for this site, but
					// the inventory on hand value has not
					// been updated.
					String msg =
							" Qty value not set for inventory item: "
									+ cartItem.description();
					log.info(msg);
					orderReq.addOrderNote(msg);
				}
				qty = cartItem.getInventoryOrderQty();
			} else {
				qty = cartItem.getQuantity();
			}
			log.info(" Item  : " + cartItem.getItemId());
			log.info(" qty  : " + qty);
			if (qty == 0) {
				continue;
			}
			mOrderQtyLog.addTo(cartItem.getItemId(),InventoryOrderQtyDataAccess.ORDER_QTY, new Integer(qty));
			double price = cartItem.getPrice();
			orderReq.addItemEntry(ii + 1, itemId, clw_skunum,
					qty, price,
					cartItem.getProduct().getUom(),
					cartItem.getProduct().getShortDesc(),
					cartItem.getProduct().getPack(),
					cartItem.getIsaInventoryItem(),
					cartItem.getInventoryParValue(),
					cartItem.getInventoryQtyOnHandString(),
					cartItem.getReSaleItem());
		}
// It looks like it was used for Early Release only - YK		
//		orderReq.setInventotyOrderQtyLog(getOrderQtyLog().values()); 
		return orderReq;
	}

	private OrderHandlingView prepareFreight(ShoppingServices scartEjb) 
	throws Exception {
		OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();
		ShoppingCartItemDataVector cartItems = mCart.getItems();
		OrderHandlingView frOrder = null;
		if (cartItems != null) {
			for (int ii = 0; ii < cartItems.size(); ii++) {
				ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(ii);
				OrderHandlingItemView frItem = OrderHandlingItemView.createValue();
				frItem.setItemId(cartItem.getProduct().getProductId());
				BigDecimal priceBD = new BigDecimal(cartItem.getPrice());
				priceBD = priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);
				frItem.setPrice(priceBD);
				int qty = 0;
				if (cartItem.getIsaInventoryItem()) {
					qty = cartItem.getInventoryOrderQty();
				} else {
					qty = cartItem.getQuantity();
				}
				frItem.setQty(qty);
				BigDecimal weight = null;
				String weightS = cartItem.getProduct().getShipWeight();
				try {
					weight = new BigDecimal(weightS);
				} catch (Exception exc) {
					log.info("WARNINIG: " + exc.getMessage());
				}
				frItem.setWeight(weight);
				frItems.add(frItem);
			}

			frOrder = OrderHandlingView.createValue();
			frOrder.setTotalHandling(new BigDecimal(0));
			frOrder.setTotalFreight(new BigDecimal(0));
			frOrder.setContractId(mContractId.intValue());
			frOrder.setAccountId(mAccountId.intValue());
			frOrder.setSiteId(mSite.getSiteId());
			frOrder.setWeight(new BigDecimal(0));
			frOrder.setItems(frItems);
			try {
				frOrder = scartEjb.calcTotalFreightAndHandlingAmount(frOrder);
			} catch (RemoteException exc) {
				exc.printStackTrace();
			}
		}
		return frOrder;
	}

	private ProcessOrderResultData placeOrder() 
	throws Exception {
		CustomerOrderRequestData orderReq = prepareOrderRequest();
		IntegrationServices isvcEjb = APIAccess.getAPIAccess().getIntegrationServicesAPI();
		Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();
		ShoppingServices scartEjb = APIAccess.getAPIAccess().getShoppingServicesAPI();
		ProcessOrderResultData orderRes = ProcessOrderResultData.createValue();
		Account acctEjb = APIAccess.getAPIAccess().getAccountAPI();
		Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();

		if (orderReq == null || orderReq.getEntriesCollection().size() == 0) {
			String sss = "Order not placed: no items for the order";
			log.error(sss);
			addLog(sss);
			orderRes.setOrderNum("empty order");
			orderRes.setOrderStatusCd("NO_ORDER_PLACED");
			orderRes.setOrderId(0);
			scartEjb.invalidateInvetoryShoppingHistory(mSite.getSiteId(),"SiteBean.placeOrder");
			// return orderRes;
		} else {
			orderRes = isvcEjb.processOrderRequest(orderReq);
			if(orderRes!=null) {
				int orderId = orderRes.getOrderId();
				if(orderId <= 0) {
					addLog("Error. Order not placed. Reason unknow. Invstigation is necessary");
				} else {
					addLog("Order placed. Order id: "+orderId);
				}
			}

		}
/*		
//Calculate Service Fee if applicable
YK !!!! - Wrong way to calculate. Should be processed when placing order. Also was used for Apple Only 
		if(orderRes!=null){
			OrderItemDataVector orderedItems = orderEjb.getOrderItemCollection(orderRes.getOrderId());
//                addToOrderQtyLog(orderedItems,InventoryOrderQtyDataAccess.ORDER_ID,new Integer(orderRes.getOrderId()));

			String addServiceFee = mAccount.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
			BigDecimal serviceFee = new BigDecimal(0);

			if(addServiceFee.equals("true") && orderReq.getContractId() > 0){
				List cartItems = mCart.getItems();
				HashMap serviceFeeDetMap = scartEjb.calculateServiceFee(orderReq.getContractId(),
						cartItems,orderReq.getAccountId());

				if(serviceFeeDetMap!=null && serviceFeeDetMap.size()>0){
					//Calculate total service fee
					for (int s = 0;s < cartItems.size();s++) {
						ShoppingCartItemData sData = (ShoppingCartItemData)cartItems.get(s);
						Integer item = new Integer(sData.getItemId());
						ServiceFeeDetail finalDet = (ServiceFeeDetail)serviceFeeDetMap.get(item);
						if(finalDet!=null){
							BigDecimal itemServiceFee = finalDet.getAmount();
							for(int i=0; i<orderedItems.size(); i++){
								OrderItemData oitem = (OrderItemData)orderedItems.get(i);
								if(oitem.getItemId() == sData.getItemId()){
									oitem.setServiceFee(itemServiceFee);
									OrderItemDataAccess.update(conn, oitem);
								}
							}
						}
					}
				}
			}
			
		}
*/		

		mOrderQtyLog.addTo(mCart.getItems(),InventoryOrderQtyDataAccess.CUTOFF_DATE, mCutoffDate);

		//loop through the items and add price
		
		for(int ii=0; ii< mCart.getItems().size(); ii++){
			ShoppingCartItemData sciD = mCart.getItem(ii);
			mOrderQtyLog.addTo(sciD.getItemId(), InventoryOrderQtyDataAccess.PRICE, new BigDecimal(sciD.getPrice()));
			mOrderQtyLog.addTo(sciD.getItemId(), InventoryOrderQtyDataAccess.CATEGORY, sciD.getCategoryName());
			mOrderQtyLog.addTo(sciD.getItemId(), InventoryOrderQtyDataAccess.COST_CENTER, sciD.getProduct().getCostCenterName());
			if(sciD.getProduct().getCatalogDistrMapping()!=null){
				mOrderQtyLog.addTo(sciD.getItemId(), InventoryOrderQtyDataAccess.DIST_ITEM_NUM, sciD.getProduct().getCatalogDistrMapping().getItemNum());
			}
		}

		//adding cost center override
		if(orderRes!=null){
			int orderId = orderRes.getOrderId();
			OrderItemDataVector orderedItems = orderEjb.getOrderItemCollection(orderRes.getOrderId());
			for(int ii=0; ii<orderedItems.size(); ii++){
				int itemId = ((OrderItemData)orderedItems.get(ii)).getItemId();
				mOrderQtyLog.addTo(itemId, InventoryOrderQtyDataAccess.ORDER_ID,orderId);
				int ccId = ((OrderItemData)orderedItems.get(ii)).getCostCenterId();
				if(ccId > 0){
					CostCenterData ccData = CostCenterDataAccess.select(mConn, ccId);
					String ccName = ccData.getShortDesc();
					mOrderQtyLog.addTo(itemId, InventoryOrderQtyDataAccess.COST_CENTER,ccName);
					
				}
			}
		}


		// Now clear out the cart for the site.
		mCart.setItems(null);
		scartEjb.saveInventoryShoppingCart(mCart, orderRes, null);
		orderEjb.saveInventoryOrderQtyLog(mOrderQtyLog.values(), mUser);
		// Reset the inventory information.
		if (mUsePhysicalInventoryCart.booleanValue()) {
			SiteInventoryConfigViewVector configViewVector =
								siteEjb.lookupSiteInventory(mSite.getSiteId());
			DBCriteria dbCriteria = new DBCriteria();
			dbCriteria.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID,
				mSite.getSiteId());
			InventoryLevelDataVector invLevelVector =
				InventoryLevelDataAccess.select(mConn, dbCriteria);
			///
			if (invLevelVector != null) {
				if (configViewVector == null) {
					ShoppingDAO.resetInventoryQty(mConn, mSite.getSiteId(), 0);
				} else {
					boolean isInventoryInfo = false;
					int parValue = 0;
					for (int i = 0; i < invLevelVector.size(); ++i) {
						isInventoryInfo = false;
						parValue = 0;
						InventoryLevelData invLevel = (InventoryLevelData) invLevelVector.get(i);
						for (int j = 0; j < configViewVector.size(); ++j) {
							if (configViewVector.get(j) instanceof SiteInventoryInfoView) {
								SiteInventoryInfoView invInfo = (SiteInventoryInfoView)configViewVector.get(j);
								if (invLevel.getItemId() == invInfo.getItemId()) {
									isInventoryInfo = true;
									parValue = invInfo.getParValue();
									break;
								}
							}
						}
						if (isInventoryInfo) {
							int physicalQtyOnHand =
								recalculatePhysicalQtyOnHand(
									invLevel.getQtyOnHand(), parValue);
							invLevel.setQtyOnHand((physicalQtyOnHand >= 0) ? String.valueOf(physicalQtyOnHand) : null);
							invLevel.setOrderQty(null);
							InventoryLevelDataAccess.update(mConn, invLevel);
						} else {
							invLevel.setQtyOnHand(null);
							invLevel.setOrderQty(null);
							InventoryLevelDataAccess.update(mConn, invLevel);
						}
					}
				}
			}
		} else {
			ShoppingDAO.resetInventoryQty(mConn, mSite.getSiteId(), 0);
		}

		if (!mSpecificDatesFl) {
			updateLastCutoffDate();
		}

		return orderRes;

	}



        private int recalculatePhysicalQtyOnHand(String qtyOnHandStr, int parValue) {
            int physicalQtyOnHand = -1;
            int qtyOnHand = 0;
            if (qtyOnHandStr != null) {
                physicalQtyOnHand = 0;
                if (qtyOnHandStr.trim().length() > 0) {
                    try {
                        qtyOnHand = Integer.parseInt(qtyOnHandStr.trim());
                    } catch (Exception ex) {
                    }
                }
            }
            if (qtyOnHand > parValue) {
                physicalQtyOnHand = qtyOnHand - parValue;
            }
            return physicalQtyOnHand;
        }

	private void updateLastCutoffDate() {
		DBCriteria dbc = new DBCriteria();
		dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,mSite.getSiteId());
		dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LOG);
		dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,LAST_CUTOFF_DATE);
		dbc.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD,RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
		dbc.addIsNotNull(PropertyDataAccess.CLW_VALUE);

		try {

			PropertyDataVector props = PropertyDataAccess.select(mConn, dbc);
			if(props.size()>0)
			{
				PropertyData property = (PropertyData)props.get(0);
				SimpleDateFormat sdf= new SimpleDateFormat(DATE_FORMAT);
				property.setValue(sdf.format(mCutoffDate));
				property.setModBy("CorporateOrderManager");
				PropertyDataAccess.update(mConn,property);
			}

			else

			{
			  SimpleDateFormat sdf= new SimpleDateFormat(DATE_FORMAT);
			  PropertyData property= PropertyData.createValue();
			  property.setBusEntityId(mSite.getSiteId());
			  property.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LOG);
			  property.setShortDesc(LAST_CUTOFF_DATE);
			  property.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			  property.setValue(sdf.format(mCutoffDate));
			  property.setAddBy("CorporateOrderManager");
			  property.setModBy("CorporateOrderManager");

			  PropertyDataAccess.insert(mConn,property);

			}

		} catch (Exception e) {
			addLog("Failed to update InventoryLog property for the order due to exception: "+
				e.getMessage());
			e.printStackTrace();
		}

	}

    private String getActualSkuNumber(ProductData pd, String pStoreTypeCd) {
        if (Utility.isSet(pd.getActualCustomerSkuNum())) {
            return pd.getActualCustomerSkuNum();
        }
        if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(pStoreTypeCd)) {
            if (pd.getCatalogDistrMapping() == null) {
                return pd.getManufacturerSku();
            } else {
                return pd.getCatalogDistrMapping().getItemNum();
            }
        }

        //default to the standard system sku
        return Integer.toString(pd.getSkuNum());
    }

}
