<!--  -->
<% { %>

<% 
   CleaningScheduleJoinViewVector csjVwV = theForm.getRestroomCleaningSchedules();
   if(csjVwV!=null && csjVwV.size()>0) {
    // boolean[] paperPlusProductApplyFl = new boolean[ppaVwV.size()];
    // theForm.setPaperPlusProductApplyFl(paperPlusProductApplyFl);
%>
<tr><td><b class="tableheader">Select Restroom Cleaning Products</b></td></tr>
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
     String procFrequencyInp = "restroomCleaningProcFrequency["+ind+"]";     
     String procTimePeriodCdInp = "restroomCleaningProcTimePeriodCd["+ind+"]";     
     CleaningScheduleData csD = csjVw.getSchedule();
     BigDecimal procFrequency = csD.getFrequency();
     String procTimePeriodCd = csD.getTimePeriodCd();
     CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
%>

<tr>
<td colspan ='8' bgcolor='#eeeeee'><b><%=csD.getName()%></b>&nbsp;&nbsp;
  Perform:
  <html:text name='SPENDING_ESTIMATOR_FORM' property='<%=procFrequencyInp%>' size='3'/> 
  time(s) per
  <html:select name='SPENDING_ESTIMATOR_FORM' property='<%=procTimePeriodCdInp%>' >
  <html:option value='<%=RefCodeNames.TIME_PERIOD_CD.YEAR%>'><%=RefCodeNames.TIME_PERIOD_CD.YEAR%></html:option>
  <html:option value='<%=RefCodeNames.TIME_PERIOD_CD.MONTH%>'><%=RefCodeNames.TIME_PERIOD_CD.MONTH%></html:option>
  <html:option value='<%=RefCodeNames.TIME_PERIOD_CD.WEEK%>'><%=RefCodeNames.TIME_PERIOD_CD.WEEK%></html:option>
  <html:option value='<%=RefCodeNames.TIME_PERIOD_CD.WORKING_DAY%>'><%=RefCodeNames.TIME_PERIOD_CD.WORKING_DAY%></html:option>
  </html:select>&nbsp;
   <% if(procFrequency!=null && procFrequency.doubleValue()>0.0001) {%>
  (Usually <%=procFrequency%> time(s) per <%=procTimePeriodCd%>)
  <% } %> 
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
     if(pajVwV!=null) {
       prodQty = pajVwV.size();
     }
  %>
<% if(prodQty==0) { %>
<tr>
<td><%=cpD.getShortDesc()%></td>
<td colspan = '7'><<<<< No Products for This Procedure >>>>></td>
</tr>
<% } else {
   int ind2 = -1;
   for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
     ind2++;
     ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
     ItemData iD = pajVw.getItem();
     ProdApplData paD = pajVw.getProdAppl();
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
     String denominator1Inp = "restroomProcProdRateDenominator1["+(((ind*1000+ind1)*1000)+ind2)+"]";     
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
<td>1:<html:text name='SPENDING_ESTIMATOR_FORM' property='<%=dilutionRateInp%>' size='4'/> </td>
<td><html:text 
  name='SPENDING_ESTIMATOR_FORM' property='<%=usagRateInp%>'  size='4'/><html:select 
   name='SPENDING_ESTIMATOR_FORM' property='<%=numeratorInp%>'  styleClass='smalltext'>
  <html:option value='<%=RefCodeNames.UNIT_CD.OZ%>'><%=RefCodeNames.UNIT_CD.OZ%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.PACK%>'><%=RefCodeNames.UNIT_CD.PACK%></html:option>
  </html:select><html:select 
   name='SPENDING_ESTIMATOR_FORM' property='<%=denominatorInp%>'  styleClass='smalltext'>
  <html:option value='<%=RefCodeNames.UNIT_CD.FACILITY%>'><%=RefCodeNames.UNIT_CD.FACILITY%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.TOILET%>'><%=RefCodeNames.UNIT_CD.TOILET%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.RESTROOM%>'><%=RefCodeNames.UNIT_CD.RESTROOM%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.SHOWER%>'><%=RefCodeNames.UNIT_CD.SHOWER%></html:option>
  </html:select><html:select 
   name='SPENDING_ESTIMATOR_FORM' property='<%=denominator1Inp%>'  styleClass='smalltext'>
  <html:option value=''>&nbsp;</html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.PROCEDURE%>'><%=RefCodeNames.UNIT_CD.PROCEDURE%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.YEAR%>'><%=RefCodeNames.UNIT_CD.YEAR%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.WEEK%>'><%=RefCodeNames.UNIT_CD.WEEK%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.DAY%>'><%=RefCodeNames.UNIT_CD.DAY%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.WORKING_DAY%>'><%=RefCodeNames.UNIT_CD.WORKING_DAY%></html:option>
  </html:select>&nbsp;<br>
<% if(usagRateS!=null) { %><%=usagRateS%>&nbsp;<%=numeratorS%>&nbsp;per&nbsp;<%=denominatorS%>
<% if(Utility.isSet(denominator1S)) { %>&nbsp;per&nbsp;<%=denominator1S%><% } %><% } %>
<!--
<html:hidden name='SPENDING_ESTIMATOR_FORM' property='<%=usagRateInp%>' value='<%=numeratorS%>' />
<html:hidden name='SPENDING_ESTIMATOR_FORM' property='<%=denominatorInp%>' value='<%=denominatorS%>' />
<html:hidden name='SPENDING_ESTIMATOR_FORM' property='<%=denominator1Inp%>' value='<%=denominator1S%>' />
-->
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

