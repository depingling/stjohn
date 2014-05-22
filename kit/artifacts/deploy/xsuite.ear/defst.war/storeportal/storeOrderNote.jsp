<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<style>
hr {
  border: 0;
  height: 1px;
  background-color: black;
}

pre {
  font-size: 8pt;
}

</style>

<%
 String action = (String)request.getParameter("action");
 if ( null == action || "".equals(action)) {
        action = new String("view");
 }

 String src = "order";

 String noteType = (String)request.getParameter("type");
 if ( null == noteType || "".equals(noteType)) {
        noteType = new String("order");
 }
 String reqItemId = (String)request.getParameter("itemid");
 int notesOrderItemId = 0;
 if ( null != reqItemId && reqItemId.length() > 0 ) {
        notesOrderItemId = Integer.parseInt(reqItemId);
 }

 String oidStr = (String)request.getParameter("orderid");
 int orderId = 0;
 if ( null != oidStr && oidStr.length() > 0 ) {
        orderId = Integer.parseInt(oidStr);
 }

 Date currd = new Date();
 String date =  currd.toString();

 String loginName = (String)request.getSession().getAttribute(Constants.USER_NAME);
%>
<script src="../externals/lib.js" language="javascript"></script>
<script language="JavaScript1.2">

<!--
function init() {
<% if ("close".equals(action)) {  %>
  self.close();
<% }  %>
  tmp = getAllElementByNameRegex(document,new Array(),"orderPropertyDetail")[0];
  if(tmp != null){
    tmp.focus();
  }
  return false;
}

//-->
</script>


<html:html>

<head>
<title>Operations Console Home: Order Note</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body onload="return init();">
  <div class="text">
  <center>    <font color=red>      <html:errors/>    </font>  </center>

  <html:form styleId="1102" name="STORE_ORDER_NOTE_FORM" action="/storeportal/storeOrderNote.do"
        type="com.cleanwise.view.forms.StoreOrderNoteForm">

<table ID=1103 style="boder: solid 1px black;">
<tr>
<% if ("order".equals(noteType)) {  %>
<td class="mediumheader">Order Notes</td>
<% } else if ("item".equals(noteType)) {  %>
<td class="mediumheader">Order Item Note</td>
<% } else if ("invoiceDistNote".equals(noteType)) {  %>
<td class="mediumheader">Distributor Invoice Note</td>
<% }  %>
<td width=200 align=right><%=action%> </td>

</tr>
</table>

<%
if ("add".equals(action)) {

/* Add a new note entry */

%>
  <table ID=1104>
        <tr>
          <td valign="top"><b>Notes:</b></td>
          <td>

<html:textarea name="STORE_ORDER_NOTE_FORM" property="orderPropertyDetail.value" rows="10" cols="80"/></td>
        </tr>
        <tr>
          <td><html:hidden name="STORE_ORDER_NOTE_FORM" property="orderId"/>
                  <html:hidden name="STORE_ORDER_NOTE_FORM" property="orderItemId"/>
                  <html:hidden name="STORE_ORDER_NOTE_FORM" property="orderNoteType"/>
                  <html:hidden name="STORE_ORDER_NOTE_FORM" property="noteId"/>
          <html:hidden name="STORE_ORDER_NOTE_FORM" property="orderPropertyDetail.addBy" value="<%= loginName %>"/></td>
        </tr>
        <tr>
          <td><html:submit property="action">
            <app:storeMessage  key="global.action.label.save"/>
          </html:submit>
      </td>
          <td><html:button onclick="javascript:window.close()" property="action">
            <app:storeMessage  key="global.action.label.cancel"/>
          </html:button>
      </td>
        </tr>
  </table>

<% /* end of adding a new note */
}
else
{



/* list the current notes */
%>

<%--view order notes --%>
 <logic:present name="STORE_ORDER_NOTE_FORM" property="orderPropertyList" >
 <%int index = 1; %>
  <logic:iterate id="itemele" indexId="i" name="STORE_ORDER_NOTE_FORM"
   property="orderPropertyList" scope="session"
   type="com.cleanwise.service.api.value.OrderPropertyData">

<%
if ( orderId > 0 &&
     orderId == itemele.getOrderId() ) {
%>
<br>
  <bean:define id="adddate" name="itemele" property="addDate"/>
  <table ID=1105 cellspacing=0>
<tr>
    <td width="200" class="mainbody"><b>Note:</b> <%=index%></td>
    <td width="500" rowspan="3" colspan="2" align="top" class="results">

<logic:present name="itemele" property="shortDesc" >
<logic:present name="itemele" property="value" >
<bean:define id="l_shortdesc" name="itemele" property="shortDesc" type="java.lang.String"/>
<logic:notEqual name="itemele" property="value" value="<%=l_shortdesc%>">
<span class="mainbody">
<b><bean:write name="itemele" property="shortDesc"/></b>
</span>
</logic:notEqual>
</logic:present>
</logic:present>

<bean:define id="notestr" name="itemele" property="value"
   type="java.lang.String"/>
<% if ( notestr.indexOf("This email") >= 0 ||
        notestr.indexOf("This Email") >= 0 ) { %> <pre> <% } %>
<br>
  <!--<bean:write name="itemele" property="value"/>-->
  <%
  String n = itemele.getValue();
  String convertedN = new String(n.getBytes(), "UTF-8");
  %>
  <%=convertedN%>
<% if ( notestr.indexOf("This email") >= 0 ||
        notestr.indexOf("This Email") >= 0 ) { %> </pre> <% } %>

</td>
</tr>
<tr>
  <td width="200" class="mainbody" ><b>Added By</b>: <bean:write name="itemele" property="addBy"/></td>
    </tr>
   <tr>
<td  width="200" class="mainbody"><b>Added Date:</b> <br>
<i18n:formatDate value="<%=adddate%>" pattern="MM/dd/yyyy hh:mm a"
  locale="<%=Locale.US%>"/></td>
    </tr>
  </table>

<%

index++;
}
%>

</logic:iterate>

</logic:present>
<%--end view order notes --%>

<%--view order item detail notes --%>

<br>
<table ID=1106>
<tr><td class="mediumheader"  style="boder: solid 1px black;">
Line Item Notes</td>        </tr>
</table>

<jsp:useBean id="STORE_ORDER_DETAIL_FORM" scope="session"
  class="com.cleanwise.view.forms.StoreOrderDetailForm" />

<logic:iterate id="itemele" indexId="i" name="STORE_ORDER_DETAIL_FORM" property="orderItemDescList" scope="session" type="com.cleanwise.service.api.value.OrderItemDescData">

<% if ( orderId == 0 ||
        orderId == itemele.getOrderItem().getOrderId() ) { %>

  <logic:present name="itemele" property="orderItemNotes">
<% if ( notesOrderItemId == 0 ||
        itemele.getOrderItem().getOrderItemId() == notesOrderItemId) { %>
  <logic:iterate id="detailele" indexId="j" name="itemele" property="orderItemNotes" type="com.cleanwise.service.api.value.OrderPropertyData">
        <bean:define id="note" name="detailele" property="value" type="java.lang.String"/>

<br>

        <table ID=1107 cellspacing=0>
            <tr>
            <td width="200" class="mainbody"><b>Note For Item:</b> <bean:write name="itemele" property="orderItem.itemSkuNum"/></td>
            <td rowspan="3" width=500 colspan="2" align="top" class="results">
            <%String convertedNote = new String(note.getBytes(), "UTF-8");%>
                <br>  <%=convertedNote%>

            </td>
            </tr>
            <tr>
               <td width="200" class="mainbody"><b>Added By</b>: <bean:write name="detailele" property="addBy"/></td>
            </tr>
            <tr>
               <bean:define id="adddate" name="detailele" property="addDate"/>
<td  width="200" class="mainbody"><b>Added Date:</b>
<br>
<i18n:formatDate value="<%=adddate%>" pattern="MM/dd/yyyy hh:mm a"
locale="<%=Locale.US%>"/></td>
            </tr>
        </table>

  </logic:iterate>
<% } %>

  </logic:present>
<% } %>
</logic:iterate>

<% 	}  // not adding a new note %>

  </html:form>
<!--
orderId=<%=orderId%>

notesOrderItemId=<%=notesOrderItemId%>
-->

<% if (!"add".equals(action)) {  %>
<center>
<button onclick="self.close()">Close this window </button>
</center>
<% } %>

</body>
</html:html>
