
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WorkOrderPropertyDataAccess
 * Description:  This class is used to build access methods to the CLW_WORK_ORDER_PROPERTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WorkOrderPropertyData;
import com.cleanwise.service.api.value.WorkOrderPropertyDataVector;
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
 * <code>WorkOrderPropertyDataAccess</code>
 */
public class WorkOrderPropertyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WorkOrderPropertyDataAccess.class.getName());

    /** <code>CLW_WORK_ORDER_PROPERTY</code> table name */
	/* Primary key: WORK_ORDER_PROPERTY_ID */
	
    public static final String CLW_WORK_ORDER_PROPERTY = "CLW_WORK_ORDER_PROPERTY";
    
    /** <code>WORK_ORDER_PROPERTY_ID</code> WORK_ORDER_PROPERTY_ID column of table CLW_WORK_ORDER_PROPERTY */
    public static final String WORK_ORDER_PROPERTY_ID = "WORK_ORDER_PROPERTY_ID";
    /** <code>WORK_ORDER_ID</code> WORK_ORDER_ID column of table CLW_WORK_ORDER_PROPERTY */
    public static final String WORK_ORDER_ID = "WORK_ORDER_ID";
    /** <code>WORK_ORDER_ITEM_ID</code> WORK_ORDER_ITEM_ID column of table CLW_WORK_ORDER_PROPERTY */
    public static final String WORK_ORDER_ITEM_ID = "WORK_ORDER_ITEM_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_WORK_ORDER_PROPERTY */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>PROPERTY_CD</code> PROPERTY_CD column of table CLW_WORK_ORDER_PROPERTY */
    public static final String PROPERTY_CD = "PROPERTY_CD";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_WORK_ORDER_PROPERTY */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_WORK_ORDER_PROPERTY */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WORK_ORDER_PROPERTY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WORK_ORDER_PROPERTY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WORK_ORDER_PROPERTY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WORK_ORDER_PROPERTY */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public WorkOrderPropertyDataAccess()
    {
    }

    /**
     * Gets a WorkOrderPropertyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWorkOrderPropertyId The key requested.
     * @return new WorkOrderPropertyData()
     * @throws            SQLException
     */
    public static WorkOrderPropertyData select(Connection pCon, int pWorkOrderPropertyId)
        throws SQLException, DataNotFoundException {
        WorkOrderPropertyData x=null;
        String sql="SELECT WORK_ORDER_PROPERTY_ID,WORK_ORDER_ID,WORK_ORDER_ITEM_ID,SHORT_DESC,PROPERTY_CD,STATUS_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_WORK_ORDER_PROPERTY WHERE WORK_ORDER_PROPERTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWorkOrderPropertyId=" + pWorkOrderPropertyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWorkOrderPropertyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WorkOrderPropertyData.createValue();
            
            x.setWorkOrderPropertyId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setWorkOrderItemId(rs.getInt(3));
            x.setShortDesc(rs.getString(4));
            x.setPropertyCd(rs.getString(5));
            x.setStatusCd(rs.getString(6));
            x.setValue(rs.getString(7));
            x.setAddBy(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WORK_ORDER_PROPERTY_ID :" + pWorkOrderPropertyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WorkOrderPropertyDataVector object that consists
     * of WorkOrderPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WorkOrderPropertyDataVector()
     * @throws            SQLException
     */
    public static WorkOrderPropertyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WorkOrderPropertyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WORK_ORDER_PROPERTY.WORK_ORDER_PROPERTY_ID,CLW_WORK_ORDER_PROPERTY.WORK_ORDER_ID,CLW_WORK_ORDER_PROPERTY.WORK_ORDER_ITEM_ID,CLW_WORK_ORDER_PROPERTY.SHORT_DESC,CLW_WORK_ORDER_PROPERTY.PROPERTY_CD,CLW_WORK_ORDER_PROPERTY.STATUS_CD,CLW_WORK_ORDER_PROPERTY.CLW_VALUE,CLW_WORK_ORDER_PROPERTY.ADD_BY,CLW_WORK_ORDER_PROPERTY.ADD_DATE,CLW_WORK_ORDER_PROPERTY.MOD_BY,CLW_WORK_ORDER_PROPERTY.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WorkOrderPropertyData Object.
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
    *@returns a populated WorkOrderPropertyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WorkOrderPropertyData x = WorkOrderPropertyData.createValue();
         
         x.setWorkOrderPropertyId(rs.getInt(1+offset));
         x.setWorkOrderId(rs.getInt(2+offset));
         x.setWorkOrderItemId(rs.getInt(3+offset));
         x.setShortDesc(rs.getString(4+offset));
         x.setPropertyCd(rs.getString(5+offset));
         x.setStatusCd(rs.getString(6+offset));
         x.setValue(rs.getString(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WorkOrderPropertyData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a WorkOrderPropertyDataVector object that consists
     * of WorkOrderPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WorkOrderPropertyDataVector()
     * @throws            SQLException
     */
    public static WorkOrderPropertyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WORK_ORDER_PROPERTY_ID,WORK_ORDER_ID,WORK_ORDER_ITEM_ID,SHORT_DESC,PROPERTY_CD,STATUS_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_WORK_ORDER_PROPERTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WORK_ORDER_PROPERTY.WORK_ORDER_PROPERTY_ID,CLW_WORK_ORDER_PROPERTY.WORK_ORDER_ID,CLW_WORK_ORDER_PROPERTY.WORK_ORDER_ITEM_ID,CLW_WORK_ORDER_PROPERTY.SHORT_DESC,CLW_WORK_ORDER_PROPERTY.PROPERTY_CD,CLW_WORK_ORDER_PROPERTY.STATUS_CD,CLW_WORK_ORDER_PROPERTY.CLW_VALUE,CLW_WORK_ORDER_PROPERTY.ADD_BY,CLW_WORK_ORDER_PROPERTY.ADD_DATE,CLW_WORK_ORDER_PROPERTY.MOD_BY,CLW_WORK_ORDER_PROPERTY.MOD_DATE FROM CLW_WORK_ORDER_PROPERTY");
                where = pCriteria.getSqlClause("CLW_WORK_ORDER_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORK_ORDER_PROPERTY.equals(otherTable)){
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
        WorkOrderPropertyDataVector v = new WorkOrderPropertyDataVector();
        while (rs.next()) {
            WorkOrderPropertyData x = WorkOrderPropertyData.createValue();
            
            x.setWorkOrderPropertyId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setWorkOrderItemId(rs.getInt(3));
            x.setShortDesc(rs.getString(4));
            x.setPropertyCd(rs.getString(5));
            x.setStatusCd(rs.getString(6));
            x.setValue(rs.getString(7));
            x.setAddBy(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a WorkOrderPropertyDataVector object that consists
     * of WorkOrderPropertyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WorkOrderPropertyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WorkOrderPropertyDataVector()
     * @throws            SQLException
     */
    public static WorkOrderPropertyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WorkOrderPropertyDataVector v = new WorkOrderPropertyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WORK_ORDER_PROPERTY_ID,WORK_ORDER_ID,WORK_ORDER_ITEM_ID,SHORT_DESC,PROPERTY_CD,STATUS_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_WORK_ORDER_PROPERTY WHERE WORK_ORDER_PROPERTY_ID IN (");

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
            WorkOrderPropertyData x=null;
            while (rs.next()) {
                // build the object
                x=WorkOrderPropertyData.createValue();
                
                x.setWorkOrderPropertyId(rs.getInt(1));
                x.setWorkOrderId(rs.getInt(2));
                x.setWorkOrderItemId(rs.getInt(3));
                x.setShortDesc(rs.getString(4));
                x.setPropertyCd(rs.getString(5));
                x.setStatusCd(rs.getString(6));
                x.setValue(rs.getString(7));
                x.setAddBy(rs.getString(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a WorkOrderPropertyDataVector object of all
     * WorkOrderPropertyData objects in the database.
     * @param pCon An open database connection.
     * @return new WorkOrderPropertyDataVector()
     * @throws            SQLException
     */
    public static WorkOrderPropertyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WORK_ORDER_PROPERTY_ID,WORK_ORDER_ID,WORK_ORDER_ITEM_ID,SHORT_DESC,PROPERTY_CD,STATUS_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_WORK_ORDER_PROPERTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WorkOrderPropertyDataVector v = new WorkOrderPropertyDataVector();
        WorkOrderPropertyData x = null;
        while (rs.next()) {
            // build the object
            x = WorkOrderPropertyData.createValue();
            
            x.setWorkOrderPropertyId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setWorkOrderItemId(rs.getInt(3));
            x.setShortDesc(rs.getString(4));
            x.setPropertyCd(rs.getString(5));
            x.setStatusCd(rs.getString(6));
            x.setValue(rs.getString(7));
            x.setAddBy(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * WorkOrderPropertyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WORK_ORDER_PROPERTY_ID FROM CLW_WORK_ORDER_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER_PROPERTY");
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
     * Inserts a WorkOrderPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WorkOrderPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkOrderPropertyData insert(Connection pCon, WorkOrderPropertyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WORK_ORDER_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WORK_ORDER_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWorkOrderPropertyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WORK_ORDER_PROPERTY (WORK_ORDER_PROPERTY_ID,WORK_ORDER_ID,WORK_ORDER_ITEM_ID,SHORT_DESC,PROPERTY_CD,STATUS_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWorkOrderPropertyId());
        if (pData.getWorkOrderId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getWorkOrderId());
        }

        if (pData.getWorkOrderItemId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getWorkOrderItemId());
        }

        pstmt.setString(4,pData.getShortDesc());
        pstmt.setString(5,pData.getPropertyCd());
        pstmt.setString(6,pData.getStatusCd());
        pstmt.setString(7,pData.getValue());
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getModBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORK_ORDER_PROPERTY_ID="+pData.getWorkOrderPropertyId());
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   WORK_ORDER_ITEM_ID="+pData.getWorkOrderItemId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   PROPERTY_CD="+pData.getPropertyCd());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
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
        pData.setWorkOrderPropertyId(0);
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
     * Updates a WorkOrderPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkOrderPropertyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WORK_ORDER_PROPERTY SET WORK_ORDER_ID = ?,WORK_ORDER_ITEM_ID = ?,SHORT_DESC = ?,PROPERTY_CD = ?,STATUS_CD = ?,CLW_VALUE = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE WORK_ORDER_PROPERTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getWorkOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getWorkOrderId());
        }

        if (pData.getWorkOrderItemId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getWorkOrderItemId());
        }

        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getPropertyCd());
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getWorkOrderPropertyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   WORK_ORDER_ITEM_ID="+pData.getWorkOrderItemId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   PROPERTY_CD="+pData.getPropertyCd());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
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
     * Deletes a WorkOrderPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkOrderPropertyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkOrderPropertyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WORK_ORDER_PROPERTY WHERE WORK_ORDER_PROPERTY_ID = " + pWorkOrderPropertyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WorkOrderPropertyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WORK_ORDER_PROPERTY");
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
     * Inserts a WorkOrderPropertyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderPropertyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WorkOrderPropertyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WORK_ORDER_PROPERTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WORK_ORDER_PROPERTY_ID,WORK_ORDER_ID,WORK_ORDER_ITEM_ID,SHORT_DESC,PROPERTY_CD,STATUS_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWorkOrderPropertyId());
        if (pData.getWorkOrderId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getWorkOrderId());
        }

        if (pData.getWorkOrderItemId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getWorkOrderItemId());
        }

        pstmt.setString(4+4,pData.getShortDesc());
        pstmt.setString(5+4,pData.getPropertyCd());
        pstmt.setString(6+4,pData.getStatusCd());
        pstmt.setString(7+4,pData.getValue());
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getModBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WorkOrderPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WorkOrderPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkOrderPropertyData insert(Connection pCon, WorkOrderPropertyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WorkOrderPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkOrderPropertyData pData, boolean pLogFl)
        throws SQLException {
        WorkOrderPropertyData oldData = null;
        if(pLogFl) {
          int id = pData.getWorkOrderPropertyId();
          try {
          oldData = WorkOrderPropertyDataAccess.select(pCon,id);
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
     * Deletes a WorkOrderPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkOrderPropertyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkOrderPropertyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WORK_ORDER_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORK_ORDER_PROPERTY d WHERE WORK_ORDER_PROPERTY_ID = " + pWorkOrderPropertyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWorkOrderPropertyId);
        return n;
     }

    /**
     * Deletes WorkOrderPropertyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WORK_ORDER_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORK_ORDER_PROPERTY d ");
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

