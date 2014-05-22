<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<app:checkLogon/>
<% String storeDir=ClwCustomizer.getStoreDir();
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
%>

<html:html>
<head>
  <title><tiles:getAsString name="title"/></title>
  <!-- meta http-equiv="Content-Type" content="text/html; charset=UTF-8" -->
  <meta http-equiv="Pragma" content="no-cache">
  <meta http-equiv="Expires" content="-1">
  <script src="../externals/lib.js" language="javascript"></script>
  <script src="../externals/table-sort.js" language="javascript"></script>
  <logic:present name="pages.css">
      <logic:equal name="pages.css" value="styles.css">
        <link rel="stylesheet" href='../externals/<app:custom pageElement="pages.css"/>'>
      </logic:equal>
    <logic:notEqual name="pages.css" value="styles.css">
      <link rel="stylesheet" href='../externals/styles.css'>
      <link rel="stylesheet" href='../externals/<app:custom pageElement="pages.css"/>'>
    </logic:notEqual>
  </logic:present>
  <logic:notPresent name="pages.css">
    <link rel="stylesheet" href='../externals/styles.css'>
  </logic:notPresent>
</head>

<body>

<%--render the image and the menuing system--%>
<table ID=523 border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>" style="border-collapse: collapse;">
  <tr valign="top">
    <td align="left" valign="middle">
     <% String storeLogo = (String)session.getAttribute("pages.store.logo1.image");
     
        if (Utility.isSet(storeLogo)) { %>
 <img ID=524 src='<app:custom
  pageElement="pages.store.logo1.image" addImagePath="true" encodeForHTML="true"/>'
  border="0"><BR>
  <% } %>
<%=appUser.getUserStore().getBusEntity().getShortDesc()%>
</td>
    <%--The navigation bar--%>
    <td align="right" colspan="4">
        <jsp:include flush='true' page="/general/navMenu.jsp"/>
      <br>
      <% java.util.Date currd = new java.util.Date(); %>
      <%= currd.toString() %>
      <br>
            <% try { %>
            <b>Server</b> <%=java.net.InetAddress.getLocalHost()%></br>
            <% } catch (Exception e) { %>
             <b>Server</b> N/A</br>
           <% }  %>
            <b>Branch</b> <%=System.getProperty("build.branch.stjohn")%><b>   Build</b> <%=System.getProperty("build.number.stjohn")%>
    </td>
    <%--END The navigation bar--%>
  </tr>
</table>
<%
int headers = -1;
int MAX_HEADERS_ALLOWED = 7;
%>

<table ID=525 width="<%=Constants.TABLEWIDTH%>" cellpadding="0" cellspacing="0" >
  <tr>
     <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_STORE%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers=0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="storeStoreMgr.do" name="Store" tabClassOff="atoff"
          tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="storeStore"/>
    </app:authorizedForFunction>

    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_ITEM%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers=0;
      %>
  </tr>
  <tr>
      <% } %>
      <app:renderStatefulButton link="searchItems.do" name="Master Items" tabClassOff="atoff"
          tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="items,categories,item-edit,storeitemupload"/>
    </app:authorizedForFunction>

    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_MASTER_ASSETS%>">
      <% if (appUser.getUserStore().isAllowAssetManagement()){ %>
   	  <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers=0;
      %>
  </tr>
  <tr>
      <% } %>
      <app:renderStatefulButton link="searchMasterAssets.do" name="Master Assets" tabClassOff="atoff"
          tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="masterAsset"/>
      <%}%>
    </app:authorizedForFunction>

  <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_ASSET%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers=0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="storeAssetSearch.do" name="Assets" tabClassOff="atoff"
          tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"
                 contains="storeAssetSearch,storeAssetDetail,storeAssetConfig,storeAssetContent"/>
      </app:authorizedForFunction>

   <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_WARRANTY%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers=0;
      %>
  </tr>
  <tr>
      <% } %>
      <app:renderStatefulButton link="storeWarrantySearch.do" name="Warranty" tabClassOff="atoff"
                                tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"
                                contains="storeWarrantySearch,storeWarrantyDetail,storeWarrantyNote,storeWarrantyContent,storeWarrantyConfig,storeAssetWarrantyConfigDetail"/>
    </app:authorizedForFunction>

    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_CATALOG%>">
    <% if (appUser.isaDistributor() || !appUser.getUserStore().getStoreType().equals(RefCodeNames.STORE_TYPE_CD.ENTERPRISE)){ %>
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers=0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="catalogs.do" name="Catalogs" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="catalog"/>
    <%}%>
    </app:authorizedForFunction>


    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.FREIGHT_TABLE_ADMINISTRATION%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers=0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="storefreightsearch.do" name="Freight Tables" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="storefreight"/>
    </app:authorizedForFunction>

    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.DISCOUNT_ADMINISTRATION%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers=0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="storeDiscountFreightSearch.do" name="Discount Tables" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="storeDiscountFreight"/>
    </app:authorizedForFunction>


    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_ITEM_MANAGER%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers=0;
      %>
  </tr>
  <tr>
      <% } %>
      <%
        String itemManagerPage = (String) session.getAttribute(SessionAttributes.ITEM_MANAGER_PAGE.CURRENT_PAGE);
        if("cat-item".equals(itemManagerPage)) {
      %>
        <app:renderStatefulButton link="cat-item.do" name="Item Manager" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="itemcontract,cat-item"/>
      <% } else { %>
        <app:renderStatefulButton link="itemcontract.do" name="Item Manager" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="itemcontract,cat-item"/>
      <% } %>
    </app:authorizedForFunction>

	<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_VENDOR_INVOICE%>">
    <% if (appUser.isaDistributor() || !appUser.getUserStore().getStoreType().equals(RefCodeNames.STORE_TYPE_CD.ENTERPRISE)){ %>
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="invoicesVendor.do" name="Vendor Invoices" tabClassOff="atoff"
          tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>
      <%}%>
    </app:authorizedForFunction>

	<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_ACCOUNT%>">
    <% if (appUser.isaDistributor() || !appUser.getUserStore().getStoreType().equals(RefCodeNames.STORE_TYPE_CD.ENTERPRISE)){ %>
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="accountsearch.do" name="Accounts" tabClassOff="atoff"
          tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"
                 contains="accountsearch,storeAccountDetail,storeAccountBillTos,storeAccountConfiguration,storeAccountSiteData,storeAccountWorkflow,storeInventoryMgr,storeAccountUIConfig,storePipelineMgr,storeDeliverySchedules,accountShoppingRestrictions,accountFiscalCalendar,accountmgrNote,accountmgrContactUsTopic,accountmgrProductUITemplate,accountmgrServiceFee,accountMgrCheckoutData"/>
      <%}%>
    </app:authorizedForFunction>

    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_SITE%>">
    <% if (appUser.isaDistributor() || !appUser.getUserStore().getStoreType().equals(RefCodeNames.STORE_TYPE_CD.ENTERPRISE)){ %>
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="sitesearch.do" name="Sites" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"
            contains="sitesearch,sitedet,siteconfig,siteworkflow,sitenote,siteprof,sitebudgets,orderguidesearch,orderguidedet,orderguidefinditems,orderguidenew,storesiteadjustmentledger"/>
      <%}%>
    </app:authorizedForFunction>

	<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_USER%>">
    <% if (appUser.isaDistributor() || !appUser.getUserStore().getStoreType().equals(RefCodeNames.STORE_TYPE_CD.ENTERPRISE)){ %>
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="usersearch.do" name="Users" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"
            contains="usersearch,userdet,userconfig"/>
      <%}%>
    </app:authorizedForFunction>

    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_RGA_USER%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
            <app:renderStatefulButton link="storeReturnsOp.do" name="RGA" tabClassOff="atoff"
                tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>
     </app:authorizedForFunction>

     <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_PO_USER%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
         <app:renderStatefulButton link="storePurchaseOrderMgr.do" name="POs" tabClassOff="atoff"
                tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"
                contains="storePurchaseOrderMgr,storePurchaseOrderMgrOpDetail,storePurchaseOrderLineTrackerOp"/>
     </app:authorizedForFunction>

     <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_COST_CENTER%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="costcenters.do?action=searchInit"
            name="Cost Centers" tabClassOff="atoff"
            contains="costcenterdet,costcenters,costcenterconf"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>
     </app:authorizedForFunction>

     <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_DIST%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="distmgr.do" name="Distributor" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="distmgr,distdet,distconf,distitem,distterr,dlschdlmgr,dlschdldet,distnote,tradingprofile"/>
     </app:authorizedForFunction>

     <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_MANUF%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="manufmgr.do" name="Manufacturer" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="manufmgr,manufdet"/>
     </app:authorizedForFunction>

     <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_GROUP%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="storeGroupMgr.do?action=init"
                                name="Groups" tabClassOff="atoff"
                                tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="storeGroupMgr,storeGroupDetail,storeGroupMgrConfig,storeGroupMgrUser"/>
     </app:authorizedForFunction>

     <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_ORDERS%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
          <app:renderStatefulButton link="storeOrder.do" name="Orders" tabClassOff="atoff"
                                tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>
      </app:authorizedForFunction>

	  <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_PROFILES%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
	    <app:renderStatefulButton link="storeProfilingMgr.do" name="Profiles" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>
	  </app:authorizedForFunction>

      <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_CUST_BLANKET_PO%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
         <app:renderStatefulButton link="blanketPo.do" name="Cust Blanket Pos" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>
      </app:authorizedForFunction>

      <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_ENTERPRISE%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
          <app:renderStatefulButton link="storeEnterpriseMgr.do" name="Enterprise" tabClassOff="atoff"
                                tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"
                                contains="storeEnterpriseMgr,storeEnterpriseMfg"/>
      </app:authorizedForFunction>

      <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_SERVICE_PROVIDER%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
            <app:renderStatefulButton link="sprmgr.do" name="Service Provider" tabClassOff="atoff"
              tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="sprmgr,sprdet"/>
        </app:authorizedForFunction>

      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="userProfile.do?action=customer_profile" name="My Password" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>


	    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_FREIGHT_HANDLER%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
            <app:renderStatefulButton link="fhmgr.do" name="Freight Handlers" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>
        </app:authorizedForFunction>

        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_CONTRACTOR%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
          	<app:renderStatefulButton link="contractormgr.do" name="Contractors" tabClassOff="atoff"
              	tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="contractormgr,contractordet"/>
        </app:authorizedForFunction>

        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_SERVICE_PROVIDER_TYPE%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
            <app:renderStatefulButton link="serviceProviderType.do" name="Service Provider Types" tabClassOff="atoff"
                tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>
        </app:authorizedForFunction>


	  <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_ORDER_TRACKING%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
	    <app:renderStatefulButton link="orderOp.do" name="Order Tracking" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>
      </app:authorizedForFunction>
<%--
      <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_EXCEPTIONS%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
          <app:renderStatefulButton link="exceptionOp.do" name="Exceptions" tabClassOff="atoff"
                tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>
      </app:authorizedForFunction>
--%>
	 <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_CUST_INVOICE%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="invoiceCustOp.do" name="Customer Invoices" tabClassOff="atoff"
          tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="invoiceCustOp"/>
     </app:authorizedForFunction>

	 <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_MESSAGES%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
        <app:renderStatefulButton link="msgsearch.do" name="Messages" tabClassOff="atoff"
          tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar" contains="msgsearch,msgdetail,msgconfig"/>
     </app:authorizedForFunction>
<% //////////////// %>
     <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.STORE_MGR_TAB_CORPORATE_SCHEDULES%>">
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>
  <tr>
      <% } %>
            <app:renderStatefulButton link="corporateSchedule.do" name="Corporate Schedules" tabClassOff="atoff"
                tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>
        </app:authorizedForFunction>
<% //////////////// %>
      <% headers++;
         if (headers > MAX_HEADERS_ALLOWED) {
           headers = 0;
      %>
  </tr>



  <tr>
      <% } %>
        <app:renderStatefulButton link="help.do" name="Help" tabClassOff="atoff"
            tabClassOn="aton" linkClassOff="tbar2" linkClassOn="tbar"/>

 <% while ( headers < MAX_HEADERS_ALLOWED ) { %>
    <% headers++; %>
    <td class="atoff">&nbsp;</td>
 <% } %>
      </tr><tr>
  </tr>

</table>
<%--insert any sub menu if present --%>
<tiles:useAttribute id="subMenu" name="subMenu" ignore="true" classname="java.lang.String"/>
<logic:present name="subMenu">
  <tiles:insert attribute="subMenu"/>
</logic:present>

<%--display any errors if present--%>
<table ID=526 width="<%=Constants.TABLEWIDTH%>">
  <tr>
    <td align="center">
      <div id="adminLayoutErrorSection" class="text"><font color=red><html:errors/></font></div>
    </td>
  </tr>
</table>

<%--insert the body content --%>
<tiles:insert attribute="body"/>


<%--render the footer --%>
<table ID=527 border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <tr>
    <td align="left">
        <app:custom pageElement="pages.footer.msg"/>
    </td>
  </tr>
</table>
</body>
</html:html>
