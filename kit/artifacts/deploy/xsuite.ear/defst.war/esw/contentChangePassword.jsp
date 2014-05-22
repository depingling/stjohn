<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
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
<script type="text/javascript">
 function submitChangePassword() {
        var frm = document.getElementById("userProfileForm");
        if (frm) {
            frm.action = "changePassword.do";
            frm.elements["<%=Constants.PARAMETER_OPERATION%>"].value = "<%=Constants.PARAMETER_OPERATION_VALUE_UPDATE_PASSWORD%>";
            frm.submit();
        }
    }
    
    function cancelPassword(){
       var frm = document.getElementById("userProfileForm");
        if (frm) {
            frm.action = "userProfile.do";
            frm.elements["<%=Constants.PARAMETER_OPERATION%>"].value = "<%=Constants.PARAMETER_OPERATION_VALUE_CANCEL_PASSWORD%>";
            frm.submit();
        }
    }
    
    function clearForm(){
      var frm = document.getElementById("userProfileForm");
          frm.elements['newPassword'].value = "";
          frm.elements['oldPassword'].value = "";
          frm.elements['confirmPassword'].value = "";
    }
</script>
<%
String updatePasswordLink= "userportal/esw/changePassword.do?operation=" + Constants.PARAMETER_OPERATION_VALUE_UPDATE_PASSWORD;
%>
<body onLoad="clearForm()">
 <div id="contentWrapper" class="singleColumn clearfix">
        
            <div id="content">
                <!-- Begin: Error Message -->
                <%
		  String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");%>
		   <jsp:include page="<%=errorsAndMessagesPage %>"/>
	       <!-- End: Error Message -->
               
               <h1 class="main"><app:storeMessage key="shop.userProfile.text.myProfile"/> (<bean:write name="esw.UserProfileForm" property="userInfo.userData.userName"/>)</h1>
                
                <!-- Start Box -->
                <div class="boxWrapper smallMargin squareBottom">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                     
                     <html:form styleId="userProfileForm" name="esw.UserProfileForm" action="<%=updatePasswordLink%>">
                        <html:hidden property="<%=Constants.PARAMETER_OPERATION%>" value="<%=Constants.PARAMETER_OPERATION_VALUE_UPDATE_PASSWORD%>"/>
                        <div class="left clearfix">						
                            <h2><app:storeMessage key="global.action.label.changePassword"/></h2>
                            <p class="required right">
                            * <app:storeMessage key="global.text.required" />
                            </p>
                            
                            <div class="twoColBox">
                                <div class="column rightPadding">
                                    <table>
                                        <colgroup>
                                            <col />
                                            <col width="55%" />
                                        </colgroup>
                                        <tr>
					    <td>
                                                <app:storeMessage key="shop.userProfile.text.oldPassword"/><span class="required">*</span>
                                            </td>
                                            <td class="search">
						<div class="inputWrapper">
                                                    <html:password name="esw.UserProfileForm" property="oldPassword" size="30"/>
						</div>
					   </td>
					</tr>
					<tr>
                                           <td>
                                                <app:storeMessage key="shop.userProfile.text.newPassword"/><span class="required">*</span>
                                            </td>
                                            <td class="search">
						<div class="inputWrapper">
							<html:password name="esw.UserProfileForm" property="newPassword" size="30"/>
						</div>
					    </td>
					</tr>
					<tr>
					   <td>
                                                <app:storeMessage key="shop.userProfile.text.confirmPassword"/><span class="required">*</span>
                                            </td>
                                            <td class="search">
						<div class="inputWrapper">
							<html:password name="esw.UserProfileForm" property="confirmPassword" size="30"/>
						</div>
					   </td>
					</tr>
										
                                    </table>
                                </div>								
				</div>
                        </html:form>
			    <hr />
									
                            <html:link href="javascript:cancelPassword()" styleClass="blueBtn">
                              <span>
                                  <app:storeMessage key="shop.userProfile.text.backToMyProfile"/>
			      </span>
                            </html:link>							
                             <html:link href="javascript:submitChangePassword()" styleClass="blueBtn">
				<span>
                                  <app:storeMessage key="global.action.label.save"/>
				</span>
			    </html:link>
                        </div>						
					</div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
                <!-- End Box -->
            </div>
 </div>
    
</body>