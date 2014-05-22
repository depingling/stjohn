
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
     BigDecimal estimatedItemsAmount = theForm.getEstimatedItemsAmount();
      
%>

<table>
<tr><td class="tableheader" align='center'><b>Allocated Categories</b></td></tr>
<% if(sumProductAmount!=null) { %>
<tr><td align='left'><b>Calulated Product Year Price: </b>
 <%=CurrencyFormat.format(sumProductAmount)%></td></tr>
<% } %>
<tr><td><b>Spend Factor to Apply for Estimated Items (%):</b>
<html:text name='SPENDING_ESTIMATOR_FORM' property='estimatedItemsFactor' size='4'/> 
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
     String allocatedCategoryPercentInp = "allocatedCategoryPer["+ind+"]";     
     BigDecimal amount = acVw.getAmount();
     BigDecimal allLocationsAmount = acVw.getAllLocationsAmount();
     %>

<tr>
<td><%=acD.getName()%></td>
<td><html:text name='SPENDING_ESTIMATOR_FORM' property='<%=allocatedCategoryPercentInp%>' size='4' 
     onchange='sumVal();' />
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
  <tr>
  <td>&nbsp;</td> 
  <td><html:text styleClass="textreadonly" size="7" 
    name="SPENDING_ESTIMATOR_FORM" property="totalPercent" readonly="true"/>
  </td>
  <td>&nbsp;</td> 
<% if(facilityQty>1 ) {%>
  <td>&nbsp;</td> 
<% } %>
  </tr>  
  <tr>
  <td colspan='<%=nbColumns%>'>
  <html:submit property="action" value="Save Allocation Percent"/>
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

 sumVal();
</script>

<% } %>
<% } %>

