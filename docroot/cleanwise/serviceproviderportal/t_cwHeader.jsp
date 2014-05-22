<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<!-- bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/ -->


<%
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
String shippingAddress = "";
String addr2 = "";
String customerName = "";
String accountName = "";

if ( null != appUser && null != appUser.getSite() &&
  ShopTool.isAnOCISession(request.getSession()) == false
  ) {
  AddressData address = appUser.getSite().getSiteAddress();

  if ( null != address ) {
    shippingAddress = address.getAddress1()+" ";
    addr2 = address.getAddress2();

    if(addr2!=null && addr2.trim().length()>0 ) shippingAddress+=addr2+" ";
    String addr3 = address.getAddress3();
    if(addr3!=null && addr3.trim().length()>0 ) shippingAddress+=addr3+" ";
    String addr4 = address.getAddress4();
    if(addr4!=null && addr4.trim().length()>0 ) shippingAddress+=addr4+" ";
    shippingAddress+=address.getCity()+", ";
    if (appUser.getUserStore().isStateProvinceRequired()) {
      shippingAddress+= address.getStateProvinceCd() != null ? address.getStateProvinceCd()+" " : "";
    }
    shippingAddress+=address.getPostalCode()+" ";
    shippingAddress+=Constants.getCountryName(address.getCountryCd());
    customerName = appUser.getUser().getFirstName()+" "+appUser.getUser().getLastName()+" ";
    accountName = appUser.getSite().getBusEntity().getShortDesc();
  }

}

%>
<!--PAGESRCID=t_cwHeader.jsp-->
<script language="JavaScript" src="/<%=storeDir%>/externals/shopMenu.js">
</script>


<script language="JavaScript" src="/<%=storeDir%>/externals/shopHelp.js">
</script>
<IFRAME style="display:none; position:absolute" id="orderGuideHelp"
    height="100" width="650" noresize  scrolling="no"
    frameborder="no"
    src="/<%=storeDir%>/externals/orderGuideHelp.html">
</IFRAME>
<IFRAME style="display:none; position:absolute" id="lastOrderHelp"
    height="100" width="650" noresize  scrolling="no"
    frameborder="no"
    src="/<%=storeDir%>/externals/lastOrderHelp.html">
</IFRAME>
<IFRAME style="display:none; position:absolute" id="janitorClosetHelp"
    height="100" width="650" noresize  scrolling="no"
    frameborder="no"
    src="/<%=storeDir%>/externals/janitorClosetHelp.html">
</IFRAME>

<%
String tbarStyle = (String)session.getAttribute("pages.toolbar.style");
if (tbarStyle == null) tbarStyle = "textOnly";
%>

<%
  // Preload the images only if they are needed.
  if (! tbarStyle.equals("textOnly") ) {
%>

<script language="javascript" type="text/javascript" src="/<%=storeDir%>/externals/lib.js"></script>

<% } %>

<table  id="TopHeaderTable"
        align="center"
        border="0"
        cellpadding="0"
        cellspacing="0"
        width="<%=Constants.TABLEWIDTH%>"
        style="{
            border-left:  solid black 1px;
            border-right: solid black 1px;
            border-bottom: solid black 1px;
        }">

<tr class="fivesidemargin shop_header_top_address">
    <td align="right">
    <span class="shop_header_top_address address">
        <app:storeMessage key="shop.header.text.lastLogin"/>
        <b>
            <bean:define id="vLoginDate" name="LoginDate"/><br>
            <%=ClwI18nUtil.formatDate(request,(Date)vLoginDate,DateFormat.MEDIUM)%>
        </b>
    </span>
    </td>
</tr>

</table>


<table id="HeaderTable" align="center" border="0" cellpadding="0"
 cellspacing="0" width="<%=Constants.TABLEWIDTH%>"
 style="border-collapse: collapse;">
<tr valign="top">

<td align="left" rowspan="2" valign="middle">

<img src='<app:custom  pageElement="pages.store.logo1.image" addImagePath="true" encodeForHTML="true"/>' border="0">

<!--
<img src="../en/images/cw_logo.gif" border="0">
-->

</td>

<%--The navigation bar--%>
<td align="right" colspan="4">
    <table cellpadding="0" cellspacing="0" class="headernavtable" style="border-collapse: collapse;">
<%
    if(appUser.isHasNotes()){
%>
        <td align="right">
            <a class="headernavlinkstart"
               href="../userportal/msbnotes.do">
                <app:storeMessage key="shop.header.text.notes"/>
            </a>
        </td>
<%
    }
%>
        <td align="right">
            <a class="headernavlinkstart"
               href="../serviceproviderportal/serviceproviderhome.do?action=contactUs">
                <app:storeMessage key="shop.header.text.contactUs"/></a>
        </td>
        <%
            if (session.getAttribute(Constants.CW_LOGOUT_ENABLED) == null) {
                session.setAttribute(Constants.CW_LOGOUT_ENABLED, "false");
            }
        %> 

        <logic:equal name="<%=Constants.CW_LOGOUT_ENABLED%>" value="true">
            <td align="right">
                <a class="headernavlink" href="../userportal/logoff.do">
                    <app:storeMessage key="shop.header.text.logOut"/></a>
            </td>
        </logic:equal>

        <logic:equal name="<%=Constants.CW_LOGOUT_ENABLED%>" value="false">
            <td align="right">
                <a class="headernavlink" href="../store/shop.do?action=catalog">
                    <app:storeMessage key="shop.header.text.search"/></a>
            </td>
        </logic:equal>

    </table>

</td>
<%--END The navigation bar--%>
</tr>
</table>