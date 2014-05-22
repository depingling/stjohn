<%@ page language="java" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.EstimatorMgrForm" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% EstimatorMgrForm theForm = (EstimatorMgrForm) session.getAttribute("ESTIMATOR_MGR_FORM"); %>


<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.ESTIMATOR_MGR_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='productSelector') {
     dml.elements[i].checked=val;
   }
 }
}

function popLocate() {
  var loc = "estimatorMgrProductAdd.do";
//alert(loc);
  locatewin = window.open(loc,"tickersearch", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

//-->
</script>

<html:html>
<head>
<title>Cleaning Actions</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals//styles.css">
</head>

<body bgcolor="#cccccc">
<div class = "text">
<%
  CleaningProcDataVector buffCleanProcDV = theForm.getBufferedProcedures();
  ProdApplJoinViewVector buffPajVwV = theForm.getBufferedProducts();
   if(buffPajVwV!=null && buffCleanProcDV!=null &&
      buffPajVwV.size()>0 && buffCleanProcDV.size()>0) {
%>
  <table border="1" width="750">
  <tr bgcolor='#cccccc'><td colspan='9'><b>Buffered Product</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <a href='estimatorMgr.do?action=ClearBuffer'>Clear Buffer</a></td>
  <tr bgcolor='#cccccc'>
  <td><b>Estimator Page</b></td>
  <td><b>Cleaning Action</b></td>
  <td><b>Sku #</b></td>
  <td><b>Product</b></td>
  <td><b>Size</b></td>
  <td><b>Pack</b></td>
  <td><b>Dillution</b></td>
  <td><b>UsageRate</b></td>
  <td><b>&nbsp;</b></td>
  </tr>
<%
  for(Iterator iter = buffCleanProcDV.iterator(), iter1=buffPajVwV.iterator();
        iter.hasNext()&&iter1.hasNext(); ) {
    CleaningProcData cpD = (CleaningProcData) iter.next();
    ProdApplJoinView pajVw = (ProdApplJoinView) iter1.next();

     ItemData iD = pajVw.getItem();
     ProdApplData paD = pajVw.getProdAppl();
     int prodApplId = paD.getProdApplId();
     ProductUomPackData pupD = pajVw.getProductUomPack();
     String dilutionRateS = Utility.strNN(pajVw.getDilutionRate());
     String usagRateS = Utility.strNN(pajVw.getUsageRate());
     String numeratorS = Utility.strNN(pajVw.getUnitCdNumerator());
     String denominatorS = Utility.strNN(pajVw.getUnitCdDenominator());
     String denominator1S = Utility.strNN(pajVw.getUnitCdDenominator1());
%>
  <tr>
  <% if(Utility.isSet(cpD.getEstimatorPageCd())) {%>
  <td><%=cpD.getEstimatorPageCd()%></td>
  <td><%=cpD.getShortDesc()%></td>
  <% } else { %>
  <td colspan='2'>&nbsp;</td>
  <% } %>
  <td><%=iD.getSkuNum()%></td>
  <td><%=iD.getShortDesc()%></td>
  <td><%=pupD.getUnitSize()%>&nbsp;<%=pupD.getUnitCd()%></td>
  <td><%=pupD.getPackQty()%></td>
  <td><%if(dilutionRateS.length()>0) {%>1:<%=dilutionRateS%><%}else{%>&nbsp;<%}%></td>
  <td><%if(usagRateS.length()>0) {%><%=usagRateS%><%=numeratorS%>/<%=denominatorS%>/<%=denominator1S%><%}else{%>&nbsp;<%}%></td>
  <%
   if(theForm.getCleanigProcSelected()>0) { 

   String hrefAdd = "estimatorMgr.do?action=addProcProduct&prodApplId="+prodApplId;
  %>
  <td> <a href='<%=hrefAdd%>'>Add</a></td>
  <%} else {%>
  <td>&nbsp;</td>
  <% } %>
  </tr>
 
<% }} %>
  
<html:form action="/adminportal/estimatorMgr.do" >
  <table border="0" width="750" class="results">
  <tr  bgcolor="#cccccc"><td colspan='3'><b>Select Activity</b></td>
  <tr>
  <td valign='top'>
<%
  CleaningProcDataVector cleanProcDV = theForm.getCleaningProcedures();
  int colSize = (int)(cleanProcDV.size()+.5)/3;
  String prevEstimatorPageCd = "@@@";
  int ind = -1;
  int colInd = -1;
  for(Iterator iter=cleanProcDV.iterator(); iter.hasNext(); ) {
    ind++;
    colInd++;
    CleaningProcData cpD = (CleaningProcData) iter.next();
    String estimatorPageCd = Utility.strNN(cpD.getEstimatorPageCd());
    if(colInd>colSize) {
      colInd = 0;
      if(ind>1) {
%>
    </td>
<% } %>
    <td valign='top'>
<% } %>
<%
    if(!estimatorPageCd.equals(prevEstimatorPageCd)) {
      prevEstimatorPageCd = estimatorPageCd;
      colInd++;
%>
  <b><%=estimatorPageCd%></b><br>
<% } %>
  <html:radio name='ESTIMATOR_MGR_FORM' property='cleanigProcSelected' 
  onclick='javascript: { document.forms[0].submit();}'
   value='<%=""+cpD.getCleaningProcId()%>'/><%=cpD.getShortDesc()%><br>
<% } %>
 </td>
 </tr>
</html:form>
  </table>
<!--- /////////////////////////// -->
<%
     ProdApplJoinViewVector pajVwV = theForm.getProcedureProducts();
   if(pajVwV!=null) {
%>

<table width='100%' border='2'>
<html:form action="/adminportal/estimatorMgr.do" >
<tr bgcolor='#cccccc'>
<td align='center'><b>Sku #</b></td>
<td align='center'><b>Product</b></td>
<td align='center'><b>Size</b></td>
<td align='center'><b>Pack</b></td>
<td align='center'><b>Dilution</b></td>
<td align='center'><b>Usage<br>Rate</b></td>
<td align='center'><b>&nbsp;</b></td>
</tr>


<%
 int prodQty = pajVwV.size();
 if(prodQty==0) { %>
<tr>
<td colspan = '6'><<<<< No Products for This Procedure >>>>></td>
</tr>
<% } else {
   int ind2 = -1;
   for(Iterator iter2 = pajVwV.iterator(); iter2.hasNext();) {
     ind2++;
     ProdApplJoinView pajVw = (ProdApplJoinView) iter2.next();
     ItemData iD = pajVw.getItem();
     ProdApplData paD = pajVw.getProdAppl();
     int prodApplId = paD.getProdApplId();
     ProductUomPackData pupD = pajVw.getProductUomPack();
     String dilutionRateS = pajVw.getDilutionRate();
     String usagRateS = pajVw.getUsageRate();
     String numeratorS = pajVw.getUnitCdNumerator();
     String denominatorS = pajVw.getUnitCdDenominator();
     String denominator1S = pajVw.getUnitCdDenominator1();
     int itemInd = ind2;
     String dilutionRateInp = "procProdDilution["+itemInd+"]";     
     String usagRateInp = "procProdRate["+itemInd+"]";     
     String numeratorInp = "procProdRateNumerator["+itemInd+"]";     
     String denominatorInp = "procProdRateDenominator["+itemInd+"]";     
     String denominator1Inp = "procProdRateDenominator1["+itemInd+"]";     
     String itemIndS = ""+itemInd;
%>
<tr>
<td><%=iD.getSkuNum()%></td>
<td><%=iD.getShortDesc()%></td>
<td><%=pupD.getUnitSize()%>&nbsp;<%=pupD.getUnitCd()%></td>
<td><%=pupD.getPackQty()%></td>
<td>1:<html:text name='ESTIMATOR_MGR_FORM' property='<%=dilutionRateInp%>' size='4'/></td>
<td><html:text name='ESTIMATOR_MGR_FORM' property='<%=usagRateInp%>' 
   size='4'/><html:select name='ESTIMATOR_MGR_FORM' property='<%=numeratorInp%>'  styleClass='smalltext'>
  <html:option value='<%=""%>'>&nbsp;</html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.SQ_F%>'><%=RefCodeNames.UNIT_CD.SQ_F%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.OZ%>'><%=RefCodeNames.UNIT_CD.OZ%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.GAL%>'><%=RefCodeNames.UNIT_CD.GAL%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.FEET%>'><%=RefCodeNames.UNIT_CD.FEET%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.INCH%>'><%=RefCodeNames.UNIT_CD.INCH%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.UNIT%>'><%=RefCodeNames.UNIT_CD.UNIT%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.PACK%>'><%=RefCodeNames.UNIT_CD.PACK%></html:option>
  </html:select><html:select 
   name='ESTIMATOR_MGR_FORM' property='<%=denominatorInp%>'  styleClass='smalltext'>
  <html:option value='<%=""%>'>&nbsp;</html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.PROCEDURE%>'><%=RefCodeNames.UNIT_CD.PROCEDURE%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.FACILITY%>'><%=RefCodeNames.UNIT_CD.FACILITY%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.GAL%>'><%=RefCodeNames.UNIT_CD.GAL%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.TOILET%>'><%=RefCodeNames.UNIT_CD.TOILET%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.RESTROOM%>'><%=RefCodeNames.UNIT_CD.RESTROOM%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.SHOWER%>'><%=RefCodeNames.UNIT_CD.SHOWER%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.USE%>'><%=RefCodeNames.UNIT_CD.USE%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.YEAR%>'><%=RefCodeNames.UNIT_CD.YEAR%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.WEEK%>'><%=RefCodeNames.UNIT_CD.WEEK%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.DAY%>'><%=RefCodeNames.UNIT_CD.DAY%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.WORKING_DAY%>'><%=RefCodeNames.UNIT_CD.WORKING_DAY%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.LB%>'><%=RefCodeNames.UNIT_CD.LB%></html:option>
  </html:select>&nbsp;
  <html:select 
   name='ESTIMATOR_MGR_FORM' property='<%=denominator1Inp%>'  styleClass='smalltext'>
  <html:option value='<%=""%>'>&nbsp;</html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.PROCEDURE%>'><%=RefCodeNames.UNIT_CD.PROCEDURE%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.FACILITY%>'><%=RefCodeNames.UNIT_CD.FACILITY%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.GAL%>'><%=RefCodeNames.UNIT_CD.GAL%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.TOILET%>'><%=RefCodeNames.UNIT_CD.TOILET%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.RESTROOM%>'><%=RefCodeNames.UNIT_CD.RESTROOM%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.SHOWER%>'><%=RefCodeNames.UNIT_CD.SHOWER%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.USE%>'><%=RefCodeNames.UNIT_CD.USE%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.YEAR%>'><%=RefCodeNames.UNIT_CD.YEAR%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.WEEK%>'><%=RefCodeNames.UNIT_CD.WEEK%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.DAY%>'><%=RefCodeNames.UNIT_CD.DAY%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.WORKING_DAY%>'><%=RefCodeNames.UNIT_CD.WORKING_DAY%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.LB%>'><%=RefCodeNames.UNIT_CD.LB%></html:option>
  </html:select>&nbsp;
</td>

<td><html:multibox name='ESTIMATOR_MGR_FORM' property='procProdSelector' value='<%=itemIndS%>'/></td>
</tr>
<% } %>
<% } %>
 <tr>
  <td colspan='8'>
  <html:submit property="action" value="Save"/>
  <html:submit property="action" value="Delete Selected"/>
  </td>
</tr>
</html:form>
</table>
<% } %>

<!-- /////////////////////////// -->
</body>

</html:html>


