<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
    String lShopUrl = "../store/shop.do";
%>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<html:html>
<head>

<div>
  <title><app:custom pageElement="pages.title"/></title>
</div>

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> -->
<!--  <app:stylesheet/>  -->
  <link rel="stylesheet" href="../externals/styles.css">
  <script src="../externals/lib.js" language="javascript"></script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="white">

<!-- START PAGE HEADER SECTION -->
<div>
  <template:get name="header"/>
</div>
<!-- END HEADER SECTION -->

<!-- START PAGE BODY FRAMEWORK (INSTITUTION LEFT NAV AND CONTENT AREA) -->
<div>
<table  ID="215"
        width="<%=Constants.TABLEWIDTH%>"
        align="center"
        border="0"
        cellspacing="0"
        cellpadding="0">
    <tr>
        <td>
            <table  width="100%" cellspacing="0" cellpadding="0" border="0">
            <tr>
                <td style="background: URL(images/cw_sppleft_top.gif) no-repeat top left; height: 9px; width: 8px; font-size: 1px;"></td>
                <td style="border-top: solid black 1px; height: 9px; width: <%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>px; font-size: 1px;">&nbsp;</td>
                <td style="background: URL(images/cw_sppright_top.gif) no-repeat top right; height: 9px; width: 8px;"></td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td width="100%"
            valign="top"
            align="center"
            style="{
                border-left:  solid black 1px;
                border-right:  solid black 1px;
            }">
            <jsp:include flush="true" page="t_serviceProviderNav.jsp">
                <jsp:param name="enableShop" value="<%=Boolean.toString(appUser != null && appUser.readyToShop())%>" />
                <jsp:param name="allowAssetManagement" value="<%=Boolean.toString(appUser != null && appUser.getUserStore().isAllowAssetManagement())%>" />
                <jsp:param name="shopUrl" value="<%=lShopUrl%>" />
            </jsp:include>
        </td>
    </tr>
    <tr>
        <td width="100%"
            valign="top"
            align="center"
            style="{
                border-left:  solid black 1px;
                border-right:  solid black 1px;
            }">&nbsp;
        </td>
    </tr>
    <template:useAttribute  id="tabBar01"
                            name="tabBar01"
                            ignore="true"
                            classname="java.lang.String"/>
    <logic:present name="tabBar01">
    <tr>
        <td width="100%"
            valign="top"
            align="center"
            style="{
                border-left:  solid black 1px;
                border-right:  solid black 1px;
            }">
            <template:get name="tabBar01" flush="true" ignore="true"/>
        </td>
    </tr>
    </logic:present>
    <tr>
        <td width="100%"
            valign="top"
            align="center"
            style="{
                border-left:  solid black 1px;
                border-right:  solid black 1px;
            }">&nbsp;
            <div class="text"><font color=red><html:errors/></font></div>
        </td>
    </tr>
    <template:useAttribute  id="content"
                            name="content"
                            ignore="true"
                            classname="java.lang.String"/>
    <logic:present name="content">
    <tr>
        <td width="100%"
            valign="top"
            align="center"
            style="{
                border-left:  solid black 1px;
                border-right:  solid black 1px;
            }">
            <template:get name="content"/>
        </td>
    </tr>
    </logic:present>
    <td colspan="3">
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
            <tr>
                <td style="background: URL(images/cw_sppleft_bottom.gif) no-repeat bottom left; height: 9px; width: 8px;"></td>
                <td style="border-bottom: solid black 1px; height: 9px; width: <%=Constants.TABLE_BOTTOM_MIDDLE_BORDER_WIDTH%>px; font-size: 1px;">&nbsp;</td>
                <td style="background: URL(images/cw_sppright_bottom.gif) no-repeat bottom right; height: 9px; width: 8px;"></td>
            </tr>
            </table>
    </td>
</table>
</div>
<!-- END PAGE BODY FRAMEWORK -->

<!-- START PAGE FOOTER SECTION -->
<div>
  <template:get name="footer"/>
</div>
<!-- END FOOTER SECTION -->

</body>
</html:html>