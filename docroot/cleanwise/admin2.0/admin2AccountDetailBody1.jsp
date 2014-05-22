<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.Admin2Tool" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/app-interface-config.tld" prefix="ui" %>

<% String configParam = request.getParameter("configMode"); %>
<% String formName = request.getParameter("formName"); %>
<% String formAction = request.getParameter("formAction"); %>
<% String messageKey; %>
<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);%>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<ui:page name="<%=RefCodeNames.UI_PAGE_CD.ACCOUNT_DETAIL%>" 
		 type="<%=RefCodeNames.UI_PAGE_TYPE_CD.ACCOUNT%>" 
		 bean="<%=formName%>" 
		 property="uiPage" 
		 configMode="<%=configParam%>">


 <tr>
    <td colspan=4 class="largeheader"><app:storeMessage key="admin2.account.detail.text.accountDetail"/></td>
 </tr>
 
<tr>
 	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ID%>" noDefault="true" template="<td></td><td></td>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ID_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ID_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.accountId"/>:</ui:label></b>
            </td>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ID_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ID_VALUE%>">
                <logic:equal parameter="configMode" value="false">
                    <bean:write name="<%=formName%>" property="accountData.accountId" scope="session"/>
                </logic:equal>
                <logic:equal parameter="configMode" value="true">
                    <app:storeMessage key="admin2.account.detail.text.noDefault"/>
                </logic:equal>
            </td>
        </ui:element>
    </ui:control>
	<logic:equal parameter="configMode" value="false">
        <html:hidden property="accountData.accountId"/>
    </logic:equal>
    
     <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_NAME%>" template="<td></td><td></td>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NAME_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NAME_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.accountName"/>:</ui:label></b><span class="reqind">*</span></td>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NAME_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NAME_VALUE%>">
                <ui:text name="<%=formName%>" property="name"  maxlength="45"/>
            </td>
        </ui:element>
    </ui:control>
</tr>

<tr>
<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_TYPE%>" template="<td></td><td></td>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TYPE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TYPE_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.accountType"/>:</ui:label></b><span class="reqind">*</span></td>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TYPE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TYPE_VALUE%>">
                <ui:select name="<%=formName%>" property="typeDesc">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.ACCOUNT_TYPE_CD%>">
                        <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.ACCOUNT_TYPE_CD%>" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request,"refcode.ACCOUNT_TYPE_CD."+ ((String) refCd.getValue()).toUpperCase());%>
                            <html:option value="<%=refCd.getValue()%>">
                                <%if (messageKey != null) { %>
                                <%=messageKey%>
                                <%} else {  %>
                                <%=refCd.getValue()%>
                                <%}%>
                            </html:option>
                        </logic:iterate>
                    </logic:present>
                </ui:select>
            </td>
        </ui:element>
    </ui:control>
	
	
<ui:control name="<%=RefCodeNames.UI_CONTROL.STATUS%>" template="<td></td><td></td>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.accountStatus"/>:</ui:label></b><span class="reqind">*</span></td>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_VALUE%>">
                <ui:select name="<%=formName%>" property="statusCd">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.BUS_ENTITY_STATUS_CD%>">
                        <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.BUS_ENTITY_STATUS_CD%>" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request,"refcode.BUS_ENTITY_STATUS_CD."+ ((String) refCd.getValue()).toUpperCase());%>
                            <html:option value="<%=refCd.getValue()%>">
                                <%if (messageKey != null) { %>
                                <%=messageKey%>
                                <%} else { %>
                                <%=refCd.getValue()%>
                                <%} %>
                            </html:option>
                        </logic:iterate>
                    </logic:present>
                </ui:select>
            </td>
        </ui:element>
    </ui:control>
</tr>

<tr>
 	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_NUMBER%>" template="<td></td><td></td>" targets="ACCOUNT_NUMBER_LABEL,ACCOUNT_NUMBER_VALUE">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NUMBER_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
			<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NUMBER_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.accountNumber"/>:</ui:label></b>
            </td>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NUMBER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
			<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NUMBER_VALUE%>">
                <logic:equal parameter="configMode" value="false">
                    <bean:write name="<%=formName%>" property="accountNumber" scope="session"/>
                </logic:equal>
				<logic:equal parameter="configMode" value="true">
                    <app:storeMessage key="admin2.account.detail.text.noDefault"/>
                </logic:equal>
            </td>
        </ui:element>
    </ui:control>
 	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ORDER_MINIMUM%>" template="<td></td><td></td>" targets="ACCOUNT_ORDER_MINIMUM_LABEL,ACCOUNT_ORDER_MINIMUM_VALUE">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_MINIMUM_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_MINIMUM_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.orderMinimum"/>:</ui:label></b>
            </td>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_MINIMUM_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_MINIMUM_VALUE%>">
				<ui:text name="<%=formName%>" property="orderMinimum"/>
            </td>
        </ui:element>
    </ui:control>
</tr>



<tr>
 	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_CREDIT_LIMIT%>" template="<td></td><td></td>" targets="ACCOUNT_CREDIT_LIMIT_LABEL,ACCOUNT_CREDIT_LIMIT_VALUE">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CREDIT_LIMIT_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CREDIT_LIMIT_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.creditLimit"/>:</ui:label></b>
            </td>
    	</ui:element>
    	<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CREDIT_LIMIT_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CREDIT_LIMIT_VALUE%>">
                <ui:text name="<%=formName%>" property="creditLimit"/>
            </td>
    	</ui:element>
    </ui:control>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_CREDIT_RATING%>" template="<td></td><td></td>" targets="ACCOUNT_CREDIT_RATING_LABEL,ACCOUNT_CREDIT_RATING_VALUE">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CREDIT_RATING_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CREDIT_RATING_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.creditRating"/>:</ui:label></b>
            </td>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CREDIT_RATING_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CREDIT_RATING_VALUE%>">
                    <ui:text name="<%=formName%>" property="creditRating"/>
            </td>
        </ui:element>
    </ui:control>
</tr>
<tr>
<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_BUDGET_TYPE%>" template="<td></td><td></td>" targets="ACCOUNT_BUDGET_TYPE_LABEL,ACCOUNT_BUDGET_TYPE_VALUE">
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BUDGET_TYPE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BUDGET_TYPE_LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.budgetType"/>:</ui:label></b><span class="reqind">*</span>
        </td>
    </ui:element>
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BUDGET_TYPE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
           <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_BUDGET_TYPE_VALUE%>">
                <ui:select name="<%=formName%>" property="budgetTypeCd">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.BUDGET_ACCRUAL_TYPE_CD%>">
                        <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.BUDGET_ACCRUAL_TYPE_CD%>" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request,"refcode.BUDGET_ACCRUAL_TYPE_CD."+ ((String) refCd.getValue()).toUpperCase());%>
                            <html:option value="<%=refCd.getValue()%>">
                                <%if (messageKey != null) {%>
                                <%=messageKey%>
                                <%} else { %>
                                <%=refCd.getValue()%>
                                <%}%>
                            </html:option>
                        </logic:iterate>
                    </logic:present>
                </ui:select>
            </td>
      </ui:element>	
</ui:control>
<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_GL_TRANSFORMATION_TYPE%>" template="<td></td><td></td>" targets="ACCOUNT_GL_TRANSFORMATION_TYPE_LABEL,ACCOUNT_GL_TRANSFORMATION_TYPE_VALUE">
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_GL_TRANSFORMATION_TYPE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_GL_TRANSFORMATION_TYPE_LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.glTransformationType"/>:</ui:label></b>
        </td>
    </ui:element>
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_GL_TRANSFORMATION_TYPE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
    	<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_GL_TRANSFORMATION_TYPE_VALUE%>">
                <ui:select name="<%=formName%>" property="glTransformationType">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.GL_TRANSFORMATION_TYPE%>">
                        <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.GL_TRANSFORMATION_TYPE%>" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request,"refcode.GL_TRANSFORMATION_TYPE."+ ((String) refCd.getValue()).toUpperCase());%>
                            <html:option value="<%=refCd.getValue()%>">
                                <%if (messageKey != null) {%>
                                <%=messageKey%>
                                <%} else { %>
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
<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_TIME_ZONE%>" template="<td></td><td></td>" targets="ACCOUNT_TIME_ZONE_LABEL,ACCOUNT_TIME_ZONE_VALUE">
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TIME_ZONE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TIME_ZONE_LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.timeZone"/>:</ui:label></b>
        </td>
    </ui:element>
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TIME_ZONE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
    	<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TIME_ZONE_VALUE%>">
                <ui:select name="<%=formName%>" property="timeZoneCd">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.TIME_ZONE_CD%>">
                        <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.TIME_ZONE_CD%>" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.TIME_ZONE_CD."+ ((String) refCd.getValue()).toUpperCase());%>
                            <html:option value="<%=refCd.getValue()%>">
                                <%if (messageKey != null) { %>
                                <%=messageKey%>
                                <%} else { %>
                                <%=refCd.getValue()%>
                                <%}%>
                            </html:option>
                        </logic:iterate>
                    </logic:present>
                </ui:select>
            </td>	
    </ui:element>
  </ui:control>
  <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ALLOW_SITE_LLC%>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_SITE_LLC_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_SITE_LLC_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_SITE_LLC_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="allowSiteLLC"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_SITE_LLC_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.allowSiteLLC"/></ui:label></b>
        </ui:element>
        </td>
    </ui:control>
</tr>
<tr>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_CUSTOMER_EMAIL%>" template="<td></td><td></td>">
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CUSTOMER_EMAIL_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CUSTOMER_EMAIL_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.customerEmail"/>:</ui:label></b>
	        </td>
	    </ui:element>
	<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CUSTOMER_EMAIL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CUSTOMER_EMAIL_VALUE%>">
                    <ui:text name="<%=formName%>" property="customerEmail"/>
            </td>
        </ui:element>
	</ui:control>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_DISTRIBUTOR_REFNUM%>" template="<td></td><td></td>">
			<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DISTRIBUTOR_REFNUM_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
		        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DISTRIBUTOR_REFNUM_LABEL%>">
		            <b><ui:label><app:storeMessage key="admin2.account.detail.label.distributorAccountRefNum"/>:</ui:label></b>
		        </td>
	    </ui:element>
	<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DISTRIBUTOR_REFNUM_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DISTRIBUTOR_REFNUM_VALUE%>">
                    <ui:text name="<%=formName%>" property="distributorAccountRefNum"/>
            </td>
        </ui:element>
	</ui:control>
</tr>
<tr>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_CONTRACT_US_CC_EMAIL%>" template="<td></td><td></td>">
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONTRACT_US_CC_EMAIL_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONTRACT_US_CC_EMAIL_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.contactUsCCEmail"/>:</ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONTRACT_US_CC_EMAIL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
	            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONTRACT_US_CC_EMAIL_VALUE%>">
	                    <ui:text name="<%=formName%>" property="contactUsCCEmail"/>
	            </td>
	    </ui:element>
    </ui:control>
    <td colspan="2"></td>
</tr>
<tr>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_DEFAULT_EMAIL%>" template="<td></td><td></td>">
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DEFAULT_EMAIL_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DEFAULT_EMAIL_LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.defaultEmail"/>:</ui:label></b>
	        </td>
	    </ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DEFAULT_EMAIL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DEFAULT_EMAIL_VALUE%>">
	                    <ui:text name="<%=formName%>" property="defaultEmail"/>
	            </td>
	    </ui:element>
    </ui:control>
    <td colspan="2"></td>
</tr>

<!-- TODO: Add custom fields  -->
<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_DATA_FIELDS%>" template="<td></td><td></td>" noDefault="true">
	<logic:equal parameter="configMode" value="true">
	<tr>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DATA_FIELDS_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
		<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_DATA_FIELDS_LABEL%>" colspan="4" height="70px">
			<b><ui:label>
				<app:storeMessage key="admin2.account.detail.label.dataFields" />:</ui:label></b>
		</td>
		</ui:element>
	</tr>
	</logic:equal>
	<logic:equal parameter="configMode" value="false">
	   <logic:present name="<%=formName%>" property="dataFieldProperties">
             <bean:size name="<%=formName%>" property="dataFieldProperties" id="dpSize"/>
                <logic:iterate name="<%=formName%>" property="dataFieldProperties" id="dataF" indexId="i">
                    <%if(i.intValue()%2==0){%><tr><%}%>
                    <%String prop = "dataFieldProperty["+i+"].value";%>
                    <logic:equal name="dataF" property="showAdmin" value="true">
                        <td><b><bean:write name="dataF" property="tag"/></b></td>
                        <td><b><html:text name="<%=formName%>" property="<%=prop%>"/></b></td>
                    </logic:equal>
                    <logic:notEqual name="dataF" property="showAdmin" value="true">
                        <td></td><td></td>
                    </logic:notEqual>
                    <bean:define id="modResult" value="<%=Integer.toString((i.intValue() + 1)%2)%>"/>
                    <logic:equal name="modResult" value="0">
                    </logic:equal>
                    <%if((i.intValue()+1)%2==0 || i.intValue()==dpSize.intValue()-1){%>
                    <%if(i.intValue()==dpSize.intValue()-1 && i.intValue()%2==0){%>
                      <td></td><td></td>
                    <%}%>
                    </tr>
                    <%}%>
                </logic:iterate>
        </logic:present>
	</logic:equal>
</ui:control>

</ui:page>