
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
    function goto(url, target) {
      if(target != null){
        window.open(url,target);
      }else{
        document.location.href = url;
      }
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
    <td><img src='<%=ClwCustomizer.getSIP(request,"/cw_globalleft.gif")%>' border="0" WIDTH="8" HEIGHT="78"></td>

      <td class="mainheadernav"
          onmouseover="paint('main_menu_shop_text','white','#ff9900');"
          onmouseout="paint('main_menu_shop_text','white','#000000');"
          onclick="goto('<%=shopUrl%>');">
         <% if (Utility.isTrue(enableShop)){%>
          <a class=mainheadernav id="main_menu_shop_text" href="<%=shopUrl%>">
              <img  src='<%=ClwCustomizer.getSIP(request,"/ss_order.jpg" )%>' WIDTH="100" HEIGHT="50"  border="0" name="cw_globalspacer"/>
               <app:storeMessage key="template.xpedx.apple.menu.main.placeOrder"/>
          </a>
          <% } else {%>
    <td class=mainheadernavspacer><img  src='<%=ClwCustomizer.getSIP(request,"cw_globalspacer.gif")%>' border="0" name="cw_globalspacer" WIDTH="85" HEIGHT="50">
    </td><%}%>
      </td>

   <td class=mainheadernav
        onmouseover="paint('main_menu_shop_report_text','white','#ff9900');"
        onmouseout="paint('main_menu_shop_report_text','white','#000000');"
        onclick="goto('../userportal/customerAccountManagement.do');">
        <a id=main_menu_shop_report_text class=mainheadernav href="../userportal/customerAccountManagement.do" >
            <img src='<%=ClwCustomizer.getSIP(request,"/ss_reporting.jpg")%>' border="0" name="cw_global3" WIDTH="96" HEIGHT="49">
            <app:storeMessage key="template.xpedx.apple.menu.main.report"/>
        </a>
    </td>

     <td class=mainheadernav
        onmouseover="paint('main_menu_shop_product_text','white','#ff9900');"
        onmouseout="paint('main_menu_shop_product_text','white','#000000');"
        onclick="goto('../store/shop.do?action=catalog');">
        <a id=main_menu_shop_product_text class=mainheadernav href="../store/shop.do?action=catalog" >
            <img src='<%=ClwCustomizer.getSIP(request,"/ss_product.jpg")%>' border="0" name="cw_global3" WIDTH="100" HEIGHT="49">
            <app:storeMessage key="template.xpedx.apple.menu.main.productInfo"/>
        </a>
    </td>



      <td class=mainheadernav
          onmouseover="paint('main_menu_shop_green_text','white','#ff9900');"
          onmouseout="paint('main_menu_shop_green_text','white','#000000');"
          onclick="goto('../userportal/templator.do?display=f_greencleaning_info&tabs=f_greenCleaningToolbar');">
          <a id=main_menu_shop_green_text class=mainheadernav href="../userportal/templator.do?display=f_greencleaning_info&tabs=f_greenCleaningToolbar" >
          <img src='<%=ClwCustomizer.getSIP(request,"/ss_green.jpg")%>' border="0" name="cw_global3" WIDTH="100" HEIGHT="49">
              <app:storeMessage key="template.xpedx.apple.menu.main.green"/>
          </a>
      </td>


    <td class=mainheadernav
        onmouseover="paint('main_menu_shop_training_text','white','#ff9900');"
        onmouseout="paint('main_menu_shop_training_text','white','#000000');"
        onclick="goto('../userportal/templator.do?display=f_ss_training&tabs=troubleShootingToolBar');">
        <a class=mainheadernav id=main_menu_shop_training_text href="../userportal/templator.do?display=f_ss_training&tabs=troubleShootingToolBar">
            <img  src='<%=ClwCustomizer.getSIP(request,"/ss_training.jpg")%>' border="0" name="cw_global3" WIDTH="100" HEIGHT="49">
            <app:storeMessage key="template.xpedx.apple.menu.main.training"/></a>
    </td>


      <td class=mainheadernav
           onmouseover="paint('main_menu_shop_ss_resources_text','white','#ff9900');"
           onmouseout="paint('main_menu_shop_ss_resources_text','white','#000000');"
           onclick="goto('../userportal/templator.do?display=f_disabled&tabs=f_companyResourcesToolbar');">
           <a class=mainheadernav id=main_menu_shop_ss_resources_text href="../userportal/templator.do?display=f_disabled&tabs=f_companyResourcesToolbar">
               <img  src='<%=ClwCustomizer.getSIP(request,"/ss_resources.jpg")%>' border="0" name="cw_global3" WIDTH="100" HEIGHT="49">
               <app:storeMessage key="template.xpedx.apple.menu.main.companyResources"/></a>
       </td>

   <td class=mainheadernav
        onmouseover="paint('main_menu_shop_msds_text','white','#ff9900');"
        onmouseout="paint('main_menu_shop_msds_text','white','#000000');"
        onclick="goto('../userportal/msdsTemplate.do?tabs=safetyToolBar&display=msdsTemplate');">
        <a id=main_menu_shop_msds_text class=mainheadernav href="../userportal/msdsTemplate.do?tabs=safetyToolBar&display=msdsTemplate">
        <img  src='<%=ClwCustomizer.getSIP(request,"/ss_msds.jpg")%>' border="0" name="cw_global3" WIDTH="100" HEIGHT="49">
        <app:storeMessage key="template.xpedx.apple.menu.main.msds"/></a>
    </td>

    <td><img src='<%=ClwCustomizer.getSIP(request,"/cw_globalright.gif")%>' border="0" WIDTH="8" HEIGHT="78"/></td>
  </tr>
</table>
