
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        KnowledgePropertyDataAccess
 * Description:  This class is used to build access methods to the CLW_KNOWLEDGE_PROPERTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.KnowledgePropertyData;
import com.cleanwise.service.api.value.KnowledgePropertyDataVector;
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
 * <code>KnowledgePropertyDataAccess</code>
 */
public class KnowledgePropertyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(KnowledgePropertyDataAccess.class.getName());

    /** <code>CLW_KNOWLEDGE_PROPERTY</code> table name */
	/* Primary key: KNOWLEDGE_PROPERTY_ID */
	
    public static final String CLW_KNOWLEDGE_PROPERTY = "CLW_KNOWLEDGE_PROPERTY";
    
    /** <code>KNOWLEDGE_PROPERTY_ID</code> KNOWLEDGE_PROPERTY_ID column of table CLW_KNOWLEDGE_PROPERTY */
    public static final String KNOWLEDGE_PROPERTY_ID = "KNOWLEDGE_PROPERTY_ID";
    /** <code>KNOWLEDGE_ID</code> KNOWLEDGE_ID column of table CLW_KNOWLEDGE_PROPERTY */
    public static final String KNOWLEDGE_ID = "KNOWLEDGE_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_KNOWLEDGE_PROPERTY */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_KNOWLEDGE_PROPERTY */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>KNOWLEDGE_PROPERTY_STATUS_CD</code> KNOWLEDGE_PROPERTY_STATUS_CD column of table CLW_KNOWLEDGE_PROPERTY */
    public static final String KNOWLEDGE_PROPERTY_STATUS_CD = "KNOWLEDGE_PROPERTY_STATUS_CD";
    /** <code>KNOWLEDGE_PROPERTY_TYPE_CD</code> KNOWLEDGE_PROPERTY_TYPE_CD column of table CLW_KNOWLEDGE_PROPERTY */
    public static final String KNOWLEDGE_PROPERTY_TYPE_CD = "KNOWLEDGE_PROPERTY_TYPE_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_KNOWLEDGE_PROPERTY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_TIME</code> ADD_TIME column of table CLW_KNOWLEDGE_PROPERTY */
    public static final String ADD_TIME = "ADD_TIME";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_KNOWLEDGE_PROPERTY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_KNOWLEDGE_PROPERTY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_KNOWLEDGE_PROPERTY */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public KnowledgePropertyDataAccess()
    {
    }

    /**
     * Gets a KnowledgePropertyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pKnowledgePropertyId The key requested.
     * @return new KnowledgePropertyData()
     * @throws            SQLException
     */
    public static KnowledgePropertyData select(Connection pCon, int pKnowledgePropertyId)
        throws SQLException, DataNotFoundException {
        KnowledgePropertyData x=null;
        String sql="SELECT KNOWLEDGE_PROPERTY_ID,KNOWLEDGE_ID,SHORT_DESC,CLW_VALUE,KNOWLEDGE_PROPERTY_STATUS_CD,KNOWLEDGE_PROPERTY_TYPE_CD,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY FROM CLW_KNOWLEDGE_PROPERTY WHERE KNOWLEDGE_PROPERTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pKnowledgePropertyId=" + pKnowledgePropertyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pKnowledgePropertyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=KnowledgePropertyData.createValue();
            
            x.setKnowledgePropertyId(rs.getInt(1));
            x.setKnowledgeId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setKnowledgePropertyStatusCd(rs.getString(5));
            x.setKnowledgePropertyTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddTime(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("KNOWLEDGE_PROPERTY_ID :" + pKnowledgePropertyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a KnowledgePropertyDataVector object that consists
     * of KnowledgePropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new KnowledgePropertyDataVector()
     * @throws            SQLException
     */
    public static KnowledgePropertyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a KnowledgePropertyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_KNOWLEDGE_PROPERTY.KNOWLEDGE_PROPERTY_ID,CLW_KNOWLEDGE_PROPERTY.KNOWLEDGE_ID,CLW_KNOWLEDGE_PROPERTY.SHORT_DESC,CLW_KNOWLEDGE_PROPERTY.CLW_VALUE,CLW_KNOWLEDGE_PROPERTY.KNOWLEDGE_PROPERTY_STATUS_CD,CLW_KNOWLEDGE_PROPERTY.KNOWLEDGE_PROPERTY_TYPE_CD,CLW_KNOWLEDGE_PROPERTY.ADD_DATE,CLW_KNOWLEDGE_PROPERTY.ADD_TIME,CLW_KNOWLEDGE_PROPERTY.ADD_BY,CLW_KNOWLEDGE_PROPERTY.MOD_DATE,CLW_KNOWLEDGE_PROPERTY.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated KnowledgePropertyData Object.
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
    *@returns a populated KnowledgePropertyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         KnowledgePropertyData x = KnowledgePropertyData.createValue();
         
         x.setKnowledgePropertyId(rs.getInt(1+offset));
         x.setKnowledgeId(rs.getInt(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setValue(rs.getString(4+offset));
         x.setKnowledgePropertyStatusCd(rs.getString(5+offset));
         x.setKnowledgePropertyTypeCd(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddTime(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the KnowledgePropertyData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a KnowledgePropertyDataVector object that consists
     * of KnowledgePropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new KnowledgePropertyDataVector()
     * @throws            SQLException
     */
    public static KnowledgePropertyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT KNOWLEDGE_PROPERTY_ID,KNOWLEDGE_ID,SHORT_DESC,CLW_VALUE,KNOWLEDGE_PROPERTY_STATUS_CD,KNOWLEDGE_PROPERTY_TYPE_CD,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY FROM CLW_KNOWLEDGE_PROPERTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_KNOWLEDGE_PROPERTY.KNOWLEDGE_PROPERTY_ID,CLW_KNOWLEDGE_PROPERTY.KNOWLEDGE_ID,CLW_KNOWLEDGE_PROPERTY.SHORT_DESC,CLW_KNOWLEDGE_PROPERTY.CLW_VALUE,CLW_KNOWLEDGE_PROPERTY.KNOWLEDGE_PROPERTY_STATUS_CD,CLW_KNOWLEDGE_PROPERTY.KNOWLEDGE_PROPERTY_TYPE_CD,CLW_KNOWLEDGE_PROPERTY.ADD_DATE,CLW_KNOWLEDGE_PROPERTY.ADD_TIME,CLW_KNOWLEDGE_PROPERTY.ADD_BY,CLW_KNOWLEDGE_PROPERTY.MOD_DATE,CLW_KNOWLEDGE_PROPERTY.MOD_BY FROM CLW_KNOWLEDGE_PROPERTY");
                where = pCriteria.getSqlClause("CLW_KNOWLEDGE_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_KNOWLEDGE_PROPERTY.equals(otherTable)){
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
        KnowledgePropertyDataVector v = new KnowledgePropertyDataVector();
        while (rs.next()) {
            KnowledgePropertyData x = KnowledgePropertyData.createValue();
            
            x.setKnowledgePropertyId(rs.getInt(1));
            x.setKnowledgeId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setKnowledgePropertyStatusCd(rs.getString(5));
            x.setKnowledgePropertyTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddTime(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a KnowledgePropertyDataVector object that consists
     * of KnowledgePropertyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for KnowledgePropertyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new KnowledgePropertyDataVector()
     * @throws            SQLException
     */
    public static KnowledgePropertyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        KnowledgePropertyDataVector v = new KnowledgePropertyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT KNOWLEDGE_PROPERTY_ID,KNOWLEDGE_ID,SHORT_DESC,CLW_VALUE,KNOWLEDGE_PROPERTY_STATUS_CD,KNOWLEDGE_PROPERTY_TYPE_CD,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY FROM CLW_KNOWLEDGE_PROPERTY WHERE KNOWLEDGE_PROPERTY_ID IN (");

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
            KnowledgePropertyData x=null;
            while (rs.next()) {
                // build the object
                x=KnowledgePropertyData.createValue();
                
                x.setKnowledgePropertyId(rs.getInt(1));
                x.setKnowledgeId(rs.getInt(2));
                x.setShortDesc(rs.getString(3));
                x.setValue(rs.getString(4));
                x.setKnowledgePropertyStatusCd(rs.getString(5));
                x.setKnowledgePropertyTypeCd(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddTime(rs.getTimestamp(8));
                x.setAddBy(rs.getString(9));
                x.setModDate(rs.getTimestamp(10));
                x.setModBy(rs.getString(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a KnowledgePropertyDataVector object of all
     * KnowledgePropertyData objects in the database.
     * @param pCon An open database connection.
     * @return new KnowledgePropertyDataVector()
     * @throws            SQLException
     */
    public static KnowledgePropertyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT KNOWLEDGE_PROPERTY_ID,KNOWLEDGE_ID,SHORT_DESC,CLW_VALUE,KNOWLEDGE_PROPERTY_STATUS_CD,KNOWLEDGE_PROPERTY_TYPE_CD,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY FROM CLW_KNOWLEDGE_PROPERTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        KnowledgePropertyDataVector v = new KnowledgePropertyDataVector();
        KnowledgePropertyData x = null;
        while (rs.next()) {
            // build the object
            x = KnowledgePropertyData.createValue();
            
            x.setKnowledgePropertyId(rs.getInt(1));
            x.setKnowledgeId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setKnowledgePropertyStatusCd(rs.getString(5));
            x.setKnowledgePropertyTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddTime(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * KnowledgePropertyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT KNOWLEDGE_PROPERTY_ID FROM CLW_KNOWLEDGE_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_KNOWLEDGE_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_KNOWLEDGE_PROPERTY");
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
     * Inserts a KnowledgePropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgePropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new KnowledgePropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static KnowledgePropertyData insert(Connection pCon, KnowledgePropertyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_KNOWLEDGE_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_KNOWLEDGE_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setKnowledgePropertyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_KNOWLEDGE_PROPERTY (KNOWLEDGE_PROPERTY_ID,KNOWLEDGE_ID,SHORT_DESC,CLW_VALUE,KNOWLEDGE_PROPERTY_STATUS_CD,KNOWLEDGE_PROPERTY_TYPE_CD,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getKnowledgePropertyId());
        if (pData.getKnowledgeId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getKnowledgeId());
        }

        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getValue());
        pstmt.setString(5,pData.getKnowledgePropertyStatusCd());
        pstmt.setString(6,pData.getKnowledgePropertyTypeCd());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddTime()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   KNOWLEDGE_PROPERTY_ID="+pData.getKnowledgePropertyId());
            log.debug("SQL:   KNOWLEDGE_ID="+pData.getKnowledgeId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   KNOWLEDGE_PROPERTY_STATUS_CD="+pData.getKnowledgePropertyStatusCd());
            log.debug("SQL:   KNOWLEDGE_PROPERTY_TYPE_CD="+pData.getKnowledgePropertyTypeCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_TIME="+pData.getAddTime());
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
        pData.setKnowledgePropertyId(0);
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
     * Updates a KnowledgePropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgePropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, KnowledgePropertyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_KNOWLEDGE_PROPERTY SET KNOWLEDGE_ID = ?,SHORT_DESC = ?,CLW_VALUE = ?,KNOWLEDGE_PROPERTY_STATUS_CD = ?,KNOWLEDGE_PROPERTY_TYPE_CD = ?,ADD_DATE = ?,ADD_TIME = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE KNOWLEDGE_PROPERTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getKnowledgeId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getKnowledgeId());
        }

        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getKnowledgePropertyStatusCd());
        pstmt.setString(i++,pData.getKnowledgePropertyTypeCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddTime()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getKnowledgePropertyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   KNOWLEDGE_ID="+pData.getKnowledgeId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   KNOWLEDGE_PROPERTY_STATUS_CD="+pData.getKnowledgePropertyStatusCd());
            log.debug("SQL:   KNOWLEDGE_PROPERTY_TYPE_CD="+pData.getKnowledgePropertyTypeCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_TIME="+pData.getAddTime());
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
     * Deletes a KnowledgePropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pKnowledgePropertyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pKnowledgePropertyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_KNOWLEDGE_PROPERTY WHERE KNOWLEDGE_PROPERTY_ID = " + pKnowledgePropertyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes KnowledgePropertyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_KNOWLEDGE_PROPERTY");
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
     * Inserts a KnowledgePropertyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgePropertyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, KnowledgePropertyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_KNOWLEDGE_PROPERTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "KNOWLEDGE_PROPERTY_ID,KNOWLEDGE_ID,SHORT_DESC,CLW_VALUE,KNOWLEDGE_PROPERTY_STATUS_CD,KNOWLEDGE_PROPERTY_TYPE_CD,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getKnowledgePropertyId());
        if (pData.getKnowledgeId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getKnowledgeId());
        }

        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getValue());
        pstmt.setString(5+4,pData.getKnowledgePropertyStatusCd());
        pstmt.setString(6+4,pData.getKnowledgePropertyTypeCd());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddTime()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a KnowledgePropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgePropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new KnowledgePropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static KnowledgePropertyData insert(Connection pCon, KnowledgePropertyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a KnowledgePropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgePropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, KnowledgePropertyData pData, boolean pLogFl)
        throws SQLException {
        KnowledgePropertyData oldData = null;
        if(pLogFl) {
          int id = pData.getKnowledgePropertyId();
          try {
          oldData = KnowledgePropertyDataAccess.select(pCon,id);
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
     * Deletes a KnowledgePropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pKnowledgePropertyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pKnowledgePropertyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_KNOWLEDGE_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_KNOWLEDGE_PROPERTY d WHERE KNOWLEDGE_PROPERTY_ID = " + pKnowledgePropertyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pKnowledgePropertyId);
        return n;
     }

    /**
     * Deletes KnowledgePropertyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_KNOWLEDGE_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_KNOWLEDGE_PROPERTY d ");
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

