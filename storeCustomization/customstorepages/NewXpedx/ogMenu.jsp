
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.session.*" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.*"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>
<%
	OrderGuideDataVector thisDV = (OrderGuideDataVector)session.getAttribute(SessionAttributes.ORDER_GUIDE.ALL_ORDER_GUIDES);
%>

<table width="100%">
<tr><td class="xpdexMenuHeader"><app:storeMessage key="template.xpedx.homepage.header.myShoppingList"/></td></tr>
<tr><td><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="4"></td></tr>
<% for(int i=0; i<thisDV.size(); i++){
	OrderGuideData thisGuide = (OrderGuideData)thisDV.get(i);
	int ogid = thisGuide.getOrderGuideId();
	String ogname = thisGuide.getShortDesc();

	String ognameEncoded = URLEncoder.encode(ogname,"UTF-8");

%>
	<tr><td align="left">

	<a class ="categorymenulevel_1" href="../store/shop.do?action=openList&orderGuideId=<%=ogid%>&orderGuideName=<%=ognameEncoded%>"><%=ogname%></a>
	</td></tr>
<% } %>

</table>


