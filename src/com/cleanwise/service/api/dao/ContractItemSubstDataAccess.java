
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ContractItemSubstDataAccess
 * Description:  This class is used to build access methods to the CLW_CONTRACT_ITEM_SUBST table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ContractItemSubstData;
import com.cleanwise.service.api.value.ContractItemSubstDataVector;
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
 * <code>ContractItemSubstDataAccess</code>
 */
public class ContractItemSubstDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ContractItemSubstDataAccess.class.getName());

    /** <code>CLW_CONTRACT_ITEM_SUBST</code> table name */
	/* Primary key: CONTRACT_ITEM_SUBST_ID */
	
    public static final String CLW_CONTRACT_ITEM_SUBST = "CLW_CONTRACT_ITEM_SUBST";
    
    /** <code>CONTRACT_ITEM_SUBST_ID</code> CONTRACT_ITEM_SUBST_ID column of table CLW_CONTRACT_ITEM_SUBST */
    public static final String CONTRACT_ITEM_SUBST_ID = "CONTRACT_ITEM_SUBST_ID";
    /** <code>CONTRACT_ID</code> CONTRACT_ID column of table CLW_CONTRACT_ITEM_SUBST */
    public static final String CONTRACT_ID = "CONTRACT_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_CONTRACT_ITEM_SUBST */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>SUBST_ITEM_ID</code> SUBST_ITEM_ID column of table CLW_CONTRACT_ITEM_SUBST */
    public static final String SUBST_ITEM_ID = "SUBST_ITEM_ID";
    /** <code>SUBST_STATUS_CD</code> SUBST_STATUS_CD column of table CLW_CONTRACT_ITEM_SUBST */
    public static final String SUBST_STATUS_CD = "SUBST_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CONTRACT_ITEM_SUBST */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CONTRACT_ITEM_SUBST */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CONTRACT_ITEM_SUBST */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CONTRACT_ITEM_SUBST */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ContractItemSubstDataAccess()
    {
    }

    /**
     * Gets a ContractItemSubstData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pContractItemSubstId The key requested.
     * @return new ContractItemSubstData()
     * @throws            SQLException
     */
    public static ContractItemSubstData select(Connection pCon, int pContractItemSubstId)
        throws SQLException, DataNotFoundException {
        ContractItemSubstData x=null;
        String sql="SELECT CONTRACT_ITEM_SUBST_ID,CONTRACT_ID,ITEM_ID,SUBST_ITEM_ID,SUBST_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CONTRACT_ITEM_SUBST WHERE CONTRACT_ITEM_SUBST_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pContractItemSubstId=" + pContractItemSubstId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pContractItemSubstId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ContractItemSubstData.createValue();
            
            x.setContractItemSubstId(rs.getInt(1));
            x.setContractId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setSubstItemId(rs.getInt(4));
            x.setSubstStatusCd(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CONTRACT_ITEM_SUBST_ID :" + pContractItemSubstId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ContractItemSubstDataVector object that consists
     * of ContractItemSubstData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ContractItemSubstDataVector()
     * @throws            SQLException
     */
    public static ContractItemSubstDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ContractItemSubstData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CONTRACT_ITEM_SUBST.CONTRACT_ITEM_SUBST_ID,CLW_CONTRACT_ITEM_SUBST.CONTRACT_ID,CLW_CONTRACT_ITEM_SUBST.ITEM_ID,CLW_CONTRACT_ITEM_SUBST.SUBST_ITEM_ID,CLW_CONTRACT_ITEM_SUBST.SUBST_STATUS_CD,CLW_CONTRACT_ITEM_SUBST.ADD_DATE,CLW_CONTRACT_ITEM_SUBST.ADD_BY,CLW_CONTRACT_ITEM_SUBST.MOD_DATE,CLW_CONTRACT_ITEM_SUBST.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ContractItemSubstData Object.
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
    *@returns a populated ContractItemSubstData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ContractItemSubstData x = ContractItemSubstData.createValue();
         
         x.setContractItemSubstId(rs.getInt(1+offset));
         x.setContractId(rs.getInt(2+offset));
         x.setItemId(rs.getInt(3+offset));
         x.setSubstItemId(rs.getInt(4+offset));
         x.setSubstStatusCd(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ContractItemSubstData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a ContractItemSubstDataVector object that consists
     * of ContractItemSubstData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ContractItemSubstDataVector()
     * @throws            SQLException
     */
    public static ContractItemSubstDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CONTRACT_ITEM_SUBST_ID,CONTRACT_ID,ITEM_ID,SUBST_ITEM_ID,SUBST_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CONTRACT_ITEM_SUBST");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CONTRACT_ITEM_SUBST.CONTRACT_ITEM_SUBST_ID,CLW_CONTRACT_ITEM_SUBST.CONTRACT_ID,CLW_CONTRACT_ITEM_SUBST.ITEM_ID,CLW_CONTRACT_ITEM_SUBST.SUBST_ITEM_ID,CLW_CONTRACT_ITEM_SUBST.SUBST_STATUS_CD,CLW_CONTRACT_ITEM_SUBST.ADD_DATE,CLW_CONTRACT_ITEM_SUBST.ADD_BY,CLW_CONTRACT_ITEM_SUBST.MOD_DATE,CLW_CONTRACT_ITEM_SUBST.MOD_BY FROM CLW_CONTRACT_ITEM_SUBST");
                where = pCriteria.getSqlClause("CLW_CONTRACT_ITEM_SUBST");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CONTRACT_ITEM_SUBST.equals(otherTable)){
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
        ContractItemSubstDataVector v = new ContractItemSubstDataVector();
        while (rs.next()) {
            ContractItemSubstData x = ContractItemSubstData.createValue();
            
            x.setContractItemSubstId(rs.getInt(1));
            x.setContractId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setSubstItemId(rs.getInt(4));
            x.setSubstStatusCd(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ContractItemSubstDataVector object that consists
     * of ContractItemSubstData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ContractItemSubstData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ContractItemSubstDataVector()
     * @throws            SQLException
     */
    public static ContractItemSubstDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ContractItemSubstDataVector v = new ContractItemSubstDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTRACT_ITEM_SUBST_ID,CONTRACT_ID,ITEM_ID,SUBST_ITEM_ID,SUBST_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CONTRACT_ITEM_SUBST WHERE CONTRACT_ITEM_SUBST_ID IN (");

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
            ContractItemSubstData x=null;
            while (rs.next()) {
                // build the object
                x=ContractItemSubstData.createValue();
                
                x.setContractItemSubstId(rs.getInt(1));
                x.setContractId(rs.getInt(2));
                x.setItemId(rs.getInt(3));
                x.setSubstItemId(rs.getInt(4));
                x.setSubstStatusCd(rs.getString(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setAddBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ContractItemSubstDataVector object of all
     * ContractItemSubstData objects in the database.
     * @param pCon An open database connection.
     * @return new ContractItemSubstDataVector()
     * @throws            SQLException
     */
    public static ContractItemSubstDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CONTRACT_ITEM_SUBST_ID,CONTRACT_ID,ITEM_ID,SUBST_ITEM_ID,SUBST_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CONTRACT_ITEM_SUBST";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ContractItemSubstDataVector v = new ContractItemSubstDataVector();
        ContractItemSubstData x = null;
        while (rs.next()) {
            // build the object
            x = ContractItemSubstData.createValue();
            
            x.setContractItemSubstId(rs.getInt(1));
            x.setContractId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setSubstItemId(rs.getInt(4));
            x.setSubstStatusCd(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ContractItemSubstData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTRACT_ITEM_SUBST_ID FROM CLW_CONTRACT_ITEM_SUBST");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTRACT_ITEM_SUBST");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTRACT_ITEM_SUBST");
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
     * Inserts a ContractItemSubstData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractItemSubstData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ContractItemSubstData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContractItemSubstData insert(Connection pCon, ContractItemSubstData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CONTRACT_ITEM_SUBST_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CONTRACT_ITEM_SUBST_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setContractItemSubstId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CONTRACT_ITEM_SUBST (CONTRACT_ITEM_SUBST_ID,CONTRACT_ID,ITEM_ID,SUBST_ITEM_ID,SUBST_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getContractItemSubstId());
        pstmt.setInt(2,pData.getContractId());
        pstmt.setInt(3,pData.getItemId());
        pstmt.setInt(4,pData.getSubstItemId());
        pstmt.setString(5,pData.getSubstStatusCd());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CONTRACT_ITEM_SUBST_ID="+pData.getContractItemSubstId());
            log.debug("SQL:   CONTRACT_ID="+pData.getContractId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   SUBST_ITEM_ID="+pData.getSubstItemId());
            log.debug("SQL:   SUBST_STATUS_CD="+pData.getSubstStatusCd());
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
        pData.setContractItemSubstId(0);
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
     * Updates a ContractItemSubstData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractItemSubstData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContractItemSubstData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CONTRACT_ITEM_SUBST SET CONTRACT_ID = ?,ITEM_ID = ?,SUBST_ITEM_ID = ?,SUBST_STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE CONTRACT_ITEM_SUBST_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getContractId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setInt(i++,pData.getSubstItemId());
        pstmt.setString(i++,pData.getSubstStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getContractItemSubstId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CONTRACT_ID="+pData.getContractId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   SUBST_ITEM_ID="+pData.getSubstItemId());
            log.debug("SQL:   SUBST_STATUS_CD="+pData.getSubstStatusCd());
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
     * Deletes a ContractItemSubstData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContractItemSubstId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContractItemSubstId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CONTRACT_ITEM_SUBST WHERE CONTRACT_ITEM_SUBST_ID = " + pContractItemSubstId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ContractItemSubstData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CONTRACT_ITEM_SUBST");
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
     * Inserts a ContractItemSubstData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractItemSubstData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ContractItemSubstData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CONTRACT_ITEM_SUBST (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CONTRACT_ITEM_SUBST_ID,CONTRACT_ID,ITEM_ID,SUBST_ITEM_ID,SUBST_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getContractItemSubstId());
        pstmt.setInt(2+4,pData.getContractId());
        pstmt.setInt(3+4,pData.getItemId());
        pstmt.setInt(4+4,pData.getSubstItemId());
        pstmt.setString(5+4,pData.getSubstStatusCd());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ContractItemSubstData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractItemSubstData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ContractItemSubstData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContractItemSubstData insert(Connection pCon, ContractItemSubstData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ContractItemSubstData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractItemSubstData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContractItemSubstData pData, boolean pLogFl)
        throws SQLException {
        ContractItemSubstData oldData = null;
        if(pLogFl) {
          int id = pData.getContractItemSubstId();
          try {
          oldData = ContractItemSubstDataAccess.select(pCon,id);
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
     * Deletes a ContractItemSubstData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContractItemSubstId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContractItemSubstId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CONTRACT_ITEM_SUBST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTRACT_ITEM_SUBST d WHERE CONTRACT_ITEM_SUBST_ID = " + pContractItemSubstId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pContractItemSubstId);
        return n;
     }

    /**
     * Deletes ContractItemSubstData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CONTRACT_ITEM_SUBST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTRACT_ITEM_SUBST d ");
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

