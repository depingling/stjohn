<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">

<table width="769" cellspacing="0" border="0" class="mainbody">
  <html:form name="ORDER_GUIDES_DETAIL_FORM" action="adminportal/orderguidesmgr.do"
    scope="session" type="com.cleanwise.view.forms.OrderGuidesMgrDetailForm">
  <tr> <td><b>Find Order Guides:</b></td>
       <td colspan="3">
			<html:text name="ORDER_GUIDES_DETAIL_FORM" property="searchField"/>
       </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio name="ORDER_GUIDES_DETAIL_FORM" property="searchType" value="id" />
         ID
         <html:radio name="ORDER_GUIDES_DETAIL_FORM" property="searchType" value="nameBegins" />
         Catalog Name(starts with)
         <html:radio name="ORDER_GUIDES_DETAIL_FORM" property="searchType" value="nameContains" />
         Catalog Name(contains)
         </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.create"/>
	</html:submit>
     </html:form>
    </td>
  </tr>
</table>

<logic:present name="OrderGuides.found.vector">
<bean:size id="ogres"  name="OrderGuides.found.vector"/>
Search result count:  <bean:write name="ogres" />


<table width="769" cellspacing="0" border="0" class="results">
<tr>
<td><a class="tableheader" href="orderguidesmgr.do?action=sort&sortField=id">Order Guide Id</td>
<td><a class="tableheader" href="orderguidesmgr.do?action=sort&sortField=name">Name</td>
<td><a class="tableheader" href="orderguidesmgr.do?action=sort&sortField=catalog">Catalog Name</td>
<td><a class="tableheader" href="orderguidesmgr.do?action=sort&sortField=status">Catalog Status</td>
<td><a class="tableheader" >Order Guide Type</td>
</tr>
<logic:iterate id="ogele" name="OrderGuides.found.vector">

<tr>
<td><bean:write name="ogele" property="orderGuideId"/></td>
<td>
<bean:define id="ogid" name="ogele" property="orderGuideId"/>
<a href="orderguidesmgr.do?action=detail&searchType=id&searchField=<%=ogid%>">
<bean:write name="ogele" property="orderGuideName"/>
</a>
</td>
<td><bean:write name="ogele" property="catalogName"/></td>
<td><bean:write name="ogele" property="status"/></td>
<td><bean:write name="ogele" property="orderGuideTypeCd"/></td>
</tr>

</logic:iterate>
</table>

</logic:present>

</div>





