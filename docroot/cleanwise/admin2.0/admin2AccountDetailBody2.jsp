<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.value.BusEntityData" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.Admin2Tool" %>

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


<ui:page name="<%=RefCodeNames.UI_PAGE_CD.ACCOUNT_DETAIL%>" 
		 type="<%=RefCodeNames.UI_PAGE_TYPE_CD.ACCOUNT%>" 
		 bean="<%=formName%>" 
		 property="uiPage" 
		 configMode="<%=configParam%>">

<jsp:include page="admin2AccountPrimaryContact.jsp" flush="true">
	<jsp:param name="formName" value="<%=formName%>"/>
	<jsp:param name="configMode" value="<%=configParam%>"/>
</jsp:include> 

<jsp:include page="admin2AccountBillingAddress.jsp" flush="true">
	<jsp:param name="formName" value="<%=formName%>"/>
	<jsp:param name="configMode" value="<%=configParam%>"/>
</jsp:include>

<jsp:include page="admin2AccountOrderContactInf.jsp" flush="true">
	<jsp:param name="formName" value="<%=formName%>"/>
	<jsp:param name="configMode" value="<%=configParam%>"/>
</jsp:include>

<tr>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ORDER_GUIDE_COMMENTS%>" template="<td>&nbsp;</td><td colspan=3>&nbsp;</td>">
       <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_GUIDE_COMMENTS_LABEL%>" >
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_GUIDE_COMMENTS_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.comments"/>:</ui:label></b>
	    </ui:element>
       </td>
       <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_GUIDE_COMMENTS_VALUE%>" colspan="3">
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_GUIDE_COMMENTS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXTAREA%>">
			<ui:textarea name="<%=formName%>" property="comments" rows="4" cols="60"></ui:textarea>		                    
        </ui:element>
		</td>
     </ui:control>	
</tr>

<tr>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ORDER_GUIDE_NOTE%>" template="<td>&nbsp;</td><td colspan=3>&nbsp;</td>">
	   <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_GUIDE_NOTE_LABEL%>">
			<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_GUIDE_NOTE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.orderGuideNote"/>:</ui:label></b>
	        </ui:element>
	   </td>
       <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_GUIDE_NOTE_VALUE%>" colspan="3">
           <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_GUIDE_NOTE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXTAREA%>">
               <ui:textarea name="<%=formName%>" property="orderGuideNote" rows="4" cols="60"/>
           </ui:element>
        </td>
     </ui:control>	
</tr>

<tr>
    <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ORDER_SKU%>" template="<td>&nbsp;</td><td>&nbsp;</td>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_SKU_LABEL%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_SKU_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.skuTag"/>:</ui:label></b>
            </ui:element>
        </td>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_SKU_VALUE%>">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_SKU_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <ui:text name="<%=formName%>" property="skuTag"/>
            </ui:element>
        </td>
    </ui:control>
    <td colspan="2">&nbsp;</td>
</tr>


<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_RUNTIME_DISPLAY_ORDER_ITEM_ACTION_TYPES%>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_RUNTIME_DISPLAY_ORDER_ITEM_ACTION_TYPES_LABEL%>">
    <tr>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_RUNTIME_DISPLAY_ORDER_ITEM_ACTION_TYPES_LABEL%>" colspan="2">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_RUNTIME_DISPLAY_ORDER_ITEM_ACTION_TYPES_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.runtimeDisplayOrderItemActionTypes"/>:</ui:label></b>
            </ui:element><br>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_RUNTIME_DISPLAY_ORDER_ITEM_ACTION_TYPES_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
                <ui:select name="<%=formName%>" property="runtimeDisplayOrderItemActionTypes" multiple="true" size="7">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.ORDER_ITEM_DETAIL_ACTION_CD%>">
                        <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.ORDER_ITEM_DETAIL_ACTION_CD%>" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.ORDER_ITEM_DETAIL_ACTION_CD." + ((String) refCd.getValue()).toUpperCase());%>
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
            </ui:element>
        </td>
        <td colspan="2">&nbsp;</td>
    </tr>
</ui:control>


<tr>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_TAXABLE_INDICATOR%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TAXABLE_INDICATOR_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TAXABLE_INDICATOR_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TAXABLE_INDICATOR_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="taxableIndicator"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TAXABLE_INDICATOR_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.taxableIndicator"/></ui:label></b>
        </ui:element>
       </td>
   </ui:control>
  <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ALLOW_CHANGE_PASSWORD%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_CHANGE_PASSWORD_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_CHANGE_PASSWORD_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_CHANGE_PASSWORD_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="allowChangePassword"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_CHANGE_PASSWORD_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.allowChangePassword"/></ui:label></b>
        </ui:element>
       </td>
  </ui:control>
</tr>

<tr>
  <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_CRC_SHOP%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CRC_SHOP_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CRC_SHOP_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CRC_SHOP_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="crcShop"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CRC_SHOP_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.crcShop"/></ui:label></b>
        </ui:element>
       </td>
  </ui:control>
<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_FREIGHT_CHARGE_TYPE%>" template="<td colspan=2>&nbsp;</td>">
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FREIGHT_CHARGE_TYPE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FREIGHT_CHARGE_TYPE_LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.freightChargeType"/>:</ui:label></b>
        </td>
    </ui:element>
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FREIGHT_CHARGE_TYPE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
    	<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FREIGHT_CHARGE_TYPE_VALUE%>">
                <ui:select name="<%=formName%>" property="freightChargeType">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.FREIGHT_CHARGE_CD%>">
                        <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.FREIGHT_CHARGE_CD%>" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.FREIGHT_CHARGE_CD." + ((String) refCd.getValue()).toUpperCase());%>
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
    <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_MAKE_SHIP_TO_BILL_TO%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_MAKE_SHIP_TO_BILL_TO_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_MAKE_SHIP_TO_BILL_TO_LABEL%>" colspan="2">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_MAKE_SHIP_TO_BILL_TO_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                <ui:checkbox  name="<%=formName%>" property="makeShipToBillTo"/>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_MAKE_SHIP_TO_BILL_TO_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.makeShipToBillTo"/></ui:label></b>
            </ui:element>
        </td>
    </ui:control>
    <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ORDER_MANAGER_EMAILS%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_MANAGER_EMAILS_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_MANAGER_EMAILS_LABEL%>" colspan="2">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_MANAGER_EMAILS_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.orderManagerEmails"/></ui:label></b>
            </ui:element><br>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ORDER_MANAGER_EMAILS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <ui:text name="<%=formName%>" property="orderManagerEmails" size="50"/>
            </ui:element>
        </td>
    </ui:control>
</tr>
<tr>
  <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_SHOW_SCHEDULED_DELIVERY%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_SCHEDULED_DELIVERY_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_SCHEDULED_DELIVERY_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_SCHEDULED_DELIVERY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="showScheduledDelivery"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_SCHEDULED_DELIVERY_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.showScheduledDelivery"/></ui:label></b>
        </ui:element>
       </td>
  </ui:control>
    <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_EDI_SHIP_TO_PREFIX%>" template="<td colspan=2>&nbsp;</td>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_EDI_SHIP_TO_PREFIX_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_EDI_SHIP_TO_PREFIX_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.ediShipToPrefix"/></ui:label></b>
            </td>
	    </ui:element>
	    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_EDI_SHIP_TO_PREFIX_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
	            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_EDI_SHIP_TO_PREFIX_VALUE%>">
	               <ui:text name="<%=formName%>" property="ediShipToPrefix" size="10"/>
		        </td>
	    </ui:element>
    </ui:control>
</tr>
<tr>
  <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_CUSTOMER_REQUEST_PO_ALLOWED%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CUSTOMER_REQUEST_PO_ALLOWED_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CUSTOMER_REQUEST_PO_ALLOWED_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CUSTOMER_REQUEST_PO_ALLOWED_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="customerRequestPoAllowed"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CUSTOMER_REQUEST_PO_ALLOWED_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.customerRequestPoAllowed"/></ui:label></b>
        </ui:element>
       </td>
  </ui:control>
  <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_RUSH_ORDER_CHARGE%>" template="<td colspan=2>&nbsp;</td>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_RUSH_ORDER_CHARGE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_RUSH_ORDER_CHARGE_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.rushOrderCharge"/>:</ui:label></b>
            </td>
	    </ui:element>
	    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_RUSH_ORDER_CHARGE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
	            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_RUSH_ORDER_CHARGE_VALUE%>">
		            <ui:text name="<%=formName%>" property="rushOrderCharge"/>
	            </td>
	    </ui:element>
    </ui:control>
</tr>
<tr>
  <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_AUTHORIZED_FOR_RESALE%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_AUTHORIZED_FOR_RESALE_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_AUTHORIZED_FOR_RESALE_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_AUTHORIZED_FOR_RESALE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="authorizedForResale"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_AUTHORIZED_FOR_RESALE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.authorizedForResale"/></ui:label></b>
        </ui:element>
       </td>
  </ui:control>
    <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_RE_SALE_ERP_NUMBER%>" template="<td colspan=2>&nbsp;</td>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_RE_SALE_ERP_NUMBER_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_RE_SALE_ERP_NUMBER_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.reSaleAccountErpNumber"/>:</ui:label></b>
            </td>
	    </ui:element>
	    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_RE_SALE_ERP_NUMBER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
	            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_RE_SALE_ERP_NUMBER_VALUE%>">
                    <ui:text name="<%=formName%>" property="reSaleAccountErpNumber" size="30" maxlength="30"/>
	            </td>
	    </ui:element>
    </ui:control>
</tr>
<tr>
  	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ALLOW_ORDER_CONSOLIDATION%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_ORDER_CONSOLIDATION_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_ORDER_CONSOLIDATION_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_ORDER_CONSOLIDATION_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="allowOrderConsolidation"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_ORDER_CONSOLIDATION_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.allowOrderConsolidation"/></ui:label></b>
        </ui:element>
       </td>
	</ui:control>
  	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_SCHEDULE_CUTOFF_DAYS%>" template="<td colspan=2>&nbsp;</td>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SCHEDULE_CUTOFF_DAYS_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SCHEDULE_CUTOFF_DAYS_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.scheduleCutoffDays"/>:</ui:label></b>
            </td>
	</ui:element>
	<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SCHEDULE_CUTOFF_DAYS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
	            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SCHEDULE_CUTOFF_DAYS_VALUE%>">
                    <ui:text name="<%=formName%>" property="scheduleCutoffDays" size="5"/>
	            </td>
	    </ui:element>
    </ui:control>
</tr>
<tr>
  	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_SHOW_DIST_SKU_NUM%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_DIST_SKU_NUM_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_DIST_SKU_NUM_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_DIST_SKU_NUM_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="showDistSkuNum"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_DIST_SKU_NUM_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.showDistSkuNum"/></ui:label></b>
        </ui:element>
       </td>
	</ui:control>
  	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_FOLDER%>" template="<td colspan=2>&nbsp;</td>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FOLDER_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FOLDER_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.accountFolder"/>:</ui:label></b>
            </td>
		</ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FOLDER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
			<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FOLDER_VALUE%>">
				<ui:text name="<%=formName%>" property="accountFolder" size="30"/>
			</td>
		</ui:element>
    </ui:control>
</tr>
<tr>
  	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_SHOW_DIST_DELIVERY_DATE%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_DIST_DELIVERY_DATE_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_DIST_DELIVERY_DATE_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_DIST_DELIVERY_DATE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox name="<%=formName%>" property="showDistDeliveryDate"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_DIST_DELIVERY_DATE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.showDistDeliveryDate"/></ui:label></b>
        </ui:element>
       </td>
	</ui:control>
  	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_SHOW_SPL%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_SPL_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_SPL_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_SPL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="showSPL"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_SPL_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.showSPL"/></ui:label></b>
        </ui:element>
       </td>
	</ui:control>
</tr>
<tr>
  	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_HOLD_PO%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_HOLD_PO_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_HOLD_PO_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_HOLD_PO_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="holdPO"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_HOLD_PO_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.holdPO"/></ui:label></b>
        </ui:element>
       </td>
	</ui:control>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_AUTO_ORDER_FACTOR%>" template="<td colspan=2>&nbsp;</td>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_AUTO_ORDER_FACTOR_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_AUTO_ORDER_FACTOR_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.autoOrderFactor"/>:</ui:label></b><span class="reqind">*</span>
            </td>
		</ui:element>
		<ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_AUTO_ORDER_FACTOR_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
	            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_AUTO_ORDER_FACTOR_VALUE%>">
                    <ui:text name="<%=formName%>" property="autoOrderFactor" size="5"/>
	            </td>
	    </ui:element>
    </ui:control>
</tr>
<tr>
  	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_MODIFY_QTY_BY_855%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_MODIFY_QTY_BY_855_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_MODIFY_QTY_BY_855_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_MODIFY_QTY_BY_855_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="modifyQtyBy855"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_MODIFY_QTY_BY_855_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.modifyQtyBy855"/></ui:label></b>
        </ui:element>
       </td>
	</ui:control>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ALLOW_ORDER_INV_ITEMS%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_ORDER_INV_ITEMS_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_ORDER_INV_ITEMS_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_ORDER_INV_ITEMS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="allowOrderInvItems"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_ORDER_INV_ITEMS_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.allowOrderInvItems"/></ui:label></b>
        </ui:element>
       </td>
	</ui:control>
</tr>
<tr>
  	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ALLOW_REORDER%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_REORDER_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_REORDER_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_REORDER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="allowReorder"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_REORDER_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.allowReorder"/></ui:label></b>
        </ui:element>
       </td>
	</ui:control>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_USE_PHYSICAL_INVENTORY%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_USE_PHYSICAL_INVENTORY_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_USE_PHYSICAL_INVENTORY_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_USE_PHYSICAL_INVENTORY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="usePhysicalInventory"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_USE_PHYSICAL_INVENTORY_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.usePhysicalInventory"/></ui:label></b>
        </ui:element>
       </td>
	</ui:control>
</tr>
<tr>
  	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_CREATE_ORDER_BY_855%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CREATE_ORDER_BY_855_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CREATE_ORDER_BY_855_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CREATE_ORDER_BY_855_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="createOrderBy855"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CREATE_ORDER_BY_855_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.createOrderBy855"/></ui:label></b>
        </ui:element>
       </td>
	</ui:control>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_SHOP_UI_TYPE%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOP_UI_TYPE_LABEL%>">
		<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOP_UI_TYPE_LABEL%>" colspan="2">
	    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOP_UI_TYPE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
	            <b><ui:label><app:storeMessage key="admin2.account.detail.label.shopUIType"/>:</ui:label></b>
	    </ui:element>
	    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOP_UI_TYPE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
	                <ui:select name="<%=formName%>" property="shopUIType">
	                    <html:option value="">
	                        <app:storeMessage  key="admin2.select"/>
	                    </html:option>
	                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.SHOP_UI_TYPE%>">
	                        <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.SHOP_UI_TYPE%>" type="com.cleanwise.service.api.value.RefCdData">
	                            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.SHOP_UI_TYPE." + ((String) refCd.getValue()).toUpperCase());%>
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
	    </ui:element>
  	</td>
  </ui:control>
</tr>
<tr>
  <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ALLOW_MODERN_SHOPPING%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_MODERN_SHOPPING_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_MODERN_SHOPPING_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_MODERN_SHOPPING_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="allowModernShopping"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_MODERN_SHOPPING_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.allowModernShopping"/></ui:label></b>
        </ui:element>&nbsp;&nbsp;
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FOLDER_NEW_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.accountFolderNew"/>:</ui:label></b>
	    </ui:element><br>
	    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FOLDER_NEW_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
              <ui:text name="<%=formName%>" property="accountFolderNew" size="30"/>
	    </ui:element>
	    </td>
    </ui:control>
	<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_FAQ_LINK%>" template="<td colspan=2>&nbsp;</td>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FAQ_LINK_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FAQ_LINK_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.faqLink"/>:</ui:label></b>
            </td>
	    </ui:element>
	    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FAQ_LINK_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
	            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_FAQ_LINK_VALUE%>">
	                <ui:text name="<%=formName%>" property="faqLink" size="30"/>
	            </td>
	    </ui:element>
    </ui:control>
</tr>
<tr>
    
    <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_REBATE_PERSENT%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_REBATE_PERSENT_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_REBATE_PERSENT_LABEL%>" colspan="2">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_REBATE_PERSENT_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.rebatePersent"/>:</ui:label></b>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_REBATE_PERSENT_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <ui:text name="<%=formName%>" property="rebatePersent" size="4"/>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_REBATE_EFF_DATE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.rebateEffDate"/>:</ui:label></b>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_REBATE_EFF_DATE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <ui:dojoInputDateProgrammaticTag name="<%=formName%>" property="rebateEffDate" id="rebateEffDate" module="clw.admin2"/>
            </ui:element>
        </td>
    </ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_SHOW_DIST_INVENTORY%>" template="<td colspan=2>&nbsp;</td>">
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_DIST_INVENTORY_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_DIST_INVENTORY_LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.showDistInventory"/>:</ui:label></b>
        </td>
    </ui:element>
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_DIST_INVENTORY_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
    	<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_DIST_INVENTORY_VALUE%>">
                <ui:select name="<%=formName%>" property="showDistInventory">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.DIST_INVENTORY_DISPLAY%>">
                        <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.DIST_INVENTORY_DISPLAY%>" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.DIST_INVENTORY_DISPLAY." + ((String) refCd.getValue()).toUpperCase());%>
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
   <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_TARGET_MARGIN_STR%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TARGET_MARGIN_STR_LABEL%>">
		<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TARGET_MARGIN_STR_LABEL%>" colspan="2" >
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TARGET_MARGIN_STR_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
             <b><ui:label><app:storeMessage key="admin2.account.detail.label.targetMarginStr"/>:</ui:label></b>
	    </ui:element>
	    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_TARGET_MARGIN_STR_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
	                    <ui:text name="<%=formName%>" property="targetMarginStr" size="10" maxlength="10"/>
	    </ui:element>
		</td>	    
    </ui:control>
<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_CUSTOMER_SYSTEM_APPROVAL%>" template="<td colspan=2>&nbsp;</td>">
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CUSTOMER_SYSTEM_APPROVAL_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CUSTOMER_SYSTEM_APPROVAL_LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.customerSystemApprovalCd"/>:</ui:label></b>
        </td>
    </ui:element>
    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CUSTOMER_SYSTEM_APPROVAL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
    	<td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CUSTOMER_SYSTEM_APPROVAL_VALUE%>">
                <ui:select name="<%=formName%>" property="customerSystemApprovalCd">
                    <html:option value="">
                        <app:storeMessage  key="admin2.select"/>
                    </html:option>
                    <logic:present name="<%=Admin2Tool.FORM_VECTORS.CUSTOMER_SYSTEM_APPROVAL_CD%>">
                        <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.CUSTOMER_SYSTEM_APPROVAL_CD%>" type="com.cleanwise.service.api.value.RefCdData">
                            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.CUSTOMER_SYSTEM_APPROVAL_CD." + ((String) refCd.getValue()).toUpperCase());%>
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
  <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_SHOW_INV_CART_TOTAL%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_INV_CART_TOTAL_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_INV_CART_TOTAL_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_INV_CART_TOTAL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="showInvCartTotal"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_INV_CART_TOTAL_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.showInvCartTotal"/></ui:label></b>
        </ui:element>
       </td>
    </ui:control>
	   <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_CART_REMINDER_INTERVAL%>" template="<td colspan=2>&nbsp;</td>">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CART_REMINDER_INTERVAL_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CART_REMINDER_INTERVAL_LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.cartReminderInterval"/>:</ui:label></b>
            </td>
	    </ui:element>
	    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CART_REMINDER_INTERVAL_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
	            <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CART_REMINDER_INTERVAL_VALUE%>">
	                    <ui:text name="<%=formName%>" property="cartReminderInterval" size="10" maxlength="10"/>
	            </td>
	    </ui:element>
    </ui:control>
</tr>

<tr>
  <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_SHOW_MY_SHOPPING_LISTS%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_MY_SHOPPING_LISTS_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_MY_SHOPPING_LISTS_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_MY_SHOPPING_LISTS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="showMyShoppingLists"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_MY_SHOPPING_LISTS_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.showMyShoppingLists"/></ui:label></b>
        </ui:element>
       </td>
    </ui:control>
  <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ALLOW_CREDIT_CARD%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_CREDIT_CARD_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_CREDIT_CARD_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_CREDIT_CARD_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="allowCreditCard"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ALLOW_CREDIT_CARD_LABEL%>"type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.allowCreditCard"/></ui:label></b>
        </ui:element>
       </td>
    </ui:control>
</tr>

<tr>
  <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_SHOW_EXPRESS_ORDER%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_EXPRESS_ORDER_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_EXPRESS_ORDER_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_EXPRESS_ORDER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="showExpressOrder"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_SHOW_EXPRESS_ORDER_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.showExpressOrder"/></ui:label></b>
        </ui:element>
       </td>
    </ui:control>
    <ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_ADD_SERVICE_FEE%>" template="<td colspan=2>&nbsp;</td>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ADD_SERVICE_FEE_LABEL%>">
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ADD_SERVICE_FEE_LABEL%>" colspan="2">
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ADD_SERVICE_FEE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
            <ui:checkbox  name="<%=formName%>" property="addServiceFee"/>
        </ui:element>
        <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ADD_SERVICE_FEE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
            <b><ui:label><app:storeMessage key="admin2.account.detail.label.addServiceFee"/></ui:label></b>
        </ui:element>
       </td>
    </ui:control>
</tr>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_CONNECTION_CUSTOMER%>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONNECTION_CUSTOMER_LABEL%>">
    <tr>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONNECTION_CUSTOMER_LABEL%>" colspan="2">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONNECTION_CUSTOMER_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>">
                <ui:checkbox  name="<%=formName%>" property="connectionCustomer"/>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_CONNECTION_CUSTOMER_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.connectionCustomer"/></ui:label></b>
            </ui:element>
        </td>
        <td colspan="2">&nbsp;</td>
    </tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_PDF_ORDER_CLASS%>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PDF_ORDER_CLASS_LABEL%>">
    <tr>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PDF_ORDER_CLASS_LABEL%>" colspan="2">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PDF_ORDER_CLASS_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.pdfOrderClass"/></ui:label></b>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PDF_ORDER_CLASS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <ui:text name="<%=formName%>" property="pdfOrderClass" size="60"/>
            </ui:element>
        </td>
        <td colspan="2">&nbsp;</td>
    </tr>
</ui:control>

<ui:control name="<%=RefCodeNames.UI_CONTROL.ACCOUNT_PDF_ORDER_STATUS_CLASS%>" targets="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PDF_ORDER_STATUS_CLASS_LABEL%>">
    <tr>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PDF_ORDER_STATUS_CLASS_LABEL%>" colspan="2">
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PDF_ORDER_STATUS_CLASS_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                <b><ui:label><app:storeMessage key="admin2.account.detail.label.pdfOrderStatusClass"/></ui:label></b>
            </ui:element>
            <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_PDF_ORDER_STATUS_CLASS_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                <ui:text name="<%=formName%>" property="pdfOrderStatusClass" size="60"/>
            </ui:element>
        </td>
        <td colspan="2">&nbsp;</td>
    </tr>
</ui:control>

</ui:page>