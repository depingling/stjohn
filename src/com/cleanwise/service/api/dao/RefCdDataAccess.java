
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        RefCdDataAccess
 * Description:  This class is used to build access methods to the CLW_REF_CD table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
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
 * <code>RefCdDataAccess</code>
 */
public class RefCdDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(RefCdDataAccess.class.getName());

    /** <code>CLW_REF_CD</code> table name */
	/* Primary key: REF_CD_ID */
	
    public static final String CLW_REF_CD = "CLW_REF_CD";
    
    /** <code>REF_CD_ID</code> REF_CD_ID column of table CLW_REF_CD */
    public static final String REF_CD_ID = "REF_CD_ID";
    /** <code>REF_CD</code> REF_CD column of table CLW_REF_CD */
    public static final String REF_CD = "REF_CD";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_REF_CD */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_REF_CD */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>REF_CD_TYPE</code> REF_CD_TYPE column of table CLW_REF_CD */
    public static final String REF_CD_TYPE = "REF_CD_TYPE";
    /** <code>REF_STATUS_CD</code> REF_STATUS_CD column of table CLW_REF_CD */
    public static final String REF_STATUS_CD = "REF_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_REF_CD */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_REF_CD */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_REF_CD */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_REF_CD */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public RefCdDataAccess()
    {
    }

    /**
     * Gets a RefCdData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pRefCdId The key requested.
     * @return new RefCdData()
     * @throws            SQLException
     */
    public static RefCdData select(Connection pCon, int pRefCdId)
        throws SQLException, DataNotFoundException {
        RefCdData x=null;
        String sql="SELECT REF_CD_ID,REF_CD,SHORT_DESC,CLW_VALUE,REF_CD_TYPE,REF_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_REF_CD WHERE REF_CD_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pRefCdId=" + pRefCdId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pRefCdId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=RefCdData.createValue();
            
            x.setRefCdId(rs.getInt(1));
            x.setRefCd(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setRefCdType(rs.getString(5));
            x.setRefStatusCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("REF_CD_ID :" + pRefCdId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a RefCdDataVector object that consists
     * of RefCdData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new RefCdDataVector()
     * @throws            SQLException
     */
    public static RefCdDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a RefCdData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_REF_CD.REF_CD_ID,CLW_REF_CD.REF_CD,CLW_REF_CD.SHORT_DESC,CLW_REF_CD.CLW_VALUE,CLW_REF_CD.REF_CD_TYPE,CLW_REF_CD.REF_STATUS_CD,CLW_REF_CD.ADD_DATE,CLW_REF_CD.ADD_BY,CLW_REF_CD.MOD_DATE,CLW_REF_CD.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated RefCdData Object.
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
    *@returns a populated RefCdData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         RefCdData x = RefCdData.createValue();
         
         x.setRefCdId(rs.getInt(1+offset));
         x.setRefCd(rs.getString(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setValue(rs.getString(4+offset));
         x.setRefCdType(rs.getString(5+offset));
         x.setRefStatusCd(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the RefCdData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a RefCdDataVector object that consists
     * of RefCdData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new RefCdDataVector()
     * @throws            SQLException
     */
    public static RefCdDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT REF_CD_ID,REF_CD,SHORT_DESC,CLW_VALUE,REF_CD_TYPE,REF_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_REF_CD");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_REF_CD.REF_CD_ID,CLW_REF_CD.REF_CD,CLW_REF_CD.SHORT_DESC,CLW_REF_CD.CLW_VALUE,CLW_REF_CD.REF_CD_TYPE,CLW_REF_CD.REF_STATUS_CD,CLW_REF_CD.ADD_DATE,CLW_REF_CD.ADD_BY,CLW_REF_CD.MOD_DATE,CLW_REF_CD.MOD_BY FROM CLW_REF_CD");
                where = pCriteria.getSqlClause("CLW_REF_CD");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_REF_CD.equals(otherTable)){
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
        RefCdDataVector v = new RefCdDataVector();
        while (rs.next()) {
            RefCdData x = RefCdData.createValue();
            
            x.setRefCdId(rs.getInt(1));
            x.setRefCd(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setRefCdType(rs.getString(5));
            x.setRefStatusCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a RefCdDataVector object that consists
     * of RefCdData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for RefCdData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new RefCdDataVector()
     * @throws            SQLException
     */
    public static RefCdDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        RefCdDataVector v = new RefCdDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT REF_CD_ID,REF_CD,SHORT_DESC,CLW_VALUE,REF_CD_TYPE,REF_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_REF_CD WHERE REF_CD_ID IN (");

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
            RefCdData x=null;
            while (rs.next()) {
                // build the object
                x=RefCdData.createValue();
                
                x.setRefCdId(rs.getInt(1));
                x.setRefCd(rs.getString(2));
                x.setShortDesc(rs.getString(3));
                x.setValue(rs.getString(4));
                x.setRefCdType(rs.getString(5));
                x.setRefStatusCd(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a RefCdDataVector object of all
     * RefCdData objects in the database.
     * @param pCon An open database connection.
     * @return new RefCdDataVector()
     * @throws            SQLException
     */
    public static RefCdDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT REF_CD_ID,REF_CD,SHORT_DESC,CLW_VALUE,REF_CD_TYPE,REF_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_REF_CD";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        RefCdDataVector v = new RefCdDataVector();
        RefCdData x = null;
        while (rs.next()) {
            // build the object
            x = RefCdData.createValue();
            
            x.setRefCdId(rs.getInt(1));
            x.setRefCd(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setRefCdType(rs.getString(5));
            x.setRefStatusCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * RefCdData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REF_CD_ID FROM CLW_REF_CD");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_REF_CD");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_REF_CD");
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
     * Inserts a RefCdData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A RefCdData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new RefCdData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static RefCdData insert(Connection pCon, RefCdData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_REF_CD_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_REF_CD_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setRefCdId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_REF_CD (REF_CD_ID,REF_CD,SHORT_DESC,CLW_VALUE,REF_CD_TYPE,REF_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getRefCdId());
        pstmt.setString(2,pData.getRefCd());
        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getValue());
        pstmt.setString(5,pData.getRefCdType());
        pstmt.setString(6,pData.getRefStatusCd());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REF_CD_ID="+pData.getRefCdId());
            log.debug("SQL:   REF_CD="+pData.getRefCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   REF_CD_TYPE="+pData.getRefCdType());
            log.debug("SQL:   REF_STATUS_CD="+pData.getRefStatusCd());
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
        pData.setRefCdId(0);
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
     * Updates a RefCdData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A RefCdData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, RefCdData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_REF_CD SET REF_CD = ?,SHORT_DESC = ?,CLW_VALUE = ?,REF_CD_TYPE = ?,REF_STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE REF_CD_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getRefCd());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getRefCdType());
        pstmt.setString(i++,pData.getRefStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getRefCdId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REF_CD="+pData.getRefCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   REF_CD_TYPE="+pData.getRefCdType());
            log.debug("SQL:   REF_STATUS_CD="+pData.getRefStatusCd());
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
     * Deletes a RefCdData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pRefCdId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pRefCdId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_REF_CD WHERE REF_CD_ID = " + pRefCdId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes RefCdData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_REF_CD");
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
     * Inserts a RefCdData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A RefCdData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, RefCdData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_REF_CD (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "REF_CD_ID,REF_CD,SHORT_DESC,CLW_VALUE,REF_CD_TYPE,REF_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getRefCdId());
        pstmt.setString(2+4,pData.getRefCd());
        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getValue());
        pstmt.setString(5+4,pData.getRefCdType());
        pstmt.setString(6+4,pData.getRefStatusCd());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a RefCdData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A RefCdData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new RefCdData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static RefCdData insert(Connection pCon, RefCdData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a RefCdData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A RefCdData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, RefCdData pData, boolean pLogFl)
        throws SQLException {
        RefCdData oldData = null;
        if(pLogFl) {
          int id = pData.getRefCdId();
          try {
          oldData = RefCdDataAccess.select(pCon,id);
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
     * Deletes a RefCdData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pRefCdId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pRefCdId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_REF_CD SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_REF_CD d WHERE REF_CD_ID = " + pRefCdId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pRefCdId);
        return n;
     }

    /**
     * Deletes RefCdData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_REF_CD SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_REF_CD d ");
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

