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
%>

<script language="JavaScript1.2">
<!--

function passIdAndName(id, name) {
  var feedBackFieldName = document.FH_SEARCH_FORM.feedField.value;
  var feedDesc = document.FH_SEARCH_FORM.feedDesc.value;

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

<html:form action="/adminportal/freighthandlerlocate.do" focus="searchField">
<input type="hidden" name="feedField" value="<%=feedField%>">
<input type="hidden" name="feedDesc" value="<%=feedDesc%>">


<table width="769" border="0"  class="mainbody">
  <tr> <td><b>Find Freight Handlers:</b></td>
       <td colspan="3">
                        <html:text name="FH_SEARCH_FORM" property="searchField"/>
       </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
         <html:radio name="FH_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="FH_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="FH_SEARCH_FORM" property="searchType" value="nameContains" />
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
    </td>
  </tr>
</table>

</div>

<div>

<logic:present name="freight_handler.vector">
<bean:size id="rescount"  name="freight_handler.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">


<table width="769" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="freighthandlerlocate.do?action=sort&sortField=id&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Id</td>
<td><a class="tableheader" href="freighthandlerlocate.do?action=sort&sortField=name&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Name</td>
<td><a class="tableheader" href="freighthandlerlocate.do?action=sort&sortField=address&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Address 1</td>
<td><a class="tableheader" href="freighthandlerlocate.do?action=sort&sortField=city&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">City</td>
<td><a class="tableheader" href="freighthandlerlocate.do?action=sort&sortField=state&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">State</td>
<td><a class="tableheader" href="freighthandlerlocate.do?action=sort&sortField=status&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Status</td>
<td><a class="tableheader" href="freighthandlerlocate.do?action=sort&sortField=ediRoutingCd&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">EDI Routing Code</td>
</tr>

<logic:iterate id="arrele" name="freight_handler.vector">
<tr>
<td><bean:write name="arrele" property="busEntityData.busEntityId"/></td>
<td>
    <bean:define id="key"  name="arrele" property="busEntityData.busEntityId"/>
    <bean:define id="name" name="arrele" property="busEntityData.shortDesc" type="String"/>

    <% String onClick = new String("return passIdAndName('"+key+"','"+ java.net.URLEncoder.encode(name) +"');");%>
    <a href="javascript:void(0);" onclick="<%=onClick%>">
    <bean:write name="arrele" property="busEntityData.shortDesc"/>
    </a>
</td>
<td><bean:write name="arrele" property="primaryAddress.address1"/></td>
<td><bean:write name="arrele" property="primaryAddress.city"/></td>
<td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
<td><bean:write name="arrele" property="busEntityData.busEntityStatusCd"/></td>
<td><bean:write name="arrele" property="ediRoutingCd"/></td>
</tr>

<tr>

</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>

</html:form>

</div>



