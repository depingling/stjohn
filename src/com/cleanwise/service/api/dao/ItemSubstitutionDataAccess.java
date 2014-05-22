
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ItemSubstitutionDataAccess
 * Description:  This class is used to build access methods to the CLW_ITEM_SUBSTITUTION table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ItemSubstitutionData;
import com.cleanwise.service.api.value.ItemSubstitutionDataVector;
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
 * <code>ItemSubstitutionDataAccess</code>
 */
public class ItemSubstitutionDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ItemSubstitutionDataAccess.class.getName());

    /** <code>CLW_ITEM_SUBSTITUTION</code> table name */
	/* Primary key: ITEM_SUBSTITUTION_ID */
	
    public static final String CLW_ITEM_SUBSTITUTION = "CLW_ITEM_SUBSTITUTION";
    
    /** <code>ITEM_SUBSTITUTION_ID</code> ITEM_SUBSTITUTION_ID column of table CLW_ITEM_SUBSTITUTION */
    public static final String ITEM_SUBSTITUTION_ID = "ITEM_SUBSTITUTION_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_ITEM_SUBSTITUTION */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>ORDER_ITEM_ID</code> ORDER_ITEM_ID column of table CLW_ITEM_SUBSTITUTION */
    public static final String ORDER_ITEM_ID = "ORDER_ITEM_ID";
    /** <code>ITEM_SKU_NUM</code> ITEM_SKU_NUM column of table CLW_ITEM_SUBSTITUTION */
    public static final String ITEM_SKU_NUM = "ITEM_SKU_NUM";
    /** <code>DIST_ITEM_SKU_NUM</code> DIST_ITEM_SKU_NUM column of table CLW_ITEM_SUBSTITUTION */
    public static final String DIST_ITEM_SKU_NUM = "DIST_ITEM_SKU_NUM";
    /** <code>MANU_ITEM_SKU_NUM</code> MANU_ITEM_SKU_NUM column of table CLW_ITEM_SUBSTITUTION */
    public static final String MANU_ITEM_SKU_NUM = "MANU_ITEM_SKU_NUM";
    /** <code>ITEM_SHORT_DESC</code> ITEM_SHORT_DESC column of table CLW_ITEM_SUBSTITUTION */
    public static final String ITEM_SHORT_DESC = "ITEM_SHORT_DESC";
    /** <code>ITEM_UOM</code> ITEM_UOM column of table CLW_ITEM_SUBSTITUTION */
    public static final String ITEM_UOM = "ITEM_UOM";
    /** <code>ITEM_PACK</code> ITEM_PACK column of table CLW_ITEM_SUBSTITUTION */
    public static final String ITEM_PACK = "ITEM_PACK";
    /** <code>ITEM_QUANTITY</code> ITEM_QUANTITY column of table CLW_ITEM_SUBSTITUTION */
    public static final String ITEM_QUANTITY = "ITEM_QUANTITY";
    /** <code>ITEM_DIST_COST</code> ITEM_DIST_COST column of table CLW_ITEM_SUBSTITUTION */
    public static final String ITEM_DIST_COST = "ITEM_DIST_COST";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ITEM_SUBSTITUTION */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ITEM_SUBSTITUTION */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ITEM_SUBSTITUTION */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ITEM_SUBSTITUTION */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ItemSubstitutionDataAccess()
    {
    }

    /**
     * Gets a ItemSubstitutionData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pItemSubstitutionId The key requested.
     * @return new ItemSubstitutionData()
     * @throws            SQLException
     */
    public static ItemSubstitutionData select(Connection pCon, int pItemSubstitutionId)
        throws SQLException, DataNotFoundException {
        ItemSubstitutionData x=null;
        String sql="SELECT ITEM_SUBSTITUTION_ID,ORDER_ID,ORDER_ITEM_ID,ITEM_SKU_NUM,DIST_ITEM_SKU_NUM,MANU_ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,ITEM_DIST_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_SUBSTITUTION WHERE ITEM_SUBSTITUTION_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pItemSubstitutionId=" + pItemSubstitutionId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pItemSubstitutionId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ItemSubstitutionData.createValue();
            
            x.setItemSubstitutionId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setOrderItemId(rs.getInt(3));
            x.setItemSkuNum(rs.getInt(4));
            x.setDistItemSkuNum(rs.getString(5));
            x.setManuItemSkuNum(rs.getString(6));
            x.setItemShortDesc(rs.getString(7));
            x.setItemUom(rs.getString(8));
            x.setItemPack(rs.getString(9));
            x.setItemQuantity(rs.getInt(10));
            x.setItemDistCost(rs.getBigDecimal(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddBy(rs.getString(13));
            x.setModDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ITEM_SUBSTITUTION_ID :" + pItemSubstitutionId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ItemSubstitutionDataVector object that consists
     * of ItemSubstitutionData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ItemSubstitutionDataVector()
     * @throws            SQLException
     */
    public static ItemSubstitutionDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ItemSubstitutionData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ITEM_SUBSTITUTION.ITEM_SUBSTITUTION_ID,CLW_ITEM_SUBSTITUTION.ORDER_ID,CLW_ITEM_SUBSTITUTION.ORDER_ITEM_ID,CLW_ITEM_SUBSTITUTION.ITEM_SKU_NUM,CLW_ITEM_SUBSTITUTION.DIST_ITEM_SKU_NUM,CLW_ITEM_SUBSTITUTION.MANU_ITEM_SKU_NUM,CLW_ITEM_SUBSTITUTION.ITEM_SHORT_DESC,CLW_ITEM_SUBSTITUTION.ITEM_UOM,CLW_ITEM_SUBSTITUTION.ITEM_PACK,CLW_ITEM_SUBSTITUTION.ITEM_QUANTITY,CLW_ITEM_SUBSTITUTION.ITEM_DIST_COST,CLW_ITEM_SUBSTITUTION.ADD_DATE,CLW_ITEM_SUBSTITUTION.ADD_BY,CLW_ITEM_SUBSTITUTION.MOD_DATE,CLW_ITEM_SUBSTITUTION.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ItemSubstitutionData Object.
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
    *@returns a populated ItemSubstitutionData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ItemSubstitutionData x = ItemSubstitutionData.createValue();
         
         x.setItemSubstitutionId(rs.getInt(1+offset));
         x.setOrderId(rs.getInt(2+offset));
         x.setOrderItemId(rs.getInt(3+offset));
         x.setItemSkuNum(rs.getInt(4+offset));
         x.setDistItemSkuNum(rs.getString(5+offset));
         x.setManuItemSkuNum(rs.getString(6+offset));
         x.setItemShortDesc(rs.getString(7+offset));
         x.setItemUom(rs.getString(8+offset));
         x.setItemPack(rs.getString(9+offset));
         x.setItemQuantity(rs.getInt(10+offset));
         x.setItemDistCost(rs.getBigDecimal(11+offset));
         x.setAddDate(rs.getTimestamp(12+offset));
         x.setAddBy(rs.getString(13+offset));
         x.setModDate(rs.getTimestamp(14+offset));
         x.setModBy(rs.getString(15+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ItemSubstitutionData Object represents.
    */
    public int getColumnCount(){
        return 15;
    }

    /**
     * Gets a ItemSubstitutionDataVector object that consists
     * of ItemSubstitutionData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ItemSubstitutionDataVector()
     * @throws            SQLException
     */
    public static ItemSubstitutionDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ITEM_SUBSTITUTION_ID,ORDER_ID,ORDER_ITEM_ID,ITEM_SKU_NUM,DIST_ITEM_SKU_NUM,MANU_ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,ITEM_DIST_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_SUBSTITUTION");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ITEM_SUBSTITUTION.ITEM_SUBSTITUTION_ID,CLW_ITEM_SUBSTITUTION.ORDER_ID,CLW_ITEM_SUBSTITUTION.ORDER_ITEM_ID,CLW_ITEM_SUBSTITUTION.ITEM_SKU_NUM,CLW_ITEM_SUBSTITUTION.DIST_ITEM_SKU_NUM,CLW_ITEM_SUBSTITUTION.MANU_ITEM_SKU_NUM,CLW_ITEM_SUBSTITUTION.ITEM_SHORT_DESC,CLW_ITEM_SUBSTITUTION.ITEM_UOM,CLW_ITEM_SUBSTITUTION.ITEM_PACK,CLW_ITEM_SUBSTITUTION.ITEM_QUANTITY,CLW_ITEM_SUBSTITUTION.ITEM_DIST_COST,CLW_ITEM_SUBSTITUTION.ADD_DATE,CLW_ITEM_SUBSTITUTION.ADD_BY,CLW_ITEM_SUBSTITUTION.MOD_DATE,CLW_ITEM_SUBSTITUTION.MOD_BY FROM CLW_ITEM_SUBSTITUTION");
                where = pCriteria.getSqlClause("CLW_ITEM_SUBSTITUTION");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ITEM_SUBSTITUTION.equals(otherTable)){
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
        ItemSubstitutionDataVector v = new ItemSubstitutionDataVector();
        while (rs.next()) {
            ItemSubstitutionData x = ItemSubstitutionData.createValue();
            
            x.setItemSubstitutionId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setOrderItemId(rs.getInt(3));
            x.setItemSkuNum(rs.getInt(4));
            x.setDistItemSkuNum(rs.getString(5));
            x.setManuItemSkuNum(rs.getString(6));
            x.setItemShortDesc(rs.getString(7));
            x.setItemUom(rs.getString(8));
            x.setItemPack(rs.getString(9));
            x.setItemQuantity(rs.getInt(10));
            x.setItemDistCost(rs.getBigDecimal(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddBy(rs.getString(13));
            x.setModDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ItemSubstitutionDataVector object that consists
     * of ItemSubstitutionData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ItemSubstitutionData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ItemSubstitutionDataVector()
     * @throws            SQLException
     */
    public static ItemSubstitutionDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ItemSubstitutionDataVector v = new ItemSubstitutionDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_SUBSTITUTION_ID,ORDER_ID,ORDER_ITEM_ID,ITEM_SKU_NUM,DIST_ITEM_SKU_NUM,MANU_ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,ITEM_DIST_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_SUBSTITUTION WHERE ITEM_SUBSTITUTION_ID IN (");

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
            ItemSubstitutionData x=null;
            while (rs.next()) {
                // build the object
                x=ItemSubstitutionData.createValue();
                
                x.setItemSubstitutionId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setOrderItemId(rs.getInt(3));
                x.setItemSkuNum(rs.getInt(4));
                x.setDistItemSkuNum(rs.getString(5));
                x.setManuItemSkuNum(rs.getString(6));
                x.setItemShortDesc(rs.getString(7));
                x.setItemUom(rs.getString(8));
                x.setItemPack(rs.getString(9));
                x.setItemQuantity(rs.getInt(10));
                x.setItemDistCost(rs.getBigDecimal(11));
                x.setAddDate(rs.getTimestamp(12));
                x.setAddBy(rs.getString(13));
                x.setModDate(rs.getTimestamp(14));
                x.setModBy(rs.getString(15));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ItemSubstitutionDataVector object of all
     * ItemSubstitutionData objects in the database.
     * @param pCon An open database connection.
     * @return new ItemSubstitutionDataVector()
     * @throws            SQLException
     */
    public static ItemSubstitutionDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ITEM_SUBSTITUTION_ID,ORDER_ID,ORDER_ITEM_ID,ITEM_SKU_NUM,DIST_ITEM_SKU_NUM,MANU_ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,ITEM_DIST_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_SUBSTITUTION";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ItemSubstitutionDataVector v = new ItemSubstitutionDataVector();
        ItemSubstitutionData x = null;
        while (rs.next()) {
            // build the object
            x = ItemSubstitutionData.createValue();
            
            x.setItemSubstitutionId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setOrderItemId(rs.getInt(3));
            x.setItemSkuNum(rs.getInt(4));
            x.setDistItemSkuNum(rs.getString(5));
            x.setManuItemSkuNum(rs.getString(6));
            x.setItemShortDesc(rs.getString(7));
            x.setItemUom(rs.getString(8));
            x.setItemPack(rs.getString(9));
            x.setItemQuantity(rs.getInt(10));
            x.setItemDistCost(rs.getBigDecimal(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddBy(rs.getString(13));
            x.setModDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ItemSubstitutionData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_SUBSTITUTION_ID FROM CLW_ITEM_SUBSTITUTION");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_SUBSTITUTION");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_SUBSTITUTION");
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
     * Inserts a ItemSubstitutionData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemSubstitutionData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ItemSubstitutionData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemSubstitutionData insert(Connection pCon, ItemSubstitutionData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ITEM_SUBSTITUTION_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ITEM_SUBSTITUTION_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setItemSubstitutionId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ITEM_SUBSTITUTION (ITEM_SUBSTITUTION_ID,ORDER_ID,ORDER_ITEM_ID,ITEM_SKU_NUM,DIST_ITEM_SKU_NUM,MANU_ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,ITEM_DIST_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getItemSubstitutionId());
        pstmt.setInt(2,pData.getOrderId());
        pstmt.setInt(3,pData.getOrderItemId());
        pstmt.setInt(4,pData.getItemSkuNum());
        pstmt.setString(5,pData.getDistItemSkuNum());
        pstmt.setString(6,pData.getManuItemSkuNum());
        pstmt.setString(7,pData.getItemShortDesc());
        pstmt.setString(8,pData.getItemUom());
        pstmt.setString(9,pData.getItemPack());
        pstmt.setInt(10,pData.getItemQuantity());
        pstmt.setBigDecimal(11,pData.getItemDistCost());
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(13,pData.getAddBy());
        pstmt.setTimestamp(14,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(15,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_SUBSTITUTION_ID="+pData.getItemSubstitutionId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   ITEM_SKU_NUM="+pData.getItemSkuNum());
            log.debug("SQL:   DIST_ITEM_SKU_NUM="+pData.getDistItemSkuNum());
            log.debug("SQL:   MANU_ITEM_SKU_NUM="+pData.getManuItemSkuNum());
            log.debug("SQL:   ITEM_SHORT_DESC="+pData.getItemShortDesc());
            log.debug("SQL:   ITEM_UOM="+pData.getItemUom());
            log.debug("SQL:   ITEM_PACK="+pData.getItemPack());
            log.debug("SQL:   ITEM_QUANTITY="+pData.getItemQuantity());
            log.debug("SQL:   ITEM_DIST_COST="+pData.getItemDistCost());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setItemSubstitutionId(0);
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
     * Updates a ItemSubstitutionData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemSubstitutionData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemSubstitutionData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ITEM_SUBSTITUTION SET ORDER_ID = ?,ORDER_ITEM_ID = ?,ITEM_SKU_NUM = ?,DIST_ITEM_SKU_NUM = ?,MANU_ITEM_SKU_NUM = ?,ITEM_SHORT_DESC = ?,ITEM_UOM = ?,ITEM_PACK = ?,ITEM_QUANTITY = ?,ITEM_DIST_COST = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ITEM_SUBSTITUTION_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getOrderId());
        pstmt.setInt(i++,pData.getOrderItemId());
        pstmt.setInt(i++,pData.getItemSkuNum());
        pstmt.setString(i++,pData.getDistItemSkuNum());
        pstmt.setString(i++,pData.getManuItemSkuNum());
        pstmt.setString(i++,pData.getItemShortDesc());
        pstmt.setString(i++,pData.getItemUom());
        pstmt.setString(i++,pData.getItemPack());
        pstmt.setInt(i++,pData.getItemQuantity());
        pstmt.setBigDecimal(i++,pData.getItemDistCost());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getItemSubstitutionId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   ITEM_SKU_NUM="+pData.getItemSkuNum());
            log.debug("SQL:   DIST_ITEM_SKU_NUM="+pData.getDistItemSkuNum());
            log.debug("SQL:   MANU_ITEM_SKU_NUM="+pData.getManuItemSkuNum());
            log.debug("SQL:   ITEM_SHORT_DESC="+pData.getItemShortDesc());
            log.debug("SQL:   ITEM_UOM="+pData.getItemUom());
            log.debug("SQL:   ITEM_PACK="+pData.getItemPack());
            log.debug("SQL:   ITEM_QUANTITY="+pData.getItemQuantity());
            log.debug("SQL:   ITEM_DIST_COST="+pData.getItemDistCost());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ItemSubstitutionData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemSubstitutionId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemSubstitutionId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ITEM_SUBSTITUTION WHERE ITEM_SUBSTITUTION_ID = " + pItemSubstitutionId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ItemSubstitutionData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ITEM_SUBSTITUTION");
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
     * Inserts a ItemSubstitutionData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemSubstitutionData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ItemSubstitutionData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ITEM_SUBSTITUTION (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ITEM_SUBSTITUTION_ID,ORDER_ID,ORDER_ITEM_ID,ITEM_SKU_NUM,DIST_ITEM_SKU_NUM,MANU_ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,ITEM_DIST_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getItemSubstitutionId());
        pstmt.setInt(2+4,pData.getOrderId());
        pstmt.setInt(3+4,pData.getOrderItemId());
        pstmt.setInt(4+4,pData.getItemSkuNum());
        pstmt.setString(5+4,pData.getDistItemSkuNum());
        pstmt.setString(6+4,pData.getManuItemSkuNum());
        pstmt.setString(7+4,pData.getItemShortDesc());
        pstmt.setString(8+4,pData.getItemUom());
        pstmt.setString(9+4,pData.getItemPack());
        pstmt.setInt(10+4,pData.getItemQuantity());
        pstmt.setBigDecimal(11+4,pData.getItemDistCost());
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(13+4,pData.getAddBy());
        pstmt.setTimestamp(14+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(15+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ItemSubstitutionData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemSubstitutionData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ItemSubstitutionData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemSubstitutionData insert(Connection pCon, ItemSubstitutionData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ItemSubstitutionData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemSubstitutionData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemSubstitutionData pData, boolean pLogFl)
        throws SQLException {
        ItemSubstitutionData oldData = null;
        if(pLogFl) {
          int id = pData.getItemSubstitutionId();
          try {
          oldData = ItemSubstitutionDataAccess.select(pCon,id);
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
     * Deletes a ItemSubstitutionData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemSubstitutionId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemSubstitutionId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ITEM_SUBSTITUTION SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM_SUBSTITUTION d WHERE ITEM_SUBSTITUTION_ID = " + pItemSubstitutionId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pItemSubstitutionId);
        return n;
     }

    /**
     * Deletes ItemSubstitutionData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ITEM_SUBSTITUTION SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM_SUBSTITUTION d ");
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

