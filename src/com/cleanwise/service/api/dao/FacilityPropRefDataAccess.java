
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        FacilityPropRefDataAccess
 * Description:  This class is used to build access methods to the CLW_FACILITY_PROP_REF table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.FacilityPropRefData;
import com.cleanwise.service.api.value.FacilityPropRefDataVector;
import com.cleanwise.service.api.framework.DataAccess;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>FacilityPropRefDataAccess</code>
 */
public class FacilityPropRefDataAccess implements DataAccess
{
    private static Category log = Category.getInstance(FacilityPropRefDataAccess.class.getName());

    /** <code>CLW_FACILITY_PROP_REF</code> table name */
    public static final String CLW_FACILITY_PROP_REF = "CLW_FACILITY_PROP_REF";
    
    /** <code>FACILITY_PROP_REF_ID</code> FACILITY_PROP_REF_ID column of table CLW_FACILITY_PROP_REF */
    public static final String FACILITY_PROP_REF_ID = "FACILITY_PROP_REF_ID";
    /** <code>DEC_LENGTH</code> DEC_LENGTH column of table CLW_FACILITY_PROP_REF */
    public static final String DEC_LENGTH = "DEC_LENGTH";
    /** <code>FACILITY_PROPERTY_TYPE_CD</code> FACILITY_PROPERTY_TYPE_CD column of table CLW_FACILITY_PROP_REF */
    public static final String FACILITY_PROPERTY_TYPE_CD = "FACILITY_PROPERTY_TYPE_CD";
    /** <code>JAVA_TYPE_CD</code> JAVA_TYPE_CD column of table CLW_FACILITY_PROP_REF */
    public static final String JAVA_TYPE_CD = "JAVA_TYPE_CD";
    /** <code>LENGTH</code> LENGTH column of table CLW_FACILITY_PROP_REF */
    public static final String LENGTH = "LENGTH";
    /** <code>PROPERTY_NAME</code> PROPERTY_NAME column of table CLW_FACILITY_PROP_REF */
    public static final String PROPERTY_NAME = "PROPERTY_NAME";

    /**
     * Constructor.
     */
    public FacilityPropRefDataAccess()
    {
    }

    /**
     * Gets a FacilityPropRefData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pFacilityPropRefId The key requested.
     * @return new FacilityPropRefData()
     * @throws            SQLException
     */
    public static FacilityPropRefData select(Connection pCon, int pFacilityPropRefId)
        throws SQLException, DataNotFoundException {
        FacilityPropRefData x=null;
        String sql="SELECT FACILITY_PROP_REF_ID,DEC_LENGTH,FACILITY_PROPERTY_TYPE_CD,JAVA_TYPE_CD,LENGTH,PROPERTY_NAME FROM CLW_FACILITY_PROP_REF WHERE FACILITY_PROP_REF_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pFacilityPropRefId=" + pFacilityPropRefId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pFacilityPropRefId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=FacilityPropRefData.createValue();
            
            x.setFacilityPropRefId(rs.getInt(1));
            x.setDecLength(rs.getInt(2));
            x.setFacilityPropertyTypeCd(rs.getString(3));
            x.setJavaTypeCd(rs.getString(4));
            x.setLength(rs.getInt(5));
            x.setPropertyName(rs.getString(6));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("FACILITY_PROP_REF_ID :" + pFacilityPropRefId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a FacilityPropRefDataVector object that consists
     * of FacilityPropRefData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new FacilityPropRefDataVector()
     * @throws            SQLException
     */
    public static FacilityPropRefDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a FacilityPropRefData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_FACILITY_PROP_REF.FACILITY_PROP_REF_ID,CLW_FACILITY_PROP_REF.DEC_LENGTH,CLW_FACILITY_PROP_REF.FACILITY_PROPERTY_TYPE_CD,CLW_FACILITY_PROP_REF.JAVA_TYPE_CD,CLW_FACILITY_PROP_REF.LENGTH,CLW_FACILITY_PROP_REF.PROPERTY_NAME";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated FacilityPropRefData Object.
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
    *@returns a populated FacilityPropRefData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         FacilityPropRefData x = FacilityPropRefData.createValue();
         
         x.setFacilityPropRefId(rs.getInt(1+offset));
         x.setDecLength(rs.getInt(2+offset));
         x.setFacilityPropertyTypeCd(rs.getString(3+offset));
         x.setJavaTypeCd(rs.getString(4+offset));
         x.setLength(rs.getInt(5+offset));
         x.setPropertyName(rs.getString(6+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the FacilityPropRefData Object represents.
    */
    public int getColumnCount(){
        return 6;
    }

    /**
     * Gets a FacilityPropRefDataVector object that consists
     * of FacilityPropRefData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new FacilityPropRefDataVector()
     * @throws            SQLException
     */
    public static FacilityPropRefDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT FACILITY_PROP_REF_ID,DEC_LENGTH,FACILITY_PROPERTY_TYPE_CD,JAVA_TYPE_CD,LENGTH,PROPERTY_NAME FROM CLW_FACILITY_PROP_REF");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_FACILITY_PROP_REF.FACILITY_PROP_REF_ID,CLW_FACILITY_PROP_REF.DEC_LENGTH,CLW_FACILITY_PROP_REF.FACILITY_PROPERTY_TYPE_CD,CLW_FACILITY_PROP_REF.JAVA_TYPE_CD,CLW_FACILITY_PROP_REF.LENGTH,CLW_FACILITY_PROP_REF.PROPERTY_NAME FROM CLW_FACILITY_PROP_REF");
                where = pCriteria.getSqlClause("CLW_FACILITY_PROP_REF");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
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
        FacilityPropRefDataVector v = new FacilityPropRefDataVector();
        while (rs.next()) {
            FacilityPropRefData x = FacilityPropRefData.createValue();
            
            x.setFacilityPropRefId(rs.getInt(1));
            x.setDecLength(rs.getInt(2));
            x.setFacilityPropertyTypeCd(rs.getString(3));
            x.setJavaTypeCd(rs.getString(4));
            x.setLength(rs.getInt(5));
            x.setPropertyName(rs.getString(6));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a FacilityPropRefDataVector object that consists
     * of FacilityPropRefData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for FacilityPropRefData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new FacilityPropRefDataVector()
     * @throws            SQLException
     */
    public static FacilityPropRefDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        FacilityPropRefDataVector v = new FacilityPropRefDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT FACILITY_PROP_REF_ID,DEC_LENGTH,FACILITY_PROPERTY_TYPE_CD,JAVA_TYPE_CD,LENGTH,PROPERTY_NAME FROM CLW_FACILITY_PROP_REF WHERE FACILITY_PROP_REF_ID IN (");

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
            FacilityPropRefData x=null;
            while (rs.next()) {
                // build the object
                x=FacilityPropRefData.createValue();
                
                x.setFacilityPropRefId(rs.getInt(1));
                x.setDecLength(rs.getInt(2));
                x.setFacilityPropertyTypeCd(rs.getString(3));
                x.setJavaTypeCd(rs.getString(4));
                x.setLength(rs.getInt(5));
                x.setPropertyName(rs.getString(6));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a FacilityPropRefDataVector object of all
     * FacilityPropRefData objects in the database.
     * @param pCon An open database connection.
     * @return new FacilityPropRefDataVector()
     * @throws            SQLException
     */
    public static FacilityPropRefDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT FACILITY_PROP_REF_ID,DEC_LENGTH,FACILITY_PROPERTY_TYPE_CD,JAVA_TYPE_CD,LENGTH,PROPERTY_NAME FROM CLW_FACILITY_PROP_REF";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        FacilityPropRefDataVector v = new FacilityPropRefDataVector();
        FacilityPropRefData x = null;
        while (rs.next()) {
            // build the object
            x = FacilityPropRefData.createValue();
            
            x.setFacilityPropRefId(rs.getInt(1));
            x.setDecLength(rs.getInt(2));
            x.setFacilityPropertyTypeCd(rs.getString(3));
            x.setJavaTypeCd(rs.getString(4));
            x.setLength(rs.getInt(5));
            x.setPropertyName(rs.getString(6));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * FacilityPropRefData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT FACILITY_PROP_REF_ID FROM CLW_FACILITY_PROP_REF");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FACILITY_PROP_REF");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FACILITY_PROP_REF");
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
     * Inserts a FacilityPropRefData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityPropRefData object to insert.
     * @return new FacilityPropRefData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FacilityPropRefData insert(Connection pCon, FacilityPropRefData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_FACILITY_PROP_REF_SEQ.NEXTVAL FROM DUAL");
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_FACILITY_PROP_REF_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setFacilityPropRefId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_FACILITY_PROP_REF (FACILITY_PROP_REF_ID,DEC_LENGTH,FACILITY_PROPERTY_TYPE_CD,JAVA_TYPE_CD,LENGTH,PROPERTY_NAME) VALUES(?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setInt(1,pData.getFacilityPropRefId());
        pstmt.setInt(2,pData.getDecLength());
        pstmt.setString(3,pData.getFacilityPropertyTypeCd());
        pstmt.setString(4,pData.getJavaTypeCd());
        pstmt.setInt(5,pData.getLength());
        pstmt.setString(6,pData.getPropertyName());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FACILITY_PROP_REF_ID="+pData.getFacilityPropRefId());
            log.debug("SQL:   DEC_LENGTH="+pData.getDecLength());
            log.debug("SQL:   FACILITY_PROPERTY_TYPE_CD="+pData.getFacilityPropertyTypeCd());
            log.debug("SQL:   JAVA_TYPE_CD="+pData.getJavaTypeCd());
            log.debug("SQL:   LENGTH="+pData.getLength());
            log.debug("SQL:   PROPERTY_NAME="+pData.getPropertyName());
            log.debug("SQL: " + sql);
        }

        pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);
        return pData;
    }

    /**
     * Updates a FacilityPropRefData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityPropRefData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FacilityPropRefData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_FACILITY_PROP_REF SET DEC_LENGTH = ?,FACILITY_PROPERTY_TYPE_CD = ?,JAVA_TYPE_CD = ?,LENGTH = ?,PROPERTY_NAME = ? WHERE FACILITY_PROP_REF_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setInt(i++,pData.getDecLength());
        pstmt.setString(i++,pData.getFacilityPropertyTypeCd());
        pstmt.setString(i++,pData.getJavaTypeCd());
        pstmt.setInt(i++,pData.getLength());
        pstmt.setString(i++,pData.getPropertyName());
        pstmt.setInt(i++,pData.getFacilityPropRefId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   DEC_LENGTH="+pData.getDecLength());
            log.debug("SQL:   FACILITY_PROPERTY_TYPE_CD="+pData.getFacilityPropertyTypeCd());
            log.debug("SQL:   JAVA_TYPE_CD="+pData.getJavaTypeCd());
            log.debug("SQL:   LENGTH="+pData.getLength());
            log.debug("SQL:   PROPERTY_NAME="+pData.getPropertyName());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a FacilityPropRefData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFacilityPropRefId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFacilityPropRefId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_FACILITY_PROP_REF WHERE FACILITY_PROP_REF_ID = " + pFacilityPropRefId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes FacilityPropRefData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_FACILITY_PROP_REF");
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
}

