
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="theForm" name="STORE_STORE_DETAIL_FORM" type="com.cleanwise.view.forms.StoreStoreMgrDetailForm"/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="store" type="java.lang.String" toScope="session"/>
<html:html>

<head>
    <link rel="stylesheet" href="../externals/styles.css">
    <title>Application Administrator Home: Stores</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<bean:define id="BusEntityId" type="java.lang.String"
             name="STORE_STORE_DETAIL_FORM" property="id"
             toScope="session" />
<bean:define id="BusEntityName" type="java.lang.String"
             name="STORE_STORE_DETAIL_FORM" property="name"
             toScope="session" />


<html:form styleId="1314" name="STORE_STORE_DETAIL_FORM" action="storeportal/storeStoreMgrDetail.do"
           scope="session" type="com.cleanwise.view.forms.StoreStoreMgrDetailForm">



<div class="text">


<table ID=1315 cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH%>"  class="mainbody">


<tr>
    <td class="largeheader" colspan="4">Store Detail</td>
</tr>
<tr>
    <td><b>Store&nbsp;Id:</b></td>
    <td>&nbsp;
        <logic:notEqual name="STORE_STORE_DETAIL_FORM" property="id" value="0">
            <bean:write name="STORE_STORE_DETAIL_FORM" property="id"/>
        </logic:notEqual>
        <html:hidden property="id"/>
    </td>
    <td><b>Name:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="name" size="30" maxlength="30"/>
        <span class="reqind">*</span>
    </td>
</tr>
<tr>
    <td><b>Store&nbsp;Prefix:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="prefix" size="30" maxlength="80"/>
    </td>
    <td><b>Description:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="description" size="40" maxlength="80"/>
    </td>
</tr>
 <%--<tr>
    <td><b>Store&nbsp;Prefix&nbsp;New:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="prefixNew" size="30" maxlength="80"/>
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>--%>
<tr>
    <td><b>Store&nbsp;Type:</b></td>
    <td>
        <html:select name="STORE_STORE_DETAIL_FORM" property="typeDesc">
            <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
            <html:options  collection="Store.type.vector" property="value" />
        </html:select>
        <span class="reqind">*</span>
    </td>
    <td><b>Status:</b></td>
    <td>
        <html:select name="STORE_STORE_DETAIL_FORM" property="statusCd">
            <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
            <html:options  collection="Store.status.vector" property="value" />
        </html:select>
        <span class="reqind">*</span>
    </td>
</tr>
<tr>
    <td><b>Customer&nbsp;Service&nbsp;Email:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="customerEmail" size="30" maxlength="80"/>
        <span class="reqind">*</span> Ex:&nbsp;James&lt;default@xpedx.com&gt;
    </td>
    <td><b>Default Locale:</b></td>
    <td>
        <html:select name="STORE_STORE_DETAIL_FORM" property="locale">
            <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
            <html:options  collection="Store.locale.vector" property="value" />
        </html:select>
        <span class="reqind">*</span>
    </td>
</tr>
<tr>
    <td><b>Contact&nbsp;Us&nbsp;Email:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="contactEmail" size="30" maxlength="80"/>
        <span class="reqind">*</span> Ex:&nbsp;James&lt;default@xpedx.com&gt;
    </td>
    <logic:equal name="STORE_STORE_DETAIL_FORM" property="parentStore" value="true">
        <td colspan="2"></td>
    </logic:equal>
    <logic:notEqual name="STORE_STORE_DETAIL_FORM" property="parentStore" value="true">
        <td>
            <b><app:storeMessage key="store.property.parentStoreId"/>:</b>

        </td>
        <td align="left">
            <html:text name="STORE_STORE_DETAIL_FORM" property="parentStoreId" size="10" maxlength="30"/>
            <logic:greaterThan name="STORE_STORE_DETAIL_FORM" property="id" value="0">
                <html:submit property="action">
                    <app:storeMessage  key="admin.button.synchronize"/>
                </html:submit>
            </logic:greaterThan>
        </td>
    </logic:notEqual>
</tr>
<tr>
    <td><b>Default&nbsp;Email:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="defaultEmail" size="30" maxlength="80"/>
        <span class="reqind">*</span> Ex:&nbsp;James&lt;default@xpedx.com&gt;
    </td>
    <td><b>Alternate&nbsp;UI:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="alternateUI" size="30" maxlength="80"/>
    </td>
</tr>
<tr>
    <td><b>Store Business Name</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="storeBusinessName" size="30" maxlength="80"/>
    </td>
    <td><b>Store Primary Web Address</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="storePrimaryWebAddress" size="30" maxlength="80"/>
    </td>
    <td colspan="2">&nbsp;</td>
</tr>
<tr>
    <td><b>Store Contact Us Page Type</b></td>
    <td>
        <html:select name="STORE_STORE_DETAIL_FORM" property="contactUsType">
            <html:options  collection="contact.type.vector" property="value" />
        </html:select>
        <span class="reqind">*</span>
    </td>
    <td><b>Call Hours</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="callHours" size="30" maxlength="30"/>
        <span class="reqind">*</span>
    </td>
</tr>
<tr>
    <td><b>Even Row Color</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="evenRowColor" size="6"/>
    </td>
    <td><b>Odd Row Color</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="oddRowColor" size="6"/>
    </td>
</tr>
<tr>
    <%if(!Utility.isSet(theForm.getParentStoreId())) {%>
    <td>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="parentStore"/>&nbsp;
        <b><app:storeMessage key="store.property.parentStore"/></b>
    </td>
    <td colspan="3"></td>
    <%} else {%>
        <td colspan="4"></td>
    <%}%>
</tr>
<tr>
    <td><b>Google Analytics ID</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="googleAnalyticsId"
        	size="30" maxlength="40"/>
    </td>
    <td><b>User Name Max Size</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="userNameMaxSize"
        	size="30" maxlength="30"/>
    </td>
</tr>
     <td><b>Datasource</b></td>
     <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="storeDatasource"
        	size="30" maxlength="40"/>
        <% if ("yes".equals(System.getProperty("multi.store.db"))){ // value of storeDatasource must be present in case of multi store DB%>
              <span class="reqind">*</span>
        <% } %>	
     </td>     
<tr>

</tr>


<tr>
    <td colspan=4 class="largeheader"><br>Primary Contact</td>
</tr>
<tr>
    <td><b>First Name:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="name1" maxlength="30"/>
    </td>
    <td><b>Phone:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="phone" maxlength="15"/>
    </td>
</tr>
<tr>
    <td><b>Last Name:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="name2" maxlength="30"/>
    </td>
    <td><b>Fax:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="fax" maxlength="15"/>
    </td>
</tr>
<tr>
    <td><b>Street Address 1:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="streetAddr1" maxlength="80"/>
    </td>
    <td><b>Email:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="emailAddress" maxlength="40"/>
        Ex:&nbsp;James&lt;default@xpedx.com&gt;
    </td>
</tr>
<tr>
    <td><b>Street Address 2:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="streetAddr2" maxlength="80"/>
    </td>
    <td><b>Country:</b></td>
    <td>
        <html:select name="STORE_STORE_DETAIL_FORM" property="country">
            <html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
            <html:options  collection="country.vector" labelProperty="uiName" property="shortDesc" />
        </html:select>
    </td>
</tr>
<tr>
    <td><b>Street Address 3:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="streetAddr3" maxlength="80"/>
    </td>

    <td><b>State/Province:</b></td>
    <td>
        <html:text size="20" maxlength="80" name="STORE_STORE_DETAIL_FORM"
                   property="stateOrProv" />
    </td>
</tr>
<tr>
    <td><b>City:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="city" maxlength="40"/>
    </td>

    <td><b>Zip/Postal Code:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="postalCode" maxlength="15"/>
    </td>
</tr>
<tr>
    <td colspan='2'>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="cleanwiseOrderNumFlag"/>
        <b>Use System Shared Order Number</b>
        <BR>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="autoSkuAssign"/>
        <b>Automatically Assign Sku Numbers</b>
        <BR>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="equalCostAndPrice"/>
        <b>Set Cost and Price Equal</b>
        <BR>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="requireExternalSysLogon"/>
        <b>Require that a customer user type (i.e. MSB Customer) logs in through an external system (NOT our logon page)</b>
        <BR>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="includeAccountNameInSiteAddress"/>
        <b>When sending the site shipping to address include the account name as part of the address</b>
        <BR>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="requireErpAccountNumber"/>
        <b>Vendor Invoice: Require ERP Account Number</b>
        <BR>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="useXiPay"/>
        <b>Use XiPay Web Service</b>
        <BR>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="allowMixedCategoryAndItemUnderSameParent"/>
        <b>Allow mixed category and items in category hierarchy</b>
        <BR>
    </td>
    <td valign="top"><b>Erp System:</b></td>
    <td>
        <html:select name="STORE_STORE_DETAIL_FORM" property="erpSystem">
            <html:option value="<%=RefCodeNames.ERP_SYSTEM_CD.CLW_JCI%>"><%=RefCodeNames.ERP_SYSTEM_CD.CLW_JCI%></html:option>
            <html:option value="<%=RefCodeNames.ERP_SYSTEM_CD.SELF_SERVICE%>"><%=RefCodeNames.ERP_SYSTEM_CD.SELF_SERVICE%></html:option>
        </html:select>
        <BR>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="showDistrNotes"/>
        <b>Show distributor PO notes to the customer</b>
        <BR>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="taxableIndicator"/>
        <b>Taxable</b>
        <BR>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="orderProcessingSplitTaxExemptOrders"/>
        <b>Split taxable and non-taxable orders into 2 seperate orders</b>
        <BR>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="allowPONumberByVendor"/>
        <b>Allow PO Number for different vendors</b>
		<BR>
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="allowSpecPermissionItemsMgt"/>
        <b>Allow Special Permission Items</b>
        <BR>
		<html:checkbox name="STORE_STORE_DETAIL_FORM" property="orderGuideNotReqd"/>
        <b>Order Guide NOT required for Catalog</b>
        <BR>
		<html:checkbox name="STORE_STORE_DETAIL_FORM" property="stageUnmatched"/>
        <b><app:storeMessage key="store.property.stageUnmatched"/></b>
    </td>
</tr>
<tr>
<td><b>Pending Order Notification:</b></td>
<td colspan="3"><html:text name="STORE_STORE_DETAIL_FORM" property="pendingOrderNotification"
 size="90" maxlength="4000"/></td>
<tr>
    <td colspan="4" class="largeheader">
        <br>Asset Management
    </td>
</tr>
<tr>
    <td><b>Work Order Email Address:</b></td>
    <td>
        <html:text name="STORE_STORE_DETAIL_FORM" property="workOrderEmailAddress" size="30" maxlength="80"/>
    </td>
    <td colspan="2"></td>
</tr>
<tr>
    <td colspan="2">
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="allowAssetManagement"/>
        <b>Allow asset management</b>
    </td>
    <td colspan="2">
    </td>
</tr>
<tr>
    <td colspan="2">
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="displayDistributorAccountReferenceNumber"/>
        <b>Display Distributor Account Reference Number</b>
    </td>
    <td colspan="2">
    </td>
</tr>
<tr>
    <td colspan="2">
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="displayDistributorSiteReferenceNumber"/>
        <b>Display Distributor Site Reference Number</b>
    </td>
    <td colspan="2">
    </td>
</tr>
<tr>
    <td colspan="2">
        <html:checkbox name="STORE_STORE_DETAIL_FORM" property="budgetThresholdFl"/>
        <b>Budget Threshold</b>
    </td>
    <td colspan="2">
    </td>
</tr>
            <tr>
            	<td class="largeheader" align=left colspan="4">
            		<br>Email Templates
            	</td>
            </tr>
            <tr>
                <td>
                	<b>Order Confirmation Email Template:</b>
                </td>
                <td colspan="3">
                	<html:select name="STORE_STORE_DETAIL_FORM" property="orderConfirmationEmailTemplate">
		           		<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				   		<html:optionsCollection property="emailTemplateChoices" label="value"/>
		        	</html:select>
		        </td>
            </tr>
            <tr>
                <td>
                	<b>Shipping Notification Email Template:</b>
                </td>
                <td colspan="3">
                	<html:select name="STORE_STORE_DETAIL_FORM" property="shippingNotificationEmailTemplate">
		           		<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				   		<html:optionsCollection property="emailTemplateChoices" label="value"/>
		        	</html:select>
		        </td>
            </tr>
            <tr>
                <td>
                	<b>Pending Approval Email Template:</b>
                </td>
                <td colspan="3">
                	<html:select name="STORE_STORE_DETAIL_FORM" property="pendingApprovalEmailTemplate">
		           		<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				   		<html:optionsCollection property="emailTemplateChoices" label="value"/>
		        	</html:select>
		        </td>
            </tr>
            <tr>
                <td>
                	<b>Rejected Order Email Template:</b>
                </td>
                <td colspan="3">
                	<html:select name="STORE_STORE_DETAIL_FORM" property="rejectedOrderEmailTemplate">
		           		<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				   		<html:optionsCollection property="emailTemplateChoices" label="value"/>
		        	</html:select>
		        </td>
            </tr>
            <tr>
                <td>
                	<b>Modified Order Email Template:</b>
                </td>
                <td colspan="3">
                	<html:select name="STORE_STORE_DETAIL_FORM" property="modifiedOrderEmailTemplate">
		           		<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				   		<html:optionsCollection property="emailTemplateChoices" label="value"/>
		        	</html:select>
		        </td>
            </tr>

<tr>
    <td colspan=4 align=center>
        <html:submit property="action">
            <app:storeMessage  key="global.action.label.save"/>
        </html:submit>
        <html:reset>
            <app:storeMessage  key="admin.button.reset"/>
        </html:reset>
        <logic:notEqual name="STORE_STORE_DETAIL_FORM" property="id" value="0">
            <html:submit property="action">
                <app:storeMessage  key="global.action.label.delete"/>
            </html:submit>
        </logic:notEqual>
    </td>
</tr>


</table>

</div>

</html:form>
</body>

</html:html>



