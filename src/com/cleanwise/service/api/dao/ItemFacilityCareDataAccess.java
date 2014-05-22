
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ItemFacilityCareDataAccess
 * Description:  This class is used to build access methods to the CLW_ITEM_FACILITY_CARE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ItemFacilityCareData;
import com.cleanwise.service.api.value.ItemFacilityCareDataVector;
import com.cleanwise.service.api.framework.DataAccess;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>ItemFacilityCareDataAccess</code>
 */
public class ItemFacilityCareDataAccess implements DataAccess
{
    private static Category log = Category.getInstance(ItemFacilityCareDataAccess.class.getName());

    /** <code>CLW_ITEM_FACILITY_CARE</code> table name */
    public static final String CLW_ITEM_FACILITY_CARE = "CLW_ITEM_FACILITY_CARE";
    
    /** <code>ITEM_FACILITY_CARE_ID</code> ITEM_FACILITY_CARE_ID column of table CLW_ITEM_FACILITY_CARE */
    public static final String ITEM_FACILITY_CARE_ID = "ITEM_FACILITY_CARE_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_ITEM_FACILITY_CARE */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ITEM_FACILITY_CARE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ITEM_FACILITY_CARE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>FACILITY_CARE_CD</code> FACILITY_CARE_CD column of table CLW_ITEM_FACILITY_CARE */
    public static final String FACILITY_CARE_CD = "FACILITY_CARE_CD";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ITEM_FACILITY_CARE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ITEM_FACILITY_CARE */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public ItemFacilityCareDataAccess()
    {
    }

    /**
     * Gets a ItemFacilityCareData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pItemFacilityCareId The key requested.
     * @return new ItemFacilityCareData()
     * @throws            SQLException
     */
    public static ItemFacilityCareData select(Connection pCon, int pItemFacilityCareId)
        throws SQLException, DataNotFoundException {
        ItemFacilityCareData x=null;
        String sql="SELECT ITEM_FACILITY_CARE_ID,ITEM_ID,ADD_BY,ADD_DATE,FACILITY_CARE_CD,MOD_BY,MOD_DATE FROM CLW_ITEM_FACILITY_CARE WHERE ITEM_FACILITY_CARE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pItemFacilityCareId=" + pItemFacilityCareId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pItemFacilityCareId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ItemFacilityCareData.createValue();
            
            x.setItemFacilityCareId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setAddBy(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setFacilityCareCd(rs.getString(5));
            x.setModBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ITEM_FACILITY_CARE_ID :" + pItemFacilityCareId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ItemFacilityCareDataVector object that consists
     * of ItemFacilityCareData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ItemFacilityCareDataVector()
     * @throws            SQLException
     */
    public static ItemFacilityCareDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ItemFacilityCareData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ITEM_FACILITY_CARE.ITEM_FACILITY_CARE_ID,CLW_ITEM_FACILITY_CARE.ITEM_ID,CLW_ITEM_FACILITY_CARE.ADD_BY,CLW_ITEM_FACILITY_CARE.ADD_DATE,CLW_ITEM_FACILITY_CARE.FACILITY_CARE_CD,CLW_ITEM_FACILITY_CARE.MOD_BY,CLW_ITEM_FACILITY_CARE.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ItemFacilityCareData Object.
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
    *@returns a populated ItemFacilityCareData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ItemFacilityCareData x = ItemFacilityCareData.createValue();
         
         x.setItemFacilityCareId(rs.getInt(1+offset));
         x.setItemId(rs.getInt(2+offset));
         x.setAddBy(rs.getString(3+offset));
         x.setAddDate(rs.getTimestamp(4+offset));
         x.setFacilityCareCd(rs.getString(5+offset));
         x.setModBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ItemFacilityCareData Object represents.
    */
    public int getColumnCount(){
        return 7;
    }

    /**
     * Gets a ItemFacilityCareDataVector object that consists
     * of ItemFacilityCareData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ItemFacilityCareDataVector()
     * @throws            SQLException
     */
    public static ItemFacilityCareDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ITEM_FACILITY_CARE_ID,ITEM_ID,ADD_BY,ADD_DATE,FACILITY_CARE_CD,MOD_BY,MOD_DATE FROM CLW_ITEM_FACILITY_CARE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ITEM_FACILITY_CARE.ITEM_FACILITY_CARE_ID,CLW_ITEM_FACILITY_CARE.ITEM_ID,CLW_ITEM_FACILITY_CARE.ADD_BY,CLW_ITEM_FACILITY_CARE.ADD_DATE,CLW_ITEM_FACILITY_CARE.FACILITY_CARE_CD,CLW_ITEM_FACILITY_CARE.MOD_BY,CLW_ITEM_FACILITY_CARE.MOD_DATE FROM CLW_ITEM_FACILITY_CARE");
                where = pCriteria.getSqlClause("CLW_ITEM_FACILITY_CARE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
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
        ItemFacilityCareDataVector v = new ItemFacilityCareDataVector();
        while (rs.next()) {
            ItemFacilityCareData x = ItemFacilityCareData.createValue();
            
            x.setItemFacilityCareId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setAddBy(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setFacilityCareCd(rs.getString(5));
            x.setModBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ItemFacilityCareDataVector object that consists
     * of ItemFacilityCareData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ItemFacilityCareData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ItemFacilityCareDataVector()
     * @throws            SQLException
     */
    public static ItemFacilityCareDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ItemFacilityCareDataVector v = new ItemFacilityCareDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_FACILITY_CARE_ID,ITEM_ID,ADD_BY,ADD_DATE,FACILITY_CARE_CD,MOD_BY,MOD_DATE FROM CLW_ITEM_FACILITY_CARE WHERE ITEM_FACILITY_CARE_ID IN (");

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
            ItemFacilityCareData x=null;
            while (rs.next()) {
                // build the object
                x=ItemFacilityCareData.createValue();
                
                x.setItemFacilityCareId(rs.getInt(1));
                x.setItemId(rs.getInt(2));
                x.setAddBy(rs.getString(3));
                x.setAddDate(rs.getTimestamp(4));
                x.setFacilityCareCd(rs.getString(5));
                x.setModBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ItemFacilityCareDataVector object of all
     * ItemFacilityCareData objects in the database.
     * @param pCon An open database connection.
     * @return new ItemFacilityCareDataVector()
     * @throws            SQLException
     */
    public static ItemFacilityCareDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ITEM_FACILITY_CARE_ID,ITEM_ID,ADD_BY,ADD_DATE,FACILITY_CARE_CD,MOD_BY,MOD_DATE FROM CLW_ITEM_FACILITY_CARE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ItemFacilityCareDataVector v = new ItemFacilityCareDataVector();
        ItemFacilityCareData x = null;
        while (rs.next()) {
            // build the object
            x = ItemFacilityCareData.createValue();
            
            x.setItemFacilityCareId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setAddBy(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setFacilityCareCd(rs.getString(5));
            x.setModBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ItemFacilityCareData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_FACILITY_CARE_ID FROM CLW_ITEM_FACILITY_CARE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_FACILITY_CARE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_FACILITY_CARE");
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
     * Inserts a ItemFacilityCareData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemFacilityCareData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ItemFacilityCareData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemFacilityCareData insert(Connection pCon, ItemFacilityCareData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ITEM_FACILITY_CARE_SEQ.NEXTVAL FROM DUAL");
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ITEM_FACILITY_CARE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setItemFacilityCareId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ITEM_FACILITY_CARE (ITEM_FACILITY_CARE_ID,ITEM_ID,ADD_BY,ADD_DATE,FACILITY_CARE_CD,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getItemFacilityCareId());
        pstmt.setInt(2,pData.getItemId());
        pstmt.setString(3,pData.getAddBy());
        pstmt.setTimestamp(4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5,pData.getFacilityCareCd());
        pstmt.setString(6,pData.getModBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_FACILITY_CARE_ID="+pData.getItemFacilityCareId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   FACILITY_CARE_CD="+pData.getFacilityCareCd());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }

        pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);
        return pData;
    }

    /**
     * Updates a ItemFacilityCareData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemFacilityCareData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemFacilityCareData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ITEM_FACILITY_CARE SET ITEM_ID = ?,ADD_BY = ?,ADD_DATE = ?,FACILITY_CARE_CD = ?,MOD_BY = ?,MOD_DATE = ? WHERE ITEM_FACILITY_CARE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getFacilityCareCd());
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getItemFacilityCareId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   FACILITY_CARE_CD="+pData.getFacilityCareCd());
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
     * Deletes a ItemFacilityCareData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemFacilityCareId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemFacilityCareId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ITEM_FACILITY_CARE WHERE ITEM_FACILITY_CARE_ID = " + pItemFacilityCareId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ItemFacilityCareData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ITEM_FACILITY_CARE");
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
}

