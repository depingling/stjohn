
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        DispatchDataAccess
 * Description:  This class is used to build access methods to the CLW_DISPATCH table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.DispatchData;
import com.cleanwise.service.api.value.DispatchDataVector;
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
 * <code>DispatchDataAccess</code>
 */
public class DispatchDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(DispatchDataAccess.class.getName());

    /** <code>CLW_DISPATCH</code> table name */
	/* Primary key: DISPATCH_ID */
	
    public static final String CLW_DISPATCH = "CLW_DISPATCH";
    
    /** <code>DISPATCH_ID</code> DISPATCH_ID column of table CLW_DISPATCH */
    public static final String DISPATCH_ID = "DISPATCH_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_DISPATCH */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_DISPATCH */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_DISPATCH */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>TYPE_CD</code> TYPE_CD column of table CLW_DISPATCH */
    public static final String TYPE_CD = "TYPE_CD";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_DISPATCH */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>SOURCE_CD</code> SOURCE_CD column of table CLW_DISPATCH */
    public static final String SOURCE_CD = "SOURCE_CD";
    /** <code>PRIORITY</code> PRIORITY column of table CLW_DISPATCH */
    public static final String PRIORITY = "PRIORITY";
    /** <code>NOTIFY_DATE</code> NOTIFY_DATE column of table CLW_DISPATCH */
    public static final String NOTIFY_DATE = "NOTIFY_DATE";
    /** <code>CREATE_DATE</code> CREATE_DATE column of table CLW_DISPATCH */
    public static final String CREATE_DATE = "CREATE_DATE";
    /** <code>COMPLETE_DATE</code> COMPLETE_DATE column of table CLW_DISPATCH */
    public static final String COMPLETE_DATE = "COMPLETE_DATE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_DISPATCH */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_DISPATCH */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_DISPATCH */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_DISPATCH */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public DispatchDataAccess()
    {
    }

    /**
     * Gets a DispatchData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pDispatchId The key requested.
     * @return new DispatchData()
     * @throws            SQLException
     */
    public static DispatchData select(Connection pCon, int pDispatchId)
        throws SQLException, DataNotFoundException {
        DispatchData x=null;
        String sql="SELECT DISPATCH_ID,BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,TYPE_CD,STATUS_CD,SOURCE_CD,PRIORITY,NOTIFY_DATE,CREATE_DATE,COMPLETE_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_DISPATCH WHERE DISPATCH_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pDispatchId=" + pDispatchId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pDispatchId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=DispatchData.createValue();
            
            x.setDispatchId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setLongDesc(rs.getString(4));
            x.setTypeCd(rs.getString(5));
            x.setStatusCd(rs.getString(6));
            x.setSourceCd(rs.getString(7));
            x.setPriority(rs.getString(8));
            x.setNotifyDate(rs.getDate(9));
            x.setCreateDate(rs.getDate(10));
            x.setCompleteDate(rs.getDate(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddBy(rs.getString(13));
            x.setModDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("DISPATCH_ID :" + pDispatchId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a DispatchDataVector object that consists
     * of DispatchData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new DispatchDataVector()
     * @throws            SQLException
     */
    public static DispatchDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a DispatchData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_DISPATCH.DISPATCH_ID,CLW_DISPATCH.BUS_ENTITY_ID,CLW_DISPATCH.SHORT_DESC,CLW_DISPATCH.LONG_DESC,CLW_DISPATCH.TYPE_CD,CLW_DISPATCH.STATUS_CD,CLW_DISPATCH.SOURCE_CD,CLW_DISPATCH.PRIORITY,CLW_DISPATCH.NOTIFY_DATE,CLW_DISPATCH.CREATE_DATE,CLW_DISPATCH.COMPLETE_DATE,CLW_DISPATCH.ADD_DATE,CLW_DISPATCH.ADD_BY,CLW_DISPATCH.MOD_DATE,CLW_DISPATCH.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated DispatchData Object.
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
    *@returns a populated DispatchData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         DispatchData x = DispatchData.createValue();
         
         x.setDispatchId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setLongDesc(rs.getString(4+offset));
         x.setTypeCd(rs.getString(5+offset));
         x.setStatusCd(rs.getString(6+offset));
         x.setSourceCd(rs.getString(7+offset));
         x.setPriority(rs.getString(8+offset));
         x.setNotifyDate(rs.getDate(9+offset));
         x.setCreateDate(rs.getDate(10+offset));
         x.setCompleteDate(rs.getDate(11+offset));
         x.setAddDate(rs.getTimestamp(12+offset));
         x.setAddBy(rs.getString(13+offset));
         x.setModDate(rs.getTimestamp(14+offset));
         x.setModBy(rs.getString(15+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the DispatchData Object represents.
    */
    public int getColumnCount(){
        return 15;
    }

    /**
     * Gets a DispatchDataVector object that consists
     * of DispatchData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new DispatchDataVector()
     * @throws            SQLException
     */
    public static DispatchDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISPATCH_ID,BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,TYPE_CD,STATUS_CD,SOURCE_CD,PRIORITY,NOTIFY_DATE,CREATE_DATE,COMPLETE_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_DISPATCH");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_DISPATCH.DISPATCH_ID,CLW_DISPATCH.BUS_ENTITY_ID,CLW_DISPATCH.SHORT_DESC,CLW_DISPATCH.LONG_DESC,CLW_DISPATCH.TYPE_CD,CLW_DISPATCH.STATUS_CD,CLW_DISPATCH.SOURCE_CD,CLW_DISPATCH.PRIORITY,CLW_DISPATCH.NOTIFY_DATE,CLW_DISPATCH.CREATE_DATE,CLW_DISPATCH.COMPLETE_DATE,CLW_DISPATCH.ADD_DATE,CLW_DISPATCH.ADD_BY,CLW_DISPATCH.MOD_DATE,CLW_DISPATCH.MOD_BY FROM CLW_DISPATCH");
                where = pCriteria.getSqlClause("CLW_DISPATCH");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_DISPATCH.equals(otherTable)){
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
        DispatchDataVector v = new DispatchDataVector();
        while (rs.next()) {
            DispatchData x = DispatchData.createValue();
            
            x.setDispatchId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setLongDesc(rs.getString(4));
            x.setTypeCd(rs.getString(5));
            x.setStatusCd(rs.getString(6));
            x.setSourceCd(rs.getString(7));
            x.setPriority(rs.getString(8));
            x.setNotifyDate(rs.getDate(9));
            x.setCreateDate(rs.getDate(10));
            x.setCompleteDate(rs.getDate(11));
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
     * Gets a DispatchDataVector object that consists
     * of DispatchData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for DispatchData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new DispatchDataVector()
     * @throws            SQLException
     */
    public static DispatchDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        DispatchDataVector v = new DispatchDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT DISPATCH_ID,BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,TYPE_CD,STATUS_CD,SOURCE_CD,PRIORITY,NOTIFY_DATE,CREATE_DATE,COMPLETE_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_DISPATCH WHERE DISPATCH_ID IN (");

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
            DispatchData x=null;
            while (rs.next()) {
                // build the object
                x=DispatchData.createValue();
                
                x.setDispatchId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setShortDesc(rs.getString(3));
                x.setLongDesc(rs.getString(4));
                x.setTypeCd(rs.getString(5));
                x.setStatusCd(rs.getString(6));
                x.setSourceCd(rs.getString(7));
                x.setPriority(rs.getString(8));
                x.setNotifyDate(rs.getDate(9));
                x.setCreateDate(rs.getDate(10));
                x.setCompleteDate(rs.getDate(11));
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
     * Gets a DispatchDataVector object of all
     * DispatchData objects in the database.
     * @param pCon An open database connection.
     * @return new DispatchDataVector()
     * @throws            SQLException
     */
    public static DispatchDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT DISPATCH_ID,BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,TYPE_CD,STATUS_CD,SOURCE_CD,PRIORITY,NOTIFY_DATE,CREATE_DATE,COMPLETE_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_DISPATCH";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        DispatchDataVector v = new DispatchDataVector();
        DispatchData x = null;
        while (rs.next()) {
            // build the object
            x = DispatchData.createValue();
            
            x.setDispatchId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setLongDesc(rs.getString(4));
            x.setTypeCd(rs.getString(5));
            x.setStatusCd(rs.getString(6));
            x.setSourceCd(rs.getString(7));
            x.setPriority(rs.getString(8));
            x.setNotifyDate(rs.getDate(9));
            x.setCreateDate(rs.getDate(10));
            x.setCompleteDate(rs.getDate(11));
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
     * DispatchData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT DISPATCH_ID FROM CLW_DISPATCH");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_DISPATCH");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_DISPATCH");
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
     * Inserts a DispatchData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A DispatchData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new DispatchData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static DispatchData insert(Connection pCon, DispatchData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_DISPATCH_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_DISPATCH_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setDispatchId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_DISPATCH (DISPATCH_ID,BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,TYPE_CD,STATUS_CD,SOURCE_CD,PRIORITY,NOTIFY_DATE,CREATE_DATE,COMPLETE_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getDispatchId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getBusEntityId());
        }

        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getLongDesc());
        pstmt.setString(5,pData.getTypeCd());
        pstmt.setString(6,pData.getStatusCd());
        pstmt.setString(7,pData.getSourceCd());
        pstmt.setString(8,pData.getPriority());
        pstmt.setDate(9,DBAccess.toSQLDate(pData.getNotifyDate()));
        pstmt.setDate(10,DBAccess.toSQLDate(pData.getCreateDate()));
        pstmt.setDate(11,DBAccess.toSQLDate(pData.getCompleteDate()));
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(13,pData.getAddBy());
        pstmt.setTimestamp(14,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(15,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   DISPATCH_ID="+pData.getDispatchId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   TYPE_CD="+pData.getTypeCd());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   SOURCE_CD="+pData.getSourceCd());
            log.debug("SQL:   PRIORITY="+pData.getPriority());
            log.debug("SQL:   NOTIFY_DATE="+pData.getNotifyDate());
            log.debug("SQL:   CREATE_DATE="+pData.getCreateDate());
            log.debug("SQL:   COMPLETE_DATE="+pData.getCompleteDate());
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
        pData.setDispatchId(0);
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
     * Updates a DispatchData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A DispatchData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, DispatchData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_DISPATCH SET BUS_ENTITY_ID = ?,SHORT_DESC = ?,LONG_DESC = ?,TYPE_CD = ?,STATUS_CD = ?,SOURCE_CD = ?,PRIORITY = ?,NOTIFY_DATE = ?,CREATE_DATE = ?,COMPLETE_DATE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE DISPATCH_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setString(i++,pData.getTypeCd());
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setString(i++,pData.getSourceCd());
        pstmt.setString(i++,pData.getPriority());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getNotifyDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getCreateDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getCompleteDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getDispatchId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   TYPE_CD="+pData.getTypeCd());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   SOURCE_CD="+pData.getSourceCd());
            log.debug("SQL:   PRIORITY="+pData.getPriority());
            log.debug("SQL:   NOTIFY_DATE="+pData.getNotifyDate());
            log.debug("SQL:   CREATE_DATE="+pData.getCreateDate());
            log.debug("SQL:   COMPLETE_DATE="+pData.getCompleteDate());
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
     * Deletes a DispatchData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pDispatchId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pDispatchId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_DISPATCH WHERE DISPATCH_ID = " + pDispatchId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes DispatchData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_DISPATCH");
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
     * Inserts a DispatchData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A DispatchData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, DispatchData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_DISPATCH (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "DISPATCH_ID,BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,TYPE_CD,STATUS_CD,SOURCE_CD,PRIORITY,NOTIFY_DATE,CREATE_DATE,COMPLETE_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getDispatchId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getBusEntityId());
        }

        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getLongDesc());
        pstmt.setString(5+4,pData.getTypeCd());
        pstmt.setString(6+4,pData.getStatusCd());
        pstmt.setString(7+4,pData.getSourceCd());
        pstmt.setString(8+4,pData.getPriority());
        pstmt.setDate(9+4,DBAccess.toSQLDate(pData.getNotifyDate()));
        pstmt.setDate(10+4,DBAccess.toSQLDate(pData.getCreateDate()));
        pstmt.setDate(11+4,DBAccess.toSQLDate(pData.getCompleteDate()));
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(13+4,pData.getAddBy());
        pstmt.setTimestamp(14+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(15+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a DispatchData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A DispatchData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new DispatchData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static DispatchData insert(Connection pCon, DispatchData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a DispatchData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A DispatchData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, DispatchData pData, boolean pLogFl)
        throws SQLException {
        DispatchData oldData = null;
        if(pLogFl) {
          int id = pData.getDispatchId();
          try {
          oldData = DispatchDataAccess.select(pCon,id);
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
     * Deletes a DispatchData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pDispatchId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pDispatchId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_DISPATCH SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_DISPATCH d WHERE DISPATCH_ID = " + pDispatchId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pDispatchId);
        return n;
     }

    /**
     * Deletes DispatchData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_DISPATCH SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_DISPATCH d ");
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

