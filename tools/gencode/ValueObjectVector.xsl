<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="text"/>
<xsl:template match="table">
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        <xsl:value-of select="@javaname"/>DataVector
 * Description:  Container object for <xsl:value-of select="@javaname"/>Data objects
 * Purpose:      Provides container storage for <xsl:value-of select="@javaname"/>Data objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * &lt;code&gt;<xsl:value-of select="@javaname"/>DataVector&lt;/code&gt;
 */
public class <xsl:value-of select="@javaname"/>DataVector extends java.util.ArrayList
{
    private static final long serialVersionUID = <xsl:choose><xsl:when 
       test="string-length(serialVersionVectorUID)=0">!!! ERROR. No Serial Version Provided !!!</xsl:when
	   ><xsl:otherwise><xsl:value-of select="serialVersionVectorUID"/>;</xsl:otherwise></xsl:choose>
    /**
     * Constructor.
     */
    public <xsl:value-of select="@javaname"/>DataVector () {}
}
</xsl:template>

</xsl:stylesheet>






