
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WorkOrderItemDataAccess
 * Description:  This class is used to build access methods to the CLW_WORK_ORDER_ITEM table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WorkOrderItemData;
import com.cleanwise.service.api.value.WorkOrderItemDataVector;
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
 * <code>WorkOrderItemDataAccess</code>
 */
public class WorkOrderItemDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WorkOrderItemDataAccess.class.getName());

    /** <code>CLW_WORK_ORDER_ITEM</code> table name */
	/* Primary key: WORK_ORDER_ITEM_ID */
	
    public static final String CLW_WORK_ORDER_ITEM = "CLW_WORK_ORDER_ITEM";
    
    /** <code>WORK_ORDER_ITEM_ID</code> WORK_ORDER_ITEM_ID column of table CLW_WORK_ORDER_ITEM */
    public static final String WORK_ORDER_ITEM_ID = "WORK_ORDER_ITEM_ID";
    /** <code>WORK_ORDER_ID</code> WORK_ORDER_ID column of table CLW_WORK_ORDER_ITEM */
    public static final String WORK_ORDER_ID = "WORK_ORDER_ID";
    /** <code>WARRANTY_ID</code> WARRANTY_ID column of table CLW_WORK_ORDER_ITEM */
    public static final String WARRANTY_ID = "WARRANTY_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_WORK_ORDER_ITEM */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_WORK_ORDER_ITEM */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_WORK_ORDER_ITEM */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_WORK_ORDER_ITEM */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>SEQUENCE</code> SEQUENCE column of table CLW_WORK_ORDER_ITEM */
    public static final String SEQUENCE = "SEQUENCE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WORK_ORDER_ITEM */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WORK_ORDER_ITEM */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WORK_ORDER_ITEM */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WORK_ORDER_ITEM */
    public static final String MOD_BY = "MOD_BY";
    /** <code>ESTIMATE_LABOR</code> ESTIMATE_LABOR column of table CLW_WORK_ORDER_ITEM */
    public static final String ESTIMATE_LABOR = "ESTIMATE_LABOR";
    /** <code>ESTIMATE_PART</code> ESTIMATE_PART column of table CLW_WORK_ORDER_ITEM */
    public static final String ESTIMATE_PART = "ESTIMATE_PART";
    /** <code>ACTUAL_LABOR</code> ACTUAL_LABOR column of table CLW_WORK_ORDER_ITEM */
    public static final String ACTUAL_LABOR = "ACTUAL_LABOR";
    /** <code>ACTUAL_PART</code> ACTUAL_PART column of table CLW_WORK_ORDER_ITEM */
    public static final String ACTUAL_PART = "ACTUAL_PART";

    /**
     * Constructor.
     */
    public WorkOrderItemDataAccess()
    {
    }

    /**
     * Gets a WorkOrderItemData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWorkOrderItemId The key requested.
     * @return new WorkOrderItemData()
     * @throws            SQLException
     */
    public static WorkOrderItemData select(Connection pCon, int pWorkOrderItemId)
        throws SQLException, DataNotFoundException {
        WorkOrderItemData x=null;
        String sql="SELECT WORK_ORDER_ITEM_ID,WORK_ORDER_ID,WARRANTY_ID,BUS_ENTITY_ID,STATUS_CD,SHORT_DESC,LONG_DESC,SEQUENCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ESTIMATE_LABOR,ESTIMATE_PART,ACTUAL_LABOR,ACTUAL_PART FROM CLW_WORK_ORDER_ITEM WHERE WORK_ORDER_ITEM_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWorkOrderItemId=" + pWorkOrderItemId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWorkOrderItemId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WorkOrderItemData.createValue();
            
            x.setWorkOrderItemId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setWarrantyId(rs.getInt(3));
            x.setBusEntityId(rs.getInt(4));
            x.setStatusCd(rs.getString(5));
            x.setShortDesc(rs.getString(6));
            x.setLongDesc(rs.getString(7));
            x.setSequence(rs.getInt(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setEstimateLabor(rs.getBigDecimal(13));
            x.setEstimatePart(rs.getBigDecimal(14));
            x.setActualLabor(rs.getBigDecimal(15));
            x.setActualPart(rs.getBigDecimal(16));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WORK_ORDER_ITEM_ID :" + pWorkOrderItemId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WorkOrderItemDataVector object that consists
     * of WorkOrderItemData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WorkOrderItemDataVector()
     * @throws            SQLException
     */
    public static WorkOrderItemDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WorkOrderItemData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WORK_ORDER_ITEM.WORK_ORDER_ITEM_ID,CLW_WORK_ORDER_ITEM.WORK_ORDER_ID,CLW_WORK_ORDER_ITEM.WARRANTY_ID,CLW_WORK_ORDER_ITEM.BUS_ENTITY_ID,CLW_WORK_ORDER_ITEM.STATUS_CD,CLW_WORK_ORDER_ITEM.SHORT_DESC,CLW_WORK_ORDER_ITEM.LONG_DESC,CLW_WORK_ORDER_ITEM.SEQUENCE,CLW_WORK_ORDER_ITEM.ADD_DATE,CLW_WORK_ORDER_ITEM.ADD_BY,CLW_WORK_ORDER_ITEM.MOD_DATE,CLW_WORK_ORDER_ITEM.MOD_BY,CLW_WORK_ORDER_ITEM.ESTIMATE_LABOR,CLW_WORK_ORDER_ITEM.ESTIMATE_PART,CLW_WORK_ORDER_ITEM.ACTUAL_LABOR,CLW_WORK_ORDER_ITEM.ACTUAL_PART";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WorkOrderItemData Object.
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
    *@returns a populated WorkOrderItemData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WorkOrderItemData x = WorkOrderItemData.createValue();
         
         x.setWorkOrderItemId(rs.getInt(1+offset));
         x.setWorkOrderId(rs.getInt(2+offset));
         x.setWarrantyId(rs.getInt(3+offset));
         x.setBusEntityId(rs.getInt(4+offset));
         x.setStatusCd(rs.getString(5+offset));
         x.setShortDesc(rs.getString(6+offset));
         x.setLongDesc(rs.getString(7+offset));
         x.setSequence(rs.getInt(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         x.setEstimateLabor(rs.getBigDecimal(13+offset));
         x.setEstimatePart(rs.getBigDecimal(14+offset));
         x.setActualLabor(rs.getBigDecimal(15+offset));
         x.setActualPart(rs.getBigDecimal(16+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WorkOrderItemData Object represents.
    */
    public int getColumnCount(){
        return 16;
    }

    /**
     * Gets a WorkOrderItemDataVector object that consists
     * of WorkOrderItemData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WorkOrderItemDataVector()
     * @throws            SQLException
     */
    public static WorkOrderItemDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WORK_ORDER_ITEM_ID,WORK_ORDER_ID,WARRANTY_ID,BUS_ENTITY_ID,STATUS_CD,SHORT_DESC,LONG_DESC,SEQUENCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ESTIMATE_LABOR,ESTIMATE_PART,ACTUAL_LABOR,ACTUAL_PART FROM CLW_WORK_ORDER_ITEM");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WORK_ORDER_ITEM.WORK_ORDER_ITEM_ID,CLW_WORK_ORDER_ITEM.WORK_ORDER_ID,CLW_WORK_ORDER_ITEM.WARRANTY_ID,CLW_WORK_ORDER_ITEM.BUS_ENTITY_ID,CLW_WORK_ORDER_ITEM.STATUS_CD,CLW_WORK_ORDER_ITEM.SHORT_DESC,CLW_WORK_ORDER_ITEM.LONG_DESC,CLW_WORK_ORDER_ITEM.SEQUENCE,CLW_WORK_ORDER_ITEM.ADD_DATE,CLW_WORK_ORDER_ITEM.ADD_BY,CLW_WORK_ORDER_ITEM.MOD_DATE,CLW_WORK_ORDER_ITEM.MOD_BY,CLW_WORK_ORDER_ITEM.ESTIMATE_LABOR,CLW_WORK_ORDER_ITEM.ESTIMATE_PART,CLW_WORK_ORDER_ITEM.ACTUAL_LABOR,CLW_WORK_ORDER_ITEM.ACTUAL_PART FROM CLW_WORK_ORDER_ITEM");
                where = pCriteria.getSqlClause("CLW_WORK_ORDER_ITEM");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORK_ORDER_ITEM.equals(otherTable)){
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
        WorkOrderItemDataVector v = new WorkOrderItemDataVector();
        while (rs.next()) {
            WorkOrderItemData x = WorkOrderItemData.createValue();
            
            x.setWorkOrderItemId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setWarrantyId(rs.getInt(3));
            x.setBusEntityId(rs.getInt(4));
            x.setStatusCd(rs.getString(5));
            x.setShortDesc(rs.getString(6));
            x.setLongDesc(rs.getString(7));
            x.setSequence(rs.getInt(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setEstimateLabor(rs.getBigDecimal(13));
            x.setEstimatePart(rs.getBigDecimal(14));
            x.setActualLabor(rs.getBigDecimal(15));
            x.setActualPart(rs.getBigDecimal(16));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a WorkOrderItemDataVector object that consists
     * of WorkOrderItemData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WorkOrderItemData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WorkOrderItemDataVector()
     * @throws            SQLException
     */
    public static WorkOrderItemDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WorkOrderItemDataVector v = new WorkOrderItemDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WORK_ORDER_ITEM_ID,WORK_ORDER_ID,WARRANTY_ID,BUS_ENTITY_ID,STATUS_CD,SHORT_DESC,LONG_DESC,SEQUENCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ESTIMATE_LABOR,ESTIMATE_PART,ACTUAL_LABOR,ACTUAL_PART FROM CLW_WORK_ORDER_ITEM WHERE WORK_ORDER_ITEM_ID IN (");

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
            WorkOrderItemData x=null;
            while (rs.next()) {
                // build the object
                x=WorkOrderItemData.createValue();
                
                x.setWorkOrderItemId(rs.getInt(1));
                x.setWorkOrderId(rs.getInt(2));
                x.setWarrantyId(rs.getInt(3));
                x.setBusEntityId(rs.getInt(4));
                x.setStatusCd(rs.getString(5));
                x.setShortDesc(rs.getString(6));
                x.setLongDesc(rs.getString(7));
                x.setSequence(rs.getInt(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setAddBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                x.setEstimateLabor(rs.getBigDecimal(13));
                x.setEstimatePart(rs.getBigDecimal(14));
                x.setActualLabor(rs.getBigDecimal(15));
                x.setActualPart(rs.getBigDecimal(16));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a WorkOrderItemDataVector object of all
     * WorkOrderItemData objects in the database.
     * @param pCon An open database connection.
     * @return new WorkOrderItemDataVector()
     * @throws            SQLException
     */
    public static WorkOrderItemDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WORK_ORDER_ITEM_ID,WORK_ORDER_ID,WARRANTY_ID,BUS_ENTITY_ID,STATUS_CD,SHORT_DESC,LONG_DESC,SEQUENCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ESTIMATE_LABOR,ESTIMATE_PART,ACTUAL_LABOR,ACTUAL_PART FROM CLW_WORK_ORDER_ITEM";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WorkOrderItemDataVector v = new WorkOrderItemDataVector();
        WorkOrderItemData x = null;
        while (rs.next()) {
            // build the object
            x = WorkOrderItemData.createValue();
            
            x.setWorkOrderItemId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setWarrantyId(rs.getInt(3));
            x.setBusEntityId(rs.getInt(4));
            x.setStatusCd(rs.getString(5));
            x.setShortDesc(rs.getString(6));
            x.setLongDesc(rs.getString(7));
            x.setSequence(rs.getInt(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setEstimateLabor(rs.getBigDecimal(13));
            x.setEstimatePart(rs.getBigDecimal(14));
            x.setActualLabor(rs.getBigDecimal(15));
            x.setActualPart(rs.getBigDecimal(16));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * WorkOrderItemData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WORK_ORDER_ITEM_ID FROM CLW_WORK_ORDER_ITEM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER_ITEM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER_ITEM");
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
     * Inserts a WorkOrderItemData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderItemData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WorkOrderItemData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkOrderItemData insert(Connection pCon, WorkOrderItemData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WORK_ORDER_ITEM_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WORK_ORDER_ITEM_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWorkOrderItemId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WORK_ORDER_ITEM (WORK_ORDER_ITEM_ID,WORK_ORDER_ID,WARRANTY_ID,BUS_ENTITY_ID,STATUS_CD,SHORT_DESC,LONG_DESC,SEQUENCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ESTIMATE_LABOR,ESTIMATE_PART,ACTUAL_LABOR,ACTUAL_PART) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWorkOrderItemId());
        pstmt.setInt(2,pData.getWorkOrderId());
        if (pData.getWarrantyId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getWarrantyId());
        }

        pstmt.setInt(4,pData.getBusEntityId());
        pstmt.setString(5,pData.getStatusCd());
        pstmt.setString(6,pData.getShortDesc());
        pstmt.setString(7,pData.getLongDesc());
        pstmt.setInt(8,pData.getSequence());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());
        pstmt.setBigDecimal(13,pData.getEstimateLabor());
        pstmt.setBigDecimal(14,pData.getEstimatePart());
        pstmt.setBigDecimal(15,pData.getActualLabor());
        pstmt.setBigDecimal(16,pData.getActualPart());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORK_ORDER_ITEM_ID="+pData.getWorkOrderItemId());
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   WARRANTY_ID="+pData.getWarrantyId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   SEQUENCE="+pData.getSequence());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ESTIMATE_LABOR="+pData.getEstimateLabor());
            log.debug("SQL:   ESTIMATE_PART="+pData.getEstimatePart());
            log.debug("SQL:   ACTUAL_LABOR="+pData.getActualLabor());
            log.debug("SQL:   ACTUAL_PART="+pData.getActualPart());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setWorkOrderItemId(0);
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
     * Updates a WorkOrderItemData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderItemData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkOrderItemData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WORK_ORDER_ITEM SET WORK_ORDER_ID = ?,WARRANTY_ID = ?,BUS_ENTITY_ID = ?,STATUS_CD = ?,SHORT_DESC = ?,LONG_DESC = ?,SEQUENCE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,ESTIMATE_LABOR = ?,ESTIMATE_PART = ?,ACTUAL_LABOR = ?,ACTUAL_PART = ? WHERE WORK_ORDER_ITEM_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getWorkOrderId());
        if (pData.getWarrantyId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getWarrantyId());
        }

        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setInt(i++,pData.getSequence());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setBigDecimal(i++,pData.getEstimateLabor());
        pstmt.setBigDecimal(i++,pData.getEstimatePart());
        pstmt.setBigDecimal(i++,pData.getActualLabor());
        pstmt.setBigDecimal(i++,pData.getActualPart());
        pstmt.setInt(i++,pData.getWorkOrderItemId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   WARRANTY_ID="+pData.getWarrantyId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   SEQUENCE="+pData.getSequence());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ESTIMATE_LABOR="+pData.getEstimateLabor());
            log.debug("SQL:   ESTIMATE_PART="+pData.getEstimatePart());
            log.debug("SQL:   ACTUAL_LABOR="+pData.getActualLabor());
            log.debug("SQL:   ACTUAL_PART="+pData.getActualPart());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a WorkOrderItemData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkOrderItemId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkOrderItemId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WORK_ORDER_ITEM WHERE WORK_ORDER_ITEM_ID = " + pWorkOrderItemId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WorkOrderItemData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WORK_ORDER_ITEM");
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
     * Inserts a WorkOrderItemData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderItemData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WorkOrderItemData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WORK_ORDER_ITEM (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WORK_ORDER_ITEM_ID,WORK_ORDER_ID,WARRANTY_ID,BUS_ENTITY_ID,STATUS_CD,SHORT_DESC,LONG_DESC,SEQUENCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ESTIMATE_LABOR,ESTIMATE_PART,ACTUAL_LABOR,ACTUAL_PART) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWorkOrderItemId());
        pstmt.setInt(2+4,pData.getWorkOrderId());
        if (pData.getWarrantyId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getWarrantyId());
        }

        pstmt.setInt(4+4,pData.getBusEntityId());
        pstmt.setString(5+4,pData.getStatusCd());
        pstmt.setString(6+4,pData.getShortDesc());
        pstmt.setString(7+4,pData.getLongDesc());
        pstmt.setInt(8+4,pData.getSequence());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());
        pstmt.setBigDecimal(13+4,pData.getEstimateLabor());
        pstmt.setBigDecimal(14+4,pData.getEstimatePart());
        pstmt.setBigDecimal(15+4,pData.getActualLabor());
        pstmt.setBigDecimal(16+4,pData.getActualPart());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WorkOrderItemData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderItemData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WorkOrderItemData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkOrderItemData insert(Connection pCon, WorkOrderItemData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WorkOrderItemData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderItemData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkOrderItemData pData, boolean pLogFl)
        throws SQLException {
        WorkOrderItemData oldData = null;
        if(pLogFl) {
          int id = pData.getWorkOrderItemId();
          try {
          oldData = WorkOrderItemDataAccess.select(pCon,id);
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
     * Deletes a WorkOrderItemData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkOrderItemId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkOrderItemId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WORK_ORDER_ITEM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORK_ORDER_ITEM d WHERE WORK_ORDER_ITEM_ID = " + pWorkOrderItemId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWorkOrderItemId);
        return n;
     }

    /**
     * Deletes WorkOrderItemData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WORK_ORDER_ITEM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORK_ORDER_ITEM d ");
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

