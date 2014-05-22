
<!--  -->
<% { %>

<% 
   AllocatedCategoryViewVector acVwV = theForm.getAllocatedCategories();
   BigDecimal sumProductAmount = theForm.getSumProductAmount();
   if(acVwV!=null && acVwV.size()>0) {
     EstimatorFacilityJoinView facilityJoin = theForm.getFacilityJoin();
     EstimatorFacilityData facility = facilityJoin.getEstimatorFacility();
     BigDecimal estimatedItemsFactorBD = facility.getEstimatedItemsFactor();
     int facilityQty = facility.getFacilityQty();
     int nbColumns = 3;
     if(facilityQty>1) nbColumns += 1;
     double estimatedItemsFactorDb = (estimatedItemsFactorBD==null)? 0: estimatedItemsFactorBD.doubleValue();
     EstimatorProdResultViewVector floorProdResults = theForm.getFloorProdResults();
     EstimatorProdResultViewVector restroomProdResults = theForm.getRestroomProdResults();
     EstimatorProdResultViewVector paperPlusProdResults = theForm.getPaperPlusProdResults();
     BigDecimal estimatedItemsAmount = theForm.getEstimatedItemsAmount();
%>

<table>
<tr><td class="tableheader" align='center'><b>Allocated Categories</b></td></tr>
<% if(paperPlusProdResults==null) { %>
<tr><td align='left'><font color='red'>No Paper, Liner, Soap Products Calculated </font></td></tr>
<% } %>
<% if(floorProdResults==null) { %>
<tr><td align='left'><font color='red'>No Floor Chemicals Calculated</font></td></tr>
<% } %>
<% if(restroomProdResults==null) { %>
<tr><td align='left'><font color='red'>No Restroom Chemicals Calculated</font></td></tr>
<% } %>
<% if(sumProductAmount!=null) { %>
<tr><td align='left'><b>Calulated Product Year Price: </b>
 <%=CurrencyFormat.format(sumProductAmount)%></td></tr>
<% } %>
<tr><td><b>Spend Factor to Apply for Estimated Items (%):</b>
<bean:write name='SPENDING_ESTIMATOR_FORM' property='estimatedItemsFactor'/> 
<% if(sumProductAmount!=null) { %>
<tr><td align='left'><b>Spend to be Allocated: </b>
<%=CurrencyFormat.format(estimatedItemsAmount)%>
<% } %>

<tr>
<td>
<table border='2'>
<tr>
<td align='center' class='tableheader' bgcolor='#cccccc'><b>Category</b></td>
<td align='center' class='tableheader' bgcolor='#cccccc'><b>Allocated Percent</b></td>
<td align='center' class='tableheader' bgcolor='#cccccc'><b>Year Amount</b></td>
<% if(facilityQty>1) {%>
<td align='center' class='tableheader' bgcolor='#cccccc'><b>All Locations Year Amount</b></td>
<% } %>
</tr>
<%
   int ind = -1;
   for(Iterator iter = acVwV.iterator(); iter.hasNext();) {
     ind ++;
     AllocatedCategoryView acVw = (AllocatedCategoryView) iter.next();
     AllocatedCategoryData acD = acVw.getAllocatedCategory();
     BigDecimal allocatedCategoryPercent = acD.getPercent();
     BigDecimal amount = acVw.getAmount();
     BigDecimal allLocationsAmount = acVw.getAllLocationsAmount();
     %>

<tr>
<td><%=acD.getName()%></td>
<td align='right'><%=allocatedCategoryPercent%>
</td>
<td align='right'>
  <% if(amount!=null) {%>
  <%=CurrencyFormat.format(amount)%>
  <% } else { %>
   No Value>
  <% } %> 
</td>
<% if(facilityQty>1 ) {%>
<td align='right'>
  <% if(allLocationsAmount!=null) {%>
  <%=CurrencyFormat.format(allLocationsAmount)%>
  <% } else { %>
   &nbsp;
  <% } %> 
</td>
<% } %>
<% } %>
<!--
  <tr>
  <td>&nbsp;</td> 
  <td><bean:write name="SPENDING_ESTIMATOR_FORM" property="totalPercent"/>
  </td>
  <td>&nbsp;</td> 
<% if(facilityQty>1 ) {%>
  <td>&nbsp;</td> 
<% } %>
<td>
  </tr>  
-->
  <tr>
  <td colspan='<%=nbColumns%>'>
  <html:submit property="action" value="Modify Allocation Percent"/>
  </td>
</tr>
</table>
</td>
</tr>
</table>

<script language="JavaScript">
function sumVal () {
  sum = 0;
  lf = "";
  sss = <%=acVwV.size()%>;
  for(ii=0; ii < sss; ii++) {
    lf = "allocatedCategoryPer["+ii+"]";
    val = document.forms[0].elements[lf].value * 1000.0;
    sum = sum + val;
  }
  sum = Math.round(sum);
  var res = ""+sum;
  var lll = res.length;
  if(lll==1) res = "0.00"+res;
  if(lll==2) res = "0.0"+res;
  if(lll==3) res = "0."+res;
  if(lll>2) res = res.substring(0,lll-3)+"."+res.substring(lll-3,lll);
  document.forms[0].elements['totalPercent'].value = res;
}


</script>

<% } %>
<% } %>

