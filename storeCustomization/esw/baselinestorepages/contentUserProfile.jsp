<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:setLocaleAndImages/>
<bean:define id="appUser" name="ApplicationUser" type="com.cleanwise.view.utils.CleanwiseUser"/>
<%
String updateUserProfile= "userportal/esw/userProfile.do?operation=" + Constants.PARAMETER_OPERATION_VALUE_UPDATE_USER_PROFILE;
String changePasswordLink = "userportal/esw/changePassword.do?operation=" + Constants.PARAMETER_OPERATION_VALUE_SHOW_CHANGE_PASSWORD;
%>

<script type="text/javascript">
    function resetForm(){
        document.getElementById("userProfileForm").reset();     
    }
</script>

<body>
    <div id="contentWrapper" class="singleColumn clearfix">
        
            <div id="content">
                <!-- Begin: Error Message -->
                <%
                String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");%>
		<jsp:include page="<%=errorsAndMessagesPage %>"/>
		<!-- End: Error Message -->
                
                <h1 class="main"><app:storeMessage key="shop.userProfile.text.myProfile"/> (<bean:write name="esw.UserProfileForm" property="userInfo.userData.userName"/>)</h1>
                
                <div class="boxWrapper smallMargin squareBottom">
                
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <html:form styleId="userProfileForm" name="esw.UserProfileForm" action="<%=updateUserProfile%>">
                        <html:hidden property="<%=Constants.PARAMETER_OPERATION%>" value="<%=Constants.PARAMETER_OPERATION_VALUE_UPDATE_USER_PROFILE%>"/>
                                
                        <div class="left clearfix">
                            <h1><app:storeMessage key="shop.userProfile.text.personalInformation"/></h1>
                            <p class="required right">
                            * <app:storeMessage key="global.text.required" />
                            </p>
                                    <hr />
                                    <div class="twoColBox">
                                        
                                    <logic:equal name="esw.UserProfileForm" property="storeProfile.profileNameDisplay" value="true">
                                    <div class="column rightPadding">
                                        <table>
                                            <colgroup>
                                                <col />
                                                <col width="55%" />
                                            </colgroup>
                                            <tr>
                                                <td>
                                                    <app:storeMessage key="shop.userProfile.text.firstName"/>: <span class="required">*</span>
                                                </td>
                                                <td class="search">
                                                    <div class="inputWrapper">
                                                        <logic:equal name="esw.UserProfileForm" property="storeProfile.profileNameEdit" value="true">
                                                            <html:text name="esw.UserProfileForm" property="userInfo.userData.firstName" size="30" tabindex="1"/>
                                                        </logic:equal>
                                                        <logic:notEqual name="esw.UserProfileForm" property="storeProfile.profileNameEdit" value="true">
                                                            <bean:write name="esw.UserProfileForm" property="userInfo.userData.firstName"/>
                                                        </logic:notEqual>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <app:storeMessage key="shop.userProfile.text.lastName"/>: <span class="required">*</span>
                                                </td>
                                                <td class="search">
                                                    <div class="inputWrapper">
                                                        <logic:equal name="esw.UserProfileForm" property="storeProfile.profileNameEdit" value="true">
                                                            <html:text name="esw.UserProfileForm" property="userInfo.userData.lastName" size="30" tabindex="1"/>
                                                        </logic:equal>
                                                        <logic:notEqual name="esw.UserProfileForm" property="storeProfile.profileNameEdit" value="true">
                                                            <bean:write name="esw.UserProfileForm" property="userInfo.userData.lastName"/>
                                                        </logic:notEqual>
                                                    </div>
                                                </td>
                                            </tr>
                                                                                    
                                        </table>
                                    </div>
                                    </logic:equal>
				    
                                    <logic:equal name="esw.UserProfileForm" property="storeProfile.languageDisplay" value="true">		
				    <div class="column">
                                        <table>
                                            <colgroup>
                                                <col />
                                                <col width="65%" />
                                            </colgroup>
                                            <tr>
                                                <td>
                                                    <app:storeMessage key="shop.userProfile.text.prefLanguage"/>: <span class="required">*</span>
                                                </td>
						<td class="limit">
                                                    <logic:equal name="esw.UserProfileForm" property="storeProfile.languageEdit" value="true">
                                                        <html:select name="esw.UserProfileForm" property="userInfo.languageData.languageId" tabindex="3">
                                                        <html:option value=""><app:storeMessage  key="admin.select.language"/></html:option>
                                                        <html:options collection="store.languages.options" labelProperty="label" property="value"/>
                                                        </html:select>
                                                    </logic:equal>
                                                    <logic:notEqual name="esw.UserProfileForm" property="storeProfile.languageEdit" value="true">
                                                        <bean:write name="esw.UserProfileForm" property="userInfo.languageData.translatedName"/>
                                                    </logic:notEqual>
						</td>
                                            </tr>
                                        										
					</table>
				    </div>
                                    </logic:equal>
                                    </div>
							
                       
                        
                                    <h1><app:storeMessage key="shop.userProfile.text.contactInformation"/></h1>
                                    <hr />
                                    
                                    <div class="twoColBox">
                                    
                                    <div class="column rightPadding">
                                        <table>
                                            <colgroup>
                                                <col />
                                                <col width="55%" />
                                            </colgroup>
                                            <logic:equal name="esw.UserProfileForm" property="storeProfile.contactAddressDisplay" value="true">
                                            <tr>
				    <td>
                                                    <app:storeMessage key="shop.userProfile.text.address1"/>:
                                                </td>
                                                <td class="search">
                                                    <div class="inputWrapper">
                                                        <logic:equal name="esw.UserProfileForm" property="storeProfile.contactAddressEdit" value="true">
                                                            <html:text name="esw.UserProfileForm" property="userInfo.addressData.address1" size="30" tabindex="4"/>
                                                        </logic:equal>
                                                        <logic:notEqual name="esw.UserProfileForm" property="storeProfile.contactAddressEdit" value="true">
                                                            <bean:write name="esw.UserProfileForm" property="userInfo.addressData.address1"/>
                                                        </logic:notEqual>
						    </div>
						</td>
					    </tr>
					    <tr>
						<td>
                                                    <app:storeMessage key="shop.userProfile.text.address2"/>:
                                                </td>
                                                <td class="search">
						    <div class="inputWrapper">
							<logic:equal name="esw.UserProfileForm" property="storeProfile.contactAddressEdit" value="true">
                                                            <html:text name="esw.UserProfileForm" property="userInfo.addressData.address2" size="30" tabindex="4"/>
                                                        </logic:equal>
                                                        <logic:notEqual name="esw.UserProfileForm" property="storeProfile.contactAddressEdit" value="true">
                                                            <bean:write name="esw.UserProfileForm" property="userInfo.addressData.address2"/>
                                                        </logic:notEqual>
						    </div>
						</td>
					    </tr>
					    <tr>
						<td>
                                                    <app:storeMessage key="shop.userProfile.text.city"/>:
                                                </td>
                                                <td class="search">
						    <div class="inputWrapper">
							<logic:equal name="esw.UserProfileForm" property="storeProfile.contactAddressEdit" value="true">
                                                            <html:text name="esw.UserProfileForm" property="userInfo.addressData.city" size="30" tabindex="4"/>
                                                        </logic:equal>
                                                        <logic:notEqual name="esw.UserProfileForm" property="storeProfile.contactAddressEdit" value="true">
                                                            <bean:write name="esw.UserProfileForm" property="userInfo.addressData.city"/>
                                                        </logic:notEqual>
						    </div>
						</td>
					    </tr>
					    <tr>
						<td>
                                                    <app:storeMessage key="shop.userProfile.text.stateProvince"/>:
                                                </td>
                                                <td class="search">
						    <div class="inputWrapper">
							<logic:equal name="esw.UserProfileForm" property="storeProfile.contactAddressEdit" value="true">
                                                            <html:text name="esw.UserProfileForm" property="userInfo.addressData.stateProvinceCd" size="30" tabindex="4"/>
                                                        </logic:equal>
                                                        <logic:notEqual name="esw.UserProfileForm" property="storeProfile.contactAddressEdit" value="true">
                                                            <bean:write name="esw.UserProfileForm" property="userInfo.addressData.stateProvinceCd"/>
                                                        </logic:notEqual>
						    </div>
						</td>
					    </tr>
					    <tr>
						<td>
                                                    <app:storeMessage key="shop.userProfile.text.zipPostalCode"/>:
                                                </td>
                                                <td class="search">
						    <div class="inputWrapper">
							<logic:equal name="esw.UserProfileForm" property="storeProfile.contactAddressEdit" value="true">
                                                            <html:text name="esw.UserProfileForm" property="userInfo.addressData.postalCode" size="30" tabindex="4"/>
                                                        </logic:equal>
                                                        <logic:notEqual name="esw.UserProfileForm" property="storeProfile.contactAddressEdit" value="true">
                                                            <bean:write name="esw.UserProfileForm" property="userInfo.addressData.postalCode"/>
                                                        </logic:notEqual>
						    </div>
						</td>
					    </tr>
                                            </logic:equal>
                                            
                                            <logic:equal name="esw.UserProfileForm" property="storeProfile.countryDisplay" value="true">
					    <tr>
						<td>
                                                    <app:storeMessage key="shop.userProfile.text.country"/>: <span class="required">*</span>
                                                </td>
                                                <td class="limit">
                                                    <logic:equal name="esw.UserProfileForm" property="storeProfile.countryEdit" value="true">
                                                        <html:select name="esw.UserProfileForm" property="userInfo.countryData.shortDesc" tabindex="9">
                                                        <html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
                                                        <html:options  collection="store.countries.options" property="value" />
                                                        </html:select>
                                                    </logic:equal>
                                                    <logic:notEqual name="esw.UserProfileForm" property="storeProfile.countryEdit" value="true">
                                                        <bean:write name="esw.UserProfileForm" property="userInfo.countryData.shortDesc"/>
                                                    </logic:notEqual>
						</td>
					    </tr>
                                            </logic:equal>
                                        </table>
                                    </div>
                                    
                                    
                                    
				    <div class="column">
                                        <table>
                                            <colgroup>
                                                <col />
                                                <col width="65%" />
                                            </colgroup>
                                            <logic:equal name="esw.UserProfileForm" property="storeProfile.phoneDisplay" value="true">
					    <tr>
						<td>
                                                    <app:storeMessage key="shop.userProfile.text.phone"/>:
                                                </td>
                                                <td class="search">
						    <div class="inputWrapper">
                                                        <logic:equal name="esw.UserProfileForm" property="storeProfile.phoneEdit" value="true">
                                                            <html:text name="esw.UserProfileForm" property="userInfo.phone.phoneNum" size="30" tabindex="10"/>
                                                        </logic:equal>
                                                        <logic:notEqual name="esw.UserProfileForm" property="storeProfile.phoneEdit" value="true">
                                                            <bean:write name="esw.UserProfileForm" property="userInfo.phone.phoneNum"/>
                                                        </logic:notEqual>
						    </div>
						</td>
					    </tr>
                                            </logic:equal>
                                            
                                            <logic:equal name="esw.UserProfileForm" property="storeProfile.mobileDisplay" value="true">
					    <tr>
                                                <td>
                                                    <app:storeMessage key="shop.userProfile.text.mobile"/>:
                                                </td>
                                                <td class="search">
						    <div class="inputWrapper">
                                                        <logic:equal name="esw.UserProfileForm" property="storeProfile.mobileEdit" value="true">
                                                            <html:text name="esw.UserProfileForm" property="userInfo.mobile.phoneNum" size="30" tabindex="11"/>
                                                        </logic:equal>
                                                        <logic:notEqual name="esw.UserProfileForm" property="storeProfile.mobileEdit" value="true">
                                                            <bean:write name="esw.UserProfileForm" property="userInfo.mobile.phoneNum"/>
                                                        </logic:notEqual>
						    </div>
						</td>
					    </tr>
                                            </logic:equal>
                                            
                                            <logic:equal name="esw.UserProfileForm" property="storeProfile.faxDisplay" value="true">
					    <tr>
						<td>
                                                    <app:storeMessage key="shop.userProfile.text.fax"/>:
                                                </td>
                                                <td class="search">
						    <div class="inputWrapper">
                                                        <logic:equal name="esw.UserProfileForm" property="storeProfile.faxEdit" value="true">
                                                            <html:text name="esw.UserProfileForm" property="userInfo.fax.phoneNum" size="30" tabindex="12"/>
                                                        </logic:equal>
                                                        <logic:notEqual name="esw.UserProfileForm" property="storeProfile.faxEdit" value="true">
                                                            <bean:write name="esw.UserProfileForm" property="userInfo.fax.phoneNum"/>
                                                        </logic:notEqual>
						    </div>
						</td>
					    </tr>
                                            </logic:equal>
                                            
                                            <logic:equal name="esw.UserProfileForm" property="storeProfile.emailDisplay" value="true">
					    <tr>
						<td>
                                                    <app:storeMessage key="shop.userProfile.text.email"/>:
                                                </td>
                                                <td class="search">
						    <div class="inputWrapper">
							
                                                        <logic:equal name="esw.UserProfileForm" property="storeProfile.emailEdit" value="true">
                                                           <html:text name="esw.UserProfileForm" property="userInfo.emailData.emailAddress" size="30" tabindex="13"/>
                                                        </logic:equal>
                                                        <logic:notEqual name="esw.UserProfileForm" property="storeProfile.emailEdit" value="true">
                                                            <bean:write name="esw.UserProfileForm" property="userInfo.emailData.emailAddress"/>
                                                        </logic:notEqual>
						    </div>
						</td>
					    </tr>
                                            </logic:equal>
					</table>
				    </div>
				    </div>
                                    <hr />
                                    
                                    <html:link href="javascript:submitForm('userProfileForm');" styleClass="blueBtn">
					    <span>
                                                <app:storeMessage key="global.action.label.save"/>
					    </span>
					</html:link>
					
                                        <html:link href="javascript:resetForm();" styleClass="blueBtn">
					    <span>
                                                <app:storeMessage key="global.action.label.reset"/>
					    </span>
					</html:link>
                                        
					<logic:equal name="esw.UserProfileForm" property="storeProfile.changePassword" value="true">
					
                                            <html:link action="<%=changePasswordLink%>" styleClass="blueBtn">
                                                <span>
                                                    <app:storeMessage key="global.action.label.changePassword"/>
                                                </span>
                                            </html:link>
                                        
                                        </logic:equal>
                                </div>
                                </html:form>
                            </div>
                            <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                        </div>

                </div>
            </div>
    </div>
</body>
