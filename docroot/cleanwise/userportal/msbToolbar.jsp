<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<logic:match name="toolBarTab" value="default">
<tr>
<td width="<%=Constants.TABLEWIDTH%>" class="top3dk">&nbsp;&nbsp;
<span class="msbon">&nbsp;<a class="tbar2" href="msbhome.do">Order Guides</a>&nbsp;</span> 
<span class="msbdef">&nbsp;<a class="tbar" href="msbsites.do?action=list_sites">Sites</a>&nbsp;</span> 
<span class="msbdef">&nbsp;<a class="tbar" href="msbreports.do">Reports</a>&nbsp;</span></td>
</tr>
<tr>
<td class="msbon"  width="<%=Constants.TABLEWIDTH%>"><img src="../<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
<tr>
<td class="msbon"  width="<%=Constants.TABLEWIDTH%>" >
<logic:equal name="toolBarTab" value="default">
&nbsp;&nbsp;&nbsp;&nbsp;Order Guides available for shopping:
</logic:equal>
<logic:equal name="toolBarTab" value="default_shop_og">
&nbsp;&nbsp;&nbsp;&nbsp;Shop using Order Guide:
</logic:equal>
</td>
</tr>

</logic:match> <% // Match on the default tab. %>

<logic:equal name="toolBarTab" value="sites">
<tr>
<td width="<%=Constants.TABLEWIDTH%>" class="top3dk">&nbsp;&nbsp;
<span class="msbdef">&nbsp;<a class="tbar" href="msbhome.do">Order Guides</a>&nbsp;</span> 
<span class="msbon">&nbsp;<a class="tbar2" href="msbsites.do?action=list_sites">Sites</a>&nbsp;</span> 
<span class="msbdef">&nbsp;<a class="tbar" href="msbreports.do">Reports</a>&nbsp;</span></td>
</tr>
<tr>
<td class="msbon"  width="<%=Constants.TABLEWIDTH%>"><img src="../<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
<tr>
<td class="msbon"  width="<%=Constants.TABLEWIDTH%>" >
&nbsp;&nbsp;&nbsp;&nbsp;Sites available for shopping:
</td>
</tr>
</logic:equal>

<logic:equal name="toolBarTab" value="reports">
<tr>
<td class="top3dk" width="<%=Constants.TABLEWIDTH%>" >&nbsp;&nbsp;
<span class="msbdef">&nbsp;<a class="tbar" href="msbhome.do">Order Guides</a>&nbsp;</span> 
<span class="msbdef">&nbsp;<a class="tbar" href="msbsites.do?action=list_sites">Sites</a>&nbsp;</span> 
<span class="msbon">&nbsp;<a class="tbar2" href="msbreports.do">Reports</a>&nbsp;</span></td>
</tr>

<tr>
<td class="msbon" ><img src="../<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>

<tr>
<td class="msbon">
&nbsp;&nbsp;&nbsp;&nbsp;Reports available:
</td>
</tr>

</logic:equal>

<tr>
<td><img src="/<%=storeDir%>/en/images/cw_shopunderlogo.gif" WIDTH="<%=Constants.TABLEWIDTH%>" HEIGHT="3"></td>
</tr>

