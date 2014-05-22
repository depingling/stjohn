
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        AssetDataAccess
 * Description:  This class is used to build access methods to the CLW_ASSET table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.AssetData;
import com.cleanwise.service.api.value.AssetDataVector;
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
 * <code>AssetDataAccess</code>
 */
public class AssetDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(AssetDataAccess.class.getName());

    /** <code>CLW_ASSET</code> table name */
	/* Primary key: ASSET_ID */
	
    public static final String CLW_ASSET = "CLW_ASSET";
    
    /** <code>ASSET_ID</code> ASSET_ID column of table CLW_ASSET */
    public static final String ASSET_ID = "ASSET_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_ASSET */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>ASSET_TYPE_CD</code> ASSET_TYPE_CD column of table CLW_ASSET */
    public static final String ASSET_TYPE_CD = "ASSET_TYPE_CD";
    /** <code>PARENT_ID</code> PARENT_ID column of table CLW_ASSET */
    public static final String PARENT_ID = "PARENT_ID";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_ASSET */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>SERIAL_NUM</code> SERIAL_NUM column of table CLW_ASSET */
    public static final String SERIAL_NUM = "SERIAL_NUM";
    /** <code>ASSET_NUM</code> ASSET_NUM column of table CLW_ASSET */
    public static final String ASSET_NUM = "ASSET_NUM";
    /** <code>MANUF_ID</code> MANUF_ID column of table CLW_ASSET */
    public static final String MANUF_ID = "MANUF_ID";
    /** <code>MANUF_NAME</code> MANUF_NAME column of table CLW_ASSET */
    public static final String MANUF_NAME = "MANUF_NAME";
    /** <code>MANUF_SKU</code> MANUF_SKU column of table CLW_ASSET */
    public static final String MANUF_SKU = "MANUF_SKU";
    /** <code>MANUF_TYPE_CD</code> MANUF_TYPE_CD column of table CLW_ASSET */
    public static final String MANUF_TYPE_CD = "MANUF_TYPE_CD";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ASSET */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ASSET */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ASSET */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ASSET */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MODEL_NUMBER</code> MODEL_NUMBER column of table CLW_ASSET */
    public static final String MODEL_NUMBER = "MODEL_NUMBER";
    /** <code>MASTER_ASSET_ID</code> MASTER_ASSET_ID column of table CLW_ASSET */
    public static final String MASTER_ASSET_ID = "MASTER_ASSET_ID";

    /**
     * Constructor.
     */
    public AssetDataAccess()
    {
    }

    /**
     * Gets a AssetData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pAssetId The key requested.
     * @return new AssetData()
     * @throws            SQLException
     */
    public static AssetData select(Connection pCon, int pAssetId)
        throws SQLException, DataNotFoundException {
        AssetData x=null;
        String sql="SELECT ASSET_ID,SHORT_DESC,ASSET_TYPE_CD,PARENT_ID,STATUS_CD,SERIAL_NUM,ASSET_NUM,MANUF_ID,MANUF_NAME,MANUF_SKU,MANUF_TYPE_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,MODEL_NUMBER,MASTER_ASSET_ID FROM CLW_ASSET WHERE ASSET_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pAssetId=" + pAssetId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pAssetId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=AssetData.createValue();
            
            x.setAssetId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setAssetTypeCd(rs.getString(3));
            x.setParentId(rs.getInt(4));
            x.setStatusCd(rs.getString(5));
            x.setSerialNum(rs.getString(6));
            x.setAssetNum(rs.getString(7));
            x.setManufId(rs.getInt(8));
            x.setManufName(rs.getString(9));
            x.setManufSku(rs.getString(10));
            x.setManufTypeCd(rs.getString(11));
            x.setAddBy(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModelNumber(rs.getString(16));
            x.setMasterAssetId(rs.getInt(17));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ASSET_ID :" + pAssetId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a AssetDataVector object that consists
     * of AssetData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new AssetDataVector()
     * @throws            SQLException
     */
    public static AssetDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a AssetData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ASSET.ASSET_ID,CLW_ASSET.SHORT_DESC,CLW_ASSET.ASSET_TYPE_CD,CLW_ASSET.PARENT_ID,CLW_ASSET.STATUS_CD,CLW_ASSET.SERIAL_NUM,CLW_ASSET.ASSET_NUM,CLW_ASSET.MANUF_ID,CLW_ASSET.MANUF_NAME,CLW_ASSET.MANUF_SKU,CLW_ASSET.MANUF_TYPE_CD,CLW_ASSET.ADD_BY,CLW_ASSET.ADD_DATE,CLW_ASSET.MOD_BY,CLW_ASSET.MOD_DATE,CLW_ASSET.MODEL_NUMBER,CLW_ASSET.MASTER_ASSET_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated AssetData Object.
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
    *@returns a populated AssetData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         AssetData x = AssetData.createValue();
         
         x.setAssetId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setAssetTypeCd(rs.getString(3+offset));
         x.setParentId(rs.getInt(4+offset));
         x.setStatusCd(rs.getString(5+offset));
         x.setSerialNum(rs.getString(6+offset));
         x.setAssetNum(rs.getString(7+offset));
         x.setManufId(rs.getInt(8+offset));
         x.setManufName(rs.getString(9+offset));
         x.setManufSku(rs.getString(10+offset));
         x.setManufTypeCd(rs.getString(11+offset));
         x.setAddBy(rs.getString(12+offset));
         x.setAddDate(rs.getTimestamp(13+offset));
         x.setModBy(rs.getString(14+offset));
         x.setModDate(rs.getTimestamp(15+offset));
         x.setModelNumber(rs.getString(16+offset));
         x.setMasterAssetId(rs.getInt(17+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the AssetData Object represents.
    */
    public int getColumnCount(){
        return 17;
    }

    /**
     * Gets a AssetDataVector object that consists
     * of AssetData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new AssetDataVector()
     * @throws            SQLException
     */
    public static AssetDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ASSET_ID,SHORT_DESC,ASSET_TYPE_CD,PARENT_ID,STATUS_CD,SERIAL_NUM,ASSET_NUM,MANUF_ID,MANUF_NAME,MANUF_SKU,MANUF_TYPE_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,MODEL_NUMBER,MASTER_ASSET_ID FROM CLW_ASSET");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ASSET.ASSET_ID,CLW_ASSET.SHORT_DESC,CLW_ASSET.ASSET_TYPE_CD,CLW_ASSET.PARENT_ID,CLW_ASSET.STATUS_CD,CLW_ASSET.SERIAL_NUM,CLW_ASSET.ASSET_NUM,CLW_ASSET.MANUF_ID,CLW_ASSET.MANUF_NAME,CLW_ASSET.MANUF_SKU,CLW_ASSET.MANUF_TYPE_CD,CLW_ASSET.ADD_BY,CLW_ASSET.ADD_DATE,CLW_ASSET.MOD_BY,CLW_ASSET.MOD_DATE,CLW_ASSET.MODEL_NUMBER,CLW_ASSET.MASTER_ASSET_ID FROM CLW_ASSET");
                where = pCriteria.getSqlClause("CLW_ASSET");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ASSET.equals(otherTable)){
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
        AssetDataVector v = new AssetDataVector();
        while (rs.next()) {
            AssetData x = AssetData.createValue();
            
            x.setAssetId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setAssetTypeCd(rs.getString(3));
            x.setParentId(rs.getInt(4));
            x.setStatusCd(rs.getString(5));
            x.setSerialNum(rs.getString(6));
            x.setAssetNum(rs.getString(7));
            x.setManufId(rs.getInt(8));
            x.setManufName(rs.getString(9));
            x.setManufSku(rs.getString(10));
            x.setManufTypeCd(rs.getString(11));
            x.setAddBy(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModelNumber(rs.getString(16));
            x.setMasterAssetId(rs.getInt(17));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a AssetDataVector object that consists
     * of AssetData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for AssetData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new AssetDataVector()
     * @throws            SQLException
     */
    public static AssetDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        AssetDataVector v = new AssetDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ASSET_ID,SHORT_DESC,ASSET_TYPE_CD,PARENT_ID,STATUS_CD,SERIAL_NUM,ASSET_NUM,MANUF_ID,MANUF_NAME,MANUF_SKU,MANUF_TYPE_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,MODEL_NUMBER,MASTER_ASSET_ID FROM CLW_ASSET WHERE ASSET_ID IN (");

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
            AssetData x=null;
            while (rs.next()) {
                // build the object
                x=AssetData.createValue();
                
                x.setAssetId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setAssetTypeCd(rs.getString(3));
                x.setParentId(rs.getInt(4));
                x.setStatusCd(rs.getString(5));
                x.setSerialNum(rs.getString(6));
                x.setAssetNum(rs.getString(7));
                x.setManufId(rs.getInt(8));
                x.setManufName(rs.getString(9));
                x.setManufSku(rs.getString(10));
                x.setManufTypeCd(rs.getString(11));
                x.setAddBy(rs.getString(12));
                x.setAddDate(rs.getTimestamp(13));
                x.setModBy(rs.getString(14));
                x.setModDate(rs.getTimestamp(15));
                x.setModelNumber(rs.getString(16));
                x.setMasterAssetId(rs.getInt(17));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a AssetDataVector object of all
     * AssetData objects in the database.
     * @param pCon An open database connection.
     * @return new AssetDataVector()
     * @throws            SQLException
     */
    public static AssetDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ASSET_ID,SHORT_DESC,ASSET_TYPE_CD,PARENT_ID,STATUS_CD,SERIAL_NUM,ASSET_NUM,MANUF_ID,MANUF_NAME,MANUF_SKU,MANUF_TYPE_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,MODEL_NUMBER,MASTER_ASSET_ID FROM CLW_ASSET";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        AssetDataVector v = new AssetDataVector();
        AssetData x = null;
        while (rs.next()) {
            // build the object
            x = AssetData.createValue();
            
            x.setAssetId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setAssetTypeCd(rs.getString(3));
            x.setParentId(rs.getInt(4));
            x.setStatusCd(rs.getString(5));
            x.setSerialNum(rs.getString(6));
            x.setAssetNum(rs.getString(7));
            x.setManufId(rs.getInt(8));
            x.setManufName(rs.getString(9));
            x.setManufSku(rs.getString(10));
            x.setManufTypeCd(rs.getString(11));
            x.setAddBy(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModelNumber(rs.getString(16));
            x.setMasterAssetId(rs.getInt(17));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * AssetData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ASSET_ID FROM CLW_ASSET");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ASSET");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ASSET");
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
     * Inserts a AssetData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new AssetData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AssetData insert(Connection pCon, AssetData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ASSET_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ASSET_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setAssetId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ASSET (ASSET_ID,SHORT_DESC,ASSET_TYPE_CD,PARENT_ID,STATUS_CD,SERIAL_NUM,ASSET_NUM,MANUF_ID,MANUF_NAME,MANUF_SKU,MANUF_TYPE_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,MODEL_NUMBER,MASTER_ASSET_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getAssetId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getAssetTypeCd());
        pstmt.setInt(4,pData.getParentId());
        pstmt.setString(5,pData.getStatusCd());
        pstmt.setString(6,pData.getSerialNum());
        pstmt.setString(7,pData.getAssetNum());
        pstmt.setInt(8,pData.getManufId());
        pstmt.setString(9,pData.getManufName());
        pstmt.setString(10,pData.getManufSku());
        pstmt.setString(11,pData.getManufTypeCd());
        pstmt.setString(12,pData.getAddBy());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14,pData.getModBy());
        pstmt.setTimestamp(15,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16,pData.getModelNumber());
        pstmt.setInt(17,pData.getMasterAssetId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ASSET_ID="+pData.getAssetId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   ASSET_TYPE_CD="+pData.getAssetTypeCd());
            log.debug("SQL:   PARENT_ID="+pData.getParentId());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   SERIAL_NUM="+pData.getSerialNum());
            log.debug("SQL:   ASSET_NUM="+pData.getAssetNum());
            log.debug("SQL:   MANUF_ID="+pData.getManufId());
            log.debug("SQL:   MANUF_NAME="+pData.getManufName());
            log.debug("SQL:   MANUF_SKU="+pData.getManufSku());
            log.debug("SQL:   MANUF_TYPE_CD="+pData.getManufTypeCd());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MODEL_NUMBER="+pData.getModelNumber());
            log.debug("SQL:   MASTER_ASSET_ID="+pData.getMasterAssetId());
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
        pData.setAssetId(0);
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
     * Updates a AssetData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AssetData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ASSET SET SHORT_DESC = ?,ASSET_TYPE_CD = ?,PARENT_ID = ?,STATUS_CD = ?,SERIAL_NUM = ?,ASSET_NUM = ?,MANUF_ID = ?,MANUF_NAME = ?,MANUF_SKU = ?,MANUF_TYPE_CD = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ?,MODEL_NUMBER = ?,MASTER_ASSET_ID = ? WHERE ASSET_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getAssetTypeCd());
        pstmt.setInt(i++,pData.getParentId());
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setString(i++,pData.getSerialNum());
        pstmt.setString(i++,pData.getAssetNum());
        pstmt.setInt(i++,pData.getManufId());
        pstmt.setString(i++,pData.getManufName());
        pstmt.setString(i++,pData.getManufSku());
        pstmt.setString(i++,pData.getManufTypeCd());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModelNumber());
        pstmt.setInt(i++,pData.getMasterAssetId());
        pstmt.setInt(i++,pData.getAssetId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   ASSET_TYPE_CD="+pData.getAssetTypeCd());
            log.debug("SQL:   PARENT_ID="+pData.getParentId());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   SERIAL_NUM="+pData.getSerialNum());
            log.debug("SQL:   ASSET_NUM="+pData.getAssetNum());
            log.debug("SQL:   MANUF_ID="+pData.getManufId());
            log.debug("SQL:   MANUF_NAME="+pData.getManufName());
            log.debug("SQL:   MANUF_SKU="+pData.getManufSku());
            log.debug("SQL:   MANUF_TYPE_CD="+pData.getManufTypeCd());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MODEL_NUMBER="+pData.getModelNumber());
            log.debug("SQL:   MASTER_ASSET_ID="+pData.getMasterAssetId());
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
     * Deletes a AssetData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAssetId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAssetId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ASSET WHERE ASSET_ID = " + pAssetId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes AssetData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ASSET");
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
     * Inserts a AssetData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, AssetData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ASSET (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ASSET_ID,SHORT_DESC,ASSET_TYPE_CD,PARENT_ID,STATUS_CD,SERIAL_NUM,ASSET_NUM,MANUF_ID,MANUF_NAME,MANUF_SKU,MANUF_TYPE_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,MODEL_NUMBER,MASTER_ASSET_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getAssetId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getAssetTypeCd());
        pstmt.setInt(4+4,pData.getParentId());
        pstmt.setString(5+4,pData.getStatusCd());
        pstmt.setString(6+4,pData.getSerialNum());
        pstmt.setString(7+4,pData.getAssetNum());
        pstmt.setInt(8+4,pData.getManufId());
        pstmt.setString(9+4,pData.getManufName());
        pstmt.setString(10+4,pData.getManufSku());
        pstmt.setString(11+4,pData.getManufTypeCd());
        pstmt.setString(12+4,pData.getAddBy());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14+4,pData.getModBy());
        pstmt.setTimestamp(15+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16+4,pData.getModelNumber());
        pstmt.setInt(17+4,pData.getMasterAssetId());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a AssetData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new AssetData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AssetData insert(Connection pCon, AssetData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a AssetData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AssetData pData, boolean pLogFl)
        throws SQLException {
        AssetData oldData = null;
        if(pLogFl) {
          int id = pData.getAssetId();
          try {
          oldData = AssetDataAccess.select(pCon,id);
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
     * Deletes a AssetData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAssetId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAssetId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ASSET SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ASSET d WHERE ASSET_ID = " + pAssetId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pAssetId);
        return n;
     }

    /**
     * Deletes AssetData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ASSET SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ASSET d ");
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

