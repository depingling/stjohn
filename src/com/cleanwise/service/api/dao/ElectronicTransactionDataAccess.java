
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ElectronicTransactionDataAccess
 * Description:  This class is used to build access methods to the CLW_ELECTRONIC_TRANSACTION table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ElectronicTransactionData;
import com.cleanwise.service.api.value.ElectronicTransactionDataVector;
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
 * <code>ElectronicTransactionDataAccess</code>
 */
public class ElectronicTransactionDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ElectronicTransactionDataAccess.class.getName());

    /** <code>CLW_ELECTRONIC_TRANSACTION</code> table name */
	/* Primary key: ELECTRONIC_TRANSACTION_ID */
	
    public static final String CLW_ELECTRONIC_TRANSACTION = "CLW_ELECTRONIC_TRANSACTION";
    
    /** <code>ELECTRONIC_TRANSACTION_ID</code> ELECTRONIC_TRANSACTION_ID column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String ELECTRONIC_TRANSACTION_ID = "ELECTRONIC_TRANSACTION_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>INTERCHANGE_ID</code> INTERCHANGE_ID column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String INTERCHANGE_ID = "INTERCHANGE_ID";
    /** <code>GROUP_TYPE</code> GROUP_TYPE column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String GROUP_TYPE = "GROUP_TYPE";
    /** <code>GROUP_SENDER</code> GROUP_SENDER column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String GROUP_SENDER = "GROUP_SENDER";
    /** <code>GROUP_RECEIVER</code> GROUP_RECEIVER column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String GROUP_RECEIVER = "GROUP_RECEIVER";
    /** <code>GROUP_CONTROL_NUMBER</code> GROUP_CONTROL_NUMBER column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String GROUP_CONTROL_NUMBER = "GROUP_CONTROL_NUMBER";
    /** <code>SET_TYPE</code> SET_TYPE column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String SET_TYPE = "SET_TYPE";
    /** <code>SET_CONTROL_NUMBER</code> SET_CONTROL_NUMBER column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String SET_CONTROL_NUMBER = "SET_CONTROL_NUMBER";
    /** <code>SET_STATUS</code> SET_STATUS column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String SET_STATUS = "SET_STATUS";
    /** <code>SET_DATA</code> SET_DATA column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String SET_DATA = "SET_DATA";
    /** <code>EXCEPTION</code> EXCEPTION column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String EXCEPTION = "EXCEPTION";
    /** <code>KEY_STRING</code> KEY_STRING column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String KEY_STRING = "KEY_STRING";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String MOD_BY = "MOD_BY";
    /** <code>REFERENCE_ID</code> REFERENCE_ID column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String REFERENCE_ID = "REFERENCE_ID";
    /** <code>REFERENCE_TABLE</code> REFERENCE_TABLE column of table CLW_ELECTRONIC_TRANSACTION */
    public static final String REFERENCE_TABLE = "REFERENCE_TABLE";

    /**
     * Constructor.
     */
    public ElectronicTransactionDataAccess()
    {
    }

    /**
     * Gets a ElectronicTransactionData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pElectronicTransactionId The key requested.
     * @return new ElectronicTransactionData()
     * @throws            SQLException
     */
    public static ElectronicTransactionData select(Connection pCon, int pElectronicTransactionId)
        throws SQLException, DataNotFoundException {
        ElectronicTransactionData x=null;
        String sql="SELECT ELECTRONIC_TRANSACTION_ID,ORDER_ID,INTERCHANGE_ID,GROUP_TYPE,GROUP_SENDER,GROUP_RECEIVER,GROUP_CONTROL_NUMBER,SET_TYPE,SET_CONTROL_NUMBER,SET_STATUS,SET_DATA,EXCEPTION,KEY_STRING,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REFERENCE_ID,REFERENCE_TABLE FROM CLW_ELECTRONIC_TRANSACTION WHERE ELECTRONIC_TRANSACTION_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pElectronicTransactionId=" + pElectronicTransactionId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pElectronicTransactionId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ElectronicTransactionData.createValue();
            
            x.setElectronicTransactionId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setInterchangeId(rs.getInt(3));
            x.setGroupType(rs.getString(4));
            x.setGroupSender(rs.getString(5));
            x.setGroupReceiver(rs.getString(6));
            x.setGroupControlNumber(rs.getInt(7));
            x.setSetType(rs.getString(8));
            x.setSetControlNumber(rs.getInt(9));
            x.setSetStatus(rs.getInt(10));
            x.setSetData(rs.getString(11));
            x.setException(rs.getString(12));
            x.setKeyString(rs.getString(13));
            x.setAddDate(rs.getTimestamp(14));
            x.setAddBy(rs.getString(15));
            x.setModDate(rs.getTimestamp(16));
            x.setModBy(rs.getString(17));
            x.setReferenceId(rs.getInt(18));
            x.setReferenceTable(rs.getString(19));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ELECTRONIC_TRANSACTION_ID :" + pElectronicTransactionId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ElectronicTransactionDataVector object that consists
     * of ElectronicTransactionData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ElectronicTransactionDataVector()
     * @throws            SQLException
     */
    public static ElectronicTransactionDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ElectronicTransactionData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ELECTRONIC_TRANSACTION.ELECTRONIC_TRANSACTION_ID,CLW_ELECTRONIC_TRANSACTION.ORDER_ID,CLW_ELECTRONIC_TRANSACTION.INTERCHANGE_ID,CLW_ELECTRONIC_TRANSACTION.GROUP_TYPE,CLW_ELECTRONIC_TRANSACTION.GROUP_SENDER,CLW_ELECTRONIC_TRANSACTION.GROUP_RECEIVER,CLW_ELECTRONIC_TRANSACTION.GROUP_CONTROL_NUMBER,CLW_ELECTRONIC_TRANSACTION.SET_TYPE,CLW_ELECTRONIC_TRANSACTION.SET_CONTROL_NUMBER,CLW_ELECTRONIC_TRANSACTION.SET_STATUS,CLW_ELECTRONIC_TRANSACTION.SET_DATA,CLW_ELECTRONIC_TRANSACTION.EXCEPTION,CLW_ELECTRONIC_TRANSACTION.KEY_STRING,CLW_ELECTRONIC_TRANSACTION.ADD_DATE,CLW_ELECTRONIC_TRANSACTION.ADD_BY,CLW_ELECTRONIC_TRANSACTION.MOD_DATE,CLW_ELECTRONIC_TRANSACTION.MOD_BY,CLW_ELECTRONIC_TRANSACTION.REFERENCE_ID,CLW_ELECTRONIC_TRANSACTION.REFERENCE_TABLE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ElectronicTransactionData Object.
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
    *@returns a populated ElectronicTransactionData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ElectronicTransactionData x = ElectronicTransactionData.createValue();
         
         x.setElectronicTransactionId(rs.getInt(1+offset));
         x.setOrderId(rs.getInt(2+offset));
         x.setInterchangeId(rs.getInt(3+offset));
         x.setGroupType(rs.getString(4+offset));
         x.setGroupSender(rs.getString(5+offset));
         x.setGroupReceiver(rs.getString(6+offset));
         x.setGroupControlNumber(rs.getInt(7+offset));
         x.setSetType(rs.getString(8+offset));
         x.setSetControlNumber(rs.getInt(9+offset));
         x.setSetStatus(rs.getInt(10+offset));
         x.setSetData(rs.getString(11+offset));
         x.setException(rs.getString(12+offset));
         x.setKeyString(rs.getString(13+offset));
         x.setAddDate(rs.getTimestamp(14+offset));
         x.setAddBy(rs.getString(15+offset));
         x.setModDate(rs.getTimestamp(16+offset));
         x.setModBy(rs.getString(17+offset));
         x.setReferenceId(rs.getInt(18+offset));
         x.setReferenceTable(rs.getString(19+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ElectronicTransactionData Object represents.
    */
    public int getColumnCount(){
        return 19;
    }

    /**
     * Gets a ElectronicTransactionDataVector object that consists
     * of ElectronicTransactionData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ElectronicTransactionDataVector()
     * @throws            SQLException
     */
    public static ElectronicTransactionDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ELECTRONIC_TRANSACTION_ID,ORDER_ID,INTERCHANGE_ID,GROUP_TYPE,GROUP_SENDER,GROUP_RECEIVER,GROUP_CONTROL_NUMBER,SET_TYPE,SET_CONTROL_NUMBER,SET_STATUS,SET_DATA,EXCEPTION,KEY_STRING,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REFERENCE_ID,REFERENCE_TABLE FROM CLW_ELECTRONIC_TRANSACTION");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ELECTRONIC_TRANSACTION.ELECTRONIC_TRANSACTION_ID,CLW_ELECTRONIC_TRANSACTION.ORDER_ID,CLW_ELECTRONIC_TRANSACTION.INTERCHANGE_ID,CLW_ELECTRONIC_TRANSACTION.GROUP_TYPE,CLW_ELECTRONIC_TRANSACTION.GROUP_SENDER,CLW_ELECTRONIC_TRANSACTION.GROUP_RECEIVER,CLW_ELECTRONIC_TRANSACTION.GROUP_CONTROL_NUMBER,CLW_ELECTRONIC_TRANSACTION.SET_TYPE,CLW_ELECTRONIC_TRANSACTION.SET_CONTROL_NUMBER,CLW_ELECTRONIC_TRANSACTION.SET_STATUS,CLW_ELECTRONIC_TRANSACTION.SET_DATA,CLW_ELECTRONIC_TRANSACTION.EXCEPTION,CLW_ELECTRONIC_TRANSACTION.KEY_STRING,CLW_ELECTRONIC_TRANSACTION.ADD_DATE,CLW_ELECTRONIC_TRANSACTION.ADD_BY,CLW_ELECTRONIC_TRANSACTION.MOD_DATE,CLW_ELECTRONIC_TRANSACTION.MOD_BY,CLW_ELECTRONIC_TRANSACTION.REFERENCE_ID,CLW_ELECTRONIC_TRANSACTION.REFERENCE_TABLE FROM CLW_ELECTRONIC_TRANSACTION");
                where = pCriteria.getSqlClause("CLW_ELECTRONIC_TRANSACTION");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ELECTRONIC_TRANSACTION.equals(otherTable)){
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
        ElectronicTransactionDataVector v = new ElectronicTransactionDataVector();
        while (rs.next()) {
            ElectronicTransactionData x = ElectronicTransactionData.createValue();
            
            x.setElectronicTransactionId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setInterchangeId(rs.getInt(3));
            x.setGroupType(rs.getString(4));
            x.setGroupSender(rs.getString(5));
            x.setGroupReceiver(rs.getString(6));
            x.setGroupControlNumber(rs.getInt(7));
            x.setSetType(rs.getString(8));
            x.setSetControlNumber(rs.getInt(9));
            x.setSetStatus(rs.getInt(10));
            x.setSetData(rs.getString(11));
            x.setException(rs.getString(12));
            x.setKeyString(rs.getString(13));
            x.setAddDate(rs.getTimestamp(14));
            x.setAddBy(rs.getString(15));
            x.setModDate(rs.getTimestamp(16));
            x.setModBy(rs.getString(17));
            x.setReferenceId(rs.getInt(18));
            x.setReferenceTable(rs.getString(19));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ElectronicTransactionDataVector object that consists
     * of ElectronicTransactionData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ElectronicTransactionData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ElectronicTransactionDataVector()
     * @throws            SQLException
     */
    public static ElectronicTransactionDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ElectronicTransactionDataVector v = new ElectronicTransactionDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ELECTRONIC_TRANSACTION_ID,ORDER_ID,INTERCHANGE_ID,GROUP_TYPE,GROUP_SENDER,GROUP_RECEIVER,GROUP_CONTROL_NUMBER,SET_TYPE,SET_CONTROL_NUMBER,SET_STATUS,SET_DATA,EXCEPTION,KEY_STRING,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REFERENCE_ID,REFERENCE_TABLE FROM CLW_ELECTRONIC_TRANSACTION WHERE ELECTRONIC_TRANSACTION_ID IN (");

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
            ElectronicTransactionData x=null;
            while (rs.next()) {
                // build the object
                x=ElectronicTransactionData.createValue();
                
                x.setElectronicTransactionId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setInterchangeId(rs.getInt(3));
                x.setGroupType(rs.getString(4));
                x.setGroupSender(rs.getString(5));
                x.setGroupReceiver(rs.getString(6));
                x.setGroupControlNumber(rs.getInt(7));
                x.setSetType(rs.getString(8));
                x.setSetControlNumber(rs.getInt(9));
                x.setSetStatus(rs.getInt(10));
                x.setSetData(rs.getString(11));
                x.setException(rs.getString(12));
                x.setKeyString(rs.getString(13));
                x.setAddDate(rs.getTimestamp(14));
                x.setAddBy(rs.getString(15));
                x.setModDate(rs.getTimestamp(16));
                x.setModBy(rs.getString(17));
                x.setReferenceId(rs.getInt(18));
                x.setReferenceTable(rs.getString(19));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ElectronicTransactionDataVector object of all
     * ElectronicTransactionData objects in the database.
     * @param pCon An open database connection.
     * @return new ElectronicTransactionDataVector()
     * @throws            SQLException
     */
    public static ElectronicTransactionDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ELECTRONIC_TRANSACTION_ID,ORDER_ID,INTERCHANGE_ID,GROUP_TYPE,GROUP_SENDER,GROUP_RECEIVER,GROUP_CONTROL_NUMBER,SET_TYPE,SET_CONTROL_NUMBER,SET_STATUS,SET_DATA,EXCEPTION,KEY_STRING,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REFERENCE_ID,REFERENCE_TABLE FROM CLW_ELECTRONIC_TRANSACTION";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ElectronicTransactionDataVector v = new ElectronicTransactionDataVector();
        ElectronicTransactionData x = null;
        while (rs.next()) {
            // build the object
            x = ElectronicTransactionData.createValue();
            
            x.setElectronicTransactionId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setInterchangeId(rs.getInt(3));
            x.setGroupType(rs.getString(4));
            x.setGroupSender(rs.getString(5));
            x.setGroupReceiver(rs.getString(6));
            x.setGroupControlNumber(rs.getInt(7));
            x.setSetType(rs.getString(8));
            x.setSetControlNumber(rs.getInt(9));
            x.setSetStatus(rs.getInt(10));
            x.setSetData(rs.getString(11));
            x.setException(rs.getString(12));
            x.setKeyString(rs.getString(13));
            x.setAddDate(rs.getTimestamp(14));
            x.setAddBy(rs.getString(15));
            x.setModDate(rs.getTimestamp(16));
            x.setModBy(rs.getString(17));
            x.setReferenceId(rs.getInt(18));
            x.setReferenceTable(rs.getString(19));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ElectronicTransactionData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ELECTRONIC_TRANSACTION_ID FROM CLW_ELECTRONIC_TRANSACTION");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ELECTRONIC_TRANSACTION");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ELECTRONIC_TRANSACTION");
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
     * Inserts a ElectronicTransactionData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ElectronicTransactionData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ElectronicTransactionData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ElectronicTransactionData insert(Connection pCon, ElectronicTransactionData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ELECTRONIC_TRANSACTION_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ELECTRONIC_TRANSACTION_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setElectronicTransactionId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ELECTRONIC_TRANSACTION (ELECTRONIC_TRANSACTION_ID,ORDER_ID,INTERCHANGE_ID,GROUP_TYPE,GROUP_SENDER,GROUP_RECEIVER,GROUP_CONTROL_NUMBER,SET_TYPE,SET_CONTROL_NUMBER,SET_STATUS,SET_DATA,EXCEPTION,KEY_STRING,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REFERENCE_ID,REFERENCE_TABLE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getElectronicTransactionId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getOrderId());
        }

        pstmt.setInt(3,pData.getInterchangeId());
        pstmt.setString(4,pData.getGroupType());
        pstmt.setString(5,pData.getGroupSender());
        pstmt.setString(6,pData.getGroupReceiver());
        pstmt.setInt(7,pData.getGroupControlNumber());
        pstmt.setString(8,pData.getSetType());
        pstmt.setInt(9,pData.getSetControlNumber());
        pstmt.setInt(10,pData.getSetStatus());
        pstmt.setString(11,pData.getSetData());
        pstmt.setString(12,pData.getException());
        pstmt.setString(13,pData.getKeyString());
        pstmt.setTimestamp(14,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(15,pData.getAddBy());
        pstmt.setTimestamp(16,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(17,pData.getModBy());
        pstmt.setInt(18,pData.getReferenceId());
        pstmt.setString(19,pData.getReferenceTable());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ELECTRONIC_TRANSACTION_ID="+pData.getElectronicTransactionId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   INTERCHANGE_ID="+pData.getInterchangeId());
            log.debug("SQL:   GROUP_TYPE="+pData.getGroupType());
            log.debug("SQL:   GROUP_SENDER="+pData.getGroupSender());
            log.debug("SQL:   GROUP_RECEIVER="+pData.getGroupReceiver());
            log.debug("SQL:   GROUP_CONTROL_NUMBER="+pData.getGroupControlNumber());
            log.debug("SQL:   SET_TYPE="+pData.getSetType());
            log.debug("SQL:   SET_CONTROL_NUMBER="+pData.getSetControlNumber());
            log.debug("SQL:   SET_STATUS="+pData.getSetStatus());
            log.debug("SQL:   SET_DATA="+pData.getSetData());
            log.debug("SQL:   EXCEPTION="+pData.getException());
            log.debug("SQL:   KEY_STRING="+pData.getKeyString());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   REFERENCE_ID="+pData.getReferenceId());
            log.debug("SQL:   REFERENCE_TABLE="+pData.getReferenceTable());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setElectronicTransactionId(0);
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
     * Updates a ElectronicTransactionData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ElectronicTransactionData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ElectronicTransactionData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ELECTRONIC_TRANSACTION SET ORDER_ID = ?,INTERCHANGE_ID = ?,GROUP_TYPE = ?,GROUP_SENDER = ?,GROUP_RECEIVER = ?,GROUP_CONTROL_NUMBER = ?,SET_TYPE = ?,SET_CONTROL_NUMBER = ?,SET_STATUS = ?,SET_DATA = ?,EXCEPTION = ?,KEY_STRING = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,REFERENCE_ID = ?,REFERENCE_TABLE = ? WHERE ELECTRONIC_TRANSACTION_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getOrderId());
        }

        pstmt.setInt(i++,pData.getInterchangeId());
        pstmt.setString(i++,pData.getGroupType());
        pstmt.setString(i++,pData.getGroupSender());
        pstmt.setString(i++,pData.getGroupReceiver());
        pstmt.setInt(i++,pData.getGroupControlNumber());
        pstmt.setString(i++,pData.getSetType());
        pstmt.setInt(i++,pData.getSetControlNumber());
        pstmt.setInt(i++,pData.getSetStatus());
        pstmt.setString(i++,pData.getSetData());
        pstmt.setString(i++,pData.getException());
        pstmt.setString(i++,pData.getKeyString());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getReferenceId());
        pstmt.setString(i++,pData.getReferenceTable());
        pstmt.setInt(i++,pData.getElectronicTransactionId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   INTERCHANGE_ID="+pData.getInterchangeId());
            log.debug("SQL:   GROUP_TYPE="+pData.getGroupType());
            log.debug("SQL:   GROUP_SENDER="+pData.getGroupSender());
            log.debug("SQL:   GROUP_RECEIVER="+pData.getGroupReceiver());
            log.debug("SQL:   GROUP_CONTROL_NUMBER="+pData.getGroupControlNumber());
            log.debug("SQL:   SET_TYPE="+pData.getSetType());
            log.debug("SQL:   SET_CONTROL_NUMBER="+pData.getSetControlNumber());
            log.debug("SQL:   SET_STATUS="+pData.getSetStatus());
            log.debug("SQL:   SET_DATA="+pData.getSetData());
            log.debug("SQL:   EXCEPTION="+pData.getException());
            log.debug("SQL:   KEY_STRING="+pData.getKeyString());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   REFERENCE_ID="+pData.getReferenceId());
            log.debug("SQL:   REFERENCE_TABLE="+pData.getReferenceTable());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ElectronicTransactionData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pElectronicTransactionId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pElectronicTransactionId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ELECTRONIC_TRANSACTION WHERE ELECTRONIC_TRANSACTION_ID = " + pElectronicTransactionId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ElectronicTransactionData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ELECTRONIC_TRANSACTION");
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
     * Inserts a ElectronicTransactionData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ElectronicTransactionData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ElectronicTransactionData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ELECTRONIC_TRANSACTION (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ELECTRONIC_TRANSACTION_ID,ORDER_ID,INTERCHANGE_ID,GROUP_TYPE,GROUP_SENDER,GROUP_RECEIVER,GROUP_CONTROL_NUMBER,SET_TYPE,SET_CONTROL_NUMBER,SET_STATUS,SET_DATA,EXCEPTION,KEY_STRING,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REFERENCE_ID,REFERENCE_TABLE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getElectronicTransactionId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getOrderId());
        }

        pstmt.setInt(3+4,pData.getInterchangeId());
        pstmt.setString(4+4,pData.getGroupType());
        pstmt.setString(5+4,pData.getGroupSender());
        pstmt.setString(6+4,pData.getGroupReceiver());
        pstmt.setInt(7+4,pData.getGroupControlNumber());
        pstmt.setString(8+4,pData.getSetType());
        pstmt.setInt(9+4,pData.getSetControlNumber());
        pstmt.setInt(10+4,pData.getSetStatus());
        pstmt.setString(11+4,pData.getSetData());
        pstmt.setString(12+4,pData.getException());
        pstmt.setString(13+4,pData.getKeyString());
        pstmt.setTimestamp(14+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(15+4,pData.getAddBy());
        pstmt.setTimestamp(16+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(17+4,pData.getModBy());
        pstmt.setInt(18+4,pData.getReferenceId());
        pstmt.setString(19+4,pData.getReferenceTable());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ElectronicTransactionData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ElectronicTransactionData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ElectronicTransactionData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ElectronicTransactionData insert(Connection pCon, ElectronicTransactionData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ElectronicTransactionData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ElectronicTransactionData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ElectronicTransactionData pData, boolean pLogFl)
        throws SQLException {
        ElectronicTransactionData oldData = null;
        if(pLogFl) {
          int id = pData.getElectronicTransactionId();
          try {
          oldData = ElectronicTransactionDataAccess.select(pCon,id);
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
     * Deletes a ElectronicTransactionData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pElectronicTransactionId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pElectronicTransactionId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ELECTRONIC_TRANSACTION SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ELECTRONIC_TRANSACTION d WHERE ELECTRONIC_TRANSACTION_ID = " + pElectronicTransactionId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pElectronicTransactionId);
        return n;
     }

    /**
     * Deletes ElectronicTransactionData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ELECTRONIC_TRANSACTION SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ELECTRONIC_TRANSACTION d ");
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

