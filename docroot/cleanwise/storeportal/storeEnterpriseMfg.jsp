<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.WarrantyData" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.cleanwise.view.forms.StoreAssetDetailForm" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.forms.StoreEnterpriseMfgForm" %>
<%@ page import="com.cleanwise.service.api.value.IdVector" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.cleanwise.service.api.value.PairViewVector" %>
<%@ page import="com.cleanwise.service.api.value.PairView" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<html:html>


<body>
<div>
<table width="<%=Constants.TABLEWIDTH%>" border="0" cellpadding="0" cellspacing="0" class="mainbody">
<logic:present name="STORE_ENTERPRISE_MGR_FORM">
<logic:present name="STORE_ENTERPRISE_MFG_FORM">
<bean:define id="theForm" name="STORE_ENTERPRISE_MFG_FORM"
             type="com.cleanwise.view.forms.StoreEnterpriseMfgForm"/>

<html:form name="STORE_ENTERPRISE_MFG_FORM"
           action="/storeportal/storeEnterpriseMfg"
           scope="session">
<tr>
    <td valign="top"><b><bean:write name="STORE_ENTERPRISE_MGR_FORM" property="managedStore.busEntity.shortDesc"/> Mfg.</b></td>
    <td valign="top">
        <html:select name="STORE_ENTERPRISE_MFG_FORM" property="selectedManagedMfg">
            <html:option value="">
                <app:storeMessage  key="admin.none"/>
            </html:option>
            <% { %>

            <%
                IdVector mMfgIds = ((StoreEnterpriseMfgForm) theForm).getManagedMfgIds();
                HashMap mMfgNames = ((StoreEnterpriseMfgForm) theForm).getManagedMfgNames();

                for (Object mMfgId : mMfgIds) {
                    Integer key = (Integer) mMfgId;
            %>

            <html:option value="<%=key.toString()%>"><%=mMfgNames.get(key)%>
            </html:option>

            <% }%>

            <% } %>

        </html:select>

    </td>
    <td rowspan="3" align="left" style="padding-left:10px">
        <table>
            <tr>
                <td valign="top"><b>Enterprise Mfg. Associations</b></td>
            </tr>
            <tr>
                <td>
                    <html:select name="STORE_ENTERPRISE_MFG_FORM" property="selectedMfgLinks" multiple="true" size="10">

                        <% { %>

                        <%

                            PairViewVector lMfgPairs = ((StoreEnterpriseMfgForm) theForm).getLinkedMfgIds();

                            HashMap mMfgNames = ((StoreEnterpriseMfgForm) theForm).getManagedMfgNames();
                            HashMap eMfgNames = ((StoreEnterpriseMfgForm) theForm).getEnterpriseMfgNames();

                            for (Object oMfgPair : lMfgPairs) {
                                PairView pKey = (PairView) oMfgPair;
                                Integer key1 = (Integer) pKey.getObject1();
                                Integer key2 = (Integer) pKey.getObject2();

                        %>

                        <html:option value="<%=key1+\"_\"+key2%>"><%=mMfgNames.get(key1) + " -> " + eMfgNames.get(key2) %>
                        </html:option>

                        <% }%>

                        <% } %>

                    </html:select>

                </td>
            </tr>
        </table>
    </td>
</tr>
<tr valign="top">
    <td width="15%" valign="top"><b>Enterprise Mfg.</B></td>
    <td width="30%" valign="top">

        <html:select name="STORE_ENTERPRISE_MFG_FORM" property="selectedEnterpriseMfg">

            <html:option value="">
                <app:storeMessage  key="admin.none"/>
            </html:option>

            <% { %>

            <%
                IdVector eMfgIds = ((StoreEnterpriseMfgForm) theForm).getEnterpriseMfgIds();
                HashMap eMfgNames = ((StoreEnterpriseMfgForm) theForm).getEnterpriseMfgNames();

                for (Object eMfgId : eMfgIds) {
                    Integer key = (Integer) eMfgId;
            %>

            <html:option value="<%=key.toString()%>"><%=eMfgNames.get(key)%>
            </html:option>

            <% }%>

            <% } %>

        </html:select>

    </td>

</tr>

<tr>
    <td colspan="2" valign="top" align="center">
        <table>
            <tr>
                <td>
                    <html:submit property="action">
                        <app:storeMessage  key="admin.button.add"/>
                    </html:submit>
                </td>
                <td>
                    <html:submit property="action">
                        <app:storeMessage  key="admin.button.drop"/>
                    </html:submit>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td colspan="3">
        &nbsp;
    </td>
</tr>
<tr>
    <td colspan="3" align="center" width="100%">
        <table>
            <tr>
                <td>
                    <html:submit property="action">
                        <app:storeMessage  key="global.action.label.save"/>
                    </html:submit>
                </td>
            </tr>
        </table>
    </td>
</tr>
</html:form>
</logic:present>
</logic:present>
</table>
</div>
</body>
</html:html>
