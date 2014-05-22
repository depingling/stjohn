<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String" 
  name="pages.store.images"/>


<div class="text"><font color=red><html:errors/></font></div>
<table align="center" border="0" cellpadding="0" cellspacing="0" 
  width="<%=Constants.TABLEWIDTH%>">

<tr>

<td class="changeshippingdk" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td width="86"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="86"></td>
<td width="478" class="customerltbkground"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="478"></td>
<td width="203"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="203"></td>
<td class="changeshippingdk" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
<tr><!-- the following row is the content area -->
<td class="changeshippingdk" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td width="86" valign="top">&nbsp;<!-- vary the number of columns, adjust colspan of next row to match and adjusts widths of remaining columns to compensate --></td>
<td width="478" class="customerltbkground" valign="top">
<!-- this is the center column, content area -->
<table border="0" cellspacing="0" cellpadding="0" width="478">
<tr>
<td><img src="<%=IMGPath%>/cw_spacer.gif" height="20" width="1"></td>
</tr>
<tr>
<td><img src='<app:store image="cw_mycwbar.gif"/>' width="478" height="5"></td>
</tr>
     <tr><td class="text">
         <app:custom pageElement="pages.main.text"/>
         </td>
     </tr>

</table>
</td>

<% /* image on the right hand side */ %>
<td width="203" align="center" valign="top">
<img src='<app:custom pageElement="pages.logo2.image" 
  addImagePath="true" encodeForHTML="true"/>'/>

<% /* customer tips on the right hand side */ %>
<logic:present name="pages.tips.text">
<div class="customer_home_tiptext">
<app:custom pageElement="pages.tips.text"/>
</div>
</logic:present>
</td>

<td class="changeshippingdk" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<table align="center" border="0" cellpadding="0" cellspacing="0" 
  width="<%=Constants.TABLEWIDTH%>">
<tr>
<td>
 <img src="<%=IMGPath%>/cw_left_footer_shop.gif" ALIGN="top">
</td>
<td>
 <img src="<%=IMGPath%>/cw_middle_footer_shop.gif" ALIGN="top" 
  width="<%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>" height="8">
</td>
<td>
 <img src="<%=IMGPath%>/cw_right_footer_shop.gif" ALIGN="top">
</td>
</tr>
<tr><td colspan=3 align=center>

<% 
String actionStr = "";
String cfg = request.getParameter("cfg");
if ( null != cfg && cfg.equals("store" ) ) {
 actionStr="adminportal/storeUIConfig.do";
} else {
 actionStr="adminportal/accountUIConfig.do";
}
%>





<html:form 
  action="<%=actionStr%>"
  name="UI_CONFIG_FORM"
  scope="session" 
  type="com.cleanwise.view.forms.UIConfigForm">


<logic:present name="UI_CONFIG_FORM" property="localeCd">   
<bean:define id="localeCd" type="java.lang.String" name="UI_CONFIG_FORM" property="localeCd"/>
<html:hidden name="UI_CONFIG_FORM" property="localeCd" value="<%=localeCd%>"/>
</logic:present>

<html:submit property="action">
<app:storeMessage  key="global.action.label.cancel"/>
</html:submit>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
</html:form>
</td>
</tr>
</table>

</div>

