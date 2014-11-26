<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="com.espendwise.view.forms.esw.StoreMessageForm"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.utils.ShopTool" %>
<%@page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<bean:define id="myForm" name="esw.StoreMessageForm"  type="com.espendwise.view.forms.esw.StoreMessageForm"/>
<%
    CleanwiseUser user = ShopTool.getCurrentUser(request);
    boolean canEditMessages = user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_MESSAGES);
    String createMessageLink = request.getContextPath() + "/userportal/esw/storeMessage.do?operation="+Constants.PARAMETER_OPERATION_VALUE_CREATE_MESSAGE;
%>
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
<html:hidden name="esw.StoreMessageForm" property="<%=Constants.PARAMETER_OPERATION%>" styleId="operationId"/>
<html:hidden name="esw.StoreMessageForm" property="sortField" styleId="sortField" />
<html:hidden name="esw.StoreMessageForm" property="sortOrder" styleId="sortOrder" />
                <!-- Start Box -->
                <div class="boxWrapper squareBottom smallMargin">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">
                            <h1 class="main"><app:storeMessage key="userportal.esw.label.messagesList" /></h1>
                            <a class="blueBtnLargeExt" href="<%=createMessageLink%>"><span><app:storeMessage key="userportal.esw.label.createMessage" /></span></a>
<logic:empty name="esw.StoreMessageForm" property="storeMessages">
                            <table class="order">
                                <thead>
                                    <tr>
                                        <th><app:storeMessage key="userportal.esw.label.created"/></th>
                                        <th><app:storeMessage key="userportal.esw.label.messageTitle" /></th>
                                        <th><app:storeMessage key="userportal.esw.label.messageType"/></th>
                                        <th><app:storeMessage key="userportal.esw.label.published"/></th>
                                        <th><app:storeMessage key="userportal.esw.label.lastModifiedBy" /></th>
                                        <th><app:storeMessage key="userportal.esw.label.effective"/></th>
                                        <th><app:storeMessage key="userportal.esw.label.expiration"/></th>
                                        <th>&nbsp;</th>                                        
                                    </tr>
                                </thead>
                            </table>
</logic:empty>
<logic:notEmpty name="esw.StoreMessageForm" property="storeMessages">
                            <table class="order">
                                <thead>
                                    <tr>
                                        <th><html:link href="<%=getSortLink(Constants.STORE_MESSAGE_SORT_FIELD_CREATED_DATE)%>"><app:storeMessage
                                                key="userportal.esw.label.created" />&nbsp;<%=getSortImage(
                                                myForm, Constants.STORE_MESSAGE_SORT_FIELD_CREATED_DATE)%></html:link></th></th>
                                        <th><html:link href="<%=getSortLink(Constants.STORE_MESSAGE_SORT_FIELD_MESSAGE_TITLE)%>"><app:storeMessage
                                                key="userportal.esw.label.messageTitle" />&nbsp;<%=getSortImage(
                                                myForm, Constants.STORE_MESSAGE_SORT_FIELD_MESSAGE_TITLE)%></html:link></th>
                                        <th><html:link href="<%=getSortLink(Constants.STORE_MESSAGE_SORT_FIELD_MESSAGE_TYPE)%>"><app:storeMessage
                                                key="userportal.esw.label.messageType" />&nbsp;<%=getSortImage(
                                                myForm, Constants.STORE_MESSAGE_SORT_FIELD_MESSAGE_TYPE)%></html:link></th>
                                        <th><html:link href="<%=getSortLink(Constants.STORE_MESSAGE_SORT_FIELD_PUBLISHED)%>"><app:storeMessage
                                                key="userportal.esw.label.published" />&nbsp;<%=getSortImage(
                                                myForm, Constants.STORE_MESSAGE_SORT_FIELD_PUBLISHED)%></html:link></th>
                                        <th><html:link href="<%=getSortLink(Constants.STORE_MESSAGE_SORT_FIELD_MOD_BY)%>"><app:storeMessage
                                                key="userportal.esw.label.lastModifiedBy" />&nbsp;<%=getSortImage(
                                                myForm, Constants.STORE_MESSAGE_SORT_FIELD_MOD_BY)%></html:link></th>
                                        <th><html:link href="<%=getSortLink(Constants.STORE_MESSAGE_SORT_FIELD_POSTED_DATE)%>"><app:storeMessage
                                                key="userportal.esw.label.effective" />&nbsp;<%=getSortImage(
                                                myForm, Constants.STORE_MESSAGE_SORT_FIELD_POSTED_DATE)%></html:link></th>
                                        <th><html:link href="<%=getSortLink(Constants.STORE_MESSAGE_SORT_FIELD_END_DATE)%>"><app:storeMessage
                                                key="userportal.esw.label.expiration" />&nbsp;<%=getSortImage(
                                                myForm, Constants.STORE_MESSAGE_SORT_FIELD_END_DATE)%></html:link></th>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
<logic:iterate  id="message"
                name="esw.StoreMessageForm"
                property="storeMessages"
                indexId="index"
                type="com.cleanwise.service.api.value.StoreMessageView"><%
boolean showEcoFriendly = false;
int messageId = message.getStoreMessageId();
String messageLink = request.getContextPath() + "/userportal/esw/storeMessage.do?operation=showMessageDetail&storeMessageId=" + messageId;
String deleteLink = request.getContextPath() + "/userportal/esw/storeMessage.do?operation=deleteMessage&storeMessageId=" + messageId;
%>
                                        <td><%=ClwI18nUtil.formatDateInp(request, message.getAddDate())%></td>
                                        <td><a href="<%=messageLink%>"><bean:write name="message" property="messageTitle" /></a></td>
                                        <td><bean:write name="message" property="messageType"/></td>
                                        <td><bean:write name="message" property="published"/></td>
                                        <td><bean:write name="message" property="modBy" /></td>
                                        <td><%=ClwI18nUtil.formatDateInp(request, message.getPostedDate())%>&nbsp;</td>
                                        <td><%=ClwI18nUtil.formatDateInp(request, message.getEndDate())%>&nbsp;</td>
                                        <td><a class="blueBtn" href="<%=deleteLink%>"><span><app:storeMessage key="global.action.label.delete" /></span></a></td>
                                    </tr>
</logic:iterate>
                                </tbody>
                            </table>
                            </logic:notEmpty>
                            <a class="blueBtnLargeExt" href="<%=createMessageLink%>"><span><app:storeMessage key="userportal.esw.label.createMessage" /></span></a>
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
function submitSortBy(sortField) {
    if (sortField == $('#sortField').val()) {
        if ('<%=Constants.PAR_VALUE_SORT_ORDER_ASCENDING%>' == $("#sortOrder").val()) {
            $('#sortOrder').val('descending');
        } else {
            $('#sortOrder').val('ascending');
        }
    } else {
        $('#sortField').val(sortField);
        $('#sortOrder').val('ascending');
    }
    $('#operationId').val('<%=Constants.PARAMETER_OPERATION_VALUE_SORT_MESSAGES%>');
    $("#storeMessageForm").submit();
    return false;
}
</script>

<%!
    private final String upArrowImg = "<img src='../../esw/images/upArrow.gif'/>";
    private final String downArrowImg = "<img src='../../esw/images/downArrow.gif'/>";

    private String getSortImage(StoreMessageForm form, String sortFieldName) {
        String sortField = form.getSortField();
        String sortOrder = form.getSortOrder();
        if (sortFieldName.equalsIgnoreCase(sortField)) {
            if (Constants.PAR_VALUE_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
                return upArrowImg;
            } else {
                return downArrowImg;
            }
       }
       return "";
    }
    
    private String getSortLink(String sortField) {
        return "javascript:submitSortBy('" + sortField + "');";
    }
%>
