<%@ page language="java" %>

<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*"%>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<%
/*
This page has been intentionally left unprotected.  It is meant to display
product info for external systems.
*/
%>

<!-- ProductId=<bean:write name="ProductData" property="productId"/> -->
<table width="769" style="border: solid 1px black;" 
     cellpadding="0" cellspacing="0">

   <tr>
<td >
<bean:define id="imageStr" name="ProductData" property="image" 
  type="java.lang.String" />
    <% if(imageStr!=null && imageStr.trim().length()>0) {    %>
      <img src="/<%=storeDir%>/<%=imageStr%>">
    <% } else { %>
      &nbsp;
    <% } %>
    </td>

    <td align="left" valign="top">
<bean:define id="skuDesc1" name="ProductData" property="customerProductShortDesc" type="java.lang.String" />
<bean:define id="skuDesc2" name="ProductData" property="shortDesc" type="java.lang.String" />
<% 
String finalSkuDesc = "";
if(skuDesc1==null || skuDesc1.trim().length()==0) 
{         finalSkuDesc = skuDesc2;    }   
else {    finalSkuDesc = skuDesc1;    }    
%>

    <table border="0" cellpadding="0" cellspacing="0">
      <tr>
      <td class="customerltbkground" align="center" valign="top">
       <div class="itemheadmargin"><%=finalSkuDesc%> </div>
      </td>
      </tr>
       <tr>
       <td class="text"><div class="itemheadmargin">
        <bean:write name="ProductData" property="longDesc"/>
       </div></td>
       </tr>
       <tr>
       <td align="right" valign="bottom">
       <div class="itemheadmargin">

<bean:define id="msds" name="ProductData" property="msds" 
  type="java.lang.String" />
       <%           if(msds!=null && msds.trim().length()>0) {       %>
         <a href="/<%=storeDir%>/<%=msds%>" target="_blank"><img border="0" src="<%=IMGPath%>/cw_msdsicon.gif"/></a>
       <% } %>

<bean:define id="ded" name="ProductData" property="ded" 
  type="java.lang.String" />
       <%           if(ded!=null && ded.trim().length()>0) {       %>
       <a href="/<%=storeDir%>/<%=ded%>" target="_blank"><img border="0" src="<%=IMGPath%>/cw_dedicon.gif"/></a>
       <% } %>

<bean:define id="spec" name="ProductData" property="spec" 
  type="java.lang.String" />
       <%           if(spec!=null && spec.trim().length()>0) {       %>
       <a href="/<%=storeDir%>/<%=spec%>" target="_blank"><img border="0" src="<%=IMGPath%>/cw_productspecsicon.gif"/></a>
       <% } %>
       </div>
       </td>
       </tr>
  </table>

  <tr><td colspan=2>

    <table width="769" border="0" cellpadding="0" cellspacing="0">

	<tr>
   <td class="shopcharthead"><div class="fivemargin">Our Sku #</div></td>
   <td class="shopcharthead"><div class="fivemargin">Name</div></td>
   <td class="shopcharthead"><div class="fivemargin">Size</div></td>
   <td class="shopcharthead"><div class="fivemargin">Pack</div></td>
   <td class="shopcharthead"><div class="fivemargin">UOM</div></td>
   <td class="shopcharthead"><div class="fivemargin">Color</div></td>
   <td class="shopcharthead"><div class="fivemargin">Manufacturer</div></td>
   <td class="shopcharthead"><div class="fivemargin">Mfg.Sku #</div></td>
  </tr>

  <tr>
  <td colspan="8" class="tableoutline">
    <img src="<%=IMGPath%>/cw_spacer.gif" height="1"></td></tr>

   <tr>

<bean:define id="sku1" name="ProductData" property="customerSkuNum"
	type="java.lang.String" />
<bean:define id="sku2" name="ProductData" property="skuNum"
	type="java.lang.Integer" />
<%
String finalSkuNum = "";
if ( sku1 == null || sku1.length() == 0 ) 
{      finalSkuNum = sku2.toString(); }
else { finalSkuNum = sku1; }
%>

   <td class="text"><div class="fivemargin">
<%=finalSkuNum%>
   </div></td>
   <td class="text">
    <div class="itemheadmargin"><%=finalSkuDesc%> </div>
   </td>
   <td class="text"><div class="fivemargin">
     <bean:write name="ProductData" property="size"/>
   </div></td>
   <td class="text"><div class="fivemargin">
     <bean:write name="ProductData" property="pack"/>
   </div></td>
   <td class="text"><div class="fivemargin">
     <bean:write name="ProductData" property="uom"/>
   </div></td>
   <td class="text"><div class="fivemargin">
     <bean:write name="ProductData" property="color"/>
   </div></td>
   <td class="text"><div class="fivemargin">
       <bean:write name="ProductData" property="manufacturerName"/>
   </div></td>
   <td class="text"><div class="fivemargin">
     <bean:write name="ProductData" property="manufacturerSku"/>
   </div></td>
   </tr>
	</table>
	</td></tr>

</table>
 







