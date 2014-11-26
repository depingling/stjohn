<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.utils.ShopTool" %>
<%@page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<script type="text/javascript" src="../../externals/ckeditor_3.6/ckeditor.js"></script>

<bean:define id="myForm" name="esw.StoreMessageForm"  type="com.espendwise.view.forms.esw.StoreMessageForm"/>
<%
    CleanwiseUser user = ShopTool.getCurrentUser(request);
    boolean canEditMessages = user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_MESSAGES);
    String forcedRead = RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ.equals(myForm.getMessageType()) ? "visible":"hidden";
    boolean showMessage = Constants.PARAMETER_OPERATION_VALUE_PREVIEW_MESSAGE.equals(myForm.getOperation());
    boolean ackPublished = myForm.getStoreMessageId() > 0 && myForm.getPublished()==true && myForm.getMessageType().equals(RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED);
    
%>
<% if (showMessage) { %>
    <script>
    showCoverLayer();
    </script><div id="coverLayer" style="visibility: visible; width: 1423px; height: 1567px;">&nbsp;</div>
    <a title="" class="popUpMessage" id="previewMessageLink" href="storeMessage.do?operation=showMessage"></a>
    <script>
    function showPreviewMessage() {
        actuateLink(document.getElementById('previewMessageLink'));
    }
    window.onload=showPreviewMessage;
    </script>
<% } %>


<app:setLocaleAndImages/>
<!-- Begin: Error Message -->
<%
    String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
%>
<jsp:include page="<%=errorsAndMessagesPage %>"/>
<!-- End: Error Message -->
<!-- Begin: Shopping Sub Tabs -->
<%
    String shoppingTabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "shoppingTabs.jsp");
%>
<jsp:include page="<%=shoppingTabPage%>"/>
<!-- End: Shopping Sub Tabs -->
<div class="singleColumn clearfix" id="contentWrapper">
<div id="content">
<%if (canEditMessages) {%>
<html:form name="esw.StoreMessageForm" styleId="storeMessageForm"
    action="userportal/esw/storeMessage.do"
    type="com.espendwise.view.forms.esw.StoreMessageForm">
<html:hidden name="esw.StoreMessageForm" property="<%=Constants.PARAMETER_OPERATION%>" styleId="operationId" />
<html:hidden name="esw.StoreMessageForm" property="storeMessageId" styleId="storeMessageId" />
<html:hidden name="esw.StoreMessageForm" property="published" styleId="published" />
<html:hidden name="esw.StoreMessageForm" property="selectedFormIndex" styleId="selectedFormIndex" />
                <!-- Start Box -->
                <div class="boxWrapper squareBottom smallMargin">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">
                            <h1 class="main">
                            <logic:equal name="esw.StoreMessageForm" property="isNew" value="true">
                            <app:storeMessage key="userportal.esw.label.createNewMessage"/>
                            </logic:equal>
                            <logic:notEqual name="esw.StoreMessageForm" property="isNew" value="true">
                            <app:storeMessage key="userportal.esw.label.editMessage"/>
                            </logic:notEqual>
                            </h1>
                            <!--  ########  buttons ############ -->
                            <% String confirmPublishVal = "if(isConfirmPublish()) {setFieldsAndSubmitForm('storeMessageForm', 'operationId', '" + Constants.PARAMETER_OPERATION_VALUE_PUBLISH_MESSAGE+"');} else {return false;};";
                            %>
                            <%if (!ackPublished) { %>
                            <a onclick="<%=confirmPublishVal%>" class="blueBtnLargeExt" disabled="<%=ackPublished%>"><span><app:storeMessage key="global.label.publish"/></span></a>
                            <% } %>
                            <a onclick="javascript:setFieldsAndSubmitForm('storeMessageForm', 'operationId', '<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_MESSAGES%>');" class="blueBtnLargeExt"><span><app:storeMessage key="global.action.label.cancel"/></span></a> 
                            <logic:equal name="esw.StoreMessageForm" property="isNew" value="true">
                            <a onclick="javascript:setFieldsAndSubmitForm('storeMessageForm', 'operationId', '<%=Constants.PARAMETER_OPERATION_VALUE_SAVE_MESSAGE%>');" class="blueBtnLargeExt"><span><app:storeMessage key="global.action.label.save"/></span></a>
                            </logic:equal>
                            <hr>
<logic:iterate  id="message"
                name="esw.StoreMessageForm"
                property="detail"
                indexId="i"
                type="com.espendwise.view.forms.esw.StoreMessageDetailForm">
<div class="twoColBox">
    <div class="column rightPadding">
        <table>
            <colgroup>
                <col>
                <col width="65%">
            </colgroup>
            <tbody><tr>             
                <td>
                    <app:storeMessage key="userportal.esw.label.messageTitle"/>: <span class="required">*</span>
                </td>
                <td class="search">
                    <div class="inputWrapper">
                        <html:text name="esw.StoreMessageForm" styleId='<%="messageTitle"+i%>' property='<%="detail["+i+"].messageTitle"%>' size="30" maxlength="128"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td><app:storeMessage key="userportal.esw.label.author"/>: <span class="required">*</span></td>
                <td class="search">
                    <div class="inputWrapper">
                        <html:text property='<%="detail["+i+"].messageAuthor"%>' size="30" maxlength="60"/>
                    </div>
                </td>
            </tr>
        <% if (i==0) {
            String defaultDateFormat = ClwI18nUtil.getDatePattern(request);
            if(Utility.isSet(defaultDateFormat)) {
                defaultDateFormat = defaultDateFormat.toLowerCase();
            }
            String postedDate = defaultDateFormat;
            String endDate = defaultDateFormat;
            if(Utility.isSet(myForm.getPostedDate())) {
                postedDate = myForm.getPostedDate();
            }
            if(Utility.isSet(myForm.getEndDate())) {
                endDate = myForm.getEndDate();
            }
        %>
            <tr>
                <td><app:storeMessage key="userportal.esw.label.effectiveDate"/>:<span class="required">*</td>
                <td class="search">
                    <div class="inputWrapper width50">
                        <html:text styleClass="default-value standardCal" property="postedDate" value="<%=postedDate%>"/>
                    </div>
                </td>
            </tr>  
            <tr>
                <td><app:storeMessage key="userportal.esw.label.expirationDate"/>:<span class="required">*</td>
                <td class="search">
                    <div class="inputWrapper width50">
                        <html:text styleClass="default-value standardCal" property="endDate" value="<%=endDate%>"/>
                    </div>
                </td>
            </tr>
        <% } %>
        </tbody>
        </table>
    </div>  
    <div class="column">
        <table>
    <% if (i==0) { %>
            <colgroup>
                <col width="10%">
                <col width="70%">
                <col>
            </colgroup>
            <tbody>
                    <tr>
                        <td colspan="2"><app:storeMessage key="userportal.esw.label.messageType"/>: <span class="required">*</span></td>
                        <td align="right"><span class="required">* Required</span></td>
                    </tr>
                    <tr>
                        <td>
                            <html:radio property="messageType" value="<%=RefCodeNames.MESSAGE_TYPE_CD.REGULAR%>" onclick="toggle(this)"></html:radio>
                        </td>
                        <td><app:storeMessage key="userportal.esw.label.regular"/></td>
                    </tr>
                    <tr>
                        <td>
                            <html:radio property="messageType" value="<%=RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED%>" onclick="toggle(this)"></html:radio>
                        </td>
                        <td><app:storeMessage key="userportal.esw.label.acknowledgementRequired"/></td>
                    </tr>
                    <tr>
                        <td>
                            <html:radio property="messageType" value="<%=RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ%>" onclick="toggle(this)"></html:radio>
                        </td>
                        <td> 
<div style="display: inline"><app:storeMessage key="userportal.esw.label.forceRead"/>&nbsp;&nbsp;&nbsp;&nbsp;</div>
<% String style = "display: inline; visibility:"+forcedRead+";";%>
<div id="toggleDiv" style="<%=style%>">
Count:
<span class="required">*</span>
<html:text property="forcedReadCount" size="4" maxlength="5" style="width:auto;visibility:true"/>
</div>
                        </td>
                    </tr>
            </tbody>
        <% } else { %>
            <colgroup>
                <col>
                <col width="65%">
            </colgroup>
            <tbody><tr>             
                <td>
                    Language: <span class="required">*</span>
                </td>
                <td class="search">
                    <div class="inputWrapper">
                        <html:select property='<%="detail["+i+"].languageCd"%>'>
                            <html:option value=""><app:storeMessage  key="admin.select.language"/></html:option>
                            <html:options collection="languages.vector" labelProperty="uiName" property="languageCode"/>
                        </html:select>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Country: <span class="required">*</span></td>
                <td class="search">
                    <div class="inputWrapper">
                        <html:select property='<%="detail["+i+"].country"%>'>
                            <html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
                            <html:options  collection="countries.vector" labelProperty="uiName" property="countryCode" />
                        </html:select>                        
                    </div>
                </td>
            </tr>                                                   
        </tbody>
        <% } %>
        </table>
    </div>
</div> <!-- END OF twoColBox -->
<div class="clear row">&nbsp;</div>

<script type="text/javascript">$(function(){ CKEDITOR.replace("messageBody<%=i%>", {
    language: "en_US", uiColor:"rgb(159, 176, 196)",readOnly:false });
})</script>

<div class="boxWrapper">
<h3 class="left">&nbsp;&nbsp;Message Content:</h3><a class="blueBtn right" onclick="javascript:detailFormAction('storeMessageForm', 'operationId', '<%=Constants.PARAMETER_OPERATION_VALUE_PREVIEW_MESSAGE%>', <%=i%>);"><span><app:storeMessage key="admin.button.preview"/></span></a>
<br/>
</div>
<html:textarea  cols='600' rows='1200' styleId='<%="messageBody"+i%>' property='<%="detail["+i+"].messageBody"%>' style="width: 100%; height: 350px; " rows="10" cols="10"/>
<br/><br/>
<%if (!ackPublished) { %>
<% if (i>0) { 
String confirmDeleteVal = "if(isConfirmDelete()) {detailFormAction('storeMessageForm', 'operationId', '" + Constants.PARAMETER_OPERATION_VALUE_DELETE_TRANSLATION+"', "+i+");} else {return false;};";
%>
<a class="blueBtn right" onclick="<%=confirmDeleteVal%>"><span><app:storeMessage key="userportal.esw.label.deleteTranslation"/></span></a>
<% } %>
<% if (i == (myForm.getDetail().size()-1)) { %>
<a class="blueBtn right" onclick="javascript:setFieldsAndSubmitForm('storeMessageForm', 'operationId', '<%=Constants.PARAMETER_OPERATION_VALUE_ADD_TRANSLATION%>');"><span><app:storeMessage key="userportal.esw.label.addTranslation"/></span></a>
<% } %>
<% } %>
<div class="clear row">&nbsp;</div>
<hr>
<br/><br/>
</logic:iterate>
<br>
<!--  ########  buttons ############ -->
<%if (!ackPublished) { %>
<a onclick="<%=confirmPublishVal%>" class="blueBtnLargeExt" disabled="<%=ackPublished%>"><span><app:storeMessage key="global.label.publish"/></span></a>
<% } %>
<a onclick="javascript:setFieldsAndSubmitForm('storeMessageForm', 'operationId', '<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_MESSAGES%>');" class="blueBtnLargeExt"><span><app:storeMessage key="global.action.label.cancel"/></span></a> 
<logic:equal name="esw.StoreMessageForm" property="isNew" value="true">
<a onclick="javascript:setFieldsAndSubmitForm('storeMessageForm', 'operationId', '<%=Constants.PARAMETER_OPERATION_VALUE_SAVE_MESSAGE%>');" class="blueBtnLargeExt"><span><app:storeMessage key="global.action.label.save"/></span></a>
</logic:equal>
<!-- ZZZZZZZZZZZZZ -->  
</div>                      
</div>
</div>
<div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
</div>
                <!-- End Box -->
</html:form>
<%}%>
</div>
</div>

<script>
function isConfirmPublish(){
    var ack = $('input:radio[name=messageType]:checked').val();
    if(ack == '<%=RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED%>'){
        return confirm('<app:storeMessage key="userportal.esw.text.confirmPublish"/>');
    }
    return true;
}

function isConfirmDelete(){
    return confirm('<app:storeMessage key="userportal.esw.text.confirmDelete"/>');
}
function toggle(object) {
    var togElement = document.getElementById('toggleDiv');
    if (object.value == 'FORCE_READ')  {
        togElement.style.visibility = 'visible';
        var forceReadInputEl = document.getElementById('forcedReadCount');
        forceReadInputEl.value = "";
    } else
        togElement.style.visibility = 'hidden';
}

function detailFormAction(formId, hiddenFieldId, hiddenFieldValue, selectedFormIndex) {
    var selectedFormIndexEl = document.getElementById('selectedFormIndex');
    selectedFormIndexEl.value = selectedFormIndex;
    setFieldsAndSubmitForm(formId, hiddenFieldId, hiddenFieldValue);
}
</script>

