<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="TRADING_PARTNER_FORM" type="com.cleanwise.view.forms.TradingPartnerMgrForm"/>

<div class="text">
  <table width="769" cellpadding="4" border="0" class="mainbody">
  <html:form name="TRADING_PARTNER_FORM"
    action="adminportal/tradingpartnermgr.do"
    type="com.cleanwise.view.forms.TradingPartnerMgrForm">

<bean:parameter id="reqAction" name="action" value="---" />
<bean:define id="trpid" name="TRADING_PARTNER_FORM"
  property="tradingPartner.tradingPartnerId"
  type="java.lang.Integer"/>
<html:hidden name="TRADING_PARTNER_FORM"
  property="tradingPartner.tradingPartnerId"
  value="<%=trpid.toString()%>"
  />

<hr>
<b>Site Identifier Type:</b>
<html:select name="TRADING_PARTNER_FORM" property="tradingPartner.siteIdentifierTypeCd" styleId="form2_siteIdentifierTypeCd">
<html:option value="<%=RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_ACCOUNT_IN_REF%>">Account in Ref Segment</html:option>
<html:option value="<%=RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_SITE_REF_NUMBER%>">Account in Ref Segment, Use Customer Site Id</html:option>
<html:option value="<%=RefCodeNames.SITE_IDENTIFIER_TYPE_CD.CONCATONATED%>">Concatenated</html:option>
<html:option value="<%=RefCodeNames.SITE_IDENTIFIER_TYPE_CD.DIST_SITE_REFERENCE_NUMBER%>">DIST_SITE_REFERENCE_NUMBER</html:option>
</html:select>

<hr>
<b>Account identifier inbound:</b>
<html:select name="TRADING_PARTNER_FORM" property="tradingPartner.accountIdentifierInbound" styleId="form2_accountIdentifierInbound">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:option value="<%=RefCodeNames.ACCOUNT_IDENTIFIER_INBOUND.ACCT_IDENTIFIER_IN_N1_LOOP%>">Account Identifier In N1 Loop</html:option>
</html:select>

<hr>
<script type="text/javascript">
function onOffAllowEmail(f) {
    if (f.checked) {
        f.form.allow856Email.disabled = false;
        f.form.allow856Email.readonly = false;
        f.form.allow856Email.focus();
    } else {
        f.form.allow856Email.disabled = true;
        f.form.allow856Email.readonly = true;
    }
}
</script>
<b>Allow 856 to process:</b>
<html:checkbox  name="TRADING_PARTNER_FORM"  property="allow856Flag" onclick="onOffAllowEmail(this)" styleId="form2_allow856Flag"/>
&nbsp;&nbsp;&nbsp;
<b>Email Address: </b><html:text name="TRADING_PARTNER_FORM" property="allow856Email" size="10" styleId="form2_allow856Email"/>




<br>
<br>
<hr>
<b>Data Exchanges</b>
<br>
<table align=center width=750>
<tr>
<th bgcolor=#ffffcc> Incoming Profile Id </td>
<th> Direction </th>
<th bgcolor=#ffffcc> Data Type </th>
<th> Outgoing Profile Id </th>
<th bgcolor=#ffffcc> Classname </th>
<th> Pattern </th>
<th bgcolor=#ffffcc> Delete </th>
</tr>

<logic:present name="TRADING_PARTNER_FORM" property="tradingDataExchanges">

<% int previd = 0; %>

<logic:iterate id="dataex" name="TRADING_PARTNER_FORM" property="tradingDataExchanges" indexId="didx">

<bean:define id="itpid" name="dataex" property="incomingTradingProfileId"
  type="java.lang.Integer" />

<% if ( previd == itpid.intValue() ) { %>
<td bgcolor=#ffffcc></td>
<% } else {
previd = itpid.intValue();
%>
<td bgcolor=#ffffcc><bean:write name="dataex" property="incomingTradingProfileId" /> </td>
<% } %>

<td><bean:write name="dataex" property="direction" /></td>
<td bgcolor=#ffffcc>
 	<bean:write name="dataex" property="setType" />
</td>
<td>
	<logic:equal name="dataex" property="direction" value="OUT">
		<bean:write name="dataex" property="tradingProfileId" />
	</logic:equal>
</td>
<td bgcolor=#ffffcc> <bean:write name="dataex" property="classname" /> </td>
<td><bean:write name="dataex" property="pattern" /></td>
<td bgcolor=#ffffcc align="center">
	<bean:define id="cfgid" name="dataex" property="tradingProfileConfigId" />
	<a href="tradingpartnermgr.do?action=delete_data_exchange&exchangeId=<%= cfgid %>">[Delete]</a>
</td>
 </tr>

</logic:iterate>
</logic:present>

</table>

<hr>

<table align=center width=750>
	<tr>
		<td><b>Direction:</b></td>
		<td><b>Inbound Trading Profile:</b></td>
		<logic:equal name="TRADING_PARTNER_FORM"  property="dataExchangeDirection" value="OUT">
			<td><b>Outbound Trading Profile:</b></td>
		</logic:equal>
		<td><b>Transaction Type:</b></td>
		<td><b>Classname:</b></td>
		<logic:equal name="TRADING_PARTNER_FORM"  property="dataExchangeDirection" value="IN">
			<td><b>Pattern (Regex):</b></td>
		</logic:equal>
	</tr>
	<tr>
		<td><html:hidden property="tradingTypeChange" value="" />
			<html:select name="TRADING_PARTNER_FORM"  property="dataExchangeDirection" onchange="this.form.tradingTypeChange.value='dataExchangeDirection';this.form.submit();">
                                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                <html:option value="IN">IN</html:option>
				<html:option value="OUT">OUT</html:option>
                        </html:select>
		</td>
                <td>
                        <html:select name="TRADING_PARTNER_FORM"  property="dataExchangeInProfileId">
                                <logic:present name="trading.profile.vector" >
                                        <html:options  collection="trading.profile.vector" property="tradingProfileId" />
                                </logic:present>
                        </html:select>
                </td>
		<logic:equal name="TRADING_PARTNER_FORM"  property="dataExchangeDirection" value="OUT">
			<td>
				<html:select name="TRADING_PARTNER_FORM"  property="dataExchangeOutProfileId">
					<logic:present name="trading.profile.vector" >
						<html:options  collection="trading.profile.vector" property="tradingProfileId" />
					</logic:present>
				</html:select>
			</td>
		</logic:equal>
		<td>
			<html:select name="TRADING_PARTNER_FORM"  property="dataExchangeTransactionType">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<logic:equal name="TRADING_PARTNER_FORM"  property="dataExchangeDirection" value="IN">		
					<html:options  collection="trading.partner.inTransactionType" property="value" />
				</logic:equal>
				<logic:equal name="TRADING_PARTNER_FORM"  property="dataExchangeDirection" value="OUT">
					<html:options  collection="trading.partner.outTransactionType" property="value" />
				</logic:equal>
			</html:select>
		</td>
		<td>
			<html:select name="TRADING_PARTNER_FORM"  property="dataExchangeClassName">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<logic:equal name="TRADING_PARTNER_FORM"  property="dataExchangeDirection" value="IN">		
					<html:options  collection="trading.partner.parser" property="value" />
				</logic:equal>
				<logic:equal name="TRADING_PARTNER_FORM"  property="dataExchangeDirection" value="OUT">
					<html:options  collection="trading.partner.generator" property="value" />
				</logic:equal>
			</html:select>
		</td>
		<logic:equal name="TRADING_PARTNER_FORM"  property="dataExchangeDirection" value="IN">
			<td>
				<html:text name="TRADING_PARTNER_FORM"  property="dataExchangePattern" size="10"/>
			</td>
		</logic:equal>
	</tr>
        <tr>
                <td colspan="10" align="center">
                        <html:submit property="action" value="Define Data Exchange"/>
                </td>
        </tr>
</table>

<hr>
<table align=center width=750 border="0">
<tr>
<td><b>Trading Profile(s)</b></td>
<td>
    <b>
    <% 
    	//STJ-6335 - don't allow the user to create a new trading profile before saving
		if (theForm.getTradingPartner().getTradingPartnerId() > 0) {
    %>
        <a href="tradingprofilemgr.do?action=create_profile&profileId=0">Create New Trading Profile</a>
    <% 
		}
    %>
    </b>
</td>
</tr>
</table>

<logic:present name="trading.profile.vector">

<logic:iterate id="profile" name="trading.profile.vector">
<table align=center width=750 border=1>
<tr>
<td><b>TradingProfileId</b></td>
<td><bean:write name="profile" property="tradingProfileId" /> </td>

<bean:define id="tpid" type="java.lang.Integer"
  name="profile" property="tradingProfileId" />
<td colspan = "5">
<a href="tradingprofilemappingmgr.do?action=edit_profile_mapping&profileId=<%= tpid %>">[Edit Mappings/Configuration]</a>
&nbsp;&nbsp;
<a href="tradingprofilemgr.do?action=edit_profile&profileId=<%= tpid %>">[Edit Profile]</a>
&nbsp;&nbsp;
<a href="tradingpartnermgr.do?action=delete_profile&profileId=<%= tpid %>">[Delete Profile]</a>
</td>
</tr>

<tr>
<td>AuthorizationQualifier</td>
<td class="adf"><bean:write name="profile" property="authorizationQualifier" /></td>

<td>Authorization</td>
<td class="adf"><bean:write name="profile" property="authorization"/></td>
</tr>

<tr>
<td>SecurityInfoQualifier</td>
<td class="adf"><bean:write name="profile" property="securityInfoQualifier"/></td>

<td>SecurityInfo</td>
<td class="adf"><bean:write name="profile" property="securityInfo"/></td>
</tr>

<tr>
<td>InterchangeSenderQualifier</td>
<td class="adf"><bean:write name="profile" property="interchangeSenderQualifier"/></td>

<td>InterchangeSender</td>
<td class="adf"><bean:write name="profile" property="interchangeSender"/></td>
</tr>

<tr>
<td>InterchangeReceiverQualifier</td>
<td class="adf"><bean:write name="profile" property="interchangeReceiverQualifier"/></td>

<td>InterchangeReceiver</td>
<td class="adf"><bean:write name="profile" property="interchangeReceiver"/></td>
</tr>

<tr>
<td>InterchangeStandardsId</td>
<td class="adf"><bean:write name="profile" property="interchangeStandardsId"/></td>

<td>InterchangeVersionNum</td>
<td class="adf"><bean:write name="profile" property="interchangeVersionNum"/></td>

<td>InterchangeControlNum</td>
<td class="adf"><bean:write name="profile" property="interchangeControlNum"/></td>
</tr>

<tr>
<td>AcknowledgmentRequested</td>
<td class="adf"><bean:write name="profile" property="acknowledgmentRequested"/></td>

<td>TestIndicator</td>
<td class="adf"><bean:write name="profile" property="testIndicator"/></td>
</tr>

<tr>
<td>SegmentTerminator</td>
<td class="adf"><bean:write name="profile" property="segmentTerminator"/></td>

<td>ElementTerminator</td>
<td class="adf"><bean:write name="profile" property="elementTerminator"/></td>

<td>SubElementTerminator</td>
<td class="adf"><bean:write name="profile" property="subElementTerminator"/></td>
</tr>

<tr>
<td>GroupSender</td>
<td class="adf"><bean:write name="profile" property="groupSender"/></td>

<td>GroupReceiver</td>
<td class="adf"><bean:write name="profile" property="groupReceiver"/></td>

<td>GroupControlNum</td>
<td class="adf"><bean:write name="profile" property="groupControlNum"/></td>
</tr>

<tr>
<td>ResponsibleAgencyCode</td>
<td class="adf"><bean:write name="profile" property="responsibleAgencyCode"/></td>

<td>VersionNum</td>
<td class="adf"><bean:write name="profile" property="versionNum"/></td>

<td>TimeZone</td>
<td class="adf"><bean:write name="profile" property="timeZone"/></td>
</tr>

<tr>
<td>AddDate</td>
<td colspan="2"><bean:write name="profile" property="addDate"/></td>

<td>AddBy</td>
<td><bean:write name="profile" property="addBy"/></td>

</tr>
<tr>
<td>ModDate</td>
<td colspan="2"><bean:write name="profile" property="modDate"/></td>

<td>ModBy</td>
<td><bean:write name="profile" property="modBy"/></td>
</tr>

</table>

</logic:iterate>
</logic:present>

<%theForm.setAcknowledgment("");%>

<tr><td> </html:form> </td></tr>

