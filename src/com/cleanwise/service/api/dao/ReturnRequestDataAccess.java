
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ReturnRequestDataAccess
 * Description:  This class is used to build access methods to the CLW_RETURN_REQUEST table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ReturnRequestData;
import com.cleanwise.service.api.value.ReturnRequestDataVector;
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
 * <code>ReturnRequestDataAccess</code>
 */
public class ReturnRequestDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ReturnRequestDataAccess.class.getName());

    /** <code>CLW_RETURN_REQUEST</code> table name */
	/* Primary key: RETURN_REQUEST_ID */
	
    public static final String CLW_RETURN_REQUEST = "CLW_RETURN_REQUEST";
    
    /** <code>RETURN_REQUEST_ID</code> RETURN_REQUEST_ID column of table CLW_RETURN_REQUEST */
    public static final String RETURN_REQUEST_ID = "RETURN_REQUEST_ID";
    /** <code>SENDER_CONTACT_NAME</code> SENDER_CONTACT_NAME column of table CLW_RETURN_REQUEST */
    public static final String SENDER_CONTACT_NAME = "SENDER_CONTACT_NAME";
    /** <code>SENDER_CONTACT_PHONE</code> SENDER_CONTACT_PHONE column of table CLW_RETURN_REQUEST */
    public static final String SENDER_CONTACT_PHONE = "SENDER_CONTACT_PHONE";
    /** <code>REASON</code> REASON column of table CLW_RETURN_REQUEST */
    public static final String REASON = "REASON";
    /** <code>DISTRIBUTOR_INVOICE_NUMBER</code> DISTRIBUTOR_INVOICE_NUMBER column of table CLW_RETURN_REQUEST */
    public static final String DISTRIBUTOR_INVOICE_NUMBER = "DISTRIBUTOR_INVOICE_NUMBER";
    /** <code>DISTRIBUTOR_REF_NUM</code> DISTRIBUTOR_REF_NUM column of table CLW_RETURN_REQUEST */
    public static final String DISTRIBUTOR_REF_NUM = "DISTRIBUTOR_REF_NUM";
    /** <code>RETURN_REQUEST_REF_NUM</code> RETURN_REQUEST_REF_NUM column of table CLW_RETURN_REQUEST */
    public static final String RETURN_REQUEST_REF_NUM = "RETURN_REQUEST_REF_NUM";
    /** <code>PURCHASE_ORDER_ID</code> PURCHASE_ORDER_ID column of table CLW_RETURN_REQUEST */
    public static final String PURCHASE_ORDER_ID = "PURCHASE_ORDER_ID";
    /** <code>RETURN_REQUEST_STATUS</code> RETURN_REQUEST_STATUS column of table CLW_RETURN_REQUEST */
    public static final String RETURN_REQUEST_STATUS = "RETURN_REQUEST_STATUS";
    /** <code>PICKUP_CONTACT_NAME</code> PICKUP_CONTACT_NAME column of table CLW_RETURN_REQUEST */
    public static final String PICKUP_CONTACT_NAME = "PICKUP_CONTACT_NAME";
    /** <code>DATE_REQUEST_RECIEVED</code> DATE_REQUEST_RECIEVED column of table CLW_RETURN_REQUEST */
    public static final String DATE_REQUEST_RECIEVED = "DATE_REQUEST_RECIEVED";
    /** <code>INVOICE_DIST_ID</code> INVOICE_DIST_ID column of table CLW_RETURN_REQUEST */
    public static final String INVOICE_DIST_ID = "INVOICE_DIST_ID";
    /** <code>NOTES_TO_DISTRIBUTOR</code> NOTES_TO_DISTRIBUTOR column of table CLW_RETURN_REQUEST */
    public static final String NOTES_TO_DISTRIBUTOR = "NOTES_TO_DISTRIBUTOR";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_RETURN_REQUEST */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_RETURN_REQUEST */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_RETURN_REQUEST */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_RETURN_REQUEST */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>RETURN_METHOD</code> RETURN_METHOD column of table CLW_RETURN_REQUEST */
    public static final String RETURN_METHOD = "RETURN_METHOD";
    /** <code>PROBLEM</code> PROBLEM column of table CLW_RETURN_REQUEST */
    public static final String PROBLEM = "PROBLEM";

    /**
     * Constructor.
     */
    public ReturnRequestDataAccess()
    {
    }

    /**
     * Gets a ReturnRequestData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pReturnRequestId The key requested.
     * @return new ReturnRequestData()
     * @throws            SQLException
     */
    public static ReturnRequestData select(Connection pCon, int pReturnRequestId)
        throws SQLException, DataNotFoundException {
        ReturnRequestData x=null;
        String sql="SELECT RETURN_REQUEST_ID,SENDER_CONTACT_NAME,SENDER_CONTACT_PHONE,REASON,DISTRIBUTOR_INVOICE_NUMBER,DISTRIBUTOR_REF_NUM,RETURN_REQUEST_REF_NUM,PURCHASE_ORDER_ID,RETURN_REQUEST_STATUS,PICKUP_CONTACT_NAME,DATE_REQUEST_RECIEVED,INVOICE_DIST_ID,NOTES_TO_DISTRIBUTOR,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,RETURN_METHOD,PROBLEM FROM CLW_RETURN_REQUEST WHERE RETURN_REQUEST_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pReturnRequestId=" + pReturnRequestId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pReturnRequestId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ReturnRequestData.createValue();
            
            x.setReturnRequestId(rs.getInt(1));
            x.setSenderContactName(rs.getString(2));
            x.setSenderContactPhone(rs.getString(3));
            x.setReason(rs.getString(4));
            x.setDistributorInvoiceNumber(rs.getString(5));
            x.setDistributorRefNum(rs.getString(6));
            x.setReturnRequestRefNum(rs.getString(7));
            x.setPurchaseOrderId(rs.getInt(8));
            x.setReturnRequestStatus(rs.getString(9));
            x.setPickupContactName(rs.getString(10));
            x.setDateRequestRecieved(rs.getDate(11));
            x.setInvoiceDistId(rs.getInt(12));
            x.setNotesToDistributor(rs.getString(13));
            x.setAddBy(rs.getString(14));
            x.setAddDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setModDate(rs.getTimestamp(17));
            x.setReturnMethod(rs.getString(18));
            x.setProblem(rs.getString(19));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("RETURN_REQUEST_ID :" + pReturnRequestId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ReturnRequestDataVector object that consists
     * of ReturnRequestData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ReturnRequestDataVector()
     * @throws            SQLException
     */
    public static ReturnRequestDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ReturnRequestData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_RETURN_REQUEST.RETURN_REQUEST_ID,CLW_RETURN_REQUEST.SENDER_CONTACT_NAME,CLW_RETURN_REQUEST.SENDER_CONTACT_PHONE,CLW_RETURN_REQUEST.REASON,CLW_RETURN_REQUEST.DISTRIBUTOR_INVOICE_NUMBER,CLW_RETURN_REQUEST.DISTRIBUTOR_REF_NUM,CLW_RETURN_REQUEST.RETURN_REQUEST_REF_NUM,CLW_RETURN_REQUEST.PURCHASE_ORDER_ID,CLW_RETURN_REQUEST.RETURN_REQUEST_STATUS,CLW_RETURN_REQUEST.PICKUP_CONTACT_NAME,CLW_RETURN_REQUEST.DATE_REQUEST_RECIEVED,CLW_RETURN_REQUEST.INVOICE_DIST_ID,CLW_RETURN_REQUEST.NOTES_TO_DISTRIBUTOR,CLW_RETURN_REQUEST.ADD_BY,CLW_RETURN_REQUEST.ADD_DATE,CLW_RETURN_REQUEST.MOD_BY,CLW_RETURN_REQUEST.MOD_DATE,CLW_RETURN_REQUEST.RETURN_METHOD,CLW_RETURN_REQUEST.PROBLEM";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ReturnRequestData Object.
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
    *@returns a populated ReturnRequestData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ReturnRequestData x = ReturnRequestData.createValue();
         
         x.setReturnRequestId(rs.getInt(1+offset));
         x.setSenderContactName(rs.getString(2+offset));
         x.setSenderContactPhone(rs.getString(3+offset));
         x.setReason(rs.getString(4+offset));
         x.setDistributorInvoiceNumber(rs.getString(5+offset));
         x.setDistributorRefNum(rs.getString(6+offset));
         x.setReturnRequestRefNum(rs.getString(7+offset));
         x.setPurchaseOrderId(rs.getInt(8+offset));
         x.setReturnRequestStatus(rs.getString(9+offset));
         x.setPickupContactName(rs.getString(10+offset));
         x.setDateRequestRecieved(rs.getDate(11+offset));
         x.setInvoiceDistId(rs.getInt(12+offset));
         x.setNotesToDistributor(rs.getString(13+offset));
         x.setAddBy(rs.getString(14+offset));
         x.setAddDate(rs.getTimestamp(15+offset));
         x.setModBy(rs.getString(16+offset));
         x.setModDate(rs.getTimestamp(17+offset));
         x.setReturnMethod(rs.getString(18+offset));
         x.setProblem(rs.getString(19+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ReturnRequestData Object represents.
    */
    public int getColumnCount(){
        return 19;
    }

    /**
     * Gets a ReturnRequestDataVector object that consists
     * of ReturnRequestData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ReturnRequestDataVector()
     * @throws            SQLException
     */
    public static ReturnRequestDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT RETURN_REQUEST_ID,SENDER_CONTACT_NAME,SENDER_CONTACT_PHONE,REASON,DISTRIBUTOR_INVOICE_NUMBER,DISTRIBUTOR_REF_NUM,RETURN_REQUEST_REF_NUM,PURCHASE_ORDER_ID,RETURN_REQUEST_STATUS,PICKUP_CONTACT_NAME,DATE_REQUEST_RECIEVED,INVOICE_DIST_ID,NOTES_TO_DISTRIBUTOR,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,RETURN_METHOD,PROBLEM FROM CLW_RETURN_REQUEST");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_RETURN_REQUEST.RETURN_REQUEST_ID,CLW_RETURN_REQUEST.SENDER_CONTACT_NAME,CLW_RETURN_REQUEST.SENDER_CONTACT_PHONE,CLW_RETURN_REQUEST.REASON,CLW_RETURN_REQUEST.DISTRIBUTOR_INVOICE_NUMBER,CLW_RETURN_REQUEST.DISTRIBUTOR_REF_NUM,CLW_RETURN_REQUEST.RETURN_REQUEST_REF_NUM,CLW_RETURN_REQUEST.PURCHASE_ORDER_ID,CLW_RETURN_REQUEST.RETURN_REQUEST_STATUS,CLW_RETURN_REQUEST.PICKUP_CONTACT_NAME,CLW_RETURN_REQUEST.DATE_REQUEST_RECIEVED,CLW_RETURN_REQUEST.INVOICE_DIST_ID,CLW_RETURN_REQUEST.NOTES_TO_DISTRIBUTOR,CLW_RETURN_REQUEST.ADD_BY,CLW_RETURN_REQUEST.ADD_DATE,CLW_RETURN_REQUEST.MOD_BY,CLW_RETURN_REQUEST.MOD_DATE,CLW_RETURN_REQUEST.RETURN_METHOD,CLW_RETURN_REQUEST.PROBLEM FROM CLW_RETURN_REQUEST");
                where = pCriteria.getSqlClause("CLW_RETURN_REQUEST");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_RETURN_REQUEST.equals(otherTable)){
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
        ReturnRequestDataVector v = new ReturnRequestDataVector();
        while (rs.next()) {
            ReturnRequestData x = ReturnRequestData.createValue();
            
            x.setReturnRequestId(rs.getInt(1));
            x.setSenderContactName(rs.getString(2));
            x.setSenderContactPhone(rs.getString(3));
            x.setReason(rs.getString(4));
            x.setDistributorInvoiceNumber(rs.getString(5));
            x.setDistributorRefNum(rs.getString(6));
            x.setReturnRequestRefNum(rs.getString(7));
            x.setPurchaseOrderId(rs.getInt(8));
            x.setReturnRequestStatus(rs.getString(9));
            x.setPickupContactName(rs.getString(10));
            x.setDateRequestRecieved(rs.getDate(11));
            x.setInvoiceDistId(rs.getInt(12));
            x.setNotesToDistributor(rs.getString(13));
            x.setAddBy(rs.getString(14));
            x.setAddDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setModDate(rs.getTimestamp(17));
            x.setReturnMethod(rs.getString(18));
            x.setProblem(rs.getString(19));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ReturnRequestDataVector object that consists
     * of ReturnRequestData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ReturnRequestData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ReturnRequestDataVector()
     * @throws            SQLException
     */
    public static ReturnRequestDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ReturnRequestDataVector v = new ReturnRequestDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT RETURN_REQUEST_ID,SENDER_CONTACT_NAME,SENDER_CONTACT_PHONE,REASON,DISTRIBUTOR_INVOICE_NUMBER,DISTRIBUTOR_REF_NUM,RETURN_REQUEST_REF_NUM,PURCHASE_ORDER_ID,RETURN_REQUEST_STATUS,PICKUP_CONTACT_NAME,DATE_REQUEST_RECIEVED,INVOICE_DIST_ID,NOTES_TO_DISTRIBUTOR,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,RETURN_METHOD,PROBLEM FROM CLW_RETURN_REQUEST WHERE RETURN_REQUEST_ID IN (");

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
            ReturnRequestData x=null;
            while (rs.next()) {
                // build the object
                x=ReturnRequestData.createValue();
                
                x.setReturnRequestId(rs.getInt(1));
                x.setSenderContactName(rs.getString(2));
                x.setSenderContactPhone(rs.getString(3));
                x.setReason(rs.getString(4));
                x.setDistributorInvoiceNumber(rs.getString(5));
                x.setDistributorRefNum(rs.getString(6));
                x.setReturnRequestRefNum(rs.getString(7));
                x.setPurchaseOrderId(rs.getInt(8));
                x.setReturnRequestStatus(rs.getString(9));
                x.setPickupContactName(rs.getString(10));
                x.setDateRequestRecieved(rs.getDate(11));
                x.setInvoiceDistId(rs.getInt(12));
                x.setNotesToDistributor(rs.getString(13));
                x.setAddBy(rs.getString(14));
                x.setAddDate(rs.getTimestamp(15));
                x.setModBy(rs.getString(16));
                x.setModDate(rs.getTimestamp(17));
                x.setReturnMethod(rs.getString(18));
                x.setProblem(rs.getString(19));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ReturnRequestDataVector object of all
     * ReturnRequestData objects in the database.
     * @param pCon An open database connection.
     * @return new ReturnRequestDataVector()
     * @throws            SQLException
     */
    public static ReturnRequestDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT RETURN_REQUEST_ID,SENDER_CONTACT_NAME,SENDER_CONTACT_PHONE,REASON,DISTRIBUTOR_INVOICE_NUMBER,DISTRIBUTOR_REF_NUM,RETURN_REQUEST_REF_NUM,PURCHASE_ORDER_ID,RETURN_REQUEST_STATUS,PICKUP_CONTACT_NAME,DATE_REQUEST_RECIEVED,INVOICE_DIST_ID,NOTES_TO_DISTRIBUTOR,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,RETURN_METHOD,PROBLEM FROM CLW_RETURN_REQUEST";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ReturnRequestDataVector v = new ReturnRequestDataVector();
        ReturnRequestData x = null;
        while (rs.next()) {
            // build the object
            x = ReturnRequestData.createValue();
            
            x.setReturnRequestId(rs.getInt(1));
            x.setSenderContactName(rs.getString(2));
            x.setSenderContactPhone(rs.getString(3));
            x.setReason(rs.getString(4));
            x.setDistributorInvoiceNumber(rs.getString(5));
            x.setDistributorRefNum(rs.getString(6));
            x.setReturnRequestRefNum(rs.getString(7));
            x.setPurchaseOrderId(rs.getInt(8));
            x.setReturnRequestStatus(rs.getString(9));
            x.setPickupContactName(rs.getString(10));
            x.setDateRequestRecieved(rs.getDate(11));
            x.setInvoiceDistId(rs.getInt(12));
            x.setNotesToDistributor(rs.getString(13));
            x.setAddBy(rs.getString(14));
            x.setAddDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setModDate(rs.getTimestamp(17));
            x.setReturnMethod(rs.getString(18));
            x.setProblem(rs.getString(19));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ReturnRequestData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT RETURN_REQUEST_ID FROM CLW_RETURN_REQUEST");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_RETURN_REQUEST");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_RETURN_REQUEST");
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
     * Inserts a ReturnRequestData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReturnRequestData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ReturnRequestData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ReturnRequestData insert(Connection pCon, ReturnRequestData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_RETURN_REQUEST_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_RETURN_REQUEST_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setReturnRequestId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_RETURN_REQUEST (RETURN_REQUEST_ID,SENDER_CONTACT_NAME,SENDER_CONTACT_PHONE,REASON,DISTRIBUTOR_INVOICE_NUMBER,DISTRIBUTOR_REF_NUM,RETURN_REQUEST_REF_NUM,PURCHASE_ORDER_ID,RETURN_REQUEST_STATUS,PICKUP_CONTACT_NAME,DATE_REQUEST_RECIEVED,INVOICE_DIST_ID,NOTES_TO_DISTRIBUTOR,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,RETURN_METHOD,PROBLEM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getReturnRequestId());
        pstmt.setString(2,pData.getSenderContactName());
        pstmt.setString(3,pData.getSenderContactPhone());
        pstmt.setString(4,pData.getReason());
        pstmt.setString(5,pData.getDistributorInvoiceNumber());
        pstmt.setString(6,pData.getDistributorRefNum());
        pstmt.setString(7,pData.getReturnRequestRefNum());
        pstmt.setInt(8,pData.getPurchaseOrderId());
        pstmt.setString(9,pData.getReturnRequestStatus());
        pstmt.setString(10,pData.getPickupContactName());
        pstmt.setDate(11,DBAccess.toSQLDate(pData.getDateRequestRecieved()));
        pstmt.setInt(12,pData.getInvoiceDistId());
        pstmt.setString(13,pData.getNotesToDistributor());
        pstmt.setString(14,pData.getAddBy());
        pstmt.setTimestamp(15,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(16,pData.getModBy());
        pstmt.setTimestamp(17,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(18,pData.getReturnMethod());
        pstmt.setString(19,pData.getProblem());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   RETURN_REQUEST_ID="+pData.getReturnRequestId());
            log.debug("SQL:   SENDER_CONTACT_NAME="+pData.getSenderContactName());
            log.debug("SQL:   SENDER_CONTACT_PHONE="+pData.getSenderContactPhone());
            log.debug("SQL:   REASON="+pData.getReason());
            log.debug("SQL:   DISTRIBUTOR_INVOICE_NUMBER="+pData.getDistributorInvoiceNumber());
            log.debug("SQL:   DISTRIBUTOR_REF_NUM="+pData.getDistributorRefNum());
            log.debug("SQL:   RETURN_REQUEST_REF_NUM="+pData.getReturnRequestRefNum());
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL:   RETURN_REQUEST_STATUS="+pData.getReturnRequestStatus());
            log.debug("SQL:   PICKUP_CONTACT_NAME="+pData.getPickupContactName());
            log.debug("SQL:   DATE_REQUEST_RECIEVED="+pData.getDateRequestRecieved());
            log.debug("SQL:   INVOICE_DIST_ID="+pData.getInvoiceDistId());
            log.debug("SQL:   NOTES_TO_DISTRIBUTOR="+pData.getNotesToDistributor());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   RETURN_METHOD="+pData.getReturnMethod());
            log.debug("SQL:   PROBLEM="+pData.getProblem());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setReturnRequestId(0);
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
     * Updates a ReturnRequestData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ReturnRequestData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ReturnRequestData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_RETURN_REQUEST SET SENDER_CONTACT_NAME = ?,SENDER_CONTACT_PHONE = ?,REASON = ?,DISTRIBUTOR_INVOICE_NUMBER = ?,DISTRIBUTOR_REF_NUM = ?,RETURN_REQUEST_REF_NUM = ?,PURCHASE_ORDER_ID = ?,RETURN_REQUEST_STATUS = ?,PICKUP_CONTACT_NAME = ?,DATE_REQUEST_RECIEVED = ?,INVOICE_DIST_ID = ?,NOTES_TO_DISTRIBUTOR = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ?,RETURN_METHOD = ?,PROBLEM = ? WHERE RETURN_REQUEST_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getSenderContactName());
        pstmt.setString(i++,pData.getSenderContactPhone());
        pstmt.setString(i++,pData.getReason());
        pstmt.setString(i++,pData.getDistributorInvoiceNumber());
        pstmt.setString(i++,pData.getDistributorRefNum());
        pstmt.setString(i++,pData.getReturnRequestRefNum());
        pstmt.setInt(i++,pData.getPurchaseOrderId());
        pstmt.setString(i++,pData.getReturnRequestStatus());
        pstmt.setString(i++,pData.getPickupContactName());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getDateRequestRecieved()));
        pstmt.setInt(i++,pData.getInvoiceDistId());
        pstmt.setString(i++,pData.getNotesToDistributor());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getReturnMethod());
        pstmt.setString(i++,pData.getProblem());
        pstmt.setInt(i++,pData.getReturnRequestId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SENDER_CONTACT_NAME="+pData.getSenderContactName());
            log.debug("SQL:   SENDER_CONTACT_PHONE="+pData.getSenderContactPhone());
            log.debug("SQL:   REASON="+pData.getReason());
            log.debug("SQL:   DISTRIBUTOR_INVOICE_NUMBER="+pData.getDistributorInvoiceNumber());
            log.debug("SQL:   DISTRIBUTOR_REF_NUM="+pData.getDistributorRefNum());
            log.debug("SQL:   RETURN_REQUEST_REF_NUM="+pData.getReturnRequestRefNum());
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL:   RETURN_REQUEST_STATUS="+pData.getReturnRequestStatus());
            log.debug("SQL:   PICKUP_CONTACT_NAME="+pData.getPickupContactName());
            log.debug("SQL:   DATE_REQUEST_RECIEVED="+pData.getDateRequestRecieved());
            log.debug("SQL:   INVOICE_DIST_ID="+pData.getInvoiceDistId());
            log.debug("SQL:   NOTES_TO_DISTRIBUTOR="+pData.getNotesToDistributor());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   RETURN_METHOD="+pData.getReturnMethod());
            log.debug("SQL:   PROBLEM="+pData.getProblem());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ReturnRequestData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pReturnRequestId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pReturnRequestId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_RETURN_REQUEST WHERE RETURN_REQUEST_ID = " + pReturnRequestId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ReturnRequestData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_RETURN_REQUEST");
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
     * Inserts a ReturnRequestData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReturnRequestData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ReturnRequestData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_RETURN_REQUEST (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "RETURN_REQUEST_ID,SENDER_CONTACT_NAME,SENDER_CONTACT_PHONE,REASON,DISTRIBUTOR_INVOICE_NUMBER,DISTRIBUTOR_REF_NUM,RETURN_REQUEST_REF_NUM,PURCHASE_ORDER_ID,RETURN_REQUEST_STATUS,PICKUP_CONTACT_NAME,DATE_REQUEST_RECIEVED,INVOICE_DIST_ID,NOTES_TO_DISTRIBUTOR,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,RETURN_METHOD,PROBLEM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getReturnRequestId());
        pstmt.setString(2+4,pData.getSenderContactName());
        pstmt.setString(3+4,pData.getSenderContactPhone());
        pstmt.setString(4+4,pData.getReason());
        pstmt.setString(5+4,pData.getDistributorInvoiceNumber());
        pstmt.setString(6+4,pData.getDistributorRefNum());
        pstmt.setString(7+4,pData.getReturnRequestRefNum());
        pstmt.setInt(8+4,pData.getPurchaseOrderId());
        pstmt.setString(9+4,pData.getReturnRequestStatus());
        pstmt.setString(10+4,pData.getPickupContactName());
        pstmt.setDate(11+4,DBAccess.toSQLDate(pData.getDateRequestRecieved()));
        pstmt.setInt(12+4,pData.getInvoiceDistId());
        pstmt.setString(13+4,pData.getNotesToDistributor());
        pstmt.setString(14+4,pData.getAddBy());
        pstmt.setTimestamp(15+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(16+4,pData.getModBy());
        pstmt.setTimestamp(17+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(18+4,pData.getReturnMethod());
        pstmt.setString(19+4,pData.getProblem());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ReturnRequestData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReturnRequestData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ReturnRequestData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ReturnRequestData insert(Connection pCon, ReturnRequestData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ReturnRequestData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ReturnRequestData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ReturnRequestData pData, boolean pLogFl)
        throws SQLException {
        ReturnRequestData oldData = null;
        if(pLogFl) {
          int id = pData.getReturnRequestId();
          try {
          oldData = ReturnRequestDataAccess.select(pCon,id);
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
     * Deletes a ReturnRequestData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pReturnRequestId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pReturnRequestId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_RETURN_REQUEST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_RETURN_REQUEST d WHERE RETURN_REQUEST_ID = " + pReturnRequestId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pReturnRequestId);
        return n;
     }

    /**
     * Deletes ReturnRequestData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_RETURN_REQUEST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_RETURN_REQUEST d ");
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

