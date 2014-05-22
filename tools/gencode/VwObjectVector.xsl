<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="text"/>
<xsl:template match="table">
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        <xsl:value-of select="@javaname"/>VwVector
 * Description:  Container object for <xsl:value-of select="@javaname"/>Vw objects
 * Purpose:      Provides container storage for <xsl:value-of select="@javaname"/>Vw objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * &lt;code&gt;<xsl:value-of select="@javaname"/>VwVector&lt;/code&gt;
 */
public class <xsl:value-of select="@javaname"/>VwVector extends java.util.ArrayList
{
    /**
     * Constructor.
     */
    public <xsl:value-of select="@javaname"/>VwVector () {}
}
</xsl:template>

</xsl:stylesheet>






