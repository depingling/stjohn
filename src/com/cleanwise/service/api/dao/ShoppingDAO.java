package com.cleanwise.service.api.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.util.CatalogProductBundle;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DefaultProductBundle;
import com.cleanwise.service.api.util.DiverseyMSDSRetrieve;
import com.cleanwise.service.api.util.OrderGuideProductBundle;
import com.cleanwise.service.api.util.PriceListProductBundle;
import com.cleanwise.service.api.util.ProductBundle;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.CatalogStructureDataVector;
import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.ContractItemDataVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.InventoryLevelData;
import com.cleanwise.service.api.value.InventoryLevelDataVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemMetaDataVector;
import com.cleanwise.service.api.value.ItemShoppingHistoryData;
import com.cleanwise.service.api.value.JanitorClosetData;
import com.cleanwise.service.api.value.JanitorClosetDataVector;
import com.cleanwise.service.api.value.MessageResourceData;
import com.cleanwise.service.api.value.MessageResourceDataVector;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderGuideStructureData;
import com.cleanwise.service.api.value.OrderGuideStructureDataVector;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemJoinData;
import com.cleanwise.service.api.value.OrderItemMetaData;
import com.cleanwise.service.api.value.OrderItemMetaDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.PriceValue;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingInfoData;
import com.cleanwise.service.api.value.ShoppingInfoDataVector;
import com.cleanwise.service.api.value.ShoppingItemRequest;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteInventoryConfigView;
import com.cleanwise.service.api.value.SkuValue;
import com.cleanwise.service.api.value.UserData;

/**
 *  Description of the Class
 *
 *@author     Dvieira
 *@created    October 22, 2003
 */
public class ShoppingDAO {

	  private static final Logger log = Logger.getLogger(ShoppingDAO.class);

    /**
     *  Description of the Method
     *
     *@param  con               Description of the Parameter
     *@param  pOldEntry         Description of the Parameter
     *@param  pNewEntry         Description of the Parameter
     *@exception  SQLException  Description of the Exception
     */
    public static void enterAuditEntry(Connection con,
                                       int pSiteId,
                                       int pOrderGuideId,
            OrderGuideStructureData pOldEntry,
            OrderGuideStructureData pNewEntry,
            String pUserName)
    throws SQLException {
        enterAuditEntry(con, pSiteId, pOrderGuideId,
            pOldEntry, pNewEntry, 0, pUserName);
    }

    public static void enterAuditEntry(Connection con, int siteId,
        int orderGuideId, OrderGuideStructureData oldEntry,
            OrderGuideStructureData newEntry, String userName,
                String historyShortDesc) throws SQLException {

        enterAuditEntry(con, siteId, orderGuideId, oldEntry,
            newEntry, 0, userName, historyShortDesc);
    }

     /**
     *  Description of the Method
     *
      * @param  con               Description of the Parameter
      * @param pOrderGuideId
      * @param  pOldEntry         Description of the Parameter
      * @param  pNewEntry         Description of the Parameter
      * @param  pOrderId          Description of the Parameter
     *@exception  SQLException  Description of the Exception
     */
    public static void enterAuditEntry(Connection con,
                                       int pSiteId,
                                       int pOrderGuideId, OrderGuideStructureData pOldEntry,
                                       OrderGuideStructureData pNewEntry,
                                       int pOrderId,
                                       String pUserName)
             throws SQLException {

        enterAuditEntry(con, pSiteId, pOrderGuideId, pOldEntry, pNewEntry,
            pOrderId, pUserName, ShoppingCartData.CART_ITEM_UPDATE);
    }

    public static void enterAuditEntry(Connection con, int siteId,
        int orderGuideId, OrderGuideStructureData oldEntry,
            OrderGuideStructureData newEntry, int orderId, String userName,
                String historyShortDesc) throws SQLException {

        ShoppingInfoData sinfo = generateAuditEntry(con, siteId, orderGuideId,
            oldEntry, newEntry, orderId, userName, historyShortDesc);
        ShoppingInfoDataAccess.insert(con, sinfo);
    }

    public static ShoppingInfoData generateAuditEntry(Connection con,
        int siteId, int orderGuideId, OrderGuideStructureData oldEntry,
            OrderGuideStructureData newEntry, int orderId,
                String userName) throws SQLException {

        return generateAuditEntry(con, siteId, orderGuideId, oldEntry, newEntry,
            orderId, userName, ShoppingCartData.CART_ITEM_UPDATE);
    }

    public static ShoppingInfoData generateAuditEntry(Connection con,
                                       int pSiteId,int orderGuideId,
            OrderGuideStructureData pOldEntry,
            OrderGuideStructureData pNewEntry,
            int pOrderId,
            String pUserName,
            String historyShortDesc)
            throws SQLException {

        String desc = "";
        String key = null;
        String arg0 = null;
        String arg0TypeCd = null;
        String arg1 = null;
        String arg1TypeCd = null;
        ShoppingInfoData sinfo = ShoppingInfoData.createValue();
        sinfo.setSiteId(pSiteId);
        if(pOrderId>0) {
            sinfo.setOrderId(pOrderId);
        }
        if(orderGuideId>0){
           sinfo.setOrderGuideId(orderGuideId);
        }

        if (null == pOldEntry) {
            // New item
            sinfo.setAddBy(pUserName);
            sinfo.setModBy(pUserName);
            sinfo.setItemId(pNewEntry.getItemId());
            //desc = "Item added with a quantity of " +
            //        pNewEntry.getQuantity() + ".";
            key = "shoppingMessages.text.itemAddedWithQty";
            arg0 = ""+pNewEntry.getQuantity();
            arg0TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;
            desc = translateMessage(con,key,arg0, null, null, null);
        } else if (null == pNewEntry) {
            // Dropped item
            sinfo.setItemId(pOldEntry.getItemId());
            //desc = "Item was removed.";
            key = "shoppingMessages.text.itemWasRemoved";
            desc = translateMessage(con,key,null, null, null, null);
            sinfo.setAddBy(pUserName);
            sinfo.setModBy(pUserName);
        } else {
            // qty update.
            sinfo.setItemId(pNewEntry.getItemId());
            //desc = "Item quantity was updated from "
            //         + pOldEntry.getQuantity()
            //         + " to "
            //         + pNewEntry.getQuantity() + ".";
            key = "shoppingMessages.text.itemQtyAdjusted";
            arg0 = ""+pOldEntry.getQuantity();
            arg0TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;
            arg1 = ""+pNewEntry.getQuantity();
            arg1TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;
            desc = translateMessage(con,key,arg0, arg1, null, null);
            sinfo.setAddBy(pUserName);
            sinfo.setModBy(pUserName);
        }
        sinfo.setShortDesc(historyShortDesc);
        sinfo.setValue(desc);
        if(key!=null) sinfo.setMessageKey(key);
        if(arg0!=null) {
          sinfo.setArg0(arg0);
          sinfo.setArg0TypeCd(arg0TypeCd);
        }
        if(arg1!=null) {
          sinfo.setArg1(arg1);
          sinfo.setArg1TypeCd(arg1TypeCd);
        }
        sinfo.setShoppingInfoStatusCd(RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE);
        return sinfo;
    }

    public static ShoppingInfoData generateInvAuditEntry(Connection con,
        int siteId, SiteInventoryConfigView oldEntry,
            SiteInventoryConfigView newEntry, int orderId,
                String userName) throws SQLException {

        return generateInvAuditEntry(con, siteId, oldEntry, newEntry, orderId,
            userName, ShoppingCartData.CART_ITEM_UPDATE);
    }

    public static ShoppingInfoData generateInvAuditEntry(Connection con, int pSiteId,
                                                         SiteInventoryConfigView pOldEntry,
                                                         SiteInventoryConfigView pNewEntry,
                                                         int pOrderId, String pUserName,
                                                         String historyShortDesc) throws SQLException {
        return generateInvAuditEntry(con, pSiteId,pOldEntry,pNewEntry,pOrderId, pUserName,historyShortDesc, false);                                                         

    }
    public static ShoppingInfoData generateInvAuditEntry(Connection con, int pSiteId,
                                                         SiteInventoryConfigView pOldEntry,
                                                         SiteInventoryConfigView pNewEntry,
                                                         int pOrderId, String pUserName,
                                                         String historyShortDesc,
                                                         boolean pUpdateFromPhysCart)
            throws SQLException {

        String desc = "";
        String key = null;
        String arg0 = null;
        String arg0TypeCd = null;
        String arg1 = null;
        String arg1TypeCd = null;
        ShoppingInfoData sinfo = ShoppingInfoData.createValue();
        sinfo.setSiteId(pSiteId);
        if (pOrderId > 0) {
            sinfo.setOrderId(pOrderId);
        }

        if (null == pOldEntry) {
            // New item
            log.info("generateInvAuditEntry => NEW "+pNewEntry.getItemId());


            sinfo.setAddBy(pUserName);
            sinfo.setModBy(pUserName);
            sinfo.setItemId(pNewEntry.getItemId());

            String nothing=translateMessage(con,"shoppingMessages.text.noThing",null,null, null, null);
            key = "shoppingMessages.text.onHandQtySetItemAdded";
            arg0 = "" + pNewEntry.getQtyOnHand();
            arg0=arg0.trim().length()==0?nothing:arg0;
            arg0TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;



            arg1 = "" + pNewEntry.getOrderQty();
            arg1=arg1.trim().length()==0?nothing:arg1;
            arg1TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;
            desc = translateMessage(con, key, arg0, arg1, null, null);

        } else if (null == pNewEntry) {
            // Dropped item
            log.info("generateInvAuditEntry => DELETE "+pOldEntry.getItemId());
            sinfo.setItemId(pOldEntry.getItemId());
            //desc = "Item was removed.";
            key = "shoppingMessages.text.itemWasRemoved";
            desc = translateMessage(con, key, null, null, null, null);
            sinfo.setAddBy(pUserName);
            sinfo.setModBy(pUserName);
        } else {
            // qty update.
            log.info("generateInvAuditEntry => UPDATE "+pOldEntry.getItemId());
            if (pOldEntry.getItemId() == pNewEntry.getItemId()) {

                sinfo.setItemId(pNewEntry.getItemId());
                boolean orderQtyChange = false;
                boolean onHandQtyChange = false;
                boolean parValQtyChange = false;

                String newOrderQtyStr  = Utility.strNN(pNewEntry.getOrderQty());
                String oldOrderQtyStr  = Utility.strNN(pOldEntry.getOrderQty());
                String newQtyOnHandStr = Utility.strNN(pNewEntry.getQtyOnHand());
                String oldQtyOnHandStr = Utility.strNN(pOldEntry.getQtyOnHand());
                String nothing=translateMessage(con,"shoppingMessages.text.noThing",null,null, null, null);

                if (!newOrderQtyStr.trim().equals(oldOrderQtyStr.trim())) {
                    orderQtyChange = true;
                }

                if (pUpdateFromPhysCart || !newQtyOnHandStr.trim().equals(oldQtyOnHandStr.trim())) {
                    onHandQtyChange = true;
                }

                if (Utility.mapNotEqual(pNewEntry.getParValues(), pOldEntry.getParValues())) {
                    parValQtyChange = true;
                }

                log.info("generateInvAuditEntry => parValQtyChange: "+parValQtyChange+" onHandQtyChange: "+onHandQtyChange+" orderQtyChange: "+orderQtyChange);

                if (orderQtyChange && !onHandQtyChange && !parValQtyChange) {

                    key = "shoppingMessages.text.itemQtyAdjusted";
                    log.info("generateInvAuditEntry => key:"+key);

                    arg0 = "" + oldOrderQtyStr;
                    arg0=arg0.trim().length()==0?nothing:arg0;
                    arg0TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;

                    arg1 = "" + newOrderQtyStr;
                    arg1=arg1.trim().length()==0?nothing:arg1;
                    arg1TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;

                    desc = translateMessage(con, key, arg0, arg1, null, null);
                } else if (!orderQtyChange && onHandQtyChange && !parValQtyChange) {

                    if (oldQtyOnHandStr.trim().length() == 0
                            || oldQtyOnHandStr.trim().equals("0")) {

                        key = "shoppingMessages.text.onHandQtySet";
                        log.info("generateInvAuditEntry => key:"+key);

                        arg0 = "" + newQtyOnHandStr;
                        arg0=arg0.trim().length()==0?nothing:arg0;
                        arg0TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;

                        desc = translateMessage(con, key, arg0, null, null, null);

                    } else {
                        key = "shoppingMessages.text.onHandModified";
                        log.info("generateInvAuditEntry => key:"+key);

                        arg0 = "" + oldQtyOnHandStr;
                        arg0=arg0.trim().length()==0?nothing:arg0;
                        arg0TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;

                        arg1 = "" + newQtyOnHandStr;
                        arg1=arg1.trim().length()==0?nothing:arg1;
                        arg1TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;

                        desc = translateMessage(con, key, arg0, arg1, null, null);
                    }
                } else if (!orderQtyChange && !onHandQtyChange && parValQtyChange) {

                } else if (orderQtyChange && onHandQtyChange && !parValQtyChange) {
                    key = "shoppingMessages.text.onHandQtySetItemSet";
                    log.info("generateInvAuditEntry => key:"+key);

                    arg0 = "" + newQtyOnHandStr;
                    arg0=arg0.trim().length()==0?nothing:arg0;
                    arg0TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;

                    arg1 = "" + newOrderQtyStr;
                    arg1=arg1.trim().length()==0?nothing:arg1;
                    arg1TypeCd = RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING;

                    desc = translateMessage(con, key, arg0, arg1, null, null);
                } else if (orderQtyChange && !onHandQtyChange && parValQtyChange) {
                    desc=null;
                } else if (!orderQtyChange && onHandQtyChange && parValQtyChange) {
                    desc=null;
                } else if (orderQtyChange && onHandQtyChange && parValQtyChange) {
                    desc=null;
                } else {
                    desc=null;
                }

                sinfo.setAddBy(pUserName);
                sinfo.setModBy(pUserName);
            }
        }

        if(desc==null) {
            return null;
        }

        sinfo.setShortDesc(historyShortDesc);
        sinfo.setValue(desc);
        if (key != null) sinfo.setMessageKey(key);
        if (arg0 != null) {
            sinfo.setArg0(arg0);
            sinfo.setArg0TypeCd(arg0TypeCd);
        }
        if (arg1 != null) {
            sinfo.setArg1(arg1);
            sinfo.setArg1TypeCd(arg1TypeCd);
        }
        sinfo.setShoppingInfoStatusCd(RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE);
        return sinfo;
    }


    /**
     *  Gets the siteCart attribute of the ShoppingDAO class
     *
     *@param  con               Description of the Parameter
     *@param  pSiteId           Description of the Parameter
     *@return                   The siteCart value
     *@exception  SQLException  Description of the Exception
     */
    public static OrderGuideData getSiteCart
            (Connection con,
            int pSiteId)
             throws SQLException {
        return getCart(con, 0, pSiteId);
    }


    public static OrderGuideData getCart
            (Connection con, int pUserId, int pSiteId)
             throws SQLException {
       return getCart(con,pUserId,pSiteId,String.valueOf(pUserId));
    }

    /**
     *  Gets the cart attribute of the ShoppingDAO class
     *
     *@param  con               Description of the Parameter
     *@param  pUserId           Description of the Parameter
     *@param  pSiteId           Description of the Parameter
     *@return                   The cart value
     *@exception  SQLException  Description of the Exception
     */
    public static OrderGuideData getCart
            (Connection con, int pUserId, int pSiteId, String pUserName)
             throws SQLException {
       return getCart(con,pUserId,pSiteId,pUserName,RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART);
    }
    
    public static OrderGuideData getCartWithUserUniqueName
    (Connection con, int pUserId, int pSiteId, String pUserName, String userName2)
     throws SQLException {
    	return getCartWithUserUniqueName(con,pUserId,pSiteId,pUserName,userName2,RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART);
    }

    public static OrderGuideData getCart(Connection con,
            int pUserId,
            int pSiteId,
            String pUserName,
            String cartType)  throws SQLException {
    	return getCartWithUserUniqueName(con,
                pUserId,
                pSiteId,
                pUserName,
                null,
                cartType);
    	
    }

       public static OrderGuideData getCartWithUserUniqueName(Connection con,
                                            int pUserId,
                                            int pSiteId,
                                            String pUserName,
                                            String userName2,
                                            String cartType)  throws SQLException {

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,cartType);
        dbc.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID, pSiteId);

        if (pUserId > 0) {
            dbc.addEqualTo(OrderGuideDataAccess.USER_ID, pUserId);
        }
        
        if (userName2 != null){
        	dbc.addEqualTo(OrderGuideDataAccess.ADD_BY, userName2);
        }
        dbc.addOrderBy(OrderGuideDataAccess.ORDER_GUIDE_ID, false);

        OrderGuideDataVector ogdv = OrderGuideDataAccess.select(con, dbc);
        if (null != ogdv && ogdv.size() > 0) {
            return (OrderGuideData) ogdv.get(0);
        }

        // Make a cart
        OrderGuideData sc = OrderGuideData.createValue();
        sc.setBusEntityId(pSiteId);
        if (pUserId > 0) {
            sc.setUserId(pUserId);
        }
        if (userName2 != null){
        	sc.setAddBy(userName2);
        	sc.setModBy(userName2);
        }else{
        	sc.setAddBy(pUserName);
        	sc.setModBy(pUserName);
        }
        sc.setModBy(pUserName);
        sc.setShortDesc("pSiteId=" + pSiteId);
        sc.setOrderGuideTypeCd(cartType);
        return OrderGuideDataAccess.insert(con, sc);
    }


    public static OrderGuideData getInvntoryCartOG(Connection con, int pUserId, int pSiteId, String pUserName)
            throws SQLException {

        return getCart(con, pUserId, pSiteId, pUserName, RefCodeNames.ORDER_GUIDE_TYPE_CD.INVENTORY_CART);
    }

    /**
     *  Description of the Method
     *
     *@param  con               Description of the Parameter
     *@exception  SQLException  Description of the Exception
     */
    public static void enterInventoryAuditEntry(Connection con, int siteId,
        int orderGuideId, int itemId, int parValue, String messageKey,
            String arg0, String arg1, String arg2,
                String user) throws SQLException {

        enterInventoryAuditEntry(con, siteId, orderGuideId, itemId, parValue,
            messageKey, arg0, arg1, arg2, user,
                ShoppingCartData.CART_ITEM_UPDATE);
    }

    public static void enterInventoryAuditEntry
            (Connection con,
            int pSiteId,
            int pOrderGuideId,
            int pItemId,
            int pParValue,
            String pMessageKey,
            String pArg0,
            String pArg1,
            String pArg2,
            String pUser,
            String historyShortDesc)
             throws SQLException {

        if ( pParValue <= 0 ) return;

        String desc = "";
        ShoppingInfoData sinfo = ShoppingInfoData.createValue();
        sinfo.setSiteId(pSiteId);
        sinfo.setOrderGuideId(pOrderGuideId);
        sinfo.setItemId(pItemId);
        //default message
        desc = translateMessage(con, pMessageKey, pArg0, pArg1, pArg2, null);
        sinfo.setValue(desc);
        sinfo.setMessageKey(pMessageKey);
        if(pArg0!=null) {
          sinfo.setArg0(pArg0);
          sinfo.setArg0TypeCd(RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING);
        }
        if(pArg1!=null) {
          sinfo.setArg1(pArg1);
          sinfo.setArg1TypeCd(RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING);
        }
        if(pArg2!=null) {
          sinfo.setArg2(pArg2);
          sinfo.setArg2TypeCd(RefCodeNames.SHOPPING_MESSAGE_ARG_CD.STRING);
        }

        sinfo.setShortDesc(historyShortDesc);
        sinfo.setAddBy(pUser);
        sinfo.setModBy(pUser);
        sinfo.setShoppingInfoStatusCd(RefCodeNames.SHOPPING_INFO_STATUS_CD.ACTIVE);
        ShoppingInfoDataAccess.insert(con, sinfo);
    }


    /**
     *  Gets a order history entry for the supplied type.  Assumes that there is only one of that type, an exception is thrown if multiple ae found
     *
     *@param  con               DB Conncetion
     *@param  pOrderGuideId     Order guide to restrict by
     *@param  pShortDesc        The actual entry type we are inteested in
     *@return                   ShoppingInfoData
     *@exception  SQLException  Description of the Exception
     */
    public static ShoppingInfoData getCartHistoryEntry(Connection con,int pOrderGuideId,String pShortDesc) throws SQLException {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(ShoppingInfoDataAccess.ORDER_GUIDE_ID, pOrderGuideId);
        dbc.addEqualTo(ShoppingInfoDataAccess.SHORT_DESC, pShortDesc);
        log.info(ShoppingInfoDataAccess.getSqlSelectIdOnly("*",dbc));
        ShoppingInfoDataVector v = ShoppingInfoDataAccess.select(con, dbc);
        int size = v.size();
        if(size == 0){
            return null;
        }else if(size == 1){
            return (ShoppingInfoData) v.get(0);
        }else{
            throw new SQLException("Expected 0 or 1 order history entry of type "+pShortDesc+" found "+size);
        }
    }

    /**
     *  Gets the orderHistory attribute of the ShoppingDAO class
     *
     *@param  con               Description of the Parameter
     *@param  pOrderId          Description of the Parameter
     *@return                   The orderHistory value
     *@exception  SQLException  Description of the Exception
     */
    public static java.util.List getOrderHistory(Connection con,
            int pOrderId)
             throws SQLException {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(ShoppingInfoDataAccess.ORDER_ID, pOrderId);
        dbc.addOrderBy(ShoppingInfoDataAccess.ADD_DATE, false);
        return ShoppingInfoDataAccess.select(con, dbc);
    }

    /**
     *  Gets the orderHistory attribute of the ShoppingDAO class
     *
     *@param  con               Description of the Parameter
     *@param  pOrderIds          Description of the Parameter
     *@return                   The orderHistory value
     *@exception  SQLException  Description of the Exception
     */
    public static Map<Integer,List<ShoppingInfoData>> getOrderHistory(Connection con,
            IdVector pOrderIds)
             throws SQLException {
    	Map<Integer,List<ShoppingInfoData>> returnValue = new HashMap<Integer,List<ShoppingInfoData>>();
        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(ShoppingInfoDataAccess.ORDER_ID, pOrderIds);
        dbc.addOrderBy(ShoppingInfoDataAccess.ORDER_ID, true);
        dbc.addOrderBy(ShoppingInfoDataAccess.ADD_DATE, false);
        ShoppingInfoDataVector shoppingInfos = ShoppingInfoDataAccess.select(con, dbc);
        Iterator<ShoppingInfoData> shoppingInfoIterator = shoppingInfos.iterator();
        while (shoppingInfoIterator.hasNext()) {
        	ShoppingInfoData shoppingInfo = shoppingInfoIterator.next();
        	int orderId = shoppingInfo.getOrderId();
        	List orderShoppingInfos = returnValue.get(orderId);
        	if (!Utility.isSet(orderShoppingInfos)) {
        		orderShoppingInfos = new ArrayList<ShoppingInfoData>();
        		returnValue.put(orderId, orderShoppingInfos);
        	}
        	orderShoppingInfos.add(shoppingInfo);
        }
        return returnValue;
    }


    /**
     *  Gets the orderHistory attribute of the ShoppingDAO class
     *
     *@param  con               Description of the Parameter
     *@param  pOrderId          Description of the Parameter
     *@param  pCartId           The new orderHistory value
     *@param  pItemId           The new orderHistory value
     *@exception  SQLException  Description of the Exception
     */
    public static void setOrderHistory(Connection con,
            int pSiteId, int pOrderId, int pCartId, int pItemId)
             throws SQLException {

        if ( pOrderId <= 0 ) return;

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(ShoppingInfoDataAccess.SITE_ID, pSiteId);
        dbc.addIsNull(ShoppingInfoDataAccess.ORDER_ID);
        if (pItemId > 0) {
            dbc.addEqualTo(ShoppingInfoDataAccess.ITEM_ID, pItemId);
        }
        dbc.addEqualTo(ShoppingInfoDataAccess.ORDER_GUIDE_ID,pCartId);

        List v = ShoppingInfoDataAccess.select(con, dbc);
        for (int idx = 0; idx < v.size(); idx++) {
            ShoppingInfoData sid = (ShoppingInfoData) v.get(idx);
            sid.setOrderId(pOrderId);
            ShoppingInfoDataAccess.update(con, sid);
        }

        // Save the current comments list.
        dbc = new DBCriteria();
        dbc.addEqualTo(ShoppingInfoDataAccess.SITE_ID, pSiteId);
        dbc.addEqualTo(ShoppingInfoDataAccess.ORDER_GUIDE_ID,pCartId);
        dbc.addEqualTo(ShoppingInfoDataAccess.SHORT_DESC,
                       RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);
        dbc.addIsNull(ShoppingInfoDataAccess.ORDER_ID);
        v = ShoppingInfoDataAccess.select(con, dbc);
        for (int idx = 0; idx < v.size(); idx++) {
            ShoppingInfoData sid = (ShoppingInfoData) v.get(idx);

            OrderPropertyData opd = OrderPropertyData.createValue();
            opd.setAddBy(sid.getAddBy());
            opd.setModBy(sid.getModBy());
            opd.setOrderId(pOrderId);
            opd.setShortDesc
                (RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);
            opd.setOrderPropertyTypeCd
                (RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);
            opd.setOrderPropertyStatusCd
                (RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
            opd.setValue(sid.getValue());
            opd = OrderPropertyDataAccess.insert(con, opd);
            // Reset the add date.
            opd.setAddDate(sid.getAddDate());
            OrderPropertyDataAccess.update(con, opd);
            // Remove from the shopping information table.
            ShoppingInfoDataAccess.remove(con,sid.getShoppingInfoId());
        }
    }

    public static void resetOnHandValues
  (Connection con, int pSiteId)
             throws SQLException
    {
  resetOnHand(con, pSiteId, 0);
    }

    /**
     *  Description of the Method
     *
     *@param  con               Description of the Parameter
     *@param  pSiteId           Description of the Parameter
     *@exception  SQLException  Description of the Exception
     */
    public static void resetOnHand(Connection con, int pSiteId, int pItemId)
             throws SQLException {
        DBCriteria dbc1 = new DBCriteria();
        dbc1.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID, pSiteId);
  if ( pItemId > 0 ) {
      dbc1.addEqualTo(InventoryLevelDataAccess.ITEM_ID, pItemId);
  }

        InventoryLevelDataVector invItems =
                InventoryLevelDataAccess.select(con, dbc1);
        for (int c2 = 0; invItems != null && c2 <
                invItems.size(); c2++) {
            InventoryLevelData ild =
                    (InventoryLevelData) invItems.get(c2);
            ild.setQtyOnHand(null);
            ild.setModBy("ShoppingDAO.resetOnHand");
            InventoryLevelDataAccess.update(con, ild);
        }
    }

    public static void incrementOnHand(Connection con, int pSiteId,
            int pItemId, int pNewQty)
            throws Exception {

      DBCriteria dbc1 = new DBCriteria();
      dbc1.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID, pSiteId);
      dbc1.addEqualTo(InventoryLevelDataAccess.ITEM_ID, pItemId);
      InventoryLevelDataVector invItems =
              InventoryLevelDataAccess.select(con, dbc1);
      int nitems = 0;
      if ( invItems != null ) nitems = invItems.size();

      Category log =
              Category.getInstance(ShoppingDAO.class.getName());

      log.info("incrementOnHand, pSiteId="
              + pSiteId + " , pItemId=" + pItemId
              + " , pNewQty=" + pNewQty
              + " , nitems=" + nitems
              );
      for (int c2 = 0; c2 < nitems; c2++) {
        InventoryLevelData ild =
                (InventoryLevelData) invItems.get(c2);
        int currQty = 0;
        if (ild.getQtyOnHand() != null ) {
          currQty = Integer.parseInt(ild.getQtyOnHand().trim());
        }
        String nv = String.valueOf( pNewQty + currQty );

        ild.setQtyOnHand(nv);
        ild.setModBy("ShoppingDAO.incrementOnHand");
        InventoryLevelDataAccess.update(con, ild);
      }
    }

    public static void inventoryItemUpdate(Connection pCon, int pSiteId,
    int pItemId, int pQty, String pOrderSourceCd) throws Exception  {
        if ( null == pOrderSourceCd ) {
            pOrderSourceCd = "";
        }
        Category log =
    Category.getInstance(ShoppingDAO.class.getName());
        if ( RefCodeNames.SYS_ORDER_SOURCE_CD.INVENTORY.equals(pOrderSourceCd)) {
      log.debug("resetOnHand, pSiteId="
          + pSiteId + " , pItemId=" + pItemId
          );
            ShoppingDAO.resetOnHand(pCon, pSiteId, pItemId);
        }
        else {
      log.info("incrementOnHand, pSiteId="
          + pSiteId + " , pItemId=" + pItemId
          + " , pQty=" + pQty
          );
            ShoppingDAO.incrementOnHand(pCon, pSiteId, pItemId, pQty);
        }
    }

    /**
     *  Description of the Method
     *
     *@param  con               Description of the Parameter
     *@param  pSiteId           Description of the Parameter
     *@param  pScartItem        Description of the Parameter
     *@exception  SQLException  Description of the Exception
     */
    public static void incrementOnHand(Connection con, int pSiteId,
            ShoppingCartItemData pScartItem)
             throws Exception {

  incrementOnHand
      (con, pSiteId,
       pScartItem.getProduct().getItemData().getItemId(),
       pScartItem.getQuantity()
       );

    }
    
    /**
     *Clears out the cart for this user/site.  Takes into account inventory shopping.
     *@returns the removed order guide structure items.
     */
    public static OrderGuideStructureDataVector clearCart(Connection con, SiteData site, int userId) throws SQLException{
    	return clearCart(con, site, userId, null);
    }

    /**
     *Clears out the cart for this user/site.  Takes into account inventory shopping.
     *@returns the removed order guide structure items.
     */
    public static OrderGuideStructureDataVector clearCart(Connection con, SiteData site, int userId, String userName2) throws SQLException{
        DBCriteria dbc = new DBCriteria();
        if (!site.hasInventoryShoppingOn() ||(site.hasInventoryShoppingOn()&&site.hasModernInventoryShopping()) ) {
              dbc.addEqualTo(OrderGuideDataAccess.USER_ID,userId);
        }

        dbc.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID,site.getSiteId());
        dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,
                       RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART);
        if (userName2 != null)
        	dbc.addEqualTo(OrderGuideDataAccess.ADD_BY,userName2);

        String cartReq = OrderGuideDataAccess.getSqlSelectIdOnly
            (OrderGuideDataAccess.ORDER_GUIDE_ID,dbc);
        DBCriteria dbc1 = new DBCriteria();
        dbc1.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,cartReq);
        OrderGuideStructureDataVector prevOgsdv = OrderGuideStructureDataAccess.select(con,dbc1);
        try{
            OrderGuideStructureDataAccess.remove(con,dbc1);
            OrderGuideDataAccess.remove(con,dbc);
            return prevOgsdv;
        }catch(SQLException e){
            log.info("Caught error printing dbc that caused error:");
            log.info("dbc1="+dbc1.getWhereClause());
            log.info("dbc="+dbc.getWhereClause());
            throw e;
        }
    }

    /**
     *Clears out the cart for this user/site.  Takes into account inventory shopping.
     *@returns the removed order guide structure items.
     */
    public static OrderGuideStructureDataVector getCartItems(Connection con, int pOrderGuideId)
    throws SQLException{
        DBCriteria dbc1 = new DBCriteria();
        dbc1.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,pOrderGuideId);
        OrderGuideStructureDataVector ogsdv = OrderGuideStructureDataAccess.select(con,dbc1);
        return ogsdv;
    }

    public static OrderGuideStructureData setCartItemQty(Connection con, OrderGuideData pCart,
    int pItemId, int pItemQty) throws SQLException{
        return setCartItemQty(con, pCart,pItemId, pItemQty, null);
    }

    public static OrderGuideStructureData setCartItemQty(Connection con, OrderGuideData pCart,
                  int pItemId, int pItemQty, String pUserName)
    throws SQLException{
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,pCart.getOrderGuideId());
        dbc.addEqualTo(OrderGuideStructureDataAccess.ITEM_ID,pItemId);

        OrderGuideStructureDataVector ogsdv = OrderGuideStructureDataAccess.select(con,dbc);
        OrderGuideStructureData ogsd = null;
        if ( ogsdv.size() == 0) {
            if( pItemQty >0) {
                ogsd = OrderGuideStructureData.createValue();
                ogsd.setOrderGuideId(pCart.getOrderGuideId());
                ogsd.setItemId(pItemId);
                ogsd.setQuantity(pItemQty);
                ogsd.setAddBy(pUserName);
                ogsd.setModBy(pUserName);
                ogsd = OrderGuideStructureDataAccess.insert(con, ogsd);
            }
        }  else {
            ogsd = (OrderGuideStructureData)ogsdv.get(0);
            if(pItemQty>0) {
                ogsd.setQuantity(pItemQty);
                ogsd.setModBy(pUserName);
                OrderGuideStructureDataAccess.update(con,ogsd);
            }  else {
                OrderGuideStructureDataAccess.remove(con,ogsd.getOrderGuideStructureId());
                ogsd = null;
            }
        }
        return ogsd;
    }

    public static OrderGuideDataVector getOrderGuides
            (Connection con, int pUserId, int pSiteId)
             throws SQLException {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,
        RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
        dbc.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID, pSiteId);
        if (pUserId > 0) {
            dbc.addEqualTo(OrderGuideDataAccess.USER_ID, pUserId);
        }
        dbc.addOrderBy(OrderGuideDataAccess.ORDER_GUIDE_ID, false);
        return OrderGuideDataAccess.select(con, dbc);
    }

    public static String translateMessage( Connection con, String pMessageKey,
            Object arg0,
            Object arg1,
            Object arg2,
            Object arg3) {
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(MessageResourceDataAccess.NAME,pMessageKey);
      String cond = "NVL("+MessageResourceDataAccess.LOCALE+",'en_US')='en_US'";
      dbc.addCondition(cond);
      dbc.addOrderBy(MessageResourceDataAccess.LOCALE,false);
      log.info("ShoppingDAO SSSSSSSSSSSSSSSSSSSSSSSSSS sql: "+(MessageResourceDataAccess.getSqlSelectIdOnly("*",dbc)));
      try {
        MessageResourceDataVector messDV = MessageResourceDataAccess.select(con,dbc);
        if(messDV.size()==0) {
          return "";
        }
        MessageResourceData messD = (MessageResourceData) messDV.get(0);
        String message = messD.getValue();
        int ind1 = message.indexOf("{");
        int ind2 = -1;
        if(ind1>=0) ind2 = message.indexOf("}",ind1);
        if(ind2<0) {
          return message;
        }
        LinkedList ind1LL = new LinkedList();
        LinkedList ind2LL = new LinkedList();
        LinkedList valLL = new LinkedList();
        while (ind1>=0 && ind2>(ind1+1)) {
          String argStr = message.substring(ind1+1,ind2).trim();
          Object argVal = null;
          if(argStr.equals("0")) {
            argVal = arg0;
          } else if (argStr.equals("1")){
            argVal = arg1;
          } else if (argStr.equals("2")){
            argVal = arg2;
          } else if (argStr.equals("3")){
            argVal = arg1;
          }
          if(argVal!=null) {
            ind1LL.add(new Integer(ind1));
            ind2LL.add(new Integer(ind2));
            valLL.add(argVal);
          }
          ind1 = message.indexOf("{",ind2);
          if(ind1>=0) ind2 = message.indexOf("}",ind1);
        }
        String messageRes = "";
        int point = 0;
        for(int ii=0; ii<ind1LL.size(); ii++) {
          ind1 = ((Integer) ind1LL.get(ii)).intValue();
          ind2 = ((Integer) ind2LL.get(ii)).intValue();
          Object val = valLL.get(ii);
          messageRes += message.substring(point,ind1);
          messageRes += val;
          point = ind2+1;
        }
        if(point<message.length()) {
          messageRes += message.substring(point);
        }
        return messageRes;

      } catch(Exception exc) {
        exc.printStackTrace();
        return "";
      }
    }

    /**
   * Returns the janitors closet for the supplied parameters
   */
  public static ShoppingCartItemDataVector getJanitorCloset(
      String pStoreType, int pCatalogId, int pContractId,
      boolean pContractOnly, int pUserId, int pSiteId, Connection con)
      throws Exception {
    return getJanitorCloset(pStoreType, pCatalogId, pContractId, pContractOnly, pUserId, pSiteId, con, null);

  }
  public static ShoppingCartItemDataVector getJanitorCloset(
      String pStoreType, int pCatalogId, int pContractId,
      boolean pContractOnly, int pUserId, int pSiteId, Connection con,
      AccCategoryToCostCenterView pCategToCostCenterView )
      throws Exception {

    ShoppingCartItemDataVector shoppingCartItemDV = new ShoppingCartItemDataVector();
    DBCriteria dbc;
    dbc = new DBCriteria();
    dbc.addEqualTo(JanitorClosetDataAccess.BUS_ENTITY_ID, pSiteId);
    if (pUserId > 0) {
    }

    // Find activity from their last order back 365 days.
    // This way we can still look at history for those
    // locations that placed orders at one time but
    // are no longer active.
    String subCond = " add_date >= ("
        + " select max(o.add_date) - 365 from clw_order o "
        + " where o.site_id = " + pSiteId + ")";

    dbc.addCondition(subCond);
    dbc.addCondition(" exists ( select o." + OrderDataAccess.ORDER_ID
        + " from " + OrderDataAccess.CLW_ORDER + " o where "
        + JanitorClosetDataAccess.CLW_JANITOR_CLOSET + "."
        + JanitorClosetDataAccess.ORDER_ID + " = o."
        + OrderDataAccess.ORDER_ID + " and ( o."
        + OrderDataAccess.ORDER_STATUS_CD + " in " + OrderDAO.kGoodOrderStatusSqlList
        //---- pick up original orders being consolidated -------------
        + " or ( o."
        + OrderDataAccess.ORDER_STATUS_CD + " = '"+ RefCodeNames.ORDER_STATUS_CD.CANCELLED + "' "
        + " and o." + OrderDataAccess.ORDER_TYPE_CD + "= '" + RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED + "'  ) )"
        //-------------------------------------------------------------
        +  ")");

    JanitorClosetDataVector closetDV = JanitorClosetDataAccess.select(con,
        dbc);

    IdVector itemIds = new IdVector();
    IdVector orderIds = new IdVector();

    for (int ii = 0; ii < closetDV.size(); ii++) {
      JanitorClosetData jcD = (JanitorClosetData) closetDV.get(ii);
      if (!itemIds.contains(jcD.getItemId()))
    	  itemIds.add(jcD.getItemId());
      if (!orderIds.contains(jcD.getOrderId()))
    	  orderIds.add(jcD.getOrderId());      
    }


    // Create shopping cart items

    if(isUseProductBundle(con,pSiteId)) {
       //set price, actual sku, category...
       shoppingCartItemDV = prepareShoppingItemsNew(con, pStoreType, pSiteId, pCatalogId, pContractId, itemIds, pCategToCostCenterView);
    } else {
       shoppingCartItemDV = prepareJanitorClosetItems(con, pSiteId, pCatalogId, pContractId, itemIds, pCategToCostCenterView);
    }

    // Pickup order information
    dbc = new DBCriteria();
    dbc.addOneOf(OrderItemDataAccess.ORDER_ID, orderIds);
    dbc.addOrderBy(OrderItemDataAccess.ITEM_ID);
    dbc.addOrderBy(OrderItemDataAccess.ORDER_ID);
    OrderItemDataVector orderItemDV = OrderItemDataAccess.select(con, dbc);

    // Pickup total history for the year
    dbc = new DBCriteria();
    if (pUserId > 0) {
    }

    String stati = "'" + RefCodeNames.ORDER_STATUS_CD.ORDERED + "', '"
        + RefCodeNames.ORDER_STATUS_CD.INVOICED + "', '"
        + RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO + "', '"
        + RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED + "'";
    dbc.addEqualTo(OrderDataAccess.SITE_ID, pSiteId);

    dbc.addOneOf(OrderDataAccess.ORDER_STATUS_CD, stati);
    dbc.addCondition(" " + OrderDataAccess.ORIGINAL_ORDER_DATE
        + " >= sysdate - 365");

    //--------- pick up items from orders being consolidated  ---------
     dbc.addCondition(" NVL(" + OrderDataAccess.ORDER_TYPE_CD + ",' ') != '"
         + RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED + "'");

     String consolidatedReq = " (1=1) UNION SELECT orig.ORDER_ID " +
     " FROM CLW_ORDER_ASSOC oa, CLW_ORDER cons, CLW_ORDER orig " +
     " WHERE   orig.ORDER_TYPE_CD = '" + RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED +"' " +
     " AND orig.ORDER_STATUS_CD = '" + RefCodeNames.ORDER_STATUS_CD.CANCELLED +"' " +
     " AND orig.site_Id = " + pSiteId +
     " AND orig.order_id = oa.ORDER1_ID " +
     " AND oa.ORDER_ASSOC_CD = '" + RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED + "' " +
     " AND oa.ORDER2_ID = cons.ORDER_ID " +
     " AND orig.ORIGINAL_ORDER_DATE >= SYSDATE - 365" +
     " AND cons.ORDER_STATUS_CD IN " + OrderDAO.kGoodOrderStatusSqlList ;

     dbc.addCondition(consolidatedReq);
    //-----------------------------------------------------------------
    String orderReq = OrderDataAccess.getSqlSelectIdOnly(
        OrderDataAccess.ORDER_ID, dbc);

    dbc = new DBCriteria();
    dbc.addOneOf(OrderItemDataAccess.ORDER_ID, orderReq);
    dbc.addOrderBy(OrderItemDataAccess.ITEM_ID);

     OrderItemDataVector yearOrderItemDV = OrderItemDataAccess.select(con,
        dbc);

    for (int ii = 0, jj = 0, kk = 0; ii < shoppingCartItemDV.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) shoppingCartItemDV.get(ii);
      int id = sciD.getProduct().getProductId();
      ItemShoppingHistoryData historyD = new ItemShoppingHistoryData();
      while (jj < orderItemDV.size()) {
        OrderItemData oiD = (OrderItemData) orderItemDV.get(jj);
        if (oiD.getItemId() < id) {
          jj++;
          continue;
        } else if (oiD.getItemId() == id) {
          java.util.Date addDate = oiD.getAddDate();
          GregorianCalendar cal = new GregorianCalendar();
          cal.setTime(addDate);
          GregorianCalendar cal1 = new GregorianCalendar(cal
              .get(GregorianCalendar.YEAR), cal
              .get(GregorianCalendar.MONTH), cal
              .get(GregorianCalendar.DAY_OF_MONTH));
          historyD.setLastDate(cal1.getTime());
          historyD.setLastQty(oiD.getTotalQuantityOrdered());
          // double amt = calcAmt(oiD.getQty(),oiD.getAmount().doubleValue());
          historyD.setLastPrice(oiD.getCustContractPrice()
              .doubleValue());
          jj++;

          // set category from last order
          int orderItemId = oiD.getOrderItemId();
          dbc = new DBCriteria();
          dbc.addEqualTo(OrderItemMetaDataAccess.ORDER_ITEM_ID, orderItemId);
          dbc.addEqualTo(OrderItemMetaDataAccess.NAME, OrderItemJoinData._propertyNames[OrderItemJoinData.CATEGORY_NAME]);
          OrderItemMetaDataVector imdV = OrderItemMetaDataAccess.select(con, dbc);
          if (imdV != null && imdV.size() > 0) {
            OrderItemMetaData imD = (OrderItemMetaData)imdV.get(0);

            //Get Low Level categories
            dbc = new DBCriteria();
            dbc.addEqualTo(ItemDataAccess.SHORT_DESC, imD.getValue());
            dbc.addOrderBy(ItemDataAccess.ITEM_ID);
            ItemDataVector categoryItemDV = ItemDataAccess.select(con, dbc);
            if (categoryItemDV != null && categoryItemDV.size() > 0) {
              ItemData cD = (ItemData) categoryItemDV.get(0);
              CatalogCategoryData ccD = new CatalogCategoryData();
              ccD.setItemData(cD);
              sciD.setCategory(ccD);
            }
          }
          continue;
        } else {
          break;
        }
      }
      while (kk < yearOrderItemDV.size()) {
        OrderItemData oiD = (OrderItemData) yearOrderItemDV.get(kk);
        if (oiD.getItemId() < id) {
          kk++;
          continue;
        } else if (oiD.getItemId() == id) {
          historyD.setY2dQty(historyD.getY2dQty()
              + oiD.getTotalQuantityOrdered());

          double amt = calcAmt(oiD.getTotalQuantityOrdered(), oiD
              .getCustContractPrice().doubleValue());
          amt = addAmt(historyD.getY2dAmt(), amt);
          historyD.setY2dAmt(amt);
          kk++;
          continue;
        } else {
          break;
        }
      }

      sciD.setShoppingHistory(historyD);
    }

    shoppingCartItemDV = setActualSkus(pStoreType, shoppingCartItemDV);
    return shoppingCartItemDV;
  }

  private static double calcAmt(int pQty, double pPrice) {
    BigDecimal priceBD = new BigDecimal(pPrice);
    priceBD = priceBD.multiply(new BigDecimal(pQty));
    return priceBD.doubleValue();
  }

  private static double addAmt(double pAmt1, double pAmt2) {
    BigDecimal amtBD1 = new BigDecimal(pAmt1);
    BigDecimal amtBD2 = new BigDecimal(pAmt2);
	amtBD1 = amtBD1.add(amtBD2);
	return amtBD1.doubleValue();
  }

  private static ShoppingCartItemDataVector prepareJanitorClosetItems(
      Connection pCon, int pSiteId, int pCatalogId, int pContractId, List pItems, AccCategoryToCostCenterView pCategToCostCenterView)
      throws RemoteException {
    log.info(">>>>>>>>>>>>>>>>>>>>>>[ShoppingDAO] pCategToCostCenterView============ prepareJanitorClosetItems 1");

    ShoppingCartItemDataVector itemList = new ShoppingCartItemDataVector();
    if (pItems.size() == 0) {
      return itemList;
    }

    try {

      DBCriteria dbc;
      // prepare Ids
      IdVector idList = new IdVector();
      // Check type
      ProductDataVector productDV = null;

      productDV = getProductsByItemId(pCon, pCatalogId, pItems, pSiteId , pCategToCostCenterView);

      Hashtable contractsHashtable = new Hashtable(1);

      // Pickup contract information
      if (pContractId != 0) {

        ContractItemDataVector contractItemDV = new ContractItemDataVector();
        for (int ii = 0; ii < productDV.size(); ii++) {
          ProductData pD = (ProductData) productDV.get(ii);
          idList.add(new Integer(pD.getProductId()));
        }
        dbc = new DBCriteria();
        dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
        dbc.addOneOf(ContractItemDataAccess.ITEM_ID, idList);
        dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
        contractItemDV = ContractItemDataAccess.select(pCon, dbc);
        if (null != contractItemDV) {
          java.util.Iterator it = contractItemDV.iterator();
          while (it.hasNext()) {
            ContractItemData cid = (ContractItemData) it.next();
            contractsHashtable.put(new Integer(cid.getItemId()),
                cid);
          }
        }
      }

      for (int ii = 0; ii < productDV.size(); ii++) {
        ProductData pD = (ProductData) productDV.get(ii);
        ShoppingCartItemData shoppingCartItemD = new ShoppingCartItemData();
        shoppingCartItemD.setProduct(pD);
        CatalogCategoryDataVector ccDV = pD.getCatalogCategories();
        if (ccDV != null && ccDV.size() > 0) {
          shoppingCartItemD.setCategory((CatalogCategoryData) ccDV.get(0));
        } else {
          shoppingCartItemD.setCategory(null);
        }

        int productId = pD.getProductId();
        ContractItemData ciD = (ContractItemData) contractsHashtable
            .get(new Integer(productId));

        if (ciD != null) {
          double price = 0;
          if (ciD.getAmount() != null) {
            price = ciD.getAmount().doubleValue();
          }
          shoppingCartItemD.setPrice(price);
          shoppingCartItemD.setContractFlag(true);
        } else {
          shoppingCartItemD.setPrice(pD.getListPrice());
          shoppingCartItemD.setContractFlag(false);
        }
        itemList.add(shoppingCartItemD);
      }
    } catch (SQLException exc) {
      exc.printStackTrace();
      throw new RemoteException(
          "prepareJanitorClosetItems, error 2005.5.16");
    }

    return itemList;
  }

  public static ShoppingCartItemDataVector prepareShoppingItemsNew(Connection pCon,
                                                                   String pStoreTypeCd,
                                                                   int pSiteId,
                                                                   int pCatalogId,
                                                                   int pContractId,
                                                                   List pItems) throws Exception {
    log.info(">>>>>>>>>>>>>>>>>>>>>>[ShoppingDAO] pCategToCostCenterView============prepareShoppingItemsNew 1- null");
    return prepareShoppingItemsNew(pCon, pStoreTypeCd, pSiteId, pCatalogId, pContractId, pItems, null);
  }

    public static ShoppingCartItemDataVector prepareShoppingItemsNew(Connection pCon,
                                                                     String pStoreTypeCd,
                                                                     int pSiteId,
                                                                     int pCatalogId,
                                                                     int pContractId,
                                                                     List pItems,
                                                                     AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {
      log.info(">>>>>>>>>>>>>>>>>>>>>>[ShoppingDAO] pCategToCostCenterView============prepareShoppingItemsNew 1");

        ShoppingCartItemDataVector itemList = new ShoppingCartItemDataVector();

        if (pItems == null || pItems.size() == 0) {
            return itemList;
        }

        //Check type
        Object itemO = pItems.get(0);
        if (itemO instanceof Integer) {
			log.info("ShoppingDAO DDDDDDDDD itemO - Integer: "+itemO);		
            ProductDataVector products = getProductsByItemId(pCon, pCatalogId, pItems, pSiteId, pCategToCostCenterView);
            for (Object oItem : products) {
			log.info("ShoppingDAO DDDDDDDDD oItem: "+oItem);		
                ShoppingCartItemData shoppingCartItemD = new ShoppingCartItemData();
                shoppingCartItemD.setProduct((ProductData) oItem);
                itemList.add(shoppingCartItemD);
            }
        } else if (itemO instanceof ProductData) {
			log.info("ShoppingDAO DDDDDDDDD itemO - ProductDate: "+itemO);		
            for (Object oItem : pItems) {
                ShoppingCartItemData shoppingCartItemD = new ShoppingCartItemData();
                shoppingCartItemD.setProduct((ProductData) oItem);
                itemList.add(shoppingCartItemD);
            }
        } else if (itemO instanceof ShoppingCartItemData) {
			log.info("ShoppingDAO DDDDDDDDD itemO - ShoppingCartItemData: "+itemO);		
            itemList.addAll(pItems);
        } else {
            throw new Exception("prepareShoppingItemsNew()=> Unknown type of requested element: " + itemO.getClass().getName());
        }

        log.info("prepareShoppingItemsNew()=>"
                + " pCatalogId=" + pCatalogId
                + " pContractId=" + pContractId
                + " pItems.size=" + pItems.size()
                + " itemList.size=" + itemList.size());


        return prepareShoppingCartItemsNew(pCon, pStoreTypeCd, pSiteId, pCatalogId, pContractId, itemList);

    }


    public static ShoppingCartItemDataVector prepareShoppingCartItemsNew(Connection pCon,
                                                                      String pStoreTypeCd,
                                                                      int pSiteId,
                                                                      int pCatalogId,
                                                                      int pContractId,
                                                                      ShoppingCartItemDataVector pItems) throws Exception {

        ShoppingCartItemDataVector itemList = new ShoppingCartItemDataVector();

        if (pItems == null || pItems.size() == 0) {
            return itemList;
        }

        if (ProductDAO.isCPMDataMissing(pItems.getProducts())) {
            ProductDAO.setCPMData(pCon,
                    pSiteId,
                    pCatalogId,
                    pContractId,
                    pItems.getProducts());
        }

        ProductBundle productBundle = ShoppingDAO.getProductBundle(pCon, pStoreTypeCd, pSiteId, pItems.getProducts());

        for (Object oItem : pItems) {

            ShoppingCartItemData shoppingCartItemD = (ShoppingCartItemData) oItem;

            ProductData product = shoppingCartItemD.getProduct();

            CatalogCategoryDataVector ccDV = product.getCatalogCategories();
            if (ccDV != null && ccDV.size() > 0) {
                shoppingCartItemD.setCategory((CatalogCategoryData) ccDV.get(0));
            } else {
                shoppingCartItemD.setCategory(null);
            }

            PriceValue price = productBundle.getPriceValue(product.getProductId());
            if (price == null) {
                throw new Exception("prepareShoppingItemsNew()=> Could not determine price for product. ProductID: " + product.getProductId());
            } else {
                shoppingCartItemD.setPriceValue(price);
                shoppingCartItemD.setPrice(price.getValue().doubleValue());
            }

            SkuValue sku = productBundle.getSkuValue(product.getProductId());
            log.info("prepareShoppingItemsNew()=> ProductID: " + product.getProductId() + ", Sku: " + sku);
            if (sku != null) {
                shoppingCartItemD.setActualSkuNum(sku.getValue());
                shoppingCartItemD.setActualSkuType(sku.getType());
            }

            shoppingCartItemD.setSkuValue(sku);
            shoppingCartItemD.setContractFlag(product.isContractItem());

            itemList.add(shoppingCartItemD);
        }

        log.info("prepareShoppingItemsNew()=>"
                + " pCatalogId=" + pCatalogId
                + " pContractId=" + pContractId
                + " pItems.size=" + pItems.size()
                + " itemList.size=" + itemList.size());


        return itemList;

    }

    public static ProductDataVector getProductsByItemId(Connection con,
	      int pCatalogId, List itemIds) throws SQLException, RemoteException {
	  return getProductsByItemId(con,pCatalogId,itemIds,0);
  }
  public static ProductDataVector getProductsByItemId(Connection con,
      int pCatalogId, List itemIds, int siteId) throws SQLException, RemoteException {
    	log.info(">>>>>>>>>>>>>>>>>>>>>>[ShoppingDAO] pCategToCostCenterView============getProductsByItemId 1 - null");
     return getProductsByItemId(con,pCatalogId,itemIds,0, null);
  }

  public static ProductDataVector getProductsByItemId(Connection con, int pCatalogId, List itemIds, int siteId,
		  AccCategoryToCostCenterView pCategToCostCenterView) throws SQLException, RemoteException {

	    ProductDataVector productDV = new ProductDataVector();
	    //Get clw_items
	    DBCriteria dbc = new DBCriteria();
	    dbc.addOneOf(ItemDataAccess.ITEM_ID, itemIds);
	    dbc.addOrderBy(ItemDataAccess.ITEM_ID);
	    ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

	    //Get attributes
	    dbc = new DBCriteria();
	    dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, itemIds);
	    dbc.addOrderBy(ItemMetaDataAccess.ITEM_ID);
	    ItemMetaDataVector itemMetaDV = ItemMetaDataAccess.select(con, dbc);

	    //Get Manufacturers
	    dbc = new DBCriteria();
	    dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIds);
	    dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
	        RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
	    String manufReq = ItemMappingDataAccess.getSqlSelectIdOnly(
	        ItemMappingDataAccess.BUS_ENTITY_ID, dbc);
	    dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
	    ItemMappingDataVector manufMappingDV = ItemMappingDataAccess.select(
	        con, dbc);

	    dbc = new DBCriteria();
	    dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, manufReq);
	    dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
	    BusEntityDataVector manufDV = BusEntityDataAccess.select(con, dbc);

	    //Get Catalog specific data
	    dbc = new DBCriteria();
	    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
	    dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemIds);
	    dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
	    CatalogStructureDataVector catalogStructureDV = CatalogStructureDataAccess.select(con, dbc);

	    IdVector distrIds = new IdVector();
	    Hashtable distmap = new Hashtable();

	    for (int ii = 0; ii < catalogStructureDV.size(); ii++) {
	      CatalogStructureData csD = (CatalogStructureData) catalogStructureDV
	          .get(ii);
	      int distrId = csD.getBusEntityId();
	      Integer nid = new Integer(distrId);

	      if (!distmap.contains(nid)) {
	        distrIds.add(nid);
	        distmap.put(nid, nid);
	      }
	    }
	    distmap = null; // Mark for removal.

	    //Get Distributor Information

	    dbc = new DBCriteria();
	    dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
	        RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
	    dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, distrIds);
	    dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
	    BusEntityDataVector distrDV = BusEntityDataAccess.select(con, dbc);

	    //Get item distributor mapping
	    dbc = new DBCriteria();
	    dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIds);
	    dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
	        RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
	    dbc.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, distrIds);
	    dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
	    ItemMappingDataVector distrMappingDV = ItemMappingDataAccess.select(con, dbc);

	    //Get item certificated companies  mapping
	    dbc = new DBCriteria();
	    dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIds);
	    dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY);
	    dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
	    ItemMappingDataVector certificatedCompaniesDV = ItemMappingDataAccess.select(con, dbc);

	   //------- prepare Product Data with hierarchy of Catalog Categories -------//
	   IdVector itemIdV = new IdVector();
	   for (int i = 0; i < itemIds.size(); i++) {
	     itemIdV.add( (Integer) itemIds.get(i));
	   }

	   ProductDataVector pdv = null;
	   ProductDAO pdao = new ProductDAO(con, itemIdV);
	   log.info("ShoppingDAO DDDDDDDDDDDD pCatalogId: "+pCatalogId);	   
	   if (pCatalogId > 0) {
	     pdao.updateCatalogInfo(con, pCatalogId, siteId, pCategToCostCenterView);
	   }
	   pdv = pdao.getResultVector();
	   log.info("ShoppingDAO DDDDDDDDDDDD pdv: "+pdv);	   

	    HashMap shoppingProducts = Utility.toMap(pdv);

	    //STJ-4374 : COST CENTER's name should not be displayed 
    	//if there is NO association (or association is removed) between COST CENTER and ACCOUNT CATALOG.
	    List<Integer> configuredCCIds = null;
	    //Get All COST CENTER ids, if ACCOUNT CATALOG is available. 
	    if(pCategToCostCenterView!=null) {
	    	 configuredCCIds = Utility.getConfiguredCostCenterIds(con,pCategToCostCenterView.getAccCatalogId());
	    }
		
	    //Now combine everything
	    for (int ii = 0, jj = 0, mm = 0, aa = 0, rr = 0; ii < itemDV.size(); ii++) {
	      ProductData productD = new ProductData();
	      //Set item data
	      ItemData itemD = (ItemData) itemDV.get(ii);
	      int itemId = itemD.getItemId();
	      productD.setItemData(itemD);
	      //Set attribute data
	      ItemMetaDataVector itemAttributes = new ItemMetaDataVector();
	      for (; jj < itemMetaDV.size(); jj++) {
	        ItemMetaData itemMetaD = (ItemMetaData) itemMetaDV.get(jj);
	        int id = itemMetaD.getItemId();
	        if (id < itemId) {
	          throw new RemoteException(
	              "getProducts: Found attribute without item. Attribute item id = "
	                  + id);
	        }
	        if (id > itemId) {
	          break;
	        }
	        itemAttributes.add(itemMetaD);
	      }

	      productD.setProductAttributes(itemAttributes);

	      //Set manufacturer data
	      if (manufMappingDV != null && manufMappingDV.size() > mm  ) {
	          ItemMappingData manufMappingD = (ItemMappingData) manufMappingDV.get(mm);
	          int id = manufMappingD.getItemId();
	          if (id < itemId) {
	            throw new RemoteException(
	                "getProducts: Found manufacturer mapping with no item. Mapping id = "
	                    + manufMappingD.getItemMappingId());
	          }
	          if (id == itemId) {
	            mm++;
	            productD.setManuMapping(manufMappingD);
	            int manufId = manufMappingD.getBusEntityId();
	            int nn = 0;
	            for (; nn < manufDV.size(); nn++) {
	              BusEntityData manufD = (BusEntityData) manufDV.get(nn);
	              if (manufId < manufD.getBusEntityId()) {
	                throw new RemoteException(
	                    "getProducts: Found manufacturer mapping with no manufacturer. Mapping id = "
	                        + manufMappingD.getItemMappingId());
	              }
	              if (manufId == manufD.getBusEntityId()) {
	                productD.setManufacturer(manufD);
	                break;
	              }
	            }
	            if (nn == manufDV.size()) {
	              throw new RemoteException(
	                  "getProducts: Found manufacturer mapping with no manufacturer. Mapping id = "
	                      + manufMappingD.getItemMappingId());
	            }
	          }
	       }

	      /*New : Setting Catalog categories */
	      for (int xx = 0; xx < pdv.size(); xx++) {
	        ProductData pd = (ProductData) pdv.get(xx);
	        ItemData itemData = pd.getItemData();
	        if (itemData.getItemId() == itemId) {
	          productD.setCatalogCategories(pd.getCatalogCategories());
	        }
	      }

	      //Set customer SKU and custormer Name, and distributor
	      //SKU and distributor Data
	      CatalogStructureData catalogStructureD = null;

	      for (int lc = 0; lc < catalogStructureDV.size(); lc++) {
	        CatalogStructureData td = (CatalogStructureData) catalogStructureDV.get(lc);
	        if (itemId == td.getItemId()) {
	          catalogStructureD = td;
	          break;
	        }
	      }

	      if (catalogStructureD == null) {
	        // This item is no longer in the catalog.
	        // The customer may be trying to access old information through
	        // janitor's closet.
	        productD.setCostCenterId(0);
	        productD.setCostCenterName("");
	      } else {
	        // Now get the cost center for this item.

	    	  /*
	    	   * Get the cost center rules if present
	    	   */
	    	  if(pCategToCostCenterView==null || configuredCCIds.contains(catalogStructureD.getCostCenterId())) {
		    	  if(pdv!=null && pdv.size()>0){
		    		  for (int xx = 0; xx < pdv.size(); xx++) {
		    			  ProductData pd = (ProductData) pdv.get(xx);
		    			  ItemData itemData = pd.getItemData();
		    			  if (itemData.getItemId() == itemId) {
		    				  productD.setCostCenterId(pd.getCostCenterId());
		    				  productD.setCostCenterName(pd.getCostCenterName());
		    			  }
		    		  }
		    	  }else{
		    		  CostCenterData ccd = fetchCostCenterData(con, catalogStructureD.getCostCenterId());
		    		  productD.setCostCenterId(ccd.getCostCenterId());
		    		  productD.setCostCenterName(ccd.getShortDesc());
		    	  }
	    	  }
	    	  productD.setCatalogStructure(catalogStructureD);

	    	  int distrId = catalogStructureD.getBusEntityId();

	        for (int dd = 0; dd < distrDV.size(); dd++) {
	          BusEntityData distrD = (BusEntityData) distrDV.get(dd);
	          if (distrId == distrD.getBusEntityId()) {
	            productD.setCatalogDistributor(distrD);
	            break;
	          }
	          if (distrId < distrD.getBusEntityId()) {
	            break;
	          }
	        }

	        for (; rr < distrMappingDV.size(); rr++) {
	          ItemMappingData imD = (ItemMappingData) distrMappingDV
	              .get(rr);
	          if (imD.getItemId() == itemId
	              && imD.getBusEntityId() == distrId) {
	            productD.setCatalogDistrMapping(imD);
	          }

	          if (imD.getItemId() > itemId) {
	            break;
	          }
	        }
	      }


	        for (int xx = 0; xx < certificatedCompaniesDV.size(); xx++) {
	            ItemMappingData imD = (ItemMappingData) certificatedCompaniesDV.get(xx);
	            if (imD.getItemId() == itemId) {
	                productD.addCertCompaniesMapping(imD);
	                BusEntityData certCompany = getCerifiedCompanyBE(con, imD.getBusEntityId());
	                productD.addMappedCertCompany(certCompany);
	            }
	            if (imD.getItemId() > itemId) {
	                break;
	            }
	        }

	        ProductData shopProd = (ProductData) shoppingProducts.get(productD.getProductId());
	        if (shopProd != null) {
	            productD.setProductPrice(shopProd.getProductPrice());
	            productD.setProductSku(shopProd.getProductSku());
	            productD.setIsContractItem(shopProd.isContractItem());
				productD.setCostCenterId(shopProd.getCostCenterId());
				productD.setCostCenterName(shopProd.getCostCenterName());
	        }

	      productDV.add(productD);
	    }

	    return productDV;
  }
  
  	public static List<Integer> populateMSDSInformation(Connection con, ShoppingCartItemDataVector shoppingCartItems, String pUserLocale, 
		  String pCountryCode, String pStoreLocale) throws SQLException, RemoteException {

  		List<Integer> returnValue = new ArrayList<Integer>();
  		
  		if (!Utility.isSet(shoppingCartItems)) {
  			return returnValue;
  		}
	  
  		Map<Integer,ShoppingCartItemData> itemIdToShoppingCartItem = new HashMap<Integer,ShoppingCartItemData>();
  		List<Integer> itemIds = new ArrayList<Integer>();
  		Iterator<ShoppingCartItemData> shoppingCartItemIterator = shoppingCartItems.iterator();
  		while (shoppingCartItemIterator.hasNext()) {
  			ShoppingCartItemData shoppingCartItem = shoppingCartItemIterator.next();
  			ItemData item = shoppingCartItem.getProduct().getItemData();
  			Integer itemId = new Integer(item.getItemId());
  			itemIds.add(itemId);
  			itemIdToShoppingCartItem.put(itemId, shoppingCartItem);
  		}
   
		StringBuilder itemIdsManufacturersSQL = new StringBuilder(200);
		itemIdsManufacturersSQL.append("select i.item_id, i.short_desc, im.item_num manuf_sku ");
		itemIdsManufacturersSQL.append("from clw_item i, clw_item_mapping im, clw_property p where i.item_id in (");
		itemIdsManufacturersSQL.append(IdVector.toCommaString(itemIds));
		itemIdsManufacturersSQL.append(") and i.item_id = im.item_id and im.item_mapping_cd = '");
		itemIdsManufacturersSQL.append(RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
		itemIdsManufacturersSQL.append("' and i.item_status_cd = '");
		itemIdsManufacturersSQL.append(RefCodeNames.STATUS_CD.ACTIVE);
		itemIdsManufacturersSQL.append("' and im.bus_entity_id = p.bus_entity_id and p.short_desc = '");
		itemIdsManufacturersSQL.append(RefCodeNames.PROPERTY_TYPE_CD.MSDS_PLUGIN);
		itemIdsManufacturersSQL.append("' and p.clw_value = '");
		itemIdsManufacturersSQL.append(RefCodeNames.MSDS_PLUGIN_CD.DIVERSEY_WEB_SERVICES);
		itemIdsManufacturersSQL.append("' and p.property_type_cd = '");
		itemIdsManufacturersSQL.append(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
		itemIdsManufacturersSQL.append("'");

  		Map<Integer, String> itemManufacturerHM = new HashMap<Integer, String>();   
  		Map<Integer, String> itemShortDescHM = new HashMap<Integer, String>();
		
  		try {
           Statement stmt = con.createStatement();
           ResultSet rs = stmt.executeQuery(itemIdsManufacturersSQL.toString());
           while (rs.next()) {
               Integer itemIdI = new Integer(rs.getInt("item_id"));
               String manufSku = rs.getString("manuf_sku");
               String itemShortDesc = rs.getString("short_desc");
               itemManufacturerHM.put(itemIdI, manufSku);
               itemShortDescHM.put(itemIdI, itemShortDesc);
           }
           rs.close();
           stmt.close();
  		} 
  		catch (SQLException exc) {
	      log.info(exc.getMessage());
	      exc.printStackTrace();   
	      throw new RemoteException(exc.getMessage());
  		}
    
  		Iterator<Integer> itemIdIterator = itemIds.iterator();
  		while (itemIdIterator.hasNext()) {
  			Integer itemId = itemIdIterator.next();
  			//set "MSDS" or "MSDS Plug-in" 
  			if (itemManufacturerHM.get(itemId) != null) {
  				ShoppingCartItemData shoppingCartItem = itemIdToShoppingCartItem.get(itemId);
  				ProductData product = shoppingCartItem.getProduct();
  				String msds = product.getMsds();
  				if (!Utility.isSet(msds)) { 
  					//MSDS for this item is not set so retrieve PDF URL via JD Web Services
  					String pManufSku = (String) itemManufacturerHM.get(itemId);
  					try {
  						String wsMsdsUrl = collectDiverseyWebServicesURLs(pManufSku, pUserLocale, pCountryCode, pStoreLocale);
  						product.setProductJdWsUrl(wsMsdsUrl);
  					}
  					catch (RemoteException re) {
  						//add any item itds for which the web service call failed to the return value, so the
  						//caller can handle them appropriately
  						returnValue.add(itemId);
  					}
  				} 
  				else {
  					//MSDS for this item is set so nothing to do
  				}
  			}
  		}
  		return returnValue;
  }

    public static BusEntityData getCerifiedCompanyBE(Connection pCon, int pCertifiedCompId) {

        if (pCertifiedCompId == 0) {
            return BusEntityData.createValue();
        }
        BusEntityData pCertifiedCompD = null;
        try {
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, pCertifiedCompId);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.CERTIFIED_COMPANY);
            BusEntityDataVector v = BusEntityDataAccess.select(pCon, dbc);
            if (v != null && v.size() > 0) {
                pCertifiedCompD = (BusEntityData) v.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pCertifiedCompD = BusEntityData.createValue();
        }
        if (null == pCertifiedCompD) {
            pCertifiedCompD = BusEntityData.createValue();
        }
        return pCertifiedCompD;
    }


  private static java.util.HashMap mCostCenters = new java.util.HashMap();

  private static java.util.Date mCacheAge = new java.util.Date();

  private static CostCenterData fetchCostCenterData(Connection con,
      int costcenterid) throws SQLException, RemoteException {
    if (costcenterid > 0) {

      // check the cache age
      java.util.Date now = new java.util.Date();
      if ((now.getTime() - mCacheAge.getTime()) > 600000) {
        mCostCenters.clear();
      }

      Integer ccid = new Integer(costcenterid);
      if (mCostCenters.containsKey(ccid)) {
        return (CostCenterData) mCostCenters.get(ccid);
      }

      try {
        CostCenterData ccd = CostCenterDataAccess.select(con,
            costcenterid);
        mCostCenters.put(ccid, ccd);
        return ccd;
      } catch (DataNotFoundException e) {
        log.info("fetchCostCenterData: no data for"
            + " cost center id: " + costcenterid);
      }

    }

    return CostCenterData.createValue();
  }



  public static ShoppingCartItemDataVector  setActualSkus(String pStoreTypeCd, ShoppingCartItemDataVector pItems)
    {
      int size = pItems.size();
      if(pStoreTypeCd==null) {
        pStoreTypeCd="";
      }
      int type = 0;
      if(pStoreTypeCd.equals(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR)) {
        type = 1;
      }

      for(int ii=0; ii<size; ii++) {

        ShoppingCartItemData item = (ShoppingCartItemData) pItems.get(ii);

          if (item.isSkuValueMissing()) {

              String custSku = item.getProduct().getCustomerSkuNum();
              if (custSku != null && custSku.trim().length() > 0) {
                  item.setActualSkuNum(custSku);
                  item.setActualSkuType(ShoppingCartItemData.CATALOG_SKU);
                  item.setSkuValue(new SkuValue(custSku, ShoppingCartItemData.CATALOG_SKU));
              } else {
                  switch (type) {
                      case 1:
                          if (item.getProduct().getCatalogDistrMapping() == null) {
                              //set the item to use the manufacturer sku as the default if the distributor
                              //sku is empty.
                              item.setActualSkuNum(item.getProduct().getManufacturerSku());
                              item.setActualSkuType(ShoppingCartItemData.MANUFACTURER_SKU);
                              item.setSkuValue(new SkuValue(item.getProduct().getManufacturerSku(), ShoppingCartItemData.MANUFACTURER_SKU));
                          } else {
                              item.setActualSkuNum(item.getProduct().getCatalogDistrMapping().getItemNum());
                              item.setActualSkuType(ShoppingCartItemData.DISTRIBUTOR_SKU);
                              item.setSkuValue(new SkuValue(item.getProduct().getCatalogDistrMapping().getItemNum(), ShoppingCartItemData.DISTRIBUTOR_SKU));
                          }
                          break;
                      case 2:
                          item.setActualSkuNum(item.getProduct().getManufacturerSku());
                          item.setActualSkuType(ShoppingCartItemData.MANUFACTURER_SKU);
                          item.setSkuValue(new SkuValue(item.getProduct().getManufacturerSku(), ShoppingCartItemData.MANUFACTURER_SKU));
                          break;
                      default:
                          item.setActualSkuNum("" + item.getProduct().getSkuNum());
                          item.setActualSkuType(ShoppingCartItemData.CLW_SKU);
                          item.setSkuValue(new SkuValue("" +item.getProduct().getSkuNum(), ShoppingCartItemData.CLW_SKU));
                  }
              }
          }
      }
      return pItems;
    }

    public static void resetInventoryQty(Connection con, int pSiteId, int pItemId) throws SQLException {

        DBCriteria dbc1 = new DBCriteria();
        dbc1.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID, pSiteId);
        if (pItemId > 0) {
            dbc1.addEqualTo(InventoryLevelDataAccess.ITEM_ID, pItemId);
        }

        InventoryLevelDataVector invItems = InventoryLevelDataAccess.select(con, dbc1);
        for (int c2 = 0; invItems != null && c2 < invItems.size(); c2++) {
            InventoryLevelData ild = (InventoryLevelData) invItems.get(c2);
            ild.setQtyOnHand(null);
            ild.setOrderQty(null);
            ild.setModBy("ShoppingDAO.resetInventoryQty");
            InventoryLevelDataAccess.update(con, ild);
        }
    }

    /**
     * If returnSQL is true, will return sql select statement which will be used as in statement of other query.
     * If returnSQL is false, will return list of shopping items id
     * @param pShoppingItemRequest
     * @param returnSQL
     * @return
     * @throws SQLException
     */
    private static Object generateShoppingItemRequest(Connection pCon, ShoppingItemRequest pShoppingItemRequest, boolean returnSQL) throws SQLException {

        DBCriteria dbc = new DBCriteria();

        if (pShoppingItemRequest == null) {
            return dbc;
        }

        String specialItemReq = null;

        if (pShoppingItemRequest.isSpecialPermissionRequired()) {

            if (!pShoppingItemRequest.getSpecialPermission()) {

                dbc = new DBCriteria();
                dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getAccountCatalogId());
                dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

                DBCriteria isolDBC = new DBCriteria();
                isolDBC.addEqualTo(CatalogStructureDataAccess.SPECIAL_PERMISSION, String.valueOf(pShoppingItemRequest.getSpecialPermission()));
                DBCriteria orDbc = new DBCriteria();
                orDbc.addIsNull(CatalogStructureDataAccess.SPECIAL_PERMISSION);
                isolDBC.addOrCriteria(orDbc);

                dbc.addIsolatedCriterita(isolDBC);

                specialItemReq = CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.ITEM_ID, dbc);
            }

            log.info("specialItemReq: " + specialItemReq);
        }

        String csTable = CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE;
    	String ciTable = ContractItemDataAccess.CLW_CONTRACT_ITEM;
    	//bug # 4793 - Added not to show inactive distributor items in the catalog.
    	String beTable = BusEntityDataAccess.CLW_BUS_ENTITY;    	
    	dbc = new DBCriteria();
    	dbc.addDataAccessForJoin(new CatalogStructureDataAccess());

        dbc.addJoinTableEqualTo(csTable, CatalogStructureDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
        dbc.addJoinTableEqualTo(csTable, CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

        if (pShoppingItemRequest.getContractId() != 0 && pShoppingItemRequest.isContractItemsOnly()) {
        	dbc.addJoinTable(beTable);
        	dbc.addJoinCondition(csTable,CatalogStructureDataAccess.BUS_ENTITY_ID,beTable,BusEntityDataAccess.BUS_ENTITY_ID); 
        	dbc.addJoinTableNotEqualTo(beTable, BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
        	dbc.addJoinTable(ciTable);
        	dbc.addJoinCondition(csTable,CatalogStructureDataAccess.ITEM_ID,ciTable,ContractItemDataAccess.ITEM_ID);
        	dbc.addJoinTableEqualTo(ciTable, ContractItemDataAccess.CONTRACT_ID, pShoppingItemRequest.getContractId());
        	dbc.addDataAccessForJoin(new ContractItemDataAccess());

        }

        if (Utility.isSet(specialItemReq)) {
            dbc.addJoinTableOneOf(csTable, CatalogStructureDataAccess.ITEM_ID, specialItemReq);
        }

/* STJ-6114: Performance Improvements - Optimize Pollock 
        if (Utility.isSet(pShoppingItemRequest.getProductBundle())) {
            addProductBundleCriteria(dbc, pShoppingItemRequest, csTable);
        }
*/
        if(!returnSQL){
        	dbc.addJoinTableOrderBy(csTable, CatalogStructureDataAccess.ITEM_ID);
        }

        String shoppingItemsIdSelect = JoinDataAccess.getSqlSelectIdOnly(csTable, CatalogStructureDataAccess.ITEM_ID, dbc);
        log.info("generateShoppingItemRequest()=> shoppingItemsIdSelect : " + shoppingItemsIdSelect);
        if(returnSQL){
        	return shoppingItemsIdSelect;
        }else{
        	return JoinDataAccess.selectIdOnly(pCon, csTable, CatalogStructureDataAccess.ITEM_ID, dbc, 0);
        }

    }

    private static void addProductBundleCriteria(DBCriteria pCriteria, ShoppingItemRequest pShoppingItemRequest, String joinTable) {


        DBCriteria proprietaryListCriteria = new DBCriteria();

        proprietaryListCriteria.addJoinCondition(PriceListDetailDataAccess.PRICE_LIST_ID, PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC, PriceListAssocDataAccess.PRICE_LIST_ID);
        proprietaryListCriteria.addJoinCondition(PriceListDetailDataAccess.PRICE_LIST_ID, PriceListDataAccess.CLW_PRICE_LIST, PriceListDataAccess.PRICE_LIST_ID);
        proprietaryListCriteria.addJoinTableEqualTo(PriceListDataAccess.CLW_PRICE_LIST, PriceListDataAccess.PRICE_LIST_STATUS_CD, RefCodeNames.PRICE_LIST_STATUS_CD.ACTIVE);
        proprietaryListCriteria.addJoinTableEqualTo(PriceListDataAccess.CLW_PRICE_LIST, PriceListDataAccess.PRICE_LIST_TYPE_CD, RefCodeNames.PRICE_LIST_TYPE_CD.PROPRIETARY_LIST);

        if (pShoppingItemRequest.getProprietaryPriceListIds() != null && !pShoppingItemRequest.getProprietaryPriceListIds().isEmpty()) {
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, pShoppingItemRequest.getProprietaryPriceListIds());
            proprietaryListCriteria.addJoinTableNotOneOf(PriceListDataAccess.CLW_PRICE_LIST, PriceListDataAccess.PRICE_LIST_ID, pShoppingItemRequest.getProprietaryPriceListIds());
            proprietaryListCriteria.addNotOneOf(PriceListDetailDataAccess.ITEM_ID, PriceListDetailDataAccess.getSqlSelectIdOnly(PriceListDetailDataAccess.ITEM_ID, dbc));
        }

        pCriteria.addJoinTableNotOneOf(joinTable, PriceListDetailDataAccess.ITEM_ID, PriceListDetailDataAccess.getSqlSelectIdOnly(PriceListDetailDataAccess.ITEM_ID, proprietaryListCriteria));

        /*
        If site Product Bundle property equals:
        -	Catalog then buyers can buy any item from shopping catalog with exception of Proprietary Lists items not related to the site
        -	Price List then buyers can buy items from any Price List associated with the site if they present in the shopping catalog and do not present in any Proprietary List not associated with the site
        -	Order Guide then buyers can buy items from any Order Guide associated with the site if they present in the shopping catalog and do not present in any Proprietary List not associated with the site
        see BUG_ID 4188 Pollock Pricing Project
        */
        if (RefCodeNames.SITE_PRODUCT_BUNDLE.PRICE_LIST.equals(pShoppingItemRequest.getProductBundle())) {

            List<Integer> priceListsIds = new ArrayList<Integer>();

            if (pShoppingItemRequest.getPriceListRank1Id() != null) {
                priceListsIds.add(pShoppingItemRequest.getPriceListRank1Id());
            }

            if (pShoppingItemRequest.getPriceListRank2Id() != null) {
                priceListsIds.add(pShoppingItemRequest.getPriceListRank2Id());
            }

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, priceListsIds);

            pCriteria.addJoinTableOneOf(joinTable, CatalogStructureDataAccess.ITEM_ID, PriceListDetailDataAccess.getSqlSelectIdOnly(PriceListDetailDataAccess.ITEM_ID, dbc));

        } else if (RefCodeNames.SITE_PRODUCT_BUNDLE.ORDER_GUIDE.equals(pShoppingItemRequest.getProductBundle())) {

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, pShoppingItemRequest.getAvailableTemplateOrderGuideIds());

            pCriteria.addJoinTableOneOf(joinTable, CatalogStructureDataAccess.ITEM_ID, OrderGuideStructureDataAccess.getSqlSelectIdOnly(OrderGuideStructureDataAccess.ITEM_ID, dbc));

        }
    }

    //---- STJ-6114: Performance Improvements - Optimize Pollock --BEGIN
    public static void addProductBundleFilter( Connection pCon, ShoppingItemRequest pShoppingItemRequest) throws SQLException {

        DBCriteria proprietaryListCriteria = new DBCriteria();

        proprietaryListCriteria.addJoinCondition(PriceListDetailDataAccess.PRICE_LIST_ID, PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC, PriceListAssocDataAccess.PRICE_LIST_ID);
        proprietaryListCriteria.addJoinCondition(PriceListDetailDataAccess.PRICE_LIST_ID, PriceListDataAccess.CLW_PRICE_LIST, PriceListDataAccess.PRICE_LIST_ID);
        proprietaryListCriteria.addJoinTableEqualTo(PriceListDataAccess.CLW_PRICE_LIST, PriceListDataAccess.PRICE_LIST_STATUS_CD, RefCodeNames.PRICE_LIST_STATUS_CD.ACTIVE);
        proprietaryListCriteria.addJoinTableEqualTo(PriceListDataAccess.CLW_PRICE_LIST, PriceListDataAccess.PRICE_LIST_TYPE_CD, RefCodeNames.PRICE_LIST_TYPE_CD.PROPRIETARY_LIST);

        if (pShoppingItemRequest.getProprietaryPriceListIds() != null && !pShoppingItemRequest.getProprietaryPriceListIds().isEmpty()) {
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, pShoppingItemRequest.getProprietaryPriceListIds());
            proprietaryListCriteria.addJoinTableNotOneOf(PriceListDataAccess.CLW_PRICE_LIST, PriceListDataAccess.PRICE_LIST_ID, pShoppingItemRequest.getProprietaryPriceListIds());
            proprietaryListCriteria.addNotOneOf(PriceListDetailDataAccess.ITEM_ID, PriceListDetailDataAccess.getSqlSelectIdOnly(PriceListDetailDataAccess.ITEM_ID, dbc));
        }
        proprietaryListCriteria.addOrderBy(PriceListDetailDataAccess.ITEM_ID);
        log.debug("addProductBundleFilter() ===>EXCLUDE SQL:" + PriceListDetailDataAccess.getSqlSelectIdOnly(PriceListDetailDataAccess.ITEM_ID, proprietaryListCriteria));
        IdVector excIds = PriceListDetailDataAccess.selectIdOnly(pCon, PriceListDetailDataAccess.ITEM_ID, proprietaryListCriteria);
//        pCriteria.addJoinTableNotOneOf(joinTable, PriceListDetailDataAccess.ITEM_ID, PriceListDetailDataAccess.getSqlSelectIdOnly(PriceListDetailDataAccess.ITEM_ID, proprietaryListCriteria));

        /*
        If site Product Bundle property equals:
        -	Catalog then buyers can buy any item from shopping catalog with exception of Proprietary Lists items not related to the site
        -	Price List then buyers can buy items from any Price List associated with the site if they present in the shopping catalog and do not present in any Proprietary List not associated with the site
        -	Order Guide then buyers can buy items from any Order Guide associated with the site if they present in the shopping catalog and do not present in any Proprietary List not associated with the site
        see BUG_ID 4188 Pollock Pricing Project
        */
        IdVector incIds = null;
        if (RefCodeNames.SITE_PRODUCT_BUNDLE.PRICE_LIST.equals(pShoppingItemRequest.getProductBundle())) {
        	incIds = new IdVector();
            List<Integer> priceListsIds = new ArrayList<Integer>();

            if (pShoppingItemRequest.getPriceListRank1Id() != null) {
                priceListsIds.add(pShoppingItemRequest.getPriceListRank1Id());
            }

            if (pShoppingItemRequest.getPriceListRank2Id() != null) {
                priceListsIds.add(pShoppingItemRequest.getPriceListRank2Id());
            }

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(PriceListDetailDataAccess.PRICE_LIST_ID, priceListsIds);
            dbc.addOrderBy(PriceListDetailDataAccess.ITEM_ID);

            log.debug("addProductBundleFilter() ===>BUNDLE:Price List - INCLUDE - SQL:" + PriceListDetailDataAccess.getSqlSelectIdOnly(PriceListDetailDataAccess.ITEM_ID, dbc));
            incIds = PriceListDetailDataAccess.selectIdOnly(pCon, PriceListDetailDataAccess.ITEM_ID, dbc);
 //           pCriteria.addJoinTableOneOf(joinTable, CatalogStructureDataAccess.ITEM_ID, PriceListDetailDataAccess.getSqlSelectIdOnly(PriceListDetailDataAccess.ITEM_ID, dbc));

        } else if (RefCodeNames.SITE_PRODUCT_BUNDLE.ORDER_GUIDE.equals(pShoppingItemRequest.getProductBundle())) {
        	incIds = new IdVector();
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, pShoppingItemRequest.getAvailableTemplateOrderGuideIds());
            dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);

            log.info("addProductBundleFilter() ===>BUNDLE:Ordere Guide - INCLUDE - SQL:" + OrderGuideStructureDataAccess.getSqlSelectIdOnly(OrderGuideStructureDataAccess.ITEM_ID, dbc));
            incIds = OrderGuideStructureDataAccess.selectIdOnly(pCon, OrderGuideStructureDataAccess.ITEM_ID, dbc);
//            pCriteria.addJoinTableOneOf(joinTable, CatalogStructureDataAccess.ITEM_ID, OrderGuideStructureDataAccess.getSqlSelectIdOnly(OrderGuideStructureDataAccess.ITEM_ID, dbc));

        }
        pShoppingItemRequest.setExcProductBundleFilterIds(excIds);
        pShoppingItemRequest.setIncProductBundleFilterIds(incIds);
        
    }

    public static String getShoppingItemIdsRequest(Connection pCon, ShoppingItemRequest pShoppingItemRequest) throws SQLException {
        log.info("getShoppingItemIdsRequest() ===>BEGIN" );
    	String shoppingItemIdsSelectSql = pShoppingItemRequest.getShoppingItemIdsSelectSql();
    	if (!Utility.isSet(shoppingItemIdsSelectSql)){
    		shoppingItemIdsSelectSql = (String) generateShoppingItemRequest(pCon, pShoppingItemRequest,true);
    		pShoppingItemRequest.setShoppingItemIdsSelectSql(shoppingItemIdsSelectSql);
    		//---- STJ-6114: Performance Improvements - Optimize Pollock 
log.info("YK YYYYYY	Utility.isSet(pShoppingItemRequest.getExcProductBundleFilterIds(): "+Utility.isSet(pShoppingItemRequest.getExcProductBundleFilterIds()));
log.info("YK YYYYYY	Utility.isSet(pShoppingItemRequest.getExcProductBundleFilterIds(): "+Utility.isSet(pShoppingItemRequest.getIncProductBundleFilterIds()));
        	//----
        }
        log.info("getShoppingItemIdsRequest() ===>END" );
    	return shoppingItemIdsSelectSql;
    }

    public static IdVector getShoppingItemIds(Connection pCon, ShoppingItemRequest pShoppingItemRequest) throws SQLException {
        log.info("getShoppingItemIds() ===>BEGIN" );

    	IdVector itemIds = pShoppingItemRequest.getShoppingItemIds();
    	if (itemIds == null){
    		itemIds = (IdVector) generateShoppingItemRequest(pCon, pShoppingItemRequest, false);
	        if(itemIds.isEmpty()) {
	            itemIds.add(-1);
	        }
	    	//---- STJ-6114: Performance Improvements - Optimize Pollock 
	        log.info("getShoppingItemIds() ===>Optimize Pollock BEGIN: itemIds.size() =" + itemIds.size());
	        filterByProductBundle(pCon, itemIds, pShoppingItemRequest);
	    	log.info("getShoppingItemIds() ===>Optimize Pollock END: itemIds.size() =" + itemIds.size());
	        //----
	        
	        pShoppingItemRequest.setShoppingItemIds(itemIds);
    	}
        log.info("getShoppingItemIds() ===>END" );
        return itemIds;

    }
    
    public static void filterByProductBundle(Connection pCon, List pList, ShoppingItemRequest pShoppingItemRequest) throws SQLException{
        log.info("filterByProductBundle()=> Begin (by YK)"  );
        // this method can be applied for ordered lists only!
        if (!Utility.isSet(pList)){
    		return;
    	}
        if (Utility.isSet(pShoppingItemRequest.getProductBundle())){
    		IdVector excludeItemIds = pShoppingItemRequest.getExcProductBundleFilterIds();
    		IdVector includeItemIds = pShoppingItemRequest.getIncProductBundleFilterIds();
    		
    		log.info("filterByProductBundle() ===> excludeItemIds.size() =" + ((excludeItemIds!=null) ? excludeItemIds.size() : "0"));
    		log.info("filterByProductBundle() ===> includeItemIds.size() =" + ((includeItemIds!=null) ? includeItemIds.size() : "0"));
		
	    	int exclItemId = 0;
			int inclItemId = 0;
			Iterator iterExcl = null;
			if(excludeItemIds!=null) iterExcl = excludeItemIds.iterator();
			Iterator iterIncl = null;
			if(includeItemIds!=null) iterIncl = includeItemIds.iterator();
	        Iterator it = pList.iterator();
	         while(it.hasNext()){
	            Object obj = it.next();
	            if(obj!= null){
	                int id = Utility.getItemId(obj);
	                //log.info("filterByProductBundle()=> trying item" + id );
	    			while(iterExcl!=null && iterExcl.hasNext()) {
	    				if(exclItemId < id) {
	    					exclItemId = (Integer) iterExcl.next();
	     				} else {
	    					break;
	    				}
	    			}
	    			if(id == exclItemId) {
	    	 			log.info("filterByProductBundle()=> ProductBundle : " + pShoppingItemRequest.getProductBundle()+" ==> skipping proprietary itemId = " + id );
	    				it.remove();
	    				continue;
	    			}
	    			if(iterIncl!=null) {
	    				while(iterIncl.hasNext()) {
	    					if(inclItemId < id) {
	    						inclItemId = (Integer) iterIncl.next();				
	    					} else {
	    						break;
	    					}
	    				}
	    				if(id != inclItemId) {
	    					log.info("filterByProductBundle()=> ProductBundle : " + pShoppingItemRequest.getProductBundle()+" ==> skipping not price list itemId = " + id );
	    					it.remove();
	    					continue;
	    				}
	    			}
	            }                 
	         }
        }  
        log.info("filterByProductBundle()=> End (by YK)"  );

    }
    
    public static void filterByProductBundle(Connection pCon, IdVector itemIds, ShoppingItemRequest pShoppingItemRequest) throws SQLException{
    	if (!Utility.isSet(itemIds)){
    		return;
    	}
        if (Utility.isSet(pShoppingItemRequest.getProductBundle())){
    		IdVector excludeItemIds = pShoppingItemRequest.getExcProductBundleFilterIds();
    		IdVector includeItemIds = pShoppingItemRequest.getIncProductBundleFilterIds();
    		
    		log.info("filterByProductBundle() ===> INPUT: itemIds number =" + itemIds.size());
    		log.info("filterByProductBundle() ===> excludeItemIds.size() =" + ((excludeItemIds!=null) ? excludeItemIds.size() : "0"));
    		log.info("filterByProductBundle() ===> includeItemIds.size() =" + ((includeItemIds!=null) ? includeItemIds.size() : "0"));
   		
    		if(Utility.isSet(includeItemIds)) {
    			itemIds.retainAll(includeItemIds);
    		}
    		
    		if(Utility.isSet(excludeItemIds)) {
    			itemIds.removeAll(excludeItemIds);
    		}
    		log.info("filterByProductBundle() ===> RESULT: itemIds number =" + itemIds.size());
    	}	
    }
	//---- STJ-6114: Performance Improvements - Optimize Pollock  --END

    public static IdVector getSpecialPermssionItemIds(Connection pCon, int pAccountCatalogId) throws SQLException {

        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pAccountCatalogId);
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
        dbc.addEqualTo(CatalogStructureDataAccess.SPECIAL_PERMISSION, String.valueOf(true));

        return CatalogStructureDataAccess.selectIdOnly(pCon, CatalogStructureDataAccess.ITEM_ID, dbc);
    }

    public static ShoppingItemRequest createShoppingItemRequest(Connection pCon, UserData pUser, SiteData pSite) throws Exception {

        return createShoppingItemRequest(pCon,
                pSite.getSiteId(),
                pSite.getAccountId(),
                pSite.getSiteCatalogId(),
                pSite.getContractData().getContractId(),
                pSite.getPriceListRank1Id(),
                pSite.getPriceListRank2Id(),
                pSite.getProprietaryPriceListIds(),
                pSite.getAvailableTemplateOrderGuideIds(),
                pSite.getProductBundle(),
                pUser);
    }

    public static ShoppingItemRequest createShoppingItemRequest(Connection pCon,
                                                                int pSiteId,
                                                                int pAccopuntId,
                                                                int pSoppingCatalogId,
                                                                int pContractId,
                                                                Integer pPriceListRank1Id,
                                                                Integer pPriceListRank2Id,
                                                                IdVector pProprietaryPriceListIds,
                                                                List<Integer> pAvailableTemplateOrderGuideIds,
                                                                String pProductBundle,
                                                                UserData pUser) throws Exception {


        log.info("createShoppingItemRequestt()=> BEGIN");

        ShoppingItemRequest shoppingItemRequest;

        int storeId = BusEntityDAO.getStoreForAccount(pCon, pAccopuntId);

        log.info("createShoppingItemRequest()=> StoreId:" + storeId + ", AccountId:" + pAccopuntId);

        UserRightsTool urt = new UserRightsTool(pUser);
        PropertyUtil pu = new PropertyUtil(pCon);

        String specPermissionVal = pu.fetchValueIgnoreMissing(0, storeId, RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SPECIAL_PRTMISSION_ITEMS);

        log.info("createShoppingItemRequest()=> specPermissionVal:" + specPermissionVal);
        log.info("createShoppingItemRequest()=> pProductBundle:" + pProductBundle);

        if (Utility.isTrue(specPermissionVal)) {

            CatalogData accountCatalog = BusEntityDAO.getAccountCatalog(pCon, pAccopuntId);

            Group groupEjb = APIAccess.getAPIAccess().getGroupAPI();
            Map groupAssociations = groupEjb.getAllValidUserGroupAssociations(pUser.getUserId());
            Set authorizedForFunctions = (Set) groupAssociations.get(RefCodeNames.GROUP_ASSOC_CD.FUNCTION_OF_GROUP);

            shoppingItemRequest = new ShoppingItemRequest(pSiteId,
                    accountCatalog.getCatalogId(),
                    pSoppingCatalogId,
                    pContractId,
                    pPriceListRank1Id,
                    pPriceListRank2Id,
                    pProprietaryPriceListIds,
                    pAvailableTemplateOrderGuideIds,
                    urt.isUserOnContract(),
                    Utility.isTrue(specPermissionVal),
                    authorizedForFunctions != null && authorizedForFunctions.contains(RefCodeNames.APPLICATION_FUNCTIONS.SPECIAL_PERMISSION_ITEMS),
                    pProductBundle);

        } else {

            shoppingItemRequest = new ShoppingItemRequest(pSiteId,
                    0,
                    pSoppingCatalogId,
                    pContractId,
                    pPriceListRank1Id,
                    pPriceListRank2Id,
                    pProprietaryPriceListIds,
                    pAvailableTemplateOrderGuideIds,
                    urt.isUserOnContract(),
                    false,
                    false,
                    pProductBundle);
        }

        log.info("createShoppingItemRequestt()=> END.");

        return shoppingItemRequest;

    }

    public static ProductBundle getProductBundle(Connection pCon, String pStoreType, int pSiteId, ProductDataVector pSiteProducts) throws RemoteException {
        PropertyUtil propertyUtil = new PropertyUtil(pCon);
        String productBundle = propertyUtil.fetchValueIgnoreMissing(0, pSiteId, RefCodeNames.PROPERTY_TYPE_CD.SITE_PRODUCT_BUNDLE);
        if (RefCodeNames.SITE_PRODUCT_BUNDLE.PRICE_LIST.equals(productBundle)) {
            return new PriceListProductBundle(pStoreType, pSiteId, pSiteProducts);
        } else if (RefCodeNames.SITE_PRODUCT_BUNDLE.ORDER_GUIDE.equals(productBundle)) {
            return new OrderGuideProductBundle(pStoreType, pSiteId, pSiteProducts);
        } else if (RefCodeNames.SITE_PRODUCT_BUNDLE.CATALOG.equals(productBundle)) {
            return new CatalogProductBundle(pStoreType, pSiteId, pSiteProducts);
        } else {
            return new DefaultProductBundle(pStoreType, pSiteId, pSiteProducts);
        }
    }

    public static boolean isUseProductBundle(Connection pCon, int pSiteId) throws RemoteException {
        return Utility.isSet(getProductBundleValue(pCon,pSiteId));
    }

    public static String getProductBundleValue(Connection pCon, int pSiteId) {
        String value = null;
        if (pSiteId > 0) {
            try {
                PropertyUtil propertyUtil = new PropertyUtil(pCon);
                value = propertyUtil.fetchValueIgnoreMissing(0, pSiteId, RefCodeNames.PROPERTY_TYPE_CD.SITE_PRODUCT_BUNDLE);
            } catch (Exception e) {
                log.error(e);
            }
        }
        return value;
    }


    public static OrderGuideDataVector getTemplateOrderGuides(Connection pCon, int pCatalogId, int pSiteId) throws SQLException {
        DBCriteria dbc = new DBCriteria();

        if (pSiteId != 0) {
            StringBuffer sqlOrBuf = new StringBuffer();
            sqlOrBuf.append("(");
            //site specific order guides
            sqlOrBuf.append("(");
            sqlOrBuf.append(OrderGuideDataAccess.BUS_ENTITY_ID);
            sqlOrBuf.append("=");
            sqlOrBuf.append(pSiteId);
            sqlOrBuf.append(" AND ");
            sqlOrBuf.append(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD);
            sqlOrBuf.append(" = '");
            sqlOrBuf.append(RefCodeNames.ORDER_GUIDE_TYPE_CD.SITE_ORDER_GUIDE_TEMPLATE);
            sqlOrBuf.append("')");
            sqlOrBuf.append(" or ");  //start major or
            //general order guides, or template order guides
            sqlOrBuf.append("(");
            sqlOrBuf.append(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD);
            sqlOrBuf.append(" = '");
            sqlOrBuf.append(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
            sqlOrBuf.append("'");
            sqlOrBuf.append(" and ");
            sqlOrBuf.append(OrderGuideDataAccess.CATALOG_ID);
            sqlOrBuf.append(" = ");
            sqlOrBuf.append(pCatalogId);
            sqlOrBuf.append(")");
            sqlOrBuf.append(")");
            dbc.addCondition(sqlOrBuf.toString());
        } else {
            dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pCatalogId);
            LinkedList tmtypes = new LinkedList();
            tmtypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
            dbc.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, tmtypes);
        }

        dbc.addOrderBy(OrderGuideDataAccess.SHORT_DESC);
        log.info("Order guide select: " + OrderGuideDataAccess.getSqlSelectIdOnly("*", dbc));

        return OrderGuideDataAccess.select(pCon, dbc);
    }

    public static OrderGuideDataVector getCustomOrderGuides(Connection con, int pAccountId, int pSiteId) throws SQLException {

        OrderGuideDataVector orderGuides;

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID, pSiteId);
        dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, RefCodeNames.ORDER_GUIDE_TYPE_CD.CUSTOM_ORDER_GUIDE);
        dbc.addOrderBy(OrderGuideDataAccess.SHORT_DESC);

        orderGuides = OrderGuideDataAccess.select(con, dbc);

        if (orderGuides.isEmpty()) {

            dbc = new DBCriteria();
            dbc.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID, pAccountId);
            dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, RefCodeNames.ORDER_GUIDE_TYPE_CD.CUSTOM_ORDER_GUIDE);
            dbc.addOrderBy(OrderGuideDataAccess.SHORT_DESC);

            orderGuides = OrderGuideDataAccess.select(con, dbc);
        }

        return orderGuides;
    }


    public static BigDecimal getContractItemPrice(Connection pCon, int pStoreId, int pSiteId, int pContractId, int pCatalogId, int pItemId) throws Exception {

        log.info("getContractItemPrice()=> BEGIN," +
                ", \n pStoreId: " + pStoreId +
                ", \n pSiteId: " + pSiteId +
                ", \n pContractId: " +  pContractId +
                ", \n pCatalogId: " + pCatalogId +
                ", \n pItemId: " + pItemId
        );

        String productBundleValue = ShoppingDAO.getProductBundleValue(pCon, pSiteId);

        log.info("getContractItemPrice()=> productBundleValue: " + productBundleValue);

        if (Utility.isSet(productBundleValue)) {

            PropertyUtil pu = new PropertyUtil(pCon);
            String storeType = pu.fetchValueIgnoreMissing(0, pStoreId, RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);

            log.info("process()=> storeType : " + storeType);

            ProductData pd = APIAccess.getAPIAccess().getCatalogInformationAPI()
                    .getCatalogClwProduct(
                            pCatalogId,
                            pItemId,
                            0,
                            pSiteId);

            ProductDataVector products = new ProductDataVector();
            products.add(pd);

            ProductBundle bundle = ShoppingDAO.getProductBundle(
                    pCon,
                    storeType,
                    pSiteId,
                    products
            );

            BigDecimal price = null;
            if (bundle != null) {
                PriceValue priceVal = bundle.getPriceValue(pItemId);
                if (priceVal != null && priceVal.getValue() != null) {
                    price = priceVal.getValue();
                }
            }

            if (price != null) {
                return price;
            } else {
                throw new Exception("Unable to get contract price for contrctId=" + pContractId + ", itemId=" + pItemId);
            }
        } else {
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
            dbc.addEqualTo(ContractItemDataAccess.ITEM_ID, pItemId);
            ContractItemDataVector citemV = ContractItemDataAccess.select(pCon, dbc);
            if (citemV.size() > 0) {
                return ((ContractItemData) citemV.get(0)).getAmount();
            } else {
                throw new Exception("Unable to get contract price for contrctId=" + pContractId + ", itemId=" + pItemId);
            }
        }
    }
    
    private static String collectDiverseyWebServicesURLs(String manufSku, String userLocale, String countryCode, String storeLocale) 
    		throws RemoteException {	        
    	//split user locale into language code and country code
        String delimiter = "_";
        String[] localeArray = userLocale.split(delimiter);
        String languageCode = localeArray[0];
        String wsMsdsUrl = null;
        
    	try {
    		wsMsdsUrl = DiverseyMSDSRetrieve.retrieveMsdsUrl(manufSku, countryCode, languageCode);
    	    if (!Utility.isSet(wsMsdsUrl)) {
    	    	//use Store's locale to set up country code and language code
    	        //Sting storeLocale = appUser.getUserStore().getBusEntity().getLocaleCd().trim(); 
    	        localeArray = storeLocale.split(delimiter);
    	        languageCode = localeArray[0];
    	        countryCode = localeArray[1];
        	    wsMsdsUrl = DiverseyMSDSRetrieve.retrieveMsdsUrl(manufSku.trim(), countryCode, languageCode);
    	    }    	       
    	} 
    	catch (ServiceException exc) {
    		log.info(exc.getMessage());
    	    exc.printStackTrace();
    	    throw new RemoteException("Error. ShoppingDAO.collectDiverseyWebServicesURLs: ServiceException:" + exc.getMessage());
    	} 
    	catch (SOAPException exc) {
    		log.info(exc.getMessage());
    	    exc.printStackTrace();
    	    throw new RemoteException("Error. ShoppingDAO.collectDiverseyWebServicesURLs: SOAPException:" + exc.getMessage());
    	}
    	catch (MalformedURLException exc) {
    		log.info(exc.getMessage());
    	    exc.printStackTrace();
    	    throw new RemoteException("Error. ShoppingDAO.collectDiverseyWebServicesURLs: MalformedURLException:" + exc.getMessage());
    	} 
    	catch (IOException exc) {
    		log.info(exc.getMessage());
    	    exc.printStackTrace();
    	    throw new RemoteException("Error. ShoppingDAO.collectDiverseyWebServicesURLs: IOException:" + exc.getMessage());
    	} 
    	catch (Exception exc) {
    		log.info(exc.getMessage());
    	    exc.printStackTrace();
    	    throw new RemoteException("Error. ShoppingDAO.collectDiverseyWebServicesURLs: Exception:" + exc.getMessage());
    	}

    	return wsMsdsUrl;
    }
    

}

