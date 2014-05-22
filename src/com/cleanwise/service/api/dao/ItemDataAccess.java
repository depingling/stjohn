
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ItemDataAccess
 * Description:  This class is used to build access methods to the CLW_ITEM table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.cachecos.Cachecos;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>ItemDataAccess</code>
 */
public class ItemDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ItemDataAccess.class.getName());

    /** <code>CLW_ITEM</code> table name */
	/* Primary key: ITEM_ID */
	
    public static final String CLW_ITEM = "CLW_ITEM";
    
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_ITEM */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_ITEM */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>SKU_NUM</code> SKU_NUM column of table CLW_ITEM */
    public static final String SKU_NUM = "SKU_NUM";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_ITEM */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_ITEM */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_ITEM */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>ITEM_TYPE_CD</code> ITEM_TYPE_CD column of table CLW_ITEM */
    public static final String ITEM_TYPE_CD = "ITEM_TYPE_CD";
    /** <code>ITEM_STATUS_CD</code> ITEM_STATUS_CD column of table CLW_ITEM */
    public static final String ITEM_STATUS_CD = "ITEM_STATUS_CD";
    /** <code>ITEM_ORDER_NUM</code> ITEM_ORDER_NUM column of table CLW_ITEM */
    public static final String ITEM_ORDER_NUM = "ITEM_ORDER_NUM";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ITEM */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ITEM */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ITEM */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ITEM */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ItemDataAccess()
    {
    }

    /**
     * Gets a ItemData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pItemId The key requested.
     * @return new ItemData()
     * @throws            SQLException
     */
    public static ItemData select(Connection pCon, int pItemId)
        throws SQLException, DataNotFoundException {
        ItemData x=null;
        String sql="SELECT ITEM_ID,SHORT_DESC,SKU_NUM,LONG_DESC,EFF_DATE,EXP_DATE,ITEM_TYPE_CD,ITEM_STATUS_CD,ITEM_ORDER_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM WHERE ITEM_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pItemId=" + pItemId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pItemId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ItemData.createValue();
            
            x.setItemId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setSkuNum(rs.getInt(3));
            x.setLongDesc(rs.getString(4));
            x.setEffDate(rs.getDate(5));
            x.setExpDate(rs.getDate(6));
            x.setItemTypeCd(rs.getString(7));
            x.setItemStatusCd(rs.getString(8));
            x.setItemOrderNum(rs.getInt(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ITEM_ID :" + pItemId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ItemDataVector object that consists
     * of ItemData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ItemDataVector()
     * @throws            SQLException
     */
    public static ItemDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ItemData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ITEM.ITEM_ID,CLW_ITEM.SHORT_DESC,CLW_ITEM.SKU_NUM,CLW_ITEM.LONG_DESC,CLW_ITEM.EFF_DATE,CLW_ITEM.EXP_DATE,CLW_ITEM.ITEM_TYPE_CD,CLW_ITEM.ITEM_STATUS_CD,CLW_ITEM.ITEM_ORDER_NUM,CLW_ITEM.ADD_DATE,CLW_ITEM.ADD_BY,CLW_ITEM.MOD_DATE,CLW_ITEM.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ItemData Object.
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
    *@returns a populated ItemData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ItemData x = ItemData.createValue();
         
         x.setItemId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setSkuNum(rs.getInt(3+offset));
         x.setLongDesc(rs.getString(4+offset));
         x.setEffDate(rs.getDate(5+offset));
         x.setExpDate(rs.getDate(6+offset));
         x.setItemTypeCd(rs.getString(7+offset));
         x.setItemStatusCd(rs.getString(8+offset));
         x.setItemOrderNum(rs.getInt(9+offset));
         x.setAddDate(rs.getTimestamp(10+offset));
         x.setAddBy(rs.getString(11+offset));
         x.setModDate(rs.getTimestamp(12+offset));
         x.setModBy(rs.getString(13+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ItemData Object represents.
    */
    public int getColumnCount(){
        return 13;
    }

    /**
     * Gets a ItemDataVector object that consists
     * of ItemData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ItemDataVector()
     * @throws            SQLException
     */
    public static ItemDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ITEM_ID,SHORT_DESC,SKU_NUM,LONG_DESC,EFF_DATE,EXP_DATE,ITEM_TYPE_CD,ITEM_STATUS_CD,ITEM_ORDER_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ITEM.ITEM_ID,CLW_ITEM.SHORT_DESC,CLW_ITEM.SKU_NUM,CLW_ITEM.LONG_DESC,CLW_ITEM.EFF_DATE,CLW_ITEM.EXP_DATE,CLW_ITEM.ITEM_TYPE_CD,CLW_ITEM.ITEM_STATUS_CD,CLW_ITEM.ITEM_ORDER_NUM,CLW_ITEM.ADD_DATE,CLW_ITEM.ADD_BY,CLW_ITEM.MOD_DATE,CLW_ITEM.MOD_BY FROM CLW_ITEM");
                where = pCriteria.getSqlClause("CLW_ITEM");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ITEM.equals(otherTable)){
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
        ItemDataVector v = new ItemDataVector();
        while (rs.next()) {
            ItemData x = ItemData.createValue();
            
            x.setItemId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setSkuNum(rs.getInt(3));
            x.setLongDesc(rs.getString(4));
            x.setEffDate(rs.getDate(5));
            x.setExpDate(rs.getDate(6));
            x.setItemTypeCd(rs.getString(7));
            x.setItemStatusCd(rs.getString(8));
            x.setItemOrderNum(rs.getInt(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ItemDataVector object that consists
     * of ItemData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ItemData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ItemDataVector()
     * @throws            SQLException
     */
    public static ItemDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ItemDataVector v = new ItemDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_ID,SHORT_DESC,SKU_NUM,LONG_DESC,EFF_DATE,EXP_DATE,ITEM_TYPE_CD,ITEM_STATUS_CD,ITEM_ORDER_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM WHERE ITEM_ID IN (");

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
            ItemData x=null;
            while (rs.next()) {
                // build the object
                x=ItemData.createValue();
                
                x.setItemId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setSkuNum(rs.getInt(3));
                x.setLongDesc(rs.getString(4));
                x.setEffDate(rs.getDate(5));
                x.setExpDate(rs.getDate(6));
                x.setItemTypeCd(rs.getString(7));
                x.setItemStatusCd(rs.getString(8));
                x.setItemOrderNum(rs.getInt(9));
                x.setAddDate(rs.getTimestamp(10));
                x.setAddBy(rs.getString(11));
                x.setModDate(rs.getTimestamp(12));
                x.setModBy(rs.getString(13));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ItemDataVector object of all
     * ItemData objects in the database.
     * @param pCon An open database connection.
     * @return new ItemDataVector()
     * @throws            SQLException
     */
    public static ItemDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ITEM_ID,SHORT_DESC,SKU_NUM,LONG_DESC,EFF_DATE,EXP_DATE,ITEM_TYPE_CD,ITEM_STATUS_CD,ITEM_ORDER_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ItemDataVector v = new ItemDataVector();
        ItemData x = null;
        while (rs.next()) {
            // build the object
            x = ItemData.createValue();
            
            x.setItemId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setSkuNum(rs.getInt(3));
            x.setLongDesc(rs.getString(4));
            x.setEffDate(rs.getDate(5));
            x.setExpDate(rs.getDate(6));
            x.setItemTypeCd(rs.getString(7));
            x.setItemStatusCd(rs.getString(8));
            x.setItemOrderNum(rs.getInt(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ItemData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_ID FROM CLW_ITEM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM");
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
     * Inserts a ItemData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ItemData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemData insert(Connection pCon, ItemData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ITEM_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ITEM_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setItemId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ITEM (ITEM_ID,SHORT_DESC,SKU_NUM,LONG_DESC,EFF_DATE,EXP_DATE,ITEM_TYPE_CD,ITEM_STATUS_CD,ITEM_ORDER_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getItemId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setInt(3,pData.getSkuNum());
        pstmt.setString(4,pData.getLongDesc());
        pstmt.setDate(5,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(6,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(7,pData.getItemTypeCd());
        pstmt.setString(8,pData.getItemStatusCd());
        pstmt.setInt(9,pData.getItemOrderNum());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11,pData.getAddBy());
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   SKU_NUM="+pData.getSkuNum());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   ITEM_TYPE_CD="+pData.getItemTypeCd());
            log.debug("SQL:   ITEM_STATUS_CD="+pData.getItemStatusCd());
            log.debug("SQL:   ITEM_ORDER_NUM="+pData.getItemOrderNum());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        
        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setItemId(0);
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
     * Updates a ItemData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ITEM SET SHORT_DESC = ?,SKU_NUM = ?,LONG_DESC = ?,EFF_DATE = ?,EXP_DATE = ?,ITEM_TYPE_CD = ?,ITEM_STATUS_CD = ?,ITEM_ORDER_NUM = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ITEM_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setInt(i++,pData.getSkuNum());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(i++,pData.getItemTypeCd());
        pstmt.setString(i++,pData.getItemStatusCd());
        pstmt.setInt(i++,pData.getItemOrderNum());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getItemId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   SKU_NUM="+pData.getSkuNum());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   ITEM_TYPE_CD="+pData.getItemTypeCd());
            log.debug("SQL:   ITEM_STATUS_CD="+pData.getItemStatusCd());
            log.debug("SQL:   ITEM_ORDER_NUM="+pData.getItemOrderNum());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();
        
        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ItemData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ITEM WHERE ITEM_ID = " + pItemId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ItemData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ITEM");
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
     * Inserts a ItemData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ItemData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ITEM (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ITEM_ID,SHORT_DESC,SKU_NUM,LONG_DESC,EFF_DATE,EXP_DATE,ITEM_TYPE_CD,ITEM_STATUS_CD,ITEM_ORDER_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getItemId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setInt(3+4,pData.getSkuNum());
        pstmt.setString(4+4,pData.getLongDesc());
        pstmt.setDate(5+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(6+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(7+4,pData.getItemTypeCd());
        pstmt.setString(8+4,pData.getItemStatusCd());
        pstmt.setInt(9+4,pData.getItemOrderNum());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11+4,pData.getAddBy());
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ItemData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ItemData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemData insert(Connection pCon, ItemData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ItemData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemData pData, boolean pLogFl)
        throws SQLException {
        ItemData oldData = null;
        if(pLogFl) {
          int id = pData.getItemId();
          try {
          oldData = ItemDataAccess.select(pCon,id);
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
     * Deletes a ItemData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ITEM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM d WHERE ITEM_ID = " + pItemId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pItemId);
        return n;
     }

    /**
     * Deletes ItemData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ITEM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM d ");
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

