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

<%
String displayHelpSection = request.getParameter("displayHelpSection");
String enableShop = request.getParameter("enableShop");
String shopUrl = request.getParameter("shopUrl");
%>

<script language="JavaScript">
    function goto(url) {
      document.location.href = url;
    }
    
    function paint(obj,bgcolor,linkcolor)
    {
    var objA = document.getElementById(obj);
    var objTD = objA.parentElement;
    objTD.style.background = bgcolor;
    objA.style.background = bgcolor;
    objA.style.color=linkcolor;
    }

 </script>

<STYLE>
td.mainheadernav  {
        background-color: #FFFFFF;
        border-top: solid 1px #69698E;
        padding-left: 0em;
        padding-right: 0em;
        padding-top: 0em;
        padding-bottom: 0em;
}
td.mainheadernav:hover     {
        background-color: #FFFFFF;
        bgcolor: #006699;
        border-top: solid 1px #69698E;
        color: #FFFFFF;
        padding-left: 0em;
        padding-right: 0em;
        padding-top: 0em;
        padding-bottom: 0em;
}
a.mainheadernav:link,a.mainheadernav:visited,a.mainheadernav:active       {
        TEXT-DECORATION: none;
        font-size: 10px;
        font-weight: bold;
        letter-spacing: -0.05em;
        color: #006699;
}
a.mainheadernav:hover       {
        TEXT-DECORATION: none;
        font-size: 10px;
        font-weight: bold;
        letter-spacing: -0.05em;
        color: #D62931;
        background-color: #FFFFFF;
}

img.mainheadernav{
    border-bottom: solid 1px #000000;
    padding-left: 0em;
}
img.mainheadernavspacer,td.mainheadernavspacer{
    border-top: solid 1px #69698E;
    border-bottom: solid 1px #000000;
    padding-left: 0em;
    padding-right: 0em;
    padding-top: 0em;
    padding-bottom: 0em;
}
table.mainheadernav{
        border-collapse: collapse;
}
</STYLE>




  

 <table class="mainheadernav" id="HelpTable" valign="top" align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <tr valign="top" align="center">
    <td><img src='<%=ClwCustomizer.getSIP(request,"cw_globalleft.gif")%>' border="0" WIDTH="8" HEIGHT="69"></td>
        <td class=mainheadernav>
        <% if (Utility.isTrue(enableShop)){%>
            <a class=mainheadernav href="<%=shopUrl%>">
                <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global1off.gif")%>' border="0" name="cw_global1" WIDTH="116" HEIGHT="50">
                <app:storeMessage key="template.jd.bsc.menu.main.shop"/></a>
        <%}else{%>
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global_noshop.gif")%>' border="0" name="cw_global_noshop" WIDTH="116" HEIGHT="50">
        <%}%>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/customerAccountManagement.do">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global6off.gif")%>' border="0" name="cw_global6" WIDTH="75" HEIGHT="50"/>
            <app:storeMessage key="template.jd.bsc.menu.main.report"/></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/msdsTemplate.do?tabs=productSupportToolBar&display=msdsTemplate">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global3off.gif")%>' border="0" name="cw_global3" WIDTH="116" HEIGHT="50">
            <app:storeMessage key="template.jd.bsc.menu.main.productSupport"/></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/templator.do?tabs=trainingToolBar&display=customsupervisortools">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global2off.gif")%>' border="0" name="cw_global3" WIDTH="116" HEIGHT="50">
            <app:storeMessage key="template.jd.bsc.menu.main.training"/></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/templator.do?tabs=safetyToolBar&display=customsafeprograms">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global5off.gif")%>' border="0" name="cw_global3" WIDTH="116" HEIGHT="50">
            <app:storeMessage key="template.jd.bsc.menu.main.safety"/></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/templator.do?display=custommarketing&tabs=marketingToolBar">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global4off.gif")%>' border="0" name="cw_global3" WIDTH="120" HEIGHT="50">
            <app:storeMessage key="template.jd.safeway.menu.main.foodsafety"/></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/templator.do?display=customjdsupport&tabs=jdSupportToolBar">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global0off.gif")%>' border="0" name="cw_global3" WIDTH="78" HEIGHT="50">
            <app:storeMessage key="template.jd.bsc.menu.main.jdSupport"/></a>
    </td>
    <td><img src='<%=ClwCustomizer.getSIP(request,"cw_globalright.gif")%>' border="0" WIDTH="8" HEIGHT="69"/></td>
  </tr>
</table>
