<% { %>
<%
    EstimatorFacilityJoinView facilityJoin = theForm.getFacilityJoin();
    EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
   
%>
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
  &nbsp;&nbsp;&nbsp;
  Facility Type:&nbsp;<b> <bean:write  name='SPENDING_ESTIMATOR_FORM' property='facilityTypeCd'/></b>
</td>
</tr>
<tr>
<td>
  Estimated Number of Resident Personnel:&nbsp;
  <b><bean:write name='SPENDING_ESTIMATOR_FORM' property='personnelQty'/></b>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  Estimated Number of Visitors:&nbsp;
  <b><bean:write name='SPENDING_ESTIMATOR_FORM' property='visitorQty'/></b>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</td>
</tr>
<tr>
<td>
  Number of Bathrooms:
  <b><bean:write name='SPENDING_ESTIMATOR_FORM' property='bathroomQty'/></b>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  Visitors Using Bathrooms (%):
  <b><bean:write name='SPENDING_ESTIMATOR_FORM' property='visitorBathroomPer'/></b>
</td>
</tr>
  <% if(RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL.equals(theForm.getFacilityTypeCd()) ||
        RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL.equals(theForm.getFacilityTypeCd())) {
  %>
<tr>
<td>
  Number of Retail Stations:&nbsp;
  <b><bean:write name='SPENDING_ESTIMATOR_FORM' property='stationQty'/></b>
</td>
</tr>
 <% } %>



</table>
</td>
</tr>
</table>
<% } %>


