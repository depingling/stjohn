
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        UiFrameSlotDataAccess
 * Description:  This class is used to build access methods to the CLW_UI_FRAME_SLOT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.UiFrameSlotData;
import com.cleanwise.service.api.value.UiFrameSlotDataVector;
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
 * <code>UiFrameSlotDataAccess</code>
 */
public class UiFrameSlotDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(UiFrameSlotDataAccess.class.getName());

    /** <code>CLW_UI_FRAME_SLOT</code> table name */
	/* Primary key: UI_FRAME_SLOT_ID */
	
    public static final String CLW_UI_FRAME_SLOT = "CLW_UI_FRAME_SLOT";
    
    /** <code>UI_FRAME_SLOT_ID</code> UI_FRAME_SLOT_ID column of table CLW_UI_FRAME_SLOT */
    public static final String UI_FRAME_SLOT_ID = "UI_FRAME_SLOT_ID";
    /** <code>UI_FRAME_ID</code> UI_FRAME_ID column of table CLW_UI_FRAME_SLOT */
    public static final String UI_FRAME_ID = "UI_FRAME_ID";
    /** <code>SLOT_TYPE_CD</code> SLOT_TYPE_CD column of table CLW_UI_FRAME_SLOT */
    public static final String SLOT_TYPE_CD = "SLOT_TYPE_CD";
    /** <code>VALUE</code> VALUE column of table CLW_UI_FRAME_SLOT */
    public static final String VALUE = "VALUE";
    /** <code>URL</code> URL column of table CLW_UI_FRAME_SLOT */
    public static final String URL = "URL";
    /** <code>BLOB</code> BLOB column of table CLW_UI_FRAME_SLOT */
    public static final String BLOB = "BLOB";
    /** <code>URL_TARGET_BLANK</code> URL_TARGET_BLANK column of table CLW_UI_FRAME_SLOT */
    public static final String URL_TARGET_BLANK = "URL_TARGET_BLANK";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_UI_FRAME_SLOT */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_UI_FRAME_SLOT */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_UI_FRAME_SLOT */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_UI_FRAME_SLOT */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public UiFrameSlotDataAccess()
    {
    }

    /**
     * Gets a UiFrameSlotData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pUiFrameSlotId The key requested.
     * @return new UiFrameSlotData()
     * @throws            SQLException
     */
    public static UiFrameSlotData select(Connection pCon, int pUiFrameSlotId)
        throws SQLException, DataNotFoundException {
        UiFrameSlotData x=null;
        String sql="SELECT UI_FRAME_SLOT_ID,UI_FRAME_ID,SLOT_TYPE_CD,VALUE,URL,BLOB,URL_TARGET_BLANK,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_UI_FRAME_SLOT WHERE UI_FRAME_SLOT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pUiFrameSlotId=" + pUiFrameSlotId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pUiFrameSlotId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=UiFrameSlotData.createValue();
            
            x.setUiFrameSlotId(rs.getInt(1));
            x.setUiFrameId(rs.getInt(2));
            x.setSlotTypeCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setUrl(rs.getString(5));
            x.setBlob(rs.getBytes(6));
            x.setUrlTargetBlank(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("UI_FRAME_SLOT_ID :" + pUiFrameSlotId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a UiFrameSlotDataVector object that consists
     * of UiFrameSlotData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new UiFrameSlotDataVector()
     * @throws            SQLException
     */
    public static UiFrameSlotDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a UiFrameSlotData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_UI_FRAME_SLOT.UI_FRAME_SLOT_ID,CLW_UI_FRAME_SLOT.UI_FRAME_ID,CLW_UI_FRAME_SLOT.SLOT_TYPE_CD,CLW_UI_FRAME_SLOT.VALUE,CLW_UI_FRAME_SLOT.URL,CLW_UI_FRAME_SLOT.BLOB,CLW_UI_FRAME_SLOT.URL_TARGET_BLANK,CLW_UI_FRAME_SLOT.ADD_DATE,CLW_UI_FRAME_SLOT.ADD_BY,CLW_UI_FRAME_SLOT.MOD_DATE,CLW_UI_FRAME_SLOT.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated UiFrameSlotData Object.
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
    *@returns a populated UiFrameSlotData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         UiFrameSlotData x = UiFrameSlotData.createValue();
         
         x.setUiFrameSlotId(rs.getInt(1+offset));
         x.setUiFrameId(rs.getInt(2+offset));
         x.setSlotTypeCd(rs.getString(3+offset));
         x.setValue(rs.getString(4+offset));
         x.setUrl(rs.getString(5+offset));
         x.setBlob(rs.getBytes(6+offset));
         x.setUrlTargetBlank(rs.getString(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the UiFrameSlotData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a UiFrameSlotDataVector object that consists
     * of UiFrameSlotData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new UiFrameSlotDataVector()
     * @throws            SQLException
     */
    public static UiFrameSlotDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT UI_FRAME_SLOT_ID,UI_FRAME_ID,SLOT_TYPE_CD,VALUE,URL,BLOB,URL_TARGET_BLANK,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_UI_FRAME_SLOT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_UI_FRAME_SLOT.UI_FRAME_SLOT_ID,CLW_UI_FRAME_SLOT.UI_FRAME_ID,CLW_UI_FRAME_SLOT.SLOT_TYPE_CD,CLW_UI_FRAME_SLOT.VALUE,CLW_UI_FRAME_SLOT.URL,CLW_UI_FRAME_SLOT.BLOB,CLW_UI_FRAME_SLOT.URL_TARGET_BLANK,CLW_UI_FRAME_SLOT.ADD_DATE,CLW_UI_FRAME_SLOT.ADD_BY,CLW_UI_FRAME_SLOT.MOD_DATE,CLW_UI_FRAME_SLOT.MOD_BY FROM CLW_UI_FRAME_SLOT");
                where = pCriteria.getSqlClause("CLW_UI_FRAME_SLOT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_UI_FRAME_SLOT.equals(otherTable)){
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
        UiFrameSlotDataVector v = new UiFrameSlotDataVector();
        while (rs.next()) {
            UiFrameSlotData x = UiFrameSlotData.createValue();
            
            x.setUiFrameSlotId(rs.getInt(1));
            x.setUiFrameId(rs.getInt(2));
            x.setSlotTypeCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setUrl(rs.getString(5));
            x.setBlob(rs.getBytes(6));
            x.setUrlTargetBlank(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a UiFrameSlotDataVector object that consists
     * of UiFrameSlotData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for UiFrameSlotData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new UiFrameSlotDataVector()
     * @throws            SQLException
     */
    public static UiFrameSlotDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        UiFrameSlotDataVector v = new UiFrameSlotDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT UI_FRAME_SLOT_ID,UI_FRAME_ID,SLOT_TYPE_CD,VALUE,URL,BLOB,URL_TARGET_BLANK,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_UI_FRAME_SLOT WHERE UI_FRAME_SLOT_ID IN (");

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
            UiFrameSlotData x=null;
            while (rs.next()) {
                // build the object
                x=UiFrameSlotData.createValue();
                
                x.setUiFrameSlotId(rs.getInt(1));
                x.setUiFrameId(rs.getInt(2));
                x.setSlotTypeCd(rs.getString(3));
                x.setValue(rs.getString(4));
                x.setUrl(rs.getString(5));
                x.setBlob(rs.getBytes(6));
                x.setUrlTargetBlank(rs.getString(7));
                x.setAddDate(rs.getTimestamp(8));
                x.setAddBy(rs.getString(9));
                x.setModDate(rs.getTimestamp(10));
                x.setModBy(rs.getString(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a UiFrameSlotDataVector object of all
     * UiFrameSlotData objects in the database.
     * @param pCon An open database connection.
     * @return new UiFrameSlotDataVector()
     * @throws            SQLException
     */
    public static UiFrameSlotDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT UI_FRAME_SLOT_ID,UI_FRAME_ID,SLOT_TYPE_CD,VALUE,URL,BLOB,URL_TARGET_BLANK,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_UI_FRAME_SLOT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        UiFrameSlotDataVector v = new UiFrameSlotDataVector();
        UiFrameSlotData x = null;
        while (rs.next()) {
            // build the object
            x = UiFrameSlotData.createValue();
            
            x.setUiFrameSlotId(rs.getInt(1));
            x.setUiFrameId(rs.getInt(2));
            x.setSlotTypeCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setUrl(rs.getString(5));
            x.setBlob(rs.getBytes(6));
            x.setUrlTargetBlank(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * UiFrameSlotData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT UI_FRAME_SLOT_ID FROM CLW_UI_FRAME_SLOT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_UI_FRAME_SLOT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_UI_FRAME_SLOT");
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
     * Inserts a UiFrameSlotData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UiFrameSlotData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new UiFrameSlotData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UiFrameSlotData insert(Connection pCon, UiFrameSlotData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_UI_FRAME_SLOT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_UI_FRAME_SLOT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setUiFrameSlotId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_UI_FRAME_SLOT (UI_FRAME_SLOT_ID,UI_FRAME_ID,SLOT_TYPE_CD,VALUE,URL,BLOB,URL_TARGET_BLANK,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getUiFrameSlotId());
        pstmt.setInt(2,pData.getUiFrameId());
        pstmt.setString(3,pData.getSlotTypeCd());
        pstmt.setString(4,pData.getValue());
        pstmt.setString(5,pData.getUrl());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(6, toBlob(pCon,pData.getBlob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getBlob());
                pstmt.setBinaryStream(6, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(7,pData.getUrlTargetBlank());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   UI_FRAME_SLOT_ID="+pData.getUiFrameSlotId());
            log.debug("SQL:   UI_FRAME_ID="+pData.getUiFrameId());
            log.debug("SQL:   SLOT_TYPE_CD="+pData.getSlotTypeCd());
            log.debug("SQL:   VALUE="+pData.getValue());
            log.debug("SQL:   URL="+pData.getUrl());
            log.debug("SQL:   BLOB="+pData.getBlob());
            log.debug("SQL:   URL_TARGET_BLANK="+pData.getUrlTargetBlank());
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
        pData.setUiFrameSlotId(0);
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
     * Updates a UiFrameSlotData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UiFrameSlotData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UiFrameSlotData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_UI_FRAME_SLOT SET UI_FRAME_ID = ?,SLOT_TYPE_CD = ?,VALUE = ?,URL = ?,BLOB = ?,URL_TARGET_BLANK = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE UI_FRAME_SLOT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getUiFrameId());
        pstmt.setString(i++,pData.getSlotTypeCd());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getUrl());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getBlob()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getBlob());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(i++,pData.getUrlTargetBlank());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getUiFrameSlotId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   UI_FRAME_ID="+pData.getUiFrameId());
            log.debug("SQL:   SLOT_TYPE_CD="+pData.getSlotTypeCd());
            log.debug("SQL:   VALUE="+pData.getValue());
            log.debug("SQL:   URL="+pData.getUrl());
            log.debug("SQL:   BLOB="+pData.getBlob());
            log.debug("SQL:   URL_TARGET_BLANK="+pData.getUrlTargetBlank());
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
     * Deletes a UiFrameSlotData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUiFrameSlotId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUiFrameSlotId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_UI_FRAME_SLOT WHERE UI_FRAME_SLOT_ID = " + pUiFrameSlotId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes UiFrameSlotData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_UI_FRAME_SLOT");
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
     * Inserts a UiFrameSlotData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UiFrameSlotData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, UiFrameSlotData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_UI_FRAME_SLOT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "UI_FRAME_SLOT_ID,UI_FRAME_ID,SLOT_TYPE_CD,VALUE,URL,BLOB,URL_TARGET_BLANK,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getUiFrameSlotId());
        pstmt.setInt(2+4,pData.getUiFrameId());
        pstmt.setString(3+4,pData.getSlotTypeCd());
        pstmt.setString(4+4,pData.getValue());
        pstmt.setString(5+4,pData.getUrl());
        pstmt.setBytes(6+4,pData.getBlob());
        pstmt.setString(7+4,pData.getUrlTargetBlank());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a UiFrameSlotData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UiFrameSlotData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new UiFrameSlotData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UiFrameSlotData insert(Connection pCon, UiFrameSlotData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a UiFrameSlotData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UiFrameSlotData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UiFrameSlotData pData, boolean pLogFl)
        throws SQLException {
        UiFrameSlotData oldData = null;
        if(pLogFl) {
          int id = pData.getUiFrameSlotId();
          try {
          oldData = UiFrameSlotDataAccess.select(pCon,id);
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
     * Deletes a UiFrameSlotData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUiFrameSlotId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUiFrameSlotId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_UI_FRAME_SLOT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_UI_FRAME_SLOT d WHERE UI_FRAME_SLOT_ID = " + pUiFrameSlotId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pUiFrameSlotId);
        return n;
     }

    /**
     * Deletes UiFrameSlotData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_UI_FRAME_SLOT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_UI_FRAME_SLOT d ");
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

