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

<script language="JavaScript1.2">
<!--
function SetChecked(val) {
  var dml=document.forms[0];
  var ellen = dml.elements.length;
  for(j=0; j<ellen; j++) {
    if (dml.elements[j].name=='action') {
      dml.elements[j].value=val;
    }
  }
  dml.submit();
}
//-->
</script>


<bean:define id="mainForm" name="STORE_DIST_DETAIL_FORM" type="com.cleanwise.view.forms.StoreDistMgrDetailForm"/>
<bean:define id="theForm" name="STORE_DIST_TERRITORY_FORM" type="com.cleanwise.view.forms.StoreDistMgrTerrForm"/>
<div class = "text">
<table ID=748 cellspacing="0" border="0" width="769"  class="mainbody">
<html:form styleId="749" action="storeportal/distterr.do">
<%
  String distIdS = mainForm.getId();
  String actConfType = theForm.getActConfigType();
%>
<tr>
<td><b>Distributor&nbsp;Id:</b></td>
<td><%=""+distIdS%></td>
 <td><b>Name:</b></td>
<td>
<bean:write name="STORE_DIST_DETAIL_FORM" property="name"/>
</td>
</tr>
<html:hidden name="STORE_DIST_TERRITORY_FORM" property="distributorId" value="<%=distIdS%>"/>
<html:hidden name="STORE_DIST_TERRITORY_FORM" property="actConfigType" value="<%=actConfType%>"/>
<tr>
  <td><b>Config Type:<b></td>
  <td>
      <html:select name="STORE_DIST_TERRITORY_FORM" property="configType">
      <html:option value="County">County</html:option>
      <html:option value="Zip Code">Zip Code</html:option>
      </html:select>
  </td>
  <td><b>City (starts with):</b></td>
  <td><html:text name="STORE_DIST_TERRITORY_FORM" property="city" size="30"/></td>
</tr>
<tr>
  <td><b>State:</b></td>
  <td><html:text name="STORE_DIST_TERRITORY_FORM" property="state" size="7"/></td>
  <td><b>County (starts with):</b></td>
  <td><html:text name="STORE_DIST_TERRITORY_FORM" property="county" size="30"/></td>
<tr>
  <td><b>Zip Code:</b></td>
  <td><html:text name="STORE_DIST_TERRITORY_FORM" property="postalCode" size="15"/></td>
  <td><b>Serviced Only:</b></td>
  <td><html:checkbox name="STORE_DIST_TERRITORY_FORM" property="servicedOnly" value="true"/></td>
</tr>
<tr>
  <td>&nbsp;</td>
  <td colspan="3"><html:submit property="action" value="Search"/></td>
</tr>
</table>
<% if("County".equals(theForm.getActConfigType())) {%>
<% int size = theForm.getCounties().size(); %>
Count: <%=size %>
<% if(size>0) { %>
<table ID=750 cellspacing="0" border="0" width="769">
<tr>
<th class="stpTH"><a ID=751 href="distterr.do?action=sort&sortField=County"><b>County</b></a></th>
<th class="stpTH"><a ID=752 href="distterr.do?action=sort&sortField=State Cd"><b>State Cd</b></a></th>
<th class="stpTH"><a ID=753 href="distterr.do?action=sort&sortField=State Name"><b>State Name</b></a></th>
<th class="stpTH"><a ID=754 href="distterr.do?action=sort&sortField=Country"><b>Country</b></a></th>
<th class="stpTH">
  <a ID=755 href="javascript:SetChecked('Select All')">[Check&nbsp;All]</a><br>
  <a ID=756 href="javascript:SetChecked('Clear Selection')">[&nbsp;Clear]</a>
</th>
<th class="stpTH"><b>Freight Cd</b></th>
</tr>
<logic:iterate id="confCounty" name="STORE_DIST_TERRITORY_FORM" property="counties"
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
<tr>
    <td class="stpTD"><bean:write name="lCounty"/></td>
    <td class="stpTD"><bean:write name="lStateCd"/></td>
    <td class="stpTD"><bean:write name="lStateName"/></td>
    <td class="stpTD"><bean:write name="lCountry"/></td>
    <% String countySelectFl = (((Boolean)lNoModifiyFl).booleanValue())?"0":"1";
       countySelectFl += "^" + lCounty+"^"+lStateCd+"^"+lCountry+"^";
    %>
    <td class="stpTD">
      <html:multibox name="STORE_DIST_TERRITORY_FORM" property="selected"
       value="<%=countySelectFl%>" disabled="<%=((Boolean)lNoModifiyFl).booleanValue()%>"/>
      </td>
    <td class="stpTD">
      <html:select name="STORE_DIST_TERRITORY_FORM" property="busEntityTerrFreightCd" disabled="<%=((Boolean)lNoModifiyFl).booleanValue()%>">
         <html:option value="<%=(countySelectFl + RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.NO_FREIGHT)%>">
           <%=RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.NO_FREIGHT%>
         </html:option>
         <html:option value="<%=(countySelectFl + RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.FREIGHT)%>">
           <%=RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.FREIGHT%>
         </html:option>
      </html:select>
    </td>
</tr>
</logic:iterate>

<tr>
  <td colspan="7">
  <html:submit property="action" value="Save"/>
  </td>
  </tr>
</table><br><br>
<% } %>
<% } %>
<% if("Zip Code".equals(theForm.getActConfigType())) {%>
<% int size = theForm.getPostalCodes().size(); %>
Count: <%=size %>
<% if(size>0) { %>
<table ID=757 cellspacing="0" border="0" width="769">
<tr>
<th class="stpTH"><a ID=758 href="distterr.do?action=sort&sortField=Postal Code"><b>Postal Code</b></a></th>
<th class="stpTH"><a ID=759 href="distterr.do?action=sort&sortField=City"><b>City</b></a></th>
<th class="stpTH"><a ID=760 href="distterr.do?action=sort&sortField=County"><b>County</b></a></th>
<th class="stpTH"><a ID=761 href="distterr.do?action=sort&sortField=State Cd"><b>State Cd</b></a></th>
<th class="stpTH"><a ID=762 href="distterr.do?action=sort&sortField=State Name"><b>State Name</b></a></th>
<th class="stpTH"><a ID=763 href="distterr.do?action=sort&sortField=Country"><b>Country</b></a></th>
<th class="stpTH">
  <a ID=764 href="javascript:SetChecked('Select All')">[Check&nbsp;All]</a><br>
  <a ID=765 href="javascript:SetChecked('Clear Selection')">[&nbsp;Clear]</a>
</th>
<th class="stpTH"><b>Freight Cd</b></th>
</tr>
<logic:iterate id="confPostalCode" name="STORE_DIST_TERRITORY_FORM" property="postalCodes"
  type="com.cleanwise.service.api.value.BusEntityTerrView"
  indexId="idx"
>
  <bean:define id="lCity"  name="confPostalCode" property="city"/>
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
<tr>
    <td class="stpTD"><bean:write name="lPostalCode"/></td>
    <td class="stpTD"><bean:write name="lCity"/></td>
    <td class="stpTD"><bean:write name="lCounty"/></td>
    <td class="stpTD"><bean:write name="lStateCd"/></td>
    <td class="stpTD"><bean:write name="lStateName"/></td>
    <td class="stpTD"><bean:write name="lCountry"/></td>
    <% String postalCodeSelectFl = lPostalCode+"^"+lCity+"^"; %>
    <td class="stpTD">
     <html:multibox name="STORE_DIST_TERRITORY_FORM" property="selected"
       value="<%=postalCodeSelectFl%>"/>
    </td>
    <td class="stpTD">
      <html:select name="STORE_DIST_TERRITORY_FORM" property="busEntityTerrFreightCd">
        <html:option value='<%=(postalCodeSelectFl + RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.NO_FREIGHT)%>'><%=RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.NO_FREIGHT%></html:option>
        <html:option value='<%=(postalCodeSelectFl + RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.FREIGHT)%>'><%=RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.FREIGHT%></html:option>
      </html:select>
    </td>
  </tr>
  </logic:iterate>

  <tr>
  <td colspan="8">
  <html:submit property="action" value="Save"/>
  </td>
  </tr>
</table><br><br>
<% } %>
<% } %>

<input type="hidden" name="action" value="unknown" />
</html:form>
</div>
