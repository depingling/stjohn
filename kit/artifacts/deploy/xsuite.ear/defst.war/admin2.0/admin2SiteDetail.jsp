<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.IdVector" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.Admin2Tool" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/app-interface.tld" prefix="ui" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="ADMIN2_SITE_DETAIL_MGR_FORM" type="com.cleanwise.view.forms.Admin2SiteDetailMgrForm"/>
<bean:define id="Location" value="site" type="java.lang.String" toScope="session"/>
<html:html>

<head>


</head>

<body>
<ui:page name="<%=RefCodeNames.UI_PAGE_CD.SITE_DETAIL%>">

<div class="text">

<logic:equal  name="ADMIN2_SITE_DETAIL_MGR_FORM" property="accountId" value="0">
    <logic:notEqual name="<%=Constants.APP_USER%>" property="aAccountAdmin" value="true">
           <logic:equal name="<%=Constants.APP_USER%>" property="aAdmin" value="true">
            <jsp:include flush='true' page="/admin2.0/locateAdmin2Account.jsp">
                <jsp:param name="jspFormAction" 	value="/admin2.0/admin2SiteDetail.do" />
                <jsp:param name="jspFormName" 	value="ADMIN2_SITE_DETAIL_MGR_FORM" />
                <jsp:param name="jspSubmitIdent" 	value="" />
                <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
            </jsp:include>
        </logic:equal>
    </logic:notEqual>
</logic:equal>

<table cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH800%>"  class="mainbody">

<html:form  styleId="ADMIN2_SITE_DETAIL_MGR_FORM_ID"
            name="ADMIN2_SITE_DETAIL_MGR_FORM"
            scope="session"
            action="admin2.0/admin2SiteDetail.do"
            scope="session"
            type="com.cleanwise.view.forms.Admin2SiteDetailMgrForm">

<html:hidden name="ADMIN2_SITE_DETAIL_MGR_FORM" property="oldSiteNumber"/>



<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_INF%>">
    <tr>
        <td><b><app:storeMessage key="admin2.site.detail.label.accountId"/>:</b></td>
        <td>
            <logic:equal  name="ADMIN2_SITE_DETAIL_MGR_FORM" property="accountId" value="0">
                <logic:notEqual name="<%=Constants.APP_USER%>" property="aAccountAdmin" value="true">
                    <logic:equal name="<%=Constants.APP_USER%>" property="aAdmin" value="true">
                        <html:button property="action"
                                     onclick="adm2Submit('ADMIN2_SITE_DETAIL_MGR_FORM_ID','action','Locate Account')"
                                     styleClass='text'>
                            <app:storeMessage key="admin2.button.locateAccount"/>
                        </html:button>
                    </logic:equal>
                </logic:notEqual>
            </logic:equal>
            <logic:notEqual name="ADMIN2_SITE_DETAIL_MGR_FORM" property="accountId" value="0">
                <bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="accountId"/>
            </logic:notEqual>
        </td>
        <td><b><app:storeMessage key="admin2.site.detail.label.accountName"/>:</b></td>
        <td>
            <logic:notEqual name="ADMIN2_SITE_DETAIL_MGR_FORM" property="accountId" value="0">
                <bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="accountName"/>
            </logic:notEqual>
        </td>
    </tr>

</ui:control>

<logic:notEqual name="ADMIN2_SITE_DETAIL_MGR_FORM" property="accountId" value="0">
    <html:hidden name="ADMIN2_SITE_DETAIL_MGR_FORM" property="accountId"/>
    <html:hidden name="ADMIN2_SITE_DETAIL_MGR_FORM" property="accountName"/>
</logic:notEqual>

<tr>
    <td class="largeheader" colspan="4"><app:storeMessage key="admin2.site.detail.text.siteDetail"/></td>
</tr>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ID_AND_NAME%>">
    <tr>
        <td><b><app:storeMessage key="admin2.site.detail.label.siteId"/>:</b> </td>
        <td>
            <bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="id"/>
        </td>
        <td><b><app:storeMessage key="admin2.site.detail.label.siteName"/>:</b><span class="reqind">*</span></td>
        <td>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.NAME_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="name">
                <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="name" size="30" maxlength="30"/>
            </ui:element>
        </td>
    </tr>
</ui:control>

<html:hidden property="id"/>

<tr>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.STATUS%>" template="<td></td><td></td>">
        <td><b><app:storeMessage key="admin2.site.detail.label.siteStatus"/>:</b><span class="reqind">*</span>
        </td>
        <td>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="statusCd">
                <html:select name="ADMIN2_SITE_DETAIL_MGR_FORM" property="statusCd">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                 <logic:present name="<%=Admin2Tool.FORM_VECTORS.BUS_ENTITY_STATUS_CD%>">
                    <logic:iterate id="statusRefCd" name="<%=Admin2Tool.FORM_VECTORS.BUS_ENTITY_STATUS_CD%>" type="com.cleanwise.service.api.value.RefCdData">
                        <%String messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.BUS_ENTITY_STATUS_CD." + ((String) statusRefCd.getValue()).toUpperCase());%>
                        <html:option value="<%=statusRefCd.getValue()%>">
                            <% if (messageKey != null) {%>
                            <%=messageKey%>
                            <%} else {%>
                            <%=statusRefCd.getValue()%>
                            <%}%>
                        </html:option>
                    </logic:iterate>
                </logic:present>
                </html:select>
            </ui:element>
        </td>
    </ui:control>


    <ui:control name="<%=RefCodeNames.UI_CONTROL.BSC%>" template="<td></td><td></td>">
        <td><b><app:storeMessage key="admin2.site.detail.label.sitebsc"/>:</b></td>
        <td>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.BSC_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="subContractor">
                <html:select name="ADMIN2_SITE_DETAIL_MGR_FORM" property="subContractor">
                    <html:option value=""><app:storeMessage  key="admin2.select"/></html:option>
                    <html:options  collection="admin2.list.all.bsc" property="busEntityData.shortDesc" />
                </html:select>
            </ui:element>
        </td>
    </ui:control>

</tr>
<tr>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.EFF_DATE%>" template="<td></td><td></td>">
        <td><b><app:storeMessage key="admin2.site.detail.label.siteEffDate"/>:</b> </td>
        <td>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.EFF_DATE_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="effDate">
                <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="effDate" size="30"/>
            </ui:element>
        </td>
    </ui:control>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.EXP_DATE%>" template="<td></td><td></td>">
        <td><b><app:storeMessage key="admin2.site.detail.label.siteExpDate"/>:</b> </td>
        <td>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.EXP_DATE_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="expDate">
                <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="expDate" size="30"/>
            </ui:element>
        </td>
    </ui:control>

</tr>

<tr>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.BUD_REF_NUM%>" template="<td></td><td></td>">
        <td><b><app:storeMessage key="admin2.site.detail.label.siteBudgetRefNum"/>:</b></td>
        <td>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.BUD_REF_NUM_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteReferenceNumber">
                <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteReferenceNumber" size="30"/>
            </ui:element>
        </td>
    </ui:control>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.DISTR_BUD_REF_NUM%>" template="<td></td><td></td>">
        <td><b><app:storeMessage key="admin2.site.detail.label.distrBudgetRefNum"/>:</b></td>
        <td>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.DISTR_BUD_REF_NUM_VAL%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="distSiteReferenceNumber">
                <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="distSiteReferenceNumber" size="30"/>
            </ui:element>
        </td>
    </ui:control>

</tr>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ERP_NUMBER%>">
    <tr>
        <td><b><app:storeMessage key="admin2.site.detail.label.siteErpNum"/>:</b></td>
        <td colspan="3">
            <%
                if(Utility.isTrue(theForm.isERPEnabled())) {

            %>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ERP_NUMBER_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteNumber">
                <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteNumber" size="10" maxlength="30"/>
            </ui:element>
            <%
                IdVector shiptos = theForm.getAvailableShipto();
                if(shiptos!=null) {
                    String shiptosStr = IdVector.toCommaString(shiptos);
            %>
            <app:storeMessage key="admin2.site.detail.text.availableNumbers"/>: <%=shiptosStr%>
            <% } %>
            <% } else { %>
            <bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteNumber"/>
            <% } %>
        </td>
    </tr>
</ui:control>

<tr>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.TAXABLE%>" template="<td></td><td></td>">
        <td><b><app:storeMessage key="admin2.site.detail.label.taxable"/>:</b></td>
        <td>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.TAXABLE_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="taxableIndicator">
                <app:storeMessage key="admin2.site.detail.text.yes"/>&nbsp;<html:radio name="ADMIN2_SITE_DETAIL_MGR_FORM" property="taxableIndicator" value="Y"/>
                &nbsp;&nbsp;&nbsp;
                <app:storeMessage key="admin2.site.detail.text.no"/>&nbsp;<html:radio name="ADMIN2_SITE_DETAIL_MGR_FORM" property="taxableIndicator" value="N"/>
            </ui:element>
        </td>
    </ui:control>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.ENABLE_INV%>" template="<td colspan=2></td>">
        <td colspan=2>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.UI_ENABLE_INV_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="inventoryShopping">
                <html:checkbox name="ADMIN2_SITE_DETAIL_MGR_FORM" property="inventoryShopping" />
            </ui:element><b><app:storeMessage key="admin2.site.detail.label.enableInventoryShopping"/></b>
        </td>
    </ui:control>

</tr>

<ui:control name="<%=RefCodeNames.UI_CONTROL.INV_SHOP_TYPE%>">
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan=2>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_TYPE_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="inventoryShoppingType">
                <html:checkbox name="ADMIN2_SITE_DETAIL_MGR_FORM" property="inventoryShoppingType" />
            </ui:element><b><app:storeMessage key="admin2.site.detail.label.inventoryShoppingType"/></b>
        </td>
    </tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.INV_SHOP_HOLD_ORDER_UDD%>">
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan=2>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_HOLD_ORDER_UDD_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="inventoryShoppingHoldOrderUntilDeliveryDate">
                <html:checkbox name="ADMIN2_SITE_DETAIL_MGR_FORM" property="inventoryShoppingHoldOrderUntilDeliveryDate" />
            </ui:element><b><app:storeMessage key="admin2.site.detail.label.holdProcessedOrderUntilDeliveryDate"/></b>
        </td>
    </tr>
</ui:control>

<tr>
    <ui:control name="<%=RefCodeNames.UI_CONTROL.TARGET_FACILITY_RANK%>" template="<td></td><td></td>">
        <td><b><app:storeMessage key="admin2.site.detail.label.targetFacilityRank"/>:</b></td>
        <td>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.TARGET_FACILITY_RANK_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="targetFacilityRank">
                <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="targetFacilityRank" size="10" maxlength="30"/>
            </ui:element>
        </td>
    </ui:control>
    <ui:control name="<%=RefCodeNames.UI_CONTROL.BYPASS_ORDER_ROUTING%>" template="<td colspan=2></td>">
        <td colspan=2>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.BYPASS_ORDER_ROUTING_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="bypassOrderRouting">
                <html:checkbox name="ADMIN2_SITE_DETAIL_MGR_FORM" property="bypassOrderRouting" />
            </ui:element><b><app:storeMessage key="admin2.site.detail.label.bypassOrderRouting"/></b>
        </td>
    </ui:control>
</tr>

<tr>
    <ui:control name="<%=RefCodeNames.UI_CONTROL.SITE_LINE_LEVEL_CODE%>" template="<td></td><td></td>">
        <td><b><app:storeMessage key="admin2.site.detail.label.siteLineLevelCode"/>:</b></td>
        <td>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.SITE_LINE_LEVEL_CODE_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="lineLevelCode">
                <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="lineLevelCode" size="10" maxlength="30"/>
            </ui:element>
        </td>
    </ui:control>
    <ui:control name="<%=RefCodeNames.UI_CONTROL.CONSOLIDATED_ORDER_WH%>" template="<td colspan=2></td>">
        <td colspan=2>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.CONSOLIDATED_ORDER_WH_VALUE%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="consolidatedOrderWarehouse">
                <html:checkbox name="ADMIN2_SITE_DETAIL_MGR_FORM" property="consolidatedOrderWarehouse" />
            </ui:element><b><app:storeMessage key="admin2.site.detail.label.consolidatedOrderWarehouse"/></b>
        </td>
    </ui:control>
</tr>

<ui:control name="<%=RefCodeNames.UI_CONTROL.BLANKET_PO_NUMBER%>">
    <logic:present  name="ADMIN2_SITE_DETAIL_MGR_FORM" property="blanketPos" >
        <tr>
            <td colspan="4"><b><app:storeMessage key="admin2.site.detail.label.blanketPoNumber"/>:</b></td>
        </tr>
        <tr>
            <td colspan="4">
                <ui:element bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="blanketPoNumId">
                    <app:storeMessage key="admin2.site.detail.text.none"/>:
                    <html:radio name="ADMIN2_SITE_DETAIL_MGR_FORM" property="blanketPoNumId" value="0" />
                    <logic:iterate id="bpo"  name="ADMIN2_SITE_DETAIL_MGR_FORM" property="blanketPos" type="com.cleanwise.service.api.value.BlanketPoNumData">
                        <bean:define id="bpoid" name="bpo" property="blanketPoNumId" type="java.lang.Integer"/>
                        <bean:write name="bpo" property="shortDesc"/> (<bean:write name="bpo" property="poNumber"/>):
                        <html:radio name="ADMIN2_SITE_DETAIL_MGR_FORM" property="blanketPoNumId" value="<%=bpoid.toString()%>" />
                    </logic:iterate>
                </ui:element>
            </td>
        </tr>
    </logic:present>
</ui:control>


<ui:control name="<%=RefCodeNames.UI_CONTROL.SITE_DATA_FIELDS%>">
<logic:present name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields">
<tr>
    <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f1ShowAdmin" value="true">
        <td><b><bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f1Tag" /></b>
            <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f1Required" value="true">
                <span class="reqind">*</span>
            </logic:equal>
        </td>
        <td>
            <ui:element bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="f1Value">
                <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="f1Value" size="30"/>
            </ui:element>
        </td>
    </logic:equal>
    <logic:notEqual name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f1ShowAdmin" value="true">
        <td colspan=2>&nbsp;</td>
    </logic:notEqual>
    <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f2ShowAdmin" value="true">
        <td><b><bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f2Tag" /></b>
            <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f2Required" value="true">
                <span class="reqind">*</span>
            </logic:equal>
        </td>
        <td>
            <ui:element bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="f2Value">
                <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="f2Value" size="30"/>
            </ui:element></td>
    </logic:equal>
</tr>

<tr>
    <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f3ShowAdmin" value="true">
        <td><b><bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f3Tag" /></b>
            <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f3Required" value="true">
                <span class="reqind">*</span>
            </logic:equal>
        </td>
        <td><ui:element bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="f3Value">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="f4Value" size="30"/>
        </ui:element>
        </td>
    </logic:equal>
    <logic:notEqual name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f3ShowAdmin" value="true">
        <td colspan=2>&nbsp;</td>
    </logic:notEqual>
    <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f4ShowAdmin" value="true">
        <td><b><bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f4Tag" /></b>
            <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f4Required" value="true">
                <span class="reqind">*</span>
            </logic:equal>
        </td>
        <td><ui:element bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="f4Value">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="f4Value" size="30"/>
        </ui:element></td>
    </logic:equal>
</tr>

<tr>
    <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f5ShowAdmin" value="true">
        <td><b><bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f5Tag" /></b>
            <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f5Required" value="true">
                <span class="reqind">*</span>
            </logic:equal>
        </td>
        <td><ui:element bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="f5Value">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="f5Value" size="30"/>
        </ui:element></td>
    </logic:equal>
    <logic:notEqual name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f5ShowAdmin" value="true">
        <td colspan=2>&nbsp;</td>
    </logic:notEqual>
    <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f6ShowAdmin" value="true">
        <td><b><bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f6Tag" /></b>
            <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f6Required" value="true">
                <span class="reqind">*</span>
            </logic:equal>
        </td>
        <td><ui:element bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="f6Value">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="f6Value" size="30"/>
        </ui:element></td>
    </logic:equal>
</tr>

<tr>
    <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f7ShowAdmin" value="true">
        <td><b><bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f7Tag" /></b>
            <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f7Required" value="true">
                <span class="reqind">*</span>
            </logic:equal>
        </td>
        <td><ui:element bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="f7Value">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="f7Value" size="30"/>
        </ui:element>></td>
    </logic:equal>
    <logic:notEqual name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f7ShowAdmin" value="true">
        <td colspan=2>&nbsp;</td>
    </logic:notEqual>
    <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f8ShowAdmin" value="true">
        <td><b><bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f8Tag" /></b>
            <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f8Required" value="true">
                <span class="reqind">*</span>
            </logic:equal>
        </td>
        <td><ui:element bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="f8Value">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="f8Value" size="30"/>
        </ui:element></td>
    </logic:equal>
</tr>

<tr>
    <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f9ShowAdmin" value="true">
        <td><b><bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f9Tag" /></b>
            <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f9Required" value="true">
                <span class="reqind">*</span>
            </logic:equal>
        </td>
        <td><ui:element bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="f9Value">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="f9Value" size="30"/>
        </ui:element></td>
    </logic:equal>
    <logic:notEqual name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f9ShowAdmin" value="true">
        <td colspan=2>&nbsp;</td>
    </logic:notEqual>
    <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f10ShowAdmin" value="true">
        <td><b><bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f10Tag" /></b>
            <logic:equal name="ADMIN2_SITE_DETAIL_MGR_FORM" property="siteFields.f10Required" value="true">
                <span class="reqind">*</span>
            </logic:equal>
        </td>
        <td><ui:element bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="f10Value">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="f10Value" size="30"/>
        </ui:element></td>
    </logic:equal>
</tr>
</logic:present>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.SHARE_BUYER_ORDER_GUIDES%>">
    <tr>
        <td><b><app:storeMessage key="admin2.site.detail.label.shareBuyerOrderGuides"/>:</b></td>
        <td>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.SHARE_BUYER_ORDER_GUIDES_VAL%>" bean="ADMIN2_SITE_DETAIL_MGR_FORM" property="shareBuyerOrderGuides">
                <app:storeMessage key="admin2.site.detail.text.yes"/>&nbsp;<html:radio name="ADMIN2_SITE_DETAIL_MGR_FORM" property="shareBuyerOrderGuides" value="true"/>
                &nbsp;&nbsp;&nbsp;
                <app:storeMessage key="admin2.site.detail.text.no"/>&nbsp;<html:radio name="ADMIN2_SITE_DETAIL_MGR_FORM" property="shareBuyerOrderGuides" value="false"/>
            </ui:element>
        </td>
    </tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ADDRESS%>">
<% String tdHeight = "24"; %>
<tr>
    <td colspan="4" class="largeheader"><br><app:storeMessage key="admin2.site.detail.label.siteAddress"/></td>
</tr>
<tr>
<td valign="top"><!-- headers1 -->
    <table cellpadding=0 cellspacing=0 border="0">
        <tr><td height="<%=tdHeight%>"><b><app:storeMessage key="admin2.site.detail.label.siteAddress.firstName"/>:</b></td></tr>
        <tr><td height="<%=tdHeight%>"><b><app:storeMessage key="admin2.site.detail.label.siteAddress.lastName"/>:</b></td></tr>
        <tr><td height="<%=tdHeight%>"><b><app:storeMessage key="admin2.site.detail.label.siteAddress.streetAddress1"/>:</b><span class="reqind">*</span></td></tr>
        <tr><td height="<%=tdHeight%>"><b><app:storeMessage key="admin2.site.detail.label.siteAddress.streetAddress2"/>:</b></td></tr>
        <tr><td height="<%=tdHeight%>"><b><app:storeMessage key="admin2.site.detail.label.siteAddress.streetAddress3"/>:</b></td></tr>
        <tr><td height="<%=tdHeight%>"><b><app:storeMessage key="admin2.site.detail.label.siteAddress.streetAddress4"/>:</b></td></tr>
        <tr><td height="<%=tdHeight%>"><b><app:storeMessage key="admin2.site.detail.label.siteAddress.city"/>:</b><span class="reqind">*</span></td></tr>
    </table>
</td>
<td valign="top"><!-- fields1 -->
    <table cellpadding=0 cellspacing=0 border="0">
        <tr><td height="<%=tdHeight%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_FIRST_NAME_VALUE%>"
                        bean="ADMIN2_SITE_DETAIL_MGR_FORM"
                        property="name1">
                <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="name1" size="30"/>
            </ui:element>
        </td>
        </tr>
        <tr><td height="<%=tdHeight%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_LAST_NAME_VALUE%>"
                        bean="ADMIN2_SITE_DETAIL_MGR_FORM"
                        property="name2">
                <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="name2" size="30"/>
            </ui:element>
        </td></tr>
        <tr><td height="<%=tdHeight%>"><ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS1_VALUE%>"
                                                   bean="ADMIN2_SITE_DETAIL_MGR_FORM"
                                                   property="streetAddr1">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="streetAddr1" size="30"/>
        </ui:element>
        </td></tr>
        <tr><td height="<%=tdHeight%>"><ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS2_VALUE%>"
                                                   bean="ADMIN2_SITE_DETAIL_MGR_FORM"
                                                   property="streetAddr2">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="streetAddr2" size="30"/>
        </ui:element></td></tr>
        <tr><td height="<%=tdHeight%>"><ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS3_VALUE%>"
                                                   bean="ADMIN2_SITE_DETAIL_MGR_FORM"
                                                   property="streetAddr3">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="streetAddr3" size="30"/>
        </ui:element></td></tr>
        <tr><td height="<%=tdHeight%>"><ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS4_VALUE%>"
                                                   bean="ADMIN2_SITE_DETAIL_MGR_FORM"
                                                   property="streetAddr4">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="streetAddr4" size="30"/>
        </ui:element></td></tr>
        <tr><td height="<%=tdHeight%>"><ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_CITY_VALUE%>"
                                                   bean="ADMIN2_SITE_DETAIL_MGR_FORM"
                                                   property="city">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="city" size="30"/>
        </ui:element></td></tr>
    </table>
</td>
<td valign="top"><!-- headers2 -->
    <table cellpadding=0 cellspacing=0 border="0">
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>"><b><app:storeMessage key="admin2.site.detail.label.siteAddress.state"/>:</b><span class="reqind">*</span></td></tr>
        <tr><td height="<%=tdHeight%>"><b><app:storeMessage key="admin2.site.detail.label.siteAddress.zip"/>:</b><span class="reqind">*</span></td></tr>
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>"><b><app:storeMessage key="admin2.site.detail.label.siteAddress.country"/>:</b><span class="reqind">*</span></td></tr>
    </table>
</td>
<td valign="top"><!-- fields2 -->
    <table cellpadding=0 cellspacing=0 border="0">
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>"><ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STATE_VALUE%>"
                                                   bean="ADMIN2_SITE_DETAIL_MGR_FORM"
                                                   property="stateOrProv">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="stateOrProv" size="20" maxlength="20"/>
        </ui:element></td></tr>
        <tr><td height="<%=tdHeight%>"><ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_ZIP_VALUE%>"
                                                   bean="ADMIN2_SITE_DETAIL_MGR_FORM"
                                                   property="postalCode">
            <html:text name="ADMIN2_SITE_DETAIL_MGR_FORM" property="postalCode" />
        </ui:element></td></tr>
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr>
            <td height="<%=tdHeight%>">
                <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_COUNTRY_VALUE%>"
                            bean="ADMIN2_SITE_DETAIL_MGR_FORM"
                            property="country">
                    <html:select name="ADMIN2_SITE_DETAIL_MGR_FORM" property="country">
                        <html:option value="">
                            <app:storeMessage  key="admin2.select"/>
                        </html:option>
                        <html:options collection="admin2.country.vector"
                                      labelProperty="uiName"
                                      property="shortDesc"/>
                    </html:select>
                </ui:element>
            </td>
        </tr>
    </table>
</td>
</tr>

<tr>
    <td><b><app:storeMessage key="admin2.site.detail.label.siteAddress.county"/>:</b></td>
    <td colspan=3>
        <bean:write name="ADMIN2_SITE_DETAIL_MGR_FORM" property="county"/>
    </td>
</tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.OG_COMMENTS%>">
    <tr>
        <td><b><app:storeMessage key="admin2.site.detail.label.orderGuideComments"/>:</b> </td>
        <td colspan=4>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.OG_COMMENTS_VALUE%>"
                        bean="ADMIN2_SITE_DETAIL_MGR_FORM"
                        property="comments">
                <html:textarea name="ADMIN2_SITE_DETAIL_MGR_FORM" cols="60"
                               property="comments"/>
            </ui:element>
        </td>
    </tr>
</ui:control>
<ui:control name="<%=RefCodeNames.UI_CONTROL.SHIPPING_MESSAGE%>">
    <tr>
        <td><b><app:storeMessage key="admin2.site.detail.label.shippingMessage"/>:</b> </td>
        <td colspan=4>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.SHIPPING_MESSAGE_VALUE%>"
                        bean="ADMIN2_SITE_DETAIL_MGR_FORM"
                        property="shippingMessage">
                <html:textarea name="ADMIN2_SITE_DETAIL_MGR_FORM" cols="60" property="shippingMessage"/>
            </ui:element>
        </td>
    </tr>
</ui:control>
<tr>
    <td colspan=4 align=center>
        <html:submit property="action">
            <app:storeMessage  key="global.action.label.save"/>
        </html:submit>
        <html:reset>
            <app:storeMessage  key="admin2.button.site.detail.button.resetFields"/>
        </html:reset>
        <logic:notEqual name="ADMIN2_SITE_DETAIL_MGR_FORM" property="id" value="0">
            <html:submit property="action">
                <app:storeMessage  key="global.action.label.delete"/>
            </html:submit>
            <ui:control name="<%=RefCodeNames.UI_CONTROL.CLONE_WITH_RELATIONSHIPS%>">
           <html:submit property="action">
                <app:storeMessage  key="admin2.button.createCloneWith"/>
            </html:submit>
        </ui:control>
            <ui:control name="<%=RefCodeNames.UI_CONTROL.CLONE_WITHOUT_RELATIONSHIPS%>">
                <html:submit property="action">
                    <app:storeMessage  key="admin2.button.createCloneWithout"/>
                </html:submit>
            </ui:control>
        </logic:notEqual>
    </td>
</tr>

<html:hidden styleId="hiddenAction" property="action" value=""/>

</html:form>

</table>

</div>

</ui:page>

</body>

</html:html>


