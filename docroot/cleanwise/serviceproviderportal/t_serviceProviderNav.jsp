<%@ page language="java" %>

<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.service.api.util.Utility"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<%
String displayHelpSection = request.getParameter("displayHelpSection");
String enableShop = request.getParameter("enableShop");
String shopUrl = request.getParameter("shopUrl");
String allowAssetManagement = request.getParameter("allowAssetManagement");
%>

<script language="JavaScript">
    function goto(url) {
      document.location.href = url;
    }
</script>

<STYLE>
a.mainheadernav:link, a.mainheadernav:visited, a.mainheadernav:active {
    text-decoration: none;
    font-size: 10px;
    font-weight: bold;
    letter-spacing: -0.05em;
    color: #C4C400;   
}
a.mainheadernav:hover {
    text-decoration: none;
    font-size: 10px;
    font-weight: bold;
    letter-spacing: -0.05em;
    color: #FFFFFF;
    background-color: #006699
}
td.mainheadernav:hover {
    background-color: #006699
}
#workordertext{
    color: #C4C400;
}
#workordertext:hover{
    color: #FFFFFF;
}
</STYLE>

<table id="HelpTable" valign="top" align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
    <tr>
        <td width="84" align="center">
            <span style="margin-left: 2px;">
                <a href="../serviceproviderportal/workOrderSearch.do?action=startSearch&tabs=f_serviceProviderToolbar&display=t_serviceProviderWorkOrder">
                    <img    ID="109"
                            src='images/work-orders.jpg'
                            border="0"
                            name="cw_globalspacer"
                            width="84"
                            height="50">
                </a>
            </span>
        </td>
        <td width="84" align="center">
            <span style="margin-left: 5px;">
                <a href="../serviceproviderportal/serviceProviderReporting.do">
                    <img    ID="109"
                            src='images/cw_global6off.gif'
                            border="0"
                            name="cw_globalspacer"
                            width="84"
                            height="50">
                </a>
            </span>
        </td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td align="center"
            style=  "{  border-top: solid black 1px;
                        border-bottom: solid black 1px;
                    }"
            onclick="goto('../serviceproviderportal/workOrderSearch.do?action=startSearch&tabs=f_serviceProviderToolbar&display=t_serviceProviderWorkOrder');">
            <a  id="workordertext"
                class="mainheadernav"
                href="../serviceproviderportal/workOrderSearch.do?action=startSearch&tabs=f_serviceProviderToolbar&display=t_serviceProviderWorkOrder">
                <app:storeMessage key="shop.menu.main.workorder"/>
            </a>
        </td>
        <td align="center"
            style=  "{  border-top: solid black 1px;
                        border-bottom: solid black 1px;
                    }"
            onclick="goto('../serviceproviderportal/serviceProviderReporting.do');">
            <a  id="workordertext"
                class="mainheadernav"
                href="../serviceproviderportal/serviceProviderReporting.do">
                <app:storeMessage key="shop.menu.main.report"/>
            </a>
        </td>
        <td align="center"
            style=  "{  border-top: solid black 1px;
                        border-bottom: solid black 1px;
                    }">
            &nbsp; 
        </td>
    </tr>
</table>
