
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        KnowledgeDataAccess
 * Description:  This class is used to build access methods to the CLW_KNOWLEDGE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.KnowledgeData;
import com.cleanwise.service.api.value.KnowledgeDataVector;
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
 * <code>KnowledgeDataAccess</code>
 */
public class KnowledgeDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(KnowledgeDataAccess.class.getName());

    /** <code>CLW_KNOWLEDGE</code> table name */
	/* Primary key: KNOWLEDGE_ID */
	
    public static final String CLW_KNOWLEDGE = "CLW_KNOWLEDGE";
    
    /** <code>KNOWLEDGE_ID</code> KNOWLEDGE_ID column of table CLW_KNOWLEDGE */
    public static final String KNOWLEDGE_ID = "KNOWLEDGE_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_KNOWLEDGE */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>USER_ID</code> USER_ID column of table CLW_KNOWLEDGE */
    public static final String USER_ID = "USER_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_KNOWLEDGE */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_KNOWLEDGE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_KNOWLEDGE */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>KNOWLEDGE_CATEGORY_CD</code> KNOWLEDGE_CATEGORY_CD column of table CLW_KNOWLEDGE */
    public static final String KNOWLEDGE_CATEGORY_CD = "KNOWLEDGE_CATEGORY_CD";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_KNOWLEDGE */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_KNOWLEDGE */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>COMMENTS</code> COMMENTS column of table CLW_KNOWLEDGE */
    public static final String COMMENTS = "COMMENTS";
    /** <code>KNOWLEDGE_STATUS_CD</code> KNOWLEDGE_STATUS_CD column of table CLW_KNOWLEDGE */
    public static final String KNOWLEDGE_STATUS_CD = "KNOWLEDGE_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_KNOWLEDGE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_TIME</code> ADD_TIME column of table CLW_KNOWLEDGE */
    public static final String ADD_TIME = "ADD_TIME";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_KNOWLEDGE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_KNOWLEDGE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_KNOWLEDGE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>STORE_ID</code> STORE_ID column of table CLW_KNOWLEDGE */
    public static final String STORE_ID = "STORE_ID";

    /**
     * Constructor.
     */
    public KnowledgeDataAccess()
    {
    }

    /**
     * Gets a KnowledgeData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pKnowledgeId The key requested.
     * @return new KnowledgeData()
     * @throws            SQLException
     */
    public static KnowledgeData select(Connection pCon, int pKnowledgeId)
        throws SQLException, DataNotFoundException {
        KnowledgeData x=null;
        String sql="SELECT KNOWLEDGE_ID,BUS_ENTITY_ID,USER_ID,ITEM_ID,SHORT_DESC,LONG_DESC,KNOWLEDGE_CATEGORY_CD,EFF_DATE,EXP_DATE,COMMENTS,KNOWLEDGE_STATUS_CD,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY,STORE_ID FROM CLW_KNOWLEDGE WHERE KNOWLEDGE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pKnowledgeId=" + pKnowledgeId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pKnowledgeId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=KnowledgeData.createValue();
            
            x.setKnowledgeId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setItemId(rs.getInt(4));
            x.setShortDesc(rs.getString(5));
            x.setLongDesc(rs.getString(6));
            x.setKnowledgeCategoryCd(rs.getString(7));
            x.setEffDate(rs.getDate(8));
            x.setExpDate(rs.getDate(9));
            x.setComments(rs.getString(10));
            x.setKnowledgeStatusCd(rs.getString(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddTime(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setStoreId(rs.getInt(17));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("KNOWLEDGE_ID :" + pKnowledgeId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a KnowledgeDataVector object that consists
     * of KnowledgeData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new KnowledgeDataVector()
     * @throws            SQLException
     */
    public static KnowledgeDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a KnowledgeData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_KNOWLEDGE.KNOWLEDGE_ID,CLW_KNOWLEDGE.BUS_ENTITY_ID,CLW_KNOWLEDGE.USER_ID,CLW_KNOWLEDGE.ITEM_ID,CLW_KNOWLEDGE.SHORT_DESC,CLW_KNOWLEDGE.LONG_DESC,CLW_KNOWLEDGE.KNOWLEDGE_CATEGORY_CD,CLW_KNOWLEDGE.EFF_DATE,CLW_KNOWLEDGE.EXP_DATE,CLW_KNOWLEDGE.COMMENTS,CLW_KNOWLEDGE.KNOWLEDGE_STATUS_CD,CLW_KNOWLEDGE.ADD_DATE,CLW_KNOWLEDGE.ADD_TIME,CLW_KNOWLEDGE.ADD_BY,CLW_KNOWLEDGE.MOD_DATE,CLW_KNOWLEDGE.MOD_BY,CLW_KNOWLEDGE.STORE_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated KnowledgeData Object.
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
    *@returns a populated KnowledgeData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         KnowledgeData x = KnowledgeData.createValue();
         
         x.setKnowledgeId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setUserId(rs.getInt(3+offset));
         x.setItemId(rs.getInt(4+offset));
         x.setShortDesc(rs.getString(5+offset));
         x.setLongDesc(rs.getString(6+offset));
         x.setKnowledgeCategoryCd(rs.getString(7+offset));
         x.setEffDate(rs.getDate(8+offset));
         x.setExpDate(rs.getDate(9+offset));
         x.setComments(rs.getString(10+offset));
         x.setKnowledgeStatusCd(rs.getString(11+offset));
         x.setAddDate(rs.getTimestamp(12+offset));
         x.setAddTime(rs.getTimestamp(13+offset));
         x.setAddBy(rs.getString(14+offset));
         x.setModDate(rs.getTimestamp(15+offset));
         x.setModBy(rs.getString(16+offset));
         x.setStoreId(rs.getInt(17+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the KnowledgeData Object represents.
    */
    public int getColumnCount(){
        return 17;
    }

    /**
     * Gets a KnowledgeDataVector object that consists
     * of KnowledgeData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new KnowledgeDataVector()
     * @throws            SQLException
     */
    public static KnowledgeDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT KNOWLEDGE_ID,BUS_ENTITY_ID,USER_ID,ITEM_ID,SHORT_DESC,LONG_DESC,KNOWLEDGE_CATEGORY_CD,EFF_DATE,EXP_DATE,COMMENTS,KNOWLEDGE_STATUS_CD,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY,STORE_ID FROM CLW_KNOWLEDGE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_KNOWLEDGE.KNOWLEDGE_ID,CLW_KNOWLEDGE.BUS_ENTITY_ID,CLW_KNOWLEDGE.USER_ID,CLW_KNOWLEDGE.ITEM_ID,CLW_KNOWLEDGE.SHORT_DESC,CLW_KNOWLEDGE.LONG_DESC,CLW_KNOWLEDGE.KNOWLEDGE_CATEGORY_CD,CLW_KNOWLEDGE.EFF_DATE,CLW_KNOWLEDGE.EXP_DATE,CLW_KNOWLEDGE.COMMENTS,CLW_KNOWLEDGE.KNOWLEDGE_STATUS_CD,CLW_KNOWLEDGE.ADD_DATE,CLW_KNOWLEDGE.ADD_TIME,CLW_KNOWLEDGE.ADD_BY,CLW_KNOWLEDGE.MOD_DATE,CLW_KNOWLEDGE.MOD_BY,CLW_KNOWLEDGE.STORE_ID FROM CLW_KNOWLEDGE");
                where = pCriteria.getSqlClause("CLW_KNOWLEDGE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_KNOWLEDGE.equals(otherTable)){
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
        KnowledgeDataVector v = new KnowledgeDataVector();
        while (rs.next()) {
            KnowledgeData x = KnowledgeData.createValue();
            
            x.setKnowledgeId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setItemId(rs.getInt(4));
            x.setShortDesc(rs.getString(5));
            x.setLongDesc(rs.getString(6));
            x.setKnowledgeCategoryCd(rs.getString(7));
            x.setEffDate(rs.getDate(8));
            x.setExpDate(rs.getDate(9));
            x.setComments(rs.getString(10));
            x.setKnowledgeStatusCd(rs.getString(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddTime(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setStoreId(rs.getInt(17));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a KnowledgeDataVector object that consists
     * of KnowledgeData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for KnowledgeData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new KnowledgeDataVector()
     * @throws            SQLException
     */
    public static KnowledgeDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        KnowledgeDataVector v = new KnowledgeDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT KNOWLEDGE_ID,BUS_ENTITY_ID,USER_ID,ITEM_ID,SHORT_DESC,LONG_DESC,KNOWLEDGE_CATEGORY_CD,EFF_DATE,EXP_DATE,COMMENTS,KNOWLEDGE_STATUS_CD,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY,STORE_ID FROM CLW_KNOWLEDGE WHERE KNOWLEDGE_ID IN (");

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
            KnowledgeData x=null;
            while (rs.next()) {
                // build the object
                x=KnowledgeData.createValue();
                
                x.setKnowledgeId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setUserId(rs.getInt(3));
                x.setItemId(rs.getInt(4));
                x.setShortDesc(rs.getString(5));
                x.setLongDesc(rs.getString(6));
                x.setKnowledgeCategoryCd(rs.getString(7));
                x.setEffDate(rs.getDate(8));
                x.setExpDate(rs.getDate(9));
                x.setComments(rs.getString(10));
                x.setKnowledgeStatusCd(rs.getString(11));
                x.setAddDate(rs.getTimestamp(12));
                x.setAddTime(rs.getTimestamp(13));
                x.setAddBy(rs.getString(14));
                x.setModDate(rs.getTimestamp(15));
                x.setModBy(rs.getString(16));
                x.setStoreId(rs.getInt(17));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a KnowledgeDataVector object of all
     * KnowledgeData objects in the database.
     * @param pCon An open database connection.
     * @return new KnowledgeDataVector()
     * @throws            SQLException
     */
    public static KnowledgeDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT KNOWLEDGE_ID,BUS_ENTITY_ID,USER_ID,ITEM_ID,SHORT_DESC,LONG_DESC,KNOWLEDGE_CATEGORY_CD,EFF_DATE,EXP_DATE,COMMENTS,KNOWLEDGE_STATUS_CD,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY,STORE_ID FROM CLW_KNOWLEDGE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        KnowledgeDataVector v = new KnowledgeDataVector();
        KnowledgeData x = null;
        while (rs.next()) {
            // build the object
            x = KnowledgeData.createValue();
            
            x.setKnowledgeId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setItemId(rs.getInt(4));
            x.setShortDesc(rs.getString(5));
            x.setLongDesc(rs.getString(6));
            x.setKnowledgeCategoryCd(rs.getString(7));
            x.setEffDate(rs.getDate(8));
            x.setExpDate(rs.getDate(9));
            x.setComments(rs.getString(10));
            x.setKnowledgeStatusCd(rs.getString(11));
            x.setAddDate(rs.getTimestamp(12));
            x.setAddTime(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setStoreId(rs.getInt(17));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * KnowledgeData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT KNOWLEDGE_ID FROM CLW_KNOWLEDGE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_KNOWLEDGE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_KNOWLEDGE");
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
     * Inserts a KnowledgeData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgeData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new KnowledgeData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static KnowledgeData insert(Connection pCon, KnowledgeData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_KNOWLEDGE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_KNOWLEDGE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setKnowledgeId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_KNOWLEDGE (KNOWLEDGE_ID,BUS_ENTITY_ID,USER_ID,ITEM_ID,SHORT_DESC,LONG_DESC,KNOWLEDGE_CATEGORY_CD,EFF_DATE,EXP_DATE,COMMENTS,KNOWLEDGE_STATUS_CD,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY,STORE_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getKnowledgeId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getBusEntityId());
        }

        if (pData.getUserId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getUserId());
        }

        if (pData.getItemId() == 0) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4,pData.getItemId());
        }

        pstmt.setString(5,pData.getShortDesc());
        pstmt.setString(6,pData.getLongDesc());
        pstmt.setString(7,pData.getKnowledgeCategoryCd());
        pstmt.setDate(8,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(9,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(10,pData.getComments());
        pstmt.setString(11,pData.getKnowledgeStatusCd());
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getAddTime()));
        pstmt.setString(14,pData.getAddBy());
        pstmt.setTimestamp(15,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16,pData.getModBy());
        pstmt.setInt(17,pData.getStoreId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   KNOWLEDGE_ID="+pData.getKnowledgeId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   KNOWLEDGE_CATEGORY_CD="+pData.getKnowledgeCategoryCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   COMMENTS="+pData.getComments());
            log.debug("SQL:   KNOWLEDGE_STATUS_CD="+pData.getKnowledgeStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_TIME="+pData.getAddTime());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setKnowledgeId(0);
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
     * Updates a KnowledgeData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgeData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, KnowledgeData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_KNOWLEDGE SET BUS_ENTITY_ID = ?,USER_ID = ?,ITEM_ID = ?,SHORT_DESC = ?,LONG_DESC = ?,KNOWLEDGE_CATEGORY_CD = ?,EFF_DATE = ?,EXP_DATE = ?,COMMENTS = ?,KNOWLEDGE_STATUS_CD = ?,ADD_DATE = ?,ADD_TIME = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,STORE_ID = ? WHERE KNOWLEDGE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
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

        if (pData.getItemId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getItemId());
        }

        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setString(i++,pData.getKnowledgeCategoryCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(i++,pData.getComments());
        pstmt.setString(i++,pData.getKnowledgeStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddTime()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getStoreId());
        pstmt.setInt(i++,pData.getKnowledgeId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   KNOWLEDGE_CATEGORY_CD="+pData.getKnowledgeCategoryCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   COMMENTS="+pData.getComments());
            log.debug("SQL:   KNOWLEDGE_STATUS_CD="+pData.getKnowledgeStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_TIME="+pData.getAddTime());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a KnowledgeData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pKnowledgeId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pKnowledgeId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_KNOWLEDGE WHERE KNOWLEDGE_ID = " + pKnowledgeId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes KnowledgeData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_KNOWLEDGE");
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
     * Inserts a KnowledgeData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgeData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, KnowledgeData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_KNOWLEDGE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "KNOWLEDGE_ID,BUS_ENTITY_ID,USER_ID,ITEM_ID,SHORT_DESC,LONG_DESC,KNOWLEDGE_CATEGORY_CD,EFF_DATE,EXP_DATE,COMMENTS,KNOWLEDGE_STATUS_CD,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY,STORE_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getKnowledgeId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getBusEntityId());
        }

        if (pData.getUserId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getUserId());
        }

        if (pData.getItemId() == 0) {
            pstmt.setNull(4+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4+4,pData.getItemId());
        }

        pstmt.setString(5+4,pData.getShortDesc());
        pstmt.setString(6+4,pData.getLongDesc());
        pstmt.setString(7+4,pData.getKnowledgeCategoryCd());
        pstmt.setDate(8+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(9+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setString(10+4,pData.getComments());
        pstmt.setString(11+4,pData.getKnowledgeStatusCd());
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getAddTime()));
        pstmt.setString(14+4,pData.getAddBy());
        pstmt.setTimestamp(15+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16+4,pData.getModBy());
        pstmt.setInt(17+4,pData.getStoreId());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a KnowledgeData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgeData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new KnowledgeData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static KnowledgeData insert(Connection pCon, KnowledgeData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a KnowledgeData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgeData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, KnowledgeData pData, boolean pLogFl)
        throws SQLException {
        KnowledgeData oldData = null;
        if(pLogFl) {
          int id = pData.getKnowledgeId();
          try {
          oldData = KnowledgeDataAccess.select(pCon,id);
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
     * Deletes a KnowledgeData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pKnowledgeId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pKnowledgeId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_KNOWLEDGE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_KNOWLEDGE d WHERE KNOWLEDGE_ID = " + pKnowledgeId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pKnowledgeId);
        return n;
     }

    /**
     * Deletes KnowledgeData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_KNOWLEDGE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_KNOWLEDGE d ");
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

