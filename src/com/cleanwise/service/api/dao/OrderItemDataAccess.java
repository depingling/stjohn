
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderItemDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_ITEM table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>OrderItemDataAccess</code>
 */
public class OrderItemDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderItemDataAccess.class.getName());

    /** <code>CLW_ORDER_ITEM</code> table name */
	/* Primary key: ORDER_ITEM_ID */
	
    public static final String CLW_ORDER_ITEM = "CLW_ORDER_ITEM";
    
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_ORDER_ITEM */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>ORDER_ITEM_ID</code> ORDER_ITEM_ID column of table CLW_ORDER_ITEM */
    public static final String ORDER_ITEM_ID = "ORDER_ITEM_ID";
    /** <code>ORDER_ITEM_STATUS_CD</code> ORDER_ITEM_STATUS_CD column of table CLW_ORDER_ITEM */
    public static final String ORDER_ITEM_STATUS_CD = "ORDER_ITEM_STATUS_CD";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_ORDER_ITEM */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>EXCEPTION_IND</code> EXCEPTION_IND column of table CLW_ORDER_ITEM */
    public static final String EXCEPTION_IND = "EXCEPTION_IND";
    /** <code>ORDER_LINE_NUM</code> ORDER_LINE_NUM column of table CLW_ORDER_ITEM */
    public static final String ORDER_LINE_NUM = "ORDER_LINE_NUM";
    /** <code>CUST_LINE_NUM</code> CUST_LINE_NUM column of table CLW_ORDER_ITEM */
    public static final String CUST_LINE_NUM = "CUST_LINE_NUM";
    /** <code>ERP_PO_LINE_NUM</code> ERP_PO_LINE_NUM column of table CLW_ORDER_ITEM */
    public static final String ERP_PO_LINE_NUM = "ERP_PO_LINE_NUM";
    /** <code>ERP_ORDER_LINE_NUM</code> ERP_ORDER_LINE_NUM column of table CLW_ORDER_ITEM */
    public static final String ERP_ORDER_LINE_NUM = "ERP_ORDER_LINE_NUM";
    /** <code>ERP_ORDER_NUM</code> ERP_ORDER_NUM column of table CLW_ORDER_ITEM */
    public static final String ERP_ORDER_NUM = "ERP_ORDER_NUM";
    /** <code>ERP_PO_NUM</code> ERP_PO_NUM column of table CLW_ORDER_ITEM */
    public static final String ERP_PO_NUM = "ERP_PO_NUM";
    /** <code>ERP_PO_DATE</code> ERP_PO_DATE column of table CLW_ORDER_ITEM */
    public static final String ERP_PO_DATE = "ERP_PO_DATE";
    /** <code>ERP_PO_TIME</code> ERP_PO_TIME column of table CLW_ORDER_ITEM */
    public static final String ERP_PO_TIME = "ERP_PO_TIME";
    /** <code>ITEM_SKU_NUM</code> ITEM_SKU_NUM column of table CLW_ORDER_ITEM */
    public static final String ITEM_SKU_NUM = "ITEM_SKU_NUM";
    /** <code>ITEM_SHORT_DESC</code> ITEM_SHORT_DESC column of table CLW_ORDER_ITEM */
    public static final String ITEM_SHORT_DESC = "ITEM_SHORT_DESC";
    /** <code>ITEM_UOM</code> ITEM_UOM column of table CLW_ORDER_ITEM */
    public static final String ITEM_UOM = "ITEM_UOM";
    /** <code>ITEM_PACK</code> ITEM_PACK column of table CLW_ORDER_ITEM */
    public static final String ITEM_PACK = "ITEM_PACK";
    /** <code>ITEM_SIZE</code> ITEM_SIZE column of table CLW_ORDER_ITEM */
    public static final String ITEM_SIZE = "ITEM_SIZE";
    /** <code>ITEM_COST</code> ITEM_COST column of table CLW_ORDER_ITEM */
    public static final String ITEM_COST = "ITEM_COST";
    /** <code>CUST_ITEM_SKU_NUM</code> CUST_ITEM_SKU_NUM column of table CLW_ORDER_ITEM */
    public static final String CUST_ITEM_SKU_NUM = "CUST_ITEM_SKU_NUM";
    /** <code>CUST_ITEM_SHORT_DESC</code> CUST_ITEM_SHORT_DESC column of table CLW_ORDER_ITEM */
    public static final String CUST_ITEM_SHORT_DESC = "CUST_ITEM_SHORT_DESC";
    /** <code>CUST_ITEM_UOM</code> CUST_ITEM_UOM column of table CLW_ORDER_ITEM */
    public static final String CUST_ITEM_UOM = "CUST_ITEM_UOM";
    /** <code>CUST_ITEM_PACK</code> CUST_ITEM_PACK column of table CLW_ORDER_ITEM */
    public static final String CUST_ITEM_PACK = "CUST_ITEM_PACK";
    /** <code>CUST_CONTRACT_PRICE</code> CUST_CONTRACT_PRICE column of table CLW_ORDER_ITEM */
    public static final String CUST_CONTRACT_PRICE = "CUST_CONTRACT_PRICE";
    /** <code>CUST_CONTRACT_ID</code> CUST_CONTRACT_ID column of table CLW_ORDER_ITEM */
    public static final String CUST_CONTRACT_ID = "CUST_CONTRACT_ID";
    /** <code>CUST_CONTRACT_SHORT_DESC</code> CUST_CONTRACT_SHORT_DESC column of table CLW_ORDER_ITEM */
    public static final String CUST_CONTRACT_SHORT_DESC = "CUST_CONTRACT_SHORT_DESC";
    /** <code>MANU_ITEM_SKU_NUM</code> MANU_ITEM_SKU_NUM column of table CLW_ORDER_ITEM */
    public static final String MANU_ITEM_SKU_NUM = "MANU_ITEM_SKU_NUM";
    /** <code>MANU_ITEM_SHORT_DESC</code> MANU_ITEM_SHORT_DESC column of table CLW_ORDER_ITEM */
    public static final String MANU_ITEM_SHORT_DESC = "MANU_ITEM_SHORT_DESC";
    /** <code>MANU_ITEM_MSRP</code> MANU_ITEM_MSRP column of table CLW_ORDER_ITEM */
    public static final String MANU_ITEM_MSRP = "MANU_ITEM_MSRP";
    /** <code>MANU_ITEM_UPC_NUM</code> MANU_ITEM_UPC_NUM column of table CLW_ORDER_ITEM */
    public static final String MANU_ITEM_UPC_NUM = "MANU_ITEM_UPC_NUM";
    /** <code>MANU_PACK_UPC_NUM</code> MANU_PACK_UPC_NUM column of table CLW_ORDER_ITEM */
    public static final String MANU_PACK_UPC_NUM = "MANU_PACK_UPC_NUM";
    /** <code>DIST_ITEM_SKU_NUM</code> DIST_ITEM_SKU_NUM column of table CLW_ORDER_ITEM */
    public static final String DIST_ITEM_SKU_NUM = "DIST_ITEM_SKU_NUM";
    /** <code>DIST_ITEM_SHORT_DESC</code> DIST_ITEM_SHORT_DESC column of table CLW_ORDER_ITEM */
    public static final String DIST_ITEM_SHORT_DESC = "DIST_ITEM_SHORT_DESC";
    /** <code>DIST_ITEM_UOM</code> DIST_ITEM_UOM column of table CLW_ORDER_ITEM */
    public static final String DIST_ITEM_UOM = "DIST_ITEM_UOM";
    /** <code>DIST_ITEM_PACK</code> DIST_ITEM_PACK column of table CLW_ORDER_ITEM */
    public static final String DIST_ITEM_PACK = "DIST_ITEM_PACK";
    /** <code>DIST_ITEM_COST</code> DIST_ITEM_COST column of table CLW_ORDER_ITEM */
    public static final String DIST_ITEM_COST = "DIST_ITEM_COST";
    /** <code>DIST_ORDER_NUM</code> DIST_ORDER_NUM column of table CLW_ORDER_ITEM */
    public static final String DIST_ORDER_NUM = "DIST_ORDER_NUM";
    /** <code>DIST_ERP_NUM</code> DIST_ERP_NUM column of table CLW_ORDER_ITEM */
    public static final String DIST_ERP_NUM = "DIST_ERP_NUM";
    /** <code>DIST_ITEM_QUANTITY</code> DIST_ITEM_QUANTITY column of table CLW_ORDER_ITEM */
    public static final String DIST_ITEM_QUANTITY = "DIST_ITEM_QUANTITY";
    /** <code>TOTAL_QUANTITY_ORDERED</code> TOTAL_QUANTITY_ORDERED column of table CLW_ORDER_ITEM */
    public static final String TOTAL_QUANTITY_ORDERED = "TOTAL_QUANTITY_ORDERED";
    /** <code>TOTAL_QUANTITY_SHIPPED</code> TOTAL_QUANTITY_SHIPPED column of table CLW_ORDER_ITEM */
    public static final String TOTAL_QUANTITY_SHIPPED = "TOTAL_QUANTITY_SHIPPED";
    /** <code>COMMENTS</code> COMMENTS column of table CLW_ORDER_ITEM */
    public static final String COMMENTS = "COMMENTS";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_ITEM */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_ITEM */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_ITEM */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_ITEM */
    public static final String MOD_BY = "MOD_BY";
    /** <code>ACK_STATUS_CD</code> ACK_STATUS_CD column of table CLW_ORDER_ITEM */
    public static final String ACK_STATUS_CD = "ACK_STATUS_CD";
    /** <code>PURCHASE_ORDER_ID</code> PURCHASE_ORDER_ID column of table CLW_ORDER_ITEM */
    public static final String PURCHASE_ORDER_ID = "PURCHASE_ORDER_ID";
    /** <code>TARGET_SHIP_DATE</code> TARGET_SHIP_DATE column of table CLW_ORDER_ITEM */
    public static final String TARGET_SHIP_DATE = "TARGET_SHIP_DATE";
    /** <code>QUANTITY_CONFIRMED</code> QUANTITY_CONFIRMED column of table CLW_ORDER_ITEM */
    public static final String QUANTITY_CONFIRMED = "QUANTITY_CONFIRMED";
    /** <code>QUANTITY_BACKORDERED</code> QUANTITY_BACKORDERED column of table CLW_ORDER_ITEM */
    public static final String QUANTITY_BACKORDERED = "QUANTITY_BACKORDERED";
    /** <code>COST_CENTER_ID</code> COST_CENTER_ID column of table CLW_ORDER_ITEM */
    public static final String COST_CENTER_ID = "COST_CENTER_ID";
    /** <code>ORDER_ROUTING_ID</code> ORDER_ROUTING_ID column of table CLW_ORDER_ITEM */
    public static final String ORDER_ROUTING_ID = "ORDER_ROUTING_ID";
    /** <code>ITEM_QTY_855</code> ITEM_QTY_855 column of table CLW_ORDER_ITEM */
    public static final String ITEM_QTY_855 = "ITEM_QTY_855";
    /** <code>FREIGHT_HANDLER_ID</code> FREIGHT_HANDLER_ID column of table CLW_ORDER_ITEM */
    public static final String FREIGHT_HANDLER_ID = "FREIGHT_HANDLER_ID";
    /** <code>INVENTORY_PAR_VALUE</code> INVENTORY_PAR_VALUE column of table CLW_ORDER_ITEM */
    public static final String INVENTORY_PAR_VALUE = "INVENTORY_PAR_VALUE";
    /** <code>INVENTORY_QTY_ON_HAND</code> INVENTORY_QTY_ON_HAND column of table CLW_ORDER_ITEM */
    public static final String INVENTORY_QTY_ON_HAND = "INVENTORY_QTY_ON_HAND";
    /** <code>SALE_TYPE_CD</code> SALE_TYPE_CD column of table CLW_ORDER_ITEM */
    public static final String SALE_TYPE_CD = "SALE_TYPE_CD";
    /** <code>DIST_BASE_COST</code> DIST_BASE_COST column of table CLW_ORDER_ITEM */
    public static final String DIST_BASE_COST = "DIST_BASE_COST";
    /** <code>DIST_UOM_CONV_MULTIPLIER</code> DIST_UOM_CONV_MULTIPLIER column of table CLW_ORDER_ITEM */
    public static final String DIST_UOM_CONV_MULTIPLIER = "DIST_UOM_CONV_MULTIPLIER";
    /** <code>DIST_UOM_CONV_COST</code> DIST_UOM_CONV_COST column of table CLW_ORDER_ITEM */
    public static final String DIST_UOM_CONV_COST = "DIST_UOM_CONV_COST";
    /** <code>TOTAL_QUANTITY_RECEIVED</code> TOTAL_QUANTITY_RECEIVED column of table CLW_ORDER_ITEM */
    public static final String TOTAL_QUANTITY_RECEIVED = "TOTAL_QUANTITY_RECEIVED";
    /** <code>ERP_PO_REF_DATE</code> ERP_PO_REF_DATE column of table CLW_ORDER_ITEM */
    public static final String ERP_PO_REF_DATE = "ERP_PO_REF_DATE";
    /** <code>ERP_PO_REF_NUM</code> ERP_PO_REF_NUM column of table CLW_ORDER_ITEM */
    public static final String ERP_PO_REF_NUM = "ERP_PO_REF_NUM";
    /** <code>ERP_PO_REF_LINE_NUM</code> ERP_PO_REF_LINE_NUM column of table CLW_ORDER_ITEM */
    public static final String ERP_PO_REF_LINE_NUM = "ERP_PO_REF_LINE_NUM";
    /** <code>ASSET_ID</code> ASSET_ID column of table CLW_ORDER_ITEM */
    public static final String ASSET_ID = "ASSET_ID";
    /** <code>TAX_RATE</code> TAX_RATE column of table CLW_ORDER_ITEM */
    public static final String TAX_RATE = "TAX_RATE";
    /** <code>TAX_AMOUNT</code> TAX_AMOUNT column of table CLW_ORDER_ITEM */
    public static final String TAX_AMOUNT = "TAX_AMOUNT";
    /** <code>TAX_EXEMPT</code> TAX_EXEMPT column of table CLW_ORDER_ITEM */
    public static final String TAX_EXEMPT = "TAX_EXEMPT";
    /** <code>OUTBOUND_PO_NUM</code> OUTBOUND_PO_NUM column of table CLW_ORDER_ITEM */
    public static final String OUTBOUND_PO_NUM = "OUTBOUND_PO_NUM";
    /** <code>SERVICE_FEE</code> SERVICE_FEE column of table CLW_ORDER_ITEM */
    public static final String SERVICE_FEE = "SERVICE_FEE";

    /**
     * Constructor.
     */
    public OrderItemDataAccess()
    {
    }

    /**
     * Gets a OrderItemData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderItemId The key requested.
     * @return new OrderItemData()
     * @throws            SQLException
     */
    public static OrderItemData select(Connection pCon, int pOrderItemId)
        throws SQLException, DataNotFoundException {
        OrderItemData x=null;
        String sql="SELECT ORDER_ID,ORDER_ITEM_ID,ORDER_ITEM_STATUS_CD,ITEM_ID,EXCEPTION_IND,ORDER_LINE_NUM,CUST_LINE_NUM,ERP_PO_LINE_NUM,ERP_ORDER_LINE_NUM,ERP_ORDER_NUM,ERP_PO_NUM,ERP_PO_DATE,ERP_PO_TIME,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_SIZE,ITEM_COST,CUST_ITEM_SKU_NUM,CUST_ITEM_SHORT_DESC,CUST_ITEM_UOM,CUST_ITEM_PACK,CUST_CONTRACT_PRICE,CUST_CONTRACT_ID,CUST_CONTRACT_SHORT_DESC,MANU_ITEM_SKU_NUM,MANU_ITEM_SHORT_DESC,MANU_ITEM_MSRP,MANU_ITEM_UPC_NUM,MANU_PACK_UPC_NUM,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC,DIST_ITEM_UOM,DIST_ITEM_PACK,DIST_ITEM_COST,DIST_ORDER_NUM,DIST_ERP_NUM,DIST_ITEM_QUANTITY,TOTAL_QUANTITY_ORDERED,TOTAL_QUANTITY_SHIPPED,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ACK_STATUS_CD,PURCHASE_ORDER_ID,TARGET_SHIP_DATE,QUANTITY_CONFIRMED,QUANTITY_BACKORDERED,COST_CENTER_ID,ORDER_ROUTING_ID,ITEM_QTY_855,FREIGHT_HANDLER_ID,INVENTORY_PAR_VALUE,INVENTORY_QTY_ON_HAND,SALE_TYPE_CD,DIST_BASE_COST,DIST_UOM_CONV_MULTIPLIER,DIST_UOM_CONV_COST,TOTAL_QUANTITY_RECEIVED,ERP_PO_REF_DATE,ERP_PO_REF_NUM,ERP_PO_REF_LINE_NUM,ASSET_ID,TAX_RATE,TAX_AMOUNT,TAX_EXEMPT,OUTBOUND_PO_NUM,SERVICE_FEE FROM CLW_ORDER_ITEM WHERE ORDER_ITEM_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderItemId=" + pOrderItemId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderItemId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderItemData.createValue();
            
            x.setOrderId(rs.getInt(1));
            x.setOrderItemId(rs.getInt(2));
            x.setOrderItemStatusCd(rs.getString(3));
            x.setItemId(rs.getInt(4));
            x.setExceptionInd(rs.getString(5));
            x.setOrderLineNum(rs.getInt(6));
            x.setCustLineNum(rs.getInt(7));
            x.setErpPoLineNum(rs.getInt(8));
            x.setErpOrderLineNum(rs.getInt(9));
            x.setErpOrderNum(rs.getString(10));
            x.setErpPoNum(rs.getString(11));
            x.setErpPoDate(rs.getDate(12));
            x.setErpPoTime(rs.getTimestamp(13));
            x.setItemSkuNum(rs.getInt(14));
            x.setItemShortDesc(rs.getString(15));
            x.setItemUom(rs.getString(16));
            x.setItemPack(rs.getString(17));
            x.setItemSize(rs.getString(18));
            x.setItemCost(rs.getBigDecimal(19));
            x.setCustItemSkuNum(rs.getString(20));
            x.setCustItemShortDesc(rs.getString(21));
            x.setCustItemUom(rs.getString(22));
            x.setCustItemPack(rs.getString(23));
            x.setCustContractPrice(rs.getBigDecimal(24));
            x.setCustContractId(rs.getInt(25));
            x.setCustContractShortDesc(rs.getString(26));
            x.setManuItemSkuNum(rs.getString(27));
            x.setManuItemShortDesc(rs.getString(28));
            x.setManuItemMsrp(rs.getBigDecimal(29));
            x.setManuItemUpcNum(rs.getString(30));
            x.setManuPackUpcNum(rs.getString(31));
            x.setDistItemSkuNum(rs.getString(32));
            x.setDistItemShortDesc(rs.getString(33));
            x.setDistItemUom(rs.getString(34));
            x.setDistItemPack(rs.getString(35));
            x.setDistItemCost(rs.getBigDecimal(36));
            x.setDistOrderNum(rs.getString(37));
            x.setDistErpNum(rs.getString(38));
            x.setDistItemQuantity(rs.getInt(39));
            x.setTotalQuantityOrdered(rs.getInt(40));
            x.setTotalQuantityShipped(rs.getInt(41));
            x.setComments(rs.getString(42));
            x.setAddDate(rs.getTimestamp(43));
            x.setAddBy(rs.getString(44));
            x.setModDate(rs.getTimestamp(45));
            x.setModBy(rs.getString(46));
            x.setAckStatusCd(rs.getString(47));
            x.setPurchaseOrderId(rs.getInt(48));
            x.setTargetShipDate(rs.getDate(49));
            x.setQuantityConfirmed(rs.getInt(50));
            x.setQuantityBackordered(rs.getInt(51));
            x.setCostCenterId(rs.getInt(52));
            x.setOrderRoutingId(rs.getInt(53));
            x.setItemQty855(rs.getInt(54));
            x.setFreightHandlerId(rs.getInt(55));
            x.setInventoryParValue(rs.getInt(56));
            x.setInventoryQtyOnHand(rs.getString(57));
            x.setSaleTypeCd(rs.getString(58));
            x.setDistBaseCost(rs.getBigDecimal(59));
            x.setDistUomConvMultiplier(rs.getBigDecimal(60));
            x.setDistUomConvCost(rs.getBigDecimal(61));
            x.setTotalQuantityReceived(rs.getInt(62));
            x.setErpPoRefDate(rs.getDate(63));
            x.setErpPoRefNum(rs.getString(64));
            x.setErpPoRefLineNum(rs.getInt(65));
            x.setAssetId(rs.getInt(66));
            x.setTaxRate(rs.getBigDecimal(67));
            x.setTaxAmount(rs.getBigDecimal(68));
            x.setTaxExempt(rs.getString(69));
            x.setOutboundPoNum(rs.getString(70));
            x.setServiceFee(rs.getBigDecimal(71));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_ITEM_ID :" + pOrderItemId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderItemDataVector object that consists
     * of OrderItemData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderItemDataVector()
     * @throws            SQLException
     */
    public static OrderItemDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderItemData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_ITEM.ORDER_ID,CLW_ORDER_ITEM.ORDER_ITEM_ID,CLW_ORDER_ITEM.ORDER_ITEM_STATUS_CD,CLW_ORDER_ITEM.ITEM_ID,CLW_ORDER_ITEM.EXCEPTION_IND,CLW_ORDER_ITEM.ORDER_LINE_NUM,CLW_ORDER_ITEM.CUST_LINE_NUM,CLW_ORDER_ITEM.ERP_PO_LINE_NUM,CLW_ORDER_ITEM.ERP_ORDER_LINE_NUM,CLW_ORDER_ITEM.ERP_ORDER_NUM,CLW_ORDER_ITEM.ERP_PO_NUM,CLW_ORDER_ITEM.ERP_PO_DATE,CLW_ORDER_ITEM.ERP_PO_TIME,CLW_ORDER_ITEM.ITEM_SKU_NUM,CLW_ORDER_ITEM.ITEM_SHORT_DESC,CLW_ORDER_ITEM.ITEM_UOM,CLW_ORDER_ITEM.ITEM_PACK,CLW_ORDER_ITEM.ITEM_SIZE,CLW_ORDER_ITEM.ITEM_COST,CLW_ORDER_ITEM.CUST_ITEM_SKU_NUM,CLW_ORDER_ITEM.CUST_ITEM_SHORT_DESC,CLW_ORDER_ITEM.CUST_ITEM_UOM,CLW_ORDER_ITEM.CUST_ITEM_PACK,CLW_ORDER_ITEM.CUST_CONTRACT_PRICE,CLW_ORDER_ITEM.CUST_CONTRACT_ID,CLW_ORDER_ITEM.CUST_CONTRACT_SHORT_DESC,CLW_ORDER_ITEM.MANU_ITEM_SKU_NUM,CLW_ORDER_ITEM.MANU_ITEM_SHORT_DESC,CLW_ORDER_ITEM.MANU_ITEM_MSRP,CLW_ORDER_ITEM.MANU_ITEM_UPC_NUM,CLW_ORDER_ITEM.MANU_PACK_UPC_NUM,CLW_ORDER_ITEM.DIST_ITEM_SKU_NUM,CLW_ORDER_ITEM.DIST_ITEM_SHORT_DESC,CLW_ORDER_ITEM.DIST_ITEM_UOM,CLW_ORDER_ITEM.DIST_ITEM_PACK,CLW_ORDER_ITEM.DIST_ITEM_COST,CLW_ORDER_ITEM.DIST_ORDER_NUM,CLW_ORDER_ITEM.DIST_ERP_NUM,CLW_ORDER_ITEM.DIST_ITEM_QUANTITY,CLW_ORDER_ITEM.TOTAL_QUANTITY_ORDERED,CLW_ORDER_ITEM.TOTAL_QUANTITY_SHIPPED,CLW_ORDER_ITEM.COMMENTS,CLW_ORDER_ITEM.ADD_DATE,CLW_ORDER_ITEM.ADD_BY,CLW_ORDER_ITEM.MOD_DATE,CLW_ORDER_ITEM.MOD_BY,CLW_ORDER_ITEM.ACK_STATUS_CD,CLW_ORDER_ITEM.PURCHASE_ORDER_ID,CLW_ORDER_ITEM.TARGET_SHIP_DATE,CLW_ORDER_ITEM.QUANTITY_CONFIRMED,CLW_ORDER_ITEM.QUANTITY_BACKORDERED,CLW_ORDER_ITEM.COST_CENTER_ID,CLW_ORDER_ITEM.ORDER_ROUTING_ID,CLW_ORDER_ITEM.ITEM_QTY_855,CLW_ORDER_ITEM.FREIGHT_HANDLER_ID,CLW_ORDER_ITEM.INVENTORY_PAR_VALUE,CLW_ORDER_ITEM.INVENTORY_QTY_ON_HAND,CLW_ORDER_ITEM.SALE_TYPE_CD,CLW_ORDER_ITEM.DIST_BASE_COST,CLW_ORDER_ITEM.DIST_UOM_CONV_MULTIPLIER,CLW_ORDER_ITEM.DIST_UOM_CONV_COST,CLW_ORDER_ITEM.TOTAL_QUANTITY_RECEIVED,CLW_ORDER_ITEM.ERP_PO_REF_DATE,CLW_ORDER_ITEM.ERP_PO_REF_NUM,CLW_ORDER_ITEM.ERP_PO_REF_LINE_NUM,CLW_ORDER_ITEM.ASSET_ID,CLW_ORDER_ITEM.TAX_RATE,CLW_ORDER_ITEM.TAX_AMOUNT,CLW_ORDER_ITEM.TAX_EXEMPT,CLW_ORDER_ITEM.OUTBOUND_PO_NUM,CLW_ORDER_ITEM.SERVICE_FEE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderItemData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs) throws SQLException{
         return parseResultSet(rs,0);
    }

    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@param int the offset to use which is useful when using 1 query to populate multiple objects
    *@returns a populated OrderItemData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderItemData x = OrderItemData.createValue();
         
         x.setOrderId(rs.getInt(1+offset));
         x.setOrderItemId(rs.getInt(2+offset));
         x.setOrderItemStatusCd(rs.getString(3+offset));
         x.setItemId(rs.getInt(4+offset));
         x.setExceptionInd(rs.getString(5+offset));
         x.setOrderLineNum(rs.getInt(6+offset));
         x.setCustLineNum(rs.getInt(7+offset));
         x.setErpPoLineNum(rs.getInt(8+offset));
         x.setErpOrderLineNum(rs.getInt(9+offset));
         x.setErpOrderNum(rs.getString(10+offset));
         x.setErpPoNum(rs.getString(11+offset));
         x.setErpPoDate(rs.getDate(12+offset));
         x.setErpPoTime(rs.getTimestamp(13+offset));
         x.setItemSkuNum(rs.getInt(14+offset));
         x.setItemShortDesc(rs.getString(15+offset));
         x.setItemUom(rs.getString(16+offset));
         x.setItemPack(rs.getString(17+offset));
         x.setItemSize(rs.getString(18+offset));
         x.setItemCost(rs.getBigDecimal(19+offset));
         x.setCustItemSkuNum(rs.getString(20+offset));
         x.setCustItemShortDesc(rs.getString(21+offset));
         x.setCustItemUom(rs.getString(22+offset));
         x.setCustItemPack(rs.getString(23+offset));
         x.setCustContractPrice(rs.getBigDecimal(24+offset));
         x.setCustContractId(rs.getInt(25+offset));
         x.setCustContractShortDesc(rs.getString(26+offset));
         x.setManuItemSkuNum(rs.getString(27+offset));
         x.setManuItemShortDesc(rs.getString(28+offset));
         x.setManuItemMsrp(rs.getBigDecimal(29+offset));
         x.setManuItemUpcNum(rs.getString(30+offset));
         x.setManuPackUpcNum(rs.getString(31+offset));
         x.setDistItemSkuNum(rs.getString(32+offset));
         x.setDistItemShortDesc(rs.getString(33+offset));
         x.setDistItemUom(rs.getString(34+offset));
         x.setDistItemPack(rs.getString(35+offset));
         x.setDistItemCost(rs.getBigDecimal(36+offset));
         x.setDistOrderNum(rs.getString(37+offset));
         x.setDistErpNum(rs.getString(38+offset));
         x.setDistItemQuantity(rs.getInt(39+offset));
         x.setTotalQuantityOrdered(rs.getInt(40+offset));
         x.setTotalQuantityShipped(rs.getInt(41+offset));
         x.setComments(rs.getString(42+offset));
         x.setAddDate(rs.getTimestamp(43+offset));
         x.setAddBy(rs.getString(44+offset));
         x.setModDate(rs.getTimestamp(45+offset));
         x.setModBy(rs.getString(46+offset));
         x.setAckStatusCd(rs.getString(47+offset));
         x.setPurchaseOrderId(rs.getInt(48+offset));
         x.setTargetShipDate(rs.getDate(49+offset));
         x.setQuantityConfirmed(rs.getInt(50+offset));
         x.setQuantityBackordered(rs.getInt(51+offset));
         x.setCostCenterId(rs.getInt(52+offset));
         x.setOrderRoutingId(rs.getInt(53+offset));
         x.setItemQty855(rs.getInt(54+offset));
         x.setFreightHandlerId(rs.getInt(55+offset));
         x.setInventoryParValue(rs.getInt(56+offset));
         x.setInventoryQtyOnHand(rs.getString(57+offset));
         x.setSaleTypeCd(rs.getString(58+offset));
         x.setDistBaseCost(rs.getBigDecimal(59+offset));
         x.setDistUomConvMultiplier(rs.getBigDecimal(60+offset));
         x.setDistUomConvCost(rs.getBigDecimal(61+offset));
         x.setTotalQuantityReceived(rs.getInt(62+offset));
         x.setErpPoRefDate(rs.getDate(63+offset));
         x.setErpPoRefNum(rs.getString(64+offset));
         x.setErpPoRefLineNum(rs.getInt(65+offset));
         x.setAssetId(rs.getInt(66+offset));
         x.setTaxRate(rs.getBigDecimal(67+offset));
         x.setTaxAmount(rs.getBigDecimal(68+offset));
         x.setTaxExempt(rs.getString(69+offset));
         x.setOutboundPoNum(rs.getString(70+offset));
         x.setServiceFee(rs.getBigDecimal(71+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderItemData Object represents.
    */
    public int getColumnCount(){
        return 71;
    }

    /**
     * Gets a OrderItemDataVector object that consists
     * of OrderItemData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderItemDataVector()
     * @throws            SQLException
     */
    public static OrderItemDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_ID,ORDER_ITEM_ID,ORDER_ITEM_STATUS_CD,ITEM_ID,EXCEPTION_IND,ORDER_LINE_NUM,CUST_LINE_NUM,ERP_PO_LINE_NUM,ERP_ORDER_LINE_NUM,ERP_ORDER_NUM,ERP_PO_NUM,ERP_PO_DATE,ERP_PO_TIME,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_SIZE,ITEM_COST,CUST_ITEM_SKU_NUM,CUST_ITEM_SHORT_DESC,CUST_ITEM_UOM,CUST_ITEM_PACK,CUST_CONTRACT_PRICE,CUST_CONTRACT_ID,CUST_CONTRACT_SHORT_DESC,MANU_ITEM_SKU_NUM,MANU_ITEM_SHORT_DESC,MANU_ITEM_MSRP,MANU_ITEM_UPC_NUM,MANU_PACK_UPC_NUM,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC,DIST_ITEM_UOM,DIST_ITEM_PACK,DIST_ITEM_COST,DIST_ORDER_NUM,DIST_ERP_NUM,DIST_ITEM_QUANTITY,TOTAL_QUANTITY_ORDERED,TOTAL_QUANTITY_SHIPPED,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ACK_STATUS_CD,PURCHASE_ORDER_ID,TARGET_SHIP_DATE,QUANTITY_CONFIRMED,QUANTITY_BACKORDERED,COST_CENTER_ID,ORDER_ROUTING_ID,ITEM_QTY_855,FREIGHT_HANDLER_ID,INVENTORY_PAR_VALUE,INVENTORY_QTY_ON_HAND,SALE_TYPE_CD,DIST_BASE_COST,DIST_UOM_CONV_MULTIPLIER,DIST_UOM_CONV_COST,TOTAL_QUANTITY_RECEIVED,ERP_PO_REF_DATE,ERP_PO_REF_NUM,ERP_PO_REF_LINE_NUM,ASSET_ID,TAX_RATE,TAX_AMOUNT,TAX_EXEMPT,OUTBOUND_PO_NUM,SERVICE_FEE FROM CLW_ORDER_ITEM");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_ITEM.ORDER_ID,CLW_ORDER_ITEM.ORDER_ITEM_ID,CLW_ORDER_ITEM.ORDER_ITEM_STATUS_CD,CLW_ORDER_ITEM.ITEM_ID,CLW_ORDER_ITEM.EXCEPTION_IND,CLW_ORDER_ITEM.ORDER_LINE_NUM,CLW_ORDER_ITEM.CUST_LINE_NUM,CLW_ORDER_ITEM.ERP_PO_LINE_NUM,CLW_ORDER_ITEM.ERP_ORDER_LINE_NUM,CLW_ORDER_ITEM.ERP_ORDER_NUM,CLW_ORDER_ITEM.ERP_PO_NUM,CLW_ORDER_ITEM.ERP_PO_DATE,CLW_ORDER_ITEM.ERP_PO_TIME,CLW_ORDER_ITEM.ITEM_SKU_NUM,CLW_ORDER_ITEM.ITEM_SHORT_DESC,CLW_ORDER_ITEM.ITEM_UOM,CLW_ORDER_ITEM.ITEM_PACK,CLW_ORDER_ITEM.ITEM_SIZE,CLW_ORDER_ITEM.ITEM_COST,CLW_ORDER_ITEM.CUST_ITEM_SKU_NUM,CLW_ORDER_ITEM.CUST_ITEM_SHORT_DESC,CLW_ORDER_ITEM.CUST_ITEM_UOM,CLW_ORDER_ITEM.CUST_ITEM_PACK,CLW_ORDER_ITEM.CUST_CONTRACT_PRICE,CLW_ORDER_ITEM.CUST_CONTRACT_ID,CLW_ORDER_ITEM.CUST_CONTRACT_SHORT_DESC,CLW_ORDER_ITEM.MANU_ITEM_SKU_NUM,CLW_ORDER_ITEM.MANU_ITEM_SHORT_DESC,CLW_ORDER_ITEM.MANU_ITEM_MSRP,CLW_ORDER_ITEM.MANU_ITEM_UPC_NUM,CLW_ORDER_ITEM.MANU_PACK_UPC_NUM,CLW_ORDER_ITEM.DIST_ITEM_SKU_NUM,CLW_ORDER_ITEM.DIST_ITEM_SHORT_DESC,CLW_ORDER_ITEM.DIST_ITEM_UOM,CLW_ORDER_ITEM.DIST_ITEM_PACK,CLW_ORDER_ITEM.DIST_ITEM_COST,CLW_ORDER_ITEM.DIST_ORDER_NUM,CLW_ORDER_ITEM.DIST_ERP_NUM,CLW_ORDER_ITEM.DIST_ITEM_QUANTITY,CLW_ORDER_ITEM.TOTAL_QUANTITY_ORDERED,CLW_ORDER_ITEM.TOTAL_QUANTITY_SHIPPED,CLW_ORDER_ITEM.COMMENTS,CLW_ORDER_ITEM.ADD_DATE,CLW_ORDER_ITEM.ADD_BY,CLW_ORDER_ITEM.MOD_DATE,CLW_ORDER_ITEM.MOD_BY,CLW_ORDER_ITEM.ACK_STATUS_CD,CLW_ORDER_ITEM.PURCHASE_ORDER_ID,CLW_ORDER_ITEM.TARGET_SHIP_DATE,CLW_ORDER_ITEM.QUANTITY_CONFIRMED,CLW_ORDER_ITEM.QUANTITY_BACKORDERED,CLW_ORDER_ITEM.COST_CENTER_ID,CLW_ORDER_ITEM.ORDER_ROUTING_ID,CLW_ORDER_ITEM.ITEM_QTY_855,CLW_ORDER_ITEM.FREIGHT_HANDLER_ID,CLW_ORDER_ITEM.INVENTORY_PAR_VALUE,CLW_ORDER_ITEM.INVENTORY_QTY_ON_HAND,CLW_ORDER_ITEM.SALE_TYPE_CD,CLW_ORDER_ITEM.DIST_BASE_COST,CLW_ORDER_ITEM.DIST_UOM_CONV_MULTIPLIER,CLW_ORDER_ITEM.DIST_UOM_CONV_COST,CLW_ORDER_ITEM.TOTAL_QUANTITY_RECEIVED,CLW_ORDER_ITEM.ERP_PO_REF_DATE,CLW_ORDER_ITEM.ERP_PO_REF_NUM,CLW_ORDER_ITEM.ERP_PO_REF_LINE_NUM,CLW_ORDER_ITEM.ASSET_ID,CLW_ORDER_ITEM.TAX_RATE,CLW_ORDER_ITEM.TAX_AMOUNT,CLW_ORDER_ITEM.TAX_EXEMPT,CLW_ORDER_ITEM.OUTBOUND_PO_NUM,CLW_ORDER_ITEM.SERVICE_FEE FROM CLW_ORDER_ITEM");
                where = pCriteria.getSqlClause("CLW_ORDER_ITEM");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_ITEM.equals(otherTable)){
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
				}
                }
        }

        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
              stmt.setMaxRows(pMaxRows);
        }
        ResultSet rs=stmt.executeQuery(sql);
        OrderItemDataVector v = new OrderItemDataVector();
        while (rs.next()) {
            OrderItemData x = OrderItemData.createValue();
            
            x.setOrderId(rs.getInt(1));
            x.setOrderItemId(rs.getInt(2));
            x.setOrderItemStatusCd(rs.getString(3));
            x.setItemId(rs.getInt(4));
            x.setExceptionInd(rs.getString(5));
            x.setOrderLineNum(rs.getInt(6));
            x.setCustLineNum(rs.getInt(7));
            x.setErpPoLineNum(rs.getInt(8));
            x.setErpOrderLineNum(rs.getInt(9));
            x.setErpOrderNum(rs.getString(10));
            x.setErpPoNum(rs.getString(11));
            x.setErpPoDate(rs.getDate(12));
            x.setErpPoTime(rs.getTimestamp(13));
            x.setItemSkuNum(rs.getInt(14));
            x.setItemShortDesc(rs.getString(15));
            x.setItemUom(rs.getString(16));
            x.setItemPack(rs.getString(17));
            x.setItemSize(rs.getString(18));
            x.setItemCost(rs.getBigDecimal(19));
            x.setCustItemSkuNum(rs.getString(20));
            x.setCustItemShortDesc(rs.getString(21));
            x.setCustItemUom(rs.getString(22));
            x.setCustItemPack(rs.getString(23));
            x.setCustContractPrice(rs.getBigDecimal(24));
            x.setCustContractId(rs.getInt(25));
            x.setCustContractShortDesc(rs.getString(26));
            x.setManuItemSkuNum(rs.getString(27));
            x.setManuItemShortDesc(rs.getString(28));
            x.setManuItemMsrp(rs.getBigDecimal(29));
            x.setManuItemUpcNum(rs.getString(30));
            x.setManuPackUpcNum(rs.getString(31));
            x.setDistItemSkuNum(rs.getString(32));
            x.setDistItemShortDesc(rs.getString(33));
            x.setDistItemUom(rs.getString(34));
            x.setDistItemPack(rs.getString(35));
            x.setDistItemCost(rs.getBigDecimal(36));
            x.setDistOrderNum(rs.getString(37));
            x.setDistErpNum(rs.getString(38));
            x.setDistItemQuantity(rs.getInt(39));
            x.setTotalQuantityOrdered(rs.getInt(40));
            x.setTotalQuantityShipped(rs.getInt(41));
            x.setComments(rs.getString(42));
            x.setAddDate(rs.getTimestamp(43));
            x.setAddBy(rs.getString(44));
            x.setModDate(rs.getTimestamp(45));
            x.setModBy(rs.getString(46));
            x.setAckStatusCd(rs.getString(47));
            x.setPurchaseOrderId(rs.getInt(48));
            x.setTargetShipDate(rs.getDate(49));
            x.setQuantityConfirmed(rs.getInt(50));
            x.setQuantityBackordered(rs.getInt(51));
            x.setCostCenterId(rs.getInt(52));
            x.setOrderRoutingId(rs.getInt(53));
            x.setItemQty855(rs.getInt(54));
            x.setFreightHandlerId(rs.getInt(55));
            x.setInventoryParValue(rs.getInt(56));
            x.setInventoryQtyOnHand(rs.getString(57));
            x.setSaleTypeCd(rs.getString(58));
            x.setDistBaseCost(rs.getBigDecimal(59));
            x.setDistUomConvMultiplier(rs.getBigDecimal(60));
            x.setDistUomConvCost(rs.getBigDecimal(61));
            x.setTotalQuantityReceived(rs.getInt(62));
            x.setErpPoRefDate(rs.getDate(63));
            x.setErpPoRefNum(rs.getString(64));
            x.setErpPoRefLineNum(rs.getInt(65));
            x.setAssetId(rs.getInt(66));
            x.setTaxRate(rs.getBigDecimal(67));
            x.setTaxAmount(rs.getBigDecimal(68));
            x.setTaxExempt(rs.getString(69));
            x.setOutboundPoNum(rs.getString(70));
            x.setServiceFee(rs.getBigDecimal(71));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderItemDataVector object that consists
     * of OrderItemData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderItemData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderItemDataVector()
     * @throws            SQLException
     */
    public static OrderItemDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderItemDataVector v = new OrderItemDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_ID,ORDER_ITEM_ID,ORDER_ITEM_STATUS_CD,ITEM_ID,EXCEPTION_IND,ORDER_LINE_NUM,CUST_LINE_NUM,ERP_PO_LINE_NUM,ERP_ORDER_LINE_NUM,ERP_ORDER_NUM,ERP_PO_NUM,ERP_PO_DATE,ERP_PO_TIME,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_SIZE,ITEM_COST,CUST_ITEM_SKU_NUM,CUST_ITEM_SHORT_DESC,CUST_ITEM_UOM,CUST_ITEM_PACK,CUST_CONTRACT_PRICE,CUST_CONTRACT_ID,CUST_CONTRACT_SHORT_DESC,MANU_ITEM_SKU_NUM,MANU_ITEM_SHORT_DESC,MANU_ITEM_MSRP,MANU_ITEM_UPC_NUM,MANU_PACK_UPC_NUM,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC,DIST_ITEM_UOM,DIST_ITEM_PACK,DIST_ITEM_COST,DIST_ORDER_NUM,DIST_ERP_NUM,DIST_ITEM_QUANTITY,TOTAL_QUANTITY_ORDERED,TOTAL_QUANTITY_SHIPPED,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ACK_STATUS_CD,PURCHASE_ORDER_ID,TARGET_SHIP_DATE,QUANTITY_CONFIRMED,QUANTITY_BACKORDERED,COST_CENTER_ID,ORDER_ROUTING_ID,ITEM_QTY_855,FREIGHT_HANDLER_ID,INVENTORY_PAR_VALUE,INVENTORY_QTY_ON_HAND,SALE_TYPE_CD,DIST_BASE_COST,DIST_UOM_CONV_MULTIPLIER,DIST_UOM_CONV_COST,TOTAL_QUANTITY_RECEIVED,ERP_PO_REF_DATE,ERP_PO_REF_NUM,ERP_PO_REF_LINE_NUM,ASSET_ID,TAX_RATE,TAX_AMOUNT,TAX_EXEMPT,OUTBOUND_PO_NUM,SERVICE_FEE FROM CLW_ORDER_ITEM WHERE ORDER_ITEM_ID IN (");

        if ( pIdVector.size() > 0 ) {
            sqlBuf.append(pIdVector.get(0).toString());
            int vecsize = pIdVector.size();
            for ( int idx = 1; idx < vecsize; idx++ ) {
                sqlBuf.append("," + pIdVector.get(idx).toString());
            }
            sqlBuf.append(")");


            String sql = sqlBuf.toString();
            if (log.isDebugEnabled()) {
                log.debug("SQL: " + sql);
            }

            Statement stmt = pCon.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            OrderItemData x=null;
            while (rs.next()) {
                // build the object
                x=OrderItemData.createValue();
                
                x.setOrderId(rs.getInt(1));
                x.setOrderItemId(rs.getInt(2));
                x.setOrderItemStatusCd(rs.getString(3));
                x.setItemId(rs.getInt(4));
                x.setExceptionInd(rs.getString(5));
                x.setOrderLineNum(rs.getInt(6));
                x.setCustLineNum(rs.getInt(7));
                x.setErpPoLineNum(rs.getInt(8));
                x.setErpOrderLineNum(rs.getInt(9));
                x.setErpOrderNum(rs.getString(10));
                x.setErpPoNum(rs.getString(11));
                x.setErpPoDate(rs.getDate(12));
                x.setErpPoTime(rs.getTimestamp(13));
                x.setItemSkuNum(rs.getInt(14));
                x.setItemShortDesc(rs.getString(15));
                x.setItemUom(rs.getString(16));
                x.setItemPack(rs.getString(17));
                x.setItemSize(rs.getString(18));
                x.setItemCost(rs.getBigDecimal(19));
                x.setCustItemSkuNum(rs.getString(20));
                x.setCustItemShortDesc(rs.getString(21));
                x.setCustItemUom(rs.getString(22));
                x.setCustItemPack(rs.getString(23));
                x.setCustContractPrice(rs.getBigDecimal(24));
                x.setCustContractId(rs.getInt(25));
                x.setCustContractShortDesc(rs.getString(26));
                x.setManuItemSkuNum(rs.getString(27));
                x.setManuItemShortDesc(rs.getString(28));
                x.setManuItemMsrp(rs.getBigDecimal(29));
                x.setManuItemUpcNum(rs.getString(30));
                x.setManuPackUpcNum(rs.getString(31));
                x.setDistItemSkuNum(rs.getString(32));
                x.setDistItemShortDesc(rs.getString(33));
                x.setDistItemUom(rs.getString(34));
                x.setDistItemPack(rs.getString(35));
                x.setDistItemCost(rs.getBigDecimal(36));
                x.setDistOrderNum(rs.getString(37));
                x.setDistErpNum(rs.getString(38));
                x.setDistItemQuantity(rs.getInt(39));
                x.setTotalQuantityOrdered(rs.getInt(40));
                x.setTotalQuantityShipped(rs.getInt(41));
                x.setComments(rs.getString(42));
                x.setAddDate(rs.getTimestamp(43));
                x.setAddBy(rs.getString(44));
                x.setModDate(rs.getTimestamp(45));
                x.setModBy(rs.getString(46));
                x.setAckStatusCd(rs.getString(47));
                x.setPurchaseOrderId(rs.getInt(48));
                x.setTargetShipDate(rs.getDate(49));
                x.setQuantityConfirmed(rs.getInt(50));
                x.setQuantityBackordered(rs.getInt(51));
                x.setCostCenterId(rs.getInt(52));
                x.setOrderRoutingId(rs.getInt(53));
                x.setItemQty855(rs.getInt(54));
                x.setFreightHandlerId(rs.getInt(55));
                x.setInventoryParValue(rs.getInt(56));
                x.setInventoryQtyOnHand(rs.getString(57));
                x.setSaleTypeCd(rs.getString(58));
                x.setDistBaseCost(rs.getBigDecimal(59));
                x.setDistUomConvMultiplier(rs.getBigDecimal(60));
                x.setDistUomConvCost(rs.getBigDecimal(61));
                x.setTotalQuantityReceived(rs.getInt(62));
                x.setErpPoRefDate(rs.getDate(63));
                x.setErpPoRefNum(rs.getString(64));
                x.setErpPoRefLineNum(rs.getInt(65));
                x.setAssetId(rs.getInt(66));
                x.setTaxRate(rs.getBigDecimal(67));
                x.setTaxAmount(rs.getBigDecimal(68));
                x.setTaxExempt(rs.getString(69));
                x.setOutboundPoNum(rs.getString(70));
                x.setServiceFee(rs.getBigDecimal(71));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderItemDataVector object of all
     * OrderItemData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderItemDataVector()
     * @throws            SQLException
     */
    public static OrderItemDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_ID,ORDER_ITEM_ID,ORDER_ITEM_STATUS_CD,ITEM_ID,EXCEPTION_IND,ORDER_LINE_NUM,CUST_LINE_NUM,ERP_PO_LINE_NUM,ERP_ORDER_LINE_NUM,ERP_ORDER_NUM,ERP_PO_NUM,ERP_PO_DATE,ERP_PO_TIME,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_SIZE,ITEM_COST,CUST_ITEM_SKU_NUM,CUST_ITEM_SHORT_DESC,CUST_ITEM_UOM,CUST_ITEM_PACK,CUST_CONTRACT_PRICE,CUST_CONTRACT_ID,CUST_CONTRACT_SHORT_DESC,MANU_ITEM_SKU_NUM,MANU_ITEM_SHORT_DESC,MANU_ITEM_MSRP,MANU_ITEM_UPC_NUM,MANU_PACK_UPC_NUM,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC,DIST_ITEM_UOM,DIST_ITEM_PACK,DIST_ITEM_COST,DIST_ORDER_NUM,DIST_ERP_NUM,DIST_ITEM_QUANTITY,TOTAL_QUANTITY_ORDERED,TOTAL_QUANTITY_SHIPPED,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ACK_STATUS_CD,PURCHASE_ORDER_ID,TARGET_SHIP_DATE,QUANTITY_CONFIRMED,QUANTITY_BACKORDERED,COST_CENTER_ID,ORDER_ROUTING_ID,ITEM_QTY_855,FREIGHT_HANDLER_ID,INVENTORY_PAR_VALUE,INVENTORY_QTY_ON_HAND,SALE_TYPE_CD,DIST_BASE_COST,DIST_UOM_CONV_MULTIPLIER,DIST_UOM_CONV_COST,TOTAL_QUANTITY_RECEIVED,ERP_PO_REF_DATE,ERP_PO_REF_NUM,ERP_PO_REF_LINE_NUM,ASSET_ID,TAX_RATE,TAX_AMOUNT,TAX_EXEMPT,OUTBOUND_PO_NUM,SERVICE_FEE FROM CLW_ORDER_ITEM";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderItemDataVector v = new OrderItemDataVector();
        OrderItemData x = null;
        while (rs.next()) {
            // build the object
            x = OrderItemData.createValue();
            
            x.setOrderId(rs.getInt(1));
            x.setOrderItemId(rs.getInt(2));
            x.setOrderItemStatusCd(rs.getString(3));
            x.setItemId(rs.getInt(4));
            x.setExceptionInd(rs.getString(5));
            x.setOrderLineNum(rs.getInt(6));
            x.setCustLineNum(rs.getInt(7));
            x.setErpPoLineNum(rs.getInt(8));
            x.setErpOrderLineNum(rs.getInt(9));
            x.setErpOrderNum(rs.getString(10));
            x.setErpPoNum(rs.getString(11));
            x.setErpPoDate(rs.getDate(12));
            x.setErpPoTime(rs.getTimestamp(13));
            x.setItemSkuNum(rs.getInt(14));
            x.setItemShortDesc(rs.getString(15));
            x.setItemUom(rs.getString(16));
            x.setItemPack(rs.getString(17));
            x.setItemSize(rs.getString(18));
            x.setItemCost(rs.getBigDecimal(19));
            x.setCustItemSkuNum(rs.getString(20));
            x.setCustItemShortDesc(rs.getString(21));
            x.setCustItemUom(rs.getString(22));
            x.setCustItemPack(rs.getString(23));
            x.setCustContractPrice(rs.getBigDecimal(24));
            x.setCustContractId(rs.getInt(25));
            x.setCustContractShortDesc(rs.getString(26));
            x.setManuItemSkuNum(rs.getString(27));
            x.setManuItemShortDesc(rs.getString(28));
            x.setManuItemMsrp(rs.getBigDecimal(29));
            x.setManuItemUpcNum(rs.getString(30));
            x.setManuPackUpcNum(rs.getString(31));
            x.setDistItemSkuNum(rs.getString(32));
            x.setDistItemShortDesc(rs.getString(33));
            x.setDistItemUom(rs.getString(34));
            x.setDistItemPack(rs.getString(35));
            x.setDistItemCost(rs.getBigDecimal(36));
            x.setDistOrderNum(rs.getString(37));
            x.setDistErpNum(rs.getString(38));
            x.setDistItemQuantity(rs.getInt(39));
            x.setTotalQuantityOrdered(rs.getInt(40));
            x.setTotalQuantityShipped(rs.getInt(41));
            x.setComments(rs.getString(42));
            x.setAddDate(rs.getTimestamp(43));
            x.setAddBy(rs.getString(44));
            x.setModDate(rs.getTimestamp(45));
            x.setModBy(rs.getString(46));
            x.setAckStatusCd(rs.getString(47));
            x.setPurchaseOrderId(rs.getInt(48));
            x.setTargetShipDate(rs.getDate(49));
            x.setQuantityConfirmed(rs.getInt(50));
            x.setQuantityBackordered(rs.getInt(51));
            x.setCostCenterId(rs.getInt(52));
            x.setOrderRoutingId(rs.getInt(53));
            x.setItemQty855(rs.getInt(54));
            x.setFreightHandlerId(rs.getInt(55));
            x.setInventoryParValue(rs.getInt(56));
            x.setInventoryQtyOnHand(rs.getString(57));
            x.setSaleTypeCd(rs.getString(58));
            x.setDistBaseCost(rs.getBigDecimal(59));
            x.setDistUomConvMultiplier(rs.getBigDecimal(60));
            x.setDistUomConvCost(rs.getBigDecimal(61));
            x.setTotalQuantityReceived(rs.getInt(62));
            x.setErpPoRefDate(rs.getDate(63));
            x.setErpPoRefNum(rs.getString(64));
            x.setErpPoRefLineNum(rs.getInt(65));
            x.setAssetId(rs.getInt(66));
            x.setTaxRate(rs.getBigDecimal(67));
            x.setTaxAmount(rs.getBigDecimal(68));
            x.setTaxExempt(rs.getString(69));
            x.setOutboundPoNum(rs.getString(70));
            x.setServiceFee(rs.getBigDecimal(71));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderItemData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_ITEM_ID FROM CLW_ORDER_ITEM");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        IdVector v = new IdVector();
        while (rs.next()) {
            Integer x = new Integer(rs.getInt(1));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of requested
     * objects in the database.
     * @param pCon An open database connection.
     * @param pIdName A column name
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, String pIdName, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_ITEM");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        IdVector v = new IdVector();
        while (rs.next()) {
            Integer x = new Integer(rs.getInt(1));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }


    /**
     * Gets an sql statement to request ids of
     * objects in the database.
     * @param pIdName A column name
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return String
     */
    public static String getSqlSelectIdOnly(String pIdName, DBCriteria pCriteria)
    {
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_ITEM");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL text: " + sql);
        }

        return sql;
    }

    /**
     * Inserts a OrderItemData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderItemData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderItemData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderItemData insert(Connection pCon, OrderItemData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_ITEM_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_ITEM_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderItemId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_ITEM (ORDER_ID,ORDER_ITEM_ID,ORDER_ITEM_STATUS_CD,ITEM_ID,EXCEPTION_IND,ORDER_LINE_NUM,CUST_LINE_NUM,ERP_PO_LINE_NUM,ERP_ORDER_LINE_NUM,ERP_ORDER_NUM,ERP_PO_NUM,ERP_PO_DATE,ERP_PO_TIME,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_SIZE,ITEM_COST,CUST_ITEM_SKU_NUM,CUST_ITEM_SHORT_DESC,CUST_ITEM_UOM,CUST_ITEM_PACK,CUST_CONTRACT_PRICE,CUST_CONTRACT_ID,CUST_CONTRACT_SHORT_DESC,MANU_ITEM_SKU_NUM,MANU_ITEM_SHORT_DESC,MANU_ITEM_MSRP,MANU_ITEM_UPC_NUM,MANU_PACK_UPC_NUM,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC,DIST_ITEM_UOM,DIST_ITEM_PACK,DIST_ITEM_COST,DIST_ORDER_NUM,DIST_ERP_NUM,DIST_ITEM_QUANTITY,TOTAL_QUANTITY_ORDERED,TOTAL_QUANTITY_SHIPPED,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ACK_STATUS_CD,PURCHASE_ORDER_ID,TARGET_SHIP_DATE,QUANTITY_CONFIRMED,QUANTITY_BACKORDERED,COST_CENTER_ID,ORDER_ROUTING_ID,ITEM_QTY_855,FREIGHT_HANDLER_ID,INVENTORY_PAR_VALUE,INVENTORY_QTY_ON_HAND,SALE_TYPE_CD,DIST_BASE_COST,DIST_UOM_CONV_MULTIPLIER,DIST_UOM_CONV_COST,TOTAL_QUANTITY_RECEIVED,ERP_PO_REF_DATE,ERP_PO_REF_NUM,ERP_PO_REF_LINE_NUM,ASSET_ID,TAX_RATE,TAX_AMOUNT,TAX_EXEMPT,OUTBOUND_PO_NUM,SERVICE_FEE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderId());
        pstmt.setInt(2,pData.getOrderItemId());
        pstmt.setString(3,pData.getOrderItemStatusCd());
        pstmt.setInt(4,pData.getItemId());
        pstmt.setString(5,pData.getExceptionInd());
        pstmt.setInt(6,pData.getOrderLineNum());
        pstmt.setInt(7,pData.getCustLineNum());
        pstmt.setInt(8,pData.getErpPoLineNum());
        pstmt.setInt(9,pData.getErpOrderLineNum());
        pstmt.setString(10,pData.getErpOrderNum());
        pstmt.setString(11,pData.getErpPoNum());
        pstmt.setDate(12,DBAccess.toSQLDate(pData.getErpPoDate()));
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getErpPoTime()));
        pstmt.setInt(14,pData.getItemSkuNum());
        pstmt.setString(15,pData.getItemShortDesc());
        pstmt.setString(16,pData.getItemUom());
        pstmt.setString(17,pData.getItemPack());
        pstmt.setString(18,pData.getItemSize());
        pstmt.setBigDecimal(19,pData.getItemCost());
        pstmt.setString(20,pData.getCustItemSkuNum());
        pstmt.setString(21,pData.getCustItemShortDesc());
        pstmt.setString(22,pData.getCustItemUom());
        pstmt.setString(23,pData.getCustItemPack());
        pstmt.setBigDecimal(24,pData.getCustContractPrice());
        if (pData.getCustContractId() == 0) {
            pstmt.setNull(25, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(25,pData.getCustContractId());
        }

        pstmt.setString(26,pData.getCustContractShortDesc());
        pstmt.setString(27,pData.getManuItemSkuNum());
        pstmt.setString(28,pData.getManuItemShortDesc());
        pstmt.setBigDecimal(29,pData.getManuItemMsrp());
        pstmt.setString(30,pData.getManuItemUpcNum());
        pstmt.setString(31,pData.getManuPackUpcNum());
        pstmt.setString(32,pData.getDistItemSkuNum());
        pstmt.setString(33,pData.getDistItemShortDesc());
        pstmt.setString(34,pData.getDistItemUom());
        pstmt.setString(35,pData.getDistItemPack());
        pstmt.setBigDecimal(36,pData.getDistItemCost());
        pstmt.setString(37,pData.getDistOrderNum());
        pstmt.setString(38,pData.getDistErpNum());
        pstmt.setInt(39,pData.getDistItemQuantity());
        pstmt.setInt(40,pData.getTotalQuantityOrdered());
        pstmt.setInt(41,pData.getTotalQuantityShipped());
        pstmt.setString(42,pData.getComments());
        pstmt.setTimestamp(43,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(44,pData.getAddBy());
        pstmt.setTimestamp(45,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(46,pData.getModBy());
        pstmt.setString(47,pData.getAckStatusCd());
        if (pData.getPurchaseOrderId() == 0) {
            pstmt.setNull(48, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(48,pData.getPurchaseOrderId());
        }

        pstmt.setDate(49,DBAccess.toSQLDate(pData.getTargetShipDate()));
        pstmt.setInt(50,pData.getQuantityConfirmed());
        pstmt.setInt(51,pData.getQuantityBackordered());
        pstmt.setInt(52,pData.getCostCenterId());
        pstmt.setInt(53,pData.getOrderRoutingId());
        pstmt.setInt(54,pData.getItemQty855());
        pstmt.setInt(55,pData.getFreightHandlerId());
        pstmt.setInt(56,pData.getInventoryParValue());
        pstmt.setString(57,pData.getInventoryQtyOnHand());
        pstmt.setString(58,pData.getSaleTypeCd());
        pstmt.setBigDecimal(59,pData.getDistBaseCost());
        pstmt.setBigDecimal(60,pData.getDistUomConvMultiplier());
        pstmt.setBigDecimal(61,pData.getDistUomConvCost());
        pstmt.setInt(62,pData.getTotalQuantityReceived());
        pstmt.setDate(63,DBAccess.toSQLDate(pData.getErpPoRefDate()));
        pstmt.setString(64,pData.getErpPoRefNum());
        pstmt.setInt(65,pData.getErpPoRefLineNum());
        if (pData.getAssetId() == 0) {
            pstmt.setNull(66, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(66,pData.getAssetId());
        }

        pstmt.setBigDecimal(67,pData.getTaxRate());
        pstmt.setBigDecimal(68,pData.getTaxAmount());
        pstmt.setString(69,pData.getTaxExempt());
        pstmt.setString(70,pData.getOutboundPoNum());
        pstmt.setBigDecimal(71,pData.getServiceFee());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   ORDER_ITEM_STATUS_CD="+pData.getOrderItemStatusCd());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   EXCEPTION_IND="+pData.getExceptionInd());
            log.debug("SQL:   ORDER_LINE_NUM="+pData.getOrderLineNum());
            log.debug("SQL:   CUST_LINE_NUM="+pData.getCustLineNum());
            log.debug("SQL:   ERP_PO_LINE_NUM="+pData.getErpPoLineNum());
            log.debug("SQL:   ERP_ORDER_LINE_NUM="+pData.getErpOrderLineNum());
            log.debug("SQL:   ERP_ORDER_NUM="+pData.getErpOrderNum());
            log.debug("SQL:   ERP_PO_NUM="+pData.getErpPoNum());
            log.debug("SQL:   ERP_PO_DATE="+pData.getErpPoDate());
            log.debug("SQL:   ERP_PO_TIME="+pData.getErpPoTime());
            log.debug("SQL:   ITEM_SKU_NUM="+pData.getItemSkuNum());
            log.debug("SQL:   ITEM_SHORT_DESC="+pData.getItemShortDesc());
            log.debug("SQL:   ITEM_UOM="+pData.getItemUom());
            log.debug("SQL:   ITEM_PACK="+pData.getItemPack());
            log.debug("SQL:   ITEM_SIZE="+pData.getItemSize());
            log.debug("SQL:   ITEM_COST="+pData.getItemCost());
            log.debug("SQL:   CUST_ITEM_SKU_NUM="+pData.getCustItemSkuNum());
            log.debug("SQL:   CUST_ITEM_SHORT_DESC="+pData.getCustItemShortDesc());
            log.debug("SQL:   CUST_ITEM_UOM="+pData.getCustItemUom());
            log.debug("SQL:   CUST_ITEM_PACK="+pData.getCustItemPack());
            log.debug("SQL:   CUST_CONTRACT_PRICE="+pData.getCustContractPrice());
            log.debug("SQL:   CUST_CONTRACT_ID="+pData.getCustContractId());
            log.debug("SQL:   CUST_CONTRACT_SHORT_DESC="+pData.getCustContractShortDesc());
            log.debug("SQL:   MANU_ITEM_SKU_NUM="+pData.getManuItemSkuNum());
            log.debug("SQL:   MANU_ITEM_SHORT_DESC="+pData.getManuItemShortDesc());
            log.debug("SQL:   MANU_ITEM_MSRP="+pData.getManuItemMsrp());
            log.debug("SQL:   MANU_ITEM_UPC_NUM="+pData.getManuItemUpcNum());
            log.debug("SQL:   MANU_PACK_UPC_NUM="+pData.getManuPackUpcNum());
            log.debug("SQL:   DIST_ITEM_SKU_NUM="+pData.getDistItemSkuNum());
            log.debug("SQL:   DIST_ITEM_SHORT_DESC="+pData.getDistItemShortDesc());
            log.debug("SQL:   DIST_ITEM_UOM="+pData.getDistItemUom());
            log.debug("SQL:   DIST_ITEM_PACK="+pData.getDistItemPack());
            log.debug("SQL:   DIST_ITEM_COST="+pData.getDistItemCost());
            log.debug("SQL:   DIST_ORDER_NUM="+pData.getDistOrderNum());
            log.debug("SQL:   DIST_ERP_NUM="+pData.getDistErpNum());
            log.debug("SQL:   DIST_ITEM_QUANTITY="+pData.getDistItemQuantity());
            log.debug("SQL:   TOTAL_QUANTITY_ORDERED="+pData.getTotalQuantityOrdered());
            log.debug("SQL:   TOTAL_QUANTITY_SHIPPED="+pData.getTotalQuantityShipped());
            log.debug("SQL:   COMMENTS="+pData.getComments());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ACK_STATUS_CD="+pData.getAckStatusCd());
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL:   TARGET_SHIP_DATE="+pData.getTargetShipDate());
            log.debug("SQL:   QUANTITY_CONFIRMED="+pData.getQuantityConfirmed());
            log.debug("SQL:   QUANTITY_BACKORDERED="+pData.getQuantityBackordered());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   ORDER_ROUTING_ID="+pData.getOrderRoutingId());
            log.debug("SQL:   ITEM_QTY_855="+pData.getItemQty855());
            log.debug("SQL:   FREIGHT_HANDLER_ID="+pData.getFreightHandlerId());
            log.debug("SQL:   INVENTORY_PAR_VALUE="+pData.getInventoryParValue());
            log.debug("SQL:   INVENTORY_QTY_ON_HAND="+pData.getInventoryQtyOnHand());
            log.debug("SQL:   SALE_TYPE_CD="+pData.getSaleTypeCd());
            log.debug("SQL:   DIST_BASE_COST="+pData.getDistBaseCost());
            log.debug("SQL:   DIST_UOM_CONV_MULTIPLIER="+pData.getDistUomConvMultiplier());
            log.debug("SQL:   DIST_UOM_CONV_COST="+pData.getDistUomConvCost());
            log.debug("SQL:   TOTAL_QUANTITY_RECEIVED="+pData.getTotalQuantityReceived());
            log.debug("SQL:   ERP_PO_REF_DATE="+pData.getErpPoRefDate());
            log.debug("SQL:   ERP_PO_REF_NUM="+pData.getErpPoRefNum());
            log.debug("SQL:   ERP_PO_REF_LINE_NUM="+pData.getErpPoRefLineNum());
            log.debug("SQL:   ASSET_ID="+pData.getAssetId());
            log.debug("SQL:   TAX_RATE="+pData.getTaxRate());
            log.debug("SQL:   TAX_AMOUNT="+pData.getTaxAmount());
            log.debug("SQL:   TAX_EXEMPT="+pData.getTaxExempt());
            log.debug("SQL:   OUTBOUND_PO_NUM="+pData.getOutboundPoNum());
            log.debug("SQL:   SERVICE_FEE="+pData.getServiceFee());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setOrderItemId(0);
        exceptionMessage=e.getMessage();
        }
        finally{
        pstmt.close();
        }

        if(exceptionMessage!=null) {
                 throw new SQLException(exceptionMessage);
        }

        return pData;
    }

    /**
     * Updates a OrderItemData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderItemData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderItemData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_ITEM SET ORDER_ID = ?,ORDER_ITEM_STATUS_CD = ?,ITEM_ID = ?,EXCEPTION_IND = ?,ORDER_LINE_NUM = ?,CUST_LINE_NUM = ?,ERP_PO_LINE_NUM = ?,ERP_ORDER_LINE_NUM = ?,ERP_ORDER_NUM = ?,ERP_PO_NUM = ?,ERP_PO_DATE = ?,ERP_PO_TIME = ?,ITEM_SKU_NUM = ?,ITEM_SHORT_DESC = ?,ITEM_UOM = ?,ITEM_PACK = ?,ITEM_SIZE = ?,ITEM_COST = ?,CUST_ITEM_SKU_NUM = ?,CUST_ITEM_SHORT_DESC = ?,CUST_ITEM_UOM = ?,CUST_ITEM_PACK = ?,CUST_CONTRACT_PRICE = ?,CUST_CONTRACT_ID = ?,CUST_CONTRACT_SHORT_DESC = ?,MANU_ITEM_SKU_NUM = ?,MANU_ITEM_SHORT_DESC = ?,MANU_ITEM_MSRP = ?,MANU_ITEM_UPC_NUM = ?,MANU_PACK_UPC_NUM = ?,DIST_ITEM_SKU_NUM = ?,DIST_ITEM_SHORT_DESC = ?,DIST_ITEM_UOM = ?,DIST_ITEM_PACK = ?,DIST_ITEM_COST = ?,DIST_ORDER_NUM = ?,DIST_ERP_NUM = ?,DIST_ITEM_QUANTITY = ?,TOTAL_QUANTITY_ORDERED = ?,TOTAL_QUANTITY_SHIPPED = ?,COMMENTS = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,ACK_STATUS_CD = ?,PURCHASE_ORDER_ID = ?,TARGET_SHIP_DATE = ?,QUANTITY_CONFIRMED = ?,QUANTITY_BACKORDERED = ?,COST_CENTER_ID = ?,ORDER_ROUTING_ID = ?,ITEM_QTY_855 = ?,FREIGHT_HANDLER_ID = ?,INVENTORY_PAR_VALUE = ?,INVENTORY_QTY_ON_HAND = ?,SALE_TYPE_CD = ?,DIST_BASE_COST = ?,DIST_UOM_CONV_MULTIPLIER = ?,DIST_UOM_CONV_COST = ?,TOTAL_QUANTITY_RECEIVED = ?,ERP_PO_REF_DATE = ?,ERP_PO_REF_NUM = ?,ERP_PO_REF_LINE_NUM = ?,ASSET_ID = ?,TAX_RATE = ?,TAX_AMOUNT = ?,TAX_EXEMPT = ?,OUTBOUND_PO_NUM = ?,SERVICE_FEE = ? WHERE ORDER_ITEM_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getOrderId());
        pstmt.setString(i++,pData.getOrderItemStatusCd());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setString(i++,pData.getExceptionInd());
        pstmt.setInt(i++,pData.getOrderLineNum());
        pstmt.setInt(i++,pData.getCustLineNum());
        pstmt.setInt(i++,pData.getErpPoLineNum());
        pstmt.setInt(i++,pData.getErpOrderLineNum());
        pstmt.setString(i++,pData.getErpOrderNum());
        pstmt.setString(i++,pData.getErpPoNum());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getErpPoDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getErpPoTime()));
        pstmt.setInt(i++,pData.getItemSkuNum());
        pstmt.setString(i++,pData.getItemShortDesc());
        pstmt.setString(i++,pData.getItemUom());
        pstmt.setString(i++,pData.getItemPack());
        pstmt.setString(i++,pData.getItemSize());
        pstmt.setBigDecimal(i++,pData.getItemCost());
        pstmt.setString(i++,pData.getCustItemSkuNum());
        pstmt.setString(i++,pData.getCustItemShortDesc());
        pstmt.setString(i++,pData.getCustItemUom());
        pstmt.setString(i++,pData.getCustItemPack());
        pstmt.setBigDecimal(i++,pData.getCustContractPrice());
        if (pData.getCustContractId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getCustContractId());
        }

        pstmt.setString(i++,pData.getCustContractShortDesc());
        pstmt.setString(i++,pData.getManuItemSkuNum());
        pstmt.setString(i++,pData.getManuItemShortDesc());
        pstmt.setBigDecimal(i++,pData.getManuItemMsrp());
        pstmt.setString(i++,pData.getManuItemUpcNum());
        pstmt.setString(i++,pData.getManuPackUpcNum());
        pstmt.setString(i++,pData.getDistItemSkuNum());
        pstmt.setString(i++,pData.getDistItemShortDesc());
        pstmt.setString(i++,pData.getDistItemUom());
        pstmt.setString(i++,pData.getDistItemPack());
        pstmt.setBigDecimal(i++,pData.getDistItemCost());
        pstmt.setString(i++,pData.getDistOrderNum());
        pstmt.setString(i++,pData.getDistErpNum());
        pstmt.setInt(i++,pData.getDistItemQuantity());
        pstmt.setInt(i++,pData.getTotalQuantityOrdered());
        pstmt.setInt(i++,pData.getTotalQuantityShipped());
        pstmt.setString(i++,pData.getComments());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getAckStatusCd());
        if (pData.getPurchaseOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getPurchaseOrderId());
        }

        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getTargetShipDate()));
        pstmt.setInt(i++,pData.getQuantityConfirmed());
        pstmt.setInt(i++,pData.getQuantityBackordered());
        pstmt.setInt(i++,pData.getCostCenterId());
        pstmt.setInt(i++,pData.getOrderRoutingId());
        pstmt.setInt(i++,pData.getItemQty855());
        pstmt.setInt(i++,pData.getFreightHandlerId());
        pstmt.setInt(i++,pData.getInventoryParValue());
        pstmt.setString(i++,pData.getInventoryQtyOnHand());
        pstmt.setString(i++,pData.getSaleTypeCd());
        pstmt.setBigDecimal(i++,pData.getDistBaseCost());
        pstmt.setBigDecimal(i++,pData.getDistUomConvMultiplier());
        pstmt.setBigDecimal(i++,pData.getDistUomConvCost());
        pstmt.setInt(i++,pData.getTotalQuantityReceived());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getErpPoRefDate()));
        pstmt.setString(i++,pData.getErpPoRefNum());
        pstmt.setInt(i++,pData.getErpPoRefLineNum());
        if (pData.getAssetId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getAssetId());
        }

        pstmt.setBigDecimal(i++,pData.getTaxRate());
        pstmt.setBigDecimal(i++,pData.getTaxAmount());
        pstmt.setString(i++,pData.getTaxExempt());
        pstmt.setString(i++,pData.getOutboundPoNum());
        pstmt.setBigDecimal(i++,pData.getServiceFee());
        pstmt.setInt(i++,pData.getOrderItemId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ORDER_ITEM_STATUS_CD="+pData.getOrderItemStatusCd());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   EXCEPTION_IND="+pData.getExceptionInd());
            log.debug("SQL:   ORDER_LINE_NUM="+pData.getOrderLineNum());
            log.debug("SQL:   CUST_LINE_NUM="+pData.getCustLineNum());
            log.debug("SQL:   ERP_PO_LINE_NUM="+pData.getErpPoLineNum());
            log.debug("SQL:   ERP_ORDER_LINE_NUM="+pData.getErpOrderLineNum());
            log.debug("SQL:   ERP_ORDER_NUM="+pData.getErpOrderNum());
            log.debug("SQL:   ERP_PO_NUM="+pData.getErpPoNum());
            log.debug("SQL:   ERP_PO_DATE="+pData.getErpPoDate());
            log.debug("SQL:   ERP_PO_TIME="+pData.getErpPoTime());
            log.debug("SQL:   ITEM_SKU_NUM="+pData.getItemSkuNum());
            log.debug("SQL:   ITEM_SHORT_DESC="+pData.getItemShortDesc());
            log.debug("SQL:   ITEM_UOM="+pData.getItemUom());
            log.debug("SQL:   ITEM_PACK="+pData.getItemPack());
            log.debug("SQL:   ITEM_SIZE="+pData.getItemSize());
            log.debug("SQL:   ITEM_COST="+pData.getItemCost());
            log.debug("SQL:   CUST_ITEM_SKU_NUM="+pData.getCustItemSkuNum());
            log.debug("SQL:   CUST_ITEM_SHORT_DESC="+pData.getCustItemShortDesc());
            log.debug("SQL:   CUST_ITEM_UOM="+pData.getCustItemUom());
            log.debug("SQL:   CUST_ITEM_PACK="+pData.getCustItemPack());
            log.debug("SQL:   CUST_CONTRACT_PRICE="+pData.getCustContractPrice());
            log.debug("SQL:   CUST_CONTRACT_ID="+pData.getCustContractId());
            log.debug("SQL:   CUST_CONTRACT_SHORT_DESC="+pData.getCustContractShortDesc());
            log.debug("SQL:   MANU_ITEM_SKU_NUM="+pData.getManuItemSkuNum());
            log.debug("SQL:   MANU_ITEM_SHORT_DESC="+pData.getManuItemShortDesc());
            log.debug("SQL:   MANU_ITEM_MSRP="+pData.getManuItemMsrp());
            log.debug("SQL:   MANU_ITEM_UPC_NUM="+pData.getManuItemUpcNum());
            log.debug("SQL:   MANU_PACK_UPC_NUM="+pData.getManuPackUpcNum());
            log.debug("SQL:   DIST_ITEM_SKU_NUM="+pData.getDistItemSkuNum());
            log.debug("SQL:   DIST_ITEM_SHORT_DESC="+pData.getDistItemShortDesc());
            log.debug("SQL:   DIST_ITEM_UOM="+pData.getDistItemUom());
            log.debug("SQL:   DIST_ITEM_PACK="+pData.getDistItemPack());
            log.debug("SQL:   DIST_ITEM_COST="+pData.getDistItemCost());
            log.debug("SQL:   DIST_ORDER_NUM="+pData.getDistOrderNum());
            log.debug("SQL:   DIST_ERP_NUM="+pData.getDistErpNum());
            log.debug("SQL:   DIST_ITEM_QUANTITY="+pData.getDistItemQuantity());
            log.debug("SQL:   TOTAL_QUANTITY_ORDERED="+pData.getTotalQuantityOrdered());
            log.debug("SQL:   TOTAL_QUANTITY_SHIPPED="+pData.getTotalQuantityShipped());
            log.debug("SQL:   COMMENTS="+pData.getComments());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ACK_STATUS_CD="+pData.getAckStatusCd());
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL:   TARGET_SHIP_DATE="+pData.getTargetShipDate());
            log.debug("SQL:   QUANTITY_CONFIRMED="+pData.getQuantityConfirmed());
            log.debug("SQL:   QUANTITY_BACKORDERED="+pData.getQuantityBackordered());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   ORDER_ROUTING_ID="+pData.getOrderRoutingId());
            log.debug("SQL:   ITEM_QTY_855="+pData.getItemQty855());
            log.debug("SQL:   FREIGHT_HANDLER_ID="+pData.getFreightHandlerId());
            log.debug("SQL:   INVENTORY_PAR_VALUE="+pData.getInventoryParValue());
            log.debug("SQL:   INVENTORY_QTY_ON_HAND="+pData.getInventoryQtyOnHand());
            log.debug("SQL:   SALE_TYPE_CD="+pData.getSaleTypeCd());
            log.debug("SQL:   DIST_BASE_COST="+pData.getDistBaseCost());
            log.debug("SQL:   DIST_UOM_CONV_MULTIPLIER="+pData.getDistUomConvMultiplier());
            log.debug("SQL:   DIST_UOM_CONV_COST="+pData.getDistUomConvCost());
            log.debug("SQL:   TOTAL_QUANTITY_RECEIVED="+pData.getTotalQuantityReceived());
            log.debug("SQL:   ERP_PO_REF_DATE="+pData.getErpPoRefDate());
            log.debug("SQL:   ERP_PO_REF_NUM="+pData.getErpPoRefNum());
            log.debug("SQL:   ERP_PO_REF_LINE_NUM="+pData.getErpPoRefLineNum());
            log.debug("SQL:   ASSET_ID="+pData.getAssetId());
            log.debug("SQL:   TAX_RATE="+pData.getTaxRate());
            log.debug("SQL:   TAX_AMOUNT="+pData.getTaxAmount());
            log.debug("SQL:   TAX_EXEMPT="+pData.getTaxExempt());
            log.debug("SQL:   OUTBOUND_PO_NUM="+pData.getOutboundPoNum());
            log.debug("SQL:   SERVICE_FEE="+pData.getServiceFee());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a OrderItemData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderItemId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderItemId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_ITEM WHERE ORDER_ITEM_ID = " + pOrderItemId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderItemData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_ITEM");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
    }

    /**
     * Inserts a OrderItemData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderItemData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderItemData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_ITEM (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_ID,ORDER_ITEM_ID,ORDER_ITEM_STATUS_CD,ITEM_ID,EXCEPTION_IND,ORDER_LINE_NUM,CUST_LINE_NUM,ERP_PO_LINE_NUM,ERP_ORDER_LINE_NUM,ERP_ORDER_NUM,ERP_PO_NUM,ERP_PO_DATE,ERP_PO_TIME,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_SIZE,ITEM_COST,CUST_ITEM_SKU_NUM,CUST_ITEM_SHORT_DESC,CUST_ITEM_UOM,CUST_ITEM_PACK,CUST_CONTRACT_PRICE,CUST_CONTRACT_ID,CUST_CONTRACT_SHORT_DESC,MANU_ITEM_SKU_NUM,MANU_ITEM_SHORT_DESC,MANU_ITEM_MSRP,MANU_ITEM_UPC_NUM,MANU_PACK_UPC_NUM,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC,DIST_ITEM_UOM,DIST_ITEM_PACK,DIST_ITEM_COST,DIST_ORDER_NUM,DIST_ERP_NUM,DIST_ITEM_QUANTITY,TOTAL_QUANTITY_ORDERED,TOTAL_QUANTITY_SHIPPED,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ACK_STATUS_CD,PURCHASE_ORDER_ID,TARGET_SHIP_DATE,QUANTITY_CONFIRMED,QUANTITY_BACKORDERED,COST_CENTER_ID,ORDER_ROUTING_ID,ITEM_QTY_855,FREIGHT_HANDLER_ID,INVENTORY_PAR_VALUE,INVENTORY_QTY_ON_HAND,SALE_TYPE_CD,DIST_BASE_COST,DIST_UOM_CONV_MULTIPLIER,DIST_UOM_CONV_COST,TOTAL_QUANTITY_RECEIVED,ERP_PO_REF_DATE,ERP_PO_REF_NUM,ERP_PO_REF_LINE_NUM,ASSET_ID,TAX_RATE,TAX_AMOUNT,TAX_EXEMPT,OUTBOUND_PO_NUM,SERVICE_FEE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderId());
        pstmt.setInt(2+4,pData.getOrderItemId());
        pstmt.setString(3+4,pData.getOrderItemStatusCd());
        pstmt.setInt(4+4,pData.getItemId());
        pstmt.setString(5+4,pData.getExceptionInd());
        pstmt.setInt(6+4,pData.getOrderLineNum());
        pstmt.setInt(7+4,pData.getCustLineNum());
        pstmt.setInt(8+4,pData.getErpPoLineNum());
        pstmt.setInt(9+4,pData.getErpOrderLineNum());
        pstmt.setString(10+4,pData.getErpOrderNum());
        pstmt.setString(11+4,pData.getErpPoNum());
        pstmt.setDate(12+4,DBAccess.toSQLDate(pData.getErpPoDate()));
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getErpPoTime()));
        pstmt.setInt(14+4,pData.getItemSkuNum());
        pstmt.setString(15+4,pData.getItemShortDesc());
        pstmt.setString(16+4,pData.getItemUom());
        pstmt.setString(17+4,pData.getItemPack());
        pstmt.setString(18+4,pData.getItemSize());
        pstmt.setBigDecimal(19+4,pData.getItemCost());
        pstmt.setString(20+4,pData.getCustItemSkuNum());
        pstmt.setString(21+4,pData.getCustItemShortDesc());
        pstmt.setString(22+4,pData.getCustItemUom());
        pstmt.setString(23+4,pData.getCustItemPack());
        pstmt.setBigDecimal(24+4,pData.getCustContractPrice());
        if (pData.getCustContractId() == 0) {
            pstmt.setNull(25+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(25+4,pData.getCustContractId());
        }

        pstmt.setString(26+4,pData.getCustContractShortDesc());
        pstmt.setString(27+4,pData.getManuItemSkuNum());
        pstmt.setString(28+4,pData.getManuItemShortDesc());
        pstmt.setBigDecimal(29+4,pData.getManuItemMsrp());
        pstmt.setString(30+4,pData.getManuItemUpcNum());
        pstmt.setString(31+4,pData.getManuPackUpcNum());
        pstmt.setString(32+4,pData.getDistItemSkuNum());
        pstmt.setString(33+4,pData.getDistItemShortDesc());
        pstmt.setString(34+4,pData.getDistItemUom());
        pstmt.setString(35+4,pData.getDistItemPack());
        pstmt.setBigDecimal(36+4,pData.getDistItemCost());
        pstmt.setString(37+4,pData.getDistOrderNum());
        pstmt.setString(38+4,pData.getDistErpNum());
        pstmt.setInt(39+4,pData.getDistItemQuantity());
        pstmt.setInt(40+4,pData.getTotalQuantityOrdered());
        pstmt.setInt(41+4,pData.getTotalQuantityShipped());
        pstmt.setString(42+4,pData.getComments());
        pstmt.setTimestamp(43+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(44+4,pData.getAddBy());
        pstmt.setTimestamp(45+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(46+4,pData.getModBy());
        pstmt.setString(47+4,pData.getAckStatusCd());
        if (pData.getPurchaseOrderId() == 0) {
            pstmt.setNull(48+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(48+4,pData.getPurchaseOrderId());
        }

        pstmt.setDate(49+4,DBAccess.toSQLDate(pData.getTargetShipDate()));
        pstmt.setInt(50+4,pData.getQuantityConfirmed());
        pstmt.setInt(51+4,pData.getQuantityBackordered());
        pstmt.setInt(52+4,pData.getCostCenterId());
        pstmt.setInt(53+4,pData.getOrderRoutingId());
        pstmt.setInt(54+4,pData.getItemQty855());
        pstmt.setInt(55+4,pData.getFreightHandlerId());
        pstmt.setInt(56+4,pData.getInventoryParValue());
        pstmt.setString(57+4,pData.getInventoryQtyOnHand());
        pstmt.setString(58+4,pData.getSaleTypeCd());
        pstmt.setBigDecimal(59+4,pData.getDistBaseCost());
        pstmt.setBigDecimal(60+4,pData.getDistUomConvMultiplier());
        pstmt.setBigDecimal(61+4,pData.getDistUomConvCost());
        pstmt.setInt(62+4,pData.getTotalQuantityReceived());
        pstmt.setDate(63+4,DBAccess.toSQLDate(pData.getErpPoRefDate()));
        pstmt.setString(64+4,pData.getErpPoRefNum());
        pstmt.setInt(65+4,pData.getErpPoRefLineNum());
        if (pData.getAssetId() == 0) {
            pstmt.setNull(66+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(66+4,pData.getAssetId());
        }

        pstmt.setBigDecimal(67+4,pData.getTaxRate());
        pstmt.setBigDecimal(68+4,pData.getTaxAmount());
        pstmt.setString(69+4,pData.getTaxExempt());
        pstmt.setString(70+4,pData.getOutboundPoNum());
        pstmt.setBigDecimal(71+4,pData.getServiceFee());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderItemData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderItemData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderItemData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderItemData insert(Connection pCon, OrderItemData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderItemData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderItemData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderItemData pData, boolean pLogFl)
        throws SQLException {
        OrderItemData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderItemId();
          try {
          oldData = OrderItemDataAccess.select(pCon,id);
          } catch(DataNotFoundException exc) {}
        }
        int n = update(pCon,pData);
        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, oldData, millis, "U", "O");
          insertLog(pCon, pData, millis, "U", "N");
        }
        return n;
    }

    /**
     * Deletes a OrderItemData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderItemId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderItemId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_ITEM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_ITEM d WHERE ORDER_ITEM_ID = " + pOrderItemId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderItemId);
        return n;
     }

    /**
     * Deletes OrderItemData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria, boolean pLogFl)
        throws SQLException {
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          StringBuffer sqlBuf =
             new StringBuffer("INSERT INTO LCLW_ORDER_ITEM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_ITEM d ");
          String where = pCriteria.getSqlClause();
          sqlBuf.append(" WHERE ");
          sqlBuf.append(where);

          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlBuf.toString());
          stmt.close();
        }
        int n = remove(pCon,pCriteria);
        return n;
    }
///////////////////////////////////////////////
}

