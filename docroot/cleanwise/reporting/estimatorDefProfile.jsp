<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.CurrencyFormat" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="SPENDING_ESTIMATOR_FORM" type="com.cleanwise.view.forms.SpendingEstimatorForm"/>



<table>
<html:form name="SPENDING_ESTIMATOR_FORM" action="reporting/estimator"
    scope="session" type="com.cleanwise.view.forms.SpendingEstimatorForm">
 <tbody>
<tr>
</tr>
<tr>
<td>Facility Name:&nbsp;<html:text name='SPENDING_ESTIMATOR_FORM' property='name' size='70'/>
  &nbsp;&nbsp;&nbsp;
Number of Locations:&nbsp;<html:text name='SPENDING_ESTIMATOR_FORM' property='facilityQty' size='5'/>
</td>
</tr>
<tr><td>Product Catalog:
  <html:select  name='SPENDING_ESTIMATOR_FORM' property='catalogId'>
    <html:option value='0'>---- Select Catalog -----</html:option>
   <%
   CatalogDataVector catalogDV = theForm.getCatalogs();
   if(catalogDV!=null) {
   for(Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
     CatalogData catalogD = (CatalogData) iter.next();
   %>
    <html:option value='<%=""+catalogD.getCatalogId()%>'><%=catalogD.getShortDesc()%></html:option>
   <% }} %>
  </html:select>
  &nbsp;&nbsp;&nbsp;
  Facility Type:&nbsp;
  <html:select  name='SPENDING_ESTIMATOR_FORM' property='facilityTypeCd' onchange='facilityTypeChange();' >
    <html:option value='<%=RefCodeNames.FACILITY_TYPE_CD.INDUSTRIAL%>'><%=RefCodeNames.FACILITY_TYPE_CD.INDUSTRIAL%></html:option>
    <html:option value='<%=RefCodeNames.FACILITY_TYPE_CD.OFFICE%>'><%=RefCodeNames.FACILITY_TYPE_CD.OFFICE%></html:option>
    <html:option value='<%=RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL%>'><%=RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL%></html:option>
    <html:option value='<%=RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL%>'><%=RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL%></html:option>
  </html:select>
  &nbsp;&nbsp;&nbsp;
  Working Days per Year:&nbsp;
  <html:text name='SPENDING_ESTIMATOR_FORM' property='workingDayYearQty' size='4'/>
  &nbsp;&nbsp;&nbsp;
</td>
</tr>
<tr>
<td>
  Estimated Number of Resident Personnel (FTE):&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='personnelQty' size='6'/>
  &nbsp;&nbsp;&nbsp;
  Estimated Number of Visitors (per day):&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='visitorQty' size='6'/>
</td>
</tr>
<tr>
<td>
<div id="retail_section">
  Number of Retail Stations:&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='stationQty' size='4'/>
</div>
</td>
</tr>
<tr>
<td>
  &nbsp;
</td>
</tr>
<tr>
<td class='tableheader' border='solid'>
  Bathrooms
</td>
</tr>
<tr>
<td>
  Number of Bathrooms:&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='bathroomQty' size='3'/>
&nbsp;&nbsp;&nbsp;&nbsp;
  Percent of Visitors Using Bathroom:
<html:text name='SPENDING_ESTIMATOR_FORM' property='visitorBathroomPer' size='3'/>
&nbsp;&nbsp;&nbsp;&nbsp;
  Number of Showers:
<html:text name='SPENDING_ESTIMATOR_FORM' property='showerQty' size='3'/>
</td>
</tr>

<tr>
<td>
  &nbsp;
</td>
</tr>
<tr>
<td class='tableheader' border='solid'>
  Floors
</td>
</tr>
<tr>
<td>
 Gross Sq. Footage:&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='grossFootage' size='6'/>
<!--
&nbsp;&nbsp;&nbsp;&nbsp;
 Cleanble % (of Gross Sq. Footage):&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='cleanableFootagePercent' size='6'/>
&nbsp;&nbsp;&nbsp;&nbsp;
 Baseboard % of Cleanble Sq. Footage)&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='baseboardPercent' size='4'/>
&nbsp;&nbsp;&nbsp;&nbsp;
-->
</td>
</tr>

<tr>
<td>
<table>
<tr>
<td><b>Floor Type</b></td>
<td width='150'><b>Percent of Total<br> Sq Footage</b></td>
<td width='100'><b>High Traffic<br> Percent</b></td>
<td width='100'><b>Medium Traffic<br> Percent</b></td>
<td width='100'><b>Low Traffic<br> Percent</b></td>
<tr>
<% 
   String[] floorTypes = theForm.getFloorTypes();
   String[] floorTypePercents = theForm.getFloorTypePercents();
   String[] floorTypePercentsHt = theForm.getFloorTypePercentsHt();
   String[] floorTypePercentsMt = theForm.getFloorTypePercentsMt();
   String[] floorTypePercentsLt = theForm.getFloorTypePercentsLt();
   double floorPr = 0;
   for(int ii=0; ii<floorTypes.length; ii++) {
     String totalPerEl = "floorTypePercentEl["+ii+"]";
     String totalPerHtEl = "floorTypePercentHtEl["+ii+"]";
     String totalPerMtEl = "floorTypePercentMtEl["+ii+"]";
     String totalPerLtEl = "floorTypePercentLtEl["+ii+"]";
%>
<tr>
<td><%=floorTypes[ii]%></td>
<td><html:text name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerEl%>' size='6'/></td>
<td><html:text name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerHtEl%>' size='6'/></td>
<td><html:text name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerMtEl%>' size='6'/></td>
<td><html:text name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerLtEl%>' size='6'/></td>
</tr>
<% } %>
<tr><td>&nbsp;</td></tr>
<!--
<tr>
<td class='tableheader'>Allocated products:</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
Spend Factor for Estimated Items:&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='estimatedItemsFactor' size='6'/>
  &nbsp;&nbsp;&nbsp;
</td>
</tr>
-->
  <tr>
  <td>
  <html:submit property="action" value="Save Profile"/>
  <% 
    EstimatorFacilityJoinView efVw = theForm.getFacilityJoin();
    if(efVw!=null) {
      EstimatorFacilityData efD = efVw.getEstimatorFacility();
      if(efD!=null) {
       String delHref = "window.location='estimator.do?action=delModel&id=" + 
       efD.getEstimatorFacilityId()+"';";
  %>
  <input type='button' name='deleteModel' value='Delete Profile' onclick="<%=delHref%>"/>
  <% }} %>
  </td>
</tr>
<script language="JavaScript1.2">

function f_hideBox(boxid) {
  document.getElementById(boxid).style.display = 'none';
}

function f_showBox(boxid) {
  document.getElementById(boxid).style.display = 'block';  
}

function facilityTypeChange() {
  var facilityTypeCd = document.forms[0].elements['facilityTypeCd'].value;
  //alert(facilityTypeCd);
  switch (facilityTypeCd) {
  case '<%=RefCodeNames.FACILITY_TYPE_CD.INDUSTRIAL%>':
    f_hideBox("retail_section");
  break;
  case '<%=RefCodeNames.FACILITY_TYPE_CD.OFFICE%>':
    f_hideBox("retail_section");
  break;
  case '<%=RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL%>':
    f_showBox("retail_section");
  break;
  case '<%=RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL%>':
    f_showBox("retail_section");
  break;
  default:
    f_hideBox("retail_section");
  }
}

 facilityTypeChange();

</script>

</tbody></html:form>
</table>



