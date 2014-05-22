<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="text"/>
<xsl:template match="database">
/* Tables */

    <xsl:for-each select="table">
CREATE TABLE <xsl:value-of select="@name"/> (
    <xsl:for-each select="column">
    <xsl:value-of select="name"/><xsl:text> </xsl:text><xsl:value-of select="type"
	/><xsl:if test="type='NUMBER'">(<xsl:value-of select="@size"
	/><xsl:if test="@digits!='0'">,<xsl:value-of select="@digits"
	/></xsl:if>)</xsl:if
	><xsl:if test="type='VARCHAR2'">(<xsl:value-of select="@size"/>)</xsl:if
	><xsl:text> </xsl:text><xsl:if test="@nullable!='true'">NOT<xsl:text> </xsl:text
	></xsl:if>NULL,
	</xsl:for-each>
    CONSTRAINT <xsl:value-of select="@name"/><xsl:for-each select="column"
	><xsl:if test="@primary_key='true'"
	>_PK PRIMARY KEY (<xsl:value-of select="name"/>)</xsl:if></xsl:for-each>	
);
	</xsl:for-each>

/* Forieign Keys */

    <xsl:for-each select="table">
    <xsl:call-template name="ForeignKey">
       <xsl:with-param name="TableName" select="@name" />
    </xsl:call-template>
	</xsl:for-each>

/* Indexes */
    <xsl:for-each select="table">
    <xsl:call-template name="Index">
       <xsl:with-param name="TableName" select="@name" />
    </xsl:call-template>
	</xsl:for-each>

</xsl:template>

  <xsl:template name="ForeignKey">
  <xsl:param name="TableName" />
    <xsl:for-each select="fkey">   
	ALTER TABLE <xsl:value-of select="$TableName"/> ADD CONSTRAINT <xsl:value-of select="@name"/>
	   FOREIGN KEY (<xsl:for-each select="fkeyref"
	   ><xsl:if test="position()>1">,</xsl:if
	   ><xsl:value-of select="@fkColumnName"/></xsl:for-each>)
	   REFERENCES <xsl:value-of select="@pkTableName"/> (<xsl:for-each select="fkeyref"
	   ><xsl:if test="position()>1">,</xsl:if
	   ><xsl:value-of select="@pkColumnName"/></xsl:for-each>);
    </xsl:for-each>
</xsl:template>

  <xsl:template name="Index">
  <xsl:param name="TableName" />
    <xsl:for-each select="index">   
	<xsl:if test="substring(@name,(string-length(@name)-string-length('_PK'))+1)!='_PK' and not(starts-with(@name,'SYS'))">
	CREATE <xsl:if test="@unique='UNIQUE'">UNIQUE </xsl:if>INDEX <xsl:value-of select="@name"/> ON 
	  <xsl:value-of select="$TableName"/> (
	   <xsl:for-each select="indcolumn"><xsl:if test="position()>1">, </xsl:if
	   ><xsl:value-of select="@name"/><xsl:if test="@ascdesc!='ASC'"> DESC</xsl:if>
	   </xsl:for-each>);
	</xsl:if>
    </xsl:for-each>
</xsl:template>

</xsl:stylesheet>
