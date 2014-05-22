<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Date"%>
<%@page import="com.espendwise.view.forms.esw.ControlsForm"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.utils.ShopTool" %>
<%@page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@page import="com.cleanwise.service.api.util.Utility" %>
<%@page import="com.cleanwise.service.api.value.AddressData" %>
<%@page import="com.cleanwise.service.api.value.BusEntityData" %>
<%@page import="com.cleanwise.service.api.value.OrderAddressData" %>
<%@page import="com.cleanwise.service.api.value.SiteData"%>
<%@page import="com.cleanwise.service.api.value.ProductData"%>
<%@page import="com.cleanwise.service.api.value.FiscalCalendarInfo"%>
<%@page import="org.apache.commons.lang.StringUtils" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<bean:define id="myForm" name="esw.ControlsForm"  type="com.espendwise.view.forms.esw.ControlsForm"/>
<%
    CleanwiseUser user = ShopTool.getCurrentUser(request);
	boolean canViewParValues = user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.VIEW_SITE_PAR_VALUES);
	boolean canEditParValues = canViewParValues && user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_PAR_VALUES);
    String readonly = canEditParValues ? "" : "readonly=\"readonly\"";

    String controlsLink = "userportal/esw/controls.do";
    SiteData thisSite = ShopTool.getCurrentSite(request);
    int numItems = myForm.getInventoryForm().getInventoryItems() == null ? 0
            			: myForm.getInventoryForm().getInventoryItems().size();
    int numPeriods = thisSite == null ? 0 : thisSite.getBudgetPeriods().size();
 	if (numPeriods == 0){
 		readonly = "readonly=\"readonly\"";
 	}
    int realPeriod = thisSite == null ? 0 : thisSite.getCurrentBudgetPeriod();
    StringBuilder periodDates = new StringBuilder();
    for (int i = 1; i <= numPeriods; i++) {
        FiscalCalendarInfo.BudgetPeriodInfo bi = (FiscalCalendarInfo.BudgetPeriodInfo) thisSite.getBudgetPeriods().get(i);
        if (bi != null)
            periodDates.append("periodStart[" + i + "]='" +
                ClwI18nUtil.formatDateInp(request, bi.getStartDate()) + "';");
    }
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
<%if (canViewParValues) {%>
<html:form name="esw.ControlsForm" styleId="controlsForm"
	action="userportal/esw/controls.do"
	type="com.espendwise.view.forms.esw.ControlsForm">
<html:hidden name="esw.ControlsForm" property="<%=Constants.PARAMETER_OPERATION%>" styleId="operationId"/>
<html:hidden name="esw.ControlsForm" property="sortField" styleId="sortField" />
<html:hidden name="esw.ControlsForm" property="sortOrder" styleId="sortOrder" />
                <!-- Start Box -->
                <div class="boxWrapper squareBottom smallMargin">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">
							<h1 class="main"><app:storeMessage key="userportal.esw.label.parValues" /></h1>
<logic:empty name="esw.ControlsForm" property="inventoryForm.inventoryItems">
                            <table class="order">
                                <thead>
                                    <tr>
                                        <th><app:storeMessage key="userportal.esw.label.sku#" /></th>
                                        <th class="ecoFriendly"></th>
                                        <th><app:storeMessage key="userportal.esw.label.productName" /></th>
										<th><app:storeMessage key="userportal.esw.label.pack" /></th>
										<th><app:storeMessage key="userportal.esw.label.uom" /></th>
										<th class="noBorder"><img src="<%=request.getContextPath()%>/externals/esw/images/prevArrow.gif"
                                            	alt="left arrow" />&nbsp;&nbsp;&nbsp; <app:storeMessage key="userportal.esw.label.prev" />
										</th>
										<th class="noBorder"><app:storeMessage key="userportal.esw.label.periods" /></th>
										<th>
                                            <app:storeMessage key="global.action.label.next" />&nbsp;&nbsp;&nbsp; <img src="<%=request.getContextPath()%>/externals/esw/images/nextArrow.gif" alt="right arrow" />
										</th>
										<th><app:storeMessage key="userportal.esw.label.modified" /></th>
                                    </tr>
                                </thead>
                            </table>
</logic:empty>
<logic:notEmpty name="esw.ControlsForm" property="inventoryForm.inventoryItems"><%if (canEditParValues) {%>
							<a class="blueBtnLargeExt" onclick="javascript:setFieldsAndSubmitForm('controlsForm', 'operationId', '<%=Constants.
							        PARAMETER_OPERATION_VALUE_UPDATE_PAR_VALUES%>');"><span><app:storeMessage key="global.action.label.update"
							        /></span></a><%}%>
							<table class="order">
                                <thead>
                                    <tr>
                                        <th><html:link href="<%=getSortLink(Constants.PAR_VALUE_SORT_FIELD_SKU)%>"><app:storeMessage
                                        		key="userportal.esw.label.sku#" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PAR_VALUE_SORT_FIELD_SKU)%></html:link></th>
                                        <th class="ecoFriendly"></th>
                                        <th><html:link href="<%=getSortLink(Constants.PAR_VALUE_SORT_FIELD_PRODUCT_NAME)%>"><app:storeMessage
                                        		key="userportal.esw.label.productName" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PAR_VALUE_SORT_FIELD_PRODUCT_NAME)%></html:link></th>
										<th><html:link href="<%=getSortLink(Constants.PAR_VALUE_SORT_FIELD_PACK)%>"><app:storeMessage
												key="userportal.esw.label.pack" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PAR_VALUE_SORT_FIELD_PACK)%></html:link></th>
										<th><html:link href="<%=getSortLink(Constants.PAR_VALUE_SORT_FIELD_UOM)%>"><app:storeMessage
												key="userportal.esw.label.uom" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PAR_VALUE_SORT_FIELD_UOM)%></html:link></th>
										<th class="noBorder">
                                            <a href="#" onclick="refresh(-1);return false;"> <img src="<%=request.getContextPath()%>/externals/esw/images/prevArrow.gif"
                                            	alt="left arrow" />&nbsp;&nbsp;&nbsp; <app:storeMessage key="userportal.esw.label.prev" /></a>
										</th>
										<th class="noBorder"><app:storeMessage key="userportal.esw.label.periods" /></th>
										<th>
                                            <a href="#" onclick="refresh(1);return false;"><app:storeMessage key="global.action.label.next" />&nbsp;&nbsp;&nbsp; <img src="<%=request.getContextPath()%>/externals/esw/images/nextArrow.gif" alt="right arrow" /></a>
										</th>
										<th><html:link href="<%=getSortLink(Constants.PAR_VALUE_SORT_FIELD_MODIFED)%>"><app:storeMessage
												key="userportal.esw.label.modified" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PAR_VALUE_SORT_FIELD_MODIFED)%></html:link></th>
                                    </tr>
                                </thead>
                                <tbody>
<logic:iterate	id="siteInv"
                name="esw.ControlsForm"
                property="inventoryForm.inventoryItems"
                indexId="index"
                type="com.cleanwise.service.api.value.SiteInventoryConfigView"><%
boolean showEcoFriendly = false;
int itemId = siteInv.getItemId();
ProductData productD = siteInv.getProductData();
if (productD != null && productD.isCertificated()) {
	showEcoFriendly = true;
}
String itemLink = request.getContextPath() + "/userportal/esw/shopping.do?operation=item&source=parValues&itemId=" + itemId;
String prefixId = "row" + index;
%>
                                    <tr>
<% for ( int bp2 = 1; bp2 <= numPeriods; bp2++ ) {
    String periodId = prefixId + "p" + bp2;
%><html:hidden styleId="<%=periodId%>" name="esw.ControlsForm" property='<%= "inventoryItem[" + itemId + "].parValue[" + bp2 + "]"%>' />
<%
}%>
                                        <td><bean:write name="siteInv" property="actualSku"/></td>
                                        <td class="ecoFriendly"><%if (showEcoFriendly) {%>
                                            <img src="<%=request.getContextPath()%>/esw/images/ecoFriendly.png" alt="eco-friendly" title="eco-friendly" /><%}%>
                                        </td>
                                        <td>
                                            <a href="<%=itemLink%>"><bean:write name="siteInv" property="itemDesc" /></a>
                                        </td>
										<td><bean:write name="siteInv" property="itemPack" /></td>
                                        <td><bean:write name="siteInv" property="itemUom" /></td>
										<td>
											<div class="qtyLine">
												<span id="<%=prefixId%>c00"></span>
												<div class="qtyInput">
													<input <%=readonly%> id="<%=prefixId%>c01" type="text" size="3" onkeypress="chg(<%=index%>,0,this.value);" onchange="chg(<%=index%>,0,this.value);" />
												</div>
												<br />
												<span id="<%=prefixId%>c02"></span>
											</div>
										</td>
										<td>
											<div class="qtyLine">
												<span id="<%=prefixId%>c10"></span>
												<div class="qtyInput">
													<input <%=readonly%> id="<%=prefixId%>c11" type="text"  size="3" onkeypress="chg(<%=index%>,1,this.value);" onchange="chg(<%=index%>,1,this.value);" />
												</div>
												<br />
												<span id="<%=prefixId%>c12"></span>
											</div>
										</td>
										<td>
											<div class="qtyLine">
												<span id="<%=prefixId%>c20"></span>
												<div class="qtyInput">
													<input <%=readonly%>  id="<%=prefixId%>c21" type="text"  size="3" onkeypress="chg(<%=index%>,2,this.value);" onchange="chg(<%=index%>,2,this.value);" />
												</div>
												<br />
												<span id="<%=prefixId%>c22"></span>
											</div>
										</td>
										<td><bean:write name="siteInv" property="modBy" />&nbsp;<%=ClwI18nUtil.formatDateInp(request, siteInv.getModDate())%></td>
									</tr>
  </logic:iterate>
                                </tbody>
                            </table><%if (canEditParValues) {%>
							<a class="blueBtnLargeExt bottomMargin" onclick="javascript:setFieldsAndSubmitForm('controlsForm', 'operationId', '<%=Constants.
							        PARAMETER_OPERATION_VALUE_UPDATE_PAR_VALUES%>');"><span><app:storeMessage key="global.action.label.update"
							        /></span></a><%}%>
</logic:notEmpty>
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
            $('#sortOrder').val('<%=Constants.PAR_VALUE_SORT_ORDER_DESCENDING%>');
        } else {
            $('#sortOrder').val('<%=Constants.PAR_VALUE_SORT_ORDER_ASCENDING%>');
        }
    } else {
        $('#sortField').val(sortField);
        $('#sortOrder').val('<%=Constants.PAR_VALUE_SORT_ORDER_ASCENDING%>');	
    }
    $('#operationId').val('<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_PAR_VALUES%>');
	$("#controlsForm").submit();
	return false;
}
$(document).ready(init);
var periodStart = new Array();
<%=periodDates.toString()%>
var curPeriod = <%=realPeriod - 1%>;
function init() {
	for (var row = 0; row < <%=numItems%>; row++) {
		for (var col = 0; col < 3; col++) {
			assignKeydownEvent(row, col);
		}
	}
	refresh(0);
}
function assignKeydownEvent(row, col) {
	var obj = $("#row" + row + "c" + col + "1");
	obj.keydown(function(e){
		if (e.keyCode == 9) {
			return kd(row, col, obj.val());
		}
	    return true;
	});
}
function kd(row, col, val) {
	chg(row, col, val);
		if (col == 2) {
			if (curPeriod + 2 >= <%=numPeriods%>) {
				refresh(-<%=numPeriods%>);
				row++;
				if (row >= <%=numItems%>) {
					row = 0;
				}
				$("#row" + row + "c" + 0 + "1").focus();
			} else {
				refresh(1);
			}
			return false;
		}
	return true;
}
function refresh(diff) {
	if (0 >= <%=numPeriods%>) {
		return;
	}
	curPeriod += diff;
	if (curPeriod <= 0) {
		curPeriod = 1;
	} else if (curPeriod + 2 > <%=numPeriods%> ) {
		curPeriod = <%=numPeriods%> - 2;
	}
	for (var x = 0; x < 3; x++) {
		for (var y = 0; y < <%=numItems%>; y++) {
			$("#row" + y + "c" + x + "0").text(curPeriod + x);
			$("#row" + y + "c" + x + "1").val('');
			var newVal = $("#row" + y + "p" + (curPeriod + x)).val();
			$("#row" + y + "c" + x + "1").val(newVal);
			$("#row" + y + "c" + x + "2").text(periodStart[curPeriod + x]);
			for (var i = 0; i < 3; i++) {
				if (curPeriod + x < <%=realPeriod%>) {
					$("#row" + y + "c" + x + "" + i).removeClass('active');
					$("#row" + y + "c" + x + "" + i).addClass('nonactive');
				} else {
					$("#row" + y + "c" + x + "" + i).removeClass('nonactive');
					$("#row" + y + "c" + x + "" + i).addClass('active');
				}	
			}
		}
	}
}
function chg(row, col, val) {
	$("#row" + row + "p" + (curPeriod + col)).val(val)
}
</script>
<%!
	private final String upArrowImg = "<img src='../../esw/images/upArrow.gif'/>";
	private final String downArrowImg = "<img src='../../esw/images/downArrow.gif'/>";

    private String getSortImage(ControlsForm form, String sortFieldName) {
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