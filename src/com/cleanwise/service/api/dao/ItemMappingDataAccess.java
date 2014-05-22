
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ItemMappingDataAccess
 * Description:  This class is used to build access methods to the CLW_ITEM_MAPPING table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
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
 * <code>ItemMappingDataAccess</code>
 */
public class ItemMappingDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ItemMappingDataAccess.class.getName());

    /** <code>CLW_ITEM_MAPPING</code> table name */
	/* Primary key: ITEM_MAPPING_ID */
	
    public static final String CLW_ITEM_MAPPING = "CLW_ITEM_MAPPING";
    
    /** <code>ITEM_MAPPING_ID</code> ITEM_MAPPING_ID column of table CLW_ITEM_MAPPING */
    public static final String ITEM_MAPPING_ID = "ITEM_MAPPING_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_ITEM_MAPPING */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_ITEM_MAPPING */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>ITEM_NUM</code> ITEM_NUM column of table CLW_ITEM_MAPPING */
    public static final String ITEM_NUM = "ITEM_NUM";
    /** <code>ITEM_UOM</code> ITEM_UOM column of table CLW_ITEM_MAPPING */
    public static final String ITEM_UOM = "ITEM_UOM";
    /** <code>ITEM_PACK</code> ITEM_PACK column of table CLW_ITEM_MAPPING */
    public static final String ITEM_PACK = "ITEM_PACK";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_ITEM_MAPPING */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_ITEM_MAPPING */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>ITEM_MAPPING_CD</code> ITEM_MAPPING_CD column of table CLW_ITEM_MAPPING */
    public static final String ITEM_MAPPING_CD = "ITEM_MAPPING_CD";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_ITEM_MAPPING */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_ITEM_MAPPING */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_ITEM_MAPPING */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ITEM_MAPPING */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ITEM_MAPPING */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ITEM_MAPPING */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ITEM_MAPPING */
    public static final String MOD_BY = "MOD_BY";
    /** <code>UOM_CONV_MULTIPLIER</code> UOM_CONV_MULTIPLIER column of table CLW_ITEM_MAPPING */
    public static final String UOM_CONV_MULTIPLIER = "UOM_CONV_MULTIPLIER";
    /** <code>STANDARD_PRODUCT_LIST</code> STANDARD_PRODUCT_LIST column of table CLW_ITEM_MAPPING */
    public static final String STANDARD_PRODUCT_LIST = "STANDARD_PRODUCT_LIST";

    /**
     * Constructor.
     */
    public ItemMappingDataAccess()
    {
    }

    /**
     * Gets a ItemMappingData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pItemMappingId The key requested.
     * @return new ItemMappingData()
     * @throws            SQLException
     */
    public static ItemMappingData select(Connection pCon, int pItemMappingId)
        throws SQLException, DataNotFoundException {
        ItemMappingData x=null;
        String sql="SELECT ITEM_MAPPING_ID,ITEM_ID,BUS_ENTITY_ID,ITEM_NUM,ITEM_UOM,ITEM_PACK,SHORT_DESC,LONG_DESC,ITEM_MAPPING_CD,EFF_DATE,EXP_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UOM_CONV_MULTIPLIER,STANDARD_PRODUCT_LIST FROM CLW_ITEM_MAPPING WHERE ITEM_MAPPING_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pItemMappingId=" + pItemMappingId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pItemMappingId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ItemMappingData.createValue();
            
            x.setItemMappingId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setItemNum(rs.getString(4));
            x.setItemUom(rs.getString(5));
            x.setItemPack(rs.getString(6));
            x.setShortDesc(rs.getString(7));
            x.setLongDesc(rs.getString(8));
            x.setItemMappingCd(rs.getString(9));
            x.setEffDate(rs.getDate(10));
            x.setExpDate(rs.getDate(11));
            x.setStatusCd(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setUomConvMultiplier(rs.getBigDecimal(17));
            x.setStandardProductList(rs.getString(18));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ITEM_MAPPING_ID :" + pItemMappingId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ItemMappingDataVector object that consists
     * of ItemMappingData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ItemMappingDataVector()
     * @throws            SQLException
     */
    public static ItemMappingDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ItemMappingData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ITEM_MAPPING.ITEM_MAPPING_ID,CLW_ITEM_MAPPING.ITEM_ID,CLW_ITEM_MAPPING.BUS_ENTITY_ID,CLW_ITEM_MAPPING.ITEM_NUM,CLW_ITEM_MAPPING.ITEM_UOM,CLW_ITEM_MAPPING.ITEM_PACK,CLW_ITEM_MAPPING.SHORT_DESC,CLW_ITEM_MAPPING.LONG_DESC,CLW_ITEM_MAPPING.ITEM_MAPPING_CD,CLW_ITEM_MAPPING.EFF_DATE,CLW_ITEM_MAPPING.EXP_DATE,CLW_ITEM_MAPPING.STATUS_CD,CLW_ITEM_MAPPING.ADD_DATE,CLW_ITEM_MAPPING.ADD_BY,CLW_ITEM_MAPPING.MOD_DATE,CLW_ITEM_MAPPING.MOD_BY,CLW_ITEM_MAPPING.UOM_CONV_MULTIPLIER,CLW_ITEM_MAPPING.STANDARD_PRODUCT_LIST";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ItemMappingData Object.
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
    *@returns a populated ItemMappingData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ItemMappingData x = ItemMappingData.createValue();
         
         x.setItemMappingId(rs.getInt(1+offset));
         x.setItemId(rs.getInt(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setItemNum(rs.getString(4+offset));
         x.setItemUom(rs.getString(5+offset));
         x.setItemPack(rs.getString(6+offset));
         x.setShortDesc(rs.getString(7+offset));
         x.setLongDesc(rs.getString(8+offset));
         x.setItemMappingCd(rs.getString(9+offset));
         x.setEffDate(rs.getDate(10+offset));
         x.setExpDate(rs.getDate(11+offset));
         x.setStatusCd(rs.getString(12+offset));
         x.setAddDate(rs.getTimestamp(13+offset));
         x.setAddBy(rs.getString(14+offset));
         x.setModDate(rs.getTimestamp(15+offset));
         x.setModBy(rs.getString(16+offset));
         x.setUomConvMultiplier(rs.getBigDecimal(17+offset));
         x.setStandardProductList(rs.getString(18+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ItemMappingData Object represents.
    */
    public int getColumnCount(){
        return 18;
    }

    /**
     * Gets a ItemMappingDataVector object that consists
     * of ItemMappingData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ItemMappingDataVector()
     * @throws            SQLException
     */
    public static ItemMappingDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ITEM_MAPPING_ID,ITEM_ID,BUS_ENTITY_ID,ITEM_NUM,ITEM_UOM,ITEM_PACK,SHORT_DESC,LONG_DESC,ITEM_MAPPING_CD,EFF_DATE,EXP_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UOM_CONV_MULTIPLIER,STANDARD_PRODUCT_LIST FROM CLW_ITEM_MAPPING");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ITEM_MAPPING.ITEM_MAPPING_ID,CLW_ITEM_MAPPING.ITEM_ID,CLW_ITEM_MAPPING.BUS_ENTITY_ID,CLW_ITEM_MAPPING.ITEM_NUM,CLW_ITEM_MAPPING.ITEM_UOM,CLW_ITEM_MAPPING.ITEM_PACK,CLW_ITEM_MAPPING.SHORT_DESC,CLW_ITEM_MAPPING.LONG_DESC,CLW_ITEM_MAPPING.ITEM_MAPPING_CD,CLW_ITEM_MAPPING.EFF_DATE,CLW_ITEM_MAPPING.EXP_DATE,CLW_ITEM_MAPPING.STATUS_CD,CLW_ITEM_MAPPING.ADD_DATE,CLW_ITEM_MAPPING.ADD_BY,CLW_ITEM_MAPPING.MOD_DATE,CLW_ITEM_MAPPING.MOD_BY,CLW_ITEM_MAPPING.UOM_CONV_MULTIPLIER,CLW_ITEM_MAPPING.STANDARD_PRODUCT_LIST FROM CLW_ITEM_MAPPING");
                where = pCriteria.getSqlClause("CLW_ITEM_MAPPING");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ITEM_MAPPING.equals(otherTable)){
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
        ItemMappingDataVector v = new ItemMappingDataVector();
        while (rs.next()) {
            ItemMappingData x = ItemMappingData.createValue();
            
            x.setItemMappingId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setItemNum(rs.getString(4));
            x.setItemUom(rs.getString(5));
            x.setItemPack(rs.getString(6));
            x.setShortDesc(rs.getString(7));
            x.setLongDesc(rs.getString(8));
            x.setItemMappingCd(rs.getString(9));
            x.setEffDate(rs.getDate(10));
            x.setExpDate(rs.getDate(11));
            x.setStatusCd(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setUomConvMultiplier(rs.getBigDecimal(17));
            x.setStandardProductList(rs.getString(18));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ItemMappingDataVector object that consists
     * of ItemMappingData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ItemMappingData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ItemMappingDataVector()
     * @throws            SQLException
     */
    public static ItemMappingDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ItemMappingDataVector v = new ItemMappingDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_MAPPING_ID,ITEM_ID,BUS_ENTITY_ID,ITEM_NUM,ITEM_UOM,ITEM_PACK,SHORT_DESC,LONG_DESC,ITEM_MAPPING_CD,EFF_DATE,EXP_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UOM_CONV_MULTIPLIER,STANDARD_PRODUCT_LIST FROM CLW_ITEM_MAPPING WHERE ITEM_MAPPING_ID IN (");

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
            ItemMappingData x=null;
            while (rs.next()) {
                // build the object
                x=ItemMappingData.createValue();
                
                x.setItemMappingId(rs.getInt(1));
                x.setItemId(rs.getInt(2));
                x.setBusEntityId(rs.getInt(3));
                x.setItemNum(rs.getString(4));
                x.setItemUom(rs.getString(5));
                x.setItemPack(rs.getString(6));
                x.setShortDesc(rs.getString(7));
                x.setLongDesc(rs.getString(8));
                x.setItemMappingCd(rs.getString(9));
                x.setEffDate(rs.getDate(10));
                x.setExpDate(rs.getDate(11));
                x.setStatusCd(rs.getString(12));
                x.setAddDate(rs.getTimestamp(13));
                x.setAddBy(rs.getString(14));
                x.setModDate(rs.getTimestamp(15));
                x.setModBy(rs.getString(16));
                x.setUomConvMultiplier(rs.getBigDecimal(17));
                x.setStandardProductList(rs.getString(18));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ItemMappingDataVector object of all
     * ItemMappingData objects in the database.
     * @param pCon An open database connection.
     * @return new ItemMappingDataVector()
     * @throws            SQLException
     */
    public static ItemMappingDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ITEM_MAPPING_ID,ITEM_ID,BUS_ENTITY_ID,ITEM_NUM,ITEM_UOM,ITEM_PACK,SHORT_DESC,LONG_DESC,ITEM_MAPPING_CD,EFF_DATE,EXP_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UOM_CONV_MULTIPLIER,STANDARD_PRODUCT_LIST FROM CLW_ITEM_MAPPING";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ItemMappingDataVector v = new ItemMappingDataVector();
        ItemMappingData x = null;
        while (rs.next()) {
            // build the object
            x = ItemMappingData.createValue();
            
            x.setItemMappingId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setItemNum(rs.getString(4));
            x.setItemUom(rs.getString(5));
            x.setItemPack(rs.getString(6));
            x.setShortDesc(rs.getString(7));
            x.setLongDesc(rs.getString(8));
            x.setItemMappingCd(rs.getString(9));
            x.setEffDate(rs.getDate(10));
            x.setExpDate(rs.getDate(11));
            x.setStatusCd(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setUomConvMultiplier(rs.getBigDecimal(17));
            x.setStandardProductList(rs.getString(18));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ItemMappingData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_MAPPING_ID FROM CLW_ITEM_MAPPING");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_MAPPING");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_MAPPING");
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
     * Inserts a ItemMappingData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemMappingData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ItemMappingData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemMappingData insert(Connection pCon, ItemMappingData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ITEM_MAPPING_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ITEM_MAPPING_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setItemMappingId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ITEM_MAPPING (ITEM_MAPPING_ID,ITEM_ID,BUS_ENTITY_ID,ITEM_NUM,ITEM_UOM,ITEM_PACK,SHORT_DESC,LONG_DESC,ITEM_MAPPING_CD,EFF_DATE,EXP_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UOM_CONV_MULTIPLIER,STANDARD_PRODUCT_LIST) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getItemMappingId());
        pstmt.setInt(2,pData.getItemId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getBusEntityId());
        }

        pstmt.setString(4,pData.getItemNum());
        pstmt.setString(5,pData.getItemUom());
        pstmt.setString(6,pData.getItemPack());
        pstmt.setString(7,pData.getShortDesc());
        pstmt.setString(8,pData.getLongDesc());
        pstmt.setString(9,pData.getItemMappingCd());
        pstmt.setDate(10,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(11,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(12,pData.getStatusCd());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14,pData.getAddBy());
        pstmt.setTimestamp(15,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16,pData.getModBy());
        pstmt.setBigDecimal(17,pData.getUomConvMultiplier());
        pstmt.setString(18,pData.getStandardProductList());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_MAPPING_ID="+pData.getItemMappingId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ITEM_NUM="+pData.getItemNum());
            log.debug("SQL:   ITEM_UOM="+pData.getItemUom());
            log.debug("SQL:   ITEM_PACK="+pData.getItemPack());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   ITEM_MAPPING_CD="+pData.getItemMappingCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   UOM_CONV_MULTIPLIER="+pData.getUomConvMultiplier());
            log.debug("SQL:   STANDARD_PRODUCT_LIST="+pData.getStandardProductList());
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
        pData.setItemMappingId(0);
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
     * Updates a ItemMappingData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemMappingData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemMappingData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ITEM_MAPPING SET ITEM_ID = ?,BUS_ENTITY_ID = ?,ITEM_NUM = ?,ITEM_UOM = ?,ITEM_PACK = ?,SHORT_DESC = ?,LONG_DESC = ?,ITEM_MAPPING_CD = ?,EFF_DATE = ?,EXP_DATE = ?,STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,UOM_CONV_MULTIPLIER = ?,STANDARD_PRODUCT_LIST = ? WHERE ITEM_MAPPING_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getItemId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getItemNum());
        pstmt.setString(i++,pData.getItemUom());
        pstmt.setString(i++,pData.getItemPack());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setString(i++,pData.getItemMappingCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setBigDecimal(i++,pData.getUomConvMultiplier());
        pstmt.setString(i++,pData.getStandardProductList());
        pstmt.setInt(i++,pData.getItemMappingId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ITEM_NUM="+pData.getItemNum());
            log.debug("SQL:   ITEM_UOM="+pData.getItemUom());
            log.debug("SQL:   ITEM_PACK="+pData.getItemPack());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   ITEM_MAPPING_CD="+pData.getItemMappingCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   UOM_CONV_MULTIPLIER="+pData.getUomConvMultiplier());
            log.debug("SQL:   STANDARD_PRODUCT_LIST="+pData.getStandardProductList());
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
     * Deletes a ItemMappingData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemMappingId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemMappingId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ITEM_MAPPING WHERE ITEM_MAPPING_ID = " + pItemMappingId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ItemMappingData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ITEM_MAPPING");
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
     * Inserts a ItemMappingData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemMappingData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ItemMappingData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ITEM_MAPPING (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ITEM_MAPPING_ID,ITEM_ID,BUS_ENTITY_ID,ITEM_NUM,ITEM_UOM,ITEM_PACK,SHORT_DESC,LONG_DESC,ITEM_MAPPING_CD,EFF_DATE,EXP_DATE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UOM_CONV_MULTIPLIER,STANDARD_PRODUCT_LIST) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getItemMappingId());
        pstmt.setInt(2+4,pData.getItemId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getBusEntityId());
        }

        pstmt.setString(4+4,pData.getItemNum());
        pstmt.setString(5+4,pData.getItemUom());
        pstmt.setString(6+4,pData.getItemPack());
        pstmt.setString(7+4,pData.getShortDesc());
        pstmt.setString(8+4,pData.getLongDesc());
        pstmt.setString(9+4,pData.getItemMappingCd());
        pstmt.setDate(10+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(11+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(12+4,pData.getStatusCd());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14+4,pData.getAddBy());
        pstmt.setTimestamp(15+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16+4,pData.getModBy());
        pstmt.setBigDecimal(17+4,pData.getUomConvMultiplier());
        pstmt.setString(18+4,pData.getStandardProductList());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ItemMappingData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemMappingData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ItemMappingData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemMappingData insert(Connection pCon, ItemMappingData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ItemMappingData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemMappingData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemMappingData pData, boolean pLogFl)
        throws SQLException {
        ItemMappingData oldData = null;
        if(pLogFl) {
          int id = pData.getItemMappingId();
          try {
          oldData = ItemMappingDataAccess.select(pCon,id);
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
     * Deletes a ItemMappingData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemMappingId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemMappingId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ITEM_MAPPING SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM_MAPPING d WHERE ITEM_MAPPING_ID = " + pItemMappingId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pItemMappingId);
        return n;
     }

    /**
     * Deletes ItemMappingData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ITEM_MAPPING SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM_MAPPING d ");
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

