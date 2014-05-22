
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PreOrderItemDataAccess
 * Description:  This class is used to build access methods to the CLW_PRE_ORDER_ITEM table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PreOrderItemData;
import com.cleanwise.service.api.value.PreOrderItemDataVector;
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
 * <code>PreOrderItemDataAccess</code>
 */
public class PreOrderItemDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PreOrderItemDataAccess.class.getName());

    /** <code>CLW_PRE_ORDER_ITEM</code> table name */
	/* Primary key: PRE_ORDER_ITEM_ID */
	
    public static final String CLW_PRE_ORDER_ITEM = "CLW_PRE_ORDER_ITEM";
    
    /** <code>PRE_ORDER_ITEM_ID</code> PRE_ORDER_ITEM_ID column of table CLW_PRE_ORDER_ITEM */
    public static final String PRE_ORDER_ITEM_ID = "PRE_ORDER_ITEM_ID";
    /** <code>PRE_ORDER_ID</code> PRE_ORDER_ID column of table CLW_PRE_ORDER_ITEM */
    public static final String PRE_ORDER_ID = "PRE_ORDER_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_PRE_ORDER_ITEM */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>LINE_NUMBER</code> LINE_NUMBER column of table CLW_PRE_ORDER_ITEM */
    public static final String LINE_NUMBER = "LINE_NUMBER";
    /** <code>QUANTITY</code> QUANTITY column of table CLW_PRE_ORDER_ITEM */
    public static final String QUANTITY = "QUANTITY";
    /** <code>CUSTOMER_UOM</code> CUSTOMER_UOM column of table CLW_PRE_ORDER_ITEM */
    public static final String CUSTOMER_UOM = "CUSTOMER_UOM";
    /** <code>CUSTOMER_SKU</code> CUSTOMER_SKU column of table CLW_PRE_ORDER_ITEM */
    public static final String CUSTOMER_SKU = "CUSTOMER_SKU";
    /** <code>CUSTOMER_PRODUCT_DESC</code> CUSTOMER_PRODUCT_DESC column of table CLW_PRE_ORDER_ITEM */
    public static final String CUSTOMER_PRODUCT_DESC = "CUSTOMER_PRODUCT_DESC";
    /** <code>CUSTOMER_PACK</code> CUSTOMER_PACK column of table CLW_PRE_ORDER_ITEM */
    public static final String CUSTOMER_PACK = "CUSTOMER_PACK";
    /** <code>AS_INVENTORY_ITEM</code> AS_INVENTORY_ITEM column of table CLW_PRE_ORDER_ITEM */
    public static final String AS_INVENTORY_ITEM = "AS_INVENTORY_ITEM";
    /** <code>SALE_TYPE_CD</code> SALE_TYPE_CD column of table CLW_PRE_ORDER_ITEM */
    public static final String SALE_TYPE_CD = "SALE_TYPE_CD";
    /** <code>PRICE</code> PRICE column of table CLW_PRE_ORDER_ITEM */
    public static final String PRICE = "PRICE";
    /** <code>INVENTORY_QTY_ON_HAND</code> INVENTORY_QTY_ON_HAND column of table CLW_PRE_ORDER_ITEM */
    public static final String INVENTORY_QTY_ON_HAND = "INVENTORY_QTY_ON_HAND";
    /** <code>INVENTORY_PAR_VALUE</code> INVENTORY_PAR_VALUE column of table CLW_PRE_ORDER_ITEM */
    public static final String INVENTORY_PAR_VALUE = "INVENTORY_PAR_VALUE";
    /** <code>ASSET_ID</code> ASSET_ID column of table CLW_PRE_ORDER_ITEM */
    public static final String ASSET_ID = "ASSET_ID";
    /** <code>DISTRIBUTOR_ID</code> DISTRIBUTOR_ID column of table CLW_PRE_ORDER_ITEM */
    public static final String DISTRIBUTOR_ID = "DISTRIBUTOR_ID";
    /** <code>ORDER_ITEM_ACTION_CD</code> ORDER_ITEM_ACTION_CD column of table CLW_PRE_ORDER_ITEM */
    public static final String ORDER_ITEM_ACTION_CD = "ORDER_ITEM_ACTION_CD";
    /** <code>TAX_EXEMPT</code> TAX_EXEMPT column of table CLW_PRE_ORDER_ITEM */
    public static final String TAX_EXEMPT = "TAX_EXEMPT";
    /** <code>TAX_AMOUNT</code> TAX_AMOUNT column of table CLW_PRE_ORDER_ITEM */
    public static final String TAX_AMOUNT = "TAX_AMOUNT";

    /**
     * Constructor.
     */
    public PreOrderItemDataAccess()
    {
    }

    /**
     * Gets a PreOrderItemData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPreOrderItemId The key requested.
     * @return new PreOrderItemData()
     * @throws            SQLException
     */
    public static PreOrderItemData select(Connection pCon, int pPreOrderItemId)
        throws SQLException, DataNotFoundException {
        PreOrderItemData x=null;
        String sql="SELECT PRE_ORDER_ITEM_ID,PRE_ORDER_ID,ITEM_ID,LINE_NUMBER,QUANTITY,CUSTOMER_UOM,CUSTOMER_SKU,CUSTOMER_PRODUCT_DESC,CUSTOMER_PACK,AS_INVENTORY_ITEM,SALE_TYPE_CD,PRICE,INVENTORY_QTY_ON_HAND,INVENTORY_PAR_VALUE,ASSET_ID,DISTRIBUTOR_ID,ORDER_ITEM_ACTION_CD,TAX_EXEMPT,TAX_AMOUNT FROM CLW_PRE_ORDER_ITEM WHERE PRE_ORDER_ITEM_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPreOrderItemId=" + pPreOrderItemId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPreOrderItemId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PreOrderItemData.createValue();
            
            x.setPreOrderItemId(rs.getInt(1));
            x.setPreOrderId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setLineNumber(rs.getInt(4));
            x.setQuantity(rs.getInt(5));
            x.setCustomerUom(rs.getString(6));
            x.setCustomerSku(rs.getString(7));
            x.setCustomerProductDesc(rs.getString(8));
            x.setCustomerPack(rs.getString(9));
            x.setAsInventoryItem(rs.getString(10));
            x.setSaleTypeCd(rs.getString(11));
            x.setPrice(rs.getBigDecimal(12));
            x.setInventoryQtyOnHand(rs.getString(13));
            x.setInventoryParValue(rs.getInt(14));
            x.setAssetId(rs.getInt(15));
            x.setDistributorId(rs.getInt(16));
            x.setOrderItemActionCd(rs.getString(17));
            x.setTaxExempt(rs.getString(18));
            x.setTaxAmount(rs.getBigDecimal(19));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PRE_ORDER_ITEM_ID :" + pPreOrderItemId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PreOrderItemDataVector object that consists
     * of PreOrderItemData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PreOrderItemDataVector()
     * @throws            SQLException
     */
    public static PreOrderItemDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PreOrderItemData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PRE_ORDER_ITEM.PRE_ORDER_ITEM_ID,CLW_PRE_ORDER_ITEM.PRE_ORDER_ID,CLW_PRE_ORDER_ITEM.ITEM_ID,CLW_PRE_ORDER_ITEM.LINE_NUMBER,CLW_PRE_ORDER_ITEM.QUANTITY,CLW_PRE_ORDER_ITEM.CUSTOMER_UOM,CLW_PRE_ORDER_ITEM.CUSTOMER_SKU,CLW_PRE_ORDER_ITEM.CUSTOMER_PRODUCT_DESC,CLW_PRE_ORDER_ITEM.CUSTOMER_PACK,CLW_PRE_ORDER_ITEM.AS_INVENTORY_ITEM,CLW_PRE_ORDER_ITEM.SALE_TYPE_CD,CLW_PRE_ORDER_ITEM.PRICE,CLW_PRE_ORDER_ITEM.INVENTORY_QTY_ON_HAND,CLW_PRE_ORDER_ITEM.INVENTORY_PAR_VALUE,CLW_PRE_ORDER_ITEM.ASSET_ID,CLW_PRE_ORDER_ITEM.DISTRIBUTOR_ID,CLW_PRE_ORDER_ITEM.ORDER_ITEM_ACTION_CD,CLW_PRE_ORDER_ITEM.TAX_EXEMPT,CLW_PRE_ORDER_ITEM.TAX_AMOUNT";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PreOrderItemData Object.
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
    *@returns a populated PreOrderItemData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PreOrderItemData x = PreOrderItemData.createValue();
         
         x.setPreOrderItemId(rs.getInt(1+offset));
         x.setPreOrderId(rs.getInt(2+offset));
         x.setItemId(rs.getInt(3+offset));
         x.setLineNumber(rs.getInt(4+offset));
         x.setQuantity(rs.getInt(5+offset));
         x.setCustomerUom(rs.getString(6+offset));
         x.setCustomerSku(rs.getString(7+offset));
         x.setCustomerProductDesc(rs.getString(8+offset));
         x.setCustomerPack(rs.getString(9+offset));
         x.setAsInventoryItem(rs.getString(10+offset));
         x.setSaleTypeCd(rs.getString(11+offset));
         x.setPrice(rs.getBigDecimal(12+offset));
         x.setInventoryQtyOnHand(rs.getString(13+offset));
         x.setInventoryParValue(rs.getInt(14+offset));
         x.setAssetId(rs.getInt(15+offset));
         x.setDistributorId(rs.getInt(16+offset));
         x.setOrderItemActionCd(rs.getString(17+offset));
         x.setTaxExempt(rs.getString(18+offset));
         x.setTaxAmount(rs.getBigDecimal(19+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PreOrderItemData Object represents.
    */
    public int getColumnCount(){
        return 19;
    }

    /**
     * Gets a PreOrderItemDataVector object that consists
     * of PreOrderItemData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PreOrderItemDataVector()
     * @throws            SQLException
     */
    public static PreOrderItemDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PRE_ORDER_ITEM_ID,PRE_ORDER_ID,ITEM_ID,LINE_NUMBER,QUANTITY,CUSTOMER_UOM,CUSTOMER_SKU,CUSTOMER_PRODUCT_DESC,CUSTOMER_PACK,AS_INVENTORY_ITEM,SALE_TYPE_CD,PRICE,INVENTORY_QTY_ON_HAND,INVENTORY_PAR_VALUE,ASSET_ID,DISTRIBUTOR_ID,ORDER_ITEM_ACTION_CD,TAX_EXEMPT,TAX_AMOUNT FROM CLW_PRE_ORDER_ITEM");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PRE_ORDER_ITEM.PRE_ORDER_ITEM_ID,CLW_PRE_ORDER_ITEM.PRE_ORDER_ID,CLW_PRE_ORDER_ITEM.ITEM_ID,CLW_PRE_ORDER_ITEM.LINE_NUMBER,CLW_PRE_ORDER_ITEM.QUANTITY,CLW_PRE_ORDER_ITEM.CUSTOMER_UOM,CLW_PRE_ORDER_ITEM.CUSTOMER_SKU,CLW_PRE_ORDER_ITEM.CUSTOMER_PRODUCT_DESC,CLW_PRE_ORDER_ITEM.CUSTOMER_PACK,CLW_PRE_ORDER_ITEM.AS_INVENTORY_ITEM,CLW_PRE_ORDER_ITEM.SALE_TYPE_CD,CLW_PRE_ORDER_ITEM.PRICE,CLW_PRE_ORDER_ITEM.INVENTORY_QTY_ON_HAND,CLW_PRE_ORDER_ITEM.INVENTORY_PAR_VALUE,CLW_PRE_ORDER_ITEM.ASSET_ID,CLW_PRE_ORDER_ITEM.DISTRIBUTOR_ID,CLW_PRE_ORDER_ITEM.ORDER_ITEM_ACTION_CD,CLW_PRE_ORDER_ITEM.TAX_EXEMPT,CLW_PRE_ORDER_ITEM.TAX_AMOUNT FROM CLW_PRE_ORDER_ITEM");
                where = pCriteria.getSqlClause("CLW_PRE_ORDER_ITEM");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRE_ORDER_ITEM.equals(otherTable)){
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
        PreOrderItemDataVector v = new PreOrderItemDataVector();
        while (rs.next()) {
            PreOrderItemData x = PreOrderItemData.createValue();
            
            x.setPreOrderItemId(rs.getInt(1));
            x.setPreOrderId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setLineNumber(rs.getInt(4));
            x.setQuantity(rs.getInt(5));
            x.setCustomerUom(rs.getString(6));
            x.setCustomerSku(rs.getString(7));
            x.setCustomerProductDesc(rs.getString(8));
            x.setCustomerPack(rs.getString(9));
            x.setAsInventoryItem(rs.getString(10));
            x.setSaleTypeCd(rs.getString(11));
            x.setPrice(rs.getBigDecimal(12));
            x.setInventoryQtyOnHand(rs.getString(13));
            x.setInventoryParValue(rs.getInt(14));
            x.setAssetId(rs.getInt(15));
            x.setDistributorId(rs.getInt(16));
            x.setOrderItemActionCd(rs.getString(17));
            x.setTaxExempt(rs.getString(18));
            x.setTaxAmount(rs.getBigDecimal(19));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a PreOrderItemDataVector object that consists
     * of PreOrderItemData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PreOrderItemData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PreOrderItemDataVector()
     * @throws            SQLException
     */
    public static PreOrderItemDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PreOrderItemDataVector v = new PreOrderItemDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PRE_ORDER_ITEM_ID,PRE_ORDER_ID,ITEM_ID,LINE_NUMBER,QUANTITY,CUSTOMER_UOM,CUSTOMER_SKU,CUSTOMER_PRODUCT_DESC,CUSTOMER_PACK,AS_INVENTORY_ITEM,SALE_TYPE_CD,PRICE,INVENTORY_QTY_ON_HAND,INVENTORY_PAR_VALUE,ASSET_ID,DISTRIBUTOR_ID,ORDER_ITEM_ACTION_CD,TAX_EXEMPT,TAX_AMOUNT FROM CLW_PRE_ORDER_ITEM WHERE PRE_ORDER_ITEM_ID IN (");

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
            PreOrderItemData x=null;
            while (rs.next()) {
                // build the object
                x=PreOrderItemData.createValue();
                
                x.setPreOrderItemId(rs.getInt(1));
                x.setPreOrderId(rs.getInt(2));
                x.setItemId(rs.getInt(3));
                x.setLineNumber(rs.getInt(4));
                x.setQuantity(rs.getInt(5));
                x.setCustomerUom(rs.getString(6));
                x.setCustomerSku(rs.getString(7));
                x.setCustomerProductDesc(rs.getString(8));
                x.setCustomerPack(rs.getString(9));
                x.setAsInventoryItem(rs.getString(10));
                x.setSaleTypeCd(rs.getString(11));
                x.setPrice(rs.getBigDecimal(12));
                x.setInventoryQtyOnHand(rs.getString(13));
                x.setInventoryParValue(rs.getInt(14));
                x.setAssetId(rs.getInt(15));
                x.setDistributorId(rs.getInt(16));
                x.setOrderItemActionCd(rs.getString(17));
                x.setTaxExempt(rs.getString(18));
                x.setTaxAmount(rs.getBigDecimal(19));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a PreOrderItemDataVector object of all
     * PreOrderItemData objects in the database.
     * @param pCon An open database connection.
     * @return new PreOrderItemDataVector()
     * @throws            SQLException
     */
    public static PreOrderItemDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PRE_ORDER_ITEM_ID,PRE_ORDER_ID,ITEM_ID,LINE_NUMBER,QUANTITY,CUSTOMER_UOM,CUSTOMER_SKU,CUSTOMER_PRODUCT_DESC,CUSTOMER_PACK,AS_INVENTORY_ITEM,SALE_TYPE_CD,PRICE,INVENTORY_QTY_ON_HAND,INVENTORY_PAR_VALUE,ASSET_ID,DISTRIBUTOR_ID,ORDER_ITEM_ACTION_CD,TAX_EXEMPT,TAX_AMOUNT FROM CLW_PRE_ORDER_ITEM";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PreOrderItemDataVector v = new PreOrderItemDataVector();
        PreOrderItemData x = null;
        while (rs.next()) {
            // build the object
            x = PreOrderItemData.createValue();
            
            x.setPreOrderItemId(rs.getInt(1));
            x.setPreOrderId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setLineNumber(rs.getInt(4));
            x.setQuantity(rs.getInt(5));
            x.setCustomerUom(rs.getString(6));
            x.setCustomerSku(rs.getString(7));
            x.setCustomerProductDesc(rs.getString(8));
            x.setCustomerPack(rs.getString(9));
            x.setAsInventoryItem(rs.getString(10));
            x.setSaleTypeCd(rs.getString(11));
            x.setPrice(rs.getBigDecimal(12));
            x.setInventoryQtyOnHand(rs.getString(13));
            x.setInventoryParValue(rs.getInt(14));
            x.setAssetId(rs.getInt(15));
            x.setDistributorId(rs.getInt(16));
            x.setOrderItemActionCd(rs.getString(17));
            x.setTaxExempt(rs.getString(18));
            x.setTaxAmount(rs.getBigDecimal(19));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * PreOrderItemData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PRE_ORDER_ITEM_ID FROM CLW_PRE_ORDER_ITEM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRE_ORDER_ITEM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRE_ORDER_ITEM");
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
     * Inserts a PreOrderItemData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderItemData object to insert.
     * @return new PreOrderItemData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PreOrderItemData insert(Connection pCon, PreOrderItemData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PRE_ORDER_ITEM_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PRE_ORDER_ITEM_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPreOrderItemId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PRE_ORDER_ITEM (PRE_ORDER_ITEM_ID,PRE_ORDER_ID,ITEM_ID,LINE_NUMBER,QUANTITY,CUSTOMER_UOM,CUSTOMER_SKU,CUSTOMER_PRODUCT_DESC,CUSTOMER_PACK,AS_INVENTORY_ITEM,SALE_TYPE_CD,PRICE,INVENTORY_QTY_ON_HAND,INVENTORY_PAR_VALUE,ASSET_ID,DISTRIBUTOR_ID,ORDER_ITEM_ACTION_CD,TAX_EXEMPT,TAX_AMOUNT) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pstmt.setInt(1,pData.getPreOrderItemId());
        pstmt.setInt(2,pData.getPreOrderId());
        pstmt.setInt(3,pData.getItemId());
        pstmt.setInt(4,pData.getLineNumber());
        pstmt.setInt(5,pData.getQuantity());
        pstmt.setString(6,pData.getCustomerUom());
        pstmt.setString(7,pData.getCustomerSku());
        pstmt.setString(8,pData.getCustomerProductDesc());
        pstmt.setString(9,pData.getCustomerPack());
        pstmt.setString(10,pData.getAsInventoryItem());
        pstmt.setString(11,pData.getSaleTypeCd());
        pstmt.setBigDecimal(12,pData.getPrice());
        pstmt.setString(13,pData.getInventoryQtyOnHand());
        pstmt.setInt(14,pData.getInventoryParValue());
        if (pData.getAssetId() == 0) {
            pstmt.setNull(15, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(15,pData.getAssetId());
        }

        pstmt.setInt(16,pData.getDistributorId());
        pstmt.setString(17,pData.getOrderItemActionCd());
        pstmt.setString(18,pData.getTaxExempt());
        pstmt.setBigDecimal(19,pData.getTaxAmount());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRE_ORDER_ITEM_ID="+pData.getPreOrderItemId());
            log.debug("SQL:   PRE_ORDER_ID="+pData.getPreOrderId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   LINE_NUMBER="+pData.getLineNumber());
            log.debug("SQL:   QUANTITY="+pData.getQuantity());
            log.debug("SQL:   CUSTOMER_UOM="+pData.getCustomerUom());
            log.debug("SQL:   CUSTOMER_SKU="+pData.getCustomerSku());
            log.debug("SQL:   CUSTOMER_PRODUCT_DESC="+pData.getCustomerProductDesc());
            log.debug("SQL:   CUSTOMER_PACK="+pData.getCustomerPack());
            log.debug("SQL:   AS_INVENTORY_ITEM="+pData.getAsInventoryItem());
            log.debug("SQL:   SALE_TYPE_CD="+pData.getSaleTypeCd());
            log.debug("SQL:   PRICE="+pData.getPrice());
            log.debug("SQL:   INVENTORY_QTY_ON_HAND="+pData.getInventoryQtyOnHand());
            log.debug("SQL:   INVENTORY_PAR_VALUE="+pData.getInventoryParValue());
            log.debug("SQL:   ASSET_ID="+pData.getAssetId());
            log.debug("SQL:   DISTRIBUTOR_ID="+pData.getDistributorId());
            log.debug("SQL:   ORDER_ITEM_ACTION_CD="+pData.getOrderItemActionCd());
            log.debug("SQL:   TAX_EXEMPT="+pData.getTaxExempt());
            log.debug("SQL:   TAX_AMOUNT="+pData.getTaxAmount());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setPreOrderItemId(0);
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
     * Updates a PreOrderItemData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderItemData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PreOrderItemData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PRE_ORDER_ITEM SET PRE_ORDER_ID = ?,ITEM_ID = ?,LINE_NUMBER = ?,QUANTITY = ?,CUSTOMER_UOM = ?,CUSTOMER_SKU = ?,CUSTOMER_PRODUCT_DESC = ?,CUSTOMER_PACK = ?,AS_INVENTORY_ITEM = ?,SALE_TYPE_CD = ?,PRICE = ?,INVENTORY_QTY_ON_HAND = ?,INVENTORY_PAR_VALUE = ?,ASSET_ID = ?,DISTRIBUTOR_ID = ?,ORDER_ITEM_ACTION_CD = ?,TAX_EXEMPT = ?,TAX_AMOUNT = ? WHERE PRE_ORDER_ITEM_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setInt(i++,pData.getPreOrderId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setInt(i++,pData.getLineNumber());
        pstmt.setInt(i++,pData.getQuantity());
        pstmt.setString(i++,pData.getCustomerUom());
        pstmt.setString(i++,pData.getCustomerSku());
        pstmt.setString(i++,pData.getCustomerProductDesc());
        pstmt.setString(i++,pData.getCustomerPack());
        pstmt.setString(i++,pData.getAsInventoryItem());
        pstmt.setString(i++,pData.getSaleTypeCd());
        pstmt.setBigDecimal(i++,pData.getPrice());
        pstmt.setString(i++,pData.getInventoryQtyOnHand());
        pstmt.setInt(i++,pData.getInventoryParValue());
        if (pData.getAssetId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getAssetId());
        }

        pstmt.setInt(i++,pData.getDistributorId());
        pstmt.setString(i++,pData.getOrderItemActionCd());
        pstmt.setString(i++,pData.getTaxExempt());
        pstmt.setBigDecimal(i++,pData.getTaxAmount());
        pstmt.setInt(i++,pData.getPreOrderItemId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRE_ORDER_ID="+pData.getPreOrderId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   LINE_NUMBER="+pData.getLineNumber());
            log.debug("SQL:   QUANTITY="+pData.getQuantity());
            log.debug("SQL:   CUSTOMER_UOM="+pData.getCustomerUom());
            log.debug("SQL:   CUSTOMER_SKU="+pData.getCustomerSku());
            log.debug("SQL:   CUSTOMER_PRODUCT_DESC="+pData.getCustomerProductDesc());
            log.debug("SQL:   CUSTOMER_PACK="+pData.getCustomerPack());
            log.debug("SQL:   AS_INVENTORY_ITEM="+pData.getAsInventoryItem());
            log.debug("SQL:   SALE_TYPE_CD="+pData.getSaleTypeCd());
            log.debug("SQL:   PRICE="+pData.getPrice());
            log.debug("SQL:   INVENTORY_QTY_ON_HAND="+pData.getInventoryQtyOnHand());
            log.debug("SQL:   INVENTORY_PAR_VALUE="+pData.getInventoryParValue());
            log.debug("SQL:   ASSET_ID="+pData.getAssetId());
            log.debug("SQL:   DISTRIBUTOR_ID="+pData.getDistributorId());
            log.debug("SQL:   ORDER_ITEM_ACTION_CD="+pData.getOrderItemActionCd());
            log.debug("SQL:   TAX_EXEMPT="+pData.getTaxExempt());
            log.debug("SQL:   TAX_AMOUNT="+pData.getTaxAmount());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a PreOrderItemData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPreOrderItemId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPreOrderItemId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PRE_ORDER_ITEM WHERE PRE_ORDER_ITEM_ID = " + pPreOrderItemId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PreOrderItemData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PRE_ORDER_ITEM");
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
     * Inserts a PreOrderItemData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderItemData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PreOrderItemData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PRE_ORDER_ITEM (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PRE_ORDER_ITEM_ID,PRE_ORDER_ID,ITEM_ID,LINE_NUMBER,QUANTITY,CUSTOMER_UOM,CUSTOMER_SKU,CUSTOMER_PRODUCT_DESC,CUSTOMER_PACK,AS_INVENTORY_ITEM,SALE_TYPE_CD,PRICE,INVENTORY_QTY_ON_HAND,INVENTORY_PAR_VALUE,ASSET_ID,DISTRIBUTOR_ID,ORDER_ITEM_ACTION_CD,TAX_EXEMPT,TAX_AMOUNT) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPreOrderItemId());
        pstmt.setInt(2+4,pData.getPreOrderId());
        pstmt.setInt(3+4,pData.getItemId());
        pstmt.setInt(4+4,pData.getLineNumber());
        pstmt.setInt(5+4,pData.getQuantity());
        pstmt.setString(6+4,pData.getCustomerUom());
        pstmt.setString(7+4,pData.getCustomerSku());
        pstmt.setString(8+4,pData.getCustomerProductDesc());
        pstmt.setString(9+4,pData.getCustomerPack());
        pstmt.setString(10+4,pData.getAsInventoryItem());
        pstmt.setString(11+4,pData.getSaleTypeCd());
        pstmt.setBigDecimal(12+4,pData.getPrice());
        pstmt.setString(13+4,pData.getInventoryQtyOnHand());
        pstmt.setInt(14+4,pData.getInventoryParValue());
        if (pData.getAssetId() == 0) {
            pstmt.setNull(15+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(15+4,pData.getAssetId());
        }

        pstmt.setInt(16+4,pData.getDistributorId());
        pstmt.setString(17+4,pData.getOrderItemActionCd());
        pstmt.setString(18+4,pData.getTaxExempt());
        pstmt.setBigDecimal(19+4,pData.getTaxAmount());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PreOrderItemData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderItemData object to insert.
     * @param pLogFl  Creates record in log table if true
     * @return new PreOrderItemData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PreOrderItemData insert(Connection pCon, PreOrderItemData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PreOrderItemData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderItemData object to update. 
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PreOrderItemData pData, boolean pLogFl)
        throws SQLException {
        PreOrderItemData oldData = null;
        if(pLogFl) {
          int id = pData.getPreOrderItemId();
          try {
          oldData = PreOrderItemDataAccess.select(pCon,id);
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
     * Deletes a PreOrderItemData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPreOrderItemId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPreOrderItemId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PRE_ORDER_ITEM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRE_ORDER_ITEM d WHERE PRE_ORDER_ITEM_ID = " + pPreOrderItemId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPreOrderItemId);
        return n;
     }

    /**
     * Deletes PreOrderItemData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PRE_ORDER_ITEM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRE_ORDER_ITEM d ");
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

