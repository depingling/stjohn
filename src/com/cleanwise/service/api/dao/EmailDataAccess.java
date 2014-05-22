
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        EmailDataAccess
 * Description:  This class is used to build access methods to the CLW_EMAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.EmailDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.cachecos.Cachecos;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>EmailDataAccess</code>
 */
public class EmailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(EmailDataAccess.class.getName());

    /** <code>CLW_EMAIL</code> table name */
	/* Primary key: EMAIL_ID */
	
    public static final String CLW_EMAIL = "CLW_EMAIL";
    
    /** <code>EMAIL_ID</code> EMAIL_ID column of table CLW_EMAIL */
    public static final String EMAIL_ID = "EMAIL_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_EMAIL */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>USER_ID</code> USER_ID column of table CLW_EMAIL */
    public static final String USER_ID = "USER_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_EMAIL */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>EMAIL_TYPE_CD</code> EMAIL_TYPE_CD column of table CLW_EMAIL */
    public static final String EMAIL_TYPE_CD = "EMAIL_TYPE_CD";
    /** <code>EMAIL_STATUS_CD</code> EMAIL_STATUS_CD column of table CLW_EMAIL */
    public static final String EMAIL_STATUS_CD = "EMAIL_STATUS_CD";
    /** <code>EMAIL_ADDRESS</code> EMAIL_ADDRESS column of table CLW_EMAIL */
    public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
    /** <code>PRIMARY_IND</code> PRIMARY_IND column of table CLW_EMAIL */
    public static final String PRIMARY_IND = "PRIMARY_IND";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_EMAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_EMAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_EMAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_EMAIL */
    public static final String MOD_BY = "MOD_BY";
    /** <code>CONTACT_ID</code> CONTACT_ID column of table CLW_EMAIL */
    public static final String CONTACT_ID = "CONTACT_ID";

    /**
     * Constructor.
     */
    public EmailDataAccess()
    {
    }

    /**
     * Gets a EmailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pEmailId The key requested.
     * @return new EmailData()
     * @throws            SQLException
     */
    public static EmailData select(Connection pCon, int pEmailId)
        throws SQLException, DataNotFoundException {
        EmailData x=null;
        String sql="SELECT EMAIL_ID,BUS_ENTITY_ID,USER_ID,SHORT_DESC,EMAIL_TYPE_CD,EMAIL_STATUS_CD,EMAIL_ADDRESS,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTACT_ID FROM CLW_EMAIL WHERE EMAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pEmailId=" + pEmailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pEmailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=EmailData.createValue();
            
            x.setEmailId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setShortDesc(rs.getString(4));
            x.setEmailTypeCd(rs.getString(5));
            x.setEmailStatusCd(rs.getString(6));
            x.setEmailAddress(rs.getString(7));
            x.setPrimaryInd(rs.getBoolean(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setContactId(rs.getInt(13));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("EMAIL_ID :" + pEmailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a EmailDataVector object that consists
     * of EmailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new EmailDataVector()
     * @throws            SQLException
     */
    public static EmailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a EmailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_EMAIL.EMAIL_ID,CLW_EMAIL.BUS_ENTITY_ID,CLW_EMAIL.USER_ID,CLW_EMAIL.SHORT_DESC,CLW_EMAIL.EMAIL_TYPE_CD,CLW_EMAIL.EMAIL_STATUS_CD,CLW_EMAIL.EMAIL_ADDRESS,CLW_EMAIL.PRIMARY_IND,CLW_EMAIL.ADD_DATE,CLW_EMAIL.ADD_BY,CLW_EMAIL.MOD_DATE,CLW_EMAIL.MOD_BY,CLW_EMAIL.CONTACT_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated EmailData Object.
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
    *@returns a populated EmailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         EmailData x = EmailData.createValue();
         
         x.setEmailId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setUserId(rs.getInt(3+offset));
         x.setShortDesc(rs.getString(4+offset));
         x.setEmailTypeCd(rs.getString(5+offset));
         x.setEmailStatusCd(rs.getString(6+offset));
         x.setEmailAddress(rs.getString(7+offset));
         x.setPrimaryInd(rs.getBoolean(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         x.setContactId(rs.getInt(13+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the EmailData Object represents.
    */
    public int getColumnCount(){
        return 13;
    }

    /**
     * Gets a EmailDataVector object that consists
     * of EmailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new EmailDataVector()
     * @throws            SQLException
     */
    public static EmailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT EMAIL_ID,BUS_ENTITY_ID,USER_ID,SHORT_DESC,EMAIL_TYPE_CD,EMAIL_STATUS_CD,EMAIL_ADDRESS,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTACT_ID FROM CLW_EMAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_EMAIL.EMAIL_ID,CLW_EMAIL.BUS_ENTITY_ID,CLW_EMAIL.USER_ID,CLW_EMAIL.SHORT_DESC,CLW_EMAIL.EMAIL_TYPE_CD,CLW_EMAIL.EMAIL_STATUS_CD,CLW_EMAIL.EMAIL_ADDRESS,CLW_EMAIL.PRIMARY_IND,CLW_EMAIL.ADD_DATE,CLW_EMAIL.ADD_BY,CLW_EMAIL.MOD_DATE,CLW_EMAIL.MOD_BY,CLW_EMAIL.CONTACT_ID FROM CLW_EMAIL");
                where = pCriteria.getSqlClause("CLW_EMAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EMAIL.equals(otherTable)){
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
        EmailDataVector v = new EmailDataVector();
        while (rs.next()) {
            EmailData x = EmailData.createValue();
            
            x.setEmailId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setShortDesc(rs.getString(4));
            x.setEmailTypeCd(rs.getString(5));
            x.setEmailStatusCd(rs.getString(6));
            x.setEmailAddress(rs.getString(7));
            x.setPrimaryInd(rs.getBoolean(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setContactId(rs.getInt(13));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a EmailDataVector object that consists
     * of EmailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for EmailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new EmailDataVector()
     * @throws            SQLException
     */
    public static EmailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        EmailDataVector v = new EmailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT EMAIL_ID,BUS_ENTITY_ID,USER_ID,SHORT_DESC,EMAIL_TYPE_CD,EMAIL_STATUS_CD,EMAIL_ADDRESS,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTACT_ID FROM CLW_EMAIL WHERE EMAIL_ID IN (");

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
            EmailData x=null;
            while (rs.next()) {
                // build the object
                x=EmailData.createValue();
                
                x.setEmailId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setUserId(rs.getInt(3));
                x.setShortDesc(rs.getString(4));
                x.setEmailTypeCd(rs.getString(5));
                x.setEmailStatusCd(rs.getString(6));
                x.setEmailAddress(rs.getString(7));
                x.setPrimaryInd(rs.getBoolean(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setAddBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                x.setContactId(rs.getInt(13));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a EmailDataVector object of all
     * EmailData objects in the database.
     * @param pCon An open database connection.
     * @return new EmailDataVector()
     * @throws            SQLException
     */
    public static EmailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT EMAIL_ID,BUS_ENTITY_ID,USER_ID,SHORT_DESC,EMAIL_TYPE_CD,EMAIL_STATUS_CD,EMAIL_ADDRESS,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTACT_ID FROM CLW_EMAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        EmailDataVector v = new EmailDataVector();
        EmailData x = null;
        while (rs.next()) {
            // build the object
            x = EmailData.createValue();
            
            x.setEmailId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setShortDesc(rs.getString(4));
            x.setEmailTypeCd(rs.getString(5));
            x.setEmailStatusCd(rs.getString(6));
            x.setEmailAddress(rs.getString(7));
            x.setPrimaryInd(rs.getBoolean(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setContactId(rs.getInt(13));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * EmailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT EMAIL_ID FROM CLW_EMAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EMAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EMAIL");
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
     * Inserts a EmailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EmailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new EmailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static EmailData insert(Connection pCon, EmailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_EMAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_EMAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setEmailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_EMAIL (EMAIL_ID,BUS_ENTITY_ID,USER_ID,SHORT_DESC,EMAIL_TYPE_CD,EMAIL_STATUS_CD,EMAIL_ADDRESS,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTACT_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getEmailId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getBusEntityId());
        }

        if (pData.getUserId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getUserId());
        }

        pstmt.setString(4,pData.getShortDesc());
        pstmt.setString(5,pData.getEmailTypeCd());
        pstmt.setString(6,pData.getEmailStatusCd());
        pstmt.setString(7,pData.getEmailAddress());
        pstmt.setInt(8, pData.getPrimaryInd()?1:0);
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());
        if (pData.getContactId() == 0) {
            pstmt.setNull(13, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(13,pData.getContactId());
        }


        if (log.isDebugEnabled()) {
            log.debug("SQL:   EMAIL_ID="+pData.getEmailId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   EMAIL_TYPE_CD="+pData.getEmailTypeCd());
            log.debug("SQL:   EMAIL_STATUS_CD="+pData.getEmailStatusCd());
            log.debug("SQL:   EMAIL_ADDRESS="+pData.getEmailAddress());
            log.debug("SQL:   PRIMARY_IND="+pData.getPrimaryInd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   CONTACT_ID="+pData.getContactId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        
        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setEmailId(0);
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
     * Updates a EmailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A EmailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, EmailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_EMAIL SET BUS_ENTITY_ID = ?,USER_ID = ?,SHORT_DESC = ?,EMAIL_TYPE_CD = ?,EMAIL_STATUS_CD = ?,EMAIL_ADDRESS = ?,PRIMARY_IND = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,CONTACT_ID = ? WHERE EMAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        if (pData.getUserId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getUserId());
        }

        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getEmailTypeCd());
        pstmt.setString(i++,pData.getEmailStatusCd());
        pstmt.setString(i++,pData.getEmailAddress());
        pstmt.setInt(i++, pData.getPrimaryInd()?1:0);
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        if (pData.getContactId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getContactId());
        }

        pstmt.setInt(i++,pData.getEmailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   EMAIL_TYPE_CD="+pData.getEmailTypeCd());
            log.debug("SQL:   EMAIL_STATUS_CD="+pData.getEmailStatusCd());
            log.debug("SQL:   EMAIL_ADDRESS="+pData.getEmailAddress());
            log.debug("SQL:   PRIMARY_IND="+pData.getPrimaryInd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   CONTACT_ID="+pData.getContactId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();
        
        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a EmailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pEmailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pEmailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_EMAIL WHERE EMAIL_ID = " + pEmailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes EmailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_EMAIL");
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
     * Inserts a EmailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EmailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, EmailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_EMAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "EMAIL_ID,BUS_ENTITY_ID,USER_ID,SHORT_DESC,EMAIL_TYPE_CD,EMAIL_STATUS_CD,EMAIL_ADDRESS,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTACT_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getEmailId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getBusEntityId());
        }

        if (pData.getUserId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getUserId());
        }

        pstmt.setString(4+4,pData.getShortDesc());
        pstmt.setString(5+4,pData.getEmailTypeCd());
        pstmt.setString(6+4,pData.getEmailStatusCd());
        pstmt.setString(7+4,pData.getEmailAddress());
        pstmt.setBoolean(8+4,pData.getPrimaryInd());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());
        if (pData.getContactId() == 0) {
            pstmt.setNull(13+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(13+4,pData.getContactId());
        }



        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a EmailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EmailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new EmailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static EmailData insert(Connection pCon, EmailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a EmailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A EmailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, EmailData pData, boolean pLogFl)
        throws SQLException {
        EmailData oldData = null;
        if(pLogFl) {
          int id = pData.getEmailId();
          try {
          oldData = EmailDataAccess.select(pCon,id);
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
     * Deletes a EmailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pEmailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pEmailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_EMAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_EMAIL d WHERE EMAIL_ID = " + pEmailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pEmailId);
        return n;
     }

    /**
     * Deletes EmailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_EMAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_EMAIL d ");
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

