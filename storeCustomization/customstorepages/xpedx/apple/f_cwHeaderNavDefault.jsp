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

 <table class="mainheadernav" id="HelpTable" valign="top" align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <tr valign="top" align="center">
     <td>   <img src='<%=ClwCustomizer.getSIP(request,"cw_globalleft.gif")%>' border="0" WIDTH="8" HEIGHT="69"></td>
      <% if (Utility.isTrue(allowAssetManagement)) {%>
      <td class="mainheadernav"
          onmouseover="paint('assettext','#636163','white');"
          onmouseout="paint('assettext','white','#636163');"
          onclick="goto('../userportal/userAssets.do');">
          <a class=mainheadernav id=assettext href="../userportal/userAssets.do">
              <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global_asset85x50.jpg")%>' border="0" name="cw_globalspacer"
                   WIDTH="85" HEIGHT="50"><br><app:storeMessage key="shop.menu.main.asset"/>
          </a>
      </td>
      <%} else {%>
     <td class=mainheadernavspacer><img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_globalspacer.gif")%>' border="0" name="cw_globalspacer" WIDTH="44" HEIGHT="50">
   </td><%}%>
    <td class="mainheadernav"
        onmouseover="paint('shoptext','#636163','white');"
        onmouseout="paint('shoptext','white','#636163');"
        onclick="goto('<%=shopUrl%>');">
        <% if (Utility.isTrue(enableShop)){%>
            <a class=mainheadernav id=shoptext href="<%=shopUrl%>">

            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cart_48x48.png")%>' border="0" name="cw_global1" WIDTH="117"
                  HEIGHT="50"><br><app:storeMessage key="shop.menu.main.shopm"/></a>

        <%}else{%>
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global_noshop.gif")%>' border="0" name="cw_global_noshop" WIDTH="116" HEIGHT="50">
        <%}%>
    </td>
    <td class=mainheadernav
        onmouseover="paint('trainingtext','#636163','white');"
        onmouseout="paint('trainingtext','white','#636163');"
        onclick="goto('../store/orderstatus.do?action=order_status');">
        <a id=trainingtext class=mainheadernav href="../store/orderstatus.do?action=order_status">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"tracking_48x48.png")%>' border="0" name="cw_global3" WIDTH="117"
              HEIGHT="50"><br><app:storeMessage key="shop.menu.main.trackOrder"/></a>
    </td>
    <td class=mainheadernav
        onmouseover="paint('reporttext','#636163','white');"
        onmouseout="paint('reporttext','white','#636163');"
        onclick="goto('../userportal/customerAccountManagement.do');">
        <a id=reporttext class=mainheadernav href="../userportal/customerAccountManagement.do">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"reports_48x48.png")%>' border="0" name="cw_global6" WIDTH="80"
              HEIGHT="50"/><br><app:storeMessage key="shop.menu.main.reports"/></a>
    </td>
    <td class=mainheadernav
        onmouseover="paint('contactustext','#636163','white');"
        onmouseout="paint('contactustext','white','#636163');"
        onclick="goto('../userportal/templator.do?display=t_contactUsBody.jsp&tabs=contactUsToolBar');">
        <a id=contactustext class=mainheadernav href="../userportal/templator.do?display=t_contactUsBody.jsp&tabs=contactUsToolBar">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"contacts_48x48.png")%>' border="0" name="cw_global3"
             WIDTH="117" HEIGHT="50"><br><app:storeMessage key="shop.menu.main.contactUs"/></a>

    </td>
	<td class=mainheadernav
        onmouseover="paint('autodistrotext','#636163','white');"
        onmouseout="paint('autodistrotext','white','#636163');"
        onclick="goto('../store/scheduledCart.do?action=showScheduledCart');">
        <a id=autodistrotext class=mainheadernav href="../store/scheduledCart.do?action=showScheduledCart">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"auto_distro.png")%>' border="0" name="cw_global3" WIDTH="117"
              HEIGHT="50"><br><app:storeMessage key="shop.menu.main.autoDistro"/></a>

	</td>


	<td class=mainheadernav
        onmouseover="paint('faqtext','#636163','white');"
        onmouseout="paint('faqtext','white','#636163');" >

        <a id=faqtext class=mainheadernav href="http://retail.apple.com" target="_blank">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"question_mark.jpg")%>' border="0" name="cw_global3" WIDTH="117"
              HEIGHT="50"><br><app:storeMessage key="shop.menu.main.faq"/></a>
	</td>
    <td class=mainheadernavspacer><img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_globalspacer.gif")%>' border="0" name="cw_globalspacer" WIDTH="44" HEIGHT="50">
           </td>
	<td><img src='<%=ClwCustomizer.getSIP(request,"cw_globalright.gif")%>' border="0" WIDTH="8" HEIGHT="69"/></td>
  </tr>
</table>
