
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        StagedAssetAssocDataAccess
 * Description:  This class is used to build access methods to the CLW_STAGED_ASSET_ASSOC table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.StagedAssetAssocData;
import com.cleanwise.service.api.value.StagedAssetAssocDataVector;
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
 * <code>StagedAssetAssocDataAccess</code>
 */
public class StagedAssetAssocDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(StagedAssetAssocDataAccess.class.getName());

    /** <code>CLW_STAGED_ASSET_ASSOC</code> table name */
	/* Primary key: STAGED_ASSET_ASSOC_ID */
	
    public static final String CLW_STAGED_ASSET_ASSOC = "CLW_STAGED_ASSET_ASSOC";
    
    /** <code>STAGED_ASSET_ASSOC_ID</code> STAGED_ASSET_ASSOC_ID column of table CLW_STAGED_ASSET_ASSOC */
    public static final String STAGED_ASSET_ASSOC_ID = "STAGED_ASSET_ASSOC_ID";
    /** <code>STAGED_ASSET_ID</code> STAGED_ASSET_ID column of table CLW_STAGED_ASSET_ASSOC */
    public static final String STAGED_ASSET_ID = "STAGED_ASSET_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_STAGED_ASSET_ASSOC */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>ASSOC_CD</code> ASSOC_CD column of table CLW_STAGED_ASSET_ASSOC */
    public static final String ASSOC_CD = "ASSOC_CD";
    /** <code>ASSOC_STATUS_CD</code> ASSOC_STATUS_CD column of table CLW_STAGED_ASSET_ASSOC */
    public static final String ASSOC_STATUS_CD = "ASSOC_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_STAGED_ASSET_ASSOC */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_STAGED_ASSET_ASSOC */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_STAGED_ASSET_ASSOC */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_STAGED_ASSET_ASSOC */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public StagedAssetAssocDataAccess()
    {
    }

    /**
     * Gets a StagedAssetAssocData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pStagedAssetAssocId The key requested.
     * @return new StagedAssetAssocData()
     * @throws            SQLException
     */
    public static StagedAssetAssocData select(Connection pCon, int pStagedAssetAssocId)
        throws SQLException, DataNotFoundException {
        StagedAssetAssocData x=null;
        String sql="SELECT STAGED_ASSET_ASSOC_ID,STAGED_ASSET_ID,BUS_ENTITY_ID,ASSOC_CD,ASSOC_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_STAGED_ASSET_ASSOC WHERE STAGED_ASSET_ASSOC_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pStagedAssetAssocId=" + pStagedAssetAssocId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pStagedAssetAssocId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=StagedAssetAssocData.createValue();
            
            x.setStagedAssetAssocId(rs.getInt(1));
            x.setStagedAssetId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setAssocCd(rs.getString(4));
            x.setAssocStatusCd(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("STAGED_ASSET_ASSOC_ID :" + pStagedAssetAssocId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a StagedAssetAssocDataVector object that consists
     * of StagedAssetAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new StagedAssetAssocDataVector()
     * @throws            SQLException
     */
    public static StagedAssetAssocDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a StagedAssetAssocData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_STAGED_ASSET_ASSOC.STAGED_ASSET_ASSOC_ID,CLW_STAGED_ASSET_ASSOC.STAGED_ASSET_ID,CLW_STAGED_ASSET_ASSOC.BUS_ENTITY_ID,CLW_STAGED_ASSET_ASSOC.ASSOC_CD,CLW_STAGED_ASSET_ASSOC.ASSOC_STATUS_CD,CLW_STAGED_ASSET_ASSOC.ADD_DATE,CLW_STAGED_ASSET_ASSOC.ADD_BY,CLW_STAGED_ASSET_ASSOC.MOD_DATE,CLW_STAGED_ASSET_ASSOC.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated StagedAssetAssocData Object.
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
    *@returns a populated StagedAssetAssocData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         StagedAssetAssocData x = StagedAssetAssocData.createValue();
         
         x.setStagedAssetAssocId(rs.getInt(1+offset));
         x.setStagedAssetId(rs.getInt(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setAssocCd(rs.getString(4+offset));
         x.setAssocStatusCd(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the StagedAssetAssocData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a StagedAssetAssocDataVector object that consists
     * of StagedAssetAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new StagedAssetAssocDataVector()
     * @throws            SQLException
     */
    public static StagedAssetAssocDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT STAGED_ASSET_ASSOC_ID,STAGED_ASSET_ID,BUS_ENTITY_ID,ASSOC_CD,ASSOC_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_STAGED_ASSET_ASSOC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_STAGED_ASSET_ASSOC.STAGED_ASSET_ASSOC_ID,CLW_STAGED_ASSET_ASSOC.STAGED_ASSET_ID,CLW_STAGED_ASSET_ASSOC.BUS_ENTITY_ID,CLW_STAGED_ASSET_ASSOC.ASSOC_CD,CLW_STAGED_ASSET_ASSOC.ASSOC_STATUS_CD,CLW_STAGED_ASSET_ASSOC.ADD_DATE,CLW_STAGED_ASSET_ASSOC.ADD_BY,CLW_STAGED_ASSET_ASSOC.MOD_DATE,CLW_STAGED_ASSET_ASSOC.MOD_BY FROM CLW_STAGED_ASSET_ASSOC");
                where = pCriteria.getSqlClause("CLW_STAGED_ASSET_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STAGED_ASSET_ASSOC.equals(otherTable)){
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
        StagedAssetAssocDataVector v = new StagedAssetAssocDataVector();
        while (rs.next()) {
            StagedAssetAssocData x = StagedAssetAssocData.createValue();
            
            x.setStagedAssetAssocId(rs.getInt(1));
            x.setStagedAssetId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setAssocCd(rs.getString(4));
            x.setAssocStatusCd(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a StagedAssetAssocDataVector object that consists
     * of StagedAssetAssocData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for StagedAssetAssocData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new StagedAssetAssocDataVector()
     * @throws            SQLException
     */
    public static StagedAssetAssocDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        StagedAssetAssocDataVector v = new StagedAssetAssocDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT STAGED_ASSET_ASSOC_ID,STAGED_ASSET_ID,BUS_ENTITY_ID,ASSOC_CD,ASSOC_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_STAGED_ASSET_ASSOC WHERE STAGED_ASSET_ASSOC_ID IN (");

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
            StagedAssetAssocData x=null;
            while (rs.next()) {
                // build the object
                x=StagedAssetAssocData.createValue();
                
                x.setStagedAssetAssocId(rs.getInt(1));
                x.setStagedAssetId(rs.getInt(2));
                x.setBusEntityId(rs.getInt(3));
                x.setAssocCd(rs.getString(4));
                x.setAssocStatusCd(rs.getString(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setAddBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a StagedAssetAssocDataVector object of all
     * StagedAssetAssocData objects in the database.
     * @param pCon An open database connection.
     * @return new StagedAssetAssocDataVector()
     * @throws            SQLException
     */
    public static StagedAssetAssocDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT STAGED_ASSET_ASSOC_ID,STAGED_ASSET_ID,BUS_ENTITY_ID,ASSOC_CD,ASSOC_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_STAGED_ASSET_ASSOC";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        StagedAssetAssocDataVector v = new StagedAssetAssocDataVector();
        StagedAssetAssocData x = null;
        while (rs.next()) {
            // build the object
            x = StagedAssetAssocData.createValue();
            
            x.setStagedAssetAssocId(rs.getInt(1));
            x.setStagedAssetId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setAssocCd(rs.getString(4));
            x.setAssocStatusCd(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * StagedAssetAssocData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT STAGED_ASSET_ASSOC_ID FROM CLW_STAGED_ASSET_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STAGED_ASSET_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STAGED_ASSET_ASSOC");
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
     * Inserts a StagedAssetAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StagedAssetAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new StagedAssetAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static StagedAssetAssocData insert(Connection pCon, StagedAssetAssocData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_STAGED_ASSET_ASSOC_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_STAGED_ASSET_ASSOC_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setStagedAssetAssocId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_STAGED_ASSET_ASSOC (STAGED_ASSET_ASSOC_ID,STAGED_ASSET_ID,BUS_ENTITY_ID,ASSOC_CD,ASSOC_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getStagedAssetAssocId());
        pstmt.setInt(2,pData.getStagedAssetId());
        pstmt.setInt(3,pData.getBusEntityId());
        pstmt.setString(4,pData.getAssocCd());
        pstmt.setString(5,pData.getAssocStatusCd());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STAGED_ASSET_ASSOC_ID="+pData.getStagedAssetAssocId());
            log.debug("SQL:   STAGED_ASSET_ID="+pData.getStagedAssetId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ASSOC_CD="+pData.getAssocCd());
            log.debug("SQL:   ASSOC_STATUS_CD="+pData.getAssocStatusCd());
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
        pData.setStagedAssetAssocId(0);
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
     * Updates a StagedAssetAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A StagedAssetAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, StagedAssetAssocData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_STAGED_ASSET_ASSOC SET STAGED_ASSET_ID = ?,BUS_ENTITY_ID = ?,ASSOC_CD = ?,ASSOC_STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE STAGED_ASSET_ASSOC_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getStagedAssetId());
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setString(i++,pData.getAssocCd());
        pstmt.setString(i++,pData.getAssocStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getStagedAssetAssocId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STAGED_ASSET_ID="+pData.getStagedAssetId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ASSOC_CD="+pData.getAssocCd());
            log.debug("SQL:   ASSOC_STATUS_CD="+pData.getAssocStatusCd());
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
     * Deletes a StagedAssetAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pStagedAssetAssocId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pStagedAssetAssocId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_STAGED_ASSET_ASSOC WHERE STAGED_ASSET_ASSOC_ID = " + pStagedAssetAssocId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes StagedAssetAssocData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_STAGED_ASSET_ASSOC");
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
     * Inserts a StagedAssetAssocData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StagedAssetAssocData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, StagedAssetAssocData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_STAGED_ASSET_ASSOC (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "STAGED_ASSET_ASSOC_ID,STAGED_ASSET_ID,BUS_ENTITY_ID,ASSOC_CD,ASSOC_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getStagedAssetAssocId());
        pstmt.setInt(2+4,pData.getStagedAssetId());
        pstmt.setInt(3+4,pData.getBusEntityId());
        pstmt.setString(4+4,pData.getAssocCd());
        pstmt.setString(5+4,pData.getAssocStatusCd());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a StagedAssetAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StagedAssetAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new StagedAssetAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static StagedAssetAssocData insert(Connection pCon, StagedAssetAssocData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a StagedAssetAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A StagedAssetAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, StagedAssetAssocData pData, boolean pLogFl)
        throws SQLException {
        StagedAssetAssocData oldData = null;
        if(pLogFl) {
          int id = pData.getStagedAssetAssocId();
          try {
          oldData = StagedAssetAssocDataAccess.select(pCon,id);
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
     * Deletes a StagedAssetAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pStagedAssetAssocId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pStagedAssetAssocId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_STAGED_ASSET_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_STAGED_ASSET_ASSOC d WHERE STAGED_ASSET_ASSOC_ID = " + pStagedAssetAssocId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pStagedAssetAssocId);
        return n;
     }

    /**
     * Deletes StagedAssetAssocData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_STAGED_ASSET_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_STAGED_ASSET_ASSOC d ");
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

