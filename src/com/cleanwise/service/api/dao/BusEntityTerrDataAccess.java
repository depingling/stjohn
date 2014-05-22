
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        BusEntityTerrDataAccess
 * Description:  This class is used to build access methods to the CLW_BUS_ENTITY_TERR table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.BusEntityTerrData;
import com.cleanwise.service.api.value.BusEntityTerrDataVector;
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
 * <code>BusEntityTerrDataAccess</code>
 */
public class BusEntityTerrDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(BusEntityTerrDataAccess.class.getName());

    /** <code>CLW_BUS_ENTITY_TERR</code> table name */
	/* Primary key: BUS_ENTITY_TERR_ID */
	
    public static final String CLW_BUS_ENTITY_TERR = "CLW_BUS_ENTITY_TERR";
    
    /** <code>BUS_ENTITY_TERR_ID</code> BUS_ENTITY_TERR_ID column of table CLW_BUS_ENTITY_TERR */
    public static final String BUS_ENTITY_TERR_ID = "BUS_ENTITY_TERR_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_BUS_ENTITY_TERR */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>POSTAL_CODE_ID</code> POSTAL_CODE_ID column of table CLW_BUS_ENTITY_TERR */
    public static final String POSTAL_CODE_ID = "POSTAL_CODE_ID";
    /** <code>BUS_ENTITY_TERR_CD</code> BUS_ENTITY_TERR_CD column of table CLW_BUS_ENTITY_TERR */
    public static final String BUS_ENTITY_TERR_CD = "BUS_ENTITY_TERR_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_BUS_ENTITY_TERR */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_BUS_ENTITY_TERR */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_BUS_ENTITY_TERR */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_BUS_ENTITY_TERR */
    public static final String MOD_BY = "MOD_BY";
    /** <code>POSTAL_CODE</code> POSTAL_CODE column of table CLW_BUS_ENTITY_TERR */
    public static final String POSTAL_CODE = "POSTAL_CODE";
    /** <code>BUS_ENTITY_TERR_FREIGHT_CD</code> BUS_ENTITY_TERR_FREIGHT_CD column of table CLW_BUS_ENTITY_TERR */
    public static final String BUS_ENTITY_TERR_FREIGHT_CD = "BUS_ENTITY_TERR_FREIGHT_CD";

    /**
     * Constructor.
     */
    public BusEntityTerrDataAccess()
    {
    }

    /**
     * Gets a BusEntityTerrData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pBusEntityTerrId The key requested.
     * @return new BusEntityTerrData()
     * @throws            SQLException
     */
    public static BusEntityTerrData select(Connection pCon, int pBusEntityTerrId)
        throws SQLException, DataNotFoundException {
        BusEntityTerrData x=null;
        String sql="SELECT BUS_ENTITY_TERR_ID,BUS_ENTITY_ID,POSTAL_CODE_ID,BUS_ENTITY_TERR_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,POSTAL_CODE,BUS_ENTITY_TERR_FREIGHT_CD FROM CLW_BUS_ENTITY_TERR WHERE BUS_ENTITY_TERR_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pBusEntityTerrId=" + pBusEntityTerrId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pBusEntityTerrId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=BusEntityTerrData.createValue();
            
            x.setBusEntityTerrId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setPostalCodeId(rs.getInt(3));
            x.setBusEntityTerrCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setPostalCode(rs.getString(9));
            x.setBusEntityTerrFreightCd(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("BUS_ENTITY_TERR_ID :" + pBusEntityTerrId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a BusEntityTerrDataVector object that consists
     * of BusEntityTerrData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new BusEntityTerrDataVector()
     * @throws            SQLException
     */
    public static BusEntityTerrDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a BusEntityTerrData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_BUS_ENTITY_TERR.BUS_ENTITY_TERR_ID,CLW_BUS_ENTITY_TERR.BUS_ENTITY_ID,CLW_BUS_ENTITY_TERR.POSTAL_CODE_ID,CLW_BUS_ENTITY_TERR.BUS_ENTITY_TERR_CD,CLW_BUS_ENTITY_TERR.ADD_DATE,CLW_BUS_ENTITY_TERR.ADD_BY,CLW_BUS_ENTITY_TERR.MOD_DATE,CLW_BUS_ENTITY_TERR.MOD_BY,CLW_BUS_ENTITY_TERR.POSTAL_CODE,CLW_BUS_ENTITY_TERR.BUS_ENTITY_TERR_FREIGHT_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated BusEntityTerrData Object.
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
    *@returns a populated BusEntityTerrData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         BusEntityTerrData x = BusEntityTerrData.createValue();
         
         x.setBusEntityTerrId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setPostalCodeId(rs.getInt(3+offset));
         x.setBusEntityTerrCd(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setPostalCode(rs.getString(9+offset));
         x.setBusEntityTerrFreightCd(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the BusEntityTerrData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a BusEntityTerrDataVector object that consists
     * of BusEntityTerrData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new BusEntityTerrDataVector()
     * @throws            SQLException
     */
    public static BusEntityTerrDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT BUS_ENTITY_TERR_ID,BUS_ENTITY_ID,POSTAL_CODE_ID,BUS_ENTITY_TERR_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,POSTAL_CODE,BUS_ENTITY_TERR_FREIGHT_CD FROM CLW_BUS_ENTITY_TERR");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_BUS_ENTITY_TERR.BUS_ENTITY_TERR_ID,CLW_BUS_ENTITY_TERR.BUS_ENTITY_ID,CLW_BUS_ENTITY_TERR.POSTAL_CODE_ID,CLW_BUS_ENTITY_TERR.BUS_ENTITY_TERR_CD,CLW_BUS_ENTITY_TERR.ADD_DATE,CLW_BUS_ENTITY_TERR.ADD_BY,CLW_BUS_ENTITY_TERR.MOD_DATE,CLW_BUS_ENTITY_TERR.MOD_BY,CLW_BUS_ENTITY_TERR.POSTAL_CODE,CLW_BUS_ENTITY_TERR.BUS_ENTITY_TERR_FREIGHT_CD FROM CLW_BUS_ENTITY_TERR");
                where = pCriteria.getSqlClause("CLW_BUS_ENTITY_TERR");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_BUS_ENTITY_TERR.equals(otherTable)){
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
        BusEntityTerrDataVector v = new BusEntityTerrDataVector();
        while (rs.next()) {
            BusEntityTerrData x = BusEntityTerrData.createValue();
            
            x.setBusEntityTerrId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setPostalCodeId(rs.getInt(3));
            x.setBusEntityTerrCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setPostalCode(rs.getString(9));
            x.setBusEntityTerrFreightCd(rs.getString(10));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a BusEntityTerrDataVector object that consists
     * of BusEntityTerrData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for BusEntityTerrData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new BusEntityTerrDataVector()
     * @throws            SQLException
     */
    public static BusEntityTerrDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        BusEntityTerrDataVector v = new BusEntityTerrDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT BUS_ENTITY_TERR_ID,BUS_ENTITY_ID,POSTAL_CODE_ID,BUS_ENTITY_TERR_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,POSTAL_CODE,BUS_ENTITY_TERR_FREIGHT_CD FROM CLW_BUS_ENTITY_TERR WHERE BUS_ENTITY_TERR_ID IN (");

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
            BusEntityTerrData x=null;
            while (rs.next()) {
                // build the object
                x=BusEntityTerrData.createValue();
                
                x.setBusEntityTerrId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setPostalCodeId(rs.getInt(3));
                x.setBusEntityTerrCd(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setPostalCode(rs.getString(9));
                x.setBusEntityTerrFreightCd(rs.getString(10));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a BusEntityTerrDataVector object of all
     * BusEntityTerrData objects in the database.
     * @param pCon An open database connection.
     * @return new BusEntityTerrDataVector()
     * @throws            SQLException
     */
    public static BusEntityTerrDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT BUS_ENTITY_TERR_ID,BUS_ENTITY_ID,POSTAL_CODE_ID,BUS_ENTITY_TERR_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,POSTAL_CODE,BUS_ENTITY_TERR_FREIGHT_CD FROM CLW_BUS_ENTITY_TERR";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        BusEntityTerrDataVector v = new BusEntityTerrDataVector();
        BusEntityTerrData x = null;
        while (rs.next()) {
            // build the object
            x = BusEntityTerrData.createValue();
            
            x.setBusEntityTerrId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setPostalCodeId(rs.getInt(3));
            x.setBusEntityTerrCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setPostalCode(rs.getString(9));
            x.setBusEntityTerrFreightCd(rs.getString(10));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * BusEntityTerrData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT BUS_ENTITY_TERR_ID FROM CLW_BUS_ENTITY_TERR");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BUS_ENTITY_TERR");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BUS_ENTITY_TERR");
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
     * Inserts a BusEntityTerrData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityTerrData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new BusEntityTerrData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BusEntityTerrData insert(Connection pCon, BusEntityTerrData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_BUS_ENTITY_TERR_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_BUS_ENTITY_TERR_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setBusEntityTerrId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_BUS_ENTITY_TERR (BUS_ENTITY_TERR_ID,BUS_ENTITY_ID,POSTAL_CODE_ID,BUS_ENTITY_TERR_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,POSTAL_CODE,BUS_ENTITY_TERR_FREIGHT_CD) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getBusEntityTerrId());
        pstmt.setInt(2,pData.getBusEntityId());
        pstmt.setInt(3,pData.getPostalCodeId());
        pstmt.setString(4,pData.getBusEntityTerrCd());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());
        pstmt.setString(9,pData.getPostalCode());
        pstmt.setString(10,pData.getBusEntityTerrFreightCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_TERR_ID="+pData.getBusEntityTerrId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   POSTAL_CODE_ID="+pData.getPostalCodeId());
            log.debug("SQL:   BUS_ENTITY_TERR_CD="+pData.getBusEntityTerrCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   POSTAL_CODE="+pData.getPostalCode());
            log.debug("SQL:   BUS_ENTITY_TERR_FREIGHT_CD="+pData.getBusEntityTerrFreightCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setBusEntityTerrId(0);
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
     * Updates a BusEntityTerrData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityTerrData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BusEntityTerrData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_BUS_ENTITY_TERR SET BUS_ENTITY_ID = ?,POSTAL_CODE_ID = ?,BUS_ENTITY_TERR_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,POSTAL_CODE = ?,BUS_ENTITY_TERR_FREIGHT_CD = ? WHERE BUS_ENTITY_TERR_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setInt(i++,pData.getPostalCodeId());
        pstmt.setString(i++,pData.getBusEntityTerrCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getPostalCode());
        pstmt.setString(i++,pData.getBusEntityTerrFreightCd());
        pstmt.setInt(i++,pData.getBusEntityTerrId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   POSTAL_CODE_ID="+pData.getPostalCodeId());
            log.debug("SQL:   BUS_ENTITY_TERR_CD="+pData.getBusEntityTerrCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   POSTAL_CODE="+pData.getPostalCode());
            log.debug("SQL:   BUS_ENTITY_TERR_FREIGHT_CD="+pData.getBusEntityTerrFreightCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a BusEntityTerrData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBusEntityTerrId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBusEntityTerrId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_BUS_ENTITY_TERR WHERE BUS_ENTITY_TERR_ID = " + pBusEntityTerrId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes BusEntityTerrData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_BUS_ENTITY_TERR");
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
     * Inserts a BusEntityTerrData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityTerrData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, BusEntityTerrData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_BUS_ENTITY_TERR (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "BUS_ENTITY_TERR_ID,BUS_ENTITY_ID,POSTAL_CODE_ID,BUS_ENTITY_TERR_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,POSTAL_CODE,BUS_ENTITY_TERR_FREIGHT_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getBusEntityTerrId());
        pstmt.setInt(2+4,pData.getBusEntityId());
        pstmt.setInt(3+4,pData.getPostalCodeId());
        pstmt.setString(4+4,pData.getBusEntityTerrCd());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());
        pstmt.setString(9+4,pData.getPostalCode());
        pstmt.setString(10+4,pData.getBusEntityTerrFreightCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a BusEntityTerrData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityTerrData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new BusEntityTerrData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BusEntityTerrData insert(Connection pCon, BusEntityTerrData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a BusEntityTerrData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BusEntityTerrData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BusEntityTerrData pData, boolean pLogFl)
        throws SQLException {
        BusEntityTerrData oldData = null;
        if(pLogFl) {
          int id = pData.getBusEntityTerrId();
          try {
          oldData = BusEntityTerrDataAccess.select(pCon,id);
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
     * Deletes a BusEntityTerrData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBusEntityTerrId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBusEntityTerrId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_BUS_ENTITY_TERR SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BUS_ENTITY_TERR d WHERE BUS_ENTITY_TERR_ID = " + pBusEntityTerrId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pBusEntityTerrId);
        return n;
     }

    /**
     * Deletes BusEntityTerrData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_BUS_ENTITY_TERR SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BUS_ENTITY_TERR d ");
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

