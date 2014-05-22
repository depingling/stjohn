
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PreOrderPropertyDataAccess
 * Description:  This class is used to build access methods to the CLW_PRE_ORDER_PROPERTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PreOrderPropertyData;
import com.cleanwise.service.api.value.PreOrderPropertyDataVector;
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
 * <code>PreOrderPropertyDataAccess</code>
 */
public class PreOrderPropertyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PreOrderPropertyDataAccess.class.getName());

    /** <code>CLW_PRE_ORDER_PROPERTY</code> table name */
	/* Primary key: PRE_ORDER_PROPERTY_ID */
	
    public static final String CLW_PRE_ORDER_PROPERTY = "CLW_PRE_ORDER_PROPERTY";
    
    /** <code>PRE_ORDER_PROPERTY_ID</code> PRE_ORDER_PROPERTY_ID column of table CLW_PRE_ORDER_PROPERTY */
    public static final String PRE_ORDER_PROPERTY_ID = "PRE_ORDER_PROPERTY_ID";
    /** <code>PRE_ORDER_ID</code> PRE_ORDER_ID column of table CLW_PRE_ORDER_PROPERTY */
    public static final String PRE_ORDER_ID = "PRE_ORDER_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_PRE_ORDER_PROPERTY */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_PRE_ORDER_PROPERTY */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>ORDER_PROPERTY_TYPE_CD</code> ORDER_PROPERTY_TYPE_CD column of table CLW_PRE_ORDER_PROPERTY */
    public static final String ORDER_PROPERTY_TYPE_CD = "ORDER_PROPERTY_TYPE_CD";

    /**
     * Constructor.
     */
    public PreOrderPropertyDataAccess()
    {
    }

    /**
     * Gets a PreOrderPropertyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPreOrderPropertyId The key requested.
     * @return new PreOrderPropertyData()
     * @throws            SQLException
     */
    public static PreOrderPropertyData select(Connection pCon, int pPreOrderPropertyId)
        throws SQLException, DataNotFoundException {
        PreOrderPropertyData x=null;
        String sql="SELECT PRE_ORDER_PROPERTY_ID,PRE_ORDER_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_TYPE_CD FROM CLW_PRE_ORDER_PROPERTY WHERE PRE_ORDER_PROPERTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPreOrderPropertyId=" + pPreOrderPropertyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPreOrderPropertyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PreOrderPropertyData.createValue();
            
            x.setPreOrderPropertyId(rs.getInt(1));
            x.setPreOrderId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setOrderPropertyTypeCd(rs.getString(5));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PRE_ORDER_PROPERTY_ID :" + pPreOrderPropertyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PreOrderPropertyDataVector object that consists
     * of PreOrderPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PreOrderPropertyDataVector()
     * @throws            SQLException
     */
    public static PreOrderPropertyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PreOrderPropertyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PRE_ORDER_PROPERTY.PRE_ORDER_PROPERTY_ID,CLW_PRE_ORDER_PROPERTY.PRE_ORDER_ID,CLW_PRE_ORDER_PROPERTY.SHORT_DESC,CLW_PRE_ORDER_PROPERTY.CLW_VALUE,CLW_PRE_ORDER_PROPERTY.ORDER_PROPERTY_TYPE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PreOrderPropertyData Object.
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
    *@returns a populated PreOrderPropertyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PreOrderPropertyData x = PreOrderPropertyData.createValue();
         
         x.setPreOrderPropertyId(rs.getInt(1+offset));
         x.setPreOrderId(rs.getInt(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setValue(rs.getString(4+offset));
         x.setOrderPropertyTypeCd(rs.getString(5+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PreOrderPropertyData Object represents.
    */
    public int getColumnCount(){
        return 5;
    }

    /**
     * Gets a PreOrderPropertyDataVector object that consists
     * of PreOrderPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PreOrderPropertyDataVector()
     * @throws            SQLException
     */
    public static PreOrderPropertyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PRE_ORDER_PROPERTY_ID,PRE_ORDER_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_TYPE_CD FROM CLW_PRE_ORDER_PROPERTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PRE_ORDER_PROPERTY.PRE_ORDER_PROPERTY_ID,CLW_PRE_ORDER_PROPERTY.PRE_ORDER_ID,CLW_PRE_ORDER_PROPERTY.SHORT_DESC,CLW_PRE_ORDER_PROPERTY.CLW_VALUE,CLW_PRE_ORDER_PROPERTY.ORDER_PROPERTY_TYPE_CD FROM CLW_PRE_ORDER_PROPERTY");
                where = pCriteria.getSqlClause("CLW_PRE_ORDER_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRE_ORDER_PROPERTY.equals(otherTable)){
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
        PreOrderPropertyDataVector v = new PreOrderPropertyDataVector();
        while (rs.next()) {
            PreOrderPropertyData x = PreOrderPropertyData.createValue();
            
            x.setPreOrderPropertyId(rs.getInt(1));
            x.setPreOrderId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setOrderPropertyTypeCd(rs.getString(5));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a PreOrderPropertyDataVector object that consists
     * of PreOrderPropertyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PreOrderPropertyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PreOrderPropertyDataVector()
     * @throws            SQLException
     */
    public static PreOrderPropertyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PreOrderPropertyDataVector v = new PreOrderPropertyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PRE_ORDER_PROPERTY_ID,PRE_ORDER_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_TYPE_CD FROM CLW_PRE_ORDER_PROPERTY WHERE PRE_ORDER_PROPERTY_ID IN (");

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
            PreOrderPropertyData x=null;
            while (rs.next()) {
                // build the object
                x=PreOrderPropertyData.createValue();
                
                x.setPreOrderPropertyId(rs.getInt(1));
                x.setPreOrderId(rs.getInt(2));
                x.setShortDesc(rs.getString(3));
                x.setValue(rs.getString(4));
                x.setOrderPropertyTypeCd(rs.getString(5));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a PreOrderPropertyDataVector object of all
     * PreOrderPropertyData objects in the database.
     * @param pCon An open database connection.
     * @return new PreOrderPropertyDataVector()
     * @throws            SQLException
     */
    public static PreOrderPropertyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PRE_ORDER_PROPERTY_ID,PRE_ORDER_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_TYPE_CD FROM CLW_PRE_ORDER_PROPERTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PreOrderPropertyDataVector v = new PreOrderPropertyDataVector();
        PreOrderPropertyData x = null;
        while (rs.next()) {
            // build the object
            x = PreOrderPropertyData.createValue();
            
            x.setPreOrderPropertyId(rs.getInt(1));
            x.setPreOrderId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setOrderPropertyTypeCd(rs.getString(5));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * PreOrderPropertyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PRE_ORDER_PROPERTY_ID FROM CLW_PRE_ORDER_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRE_ORDER_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRE_ORDER_PROPERTY");
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
     * Inserts a PreOrderPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderPropertyData object to insert.
     * @return new PreOrderPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PreOrderPropertyData insert(Connection pCon, PreOrderPropertyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PRE_ORDER_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PRE_ORDER_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPreOrderPropertyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PRE_ORDER_PROPERTY (PRE_ORDER_PROPERTY_ID,PRE_ORDER_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_TYPE_CD) VALUES(?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pstmt.setInt(1,pData.getPreOrderPropertyId());
        pstmt.setInt(2,pData.getPreOrderId());
        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getValue());
        pstmt.setString(5,pData.getOrderPropertyTypeCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRE_ORDER_PROPERTY_ID="+pData.getPreOrderPropertyId());
            log.debug("SQL:   PRE_ORDER_ID="+pData.getPreOrderId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ORDER_PROPERTY_TYPE_CD="+pData.getOrderPropertyTypeCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setPreOrderPropertyId(0);
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
     * Updates a PreOrderPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderPropertyData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PreOrderPropertyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PRE_ORDER_PROPERTY SET PRE_ORDER_ID = ?,SHORT_DESC = ?,CLW_VALUE = ?,ORDER_PROPERTY_TYPE_CD = ? WHERE PRE_ORDER_PROPERTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setInt(i++,pData.getPreOrderId());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getOrderPropertyTypeCd());
        pstmt.setInt(i++,pData.getPreOrderPropertyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRE_ORDER_ID="+pData.getPreOrderId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ORDER_PROPERTY_TYPE_CD="+pData.getOrderPropertyTypeCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a PreOrderPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPreOrderPropertyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPreOrderPropertyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PRE_ORDER_PROPERTY WHERE PRE_ORDER_PROPERTY_ID = " + pPreOrderPropertyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PreOrderPropertyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PRE_ORDER_PROPERTY");
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
     * Inserts a PreOrderPropertyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderPropertyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PreOrderPropertyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PRE_ORDER_PROPERTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PRE_ORDER_PROPERTY_ID,PRE_ORDER_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPreOrderPropertyId());
        pstmt.setInt(2+4,pData.getPreOrderId());
        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getValue());
        pstmt.setString(5+4,pData.getOrderPropertyTypeCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PreOrderPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderPropertyData object to insert.
     * @param pLogFl  Creates record in log table if true
     * @return new PreOrderPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PreOrderPropertyData insert(Connection pCon, PreOrderPropertyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PreOrderPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderPropertyData object to update. 
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PreOrderPropertyData pData, boolean pLogFl)
        throws SQLException {
        PreOrderPropertyData oldData = null;
        if(pLogFl) {
          int id = pData.getPreOrderPropertyId();
          try {
          oldData = PreOrderPropertyDataAccess.select(pCon,id);
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
     * Deletes a PreOrderPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPreOrderPropertyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPreOrderPropertyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PRE_ORDER_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRE_ORDER_PROPERTY d WHERE PRE_ORDER_PROPERTY_ID = " + pPreOrderPropertyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPreOrderPropertyId);
        return n;
     }

    /**
     * Deletes PreOrderPropertyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PRE_ORDER_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRE_ORDER_PROPERTY d ");
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

