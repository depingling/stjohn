
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        AddressDataAccess
 * Description:  This class is used to build access methods to the CLW_ADDRESS table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AddressDataVector;
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
 * <code>AddressDataAccess</code>
 */
public class AddressDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(AddressDataAccess.class.getName());

    /** <code>CLW_ADDRESS</code> table name */
	/* Primary key: ADDRESS_ID */
	
    public static final String CLW_ADDRESS = "CLW_ADDRESS";
    
    /** <code>ADDRESS_ID</code> ADDRESS_ID column of table CLW_ADDRESS */
    public static final String ADDRESS_ID = "ADDRESS_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_ADDRESS */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>USER_ID</code> USER_ID column of table CLW_ADDRESS */
    public static final String USER_ID = "USER_ID";
    /** <code>NAME1</code> NAME1 column of table CLW_ADDRESS */
    public static final String NAME1 = "NAME1";
    /** <code>NAME2</code> NAME2 column of table CLW_ADDRESS */
    public static final String NAME2 = "NAME2";
    /** <code>ADDRESS1</code> ADDRESS1 column of table CLW_ADDRESS */
    public static final String ADDRESS1 = "ADDRESS1";
    /** <code>ADDRESS2</code> ADDRESS2 column of table CLW_ADDRESS */
    public static final String ADDRESS2 = "ADDRESS2";
    /** <code>ADDRESS3</code> ADDRESS3 column of table CLW_ADDRESS */
    public static final String ADDRESS3 = "ADDRESS3";
    /** <code>ADDRESS4</code> ADDRESS4 column of table CLW_ADDRESS */
    public static final String ADDRESS4 = "ADDRESS4";
    /** <code>CITY</code> CITY column of table CLW_ADDRESS */
    public static final String CITY = "CITY";
    /** <code>STATE_PROVINCE_CD</code> STATE_PROVINCE_CD column of table CLW_ADDRESS */
    public static final String STATE_PROVINCE_CD = "STATE_PROVINCE_CD";
    /** <code>COUNTRY_CD</code> COUNTRY_CD column of table CLW_ADDRESS */
    public static final String COUNTRY_CD = "COUNTRY_CD";
    /** <code>COUNTY_CD</code> COUNTY_CD column of table CLW_ADDRESS */
    public static final String COUNTY_CD = "COUNTY_CD";
    /** <code>POSTAL_CODE</code> POSTAL_CODE column of table CLW_ADDRESS */
    public static final String POSTAL_CODE = "POSTAL_CODE";
    /** <code>ADDRESS_STATUS_CD</code> ADDRESS_STATUS_CD column of table CLW_ADDRESS */
    public static final String ADDRESS_STATUS_CD = "ADDRESS_STATUS_CD";
    /** <code>ADDRESS_TYPE_CD</code> ADDRESS_TYPE_CD column of table CLW_ADDRESS */
    public static final String ADDRESS_TYPE_CD = "ADDRESS_TYPE_CD";
    /** <code>PRIMARY_IND</code> PRIMARY_IND column of table CLW_ADDRESS */
    public static final String PRIMARY_IND = "PRIMARY_IND";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ADDRESS */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ADDRESS */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ADDRESS */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ADDRESS */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public AddressDataAccess()
    {
    }

    /**
     * Gets a AddressData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pAddressId The key requested.
     * @return new AddressData()
     * @throws            SQLException
     */
    public static AddressData select(Connection pCon, int pAddressId)
        throws SQLException, DataNotFoundException {
        AddressData x=null;
        String sql="SELECT ADDRESS_ID,BUS_ENTITY_ID,USER_ID,NAME1,NAME2,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,COUNTY_CD,POSTAL_CODE,ADDRESS_STATUS_CD,ADDRESS_TYPE_CD,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ADDRESS WHERE ADDRESS_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pAddressId=" + pAddressId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pAddressId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=AddressData.createValue();
            
            x.setAddressId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setName1(rs.getString(4));
            x.setName2(rs.getString(5));
            x.setAddress1(rs.getString(6));
            x.setAddress2(rs.getString(7));
            x.setAddress3(rs.getString(8));
            x.setAddress4(rs.getString(9));
            x.setCity(rs.getString(10));
            x.setStateProvinceCd(rs.getString(11));
            x.setCountryCd(rs.getString(12));
            x.setCountyCd(rs.getString(13));
            x.setPostalCode(rs.getString(14));
            x.setAddressStatusCd(rs.getString(15));
            x.setAddressTypeCd(rs.getString(16));
            x.setPrimaryInd(rs.getBoolean(17));
            x.setAddDate(rs.getTimestamp(18));
            x.setAddBy(rs.getString(19));
            x.setModDate(rs.getTimestamp(20));
            x.setModBy(rs.getString(21));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ADDRESS_ID :" + pAddressId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a AddressDataVector object that consists
     * of AddressData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new AddressDataVector()
     * @throws            SQLException
     */
    public static AddressDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a AddressData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ADDRESS.ADDRESS_ID,CLW_ADDRESS.BUS_ENTITY_ID,CLW_ADDRESS.USER_ID,CLW_ADDRESS.NAME1,CLW_ADDRESS.NAME2,CLW_ADDRESS.ADDRESS1,CLW_ADDRESS.ADDRESS2,CLW_ADDRESS.ADDRESS3,CLW_ADDRESS.ADDRESS4,CLW_ADDRESS.CITY,CLW_ADDRESS.STATE_PROVINCE_CD,CLW_ADDRESS.COUNTRY_CD,CLW_ADDRESS.COUNTY_CD,CLW_ADDRESS.POSTAL_CODE,CLW_ADDRESS.ADDRESS_STATUS_CD,CLW_ADDRESS.ADDRESS_TYPE_CD,CLW_ADDRESS.PRIMARY_IND,CLW_ADDRESS.ADD_DATE,CLW_ADDRESS.ADD_BY,CLW_ADDRESS.MOD_DATE,CLW_ADDRESS.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated AddressData Object.
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
    *@returns a populated AddressData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         AddressData x = AddressData.createValue();
         
         x.setAddressId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setUserId(rs.getInt(3+offset));
         x.setName1(rs.getString(4+offset));
         x.setName2(rs.getString(5+offset));
         x.setAddress1(rs.getString(6+offset));
         x.setAddress2(rs.getString(7+offset));
         x.setAddress3(rs.getString(8+offset));
         x.setAddress4(rs.getString(9+offset));
         x.setCity(rs.getString(10+offset));
         x.setStateProvinceCd(rs.getString(11+offset));
         x.setCountryCd(rs.getString(12+offset));
         x.setCountyCd(rs.getString(13+offset));
         x.setPostalCode(rs.getString(14+offset));
         x.setAddressStatusCd(rs.getString(15+offset));
         x.setAddressTypeCd(rs.getString(16+offset));
         x.setPrimaryInd(rs.getBoolean(17+offset));
         x.setAddDate(rs.getTimestamp(18+offset));
         x.setAddBy(rs.getString(19+offset));
         x.setModDate(rs.getTimestamp(20+offset));
         x.setModBy(rs.getString(21+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the AddressData Object represents.
    */
    public int getColumnCount(){
        return 21;
    }

    /**
     * Gets a AddressDataVector object that consists
     * of AddressData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new AddressDataVector()
     * @throws            SQLException
     */
    public static AddressDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ADDRESS_ID,BUS_ENTITY_ID,USER_ID,NAME1,NAME2,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,COUNTY_CD,POSTAL_CODE,ADDRESS_STATUS_CD,ADDRESS_TYPE_CD,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ADDRESS");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ADDRESS.ADDRESS_ID,CLW_ADDRESS.BUS_ENTITY_ID,CLW_ADDRESS.USER_ID,CLW_ADDRESS.NAME1,CLW_ADDRESS.NAME2,CLW_ADDRESS.ADDRESS1,CLW_ADDRESS.ADDRESS2,CLW_ADDRESS.ADDRESS3,CLW_ADDRESS.ADDRESS4,CLW_ADDRESS.CITY,CLW_ADDRESS.STATE_PROVINCE_CD,CLW_ADDRESS.COUNTRY_CD,CLW_ADDRESS.COUNTY_CD,CLW_ADDRESS.POSTAL_CODE,CLW_ADDRESS.ADDRESS_STATUS_CD,CLW_ADDRESS.ADDRESS_TYPE_CD,CLW_ADDRESS.PRIMARY_IND,CLW_ADDRESS.ADD_DATE,CLW_ADDRESS.ADD_BY,CLW_ADDRESS.MOD_DATE,CLW_ADDRESS.MOD_BY FROM CLW_ADDRESS");
                where = pCriteria.getSqlClause("CLW_ADDRESS");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ADDRESS.equals(otherTable)){
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
        AddressDataVector v = new AddressDataVector();
        while (rs.next()) {
            AddressData x = AddressData.createValue();
            
            x.setAddressId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setName1(rs.getString(4));
            x.setName2(rs.getString(5));
            x.setAddress1(rs.getString(6));
            x.setAddress2(rs.getString(7));
            x.setAddress3(rs.getString(8));
            x.setAddress4(rs.getString(9));
            x.setCity(rs.getString(10));
            x.setStateProvinceCd(rs.getString(11));
            x.setCountryCd(rs.getString(12));
            x.setCountyCd(rs.getString(13));
            x.setPostalCode(rs.getString(14));
            x.setAddressStatusCd(rs.getString(15));
            x.setAddressTypeCd(rs.getString(16));
            x.setPrimaryInd(rs.getBoolean(17));
            x.setAddDate(rs.getTimestamp(18));
            x.setAddBy(rs.getString(19));
            x.setModDate(rs.getTimestamp(20));
            x.setModBy(rs.getString(21));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a AddressDataVector object that consists
     * of AddressData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for AddressData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new AddressDataVector()
     * @throws            SQLException
     */
    public static AddressDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        AddressDataVector v = new AddressDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ADDRESS_ID,BUS_ENTITY_ID,USER_ID,NAME1,NAME2,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,COUNTY_CD,POSTAL_CODE,ADDRESS_STATUS_CD,ADDRESS_TYPE_CD,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ADDRESS WHERE ADDRESS_ID IN (");

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
            AddressData x=null;
            while (rs.next()) {
                // build the object
                x=AddressData.createValue();
                
                x.setAddressId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setUserId(rs.getInt(3));
                x.setName1(rs.getString(4));
                x.setName2(rs.getString(5));
                x.setAddress1(rs.getString(6));
                x.setAddress2(rs.getString(7));
                x.setAddress3(rs.getString(8));
                x.setAddress4(rs.getString(9));
                x.setCity(rs.getString(10));
                x.setStateProvinceCd(rs.getString(11));
                x.setCountryCd(rs.getString(12));
                x.setCountyCd(rs.getString(13));
                x.setPostalCode(rs.getString(14));
                x.setAddressStatusCd(rs.getString(15));
                x.setAddressTypeCd(rs.getString(16));
                x.setPrimaryInd(rs.getBoolean(17));
                x.setAddDate(rs.getTimestamp(18));
                x.setAddBy(rs.getString(19));
                x.setModDate(rs.getTimestamp(20));
                x.setModBy(rs.getString(21));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a AddressDataVector object of all
     * AddressData objects in the database.
     * @param pCon An open database connection.
     * @return new AddressDataVector()
     * @throws            SQLException
     */
    public static AddressDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ADDRESS_ID,BUS_ENTITY_ID,USER_ID,NAME1,NAME2,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,COUNTY_CD,POSTAL_CODE,ADDRESS_STATUS_CD,ADDRESS_TYPE_CD,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ADDRESS";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        AddressDataVector v = new AddressDataVector();
        AddressData x = null;
        while (rs.next()) {
            // build the object
            x = AddressData.createValue();
            
            x.setAddressId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setName1(rs.getString(4));
            x.setName2(rs.getString(5));
            x.setAddress1(rs.getString(6));
            x.setAddress2(rs.getString(7));
            x.setAddress3(rs.getString(8));
            x.setAddress4(rs.getString(9));
            x.setCity(rs.getString(10));
            x.setStateProvinceCd(rs.getString(11));
            x.setCountryCd(rs.getString(12));
            x.setCountyCd(rs.getString(13));
            x.setPostalCode(rs.getString(14));
            x.setAddressStatusCd(rs.getString(15));
            x.setAddressTypeCd(rs.getString(16));
            x.setPrimaryInd(rs.getBoolean(17));
            x.setAddDate(rs.getTimestamp(18));
            x.setAddBy(rs.getString(19));
            x.setModDate(rs.getTimestamp(20));
            x.setModBy(rs.getString(21));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * AddressData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ADDRESS_ID FROM CLW_ADDRESS");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ADDRESS");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ADDRESS");
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
     * Inserts a AddressData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AddressData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new AddressData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AddressData insert(Connection pCon, AddressData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ADDRESS_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ADDRESS_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setAddressId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ADDRESS (ADDRESS_ID,BUS_ENTITY_ID,USER_ID,NAME1,NAME2,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,COUNTY_CD,POSTAL_CODE,ADDRESS_STATUS_CD,ADDRESS_TYPE_CD,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getAddressId());
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

        pstmt.setString(4,pData.getName1());
        pstmt.setString(5,pData.getName2());
        pstmt.setString(6,pData.getAddress1());
        pstmt.setString(7,pData.getAddress2());
        pstmt.setString(8,pData.getAddress3());
        pstmt.setString(9,pData.getAddress4());
        pstmt.setString(10,pData.getCity());
        pstmt.setString(11,pData.getStateProvinceCd());
        pstmt.setString(12,pData.getCountryCd());
        pstmt.setString(13,pData.getCountyCd());
        pstmt.setString(14,pData.getPostalCode());
        pstmt.setString(15,pData.getAddressStatusCd());
        pstmt.setString(16,pData.getAddressTypeCd());
        pstmt.setInt(17, pData.getPrimaryInd()?1:0);
        pstmt.setTimestamp(18,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(19,pData.getAddBy());
        pstmt.setTimestamp(20,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(21,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ADDRESS_ID="+pData.getAddressId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   NAME1="+pData.getName1());
            log.debug("SQL:   NAME2="+pData.getName2());
            log.debug("SQL:   ADDRESS1="+pData.getAddress1());
            log.debug("SQL:   ADDRESS2="+pData.getAddress2());
            log.debug("SQL:   ADDRESS3="+pData.getAddress3());
            log.debug("SQL:   ADDRESS4="+pData.getAddress4());
            log.debug("SQL:   CITY="+pData.getCity());
            log.debug("SQL:   STATE_PROVINCE_CD="+pData.getStateProvinceCd());
            log.debug("SQL:   COUNTRY_CD="+pData.getCountryCd());
            log.debug("SQL:   COUNTY_CD="+pData.getCountyCd());
            log.debug("SQL:   POSTAL_CODE="+pData.getPostalCode());
            log.debug("SQL:   ADDRESS_STATUS_CD="+pData.getAddressStatusCd());
            log.debug("SQL:   ADDRESS_TYPE_CD="+pData.getAddressTypeCd());
            log.debug("SQL:   PRIMARY_IND="+pData.getPrimaryInd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
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
        pData.setAddressId(0);
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
     * Updates a AddressData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AddressData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AddressData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ADDRESS SET BUS_ENTITY_ID = ?,USER_ID = ?,NAME1 = ?,NAME2 = ?,ADDRESS1 = ?,ADDRESS2 = ?,ADDRESS3 = ?,ADDRESS4 = ?,CITY = ?,STATE_PROVINCE_CD = ?,COUNTRY_CD = ?,COUNTY_CD = ?,POSTAL_CODE = ?,ADDRESS_STATUS_CD = ?,ADDRESS_TYPE_CD = ?,PRIMARY_IND = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ADDRESS_ID = ?";

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

        pstmt.setString(i++,pData.getName1());
        pstmt.setString(i++,pData.getName2());
        pstmt.setString(i++,pData.getAddress1());
        pstmt.setString(i++,pData.getAddress2());
        pstmt.setString(i++,pData.getAddress3());
        pstmt.setString(i++,pData.getAddress4());
        pstmt.setString(i++,pData.getCity());
        pstmt.setString(i++,pData.getStateProvinceCd());
        pstmt.setString(i++,pData.getCountryCd());
        pstmt.setString(i++,pData.getCountyCd());
        pstmt.setString(i++,pData.getPostalCode());
        pstmt.setString(i++,pData.getAddressStatusCd());
        pstmt.setString(i++,pData.getAddressTypeCd());
        pstmt.setInt(i++, pData.getPrimaryInd()?1:0);
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getAddressId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   NAME1="+pData.getName1());
            log.debug("SQL:   NAME2="+pData.getName2());
            log.debug("SQL:   ADDRESS1="+pData.getAddress1());
            log.debug("SQL:   ADDRESS2="+pData.getAddress2());
            log.debug("SQL:   ADDRESS3="+pData.getAddress3());
            log.debug("SQL:   ADDRESS4="+pData.getAddress4());
            log.debug("SQL:   CITY="+pData.getCity());
            log.debug("SQL:   STATE_PROVINCE_CD="+pData.getStateProvinceCd());
            log.debug("SQL:   COUNTRY_CD="+pData.getCountryCd());
            log.debug("SQL:   COUNTY_CD="+pData.getCountyCd());
            log.debug("SQL:   POSTAL_CODE="+pData.getPostalCode());
            log.debug("SQL:   ADDRESS_STATUS_CD="+pData.getAddressStatusCd());
            log.debug("SQL:   ADDRESS_TYPE_CD="+pData.getAddressTypeCd());
            log.debug("SQL:   PRIMARY_IND="+pData.getPrimaryInd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
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
     * Deletes a AddressData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAddressId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAddressId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ADDRESS WHERE ADDRESS_ID = " + pAddressId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes AddressData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ADDRESS");
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
     * Inserts a AddressData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AddressData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, AddressData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ADDRESS (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ADDRESS_ID,BUS_ENTITY_ID,USER_ID,NAME1,NAME2,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,COUNTY_CD,POSTAL_CODE,ADDRESS_STATUS_CD,ADDRESS_TYPE_CD,PRIMARY_IND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getAddressId());
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

        pstmt.setString(4+4,pData.getName1());
        pstmt.setString(5+4,pData.getName2());
        pstmt.setString(6+4,pData.getAddress1());
        pstmt.setString(7+4,pData.getAddress2());
        pstmt.setString(8+4,pData.getAddress3());
        pstmt.setString(9+4,pData.getAddress4());
        pstmt.setString(10+4,pData.getCity());
        pstmt.setString(11+4,pData.getStateProvinceCd());
        pstmt.setString(12+4,pData.getCountryCd());
        pstmt.setString(13+4,pData.getCountyCd());
        pstmt.setString(14+4,pData.getPostalCode());
        pstmt.setString(15+4,pData.getAddressStatusCd());
        pstmt.setString(16+4,pData.getAddressTypeCd());
        pstmt.setBoolean(17+4,pData.getPrimaryInd());
        pstmt.setTimestamp(18+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(19+4,pData.getAddBy());
        pstmt.setTimestamp(20+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(21+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a AddressData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AddressData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new AddressData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AddressData insert(Connection pCon, AddressData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a AddressData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AddressData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AddressData pData, boolean pLogFl)
        throws SQLException {
        AddressData oldData = null;
        if(pLogFl) {
          int id = pData.getAddressId();
          try {
          oldData = AddressDataAccess.select(pCon,id);
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
     * Deletes a AddressData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAddressId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAddressId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ADDRESS SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ADDRESS d WHERE ADDRESS_ID = " + pAddressId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pAddressId);
        return n;
     }

    /**
     * Deletes AddressData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ADDRESS SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ADDRESS d ");
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

