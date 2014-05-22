<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js"
  CHARSET="ISO-8859-1"></SCRIPT>

<% 
  String storeDir=ClwCustomizer.getStoreDir(); 
  String portal = request.getParameter("portal");
  boolean adminPortalFl = ("adminportal".equalsIgnoreCase(portal))?true:false;
  String actionStr = (adminPortalFl)?"/adminportal/distConfig.do":"/console/crcdistTerr.do";
  String thisLink = (adminPortalFl)?"distConfig.do":"crcdistTerr.do";
  boolean readOnlyFl = (adminPortalFl)?false:true;
  
%>  

<bean:define id="mainForm" name="DIST_DETAIL_FORM" type="com.cleanwise.view.forms.DistMgrDetailForm"/>
<bean:define id="theForm" name="DIST_CONFIG_FORM" type="com.cleanwise.view.forms.DistMgrConfigForm"/>
<div class = "text">
<font color=red><html:errors/></font>
<table cellspacing="0" border="0" width="769"  class="mainbody">
<html:form action="<%=actionStr%>">
<%
  String distIdS = mainForm.getId();
  String actConfType = theForm.getActConfigType();
%>
<tr>
<td><b>Distributor&nbsp;Id:</b></td>
<td><%=""+distIdS%></td>
 <td><b>Name:</b></td>
<td>
<bean:write name="DIST_DETAIL_FORM" property="name"/>
</td>
</tr>
<html:hidden name="DIST_CONFIG_FORM" property="distributorId" value="<%=distIdS%>"/>
<html:hidden name="DIST_CONFIG_FORM" property="actConfigType" value="<%=actConfType%>"/>
<tr><td><b>Config Type:<b></td>
      <td colspan="3">
      <html:select name="DIST_CONFIG_FORM" property="configType">
      <html:option value="County">County</html:option>
      <html:option value="Zip Code">Zip Code</html:option>
      </html:select>
  </tr>
  <tr><td><b>State:</b></td>
  <td><html:text name="DIST_CONFIG_FORM" property="state" size="30"/>
  </td>
  <td><b>County:</b></td>
  <td><html:text name="DIST_CONFIG_FORM" property="county" size="30"/>
  </td>
  <tr><td><b>Zip Code:</b></td>
  <td><html:text name="DIST_CONFIG_FORM" property="postalCode" size="30"/>
  </td>
  <td><b>Serviced Only:</b></td>
  <td><html:checkbox name="DIST_CONFIG_FORM" property="servicedOnly" value="true"/>
  </td>
  </tr>
  <tr> <td>&nbsp;</td>
  <td colspan="3">
  <html:submit property="action" value="Search"/>
  </td>
  </tr>
</table>
<% if("County".equals(theForm.getActConfigType())) {%>
<% int size = theForm.getCounties().size(); %>
Search result count: <%=size %>
<% if(size>0) { %>
<table cellspacing="0" border="0" width="769"  class="results">
<tr align=center>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=County"><b>County</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=State Cd"><b>State Cd</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=State Name"><b>State Name</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=Country"><b>Country</b></a> </td>
<td class="tableheader">&nbsp;</td>
<td class="tableheader"><b>Freight Cd</b></td>
</tr>
<logic:iterate id="confCounty" name="DIST_CONFIG_FORM" property="counties"
  type="com.cleanwise.service.api.value.BusEntityTerrView"
  indexId="idx"
>
  <bean:define id="lCounty"  name="confCounty" property="countyCd"/>
  <bean:define id="lStateCd"  name="confCounty" property="stateProvinceCd"/>
  <bean:define id="lStateName"  name="confCounty" property="stateProvinceName"/>
  <bean:define id="lCountry"  name="confCounty" property="countryCd"/>
  <bean:define id="lDistId" name="confCounty" property="busEntityId"/>
  <bean:define id="lNoModifiyFl" name="confCounty" property="noModifiyFl"/>
  
  <% String lFreight = "";
   if(confCounty.getBusEntityTerrFreightCd()!=null) { 
    lFreight = confCounty.getBusEntityTerrFreightCd();
   }
  %>
<% if ( idx.intValue()%2==0 ) { %>
    <tr class="rowa">
<% } else { %>
    <tr class="rowb">
<% } %>
    <td><bean:write name="lCounty"/></td>
    <td><bean:write name="lStateCd"/></td>
    <td><bean:write name="lStateName"/></td>
    <td><bean:write name="lCountry"/></td>
    <% String countySelectFl = (((Boolean)lNoModifiyFl).booleanValue())?"0":"1";
       countySelectFl += lCounty+"^"+lStateCd+"^"+lCountry+"^";
    %>
    <td> 
      <% if(!readOnlyFl) { %>
      <html:multibox name="DIST_CONFIG_FORM" property="selected"
       value="<%=countySelectFl%>" disabled="<%=((Boolean)lNoModifiyFl).booleanValue()%>"/>
      <% } else { %>
      <% if(lFreight.trim().length()>0) {  /* this territory is covered */ %>Y 
      <% } else {   /* this territory is NOT covered */ %>N <% } %>
<% } %>
      </td>      
    <td>
      <% if(!readOnlyFl) { %>
    	<html:select name="DIST_CONFIG_FORM" property="busEntityTerrFreightCd" disabled="<%=((Boolean)lNoModifiyFl).booleanValue()%>">
     		<html:option value="<%=(countySelectFl + RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.NO_FREIGHT)%>">
<%=RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.NO_FREIGHT%>
</html:option>
		     <html:option value="<%=(countySelectFl + RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.FREIGHT)%>">
<%=RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.FREIGHT%>
</html:option>
       </html:select>
      <% } else { %>
      <%=lFreight%>
      <% } %>
    </td>
 </logic:iterate>

 <% if(!readOnlyFl) { %>
  <tr>
  <td colspan="5">
  <html:submit property="action" value="Save"/>
  <html:submit property="action" value="Select All"/>
  <html:submit property="action" value="Clear Selection"/>
  </td>
  </tr>
<% } %>
</table><br><br>
<% } %>
<% } %>
<% if("Zip Code".equals(theForm.getActConfigType())) {%>
<% int size = theForm.getPostalCodes().size(); %>
Search result count: <%=size %>
<% if(size>0) { %>
<table cellspacing="0" border="0" width="769"  class="results">
<tr align=center>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=Postal Code"><b>Postal Code</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=County"><b>County</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=State Cd"><b>State Cd</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=State Name"><b>State Name</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=Country"><b>Country</b></a> </td>
<td class="tableheader">&nbsp;</td>
<td class="tableheader"><b>Freight Cd</b></td>
</tr>
<logic:iterate id="confPostalCode" name="DIST_CONFIG_FORM" property="postalCodes"
  type="com.cleanwise.service.api.value.BusEntityTerrView"
  indexId="idx"
>
  <bean:define id="lPostalCodeId"  name="confPostalCode" property="postalCodeId"/>
  <bean:define id="lPostalCode"  name="confPostalCode" property="postalCode"/>
  <bean:define id="lCounty"  name="confPostalCode" property="countyCd"/>
  <bean:define id="lStateCd"  name="confPostalCode" property="stateProvinceCd"/>
  <bean:define id="lStateName"  name="confPostalCode" property="stateProvinceName"/>
  <bean:define id="lCountry"  name="confPostalCode" property="countryCd"/>
  <bean:define id="lDistId" name="confPostalCode" property="busEntityId"/>
  <% String lFreight = "";
   if(confPostalCode.getBusEntityTerrFreightCd()!=null) { 
    lFreight = confPostalCode.getBusEntityTerrFreightCd();
   }
  %>
<% if ( idx.intValue()%2==0 ) { %>
    <tr class="rowa">
<% } else { %>
    <tr class="rowb">
<% } %>
    <td><bean:write name="lPostalCode"/></td>
    <td><bean:write name="lCounty"/></td>
    <td><bean:write name="lStateCd"/></td>
    <td><bean:write name="lStateName"/></td>
    <td><bean:write name="lCountry"/></td>
    <% String postalCodeSelectFl = lPostalCode+"^";
    %>
    <td>
     <% if(!readOnlyFl) { %>
     <html:multibox name="DIST_CONFIG_FORM" property="selected"
       value="<%=postalCodeSelectFl%>"/>
      <% } else { %>
      <% if(lFreight.trim().length()>0){%>Y&nbsp;<%}%>
      <% } %>
    </td>
    <td>
     <% if(!readOnlyFl) { %>
	    <html:select name="DIST_CONFIG_FORM" property="busEntityTerrFreightCd">
		<html:option value="<%=(postalCodeSelectFl + RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.NO_FREIGHT)%>"><%=RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.NO_FREIGHT%></html:option>
		<html:option value="<%=(postalCodeSelectFl + RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.FREIGHT)%>"><%=RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.FREIGHT%></html:option>
	    </html:select>
      <% } else { %>
      <%=lFreight%>
      <% } %>
    </td>
 </logic:iterate>

 <% if(!readOnlyFl) { %>
  <tr>
  <td colspan="5">
  <html:submit property="action" value="Save"/>
  <html:submit property="action" value="Select All"/>
  <html:submit property="action" value="Clear Selection"/>
  </td>
  </tr>
<% } %>
</table><br><br>
<% } %>
<% } %>
</html:form>
</div>


