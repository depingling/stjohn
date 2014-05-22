<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>



<xsl:template match="beans">


<jboss>
   <enterprise-beans>
    
    <xsl:for-each select="bean">
    <session>
       <ejb-name><xsl:value-of select="@javaname"/></ejb-name>
       <jndi-name>cleanwise/<xsl:value-of select="@javaname"/></jndi-name>
     </session>
    </xsl:for-each>

  </enterprise-beans>
</jboss>

</xsl:template>
</xsl:stylesheet>