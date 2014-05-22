
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ContactDataAccess
 * Description:  This class is used to build access methods to the CLW_CONTACT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ContactData;
import com.cleanwise.service.api.value.ContactDataVector;
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
 * <code>ContactDataAccess</code>
 */
public class ContactDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ContactDataAccess.class.getName());

    /** <code>CLW_CONTACT</code> table name */
	/* Primary key: CONTACT_ID */
	
    public static final String CLW_CONTACT = "CLW_CONTACT";
    
    /** <code>CONTACT_ID</code> CONTACT_ID column of table CLW_CONTACT */
    public static final String CONTACT_ID = "CONTACT_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_CONTACT */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>FIRST_NAME</code> FIRST_NAME column of table CLW_CONTACT */
    public static final String FIRST_NAME = "FIRST_NAME";
    /** <code>LAST_NAME</code> LAST_NAME column of table CLW_CONTACT */
    public static final String LAST_NAME = "LAST_NAME";
    /** <code>CONTACT_TYPE_CD</code> CONTACT_TYPE_CD column of table CLW_CONTACT */
    public static final String CONTACT_TYPE_CD = "CONTACT_TYPE_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CONTACT */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CONTACT */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CONTACT */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CONTACT */
    public static final String MOD_BY = "MOD_BY";
    /** <code>ADDRESS_ID</code> ADDRESS_ID column of table CLW_CONTACT */
    public static final String ADDRESS_ID = "ADDRESS_ID";

    /**
     * Constructor.
     */
    public ContactDataAccess()
    {
    }

    /**
     * Gets a ContactData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pContactId The key requested.
     * @return new ContactData()
     * @throws            SQLException
     */
    public static ContactData select(Connection pCon, int pContactId)
        throws SQLException, DataNotFoundException {
        ContactData x=null;
        String sql="SELECT CONTACT_ID,BUS_ENTITY_ID,FIRST_NAME,LAST_NAME,CONTACT_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ADDRESS_ID FROM CLW_CONTACT WHERE CONTACT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pContactId=" + pContactId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pContactId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ContactData.createValue();
            
            x.setContactId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setFirstName(rs.getString(3));
            x.setLastName(rs.getString(4));
            x.setContactTypeCd(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setAddressId(rs.getInt(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CONTACT_ID :" + pContactId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ContactDataVector object that consists
     * of ContactData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ContactDataVector()
     * @throws            SQLException
     */
    public static ContactDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ContactData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CONTACT.CONTACT_ID,CLW_CONTACT.BUS_ENTITY_ID,CLW_CONTACT.FIRST_NAME,CLW_CONTACT.LAST_NAME,CLW_CONTACT.CONTACT_TYPE_CD,CLW_CONTACT.ADD_DATE,CLW_CONTACT.ADD_BY,CLW_CONTACT.MOD_DATE,CLW_CONTACT.MOD_BY,CLW_CONTACT.ADDRESS_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ContactData Object.
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
    *@returns a populated ContactData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ContactData x = ContactData.createValue();
         
         x.setContactId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setFirstName(rs.getString(3+offset));
         x.setLastName(rs.getString(4+offset));
         x.setContactTypeCd(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         x.setAddressId(rs.getInt(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ContactData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a ContactDataVector object that consists
     * of ContactData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ContactDataVector()
     * @throws            SQLException
     */
    public static ContactDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CONTACT_ID,BUS_ENTITY_ID,FIRST_NAME,LAST_NAME,CONTACT_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ADDRESS_ID FROM CLW_CONTACT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CONTACT.CONTACT_ID,CLW_CONTACT.BUS_ENTITY_ID,CLW_CONTACT.FIRST_NAME,CLW_CONTACT.LAST_NAME,CLW_CONTACT.CONTACT_TYPE_CD,CLW_CONTACT.ADD_DATE,CLW_CONTACT.ADD_BY,CLW_CONTACT.MOD_DATE,CLW_CONTACT.MOD_BY,CLW_CONTACT.ADDRESS_ID FROM CLW_CONTACT");
                where = pCriteria.getSqlClause("CLW_CONTACT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CONTACT.equals(otherTable)){
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
        ContactDataVector v = new ContactDataVector();
        while (rs.next()) {
            ContactData x = ContactData.createValue();
            
            x.setContactId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setFirstName(rs.getString(3));
            x.setLastName(rs.getString(4));
            x.setContactTypeCd(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setAddressId(rs.getInt(10));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ContactDataVector object that consists
     * of ContactData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ContactData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ContactDataVector()
     * @throws            SQLException
     */
    public static ContactDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ContactDataVector v = new ContactDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTACT_ID,BUS_ENTITY_ID,FIRST_NAME,LAST_NAME,CONTACT_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ADDRESS_ID FROM CLW_CONTACT WHERE CONTACT_ID IN (");

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
            ContactData x=null;
            while (rs.next()) {
                // build the object
                x=ContactData.createValue();
                
                x.setContactId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setFirstName(rs.getString(3));
                x.setLastName(rs.getString(4));
                x.setContactTypeCd(rs.getString(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setAddBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));
                x.setAddressId(rs.getInt(10));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ContactDataVector object of all
     * ContactData objects in the database.
     * @param pCon An open database connection.
     * @return new ContactDataVector()
     * @throws            SQLException
     */
    public static ContactDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CONTACT_ID,BUS_ENTITY_ID,FIRST_NAME,LAST_NAME,CONTACT_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ADDRESS_ID FROM CLW_CONTACT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ContactDataVector v = new ContactDataVector();
        ContactData x = null;
        while (rs.next()) {
            // build the object
            x = ContactData.createValue();
            
            x.setContactId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setFirstName(rs.getString(3));
            x.setLastName(rs.getString(4));
            x.setContactTypeCd(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setAddressId(rs.getInt(10));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ContactData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTACT_ID FROM CLW_CONTACT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTACT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTACT");
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
     * Inserts a ContactData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContactData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ContactData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContactData insert(Connection pCon, ContactData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CONTACT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CONTACT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setContactId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CONTACT (CONTACT_ID,BUS_ENTITY_ID,FIRST_NAME,LAST_NAME,CONTACT_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ADDRESS_ID) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getContactId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getBusEntityId());
        }

        pstmt.setString(3,pData.getFirstName());
        pstmt.setString(4,pData.getLastName());
        pstmt.setString(5,pData.getContactTypeCd());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());
        pstmt.setInt(10,pData.getAddressId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CONTACT_ID="+pData.getContactId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   FIRST_NAME="+pData.getFirstName());
            log.debug("SQL:   LAST_NAME="+pData.getLastName());
            log.debug("SQL:   CONTACT_TYPE_CD="+pData.getContactTypeCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ADDRESS_ID="+pData.getAddressId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setContactId(0);
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
     * Updates a ContactData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContactData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContactData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CONTACT SET BUS_ENTITY_ID = ?,FIRST_NAME = ?,LAST_NAME = ?,CONTACT_TYPE_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,ADDRESS_ID = ? WHERE CONTACT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getFirstName());
        pstmt.setString(i++,pData.getLastName());
        pstmt.setString(i++,pData.getContactTypeCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getAddressId());
        pstmt.setInt(i++,pData.getContactId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   FIRST_NAME="+pData.getFirstName());
            log.debug("SQL:   LAST_NAME="+pData.getLastName());
            log.debug("SQL:   CONTACT_TYPE_CD="+pData.getContactTypeCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ADDRESS_ID="+pData.getAddressId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ContactData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContactId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContactId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CONTACT WHERE CONTACT_ID = " + pContactId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ContactData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CONTACT");
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
     * Inserts a ContactData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContactData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ContactData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CONTACT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CONTACT_ID,BUS_ENTITY_ID,FIRST_NAME,LAST_NAME,CONTACT_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ADDRESS_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getContactId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getBusEntityId());
        }

        pstmt.setString(3+4,pData.getFirstName());
        pstmt.setString(4+4,pData.getLastName());
        pstmt.setString(5+4,pData.getContactTypeCd());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());
        pstmt.setInt(10+4,pData.getAddressId());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ContactData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContactData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ContactData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContactData insert(Connection pCon, ContactData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ContactData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContactData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContactData pData, boolean pLogFl)
        throws SQLException {
        ContactData oldData = null;
        if(pLogFl) {
          int id = pData.getContactId();
          try {
          oldData = ContactDataAccess.select(pCon,id);
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
     * Deletes a ContactData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContactId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContactId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CONTACT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTACT d WHERE CONTACT_ID = " + pContactId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pContactId);
        return n;
     }

    /**
     * Deletes ContactData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CONTACT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTACT d ");
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

