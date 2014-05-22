
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        NoteDataAccess
 * Description:  This class is used to build access methods to the CLW_NOTE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.NoteData;
import com.cleanwise.service.api.value.NoteDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.cachecos.Cachecos;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>NoteDataAccess</code>
 */
public class NoteDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(NoteDataAccess.class.getName());

    /** <code>CLW_NOTE</code> table name */
	/* Primary key: NOTE_ID */
	
    public static final String CLW_NOTE = "CLW_NOTE";
    
    /** <code>NOTE_ID</code> NOTE_ID column of table CLW_NOTE */
    public static final String NOTE_ID = "NOTE_ID";
    /** <code>PROPERTY_ID</code> PROPERTY_ID column of table CLW_NOTE */
    public static final String PROPERTY_ID = "PROPERTY_ID";
    /** <code>NOTE_TYPE_CD</code> NOTE_TYPE_CD column of table CLW_NOTE */
    public static final String NOTE_TYPE_CD = "NOTE_TYPE_CD";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_NOTE */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>TITLE</code> TITLE column of table CLW_NOTE */
    public static final String TITLE = "TITLE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_NOTE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_NOTE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_NOTE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_NOTE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_NOTE */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_NOTE */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>COUNTER</code> COUNTER column of table CLW_NOTE */
    public static final String COUNTER = "COUNTER";
    /** <code>LOCALE_CD</code> LOCALE_CD column of table CLW_NOTE */
    public static final String LOCALE_CD = "LOCALE_CD";
    /** <code>FORCED_EACH_LOGIN</code> FORCED_EACH_LOGIN column of table CLW_NOTE */
    public static final String FORCED_EACH_LOGIN = "FORCED_EACH_LOGIN";

    /**
     * Constructor.
     */
    public NoteDataAccess()
    {
    }

    /**
     * Gets a NoteData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pNoteId The key requested.
     * @return new NoteData()
     * @throws            SQLException
     */
    public static NoteData select(Connection pCon, int pNoteId)
        throws SQLException, DataNotFoundException {
        NoteData x=null;
        String sql="SELECT NOTE_ID,PROPERTY_ID,NOTE_TYPE_CD,BUS_ENTITY_ID,TITLE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,EFF_DATE,EXP_DATE,COUNTER,LOCALE_CD,FORCED_EACH_LOGIN FROM CLW_NOTE WHERE NOTE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pNoteId=" + pNoteId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pNoteId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=NoteData.createValue();
            
            x.setNoteId(rs.getInt(1));
            x.setPropertyId(rs.getInt(2));
            x.setNoteTypeCd(rs.getString(3));
            x.setBusEntityId(rs.getInt(4));
            x.setTitle(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setEffDate(rs.getDate(10));
            x.setExpDate(rs.getDate(11));
            x.setCounter(rs.getInt(12));
            x.setLocaleCd(rs.getString(13));
            x.setForcedEachLogin(rs.getString(14));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("NOTE_ID :" + pNoteId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a NoteDataVector object that consists
     * of NoteData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new NoteDataVector()
     * @throws            SQLException
     */
    public static NoteDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a NoteData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_NOTE.NOTE_ID,CLW_NOTE.PROPERTY_ID,CLW_NOTE.NOTE_TYPE_CD,CLW_NOTE.BUS_ENTITY_ID,CLW_NOTE.TITLE,CLW_NOTE.ADD_DATE,CLW_NOTE.ADD_BY,CLW_NOTE.MOD_DATE,CLW_NOTE.MOD_BY,CLW_NOTE.EFF_DATE,CLW_NOTE.EXP_DATE,CLW_NOTE.COUNTER,CLW_NOTE.LOCALE_CD,CLW_NOTE.FORCED_EACH_LOGIN";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated NoteData Object.
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
    *@returns a populated NoteData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         NoteData x = NoteData.createValue();
         
         x.setNoteId(rs.getInt(1+offset));
         x.setPropertyId(rs.getInt(2+offset));
         x.setNoteTypeCd(rs.getString(3+offset));
         x.setBusEntityId(rs.getInt(4+offset));
         x.setTitle(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         x.setEffDate(rs.getDate(10+offset));
         x.setExpDate(rs.getDate(11+offset));
         x.setCounter(rs.getInt(12+offset));
         x.setLocaleCd(rs.getString(13+offset));
         x.setForcedEachLogin(rs.getString(14+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the NoteData Object represents.
    */
    public int getColumnCount(){
        return 14;
    }

    /**
     * Gets a NoteDataVector object that consists
     * of NoteData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new NoteDataVector()
     * @throws            SQLException
     */
    public static NoteDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT NOTE_ID,PROPERTY_ID,NOTE_TYPE_CD,BUS_ENTITY_ID,TITLE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,EFF_DATE,EXP_DATE,COUNTER,LOCALE_CD,FORCED_EACH_LOGIN FROM CLW_NOTE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_NOTE.NOTE_ID,CLW_NOTE.PROPERTY_ID,CLW_NOTE.NOTE_TYPE_CD,CLW_NOTE.BUS_ENTITY_ID,CLW_NOTE.TITLE,CLW_NOTE.ADD_DATE,CLW_NOTE.ADD_BY,CLW_NOTE.MOD_DATE,CLW_NOTE.MOD_BY,CLW_NOTE.EFF_DATE,CLW_NOTE.EXP_DATE,CLW_NOTE.COUNTER,CLW_NOTE.LOCALE_CD,CLW_NOTE.FORCED_EACH_LOGIN FROM CLW_NOTE");
                where = pCriteria.getSqlClause("CLW_NOTE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_NOTE.equals(otherTable)){
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
        NoteDataVector v = new NoteDataVector();
        while (rs.next()) {
            NoteData x = NoteData.createValue();
            
            x.setNoteId(rs.getInt(1));
            x.setPropertyId(rs.getInt(2));
            x.setNoteTypeCd(rs.getString(3));
            x.setBusEntityId(rs.getInt(4));
            x.setTitle(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setEffDate(rs.getDate(10));
            x.setExpDate(rs.getDate(11));
            x.setCounter(rs.getInt(12));
            x.setLocaleCd(rs.getString(13));
            x.setForcedEachLogin(rs.getString(14));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a NoteDataVector object that consists
     * of NoteData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for NoteData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new NoteDataVector()
     * @throws            SQLException
     */
    public static NoteDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        NoteDataVector v = new NoteDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT NOTE_ID,PROPERTY_ID,NOTE_TYPE_CD,BUS_ENTITY_ID,TITLE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,EFF_DATE,EXP_DATE,COUNTER,LOCALE_CD,FORCED_EACH_LOGIN FROM CLW_NOTE WHERE NOTE_ID IN (");

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
            NoteData x=null;
            while (rs.next()) {
                // build the object
                x=NoteData.createValue();
                
                x.setNoteId(rs.getInt(1));
                x.setPropertyId(rs.getInt(2));
                x.setNoteTypeCd(rs.getString(3));
                x.setBusEntityId(rs.getInt(4));
                x.setTitle(rs.getString(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setAddBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));
                x.setEffDate(rs.getDate(10));
                x.setExpDate(rs.getDate(11));
                x.setCounter(rs.getInt(12));
                x.setLocaleCd(rs.getString(13));
                x.setForcedEachLogin(rs.getString(14));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a NoteDataVector object of all
     * NoteData objects in the database.
     * @param pCon An open database connection.
     * @return new NoteDataVector()
     * @throws            SQLException
     */
    public static NoteDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT NOTE_ID,PROPERTY_ID,NOTE_TYPE_CD,BUS_ENTITY_ID,TITLE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,EFF_DATE,EXP_DATE,COUNTER,LOCALE_CD,FORCED_EACH_LOGIN FROM CLW_NOTE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        NoteDataVector v = new NoteDataVector();
        NoteData x = null;
        while (rs.next()) {
            // build the object
            x = NoteData.createValue();
            
            x.setNoteId(rs.getInt(1));
            x.setPropertyId(rs.getInt(2));
            x.setNoteTypeCd(rs.getString(3));
            x.setBusEntityId(rs.getInt(4));
            x.setTitle(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setEffDate(rs.getDate(10));
            x.setExpDate(rs.getDate(11));
            x.setCounter(rs.getInt(12));
            x.setLocaleCd(rs.getString(13));
            x.setForcedEachLogin(rs.getString(14));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * NoteData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT NOTE_ID FROM CLW_NOTE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT NOTE_ID FROM CLW_NOTE");
                where = pCriteria.getSqlClause("CLW_NOTE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_NOTE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_NOTE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_NOTE");
                where = pCriteria.getSqlClause("CLW_NOTE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_NOTE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_NOTE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_NOTE");
                where = pCriteria.getSqlClause("CLW_NOTE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_NOTE.equals(otherTable)){
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
     * Inserts a NoteData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new NoteData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static NoteData insert(Connection pCon, NoteData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_NOTE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_NOTE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setNoteId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_NOTE (NOTE_ID,PROPERTY_ID,NOTE_TYPE_CD,BUS_ENTITY_ID,TITLE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,EFF_DATE,EXP_DATE,COUNTER,LOCALE_CD,FORCED_EACH_LOGIN) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getNoteId());
        pstmt.setInt(2,pData.getPropertyId());
        pstmt.setString(3,pData.getNoteTypeCd());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4,pData.getBusEntityId());
        }

        pstmt.setString(5,pData.getTitle());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());
        pstmt.setDate(10,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(11,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setInt(12,pData.getCounter());
        pstmt.setString(13,pData.getLocaleCd());
        pstmt.setString(14,pData.getForcedEachLogin());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   NOTE_ID="+pData.getNoteId());
            log.debug("SQL:   PROPERTY_ID="+pData.getPropertyId());
            log.debug("SQL:   NOTE_TYPE_CD="+pData.getNoteTypeCd());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   TITLE="+pData.getTitle());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   COUNTER="+pData.getCounter());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   FORCED_EACH_LOGIN="+pData.getForcedEachLogin());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setNoteId(0);
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
     * Updates a NoteData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, NoteData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_NOTE SET PROPERTY_ID = ?,NOTE_TYPE_CD = ?,BUS_ENTITY_ID = ?,TITLE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,EFF_DATE = ?,EXP_DATE = ?,COUNTER = ?,LOCALE_CD = ?,FORCED_EACH_LOGIN = ? WHERE NOTE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getPropertyId());
        pstmt.setString(i++,pData.getNoteTypeCd());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getTitle());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setInt(i++,pData.getCounter());
        pstmt.setString(i++,pData.getLocaleCd());
        pstmt.setString(i++,pData.getForcedEachLogin());
        pstmt.setInt(i++,pData.getNoteId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PROPERTY_ID="+pData.getPropertyId());
            log.debug("SQL:   NOTE_TYPE_CD="+pData.getNoteTypeCd());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   TITLE="+pData.getTitle());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   COUNTER="+pData.getCounter());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   FORCED_EACH_LOGIN="+pData.getForcedEachLogin());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a NoteData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pNoteId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pNoteId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_NOTE WHERE NOTE_ID = " + pNoteId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes NoteData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_NOTE");
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
     * Inserts a NoteData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, NoteData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_NOTE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "NOTE_ID,PROPERTY_ID,NOTE_TYPE_CD,BUS_ENTITY_ID,TITLE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,EFF_DATE,EXP_DATE,COUNTER,LOCALE_CD,FORCED_EACH_LOGIN) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getNoteId());
        pstmt.setInt(2+4,pData.getPropertyId());
        pstmt.setString(3+4,pData.getNoteTypeCd());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(4+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4+4,pData.getBusEntityId());
        }

        pstmt.setString(5+4,pData.getTitle());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());
        pstmt.setDate(10+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(11+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setInt(12+4,pData.getCounter());
        pstmt.setString(13+4,pData.getLocaleCd());
        pstmt.setString(14+4,pData.getForcedEachLogin());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a NoteData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new NoteData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static NoteData insert(Connection pCon, NoteData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a NoteData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, NoteData pData, boolean pLogFl)
        throws SQLException {
        NoteData oldData = null;
        if(pLogFl) {
          int id = pData.getNoteId();
          try {
          oldData = NoteDataAccess.select(pCon,id);
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
     * Deletes a NoteData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pNoteId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pNoteId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_NOTE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_NOTE d WHERE NOTE_ID = " + pNoteId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pNoteId);
        return n;
     }

    /**
     * Deletes NoteData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_NOTE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_NOTE d ");
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

