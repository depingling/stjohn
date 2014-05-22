
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ProdApplDataAccess
 * Description:  This class is used to build access methods to the CLW_PROD_APPL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ProdApplData;
import com.cleanwise.service.api.value.ProdApplDataVector;
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
 * <code>ProdApplDataAccess</code>
 */
public class ProdApplDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ProdApplDataAccess.class.getName());

    /** <code>CLW_PROD_APPL</code> table name */
	/* Primary key: PROD_APPL_ID */
	
    public static final String CLW_PROD_APPL = "CLW_PROD_APPL";
    
    /** <code>PROD_APPL_ID</code> PROD_APPL_ID column of table CLW_PROD_APPL */
    public static final String PROD_APPL_ID = "PROD_APPL_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_PROD_APPL */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>CLEANING_PROC_ID</code> CLEANING_PROC_ID column of table CLW_PROD_APPL */
    public static final String CLEANING_PROC_ID = "CLEANING_PROC_ID";
    /** <code>ESTIMATOR_PRODUCT_CD</code> ESTIMATOR_PRODUCT_CD column of table CLW_PROD_APPL */
    public static final String ESTIMATOR_PRODUCT_CD = "ESTIMATOR_PRODUCT_CD";
    /** <code>ESTIMATOR_FACILITY_ID</code> ESTIMATOR_FACILITY_ID column of table CLW_PROD_APPL */
    public static final String ESTIMATOR_FACILITY_ID = "ESTIMATOR_FACILITY_ID";
    /** <code>CLEANING_SCHED_STRUCT_ID</code> CLEANING_SCHED_STRUCT_ID column of table CLW_PROD_APPL */
    public static final String CLEANING_SCHED_STRUCT_ID = "CLEANING_SCHED_STRUCT_ID";
    /** <code>USAGE_RATE</code> USAGE_RATE column of table CLW_PROD_APPL */
    public static final String USAGE_RATE = "USAGE_RATE";
    /** <code>UNIT_CD_NUMERATOR</code> UNIT_CD_NUMERATOR column of table CLW_PROD_APPL */
    public static final String UNIT_CD_NUMERATOR = "UNIT_CD_NUMERATOR";
    /** <code>UNIT_CD_DENOMINATOR</code> UNIT_CD_DENOMINATOR column of table CLW_PROD_APPL */
    public static final String UNIT_CD_DENOMINATOR = "UNIT_CD_DENOMINATOR";
    /** <code>SHARING_PERCENT</code> SHARING_PERCENT column of table CLW_PROD_APPL */
    public static final String SHARING_PERCENT = "SHARING_PERCENT";
    /** <code>FLOOR_CARE_ID</code> FLOOR_CARE_ID column of table CLW_PROD_APPL */
    public static final String FLOOR_CARE_ID = "FLOOR_CARE_ID";
    /** <code>FLOOR_TYPE_CD</code> FLOOR_TYPE_CD column of table CLW_PROD_APPL */
    public static final String FLOOR_TYPE_CD = "FLOOR_TYPE_CD";
    /** <code>DILUTION_RATE</code> DILUTION_RATE column of table CLW_PROD_APPL */
    public static final String DILUTION_RATE = "DILUTION_RATE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PROD_APPL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PROD_APPL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PROD_APPL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PROD_APPL */
    public static final String MOD_BY = "MOD_BY";
    /** <code>UI_FILTER</code> UI_FILTER column of table CLW_PROD_APPL */
    public static final String UI_FILTER = "UI_FILTER";
    /** <code>UNIT_CD_DENOMINATOR1</code> UNIT_CD_DENOMINATOR1 column of table CLW_PROD_APPL */
    public static final String UNIT_CD_DENOMINATOR1 = "UNIT_CD_DENOMINATOR1";

    /**
     * Constructor.
     */
    public ProdApplDataAccess()
    {
    }

    /**
     * Gets a ProdApplData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pProdApplId The key requested.
     * @return new ProdApplData()
     * @throws            SQLException
     */
    public static ProdApplData select(Connection pCon, int pProdApplId)
        throws SQLException, DataNotFoundException {
        ProdApplData x=null;
        String sql="SELECT PROD_APPL_ID,ITEM_ID,CLEANING_PROC_ID,ESTIMATOR_PRODUCT_CD,ESTIMATOR_FACILITY_ID,CLEANING_SCHED_STRUCT_ID,USAGE_RATE,UNIT_CD_NUMERATOR,UNIT_CD_DENOMINATOR,SHARING_PERCENT,FLOOR_CARE_ID,FLOOR_TYPE_CD,DILUTION_RATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UI_FILTER,UNIT_CD_DENOMINATOR1 FROM CLW_PROD_APPL WHERE PROD_APPL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pProdApplId=" + pProdApplId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pProdApplId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ProdApplData.createValue();
            
            x.setProdApplId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setCleaningProcId(rs.getInt(3));
            x.setEstimatorProductCd(rs.getString(4));
            x.setEstimatorFacilityId(rs.getInt(5));
            x.setCleaningSchedStructId(rs.getInt(6));
            x.setUsageRate(rs.getBigDecimal(7));
            x.setUnitCdNumerator(rs.getString(8));
            x.setUnitCdDenominator(rs.getString(9));
            x.setSharingPercent(rs.getBigDecimal(10));
            x.setFloorCareId(rs.getInt(11));
            x.setFloorTypeCd(rs.getString(12));
            x.setDilutionRate(rs.getBigDecimal(13));
            x.setAddDate(rs.getTimestamp(14));
            x.setAddBy(rs.getString(15));
            x.setModDate(rs.getTimestamp(16));
            x.setModBy(rs.getString(17));
            x.setUiFilter(rs.getBigDecimal(18));
            x.setUnitCdDenominator1(rs.getString(19));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PROD_APPL_ID :" + pProdApplId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ProdApplDataVector object that consists
     * of ProdApplData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ProdApplDataVector()
     * @throws            SQLException
     */
    public static ProdApplDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ProdApplData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PROD_APPL.PROD_APPL_ID,CLW_PROD_APPL.ITEM_ID,CLW_PROD_APPL.CLEANING_PROC_ID,CLW_PROD_APPL.ESTIMATOR_PRODUCT_CD,CLW_PROD_APPL.ESTIMATOR_FACILITY_ID,CLW_PROD_APPL.CLEANING_SCHED_STRUCT_ID,CLW_PROD_APPL.USAGE_RATE,CLW_PROD_APPL.UNIT_CD_NUMERATOR,CLW_PROD_APPL.UNIT_CD_DENOMINATOR,CLW_PROD_APPL.SHARING_PERCENT,CLW_PROD_APPL.FLOOR_CARE_ID,CLW_PROD_APPL.FLOOR_TYPE_CD,CLW_PROD_APPL.DILUTION_RATE,CLW_PROD_APPL.ADD_DATE,CLW_PROD_APPL.ADD_BY,CLW_PROD_APPL.MOD_DATE,CLW_PROD_APPL.MOD_BY,CLW_PROD_APPL.UI_FILTER,CLW_PROD_APPL.UNIT_CD_DENOMINATOR1";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ProdApplData Object.
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
    *@returns a populated ProdApplData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ProdApplData x = ProdApplData.createValue();
         
         x.setProdApplId(rs.getInt(1+offset));
         x.setItemId(rs.getInt(2+offset));
         x.setCleaningProcId(rs.getInt(3+offset));
         x.setEstimatorProductCd(rs.getString(4+offset));
         x.setEstimatorFacilityId(rs.getInt(5+offset));
         x.setCleaningSchedStructId(rs.getInt(6+offset));
         x.setUsageRate(rs.getBigDecimal(7+offset));
         x.setUnitCdNumerator(rs.getString(8+offset));
         x.setUnitCdDenominator(rs.getString(9+offset));
         x.setSharingPercent(rs.getBigDecimal(10+offset));
         x.setFloorCareId(rs.getInt(11+offset));
         x.setFloorTypeCd(rs.getString(12+offset));
         x.setDilutionRate(rs.getBigDecimal(13+offset));
         x.setAddDate(rs.getTimestamp(14+offset));
         x.setAddBy(rs.getString(15+offset));
         x.setModDate(rs.getTimestamp(16+offset));
         x.setModBy(rs.getString(17+offset));
         x.setUiFilter(rs.getBigDecimal(18+offset));
         x.setUnitCdDenominator1(rs.getString(19+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ProdApplData Object represents.
    */
    public int getColumnCount(){
        return 19;
    }

    /**
     * Gets a ProdApplDataVector object that consists
     * of ProdApplData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ProdApplDataVector()
     * @throws            SQLException
     */
    public static ProdApplDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PROD_APPL_ID,ITEM_ID,CLEANING_PROC_ID,ESTIMATOR_PRODUCT_CD,ESTIMATOR_FACILITY_ID,CLEANING_SCHED_STRUCT_ID,USAGE_RATE,UNIT_CD_NUMERATOR,UNIT_CD_DENOMINATOR,SHARING_PERCENT,FLOOR_CARE_ID,FLOOR_TYPE_CD,DILUTION_RATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UI_FILTER,UNIT_CD_DENOMINATOR1 FROM CLW_PROD_APPL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PROD_APPL.PROD_APPL_ID,CLW_PROD_APPL.ITEM_ID,CLW_PROD_APPL.CLEANING_PROC_ID,CLW_PROD_APPL.ESTIMATOR_PRODUCT_CD,CLW_PROD_APPL.ESTIMATOR_FACILITY_ID,CLW_PROD_APPL.CLEANING_SCHED_STRUCT_ID,CLW_PROD_APPL.USAGE_RATE,CLW_PROD_APPL.UNIT_CD_NUMERATOR,CLW_PROD_APPL.UNIT_CD_DENOMINATOR,CLW_PROD_APPL.SHARING_PERCENT,CLW_PROD_APPL.FLOOR_CARE_ID,CLW_PROD_APPL.FLOOR_TYPE_CD,CLW_PROD_APPL.DILUTION_RATE,CLW_PROD_APPL.ADD_DATE,CLW_PROD_APPL.ADD_BY,CLW_PROD_APPL.MOD_DATE,CLW_PROD_APPL.MOD_BY,CLW_PROD_APPL.UI_FILTER,CLW_PROD_APPL.UNIT_CD_DENOMINATOR1 FROM CLW_PROD_APPL");
                where = pCriteria.getSqlClause("CLW_PROD_APPL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PROD_APPL.equals(otherTable)){
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
        ProdApplDataVector v = new ProdApplDataVector();
        while (rs.next()) {
            ProdApplData x = ProdApplData.createValue();
            
            x.setProdApplId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setCleaningProcId(rs.getInt(3));
            x.setEstimatorProductCd(rs.getString(4));
            x.setEstimatorFacilityId(rs.getInt(5));
            x.setCleaningSchedStructId(rs.getInt(6));
            x.setUsageRate(rs.getBigDecimal(7));
            x.setUnitCdNumerator(rs.getString(8));
            x.setUnitCdDenominator(rs.getString(9));
            x.setSharingPercent(rs.getBigDecimal(10));
            x.setFloorCareId(rs.getInt(11));
            x.setFloorTypeCd(rs.getString(12));
            x.setDilutionRate(rs.getBigDecimal(13));
            x.setAddDate(rs.getTimestamp(14));
            x.setAddBy(rs.getString(15));
            x.setModDate(rs.getTimestamp(16));
            x.setModBy(rs.getString(17));
            x.setUiFilter(rs.getBigDecimal(18));
            x.setUnitCdDenominator1(rs.getString(19));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ProdApplDataVector object that consists
     * of ProdApplData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ProdApplData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ProdApplDataVector()
     * @throws            SQLException
     */
    public static ProdApplDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ProdApplDataVector v = new ProdApplDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PROD_APPL_ID,ITEM_ID,CLEANING_PROC_ID,ESTIMATOR_PRODUCT_CD,ESTIMATOR_FACILITY_ID,CLEANING_SCHED_STRUCT_ID,USAGE_RATE,UNIT_CD_NUMERATOR,UNIT_CD_DENOMINATOR,SHARING_PERCENT,FLOOR_CARE_ID,FLOOR_TYPE_CD,DILUTION_RATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UI_FILTER,UNIT_CD_DENOMINATOR1 FROM CLW_PROD_APPL WHERE PROD_APPL_ID IN (");

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
            ProdApplData x=null;
            while (rs.next()) {
                // build the object
                x=ProdApplData.createValue();
                
                x.setProdApplId(rs.getInt(1));
                x.setItemId(rs.getInt(2));
                x.setCleaningProcId(rs.getInt(3));
                x.setEstimatorProductCd(rs.getString(4));
                x.setEstimatorFacilityId(rs.getInt(5));
                x.setCleaningSchedStructId(rs.getInt(6));
                x.setUsageRate(rs.getBigDecimal(7));
                x.setUnitCdNumerator(rs.getString(8));
                x.setUnitCdDenominator(rs.getString(9));
                x.setSharingPercent(rs.getBigDecimal(10));
                x.setFloorCareId(rs.getInt(11));
                x.setFloorTypeCd(rs.getString(12));
                x.setDilutionRate(rs.getBigDecimal(13));
                x.setAddDate(rs.getTimestamp(14));
                x.setAddBy(rs.getString(15));
                x.setModDate(rs.getTimestamp(16));
                x.setModBy(rs.getString(17));
                x.setUiFilter(rs.getBigDecimal(18));
                x.setUnitCdDenominator1(rs.getString(19));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ProdApplDataVector object of all
     * ProdApplData objects in the database.
     * @param pCon An open database connection.
     * @return new ProdApplDataVector()
     * @throws            SQLException
     */
    public static ProdApplDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PROD_APPL_ID,ITEM_ID,CLEANING_PROC_ID,ESTIMATOR_PRODUCT_CD,ESTIMATOR_FACILITY_ID,CLEANING_SCHED_STRUCT_ID,USAGE_RATE,UNIT_CD_NUMERATOR,UNIT_CD_DENOMINATOR,SHARING_PERCENT,FLOOR_CARE_ID,FLOOR_TYPE_CD,DILUTION_RATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UI_FILTER,UNIT_CD_DENOMINATOR1 FROM CLW_PROD_APPL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ProdApplDataVector v = new ProdApplDataVector();
        ProdApplData x = null;
        while (rs.next()) {
            // build the object
            x = ProdApplData.createValue();
            
            x.setProdApplId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setCleaningProcId(rs.getInt(3));
            x.setEstimatorProductCd(rs.getString(4));
            x.setEstimatorFacilityId(rs.getInt(5));
            x.setCleaningSchedStructId(rs.getInt(6));
            x.setUsageRate(rs.getBigDecimal(7));
            x.setUnitCdNumerator(rs.getString(8));
            x.setUnitCdDenominator(rs.getString(9));
            x.setSharingPercent(rs.getBigDecimal(10));
            x.setFloorCareId(rs.getInt(11));
            x.setFloorTypeCd(rs.getString(12));
            x.setDilutionRate(rs.getBigDecimal(13));
            x.setAddDate(rs.getTimestamp(14));
            x.setAddBy(rs.getString(15));
            x.setModDate(rs.getTimestamp(16));
            x.setModBy(rs.getString(17));
            x.setUiFilter(rs.getBigDecimal(18));
            x.setUnitCdDenominator1(rs.getString(19));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ProdApplData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PROD_APPL_ID FROM CLW_PROD_APPL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PROD_APPL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PROD_APPL");
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
     * Inserts a ProdApplData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProdApplData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ProdApplData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProdApplData insert(Connection pCon, ProdApplData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PROD_APPL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PROD_APPL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setProdApplId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PROD_APPL (PROD_APPL_ID,ITEM_ID,CLEANING_PROC_ID,ESTIMATOR_PRODUCT_CD,ESTIMATOR_FACILITY_ID,CLEANING_SCHED_STRUCT_ID,USAGE_RATE,UNIT_CD_NUMERATOR,UNIT_CD_DENOMINATOR,SHARING_PERCENT,FLOOR_CARE_ID,FLOOR_TYPE_CD,DILUTION_RATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UI_FILTER,UNIT_CD_DENOMINATOR1) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getProdApplId());
        pstmt.setInt(2,pData.getItemId());
        if (pData.getCleaningProcId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getCleaningProcId());
        }

        pstmt.setString(4,pData.getEstimatorProductCd());
        if (pData.getEstimatorFacilityId() == 0) {
            pstmt.setNull(5, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(5,pData.getEstimatorFacilityId());
        }

        if (pData.getCleaningSchedStructId() == 0) {
            pstmt.setNull(6, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(6,pData.getCleaningSchedStructId());
        }

        pstmt.setBigDecimal(7,pData.getUsageRate());
        pstmt.setString(8,pData.getUnitCdNumerator());
        pstmt.setString(9,pData.getUnitCdDenominator());
        pstmt.setBigDecimal(10,pData.getSharingPercent());
        pstmt.setInt(11,pData.getFloorCareId());
        pstmt.setString(12,pData.getFloorTypeCd());
        pstmt.setBigDecimal(13,pData.getDilutionRate());
        pstmt.setTimestamp(14,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(15,pData.getAddBy());
        pstmt.setTimestamp(16,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(17,pData.getModBy());
        pstmt.setBigDecimal(18,pData.getUiFilter());
        pstmt.setString(19,pData.getUnitCdDenominator1());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PROD_APPL_ID="+pData.getProdApplId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   CLEANING_PROC_ID="+pData.getCleaningProcId());
            log.debug("SQL:   ESTIMATOR_PRODUCT_CD="+pData.getEstimatorProductCd());
            log.debug("SQL:   ESTIMATOR_FACILITY_ID="+pData.getEstimatorFacilityId());
            log.debug("SQL:   CLEANING_SCHED_STRUCT_ID="+pData.getCleaningSchedStructId());
            log.debug("SQL:   USAGE_RATE="+pData.getUsageRate());
            log.debug("SQL:   UNIT_CD_NUMERATOR="+pData.getUnitCdNumerator());
            log.debug("SQL:   UNIT_CD_DENOMINATOR="+pData.getUnitCdDenominator());
            log.debug("SQL:   SHARING_PERCENT="+pData.getSharingPercent());
            log.debug("SQL:   FLOOR_CARE_ID="+pData.getFloorCareId());
            log.debug("SQL:   FLOOR_TYPE_CD="+pData.getFloorTypeCd());
            log.debug("SQL:   DILUTION_RATE="+pData.getDilutionRate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   UI_FILTER="+pData.getUiFilter());
            log.debug("SQL:   UNIT_CD_DENOMINATOR1="+pData.getUnitCdDenominator1());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setProdApplId(0);
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
     * Updates a ProdApplData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProdApplData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProdApplData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PROD_APPL SET ITEM_ID = ?,CLEANING_PROC_ID = ?,ESTIMATOR_PRODUCT_CD = ?,ESTIMATOR_FACILITY_ID = ?,CLEANING_SCHED_STRUCT_ID = ?,USAGE_RATE = ?,UNIT_CD_NUMERATOR = ?,UNIT_CD_DENOMINATOR = ?,SHARING_PERCENT = ?,FLOOR_CARE_ID = ?,FLOOR_TYPE_CD = ?,DILUTION_RATE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,UI_FILTER = ?,UNIT_CD_DENOMINATOR1 = ? WHERE PROD_APPL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getItemId());
        if (pData.getCleaningProcId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getCleaningProcId());
        }

        pstmt.setString(i++,pData.getEstimatorProductCd());
        if (pData.getEstimatorFacilityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getEstimatorFacilityId());
        }

        if (pData.getCleaningSchedStructId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getCleaningSchedStructId());
        }

        pstmt.setBigDecimal(i++,pData.getUsageRate());
        pstmt.setString(i++,pData.getUnitCdNumerator());
        pstmt.setString(i++,pData.getUnitCdDenominator());
        pstmt.setBigDecimal(i++,pData.getSharingPercent());
        pstmt.setInt(i++,pData.getFloorCareId());
        pstmt.setString(i++,pData.getFloorTypeCd());
        pstmt.setBigDecimal(i++,pData.getDilutionRate());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setBigDecimal(i++,pData.getUiFilter());
        pstmt.setString(i++,pData.getUnitCdDenominator1());
        pstmt.setInt(i++,pData.getProdApplId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   CLEANING_PROC_ID="+pData.getCleaningProcId());
            log.debug("SQL:   ESTIMATOR_PRODUCT_CD="+pData.getEstimatorProductCd());
            log.debug("SQL:   ESTIMATOR_FACILITY_ID="+pData.getEstimatorFacilityId());
            log.debug("SQL:   CLEANING_SCHED_STRUCT_ID="+pData.getCleaningSchedStructId());
            log.debug("SQL:   USAGE_RATE="+pData.getUsageRate());
            log.debug("SQL:   UNIT_CD_NUMERATOR="+pData.getUnitCdNumerator());
            log.debug("SQL:   UNIT_CD_DENOMINATOR="+pData.getUnitCdDenominator());
            log.debug("SQL:   SHARING_PERCENT="+pData.getSharingPercent());
            log.debug("SQL:   FLOOR_CARE_ID="+pData.getFloorCareId());
            log.debug("SQL:   FLOOR_TYPE_CD="+pData.getFloorTypeCd());
            log.debug("SQL:   DILUTION_RATE="+pData.getDilutionRate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   UI_FILTER="+pData.getUiFilter());
            log.debug("SQL:   UNIT_CD_DENOMINATOR1="+pData.getUnitCdDenominator1());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ProdApplData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProdApplId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProdApplId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PROD_APPL WHERE PROD_APPL_ID = " + pProdApplId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ProdApplData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PROD_APPL");
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
     * Inserts a ProdApplData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProdApplData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ProdApplData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PROD_APPL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PROD_APPL_ID,ITEM_ID,CLEANING_PROC_ID,ESTIMATOR_PRODUCT_CD,ESTIMATOR_FACILITY_ID,CLEANING_SCHED_STRUCT_ID,USAGE_RATE,UNIT_CD_NUMERATOR,UNIT_CD_DENOMINATOR,SHARING_PERCENT,FLOOR_CARE_ID,FLOOR_TYPE_CD,DILUTION_RATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UI_FILTER,UNIT_CD_DENOMINATOR1) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getProdApplId());
        pstmt.setInt(2+4,pData.getItemId());
        if (pData.getCleaningProcId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getCleaningProcId());
        }

        pstmt.setString(4+4,pData.getEstimatorProductCd());
        if (pData.getEstimatorFacilityId() == 0) {
            pstmt.setNull(5+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(5+4,pData.getEstimatorFacilityId());
        }

        if (pData.getCleaningSchedStructId() == 0) {
            pstmt.setNull(6+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(6+4,pData.getCleaningSchedStructId());
        }

        pstmt.setBigDecimal(7+4,pData.getUsageRate());
        pstmt.setString(8+4,pData.getUnitCdNumerator());
        pstmt.setString(9+4,pData.getUnitCdDenominator());
        pstmt.setBigDecimal(10+4,pData.getSharingPercent());
        pstmt.setInt(11+4,pData.getFloorCareId());
        pstmt.setString(12+4,pData.getFloorTypeCd());
        pstmt.setBigDecimal(13+4,pData.getDilutionRate());
        pstmt.setTimestamp(14+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(15+4,pData.getAddBy());
        pstmt.setTimestamp(16+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(17+4,pData.getModBy());
        pstmt.setBigDecimal(18+4,pData.getUiFilter());
        pstmt.setString(19+4,pData.getUnitCdDenominator1());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ProdApplData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProdApplData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ProdApplData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProdApplData insert(Connection pCon, ProdApplData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ProdApplData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProdApplData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProdApplData pData, boolean pLogFl)
        throws SQLException {
        ProdApplData oldData = null;
        if(pLogFl) {
          int id = pData.getProdApplId();
          try {
          oldData = ProdApplDataAccess.select(pCon,id);
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
     * Deletes a ProdApplData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProdApplId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProdApplId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PROD_APPL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PROD_APPL d WHERE PROD_APPL_ID = " + pProdApplId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pProdApplId);
        return n;
     }

    /**
     * Deletes ProdApplData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PROD_APPL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PROD_APPL d ");
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

