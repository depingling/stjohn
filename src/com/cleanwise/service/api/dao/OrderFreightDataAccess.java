
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderFreightDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_FREIGHT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderFreightData;
import com.cleanwise.service.api.value.OrderFreightDataVector;
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
 * <code>OrderFreightDataAccess</code>
 */
public class OrderFreightDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderFreightDataAccess.class.getName());

    /** <code>CLW_ORDER_FREIGHT</code> table name */
	/* Primary key: ORDER_FREIGHT_ID */
	
    public static final String CLW_ORDER_FREIGHT = "CLW_ORDER_FREIGHT";
    
    /** <code>ORDER_FREIGHT_ID</code> ORDER_FREIGHT_ID column of table CLW_ORDER_FREIGHT */
    public static final String ORDER_FREIGHT_ID = "ORDER_FREIGHT_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_ORDER_FREIGHT */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_ORDER_FREIGHT */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>FREIGHT_TYPE_CD</code> FREIGHT_TYPE_CD column of table CLW_ORDER_FREIGHT */
    public static final String FREIGHT_TYPE_CD = "FREIGHT_TYPE_CD";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_ORDER_FREIGHT */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>AMOUNT</code> AMOUNT column of table CLW_ORDER_FREIGHT */
    public static final String AMOUNT = "AMOUNT";
    /** <code>FREIGHT_HANDLER_ID</code> FREIGHT_HANDLER_ID column of table CLW_ORDER_FREIGHT */
    public static final String FREIGHT_HANDLER_ID = "FREIGHT_HANDLER_ID";

    /**
     * Constructor.
     */
    public OrderFreightDataAccess()
    {
    }

    /**
     * Gets a OrderFreightData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderFreightId The key requested.
     * @return new OrderFreightData()
     * @throws            SQLException
     */
    public static OrderFreightData select(Connection pCon, int pOrderFreightId)
        throws SQLException, DataNotFoundException {
        OrderFreightData x=null;
        String sql="SELECT ORDER_FREIGHT_ID,ORDER_ID,BUS_ENTITY_ID,FREIGHT_TYPE_CD,SHORT_DESC,AMOUNT,FREIGHT_HANDLER_ID FROM CLW_ORDER_FREIGHT WHERE ORDER_FREIGHT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderFreightId=" + pOrderFreightId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderFreightId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderFreightData.createValue();
            
            x.setOrderFreightId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setFreightTypeCd(rs.getString(4));
            x.setShortDesc(rs.getString(5));
            x.setAmount(rs.getBigDecimal(6));
            x.setFreightHandlerId(rs.getInt(7));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_FREIGHT_ID :" + pOrderFreightId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderFreightDataVector object that consists
     * of OrderFreightData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderFreightDataVector()
     * @throws            SQLException
     */
    public static OrderFreightDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderFreightData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_FREIGHT.ORDER_FREIGHT_ID,CLW_ORDER_FREIGHT.ORDER_ID,CLW_ORDER_FREIGHT.BUS_ENTITY_ID,CLW_ORDER_FREIGHT.FREIGHT_TYPE_CD,CLW_ORDER_FREIGHT.SHORT_DESC,CLW_ORDER_FREIGHT.AMOUNT,CLW_ORDER_FREIGHT.FREIGHT_HANDLER_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderFreightData Object.
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
    *@returns a populated OrderFreightData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderFreightData x = OrderFreightData.createValue();
         
         x.setOrderFreightId(rs.getInt(1+offset));
         x.setOrderId(rs.getInt(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setFreightTypeCd(rs.getString(4+offset));
         x.setShortDesc(rs.getString(5+offset));
         x.setAmount(rs.getBigDecimal(6+offset));
         x.setFreightHandlerId(rs.getInt(7+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderFreightData Object represents.
    */
    public int getColumnCount(){
        return 7;
    }

    /**
     * Gets a OrderFreightDataVector object that consists
     * of OrderFreightData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderFreightDataVector()
     * @throws            SQLException
     */
    public static OrderFreightDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_FREIGHT_ID,ORDER_ID,BUS_ENTITY_ID,FREIGHT_TYPE_CD,SHORT_DESC,AMOUNT,FREIGHT_HANDLER_ID FROM CLW_ORDER_FREIGHT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_FREIGHT.ORDER_FREIGHT_ID,CLW_ORDER_FREIGHT.ORDER_ID,CLW_ORDER_FREIGHT.BUS_ENTITY_ID,CLW_ORDER_FREIGHT.FREIGHT_TYPE_CD,CLW_ORDER_FREIGHT.SHORT_DESC,CLW_ORDER_FREIGHT.AMOUNT,CLW_ORDER_FREIGHT.FREIGHT_HANDLER_ID FROM CLW_ORDER_FREIGHT");
                where = pCriteria.getSqlClause("CLW_ORDER_FREIGHT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_FREIGHT.equals(otherTable)){
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
        OrderFreightDataVector v = new OrderFreightDataVector();
        while (rs.next()) {
            OrderFreightData x = OrderFreightData.createValue();
            
            x.setOrderFreightId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setFreightTypeCd(rs.getString(4));
            x.setShortDesc(rs.getString(5));
            x.setAmount(rs.getBigDecimal(6));
            x.setFreightHandlerId(rs.getInt(7));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderFreightDataVector object that consists
     * of OrderFreightData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderFreightData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderFreightDataVector()
     * @throws            SQLException
     */
    public static OrderFreightDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderFreightDataVector v = new OrderFreightDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_FREIGHT_ID,ORDER_ID,BUS_ENTITY_ID,FREIGHT_TYPE_CD,SHORT_DESC,AMOUNT,FREIGHT_HANDLER_ID FROM CLW_ORDER_FREIGHT WHERE ORDER_FREIGHT_ID IN (");

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
            OrderFreightData x=null;
            while (rs.next()) {
                // build the object
                x=OrderFreightData.createValue();
                
                x.setOrderFreightId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setBusEntityId(rs.getInt(3));
                x.setFreightTypeCd(rs.getString(4));
                x.setShortDesc(rs.getString(5));
                x.setAmount(rs.getBigDecimal(6));
                x.setFreightHandlerId(rs.getInt(7));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderFreightDataVector object of all
     * OrderFreightData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderFreightDataVector()
     * @throws            SQLException
     */
    public static OrderFreightDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_FREIGHT_ID,ORDER_ID,BUS_ENTITY_ID,FREIGHT_TYPE_CD,SHORT_DESC,AMOUNT,FREIGHT_HANDLER_ID FROM CLW_ORDER_FREIGHT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderFreightDataVector v = new OrderFreightDataVector();
        OrderFreightData x = null;
        while (rs.next()) {
            // build the object
            x = OrderFreightData.createValue();
            
            x.setOrderFreightId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setFreightTypeCd(rs.getString(4));
            x.setShortDesc(rs.getString(5));
            x.setAmount(rs.getBigDecimal(6));
            x.setFreightHandlerId(rs.getInt(7));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderFreightData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_FREIGHT_ID FROM CLW_ORDER_FREIGHT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_FREIGHT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_FREIGHT");
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
     * Inserts a OrderFreightData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderFreightData object to insert.
     * @return new OrderFreightData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderFreightData insert(Connection pCon, OrderFreightData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_FREIGHT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_FREIGHT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderFreightId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_FREIGHT (ORDER_FREIGHT_ID,ORDER_ID,BUS_ENTITY_ID,FREIGHT_TYPE_CD,SHORT_DESC,AMOUNT,FREIGHT_HANDLER_ID) VALUES(?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pstmt.setInt(1,pData.getOrderFreightId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getOrderId());
        }

        pstmt.setInt(3,pData.getBusEntityId());
        pstmt.setString(4,pData.getFreightTypeCd());
        pstmt.setString(5,pData.getShortDesc());
        pstmt.setBigDecimal(6,pData.getAmount());
        pstmt.setInt(7,pData.getFreightHandlerId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_FREIGHT_ID="+pData.getOrderFreightId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   FREIGHT_TYPE_CD="+pData.getFreightTypeCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   AMOUNT="+pData.getAmount());
            log.debug("SQL:   FREIGHT_HANDLER_ID="+pData.getFreightHandlerId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setOrderFreightId(0);
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
     * Updates a OrderFreightData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderFreightData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderFreightData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_FREIGHT SET ORDER_ID = ?,BUS_ENTITY_ID = ?,FREIGHT_TYPE_CD = ?,SHORT_DESC = ?,AMOUNT = ?,FREIGHT_HANDLER_ID = ? WHERE ORDER_FREIGHT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        if (pData.getOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getOrderId());
        }

        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setString(i++,pData.getFreightTypeCd());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setBigDecimal(i++,pData.getAmount());
        pstmt.setInt(i++,pData.getFreightHandlerId());
        pstmt.setInt(i++,pData.getOrderFreightId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   FREIGHT_TYPE_CD="+pData.getFreightTypeCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   AMOUNT="+pData.getAmount());
            log.debug("SQL:   FREIGHT_HANDLER_ID="+pData.getFreightHandlerId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a OrderFreightData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderFreightId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderFreightId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_FREIGHT WHERE ORDER_FREIGHT_ID = " + pOrderFreightId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderFreightData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_FREIGHT");
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
     * Inserts a OrderFreightData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderFreightData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderFreightData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_FREIGHT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_FREIGHT_ID,ORDER_ID,BUS_ENTITY_ID,FREIGHT_TYPE_CD,SHORT_DESC,AMOUNT,FREIGHT_HANDLER_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderFreightId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getOrderId());
        }

        pstmt.setInt(3+4,pData.getBusEntityId());
        pstmt.setString(4+4,pData.getFreightTypeCd());
        pstmt.setString(5+4,pData.getShortDesc());
        pstmt.setBigDecimal(6+4,pData.getAmount());
        pstmt.setInt(7+4,pData.getFreightHandlerId());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderFreightData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderFreightData object to insert.
     * @param pLogFl  Creates record in log table if true
     * @return new OrderFreightData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderFreightData insert(Connection pCon, OrderFreightData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderFreightData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderFreightData object to update. 
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderFreightData pData, boolean pLogFl)
        throws SQLException {
        OrderFreightData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderFreightId();
          try {
          oldData = OrderFreightDataAccess.select(pCon,id);
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
     * Deletes a OrderFreightData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderFreightId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderFreightId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_FREIGHT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_FREIGHT d WHERE ORDER_FREIGHT_ID = " + pOrderFreightId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderFreightId);
        return n;
     }

    /**
     * Deletes OrderFreightData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_FREIGHT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_FREIGHT d ");
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

