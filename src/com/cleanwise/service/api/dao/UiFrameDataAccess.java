
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        UiFrameDataAccess
 * Description:  This class is used to build access methods to the CLW_UI_FRAME table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.UiFrameData;
import com.cleanwise.service.api.value.UiFrameDataVector;
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
 * <code>UiFrameDataAccess</code>
 */
public class UiFrameDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(UiFrameDataAccess.class.getName());

    /** <code>CLW_UI_FRAME</code> table name */
	/* Primary key: UI_FRAME_ID */
	
    public static final String CLW_UI_FRAME = "CLW_UI_FRAME";
    
    /** <code>UI_FRAME_ID</code> UI_FRAME_ID column of table CLW_UI_FRAME */
    public static final String UI_FRAME_ID = "UI_FRAME_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_UI_FRAME */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_UI_FRAME */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>JSP</code> JSP column of table CLW_UI_FRAME */
    public static final String JSP = "JSP";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_UI_FRAME */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>FRAME_TYPE_CD</code> FRAME_TYPE_CD column of table CLW_UI_FRAME */
    public static final String FRAME_TYPE_CD = "FRAME_TYPE_CD";
    /** <code>PARENT_UI_FRAME_ID</code> PARENT_UI_FRAME_ID column of table CLW_UI_FRAME */
    public static final String PARENT_UI_FRAME_ID = "PARENT_UI_FRAME_ID";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_UI_FRAME */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_UI_FRAME */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_UI_FRAME */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_UI_FRAME */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_UI_FRAME */
    public static final String MOD_BY = "MOD_BY";
    /** <code>LOCALE_CD</code> LOCALE_CD column of table CLW_UI_FRAME */
    public static final String LOCALE_CD = "LOCALE_CD";

    /**
     * Constructor.
     */
    public UiFrameDataAccess()
    {
    }

    /**
     * Gets a UiFrameData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pUiFrameId The key requested.
     * @return new UiFrameData()
     * @throws            SQLException
     */
    public static UiFrameData select(Connection pCon, int pUiFrameId)
        throws SQLException, DataNotFoundException {
        UiFrameData x=null;
        String sql="SELECT UI_FRAME_ID,SHORT_DESC,LONG_DESC,JSP,BUS_ENTITY_ID,FRAME_TYPE_CD,PARENT_UI_FRAME_ID,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD FROM CLW_UI_FRAME WHERE UI_FRAME_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pUiFrameId=" + pUiFrameId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pUiFrameId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=UiFrameData.createValue();
            
            x.setUiFrameId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setLongDesc(rs.getString(3));
            x.setJsp(rs.getString(4));
            x.setBusEntityId(rs.getInt(5));
            x.setFrameTypeCd(rs.getString(6));
            x.setParentUiFrameId(rs.getInt(7));
            x.setStatusCd(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setLocaleCd(rs.getString(13));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("UI_FRAME_ID :" + pUiFrameId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a UiFrameDataVector object that consists
     * of UiFrameData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new UiFrameDataVector()
     * @throws            SQLException
     */
    public static UiFrameDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a UiFrameData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_UI_FRAME.UI_FRAME_ID,CLW_UI_FRAME.SHORT_DESC,CLW_UI_FRAME.LONG_DESC,CLW_UI_FRAME.JSP,CLW_UI_FRAME.BUS_ENTITY_ID,CLW_UI_FRAME.FRAME_TYPE_CD,CLW_UI_FRAME.PARENT_UI_FRAME_ID,CLW_UI_FRAME.STATUS_CD,CLW_UI_FRAME.ADD_DATE,CLW_UI_FRAME.ADD_BY,CLW_UI_FRAME.MOD_DATE,CLW_UI_FRAME.MOD_BY,CLW_UI_FRAME.LOCALE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated UiFrameData Object.
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
    *@returns a populated UiFrameData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         UiFrameData x = UiFrameData.createValue();
         
         x.setUiFrameId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setLongDesc(rs.getString(3+offset));
         x.setJsp(rs.getString(4+offset));
         x.setBusEntityId(rs.getInt(5+offset));
         x.setFrameTypeCd(rs.getString(6+offset));
         x.setParentUiFrameId(rs.getInt(7+offset));
         x.setStatusCd(rs.getString(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         x.setLocaleCd(rs.getString(13+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the UiFrameData Object represents.
    */
    public int getColumnCount(){
        return 13;
    }

    /**
     * Gets a UiFrameDataVector object that consists
     * of UiFrameData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new UiFrameDataVector()
     * @throws            SQLException
     */
    public static UiFrameDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT UI_FRAME_ID,SHORT_DESC,LONG_DESC,JSP,BUS_ENTITY_ID,FRAME_TYPE_CD,PARENT_UI_FRAME_ID,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD FROM CLW_UI_FRAME");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_UI_FRAME.UI_FRAME_ID,CLW_UI_FRAME.SHORT_DESC,CLW_UI_FRAME.LONG_DESC,CLW_UI_FRAME.JSP,CLW_UI_FRAME.BUS_ENTITY_ID,CLW_UI_FRAME.FRAME_TYPE_CD,CLW_UI_FRAME.PARENT_UI_FRAME_ID,CLW_UI_FRAME.STATUS_CD,CLW_UI_FRAME.ADD_DATE,CLW_UI_FRAME.ADD_BY,CLW_UI_FRAME.MOD_DATE,CLW_UI_FRAME.MOD_BY,CLW_UI_FRAME.LOCALE_CD FROM CLW_UI_FRAME");
                where = pCriteria.getSqlClause("CLW_UI_FRAME");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_UI_FRAME.equals(otherTable)){
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
        UiFrameDataVector v = new UiFrameDataVector();
        while (rs.next()) {
            UiFrameData x = UiFrameData.createValue();
            
            x.setUiFrameId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setLongDesc(rs.getString(3));
            x.setJsp(rs.getString(4));
            x.setBusEntityId(rs.getInt(5));
            x.setFrameTypeCd(rs.getString(6));
            x.setParentUiFrameId(rs.getInt(7));
            x.setStatusCd(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setLocaleCd(rs.getString(13));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a UiFrameDataVector object that consists
     * of UiFrameData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for UiFrameData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new UiFrameDataVector()
     * @throws            SQLException
     */
    public static UiFrameDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        UiFrameDataVector v = new UiFrameDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT UI_FRAME_ID,SHORT_DESC,LONG_DESC,JSP,BUS_ENTITY_ID,FRAME_TYPE_CD,PARENT_UI_FRAME_ID,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD FROM CLW_UI_FRAME WHERE UI_FRAME_ID IN (");

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
            UiFrameData x=null;
            while (rs.next()) {
                // build the object
                x=UiFrameData.createValue();
                
                x.setUiFrameId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setLongDesc(rs.getString(3));
                x.setJsp(rs.getString(4));
                x.setBusEntityId(rs.getInt(5));
                x.setFrameTypeCd(rs.getString(6));
                x.setParentUiFrameId(rs.getInt(7));
                x.setStatusCd(rs.getString(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setAddBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                x.setLocaleCd(rs.getString(13));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a UiFrameDataVector object of all
     * UiFrameData objects in the database.
     * @param pCon An open database connection.
     * @return new UiFrameDataVector()
     * @throws            SQLException
     */
    public static UiFrameDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT UI_FRAME_ID,SHORT_DESC,LONG_DESC,JSP,BUS_ENTITY_ID,FRAME_TYPE_CD,PARENT_UI_FRAME_ID,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD FROM CLW_UI_FRAME";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        UiFrameDataVector v = new UiFrameDataVector();
        UiFrameData x = null;
        while (rs.next()) {
            // build the object
            x = UiFrameData.createValue();
            
            x.setUiFrameId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setLongDesc(rs.getString(3));
            x.setJsp(rs.getString(4));
            x.setBusEntityId(rs.getInt(5));
            x.setFrameTypeCd(rs.getString(6));
            x.setParentUiFrameId(rs.getInt(7));
            x.setStatusCd(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setLocaleCd(rs.getString(13));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * UiFrameData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT UI_FRAME_ID FROM CLW_UI_FRAME");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_UI_FRAME");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_UI_FRAME");
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
     * Inserts a UiFrameData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UiFrameData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new UiFrameData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UiFrameData insert(Connection pCon, UiFrameData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_UI_FRAME_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_UI_FRAME_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setUiFrameId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_UI_FRAME (UI_FRAME_ID,SHORT_DESC,LONG_DESC,JSP,BUS_ENTITY_ID,FRAME_TYPE_CD,PARENT_UI_FRAME_ID,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getUiFrameId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getLongDesc());
        pstmt.setString(4,pData.getJsp());
        pstmt.setInt(5,pData.getBusEntityId());
        pstmt.setString(6,pData.getFrameTypeCd());
        pstmt.setInt(7,pData.getParentUiFrameId());
        pstmt.setString(8,pData.getStatusCd());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());
        pstmt.setString(13,pData.getLocaleCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   UI_FRAME_ID="+pData.getUiFrameId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   JSP="+pData.getJsp());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   FRAME_TYPE_CD="+pData.getFrameTypeCd());
            log.debug("SQL:   PARENT_UI_FRAME_ID="+pData.getParentUiFrameId());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setUiFrameId(0);
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
     * Updates a UiFrameData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UiFrameData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UiFrameData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_UI_FRAME SET SHORT_DESC = ?,LONG_DESC = ?,JSP = ?,BUS_ENTITY_ID = ?,FRAME_TYPE_CD = ?,PARENT_UI_FRAME_ID = ?,STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,LOCALE_CD = ? WHERE UI_FRAME_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setString(i++,pData.getJsp());
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setString(i++,pData.getFrameTypeCd());
        pstmt.setInt(i++,pData.getParentUiFrameId());
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getLocaleCd());
        pstmt.setInt(i++,pData.getUiFrameId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   JSP="+pData.getJsp());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   FRAME_TYPE_CD="+pData.getFrameTypeCd());
            log.debug("SQL:   PARENT_UI_FRAME_ID="+pData.getParentUiFrameId());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a UiFrameData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUiFrameId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUiFrameId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_UI_FRAME WHERE UI_FRAME_ID = " + pUiFrameId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes UiFrameData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_UI_FRAME");
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
     * Inserts a UiFrameData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UiFrameData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, UiFrameData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_UI_FRAME (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "UI_FRAME_ID,SHORT_DESC,LONG_DESC,JSP,BUS_ENTITY_ID,FRAME_TYPE_CD,PARENT_UI_FRAME_ID,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getUiFrameId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getLongDesc());
        pstmt.setString(4+4,pData.getJsp());
        pstmt.setInt(5+4,pData.getBusEntityId());
        pstmt.setString(6+4,pData.getFrameTypeCd());
        pstmt.setInt(7+4,pData.getParentUiFrameId());
        pstmt.setString(8+4,pData.getStatusCd());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());
        pstmt.setString(13+4,pData.getLocaleCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a UiFrameData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UiFrameData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new UiFrameData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UiFrameData insert(Connection pCon, UiFrameData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a UiFrameData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UiFrameData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UiFrameData pData, boolean pLogFl)
        throws SQLException {
        UiFrameData oldData = null;
        if(pLogFl) {
          int id = pData.getUiFrameId();
          try {
          oldData = UiFrameDataAccess.select(pCon,id);
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
     * Deletes a UiFrameData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUiFrameId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUiFrameId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_UI_FRAME SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_UI_FRAME d WHERE UI_FRAME_ID = " + pUiFrameId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pUiFrameId);
        return n;
     }

    /**
     * Deletes UiFrameData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_UI_FRAME SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_UI_FRAME d ");
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

