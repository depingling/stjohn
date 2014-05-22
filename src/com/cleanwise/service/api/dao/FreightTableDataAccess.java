
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        FreightTableDataAccess
 * Description:  This class is used to build access methods to the CLW_FREIGHT_TABLE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.FreightTableData;
import com.cleanwise.service.api.value.FreightTableDataVector;
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
 * <code>FreightTableDataAccess</code>
 */
public class FreightTableDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(FreightTableDataAccess.class.getName());

    /** <code>CLW_FREIGHT_TABLE</code> table name */
	/* Primary key: FREIGHT_TABLE_ID */
	
    public static final String CLW_FREIGHT_TABLE = "CLW_FREIGHT_TABLE";
    
    /** <code>FREIGHT_TABLE_ID</code> FREIGHT_TABLE_ID column of table CLW_FREIGHT_TABLE */
    public static final String FREIGHT_TABLE_ID = "FREIGHT_TABLE_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_FREIGHT_TABLE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>FREIGHT_TABLE_STATUS_CD</code> FREIGHT_TABLE_STATUS_CD column of table CLW_FREIGHT_TABLE */
    public static final String FREIGHT_TABLE_STATUS_CD = "FREIGHT_TABLE_STATUS_CD";
    /** <code>FREIGHT_TABLE_TYPE_CD</code> FREIGHT_TABLE_TYPE_CD column of table CLW_FREIGHT_TABLE */
    public static final String FREIGHT_TABLE_TYPE_CD = "FREIGHT_TABLE_TYPE_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_FREIGHT_TABLE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_FREIGHT_TABLE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_FREIGHT_TABLE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_FREIGHT_TABLE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>STORE_ID</code> STORE_ID column of table CLW_FREIGHT_TABLE */
    public static final String STORE_ID = "STORE_ID";
    /** <code>DISTRIBUTOR_ID</code> DISTRIBUTOR_ID column of table CLW_FREIGHT_TABLE */
    public static final String DISTRIBUTOR_ID = "DISTRIBUTOR_ID";
    /** <code>FREIGHT_TABLE_CHARGE_CD</code> FREIGHT_TABLE_CHARGE_CD column of table CLW_FREIGHT_TABLE */
    public static final String FREIGHT_TABLE_CHARGE_CD = "FREIGHT_TABLE_CHARGE_CD";

    /**
     * Constructor.
     */
    public FreightTableDataAccess()
    {
    }

    /**
     * Gets a FreightTableData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pFreightTableId The key requested.
     * @return new FreightTableData()
     * @throws            SQLException
     */
    public static FreightTableData select(Connection pCon, int pFreightTableId)
        throws SQLException, DataNotFoundException {
        FreightTableData x=null;
        String sql="SELECT FREIGHT_TABLE_ID,SHORT_DESC,FREIGHT_TABLE_STATUS_CD,FREIGHT_TABLE_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,STORE_ID,DISTRIBUTOR_ID,FREIGHT_TABLE_CHARGE_CD FROM CLW_FREIGHT_TABLE WHERE FREIGHT_TABLE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pFreightTableId=" + pFreightTableId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pFreightTableId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=FreightTableData.createValue();
            
            x.setFreightTableId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setFreightTableStatusCd(rs.getString(3));
            x.setFreightTableTypeCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setStoreId(rs.getInt(9));
            x.setDistributorId(rs.getInt(10));
            x.setFreightTableChargeCd(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("FREIGHT_TABLE_ID :" + pFreightTableId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a FreightTableDataVector object that consists
     * of FreightTableData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new FreightTableDataVector()
     * @throws            SQLException
     */
    public static FreightTableDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a FreightTableData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_FREIGHT_TABLE.FREIGHT_TABLE_ID,CLW_FREIGHT_TABLE.SHORT_DESC,CLW_FREIGHT_TABLE.FREIGHT_TABLE_STATUS_CD,CLW_FREIGHT_TABLE.FREIGHT_TABLE_TYPE_CD,CLW_FREIGHT_TABLE.ADD_DATE,CLW_FREIGHT_TABLE.ADD_BY,CLW_FREIGHT_TABLE.MOD_DATE,CLW_FREIGHT_TABLE.MOD_BY,CLW_FREIGHT_TABLE.STORE_ID,CLW_FREIGHT_TABLE.DISTRIBUTOR_ID,CLW_FREIGHT_TABLE.FREIGHT_TABLE_CHARGE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated FreightTableData Object.
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
    *@returns a populated FreightTableData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         FreightTableData x = FreightTableData.createValue();
         
         x.setFreightTableId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setFreightTableStatusCd(rs.getString(3+offset));
         x.setFreightTableTypeCd(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setStoreId(rs.getInt(9+offset));
         x.setDistributorId(rs.getInt(10+offset));
         x.setFreightTableChargeCd(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the FreightTableData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a FreightTableDataVector object that consists
     * of FreightTableData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new FreightTableDataVector()
     * @throws            SQLException
     */
    public static FreightTableDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT FREIGHT_TABLE_ID,SHORT_DESC,FREIGHT_TABLE_STATUS_CD,FREIGHT_TABLE_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,STORE_ID,DISTRIBUTOR_ID,FREIGHT_TABLE_CHARGE_CD FROM CLW_FREIGHT_TABLE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_FREIGHT_TABLE.FREIGHT_TABLE_ID,CLW_FREIGHT_TABLE.SHORT_DESC,CLW_FREIGHT_TABLE.FREIGHT_TABLE_STATUS_CD,CLW_FREIGHT_TABLE.FREIGHT_TABLE_TYPE_CD,CLW_FREIGHT_TABLE.ADD_DATE,CLW_FREIGHT_TABLE.ADD_BY,CLW_FREIGHT_TABLE.MOD_DATE,CLW_FREIGHT_TABLE.MOD_BY,CLW_FREIGHT_TABLE.STORE_ID,CLW_FREIGHT_TABLE.DISTRIBUTOR_ID,CLW_FREIGHT_TABLE.FREIGHT_TABLE_CHARGE_CD FROM CLW_FREIGHT_TABLE");
                where = pCriteria.getSqlClause("CLW_FREIGHT_TABLE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_FREIGHT_TABLE.equals(otherTable)){
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
        FreightTableDataVector v = new FreightTableDataVector();
        while (rs.next()) {
            FreightTableData x = FreightTableData.createValue();
            
            x.setFreightTableId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setFreightTableStatusCd(rs.getString(3));
            x.setFreightTableTypeCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setStoreId(rs.getInt(9));
            x.setDistributorId(rs.getInt(10));
            x.setFreightTableChargeCd(rs.getString(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a FreightTableDataVector object that consists
     * of FreightTableData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for FreightTableData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new FreightTableDataVector()
     * @throws            SQLException
     */
    public static FreightTableDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        FreightTableDataVector v = new FreightTableDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT FREIGHT_TABLE_ID,SHORT_DESC,FREIGHT_TABLE_STATUS_CD,FREIGHT_TABLE_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,STORE_ID,DISTRIBUTOR_ID,FREIGHT_TABLE_CHARGE_CD FROM CLW_FREIGHT_TABLE WHERE FREIGHT_TABLE_ID IN (");

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
            FreightTableData x=null;
            while (rs.next()) {
                // build the object
                x=FreightTableData.createValue();
                
                x.setFreightTableId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setFreightTableStatusCd(rs.getString(3));
                x.setFreightTableTypeCd(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setStoreId(rs.getInt(9));
                x.setDistributorId(rs.getInt(10));
                x.setFreightTableChargeCd(rs.getString(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a FreightTableDataVector object of all
     * FreightTableData objects in the database.
     * @param pCon An open database connection.
     * @return new FreightTableDataVector()
     * @throws            SQLException
     */
    public static FreightTableDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT FREIGHT_TABLE_ID,SHORT_DESC,FREIGHT_TABLE_STATUS_CD,FREIGHT_TABLE_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,STORE_ID,DISTRIBUTOR_ID,FREIGHT_TABLE_CHARGE_CD FROM CLW_FREIGHT_TABLE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        FreightTableDataVector v = new FreightTableDataVector();
        FreightTableData x = null;
        while (rs.next()) {
            // build the object
            x = FreightTableData.createValue();
            
            x.setFreightTableId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setFreightTableStatusCd(rs.getString(3));
            x.setFreightTableTypeCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setStoreId(rs.getInt(9));
            x.setDistributorId(rs.getInt(10));
            x.setFreightTableChargeCd(rs.getString(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * FreightTableData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT FREIGHT_TABLE_ID FROM CLW_FREIGHT_TABLE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FREIGHT_TABLE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FREIGHT_TABLE");
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
     * Inserts a FreightTableData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FreightTableData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new FreightTableData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FreightTableData insert(Connection pCon, FreightTableData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_FREIGHT_TABLE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_FREIGHT_TABLE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setFreightTableId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_FREIGHT_TABLE (FREIGHT_TABLE_ID,SHORT_DESC,FREIGHT_TABLE_STATUS_CD,FREIGHT_TABLE_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,STORE_ID,DISTRIBUTOR_ID,FREIGHT_TABLE_CHARGE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getFreightTableId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getFreightTableStatusCd());
        pstmt.setString(4,pData.getFreightTableTypeCd());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());
        if (pData.getStoreId() == 0) {
            pstmt.setNull(9, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(9,pData.getStoreId());
        }

        if (pData.getDistributorId() == 0) {
            pstmt.setNull(10, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(10,pData.getDistributorId());
        }

        pstmt.setString(11,pData.getFreightTableChargeCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FREIGHT_TABLE_ID="+pData.getFreightTableId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   FREIGHT_TABLE_STATUS_CD="+pData.getFreightTableStatusCd());
            log.debug("SQL:   FREIGHT_TABLE_TYPE_CD="+pData.getFreightTableTypeCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   DISTRIBUTOR_ID="+pData.getDistributorId());
            log.debug("SQL:   FREIGHT_TABLE_CHARGE_CD="+pData.getFreightTableChargeCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setFreightTableId(0);
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
     * Updates a FreightTableData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FreightTableData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FreightTableData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_FREIGHT_TABLE SET SHORT_DESC = ?,FREIGHT_TABLE_STATUS_CD = ?,FREIGHT_TABLE_TYPE_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,STORE_ID = ?,DISTRIBUTOR_ID = ?,FREIGHT_TABLE_CHARGE_CD = ? WHERE FREIGHT_TABLE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getFreightTableStatusCd());
        pstmt.setString(i++,pData.getFreightTableTypeCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        if (pData.getStoreId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getStoreId());
        }

        if (pData.getDistributorId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getDistributorId());
        }

        pstmt.setString(i++,pData.getFreightTableChargeCd());
        pstmt.setInt(i++,pData.getFreightTableId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   FREIGHT_TABLE_STATUS_CD="+pData.getFreightTableStatusCd());
            log.debug("SQL:   FREIGHT_TABLE_TYPE_CD="+pData.getFreightTableTypeCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   DISTRIBUTOR_ID="+pData.getDistributorId());
            log.debug("SQL:   FREIGHT_TABLE_CHARGE_CD="+pData.getFreightTableChargeCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a FreightTableData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFreightTableId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFreightTableId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_FREIGHT_TABLE WHERE FREIGHT_TABLE_ID = " + pFreightTableId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes FreightTableData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_FREIGHT_TABLE");
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
     * Inserts a FreightTableData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FreightTableData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, FreightTableData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_FREIGHT_TABLE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "FREIGHT_TABLE_ID,SHORT_DESC,FREIGHT_TABLE_STATUS_CD,FREIGHT_TABLE_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,STORE_ID,DISTRIBUTOR_ID,FREIGHT_TABLE_CHARGE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getFreightTableId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getFreightTableStatusCd());
        pstmt.setString(4+4,pData.getFreightTableTypeCd());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());
        if (pData.getStoreId() == 0) {
            pstmt.setNull(9+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(9+4,pData.getStoreId());
        }

        if (pData.getDistributorId() == 0) {
            pstmt.setNull(10+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(10+4,pData.getDistributorId());
        }

        pstmt.setString(11+4,pData.getFreightTableChargeCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a FreightTableData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FreightTableData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new FreightTableData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FreightTableData insert(Connection pCon, FreightTableData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a FreightTableData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FreightTableData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FreightTableData pData, boolean pLogFl)
        throws SQLException {
        FreightTableData oldData = null;
        if(pLogFl) {
          int id = pData.getFreightTableId();
          try {
          oldData = FreightTableDataAccess.select(pCon,id);
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
     * Deletes a FreightTableData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFreightTableId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFreightTableId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_FREIGHT_TABLE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FREIGHT_TABLE d WHERE FREIGHT_TABLE_ID = " + pFreightTableId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pFreightTableId);
        return n;
     }

    /**
     * Deletes FreightTableData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_FREIGHT_TABLE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FREIGHT_TABLE d ");
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

