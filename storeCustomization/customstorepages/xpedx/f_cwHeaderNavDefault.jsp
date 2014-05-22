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
        border-top: solid 1px #003366;
        padding-left: 0em;
        padding-right: 0em;
        padding-top: 0em;
        padding-bottom: 0em;
}
td.mainheadernav:hover     {
        background-color: #006699;
        bgcolor: #006699;
        border-top: solid 1px #003366;
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
        color: #003366;
}
a.mainheadernav:hover       {
        TEXT-DECORATION: none;
        font-size: 10px;
        font-weight: bold;
        letter-spacing: -0.05em;
        color: #FFFFFF;
        background-color: #003366;
}


img.mainheadernav{
    border-bottom: solid 1px #000000;
    padding-left: 0em;
    padding-right: 0em;
}
img.mainheadernavspacer,td.mainheadernavspacer{
    border-top: solid 1px #69698E;
    padding-left: 0em;
    padding-right: 0em;
    padding-top: 0em;
    padding-bottom: 0em;
}
table.mainheadernav{
        border-collapse: collapse;
}

#assettext{
    color: #003366;
}
#assettext:hover{
    color: #FFFFFF;
}
#shoptext{
    color: #003366;
}
#shoptext:hover{
    color: #FFFFFF;
}
#trainingtext{
    color: #003366;
}
#trainingtext:hover{
    color: #FFFFFF;
}

#producttext{
    color: #003366;
}
#producttext:hover{
    color: #FFFFFF;
}
#troubletext{
    color: #003366;
}
#troubletext:hover{
    color: #FFFFFF;
}
#safetytext{
    color: #003366;
}
#safetytext:hover{
    color: #FFFFFF;
}
#reporttext{
    color: #003366;
}
#reporttext:hover{
    color: #FFFFFF;
}
</STYLE>






 <table class="mainheadernav" id="HelpTable" valign="top" align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <tr valign="top" align="center">
     <td>   <img src='<%=ClwCustomizer.getSIP(request,"cw_globalleft.gif")%>' border="0" WIDTH="8" HEIGHT="69"></td>
      <% if (Utility.isTrue(allowAssetManagement)) {%>
      <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
      <td class="mainheadernav"
          onmouseover="paint('assettext','#003366','white');"
          onmouseout="paint('assettext','white','#6B8E23');"
          onclick="goto('../userportal/userAssets.do');">
          <a class=mainheadernav id=assettext href="../userportal/userAssets.do">
              <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global_asset85x50.jpg")%>' border="0" name="cw_globalspacer"
                   WIDTH="85" HEIGHT="50">
              <app:storeMessage key="shop.menu.main.asset"/>
          </a>
      </td>
      <%} else {%>
    <td class=mainheadernavspacer><img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_globalspacer.gif")%>' border="0" name="cw_globalspacer" WIDTH="85" HEIGHT="50">
    </td><%}%>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class="mainheadernav"
        onmouseover="paint('shoptext','#003366','white');"
        onmouseout="paint('shoptext','white','#336666');"
        onclick="goto('<%=shopUrl%>');">
        <% if (Utility.isTrue(enableShop)){%>
            <a class=mainheadernav id=shoptext href="<%=shopUrl%>">
                <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global1off.gif")%>' border="0" name="cw_global1" WIDTH="116" HEIGHT="50">
                <app:storeMessage key="shop.menu.main.shop"/></a>
        <%}else{%>
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global_noshop.gif")%>' border="0" name="cw_global_noshop" WIDTH="116" HEIGHT="50">
        <%}%>
    </td>
    <td class=mainheadernavspacer><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav
        onmouseover="paint('trainingtext','#003366','white');"
        onmouseout="paint('trainingtext','white','#9c0000');"
        onclick="goto('../userportal/templator.do?tabs=trainingToolBar&display=f_trainingController&section=tips');">
        <a id=trainingtext class=mainheadernav href="../userportal/templator.do?tabs=trainingToolBar&display=f_trainingController&section=tips">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global2off.gif")%>' border="0" name="cw_global3" WIDTH="117" HEIGHT="50">
            <app:storeMessage key="shop.menu.main.training"/></a>
    </td>
    <td class=mainheadernavspacer><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav
        onmouseover="paint('producttext','#003366','white');"
        onmouseout="paint('producttext','white','#003366');"
        onclick="goto('../userportal/templator.do?tabs=&display=f_disabled');">
        <a id=producttext class=mainheadernav href="../userportal/templator.do?tabs=blankToolBar&display=f_disabled">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global3off.gif")%>' border="0" name="cw_global3" WIDTH="116" HEIGHT="50">
            <app:storeMessage key="shop.menu.main.productSelector"/></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav
        onmouseover="paint('troubletext','#003366','white');"
        onmouseout="paint('troubletext','white','#ff9a00');"
        onclick="goto('../userportal/templator.do?display=f_troubleshootingController&tabs=troubleShootingToolBar');">
        <a id=troubletext class=mainheadernav href="../userportal/templator.do?display=f_troubleshootingController&tabs=troubleShootingToolBar">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global4off.gif")%>' border="0" name="cw_global3" WIDTH="120" HEIGHT="50">
            <app:storeMessage key="template.xpedx.shop.menu.main.help"/></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav
        onmouseover="paint('safetytext','#003366','white');"
        onmouseout="paint('safetytext','white','#310063');"
        onclick="goto('http://www.msdsonfile.com/mctx/msds/msdsonfile.jsp');">
        <a id=safetytext class=mainheadernav href="http://www.msdsonfile.com/mctx/msds/msdsonfile.jsp">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global5off.gif")%>' border="0" name="cw_global3" WIDTH="116" HEIGHT="50">
            <app:storeMessage key="shop.menu.main.safety"/></a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav
        onmouseover="paint('reporttext','#003366','white');"
        onmouseout="paint('reporttext','white','#006500');"
        onclick="goto('../userportal/customerAccountManagement.do');">
        <a id=reporttext class=mainheadernav href="../userportal/customerAccountManagement.do">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global6off.gif")%>' border="0" name="cw_global6" WIDTH="75" HEIGHT="50"/>
            <app:storeMessage key="shop.menu.main.report"/></a>
    </td>
    <td><img src='<%=ClwCustomizer.getSIP(request,"cw_globalright.gif")%>' border="0" WIDTH="8" HEIGHT="69"/></td>
  </tr>
</table>
