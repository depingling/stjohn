<!--  -->
<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.SPENDING_ESTIMATOR_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='productFilter') {
     dml.elements[i].checked=val;
   }
 }
}

//-->
</script>
<% { %>

<% 
   OrderGuideItemDescData[] productA = theForm.getEstimatorProducts();
   if(productA!=null && productA.length>0) {
%>
<tr><td><b class="tableheader">Select Products</b></td></tr>
<tr>
<td>
<table border='2'>
<tr>
<td align='center' bgcolor='#cccccc'><b>Sku #</b></td>
<td align='center' bgcolor='#cccccc'><b>Product</b></td>
<td align='center' bgcolor='#cccccc'><b>Size</b></td>
<td align='center' bgcolor='#cccccc'><b>Pack</b></td>
<td align='center' bgcolor='#cccccc'><b>Uom</b></td>
<td align='center' bgcolor='#cccccc'><b>Manufacturer</b></td>
<td align='center' bgcolor='#cccccc'><b>Price</b></td>
<td align='center' bgcolor='#cccccc'><b>&nbsp;</b></td>
</tr>
<%
  String categoryPrev = "";
  for(int ii=0; ii<productA.length; ii++) {
    OrderGuideItemDescData ogidD = productA[ii];
    int itemId = ogidD.getItemId();
    String itemIdS = ""+itemId;
    String category = ogidD.getCategoryDesc();
    String sku = ogidD.getCwSKU();
    BigDecimal price = ogidD.getPrice();
    String size = ogidD.getSizeDesc();
    String pack = ogidD.getPackDesc();
    String name = ogidD.getShortDesc();
    String manuf = ogidD.getManufacturerCd();
    String uom = ogidD.getUomDesc();
    if(category==null) {
      category = "";
    } else {
      category = category.trim();
    }
    if(ii==0 || !category.equalsIgnoreCase(categoryPrev)) {
       categoryPrev = category;
%>
<tr>
 <td colspan='8'  bgcolor='#cccccc'><b>
<% if(category.length()==0) { %>
  No category products</td>
<% } else {%>
 <%=category%></td>
<% } %>
</b></td>
</tr>
<% } %>
<tr>
<td><%=sku%></td>
<td><%=name%></td>
<td><%=size%></td>
<td><%=pack%></td>
<td><%=uom%></td>
<td><%=manuf%></td>
<td><%=CurrencyFormat.format(price)%></td>
<td>&nbsp
<html:multibox name='SPENDING_ESTIMATOR_FORM' property='productFilter' value='<%=itemIdS%>'/>
                                                       
</td>
</tr>
<% } %>
  <tr>
  <td colspan='5'>
  <html:submit property="action" value="Save Products"/>
  </td>
  <td colspan='3' align='right'>
<a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
  </td>
</tr>
</table>
</td>
</tr>
<% } %>
<% } %>

