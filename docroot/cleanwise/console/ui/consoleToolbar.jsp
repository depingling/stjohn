
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% boolean clwSwitch=ClwCustomizer.getClwSwitch(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<script src="../externals/lib.js" language="javascript"></script>

<%
  /* Get the page being accessed. */
  String pg = request.getRequestURI();

  /* Get the user type */

  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
  String userType = appUser.getUser().getUserTypeCd();
  boolean adminFl = appUser.isaAdmin();

%>


<%--render the image and the menuing system--%>
<table border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>" style="border-collapse: collapse;">
	<tr valign="top">
		<td align="left" valign="middle">
		  <img src='<app:custom  pageElement="pages.store.logo1.image" 
                 addImagePath="true" encodeForHTML="true"/>' border="0">
		</td>
		<%--The navigation bar--%>
		<td align="right" colspan="4">
		    <jsp:include flush='true' page="/general/navMenu.jsp"/>
			<br>
			<% java.util.Date currd = new java.util.Date(); %>
			<%= currd.toString() %>
			<br>
			Server: <%=java.net.InetAddress.getLocalHost()%>
		</td>
		<%--END The navigation bar--%>
	</tr>
</table>
<br>
<table border="0" cellpadding="3" cellspacing="0" width=769>

<tr align=center>

<%
String tabClass = ""; String linkClass = "";

if ( pg.indexOf("order") == -1 || pg.indexOf("pendingorder") != -1) {
        tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}

%>
  <td class="<%=tabClass%>" >
    <a class="<%=linkClass%>" href="orderOp.do">Order Tracking</a>
  </td>

<%
if ( pg.indexOf("opShop") == -1 ) {
        tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}

%>
<td class="<%=tabClass%>" >
    <a class="<%=linkClass%>" href="opShop.do">Shop</a>
</td>
<% if(clwSwitch) {  %>
<%
if ( pg.indexOf("invoice") == -1 ) { tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}
%>
    <%
       if(adminFl){

    %>
  <td class="<%=tabClass%>" >
      <a class="<%=linkClass%>" href="invoiceOp.do">Invoices</a>
  </td>
    <% }  %>
<% } %>
<%
if ( pg.indexOf("knowledge") == -1 ) { tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}

%>

  <td class="<%=tabClass%>" >
      <a class="<%=linkClass%>" href="knowledgeOp.do">KB</a>
    </td>


<% if(clwSwitch) {  %>
<%
if ( pg.indexOf("purchaseOrder") == -1 ) { tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}

%>
  <td class="<%=tabClass%>">
      <a class="<%=linkClass%>" href="purchaseOrderOp.do">POs</a>
    </td>
<% } %>

<% if(clwSwitch) {  %>
<%
if ( pg.indexOf("returns") == -1 ) { tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}

%>
  <td class="<%=tabClass%>">
      <a class="<%=linkClass%>" href="returnsOp.do">RGA</a>
    </td>
<% } %>

<% if(clwSwitch) {  %>
<%
if ( pg.indexOf("erpIntegration") == -1 ) { tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}
%>
    <%
       if(adminFl){

    %>
  <td class="<%=tabClass%>">
      <a class="<%=linkClass%>" href="erpIntegration.do">Erp Integration</a>
  </td>
    <% }  %>
<% }  %>
<% if(clwSwitch) {  %>
<%
if ( pg.indexOf("crcsite") == -1 ) { tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}
%>
    <%
       if(!adminFl){
    %>
  <td class="<%=tabClass%>">
      <a class="<%=linkClass%>" href="crcsite.do">Site</a>
  </td>
    <% }  %>
<% }  %>

<!-- distributor info -->
<% if(clwSwitch) {  %>
<%
if ( pg.indexOf("crcdist") == -1 ) { tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}
%>
    <%
       if(!adminFl){
    %>
  <td class="<%=tabClass%>">
      <a class="<%=linkClass%>" href="crcdist.do">Distributor</a>
  </td>
    <% }  %>
<% }  %>

<% if(clwSwitch) {  %>
<%
if ( pg.indexOf("crcuser") == -1 ) { tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}
%>
    <%
       if(!adminFl){
    %>
  <td class="<%=tabClass%>">
      <a class="<%=linkClass%>" href="crcusers.do">User</a>
  </td>
  <% }  %>
<% }  %>


  </tr>

</table>

