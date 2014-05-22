
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ProductUomPackDataAccess
 * Description:  This class is used to build access methods to the CLW_PRODUCT_UOM_PACK table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ProductUomPackData;
import com.cleanwise.service.api.value.ProductUomPackDataVector;
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
 * <code>ProductUomPackDataAccess</code>
 */
public class ProductUomPackDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ProductUomPackDataAccess.class.getName());

    /** <code>CLW_PRODUCT_UOM_PACK</code> table name */
	/* Primary key: PRODUCT_UOM_PACK_ID */
	
    public static final String CLW_PRODUCT_UOM_PACK = "CLW_PRODUCT_UOM_PACK";
    
    /** <code>PRODUCT_UOM_PACK_ID</code> PRODUCT_UOM_PACK_ID column of table CLW_PRODUCT_UOM_PACK */
    public static final String PRODUCT_UOM_PACK_ID = "PRODUCT_UOM_PACK_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_PRODUCT_UOM_PACK */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>ESTIMATOR_PRODUCT_CD</code> ESTIMATOR_PRODUCT_CD column of table CLW_PRODUCT_UOM_PACK */
    public static final String ESTIMATOR_PRODUCT_CD = "ESTIMATOR_PRODUCT_CD";
    /** <code>UNIT_SIZE</code> UNIT_SIZE column of table CLW_PRODUCT_UOM_PACK */
    public static final String UNIT_SIZE = "UNIT_SIZE";
    /** <code>UNIT_CD</code> UNIT_CD column of table CLW_PRODUCT_UOM_PACK */
    public static final String UNIT_CD = "UNIT_CD";
    /** <code>PACK_QTY</code> PACK_QTY column of table CLW_PRODUCT_UOM_PACK */
    public static final String PACK_QTY = "PACK_QTY";
    /** <code>CHEMICAL_USAGE_MODEL_CD</code> CHEMICAL_USAGE_MODEL_CD column of table CLW_PRODUCT_UOM_PACK */
    public static final String CHEMICAL_USAGE_MODEL_CD = "CHEMICAL_USAGE_MODEL_CD";
    /** <code>LINER_SIZE_CD</code> LINER_SIZE_CD column of table CLW_PRODUCT_UOM_PACK */
    public static final String LINER_SIZE_CD = "LINER_SIZE_CD";
    /** <code>APPEARANCE_STANDARD_CD</code> APPEARANCE_STANDARD_CD column of table CLW_PRODUCT_UOM_PACK */
    public static final String APPEARANCE_STANDARD_CD = "APPEARANCE_STANDARD_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PRODUCT_UOM_PACK */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PRODUCT_UOM_PACK */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PRODUCT_UOM_PACK */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PRODUCT_UOM_PACK */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ProductUomPackDataAccess()
    {
    }

    /**
     * Gets a ProductUomPackData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pProductUomPackId The key requested.
     * @return new ProductUomPackData()
     * @throws            SQLException
     */
    public static ProductUomPackData select(Connection pCon, int pProductUomPackId)
        throws SQLException, DataNotFoundException {
        ProductUomPackData x=null;
        String sql="SELECT PRODUCT_UOM_PACK_ID,ITEM_ID,ESTIMATOR_PRODUCT_CD,UNIT_SIZE,UNIT_CD,PACK_QTY,CHEMICAL_USAGE_MODEL_CD,LINER_SIZE_CD,APPEARANCE_STANDARD_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRODUCT_UOM_PACK WHERE PRODUCT_UOM_PACK_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pProductUomPackId=" + pProductUomPackId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pProductUomPackId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ProductUomPackData.createValue();
            
            x.setProductUomPackId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setEstimatorProductCd(rs.getString(3));
            x.setUnitSize(rs.getBigDecimal(4));
            x.setUnitCd(rs.getString(5));
            x.setPackQty(rs.getInt(6));
            x.setChemicalUsageModelCd(rs.getString(7));
            x.setLinerSizeCd(rs.getString(8));
            x.setAppearanceStandardCd(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PRODUCT_UOM_PACK_ID :" + pProductUomPackId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ProductUomPackDataVector object that consists
     * of ProductUomPackData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ProductUomPackDataVector()
     * @throws            SQLException
     */
    public static ProductUomPackDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ProductUomPackData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PRODUCT_UOM_PACK.PRODUCT_UOM_PACK_ID,CLW_PRODUCT_UOM_PACK.ITEM_ID,CLW_PRODUCT_UOM_PACK.ESTIMATOR_PRODUCT_CD,CLW_PRODUCT_UOM_PACK.UNIT_SIZE,CLW_PRODUCT_UOM_PACK.UNIT_CD,CLW_PRODUCT_UOM_PACK.PACK_QTY,CLW_PRODUCT_UOM_PACK.CHEMICAL_USAGE_MODEL_CD,CLW_PRODUCT_UOM_PACK.LINER_SIZE_CD,CLW_PRODUCT_UOM_PACK.APPEARANCE_STANDARD_CD,CLW_PRODUCT_UOM_PACK.ADD_DATE,CLW_PRODUCT_UOM_PACK.ADD_BY,CLW_PRODUCT_UOM_PACK.MOD_DATE,CLW_PRODUCT_UOM_PACK.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ProductUomPackData Object.
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
    *@returns a populated ProductUomPackData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ProductUomPackData x = ProductUomPackData.createValue();
         
         x.setProductUomPackId(rs.getInt(1+offset));
         x.setItemId(rs.getInt(2+offset));
         x.setEstimatorProductCd(rs.getString(3+offset));
         x.setUnitSize(rs.getBigDecimal(4+offset));
         x.setUnitCd(rs.getString(5+offset));
         x.setPackQty(rs.getInt(6+offset));
         x.setChemicalUsageModelCd(rs.getString(7+offset));
         x.setLinerSizeCd(rs.getString(8+offset));
         x.setAppearanceStandardCd(rs.getString(9+offset));
         x.setAddDate(rs.getTimestamp(10+offset));
         x.setAddBy(rs.getString(11+offset));
         x.setModDate(rs.getTimestamp(12+offset));
         x.setModBy(rs.getString(13+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ProductUomPackData Object represents.
    */
    public int getColumnCount(){
        return 13;
    }

    /**
     * Gets a ProductUomPackDataVector object that consists
     * of ProductUomPackData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ProductUomPackDataVector()
     * @throws            SQLException
     */
    public static ProductUomPackDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PRODUCT_UOM_PACK_ID,ITEM_ID,ESTIMATOR_PRODUCT_CD,UNIT_SIZE,UNIT_CD,PACK_QTY,CHEMICAL_USAGE_MODEL_CD,LINER_SIZE_CD,APPEARANCE_STANDARD_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRODUCT_UOM_PACK");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PRODUCT_UOM_PACK.PRODUCT_UOM_PACK_ID,CLW_PRODUCT_UOM_PACK.ITEM_ID,CLW_PRODUCT_UOM_PACK.ESTIMATOR_PRODUCT_CD,CLW_PRODUCT_UOM_PACK.UNIT_SIZE,CLW_PRODUCT_UOM_PACK.UNIT_CD,CLW_PRODUCT_UOM_PACK.PACK_QTY,CLW_PRODUCT_UOM_PACK.CHEMICAL_USAGE_MODEL_CD,CLW_PRODUCT_UOM_PACK.LINER_SIZE_CD,CLW_PRODUCT_UOM_PACK.APPEARANCE_STANDARD_CD,CLW_PRODUCT_UOM_PACK.ADD_DATE,CLW_PRODUCT_UOM_PACK.ADD_BY,CLW_PRODUCT_UOM_PACK.MOD_DATE,CLW_PRODUCT_UOM_PACK.MOD_BY FROM CLW_PRODUCT_UOM_PACK");
                where = pCriteria.getSqlClause("CLW_PRODUCT_UOM_PACK");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRODUCT_UOM_PACK.equals(otherTable)){
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
        ProductUomPackDataVector v = new ProductUomPackDataVector();
        while (rs.next()) {
            ProductUomPackData x = ProductUomPackData.createValue();
            
            x.setProductUomPackId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setEstimatorProductCd(rs.getString(3));
            x.setUnitSize(rs.getBigDecimal(4));
            x.setUnitCd(rs.getString(5));
            x.setPackQty(rs.getInt(6));
            x.setChemicalUsageModelCd(rs.getString(7));
            x.setLinerSizeCd(rs.getString(8));
            x.setAppearanceStandardCd(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ProductUomPackDataVector object that consists
     * of ProductUomPackData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ProductUomPackData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ProductUomPackDataVector()
     * @throws            SQLException
     */
    public static ProductUomPackDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ProductUomPackDataVector v = new ProductUomPackDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PRODUCT_UOM_PACK_ID,ITEM_ID,ESTIMATOR_PRODUCT_CD,UNIT_SIZE,UNIT_CD,PACK_QTY,CHEMICAL_USAGE_MODEL_CD,LINER_SIZE_CD,APPEARANCE_STANDARD_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRODUCT_UOM_PACK WHERE PRODUCT_UOM_PACK_ID IN (");

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
            ProductUomPackData x=null;
            while (rs.next()) {
                // build the object
                x=ProductUomPackData.createValue();
                
                x.setProductUomPackId(rs.getInt(1));
                x.setItemId(rs.getInt(2));
                x.setEstimatorProductCd(rs.getString(3));
                x.setUnitSize(rs.getBigDecimal(4));
                x.setUnitCd(rs.getString(5));
                x.setPackQty(rs.getInt(6));
                x.setChemicalUsageModelCd(rs.getString(7));
                x.setLinerSizeCd(rs.getString(8));
                x.setAppearanceStandardCd(rs.getString(9));
                x.setAddDate(rs.getTimestamp(10));
                x.setAddBy(rs.getString(11));
                x.setModDate(rs.getTimestamp(12));
                x.setModBy(rs.getString(13));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ProductUomPackDataVector object of all
     * ProductUomPackData objects in the database.
     * @param pCon An open database connection.
     * @return new ProductUomPackDataVector()
     * @throws            SQLException
     */
    public static ProductUomPackDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PRODUCT_UOM_PACK_ID,ITEM_ID,ESTIMATOR_PRODUCT_CD,UNIT_SIZE,UNIT_CD,PACK_QTY,CHEMICAL_USAGE_MODEL_CD,LINER_SIZE_CD,APPEARANCE_STANDARD_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRODUCT_UOM_PACK";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ProductUomPackDataVector v = new ProductUomPackDataVector();
        ProductUomPackData x = null;
        while (rs.next()) {
            // build the object
            x = ProductUomPackData.createValue();
            
            x.setProductUomPackId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setEstimatorProductCd(rs.getString(3));
            x.setUnitSize(rs.getBigDecimal(4));
            x.setUnitCd(rs.getString(5));
            x.setPackQty(rs.getInt(6));
            x.setChemicalUsageModelCd(rs.getString(7));
            x.setLinerSizeCd(rs.getString(8));
            x.setAppearanceStandardCd(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ProductUomPackData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PRODUCT_UOM_PACK_ID FROM CLW_PRODUCT_UOM_PACK");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRODUCT_UOM_PACK");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRODUCT_UOM_PACK");
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
     * Inserts a ProductUomPackData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProductUomPackData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ProductUomPackData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProductUomPackData insert(Connection pCon, ProductUomPackData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PRODUCT_UOM_PACK_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PRODUCT_UOM_PACK_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setProductUomPackId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PRODUCT_UOM_PACK (PRODUCT_UOM_PACK_ID,ITEM_ID,ESTIMATOR_PRODUCT_CD,UNIT_SIZE,UNIT_CD,PACK_QTY,CHEMICAL_USAGE_MODEL_CD,LINER_SIZE_CD,APPEARANCE_STANDARD_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getProductUomPackId());
        if (pData.getItemId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getItemId());
        }

        pstmt.setString(3,pData.getEstimatorProductCd());
        pstmt.setBigDecimal(4,pData.getUnitSize());
        pstmt.setString(5,pData.getUnitCd());
        pstmt.setInt(6,pData.getPackQty());
        pstmt.setString(7,pData.getChemicalUsageModelCd());
        pstmt.setString(8,pData.getLinerSizeCd());
        pstmt.setString(9,pData.getAppearanceStandardCd());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11,pData.getAddBy());
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRODUCT_UOM_PACK_ID="+pData.getProductUomPackId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   ESTIMATOR_PRODUCT_CD="+pData.getEstimatorProductCd());
            log.debug("SQL:   UNIT_SIZE="+pData.getUnitSize());
            log.debug("SQL:   UNIT_CD="+pData.getUnitCd());
            log.debug("SQL:   PACK_QTY="+pData.getPackQty());
            log.debug("SQL:   CHEMICAL_USAGE_MODEL_CD="+pData.getChemicalUsageModelCd());
            log.debug("SQL:   LINER_SIZE_CD="+pData.getLinerSizeCd());
            log.debug("SQL:   APPEARANCE_STANDARD_CD="+pData.getAppearanceStandardCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setProductUomPackId(0);
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
     * Updates a ProductUomPackData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProductUomPackData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProductUomPackData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PRODUCT_UOM_PACK SET ITEM_ID = ?,ESTIMATOR_PRODUCT_CD = ?,UNIT_SIZE = ?,UNIT_CD = ?,PACK_QTY = ?,CHEMICAL_USAGE_MODEL_CD = ?,LINER_SIZE_CD = ?,APPEARANCE_STANDARD_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE PRODUCT_UOM_PACK_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getItemId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getItemId());
        }

        pstmt.setString(i++,pData.getEstimatorProductCd());
        pstmt.setBigDecimal(i++,pData.getUnitSize());
        pstmt.setString(i++,pData.getUnitCd());
        pstmt.setInt(i++,pData.getPackQty());
        pstmt.setString(i++,pData.getChemicalUsageModelCd());
        pstmt.setString(i++,pData.getLinerSizeCd());
        pstmt.setString(i++,pData.getAppearanceStandardCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getProductUomPackId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   ESTIMATOR_PRODUCT_CD="+pData.getEstimatorProductCd());
            log.debug("SQL:   UNIT_SIZE="+pData.getUnitSize());
            log.debug("SQL:   UNIT_CD="+pData.getUnitCd());
            log.debug("SQL:   PACK_QTY="+pData.getPackQty());
            log.debug("SQL:   CHEMICAL_USAGE_MODEL_CD="+pData.getChemicalUsageModelCd());
            log.debug("SQL:   LINER_SIZE_CD="+pData.getLinerSizeCd());
            log.debug("SQL:   APPEARANCE_STANDARD_CD="+pData.getAppearanceStandardCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ProductUomPackData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProductUomPackId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProductUomPackId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PRODUCT_UOM_PACK WHERE PRODUCT_UOM_PACK_ID = " + pProductUomPackId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ProductUomPackData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PRODUCT_UOM_PACK");
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
     * Inserts a ProductUomPackData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProductUomPackData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ProductUomPackData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PRODUCT_UOM_PACK (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PRODUCT_UOM_PACK_ID,ITEM_ID,ESTIMATOR_PRODUCT_CD,UNIT_SIZE,UNIT_CD,PACK_QTY,CHEMICAL_USAGE_MODEL_CD,LINER_SIZE_CD,APPEARANCE_STANDARD_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getProductUomPackId());
        if (pData.getItemId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getItemId());
        }

        pstmt.setString(3+4,pData.getEstimatorProductCd());
        pstmt.setBigDecimal(4+4,pData.getUnitSize());
        pstmt.setString(5+4,pData.getUnitCd());
        pstmt.setInt(6+4,pData.getPackQty());
        pstmt.setString(7+4,pData.getChemicalUsageModelCd());
        pstmt.setString(8+4,pData.getLinerSizeCd());
        pstmt.setString(9+4,pData.getAppearanceStandardCd());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11+4,pData.getAddBy());
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ProductUomPackData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProductUomPackData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ProductUomPackData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProductUomPackData insert(Connection pCon, ProductUomPackData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ProductUomPackData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProductUomPackData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProductUomPackData pData, boolean pLogFl)
        throws SQLException {
        ProductUomPackData oldData = null;
        if(pLogFl) {
          int id = pData.getProductUomPackId();
          try {
          oldData = ProductUomPackDataAccess.select(pCon,id);
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
     * Deletes a ProductUomPackData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProductUomPackId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProductUomPackId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PRODUCT_UOM_PACK SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRODUCT_UOM_PACK d WHERE PRODUCT_UOM_PACK_ID = " + pProductUomPackId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pProductUomPackId);
        return n;
     }

    /**
     * Deletes ProductUomPackData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PRODUCT_UOM_PACK SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRODUCT_UOM_PACK d ");
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

