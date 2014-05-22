
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ManifestItemDataAccess
 * Description:  This class is used to build access methods to the CLW_MANIFEST_ITEM table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ManifestItemData;
import com.cleanwise.service.api.value.ManifestItemDataVector;
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
 * <code>ManifestItemDataAccess</code>
 */
public class ManifestItemDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ManifestItemDataAccess.class.getName());

    /** <code>CLW_MANIFEST_ITEM</code> table name */
	/* Primary key: MANIFEST_ITEM_ID */
	
    public static final String CLW_MANIFEST_ITEM = "CLW_MANIFEST_ITEM";
    
    /** <code>MANIFEST_ITEM_ID</code> MANIFEST_ITEM_ID column of table CLW_MANIFEST_ITEM */
    public static final String MANIFEST_ITEM_ID = "MANIFEST_ITEM_ID";
    /** <code>PURCHASE_ORDER_ID</code> PURCHASE_ORDER_ID column of table CLW_MANIFEST_ITEM */
    public static final String PURCHASE_ORDER_ID = "PURCHASE_ORDER_ID";
    /** <code>PACKAGE_ID</code> PACKAGE_ID column of table CLW_MANIFEST_ITEM */
    public static final String PACKAGE_ID = "PACKAGE_ID";
    /** <code>PACKAGE_CONFIRM_ID</code> PACKAGE_CONFIRM_ID column of table CLW_MANIFEST_ITEM */
    public static final String PACKAGE_CONFIRM_ID = "PACKAGE_CONFIRM_ID";
    /** <code>DISTRIBUTION_CENTER_ID</code> DISTRIBUTION_CENTER_ID column of table CLW_MANIFEST_ITEM */
    public static final String DISTRIBUTION_CENTER_ID = "DISTRIBUTION_CENTER_ID";
    /** <code>MANIFEST_ITEM_STATUS_CD</code> MANIFEST_ITEM_STATUS_CD column of table CLW_MANIFEST_ITEM */
    public static final String MANIFEST_ITEM_STATUS_CD = "MANIFEST_ITEM_STATUS_CD";
    /** <code>CUBIC_SIZE</code> CUBIC_SIZE column of table CLW_MANIFEST_ITEM */
    public static final String CUBIC_SIZE = "CUBIC_SIZE";
    /** <code>WEIGHT</code> WEIGHT column of table CLW_MANIFEST_ITEM */
    public static final String WEIGHT = "WEIGHT";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_MANIFEST_ITEM */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_MANIFEST_ITEM */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_MANIFEST_ITEM */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_MANIFEST_ITEM */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>RECIEVED_POSTAL_CD</code> RECIEVED_POSTAL_CD column of table CLW_MANIFEST_ITEM */
    public static final String RECIEVED_POSTAL_CD = "RECIEVED_POSTAL_CD";
    /** <code>RECIEVED_ZONE_CD</code> RECIEVED_ZONE_CD column of table CLW_MANIFEST_ITEM */
    public static final String RECIEVED_ZONE_CD = "RECIEVED_ZONE_CD";
    /** <code>RECIEVED_ACTUAL_WEIGHT</code> RECIEVED_ACTUAL_WEIGHT column of table CLW_MANIFEST_ITEM */
    public static final String RECIEVED_ACTUAL_WEIGHT = "RECIEVED_ACTUAL_WEIGHT";
    /** <code>RECIEVED_PACKAGE_CATEGORY_CD</code> RECIEVED_PACKAGE_CATEGORY_CD column of table CLW_MANIFEST_ITEM */
    public static final String RECIEVED_PACKAGE_CATEGORY_CD = "RECIEVED_PACKAGE_CATEGORY_CD";
    /** <code>RECIEVED_PACKAGE_COST</code> RECIEVED_PACKAGE_COST column of table CLW_MANIFEST_ITEM */
    public static final String RECIEVED_PACKAGE_COST = "RECIEVED_PACKAGE_COST";
    /** <code>MANIFEST_ID</code> MANIFEST_ID column of table CLW_MANIFEST_ITEM */
    public static final String MANIFEST_ID = "MANIFEST_ID";
    /** <code>MANIFEST_LABEL_TYPE_CD</code> MANIFEST_LABEL_TYPE_CD column of table CLW_MANIFEST_ITEM */
    public static final String MANIFEST_LABEL_TYPE_CD = "MANIFEST_LABEL_TYPE_CD";

    /**
     * Constructor.
     */
    public ManifestItemDataAccess()
    {
    }

    /**
     * Gets a ManifestItemData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pManifestItemId The key requested.
     * @return new ManifestItemData()
     * @throws            SQLException
     */
    public static ManifestItemData select(Connection pCon, int pManifestItemId)
        throws SQLException, DataNotFoundException {
        ManifestItemData x=null;
        String sql="SELECT MANIFEST_ITEM_ID,PURCHASE_ORDER_ID,PACKAGE_ID,PACKAGE_CONFIRM_ID,DISTRIBUTION_CENTER_ID,MANIFEST_ITEM_STATUS_CD,CUBIC_SIZE,WEIGHT,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,RECIEVED_POSTAL_CD,RECIEVED_ZONE_CD,RECIEVED_ACTUAL_WEIGHT,RECIEVED_PACKAGE_CATEGORY_CD,RECIEVED_PACKAGE_COST,MANIFEST_ID,MANIFEST_LABEL_TYPE_CD FROM CLW_MANIFEST_ITEM WHERE MANIFEST_ITEM_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pManifestItemId=" + pManifestItemId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pManifestItemId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ManifestItemData.createValue();
            
            x.setManifestItemId(rs.getInt(1));
            x.setPurchaseOrderId(rs.getInt(2));
            x.setPackageId(rs.getString(3));
            x.setPackageConfirmId(rs.getString(4));
            x.setDistributionCenterId(rs.getString(5));
            x.setManifestItemStatusCd(rs.getString(6));
            x.setCubicSize(rs.getBigDecimal(7));
            x.setWeight(rs.getBigDecimal(8));
            x.setAddBy(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setRecievedPostalCd(rs.getString(13));
            x.setRecievedZoneCd(rs.getString(14));
            x.setRecievedActualWeight(rs.getString(15));
            x.setRecievedPackageCategoryCd(rs.getString(16));
            x.setRecievedPackageCost(rs.getString(17));
            x.setManifestId(rs.getString(18));
            x.setManifestLabelTypeCd(rs.getString(19));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("MANIFEST_ITEM_ID :" + pManifestItemId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ManifestItemDataVector object that consists
     * of ManifestItemData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ManifestItemDataVector()
     * @throws            SQLException
     */
    public static ManifestItemDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ManifestItemData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_MANIFEST_ITEM.MANIFEST_ITEM_ID,CLW_MANIFEST_ITEM.PURCHASE_ORDER_ID,CLW_MANIFEST_ITEM.PACKAGE_ID,CLW_MANIFEST_ITEM.PACKAGE_CONFIRM_ID,CLW_MANIFEST_ITEM.DISTRIBUTION_CENTER_ID,CLW_MANIFEST_ITEM.MANIFEST_ITEM_STATUS_CD,CLW_MANIFEST_ITEM.CUBIC_SIZE,CLW_MANIFEST_ITEM.WEIGHT,CLW_MANIFEST_ITEM.ADD_BY,CLW_MANIFEST_ITEM.ADD_DATE,CLW_MANIFEST_ITEM.MOD_BY,CLW_MANIFEST_ITEM.MOD_DATE,CLW_MANIFEST_ITEM.RECIEVED_POSTAL_CD,CLW_MANIFEST_ITEM.RECIEVED_ZONE_CD,CLW_MANIFEST_ITEM.RECIEVED_ACTUAL_WEIGHT,CLW_MANIFEST_ITEM.RECIEVED_PACKAGE_CATEGORY_CD,CLW_MANIFEST_ITEM.RECIEVED_PACKAGE_COST,CLW_MANIFEST_ITEM.MANIFEST_ID,CLW_MANIFEST_ITEM.MANIFEST_LABEL_TYPE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ManifestItemData Object.
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
    *@returns a populated ManifestItemData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ManifestItemData x = ManifestItemData.createValue();
         
         x.setManifestItemId(rs.getInt(1+offset));
         x.setPurchaseOrderId(rs.getInt(2+offset));
         x.setPackageId(rs.getString(3+offset));
         x.setPackageConfirmId(rs.getString(4+offset));
         x.setDistributionCenterId(rs.getString(5+offset));
         x.setManifestItemStatusCd(rs.getString(6+offset));
         x.setCubicSize(rs.getBigDecimal(7+offset));
         x.setWeight(rs.getBigDecimal(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setAddDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         x.setModDate(rs.getTimestamp(12+offset));
         x.setRecievedPostalCd(rs.getString(13+offset));
         x.setRecievedZoneCd(rs.getString(14+offset));
         x.setRecievedActualWeight(rs.getString(15+offset));
         x.setRecievedPackageCategoryCd(rs.getString(16+offset));
         x.setRecievedPackageCost(rs.getString(17+offset));
         x.setManifestId(rs.getString(18+offset));
         x.setManifestLabelTypeCd(rs.getString(19+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ManifestItemData Object represents.
    */
    public int getColumnCount(){
        return 19;
    }

    /**
     * Gets a ManifestItemDataVector object that consists
     * of ManifestItemData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ManifestItemDataVector()
     * @throws            SQLException
     */
    public static ManifestItemDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT MANIFEST_ITEM_ID,PURCHASE_ORDER_ID,PACKAGE_ID,PACKAGE_CONFIRM_ID,DISTRIBUTION_CENTER_ID,MANIFEST_ITEM_STATUS_CD,CUBIC_SIZE,WEIGHT,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,RECIEVED_POSTAL_CD,RECIEVED_ZONE_CD,RECIEVED_ACTUAL_WEIGHT,RECIEVED_PACKAGE_CATEGORY_CD,RECIEVED_PACKAGE_COST,MANIFEST_ID,MANIFEST_LABEL_TYPE_CD FROM CLW_MANIFEST_ITEM");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_MANIFEST_ITEM.MANIFEST_ITEM_ID,CLW_MANIFEST_ITEM.PURCHASE_ORDER_ID,CLW_MANIFEST_ITEM.PACKAGE_ID,CLW_MANIFEST_ITEM.PACKAGE_CONFIRM_ID,CLW_MANIFEST_ITEM.DISTRIBUTION_CENTER_ID,CLW_MANIFEST_ITEM.MANIFEST_ITEM_STATUS_CD,CLW_MANIFEST_ITEM.CUBIC_SIZE,CLW_MANIFEST_ITEM.WEIGHT,CLW_MANIFEST_ITEM.ADD_BY,CLW_MANIFEST_ITEM.ADD_DATE,CLW_MANIFEST_ITEM.MOD_BY,CLW_MANIFEST_ITEM.MOD_DATE,CLW_MANIFEST_ITEM.RECIEVED_POSTAL_CD,CLW_MANIFEST_ITEM.RECIEVED_ZONE_CD,CLW_MANIFEST_ITEM.RECIEVED_ACTUAL_WEIGHT,CLW_MANIFEST_ITEM.RECIEVED_PACKAGE_CATEGORY_CD,CLW_MANIFEST_ITEM.RECIEVED_PACKAGE_COST,CLW_MANIFEST_ITEM.MANIFEST_ID,CLW_MANIFEST_ITEM.MANIFEST_LABEL_TYPE_CD FROM CLW_MANIFEST_ITEM");
                where = pCriteria.getSqlClause("CLW_MANIFEST_ITEM");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_MANIFEST_ITEM.equals(otherTable)){
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
        ManifestItemDataVector v = new ManifestItemDataVector();
        while (rs.next()) {
            ManifestItemData x = ManifestItemData.createValue();
            
            x.setManifestItemId(rs.getInt(1));
            x.setPurchaseOrderId(rs.getInt(2));
            x.setPackageId(rs.getString(3));
            x.setPackageConfirmId(rs.getString(4));
            x.setDistributionCenterId(rs.getString(5));
            x.setManifestItemStatusCd(rs.getString(6));
            x.setCubicSize(rs.getBigDecimal(7));
            x.setWeight(rs.getBigDecimal(8));
            x.setAddBy(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setRecievedPostalCd(rs.getString(13));
            x.setRecievedZoneCd(rs.getString(14));
            x.setRecievedActualWeight(rs.getString(15));
            x.setRecievedPackageCategoryCd(rs.getString(16));
            x.setRecievedPackageCost(rs.getString(17));
            x.setManifestId(rs.getString(18));
            x.setManifestLabelTypeCd(rs.getString(19));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ManifestItemDataVector object that consists
     * of ManifestItemData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ManifestItemData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ManifestItemDataVector()
     * @throws            SQLException
     */
    public static ManifestItemDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ManifestItemDataVector v = new ManifestItemDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT MANIFEST_ITEM_ID,PURCHASE_ORDER_ID,PACKAGE_ID,PACKAGE_CONFIRM_ID,DISTRIBUTION_CENTER_ID,MANIFEST_ITEM_STATUS_CD,CUBIC_SIZE,WEIGHT,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,RECIEVED_POSTAL_CD,RECIEVED_ZONE_CD,RECIEVED_ACTUAL_WEIGHT,RECIEVED_PACKAGE_CATEGORY_CD,RECIEVED_PACKAGE_COST,MANIFEST_ID,MANIFEST_LABEL_TYPE_CD FROM CLW_MANIFEST_ITEM WHERE MANIFEST_ITEM_ID IN (");

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
            ManifestItemData x=null;
            while (rs.next()) {
                // build the object
                x=ManifestItemData.createValue();
                
                x.setManifestItemId(rs.getInt(1));
                x.setPurchaseOrderId(rs.getInt(2));
                x.setPackageId(rs.getString(3));
                x.setPackageConfirmId(rs.getString(4));
                x.setDistributionCenterId(rs.getString(5));
                x.setManifestItemStatusCd(rs.getString(6));
                x.setCubicSize(rs.getBigDecimal(7));
                x.setWeight(rs.getBigDecimal(8));
                x.setAddBy(rs.getString(9));
                x.setAddDate(rs.getTimestamp(10));
                x.setModBy(rs.getString(11));
                x.setModDate(rs.getTimestamp(12));
                x.setRecievedPostalCd(rs.getString(13));
                x.setRecievedZoneCd(rs.getString(14));
                x.setRecievedActualWeight(rs.getString(15));
                x.setRecievedPackageCategoryCd(rs.getString(16));
                x.setRecievedPackageCost(rs.getString(17));
                x.setManifestId(rs.getString(18));
                x.setManifestLabelTypeCd(rs.getString(19));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ManifestItemDataVector object of all
     * ManifestItemData objects in the database.
     * @param pCon An open database connection.
     * @return new ManifestItemDataVector()
     * @throws            SQLException
     */
    public static ManifestItemDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT MANIFEST_ITEM_ID,PURCHASE_ORDER_ID,PACKAGE_ID,PACKAGE_CONFIRM_ID,DISTRIBUTION_CENTER_ID,MANIFEST_ITEM_STATUS_CD,CUBIC_SIZE,WEIGHT,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,RECIEVED_POSTAL_CD,RECIEVED_ZONE_CD,RECIEVED_ACTUAL_WEIGHT,RECIEVED_PACKAGE_CATEGORY_CD,RECIEVED_PACKAGE_COST,MANIFEST_ID,MANIFEST_LABEL_TYPE_CD FROM CLW_MANIFEST_ITEM";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ManifestItemDataVector v = new ManifestItemDataVector();
        ManifestItemData x = null;
        while (rs.next()) {
            // build the object
            x = ManifestItemData.createValue();
            
            x.setManifestItemId(rs.getInt(1));
            x.setPurchaseOrderId(rs.getInt(2));
            x.setPackageId(rs.getString(3));
            x.setPackageConfirmId(rs.getString(4));
            x.setDistributionCenterId(rs.getString(5));
            x.setManifestItemStatusCd(rs.getString(6));
            x.setCubicSize(rs.getBigDecimal(7));
            x.setWeight(rs.getBigDecimal(8));
            x.setAddBy(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setRecievedPostalCd(rs.getString(13));
            x.setRecievedZoneCd(rs.getString(14));
            x.setRecievedActualWeight(rs.getString(15));
            x.setRecievedPackageCategoryCd(rs.getString(16));
            x.setRecievedPackageCost(rs.getString(17));
            x.setManifestId(rs.getString(18));
            x.setManifestLabelTypeCd(rs.getString(19));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ManifestItemData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT MANIFEST_ITEM_ID FROM CLW_MANIFEST_ITEM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_MANIFEST_ITEM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_MANIFEST_ITEM");
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
     * Inserts a ManifestItemData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ManifestItemData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ManifestItemData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ManifestItemData insert(Connection pCon, ManifestItemData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_MANIFEST_ITEM_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_MANIFEST_ITEM_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setManifestItemId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_MANIFEST_ITEM (MANIFEST_ITEM_ID,PURCHASE_ORDER_ID,PACKAGE_ID,PACKAGE_CONFIRM_ID,DISTRIBUTION_CENTER_ID,MANIFEST_ITEM_STATUS_CD,CUBIC_SIZE,WEIGHT,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,RECIEVED_POSTAL_CD,RECIEVED_ZONE_CD,RECIEVED_ACTUAL_WEIGHT,RECIEVED_PACKAGE_CATEGORY_CD,RECIEVED_PACKAGE_COST,MANIFEST_ID,MANIFEST_LABEL_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getManifestItemId());
        if (pData.getPurchaseOrderId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getPurchaseOrderId());
        }

        pstmt.setString(3,pData.getPackageId());
        pstmt.setString(4,pData.getPackageConfirmId());
        pstmt.setString(5,pData.getDistributionCenterId());
        pstmt.setString(6,pData.getManifestItemStatusCd());
        pstmt.setBigDecimal(7,pData.getCubicSize());
        pstmt.setBigDecimal(8,pData.getWeight());
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11,pData.getModBy());
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13,pData.getRecievedPostalCd());
        pstmt.setString(14,pData.getRecievedZoneCd());
        pstmt.setString(15,pData.getRecievedActualWeight());
        pstmt.setString(16,pData.getRecievedPackageCategoryCd());
        pstmt.setString(17,pData.getRecievedPackageCost());
        pstmt.setString(18,pData.getManifestId());
        pstmt.setString(19,pData.getManifestLabelTypeCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   MANIFEST_ITEM_ID="+pData.getManifestItemId());
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL:   PACKAGE_ID="+pData.getPackageId());
            log.debug("SQL:   PACKAGE_CONFIRM_ID="+pData.getPackageConfirmId());
            log.debug("SQL:   DISTRIBUTION_CENTER_ID="+pData.getDistributionCenterId());
            log.debug("SQL:   MANIFEST_ITEM_STATUS_CD="+pData.getManifestItemStatusCd());
            log.debug("SQL:   CUBIC_SIZE="+pData.getCubicSize());
            log.debug("SQL:   WEIGHT="+pData.getWeight());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   RECIEVED_POSTAL_CD="+pData.getRecievedPostalCd());
            log.debug("SQL:   RECIEVED_ZONE_CD="+pData.getRecievedZoneCd());
            log.debug("SQL:   RECIEVED_ACTUAL_WEIGHT="+pData.getRecievedActualWeight());
            log.debug("SQL:   RECIEVED_PACKAGE_CATEGORY_CD="+pData.getRecievedPackageCategoryCd());
            log.debug("SQL:   RECIEVED_PACKAGE_COST="+pData.getRecievedPackageCost());
            log.debug("SQL:   MANIFEST_ID="+pData.getManifestId());
            log.debug("SQL:   MANIFEST_LABEL_TYPE_CD="+pData.getManifestLabelTypeCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setManifestItemId(0);
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
     * Updates a ManifestItemData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ManifestItemData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ManifestItemData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_MANIFEST_ITEM SET PURCHASE_ORDER_ID = ?,PACKAGE_ID = ?,PACKAGE_CONFIRM_ID = ?,DISTRIBUTION_CENTER_ID = ?,MANIFEST_ITEM_STATUS_CD = ?,CUBIC_SIZE = ?,WEIGHT = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ?,RECIEVED_POSTAL_CD = ?,RECIEVED_ZONE_CD = ?,RECIEVED_ACTUAL_WEIGHT = ?,RECIEVED_PACKAGE_CATEGORY_CD = ?,RECIEVED_PACKAGE_COST = ?,MANIFEST_ID = ?,MANIFEST_LABEL_TYPE_CD = ? WHERE MANIFEST_ITEM_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getPurchaseOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getPurchaseOrderId());
        }

        pstmt.setString(i++,pData.getPackageId());
        pstmt.setString(i++,pData.getPackageConfirmId());
        pstmt.setString(i++,pData.getDistributionCenterId());
        pstmt.setString(i++,pData.getManifestItemStatusCd());
        pstmt.setBigDecimal(i++,pData.getCubicSize());
        pstmt.setBigDecimal(i++,pData.getWeight());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getRecievedPostalCd());
        pstmt.setString(i++,pData.getRecievedZoneCd());
        pstmt.setString(i++,pData.getRecievedActualWeight());
        pstmt.setString(i++,pData.getRecievedPackageCategoryCd());
        pstmt.setString(i++,pData.getRecievedPackageCost());
        pstmt.setString(i++,pData.getManifestId());
        pstmt.setString(i++,pData.getManifestLabelTypeCd());
        pstmt.setInt(i++,pData.getManifestItemId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL:   PACKAGE_ID="+pData.getPackageId());
            log.debug("SQL:   PACKAGE_CONFIRM_ID="+pData.getPackageConfirmId());
            log.debug("SQL:   DISTRIBUTION_CENTER_ID="+pData.getDistributionCenterId());
            log.debug("SQL:   MANIFEST_ITEM_STATUS_CD="+pData.getManifestItemStatusCd());
            log.debug("SQL:   CUBIC_SIZE="+pData.getCubicSize());
            log.debug("SQL:   WEIGHT="+pData.getWeight());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   RECIEVED_POSTAL_CD="+pData.getRecievedPostalCd());
            log.debug("SQL:   RECIEVED_ZONE_CD="+pData.getRecievedZoneCd());
            log.debug("SQL:   RECIEVED_ACTUAL_WEIGHT="+pData.getRecievedActualWeight());
            log.debug("SQL:   RECIEVED_PACKAGE_CATEGORY_CD="+pData.getRecievedPackageCategoryCd());
            log.debug("SQL:   RECIEVED_PACKAGE_COST="+pData.getRecievedPackageCost());
            log.debug("SQL:   MANIFEST_ID="+pData.getManifestId());
            log.debug("SQL:   MANIFEST_LABEL_TYPE_CD="+pData.getManifestLabelTypeCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ManifestItemData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pManifestItemId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pManifestItemId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_MANIFEST_ITEM WHERE MANIFEST_ITEM_ID = " + pManifestItemId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ManifestItemData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_MANIFEST_ITEM");
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
     * Inserts a ManifestItemData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ManifestItemData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ManifestItemData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_MANIFEST_ITEM (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "MANIFEST_ITEM_ID,PURCHASE_ORDER_ID,PACKAGE_ID,PACKAGE_CONFIRM_ID,DISTRIBUTION_CENTER_ID,MANIFEST_ITEM_STATUS_CD,CUBIC_SIZE,WEIGHT,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,RECIEVED_POSTAL_CD,RECIEVED_ZONE_CD,RECIEVED_ACTUAL_WEIGHT,RECIEVED_PACKAGE_CATEGORY_CD,RECIEVED_PACKAGE_COST,MANIFEST_ID,MANIFEST_LABEL_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getManifestItemId());
        if (pData.getPurchaseOrderId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getPurchaseOrderId());
        }

        pstmt.setString(3+4,pData.getPackageId());
        pstmt.setString(4+4,pData.getPackageConfirmId());
        pstmt.setString(5+4,pData.getDistributionCenterId());
        pstmt.setString(6+4,pData.getManifestItemStatusCd());
        pstmt.setBigDecimal(7+4,pData.getCubicSize());
        pstmt.setBigDecimal(8+4,pData.getWeight());
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11+4,pData.getModBy());
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13+4,pData.getRecievedPostalCd());
        pstmt.setString(14+4,pData.getRecievedZoneCd());
        pstmt.setString(15+4,pData.getRecievedActualWeight());
        pstmt.setString(16+4,pData.getRecievedPackageCategoryCd());
        pstmt.setString(17+4,pData.getRecievedPackageCost());
        pstmt.setString(18+4,pData.getManifestId());
        pstmt.setString(19+4,pData.getManifestLabelTypeCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ManifestItemData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ManifestItemData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ManifestItemData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ManifestItemData insert(Connection pCon, ManifestItemData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ManifestItemData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ManifestItemData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ManifestItemData pData, boolean pLogFl)
        throws SQLException {
        ManifestItemData oldData = null;
        if(pLogFl) {
          int id = pData.getManifestItemId();
          try {
          oldData = ManifestItemDataAccess.select(pCon,id);
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
     * Deletes a ManifestItemData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pManifestItemId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pManifestItemId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_MANIFEST_ITEM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_MANIFEST_ITEM d WHERE MANIFEST_ITEM_ID = " + pManifestItemId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pManifestItemId);
        return n;
     }

    /**
     * Deletes ManifestItemData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_MANIFEST_ITEM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_MANIFEST_ITEM d ");
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

