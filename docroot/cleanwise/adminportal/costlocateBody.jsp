<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="COST_SEARCH_FORM"
  type="com.cleanwise.view.forms.CostLocateMgrForm"/>

<%
	String feedField =  (String)request.getParameter("feedField");
	if(null == feedField) {
		feedField = new String("");
	}
	String feedDesc =  (String)request.getParameter("feedDesc");
	if(null == feedDesc) {
		feedDesc = new String("");
	}
	String locateFilter =  (String)request.getParameter("locateFilter");
	if(null == locateFilter) {
		locateFilter = new String("");
	}
%>

<script language="JavaScript1.2">
<!--

function passResult(pValue) {
  var feedBackFieldName = '<%=theForm.getFeedBackFieldName()%>';
  if(feedBackFieldName && ""!= feedBackFieldName) {
    window.opener.document.forms[0].elements['<%=theForm.getFeedBackFieldName()%>'].value = pValue;
  }
  self.close();
}

//-->
</script>


<div class="text">

<html:form name="COST_SEARCH_FORM" action="/adminportal/costlocate.do" type="com.cleanwise.view.forms.CostLocateMgrForm">
<input type="hidden" name="feedField" value="<%=feedField%>">
<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
<input type="hidden" name="locateFilter" value="<%=locateFilter%>">


<table width="769" border="0"  class="mainbody">
<tr><td><b>SKU: <%=theForm.getSkuNum()%></b></td></tr>
<tr><td><b>Assigned Distributor: <%=theForm.getDistDesc()%></b></td></tr>
<tr>
<td align="center"><html:submit property="action" value="Assigned Distributor"/>
<html:submit property="action" value="All Distributors"/></td>
</tr>
<div>
<bean:size id="rescount"  name="COST_SEARCH_FORM" property="itemCostVector"/>
<table width="769" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="costlocate.do?action=sort&sortField=distId">Dist.Id</td>
<td><a class="tableheader" href="costlocate.do?action=sort&sortField=DistDesc">Dist.Name</td>
<td><a class="tableheader" href="costlocate.do?action=sort&sortField=ItemCost">Dist.Cost</td>
<td class="tableheader">Contracts</td>
</tr>

<logic:iterate id="arrele" name="COST_SEARCH_FORM" property="itemCostVector" 
    scope="session" type="com.cleanwise.service.api.value.ItemContractCostView">
<tr>
<td><bean:write name="arrele" property="distId"/></td>
<td><bean:write name="arrele" property="distDesc"/></td>
<td>
  <bean:define id="cost"  name="arrele" property="itemCost"/>
  <% String onClick = "return passResult('"+cost+"')"; %>
  <a href="javascript:void(0);" onclick="<%=onClick%>">
      <i18n:formatCurrency value="<%=cost%>" locale="<%=Locale.US%>"/>
  </a>
</td>
<td><bean:write name="arrele" property="contractDesc"/></td>
</tr>
</logic:iterate>
</table>

</html:form>
</div>


