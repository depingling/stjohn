<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
	String feedField =  (String)request.getParameter("feedField");
	if(null == feedField) {
		feedField =  (String)request.getParameter("parm");
		if(null == feedField) {
			feedField = new String("");
		}
	}
	String feedDesc =  (String)request.getParameter("feedDesc");
	if(null == feedDesc) {
		feedDesc = new String("");
	}
%>

<script language="JavaScript1.2">
<!--

function passIdAndName(id, name) {
  var feedBackFieldName = document.STORE_SEARCH_FORM.feedField.value;
  var feedDesc = document.STORE_SEARCH_FORM.feedDesc.value;

  if(feedBackFieldName && ""!= feedBackFieldName) {
    window.opener.document.forms[0].elements[feedBackFieldName].value = id;
  }
  if(feedDesc && ""!= feedDesc) {
    window.opener.document.forms[0].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
  }
  self.close();
}

//-->
</script>


<div class="text">

<html:form name="STORE_SEARCH_FORM" 
action="/adminportal/storelocate.do" focus="searchField"
type="com.cleanwise.view.forms.StoreMgrSearchForm">
<input type="hidden" name="feedField" value="<%=feedField%>">
<input type="hidden" name="feedDesc" value="<%=feedDesc%>">


<table width="769" border="0"  class="mainbody">
  <html:form name="STORE_SEARCH_FORM" action="adminportal/storemgr.do"
    scope="session" type="com.cleanwise.view.forms.StoreMgrSearchForm">
  <tr> <td><b>Find Stores:</b></td>
       <td colspan="3">
			<html:text name="STORE_SEARCH_FORM" property="searchField"/>
       </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
         <html:radio name="STORE_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="STORE_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="STORE_SEARCH_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
     </html:form>
    </td>
  </tr>
</table>

</div>

<div>

<logic:present name="Store.found.vector">
<bean:size id="rescount"  name="Store.found.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">


<table width="769" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="storelocate.do?action=sort&sortField=id&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Store Id</td>
<td><a class="tableheader" href="storelocate.do?action=sort&sortField=name&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Name </td>
<td><a class="tableheader" href="storelocate.do?action=sort&sortField=city&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">City</td>
<td><a class="tableheader" href="storelocate.do?action=sort&sortField=state&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">State/Province</td>
<td><a class="tableheader" href="storelocate.do?action=sort&sortField=type&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Type</td>
</tr>

<logic:iterate id="arrele" name="Store.found.vector">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
    <bean:define id="key"  name="arrele" property="busEntity.busEntityId"/>
    <bean:define id="name" name="arrele" property="busEntity.shortDesc" type="String"/>

    <% String onClick = new String("return passIdAndName('"+key+"','"+ java.net.URLEncoder.encode(name) +"');");%>
    <a href="javascript:void(0);" onclick="<%=onClick%>">
    <bean:write name="arrele" property="busEntity.shortDesc"/>
    </a>
</td>
<td><bean:write name="arrele" property="primaryAddress.city"/></td>
<td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
<td><bean:write name="arrele" property="storeType.value"/></td>
</tr>

</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>

</html:form>

</div>



