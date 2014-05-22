
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ProfileDataAccess
 * Description:  This class is used to build access methods to the CLW_PROFILE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ProfileData;
import com.cleanwise.service.api.value.ProfileDataVector;
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
 * <code>ProfileDataAccess</code>
 */
public class ProfileDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ProfileDataAccess.class.getName());

    /** <code>CLW_PROFILE</code> table name */
	/* Primary key: PROFILE_ID */
	
    public static final String CLW_PROFILE = "CLW_PROFILE";
    
    /** <code>PROFILE_ID</code> PROFILE_ID column of table CLW_PROFILE */
    public static final String PROFILE_ID = "PROFILE_ID";
    /** <code>PROFILE_ORDER</code> PROFILE_ORDER column of table CLW_PROFILE */
    public static final String PROFILE_ORDER = "PROFILE_ORDER";
    /** <code>PROFILE_TYPE_CD</code> PROFILE_TYPE_CD column of table CLW_PROFILE */
    public static final String PROFILE_TYPE_CD = "PROFILE_TYPE_CD";
    /** <code>PROFILE_STATUS_CD</code> PROFILE_STATUS_CD column of table CLW_PROFILE */
    public static final String PROFILE_STATUS_CD = "PROFILE_STATUS_CD";
    /** <code>PROFILE_QUESTION_TYPE_CD</code> PROFILE_QUESTION_TYPE_CD column of table CLW_PROFILE */
    public static final String PROFILE_QUESTION_TYPE_CD = "PROFILE_QUESTION_TYPE_CD";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_PROFILE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>HELP_TEXT</code> HELP_TEXT column of table CLW_PROFILE */
    public static final String HELP_TEXT = "HELP_TEXT";
    /** <code>READ_ONLY</code> READ_ONLY column of table CLW_PROFILE */
    public static final String READ_ONLY = "READ_ONLY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PROFILE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PROFILE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PROFILE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PROFILE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>IMAGE</code> IMAGE column of table CLW_PROFILE */
    public static final String IMAGE = "IMAGE";

    /**
     * Constructor.
     */
    public ProfileDataAccess()
    {
    }

    /**
     * Gets a ProfileData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pProfileId The key requested.
     * @return new ProfileData()
     * @throws            SQLException
     */
    public static ProfileData select(Connection pCon, int pProfileId)
        throws SQLException, DataNotFoundException {
        ProfileData x=null;
        String sql="SELECT PROFILE_ID,PROFILE_ORDER,PROFILE_TYPE_CD,PROFILE_STATUS_CD,PROFILE_QUESTION_TYPE_CD,SHORT_DESC,HELP_TEXT,READ_ONLY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,IMAGE FROM CLW_PROFILE WHERE PROFILE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pProfileId=" + pProfileId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pProfileId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ProfileData.createValue();
            
            x.setProfileId(rs.getInt(1));
            x.setProfileOrder(rs.getInt(2));
            x.setProfileTypeCd(rs.getString(3));
            x.setProfileStatusCd(rs.getString(4));
            x.setProfileQuestionTypeCd(rs.getString(5));
            x.setShortDesc(rs.getString(6));
            x.setHelpText(rs.getString(7));
            x.setReadOnly(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setImage(rs.getString(13));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PROFILE_ID :" + pProfileId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ProfileDataVector object that consists
     * of ProfileData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ProfileDataVector()
     * @throws            SQLException
     */
    public static ProfileDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ProfileData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PROFILE.PROFILE_ID,CLW_PROFILE.PROFILE_ORDER,CLW_PROFILE.PROFILE_TYPE_CD,CLW_PROFILE.PROFILE_STATUS_CD,CLW_PROFILE.PROFILE_QUESTION_TYPE_CD,CLW_PROFILE.SHORT_DESC,CLW_PROFILE.HELP_TEXT,CLW_PROFILE.READ_ONLY,CLW_PROFILE.ADD_DATE,CLW_PROFILE.ADD_BY,CLW_PROFILE.MOD_DATE,CLW_PROFILE.MOD_BY,CLW_PROFILE.IMAGE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ProfileData Object.
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
    *@returns a populated ProfileData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ProfileData x = ProfileData.createValue();
         
         x.setProfileId(rs.getInt(1+offset));
         x.setProfileOrder(rs.getInt(2+offset));
         x.setProfileTypeCd(rs.getString(3+offset));
         x.setProfileStatusCd(rs.getString(4+offset));
         x.setProfileQuestionTypeCd(rs.getString(5+offset));
         x.setShortDesc(rs.getString(6+offset));
         x.setHelpText(rs.getString(7+offset));
         x.setReadOnly(rs.getString(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         x.setImage(rs.getString(13+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ProfileData Object represents.
    */
    public int getColumnCount(){
        return 13;
    }

    /**
     * Gets a ProfileDataVector object that consists
     * of ProfileData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ProfileDataVector()
     * @throws            SQLException
     */
    public static ProfileDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PROFILE_ID,PROFILE_ORDER,PROFILE_TYPE_CD,PROFILE_STATUS_CD,PROFILE_QUESTION_TYPE_CD,SHORT_DESC,HELP_TEXT,READ_ONLY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,IMAGE FROM CLW_PROFILE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PROFILE.PROFILE_ID,CLW_PROFILE.PROFILE_ORDER,CLW_PROFILE.PROFILE_TYPE_CD,CLW_PROFILE.PROFILE_STATUS_CD,CLW_PROFILE.PROFILE_QUESTION_TYPE_CD,CLW_PROFILE.SHORT_DESC,CLW_PROFILE.HELP_TEXT,CLW_PROFILE.READ_ONLY,CLW_PROFILE.ADD_DATE,CLW_PROFILE.ADD_BY,CLW_PROFILE.MOD_DATE,CLW_PROFILE.MOD_BY,CLW_PROFILE.IMAGE FROM CLW_PROFILE");
                where = pCriteria.getSqlClause("CLW_PROFILE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PROFILE.equals(otherTable)){
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
        ProfileDataVector v = new ProfileDataVector();
        while (rs.next()) {
            ProfileData x = ProfileData.createValue();
            
            x.setProfileId(rs.getInt(1));
            x.setProfileOrder(rs.getInt(2));
            x.setProfileTypeCd(rs.getString(3));
            x.setProfileStatusCd(rs.getString(4));
            x.setProfileQuestionTypeCd(rs.getString(5));
            x.setShortDesc(rs.getString(6));
            x.setHelpText(rs.getString(7));
            x.setReadOnly(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setImage(rs.getString(13));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ProfileDataVector object that consists
     * of ProfileData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ProfileData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ProfileDataVector()
     * @throws            SQLException
     */
    public static ProfileDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ProfileDataVector v = new ProfileDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PROFILE_ID,PROFILE_ORDER,PROFILE_TYPE_CD,PROFILE_STATUS_CD,PROFILE_QUESTION_TYPE_CD,SHORT_DESC,HELP_TEXT,READ_ONLY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,IMAGE FROM CLW_PROFILE WHERE PROFILE_ID IN (");

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
            ProfileData x=null;
            while (rs.next()) {
                // build the object
                x=ProfileData.createValue();
                
                x.setProfileId(rs.getInt(1));
                x.setProfileOrder(rs.getInt(2));
                x.setProfileTypeCd(rs.getString(3));
                x.setProfileStatusCd(rs.getString(4));
                x.setProfileQuestionTypeCd(rs.getString(5));
                x.setShortDesc(rs.getString(6));
                x.setHelpText(rs.getString(7));
                x.setReadOnly(rs.getString(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setAddBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                x.setImage(rs.getString(13));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ProfileDataVector object of all
     * ProfileData objects in the database.
     * @param pCon An open database connection.
     * @return new ProfileDataVector()
     * @throws            SQLException
     */
    public static ProfileDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PROFILE_ID,PROFILE_ORDER,PROFILE_TYPE_CD,PROFILE_STATUS_CD,PROFILE_QUESTION_TYPE_CD,SHORT_DESC,HELP_TEXT,READ_ONLY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,IMAGE FROM CLW_PROFILE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ProfileDataVector v = new ProfileDataVector();
        ProfileData x = null;
        while (rs.next()) {
            // build the object
            x = ProfileData.createValue();
            
            x.setProfileId(rs.getInt(1));
            x.setProfileOrder(rs.getInt(2));
            x.setProfileTypeCd(rs.getString(3));
            x.setProfileStatusCd(rs.getString(4));
            x.setProfileQuestionTypeCd(rs.getString(5));
            x.setShortDesc(rs.getString(6));
            x.setHelpText(rs.getString(7));
            x.setReadOnly(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setImage(rs.getString(13));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ProfileData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PROFILE_ID FROM CLW_PROFILE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PROFILE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PROFILE");
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
     * Inserts a ProfileData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ProfileData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProfileData insert(Connection pCon, ProfileData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PROFILE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PROFILE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setProfileId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PROFILE (PROFILE_ID,PROFILE_ORDER,PROFILE_TYPE_CD,PROFILE_STATUS_CD,PROFILE_QUESTION_TYPE_CD,SHORT_DESC,HELP_TEXT,READ_ONLY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,IMAGE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getProfileId());
        pstmt.setInt(2,pData.getProfileOrder());
        pstmt.setString(3,pData.getProfileTypeCd());
        pstmt.setString(4,pData.getProfileStatusCd());
        pstmt.setString(5,pData.getProfileQuestionTypeCd());
        pstmt.setString(6,pData.getShortDesc());
        pstmt.setString(7,pData.getHelpText());
        pstmt.setString(8,pData.getReadOnly());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());
        pstmt.setString(13,pData.getImage());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PROFILE_ID="+pData.getProfileId());
            log.debug("SQL:   PROFILE_ORDER="+pData.getProfileOrder());
            log.debug("SQL:   PROFILE_TYPE_CD="+pData.getProfileTypeCd());
            log.debug("SQL:   PROFILE_STATUS_CD="+pData.getProfileStatusCd());
            log.debug("SQL:   PROFILE_QUESTION_TYPE_CD="+pData.getProfileQuestionTypeCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   HELP_TEXT="+pData.getHelpText());
            log.debug("SQL:   READ_ONLY="+pData.getReadOnly());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   IMAGE="+pData.getImage());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setProfileId(0);
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
     * Updates a ProfileData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProfileData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PROFILE SET PROFILE_ORDER = ?,PROFILE_TYPE_CD = ?,PROFILE_STATUS_CD = ?,PROFILE_QUESTION_TYPE_CD = ?,SHORT_DESC = ?,HELP_TEXT = ?,READ_ONLY = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,IMAGE = ? WHERE PROFILE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getProfileOrder());
        pstmt.setString(i++,pData.getProfileTypeCd());
        pstmt.setString(i++,pData.getProfileStatusCd());
        pstmt.setString(i++,pData.getProfileQuestionTypeCd());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getHelpText());
        pstmt.setString(i++,pData.getReadOnly());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getImage());
        pstmt.setInt(i++,pData.getProfileId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PROFILE_ORDER="+pData.getProfileOrder());
            log.debug("SQL:   PROFILE_TYPE_CD="+pData.getProfileTypeCd());
            log.debug("SQL:   PROFILE_STATUS_CD="+pData.getProfileStatusCd());
            log.debug("SQL:   PROFILE_QUESTION_TYPE_CD="+pData.getProfileQuestionTypeCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   HELP_TEXT="+pData.getHelpText());
            log.debug("SQL:   READ_ONLY="+pData.getReadOnly());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   IMAGE="+pData.getImage());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ProfileData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProfileId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProfileId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PROFILE WHERE PROFILE_ID = " + pProfileId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ProfileData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PROFILE");
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
     * Inserts a ProfileData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ProfileData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PROFILE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PROFILE_ID,PROFILE_ORDER,PROFILE_TYPE_CD,PROFILE_STATUS_CD,PROFILE_QUESTION_TYPE_CD,SHORT_DESC,HELP_TEXT,READ_ONLY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,IMAGE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getProfileId());
        pstmt.setInt(2+4,pData.getProfileOrder());
        pstmt.setString(3+4,pData.getProfileTypeCd());
        pstmt.setString(4+4,pData.getProfileStatusCd());
        pstmt.setString(5+4,pData.getProfileQuestionTypeCd());
        pstmt.setString(6+4,pData.getShortDesc());
        pstmt.setString(7+4,pData.getHelpText());
        pstmt.setString(8+4,pData.getReadOnly());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());
        pstmt.setString(13+4,pData.getImage());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ProfileData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ProfileData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProfileData insert(Connection pCon, ProfileData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ProfileData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProfileData pData, boolean pLogFl)
        throws SQLException {
        ProfileData oldData = null;
        if(pLogFl) {
          int id = pData.getProfileId();
          try {
          oldData = ProfileDataAccess.select(pCon,id);
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
     * Deletes a ProfileData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProfileId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProfileId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PROFILE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PROFILE d WHERE PROFILE_ID = " + pProfileId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pProfileId);
        return n;
     }

    /**
     * Deletes ProfileData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PROFILE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PROFILE d ");
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

