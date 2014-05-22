
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        SiteDeliveryDataAccess
 * Description:  This class is used to build access methods to the CLW_SITE_DELIVERY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.SiteDeliveryData;
import com.cleanwise.service.api.value.SiteDeliveryDataVector;
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
 * <code>SiteDeliveryDataAccess</code>
 */
public class SiteDeliveryDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(SiteDeliveryDataAccess.class.getName());

    /** <code>CLW_SITE_DELIVERY</code> table name */
	/* Primary key: SITE_DELIVERY_ID */
	
    public static final String CLW_SITE_DELIVERY = "CLW_SITE_DELIVERY";
    
    /** <code>SITE_DELIVERY_ID</code> SITE_DELIVERY_ID column of table CLW_SITE_DELIVERY */
    public static final String SITE_DELIVERY_ID = "SITE_DELIVERY_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_SITE_DELIVERY */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>CUST_MAJ</code> CUST_MAJ column of table CLW_SITE_DELIVERY */
    public static final String CUST_MAJ = "CUST_MAJ";
    /** <code>SITE_REF_NUM</code> SITE_REF_NUM column of table CLW_SITE_DELIVERY */
    public static final String SITE_REF_NUM = "SITE_REF_NUM";
    /** <code>YEAR</code> YEAR column of table CLW_SITE_DELIVERY */
    public static final String YEAR = "YEAR";
    /** <code>WEEK</code> WEEK column of table CLW_SITE_DELIVERY */
    public static final String WEEK = "WEEK";
    /** <code>DELIVERY_DAY</code> DELIVERY_DAY column of table CLW_SITE_DELIVERY */
    public static final String DELIVERY_DAY = "DELIVERY_DAY";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_SITE_DELIVERY */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>DELIVERY_FLAG</code> DELIVERY_FLAG column of table CLW_SITE_DELIVERY */
    public static final String DELIVERY_FLAG = "DELIVERY_FLAG";
    /** <code>CUTOFF_DAY</code> CUTOFF_DAY column of table CLW_SITE_DELIVERY */
    public static final String CUTOFF_DAY = "CUTOFF_DAY";
    /** <code>CUTOFF_SYSTEM_TIME</code> CUTOFF_SYSTEM_TIME column of table CLW_SITE_DELIVERY */
    public static final String CUTOFF_SYSTEM_TIME = "CUTOFF_SYSTEM_TIME";
    /** <code>CUTOFF_SITE_TIME</code> CUTOFF_SITE_TIME column of table CLW_SITE_DELIVERY */
    public static final String CUTOFF_SITE_TIME = "CUTOFF_SITE_TIME";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_SITE_DELIVERY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_SITE_DELIVERY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_SITE_DELIVERY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_SITE_DELIVERY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>DELIVERY_DATE</code> DELIVERY_DATE column of table CLW_SITE_DELIVERY */
    public static final String DELIVERY_DATE = "DELIVERY_DATE";
    /** <code>CUTOFF_SYSTEM_TIME_SOURCE</code> CUTOFF_SYSTEM_TIME_SOURCE column of table CLW_SITE_DELIVERY */
    public static final String CUTOFF_SYSTEM_TIME_SOURCE = "CUTOFF_SYSTEM_TIME_SOURCE";
    /** <code>CUTOFF_SITE_TIME_SOURCE</code> CUTOFF_SITE_TIME_SOURCE column of table CLW_SITE_DELIVERY */
    public static final String CUTOFF_SITE_TIME_SOURCE = "CUTOFF_SITE_TIME_SOURCE";

    /**
     * Constructor.
     */
    public SiteDeliveryDataAccess()
    {
    }

    /**
     * Gets a SiteDeliveryData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pSiteDeliveryId The key requested.
     * @return new SiteDeliveryData()
     * @throws            SQLException
     */
    public static SiteDeliveryData select(Connection pCon, int pSiteDeliveryId)
        throws SQLException, DataNotFoundException {
        SiteDeliveryData x=null;
        String sql="SELECT SITE_DELIVERY_ID,BUS_ENTITY_ID,CUST_MAJ,SITE_REF_NUM,YEAR,WEEK,DELIVERY_DAY,STATUS_CD,DELIVERY_FLAG,CUTOFF_DAY,CUTOFF_SYSTEM_TIME,CUTOFF_SITE_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DELIVERY_DATE,CUTOFF_SYSTEM_TIME_SOURCE,CUTOFF_SITE_TIME_SOURCE FROM CLW_SITE_DELIVERY WHERE SITE_DELIVERY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pSiteDeliveryId=" + pSiteDeliveryId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pSiteDeliveryId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=SiteDeliveryData.createValue();
            
            x.setSiteDeliveryId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setCustMaj(rs.getString(3));
            x.setSiteRefNum(rs.getString(4));
            x.setYear(rs.getInt(5));
            x.setWeek(rs.getInt(6));
            x.setDeliveryDay(rs.getInt(7));
            x.setStatusCd(rs.getString(8));
            x.setDeliveryFlag(rs.getBoolean(9));
            x.setCutoffDay(rs.getInt(10));
            x.setCutoffSystemTime(rs.getTimestamp(11));
            x.setCutoffSiteTime(rs.getTimestamp(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setDeliveryDate(rs.getDate(17));
            x.setCutoffSystemTimeSource(rs.getString(18));
            x.setCutoffSiteTimeSource(rs.getString(19));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("SITE_DELIVERY_ID :" + pSiteDeliveryId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a SiteDeliveryDataVector object that consists
     * of SiteDeliveryData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new SiteDeliveryDataVector()
     * @throws            SQLException
     */
    public static SiteDeliveryDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a SiteDeliveryData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_SITE_DELIVERY.SITE_DELIVERY_ID,CLW_SITE_DELIVERY.BUS_ENTITY_ID,CLW_SITE_DELIVERY.CUST_MAJ,CLW_SITE_DELIVERY.SITE_REF_NUM,CLW_SITE_DELIVERY.YEAR,CLW_SITE_DELIVERY.WEEK,CLW_SITE_DELIVERY.DELIVERY_DAY,CLW_SITE_DELIVERY.STATUS_CD,CLW_SITE_DELIVERY.DELIVERY_FLAG,CLW_SITE_DELIVERY.CUTOFF_DAY,CLW_SITE_DELIVERY.CUTOFF_SYSTEM_TIME,CLW_SITE_DELIVERY.CUTOFF_SITE_TIME,CLW_SITE_DELIVERY.ADD_DATE,CLW_SITE_DELIVERY.ADD_BY,CLW_SITE_DELIVERY.MOD_DATE,CLW_SITE_DELIVERY.MOD_BY,CLW_SITE_DELIVERY.DELIVERY_DATE,CLW_SITE_DELIVERY.CUTOFF_SYSTEM_TIME_SOURCE,CLW_SITE_DELIVERY.CUTOFF_SITE_TIME_SOURCE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated SiteDeliveryData Object.
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
    *@returns a populated SiteDeliveryData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         SiteDeliveryData x = SiteDeliveryData.createValue();
         
         x.setSiteDeliveryId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setCustMaj(rs.getString(3+offset));
         x.setSiteRefNum(rs.getString(4+offset));
         x.setYear(rs.getInt(5+offset));
         x.setWeek(rs.getInt(6+offset));
         x.setDeliveryDay(rs.getInt(7+offset));
         x.setStatusCd(rs.getString(8+offset));
         x.setDeliveryFlag(rs.getBoolean(9+offset));
         x.setCutoffDay(rs.getInt(10+offset));
         x.setCutoffSystemTime(rs.getTimestamp(11+offset));
         x.setCutoffSiteTime(rs.getTimestamp(12+offset));
         x.setAddDate(rs.getTimestamp(13+offset));
         x.setAddBy(rs.getString(14+offset));
         x.setModDate(rs.getTimestamp(15+offset));
         x.setModBy(rs.getString(16+offset));
         x.setDeliveryDate(rs.getDate(17+offset));
         x.setCutoffSystemTimeSource(rs.getString(18+offset));
         x.setCutoffSiteTimeSource(rs.getString(19+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the SiteDeliveryData Object represents.
    */
    public int getColumnCount(){
        return 19;
    }

    /**
     * Gets a SiteDeliveryDataVector object that consists
     * of SiteDeliveryData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new SiteDeliveryDataVector()
     * @throws            SQLException
     */
    public static SiteDeliveryDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT SITE_DELIVERY_ID,BUS_ENTITY_ID,CUST_MAJ,SITE_REF_NUM,YEAR,WEEK,DELIVERY_DAY,STATUS_CD,DELIVERY_FLAG,CUTOFF_DAY,CUTOFF_SYSTEM_TIME,CUTOFF_SITE_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DELIVERY_DATE,CUTOFF_SYSTEM_TIME_SOURCE,CUTOFF_SITE_TIME_SOURCE FROM CLW_SITE_DELIVERY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_SITE_DELIVERY.SITE_DELIVERY_ID,CLW_SITE_DELIVERY.BUS_ENTITY_ID,CLW_SITE_DELIVERY.CUST_MAJ,CLW_SITE_DELIVERY.SITE_REF_NUM,CLW_SITE_DELIVERY.YEAR,CLW_SITE_DELIVERY.WEEK,CLW_SITE_DELIVERY.DELIVERY_DAY,CLW_SITE_DELIVERY.STATUS_CD,CLW_SITE_DELIVERY.DELIVERY_FLAG,CLW_SITE_DELIVERY.CUTOFF_DAY,CLW_SITE_DELIVERY.CUTOFF_SYSTEM_TIME,CLW_SITE_DELIVERY.CUTOFF_SITE_TIME,CLW_SITE_DELIVERY.ADD_DATE,CLW_SITE_DELIVERY.ADD_BY,CLW_SITE_DELIVERY.MOD_DATE,CLW_SITE_DELIVERY.MOD_BY,CLW_SITE_DELIVERY.DELIVERY_DATE,CLW_SITE_DELIVERY.CUTOFF_SYSTEM_TIME_SOURCE,CLW_SITE_DELIVERY.CUTOFF_SITE_TIME_SOURCE FROM CLW_SITE_DELIVERY");
                where = pCriteria.getSqlClause("CLW_SITE_DELIVERY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SITE_DELIVERY.equals(otherTable)){
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
        SiteDeliveryDataVector v = new SiteDeliveryDataVector();
        while (rs.next()) {
            SiteDeliveryData x = SiteDeliveryData.createValue();
            
            x.setSiteDeliveryId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setCustMaj(rs.getString(3));
            x.setSiteRefNum(rs.getString(4));
            x.setYear(rs.getInt(5));
            x.setWeek(rs.getInt(6));
            x.setDeliveryDay(rs.getInt(7));
            x.setStatusCd(rs.getString(8));
            x.setDeliveryFlag(rs.getBoolean(9));
            x.setCutoffDay(rs.getInt(10));
            x.setCutoffSystemTime(rs.getTimestamp(11));
            x.setCutoffSiteTime(rs.getTimestamp(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setDeliveryDate(rs.getDate(17));
            x.setCutoffSystemTimeSource(rs.getString(18));
            x.setCutoffSiteTimeSource(rs.getString(19));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a SiteDeliveryDataVector object that consists
     * of SiteDeliveryData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for SiteDeliveryData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new SiteDeliveryDataVector()
     * @throws            SQLException
     */
    public static SiteDeliveryDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        SiteDeliveryDataVector v = new SiteDeliveryDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT SITE_DELIVERY_ID,BUS_ENTITY_ID,CUST_MAJ,SITE_REF_NUM,YEAR,WEEK,DELIVERY_DAY,STATUS_CD,DELIVERY_FLAG,CUTOFF_DAY,CUTOFF_SYSTEM_TIME,CUTOFF_SITE_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DELIVERY_DATE,CUTOFF_SYSTEM_TIME_SOURCE,CUTOFF_SITE_TIME_SOURCE FROM CLW_SITE_DELIVERY WHERE SITE_DELIVERY_ID IN (");

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
            SiteDeliveryData x=null;
            while (rs.next()) {
                // build the object
                x=SiteDeliveryData.createValue();
                
                x.setSiteDeliveryId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setCustMaj(rs.getString(3));
                x.setSiteRefNum(rs.getString(4));
                x.setYear(rs.getInt(5));
                x.setWeek(rs.getInt(6));
                x.setDeliveryDay(rs.getInt(7));
                x.setStatusCd(rs.getString(8));
                x.setDeliveryFlag(rs.getBoolean(9));
                x.setCutoffDay(rs.getInt(10));
                x.setCutoffSystemTime(rs.getTimestamp(11));
                x.setCutoffSiteTime(rs.getTimestamp(12));
                x.setAddDate(rs.getTimestamp(13));
                x.setAddBy(rs.getString(14));
                x.setModDate(rs.getTimestamp(15));
                x.setModBy(rs.getString(16));
                x.setDeliveryDate(rs.getDate(17));
                x.setCutoffSystemTimeSource(rs.getString(18));
                x.setCutoffSiteTimeSource(rs.getString(19));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a SiteDeliveryDataVector object of all
     * SiteDeliveryData objects in the database.
     * @param pCon An open database connection.
     * @return new SiteDeliveryDataVector()
     * @throws            SQLException
     */
    public static SiteDeliveryDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT SITE_DELIVERY_ID,BUS_ENTITY_ID,CUST_MAJ,SITE_REF_NUM,YEAR,WEEK,DELIVERY_DAY,STATUS_CD,DELIVERY_FLAG,CUTOFF_DAY,CUTOFF_SYSTEM_TIME,CUTOFF_SITE_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DELIVERY_DATE,CUTOFF_SYSTEM_TIME_SOURCE,CUTOFF_SITE_TIME_SOURCE FROM CLW_SITE_DELIVERY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        SiteDeliveryDataVector v = new SiteDeliveryDataVector();
        SiteDeliveryData x = null;
        while (rs.next()) {
            // build the object
            x = SiteDeliveryData.createValue();
            
            x.setSiteDeliveryId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setCustMaj(rs.getString(3));
            x.setSiteRefNum(rs.getString(4));
            x.setYear(rs.getInt(5));
            x.setWeek(rs.getInt(6));
            x.setDeliveryDay(rs.getInt(7));
            x.setStatusCd(rs.getString(8));
            x.setDeliveryFlag(rs.getBoolean(9));
            x.setCutoffDay(rs.getInt(10));
            x.setCutoffSystemTime(rs.getTimestamp(11));
            x.setCutoffSiteTime(rs.getTimestamp(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setDeliveryDate(rs.getDate(17));
            x.setCutoffSystemTimeSource(rs.getString(18));
            x.setCutoffSiteTimeSource(rs.getString(19));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * SiteDeliveryData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT SITE_DELIVERY_ID FROM CLW_SITE_DELIVERY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SITE_DELIVERY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SITE_DELIVERY");
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
     * Inserts a SiteDeliveryData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A SiteDeliveryData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new SiteDeliveryData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static SiteDeliveryData insert(Connection pCon, SiteDeliveryData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_SITE_DELIVERY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_SITE_DELIVERY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setSiteDeliveryId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_SITE_DELIVERY (SITE_DELIVERY_ID,BUS_ENTITY_ID,CUST_MAJ,SITE_REF_NUM,YEAR,WEEK,DELIVERY_DAY,STATUS_CD,DELIVERY_FLAG,CUTOFF_DAY,CUTOFF_SYSTEM_TIME,CUTOFF_SITE_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DELIVERY_DATE,CUTOFF_SYSTEM_TIME_SOURCE,CUTOFF_SITE_TIME_SOURCE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getSiteDeliveryId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getBusEntityId());
        }

        pstmt.setString(3,pData.getCustMaj());
        pstmt.setString(4,pData.getSiteRefNum());
        pstmt.setInt(5,pData.getYear());
        pstmt.setInt(6,pData.getWeek());
        pstmt.setInt(7,pData.getDeliveryDay());
        pstmt.setString(8,pData.getStatusCd());
        pstmt.setInt(9, pData.getDeliveryFlag()?1:0);
        pstmt.setInt(10,pData.getCutoffDay());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getCutoffSystemTime()));
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getCutoffSiteTime()));
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14,pData.getAddBy());
        pstmt.setTimestamp(15,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16,pData.getModBy());
        pstmt.setDate(17,DBAccess.toSQLDate(pData.getDeliveryDate()));
        pstmt.setString(18,pData.getCutoffSystemTimeSource());
        pstmt.setString(19,pData.getCutoffSiteTimeSource());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SITE_DELIVERY_ID="+pData.getSiteDeliveryId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   CUST_MAJ="+pData.getCustMaj());
            log.debug("SQL:   SITE_REF_NUM="+pData.getSiteRefNum());
            log.debug("SQL:   YEAR="+pData.getYear());
            log.debug("SQL:   WEEK="+pData.getWeek());
            log.debug("SQL:   DELIVERY_DAY="+pData.getDeliveryDay());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   DELIVERY_FLAG="+pData.getDeliveryFlag());
            log.debug("SQL:   CUTOFF_DAY="+pData.getCutoffDay());
            log.debug("SQL:   CUTOFF_SYSTEM_TIME="+pData.getCutoffSystemTime());
            log.debug("SQL:   CUTOFF_SITE_TIME="+pData.getCutoffSiteTime());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   DELIVERY_DATE="+pData.getDeliveryDate());
            log.debug("SQL:   CUTOFF_SYSTEM_TIME_SOURCE="+pData.getCutoffSystemTimeSource());
            log.debug("SQL:   CUTOFF_SITE_TIME_SOURCE="+pData.getCutoffSiteTimeSource());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setSiteDeliveryId(0);
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
     * Updates a SiteDeliveryData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A SiteDeliveryData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, SiteDeliveryData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_SITE_DELIVERY SET BUS_ENTITY_ID = ?,CUST_MAJ = ?,SITE_REF_NUM = ?,YEAR = ?,WEEK = ?,DELIVERY_DAY = ?,STATUS_CD = ?,DELIVERY_FLAG = ?,CUTOFF_DAY = ?,CUTOFF_SYSTEM_TIME = ?,CUTOFF_SITE_TIME = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,DELIVERY_DATE = ?,CUTOFF_SYSTEM_TIME_SOURCE = ?,CUTOFF_SITE_TIME_SOURCE = ? WHERE SITE_DELIVERY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getCustMaj());
        pstmt.setString(i++,pData.getSiteRefNum());
        pstmt.setInt(i++,pData.getYear());
        pstmt.setInt(i++,pData.getWeek());
        pstmt.setInt(i++,pData.getDeliveryDay());
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setInt(i++, pData.getDeliveryFlag()?1:0);
        pstmt.setInt(i++,pData.getCutoffDay());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getCutoffSystemTime()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getCutoffSiteTime()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getDeliveryDate()));
        pstmt.setString(i++,pData.getCutoffSystemTimeSource());
        pstmt.setString(i++,pData.getCutoffSiteTimeSource());
        pstmt.setInt(i++,pData.getSiteDeliveryId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   CUST_MAJ="+pData.getCustMaj());
            log.debug("SQL:   SITE_REF_NUM="+pData.getSiteRefNum());
            log.debug("SQL:   YEAR="+pData.getYear());
            log.debug("SQL:   WEEK="+pData.getWeek());
            log.debug("SQL:   DELIVERY_DAY="+pData.getDeliveryDay());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   DELIVERY_FLAG="+pData.getDeliveryFlag());
            log.debug("SQL:   CUTOFF_DAY="+pData.getCutoffDay());
            log.debug("SQL:   CUTOFF_SYSTEM_TIME="+pData.getCutoffSystemTime());
            log.debug("SQL:   CUTOFF_SITE_TIME="+pData.getCutoffSiteTime());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   DELIVERY_DATE="+pData.getDeliveryDate());
            log.debug("SQL:   CUTOFF_SYSTEM_TIME_SOURCE="+pData.getCutoffSystemTimeSource());
            log.debug("SQL:   CUTOFF_SITE_TIME_SOURCE="+pData.getCutoffSiteTimeSource());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a SiteDeliveryData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pSiteDeliveryId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pSiteDeliveryId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_SITE_DELIVERY WHERE SITE_DELIVERY_ID = " + pSiteDeliveryId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes SiteDeliveryData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_SITE_DELIVERY");
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
     * Inserts a SiteDeliveryData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A SiteDeliveryData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, SiteDeliveryData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_SITE_DELIVERY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "SITE_DELIVERY_ID,BUS_ENTITY_ID,CUST_MAJ,SITE_REF_NUM,YEAR,WEEK,DELIVERY_DAY,STATUS_CD,DELIVERY_FLAG,CUTOFF_DAY,CUTOFF_SYSTEM_TIME,CUTOFF_SITE_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DELIVERY_DATE,CUTOFF_SYSTEM_TIME_SOURCE,CUTOFF_SITE_TIME_SOURCE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getSiteDeliveryId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getBusEntityId());
        }

        pstmt.setString(3+4,pData.getCustMaj());
        pstmt.setString(4+4,pData.getSiteRefNum());
        pstmt.setInt(5+4,pData.getYear());
        pstmt.setInt(6+4,pData.getWeek());
        pstmt.setInt(7+4,pData.getDeliveryDay());
        pstmt.setString(8+4,pData.getStatusCd());
        pstmt.setBoolean(9+4,pData.getDeliveryFlag());
        pstmt.setInt(10+4,pData.getCutoffDay());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getCutoffSystemTime()));
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getCutoffSiteTime()));
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14+4,pData.getAddBy());
        pstmt.setTimestamp(15+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16+4,pData.getModBy());
        pstmt.setDate(17+4,DBAccess.toSQLDate(pData.getDeliveryDate()));
        pstmt.setString(18+4,pData.getCutoffSystemTimeSource());
        pstmt.setString(19+4,pData.getCutoffSiteTimeSource());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a SiteDeliveryData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A SiteDeliveryData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new SiteDeliveryData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static SiteDeliveryData insert(Connection pCon, SiteDeliveryData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a SiteDeliveryData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A SiteDeliveryData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, SiteDeliveryData pData, boolean pLogFl)
        throws SQLException {
        SiteDeliveryData oldData = null;
        if(pLogFl) {
          int id = pData.getSiteDeliveryId();
          try {
          oldData = SiteDeliveryDataAccess.select(pCon,id);
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
     * Deletes a SiteDeliveryData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pSiteDeliveryId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pSiteDeliveryId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_SITE_DELIVERY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SITE_DELIVERY d WHERE SITE_DELIVERY_ID = " + pSiteDeliveryId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pSiteDeliveryId);
        return n;
     }

    /**
     * Deletes SiteDeliveryData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_SITE_DELIVERY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SITE_DELIVERY d ");
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

