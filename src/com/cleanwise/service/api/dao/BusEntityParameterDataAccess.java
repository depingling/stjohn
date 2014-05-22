
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        BusEntityParameterDataAccess
 * Description:  This class is used to build access methods to the CLW_BUS_ENTITY_PARAMETER table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.BusEntityParameterData;
import com.cleanwise.service.api.value.BusEntityParameterDataVector;
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
 * <code>BusEntityParameterDataAccess</code>
 */
public class BusEntityParameterDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(BusEntityParameterDataAccess.class.getName());

    /** <code>CLW_BUS_ENTITY_PARAMETER</code> table name */
	/* Primary key: BUS_ENTITY_PARAMETER_ID */
	
    public static final String CLW_BUS_ENTITY_PARAMETER = "CLW_BUS_ENTITY_PARAMETER";
    
    /** <code>BUS_ENTITY_PARAMETER_ID</code> BUS_ENTITY_PARAMETER_ID column of table CLW_BUS_ENTITY_PARAMETER */
    public static final String BUS_ENTITY_PARAMETER_ID = "BUS_ENTITY_PARAMETER_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_BUS_ENTITY_PARAMETER */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>NAME</code> NAME column of table CLW_BUS_ENTITY_PARAMETER */
    public static final String NAME = "NAME";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_BUS_ENTITY_PARAMETER */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_BUS_ENTITY_PARAMETER */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_BUS_ENTITY_PARAMETER */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_BUS_ENTITY_PARAMETER */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_BUS_ENTITY_PARAMETER */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_BUS_ENTITY_PARAMETER */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_BUS_ENTITY_PARAMETER */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public BusEntityParameterDataAccess()
    {
    }

    /**
     * Gets a BusEntityParameterData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pBusEntityParameterId The key requested.
     * @return new BusEntityParameterData()
     * @throws            SQLException
     */
    public static BusEntityParameterData select(Connection pCon, int pBusEntityParameterId)
        throws SQLException, DataNotFoundException {
        BusEntityParameterData x=null;
        String sql="SELECT BUS_ENTITY_PARAMETER_ID,BUS_ENTITY_ID,NAME,CLW_VALUE,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUS_ENTITY_PARAMETER WHERE BUS_ENTITY_PARAMETER_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pBusEntityParameterId=" + pBusEntityParameterId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pBusEntityParameterId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=BusEntityParameterData.createValue();
            
            x.setBusEntityParameterId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setName(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setEffDate(rs.getDate(5));
            x.setExpDate(rs.getDate(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("BUS_ENTITY_PARAMETER_ID :" + pBusEntityParameterId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a BusEntityParameterDataVector object that consists
     * of BusEntityParameterData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new BusEntityParameterDataVector()
     * @throws            SQLException
     */
    public static BusEntityParameterDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a BusEntityParameterData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_BUS_ENTITY_PARAMETER.BUS_ENTITY_PARAMETER_ID,CLW_BUS_ENTITY_PARAMETER.BUS_ENTITY_ID,CLW_BUS_ENTITY_PARAMETER.NAME,CLW_BUS_ENTITY_PARAMETER.CLW_VALUE,CLW_BUS_ENTITY_PARAMETER.EFF_DATE,CLW_BUS_ENTITY_PARAMETER.EXP_DATE,CLW_BUS_ENTITY_PARAMETER.ADD_DATE,CLW_BUS_ENTITY_PARAMETER.ADD_BY,CLW_BUS_ENTITY_PARAMETER.MOD_DATE,CLW_BUS_ENTITY_PARAMETER.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated BusEntityParameterData Object.
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
    *@returns a populated BusEntityParameterData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         BusEntityParameterData x = BusEntityParameterData.createValue();
         
         x.setBusEntityParameterId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setName(rs.getString(3+offset));
         x.setValue(rs.getString(4+offset));
         x.setEffDate(rs.getDate(5+offset));
         x.setExpDate(rs.getDate(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the BusEntityParameterData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a BusEntityParameterDataVector object that consists
     * of BusEntityParameterData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new BusEntityParameterDataVector()
     * @throws            SQLException
     */
    public static BusEntityParameterDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT BUS_ENTITY_PARAMETER_ID,BUS_ENTITY_ID,NAME,CLW_VALUE,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUS_ENTITY_PARAMETER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_BUS_ENTITY_PARAMETER.BUS_ENTITY_PARAMETER_ID,CLW_BUS_ENTITY_PARAMETER.BUS_ENTITY_ID,CLW_BUS_ENTITY_PARAMETER.NAME,CLW_BUS_ENTITY_PARAMETER.CLW_VALUE,CLW_BUS_ENTITY_PARAMETER.EFF_DATE,CLW_BUS_ENTITY_PARAMETER.EXP_DATE,CLW_BUS_ENTITY_PARAMETER.ADD_DATE,CLW_BUS_ENTITY_PARAMETER.ADD_BY,CLW_BUS_ENTITY_PARAMETER.MOD_DATE,CLW_BUS_ENTITY_PARAMETER.MOD_BY FROM CLW_BUS_ENTITY_PARAMETER");
                where = pCriteria.getSqlClause("CLW_BUS_ENTITY_PARAMETER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_BUS_ENTITY_PARAMETER.equals(otherTable)){
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
        BusEntityParameterDataVector v = new BusEntityParameterDataVector();
        while (rs.next()) {
            BusEntityParameterData x = BusEntityParameterData.createValue();
            
            x.setBusEntityParameterId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setName(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setEffDate(rs.getDate(5));
            x.setExpDate(rs.getDate(6));
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
     * Gets a BusEntityParameterDataVector object that consists
     * of BusEntityParameterData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for BusEntityParameterData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new BusEntityParameterDataVector()
     * @throws            SQLException
     */
    public static BusEntityParameterDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        BusEntityParameterDataVector v = new BusEntityParameterDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT BUS_ENTITY_PARAMETER_ID,BUS_ENTITY_ID,NAME,CLW_VALUE,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUS_ENTITY_PARAMETER WHERE BUS_ENTITY_PARAMETER_ID IN (");

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
            BusEntityParameterData x=null;
            while (rs.next()) {
                // build the object
                x=BusEntityParameterData.createValue();
                
                x.setBusEntityParameterId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setName(rs.getString(3));
                x.setValue(rs.getString(4));
                x.setEffDate(rs.getDate(5));
                x.setExpDate(rs.getDate(6));
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
     * Gets a BusEntityParameterDataVector object of all
     * BusEntityParameterData objects in the database.
     * @param pCon An open database connection.
     * @return new BusEntityParameterDataVector()
     * @throws            SQLException
     */
    public static BusEntityParameterDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT BUS_ENTITY_PARAMETER_ID,BUS_ENTITY_ID,NAME,CLW_VALUE,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUS_ENTITY_PARAMETER";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        BusEntityParameterDataVector v = new BusEntityParameterDataVector();
        BusEntityParameterData x = null;
        while (rs.next()) {
            // build the object
            x = BusEntityParameterData.createValue();
            
            x.setBusEntityParameterId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setName(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setEffDate(rs.getDate(5));
            x.setExpDate(rs.getDate(6));
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
     * BusEntityParameterData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT BUS_ENTITY_PARAMETER_ID FROM CLW_BUS_ENTITY_PARAMETER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BUS_ENTITY_PARAMETER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BUS_ENTITY_PARAMETER");
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
     * Inserts a BusEntityParameterData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityParameterData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new BusEntityParameterData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BusEntityParameterData insert(Connection pCon, BusEntityParameterData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_BUS_ENTITY_PARAMETER_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_BUS_ENTITY_PARAMETER_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setBusEntityParameterId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_BUS_ENTITY_PARAMETER (BUS_ENTITY_PARAMETER_ID,BUS_ENTITY_ID,NAME,CLW_VALUE,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getBusEntityParameterId());
        pstmt.setInt(2,pData.getBusEntityId());
        pstmt.setString(3,pData.getName());
        pstmt.setString(4,pData.getValue());
        pstmt.setDate(5,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(6,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_PARAMETER_ID="+pData.getBusEntityParameterId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
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
        pData.setBusEntityParameterId(0);
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
     * Updates a BusEntityParameterData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityParameterData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BusEntityParameterData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_BUS_ENTITY_PARAMETER SET BUS_ENTITY_ID = ?,NAME = ?,CLW_VALUE = ?,EFF_DATE = ?,EXP_DATE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE BUS_ENTITY_PARAMETER_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setString(i++,pData.getName());
        pstmt.setString(i++,pData.getValue());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getBusEntityParameterId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
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
     * Deletes a BusEntityParameterData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBusEntityParameterId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBusEntityParameterId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_BUS_ENTITY_PARAMETER WHERE BUS_ENTITY_PARAMETER_ID = " + pBusEntityParameterId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes BusEntityParameterData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_BUS_ENTITY_PARAMETER");
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
     * Inserts a BusEntityParameterData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityParameterData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, BusEntityParameterData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_BUS_ENTITY_PARAMETER (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "BUS_ENTITY_PARAMETER_ID,BUS_ENTITY_ID,NAME,CLW_VALUE,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getBusEntityParameterId());
        pstmt.setInt(2+4,pData.getBusEntityId());
        pstmt.setString(3+4,pData.getName());
        pstmt.setString(4+4,pData.getValue());
        pstmt.setDate(5+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(6+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a BusEntityParameterData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityParameterData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new BusEntityParameterData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BusEntityParameterData insert(Connection pCon, BusEntityParameterData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a BusEntityParameterData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityParameterData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BusEntityParameterData pData, boolean pLogFl)
        throws SQLException {
        BusEntityParameterData oldData = null;
        if(pLogFl) {
          int id = pData.getBusEntityParameterId();
          try {
          oldData = BusEntityParameterDataAccess.select(pCon,id);
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
     * Deletes a BusEntityParameterData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBusEntityParameterId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBusEntityParameterId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_BUS_ENTITY_PARAMETER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BUS_ENTITY_PARAMETER d WHERE BUS_ENTITY_PARAMETER_ID = " + pBusEntityParameterId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pBusEntityParameterId);
        return n;
     }

    /**
     * Deletes BusEntityParameterData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_BUS_ENTITY_PARAMETER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BUS_ENTITY_PARAMETER d ");
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

