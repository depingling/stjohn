
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        BusEntityAssocDataAccess
 * Description:  This class is used to build access methods to the CLW_BUS_ENTITY_ASSOC table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.BusEntityAssocData;
import com.cleanwise.service.api.value.BusEntityAssocDataVector;
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
 * <code>BusEntityAssocDataAccess</code>
 */
public class BusEntityAssocDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(BusEntityAssocDataAccess.class.getName());

    /** <code>CLW_BUS_ENTITY_ASSOC</code> table name */
	/* Primary key: BUS_ENTITY_ASSOC_ID */
	
    public static final String CLW_BUS_ENTITY_ASSOC = "CLW_BUS_ENTITY_ASSOC";
    
    /** <code>BUS_ENTITY_ASSOC_ID</code> BUS_ENTITY_ASSOC_ID column of table CLW_BUS_ENTITY_ASSOC */
    public static final String BUS_ENTITY_ASSOC_ID = "BUS_ENTITY_ASSOC_ID";
    /** <code>BUS_ENTITY1_ID</code> BUS_ENTITY1_ID column of table CLW_BUS_ENTITY_ASSOC */
    public static final String BUS_ENTITY1_ID = "BUS_ENTITY1_ID";
    /** <code>BUS_ENTITY2_ID</code> BUS_ENTITY2_ID column of table CLW_BUS_ENTITY_ASSOC */
    public static final String BUS_ENTITY2_ID = "BUS_ENTITY2_ID";
    /** <code>BUS_ENTITY_ASSOC_CD</code> BUS_ENTITY_ASSOC_CD column of table CLW_BUS_ENTITY_ASSOC */
    public static final String BUS_ENTITY_ASSOC_CD = "BUS_ENTITY_ASSOC_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_BUS_ENTITY_ASSOC */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_BUS_ENTITY_ASSOC */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_BUS_ENTITY_ASSOC */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_BUS_ENTITY_ASSOC */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public BusEntityAssocDataAccess()
    {
    }

    /**
     * Gets a BusEntityAssocData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pBusEntityAssocId The key requested.
     * @return new BusEntityAssocData()
     * @throws            SQLException
     */
    public static BusEntityAssocData select(Connection pCon, int pBusEntityAssocId)
        throws SQLException, DataNotFoundException {
        BusEntityAssocData x=null;
        String sql="SELECT BUS_ENTITY_ASSOC_ID,BUS_ENTITY1_ID,BUS_ENTITY2_ID,BUS_ENTITY_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUS_ENTITY_ASSOC WHERE BUS_ENTITY_ASSOC_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pBusEntityAssocId=" + pBusEntityAssocId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pBusEntityAssocId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=BusEntityAssocData.createValue();
            
            x.setBusEntityAssocId(rs.getInt(1));
            x.setBusEntity1Id(rs.getInt(2));
            x.setBusEntity2Id(rs.getInt(3));
            x.setBusEntityAssocCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("BUS_ENTITY_ASSOC_ID :" + pBusEntityAssocId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a BusEntityAssocDataVector object that consists
     * of BusEntityAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new BusEntityAssocDataVector()
     * @throws            SQLException
     */
    public static BusEntityAssocDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a BusEntityAssocData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_BUS_ENTITY_ASSOC.BUS_ENTITY_ASSOC_ID,CLW_BUS_ENTITY_ASSOC.BUS_ENTITY1_ID,CLW_BUS_ENTITY_ASSOC.BUS_ENTITY2_ID,CLW_BUS_ENTITY_ASSOC.BUS_ENTITY_ASSOC_CD,CLW_BUS_ENTITY_ASSOC.ADD_DATE,CLW_BUS_ENTITY_ASSOC.ADD_BY,CLW_BUS_ENTITY_ASSOC.MOD_DATE,CLW_BUS_ENTITY_ASSOC.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated BusEntityAssocData Object.
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
    *@returns a populated BusEntityAssocData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         BusEntityAssocData x = BusEntityAssocData.createValue();
         
         x.setBusEntityAssocId(rs.getInt(1+offset));
         x.setBusEntity1Id(rs.getInt(2+offset));
         x.setBusEntity2Id(rs.getInt(3+offset));
         x.setBusEntityAssocCd(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the BusEntityAssocData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a BusEntityAssocDataVector object that consists
     * of BusEntityAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new BusEntityAssocDataVector()
     * @throws            SQLException
     */
    public static BusEntityAssocDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT BUS_ENTITY_ASSOC_ID,BUS_ENTITY1_ID,BUS_ENTITY2_ID,BUS_ENTITY_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUS_ENTITY_ASSOC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_BUS_ENTITY_ASSOC.BUS_ENTITY_ASSOC_ID,CLW_BUS_ENTITY_ASSOC.BUS_ENTITY1_ID,CLW_BUS_ENTITY_ASSOC.BUS_ENTITY2_ID,CLW_BUS_ENTITY_ASSOC.BUS_ENTITY_ASSOC_CD,CLW_BUS_ENTITY_ASSOC.ADD_DATE,CLW_BUS_ENTITY_ASSOC.ADD_BY,CLW_BUS_ENTITY_ASSOC.MOD_DATE,CLW_BUS_ENTITY_ASSOC.MOD_BY FROM CLW_BUS_ENTITY_ASSOC");
                where = pCriteria.getSqlClause("CLW_BUS_ENTITY_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_BUS_ENTITY_ASSOC.equals(otherTable)){
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
        BusEntityAssocDataVector v = new BusEntityAssocDataVector();
        while (rs.next()) {
            BusEntityAssocData x = BusEntityAssocData.createValue();
            
            x.setBusEntityAssocId(rs.getInt(1));
            x.setBusEntity1Id(rs.getInt(2));
            x.setBusEntity2Id(rs.getInt(3));
            x.setBusEntityAssocCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a BusEntityAssocDataVector object that consists
     * of BusEntityAssocData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for BusEntityAssocData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new BusEntityAssocDataVector()
     * @throws            SQLException
     */
    public static BusEntityAssocDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        BusEntityAssocDataVector v = new BusEntityAssocDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT BUS_ENTITY_ASSOC_ID,BUS_ENTITY1_ID,BUS_ENTITY2_ID,BUS_ENTITY_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUS_ENTITY_ASSOC WHERE BUS_ENTITY_ASSOC_ID IN (");

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
            BusEntityAssocData x=null;
            while (rs.next()) {
                // build the object
                x=BusEntityAssocData.createValue();
                
                x.setBusEntityAssocId(rs.getInt(1));
                x.setBusEntity1Id(rs.getInt(2));
                x.setBusEntity2Id(rs.getInt(3));
                x.setBusEntityAssocCd(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a BusEntityAssocDataVector object of all
     * BusEntityAssocData objects in the database.
     * @param pCon An open database connection.
     * @return new BusEntityAssocDataVector()
     * @throws            SQLException
     */
    public static BusEntityAssocDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT BUS_ENTITY_ASSOC_ID,BUS_ENTITY1_ID,BUS_ENTITY2_ID,BUS_ENTITY_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUS_ENTITY_ASSOC";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        BusEntityAssocDataVector v = new BusEntityAssocDataVector();
        BusEntityAssocData x = null;
        while (rs.next()) {
            // build the object
            x = BusEntityAssocData.createValue();
            
            x.setBusEntityAssocId(rs.getInt(1));
            x.setBusEntity1Id(rs.getInt(2));
            x.setBusEntity2Id(rs.getInt(3));
            x.setBusEntityAssocCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * BusEntityAssocData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT BUS_ENTITY_ASSOC_ID FROM CLW_BUS_ENTITY_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BUS_ENTITY_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BUS_ENTITY_ASSOC");
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
     * Inserts a BusEntityAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new BusEntityAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BusEntityAssocData insert(Connection pCon, BusEntityAssocData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_BUS_ENTITY_ASSOC_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_BUS_ENTITY_ASSOC_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setBusEntityAssocId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_BUS_ENTITY_ASSOC (BUS_ENTITY_ASSOC_ID,BUS_ENTITY1_ID,BUS_ENTITY2_ID,BUS_ENTITY_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getBusEntityAssocId());
        pstmt.setInt(2,pData.getBusEntity1Id());
        pstmt.setInt(3,pData.getBusEntity2Id());
        pstmt.setString(4,pData.getBusEntityAssocCd());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ASSOC_ID="+pData.getBusEntityAssocId());
            log.debug("SQL:   BUS_ENTITY1_ID="+pData.getBusEntity1Id());
            log.debug("SQL:   BUS_ENTITY2_ID="+pData.getBusEntity2Id());
            log.debug("SQL:   BUS_ENTITY_ASSOC_CD="+pData.getBusEntityAssocCd());
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
        pData.setBusEntityAssocId(0);
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
     * Updates a BusEntityAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BusEntityAssocData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_BUS_ENTITY_ASSOC SET BUS_ENTITY1_ID = ?,BUS_ENTITY2_ID = ?,BUS_ENTITY_ASSOC_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE BUS_ENTITY_ASSOC_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getBusEntity1Id());
        pstmt.setInt(i++,pData.getBusEntity2Id());
        pstmt.setString(i++,pData.getBusEntityAssocCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getBusEntityAssocId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY1_ID="+pData.getBusEntity1Id());
            log.debug("SQL:   BUS_ENTITY2_ID="+pData.getBusEntity2Id());
            log.debug("SQL:   BUS_ENTITY_ASSOC_CD="+pData.getBusEntityAssocCd());
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
     * Deletes a BusEntityAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBusEntityAssocId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBusEntityAssocId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_BUS_ENTITY_ASSOC WHERE BUS_ENTITY_ASSOC_ID = " + pBusEntityAssocId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes BusEntityAssocData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_BUS_ENTITY_ASSOC");
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
     * Inserts a BusEntityAssocData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityAssocData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, BusEntityAssocData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_BUS_ENTITY_ASSOC (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "BUS_ENTITY_ASSOC_ID,BUS_ENTITY1_ID,BUS_ENTITY2_ID,BUS_ENTITY_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getBusEntityAssocId());
        pstmt.setInt(2+4,pData.getBusEntity1Id());
        pstmt.setInt(3+4,pData.getBusEntity2Id());
        pstmt.setString(4+4,pData.getBusEntityAssocCd());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a BusEntityAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new BusEntityAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BusEntityAssocData insert(Connection pCon, BusEntityAssocData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a BusEntityAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BusEntityAssocData pData, boolean pLogFl)
        throws SQLException {
        BusEntityAssocData oldData = null;
        if(pLogFl) {
          int id = pData.getBusEntityAssocId();
          try {
          oldData = BusEntityAssocDataAccess.select(pCon,id);
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
     * Deletes a BusEntityAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBusEntityAssocId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBusEntityAssocId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_BUS_ENTITY_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BUS_ENTITY_ASSOC d WHERE BUS_ENTITY_ASSOC_ID = " + pBusEntityAssocId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pBusEntityAssocId);
        return n;
     }

    /**
     * Deletes BusEntityAssocData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_BUS_ENTITY_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BUS_ENTITY_ASSOC d ");
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

