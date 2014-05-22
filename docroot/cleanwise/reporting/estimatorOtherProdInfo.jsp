
<!--  -->
<% { %>

<% 
   CleaningScheduleJoinViewVector csjVwV = theForm.getOtherSchedules();
   if(csjVwV!=null && csjVwV.size()>0) {
%>
<tr><td><b class="tableheader">Selected Products</b></td></tr>
<tr>
<td>
<table border='2'>
<tr bgcolor='#cccccc'>
<td align='center'><b>Procedure</b></td>
<td align='center'><b>Sku #</b></td>
<td align='center'><b>Product</b></td>
<td align='center'><b>Size</b></td>
<td align='center'><b>Pack</b></td>
<td align='center'><b>Usage<br>Rate</b></td>
<td align='center'><b>Sharing Percent</b></td>
</tr>
<%
   int ind = -1;
   for(Iterator iter = csjVwV.iterator(); iter.hasNext();) {
     ind ++;
     CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
     CleaningScheduleData csD = csjVw.getSchedule();
     String cleaningScheduleCd = csD.getCleaningScheduleCd();
     CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
%>

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
     ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
    
     ItemData iD = pajVw.getItem();
     ProdApplData paD = pajVw.getProdAppl();
     if(paD.getEstimatorFacilityId()<=0) {
       continue;
     }
     ind2++;
     String estimatorProductCd = paD.getEstimatorProductCd();
     ProductUomPackData pupD = pajVw.getProductUomPack();
     String usageRateS = pajVw.getUsageRate();
     String numeratorS = pajVw.getUnitCdNumerator();
     String denominatorS = pajVw.getUnitCdDenominator();
     String denominator1S = pajVw.getUnitCdDenominator1();
     String sharingS = pajVw.getSharingPercent();
%>
<tr>
<% if(ind2==0) { %>
<td rowspan='<%=prodQty%>'><%=cpD.getShortDesc()%></td>
<% } %>
<td><%=iD.getSkuNum()%></td>
<td><%=iD.getShortDesc()%></td>
<td><%=pupD.getUnitSize()%>&nbsp;<%=pupD.getUnitCd()%></td>
<td><%=pupD.getPackQty()%></td>
<td>
   <%=usageRateS%> 
   <%=paD.getUnitCdNumerator()%>&nbsp;per&nbsp;<%=paD.getUnitCdDenominator()%>&nbsp;per&nbsp;<%=paD.getUnitCdDenominator1()%>
</td>

<td> <%=sharingS%></td>
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

