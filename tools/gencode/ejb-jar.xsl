<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>



<xsl:template match="beans">

<xsl:text disable-output-escaping="yes"><![CDATA[ <!DOCTYPE ejb-jar PUBLIC '-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 1.1//EN' 'http://java.sun.com/j2ee/dtds/ejb-jar_1_1.dtd'>]]></xsl:text>

<ejb-jar>
  <description>St. John EJBs </description>
  <display-name>St. John EJBs</display-name>
  <enterprise-beans>
    
    <xsl:for-each select="bean">
    <session>
      <ejb-name><xsl:value-of select="@javaname"/></ejb-name>
      <home>com.cleanwise.service.api.session.<xsl:value-of select="@javaname"/>Home</home>
      <remote>com.cleanwise.service.api.session.<xsl:value-of select="@javaname"/></remote>
      <ejb-class>com.cleanwise.service.api.session.<xsl:value-of select="@javaname"/>Bean</ejb-class>
      <session-type>Stateless</session-type>
      <transaction-type>Container</transaction-type>
    </session>
    </xsl:for-each>


</enterprise-beans>
</ejb-jar>

</xsl:template>
</xsl:stylesheet>