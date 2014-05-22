
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ItemAssocDataAccess
 * Description:  This class is used to build access methods to the CLW_ITEM_ASSOC table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ItemAssocData;
import com.cleanwise.service.api.value.ItemAssocDataVector;
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
 * <code>ItemAssocDataAccess</code>
 */
public class ItemAssocDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ItemAssocDataAccess.class.getName());

    /** <code>CLW_ITEM_ASSOC</code> table name */
	/* Primary key: ITEM_ASSOC_ID */
	
    public static final String CLW_ITEM_ASSOC = "CLW_ITEM_ASSOC";
    
    /** <code>ITEM_ASSOC_ID</code> ITEM_ASSOC_ID column of table CLW_ITEM_ASSOC */
    public static final String ITEM_ASSOC_ID = "ITEM_ASSOC_ID";
    /** <code>ITEM1_ID</code> ITEM1_ID column of table CLW_ITEM_ASSOC */
    public static final String ITEM1_ID = "ITEM1_ID";
    /** <code>ITEM2_ID</code> ITEM2_ID column of table CLW_ITEM_ASSOC */
    public static final String ITEM2_ID = "ITEM2_ID";
    /** <code>CATALOG_ID</code> CATALOG_ID column of table CLW_ITEM_ASSOC */
    public static final String CATALOG_ID = "CATALOG_ID";
    /** <code>ITEM_ASSOC_CD</code> ITEM_ASSOC_CD column of table CLW_ITEM_ASSOC */
    public static final String ITEM_ASSOC_CD = "ITEM_ASSOC_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ITEM_ASSOC */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ITEM_ASSOC */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ITEM_ASSOC */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ITEM_ASSOC */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ItemAssocDataAccess()
    {
    }

    /**
     * Gets a ItemAssocData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pItemAssocId The key requested.
     * @return new ItemAssocData()
     * @throws            SQLException
     */
    public static ItemAssocData select(Connection pCon, int pItemAssocId)
        throws SQLException, DataNotFoundException {
        ItemAssocData x=null;
        String sql="SELECT ITEM_ASSOC_ID,ITEM1_ID,ITEM2_ID,CATALOG_ID,ITEM_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_ASSOC WHERE ITEM_ASSOC_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pItemAssocId=" + pItemAssocId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pItemAssocId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ItemAssocData.createValue();
            
            x.setItemAssocId(rs.getInt(1));
            x.setItem1Id(rs.getInt(2));
            x.setItem2Id(rs.getInt(3));
            x.setCatalogId(rs.getInt(4));
            x.setItemAssocCd(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ITEM_ASSOC_ID :" + pItemAssocId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ItemAssocDataVector object that consists
     * of ItemAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ItemAssocDataVector()
     * @throws            SQLException
     */
    public static ItemAssocDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ItemAssocData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ITEM_ASSOC.ITEM_ASSOC_ID,CLW_ITEM_ASSOC.ITEM1_ID,CLW_ITEM_ASSOC.ITEM2_ID,CLW_ITEM_ASSOC.CATALOG_ID,CLW_ITEM_ASSOC.ITEM_ASSOC_CD,CLW_ITEM_ASSOC.ADD_DATE,CLW_ITEM_ASSOC.ADD_BY,CLW_ITEM_ASSOC.MOD_DATE,CLW_ITEM_ASSOC.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ItemAssocData Object.
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
    *@returns a populated ItemAssocData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ItemAssocData x = ItemAssocData.createValue();
         
         x.setItemAssocId(rs.getInt(1+offset));
         x.setItem1Id(rs.getInt(2+offset));
         x.setItem2Id(rs.getInt(3+offset));
         x.setCatalogId(rs.getInt(4+offset));
         x.setItemAssocCd(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ItemAssocData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a ItemAssocDataVector object that consists
     * of ItemAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ItemAssocDataVector()
     * @throws            SQLException
     */
    public static ItemAssocDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ITEM_ASSOC_ID,ITEM1_ID,ITEM2_ID,CATALOG_ID,ITEM_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_ASSOC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ITEM_ASSOC.ITEM_ASSOC_ID,CLW_ITEM_ASSOC.ITEM1_ID,CLW_ITEM_ASSOC.ITEM2_ID,CLW_ITEM_ASSOC.CATALOG_ID,CLW_ITEM_ASSOC.ITEM_ASSOC_CD,CLW_ITEM_ASSOC.ADD_DATE,CLW_ITEM_ASSOC.ADD_BY,CLW_ITEM_ASSOC.MOD_DATE,CLW_ITEM_ASSOC.MOD_BY FROM CLW_ITEM_ASSOC");
                where = pCriteria.getSqlClause("CLW_ITEM_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ITEM_ASSOC.equals(otherTable)){
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
        ItemAssocDataVector v = new ItemAssocDataVector();
        while (rs.next()) {
            ItemAssocData x = ItemAssocData.createValue();
            
            x.setItemAssocId(rs.getInt(1));
            x.setItem1Id(rs.getInt(2));
            x.setItem2Id(rs.getInt(3));
            x.setCatalogId(rs.getInt(4));
            x.setItemAssocCd(rs.getString(5));
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
     * Gets a ItemAssocDataVector object that consists
     * of ItemAssocData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ItemAssocData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ItemAssocDataVector()
     * @throws            SQLException
     */
    public static ItemAssocDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ItemAssocDataVector v = new ItemAssocDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_ASSOC_ID,ITEM1_ID,ITEM2_ID,CATALOG_ID,ITEM_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_ASSOC WHERE ITEM_ASSOC_ID IN (");

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
            ItemAssocData x=null;
            while (rs.next()) {
                // build the object
                x=ItemAssocData.createValue();
                
                x.setItemAssocId(rs.getInt(1));
                x.setItem1Id(rs.getInt(2));
                x.setItem2Id(rs.getInt(3));
                x.setCatalogId(rs.getInt(4));
                x.setItemAssocCd(rs.getString(5));
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
     * Gets a ItemAssocDataVector object of all
     * ItemAssocData objects in the database.
     * @param pCon An open database connection.
     * @return new ItemAssocDataVector()
     * @throws            SQLException
     */
    public static ItemAssocDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ITEM_ASSOC_ID,ITEM1_ID,ITEM2_ID,CATALOG_ID,ITEM_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_ASSOC";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ItemAssocDataVector v = new ItemAssocDataVector();
        ItemAssocData x = null;
        while (rs.next()) {
            // build the object
            x = ItemAssocData.createValue();
            
            x.setItemAssocId(rs.getInt(1));
            x.setItem1Id(rs.getInt(2));
            x.setItem2Id(rs.getInt(3));
            x.setCatalogId(rs.getInt(4));
            x.setItemAssocCd(rs.getString(5));
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
     * ItemAssocData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_ASSOC_ID FROM CLW_ITEM_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_ASSOC");
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
     * Inserts a ItemAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ItemAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemAssocData insert(Connection pCon, ItemAssocData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ITEM_ASSOC_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ITEM_ASSOC_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setItemAssocId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ITEM_ASSOC (ITEM_ASSOC_ID,ITEM1_ID,ITEM2_ID,CATALOG_ID,ITEM_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getItemAssocId());
        pstmt.setInt(2,pData.getItem1Id());
        pstmt.setInt(3,pData.getItem2Id());
        if (pData.getCatalogId() == 0) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4,pData.getCatalogId());
        }

        pstmt.setString(5,pData.getItemAssocCd());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_ASSOC_ID="+pData.getItemAssocId());
            log.debug("SQL:   ITEM1_ID="+pData.getItem1Id());
            log.debug("SQL:   ITEM2_ID="+pData.getItem2Id());
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   ITEM_ASSOC_CD="+pData.getItemAssocCd());
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
        pData.setItemAssocId(0);
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
     * Updates a ItemAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemAssocData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ITEM_ASSOC SET ITEM1_ID = ?,ITEM2_ID = ?,CATALOG_ID = ?,ITEM_ASSOC_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ITEM_ASSOC_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getItem1Id());
        pstmt.setInt(i++,pData.getItem2Id());
        if (pData.getCatalogId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getCatalogId());
        }

        pstmt.setString(i++,pData.getItemAssocCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getItemAssocId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM1_ID="+pData.getItem1Id());
            log.debug("SQL:   ITEM2_ID="+pData.getItem2Id());
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   ITEM_ASSOC_CD="+pData.getItemAssocCd());
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
     * Deletes a ItemAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemAssocId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemAssocId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ITEM_ASSOC WHERE ITEM_ASSOC_ID = " + pItemAssocId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ItemAssocData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ITEM_ASSOC");
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
     * Inserts a ItemAssocData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemAssocData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ItemAssocData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ITEM_ASSOC (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ITEM_ASSOC_ID,ITEM1_ID,ITEM2_ID,CATALOG_ID,ITEM_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getItemAssocId());
        pstmt.setInt(2+4,pData.getItem1Id());
        pstmt.setInt(3+4,pData.getItem2Id());
        if (pData.getCatalogId() == 0) {
            pstmt.setNull(4+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4+4,pData.getCatalogId());
        }

        pstmt.setString(5+4,pData.getItemAssocCd());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ItemAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ItemAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemAssocData insert(Connection pCon, ItemAssocData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ItemAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemAssocData pData, boolean pLogFl)
        throws SQLException {
        ItemAssocData oldData = null;
        if(pLogFl) {
          int id = pData.getItemAssocId();
          try {
          oldData = ItemAssocDataAccess.select(pCon,id);
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
     * Deletes a ItemAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemAssocId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemAssocId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ITEM_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM_ASSOC d WHERE ITEM_ASSOC_ID = " + pItemAssocId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pItemAssocId);
        return n;
     }

    /**
     * Deletes ItemAssocData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ITEM_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM_ASSOC d ");
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

