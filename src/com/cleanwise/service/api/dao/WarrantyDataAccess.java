
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WarrantyDataAccess
 * Description:  This class is used to build access methods to the CLW_WARRANTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WarrantyData;
import com.cleanwise.service.api.value.WarrantyDataVector;
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
 * <code>WarrantyDataAccess</code>
 */
public class WarrantyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WarrantyDataAccess.class.getName());

    /** <code>CLW_WARRANTY</code> table name */
	/* Primary key: WARRANTY_ID */
	
    public static final String CLW_WARRANTY = "CLW_WARRANTY";
    
    /** <code>WARRANTY_ID</code> WARRANTY_ID column of table CLW_WARRANTY */
    public static final String WARRANTY_ID = "WARRANTY_ID";
    /** <code>WARRANTY_NUMBER</code> WARRANTY_NUMBER column of table CLW_WARRANTY */
    public static final String WARRANTY_NUMBER = "WARRANTY_NUMBER";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_WARRANTY */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_WARRANTY */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>BUSINESS_NAME</code> BUSINESS_NAME column of table CLW_WARRANTY */
    public static final String BUSINESS_NAME = "BUSINESS_NAME";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_WARRANTY */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>TYPE_CD</code> TYPE_CD column of table CLW_WARRANTY */
    public static final String TYPE_CD = "TYPE_CD";
    /** <code>DURATION</code> DURATION column of table CLW_WARRANTY */
    public static final String DURATION = "DURATION";
    /** <code>COST</code> COST column of table CLW_WARRANTY */
    public static final String COST = "COST";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WARRANTY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WARRANTY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WARRANTY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WARRANTY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>DURATION_TYPE_CD</code> DURATION_TYPE_CD column of table CLW_WARRANTY */
    public static final String DURATION_TYPE_CD = "DURATION_TYPE_CD";

    /**
     * Constructor.
     */
    public WarrantyDataAccess()
    {
    }

    /**
     * Gets a WarrantyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWarrantyId The key requested.
     * @return new WarrantyData()
     * @throws            SQLException
     */
    public static WarrantyData select(Connection pCon, int pWarrantyId)
        throws SQLException, DataNotFoundException {
        WarrantyData x=null;
        String sql="SELECT WARRANTY_ID,WARRANTY_NUMBER,SHORT_DESC,LONG_DESC,BUSINESS_NAME,STATUS_CD,TYPE_CD,DURATION,COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DURATION_TYPE_CD FROM CLW_WARRANTY WHERE WARRANTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWarrantyId=" + pWarrantyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWarrantyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WarrantyData.createValue();
            
            x.setWarrantyId(rs.getInt(1));
            x.setWarrantyNumber(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setLongDesc(rs.getString(4));
            x.setBusinessName(rs.getString(5));
            x.setStatusCd(rs.getString(6));
            x.setTypeCd(rs.getString(7));
            x.setDuration(rs.getInt(8));
            x.setCost(rs.getBigDecimal(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));
            x.setDurationTypeCd(rs.getString(14));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WARRANTY_ID :" + pWarrantyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WarrantyDataVector object that consists
     * of WarrantyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WarrantyDataVector()
     * @throws            SQLException
     */
    public static WarrantyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WarrantyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WARRANTY.WARRANTY_ID,CLW_WARRANTY.WARRANTY_NUMBER,CLW_WARRANTY.SHORT_DESC,CLW_WARRANTY.LONG_DESC,CLW_WARRANTY.BUSINESS_NAME,CLW_WARRANTY.STATUS_CD,CLW_WARRANTY.TYPE_CD,CLW_WARRANTY.DURATION,CLW_WARRANTY.COST,CLW_WARRANTY.ADD_DATE,CLW_WARRANTY.ADD_BY,CLW_WARRANTY.MOD_DATE,CLW_WARRANTY.MOD_BY,CLW_WARRANTY.DURATION_TYPE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WarrantyData Object.
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
    *@returns a populated WarrantyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WarrantyData x = WarrantyData.createValue();
         
         x.setWarrantyId(rs.getInt(1+offset));
         x.setWarrantyNumber(rs.getString(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setLongDesc(rs.getString(4+offset));
         x.setBusinessName(rs.getString(5+offset));
         x.setStatusCd(rs.getString(6+offset));
         x.setTypeCd(rs.getString(7+offset));
         x.setDuration(rs.getInt(8+offset));
         x.setCost(rs.getBigDecimal(9+offset));
         x.setAddDate(rs.getTimestamp(10+offset));
         x.setAddBy(rs.getString(11+offset));
         x.setModDate(rs.getTimestamp(12+offset));
         x.setModBy(rs.getString(13+offset));
         x.setDurationTypeCd(rs.getString(14+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WarrantyData Object represents.
    */
    public int getColumnCount(){
        return 14;
    }

    /**
     * Gets a WarrantyDataVector object that consists
     * of WarrantyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WarrantyDataVector()
     * @throws            SQLException
     */
    public static WarrantyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WARRANTY_ID,WARRANTY_NUMBER,SHORT_DESC,LONG_DESC,BUSINESS_NAME,STATUS_CD,TYPE_CD,DURATION,COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DURATION_TYPE_CD FROM CLW_WARRANTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WARRANTY.WARRANTY_ID,CLW_WARRANTY.WARRANTY_NUMBER,CLW_WARRANTY.SHORT_DESC,CLW_WARRANTY.LONG_DESC,CLW_WARRANTY.BUSINESS_NAME,CLW_WARRANTY.STATUS_CD,CLW_WARRANTY.TYPE_CD,CLW_WARRANTY.DURATION,CLW_WARRANTY.COST,CLW_WARRANTY.ADD_DATE,CLW_WARRANTY.ADD_BY,CLW_WARRANTY.MOD_DATE,CLW_WARRANTY.MOD_BY,CLW_WARRANTY.DURATION_TYPE_CD FROM CLW_WARRANTY");
                where = pCriteria.getSqlClause("CLW_WARRANTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WARRANTY.equals(otherTable)){
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
        WarrantyDataVector v = new WarrantyDataVector();
        while (rs.next()) {
            WarrantyData x = WarrantyData.createValue();
            
            x.setWarrantyId(rs.getInt(1));
            x.setWarrantyNumber(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setLongDesc(rs.getString(4));
            x.setBusinessName(rs.getString(5));
            x.setStatusCd(rs.getString(6));
            x.setTypeCd(rs.getString(7));
            x.setDuration(rs.getInt(8));
            x.setCost(rs.getBigDecimal(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));
            x.setDurationTypeCd(rs.getString(14));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a WarrantyDataVector object that consists
     * of WarrantyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WarrantyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WarrantyDataVector()
     * @throws            SQLException
     */
    public static WarrantyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WarrantyDataVector v = new WarrantyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WARRANTY_ID,WARRANTY_NUMBER,SHORT_DESC,LONG_DESC,BUSINESS_NAME,STATUS_CD,TYPE_CD,DURATION,COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DURATION_TYPE_CD FROM CLW_WARRANTY WHERE WARRANTY_ID IN (");

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
            WarrantyData x=null;
            while (rs.next()) {
                // build the object
                x=WarrantyData.createValue();
                
                x.setWarrantyId(rs.getInt(1));
                x.setWarrantyNumber(rs.getString(2));
                x.setShortDesc(rs.getString(3));
                x.setLongDesc(rs.getString(4));
                x.setBusinessName(rs.getString(5));
                x.setStatusCd(rs.getString(6));
                x.setTypeCd(rs.getString(7));
                x.setDuration(rs.getInt(8));
                x.setCost(rs.getBigDecimal(9));
                x.setAddDate(rs.getTimestamp(10));
                x.setAddBy(rs.getString(11));
                x.setModDate(rs.getTimestamp(12));
                x.setModBy(rs.getString(13));
                x.setDurationTypeCd(rs.getString(14));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a WarrantyDataVector object of all
     * WarrantyData objects in the database.
     * @param pCon An open database connection.
     * @return new WarrantyDataVector()
     * @throws            SQLException
     */
    public static WarrantyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WARRANTY_ID,WARRANTY_NUMBER,SHORT_DESC,LONG_DESC,BUSINESS_NAME,STATUS_CD,TYPE_CD,DURATION,COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DURATION_TYPE_CD FROM CLW_WARRANTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WarrantyDataVector v = new WarrantyDataVector();
        WarrantyData x = null;
        while (rs.next()) {
            // build the object
            x = WarrantyData.createValue();
            
            x.setWarrantyId(rs.getInt(1));
            x.setWarrantyNumber(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setLongDesc(rs.getString(4));
            x.setBusinessName(rs.getString(5));
            x.setStatusCd(rs.getString(6));
            x.setTypeCd(rs.getString(7));
            x.setDuration(rs.getInt(8));
            x.setCost(rs.getBigDecimal(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));
            x.setDurationTypeCd(rs.getString(14));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * WarrantyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WARRANTY_ID FROM CLW_WARRANTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WARRANTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WARRANTY");
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
     * Inserts a WarrantyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WarrantyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WarrantyData insert(Connection pCon, WarrantyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WARRANTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WARRANTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWarrantyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WARRANTY (WARRANTY_ID,WARRANTY_NUMBER,SHORT_DESC,LONG_DESC,BUSINESS_NAME,STATUS_CD,TYPE_CD,DURATION,COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DURATION_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWarrantyId());
        pstmt.setString(2,pData.getWarrantyNumber());
        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getLongDesc());
        pstmt.setString(5,pData.getBusinessName());
        pstmt.setString(6,pData.getStatusCd());
        pstmt.setString(7,pData.getTypeCd());
        pstmt.setInt(8,pData.getDuration());
        pstmt.setBigDecimal(9,pData.getCost());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11,pData.getAddBy());
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13,pData.getModBy());
        pstmt.setString(14,pData.getDurationTypeCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WARRANTY_ID="+pData.getWarrantyId());
            log.debug("SQL:   WARRANTY_NUMBER="+pData.getWarrantyNumber());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   BUSINESS_NAME="+pData.getBusinessName());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   TYPE_CD="+pData.getTypeCd());
            log.debug("SQL:   DURATION="+pData.getDuration());
            log.debug("SQL:   COST="+pData.getCost());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   DURATION_TYPE_CD="+pData.getDurationTypeCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setWarrantyId(0);
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
     * Updates a WarrantyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WarrantyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WARRANTY SET WARRANTY_NUMBER = ?,SHORT_DESC = ?,LONG_DESC = ?,BUSINESS_NAME = ?,STATUS_CD = ?,TYPE_CD = ?,DURATION = ?,COST = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,DURATION_TYPE_CD = ? WHERE WARRANTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getWarrantyNumber());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setString(i++,pData.getBusinessName());
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setString(i++,pData.getTypeCd());
        pstmt.setInt(i++,pData.getDuration());
        pstmt.setBigDecimal(i++,pData.getCost());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getDurationTypeCd());
        pstmt.setInt(i++,pData.getWarrantyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WARRANTY_NUMBER="+pData.getWarrantyNumber());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   BUSINESS_NAME="+pData.getBusinessName());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   TYPE_CD="+pData.getTypeCd());
            log.debug("SQL:   DURATION="+pData.getDuration());
            log.debug("SQL:   COST="+pData.getCost());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   DURATION_TYPE_CD="+pData.getDurationTypeCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a WarrantyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWarrantyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWarrantyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WARRANTY WHERE WARRANTY_ID = " + pWarrantyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WarrantyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WARRANTY");
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
     * Inserts a WarrantyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WarrantyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WARRANTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WARRANTY_ID,WARRANTY_NUMBER,SHORT_DESC,LONG_DESC,BUSINESS_NAME,STATUS_CD,TYPE_CD,DURATION,COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DURATION_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWarrantyId());
        pstmt.setString(2+4,pData.getWarrantyNumber());
        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getLongDesc());
        pstmt.setString(5+4,pData.getBusinessName());
        pstmt.setString(6+4,pData.getStatusCd());
        pstmt.setString(7+4,pData.getTypeCd());
        pstmt.setInt(8+4,pData.getDuration());
        pstmt.setBigDecimal(9+4,pData.getCost());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11+4,pData.getAddBy());
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13+4,pData.getModBy());
        pstmt.setString(14+4,pData.getDurationTypeCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WarrantyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WarrantyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WarrantyData insert(Connection pCon, WarrantyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WarrantyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WarrantyData pData, boolean pLogFl)
        throws SQLException {
        WarrantyData oldData = null;
        if(pLogFl) {
          int id = pData.getWarrantyId();
          try {
          oldData = WarrantyDataAccess.select(pCon,id);
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
     * Deletes a WarrantyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWarrantyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWarrantyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WARRANTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WARRANTY d WHERE WARRANTY_ID = " + pWarrantyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWarrantyId);
        return n;
     }

    /**
     * Deletes WarrantyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WARRANTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WARRANTY d ");
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

