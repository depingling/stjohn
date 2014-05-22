<%@page import="com.espendwise.view.forms.esw.LocateForm"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>


<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>



<bean:define id="locateForm" name="esw.LocateForm"  type="com.espendwise.view.forms.esw.LocateForm"/>
<%
CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
String locateLink = "userportal/esw/locate.do";
String defaultValueAccountName = ClwMessageResourcesImpl.getMessage(request,"locate.specifyAccounts.search.defaultValue.accountName");
String styleClassAccountName = "default-value";
String valueAccountName = defaultValueAccountName;
if (locateForm != null && Utility.isSet(locateForm.getLocateReportAccountForm().getSearchField())) {
	styleClassAccountName = StringUtils.EMPTY;
	valueAccountName = locateForm.getLocateReportAccountForm().getSearchField();
}
%>
<body onload="loadDoc();" class="largePopUp widePopUp">
<script type="text/javascript">
function loadDoc() {
	$('#buttonReturnSelected').bind('click', returnSelected);
	$('#buttonReturnSelected2').bind('click', returnSelected);
}
var specifyText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.
        getMessage(request,"locate.specifyAccounts.label.specifiedAccounts"))%>';
function returnSelected(){
    var ids = new Array();
    var idString = '';
	var hiddenIds = '';
	$.each($("input[name='selected']:checked"), function() {
	    ids.push($(this).val());
	    idString += (idString.length > 0 ? ',' : '') + $(this).val();
        hiddenIds += "<input type='hidden' name='<%=locateForm.getHiddenName()%>' value='"
		    + $(this).val() + "'/>"; 
    });
	var objInfo  = window.top.document.getElementById('specifyAccounts<%=locateForm.getRandId()%>Info');
	var objLink = window.top.document.getElementById('specifyAccounts<%=locateForm.getRandId()%>Link');
	var optText = '';
	if (ids.length > 0) {
		optText = specifyText.replace('({0})', '(' + ids.length + ')');
	}
	objInfo.innerHTML = optText + hiddenIds;
	objLink.href = '<%=request.getContextPath()%>/userportal/esw/locate.do?operation=specifyAccounts&randId=<%=locateForm.getRandId()%>&hiddenName=<%=locateForm.getHiddenName()%>&ids=' + idString;
    window.top.closePopUp();
}
function checkAll(value) {
	$.each($("input[name='selected']"), function() {
		if (value) {
			$(this).attr('checked', 'true');	
		} else {
			$(this).removeAttr('checked');
		}
		
	});
}
function submitSortBy(sortField) {
    if (sortField == $('#sortField').val()) {
        if ('<%=Constants.LOCATION_SORT_ORDER_ASCENDING%>' == $("#sortOrder").val()) {
            $('#sortOrder').val('<%=Constants.LOCATION_SORT_ORDER_DESCENDING%>');
        } else {
            $('#sortOrder').val('<%=Constants.LOCATION_SORT_ORDER_ASCENDING%>');
        }
    } else {
        $('#sortField').val(sortField);
        $('#sortOrder').val('<%=Constants.LOCATION_SORT_ORDER_ASCENDING%>');	
    }
	$("#sortLocateFormId").submit();
	return false;
}
function initSearchValueStyles() {
    if ($('#accountName').val() != '<%=defaultValueAccountName%>') {
        $('#accountName').css('color', active_color);
    }
	return true;
}
function clearDefaultSearchValues() {
    if ($('#accountName').val() == '<%=defaultValueAccountName%>') {
    	$('#accountName').val('');
    }
}
</script>
<app:setLocaleAndImages/>
		<div class="clearfix">
            <div class="content">                
                <div class="left clearfix" style="width:720px;">
                    <h1><app:storeMessage key="locate.specifyAccounts.link.title" /></h1>
                    <p class="filters clearfix">
<script type="text/javascript">initSearchValueStyles();</script>
<html:form styleId="locateFormId" action="<%=locateLink%>">
<html:hidden property="randId" />
<html:hidden property="<%=Constants.PARAMETER_OPERATION%>" value="<%=Constants.PARAMETER_OPERATION_SPECIFY_ACCOUNTS_SEARCH%>"/>

						<div>	
							<p>
<span><app:storeMessage key="location.search.filters" /></span>
<html:text name="locateForm" property="locateReportAccountForm.searchField" styleClass="<%=styleClassAccountName%>" styleId="accountName" value="<%=valueAccountName%>" />&nbsp;
<label><html:radio name="locateForm" property="locateReportAccountForm.searchType" value="<%=Constants.NAME_BEGINS%>" style="width:10px;"/><app:storeMessage key="locate.specifyAccounts.label.nameStartsWith" /></label>&nbsp;
<label><html:radio name="locateForm" property="locateReportAccountForm.searchType" value="<%=Constants.NAME_CONTAINS%>" style="width:10px;"/><app:storeMessage key="locate.specifyAccounts.label.nameContains" /></label>
<a href="#" class="btn" onclick="javascript:clearDefaultSearchValues();javascript:submitForm('locateFormId');"><span><app:storeMessage key="location.search.filter" /></span></a>
					        </p>
					        <p>	
						 <label><html:checkbox name="locateForm" property="locateReportAccountForm.showInactiveFl"
								styleClass="chkBox"/><app:storeMessage key="global.label.showInactive" /></label>
					        </p>
						</div>
</html:form>
					</p>
					<hr />
					<a id="buttonReturnSelected" href="#" class="blueBtnMed right rightMargin bottomMargin" ><span><app:storeMessage key="locate.label.returnSelected" /></span></a>
<div style="overflow: auto;height: 300px; width:700px"><br />
                        <table>
                        	<colgroup>
                            	<col />
                                <col class="locationName" />
                                <col class="address" />
                                <col class="locationNumber" />
                                <col class="distNumber" />
                                <col class="select" />
                                <col />
                            </colgroup>
                            <thead>
                                <tr>
                                    <th><html:link href="<%=getSortLink(Constants.ACCOUNT_SORT_FIELD_NAME)%>"><app:storeMessage key="locate.specifyAccounts.label.accountName" /><%=getSortImage(locateForm,
                                                Constants.ACCOUNT_SORT_FIELD_NAME)%></html:link></th>
                                    <th><html:link href="<%=getSortLink(Constants.ACCOUNT_SORT_FIELD_CITY)%>"><app:storeMessage key="locate.specifyAccounts.label.city" /><%=getSortImage(locateForm,
                                                Constants.ACCOUNT_SORT_FIELD_CITY)%></html:link></th>
                                    <th><html:link href="<%=getSortLink(Constants.ACCOUNT_SORT_FIELD_STATE)%>"><app:storeMessage key="locate.specifyAccounts.label.stateProvence" /><%=getSortImage(locateForm,
                                                Constants.ACCOUNT_SORT_FIELD_STATE)%></html:link></th>
                                    <th><html:link href="<%=getSortLink(Constants.ACCOUNT_SORT_FIELD_TYPE)%>"><app:storeMessage key="locate.specifyAccounts.label.type" /><%=getSortImage(locateForm,
                                                Constants.ACCOUNT_SORT_FIELD_TYPE)%></html:link></th>
                                    <th><html:link href="<%=getSortLink(Constants.ACCOUNT_SORT_FIELD_STATUS)%>"><app:storeMessage key="locate.specifyAccounts.label.status" /><%=getSortImage(locateForm,
                                                Constants.ACCOUNT_SORT_FIELD_STATUS)%></html:link></th>
                                    <th><label class="chkBoxSm resale"><input type="checkbox" onclick="checkAll(this.checked);"/></label></th>
                                    <th class="last">&nbsp;</th>
                                </tr>
                            </thead>
                            <tbody>
<logic:notEmpty name="locateForm" property="locateReportAccountForm.accounts">
<logic:iterate id="item" name="locateForm" property="locateReportAccountForm.accounts" 
type="com.cleanwise.service.api.value.AccountUIView">
								<tr>
                                    <td><bean:write name="item" property="busEntity.shortDesc" /></td>
                                    <td><bean:write name="item" property="primaryAddress.city" /></td>
                                    <td><bean:write name="item" property="primaryAddress.stateProvinceCd" /></td>
									<td><bean:write name="item" property="accountType.value" /></td>
									<td><bean:write name="item" property="busEntity.busEntityStatusCd" /></td>
                                    <td><label class="chkBox"><html:multibox name="locateForm"
                                            property="selected" value="<%=String.valueOf(item.getBusEntity().getBusEntityId())%>"/></label></td>
                                </tr>
</logic:iterate>
</logic:notEmpty>
                            </tbody>
                        </table>
</div><br />
                    <a id="buttonReturnSelected2" href="#" class="blueBtnMed right rightMargin bottomMargin"><span><app:storeMessage key="locate.label.returnSelected" /></span></a>
                </div>               
            </div>
        </div>
<html:form styleId="sortLocateFormId" action="<%=locateLink%>">
<html:hidden name="locateForm" property="<%=Constants.PARAMETER_OPERATION%>" value="<%=Constants.PARAMETER_OPERATION_SPECIFY_ACCOUNTS_SEARCH%>" />
<html:hidden name="locateForm" property="locateReportAccountForm.searchField" />
<html:hidden name="locateForm" property="locateReportAccountForm.searchType" />
<html:hidden name="locateForm" property="locateReportAccountForm.showInactiveFl" />
<html:hidden name="locateForm" property="randId" />
<html:hidden name="locateForm" property="sortField" styleId="sortField" />
<html:hidden name="locateForm" property="sortOrder" styleId="sortOrder" />
</html:form>
</body>
<%!
	private final String upArrowImg = "<img src='../../esw/images/upArrow.gif'/>";
	private final String downArrowImg = "<img src='../../esw/images/downArrow.gif'/>";

    private String getSortImage(LocateForm locateForm, String locationSortFieldName) {
        String sortField = locateForm.getSortField();
   	    String sortOrder = locateForm.getSortOrder();
   	    if (locationSortFieldName.equalsIgnoreCase(sortField)) {
       	    if (Constants.ACCOUNT_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
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