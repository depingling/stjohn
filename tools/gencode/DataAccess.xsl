<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>

<xsl:template match="table">
<xsl:variable name="pk_col_val"><xsl:call-template name="pk_col1"/></xsl:variable>
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        <xsl:value-of select="@javaname"/>DataAccess
 * Description:  This class is used to build access methods to the <xsl:value-of select="@name"/> table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.<xsl:value-of select="@javaname"/>Data;
import com.cleanwise.service.api.value.<xsl:value-of select="@javaname"/>DataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;<xsl:if test="@cache='true'">
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.cachecos.Cachecos;</xsl:if>
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * &lt;code&gt;<xsl:value-of select="@javaname"/>DataAccess&lt;/code&gt;
 */
public class <xsl:value-of select="@javaname"/>DataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(<xsl:value-of select="@javaname"/>DataAccess.class.getName());

    /** &lt;code&gt;<xsl:value-of select="@name"/>&lt;/code&gt; table name */
	/* Primary key: <xsl:value-of select="$pk_col_val"/> */
	<xsl:if test="$pk_col_val=''">
	!!! Error. No primary key defined
	</xsl:if>
    public static final String <xsl:value-of select="@name"/> = "<xsl:value-of select="@name"/>";
    <xsl:call-template name="col_const"/>

    /**
     * Constructor.
     */
    public <xsl:value-of select="@javaname"/>DataAccess()
    {
    }

    /**
     * Gets a <xsl:value-of select="@javaname"/>Data object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param p<xsl:call-template name="pk_javaname"/> The key requested.
     * @return new <xsl:value-of select="@javaname"/>Data()
     * @throws            SQLException
     */
    public static <xsl:value-of select="@javaname"/>Data select(Connection pCon, int p<xsl:value-of select="@javaname"/>Id)
        throws SQLException, DataNotFoundException {
        <xsl:value-of select="@javaname"/>Data x=null;
        String sql=&quot;SELECT <xsl:call-template name="col_list"/> FROM <xsl:value-of select="@name"/> WHERE <xsl:value-of select="$pk_col_val"/> = ?&quot;;

        if (log.isDebugEnabled()) {
            log.debug("SQL: p<xsl:value-of select="@javaname"/>Id=" + p<xsl:value-of select="@javaname"/>Id);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,p<xsl:value-of select="@javaname"/>Id);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=<xsl:value-of select="@javaname"/>Data.createValue();
            <xsl:for-each select="column">
            x.set<xsl:value-of select="javaname"/><xsl:text>(</xsl:text>rs.get<xsl:value-of select="rsgetter"/><xsl:text>(</xsl:text><xsl:value-of select="position()"/><xsl:text>));</xsl:text>
            </xsl:for-each>

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("<xsl:value-of select="$pk_col_val"/> :" + p<xsl:value-of select="@javaname"/>Id);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a <xsl:value-of select="@javaname"/>DataVector object that consists
     * of <xsl:value-of select="@javaname"/>Data objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new <xsl:value-of select="@javaname"/>DataVector()
     * @throws            SQLException
     */
    public static <xsl:value-of select="@javaname"/>DataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a <xsl:value-of select="@javaname"/>Data Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "<xsl:call-template name="col_list_explicit"/>";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated <xsl:value-of select="@javaname"/>Data Object.
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
    *@returns a populated <xsl:value-of select="@javaname"/>Data Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         <xsl:value-of select="@javaname"/>Data x = <xsl:value-of select="@javaname"/>Data.createValue();
         <xsl:for-each select="column">
         x.set<xsl:value-of select="javaname"/><xsl:text>(</xsl:text>rs.get<xsl:value-of select="rsgetter"/><xsl:text>(</xsl:text><xsl:value-of select="position()"/><xsl:text>+offset));</xsl:text>
         </xsl:for-each>
         return x;
    }

    /**
    *@Returns a count of the number of columns the <xsl:value-of select="@javaname"/>Data Object represents.
    */
    public int getColumnCount(){
        return <xsl:value-of select="count(column)"/>;
    }

    /**
     * Gets a <xsl:value-of select="@javaname"/>DataVector object that consists
     * of <xsl:value-of select="@javaname"/>Data objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new <xsl:value-of select="@javaname"/>DataVector()
     * @throws            SQLException
     */
    public static <xsl:value-of select="@javaname"/>DataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT <xsl:call-template name="col_list"/> FROM <xsl:value-of select="@name"/>");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT <xsl:call-template name="col_list_explicit"/> FROM <xsl:value-of select="@name"/>");
                where = pCriteria.getSqlClause("<xsl:value-of select="@name"/>");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!<xsl:value-of select="@name"/>.equals(otherTable)){
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
				}
                }
        }

        if (where != null &amp;&amp; !where.equals("")) {
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
        <xsl:value-of select="@javaname"/>DataVector v = new <xsl:value-of select="@javaname"/>DataVector();
        while (rs.next()) {
            <xsl:value-of select="@javaname"/>Data x = <xsl:value-of select="@javaname"/>Data.createValue();
            <xsl:for-each select="column">
            x.set<xsl:value-of select="javaname"/><xsl:text>(</xsl:text>rs.get<xsl:value-of select="rsgetter"/><xsl:text>(</xsl:text><xsl:value-of select="position()"/><xsl:text>));</xsl:text>
            </xsl:for-each>
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a <xsl:value-of select="@javaname"/>DataVector object that consists
     * of <xsl:value-of select="@javaname"/>Data objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for <xsl:value-of select="@javaname"/>Data
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new <xsl:value-of select="@javaname"/>DataVector()
     * @throws            SQLException
     */
    public static <xsl:value-of select="@javaname"/>DataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        <xsl:value-of select="@javaname"/>DataVector v = new <xsl:value-of select="@javaname"/>DataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT <xsl:call-template name="col_list"/> FROM <xsl:value-of select="@name"/> WHERE <xsl:value-of select="$pk_col_val"/> IN (");

        if ( pIdVector.size() > 0 ) {
            sqlBuf.append(pIdVector.get(0).toString());
            int vecsize = pIdVector.size();
            for ( int idx = 1; idx &lt; vecsize; idx++ ) {
                sqlBuf.append("," + pIdVector.get(idx).toString());
            }
            sqlBuf.append(")");


            String sql = sqlBuf.toString();
            if (log.isDebugEnabled()) {
                log.debug("SQL: " + sql);
            }

            Statement stmt = pCon.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            <xsl:value-of select="@javaname"/>Data x=null;
            while (rs.next()) {
                // build the object
                x=<xsl:value-of select="@javaname"/>Data.createValue();
                <xsl:for-each select="column">
                x.set<xsl:value-of select="javaname"/><xsl:text>(</xsl:text>rs.get<xsl:value-of select="rsgetter"/><xsl:text>(</xsl:text><xsl:value-of select="position()"/><xsl:text>));</xsl:text>
                </xsl:for-each>
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a <xsl:value-of select="@javaname"/>DataVector object of all
     * <xsl:value-of select="@javaname"/>Data objects in the database.
     * @param pCon An open database connection.
     * @return new <xsl:value-of select="@javaname"/>DataVector()
     * @throws            SQLException
     */
    public static <xsl:value-of select="@javaname"/>DataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT <xsl:call-template name="col_list"/> FROM <xsl:value-of select="@name"/>";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        <xsl:value-of select="@javaname"/>DataVector v = new <xsl:value-of select="@javaname"/>DataVector();
        <xsl:value-of select="@javaname"/>Data x = null;
        while (rs.next()) {
            // build the object
            x = <xsl:value-of select="@javaname"/>Data.createValue();
            <xsl:for-each select="column">
            x.set<xsl:value-of select="javaname"/><xsl:text>(</xsl:text>rs.get<xsl:value-of select="rsgetter"/><xsl:text>(</xsl:text><xsl:value-of select="position()"/><xsl:text>));</xsl:text>
            </xsl:for-each>

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * <xsl:value-of select="@javaname"/>Data objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT <xsl:value-of select="$pk_col_val"/> FROM <xsl:value-of select="@name"/>");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT <xsl:value-of select="$pk_col_val"/> FROM <xsl:value-of select="@name"/>");
                where = pCriteria.getSqlClause("<xsl:value-of select="@name"/>");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!<xsl:value-of select="@name"/>.equals(otherTable)){
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
				}
                }
        }

        if (where != null &amp;&amp; !where.equals("")) {
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM <xsl:value-of select="@name"/>");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM <xsl:value-of select="@name"/>");
                where = pCriteria.getSqlClause("<xsl:value-of select="@name"/>");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!<xsl:value-of select="@name"/>.equals(otherTable)){
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
				}
                }
        }

        if (where != null &amp;&amp; !where.equals("")) {
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM <xsl:value-of select="@name"/>");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM <xsl:value-of select="@name"/>");
                where = pCriteria.getSqlClause("<xsl:value-of select="@name"/>");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!<xsl:value-of select="@name"/>.equals(otherTable)){
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
				}
                }
        }

        if (where != null &amp;&amp; !where.equals("")) {
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
     * Inserts a <xsl:value-of select="@javaname"/>Data object into the database.
     * @param pCon  An open database connection.
     * @param pData  A <xsl:value-of select="@javaname"/>Data object to insert.<xsl:call-template name="insert_javadoc"/>
     * @return new <xsl:value-of select="@javaname"/>Data() with the generated
     *         key set
     * @throws            SQLException
     */
    public static <xsl:value-of select="@javaname"/>Data insert(Connection pCon, <xsl:value-of select="@javaname"/>Data pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT <xsl:value-of select="@name"/>_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT <xsl:value-of select="@name"/>_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.set<xsl:value-of select="@javaname"/>Id(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO <xsl:value-of select="@name"/> (<xsl:for-each select="column"><xsl:value-of select="name"/><xsl:if test="not(position()=last())">,</xsl:if></xsl:for-each>) VALUES(<xsl:for-each select="column"><xsl:text>?</xsl:text><xsl:if test="not(position()=last())">,</xsl:if></xsl:for-each>)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        <xsl:call-template name="col_insert_timestamp"/>
        <xsl:call-template name="col_assign_insert"/>

        if (log.isDebugEnabled()) {<xsl:call-template name="col_insert_logging"/>
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();<xsl:if test="@cache='true'"><xsl:call-template name="cache"/></xsl:if>
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.set<xsl:value-of select="@javaname"/>Id(0);
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
     * Updates a <xsl:value-of select="@javaname"/>Data object in the database.
     * @param pCon  An open database connection.
     * @param pData  A <xsl:value-of select="@javaname"/>Data object to update. <xsl:call-template name="update_javadoc"/>
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, <xsl:value-of select="@javaname"/>Data pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE <xsl:value-of select="@name"/><xsl:text> SET </xsl:text><xsl:for-each select="column"><xsl:if test="@primary_key='false'"><xsl:value-of select="name"/> = ?<xsl:if test="not(position()=last())">,</xsl:if></xsl:if></xsl:for-each> WHERE <xsl:value-of select="$pk_col_val"/> = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        <xsl:call-template name="col_update_timestamp"/>
        int i = 1;
        <xsl:call-template name="col_assign_update"/>

        if (log.isDebugEnabled()) {<xsl:call-template name="col_update_logging"/>
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();
<xsl:if test="@cache='true'"><xsl:call-template name="cache"/></xsl:if>
        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a <xsl:value-of select="@javaname"/>Data object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param p<xsl:call-template name="pk_javaname"/> Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int p<xsl:value-of select="@javaname"/>Id)
        throws SQLException{
        int n = 0;
        String sql=&quot;DELETE FROM <xsl:value-of select="@name"/> WHERE <xsl:value-of select="$pk_col_val"/> = &quot; + p<xsl:value-of select="@javaname"/>Id;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes <xsl:value-of select="@javaname"/>Data objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM <xsl:value-of select="@name"/>");
        String where = pCriteria.getSqlClause();
        if (where != null &amp;&amp; !where.equals("")) {
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
     * Inserts a <xsl:value-of select="@javaname"/>Data log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A <xsl:value-of select="@javaname"/>Data object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, <xsl:value-of select="@javaname"/>Data pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO L<xsl:value-of select="@name"/> (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "<xsl:for-each select="column"><xsl:value-of select="name"/><xsl:if test="not(position()=last())">,</xsl:if></xsl:for-each>) VALUES(?,?,?,?,<xsl:for-each select="column"><xsl:text>?</xsl:text><xsl:if test="not(position()=last())">,</xsl:if></xsl:for-each>)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        <xsl:call-template name="log_col_assign_insert"/>


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a <xsl:value-of select="@javaname"/>Data object into the database.
     * @param pCon  An open database connection.
     * @param pData  A <xsl:value-of select="@javaname"/>Data object to insert.<xsl:call-template name="insert_javadoc"/>
     * @param pLogFl  Creates record in log table if true
     * @return new <xsl:value-of select="@javaname"/>Data() with the generated
     *         key set
     * @throws            SQLException
     */
    public static <xsl:value-of select="@javaname"/>Data insert(Connection pCon, <xsl:value-of select="@javaname"/>Data pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a <xsl:value-of select="@javaname"/>Data object in the database.
     * @param pCon  An open database connection.
     * @param pData  A <xsl:value-of select="@javaname"/>Data object to update. <xsl:call-template name="update_javadoc"/>
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, <xsl:value-of select="@javaname"/>Data pData, boolean pLogFl)
        throws SQLException {
        <xsl:value-of select="@javaname"/>Data oldData = null;
        if(pLogFl) {
          int id = pData.get<xsl:value-of select="@javaname"/>Id();
          try {
          oldData = <xsl:value-of select="@javaname"/>DataAccess.select(pCon,id);
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
     * Deletes a <xsl:value-of select="@javaname"/>Data object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param p<xsl:call-template name="pk_javaname"/> Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int p<xsl:value-of select="@javaname"/>Id, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO L<xsl:value-of select="@name"/> SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM <xsl:value-of select="@name"/> d WHERE <xsl:value-of select="$pk_col_val"/> = " + p<xsl:value-of select="@javaname"/>Id;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,p<xsl:value-of select="@javaname"/>Id);
        return n;
     }

    /**
     * Deletes <xsl:value-of select="@javaname"/>Data objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO L<xsl:value-of select="@name"/> SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM <xsl:value-of select="@name"/> d ");
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

</xsl:template>

<xsl:template name="cols_as_parms">
<xsl:for-each select="column"><xsl:value-of select="javatype"/> p<xsl:value-of select="javaname"/><xsl:if test="not(position()=last())"><xsl:text>,</xsl:text></xsl:if></xsl:for-each>
</xsl:template>


<xsl:template name="pk_col1">
<xsl:for-each select="column"><xsl:if test="@primary_key='true'"><xsl:value-of select="name"/></xsl:if></xsl:for-each>
</xsl:template>

<xsl:template name="pk_javaname">
<xsl:for-each select="column"><xsl:if test="@primary_key='true'"><xsl:value-of select="javaname"/></xsl:if></xsl:for-each>
</xsl:template>

<xsl:template name="col_list">
<xsl:for-each select="column"><xsl:value-of select="name"/><xsl:if test="not(position()=last())">,</xsl:if></xsl:for-each>
</xsl:template>

<xsl:template name="col_list_explicit">
<xsl:for-each select="column"><xsl:value-of select="../@name"/>.<xsl:value-of select="name"/><xsl:if test="not(position()=last())">,</xsl:if></xsl:for-each>
</xsl:template>

<xsl:template name="col_const">
<xsl:for-each select="column">
    /** &lt;code&gt;<xsl:value-of select="name"/>&lt;/code&gt; <xsl:value-of select="name"/> column of table <xsl:value-of select="../@name"/> */
    public static final String <xsl:value-of select="name"/> = "<xsl:value-of select="name"/>";</xsl:for-each>
</xsl:template>

<xsl:template name="col_assign_update">
<xsl:for-each select="column"><xsl:if test="@primary_key='false'"><xsl:if test="@nullable='true' and @foreign_key='true' and javatype='int'">
        if (pData.get<xsl:value-of select="javaname"/>() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.set<xsl:value-of select="rsgetter"/>(i++,pData.get<xsl:value-of select="javaname"/>());
        }
</xsl:if>
<xsl:if test="not(@nullable='true' and @foreign_key='true' and javatype='int') and not(javatype='boolean') and javatype='byte[]'">
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.get<xsl:value-of select="javaname"/>()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.get<xsl:value-of select="javaname"/>());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }</xsl:if>
<xsl:if test="not(@nullable='true' and @foreign_key='true' and javatype='int') and javatype='boolean'  and not(javatype='byte[]')">
        pstmt.setInt(i++, pData.get<xsl:value-of select="javaname"/>()?1:0);</xsl:if>
<xsl:if test="not(@nullable='true' and @foreign_key='true' and javatype='int') and not(javatype='boolean')  and not(javatype='byte[]')">
        pstmt.set<xsl:value-of select="rsgetter"/>(i++,<xsl:if test="rsgetter='Date'">DBAccess.toSQLDate(</xsl:if><xsl:if test="rsgetter='Timestamp'">DBAccess.toSQLTimestamp(</xsl:if><xsl:if test="rsgetter='Time'">DBAccess.toSQLTime(</xsl:if>pData.get<xsl:value-of select="javaname"/>()<xsl:if test="rsgetter='Date'">)</xsl:if><xsl:if test="rsgetter='Timestamp'">)</xsl:if><xsl:if test="rsgetter='Time'">)</xsl:if>);</xsl:if>
</xsl:if>
</xsl:for-each>
<xsl:for-each select="column"><xsl:if test="@primary_key='true'">
        pstmt.set<xsl:value-of select="rsgetter"/>(i++,<xsl:if test="rsgetter='Date'">DBAccess.toSQLDate(</xsl:if><xsl:if test="rsgetter='Timestamp'">DBAccess.toSQLTimestamp(</xsl:if><xsl:if test="rsgetter='Time'">DBAccess.toSQLTime(</xsl:if>pData.get<xsl:value-of select="javaname"/>()<xsl:if test="rsgetter='Date'">)</xsl:if><xsl:if test="rsgetter='Timestamp'">)</xsl:if><xsl:if test="rsgetter='Time'">)</xsl:if>);</xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template name="col_assign_insert">
<xsl:for-each select="column"><xsl:if test="@nullable='true' and @foreign_key='true' and javatype='int'">
        if (pData.get<xsl:value-of select="javaname"/>() == 0) {
            pstmt.setNull(<xsl:value-of select="position()"/>, java.sql.Types.INTEGER);
        } else {
            pstmt.set<xsl:value-of select="rsgetter"/>(<xsl:value-of select="position()"/>,pData.get<xsl:value-of select="javaname"/>());
        }
</xsl:if>
<xsl:if test="not(@nullable='true' and @foreign_key='true')  and not(javatype='boolean') and javatype='byte[]'">
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(<xsl:value-of select="position()"/>, toBlob(pCon,pData.get<xsl:value-of select="javaname"/>()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.get<xsl:value-of select="javaname"/>());
                pstmt.setBinaryStream(<xsl:value-of select="position()"/>, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }</xsl:if>
<xsl:if test="not(@nullable='true' and @foreign_key='true') and javatype='boolean' and not(javatype='byte[]')">
        pstmt.setInt(<xsl:value-of select="position()"/>, pData.get<xsl:value-of select="javaname"/>()?1:0);</xsl:if>
<xsl:if test="not(@nullable='true' and @foreign_key='true' and javatype='int') and not(javatype='boolean') and not(javatype='byte[]')">
        pstmt.set<xsl:value-of select="rsgetter"/>(<xsl:value-of select="position()"/>,<xsl:if test="rsgetter='Date'">DBAccess.toSQLDate(</xsl:if><xsl:if test="rsgetter='Timestamp'">DBAccess.toSQLTimestamp(</xsl:if><xsl:if test="rsgetter='Time'">DBAccess.toSQLTime(</xsl:if>pData.get<xsl:value-of select="javaname"/>()<xsl:if test="rsgetter='Date'">)</xsl:if><xsl:if test="rsgetter='Timestamp'">)</xsl:if><xsl:if test="rsgetter='Time'">)</xsl:if>);</xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template name="col_insert_logging"><xsl:for-each select="column">
            log.debug("SQL:   <xsl:value-of select="name"/>="+pData.get<xsl:value-of select="javaname"/>());</xsl:for-each>
</xsl:template>

<xsl:template name="col_update_logging"><xsl:for-each select="column"><xsl:if test="@primary_key='false'">
            log.debug("SQL:   <xsl:value-of select="name"/>="+pData.get<xsl:value-of select="javaname"/>());</xsl:if></xsl:for-each>
</xsl:template>

<xsl:template name="col_insert_timestamp">
<xsl:for-each select="column"><xsl:if test="javaname='AddDate'">
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);
</xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template name="col_update_timestamp">
<xsl:for-each select="column"><xsl:if test="javaname='ModDate'">
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));
</xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template name="insert_javadoc">
<xsl:for-each select="column"><xsl:if test="javaname='AddDate'">
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. </xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template name="update_javadoc">
<xsl:for-each select="column"><xsl:if test="javaname='ModDate'">
     * The "ModDate" field of the object will be set to the current date.</xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template name="log_col_assign_insert">
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);
<xsl:for-each select="column"><xsl:if test="@nullable='true' and @foreign_key='true' and javatype='int'">
        if (pData.get<xsl:value-of select="javaname"/>() == 0) {
            pstmt.setNull(<xsl:value-of select="position()"/>+4, java.sql.Types.INTEGER);
        } else {
            pstmt.set<xsl:value-of select="rsgetter"/>(<xsl:value-of select="position()"/>+4,pData.get<xsl:value-of select="javaname"/>());
        }
</xsl:if>
<xsl:if test="not(@nullable='true' and @foreign_key='true' and javatype='int')">
        pstmt.set<xsl:value-of select="rsgetter"/>(<xsl:value-of select="position()"/>+4,<xsl:if test="rsgetter='Date'">DBAccess.toSQLDate(</xsl:if><xsl:if test="rsgetter='Timestamp'">DBAccess.toSQLTimestamp(</xsl:if><xsl:if test="rsgetter='Time'">DBAccess.toSQLTime(</xsl:if>pData.get<xsl:value-of select="javaname"/>()<xsl:if test="rsgetter='Date'">)</xsl:if><xsl:if test="rsgetter='Timestamp'">)</xsl:if><xsl:if test="rsgetter='Time'">)</xsl:if>);</xsl:if>
</xsl:for-each>
</xsl:template>
<xsl:template name="cache">
        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null &amp;&amp; cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
</xsl:template>

</xsl:stylesheet>
