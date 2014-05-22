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
		feedField = new String("");
	}
	String feedDesc =  (String)request.getParameter("feedDesc");
	if(null == feedDesc) {
		feedDesc = new String("");
	}

	String returnFormNum =  (String)request.getParameter("returnFormNum");
	if(null == returnFormNum) {
		returnFormNum = new String("");
	}

        String submitFl =  (String)request.getParameter("submitFl");
        if(null == submitFl) {
           submitFl = new String("false");
}
%>

<script language="JavaScript1.2">
<!--

function passIdAndName(id, name) {
  var feedBackFieldName = document.MANUF_SEARCH_FORM.feedField.value;
  var feedDesc = document.MANUF_SEARCH_FORM.feedDesc.value;
  var returnFormNum = document.MANUF_SEARCH_FORM.returnFormNum.value;
  var submitFl = document.MANUF_SEARCH_FORM.submitFl.value;

  if(returnFormNum && ""!=returnFormNum && "undefined"!=returnFormNum) {
    if(feedBackFieldName && ""!= feedBackFieldName) {
      window.opener.document.forms[returnFormNum].elements[feedBackFieldName].value = id;
    }
    if(feedDesc && ""!= feedDesc) {
      window.opener.document.forms[returnFormNum].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
    }
  } else {
    if(feedBackFieldName && ""!= feedBackFieldName) {
      window.opener.document.forms[0].elements[feedBackFieldName].value = id;
    }
    if(feedDesc && ""!= feedDesc) {
      window.opener.document.forms[0].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
    }
  }
  if(submitFl && submitFl=="true") {
    window.opener.document.forms[0].submit();
  }

self.close();
}

//-->
</script>


<div class="text">

<html:form name="MANUF_SEARCH_FORM" 
action="/adminportal/manuflocate.do" focus="searchField"
type="com.cleanwise.view.forms.ManufMgrSearchForm">
<input type="hidden" name="feedField" value="<%=feedField%>">
<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
<input type="hidden" name="returnFormNum" value="<%=returnFormNum%>">
<input type="hidden" name="submitFl" value="<%=submitFl%>">


<table width="769" border="0"  class="mainbody">
  <html:form name="MANUF_SEARCH_FORM" action="adminportal/manufmgr.do"
    scope="session" type="com.cleanwise.view.forms.ManufMgrSearchForm">
  <tr> <td><b>Find Manufacturers:</b></td>
       <td colspan="3">
			<html:text name="MANUF_SEARCH_FORM" property="searchField"/>
       </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
         <html:radio name="MANUF_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="MANUF_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="MANUF_SEARCH_FORM" property="searchType" value="nameContains" />
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

<logic:present name="Manuf.found.vector">
<bean:size id="rescount"  name="Manuf.found.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">


<table width="769" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="manuflocate.do?action=sort&sortField=id&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&returnFormNum=<%=returnFormNum%>&submitFl=<%=submitFl%>">Manufacturer&nbsp;Id</td><td><a class="tableheader" href="manuflocate.do?action=sort&sortField=name&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&returnFormNum=<%=returnFormNum%>&submitFl=<%=submitFl%>">Name</td><td><a class="tableheader" href="manuflocate.do?action=sort&sortField=status&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&returnFormNum=<%=returnFormNum%>&submitFl=<%=submitFl%>">Status</td>
</tr>

<logic:iterate id="arrele" name="Manuf.found.vector">
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
<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</tr>

</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>

</html:form>

</div>



