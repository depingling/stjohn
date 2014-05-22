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
String allowAssetManagement = request.getParameter("allowAssetManagement");
CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);


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

<%
   String color01 = "#FFFFFF";
   String color02 = "#003366";
   String color03 = "#006699";
   String color04 = "#6699CC";  //shopchartheader background style
%>

<STYLE>

td.mainheadernav  {
        background-color: <%=color01%>;
        border-top: solid 1px #69698E;
        padding-left: 0em;
        padding-right: 0em;
        padding-top: 0em;
        padding-bottom: 0em;
}

td.mainheadernav:hover     {
        background-color: <%=color01%>;
        bgcolor: <%=color03%>;
        border-top: solid 1px #69698E;
        color: #FFFFFF;
        padding-left: 0em;
        padding-right: 0em;
        padding-top: 0em;
        padding-bottom: 0em;
}

a.mainheadernavlink:link, a.mainheadernavlink:visited, a.mainheadernavlink:active       {
        TEXT-DECORATION: none;
        /* STJ-6231
        font-size: 10px;
        */
        font-weight: bold;
        letter-spacing: -0.05em;
        color: <%=color03%>;
}

a.mainheadernavlink:hover       {
        TEXT-DECORATION: none;
        /* STJ-6231
        font-size: 10px;
        */
        font-weight: bold;
        letter-spacing: -0.05em;
        color: #D62931;
        background-color: <%=color01%>;
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
.shopcharthead	{color:  <%=color02%>;
        font-weight: bold;
        background-color:  <%=color04%>;
        }

 </STYLE>

 <table class="mainheadernav" id="HelpTable" valign="top" align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <tr valign="top" align="center">
    <td align="left"><img src='<%=ClwCustomizer.getSIP(request,"cw_globalleft.gif")%>' border="0"  ></td>
    <td class=mainheadernav>
        <% if (Utility.isTrue(enableShop)){%>
            <a class="mainheadernavlink" href="<%=shopUrl%>">
                <img src='<%=ClwCustomizer.getSIP(request,"cw_global1off.gif")%>' border="0" name="cw_global1"  HEIGHT="50"><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"><br><app:storeMessage key="template.jd.bsc.menu.main.shop"/>
            </a>
        <%}else{%>
            <img src='<%=ClwCustomizer.getSIP(request,"cw_global_noshop.gif")%>' border="0" name="cw_global_noshop"  HEIGHT="50">
            <br>
            <img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%">
        <% } %>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class="mainheadernavlink" href="../userportal/customerAccountManagement.do">
            <img src='<%=ClwCustomizer.getSIP(request,"cw_global6off.gif")%>' border="0" name="cw_global6"  HEIGHT="50"><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"><br><app:storeMessage key="template.jd.bsc.menu.main.report"/>
        </a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class="mainheadernavlink" href="../userportal/msdsTemplate.do?tabs=productSupportToolBar&display=msdsTemplate">
            <img src='<%=ClwCustomizer.getSIP(request,"cw_global3off.gif")%>' border="0" name="cw_global3"  HEIGHT="50"><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"><br><app:storeMessage key="template.jd.bsc.menu.main.productSupport"/>
        </a>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
<%String tutURL=ClwCustomizer.getRootFilePath(request, "flash/customtrainingflash.jsp");%>
        <a class="mainheadernavlink" href="#" onclick="javascript:window.open('<%=tutURL%>','','location=0,status=0,scrollbars=0,width=820,height=700');return false;">
            <img src='<%=ClwCustomizer.getSIP(request,"cw_global2off.gif")%>' border="0" name="cw_global3" HEIGHT="50"
			><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
			><br><app:storeMessage key="template.jd.bsc.menu.main.training"/></a>
    </td>
    <% if (Utility.isTrue(allowAssetManagement)
	       && appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_USER)){
    %>
      <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
      <td class=mainheadernav>
          <a class="mainheadernavlink" href="../userportal/userAssets.do?tabs=f_userAssetToolbar&display=t_userAssetSearch">
              <img src='<%=ClwCustomizer.getSIP(request,"equipment.jpg")%>' border="0" name="cw_global3"  HEIGHT="50"><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"><br><app:storeMessage key="shop.menu.main.equipment"/>
          </a>
      </td>
    <% } %>
    <% if (Utility.isTrue(allowAssetManagement)
	       && appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_USER)){
    %>

      <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
      <td class=mainheadernav>
          <a class="mainheadernavlink" href="../userportal/userWorkOrder.do?tabs=f_userWorkOrderToolbar&display=t_userWorkOrder">
              <img src='<%=ClwCustomizer.getSIP(request,"work-orders.jpg")%>' border="0" name="cw_global3" WIDTH="100%" HEIGHT="50"><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"><br><app:storeMessage key="shop.menu.main.workorder"/>
          </a>
      </td>
    <% } %>
    <td align="right"><img src='<%=ClwCustomizer.getSIP(request,"cw_globalright.gif")%>' border="0" /></td>
  </tr>
</table>

