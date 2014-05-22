<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String" 
  name="pages.store.images"/>

<div class="text"><font color=red><html:errors/></font></div>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
	<tr><!-- the following row locks the content area into the correct minimum sizes for Netscape etc. -->
		<td class="changeshippingdk" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
		<td width="86"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="86"></td>
		<td class="customerltbkground"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" ></td>
		<td ><img src="<%=IMGPath%>/cw_spacer.gif" height="1" ></td>
		<td class="changeshippingdk" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
	</tr>

<tr>
<!-- the following row is the content area -->

<td class="changeshippingdk" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>

<td width="86" valign="top">&nbsp;<!-- vary the number of columns, adjust colspan of next row to match and adjusts widths of remaining columns to compensate --></td>
<td class="customerltbkground" valign="top">

<table border="0" cellspacing="0" cellpadding="0" >
<tr>
<td><img src="<%=IMGPath%>/spacer.gif" height="20" width="1"></td>
</tr>
<tr>
<td><img src='<app:store image="cw_mycwbar.gif"/>' height="5"></td>
</tr>
<tr><td class="text">
<app:custom pageElement="pages.main.text"/>
</td>
</tr>

</table>

</td>

<% /* image on the right hand side */ %>
<td width=203 align="center" valign="top">
<img src='<app:custom pageElement="pages.logo2.image" 
  addImagePath="true" encodeForHTML="true"/>'/>

<% /* customer tips on the right hand side */ %>
<%--<div class="customer_home_tiptext">
<app:custom pageElement="pages.tips.text"/>
</div>--%>
</td>

<td class="changeshippingdk" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>

</tr>
</table>
</td>
</tr>
<tr>
<td>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>

</div>



