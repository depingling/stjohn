
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        FreightTableCriteriaDataAccess
 * Description:  This class is used to build access methods to the CLW_FREIGHT_TABLE_CRITERIA table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.FreightTableCriteriaData;
import com.cleanwise.service.api.value.FreightTableCriteriaDataVector;
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
 * <code>FreightTableCriteriaDataAccess</code>
 */
public class FreightTableCriteriaDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(FreightTableCriteriaDataAccess.class.getName());

    /** <code>CLW_FREIGHT_TABLE_CRITERIA</code> table name */
	/* Primary key: FREIGHT_TABLE_CRITERIA_ID */
	
    public static final String CLW_FREIGHT_TABLE_CRITERIA = "CLW_FREIGHT_TABLE_CRITERIA";
    
    /** <code>FREIGHT_TABLE_ID</code> FREIGHT_TABLE_ID column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String FREIGHT_TABLE_ID = "FREIGHT_TABLE_ID";
    /** <code>FREIGHT_TABLE_CRITERIA_ID</code> FREIGHT_TABLE_CRITERIA_ID column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String FREIGHT_TABLE_CRITERIA_ID = "FREIGHT_TABLE_CRITERIA_ID";
    /** <code>LOWER_AMOUNT</code> LOWER_AMOUNT column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String LOWER_AMOUNT = "LOWER_AMOUNT";
    /** <code>HIGHER_AMOUNT</code> HIGHER_AMOUNT column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String HIGHER_AMOUNT = "HIGHER_AMOUNT";
    /** <code>FREIGHT_AMOUNT</code> FREIGHT_AMOUNT column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String FREIGHT_AMOUNT = "FREIGHT_AMOUNT";
    /** <code>HANDLING_AMOUNT</code> HANDLING_AMOUNT column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String HANDLING_AMOUNT = "HANDLING_AMOUNT";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String MOD_BY = "MOD_BY";
    /** <code>FREIGHT_HANDLER_ID</code> FREIGHT_HANDLER_ID column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String FREIGHT_HANDLER_ID = "FREIGHT_HANDLER_ID";
    /** <code>FREIGHT_CRITERIA_TYPE_CD</code> FREIGHT_CRITERIA_TYPE_CD column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String FREIGHT_CRITERIA_TYPE_CD = "FREIGHT_CRITERIA_TYPE_CD";
    /** <code>RUNTIME_TYPE_CD</code> RUNTIME_TYPE_CD column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String RUNTIME_TYPE_CD = "RUNTIME_TYPE_CD";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>UI_ORDER</code> UI_ORDER column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String UI_ORDER = "UI_ORDER";
    /** <code>CHARGE_CD</code> CHARGE_CD column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String CHARGE_CD = "CHARGE_CD";
    /** <code>DISCOUNT</code> DISCOUNT column of table CLW_FREIGHT_TABLE_CRITERIA */
    public static final String DISCOUNT = "DISCOUNT";

    /**
     * Constructor.
     */
    public FreightTableCriteriaDataAccess()
    {
    }

    /**
     * Gets a FreightTableCriteriaData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pFreightTableCriteriaId The key requested.
     * @return new FreightTableCriteriaData()
     * @throws            SQLException
     */
    public static FreightTableCriteriaData select(Connection pCon, int pFreightTableCriteriaId)
        throws SQLException, DataNotFoundException {
        FreightTableCriteriaData x=null;
        String sql="SELECT FREIGHT_TABLE_ID,FREIGHT_TABLE_CRITERIA_ID,LOWER_AMOUNT,HIGHER_AMOUNT,FREIGHT_AMOUNT,HANDLING_AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,FREIGHT_HANDLER_ID,FREIGHT_CRITERIA_TYPE_CD,RUNTIME_TYPE_CD,SHORT_DESC,UI_ORDER,CHARGE_CD,DISCOUNT FROM CLW_FREIGHT_TABLE_CRITERIA WHERE FREIGHT_TABLE_CRITERIA_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pFreightTableCriteriaId=" + pFreightTableCriteriaId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pFreightTableCriteriaId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=FreightTableCriteriaData.createValue();
            
            x.setFreightTableId(rs.getInt(1));
            x.setFreightTableCriteriaId(rs.getInt(2));
            x.setLowerAmount(rs.getBigDecimal(3));
            x.setHigherAmount(rs.getBigDecimal(4));
            x.setFreightAmount(rs.getBigDecimal(5));
            x.setHandlingAmount(rs.getBigDecimal(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setFreightHandlerId(rs.getInt(11));
            x.setFreightCriteriaTypeCd(rs.getString(12));
            x.setRuntimeTypeCd(rs.getString(13));
            x.setShortDesc(rs.getString(14));
            x.setUiOrder(rs.getInt(15));
            x.setChargeCd(rs.getString(16));
            x.setDiscount(rs.getBigDecimal(17));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("FREIGHT_TABLE_CRITERIA_ID :" + pFreightTableCriteriaId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a FreightTableCriteriaDataVector object that consists
     * of FreightTableCriteriaData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new FreightTableCriteriaDataVector()
     * @throws            SQLException
     */
    public static FreightTableCriteriaDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a FreightTableCriteriaData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_FREIGHT_TABLE_CRITERIA.FREIGHT_TABLE_ID,CLW_FREIGHT_TABLE_CRITERIA.FREIGHT_TABLE_CRITERIA_ID,CLW_FREIGHT_TABLE_CRITERIA.LOWER_AMOUNT,CLW_FREIGHT_TABLE_CRITERIA.HIGHER_AMOUNT,CLW_FREIGHT_TABLE_CRITERIA.FREIGHT_AMOUNT,CLW_FREIGHT_TABLE_CRITERIA.HANDLING_AMOUNT,CLW_FREIGHT_TABLE_CRITERIA.ADD_DATE,CLW_FREIGHT_TABLE_CRITERIA.ADD_BY,CLW_FREIGHT_TABLE_CRITERIA.MOD_DATE,CLW_FREIGHT_TABLE_CRITERIA.MOD_BY,CLW_FREIGHT_TABLE_CRITERIA.FREIGHT_HANDLER_ID,CLW_FREIGHT_TABLE_CRITERIA.FREIGHT_CRITERIA_TYPE_CD,CLW_FREIGHT_TABLE_CRITERIA.RUNTIME_TYPE_CD,CLW_FREIGHT_TABLE_CRITERIA.SHORT_DESC,CLW_FREIGHT_TABLE_CRITERIA.UI_ORDER,CLW_FREIGHT_TABLE_CRITERIA.CHARGE_CD,CLW_FREIGHT_TABLE_CRITERIA.DISCOUNT";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated FreightTableCriteriaData Object.
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
    *@returns a populated FreightTableCriteriaData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         FreightTableCriteriaData x = FreightTableCriteriaData.createValue();
         
         x.setFreightTableId(rs.getInt(1+offset));
         x.setFreightTableCriteriaId(rs.getInt(2+offset));
         x.setLowerAmount(rs.getBigDecimal(3+offset));
         x.setHigherAmount(rs.getBigDecimal(4+offset));
         x.setFreightAmount(rs.getBigDecimal(5+offset));
         x.setHandlingAmount(rs.getBigDecimal(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         x.setFreightHandlerId(rs.getInt(11+offset));
         x.setFreightCriteriaTypeCd(rs.getString(12+offset));
         x.setRuntimeTypeCd(rs.getString(13+offset));
         x.setShortDesc(rs.getString(14+offset));
         x.setUiOrder(rs.getInt(15+offset));
         x.setChargeCd(rs.getString(16+offset));
         x.setDiscount(rs.getBigDecimal(17+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the FreightTableCriteriaData Object represents.
    */
    public int getColumnCount(){
        return 17;
    }

    /**
     * Gets a FreightTableCriteriaDataVector object that consists
     * of FreightTableCriteriaData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new FreightTableCriteriaDataVector()
     * @throws            SQLException
     */
    public static FreightTableCriteriaDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT FREIGHT_TABLE_ID,FREIGHT_TABLE_CRITERIA_ID,LOWER_AMOUNT,HIGHER_AMOUNT,FREIGHT_AMOUNT,HANDLING_AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,FREIGHT_HANDLER_ID,FREIGHT_CRITERIA_TYPE_CD,RUNTIME_TYPE_CD,SHORT_DESC,UI_ORDER,CHARGE_CD,DISCOUNT FROM CLW_FREIGHT_TABLE_CRITERIA");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_FREIGHT_TABLE_CRITERIA.FREIGHT_TABLE_ID,CLW_FREIGHT_TABLE_CRITERIA.FREIGHT_TABLE_CRITERIA_ID,CLW_FREIGHT_TABLE_CRITERIA.LOWER_AMOUNT,CLW_FREIGHT_TABLE_CRITERIA.HIGHER_AMOUNT,CLW_FREIGHT_TABLE_CRITERIA.FREIGHT_AMOUNT,CLW_FREIGHT_TABLE_CRITERIA.HANDLING_AMOUNT,CLW_FREIGHT_TABLE_CRITERIA.ADD_DATE,CLW_FREIGHT_TABLE_CRITERIA.ADD_BY,CLW_FREIGHT_TABLE_CRITERIA.MOD_DATE,CLW_FREIGHT_TABLE_CRITERIA.MOD_BY,CLW_FREIGHT_TABLE_CRITERIA.FREIGHT_HANDLER_ID,CLW_FREIGHT_TABLE_CRITERIA.FREIGHT_CRITERIA_TYPE_CD,CLW_FREIGHT_TABLE_CRITERIA.RUNTIME_TYPE_CD,CLW_FREIGHT_TABLE_CRITERIA.SHORT_DESC,CLW_FREIGHT_TABLE_CRITERIA.UI_ORDER,CLW_FREIGHT_TABLE_CRITERIA.CHARGE_CD,CLW_FREIGHT_TABLE_CRITERIA.DISCOUNT FROM CLW_FREIGHT_TABLE_CRITERIA");
                where = pCriteria.getSqlClause("CLW_FREIGHT_TABLE_CRITERIA");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_FREIGHT_TABLE_CRITERIA.equals(otherTable)){
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
        FreightTableCriteriaDataVector v = new FreightTableCriteriaDataVector();
        while (rs.next()) {
            FreightTableCriteriaData x = FreightTableCriteriaData.createValue();
            
            x.setFreightTableId(rs.getInt(1));
            x.setFreightTableCriteriaId(rs.getInt(2));
            x.setLowerAmount(rs.getBigDecimal(3));
            x.setHigherAmount(rs.getBigDecimal(4));
            x.setFreightAmount(rs.getBigDecimal(5));
            x.setHandlingAmount(rs.getBigDecimal(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setFreightHandlerId(rs.getInt(11));
            x.setFreightCriteriaTypeCd(rs.getString(12));
            x.setRuntimeTypeCd(rs.getString(13));
            x.setShortDesc(rs.getString(14));
            x.setUiOrder(rs.getInt(15));
            x.setChargeCd(rs.getString(16));
            x.setDiscount(rs.getBigDecimal(17));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a FreightTableCriteriaDataVector object that consists
     * of FreightTableCriteriaData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for FreightTableCriteriaData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new FreightTableCriteriaDataVector()
     * @throws            SQLException
     */
    public static FreightTableCriteriaDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        FreightTableCriteriaDataVector v = new FreightTableCriteriaDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT FREIGHT_TABLE_ID,FREIGHT_TABLE_CRITERIA_ID,LOWER_AMOUNT,HIGHER_AMOUNT,FREIGHT_AMOUNT,HANDLING_AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,FREIGHT_HANDLER_ID,FREIGHT_CRITERIA_TYPE_CD,RUNTIME_TYPE_CD,SHORT_DESC,UI_ORDER,CHARGE_CD,DISCOUNT FROM CLW_FREIGHT_TABLE_CRITERIA WHERE FREIGHT_TABLE_CRITERIA_ID IN (");

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
            FreightTableCriteriaData x=null;
            while (rs.next()) {
                // build the object
                x=FreightTableCriteriaData.createValue();
                
                x.setFreightTableId(rs.getInt(1));
                x.setFreightTableCriteriaId(rs.getInt(2));
                x.setLowerAmount(rs.getBigDecimal(3));
                x.setHigherAmount(rs.getBigDecimal(4));
                x.setFreightAmount(rs.getBigDecimal(5));
                x.setHandlingAmount(rs.getBigDecimal(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                x.setFreightHandlerId(rs.getInt(11));
                x.setFreightCriteriaTypeCd(rs.getString(12));
                x.setRuntimeTypeCd(rs.getString(13));
                x.setShortDesc(rs.getString(14));
                x.setUiOrder(rs.getInt(15));
                x.setChargeCd(rs.getString(16));
                x.setDiscount(rs.getBigDecimal(17));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a FreightTableCriteriaDataVector object of all
     * FreightTableCriteriaData objects in the database.
     * @param pCon An open database connection.
     * @return new FreightTableCriteriaDataVector()
     * @throws            SQLException
     */
    public static FreightTableCriteriaDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT FREIGHT_TABLE_ID,FREIGHT_TABLE_CRITERIA_ID,LOWER_AMOUNT,HIGHER_AMOUNT,FREIGHT_AMOUNT,HANDLING_AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,FREIGHT_HANDLER_ID,FREIGHT_CRITERIA_TYPE_CD,RUNTIME_TYPE_CD,SHORT_DESC,UI_ORDER,CHARGE_CD,DISCOUNT FROM CLW_FREIGHT_TABLE_CRITERIA";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        FreightTableCriteriaDataVector v = new FreightTableCriteriaDataVector();
        FreightTableCriteriaData x = null;
        while (rs.next()) {
            // build the object
            x = FreightTableCriteriaData.createValue();
            
            x.setFreightTableId(rs.getInt(1));
            x.setFreightTableCriteriaId(rs.getInt(2));
            x.setLowerAmount(rs.getBigDecimal(3));
            x.setHigherAmount(rs.getBigDecimal(4));
            x.setFreightAmount(rs.getBigDecimal(5));
            x.setHandlingAmount(rs.getBigDecimal(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setFreightHandlerId(rs.getInt(11));
            x.setFreightCriteriaTypeCd(rs.getString(12));
            x.setRuntimeTypeCd(rs.getString(13));
            x.setShortDesc(rs.getString(14));
            x.setUiOrder(rs.getInt(15));
            x.setChargeCd(rs.getString(16));
            x.setDiscount(rs.getBigDecimal(17));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * FreightTableCriteriaData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT FREIGHT_TABLE_CRITERIA_ID FROM CLW_FREIGHT_TABLE_CRITERIA");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FREIGHT_TABLE_CRITERIA");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FREIGHT_TABLE_CRITERIA");
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
     * Inserts a FreightTableCriteriaData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FreightTableCriteriaData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new FreightTableCriteriaData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FreightTableCriteriaData insert(Connection pCon, FreightTableCriteriaData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_FREIGHT_TABLE_CRITERIA_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_FREIGHT_TABLE_CRITERIA_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setFreightTableCriteriaId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_FREIGHT_TABLE_CRITERIA (FREIGHT_TABLE_ID,FREIGHT_TABLE_CRITERIA_ID,LOWER_AMOUNT,HIGHER_AMOUNT,FREIGHT_AMOUNT,HANDLING_AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,FREIGHT_HANDLER_ID,FREIGHT_CRITERIA_TYPE_CD,RUNTIME_TYPE_CD,SHORT_DESC,UI_ORDER,CHARGE_CD,DISCOUNT) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getFreightTableId());
        pstmt.setInt(2,pData.getFreightTableCriteriaId());
        pstmt.setBigDecimal(3,pData.getLowerAmount());
        pstmt.setBigDecimal(4,pData.getHigherAmount());
        pstmt.setBigDecimal(5,pData.getFreightAmount());
        pstmt.setBigDecimal(6,pData.getHandlingAmount());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());
        if (pData.getFreightHandlerId() == 0) {
            pstmt.setNull(11, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(11,pData.getFreightHandlerId());
        }

        pstmt.setString(12,pData.getFreightCriteriaTypeCd());
        pstmt.setString(13,pData.getRuntimeTypeCd());
        pstmt.setString(14,pData.getShortDesc());
        pstmt.setInt(15,pData.getUiOrder());
        pstmt.setString(16,pData.getChargeCd());
        pstmt.setBigDecimal(17,pData.getDiscount());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FREIGHT_TABLE_ID="+pData.getFreightTableId());
            log.debug("SQL:   FREIGHT_TABLE_CRITERIA_ID="+pData.getFreightTableCriteriaId());
            log.debug("SQL:   LOWER_AMOUNT="+pData.getLowerAmount());
            log.debug("SQL:   HIGHER_AMOUNT="+pData.getHigherAmount());
            log.debug("SQL:   FREIGHT_AMOUNT="+pData.getFreightAmount());
            log.debug("SQL:   HANDLING_AMOUNT="+pData.getHandlingAmount());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   FREIGHT_HANDLER_ID="+pData.getFreightHandlerId());
            log.debug("SQL:   FREIGHT_CRITERIA_TYPE_CD="+pData.getFreightCriteriaTypeCd());
            log.debug("SQL:   RUNTIME_TYPE_CD="+pData.getRuntimeTypeCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   UI_ORDER="+pData.getUiOrder());
            log.debug("SQL:   CHARGE_CD="+pData.getChargeCd());
            log.debug("SQL:   DISCOUNT="+pData.getDiscount());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setFreightTableCriteriaId(0);
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
     * Updates a FreightTableCriteriaData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FreightTableCriteriaData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FreightTableCriteriaData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_FREIGHT_TABLE_CRITERIA SET FREIGHT_TABLE_ID = ?,LOWER_AMOUNT = ?,HIGHER_AMOUNT = ?,FREIGHT_AMOUNT = ?,HANDLING_AMOUNT = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,FREIGHT_HANDLER_ID = ?,FREIGHT_CRITERIA_TYPE_CD = ?,RUNTIME_TYPE_CD = ?,SHORT_DESC = ?,UI_ORDER = ?,CHARGE_CD = ?,DISCOUNT = ? WHERE FREIGHT_TABLE_CRITERIA_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getFreightTableId());
        pstmt.setBigDecimal(i++,pData.getLowerAmount());
        pstmt.setBigDecimal(i++,pData.getHigherAmount());
        pstmt.setBigDecimal(i++,pData.getFreightAmount());
        pstmt.setBigDecimal(i++,pData.getHandlingAmount());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        if (pData.getFreightHandlerId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getFreightHandlerId());
        }

        pstmt.setString(i++,pData.getFreightCriteriaTypeCd());
        pstmt.setString(i++,pData.getRuntimeTypeCd());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setInt(i++,pData.getUiOrder());
        pstmt.setString(i++,pData.getChargeCd());
        pstmt.setBigDecimal(i++,pData.getDiscount());
        pstmt.setInt(i++,pData.getFreightTableCriteriaId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FREIGHT_TABLE_ID="+pData.getFreightTableId());
            log.debug("SQL:   LOWER_AMOUNT="+pData.getLowerAmount());
            log.debug("SQL:   HIGHER_AMOUNT="+pData.getHigherAmount());
            log.debug("SQL:   FREIGHT_AMOUNT="+pData.getFreightAmount());
            log.debug("SQL:   HANDLING_AMOUNT="+pData.getHandlingAmount());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   FREIGHT_HANDLER_ID="+pData.getFreightHandlerId());
            log.debug("SQL:   FREIGHT_CRITERIA_TYPE_CD="+pData.getFreightCriteriaTypeCd());
            log.debug("SQL:   RUNTIME_TYPE_CD="+pData.getRuntimeTypeCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   UI_ORDER="+pData.getUiOrder());
            log.debug("SQL:   CHARGE_CD="+pData.getChargeCd());
            log.debug("SQL:   DISCOUNT="+pData.getDiscount());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a FreightTableCriteriaData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFreightTableCriteriaId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFreightTableCriteriaId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_FREIGHT_TABLE_CRITERIA WHERE FREIGHT_TABLE_CRITERIA_ID = " + pFreightTableCriteriaId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes FreightTableCriteriaData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_FREIGHT_TABLE_CRITERIA");
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
     * Inserts a FreightTableCriteriaData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FreightTableCriteriaData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, FreightTableCriteriaData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_FREIGHT_TABLE_CRITERIA (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "FREIGHT_TABLE_ID,FREIGHT_TABLE_CRITERIA_ID,LOWER_AMOUNT,HIGHER_AMOUNT,FREIGHT_AMOUNT,HANDLING_AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,FREIGHT_HANDLER_ID,FREIGHT_CRITERIA_TYPE_CD,RUNTIME_TYPE_CD,SHORT_DESC,UI_ORDER,CHARGE_CD,DISCOUNT) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getFreightTableId());
        pstmt.setInt(2+4,pData.getFreightTableCriteriaId());
        pstmt.setBigDecimal(3+4,pData.getLowerAmount());
        pstmt.setBigDecimal(4+4,pData.getHigherAmount());
        pstmt.setBigDecimal(5+4,pData.getFreightAmount());
        pstmt.setBigDecimal(6+4,pData.getHandlingAmount());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());
        if (pData.getFreightHandlerId() == 0) {
            pstmt.setNull(11+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(11+4,pData.getFreightHandlerId());
        }

        pstmt.setString(12+4,pData.getFreightCriteriaTypeCd());
        pstmt.setString(13+4,pData.getRuntimeTypeCd());
        pstmt.setString(14+4,pData.getShortDesc());
        pstmt.setInt(15+4,pData.getUiOrder());
        pstmt.setString(16+4,pData.getChargeCd());
        pstmt.setBigDecimal(17+4,pData.getDiscount());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a FreightTableCriteriaData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FreightTableCriteriaData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new FreightTableCriteriaData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FreightTableCriteriaData insert(Connection pCon, FreightTableCriteriaData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a FreightTableCriteriaData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FreightTableCriteriaData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FreightTableCriteriaData pData, boolean pLogFl)
        throws SQLException {
        FreightTableCriteriaData oldData = null;
        if(pLogFl) {
          int id = pData.getFreightTableCriteriaId();
          try {
          oldData = FreightTableCriteriaDataAccess.select(pCon,id);
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
     * Deletes a FreightTableCriteriaData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFreightTableCriteriaId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFreightTableCriteriaId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_FREIGHT_TABLE_CRITERIA SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FREIGHT_TABLE_CRITERIA d WHERE FREIGHT_TABLE_CRITERIA_ID = " + pFreightTableCriteriaId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pFreightTableCriteriaId);
        return n;
     }

    /**
     * Deletes FreightTableCriteriaData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_FREIGHT_TABLE_CRITERIA SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FREIGHT_TABLE_CRITERIA d ");
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

