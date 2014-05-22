
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
String storeDir=ClwCustomizer.getStoreDir();
boolean clwSwitch = ClwCustomizer.getClwSwitch();
%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<script src="../externals/lib.js" language="javascript"></script>

<%
  /* Get the page being accessed. */
  String pg = request.getRequestURI();
%>

<%--render the image and the menuing system--%>
<table border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>" style="border-collapse: collapse;">
	<tr valign="top">
		<td align="left" valign="middle">
        <% String storeLogo = (String)session.getAttribute("pages.logo1.image");
          if (Utility.isSet(storeLogo)) { %>
		  <img src='<app:custom  pageElement="pages.logo1.image" addImagePath="true" encodeForHTML="true"/>' border="0">
		<% } %>
		</td>
		<%--The navigation bar--%>
		<td align="right" colspan="4">
		    <jsp:include flush='true' page="/general/navMenu.jsp"/>
			<br>
			<% java.util.Date currd = new java.util.Date(); %>
			<%= currd.toString() %>
		</td>
		<%--END The navigation bar--%>
	</tr>
</table>
<br>
<table cellpadding="0" cellspacing="0" width="769">
<tr>
<td class="atoff">
<table cellpadding="3" cellspacing="0" align="left">
<tr>
<%--Start Toolbar Links--%>
<%
String tabClass = ""; String linkClass = "";
if ( pg.indexOf("purchaseOrder") == -1 ) { tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}
%>

  <td class="<%=tabClass%>">
    <a class="<%=linkClass%>" href="purchaseOrderDist.do">Purchase Orders</a>
  </td>

<%
if ( pg.indexOf("help") == -1 ) { tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}
%>
  <td class="<%=tabClass%>" >
    <a class="<%=linkClass%>" href="help.do">Help</a>
  </td>

<%
if ( pg.indexOf("distributorProfile") == -1 ) { tabClass = "atoff"; linkClass = "tbar2";
} else { tabClass = "aton"; linkClass = "tbar";}
%>
  <td class="<%=tabClass%>" >
    <a class="<%=linkClass%>" href="distributorProfile.do?action=customer_profile">Change Password</a>
  </td>
<%--End Toolbar Links--%>
</tr>
</table>
  </td>
  </tr>
<tr>
<td class="aton" colspan=10><img src="../<%=ip%>images/cw_spacer.gif" HEIGHT="2"></td>
</tr>
</table>