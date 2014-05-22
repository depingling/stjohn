
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CallDataAccess
 * Description:  This class is used to build access methods to the CLW_CALL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CallData;
import com.cleanwise.service.api.value.CallDataVector;
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
 * <code>CallDataAccess</code>
 */
public class CallDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CallDataAccess.class.getName());

    /** <code>CLW_CALL</code> table name */
	/* Primary key: CALL_ID */
	
    public static final String CLW_CALL = "CLW_CALL";
    
    /** <code>CALL_ID</code> CALL_ID column of table CLW_CALL */
    public static final String CALL_ID = "CALL_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_CALL */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>ACCOUNT_ID</code> ACCOUNT_ID column of table CLW_CALL */
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    /** <code>SITE_ID</code> SITE_ID column of table CLW_CALL */
    public static final String SITE_ID = "SITE_ID";
    /** <code>CONTACT_NAME</code> CONTACT_NAME column of table CLW_CALL */
    public static final String CONTACT_NAME = "CONTACT_NAME";
    /** <code>CONTACT_EMAIL_ADDRESS</code> CONTACT_EMAIL_ADDRESS column of table CLW_CALL */
    public static final String CONTACT_EMAIL_ADDRESS = "CONTACT_EMAIL_ADDRESS";
    /** <code>CONTACT_PHONE_NUMBER</code> CONTACT_PHONE_NUMBER column of table CLW_CALL */
    public static final String CONTACT_PHONE_NUMBER = "CONTACT_PHONE_NUMBER";
    /** <code>CUSTOMER_FIELD_1</code> CUSTOMER_FIELD_1 column of table CLW_CALL */
    public static final String CUSTOMER_FIELD_1 = "CUSTOMER_FIELD_1";
    /** <code>PRODUCT_NAME</code> PRODUCT_NAME column of table CLW_CALL */
    public static final String PRODUCT_NAME = "PRODUCT_NAME";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_CALL */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_CALL */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>COMMENTS</code> COMMENTS column of table CLW_CALL */
    public static final String COMMENTS = "COMMENTS";
    /** <code>CALL_STATUS_CD</code> CALL_STATUS_CD column of table CLW_CALL */
    public static final String CALL_STATUS_CD = "CALL_STATUS_CD";
    /** <code>CALL_TYPE_CD</code> CALL_TYPE_CD column of table CLW_CALL */
    public static final String CALL_TYPE_CD = "CALL_TYPE_CD";
    /** <code>CALL_SEVERITY_CD</code> CALL_SEVERITY_CD column of table CLW_CALL */
    public static final String CALL_SEVERITY_CD = "CALL_SEVERITY_CD";
    /** <code>OPENED_BY_ID</code> OPENED_BY_ID column of table CLW_CALL */
    public static final String OPENED_BY_ID = "OPENED_BY_ID";
    /** <code>ASSIGNED_TO_ID</code> ASSIGNED_TO_ID column of table CLW_CALL */
    public static final String ASSIGNED_TO_ID = "ASSIGNED_TO_ID";
    /** <code>CLOSED_DATE</code> CLOSED_DATE column of table CLW_CALL */
    public static final String CLOSED_DATE = "CLOSED_DATE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CALL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_TIME</code> ADD_TIME column of table CLW_CALL */
    public static final String ADD_TIME = "ADD_TIME";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CALL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CALL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CALL */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public CallDataAccess()
    {
    }

    /**
     * Gets a CallData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCallId The key requested.
     * @return new CallData()
     * @throws            SQLException
     */
    public static CallData select(Connection pCon, int pCallId)
        throws SQLException, DataNotFoundException {
        CallData x=null;
        String sql="SELECT CALL_ID,ORDER_ID,ACCOUNT_ID,SITE_ID,CONTACT_NAME,CONTACT_EMAIL_ADDRESS,CONTACT_PHONE_NUMBER,CUSTOMER_FIELD_1,PRODUCT_NAME,SHORT_DESC,LONG_DESC,COMMENTS,CALL_STATUS_CD,CALL_TYPE_CD,CALL_SEVERITY_CD,OPENED_BY_ID,ASSIGNED_TO_ID,CLOSED_DATE,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CALL WHERE CALL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCallId=" + pCallId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCallId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CallData.createValue();
            
            x.setCallId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setAccountId(rs.getInt(3));
            x.setSiteId(rs.getInt(4));
            x.setContactName(rs.getString(5));
            x.setContactEmailAddress(rs.getString(6));
            x.setContactPhoneNumber(rs.getString(7));
            x.setCustomerField1(rs.getString(8));
            x.setProductName(rs.getString(9));
            x.setShortDesc(rs.getString(10));
            x.setLongDesc(rs.getString(11));
            x.setComments(rs.getString(12));
            x.setCallStatusCd(rs.getString(13));
            x.setCallTypeCd(rs.getString(14));
            x.setCallSeverityCd(rs.getString(15));
            x.setOpenedById(rs.getInt(16));
            x.setAssignedToId(rs.getInt(17));
            x.setClosedDate(rs.getDate(18));
            x.setAddDate(rs.getTimestamp(19));
            x.setAddTime(rs.getTimestamp(20));
            x.setAddBy(rs.getString(21));
            x.setModDate(rs.getTimestamp(22));
            x.setModBy(rs.getString(23));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CALL_ID :" + pCallId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CallDataVector object that consists
     * of CallData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CallDataVector()
     * @throws            SQLException
     */
    public static CallDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CallData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CALL.CALL_ID,CLW_CALL.ORDER_ID,CLW_CALL.ACCOUNT_ID,CLW_CALL.SITE_ID,CLW_CALL.CONTACT_NAME,CLW_CALL.CONTACT_EMAIL_ADDRESS,CLW_CALL.CONTACT_PHONE_NUMBER,CLW_CALL.CUSTOMER_FIELD_1,CLW_CALL.PRODUCT_NAME,CLW_CALL.SHORT_DESC,CLW_CALL.LONG_DESC,CLW_CALL.COMMENTS,CLW_CALL.CALL_STATUS_CD,CLW_CALL.CALL_TYPE_CD,CLW_CALL.CALL_SEVERITY_CD,CLW_CALL.OPENED_BY_ID,CLW_CALL.ASSIGNED_TO_ID,CLW_CALL.CLOSED_DATE,CLW_CALL.ADD_DATE,CLW_CALL.ADD_TIME,CLW_CALL.ADD_BY,CLW_CALL.MOD_DATE,CLW_CALL.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CallData Object.
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
    *@returns a populated CallData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CallData x = CallData.createValue();
         
         x.setCallId(rs.getInt(1+offset));
         x.setOrderId(rs.getInt(2+offset));
         x.setAccountId(rs.getInt(3+offset));
         x.setSiteId(rs.getInt(4+offset));
         x.setContactName(rs.getString(5+offset));
         x.setContactEmailAddress(rs.getString(6+offset));
         x.setContactPhoneNumber(rs.getString(7+offset));
         x.setCustomerField1(rs.getString(8+offset));
         x.setProductName(rs.getString(9+offset));
         x.setShortDesc(rs.getString(10+offset));
         x.setLongDesc(rs.getString(11+offset));
         x.setComments(rs.getString(12+offset));
         x.setCallStatusCd(rs.getString(13+offset));
         x.setCallTypeCd(rs.getString(14+offset));
         x.setCallSeverityCd(rs.getString(15+offset));
         x.setOpenedById(rs.getInt(16+offset));
         x.setAssignedToId(rs.getInt(17+offset));
         x.setClosedDate(rs.getDate(18+offset));
         x.setAddDate(rs.getTimestamp(19+offset));
         x.setAddTime(rs.getTimestamp(20+offset));
         x.setAddBy(rs.getString(21+offset));
         x.setModDate(rs.getTimestamp(22+offset));
         x.setModBy(rs.getString(23+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CallData Object represents.
    */
    public int getColumnCount(){
        return 23;
    }

    /**
     * Gets a CallDataVector object that consists
     * of CallData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CallDataVector()
     * @throws            SQLException
     */
    public static CallDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CALL_ID,ORDER_ID,ACCOUNT_ID,SITE_ID,CONTACT_NAME,CONTACT_EMAIL_ADDRESS,CONTACT_PHONE_NUMBER,CUSTOMER_FIELD_1,PRODUCT_NAME,SHORT_DESC,LONG_DESC,COMMENTS,CALL_STATUS_CD,CALL_TYPE_CD,CALL_SEVERITY_CD,OPENED_BY_ID,ASSIGNED_TO_ID,CLOSED_DATE,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CALL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CALL.CALL_ID,CLW_CALL.ORDER_ID,CLW_CALL.ACCOUNT_ID,CLW_CALL.SITE_ID,CLW_CALL.CONTACT_NAME,CLW_CALL.CONTACT_EMAIL_ADDRESS,CLW_CALL.CONTACT_PHONE_NUMBER,CLW_CALL.CUSTOMER_FIELD_1,CLW_CALL.PRODUCT_NAME,CLW_CALL.SHORT_DESC,CLW_CALL.LONG_DESC,CLW_CALL.COMMENTS,CLW_CALL.CALL_STATUS_CD,CLW_CALL.CALL_TYPE_CD,CLW_CALL.CALL_SEVERITY_CD,CLW_CALL.OPENED_BY_ID,CLW_CALL.ASSIGNED_TO_ID,CLW_CALL.CLOSED_DATE,CLW_CALL.ADD_DATE,CLW_CALL.ADD_TIME,CLW_CALL.ADD_BY,CLW_CALL.MOD_DATE,CLW_CALL.MOD_BY FROM CLW_CALL");
                where = pCriteria.getSqlClause("CLW_CALL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CALL.equals(otherTable)){
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
        CallDataVector v = new CallDataVector();
        while (rs.next()) {
            CallData x = CallData.createValue();
            
            x.setCallId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setAccountId(rs.getInt(3));
            x.setSiteId(rs.getInt(4));
            x.setContactName(rs.getString(5));
            x.setContactEmailAddress(rs.getString(6));
            x.setContactPhoneNumber(rs.getString(7));
            x.setCustomerField1(rs.getString(8));
            x.setProductName(rs.getString(9));
            x.setShortDesc(rs.getString(10));
            x.setLongDesc(rs.getString(11));
            x.setComments(rs.getString(12));
            x.setCallStatusCd(rs.getString(13));
            x.setCallTypeCd(rs.getString(14));
            x.setCallSeverityCd(rs.getString(15));
            x.setOpenedById(rs.getInt(16));
            x.setAssignedToId(rs.getInt(17));
            x.setClosedDate(rs.getDate(18));
            x.setAddDate(rs.getTimestamp(19));
            x.setAddTime(rs.getTimestamp(20));
            x.setAddBy(rs.getString(21));
            x.setModDate(rs.getTimestamp(22));
            x.setModBy(rs.getString(23));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a CallDataVector object that consists
     * of CallData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CallData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CallDataVector()
     * @throws            SQLException
     */
    public static CallDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CallDataVector v = new CallDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CALL_ID,ORDER_ID,ACCOUNT_ID,SITE_ID,CONTACT_NAME,CONTACT_EMAIL_ADDRESS,CONTACT_PHONE_NUMBER,CUSTOMER_FIELD_1,PRODUCT_NAME,SHORT_DESC,LONG_DESC,COMMENTS,CALL_STATUS_CD,CALL_TYPE_CD,CALL_SEVERITY_CD,OPENED_BY_ID,ASSIGNED_TO_ID,CLOSED_DATE,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CALL WHERE CALL_ID IN (");

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
            CallData x=null;
            while (rs.next()) {
                // build the object
                x=CallData.createValue();
                
                x.setCallId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setAccountId(rs.getInt(3));
                x.setSiteId(rs.getInt(4));
                x.setContactName(rs.getString(5));
                x.setContactEmailAddress(rs.getString(6));
                x.setContactPhoneNumber(rs.getString(7));
                x.setCustomerField1(rs.getString(8));
                x.setProductName(rs.getString(9));
                x.setShortDesc(rs.getString(10));
                x.setLongDesc(rs.getString(11));
                x.setComments(rs.getString(12));
                x.setCallStatusCd(rs.getString(13));
                x.setCallTypeCd(rs.getString(14));
                x.setCallSeverityCd(rs.getString(15));
                x.setOpenedById(rs.getInt(16));
                x.setAssignedToId(rs.getInt(17));
                x.setClosedDate(rs.getDate(18));
                x.setAddDate(rs.getTimestamp(19));
                x.setAddTime(rs.getTimestamp(20));
                x.setAddBy(rs.getString(21));
                x.setModDate(rs.getTimestamp(22));
                x.setModBy(rs.getString(23));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a CallDataVector object of all
     * CallData objects in the database.
     * @param pCon An open database connection.
     * @return new CallDataVector()
     * @throws            SQLException
     */
    public static CallDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CALL_ID,ORDER_ID,ACCOUNT_ID,SITE_ID,CONTACT_NAME,CONTACT_EMAIL_ADDRESS,CONTACT_PHONE_NUMBER,CUSTOMER_FIELD_1,PRODUCT_NAME,SHORT_DESC,LONG_DESC,COMMENTS,CALL_STATUS_CD,CALL_TYPE_CD,CALL_SEVERITY_CD,OPENED_BY_ID,ASSIGNED_TO_ID,CLOSED_DATE,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CALL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CallDataVector v = new CallDataVector();
        CallData x = null;
        while (rs.next()) {
            // build the object
            x = CallData.createValue();
            
            x.setCallId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setAccountId(rs.getInt(3));
            x.setSiteId(rs.getInt(4));
            x.setContactName(rs.getString(5));
            x.setContactEmailAddress(rs.getString(6));
            x.setContactPhoneNumber(rs.getString(7));
            x.setCustomerField1(rs.getString(8));
            x.setProductName(rs.getString(9));
            x.setShortDesc(rs.getString(10));
            x.setLongDesc(rs.getString(11));
            x.setComments(rs.getString(12));
            x.setCallStatusCd(rs.getString(13));
            x.setCallTypeCd(rs.getString(14));
            x.setCallSeverityCd(rs.getString(15));
            x.setOpenedById(rs.getInt(16));
            x.setAssignedToId(rs.getInt(17));
            x.setClosedDate(rs.getDate(18));
            x.setAddDate(rs.getTimestamp(19));
            x.setAddTime(rs.getTimestamp(20));
            x.setAddBy(rs.getString(21));
            x.setModDate(rs.getTimestamp(22));
            x.setModBy(rs.getString(23));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * CallData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CALL_ID FROM CLW_CALL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CALL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CALL");
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
     * Inserts a CallData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CallData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CallData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CallData insert(Connection pCon, CallData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CALL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CALL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCallId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CALL (CALL_ID,ORDER_ID,ACCOUNT_ID,SITE_ID,CONTACT_NAME,CONTACT_EMAIL_ADDRESS,CONTACT_PHONE_NUMBER,CUSTOMER_FIELD_1,PRODUCT_NAME,SHORT_DESC,LONG_DESC,COMMENTS,CALL_STATUS_CD,CALL_TYPE_CD,CALL_SEVERITY_CD,OPENED_BY_ID,ASSIGNED_TO_ID,CLOSED_DATE,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCallId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getOrderId());
        }

        if (pData.getAccountId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getAccountId());
        }

        if (pData.getSiteId() == 0) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4,pData.getSiteId());
        }

        pstmt.setString(5,pData.getContactName());
        pstmt.setString(6,pData.getContactEmailAddress());
        pstmt.setString(7,pData.getContactPhoneNumber());
        pstmt.setString(8,pData.getCustomerField1());
        pstmt.setString(9,pData.getProductName());
        pstmt.setString(10,pData.getShortDesc());
        pstmt.setString(11,pData.getLongDesc());
        pstmt.setString(12,pData.getComments());
        pstmt.setString(13,pData.getCallStatusCd());
        pstmt.setString(14,pData.getCallTypeCd());
        pstmt.setString(15,pData.getCallSeverityCd());
        pstmt.setInt(16,pData.getOpenedById());
        pstmt.setInt(17,pData.getAssignedToId());
        pstmt.setDate(18,DBAccess.toSQLDate(pData.getClosedDate()));
        pstmt.setTimestamp(19,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(20,DBAccess.toSQLTimestamp(pData.getAddTime()));
        pstmt.setString(21,pData.getAddBy());
        pstmt.setTimestamp(22,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(23,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CALL_ID="+pData.getCallId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   CONTACT_NAME="+pData.getContactName());
            log.debug("SQL:   CONTACT_EMAIL_ADDRESS="+pData.getContactEmailAddress());
            log.debug("SQL:   CONTACT_PHONE_NUMBER="+pData.getContactPhoneNumber());
            log.debug("SQL:   CUSTOMER_FIELD_1="+pData.getCustomerField1());
            log.debug("SQL:   PRODUCT_NAME="+pData.getProductName());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   COMMENTS="+pData.getComments());
            log.debug("SQL:   CALL_STATUS_CD="+pData.getCallStatusCd());
            log.debug("SQL:   CALL_TYPE_CD="+pData.getCallTypeCd());
            log.debug("SQL:   CALL_SEVERITY_CD="+pData.getCallSeverityCd());
            log.debug("SQL:   OPENED_BY_ID="+pData.getOpenedById());
            log.debug("SQL:   ASSIGNED_TO_ID="+pData.getAssignedToId());
            log.debug("SQL:   CLOSED_DATE="+pData.getClosedDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_TIME="+pData.getAddTime());
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
        pData.setCallId(0);
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
     * Updates a CallData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CallData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CallData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CALL SET ORDER_ID = ?,ACCOUNT_ID = ?,SITE_ID = ?,CONTACT_NAME = ?,CONTACT_EMAIL_ADDRESS = ?,CONTACT_PHONE_NUMBER = ?,CUSTOMER_FIELD_1 = ?,PRODUCT_NAME = ?,SHORT_DESC = ?,LONG_DESC = ?,COMMENTS = ?,CALL_STATUS_CD = ?,CALL_TYPE_CD = ?,CALL_SEVERITY_CD = ?,OPENED_BY_ID = ?,ASSIGNED_TO_ID = ?,CLOSED_DATE = ?,ADD_DATE = ?,ADD_TIME = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE CALL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getOrderId());
        }

        if (pData.getAccountId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getAccountId());
        }

        if (pData.getSiteId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getSiteId());
        }

        pstmt.setString(i++,pData.getContactName());
        pstmt.setString(i++,pData.getContactEmailAddress());
        pstmt.setString(i++,pData.getContactPhoneNumber());
        pstmt.setString(i++,pData.getCustomerField1());
        pstmt.setString(i++,pData.getProductName());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setString(i++,pData.getComments());
        pstmt.setString(i++,pData.getCallStatusCd());
        pstmt.setString(i++,pData.getCallTypeCd());
        pstmt.setString(i++,pData.getCallSeverityCd());
        pstmt.setInt(i++,pData.getOpenedById());
        pstmt.setInt(i++,pData.getAssignedToId());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getClosedDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddTime()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getCallId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   CONTACT_NAME="+pData.getContactName());
            log.debug("SQL:   CONTACT_EMAIL_ADDRESS="+pData.getContactEmailAddress());
            log.debug("SQL:   CONTACT_PHONE_NUMBER="+pData.getContactPhoneNumber());
            log.debug("SQL:   CUSTOMER_FIELD_1="+pData.getCustomerField1());
            log.debug("SQL:   PRODUCT_NAME="+pData.getProductName());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   COMMENTS="+pData.getComments());
            log.debug("SQL:   CALL_STATUS_CD="+pData.getCallStatusCd());
            log.debug("SQL:   CALL_TYPE_CD="+pData.getCallTypeCd());
            log.debug("SQL:   CALL_SEVERITY_CD="+pData.getCallSeverityCd());
            log.debug("SQL:   OPENED_BY_ID="+pData.getOpenedById());
            log.debug("SQL:   ASSIGNED_TO_ID="+pData.getAssignedToId());
            log.debug("SQL:   CLOSED_DATE="+pData.getClosedDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_TIME="+pData.getAddTime());
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
     * Deletes a CallData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCallId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCallId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CALL WHERE CALL_ID = " + pCallId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CallData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CALL");
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
     * Inserts a CallData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CallData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CallData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CALL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CALL_ID,ORDER_ID,ACCOUNT_ID,SITE_ID,CONTACT_NAME,CONTACT_EMAIL_ADDRESS,CONTACT_PHONE_NUMBER,CUSTOMER_FIELD_1,PRODUCT_NAME,SHORT_DESC,LONG_DESC,COMMENTS,CALL_STATUS_CD,CALL_TYPE_CD,CALL_SEVERITY_CD,OPENED_BY_ID,ASSIGNED_TO_ID,CLOSED_DATE,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCallId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getOrderId());
        }

        if (pData.getAccountId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getAccountId());
        }

        if (pData.getSiteId() == 0) {
            pstmt.setNull(4+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4+4,pData.getSiteId());
        }

        pstmt.setString(5+4,pData.getContactName());
        pstmt.setString(6+4,pData.getContactEmailAddress());
        pstmt.setString(7+4,pData.getContactPhoneNumber());
        pstmt.setString(8+4,pData.getCustomerField1());
        pstmt.setString(9+4,pData.getProductName());
        pstmt.setString(10+4,pData.getShortDesc());
        pstmt.setString(11+4,pData.getLongDesc());
        pstmt.setString(12+4,pData.getComments());
        pstmt.setString(13+4,pData.getCallStatusCd());
        pstmt.setString(14+4,pData.getCallTypeCd());
        pstmt.setString(15+4,pData.getCallSeverityCd());
        pstmt.setInt(16+4,pData.getOpenedById());
        pstmt.setInt(17+4,pData.getAssignedToId());
        pstmt.setDate(18+4,DBAccess.toSQLDate(pData.getClosedDate()));
        pstmt.setTimestamp(19+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(20+4,DBAccess.toSQLTimestamp(pData.getAddTime()));
        pstmt.setString(21+4,pData.getAddBy());
        pstmt.setTimestamp(22+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(23+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CallData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CallData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CallData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CallData insert(Connection pCon, CallData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CallData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CallData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CallData pData, boolean pLogFl)
        throws SQLException {
        CallData oldData = null;
        if(pLogFl) {
          int id = pData.getCallId();
          try {
          oldData = CallDataAccess.select(pCon,id);
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
     * Deletes a CallData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCallId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCallId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CALL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CALL d WHERE CALL_ID = " + pCallId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCallId);
        return n;
     }

    /**
     * Deletes CallData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CALL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CALL d ");
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

