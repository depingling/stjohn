
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        BlanketPoNumAssocDataAccess
 * Description:  This class is used to build access methods to the CLW_BLANKET_PO_NUM_ASSOC table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.BlanketPoNumAssocData;
import com.cleanwise.service.api.value.BlanketPoNumAssocDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.cachecos.Cachecos;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>BlanketPoNumAssocDataAccess</code>
 */
public class BlanketPoNumAssocDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(BlanketPoNumAssocDataAccess.class.getName());

    /** <code>CLW_BLANKET_PO_NUM_ASSOC</code> table name */
	/* Primary key: BLANKET_PO_NUM_ASSOC_ID */
	
    public static final String CLW_BLANKET_PO_NUM_ASSOC = "CLW_BLANKET_PO_NUM_ASSOC";
    
    /** <code>BLANKET_PO_NUM_ASSOC_ID</code> BLANKET_PO_NUM_ASSOC_ID column of table CLW_BLANKET_PO_NUM_ASSOC */
    public static final String BLANKET_PO_NUM_ASSOC_ID = "BLANKET_PO_NUM_ASSOC_ID";
    /** <code>BLANKET_PO_NUM_ID</code> BLANKET_PO_NUM_ID column of table CLW_BLANKET_PO_NUM_ASSOC */
    public static final String BLANKET_PO_NUM_ID = "BLANKET_PO_NUM_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_BLANKET_PO_NUM_ASSOC */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_BLANKET_PO_NUM_ASSOC */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_BLANKET_PO_NUM_ASSOC */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_BLANKET_PO_NUM_ASSOC */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_BLANKET_PO_NUM_ASSOC */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public BlanketPoNumAssocDataAccess()
    {
    }

    /**
     * Gets a BlanketPoNumAssocData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pBlanketPoNumAssocId The key requested.
     * @return new BlanketPoNumAssocData()
     * @throws            SQLException
     */
    public static BlanketPoNumAssocData select(Connection pCon, int pBlanketPoNumAssocId)
        throws SQLException, DataNotFoundException {
        BlanketPoNumAssocData x=null;
        String sql="SELECT BLANKET_PO_NUM_ASSOC_ID,BLANKET_PO_NUM_ID,BUS_ENTITY_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BLANKET_PO_NUM_ASSOC WHERE BLANKET_PO_NUM_ASSOC_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pBlanketPoNumAssocId=" + pBlanketPoNumAssocId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pBlanketPoNumAssocId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=BlanketPoNumAssocData.createValue();
            
            x.setBlanketPoNumAssocId(rs.getInt(1));
            x.setBlanketPoNumId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setAddBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("BLANKET_PO_NUM_ASSOC_ID :" + pBlanketPoNumAssocId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a BlanketPoNumAssocDataVector object that consists
     * of BlanketPoNumAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new BlanketPoNumAssocDataVector()
     * @throws            SQLException
     */
    public static BlanketPoNumAssocDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a BlanketPoNumAssocData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_BLANKET_PO_NUM_ASSOC.BLANKET_PO_NUM_ASSOC_ID,CLW_BLANKET_PO_NUM_ASSOC.BLANKET_PO_NUM_ID,CLW_BLANKET_PO_NUM_ASSOC.BUS_ENTITY_ID,CLW_BLANKET_PO_NUM_ASSOC.ADD_DATE,CLW_BLANKET_PO_NUM_ASSOC.ADD_BY,CLW_BLANKET_PO_NUM_ASSOC.MOD_DATE,CLW_BLANKET_PO_NUM_ASSOC.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated BlanketPoNumAssocData Object.
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
    *@returns a populated BlanketPoNumAssocData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         BlanketPoNumAssocData x = BlanketPoNumAssocData.createValue();
         
         x.setBlanketPoNumAssocId(rs.getInt(1+offset));
         x.setBlanketPoNumId(rs.getInt(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setAddDate(rs.getTimestamp(4+offset));
         x.setAddBy(rs.getString(5+offset));
         x.setModDate(rs.getTimestamp(6+offset));
         x.setModBy(rs.getString(7+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the BlanketPoNumAssocData Object represents.
    */
    public int getColumnCount(){
        return 7;
    }

    /**
     * Gets a BlanketPoNumAssocDataVector object that consists
     * of BlanketPoNumAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new BlanketPoNumAssocDataVector()
     * @throws            SQLException
     */
    public static BlanketPoNumAssocDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT BLANKET_PO_NUM_ASSOC_ID,BLANKET_PO_NUM_ID,BUS_ENTITY_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BLANKET_PO_NUM_ASSOC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_BLANKET_PO_NUM_ASSOC.BLANKET_PO_NUM_ASSOC_ID,CLW_BLANKET_PO_NUM_ASSOC.BLANKET_PO_NUM_ID,CLW_BLANKET_PO_NUM_ASSOC.BUS_ENTITY_ID,CLW_BLANKET_PO_NUM_ASSOC.ADD_DATE,CLW_BLANKET_PO_NUM_ASSOC.ADD_BY,CLW_BLANKET_PO_NUM_ASSOC.MOD_DATE,CLW_BLANKET_PO_NUM_ASSOC.MOD_BY FROM CLW_BLANKET_PO_NUM_ASSOC");
                where = pCriteria.getSqlClause("CLW_BLANKET_PO_NUM_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_BLANKET_PO_NUM_ASSOC.equals(otherTable)){
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
        BlanketPoNumAssocDataVector v = new BlanketPoNumAssocDataVector();
        while (rs.next()) {
            BlanketPoNumAssocData x = BlanketPoNumAssocData.createValue();
            
            x.setBlanketPoNumAssocId(rs.getInt(1));
            x.setBlanketPoNumId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
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
     * Gets a BlanketPoNumAssocDataVector object that consists
     * of BlanketPoNumAssocData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for BlanketPoNumAssocData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new BlanketPoNumAssocDataVector()
     * @throws            SQLException
     */
    public static BlanketPoNumAssocDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        BlanketPoNumAssocDataVector v = new BlanketPoNumAssocDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT BLANKET_PO_NUM_ASSOC_ID,BLANKET_PO_NUM_ID,BUS_ENTITY_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BLANKET_PO_NUM_ASSOC WHERE BLANKET_PO_NUM_ASSOC_ID IN (");

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
            BlanketPoNumAssocData x=null;
            while (rs.next()) {
                // build the object
                x=BlanketPoNumAssocData.createValue();
                
                x.setBlanketPoNumAssocId(rs.getInt(1));
                x.setBlanketPoNumId(rs.getInt(2));
                x.setBusEntityId(rs.getInt(3));
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
     * Gets a BlanketPoNumAssocDataVector object of all
     * BlanketPoNumAssocData objects in the database.
     * @param pCon An open database connection.
     * @return new BlanketPoNumAssocDataVector()
     * @throws            SQLException
     */
    public static BlanketPoNumAssocDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT BLANKET_PO_NUM_ASSOC_ID,BLANKET_PO_NUM_ID,BUS_ENTITY_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BLANKET_PO_NUM_ASSOC";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        BlanketPoNumAssocDataVector v = new BlanketPoNumAssocDataVector();
        BlanketPoNumAssocData x = null;
        while (rs.next()) {
            // build the object
            x = BlanketPoNumAssocData.createValue();
            
            x.setBlanketPoNumAssocId(rs.getInt(1));
            x.setBlanketPoNumId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
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
     * BlanketPoNumAssocData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT BLANKET_PO_NUM_ASSOC_ID FROM CLW_BLANKET_PO_NUM_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BLANKET_PO_NUM_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BLANKET_PO_NUM_ASSOC");
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
     * Inserts a BlanketPoNumAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BlanketPoNumAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new BlanketPoNumAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BlanketPoNumAssocData insert(Connection pCon, BlanketPoNumAssocData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_BLANKET_PO_NUM_ASSOC_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_BLANKET_PO_NUM_ASSOC_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setBlanketPoNumAssocId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_BLANKET_PO_NUM_ASSOC (BLANKET_PO_NUM_ASSOC_ID,BLANKET_PO_NUM_ID,BUS_ENTITY_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getBlanketPoNumAssocId());
        if (pData.getBlanketPoNumId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getBlanketPoNumId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getBusEntityId());
        }

        pstmt.setTimestamp(4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5,pData.getAddBy());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(7,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BLANKET_PO_NUM_ASSOC_ID="+pData.getBlanketPoNumAssocId());
            log.debug("SQL:   BLANKET_PO_NUM_ID="+pData.getBlanketPoNumId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        
        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setBlanketPoNumAssocId(0);
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
     * Updates a BlanketPoNumAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BlanketPoNumAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BlanketPoNumAssocData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_BLANKET_PO_NUM_ASSOC SET BLANKET_PO_NUM_ID = ?,BUS_ENTITY_ID = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE BLANKET_PO_NUM_ASSOC_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getBlanketPoNumId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBlanketPoNumId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getBlanketPoNumAssocId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BLANKET_PO_NUM_ID="+pData.getBlanketPoNumId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();
        
        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a BlanketPoNumAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBlanketPoNumAssocId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBlanketPoNumAssocId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_BLANKET_PO_NUM_ASSOC WHERE BLANKET_PO_NUM_ASSOC_ID = " + pBlanketPoNumAssocId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes BlanketPoNumAssocData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_BLANKET_PO_NUM_ASSOC");
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
     * Inserts a BlanketPoNumAssocData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BlanketPoNumAssocData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, BlanketPoNumAssocData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_BLANKET_PO_NUM_ASSOC (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "BLANKET_PO_NUM_ASSOC_ID,BLANKET_PO_NUM_ID,BUS_ENTITY_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getBlanketPoNumAssocId());
        if (pData.getBlanketPoNumId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getBlanketPoNumId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getBusEntityId());
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
     * Inserts a BlanketPoNumAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BlanketPoNumAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new BlanketPoNumAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BlanketPoNumAssocData insert(Connection pCon, BlanketPoNumAssocData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a BlanketPoNumAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BlanketPoNumAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BlanketPoNumAssocData pData, boolean pLogFl)
        throws SQLException {
        BlanketPoNumAssocData oldData = null;
        if(pLogFl) {
          int id = pData.getBlanketPoNumAssocId();
          try {
          oldData = BlanketPoNumAssocDataAccess.select(pCon,id);
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
     * Deletes a BlanketPoNumAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBlanketPoNumAssocId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBlanketPoNumAssocId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_BLANKET_PO_NUM_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BLANKET_PO_NUM_ASSOC d WHERE BLANKET_PO_NUM_ASSOC_ID = " + pBlanketPoNumAssocId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pBlanketPoNumAssocId);
        return n;
     }

    /**
     * Deletes BlanketPoNumAssocData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_BLANKET_PO_NUM_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BLANKET_PO_NUM_ASSOC d ");
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

