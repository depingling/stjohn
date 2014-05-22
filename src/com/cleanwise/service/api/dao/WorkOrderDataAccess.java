
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WorkOrderDataAccess
 * Description:  This class is used to build access methods to the CLW_WORK_ORDER table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WorkOrderData;
import com.cleanwise.service.api.value.WorkOrderDataVector;
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
 * <code>WorkOrderDataAccess</code>
 */
public class WorkOrderDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WorkOrderDataAccess.class.getName());

    /** <code>CLW_WORK_ORDER</code> table name */
	/* Primary key: WORK_ORDER_ID */
	
    public static final String CLW_WORK_ORDER = "CLW_WORK_ORDER";
    
    /** <code>WORK_ORDER_ID</code> WORK_ORDER_ID column of table CLW_WORK_ORDER */
    public static final String WORK_ORDER_ID = "WORK_ORDER_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_WORK_ORDER */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>TYPE_CD</code> TYPE_CD column of table CLW_WORK_ORDER */
    public static final String TYPE_CD = "TYPE_CD";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_WORK_ORDER */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_WORK_ORDER */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_WORK_ORDER */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>CATEGORY_CD</code> CATEGORY_CD column of table CLW_WORK_ORDER */
    public static final String CATEGORY_CD = "CATEGORY_CD";
    /** <code>PRIORITY</code> PRIORITY column of table CLW_WORK_ORDER */
    public static final String PRIORITY = "PRIORITY";
    /** <code>ESTIMATE_HOURS</code> ESTIMATE_HOURS column of table CLW_WORK_ORDER */
    public static final String ESTIMATE_HOURS = "ESTIMATE_HOURS";
    /** <code>ESTIMATE_COST</code> ESTIMATE_COST column of table CLW_WORK_ORDER */
    public static final String ESTIMATE_COST = "ESTIMATE_COST";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WORK_ORDER */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WORK_ORDER */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WORK_ORDER */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WORK_ORDER */
    public static final String MOD_BY = "MOD_BY";
    /** <code>REQUEST_DATE</code> REQUEST_DATE column of table CLW_WORK_ORDER */
    public static final String REQUEST_DATE = "REQUEST_DATE";
    /** <code>ACTUAL_START_DATE</code> ACTUAL_START_DATE column of table CLW_WORK_ORDER */
    public static final String ACTUAL_START_DATE = "ACTUAL_START_DATE";
    /** <code>ACTUAL_FINISH_DATE</code> ACTUAL_FINISH_DATE column of table CLW_WORK_ORDER */
    public static final String ACTUAL_FINISH_DATE = "ACTUAL_FINISH_DATE";
    /** <code>ACTUAL_START_TIME</code> ACTUAL_START_TIME column of table CLW_WORK_ORDER */
    public static final String ACTUAL_START_TIME = "ACTUAL_START_TIME";
    /** <code>ACTUAL_FINISH_TIME</code> ACTUAL_FINISH_TIME column of table CLW_WORK_ORDER */
    public static final String ACTUAL_FINISH_TIME = "ACTUAL_FINISH_TIME";
    /** <code>ACTION_CD</code> ACTION_CD column of table CLW_WORK_ORDER */
    public static final String ACTION_CD = "ACTION_CD";
    /** <code>ESTIMATE_START_DATE</code> ESTIMATE_START_DATE column of table CLW_WORK_ORDER */
    public static final String ESTIMATE_START_DATE = "ESTIMATE_START_DATE";
    /** <code>ESTIMATE_FINISH_DATE</code> ESTIMATE_FINISH_DATE column of table CLW_WORK_ORDER */
    public static final String ESTIMATE_FINISH_DATE = "ESTIMATE_FINISH_DATE";
    /** <code>ESTIMATE_START_TIME</code> ESTIMATE_START_TIME column of table CLW_WORK_ORDER */
    public static final String ESTIMATE_START_TIME = "ESTIMATE_START_TIME";
    /** <code>ESTIMATE_FINISH_TIME</code> ESTIMATE_FINISH_TIME column of table CLW_WORK_ORDER */
    public static final String ESTIMATE_FINISH_TIME = "ESTIMATE_FINISH_TIME";
    /** <code>COST_CENTER_ID</code> COST_CENTER_ID column of table CLW_WORK_ORDER */
    public static final String COST_CENTER_ID = "COST_CENTER_ID";
    /** <code>WORK_ORDER_NUM</code> WORK_ORDER_NUM column of table CLW_WORK_ORDER */
    public static final String WORK_ORDER_NUM = "WORK_ORDER_NUM";
    /** <code>PO_NUMBER</code> PO_NUMBER column of table CLW_WORK_ORDER */
    public static final String PO_NUMBER = "PO_NUMBER";
    /** <code>NTE</code> NTE column of table CLW_WORK_ORDER */
    public static final String NTE = "NTE";
    /** <code>RECEIVED_DATE</code> RECEIVED_DATE column of table CLW_WORK_ORDER */
    public static final String RECEIVED_DATE = "RECEIVED_DATE";
    /** <code>RECEIVED_TIME</code> RECEIVED_TIME column of table CLW_WORK_ORDER */
    public static final String RECEIVED_TIME = "RECEIVED_TIME";

    /**
     * Constructor.
     */
    public WorkOrderDataAccess()
    {
    }

    /**
     * Gets a WorkOrderData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWorkOrderId The key requested.
     * @return new WorkOrderData()
     * @throws            SQLException
     */
    public static WorkOrderData select(Connection pCon, int pWorkOrderId)
        throws SQLException, DataNotFoundException {
        WorkOrderData x=null;
        String sql="SELECT WORK_ORDER_ID,BUS_ENTITY_ID,TYPE_CD,STATUS_CD,SHORT_DESC,LONG_DESC,CATEGORY_CD,PRIORITY,ESTIMATE_HOURS,ESTIMATE_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REQUEST_DATE,ACTUAL_START_DATE,ACTUAL_FINISH_DATE,ACTUAL_START_TIME,ACTUAL_FINISH_TIME,ACTION_CD,ESTIMATE_START_DATE,ESTIMATE_FINISH_DATE,ESTIMATE_START_TIME,ESTIMATE_FINISH_TIME,COST_CENTER_ID,WORK_ORDER_NUM,PO_NUMBER,NTE,RECEIVED_DATE,RECEIVED_TIME FROM CLW_WORK_ORDER WHERE WORK_ORDER_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWorkOrderId=" + pWorkOrderId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWorkOrderId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WorkOrderData.createValue();
            
            x.setWorkOrderId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setTypeCd(rs.getString(3));
            x.setStatusCd(rs.getString(4));
            x.setShortDesc(rs.getString(5));
            x.setLongDesc(rs.getString(6));
            x.setCategoryCd(rs.getString(7));
            x.setPriority(rs.getString(8));
            x.setEstimateHours(rs.getInt(9));
            x.setEstimateCost(rs.getBigDecimal(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setRequestDate(rs.getDate(15));
            x.setActualStartDate(rs.getDate(16));
            x.setActualFinishDate(rs.getDate(17));
            x.setActualStartTime(rs.getTimestamp(18));
            x.setActualFinishTime(rs.getTimestamp(19));
            x.setActionCd(rs.getString(20));
            x.setEstimateStartDate(rs.getDate(21));
            x.setEstimateFinishDate(rs.getDate(22));
            x.setEstimateStartTime(rs.getTimestamp(23));
            x.setEstimateFinishTime(rs.getTimestamp(24));
            x.setCostCenterId(rs.getInt(25));
            x.setWorkOrderNum(rs.getString(26));
            x.setPoNumber(rs.getString(27));
            x.setNte(rs.getBigDecimal(28));
            x.setReceivedDate(rs.getDate(29));
            x.setReceivedTime(rs.getTimestamp(30));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WORK_ORDER_ID :" + pWorkOrderId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WorkOrderDataVector object that consists
     * of WorkOrderData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WorkOrderDataVector()
     * @throws            SQLException
     */
    public static WorkOrderDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WorkOrderData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WORK_ORDER.WORK_ORDER_ID,CLW_WORK_ORDER.BUS_ENTITY_ID,CLW_WORK_ORDER.TYPE_CD,CLW_WORK_ORDER.STATUS_CD,CLW_WORK_ORDER.SHORT_DESC,CLW_WORK_ORDER.LONG_DESC,CLW_WORK_ORDER.CATEGORY_CD,CLW_WORK_ORDER.PRIORITY,CLW_WORK_ORDER.ESTIMATE_HOURS,CLW_WORK_ORDER.ESTIMATE_COST,CLW_WORK_ORDER.ADD_DATE,CLW_WORK_ORDER.ADD_BY,CLW_WORK_ORDER.MOD_DATE,CLW_WORK_ORDER.MOD_BY,CLW_WORK_ORDER.REQUEST_DATE,CLW_WORK_ORDER.ACTUAL_START_DATE,CLW_WORK_ORDER.ACTUAL_FINISH_DATE,CLW_WORK_ORDER.ACTUAL_START_TIME,CLW_WORK_ORDER.ACTUAL_FINISH_TIME,CLW_WORK_ORDER.ACTION_CD,CLW_WORK_ORDER.ESTIMATE_START_DATE,CLW_WORK_ORDER.ESTIMATE_FINISH_DATE,CLW_WORK_ORDER.ESTIMATE_START_TIME,CLW_WORK_ORDER.ESTIMATE_FINISH_TIME,CLW_WORK_ORDER.COST_CENTER_ID,CLW_WORK_ORDER.WORK_ORDER_NUM,CLW_WORK_ORDER.PO_NUMBER,CLW_WORK_ORDER.NTE,CLW_WORK_ORDER.RECEIVED_DATE,CLW_WORK_ORDER.RECEIVED_TIME";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WorkOrderData Object.
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
    *@returns a populated WorkOrderData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WorkOrderData x = WorkOrderData.createValue();
         
         x.setWorkOrderId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setTypeCd(rs.getString(3+offset));
         x.setStatusCd(rs.getString(4+offset));
         x.setShortDesc(rs.getString(5+offset));
         x.setLongDesc(rs.getString(6+offset));
         x.setCategoryCd(rs.getString(7+offset));
         x.setPriority(rs.getString(8+offset));
         x.setEstimateHours(rs.getInt(9+offset));
         x.setEstimateCost(rs.getBigDecimal(10+offset));
         x.setAddDate(rs.getTimestamp(11+offset));
         x.setAddBy(rs.getString(12+offset));
         x.setModDate(rs.getTimestamp(13+offset));
         x.setModBy(rs.getString(14+offset));
         x.setRequestDate(rs.getDate(15+offset));
         x.setActualStartDate(rs.getDate(16+offset));
         x.setActualFinishDate(rs.getDate(17+offset));
         x.setActualStartTime(rs.getTimestamp(18+offset));
         x.setActualFinishTime(rs.getTimestamp(19+offset));
         x.setActionCd(rs.getString(20+offset));
         x.setEstimateStartDate(rs.getDate(21+offset));
         x.setEstimateFinishDate(rs.getDate(22+offset));
         x.setEstimateStartTime(rs.getTimestamp(23+offset));
         x.setEstimateFinishTime(rs.getTimestamp(24+offset));
         x.setCostCenterId(rs.getInt(25+offset));
         x.setWorkOrderNum(rs.getString(26+offset));
         x.setPoNumber(rs.getString(27+offset));
         x.setNte(rs.getBigDecimal(28+offset));
         x.setReceivedDate(rs.getDate(29+offset));
         x.setReceivedTime(rs.getTimestamp(30+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WorkOrderData Object represents.
    */
    public int getColumnCount(){
        return 30;
    }

    /**
     * Gets a WorkOrderDataVector object that consists
     * of WorkOrderData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WorkOrderDataVector()
     * @throws            SQLException
     */
    public static WorkOrderDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WORK_ORDER_ID,BUS_ENTITY_ID,TYPE_CD,STATUS_CD,SHORT_DESC,LONG_DESC,CATEGORY_CD,PRIORITY,ESTIMATE_HOURS,ESTIMATE_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REQUEST_DATE,ACTUAL_START_DATE,ACTUAL_FINISH_DATE,ACTUAL_START_TIME,ACTUAL_FINISH_TIME,ACTION_CD,ESTIMATE_START_DATE,ESTIMATE_FINISH_DATE,ESTIMATE_START_TIME,ESTIMATE_FINISH_TIME,COST_CENTER_ID,WORK_ORDER_NUM,PO_NUMBER,NTE,RECEIVED_DATE,RECEIVED_TIME FROM CLW_WORK_ORDER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WORK_ORDER.WORK_ORDER_ID,CLW_WORK_ORDER.BUS_ENTITY_ID,CLW_WORK_ORDER.TYPE_CD,CLW_WORK_ORDER.STATUS_CD,CLW_WORK_ORDER.SHORT_DESC,CLW_WORK_ORDER.LONG_DESC,CLW_WORK_ORDER.CATEGORY_CD,CLW_WORK_ORDER.PRIORITY,CLW_WORK_ORDER.ESTIMATE_HOURS,CLW_WORK_ORDER.ESTIMATE_COST,CLW_WORK_ORDER.ADD_DATE,CLW_WORK_ORDER.ADD_BY,CLW_WORK_ORDER.MOD_DATE,CLW_WORK_ORDER.MOD_BY,CLW_WORK_ORDER.REQUEST_DATE,CLW_WORK_ORDER.ACTUAL_START_DATE,CLW_WORK_ORDER.ACTUAL_FINISH_DATE,CLW_WORK_ORDER.ACTUAL_START_TIME,CLW_WORK_ORDER.ACTUAL_FINISH_TIME,CLW_WORK_ORDER.ACTION_CD,CLW_WORK_ORDER.ESTIMATE_START_DATE,CLW_WORK_ORDER.ESTIMATE_FINISH_DATE,CLW_WORK_ORDER.ESTIMATE_START_TIME,CLW_WORK_ORDER.ESTIMATE_FINISH_TIME,CLW_WORK_ORDER.COST_CENTER_ID,CLW_WORK_ORDER.WORK_ORDER_NUM,CLW_WORK_ORDER.PO_NUMBER,CLW_WORK_ORDER.NTE,CLW_WORK_ORDER.RECEIVED_DATE,CLW_WORK_ORDER.RECEIVED_TIME FROM CLW_WORK_ORDER");
                where = pCriteria.getSqlClause("CLW_WORK_ORDER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORK_ORDER.equals(otherTable)){
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
        WorkOrderDataVector v = new WorkOrderDataVector();
        while (rs.next()) {
            WorkOrderData x = WorkOrderData.createValue();
            
            x.setWorkOrderId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setTypeCd(rs.getString(3));
            x.setStatusCd(rs.getString(4));
            x.setShortDesc(rs.getString(5));
            x.setLongDesc(rs.getString(6));
            x.setCategoryCd(rs.getString(7));
            x.setPriority(rs.getString(8));
            x.setEstimateHours(rs.getInt(9));
            x.setEstimateCost(rs.getBigDecimal(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setRequestDate(rs.getDate(15));
            x.setActualStartDate(rs.getDate(16));
            x.setActualFinishDate(rs.getDate(17));
            x.setActualStartTime(rs.getTimestamp(18));
            x.setActualFinishTime(rs.getTimestamp(19));
            x.setActionCd(rs.getString(20));
            x.setEstimateStartDate(rs.getDate(21));
            x.setEstimateFinishDate(rs.getDate(22));
            x.setEstimateStartTime(rs.getTimestamp(23));
            x.setEstimateFinishTime(rs.getTimestamp(24));
            x.setCostCenterId(rs.getInt(25));
            x.setWorkOrderNum(rs.getString(26));
            x.setPoNumber(rs.getString(27));
            x.setNte(rs.getBigDecimal(28));
            x.setReceivedDate(rs.getDate(29));
            x.setReceivedTime(rs.getTimestamp(30));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a WorkOrderDataVector object that consists
     * of WorkOrderData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WorkOrderData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WorkOrderDataVector()
     * @throws            SQLException
     */
    public static WorkOrderDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WorkOrderDataVector v = new WorkOrderDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WORK_ORDER_ID,BUS_ENTITY_ID,TYPE_CD,STATUS_CD,SHORT_DESC,LONG_DESC,CATEGORY_CD,PRIORITY,ESTIMATE_HOURS,ESTIMATE_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REQUEST_DATE,ACTUAL_START_DATE,ACTUAL_FINISH_DATE,ACTUAL_START_TIME,ACTUAL_FINISH_TIME,ACTION_CD,ESTIMATE_START_DATE,ESTIMATE_FINISH_DATE,ESTIMATE_START_TIME,ESTIMATE_FINISH_TIME,COST_CENTER_ID,WORK_ORDER_NUM,PO_NUMBER,NTE,RECEIVED_DATE,RECEIVED_TIME FROM CLW_WORK_ORDER WHERE WORK_ORDER_ID IN (");

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
            WorkOrderData x=null;
            while (rs.next()) {
                // build the object
                x=WorkOrderData.createValue();
                
                x.setWorkOrderId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setTypeCd(rs.getString(3));
                x.setStatusCd(rs.getString(4));
                x.setShortDesc(rs.getString(5));
                x.setLongDesc(rs.getString(6));
                x.setCategoryCd(rs.getString(7));
                x.setPriority(rs.getString(8));
                x.setEstimateHours(rs.getInt(9));
                x.setEstimateCost(rs.getBigDecimal(10));
                x.setAddDate(rs.getTimestamp(11));
                x.setAddBy(rs.getString(12));
                x.setModDate(rs.getTimestamp(13));
                x.setModBy(rs.getString(14));
                x.setRequestDate(rs.getDate(15));
                x.setActualStartDate(rs.getDate(16));
                x.setActualFinishDate(rs.getDate(17));
                x.setActualStartTime(rs.getTimestamp(18));
                x.setActualFinishTime(rs.getTimestamp(19));
                x.setActionCd(rs.getString(20));
                x.setEstimateStartDate(rs.getDate(21));
                x.setEstimateFinishDate(rs.getDate(22));
                x.setEstimateStartTime(rs.getTimestamp(23));
                x.setEstimateFinishTime(rs.getTimestamp(24));
                x.setCostCenterId(rs.getInt(25));
                x.setWorkOrderNum(rs.getString(26));
                x.setPoNumber(rs.getString(27));
                x.setNte(rs.getBigDecimal(28));
                x.setReceivedDate(rs.getDate(29));
                x.setReceivedTime(rs.getTimestamp(30));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a WorkOrderDataVector object of all
     * WorkOrderData objects in the database.
     * @param pCon An open database connection.
     * @return new WorkOrderDataVector()
     * @throws            SQLException
     */
    public static WorkOrderDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WORK_ORDER_ID,BUS_ENTITY_ID,TYPE_CD,STATUS_CD,SHORT_DESC,LONG_DESC,CATEGORY_CD,PRIORITY,ESTIMATE_HOURS,ESTIMATE_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REQUEST_DATE,ACTUAL_START_DATE,ACTUAL_FINISH_DATE,ACTUAL_START_TIME,ACTUAL_FINISH_TIME,ACTION_CD,ESTIMATE_START_DATE,ESTIMATE_FINISH_DATE,ESTIMATE_START_TIME,ESTIMATE_FINISH_TIME,COST_CENTER_ID,WORK_ORDER_NUM,PO_NUMBER,NTE,RECEIVED_DATE,RECEIVED_TIME FROM CLW_WORK_ORDER";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WorkOrderDataVector v = new WorkOrderDataVector();
        WorkOrderData x = null;
        while (rs.next()) {
            // build the object
            x = WorkOrderData.createValue();
            
            x.setWorkOrderId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setTypeCd(rs.getString(3));
            x.setStatusCd(rs.getString(4));
            x.setShortDesc(rs.getString(5));
            x.setLongDesc(rs.getString(6));
            x.setCategoryCd(rs.getString(7));
            x.setPriority(rs.getString(8));
            x.setEstimateHours(rs.getInt(9));
            x.setEstimateCost(rs.getBigDecimal(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setRequestDate(rs.getDate(15));
            x.setActualStartDate(rs.getDate(16));
            x.setActualFinishDate(rs.getDate(17));
            x.setActualStartTime(rs.getTimestamp(18));
            x.setActualFinishTime(rs.getTimestamp(19));
            x.setActionCd(rs.getString(20));
            x.setEstimateStartDate(rs.getDate(21));
            x.setEstimateFinishDate(rs.getDate(22));
            x.setEstimateStartTime(rs.getTimestamp(23));
            x.setEstimateFinishTime(rs.getTimestamp(24));
            x.setCostCenterId(rs.getInt(25));
            x.setWorkOrderNum(rs.getString(26));
            x.setPoNumber(rs.getString(27));
            x.setNte(rs.getBigDecimal(28));
            x.setReceivedDate(rs.getDate(29));
            x.setReceivedTime(rs.getTimestamp(30));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * WorkOrderData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT WORK_ORDER_ID FROM CLW_WORK_ORDER");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT WORK_ORDER_ID FROM CLW_WORK_ORDER");
                where = pCriteria.getSqlClause("CLW_WORK_ORDER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORK_ORDER.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER");
                where = pCriteria.getSqlClause("CLW_WORK_ORDER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORK_ORDER.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER");
                where = pCriteria.getSqlClause("CLW_WORK_ORDER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORK_ORDER.equals(otherTable)){
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
     * Inserts a WorkOrderData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WorkOrderData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkOrderData insert(Connection pCon, WorkOrderData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WORK_ORDER_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WORK_ORDER_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWorkOrderId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WORK_ORDER (WORK_ORDER_ID,BUS_ENTITY_ID,TYPE_CD,STATUS_CD,SHORT_DESC,LONG_DESC,CATEGORY_CD,PRIORITY,ESTIMATE_HOURS,ESTIMATE_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REQUEST_DATE,ACTUAL_START_DATE,ACTUAL_FINISH_DATE,ACTUAL_START_TIME,ACTUAL_FINISH_TIME,ACTION_CD,ESTIMATE_START_DATE,ESTIMATE_FINISH_DATE,ESTIMATE_START_TIME,ESTIMATE_FINISH_TIME,COST_CENTER_ID,WORK_ORDER_NUM,PO_NUMBER,NTE,RECEIVED_DATE,RECEIVED_TIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWorkOrderId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getBusEntityId());
        }

        pstmt.setString(3,pData.getTypeCd());
        pstmt.setString(4,pData.getStatusCd());
        pstmt.setString(5,pData.getShortDesc());
        pstmt.setString(6,pData.getLongDesc());
        pstmt.setString(7,pData.getCategoryCd());
        pstmt.setString(8,pData.getPriority());
        pstmt.setInt(9,pData.getEstimateHours());
        pstmt.setBigDecimal(10,pData.getEstimateCost());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12,pData.getAddBy());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14,pData.getModBy());
        pstmt.setDate(15,DBAccess.toSQLDate(pData.getRequestDate()));
        pstmt.setDate(16,DBAccess.toSQLDate(pData.getActualStartDate()));
        pstmt.setDate(17,DBAccess.toSQLDate(pData.getActualFinishDate()));
        pstmt.setTimestamp(18,DBAccess.toSQLTimestamp(pData.getActualStartTime()));
        pstmt.setTimestamp(19,DBAccess.toSQLTimestamp(pData.getActualFinishTime()));
        pstmt.setString(20,pData.getActionCd());
        pstmt.setDate(21,DBAccess.toSQLDate(pData.getEstimateStartDate()));
        pstmt.setDate(22,DBAccess.toSQLDate(pData.getEstimateFinishDate()));
        pstmt.setTimestamp(23,DBAccess.toSQLTimestamp(pData.getEstimateStartTime()));
        pstmt.setTimestamp(24,DBAccess.toSQLTimestamp(pData.getEstimateFinishTime()));
        if (pData.getCostCenterId() == 0) {
            pstmt.setNull(25, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(25,pData.getCostCenterId());
        }

        pstmt.setString(26,pData.getWorkOrderNum());
        pstmt.setString(27,pData.getPoNumber());
        pstmt.setBigDecimal(28,pData.getNte());
        pstmt.setDate(29,DBAccess.toSQLDate(pData.getReceivedDate()));
        pstmt.setTimestamp(30,DBAccess.toSQLTimestamp(pData.getReceivedTime()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   TYPE_CD="+pData.getTypeCd());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   CATEGORY_CD="+pData.getCategoryCd());
            log.debug("SQL:   PRIORITY="+pData.getPriority());
            log.debug("SQL:   ESTIMATE_HOURS="+pData.getEstimateHours());
            log.debug("SQL:   ESTIMATE_COST="+pData.getEstimateCost());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   REQUEST_DATE="+pData.getRequestDate());
            log.debug("SQL:   ACTUAL_START_DATE="+pData.getActualStartDate());
            log.debug("SQL:   ACTUAL_FINISH_DATE="+pData.getActualFinishDate());
            log.debug("SQL:   ACTUAL_START_TIME="+pData.getActualStartTime());
            log.debug("SQL:   ACTUAL_FINISH_TIME="+pData.getActualFinishTime());
            log.debug("SQL:   ACTION_CD="+pData.getActionCd());
            log.debug("SQL:   ESTIMATE_START_DATE="+pData.getEstimateStartDate());
            log.debug("SQL:   ESTIMATE_FINISH_DATE="+pData.getEstimateFinishDate());
            log.debug("SQL:   ESTIMATE_START_TIME="+pData.getEstimateStartTime());
            log.debug("SQL:   ESTIMATE_FINISH_TIME="+pData.getEstimateFinishTime());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   WORK_ORDER_NUM="+pData.getWorkOrderNum());
            log.debug("SQL:   PO_NUMBER="+pData.getPoNumber());
            log.debug("SQL:   NTE="+pData.getNte());
            log.debug("SQL:   RECEIVED_DATE="+pData.getReceivedDate());
            log.debug("SQL:   RECEIVED_TIME="+pData.getReceivedTime());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setWorkOrderId(0);
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
     * Updates a WorkOrderData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkOrderData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WORK_ORDER SET BUS_ENTITY_ID = ?,TYPE_CD = ?,STATUS_CD = ?,SHORT_DESC = ?,LONG_DESC = ?,CATEGORY_CD = ?,PRIORITY = ?,ESTIMATE_HOURS = ?,ESTIMATE_COST = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,REQUEST_DATE = ?,ACTUAL_START_DATE = ?,ACTUAL_FINISH_DATE = ?,ACTUAL_START_TIME = ?,ACTUAL_FINISH_TIME = ?,ACTION_CD = ?,ESTIMATE_START_DATE = ?,ESTIMATE_FINISH_DATE = ?,ESTIMATE_START_TIME = ?,ESTIMATE_FINISH_TIME = ?,COST_CENTER_ID = ?,WORK_ORDER_NUM = ?,PO_NUMBER = ?,NTE = ?,RECEIVED_DATE = ?,RECEIVED_TIME = ? WHERE WORK_ORDER_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getTypeCd());
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setString(i++,pData.getCategoryCd());
        pstmt.setString(i++,pData.getPriority());
        pstmt.setInt(i++,pData.getEstimateHours());
        pstmt.setBigDecimal(i++,pData.getEstimateCost());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getRequestDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getActualStartDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getActualFinishDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getActualStartTime()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getActualFinishTime()));
        pstmt.setString(i++,pData.getActionCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEstimateStartDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEstimateFinishDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getEstimateStartTime()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getEstimateFinishTime()));
        if (pData.getCostCenterId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getCostCenterId());
        }

        pstmt.setString(i++,pData.getWorkOrderNum());
        pstmt.setString(i++,pData.getPoNumber());
        pstmt.setBigDecimal(i++,pData.getNte());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getReceivedDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getReceivedTime()));
        pstmt.setInt(i++,pData.getWorkOrderId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   TYPE_CD="+pData.getTypeCd());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   CATEGORY_CD="+pData.getCategoryCd());
            log.debug("SQL:   PRIORITY="+pData.getPriority());
            log.debug("SQL:   ESTIMATE_HOURS="+pData.getEstimateHours());
            log.debug("SQL:   ESTIMATE_COST="+pData.getEstimateCost());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   REQUEST_DATE="+pData.getRequestDate());
            log.debug("SQL:   ACTUAL_START_DATE="+pData.getActualStartDate());
            log.debug("SQL:   ACTUAL_FINISH_DATE="+pData.getActualFinishDate());
            log.debug("SQL:   ACTUAL_START_TIME="+pData.getActualStartTime());
            log.debug("SQL:   ACTUAL_FINISH_TIME="+pData.getActualFinishTime());
            log.debug("SQL:   ACTION_CD="+pData.getActionCd());
            log.debug("SQL:   ESTIMATE_START_DATE="+pData.getEstimateStartDate());
            log.debug("SQL:   ESTIMATE_FINISH_DATE="+pData.getEstimateFinishDate());
            log.debug("SQL:   ESTIMATE_START_TIME="+pData.getEstimateStartTime());
            log.debug("SQL:   ESTIMATE_FINISH_TIME="+pData.getEstimateFinishTime());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   WORK_ORDER_NUM="+pData.getWorkOrderNum());
            log.debug("SQL:   PO_NUMBER="+pData.getPoNumber());
            log.debug("SQL:   NTE="+pData.getNte());
            log.debug("SQL:   RECEIVED_DATE="+pData.getReceivedDate());
            log.debug("SQL:   RECEIVED_TIME="+pData.getReceivedTime());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a WorkOrderData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkOrderId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkOrderId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WORK_ORDER WHERE WORK_ORDER_ID = " + pWorkOrderId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WorkOrderData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WORK_ORDER");
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
     * Inserts a WorkOrderData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WorkOrderData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WORK_ORDER (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WORK_ORDER_ID,BUS_ENTITY_ID,TYPE_CD,STATUS_CD,SHORT_DESC,LONG_DESC,CATEGORY_CD,PRIORITY,ESTIMATE_HOURS,ESTIMATE_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REQUEST_DATE,ACTUAL_START_DATE,ACTUAL_FINISH_DATE,ACTUAL_START_TIME,ACTUAL_FINISH_TIME,ACTION_CD,ESTIMATE_START_DATE,ESTIMATE_FINISH_DATE,ESTIMATE_START_TIME,ESTIMATE_FINISH_TIME,COST_CENTER_ID,WORK_ORDER_NUM,PO_NUMBER,NTE,RECEIVED_DATE,RECEIVED_TIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWorkOrderId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getBusEntityId());
        }

        pstmt.setString(3+4,pData.getTypeCd());
        pstmt.setString(4+4,pData.getStatusCd());
        pstmt.setString(5+4,pData.getShortDesc());
        pstmt.setString(6+4,pData.getLongDesc());
        pstmt.setString(7+4,pData.getCategoryCd());
        pstmt.setString(8+4,pData.getPriority());
        pstmt.setInt(9+4,pData.getEstimateHours());
        pstmt.setBigDecimal(10+4,pData.getEstimateCost());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12+4,pData.getAddBy());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14+4,pData.getModBy());
        pstmt.setDate(15+4,DBAccess.toSQLDate(pData.getRequestDate()));
        pstmt.setDate(16+4,DBAccess.toSQLDate(pData.getActualStartDate()));
        pstmt.setDate(17+4,DBAccess.toSQLDate(pData.getActualFinishDate()));
        pstmt.setTimestamp(18+4,DBAccess.toSQLTimestamp(pData.getActualStartTime()));
        pstmt.setTimestamp(19+4,DBAccess.toSQLTimestamp(pData.getActualFinishTime()));
        pstmt.setString(20+4,pData.getActionCd());
        pstmt.setDate(21+4,DBAccess.toSQLDate(pData.getEstimateStartDate()));
        pstmt.setDate(22+4,DBAccess.toSQLDate(pData.getEstimateFinishDate()));
        pstmt.setTimestamp(23+4,DBAccess.toSQLTimestamp(pData.getEstimateStartTime()));
        pstmt.setTimestamp(24+4,DBAccess.toSQLTimestamp(pData.getEstimateFinishTime()));
        if (pData.getCostCenterId() == 0) {
            pstmt.setNull(25+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(25+4,pData.getCostCenterId());
        }

        pstmt.setString(26+4,pData.getWorkOrderNum());
        pstmt.setString(27+4,pData.getPoNumber());
        pstmt.setBigDecimal(28+4,pData.getNte());
        pstmt.setDate(29+4,DBAccess.toSQLDate(pData.getReceivedDate()));
        pstmt.setTimestamp(30+4,DBAccess.toSQLTimestamp(pData.getReceivedTime()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WorkOrderData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WorkOrderData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkOrderData insert(Connection pCon, WorkOrderData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WorkOrderData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkOrderData pData, boolean pLogFl)
        throws SQLException {
        WorkOrderData oldData = null;
        if(pLogFl) {
          int id = pData.getWorkOrderId();
          try {
          oldData = WorkOrderDataAccess.select(pCon,id);
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
     * Deletes a WorkOrderData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkOrderId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkOrderId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WORK_ORDER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORK_ORDER d WHERE WORK_ORDER_ID = " + pWorkOrderId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWorkOrderId);
        return n;
     }

    /**
     * Deletes WorkOrderData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WORK_ORDER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORK_ORDER d ");
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

