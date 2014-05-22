
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderScheduleDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_SCHEDULE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderScheduleData;
import com.cleanwise.service.api.value.OrderScheduleDataVector;
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
 * <code>OrderScheduleDataAccess</code>
 */
public class OrderScheduleDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderScheduleDataAccess.class.getName());

    /** <code>CLW_ORDER_SCHEDULE</code> table name */
	/* Primary key: ORDER_SCHEDULE_ID */
	
    public static final String CLW_ORDER_SCHEDULE = "CLW_ORDER_SCHEDULE";
    
    /** <code>ORDER_SCHEDULE_ID</code> ORDER_SCHEDULE_ID column of table CLW_ORDER_SCHEDULE */
    public static final String ORDER_SCHEDULE_ID = "ORDER_SCHEDULE_ID";
    /** <code>RECORD_STATUS_CD</code> RECORD_STATUS_CD column of table CLW_ORDER_SCHEDULE */
    public static final String RECORD_STATUS_CD = "RECORD_STATUS_CD";
    /** <code>ORDER_GUIDE_ID</code> ORDER_GUIDE_ID column of table CLW_ORDER_SCHEDULE */
    public static final String ORDER_GUIDE_ID = "ORDER_GUIDE_ID";
    /** <code>USER_ID</code> USER_ID column of table CLW_ORDER_SCHEDULE */
    public static final String USER_ID = "USER_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_ORDER_SCHEDULE */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>ORDER_SCHEDULE_CD</code> ORDER_SCHEDULE_CD column of table CLW_ORDER_SCHEDULE */
    public static final String ORDER_SCHEDULE_CD = "ORDER_SCHEDULE_CD";
    /** <code>ORDER_SCHEDULE_RULE_CD</code> ORDER_SCHEDULE_RULE_CD column of table CLW_ORDER_SCHEDULE */
    public static final String ORDER_SCHEDULE_RULE_CD = "ORDER_SCHEDULE_RULE_CD";
    /** <code>CYCLE</code> CYCLE column of table CLW_ORDER_SCHEDULE */
    public static final String CYCLE = "CYCLE";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_ORDER_SCHEDULE */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_ORDER_SCHEDULE */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>CC_EMAIL</code> CC_EMAIL column of table CLW_ORDER_SCHEDULE */
    public static final String CC_EMAIL = "CC_EMAIL";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_SCHEDULE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_SCHEDULE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_SCHEDULE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_SCHEDULE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>WORK_ORDER_ID</code> WORK_ORDER_ID column of table CLW_ORDER_SCHEDULE */
    public static final String WORK_ORDER_ID = "WORK_ORDER_ID";

    /**
     * Constructor.
     */
    public OrderScheduleDataAccess()
    {
    }

    /**
     * Gets a OrderScheduleData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderScheduleId The key requested.
     * @return new OrderScheduleData()
     * @throws            SQLException
     */
    public static OrderScheduleData select(Connection pCon, int pOrderScheduleId)
        throws SQLException, DataNotFoundException {
        OrderScheduleData x=null;
        String sql="SELECT ORDER_SCHEDULE_ID,RECORD_STATUS_CD,ORDER_GUIDE_ID,USER_ID,BUS_ENTITY_ID,ORDER_SCHEDULE_CD,ORDER_SCHEDULE_RULE_CD,CYCLE,EFF_DATE,EXP_DATE,CC_EMAIL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,WORK_ORDER_ID FROM CLW_ORDER_SCHEDULE WHERE ORDER_SCHEDULE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderScheduleId=" + pOrderScheduleId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderScheduleId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderScheduleData.createValue();
            
            x.setOrderScheduleId(rs.getInt(1));
            x.setRecordStatusCd(rs.getString(2));
            x.setOrderGuideId(rs.getInt(3));
            x.setUserId(rs.getInt(4));
            x.setBusEntityId(rs.getInt(5));
            x.setOrderScheduleCd(rs.getString(6));
            x.setOrderScheduleRuleCd(rs.getString(7));
            x.setCycle(rs.getInt(8));
            x.setEffDate(rs.getDate(9));
            x.setExpDate(rs.getDate(10));
            x.setCcEmail(rs.getString(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddBy(rs.getString(13));
            x.setModDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));
            x.setWorkOrderId(rs.getInt(16));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_SCHEDULE_ID :" + pOrderScheduleId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderScheduleDataVector object that consists
     * of OrderScheduleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderScheduleDataVector()
     * @throws            SQLException
     */
    public static OrderScheduleDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderScheduleData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_SCHEDULE.ORDER_SCHEDULE_ID,CLW_ORDER_SCHEDULE.RECORD_STATUS_CD,CLW_ORDER_SCHEDULE.ORDER_GUIDE_ID,CLW_ORDER_SCHEDULE.USER_ID,CLW_ORDER_SCHEDULE.BUS_ENTITY_ID,CLW_ORDER_SCHEDULE.ORDER_SCHEDULE_CD,CLW_ORDER_SCHEDULE.ORDER_SCHEDULE_RULE_CD,CLW_ORDER_SCHEDULE.CYCLE,CLW_ORDER_SCHEDULE.EFF_DATE,CLW_ORDER_SCHEDULE.EXP_DATE,CLW_ORDER_SCHEDULE.CC_EMAIL,CLW_ORDER_SCHEDULE.ADD_DATE,CLW_ORDER_SCHEDULE.ADD_BY,CLW_ORDER_SCHEDULE.MOD_DATE,CLW_ORDER_SCHEDULE.MOD_BY,CLW_ORDER_SCHEDULE.WORK_ORDER_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderScheduleData Object.
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
    *@returns a populated OrderScheduleData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderScheduleData x = OrderScheduleData.createValue();
         
         x.setOrderScheduleId(rs.getInt(1+offset));
         x.setRecordStatusCd(rs.getString(2+offset));
         x.setOrderGuideId(rs.getInt(3+offset));
         x.setUserId(rs.getInt(4+offset));
         x.setBusEntityId(rs.getInt(5+offset));
         x.setOrderScheduleCd(rs.getString(6+offset));
         x.setOrderScheduleRuleCd(rs.getString(7+offset));
         x.setCycle(rs.getInt(8+offset));
         x.setEffDate(rs.getDate(9+offset));
         x.setExpDate(rs.getDate(10+offset));
         x.setCcEmail(rs.getString(11+offset));
         x.setAddDate(rs.getTimestamp(12+offset));
         x.setAddBy(rs.getString(13+offset));
         x.setModDate(rs.getTimestamp(14+offset));
         x.setModBy(rs.getString(15+offset));
         x.setWorkOrderId(rs.getInt(16+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderScheduleData Object represents.
    */
    public int getColumnCount(){
        return 16;
    }

    /**
     * Gets a OrderScheduleDataVector object that consists
     * of OrderScheduleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderScheduleDataVector()
     * @throws            SQLException
     */
    public static OrderScheduleDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_SCHEDULE_ID,RECORD_STATUS_CD,ORDER_GUIDE_ID,USER_ID,BUS_ENTITY_ID,ORDER_SCHEDULE_CD,ORDER_SCHEDULE_RULE_CD,CYCLE,EFF_DATE,EXP_DATE,CC_EMAIL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,WORK_ORDER_ID FROM CLW_ORDER_SCHEDULE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_SCHEDULE.ORDER_SCHEDULE_ID,CLW_ORDER_SCHEDULE.RECORD_STATUS_CD,CLW_ORDER_SCHEDULE.ORDER_GUIDE_ID,CLW_ORDER_SCHEDULE.USER_ID,CLW_ORDER_SCHEDULE.BUS_ENTITY_ID,CLW_ORDER_SCHEDULE.ORDER_SCHEDULE_CD,CLW_ORDER_SCHEDULE.ORDER_SCHEDULE_RULE_CD,CLW_ORDER_SCHEDULE.CYCLE,CLW_ORDER_SCHEDULE.EFF_DATE,CLW_ORDER_SCHEDULE.EXP_DATE,CLW_ORDER_SCHEDULE.CC_EMAIL,CLW_ORDER_SCHEDULE.ADD_DATE,CLW_ORDER_SCHEDULE.ADD_BY,CLW_ORDER_SCHEDULE.MOD_DATE,CLW_ORDER_SCHEDULE.MOD_BY,CLW_ORDER_SCHEDULE.WORK_ORDER_ID FROM CLW_ORDER_SCHEDULE");
                where = pCriteria.getSqlClause("CLW_ORDER_SCHEDULE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_SCHEDULE.equals(otherTable)){
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
        OrderScheduleDataVector v = new OrderScheduleDataVector();
        while (rs.next()) {
            OrderScheduleData x = OrderScheduleData.createValue();
            
            x.setOrderScheduleId(rs.getInt(1));
            x.setRecordStatusCd(rs.getString(2));
            x.setOrderGuideId(rs.getInt(3));
            x.setUserId(rs.getInt(4));
            x.setBusEntityId(rs.getInt(5));
            x.setOrderScheduleCd(rs.getString(6));
            x.setOrderScheduleRuleCd(rs.getString(7));
            x.setCycle(rs.getInt(8));
            x.setEffDate(rs.getDate(9));
            x.setExpDate(rs.getDate(10));
            x.setCcEmail(rs.getString(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddBy(rs.getString(13));
            x.setModDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));
            x.setWorkOrderId(rs.getInt(16));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderScheduleDataVector object that consists
     * of OrderScheduleData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderScheduleData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderScheduleDataVector()
     * @throws            SQLException
     */
    public static OrderScheduleDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderScheduleDataVector v = new OrderScheduleDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_SCHEDULE_ID,RECORD_STATUS_CD,ORDER_GUIDE_ID,USER_ID,BUS_ENTITY_ID,ORDER_SCHEDULE_CD,ORDER_SCHEDULE_RULE_CD,CYCLE,EFF_DATE,EXP_DATE,CC_EMAIL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,WORK_ORDER_ID FROM CLW_ORDER_SCHEDULE WHERE ORDER_SCHEDULE_ID IN (");

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
            OrderScheduleData x=null;
            while (rs.next()) {
                // build the object
                x=OrderScheduleData.createValue();
                
                x.setOrderScheduleId(rs.getInt(1));
                x.setRecordStatusCd(rs.getString(2));
                x.setOrderGuideId(rs.getInt(3));
                x.setUserId(rs.getInt(4));
                x.setBusEntityId(rs.getInt(5));
                x.setOrderScheduleCd(rs.getString(6));
                x.setOrderScheduleRuleCd(rs.getString(7));
                x.setCycle(rs.getInt(8));
                x.setEffDate(rs.getDate(9));
                x.setExpDate(rs.getDate(10));
                x.setCcEmail(rs.getString(11));
                x.setAddDate(rs.getTimestamp(12));
                x.setAddBy(rs.getString(13));
                x.setModDate(rs.getTimestamp(14));
                x.setModBy(rs.getString(15));
                x.setWorkOrderId(rs.getInt(16));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderScheduleDataVector object of all
     * OrderScheduleData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderScheduleDataVector()
     * @throws            SQLException
     */
    public static OrderScheduleDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_SCHEDULE_ID,RECORD_STATUS_CD,ORDER_GUIDE_ID,USER_ID,BUS_ENTITY_ID,ORDER_SCHEDULE_CD,ORDER_SCHEDULE_RULE_CD,CYCLE,EFF_DATE,EXP_DATE,CC_EMAIL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,WORK_ORDER_ID FROM CLW_ORDER_SCHEDULE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderScheduleDataVector v = new OrderScheduleDataVector();
        OrderScheduleData x = null;
        while (rs.next()) {
            // build the object
            x = OrderScheduleData.createValue();
            
            x.setOrderScheduleId(rs.getInt(1));
            x.setRecordStatusCd(rs.getString(2));
            x.setOrderGuideId(rs.getInt(3));
            x.setUserId(rs.getInt(4));
            x.setBusEntityId(rs.getInt(5));
            x.setOrderScheduleCd(rs.getString(6));
            x.setOrderScheduleRuleCd(rs.getString(7));
            x.setCycle(rs.getInt(8));
            x.setEffDate(rs.getDate(9));
            x.setExpDate(rs.getDate(10));
            x.setCcEmail(rs.getString(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddBy(rs.getString(13));
            x.setModDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));
            x.setWorkOrderId(rs.getInt(16));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderScheduleData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_SCHEDULE_ID FROM CLW_ORDER_SCHEDULE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_SCHEDULE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_SCHEDULE");
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
     * Inserts a OrderScheduleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderScheduleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderScheduleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderScheduleData insert(Connection pCon, OrderScheduleData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_SCHEDULE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_SCHEDULE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderScheduleId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_SCHEDULE (ORDER_SCHEDULE_ID,RECORD_STATUS_CD,ORDER_GUIDE_ID,USER_ID,BUS_ENTITY_ID,ORDER_SCHEDULE_CD,ORDER_SCHEDULE_RULE_CD,CYCLE,EFF_DATE,EXP_DATE,CC_EMAIL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,WORK_ORDER_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderScheduleId());
        pstmt.setString(2,pData.getRecordStatusCd());
        if (pData.getOrderGuideId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getOrderGuideId());
        }

        pstmt.setInt(4,pData.getUserId());
        pstmt.setInt(5,pData.getBusEntityId());
        pstmt.setString(6,pData.getOrderScheduleCd());
        pstmt.setString(7,pData.getOrderScheduleRuleCd());
        pstmt.setInt(8,pData.getCycle());
        pstmt.setDate(9,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(10,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(11,pData.getCcEmail());
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(13,pData.getAddBy());
        pstmt.setTimestamp(14,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(15,pData.getModBy());
        pstmt.setInt(16,pData.getWorkOrderId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_SCHEDULE_ID="+pData.getOrderScheduleId());
            log.debug("SQL:   RECORD_STATUS_CD="+pData.getRecordStatusCd());
            log.debug("SQL:   ORDER_GUIDE_ID="+pData.getOrderGuideId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ORDER_SCHEDULE_CD="+pData.getOrderScheduleCd());
            log.debug("SQL:   ORDER_SCHEDULE_RULE_CD="+pData.getOrderScheduleRuleCd());
            log.debug("SQL:   CYCLE="+pData.getCycle());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   CC_EMAIL="+pData.getCcEmail());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setOrderScheduleId(0);
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
     * Updates a OrderScheduleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderScheduleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderScheduleData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_SCHEDULE SET RECORD_STATUS_CD = ?,ORDER_GUIDE_ID = ?,USER_ID = ?,BUS_ENTITY_ID = ?,ORDER_SCHEDULE_CD = ?,ORDER_SCHEDULE_RULE_CD = ?,CYCLE = ?,EFF_DATE = ?,EXP_DATE = ?,CC_EMAIL = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,WORK_ORDER_ID = ? WHERE ORDER_SCHEDULE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getRecordStatusCd());
        if (pData.getOrderGuideId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getOrderGuideId());
        }

        pstmt.setInt(i++,pData.getUserId());
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setString(i++,pData.getOrderScheduleCd());
        pstmt.setString(i++,pData.getOrderScheduleRuleCd());
        pstmt.setInt(i++,pData.getCycle());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(i++,pData.getCcEmail());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getWorkOrderId());
        pstmt.setInt(i++,pData.getOrderScheduleId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   RECORD_STATUS_CD="+pData.getRecordStatusCd());
            log.debug("SQL:   ORDER_GUIDE_ID="+pData.getOrderGuideId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ORDER_SCHEDULE_CD="+pData.getOrderScheduleCd());
            log.debug("SQL:   ORDER_SCHEDULE_RULE_CD="+pData.getOrderScheduleRuleCd());
            log.debug("SQL:   CYCLE="+pData.getCycle());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   CC_EMAIL="+pData.getCcEmail());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a OrderScheduleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderScheduleId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderScheduleId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_SCHEDULE WHERE ORDER_SCHEDULE_ID = " + pOrderScheduleId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderScheduleData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_SCHEDULE");
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
     * Inserts a OrderScheduleData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderScheduleData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderScheduleData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_SCHEDULE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_SCHEDULE_ID,RECORD_STATUS_CD,ORDER_GUIDE_ID,USER_ID,BUS_ENTITY_ID,ORDER_SCHEDULE_CD,ORDER_SCHEDULE_RULE_CD,CYCLE,EFF_DATE,EXP_DATE,CC_EMAIL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,WORK_ORDER_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderScheduleId());
        pstmt.setString(2+4,pData.getRecordStatusCd());
        if (pData.getOrderGuideId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getOrderGuideId());
        }

        pstmt.setInt(4+4,pData.getUserId());
        pstmt.setInt(5+4,pData.getBusEntityId());
        pstmt.setString(6+4,pData.getOrderScheduleCd());
        pstmt.setString(7+4,pData.getOrderScheduleRuleCd());
        pstmt.setInt(8+4,pData.getCycle());
        pstmt.setDate(9+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(10+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(11+4,pData.getCcEmail());
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(13+4,pData.getAddBy());
        pstmt.setTimestamp(14+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(15+4,pData.getModBy());
        pstmt.setInt(16+4,pData.getWorkOrderId());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderScheduleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderScheduleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderScheduleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderScheduleData insert(Connection pCon, OrderScheduleData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderScheduleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderScheduleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderScheduleData pData, boolean pLogFl)
        throws SQLException {
        OrderScheduleData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderScheduleId();
          try {
          oldData = OrderScheduleDataAccess.select(pCon,id);
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
     * Deletes a OrderScheduleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderScheduleId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderScheduleId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_SCHEDULE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_SCHEDULE d WHERE ORDER_SCHEDULE_ID = " + pOrderScheduleId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderScheduleId);
        return n;
     }

    /**
     * Deletes OrderScheduleData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_SCHEDULE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_SCHEDULE d ");
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

