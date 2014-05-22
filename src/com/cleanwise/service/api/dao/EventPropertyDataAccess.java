
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        EventPropertyDataAccess
 * Description:  This class is used to build access methods to the CLW_EVENT_PROPERTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.EventPropertyData;
import com.cleanwise.service.api.value.EventPropertyDataVector;
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
 * <code>EventPropertyDataAccess</code>
 */
public class EventPropertyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(EventPropertyDataAccess.class.getName());

    /** <code>CLW_EVENT_PROPERTY</code> table name */
	/* Primary key: EVENT_PROPERTY_ID */
	
    public static final String CLW_EVENT_PROPERTY = "CLW_EVENT_PROPERTY";
    
    /** <code>EVENT_PROPERTY_ID</code> EVENT_PROPERTY_ID column of table CLW_EVENT_PROPERTY */
    public static final String EVENT_PROPERTY_ID = "EVENT_PROPERTY_ID";
    /** <code>EVENT_ID</code> EVENT_ID column of table CLW_EVENT_PROPERTY */
    public static final String EVENT_ID = "EVENT_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_EVENT_PROPERTY */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>TYPE</code> TYPE column of table CLW_EVENT_PROPERTY */
    public static final String TYPE = "TYPE";
    /** <code>BLOB_VALUE</code> BLOB_VALUE column of table CLW_EVENT_PROPERTY */
    public static final String BLOB_VALUE = "BLOB_VALUE";
    /** <code>NUM</code> NUM column of table CLW_EVENT_PROPERTY */
    public static final String NUM = "NUM";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_EVENT_PROPERTY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_EVENT_PROPERTY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_EVENT_PROPERTY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_EVENT_PROPERTY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>NUMBER_VAL</code> NUMBER_VAL column of table CLW_EVENT_PROPERTY */
    public static final String NUMBER_VAL = "NUMBER_VAL";
    /** <code>STRING_VAL</code> STRING_VAL column of table CLW_EVENT_PROPERTY */
    public static final String STRING_VAL = "STRING_VAL";
    /** <code>DATE_VAL</code> DATE_VAL column of table CLW_EVENT_PROPERTY */
    public static final String DATE_VAL = "DATE_VAL";
    /** <code>VAR_CLASS</code> VAR_CLASS column of table CLW_EVENT_PROPERTY */
    public static final String VAR_CLASS = "VAR_CLASS";
    /** <code>BINARY_DATA_SERVER</code> BINARY_DATA_SERVER column of table CLW_EVENT_PROPERTY */
    public static final String BINARY_DATA_SERVER = "BINARY_DATA_SERVER";
    /** <code>BLOB_VALUE_SYSTEM_REF</code> BLOB_VALUE_SYSTEM_REF column of table CLW_EVENT_PROPERTY */
    public static final String BLOB_VALUE_SYSTEM_REF = "BLOB_VALUE_SYSTEM_REF";
    /** <code>STORAGE_TYPE_CD</code> STORAGE_TYPE_CD column of table CLW_EVENT_PROPERTY */
    public static final String STORAGE_TYPE_CD = "STORAGE_TYPE_CD";

    /**
     * Constructor.
     */
    public EventPropertyDataAccess()
    {
    }

    /**
     * Gets a EventPropertyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pEventPropertyId The key requested.
     * @return new EventPropertyData()
     * @throws            SQLException
     */
    public static EventPropertyData select(Connection pCon, int pEventPropertyId)
        throws SQLException, DataNotFoundException {
        EventPropertyData x=null;
        String sql="SELECT EVENT_PROPERTY_ID,EVENT_ID,SHORT_DESC,TYPE,BLOB_VALUE,NUM,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,NUMBER_VAL,STRING_VAL,DATE_VAL,VAR_CLASS,BINARY_DATA_SERVER,BLOB_VALUE_SYSTEM_REF,STORAGE_TYPE_CD FROM CLW_EVENT_PROPERTY WHERE EVENT_PROPERTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pEventPropertyId=" + pEventPropertyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pEventPropertyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=EventPropertyData.createValue();
            
            x.setEventPropertyId(rs.getInt(1));
            x.setEventId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setType(rs.getString(4));
            x.setBlobValue(rs.getBytes(5));
            x.setNum(rs.getInt(6));
            x.setAddBy(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setNumberVal(rs.getInt(11));
            x.setStringVal(rs.getString(12));
            x.setDateVal(rs.getDate(13));
            x.setVarClass(rs.getString(14));
            x.setBinaryDataServer(rs.getString(15));
            x.setBlobValueSystemRef(rs.getString(16));
            x.setStorageTypeCd(rs.getString(17));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("EVENT_PROPERTY_ID :" + pEventPropertyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a EventPropertyDataVector object that consists
     * of EventPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new EventPropertyDataVector()
     * @throws            SQLException
     */
    public static EventPropertyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a EventPropertyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_EVENT_PROPERTY.EVENT_PROPERTY_ID,CLW_EVENT_PROPERTY.EVENT_ID,CLW_EVENT_PROPERTY.SHORT_DESC,CLW_EVENT_PROPERTY.TYPE,CLW_EVENT_PROPERTY.BLOB_VALUE,CLW_EVENT_PROPERTY.NUM,CLW_EVENT_PROPERTY.ADD_BY,CLW_EVENT_PROPERTY.ADD_DATE,CLW_EVENT_PROPERTY.MOD_BY,CLW_EVENT_PROPERTY.MOD_DATE,CLW_EVENT_PROPERTY.NUMBER_VAL,CLW_EVENT_PROPERTY.STRING_VAL,CLW_EVENT_PROPERTY.DATE_VAL,CLW_EVENT_PROPERTY.VAR_CLASS,CLW_EVENT_PROPERTY.BINARY_DATA_SERVER,CLW_EVENT_PROPERTY.BLOB_VALUE_SYSTEM_REF,CLW_EVENT_PROPERTY.STORAGE_TYPE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated EventPropertyData Object.
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
    *@returns a populated EventPropertyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         EventPropertyData x = EventPropertyData.createValue();
         
         x.setEventPropertyId(rs.getInt(1+offset));
         x.setEventId(rs.getInt(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setType(rs.getString(4+offset));
         x.setBlobValue(rs.getBytes(5+offset));
         x.setNum(rs.getInt(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setNumberVal(rs.getInt(11+offset));
         x.setStringVal(rs.getString(12+offset));
         x.setDateVal(rs.getDate(13+offset));
         x.setVarClass(rs.getString(14+offset));
         x.setBinaryDataServer(rs.getString(15+offset));
         x.setBlobValueSystemRef(rs.getString(16+offset));
         x.setStorageTypeCd(rs.getString(17+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the EventPropertyData Object represents.
    */
    public int getColumnCount(){
        return 17;
    }

    /**
     * Gets a EventPropertyDataVector object that consists
     * of EventPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new EventPropertyDataVector()
     * @throws            SQLException
     */
    public static EventPropertyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT EVENT_PROPERTY_ID,EVENT_ID,SHORT_DESC,TYPE,BLOB_VALUE,NUM,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,NUMBER_VAL,STRING_VAL,DATE_VAL,VAR_CLASS,BINARY_DATA_SERVER,BLOB_VALUE_SYSTEM_REF,STORAGE_TYPE_CD FROM CLW_EVENT_PROPERTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_EVENT_PROPERTY.EVENT_PROPERTY_ID,CLW_EVENT_PROPERTY.EVENT_ID,CLW_EVENT_PROPERTY.SHORT_DESC,CLW_EVENT_PROPERTY.TYPE,CLW_EVENT_PROPERTY.BLOB_VALUE,CLW_EVENT_PROPERTY.NUM,CLW_EVENT_PROPERTY.ADD_BY,CLW_EVENT_PROPERTY.ADD_DATE,CLW_EVENT_PROPERTY.MOD_BY,CLW_EVENT_PROPERTY.MOD_DATE,CLW_EVENT_PROPERTY.NUMBER_VAL,CLW_EVENT_PROPERTY.STRING_VAL,CLW_EVENT_PROPERTY.DATE_VAL,CLW_EVENT_PROPERTY.VAR_CLASS,CLW_EVENT_PROPERTY.BINARY_DATA_SERVER,CLW_EVENT_PROPERTY.BLOB_VALUE_SYSTEM_REF,CLW_EVENT_PROPERTY.STORAGE_TYPE_CD FROM CLW_EVENT_PROPERTY");
                where = pCriteria.getSqlClause("CLW_EVENT_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EVENT_PROPERTY.equals(otherTable)){
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
        EventPropertyDataVector v = new EventPropertyDataVector();
        while (rs.next()) {
            EventPropertyData x = EventPropertyData.createValue();
            
            x.setEventPropertyId(rs.getInt(1));
            x.setEventId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setType(rs.getString(4));
            x.setBlobValue(rs.getBytes(5));
            x.setNum(rs.getInt(6));
            x.setAddBy(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setNumberVal(rs.getInt(11));
            x.setStringVal(rs.getString(12));
            x.setDateVal(rs.getDate(13));
            x.setVarClass(rs.getString(14));
            x.setBinaryDataServer(rs.getString(15));
            x.setBlobValueSystemRef(rs.getString(16));
            x.setStorageTypeCd(rs.getString(17));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a EventPropertyDataVector object that consists
     * of EventPropertyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for EventPropertyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new EventPropertyDataVector()
     * @throws            SQLException
     */
    public static EventPropertyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        EventPropertyDataVector v = new EventPropertyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT EVENT_PROPERTY_ID,EVENT_ID,SHORT_DESC,TYPE,BLOB_VALUE,NUM,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,NUMBER_VAL,STRING_VAL,DATE_VAL,VAR_CLASS,BINARY_DATA_SERVER,BLOB_VALUE_SYSTEM_REF,STORAGE_TYPE_CD FROM CLW_EVENT_PROPERTY WHERE EVENT_PROPERTY_ID IN (");

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
            EventPropertyData x=null;
            while (rs.next()) {
                // build the object
                x=EventPropertyData.createValue();
                
                x.setEventPropertyId(rs.getInt(1));
                x.setEventId(rs.getInt(2));
                x.setShortDesc(rs.getString(3));
                x.setType(rs.getString(4));
                x.setBlobValue(rs.getBytes(5));
                x.setNum(rs.getInt(6));
                x.setAddBy(rs.getString(7));
                x.setAddDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));
                x.setModDate(rs.getTimestamp(10));
                x.setNumberVal(rs.getInt(11));
                x.setStringVal(rs.getString(12));
                x.setDateVal(rs.getDate(13));
                x.setVarClass(rs.getString(14));
                x.setBinaryDataServer(rs.getString(15));
                x.setBlobValueSystemRef(rs.getString(16));
                x.setStorageTypeCd(rs.getString(17));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a EventPropertyDataVector object of all
     * EventPropertyData objects in the database.
     * @param pCon An open database connection.
     * @return new EventPropertyDataVector()
     * @throws            SQLException
     */
    public static EventPropertyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT EVENT_PROPERTY_ID,EVENT_ID,SHORT_DESC,TYPE,BLOB_VALUE,NUM,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,NUMBER_VAL,STRING_VAL,DATE_VAL,VAR_CLASS,BINARY_DATA_SERVER,BLOB_VALUE_SYSTEM_REF,STORAGE_TYPE_CD FROM CLW_EVENT_PROPERTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        EventPropertyDataVector v = new EventPropertyDataVector();
        EventPropertyData x = null;
        while (rs.next()) {
            // build the object
            x = EventPropertyData.createValue();
            
            x.setEventPropertyId(rs.getInt(1));
            x.setEventId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setType(rs.getString(4));
            x.setBlobValue(rs.getBytes(5));
            x.setNum(rs.getInt(6));
            x.setAddBy(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setNumberVal(rs.getInt(11));
            x.setStringVal(rs.getString(12));
            x.setDateVal(rs.getDate(13));
            x.setVarClass(rs.getString(14));
            x.setBinaryDataServer(rs.getString(15));
            x.setBlobValueSystemRef(rs.getString(16));
            x.setStorageTypeCd(rs.getString(17));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * EventPropertyData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT EVENT_PROPERTY_ID FROM CLW_EVENT_PROPERTY");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT EVENT_PROPERTY_ID FROM CLW_EVENT_PROPERTY");
                where = pCriteria.getSqlClause("CLW_EVENT_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EVENT_PROPERTY.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EVENT_PROPERTY");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EVENT_PROPERTY");
                where = pCriteria.getSqlClause("CLW_EVENT_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EVENT_PROPERTY.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EVENT_PROPERTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EVENT_PROPERTY");
                where = pCriteria.getSqlClause("CLW_EVENT_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EVENT_PROPERTY.equals(otherTable)){
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
     * Inserts a EventPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EventPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new EventPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static EventPropertyData insert(Connection pCon, EventPropertyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_EVENT_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_EVENT_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setEventPropertyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_EVENT_PROPERTY (EVENT_PROPERTY_ID,EVENT_ID,SHORT_DESC,TYPE,BLOB_VALUE,NUM,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,NUMBER_VAL,STRING_VAL,DATE_VAL,VAR_CLASS,BINARY_DATA_SERVER,BLOB_VALUE_SYSTEM_REF,STORAGE_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getEventPropertyId());
        pstmt.setInt(2,pData.getEventId());
        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getType());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(5, toBlob(pCon,pData.getBlobValue()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getBlobValue());
                pstmt.setBinaryStream(5, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setInt(6,pData.getNum());
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getModBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(11,pData.getNumberVal());
        pstmt.setString(12,pData.getStringVal());
        pstmt.setDate(13,DBAccess.toSQLDate(pData.getDateVal()));
        pstmt.setString(14,pData.getVarClass());
        pstmt.setString(15,pData.getBinaryDataServer());
        pstmt.setString(16,pData.getBlobValueSystemRef());
        pstmt.setString(17,pData.getStorageTypeCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   EVENT_PROPERTY_ID="+pData.getEventPropertyId());
            log.debug("SQL:   EVENT_ID="+pData.getEventId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   TYPE="+pData.getType());
            log.debug("SQL:   BLOB_VALUE="+pData.getBlobValue());
            log.debug("SQL:   NUM="+pData.getNum());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   NUMBER_VAL="+pData.getNumberVal());
            log.debug("SQL:   STRING_VAL="+pData.getStringVal());
            log.debug("SQL:   DATE_VAL="+pData.getDateVal());
            log.debug("SQL:   VAR_CLASS="+pData.getVarClass());
            log.debug("SQL:   BINARY_DATA_SERVER="+pData.getBinaryDataServer());
            log.debug("SQL:   BLOB_VALUE_SYSTEM_REF="+pData.getBlobValueSystemRef());
            log.debug("SQL:   STORAGE_TYPE_CD="+pData.getStorageTypeCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setEventPropertyId(0);
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
     * Updates a EventPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A EventPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, EventPropertyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_EVENT_PROPERTY SET EVENT_ID = ?,SHORT_DESC = ?,TYPE = ?,BLOB_VALUE = ?,NUM = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ?,NUMBER_VAL = ?,STRING_VAL = ?,DATE_VAL = ?,VAR_CLASS = ?,BINARY_DATA_SERVER = ?,BLOB_VALUE_SYSTEM_REF = ?,STORAGE_TYPE_CD = ? WHERE EVENT_PROPERTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getEventId());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getType());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getBlobValue()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getBlobValue());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setInt(i++,pData.getNum());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getNumberVal());
        pstmt.setString(i++,pData.getStringVal());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getDateVal()));
        pstmt.setString(i++,pData.getVarClass());
        pstmt.setString(i++,pData.getBinaryDataServer());
        pstmt.setString(i++,pData.getBlobValueSystemRef());
        pstmt.setString(i++,pData.getStorageTypeCd());
        pstmt.setInt(i++,pData.getEventPropertyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   EVENT_ID="+pData.getEventId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   TYPE="+pData.getType());
            log.debug("SQL:   BLOB_VALUE="+pData.getBlobValue());
            log.debug("SQL:   NUM="+pData.getNum());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   NUMBER_VAL="+pData.getNumberVal());
            log.debug("SQL:   STRING_VAL="+pData.getStringVal());
            log.debug("SQL:   DATE_VAL="+pData.getDateVal());
            log.debug("SQL:   VAR_CLASS="+pData.getVarClass());
            log.debug("SQL:   BINARY_DATA_SERVER="+pData.getBinaryDataServer());
            log.debug("SQL:   BLOB_VALUE_SYSTEM_REF="+pData.getBlobValueSystemRef());
            log.debug("SQL:   STORAGE_TYPE_CD="+pData.getStorageTypeCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a EventPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pEventPropertyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pEventPropertyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_EVENT_PROPERTY WHERE EVENT_PROPERTY_ID = " + pEventPropertyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes EventPropertyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_EVENT_PROPERTY");
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
     * Inserts a EventPropertyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EventPropertyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, EventPropertyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_EVENT_PROPERTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "EVENT_PROPERTY_ID,EVENT_ID,SHORT_DESC,TYPE,BLOB_VALUE,NUM,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,NUMBER_VAL,STRING_VAL,DATE_VAL,VAR_CLASS,BINARY_DATA_SERVER,BLOB_VALUE_SYSTEM_REF,STORAGE_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getEventPropertyId());
        pstmt.setInt(2+4,pData.getEventId());
        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getType());
        pstmt.setBytes(5+4,pData.getBlobValue());
        pstmt.setInt(6+4,pData.getNum());
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getModBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(11+4,pData.getNumberVal());
        pstmt.setString(12+4,pData.getStringVal());
        pstmt.setDate(13+4,DBAccess.toSQLDate(pData.getDateVal()));
        pstmt.setString(14+4,pData.getVarClass());
        pstmt.setString(15+4,pData.getBinaryDataServer());
        pstmt.setString(16+4,pData.getBlobValueSystemRef());
        pstmt.setString(17+4,pData.getStorageTypeCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a EventPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EventPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new EventPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static EventPropertyData insert(Connection pCon, EventPropertyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a EventPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A EventPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, EventPropertyData pData, boolean pLogFl)
        throws SQLException {
        EventPropertyData oldData = null;
        if(pLogFl) {
          int id = pData.getEventPropertyId();
          try {
          oldData = EventPropertyDataAccess.select(pCon,id);
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
     * Deletes a EventPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pEventPropertyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pEventPropertyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_EVENT_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_EVENT_PROPERTY d WHERE EVENT_PROPERTY_ID = " + pEventPropertyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pEventPropertyId);
        return n;
     }

    /**
     * Deletes EventPropertyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_EVENT_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_EVENT_PROPERTY d ");
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

