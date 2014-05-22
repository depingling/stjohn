<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>

<app:setLocaleAndImages/>
<body>
<div id="contentWrapper" class="singleColumn clearfix">
	<div id="content">
		<div>
			<h1 class="main"><app:storeMessage key="contactUs.label.contactUs" /></h1>
		</div>
<logic:present name="pages.contactus.text">
		<div class="boxWrapper smallMargin squareBottom">
			<div class="top clearfix"><span class="left">&nbsp;</span><span
					class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
				<div class="content">
					<div class="left clearfix">
						<app:custom pageElement="pages.contactus.text"/>
					</div>
				</div>
			<div class="bottom clearfix"><span class="left">&nbsp;</span><span
				class="center">&nbsp;</span><span class="right">&nbsp;</span>
			</div>
		</div>
</logic:present>
<logic:notPresent name="pages.contactus.text">
<bean:size id="size" name="<%=Constants.APP_USER%>" property="contactUsList"/>
<logic:iterate id="contact" indexId="idx" name="<%=Constants.APP_USER%>" property="contactUsList" type="com.cleanwise.view.utils.ContactUsInfo">
		<div class="boxWrapper smallMargin squareBottom">
			<div class="top clearfix"><span class="left">&nbsp;</span><span
					class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
				<div class="content">
					<div class="left clearfix">
						<h3><bean:write name="contact" property="nickName" /></h3>
					<hr />
					<div class="twoColBox">
						<div class="column width50">
							<table>
								<colgroup>
									<col />
									<col width="65%" />

								</colgroup>
								<tr>
									<td><app:storeMessage key="contactUs.label.contact" /></td>
									<td><bean:write name="contact" property="contactName" /></td>
								</tr>
								<tr>
									<td><app:storeMessage key="contactUs.label.phone" /></td>
									<td><bean:write name="contact" property="phone" /></td>
								</tr>
								<tr>
									<td><app:storeMessage key="contactUs.label.fax" /></td>
									<td><bean:write name="contact" property="fax" /></td>
								</tr>
								<tr>
									<td><app:storeMessage key="contactUs.label.callHours" /></td>
									<td><bean:write name="contact" property="callHours" /></td>
								</tr>
							</table>
						</div>
						<div class="column width50">
							<table>
								<colgroup>
									<col />
									<col width="65%" />
								</colgroup>
								<tr>
									<td><app:storeMessage key="contactUs.label.email" /></td>
									<td><a href="mailto:<bean:write name="contact" property="email" />"><bean:write name="contact" property="email" /></a></td>
								</tr>
								<tr>
									<td><app:storeMessage key="contactUs.label.address" /></td>
									<td>
										<%
											String address1 = contact.getAddress().getAddress1();
											String address2 = contact.getAddress().getAddress2();
											String address3 = contact.getAddress().getAddress3();
											String address4 = contact.getAddress().getAddress4();
											String city = contact.getAddress().getCity();
											String state = contact.getAddress().getStateProvinceCd();
											String postalCode = contact.getAddress().getPostalCode();
											String country = contact.getAddress().getCountryCd();
											String addressFormat = Utility.getAddressFormatFor(country);
										%>
										<eswi18n:formatAddress postalCode="<%=postalCode %>"  
                                					address1="<%=address1 %>" address2="<%=address2 %>" address3="<%=address3 %>" 
                                					address4="<%=address4 %>" city="<%=city %>" state="<%=state %>" 
                                					country="<%=country %>" addressFormat="<%=addressFormat %>"/> 
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="bottom clearfix"><span class="left">&nbsp;</span><span
				class="center">&nbsp;</span><span class="right">&nbsp;</span>
			</div>
		</div>
</logic:iterate>
</logic:notPresent>
	</div>
</div>
</body>