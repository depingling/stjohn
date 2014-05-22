<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.forms.UserShopForm" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.io.File" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!-- adjust images to current state -->
<%
String img_row2 = "";
    img_row2 = ClwCustomizer.getImagePath(session,"cw_allmsds.gif");
%>

<!-- pickup images -->
<table align="left" border="0" cellpadding="0" cellspacing="0"><tr><td class="msdsdk"><img src="<%=img_row2%>"></td></tr></table>



