<% { %>
<%
  EstimatorFacilityData defaultFacility = theForm.getDefaultFacility();
%>
<table>
<tr>
<td class='tableheader'>Assumptions</td>
</tr>
<tr>
<td>
Average employee washes hands <html:text  name='SPENDING_ESTIMATOR_FORM' property='washHandPerDay' 
                         size='4'/>(<%=defaultFacility.getWashHandQty()%>) times per day
</td>
</tr>
<tr>
<td>
Average employee uses toilet tissue <html:text  name='SPENDING_ESTIMATOR_FORM' property='tissueUsagePerDay'
                          size='4'/>(<%=defaultFacility.getTissueUsageQty()%>) times per day
</td>
</tr>
<tr>
<td>
Among visitors using bathroom <html:text  name='SPENDING_ESTIMATOR_FORM' property='visitorToiletTissuePer'
             size='4'/>(<%=defaultFacility.getVisitorToiletTissuePercent()%>) percent would use toilet tissue
</td>
</tr>
<%
 String facilityTypeCd = theForm.getFacilityTypeCd();
 if(RefCodeNames.FACILITY_TYPE_CD.INDUSTRIAL.equals(facilityTypeCd)||
    RefCodeNames.FACILITY_TYPE_CD.OFFICE.equals(facilityTypeCd)) {
%>
<tr>
<td>
One person needs <html:text  name='SPENDING_ESTIMATOR_FORM' property='smallLinerPerDay'
                 size='4'/>(<%=defaultFacility.getReceptacleLinerRatio()%>) trash receptacle liners per day
</td>
</tr>
<tr>
<td>
Cleaning persons put <html:text  name='SPENDING_ESTIMATOR_FORM' property='largeLinerPerDay'
             size='4'/>(<%=defaultFacility.getLargeLinerRatio()%>) personal trash receptacle liners into one container liner
</td>
</tr>
<% 
  } else if(RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL.equals(facilityTypeCd)||
            RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL.equals(facilityTypeCd)) {
 %>
<tr>
<td>
One station needs <html:text  name='SPENDING_ESTIMATOR_FORM' property='smallLinerPerDay'
              size='4'/>(<%=defaultFacility.getReceptacleLinerRatio()%>) trash receptacle liners per day
</td>
</tr>
<tr>
<td>
Cleaning persons put <html:text  name='SPENDING_ESTIMATOR_FORM' property='largeLinerPerDay'
             size='4'/>(<%=defaultFacility.getLargeLinerRatio()%>) station trash receptacle liners into one container liner
</td>
</tr>
<% } %>
<tr>
<td>
Cleaning persons put <html:text  name='SPENDING_ESTIMATOR_FORM' property='largeLinerCaLinerQty'
             size='4'/>(<%=defaultFacility.getLargeLinerCaLinerQty()%>) common area trash receptacle liners into one container liner
</td>
</tr>
<tr>
<td>
Every restroom needs <html:text  name='SPENDING_ESTIMATOR_FORM' property='toiletLinerPerDay'
            size='4'/>(<%=defaultFacility.getToiletLinerRatio()%>) liners per day
</td>
</tr>
<tr>
<td>
  <html:submit property="action" value="Save Assumptions"/>
</td>
</tr>
</table>
<% } %>



