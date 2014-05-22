
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        Edi997DataAccess
 * Description:  This class is used to build access methods to the CLW_EDI_997 table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.Edi997Data;
import com.cleanwise.service.api.value.Edi997DataVector;
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
 * <code>Edi997DataAccess</code>
 */
public class Edi997DataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(Edi997DataAccess.class.getName());

    /** <code>CLW_EDI_997</code> table name */
	/* Primary key: EDI_997_ID */
	
    public static final String CLW_EDI_997 = "CLW_EDI_997";
    
    /** <code>EDI_997_ID</code> EDI_997_ID column of table CLW_EDI_997 */
    public static final String EDI_997_ID = "EDI_997_ID";
    /** <code>ELECTRONIC_TRANSACTION_ID</code> ELECTRONIC_TRANSACTION_ID column of table CLW_EDI_997 */
    public static final String ELECTRONIC_TRANSACTION_ID = "ELECTRONIC_TRANSACTION_ID";
    /** <code>ACK_GROUP_TYPE</code> ACK_GROUP_TYPE column of table CLW_EDI_997 */
    public static final String ACK_GROUP_TYPE = "ACK_GROUP_TYPE";
    /** <code>ACK_GROUP_CONTROL_NUMBER</code> ACK_GROUP_CONTROL_NUMBER column of table CLW_EDI_997 */
    public static final String ACK_GROUP_CONTROL_NUMBER = "ACK_GROUP_CONTROL_NUMBER";
    /** <code>ACK_SET_TYPE</code> ACK_SET_TYPE column of table CLW_EDI_997 */
    public static final String ACK_SET_TYPE = "ACK_SET_TYPE";
    /** <code>ACK_SET_CONTROL_NUMBER</code> ACK_SET_CONTROL_NUMBER column of table CLW_EDI_997 */
    public static final String ACK_SET_CONTROL_NUMBER = "ACK_SET_CONTROL_NUMBER";
    /** <code>ACK_STATUS</code> ACK_STATUS column of table CLW_EDI_997 */
    public static final String ACK_STATUS = "ACK_STATUS";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_EDI_997 */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_EDI_997 */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_EDI_997 */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_EDI_997 */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public Edi997DataAccess()
    {
    }

    /**
     * Gets a Edi997Data object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pEdi997Id The key requested.
     * @return new Edi997Data()
     * @throws            SQLException
     */
    public static Edi997Data select(Connection pCon, int pEdi997Id)
        throws SQLException, DataNotFoundException {
        Edi997Data x=null;
        String sql="SELECT EDI_997_ID,ELECTRONIC_TRANSACTION_ID,ACK_GROUP_TYPE,ACK_GROUP_CONTROL_NUMBER,ACK_SET_TYPE,ACK_SET_CONTROL_NUMBER,ACK_STATUS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_EDI_997 WHERE EDI_997_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pEdi997Id=" + pEdi997Id);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pEdi997Id);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=Edi997Data.createValue();
            
            x.setEdi997Id(rs.getInt(1));
            x.setElectronicTransactionId(rs.getInt(2));
            x.setAckGroupType(rs.getString(3));
            x.setAckGroupControlNumber(rs.getInt(4));
            x.setAckSetType(rs.getString(5));
            x.setAckSetControlNumber(rs.getInt(6));
            x.setAckStatus(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("EDI_997_ID :" + pEdi997Id);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a Edi997DataVector object that consists
     * of Edi997Data objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new Edi997DataVector()
     * @throws            SQLException
     */
    public static Edi997DataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a Edi997Data Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_EDI_997.EDI_997_ID,CLW_EDI_997.ELECTRONIC_TRANSACTION_ID,CLW_EDI_997.ACK_GROUP_TYPE,CLW_EDI_997.ACK_GROUP_CONTROL_NUMBER,CLW_EDI_997.ACK_SET_TYPE,CLW_EDI_997.ACK_SET_CONTROL_NUMBER,CLW_EDI_997.ACK_STATUS,CLW_EDI_997.ADD_DATE,CLW_EDI_997.ADD_BY,CLW_EDI_997.MOD_DATE,CLW_EDI_997.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated Edi997Data Object.
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
    *@returns a populated Edi997Data Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         Edi997Data x = Edi997Data.createValue();
         
         x.setEdi997Id(rs.getInt(1+offset));
         x.setElectronicTransactionId(rs.getInt(2+offset));
         x.setAckGroupType(rs.getString(3+offset));
         x.setAckGroupControlNumber(rs.getInt(4+offset));
         x.setAckSetType(rs.getString(5+offset));
         x.setAckSetControlNumber(rs.getInt(6+offset));
         x.setAckStatus(rs.getString(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the Edi997Data Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a Edi997DataVector object that consists
     * of Edi997Data objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new Edi997DataVector()
     * @throws            SQLException
     */
    public static Edi997DataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT EDI_997_ID,ELECTRONIC_TRANSACTION_ID,ACK_GROUP_TYPE,ACK_GROUP_CONTROL_NUMBER,ACK_SET_TYPE,ACK_SET_CONTROL_NUMBER,ACK_STATUS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_EDI_997");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_EDI_997.EDI_997_ID,CLW_EDI_997.ELECTRONIC_TRANSACTION_ID,CLW_EDI_997.ACK_GROUP_TYPE,CLW_EDI_997.ACK_GROUP_CONTROL_NUMBER,CLW_EDI_997.ACK_SET_TYPE,CLW_EDI_997.ACK_SET_CONTROL_NUMBER,CLW_EDI_997.ACK_STATUS,CLW_EDI_997.ADD_DATE,CLW_EDI_997.ADD_BY,CLW_EDI_997.MOD_DATE,CLW_EDI_997.MOD_BY FROM CLW_EDI_997");
                where = pCriteria.getSqlClause("CLW_EDI_997");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EDI_997.equals(otherTable)){
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
        Edi997DataVector v = new Edi997DataVector();
        while (rs.next()) {
            Edi997Data x = Edi997Data.createValue();
            
            x.setEdi997Id(rs.getInt(1));
            x.setElectronicTransactionId(rs.getInt(2));
            x.setAckGroupType(rs.getString(3));
            x.setAckGroupControlNumber(rs.getInt(4));
            x.setAckSetType(rs.getString(5));
            x.setAckSetControlNumber(rs.getInt(6));
            x.setAckStatus(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a Edi997DataVector object that consists
     * of Edi997Data objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for Edi997Data
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new Edi997DataVector()
     * @throws            SQLException
     */
    public static Edi997DataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        Edi997DataVector v = new Edi997DataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT EDI_997_ID,ELECTRONIC_TRANSACTION_ID,ACK_GROUP_TYPE,ACK_GROUP_CONTROL_NUMBER,ACK_SET_TYPE,ACK_SET_CONTROL_NUMBER,ACK_STATUS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_EDI_997 WHERE EDI_997_ID IN (");

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
            Edi997Data x=null;
            while (rs.next()) {
                // build the object
                x=Edi997Data.createValue();
                
                x.setEdi997Id(rs.getInt(1));
                x.setElectronicTransactionId(rs.getInt(2));
                x.setAckGroupType(rs.getString(3));
                x.setAckGroupControlNumber(rs.getInt(4));
                x.setAckSetType(rs.getString(5));
                x.setAckSetControlNumber(rs.getInt(6));
                x.setAckStatus(rs.getString(7));
                x.setAddDate(rs.getTimestamp(8));
                x.setAddBy(rs.getString(9));
                x.setModDate(rs.getTimestamp(10));
                x.setModBy(rs.getString(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a Edi997DataVector object of all
     * Edi997Data objects in the database.
     * @param pCon An open database connection.
     * @return new Edi997DataVector()
     * @throws            SQLException
     */
    public static Edi997DataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT EDI_997_ID,ELECTRONIC_TRANSACTION_ID,ACK_GROUP_TYPE,ACK_GROUP_CONTROL_NUMBER,ACK_SET_TYPE,ACK_SET_CONTROL_NUMBER,ACK_STATUS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_EDI_997";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        Edi997DataVector v = new Edi997DataVector();
        Edi997Data x = null;
        while (rs.next()) {
            // build the object
            x = Edi997Data.createValue();
            
            x.setEdi997Id(rs.getInt(1));
            x.setElectronicTransactionId(rs.getInt(2));
            x.setAckGroupType(rs.getString(3));
            x.setAckGroupControlNumber(rs.getInt(4));
            x.setAckSetType(rs.getString(5));
            x.setAckSetControlNumber(rs.getInt(6));
            x.setAckStatus(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * Edi997Data objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT EDI_997_ID FROM CLW_EDI_997");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EDI_997");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EDI_997");
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
     * Inserts a Edi997Data object into the database.
     * @param pCon  An open database connection.
     * @param pData  A Edi997Data object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new Edi997Data() with the generated
     *         key set
     * @throws            SQLException
     */
    public static Edi997Data insert(Connection pCon, Edi997Data pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_EDI_997_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_EDI_997_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setEdi997Id(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_EDI_997 (EDI_997_ID,ELECTRONIC_TRANSACTION_ID,ACK_GROUP_TYPE,ACK_GROUP_CONTROL_NUMBER,ACK_SET_TYPE,ACK_SET_CONTROL_NUMBER,ACK_STATUS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getEdi997Id());
        pstmt.setInt(2,pData.getElectronicTransactionId());
        pstmt.setString(3,pData.getAckGroupType());
        pstmt.setInt(4,pData.getAckGroupControlNumber());
        pstmt.setString(5,pData.getAckSetType());
        pstmt.setInt(6,pData.getAckSetControlNumber());
        pstmt.setString(7,pData.getAckStatus());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   EDI_997_ID="+pData.getEdi997Id());
            log.debug("SQL:   ELECTRONIC_TRANSACTION_ID="+pData.getElectronicTransactionId());
            log.debug("SQL:   ACK_GROUP_TYPE="+pData.getAckGroupType());
            log.debug("SQL:   ACK_GROUP_CONTROL_NUMBER="+pData.getAckGroupControlNumber());
            log.debug("SQL:   ACK_SET_TYPE="+pData.getAckSetType());
            log.debug("SQL:   ACK_SET_CONTROL_NUMBER="+pData.getAckSetControlNumber());
            log.debug("SQL:   ACK_STATUS="+pData.getAckStatus());
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
        pData.setEdi997Id(0);
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
     * Updates a Edi997Data object in the database.
     * @param pCon  An open database connection.
     * @param pData  A Edi997Data object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, Edi997Data pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_EDI_997 SET ELECTRONIC_TRANSACTION_ID = ?,ACK_GROUP_TYPE = ?,ACK_GROUP_CONTROL_NUMBER = ?,ACK_SET_TYPE = ?,ACK_SET_CONTROL_NUMBER = ?,ACK_STATUS = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE EDI_997_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getElectronicTransactionId());
        pstmt.setString(i++,pData.getAckGroupType());
        pstmt.setInt(i++,pData.getAckGroupControlNumber());
        pstmt.setString(i++,pData.getAckSetType());
        pstmt.setInt(i++,pData.getAckSetControlNumber());
        pstmt.setString(i++,pData.getAckStatus());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getEdi997Id());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ELECTRONIC_TRANSACTION_ID="+pData.getElectronicTransactionId());
            log.debug("SQL:   ACK_GROUP_TYPE="+pData.getAckGroupType());
            log.debug("SQL:   ACK_GROUP_CONTROL_NUMBER="+pData.getAckGroupControlNumber());
            log.debug("SQL:   ACK_SET_TYPE="+pData.getAckSetType());
            log.debug("SQL:   ACK_SET_CONTROL_NUMBER="+pData.getAckSetControlNumber());
            log.debug("SQL:   ACK_STATUS="+pData.getAckStatus());
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
     * Deletes a Edi997Data object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pEdi997Id Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pEdi997Id)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_EDI_997 WHERE EDI_997_ID = " + pEdi997Id;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes Edi997Data objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_EDI_997");
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
     * Inserts a Edi997Data log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A Edi997Data object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, Edi997Data pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_EDI_997 (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "EDI_997_ID,ELECTRONIC_TRANSACTION_ID,ACK_GROUP_TYPE,ACK_GROUP_CONTROL_NUMBER,ACK_SET_TYPE,ACK_SET_CONTROL_NUMBER,ACK_STATUS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getEdi997Id());
        pstmt.setInt(2+4,pData.getElectronicTransactionId());
        pstmt.setString(3+4,pData.getAckGroupType());
        pstmt.setInt(4+4,pData.getAckGroupControlNumber());
        pstmt.setString(5+4,pData.getAckSetType());
        pstmt.setInt(6+4,pData.getAckSetControlNumber());
        pstmt.setString(7+4,pData.getAckStatus());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a Edi997Data object into the database.
     * @param pCon  An open database connection.
     * @param pData  A Edi997Data object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new Edi997Data() with the generated
     *         key set
     * @throws            SQLException
     */
    public static Edi997Data insert(Connection pCon, Edi997Data pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a Edi997Data object in the database.
     * @param pCon  An open database connection.
     * @param pData  A Edi997Data object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, Edi997Data pData, boolean pLogFl)
        throws SQLException {
        Edi997Data oldData = null;
        if(pLogFl) {
          int id = pData.getEdi997Id();
          try {
          oldData = Edi997DataAccess.select(pCon,id);
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
     * Deletes a Edi997Data object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pEdi997Id Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pEdi997Id, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_EDI_997 SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_EDI_997 d WHERE EDI_997_ID = " + pEdi997Id;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pEdi997Id);
        return n;
     }

    /**
     * Deletes Edi997Data objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_EDI_997 SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_EDI_997 d ");
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

