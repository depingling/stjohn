
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        EventEmailDataAccess
 * Description:  This class is used to build access methods to the CLW_EVENT_EMAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.EventEmailData;
import com.cleanwise.service.api.value.EventEmailDataVector;
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
 * <code>EventEmailDataAccess</code>
 */
public class EventEmailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(EventEmailDataAccess.class.getName());

    /** <code>CLW_EVENT_EMAIL</code> table name */
	/* Primary key: EVENT_EMAIL_ID */
	
    public static final String CLW_EVENT_EMAIL = "CLW_EVENT_EMAIL";
    
    /** <code>EVENT_EMAIL_ID</code> EVENT_EMAIL_ID column of table CLW_EVENT_EMAIL */
    public static final String EVENT_EMAIL_ID = "EVENT_EMAIL_ID";
    /** <code>EVENT_ID</code> EVENT_ID column of table CLW_EVENT_EMAIL */
    public static final String EVENT_ID = "EVENT_ID";
    /** <code>TO_ADDRESS</code> TO_ADDRESS column of table CLW_EVENT_EMAIL */
    public static final String TO_ADDRESS = "TO_ADDRESS";
    /** <code>CC_ADDRESS</code> CC_ADDRESS column of table CLW_EVENT_EMAIL */
    public static final String CC_ADDRESS = "CC_ADDRESS";
    /** <code>SUBJECT</code> SUBJECT column of table CLW_EVENT_EMAIL */
    public static final String SUBJECT = "SUBJECT";
    /** <code>TEXT</code> TEXT column of table CLW_EVENT_EMAIL */
    public static final String TEXT = "TEXT";
    /** <code>IMPORTANCE</code> IMPORTANCE column of table CLW_EVENT_EMAIL */
    public static final String IMPORTANCE = "IMPORTANCE";
    /** <code>EMAIL_STATUS_CD</code> EMAIL_STATUS_CD column of table CLW_EVENT_EMAIL */
    public static final String EMAIL_STATUS_CD = "EMAIL_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_EVENT_EMAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_EVENT_EMAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_EVENT_EMAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_EVENT_EMAIL */
    public static final String MOD_BY = "MOD_BY";
    /** <code>ATTACHMENTS</code> ATTACHMENTS column of table CLW_EVENT_EMAIL */
    public static final String ATTACHMENTS = "ATTACHMENTS";
    /** <code>FROM_ADDRESS</code> FROM_ADDRESS column of table CLW_EVENT_EMAIL */
    public static final String FROM_ADDRESS = "FROM_ADDRESS";
    /** <code>LONG_TEXT</code> LONG_TEXT column of table CLW_EVENT_EMAIL */
    public static final String LONG_TEXT = "LONG_TEXT";
    /** <code>BINARY_DATA_SERVER</code> BINARY_DATA_SERVER column of table CLW_EVENT_EMAIL */
    public static final String BINARY_DATA_SERVER = "BINARY_DATA_SERVER";
    /** <code>ATTACHMENTS_SYSTEM_REF</code> ATTACHMENTS_SYSTEM_REF column of table CLW_EVENT_EMAIL */
    public static final String ATTACHMENTS_SYSTEM_REF = "ATTACHMENTS_SYSTEM_REF";
    /** <code>LONG_TEXT_SYSTEM_REF</code> LONG_TEXT_SYSTEM_REF column of table CLW_EVENT_EMAIL */
    public static final String LONG_TEXT_SYSTEM_REF = "LONG_TEXT_SYSTEM_REF";
    /** <code>STORAGE_TYPE_CD</code> STORAGE_TYPE_CD column of table CLW_EVENT_EMAIL */
    public static final String STORAGE_TYPE_CD = "STORAGE_TYPE_CD";

    /**
     * Constructor.
     */
    public EventEmailDataAccess()
    {
    }

    /**
     * Gets a EventEmailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pEventEmailId The key requested.
     * @return new EventEmailData()
     * @throws            SQLException
     */
    public static EventEmailData select(Connection pCon, int pEventEmailId)
        throws SQLException, DataNotFoundException {
        EventEmailData x=null;
        String sql="SELECT EVENT_EMAIL_ID,EVENT_ID,TO_ADDRESS,CC_ADDRESS,SUBJECT,TEXT,IMPORTANCE,EMAIL_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ATTACHMENTS,FROM_ADDRESS,LONG_TEXT,BINARY_DATA_SERVER,ATTACHMENTS_SYSTEM_REF,LONG_TEXT_SYSTEM_REF,STORAGE_TYPE_CD FROM CLW_EVENT_EMAIL WHERE EVENT_EMAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pEventEmailId=" + pEventEmailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pEventEmailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=EventEmailData.createValue();
            
            x.setEventEmailId(rs.getInt(1));
            x.setEventId(rs.getInt(2));
            x.setToAddress(rs.getString(3));
            x.setCcAddress(rs.getString(4));
            x.setSubject(rs.getString(5));
            x.setText(rs.getString(6));
            x.setImportance(rs.getString(7));
            x.setEmailStatusCd(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setAttachments(rs.getBytes(13));
            x.setFromAddress(rs.getString(14));
            x.setLongText(rs.getBytes(15));
            x.setBinaryDataServer(rs.getString(16));
            x.setAttachmentsSystemRef(rs.getString(17));
            x.setLongTextSystemRef(rs.getString(18));
            x.setStorageTypeCd(rs.getString(19));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("EVENT_EMAIL_ID :" + pEventEmailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a EventEmailDataVector object that consists
     * of EventEmailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new EventEmailDataVector()
     * @throws            SQLException
     */
    public static EventEmailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a EventEmailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_EVENT_EMAIL.EVENT_EMAIL_ID,CLW_EVENT_EMAIL.EVENT_ID,CLW_EVENT_EMAIL.TO_ADDRESS,CLW_EVENT_EMAIL.CC_ADDRESS,CLW_EVENT_EMAIL.SUBJECT,CLW_EVENT_EMAIL.TEXT,CLW_EVENT_EMAIL.IMPORTANCE,CLW_EVENT_EMAIL.EMAIL_STATUS_CD,CLW_EVENT_EMAIL.ADD_DATE,CLW_EVENT_EMAIL.ADD_BY,CLW_EVENT_EMAIL.MOD_DATE,CLW_EVENT_EMAIL.MOD_BY,CLW_EVENT_EMAIL.ATTACHMENTS,CLW_EVENT_EMAIL.FROM_ADDRESS,CLW_EVENT_EMAIL.LONG_TEXT,CLW_EVENT_EMAIL.BINARY_DATA_SERVER,CLW_EVENT_EMAIL.ATTACHMENTS_SYSTEM_REF,CLW_EVENT_EMAIL.LONG_TEXT_SYSTEM_REF,CLW_EVENT_EMAIL.STORAGE_TYPE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated EventEmailData Object.
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
    *@returns a populated EventEmailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         EventEmailData x = EventEmailData.createValue();
         
         x.setEventEmailId(rs.getInt(1+offset));
         x.setEventId(rs.getInt(2+offset));
         x.setToAddress(rs.getString(3+offset));
         x.setCcAddress(rs.getString(4+offset));
         x.setSubject(rs.getString(5+offset));
         x.setText(rs.getString(6+offset));
         x.setImportance(rs.getString(7+offset));
         x.setEmailStatusCd(rs.getString(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         x.setAttachments(rs.getBytes(13+offset));
         x.setFromAddress(rs.getString(14+offset));
         x.setLongText(rs.getBytes(15+offset));
         x.setBinaryDataServer(rs.getString(16+offset));
         x.setAttachmentsSystemRef(rs.getString(17+offset));
         x.setLongTextSystemRef(rs.getString(18+offset));
         x.setStorageTypeCd(rs.getString(19+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the EventEmailData Object represents.
    */
    public int getColumnCount(){
        return 19;
    }

    /**
     * Gets a EventEmailDataVector object that consists
     * of EventEmailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new EventEmailDataVector()
     * @throws            SQLException
     */
    public static EventEmailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT EVENT_EMAIL_ID,EVENT_ID,TO_ADDRESS,CC_ADDRESS,SUBJECT,TEXT,IMPORTANCE,EMAIL_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ATTACHMENTS,FROM_ADDRESS,LONG_TEXT,BINARY_DATA_SERVER,ATTACHMENTS_SYSTEM_REF,LONG_TEXT_SYSTEM_REF,STORAGE_TYPE_CD FROM CLW_EVENT_EMAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_EVENT_EMAIL.EVENT_EMAIL_ID,CLW_EVENT_EMAIL.EVENT_ID,CLW_EVENT_EMAIL.TO_ADDRESS,CLW_EVENT_EMAIL.CC_ADDRESS,CLW_EVENT_EMAIL.SUBJECT,CLW_EVENT_EMAIL.TEXT,CLW_EVENT_EMAIL.IMPORTANCE,CLW_EVENT_EMAIL.EMAIL_STATUS_CD,CLW_EVENT_EMAIL.ADD_DATE,CLW_EVENT_EMAIL.ADD_BY,CLW_EVENT_EMAIL.MOD_DATE,CLW_EVENT_EMAIL.MOD_BY,CLW_EVENT_EMAIL.ATTACHMENTS,CLW_EVENT_EMAIL.FROM_ADDRESS,CLW_EVENT_EMAIL.LONG_TEXT,CLW_EVENT_EMAIL.BINARY_DATA_SERVER,CLW_EVENT_EMAIL.ATTACHMENTS_SYSTEM_REF,CLW_EVENT_EMAIL.LONG_TEXT_SYSTEM_REF,CLW_EVENT_EMAIL.STORAGE_TYPE_CD FROM CLW_EVENT_EMAIL");
                where = pCriteria.getSqlClause("CLW_EVENT_EMAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EVENT_EMAIL.equals(otherTable)){
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
        EventEmailDataVector v = new EventEmailDataVector();
        while (rs.next()) {
            EventEmailData x = EventEmailData.createValue();
            
            x.setEventEmailId(rs.getInt(1));
            x.setEventId(rs.getInt(2));
            x.setToAddress(rs.getString(3));
            x.setCcAddress(rs.getString(4));
            x.setSubject(rs.getString(5));
            x.setText(rs.getString(6));
            x.setImportance(rs.getString(7));
            x.setEmailStatusCd(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setAttachments(rs.getBytes(13));
            x.setFromAddress(rs.getString(14));
            x.setLongText(rs.getBytes(15));
            x.setBinaryDataServer(rs.getString(16));
            x.setAttachmentsSystemRef(rs.getString(17));
            x.setLongTextSystemRef(rs.getString(18));
            x.setStorageTypeCd(rs.getString(19));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a EventEmailDataVector object that consists
     * of EventEmailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for EventEmailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new EventEmailDataVector()
     * @throws            SQLException
     */
    public static EventEmailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        EventEmailDataVector v = new EventEmailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT EVENT_EMAIL_ID,EVENT_ID,TO_ADDRESS,CC_ADDRESS,SUBJECT,TEXT,IMPORTANCE,EMAIL_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ATTACHMENTS,FROM_ADDRESS,LONG_TEXT,BINARY_DATA_SERVER,ATTACHMENTS_SYSTEM_REF,LONG_TEXT_SYSTEM_REF,STORAGE_TYPE_CD FROM CLW_EVENT_EMAIL WHERE EVENT_EMAIL_ID IN (");

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
            EventEmailData x=null;
            while (rs.next()) {
                // build the object
                x=EventEmailData.createValue();
                
                x.setEventEmailId(rs.getInt(1));
                x.setEventId(rs.getInt(2));
                x.setToAddress(rs.getString(3));
                x.setCcAddress(rs.getString(4));
                x.setSubject(rs.getString(5));
                x.setText(rs.getString(6));
                x.setImportance(rs.getString(7));
                x.setEmailStatusCd(rs.getString(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setAddBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                x.setAttachments(rs.getBytes(13));
                x.setFromAddress(rs.getString(14));
                x.setLongText(rs.getBytes(15));
                x.setBinaryDataServer(rs.getString(16));
                x.setAttachmentsSystemRef(rs.getString(17));
                x.setLongTextSystemRef(rs.getString(18));
                x.setStorageTypeCd(rs.getString(19));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a EventEmailDataVector object of all
     * EventEmailData objects in the database.
     * @param pCon An open database connection.
     * @return new EventEmailDataVector()
     * @throws            SQLException
     */
    public static EventEmailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT EVENT_EMAIL_ID,EVENT_ID,TO_ADDRESS,CC_ADDRESS,SUBJECT,TEXT,IMPORTANCE,EMAIL_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ATTACHMENTS,FROM_ADDRESS,LONG_TEXT,BINARY_DATA_SERVER,ATTACHMENTS_SYSTEM_REF,LONG_TEXT_SYSTEM_REF,STORAGE_TYPE_CD FROM CLW_EVENT_EMAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        EventEmailDataVector v = new EventEmailDataVector();
        EventEmailData x = null;
        while (rs.next()) {
            // build the object
            x = EventEmailData.createValue();
            
            x.setEventEmailId(rs.getInt(1));
            x.setEventId(rs.getInt(2));
            x.setToAddress(rs.getString(3));
            x.setCcAddress(rs.getString(4));
            x.setSubject(rs.getString(5));
            x.setText(rs.getString(6));
            x.setImportance(rs.getString(7));
            x.setEmailStatusCd(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setAttachments(rs.getBytes(13));
            x.setFromAddress(rs.getString(14));
            x.setLongText(rs.getBytes(15));
            x.setBinaryDataServer(rs.getString(16));
            x.setAttachmentsSystemRef(rs.getString(17));
            x.setLongTextSystemRef(rs.getString(18));
            x.setStorageTypeCd(rs.getString(19));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * EventEmailData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT EVENT_EMAIL_ID FROM CLW_EVENT_EMAIL");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT EVENT_EMAIL_ID FROM CLW_EVENT_EMAIL");
                where = pCriteria.getSqlClause("CLW_EVENT_EMAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EVENT_EMAIL.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EVENT_EMAIL");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EVENT_EMAIL");
                where = pCriteria.getSqlClause("CLW_EVENT_EMAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EVENT_EMAIL.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EVENT_EMAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EVENT_EMAIL");
                where = pCriteria.getSqlClause("CLW_EVENT_EMAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EVENT_EMAIL.equals(otherTable)){
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
     * Inserts a EventEmailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EventEmailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new EventEmailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static EventEmailData insert(Connection pCon, EventEmailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_EVENT_EMAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_EVENT_EMAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setEventEmailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_EVENT_EMAIL (EVENT_EMAIL_ID,EVENT_ID,TO_ADDRESS,CC_ADDRESS,SUBJECT,TEXT,IMPORTANCE,EMAIL_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ATTACHMENTS,FROM_ADDRESS,LONG_TEXT,BINARY_DATA_SERVER,ATTACHMENTS_SYSTEM_REF,LONG_TEXT_SYSTEM_REF,STORAGE_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getEventEmailId());
        if (pData.getEventId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getEventId());
        }

        pstmt.setString(3,pData.getToAddress());
        pstmt.setString(4,pData.getCcAddress());
        pstmt.setString(5,pData.getSubject());
        pstmt.setString(6,pData.getText());
        pstmt.setString(7,pData.getImportance());
        pstmt.setString(8,pData.getEmailStatusCd());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(13, toBlob(pCon,pData.getAttachments()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getAttachments());
                pstmt.setBinaryStream(13, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(14,pData.getFromAddress());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(15, toBlob(pCon,pData.getLongText()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getLongText());
                pstmt.setBinaryStream(15, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(16,pData.getBinaryDataServer());
        pstmt.setString(17,pData.getAttachmentsSystemRef());
        pstmt.setString(18,pData.getLongTextSystemRef());
        pstmt.setString(19,pData.getStorageTypeCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   EVENT_EMAIL_ID="+pData.getEventEmailId());
            log.debug("SQL:   EVENT_ID="+pData.getEventId());
            log.debug("SQL:   TO_ADDRESS="+pData.getToAddress());
            log.debug("SQL:   CC_ADDRESS="+pData.getCcAddress());
            log.debug("SQL:   SUBJECT="+pData.getSubject());
            log.debug("SQL:   TEXT="+pData.getText());
            log.debug("SQL:   IMPORTANCE="+pData.getImportance());
            log.debug("SQL:   EMAIL_STATUS_CD="+pData.getEmailStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ATTACHMENTS="+pData.getAttachments());
            log.debug("SQL:   FROM_ADDRESS="+pData.getFromAddress());
            log.debug("SQL:   LONG_TEXT="+pData.getLongText());
            log.debug("SQL:   BINARY_DATA_SERVER="+pData.getBinaryDataServer());
            log.debug("SQL:   ATTACHMENTS_SYSTEM_REF="+pData.getAttachmentsSystemRef());
            log.debug("SQL:   LONG_TEXT_SYSTEM_REF="+pData.getLongTextSystemRef());
            log.debug("SQL:   STORAGE_TYPE_CD="+pData.getStorageTypeCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setEventEmailId(0);
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
     * Updates a EventEmailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A EventEmailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, EventEmailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_EVENT_EMAIL SET EVENT_ID = ?,TO_ADDRESS = ?,CC_ADDRESS = ?,SUBJECT = ?,TEXT = ?,IMPORTANCE = ?,EMAIL_STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,ATTACHMENTS = ?,FROM_ADDRESS = ?,LONG_TEXT = ?,BINARY_DATA_SERVER = ?,ATTACHMENTS_SYSTEM_REF = ?,LONG_TEXT_SYSTEM_REF = ?,STORAGE_TYPE_CD = ? WHERE EVENT_EMAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getEventId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getEventId());
        }

        pstmt.setString(i++,pData.getToAddress());
        pstmt.setString(i++,pData.getCcAddress());
        pstmt.setString(i++,pData.getSubject());
        pstmt.setString(i++,pData.getText());
        pstmt.setString(i++,pData.getImportance());
        pstmt.setString(i++,pData.getEmailStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getAttachments()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getAttachments());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(i++,pData.getFromAddress());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getLongText()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getLongText());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(i++,pData.getBinaryDataServer());
        pstmt.setString(i++,pData.getAttachmentsSystemRef());
        pstmt.setString(i++,pData.getLongTextSystemRef());
        pstmt.setString(i++,pData.getStorageTypeCd());
        pstmt.setInt(i++,pData.getEventEmailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   EVENT_ID="+pData.getEventId());
            log.debug("SQL:   TO_ADDRESS="+pData.getToAddress());
            log.debug("SQL:   CC_ADDRESS="+pData.getCcAddress());
            log.debug("SQL:   SUBJECT="+pData.getSubject());
            log.debug("SQL:   TEXT="+pData.getText());
            log.debug("SQL:   IMPORTANCE="+pData.getImportance());
            log.debug("SQL:   EMAIL_STATUS_CD="+pData.getEmailStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ATTACHMENTS="+pData.getAttachments());
            log.debug("SQL:   FROM_ADDRESS="+pData.getFromAddress());
            log.debug("SQL:   LONG_TEXT="+pData.getLongText());
            log.debug("SQL:   BINARY_DATA_SERVER="+pData.getBinaryDataServer());
            log.debug("SQL:   ATTACHMENTS_SYSTEM_REF="+pData.getAttachmentsSystemRef());
            log.debug("SQL:   LONG_TEXT_SYSTEM_REF="+pData.getLongTextSystemRef());
            log.debug("SQL:   STORAGE_TYPE_CD="+pData.getStorageTypeCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a EventEmailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pEventEmailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pEventEmailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_EVENT_EMAIL WHERE EVENT_EMAIL_ID = " + pEventEmailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes EventEmailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_EVENT_EMAIL");
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
     * Inserts a EventEmailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EventEmailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, EventEmailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_EVENT_EMAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "EVENT_EMAIL_ID,EVENT_ID,TO_ADDRESS,CC_ADDRESS,SUBJECT,TEXT,IMPORTANCE,EMAIL_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ATTACHMENTS,FROM_ADDRESS,LONG_TEXT,BINARY_DATA_SERVER,ATTACHMENTS_SYSTEM_REF,LONG_TEXT_SYSTEM_REF,STORAGE_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getEventEmailId());
        if (pData.getEventId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getEventId());
        }

        pstmt.setString(3+4,pData.getToAddress());
        pstmt.setString(4+4,pData.getCcAddress());
        pstmt.setString(5+4,pData.getSubject());
        pstmt.setString(6+4,pData.getText());
        pstmt.setString(7+4,pData.getImportance());
        pstmt.setString(8+4,pData.getEmailStatusCd());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());
        pstmt.setBytes(13+4,pData.getAttachments());
        pstmt.setString(14+4,pData.getFromAddress());
        pstmt.setBytes(15+4,pData.getLongText());
        pstmt.setString(16+4,pData.getBinaryDataServer());
        pstmt.setString(17+4,pData.getAttachmentsSystemRef());
        pstmt.setString(18+4,pData.getLongTextSystemRef());
        pstmt.setString(19+4,pData.getStorageTypeCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a EventEmailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EventEmailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new EventEmailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static EventEmailData insert(Connection pCon, EventEmailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a EventEmailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A EventEmailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, EventEmailData pData, boolean pLogFl)
        throws SQLException {
        EventEmailData oldData = null;
        if(pLogFl) {
          int id = pData.getEventEmailId();
          try {
          oldData = EventEmailDataAccess.select(pCon,id);
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
     * Deletes a EventEmailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pEventEmailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pEventEmailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_EVENT_EMAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_EVENT_EMAIL d WHERE EVENT_EMAIL_ID = " + pEventEmailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pEventEmailId);
        return n;
     }

    /**
     * Deletes EventEmailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_EVENT_EMAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_EVENT_EMAIL d ");
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

