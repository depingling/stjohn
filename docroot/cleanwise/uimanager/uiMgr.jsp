<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.forms.UiSiteMgrForm" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.UiMgrForm" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
    String property;
%>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="UI_MGR_FORM" type="com.cleanwise.view.forms.UiMgrForm"/>

<html:html>

<head>

</head>


<body class="tundra">

<div class="text">

<table cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH800%>"  class="mainbody">

<html:form name="UI_MGR_FORM"
           scope="session"
           action="uimanager/uimgr.do"
           scope="session"
           type="com.cleanwise.view.forms.UiMgrForm">


    <tr>
        <td>
            <b>Ui Id:</b>
        </td>

        <td>
            <%=((UiMgrForm) theForm).getUiView().getUiData().getUiId()%>
        </td>

        <td>
            <b>Ui Name:</b>
        </td>
        <td>
            <%property = "uiView.uiData.shortDesc";%>
            <html:text name="UI_MGR_FORM" property="<%=property%>"/>
        </td>
    </tr>

       <tr>
        <td colspan="2">&nbsp;</td>

        <td>
            <b>Ui Status:</b>
        </td>
        <td>
            <% property = "uiView.uiData.statusCd";%>
        <html:select name="UI_MGR_FORM" property="<%=property%>">
            <html:option value="<%=RefCodeNames.UI_STATUS_CD.ACTIVE%>"><%=RefCodeNames.UI_STATUS_CD.ACTIVE%></html:option>
        </html:select>
        </td>
    </tr>


<tr>
    <td class="largeheader" colspan="4"></td>
</tr>
 <tr>
    <td class="largeheader" align="center" colspan="4"><html:submit property="action">Save</html:submit></td>
</tr>

 </html:form>
</table>

    </div>
</body>

</html:html>


