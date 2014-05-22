
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        StoreProfileDataAccess
 * Description:  This class is used to build access methods to the CLW_STORE_PROFILE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.StoreProfileData;
import com.cleanwise.service.api.value.StoreProfileDataVector;
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
 * <code>StoreProfileDataAccess</code>
 */
public class StoreProfileDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(StoreProfileDataAccess.class.getName());

    /** <code>CLW_STORE_PROFILE</code> table name */
	/* Primary key: STORE_PROFILE_ID */
	
    public static final String CLW_STORE_PROFILE = "CLW_STORE_PROFILE";
    
    /** <code>STORE_PROFILE_ID</code> STORE_PROFILE_ID column of table CLW_STORE_PROFILE */
    public static final String STORE_PROFILE_ID = "STORE_PROFILE_ID";
    /** <code>STORE_ID</code> STORE_ID column of table CLW_STORE_PROFILE */
    public static final String STORE_ID = "STORE_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_STORE_PROFILE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>DISPLAY</code> DISPLAY column of table CLW_STORE_PROFILE */
    public static final String DISPLAY = "DISPLAY";
    /** <code>EDIT</code> EDIT column of table CLW_STORE_PROFILE */
    public static final String EDIT = "EDIT";
    /** <code>OPTION_TYPE_CD</code> OPTION_TYPE_CD column of table CLW_STORE_PROFILE */
    public static final String OPTION_TYPE_CD = "OPTION_TYPE_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_STORE_PROFILE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_STORE_PROFILE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_STORE_PROFILE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_STORE_PROFILE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public StoreProfileDataAccess()
    {
    }

    /**
     * Gets a StoreProfileData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pStoreProfileId The key requested.
     * @return new StoreProfileData()
     * @throws            SQLException
     */
    public static StoreProfileData select(Connection pCon, int pStoreProfileId)
        throws SQLException, DataNotFoundException {
        StoreProfileData x=null;
        String sql="SELECT STORE_PROFILE_ID,STORE_ID,SHORT_DESC,DISPLAY,EDIT,OPTION_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_STORE_PROFILE WHERE STORE_PROFILE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pStoreProfileId=" + pStoreProfileId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pStoreProfileId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=StoreProfileData.createValue();
            
            x.setStoreProfileId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setDisplay(rs.getString(4));
            x.setEdit(rs.getString(5));
            x.setOptionTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("STORE_PROFILE_ID :" + pStoreProfileId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a StoreProfileDataVector object that consists
     * of StoreProfileData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new StoreProfileDataVector()
     * @throws            SQLException
     */
    public static StoreProfileDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a StoreProfileData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_STORE_PROFILE.STORE_PROFILE_ID,CLW_STORE_PROFILE.STORE_ID,CLW_STORE_PROFILE.SHORT_DESC,CLW_STORE_PROFILE.DISPLAY,CLW_STORE_PROFILE.EDIT,CLW_STORE_PROFILE.OPTION_TYPE_CD,CLW_STORE_PROFILE.ADD_DATE,CLW_STORE_PROFILE.ADD_BY,CLW_STORE_PROFILE.MOD_DATE,CLW_STORE_PROFILE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated StoreProfileData Object.
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
    *@returns a populated StoreProfileData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         StoreProfileData x = StoreProfileData.createValue();
         
         x.setStoreProfileId(rs.getInt(1+offset));
         x.setStoreId(rs.getInt(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setDisplay(rs.getString(4+offset));
         x.setEdit(rs.getString(5+offset));
         x.setOptionTypeCd(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the StoreProfileData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a StoreProfileDataVector object that consists
     * of StoreProfileData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new StoreProfileDataVector()
     * @throws            SQLException
     */
    public static StoreProfileDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT STORE_PROFILE_ID,STORE_ID,SHORT_DESC,DISPLAY,EDIT,OPTION_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_STORE_PROFILE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_STORE_PROFILE.STORE_PROFILE_ID,CLW_STORE_PROFILE.STORE_ID,CLW_STORE_PROFILE.SHORT_DESC,CLW_STORE_PROFILE.DISPLAY,CLW_STORE_PROFILE.EDIT,CLW_STORE_PROFILE.OPTION_TYPE_CD,CLW_STORE_PROFILE.ADD_DATE,CLW_STORE_PROFILE.ADD_BY,CLW_STORE_PROFILE.MOD_DATE,CLW_STORE_PROFILE.MOD_BY FROM CLW_STORE_PROFILE");
                where = pCriteria.getSqlClause("CLW_STORE_PROFILE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STORE_PROFILE.equals(otherTable)){
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
        StoreProfileDataVector v = new StoreProfileDataVector();
        while (rs.next()) {
            StoreProfileData x = StoreProfileData.createValue();
            
            x.setStoreProfileId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setDisplay(rs.getString(4));
            x.setEdit(rs.getString(5));
            x.setOptionTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a StoreProfileDataVector object that consists
     * of StoreProfileData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for StoreProfileData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new StoreProfileDataVector()
     * @throws            SQLException
     */
    public static StoreProfileDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        StoreProfileDataVector v = new StoreProfileDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT STORE_PROFILE_ID,STORE_ID,SHORT_DESC,DISPLAY,EDIT,OPTION_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_STORE_PROFILE WHERE STORE_PROFILE_ID IN (");

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
            StoreProfileData x=null;
            while (rs.next()) {
                // build the object
                x=StoreProfileData.createValue();
                
                x.setStoreProfileId(rs.getInt(1));
                x.setStoreId(rs.getInt(2));
                x.setShortDesc(rs.getString(3));
                x.setDisplay(rs.getString(4));
                x.setEdit(rs.getString(5));
                x.setOptionTypeCd(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a StoreProfileDataVector object of all
     * StoreProfileData objects in the database.
     * @param pCon An open database connection.
     * @return new StoreProfileDataVector()
     * @throws            SQLException
     */
    public static StoreProfileDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT STORE_PROFILE_ID,STORE_ID,SHORT_DESC,DISPLAY,EDIT,OPTION_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_STORE_PROFILE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        StoreProfileDataVector v = new StoreProfileDataVector();
        StoreProfileData x = null;
        while (rs.next()) {
            // build the object
            x = StoreProfileData.createValue();
            
            x.setStoreProfileId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setDisplay(rs.getString(4));
            x.setEdit(rs.getString(5));
            x.setOptionTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * StoreProfileData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT STORE_PROFILE_ID FROM CLW_STORE_PROFILE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT STORE_PROFILE_ID FROM CLW_STORE_PROFILE");
                where = pCriteria.getSqlClause("CLW_STORE_PROFILE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STORE_PROFILE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STORE_PROFILE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STORE_PROFILE");
                where = pCriteria.getSqlClause("CLW_STORE_PROFILE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STORE_PROFILE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STORE_PROFILE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STORE_PROFILE");
                where = pCriteria.getSqlClause("CLW_STORE_PROFILE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STORE_PROFILE.equals(otherTable)){
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
     * Inserts a StoreProfileData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreProfileData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new StoreProfileData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static StoreProfileData insert(Connection pCon, StoreProfileData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_STORE_PROFILE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_STORE_PROFILE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setStoreProfileId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_STORE_PROFILE (STORE_PROFILE_ID,STORE_ID,SHORT_DESC,DISPLAY,EDIT,OPTION_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getStoreProfileId());
        pstmt.setInt(2,pData.getStoreId());
        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getDisplay());
        pstmt.setString(5,pData.getEdit());
        pstmt.setString(6,pData.getOptionTypeCd());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STORE_PROFILE_ID="+pData.getStoreProfileId());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   DISPLAY="+pData.getDisplay());
            log.debug("SQL:   EDIT="+pData.getEdit());
            log.debug("SQL:   OPTION_TYPE_CD="+pData.getOptionTypeCd());
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
        pData.setStoreProfileId(0);
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
     * Updates a StoreProfileData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreProfileData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, StoreProfileData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_STORE_PROFILE SET STORE_ID = ?,SHORT_DESC = ?,DISPLAY = ?,EDIT = ?,OPTION_TYPE_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE STORE_PROFILE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getStoreId());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getDisplay());
        pstmt.setString(i++,pData.getEdit());
        pstmt.setString(i++,pData.getOptionTypeCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getStoreProfileId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   DISPLAY="+pData.getDisplay());
            log.debug("SQL:   EDIT="+pData.getEdit());
            log.debug("SQL:   OPTION_TYPE_CD="+pData.getOptionTypeCd());
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
     * Deletes a StoreProfileData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pStoreProfileId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pStoreProfileId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_STORE_PROFILE WHERE STORE_PROFILE_ID = " + pStoreProfileId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes StoreProfileData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_STORE_PROFILE");
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
     * Inserts a StoreProfileData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreProfileData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, StoreProfileData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_STORE_PROFILE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "STORE_PROFILE_ID,STORE_ID,SHORT_DESC,DISPLAY,EDIT,OPTION_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getStoreProfileId());
        pstmt.setInt(2+4,pData.getStoreId());
        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getDisplay());
        pstmt.setString(5+4,pData.getEdit());
        pstmt.setString(6+4,pData.getOptionTypeCd());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a StoreProfileData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreProfileData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new StoreProfileData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static StoreProfileData insert(Connection pCon, StoreProfileData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a StoreProfileData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreProfileData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, StoreProfileData pData, boolean pLogFl)
        throws SQLException {
        StoreProfileData oldData = null;
        if(pLogFl) {
          int id = pData.getStoreProfileId();
          try {
          oldData = StoreProfileDataAccess.select(pCon,id);
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
     * Deletes a StoreProfileData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pStoreProfileId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pStoreProfileId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_STORE_PROFILE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_STORE_PROFILE d WHERE STORE_PROFILE_ID = " + pStoreProfileId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pStoreProfileId);
        return n;
     }

    /**
     * Deletes StoreProfileData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_STORE_PROFILE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_STORE_PROFILE d ");
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

