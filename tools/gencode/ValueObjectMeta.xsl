<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

          <xsl:variable name="classname" select="concat(@javaname,'Data')"/>
        <xsl:variable name="pk_col_val"><xsl:call-template name="pk_col1"/></xsl:variable>  <xsl:output method="text"/>
    <xsl:template match="table">
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        <xsl:value-of select="@javaname"/>DataMeta
 * Description:  This is a meta class describing the object of database table  <xsl:value-of select="@name"/>.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.<xsl:value-of select="$classname"/>Access;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * &lt;code&gt;<xsl:value-of select="@javaname"/>DataMeta&lt;/code&gt; is a meta class describing the database table object <xsl:value-of select="@name"/>.
 */
public class <xsl:value-of select="@javaname"/>DataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = <xsl:choose><xsl:when  test="string-length(serialVersionDataMetaUID)=0">-1L; //ERROR. No Serial Version Provided</xsl:when><xsl:otherwise><xsl:value-of select="serialVersionDataMetaUID"/>;</xsl:otherwise></xsl:choose>
       
    /**
     * Constructor.
     */
    public <xsl:value-of select="@javaname"/>DataMeta(TableObject pData) {
        <xsl:call-template name="constructor_1"/>
    }

    /**
     * Constructor.
     */
    public <xsl:value-of select="@javaname"/>DataMeta(TableField... fields) {
        <xsl:call-template name="constructor_2"/>
    }

}
    </xsl:template>


    <xsl:template name="constructor_1">
        super(<xsl:value-of select="$classname"/>Access.<xsl:value-of select="@name"/>);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(<xsl:value-of select="$classname"/>Access.<xsl:value-of select="$pk_col_val"/>);
            fm.setValue(pData.getFieldValue(<xsl:value-of select="$classname"/>Access.<xsl:value-of select="$pk_col_val"/>));
            addField(fm);
        }
    </xsl:template>
    <xsl:template name="constructor_2">
        super(<xsl:value-of select="$classname"/>Access.<xsl:value-of select="@name"/>);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    </xsl:template>
    <xsl:template name="pk_col1">
<xsl:for-each select="column"><xsl:if test="@primary_key='true'"><xsl:value-of select="name"/></xsl:if></xsl:for-each>
    </xsl:template>

</xsl:stylesheet>






