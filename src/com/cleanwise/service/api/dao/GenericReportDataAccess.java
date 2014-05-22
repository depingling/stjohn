
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        GenericReportDataAccess
 * Description:  This class is used to build access methods to the CLW_GENERIC_REPORT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportDataVector;
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
 * <code>GenericReportDataAccess</code>
 */
public class GenericReportDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(GenericReportDataAccess.class.getName());

    /** <code>CLW_GENERIC_REPORT</code> table name */
	/* Primary key: GENERIC_REPORT_ID */
	
    public static final String CLW_GENERIC_REPORT = "CLW_GENERIC_REPORT";
    
    /** <code>GENERIC_REPORT_ID</code> GENERIC_REPORT_ID column of table CLW_GENERIC_REPORT */
    public static final String GENERIC_REPORT_ID = "GENERIC_REPORT_ID";
    /** <code>CATEGORY</code> CATEGORY column of table CLW_GENERIC_REPORT */
    public static final String CATEGORY = "CATEGORY";
    /** <code>NAME</code> NAME column of table CLW_GENERIC_REPORT */
    public static final String NAME = "NAME";
    /** <code>PARAMETER_TOKEN</code> PARAMETER_TOKEN column of table CLW_GENERIC_REPORT */
    public static final String PARAMETER_TOKEN = "PARAMETER_TOKEN";
    /** <code>REPORT_SCHEMA_CD</code> REPORT_SCHEMA_CD column of table CLW_GENERIC_REPORT */
    public static final String REPORT_SCHEMA_CD = "REPORT_SCHEMA_CD";
    /** <code>INTERFACE_TABLE</code> INTERFACE_TABLE column of table CLW_GENERIC_REPORT */
    public static final String INTERFACE_TABLE = "INTERFACE_TABLE";
    /** <code>SQL_TEXT</code> SQL_TEXT column of table CLW_GENERIC_REPORT */
    public static final String SQL_TEXT = "SQL_TEXT";
    /** <code>SCRIPT_TEXT</code> SCRIPT_TEXT column of table CLW_GENERIC_REPORT */
    public static final String SCRIPT_TEXT = "SCRIPT_TEXT";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_GENERIC_REPORT */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_GENERIC_REPORT */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_GENERIC_REPORT */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_GENERIC_REPORT */
    public static final String MOD_BY = "MOD_BY";
    /** <code>GENERIC_REPORT_TYPE</code> GENERIC_REPORT_TYPE column of table CLW_GENERIC_REPORT */
    public static final String GENERIC_REPORT_TYPE = "GENERIC_REPORT_TYPE";
    /** <code>CLASSNAME</code> CLASSNAME column of table CLW_GENERIC_REPORT */
    public static final String CLASSNAME = "CLASSNAME";
    /** <code>SUPPLEMENTARY_CONTROLS</code> SUPPLEMENTARY_CONTROLS column of table CLW_GENERIC_REPORT */
    public static final String SUPPLEMENTARY_CONTROLS = "SUPPLEMENTARY_CONTROLS";
    /** <code>RUNTIME_ENABLED</code> RUNTIME_ENABLED column of table CLW_GENERIC_REPORT */
    public static final String RUNTIME_ENABLED = "RUNTIME_ENABLED";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_GENERIC_REPORT */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>USER_TYPES</code> USER_TYPES column of table CLW_GENERIC_REPORT */
    public static final String USER_TYPES = "USER_TYPES";
    /** <code>DB_NAME</code> DB_NAME column of table CLW_GENERIC_REPORT */
    public static final String DB_NAME = "DB_NAME";

    /**
     * Constructor.
     */
    public GenericReportDataAccess()
    {
    }

    /**
     * Gets a GenericReportData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pGenericReportId The key requested.
     * @return new GenericReportData()
     * @throws            SQLException
     */
    public static GenericReportData select(Connection pCon, int pGenericReportId)
        throws SQLException, DataNotFoundException {
        GenericReportData x=null;
        String sql="SELECT GENERIC_REPORT_ID,CATEGORY,NAME,PARAMETER_TOKEN,REPORT_SCHEMA_CD,INTERFACE_TABLE,SQL_TEXT,SCRIPT_TEXT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,GENERIC_REPORT_TYPE,CLASSNAME,SUPPLEMENTARY_CONTROLS,RUNTIME_ENABLED,LONG_DESC,USER_TYPES,DB_NAME FROM CLW_GENERIC_REPORT WHERE GENERIC_REPORT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pGenericReportId=" + pGenericReportId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pGenericReportId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=GenericReportData.createValue();
            
            x.setGenericReportId(rs.getInt(1));
            x.setCategory(rs.getString(2));
            x.setName(rs.getString(3));
            x.setParameterToken(rs.getString(4));
            x.setReportSchemaCd(rs.getString(5));
            x.setInterfaceTable(rs.getString(6));
            x.setSqlText(rs.getString(7));
            x.setScriptText(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setGenericReportType(rs.getString(13));
            x.setClassname(rs.getString(14));
            x.setSupplementaryControls(rs.getString(15));
            x.setRuntimeEnabled(rs.getString(16));
            x.setLongDesc(rs.getString(17));
            x.setUserTypes(rs.getString(18));
            x.setDbName(rs.getString(19));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("GENERIC_REPORT_ID :" + pGenericReportId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a GenericReportDataVector object that consists
     * of GenericReportData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new GenericReportDataVector()
     * @throws            SQLException
     */
    public static GenericReportDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a GenericReportData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_GENERIC_REPORT.GENERIC_REPORT_ID,CLW_GENERIC_REPORT.CATEGORY,CLW_GENERIC_REPORT.NAME,CLW_GENERIC_REPORT.PARAMETER_TOKEN,CLW_GENERIC_REPORT.REPORT_SCHEMA_CD,CLW_GENERIC_REPORT.INTERFACE_TABLE,CLW_GENERIC_REPORT.SQL_TEXT,CLW_GENERIC_REPORT.SCRIPT_TEXT,CLW_GENERIC_REPORT.ADD_DATE,CLW_GENERIC_REPORT.ADD_BY,CLW_GENERIC_REPORT.MOD_DATE,CLW_GENERIC_REPORT.MOD_BY,CLW_GENERIC_REPORT.GENERIC_REPORT_TYPE,CLW_GENERIC_REPORT.CLASSNAME,CLW_GENERIC_REPORT.SUPPLEMENTARY_CONTROLS,CLW_GENERIC_REPORT.RUNTIME_ENABLED,CLW_GENERIC_REPORT.LONG_DESC,CLW_GENERIC_REPORT.USER_TYPES,CLW_GENERIC_REPORT.DB_NAME";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated GenericReportData Object.
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
    *@returns a populated GenericReportData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         GenericReportData x = GenericReportData.createValue();
         
         x.setGenericReportId(rs.getInt(1+offset));
         x.setCategory(rs.getString(2+offset));
         x.setName(rs.getString(3+offset));
         x.setParameterToken(rs.getString(4+offset));
         x.setReportSchemaCd(rs.getString(5+offset));
         x.setInterfaceTable(rs.getString(6+offset));
         x.setSqlText(rs.getString(7+offset));
         x.setScriptText(rs.getString(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         x.setGenericReportType(rs.getString(13+offset));
         x.setClassname(rs.getString(14+offset));
         x.setSupplementaryControls(rs.getString(15+offset));
         x.setRuntimeEnabled(rs.getString(16+offset));
         x.setLongDesc(rs.getString(17+offset));
         x.setUserTypes(rs.getString(18+offset));
         x.setDbName(rs.getString(19+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the GenericReportData Object represents.
    */
    public int getColumnCount(){
        return 19;
    }

    /**
     * Gets a GenericReportDataVector object that consists
     * of GenericReportData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new GenericReportDataVector()
     * @throws            SQLException
     */
    public static GenericReportDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT GENERIC_REPORT_ID,CATEGORY,NAME,PARAMETER_TOKEN,REPORT_SCHEMA_CD,INTERFACE_TABLE,SQL_TEXT,SCRIPT_TEXT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,GENERIC_REPORT_TYPE,CLASSNAME,SUPPLEMENTARY_CONTROLS,RUNTIME_ENABLED,LONG_DESC,USER_TYPES,DB_NAME FROM CLW_GENERIC_REPORT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_GENERIC_REPORT.GENERIC_REPORT_ID,CLW_GENERIC_REPORT.CATEGORY,CLW_GENERIC_REPORT.NAME,CLW_GENERIC_REPORT.PARAMETER_TOKEN,CLW_GENERIC_REPORT.REPORT_SCHEMA_CD,CLW_GENERIC_REPORT.INTERFACE_TABLE,CLW_GENERIC_REPORT.SQL_TEXT,CLW_GENERIC_REPORT.SCRIPT_TEXT,CLW_GENERIC_REPORT.ADD_DATE,CLW_GENERIC_REPORT.ADD_BY,CLW_GENERIC_REPORT.MOD_DATE,CLW_GENERIC_REPORT.MOD_BY,CLW_GENERIC_REPORT.GENERIC_REPORT_TYPE,CLW_GENERIC_REPORT.CLASSNAME,CLW_GENERIC_REPORT.SUPPLEMENTARY_CONTROLS,CLW_GENERIC_REPORT.RUNTIME_ENABLED,CLW_GENERIC_REPORT.LONG_DESC,CLW_GENERIC_REPORT.USER_TYPES,CLW_GENERIC_REPORT.DB_NAME FROM CLW_GENERIC_REPORT");
                where = pCriteria.getSqlClause("CLW_GENERIC_REPORT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_GENERIC_REPORT.equals(otherTable)){
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
        GenericReportDataVector v = new GenericReportDataVector();
        while (rs.next()) {
            GenericReportData x = GenericReportData.createValue();
            
            x.setGenericReportId(rs.getInt(1));
            x.setCategory(rs.getString(2));
            x.setName(rs.getString(3));
            x.setParameterToken(rs.getString(4));
            x.setReportSchemaCd(rs.getString(5));
            x.setInterfaceTable(rs.getString(6));
            x.setSqlText(rs.getString(7));
            x.setScriptText(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setGenericReportType(rs.getString(13));
            x.setClassname(rs.getString(14));
            x.setSupplementaryControls(rs.getString(15));
            x.setRuntimeEnabled(rs.getString(16));
            x.setLongDesc(rs.getString(17));
            x.setUserTypes(rs.getString(18));
            x.setDbName(rs.getString(19));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a GenericReportDataVector object that consists
     * of GenericReportData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for GenericReportData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new GenericReportDataVector()
     * @throws            SQLException
     */
    public static GenericReportDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        GenericReportDataVector v = new GenericReportDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT GENERIC_REPORT_ID,CATEGORY,NAME,PARAMETER_TOKEN,REPORT_SCHEMA_CD,INTERFACE_TABLE,SQL_TEXT,SCRIPT_TEXT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,GENERIC_REPORT_TYPE,CLASSNAME,SUPPLEMENTARY_CONTROLS,RUNTIME_ENABLED,LONG_DESC,USER_TYPES,DB_NAME FROM CLW_GENERIC_REPORT WHERE GENERIC_REPORT_ID IN (");

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
            GenericReportData x=null;
            while (rs.next()) {
                // build the object
                x=GenericReportData.createValue();
                
                x.setGenericReportId(rs.getInt(1));
                x.setCategory(rs.getString(2));
                x.setName(rs.getString(3));
                x.setParameterToken(rs.getString(4));
                x.setReportSchemaCd(rs.getString(5));
                x.setInterfaceTable(rs.getString(6));
                x.setSqlText(rs.getString(7));
                x.setScriptText(rs.getString(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setAddBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                x.setGenericReportType(rs.getString(13));
                x.setClassname(rs.getString(14));
                x.setSupplementaryControls(rs.getString(15));
                x.setRuntimeEnabled(rs.getString(16));
                x.setLongDesc(rs.getString(17));
                x.setUserTypes(rs.getString(18));
                x.setDbName(rs.getString(19));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a GenericReportDataVector object of all
     * GenericReportData objects in the database.
     * @param pCon An open database connection.
     * @return new GenericReportDataVector()
     * @throws            SQLException
     */
    public static GenericReportDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT GENERIC_REPORT_ID,CATEGORY,NAME,PARAMETER_TOKEN,REPORT_SCHEMA_CD,INTERFACE_TABLE,SQL_TEXT,SCRIPT_TEXT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,GENERIC_REPORT_TYPE,CLASSNAME,SUPPLEMENTARY_CONTROLS,RUNTIME_ENABLED,LONG_DESC,USER_TYPES,DB_NAME FROM CLW_GENERIC_REPORT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        GenericReportDataVector v = new GenericReportDataVector();
        GenericReportData x = null;
        while (rs.next()) {
            // build the object
            x = GenericReportData.createValue();
            
            x.setGenericReportId(rs.getInt(1));
            x.setCategory(rs.getString(2));
            x.setName(rs.getString(3));
            x.setParameterToken(rs.getString(4));
            x.setReportSchemaCd(rs.getString(5));
            x.setInterfaceTable(rs.getString(6));
            x.setSqlText(rs.getString(7));
            x.setScriptText(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setGenericReportType(rs.getString(13));
            x.setClassname(rs.getString(14));
            x.setSupplementaryControls(rs.getString(15));
            x.setRuntimeEnabled(rs.getString(16));
            x.setLongDesc(rs.getString(17));
            x.setUserTypes(rs.getString(18));
            x.setDbName(rs.getString(19));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * GenericReportData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT GENERIC_REPORT_ID FROM CLW_GENERIC_REPORT");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT GENERIC_REPORT_ID FROM CLW_GENERIC_REPORT");
                where = pCriteria.getSqlClause("CLW_GENERIC_REPORT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_GENERIC_REPORT.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_GENERIC_REPORT");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_GENERIC_REPORT");
                where = pCriteria.getSqlClause("CLW_GENERIC_REPORT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_GENERIC_REPORT.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_GENERIC_REPORT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_GENERIC_REPORT");
                where = pCriteria.getSqlClause("CLW_GENERIC_REPORT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_GENERIC_REPORT.equals(otherTable)){
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
     * Inserts a GenericReportData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A GenericReportData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new GenericReportData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static GenericReportData insert(Connection pCon, GenericReportData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_GENERIC_REPORT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_GENERIC_REPORT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setGenericReportId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_GENERIC_REPORT (GENERIC_REPORT_ID,CATEGORY,NAME,PARAMETER_TOKEN,REPORT_SCHEMA_CD,INTERFACE_TABLE,SQL_TEXT,SCRIPT_TEXT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,GENERIC_REPORT_TYPE,CLASSNAME,SUPPLEMENTARY_CONTROLS,RUNTIME_ENABLED,LONG_DESC,USER_TYPES,DB_NAME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getGenericReportId());
        pstmt.setString(2,pData.getCategory());
        pstmt.setString(3,pData.getName());
        pstmt.setString(4,pData.getParameterToken());
        pstmt.setString(5,pData.getReportSchemaCd());
        pstmt.setString(6,pData.getInterfaceTable());
        pstmt.setString(7,pData.getSqlText());
        pstmt.setString(8,pData.getScriptText());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());
        pstmt.setString(13,pData.getGenericReportType());
        pstmt.setString(14,pData.getClassname());
        pstmt.setString(15,pData.getSupplementaryControls());
        pstmt.setString(16,pData.getRuntimeEnabled());
        pstmt.setString(17,pData.getLongDesc());
        pstmt.setString(18,pData.getUserTypes());
        pstmt.setString(19,pData.getDbName());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   GENERIC_REPORT_ID="+pData.getGenericReportId());
            log.debug("SQL:   CATEGORY="+pData.getCategory());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   PARAMETER_TOKEN="+pData.getParameterToken());
            log.debug("SQL:   REPORT_SCHEMA_CD="+pData.getReportSchemaCd());
            log.debug("SQL:   INTERFACE_TABLE="+pData.getInterfaceTable());
            log.debug("SQL:   SQL_TEXT="+pData.getSqlText());
            log.debug("SQL:   SCRIPT_TEXT="+pData.getScriptText());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   GENERIC_REPORT_TYPE="+pData.getGenericReportType());
            log.debug("SQL:   CLASSNAME="+pData.getClassname());
            log.debug("SQL:   SUPPLEMENTARY_CONTROLS="+pData.getSupplementaryControls());
            log.debug("SQL:   RUNTIME_ENABLED="+pData.getRuntimeEnabled());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   USER_TYPES="+pData.getUserTypes());
            log.debug("SQL:   DB_NAME="+pData.getDbName());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setGenericReportId(0);
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
     * Updates a GenericReportData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A GenericReportData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, GenericReportData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_GENERIC_REPORT SET CATEGORY = ?,NAME = ?,PARAMETER_TOKEN = ?,REPORT_SCHEMA_CD = ?,INTERFACE_TABLE = ?,SQL_TEXT = ?,SCRIPT_TEXT = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,GENERIC_REPORT_TYPE = ?,CLASSNAME = ?,SUPPLEMENTARY_CONTROLS = ?,RUNTIME_ENABLED = ?,LONG_DESC = ?,USER_TYPES = ?,DB_NAME = ? WHERE GENERIC_REPORT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getCategory());
        pstmt.setString(i++,pData.getName());
        pstmt.setString(i++,pData.getParameterToken());
        pstmt.setString(i++,pData.getReportSchemaCd());
        pstmt.setString(i++,pData.getInterfaceTable());
        pstmt.setString(i++,pData.getSqlText());
        pstmt.setString(i++,pData.getScriptText());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getGenericReportType());
        pstmt.setString(i++,pData.getClassname());
        pstmt.setString(i++,pData.getSupplementaryControls());
        pstmt.setString(i++,pData.getRuntimeEnabled());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setString(i++,pData.getUserTypes());
        pstmt.setString(i++,pData.getDbName());
        pstmt.setInt(i++,pData.getGenericReportId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CATEGORY="+pData.getCategory());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   PARAMETER_TOKEN="+pData.getParameterToken());
            log.debug("SQL:   REPORT_SCHEMA_CD="+pData.getReportSchemaCd());
            log.debug("SQL:   INTERFACE_TABLE="+pData.getInterfaceTable());
            log.debug("SQL:   SQL_TEXT="+pData.getSqlText());
            log.debug("SQL:   SCRIPT_TEXT="+pData.getScriptText());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   GENERIC_REPORT_TYPE="+pData.getGenericReportType());
            log.debug("SQL:   CLASSNAME="+pData.getClassname());
            log.debug("SQL:   SUPPLEMENTARY_CONTROLS="+pData.getSupplementaryControls());
            log.debug("SQL:   RUNTIME_ENABLED="+pData.getRuntimeEnabled());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   USER_TYPES="+pData.getUserTypes());
            log.debug("SQL:   DB_NAME="+pData.getDbName());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a GenericReportData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pGenericReportId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pGenericReportId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_GENERIC_REPORT WHERE GENERIC_REPORT_ID = " + pGenericReportId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes GenericReportData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_GENERIC_REPORT");
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
     * Inserts a GenericReportData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A GenericReportData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, GenericReportData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_GENERIC_REPORT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "GENERIC_REPORT_ID,CATEGORY,NAME,PARAMETER_TOKEN,REPORT_SCHEMA_CD,INTERFACE_TABLE,SQL_TEXT,SCRIPT_TEXT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,GENERIC_REPORT_TYPE,CLASSNAME,SUPPLEMENTARY_CONTROLS,RUNTIME_ENABLED,LONG_DESC,USER_TYPES,DB_NAME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getGenericReportId());
        pstmt.setString(2+4,pData.getCategory());
        pstmt.setString(3+4,pData.getName());
        pstmt.setString(4+4,pData.getParameterToken());
        pstmt.setString(5+4,pData.getReportSchemaCd());
        pstmt.setString(6+4,pData.getInterfaceTable());
        pstmt.setString(7+4,pData.getSqlText());
        pstmt.setString(8+4,pData.getScriptText());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());
        pstmt.setString(13+4,pData.getGenericReportType());
        pstmt.setString(14+4,pData.getClassname());
        pstmt.setString(15+4,pData.getSupplementaryControls());
        pstmt.setString(16+4,pData.getRuntimeEnabled());
        pstmt.setString(17+4,pData.getLongDesc());
        pstmt.setString(18+4,pData.getUserTypes());
        pstmt.setString(19+4,pData.getDbName());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a GenericReportData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A GenericReportData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new GenericReportData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static GenericReportData insert(Connection pCon, GenericReportData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a GenericReportData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A GenericReportData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, GenericReportData pData, boolean pLogFl)
        throws SQLException {
        GenericReportData oldData = null;
        if(pLogFl) {
          int id = pData.getGenericReportId();
          try {
          oldData = GenericReportDataAccess.select(pCon,id);
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
     * Deletes a GenericReportData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pGenericReportId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pGenericReportId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_GENERIC_REPORT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_GENERIC_REPORT d WHERE GENERIC_REPORT_ID = " + pGenericReportId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pGenericReportId);
        return n;
     }

    /**
     * Deletes GenericReportData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_GENERIC_REPORT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_GENERIC_REPORT d ");
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

