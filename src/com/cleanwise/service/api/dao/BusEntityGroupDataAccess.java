
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        BusEntityGroupDataAccess
 * Description:  This class is used to build access methods to the CLW_BUS_ENTITY_GROUP table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.BusEntityGroupData;
import com.cleanwise.service.api.value.BusEntityGroupDataVector;
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
 * <code>BusEntityGroupDataAccess</code>
 */
public class BusEntityGroupDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(BusEntityGroupDataAccess.class.getName());

    /** <code>CLW_BUS_ENTITY_GROUP</code> table name */
	/* Primary key: BUS_ENTITY_GROUP_ID */
	
    public static final String CLW_BUS_ENTITY_GROUP = "CLW_BUS_ENTITY_GROUP";
    
    /** <code>BUS_ENTITY_GROUP_ID</code> BUS_ENTITY_GROUP_ID column of table CLW_BUS_ENTITY_GROUP */
    public static final String BUS_ENTITY_GROUP_ID = "BUS_ENTITY_GROUP_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_BUS_ENTITY_GROUP */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>GROUP_ID</code> GROUP_ID column of table CLW_BUS_ENTITY_GROUP */
    public static final String GROUP_ID = "GROUP_ID";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_BUS_ENTITY_GROUP */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_BUS_ENTITY_GROUP */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_BUS_ENTITY_GROUP */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_BUS_ENTITY_GROUP */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public BusEntityGroupDataAccess()
    {
    }

    /**
     * Gets a BusEntityGroupData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pBusEntityGroupId The key requested.
     * @return new BusEntityGroupData()
     * @throws            SQLException
     */
    public static BusEntityGroupData select(Connection pCon, int pBusEntityGroupId)
        throws SQLException, DataNotFoundException {
        BusEntityGroupData x=null;
        String sql="SELECT BUS_ENTITY_GROUP_ID,BUS_ENTITY_ID,GROUP_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUS_ENTITY_GROUP WHERE BUS_ENTITY_GROUP_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pBusEntityGroupId=" + pBusEntityGroupId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pBusEntityGroupId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=BusEntityGroupData.createValue();
            
            x.setBusEntityGroupId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setGroupId(rs.getInt(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setAddBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("BUS_ENTITY_GROUP_ID :" + pBusEntityGroupId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a BusEntityGroupDataVector object that consists
     * of BusEntityGroupData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new BusEntityGroupDataVector()
     * @throws            SQLException
     */
    public static BusEntityGroupDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a BusEntityGroupData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_BUS_ENTITY_GROUP.BUS_ENTITY_GROUP_ID,CLW_BUS_ENTITY_GROUP.BUS_ENTITY_ID,CLW_BUS_ENTITY_GROUP.GROUP_ID,CLW_BUS_ENTITY_GROUP.ADD_DATE,CLW_BUS_ENTITY_GROUP.ADD_BY,CLW_BUS_ENTITY_GROUP.MOD_DATE,CLW_BUS_ENTITY_GROUP.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated BusEntityGroupData Object.
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
    *@returns a populated BusEntityGroupData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         BusEntityGroupData x = BusEntityGroupData.createValue();
         
         x.setBusEntityGroupId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setGroupId(rs.getInt(3+offset));
         x.setAddDate(rs.getTimestamp(4+offset));
         x.setAddBy(rs.getString(5+offset));
         x.setModDate(rs.getTimestamp(6+offset));
         x.setModBy(rs.getString(7+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the BusEntityGroupData Object represents.
    */
    public int getColumnCount(){
        return 7;
    }

    /**
     * Gets a BusEntityGroupDataVector object that consists
     * of BusEntityGroupData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new BusEntityGroupDataVector()
     * @throws            SQLException
     */
    public static BusEntityGroupDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT BUS_ENTITY_GROUP_ID,BUS_ENTITY_ID,GROUP_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUS_ENTITY_GROUP");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_BUS_ENTITY_GROUP.BUS_ENTITY_GROUP_ID,CLW_BUS_ENTITY_GROUP.BUS_ENTITY_ID,CLW_BUS_ENTITY_GROUP.GROUP_ID,CLW_BUS_ENTITY_GROUP.ADD_DATE,CLW_BUS_ENTITY_GROUP.ADD_BY,CLW_BUS_ENTITY_GROUP.MOD_DATE,CLW_BUS_ENTITY_GROUP.MOD_BY FROM CLW_BUS_ENTITY_GROUP");
                where = pCriteria.getSqlClause("CLW_BUS_ENTITY_GROUP");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_BUS_ENTITY_GROUP.equals(otherTable)){
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
        BusEntityGroupDataVector v = new BusEntityGroupDataVector();
        while (rs.next()) {
            BusEntityGroupData x = BusEntityGroupData.createValue();
            
            x.setBusEntityGroupId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setGroupId(rs.getInt(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setAddBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a BusEntityGroupDataVector object that consists
     * of BusEntityGroupData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for BusEntityGroupData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new BusEntityGroupDataVector()
     * @throws            SQLException
     */
    public static BusEntityGroupDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        BusEntityGroupDataVector v = new BusEntityGroupDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT BUS_ENTITY_GROUP_ID,BUS_ENTITY_ID,GROUP_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUS_ENTITY_GROUP WHERE BUS_ENTITY_GROUP_ID IN (");

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
            BusEntityGroupData x=null;
            while (rs.next()) {
                // build the object
                x=BusEntityGroupData.createValue();
                
                x.setBusEntityGroupId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setGroupId(rs.getInt(3));
                x.setAddDate(rs.getTimestamp(4));
                x.setAddBy(rs.getString(5));
                x.setModDate(rs.getTimestamp(6));
                x.setModBy(rs.getString(7));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a BusEntityGroupDataVector object of all
     * BusEntityGroupData objects in the database.
     * @param pCon An open database connection.
     * @return new BusEntityGroupDataVector()
     * @throws            SQLException
     */
    public static BusEntityGroupDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT BUS_ENTITY_GROUP_ID,BUS_ENTITY_ID,GROUP_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUS_ENTITY_GROUP";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        BusEntityGroupDataVector v = new BusEntityGroupDataVector();
        BusEntityGroupData x = null;
        while (rs.next()) {
            // build the object
            x = BusEntityGroupData.createValue();
            
            x.setBusEntityGroupId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setGroupId(rs.getInt(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setAddBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * BusEntityGroupData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT BUS_ENTITY_GROUP_ID FROM CLW_BUS_ENTITY_GROUP");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BUS_ENTITY_GROUP");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BUS_ENTITY_GROUP");
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
     * Inserts a BusEntityGroupData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityGroupData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new BusEntityGroupData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BusEntityGroupData insert(Connection pCon, BusEntityGroupData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_BUS_ENTITY_GROUP_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_BUS_ENTITY_GROUP_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setBusEntityGroupId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_BUS_ENTITY_GROUP (BUS_ENTITY_GROUP_ID,BUS_ENTITY_ID,GROUP_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getBusEntityGroupId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getBusEntityId());
        }

        if (pData.getGroupId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getGroupId());
        }

        pstmt.setTimestamp(4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5,pData.getAddBy());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(7,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_GROUP_ID="+pData.getBusEntityGroupId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   GROUP_ID="+pData.getGroupId());
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
        pData.setBusEntityGroupId(0);
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
     * Updates a BusEntityGroupData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityGroupData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BusEntityGroupData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_BUS_ENTITY_GROUP SET BUS_ENTITY_ID = ?,GROUP_ID = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE BUS_ENTITY_GROUP_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        if (pData.getGroupId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getGroupId());
        }

        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getBusEntityGroupId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   GROUP_ID="+pData.getGroupId());
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
     * Deletes a BusEntityGroupData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBusEntityGroupId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBusEntityGroupId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_BUS_ENTITY_GROUP WHERE BUS_ENTITY_GROUP_ID = " + pBusEntityGroupId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes BusEntityGroupData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_BUS_ENTITY_GROUP");
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
     * Inserts a BusEntityGroupData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityGroupData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, BusEntityGroupData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_BUS_ENTITY_GROUP (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "BUS_ENTITY_GROUP_ID,BUS_ENTITY_ID,GROUP_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getBusEntityGroupId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getBusEntityId());
        }

        if (pData.getGroupId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getGroupId());
        }

        pstmt.setTimestamp(4+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5+4,pData.getAddBy());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(7+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a BusEntityGroupData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityGroupData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new BusEntityGroupData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BusEntityGroupData insert(Connection pCon, BusEntityGroupData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a BusEntityGroupData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityGroupData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BusEntityGroupData pData, boolean pLogFl)
        throws SQLException {
        BusEntityGroupData oldData = null;
        if(pLogFl) {
          int id = pData.getBusEntityGroupId();
          try {
          oldData = BusEntityGroupDataAccess.select(pCon,id);
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
     * Deletes a BusEntityGroupData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBusEntityGroupId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBusEntityGroupId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_BUS_ENTITY_GROUP SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BUS_ENTITY_GROUP d WHERE BUS_ENTITY_GROUP_ID = " + pBusEntityGroupId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pBusEntityGroupId);
        return n;
     }

    /**
     * Deletes BusEntityGroupData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_BUS_ENTITY_GROUP SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BUS_ENTITY_GROUP d ");
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

