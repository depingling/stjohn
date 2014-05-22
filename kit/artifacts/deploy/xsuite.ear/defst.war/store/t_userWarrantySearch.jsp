<%--
 Date: 16.09.2007
 Time: 19:06:29
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.UserWarrantyMgrForm" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
<!--
document.onkeypress = freeKeyHit;

function freeKeyHit(evt) {
    var evt = (evt) ? evt : event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;

    if (charCode == 13) {
        actionSubmit(0, 'Search');
    }
}

function actionSubmit(formNum, action) {
    var actions;
    actions=document.forms[formNum]["action"];
    //alert(actions.length);
    for (ii=actions.length-1; ii>=0; ii--) {
        if(actions[ii].value=='hiddenAction') {
            actions[ii].value=action;
            document.forms[formNum].submit();
        break;
        }
    }
return false;
}
-->
</script>

<html:html>
<body>

<table align=center CELLSPACING=0 CELLPADDING=0 width="100%">
<tr>
<td>
<html:form name="USER_WARRANTY_MGR_FORM" action="/userportal/userWarranty.do" scope="session"
           type="com.cleanwise.view.forms.UserWarrantyMgrForm">

<logic:present name="<%=Constants.APP_USER%>">
<logic:greaterThan name="<%=Constants.APP_USER%>" property="siteNumber" value="0">
<%String messageKey;%>
<table width="100%">
<tr>
    <td width="20%"><b>
        <app:storeMessage key="userWarranty.text.findWarranty"/>
    </b></td>
    <td align="left" width="20%">
        <html:text styleId="searchField" name="USER_WARRANTY_MGR_FORM" property="searchField"/>
    </td>


    <td>
        <html:select name="USER_WARRANTY_MGR_FORM" property="searchFieldType">
            <html:option value="<%=UserWarrantyMgrForm.WARRANTY_NAME%>">
                <app:storeMessage key="userWarranty.text.warrantyName"/>
            </html:option>
            <html:option value="<%=UserWarrantyMgrForm.ASSET_NAME%>">
                <app:storeMessage key="userWarranty.text.assetName"/>
            </html:option>
            <html:option value="<%=UserWarrantyMgrForm.ASSET_NUMBER%>">
                <app:storeMessage key="userWarranty.text.assetNumber"/>
            </html:option>
            <html:option value="<%=UserWarrantyMgrForm.WARRANTY_PROVIDER%>">
                <app:storeMessage key="userWarranty.text.warrantyProvider"/>
            </html:option>
            <html:option value="<%=UserWarrantyMgrForm.SERVICE_PROVIDER%>">
                <app:storeMessage key="userWarranty.text.serviceProvider"/>
            </html:option>
        </html:select>

    </td>

</tr>
<tr>
    <td></td>
    <td colspan="2">
        <html:radio name="USER_WARRANTY_MGR_FORM" property="searchType"
                    value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
        <app:storeMessage key="userWarranty.text.nameStartsWith"/>
        <html:radio name="USER_WARRANTY_MGR_FORM" property="searchType"
                    value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
        <app:storeMessage key="userWarranty.text.nameContains"/>
    </td>
</tr>

<tr>
    <td>&nbsp;</td>
    <td colspan="2">
    <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'Search');">
        <app:storeMessage key="global.action.label.search"/>
    </html:button>
    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
        <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'Create');">
            <app:storeMessage key="global.action.label.create"/>
        </html:button>
    </app:authorizedForFunction>
    
    </td>
</tr>

<tr>
    <td colspan="3">
        <logic:present name="USER_WARRANTY_MGR_FORM" property="searchResult">
            <bean:size id="rescount" name="USER_WARRANTY_MGR_FORM" property="searchResult"/>
            <table width="100%">
                <tr>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.warrantyNumber"/>
                        </div>
                    </td>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.warrantyProvider"/>
                        </div>
                    </td>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.assetCategories"/>
                        </div>
                    </td>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.warrantyType"/>
                        </div>
                    </td>
                    <td class="shopcharthead">
                        <div class="fivemargin">
                            <app:storeMessage key="userWarranty.text.warrantyStatusCd"/>
                        </div>
                    </td>
                </tr>
                <logic:iterate id="arrele"
                               name="USER_WARRANTY_MGR_FORM"
                               property="searchResult" indexId="i"
                               type="com.cleanwise.service.api.value.WarrantyView">
                    <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
                        <td>
                            <bean:define id="eleid" name="arrele" property="warrantyId"/>
                            <a href="../userportal/userWarrantyDetail.do?action=detail&warrantyId=<%=eleid%>&display=t_userWarrantyDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                <bean:write name="arrele" property="warrantyNumber"/>
                            </a>
                        </td>
                        <td><logic:present name="arrele" property="warrantyProvider.shortDesc">
                            <bean:write name="arrele" property="warrantyProvider.shortDesc"/>
                        </logic:present></td>
                        <logic:present name="arrele" property="assets">
                            <bean:size id="assetcount" name="arrele" property="assets"/>
                            <td>
                                <logic:iterate id="asset" name="arrele" property="assets" indexId="j">
                                    <logic:greaterThan name="j" value="0">
                                        <br/>
                                    </logic:greaterThan>
                                    <bean:write name="asset" property="shortDesc"/>
                                </logic:iterate>
                            </td>
                        </logic:present>
                        <logic:notPresent name="arrele" property="assets">
                            <td>&nbsp; </td>
                            <td>&nbsp;</td>
                        </logic:notPresent>
                        <td>
                            <logic:present name="arrele" property="type">
                                <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.warrantyType." + ((String) arrele.getType()).toUpperCase());
                                    if (messageKey != null) {%>
                                <%=messageKey%>
                                <%} else{%>
                                <bean:write name="arrele" property="type"/>
                                <%}%>
                            </logic:present>
                        </td>
                        <td>
                            <logic:present name="arrele" property="status">
                                <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.warrantyStatusCd." + ((String) arrele.getStatus()).toUpperCase());
                                    if (messageKey != null) {%>
                                <%=messageKey%>
                                <%} else{%>
                                <bean:write name="arrele" property="status"/>
                                <%}%>
                            </logic:present>
                        </td>
                    </tr>
                </logic:iterate>
            </table>
        </logic:present>
    </td>
</tr>
</table>
</logic:greaterThan>
</logic:present>
<html:hidden property="action" value="hiddenAction"/>
<html:hidden property="tabs" value="f_userAssetToolbar"/>
<html:hidden property="display" value="t_userWarrantySearch"/>
</html:form>
</td>
</tr>
</table>
</body>
</html:html>