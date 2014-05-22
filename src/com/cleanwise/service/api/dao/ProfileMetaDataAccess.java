
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ProfileMetaDataAccess
 * Description:  This class is used to build access methods to the CLW_PROFILE_META table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ProfileMetaData;
import com.cleanwise.service.api.value.ProfileMetaDataVector;
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
 * <code>ProfileMetaDataAccess</code>
 */
public class ProfileMetaDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ProfileMetaDataAccess.class.getName());

    /** <code>CLW_PROFILE_META</code> table name */
	/* Primary key: PROFILE_META_ID */
	
    public static final String CLW_PROFILE_META = "CLW_PROFILE_META";
    
    /** <code>PROFILE_META_ID</code> PROFILE_META_ID column of table CLW_PROFILE_META */
    public static final String PROFILE_META_ID = "PROFILE_META_ID";
    /** <code>PROFILE_ID</code> PROFILE_ID column of table CLW_PROFILE_META */
    public static final String PROFILE_ID = "PROFILE_ID";
    /** <code>PROFILE_META_TYPE_CD</code> PROFILE_META_TYPE_CD column of table CLW_PROFILE_META */
    public static final String PROFILE_META_TYPE_CD = "PROFILE_META_TYPE_CD";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_PROFILE_META */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>HELP_TEXT</code> HELP_TEXT column of table CLW_PROFILE_META */
    public static final String HELP_TEXT = "HELP_TEXT";
    /** <code>PROFILE_META_STATUS_CD</code> PROFILE_META_STATUS_CD column of table CLW_PROFILE_META */
    public static final String PROFILE_META_STATUS_CD = "PROFILE_META_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PROFILE_META */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PROFILE_META */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PROFILE_META */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PROFILE_META */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ProfileMetaDataAccess()
    {
    }

    /**
     * Gets a ProfileMetaData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pProfileMetaId The key requested.
     * @return new ProfileMetaData()
     * @throws            SQLException
     */
    public static ProfileMetaData select(Connection pCon, int pProfileMetaId)
        throws SQLException, DataNotFoundException {
        ProfileMetaData x=null;
        String sql="SELECT PROFILE_META_ID,PROFILE_ID,PROFILE_META_TYPE_CD,CLW_VALUE,HELP_TEXT,PROFILE_META_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PROFILE_META WHERE PROFILE_META_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pProfileMetaId=" + pProfileMetaId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pProfileMetaId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ProfileMetaData.createValue();
            
            x.setProfileMetaId(rs.getInt(1));
            x.setProfileId(rs.getInt(2));
            x.setProfileMetaTypeCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setHelpText(rs.getString(5));
            x.setProfileMetaStatusCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PROFILE_META_ID :" + pProfileMetaId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ProfileMetaDataVector object that consists
     * of ProfileMetaData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ProfileMetaDataVector()
     * @throws            SQLException
     */
    public static ProfileMetaDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ProfileMetaData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PROFILE_META.PROFILE_META_ID,CLW_PROFILE_META.PROFILE_ID,CLW_PROFILE_META.PROFILE_META_TYPE_CD,CLW_PROFILE_META.CLW_VALUE,CLW_PROFILE_META.HELP_TEXT,CLW_PROFILE_META.PROFILE_META_STATUS_CD,CLW_PROFILE_META.ADD_DATE,CLW_PROFILE_META.ADD_BY,CLW_PROFILE_META.MOD_DATE,CLW_PROFILE_META.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ProfileMetaData Object.
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
    *@returns a populated ProfileMetaData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ProfileMetaData x = ProfileMetaData.createValue();
         
         x.setProfileMetaId(rs.getInt(1+offset));
         x.setProfileId(rs.getInt(2+offset));
         x.setProfileMetaTypeCd(rs.getString(3+offset));
         x.setValue(rs.getString(4+offset));
         x.setHelpText(rs.getString(5+offset));
         x.setProfileMetaStatusCd(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ProfileMetaData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a ProfileMetaDataVector object that consists
     * of ProfileMetaData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ProfileMetaDataVector()
     * @throws            SQLException
     */
    public static ProfileMetaDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PROFILE_META_ID,PROFILE_ID,PROFILE_META_TYPE_CD,CLW_VALUE,HELP_TEXT,PROFILE_META_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PROFILE_META");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PROFILE_META.PROFILE_META_ID,CLW_PROFILE_META.PROFILE_ID,CLW_PROFILE_META.PROFILE_META_TYPE_CD,CLW_PROFILE_META.CLW_VALUE,CLW_PROFILE_META.HELP_TEXT,CLW_PROFILE_META.PROFILE_META_STATUS_CD,CLW_PROFILE_META.ADD_DATE,CLW_PROFILE_META.ADD_BY,CLW_PROFILE_META.MOD_DATE,CLW_PROFILE_META.MOD_BY FROM CLW_PROFILE_META");
                where = pCriteria.getSqlClause("CLW_PROFILE_META");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PROFILE_META.equals(otherTable)){
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
        ProfileMetaDataVector v = new ProfileMetaDataVector();
        while (rs.next()) {
            ProfileMetaData x = ProfileMetaData.createValue();
            
            x.setProfileMetaId(rs.getInt(1));
            x.setProfileId(rs.getInt(2));
            x.setProfileMetaTypeCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setHelpText(rs.getString(5));
            x.setProfileMetaStatusCd(rs.getString(6));
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
     * Gets a ProfileMetaDataVector object that consists
     * of ProfileMetaData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ProfileMetaData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ProfileMetaDataVector()
     * @throws            SQLException
     */
    public static ProfileMetaDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ProfileMetaDataVector v = new ProfileMetaDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PROFILE_META_ID,PROFILE_ID,PROFILE_META_TYPE_CD,CLW_VALUE,HELP_TEXT,PROFILE_META_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PROFILE_META WHERE PROFILE_META_ID IN (");

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
            ProfileMetaData x=null;
            while (rs.next()) {
                // build the object
                x=ProfileMetaData.createValue();
                
                x.setProfileMetaId(rs.getInt(1));
                x.setProfileId(rs.getInt(2));
                x.setProfileMetaTypeCd(rs.getString(3));
                x.setValue(rs.getString(4));
                x.setHelpText(rs.getString(5));
                x.setProfileMetaStatusCd(rs.getString(6));
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
     * Gets a ProfileMetaDataVector object of all
     * ProfileMetaData objects in the database.
     * @param pCon An open database connection.
     * @return new ProfileMetaDataVector()
     * @throws            SQLException
     */
    public static ProfileMetaDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PROFILE_META_ID,PROFILE_ID,PROFILE_META_TYPE_CD,CLW_VALUE,HELP_TEXT,PROFILE_META_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PROFILE_META";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ProfileMetaDataVector v = new ProfileMetaDataVector();
        ProfileMetaData x = null;
        while (rs.next()) {
            // build the object
            x = ProfileMetaData.createValue();
            
            x.setProfileMetaId(rs.getInt(1));
            x.setProfileId(rs.getInt(2));
            x.setProfileMetaTypeCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setHelpText(rs.getString(5));
            x.setProfileMetaStatusCd(rs.getString(6));
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
     * ProfileMetaData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PROFILE_META_ID FROM CLW_PROFILE_META");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PROFILE_META");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PROFILE_META");
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
     * Inserts a ProfileMetaData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileMetaData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ProfileMetaData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProfileMetaData insert(Connection pCon, ProfileMetaData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PROFILE_META_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PROFILE_META_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setProfileMetaId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PROFILE_META (PROFILE_META_ID,PROFILE_ID,PROFILE_META_TYPE_CD,CLW_VALUE,HELP_TEXT,PROFILE_META_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getProfileMetaId());
        pstmt.setInt(2,pData.getProfileId());
        pstmt.setString(3,pData.getProfileMetaTypeCd());
        pstmt.setString(4,pData.getValue());
        pstmt.setString(5,pData.getHelpText());
        pstmt.setString(6,pData.getProfileMetaStatusCd());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PROFILE_META_ID="+pData.getProfileMetaId());
            log.debug("SQL:   PROFILE_ID="+pData.getProfileId());
            log.debug("SQL:   PROFILE_META_TYPE_CD="+pData.getProfileMetaTypeCd());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   HELP_TEXT="+pData.getHelpText());
            log.debug("SQL:   PROFILE_META_STATUS_CD="+pData.getProfileMetaStatusCd());
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
        pData.setProfileMetaId(0);
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
     * Updates a ProfileMetaData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileMetaData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProfileMetaData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PROFILE_META SET PROFILE_ID = ?,PROFILE_META_TYPE_CD = ?,CLW_VALUE = ?,HELP_TEXT = ?,PROFILE_META_STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE PROFILE_META_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getProfileId());
        pstmt.setString(i++,pData.getProfileMetaTypeCd());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getHelpText());
        pstmt.setString(i++,pData.getProfileMetaStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getProfileMetaId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PROFILE_ID="+pData.getProfileId());
            log.debug("SQL:   PROFILE_META_TYPE_CD="+pData.getProfileMetaTypeCd());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   HELP_TEXT="+pData.getHelpText());
            log.debug("SQL:   PROFILE_META_STATUS_CD="+pData.getProfileMetaStatusCd());
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
     * Deletes a ProfileMetaData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProfileMetaId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProfileMetaId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PROFILE_META WHERE PROFILE_META_ID = " + pProfileMetaId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ProfileMetaData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PROFILE_META");
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
     * Inserts a ProfileMetaData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileMetaData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ProfileMetaData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PROFILE_META (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PROFILE_META_ID,PROFILE_ID,PROFILE_META_TYPE_CD,CLW_VALUE,HELP_TEXT,PROFILE_META_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getProfileMetaId());
        pstmt.setInt(2+4,pData.getProfileId());
        pstmt.setString(3+4,pData.getProfileMetaTypeCd());
        pstmt.setString(4+4,pData.getValue());
        pstmt.setString(5+4,pData.getHelpText());
        pstmt.setString(6+4,pData.getProfileMetaStatusCd());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ProfileMetaData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileMetaData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ProfileMetaData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProfileMetaData insert(Connection pCon, ProfileMetaData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ProfileMetaData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileMetaData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProfileMetaData pData, boolean pLogFl)
        throws SQLException {
        ProfileMetaData oldData = null;
        if(pLogFl) {
          int id = pData.getProfileMetaId();
          try {
          oldData = ProfileMetaDataAccess.select(pCon,id);
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
     * Deletes a ProfileMetaData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProfileMetaId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProfileMetaId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PROFILE_META SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PROFILE_META d WHERE PROFILE_META_ID = " + pProfileMetaId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pProfileMetaId);
        return n;
     }

    /**
     * Deletes ProfileMetaData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PROFILE_META SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PROFILE_META d ");
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

