
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        FacilityPropValueRefDataAccess
 * Description:  This class is used to build access methods to the CLW_FACILITY_PROP_VALUE_REF table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.FacilityPropValueRefData;
import com.cleanwise.service.api.value.FacilityPropValueRefDataVector;
import com.cleanwise.service.api.framework.DataAccess;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>FacilityPropValueRefDataAccess</code>
 */
public class FacilityPropValueRefDataAccess implements DataAccess
{
    private static Category log = Category.getInstance(FacilityPropValueRefDataAccess.class.getName());

    /** <code>CLW_FACILITY_PROP_VALUE_REF</code> table name */
    public static final String CLW_FACILITY_PROP_VALUE_REF = "CLW_FACILITY_PROP_VALUE_REF";
    
    /** <code>FACILITY_PROP_REF_ID</code> FACILITY_PROP_REF_ID column of table CLW_FACILITY_PROP_VALUE_REF */
    public static final String FACILITY_PROP_REF_ID = "FACILITY_PROP_REF_ID";
    /** <code>FACILITY_PROP_VALUE_REF_ID</code> FACILITY_PROP_VALUE_REF_ID column of table CLW_FACILITY_PROP_VALUE_REF */
    public static final String FACILITY_PROP_VALUE_REF_ID = "FACILITY_PROP_VALUE_REF_ID";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_FACILITY_PROP_VALUE_REF */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>JAVA_CLASS</code> JAVA_CLASS column of table CLW_FACILITY_PROP_VALUE_REF */
    public static final String JAVA_CLASS = "JAVA_CLASS";

    /**
     * Constructor.
     */
    public FacilityPropValueRefDataAccess()
    {
    }

    /**
     * Gets a FacilityPropValueRefData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pFacilityPropValueRefId The key requested.
     * @return new FacilityPropValueRefData()
     * @throws            SQLException
     */
    public static FacilityPropValueRefData select(Connection pCon, int pFacilityPropValueRefId)
        throws SQLException, DataNotFoundException {
        FacilityPropValueRefData x=null;
        String sql="SELECT FACILITY_PROP_REF_ID,FACILITY_PROP_VALUE_REF_ID,CLW_VALUE,JAVA_CLASS FROM CLW_FACILITY_PROP_VALUE_REF WHERE FACILITY_PROP_VALUE_REF_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pFacilityPropValueRefId=" + pFacilityPropValueRefId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pFacilityPropValueRefId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=FacilityPropValueRefData.createValue();
            
            x.setFacilityPropRefId(rs.getInt(1));
            x.setFacilityPropValueRefId(rs.getInt(2));
            x.setValue(rs.getString(3));
            x.setJavaClass(rs.getString(4));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("FACILITY_PROP_VALUE_REF_ID :" + pFacilityPropValueRefId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a FacilityPropValueRefDataVector object that consists
     * of FacilityPropValueRefData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new FacilityPropValueRefDataVector()
     * @throws            SQLException
     */
    public static FacilityPropValueRefDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a FacilityPropValueRefData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_FACILITY_PROP_VALUE_REF.FACILITY_PROP_REF_ID,CLW_FACILITY_PROP_VALUE_REF.FACILITY_PROP_VALUE_REF_ID,CLW_FACILITY_PROP_VALUE_REF.CLW_VALUE,CLW_FACILITY_PROP_VALUE_REF.JAVA_CLASS";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated FacilityPropValueRefData Object.
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
    *@returns a populated FacilityPropValueRefData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         FacilityPropValueRefData x = FacilityPropValueRefData.createValue();
         
         x.setFacilityPropRefId(rs.getInt(1+offset));
         x.setFacilityPropValueRefId(rs.getInt(2+offset));
         x.setValue(rs.getString(3+offset));
         x.setJavaClass(rs.getString(4+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the FacilityPropValueRefData Object represents.
    */
    public int getColumnCount(){
        return 4;
    }

    /**
     * Gets a FacilityPropValueRefDataVector object that consists
     * of FacilityPropValueRefData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new FacilityPropValueRefDataVector()
     * @throws            SQLException
     */
    public static FacilityPropValueRefDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT FACILITY_PROP_REF_ID,FACILITY_PROP_VALUE_REF_ID,CLW_VALUE,JAVA_CLASS FROM CLW_FACILITY_PROP_VALUE_REF");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_FACILITY_PROP_VALUE_REF.FACILITY_PROP_REF_ID,CLW_FACILITY_PROP_VALUE_REF.FACILITY_PROP_VALUE_REF_ID,CLW_FACILITY_PROP_VALUE_REF.CLW_VALUE,CLW_FACILITY_PROP_VALUE_REF.JAVA_CLASS FROM CLW_FACILITY_PROP_VALUE_REF");
                where = pCriteria.getSqlClause("CLW_FACILITY_PROP_VALUE_REF");

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
        FacilityPropValueRefDataVector v = new FacilityPropValueRefDataVector();
        while (rs.next()) {
            FacilityPropValueRefData x = FacilityPropValueRefData.createValue();
            
            x.setFacilityPropRefId(rs.getInt(1));
            x.setFacilityPropValueRefId(rs.getInt(2));
            x.setValue(rs.getString(3));
            x.setJavaClass(rs.getString(4));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a FacilityPropValueRefDataVector object that consists
     * of FacilityPropValueRefData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for FacilityPropValueRefData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new FacilityPropValueRefDataVector()
     * @throws            SQLException
     */
    public static FacilityPropValueRefDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        FacilityPropValueRefDataVector v = new FacilityPropValueRefDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT FACILITY_PROP_REF_ID,FACILITY_PROP_VALUE_REF_ID,CLW_VALUE,JAVA_CLASS FROM CLW_FACILITY_PROP_VALUE_REF WHERE FACILITY_PROP_VALUE_REF_ID IN (");

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
            FacilityPropValueRefData x=null;
            while (rs.next()) {
                // build the object
                x=FacilityPropValueRefData.createValue();
                
                x.setFacilityPropRefId(rs.getInt(1));
                x.setFacilityPropValueRefId(rs.getInt(2));
                x.setValue(rs.getString(3));
                x.setJavaClass(rs.getString(4));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a FacilityPropValueRefDataVector object of all
     * FacilityPropValueRefData objects in the database.
     * @param pCon An open database connection.
     * @return new FacilityPropValueRefDataVector()
     * @throws            SQLException
     */
    public static FacilityPropValueRefDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT FACILITY_PROP_REF_ID,FACILITY_PROP_VALUE_REF_ID,CLW_VALUE,JAVA_CLASS FROM CLW_FACILITY_PROP_VALUE_REF";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        FacilityPropValueRefDataVector v = new FacilityPropValueRefDataVector();
        FacilityPropValueRefData x = null;
        while (rs.next()) {
            // build the object
            x = FacilityPropValueRefData.createValue();
            
            x.setFacilityPropRefId(rs.getInt(1));
            x.setFacilityPropValueRefId(rs.getInt(2));
            x.setValue(rs.getString(3));
            x.setJavaClass(rs.getString(4));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * FacilityPropValueRefData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT FACILITY_PROP_VALUE_REF_ID FROM CLW_FACILITY_PROP_VALUE_REF");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FACILITY_PROP_VALUE_REF");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FACILITY_PROP_VALUE_REF");
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
     * Inserts a FacilityPropValueRefData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityPropValueRefData object to insert.
     * @return new FacilityPropValueRefData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FacilityPropValueRefData insert(Connection pCon, FacilityPropValueRefData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_FACILITY_PROP_VALUE_REF_SEQ.NEXTVAL FROM DUAL");
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_FACILITY_PROP_VALUE_REF_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setFacilityPropValueRefId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_FACILITY_PROP_VALUE_REF (FACILITY_PROP_REF_ID,FACILITY_PROP_VALUE_REF_ID,CLW_VALUE,JAVA_CLASS) VALUES(?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setInt(1,pData.getFacilityPropRefId());
        pstmt.setInt(2,pData.getFacilityPropValueRefId());
        pstmt.setString(3,pData.getValue());
        pstmt.setString(4,pData.getJavaClass());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FACILITY_PROP_REF_ID="+pData.getFacilityPropRefId());
            log.debug("SQL:   FACILITY_PROP_VALUE_REF_ID="+pData.getFacilityPropValueRefId());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   JAVA_CLASS="+pData.getJavaClass());
            log.debug("SQL: " + sql);
        }

        pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);
        return pData;
    }

    /**
     * Updates a FacilityPropValueRefData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityPropValueRefData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FacilityPropValueRefData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_FACILITY_PROP_VALUE_REF SET FACILITY_PROP_REF_ID = ?,CLW_VALUE = ?,JAVA_CLASS = ? WHERE FACILITY_PROP_VALUE_REF_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setInt(i++,pData.getFacilityPropRefId());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getJavaClass());
        pstmt.setInt(i++,pData.getFacilityPropValueRefId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FACILITY_PROP_REF_ID="+pData.getFacilityPropRefId());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   JAVA_CLASS="+pData.getJavaClass());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a FacilityPropValueRefData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFacilityPropValueRefId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFacilityPropValueRefId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_FACILITY_PROP_VALUE_REF WHERE FACILITY_PROP_VALUE_REF_ID = " + pFacilityPropValueRefId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes FacilityPropValueRefData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_FACILITY_PROP_VALUE_REF");
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

