<% { %>
<table border = 1 width='735'>
<tr>
<td>
<div class='tableheader'>Assumptions</div><br>
Cleanable Sq. Footage is  
 <bean:write  name='SPENDING_ESTIMATOR_FORM' property='cleanableFootagePercent'/>% of Gross Sq. Footage<br>
Cleanabe Baseboard Sq. Footage is <bean:write  name='SPENDING_ESTIMATOR_FORM' property='baseboardPercent'/>% of Cleanable Sq. Footage<br>
  <html:submit property="action" value="Modify Assumptions"/>
</td>
</tr>
</table>
<% } %>



