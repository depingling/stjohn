<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="text"/>
<xsl:template match="table">
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        <xsl:value-of select="@javaname"/>Vw
 * Description:  This is a ValueObject class wrapping the database table <xsl:value-of select="@name"/>.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;

<!--
import jxl.*;
import jxl.write.*;
import java.math.BigDecimal;
-->
/**
 * &lt;code&gt;<xsl:value-of select="@javaname"/>Vw&lt;/code&gt; is a ValueObject class wrapping of the database table <xsl:value-of select="@name"/>.
 */
public class <xsl:value-of select="@javaname"/>Vw extends ValueObject
{
    <xsl:for-each select="column">
    private <xsl:value-of select="javatype"/> m<xsl:value-of select="javaname"/>;// SQL type:<xsl:value-of select="type"/><xsl:if test="@nullable='false'">, not null</xsl:if> </xsl:for-each>

    /**
     * Constructor.
     */
    private <xsl:value-of select="@javaname"/>Vw ()
    {<xsl:call-template name="remove_nulls"/>
    }

    /**
     * Constructor. 
     */
    public <xsl:value-of select="@javaname"/>Vw(<xsl:call-template name="col_param_list"/>)
    {
        <xsl:call-template name="col_assignment"/>
    }

    /**
     * Creates a new <xsl:value-of select="@javaname"/>Vw
     *
     * @return
     *  Newly initialized <xsl:value-of select="@javaname"/>Vw object.
     */
    public static <xsl:value-of select="@javaname"/>Vw createValue () 
    {
        <xsl:value-of select="@javaname"/>Vw valueData = new <xsl:value-of select="@javaname"/>Vw();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this <xsl:value-of select="@javaname"/>Vw object
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
        Element root = doc.createElement(&quot;<xsl:value-of select="@javaname"/>&quot;);
	<xsl:call-template name="to_xml"/>
        return root;
    }

<!--
    /**
     * Gets field names
     *
     * @return ElementNode.
     */
    public ArrayList getColums(Document doc) {
       ArrayList fieldNames = new ArrayList();
<xsl:for-each select="column">
       fieldNames.add(m<xsl:value-of select="javaname"/>);
</xsl:for-each>
       return fieldNames;
    }

    /**
     * Put values to XLS sheet.
     * @parameter xls sheet 
     * @parameter row 
     * @throws Exception
     */
    public void toXls(WritableSheet sheet, int row) 
    throws Exception
    {
      int col = 0;
      jxl.write.Label label;
      jxl.write.Number number;
      jxl.write.DateTime date;
      
<xsl:for-each select="column">
<xsl:choose>
<xsl:when test="javatype='short' or javatype='int' 
            or javatype='long' 
            or javatype='double' or javatype='float'">    
      number = new jxl.write.Number(col++,row, m<xsl:value-of select="javaname"/>);
      sheet.addCell(number);</xsl:when>
<xsl:when test="javatype='java.math.BigDecimal'"> 
      if(m<xsl:value-of select="javaname"/>==null) m<xsl:value-of select="javaname"/> = new BigDecimal(0);
      number = new jxl.write.Number(col++,row, m<xsl:value-of select="javaname"/>.doubleValue());
      sheet.addCell(number);</xsl:when>
<xsl:when test="javatype='Date'"> 
      date = new jxl.write.DateTime(col++,row, m<xsl:value-of select="javaname"/>);
      sheet.addCell(date);</xsl:when>
<xsl:otherwise>     
      label = new jxl.write.Label(col++,row, String.valueOf(m<xsl:value-of select="javaname"/>));
      sheet.addCell(label);</xsl:otherwise>
</xsl:choose>
</xsl:for-each>
    }
-->
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
<xsl:for-each select="column">
<xsl:if test="position()=1">root.setAttribute("Id", String.valueOf(m<xsl:value-of select="javaname"/>));

	Element node;
</xsl:if>
<xsl:if test="not(position()=1)">
        node = doc.createElement(&quot;<xsl:value-of select="javaname"/>&quot;);
        node.appendChild(doc.createTextNode(String.valueOf(m<xsl:value-of select="javaname"/>)));
        root.appendChild(node);
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






