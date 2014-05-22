<% { %>
<%
  EstimatorFacilityData defaultFacility = theForm.getDefaultFacility();
%>
<table border = 1 width='735'>
<tr>
<td>
<div class='tableheader'>Assumptions</div><br>
Each bathroom has   
 <html:text  name='SPENDING_ESTIMATOR_FORM' property='toiletBathroomQty'
    size='5'/>(<%=defaultFacility.getToiletBathroomQty()%>) toilets (urinals)<br>
  <html:submit property="action" value="Save Assumptions"/>
</td>
</tr>
</table>
<% } %>



