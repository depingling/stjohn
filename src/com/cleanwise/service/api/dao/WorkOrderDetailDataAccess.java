
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WorkOrderDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_WORK_ORDER_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WorkOrderDetailData;
import com.cleanwise.service.api.value.WorkOrderDetailDataVector;
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
 * <code>WorkOrderDetailDataAccess</code>
 */
public class WorkOrderDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WorkOrderDetailDataAccess.class.getName());

    /** <code>CLW_WORK_ORDER_DETAIL</code> table name */
	/* Primary key: WORK_ORDER_DETAIL_ID */
	
    public static final String CLW_WORK_ORDER_DETAIL = "CLW_WORK_ORDER_DETAIL";
    
    /** <code>WORK_ORDER_DETAIL_ID</code> WORK_ORDER_DETAIL_ID column of table CLW_WORK_ORDER_DETAIL */
    public static final String WORK_ORDER_DETAIL_ID = "WORK_ORDER_DETAIL_ID";
    /** <code>WORK_ORDER_ID</code> WORK_ORDER_ID column of table CLW_WORK_ORDER_DETAIL */
    public static final String WORK_ORDER_ID = "WORK_ORDER_ID";
    /** <code>LINE_NUM</code> LINE_NUM column of table CLW_WORK_ORDER_DETAIL */
    public static final String LINE_NUM = "LINE_NUM";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_WORK_ORDER_DETAIL */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>PAYMENT_TYPE_CD</code> PAYMENT_TYPE_CD column of table CLW_WORK_ORDER_DETAIL */
    public static final String PAYMENT_TYPE_CD = "PAYMENT_TYPE_CD";
    /** <code>PART_NUMBER</code> PART_NUMBER column of table CLW_WORK_ORDER_DETAIL */
    public static final String PART_NUMBER = "PART_NUMBER";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_WORK_ORDER_DETAIL */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>PART_PRICE</code> PART_PRICE column of table CLW_WORK_ORDER_DETAIL */
    public static final String PART_PRICE = "PART_PRICE";
    /** <code>QUANTITY</code> QUANTITY column of table CLW_WORK_ORDER_DETAIL */
    public static final String QUANTITY = "QUANTITY";
    /** <code>LABOR</code> LABOR column of table CLW_WORK_ORDER_DETAIL */
    public static final String LABOR = "LABOR";
    /** <code>TRAVEL</code> TRAVEL column of table CLW_WORK_ORDER_DETAIL */
    public static final String TRAVEL = "TRAVEL";
    /** <code>COMMENTS</code> COMMENTS column of table CLW_WORK_ORDER_DETAIL */
    public static final String COMMENTS = "COMMENTS";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WORK_ORDER_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WORK_ORDER_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WORK_ORDER_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WORK_ORDER_DETAIL */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public WorkOrderDetailDataAccess()
    {
    }

    /**
     * Gets a WorkOrderDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWorkOrderDetailId The key requested.
     * @return new WorkOrderDetailData()
     * @throws            SQLException
     */
    public static WorkOrderDetailData select(Connection pCon, int pWorkOrderDetailId)
        throws SQLException, DataNotFoundException {
        WorkOrderDetailData x=null;
        String sql="SELECT WORK_ORDER_DETAIL_ID,WORK_ORDER_ID,LINE_NUM,STATUS_CD,PAYMENT_TYPE_CD,PART_NUMBER,SHORT_DESC,PART_PRICE,QUANTITY,LABOR,TRAVEL,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORK_ORDER_DETAIL WHERE WORK_ORDER_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWorkOrderDetailId=" + pWorkOrderDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWorkOrderDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WorkOrderDetailData.createValue();
            
            x.setWorkOrderDetailId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setLineNum(rs.getInt(3));
            x.setStatusCd(rs.getString(4));
            x.setPaymentTypeCd(rs.getString(5));
            x.setPartNumber(rs.getString(6));
            x.setShortDesc(rs.getString(7));
            x.setPartPrice(rs.getBigDecimal(8));
            x.setQuantity(rs.getInt(9));
            x.setLabor(rs.getBigDecimal(10));
            x.setTravel(rs.getBigDecimal(11));
            x.setComments(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WORK_ORDER_DETAIL_ID :" + pWorkOrderDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WorkOrderDetailDataVector object that consists
     * of WorkOrderDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WorkOrderDetailDataVector()
     * @throws            SQLException
     */
    public static WorkOrderDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WorkOrderDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WORK_ORDER_DETAIL.WORK_ORDER_DETAIL_ID,CLW_WORK_ORDER_DETAIL.WORK_ORDER_ID,CLW_WORK_ORDER_DETAIL.LINE_NUM,CLW_WORK_ORDER_DETAIL.STATUS_CD,CLW_WORK_ORDER_DETAIL.PAYMENT_TYPE_CD,CLW_WORK_ORDER_DETAIL.PART_NUMBER,CLW_WORK_ORDER_DETAIL.SHORT_DESC,CLW_WORK_ORDER_DETAIL.PART_PRICE,CLW_WORK_ORDER_DETAIL.QUANTITY,CLW_WORK_ORDER_DETAIL.LABOR,CLW_WORK_ORDER_DETAIL.TRAVEL,CLW_WORK_ORDER_DETAIL.COMMENTS,CLW_WORK_ORDER_DETAIL.ADD_DATE,CLW_WORK_ORDER_DETAIL.ADD_BY,CLW_WORK_ORDER_DETAIL.MOD_DATE,CLW_WORK_ORDER_DETAIL.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WorkOrderDetailData Object.
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
    *@returns a populated WorkOrderDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WorkOrderDetailData x = WorkOrderDetailData.createValue();
         
         x.setWorkOrderDetailId(rs.getInt(1+offset));
         x.setWorkOrderId(rs.getInt(2+offset));
         x.setLineNum(rs.getInt(3+offset));
         x.setStatusCd(rs.getString(4+offset));
         x.setPaymentTypeCd(rs.getString(5+offset));
         x.setPartNumber(rs.getString(6+offset));
         x.setShortDesc(rs.getString(7+offset));
         x.setPartPrice(rs.getBigDecimal(8+offset));
         x.setQuantity(rs.getInt(9+offset));
         x.setLabor(rs.getBigDecimal(10+offset));
         x.setTravel(rs.getBigDecimal(11+offset));
         x.setComments(rs.getString(12+offset));
         x.setAddDate(rs.getTimestamp(13+offset));
         x.setAddBy(rs.getString(14+offset));
         x.setModDate(rs.getTimestamp(15+offset));
         x.setModBy(rs.getString(16+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WorkOrderDetailData Object represents.
    */
    public int getColumnCount(){
        return 16;
    }

    /**
     * Gets a WorkOrderDetailDataVector object that consists
     * of WorkOrderDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WorkOrderDetailDataVector()
     * @throws            SQLException
     */
    public static WorkOrderDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WORK_ORDER_DETAIL_ID,WORK_ORDER_ID,LINE_NUM,STATUS_CD,PAYMENT_TYPE_CD,PART_NUMBER,SHORT_DESC,PART_PRICE,QUANTITY,LABOR,TRAVEL,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORK_ORDER_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WORK_ORDER_DETAIL.WORK_ORDER_DETAIL_ID,CLW_WORK_ORDER_DETAIL.WORK_ORDER_ID,CLW_WORK_ORDER_DETAIL.LINE_NUM,CLW_WORK_ORDER_DETAIL.STATUS_CD,CLW_WORK_ORDER_DETAIL.PAYMENT_TYPE_CD,CLW_WORK_ORDER_DETAIL.PART_NUMBER,CLW_WORK_ORDER_DETAIL.SHORT_DESC,CLW_WORK_ORDER_DETAIL.PART_PRICE,CLW_WORK_ORDER_DETAIL.QUANTITY,CLW_WORK_ORDER_DETAIL.LABOR,CLW_WORK_ORDER_DETAIL.TRAVEL,CLW_WORK_ORDER_DETAIL.COMMENTS,CLW_WORK_ORDER_DETAIL.ADD_DATE,CLW_WORK_ORDER_DETAIL.ADD_BY,CLW_WORK_ORDER_DETAIL.MOD_DATE,CLW_WORK_ORDER_DETAIL.MOD_BY FROM CLW_WORK_ORDER_DETAIL");
                where = pCriteria.getSqlClause("CLW_WORK_ORDER_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORK_ORDER_DETAIL.equals(otherTable)){
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
        WorkOrderDetailDataVector v = new WorkOrderDetailDataVector();
        while (rs.next()) {
            WorkOrderDetailData x = WorkOrderDetailData.createValue();
            
            x.setWorkOrderDetailId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setLineNum(rs.getInt(3));
            x.setStatusCd(rs.getString(4));
            x.setPaymentTypeCd(rs.getString(5));
            x.setPartNumber(rs.getString(6));
            x.setShortDesc(rs.getString(7));
            x.setPartPrice(rs.getBigDecimal(8));
            x.setQuantity(rs.getInt(9));
            x.setLabor(rs.getBigDecimal(10));
            x.setTravel(rs.getBigDecimal(11));
            x.setComments(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a WorkOrderDetailDataVector object that consists
     * of WorkOrderDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WorkOrderDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WorkOrderDetailDataVector()
     * @throws            SQLException
     */
    public static WorkOrderDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WorkOrderDetailDataVector v = new WorkOrderDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WORK_ORDER_DETAIL_ID,WORK_ORDER_ID,LINE_NUM,STATUS_CD,PAYMENT_TYPE_CD,PART_NUMBER,SHORT_DESC,PART_PRICE,QUANTITY,LABOR,TRAVEL,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORK_ORDER_DETAIL WHERE WORK_ORDER_DETAIL_ID IN (");

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
            WorkOrderDetailData x=null;
            while (rs.next()) {
                // build the object
                x=WorkOrderDetailData.createValue();
                
                x.setWorkOrderDetailId(rs.getInt(1));
                x.setWorkOrderId(rs.getInt(2));
                x.setLineNum(rs.getInt(3));
                x.setStatusCd(rs.getString(4));
                x.setPaymentTypeCd(rs.getString(5));
                x.setPartNumber(rs.getString(6));
                x.setShortDesc(rs.getString(7));
                x.setPartPrice(rs.getBigDecimal(8));
                x.setQuantity(rs.getInt(9));
                x.setLabor(rs.getBigDecimal(10));
                x.setTravel(rs.getBigDecimal(11));
                x.setComments(rs.getString(12));
                x.setAddDate(rs.getTimestamp(13));
                x.setAddBy(rs.getString(14));
                x.setModDate(rs.getTimestamp(15));
                x.setModBy(rs.getString(16));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a WorkOrderDetailDataVector object of all
     * WorkOrderDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new WorkOrderDetailDataVector()
     * @throws            SQLException
     */
    public static WorkOrderDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WORK_ORDER_DETAIL_ID,WORK_ORDER_ID,LINE_NUM,STATUS_CD,PAYMENT_TYPE_CD,PART_NUMBER,SHORT_DESC,PART_PRICE,QUANTITY,LABOR,TRAVEL,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORK_ORDER_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WorkOrderDetailDataVector v = new WorkOrderDetailDataVector();
        WorkOrderDetailData x = null;
        while (rs.next()) {
            // build the object
            x = WorkOrderDetailData.createValue();
            
            x.setWorkOrderDetailId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setLineNum(rs.getInt(3));
            x.setStatusCd(rs.getString(4));
            x.setPaymentTypeCd(rs.getString(5));
            x.setPartNumber(rs.getString(6));
            x.setShortDesc(rs.getString(7));
            x.setPartPrice(rs.getBigDecimal(8));
            x.setQuantity(rs.getInt(9));
            x.setLabor(rs.getBigDecimal(10));
            x.setTravel(rs.getBigDecimal(11));
            x.setComments(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * WorkOrderDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WORK_ORDER_DETAIL_ID FROM CLW_WORK_ORDER_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER_DETAIL");
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
     * Inserts a WorkOrderDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WorkOrderDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkOrderDetailData insert(Connection pCon, WorkOrderDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WORK_ORDER_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WORK_ORDER_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWorkOrderDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WORK_ORDER_DETAIL (WORK_ORDER_DETAIL_ID,WORK_ORDER_ID,LINE_NUM,STATUS_CD,PAYMENT_TYPE_CD,PART_NUMBER,SHORT_DESC,PART_PRICE,QUANTITY,LABOR,TRAVEL,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWorkOrderDetailId());
        pstmt.setInt(2,pData.getWorkOrderId());
        pstmt.setInt(3,pData.getLineNum());
        pstmt.setString(4,pData.getStatusCd());
        pstmt.setString(5,pData.getPaymentTypeCd());
        pstmt.setString(6,pData.getPartNumber());
        pstmt.setString(7,pData.getShortDesc());
        pstmt.setBigDecimal(8,pData.getPartPrice());
        pstmt.setInt(9,pData.getQuantity());
        pstmt.setBigDecimal(10,pData.getLabor());
        pstmt.setBigDecimal(11,pData.getTravel());
        pstmt.setString(12,pData.getComments());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14,pData.getAddBy());
        pstmt.setTimestamp(15,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORK_ORDER_DETAIL_ID="+pData.getWorkOrderDetailId());
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   LINE_NUM="+pData.getLineNum());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   PAYMENT_TYPE_CD="+pData.getPaymentTypeCd());
            log.debug("SQL:   PART_NUMBER="+pData.getPartNumber());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   PART_PRICE="+pData.getPartPrice());
            log.debug("SQL:   QUANTITY="+pData.getQuantity());
            log.debug("SQL:   LABOR="+pData.getLabor());
            log.debug("SQL:   TRAVEL="+pData.getTravel());
            log.debug("SQL:   COMMENTS="+pData.getComments());
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
        pData.setWorkOrderDetailId(0);
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
     * Updates a WorkOrderDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkOrderDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WORK_ORDER_DETAIL SET WORK_ORDER_ID = ?,LINE_NUM = ?,STATUS_CD = ?,PAYMENT_TYPE_CD = ?,PART_NUMBER = ?,SHORT_DESC = ?,PART_PRICE = ?,QUANTITY = ?,LABOR = ?,TRAVEL = ?,COMMENTS = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE WORK_ORDER_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getWorkOrderId());
        pstmt.setInt(i++,pData.getLineNum());
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setString(i++,pData.getPaymentTypeCd());
        pstmt.setString(i++,pData.getPartNumber());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setBigDecimal(i++,pData.getPartPrice());
        pstmt.setInt(i++,pData.getQuantity());
        pstmt.setBigDecimal(i++,pData.getLabor());
        pstmt.setBigDecimal(i++,pData.getTravel());
        pstmt.setString(i++,pData.getComments());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getWorkOrderDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   LINE_NUM="+pData.getLineNum());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   PAYMENT_TYPE_CD="+pData.getPaymentTypeCd());
            log.debug("SQL:   PART_NUMBER="+pData.getPartNumber());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   PART_PRICE="+pData.getPartPrice());
            log.debug("SQL:   QUANTITY="+pData.getQuantity());
            log.debug("SQL:   LABOR="+pData.getLabor());
            log.debug("SQL:   TRAVEL="+pData.getTravel());
            log.debug("SQL:   COMMENTS="+pData.getComments());
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
     * Deletes a WorkOrderDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkOrderDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkOrderDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WORK_ORDER_DETAIL WHERE WORK_ORDER_DETAIL_ID = " + pWorkOrderDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WorkOrderDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WORK_ORDER_DETAIL");
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
     * Inserts a WorkOrderDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WorkOrderDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WORK_ORDER_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WORK_ORDER_DETAIL_ID,WORK_ORDER_ID,LINE_NUM,STATUS_CD,PAYMENT_TYPE_CD,PART_NUMBER,SHORT_DESC,PART_PRICE,QUANTITY,LABOR,TRAVEL,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWorkOrderDetailId());
        pstmt.setInt(2+4,pData.getWorkOrderId());
        pstmt.setInt(3+4,pData.getLineNum());
        pstmt.setString(4+4,pData.getStatusCd());
        pstmt.setString(5+4,pData.getPaymentTypeCd());
        pstmt.setString(6+4,pData.getPartNumber());
        pstmt.setString(7+4,pData.getShortDesc());
        pstmt.setBigDecimal(8+4,pData.getPartPrice());
        pstmt.setInt(9+4,pData.getQuantity());
        pstmt.setBigDecimal(10+4,pData.getLabor());
        pstmt.setBigDecimal(11+4,pData.getTravel());
        pstmt.setString(12+4,pData.getComments());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14+4,pData.getAddBy());
        pstmt.setTimestamp(15+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WorkOrderDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WorkOrderDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkOrderDetailData insert(Connection pCon, WorkOrderDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WorkOrderDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkOrderDetailData pData, boolean pLogFl)
        throws SQLException {
        WorkOrderDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getWorkOrderDetailId();
          try {
          oldData = WorkOrderDetailDataAccess.select(pCon,id);
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
     * Deletes a WorkOrderDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkOrderDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkOrderDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WORK_ORDER_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORK_ORDER_DETAIL d WHERE WORK_ORDER_DETAIL_ID = " + pWorkOrderDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWorkOrderDetailId);
        return n;
     }

    /**
     * Deletes WorkOrderDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WORK_ORDER_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORK_ORDER_DETAIL d ");
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

