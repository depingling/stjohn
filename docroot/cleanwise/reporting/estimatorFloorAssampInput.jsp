<% { %>
<%
  EstimatorFacilityData defaultFacility = theForm.getDefaultFacility();
%>
<table border = 1 width='735'>
<tr>
<td>
<div class='tableheader'>Assumptions</div><br>
Cleanable Sq. Footage is  
 <html:text  name='SPENDING_ESTIMATOR_FORM' property='cleanableFootagePercent'
     size='5'/>(<%=defaultFacility.getCleanableFootagePercent()%>) % of Gross Sq. Footage<br>
Cleanabe Baseboard Sq. Footage is <html:text  name='SPENDING_ESTIMATOR_FORM' property='baseboardPercent'
     size='5'/>(<%=defaultFacility.getBaseboardPercent()%>) % of Cleanable Sq. Footage<br>
  <html:submit property="action" value="Save Assumptions"/>
</td>
</tr>
</table>
<% } %>



