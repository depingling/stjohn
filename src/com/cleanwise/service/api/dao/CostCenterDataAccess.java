
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CostCenterDataAccess
 * Description:  This class is used to build access methods to the CLW_COST_CENTER table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterDataVector;
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
 * <code>CostCenterDataAccess</code>
 */
public class CostCenterDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CostCenterDataAccess.class.getName());

    /** <code>CLW_COST_CENTER</code> table name */
	/* Primary key: COST_CENTER_ID */
	
    public static final String CLW_COST_CENTER = "CLW_COST_CENTER";
    
    /** <code>COST_CENTER_ID</code> COST_CENTER_ID column of table CLW_COST_CENTER */
    public static final String COST_CENTER_ID = "COST_CENTER_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_COST_CENTER */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>COST_CENTER_STATUS_CD</code> COST_CENTER_STATUS_CD column of table CLW_COST_CENTER */
    public static final String COST_CENTER_STATUS_CD = "COST_CENTER_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_COST_CENTER */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_COST_CENTER */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_COST_CENTER */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_COST_CENTER */
    public static final String MOD_BY = "MOD_BY";
    /** <code>COST_CENTER_TYPE_CD</code> COST_CENTER_TYPE_CD column of table CLW_COST_CENTER */
    public static final String COST_CENTER_TYPE_CD = "COST_CENTER_TYPE_CD";
    /** <code>ALLOCATE_FREIGHT</code> ALLOCATE_FREIGHT column of table CLW_COST_CENTER */
    public static final String ALLOCATE_FREIGHT = "ALLOCATE_FREIGHT";
    /** <code>ALLOCATE_DISCOUNT</code> ALLOCATE_DISCOUNT column of table CLW_COST_CENTER */
    public static final String ALLOCATE_DISCOUNT = "ALLOCATE_DISCOUNT";
    /** <code>COST_CENTER_TAX_TYPE</code> COST_CENTER_TAX_TYPE column of table CLW_COST_CENTER */
    public static final String COST_CENTER_TAX_TYPE = "COST_CENTER_TAX_TYPE";
    /** <code>STORE_ID</code> STORE_ID column of table CLW_COST_CENTER */
    public static final String STORE_ID = "STORE_ID";
    /** <code>COST_CENTER_CODE</code> COST_CENTER_CODE column of table CLW_COST_CENTER */
    public static final String COST_CENTER_CODE = "COST_CENTER_CODE";
    /** <code>NO_BUDGET</code> NO_BUDGET column of table CLW_COST_CENTER */
    public static final String NO_BUDGET = "NO_BUDGET";

    /**
     * Constructor.
     */
    public CostCenterDataAccess()
    {
    }

    /**
     * Gets a CostCenterData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCostCenterId The key requested.
     * @return new CostCenterData()
     * @throws            SQLException
     */
    public static CostCenterData select(Connection pCon, int pCostCenterId)
        throws SQLException, DataNotFoundException {
        CostCenterData x=null;
        String sql="SELECT COST_CENTER_ID,SHORT_DESC,COST_CENTER_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,COST_CENTER_TYPE_CD,ALLOCATE_FREIGHT,ALLOCATE_DISCOUNT,COST_CENTER_TAX_TYPE,STORE_ID,COST_CENTER_CODE,NO_BUDGET FROM CLW_COST_CENTER WHERE COST_CENTER_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCostCenterId=" + pCostCenterId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCostCenterId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CostCenterData.createValue();
            
            x.setCostCenterId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setCostCenterStatusCd(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setAddBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            x.setCostCenterTypeCd(rs.getString(8));
            x.setAllocateFreight(rs.getString(9));
            x.setAllocateDiscount(rs.getString(10));
            x.setCostCenterTaxType(rs.getString(11));
            x.setStoreId(rs.getInt(12));
            x.setCostCenterCode(rs.getString(13));
            x.setNoBudget(rs.getString(14));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("COST_CENTER_ID :" + pCostCenterId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CostCenterDataVector object that consists
     * of CostCenterData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CostCenterDataVector()
     * @throws            SQLException
     */
    public static CostCenterDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CostCenterData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_COST_CENTER.COST_CENTER_ID,CLW_COST_CENTER.SHORT_DESC,CLW_COST_CENTER.COST_CENTER_STATUS_CD,CLW_COST_CENTER.ADD_DATE,CLW_COST_CENTER.ADD_BY,CLW_COST_CENTER.MOD_DATE,CLW_COST_CENTER.MOD_BY,CLW_COST_CENTER.COST_CENTER_TYPE_CD,CLW_COST_CENTER.ALLOCATE_FREIGHT,CLW_COST_CENTER.ALLOCATE_DISCOUNT,CLW_COST_CENTER.COST_CENTER_TAX_TYPE,CLW_COST_CENTER.STORE_ID,CLW_COST_CENTER.COST_CENTER_CODE,CLW_COST_CENTER.NO_BUDGET";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CostCenterData Object.
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
    *@returns a populated CostCenterData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CostCenterData x = CostCenterData.createValue();
         
         x.setCostCenterId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setCostCenterStatusCd(rs.getString(3+offset));
         x.setAddDate(rs.getTimestamp(4+offset));
         x.setAddBy(rs.getString(5+offset));
         x.setModDate(rs.getTimestamp(6+offset));
         x.setModBy(rs.getString(7+offset));
         x.setCostCenterTypeCd(rs.getString(8+offset));
         x.setAllocateFreight(rs.getString(9+offset));
         x.setAllocateDiscount(rs.getString(10+offset));
         x.setCostCenterTaxType(rs.getString(11+offset));
         x.setStoreId(rs.getInt(12+offset));
         x.setCostCenterCode(rs.getString(13+offset));
         x.setNoBudget(rs.getString(14+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CostCenterData Object represents.
    */
    public int getColumnCount(){
        return 14;
    }

    /**
     * Gets a CostCenterDataVector object that consists
     * of CostCenterData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CostCenterDataVector()
     * @throws            SQLException
     */
    public static CostCenterDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT COST_CENTER_ID,SHORT_DESC,COST_CENTER_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,COST_CENTER_TYPE_CD,ALLOCATE_FREIGHT,ALLOCATE_DISCOUNT,COST_CENTER_TAX_TYPE,STORE_ID,COST_CENTER_CODE,NO_BUDGET FROM CLW_COST_CENTER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_COST_CENTER.COST_CENTER_ID,CLW_COST_CENTER.SHORT_DESC,CLW_COST_CENTER.COST_CENTER_STATUS_CD,CLW_COST_CENTER.ADD_DATE,CLW_COST_CENTER.ADD_BY,CLW_COST_CENTER.MOD_DATE,CLW_COST_CENTER.MOD_BY,CLW_COST_CENTER.COST_CENTER_TYPE_CD,CLW_COST_CENTER.ALLOCATE_FREIGHT,CLW_COST_CENTER.ALLOCATE_DISCOUNT,CLW_COST_CENTER.COST_CENTER_TAX_TYPE,CLW_COST_CENTER.STORE_ID,CLW_COST_CENTER.COST_CENTER_CODE,CLW_COST_CENTER.NO_BUDGET FROM CLW_COST_CENTER");
                where = pCriteria.getSqlClause("CLW_COST_CENTER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_COST_CENTER.equals(otherTable)){
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
        CostCenterDataVector v = new CostCenterDataVector();
        while (rs.next()) {
            CostCenterData x = CostCenterData.createValue();
            
            x.setCostCenterId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setCostCenterStatusCd(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setAddBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            x.setCostCenterTypeCd(rs.getString(8));
            x.setAllocateFreight(rs.getString(9));
            x.setAllocateDiscount(rs.getString(10));
            x.setCostCenterTaxType(rs.getString(11));
            x.setStoreId(rs.getInt(12));
            x.setCostCenterCode(rs.getString(13));
            x.setNoBudget(rs.getString(14));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a CostCenterDataVector object that consists
     * of CostCenterData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CostCenterData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CostCenterDataVector()
     * @throws            SQLException
     */
    public static CostCenterDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CostCenterDataVector v = new CostCenterDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT COST_CENTER_ID,SHORT_DESC,COST_CENTER_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,COST_CENTER_TYPE_CD,ALLOCATE_FREIGHT,ALLOCATE_DISCOUNT,COST_CENTER_TAX_TYPE,STORE_ID,COST_CENTER_CODE,NO_BUDGET FROM CLW_COST_CENTER WHERE COST_CENTER_ID IN (");

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
            CostCenterData x=null;
            while (rs.next()) {
                // build the object
                x=CostCenterData.createValue();
                
                x.setCostCenterId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setCostCenterStatusCd(rs.getString(3));
                x.setAddDate(rs.getTimestamp(4));
                x.setAddBy(rs.getString(5));
                x.setModDate(rs.getTimestamp(6));
                x.setModBy(rs.getString(7));
                x.setCostCenterTypeCd(rs.getString(8));
                x.setAllocateFreight(rs.getString(9));
                x.setAllocateDiscount(rs.getString(10));
                x.setCostCenterTaxType(rs.getString(11));
                x.setStoreId(rs.getInt(12));
                x.setCostCenterCode(rs.getString(13));
                x.setNoBudget(rs.getString(14));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a CostCenterDataVector object of all
     * CostCenterData objects in the database.
     * @param pCon An open database connection.
     * @return new CostCenterDataVector()
     * @throws            SQLException
     */
    public static CostCenterDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT COST_CENTER_ID,SHORT_DESC,COST_CENTER_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,COST_CENTER_TYPE_CD,ALLOCATE_FREIGHT,ALLOCATE_DISCOUNT,COST_CENTER_TAX_TYPE,STORE_ID,COST_CENTER_CODE,NO_BUDGET FROM CLW_COST_CENTER";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CostCenterDataVector v = new CostCenterDataVector();
        CostCenterData x = null;
        while (rs.next()) {
            // build the object
            x = CostCenterData.createValue();
            
            x.setCostCenterId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setCostCenterStatusCd(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setAddBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            x.setCostCenterTypeCd(rs.getString(8));
            x.setAllocateFreight(rs.getString(9));
            x.setAllocateDiscount(rs.getString(10));
            x.setCostCenterTaxType(rs.getString(11));
            x.setStoreId(rs.getInt(12));
            x.setCostCenterCode(rs.getString(13));
            x.setNoBudget(rs.getString(14));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * CostCenterData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT COST_CENTER_ID FROM CLW_COST_CENTER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COST_CENTER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COST_CENTER");
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
     * Inserts a CostCenterData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CostCenterData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CostCenterData insert(Connection pCon, CostCenterData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_COST_CENTER_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_COST_CENTER_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCostCenterId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_COST_CENTER (COST_CENTER_ID,SHORT_DESC,COST_CENTER_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,COST_CENTER_TYPE_CD,ALLOCATE_FREIGHT,ALLOCATE_DISCOUNT,COST_CENTER_TAX_TYPE,STORE_ID,COST_CENTER_CODE,NO_BUDGET) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCostCenterId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getCostCenterStatusCd());
        pstmt.setTimestamp(4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5,pData.getAddBy());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(7,pData.getModBy());
        pstmt.setString(8,pData.getCostCenterTypeCd());
        pstmt.setString(9,pData.getAllocateFreight());
        pstmt.setString(10,pData.getAllocateDiscount());
        pstmt.setString(11,pData.getCostCenterTaxType());
        if (pData.getStoreId() == 0) {
            pstmt.setNull(12, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(12,pData.getStoreId());
        }

        pstmt.setString(13,pData.getCostCenterCode());
        pstmt.setString(14,pData.getNoBudget());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   COST_CENTER_STATUS_CD="+pData.getCostCenterStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   COST_CENTER_TYPE_CD="+pData.getCostCenterTypeCd());
            log.debug("SQL:   ALLOCATE_FREIGHT="+pData.getAllocateFreight());
            log.debug("SQL:   ALLOCATE_DISCOUNT="+pData.getAllocateDiscount());
            log.debug("SQL:   COST_CENTER_TAX_TYPE="+pData.getCostCenterTaxType());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   COST_CENTER_CODE="+pData.getCostCenterCode());
            log.debug("SQL:   NO_BUDGET="+pData.getNoBudget());
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
        pData.setCostCenterId(0);
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
     * Updates a CostCenterData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CostCenterData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_COST_CENTER SET SHORT_DESC = ?,COST_CENTER_STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,COST_CENTER_TYPE_CD = ?,ALLOCATE_FREIGHT = ?,ALLOCATE_DISCOUNT = ?,COST_CENTER_TAX_TYPE = ?,STORE_ID = ?,COST_CENTER_CODE = ?,NO_BUDGET = ? WHERE COST_CENTER_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getCostCenterStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getCostCenterTypeCd());
        pstmt.setString(i++,pData.getAllocateFreight());
        pstmt.setString(i++,pData.getAllocateDiscount());
        pstmt.setString(i++,pData.getCostCenterTaxType());
        if (pData.getStoreId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getStoreId());
        }

        pstmt.setString(i++,pData.getCostCenterCode());
        pstmt.setString(i++,pData.getNoBudget());
        pstmt.setInt(i++,pData.getCostCenterId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   COST_CENTER_STATUS_CD="+pData.getCostCenterStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   COST_CENTER_TYPE_CD="+pData.getCostCenterTypeCd());
            log.debug("SQL:   ALLOCATE_FREIGHT="+pData.getAllocateFreight());
            log.debug("SQL:   ALLOCATE_DISCOUNT="+pData.getAllocateDiscount());
            log.debug("SQL:   COST_CENTER_TAX_TYPE="+pData.getCostCenterTaxType());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   COST_CENTER_CODE="+pData.getCostCenterCode());
            log.debug("SQL:   NO_BUDGET="+pData.getNoBudget());
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
     * Deletes a CostCenterData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCostCenterId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCostCenterId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_COST_CENTER WHERE COST_CENTER_ID = " + pCostCenterId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CostCenterData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_COST_CENTER");
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
     * Inserts a CostCenterData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CostCenterData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_COST_CENTER (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "COST_CENTER_ID,SHORT_DESC,COST_CENTER_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,COST_CENTER_TYPE_CD,ALLOCATE_FREIGHT,ALLOCATE_DISCOUNT,COST_CENTER_TAX_TYPE,STORE_ID,COST_CENTER_CODE,NO_BUDGET) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCostCenterId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getCostCenterStatusCd());
        pstmt.setTimestamp(4+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5+4,pData.getAddBy());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(7+4,pData.getModBy());
        pstmt.setString(8+4,pData.getCostCenterTypeCd());
        pstmt.setString(9+4,pData.getAllocateFreight());
        pstmt.setString(10+4,pData.getAllocateDiscount());
        pstmt.setString(11+4,pData.getCostCenterTaxType());
        if (pData.getStoreId() == 0) {
            pstmt.setNull(12+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(12+4,pData.getStoreId());
        }

        pstmt.setString(13+4,pData.getCostCenterCode());
        pstmt.setString(14+4,pData.getNoBudget());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CostCenterData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CostCenterData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CostCenterData insert(Connection pCon, CostCenterData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CostCenterData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CostCenterData pData, boolean pLogFl)
        throws SQLException {
        CostCenterData oldData = null;
        if(pLogFl) {
          int id = pData.getCostCenterId();
          try {
          oldData = CostCenterDataAccess.select(pCon,id);
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
     * Deletes a CostCenterData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCostCenterId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCostCenterId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_COST_CENTER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_COST_CENTER d WHERE COST_CENTER_ID = " + pCostCenterId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCostCenterId);
        return n;
     }

    /**
     * Deletes CostCenterData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_COST_CENTER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_COST_CENTER d ");
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

