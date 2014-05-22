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

 <table class="mainheadernav" id="HelpTable" valign="top" align="center" border="0"
   cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<tr><td colspan="6">&nbsp;</td></tr>
<tr valign="top" align="center">

      <% if (Utility.isTrue(allowAssetManagement)) {%>
      <td class="mainheadernav"
        onmouseover="paint('assettext','#ffffff','#66CC66');"
        onmouseout="paint('assettext','#ffffff','#003399');"
          onclick="goto('../userportal/userAssets.do');">
          <a class=mainheadernav id=assettext href="../userportal/userAssets.do">
              <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"cw_global_asset85x50.jpg")%>' border="0" name="cw_globalspacer"
                   WIDTH="85" HEIGHT="50"><br><app:storeMessage key="shop.menu.main.asset"/>
          </a>
      </td>
    <%}%>

   <% if (Utility.isTrue(enableShop)){%>
    <td class="mainheadernav"
        onmouseover="paint('placeordertext','#ffffff','#66CC66');"
        onmouseout="paint('placeordertext','#ffffff','#003399');"
        onclick="goto('<%=shopUrl%>');">
            <a class=mainheadernav id=placeordertext href="<%=shopUrl%>">
                <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"placeOrder.gif")%>' border="0"
                  name="cw_global1" WIDTH="58" HEIGHT="43"><br><app:storeMessage key="shop.menu.main.placeOrder"/></a>
    </td>
    <%}%>

    <td class=mainheadernav
        onmouseover="paint('reporttext','#ffffff','#66CC66');"
        onmouseout="paint('reporttext','#ffffff','#003399');"
        onclick="goto('../userportal/customerAccountManagement.do');">
        <a id=reporttext class=mainheadernav href="../userportal/customerAccountManagement.do">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"reporting.gif")%>' border="0"
              name="cw_global6"
              WIDTH="46" HEIGHT="43"/><br><app:storeMessage key="shop.menu.main.report"/></a>
    </td>

    <!-- td class=mainheadernav
        onmouseover="paint('productinfotext','#ffffff','#66CC66');"
        onmouseout="paint('productinfotext','#ffffff','#003399');"
        onclick="goto('../userportal/templator.do?tabs=educatorToolBar&display=f_educatorController');">
        <a id=productinfotext class=mainheadernav href="../userportal/templator.do?tabs=educatorToolBar&display=f_educatorController">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"productInfo.gif")%>'
              border="0" name="cw_global3" WIDTH="51" HEIGHT="43"><br><app:storeMessage key="shop.menu.main.productInfo"/></a>
    </td -->

    <td class=mainheadernav
        onmouseover="paint('companytext','#ffffff','#66CC66');"
        onmouseout="paint('companytext','#ffffff','#003399');"
        onclick="goto('../userportal/templator.do?display=t_companyResourcesBody.jsp&tabs=companyResourcesToolBar');">
        <a id=companytext class=mainheadernav href="../userportal/templator.do?display=t_companyResourcesBody.jsp&tabs=companyResourcesToolBar">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"companyResources.gif")%>' border="0"
              name="cw_global3" WIDTH="45" HEIGHT="45"><br><app:storeMessage key="shop.menu.main.companyResources"/></a>
    </td>

    <!-- td class=mainheadernav
        onmouseover="paint('msdstext','#ffffff','#66CC66');"
        onmouseout="paint('msdstext','#ffffff','#003399');"
        onclick="goto('http://www.msdsonfile.com/mctx/msds/msdsonfile.jsp');">
        <a id=msdstext class=mainheadernav href="http://www.msdsonfile.com/mctx/msds/msdsonfile.jsp">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"specifications.gif")%>' border="0"
              name="cw_global3" WIDTH="51" HEIGHT="48"><br><app:storeMessage key="shop.menu.main.msdsSpecs"/></a>
    </td -->

    <td class=mainheadernav
        onmouseover="paint('contactustext','#ffffff','#66CC66');"
        onmouseout="paint('contactustext','#ffffff','#003399');"
        onclick="goto('../userportal/templator.do?display=t_contactUsBody.jsp&tabs=contactUsToolBar');">
        <a id=contactustext class=mainheadernav href="../userportal/templator.do?display=t_contactUsBody.jsp&tabs=contactUsToolBar">
            <img class=mainheadernav src='<%=ClwCustomizer.getSIP(request,"contactUs.gif")%>' border="0"
              name="contactUs" WIDTH="50" HEIGHT="43"/><br><app:storeMessage key="shop.menu.main.contactUsCap"/></a>
    </td>
  </tr>
</table>
