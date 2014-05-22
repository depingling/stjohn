
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ShoppingInfoDataAccess
 * Description:  This class is used to build access methods to the CLW_SHOPPING_INFO table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ShoppingInfoData;
import com.cleanwise.service.api.value.ShoppingInfoDataVector;
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
 * <code>ShoppingInfoDataAccess</code>
 */
public class ShoppingInfoDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ShoppingInfoDataAccess.class.getName());

    /** <code>CLW_SHOPPING_INFO</code> table name */
	/* Primary key: SHOPPING_INFO_ID */
	
    public static final String CLW_SHOPPING_INFO = "CLW_SHOPPING_INFO";
    
    /** <code>SHOPPING_INFO_ID</code> SHOPPING_INFO_ID column of table CLW_SHOPPING_INFO */
    public static final String SHOPPING_INFO_ID = "SHOPPING_INFO_ID";
    /** <code>ORDER_GUIDE_ID</code> ORDER_GUIDE_ID column of table CLW_SHOPPING_INFO */
    public static final String ORDER_GUIDE_ID = "ORDER_GUIDE_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_SHOPPING_INFO */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_SHOPPING_INFO */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_SHOPPING_INFO */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_SHOPPING_INFO */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_SHOPPING_INFO */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_SHOPPING_INFO */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_SHOPPING_INFO */
    public static final String MOD_BY = "MOD_BY";
    /** <code>SITE_ID</code> SITE_ID column of table CLW_SHOPPING_INFO */
    public static final String SITE_ID = "SITE_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_SHOPPING_INFO */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>MESSAGE_KEY</code> MESSAGE_KEY column of table CLW_SHOPPING_INFO */
    public static final String MESSAGE_KEY = "MESSAGE_KEY";
    /** <code>ARG0</code> ARG0 column of table CLW_SHOPPING_INFO */
    public static final String ARG0 = "ARG0";
    /** <code>ARG0_TYPE_CD</code> ARG0_TYPE_CD column of table CLW_SHOPPING_INFO */
    public static final String ARG0_TYPE_CD = "ARG0_TYPE_CD";
    /** <code>ARG1</code> ARG1 column of table CLW_SHOPPING_INFO */
    public static final String ARG1 = "ARG1";
    /** <code>ARG1_TYPE_CD</code> ARG1_TYPE_CD column of table CLW_SHOPPING_INFO */
    public static final String ARG1_TYPE_CD = "ARG1_TYPE_CD";
    /** <code>ARG2</code> ARG2 column of table CLW_SHOPPING_INFO */
    public static final String ARG2 = "ARG2";
    /** <code>ARG2_TYPE_CD</code> ARG2_TYPE_CD column of table CLW_SHOPPING_INFO */
    public static final String ARG2_TYPE_CD = "ARG2_TYPE_CD";
    /** <code>ARG3</code> ARG3 column of table CLW_SHOPPING_INFO */
    public static final String ARG3 = "ARG3";
    /** <code>ARG3_TYPE_CD</code> ARG3_TYPE_CD column of table CLW_SHOPPING_INFO */
    public static final String ARG3_TYPE_CD = "ARG3_TYPE_CD";
    /** <code>SHOPPING_INFO_STATUS_CD</code> SHOPPING_INFO_STATUS_CD column of table CLW_SHOPPING_INFO */
    public static final String SHOPPING_INFO_STATUS_CD = "SHOPPING_INFO_STATUS_CD";

    /**
     * Constructor.
     */
    public ShoppingInfoDataAccess()
    {
    }

    /**
     * Gets a ShoppingInfoData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pShoppingInfoId The key requested.
     * @return new ShoppingInfoData()
     * @throws            SQLException
     */
    public static ShoppingInfoData select(Connection pCon, int pShoppingInfoId)
        throws SQLException, DataNotFoundException {
        ShoppingInfoData x=null;
        String sql="SELECT SHOPPING_INFO_ID,ORDER_GUIDE_ID,ITEM_ID,SHORT_DESC,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID,ORDER_ID,MESSAGE_KEY,ARG0,ARG0_TYPE_CD,ARG1,ARG1_TYPE_CD,ARG2,ARG2_TYPE_CD,ARG3,ARG3_TYPE_CD,SHOPPING_INFO_STATUS_CD FROM CLW_SHOPPING_INFO WHERE SHOPPING_INFO_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pShoppingInfoId=" + pShoppingInfoId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pShoppingInfoId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ShoppingInfoData.createValue();
            
            x.setShoppingInfoId(rs.getInt(1));
            x.setOrderGuideId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setShortDesc(rs.getString(4));
            x.setValue(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setSiteId(rs.getInt(10));
            x.setOrderId(rs.getInt(11));
            x.setMessageKey(rs.getString(12));
            x.setArg0(rs.getString(13));
            x.setArg0TypeCd(rs.getString(14));
            x.setArg1(rs.getString(15));
            x.setArg1TypeCd(rs.getString(16));
            x.setArg2(rs.getString(17));
            x.setArg2TypeCd(rs.getString(18));
            x.setArg3(rs.getString(19));
            x.setArg3TypeCd(rs.getString(20));
            x.setShoppingInfoStatusCd(rs.getString(21));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("SHOPPING_INFO_ID :" + pShoppingInfoId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ShoppingInfoDataVector object that consists
     * of ShoppingInfoData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ShoppingInfoDataVector()
     * @throws            SQLException
     */
    public static ShoppingInfoDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ShoppingInfoData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_SHOPPING_INFO.SHOPPING_INFO_ID,CLW_SHOPPING_INFO.ORDER_GUIDE_ID,CLW_SHOPPING_INFO.ITEM_ID,CLW_SHOPPING_INFO.SHORT_DESC,CLW_SHOPPING_INFO.CLW_VALUE,CLW_SHOPPING_INFO.ADD_DATE,CLW_SHOPPING_INFO.ADD_BY,CLW_SHOPPING_INFO.MOD_DATE,CLW_SHOPPING_INFO.MOD_BY,CLW_SHOPPING_INFO.SITE_ID,CLW_SHOPPING_INFO.ORDER_ID,CLW_SHOPPING_INFO.MESSAGE_KEY,CLW_SHOPPING_INFO.ARG0,CLW_SHOPPING_INFO.ARG0_TYPE_CD,CLW_SHOPPING_INFO.ARG1,CLW_SHOPPING_INFO.ARG1_TYPE_CD,CLW_SHOPPING_INFO.ARG2,CLW_SHOPPING_INFO.ARG2_TYPE_CD,CLW_SHOPPING_INFO.ARG3,CLW_SHOPPING_INFO.ARG3_TYPE_CD,CLW_SHOPPING_INFO.SHOPPING_INFO_STATUS_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ShoppingInfoData Object.
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
    *@returns a populated ShoppingInfoData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ShoppingInfoData x = ShoppingInfoData.createValue();
         
         x.setShoppingInfoId(rs.getInt(1+offset));
         x.setOrderGuideId(rs.getInt(2+offset));
         x.setItemId(rs.getInt(3+offset));
         x.setShortDesc(rs.getString(4+offset));
         x.setValue(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         x.setSiteId(rs.getInt(10+offset));
         x.setOrderId(rs.getInt(11+offset));
         x.setMessageKey(rs.getString(12+offset));
         x.setArg0(rs.getString(13+offset));
         x.setArg0TypeCd(rs.getString(14+offset));
         x.setArg1(rs.getString(15+offset));
         x.setArg1TypeCd(rs.getString(16+offset));
         x.setArg2(rs.getString(17+offset));
         x.setArg2TypeCd(rs.getString(18+offset));
         x.setArg3(rs.getString(19+offset));
         x.setArg3TypeCd(rs.getString(20+offset));
         x.setShoppingInfoStatusCd(rs.getString(21+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ShoppingInfoData Object represents.
    */
    public int getColumnCount(){
        return 21;
    }

    /**
     * Gets a ShoppingInfoDataVector object that consists
     * of ShoppingInfoData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ShoppingInfoDataVector()
     * @throws            SQLException
     */
    public static ShoppingInfoDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT SHOPPING_INFO_ID,ORDER_GUIDE_ID,ITEM_ID,SHORT_DESC,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID,ORDER_ID,MESSAGE_KEY,ARG0,ARG0_TYPE_CD,ARG1,ARG1_TYPE_CD,ARG2,ARG2_TYPE_CD,ARG3,ARG3_TYPE_CD,SHOPPING_INFO_STATUS_CD FROM CLW_SHOPPING_INFO");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_SHOPPING_INFO.SHOPPING_INFO_ID,CLW_SHOPPING_INFO.ORDER_GUIDE_ID,CLW_SHOPPING_INFO.ITEM_ID,CLW_SHOPPING_INFO.SHORT_DESC,CLW_SHOPPING_INFO.CLW_VALUE,CLW_SHOPPING_INFO.ADD_DATE,CLW_SHOPPING_INFO.ADD_BY,CLW_SHOPPING_INFO.MOD_DATE,CLW_SHOPPING_INFO.MOD_BY,CLW_SHOPPING_INFO.SITE_ID,CLW_SHOPPING_INFO.ORDER_ID,CLW_SHOPPING_INFO.MESSAGE_KEY,CLW_SHOPPING_INFO.ARG0,CLW_SHOPPING_INFO.ARG0_TYPE_CD,CLW_SHOPPING_INFO.ARG1,CLW_SHOPPING_INFO.ARG1_TYPE_CD,CLW_SHOPPING_INFO.ARG2,CLW_SHOPPING_INFO.ARG2_TYPE_CD,CLW_SHOPPING_INFO.ARG3,CLW_SHOPPING_INFO.ARG3_TYPE_CD,CLW_SHOPPING_INFO.SHOPPING_INFO_STATUS_CD FROM CLW_SHOPPING_INFO");
                where = pCriteria.getSqlClause("CLW_SHOPPING_INFO");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SHOPPING_INFO.equals(otherTable)){
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
        ShoppingInfoDataVector v = new ShoppingInfoDataVector();
        while (rs.next()) {
            ShoppingInfoData x = ShoppingInfoData.createValue();
            
            x.setShoppingInfoId(rs.getInt(1));
            x.setOrderGuideId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setShortDesc(rs.getString(4));
            x.setValue(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setSiteId(rs.getInt(10));
            x.setOrderId(rs.getInt(11));
            x.setMessageKey(rs.getString(12));
            x.setArg0(rs.getString(13));
            x.setArg0TypeCd(rs.getString(14));
            x.setArg1(rs.getString(15));
            x.setArg1TypeCd(rs.getString(16));
            x.setArg2(rs.getString(17));
            x.setArg2TypeCd(rs.getString(18));
            x.setArg3(rs.getString(19));
            x.setArg3TypeCd(rs.getString(20));
            x.setShoppingInfoStatusCd(rs.getString(21));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ShoppingInfoDataVector object that consists
     * of ShoppingInfoData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ShoppingInfoData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ShoppingInfoDataVector()
     * @throws            SQLException
     */
    public static ShoppingInfoDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ShoppingInfoDataVector v = new ShoppingInfoDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT SHOPPING_INFO_ID,ORDER_GUIDE_ID,ITEM_ID,SHORT_DESC,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID,ORDER_ID,MESSAGE_KEY,ARG0,ARG0_TYPE_CD,ARG1,ARG1_TYPE_CD,ARG2,ARG2_TYPE_CD,ARG3,ARG3_TYPE_CD,SHOPPING_INFO_STATUS_CD FROM CLW_SHOPPING_INFO WHERE SHOPPING_INFO_ID IN (");

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
            ShoppingInfoData x=null;
            while (rs.next()) {
                // build the object
                x=ShoppingInfoData.createValue();
                
                x.setShoppingInfoId(rs.getInt(1));
                x.setOrderGuideId(rs.getInt(2));
                x.setItemId(rs.getInt(3));
                x.setShortDesc(rs.getString(4));
                x.setValue(rs.getString(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setAddBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));
                x.setSiteId(rs.getInt(10));
                x.setOrderId(rs.getInt(11));
                x.setMessageKey(rs.getString(12));
                x.setArg0(rs.getString(13));
                x.setArg0TypeCd(rs.getString(14));
                x.setArg1(rs.getString(15));
                x.setArg1TypeCd(rs.getString(16));
                x.setArg2(rs.getString(17));
                x.setArg2TypeCd(rs.getString(18));
                x.setArg3(rs.getString(19));
                x.setArg3TypeCd(rs.getString(20));
                x.setShoppingInfoStatusCd(rs.getString(21));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ShoppingInfoDataVector object of all
     * ShoppingInfoData objects in the database.
     * @param pCon An open database connection.
     * @return new ShoppingInfoDataVector()
     * @throws            SQLException
     */
    public static ShoppingInfoDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT SHOPPING_INFO_ID,ORDER_GUIDE_ID,ITEM_ID,SHORT_DESC,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID,ORDER_ID,MESSAGE_KEY,ARG0,ARG0_TYPE_CD,ARG1,ARG1_TYPE_CD,ARG2,ARG2_TYPE_CD,ARG3,ARG3_TYPE_CD,SHOPPING_INFO_STATUS_CD FROM CLW_SHOPPING_INFO";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ShoppingInfoDataVector v = new ShoppingInfoDataVector();
        ShoppingInfoData x = null;
        while (rs.next()) {
            // build the object
            x = ShoppingInfoData.createValue();
            
            x.setShoppingInfoId(rs.getInt(1));
            x.setOrderGuideId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setShortDesc(rs.getString(4));
            x.setValue(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setSiteId(rs.getInt(10));
            x.setOrderId(rs.getInt(11));
            x.setMessageKey(rs.getString(12));
            x.setArg0(rs.getString(13));
            x.setArg0TypeCd(rs.getString(14));
            x.setArg1(rs.getString(15));
            x.setArg1TypeCd(rs.getString(16));
            x.setArg2(rs.getString(17));
            x.setArg2TypeCd(rs.getString(18));
            x.setArg3(rs.getString(19));
            x.setArg3TypeCd(rs.getString(20));
            x.setShoppingInfoStatusCd(rs.getString(21));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ShoppingInfoData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT SHOPPING_INFO_ID FROM CLW_SHOPPING_INFO");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_INFO");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_INFO");
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
     * Inserts a ShoppingInfoData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingInfoData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ShoppingInfoData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ShoppingInfoData insert(Connection pCon, ShoppingInfoData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_SHOPPING_INFO_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_SHOPPING_INFO_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setShoppingInfoId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_SHOPPING_INFO (SHOPPING_INFO_ID,ORDER_GUIDE_ID,ITEM_ID,SHORT_DESC,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID,ORDER_ID,MESSAGE_KEY,ARG0,ARG0_TYPE_CD,ARG1,ARG1_TYPE_CD,ARG2,ARG2_TYPE_CD,ARG3,ARG3_TYPE_CD,SHOPPING_INFO_STATUS_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getShoppingInfoId());
        pstmt.setInt(2,pData.getOrderGuideId());
        pstmt.setInt(3,pData.getItemId());
        pstmt.setString(4,pData.getShortDesc());
        pstmt.setString(5,pData.getValue());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());
        pstmt.setInt(10,pData.getSiteId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(11, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(11,pData.getOrderId());
        }

        pstmt.setString(12,pData.getMessageKey());
        pstmt.setString(13,pData.getArg0());
        pstmt.setString(14,pData.getArg0TypeCd());
        pstmt.setString(15,pData.getArg1());
        pstmt.setString(16,pData.getArg1TypeCd());
        pstmt.setString(17,pData.getArg2());
        pstmt.setString(18,pData.getArg2TypeCd());
        pstmt.setString(19,pData.getArg3());
        pstmt.setString(20,pData.getArg3TypeCd());
        pstmt.setString(21,pData.getShoppingInfoStatusCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHOPPING_INFO_ID="+pData.getShoppingInfoId());
            log.debug("SQL:   ORDER_GUIDE_ID="+pData.getOrderGuideId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   MESSAGE_KEY="+pData.getMessageKey());
            log.debug("SQL:   ARG0="+pData.getArg0());
            log.debug("SQL:   ARG0_TYPE_CD="+pData.getArg0TypeCd());
            log.debug("SQL:   ARG1="+pData.getArg1());
            log.debug("SQL:   ARG1_TYPE_CD="+pData.getArg1TypeCd());
            log.debug("SQL:   ARG2="+pData.getArg2());
            log.debug("SQL:   ARG2_TYPE_CD="+pData.getArg2TypeCd());
            log.debug("SQL:   ARG3="+pData.getArg3());
            log.debug("SQL:   ARG3_TYPE_CD="+pData.getArg3TypeCd());
            log.debug("SQL:   SHOPPING_INFO_STATUS_CD="+pData.getShoppingInfoStatusCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setShoppingInfoId(0);
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
     * Updates a ShoppingInfoData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingInfoData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ShoppingInfoData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_SHOPPING_INFO SET ORDER_GUIDE_ID = ?,ITEM_ID = ?,SHORT_DESC = ?,CLW_VALUE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,SITE_ID = ?,ORDER_ID = ?,MESSAGE_KEY = ?,ARG0 = ?,ARG0_TYPE_CD = ?,ARG1 = ?,ARG1_TYPE_CD = ?,ARG2 = ?,ARG2_TYPE_CD = ?,ARG3 = ?,ARG3_TYPE_CD = ?,SHOPPING_INFO_STATUS_CD = ? WHERE SHOPPING_INFO_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getOrderGuideId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getValue());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getSiteId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getOrderId());
        }

        pstmt.setString(i++,pData.getMessageKey());
        pstmt.setString(i++,pData.getArg0());
        pstmt.setString(i++,pData.getArg0TypeCd());
        pstmt.setString(i++,pData.getArg1());
        pstmt.setString(i++,pData.getArg1TypeCd());
        pstmt.setString(i++,pData.getArg2());
        pstmt.setString(i++,pData.getArg2TypeCd());
        pstmt.setString(i++,pData.getArg3());
        pstmt.setString(i++,pData.getArg3TypeCd());
        pstmt.setString(i++,pData.getShoppingInfoStatusCd());
        pstmt.setInt(i++,pData.getShoppingInfoId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_GUIDE_ID="+pData.getOrderGuideId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   MESSAGE_KEY="+pData.getMessageKey());
            log.debug("SQL:   ARG0="+pData.getArg0());
            log.debug("SQL:   ARG0_TYPE_CD="+pData.getArg0TypeCd());
            log.debug("SQL:   ARG1="+pData.getArg1());
            log.debug("SQL:   ARG1_TYPE_CD="+pData.getArg1TypeCd());
            log.debug("SQL:   ARG2="+pData.getArg2());
            log.debug("SQL:   ARG2_TYPE_CD="+pData.getArg2TypeCd());
            log.debug("SQL:   ARG3="+pData.getArg3());
            log.debug("SQL:   ARG3_TYPE_CD="+pData.getArg3TypeCd());
            log.debug("SQL:   SHOPPING_INFO_STATUS_CD="+pData.getShoppingInfoStatusCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ShoppingInfoData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pShoppingInfoId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pShoppingInfoId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_SHOPPING_INFO WHERE SHOPPING_INFO_ID = " + pShoppingInfoId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ShoppingInfoData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_SHOPPING_INFO");
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
     * Inserts a ShoppingInfoData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingInfoData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ShoppingInfoData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_SHOPPING_INFO (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "SHOPPING_INFO_ID,ORDER_GUIDE_ID,ITEM_ID,SHORT_DESC,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID,ORDER_ID,MESSAGE_KEY,ARG0,ARG0_TYPE_CD,ARG1,ARG1_TYPE_CD,ARG2,ARG2_TYPE_CD,ARG3,ARG3_TYPE_CD,SHOPPING_INFO_STATUS_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getShoppingInfoId());
        pstmt.setInt(2+4,pData.getOrderGuideId());
        pstmt.setInt(3+4,pData.getItemId());
        pstmt.setString(4+4,pData.getShortDesc());
        pstmt.setString(5+4,pData.getValue());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());
        pstmt.setInt(10+4,pData.getSiteId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(11+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(11+4,pData.getOrderId());
        }

        pstmt.setString(12+4,pData.getMessageKey());
        pstmt.setString(13+4,pData.getArg0());
        pstmt.setString(14+4,pData.getArg0TypeCd());
        pstmt.setString(15+4,pData.getArg1());
        pstmt.setString(16+4,pData.getArg1TypeCd());
        pstmt.setString(17+4,pData.getArg2());
        pstmt.setString(18+4,pData.getArg2TypeCd());
        pstmt.setString(19+4,pData.getArg3());
        pstmt.setString(20+4,pData.getArg3TypeCd());
        pstmt.setString(21+4,pData.getShoppingInfoStatusCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ShoppingInfoData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingInfoData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ShoppingInfoData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ShoppingInfoData insert(Connection pCon, ShoppingInfoData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ShoppingInfoData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingInfoData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ShoppingInfoData pData, boolean pLogFl)
        throws SQLException {
        ShoppingInfoData oldData = null;
        if(pLogFl) {
          int id = pData.getShoppingInfoId();
          try {
          oldData = ShoppingInfoDataAccess.select(pCon,id);
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
     * Deletes a ShoppingInfoData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pShoppingInfoId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pShoppingInfoId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_SHOPPING_INFO SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SHOPPING_INFO d WHERE SHOPPING_INFO_ID = " + pShoppingInfoId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pShoppingInfoId);
        return n;
     }

    /**
     * Deletes ShoppingInfoData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_SHOPPING_INFO SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SHOPPING_INFO d ");
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

