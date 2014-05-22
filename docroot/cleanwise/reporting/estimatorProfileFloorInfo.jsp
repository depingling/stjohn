<% { %>
<tr>
<td class="tableheader"><b>Facility Profile</b></td>
</tr>

<table border='1'>
<tr>
<td>Facility Name:&nbsp;<b><bean:write name='SPENDING_ESTIMATOR_FORM' property='name'/></b>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Number of Locations:&nbsp;<b><bean:write name='SPENDING_ESTIMATOR_FORM' property='facilityQty'/></b>
</td>
</tr>
<tr><td>Product Catalog: <b>
   <%
   int catalogId = theForm.getCatalogId();
   CatalogDataVector catalogDV = theForm.getCatalogs();
   if(catalogDV!=null) {
   for(Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
     CatalogData catalogD = (CatalogData) iter.next();
     if(catalogId==catalogD.getCatalogId()) {
   %>
    <%=catalogD.getShortDesc()%>
   <% 
    break;
    }}} 
    %>
    </b>
  &nbsp;&nbsp;&nbsp;&nbsp;
  Facility Type:&nbsp;<b> <bean:write  name='SPENDING_ESTIMATOR_FORM' property='facilityTypeCd'/></b>
  &nbsp;&nbsp;&nbsp;&nbsp;
<!--
  Estimated Number of Resident Personnel:&nbsp;
  <b><bean:write name='SPENDING_ESTIMATOR_FORM' property='personnelQty'/></b>
-->
 Gross Sq. Footage:&nbsp;
<b><bean:write name='SPENDING_ESTIMATOR_FORM' property='grossFootage'/></b>
</td>
</tr>

<!--
<tr>
<td>
 Gross Sq. Footage:&nbsp;
<b><bean:write name='SPENDING_ESTIMATOR_FORM' property='grossFootage'/></b>
&nbsp;&nbsp;&nbsp;&nbsp;
 Cleanable % 
<b><bean:write name='SPENDING_ESTIMATOR_FORM' property='cleanableFootagePercent'/></b>
&nbsp;&nbsp;&nbsp;&nbsp;
 Basboard size (% of Cleanble Sq. Footage)&nbsp;
<b><bean:write name='SPENDING_ESTIMATOR_FORM' property='baseboardPercent'/></b>
</td>
</tr>
-->

<tr>
<td>
<table border='1'>
<tr>
<td><b>Floor Type</b></td>
<td><b>Percent of Total Sq Footage</b></td>
<td><b>High Traffic Percent</b></td>
<td><b>Medium Traffic Percent</b></td>
<td><b>Low Traffic Percent</b></td>
<tr>
<% 
   String[] floorTypes = theForm.getFloorTypes();
   String[] floorTypePercents = theForm.getFloorTypePercents();
   String[] floorTypePercentsHt = theForm.getFloorTypePercentsHt();
   String[] floorTypePercentsMt = theForm.getFloorTypePercentsMt();
   String[] floorTypePercentsLt = theForm.getFloorTypePercentsLt();
   for(int ii=0; ii<floorTypes.length; ii++) {
     String totalPerEl = "floorTypePercentEl["+ii+"]";
     String totalPerHtEl = "floorTypePercentHtEl["+ii+"]";
     String totalPerMtEl = "floorTypePercentMtEl["+ii+"]";
     String totalPerLtEl = "floorTypePercentLtEl["+ii+"]";
    if(floorTypePercents[ii].length()>0) {
%>
<tr>
<td><%=floorTypes[ii]%></td>
<td><bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerEl%>'/></td>
<td><bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerHtEl%>'/>&nbsp;</td>
<td><bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerMtEl%>'/>&nbsp;</td>
<td><bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerLtEl%>'/>&nbsp;</td>
</tr>
<% }} %>
</table>
</td>
</tr>
</table>
<% } %>


