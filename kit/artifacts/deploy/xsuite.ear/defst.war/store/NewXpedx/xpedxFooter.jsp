<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ShopTool"%>
<%@ page import="com.cleanwise.service.api.util.I18nUtil" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table width="100%"  cellspacing="0" cellpadding="0"  >
<tr>
<%
		CleanwiseUser user = ShopTool.getCurrentUser(request.getSession());
%>
<td align="center"  class="xpdexMenuHeader"><%=I18nUtil.getMessage(user.getStorePrefixLocale(),"template.xpedx.store.footerHeader")%>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td><%=I18nUtil.getMessage(user.getStorePrefixLocale(),"template.xpedx.store.footer")%>
</td>
</tr>