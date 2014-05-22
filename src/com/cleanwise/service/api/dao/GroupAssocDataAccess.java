
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        GroupAssocDataAccess
 * Description:  This class is used to build access methods to the CLW_GROUP_ASSOC table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.GroupAssocData;
import com.cleanwise.service.api.value.GroupAssocDataVector;
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
 * <code>GroupAssocDataAccess</code>
 */
public class GroupAssocDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(GroupAssocDataAccess.class.getName());

    /** <code>CLW_GROUP_ASSOC</code> table name */
	/* Primary key: GROUP_ASSOC_ID */
	
    public static final String CLW_GROUP_ASSOC = "CLW_GROUP_ASSOC";
    
    /** <code>GROUP_ASSOC_ID</code> GROUP_ASSOC_ID column of table CLW_GROUP_ASSOC */
    public static final String GROUP_ASSOC_ID = "GROUP_ASSOC_ID";
    /** <code>GROUP_ASSOC_CD</code> GROUP_ASSOC_CD column of table CLW_GROUP_ASSOC */
    public static final String GROUP_ASSOC_CD = "GROUP_ASSOC_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_GROUP_ASSOC */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_GROUP_ASSOC */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_GROUP_ASSOC */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_GROUP_ASSOC */
    public static final String MOD_BY = "MOD_BY";
    /** <code>USER_ID</code> USER_ID column of table CLW_GROUP_ASSOC */
    public static final String USER_ID = "USER_ID";
    /** <code>GROUP_ID</code> GROUP_ID column of table CLW_GROUP_ASSOC */
    public static final String GROUP_ID = "GROUP_ID";
    /** <code>GENERIC_REPORT_ID</code> GENERIC_REPORT_ID column of table CLW_GROUP_ASSOC */
    public static final String GENERIC_REPORT_ID = "GENERIC_REPORT_ID";
    /** <code>APPLICATION_FUNCTION</code> APPLICATION_FUNCTION column of table CLW_GROUP_ASSOC */
    public static final String APPLICATION_FUNCTION = "APPLICATION_FUNCTION";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_GROUP_ASSOC */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";

    /**
     * Constructor.
     */
    public GroupAssocDataAccess()
    {
    }

    /**
     * Gets a GroupAssocData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pGroupAssocId The key requested.
     * @return new GroupAssocData()
     * @throws            SQLException
     */
    public static GroupAssocData select(Connection pCon, int pGroupAssocId)
        throws SQLException, DataNotFoundException {
        GroupAssocData x=null;
        String sql="SELECT GROUP_ASSOC_ID,GROUP_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,USER_ID,GROUP_ID,GENERIC_REPORT_ID,APPLICATION_FUNCTION,BUS_ENTITY_ID FROM CLW_GROUP_ASSOC WHERE GROUP_ASSOC_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pGroupAssocId=" + pGroupAssocId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pGroupAssocId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=GroupAssocData.createValue();
            
            x.setGroupAssocId(rs.getInt(1));
            x.setGroupAssocCd(rs.getString(2));
            x.setAddDate(rs.getTimestamp(3));
            x.setAddBy(rs.getString(4));
            x.setModDate(rs.getTimestamp(5));
            x.setModBy(rs.getString(6));
            x.setUserId(rs.getInt(7));
            x.setGroupId(rs.getInt(8));
            x.setGenericReportId(rs.getInt(9));
            x.setApplicationFunction(rs.getString(10));
            x.setBusEntityId(rs.getInt(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("GROUP_ASSOC_ID :" + pGroupAssocId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a GroupAssocDataVector object that consists
     * of GroupAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new GroupAssocDataVector()
     * @throws            SQLException
     */
    public static GroupAssocDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a GroupAssocData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_GROUP_ASSOC.GROUP_ASSOC_ID,CLW_GROUP_ASSOC.GROUP_ASSOC_CD,CLW_GROUP_ASSOC.ADD_DATE,CLW_GROUP_ASSOC.ADD_BY,CLW_GROUP_ASSOC.MOD_DATE,CLW_GROUP_ASSOC.MOD_BY,CLW_GROUP_ASSOC.USER_ID,CLW_GROUP_ASSOC.GROUP_ID,CLW_GROUP_ASSOC.GENERIC_REPORT_ID,CLW_GROUP_ASSOC.APPLICATION_FUNCTION,CLW_GROUP_ASSOC.BUS_ENTITY_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated GroupAssocData Object.
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
    *@returns a populated GroupAssocData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         GroupAssocData x = GroupAssocData.createValue();
         
         x.setGroupAssocId(rs.getInt(1+offset));
         x.setGroupAssocCd(rs.getString(2+offset));
         x.setAddDate(rs.getTimestamp(3+offset));
         x.setAddBy(rs.getString(4+offset));
         x.setModDate(rs.getTimestamp(5+offset));
         x.setModBy(rs.getString(6+offset));
         x.setUserId(rs.getInt(7+offset));
         x.setGroupId(rs.getInt(8+offset));
         x.setGenericReportId(rs.getInt(9+offset));
         x.setApplicationFunction(rs.getString(10+offset));
         x.setBusEntityId(rs.getInt(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the GroupAssocData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a GroupAssocDataVector object that consists
     * of GroupAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new GroupAssocDataVector()
     * @throws            SQLException
     */
    public static GroupAssocDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT GROUP_ASSOC_ID,GROUP_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,USER_ID,GROUP_ID,GENERIC_REPORT_ID,APPLICATION_FUNCTION,BUS_ENTITY_ID FROM CLW_GROUP_ASSOC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_GROUP_ASSOC.GROUP_ASSOC_ID,CLW_GROUP_ASSOC.GROUP_ASSOC_CD,CLW_GROUP_ASSOC.ADD_DATE,CLW_GROUP_ASSOC.ADD_BY,CLW_GROUP_ASSOC.MOD_DATE,CLW_GROUP_ASSOC.MOD_BY,CLW_GROUP_ASSOC.USER_ID,CLW_GROUP_ASSOC.GROUP_ID,CLW_GROUP_ASSOC.GENERIC_REPORT_ID,CLW_GROUP_ASSOC.APPLICATION_FUNCTION,CLW_GROUP_ASSOC.BUS_ENTITY_ID FROM CLW_GROUP_ASSOC");
                where = pCriteria.getSqlClause("CLW_GROUP_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_GROUP_ASSOC.equals(otherTable)){
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
        GroupAssocDataVector v = new GroupAssocDataVector();
        while (rs.next()) {
            GroupAssocData x = GroupAssocData.createValue();
            
            x.setGroupAssocId(rs.getInt(1));
            x.setGroupAssocCd(rs.getString(2));
            x.setAddDate(rs.getTimestamp(3));
            x.setAddBy(rs.getString(4));
            x.setModDate(rs.getTimestamp(5));
            x.setModBy(rs.getString(6));
            x.setUserId(rs.getInt(7));
            x.setGroupId(rs.getInt(8));
            x.setGenericReportId(rs.getInt(9));
            x.setApplicationFunction(rs.getString(10));
            x.setBusEntityId(rs.getInt(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a GroupAssocDataVector object that consists
     * of GroupAssocData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for GroupAssocData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new GroupAssocDataVector()
     * @throws            SQLException
     */
    public static GroupAssocDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        GroupAssocDataVector v = new GroupAssocDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT GROUP_ASSOC_ID,GROUP_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,USER_ID,GROUP_ID,GENERIC_REPORT_ID,APPLICATION_FUNCTION,BUS_ENTITY_ID FROM CLW_GROUP_ASSOC WHERE GROUP_ASSOC_ID IN (");

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
            GroupAssocData x=null;
            while (rs.next()) {
                // build the object
                x=GroupAssocData.createValue();
                
                x.setGroupAssocId(rs.getInt(1));
                x.setGroupAssocCd(rs.getString(2));
                x.setAddDate(rs.getTimestamp(3));
                x.setAddBy(rs.getString(4));
                x.setModDate(rs.getTimestamp(5));
                x.setModBy(rs.getString(6));
                x.setUserId(rs.getInt(7));
                x.setGroupId(rs.getInt(8));
                x.setGenericReportId(rs.getInt(9));
                x.setApplicationFunction(rs.getString(10));
                x.setBusEntityId(rs.getInt(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a GroupAssocDataVector object of all
     * GroupAssocData objects in the database.
     * @param pCon An open database connection.
     * @return new GroupAssocDataVector()
     * @throws            SQLException
     */
    public static GroupAssocDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT GROUP_ASSOC_ID,GROUP_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,USER_ID,GROUP_ID,GENERIC_REPORT_ID,APPLICATION_FUNCTION,BUS_ENTITY_ID FROM CLW_GROUP_ASSOC";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        GroupAssocDataVector v = new GroupAssocDataVector();
        GroupAssocData x = null;
        while (rs.next()) {
            // build the object
            x = GroupAssocData.createValue();
            
            x.setGroupAssocId(rs.getInt(1));
            x.setGroupAssocCd(rs.getString(2));
            x.setAddDate(rs.getTimestamp(3));
            x.setAddBy(rs.getString(4));
            x.setModDate(rs.getTimestamp(5));
            x.setModBy(rs.getString(6));
            x.setUserId(rs.getInt(7));
            x.setGroupId(rs.getInt(8));
            x.setGenericReportId(rs.getInt(9));
            x.setApplicationFunction(rs.getString(10));
            x.setBusEntityId(rs.getInt(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * GroupAssocData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT GROUP_ASSOC_ID FROM CLW_GROUP_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_GROUP_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_GROUP_ASSOC");
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
     * Inserts a GroupAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A GroupAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new GroupAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static GroupAssocData insert(Connection pCon, GroupAssocData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_GROUP_ASSOC_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_GROUP_ASSOC_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setGroupAssocId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_GROUP_ASSOC (GROUP_ASSOC_ID,GROUP_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,USER_ID,GROUP_ID,GENERIC_REPORT_ID,APPLICATION_FUNCTION,BUS_ENTITY_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getGroupAssocId());
        pstmt.setString(2,pData.getGroupAssocCd());
        pstmt.setTimestamp(3,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(4,pData.getAddBy());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(6,pData.getModBy());
        if (pData.getUserId() == 0) {
            pstmt.setNull(7, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(7,pData.getUserId());
        }

        pstmt.setInt(8,pData.getGroupId());
        pstmt.setInt(9,pData.getGenericReportId());
        pstmt.setString(10,pData.getApplicationFunction());
        pstmt.setInt(11,pData.getBusEntityId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   GROUP_ASSOC_ID="+pData.getGroupAssocId());
            log.debug("SQL:   GROUP_ASSOC_CD="+pData.getGroupAssocCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   GROUP_ID="+pData.getGroupId());
            log.debug("SQL:   GENERIC_REPORT_ID="+pData.getGenericReportId());
            log.debug("SQL:   APPLICATION_FUNCTION="+pData.getApplicationFunction());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setGroupAssocId(0);
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
     * Updates a GroupAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A GroupAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, GroupAssocData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_GROUP_ASSOC SET GROUP_ASSOC_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,USER_ID = ?,GROUP_ID = ?,GENERIC_REPORT_ID = ?,APPLICATION_FUNCTION = ?,BUS_ENTITY_ID = ? WHERE GROUP_ASSOC_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getGroupAssocCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        if (pData.getUserId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getUserId());
        }

        pstmt.setInt(i++,pData.getGroupId());
        pstmt.setInt(i++,pData.getGenericReportId());
        pstmt.setString(i++,pData.getApplicationFunction());
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setInt(i++,pData.getGroupAssocId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   GROUP_ASSOC_CD="+pData.getGroupAssocCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   GROUP_ID="+pData.getGroupId());
            log.debug("SQL:   GENERIC_REPORT_ID="+pData.getGenericReportId());
            log.debug("SQL:   APPLICATION_FUNCTION="+pData.getApplicationFunction());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a GroupAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pGroupAssocId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pGroupAssocId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_GROUP_ASSOC WHERE GROUP_ASSOC_ID = " + pGroupAssocId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes GroupAssocData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_GROUP_ASSOC");
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
     * Inserts a GroupAssocData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A GroupAssocData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, GroupAssocData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_GROUP_ASSOC (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "GROUP_ASSOC_ID,GROUP_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,USER_ID,GROUP_ID,GENERIC_REPORT_ID,APPLICATION_FUNCTION,BUS_ENTITY_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getGroupAssocId());
        pstmt.setString(2+4,pData.getGroupAssocCd());
        pstmt.setTimestamp(3+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(4+4,pData.getAddBy());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(6+4,pData.getModBy());
        if (pData.getUserId() == 0) {
            pstmt.setNull(7+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(7+4,pData.getUserId());
        }

        pstmt.setInt(8+4,pData.getGroupId());
        pstmt.setInt(9+4,pData.getGenericReportId());
        pstmt.setString(10+4,pData.getApplicationFunction());
        pstmt.setInt(11+4,pData.getBusEntityId());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a GroupAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A GroupAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new GroupAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static GroupAssocData insert(Connection pCon, GroupAssocData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a GroupAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A GroupAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, GroupAssocData pData, boolean pLogFl)
        throws SQLException {
        GroupAssocData oldData = null;
        if(pLogFl) {
          int id = pData.getGroupAssocId();
          try {
          oldData = GroupAssocDataAccess.select(pCon,id);
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
     * Deletes a GroupAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pGroupAssocId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pGroupAssocId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_GROUP_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_GROUP_ASSOC d WHERE GROUP_ASSOC_ID = " + pGroupAssocId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pGroupAssocId);
        return n;
     }

    /**
     * Deletes GroupAssocData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_GROUP_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_GROUP_ASSOC d ");
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

