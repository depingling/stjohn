
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ProfileDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_PROFILE_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ProfileDetailData;
import com.cleanwise.service.api.value.ProfileDetailDataVector;
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
 * <code>ProfileDetailDataAccess</code>
 */
public class ProfileDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ProfileDetailDataAccess.class.getName());

    /** <code>CLW_PROFILE_DETAIL</code> table name */
	/* Primary key: PROFILE_DETAIL_ID */
	
    public static final String CLW_PROFILE_DETAIL = "CLW_PROFILE_DETAIL";
    
    /** <code>PROFILE_DETAIL_ID</code> PROFILE_DETAIL_ID column of table CLW_PROFILE_DETAIL */
    public static final String PROFILE_DETAIL_ID = "PROFILE_DETAIL_ID";
    /** <code>PROFILE_ID</code> PROFILE_ID column of table CLW_PROFILE_DETAIL */
    public static final String PROFILE_ID = "PROFILE_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_PROFILE_DETAIL */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>PROFILE_DETAIL_STATUS_CD</code> PROFILE_DETAIL_STATUS_CD column of table CLW_PROFILE_DETAIL */
    public static final String PROFILE_DETAIL_STATUS_CD = "PROFILE_DETAIL_STATUS_CD";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_PROFILE_DETAIL */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_PROFILE_DETAIL */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PROFILE_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PROFILE_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PROFILE_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PROFILE_DETAIL */
    public static final String MOD_BY = "MOD_BY";
    /** <code>PROFILE_DETAIL_PARENT_ID</code> PROFILE_DETAIL_PARENT_ID column of table CLW_PROFILE_DETAIL */
    public static final String PROFILE_DETAIL_PARENT_ID = "PROFILE_DETAIL_PARENT_ID";
    /** <code>LOOP_VALUE</code> LOOP_VALUE column of table CLW_PROFILE_DETAIL */
    public static final String LOOP_VALUE = "LOOP_VALUE";

    /**
     * Constructor.
     */
    public ProfileDetailDataAccess()
    {
    }

    /**
     * Gets a ProfileDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pProfileDetailId The key requested.
     * @return new ProfileDetailData()
     * @throws            SQLException
     */
    public static ProfileDetailData select(Connection pCon, int pProfileDetailId)
        throws SQLException, DataNotFoundException {
        ProfileDetailData x=null;
        String sql="SELECT PROFILE_DETAIL_ID,PROFILE_ID,BUS_ENTITY_ID,PROFILE_DETAIL_STATUS_CD,SHORT_DESC,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PROFILE_DETAIL_PARENT_ID,LOOP_VALUE FROM CLW_PROFILE_DETAIL WHERE PROFILE_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pProfileDetailId=" + pProfileDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pProfileDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ProfileDetailData.createValue();
            
            x.setProfileDetailId(rs.getInt(1));
            x.setProfileId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setProfileDetailStatusCd(rs.getString(4));
            x.setShortDesc(rs.getString(5));
            x.setValue(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setProfileDetailParentId(rs.getInt(11));
            x.setLoopValue(rs.getInt(12));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PROFILE_DETAIL_ID :" + pProfileDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ProfileDetailDataVector object that consists
     * of ProfileDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ProfileDetailDataVector()
     * @throws            SQLException
     */
    public static ProfileDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ProfileDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PROFILE_DETAIL.PROFILE_DETAIL_ID,CLW_PROFILE_DETAIL.PROFILE_ID,CLW_PROFILE_DETAIL.BUS_ENTITY_ID,CLW_PROFILE_DETAIL.PROFILE_DETAIL_STATUS_CD,CLW_PROFILE_DETAIL.SHORT_DESC,CLW_PROFILE_DETAIL.CLW_VALUE,CLW_PROFILE_DETAIL.ADD_DATE,CLW_PROFILE_DETAIL.ADD_BY,CLW_PROFILE_DETAIL.MOD_DATE,CLW_PROFILE_DETAIL.MOD_BY,CLW_PROFILE_DETAIL.PROFILE_DETAIL_PARENT_ID,CLW_PROFILE_DETAIL.LOOP_VALUE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ProfileDetailData Object.
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
    *@returns a populated ProfileDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ProfileDetailData x = ProfileDetailData.createValue();
         
         x.setProfileDetailId(rs.getInt(1+offset));
         x.setProfileId(rs.getInt(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setProfileDetailStatusCd(rs.getString(4+offset));
         x.setShortDesc(rs.getString(5+offset));
         x.setValue(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         x.setProfileDetailParentId(rs.getInt(11+offset));
         x.setLoopValue(rs.getInt(12+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ProfileDetailData Object represents.
    */
    public int getColumnCount(){
        return 12;
    }

    /**
     * Gets a ProfileDetailDataVector object that consists
     * of ProfileDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ProfileDetailDataVector()
     * @throws            SQLException
     */
    public static ProfileDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PROFILE_DETAIL_ID,PROFILE_ID,BUS_ENTITY_ID,PROFILE_DETAIL_STATUS_CD,SHORT_DESC,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PROFILE_DETAIL_PARENT_ID,LOOP_VALUE FROM CLW_PROFILE_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PROFILE_DETAIL.PROFILE_DETAIL_ID,CLW_PROFILE_DETAIL.PROFILE_ID,CLW_PROFILE_DETAIL.BUS_ENTITY_ID,CLW_PROFILE_DETAIL.PROFILE_DETAIL_STATUS_CD,CLW_PROFILE_DETAIL.SHORT_DESC,CLW_PROFILE_DETAIL.CLW_VALUE,CLW_PROFILE_DETAIL.ADD_DATE,CLW_PROFILE_DETAIL.ADD_BY,CLW_PROFILE_DETAIL.MOD_DATE,CLW_PROFILE_DETAIL.MOD_BY,CLW_PROFILE_DETAIL.PROFILE_DETAIL_PARENT_ID,CLW_PROFILE_DETAIL.LOOP_VALUE FROM CLW_PROFILE_DETAIL");
                where = pCriteria.getSqlClause("CLW_PROFILE_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PROFILE_DETAIL.equals(otherTable)){
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
        ProfileDetailDataVector v = new ProfileDetailDataVector();
        while (rs.next()) {
            ProfileDetailData x = ProfileDetailData.createValue();
            
            x.setProfileDetailId(rs.getInt(1));
            x.setProfileId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setProfileDetailStatusCd(rs.getString(4));
            x.setShortDesc(rs.getString(5));
            x.setValue(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setProfileDetailParentId(rs.getInt(11));
            x.setLoopValue(rs.getInt(12));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ProfileDetailDataVector object that consists
     * of ProfileDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ProfileDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ProfileDetailDataVector()
     * @throws            SQLException
     */
    public static ProfileDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ProfileDetailDataVector v = new ProfileDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PROFILE_DETAIL_ID,PROFILE_ID,BUS_ENTITY_ID,PROFILE_DETAIL_STATUS_CD,SHORT_DESC,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PROFILE_DETAIL_PARENT_ID,LOOP_VALUE FROM CLW_PROFILE_DETAIL WHERE PROFILE_DETAIL_ID IN (");

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
            ProfileDetailData x=null;
            while (rs.next()) {
                // build the object
                x=ProfileDetailData.createValue();
                
                x.setProfileDetailId(rs.getInt(1));
                x.setProfileId(rs.getInt(2));
                x.setBusEntityId(rs.getInt(3));
                x.setProfileDetailStatusCd(rs.getString(4));
                x.setShortDesc(rs.getString(5));
                x.setValue(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                x.setProfileDetailParentId(rs.getInt(11));
                x.setLoopValue(rs.getInt(12));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ProfileDetailDataVector object of all
     * ProfileDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new ProfileDetailDataVector()
     * @throws            SQLException
     */
    public static ProfileDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PROFILE_DETAIL_ID,PROFILE_ID,BUS_ENTITY_ID,PROFILE_DETAIL_STATUS_CD,SHORT_DESC,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PROFILE_DETAIL_PARENT_ID,LOOP_VALUE FROM CLW_PROFILE_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ProfileDetailDataVector v = new ProfileDetailDataVector();
        ProfileDetailData x = null;
        while (rs.next()) {
            // build the object
            x = ProfileDetailData.createValue();
            
            x.setProfileDetailId(rs.getInt(1));
            x.setProfileId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setProfileDetailStatusCd(rs.getString(4));
            x.setShortDesc(rs.getString(5));
            x.setValue(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setProfileDetailParentId(rs.getInt(11));
            x.setLoopValue(rs.getInt(12));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ProfileDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PROFILE_DETAIL_ID FROM CLW_PROFILE_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PROFILE_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PROFILE_DETAIL");
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
     * Inserts a ProfileDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ProfileDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProfileDetailData insert(Connection pCon, ProfileDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PROFILE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PROFILE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setProfileDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PROFILE_DETAIL (PROFILE_DETAIL_ID,PROFILE_ID,BUS_ENTITY_ID,PROFILE_DETAIL_STATUS_CD,SHORT_DESC,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PROFILE_DETAIL_PARENT_ID,LOOP_VALUE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getProfileDetailId());
        pstmt.setInt(2,pData.getProfileId());
        pstmt.setInt(3,pData.getBusEntityId());
        pstmt.setString(4,pData.getProfileDetailStatusCd());
        pstmt.setString(5,pData.getShortDesc());
        pstmt.setString(6,pData.getValue());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());
        pstmt.setInt(11,pData.getProfileDetailParentId());
        pstmt.setInt(12,pData.getLoopValue());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PROFILE_DETAIL_ID="+pData.getProfileDetailId());
            log.debug("SQL:   PROFILE_ID="+pData.getProfileId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   PROFILE_DETAIL_STATUS_CD="+pData.getProfileDetailStatusCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PROFILE_DETAIL_PARENT_ID="+pData.getProfileDetailParentId());
            log.debug("SQL:   LOOP_VALUE="+pData.getLoopValue());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setProfileDetailId(0);
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
     * Updates a ProfileDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProfileDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PROFILE_DETAIL SET PROFILE_ID = ?,BUS_ENTITY_ID = ?,PROFILE_DETAIL_STATUS_CD = ?,SHORT_DESC = ?,CLW_VALUE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,PROFILE_DETAIL_PARENT_ID = ?,LOOP_VALUE = ? WHERE PROFILE_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getProfileId());
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setString(i++,pData.getProfileDetailStatusCd());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getValue());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getProfileDetailParentId());
        pstmt.setInt(i++,pData.getLoopValue());
        pstmt.setInt(i++,pData.getProfileDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PROFILE_ID="+pData.getProfileId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   PROFILE_DETAIL_STATUS_CD="+pData.getProfileDetailStatusCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PROFILE_DETAIL_PARENT_ID="+pData.getProfileDetailParentId());
            log.debug("SQL:   LOOP_VALUE="+pData.getLoopValue());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ProfileDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProfileDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProfileDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PROFILE_DETAIL WHERE PROFILE_DETAIL_ID = " + pProfileDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ProfileDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PROFILE_DETAIL");
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
     * Inserts a ProfileDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ProfileDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PROFILE_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PROFILE_DETAIL_ID,PROFILE_ID,BUS_ENTITY_ID,PROFILE_DETAIL_STATUS_CD,SHORT_DESC,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PROFILE_DETAIL_PARENT_ID,LOOP_VALUE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getProfileDetailId());
        pstmt.setInt(2+4,pData.getProfileId());
        pstmt.setInt(3+4,pData.getBusEntityId());
        pstmt.setString(4+4,pData.getProfileDetailStatusCd());
        pstmt.setString(5+4,pData.getShortDesc());
        pstmt.setString(6+4,pData.getValue());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());
        pstmt.setInt(11+4,pData.getProfileDetailParentId());
        pstmt.setInt(12+4,pData.getLoopValue());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ProfileDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ProfileDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProfileDetailData insert(Connection pCon, ProfileDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ProfileDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProfileDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProfileDetailData pData, boolean pLogFl)
        throws SQLException {
        ProfileDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getProfileDetailId();
          try {
          oldData = ProfileDetailDataAccess.select(pCon,id);
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
     * Deletes a ProfileDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProfileDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProfileDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PROFILE_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PROFILE_DETAIL d WHERE PROFILE_DETAIL_ID = " + pProfileDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pProfileDetailId);
        return n;
     }

    /**
     * Deletes ProfileDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PROFILE_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PROFILE_DETAIL d ");
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

