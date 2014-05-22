
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ScheduleDataAccess
 * Description:  This class is used to build access methods to the CLW_SCHEDULE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ScheduleData;
import com.cleanwise.service.api.value.ScheduleDataVector;
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
 * <code>ScheduleDataAccess</code>
 */
public class ScheduleDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ScheduleDataAccess.class.getName());

    /** <code>CLW_SCHEDULE</code> table name */
	/* Primary key: SCHEDULE_ID */
	
    public static final String CLW_SCHEDULE = "CLW_SCHEDULE";
    
    /** <code>SCHEDULE_ID</code> SCHEDULE_ID column of table CLW_SCHEDULE */
    public static final String SCHEDULE_ID = "SCHEDULE_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_SCHEDULE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_SCHEDULE */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>SCHEDULE_STATUS_CD</code> SCHEDULE_STATUS_CD column of table CLW_SCHEDULE */
    public static final String SCHEDULE_STATUS_CD = "SCHEDULE_STATUS_CD";
    /** <code>SCHEDULE_TYPE_CD</code> SCHEDULE_TYPE_CD column of table CLW_SCHEDULE */
    public static final String SCHEDULE_TYPE_CD = "SCHEDULE_TYPE_CD";
    /** <code>SCHEDULE_RULE_CD</code> SCHEDULE_RULE_CD column of table CLW_SCHEDULE */
    public static final String SCHEDULE_RULE_CD = "SCHEDULE_RULE_CD";
    /** <code>CYCLE</code> CYCLE column of table CLW_SCHEDULE */
    public static final String CYCLE = "CYCLE";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_SCHEDULE */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_SCHEDULE */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>LAST_PROCESSED_DT</code> LAST_PROCESSED_DT column of table CLW_SCHEDULE */
    public static final String LAST_PROCESSED_DT = "LAST_PROCESSED_DT";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_SCHEDULE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_SCHEDULE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_SCHEDULE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_SCHEDULE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ScheduleDataAccess()
    {
    }

    /**
     * Gets a ScheduleData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pScheduleId The key requested.
     * @return new ScheduleData()
     * @throws            SQLException
     */
    public static ScheduleData select(Connection pCon, int pScheduleId)
        throws SQLException, DataNotFoundException {
        ScheduleData x=null;
        String sql="SELECT SCHEDULE_ID,SHORT_DESC,BUS_ENTITY_ID,SCHEDULE_STATUS_CD,SCHEDULE_TYPE_CD,SCHEDULE_RULE_CD,CYCLE,EFF_DATE,EXP_DATE,LAST_PROCESSED_DT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_SCHEDULE WHERE SCHEDULE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pScheduleId=" + pScheduleId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pScheduleId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ScheduleData.createValue();
            
            x.setScheduleId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setBusEntityId(rs.getInt(3));
            x.setScheduleStatusCd(rs.getString(4));
            x.setScheduleTypeCd(rs.getString(5));
            x.setScheduleRuleCd(rs.getString(6));
            x.setCycle(rs.getInt(7));
            x.setEffDate(rs.getDate(8));
            x.setExpDate(rs.getDate(9));
            x.setLastProcessedDt(rs.getTimestamp(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("SCHEDULE_ID :" + pScheduleId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ScheduleDataVector object that consists
     * of ScheduleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ScheduleDataVector()
     * @throws            SQLException
     */
    public static ScheduleDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ScheduleData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_SCHEDULE.SCHEDULE_ID,CLW_SCHEDULE.SHORT_DESC,CLW_SCHEDULE.BUS_ENTITY_ID,CLW_SCHEDULE.SCHEDULE_STATUS_CD,CLW_SCHEDULE.SCHEDULE_TYPE_CD,CLW_SCHEDULE.SCHEDULE_RULE_CD,CLW_SCHEDULE.CYCLE,CLW_SCHEDULE.EFF_DATE,CLW_SCHEDULE.EXP_DATE,CLW_SCHEDULE.LAST_PROCESSED_DT,CLW_SCHEDULE.ADD_DATE,CLW_SCHEDULE.ADD_BY,CLW_SCHEDULE.MOD_DATE,CLW_SCHEDULE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ScheduleData Object.
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
    *@returns a populated ScheduleData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ScheduleData x = ScheduleData.createValue();
         
         x.setScheduleId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setScheduleStatusCd(rs.getString(4+offset));
         x.setScheduleTypeCd(rs.getString(5+offset));
         x.setScheduleRuleCd(rs.getString(6+offset));
         x.setCycle(rs.getInt(7+offset));
         x.setEffDate(rs.getDate(8+offset));
         x.setExpDate(rs.getDate(9+offset));
         x.setLastProcessedDt(rs.getTimestamp(10+offset));
         x.setAddDate(rs.getTimestamp(11+offset));
         x.setAddBy(rs.getString(12+offset));
         x.setModDate(rs.getTimestamp(13+offset));
         x.setModBy(rs.getString(14+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ScheduleData Object represents.
    */
    public int getColumnCount(){
        return 14;
    }

    /**
     * Gets a ScheduleDataVector object that consists
     * of ScheduleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ScheduleDataVector()
     * @throws            SQLException
     */
    public static ScheduleDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT SCHEDULE_ID,SHORT_DESC,BUS_ENTITY_ID,SCHEDULE_STATUS_CD,SCHEDULE_TYPE_CD,SCHEDULE_RULE_CD,CYCLE,EFF_DATE,EXP_DATE,LAST_PROCESSED_DT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_SCHEDULE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_SCHEDULE.SCHEDULE_ID,CLW_SCHEDULE.SHORT_DESC,CLW_SCHEDULE.BUS_ENTITY_ID,CLW_SCHEDULE.SCHEDULE_STATUS_CD,CLW_SCHEDULE.SCHEDULE_TYPE_CD,CLW_SCHEDULE.SCHEDULE_RULE_CD,CLW_SCHEDULE.CYCLE,CLW_SCHEDULE.EFF_DATE,CLW_SCHEDULE.EXP_DATE,CLW_SCHEDULE.LAST_PROCESSED_DT,CLW_SCHEDULE.ADD_DATE,CLW_SCHEDULE.ADD_BY,CLW_SCHEDULE.MOD_DATE,CLW_SCHEDULE.MOD_BY FROM CLW_SCHEDULE");
                where = pCriteria.getSqlClause("CLW_SCHEDULE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SCHEDULE.equals(otherTable)){
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
        ScheduleDataVector v = new ScheduleDataVector();
        while (rs.next()) {
            ScheduleData x = ScheduleData.createValue();
            
            x.setScheduleId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setBusEntityId(rs.getInt(3));
            x.setScheduleStatusCd(rs.getString(4));
            x.setScheduleTypeCd(rs.getString(5));
            x.setScheduleRuleCd(rs.getString(6));
            x.setCycle(rs.getInt(7));
            x.setEffDate(rs.getDate(8));
            x.setExpDate(rs.getDate(9));
            x.setLastProcessedDt(rs.getTimestamp(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ScheduleDataVector object that consists
     * of ScheduleData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ScheduleData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ScheduleDataVector()
     * @throws            SQLException
     */
    public static ScheduleDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ScheduleDataVector v = new ScheduleDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT SCHEDULE_ID,SHORT_DESC,BUS_ENTITY_ID,SCHEDULE_STATUS_CD,SCHEDULE_TYPE_CD,SCHEDULE_RULE_CD,CYCLE,EFF_DATE,EXP_DATE,LAST_PROCESSED_DT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_SCHEDULE WHERE SCHEDULE_ID IN (");

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
            ScheduleData x=null;
            while (rs.next()) {
                // build the object
                x=ScheduleData.createValue();
                
                x.setScheduleId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setBusEntityId(rs.getInt(3));
                x.setScheduleStatusCd(rs.getString(4));
                x.setScheduleTypeCd(rs.getString(5));
                x.setScheduleRuleCd(rs.getString(6));
                x.setCycle(rs.getInt(7));
                x.setEffDate(rs.getDate(8));
                x.setExpDate(rs.getDate(9));
                x.setLastProcessedDt(rs.getTimestamp(10));
                x.setAddDate(rs.getTimestamp(11));
                x.setAddBy(rs.getString(12));
                x.setModDate(rs.getTimestamp(13));
                x.setModBy(rs.getString(14));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ScheduleDataVector object of all
     * ScheduleData objects in the database.
     * @param pCon An open database connection.
     * @return new ScheduleDataVector()
     * @throws            SQLException
     */
    public static ScheduleDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT SCHEDULE_ID,SHORT_DESC,BUS_ENTITY_ID,SCHEDULE_STATUS_CD,SCHEDULE_TYPE_CD,SCHEDULE_RULE_CD,CYCLE,EFF_DATE,EXP_DATE,LAST_PROCESSED_DT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_SCHEDULE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ScheduleDataVector v = new ScheduleDataVector();
        ScheduleData x = null;
        while (rs.next()) {
            // build the object
            x = ScheduleData.createValue();
            
            x.setScheduleId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setBusEntityId(rs.getInt(3));
            x.setScheduleStatusCd(rs.getString(4));
            x.setScheduleTypeCd(rs.getString(5));
            x.setScheduleRuleCd(rs.getString(6));
            x.setCycle(rs.getInt(7));
            x.setEffDate(rs.getDate(8));
            x.setExpDate(rs.getDate(9));
            x.setLastProcessedDt(rs.getTimestamp(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ScheduleData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT SCHEDULE_ID FROM CLW_SCHEDULE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT SCHEDULE_ID FROM CLW_SCHEDULE");
                where = pCriteria.getSqlClause("CLW_SCHEDULE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SCHEDULE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SCHEDULE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SCHEDULE");
                where = pCriteria.getSqlClause("CLW_SCHEDULE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SCHEDULE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SCHEDULE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SCHEDULE");
                where = pCriteria.getSqlClause("CLW_SCHEDULE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SCHEDULE.equals(otherTable)){
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
     * Inserts a ScheduleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ScheduleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ScheduleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ScheduleData insert(Connection pCon, ScheduleData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_SCHEDULE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_SCHEDULE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setScheduleId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_SCHEDULE (SCHEDULE_ID,SHORT_DESC,BUS_ENTITY_ID,SCHEDULE_STATUS_CD,SCHEDULE_TYPE_CD,SCHEDULE_RULE_CD,CYCLE,EFF_DATE,EXP_DATE,LAST_PROCESSED_DT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getScheduleId());
        pstmt.setString(2,pData.getShortDesc());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getBusEntityId());
        }

        pstmt.setString(4,pData.getScheduleStatusCd());
        pstmt.setString(5,pData.getScheduleTypeCd());
        pstmt.setString(6,pData.getScheduleRuleCd());
        pstmt.setInt(7,pData.getCycle());
        pstmt.setDate(8,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(9,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getLastProcessedDt()));
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12,pData.getAddBy());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SCHEDULE_ID="+pData.getScheduleId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   SCHEDULE_STATUS_CD="+pData.getScheduleStatusCd());
            log.debug("SQL:   SCHEDULE_TYPE_CD="+pData.getScheduleTypeCd());
            log.debug("SQL:   SCHEDULE_RULE_CD="+pData.getScheduleRuleCd());
            log.debug("SQL:   CYCLE="+pData.getCycle());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   LAST_PROCESSED_DT="+pData.getLastProcessedDt());
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
        pData.setScheduleId(0);
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
     * Updates a ScheduleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ScheduleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ScheduleData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_SCHEDULE SET SHORT_DESC = ?,BUS_ENTITY_ID = ?,SCHEDULE_STATUS_CD = ?,SCHEDULE_TYPE_CD = ?,SCHEDULE_RULE_CD = ?,CYCLE = ?,EFF_DATE = ?,EXP_DATE = ?,LAST_PROCESSED_DT = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE SCHEDULE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getScheduleStatusCd());
        pstmt.setString(i++,pData.getScheduleTypeCd());
        pstmt.setString(i++,pData.getScheduleRuleCd());
        pstmt.setInt(i++,pData.getCycle());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getLastProcessedDt()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getScheduleId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   SCHEDULE_STATUS_CD="+pData.getScheduleStatusCd());
            log.debug("SQL:   SCHEDULE_TYPE_CD="+pData.getScheduleTypeCd());
            log.debug("SQL:   SCHEDULE_RULE_CD="+pData.getScheduleRuleCd());
            log.debug("SQL:   CYCLE="+pData.getCycle());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   LAST_PROCESSED_DT="+pData.getLastProcessedDt());
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
     * Deletes a ScheduleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pScheduleId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pScheduleId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_SCHEDULE WHERE SCHEDULE_ID = " + pScheduleId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ScheduleData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_SCHEDULE");
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
     * Inserts a ScheduleData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ScheduleData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ScheduleData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_SCHEDULE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "SCHEDULE_ID,SHORT_DESC,BUS_ENTITY_ID,SCHEDULE_STATUS_CD,SCHEDULE_TYPE_CD,SCHEDULE_RULE_CD,CYCLE,EFF_DATE,EXP_DATE,LAST_PROCESSED_DT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getScheduleId());
        pstmt.setString(2+4,pData.getShortDesc());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getBusEntityId());
        }

        pstmt.setString(4+4,pData.getScheduleStatusCd());
        pstmt.setString(5+4,pData.getScheduleTypeCd());
        pstmt.setString(6+4,pData.getScheduleRuleCd());
        pstmt.setInt(7+4,pData.getCycle());
        pstmt.setDate(8+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(9+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getLastProcessedDt()));
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12+4,pData.getAddBy());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ScheduleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ScheduleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ScheduleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ScheduleData insert(Connection pCon, ScheduleData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ScheduleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ScheduleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ScheduleData pData, boolean pLogFl)
        throws SQLException {
        ScheduleData oldData = null;
        if(pLogFl) {
          int id = pData.getScheduleId();
          try {
          oldData = ScheduleDataAccess.select(pCon,id);
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
     * Deletes a ScheduleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pScheduleId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pScheduleId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_SCHEDULE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SCHEDULE d WHERE SCHEDULE_ID = " + pScheduleId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pScheduleId);
        return n;
     }

    /**
     * Deletes ScheduleData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_SCHEDULE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SCHEDULE d ");
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

