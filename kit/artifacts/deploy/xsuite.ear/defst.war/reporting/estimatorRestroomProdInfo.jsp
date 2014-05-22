<!--  -->
<% { %>

<% 
   CleaningScheduleJoinViewVector csjVwV = theForm.getRestroomCleaningSchedules();
   if(csjVwV!=null && csjVwV.size()>0) {
%>
<tr><td><b class="tableheader">Restroom Cleaning Products</b></td></tr>
<tr>
<td>
<table border='2'>
<tr>
<td align='center' bgcolor='#cccccc'><b>Procedure</b></td>
<td align='center' bgcolor='#cccccc'><b>Sku #</b></td>
<td align='center' bgcolor='#cccccc'><b>Product</b></td>
<td align='center' bgcolor='#cccccc'><b>Size</b></td>
<td align='center' bgcolor='#cccccc'><b>Pack</b></td>
<td align='center' bgcolor='#cccccc'><b>Dilution</b></td>
<td align='center' bgcolor='#cccccc'><b>Usage<br>Rate</b></td>
<td align='center' bgcolor='#cccccc'><b>Sharing Percent</b></td>
</tr>
<%
   int ind = -1;
   for(Iterator iter = csjVwV.iterator(); iter.hasNext();) {
     ind ++;
     CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
     CleaningScheduleData csD = csjVw.getSchedule();
     String procFrequencyS = csjVw.getFrequency();
     if(csD.getEstimatorFacilityId()<=0) {
       continue;
     }
     String procTimePeriodCd = csjVw.getTimePeriodCd();
     CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
%>

<tr>
<td colspan ='8' bgcolor='#eeeeee'><b><%=csD.getName()%></b>&nbsp;&nbsp;
  Perform: <%=procFrequencyS%>  time(s) per <%=procTimePeriodCd%>
</td>
</tr>
  <%
   int ind1 = -1;
   for(Iterator iter1 = cssjVwV.iterator(); iter1.hasNext();) {
     ind1 ++;
     CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) iter1.next();
     CleaningSchedStructData cssD = cssjVw.getScheduleStep();
     CleaningProcData cpD = cssjVw.getProcedure();
     ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
     int prodQty = 0;
     for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
       ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
       ProdApplData paD = pajVw.getProdAppl();
       if(paD.getEstimatorFacilityId()<=0) {
         continue;
       }
       prodQty++;
     }
     if(prodQty==0) {
       continue;
     }

     int ind2 = -1;
     for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
       ind2++;
       ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
       ItemData iD = pajVw.getItem();
       ProdApplData paD = pajVw.getProdAppl();
       if(paD.getEstimatorFacilityId()<=0) {
         continue;
       }
       ProductUomPackData pupD = pajVw.getProductUomPack();
       String dilutionRateS = pajVw.getDilutionRate();
       String usagRateS = pajVw.getUsageRate();
       String numeratorS = pajVw.getUnitCdNumerator();
       String denominatorS = pajVw.getUnitCdDenominator();
       String denominator1S = pajVw.getUnitCdDenominator1();
       String sharingS = pajVw.getSharingPercent();
       String dilutionRateInp = "restroomProcProdDilution["+(((ind*1000+ind1)*1000)+ind2)+"]";     
       String usagRateInp = "restroomProcProdRate["+(((ind*1000+ind1)*1000)+ind2)+"]";     
       String numeratorInp = "restroomProcProdRateNumerator["+(((ind*1000+ind1)*1000)+ind2)+"]";     
       String denominatorInp = "restroomProcProdRateDenominator["+(((ind*1000+ind1)*1000)+ind2)+"]";     
       String sharingInp = "restroomProcProdSharing["+(((ind*1000+ind1)*1000)+ind2)+"]";     
%>
<tr>
<% if(ind2==0) { %>
<td rowspan='<%=prodQty%>'><%=cpD.getShortDesc()%></td>
<% } %>
<td><%=iD.getSkuNum()%></td>
<td><%=iD.getShortDesc()%></td>
<td><%=pupD.getUnitSize()%>&nbsp;<%=pupD.getUnitCd()%></td>
<td><%=pupD.getPackQty()%></td>
<% if(dilutionRateS!=null && dilutionRateS.trim().length()>0) {%>
<td>1:<bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=dilutionRateInp%>'/></td>
<% } else { %>
<td>&nbsp;</td>
<% } %>
<td><bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=usagRateInp%>' />
    <bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=numeratorInp%>'/> /
    <bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=denominatorInp%>'/> /
   <%=denominator1S%>
<td> <bean:write name='SPENDING_ESTIMATOR_FORM' property='<%=sharingInp%>'/></td>
</tr>
<% } %>
<% } %>
<% } %>
  <tr>
  <td colspan='8'>
  <html:submit property="action" value="Modify Products"/>
  </td>
</tr>
</table>
</td>
</tr>
<% } %>
<% } %>

