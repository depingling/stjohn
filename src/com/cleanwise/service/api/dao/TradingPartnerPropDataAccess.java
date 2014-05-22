  
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        TradingPartnerPropDataAccess
 * Description:  This class is used to build access methods to the CLW_TRADING_PARTNER_PROP table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.TradingPartnerPropData;
import com.cleanwise.service.api.value.TradingPartnerPropDataVector;
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
 * <code>TradingPartnerPropDataAccess</code>
 */
public class TradingPartnerPropDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(TradingPartnerPropDataAccess.class.getName());

    /** <code>CLW_TRADING_PARTNER_PROP</code> table name */
	/* Primary key: TRADING_PARTNER_PROPERTY_ID */
	
    public static final String CLW_TRADING_PARTNER_PROP = "CLW_TRADING_PARTNER_PROP";
    
    /** <code>TRADING_PARTNER_PROPERTY_ID</code> TRADING_PARTNER_PROPERTY_ID column of table CLW_TRADING_PARTNER_PROP */
    public static final String TRADING_PARTNER_PROPERTY_ID = "TRADING_PARTNER_PROPERTY_ID";
    /** <code>TRADING_PARTNER_ID</code> TRADING_PARTNER_ID column of table CLW_TRADING_PARTNER_PROP */
    public static final String TRADING_PARTNER_ID = "TRADING_PARTNER_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_TRADING_PARTNER_PROP */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_TRADING_PARTNER_PROP */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>PROPERTY_STATUS_CD</code> PROPERTY_STATUS_CD column of table CLW_TRADING_PARTNER_PROP */
    public static final String PROPERTY_STATUS_CD = "PROPERTY_STATUS_CD";
    /** <code>PROPERTY_TYPE_CD</code> PROPERTY_TYPE_CD column of table CLW_TRADING_PARTNER_PROP */
    public static final String PROPERTY_TYPE_CD = "PROPERTY_TYPE_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_TRADING_PARTNER_PROP */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_TRADING_PARTNER_PROP */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_TRADING_PARTNER_PROP */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_TRADING_PARTNER_PROP */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public TradingPartnerPropDataAccess()
    {
    }

    /**
     * Gets a TradingPartnerPropData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pTradingPartnerPropertyId The key requested.
     * @return new TradingPartnerPropData()
     * @throws            SQLException
     */
    public static TradingPartnerPropData select(Connection pCon, int pTradingPartnerPropId)
        throws SQLException, DataNotFoundException {
        TradingPartnerPropData x=null;
        String sql="SELECT TRADING_PARTNER_PROPERTY_ID,TRADING_PARTNER_ID,SHORT_DESC,CLW_VALUE,PROPERTY_STATUS_CD,PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_TRADING_PARTNER_PROP WHERE TRADING_PARTNER_PROPERTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pTradingPartnerPropId=" + pTradingPartnerPropId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pTradingPartnerPropId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=TradingPartnerPropData.createValue();
            
            x.setTradingPartnerPropertyId(rs.getInt(1));
            x.setTradingPartnerId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setPropertyStatusCd(rs.getString(5));
            x.setPropertyTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("TRADING_PARTNER_PROPERTY_ID :" + pTradingPartnerPropId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a TradingPartnerPropDataVector object that consists
     * of TradingPartnerPropData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new TradingPartnerPropDataVector()
     * @throws            SQLException
     */
    public static TradingPartnerPropDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a TradingPartnerPropData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_TRADING_PARTNER_PROP.TRADING_PARTNER_PROPERTY_ID,CLW_TRADING_PARTNER_PROP.TRADING_PARTNER_ID,CLW_TRADING_PARTNER_PROP.SHORT_DESC,CLW_TRADING_PARTNER_PROP.CLW_VALUE,CLW_TRADING_PARTNER_PROP.PROPERTY_STATUS_CD,CLW_TRADING_PARTNER_PROP.PROPERTY_TYPE_CD,CLW_TRADING_PARTNER_PROP.ADD_DATE,CLW_TRADING_PARTNER_PROP.ADD_BY,CLW_TRADING_PARTNER_PROP.MOD_DATE,CLW_TRADING_PARTNER_PROP.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated TradingPartnerPropData Object.
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
    *@returns a populated TradingPartnerPropData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         TradingPartnerPropData x = TradingPartnerPropData.createValue();
         
         x.setTradingPartnerPropertyId(rs.getInt(1+offset));
         x.setTradingPartnerId(rs.getInt(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setValue(rs.getString(4+offset));
         x.setPropertyStatusCd(rs.getString(5+offset));
         x.setPropertyTypeCd(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the TradingPartnerPropData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a TradingPartnerPropDataVector object that consists
     * of TradingPartnerPropData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new TradingPartnerPropDataVector()
     * @throws            SQLException
     */
    public static TradingPartnerPropDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT TRADING_PARTNER_PROPERTY_ID,TRADING_PARTNER_ID,SHORT_DESC,CLW_VALUE,PROPERTY_STATUS_CD,PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_TRADING_PARTNER_PROP");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_TRADING_PARTNER_PROP.TRADING_PARTNER_PROPERTY_ID,CLW_TRADING_PARTNER_PROP.TRADING_PARTNER_ID,CLW_TRADING_PARTNER_PROP.SHORT_DESC,CLW_TRADING_PARTNER_PROP.CLW_VALUE,CLW_TRADING_PARTNER_PROP.PROPERTY_STATUS_CD,CLW_TRADING_PARTNER_PROP.PROPERTY_TYPE_CD,CLW_TRADING_PARTNER_PROP.ADD_DATE,CLW_TRADING_PARTNER_PROP.ADD_BY,CLW_TRADING_PARTNER_PROP.MOD_DATE,CLW_TRADING_PARTNER_PROP.MOD_BY FROM CLW_TRADING_PARTNER_PROP");
                where = pCriteria.getSqlClause("CLW_TRADING_PARTNER_PROP");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_TRADING_PARTNER_PROP.equals(otherTable)){
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
        TradingPartnerPropDataVector v = new TradingPartnerPropDataVector();
        while (rs.next()) {
            TradingPartnerPropData x = TradingPartnerPropData.createValue();
            
            x.setTradingPartnerPropertyId(rs.getInt(1));
            x.setTradingPartnerId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setPropertyStatusCd(rs.getString(5));
            x.setPropertyTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a TradingPartnerPropDataVector object that consists
     * of TradingPartnerPropData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for TradingPartnerPropData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new TradingPartnerPropDataVector()
     * @throws            SQLException
     */
    public static TradingPartnerPropDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        TradingPartnerPropDataVector v = new TradingPartnerPropDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT TRADING_PARTNER_PROPERTY_ID,TRADING_PARTNER_ID,SHORT_DESC,CLW_VALUE,PROPERTY_STATUS_CD,PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_TRADING_PARTNER_PROP WHERE TRADING_PARTNER_PROPERTY_ID IN (");

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
            TradingPartnerPropData x=null;
            while (rs.next()) {
                // build the object
                x=TradingPartnerPropData.createValue();
                
                x.setTradingPartnerPropertyId(rs.getInt(1));
                x.setTradingPartnerId(rs.getInt(2));
                x.setShortDesc(rs.getString(3));
                x.setValue(rs.getString(4));
                x.setPropertyStatusCd(rs.getString(5));
                x.setPropertyTypeCd(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a TradingPartnerPropDataVector object of all
     * TradingPartnerPropData objects in the database.
     * @param pCon An open database connection.
     * @return new TradingPartnerPropDataVector()
     * @throws            SQLException
     */
    public static TradingPartnerPropDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT TRADING_PARTNER_PROPERTY_ID,TRADING_PARTNER_ID,SHORT_DESC,CLW_VALUE,PROPERTY_STATUS_CD,PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_TRADING_PARTNER_PROP";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        TradingPartnerPropDataVector v = new TradingPartnerPropDataVector();
        TradingPartnerPropData x = null;
        while (rs.next()) {
            // build the object
            x = TradingPartnerPropData.createValue();
            
            x.setTradingPartnerPropertyId(rs.getInt(1));
            x.setTradingPartnerId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setPropertyStatusCd(rs.getString(5));
            x.setPropertyTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * TradingPartnerPropData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT TRADING_PARTNER_PROPERTY_ID FROM CLW_TRADING_PARTNER_PROP");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT TRADING_PARTNER_PROPERTY_ID FROM CLW_TRADING_PARTNER_PROP");
                where = pCriteria.getSqlClause("CLW_TRADING_PARTNER_PROP");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_TRADING_PARTNER_PROP.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TRADING_PARTNER_PROP");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TRADING_PARTNER_PROP");
                where = pCriteria.getSqlClause("CLW_TRADING_PARTNER_PROP");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_TRADING_PARTNER_PROP.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TRADING_PARTNER_PROP");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TRADING_PARTNER_PROP");
                where = pCriteria.getSqlClause("CLW_TRADING_PARTNER_PROP");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_TRADING_PARTNER_PROP.equals(otherTable)){
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
            log.debug("SQL text: " + sql);
        }

        return sql;
    }

    /**
     * Inserts a TradingPartnerPropData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPartnerPropData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new TradingPartnerPropData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static TradingPartnerPropData insert(Connection pCon, TradingPartnerPropData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_TRADING_PARTNER_PROP_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_TRADING_PARTNER_PROP_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setTradingPartnerPropertyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_TRADING_PARTNER_PROP (TRADING_PARTNER_PROPERTY_ID,TRADING_PARTNER_ID,SHORT_DESC,CLW_VALUE,PROPERTY_STATUS_CD,PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getTradingPartnerPropertyId());
        pstmt.setInt(2,pData.getTradingPartnerId());
        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getValue());
        pstmt.setString(5,pData.getPropertyStatusCd());
        pstmt.setString(6,pData.getPropertyTypeCd());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TRADING_PARTNER_PROPERTY_ID="+pData.getTradingPartnerPropertyId());
            log.debug("SQL:   TRADING_PARTNER_ID="+pData.getTradingPartnerId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   PROPERTY_STATUS_CD="+pData.getPropertyStatusCd());
            log.debug("SQL:   PROPERTY_TYPE_CD="+pData.getPropertyTypeCd());
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
        pData.setTradingPartnerPropertyId(0);
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
     * Updates a TradingPartnerPropData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPartnerPropData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, TradingPartnerPropData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_TRADING_PARTNER_PROP SET TRADING_PARTNER_ID = ?,SHORT_DESC = ?,CLW_VALUE = ?,PROPERTY_STATUS_CD = ?,PROPERTY_TYPE_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE TRADING_PARTNER_PROPERTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getTradingPartnerId());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getPropertyStatusCd());
        pstmt.setString(i++,pData.getPropertyTypeCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getTradingPartnerPropertyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TRADING_PARTNER_ID="+pData.getTradingPartnerId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   PROPERTY_STATUS_CD="+pData.getPropertyStatusCd());
            log.debug("SQL:   PROPERTY_TYPE_CD="+pData.getPropertyTypeCd());
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
     * Deletes a TradingPartnerPropData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pTradingPartnerPropertyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pTradingPartnerPropId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_TRADING_PARTNER_PROP WHERE TRADING_PARTNER_PROPERTY_ID = " + pTradingPartnerPropId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes TradingPartnerPropData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_TRADING_PARTNER_PROP");
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
     * Inserts a TradingPartnerPropData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPartnerPropData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, TradingPartnerPropData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_TRADING_PARTNER_PROP (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "TRADING_PARTNER_PROPERTY_ID,TRADING_PARTNER_ID,SHORT_DESC,CLW_VALUE,PROPERTY_STATUS_CD,PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getTradingPartnerPropertyId());
        pstmt.setInt(2+4,pData.getTradingPartnerId());
        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getValue());
        pstmt.setString(5+4,pData.getPropertyStatusCd());
        pstmt.setString(6+4,pData.getPropertyTypeCd());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a TradingPartnerPropData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPartnerPropData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new TradingPartnerPropData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static TradingPartnerPropData insert(Connection pCon, TradingPartnerPropData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a TradingPartnerPropData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPartnerPropData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, TradingPartnerPropData pData, boolean pLogFl)
        throws SQLException {
        TradingPartnerPropData oldData = null;
        if(pLogFl) {
          int id = pData.getTradingPartnerPropertyId();
          try {
          oldData = TradingPartnerPropDataAccess.select(pCon,id);
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
     * Deletes a TradingPartnerPropData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pTradingPartnerPropertyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pTradingPartnerPropId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_TRADING_PARTNER_PROP SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_TRADING_PARTNER_PROP d WHERE TRADING_PARTNER_PROPERTY_ID = " + pTradingPartnerPropId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pTradingPartnerPropId);
        return n;
     }

    /**
     * Deletes TradingPartnerPropData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_TRADING_PARTNER_PROP SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_TRADING_PARTNER_PROP d ");
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

