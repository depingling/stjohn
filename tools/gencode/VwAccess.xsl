<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>

<xsl:template match="table">
/* DO NOT EDIT - Generated code from XSL file VwDataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        <xsl:value-of select="@javaname"/>VwAccess
 * Description:  This class is used to build access methods to the <xsl:value-of select="@name"/> table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file VwAccess.xsl
 */

import com.cleanwise.service.api.value.<xsl:value-of select="@javaname"/>Vw;
import com.cleanwise.service.api.value.<xsl:value-of select="@javaname"/>VwVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * &lt;code&gt;<xsl:value-of select="@javaname"/>VwAccess&lt;/code&gt;
 */
public class <xsl:value-of select="@javaname"/>VwAccess
{
    private static Category log = Category.getInstance(<xsl:value-of select="@javaname"/>VwAccess.class.getName());

    /** &lt;code&gt;<xsl:value-of select="@name"/>&lt;/code&gt; table name */
    public static final String <xsl:value-of select="@name"/> = "<xsl:value-of select="@name"/>";
    <xsl:call-template name="col_const"/>

    /**
     * Constructor.
     */
    public <xsl:value-of select="@javaname"/>VwAccess()
    {
    }

    /**
     * Gets a <xsl:value-of select="@javaname"/>VwVector object that consists
     * of <xsl:value-of select="@javaname"/>Vw objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new <xsl:value-of select="@javaname"/>VwVector()
     * @throws            SQLException
     */
    public static <xsl:value-of select="@javaname"/>VwVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
     * Gets a <xsl:value-of select="@javaname"/>VwVector object that consists
     * of <xsl:value-of select="@javaname"/>Vw objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new <xsl:value-of select="@javaname"/>VwVector()
     * @throws            SQLException
     */
    public static <xsl:value-of select="@javaname"/>VwVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT <xsl:call-template name="col_list"/> FROM <xsl:value-of select="@name"/>");
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
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
              stmt.setMaxRows(pMaxRows);
        }
        ResultSet rs=stmt.executeQuery(sql);
        <xsl:value-of select="@javaname"/>VwVector v = new <xsl:value-of select="@javaname"/>VwVector();
        <xsl:value-of select="@javaname"/>Vw x=null;
        while (rs.next()) {
            // build the object
            x = <xsl:value-of select="@javaname"/>Vw.createValue();
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
     * Gets a <xsl:value-of select="@javaname"/>VwVector object that consists
     * of <xsl:value-of select="@javaname"/>Vw objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new <xsl:value-of select="@javaname"/>VwVector()
     * @throws            SQLException
     */
    public static <xsl:value-of select="@javaname"/>VwVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows, List pOtherTables)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT <xsl:call-template name="col_assign_update"/> FROM <xsl:value-of select="@name"/>");

        Iterator it = pOtherTables.iterator();
        while(it.hasNext()){
                String otherTable = (String) it.next();
                sqlBuf.append(",");
                sqlBuf.append(otherTable);
        }

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
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
              stmt.setMaxRows(pMaxRows);
        }
        ResultSet rs=stmt.executeQuery(sql);
        <xsl:value-of select="@javaname"/>VwVector v = new <xsl:value-of select="@javaname"/>VwVector();
        <xsl:value-of select="@javaname"/>Vw x=null;
        while (rs.next()) {
            // build the object
            x = <xsl:value-of select="@javaname"/>Vw.createValue();
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
     * Gets a <xsl:value-of select="@javaname"/>VwVector object that consists
     * of <xsl:value-of select="@javaname"/>Vw objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for <xsl:value-of select="@javaname"/>Vw
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new <xsl:value-of select="@javaname"/>VwVector()
     * @throws            SQLException
     */
    public static <xsl:value-of select="@javaname"/>VwVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        <xsl:value-of select="@javaname"/>VwVector v = new <xsl:value-of select="@javaname"/>VwVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT <xsl:call-template name="col_list"/> FROM <xsl:value-of select="@name"/> WHERE <xsl:call-template name="pk_col"/> IN (");

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
            <xsl:value-of select="@javaname"/>Vw x=null;
            while (rs.next()) {
                // build the object
                x=<xsl:value-of select="@javaname"/>Vw.createValue();
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
     * Gets a <xsl:value-of select="@javaname"/>VwVector object of all
     * <xsl:value-of select="@javaname"/>Vw objects in the database.
     * @param pCon An open database connection.
     * @return new <xsl:value-of select="@javaname"/>VwVector()
     * @throws            SQLException
     */
    public static <xsl:value-of select="@javaname"/>VwVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT <xsl:call-template name="col_list"/> FROM <xsl:value-of select="@name"/>";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        <xsl:value-of select="@javaname"/>VwVector v = new <xsl:value-of select="@javaname"/>VwVector();
        <xsl:value-of select="@javaname"/>Vw x = null;
        while (rs.next()) {
            // build the object
            x = <xsl:value-of select="@javaname"/>Vw.createValue();
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM <xsl:value-of select="@name"/>");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM <xsl:value-of select="@name"/>");
        String where = pCriteria.getSqlClause();
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
}
</xsl:template>

<xsl:template name="cols_as_parms">
<xsl:for-each select="column"><xsl:value-of select="javatype"/> p<xsl:value-of select="javaname"/><xsl:if test="not(position()=last())"><xsl:text>,</xsl:text></xsl:if></xsl:for-each>
</xsl:template>

<xsl:template name="pk_col">
<xsl:for-each select="column"><xsl:if test="@primary_key='true'"><xsl:value-of select="name"/></xsl:if></xsl:for-each>
</xsl:template>

<xsl:template name="pk_javaname">
<xsl:for-each select="column"><xsl:if test="@primary_key='true'"><xsl:value-of select="javaname"/></xsl:if></xsl:for-each>
</xsl:template>

<xsl:template name="col_list">
<xsl:for-each select="column"><xsl:value-of select="name"/><xsl:if test="not(position()=last())">,</xsl:if></xsl:for-each>
</xsl:template>

<xsl:template name="col_list_explicit">
<xsl:for-each select="column"><xsl:value-of select="@name"/><xsl:value-of select="name"/><xsl:value-of select="name"/><xsl:if test="not(position()=last())">,</xsl:if></xsl:for-each>
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
<xsl:if test="not(@nullable='true' and @foreign_key='true' and javatype='int')">
        pstmt.set<xsl:value-of select="rsgetter"/>(i++,<xsl:if test="rsgetter='Date'">DBAccess.toSQLDate(</xsl:if><xsl:if test="rsgetter='Time'">DBAccess.toSQLTime(</xsl:if>pData.get<xsl:value-of select="javaname"/>()<xsl:if test="rsgetter='Date'">)</xsl:if><xsl:if test="rsgetter='Time'">)</xsl:if>);</xsl:if>
</xsl:if>
</xsl:for-each>
<xsl:for-each select="column"><xsl:if test="@primary_key='true'">
        pstmt.set<xsl:value-of select="rsgetter"/>(i++,<xsl:if test="rsgetter='Date'">DBAccess.toSQLDate(</xsl:if><xsl:if test="rsgetter='Time'">DBAccess.toSQLTime(</xsl:if>pData.get<xsl:value-of select="javaname"/>()<xsl:if test="rsgetter='Date'">)</xsl:if><xsl:if test="rsgetter='Time'">)</xsl:if>);</xsl:if>
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
<xsl:if test="not(@nullable='true' and @foreign_key='true' and javatype='int')">
        pstmt.set<xsl:value-of select="rsgetter"/>(<xsl:value-of select="position()"/>,<xsl:if test="rsgetter='Date'">DBAccess.toSQLDate(</xsl:if><xsl:if test="rsgetter='Time'">DBAccess.toSQLTime(</xsl:if>pData.get<xsl:value-of select="javaname"/>()<xsl:if test="rsgetter='Date'">)</xsl:if><xsl:if test="rsgetter='Time'">)</xsl:if>);</xsl:if>
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

</xsl:stylesheet>
