
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        InterchangeDataAccess
 * Description:  This class is used to build access methods to the CLW_INTERCHANGE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.InterchangeData;
import com.cleanwise.service.api.value.InterchangeDataVector;
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
 * <code>InterchangeDataAccess</code>
 */
public class InterchangeDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(InterchangeDataAccess.class.getName());

    /** <code>CLW_INTERCHANGE</code> table name */
	/* Primary key: INTERCHANGE_ID */
	
    public static final String CLW_INTERCHANGE = "CLW_INTERCHANGE";
    
    /** <code>INTERCHANGE_ID</code> INTERCHANGE_ID column of table CLW_INTERCHANGE */
    public static final String INTERCHANGE_ID = "INTERCHANGE_ID";
    /** <code>TRADING_PROFILE_ID</code> TRADING_PROFILE_ID column of table CLW_INTERCHANGE */
    public static final String TRADING_PROFILE_ID = "TRADING_PROFILE_ID";
    /** <code>INTERCHANGE_TYPE</code> INTERCHANGE_TYPE column of table CLW_INTERCHANGE */
    public static final String INTERCHANGE_TYPE = "INTERCHANGE_TYPE";
    /** <code>INTERCHANGE_SENDER</code> INTERCHANGE_SENDER column of table CLW_INTERCHANGE */
    public static final String INTERCHANGE_SENDER = "INTERCHANGE_SENDER";
    /** <code>INTERCHANGE_RECEIVER</code> INTERCHANGE_RECEIVER column of table CLW_INTERCHANGE */
    public static final String INTERCHANGE_RECEIVER = "INTERCHANGE_RECEIVER";
    /** <code>INTERCHANGE_CONTROL_NUM</code> INTERCHANGE_CONTROL_NUM column of table CLW_INTERCHANGE */
    public static final String INTERCHANGE_CONTROL_NUM = "INTERCHANGE_CONTROL_NUM";
    /** <code>TEST_IND</code> TEST_IND column of table CLW_INTERCHANGE */
    public static final String TEST_IND = "TEST_IND";
    /** <code>EDI_FILE_NAME</code> EDI_FILE_NAME column of table CLW_INTERCHANGE */
    public static final String EDI_FILE_NAME = "EDI_FILE_NAME";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_INTERCHANGE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_INTERCHANGE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_INTERCHANGE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_INTERCHANGE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public InterchangeDataAccess()
    {
    }

    /**
     * Gets a InterchangeData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pInterchangeId The key requested.
     * @return new InterchangeData()
     * @throws            SQLException
     */
    public static InterchangeData select(Connection pCon, int pInterchangeId)
        throws SQLException, DataNotFoundException {
        InterchangeData x=null;
        String sql="SELECT INTERCHANGE_ID,TRADING_PROFILE_ID,INTERCHANGE_TYPE,INTERCHANGE_SENDER,INTERCHANGE_RECEIVER,INTERCHANGE_CONTROL_NUM,TEST_IND,EDI_FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INTERCHANGE WHERE INTERCHANGE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pInterchangeId=" + pInterchangeId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pInterchangeId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=InterchangeData.createValue();
            
            x.setInterchangeId(rs.getInt(1));
            x.setTradingProfileId(rs.getInt(2));
            x.setInterchangeType(rs.getString(3));
            x.setInterchangeSender(rs.getString(4));
            x.setInterchangeReceiver(rs.getString(5));
            x.setInterchangeControlNum(rs.getInt(6));
            x.setTestInd(rs.getString(7));
            x.setEdiFileName(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("INTERCHANGE_ID :" + pInterchangeId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a InterchangeDataVector object that consists
     * of InterchangeData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new InterchangeDataVector()
     * @throws            SQLException
     */
    public static InterchangeDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a InterchangeData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_INTERCHANGE.INTERCHANGE_ID,CLW_INTERCHANGE.TRADING_PROFILE_ID,CLW_INTERCHANGE.INTERCHANGE_TYPE,CLW_INTERCHANGE.INTERCHANGE_SENDER,CLW_INTERCHANGE.INTERCHANGE_RECEIVER,CLW_INTERCHANGE.INTERCHANGE_CONTROL_NUM,CLW_INTERCHANGE.TEST_IND,CLW_INTERCHANGE.EDI_FILE_NAME,CLW_INTERCHANGE.ADD_DATE,CLW_INTERCHANGE.ADD_BY,CLW_INTERCHANGE.MOD_DATE,CLW_INTERCHANGE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated InterchangeData Object.
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
    *@returns a populated InterchangeData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         InterchangeData x = InterchangeData.createValue();
         
         x.setInterchangeId(rs.getInt(1+offset));
         x.setTradingProfileId(rs.getInt(2+offset));
         x.setInterchangeType(rs.getString(3+offset));
         x.setInterchangeSender(rs.getString(4+offset));
         x.setInterchangeReceiver(rs.getString(5+offset));
         x.setInterchangeControlNum(rs.getInt(6+offset));
         x.setTestInd(rs.getString(7+offset));
         x.setEdiFileName(rs.getString(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the InterchangeData Object represents.
    */
    public int getColumnCount(){
        return 12;
    }

    /**
     * Gets a InterchangeDataVector object that consists
     * of InterchangeData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new InterchangeDataVector()
     * @throws            SQLException
     */
    public static InterchangeDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT INTERCHANGE_ID,TRADING_PROFILE_ID,INTERCHANGE_TYPE,INTERCHANGE_SENDER,INTERCHANGE_RECEIVER,INTERCHANGE_CONTROL_NUM,TEST_IND,EDI_FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INTERCHANGE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_INTERCHANGE.INTERCHANGE_ID,CLW_INTERCHANGE.TRADING_PROFILE_ID,CLW_INTERCHANGE.INTERCHANGE_TYPE,CLW_INTERCHANGE.INTERCHANGE_SENDER,CLW_INTERCHANGE.INTERCHANGE_RECEIVER,CLW_INTERCHANGE.INTERCHANGE_CONTROL_NUM,CLW_INTERCHANGE.TEST_IND,CLW_INTERCHANGE.EDI_FILE_NAME,CLW_INTERCHANGE.ADD_DATE,CLW_INTERCHANGE.ADD_BY,CLW_INTERCHANGE.MOD_DATE,CLW_INTERCHANGE.MOD_BY FROM CLW_INTERCHANGE");
                where = pCriteria.getSqlClause("CLW_INTERCHANGE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INTERCHANGE.equals(otherTable)){
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
        InterchangeDataVector v = new InterchangeDataVector();
        while (rs.next()) {
            InterchangeData x = InterchangeData.createValue();
            
            x.setInterchangeId(rs.getInt(1));
            x.setTradingProfileId(rs.getInt(2));
            x.setInterchangeType(rs.getString(3));
            x.setInterchangeSender(rs.getString(4));
            x.setInterchangeReceiver(rs.getString(5));
            x.setInterchangeControlNum(rs.getInt(6));
            x.setTestInd(rs.getString(7));
            x.setEdiFileName(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a InterchangeDataVector object that consists
     * of InterchangeData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for InterchangeData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new InterchangeDataVector()
     * @throws            SQLException
     */
    public static InterchangeDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        InterchangeDataVector v = new InterchangeDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT INTERCHANGE_ID,TRADING_PROFILE_ID,INTERCHANGE_TYPE,INTERCHANGE_SENDER,INTERCHANGE_RECEIVER,INTERCHANGE_CONTROL_NUM,TEST_IND,EDI_FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INTERCHANGE WHERE INTERCHANGE_ID IN (");

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
            InterchangeData x=null;
            while (rs.next()) {
                // build the object
                x=InterchangeData.createValue();
                
                x.setInterchangeId(rs.getInt(1));
                x.setTradingProfileId(rs.getInt(2));
                x.setInterchangeType(rs.getString(3));
                x.setInterchangeSender(rs.getString(4));
                x.setInterchangeReceiver(rs.getString(5));
                x.setInterchangeControlNum(rs.getInt(6));
                x.setTestInd(rs.getString(7));
                x.setEdiFileName(rs.getString(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setAddBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a InterchangeDataVector object of all
     * InterchangeData objects in the database.
     * @param pCon An open database connection.
     * @return new InterchangeDataVector()
     * @throws            SQLException
     */
    public static InterchangeDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT INTERCHANGE_ID,TRADING_PROFILE_ID,INTERCHANGE_TYPE,INTERCHANGE_SENDER,INTERCHANGE_RECEIVER,INTERCHANGE_CONTROL_NUM,TEST_IND,EDI_FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INTERCHANGE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        InterchangeDataVector v = new InterchangeDataVector();
        InterchangeData x = null;
        while (rs.next()) {
            // build the object
            x = InterchangeData.createValue();
            
            x.setInterchangeId(rs.getInt(1));
            x.setTradingProfileId(rs.getInt(2));
            x.setInterchangeType(rs.getString(3));
            x.setInterchangeSender(rs.getString(4));
            x.setInterchangeReceiver(rs.getString(5));
            x.setInterchangeControlNum(rs.getInt(6));
            x.setTestInd(rs.getString(7));
            x.setEdiFileName(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * InterchangeData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT INTERCHANGE_ID FROM CLW_INTERCHANGE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INTERCHANGE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INTERCHANGE");
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
     * Inserts a InterchangeData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InterchangeData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new InterchangeData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InterchangeData insert(Connection pCon, InterchangeData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_INTERCHANGE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_INTERCHANGE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setInterchangeId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_INTERCHANGE (INTERCHANGE_ID,TRADING_PROFILE_ID,INTERCHANGE_TYPE,INTERCHANGE_SENDER,INTERCHANGE_RECEIVER,INTERCHANGE_CONTROL_NUM,TEST_IND,EDI_FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getInterchangeId());
        if (pData.getTradingProfileId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getTradingProfileId());
        }

        pstmt.setString(3,pData.getInterchangeType());
        pstmt.setString(4,pData.getInterchangeSender());
        pstmt.setString(5,pData.getInterchangeReceiver());
        pstmt.setInt(6,pData.getInterchangeControlNum());
        pstmt.setString(7,pData.getTestInd());
        pstmt.setString(8,pData.getEdiFileName());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INTERCHANGE_ID="+pData.getInterchangeId());
            log.debug("SQL:   TRADING_PROFILE_ID="+pData.getTradingProfileId());
            log.debug("SQL:   INTERCHANGE_TYPE="+pData.getInterchangeType());
            log.debug("SQL:   INTERCHANGE_SENDER="+pData.getInterchangeSender());
            log.debug("SQL:   INTERCHANGE_RECEIVER="+pData.getInterchangeReceiver());
            log.debug("SQL:   INTERCHANGE_CONTROL_NUM="+pData.getInterchangeControlNum());
            log.debug("SQL:   TEST_IND="+pData.getTestInd());
            log.debug("SQL:   EDI_FILE_NAME="+pData.getEdiFileName());
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
        pData.setInterchangeId(0);
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
     * Updates a InterchangeData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InterchangeData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InterchangeData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_INTERCHANGE SET TRADING_PROFILE_ID = ?,INTERCHANGE_TYPE = ?,INTERCHANGE_SENDER = ?,INTERCHANGE_RECEIVER = ?,INTERCHANGE_CONTROL_NUM = ?,TEST_IND = ?,EDI_FILE_NAME = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE INTERCHANGE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getTradingProfileId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getTradingProfileId());
        }

        pstmt.setString(i++,pData.getInterchangeType());
        pstmt.setString(i++,pData.getInterchangeSender());
        pstmt.setString(i++,pData.getInterchangeReceiver());
        pstmt.setInt(i++,pData.getInterchangeControlNum());
        pstmt.setString(i++,pData.getTestInd());
        pstmt.setString(i++,pData.getEdiFileName());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getInterchangeId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TRADING_PROFILE_ID="+pData.getTradingProfileId());
            log.debug("SQL:   INTERCHANGE_TYPE="+pData.getInterchangeType());
            log.debug("SQL:   INTERCHANGE_SENDER="+pData.getInterchangeSender());
            log.debug("SQL:   INTERCHANGE_RECEIVER="+pData.getInterchangeReceiver());
            log.debug("SQL:   INTERCHANGE_CONTROL_NUM="+pData.getInterchangeControlNum());
            log.debug("SQL:   TEST_IND="+pData.getTestInd());
            log.debug("SQL:   EDI_FILE_NAME="+pData.getEdiFileName());
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
     * Deletes a InterchangeData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInterchangeId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInterchangeId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_INTERCHANGE WHERE INTERCHANGE_ID = " + pInterchangeId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes InterchangeData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_INTERCHANGE");
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
     * Inserts a InterchangeData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InterchangeData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, InterchangeData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_INTERCHANGE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "INTERCHANGE_ID,TRADING_PROFILE_ID,INTERCHANGE_TYPE,INTERCHANGE_SENDER,INTERCHANGE_RECEIVER,INTERCHANGE_CONTROL_NUM,TEST_IND,EDI_FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getInterchangeId());
        if (pData.getTradingProfileId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getTradingProfileId());
        }

        pstmt.setString(3+4,pData.getInterchangeType());
        pstmt.setString(4+4,pData.getInterchangeSender());
        pstmt.setString(5+4,pData.getInterchangeReceiver());
        pstmt.setInt(6+4,pData.getInterchangeControlNum());
        pstmt.setString(7+4,pData.getTestInd());
        pstmt.setString(8+4,pData.getEdiFileName());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a InterchangeData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InterchangeData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new InterchangeData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InterchangeData insert(Connection pCon, InterchangeData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a InterchangeData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InterchangeData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InterchangeData pData, boolean pLogFl)
        throws SQLException {
        InterchangeData oldData = null;
        if(pLogFl) {
          int id = pData.getInterchangeId();
          try {
          oldData = InterchangeDataAccess.select(pCon,id);
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
     * Deletes a InterchangeData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInterchangeId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInterchangeId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_INTERCHANGE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INTERCHANGE d WHERE INTERCHANGE_ID = " + pInterchangeId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pInterchangeId);
        return n;
     }

    /**
     * Deletes InterchangeData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_INTERCHANGE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INTERCHANGE d ");
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

