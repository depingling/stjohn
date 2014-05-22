
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ItemSubstitutionDefDataAccess
 * Description:  This class is used to build access methods to the CLW_ITEM_SUBSTITUTION_DEF table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ItemSubstitutionDefData;
import com.cleanwise.service.api.value.ItemSubstitutionDefDataVector;
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
 * <code>ItemSubstitutionDefDataAccess</code>
 */
public class ItemSubstitutionDefDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ItemSubstitutionDefDataAccess.class.getName());

    /** <code>CLW_ITEM_SUBSTITUTION_DEF</code> table name */
	/* Primary key: ITEM_SUBSTITUTION_DEF_ID */
	
    public static final String CLW_ITEM_SUBSTITUTION_DEF = "CLW_ITEM_SUBSTITUTION_DEF";
    
    /** <code>ITEM_SUBSTITUTION_DEF_ID</code> ITEM_SUBSTITUTION_DEF_ID column of table CLW_ITEM_SUBSTITUTION_DEF */
    public static final String ITEM_SUBSTITUTION_DEF_ID = "ITEM_SUBSTITUTION_DEF_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_ITEM_SUBSTITUTION_DEF */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_ITEM_SUBSTITUTION_DEF */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>SUBST_ITEM_ID</code> SUBST_ITEM_ID column of table CLW_ITEM_SUBSTITUTION_DEF */
    public static final String SUBST_ITEM_ID = "SUBST_ITEM_ID";
    /** <code>SUBST_STATUS_CD</code> SUBST_STATUS_CD column of table CLW_ITEM_SUBSTITUTION_DEF */
    public static final String SUBST_STATUS_CD = "SUBST_STATUS_CD";
    /** <code>SUBST_TYPE_CD</code> SUBST_TYPE_CD column of table CLW_ITEM_SUBSTITUTION_DEF */
    public static final String SUBST_TYPE_CD = "SUBST_TYPE_CD";
    /** <code>UOM_CONVERSION_FACTOR</code> UOM_CONVERSION_FACTOR column of table CLW_ITEM_SUBSTITUTION_DEF */
    public static final String UOM_CONVERSION_FACTOR = "UOM_CONVERSION_FACTOR";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ITEM_SUBSTITUTION_DEF */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ITEM_SUBSTITUTION_DEF */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ITEM_SUBSTITUTION_DEF */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ITEM_SUBSTITUTION_DEF */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ItemSubstitutionDefDataAccess()
    {
    }

    /**
     * Gets a ItemSubstitutionDefData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pItemSubstitutionDefId The key requested.
     * @return new ItemSubstitutionDefData()
     * @throws            SQLException
     */
    public static ItemSubstitutionDefData select(Connection pCon, int pItemSubstitutionDefId)
        throws SQLException, DataNotFoundException {
        ItemSubstitutionDefData x=null;
        String sql="SELECT ITEM_SUBSTITUTION_DEF_ID,BUS_ENTITY_ID,ITEM_ID,SUBST_ITEM_ID,SUBST_STATUS_CD,SUBST_TYPE_CD,UOM_CONVERSION_FACTOR,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_SUBSTITUTION_DEF WHERE ITEM_SUBSTITUTION_DEF_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pItemSubstitutionDefId=" + pItemSubstitutionDefId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pItemSubstitutionDefId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ItemSubstitutionDefData.createValue();
            
            x.setItemSubstitutionDefId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setSubstItemId(rs.getInt(4));
            x.setSubstStatusCd(rs.getString(5));
            x.setSubstTypeCd(rs.getString(6));
            x.setUomConversionFactor(rs.getBigDecimal(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ITEM_SUBSTITUTION_DEF_ID :" + pItemSubstitutionDefId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ItemSubstitutionDefDataVector object that consists
     * of ItemSubstitutionDefData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ItemSubstitutionDefDataVector()
     * @throws            SQLException
     */
    public static ItemSubstitutionDefDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ItemSubstitutionDefData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ITEM_SUBSTITUTION_DEF.ITEM_SUBSTITUTION_DEF_ID,CLW_ITEM_SUBSTITUTION_DEF.BUS_ENTITY_ID,CLW_ITEM_SUBSTITUTION_DEF.ITEM_ID,CLW_ITEM_SUBSTITUTION_DEF.SUBST_ITEM_ID,CLW_ITEM_SUBSTITUTION_DEF.SUBST_STATUS_CD,CLW_ITEM_SUBSTITUTION_DEF.SUBST_TYPE_CD,CLW_ITEM_SUBSTITUTION_DEF.UOM_CONVERSION_FACTOR,CLW_ITEM_SUBSTITUTION_DEF.ADD_DATE,CLW_ITEM_SUBSTITUTION_DEF.ADD_BY,CLW_ITEM_SUBSTITUTION_DEF.MOD_DATE,CLW_ITEM_SUBSTITUTION_DEF.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ItemSubstitutionDefData Object.
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
    *@returns a populated ItemSubstitutionDefData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ItemSubstitutionDefData x = ItemSubstitutionDefData.createValue();
         
         x.setItemSubstitutionDefId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setItemId(rs.getInt(3+offset));
         x.setSubstItemId(rs.getInt(4+offset));
         x.setSubstStatusCd(rs.getString(5+offset));
         x.setSubstTypeCd(rs.getString(6+offset));
         x.setUomConversionFactor(rs.getBigDecimal(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ItemSubstitutionDefData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a ItemSubstitutionDefDataVector object that consists
     * of ItemSubstitutionDefData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ItemSubstitutionDefDataVector()
     * @throws            SQLException
     */
    public static ItemSubstitutionDefDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ITEM_SUBSTITUTION_DEF_ID,BUS_ENTITY_ID,ITEM_ID,SUBST_ITEM_ID,SUBST_STATUS_CD,SUBST_TYPE_CD,UOM_CONVERSION_FACTOR,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_SUBSTITUTION_DEF");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ITEM_SUBSTITUTION_DEF.ITEM_SUBSTITUTION_DEF_ID,CLW_ITEM_SUBSTITUTION_DEF.BUS_ENTITY_ID,CLW_ITEM_SUBSTITUTION_DEF.ITEM_ID,CLW_ITEM_SUBSTITUTION_DEF.SUBST_ITEM_ID,CLW_ITEM_SUBSTITUTION_DEF.SUBST_STATUS_CD,CLW_ITEM_SUBSTITUTION_DEF.SUBST_TYPE_CD,CLW_ITEM_SUBSTITUTION_DEF.UOM_CONVERSION_FACTOR,CLW_ITEM_SUBSTITUTION_DEF.ADD_DATE,CLW_ITEM_SUBSTITUTION_DEF.ADD_BY,CLW_ITEM_SUBSTITUTION_DEF.MOD_DATE,CLW_ITEM_SUBSTITUTION_DEF.MOD_BY FROM CLW_ITEM_SUBSTITUTION_DEF");
                where = pCriteria.getSqlClause("CLW_ITEM_SUBSTITUTION_DEF");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ITEM_SUBSTITUTION_DEF.equals(otherTable)){
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
        ItemSubstitutionDefDataVector v = new ItemSubstitutionDefDataVector();
        while (rs.next()) {
            ItemSubstitutionDefData x = ItemSubstitutionDefData.createValue();
            
            x.setItemSubstitutionDefId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setSubstItemId(rs.getInt(4));
            x.setSubstStatusCd(rs.getString(5));
            x.setSubstTypeCd(rs.getString(6));
            x.setUomConversionFactor(rs.getBigDecimal(7));
            x.setAddDate(rs.getTimestamp(8));
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
     * Gets a ItemSubstitutionDefDataVector object that consists
     * of ItemSubstitutionDefData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ItemSubstitutionDefData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ItemSubstitutionDefDataVector()
     * @throws            SQLException
     */
    public static ItemSubstitutionDefDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ItemSubstitutionDefDataVector v = new ItemSubstitutionDefDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_SUBSTITUTION_DEF_ID,BUS_ENTITY_ID,ITEM_ID,SUBST_ITEM_ID,SUBST_STATUS_CD,SUBST_TYPE_CD,UOM_CONVERSION_FACTOR,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_SUBSTITUTION_DEF WHERE ITEM_SUBSTITUTION_DEF_ID IN (");

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
            ItemSubstitutionDefData x=null;
            while (rs.next()) {
                // build the object
                x=ItemSubstitutionDefData.createValue();
                
                x.setItemSubstitutionDefId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setItemId(rs.getInt(3));
                x.setSubstItemId(rs.getInt(4));
                x.setSubstStatusCd(rs.getString(5));
                x.setSubstTypeCd(rs.getString(6));
                x.setUomConversionFactor(rs.getBigDecimal(7));
                x.setAddDate(rs.getTimestamp(8));
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
     * Gets a ItemSubstitutionDefDataVector object of all
     * ItemSubstitutionDefData objects in the database.
     * @param pCon An open database connection.
     * @return new ItemSubstitutionDefDataVector()
     * @throws            SQLException
     */
    public static ItemSubstitutionDefDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ITEM_SUBSTITUTION_DEF_ID,BUS_ENTITY_ID,ITEM_ID,SUBST_ITEM_ID,SUBST_STATUS_CD,SUBST_TYPE_CD,UOM_CONVERSION_FACTOR,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_SUBSTITUTION_DEF";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ItemSubstitutionDefDataVector v = new ItemSubstitutionDefDataVector();
        ItemSubstitutionDefData x = null;
        while (rs.next()) {
            // build the object
            x = ItemSubstitutionDefData.createValue();
            
            x.setItemSubstitutionDefId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setSubstItemId(rs.getInt(4));
            x.setSubstStatusCd(rs.getString(5));
            x.setSubstTypeCd(rs.getString(6));
            x.setUomConversionFactor(rs.getBigDecimal(7));
            x.setAddDate(rs.getTimestamp(8));
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
     * ItemSubstitutionDefData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_SUBSTITUTION_DEF_ID FROM CLW_ITEM_SUBSTITUTION_DEF");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_SUBSTITUTION_DEF");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_SUBSTITUTION_DEF");
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
     * Inserts a ItemSubstitutionDefData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemSubstitutionDefData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ItemSubstitutionDefData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemSubstitutionDefData insert(Connection pCon, ItemSubstitutionDefData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ITEM_SUBSTITUTION_DEF_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ITEM_SUBSTITUTION_DEF_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setItemSubstitutionDefId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ITEM_SUBSTITUTION_DEF (ITEM_SUBSTITUTION_DEF_ID,BUS_ENTITY_ID,ITEM_ID,SUBST_ITEM_ID,SUBST_STATUS_CD,SUBST_TYPE_CD,UOM_CONVERSION_FACTOR,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getItemSubstitutionDefId());
        pstmt.setInt(2,pData.getBusEntityId());
        pstmt.setInt(3,pData.getItemId());
        pstmt.setInt(4,pData.getSubstItemId());
        pstmt.setString(5,pData.getSubstStatusCd());
        pstmt.setString(6,pData.getSubstTypeCd());
        pstmt.setBigDecimal(7,pData.getUomConversionFactor());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_SUBSTITUTION_DEF_ID="+pData.getItemSubstitutionDefId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   SUBST_ITEM_ID="+pData.getSubstItemId());
            log.debug("SQL:   SUBST_STATUS_CD="+pData.getSubstStatusCd());
            log.debug("SQL:   SUBST_TYPE_CD="+pData.getSubstTypeCd());
            log.debug("SQL:   UOM_CONVERSION_FACTOR="+pData.getUomConversionFactor());
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
        pData.setItemSubstitutionDefId(0);
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
     * Updates a ItemSubstitutionDefData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemSubstitutionDefData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemSubstitutionDefData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ITEM_SUBSTITUTION_DEF SET BUS_ENTITY_ID = ?,ITEM_ID = ?,SUBST_ITEM_ID = ?,SUBST_STATUS_CD = ?,SUBST_TYPE_CD = ?,UOM_CONVERSION_FACTOR = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ITEM_SUBSTITUTION_DEF_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setInt(i++,pData.getSubstItemId());
        pstmt.setString(i++,pData.getSubstStatusCd());
        pstmt.setString(i++,pData.getSubstTypeCd());
        pstmt.setBigDecimal(i++,pData.getUomConversionFactor());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getItemSubstitutionDefId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   SUBST_ITEM_ID="+pData.getSubstItemId());
            log.debug("SQL:   SUBST_STATUS_CD="+pData.getSubstStatusCd());
            log.debug("SQL:   SUBST_TYPE_CD="+pData.getSubstTypeCd());
            log.debug("SQL:   UOM_CONVERSION_FACTOR="+pData.getUomConversionFactor());
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
     * Deletes a ItemSubstitutionDefData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemSubstitutionDefId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemSubstitutionDefId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ITEM_SUBSTITUTION_DEF WHERE ITEM_SUBSTITUTION_DEF_ID = " + pItemSubstitutionDefId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ItemSubstitutionDefData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ITEM_SUBSTITUTION_DEF");
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
     * Inserts a ItemSubstitutionDefData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemSubstitutionDefData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ItemSubstitutionDefData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ITEM_SUBSTITUTION_DEF (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ITEM_SUBSTITUTION_DEF_ID,BUS_ENTITY_ID,ITEM_ID,SUBST_ITEM_ID,SUBST_STATUS_CD,SUBST_TYPE_CD,UOM_CONVERSION_FACTOR,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getItemSubstitutionDefId());
        pstmt.setInt(2+4,pData.getBusEntityId());
        pstmt.setInt(3+4,pData.getItemId());
        pstmt.setInt(4+4,pData.getSubstItemId());
        pstmt.setString(5+4,pData.getSubstStatusCd());
        pstmt.setString(6+4,pData.getSubstTypeCd());
        pstmt.setBigDecimal(7+4,pData.getUomConversionFactor());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ItemSubstitutionDefData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemSubstitutionDefData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ItemSubstitutionDefData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemSubstitutionDefData insert(Connection pCon, ItemSubstitutionDefData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ItemSubstitutionDefData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemSubstitutionDefData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemSubstitutionDefData pData, boolean pLogFl)
        throws SQLException {
        ItemSubstitutionDefData oldData = null;
        if(pLogFl) {
          int id = pData.getItemSubstitutionDefId();
          try {
          oldData = ItemSubstitutionDefDataAccess.select(pCon,id);
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
     * Deletes a ItemSubstitutionDefData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemSubstitutionDefId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemSubstitutionDefId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ITEM_SUBSTITUTION_DEF SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM_SUBSTITUTION_DEF d WHERE ITEM_SUBSTITUTION_DEF_ID = " + pItemSubstitutionDefId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pItemSubstitutionDefId);
        return n;
     }

    /**
     * Deletes ItemSubstitutionDefData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ITEM_SUBSTITUTION_DEF SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM_SUBSTITUTION_DEF d ");
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

