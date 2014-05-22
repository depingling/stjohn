<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.value.BusEntityData" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/app-interface-config.tld" prefix="ui" %>

<%String configParam = request.getParameter("configMode");%>
<%String formName = request.getParameter("formName");%>
<%String formAction = request.getParameter("formAction");%>
<%String messageKey;%>
<%CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);%>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<ui:page name="<%=RefCodeNames.UI_PAGE_CD.USER_DETAIL%>" type="<%=RefCodeNames.UI_PAGE_TYPE_CD.USER%>" bean="<%=formName%>" property="uiPage" configMode="<%=configParam%>">

<body class="admin2">

<div class="text">
<table cellpadding="2" cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH800%>" class="mainbody">

<html:form styleId="<%=RefCodeNames.UI_PAGE_CD.USER_DETAIL%>"
           name="<%=formName%>"
           action="<%=formAction%>"
           scope="session">

<logic:equal parameter="configMode" value="false">
  <html:hidden property="isEditableForUserFl"   />
</logic:equal>
<tr>
    <td colspan=4 class="largeheader"><app:storeMessage key="admin2.user.detail.text.userDetail"/></td>
</tr>

<tr>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_ID%>" noDefault="true" template="<td></td><td></td>">

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_ID_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_ID_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.user.detail.label.userId"/>:</ui:label></b>
            </td>
        </ui:element>

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_ID_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_ID_VALUE%>">
                <logic:equal parameter="configMode" value="false">
                    <bean:write name="<%=formName%>" property="detail.userData.userId" scope="session"/>
                </logic:equal>
                <logic:equal parameter="configMode" value="true">
                    <app:storeMessage key="admin2.user.detail.text.noDefault"/>
                </logic:equal>
            </td>
        </ui:element>

    </ui:control>


    <logic:equal parameter="configMode" value="false">
        <html:hidden property="detail.userData.userId"/>
    </logic:equal>

    <%String passwordLabel = ClwI18nUtil.getMessage(request,"admin2.user.detail.label.password",null);%>
    <logic:equal parameter="configMode" value="false">
        <bean:define id="userId" name="<%=formName%>" property="detail.userData.userId" type="java.lang.Integer"/>
        <% if (null != userId && 0 < userId.intValue()) { %>
        <%passwordLabel = ClwI18nUtil.getMessage(request,"admin2.user.detail.label.newPassword",null);%>
        <% } %>
    </logic:equal>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_PASSWORD%>" template="<td></td><td></td>">

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_PASSWORD_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_PASSWORD_LABEL%>">
                <b><ui:label><%=passwordLabel%>:</ui:label></b><span class="reqind">*</span>
            </td>
        </ui:element>

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_PASSWORD_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.PASSWORD%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_PASSWORD_VALUE%>">
                <ui:password name="<%=formName%>" property="password" maxlength="30"/>
            </td>
        </ui:element>

    </ui:control>

</tr>

<tr>

<ui:control name="<%=RefCodeNames.UI_CONTROL.USER_TYPE%>" template="<td></td><td></td>">

    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_TYPE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_TYPE_LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.user.detail.label.userType"/>:</ui:label></b><span class="reqind">*</span>
        </td>
    </ui:element>

    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_TYPE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">

        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_TYPE_VALUE%>">
            <logic:equal parameter="configMode" value="false">
                <bean:define id="userId" name="<%=formName%>" property="detail.userData.userId" type="java.lang.Integer"/>
                <% if( null == userId || 0 == userId.intValue() ) {%>
                <%String changeUserType = "adm2Submit('" + RefCodeNames.UI_PAGE_CD.USER_DETAIL + "','action','changeUserType')";%>
                <ui:select name="<%=formName%>" property="detail.userData.userTypeCd" onchange="<%=changeUserType%>">
                    <%  if(appUser.isaAccountAdmin()) { %>
                    <html:option value="<%=RefCodeNames.USER_TYPE_CD.MSB%>">
                        <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.USER_TYPE_CD." + (RefCodeNames.USER_TYPE_CD.MSB).toUpperCase());%>
                        <% if (messageKey != null) {%>
                        <%=messageKey%>
                        <%} else {%>
                        <%=RefCodeNames.USER_TYPE_CD.MSB%>
                        <%}%>
                    </html:option>
                    <%} else{%>
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:iterate id="refCd" name="admin2.user.type.vector" type="com.cleanwise.service.api.value.RefCdData">
                        <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.USER_TYPE_CD." + ((String) refCd.getValue()).toUpperCase());%>
                        <html:option value="<%=refCd.getValue()%>">
                            <% if (messageKey != null) {%>
                            <%=messageKey%>
                            <%} else {%>
                            <%=refCd.getValue()%>
                            <%}%>
                        </html:option>
                    </logic:iterate>
                    <%}%>
                </ui:select>
                <% } else {  %>
                <bean:write name="<%=formName%>" property="detail.userData.userTypeCd" filter="true" />
                <%  }  %>
            </logic:equal>
            <logic:equal parameter="configMode" value="true">
                <ui:select>
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="admin2.user.type.vector">
                        <logic:iterate id="refCd" name="admin2.user.type.vector" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.USER_TYPE_CD." + ((String) refCd.getValue()).toUpperCase());%>
                            <html:option value="<%=refCd.getValue()%>">
                                <% if (messageKey != null) {%>
                                <%=messageKey%>
                                <%} else {%>
                                <%=refCd.getValue()%>
                                <%}%>
                            </html:option>
                        </logic:iterate>
                    </logic:present>
                </ui:select>
            </logic:equal>
        </td>
    </ui:element>

</ui:control>

<%String confPasswordLabel = ClwI18nUtil.getMessage(request,"admin2.user.detail.label.confirmPassword",null);%>
<logic:equal parameter="configMode" value="false">
    <bean:define id="userId" name="<%=formName%>" property="detail.userData.userId" type="java.lang.Integer"/>
    <% if (null != userId && 0 < userId.intValue()) { %>
    <%confPasswordLabel =  ClwI18nUtil.getMessage(request,"admin2.user.detail.label.confirmNewPassword",null);%>
    <% } %>
</logic:equal>

<ui:control name="<%=RefCodeNames.UI_CONTROL.USER_CONFIRM_PASSWORD%>" template="<td></td><td></td>">

    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_CONFIRM_PASSWORD_LABEL%>"
                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_CONFIRM_PASSWORD_LABEL%>">
            <b><ui:label><%=confPasswordLabel%>:</ui:label></b><span class="reqind">*</span>
        </td>
    </ui:element>

    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_CONFIRM_PASSWORD_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.PASSWORD%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_CONFIRM_PASSWORD_VALUE%>">
            <ui:password name="<%=formName%>" property="confirmPassword"/>
        </td>
    </ui:element>

</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.PREFERRED_LANGUAGE%>"  targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.PREFERRED_LANGUAGE_LABEL%>" template="<td rowspan=3></td>">

    <td rowspan=3 id="<%=RefCodeNames.UI_CONTROL_ELEMENT.PREFERRED_LANGUAGE_LABEL%>">

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.PREFERRED_LANGUAGE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.user.detail.label.preferredLanguage"/>:</ui:label></b><span class="reqind">*</span>
        </ui:element>

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.PREFERRED_LANGUAGE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
            <ui:select name="<%=formName%>" property="detail.userData.prefLocaleCd">
                <html:option value="">
                    <app:storeMessage  key="admin2.select"/>
                </html:option>
                <logic:present name="admin2.user.locale.vector">
                    <logic:iterate id="localeRefCd" name="admin2.user.locale.vector" type="com.cleanwise.service.api.value.RefCdData">
                        <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.LOCALE_CD." + ((String) localeRefCd.getValue()).toUpperCase());%>
                        <html:option value="<%=localeRefCd.getValue()%>">
                            <% if (messageKey != null) {%>
                            <%=messageKey%>
                            <%} else {%>
                            <%=localeRefCd.getValue()%>
                            <%}%>
                        </html:option>
                    </logic:iterate>
                </logic:present>
            </ui:select>
        </ui:element>

    </td>

</ui:control>

</tr>


<tr>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_NAME%>" template="<td></td><td></td>">

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_NAME_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_NAME_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.user.detail.label.loginName"/>:</ui:label></b><span class="reqind">*</span></td>
        </ui:element>

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_NAME_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_NAME_VALUE%>">
                <ui:text name="<%=formName%>" property="detail.userData.userName"  maxlength="45"/>
            </td>
        </ui:element>

    </ui:control>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.STATUS%>" template="<td></td><td></td>">

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.user.detail.label.userStatus"/>:</ui:label></b><span class="reqind">*</span></td>
        </ui:element>

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_VALUE%>">
                <ui:select name="<%=formName%>" property="detail.userData.userStatusCd">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="admin2.user.status.vector">
                        <logic:iterate id="refCd" name="admin2.user.status.vector" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.USER_STATUS_CD." + ((String) refCd.getValue()).toUpperCase());%>
                            <html:option value="<%=refCd.getValue()%>">
                                <% if (messageKey != null) {%>
                                <%=messageKey%>
                                <%} else {%>
                                <%=refCd.getValue()%>
                                <%}%>
                            </html:option>
                        </logic:iterate>
                    </logic:present>
                </ui:select>
            </td>
        </ui:element>

    </ui:control>
</tr>



<tr>

<ui:control name="<%=RefCodeNames.UI_CONTROL.USER_ACTIVE_DATE%>" template="<td></td><td></td>">

<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_ACTIVE_DATE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_ACTIVE_DATE_LABEL%>">
    <b><ui:label><app:storeMessage key="admin2.user.detail.label.userActiveDate"/>:</ui:label>
</b><span class="reqind">*</span>
</td>
</ui:element>

<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_ACTIVE_DATE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_ACTIVE_DATE_VALUE%>">
    <ui:dojoInputDateProgrammaticTag name="<%=formName%>" property="effDate" id="userActiveDate" module="clw.admin2"/>
</td>
</ui:element>

</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.USER_INACTIVE_DATE%>" template="<td></td><td></td>">

<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_INACTIVE_DATE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_INACTIVE_DATE_LABEL%>">
    <b><ui:label><app:storeMessage key="admin2.user.detail.label.userInactiveDate"/>:</ui:label></b>
</td>
</ui:element>

<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_INACTIVE_DATE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_INACTIVE_DATE_VALUE%>">
    <ui:dojoInputDateProgrammaticTag name="<%=formName%>" property="expDate" id="userInactiveDate" module="clw.admin2"/>
</td>
</ui:element>

</ui:control>

<tr>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.USER_CODE%>">

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_CODE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_CODE_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.user.detail.label.userCode"/>:</ui:label></b></td>
        </ui:element>

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_CODE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.USER_CODE_VALUE%>">
                <ui:text name="<%=formName%>" property="userIDCode" size="20" maxlength="255"/>
            </td>
        </ui:element>

    </ui:control>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.CORPARATE_USER%>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.CORPARATE_USER_LABEL%>">

        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.CORPARATE_USER_LABEL%>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.CORPARATE_USER_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.user.detail.label.corporateUser"/></ui:label></b>
        </ui:element>

        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.CORPARATE_USER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="isCorporateUser"/>
        </ui:element>
       </td>

    </ui:control>

    <ui:control name="<%=RefCodeNames.UI_CONTROL.RECEIVE_INV_MISSING_EMAIL%>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.RECEIVE_INV_MISSING_EMAIL_LBL%>">

        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.RECEIVE_INV_MISSING_EMAIL_LBL%>">

            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.RECEIVE_INV_MISSING_EMAIL_LBL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.user.detail.label.receiveInvMissingEmail"/></ui:label></b>
            </ui:element>

            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.RECEIVE_INV_MISSING_EMAIL_VAL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                <ui:checkbox name="<%=formName%>" property="receiveInvMissingEmail"/>
            </ui:element>
        </td>

    </ui:control>
</tr>

<logic:equal parameter="configMode" value="false">
    <bean:define id="userType" name="<%=formName%>" property="detail.userData.userTypeCd"/>
    <% if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userType)) {%>
            <jsp:include page="admin2UserDistributorLabels.jsp" flush="true">
                <jsp:param name="formName" value="<%=formName%>"/>
                <jsp:param name="configMode" value="<%=configParam%>"/>
            </jsp:include>
    <% } %>
</logic:equal>

<logic:equal parameter="configMode" value="true">
    <jsp:include page="admin2UserDistributorLabels.jsp" flush="true">
        <jsp:param name="formName" value="<%=formName%>"/>
        <jsp:param name="configMode" value="<%=configParam%>"/>
    </jsp:include>
</logic:equal>

<logic:equal parameter="configMode" value="false">

<ui:control name="<%=RefCodeNames.UI_CONTROL.STORE_OR_ACCOUNT_ASSOC%>">
    <bean:define id="entities" name="<%=formName%>"  property="entities.values" type="java.util.List"/>
<tr>
    <td valign="top">
        <b>  <%
            if(entities.size()>1) {%>
            <%
                String entityMsg;
                if (appUser.isaAccountAdmin()) {
                    entityMsg = ClwI18nUtil.getMessage(request,"admin2.user.detail.label.accounts",null);
                } else if (appUser.isaAdmin()) {
                    entityMsg =  ClwI18nUtil.getMessage(request,"admin2.user.detail.label.stores",null);
                } else {
                    entityMsg = null;
                }
            %>
            <%=Utility.strNN(entityMsg)%>
            <%}%>
        </b>
    </td>
    <td colspan="3">
        <table>

            <%
                if(entities.size()>1) {
                    for(int ii=0; ii<entities.size(); ii++) {
                        BusEntityData ent = (BusEntityData) entities.get(ii);
                        String selectedStr = "entities.selected["+ii+"]";
            %>
            <tr>
                <td><%=ent.getShortDesc()%></td>
                <td>
                    <%  if(appUser.isaAccountAdmin()) { %>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.STORE_OR_ACCOUNT_ASSOC_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.MULTIBOX%>">
                        <ui:hidden name="<%=formName%>" property="<%=selectedStr%>"/>
                    </ui:element>
                    <%} else {%>
                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.STORE_OR_ACCOUNT_ASSOC_VALUE%>"
                                type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.MULTIBOX%>"><ui:multibox name="<%=formName%>" property="<%=selectedStr%>"  value="true"/>
                    </ui:element>
                    <%}%>
                </td>
            </tr>
            <% } %>
            <% } else {  %>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.STORE_OR_ACCOUNT_ASSOC_VALUE%>"
                        type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.MULTIBOX%>">
                <bean:define id="userId" name="<%=formName%>" property="detail.userData.userId" type="java.lang.Integer"/>
                <% if (null != userId && 0 < userId.intValue()) { %>
                <ui:hidden name="<%=formName%>" property="entities.selected[0]"/>
                <% } else {%>
                <ui:hidden name="<%=formName%>" property="entities.selected[0]" value="true"/>
                <%}%>
            </ui:element>
            <% }  %>
            </ui:control>
        </table> </td>
</tr>
</logic:equal>
<logic:equal parameter="configMode" value="true">
<ui:control name="<%=RefCodeNames.UI_CONTROL.STORE_OR_ACCOUNT_ASSOC%>" targets="STORE_OR_ACCOUNT_ASSOC_TARGET" noDefault="true">
<tr>
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.STORE_OR_ACCOUNT_ASSOC_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.MULTIBOX%>">

        <td id="STORE_OR_ACCOUNT_ASSOC_TARGET" colspan="5" valign="middle" align="center" width="100%" height="70px"><b>
            Managed Stores or Accounts
        </b></td>
    </ui:element>
</tr>
</ui:control>
</logic:equal>

<logic:equal parameter="configMode" value="false">
    <bean:define id="userType" name="<%=formName%>" property="detail.userData.userTypeCd"/>
    <% if (RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userType) || RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType)) { %>
        <ui:control name="<%=RefCodeNames.UI_CONTROL.CUST_SERVICE_ROLE%>">
            <tr>
              <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.CUST_SERVICE_ROLE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                 <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.CUST_SERVICE_ROLE_LABEL%>">
                     <b><ui:label><app:storeMessage key="admin2.user.detail.label.customerServiceRole"/>:</ui:label></b>
                 </td>
             </ui:element>

              <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.CUST_SERVICE_ROLE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
                 <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.CUST_SERVICE_ROLE_VALUE%>">
                     <ui:select name="<%=formName%>" property="customerServiceRoleCd">
                         <html:option value=""><app:storeMessage  key="admin2.select"/></html:option>
                         <logic:present name="admin2.customerService.role.vector">
                             <logic:iterate id="refCd" name="admin2.customerService.role.vector" type="com.cleanwise.service.api.value.RefCdData">
                                 <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.CUSTOMER_SERVICE_ROLE_CD." + ((String) refCd.getValue()).toUpperCase());%>
                                 <html:option value="<%=refCd.getValue()%>">
                                     <% if (messageKey != null) {%> <%=messageKey%><%} else {%> <%=refCd.getValue()%> <%}%>
                                 </html:option>
                             </logic:iterate>
                         </logic:present>
                     </ui:select>
                 </td>
              </ui:element>
             <td colspan="2">&nbsp;</td>
            </tr>
</ui:control>
    <% } %>
</logic:equal>

<logic:equal parameter="configMode" value="true">
<ui:control name="<%=RefCodeNames.UI_CONTROL.CUST_SERVICE_ROLE%>">
<tr>

    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.CUST_SERVICE_ROLE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.CUST_SERVICE_ROLE_LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.user.detail.label.customerServiceRole"/>:</ui:label></b>
        </td>
    </ui:element>

    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.CUST_SERVICE_ROLE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.CUST_SERVICE_ROLE_VALUE%>">
            <ui:select name="<%=formName%>" property="customerServiceRoleCd">
                <html:option value=""><app:storeMessage  key="admin2.select"/></html:option>
                <logic:present name="admin2.customerService.role.vector">
                    <logic:iterate id="refCd" name="admin2.customerService.role.vector" type="com.cleanwise.service.api.value.RefCdData">
                        <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.CUSTOMER_SERVICE_ROLE_CD." + ((String) refCd.getValue()).toUpperCase());%>
                        <html:option value="<%=refCd.getValue()%>">
                            <% if (messageKey != null) {%> <%=messageKey%><%} else {%> <%=refCd.getValue()%> <%}%>
                        </html:option>
                    </logic:iterate>
                </logic:present>
            </ui:select>
        </td>
    </ui:element>
    <td colspan="2">&nbsp;</td>
</tr>
</ui:control>
</logic:equal>

<logic:equal parameter="configMode" value="false">
<bean:define id="userType" name="<%=formName%>" property="detail.userData.userTypeCd"/>
<% if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)) {  %>
<ui:control name="<%=RefCodeNames.UI_CONTROL.TOTAL_FIELD_READONLY%>">
<tr>

    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.TOTAL_FIELD_READONLY_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.TOTAL_FIELD_READONLY_LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.user.detail.label.totalFieldReadonly"/>:</ui:label></b>
        </td>
    </ui:element>

    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.TOTAL_FIELD_READONLY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.TOTAL_FIELD_READONLY_VALUE%>">
            <ui:checkbox name="<%=formName%>" property="totalReadOnly"/>
        </td>
    </ui:element>

    <td colspan="2">&nbsp;</td>
</tr>
</ui:control>
<% } %>
</logic:equal>

<logic:equal parameter="configMode" value="true">

<ui:control name="<%=RefCodeNames.UI_CONTROL.TOTAL_FIELD_READONLY%>">
<tr>
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.TOTAL_FIELD_READONLY_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.TOTAL_FIELD_READONLY_LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.user.detail.label.totalFieldReadonly"/>:</ui:label></b>
        </td>
    </ui:element>
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.TOTAL_FIELD_READONLY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.TOTAL_FIELD_READONLY_VALUE%>">
            <ui:checkbox name="<%=formName%>" property="totalReadOnly"/>
        </td>
    </ui:element>
    <td colspan="2">&nbsp;</td>
</tr>
</ui:control>
</logic:equal>

<tr>
    <td colspan="5">
        <table cellpadding="2" cellspacing="0" border="0" width="100%" class="mainbody">

            <jsp:include page="admin2UserContactInfo.jsp" flush="true">
                <jsp:param name="formName" value="<%=formName%>"/>
                <jsp:param name="configMode" value="<%=configParam%>"/>
            </jsp:include>
            <!-- Default User Rights -->
            <tr>
                <td colspan="4" align="center">
                    <b><app:storeMessage key="admin2.user.detail.text.defaultUserRights"/></b>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <jsp:include page="admin2UserRightsConfig.jsp" flush="true">
                        <jsp:param name="formName" value="<%=formName%>"/>
                        <jsp:param name="configMode" value="<%=configParam%>"/>
                    </jsp:include>
                </td>
            </tr>


        </table>
    </td>
</tr>

<logic:equal parameter="configMode" value="true">
<tr>
    <td colspan=5>
        <table cellpadding="2" cellspacing="0" border="0" width="100%" class="mainbody">
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <html:submit property="action" value="Save Page Interface"></html:submit>
                </td>
            </tr>

        </table>
    </td>
</tr>
</logic:equal>
<logic:equal parameter="configMode" value="false">
<tr>
    <td colspan=5>
        <table cellpadding="2" cellspacing="0" border="0" width="100%" class="mainbody">
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="4">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <html:submit property="action">
                        <app:storeMessage  key="global.action.label.save"/>
                    </html:submit>
                    <html:reset>
                        <app:storeMessage  key="admin2.button.resetFields"/>
                    </html:reset>
                </td>
            </tr>
        </table>
    </td>
</tr>
</logic:equal>
</table>
<html:hidden styleId="hiddenAction" property="action" value=""/>
</html:form>
<script type="text/javascript">
  var editable = 'true';
  var forms = document.forms;
  for (var i=0;i<forms.length;i++) {
    var form = forms[i];
    var elts = form.elements;
//    alert("elements=" + elts.length);
    for (var j=0;j<elts.length;j++) {
      var el = elts[j];
      if (el.name == "isEditableForUserFl"){
        editable=el.value;
      }
    }
//    alert ( "editable=" +editable);
    if (editable != 'true'){
      for (var j=0;j<elts.length;j++) {
        var el = elts[j];
        el.disabled = true;
      }
    }
  }
</script>
</div>
</body>
</ui:page>


