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
    <td><img src='<%=ClwCustomizer.getSIP(request,"cw_globalleft.gif")%>' border="0" ></td>
        <td class=mainheadernav>
        <% if (Utility.isTrue(enableShop)){%>
            <a class=mainheadernav href="../store/shop.do?action=initAndSelectOrderGuide">
                <img src='<%=ClwCustomizer.getSIP(request,"cw_global1off.gif")%>' border="0" name="cw_global1"  HEIGHT="50"
			    ><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
			    ><br><app:storeMessage key="template.jd.bsc.menu.main.shop"/></a>
        <%}else{%>
            <img src='<%=ClwCustomizer.getSIP(request,"cw_global_noshop.gif")%>' border="0" name="cw_global_noshop"  HEIGHT="50"
			><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
			>
		<% } %>
    </td>
    <td><img class=mainheadernavspacer src='<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>' HEIGHT="64"/></td>
    <td class=mainheadernav>
        <a class=mainheadernav href="../userportal/msdsTemplate.do?tabs=productSupportToolBar&display=msdsTemplate">
            <img src='<%=ClwCustomizer.getSIP(request,"cw_global3off.gif")%>' border="0" name="cw_global3"  HEIGHT="50"
			><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
			><br><app:storeMessage key="template.jd.bsc.menu.main.productSupport"/></a>
    </td>
    <% if (Utility.isTrue(allowAssetManagement)
	       && appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_USER)){
    %>
      <td><img class=mainheadernavspacer src="<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>" HEIGHT="64"/></td>
      <td class=mainheadernav>
          <a class=mainheadernav href="../userportal/userAssets.do?tabs=f_userAssetToolbar&display=t_userAssetSearch">
              <img src="<%=ClwCustomizer.getSIP(request,"equipment.jpg")%>" border="0" name="cw_global3"  HEIGHT="50"
				><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
                ><br><app:storeMessage key="shop.menu.main.equipment"/>
          </a>
      </td>
	<%}%>
    <% if (Utility.isTrue(allowAssetManagement)
	       && appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_USER)){
    %>

      <td><img class=mainheadernavspacer src="<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>" HEIGHT="64"/></td>
      <td class=mainheadernav>
          <a class=mainheadernav href="../userportal/userWorkOrder.do?tabs=f_userWorkOrderToolbar&display=t_userWorkOrder">
              <img src="<%=ClwCustomizer.getSIP(request,"work-orders.jpg")%>" border="0" name="cw_global3" WIDTH="100%" HEIGHT="50"
					><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
				    ><br><app:storeMessage key="shop.menu.main.workorder"/>
          </a>
      </td>
	<%}%>
	<td><img class=mainheadernavspacer src="<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>" HEIGHT="64"/></td>
      <td class=mainheadernav >
          <a class=mainheadernav href="#">
              <img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" name="cw_global3" width="190" HEIGHT="50"
					><br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%"
				    ><br>&nbsp;
          </a>
      </td>
	<%--<td><img class=mainheadernavspacer src="<%=ClwCustomizer.getSIP(request,"cw_global_main_menu_spacer.gif")%>" HEIGHT="64" /></td>
      <a class=mainheadernav href="#"><td class=mainheadernav>
              <img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" name="cw_global4" width="100%" HEIGHT="50">
			  <br><img src='<%=ClwCustomizer.getSIP(request,"cw_hline.gif")%>' border="0" height="1" width="100%">
			  <br>empty
			  </a>
      </td>--%>
<td><img ID=122 src="<%=ClwCustomizer.getSIP(request,"cw_globalright.gif")%>" border="0" WIDTH="8" HEIGHT="69"/></td>
    </tr>
</table>
