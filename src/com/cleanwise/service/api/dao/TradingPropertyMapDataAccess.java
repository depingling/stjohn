
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        TradingPropertyMapDataAccess
 * Description:  This class is used to build access methods to the CLW_TRADING_PROPERTY_MAP table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;
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
 * <code>TradingPropertyMapDataAccess</code>
 */
public class TradingPropertyMapDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(TradingPropertyMapDataAccess.class.getName());

    /** <code>CLW_TRADING_PROPERTY_MAP</code> table name */
	/* Primary key: TRADING_PROPERTY_MAP_ID */
	
    public static final String CLW_TRADING_PROPERTY_MAP = "CLW_TRADING_PROPERTY_MAP";
    
    /** <code>TRADING_PROPERTY_MAP_ID</code> TRADING_PROPERTY_MAP_ID column of table CLW_TRADING_PROPERTY_MAP */
    public static final String TRADING_PROPERTY_MAP_ID = "TRADING_PROPERTY_MAP_ID";
    /** <code>TRADING_PROFILE_ID</code> TRADING_PROFILE_ID column of table CLW_TRADING_PROPERTY_MAP */
    public static final String TRADING_PROFILE_ID = "TRADING_PROFILE_ID";
    /** <code>SET_TYPE</code> SET_TYPE column of table CLW_TRADING_PROPERTY_MAP */
    public static final String SET_TYPE = "SET_TYPE";
    /** <code>DIRECTION</code> DIRECTION column of table CLW_TRADING_PROPERTY_MAP */
    public static final String DIRECTION = "DIRECTION";
    /** <code>ENTITY_PROPERTY</code> ENTITY_PROPERTY column of table CLW_TRADING_PROPERTY_MAP */
    public static final String ENTITY_PROPERTY = "ENTITY_PROPERTY";
    /** <code>PROPERTY_TYPE_CD</code> PROPERTY_TYPE_CD column of table CLW_TRADING_PROPERTY_MAP */
    public static final String PROPERTY_TYPE_CD = "PROPERTY_TYPE_CD";
    /** <code>QUALIFIER_CODE</code> QUALIFIER_CODE column of table CLW_TRADING_PROPERTY_MAP */
    public static final String QUALIFIER_CODE = "QUALIFIER_CODE";
    /** <code>HARD_VALUE</code> HARD_VALUE column of table CLW_TRADING_PROPERTY_MAP */
    public static final String HARD_VALUE = "HARD_VALUE";
    /** <code>MANDATORY</code> MANDATORY column of table CLW_TRADING_PROPERTY_MAP */
    public static final String MANDATORY = "MANDATORY";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_TRADING_PROPERTY_MAP */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_TRADING_PROPERTY_MAP */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_TRADING_PROPERTY_MAP */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_TRADING_PROPERTY_MAP */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>USE_CODE</code> USE_CODE column of table CLW_TRADING_PROPERTY_MAP */
    public static final String USE_CODE = "USE_CODE";
    /** <code>ORDER_BY</code> ORDER_BY column of table CLW_TRADING_PROPERTY_MAP */
    public static final String ORDER_BY = "ORDER_BY";
    /** <code>TRADING_PROPERTY_MAP_CODE</code> TRADING_PROPERTY_MAP_CODE column of table CLW_TRADING_PROPERTY_MAP */
    public static final String TRADING_PROPERTY_MAP_CODE = "TRADING_PROPERTY_MAP_CODE";
    /** <code>PATTERN</code> PATTERN column of table CLW_TRADING_PROPERTY_MAP */
    public static final String PATTERN = "PATTERN";

    /**
     * Constructor.
     */
    public TradingPropertyMapDataAccess()
    {
    }

    /**
     * Gets a TradingPropertyMapData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pTradingPropertyMapId The key requested.
     * @return new TradingPropertyMapData()
     * @throws            SQLException
     */
    public static TradingPropertyMapData select(Connection pCon, int pTradingPropertyMapId)
        throws SQLException, DataNotFoundException {
        TradingPropertyMapData x=null;
        String sql="SELECT TRADING_PROPERTY_MAP_ID,TRADING_PROFILE_ID,SET_TYPE,DIRECTION,ENTITY_PROPERTY,PROPERTY_TYPE_CD,QUALIFIER_CODE,HARD_VALUE,MANDATORY,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,USE_CODE,ORDER_BY,TRADING_PROPERTY_MAP_CODE,PATTERN FROM CLW_TRADING_PROPERTY_MAP WHERE TRADING_PROPERTY_MAP_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pTradingPropertyMapId=" + pTradingPropertyMapId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pTradingPropertyMapId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=TradingPropertyMapData.createValue();
            
            x.setTradingPropertyMapId(rs.getInt(1));
            x.setTradingProfileId(rs.getInt(2));
            x.setSetType(rs.getString(3));
            x.setDirection(rs.getString(4));
            x.setEntityProperty(rs.getString(5));
            x.setPropertyTypeCd(rs.getString(6));
            x.setQualifierCode(rs.getString(7));
            x.setHardValue(rs.getString(8));
            x.setMandatory(rs.getString(9));
            x.setAddBy(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setUseCode(rs.getString(14));
            x.setOrderBy(rs.getInt(15));
            x.setTradingPropertyMapCode(rs.getString(16));
            x.setPattern(rs.getString(17));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("TRADING_PROPERTY_MAP_ID :" + pTradingPropertyMapId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a TradingPropertyMapDataVector object that consists
     * of TradingPropertyMapData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new TradingPropertyMapDataVector()
     * @throws            SQLException
     */
    public static TradingPropertyMapDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a TradingPropertyMapData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_TRADING_PROPERTY_MAP.TRADING_PROPERTY_MAP_ID,CLW_TRADING_PROPERTY_MAP.TRADING_PROFILE_ID,CLW_TRADING_PROPERTY_MAP.SET_TYPE,CLW_TRADING_PROPERTY_MAP.DIRECTION,CLW_TRADING_PROPERTY_MAP.ENTITY_PROPERTY,CLW_TRADING_PROPERTY_MAP.PROPERTY_TYPE_CD,CLW_TRADING_PROPERTY_MAP.QUALIFIER_CODE,CLW_TRADING_PROPERTY_MAP.HARD_VALUE,CLW_TRADING_PROPERTY_MAP.MANDATORY,CLW_TRADING_PROPERTY_MAP.ADD_BY,CLW_TRADING_PROPERTY_MAP.ADD_DATE,CLW_TRADING_PROPERTY_MAP.MOD_BY,CLW_TRADING_PROPERTY_MAP.MOD_DATE,CLW_TRADING_PROPERTY_MAP.USE_CODE,CLW_TRADING_PROPERTY_MAP.ORDER_BY,CLW_TRADING_PROPERTY_MAP.TRADING_PROPERTY_MAP_CODE,CLW_TRADING_PROPERTY_MAP.PATTERN";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated TradingPropertyMapData Object.
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
    *@returns a populated TradingPropertyMapData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         TradingPropertyMapData x = TradingPropertyMapData.createValue();
         
         x.setTradingPropertyMapId(rs.getInt(1+offset));
         x.setTradingProfileId(rs.getInt(2+offset));
         x.setSetType(rs.getString(3+offset));
         x.setDirection(rs.getString(4+offset));
         x.setEntityProperty(rs.getString(5+offset));
         x.setPropertyTypeCd(rs.getString(6+offset));
         x.setQualifierCode(rs.getString(7+offset));
         x.setHardValue(rs.getString(8+offset));
         x.setMandatory(rs.getString(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setAddDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         x.setModDate(rs.getTimestamp(13+offset));
         x.setUseCode(rs.getString(14+offset));
         x.setOrderBy(rs.getInt(15+offset));
         x.setTradingPropertyMapCode(rs.getString(16+offset));
         x.setPattern(rs.getString(17+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the TradingPropertyMapData Object represents.
    */
    public int getColumnCount(){
        return 17;
    }

    /**
     * Gets a TradingPropertyMapDataVector object that consists
     * of TradingPropertyMapData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new TradingPropertyMapDataVector()
     * @throws            SQLException
     */
    public static TradingPropertyMapDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT TRADING_PROPERTY_MAP_ID,TRADING_PROFILE_ID,SET_TYPE,DIRECTION,ENTITY_PROPERTY,PROPERTY_TYPE_CD,QUALIFIER_CODE,HARD_VALUE,MANDATORY,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,USE_CODE,ORDER_BY,TRADING_PROPERTY_MAP_CODE,PATTERN FROM CLW_TRADING_PROPERTY_MAP");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_TRADING_PROPERTY_MAP.TRADING_PROPERTY_MAP_ID,CLW_TRADING_PROPERTY_MAP.TRADING_PROFILE_ID,CLW_TRADING_PROPERTY_MAP.SET_TYPE,CLW_TRADING_PROPERTY_MAP.DIRECTION,CLW_TRADING_PROPERTY_MAP.ENTITY_PROPERTY,CLW_TRADING_PROPERTY_MAP.PROPERTY_TYPE_CD,CLW_TRADING_PROPERTY_MAP.QUALIFIER_CODE,CLW_TRADING_PROPERTY_MAP.HARD_VALUE,CLW_TRADING_PROPERTY_MAP.MANDATORY,CLW_TRADING_PROPERTY_MAP.ADD_BY,CLW_TRADING_PROPERTY_MAP.ADD_DATE,CLW_TRADING_PROPERTY_MAP.MOD_BY,CLW_TRADING_PROPERTY_MAP.MOD_DATE,CLW_TRADING_PROPERTY_MAP.USE_CODE,CLW_TRADING_PROPERTY_MAP.ORDER_BY,CLW_TRADING_PROPERTY_MAP.TRADING_PROPERTY_MAP_CODE,CLW_TRADING_PROPERTY_MAP.PATTERN FROM CLW_TRADING_PROPERTY_MAP");
                where = pCriteria.getSqlClause("CLW_TRADING_PROPERTY_MAP");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_TRADING_PROPERTY_MAP.equals(otherTable)){
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
        TradingPropertyMapDataVector v = new TradingPropertyMapDataVector();
        while (rs.next()) {
            TradingPropertyMapData x = TradingPropertyMapData.createValue();
            
            x.setTradingPropertyMapId(rs.getInt(1));
            x.setTradingProfileId(rs.getInt(2));
            x.setSetType(rs.getString(3));
            x.setDirection(rs.getString(4));
            x.setEntityProperty(rs.getString(5));
            x.setPropertyTypeCd(rs.getString(6));
            x.setQualifierCode(rs.getString(7));
            x.setHardValue(rs.getString(8));
            x.setMandatory(rs.getString(9));
            x.setAddBy(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setUseCode(rs.getString(14));
            x.setOrderBy(rs.getInt(15));
            x.setTradingPropertyMapCode(rs.getString(16));
            x.setPattern(rs.getString(17));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a TradingPropertyMapDataVector object that consists
     * of TradingPropertyMapData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for TradingPropertyMapData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new TradingPropertyMapDataVector()
     * @throws            SQLException
     */
    public static TradingPropertyMapDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        TradingPropertyMapDataVector v = new TradingPropertyMapDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT TRADING_PROPERTY_MAP_ID,TRADING_PROFILE_ID,SET_TYPE,DIRECTION,ENTITY_PROPERTY,PROPERTY_TYPE_CD,QUALIFIER_CODE,HARD_VALUE,MANDATORY,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,USE_CODE,ORDER_BY,TRADING_PROPERTY_MAP_CODE,PATTERN FROM CLW_TRADING_PROPERTY_MAP WHERE TRADING_PROPERTY_MAP_ID IN (");

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
            TradingPropertyMapData x=null;
            while (rs.next()) {
                // build the object
                x=TradingPropertyMapData.createValue();
                
                x.setTradingPropertyMapId(rs.getInt(1));
                x.setTradingProfileId(rs.getInt(2));
                x.setSetType(rs.getString(3));
                x.setDirection(rs.getString(4));
                x.setEntityProperty(rs.getString(5));
                x.setPropertyTypeCd(rs.getString(6));
                x.setQualifierCode(rs.getString(7));
                x.setHardValue(rs.getString(8));
                x.setMandatory(rs.getString(9));
                x.setAddBy(rs.getString(10));
                x.setAddDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                x.setModDate(rs.getTimestamp(13));
                x.setUseCode(rs.getString(14));
                x.setOrderBy(rs.getInt(15));
                x.setTradingPropertyMapCode(rs.getString(16));
                x.setPattern(rs.getString(17));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a TradingPropertyMapDataVector object of all
     * TradingPropertyMapData objects in the database.
     * @param pCon An open database connection.
     * @return new TradingPropertyMapDataVector()
     * @throws            SQLException
     */
    public static TradingPropertyMapDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT TRADING_PROPERTY_MAP_ID,TRADING_PROFILE_ID,SET_TYPE,DIRECTION,ENTITY_PROPERTY,PROPERTY_TYPE_CD,QUALIFIER_CODE,HARD_VALUE,MANDATORY,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,USE_CODE,ORDER_BY,TRADING_PROPERTY_MAP_CODE,PATTERN FROM CLW_TRADING_PROPERTY_MAP";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        TradingPropertyMapDataVector v = new TradingPropertyMapDataVector();
        TradingPropertyMapData x = null;
        while (rs.next()) {
            // build the object
            x = TradingPropertyMapData.createValue();
            
            x.setTradingPropertyMapId(rs.getInt(1));
            x.setTradingProfileId(rs.getInt(2));
            x.setSetType(rs.getString(3));
            x.setDirection(rs.getString(4));
            x.setEntityProperty(rs.getString(5));
            x.setPropertyTypeCd(rs.getString(6));
            x.setQualifierCode(rs.getString(7));
            x.setHardValue(rs.getString(8));
            x.setMandatory(rs.getString(9));
            x.setAddBy(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setUseCode(rs.getString(14));
            x.setOrderBy(rs.getInt(15));
            x.setTradingPropertyMapCode(rs.getString(16));
            x.setPattern(rs.getString(17));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * TradingPropertyMapData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT TRADING_PROPERTY_MAP_ID FROM CLW_TRADING_PROPERTY_MAP");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TRADING_PROPERTY_MAP");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TRADING_PROPERTY_MAP");
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
     * Inserts a TradingPropertyMapData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPropertyMapData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new TradingPropertyMapData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static TradingPropertyMapData insert(Connection pCon, TradingPropertyMapData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_TRADING_PROPERTY_MAP_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_TRADING_PROPERTY_MAP_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setTradingPropertyMapId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_TRADING_PROPERTY_MAP (TRADING_PROPERTY_MAP_ID,TRADING_PROFILE_ID,SET_TYPE,DIRECTION,ENTITY_PROPERTY,PROPERTY_TYPE_CD,QUALIFIER_CODE,HARD_VALUE,MANDATORY,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,USE_CODE,ORDER_BY,TRADING_PROPERTY_MAP_CODE,PATTERN) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getTradingPropertyMapId());
        pstmt.setInt(2,pData.getTradingProfileId());
        pstmt.setString(3,pData.getSetType());
        pstmt.setString(4,pData.getDirection());
        pstmt.setString(5,pData.getEntityProperty());
        pstmt.setString(6,pData.getPropertyTypeCd());
        pstmt.setString(7,pData.getQualifierCode());
        pstmt.setString(8,pData.getHardValue());
        pstmt.setString(9,pData.getMandatory());
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12,pData.getModBy());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14,pData.getUseCode());
        pstmt.setInt(15,pData.getOrderBy());
        pstmt.setString(16,pData.getTradingPropertyMapCode());
        pstmt.setString(17,pData.getPattern());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TRADING_PROPERTY_MAP_ID="+pData.getTradingPropertyMapId());
            log.debug("SQL:   TRADING_PROFILE_ID="+pData.getTradingProfileId());
            log.debug("SQL:   SET_TYPE="+pData.getSetType());
            log.debug("SQL:   DIRECTION="+pData.getDirection());
            log.debug("SQL:   ENTITY_PROPERTY="+pData.getEntityProperty());
            log.debug("SQL:   PROPERTY_TYPE_CD="+pData.getPropertyTypeCd());
            log.debug("SQL:   QUALIFIER_CODE="+pData.getQualifierCode());
            log.debug("SQL:   HARD_VALUE="+pData.getHardValue());
            log.debug("SQL:   MANDATORY="+pData.getMandatory());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   USE_CODE="+pData.getUseCode());
            log.debug("SQL:   ORDER_BY="+pData.getOrderBy());
            log.debug("SQL:   TRADING_PROPERTY_MAP_CODE="+pData.getTradingPropertyMapCode());
            log.debug("SQL:   PATTERN="+pData.getPattern());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setTradingPropertyMapId(0);
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
     * Updates a TradingPropertyMapData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPropertyMapData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, TradingPropertyMapData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_TRADING_PROPERTY_MAP SET TRADING_PROFILE_ID = ?,SET_TYPE = ?,DIRECTION = ?,ENTITY_PROPERTY = ?,PROPERTY_TYPE_CD = ?,QUALIFIER_CODE = ?,HARD_VALUE = ?,MANDATORY = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ?,USE_CODE = ?,ORDER_BY = ?,TRADING_PROPERTY_MAP_CODE = ?,PATTERN = ? WHERE TRADING_PROPERTY_MAP_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getTradingProfileId());
        pstmt.setString(i++,pData.getSetType());
        pstmt.setString(i++,pData.getDirection());
        pstmt.setString(i++,pData.getEntityProperty());
        pstmt.setString(i++,pData.getPropertyTypeCd());
        pstmt.setString(i++,pData.getQualifierCode());
        pstmt.setString(i++,pData.getHardValue());
        pstmt.setString(i++,pData.getMandatory());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getUseCode());
        pstmt.setInt(i++,pData.getOrderBy());
        pstmt.setString(i++,pData.getTradingPropertyMapCode());
        pstmt.setString(i++,pData.getPattern());
        pstmt.setInt(i++,pData.getTradingPropertyMapId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TRADING_PROFILE_ID="+pData.getTradingProfileId());
            log.debug("SQL:   SET_TYPE="+pData.getSetType());
            log.debug("SQL:   DIRECTION="+pData.getDirection());
            log.debug("SQL:   ENTITY_PROPERTY="+pData.getEntityProperty());
            log.debug("SQL:   PROPERTY_TYPE_CD="+pData.getPropertyTypeCd());
            log.debug("SQL:   QUALIFIER_CODE="+pData.getQualifierCode());
            log.debug("SQL:   HARD_VALUE="+pData.getHardValue());
            log.debug("SQL:   MANDATORY="+pData.getMandatory());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   USE_CODE="+pData.getUseCode());
            log.debug("SQL:   ORDER_BY="+pData.getOrderBy());
            log.debug("SQL:   TRADING_PROPERTY_MAP_CODE="+pData.getTradingPropertyMapCode());
            log.debug("SQL:   PATTERN="+pData.getPattern());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a TradingPropertyMapData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pTradingPropertyMapId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pTradingPropertyMapId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_TRADING_PROPERTY_MAP WHERE TRADING_PROPERTY_MAP_ID = " + pTradingPropertyMapId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes TradingPropertyMapData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_TRADING_PROPERTY_MAP");
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
     * Inserts a TradingPropertyMapData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPropertyMapData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, TradingPropertyMapData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_TRADING_PROPERTY_MAP (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "TRADING_PROPERTY_MAP_ID,TRADING_PROFILE_ID,SET_TYPE,DIRECTION,ENTITY_PROPERTY,PROPERTY_TYPE_CD,QUALIFIER_CODE,HARD_VALUE,MANDATORY,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,USE_CODE,ORDER_BY,TRADING_PROPERTY_MAP_CODE,PATTERN) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getTradingPropertyMapId());
        pstmt.setInt(2+4,pData.getTradingProfileId());
        pstmt.setString(3+4,pData.getSetType());
        pstmt.setString(4+4,pData.getDirection());
        pstmt.setString(5+4,pData.getEntityProperty());
        pstmt.setString(6+4,pData.getPropertyTypeCd());
        pstmt.setString(7+4,pData.getQualifierCode());
        pstmt.setString(8+4,pData.getHardValue());
        pstmt.setString(9+4,pData.getMandatory());
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12+4,pData.getModBy());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14+4,pData.getUseCode());
        pstmt.setInt(15+4,pData.getOrderBy());
        pstmt.setString(16+4,pData.getTradingPropertyMapCode());
        pstmt.setString(17+4,pData.getPattern());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a TradingPropertyMapData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPropertyMapData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new TradingPropertyMapData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static TradingPropertyMapData insert(Connection pCon, TradingPropertyMapData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a TradingPropertyMapData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPropertyMapData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, TradingPropertyMapData pData, boolean pLogFl)
        throws SQLException {
        TradingPropertyMapData oldData = null;
        if(pLogFl) {
          int id = pData.getTradingPropertyMapId();
          try {
          oldData = TradingPropertyMapDataAccess.select(pCon,id);
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
     * Deletes a TradingPropertyMapData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pTradingPropertyMapId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pTradingPropertyMapId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_TRADING_PROPERTY_MAP SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_TRADING_PROPERTY_MAP d WHERE TRADING_PROPERTY_MAP_ID = " + pTradingPropertyMapId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pTradingPropertyMapId);
        return n;
     }

    /**
     * Deletes TradingPropertyMapData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_TRADING_PROPERTY_MAP SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_TRADING_PROPERTY_MAP d ");
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

