
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ProductViewDefDataAccess
 * Description:  This class is used to build access methods to the CLW_PRODUCT_VIEW_DEF table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ProductViewDefData;
import com.cleanwise.service.api.value.ProductViewDefDataVector;
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
 * <code>ProductViewDefDataAccess</code>
 */
public class ProductViewDefDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ProductViewDefDataAccess.class.getName());

    /** <code>CLW_PRODUCT_VIEW_DEF</code> table name */
	/* Primary key: PRODUCT_VIEW_DEF_ID */
	
    public static final String CLW_PRODUCT_VIEW_DEF = "CLW_PRODUCT_VIEW_DEF";
    
    /** <code>PRODUCT_VIEW_DEF_ID</code> PRODUCT_VIEW_DEF_ID column of table CLW_PRODUCT_VIEW_DEF */
    public static final String PRODUCT_VIEW_DEF_ID = "PRODUCT_VIEW_DEF_ID";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_PRODUCT_VIEW_DEF */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>ACCOUNT_ID</code> ACCOUNT_ID column of table CLW_PRODUCT_VIEW_DEF */
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    /** <code>ATTRIBUTENAME</code> ATTRIBUTENAME column of table CLW_PRODUCT_VIEW_DEF */
    public static final String ATTRIBUTENAME = "ATTRIBUTENAME";
    /** <code>SORT_ORDER</code> SORT_ORDER column of table CLW_PRODUCT_VIEW_DEF */
    public static final String SORT_ORDER = "SORT_ORDER";
    /** <code>WIDTH</code> WIDTH column of table CLW_PRODUCT_VIEW_DEF */
    public static final String WIDTH = "WIDTH";
    /** <code>STYLE_CLASS</code> STYLE_CLASS column of table CLW_PRODUCT_VIEW_DEF */
    public static final String STYLE_CLASS = "STYLE_CLASS";
    /** <code>PRODUCT_VIEW_CD</code> PRODUCT_VIEW_CD column of table CLW_PRODUCT_VIEW_DEF */
    public static final String PRODUCT_VIEW_CD = "PRODUCT_VIEW_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PRODUCT_VIEW_DEF */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PRODUCT_VIEW_DEF */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PRODUCT_VIEW_DEF */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PRODUCT_VIEW_DEF */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ProductViewDefDataAccess()
    {
    }

    /**
     * Gets a ProductViewDefData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pProductViewDefId The key requested.
     * @return new ProductViewDefData()
     * @throws            SQLException
     */
    public static ProductViewDefData select(Connection pCon, int pProductViewDefId)
        throws SQLException, DataNotFoundException {
        ProductViewDefData x=null;
        String sql="SELECT PRODUCT_VIEW_DEF_ID,STATUS_CD,ACCOUNT_ID,ATTRIBUTENAME,SORT_ORDER,WIDTH,STYLE_CLASS,PRODUCT_VIEW_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRODUCT_VIEW_DEF WHERE PRODUCT_VIEW_DEF_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pProductViewDefId=" + pProductViewDefId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pProductViewDefId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ProductViewDefData.createValue();
            
            x.setProductViewDefId(rs.getInt(1));
            x.setStatusCd(rs.getString(2));
            x.setAccountId(rs.getInt(3));
            x.setAttributename(rs.getString(4));
            x.setSortOrder(rs.getInt(5));
            x.setWidth(rs.getInt(6));
            x.setStyleClass(rs.getString(7));
            x.setProductViewCd(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PRODUCT_VIEW_DEF_ID :" + pProductViewDefId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ProductViewDefDataVector object that consists
     * of ProductViewDefData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ProductViewDefDataVector()
     * @throws            SQLException
     */
    public static ProductViewDefDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ProductViewDefData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PRODUCT_VIEW_DEF.PRODUCT_VIEW_DEF_ID,CLW_PRODUCT_VIEW_DEF.STATUS_CD,CLW_PRODUCT_VIEW_DEF.ACCOUNT_ID,CLW_PRODUCT_VIEW_DEF.ATTRIBUTENAME,CLW_PRODUCT_VIEW_DEF.SORT_ORDER,CLW_PRODUCT_VIEW_DEF.WIDTH,CLW_PRODUCT_VIEW_DEF.STYLE_CLASS,CLW_PRODUCT_VIEW_DEF.PRODUCT_VIEW_CD,CLW_PRODUCT_VIEW_DEF.ADD_DATE,CLW_PRODUCT_VIEW_DEF.ADD_BY,CLW_PRODUCT_VIEW_DEF.MOD_DATE,CLW_PRODUCT_VIEW_DEF.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ProductViewDefData Object.
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
    *@returns a populated ProductViewDefData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ProductViewDefData x = ProductViewDefData.createValue();
         
         x.setProductViewDefId(rs.getInt(1+offset));
         x.setStatusCd(rs.getString(2+offset));
         x.setAccountId(rs.getInt(3+offset));
         x.setAttributename(rs.getString(4+offset));
         x.setSortOrder(rs.getInt(5+offset));
         x.setWidth(rs.getInt(6+offset));
         x.setStyleClass(rs.getString(7+offset));
         x.setProductViewCd(rs.getString(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ProductViewDefData Object represents.
    */
    public int getColumnCount(){
        return 12;
    }

    /**
     * Gets a ProductViewDefDataVector object that consists
     * of ProductViewDefData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ProductViewDefDataVector()
     * @throws            SQLException
     */
    public static ProductViewDefDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PRODUCT_VIEW_DEF_ID,STATUS_CD,ACCOUNT_ID,ATTRIBUTENAME,SORT_ORDER,WIDTH,STYLE_CLASS,PRODUCT_VIEW_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRODUCT_VIEW_DEF");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PRODUCT_VIEW_DEF.PRODUCT_VIEW_DEF_ID,CLW_PRODUCT_VIEW_DEF.STATUS_CD,CLW_PRODUCT_VIEW_DEF.ACCOUNT_ID,CLW_PRODUCT_VIEW_DEF.ATTRIBUTENAME,CLW_PRODUCT_VIEW_DEF.SORT_ORDER,CLW_PRODUCT_VIEW_DEF.WIDTH,CLW_PRODUCT_VIEW_DEF.STYLE_CLASS,CLW_PRODUCT_VIEW_DEF.PRODUCT_VIEW_CD,CLW_PRODUCT_VIEW_DEF.ADD_DATE,CLW_PRODUCT_VIEW_DEF.ADD_BY,CLW_PRODUCT_VIEW_DEF.MOD_DATE,CLW_PRODUCT_VIEW_DEF.MOD_BY FROM CLW_PRODUCT_VIEW_DEF");
                where = pCriteria.getSqlClause("CLW_PRODUCT_VIEW_DEF");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRODUCT_VIEW_DEF.equals(otherTable)){
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
        ProductViewDefDataVector v = new ProductViewDefDataVector();
        while (rs.next()) {
            ProductViewDefData x = ProductViewDefData.createValue();
            
            x.setProductViewDefId(rs.getInt(1));
            x.setStatusCd(rs.getString(2));
            x.setAccountId(rs.getInt(3));
            x.setAttributename(rs.getString(4));
            x.setSortOrder(rs.getInt(5));
            x.setWidth(rs.getInt(6));
            x.setStyleClass(rs.getString(7));
            x.setProductViewCd(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ProductViewDefDataVector object that consists
     * of ProductViewDefData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ProductViewDefData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ProductViewDefDataVector()
     * @throws            SQLException
     */
    public static ProductViewDefDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ProductViewDefDataVector v = new ProductViewDefDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PRODUCT_VIEW_DEF_ID,STATUS_CD,ACCOUNT_ID,ATTRIBUTENAME,SORT_ORDER,WIDTH,STYLE_CLASS,PRODUCT_VIEW_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRODUCT_VIEW_DEF WHERE PRODUCT_VIEW_DEF_ID IN (");

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
            ProductViewDefData x=null;
            while (rs.next()) {
                // build the object
                x=ProductViewDefData.createValue();
                
                x.setProductViewDefId(rs.getInt(1));
                x.setStatusCd(rs.getString(2));
                x.setAccountId(rs.getInt(3));
                x.setAttributename(rs.getString(4));
                x.setSortOrder(rs.getInt(5));
                x.setWidth(rs.getInt(6));
                x.setStyleClass(rs.getString(7));
                x.setProductViewCd(rs.getString(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setAddBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ProductViewDefDataVector object of all
     * ProductViewDefData objects in the database.
     * @param pCon An open database connection.
     * @return new ProductViewDefDataVector()
     * @throws            SQLException
     */
    public static ProductViewDefDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PRODUCT_VIEW_DEF_ID,STATUS_CD,ACCOUNT_ID,ATTRIBUTENAME,SORT_ORDER,WIDTH,STYLE_CLASS,PRODUCT_VIEW_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRODUCT_VIEW_DEF";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ProductViewDefDataVector v = new ProductViewDefDataVector();
        ProductViewDefData x = null;
        while (rs.next()) {
            // build the object
            x = ProductViewDefData.createValue();
            
            x.setProductViewDefId(rs.getInt(1));
            x.setStatusCd(rs.getString(2));
            x.setAccountId(rs.getInt(3));
            x.setAttributename(rs.getString(4));
            x.setSortOrder(rs.getInt(5));
            x.setWidth(rs.getInt(6));
            x.setStyleClass(rs.getString(7));
            x.setProductViewCd(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ProductViewDefData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PRODUCT_VIEW_DEF_ID FROM CLW_PRODUCT_VIEW_DEF");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRODUCT_VIEW_DEF");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRODUCT_VIEW_DEF");
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
     * Inserts a ProductViewDefData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProductViewDefData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ProductViewDefData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProductViewDefData insert(Connection pCon, ProductViewDefData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PRODUCT_VIEW_DEF_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PRODUCT_VIEW_DEF_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setProductViewDefId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PRODUCT_VIEW_DEF (PRODUCT_VIEW_DEF_ID,STATUS_CD,ACCOUNT_ID,ATTRIBUTENAME,SORT_ORDER,WIDTH,STYLE_CLASS,PRODUCT_VIEW_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getProductViewDefId());
        pstmt.setString(2,pData.getStatusCd());
        pstmt.setInt(3,pData.getAccountId());
        pstmt.setString(4,pData.getAttributename());
        pstmt.setInt(5,pData.getSortOrder());
        pstmt.setInt(6,pData.getWidth());
        pstmt.setString(7,pData.getStyleClass());
        pstmt.setString(8,pData.getProductViewCd());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRODUCT_VIEW_DEF_ID="+pData.getProductViewDefId());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   ATTRIBUTENAME="+pData.getAttributename());
            log.debug("SQL:   SORT_ORDER="+pData.getSortOrder());
            log.debug("SQL:   WIDTH="+pData.getWidth());
            log.debug("SQL:   STYLE_CLASS="+pData.getStyleClass());
            log.debug("SQL:   PRODUCT_VIEW_CD="+pData.getProductViewCd());
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
        pData.setProductViewDefId(0);
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
     * Updates a ProductViewDefData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProductViewDefData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProductViewDefData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PRODUCT_VIEW_DEF SET STATUS_CD = ?,ACCOUNT_ID = ?,ATTRIBUTENAME = ?,SORT_ORDER = ?,WIDTH = ?,STYLE_CLASS = ?,PRODUCT_VIEW_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE PRODUCT_VIEW_DEF_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setInt(i++,pData.getAccountId());
        pstmt.setString(i++,pData.getAttributename());
        pstmt.setInt(i++,pData.getSortOrder());
        pstmt.setInt(i++,pData.getWidth());
        pstmt.setString(i++,pData.getStyleClass());
        pstmt.setString(i++,pData.getProductViewCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getProductViewDefId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   ATTRIBUTENAME="+pData.getAttributename());
            log.debug("SQL:   SORT_ORDER="+pData.getSortOrder());
            log.debug("SQL:   WIDTH="+pData.getWidth());
            log.debug("SQL:   STYLE_CLASS="+pData.getStyleClass());
            log.debug("SQL:   PRODUCT_VIEW_CD="+pData.getProductViewCd());
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
     * Deletes a ProductViewDefData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProductViewDefId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProductViewDefId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PRODUCT_VIEW_DEF WHERE PRODUCT_VIEW_DEF_ID = " + pProductViewDefId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ProductViewDefData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PRODUCT_VIEW_DEF");
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
     * Inserts a ProductViewDefData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProductViewDefData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ProductViewDefData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PRODUCT_VIEW_DEF (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PRODUCT_VIEW_DEF_ID,STATUS_CD,ACCOUNT_ID,ATTRIBUTENAME,SORT_ORDER,WIDTH,STYLE_CLASS,PRODUCT_VIEW_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getProductViewDefId());
        pstmt.setString(2+4,pData.getStatusCd());
        pstmt.setInt(3+4,pData.getAccountId());
        pstmt.setString(4+4,pData.getAttributename());
        pstmt.setInt(5+4,pData.getSortOrder());
        pstmt.setInt(6+4,pData.getWidth());
        pstmt.setString(7+4,pData.getStyleClass());
        pstmt.setString(8+4,pData.getProductViewCd());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ProductViewDefData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProductViewDefData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ProductViewDefData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProductViewDefData insert(Connection pCon, ProductViewDefData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ProductViewDefData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProductViewDefData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProductViewDefData pData, boolean pLogFl)
        throws SQLException {
        ProductViewDefData oldData = null;
        if(pLogFl) {
          int id = pData.getProductViewDefId();
          try {
          oldData = ProductViewDefDataAccess.select(pCon,id);
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
     * Deletes a ProductViewDefData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProductViewDefId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProductViewDefId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PRODUCT_VIEW_DEF SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRODUCT_VIEW_DEF d WHERE PRODUCT_VIEW_DEF_ID = " + pProductViewDefId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pProductViewDefId);
        return n;
     }

    /**
     * Deletes ProductViewDefData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PRODUCT_VIEW_DEF SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRODUCT_VIEW_DEF d ");
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

