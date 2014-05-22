
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderScheduleDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_SCHEDULE_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderScheduleDetailData;
import com.cleanwise.service.api.value.OrderScheduleDetailDataVector;
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
 * <code>OrderScheduleDetailDataAccess</code>
 */
public class OrderScheduleDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderScheduleDetailDataAccess.class.getName());

    /** <code>CLW_ORDER_SCHEDULE_DETAIL</code> table name */
	/* Primary key: ORDER_SCHEDULE_DETAIL_ID */
	
    public static final String CLW_ORDER_SCHEDULE_DETAIL = "CLW_ORDER_SCHEDULE_DETAIL";
    
    /** <code>ORDER_SCHEDULE_DETAIL_ID</code> ORDER_SCHEDULE_DETAIL_ID column of table CLW_ORDER_SCHEDULE_DETAIL */
    public static final String ORDER_SCHEDULE_DETAIL_ID = "ORDER_SCHEDULE_DETAIL_ID";
    /** <code>RECORD_STATUS_CD</code> RECORD_STATUS_CD column of table CLW_ORDER_SCHEDULE_DETAIL */
    public static final String RECORD_STATUS_CD = "RECORD_STATUS_CD";
    /** <code>ORDER_SCHEDULE_ID</code> ORDER_SCHEDULE_ID column of table CLW_ORDER_SCHEDULE_DETAIL */
    public static final String ORDER_SCHEDULE_ID = "ORDER_SCHEDULE_ID";
    /** <code>ORDER_SCHEDULE_DETAIL_CD</code> ORDER_SCHEDULE_DETAIL_CD column of table CLW_ORDER_SCHEDULE_DETAIL */
    public static final String ORDER_SCHEDULE_DETAIL_CD = "ORDER_SCHEDULE_DETAIL_CD";
    /** <code>DETAIL</code> DETAIL column of table CLW_ORDER_SCHEDULE_DETAIL */
    public static final String DETAIL = "DETAIL";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_SCHEDULE_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_SCHEDULE_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_SCHEDULE_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_SCHEDULE_DETAIL */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public OrderScheduleDetailDataAccess()
    {
    }

    /**
     * Gets a OrderScheduleDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderScheduleDetailId The key requested.
     * @return new OrderScheduleDetailData()
     * @throws            SQLException
     */
    public static OrderScheduleDetailData select(Connection pCon, int pOrderScheduleDetailId)
        throws SQLException, DataNotFoundException {
        OrderScheduleDetailData x=null;
        String sql="SELECT ORDER_SCHEDULE_DETAIL_ID,RECORD_STATUS_CD,ORDER_SCHEDULE_ID,ORDER_SCHEDULE_DETAIL_CD,DETAIL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_SCHEDULE_DETAIL WHERE ORDER_SCHEDULE_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderScheduleDetailId=" + pOrderScheduleDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderScheduleDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderScheduleDetailData.createValue();
            
            x.setOrderScheduleDetailId(rs.getInt(1));
            x.setRecordStatusCd(rs.getString(2));
            x.setOrderScheduleId(rs.getInt(3));
            x.setOrderScheduleDetailCd(rs.getString(4));
            x.setDetail(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_SCHEDULE_DETAIL_ID :" + pOrderScheduleDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderScheduleDetailDataVector object that consists
     * of OrderScheduleDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderScheduleDetailDataVector()
     * @throws            SQLException
     */
    public static OrderScheduleDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderScheduleDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_SCHEDULE_DETAIL.ORDER_SCHEDULE_DETAIL_ID,CLW_ORDER_SCHEDULE_DETAIL.RECORD_STATUS_CD,CLW_ORDER_SCHEDULE_DETAIL.ORDER_SCHEDULE_ID,CLW_ORDER_SCHEDULE_DETAIL.ORDER_SCHEDULE_DETAIL_CD,CLW_ORDER_SCHEDULE_DETAIL.DETAIL,CLW_ORDER_SCHEDULE_DETAIL.ADD_DATE,CLW_ORDER_SCHEDULE_DETAIL.ADD_BY,CLW_ORDER_SCHEDULE_DETAIL.MOD_DATE,CLW_ORDER_SCHEDULE_DETAIL.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderScheduleDetailData Object.
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
    *@returns a populated OrderScheduleDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderScheduleDetailData x = OrderScheduleDetailData.createValue();
         
         x.setOrderScheduleDetailId(rs.getInt(1+offset));
         x.setRecordStatusCd(rs.getString(2+offset));
         x.setOrderScheduleId(rs.getInt(3+offset));
         x.setOrderScheduleDetailCd(rs.getString(4+offset));
         x.setDetail(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderScheduleDetailData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a OrderScheduleDetailDataVector object that consists
     * of OrderScheduleDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderScheduleDetailDataVector()
     * @throws            SQLException
     */
    public static OrderScheduleDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_SCHEDULE_DETAIL_ID,RECORD_STATUS_CD,ORDER_SCHEDULE_ID,ORDER_SCHEDULE_DETAIL_CD,DETAIL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_SCHEDULE_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_SCHEDULE_DETAIL.ORDER_SCHEDULE_DETAIL_ID,CLW_ORDER_SCHEDULE_DETAIL.RECORD_STATUS_CD,CLW_ORDER_SCHEDULE_DETAIL.ORDER_SCHEDULE_ID,CLW_ORDER_SCHEDULE_DETAIL.ORDER_SCHEDULE_DETAIL_CD,CLW_ORDER_SCHEDULE_DETAIL.DETAIL,CLW_ORDER_SCHEDULE_DETAIL.ADD_DATE,CLW_ORDER_SCHEDULE_DETAIL.ADD_BY,CLW_ORDER_SCHEDULE_DETAIL.MOD_DATE,CLW_ORDER_SCHEDULE_DETAIL.MOD_BY FROM CLW_ORDER_SCHEDULE_DETAIL");
                where = pCriteria.getSqlClause("CLW_ORDER_SCHEDULE_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_SCHEDULE_DETAIL.equals(otherTable)){
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
        OrderScheduleDetailDataVector v = new OrderScheduleDetailDataVector();
        while (rs.next()) {
            OrderScheduleDetailData x = OrderScheduleDetailData.createValue();
            
            x.setOrderScheduleDetailId(rs.getInt(1));
            x.setRecordStatusCd(rs.getString(2));
            x.setOrderScheduleId(rs.getInt(3));
            x.setOrderScheduleDetailCd(rs.getString(4));
            x.setDetail(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderScheduleDetailDataVector object that consists
     * of OrderScheduleDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderScheduleDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderScheduleDetailDataVector()
     * @throws            SQLException
     */
    public static OrderScheduleDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderScheduleDetailDataVector v = new OrderScheduleDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_SCHEDULE_DETAIL_ID,RECORD_STATUS_CD,ORDER_SCHEDULE_ID,ORDER_SCHEDULE_DETAIL_CD,DETAIL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_SCHEDULE_DETAIL WHERE ORDER_SCHEDULE_DETAIL_ID IN (");

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
            OrderScheduleDetailData x=null;
            while (rs.next()) {
                // build the object
                x=OrderScheduleDetailData.createValue();
                
                x.setOrderScheduleDetailId(rs.getInt(1));
                x.setRecordStatusCd(rs.getString(2));
                x.setOrderScheduleId(rs.getInt(3));
                x.setOrderScheduleDetailCd(rs.getString(4));
                x.setDetail(rs.getString(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setAddBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderScheduleDetailDataVector object of all
     * OrderScheduleDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderScheduleDetailDataVector()
     * @throws            SQLException
     */
    public static OrderScheduleDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_SCHEDULE_DETAIL_ID,RECORD_STATUS_CD,ORDER_SCHEDULE_ID,ORDER_SCHEDULE_DETAIL_CD,DETAIL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_SCHEDULE_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderScheduleDetailDataVector v = new OrderScheduleDetailDataVector();
        OrderScheduleDetailData x = null;
        while (rs.next()) {
            // build the object
            x = OrderScheduleDetailData.createValue();
            
            x.setOrderScheduleDetailId(rs.getInt(1));
            x.setRecordStatusCd(rs.getString(2));
            x.setOrderScheduleId(rs.getInt(3));
            x.setOrderScheduleDetailCd(rs.getString(4));
            x.setDetail(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderScheduleDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_SCHEDULE_DETAIL_ID FROM CLW_ORDER_SCHEDULE_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_SCHEDULE_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_SCHEDULE_DETAIL");
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
     * Inserts a OrderScheduleDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderScheduleDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderScheduleDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderScheduleDetailData insert(Connection pCon, OrderScheduleDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_SCHEDULE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_SCHEDULE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderScheduleDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_SCHEDULE_DETAIL (ORDER_SCHEDULE_DETAIL_ID,RECORD_STATUS_CD,ORDER_SCHEDULE_ID,ORDER_SCHEDULE_DETAIL_CD,DETAIL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderScheduleDetailId());
        pstmt.setString(2,pData.getRecordStatusCd());
        pstmt.setInt(3,pData.getOrderScheduleId());
        pstmt.setString(4,pData.getOrderScheduleDetailCd());
        pstmt.setString(5,pData.getDetail());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_SCHEDULE_DETAIL_ID="+pData.getOrderScheduleDetailId());
            log.debug("SQL:   RECORD_STATUS_CD="+pData.getRecordStatusCd());
            log.debug("SQL:   ORDER_SCHEDULE_ID="+pData.getOrderScheduleId());
            log.debug("SQL:   ORDER_SCHEDULE_DETAIL_CD="+pData.getOrderScheduleDetailCd());
            log.debug("SQL:   DETAIL="+pData.getDetail());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setOrderScheduleDetailId(0);
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
     * Updates a OrderScheduleDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderScheduleDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderScheduleDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_SCHEDULE_DETAIL SET RECORD_STATUS_CD = ?,ORDER_SCHEDULE_ID = ?,ORDER_SCHEDULE_DETAIL_CD = ?,DETAIL = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ORDER_SCHEDULE_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getRecordStatusCd());
        pstmt.setInt(i++,pData.getOrderScheduleId());
        pstmt.setString(i++,pData.getOrderScheduleDetailCd());
        pstmt.setString(i++,pData.getDetail());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getOrderScheduleDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   RECORD_STATUS_CD="+pData.getRecordStatusCd());
            log.debug("SQL:   ORDER_SCHEDULE_ID="+pData.getOrderScheduleId());
            log.debug("SQL:   ORDER_SCHEDULE_DETAIL_CD="+pData.getOrderScheduleDetailCd());
            log.debug("SQL:   DETAIL="+pData.getDetail());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a OrderScheduleDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderScheduleDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderScheduleDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_SCHEDULE_DETAIL WHERE ORDER_SCHEDULE_DETAIL_ID = " + pOrderScheduleDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderScheduleDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_SCHEDULE_DETAIL");
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
     * Inserts a OrderScheduleDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderScheduleDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderScheduleDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_SCHEDULE_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_SCHEDULE_DETAIL_ID,RECORD_STATUS_CD,ORDER_SCHEDULE_ID,ORDER_SCHEDULE_DETAIL_CD,DETAIL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderScheduleDetailId());
        pstmt.setString(2+4,pData.getRecordStatusCd());
        pstmt.setInt(3+4,pData.getOrderScheduleId());
        pstmt.setString(4+4,pData.getOrderScheduleDetailCd());
        pstmt.setString(5+4,pData.getDetail());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderScheduleDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderScheduleDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderScheduleDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderScheduleDetailData insert(Connection pCon, OrderScheduleDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderScheduleDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderScheduleDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderScheduleDetailData pData, boolean pLogFl)
        throws SQLException {
        OrderScheduleDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderScheduleDetailId();
          try {
          oldData = OrderScheduleDetailDataAccess.select(pCon,id);
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
     * Deletes a OrderScheduleDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderScheduleDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderScheduleDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_SCHEDULE_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_SCHEDULE_DETAIL d WHERE ORDER_SCHEDULE_DETAIL_ID = " + pOrderScheduleDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderScheduleDetailId);
        return n;
     }

    /**
     * Deletes OrderScheduleDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_SCHEDULE_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_SCHEDULE_DETAIL d ");
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

