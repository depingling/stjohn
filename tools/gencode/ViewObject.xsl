<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="text"/>
<xsl:template match="table">
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        <xsl:value-of select="@javaname"/>View
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;

<xsl:for-each select="import">import <xsl:value-of select="value"/>;</xsl:for-each>


/**
 * &lt;code&gt;<xsl:value-of select="@javaname"/>View&lt;/code&gt; is a ViewObject class for UI.
 */
public class <xsl:value-of select="@javaname"/>View
extends <xsl:if test="extends"><xsl:value-of select="extends"/> //</xsl:if>ValueObject
{
   <xsl:for-each select="interface">
   public interface <xsl:value-of select="name"/> {
   <xsl:for-each select="constant">&#09;&#09;public static final <xsl:value-of select="javatype"/>
       <xsl:value-of select="concat(@name,' ',javaname)"/>=<xsl:choose><xsl:when test='javatype="String"'>"<xsl:value-of select="javavalue"/>"</xsl:when></xsl:choose>;
   </xsl:for-each>}
   </xsl:for-each>
    private static final long serialVersionUID = <xsl:choose><xsl:when 
       test="string-length(serialVersionViewUID)=0">!!! ERROR. No Serial Version Provided !!!</xsl:when
	   ><xsl:otherwise><xsl:value-of select="serialVersionViewUID"/>;</xsl:otherwise></xsl:choose>
    <xsl:for-each select="property">
    private <xsl:value-of select="javatype"/> m<xsl:value-of select="javaname"/>;</xsl:for-each>

    /**
     * Constructor.
     */
    public <xsl:value-of select="@javaname"/>View ()
    {<xsl:call-template name="remove_nulls"/>
    }

    /**
     * Constructor. 
     */
    public <xsl:value-of select="@javaname"/>View(<xsl:call-template name="col_param_list"/>)
    {
        <xsl:call-template name="col_assignment"/>
    }

    /**
     * Creates a new <xsl:value-of select="@javaname"/>View
     *
     * @return
     *  Newly initialized <xsl:value-of select="@javaname"/>View object.
     */
    public static <xsl:value-of select="@javaname"/>View createValue () 
    {
        <xsl:value-of select="@javaname"/>View valueView = new <xsl:value-of select="@javaname"/>View();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this <xsl:value-of select="@javaname"/>View object
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

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public <xsl:value-of select="@javaname"/>View copy()  {
      <xsl:value-of select="@javaname"/>View obj = new <xsl:value-of select="@javaname"/>View();
      <xsl:for-each select="property">obj.set<xsl:value-of select="javaname"/>(m<xsl:value-of select="javaname"/>);
      </xsl:for-each>
      return obj;
    }

    <xsl:for-each select="property">
    <xsl:apply-templates select="." />
    </xsl:for-each>
    <xsl:if test="@includeResetMethod='true'">
    /**
    *Resets any state tracking this pbject may contain;
    */
    public void reset(){
        <xsl:call-template name="reset"/>
    }
    </xsl:if>
    
}
</xsl:template>

<xsl:template match="property">
    <xsl:if test="changeTracking='true'">
    /**
    * Keeps track if the <xsl:value-of select="javaname"/> property has been changed.  This is a best guess
    * as if the property was changed and then changed back to the original value it would not be reflected here.
    */
    private boolean m<xsl:value-of select="javaname"/>Changed = false;
    public boolean is<xsl:value-of select="javaname"/>Changed(){
        return m<xsl:value-of select="javaname"/>Changed;
    }
    </xsl:if>
    /**
     * Sets the <xsl:value-of select="javaname"/> property.<xsl:call-template name="required_field"/>
     *
     * @param p<xsl:value-of select="javaname"/>
     *  <xsl:value-of select="javatype"/> to use to update the property.
     */
    public void set<xsl:value-of select="javaname"/>(<xsl:value-of select="javatype"/> p<xsl:value-of select="javaname"/>){
        <xsl:choose><xsl:when test='changeTracking="true"'><xsl:choose>
          <xsl:when test='javatype="boolean"'>
        if(this.m<xsl:value-of select="javaname"/>!=(p<xsl:value-of select="javaname"/>)){
                m<xsl:value-of select="javaname"/>Changed = true;
                this.m<xsl:value-of select="javaname"/> = p<xsl:value-of select="javaname"/>;
        }
          </xsl:when>
          <xsl:when test='javatype="char"'>
        if(this.m<xsl:value-of select="javaname"/>!=(p<xsl:value-of select="javaname"/>)){
                m<xsl:value-of select="javaname"/>Changed = true;
                this.m<xsl:value-of select="javaname"/> = p<xsl:value-of select="javaname"/>;
        }
          </xsl:when>
          <xsl:when test='javatype="double"'>
        if(this.m<xsl:value-of select="javaname"/>!=(p<xsl:value-of select="javaname"/>)){
                m<xsl:value-of select="javaname"/>Changed = true;
                this.m<xsl:value-of select="javaname"/> = p<xsl:value-of select="javaname"/>;
        }
          </xsl:when>
          <xsl:when test='javatype="float"'>
        if(this.m<xsl:value-of select="javaname"/>!=(p<xsl:value-of select="javaname"/>)){
                m<xsl:value-of select="javaname"/>Changed = true;
                this.m<xsl:value-of select="javaname"/> = p<xsl:value-of select="javaname"/>;
        }
          </xsl:when>
          <xsl:when test='javatype="int"'>
        if(this.m<xsl:value-of select="javaname"/>!=(p<xsl:value-of select="javaname"/>)){
                m<xsl:value-of select="javaname"/>Changed = true;
                this.m<xsl:value-of select="javaname"/> = p<xsl:value-of select="javaname"/>;
        }
          </xsl:when>
          <xsl:when test='javatype="long"'>
        if(this.m<xsl:value-of select="javaname"/>!=(p<xsl:value-of select="javaname"/>)){
                m<xsl:value-of select="javaname"/>Changed = true;
                this.m<xsl:value-of select="javaname"/> = p<xsl:value-of select="javaname"/>;
        }
          </xsl:when>
          <xsl:when test='javatype="short"'>
        if(this.m<xsl:value-of select="javaname"/>!=(p<xsl:value-of select="javaname"/>)){
                m<xsl:value-of select="javaname"/>Changed = true;
                this.m<xsl:value-of select="javaname"/> = p<xsl:value-of select="javaname"/>;
        }
          </xsl:when>
          <xsl:otherwise>
        if(this.m<xsl:value-of select="javaname"/> == null &amp;&amp; p<xsl:value-of select="javaname"/>!= null){
                this.m<xsl:value-of select="javaname"/>Changed = true;
                this.m<xsl:value-of select="javaname"/> = p<xsl:value-of select="javaname"/>;
        }else if(this.m<xsl:value-of select="javaname"/> == null &amp;&amp; p<xsl:value-of select="javaname"/> == null){
        }else if(!this.m<xsl:value-of select="javaname"/>.equals(p<xsl:value-of select="javaname"/>)){
                this.m<xsl:value-of select="javaname"/>Changed = true;
                this.m<xsl:value-of select="javaname"/> = p<xsl:value-of select="javaname"/>;
        }
          </xsl:otherwise>
        </xsl:choose>
        </xsl:when>
        <xsl:otherwise>this.m<xsl:value-of select="javaname"/> = p<xsl:value-of select="javaname"/>;</xsl:otherwise></xsl:choose>
    }
    /**
     * Retrieves the <xsl:value-of select="javaname"/> property.
     *
     * @return
     *  <xsl:value-of select="javatype"/> containing the <xsl:value-of select="javaname"/> property.
     */
    public <xsl:value-of select="javatype"/> get<xsl:value-of select="javaname"/>(){
        return m<xsl:value-of select="javaname"/>;
    }

<xsl:if test="indexAccessible='true'">
      <xsl:choose>
        <xsl:when test='contains(javatype,"Vector")'>
          <xsl:call-template name="indexAccessible">
            <xsl:with-param name="returnType" select='substring-before(javatype,"Vector")' />
            <xsl:with-param name="javaname" select='javaname' />
          </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
          <xsl:call-template name="indexAccessible">
            <xsl:with-param name="returnType" select='javatype' />
            <xsl:with-param name="javaname" select='javaname' />
          </xsl:call-template>
        </xsl:otherwise>
      </xsl:choose>
</xsl:if>

</xsl:template>

<xsl:template name="indexAccessible">
        <xsl:param name="returnType" />
        <xsl:param name="javaname" />
        /**
        *Index accessible getter for property <xsl:value-of select="javaname"/>.  This has the same effect as calling:
        *&lt;code &gt;
        *get<xsl:value-of select="javaname"/>().get(pIndex);
        *&lt;/code&gt;
        *But will create all necessary empty return types such that an array out of bounds exception
        *is not thrown, and this method can be utilized from jsp using the:
        *&lt;code&gt;
        *<xsl:value-of select="javaname"/>Element[pIndex]
        *&lt;/code&gt;
        *Syntax.
        *@param int the index you wish to access.
        *@returns <xsl:value-of select='$returnType'/> at the specified index.
        */
        public <xsl:value-of select='$returnType'/> get<xsl:value-of select="javaname"/>Element(int pIndex){
          while(pIndex >= m<xsl:value-of select="javaname"/>.size()){
                m<xsl:value-of select="javaname"/>.add(<xsl:value-of select='$returnType'/>.createValue());
          }
          return (<xsl:value-of select='$returnType'/>) m<xsl:value-of select="javaname"/>.get(pIndex);
        }

        /**
        *Index accessible setter for property <xsl:value-of select="javaname"/>.  This has the same effect as calling:
        *&lt;code &gt;
        *get<xsl:value-of select="javaname"/>().add(pIndex,p<xsl:value-of select="javaname"/>);
        *&lt;/code&gt;
        *But will create all necessary empty return types such that an array out of bounds exception
        *is not thrown, and this method can be utilized from jsp using the:
        *&lt;code&gt;
        *<xsl:value-of select="javaname"/>Element[pIndex]
        *&lt;/code&gt;
        *Syntax.
        *@param int the index you wish to access.
        *@param <xsl:value-of select='$returnType'/> new value of property.
        */
        public void set<xsl:value-of select="javaname"/>Element(int pIndex,<xsl:value-of select='$returnType'/> p<xsl:value-of select="javaname"/>){
          while(pIndex > m<xsl:value-of select="javaname"/>.size()){
                m<xsl:value-of select="javaname"/>.add(<xsl:value-of select='$returnType'/>.createValue());
          }
          m<xsl:value-of select="javaname"/>.add(pIndex,p<xsl:value-of select="javaname"/>);
        }
</xsl:template>

<xsl:template name="to_xml">
<xsl:for-each select="property">
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

<xsl:template name="reset">
<xsl:for-each select="property">
<xsl:if test="changeTracking='true'">
        this.m<xsl:value-of select="javaname"/>Changed = false;</xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template name="col_param_list">
<xsl:for-each select="property"><xsl:value-of select="javatype"/> parm<xsl:value-of select="position()"/><xsl:if test="not(position()=last())">, </xsl:if></xsl:for-each>
</xsl:template>

<xsl:template name="col_string">
<xsl:for-each select="property"> + &quot;<xsl:if test="not(position()=1)">, </xsl:if><xsl:value-of select="javaname"/>=&quot; + m<xsl:value-of select="javaname"/></xsl:for-each>
</xsl:template>

<xsl:template name="col_assignment">
<xsl:for-each select="property">m<xsl:value-of select="javaname"/> = parm<xsl:value-of select="position()"/>;
        </xsl:for-each></xsl:template>

<xsl:template name="remove_nulls">
<xsl:for-each select="property"><xsl:if test="javatype='String'">
        m<xsl:value-of select="javaname"/> = "";</xsl:if>
        <xsl:if test="notNull='true'">
        <xsl:choose>
        <xsl:when test='contains(javatype,"Vector")'>
                m<xsl:value-of select="javaname"/> = new <xsl:value-of select="javatype"/>();
        </xsl:when>
        <xsl:when test='contains(javatype,"View")'>
                m<xsl:value-of select="javaname"/> = <xsl:value-of select="javatype"/>.createValue();
        </xsl:when>
        <xsl:when test='contains(javatype,"Data")'>
                m<xsl:value-of select="javaname"/> = <xsl:value-of select="javatype"/>.createValue();
        </xsl:when>
        <xsl:otherwise>
                m<xsl:value-of select="javaname"/> = new <xsl:value-of select="javatype"/>();
        </xsl:otherwise>
        </xsl:choose>
        </xsl:if>
</xsl:for-each>
</xsl:template>

<xsl:template name="required_field">
<xsl:if test="@nullable='false'"> This field is required to be set in the database.</xsl:if>
</xsl:template>

</xsl:stylesheet>






