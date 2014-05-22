
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        TradingProfileConfigDataAccess
 * Description:  This class is used to build access methods to the CLW_TRADING_PROFILE_CONFIG table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.TradingProfileConfigData;
import com.cleanwise.service.api.value.TradingProfileConfigDataVector;
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
 * <code>TradingProfileConfigDataAccess</code>
 */
public class TradingProfileConfigDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(TradingProfileConfigDataAccess.class.getName());

    /** <code>CLW_TRADING_PROFILE_CONFIG</code> table name */
	/* Primary key: TRADING_PROFILE_CONFIG_ID */
	
    public static final String CLW_TRADING_PROFILE_CONFIG = "CLW_TRADING_PROFILE_CONFIG";
    
    /** <code>TRADING_PROFILE_CONFIG_ID</code> TRADING_PROFILE_CONFIG_ID column of table CLW_TRADING_PROFILE_CONFIG */
    public static final String TRADING_PROFILE_CONFIG_ID = "TRADING_PROFILE_CONFIG_ID";
    /** <code>TRADING_PROFILE_ID</code> TRADING_PROFILE_ID column of table CLW_TRADING_PROFILE_CONFIG */
    public static final String TRADING_PROFILE_ID = "TRADING_PROFILE_ID";
    /** <code>INCOMING_TRADING_PROFILE_ID</code> INCOMING_TRADING_PROFILE_ID column of table CLW_TRADING_PROFILE_CONFIG */
    public static final String INCOMING_TRADING_PROFILE_ID = "INCOMING_TRADING_PROFILE_ID";
    /** <code>SET_TYPE</code> SET_TYPE column of table CLW_TRADING_PROFILE_CONFIG */
    public static final String SET_TYPE = "SET_TYPE";
    /** <code>DIRECTION</code> DIRECTION column of table CLW_TRADING_PROFILE_CONFIG */
    public static final String DIRECTION = "DIRECTION";
    /** <code>CLASSNAME</code> CLASSNAME column of table CLW_TRADING_PROFILE_CONFIG */
    public static final String CLASSNAME = "CLASSNAME";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_TRADING_PROFILE_CONFIG */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_TRADING_PROFILE_CONFIG */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_TRADING_PROFILE_CONFIG */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_TRADING_PROFILE_CONFIG */
    public static final String MOD_BY = "MOD_BY";
    /** <code>PATTERN</code> PATTERN column of table CLW_TRADING_PROFILE_CONFIG */
    public static final String PATTERN = "PATTERN";

    /**
     * Constructor.
     */
    public TradingProfileConfigDataAccess()
    {
    }

    /**
     * Gets a TradingProfileConfigData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pTradingProfileConfigId The key requested.
     * @return new TradingProfileConfigData()
     * @throws            SQLException
     */
    public static TradingProfileConfigData select(Connection pCon, int pTradingProfileConfigId)
        throws SQLException, DataNotFoundException {
        TradingProfileConfigData x=null;
        String sql="SELECT TRADING_PROFILE_CONFIG_ID,TRADING_PROFILE_ID,INCOMING_TRADING_PROFILE_ID,SET_TYPE,DIRECTION,CLASSNAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PATTERN FROM CLW_TRADING_PROFILE_CONFIG WHERE TRADING_PROFILE_CONFIG_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pTradingProfileConfigId=" + pTradingProfileConfigId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pTradingProfileConfigId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=TradingProfileConfigData.createValue();
            
            x.setTradingProfileConfigId(rs.getInt(1));
            x.setTradingProfileId(rs.getInt(2));
            x.setIncomingTradingProfileId(rs.getInt(3));
            x.setSetType(rs.getString(4));
            x.setDirection(rs.getString(5));
            x.setClassname(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setPattern(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("TRADING_PROFILE_CONFIG_ID :" + pTradingProfileConfigId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a TradingProfileConfigDataVector object that consists
     * of TradingProfileConfigData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new TradingProfileConfigDataVector()
     * @throws            SQLException
     */
    public static TradingProfileConfigDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a TradingProfileConfigData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_TRADING_PROFILE_CONFIG.TRADING_PROFILE_CONFIG_ID,CLW_TRADING_PROFILE_CONFIG.TRADING_PROFILE_ID,CLW_TRADING_PROFILE_CONFIG.INCOMING_TRADING_PROFILE_ID,CLW_TRADING_PROFILE_CONFIG.SET_TYPE,CLW_TRADING_PROFILE_CONFIG.DIRECTION,CLW_TRADING_PROFILE_CONFIG.CLASSNAME,CLW_TRADING_PROFILE_CONFIG.ADD_DATE,CLW_TRADING_PROFILE_CONFIG.ADD_BY,CLW_TRADING_PROFILE_CONFIG.MOD_DATE,CLW_TRADING_PROFILE_CONFIG.MOD_BY,CLW_TRADING_PROFILE_CONFIG.PATTERN";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated TradingProfileConfigData Object.
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
    *@returns a populated TradingProfileConfigData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         TradingProfileConfigData x = TradingProfileConfigData.createValue();
         
         x.setTradingProfileConfigId(rs.getInt(1+offset));
         x.setTradingProfileId(rs.getInt(2+offset));
         x.setIncomingTradingProfileId(rs.getInt(3+offset));
         x.setSetType(rs.getString(4+offset));
         x.setDirection(rs.getString(5+offset));
         x.setClassname(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         x.setPattern(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the TradingProfileConfigData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a TradingProfileConfigDataVector object that consists
     * of TradingProfileConfigData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new TradingProfileConfigDataVector()
     * @throws            SQLException
     */
    public static TradingProfileConfigDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT TRADING_PROFILE_CONFIG_ID,TRADING_PROFILE_ID,INCOMING_TRADING_PROFILE_ID,SET_TYPE,DIRECTION,CLASSNAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PATTERN FROM CLW_TRADING_PROFILE_CONFIG");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_TRADING_PROFILE_CONFIG.TRADING_PROFILE_CONFIG_ID,CLW_TRADING_PROFILE_CONFIG.TRADING_PROFILE_ID,CLW_TRADING_PROFILE_CONFIG.INCOMING_TRADING_PROFILE_ID,CLW_TRADING_PROFILE_CONFIG.SET_TYPE,CLW_TRADING_PROFILE_CONFIG.DIRECTION,CLW_TRADING_PROFILE_CONFIG.CLASSNAME,CLW_TRADING_PROFILE_CONFIG.ADD_DATE,CLW_TRADING_PROFILE_CONFIG.ADD_BY,CLW_TRADING_PROFILE_CONFIG.MOD_DATE,CLW_TRADING_PROFILE_CONFIG.MOD_BY,CLW_TRADING_PROFILE_CONFIG.PATTERN FROM CLW_TRADING_PROFILE_CONFIG");
                where = pCriteria.getSqlClause("CLW_TRADING_PROFILE_CONFIG");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_TRADING_PROFILE_CONFIG.equals(otherTable)){
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
        TradingProfileConfigDataVector v = new TradingProfileConfigDataVector();
        while (rs.next()) {
            TradingProfileConfigData x = TradingProfileConfigData.createValue();
            
            x.setTradingProfileConfigId(rs.getInt(1));
            x.setTradingProfileId(rs.getInt(2));
            x.setIncomingTradingProfileId(rs.getInt(3));
            x.setSetType(rs.getString(4));
            x.setDirection(rs.getString(5));
            x.setClassname(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setPattern(rs.getString(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a TradingProfileConfigDataVector object that consists
     * of TradingProfileConfigData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for TradingProfileConfigData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new TradingProfileConfigDataVector()
     * @throws            SQLException
     */
    public static TradingProfileConfigDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        TradingProfileConfigDataVector v = new TradingProfileConfigDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT TRADING_PROFILE_CONFIG_ID,TRADING_PROFILE_ID,INCOMING_TRADING_PROFILE_ID,SET_TYPE,DIRECTION,CLASSNAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PATTERN FROM CLW_TRADING_PROFILE_CONFIG WHERE TRADING_PROFILE_CONFIG_ID IN (");

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
            TradingProfileConfigData x=null;
            while (rs.next()) {
                // build the object
                x=TradingProfileConfigData.createValue();
                
                x.setTradingProfileConfigId(rs.getInt(1));
                x.setTradingProfileId(rs.getInt(2));
                x.setIncomingTradingProfileId(rs.getInt(3));
                x.setSetType(rs.getString(4));
                x.setDirection(rs.getString(5));
                x.setClassname(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                x.setPattern(rs.getString(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a TradingProfileConfigDataVector object of all
     * TradingProfileConfigData objects in the database.
     * @param pCon An open database connection.
     * @return new TradingProfileConfigDataVector()
     * @throws            SQLException
     */
    public static TradingProfileConfigDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT TRADING_PROFILE_CONFIG_ID,TRADING_PROFILE_ID,INCOMING_TRADING_PROFILE_ID,SET_TYPE,DIRECTION,CLASSNAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PATTERN FROM CLW_TRADING_PROFILE_CONFIG";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        TradingProfileConfigDataVector v = new TradingProfileConfigDataVector();
        TradingProfileConfigData x = null;
        while (rs.next()) {
            // build the object
            x = TradingProfileConfigData.createValue();
            
            x.setTradingProfileConfigId(rs.getInt(1));
            x.setTradingProfileId(rs.getInt(2));
            x.setIncomingTradingProfileId(rs.getInt(3));
            x.setSetType(rs.getString(4));
            x.setDirection(rs.getString(5));
            x.setClassname(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setPattern(rs.getString(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * TradingProfileConfigData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT TRADING_PROFILE_CONFIG_ID FROM CLW_TRADING_PROFILE_CONFIG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TRADING_PROFILE_CONFIG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TRADING_PROFILE_CONFIG");
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
     * Inserts a TradingProfileConfigData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingProfileConfigData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new TradingProfileConfigData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static TradingProfileConfigData insert(Connection pCon, TradingProfileConfigData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_TRADING_PROFILE_CONFIG_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_TRADING_PROFILE_CONFIG_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setTradingProfileConfigId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_TRADING_PROFILE_CONFIG (TRADING_PROFILE_CONFIG_ID,TRADING_PROFILE_ID,INCOMING_TRADING_PROFILE_ID,SET_TYPE,DIRECTION,CLASSNAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PATTERN) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getTradingProfileConfigId());
        pstmt.setInt(2,pData.getTradingProfileId());
        if (pData.getIncomingTradingProfileId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getIncomingTradingProfileId());
        }

        pstmt.setString(4,pData.getSetType());
        pstmt.setString(5,pData.getDirection());
        pstmt.setString(6,pData.getClassname());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());
        pstmt.setString(11,pData.getPattern());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TRADING_PROFILE_CONFIG_ID="+pData.getTradingProfileConfigId());
            log.debug("SQL:   TRADING_PROFILE_ID="+pData.getTradingProfileId());
            log.debug("SQL:   INCOMING_TRADING_PROFILE_ID="+pData.getIncomingTradingProfileId());
            log.debug("SQL:   SET_TYPE="+pData.getSetType());
            log.debug("SQL:   DIRECTION="+pData.getDirection());
            log.debug("SQL:   CLASSNAME="+pData.getClassname());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PATTERN="+pData.getPattern());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setTradingProfileConfigId(0);
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
     * Updates a TradingProfileConfigData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingProfileConfigData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, TradingProfileConfigData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_TRADING_PROFILE_CONFIG SET TRADING_PROFILE_ID = ?,INCOMING_TRADING_PROFILE_ID = ?,SET_TYPE = ?,DIRECTION = ?,CLASSNAME = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,PATTERN = ? WHERE TRADING_PROFILE_CONFIG_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getTradingProfileId());
        if (pData.getIncomingTradingProfileId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getIncomingTradingProfileId());
        }

        pstmt.setString(i++,pData.getSetType());
        pstmt.setString(i++,pData.getDirection());
        pstmt.setString(i++,pData.getClassname());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getPattern());
        pstmt.setInt(i++,pData.getTradingProfileConfigId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TRADING_PROFILE_ID="+pData.getTradingProfileId());
            log.debug("SQL:   INCOMING_TRADING_PROFILE_ID="+pData.getIncomingTradingProfileId());
            log.debug("SQL:   SET_TYPE="+pData.getSetType());
            log.debug("SQL:   DIRECTION="+pData.getDirection());
            log.debug("SQL:   CLASSNAME="+pData.getClassname());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PATTERN="+pData.getPattern());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a TradingProfileConfigData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pTradingProfileConfigId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pTradingProfileConfigId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_TRADING_PROFILE_CONFIG WHERE TRADING_PROFILE_CONFIG_ID = " + pTradingProfileConfigId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes TradingProfileConfigData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_TRADING_PROFILE_CONFIG");
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
     * Inserts a TradingProfileConfigData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingProfileConfigData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, TradingProfileConfigData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_TRADING_PROFILE_CONFIG (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "TRADING_PROFILE_CONFIG_ID,TRADING_PROFILE_ID,INCOMING_TRADING_PROFILE_ID,SET_TYPE,DIRECTION,CLASSNAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PATTERN) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getTradingProfileConfigId());
        pstmt.setInt(2+4,pData.getTradingProfileId());
        if (pData.getIncomingTradingProfileId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getIncomingTradingProfileId());
        }

        pstmt.setString(4+4,pData.getSetType());
        pstmt.setString(5+4,pData.getDirection());
        pstmt.setString(6+4,pData.getClassname());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());
        pstmt.setString(11+4,pData.getPattern());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a TradingProfileConfigData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingProfileConfigData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new TradingProfileConfigData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static TradingProfileConfigData insert(Connection pCon, TradingProfileConfigData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a TradingProfileConfigData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingProfileConfigData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, TradingProfileConfigData pData, boolean pLogFl)
        throws SQLException {
        TradingProfileConfigData oldData = null;
        if(pLogFl) {
          int id = pData.getTradingProfileConfigId();
          try {
          oldData = TradingProfileConfigDataAccess.select(pCon,id);
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
     * Deletes a TradingProfileConfigData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pTradingProfileConfigId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pTradingProfileConfigId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_TRADING_PROFILE_CONFIG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_TRADING_PROFILE_CONFIG d WHERE TRADING_PROFILE_CONFIG_ID = " + pTradingProfileConfigId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pTradingProfileConfigId);
        return n;
     }

    /**
     * Deletes TradingProfileConfigData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_TRADING_PROFILE_CONFIG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_TRADING_PROFILE_CONFIG d ");
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

