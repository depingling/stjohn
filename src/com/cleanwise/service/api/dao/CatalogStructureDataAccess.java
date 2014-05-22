
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CatalogStructureDataAccess
 * Description:  This class is used to build access methods to the CLW_CATALOG_STRUCTURE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Category;

import com.cleanwise.service.api.cachecos.Cachecos;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.CatalogStructureDataVector;
import com.cleanwise.service.api.value.IdVector;

/**
 * <code>CatalogStructureDataAccess</code>
 */
public class CatalogStructureDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CatalogStructureDataAccess.class.getName());

    /** <code>CLW_CATALOG_STRUCTURE</code> table name */
	/* Primary key: CATALOG_STRUCTURE_ID */
	
    public static final String CLW_CATALOG_STRUCTURE = "CLW_CATALOG_STRUCTURE";
    
    /** <code>CATALOG_STRUCTURE_ID</code> CATALOG_STRUCTURE_ID column of table CLW_CATALOG_STRUCTURE */
    public static final String CATALOG_STRUCTURE_ID = "CATALOG_STRUCTURE_ID";
    /** <code>CATALOG_ID</code> CATALOG_ID column of table CLW_CATALOG_STRUCTURE */
    public static final String CATALOG_ID = "CATALOG_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_CATALOG_STRUCTURE */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>CATALOG_STRUCTURE_CD</code> CATALOG_STRUCTURE_CD column of table CLW_CATALOG_STRUCTURE */
    public static final String CATALOG_STRUCTURE_CD = "CATALOG_STRUCTURE_CD";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_CATALOG_STRUCTURE */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>CUSTOMER_SKU_NUM</code> CUSTOMER_SKU_NUM column of table CLW_CATALOG_STRUCTURE */
    public static final String CUSTOMER_SKU_NUM = "CUSTOMER_SKU_NUM";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_CATALOG_STRUCTURE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_CATALOG_STRUCTURE */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_CATALOG_STRUCTURE */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_CATALOG_STRUCTURE */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CATALOG_STRUCTURE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CATALOG_STRUCTURE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CATALOG_STRUCTURE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CATALOG_STRUCTURE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>COST_CENTER_ID</code> COST_CENTER_ID column of table CLW_CATALOG_STRUCTURE */
    public static final String COST_CENTER_ID = "COST_CENTER_ID";
    /** <code>TAX_EXEMPT</code> TAX_EXEMPT column of table CLW_CATALOG_STRUCTURE */
    public static final String TAX_EXEMPT = "TAX_EXEMPT";
    /** <code>ITEM_GROUP_ID</code> ITEM_GROUP_ID column of table CLW_CATALOG_STRUCTURE */
    public static final String ITEM_GROUP_ID = "ITEM_GROUP_ID";
    /** <code>SPECIAL_PERMISSION</code> SPECIAL_PERMISSION column of table CLW_CATALOG_STRUCTURE */
    public static final String SPECIAL_PERMISSION = "SPECIAL_PERMISSION";
    /** <code>SORT_ORDER</code> SORT_ORDER column of table CLW_CATALOG_STRUCTURE */
    public static final String SORT_ORDER = "SORT_ORDER";

    /**
     * Constructor.
     */
    public CatalogStructureDataAccess()
    {
    }

    /**
     * Gets a CatalogStructureData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCatalogStructureId The key requested.
     * @return new CatalogStructureData()
     * @throws            SQLException
     */
    public static CatalogStructureData select(Connection pCon, int pCatalogStructureId)
        throws SQLException, DataNotFoundException {
        CatalogStructureData x=null;
        String sql="SELECT CATALOG_STRUCTURE_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_STRUCTURE_CD,ITEM_ID,CUSTOMER_SKU_NUM,SHORT_DESC,EFF_DATE,EXP_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,COST_CENTER_ID,TAX_EXEMPT,ITEM_GROUP_ID,SPECIAL_PERMISSION,SORT_ORDER FROM CLW_CATALOG_STRUCTURE WHERE CATALOG_STRUCTURE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCatalogStructureId=" + pCatalogStructureId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCatalogStructureId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CatalogStructureData.createValue();
            
            x.setCatalogStructureId(rs.getInt(1));
            x.setCatalogId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setCatalogStructureCd(rs.getString(4));
            x.setItemId(rs.getInt(5));
            x.setCustomerSkuNum(rs.getString(6));
            x.setShortDesc(rs.getString(7));
            x.setEffDate(rs.getDate(8));
            x.setExpDate(rs.getDate(9));
            x.setStatusCd(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setCostCenterId(rs.getInt(15));
            x.setTaxExempt(rs.getString(16));
            x.setItemGroupId(rs.getInt(17));
            x.setSpecialPermission(rs.getString(18));
            x.setSortOrder(rs.getInt(19));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CATALOG_STRUCTURE_ID :" + pCatalogStructureId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CatalogStructureDataVector object that consists
     * of CatalogStructureData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CatalogStructureDataVector()
     * @throws            SQLException
     */
    public static CatalogStructureDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CatalogStructureData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CATALOG_STRUCTURE.CATALOG_STRUCTURE_ID,CLW_CATALOG_STRUCTURE.CATALOG_ID,CLW_CATALOG_STRUCTURE.BUS_ENTITY_ID,CLW_CATALOG_STRUCTURE.CATALOG_STRUCTURE_CD,CLW_CATALOG_STRUCTURE.ITEM_ID,CLW_CATALOG_STRUCTURE.CUSTOMER_SKU_NUM,CLW_CATALOG_STRUCTURE.SHORT_DESC,CLW_CATALOG_STRUCTURE.EFF_DATE,CLW_CATALOG_STRUCTURE.EXP_DATE,CLW_CATALOG_STRUCTURE.STATUS_CD,CLW_CATALOG_STRUCTURE.ADD_DATE,CLW_CATALOG_STRUCTURE.ADD_BY,CLW_CATALOG_STRUCTURE.MOD_DATE,CLW_CATALOG_STRUCTURE.MOD_BY,CLW_CATALOG_STRUCTURE.COST_CENTER_ID,CLW_CATALOG_STRUCTURE.TAX_EXEMPT,CLW_CATALOG_STRUCTURE.ITEM_GROUP_ID,CLW_CATALOG_STRUCTURE.SPECIAL_PERMISSION,CLW_CATALOG_STRUCTURE.SORT_ORDER";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CatalogStructureData Object.
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
    *@returns a populated CatalogStructureData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CatalogStructureData x = CatalogStructureData.createValue();
         
         x.setCatalogStructureId(rs.getInt(1+offset));
         x.setCatalogId(rs.getInt(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setCatalogStructureCd(rs.getString(4+offset));
         x.setItemId(rs.getInt(5+offset));
         x.setCustomerSkuNum(rs.getString(6+offset));
         x.setShortDesc(rs.getString(7+offset));
         x.setEffDate(rs.getDate(8+offset));
         x.setExpDate(rs.getDate(9+offset));
         x.setStatusCd(rs.getString(10+offset));
         x.setAddDate(rs.getTimestamp(11+offset));
         x.setAddBy(rs.getString(12+offset));
         x.setModDate(rs.getTimestamp(13+offset));
         x.setModBy(rs.getString(14+offset));
         x.setCostCenterId(rs.getInt(15+offset));
         x.setTaxExempt(rs.getString(16+offset));
         x.setItemGroupId(rs.getInt(17+offset));
         x.setSpecialPermission(rs.getString(18+offset));
         x.setSortOrder(rs.getInt(19+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CatalogStructureData Object represents.
    */
    public int getColumnCount(){
        return 19;
    }

    /**
     * Gets a CatalogStructureDataVector object that consists
     * of CatalogStructureData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CatalogStructureDataVector()
     * @throws            SQLException
     */
    public static CatalogStructureDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CATALOG_STRUCTURE_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_STRUCTURE_CD,ITEM_ID,CUSTOMER_SKU_NUM,SHORT_DESC,EFF_DATE,EXP_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,COST_CENTER_ID,TAX_EXEMPT,ITEM_GROUP_ID,SPECIAL_PERMISSION,SORT_ORDER FROM CLW_CATALOG_STRUCTURE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CATALOG_STRUCTURE.CATALOG_STRUCTURE_ID,CLW_CATALOG_STRUCTURE.CATALOG_ID,CLW_CATALOG_STRUCTURE.BUS_ENTITY_ID,CLW_CATALOG_STRUCTURE.CATALOG_STRUCTURE_CD,CLW_CATALOG_STRUCTURE.ITEM_ID,CLW_CATALOG_STRUCTURE.CUSTOMER_SKU_NUM,CLW_CATALOG_STRUCTURE.SHORT_DESC,CLW_CATALOG_STRUCTURE.EFF_DATE,CLW_CATALOG_STRUCTURE.EXP_DATE,CLW_CATALOG_STRUCTURE.STATUS_CD,CLW_CATALOG_STRUCTURE.ADD_DATE,CLW_CATALOG_STRUCTURE.ADD_BY,CLW_CATALOG_STRUCTURE.MOD_DATE,CLW_CATALOG_STRUCTURE.MOD_BY,CLW_CATALOG_STRUCTURE.COST_CENTER_ID,CLW_CATALOG_STRUCTURE.TAX_EXEMPT,CLW_CATALOG_STRUCTURE.ITEM_GROUP_ID,CLW_CATALOG_STRUCTURE.SPECIAL_PERMISSION,CLW_CATALOG_STRUCTURE.SORT_ORDER FROM CLW_CATALOG_STRUCTURE");
                where = pCriteria.getSqlClause("CLW_CATALOG_STRUCTURE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CATALOG_STRUCTURE.equals(otherTable)){
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
        CatalogStructureDataVector v = new CatalogStructureDataVector();
        while (rs.next()) {
            CatalogStructureData x = CatalogStructureData.createValue();
            
            x.setCatalogStructureId(rs.getInt(1));
            x.setCatalogId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setCatalogStructureCd(rs.getString(4));
            x.setItemId(rs.getInt(5));
            x.setCustomerSkuNum(rs.getString(6));
            x.setShortDesc(rs.getString(7));
            x.setEffDate(rs.getDate(8));
            x.setExpDate(rs.getDate(9));
            x.setStatusCd(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setCostCenterId(rs.getInt(15));
            x.setTaxExempt(rs.getString(16));
            x.setItemGroupId(rs.getInt(17));
            x.setSpecialPermission(rs.getString(18));
            x.setSortOrder(rs.getInt(19));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a CatalogStructureDataVector object that consists
     * of CatalogStructureData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CatalogStructureData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CatalogStructureDataVector()
     * @throws            SQLException
     */
    public static CatalogStructureDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CatalogStructureDataVector v = new CatalogStructureDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CATALOG_STRUCTURE_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_STRUCTURE_CD,ITEM_ID,CUSTOMER_SKU_NUM,SHORT_DESC,EFF_DATE,EXP_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,COST_CENTER_ID,TAX_EXEMPT,ITEM_GROUP_ID,SPECIAL_PERMISSION,SORT_ORDER FROM CLW_CATALOG_STRUCTURE WHERE CATALOG_STRUCTURE_ID IN (");

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
            CatalogStructureData x=null;
            while (rs.next()) {
                // build the object
                x=CatalogStructureData.createValue();
                
                x.setCatalogStructureId(rs.getInt(1));
                x.setCatalogId(rs.getInt(2));
                x.setBusEntityId(rs.getInt(3));
                x.setCatalogStructureCd(rs.getString(4));
                x.setItemId(rs.getInt(5));
                x.setCustomerSkuNum(rs.getString(6));
                x.setShortDesc(rs.getString(7));
                x.setEffDate(rs.getDate(8));
                x.setExpDate(rs.getDate(9));
                x.setStatusCd(rs.getString(10));
                x.setAddDate(rs.getTimestamp(11));
                x.setAddBy(rs.getString(12));
                x.setModDate(rs.getTimestamp(13));
                x.setModBy(rs.getString(14));
                x.setCostCenterId(rs.getInt(15));
                x.setTaxExempt(rs.getString(16));
                x.setItemGroupId(rs.getInt(17));
                x.setSpecialPermission(rs.getString(18));
                x.setSortOrder(rs.getInt(19));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a CatalogStructureDataVector object of all
     * CatalogStructureData objects in the database.
     * @param pCon An open database connection.
     * @return new CatalogStructureDataVector()
     * @throws            SQLException
     */
    public static CatalogStructureDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CATALOG_STRUCTURE_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_STRUCTURE_CD,ITEM_ID,CUSTOMER_SKU_NUM,SHORT_DESC,EFF_DATE,EXP_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,COST_CENTER_ID,TAX_EXEMPT,ITEM_GROUP_ID,SPECIAL_PERMISSION,SORT_ORDER FROM CLW_CATALOG_STRUCTURE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CatalogStructureDataVector v = new CatalogStructureDataVector();
        CatalogStructureData x = null;
        while (rs.next()) {
            // build the object
            x = CatalogStructureData.createValue();
            
            x.setCatalogStructureId(rs.getInt(1));
            x.setCatalogId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setCatalogStructureCd(rs.getString(4));
            x.setItemId(rs.getInt(5));
            x.setCustomerSkuNum(rs.getString(6));
            x.setShortDesc(rs.getString(7));
            x.setEffDate(rs.getDate(8));
            x.setExpDate(rs.getDate(9));
            x.setStatusCd(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setCostCenterId(rs.getInt(15));
            x.setTaxExempt(rs.getString(16));
            x.setItemGroupId(rs.getInt(17));
            x.setSpecialPermission(rs.getString(18));
            x.setSortOrder(rs.getInt(19));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * CatalogStructureData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CATALOG_STRUCTURE_ID FROM CLW_CATALOG_STRUCTURE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CATALOG_STRUCTURE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CATALOG_STRUCTURE");
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
     * Inserts a CatalogStructureData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogStructureData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CatalogStructureData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CatalogStructureData insert(Connection pCon, CatalogStructureData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CATALOG_STRUCTURE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CATALOG_STRUCTURE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCatalogStructureId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CATALOG_STRUCTURE (CATALOG_STRUCTURE_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_STRUCTURE_CD,ITEM_ID,CUSTOMER_SKU_NUM,SHORT_DESC,EFF_DATE,EXP_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,COST_CENTER_ID,TAX_EXEMPT,ITEM_GROUP_ID,SPECIAL_PERMISSION,SORT_ORDER) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCatalogStructureId());
        pstmt.setInt(2,pData.getCatalogId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getBusEntityId());
        }

        pstmt.setString(4,pData.getCatalogStructureCd());
        pstmt.setInt(5,pData.getItemId());
        pstmt.setString(6,pData.getCustomerSkuNum());
        pstmt.setString(7,pData.getShortDesc());
        pstmt.setDate(8,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(9,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(10,pData.getStatusCd());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12,pData.getAddBy());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14,pData.getModBy());
        pstmt.setInt(15,pData.getCostCenterId());
        pstmt.setString(16,pData.getTaxExempt());
        if (pData.getItemGroupId() == 0) {
            pstmt.setNull(17, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(17,pData.getItemGroupId());
        }

        pstmt.setString(18,pData.getSpecialPermission());
        pstmt.setInt(19,pData.getSortOrder());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CATALOG_STRUCTURE_ID="+pData.getCatalogStructureId());
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   CATALOG_STRUCTURE_CD="+pData.getCatalogStructureCd());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   CUSTOMER_SKU_NUM="+pData.getCustomerSkuNum());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   TAX_EXEMPT="+pData.getTaxExempt());
            log.debug("SQL:   ITEM_GROUP_ID="+pData.getItemGroupId());
            log.debug("SQL:   SPECIAL_PERMISSION="+pData.getSpecialPermission());
            log.debug("SQL:   SORT_ORDER="+pData.getSortOrder());
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
        pData.setCatalogStructureId(0);
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
     * Updates a CatalogStructureData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogStructureData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CatalogStructureData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CATALOG_STRUCTURE SET CATALOG_ID = ?,BUS_ENTITY_ID = ?,CATALOG_STRUCTURE_CD = ?,ITEM_ID = ?,CUSTOMER_SKU_NUM = ?,SHORT_DESC = ?,EFF_DATE = ?,EXP_DATE = ?,STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,COST_CENTER_ID = ?,TAX_EXEMPT = ?,ITEM_GROUP_ID = ?,SPECIAL_PERMISSION = ?,SORT_ORDER = ? WHERE CATALOG_STRUCTURE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getCatalogId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getCatalogStructureCd());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setString(i++,pData.getCustomerSkuNum());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getCostCenterId());
        pstmt.setString(i++,pData.getTaxExempt());
        if (pData.getItemGroupId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getItemGroupId());
        }

        pstmt.setString(i++,pData.getSpecialPermission());
        pstmt.setInt(i++,pData.getSortOrder());
        pstmt.setInt(i++,pData.getCatalogStructureId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   CATALOG_STRUCTURE_CD="+pData.getCatalogStructureCd());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   CUSTOMER_SKU_NUM="+pData.getCustomerSkuNum());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   TAX_EXEMPT="+pData.getTaxExempt());
            log.debug("SQL:   ITEM_GROUP_ID="+pData.getItemGroupId());
            log.debug("SQL:   SPECIAL_PERMISSION="+pData.getSpecialPermission());
            log.debug("SQL:   SORT_ORDER="+pData.getSortOrder());
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
     * Deletes a CatalogStructureData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCatalogStructureId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCatalogStructureId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CATALOG_STRUCTURE WHERE CATALOG_STRUCTURE_ID = " + pCatalogStructureId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CatalogStructureData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CATALOG_STRUCTURE");
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
     * Inserts a CatalogStructureData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogStructureData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CatalogStructureData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CATALOG_STRUCTURE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CATALOG_STRUCTURE_ID,CATALOG_ID,BUS_ENTITY_ID,CATALOG_STRUCTURE_CD,ITEM_ID,CUSTOMER_SKU_NUM,SHORT_DESC,EFF_DATE,EXP_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,COST_CENTER_ID,TAX_EXEMPT,ITEM_GROUP_ID,SPECIAL_PERMISSION,SORT_ORDER) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCatalogStructureId());
        pstmt.setInt(2+4,pData.getCatalogId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getBusEntityId());
        }

        pstmt.setString(4+4,pData.getCatalogStructureCd());
        pstmt.setInt(5+4,pData.getItemId());
        pstmt.setString(6+4,pData.getCustomerSkuNum());
        pstmt.setString(7+4,pData.getShortDesc());
        pstmt.setDate(8+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(9+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(10+4,pData.getStatusCd());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12+4,pData.getAddBy());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14+4,pData.getModBy());
        pstmt.setInt(15+4,pData.getCostCenterId());
        pstmt.setString(16+4,pData.getTaxExempt());
        if (pData.getItemGroupId() == 0) {
            pstmt.setNull(17+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(17+4,pData.getItemGroupId());
        }

        pstmt.setString(18+4,pData.getSpecialPermission());
        pstmt.setInt(19+4,pData.getSortOrder());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CatalogStructureData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogStructureData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CatalogStructureData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CatalogStructureData insert(Connection pCon, CatalogStructureData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CatalogStructureData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CatalogStructureData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CatalogStructureData pData, boolean pLogFl)
        throws SQLException {
        CatalogStructureData oldData = null;
        if(pLogFl) {
          int id = pData.getCatalogStructureId();
          try {
          oldData = CatalogStructureDataAccess.select(pCon,id);
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
     * Deletes a CatalogStructureData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCatalogStructureId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCatalogStructureId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CATALOG_STRUCTURE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CATALOG_STRUCTURE d WHERE CATALOG_STRUCTURE_ID = " + pCatalogStructureId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCatalogStructureId);
        return n;
     }

    /**
     * Deletes CatalogStructureData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CATALOG_STRUCTURE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CATALOG_STRUCTURE d ");
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
    
       
    /**
     * Gets Catalog structure ids.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return List<Integer> 
     * @throws            SQLException
     */
    public static List<Integer> getCatalogStructureIds(Connection pCon, DBCriteria pCriteria)
        throws SQLException{  
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        sqlBuf = new StringBuffer("SELECT CLW_CATALOG_STRUCTURE.CATALOG_ID FROM CLW_CATALOG_STRUCTURE");
        if(otherTables == null || otherTables.isEmpty()){
                where = pCriteria.getSqlClause();
        }else{
            where = pCriteria.getSqlClause("CLW_CATALOG_STRUCTURE");

            Iterator it = otherTables.iterator();
            while(it.hasNext()){
                    String otherTable = (String) it.next();
                    if(!CLW_CATALOG_STRUCTURE.equals(otherTable)){
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
        List<Integer> catalogIdList = new ArrayList<Integer>();
        while (rs.next()) {
        	catalogIdList.add(rs.getInt(1));
        }
        rs.close();
        stmt.close();

        return catalogIdList;
    }
    
///////////////////////////////////////////////
}

