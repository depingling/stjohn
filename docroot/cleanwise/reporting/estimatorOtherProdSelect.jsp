
<!--  -->
<% { %>

<% 
   CleaningScheduleJoinViewVector csjVwV = theForm.getOtherSchedules();
   if(csjVwV!=null && csjVwV.size()>0) {
%>
<tr><td><b class="tableheader">Select Products</b></td></tr>
<tr>
<td>
<table border='2'>
<%
   int ind = -1;
   for(Iterator iter = csjVwV.iterator(); iter.hasNext();) {
     ind ++;
     CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
     CleaningScheduleData csD = csjVw.getSchedule();
     String cleaningScheduleCd = csD.getCleaningScheduleCd();
     CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
%>
<tr>
<td colspan ='7' align='center' class='tableheader' bgcolor='#cccccc'>
 <b><%=csD.getName()%></b>
</tr>
<tr bgcolor='#cccccc'>
<td align='center'><b>Procedure</b></td>
<td align='center'><b>Sku #</b></td>
<td align='center'><b>Product</b></td>
<td align='center'><b>Size</b></td>
<td align='center'><b>Pack</b></td>
<td align='center'><b>Amount</b></td>
<td align='center'><b>Sharing Percent</b></td>
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
     if(pajVwV!=null) {
       prodQty = pajVwV.size();
     }
  %>
<% if(prodQty==0) { %>
<tr>
<td><%=cpD.getShortDesc()%></td>
<td colspan = '6'><<<<< No Products for This Procedure >>>>></td>
</tr>
<% } else {
   int ind2 = -1;
   for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
     ind2++;
     ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
    
     ItemData iD = pajVw.getItem();
     ProdApplData paD = pajVw.getProdAppl();

     ProductUomPackData pupD = pajVw.getProductUomPack();
     String usagRateS = pajVw.getUsageRate();
     String numeratorS = pajVw.getUnitCdNumerator();
     String denominatorS = pajVw.getUnitCdDenominator();
     String denominator1S = pajVw.getUnitCdDenominator1();
     String sharingS = pajVw.getSharingPercent();
     String usageRateInp = "otherProdRate["+(((ind*1000+ind1)*1000)+ind2)+"]";     
     String numeratorInp = "otherProdRateNumerator["+(((ind*1000+ind1)*1000)+ind2)+"]";     
     String denominatorInp = "otherProdRateDenominator["+(((ind*1000+ind1)*1000)+ind2)+"]";     
     String denominator1Inp = "otherProdRateDenominator1["+(((ind*1000+ind1)*1000)+ind2)+"]";     
     String sharingInp = "otherProdSharing["+(((ind*1000+ind1)*1000)+ind2)+"]";     
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
   <html:text name='SPENDING_ESTIMATOR_FORM' property='<%=usageRateInp%>' 
   size='4'/><html:select 
   name='SPENDING_ESTIMATOR_FORM' property='<%=numeratorInp%>'  styleClass='smalltext'>
  <html:option value=''>&nbsp;</html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.UNIT%>'><%=RefCodeNames.UNIT_CD.UNIT%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.PACK%>'><%=RefCodeNames.UNIT_CD.PACK%></html:option>
  </html:select><html:select 
   name='SPENDING_ESTIMATOR_FORM' property='<%=denominatorInp%>'  styleClass='smalltext'>
  <html:option value=''>&nbsp;</html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.FACILITY%>'><%=RefCodeNames.UNIT_CD.FACILITY%></html:option>
  </html:select><html:select 
   name='SPENDING_ESTIMATOR_FORM' property='<%=denominator1Inp%>'  styleClass='smalltext'>
  <html:option value=''>&nbsp;</html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.YEAR%>'><%=RefCodeNames.UNIT_CD.YEAR%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.DAY%>'><%=RefCodeNames.UNIT_CD.DAY%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.WORKING_DAY%>'><%=RefCodeNames.UNIT_CD.WORKING_DAY%></html:option>
  </html:select>&nbsp;<br>
   <% if(paD.getUsageRate()!=null) { %>
     <%=paD.getUsageRate()%>
     <%=paD.getUnitCdNumerator()%>&nbsp;per&nbsp;<%=paD.getUnitCdDenominator()%>
     &nbsp;per&nbsp;<%=paD.getUnitCdDenominator1()%>
   <% } %>

</td>

<td> <html:text name='SPENDING_ESTIMATOR_FORM' property='<%=sharingInp%>' size='4'/></td>
</tr>
<% } %>
<% } %>
<% } %>
<% } %>
  <tr>
  <td colspan='8'>
  <html:submit property="action" value="Save Products"/>
  </td>
</tr>
</table>
</td>
</tr>
<% } %>
<% } %>

