<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
   //parameters begin

   String toolLink01 = request.getParameter("toolLink01");
   String tabs01 = request.getParameter("tabs01");
   String display01 = request.getParameter("display01");
   if(toolLink01!=null) {
       char sp01 = (toolLink01.indexOf("?")>=0)?'&':'?';
       toolLink01 += sp01+"tabs="+tabs01+"&display="+display01;
   }
   String toolLable01 = request.getParameter("toolLable01");
   String toolSecondaryToolBar01 = request.getParameter("toolSecondaryToolBar01");

   String toolLink02 = request.getParameter("toolLink02");
   String tabs02 = request.getParameter("tabs02");
   String display02 = request.getParameter("display02");
   String onClick02 = request.getParameter("onClick02");
   if(toolLink02!=null) {
       char sp02 = (toolLink02.indexOf("?")>=0)?'&':'?';
       toolLink02 += sp02+"tabs="+tabs02+"&display="+display02;
   }
   String toolLable02 = request.getParameter("toolLable02");
   String toolSecondaryToolBar02 = request.getParameter("toolSecondaryToolBar02");

   //new code:Begin
   //String toolLink0201 = request.getParameter("toolLink0201");
   String tabs0201 = request.getParameter("tabs0201");
   String display0201 = request.getParameter("display0201");

   //if(toolLink0201!=null) {
   //    char sp0201 = (toolLink0201.indexOf("?")>=0)?'&':'?';
   //    toolLink0201 += sp0201+"tabs="+tabs0201+"&display="+display0201;
   //}
   
   String toolLable0201 = request.getParameter("toolLable0201");
   String toolSecondaryToolBar0201 = request.getParameter("toolSecondaryToolBar0201");
   //new code: End
   
   String toolLink03 = request.getParameter("toolLink03");
   String tabs03 = request.getParameter("tabs03");
   String display03 = request.getParameter("display03");
   if(toolLink03!=null) {
       char sp03 = (toolLink03.indexOf("?")>=0)?'&':'?';
       toolLink03 += sp03+"tabs="+tabs03+"&display="+display03;
   }
   String toolLable03 = request.getParameter("toolLable03");
   String toolSecondaryToolBar03 = request.getParameter("toolSecondaryToolBar03");

   String toolLink04 = request.getParameter("toolLink04");
   String tabs04 = request.getParameter("tabs04");
   String display04 = request.getParameter("display04");
   if(toolLink04!=null) {
       char sp04 = (toolLink04.indexOf("?")>=0)?'&':'?';
       toolLink04 += sp04+"tabs="+tabs04+"&display="+display04;
   }
   String toolLable04 = request.getParameter("toolLable04");
   String toolSecondaryToolBar04 = request.getParameter("toolSecondaryToolBar04");

   String toolLink05 = request.getParameter("toolLink05");
   String tabs05 = request.getParameter("tabs05");
   String display05 = request.getParameter("display05");
   if(toolLink05!=null) {
       char sp05 = (toolLink05.indexOf("?")>=0)?'&':'?';
       toolLink05 += "?tabs="+tabs05+"&display="+display05;
   }
   String toolLable05 = request.getParameter("toolLable05");
   String toolSecondaryToolBar05 = request.getParameter("toolSecondaryToolBar05");

   String color01 = request.getParameter("color01");
   String color02 = request.getParameter("color02");
   String color03 = request.getParameter("color03");

   String headerLabel = request.getParameter("headerLabel");

   //parameters end
   String display = request.getParameter("display");
   if(display==null) display = "";
   String displayStr = "display="+display;
   String section = request.getParameter("section");
   String sectionStr = (section==null)? "":"section="+section;
   String storeDir=ClwCustomizer.getStoreDir();
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
        color: <%=color01%>;
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
                <%if(toolLink01 != null){%>
                    <%
                    boolean currPage = false;
                    if(toolLink01.indexOf(displayStr) >= 0 &&
                        toolLink01.indexOf(sectionStr) >=0) {
                        currPage = true;
                        headerLabelSection = toolLable01;
                        toolSecondaryToolBar=toolSecondaryToolBar01;
                    }
                    %>
                	<td class="toolbar1" valign="bottom">
                	  <a class=secondaryNavButton<%=(currPage)? "Curr": ""%> href="<%=toolLink01%>"><%if(toolLable01!=null && toolLable01.length()>0){%><app:storeMessage key="<%=toolLable01%>"/><%}%></a>
                    </td>
                <%}%> 
                <%if(toolLink02 != null){%>
                    <%
                    boolean currPage = false;
                    if(toolLink02.indexOf(displayStr) >= 0 &&
                        toolLink02.indexOf(sectionStr) >=0) {
                        currPage = true;
                        headerLabelSection = toolLable02;
                        toolSecondaryToolBar=toolSecondaryToolBar02;
                    }
                    %>
                    <td class="toolbar1" valign="bottom">
                    <%if(onClick02!=null){ 
                    	String tutURL=ClwCustomizer.getRootFilePath(request, "customtrainingflash.jsp");
                    %>
                      	<a class=secondaryNavButton<%=(currPage)? "Curr": ""%>  href="#" onclick="javascript:window.open('<%=tutURL%>','','location=0,status=0,scrollbars=0,width=800,height=600');return false;">
                    <%}else{%> 
                      	<a class=secondaryNavButton<%=(currPage)? "Curr": ""%> href="<%=toolLink02%>">
                    <%} %>
                    <%if(toolLable02!=null && toolLable02.length()>0){%><app:storeMessage key="<%=toolLable02%>"/><%}%></a>
                	</td>
                <%}%> 
                <%--
                <%if(toolLink0201 != null){%>
                    <%
                    boolean currPage = false;
                    if(toolLink0201.indexOf(displayStr) >= 0 &&
                        toolLink0201.indexOf(sectionStr) >=0) {
                        currPage = true;
                        headerLabelSection = toolLable0201;
                        toolSecondaryToolBar=toolSecondaryToolBar0201;
                    }
                    %>
                    <td class="toolbar1" valign="bottom">
                      <a class=secondaryNavButton<%=(currPage)? "Curr": ""%> href="<%=toolLink0201%>"><%if(toolLable0201!=null && toolLable0201.length()>0){%><app:storeMessage key="<%=toolLable0201%>"/><%}%></a>
                	</td>
                <%}%> 
                --%>
                <%if(toolLink03 != null){%>
                    <%
                    boolean currPage = false;
                    if(toolLink03.indexOf(displayStr) >= 0 &&
                        toolLink03.indexOf(sectionStr) >=0) {
                        currPage = true;
                        headerLabelSection = toolLable03;
                        toolSecondaryToolBar=toolSecondaryToolBar03;
                    }
                    %>
                    <td class="toolbar1" valign="bottom">
                      <a class=secondaryNavButton<%=(currPage)? "Curr": ""%> href="<%=toolLink03%>"><%if(toolLable03!=null && toolLable03.length()>0){%><app:storeMessage key="<%=toolLable03%>"/><%}%></a>
                	</td>
                <%}%> 
                <%if(toolLink04 != null){%>
                    <%
                    boolean currPage = false;
                    if(toolLink04.indexOf(displayStr) >= 0 &&
                        toolLink04.indexOf(sectionStr) >=0) {
                        currPage = true;
                        headerLabelSection = toolLable04;
                        toolSecondaryToolBar=toolSecondaryToolBar04;
                    }
                    %>
                    <td class="toolbar1" valign="bottom">
                      <a class=secondaryNavButton<%=(currPage)? "Curr": ""%> href="<%=toolLink04%>"><%if(toolLable04!=null && toolLable04.length()>0){%><app:storeMessage key="<%=toolLable04%>"/><%}%></a>
                	</td>
                <%}%> 
                <%if(toolLink05 != null){%>
                    <%
                    boolean currPage = false;
                    if(toolLink05.indexOf(displayStr) >= 0 &&
                        toolLink05.indexOf(sectionStr) >=0) {
                        currPage = true;
                        headerLabelSection = toolLable05;
                        toolSecondaryToolBar=toolSecondaryToolBar05;
                    }
                    %>
                    <td class="toolbar1" valign="bottom">
                      <a class=secondaryNavButton<%=(currPage)? "Curr": ""%> href="<%=toolLink05%>"><%if(toolLable05!=null && toolLable05.length()>0){%><app:storeMessage key="<%=toolLable05%>"/><%}%></a>
                	</td>
                <%}%>              
            </tr>
        </table>
	</td>	
  </tr>
  <tr valign="middle"><!-- the following row contains label content -->	
	<td class="toolbar2">
	  <%if(headerLabel!=null && headerLabel.length() > 0){%>
	    <img src='<%=ClwCustomizer.getSIP(request,"cw_spacer.gif")%>' WIDTH="20" HEIGHT="30"><span class="subHeader"><app:storeMessage key="<%=headerLabel%>"/><%if(headerLabelSection!=null){%>:<app:storeMessage key="<%=headerLabelSection%>"/><%}%></span>
      <%}else{%>
        <img src='<%=ClwCustomizer.getSIP(request,"cw_spacer.gif")%>' WIDTH="<%=Constants.TABLEWIDTH%>" HEIGHT="3">
      <%}%>
    </td><td class="toolbar2" align="right">
      <%if(toolSecondaryToolBar!=null && toolSecondaryToolBar.length() > 0){%>
        <jsp:include page="<%=toolSecondaryToolBar%>" flush="true" />
      <%}%>
	</td>
  </tr>
</table>