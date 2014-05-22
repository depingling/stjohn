<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="text"/>
<xsl:variable name="classname" select="concat(@javaname,'Data')"/>
<xsl:template match="table">
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        <xsl:value-of select="@javaname"/>Data
 * Description:  This is a ValueObject class wrapping the database table <xsl:value-of select="@name"/>.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.<xsl:value-of select="$classname"/>Access;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * &lt;code&gt;<xsl:value-of select="@javaname"/>Data&lt;/code&gt; is a ValueObject class wrapping of the database table <xsl:value-of select="@name"/>.
 */
public class <xsl:value-of select="@javaname"/>Data extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = <xsl:choose><xsl:when 
       test="string-length(serialVersionDataUID)=0">!!! ERROR. No Serial Version Provided !!!</xsl:when
	   ><xsl:otherwise><xsl:value-of select="serialVersionDataUID"/>;</xsl:otherwise></xsl:choose>
    <xsl:for-each select="column">
    private <xsl:value-of select="javatype"/> m<xsl:value-of select="javaname"/>;// SQL type:<xsl:value-of select="type"/><xsl:if test="@nullable='false'">, not null</xsl:if> </xsl:for-each>

    /**
     * Constructor.
     */
    public <xsl:value-of select="@javaname"/>Data ()
    {<xsl:call-template name="remove_nulls"/>
    }

    /**
     * Constructor.
     */
    public <xsl:value-of select="@javaname"/>Data(<xsl:call-template name="col_param_list"/>)
    {
        <xsl:call-template name="col_assignment"/>
    }

    /**
     * Creates a new <xsl:value-of select="@javaname"/>Data
     *
     * @return
     *  Newly initialized <xsl:value-of select="@javaname"/>Data object.
     */
    public static <xsl:value-of select="@javaname"/>Data createValue ()
    {
        <xsl:value-of select="@javaname"/>Data valueData = new <xsl:value-of select="@javaname"/>Data();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this <xsl:value-of select="@javaname"/>Data object
     */
    public String toString()
    {
        return "["<xsl:call-template name="col_string"/> + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement(&quot;<xsl:value-of select="@javaname"/>&quot;);
        <xsl:call-template name="to_xml"/>
        return root;
    }

    /**
    * creates a clone of this object, the <xsl:value-of select="@javaname"/>Id field is not cloned.
    *
    * @return <xsl:value-of select="@javaname"/>Data object
    */
    public Object clone(){
        <xsl:value-of select="@javaname"/>Data myClone = new <xsl:value-of select="@javaname"/>Data();
        <xsl:call-template name="clone"/>
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        <xsl:for-each select="column">
        <xsl:if test="position()>1"> else </xsl:if>if (<xsl:value-of select="$classname"/>Access.<xsl:value-of select="name"/>.equals(pFieldName)) {
            return get<xsl:value-of select="javaname"/>();
        }</xsl:for-each> else {
            return null;
        }

    }
    /**
    * Gets table name
    *
    * @return Table name
    */
    public String getTable() {
        return <xsl:value-of select="$classname"/>Access.<xsl:value-of select="@name"/>;
    }

    <xsl:for-each select="column">
    <xsl:apply-templates select="." />
    </xsl:for-each>

}
</xsl:template>

<xsl:template match="column">
    /**
     * Sets the <xsl:value-of select="javaname"/> field.<xsl:call-template name="required_field"/>
     *
     * @param p<xsl:value-of select="javaname"/>
     *  <xsl:value-of select="javatype"/> to use to update the field.
     */
    public void set<xsl:value-of select="javaname"/>(<xsl:value-of select="javatype"/> p<xsl:value-of select="javaname"/>){
        this.m<xsl:value-of select="javaname"/> = p<xsl:value-of select="javaname"/>;
        setDirty(true);
    }
    /**
     * Retrieves the <xsl:value-of select="javaname"/> field.
     *
     * @return
     *  <xsl:value-of select="javatype"/> containing the <xsl:value-of select="javaname"/> field.
     */
    public <xsl:value-of select="javatype"/> get<xsl:value-of select="javaname"/>(){
        return m<xsl:value-of select="javaname"/>;
    }
</xsl:template>

<xsl:template name="to_xml">
        Element node;
<xsl:for-each select="column">
<xsl:if test="@primary_key='true'">
        root.setAttribute("Id", String.valueOf(m<xsl:value-of select="javaname"/>));
</xsl:if>
<xsl:if test="@primary_key='false'">
        node =  doc.createElement(&quot;<xsl:value-of select="javaname"/>&quot;);
        node.appendChild(doc.createTextNode(String.valueOf(m<xsl:value-of select="javaname"/>)));
        root.appendChild(node);
</xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template name="clone">
<xsl:for-each select="column">

        <xsl:if test="@primary_key='false'">
        <xsl:if test="javatype='Date'">
        if(m<xsl:value-of select="javaname"/> != null){
                myClone.m<xsl:value-of select="javaname"/> = (Date) m<xsl:value-of select="javaname"/>.clone();
        }
        </xsl:if>
        <xsl:if test="not(javatype='Date')">
        myClone.m<xsl:value-of select="javaname"/> = m<xsl:value-of select="javaname"/>;
        </xsl:if>
        </xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template name="col_param_list">
<xsl:for-each select="column"><xsl:value-of select="javatype"/> parm<xsl:value-of select="position()"/><xsl:if test="not(position()=last())">, </xsl:if></xsl:for-each>
</xsl:template>

<xsl:template name="col_string">
<xsl:for-each select="column"> + &quot;<xsl:if test="not(position()=1)">, </xsl:if><xsl:value-of select="javaname"/>=&quot; + m<xsl:value-of select="javaname"/></xsl:for-each>
</xsl:template>

<xsl:template name="col_assignment">
<xsl:for-each select="column">m<xsl:value-of select="javaname"/> = parm<xsl:value-of select="position()"/>;
        </xsl:for-each></xsl:template>

<xsl:template name="remove_nulls">
<xsl:for-each select="column"><xsl:if test="javatype='String'">
        m<xsl:value-of select="javaname"/> = "";</xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template name="required_field">
<xsl:if test="@nullable='false'"> This field is required to be set in the database.</xsl:if>
</xsl:template>
</xsl:stylesheet>






