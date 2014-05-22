
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        UserDataAccess
 * Description:  This class is used to build access methods to the CLW_USER table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
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
 * <code>UserDataAccess</code>
 */
public class UserDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(UserDataAccess.class.getName());

    /** <code>CLW_USER</code> table name */
	/* Primary key: USER_ID */
	
    public static final String CLW_USER = "CLW_USER";
    
    /** <code>USER_ID</code> USER_ID column of table CLW_USER */
    public static final String USER_ID = "USER_ID";
    /** <code>FIRST_NAME</code> FIRST_NAME column of table CLW_USER */
    public static final String FIRST_NAME = "FIRST_NAME";
    /** <code>LAST_NAME</code> LAST_NAME column of table CLW_USER */
    public static final String LAST_NAME = "LAST_NAME";
    /** <code>USER_NAME</code> USER_NAME column of table CLW_USER */
    public static final String USER_NAME = "USER_NAME";
    /** <code>PASSWORD</code> PASSWORD column of table CLW_USER */
    public static final String PASSWORD = "PASSWORD";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_USER */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_USER */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>USER_STATUS_CD</code> USER_STATUS_CD column of table CLW_USER */
    public static final String USER_STATUS_CD = "USER_STATUS_CD";
    /** <code>USER_ROLE_CD</code> USER_ROLE_CD column of table CLW_USER */
    public static final String USER_ROLE_CD = "USER_ROLE_CD";
    /** <code>USER_TYPE_CD</code> USER_TYPE_CD column of table CLW_USER */
    public static final String USER_TYPE_CD = "USER_TYPE_CD";
    /** <code>REGISTER_DATE</code> REGISTER_DATE column of table CLW_USER */
    public static final String REGISTER_DATE = "REGISTER_DATE";
    /** <code>LAST_ACTIVITY_DATE</code> LAST_ACTIVITY_DATE column of table CLW_USER */
    public static final String LAST_ACTIVITY_DATE = "LAST_ACTIVITY_DATE";
    /** <code>PREF_LOCALE_CD</code> PREF_LOCALE_CD column of table CLW_USER */
    public static final String PREF_LOCALE_CD = "PREF_LOCALE_CD";
    /** <code>WORKFLOW_ROLE_CD</code> WORKFLOW_ROLE_CD column of table CLW_USER */
    public static final String WORKFLOW_ROLE_CD = "WORKFLOW_ROLE_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_USER */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_USER */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_USER */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_USER */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public UserDataAccess()
    {
    }

    /**
     * Gets a UserData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pUserId The key requested.
     * @return new UserData()
     * @throws            SQLException
     */
    public static UserData select(Connection pCon, int pUserId)
        throws SQLException, DataNotFoundException {
        UserData x=null;
        String sql="SELECT USER_ID,FIRST_NAME,LAST_NAME,USER_NAME,PASSWORD,EFF_DATE,EXP_DATE,USER_STATUS_CD,USER_ROLE_CD,USER_TYPE_CD,REGISTER_DATE,LAST_ACTIVITY_DATE,PREF_LOCALE_CD,WORKFLOW_ROLE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_USER WHERE USER_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pUserId=" + pUserId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pUserId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=UserData.createValue();
            
            x.setUserId(rs.getInt(1));
            x.setFirstName(rs.getString(2));
            x.setLastName(rs.getString(3));
            x.setUserName(rs.getString(4));
            x.setPassword(rs.getString(5));
            x.setEffDate(rs.getDate(6));
            x.setExpDate(rs.getDate(7));
            x.setUserStatusCd(rs.getString(8));
            x.setUserRoleCd(rs.getString(9));
            x.setUserTypeCd(rs.getString(10));
            x.setRegisterDate(rs.getDate(11));
            x.setLastActivityDate(rs.getDate(12));
            x.setPrefLocaleCd(rs.getString(13));
            x.setWorkflowRoleCd(rs.getString(14));
            x.setAddDate(rs.getTimestamp(15));
            x.setAddBy(rs.getString(16));
            x.setModDate(rs.getTimestamp(17));
            x.setModBy(rs.getString(18));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("USER_ID :" + pUserId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a UserDataVector object that consists
     * of UserData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new UserDataVector()
     * @throws            SQLException
     */
    public static UserDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a UserData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_USER.USER_ID,CLW_USER.FIRST_NAME,CLW_USER.LAST_NAME,CLW_USER.USER_NAME,CLW_USER.PASSWORD,CLW_USER.EFF_DATE,CLW_USER.EXP_DATE,CLW_USER.USER_STATUS_CD,CLW_USER.USER_ROLE_CD,CLW_USER.USER_TYPE_CD,CLW_USER.REGISTER_DATE,CLW_USER.LAST_ACTIVITY_DATE,CLW_USER.PREF_LOCALE_CD,CLW_USER.WORKFLOW_ROLE_CD,CLW_USER.ADD_DATE,CLW_USER.ADD_BY,CLW_USER.MOD_DATE,CLW_USER.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated UserData Object.
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
    *@returns a populated UserData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         UserData x = UserData.createValue();
         
         x.setUserId(rs.getInt(1+offset));
         x.setFirstName(rs.getString(2+offset));
         x.setLastName(rs.getString(3+offset));
         x.setUserName(rs.getString(4+offset));
         x.setPassword(rs.getString(5+offset));
         x.setEffDate(rs.getDate(6+offset));
         x.setExpDate(rs.getDate(7+offset));
         x.setUserStatusCd(rs.getString(8+offset));
         x.setUserRoleCd(rs.getString(9+offset));
         x.setUserTypeCd(rs.getString(10+offset));
         x.setRegisterDate(rs.getDate(11+offset));
         x.setLastActivityDate(rs.getDate(12+offset));
         x.setPrefLocaleCd(rs.getString(13+offset));
         x.setWorkflowRoleCd(rs.getString(14+offset));
         x.setAddDate(rs.getTimestamp(15+offset));
         x.setAddBy(rs.getString(16+offset));
         x.setModDate(rs.getTimestamp(17+offset));
         x.setModBy(rs.getString(18+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the UserData Object represents.
    */
    public int getColumnCount(){
        return 18;
    }

    /**
     * Gets a UserDataVector object that consists
     * of UserData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new UserDataVector()
     * @throws            SQLException
     */
    public static UserDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT USER_ID,FIRST_NAME,LAST_NAME,USER_NAME,PASSWORD,EFF_DATE,EXP_DATE,USER_STATUS_CD,USER_ROLE_CD,USER_TYPE_CD,REGISTER_DATE,LAST_ACTIVITY_DATE,PREF_LOCALE_CD,WORKFLOW_ROLE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_USER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_USER.USER_ID,CLW_USER.FIRST_NAME,CLW_USER.LAST_NAME,CLW_USER.USER_NAME,CLW_USER.PASSWORD,CLW_USER.EFF_DATE,CLW_USER.EXP_DATE,CLW_USER.USER_STATUS_CD,CLW_USER.USER_ROLE_CD,CLW_USER.USER_TYPE_CD,CLW_USER.REGISTER_DATE,CLW_USER.LAST_ACTIVITY_DATE,CLW_USER.PREF_LOCALE_CD,CLW_USER.WORKFLOW_ROLE_CD,CLW_USER.ADD_DATE,CLW_USER.ADD_BY,CLW_USER.MOD_DATE,CLW_USER.MOD_BY FROM CLW_USER");
                where = pCriteria.getSqlClause("CLW_USER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_USER.equals(otherTable)){
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
        UserDataVector v = new UserDataVector();
        while (rs.next()) {
            UserData x = UserData.createValue();
            
            x.setUserId(rs.getInt(1));
            x.setFirstName(rs.getString(2));
            x.setLastName(rs.getString(3));
            x.setUserName(rs.getString(4));
            x.setPassword(rs.getString(5));
            x.setEffDate(rs.getDate(6));
            x.setExpDate(rs.getDate(7));
            x.setUserStatusCd(rs.getString(8));
            x.setUserRoleCd(rs.getString(9));
            x.setUserTypeCd(rs.getString(10));
            x.setRegisterDate(rs.getDate(11));
            x.setLastActivityDate(rs.getDate(12));
            x.setPrefLocaleCd(rs.getString(13));
            x.setWorkflowRoleCd(rs.getString(14));
            x.setAddDate(rs.getTimestamp(15));
            x.setAddBy(rs.getString(16));
            x.setModDate(rs.getTimestamp(17));
            x.setModBy(rs.getString(18));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a UserDataVector object that consists
     * of UserData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for UserData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new UserDataVector()
     * @throws            SQLException
     */
    public static UserDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        UserDataVector v = new UserDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT USER_ID,FIRST_NAME,LAST_NAME,USER_NAME,PASSWORD,EFF_DATE,EXP_DATE,USER_STATUS_CD,USER_ROLE_CD,USER_TYPE_CD,REGISTER_DATE,LAST_ACTIVITY_DATE,PREF_LOCALE_CD,WORKFLOW_ROLE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_USER WHERE USER_ID IN (");

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
            UserData x=null;
            while (rs.next()) {
                // build the object
                x=UserData.createValue();
                
                x.setUserId(rs.getInt(1));
                x.setFirstName(rs.getString(2));
                x.setLastName(rs.getString(3));
                x.setUserName(rs.getString(4));
                x.setPassword(rs.getString(5));
                x.setEffDate(rs.getDate(6));
                x.setExpDate(rs.getDate(7));
                x.setUserStatusCd(rs.getString(8));
                x.setUserRoleCd(rs.getString(9));
                x.setUserTypeCd(rs.getString(10));
                x.setRegisterDate(rs.getDate(11));
                x.setLastActivityDate(rs.getDate(12));
                x.setPrefLocaleCd(rs.getString(13));
                x.setWorkflowRoleCd(rs.getString(14));
                x.setAddDate(rs.getTimestamp(15));
                x.setAddBy(rs.getString(16));
                x.setModDate(rs.getTimestamp(17));
                x.setModBy(rs.getString(18));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a UserDataVector object of all
     * UserData objects in the database.
     * @param pCon An open database connection.
     * @return new UserDataVector()
     * @throws            SQLException
     */
    public static UserDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT USER_ID,FIRST_NAME,LAST_NAME,USER_NAME,PASSWORD,EFF_DATE,EXP_DATE,USER_STATUS_CD,USER_ROLE_CD,USER_TYPE_CD,REGISTER_DATE,LAST_ACTIVITY_DATE,PREF_LOCALE_CD,WORKFLOW_ROLE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_USER";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        UserDataVector v = new UserDataVector();
        UserData x = null;
        while (rs.next()) {
            // build the object
            x = UserData.createValue();
            
            x.setUserId(rs.getInt(1));
            x.setFirstName(rs.getString(2));
            x.setLastName(rs.getString(3));
            x.setUserName(rs.getString(4));
            x.setPassword(rs.getString(5));
            x.setEffDate(rs.getDate(6));
            x.setExpDate(rs.getDate(7));
            x.setUserStatusCd(rs.getString(8));
            x.setUserRoleCd(rs.getString(9));
            x.setUserTypeCd(rs.getString(10));
            x.setRegisterDate(rs.getDate(11));
            x.setLastActivityDate(rs.getDate(12));
            x.setPrefLocaleCd(rs.getString(13));
            x.setWorkflowRoleCd(rs.getString(14));
            x.setAddDate(rs.getTimestamp(15));
            x.setAddBy(rs.getString(16));
            x.setModDate(rs.getTimestamp(17));
            x.setModBy(rs.getString(18));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * UserData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT USER_ID FROM CLW_USER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_USER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_USER");
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
     * Inserts a UserData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UserData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new UserData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UserData insert(Connection pCon, UserData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_USER_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_USER_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setUserId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_USER (USER_ID,FIRST_NAME,LAST_NAME,USER_NAME,PASSWORD,EFF_DATE,EXP_DATE,USER_STATUS_CD,USER_ROLE_CD,USER_TYPE_CD,REGISTER_DATE,LAST_ACTIVITY_DATE,PREF_LOCALE_CD,WORKFLOW_ROLE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getUserId());
        pstmt.setString(2,pData.getFirstName());
        pstmt.setString(3,pData.getLastName());
        pstmt.setString(4,pData.getUserName());
        pstmt.setString(5,pData.getPassword());
        pstmt.setDate(6,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(7,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(8,pData.getUserStatusCd());
        pstmt.setString(9,pData.getUserRoleCd());
        pstmt.setString(10,pData.getUserTypeCd());
        pstmt.setDate(11,DBAccess.toSQLDate(pData.getRegisterDate()));
        pstmt.setDate(12,DBAccess.toSQLDate(pData.getLastActivityDate()));
        pstmt.setString(13,pData.getPrefLocaleCd());
        pstmt.setString(14,pData.getWorkflowRoleCd());
        pstmt.setTimestamp(15,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(16,pData.getAddBy());
        pstmt.setTimestamp(17,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(18,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   FIRST_NAME="+pData.getFirstName());
            log.debug("SQL:   LAST_NAME="+pData.getLastName());
            log.debug("SQL:   USER_NAME="+pData.getUserName());
            log.debug("SQL:   PASSWORD="+pData.getPassword());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   USER_STATUS_CD="+pData.getUserStatusCd());
            log.debug("SQL:   USER_ROLE_CD="+pData.getUserRoleCd());
            log.debug("SQL:   USER_TYPE_CD="+pData.getUserTypeCd());
            log.debug("SQL:   REGISTER_DATE="+pData.getRegisterDate());
            log.debug("SQL:   LAST_ACTIVITY_DATE="+pData.getLastActivityDate());
            log.debug("SQL:   PREF_LOCALE_CD="+pData.getPrefLocaleCd());
            log.debug("SQL:   WORKFLOW_ROLE_CD="+pData.getWorkflowRoleCd());
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
        pData.setUserId(0);
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
     * Updates a UserData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UserData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UserData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_USER SET FIRST_NAME = ?,LAST_NAME = ?,USER_NAME = ?,PASSWORD = ?,EFF_DATE = ?,EXP_DATE = ?,USER_STATUS_CD = ?,USER_ROLE_CD = ?,USER_TYPE_CD = ?,REGISTER_DATE = ?,LAST_ACTIVITY_DATE = ?,PREF_LOCALE_CD = ?,WORKFLOW_ROLE_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE USER_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getFirstName());
        pstmt.setString(i++,pData.getLastName());
        pstmt.setString(i++,pData.getUserName());
        pstmt.setString(i++,pData.getPassword());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(i++,pData.getUserStatusCd());
        pstmt.setString(i++,pData.getUserRoleCd());
        pstmt.setString(i++,pData.getUserTypeCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getRegisterDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getLastActivityDate()));
        pstmt.setString(i++,pData.getPrefLocaleCd());
        pstmt.setString(i++,pData.getWorkflowRoleCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getUserId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FIRST_NAME="+pData.getFirstName());
            log.debug("SQL:   LAST_NAME="+pData.getLastName());
            log.debug("SQL:   USER_NAME="+pData.getUserName());
            log.debug("SQL:   PASSWORD="+pData.getPassword());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   USER_STATUS_CD="+pData.getUserStatusCd());
            log.debug("SQL:   USER_ROLE_CD="+pData.getUserRoleCd());
            log.debug("SQL:   USER_TYPE_CD="+pData.getUserTypeCd());
            log.debug("SQL:   REGISTER_DATE="+pData.getRegisterDate());
            log.debug("SQL:   LAST_ACTIVITY_DATE="+pData.getLastActivityDate());
            log.debug("SQL:   PREF_LOCALE_CD="+pData.getPrefLocaleCd());
            log.debug("SQL:   WORKFLOW_ROLE_CD="+pData.getWorkflowRoleCd());
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
     * Deletes a UserData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUserId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUserId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_USER WHERE USER_ID = " + pUserId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes UserData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_USER");
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
     * Inserts a UserData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UserData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, UserData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_USER (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "USER_ID,FIRST_NAME,LAST_NAME,USER_NAME,PASSWORD,EFF_DATE,EXP_DATE,USER_STATUS_CD,USER_ROLE_CD,USER_TYPE_CD,REGISTER_DATE,LAST_ACTIVITY_DATE,PREF_LOCALE_CD,WORKFLOW_ROLE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getUserId());
        pstmt.setString(2+4,pData.getFirstName());
        pstmt.setString(3+4,pData.getLastName());
        pstmt.setString(4+4,pData.getUserName());
        pstmt.setString(5+4,pData.getPassword());
        pstmt.setDate(6+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(7+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(8+4,pData.getUserStatusCd());
        pstmt.setString(9+4,pData.getUserRoleCd());
        pstmt.setString(10+4,pData.getUserTypeCd());
        pstmt.setDate(11+4,DBAccess.toSQLDate(pData.getRegisterDate()));
        pstmt.setDate(12+4,DBAccess.toSQLDate(pData.getLastActivityDate()));
        pstmt.setString(13+4,pData.getPrefLocaleCd());
        pstmt.setString(14+4,pData.getWorkflowRoleCd());
        pstmt.setTimestamp(15+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(16+4,pData.getAddBy());
        pstmt.setTimestamp(17+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(18+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a UserData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UserData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new UserData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UserData insert(Connection pCon, UserData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a UserData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UserData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UserData pData, boolean pLogFl)
        throws SQLException {
        UserData oldData = null;
        if(pLogFl) {
          int id = pData.getUserId();
          try {
          oldData = UserDataAccess.select(pCon,id);
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
     * Deletes a UserData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUserId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUserId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_USER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_USER d WHERE USER_ID = " + pUserId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pUserId);
        return n;
     }

    /**
     * Deletes UserData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_USER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_USER d ");
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

