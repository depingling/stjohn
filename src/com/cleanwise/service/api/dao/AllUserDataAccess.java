
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        AllUserDataAccess
 * Description:  This class is used to build access methods to the ESW_ALL_USER table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.AllUserData;
import com.cleanwise.service.api.value.AllUserDataVector;
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
 * <code>AllUserDataAccess</code>
 */
public class AllUserDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(AllUserDataAccess.class.getName());

    /** <code>ESW_ALL_USER</code> table name */
	/* Primary key: ALL_USER_ID */
	
    public static final String ESW_ALL_USER = "ESW_ALL_USER";
    
    /** <code>ALL_USER_ID</code> ALL_USER_ID column of table ESW_ALL_USER */
    public static final String ALL_USER_ID = "ALL_USER_ID";
    /** <code>USER_ID</code> USER_ID column of table ESW_ALL_USER */
    public static final String USER_ID = "USER_ID";
    /** <code>USER_NAME</code> USER_NAME column of table ESW_ALL_USER */
    public static final String USER_NAME = "USER_NAME";
    /** <code>FIRST_NAME</code> FIRST_NAME column of table ESW_ALL_USER */
    public static final String FIRST_NAME = "FIRST_NAME";
    /** <code>LAST_NAME</code> LAST_NAME column of table ESW_ALL_USER */
    public static final String LAST_NAME = "LAST_NAME";
    /** <code>PASSWORD</code> PASSWORD column of table ESW_ALL_USER */
    public static final String PASSWORD = "PASSWORD";
    /** <code>EFF_DATE</code> EFF_DATE column of table ESW_ALL_USER */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table ESW_ALL_USER */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>USER_STATUS_CD</code> USER_STATUS_CD column of table ESW_ALL_USER */
    public static final String USER_STATUS_CD = "USER_STATUS_CD";
    /** <code>USER_TYPE_CD</code> USER_TYPE_CD column of table ESW_ALL_USER */
    public static final String USER_TYPE_CD = "USER_TYPE_CD";
    /** <code>DEFAULT_STORE_ID</code> DEFAULT_STORE_ID column of table ESW_ALL_USER */
    public static final String DEFAULT_STORE_ID = "DEFAULT_STORE_ID";
    /** <code>ADD_DATE</code> ADD_DATE column of table ESW_ALL_USER */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table ESW_ALL_USER */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table ESW_ALL_USER */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table ESW_ALL_USER */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public AllUserDataAccess()
    {
    }

    /**
     * Gets a AllUserData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pAllUserId The key requested.
     * @return new AllUserData()
     * @throws            SQLException
     */
    public static AllUserData select(Connection pCon, int pAllUserId)
        throws SQLException, DataNotFoundException {
        AllUserData x=null;
        String sql="SELECT ALL_USER_ID,USER_ID,USER_NAME,FIRST_NAME,LAST_NAME,PASSWORD,EFF_DATE,EXP_DATE,USER_STATUS_CD,USER_TYPE_CD,DEFAULT_STORE_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM ESW_ALL_USER WHERE ALL_USER_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pAllUserId=" + pAllUserId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pAllUserId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=AllUserData.createValue();
            
            x.setAllUserId(rs.getInt(1));
            x.setUserId(rs.getInt(2));
            x.setUserName(rs.getString(3));
            x.setFirstName(rs.getString(4));
            x.setLastName(rs.getString(5));
            x.setPassword(rs.getString(6));
            x.setEffDate(rs.getDate(7));
            x.setExpDate(rs.getDate(8));
            x.setUserStatusCd(rs.getString(9));
            x.setUserTypeCd(rs.getString(10));
            x.setDefaultStoreId(rs.getInt(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddBy(rs.getString(13));
            x.setModDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ALL_USER_ID :" + pAllUserId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a AllUserDataVector object that consists
     * of AllUserData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new AllUserDataVector()
     * @throws            SQLException
     */
    public static AllUserDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a AllUserData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "ESW_ALL_USER.ALL_USER_ID,ESW_ALL_USER.USER_ID,ESW_ALL_USER.USER_NAME,ESW_ALL_USER.FIRST_NAME,ESW_ALL_USER.LAST_NAME,ESW_ALL_USER.PASSWORD,ESW_ALL_USER.EFF_DATE,ESW_ALL_USER.EXP_DATE,ESW_ALL_USER.USER_STATUS_CD,ESW_ALL_USER.USER_TYPE_CD,ESW_ALL_USER.DEFAULT_STORE_ID,ESW_ALL_USER.ADD_DATE,ESW_ALL_USER.ADD_BY,ESW_ALL_USER.MOD_DATE,ESW_ALL_USER.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated AllUserData Object.
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
    *@returns a populated AllUserData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         AllUserData x = AllUserData.createValue();
         
         x.setAllUserId(rs.getInt(1+offset));
         x.setUserId(rs.getInt(2+offset));
         x.setUserName(rs.getString(3+offset));
         x.setFirstName(rs.getString(4+offset));
         x.setLastName(rs.getString(5+offset));
         x.setPassword(rs.getString(6+offset));
         x.setEffDate(rs.getDate(7+offset));
         x.setExpDate(rs.getDate(8+offset));
         x.setUserStatusCd(rs.getString(9+offset));
         x.setUserTypeCd(rs.getString(10+offset));
         x.setDefaultStoreId(rs.getInt(11+offset));
         x.setAddDate(rs.getTimestamp(12+offset));
         x.setAddBy(rs.getString(13+offset));
         x.setModDate(rs.getTimestamp(14+offset));
         x.setModBy(rs.getString(15+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the AllUserData Object represents.
    */
    public int getColumnCount(){
        return 15;
    }

    /**
     * Gets a AllUserDataVector object that consists
     * of AllUserData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new AllUserDataVector()
     * @throws            SQLException
     */
    public static AllUserDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ALL_USER_ID,USER_ID,USER_NAME,FIRST_NAME,LAST_NAME,PASSWORD,EFF_DATE,EXP_DATE,USER_STATUS_CD,USER_TYPE_CD,DEFAULT_STORE_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM ESW_ALL_USER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT ESW_ALL_USER.ALL_USER_ID,ESW_ALL_USER.USER_ID,ESW_ALL_USER.USER_NAME,ESW_ALL_USER.FIRST_NAME,ESW_ALL_USER.LAST_NAME,ESW_ALL_USER.PASSWORD,ESW_ALL_USER.EFF_DATE,ESW_ALL_USER.EXP_DATE,ESW_ALL_USER.USER_STATUS_CD,ESW_ALL_USER.USER_TYPE_CD,ESW_ALL_USER.DEFAULT_STORE_ID,ESW_ALL_USER.ADD_DATE,ESW_ALL_USER.ADD_BY,ESW_ALL_USER.MOD_DATE,ESW_ALL_USER.MOD_BY FROM ESW_ALL_USER");
                where = pCriteria.getSqlClause("ESW_ALL_USER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!ESW_ALL_USER.equals(otherTable)){
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
        AllUserDataVector v = new AllUserDataVector();
        while (rs.next()) {
            AllUserData x = AllUserData.createValue();
            
            x.setAllUserId(rs.getInt(1));
            x.setUserId(rs.getInt(2));
            x.setUserName(rs.getString(3));
            x.setFirstName(rs.getString(4));
            x.setLastName(rs.getString(5));
            x.setPassword(rs.getString(6));
            x.setEffDate(rs.getDate(7));
            x.setExpDate(rs.getDate(8));
            x.setUserStatusCd(rs.getString(9));
            x.setUserTypeCd(rs.getString(10));
            x.setDefaultStoreId(rs.getInt(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddBy(rs.getString(13));
            x.setModDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a AllUserDataVector object that consists
     * of AllUserData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for AllUserData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new AllUserDataVector()
     * @throws            SQLException
     */
    public static AllUserDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        AllUserDataVector v = new AllUserDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ALL_USER_ID,USER_ID,USER_NAME,FIRST_NAME,LAST_NAME,PASSWORD,EFF_DATE,EXP_DATE,USER_STATUS_CD,USER_TYPE_CD,DEFAULT_STORE_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM ESW_ALL_USER WHERE ALL_USER_ID IN (");

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
            AllUserData x=null;
            while (rs.next()) {
                // build the object
                x=AllUserData.createValue();
                
                x.setAllUserId(rs.getInt(1));
                x.setUserId(rs.getInt(2));
                x.setUserName(rs.getString(3));
                x.setFirstName(rs.getString(4));
                x.setLastName(rs.getString(5));
                x.setPassword(rs.getString(6));
                x.setEffDate(rs.getDate(7));
                x.setExpDate(rs.getDate(8));
                x.setUserStatusCd(rs.getString(9));
                x.setUserTypeCd(rs.getString(10));
                x.setDefaultStoreId(rs.getInt(11));
                x.setAddDate(rs.getTimestamp(12));
                x.setAddBy(rs.getString(13));
                x.setModDate(rs.getTimestamp(14));
                x.setModBy(rs.getString(15));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a AllUserDataVector object of all
     * AllUserData objects in the database.
     * @param pCon An open database connection.
     * @return new AllUserDataVector()
     * @throws            SQLException
     */
    public static AllUserDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ALL_USER_ID,USER_ID,USER_NAME,FIRST_NAME,LAST_NAME,PASSWORD,EFF_DATE,EXP_DATE,USER_STATUS_CD,USER_TYPE_CD,DEFAULT_STORE_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM ESW_ALL_USER";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        AllUserDataVector v = new AllUserDataVector();
        AllUserData x = null;
        while (rs.next()) {
            // build the object
            x = AllUserData.createValue();
            
            x.setAllUserId(rs.getInt(1));
            x.setUserId(rs.getInt(2));
            x.setUserName(rs.getString(3));
            x.setFirstName(rs.getString(4));
            x.setLastName(rs.getString(5));
            x.setPassword(rs.getString(6));
            x.setEffDate(rs.getDate(7));
            x.setExpDate(rs.getDate(8));
            x.setUserStatusCd(rs.getString(9));
            x.setUserTypeCd(rs.getString(10));
            x.setDefaultStoreId(rs.getInt(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddBy(rs.getString(13));
            x.setModDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * AllUserData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT ALL_USER_ID FROM ESW_ALL_USER");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT ALL_USER_ID FROM ESW_ALL_USER");
                where = pCriteria.getSqlClause("ESW_ALL_USER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!ESW_ALL_USER.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM ESW_ALL_USER");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM ESW_ALL_USER");
                where = pCriteria.getSqlClause("ESW_ALL_USER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!ESW_ALL_USER.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM ESW_ALL_USER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM ESW_ALL_USER");
                where = pCriteria.getSqlClause("ESW_ALL_USER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!ESW_ALL_USER.equals(otherTable)){
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
     * Inserts a AllUserData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AllUserData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new AllUserData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AllUserData insert(Connection pCon, AllUserData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT ESW_ALL_USER_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT ESW_ALL_USER_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setAllUserId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO ESW_ALL_USER (ALL_USER_ID,USER_ID,USER_NAME,FIRST_NAME,LAST_NAME,PASSWORD,EFF_DATE,EXP_DATE,USER_STATUS_CD,USER_TYPE_CD,DEFAULT_STORE_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getAllUserId());
        pstmt.setInt(2,pData.getUserId());
        pstmt.setString(3,pData.getUserName());
        pstmt.setString(4,pData.getFirstName());
        pstmt.setString(5,pData.getLastName());
        pstmt.setString(6,pData.getPassword());
        pstmt.setDate(7,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(8,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(9,pData.getUserStatusCd());
        pstmt.setString(10,pData.getUserTypeCd());
        pstmt.setInt(11,pData.getDefaultStoreId());
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(13,pData.getAddBy());
        pstmt.setTimestamp(14,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(15,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ALL_USER_ID="+pData.getAllUserId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   USER_NAME="+pData.getUserName());
            log.debug("SQL:   FIRST_NAME="+pData.getFirstName());
            log.debug("SQL:   LAST_NAME="+pData.getLastName());
            log.debug("SQL:   PASSWORD="+pData.getPassword());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   USER_STATUS_CD="+pData.getUserStatusCd());
            log.debug("SQL:   USER_TYPE_CD="+pData.getUserTypeCd());
            log.debug("SQL:   DEFAULT_STORE_ID="+pData.getDefaultStoreId());
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
        pData.setAllUserId(0);
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
     * Updates a AllUserData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AllUserData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AllUserData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE ESW_ALL_USER SET USER_ID = ?,USER_NAME = ?,FIRST_NAME = ?,LAST_NAME = ?,PASSWORD = ?,EFF_DATE = ?,EXP_DATE = ?,USER_STATUS_CD = ?,USER_TYPE_CD = ?,DEFAULT_STORE_ID = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ALL_USER_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getUserId());
        pstmt.setString(i++,pData.getUserName());
        pstmt.setString(i++,pData.getFirstName());
        pstmt.setString(i++,pData.getLastName());
        pstmt.setString(i++,pData.getPassword());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(i++,pData.getUserStatusCd());
        pstmt.setString(i++,pData.getUserTypeCd());
        pstmt.setInt(i++,pData.getDefaultStoreId());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getAllUserId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   USER_NAME="+pData.getUserName());
            log.debug("SQL:   FIRST_NAME="+pData.getFirstName());
            log.debug("SQL:   LAST_NAME="+pData.getLastName());
            log.debug("SQL:   PASSWORD="+pData.getPassword());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   USER_STATUS_CD="+pData.getUserStatusCd());
            log.debug("SQL:   USER_TYPE_CD="+pData.getUserTypeCd());
            log.debug("SQL:   DEFAULT_STORE_ID="+pData.getDefaultStoreId());
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
     * Deletes a AllUserData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAllUserId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAllUserId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM ESW_ALL_USER WHERE ALL_USER_ID = " + pAllUserId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes AllUserData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM ESW_ALL_USER");
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
     * Inserts a AllUserData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AllUserData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, AllUserData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LESW_ALL_USER (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ALL_USER_ID,USER_ID,USER_NAME,FIRST_NAME,LAST_NAME,PASSWORD,EFF_DATE,EXP_DATE,USER_STATUS_CD,USER_TYPE_CD,DEFAULT_STORE_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getAllUserId());
        pstmt.setInt(2+4,pData.getUserId());
        pstmt.setString(3+4,pData.getUserName());
        pstmt.setString(4+4,pData.getFirstName());
        pstmt.setString(5+4,pData.getLastName());
        pstmt.setString(6+4,pData.getPassword());
        pstmt.setDate(7+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(8+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(9+4,pData.getUserStatusCd());
        pstmt.setString(10+4,pData.getUserTypeCd());
        pstmt.setInt(11+4,pData.getDefaultStoreId());
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(13+4,pData.getAddBy());
        pstmt.setTimestamp(14+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(15+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a AllUserData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AllUserData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new AllUserData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AllUserData insert(Connection pCon, AllUserData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a AllUserData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AllUserData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AllUserData pData, boolean pLogFl)
        throws SQLException {
        AllUserData oldData = null;
        if(pLogFl) {
          int id = pData.getAllUserId();
          try {
          oldData = AllUserDataAccess.select(pCon,id);
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
     * Deletes a AllUserData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAllUserId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAllUserId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LESW_ALL_USER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM ESW_ALL_USER d WHERE ALL_USER_ID = " + pAllUserId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pAllUserId);
        return n;
     }

    /**
     * Deletes AllUserData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LESW_ALL_USER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM ESW_ALL_USER d ");
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

