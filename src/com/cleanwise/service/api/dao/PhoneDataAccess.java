
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PhoneDataAccess
 * Description:  This class is used to build access methods to the CLW_PHONE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PhoneDataVector;
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
 * <code>PhoneDataAccess</code>
 */
public class PhoneDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PhoneDataAccess.class.getName());

    /** <code>CLW_PHONE</code> table name */
	/* Primary key: PHONE_ID */
	
    public static final String CLW_PHONE = "CLW_PHONE";
    
    /** <code>PHONE_ID</code> PHONE_ID column of table CLW_PHONE */
    public static final String PHONE_ID = "PHONE_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_PHONE */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>USER_ID</code> USER_ID column of table CLW_PHONE */
    public static final String USER_ID = "USER_ID";
    /** <code>PHONE_COUNTRY_CD</code> PHONE_COUNTRY_CD column of table CLW_PHONE */
    public static final String PHONE_COUNTRY_CD = "PHONE_COUNTRY_CD";
    /** <code>PHONE_AREA_CODE</code> PHONE_AREA_CODE column of table CLW_PHONE */
    public static final String PHONE_AREA_CODE = "PHONE_AREA_CODE";
    /** <code>PHONE_NUM</code> PHONE_NUM column of table CLW_PHONE */
    public static final String PHONE_NUM = "PHONE_NUM";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_PHONE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>PHONE_TYPE_CD</code> PHONE_TYPE_CD column of table CLW_PHONE */
    public static final String PHONE_TYPE_CD = "PHONE_TYPE_CD";
    /** <code>PHONE_STATUS_CD</code> PHONE_STATUS_CD column of table CLW_PHONE */
    public static final String PHONE_STATUS_CD = "PHONE_STATUS_CD";
    /** <code>PRIMARY_IND</code> PRIMARY_IND column of table CLW_PHONE */
    public static final String PRIMARY_IND = "PRIMARY_IND";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PHONE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PHONE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PHONE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PHONE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>CONTACT_ID</code> CONTACT_ID column of table CLW_PHONE */
    public static final String CONTACT_ID = "CONTACT_ID";

    /**
     * Constructor.
     */
    public PhoneDataAccess()
    {
    }

    /**
     * Gets a PhoneData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPhoneId The key requested.
     * @return new PhoneData()
     * @throws            SQLException
     */
    public static PhoneData select(Connection pCon, int pPhoneId)
        throws SQLException, DataNotFoundException {
        PhoneData x=null;
        String sql="SELECT PHONE_ID,BUS_ENTITY_ID,USER_ID,PHONE_COUNTRY_CD,PHONE_AREA_CODE,PHONE_NUM,SHORT_DESC,PHONE_TYPE_CD,PHONE_STATUS_CD,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTACT_ID FROM CLW_PHONE WHERE PHONE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPhoneId=" + pPhoneId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPhoneId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PhoneData.createValue();
            
            x.setPhoneId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setPhoneCountryCd(rs.getString(4));
            x.setPhoneAreaCode(rs.getString(5));
            x.setPhoneNum(rs.getString(6));
            x.setShortDesc(rs.getString(7));
            x.setPhoneTypeCd(rs.getString(8));
            x.setPhoneStatusCd(rs.getString(9));
            x.setPrimaryInd(rs.getBoolean(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setContactId(rs.getInt(15));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PHONE_ID :" + pPhoneId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PhoneDataVector object that consists
     * of PhoneData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PhoneDataVector()
     * @throws            SQLException
     */
    public static PhoneDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PhoneData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PHONE.PHONE_ID,CLW_PHONE.BUS_ENTITY_ID,CLW_PHONE.USER_ID,CLW_PHONE.PHONE_COUNTRY_CD,CLW_PHONE.PHONE_AREA_CODE,CLW_PHONE.PHONE_NUM,CLW_PHONE.SHORT_DESC,CLW_PHONE.PHONE_TYPE_CD,CLW_PHONE.PHONE_STATUS_CD,CLW_PHONE.PRIMARY_IND,CLW_PHONE.ADD_DATE,CLW_PHONE.ADD_BY,CLW_PHONE.MOD_DATE,CLW_PHONE.MOD_BY,CLW_PHONE.CONTACT_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PhoneData Object.
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
    *@returns a populated PhoneData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PhoneData x = PhoneData.createValue();
         
         x.setPhoneId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setUserId(rs.getInt(3+offset));
         x.setPhoneCountryCd(rs.getString(4+offset));
         x.setPhoneAreaCode(rs.getString(5+offset));
         x.setPhoneNum(rs.getString(6+offset));
         x.setShortDesc(rs.getString(7+offset));
         x.setPhoneTypeCd(rs.getString(8+offset));
         x.setPhoneStatusCd(rs.getString(9+offset));
         x.setPrimaryInd(rs.getBoolean(10+offset));
         x.setAddDate(rs.getTimestamp(11+offset));
         x.setAddBy(rs.getString(12+offset));
         x.setModDate(rs.getTimestamp(13+offset));
         x.setModBy(rs.getString(14+offset));
         x.setContactId(rs.getInt(15+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PhoneData Object represents.
    */
    public int getColumnCount(){
        return 15;
    }

    /**
     * Gets a PhoneDataVector object that consists
     * of PhoneData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PhoneDataVector()
     * @throws            SQLException
     */
    public static PhoneDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PHONE_ID,BUS_ENTITY_ID,USER_ID,PHONE_COUNTRY_CD,PHONE_AREA_CODE,PHONE_NUM,SHORT_DESC,PHONE_TYPE_CD,PHONE_STATUS_CD,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTACT_ID FROM CLW_PHONE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PHONE.PHONE_ID,CLW_PHONE.BUS_ENTITY_ID,CLW_PHONE.USER_ID,CLW_PHONE.PHONE_COUNTRY_CD,CLW_PHONE.PHONE_AREA_CODE,CLW_PHONE.PHONE_NUM,CLW_PHONE.SHORT_DESC,CLW_PHONE.PHONE_TYPE_CD,CLW_PHONE.PHONE_STATUS_CD,CLW_PHONE.PRIMARY_IND,CLW_PHONE.ADD_DATE,CLW_PHONE.ADD_BY,CLW_PHONE.MOD_DATE,CLW_PHONE.MOD_BY,CLW_PHONE.CONTACT_ID FROM CLW_PHONE");
                where = pCriteria.getSqlClause("CLW_PHONE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PHONE.equals(otherTable)){
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
        PhoneDataVector v = new PhoneDataVector();
        while (rs.next()) {
            PhoneData x = PhoneData.createValue();
            
            x.setPhoneId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setPhoneCountryCd(rs.getString(4));
            x.setPhoneAreaCode(rs.getString(5));
            x.setPhoneNum(rs.getString(6));
            x.setShortDesc(rs.getString(7));
            x.setPhoneTypeCd(rs.getString(8));
            x.setPhoneStatusCd(rs.getString(9));
            x.setPrimaryInd(rs.getBoolean(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setContactId(rs.getInt(15));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a PhoneDataVector object that consists
     * of PhoneData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PhoneData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PhoneDataVector()
     * @throws            SQLException
     */
    public static PhoneDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PhoneDataVector v = new PhoneDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PHONE_ID,BUS_ENTITY_ID,USER_ID,PHONE_COUNTRY_CD,PHONE_AREA_CODE,PHONE_NUM,SHORT_DESC,PHONE_TYPE_CD,PHONE_STATUS_CD,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTACT_ID FROM CLW_PHONE WHERE PHONE_ID IN (");

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
            PhoneData x=null;
            while (rs.next()) {
                // build the object
                x=PhoneData.createValue();
                
                x.setPhoneId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setUserId(rs.getInt(3));
                x.setPhoneCountryCd(rs.getString(4));
                x.setPhoneAreaCode(rs.getString(5));
                x.setPhoneNum(rs.getString(6));
                x.setShortDesc(rs.getString(7));
                x.setPhoneTypeCd(rs.getString(8));
                x.setPhoneStatusCd(rs.getString(9));
                x.setPrimaryInd(rs.getBoolean(10));
                x.setAddDate(rs.getTimestamp(11));
                x.setAddBy(rs.getString(12));
                x.setModDate(rs.getTimestamp(13));
                x.setModBy(rs.getString(14));
                x.setContactId(rs.getInt(15));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a PhoneDataVector object of all
     * PhoneData objects in the database.
     * @param pCon An open database connection.
     * @return new PhoneDataVector()
     * @throws            SQLException
     */
    public static PhoneDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PHONE_ID,BUS_ENTITY_ID,USER_ID,PHONE_COUNTRY_CD,PHONE_AREA_CODE,PHONE_NUM,SHORT_DESC,PHONE_TYPE_CD,PHONE_STATUS_CD,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTACT_ID FROM CLW_PHONE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PhoneDataVector v = new PhoneDataVector();
        PhoneData x = null;
        while (rs.next()) {
            // build the object
            x = PhoneData.createValue();
            
            x.setPhoneId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setPhoneCountryCd(rs.getString(4));
            x.setPhoneAreaCode(rs.getString(5));
            x.setPhoneNum(rs.getString(6));
            x.setShortDesc(rs.getString(7));
            x.setPhoneTypeCd(rs.getString(8));
            x.setPhoneStatusCd(rs.getString(9));
            x.setPrimaryInd(rs.getBoolean(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setContactId(rs.getInt(15));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * PhoneData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PHONE_ID FROM CLW_PHONE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PHONE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PHONE");
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
     * Inserts a PhoneData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PhoneData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new PhoneData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PhoneData insert(Connection pCon, PhoneData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PHONE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PHONE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPhoneId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PHONE (PHONE_ID,BUS_ENTITY_ID,USER_ID,PHONE_COUNTRY_CD,PHONE_AREA_CODE,PHONE_NUM,SHORT_DESC,PHONE_TYPE_CD,PHONE_STATUS_CD,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTACT_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getPhoneId());
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

        pstmt.setString(4,pData.getPhoneCountryCd());
        pstmt.setString(5,pData.getPhoneAreaCode());
        pstmt.setString(6,pData.getPhoneNum());
        pstmt.setString(7,pData.getShortDesc());
        pstmt.setString(8,pData.getPhoneTypeCd());
        pstmt.setString(9,pData.getPhoneStatusCd());
        pstmt.setInt(10, pData.getPrimaryInd()?1:0);
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12,pData.getAddBy());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14,pData.getModBy());
        if (pData.getContactId() == 0) {
            pstmt.setNull(15, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(15,pData.getContactId());
        }


        if (log.isDebugEnabled()) {
            log.debug("SQL:   PHONE_ID="+pData.getPhoneId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   PHONE_COUNTRY_CD="+pData.getPhoneCountryCd());
            log.debug("SQL:   PHONE_AREA_CODE="+pData.getPhoneAreaCode());
            log.debug("SQL:   PHONE_NUM="+pData.getPhoneNum());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   PHONE_TYPE_CD="+pData.getPhoneTypeCd());
            log.debug("SQL:   PHONE_STATUS_CD="+pData.getPhoneStatusCd());
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
        pData.setPhoneId(0);
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
     * Updates a PhoneData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PhoneData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PhoneData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PHONE SET BUS_ENTITY_ID = ?,USER_ID = ?,PHONE_COUNTRY_CD = ?,PHONE_AREA_CODE = ?,PHONE_NUM = ?,SHORT_DESC = ?,PHONE_TYPE_CD = ?,PHONE_STATUS_CD = ?,PRIMARY_IND = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,CONTACT_ID = ? WHERE PHONE_ID = ?";

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

        pstmt.setString(i++,pData.getPhoneCountryCd());
        pstmt.setString(i++,pData.getPhoneAreaCode());
        pstmt.setString(i++,pData.getPhoneNum());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getPhoneTypeCd());
        pstmt.setString(i++,pData.getPhoneStatusCd());
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

        pstmt.setInt(i++,pData.getPhoneId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   PHONE_COUNTRY_CD="+pData.getPhoneCountryCd());
            log.debug("SQL:   PHONE_AREA_CODE="+pData.getPhoneAreaCode());
            log.debug("SQL:   PHONE_NUM="+pData.getPhoneNum());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   PHONE_TYPE_CD="+pData.getPhoneTypeCd());
            log.debug("SQL:   PHONE_STATUS_CD="+pData.getPhoneStatusCd());
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
     * Deletes a PhoneData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPhoneId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPhoneId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PHONE WHERE PHONE_ID = " + pPhoneId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PhoneData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PHONE");
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
     * Inserts a PhoneData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PhoneData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PhoneData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PHONE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PHONE_ID,BUS_ENTITY_ID,USER_ID,PHONE_COUNTRY_CD,PHONE_AREA_CODE,PHONE_NUM,SHORT_DESC,PHONE_TYPE_CD,PHONE_STATUS_CD,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTACT_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPhoneId());
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

        pstmt.setString(4+4,pData.getPhoneCountryCd());
        pstmt.setString(5+4,pData.getPhoneAreaCode());
        pstmt.setString(6+4,pData.getPhoneNum());
        pstmt.setString(7+4,pData.getShortDesc());
        pstmt.setString(8+4,pData.getPhoneTypeCd());
        pstmt.setString(9+4,pData.getPhoneStatusCd());
        pstmt.setBoolean(10+4,pData.getPrimaryInd());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12+4,pData.getAddBy());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14+4,pData.getModBy());
        if (pData.getContactId() == 0) {
            pstmt.setNull(15+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(15+4,pData.getContactId());
        }



        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PhoneData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PhoneData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new PhoneData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PhoneData insert(Connection pCon, PhoneData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PhoneData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PhoneData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PhoneData pData, boolean pLogFl)
        throws SQLException {
        PhoneData oldData = null;
        if(pLogFl) {
          int id = pData.getPhoneId();
          try {
          oldData = PhoneDataAccess.select(pCon,id);
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
     * Deletes a PhoneData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPhoneId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPhoneId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PHONE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PHONE d WHERE PHONE_ID = " + pPhoneId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPhoneId);
        return n;
     }

    /**
     * Deletes PhoneData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PHONE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PHONE d ");
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

