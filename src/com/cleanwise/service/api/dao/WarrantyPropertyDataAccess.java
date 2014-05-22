
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WarrantyPropertyDataAccess
 * Description:  This class is used to build access methods to the CLW_WARRANTY_PROPERTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WarrantyPropertyData;
import com.cleanwise.service.api.value.WarrantyPropertyDataVector;
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
 * <code>WarrantyPropertyDataAccess</code>
 */
public class WarrantyPropertyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WarrantyPropertyDataAccess.class.getName());

    /** <code>CLW_WARRANTY_PROPERTY</code> table name */
	/* Primary key: WARRANTY_PROPERTY_ID */
	
    public static final String CLW_WARRANTY_PROPERTY = "CLW_WARRANTY_PROPERTY";
    
    /** <code>WARRANTY_PROPERTY_ID</code> WARRANTY_PROPERTY_ID column of table CLW_WARRANTY_PROPERTY */
    public static final String WARRANTY_PROPERTY_ID = "WARRANTY_PROPERTY_ID";
    /** <code>WARRANTY_ID</code> WARRANTY_ID column of table CLW_WARRANTY_PROPERTY */
    public static final String WARRANTY_ID = "WARRANTY_ID";
    /** <code>WARRANTY_PROPERTY_CD</code> WARRANTY_PROPERTY_CD column of table CLW_WARRANTY_PROPERTY */
    public static final String WARRANTY_PROPERTY_CD = "WARRANTY_PROPERTY_CD";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_WARRANTY_PROPERTY */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WARRANTY_PROPERTY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WARRANTY_PROPERTY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WARRANTY_PROPERTY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WARRANTY_PROPERTY */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public WarrantyPropertyDataAccess()
    {
    }

    /**
     * Gets a WarrantyPropertyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWarrantyPropertyId The key requested.
     * @return new WarrantyPropertyData()
     * @throws            SQLException
     */
    public static WarrantyPropertyData select(Connection pCon, int pWarrantyPropertyId)
        throws SQLException, DataNotFoundException {
        WarrantyPropertyData x=null;
        String sql="SELECT WARRANTY_PROPERTY_ID,WARRANTY_ID,WARRANTY_PROPERTY_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_WARRANTY_PROPERTY WHERE WARRANTY_PROPERTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWarrantyPropertyId=" + pWarrantyPropertyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWarrantyPropertyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WarrantyPropertyData.createValue();
            
            x.setWarrantyPropertyId(rs.getInt(1));
            x.setWarrantyId(rs.getInt(2));
            x.setWarrantyPropertyCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setAddBy(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WARRANTY_PROPERTY_ID :" + pWarrantyPropertyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WarrantyPropertyDataVector object that consists
     * of WarrantyPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WarrantyPropertyDataVector()
     * @throws            SQLException
     */
    public static WarrantyPropertyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WarrantyPropertyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WARRANTY_PROPERTY.WARRANTY_PROPERTY_ID,CLW_WARRANTY_PROPERTY.WARRANTY_ID,CLW_WARRANTY_PROPERTY.WARRANTY_PROPERTY_CD,CLW_WARRANTY_PROPERTY.CLW_VALUE,CLW_WARRANTY_PROPERTY.ADD_BY,CLW_WARRANTY_PROPERTY.ADD_DATE,CLW_WARRANTY_PROPERTY.MOD_BY,CLW_WARRANTY_PROPERTY.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WarrantyPropertyData Object.
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
    *@returns a populated WarrantyPropertyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WarrantyPropertyData x = WarrantyPropertyData.createValue();
         
         x.setWarrantyPropertyId(rs.getInt(1+offset));
         x.setWarrantyId(rs.getInt(2+offset));
         x.setWarrantyPropertyCd(rs.getString(3+offset));
         x.setValue(rs.getString(4+offset));
         x.setAddBy(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setModBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WarrantyPropertyData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a WarrantyPropertyDataVector object that consists
     * of WarrantyPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WarrantyPropertyDataVector()
     * @throws            SQLException
     */
    public static WarrantyPropertyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WARRANTY_PROPERTY_ID,WARRANTY_ID,WARRANTY_PROPERTY_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_WARRANTY_PROPERTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WARRANTY_PROPERTY.WARRANTY_PROPERTY_ID,CLW_WARRANTY_PROPERTY.WARRANTY_ID,CLW_WARRANTY_PROPERTY.WARRANTY_PROPERTY_CD,CLW_WARRANTY_PROPERTY.CLW_VALUE,CLW_WARRANTY_PROPERTY.ADD_BY,CLW_WARRANTY_PROPERTY.ADD_DATE,CLW_WARRANTY_PROPERTY.MOD_BY,CLW_WARRANTY_PROPERTY.MOD_DATE FROM CLW_WARRANTY_PROPERTY");
                where = pCriteria.getSqlClause("CLW_WARRANTY_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WARRANTY_PROPERTY.equals(otherTable)){
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
        WarrantyPropertyDataVector v = new WarrantyPropertyDataVector();
        while (rs.next()) {
            WarrantyPropertyData x = WarrantyPropertyData.createValue();
            
            x.setWarrantyPropertyId(rs.getInt(1));
            x.setWarrantyId(rs.getInt(2));
            x.setWarrantyPropertyCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setAddBy(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a WarrantyPropertyDataVector object that consists
     * of WarrantyPropertyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WarrantyPropertyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WarrantyPropertyDataVector()
     * @throws            SQLException
     */
    public static WarrantyPropertyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WarrantyPropertyDataVector v = new WarrantyPropertyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WARRANTY_PROPERTY_ID,WARRANTY_ID,WARRANTY_PROPERTY_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_WARRANTY_PROPERTY WHERE WARRANTY_PROPERTY_ID IN (");

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
            WarrantyPropertyData x=null;
            while (rs.next()) {
                // build the object
                x=WarrantyPropertyData.createValue();
                
                x.setWarrantyPropertyId(rs.getInt(1));
                x.setWarrantyId(rs.getInt(2));
                x.setWarrantyPropertyCd(rs.getString(3));
                x.setValue(rs.getString(4));
                x.setAddBy(rs.getString(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setModBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a WarrantyPropertyDataVector object of all
     * WarrantyPropertyData objects in the database.
     * @param pCon An open database connection.
     * @return new WarrantyPropertyDataVector()
     * @throws            SQLException
     */
    public static WarrantyPropertyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WARRANTY_PROPERTY_ID,WARRANTY_ID,WARRANTY_PROPERTY_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_WARRANTY_PROPERTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WarrantyPropertyDataVector v = new WarrantyPropertyDataVector();
        WarrantyPropertyData x = null;
        while (rs.next()) {
            // build the object
            x = WarrantyPropertyData.createValue();
            
            x.setWarrantyPropertyId(rs.getInt(1));
            x.setWarrantyId(rs.getInt(2));
            x.setWarrantyPropertyCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setAddBy(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * WarrantyPropertyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WARRANTY_PROPERTY_ID FROM CLW_WARRANTY_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WARRANTY_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WARRANTY_PROPERTY");
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
     * Inserts a WarrantyPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WarrantyPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WarrantyPropertyData insert(Connection pCon, WarrantyPropertyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WARRANTY_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WARRANTY_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWarrantyPropertyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WARRANTY_PROPERTY (WARRANTY_PROPERTY_ID,WARRANTY_ID,WARRANTY_PROPERTY_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWarrantyPropertyId());
        pstmt.setInt(2,pData.getWarrantyId());
        pstmt.setString(3,pData.getWarrantyPropertyCd());
        pstmt.setString(4,pData.getValue());
        pstmt.setString(5,pData.getAddBy());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getModBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WARRANTY_PROPERTY_ID="+pData.getWarrantyPropertyId());
            log.debug("SQL:   WARRANTY_ID="+pData.getWarrantyId());
            log.debug("SQL:   WARRANTY_PROPERTY_CD="+pData.getWarrantyPropertyCd());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setWarrantyPropertyId(0);
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
     * Updates a WarrantyPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WarrantyPropertyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WARRANTY_PROPERTY SET WARRANTY_ID = ?,WARRANTY_PROPERTY_CD = ?,CLW_VALUE = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE WARRANTY_PROPERTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getWarrantyId());
        pstmt.setString(i++,pData.getWarrantyPropertyCd());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getWarrantyPropertyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WARRANTY_ID="+pData.getWarrantyId());
            log.debug("SQL:   WARRANTY_PROPERTY_CD="+pData.getWarrantyPropertyCd());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a WarrantyPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWarrantyPropertyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWarrantyPropertyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WARRANTY_PROPERTY WHERE WARRANTY_PROPERTY_ID = " + pWarrantyPropertyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WarrantyPropertyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WARRANTY_PROPERTY");
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
     * Inserts a WarrantyPropertyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyPropertyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WarrantyPropertyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WARRANTY_PROPERTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WARRANTY_PROPERTY_ID,WARRANTY_ID,WARRANTY_PROPERTY_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWarrantyPropertyId());
        pstmt.setInt(2+4,pData.getWarrantyId());
        pstmt.setString(3+4,pData.getWarrantyPropertyCd());
        pstmt.setString(4+4,pData.getValue());
        pstmt.setString(5+4,pData.getAddBy());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getModBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WarrantyPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WarrantyPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WarrantyPropertyData insert(Connection pCon, WarrantyPropertyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WarrantyPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WarrantyPropertyData pData, boolean pLogFl)
        throws SQLException {
        WarrantyPropertyData oldData = null;
        if(pLogFl) {
          int id = pData.getWarrantyPropertyId();
          try {
          oldData = WarrantyPropertyDataAccess.select(pCon,id);
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
     * Deletes a WarrantyPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWarrantyPropertyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWarrantyPropertyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WARRANTY_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WARRANTY_PROPERTY d WHERE WARRANTY_PROPERTY_ID = " + pWarrantyPropertyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWarrantyPropertyId);
        return n;
     }

    /**
     * Deletes WarrantyPropertyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WARRANTY_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WARRANTY_PROPERTY d ");
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

