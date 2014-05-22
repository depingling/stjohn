<% { %>
<table border = 1 width='735'>
<tr>
<td>
<div class='tableheader'>Assumptions</div><br>
Average employee washes hands <bean:write  name='SPENDING_ESTIMATOR_FORM' property='washHandPerDay'/> times per day<br>
Average employee uses toilet tissue <bean:write  name='SPENDING_ESTIMATOR_FORM' property='tissueUsagePerDay'/> times per day<br>
Among visitors using bathroom <bean:write  name='SPENDING_ESTIMATOR_FORM' property='visitorToiletTissuePer'/> percent would use toilet tissue<br>
<%
 String facilityTypeCd = theForm.getFacilityTypeCd();
 if(RefCodeNames.FACILITY_TYPE_CD.INDUSTRIAL.equals(facilityTypeCd)||
    RefCodeNames.FACILITY_TYPE_CD.OFFICE.equals(facilityTypeCd)) {
%>
One person needs <bean:write  name='SPENDING_ESTIMATOR_FORM' property='smallLinerPerDay'/> trash receptacle liners per day<br>
Cleaning persons put <bean:write  name='SPENDING_ESTIMATOR_FORM' property='largeLinerPerDay'/> personal trash receptacle liners into one container liner<br>
<% 
  } else if(RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL.equals(facilityTypeCd)||
            RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL.equals(facilityTypeCd)) {
 %>
One station needs <bean:write  name='SPENDING_ESTIMATOR_FORM' property='smallLinerPerDay'/> trash receptacle liners per day<br>
Cleaning persons put <bean:write  name='SPENDING_ESTIMATOR_FORM' property='largeLinerPerDay'/> station trash receptacle liners into one container liner<br>
<% } %>
Cleaning persons put <bean:write  name='SPENDING_ESTIMATOR_FORM' property='largeLinerCaLinerQty'/> common area trash receptacle liners into one container liner<br>
Every restroom needs <bean:write  name='SPENDING_ESTIMATOR_FORM' property='toiletLinerPerDay'/> liners per day<br>
  <html:submit property="action" value="Modify Assumptions"/>
</td>
</tr>
</table>
<% } %>



