
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CatalogAssocDataAccess
 * Description:  This class is used to build access methods to the CLW_CATALOG_ASSOC table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CatalogAssocData;
import com.cleanwise.service.api.value.CatalogAssocDataVector;
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
 * <code>CatalogAssocDataAccess</code>
 */
public class CatalogAssocDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CatalogAssocDataAccess.class.getName());

    /** <code>CLW_CATALOG_ASSOC</code> table name */
	/* Primary key: CATALOG_ASSOC_ID */
	
    public static final String CLW_CATALOG_ASSOC = "CLW_CATALOG_ASSOC";
    
    /** <code>CATALOG_ASSOC_ID</code> CATALOG_ASSOC_ID column of table CLW_CATALOG_ASSOC */
    public static final String CATALOG_ASSOC_ID = "CATALOG_ASSOC_ID";
    /** <code>CATALOG_ID</code> CATALOG_ID column of table CLW_CATALOG_ASSOC */
    public static final String CATALOG_ID = "CATALOG_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_CATALOG_ASSOC */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>CATALOG_ASSOC_CD</code> CATALOG_ASSOC_CD column of table CLW_CATALOG_ASSOC */
    public static final String CATALOG_ASSOC_CD = "CATALOG_ASSOC_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CATALOG_ASSOC */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CATALOG_ASSOC */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CATALOG_ASSOC */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CATALOG_ASSOC */
    public static final String MOD_BY = "MOD_BY";
    /** <code>NEW_CATALOG_ID</code> NEW_CATALOG_ID column of table CLW_CATALOG_ASSOC */
    public static final String NEW_CATALOG_ID = "NEW_CATALOG_ID";

    /**
     * Constructor.
     */
    public CatalogAssocDataAccess()
    {
    }

    /**
     * Gets a CatalogAssocData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCatalogAssocId The key requested.
     * @return new CatalogAssocData()
     * @throws            SQLException
     */
    public static CatalogAssocData select(Connection pCon, int pCatalogAssocId)
        throws SQLException, DataNotFoundException {
        CatalogAssocData x=null;
        String sql="SELECT CATALOG_ASSOC_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,NEW_CATALOG_ID FROM CLW_CATALOG_ASSOC WHERE CATALOG_ASSOC_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCatalogAssocId=" + pCatalogAssocId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCatalogAssocId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CatalogAssocData.createValue();
            
            x.setCatalogAssocId(rs.getInt(1));
            x.setCatalogId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setCatalogAssocCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setNewCatalogId(rs.getInt(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CATALOG_ASSOC_ID :" + pCatalogAssocId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CatalogAssocDataVector object that consists
     * of CatalogAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CatalogAssocDataVector()
     * @throws            SQLException
     */
    public static CatalogAssocDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CatalogAssocData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CATALOG_ASSOC.CATALOG_ASSOC_ID,CLW_CATALOG_ASSOC.CATALOG_ID,CLW_CATALOG_ASSOC.BUS_ENTITY_ID,CLW_CATALOG_ASSOC.CATALOG_ASSOC_CD,CLW_CATALOG_ASSOC.ADD_DATE,CLW_CATALOG_ASSOC.ADD_BY,CLW_CATALOG_ASSOC.MOD_DATE,CLW_CATALOG_ASSOC.MOD_BY,CLW_CATALOG_ASSOC.NEW_CATALOG_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CatalogAssocData Object.
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
    *@returns a populated CatalogAssocData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CatalogAssocData x = CatalogAssocData.createValue();
         
         x.setCatalogAssocId(rs.getInt(1+offset));
         x.setCatalogId(rs.getInt(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setCatalogAssocCd(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setNewCatalogId(rs.getInt(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CatalogAssocData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a CatalogAssocDataVector object that consists
     * of CatalogAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CatalogAssocDataVector()
     * @throws            SQLException
     */
    public static CatalogAssocDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CATALOG_ASSOC_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,NEW_CATALOG_ID FROM CLW_CATALOG_ASSOC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CATALOG_ASSOC.CATALOG_ASSOC_ID,CLW_CATALOG_ASSOC.CATALOG_ID,CLW_CATALOG_ASSOC.BUS_ENTITY_ID,CLW_CATALOG_ASSOC.CATALOG_ASSOC_CD,CLW_CATALOG_ASSOC.ADD_DATE,CLW_CATALOG_ASSOC.ADD_BY,CLW_CATALOG_ASSOC.MOD_DATE,CLW_CATALOG_ASSOC.MOD_BY,CLW_CATALOG_ASSOC.NEW_CATALOG_ID FROM CLW_CATALOG_ASSOC");
                where = pCriteria.getSqlClause("CLW_CATALOG_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CATALOG_ASSOC.equals(otherTable)){
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
        CatalogAssocDataVector v = new CatalogAssocDataVector();
        while (rs.next()) {
            CatalogAssocData x = CatalogAssocData.createValue();
            
            x.setCatalogAssocId(rs.getInt(1));
            x.setCatalogId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setCatalogAssocCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setNewCatalogId(rs.getInt(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a CatalogAssocDataVector object that consists
     * of CatalogAssocData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CatalogAssocData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CatalogAssocDataVector()
     * @throws            SQLException
     */
    public static CatalogAssocDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CatalogAssocDataVector v = new CatalogAssocDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CATALOG_ASSOC_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,NEW_CATALOG_ID FROM CLW_CATALOG_ASSOC WHERE CATALOG_ASSOC_ID IN (");

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
            CatalogAssocData x=null;
            while (rs.next()) {
                // build the object
                x=CatalogAssocData.createValue();
                
                x.setCatalogAssocId(rs.getInt(1));
                x.setCatalogId(rs.getInt(2));
                x.setBusEntityId(rs.getInt(3));
                x.setCatalogAssocCd(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setNewCatalogId(rs.getInt(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a CatalogAssocDataVector object of all
     * CatalogAssocData objects in the database.
     * @param pCon An open database connection.
     * @return new CatalogAssocDataVector()
     * @throws            SQLException
     */
    public static CatalogAssocDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CATALOG_ASSOC_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,NEW_CATALOG_ID FROM CLW_CATALOG_ASSOC";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CatalogAssocDataVector v = new CatalogAssocDataVector();
        CatalogAssocData x = null;
        while (rs.next()) {
            // build the object
            x = CatalogAssocData.createValue();
            
            x.setCatalogAssocId(rs.getInt(1));
            x.setCatalogId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setCatalogAssocCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setNewCatalogId(rs.getInt(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * CatalogAssocData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT CATALOG_ASSOC_ID FROM CLW_CATALOG_ASSOC");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT CATALOG_ASSOC_ID FROM CLW_CATALOG_ASSOC");
                where = pCriteria.getSqlClause("CLW_CATALOG_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CATALOG_ASSOC.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CATALOG_ASSOC");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CATALOG_ASSOC");
                where = pCriteria.getSqlClause("CLW_CATALOG_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CATALOG_ASSOC.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CATALOG_ASSOC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CATALOG_ASSOC");
                where = pCriteria.getSqlClause("CLW_CATALOG_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CATALOG_ASSOC.equals(otherTable)){
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
     * Inserts a CatalogAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CatalogAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CatalogAssocData insert(Connection pCon, CatalogAssocData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CATALOG_ASSOC_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CATALOG_ASSOC_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCatalogAssocId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CATALOG_ASSOC (CATALOG_ASSOC_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,NEW_CATALOG_ID) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCatalogAssocId());
        pstmt.setInt(2,pData.getCatalogId());
        pstmt.setInt(3,pData.getBusEntityId());
        pstmt.setString(4,pData.getCatalogAssocCd());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());
        pstmt.setInt(9,pData.getNewCatalogId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CATALOG_ASSOC_ID="+pData.getCatalogAssocId());
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   CATALOG_ASSOC_CD="+pData.getCatalogAssocCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   NEW_CATALOG_ID="+pData.getNewCatalogId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setCatalogAssocId(0);
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
     * Updates a CatalogAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CatalogAssocData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CATALOG_ASSOC SET CATALOG_ID = ?,BUS_ENTITY_ID = ?,CATALOG_ASSOC_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,NEW_CATALOG_ID = ? WHERE CATALOG_ASSOC_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getCatalogId());
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setString(i++,pData.getCatalogAssocCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getNewCatalogId());
        pstmt.setInt(i++,pData.getCatalogAssocId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   CATALOG_ASSOC_CD="+pData.getCatalogAssocCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   NEW_CATALOG_ID="+pData.getNewCatalogId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a CatalogAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCatalogAssocId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCatalogAssocId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CATALOG_ASSOC WHERE CATALOG_ASSOC_ID = " + pCatalogAssocId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CatalogAssocData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CATALOG_ASSOC");
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
     * Inserts a CatalogAssocData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogAssocData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CatalogAssocData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CATALOG_ASSOC (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CATALOG_ASSOC_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,NEW_CATALOG_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCatalogAssocId());
        pstmt.setInt(2+4,pData.getCatalogId());
        pstmt.setInt(3+4,pData.getBusEntityId());
        pstmt.setString(4+4,pData.getCatalogAssocCd());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());
        pstmt.setInt(9+4,pData.getNewCatalogId());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CatalogAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CatalogAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CatalogAssocData insert(Connection pCon, CatalogAssocData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CatalogAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CatalogAssocData pData, boolean pLogFl)
        throws SQLException {
        CatalogAssocData oldData = null;
        if(pLogFl) {
          int id = pData.getCatalogAssocId();
          try {
          oldData = CatalogAssocDataAccess.select(pCon,id);
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
     * Deletes a CatalogAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCatalogAssocId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCatalogAssocId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CATALOG_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CATALOG_ASSOC d WHERE CATALOG_ASSOC_ID = " + pCatalogAssocId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCatalogAssocId);
        return n;
     }

    /**
     * Deletes CatalogAssocData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CATALOG_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CATALOG_ASSOC d ");
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

