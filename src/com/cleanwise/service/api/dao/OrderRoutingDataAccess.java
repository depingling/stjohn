
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderRoutingDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_ROUTING table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderRoutingData;
import com.cleanwise.service.api.value.OrderRoutingDataVector;
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
 * <code>OrderRoutingDataAccess</code>
 */
public class OrderRoutingDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderRoutingDataAccess.class.getName());

    /** <code>CLW_ORDER_ROUTING</code> table name */
	/* Primary key: ORDER_ROUTING_ID */
	
    public static final String CLW_ORDER_ROUTING = "CLW_ORDER_ROUTING";
    
    /** <code>ORDER_ROUTING_ID</code> ORDER_ROUTING_ID column of table CLW_ORDER_ROUTING */
    public static final String ORDER_ROUTING_ID = "ORDER_ROUTING_ID";
    /** <code>ZIP</code> ZIP column of table CLW_ORDER_ROUTING */
    public static final String ZIP = "ZIP";
    /** <code>DISTRIBUTOR_ID</code> DISTRIBUTOR_ID column of table CLW_ORDER_ROUTING */
    public static final String DISTRIBUTOR_ID = "DISTRIBUTOR_ID";
    /** <code>FREIGHT_HANDLER_ID</code> FREIGHT_HANDLER_ID column of table CLW_ORDER_ROUTING */
    public static final String FREIGHT_HANDLER_ID = "FREIGHT_HANDLER_ID";
    /** <code>ACCOUNT_ID</code> ACCOUNT_ID column of table CLW_ORDER_ROUTING */
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    /** <code>CONTRACT_ID</code> CONTRACT_ID column of table CLW_ORDER_ROUTING */
    public static final String CONTRACT_ID = "CONTRACT_ID";
    /** <code>FINAL_DISTRIBUTOR_ID</code> FINAL_DISTRIBUTOR_ID column of table CLW_ORDER_ROUTING */
    public static final String FINAL_DISTRIBUTOR_ID = "FINAL_DISTRIBUTOR_ID";
    /** <code>FINAL_CONTRACT_ID</code> FINAL_CONTRACT_ID column of table CLW_ORDER_ROUTING */
    public static final String FINAL_CONTRACT_ID = "FINAL_CONTRACT_ID";
    /** <code>FINAL_FREIGHT_HANDLER_ID</code> FINAL_FREIGHT_HANDLER_ID column of table CLW_ORDER_ROUTING */
    public static final String FINAL_FREIGHT_HANDLER_ID = "FINAL_FREIGHT_HANDLER_ID";
    /** <code>LTL_FREIGHT_HANDLER_ID</code> LTL_FREIGHT_HANDLER_ID column of table CLW_ORDER_ROUTING */
    public static final String LTL_FREIGHT_HANDLER_ID = "LTL_FREIGHT_HANDLER_ID";

    /**
     * Constructor.
     */
    public OrderRoutingDataAccess()
    {
    }

    /**
     * Gets a OrderRoutingData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderRoutingId The key requested.
     * @return new OrderRoutingData()
     * @throws            SQLException
     */
    public static OrderRoutingData select(Connection pCon, int pOrderRoutingId)
        throws SQLException, DataNotFoundException {
        OrderRoutingData x=null;
        String sql="SELECT ORDER_ROUTING_ID,ZIP,DISTRIBUTOR_ID,FREIGHT_HANDLER_ID,ACCOUNT_ID,CONTRACT_ID,FINAL_DISTRIBUTOR_ID,FINAL_CONTRACT_ID,FINAL_FREIGHT_HANDLER_ID,LTL_FREIGHT_HANDLER_ID FROM CLW_ORDER_ROUTING WHERE ORDER_ROUTING_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderRoutingId=" + pOrderRoutingId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderRoutingId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderRoutingData.createValue();
            
            x.setOrderRoutingId(rs.getInt(1));
            x.setZip(rs.getString(2));
            x.setDistributorId(rs.getInt(3));
            x.setFreightHandlerId(rs.getInt(4));
            x.setAccountId(rs.getInt(5));
            x.setContractId(rs.getInt(6));
            x.setFinalDistributorId(rs.getInt(7));
            x.setFinalContractId(rs.getInt(8));
            x.setFinalFreightHandlerId(rs.getInt(9));
            x.setLtlFreightHandlerId(rs.getInt(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_ROUTING_ID :" + pOrderRoutingId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderRoutingDataVector object that consists
     * of OrderRoutingData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderRoutingDataVector()
     * @throws            SQLException
     */
    public static OrderRoutingDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderRoutingData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_ROUTING.ORDER_ROUTING_ID,CLW_ORDER_ROUTING.ZIP,CLW_ORDER_ROUTING.DISTRIBUTOR_ID,CLW_ORDER_ROUTING.FREIGHT_HANDLER_ID,CLW_ORDER_ROUTING.ACCOUNT_ID,CLW_ORDER_ROUTING.CONTRACT_ID,CLW_ORDER_ROUTING.FINAL_DISTRIBUTOR_ID,CLW_ORDER_ROUTING.FINAL_CONTRACT_ID,CLW_ORDER_ROUTING.FINAL_FREIGHT_HANDLER_ID,CLW_ORDER_ROUTING.LTL_FREIGHT_HANDLER_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderRoutingData Object.
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
    *@returns a populated OrderRoutingData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderRoutingData x = OrderRoutingData.createValue();
         
         x.setOrderRoutingId(rs.getInt(1+offset));
         x.setZip(rs.getString(2+offset));
         x.setDistributorId(rs.getInt(3+offset));
         x.setFreightHandlerId(rs.getInt(4+offset));
         x.setAccountId(rs.getInt(5+offset));
         x.setContractId(rs.getInt(6+offset));
         x.setFinalDistributorId(rs.getInt(7+offset));
         x.setFinalContractId(rs.getInt(8+offset));
         x.setFinalFreightHandlerId(rs.getInt(9+offset));
         x.setLtlFreightHandlerId(rs.getInt(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderRoutingData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a OrderRoutingDataVector object that consists
     * of OrderRoutingData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderRoutingDataVector()
     * @throws            SQLException
     */
    public static OrderRoutingDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_ROUTING_ID,ZIP,DISTRIBUTOR_ID,FREIGHT_HANDLER_ID,ACCOUNT_ID,CONTRACT_ID,FINAL_DISTRIBUTOR_ID,FINAL_CONTRACT_ID,FINAL_FREIGHT_HANDLER_ID,LTL_FREIGHT_HANDLER_ID FROM CLW_ORDER_ROUTING");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_ROUTING.ORDER_ROUTING_ID,CLW_ORDER_ROUTING.ZIP,CLW_ORDER_ROUTING.DISTRIBUTOR_ID,CLW_ORDER_ROUTING.FREIGHT_HANDLER_ID,CLW_ORDER_ROUTING.ACCOUNT_ID,CLW_ORDER_ROUTING.CONTRACT_ID,CLW_ORDER_ROUTING.FINAL_DISTRIBUTOR_ID,CLW_ORDER_ROUTING.FINAL_CONTRACT_ID,CLW_ORDER_ROUTING.FINAL_FREIGHT_HANDLER_ID,CLW_ORDER_ROUTING.LTL_FREIGHT_HANDLER_ID FROM CLW_ORDER_ROUTING");
                where = pCriteria.getSqlClause("CLW_ORDER_ROUTING");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_ROUTING.equals(otherTable)){
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
        OrderRoutingDataVector v = new OrderRoutingDataVector();
        while (rs.next()) {
            OrderRoutingData x = OrderRoutingData.createValue();
            
            x.setOrderRoutingId(rs.getInt(1));
            x.setZip(rs.getString(2));
            x.setDistributorId(rs.getInt(3));
            x.setFreightHandlerId(rs.getInt(4));
            x.setAccountId(rs.getInt(5));
            x.setContractId(rs.getInt(6));
            x.setFinalDistributorId(rs.getInt(7));
            x.setFinalContractId(rs.getInt(8));
            x.setFinalFreightHandlerId(rs.getInt(9));
            x.setLtlFreightHandlerId(rs.getInt(10));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderRoutingDataVector object that consists
     * of OrderRoutingData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderRoutingData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderRoutingDataVector()
     * @throws            SQLException
     */
    public static OrderRoutingDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderRoutingDataVector v = new OrderRoutingDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_ROUTING_ID,ZIP,DISTRIBUTOR_ID,FREIGHT_HANDLER_ID,ACCOUNT_ID,CONTRACT_ID,FINAL_DISTRIBUTOR_ID,FINAL_CONTRACT_ID,FINAL_FREIGHT_HANDLER_ID,LTL_FREIGHT_HANDLER_ID FROM CLW_ORDER_ROUTING WHERE ORDER_ROUTING_ID IN (");

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
            OrderRoutingData x=null;
            while (rs.next()) {
                // build the object
                x=OrderRoutingData.createValue();
                
                x.setOrderRoutingId(rs.getInt(1));
                x.setZip(rs.getString(2));
                x.setDistributorId(rs.getInt(3));
                x.setFreightHandlerId(rs.getInt(4));
                x.setAccountId(rs.getInt(5));
                x.setContractId(rs.getInt(6));
                x.setFinalDistributorId(rs.getInt(7));
                x.setFinalContractId(rs.getInt(8));
                x.setFinalFreightHandlerId(rs.getInt(9));
                x.setLtlFreightHandlerId(rs.getInt(10));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderRoutingDataVector object of all
     * OrderRoutingData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderRoutingDataVector()
     * @throws            SQLException
     */
    public static OrderRoutingDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_ROUTING_ID,ZIP,DISTRIBUTOR_ID,FREIGHT_HANDLER_ID,ACCOUNT_ID,CONTRACT_ID,FINAL_DISTRIBUTOR_ID,FINAL_CONTRACT_ID,FINAL_FREIGHT_HANDLER_ID,LTL_FREIGHT_HANDLER_ID FROM CLW_ORDER_ROUTING";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderRoutingDataVector v = new OrderRoutingDataVector();
        OrderRoutingData x = null;
        while (rs.next()) {
            // build the object
            x = OrderRoutingData.createValue();
            
            x.setOrderRoutingId(rs.getInt(1));
            x.setZip(rs.getString(2));
            x.setDistributorId(rs.getInt(3));
            x.setFreightHandlerId(rs.getInt(4));
            x.setAccountId(rs.getInt(5));
            x.setContractId(rs.getInt(6));
            x.setFinalDistributorId(rs.getInt(7));
            x.setFinalContractId(rs.getInt(8));
            x.setFinalFreightHandlerId(rs.getInt(9));
            x.setLtlFreightHandlerId(rs.getInt(10));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderRoutingData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_ROUTING_ID FROM CLW_ORDER_ROUTING");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_ROUTING");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_ROUTING");
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
     * Inserts a OrderRoutingData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderRoutingData object to insert.
     * @return new OrderRoutingData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderRoutingData insert(Connection pCon, OrderRoutingData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_ROUTING_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_ROUTING_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderRoutingId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_ROUTING (ORDER_ROUTING_ID,ZIP,DISTRIBUTOR_ID,FREIGHT_HANDLER_ID,ACCOUNT_ID,CONTRACT_ID,FINAL_DISTRIBUTOR_ID,FINAL_CONTRACT_ID,FINAL_FREIGHT_HANDLER_ID,LTL_FREIGHT_HANDLER_ID) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pstmt.setInt(1,pData.getOrderRoutingId());
        pstmt.setString(2,pData.getZip());
        pstmt.setInt(3,pData.getDistributorId());
        pstmt.setInt(4,pData.getFreightHandlerId());
        pstmt.setInt(5,pData.getAccountId());
        pstmt.setInt(6,pData.getContractId());
        pstmt.setInt(7,pData.getFinalDistributorId());
        pstmt.setInt(8,pData.getFinalContractId());
        pstmt.setInt(9,pData.getFinalFreightHandlerId());
        pstmt.setInt(10,pData.getLtlFreightHandlerId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ROUTING_ID="+pData.getOrderRoutingId());
            log.debug("SQL:   ZIP="+pData.getZip());
            log.debug("SQL:   DISTRIBUTOR_ID="+pData.getDistributorId());
            log.debug("SQL:   FREIGHT_HANDLER_ID="+pData.getFreightHandlerId());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   CONTRACT_ID="+pData.getContractId());
            log.debug("SQL:   FINAL_DISTRIBUTOR_ID="+pData.getFinalDistributorId());
            log.debug("SQL:   FINAL_CONTRACT_ID="+pData.getFinalContractId());
            log.debug("SQL:   FINAL_FREIGHT_HANDLER_ID="+pData.getFinalFreightHandlerId());
            log.debug("SQL:   LTL_FREIGHT_HANDLER_ID="+pData.getLtlFreightHandlerId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setOrderRoutingId(0);
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
     * Updates a OrderRoutingData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderRoutingData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderRoutingData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_ROUTING SET ZIP = ?,DISTRIBUTOR_ID = ?,FREIGHT_HANDLER_ID = ?,ACCOUNT_ID = ?,CONTRACT_ID = ?,FINAL_DISTRIBUTOR_ID = ?,FINAL_CONTRACT_ID = ?,FINAL_FREIGHT_HANDLER_ID = ?,LTL_FREIGHT_HANDLER_ID = ? WHERE ORDER_ROUTING_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setString(i++,pData.getZip());
        pstmt.setInt(i++,pData.getDistributorId());
        pstmt.setInt(i++,pData.getFreightHandlerId());
        pstmt.setInt(i++,pData.getAccountId());
        pstmt.setInt(i++,pData.getContractId());
        pstmt.setInt(i++,pData.getFinalDistributorId());
        pstmt.setInt(i++,pData.getFinalContractId());
        pstmt.setInt(i++,pData.getFinalFreightHandlerId());
        pstmt.setInt(i++,pData.getLtlFreightHandlerId());
        pstmt.setInt(i++,pData.getOrderRoutingId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ZIP="+pData.getZip());
            log.debug("SQL:   DISTRIBUTOR_ID="+pData.getDistributorId());
            log.debug("SQL:   FREIGHT_HANDLER_ID="+pData.getFreightHandlerId());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   CONTRACT_ID="+pData.getContractId());
            log.debug("SQL:   FINAL_DISTRIBUTOR_ID="+pData.getFinalDistributorId());
            log.debug("SQL:   FINAL_CONTRACT_ID="+pData.getFinalContractId());
            log.debug("SQL:   FINAL_FREIGHT_HANDLER_ID="+pData.getFinalFreightHandlerId());
            log.debug("SQL:   LTL_FREIGHT_HANDLER_ID="+pData.getLtlFreightHandlerId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a OrderRoutingData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderRoutingId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderRoutingId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_ROUTING WHERE ORDER_ROUTING_ID = " + pOrderRoutingId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderRoutingData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_ROUTING");
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
     * Inserts a OrderRoutingData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderRoutingData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderRoutingData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_ROUTING (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_ROUTING_ID,ZIP,DISTRIBUTOR_ID,FREIGHT_HANDLER_ID,ACCOUNT_ID,CONTRACT_ID,FINAL_DISTRIBUTOR_ID,FINAL_CONTRACT_ID,FINAL_FREIGHT_HANDLER_ID,LTL_FREIGHT_HANDLER_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderRoutingId());
        pstmt.setString(2+4,pData.getZip());
        pstmt.setInt(3+4,pData.getDistributorId());
        pstmt.setInt(4+4,pData.getFreightHandlerId());
        pstmt.setInt(5+4,pData.getAccountId());
        pstmt.setInt(6+4,pData.getContractId());
        pstmt.setInt(7+4,pData.getFinalDistributorId());
        pstmt.setInt(8+4,pData.getFinalContractId());
        pstmt.setInt(9+4,pData.getFinalFreightHandlerId());
        pstmt.setInt(10+4,pData.getLtlFreightHandlerId());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderRoutingData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderRoutingData object to insert.
     * @param pLogFl  Creates record in log table if true
     * @return new OrderRoutingData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderRoutingData insert(Connection pCon, OrderRoutingData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderRoutingData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderRoutingData object to update. 
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderRoutingData pData, boolean pLogFl)
        throws SQLException {
        OrderRoutingData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderRoutingId();
          try {
          oldData = OrderRoutingDataAccess.select(pCon,id);
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
     * Deletes a OrderRoutingData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderRoutingId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderRoutingId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_ROUTING SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_ROUTING d WHERE ORDER_ROUTING_ID = " + pOrderRoutingId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderRoutingId);
        return n;
     }

    /**
     * Deletes OrderRoutingData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_ROUTING SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_ROUTING d ");
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

