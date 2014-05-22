
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        BlanketPoNumDataAccess
 * Description:  This class is used to build access methods to the CLW_BLANKET_PO_NUM table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.BlanketPoNumData;
import com.cleanwise.service.api.value.BlanketPoNumDataVector;
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
 * <code>BlanketPoNumDataAccess</code>
 */
public class BlanketPoNumDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(BlanketPoNumDataAccess.class.getName());

    /** <code>CLW_BLANKET_PO_NUM</code> table name */
	/* Primary key: BLANKET_PO_NUM_ID */
	
    public static final String CLW_BLANKET_PO_NUM = "CLW_BLANKET_PO_NUM";
    
    /** <code>BLANKET_PO_NUM_ID</code> BLANKET_PO_NUM_ID column of table CLW_BLANKET_PO_NUM */
    public static final String BLANKET_PO_NUM_ID = "BLANKET_PO_NUM_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_BLANKET_PO_NUM */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>PO_NUMBER</code> PO_NUMBER column of table CLW_BLANKET_PO_NUM */
    public static final String PO_NUMBER = "PO_NUMBER";
    /** <code>SEPERATOR</code> SEPERATOR column of table CLW_BLANKET_PO_NUM */
    public static final String SEPERATOR = "SEPERATOR";
    /** <code>CURRENT_RELEASE</code> CURRENT_RELEASE column of table CLW_BLANKET_PO_NUM */
    public static final String CURRENT_RELEASE = "CURRENT_RELEASE";
    /** <code>BLANKET_CUST_PO_NUMBER_TYPE_CD</code> BLANKET_CUST_PO_NUMBER_TYPE_CD column of table CLW_BLANKET_PO_NUM */
    public static final String BLANKET_CUST_PO_NUMBER_TYPE_CD = "BLANKET_CUST_PO_NUMBER_TYPE_CD";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_BLANKET_PO_NUM */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_BLANKET_PO_NUM */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_BLANKET_PO_NUM */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_BLANKET_PO_NUM */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_BLANKET_PO_NUM */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public BlanketPoNumDataAccess()
    {
    }

    /**
     * Gets a BlanketPoNumData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pBlanketPoNumId The key requested.
     * @return new BlanketPoNumData()
     * @throws            SQLException
     */
    public static BlanketPoNumData select(Connection pCon, int pBlanketPoNumId)
        throws SQLException, DataNotFoundException {
        BlanketPoNumData x=null;
        String sql="SELECT BLANKET_PO_NUM_ID,SHORT_DESC,PO_NUMBER,SEPERATOR,CURRENT_RELEASE,BLANKET_CUST_PO_NUMBER_TYPE_CD,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BLANKET_PO_NUM WHERE BLANKET_PO_NUM_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pBlanketPoNumId=" + pBlanketPoNumId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pBlanketPoNumId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=BlanketPoNumData.createValue();
            
            x.setBlanketPoNumId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setPoNumber(rs.getString(3));
            x.setSeperator(rs.getString(4));
            x.setCurrentRelease(rs.getInt(5));
            x.setBlanketCustPoNumberTypeCd(rs.getString(6));
            x.setStatusCd(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("BLANKET_PO_NUM_ID :" + pBlanketPoNumId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a BlanketPoNumDataVector object that consists
     * of BlanketPoNumData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new BlanketPoNumDataVector()
     * @throws            SQLException
     */
    public static BlanketPoNumDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a BlanketPoNumData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_BLANKET_PO_NUM.BLANKET_PO_NUM_ID,CLW_BLANKET_PO_NUM.SHORT_DESC,CLW_BLANKET_PO_NUM.PO_NUMBER,CLW_BLANKET_PO_NUM.SEPERATOR,CLW_BLANKET_PO_NUM.CURRENT_RELEASE,CLW_BLANKET_PO_NUM.BLANKET_CUST_PO_NUMBER_TYPE_CD,CLW_BLANKET_PO_NUM.STATUS_CD,CLW_BLANKET_PO_NUM.ADD_DATE,CLW_BLANKET_PO_NUM.ADD_BY,CLW_BLANKET_PO_NUM.MOD_DATE,CLW_BLANKET_PO_NUM.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated BlanketPoNumData Object.
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
    *@returns a populated BlanketPoNumData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         BlanketPoNumData x = BlanketPoNumData.createValue();
         
         x.setBlanketPoNumId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setPoNumber(rs.getString(3+offset));
         x.setSeperator(rs.getString(4+offset));
         x.setCurrentRelease(rs.getInt(5+offset));
         x.setBlanketCustPoNumberTypeCd(rs.getString(6+offset));
         x.setStatusCd(rs.getString(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the BlanketPoNumData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a BlanketPoNumDataVector object that consists
     * of BlanketPoNumData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new BlanketPoNumDataVector()
     * @throws            SQLException
     */
    public static BlanketPoNumDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT BLANKET_PO_NUM_ID,SHORT_DESC,PO_NUMBER,SEPERATOR,CURRENT_RELEASE,BLANKET_CUST_PO_NUMBER_TYPE_CD,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BLANKET_PO_NUM");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_BLANKET_PO_NUM.BLANKET_PO_NUM_ID,CLW_BLANKET_PO_NUM.SHORT_DESC,CLW_BLANKET_PO_NUM.PO_NUMBER,CLW_BLANKET_PO_NUM.SEPERATOR,CLW_BLANKET_PO_NUM.CURRENT_RELEASE,CLW_BLANKET_PO_NUM.BLANKET_CUST_PO_NUMBER_TYPE_CD,CLW_BLANKET_PO_NUM.STATUS_CD,CLW_BLANKET_PO_NUM.ADD_DATE,CLW_BLANKET_PO_NUM.ADD_BY,CLW_BLANKET_PO_NUM.MOD_DATE,CLW_BLANKET_PO_NUM.MOD_BY FROM CLW_BLANKET_PO_NUM");
                where = pCriteria.getSqlClause("CLW_BLANKET_PO_NUM");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_BLANKET_PO_NUM.equals(otherTable)){
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
        BlanketPoNumDataVector v = new BlanketPoNumDataVector();
        while (rs.next()) {
            BlanketPoNumData x = BlanketPoNumData.createValue();
            
            x.setBlanketPoNumId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setPoNumber(rs.getString(3));
            x.setSeperator(rs.getString(4));
            x.setCurrentRelease(rs.getInt(5));
            x.setBlanketCustPoNumberTypeCd(rs.getString(6));
            x.setStatusCd(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a BlanketPoNumDataVector object that consists
     * of BlanketPoNumData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for BlanketPoNumData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new BlanketPoNumDataVector()
     * @throws            SQLException
     */
    public static BlanketPoNumDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        BlanketPoNumDataVector v = new BlanketPoNumDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT BLANKET_PO_NUM_ID,SHORT_DESC,PO_NUMBER,SEPERATOR,CURRENT_RELEASE,BLANKET_CUST_PO_NUMBER_TYPE_CD,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BLANKET_PO_NUM WHERE BLANKET_PO_NUM_ID IN (");

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
            BlanketPoNumData x=null;
            while (rs.next()) {
                // build the object
                x=BlanketPoNumData.createValue();
                
                x.setBlanketPoNumId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setPoNumber(rs.getString(3));
                x.setSeperator(rs.getString(4));
                x.setCurrentRelease(rs.getInt(5));
                x.setBlanketCustPoNumberTypeCd(rs.getString(6));
                x.setStatusCd(rs.getString(7));
                x.setAddDate(rs.getTimestamp(8));
                x.setAddBy(rs.getString(9));
                x.setModDate(rs.getTimestamp(10));
                x.setModBy(rs.getString(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a BlanketPoNumDataVector object of all
     * BlanketPoNumData objects in the database.
     * @param pCon An open database connection.
     * @return new BlanketPoNumDataVector()
     * @throws            SQLException
     */
    public static BlanketPoNumDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT BLANKET_PO_NUM_ID,SHORT_DESC,PO_NUMBER,SEPERATOR,CURRENT_RELEASE,BLANKET_CUST_PO_NUMBER_TYPE_CD,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BLANKET_PO_NUM";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        BlanketPoNumDataVector v = new BlanketPoNumDataVector();
        BlanketPoNumData x = null;
        while (rs.next()) {
            // build the object
            x = BlanketPoNumData.createValue();
            
            x.setBlanketPoNumId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setPoNumber(rs.getString(3));
            x.setSeperator(rs.getString(4));
            x.setCurrentRelease(rs.getInt(5));
            x.setBlanketCustPoNumberTypeCd(rs.getString(6));
            x.setStatusCd(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * BlanketPoNumData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT BLANKET_PO_NUM_ID FROM CLW_BLANKET_PO_NUM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BLANKET_PO_NUM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BLANKET_PO_NUM");
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
     * Inserts a BlanketPoNumData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BlanketPoNumData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new BlanketPoNumData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BlanketPoNumData insert(Connection pCon, BlanketPoNumData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_BLANKET_PO_NUM_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_BLANKET_PO_NUM_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setBlanketPoNumId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_BLANKET_PO_NUM (BLANKET_PO_NUM_ID,SHORT_DESC,PO_NUMBER,SEPERATOR,CURRENT_RELEASE,BLANKET_CUST_PO_NUMBER_TYPE_CD,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getBlanketPoNumId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getPoNumber());
        pstmt.setString(4,pData.getSeperator());
        pstmt.setInt(5,pData.getCurrentRelease());
        pstmt.setString(6,pData.getBlanketCustPoNumberTypeCd());
        pstmt.setString(7,pData.getStatusCd());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BLANKET_PO_NUM_ID="+pData.getBlanketPoNumId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   PO_NUMBER="+pData.getPoNumber());
            log.debug("SQL:   SEPERATOR="+pData.getSeperator());
            log.debug("SQL:   CURRENT_RELEASE="+pData.getCurrentRelease());
            log.debug("SQL:   BLANKET_CUST_PO_NUMBER_TYPE_CD="+pData.getBlanketCustPoNumberTypeCd());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
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
        pData.setBlanketPoNumId(0);
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
     * Updates a BlanketPoNumData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BlanketPoNumData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BlanketPoNumData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_BLANKET_PO_NUM SET SHORT_DESC = ?,PO_NUMBER = ?,SEPERATOR = ?,CURRENT_RELEASE = ?,BLANKET_CUST_PO_NUMBER_TYPE_CD = ?,STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE BLANKET_PO_NUM_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getPoNumber());
        pstmt.setString(i++,pData.getSeperator());
        pstmt.setInt(i++,pData.getCurrentRelease());
        pstmt.setString(i++,pData.getBlanketCustPoNumberTypeCd());
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getBlanketPoNumId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   PO_NUMBER="+pData.getPoNumber());
            log.debug("SQL:   SEPERATOR="+pData.getSeperator());
            log.debug("SQL:   CURRENT_RELEASE="+pData.getCurrentRelease());
            log.debug("SQL:   BLANKET_CUST_PO_NUMBER_TYPE_CD="+pData.getBlanketCustPoNumberTypeCd());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
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
     * Deletes a BlanketPoNumData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBlanketPoNumId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBlanketPoNumId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_BLANKET_PO_NUM WHERE BLANKET_PO_NUM_ID = " + pBlanketPoNumId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes BlanketPoNumData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_BLANKET_PO_NUM");
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
     * Inserts a BlanketPoNumData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BlanketPoNumData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, BlanketPoNumData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_BLANKET_PO_NUM (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "BLANKET_PO_NUM_ID,SHORT_DESC,PO_NUMBER,SEPERATOR,CURRENT_RELEASE,BLANKET_CUST_PO_NUMBER_TYPE_CD,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getBlanketPoNumId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getPoNumber());
        pstmt.setString(4+4,pData.getSeperator());
        pstmt.setInt(5+4,pData.getCurrentRelease());
        pstmt.setString(6+4,pData.getBlanketCustPoNumberTypeCd());
        pstmt.setString(7+4,pData.getStatusCd());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a BlanketPoNumData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BlanketPoNumData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new BlanketPoNumData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BlanketPoNumData insert(Connection pCon, BlanketPoNumData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a BlanketPoNumData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BlanketPoNumData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BlanketPoNumData pData, boolean pLogFl)
        throws SQLException {
        BlanketPoNumData oldData = null;
        if(pLogFl) {
          int id = pData.getBlanketPoNumId();
          try {
          oldData = BlanketPoNumDataAccess.select(pCon,id);
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
     * Deletes a BlanketPoNumData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBlanketPoNumId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBlanketPoNumId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_BLANKET_PO_NUM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BLANKET_PO_NUM d WHERE BLANKET_PO_NUM_ID = " + pBlanketPoNumId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pBlanketPoNumId);
        return n;
     }

    /**
     * Deletes BlanketPoNumData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_BLANKET_PO_NUM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BLANKET_PO_NUM d ");
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

