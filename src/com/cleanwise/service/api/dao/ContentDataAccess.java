
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ContentDataAccess
 * Description:  This class is used to build access methods to the CLW_CONTENT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ContentData;
import com.cleanwise.service.api.value.ContentDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>ContentDataAccess</code>
 */
public class ContentDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ContentDataAccess.class.getName());

    /** <code>CLW_CONTENT</code> table name */
	/* Primary key: CONTENT_ID */
	
    public static final String CLW_CONTENT = "CLW_CONTENT";
    
    /** <code>CONTENT_ID</code> CONTENT_ID column of table CLW_CONTENT */
    public static final String CONTENT_ID = "CONTENT_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_CONTENT */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_CONTENT */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>VERSION</code> VERSION column of table CLW_CONTENT */
    public static final String VERSION = "VERSION";
    /** <code>CONTENT_TYPE_CD</code> CONTENT_TYPE_CD column of table CLW_CONTENT */
    public static final String CONTENT_TYPE_CD = "CONTENT_TYPE_CD";
    /** <code>CONTENT_STATUS_CD</code> CONTENT_STATUS_CD column of table CLW_CONTENT */
    public static final String CONTENT_STATUS_CD = "CONTENT_STATUS_CD";
    /** <code>SOURCE_CD</code> SOURCE_CD column of table CLW_CONTENT */
    public static final String SOURCE_CD = "SOURCE_CD";
    /** <code>LOCALE_CD</code> LOCALE_CD column of table CLW_CONTENT */
    public static final String LOCALE_CD = "LOCALE_CD";
    /** <code>LANGUAGE_CD</code> LANGUAGE_CD column of table CLW_CONTENT */
    public static final String LANGUAGE_CD = "LANGUAGE_CD";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_CONTENT */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_CONTENT */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>PATH</code> PATH column of table CLW_CONTENT */
    public static final String PATH = "PATH";
    /** <code>CONTENT_USAGE_CD</code> CONTENT_USAGE_CD column of table CLW_CONTENT */
    public static final String CONTENT_USAGE_CD = "CONTENT_USAGE_CD";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_CONTENT */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_CONTENT */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CONTENT */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CONTENT */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CONTENT */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CONTENT */
    public static final String MOD_BY = "MOD_BY";
    /** <code>BINARY_DATA</code> BINARY_DATA column of table CLW_CONTENT */
    public static final String BINARY_DATA = "BINARY_DATA";
    /** <code>CONTENT_SERVER</code> CONTENT_SERVER column of table CLW_CONTENT */
    public static final String CONTENT_SERVER = "CONTENT_SERVER";
    /** <code>CONTENT_SYSTEM_REF</code> CONTENT_SYSTEM_REF column of table CLW_CONTENT */
    public static final String CONTENT_SYSTEM_REF = "CONTENT_SYSTEM_REF";
    /** <code>STORAGE_TYPE_CD</code> STORAGE_TYPE_CD column of table CLW_CONTENT */
    public static final String STORAGE_TYPE_CD = "STORAGE_TYPE_CD";

    /**
     * Constructor.
     */
    public ContentDataAccess()
    {
    }

    /**
     * Gets a ContentData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pContentId The key requested.
     * @return new ContentData()
     * @throws            SQLException
     */
    public static ContentData select(Connection pCon, int pContentId)
        throws SQLException, DataNotFoundException {
        ContentData x=null;
        String sql="SELECT CONTENT_ID,SHORT_DESC,LONG_DESC,VERSION,CONTENT_TYPE_CD,CONTENT_STATUS_CD,SOURCE_CD,LOCALE_CD,LANGUAGE_CD,ITEM_ID,BUS_ENTITY_ID,PATH,CONTENT_USAGE_CD,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BINARY_DATA,CONTENT_SERVER,CONTENT_SYSTEM_REF,STORAGE_TYPE_CD FROM CLW_CONTENT WHERE CONTENT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pContentId=" + pContentId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pContentId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ContentData.createValue();
            
            x.setContentId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setLongDesc(rs.getString(3));
            x.setVersion(rs.getInt(4));
            x.setContentTypeCd(rs.getString(5));
            x.setContentStatusCd(rs.getString(6));
            x.setSourceCd(rs.getString(7));
            x.setLocaleCd(rs.getString(8));
            x.setLanguageCd(rs.getString(9));
            x.setItemId(rs.getInt(10));
            x.setBusEntityId(rs.getInt(11));
            x.setPath(rs.getString(12));
            x.setContentUsageCd(rs.getString(13));
            x.setEffDate(rs.getDate(14));
            x.setExpDate(rs.getDate(15));
            x.setAddDate(rs.getTimestamp(16));
            x.setAddBy(rs.getString(17));
            x.setModDate(rs.getTimestamp(18));
            x.setModBy(rs.getString(19));
            x.setBinaryData(rs.getBytes(20));
            x.setContentServer(rs.getString(21));
            x.setContentSystemRef(rs.getString(22));
            x.setStorageTypeCd(rs.getString(23));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CONTENT_ID :" + pContentId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ContentDataVector object that consists
     * of ContentData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ContentDataVector()
     * @throws            SQLException
     */
    public static ContentDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ContentData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CONTENT.CONTENT_ID,CLW_CONTENT.SHORT_DESC,CLW_CONTENT.LONG_DESC,CLW_CONTENT.VERSION,CLW_CONTENT.CONTENT_TYPE_CD,CLW_CONTENT.CONTENT_STATUS_CD,CLW_CONTENT.SOURCE_CD,CLW_CONTENT.LOCALE_CD,CLW_CONTENT.LANGUAGE_CD,CLW_CONTENT.ITEM_ID,CLW_CONTENT.BUS_ENTITY_ID,CLW_CONTENT.PATH,CLW_CONTENT.CONTENT_USAGE_CD,CLW_CONTENT.EFF_DATE,CLW_CONTENT.EXP_DATE,CLW_CONTENT.ADD_DATE,CLW_CONTENT.ADD_BY,CLW_CONTENT.MOD_DATE,CLW_CONTENT.MOD_BY,CLW_CONTENT.BINARY_DATA,CLW_CONTENT.CONTENT_SERVER,CLW_CONTENT.CONTENT_SYSTEM_REF,CLW_CONTENT.STORAGE_TYPE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ContentData Object.
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
    *@returns a populated ContentData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ContentData x = ContentData.createValue();
         
         x.setContentId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setLongDesc(rs.getString(3+offset));
         x.setVersion(rs.getInt(4+offset));
         x.setContentTypeCd(rs.getString(5+offset));
         x.setContentStatusCd(rs.getString(6+offset));
         x.setSourceCd(rs.getString(7+offset));
         x.setLocaleCd(rs.getString(8+offset));
         x.setLanguageCd(rs.getString(9+offset));
         x.setItemId(rs.getInt(10+offset));
         x.setBusEntityId(rs.getInt(11+offset));
         x.setPath(rs.getString(12+offset));
         x.setContentUsageCd(rs.getString(13+offset));
         x.setEffDate(rs.getDate(14+offset));
         x.setExpDate(rs.getDate(15+offset));
         x.setAddDate(rs.getTimestamp(16+offset));
         x.setAddBy(rs.getString(17+offset));
         x.setModDate(rs.getTimestamp(18+offset));
         x.setModBy(rs.getString(19+offset));
         x.setBinaryData(rs.getBytes(20+offset));
         x.setContentServer(rs.getString(21+offset));
         x.setContentSystemRef(rs.getString(22+offset));
         x.setStorageTypeCd(rs.getString(23+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ContentData Object represents.
    */
    public int getColumnCount(){
        return 23;
    }

    /**
     * Gets a ContentDataVector object that consists
     * of ContentData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ContentDataVector()
     * @throws            SQLException
     */
    public static ContentDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CONTENT_ID,SHORT_DESC,LONG_DESC,VERSION,CONTENT_TYPE_CD,CONTENT_STATUS_CD,SOURCE_CD,LOCALE_CD,LANGUAGE_CD,ITEM_ID,BUS_ENTITY_ID,PATH,CONTENT_USAGE_CD,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BINARY_DATA,CONTENT_SERVER,CONTENT_SYSTEM_REF,STORAGE_TYPE_CD FROM CLW_CONTENT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CONTENT.CONTENT_ID,CLW_CONTENT.SHORT_DESC,CLW_CONTENT.LONG_DESC,CLW_CONTENT.VERSION,CLW_CONTENT.CONTENT_TYPE_CD,CLW_CONTENT.CONTENT_STATUS_CD,CLW_CONTENT.SOURCE_CD,CLW_CONTENT.LOCALE_CD,CLW_CONTENT.LANGUAGE_CD,CLW_CONTENT.ITEM_ID,CLW_CONTENT.BUS_ENTITY_ID,CLW_CONTENT.PATH,CLW_CONTENT.CONTENT_USAGE_CD,CLW_CONTENT.EFF_DATE,CLW_CONTENT.EXP_DATE,CLW_CONTENT.ADD_DATE,CLW_CONTENT.ADD_BY,CLW_CONTENT.MOD_DATE,CLW_CONTENT.MOD_BY,CLW_CONTENT.BINARY_DATA,CLW_CONTENT.CONTENT_SERVER,CLW_CONTENT.CONTENT_SYSTEM_REF,CLW_CONTENT.STORAGE_TYPE_CD FROM CLW_CONTENT");
                where = pCriteria.getSqlClause("CLW_CONTENT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CONTENT.equals(otherTable)){
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
        ContentDataVector v = new ContentDataVector();
        while (rs.next()) {
            ContentData x = ContentData.createValue();
            
            x.setContentId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setLongDesc(rs.getString(3));
            x.setVersion(rs.getInt(4));
            x.setContentTypeCd(rs.getString(5));
            x.setContentStatusCd(rs.getString(6));
            x.setSourceCd(rs.getString(7));
            x.setLocaleCd(rs.getString(8));
            x.setLanguageCd(rs.getString(9));
            x.setItemId(rs.getInt(10));
            x.setBusEntityId(rs.getInt(11));
            x.setPath(rs.getString(12));
            x.setContentUsageCd(rs.getString(13));
            x.setEffDate(rs.getDate(14));
            x.setExpDate(rs.getDate(15));
            x.setAddDate(rs.getTimestamp(16));
            x.setAddBy(rs.getString(17));
            x.setModDate(rs.getTimestamp(18));
            x.setModBy(rs.getString(19));
            x.setBinaryData(rs.getBytes(20));
            x.setContentServer(rs.getString(21));
            x.setContentSystemRef(rs.getString(22));
            x.setStorageTypeCd(rs.getString(23));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ContentDataVector object that consists
     * of ContentData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ContentData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ContentDataVector()
     * @throws            SQLException
     */
    public static ContentDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ContentDataVector v = new ContentDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTENT_ID,SHORT_DESC,LONG_DESC,VERSION,CONTENT_TYPE_CD,CONTENT_STATUS_CD,SOURCE_CD,LOCALE_CD,LANGUAGE_CD,ITEM_ID,BUS_ENTITY_ID,PATH,CONTENT_USAGE_CD,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BINARY_DATA,CONTENT_SERVER,CONTENT_SYSTEM_REF,STORAGE_TYPE_CD FROM CLW_CONTENT WHERE CONTENT_ID IN (");

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
            ContentData x=null;
            while (rs.next()) {
                // build the object
                x=ContentData.createValue();
                
                x.setContentId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setLongDesc(rs.getString(3));
                x.setVersion(rs.getInt(4));
                x.setContentTypeCd(rs.getString(5));
                x.setContentStatusCd(rs.getString(6));
                x.setSourceCd(rs.getString(7));
                x.setLocaleCd(rs.getString(8));
                x.setLanguageCd(rs.getString(9));
                x.setItemId(rs.getInt(10));
                x.setBusEntityId(rs.getInt(11));
                x.setPath(rs.getString(12));
                x.setContentUsageCd(rs.getString(13));
                x.setEffDate(rs.getDate(14));
                x.setExpDate(rs.getDate(15));
                x.setAddDate(rs.getTimestamp(16));
                x.setAddBy(rs.getString(17));
                x.setModDate(rs.getTimestamp(18));
                x.setModBy(rs.getString(19));
                x.setBinaryData(rs.getBytes(20));
                x.setContentServer(rs.getString(21));
                x.setContentSystemRef(rs.getString(22));
                x.setStorageTypeCd(rs.getString(23));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ContentDataVector object of all
     * ContentData objects in the database.
     * @param pCon An open database connection.
     * @return new ContentDataVector()
     * @throws            SQLException
     */
    public static ContentDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CONTENT_ID,SHORT_DESC,LONG_DESC,VERSION,CONTENT_TYPE_CD,CONTENT_STATUS_CD,SOURCE_CD,LOCALE_CD,LANGUAGE_CD,ITEM_ID,BUS_ENTITY_ID,PATH,CONTENT_USAGE_CD,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BINARY_DATA,CONTENT_SERVER,CONTENT_SYSTEM_REF,STORAGE_TYPE_CD FROM CLW_CONTENT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ContentDataVector v = new ContentDataVector();
        ContentData x = null;
        while (rs.next()) {
            // build the object
            x = ContentData.createValue();
            
            x.setContentId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setLongDesc(rs.getString(3));
            x.setVersion(rs.getInt(4));
            x.setContentTypeCd(rs.getString(5));
            x.setContentStatusCd(rs.getString(6));
            x.setSourceCd(rs.getString(7));
            x.setLocaleCd(rs.getString(8));
            x.setLanguageCd(rs.getString(9));
            x.setItemId(rs.getInt(10));
            x.setBusEntityId(rs.getInt(11));
            x.setPath(rs.getString(12));
            x.setContentUsageCd(rs.getString(13));
            x.setEffDate(rs.getDate(14));
            x.setExpDate(rs.getDate(15));
            x.setAddDate(rs.getTimestamp(16));
            x.setAddBy(rs.getString(17));
            x.setModDate(rs.getTimestamp(18));
            x.setModBy(rs.getString(19));
            x.setBinaryData(rs.getBytes(20));
            x.setContentServer(rs.getString(21));
            x.setContentSystemRef(rs.getString(22));
            x.setStorageTypeCd(rs.getString(23));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ContentData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT CONTENT_ID FROM CLW_CONTENT");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT CONTENT_ID FROM CLW_CONTENT");
                where = pCriteria.getSqlClause("CLW_CONTENT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CONTENT.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTENT");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTENT");
                where = pCriteria.getSqlClause("CLW_CONTENT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CONTENT.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTENT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTENT");
                where = pCriteria.getSqlClause("CLW_CONTENT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CONTENT.equals(otherTable)){
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
     * Inserts a ContentData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ContentData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContentData insert(Connection pCon, ContentData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CONTENT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CONTENT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setContentId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CONTENT (CONTENT_ID,SHORT_DESC,LONG_DESC,VERSION,CONTENT_TYPE_CD,CONTENT_STATUS_CD,SOURCE_CD,LOCALE_CD,LANGUAGE_CD,ITEM_ID,BUS_ENTITY_ID,PATH,CONTENT_USAGE_CD,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BINARY_DATA,CONTENT_SERVER,CONTENT_SYSTEM_REF,STORAGE_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getContentId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getLongDesc());
        pstmt.setInt(4,pData.getVersion());
        pstmt.setString(5,pData.getContentTypeCd());
        pstmt.setString(6,pData.getContentStatusCd());
        pstmt.setString(7,pData.getSourceCd());
        pstmt.setString(8,pData.getLocaleCd());
        pstmt.setString(9,pData.getLanguageCd());
        if (pData.getItemId() == 0) {
            pstmt.setNull(10, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(10,pData.getItemId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(11, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(11,pData.getBusEntityId());
        }

        pstmt.setString(12,pData.getPath());
        pstmt.setString(13,pData.getContentUsageCd());
        pstmt.setDate(14,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(15,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setTimestamp(16,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(17,pData.getAddBy());
        pstmt.setTimestamp(18,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(19,pData.getModBy());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(20, toBlob(pCon,pData.getBinaryData()));
        } else {
            try {
                byte b[] = pData.getBinaryData();
                log.info("b = " + b);
                pstmt.setBytes(20,b);
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(21,pData.getContentServer());
        pstmt.setString(22,pData.getContentSystemRef());
        pstmt.setString(23,pData.getStorageTypeCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CONTENT_ID="+pData.getContentId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   VERSION="+pData.getVersion());
            log.debug("SQL:   CONTENT_TYPE_CD="+pData.getContentTypeCd());
            log.debug("SQL:   CONTENT_STATUS_CD="+pData.getContentStatusCd());
            log.debug("SQL:   SOURCE_CD="+pData.getSourceCd());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   LANGUAGE_CD="+pData.getLanguageCd());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   PATH="+pData.getPath());
            log.debug("SQL:   CONTENT_USAGE_CD="+pData.getContentUsageCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   BINARY_DATA="+pData.getBinaryData());
            log.debug("SQL:   CONTENT_SERVER="+pData.getContentServer());
            log.debug("SQL:   CONTENT_SYSTEM_REF="+pData.getContentSystemRef());
            log.debug("SQL:   STORAGE_TYPE_CD="+pData.getStorageTypeCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setContentId(0);
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
     * Updates a ContentData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContentData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CONTENT SET SHORT_DESC = ?,LONG_DESC = ?,VERSION = ?,CONTENT_TYPE_CD = ?,CONTENT_STATUS_CD = ?,SOURCE_CD = ?,LOCALE_CD = ?,LANGUAGE_CD = ?,ITEM_ID = ?,BUS_ENTITY_ID = ?,PATH = ?,CONTENT_USAGE_CD = ?,EFF_DATE = ?,EXP_DATE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,BINARY_DATA = ?,CONTENT_SERVER = ?,CONTENT_SYSTEM_REF = ?,STORAGE_TYPE_CD = ? WHERE CONTENT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setInt(i++,pData.getVersion());
        pstmt.setString(i++,pData.getContentTypeCd());
        pstmt.setString(i++,pData.getContentStatusCd());
        pstmt.setString(i++,pData.getSourceCd());
        pstmt.setString(i++,pData.getLocaleCd());
        pstmt.setString(i++,pData.getLanguageCd());
        if (pData.getItemId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getItemId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getPath());
        pstmt.setString(i++,pData.getContentUsageCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getBinaryData()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getBinaryData());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(i++,pData.getContentServer());
        pstmt.setString(i++,pData.getContentSystemRef());
        pstmt.setString(i++,pData.getStorageTypeCd());
        pstmt.setInt(i++,pData.getContentId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   VERSION="+pData.getVersion());
            log.debug("SQL:   CONTENT_TYPE_CD="+pData.getContentTypeCd());
            log.debug("SQL:   CONTENT_STATUS_CD="+pData.getContentStatusCd());
            log.debug("SQL:   SOURCE_CD="+pData.getSourceCd());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   LANGUAGE_CD="+pData.getLanguageCd());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   PATH="+pData.getPath());
            log.debug("SQL:   CONTENT_USAGE_CD="+pData.getContentUsageCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   BINARY_DATA="+pData.getBinaryData());
            log.debug("SQL:   CONTENT_SERVER="+pData.getContentServer());
            log.debug("SQL:   CONTENT_SYSTEM_REF="+pData.getContentSystemRef());
            log.debug("SQL:   STORAGE_TYPE_CD="+pData.getStorageTypeCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ContentData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContentId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContentId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CONTENT WHERE CONTENT_ID = " + pContentId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ContentData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CONTENT");
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
     * Inserts a ContentData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ContentData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CONTENT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CONTENT_ID,SHORT_DESC,LONG_DESC,VERSION,CONTENT_TYPE_CD,CONTENT_STATUS_CD,SOURCE_CD,LOCALE_CD,LANGUAGE_CD,ITEM_ID,BUS_ENTITY_ID,PATH,CONTENT_USAGE_CD,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BINARY_DATA,CONTENT_SERVER,CONTENT_SYSTEM_REF,STORAGE_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getContentId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getLongDesc());
        pstmt.setInt(4+4,pData.getVersion());
        pstmt.setString(5+4,pData.getContentTypeCd());
        pstmt.setString(6+4,pData.getContentStatusCd());
        pstmt.setString(7+4,pData.getSourceCd());
        pstmt.setString(8+4,pData.getLocaleCd());
        pstmt.setString(9+4,pData.getLanguageCd());
        if (pData.getItemId() == 0) {
            pstmt.setNull(10+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(10+4,pData.getItemId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(11+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(11+4,pData.getBusEntityId());
        }

        pstmt.setString(12+4,pData.getPath());
        pstmt.setString(13+4,pData.getContentUsageCd());
        pstmt.setDate(14+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(15+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setTimestamp(16+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(17+4,pData.getAddBy());
        pstmt.setTimestamp(18+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(19+4,pData.getModBy());
        pstmt.setBytes(20+4,pData.getBinaryData());
        pstmt.setString(21+4,pData.getContentServer());
        pstmt.setString(22+4,pData.getContentSystemRef());
        pstmt.setString(23+4,pData.getStorageTypeCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ContentData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ContentData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContentData insert(Connection pCon, ContentData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ContentData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContentData pData, boolean pLogFl)
        throws SQLException {
        ContentData oldData = null;
        if(pLogFl) {
          int id = pData.getContentId();
          try {
          oldData = ContentDataAccess.select(pCon,id);
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
     * Deletes a ContentData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContentId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContentId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CONTENT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTENT d WHERE CONTENT_ID = " + pContentId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pContentId);
        return n;
     }

    /**
     * Deletes ContentData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CONTENT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTENT d ");
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

