<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="text"/>
<xsl:template match="table">
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
<xsl:for-each select="import">import <xsl:value-of select="value"/>;</xsl:for-each>

/**
 * Title:        <xsl:value-of select="@javaname"/>ViewVector
 * Description:  Container object for <xsl:value-of select="@javaname"/>View objects
 * Purpose:      Provides container storage for <xsl:value-of select="@javaname"/>View objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * &lt;code&gt;<xsl:value-of select="@javaname"/>ViewVector&lt;/code&gt;
 */
public class <xsl:value-of select="@javaname"/>ViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = <xsl:choose><xsl:when 
       test="string-length(serialVersionVectorUID)=0">!!! ERROR. No Serial Version Provided !!!</xsl:when
	   ><xsl:otherwise><xsl:value-of select="serialVersionVectorUID"/>;</xsl:otherwise></xsl:choose>
    /**
     * Constructor.
     */
    public <xsl:value-of select="@javaname"/>ViewVector () {}

    String _sortField = "";
    boolean _ascFl = true;
    /**
     * Sort
     */
    public void sort(String pFieldName) {
       sort(pFieldName,true);     
    }

    public void sort(String pFieldName, boolean pAscFl) {
       _sortField = pFieldName;
       _ascFl = pAscFl;       
       Collections.sort(this,this);
    }

    /*
    *
    */
    public int compare(Object o1, Object o2)
    {
      int retcode = -1;
      <xsl:value-of select="@javaname"/>View obj1 = (<xsl:value-of select="@javaname"/>View)o1;
      <xsl:value-of select="@javaname"/>View obj2 = (<xsl:value-of select="@javaname"/>View)o2;
      <xsl:for-each select="property">
      <xsl:if test="sort='true'">
      if("<xsl:value-of select="javaname"/>".equalsIgnoreCase(_sortField)) {
        <xsl:variable name="basic" select="'false'"/>
    		<xsl:value-of select="javatype"/> i1 = obj1.get<xsl:value-of select="javaname"/>();
        <xsl:value-of select="javatype"/> i2 = obj2.get<xsl:value-of select="javaname"/>();
        <xsl:if test="javatype='byte' or javatype='short' or javatype='int' or javatype='long' 
                   or javatype='double' or javatype='float'  
                   or javatype='byte' or javatype='char'">retcode = i1-i2;</xsl:if>
        <xsl:if test="javatype='boolean'">if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;</xsl:if>
        <xsl:if test="javatype!='byte' and javatype!='short' and javatype!='int' and javatype!='long' 
                   and javatype!='double' and javatype!='float'  
                   and javatype!='byte' and javatype!='char'
                   and javatype!='boolean'">if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);</xsl:if>
      }
      </xsl:if>
      </xsl:for-each>
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
</xsl:template>

</xsl:stylesheet>






