
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        HistoryObjectDataAccess
 * Description:  This class is used to build access methods to the CLW_HISTORY_OBJECT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.HistoryObjectData;
import com.cleanwise.service.api.value.HistoryObjectDataVector;
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
 * <code>HistoryObjectDataAccess</code>
 */
public class HistoryObjectDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(HistoryObjectDataAccess.class.getName());

    /** <code>CLW_HISTORY_OBJECT</code> table name */
	/* Primary key: HISTORY_OBJECT_ID */
	
    public static final String CLW_HISTORY_OBJECT = "CLW_HISTORY_OBJECT";
    
    /** <code>HISTORY_OBJECT_ID</code> HISTORY_OBJECT_ID column of table CLW_HISTORY_OBJECT */
    public static final String HISTORY_OBJECT_ID = "HISTORY_OBJECT_ID";
    /** <code>HISTORY_ID</code> HISTORY_ID column of table CLW_HISTORY_OBJECT */
    public static final String HISTORY_ID = "HISTORY_ID";
    /** <code>OBJECT_ID</code> OBJECT_ID column of table CLW_HISTORY_OBJECT */
    public static final String OBJECT_ID = "OBJECT_ID";
    /** <code>OBJECT_TYPE_CD</code> OBJECT_TYPE_CD column of table CLW_HISTORY_OBJECT */
    public static final String OBJECT_TYPE_CD = "OBJECT_TYPE_CD";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_HISTORY_OBJECT */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_HISTORY_OBJECT */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_HISTORY_OBJECT */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_HISTORY_OBJECT */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public HistoryObjectDataAccess()
    {
    }

    /**
     * Gets a HistoryObjectData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pHistoryObjectId The key requested.
     * @return new HistoryObjectData()
     * @throws            SQLException
     */
    public static HistoryObjectData select(Connection pCon, int pHistoryObjectId)
        throws SQLException, DataNotFoundException {
        HistoryObjectData x=null;
        String sql="SELECT HISTORY_OBJECT_ID,HISTORY_ID,OBJECT_ID,OBJECT_TYPE_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_HISTORY_OBJECT WHERE HISTORY_OBJECT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pHistoryObjectId=" + pHistoryObjectId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pHistoryObjectId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=HistoryObjectData.createValue();
            
            x.setHistoryObjectId(rs.getInt(1));
            x.setHistoryId(rs.getInt(2));
            x.setObjectId(rs.getInt(3));
            x.setObjectTypeCd(rs.getString(4));
            x.setAddBy(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("HISTORY_OBJECT_ID :" + pHistoryObjectId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a HistoryObjectDataVector object that consists
     * of HistoryObjectData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new HistoryObjectDataVector()
     * @throws            SQLException
     */
    public static HistoryObjectDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a HistoryObjectData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_HISTORY_OBJECT.HISTORY_OBJECT_ID,CLW_HISTORY_OBJECT.HISTORY_ID,CLW_HISTORY_OBJECT.OBJECT_ID,CLW_HISTORY_OBJECT.OBJECT_TYPE_CD,CLW_HISTORY_OBJECT.ADD_BY,CLW_HISTORY_OBJECT.ADD_DATE,CLW_HISTORY_OBJECT.MOD_BY,CLW_HISTORY_OBJECT.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated HistoryObjectData Object.
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
    *@returns a populated HistoryObjectData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         HistoryObjectData x = HistoryObjectData.createValue();
         
         x.setHistoryObjectId(rs.getInt(1+offset));
         x.setHistoryId(rs.getInt(2+offset));
         x.setObjectId(rs.getInt(3+offset));
         x.setObjectTypeCd(rs.getString(4+offset));
         x.setAddBy(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setModBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the HistoryObjectData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a HistoryObjectDataVector object that consists
     * of HistoryObjectData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new HistoryObjectDataVector()
     * @throws            SQLException
     */
    public static HistoryObjectDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT HISTORY_OBJECT_ID,HISTORY_ID,OBJECT_ID,OBJECT_TYPE_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_HISTORY_OBJECT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_HISTORY_OBJECT.HISTORY_OBJECT_ID,CLW_HISTORY_OBJECT.HISTORY_ID,CLW_HISTORY_OBJECT.OBJECT_ID,CLW_HISTORY_OBJECT.OBJECT_TYPE_CD,CLW_HISTORY_OBJECT.ADD_BY,CLW_HISTORY_OBJECT.ADD_DATE,CLW_HISTORY_OBJECT.MOD_BY,CLW_HISTORY_OBJECT.MOD_DATE FROM CLW_HISTORY_OBJECT");
                where = pCriteria.getSqlClause("CLW_HISTORY_OBJECT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_HISTORY_OBJECT.equals(otherTable)){
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
        HistoryObjectDataVector v = new HistoryObjectDataVector();
        while (rs.next()) {
            HistoryObjectData x = HistoryObjectData.createValue();
            
            x.setHistoryObjectId(rs.getInt(1));
            x.setHistoryId(rs.getInt(2));
            x.setObjectId(rs.getInt(3));
            x.setObjectTypeCd(rs.getString(4));
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
     * Gets a HistoryObjectDataVector object that consists
     * of HistoryObjectData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for HistoryObjectData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new HistoryObjectDataVector()
     * @throws            SQLException
     */
    public static HistoryObjectDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        HistoryObjectDataVector v = new HistoryObjectDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT HISTORY_OBJECT_ID,HISTORY_ID,OBJECT_ID,OBJECT_TYPE_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_HISTORY_OBJECT WHERE HISTORY_OBJECT_ID IN (");

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
            HistoryObjectData x=null;
            while (rs.next()) {
                // build the object
                x=HistoryObjectData.createValue();
                
                x.setHistoryObjectId(rs.getInt(1));
                x.setHistoryId(rs.getInt(2));
                x.setObjectId(rs.getInt(3));
                x.setObjectTypeCd(rs.getString(4));
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
     * Gets a HistoryObjectDataVector object of all
     * HistoryObjectData objects in the database.
     * @param pCon An open database connection.
     * @return new HistoryObjectDataVector()
     * @throws            SQLException
     */
    public static HistoryObjectDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT HISTORY_OBJECT_ID,HISTORY_ID,OBJECT_ID,OBJECT_TYPE_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_HISTORY_OBJECT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        HistoryObjectDataVector v = new HistoryObjectDataVector();
        HistoryObjectData x = null;
        while (rs.next()) {
            // build the object
            x = HistoryObjectData.createValue();
            
            x.setHistoryObjectId(rs.getInt(1));
            x.setHistoryId(rs.getInt(2));
            x.setObjectId(rs.getInt(3));
            x.setObjectTypeCd(rs.getString(4));
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
     * HistoryObjectData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT HISTORY_OBJECT_ID FROM CLW_HISTORY_OBJECT");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT HISTORY_OBJECT_ID FROM CLW_HISTORY_OBJECT");
                where = pCriteria.getSqlClause("CLW_HISTORY_OBJECT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_HISTORY_OBJECT.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_HISTORY_OBJECT");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_HISTORY_OBJECT");
                where = pCriteria.getSqlClause("CLW_HISTORY_OBJECT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_HISTORY_OBJECT.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_HISTORY_OBJECT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_HISTORY_OBJECT");
                where = pCriteria.getSqlClause("CLW_HISTORY_OBJECT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_HISTORY_OBJECT.equals(otherTable)){
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
            log.debug("SQL text: " + sql);
        }

        return sql;
    }

    /**
     * Inserts a HistoryObjectData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A HistoryObjectData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new HistoryObjectData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static HistoryObjectData insert(Connection pCon, HistoryObjectData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_HISTORY_OBJECT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_HISTORY_OBJECT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setHistoryObjectId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_HISTORY_OBJECT (HISTORY_OBJECT_ID,HISTORY_ID,OBJECT_ID,OBJECT_TYPE_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getHistoryObjectId());
        pstmt.setInt(2,pData.getHistoryId());
        pstmt.setInt(3,pData.getObjectId());
        pstmt.setString(4,pData.getObjectTypeCd());
        pstmt.setString(5,pData.getAddBy());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getModBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   HISTORY_OBJECT_ID="+pData.getHistoryObjectId());
            log.debug("SQL:   HISTORY_ID="+pData.getHistoryId());
            log.debug("SQL:   OBJECT_ID="+pData.getObjectId());
            log.debug("SQL:   OBJECT_TYPE_CD="+pData.getObjectTypeCd());
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
        pData.setHistoryObjectId(0);
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
     * Updates a HistoryObjectData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A HistoryObjectData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, HistoryObjectData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_HISTORY_OBJECT SET HISTORY_ID = ?,OBJECT_ID = ?,OBJECT_TYPE_CD = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE HISTORY_OBJECT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getHistoryId());
        pstmt.setInt(i++,pData.getObjectId());
        pstmt.setString(i++,pData.getObjectTypeCd());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getHistoryObjectId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   HISTORY_ID="+pData.getHistoryId());
            log.debug("SQL:   OBJECT_ID="+pData.getObjectId());
            log.debug("SQL:   OBJECT_TYPE_CD="+pData.getObjectTypeCd());
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
     * Deletes a HistoryObjectData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pHistoryObjectId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pHistoryObjectId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_HISTORY_OBJECT WHERE HISTORY_OBJECT_ID = " + pHistoryObjectId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes HistoryObjectData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_HISTORY_OBJECT");
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
     * Inserts a HistoryObjectData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A HistoryObjectData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, HistoryObjectData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_HISTORY_OBJECT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "HISTORY_OBJECT_ID,HISTORY_ID,OBJECT_ID,OBJECT_TYPE_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getHistoryObjectId());
        pstmt.setInt(2+4,pData.getHistoryId());
        pstmt.setInt(3+4,pData.getObjectId());
        pstmt.setString(4+4,pData.getObjectTypeCd());
        pstmt.setString(5+4,pData.getAddBy());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getModBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a HistoryObjectData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A HistoryObjectData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new HistoryObjectData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static HistoryObjectData insert(Connection pCon, HistoryObjectData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a HistoryObjectData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A HistoryObjectData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, HistoryObjectData pData, boolean pLogFl)
        throws SQLException {
        HistoryObjectData oldData = null;
        if(pLogFl) {
          int id = pData.getHistoryObjectId();
          try {
          oldData = HistoryObjectDataAccess.select(pCon,id);
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
     * Deletes a HistoryObjectData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pHistoryObjectId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pHistoryObjectId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_HISTORY_OBJECT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_HISTORY_OBJECT d WHERE HISTORY_OBJECT_ID = " + pHistoryObjectId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pHistoryObjectId);
        return n;
     }

    /**
     * Deletes HistoryObjectData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_HISTORY_OBJECT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_HISTORY_OBJECT d ");
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

