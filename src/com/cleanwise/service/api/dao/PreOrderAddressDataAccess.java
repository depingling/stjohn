
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PreOrderAddressDataAccess
 * Description:  This class is used to build access methods to the CLW_PRE_ORDER_ADDRESS table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PreOrderAddressData;
import com.cleanwise.service.api.value.PreOrderAddressDataVector;
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
 * <code>PreOrderAddressDataAccess</code>
 */
public class PreOrderAddressDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PreOrderAddressDataAccess.class.getName());

    /** <code>CLW_PRE_ORDER_ADDRESS</code> table name */
	/* Primary key: PRE_ORDER_ADDRESS_ID */
	
    public static final String CLW_PRE_ORDER_ADDRESS = "CLW_PRE_ORDER_ADDRESS";
    
    /** <code>PRE_ORDER_ADDRESS_ID</code> PRE_ORDER_ADDRESS_ID column of table CLW_PRE_ORDER_ADDRESS */
    public static final String PRE_ORDER_ADDRESS_ID = "PRE_ORDER_ADDRESS_ID";
    /** <code>PRE_ORDER_ID</code> PRE_ORDER_ID column of table CLW_PRE_ORDER_ADDRESS */
    public static final String PRE_ORDER_ID = "PRE_ORDER_ID";
    /** <code>ADDRESS_TYPE_CD</code> ADDRESS_TYPE_CD column of table CLW_PRE_ORDER_ADDRESS */
    public static final String ADDRESS_TYPE_CD = "ADDRESS_TYPE_CD";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_PRE_ORDER_ADDRESS */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>ADDRESS1</code> ADDRESS1 column of table CLW_PRE_ORDER_ADDRESS */
    public static final String ADDRESS1 = "ADDRESS1";
    /** <code>ADDRESS2</code> ADDRESS2 column of table CLW_PRE_ORDER_ADDRESS */
    public static final String ADDRESS2 = "ADDRESS2";
    /** <code>ADDRESS3</code> ADDRESS3 column of table CLW_PRE_ORDER_ADDRESS */
    public static final String ADDRESS3 = "ADDRESS3";
    /** <code>ADDRESS4</code> ADDRESS4 column of table CLW_PRE_ORDER_ADDRESS */
    public static final String ADDRESS4 = "ADDRESS4";
    /** <code>CITY</code> CITY column of table CLW_PRE_ORDER_ADDRESS */
    public static final String CITY = "CITY";
    /** <code>STATE_PROVINCE_CD</code> STATE_PROVINCE_CD column of table CLW_PRE_ORDER_ADDRESS */
    public static final String STATE_PROVINCE_CD = "STATE_PROVINCE_CD";
    /** <code>COUNTRY_CD</code> COUNTRY_CD column of table CLW_PRE_ORDER_ADDRESS */
    public static final String COUNTRY_CD = "COUNTRY_CD";
    /** <code>COUNTY_CD</code> COUNTY_CD column of table CLW_PRE_ORDER_ADDRESS */
    public static final String COUNTY_CD = "COUNTY_CD";
    /** <code>POSTAL_CODE</code> POSTAL_CODE column of table CLW_PRE_ORDER_ADDRESS */
    public static final String POSTAL_CODE = "POSTAL_CODE";

    /**
     * Constructor.
     */
    public PreOrderAddressDataAccess()
    {
    }

    /**
     * Gets a PreOrderAddressData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPreOrderAddressId The key requested.
     * @return new PreOrderAddressData()
     * @throws            SQLException
     */
    public static PreOrderAddressData select(Connection pCon, int pPreOrderAddressId)
        throws SQLException, DataNotFoundException {
        PreOrderAddressData x=null;
        String sql="SELECT PRE_ORDER_ADDRESS_ID,PRE_ORDER_ID,ADDRESS_TYPE_CD,SHORT_DESC,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,COUNTY_CD,POSTAL_CODE FROM CLW_PRE_ORDER_ADDRESS WHERE PRE_ORDER_ADDRESS_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPreOrderAddressId=" + pPreOrderAddressId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPreOrderAddressId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PreOrderAddressData.createValue();
            
            x.setPreOrderAddressId(rs.getInt(1));
            x.setPreOrderId(rs.getInt(2));
            x.setAddressTypeCd(rs.getString(3));
            x.setShortDesc(rs.getString(4));
            x.setAddress1(rs.getString(5));
            x.setAddress2(rs.getString(6));
            x.setAddress3(rs.getString(7));
            x.setAddress4(rs.getString(8));
            x.setCity(rs.getString(9));
            x.setStateProvinceCd(rs.getString(10));
            x.setCountryCd(rs.getString(11));
            x.setCountyCd(rs.getString(12));
            x.setPostalCode(rs.getString(13));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PRE_ORDER_ADDRESS_ID :" + pPreOrderAddressId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PreOrderAddressDataVector object that consists
     * of PreOrderAddressData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PreOrderAddressDataVector()
     * @throws            SQLException
     */
    public static PreOrderAddressDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PreOrderAddressData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PRE_ORDER_ADDRESS.PRE_ORDER_ADDRESS_ID,CLW_PRE_ORDER_ADDRESS.PRE_ORDER_ID,CLW_PRE_ORDER_ADDRESS.ADDRESS_TYPE_CD,CLW_PRE_ORDER_ADDRESS.SHORT_DESC,CLW_PRE_ORDER_ADDRESS.ADDRESS1,CLW_PRE_ORDER_ADDRESS.ADDRESS2,CLW_PRE_ORDER_ADDRESS.ADDRESS3,CLW_PRE_ORDER_ADDRESS.ADDRESS4,CLW_PRE_ORDER_ADDRESS.CITY,CLW_PRE_ORDER_ADDRESS.STATE_PROVINCE_CD,CLW_PRE_ORDER_ADDRESS.COUNTRY_CD,CLW_PRE_ORDER_ADDRESS.COUNTY_CD,CLW_PRE_ORDER_ADDRESS.POSTAL_CODE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PreOrderAddressData Object.
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
    *@returns a populated PreOrderAddressData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PreOrderAddressData x = PreOrderAddressData.createValue();
         
         x.setPreOrderAddressId(rs.getInt(1+offset));
         x.setPreOrderId(rs.getInt(2+offset));
         x.setAddressTypeCd(rs.getString(3+offset));
         x.setShortDesc(rs.getString(4+offset));
         x.setAddress1(rs.getString(5+offset));
         x.setAddress2(rs.getString(6+offset));
         x.setAddress3(rs.getString(7+offset));
         x.setAddress4(rs.getString(8+offset));
         x.setCity(rs.getString(9+offset));
         x.setStateProvinceCd(rs.getString(10+offset));
         x.setCountryCd(rs.getString(11+offset));
         x.setCountyCd(rs.getString(12+offset));
         x.setPostalCode(rs.getString(13+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PreOrderAddressData Object represents.
    */
    public int getColumnCount(){
        return 13;
    }

    /**
     * Gets a PreOrderAddressDataVector object that consists
     * of PreOrderAddressData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PreOrderAddressDataVector()
     * @throws            SQLException
     */
    public static PreOrderAddressDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PRE_ORDER_ADDRESS_ID,PRE_ORDER_ID,ADDRESS_TYPE_CD,SHORT_DESC,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,COUNTY_CD,POSTAL_CODE FROM CLW_PRE_ORDER_ADDRESS");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PRE_ORDER_ADDRESS.PRE_ORDER_ADDRESS_ID,CLW_PRE_ORDER_ADDRESS.PRE_ORDER_ID,CLW_PRE_ORDER_ADDRESS.ADDRESS_TYPE_CD,CLW_PRE_ORDER_ADDRESS.SHORT_DESC,CLW_PRE_ORDER_ADDRESS.ADDRESS1,CLW_PRE_ORDER_ADDRESS.ADDRESS2,CLW_PRE_ORDER_ADDRESS.ADDRESS3,CLW_PRE_ORDER_ADDRESS.ADDRESS4,CLW_PRE_ORDER_ADDRESS.CITY,CLW_PRE_ORDER_ADDRESS.STATE_PROVINCE_CD,CLW_PRE_ORDER_ADDRESS.COUNTRY_CD,CLW_PRE_ORDER_ADDRESS.COUNTY_CD,CLW_PRE_ORDER_ADDRESS.POSTAL_CODE FROM CLW_PRE_ORDER_ADDRESS");
                where = pCriteria.getSqlClause("CLW_PRE_ORDER_ADDRESS");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRE_ORDER_ADDRESS.equals(otherTable)){
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
        PreOrderAddressDataVector v = new PreOrderAddressDataVector();
        while (rs.next()) {
            PreOrderAddressData x = PreOrderAddressData.createValue();
            
            x.setPreOrderAddressId(rs.getInt(1));
            x.setPreOrderId(rs.getInt(2));
            x.setAddressTypeCd(rs.getString(3));
            x.setShortDesc(rs.getString(4));
            x.setAddress1(rs.getString(5));
            x.setAddress2(rs.getString(6));
            x.setAddress3(rs.getString(7));
            x.setAddress4(rs.getString(8));
            x.setCity(rs.getString(9));
            x.setStateProvinceCd(rs.getString(10));
            x.setCountryCd(rs.getString(11));
            x.setCountyCd(rs.getString(12));
            x.setPostalCode(rs.getString(13));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a PreOrderAddressDataVector object that consists
     * of PreOrderAddressData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PreOrderAddressData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PreOrderAddressDataVector()
     * @throws            SQLException
     */
    public static PreOrderAddressDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PreOrderAddressDataVector v = new PreOrderAddressDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PRE_ORDER_ADDRESS_ID,PRE_ORDER_ID,ADDRESS_TYPE_CD,SHORT_DESC,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,COUNTY_CD,POSTAL_CODE FROM CLW_PRE_ORDER_ADDRESS WHERE PRE_ORDER_ADDRESS_ID IN (");

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
            PreOrderAddressData x=null;
            while (rs.next()) {
                // build the object
                x=PreOrderAddressData.createValue();
                
                x.setPreOrderAddressId(rs.getInt(1));
                x.setPreOrderId(rs.getInt(2));
                x.setAddressTypeCd(rs.getString(3));
                x.setShortDesc(rs.getString(4));
                x.setAddress1(rs.getString(5));
                x.setAddress2(rs.getString(6));
                x.setAddress3(rs.getString(7));
                x.setAddress4(rs.getString(8));
                x.setCity(rs.getString(9));
                x.setStateProvinceCd(rs.getString(10));
                x.setCountryCd(rs.getString(11));
                x.setCountyCd(rs.getString(12));
                x.setPostalCode(rs.getString(13));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a PreOrderAddressDataVector object of all
     * PreOrderAddressData objects in the database.
     * @param pCon An open database connection.
     * @return new PreOrderAddressDataVector()
     * @throws            SQLException
     */
    public static PreOrderAddressDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PRE_ORDER_ADDRESS_ID,PRE_ORDER_ID,ADDRESS_TYPE_CD,SHORT_DESC,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,COUNTY_CD,POSTAL_CODE FROM CLW_PRE_ORDER_ADDRESS";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PreOrderAddressDataVector v = new PreOrderAddressDataVector();
        PreOrderAddressData x = null;
        while (rs.next()) {
            // build the object
            x = PreOrderAddressData.createValue();
            
            x.setPreOrderAddressId(rs.getInt(1));
            x.setPreOrderId(rs.getInt(2));
            x.setAddressTypeCd(rs.getString(3));
            x.setShortDesc(rs.getString(4));
            x.setAddress1(rs.getString(5));
            x.setAddress2(rs.getString(6));
            x.setAddress3(rs.getString(7));
            x.setAddress4(rs.getString(8));
            x.setCity(rs.getString(9));
            x.setStateProvinceCd(rs.getString(10));
            x.setCountryCd(rs.getString(11));
            x.setCountyCd(rs.getString(12));
            x.setPostalCode(rs.getString(13));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * PreOrderAddressData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PRE_ORDER_ADDRESS_ID FROM CLW_PRE_ORDER_ADDRESS");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRE_ORDER_ADDRESS");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRE_ORDER_ADDRESS");
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
     * Inserts a PreOrderAddressData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderAddressData object to insert.
     * @return new PreOrderAddressData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PreOrderAddressData insert(Connection pCon, PreOrderAddressData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PRE_ORDER_ADDRESS_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PRE_ORDER_ADDRESS_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPreOrderAddressId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PRE_ORDER_ADDRESS (PRE_ORDER_ADDRESS_ID,PRE_ORDER_ID,ADDRESS_TYPE_CD,SHORT_DESC,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,COUNTY_CD,POSTAL_CODE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pstmt.setInt(1,pData.getPreOrderAddressId());
        pstmt.setInt(2,pData.getPreOrderId());
        pstmt.setString(3,pData.getAddressTypeCd());
        pstmt.setString(4,pData.getShortDesc());
        pstmt.setString(5,pData.getAddress1());
        pstmt.setString(6,pData.getAddress2());
        pstmt.setString(7,pData.getAddress3());
        pstmt.setString(8,pData.getAddress4());
        pstmt.setString(9,pData.getCity());
        pstmt.setString(10,pData.getStateProvinceCd());
        pstmt.setString(11,pData.getCountryCd());
        pstmt.setString(12,pData.getCountyCd());
        pstmt.setString(13,pData.getPostalCode());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRE_ORDER_ADDRESS_ID="+pData.getPreOrderAddressId());
            log.debug("SQL:   PRE_ORDER_ID="+pData.getPreOrderId());
            log.debug("SQL:   ADDRESS_TYPE_CD="+pData.getAddressTypeCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   ADDRESS1="+pData.getAddress1());
            log.debug("SQL:   ADDRESS2="+pData.getAddress2());
            log.debug("SQL:   ADDRESS3="+pData.getAddress3());
            log.debug("SQL:   ADDRESS4="+pData.getAddress4());
            log.debug("SQL:   CITY="+pData.getCity());
            log.debug("SQL:   STATE_PROVINCE_CD="+pData.getStateProvinceCd());
            log.debug("SQL:   COUNTRY_CD="+pData.getCountryCd());
            log.debug("SQL:   COUNTY_CD="+pData.getCountyCd());
            log.debug("SQL:   POSTAL_CODE="+pData.getPostalCode());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setPreOrderAddressId(0);
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
     * Updates a PreOrderAddressData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderAddressData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PreOrderAddressData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PRE_ORDER_ADDRESS SET PRE_ORDER_ID = ?,ADDRESS_TYPE_CD = ?,SHORT_DESC = ?,ADDRESS1 = ?,ADDRESS2 = ?,ADDRESS3 = ?,ADDRESS4 = ?,CITY = ?,STATE_PROVINCE_CD = ?,COUNTRY_CD = ?,COUNTY_CD = ?,POSTAL_CODE = ? WHERE PRE_ORDER_ADDRESS_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setInt(i++,pData.getPreOrderId());
        pstmt.setString(i++,pData.getAddressTypeCd());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getAddress1());
        pstmt.setString(i++,pData.getAddress2());
        pstmt.setString(i++,pData.getAddress3());
        pstmt.setString(i++,pData.getAddress4());
        pstmt.setString(i++,pData.getCity());
        pstmt.setString(i++,pData.getStateProvinceCd());
        pstmt.setString(i++,pData.getCountryCd());
        pstmt.setString(i++,pData.getCountyCd());
        pstmt.setString(i++,pData.getPostalCode());
        pstmt.setInt(i++,pData.getPreOrderAddressId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRE_ORDER_ID="+pData.getPreOrderId());
            log.debug("SQL:   ADDRESS_TYPE_CD="+pData.getAddressTypeCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   ADDRESS1="+pData.getAddress1());
            log.debug("SQL:   ADDRESS2="+pData.getAddress2());
            log.debug("SQL:   ADDRESS3="+pData.getAddress3());
            log.debug("SQL:   ADDRESS4="+pData.getAddress4());
            log.debug("SQL:   CITY="+pData.getCity());
            log.debug("SQL:   STATE_PROVINCE_CD="+pData.getStateProvinceCd());
            log.debug("SQL:   COUNTRY_CD="+pData.getCountryCd());
            log.debug("SQL:   COUNTY_CD="+pData.getCountyCd());
            log.debug("SQL:   POSTAL_CODE="+pData.getPostalCode());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a PreOrderAddressData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPreOrderAddressId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPreOrderAddressId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PRE_ORDER_ADDRESS WHERE PRE_ORDER_ADDRESS_ID = " + pPreOrderAddressId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PreOrderAddressData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PRE_ORDER_ADDRESS");
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
     * Inserts a PreOrderAddressData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderAddressData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PreOrderAddressData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PRE_ORDER_ADDRESS (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PRE_ORDER_ADDRESS_ID,PRE_ORDER_ID,ADDRESS_TYPE_CD,SHORT_DESC,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,COUNTY_CD,POSTAL_CODE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPreOrderAddressId());
        pstmt.setInt(2+4,pData.getPreOrderId());
        pstmt.setString(3+4,pData.getAddressTypeCd());
        pstmt.setString(4+4,pData.getShortDesc());
        pstmt.setString(5+4,pData.getAddress1());
        pstmt.setString(6+4,pData.getAddress2());
        pstmt.setString(7+4,pData.getAddress3());
        pstmt.setString(8+4,pData.getAddress4());
        pstmt.setString(9+4,pData.getCity());
        pstmt.setString(10+4,pData.getStateProvinceCd());
        pstmt.setString(11+4,pData.getCountryCd());
        pstmt.setString(12+4,pData.getCountyCd());
        pstmt.setString(13+4,pData.getPostalCode());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PreOrderAddressData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderAddressData object to insert.
     * @param pLogFl  Creates record in log table if true
     * @return new PreOrderAddressData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PreOrderAddressData insert(Connection pCon, PreOrderAddressData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PreOrderAddressData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderAddressData object to update. 
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PreOrderAddressData pData, boolean pLogFl)
        throws SQLException {
        PreOrderAddressData oldData = null;
        if(pLogFl) {
          int id = pData.getPreOrderAddressId();
          try {
          oldData = PreOrderAddressDataAccess.select(pCon,id);
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
     * Deletes a PreOrderAddressData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPreOrderAddressId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPreOrderAddressId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PRE_ORDER_ADDRESS SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRE_ORDER_ADDRESS d WHERE PRE_ORDER_ADDRESS_ID = " + pPreOrderAddressId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPreOrderAddressId);
        return n;
     }

    /**
     * Deletes PreOrderAddressData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PRE_ORDER_ADDRESS SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRE_ORDER_ADDRESS d ");
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

