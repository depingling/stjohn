<!--  -->
<% { %>

<% 
   CleaningScheduleJoinViewVector csjVwV = theForm.getFloorCleaningSchedules();
   if(csjVwV!=null && csjVwV.size()>0) {
%>
<tr><td><b class="tableheader">Select Floor Cleaning Products</b></td></tr>
<tr>
<td>
<table border='2'>
<%
   int ind = -1;
   String cleaningScheduleCdPrev = "";
   String cleaningTitle = "";
   for(Iterator iter = csjVwV.iterator(); iter.hasNext();) {
     ind ++;
     CleaningScheduleJoinView csjVw = (CleaningScheduleJoinView) iter.next();
     String procFrequencyInp = "floorCleaningProcFrequency["+ind+"]";     
     String procTimePeriodCdInp = "floorCleaningProcTimePeriodCd["+ind+"]";     
     CleaningScheduleData csD = csjVw.getSchedule();
     String cleaningScheduleCd = csD.getCleaningScheduleCd();
     boolean newCleaningTitleFl = false;
     if(!cleaningScheduleCdPrev.equals(cleaningScheduleCd)) {
       newCleaningTitleFl = true;
       cleaningScheduleCdPrev = cleaningScheduleCd;
       if(RefCodeNames.CLEANING_SCHEDULE_CD.HT_CARPET_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Carpet Floor. High Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.HT_CERAMIC_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Ceramic Floor. High Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.HT_CONCRETE_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Concrete Floor. High Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.HT_TERRAZZO_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Terrazzo Floor. High Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.HT_VCT_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "VCT Tile Floor. High Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.HT_WOOD_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Wood Floor. High Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.MT_CARPET_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Carpet Floor. Medium Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.MT_CERAMIC_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Ceramic Floor. Medium Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.MT_CONCRETE_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Concrete Floor. Medium Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.MT_TERRAZZO_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Terrazzo Floor. Medium Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.MT_VCT_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "VCT Tile Floor. Medium Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.MT_WOOD_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Wood Floor. Medium Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.LT_CARPET_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Carpet Floor. Low Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.LT_CERAMIC_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Ceramic Floor. Low Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.LT_CONCRETE_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Concrete Floor. Low Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.LT_TERRAZZO_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Terrazzo Floor. Low Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.LT_VCT_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "VCT Tile Floor. Low Traffic";
       } else if (RefCodeNames.CLEANING_SCHEDULE_CD.LT_WOOD_FLOOR_CLEANING.equals(cleaningScheduleCd)) {
         cleaningTitle = "Wood Floor. Low Traffic";
       }
     }
     BigDecimal procFrequency = csD.getFrequency();
     String procTimePeriodCd = csD.getTimePeriodCd();
     CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
     if(newCleaningTitleFl) {
     %>
<tr>
<td colspan ='8' align='center' class='tableheader' bgcolor='#cccccc'>
 <b><%=cleaningTitle%></b>
</tr>
<tr bgcolor='#cccccc'>
<td align='center'><b>Procedure</b></td>
<td align='center'><b>Sku #</b></td>
<td align='center'><b>Product</b></td>
<td align='center'><b>Size</b></td>
<td align='center'><b>Pack</b></td>
<td align='center'><b>Dilution</b></td>
<td align='center'><b>Usage<br>Rate</b></td>
<td align='center'><b>Sharing Percent</b></td>
</tr>
     <% } %>

<tr bgcolor='#eeeeee'>
<td colspan ='8'><b><%=csD.getName()%></b>&nbsp;&nbsp;
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
     String dilutionRateInp = "floorProcProdDilution["+(((ind*1000+ind1)*1000)+ind2)+"]";     
     String usagRateInp = "floorProcProdRate["+(((ind*1000+ind1)*1000)+ind2)+"]";     
     String numeratorInp = "floorProcProdRateNumerator["+(((ind*1000+ind1)*1000)+ind2)+"]";     
     String denominatorInp = "floorProcProdRateDenominator["+(((ind*1000+ind1)*1000)+ind2)+"]";     
     String denominator1Inp = "floorProcProdRateDenominator1["+(((ind*1000+ind1)*1000)+ind2)+"]";     
     String sharingInp = "floorProcProdSharing["+(((ind*1000+ind1)*1000)+ind2)+"]";     
%>
<tr>
<% if(ind2==0) { %>
<td rowspan='<%=prodQty%>'><%=cpD.getShortDesc()%></td>
<% } %>
<td><%=iD.getSkuNum()%></td>
<td><%=iD.getShortDesc()%></td>
<td><%=pupD.getUnitSize()%>&nbsp;<%=pupD.getUnitCd()%></td>
<td><%=pupD.getPackQty()%></td>
<td>1:<html:text name='SPENDING_ESTIMATOR_FORM' property='<%=dilutionRateInp%>' size='4'/></td>
<td><html:text name='SPENDING_ESTIMATOR_FORM' property='<%=usagRateInp%>' 
   size='4'/><html:select name='SPENDING_ESTIMATOR_FORM' property='<%=numeratorInp%>'  styleClass='smalltext'>
  <html:option value='<%=RefCodeNames.UNIT_CD.SQ_F%>'><%=RefCodeNames.UNIT_CD.SQ_F%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.PACK%>'><%=RefCodeNames.UNIT_CD.PACK%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.GAL%>'><%=RefCodeNames.UNIT_CD.GAL%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.LB%>'><%=RefCodeNames.UNIT_CD.LB%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.UNIT%>'><%=RefCodeNames.UNIT_CD.UNIT%></html:option>
  </html:select><html:select 
   name='SPENDING_ESTIMATOR_FORM' property='<%=denominatorInp%>'  styleClass='smalltext'>
  <html:option value='<%=RefCodeNames.UNIT_CD.GAL%>'><%=RefCodeNames.UNIT_CD.GAL%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.LB%>'><%=RefCodeNames.UNIT_CD.LB%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.YEAR%>'><%=RefCodeNames.UNIT_CD.YEAR%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.WEEK%>'><%=RefCodeNames.UNIT_CD.WEEK%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.PROCEDURE%>'><%=RefCodeNames.UNIT_CD.PROCEDURE%></html:option>
 </html:select><!--
  <html:hidden name='SPENDING_ESTIMATOR_FORM' property='<%=numeratorInp%>' value='<%=numeratorS%>'/>
  <%=numeratorS%> per
  <html:hidden name='SPENDING_ESTIMATOR_FORM' property='<%=denominatorInp%>' value='<%=denominatorS%>'/>
  <%=denominatorS%> 
--><br>
  
<% if(paD.getUsageRate()!=null) { %>
<%=paD.getUsageRate()%>&nbsp;<%=paD.getUnitCdNumerator()%>&nbsp;per&nbsp;<%=paD.getUnitCdDenominator()%>
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

