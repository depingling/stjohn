<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<table ID=1140 width="<%=Constants.TABLEWIDTH%>" class="mainbody">
<html:form styleId="1141" action="storeportal/storePoErpLookUp.do">
	<tr><td>&nbsp;</td></tr>
	<tr>
        <td><b>PO Number:</b>
	<html:text name="STORE_PO_FORM" property="outboundPoNum" />
	(At least 3 characters, please)
	</td>
	</tr>
	<tr>
	<td>
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
<html:hidden property="action" value="Search"/>
</html:form>
</table>

<% 
  RealPurchaseOrderNumViewVector pOrderVwV = (RealPurchaseOrderNumViewVector) session.getAttribute("PurchaseOrder.found.vector");
  if(pOrderVwV!=null){    
%>
<table ID=1142 width="<%=Constants.TABLEWIDTH%>" class="results">
	<tr align=left>
		<td>&nbsp;</td>
		<td><b>Outbound&nbsp;PO&nbsp;Num</b></td>
		<td><b>PO&nbsp;Date</b></td>
		<td><b>Distributor</b></td>
	</tr>
<logic:iterate name="PurchaseOrder.found.vector" id="aPO">

<tr class="results">
    <td>&nbsp;</td>
    <td><bean:write name="aPO" property="PONumValue"/> </td>
<logic:empty name="aPO" property="purchaseOrder">
    <td>&nbsp;</td><td>&nbsp;</td>
</logic:empty>
<logic:notEmpty name="aPO" property="purchaseOrder">
    <td><bean:write name="aPO" property="purchaseOrder.poDate"/></td>
    <td><bean:write name="aPO" property="distributorName"/></td>
</logic:notEmpty>
</tr>
</logic:iterate>
</table>
<% } %>


<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms[0]['outboundPoNum'];
  if('undefined' != typeof focusControl) {
     focusControl.focus();
  }
  
function kH(e) {
  var keyCode = window.event.keyCode;
  if(keyCode==13) {
    var actionButton = document.forms[0]['action'];
    if('undefined' != typeof actionButton) {
      if('Search' == actionButton.value){
          actionButton.select();
          actionButton.click();
          return;
      }
      var len = actionButton.length;
      for(ii=0; ii<len; ii++) {
        if('Search' == actionButton[ii].value) {
          actionButton[ii].select();
          actionButton[ii].click();
          break;
        }      
      }
    }
  }
}
document.onkeypress = kH;
  

  
  // -->
</script>