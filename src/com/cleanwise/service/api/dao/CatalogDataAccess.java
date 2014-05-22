
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CatalogDataAccess
 * Description:  This class is used to build access methods to the CLW_CATALOG table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
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
 * <code>CatalogDataAccess</code>
 */
public class CatalogDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CatalogDataAccess.class.getName());

    /** <code>CLW_CATALOG</code> table name */
	/* Primary key: CATALOG_ID */
	
    public static final String CLW_CATALOG = "CLW_CATALOG";
    
    /** <code>CATALOG_ID</code> CATALOG_ID column of table CLW_CATALOG */
    public static final String CATALOG_ID = "CATALOG_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_CATALOG */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>SHIPPING_MESSAGE</code> SHIPPING_MESSAGE column of table CLW_CATALOG */
    public static final String SHIPPING_MESSAGE = "SHIPPING_MESSAGE";
    /** <code>CATALOG_STATUS_CD</code> CATALOG_STATUS_CD column of table CLW_CATALOG */
    public static final String CATALOG_STATUS_CD = "CATALOG_STATUS_CD";
    /** <code>CATALOG_TYPE_CD</code> CATALOG_TYPE_CD column of table CLW_CATALOG */
    public static final String CATALOG_TYPE_CD = "CATALOG_TYPE_CD";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_CATALOG */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_CATALOG */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>RANK_WEIGHT</code> RANK_WEIGHT column of table CLW_CATALOG */
    public static final String RANK_WEIGHT = "RANK_WEIGHT";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CATALOG */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CATALOG */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CATALOG */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CATALOG */
    public static final String MOD_BY = "MOD_BY";
    /** <code>LOADER_FIELD</code> LOADER_FIELD column of table CLW_CATALOG */
    public static final String LOADER_FIELD = "LOADER_FIELD";

    /**
     * Constructor.
     */
    public CatalogDataAccess()
    {
    }

    /**
     * Gets a CatalogData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCatalogId The key requested.
     * @return new CatalogData()
     * @throws            SQLException
     */
    public static CatalogData select(Connection pCon, int pCatalogId)
        throws SQLException, DataNotFoundException {
        CatalogData x=null;
        String sql="SELECT CATALOG_ID,SHORT_DESC,SHIPPING_MESSAGE,CATALOG_STATUS_CD,CATALOG_TYPE_CD,EFF_DATE,EXP_DATE,RANK_WEIGHT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOADER_FIELD FROM CLW_CATALOG WHERE CATALOG_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCatalogId=" + pCatalogId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCatalogId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CatalogData.createValue();
            
            x.setCatalogId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setShippingMessage(rs.getString(3));
            x.setCatalogStatusCd(rs.getString(4));
            x.setCatalogTypeCd(rs.getString(5));
            x.setEffDate(rs.getDate(6));
            x.setExpDate(rs.getDate(7));
            x.setRankWeight(rs.getInt(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setLoaderField(rs.getString(13));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CATALOG_ID :" + pCatalogId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CatalogDataVector object that consists
     * of CatalogData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CatalogDataVector()
     * @throws            SQLException
     */
    public static CatalogDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CatalogData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CATALOG.CATALOG_ID,CLW_CATALOG.SHORT_DESC,CLW_CATALOG.SHIPPING_MESSAGE,CLW_CATALOG.CATALOG_STATUS_CD,CLW_CATALOG.CATALOG_TYPE_CD,CLW_CATALOG.EFF_DATE,CLW_CATALOG.EXP_DATE,CLW_CATALOG.RANK_WEIGHT,CLW_CATALOG.ADD_DATE,CLW_CATALOG.ADD_BY,CLW_CATALOG.MOD_DATE,CLW_CATALOG.MOD_BY,CLW_CATALOG.LOADER_FIELD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CatalogData Object.
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
    *@returns a populated CatalogData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CatalogData x = CatalogData.createValue();
         
         x.setCatalogId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setShippingMessage(rs.getString(3+offset));
         x.setCatalogStatusCd(rs.getString(4+offset));
         x.setCatalogTypeCd(rs.getString(5+offset));
         x.setEffDate(rs.getDate(6+offset));
         x.setExpDate(rs.getDate(7+offset));
         x.setRankWeight(rs.getInt(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         x.setLoaderField(rs.getString(13+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CatalogData Object represents.
    */
    public int getColumnCount(){
        return 13;
    }

    /**
     * Gets a CatalogDataVector object that consists
     * of CatalogData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CatalogDataVector()
     * @throws            SQLException
     */
    public static CatalogDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CATALOG_ID,SHORT_DESC,SHIPPING_MESSAGE,CATALOG_STATUS_CD,CATALOG_TYPE_CD,EFF_DATE,EXP_DATE,RANK_WEIGHT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOADER_FIELD FROM CLW_CATALOG");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CATALOG.CATALOG_ID,CLW_CATALOG.SHORT_DESC,CLW_CATALOG.SHIPPING_MESSAGE,CLW_CATALOG.CATALOG_STATUS_CD,CLW_CATALOG.CATALOG_TYPE_CD,CLW_CATALOG.EFF_DATE,CLW_CATALOG.EXP_DATE,CLW_CATALOG.RANK_WEIGHT,CLW_CATALOG.ADD_DATE,CLW_CATALOG.ADD_BY,CLW_CATALOG.MOD_DATE,CLW_CATALOG.MOD_BY,CLW_CATALOG.LOADER_FIELD FROM CLW_CATALOG");
                where = pCriteria.getSqlClause("CLW_CATALOG");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CATALOG.equals(otherTable)){
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
        CatalogDataVector v = new CatalogDataVector();
        while (rs.next()) {
            CatalogData x = CatalogData.createValue();
            
            x.setCatalogId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setShippingMessage(rs.getString(3));
            x.setCatalogStatusCd(rs.getString(4));
            x.setCatalogTypeCd(rs.getString(5));
            x.setEffDate(rs.getDate(6));
            x.setExpDate(rs.getDate(7));
            x.setRankWeight(rs.getInt(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setLoaderField(rs.getString(13));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a CatalogDataVector object that consists
     * of CatalogData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CatalogData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CatalogDataVector()
     * @throws            SQLException
     */
    public static CatalogDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CatalogDataVector v = new CatalogDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CATALOG_ID,SHORT_DESC,SHIPPING_MESSAGE,CATALOG_STATUS_CD,CATALOG_TYPE_CD,EFF_DATE,EXP_DATE,RANK_WEIGHT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOADER_FIELD FROM CLW_CATALOG WHERE CATALOG_ID IN (");

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
            CatalogData x=null;
            while (rs.next()) {
                // build the object
                x=CatalogData.createValue();
                
                x.setCatalogId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setShippingMessage(rs.getString(3));
                x.setCatalogStatusCd(rs.getString(4));
                x.setCatalogTypeCd(rs.getString(5));
                x.setEffDate(rs.getDate(6));
                x.setExpDate(rs.getDate(7));
                x.setRankWeight(rs.getInt(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setAddBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                x.setLoaderField(rs.getString(13));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a CatalogDataVector object of all
     * CatalogData objects in the database.
     * @param pCon An open database connection.
     * @return new CatalogDataVector()
     * @throws            SQLException
     */
    public static CatalogDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CATALOG_ID,SHORT_DESC,SHIPPING_MESSAGE,CATALOG_STATUS_CD,CATALOG_TYPE_CD,EFF_DATE,EXP_DATE,RANK_WEIGHT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOADER_FIELD FROM CLW_CATALOG";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CatalogDataVector v = new CatalogDataVector();
        CatalogData x = null;
        while (rs.next()) {
            // build the object
            x = CatalogData.createValue();
            
            x.setCatalogId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setShippingMessage(rs.getString(3));
            x.setCatalogStatusCd(rs.getString(4));
            x.setCatalogTypeCd(rs.getString(5));
            x.setEffDate(rs.getDate(6));
            x.setExpDate(rs.getDate(7));
            x.setRankWeight(rs.getInt(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setLoaderField(rs.getString(13));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * CatalogData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CATALOG_ID FROM CLW_CATALOG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CATALOG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CATALOG");
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
     * Inserts a CatalogData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CatalogData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CatalogData insert(Connection pCon, CatalogData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CATALOG_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CATALOG_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCatalogId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CATALOG (CATALOG_ID,SHORT_DESC,SHIPPING_MESSAGE,CATALOG_STATUS_CD,CATALOG_TYPE_CD,EFF_DATE,EXP_DATE,RANK_WEIGHT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOADER_FIELD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCatalogId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getShippingMessage());
        pstmt.setString(4,pData.getCatalogStatusCd());
        pstmt.setString(5,pData.getCatalogTypeCd());
        pstmt.setDate(6,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(7,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setInt(8,pData.getRankWeight());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());
        pstmt.setString(13,pData.getLoaderField());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   SHIPPING_MESSAGE="+pData.getShippingMessage());
            log.debug("SQL:   CATALOG_STATUS_CD="+pData.getCatalogStatusCd());
            log.debug("SQL:   CATALOG_TYPE_CD="+pData.getCatalogTypeCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   RANK_WEIGHT="+pData.getRankWeight());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   LOADER_FIELD="+pData.getLoaderField());
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
        pData.setCatalogId(0);
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
     * Updates a CatalogData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CatalogData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CATALOG SET SHORT_DESC = ?,SHIPPING_MESSAGE = ?,CATALOG_STATUS_CD = ?,CATALOG_TYPE_CD = ?,EFF_DATE = ?,EXP_DATE = ?,RANK_WEIGHT = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,LOADER_FIELD = ? WHERE CATALOG_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getShippingMessage());
        pstmt.setString(i++,pData.getCatalogStatusCd());
        pstmt.setString(i++,pData.getCatalogTypeCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setInt(i++,pData.getRankWeight());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getLoaderField());
        pstmt.setInt(i++,pData.getCatalogId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   SHIPPING_MESSAGE="+pData.getShippingMessage());
            log.debug("SQL:   CATALOG_STATUS_CD="+pData.getCatalogStatusCd());
            log.debug("SQL:   CATALOG_TYPE_CD="+pData.getCatalogTypeCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   RANK_WEIGHT="+pData.getRankWeight());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   LOADER_FIELD="+pData.getLoaderField());
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
     * Deletes a CatalogData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCatalogId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCatalogId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CATALOG WHERE CATALOG_ID = " + pCatalogId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CatalogData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CATALOG");
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
     * Inserts a CatalogData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CatalogData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CATALOG (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CATALOG_ID,SHORT_DESC,SHIPPING_MESSAGE,CATALOG_STATUS_CD,CATALOG_TYPE_CD,EFF_DATE,EXP_DATE,RANK_WEIGHT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOADER_FIELD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCatalogId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getShippingMessage());
        pstmt.setString(4+4,pData.getCatalogStatusCd());
        pstmt.setString(5+4,pData.getCatalogTypeCd());
        pstmt.setDate(6+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(7+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setInt(8+4,pData.getRankWeight());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());
        pstmt.setString(13+4,pData.getLoaderField());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CatalogData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CatalogData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CatalogData insert(Connection pCon, CatalogData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CatalogData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CatalogData pData, boolean pLogFl)
        throws SQLException {
        CatalogData oldData = null;
        if(pLogFl) {
          int id = pData.getCatalogId();
          try {
          oldData = CatalogDataAccess.select(pCon,id);
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
     * Deletes a CatalogData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCatalogId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCatalogId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CATALOG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CATALOG d WHERE CATALOG_ID = " + pCatalogId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCatalogId);
        return n;
     }

    /**
     * Deletes CatalogData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CATALOG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CATALOG d ");
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

