<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.util.Iterator"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="theForm" name="STORE_ADMIN_CATALOG_FORM" type="com.cleanwise.view.forms.StoreCatalogMgrForm"/>
<% 
  CatalogData catalogD = theForm.getCatalogDetail();
  int catalogId = catalogD.getCatalogId();
  String action = theForm.getAction();
%>
<bean:define id="masterCatalogId" name="STORE_ADMIN_CATALOG_FORM" property="masterCatalogId" />
<bean:define id="Location" value="catalog" type="java.lang.String" toScope="session"/>

<%
com.cleanwise.view.forms.StoreCatalogMgrForm catForm =
  (com.cleanwise.view.forms.StoreCatalogMgrForm)
    session.getAttribute("STORE_ADMIN_CATALOG_FORM");
int thisCatalogId = catForm.getCatalogDetail().getCatalogId();
%>

<html:hidden name="STORE_ADMIN_CATALOG_FORM" property="catalogId"
  value="<%= String.valueOf(thisCatalogId) %>" />

<script language="JavaScript1.2">
<!--
function popCatalogLocate(name, pDesc) {
  var loc = "cataloglocate.do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}


function popLocate(pLoc) {
var loc = pLoc;
locatewin = window.open(loc,"Detail", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();

return false;
}

function f_hideBox(boxid) {
  if(document.getElementById(boxid)==null) {
    return;
  }
  document.getElementById(boxid).style.display = 'none';
}

function f_showBox(boxid) {
  document.getElementById(boxid).style.display = 'block';  
}

function catalogTypeChange() {
 <% if("Create New".equals(action)) { %>
  var catalogTypeCd = document.forms[0].elements['catalogDetail.catalogTypeCd'].value;  
  //alert(catalogTypeCd);
  switch (catalogTypeCd) {
  case '<%=RefCodeNames.CATALOG_TYPE_CD.ACCOUNT%>':
    f_showBox("populateCheckBox");
  break;
  default:
    f_hideBox("populateCheckBox");
  }
 <% } else { %> 
    f_hideBox("populateCheckBox");
 <% } %>
 }


//-->

</script>

<div  bgcolor="#cccccc">


<table ID=592 border="0"cellpadding="2" cellspacing="1" width="769"  bgcolor="#cccccc">
<html:form styleId="593" name="STORE_ADMIN_CATALOG_FORM"
           action="/storeportal/storecatalogdet.do"
           scope="session"
           type="com.cleanwise.view.forms.StoreCatalogMgrForm">
<tr>
    <td><b>Catalog ID:</b>
    </td>
    <td><%=catalogId%></td>
    <td> <b>Catalog Name:</b> </td>
    <td>
        <html:text size="30" maxlength="30" name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.shortDesc" /><span class="reqind">*</span>
    </td>
</tr>
<tr>
    <td> <b>Catalog Type:</b> </td>
    <td>
        <logic:equal name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogId" value="0">
            <html:select name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogTypeCd"
                         onchange='catalogTypeChange();'>
                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                <html:options  collection="Catalog.type.vector" property="value" />
            </html:select><span class="reqind">*</span>
        </logic:equal>
        <logic:notEqual name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogId" value="0">
            <bean:write name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogTypeCd"/>
        </logic:notEqual>
    </td>
    <td><b>Catalog Status: </b> </td>
    <td>
        <html:select name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogStatusCd">
            <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
            <html:options  collection="Catalog.status.vector" property="value" />
        </html:select><span class="reqind">*</span>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <%if(!RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(catalogD.getCatalogTypeCd())) { %>
        <b>Locale: </b>
        <html:select name="STORE_ADMIN_CATALOG_FORM" property="contractDetail.localeCd">
			<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
            <% 
			 java.util.HashMap currMap = I18nUtil.getCurrencyHashMap();
			 for(Iterator iter=currMap.keySet().iterator(); iter.hasNext();){
			    Object key = iter.next();
                CurrencyData currency = (CurrencyData) currMap.get(key);
				String localeCd = currency.getLocale();
				String desc = currency.getLocale() + " (" + currency.getGlobalCode()+")";
            %>
            <html:option value="<%=localeCd%>"><%=desc%></html:option>
            <% } %>
        </html:select>
        <% } %>
    </td>
</tr>
<%
    OrderGuideDescDataVector ogdDV = theForm.getOrderGuides();
    if(ogdDV!=null) {
        for(int ii=0; ii<ogdDV.size(); ii++) {
            OrderGuideDescData ogdD = (OrderGuideDescData) ogdDV.get(ii);
            String orderGuideName = "orderGuideName["+ii+"]";



%>

<tr>
    <% if(ii==0) { %>
    <td rowspan='<%=ogdDV.size()%>'><b>Order Guide Name:</b><br>
        <html:submit property="action" value="Add Order Guide" styleClass="text" /></td>
    <% } %>
    <td colspan='3'>
        <html:text property="<%=orderGuideName%>" size="50"/>
    </td>
</tr>
<% } }%>

<tr>
    <td><b>Special Catalog Code:</b></td>
    <td colspan='3'>
        <html:text name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.loaderField" size="50"/>
        <!-- a href="/storeportal/storefreight.do&action=Create Freight Table">Create Freight Table</a -->
    </td>
</tr>

<!-- Freight -->
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.FREIGHT_TABLE_ADMINISTRATION%>">
<tr>
    <td><b>Freight Table:</b></td>
    <td colspan='3'>
        <html:select name="STORE_ADMIN_CATALOG_FORM" property="freightTableId">
            <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
            <html:options  collection="FreightTable.vector"
                           labelProperty="shortDesc" property="freightTableId" />
        </html:select>
        <!-- a href="/storeportal/storefreight.do&action=Create Freight Table">Create Freight Table</a -->
    </td>
</tr>
</app:authorizedForFunction>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.DISCOUNT_ADMINISTRATION%>">
<!-- Discount -->
<tr>
    <td><b>Discount Table:</b></td>
    <td colspan='3'>
        <html:select name="STORE_ADMIN_CATALOG_FORM" property="discountTableId">
            <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
            <html:options collection="Discount.FreightTable.vector" labelProperty="shortDesc" property="freightTableId" />
        </html:select>
    </td>
</tr>
</app:authorizedForFunction>
<logic:notEqual name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogId" value="0">
    <tr>
        <td> <b>Master Catalog Id: </b> </td>
        <td> <bean:write name="STORE_ADMIN_CATALOG_FORM" property="masterCatalogId" filter="true"/>
        </td>
        <td> <b>&nbsp;</b> </td>
        <td>&nbsp;
        </td>
    </tr>
    <tr>
        <td><b>Shipping Message:</b> </td>
        <td colspan=3>
            <html:textarea name="STORE_ADMIN_CATALOG_FORM" cols="60"
                           property="catalogDetail.shippingMessage"/>
        </td>
    </tr>

				<tr>
				<td colspan=3>
					<html:checkbox name="STORE_ADMIN_CATALOG_FORM" property="updatePriceFromLoader"

					  ><b>Update price from loader</b></html:checkbox>
				</td>
				</tr> 
</logic:notEqual>

<logic:equal name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogId" value="0">
    <tr><td colspan="4">
        <html:submit property="action" value="Create Catalog"/>
        <% if(theForm.getFoundCatalogId()>0) { %>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Clone Catalog Prices
        <html:radio property="copyContractFl"  value="copyContractYes"/>
        Yes
        <html:radio property="copyContractFl"  value="copyContractNo"/>
        No
        <span class="reqind">*</span>
        <!--                 <html:checkbox name="STORE_ADMIN_CATALOG_FORM" property="copyContractFl"/> -->
        <% } %>
        <div id='populateCheckBox'>
            Populate Catalog with Items
            <html:checkbox name="STORE_ADMIN_CATALOG_FORM" property="populateCatalogFl"/>
        </div>
    </td>
    </tr>
</logic:equal>

<logic:notEqual name="STORE_ADMIN_CATALOG_FORM" property="catalogDetail.catalogId" value="0">
    <tr>
        <td colspan="4" align="center">
            <html:submit property="action" value="Save Catalog"/>
            <% if(catalogId>0) { %>
            <html:submit property="action" value="Clone Catalog"/>
            <% } %>
            <%
                if(theForm.getMayDelete()) { %>
            <html:submit property="action" value="Delete Catalog"/>
            <% } %>
         	<%--  Start modification to create link from Catalog Tab to ItemManager Tab --%>
         	<a href="/defst/storeportal/catalogItems.do?showCatalog=y&catalogToView=<%=catalogId%>">
         		Edit Catalog Items
         	</a>
         	<!-- end of modification  -->
        </td>
    </tr>
</logic:notEqual>

</table>


</html:form>  
</div>
<script language="JavaScript1.2">
<!--
 catalogTypeChange();
//-->
</script>
