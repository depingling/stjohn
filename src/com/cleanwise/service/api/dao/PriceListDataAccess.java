
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PriceListDataAccess
 * Description:  This class is used to build access methods to the CLW_PRICE_LIST table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PriceListData;
import com.cleanwise.service.api.value.PriceListDataVector;
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
 * <code>PriceListDataAccess</code>
 */
public class PriceListDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PriceListDataAccess.class.getName());

    /** <code>CLW_PRICE_LIST</code> table name */
	/* Primary key: PRICE_LIST_ID */
	
    public static final String CLW_PRICE_LIST = "CLW_PRICE_LIST";
    
    /** <code>PRICE_LIST_ID</code> PRICE_LIST_ID column of table CLW_PRICE_LIST */
    public static final String PRICE_LIST_ID = "PRICE_LIST_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_PRICE_LIST */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>RANK</code> RANK column of table CLW_PRICE_LIST */
    public static final String RANK = "RANK";
    /** <code>PRICE_LIST_STATUS_CD</code> PRICE_LIST_STATUS_CD column of table CLW_PRICE_LIST */
    public static final String PRICE_LIST_STATUS_CD = "PRICE_LIST_STATUS_CD";
    /** <code>PRICE_LIST_TYPE_CD</code> PRICE_LIST_TYPE_CD column of table CLW_PRICE_LIST */
    public static final String PRICE_LIST_TYPE_CD = "PRICE_LIST_TYPE_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PRICE_LIST */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PRICE_LIST */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PRICE_LIST */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PRICE_LIST */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public PriceListDataAccess()
    {
    }

    /**
     * Gets a PriceListData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPriceListId The key requested.
     * @return new PriceListData()
     * @throws            SQLException
     */
    public static PriceListData select(Connection pCon, int pPriceListId)
        throws SQLException, DataNotFoundException {
        PriceListData x=null;
        String sql="SELECT PRICE_LIST_ID,SHORT_DESC,RANK,PRICE_LIST_STATUS_CD,PRICE_LIST_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRICE_LIST WHERE PRICE_LIST_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPriceListId=" + pPriceListId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPriceListId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PriceListData.createValue();
            
            x.setPriceListId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setRank(rs.getInt(3));
            x.setPriceListStatusCd(rs.getString(4));
            x.setPriceListTypeCd(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PRICE_LIST_ID :" + pPriceListId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PriceListDataVector object that consists
     * of PriceListData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PriceListDataVector()
     * @throws            SQLException
     */
    public static PriceListDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PriceListData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PRICE_LIST.PRICE_LIST_ID,CLW_PRICE_LIST.SHORT_DESC,CLW_PRICE_LIST.RANK,CLW_PRICE_LIST.PRICE_LIST_STATUS_CD,CLW_PRICE_LIST.PRICE_LIST_TYPE_CD,CLW_PRICE_LIST.ADD_DATE,CLW_PRICE_LIST.ADD_BY,CLW_PRICE_LIST.MOD_DATE,CLW_PRICE_LIST.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PriceListData Object.
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
    *@returns a populated PriceListData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PriceListData x = PriceListData.createValue();
         
         x.setPriceListId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setRank(rs.getInt(3+offset));
         x.setPriceListStatusCd(rs.getString(4+offset));
         x.setPriceListTypeCd(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PriceListData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a PriceListDataVector object that consists
     * of PriceListData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PriceListDataVector()
     * @throws            SQLException
     */
    public static PriceListDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PRICE_LIST_ID,SHORT_DESC,RANK,PRICE_LIST_STATUS_CD,PRICE_LIST_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRICE_LIST");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PRICE_LIST.PRICE_LIST_ID,CLW_PRICE_LIST.SHORT_DESC,CLW_PRICE_LIST.RANK,CLW_PRICE_LIST.PRICE_LIST_STATUS_CD,CLW_PRICE_LIST.PRICE_LIST_TYPE_CD,CLW_PRICE_LIST.ADD_DATE,CLW_PRICE_LIST.ADD_BY,CLW_PRICE_LIST.MOD_DATE,CLW_PRICE_LIST.MOD_BY FROM CLW_PRICE_LIST");
                where = pCriteria.getSqlClause("CLW_PRICE_LIST");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_LIST.equals(otherTable)){
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
        PriceListDataVector v = new PriceListDataVector();
        while (rs.next()) {
            PriceListData x = PriceListData.createValue();
            
            x.setPriceListId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setRank(rs.getInt(3));
            x.setPriceListStatusCd(rs.getString(4));
            x.setPriceListTypeCd(rs.getString(5));
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
     * Gets a PriceListDataVector object that consists
     * of PriceListData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PriceListData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PriceListDataVector()
     * @throws            SQLException
     */
    public static PriceListDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PriceListDataVector v = new PriceListDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PRICE_LIST_ID,SHORT_DESC,RANK,PRICE_LIST_STATUS_CD,PRICE_LIST_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRICE_LIST WHERE PRICE_LIST_ID IN (");

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
            PriceListData x=null;
            while (rs.next()) {
                // build the object
                x=PriceListData.createValue();
                
                x.setPriceListId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setRank(rs.getInt(3));
                x.setPriceListStatusCd(rs.getString(4));
                x.setPriceListTypeCd(rs.getString(5));
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
     * Gets a PriceListDataVector object of all
     * PriceListData objects in the database.
     * @param pCon An open database connection.
     * @return new PriceListDataVector()
     * @throws            SQLException
     */
    public static PriceListDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PRICE_LIST_ID,SHORT_DESC,RANK,PRICE_LIST_STATUS_CD,PRICE_LIST_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRICE_LIST";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PriceListDataVector v = new PriceListDataVector();
        PriceListData x = null;
        while (rs.next()) {
            // build the object
            x = PriceListData.createValue();
            
            x.setPriceListId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setRank(rs.getInt(3));
            x.setPriceListStatusCd(rs.getString(4));
            x.setPriceListTypeCd(rs.getString(5));
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
     * PriceListData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT PRICE_LIST_ID FROM CLW_PRICE_LIST");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT PRICE_LIST_ID FROM CLW_PRICE_LIST");
                where = pCriteria.getSqlClause("CLW_PRICE_LIST");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_LIST.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_LIST");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_LIST");
                where = pCriteria.getSqlClause("CLW_PRICE_LIST");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_LIST.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_LIST");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_LIST");
                where = pCriteria.getSqlClause("CLW_PRICE_LIST");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_LIST.equals(otherTable)){
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
     * Inserts a PriceListData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new PriceListData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PriceListData insert(Connection pCon, PriceListData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PRICE_LIST_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PRICE_LIST_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPriceListId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PRICE_LIST (PRICE_LIST_ID,SHORT_DESC,RANK,PRICE_LIST_STATUS_CD,PRICE_LIST_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getPriceListId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setInt(3,pData.getRank());
        pstmt.setString(4,pData.getPriceListStatusCd());
        pstmt.setString(5,pData.getPriceListTypeCd());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRICE_LIST_ID="+pData.getPriceListId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   RANK="+pData.getRank());
            log.debug("SQL:   PRICE_LIST_STATUS_CD="+pData.getPriceListStatusCd());
            log.debug("SQL:   PRICE_LIST_TYPE_CD="+pData.getPriceListTypeCd());
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
        pData.setPriceListId(0);
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
     * Updates a PriceListData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PriceListData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PRICE_LIST SET SHORT_DESC = ?,RANK = ?,PRICE_LIST_STATUS_CD = ?,PRICE_LIST_TYPE_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE PRICE_LIST_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setInt(i++,pData.getRank());
        pstmt.setString(i++,pData.getPriceListStatusCd());
        pstmt.setString(i++,pData.getPriceListTypeCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getPriceListId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   RANK="+pData.getRank());
            log.debug("SQL:   PRICE_LIST_STATUS_CD="+pData.getPriceListStatusCd());
            log.debug("SQL:   PRICE_LIST_TYPE_CD="+pData.getPriceListTypeCd());
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
     * Deletes a PriceListData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPriceListId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPriceListId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PRICE_LIST WHERE PRICE_LIST_ID = " + pPriceListId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PriceListData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PRICE_LIST");
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
     * Inserts a PriceListData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PriceListData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PRICE_LIST (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PRICE_LIST_ID,SHORT_DESC,RANK,PRICE_LIST_STATUS_CD,PRICE_LIST_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPriceListId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setInt(3+4,pData.getRank());
        pstmt.setString(4+4,pData.getPriceListStatusCd());
        pstmt.setString(5+4,pData.getPriceListTypeCd());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PriceListData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new PriceListData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PriceListData insert(Connection pCon, PriceListData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PriceListData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PriceListData pData, boolean pLogFl)
        throws SQLException {
        PriceListData oldData = null;
        if(pLogFl) {
          int id = pData.getPriceListId();
          try {
          oldData = PriceListDataAccess.select(pCon,id);
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
     * Deletes a PriceListData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPriceListId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPriceListId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PRICE_LIST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRICE_LIST d WHERE PRICE_LIST_ID = " + pPriceListId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPriceListId);
        return n;
     }

    /**
     * Deletes PriceListData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PRICE_LIST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRICE_LIST d ");
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

