
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PriceListAssocDataAccess
 * Description:  This class is used to build access methods to the CLW_PRICE_LIST_ASSOC table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PriceListAssocData;
import com.cleanwise.service.api.value.PriceListAssocDataVector;
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
 * <code>PriceListAssocDataAccess</code>
 */
public class PriceListAssocDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PriceListAssocDataAccess.class.getName());

    /** <code>CLW_PRICE_LIST_ASSOC</code> table name */
	/* Primary key: PRICE_LIST_ASSOC_ID */
	
    public static final String CLW_PRICE_LIST_ASSOC = "CLW_PRICE_LIST_ASSOC";
    
    /** <code>PRICE_LIST_ASSOC_ID</code> PRICE_LIST_ASSOC_ID column of table CLW_PRICE_LIST_ASSOC */
    public static final String PRICE_LIST_ASSOC_ID = "PRICE_LIST_ASSOC_ID";
    /** <code>PRICE_LIST_ID</code> PRICE_LIST_ID column of table CLW_PRICE_LIST_ASSOC */
    public static final String PRICE_LIST_ID = "PRICE_LIST_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_PRICE_LIST_ASSOC */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>PRICE_LIST_ASSOC_CD</code> PRICE_LIST_ASSOC_CD column of table CLW_PRICE_LIST_ASSOC */
    public static final String PRICE_LIST_ASSOC_CD = "PRICE_LIST_ASSOC_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PRICE_LIST_ASSOC */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PRICE_LIST_ASSOC */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PRICE_LIST_ASSOC */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PRICE_LIST_ASSOC */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public PriceListAssocDataAccess()
    {
    }

    /**
     * Gets a PriceListAssocData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPriceListAssocId The key requested.
     * @return new PriceListAssocData()
     * @throws            SQLException
     */
    public static PriceListAssocData select(Connection pCon, int pPriceListAssocId)
        throws SQLException, DataNotFoundException {
        PriceListAssocData x=null;
        String sql="SELECT PRICE_LIST_ASSOC_ID,PRICE_LIST_ID,BUS_ENTITY_ID,PRICE_LIST_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRICE_LIST_ASSOC WHERE PRICE_LIST_ASSOC_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPriceListAssocId=" + pPriceListAssocId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPriceListAssocId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PriceListAssocData.createValue();
            
            x.setPriceListAssocId(rs.getInt(1));
            x.setPriceListId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setPriceListAssocCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PRICE_LIST_ASSOC_ID :" + pPriceListAssocId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PriceListAssocDataVector object that consists
     * of PriceListAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PriceListAssocDataVector()
     * @throws            SQLException
     */
    public static PriceListAssocDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PriceListAssocData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PRICE_LIST_ASSOC.PRICE_LIST_ASSOC_ID,CLW_PRICE_LIST_ASSOC.PRICE_LIST_ID,CLW_PRICE_LIST_ASSOC.BUS_ENTITY_ID,CLW_PRICE_LIST_ASSOC.PRICE_LIST_ASSOC_CD,CLW_PRICE_LIST_ASSOC.ADD_DATE,CLW_PRICE_LIST_ASSOC.ADD_BY,CLW_PRICE_LIST_ASSOC.MOD_DATE,CLW_PRICE_LIST_ASSOC.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PriceListAssocData Object.
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
    *@returns a populated PriceListAssocData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PriceListAssocData x = PriceListAssocData.createValue();
         
         x.setPriceListAssocId(rs.getInt(1+offset));
         x.setPriceListId(rs.getInt(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setPriceListAssocCd(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PriceListAssocData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a PriceListAssocDataVector object that consists
     * of PriceListAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PriceListAssocDataVector()
     * @throws            SQLException
     */
    public static PriceListAssocDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PRICE_LIST_ASSOC_ID,PRICE_LIST_ID,BUS_ENTITY_ID,PRICE_LIST_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRICE_LIST_ASSOC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PRICE_LIST_ASSOC.PRICE_LIST_ASSOC_ID,CLW_PRICE_LIST_ASSOC.PRICE_LIST_ID,CLW_PRICE_LIST_ASSOC.BUS_ENTITY_ID,CLW_PRICE_LIST_ASSOC.PRICE_LIST_ASSOC_CD,CLW_PRICE_LIST_ASSOC.ADD_DATE,CLW_PRICE_LIST_ASSOC.ADD_BY,CLW_PRICE_LIST_ASSOC.MOD_DATE,CLW_PRICE_LIST_ASSOC.MOD_BY FROM CLW_PRICE_LIST_ASSOC");
                where = pCriteria.getSqlClause("CLW_PRICE_LIST_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_LIST_ASSOC.equals(otherTable)){
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
        PriceListAssocDataVector v = new PriceListAssocDataVector();
        while (rs.next()) {
            PriceListAssocData x = PriceListAssocData.createValue();
            
            x.setPriceListAssocId(rs.getInt(1));
            x.setPriceListId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setPriceListAssocCd(rs.getString(4));
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
     * Gets a PriceListAssocDataVector object that consists
     * of PriceListAssocData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PriceListAssocData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PriceListAssocDataVector()
     * @throws            SQLException
     */
    public static PriceListAssocDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PriceListAssocDataVector v = new PriceListAssocDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PRICE_LIST_ASSOC_ID,PRICE_LIST_ID,BUS_ENTITY_ID,PRICE_LIST_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRICE_LIST_ASSOC WHERE PRICE_LIST_ASSOC_ID IN (");

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
            PriceListAssocData x=null;
            while (rs.next()) {
                // build the object
                x=PriceListAssocData.createValue();
                
                x.setPriceListAssocId(rs.getInt(1));
                x.setPriceListId(rs.getInt(2));
                x.setBusEntityId(rs.getInt(3));
                x.setPriceListAssocCd(rs.getString(4));
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
     * Gets a PriceListAssocDataVector object of all
     * PriceListAssocData objects in the database.
     * @param pCon An open database connection.
     * @return new PriceListAssocDataVector()
     * @throws            SQLException
     */
    public static PriceListAssocDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PRICE_LIST_ASSOC_ID,PRICE_LIST_ID,BUS_ENTITY_ID,PRICE_LIST_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRICE_LIST_ASSOC";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PriceListAssocDataVector v = new PriceListAssocDataVector();
        PriceListAssocData x = null;
        while (rs.next()) {
            // build the object
            x = PriceListAssocData.createValue();
            
            x.setPriceListAssocId(rs.getInt(1));
            x.setPriceListId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setPriceListAssocCd(rs.getString(4));
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
     * PriceListAssocData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT PRICE_LIST_ASSOC_ID FROM CLW_PRICE_LIST_ASSOC");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT PRICE_LIST_ASSOC_ID FROM CLW_PRICE_LIST_ASSOC");
                where = pCriteria.getSqlClause("CLW_PRICE_LIST_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_LIST_ASSOC.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_LIST_ASSOC");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_LIST_ASSOC");
                where = pCriteria.getSqlClause("CLW_PRICE_LIST_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_LIST_ASSOC.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_LIST_ASSOC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_LIST_ASSOC");
                where = pCriteria.getSqlClause("CLW_PRICE_LIST_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_LIST_ASSOC.equals(otherTable)){
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
     * Inserts a PriceListAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new PriceListAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PriceListAssocData insert(Connection pCon, PriceListAssocData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PRICE_LIST_ASSOC_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PRICE_LIST_ASSOC_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPriceListAssocId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PRICE_LIST_ASSOC (PRICE_LIST_ASSOC_ID,PRICE_LIST_ID,BUS_ENTITY_ID,PRICE_LIST_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getPriceListAssocId());
        pstmt.setInt(2,pData.getPriceListId());
        pstmt.setInt(3,pData.getBusEntityId());
        pstmt.setString(4,pData.getPriceListAssocCd());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRICE_LIST_ASSOC_ID="+pData.getPriceListAssocId());
            log.debug("SQL:   PRICE_LIST_ID="+pData.getPriceListId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   PRICE_LIST_ASSOC_CD="+pData.getPriceListAssocCd());
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
        pData.setPriceListAssocId(0);
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
     * Updates a PriceListAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PriceListAssocData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PRICE_LIST_ASSOC SET PRICE_LIST_ID = ?,BUS_ENTITY_ID = ?,PRICE_LIST_ASSOC_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE PRICE_LIST_ASSOC_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getPriceListId());
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setString(i++,pData.getPriceListAssocCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getPriceListAssocId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRICE_LIST_ID="+pData.getPriceListId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   PRICE_LIST_ASSOC_CD="+pData.getPriceListAssocCd());
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
     * Deletes a PriceListAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPriceListAssocId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPriceListAssocId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PRICE_LIST_ASSOC WHERE PRICE_LIST_ASSOC_ID = " + pPriceListAssocId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PriceListAssocData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PRICE_LIST_ASSOC");
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
     * Inserts a PriceListAssocData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListAssocData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PriceListAssocData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PRICE_LIST_ASSOC (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PRICE_LIST_ASSOC_ID,PRICE_LIST_ID,BUS_ENTITY_ID,PRICE_LIST_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPriceListAssocId());
        pstmt.setInt(2+4,pData.getPriceListId());
        pstmt.setInt(3+4,pData.getBusEntityId());
        pstmt.setString(4+4,pData.getPriceListAssocCd());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PriceListAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new PriceListAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PriceListAssocData insert(Connection pCon, PriceListAssocData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PriceListAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PriceListAssocData pData, boolean pLogFl)
        throws SQLException {
        PriceListAssocData oldData = null;
        if(pLogFl) {
          int id = pData.getPriceListAssocId();
          try {
          oldData = PriceListAssocDataAccess.select(pCon,id);
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
     * Deletes a PriceListAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPriceListAssocId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPriceListAssocId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PRICE_LIST_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRICE_LIST_ASSOC d WHERE PRICE_LIST_ASSOC_ID = " + pPriceListAssocId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPriceListAssocId);
        return n;
     }

    /**
     * Deletes PriceListAssocData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PRICE_LIST_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRICE_LIST_ASSOC d ");
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

