<%--
  Date: 03.10.2007
  Time: 11:55:10
--%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
    //parameters begin

    String actionLink01 = request.getParameter("actionLink01");
    String tabName01 = request.getParameter("tabName01");
    String item01 = Utility.strNN(request.getParameter("item01"));
    String secondaryItems01 = Utility.strNN(request.getParameter("secondaryItems01"));
    String itemLable01 = request.getParameter("itemLable01");
    String toolSecondaryToolBar01 = request.getParameter("toolSecondaryToolBar01");
    if (actionLink01 != null) {
        char sp01 = (actionLink01.indexOf("?") >= 0) ? '&' : '?';
        actionLink01 += sp01 + "tabs=" + tabName01 + "&display=" + item01;
    }

    String actionLink02 = request.getParameter("actionLink02");
    String tabName02 = request.getParameter("tabName02");
    String item02 = Utility.strNN(request.getParameter("item02"));
    String secondaryItems02 = Utility.strNN(request.getParameter("secondaryItems02"));
    String itemLable02 = request.getParameter("itemLable02");
    String toolSecondaryToolBar02 = request.getParameter("toolSecondaryToolBar02");

    if (actionLink02 != null) {
        char sp02 = (actionLink02.indexOf("?") >= 0) ? '&' : '?';
        actionLink02 += sp02 + "tabs=" + tabName02 + "&display=" + item02;
    }

    String color01 = request.getParameter("color01");
    String color02 = request.getParameter("color02");
    String color03 = request.getParameter("color03");
	String color04 = request.getParameter("color04");

    String headerLabel = request.getParameter("headerLabel");

    //parameters end
    String display = request.getParameter("display");
    if (display == null) display = "";

    boolean breadCrumbBar = Utility.isTrue(request.getParameter("breadCrumbBar"));

%>
<style>


a.secondaryNavButtonCurr:link,a.secondaryNavButtonCurr:visited,a.secondaryNavButtonCurr:active   {
        color: <%=color01%>;
        background-color: <%=color03%>;
        font-size: 10px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
        padding-bottom: 0em;
        border-top: solid 1px <%=color02%>;
        border-left: solid 1px <%=color02%>;
        border-right: solid 1px <%=color02%>;

}
a.secondaryNavButtonCurr:hover {
    color: <%=color01%>;
}

a.secondaryNavButton:link,a.secondaryNavButton:visited,a.secondaryNavButton:active   {
        color: <%=color04%>;
        background-color: <%=color02%>;
        font-size: 10px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
        padding-bottom: 0em;
        border-top: solid 1px <%=color02%>;
        border-left: solid 1px <%=color02%>;
        border-right: solid 1px <%=color02%>;

}
a.secondaryNavButton:hover {
	color: <%=color01%>;
    background-color: <%=color03%>;
}
.subHeader   {
        color: <%=color01%>;
        background-color: <%=color03%>;
        font-size: 14px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;

}
.toolbar1{
    background-color : <%=color02%>;
}
.toolbar2{
    background-color : <%=color03%>;
}
</style>


<bean:define id="toolSecondaryToolBar" value="" type="String"/>

<%
String headerLabelSection = null;
%>

<table style="border-collapse: collapse;" align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <!-- the following row contains the secondary navigation -->
  <tr><!-- the following row contains a the border between navigation and content -->
	<td class="toolbar1" colspan="2">
    <img src='<%=ClwCustomizer.getSIP(request,"cw_spacer.gif")%>' WIDTH="<%=Constants.TABLEWIDTH%>" HEIGHT="3">
	</td>
  </tr>
  <tr><!-- the following row contains the section-specific branding -->
    <td class="toolbar1" align="left" colspan="2">
        <table style="padding-bottom: 0em;" cellpadding="0" cellspacing="0" class="headernavtable" style="border-collapse: collapse;">
            <tr>
                <%if(secondaryItems01 != null || item01!=null){%>
                    <%
                    boolean currPage = false;
                    if(item01.indexOf(display) >= 0 ||
                            secondaryItems01.indexOf(display)>0) {

                        currPage = true;
                        headerLabelSection = itemLable01;
                        toolSecondaryToolBar=toolSecondaryToolBar01;

                    }
                    %>
                	<td class="toolbar1" valign="bottom">
                	  <a class=secondaryNavButton<%=(currPage)? "Curr": ""%> href="<%=actionLink01%>"><%if(itemLable01!=null && itemLable01.length()>0){%><app:storeMessage key="<%=itemLable01%>"/><%}%></a>
                    </td>
                <%}%>
                  <%if(secondaryItems02 != null || item02!=null){%>
                    <%
                    boolean currPage = false;
                    if(item02.indexOf(display) >= 0 ||
                            secondaryItems02.indexOf(display)>0) {

                        currPage = true;
                        headerLabelSection = itemLable02;
                        toolSecondaryToolBar=toolSecondaryToolBar02;

                    }
                    %>
                	<td class="toolbar1" valign="bottom">
                	  <a class=secondaryNavButton<%=(currPage)? "Curr": ""%> href="<%=actionLink02%>"><%if(itemLable02!=null && itemLable02.length()>0){%><app:storeMessage key="<%=itemLable02%>"/><%}%></a>
                    </td>
                <%}%>
            </tr>
        </table>
	</td>
  </tr>
  <%if(breadCrumbBar){ %>
    <tr valign="bottom"><!-- the following row contains label content -->
    <td class="toolbar2"><span class="subHeader"><app:breadCrumbBar/></span></td>
    <%if(toolSecondaryToolBar!=null && toolSecondaryToolBar.length() > 0){%>
        <jsp:include page="<%=toolSecondaryToolBar%>" flush="true" >
           <jsp:param name="headerLabel" value="<%=headerLabel%>"/>
           <jsp:param name="headerLabelSection" value="<%=headerLabelSection%>"/>
           <jsp:param name="breadCrumbBar" value="<%=String.valueOf(breadCrumbBar)%>"/>
      </jsp:include>
      <%}%>
  <%} else {%>

  <tr valign="bottom"><!-- the following row contains label content -->
	 <%if(!Utility.isSet(toolSecondaryToolBar) && headerLabel!=null && headerLabel.length() > 0){%> <td colspan="2"  class="toolbar2">
        <img src='<%=ClwCustomizer.getSIP(request,"cw_spacer.gif")%>' WIDTH="20" HEIGHT="30"><span class="subHeader"><app:storeMessage key="<%=headerLabel%>"/><%if(headerLabelSection!=null){%>:<app:storeMessage key="<%=headerLabelSection%>"/><%}%></span>
      </td><%}else if(!Utility.isSet(toolSecondaryToolBar)){%><td colspan="2"  class="toolbar2">
        <img src='<%=ClwCustomizer.getSIP(request,"cw_spacer.gif")%>' WIDTH="<%=Constants.TABLEWIDTH%>" HEIGHT="3">
     </td> <%} else
      if(toolSecondaryToolBar!=null && toolSecondaryToolBar.length() > 0){%>
        <jsp:include page="<%=toolSecondaryToolBar%>" flush="true" >
           <jsp:param name="headerLabel" value="<%=headerLabel%>"/>
           <jsp:param name="headerLabelSection" value="<%=headerLabelSection%>"/>
      </jsp:include>
      <%}%>

  </tr>
    <%}%>
</table>