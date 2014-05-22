
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        HistoryDataAccess
 * Description:  This class is used to build access methods to the CLW_HISTORY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.HistoryData;
import com.cleanwise.service.api.value.HistoryDataVector;
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
 * <code>HistoryDataAccess</code>
 */
public class HistoryDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(HistoryDataAccess.class.getName());

    /** <code>CLW_HISTORY</code> table name */
	/* Primary key: HISTORY_ID */
	
    public static final String CLW_HISTORY = "CLW_HISTORY";
    
    /** <code>HISTORY_ID</code> HISTORY_ID column of table CLW_HISTORY */
    public static final String HISTORY_ID = "HISTORY_ID";
    /** <code>HISTORY_TYPE_CD</code> HISTORY_TYPE_CD column of table CLW_HISTORY */
    public static final String HISTORY_TYPE_CD = "HISTORY_TYPE_CD";
    /** <code>USER_ID</code> USER_ID column of table CLW_HISTORY */
    public static final String USER_ID = "USER_ID";
    /** <code>ACTIVITY_DATE</code> ACTIVITY_DATE column of table CLW_HISTORY */
    public static final String ACTIVITY_DATE = "ACTIVITY_DATE";
    /** <code>ATTRIBUTE01</code> ATTRIBUTE01 column of table CLW_HISTORY */
    public static final String ATTRIBUTE01 = "ATTRIBUTE01";
    /** <code>ATTRIBUTE02</code> ATTRIBUTE02 column of table CLW_HISTORY */
    public static final String ATTRIBUTE02 = "ATTRIBUTE02";
    /** <code>ATTRIBUTE03</code> ATTRIBUTE03 column of table CLW_HISTORY */
    public static final String ATTRIBUTE03 = "ATTRIBUTE03";
    /** <code>ATTRIBUTE04</code> ATTRIBUTE04 column of table CLW_HISTORY */
    public static final String ATTRIBUTE04 = "ATTRIBUTE04";
    /** <code>ATTRIBUTE05</code> ATTRIBUTE05 column of table CLW_HISTORY */
    public static final String ATTRIBUTE05 = "ATTRIBUTE05";
    /** <code>ATTRIBUTE06</code> ATTRIBUTE06 column of table CLW_HISTORY */
    public static final String ATTRIBUTE06 = "ATTRIBUTE06";
    /** <code>ATTRIBUTE07</code> ATTRIBUTE07 column of table CLW_HISTORY */
    public static final String ATTRIBUTE07 = "ATTRIBUTE07";
    /** <code>ATTRIBUTE08</code> ATTRIBUTE08 column of table CLW_HISTORY */
    public static final String ATTRIBUTE08 = "ATTRIBUTE08";
    /** <code>ATTRIBUTE09</code> ATTRIBUTE09 column of table CLW_HISTORY */
    public static final String ATTRIBUTE09 = "ATTRIBUTE09";
    /** <code>ATTRIBUTE10</code> ATTRIBUTE10 column of table CLW_HISTORY */
    public static final String ATTRIBUTE10 = "ATTRIBUTE10";
    /** <code>ATTRIBUTE11</code> ATTRIBUTE11 column of table CLW_HISTORY */
    public static final String ATTRIBUTE11 = "ATTRIBUTE11";
    /** <code>ATTRIBUTE12</code> ATTRIBUTE12 column of table CLW_HISTORY */
    public static final String ATTRIBUTE12 = "ATTRIBUTE12";
    /** <code>ATTRIBUTE13</code> ATTRIBUTE13 column of table CLW_HISTORY */
    public static final String ATTRIBUTE13 = "ATTRIBUTE13";
    /** <code>ATTRIBUTE14</code> ATTRIBUTE14 column of table CLW_HISTORY */
    public static final String ATTRIBUTE14 = "ATTRIBUTE14";
    /** <code>ATTRIBUTE15</code> ATTRIBUTE15 column of table CLW_HISTORY */
    public static final String ATTRIBUTE15 = "ATTRIBUTE15";
    /** <code>ATTRIBUTE16</code> ATTRIBUTE16 column of table CLW_HISTORY */
    public static final String ATTRIBUTE16 = "ATTRIBUTE16";
    /** <code>ATTRIBUTE17</code> ATTRIBUTE17 column of table CLW_HISTORY */
    public static final String ATTRIBUTE17 = "ATTRIBUTE17";
    /** <code>ATTRIBUTE18</code> ATTRIBUTE18 column of table CLW_HISTORY */
    public static final String ATTRIBUTE18 = "ATTRIBUTE18";
    /** <code>ATTRIBUTE19</code> ATTRIBUTE19 column of table CLW_HISTORY */
    public static final String ATTRIBUTE19 = "ATTRIBUTE19";
    /** <code>ATTRIBUTE20</code> ATTRIBUTE20 column of table CLW_HISTORY */
    public static final String ATTRIBUTE20 = "ATTRIBUTE20";
    /** <code>ATTRIBUTE21</code> ATTRIBUTE21 column of table CLW_HISTORY */
    public static final String ATTRIBUTE21 = "ATTRIBUTE21";
    /** <code>ATTRIBUTE22</code> ATTRIBUTE22 column of table CLW_HISTORY */
    public static final String ATTRIBUTE22 = "ATTRIBUTE22";
    /** <code>ATTRIBUTE23</code> ATTRIBUTE23 column of table CLW_HISTORY */
    public static final String ATTRIBUTE23 = "ATTRIBUTE23";
    /** <code>ATTRIBUTE24</code> ATTRIBUTE24 column of table CLW_HISTORY */
    public static final String ATTRIBUTE24 = "ATTRIBUTE24";
    /** <code>ATTRIBUTE25</code> ATTRIBUTE25 column of table CLW_HISTORY */
    public static final String ATTRIBUTE25 = "ATTRIBUTE25";
    /** <code>ATTRIBUTE26</code> ATTRIBUTE26 column of table CLW_HISTORY */
    public static final String ATTRIBUTE26 = "ATTRIBUTE26";
    /** <code>ATTRIBUTE27</code> ATTRIBUTE27 column of table CLW_HISTORY */
    public static final String ATTRIBUTE27 = "ATTRIBUTE27";
    /** <code>ATTRIBUTE28</code> ATTRIBUTE28 column of table CLW_HISTORY */
    public static final String ATTRIBUTE28 = "ATTRIBUTE28";
    /** <code>ATTRIBUTE29</code> ATTRIBUTE29 column of table CLW_HISTORY */
    public static final String ATTRIBUTE29 = "ATTRIBUTE29";
    /** <code>ATTRIBUTE30</code> ATTRIBUTE30 column of table CLW_HISTORY */
    public static final String ATTRIBUTE30 = "ATTRIBUTE30";
    /** <code>ATTRIBUTE31</code> ATTRIBUTE31 column of table CLW_HISTORY */
    public static final String ATTRIBUTE31 = "ATTRIBUTE31";
    /** <code>ATTRIBUTE32</code> ATTRIBUTE32 column of table CLW_HISTORY */
    public static final String ATTRIBUTE32 = "ATTRIBUTE32";
    /** <code>ATTRIBUTE33</code> ATTRIBUTE33 column of table CLW_HISTORY */
    public static final String ATTRIBUTE33 = "ATTRIBUTE33";
    /** <code>ATTRIBUTE34</code> ATTRIBUTE34 column of table CLW_HISTORY */
    public static final String ATTRIBUTE34 = "ATTRIBUTE34";
    /** <code>ATTRIBUTE35</code> ATTRIBUTE35 column of table CLW_HISTORY */
    public static final String ATTRIBUTE35 = "ATTRIBUTE35";
    /** <code>CLOB1</code> CLOB1 column of table CLW_HISTORY */
    public static final String CLOB1 = "CLOB1";
    /** <code>CLOB2</code> CLOB2 column of table CLW_HISTORY */
    public static final String CLOB2 = "CLOB2";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_HISTORY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_HISTORY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_HISTORY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_HISTORY */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public HistoryDataAccess()
    {
    }

    /**
     * Gets a HistoryData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pHistoryId The key requested.
     * @return new HistoryData()
     * @throws            SQLException
     */
    public static HistoryData select(Connection pCon, int pHistoryId)
        throws SQLException, DataNotFoundException {
        HistoryData x=null;
        String sql="SELECT HISTORY_ID,HISTORY_TYPE_CD,USER_ID,ACTIVITY_DATE,ATTRIBUTE01,ATTRIBUTE02,ATTRIBUTE03,ATTRIBUTE04,ATTRIBUTE05,ATTRIBUTE06,ATTRIBUTE07,ATTRIBUTE08,ATTRIBUTE09,ATTRIBUTE10,ATTRIBUTE11,ATTRIBUTE12,ATTRIBUTE13,ATTRIBUTE14,ATTRIBUTE15,ATTRIBUTE16,ATTRIBUTE17,ATTRIBUTE18,ATTRIBUTE19,ATTRIBUTE20,ATTRIBUTE21,ATTRIBUTE22,ATTRIBUTE23,ATTRIBUTE24,ATTRIBUTE25,ATTRIBUTE26,ATTRIBUTE27,ATTRIBUTE28,ATTRIBUTE29,ATTRIBUTE30,ATTRIBUTE31,ATTRIBUTE32,ATTRIBUTE33,ATTRIBUTE34,ATTRIBUTE35,CLOB1,CLOB2,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_HISTORY WHERE HISTORY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pHistoryId=" + pHistoryId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pHistoryId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=HistoryData.createValue();
            
            x.setHistoryId(rs.getInt(1));
            x.setHistoryTypeCd(rs.getString(2));
            x.setUserId(rs.getString(3));
            x.setActivityDate(rs.getDate(4));
            x.setAttribute01(rs.getString(5));
            x.setAttribute02(rs.getString(6));
            x.setAttribute03(rs.getString(7));
            x.setAttribute04(rs.getString(8));
            x.setAttribute05(rs.getString(9));
            x.setAttribute06(rs.getString(10));
            x.setAttribute07(rs.getString(11));
            x.setAttribute08(rs.getString(12));
            x.setAttribute09(rs.getString(13));
            x.setAttribute10(rs.getString(14));
            x.setAttribute11(rs.getString(15));
            x.setAttribute12(rs.getString(16));
            x.setAttribute13(rs.getString(17));
            x.setAttribute14(rs.getString(18));
            x.setAttribute15(rs.getString(19));
            x.setAttribute16(rs.getString(20));
            x.setAttribute17(rs.getString(21));
            x.setAttribute18(rs.getString(22));
            x.setAttribute19(rs.getString(23));
            x.setAttribute20(rs.getString(24));
            x.setAttribute21(rs.getString(25));
            x.setAttribute22(rs.getString(26));
            x.setAttribute23(rs.getString(27));
            x.setAttribute24(rs.getString(28));
            x.setAttribute25(rs.getString(29));
            x.setAttribute26(rs.getString(30));
            x.setAttribute27(rs.getString(31));
            x.setAttribute28(rs.getString(32));
            x.setAttribute29(rs.getString(33));
            x.setAttribute30(rs.getString(34));
            x.setAttribute31(rs.getString(35));
            x.setAttribute32(rs.getString(36));
            x.setAttribute33(rs.getString(37));
            x.setAttribute34(rs.getString(38));
            x.setAttribute35(rs.getString(39));
            x.setClob1(rs.getString(40));
            x.setClob2(rs.getString(41));
            x.setAddBy(rs.getString(42));
            x.setAddDate(rs.getTimestamp(43));
            x.setModBy(rs.getString(44));
            x.setModDate(rs.getTimestamp(45));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("HISTORY_ID :" + pHistoryId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a HistoryDataVector object that consists
     * of HistoryData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new HistoryDataVector()
     * @throws            SQLException
     */
    public static HistoryDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a HistoryData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_HISTORY.HISTORY_ID,CLW_HISTORY.HISTORY_TYPE_CD,CLW_HISTORY.USER_ID,CLW_HISTORY.ACTIVITY_DATE,CLW_HISTORY.ATTRIBUTE01,CLW_HISTORY.ATTRIBUTE02,CLW_HISTORY.ATTRIBUTE03,CLW_HISTORY.ATTRIBUTE04,CLW_HISTORY.ATTRIBUTE05,CLW_HISTORY.ATTRIBUTE06,CLW_HISTORY.ATTRIBUTE07,CLW_HISTORY.ATTRIBUTE08,CLW_HISTORY.ATTRIBUTE09,CLW_HISTORY.ATTRIBUTE10,CLW_HISTORY.ATTRIBUTE11,CLW_HISTORY.ATTRIBUTE12,CLW_HISTORY.ATTRIBUTE13,CLW_HISTORY.ATTRIBUTE14,CLW_HISTORY.ATTRIBUTE15,CLW_HISTORY.ATTRIBUTE16,CLW_HISTORY.ATTRIBUTE17,CLW_HISTORY.ATTRIBUTE18,CLW_HISTORY.ATTRIBUTE19,CLW_HISTORY.ATTRIBUTE20,CLW_HISTORY.ATTRIBUTE21,CLW_HISTORY.ATTRIBUTE22,CLW_HISTORY.ATTRIBUTE23,CLW_HISTORY.ATTRIBUTE24,CLW_HISTORY.ATTRIBUTE25,CLW_HISTORY.ATTRIBUTE26,CLW_HISTORY.ATTRIBUTE27,CLW_HISTORY.ATTRIBUTE28,CLW_HISTORY.ATTRIBUTE29,CLW_HISTORY.ATTRIBUTE30,CLW_HISTORY.ATTRIBUTE31,CLW_HISTORY.ATTRIBUTE32,CLW_HISTORY.ATTRIBUTE33,CLW_HISTORY.ATTRIBUTE34,CLW_HISTORY.ATTRIBUTE35,CLW_HISTORY.CLOB1,CLW_HISTORY.CLOB2,CLW_HISTORY.ADD_BY,CLW_HISTORY.ADD_DATE,CLW_HISTORY.MOD_BY,CLW_HISTORY.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated HistoryData Object.
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
    *@returns a populated HistoryData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         HistoryData x = HistoryData.createValue();
         
         x.setHistoryId(rs.getInt(1+offset));
         x.setHistoryTypeCd(rs.getString(2+offset));
         x.setUserId(rs.getString(3+offset));
         x.setActivityDate(rs.getDate(4+offset));
         x.setAttribute01(rs.getString(5+offset));
         x.setAttribute02(rs.getString(6+offset));
         x.setAttribute03(rs.getString(7+offset));
         x.setAttribute04(rs.getString(8+offset));
         x.setAttribute05(rs.getString(9+offset));
         x.setAttribute06(rs.getString(10+offset));
         x.setAttribute07(rs.getString(11+offset));
         x.setAttribute08(rs.getString(12+offset));
         x.setAttribute09(rs.getString(13+offset));
         x.setAttribute10(rs.getString(14+offset));
         x.setAttribute11(rs.getString(15+offset));
         x.setAttribute12(rs.getString(16+offset));
         x.setAttribute13(rs.getString(17+offset));
         x.setAttribute14(rs.getString(18+offset));
         x.setAttribute15(rs.getString(19+offset));
         x.setAttribute16(rs.getString(20+offset));
         x.setAttribute17(rs.getString(21+offset));
         x.setAttribute18(rs.getString(22+offset));
         x.setAttribute19(rs.getString(23+offset));
         x.setAttribute20(rs.getString(24+offset));
         x.setAttribute21(rs.getString(25+offset));
         x.setAttribute22(rs.getString(26+offset));
         x.setAttribute23(rs.getString(27+offset));
         x.setAttribute24(rs.getString(28+offset));
         x.setAttribute25(rs.getString(29+offset));
         x.setAttribute26(rs.getString(30+offset));
         x.setAttribute27(rs.getString(31+offset));
         x.setAttribute28(rs.getString(32+offset));
         x.setAttribute29(rs.getString(33+offset));
         x.setAttribute30(rs.getString(34+offset));
         x.setAttribute31(rs.getString(35+offset));
         x.setAttribute32(rs.getString(36+offset));
         x.setAttribute33(rs.getString(37+offset));
         x.setAttribute34(rs.getString(38+offset));
         x.setAttribute35(rs.getString(39+offset));
         x.setClob1(rs.getString(40+offset));
         x.setClob2(rs.getString(41+offset));
         x.setAddBy(rs.getString(42+offset));
         x.setAddDate(rs.getTimestamp(43+offset));
         x.setModBy(rs.getString(44+offset));
         x.setModDate(rs.getTimestamp(45+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the HistoryData Object represents.
    */
    public int getColumnCount(){
        return 45;
    }

    /**
     * Gets a HistoryDataVector object that consists
     * of HistoryData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new HistoryDataVector()
     * @throws            SQLException
     */
    public static HistoryDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT HISTORY_ID,HISTORY_TYPE_CD,USER_ID,ACTIVITY_DATE,ATTRIBUTE01,ATTRIBUTE02,ATTRIBUTE03,ATTRIBUTE04,ATTRIBUTE05,ATTRIBUTE06,ATTRIBUTE07,ATTRIBUTE08,ATTRIBUTE09,ATTRIBUTE10,ATTRIBUTE11,ATTRIBUTE12,ATTRIBUTE13,ATTRIBUTE14,ATTRIBUTE15,ATTRIBUTE16,ATTRIBUTE17,ATTRIBUTE18,ATTRIBUTE19,ATTRIBUTE20,ATTRIBUTE21,ATTRIBUTE22,ATTRIBUTE23,ATTRIBUTE24,ATTRIBUTE25,ATTRIBUTE26,ATTRIBUTE27,ATTRIBUTE28,ATTRIBUTE29,ATTRIBUTE30,ATTRIBUTE31,ATTRIBUTE32,ATTRIBUTE33,ATTRIBUTE34,ATTRIBUTE35,CLOB1,CLOB2,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_HISTORY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_HISTORY.HISTORY_ID,CLW_HISTORY.HISTORY_TYPE_CD,CLW_HISTORY.USER_ID,CLW_HISTORY.ACTIVITY_DATE,CLW_HISTORY.ATTRIBUTE01,CLW_HISTORY.ATTRIBUTE02,CLW_HISTORY.ATTRIBUTE03,CLW_HISTORY.ATTRIBUTE04,CLW_HISTORY.ATTRIBUTE05,CLW_HISTORY.ATTRIBUTE06,CLW_HISTORY.ATTRIBUTE07,CLW_HISTORY.ATTRIBUTE08,CLW_HISTORY.ATTRIBUTE09,CLW_HISTORY.ATTRIBUTE10,CLW_HISTORY.ATTRIBUTE11,CLW_HISTORY.ATTRIBUTE12,CLW_HISTORY.ATTRIBUTE13,CLW_HISTORY.ATTRIBUTE14,CLW_HISTORY.ATTRIBUTE15,CLW_HISTORY.ATTRIBUTE16,CLW_HISTORY.ATTRIBUTE17,CLW_HISTORY.ATTRIBUTE18,CLW_HISTORY.ATTRIBUTE19,CLW_HISTORY.ATTRIBUTE20,CLW_HISTORY.ATTRIBUTE21,CLW_HISTORY.ATTRIBUTE22,CLW_HISTORY.ATTRIBUTE23,CLW_HISTORY.ATTRIBUTE24,CLW_HISTORY.ATTRIBUTE25,CLW_HISTORY.ATTRIBUTE26,CLW_HISTORY.ATTRIBUTE27,CLW_HISTORY.ATTRIBUTE28,CLW_HISTORY.ATTRIBUTE29,CLW_HISTORY.ATTRIBUTE30,CLW_HISTORY.ATTRIBUTE31,CLW_HISTORY.ATTRIBUTE32,CLW_HISTORY.ATTRIBUTE33,CLW_HISTORY.ATTRIBUTE34,CLW_HISTORY.ATTRIBUTE35,CLW_HISTORY.CLOB1,CLW_HISTORY.CLOB2,CLW_HISTORY.ADD_BY,CLW_HISTORY.ADD_DATE,CLW_HISTORY.MOD_BY,CLW_HISTORY.MOD_DATE FROM CLW_HISTORY");
                where = pCriteria.getSqlClause("CLW_HISTORY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_HISTORY.equals(otherTable)){
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
        HistoryDataVector v = new HistoryDataVector();
        while (rs.next()) {
            HistoryData x = HistoryData.createValue();
            
            x.setHistoryId(rs.getInt(1));
            x.setHistoryTypeCd(rs.getString(2));
            x.setUserId(rs.getString(3));
            x.setActivityDate(rs.getDate(4));
            x.setAttribute01(rs.getString(5));
            x.setAttribute02(rs.getString(6));
            x.setAttribute03(rs.getString(7));
            x.setAttribute04(rs.getString(8));
            x.setAttribute05(rs.getString(9));
            x.setAttribute06(rs.getString(10));
            x.setAttribute07(rs.getString(11));
            x.setAttribute08(rs.getString(12));
            x.setAttribute09(rs.getString(13));
            x.setAttribute10(rs.getString(14));
            x.setAttribute11(rs.getString(15));
            x.setAttribute12(rs.getString(16));
            x.setAttribute13(rs.getString(17));
            x.setAttribute14(rs.getString(18));
            x.setAttribute15(rs.getString(19));
            x.setAttribute16(rs.getString(20));
            x.setAttribute17(rs.getString(21));
            x.setAttribute18(rs.getString(22));
            x.setAttribute19(rs.getString(23));
            x.setAttribute20(rs.getString(24));
            x.setAttribute21(rs.getString(25));
            x.setAttribute22(rs.getString(26));
            x.setAttribute23(rs.getString(27));
            x.setAttribute24(rs.getString(28));
            x.setAttribute25(rs.getString(29));
            x.setAttribute26(rs.getString(30));
            x.setAttribute27(rs.getString(31));
            x.setAttribute28(rs.getString(32));
            x.setAttribute29(rs.getString(33));
            x.setAttribute30(rs.getString(34));
            x.setAttribute31(rs.getString(35));
            x.setAttribute32(rs.getString(36));
            x.setAttribute33(rs.getString(37));
            x.setAttribute34(rs.getString(38));
            x.setAttribute35(rs.getString(39));
            x.setClob1(rs.getString(40));
            x.setClob2(rs.getString(41));
            x.setAddBy(rs.getString(42));
            x.setAddDate(rs.getTimestamp(43));
            x.setModBy(rs.getString(44));
            x.setModDate(rs.getTimestamp(45));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a HistoryDataVector object that consists
     * of HistoryData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for HistoryData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new HistoryDataVector()
     * @throws            SQLException
     */
    public static HistoryDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        HistoryDataVector v = new HistoryDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT HISTORY_ID,HISTORY_TYPE_CD,USER_ID,ACTIVITY_DATE,ATTRIBUTE01,ATTRIBUTE02,ATTRIBUTE03,ATTRIBUTE04,ATTRIBUTE05,ATTRIBUTE06,ATTRIBUTE07,ATTRIBUTE08,ATTRIBUTE09,ATTRIBUTE10,ATTRIBUTE11,ATTRIBUTE12,ATTRIBUTE13,ATTRIBUTE14,ATTRIBUTE15,ATTRIBUTE16,ATTRIBUTE17,ATTRIBUTE18,ATTRIBUTE19,ATTRIBUTE20,ATTRIBUTE21,ATTRIBUTE22,ATTRIBUTE23,ATTRIBUTE24,ATTRIBUTE25,ATTRIBUTE26,ATTRIBUTE27,ATTRIBUTE28,ATTRIBUTE29,ATTRIBUTE30,ATTRIBUTE31,ATTRIBUTE32,ATTRIBUTE33,ATTRIBUTE34,ATTRIBUTE35,CLOB1,CLOB2,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_HISTORY WHERE HISTORY_ID IN (");

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
            HistoryData x=null;
            while (rs.next()) {
                // build the object
                x=HistoryData.createValue();
                
                x.setHistoryId(rs.getInt(1));
                x.setHistoryTypeCd(rs.getString(2));
                x.setUserId(rs.getString(3));
                x.setActivityDate(rs.getDate(4));
                x.setAttribute01(rs.getString(5));
                x.setAttribute02(rs.getString(6));
                x.setAttribute03(rs.getString(7));
                x.setAttribute04(rs.getString(8));
                x.setAttribute05(rs.getString(9));
                x.setAttribute06(rs.getString(10));
                x.setAttribute07(rs.getString(11));
                x.setAttribute08(rs.getString(12));
                x.setAttribute09(rs.getString(13));
                x.setAttribute10(rs.getString(14));
                x.setAttribute11(rs.getString(15));
                x.setAttribute12(rs.getString(16));
                x.setAttribute13(rs.getString(17));
                x.setAttribute14(rs.getString(18));
                x.setAttribute15(rs.getString(19));
                x.setAttribute16(rs.getString(20));
                x.setAttribute17(rs.getString(21));
                x.setAttribute18(rs.getString(22));
                x.setAttribute19(rs.getString(23));
                x.setAttribute20(rs.getString(24));
                x.setAttribute21(rs.getString(25));
                x.setAttribute22(rs.getString(26));
                x.setAttribute23(rs.getString(27));
                x.setAttribute24(rs.getString(28));
                x.setAttribute25(rs.getString(29));
                x.setAttribute26(rs.getString(30));
                x.setAttribute27(rs.getString(31));
                x.setAttribute28(rs.getString(32));
                x.setAttribute29(rs.getString(33));
                x.setAttribute30(rs.getString(34));
                x.setAttribute31(rs.getString(35));
                x.setAttribute32(rs.getString(36));
                x.setAttribute33(rs.getString(37));
                x.setAttribute34(rs.getString(38));
                x.setAttribute35(rs.getString(39));
                x.setClob1(rs.getString(40));
                x.setClob2(rs.getString(41));
                x.setAddBy(rs.getString(42));
                x.setAddDate(rs.getTimestamp(43));
                x.setModBy(rs.getString(44));
                x.setModDate(rs.getTimestamp(45));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a HistoryDataVector object of all
     * HistoryData objects in the database.
     * @param pCon An open database connection.
     * @return new HistoryDataVector()
     * @throws            SQLException
     */
    public static HistoryDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT HISTORY_ID,HISTORY_TYPE_CD,USER_ID,ACTIVITY_DATE,ATTRIBUTE01,ATTRIBUTE02,ATTRIBUTE03,ATTRIBUTE04,ATTRIBUTE05,ATTRIBUTE06,ATTRIBUTE07,ATTRIBUTE08,ATTRIBUTE09,ATTRIBUTE10,ATTRIBUTE11,ATTRIBUTE12,ATTRIBUTE13,ATTRIBUTE14,ATTRIBUTE15,ATTRIBUTE16,ATTRIBUTE17,ATTRIBUTE18,ATTRIBUTE19,ATTRIBUTE20,ATTRIBUTE21,ATTRIBUTE22,ATTRIBUTE23,ATTRIBUTE24,ATTRIBUTE25,ATTRIBUTE26,ATTRIBUTE27,ATTRIBUTE28,ATTRIBUTE29,ATTRIBUTE30,ATTRIBUTE31,ATTRIBUTE32,ATTRIBUTE33,ATTRIBUTE34,ATTRIBUTE35,CLOB1,CLOB2,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_HISTORY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        HistoryDataVector v = new HistoryDataVector();
        HistoryData x = null;
        while (rs.next()) {
            // build the object
            x = HistoryData.createValue();
            
            x.setHistoryId(rs.getInt(1));
            x.setHistoryTypeCd(rs.getString(2));
            x.setUserId(rs.getString(3));
            x.setActivityDate(rs.getDate(4));
            x.setAttribute01(rs.getString(5));
            x.setAttribute02(rs.getString(6));
            x.setAttribute03(rs.getString(7));
            x.setAttribute04(rs.getString(8));
            x.setAttribute05(rs.getString(9));
            x.setAttribute06(rs.getString(10));
            x.setAttribute07(rs.getString(11));
            x.setAttribute08(rs.getString(12));
            x.setAttribute09(rs.getString(13));
            x.setAttribute10(rs.getString(14));
            x.setAttribute11(rs.getString(15));
            x.setAttribute12(rs.getString(16));
            x.setAttribute13(rs.getString(17));
            x.setAttribute14(rs.getString(18));
            x.setAttribute15(rs.getString(19));
            x.setAttribute16(rs.getString(20));
            x.setAttribute17(rs.getString(21));
            x.setAttribute18(rs.getString(22));
            x.setAttribute19(rs.getString(23));
            x.setAttribute20(rs.getString(24));
            x.setAttribute21(rs.getString(25));
            x.setAttribute22(rs.getString(26));
            x.setAttribute23(rs.getString(27));
            x.setAttribute24(rs.getString(28));
            x.setAttribute25(rs.getString(29));
            x.setAttribute26(rs.getString(30));
            x.setAttribute27(rs.getString(31));
            x.setAttribute28(rs.getString(32));
            x.setAttribute29(rs.getString(33));
            x.setAttribute30(rs.getString(34));
            x.setAttribute31(rs.getString(35));
            x.setAttribute32(rs.getString(36));
            x.setAttribute33(rs.getString(37));
            x.setAttribute34(rs.getString(38));
            x.setAttribute35(rs.getString(39));
            x.setClob1(rs.getString(40));
            x.setClob2(rs.getString(41));
            x.setAddBy(rs.getString(42));
            x.setAddDate(rs.getTimestamp(43));
            x.setModBy(rs.getString(44));
            x.setModDate(rs.getTimestamp(45));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * HistoryData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT HISTORY_ID FROM CLW_HISTORY");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT HISTORY_ID FROM CLW_HISTORY");
                where = pCriteria.getSqlClause("CLW_HISTORY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_HISTORY.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_HISTORY");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_HISTORY");
                where = pCriteria.getSqlClause("CLW_HISTORY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_HISTORY.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_HISTORY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_HISTORY");
                where = pCriteria.getSqlClause("CLW_HISTORY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_HISTORY.equals(otherTable)){
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
     * Inserts a HistoryData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A HistoryData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new HistoryData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static HistoryData insert(Connection pCon, HistoryData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_HISTORY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_HISTORY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setHistoryId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_HISTORY (HISTORY_ID,HISTORY_TYPE_CD,USER_ID,ACTIVITY_DATE,ATTRIBUTE01,ATTRIBUTE02,ATTRIBUTE03,ATTRIBUTE04,ATTRIBUTE05,ATTRIBUTE06,ATTRIBUTE07,ATTRIBUTE08,ATTRIBUTE09,ATTRIBUTE10,ATTRIBUTE11,ATTRIBUTE12,ATTRIBUTE13,ATTRIBUTE14,ATTRIBUTE15,ATTRIBUTE16,ATTRIBUTE17,ATTRIBUTE18,ATTRIBUTE19,ATTRIBUTE20,ATTRIBUTE21,ATTRIBUTE22,ATTRIBUTE23,ATTRIBUTE24,ATTRIBUTE25,ATTRIBUTE26,ATTRIBUTE27,ATTRIBUTE28,ATTRIBUTE29,ATTRIBUTE30,ATTRIBUTE31,ATTRIBUTE32,ATTRIBUTE33,ATTRIBUTE34,ATTRIBUTE35,CLOB1,CLOB2,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getHistoryId());
        pstmt.setString(2,pData.getHistoryTypeCd());
        pstmt.setString(3,pData.getUserId());
        pstmt.setDate(4,DBAccess.toSQLDate(pData.getActivityDate()));
        pstmt.setString(5,pData.getAttribute01());
        pstmt.setString(6,pData.getAttribute02());
        pstmt.setString(7,pData.getAttribute03());
        pstmt.setString(8,pData.getAttribute04());
        pstmt.setString(9,pData.getAttribute05());
        pstmt.setString(10,pData.getAttribute06());
        pstmt.setString(11,pData.getAttribute07());
        pstmt.setString(12,pData.getAttribute08());
        pstmt.setString(13,pData.getAttribute09());
        pstmt.setString(14,pData.getAttribute10());
        pstmt.setString(15,pData.getAttribute11());
        pstmt.setString(16,pData.getAttribute12());
        pstmt.setString(17,pData.getAttribute13());
        pstmt.setString(18,pData.getAttribute14());
        pstmt.setString(19,pData.getAttribute15());
        pstmt.setString(20,pData.getAttribute16());
        pstmt.setString(21,pData.getAttribute17());
        pstmt.setString(22,pData.getAttribute18());
        pstmt.setString(23,pData.getAttribute19());
        pstmt.setString(24,pData.getAttribute20());
        pstmt.setString(25,pData.getAttribute21());
        pstmt.setString(26,pData.getAttribute22());
        pstmt.setString(27,pData.getAttribute23());
        pstmt.setString(28,pData.getAttribute24());
        pstmt.setString(29,pData.getAttribute25());
        pstmt.setString(30,pData.getAttribute26());
        pstmt.setString(31,pData.getAttribute27());
        pstmt.setString(32,pData.getAttribute28());
        pstmt.setString(33,pData.getAttribute29());
        pstmt.setString(34,pData.getAttribute30());
        pstmt.setString(35,pData.getAttribute31());
        pstmt.setString(36,pData.getAttribute32());
        pstmt.setString(37,pData.getAttribute33());
        pstmt.setString(38,pData.getAttribute34());
        pstmt.setString(39,pData.getAttribute35());
        pstmt.setString(40,pData.getClob1());
        pstmt.setString(41,pData.getClob2());
        pstmt.setString(42,pData.getAddBy());
        pstmt.setTimestamp(43,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(44,pData.getModBy());
        pstmt.setTimestamp(45,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   HISTORY_ID="+pData.getHistoryId());
            log.debug("SQL:   HISTORY_TYPE_CD="+pData.getHistoryTypeCd());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ACTIVITY_DATE="+pData.getActivityDate());
            log.debug("SQL:   ATTRIBUTE01="+pData.getAttribute01());
            log.debug("SQL:   ATTRIBUTE02="+pData.getAttribute02());
            log.debug("SQL:   ATTRIBUTE03="+pData.getAttribute03());
            log.debug("SQL:   ATTRIBUTE04="+pData.getAttribute04());
            log.debug("SQL:   ATTRIBUTE05="+pData.getAttribute05());
            log.debug("SQL:   ATTRIBUTE06="+pData.getAttribute06());
            log.debug("SQL:   ATTRIBUTE07="+pData.getAttribute07());
            log.debug("SQL:   ATTRIBUTE08="+pData.getAttribute08());
            log.debug("SQL:   ATTRIBUTE09="+pData.getAttribute09());
            log.debug("SQL:   ATTRIBUTE10="+pData.getAttribute10());
            log.debug("SQL:   ATTRIBUTE11="+pData.getAttribute11());
            log.debug("SQL:   ATTRIBUTE12="+pData.getAttribute12());
            log.debug("SQL:   ATTRIBUTE13="+pData.getAttribute13());
            log.debug("SQL:   ATTRIBUTE14="+pData.getAttribute14());
            log.debug("SQL:   ATTRIBUTE15="+pData.getAttribute15());
            log.debug("SQL:   ATTRIBUTE16="+pData.getAttribute16());
            log.debug("SQL:   ATTRIBUTE17="+pData.getAttribute17());
            log.debug("SQL:   ATTRIBUTE18="+pData.getAttribute18());
            log.debug("SQL:   ATTRIBUTE19="+pData.getAttribute19());
            log.debug("SQL:   ATTRIBUTE20="+pData.getAttribute20());
            log.debug("SQL:   ATTRIBUTE21="+pData.getAttribute21());
            log.debug("SQL:   ATTRIBUTE22="+pData.getAttribute22());
            log.debug("SQL:   ATTRIBUTE23="+pData.getAttribute23());
            log.debug("SQL:   ATTRIBUTE24="+pData.getAttribute24());
            log.debug("SQL:   ATTRIBUTE25="+pData.getAttribute25());
            log.debug("SQL:   ATTRIBUTE26="+pData.getAttribute26());
            log.debug("SQL:   ATTRIBUTE27="+pData.getAttribute27());
            log.debug("SQL:   ATTRIBUTE28="+pData.getAttribute28());
            log.debug("SQL:   ATTRIBUTE29="+pData.getAttribute29());
            log.debug("SQL:   ATTRIBUTE30="+pData.getAttribute30());
            log.debug("SQL:   ATTRIBUTE31="+pData.getAttribute31());
            log.debug("SQL:   ATTRIBUTE32="+pData.getAttribute32());
            log.debug("SQL:   ATTRIBUTE33="+pData.getAttribute33());
            log.debug("SQL:   ATTRIBUTE34="+pData.getAttribute34());
            log.debug("SQL:   ATTRIBUTE35="+pData.getAttribute35());
            log.debug("SQL:   CLOB1="+pData.getClob1());
            log.debug("SQL:   CLOB2="+pData.getClob2());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setHistoryId(0);
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
     * Updates a HistoryData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A HistoryData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, HistoryData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_HISTORY SET HISTORY_TYPE_CD = ?,USER_ID = ?,ACTIVITY_DATE = ?,ATTRIBUTE01 = ?,ATTRIBUTE02 = ?,ATTRIBUTE03 = ?,ATTRIBUTE04 = ?,ATTRIBUTE05 = ?,ATTRIBUTE06 = ?,ATTRIBUTE07 = ?,ATTRIBUTE08 = ?,ATTRIBUTE09 = ?,ATTRIBUTE10 = ?,ATTRIBUTE11 = ?,ATTRIBUTE12 = ?,ATTRIBUTE13 = ?,ATTRIBUTE14 = ?,ATTRIBUTE15 = ?,ATTRIBUTE16 = ?,ATTRIBUTE17 = ?,ATTRIBUTE18 = ?,ATTRIBUTE19 = ?,ATTRIBUTE20 = ?,ATTRIBUTE21 = ?,ATTRIBUTE22 = ?,ATTRIBUTE23 = ?,ATTRIBUTE24 = ?,ATTRIBUTE25 = ?,ATTRIBUTE26 = ?,ATTRIBUTE27 = ?,ATTRIBUTE28 = ?,ATTRIBUTE29 = ?,ATTRIBUTE30 = ?,ATTRIBUTE31 = ?,ATTRIBUTE32 = ?,ATTRIBUTE33 = ?,ATTRIBUTE34 = ?,ATTRIBUTE35 = ?,CLOB1 = ?,CLOB2 = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE HISTORY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getHistoryTypeCd());
        pstmt.setString(i++,pData.getUserId());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getActivityDate()));
        pstmt.setString(i++,pData.getAttribute01());
        pstmt.setString(i++,pData.getAttribute02());
        pstmt.setString(i++,pData.getAttribute03());
        pstmt.setString(i++,pData.getAttribute04());
        pstmt.setString(i++,pData.getAttribute05());
        pstmt.setString(i++,pData.getAttribute06());
        pstmt.setString(i++,pData.getAttribute07());
        pstmt.setString(i++,pData.getAttribute08());
        pstmt.setString(i++,pData.getAttribute09());
        pstmt.setString(i++,pData.getAttribute10());
        pstmt.setString(i++,pData.getAttribute11());
        pstmt.setString(i++,pData.getAttribute12());
        pstmt.setString(i++,pData.getAttribute13());
        pstmt.setString(i++,pData.getAttribute14());
        pstmt.setString(i++,pData.getAttribute15());
        pstmt.setString(i++,pData.getAttribute16());
        pstmt.setString(i++,pData.getAttribute17());
        pstmt.setString(i++,pData.getAttribute18());
        pstmt.setString(i++,pData.getAttribute19());
        pstmt.setString(i++,pData.getAttribute20());
        pstmt.setString(i++,pData.getAttribute21());
        pstmt.setString(i++,pData.getAttribute22());
        pstmt.setString(i++,pData.getAttribute23());
        pstmt.setString(i++,pData.getAttribute24());
        pstmt.setString(i++,pData.getAttribute25());
        pstmt.setString(i++,pData.getAttribute26());
        pstmt.setString(i++,pData.getAttribute27());
        pstmt.setString(i++,pData.getAttribute28());
        pstmt.setString(i++,pData.getAttribute29());
        pstmt.setString(i++,pData.getAttribute30());
        pstmt.setString(i++,pData.getAttribute31());
        pstmt.setString(i++,pData.getAttribute32());
        pstmt.setString(i++,pData.getAttribute33());
        pstmt.setString(i++,pData.getAttribute34());
        pstmt.setString(i++,pData.getAttribute35());
        pstmt.setString(i++,pData.getClob1());
        pstmt.setString(i++,pData.getClob2());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getHistoryId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   HISTORY_TYPE_CD="+pData.getHistoryTypeCd());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ACTIVITY_DATE="+pData.getActivityDate());
            log.debug("SQL:   ATTRIBUTE01="+pData.getAttribute01());
            log.debug("SQL:   ATTRIBUTE02="+pData.getAttribute02());
            log.debug("SQL:   ATTRIBUTE03="+pData.getAttribute03());
            log.debug("SQL:   ATTRIBUTE04="+pData.getAttribute04());
            log.debug("SQL:   ATTRIBUTE05="+pData.getAttribute05());
            log.debug("SQL:   ATTRIBUTE06="+pData.getAttribute06());
            log.debug("SQL:   ATTRIBUTE07="+pData.getAttribute07());
            log.debug("SQL:   ATTRIBUTE08="+pData.getAttribute08());
            log.debug("SQL:   ATTRIBUTE09="+pData.getAttribute09());
            log.debug("SQL:   ATTRIBUTE10="+pData.getAttribute10());
            log.debug("SQL:   ATTRIBUTE11="+pData.getAttribute11());
            log.debug("SQL:   ATTRIBUTE12="+pData.getAttribute12());
            log.debug("SQL:   ATTRIBUTE13="+pData.getAttribute13());
            log.debug("SQL:   ATTRIBUTE14="+pData.getAttribute14());
            log.debug("SQL:   ATTRIBUTE15="+pData.getAttribute15());
            log.debug("SQL:   ATTRIBUTE16="+pData.getAttribute16());
            log.debug("SQL:   ATTRIBUTE17="+pData.getAttribute17());
            log.debug("SQL:   ATTRIBUTE18="+pData.getAttribute18());
            log.debug("SQL:   ATTRIBUTE19="+pData.getAttribute19());
            log.debug("SQL:   ATTRIBUTE20="+pData.getAttribute20());
            log.debug("SQL:   ATTRIBUTE21="+pData.getAttribute21());
            log.debug("SQL:   ATTRIBUTE22="+pData.getAttribute22());
            log.debug("SQL:   ATTRIBUTE23="+pData.getAttribute23());
            log.debug("SQL:   ATTRIBUTE24="+pData.getAttribute24());
            log.debug("SQL:   ATTRIBUTE25="+pData.getAttribute25());
            log.debug("SQL:   ATTRIBUTE26="+pData.getAttribute26());
            log.debug("SQL:   ATTRIBUTE27="+pData.getAttribute27());
            log.debug("SQL:   ATTRIBUTE28="+pData.getAttribute28());
            log.debug("SQL:   ATTRIBUTE29="+pData.getAttribute29());
            log.debug("SQL:   ATTRIBUTE30="+pData.getAttribute30());
            log.debug("SQL:   ATTRIBUTE31="+pData.getAttribute31());
            log.debug("SQL:   ATTRIBUTE32="+pData.getAttribute32());
            log.debug("SQL:   ATTRIBUTE33="+pData.getAttribute33());
            log.debug("SQL:   ATTRIBUTE34="+pData.getAttribute34());
            log.debug("SQL:   ATTRIBUTE35="+pData.getAttribute35());
            log.debug("SQL:   CLOB1="+pData.getClob1());
            log.debug("SQL:   CLOB2="+pData.getClob2());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a HistoryData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pHistoryId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pHistoryId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_HISTORY WHERE HISTORY_ID = " + pHistoryId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes HistoryData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_HISTORY");
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
     * Inserts a HistoryData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A HistoryData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, HistoryData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_HISTORY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "HISTORY_ID,HISTORY_TYPE_CD,USER_ID,ACTIVITY_DATE,ATTRIBUTE01,ATTRIBUTE02,ATTRIBUTE03,ATTRIBUTE04,ATTRIBUTE05,ATTRIBUTE06,ATTRIBUTE07,ATTRIBUTE08,ATTRIBUTE09,ATTRIBUTE10,ATTRIBUTE11,ATTRIBUTE12,ATTRIBUTE13,ATTRIBUTE14,ATTRIBUTE15,ATTRIBUTE16,ATTRIBUTE17,ATTRIBUTE18,ATTRIBUTE19,ATTRIBUTE20,ATTRIBUTE21,ATTRIBUTE22,ATTRIBUTE23,ATTRIBUTE24,ATTRIBUTE25,ATTRIBUTE26,ATTRIBUTE27,ATTRIBUTE28,ATTRIBUTE29,ATTRIBUTE30,ATTRIBUTE31,ATTRIBUTE32,ATTRIBUTE33,ATTRIBUTE34,ATTRIBUTE35,CLOB1,CLOB2,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getHistoryId());
        pstmt.setString(2+4,pData.getHistoryTypeCd());
        pstmt.setString(3+4,pData.getUserId());
        pstmt.setDate(4+4,DBAccess.toSQLDate(pData.getActivityDate()));
        pstmt.setString(5+4,pData.getAttribute01());
        pstmt.setString(6+4,pData.getAttribute02());
        pstmt.setString(7+4,pData.getAttribute03());
        pstmt.setString(8+4,pData.getAttribute04());
        pstmt.setString(9+4,pData.getAttribute05());
        pstmt.setString(10+4,pData.getAttribute06());
        pstmt.setString(11+4,pData.getAttribute07());
        pstmt.setString(12+4,pData.getAttribute08());
        pstmt.setString(13+4,pData.getAttribute09());
        pstmt.setString(14+4,pData.getAttribute10());
        pstmt.setString(15+4,pData.getAttribute11());
        pstmt.setString(16+4,pData.getAttribute12());
        pstmt.setString(17+4,pData.getAttribute13());
        pstmt.setString(18+4,pData.getAttribute14());
        pstmt.setString(19+4,pData.getAttribute15());
        pstmt.setString(20+4,pData.getAttribute16());
        pstmt.setString(21+4,pData.getAttribute17());
        pstmt.setString(22+4,pData.getAttribute18());
        pstmt.setString(23+4,pData.getAttribute19());
        pstmt.setString(24+4,pData.getAttribute20());
        pstmt.setString(25+4,pData.getAttribute21());
        pstmt.setString(26+4,pData.getAttribute22());
        pstmt.setString(27+4,pData.getAttribute23());
        pstmt.setString(28+4,pData.getAttribute24());
        pstmt.setString(29+4,pData.getAttribute25());
        pstmt.setString(30+4,pData.getAttribute26());
        pstmt.setString(31+4,pData.getAttribute27());
        pstmt.setString(32+4,pData.getAttribute28());
        pstmt.setString(33+4,pData.getAttribute29());
        pstmt.setString(34+4,pData.getAttribute30());
        pstmt.setString(35+4,pData.getAttribute31());
        pstmt.setString(36+4,pData.getAttribute32());
        pstmt.setString(37+4,pData.getAttribute33());
        pstmt.setString(38+4,pData.getAttribute34());
        pstmt.setString(39+4,pData.getAttribute35());
        pstmt.setString(40+4,pData.getClob1());
        pstmt.setString(41+4,pData.getClob2());
        pstmt.setString(42+4,pData.getAddBy());
        pstmt.setTimestamp(43+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(44+4,pData.getModBy());
        pstmt.setTimestamp(45+4,DBAccess.toSQLTimestamp(pData.getModDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a HistoryData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A HistoryData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new HistoryData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static HistoryData insert(Connection pCon, HistoryData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a HistoryData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A HistoryData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, HistoryData pData, boolean pLogFl)
        throws SQLException {
        HistoryData oldData = null;
        if(pLogFl) {
          int id = pData.getHistoryId();
          try {
          oldData = HistoryDataAccess.select(pCon,id);
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
     * Deletes a HistoryData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pHistoryId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pHistoryId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_HISTORY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_HISTORY d WHERE HISTORY_ID = " + pHistoryId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pHistoryId);
        return n;
     }

    /**
     * Deletes HistoryData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_HISTORY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_HISTORY d ");
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

