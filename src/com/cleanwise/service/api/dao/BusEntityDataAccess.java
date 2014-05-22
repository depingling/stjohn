
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        BusEntityDataAccess
 * Description:  This class is used to build access methods to the CLW_BUS_ENTITY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
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
 * <code>BusEntityDataAccess</code>
 */
public class BusEntityDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(BusEntityDataAccess.class.getName());

    /** <code>CLW_BUS_ENTITY</code> table name */
	/* Primary key: BUS_ENTITY_ID */
	
    public static final String CLW_BUS_ENTITY = "CLW_BUS_ENTITY";
    
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_BUS_ENTITY */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_BUS_ENTITY */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_BUS_ENTITY */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_BUS_ENTITY */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_BUS_ENTITY */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>BUS_ENTITY_TYPE_CD</code> BUS_ENTITY_TYPE_CD column of table CLW_BUS_ENTITY */
    public static final String BUS_ENTITY_TYPE_CD = "BUS_ENTITY_TYPE_CD";
    /** <code>BUS_ENTITY_STATUS_CD</code> BUS_ENTITY_STATUS_CD column of table CLW_BUS_ENTITY */
    public static final String BUS_ENTITY_STATUS_CD = "BUS_ENTITY_STATUS_CD";
    /** <code>WORKFLOW_ROLE_CD</code> WORKFLOW_ROLE_CD column of table CLW_BUS_ENTITY */
    public static final String WORKFLOW_ROLE_CD = "WORKFLOW_ROLE_CD";
    /** <code>LOCALE_CD</code> LOCALE_CD column of table CLW_BUS_ENTITY */
    public static final String LOCALE_CD = "LOCALE_CD";
    /** <code>ERP_NUM</code> ERP_NUM column of table CLW_BUS_ENTITY */
    public static final String ERP_NUM = "ERP_NUM";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_BUS_ENTITY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_BUS_ENTITY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_BUS_ENTITY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_BUS_ENTITY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>TIME_ZONE_CD</code> TIME_ZONE_CD column of table CLW_BUS_ENTITY */
    public static final String TIME_ZONE_CD = "TIME_ZONE_CD";

    /**
     * Constructor.
     */
    public BusEntityDataAccess()
    {
    }

    /**
     * Gets a BusEntityData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pBusEntityId The key requested.
     * @return new BusEntityData()
     * @throws            SQLException
     */
    public static BusEntityData select(Connection pCon, int pBusEntityId)
        throws SQLException, DataNotFoundException {
        BusEntityData x=null;
        String sql="SELECT BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,EFF_DATE,EXP_DATE,BUS_ENTITY_TYPE_CD,BUS_ENTITY_STATUS_CD,WORKFLOW_ROLE_CD,LOCALE_CD,ERP_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,TIME_ZONE_CD FROM CLW_BUS_ENTITY WHERE BUS_ENTITY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pBusEntityId=" + pBusEntityId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pBusEntityId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=BusEntityData.createValue();
            
            x.setBusEntityId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setLongDesc(rs.getString(3));
            x.setEffDate(rs.getDate(4));
            x.setExpDate(rs.getDate(5));
            x.setBusEntityTypeCd(rs.getString(6));
            x.setBusEntityStatusCd(rs.getString(7));
            x.setWorkflowRoleCd(rs.getString(8));
            x.setLocaleCd(rs.getString(9));
            x.setErpNum(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setTimeZoneCd(rs.getString(15));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("BUS_ENTITY_ID :" + pBusEntityId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a BusEntityDataVector object that consists
     * of BusEntityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new BusEntityDataVector()
     * @throws            SQLException
     */
    public static BusEntityDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a BusEntityData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_BUS_ENTITY.BUS_ENTITY_ID,CLW_BUS_ENTITY.SHORT_DESC,CLW_BUS_ENTITY.LONG_DESC,CLW_BUS_ENTITY.EFF_DATE,CLW_BUS_ENTITY.EXP_DATE,CLW_BUS_ENTITY.BUS_ENTITY_TYPE_CD,CLW_BUS_ENTITY.BUS_ENTITY_STATUS_CD,CLW_BUS_ENTITY.WORKFLOW_ROLE_CD,CLW_BUS_ENTITY.LOCALE_CD,CLW_BUS_ENTITY.ERP_NUM,CLW_BUS_ENTITY.ADD_DATE,CLW_BUS_ENTITY.ADD_BY,CLW_BUS_ENTITY.MOD_DATE,CLW_BUS_ENTITY.MOD_BY,CLW_BUS_ENTITY.TIME_ZONE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated BusEntityData Object.
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
    *@returns a populated BusEntityData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         BusEntityData x = BusEntityData.createValue();
         
         x.setBusEntityId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setLongDesc(rs.getString(3+offset));
         x.setEffDate(rs.getDate(4+offset));
         x.setExpDate(rs.getDate(5+offset));
         x.setBusEntityTypeCd(rs.getString(6+offset));
         x.setBusEntityStatusCd(rs.getString(7+offset));
         x.setWorkflowRoleCd(rs.getString(8+offset));
         x.setLocaleCd(rs.getString(9+offset));
         x.setErpNum(rs.getString(10+offset));
         x.setAddDate(rs.getTimestamp(11+offset));
         x.setAddBy(rs.getString(12+offset));
         x.setModDate(rs.getTimestamp(13+offset));
         x.setModBy(rs.getString(14+offset));
         x.setTimeZoneCd(rs.getString(15+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the BusEntityData Object represents.
    */
    public int getColumnCount(){
        return 15;
    }

    /**
     * Gets a BusEntityDataVector object that consists
     * of BusEntityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new BusEntityDataVector()
     * @throws            SQLException
     */
    public static BusEntityDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,EFF_DATE,EXP_DATE,BUS_ENTITY_TYPE_CD,BUS_ENTITY_STATUS_CD,WORKFLOW_ROLE_CD,LOCALE_CD,ERP_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,TIME_ZONE_CD FROM CLW_BUS_ENTITY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_BUS_ENTITY.BUS_ENTITY_ID,CLW_BUS_ENTITY.SHORT_DESC,CLW_BUS_ENTITY.LONG_DESC,CLW_BUS_ENTITY.EFF_DATE,CLW_BUS_ENTITY.EXP_DATE,CLW_BUS_ENTITY.BUS_ENTITY_TYPE_CD,CLW_BUS_ENTITY.BUS_ENTITY_STATUS_CD,CLW_BUS_ENTITY.WORKFLOW_ROLE_CD,CLW_BUS_ENTITY.LOCALE_CD,CLW_BUS_ENTITY.ERP_NUM,CLW_BUS_ENTITY.ADD_DATE,CLW_BUS_ENTITY.ADD_BY,CLW_BUS_ENTITY.MOD_DATE,CLW_BUS_ENTITY.MOD_BY,CLW_BUS_ENTITY.TIME_ZONE_CD FROM CLW_BUS_ENTITY");
                where = pCriteria.getSqlClause("CLW_BUS_ENTITY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_BUS_ENTITY.equals(otherTable)){
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
        BusEntityDataVector v = new BusEntityDataVector();
        while (rs.next()) {
            BusEntityData x = BusEntityData.createValue();
            
            x.setBusEntityId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setLongDesc(rs.getString(3));
            x.setEffDate(rs.getDate(4));
            x.setExpDate(rs.getDate(5));
            x.setBusEntityTypeCd(rs.getString(6));
            x.setBusEntityStatusCd(rs.getString(7));
            x.setWorkflowRoleCd(rs.getString(8));
            x.setLocaleCd(rs.getString(9));
            x.setErpNum(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setTimeZoneCd(rs.getString(15));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a BusEntityDataVector object that consists
     * of BusEntityData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for BusEntityData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new BusEntityDataVector()
     * @throws            SQLException
     */
    public static BusEntityDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        BusEntityDataVector v = new BusEntityDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,EFF_DATE,EXP_DATE,BUS_ENTITY_TYPE_CD,BUS_ENTITY_STATUS_CD,WORKFLOW_ROLE_CD,LOCALE_CD,ERP_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,TIME_ZONE_CD FROM CLW_BUS_ENTITY WHERE BUS_ENTITY_ID IN (");

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
            BusEntityData x=null;
            while (rs.next()) {
                // build the object
                x=BusEntityData.createValue();
                
                x.setBusEntityId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setLongDesc(rs.getString(3));
                x.setEffDate(rs.getDate(4));
                x.setExpDate(rs.getDate(5));
                x.setBusEntityTypeCd(rs.getString(6));
                x.setBusEntityStatusCd(rs.getString(7));
                x.setWorkflowRoleCd(rs.getString(8));
                x.setLocaleCd(rs.getString(9));
                x.setErpNum(rs.getString(10));
                x.setAddDate(rs.getTimestamp(11));
                x.setAddBy(rs.getString(12));
                x.setModDate(rs.getTimestamp(13));
                x.setModBy(rs.getString(14));
                x.setTimeZoneCd(rs.getString(15));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a BusEntityDataVector object of all
     * BusEntityData objects in the database.
     * @param pCon An open database connection.
     * @return new BusEntityDataVector()
     * @throws            SQLException
     */
    public static BusEntityDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,EFF_DATE,EXP_DATE,BUS_ENTITY_TYPE_CD,BUS_ENTITY_STATUS_CD,WORKFLOW_ROLE_CD,LOCALE_CD,ERP_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,TIME_ZONE_CD FROM CLW_BUS_ENTITY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        BusEntityDataVector v = new BusEntityDataVector();
        BusEntityData x = null;
        while (rs.next()) {
            // build the object
            x = BusEntityData.createValue();
            
            x.setBusEntityId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setLongDesc(rs.getString(3));
            x.setEffDate(rs.getDate(4));
            x.setExpDate(rs.getDate(5));
            x.setBusEntityTypeCd(rs.getString(6));
            x.setBusEntityStatusCd(rs.getString(7));
            x.setWorkflowRoleCd(rs.getString(8));
            x.setLocaleCd(rs.getString(9));
            x.setErpNum(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setTimeZoneCd(rs.getString(15));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * BusEntityData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT BUS_ENTITY_ID FROM CLW_BUS_ENTITY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BUS_ENTITY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BUS_ENTITY");
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
     * Inserts a BusEntityData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new BusEntityData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BusEntityData insert(Connection pCon, BusEntityData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_BUS_ENTITY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_BUS_ENTITY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setBusEntityId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_BUS_ENTITY (BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,EFF_DATE,EXP_DATE,BUS_ENTITY_TYPE_CD,BUS_ENTITY_STATUS_CD,WORKFLOW_ROLE_CD,LOCALE_CD,ERP_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,TIME_ZONE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getBusEntityId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getLongDesc());
        pstmt.setDate(4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(5,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(6,pData.getBusEntityTypeCd());
        pstmt.setString(7,pData.getBusEntityStatusCd());
        pstmt.setString(8,pData.getWorkflowRoleCd());
        pstmt.setString(9,pData.getLocaleCd());
        pstmt.setString(10,pData.getErpNum());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12,pData.getAddBy());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14,pData.getModBy());
        pstmt.setString(15,pData.getTimeZoneCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   BUS_ENTITY_TYPE_CD="+pData.getBusEntityTypeCd());
            log.debug("SQL:   BUS_ENTITY_STATUS_CD="+pData.getBusEntityStatusCd());
            log.debug("SQL:   WORKFLOW_ROLE_CD="+pData.getWorkflowRoleCd());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   ERP_NUM="+pData.getErpNum());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   TIME_ZONE_CD="+pData.getTimeZoneCd());
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
        pData.setBusEntityId(0);
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
     * Updates a BusEntityData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BusEntityData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_BUS_ENTITY SET SHORT_DESC = ?,LONG_DESC = ?,EFF_DATE = ?,EXP_DATE = ?,BUS_ENTITY_TYPE_CD = ?,BUS_ENTITY_STATUS_CD = ?,WORKFLOW_ROLE_CD = ?,LOCALE_CD = ?,ERP_NUM = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,TIME_ZONE_CD = ? WHERE BUS_ENTITY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(i++,pData.getBusEntityTypeCd());
        pstmt.setString(i++,pData.getBusEntityStatusCd());
        pstmt.setString(i++,pData.getWorkflowRoleCd());
        pstmt.setString(i++,pData.getLocaleCd());
        pstmt.setString(i++,pData.getErpNum());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getTimeZoneCd());
        pstmt.setInt(i++,pData.getBusEntityId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   BUS_ENTITY_TYPE_CD="+pData.getBusEntityTypeCd());
            log.debug("SQL:   BUS_ENTITY_STATUS_CD="+pData.getBusEntityStatusCd());
            log.debug("SQL:   WORKFLOW_ROLE_CD="+pData.getWorkflowRoleCd());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   ERP_NUM="+pData.getErpNum());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   TIME_ZONE_CD="+pData.getTimeZoneCd());
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
     * Deletes a BusEntityData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBusEntityId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBusEntityId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_BUS_ENTITY WHERE BUS_ENTITY_ID = " + pBusEntityId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes BusEntityData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_BUS_ENTITY");
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
     * Inserts a BusEntityData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, BusEntityData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_BUS_ENTITY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,EFF_DATE,EXP_DATE,BUS_ENTITY_TYPE_CD,BUS_ENTITY_STATUS_CD,WORKFLOW_ROLE_CD,LOCALE_CD,ERP_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,TIME_ZONE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getBusEntityId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getLongDesc());
        pstmt.setDate(4+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(5+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(6+4,pData.getBusEntityTypeCd());
        pstmt.setString(7+4,pData.getBusEntityStatusCd());
        pstmt.setString(8+4,pData.getWorkflowRoleCd());
        pstmt.setString(9+4,pData.getLocaleCd());
        pstmt.setString(10+4,pData.getErpNum());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12+4,pData.getAddBy());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14+4,pData.getModBy());
        pstmt.setString(15+4,pData.getTimeZoneCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a BusEntityData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new BusEntityData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BusEntityData insert(Connection pCon, BusEntityData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a BusEntityData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BusEntityData pData, boolean pLogFl)
        throws SQLException {
        BusEntityData oldData = null;
        if(pLogFl) {
          int id = pData.getBusEntityId();
          try {
          oldData = BusEntityDataAccess.select(pCon,id);
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
     * Deletes a BusEntityData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBusEntityId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBusEntityId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_BUS_ENTITY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BUS_ENTITY d WHERE BUS_ENTITY_ID = " + pBusEntityId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pBusEntityId);
        return n;
     }

    /**
     * Deletes BusEntityData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_BUS_ENTITY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BUS_ENTITY d ");
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

}

