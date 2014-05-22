
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderGuideDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_GUIDE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
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
 * <code>OrderGuideDataAccess</code>
 */
public class OrderGuideDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderGuideDataAccess.class.getName());

    /** <code>CLW_ORDER_GUIDE</code> table name */
	/* Primary key: ORDER_GUIDE_ID */
	
    public static final String CLW_ORDER_GUIDE = "CLW_ORDER_GUIDE";
    
    /** <code>ORDER_GUIDE_ID</code> ORDER_GUIDE_ID column of table CLW_ORDER_GUIDE */
    public static final String ORDER_GUIDE_ID = "ORDER_GUIDE_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_ORDER_GUIDE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>CATALOG_ID</code> CATALOG_ID column of table CLW_ORDER_GUIDE */
    public static final String CATALOG_ID = "CATALOG_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_ORDER_GUIDE */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>USER_ID</code> USER_ID column of table CLW_ORDER_GUIDE */
    public static final String USER_ID = "USER_ID";
    /** <code>ORDER_GUIDE_TYPE_CD</code> ORDER_GUIDE_TYPE_CD column of table CLW_ORDER_GUIDE */
    public static final String ORDER_GUIDE_TYPE_CD = "ORDER_GUIDE_TYPE_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_GUIDE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_GUIDE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_GUIDE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_GUIDE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>ORDER_BUDGET_TYPE_CD</code> ORDER_BUDGET_TYPE_CD column of table CLW_ORDER_GUIDE */
    public static final String ORDER_BUDGET_TYPE_CD = "ORDER_BUDGET_TYPE_CD";

    /**
     * Constructor.
     */
    public OrderGuideDataAccess()
    {
    }

    /**
     * Gets a OrderGuideData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderGuideId The key requested.
     * @return new OrderGuideData()
     * @throws            SQLException
     */
    public static OrderGuideData select(Connection pCon, int pOrderGuideId)
        throws SQLException, DataNotFoundException {
        OrderGuideData x=null;
        String sql="SELECT ORDER_GUIDE_ID,SHORT_DESC,CATALOG_ID,BUS_ENTITY_ID,USER_ID,ORDER_GUIDE_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_BUDGET_TYPE_CD FROM CLW_ORDER_GUIDE WHERE ORDER_GUIDE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderGuideId=" + pOrderGuideId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderGuideId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderGuideData.createValue();
            
            x.setOrderGuideId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setCatalogId(rs.getInt(3));
            x.setBusEntityId(rs.getInt(4));
            x.setUserId(rs.getInt(5));
            x.setOrderGuideTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setOrderBudgetTypeCd(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_GUIDE_ID :" + pOrderGuideId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderGuideDataVector object that consists
     * of OrderGuideData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderGuideDataVector()
     * @throws            SQLException
     */
    public static OrderGuideDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderGuideData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_GUIDE.ORDER_GUIDE_ID,CLW_ORDER_GUIDE.SHORT_DESC,CLW_ORDER_GUIDE.CATALOG_ID,CLW_ORDER_GUIDE.BUS_ENTITY_ID,CLW_ORDER_GUIDE.USER_ID,CLW_ORDER_GUIDE.ORDER_GUIDE_TYPE_CD,CLW_ORDER_GUIDE.ADD_DATE,CLW_ORDER_GUIDE.ADD_BY,CLW_ORDER_GUIDE.MOD_DATE,CLW_ORDER_GUIDE.MOD_BY,CLW_ORDER_GUIDE.ORDER_BUDGET_TYPE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderGuideData Object.
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
    *@returns a populated OrderGuideData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderGuideData x = OrderGuideData.createValue();
         
         x.setOrderGuideId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setCatalogId(rs.getInt(3+offset));
         x.setBusEntityId(rs.getInt(4+offset));
         x.setUserId(rs.getInt(5+offset));
         x.setOrderGuideTypeCd(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         x.setOrderBudgetTypeCd(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderGuideData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a OrderGuideDataVector object that consists
     * of OrderGuideData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderGuideDataVector()
     * @throws            SQLException
     */
    public static OrderGuideDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_GUIDE_ID,SHORT_DESC,CATALOG_ID,BUS_ENTITY_ID,USER_ID,ORDER_GUIDE_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_BUDGET_TYPE_CD FROM CLW_ORDER_GUIDE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_GUIDE.ORDER_GUIDE_ID,CLW_ORDER_GUIDE.SHORT_DESC,CLW_ORDER_GUIDE.CATALOG_ID,CLW_ORDER_GUIDE.BUS_ENTITY_ID,CLW_ORDER_GUIDE.USER_ID,CLW_ORDER_GUIDE.ORDER_GUIDE_TYPE_CD,CLW_ORDER_GUIDE.ADD_DATE,CLW_ORDER_GUIDE.ADD_BY,CLW_ORDER_GUIDE.MOD_DATE,CLW_ORDER_GUIDE.MOD_BY,CLW_ORDER_GUIDE.ORDER_BUDGET_TYPE_CD FROM CLW_ORDER_GUIDE");
                where = pCriteria.getSqlClause("CLW_ORDER_GUIDE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_GUIDE.equals(otherTable)){
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
        log.info("SQL: " + sql);
        Statement stmt = pCon.createStatement();
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
              stmt.setMaxRows(pMaxRows);
        }
        ResultSet rs=stmt.executeQuery(sql);
        OrderGuideDataVector v = new OrderGuideDataVector();
        while (rs.next()) {
            OrderGuideData x = OrderGuideData.createValue();
            
            x.setOrderGuideId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setCatalogId(rs.getInt(3));
            x.setBusEntityId(rs.getInt(4));
            x.setUserId(rs.getInt(5));
            x.setOrderGuideTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setOrderBudgetTypeCd(rs.getString(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderGuideDataVector object that consists
     * of OrderGuideData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderGuideData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderGuideDataVector()
     * @throws            SQLException
     */
    public static OrderGuideDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderGuideDataVector v = new OrderGuideDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_GUIDE_ID,SHORT_DESC,CATALOG_ID,BUS_ENTITY_ID,USER_ID,ORDER_GUIDE_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_BUDGET_TYPE_CD FROM CLW_ORDER_GUIDE WHERE ORDER_GUIDE_ID IN (");

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
            OrderGuideData x=null;
            while (rs.next()) {
                // build the object
                x=OrderGuideData.createValue();
                
                x.setOrderGuideId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setCatalogId(rs.getInt(3));
                x.setBusEntityId(rs.getInt(4));
                x.setUserId(rs.getInt(5));
                x.setOrderGuideTypeCd(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                x.setOrderBudgetTypeCd(rs.getString(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderGuideDataVector object of all
     * OrderGuideData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderGuideDataVector()
     * @throws            SQLException
     */
    public static OrderGuideDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_GUIDE_ID,SHORT_DESC,CATALOG_ID,BUS_ENTITY_ID,USER_ID,ORDER_GUIDE_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_BUDGET_TYPE_CD FROM CLW_ORDER_GUIDE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderGuideDataVector v = new OrderGuideDataVector();
        OrderGuideData x = null;
        while (rs.next()) {
            // build the object
            x = OrderGuideData.createValue();
            
            x.setOrderGuideId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setCatalogId(rs.getInt(3));
            x.setBusEntityId(rs.getInt(4));
            x.setUserId(rs.getInt(5));
            x.setOrderGuideTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setOrderBudgetTypeCd(rs.getString(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderGuideData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT ORDER_GUIDE_ID FROM CLW_ORDER_GUIDE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT ORDER_GUIDE_ID FROM CLW_ORDER_GUIDE");
                where = pCriteria.getSqlClause("CLW_ORDER_GUIDE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_GUIDE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_GUIDE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_GUIDE");
                where = pCriteria.getSqlClause("CLW_ORDER_GUIDE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_GUIDE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_GUIDE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_GUIDE");
                where = pCriteria.getSqlClause("CLW_ORDER_GUIDE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_GUIDE.equals(otherTable)){
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
     * Inserts a OrderGuideData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderGuideData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderGuideData insert(Connection pCon, OrderGuideData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_GUIDE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_GUIDE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderGuideId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_GUIDE (ORDER_GUIDE_ID,SHORT_DESC,CATALOG_ID,BUS_ENTITY_ID,USER_ID,ORDER_GUIDE_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_BUDGET_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderGuideId());
        pstmt.setString(2,pData.getShortDesc());
        if (pData.getCatalogId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getCatalogId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4,pData.getBusEntityId());
        }

        if (pData.getUserId() == 0) {
            pstmt.setNull(5, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(5,pData.getUserId());
        }

        pstmt.setString(6,pData.getOrderGuideTypeCd());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());
        pstmt.setString(11,pData.getOrderBudgetTypeCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_GUIDE_ID="+pData.getOrderGuideId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ORDER_GUIDE_TYPE_CD="+pData.getOrderGuideTypeCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ORDER_BUDGET_TYPE_CD="+pData.getOrderBudgetTypeCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setOrderGuideId(0);
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
     * Updates a OrderGuideData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderGuideData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_GUIDE SET SHORT_DESC = ?,CATALOG_ID = ?,BUS_ENTITY_ID = ?,USER_ID = ?,ORDER_GUIDE_TYPE_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,ORDER_BUDGET_TYPE_CD = ? WHERE ORDER_GUIDE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        if (pData.getCatalogId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getCatalogId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        if (pData.getUserId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getUserId());
        }

        pstmt.setString(i++,pData.getOrderGuideTypeCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getOrderBudgetTypeCd());
        pstmt.setInt(i++,pData.getOrderGuideId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ORDER_GUIDE_TYPE_CD="+pData.getOrderGuideTypeCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ORDER_BUDGET_TYPE_CD="+pData.getOrderBudgetTypeCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a OrderGuideData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderGuideId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderGuideId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_GUIDE WHERE ORDER_GUIDE_ID = " + pOrderGuideId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderGuideData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_GUIDE");
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
     * Inserts a OrderGuideData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderGuideData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_GUIDE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_GUIDE_ID,SHORT_DESC,CATALOG_ID,BUS_ENTITY_ID,USER_ID,ORDER_GUIDE_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_BUDGET_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderGuideId());
        pstmt.setString(2+4,pData.getShortDesc());
        if (pData.getCatalogId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getCatalogId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(4+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4+4,pData.getBusEntityId());
        }

        if (pData.getUserId() == 0) {
            pstmt.setNull(5+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(5+4,pData.getUserId());
        }

        pstmt.setString(6+4,pData.getOrderGuideTypeCd());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());
        pstmt.setString(11+4,pData.getOrderBudgetTypeCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderGuideData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderGuideData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderGuideData insert(Connection pCon, OrderGuideData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderGuideData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderGuideData pData, boolean pLogFl)
        throws SQLException {
        OrderGuideData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderGuideId();
          try {
          oldData = OrderGuideDataAccess.select(pCon,id);
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
     * Deletes a OrderGuideData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderGuideId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderGuideId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_GUIDE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_GUIDE d WHERE ORDER_GUIDE_ID = " + pOrderGuideId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderGuideId);
        return n;
     }

    /**
     * Deletes OrderGuideData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_GUIDE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_GUIDE d ");
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

