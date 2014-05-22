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
    boolean renderlink01 = Utility.isTrue(request.getParameter("renderLink01"));

    if (secondaryActionLink01 != null) {
        char sp01 = (secondaryActionLink01.indexOf("?") >= 0) ? '&' : '?';
        secondaryActionLink01 += sp01 + "tabs=" + tabName01 + "&display=" + secondaryItem01 + "&secondaryToolbar=f_userSecondaryToolbar";
    }
    ;

    String secondaryActionLink02 = request.getParameter("secondaryActionLink02");
    String secondaryItem02 = request.getParameter("secondaryItem02");
    String secondaryItemLable02 = request.getParameter("secondaryItemLable02");
    String tabName02 = request.getParameter("tabName01");
    String secondaryChildItems02 = Utility.strNN(request.getParameter("secondaryChildItems02"));
    boolean renderlink02 = Utility.isTrue(request.getParameter("renderLink02"));

    if (secondaryActionLink02 != null) {
        char sp02 = (secondaryActionLink02.indexOf("?") >= 0) ? '&' : '?';
        secondaryActionLink02 += sp02 + "tabs=" + tabName02 + "&display=" + secondaryItem02 + "&secondaryToolbar=f_userSecondaryToolbar";
    }
    ;


    String secondaryActionLink03 = request.getParameter("secondaryActionLink03");
    String secondaryItem03 = request.getParameter("secondaryItem03");
    String secondaryItemLable03 = request.getParameter("secondaryItemLable03");
    String tabName03 = request.getParameter("tabName01");
    String secondaryChildItems03 = Utility.strNN(request.getParameter("secondaryChildItems03"));
    boolean renderlink03 = Utility.isTrue(request.getParameter("renderLink03"));

    if (secondaryActionLink03 != null) {
        char sp03 = (secondaryActionLink03.indexOf("?") >= 0) ? '&' : '?';
        secondaryActionLink03 += sp03 + "tabs=" + tabName03 + "&display=" + secondaryItem03 + "&secondaryToolbar=f_userSecondaryToolbar";
    }
    ;

    String secondaryActionLink04 = request.getParameter("secondaryActionLink04");
    String secondaryItem04 = request.getParameter("secondaryItem04");
    String secondaryItemLable04 = request.getParameter("secondaryItemLable04");
    String tabName04 = request.getParameter("tabName01");
    String secondaryChildItems04 = Utility.strNN(request.getParameter("secondaryChildItems04"));
    boolean renderlink04 = Utility.isTrue(request.getParameter("renderLink01"));

    if (secondaryActionLink04 != null) {
        char sp04 = (secondaryActionLink04.indexOf("?") >= 0) ? '&' : '?';
        secondaryActionLink04 += sp04 + "tabs=" + tabName04 + "&display=" + secondaryItem04 + "&secondaryToolbar=f_userSecondaryToolbar";
    }
    ;

    String display = request.getParameter("display");
    if (display == null) display = "";


    String headerLabel = request.getParameter("headerLabel");
    String headerLabelSection = request.getParameter("headerLabelSection");
    String secondaryLabelSection = null;
    boolean variantB=  Utility.isTrue(request.getParameter("variantB"));
    //int index = 0;
    String color03 = request.getParameter("color03");

    boolean breadCrumbBar = Utility.isTrue(request.getParameter("breadCrumbBar"));

%>
<style>

    a.ssNavButtonCurr:link, a.ssNavButtonCurr:visited, a.ssNavButtonCurr:active {
        color: <%=color03%>;
        background-color: #FFFFFF;
        font-size: 10px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
        padding-bottom: 0em;

        border-top: solid 1px <%=color03%>;
        border-left: solid 1px <%=color03%>;
        border-right: solid 1px <%=color03%>;

    }

    a.ssNavButtonCurr:hover {
        color: <%=color03%>;
    }

    a.ssNavButton:link, a.ssNavButton:visited, a.ssNavButton:active {
        color: #FFFFFF;
        background-color:<%=color03%>;
        font-size: 10px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
        padding-bottom: 0em;
        border-top: solid 1px <%=color03%>;
        border-left: solid 1px <%=color03%>;
        border-right: solid 1px <%=color03%>;

    }

    a.ssNavButton:hover {
        background-color: #FFFFFF;
        color:<%=color03%>;
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
<%if(!breadCrumbBar){%>
<%if (headerLabel != null && headerLabel.length() > 0 && !variantB) {%>
<td class="toolbar2">
    <img src='<%=ClwCustomizer.getSIP(request,"cw_spacer.gif")%>' WIDTH="20" HEIGHT="30"><span class="subHeader"><app:storeMessage key="<%=headerLabel%>"/>
    <%if (headerLabelSection != null) {%>:<app:storeMessage key="<%=headerLabelSection%>"/><%}%>
    <%if (secondaryLabelSection != null) {%>:<app:storeMessage key="<%=secondaryLabelSection%>"/><%}%></span>
</td>
<%}else if(headerLabel != null && headerLabel.length() > 0 && variantB){%>
<td class="toolbar2">
    <img src='<%=ClwCustomizer.getSIP(request,"cw_spacer.gif")%>' WIDTH="20" HEIGHT="30"><span class="subHeader"><app:storeMessage key="<%=headerLabel%>"/>
    <%if (headerLabelSection != null) {%>:<app:storeMessage key="<%=headerLabelSection%>"/><%}%>
    <%if (secondaryItemLable01 != null) {%>:<app:storeMessage key="<%=secondaryItemLable01%>"/><%}%>
    <%if (secondaryLabelSection != null && !secondaryLabelSection.equals(secondaryItemLable01)) {%>:<app:storeMessage key="<%=secondaryLabelSection%>"/><%}%></span>
</td> <%} else {%>
<td  class="toolbar2">
    <img src='<%=ClwCustomizer.getSIP(request,"cw_spacer.gif")%>' WIDTH="<%=Constants.TABLEWIDTH%>" HEIGHT="3">
</td>
<%}%>
<%}%>
<td align="right"  class="toolbar2">
    <table cellpadding="0" cellspacing="0">
        <tr>

            <%if(!variantB){%>
            <%if (secondaryItem01 != null && renderlink01) {%>
            <%
                boolean currPage = false;
                if (secondaryItem01.indexOf(display) >= 0 || secondaryChildItems01.indexOf(display)>=0) {
                    currPage = true;
                }
            %>
            <td valign="bottom" class="toolbar2">
                <a class=ssNavButton<%=(currPage) ? "Curr" : ""%>
                   href="<%=secondaryActionLink01%>"><%if (Utility.isSet(secondaryItemLable01)) {%>
                    <app:storeMessage key="<%=secondaryItemLable01%>"/>
                    <%}%></a>
            </td>
            <%}%>
            <%if (secondaryItem02 != null && renderlink02) {%>
            <%
                boolean currPage = false;
                if (secondaryItem02.indexOf(display) >= 0 || secondaryChildItems02.indexOf(display)>=0) {
                    currPage = true;
                }
            %>
            <td valign="bottom" class="toolbar2">
                <a class=ssNavButton<%=(currPage) ? "Curr" : ""%>
                   href="<%=secondaryActionLink02%>"><%if (Utility.isSet(secondaryItemLable02)) {%>
                    <app:storeMessage key="<%=secondaryItemLable02%>"/>
                    <%}%></a>
            </td>
            <%}%>
            <%if (secondaryItem03 != null && renderlink03) {%>
            <%
                boolean currPage = false;
                if (secondaryItem03.indexOf(display) >= 0 || secondaryChildItems03.indexOf(display)>=0) {
                    currPage = true;
                }
            %>
            <td valign="bottom" class="toolbar2">
                <a class=ssNavButton<%=(currPage) ? "Curr" : ""%>
                   href="<%=secondaryActionLink03%>"><%if (Utility.isSet(secondaryItemLable03)) {%>
                    <app:storeMessage key="<%=secondaryItemLable03%>"/>
                    <%}%></a>
            </td>
            <%}%>
            <%if (secondaryItem04 != null && renderlink04) {%>
            <%
                boolean currPage = false;
                if (secondaryItem04.indexOf(display) >= 0 || secondaryChildItems04.indexOf(display)>=0) {
                    currPage = true;
                }
            %>
            <td valign="bottom" class="toolbar2">
                <a class=ssNavButton<%=(currPage) ? "Curr" : ""%>
                   href="<%=secondaryActionLink04%>"><%if (Utility.isSet(secondaryItemLable04)) {%>
                    <app:storeMessage key="<%=secondaryItemLable04%>"/>
                    <%}%></a>
            </td>
            <%}
            } else  {%>
            <td valign="bottom" class="toolbar2">
                <a class=ssNavButtonCurr
                   href="<%=secondaryActionLink01%>"><%if (Utility.isSet(secondaryItemLable01)) {%>
                    <app:storeMessage key="<%=secondaryItemLable01%>"/>
                    <%}%></a>
            </td>
            <%}%>
        </tr>
    </table>

</td>