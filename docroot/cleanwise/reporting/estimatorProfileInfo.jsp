<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.CurrencyFormat" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% { %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="SPENDING_ESTIMATOR_FORM" type="com.cleanwise.view.forms.SpendingEstimatorForm"/>
<%
 CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER); 
 String userType = user.getUser().getUserTypeCd(); 
 boolean adminFl = false;
 if ( RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType) ) {
   adminFl = true;
 }
 EstimatorFacilityJoinView efVw = theForm.getFacilityJoin();
 EstimatorFacilityData efD = efVw.getEstimatorFacility();
 String facilityTypeCd = efD.getFacilityTypeCd();
 boolean retailFl = 
  (RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL.equals(facilityTypeCd) ||
   RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL.equals(facilityTypeCd))? true:false;
%>


<table>
<html:form name="SPENDING_ESTIMATOR_FORM" action="reporting/estimator"
    scope="session" type="com.cleanwise.view.forms.SpendingEstimatorForm">
 <tbody>
<tr>
</tr>
<tr>
<td><b>Facility Name:</b>&nbsp;<html:text name='SPENDING_ESTIMATOR_FORM' property='name' size='70'/>
  &nbsp;&nbsp;&nbsp;
<!--
Number of Locations:&nbsp;<html:text name='SPENDING_ESTIMATOR_FORM' property='facilityQty' size='5'/>
-->
</td>
</tr>

<tr>
<td>
<%  
  int catalogId = theForm.getCatalogId();
   CatalogDataVector catalogDV = theForm.getCatalogs();
   if(catalogDV!=null) {
   for(Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
     CatalogData catalogD = (CatalogData) iter.next();
     if(catalogD.getCatalogId()==catalogId) {
     String catalogName = catalogD.getShortDesc();
%>
<b>Product Catalog:</b> <%=catalogName%>
<% } break; }} %>


</td>
</tr>
<tr>
<td>
  <b>Facility Type:</b>&nbsp;
  <bean:write  name='SPENDING_ESTIMATOR_FORM' property='facilityTypeCd'/>
  &nbsp;&nbsp;&nbsp;<b>Working Days per Year:</b>&nbsp;
  <bean:write name='SPENDING_ESTIMATOR_FORM' property='workingDayYearQty'/>
  &nbsp;&nbsp;&nbsp;
</td>
</tr>
<tr>
<td>
  <b>Estimated Number of Resident Personnel (FTE)</b>:&nbsp;
<bean:write name='SPENDING_ESTIMATOR_FORM' property='personnelQty'/>
  &nbsp;&nbsp;&nbsp;
  <b>Estimated Number of Visitors (per day):</b>&nbsp;
<bean:write name='SPENDING_ESTIMATOR_FORM' property='visitorQty'/>
</td>
</tr>
<%  if(retailFl) { %>
<tr>
<td>
  <b>Number of Retail Stations:</b>&nbsp;
<bean:write name='SPENDING_ESTIMATOR_FORM' property='stationQty'/>
</td>
</tr>
<% } %>
<tr>
<td>
&nbsp;
</td>
</tr>
<tr>
<td class='tableheader' border='solid'>
  Common Area
</td>
</tr>
<tr>
<td>
  <b>Number of Common Area Trash Receptacles (per day):</b>&nbsp;
<bean:write name='SPENDING_ESTIMATOR_FORM' property='commonAreaLinerPerDay'/>
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
  <b>Number of Bathrooms:</b>&nbsp;
<bean:write name='SPENDING_ESTIMATOR_FORM' property='bathroomQty'/>
&nbsp;&nbsp;&nbsp;&nbsp;
  <b>Percent of Visitors Using Bathroom:</b>
<bean:write name='SPENDING_ESTIMATOR_FORM' property='visitorBathroomPer'/>
&nbsp;&nbsp;&nbsp;&nbsp;
  <b>Number of Showers:</b>
<bean:write name='SPENDING_ESTIMATOR_FORM' property='showerQty'/>
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
 <b>Net Cleanable Sq. Footage:</b>&nbsp;
<bean:write name='SPENDING_ESTIMATOR_FORM' property='netCleanableFootage'/>
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
<table border='1'>
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
<logic:present name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerEl%>'>
<logic:notEqual name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerEl%>' value='<%=""%>'>
<tr>
<td><b><%=floorTypes[ii]%></b></td>
<td><bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerEl%>'/>&nbsp;</td>
<td><bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerHtEl%>'/>&nbsp;</td>
<td><bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerMtEl%>'/>&nbsp;</td>
<td><bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerLtEl%>'/>&nbsp;</td>
</tr>
</logic:notEqual>
</logic:present>
<% } %>
</table>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
  <tr>
  <td>
  <html:submit property="action" value="Create Model"/>
  </td>
</tr>

</tbody></html:form>
</table>
<% } %>


