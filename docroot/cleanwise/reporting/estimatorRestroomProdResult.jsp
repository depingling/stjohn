<!--  -->
<% { %>

<% 
   EstimatorProdResultViewVector eprVwV = theForm.getRestroomProdResults();
   if(eprVwV!=null && eprVwV.size()>0) {
   EstimatorFacilityJoinView facilityJoin = theForm.getFacilityJoin();
   EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
   int facilityQty = facility.getFacilityQty();
   int nbColumns = 7;
   if(facilityQty>1) nbColumns += 2;
%>
<tr><td><b class="tableheader">Product Set</b></td></tr>
<tr>
<td>
<table border='2'>
<tr>
<td bgcolor='#cccccc'><b>Sku #</b></td>
<td bgcolor='#cccccc'><b>Product</b></td>
<td bgcolor='#cccccc'><b>Size</b></td>
<td bgcolor='#cccccc'><b>Pack</b></td>
<td bgcolor='#cccccc'><b>Price</b></td>
<td bgcolor='#cccccc'><b>Year Qty</b></td>
<td bgcolor='#cccccc'><b>Year Amount</b></td>
<% if(facilityQty>1) {%>
<td bgcolor='#cccccc'><b>All Locations Year Qty</b></td>
<td bgcolor='#cccccc'><b>All Locations Year Amount</b></td>
<% } %>
</tr>
<%
   int ind = -1;
   for(Iterator iter = eprVwV.iterator(); iter.hasNext();) {
     ind ++;
     EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
 %>
<tr>
<td><%=eprVw.getSkuNum()%></td>
<td><%=eprVw.getProductName()%></td>
<td><%=eprVw.getUnitSize()%><br><%=eprVw.getUnitCd()%></td>
<td><%=eprVw.getPackQty()%></td>
<td align='right'><%=CurrencyFormat.format(eprVw.getProductPrice())%></td>
<td align='right'><%=NumberFormat.getNumberInstance().format(eprVw.getYearQty())%></td>
<td align='right'><%=CurrencyFormat.format(eprVw.getYearPrice())%></td>
<% if(facilityQty>1) {%>
<td align='right'><%=NumberFormat.getNumberInstance().format(eprVw.getAllFacilityYearQty())%></td>
<td align='right'><%=CurrencyFormat.format(eprVw.getAllFacilityYearPrice())%></td>
<% } %>
</tr>
<% } %>
</table>
</td>
</tr>
<% } %>
<% } %>
