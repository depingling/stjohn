<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>

<xsl:template match="table">
&lt;!-- DO NOT EDIT - Generated code from XSL file DTD.xsl --&gt;

&lt;?xml encoding="US-ASCII"?&gt;

&lt;!ELEMENT <xsl:value-of select="@javaname"/>List (<xsl:value-of select="@javaname"/>)+&gt;
&lt;!ELEMENT <xsl:value-of select="@javaname"/> (<xsl:call-template name="nested_elements"/>)&gt;
&lt;!ATTLIST <xsl:value-of select="@javaname"/> Id ID #REQUIRED&gt;<xsl:for-each select="column">
<xsl:if test="@primary_key='false'">
&lt;!ELEMENT <xsl:value-of select="javaname"/> (#PCDATA)&gt;</xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template name="nested_elements">
<xsl:for-each select="column"><xsl:if test="@primary_key='false'"><xsl:if test="not(position()=2)">,</xsl:if><xsl:value-of select="javaname"/></xsl:if></xsl:for-each>
</xsl:template>

</xsl:stylesheet>
