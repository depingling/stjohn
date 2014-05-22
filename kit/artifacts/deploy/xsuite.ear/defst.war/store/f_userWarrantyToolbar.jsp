<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%

    String secondaryActionLink01 = request.getParameter("secondaryActionLink01");
    String secondaryItem01 = request.getParameter("secondaryItem01");
    String secondaryItemLable01 = request.getParameter("secondaryItemLable01");
    String tabName01 = request.getParameter("tabName01");
    String secondaryChildItems01 = Utility.strNN(request.getParameter("secondaryChildItems01"));

    if (secondaryActionLink01 != null) {
        char sp01 = (secondaryActionLink01.indexOf("?") >= 0) ? '&' : '?';
        secondaryActionLink01 += sp01 + "tabs=" + tabName01 + "&display=" + secondaryItem01 + "&secondaryToolbar=f_userWarrantyToolbar";
    }
    ;

    String secondaryActionLink02 = request.getParameter("secondaryActionLink02");
    String secondaryItem02 = request.getParameter("secondaryItem02");
    String secondaryItemLable02 = request.getParameter("secondaryItemLable02");
    String tabName02 = request.getParameter("tabName01");
    String secondaryChildItems02 = Utility.strNN(request.getParameter("secondaryChildItems02"));

    if (secondaryActionLink02 != null) {
        char sp02 = (secondaryActionLink02.indexOf("?") >= 0) ? '&' : '?';
        secondaryActionLink02 += sp02 + "tabs=" + tabName02 + "&display=" + secondaryItem02 + "&secondaryToolbar=f_userWarrantyToolbar";
    }
    ;


    String secondaryActionLink03 = request.getParameter("secondaryActionLink03");
    String secondaryItem03 = request.getParameter("secondaryItem03");
    String secondaryItemLable03 = request.getParameter("secondaryItemLable03");
    String tabName03 = request.getParameter("tabName01");
    String secondaryChildItems03 = Utility.strNN(request.getParameter("secondaryChildItems03"));

    if (secondaryActionLink03 != null) {
        char sp03 = (secondaryActionLink03.indexOf("?") >= 0) ? '&' : '?';
        secondaryActionLink03 += sp03 + "tabs=" + tabName03 + "&display=" + secondaryItem03 + "&secondaryToolbar=f_userWarrantyToolbar";
    }
    ;

    String secondaryActionLink04 = request.getParameter("secondaryActionLink04");
    String secondaryItem04 = request.getParameter("secondaryItem04");
    String secondaryItemLable04 = request.getParameter("secondaryItemLable04");
    String tabName04 = request.getParameter("tabName01");
    String secondaryChildItems04 = Utility.strNN(request.getParameter("secondaryChildItems04"));

    if (secondaryActionLink04 != null) {
        char sp04 = (secondaryActionLink04.indexOf("?") >= 0) ? '&' : '?';
        secondaryActionLink04 += sp04 + "tabs=" + tabName04 + "&display=" + secondaryItem04 + "&secondaryToolbar=f_userWarrantyToolbar";
    }
    ;

    String display = request.getParameter("display");
    if (display == null) display = "";


    String headerLabel = request.getParameter("headerLabel");
    String headerLabelSection = request.getParameter("headerLabelSection");
    String secondaryLabelSection = null;
    //int index = 0;
    String color03 = request.getParameter("color03");
%>
<style>

    a.ssNavButtonCurr:link, a.ssNavButtonCurr:visited, a.ssNavButtonCurr:active {
        color: #6B8E23;
        background-color: #FFFFFF;
        font-size: 10px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
        padding-bottom: 0em;

        border-top: solid 1px #6B8E23;
        border-left: solid 1px #6B8E23;
        border-right: solid 1px #6B8E23;

    }

    a.ssNavButtonCurr:hover {
        color: #6B8E23;
    }

    a.ssNavButton:link, a.ssNavButton:visited, a.ssNavButton:active {
        color: #FFFFFF;
        background-color:#6B8E23;
        font-size: 10px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
        padding-bottom: 0em;
        border-top: solid 1px #6B8E23;
        border-left: solid 1px #6B8E23;
        border-right: solid 1px #6B8E23;

    }

    a.ssNavButton:hover {
        background-color: #FFFFFF;
		color:#6B8E23;
    }


</style>

<%if (secondaryItem01 != null) {%>
<%
    if (secondaryItem01.indexOf(display) >= 0 || secondaryChildItems01.indexOf(display)>=0) {
        secondaryLabelSection = secondaryItemLable01;
    }
%>

<%}%>
<%if (secondaryItem02 != null) {%>
<%
    if (secondaryItem02.indexOf(display) >= 0 || secondaryChildItems02.indexOf(display)>=0) {
        secondaryLabelSection = secondaryItemLable02;
    }
%>

<%}%>
<%if (secondaryItem03 != null) {%>
<%
    if (secondaryItem03.indexOf(display) >= 0  || secondaryChildItems03.indexOf(display)>=0) {
        secondaryLabelSection = secondaryItemLable03;
    }
%>
<%}%>
<%if (secondaryItem04 != null) {%>
<%
    if (secondaryItem04.indexOf(display) >= 0 || secondaryChildItems04.indexOf(display)>=0) {
        secondaryLabelSection = secondaryItemLable04;
    }
%>
<%}%>

<%if (headerLabel != null && headerLabel.length() > 0) {%>
<td class="toolbar2">
    <img src='<%=ClwCustomizer.getSIP(request,"cw_spacer.gif")%>' WIDTH="20" HEIGHT="30"><span class="subHeader"><app:storeMessage key="<%=headerLabel%>"/>
    <%if (headerLabelSection != null) {%>:<app:storeMessage key="<%=headerLabelSection%>"/><%}%>
    <%if (secondaryLabelSection != null) {%>:<app:storeMessage key="<%=secondaryLabelSection%>"/><%}%></span>
</td>
<%} else {%>
<td  class="toolbar2">
    <img src='<%=ClwCustomizer.getSIP(request,"cw_spacer.gif")%>' WIDTH="<%=Constants.TABLEWIDTH%>" HEIGHT="3">
</td>
<%}%>

<td align="right"  class="toolbar2">
    <table cellpadding="0" cellspacing="0">
        <tr>


            <%if (secondaryItem01 != null) {%>
            <%
                boolean currPage = false;
                if (secondaryItem01.indexOf(display) >= 0 || secondaryChildItems01.indexOf(display)>=0) {
                    currPage = true;
                }
            %>
            <td valign="bottom" class="toolbar2">
                <a class=ssNavButton<%=(currPage) ? "Curr" : ""%>
                   href="<%=secondaryActionLink01%>"><%if (secondaryActionLink01 != null && secondaryActionLink01.length() > 0) {%>
                    <app:storeMessage key="<%=secondaryItemLable01%>"/>
                    <%}%></a>
            </td>
            <%}%>
            <%if (secondaryItem02 != null) {%>
            <%
                boolean currPage = false;
                if (secondaryItem02.indexOf(display) >= 0 || secondaryChildItems02.indexOf(display)>=0) {
                    currPage = true;
                }
            %>
            <td valign="bottom" class="toolbar2">
                <a class=ssNavButton<%=(currPage) ? "Curr" : ""%>
                   href="<%=secondaryActionLink02%>"><%if (secondaryActionLink02 != null && secondaryActionLink02.length() > 0) {%>
                    <app:storeMessage key="<%=secondaryItemLable02%>"/>
                    <%}%></a>
            </td>
            <%}%>
            <%if (secondaryItem03 != null) {%>
            <%
                boolean currPage = false;
                if (secondaryItem03.indexOf(display) >= 0 || secondaryChildItems03.indexOf(display)>=0) {
                    currPage = true;
                }
            %>
            <td valign="bottom" class="toolbar2">
                <a class=ssNavButton<%=(currPage) ? "Curr" : ""%>
                   href="<%=secondaryActionLink03%>"><%if (secondaryActionLink03 != null && secondaryActionLink03.length() > 0) {%>
                    <app:storeMessage key="<%=secondaryItemLable03%>"/>
                    <%}%></a>
            </td>
            <%}%>
                <%if (secondaryItem04 != null) {%>
            <%
                boolean currPage = false;
                if (secondaryItem04.indexOf(display) >= 0 || secondaryChildItems04.indexOf(display)>=0) {
                    currPage = true;
                }
            %>
            <td valign="bottom" class="toolbar2">
                <a class=ssNavButton<%=(currPage) ? "Curr" : ""%>
                   href="<%=secondaryActionLink04%>"><%if (secondaryActionLink04 != null && secondaryActionLink04.length() > 0) {%>
                    <app:storeMessage key="<%=secondaryItemLable04%>"/>
                    <%}%></a>
            </td>
            <%}%>
        </tr>
    </table>

</td>