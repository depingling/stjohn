<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Store Administrator Home: Select an Order Guide</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="results">

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
  var feedBackFieldName = document.STORE_SITE_ORDER_GUIDE_FORM.feedField.value;
  var feedDesc = document.STORE_SITE_ORDER_GUIDE_FORM.feedDesc.value;

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

<html:form styleId="439" name="STORE_SITE_ORDER_GUIDE_FORM"
action="/storeportal/orderguidelocate.do" focus="searchField"
type="com.cleanwise.view.forms.StoreSiteOrderGuideDetForm">
<input type="hidden" name="feedField" value="<%=feedField%>">
<input type="hidden" name="feedDesc" value="<%=feedDesc%>">


<table ID=440 width="769" border="0" >
  <html:form styleId="441" name="STORE_SITE_ORDER_GUIDE_FORM" action="adminportal/orderguidesmgr.do"
    scope="session" type="com.cleanwise.view.forms.OrderGuidesMgrDetailForm">
  <tr> <td><b>Find Order Guides:</b></td>
       <td colspan="3">
      <html:text name="STORE_SITE_ORDER_GUIDE_FORM" property="searchField"/>
       </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
         <html:radio name="STORE_SITE_ORDER_GUIDE_FORM" property="searchType" value="id" />
         ID
         <html:radio name="STORE_SITE_ORDER_GUIDE_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="STORE_SITE_ORDER_GUIDE_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
  <html:submit property="action">
    <app:storeMessage  key="global.action.label.search"/>
  </html:submit>
     </html:form>
    </td>
  </tr>
</table>

</div>



<logic:present name="StoreSiteOrderGuides.found.vector">

<bean:size id="rescount"  name="StoreSiteOrderGuides.found.vector"/>
Count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">

<table ID=442 width="750" class="results">
<tr>
<td><a ID=443 class="tableheader" href="orderguidelocate.do?action=sort&sortField=id&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Order Guide Id</td>
<td><a ID=444 class="tableheader" href="orderguidelocate.do?action=sort&sortField=name&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Name</td>
<td><a ID=445 class="tableheader" href="orderguidelocate.do?action=sort&sortField=catalog&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Catalog Name</td>
<td><a ID=446 class="tableheader" href="orderguidelocate.do?action=sort&sortField=status&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Status</td>
</tr>
<logic:iterate id="ogele" name="StoreSiteOrderGuides.found.vector">

<tr>
<td><bean:write name="ogele" property="orderGuideId"/></td>
<td>
    <bean:define id="key"  name="ogele" property="orderGuideId"/>
    <bean:define id="name" name="ogele" property="orderGuideName" type="String"/>

    <% String onClick = new String("return passIdAndName('"+key+"','"+ java.net.URLEncoder.encode(name) +"');");%>
    <a ID=447 href="javascript:void(0);" onclick="<%=onClick%>">
    <bean:write name="ogele" property="orderGuideName"/>
    </a>
</td>
<td>
    <bean:write name="ogele" property="catalogName"/>
</td>
<td>
    <bean:write name="ogele" property="status"/>
</td>

</tr>

</logic:iterate>

</table>

</logic:greaterThan>
</logic:present>

</html:form>


</body>

</html:html>


