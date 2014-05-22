<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.CurrencyFormat" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% { %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="SPENDING_ESTIMATOR_FORM" type="com.cleanwise.view.forms.SpendingEstimatorForm"/>
<%
 CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER); 
 String userType = user.getUser().getUserTypeCd(); 
 boolean adminFl = false;
 if ( RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType) ) {
   adminFl = true;
 }
 
 EstimatorFacilityJoinView efVw = theForm.getFacilityJoin();
 EstimatorFacilityData efD = null;
 boolean templateFl = true;
    if(efVw!=null) {
      efD = efVw.getEstimatorFacility();
      if(!"T".equals(efD.getTemplateFl())) templateFl = false;
    }
%>


<table>
<html:form name="SPENDING_ESTIMATOR_FORM" action="reporting/estimator"
    scope="session" type="com.cleanwise.view.forms.SpendingEstimatorForm">
 <tbody>
<tr>
</tr>
<tr>
<td>Facility Name:&nbsp;<html:text name='SPENDING_ESTIMATOR_FORM' property='name' size='70'/>
  &nbsp;&nbsp;&nbsp;
Number of Locations:&nbsp;<html:text name='SPENDING_ESTIMATOR_FORM' property='facilityQty' size='5'/>
</td>
<tr>
<td>
&nbsp;
</td>
</tr>
<tr>
<td>
<!-- ///////////////// RESULTS //////////////////// -->
<%
 if(efD!=null) {
   BigDecimal yearPriceBD = new BigDecimal(0);
   BigDecimal allFacilityYearPriceBD = new BigDecimal(0);
   boolean totalFl = true;
   int facilityQty = efD.getFacilityQty(); 
%>
<tr>
<td>
<table border="1">
<tr>
<td align='center' colspan='3' class='tableheader'>Calculated Results (one year)</td>
</tr>
<tr>
<td align='center' class='tableheader'>&nbsp;</td>
<td align='center' class='tableheader'>One Location</td>
<td align='center' class='tableheader'>All Locations</td>
</tr>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Paper, Soap, Liners&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<% { %>
<% 
   EstimatorProdResultViewVector eprVwV = theForm.getPaperPlusProdResults();
   if(eprVwV!=null && eprVwV.size()>0) {
       BigDecimal paperYearPriceBD = new BigDecimal(0);
       BigDecimal paperAllFacilityYearPriceBD = new BigDecimal(0);
       for(Iterator iter = eprVwV.iterator(); iter.hasNext();) {
         EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
         paperYearPriceBD = paperYearPriceBD.add(eprVw.getYearPrice());
         paperAllFacilityYearPriceBD = paperAllFacilityYearPriceBD.add(eprVw.getAllFacilityYearPrice());
       }
       yearPriceBD = yearPriceBD.add(paperYearPriceBD);
       allFacilityYearPriceBD = allFacilityYearPriceBD.add(paperAllFacilityYearPriceBD);
 %>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CurrencyFormat.format(paperYearPriceBD)%></td>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CurrencyFormat.format(paperAllFacilityYearPriceBD)%></td>
<% } else {
  totalFl = false;
%>
<td colspan='2'> Please review the procedures</td>
<% }} %>
</tr>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Floor Care&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<%
{
   EstimatorProdResultViewVector eprVwV = theForm.getFloorProdResults();
   if(eprVwV!=null && eprVwV.size()>0) {
       BigDecimal floorYearPriceBD = new BigDecimal(0);
       BigDecimal floorAllFacilityYearPriceBD = new BigDecimal(0);
       for(Iterator iter = eprVwV.iterator(); iter.hasNext();) {
         EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
         floorYearPriceBD = floorYearPriceBD.add(eprVw.getYearPrice());
         floorAllFacilityYearPriceBD = floorAllFacilityYearPriceBD.add(eprVw.getAllFacilityYearPrice());
       }
       yearPriceBD = yearPriceBD.add(floorYearPriceBD);
       allFacilityYearPriceBD = allFacilityYearPriceBD.add(floorAllFacilityYearPriceBD);
 %>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CurrencyFormat.format(floorYearPriceBD)%></td>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CurrencyFormat.format(floorAllFacilityYearPriceBD)%></td>
<% } else {
  totalFl = false;
%>
<td colspan='2'> Please review the procedures</td>
<% }} %>
</tr>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Restroom Care&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<% { %>
<% 
   EstimatorProdResultViewVector eprVwV = theForm.getRestroomProdResults();
   if(eprVwV!=null && eprVwV.size()>0) {
       BigDecimal restroomYearPriceBD = new BigDecimal(0);
       BigDecimal restroomAllFacilityYearPriceBD = new BigDecimal(0);
       for(Iterator iter = eprVwV.iterator(); iter.hasNext();) {
         EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
         restroomYearPriceBD = restroomYearPriceBD.add(eprVw.getYearPrice());
         restroomAllFacilityYearPriceBD = restroomAllFacilityYearPriceBD.add(eprVw.getAllFacilityYearPrice());
       }
       yearPriceBD = yearPriceBD.add(restroomYearPriceBD);
       allFacilityYearPriceBD = allFacilityYearPriceBD.add(restroomAllFacilityYearPriceBD);
 %>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CurrencyFormat.format(restroomYearPriceBD)%></td>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CurrencyFormat.format(restroomAllFacilityYearPriceBD)%></td>
<% } else {
  totalFl = false;
%>
<td colspan='2'> Please review the procedures</td>
<% }} %>
</tr>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<% { %>
<% 
   EstimatorProdResultViewVector eprVwV = theForm.getOtherProdResults();
   if(eprVwV!=null && eprVwV.size()>0) {
       BigDecimal otherYearPriceBD = new BigDecimal(0);
       BigDecimal otherAllFacilityYearPriceBD = new BigDecimal(0);
       for(Iterator iter = eprVwV.iterator(); iter.hasNext();) {
         EstimatorProdResultView eprVw = (EstimatorProdResultView) iter.next();
         otherYearPriceBD = otherYearPriceBD.add(eprVw.getYearPrice());
         otherAllFacilityYearPriceBD = otherAllFacilityYearPriceBD.add(eprVw.getAllFacilityYearPrice());
       }
       yearPriceBD = yearPriceBD.add(otherYearPriceBD);
       allFacilityYearPriceBD = allFacilityYearPriceBD.add(otherAllFacilityYearPriceBD);
 %>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CurrencyFormat.format(otherYearPriceBD)%></td>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CurrencyFormat.format(otherAllFacilityYearPriceBD)%></td>
<% } else {
  totalFl = false;
%>
<td colspan='2'> Please review the procedures</td>
<% }} %>
</tr>
<!-- Allocated categories -->
<% { %>

<% 
   AllocatedCategoryViewVector acVwV = theForm.getAllocatedCategories();
   if(acVwV!=null && acVwV.size()>0 && totalFl) {
     BigDecimal estimatedItemsAmount = theForm.getEstimatedItemsAmount();
     BigDecimal allFacilityEstimatedItemsAmount = 
        estimatedItemsAmount.multiply(new BigDecimal(facilityQty)).setScale(2,BigDecimal.ROUND_HALF_UP);
     yearPriceBD = yearPriceBD.add(estimatedItemsAmount);
     allFacilityYearPriceBD = allFacilityYearPriceBD.add(allFacilityEstimatedItemsAmount);
%>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Allocated Categories&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CurrencyFormat.format(estimatedItemsAmount)%></td>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CurrencyFormat.format(allFacilityEstimatedItemsAmount)%></td>
</tr>
<% }} %>
<% if(totalFl) {%>
<tr>
<td class="tableheader">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CurrencyFormat.format(yearPriceBD)%></td>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CurrencyFormat.format(allFacilityYearPriceBD)%></td>
</tr>
<% } %>
</table>
</td>
</tr>
<% } %>
<!--/////////////////  END OF RESULTS /////////////// -->
<tr>
<td>
&nbsp;
</td>
</tr>

<tr>
<td>
<table border='1'>
<tr>
<td>
<table width='730'>
<tr>
<td align='center'>
 Fill in the following fields for one(1) location only
</td>
</tr>
</tr>
<% List facilityGroups =  theForm.getFacilityGroups();
  if(facilityGroups!= null && facilityGroups.size()==0) { %>
<tr>
<td>Facility Group:&nbsp;<html:text name='SPENDING_ESTIMATOR_FORM' property='facilityGroup' size='70'/>
<html:hidden property='facilityGroupSelect' value='3'/>
</td>
</tr>
<% } else { %>
<tr>
<td>
<html:hidden property='facilityGroupSelect' value='2'/>
<div id='selectFacilityGroup'>
Facility Group:&nbsp;<html:select name='SPENDING_ESTIMATOR_FORM' property='facilityGroup'>
  <html:option value='<%=""%>'/>
  <% 
    
    for(Iterator iter = facilityGroups.iterator(); iter.hasNext(); ) { 
    String facilityGroup = (String) iter.next();
  %>
  <html:option value='<%=facilityGroup%>'/>
  <% } %>
</html:select>
 &nbsp;

 <button  class='smalltext' onclick='javascript: f_hideBox("selectFacilityGroup"); 
    f_showBox("typeFacilityGroup"); 
    document.forms[0].elements["facilityGroupSelect"].value="3";'>Add New</button>
</div>
<div id='typeFacilityGroup'>
  Facility Group:&nbsp;<html:text name='SPENDING_ESTIMATOR_FORM' property='newFacilityGroup' size='70'/>
  &nbsp;<button class='smalltext' onclick='f_hideBox("typeFacilityGroup");
    f_showBox("selectFacilityGroup");
    document.forms[0].elements["facilityGroupSelect"].value="2"; 
    return;'>Show List</button>
 <script language="JavaScript1.2">
   document.getElementById('typeFacilityGroup').style.display = 'none';
 </script>
</div>
</td>
</tr>
<% } %>
<tr>
<td>
<% if(templateFl) { %>
Template Flag:&nbsp;<html:checkbox name='SPENDING_ESTIMATOR_FORM' property='templateFl'/>
  &nbsp;&nbsp;&nbsp;
Product Catalog:
  <html:select  name='SPENDING_ESTIMATOR_FORM' property='catalogId'>
    <html:option value='0'>---- Select Catalog -----</html:option>
   <%
   CatalogDataVector catalogDV = theForm.getCatalogs();
   if(catalogDV!=null) {
   for(Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
     CatalogData catalogD = (CatalogData) iter.next();
   %>
    <html:option value='<%=""+catalogD.getCatalogId()%>'><%=catalogD.getShortDesc()%></html:option>
   <% }} %>
   </html:select>
<% } else { 
  int catalogId = theForm.getCatalogId();
   CatalogDataVector catalogDV = theForm.getCatalogs();
   if(catalogDV!=null) {
   for(Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
     CatalogData catalogD = (CatalogData) iter.next();
     if(catalogD.getCatalogId()==catalogId) {
     String catalogName = catalogD.getShortDesc();
%>
Product Catalog: <font class='tableheader'><%=catalogName%></font>
<% } break; }} %>

<% } %>
</td>
</tr>
<tr>
<td>
  Facility Type:&nbsp;
  <% if(templateFl) { %>
  <html:select  name='SPENDING_ESTIMATOR_FORM' property='facilityTypeCd' onchange='facilityTypeChange();' >
    <html:option value='<%=RefCodeNames.FACILITY_TYPE_CD.INDUSTRIAL%>'><%=RefCodeNames.FACILITY_TYPE_CD.INDUSTRIAL%></html:option>
    <html:option value='<%=RefCodeNames.FACILITY_TYPE_CD.OFFICE%>'><%=RefCodeNames.FACILITY_TYPE_CD.OFFICE%></html:option>
    <html:option value='<%=RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL%>'><%=RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL%></html:option>
    <html:option value='<%=RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL%>'><%=RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL%></html:option>
  </html:select>
  <% } else { %>
  <font class='tableheader'><bean:write   name='SPENDING_ESTIMATOR_FORM' property='facilityTypeCd'/></font>
  <% } %>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  Working Days per Year:&nbsp;
  <html:text name='SPENDING_ESTIMATOR_FORM' property='workingDayYearQty' size='4'/>
  &nbsp;&nbsp;&nbsp;
</td>
</tr>
<tr>
<td>
  Estimated Number of Resident Personnel (FTE):&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='personnelQty' size='6'/>
  &nbsp;&nbsp;&nbsp;
  Estimated Number of Visitors (per day):&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='visitorQty' size='6'/>
</td>
</tr>
<tr>
<td>
<div id="retail_section">
  Number of Retail Stations:&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='stationQty' size='4'/>
</div>
</td>
</tr>
<tr>
<td>
  &nbsp;
</td>
</tr>
<tr>
<td class='tableheader' border='solid'>
  Common Area
</td>
</tr>
<tr>
<td>
  Number of Common Area Trash Receptacles (per day):&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='commonAreaLinerPerDay' size='4'/>
</td>
</tr>
<tr>
<td>
  &nbsp;
</td>
</tr>
<tr>
<td class='tableheader' border='solid'>
  Bathrooms
</td>
</tr>
<tr>
<td>
  Number of Bathrooms:&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='bathroomQty' size='3'/>
&nbsp;&nbsp;&nbsp;&nbsp;
  Percent of Visitors Using Bathroom:
<html:text name='SPENDING_ESTIMATOR_FORM' property='visitorBathroomPer' size='3'/>
&nbsp;&nbsp;&nbsp;&nbsp;
  Number of Showers:
<html:text name='SPENDING_ESTIMATOR_FORM' property='showerQty' size='3'/>
</td>
</tr>

<tr>
<td>
  &nbsp;
</td>
</tr>
<tr>
<td class='tableheader' border='solid'>
  Floors
</td>
</tr>
<tr>
<td>
 Gross Footage:&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='grossFootage' size='6'/>
&nbsp;&nbsp;&nbsp;&nbsp;
Floor Machine:&nbsp;
  <html:select  name='SPENDING_ESTIMATOR_FORM' property='floorMachine' >
    <html:option value='Auto'>Auto</html:option>
    <html:option value='Rotary'>Rotary</html:option>
  </html:select>
<!--
&nbsp;&nbsp;&nbsp;&nbsp;
 Cleanble % (of Gross Sq. Footage):&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='cleanableFootagePercent' size='6'/>
&nbsp;&nbsp;&nbsp;&nbsp;
 Baseboard % of Cleanble Sq. Footage)&nbsp;
<html:text name='SPENDING_ESTIMATOR_FORM' property='baseboardPercent' size='4'/>
&nbsp;&nbsp;&nbsp;&nbsp;
-->
</td>
</tr>

<tr>
<td>
<table>
<tr>
<td><b>Floor Type</b></td>
<td width='150'><b>Percent of Total<br> Sq Footage</b></td>
<td width='100'><b>High Traffic<br> Percent</b></td>
<td width='100'><b>Medium Traffic<br> Percent</b></td>
<td width='100'><b>Low Traffic<br> Percent</b></td>
<tr>
<% 
   String[] floorTypes = theForm.getFloorTypes();
   String[] floorTypePercents = theForm.getFloorTypePercents();
   String[] floorTypePercentsHt = theForm.getFloorTypePercentsHt();
   String[] floorTypePercentsMt = theForm.getFloorTypePercentsMt();
   String[] floorTypePercentsLt = theForm.getFloorTypePercentsLt();
   boolean[] floorFl = theForm.getFloorTypeFl();
   boolean[] floorFlHt = theForm.getFloorTypeFlHt();
   boolean[] floorFlMt = theForm.getFloorTypeFlMt();
   boolean[] floorFlLt = theForm.getFloorTypeFlLt();
   double floorPr = 0;
   for(int ii=0; ii<floorTypes.length; ii++) {
     String totalPerEl = "floorTypePercentEl["+ii+"]";
     String totalPerHtEl = "floorTypePercentHtEl["+ii+"]";
     String totalPerMtEl = "floorTypePercentMtEl["+ii+"]";
     String totalPerLtEl = "floorTypePercentLtEl["+ii+"]";
%>
<% if(floorFl[ii]) { %>
<tr>
<td><%=floorTypes[ii]%></td>
<td><html:text name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerEl%>' size='6'/></td>
<% if(floorFlHt[ii]) { %>
<td><html:text name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerHtEl%>' size='6'/></td>
<% } else { %> <td>&nbsp;</td><% } %>
<% if(floorFlMt[ii]) { %>
<td><html:text name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerMtEl%>' size='6'/></td>
<% } else { %> <td>&nbsp;</td><% } %>
<% if(floorFlLt[ii]) { %>
<td><html:text name='SPENDING_ESTIMATOR_FORM' property='<%=totalPerLtEl%>' size='6'/></td>
<% } else { %> <td>&nbsp;</td><% } %>
</tr>
<% } %>
<% } %>
</table>
</td>
</tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
  <tr>
  <td>
  <html:submit property="action" value="Save Profile"/>
  <% 
      if(efD!=null) {
       String copyHref = "window.location='estimator.do?action=copyModel&id=" + 
       efD.getEstimatorFacilityId()+"';";
       String delHref = "window.location='estimator.do?action=delModel&id=" + 
       efD.getEstimatorFacilityId()+"';";
  %>
  <input type='button' name='copyModel' value='Copy Profile' onclick="<%=copyHref%>"/>
  <input type='button' name='deleteModel' value='Delete Profile' onclick="<%=delHref%>"/>
  <% } %>
  </td>
</tr>
<script language="JavaScript1.2">

function f_hideBox(boxid) {
  document.getElementById(boxid).style.display = 'none';
}

function f_showBox(boxid) {
  document.getElementById(boxid).style.display = 'block';  
}

function facilityTypeChange() {
  var facilityTypeCd = document.forms[0].elements['facilityTypeCd'].value;
  //alert(facilityTypeCd);
  switch (facilityTypeCd) {
  case '<%=RefCodeNames.FACILITY_TYPE_CD.INDUSTRIAL%>':
    f_hideBox("retail_section");
  break;
  case '<%=RefCodeNames.FACILITY_TYPE_CD.OFFICE%>':
    f_hideBox("retail_section");
  break;
  case '<%=RefCodeNames.FACILITY_TYPE_CD.FOOD_RETAIL%>':
    f_showBox("retail_section");
  break;
  case '<%=RefCodeNames.FACILITY_TYPE_CD.NON_FOOD_RETAIL%>':
    f_showBox("retail_section");
  break;
  default:
    f_hideBox("retail_section");
  }
}
 <% if(templateFl) { %>
 facilityTypeChange();
 <% } %>
</script>

</tbody></html:form>
</table>
<% } %>


