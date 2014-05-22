<%--
  Date: 10.10.2007
  Time: 19:35:42
--%>

<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.forms.UserWorkOrderDetailMgrForm" %>
<%@ page import="com.cleanwise.view.logic.UserWorkOrderMgrLogic" %>
<%@ page import="com.cleanwise.service.api.value.WorkOrderDetailData" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.StringUtils" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%@include file="/externals/calendar/calendar.jsp"%>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<script language="JavaScript" src="../externals/ajaxutil.js"></script>
<script language="JavaScript" src="../externals/workOrderUtil.js"></script>

<script language="JavaScript1.2"> <!--
 function viewPrinterFriendly(loc) {
 var prtwin = window.open(loc,"view_docs","menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500,width=775,left=100,top=165");
   <!--if (window.focus) { prtwin.focus(); }-->
	return false;
 }

 function viewPrinterFriendly1(printloc) {    
    var prtwin_proba = window.open(printloc,"print_doc","menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500,width=775,left=100,top=165");
    return false;
 }

function getElementPosition(elemId)
{
    var elem = document.getElementById(elemId);

    var w = elem.offsetWidth;
    var h = elem.offsetHeight;

    var l = 0;
    var t = 0;

    while (elem)
    {
        l += elem.offsetLeft;
        t += elem.offsetTop;
        elem = elem.offsetParent;
    }

    return {"left":l, "top":t, "width": w, "height":h};
}

 function actionSubmit(formNum, action) {
 var actions;
 
 actions=document.forms[formNum]["action"];
 //alert(actions.length);
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='hiddenAction') {
     actions[ii].value=action;
     document.forms[formNum].submit();
     break;
   }
 }
 return false;
 }

 function actionSubmitTb(formNum, action,fwdPage,sbar) {
     var actions;
     var disp
     var secBar;
     actions=document.forms[formNum]["action"];
     disp=document.forms[formNum]["display"];
     secBar=document.forms[formNum]["secondaryToolbar"];

     for(ii=actions.length-1; ii>=0; ii--) {
       if(actions[ii].value=='hiddenAction') {
         disp.value=fwdPage;
         secBar.value=sbar;
         actions[ii].value=action;
         document.forms[formNum].submit();
         break;
       }
     }
     return false;
     }


var baseopacity=0;
var browserdetect;
function showtext(elId,thetext){
if (!document.getElementById)
return
textcontainerobj=document.getElementById(elId)
textcontainerobj.style.visibility ="visible"
var tablepos=getElementPosition("Table"+elId)
var pos=getElementPosition(elId)
document.getElementById(elId).style.top=tablepos.top-pos.height
document.getElementById(elId).style.left=tablepos.left-(pos.width/2)
browserdetect=textcontainerobj.filters? "ie" : typeof textcontainerobj.style.MozOpacity=="string"? "mozilla" : ""
instantset(elId,baseopacity)
document.getElementById(elId).innerHTML=thetext
highlighting=setInterval("gradualfade(textcontainerobj)",50)
}

function hidetext(elId){
cleartimer()
instantset(elId,baseopacity)
document.getElementById(elId).style.visibility ="hidden"
}

function instantset(elId,degree){
if (browserdetect=="mozilla")
textcontainerobj.style.MozOpacity=degree/100
else if (browserdetect=="ie")
textcontainerobj.filters.alpha.opacity=degree
else if (document.getElementById && baseopacity==0)
document.getElementById(elId).innerHTML=""
}

function cleartimer(){
if (window.highlighting) clearInterval(highlighting)
}

function gradualfade(cur2){
if (browserdetect=="mozilla" && cur2.style.MozOpacity<1)
cur2.style.MozOpacity=Math.min(parseFloat(cur2.style.MozOpacity)+0.2, 0.99)
else if (browserdetect=="ie" && cur2.filters.alpha.opacity<100)
cur2.filters.alpha.opacity+=20
else if (window.highlighting)
clearInterval(highlighting)
}

<%
    String showAssets = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"global.label.showWorkOrderItemAssets"));
    if (showAssets == null) {
        showAssets = "Show Assets";
    }
    String hideAssets = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"global.label.hideWorkOrderItemAssets"));
    if (hideAssets == null) {
        hideAssets = "Hide Assets";
    }
    
    String commentStr = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWorkOrder.text.Comments"));
    if (commentStr == null) {
        commentStr = "Comments";
    }
    
    String acceptMessage = ClwI18nUtil.getMessageOrNull(request, "shop.orderStatus.text.accept");
    if (acceptMessage == null) {
        acceptMessage = "Accept";
    }
    
    String rejectMessage = ClwI18nUtil.getMessageOrNull(request, "global.action.label.reject");
    if (rejectMessage == null) {
        rejectMessage = "Reject";
    }
    
    String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdFormat = new SimpleDateFormat(dateFormat, ClwI18nUtil.getUserLocale(request));	
    
    String today = sdFormat.format(new Date());
%>

function doShowAssetAndWarrantyConfig() {
    var ctrl = document.getElementById('AssetAndWarrantyConfig');
    
    if(ctrl.style.display == "none") {
       ctrl.style.display = "";
    }
}

function addItemizedServiceRow() {
    var insert = 11;
    var lineColor = '#F0F0F0';
    var lineNum = 15;
    
    
    var tbl = document.getElementById('itemizedServiceTable');
    var row = tbl.insertRow(insert);
    row.align = 'center';
    row.bgColor = lineColor;
    
    var cell = row.insertCell(0);
    var textNode = document.createTextNode(lineNum);
    cell.appendChild(textNode);
    
    //cell = row.insertCell(1);
    //var el = document.createElement('input');
    //el.type = 'radio';
    //el.name = 'paymentTypeCd[' + (lineNum - 1) + ']';
    //el.value = '<%=RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE%>';
    //el.checked = true;
    //cell.appendChild(el);
    
    cell = row.insertCell(1);
    cell.innerHTML = '<input type="radio" name="paymentTypeCd["' + (lineNum - 1) + '] value="<%=RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE%>" checked="checked" />';
    cell = row.insertCell(2);
    cell.innerHTML = '<input type="radio" name="paymentTypeCd["' + (lineNum - 1) + '] value="<%=RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.WARRANTY%>" />';
    cell = row.insertCell(3);
    cell.innerHTML = '<input type="radio" name="paymentTypeCd["' + (lineNum - 1) + '] value="<%=RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.PM_CONTRACT%>" />';
    
    cell = row.insertCell(4);
    el = document.createElement('input');
    el.type = 'text';
    el.name = 'quantity[' + (lineNum - 1) + ']';
    el.value = '0';
    el.size = 1;
    el.maxlength = 32;
    cell.appendChild(el);
    
    cell = row.insertCell(5);
    el = document.createElement('input');
    el.type = 'text';
    el.name = 'partNumber[' + (lineNum - 1) + ']';
    el.value = '';
    el.size = 4;
    el.maxlength = 32;
    cell.appendChild(el);
    
    cell = row.insertCell(6);
    el = document.createElement('input');
    el.type = 'text';
    el.name = 'shortDescr[' + (lineNum - 1) + ']';
    el.value = '';
    el.size = 26;
    el.maxlength = 255;
    cell.appendChild(el);
    
    cell = row.insertCell(7);
    el = document.createElement('input');
    el.type = 'text';
    el.name = 'partPrice[' + (lineNum - 1) + ']';
    el.value = '0.00';
    el.size = 2;
    el.maxlength = 32;
    cell.appendChild(el);
    
    cell = row.insertCell(8);
    textNode = document.createTextNode('0.00');
    cell.appendChild(textNode);
    
    cell = row.insertCell(9);
    el = document.createElement('input');
    el.type = 'text';
    el.name = 'labor[' + (lineNum - 1) + ']';
    el.value = '0.00';
    el.size = 2;
    el.maxlength = 32;
    cell.appendChild(el);
    
    cell = row.insertCell(10);
    el = document.createElement('input');
    el.type = 'text';
    el.name = 'travel[' + (lineNum - 1) + ']';
    el.value = '0.00';
    el.size = 2;
    el.maxlength = 32;
    cell.appendChild(el);
    
    cell = row.insertCell(11);
    textNode = document.createTextNode('<%=today%>');
    cell.appendChild(textNode);
    
    //cell = row.insertCell(12);
    cell = row.insertCell(12);
    cell.innerHTML = '<input type="checkbox" name="delete["' + (lineNum - 1) + '] value="<%=RefCodeNames.STATUS_CD.INACTIVE%>" />';
    
    row = tbl.insertRow(insert + 1);
    row.align = 'center';
    row.bgColor = lineColor;
    
    cell = row.insertCell(0);
    cell.colSpan = 13;
    cell.align = 'left';
    
    var div = document.createElement('div');
    div.style.marginLeft = '10px';
    
    textNode = document.createTextNode('<%=commentStr%>');
    
    el = document.createElement('input');
    el.type = 'text';
    el.name = 'comments[' + (lineNum - 1) + ']';
    el.value = '';
    el.size = 106;
    el.maxlength = 255;
    
    div.appendChild(textNode);
    div.appendChild(el);
    cell.appendChild(div);
}

function submitSend () {
    var type = document.getElementsByName("sendType");
    
    for (var i = 0; i < type.length; i++) {
        if (type[i].checked) {
            if (type[i].value == '<%=acceptMessage%>') {
                //alert("accept");
                actionSubmit('0', 'accept');
            } else if (type[i].value == '<%=rejectMessage%>') {
                //alert("reject");
                actionSubmit('0', 'reject');
            }
        }
    }
}
-->
</script>

<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String messageKey;
%>
<%
    String browser = (String) request.getHeader("User-Agent");
    String isMSIE = "";
    if (browser != null && browser.indexOf("MSIE") >= 0) {
        isMSIE = "Y";
%>
<script language="JavaScript" src="../externals/calendar.js"></script>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
        marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% } else { %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% } %>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<bean:define id="theForm" name="USER_WORK_ORDER_DETAIL_MGR_FORM"
             type="com.cleanwise.view.forms.UserWorkOrderDetailMgrForm"/>
<html:form name="USER_WORK_ORDER_DETAIL_MGR_FORM" action="/serviceproviderportal/workOrderDetail.do" scope="session">
<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderDetail">
<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderDetail.workOrder">
<bean:define id="workorderdet" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderDetail"/>
<bean:define id="woid" name="workorderdet" property="workOrder.workOrderId"/>

<!-- init page varriable-->

<% String mainTablePercent = "50%";%>

<%  
   String workOrderStatus = theForm.getWorkOrderDetail().getWorkOrder().getStatusCd();
   boolean editingAuthorized = appUser.canEditWorkOrder(workOrderStatus);
   boolean isWorkOrderApprover = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER);
   boolean isSendToProviderStatus = RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER.equals(workOrderStatus);
   
   boolean isWOworkflowConfigured = false;
   if (theForm.getWorkOrderId() > 0) {
        int siteId = theForm.getWorkOrderDetail().getWorkOrder().getBusEntityId();
        if (siteId > 0) {
            isWOworkflowConfigured = UserWorkOrderMgrLogic.isWOworkflowConfiguredForSite(siteId);
        }
   }
   Integer ZERO = new Integer(0);
%>
<!-- end init -->

<TR>
    <td colspan="5">&nbsp;</td>
</TR>

<TR>
	<td width="1%">&nbsp;</td>
	
	<td width="98%">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="3">
				
				<!-- begin Summary -->
				<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border:#000000 1px solid">
				
				<tr>
					<td colspan="3">
						<table width="100%" border="0" cellpadding="2" cellspacing="1">
						<tr>
							<td class="customerltbkground" valign="top" align="center" colSpan="4">
									<span class="shopassetdetailtxt">
									   <b><app:storeMessage key="userWorkOrder.text.workOrderPlan"/></b>
									</span>
								<logic:greaterThan name="woid" value="0">
									<%String printloc="../userportal/userWorkOrderDetail.do?action=print&display=t_userWorkOrderDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar";%>
									<a href="#" class="linkButton" onclick="return viewPrinterFriendly1('<%=printloc%>');">
										<img src='<%=IMGPath + "/b_print.gif"%>' border="0"/>
										<app:storeMessage key="global.label.printerFriendly"/>
									</a>
								</logic:greaterThan>
							</td>
						</tr>
						</table>
					</td>
				</tr>
				<tr align="center">
					<td width="53%" valign="top">
						<table width="100%" border="0" cellpadding="2" cellspacing="1">
						<tr>
							<td>
								<span class="shopassetdetailtxt">
								  <b><app:storeMessage key="userWorkOrder.text.workOrderNumber"/>:</b>
								</span>
							</td>
							<td>
								<bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderNum"/>
							</td>
							<td colspan="2">
							</td>
						</tr>

						<tr>
							<td>
								<span class="shopassetdetailtxt">
									  <b><app:storeMessage key="userWorkOrder.text.PONumber"/>:</b>
								</span>
								<% if(editingAuthorized) { %>
								<logic:equal name="theForm" property="allowSetWorkOrderPoNumber" value="true">
									<logic:equal name="theForm" property="workOrderPoNumberIsRequired" value="true">
										<span class="reqind">&nbsp;*</span>
									</logic:equal>
									</td>
									<td>
									<html:text name="theForm" property="workOrderPoNum"/>
									</td>
								</logic:equal>
								<logic:notEqual name="theForm" property="allowSetWorkOrderPoNumber" value="true">
									<td>
									<bean:write name="theForm" property="workOrderPoNum"/>
									</td>
								</logic:notEqual>
								<% } else  { %>
									<td>
									<bean:write name="theForm" property="workOrderPoNum"/>
									</td>
								<% } %>
							<td colspan="2">
							</td>
						</tr>

						<tr>
							<td>
								<span class="shopassetdetailtxt">
									<b><app:storeMessage key="userWorkOrder.text.workOrderName"/>:</b>&nbsp;<span class="reqind">*</span>
								</span>
							</td>
							<td colspan="3">
								<% if(editingAuthorized) { %>
								<html:text size="32" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="shortDesc"/>
								<% } else  { %>
								<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="shortDesc">
									<bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="shortDesc"/>
								</logic:present>
								<% } %>
							</td>
						</tr>

						<tr>
							<td>
								<span class="shopassetdetailtxt">
									<b><app:storeMessage key="userWorkOrder.text.actionCd"/>:</b>&nbsp;<span class="reqind">*</span>
								 </span>
							</td>
							<td colspan="3">
								<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="actionCode">
									<% if(editingAuthorized) { %>
									<html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="actionCode">
										<html:option value="">
											<app:storeMessage  key="admin.select"/>
										</html:option>
										<logic:present name="WorkOrder.user.action.vector">
											<logic:iterate id="actionCode" name="WorkOrder.user.action.vector"
														   type="com.cleanwise.service.api.value.RefCdData">
												<%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.action." + Utility.strNN((String)actionCode.getValue()).replaceAll(" ","_").toUpperCase());%>
												<html:option value="<%=actionCode.getValue()%>">
													<% if (messageKey != null) {%>
													<%=messageKey%>
													<%} else {%>
													<%=actionCode.getValue()%>
													<%}%>
												</html:option>
											</logic:iterate>
										</logic:present>
									</html:select>
									<% } else  { %>
									<bean:define id="action"
												 name="USER_WORK_ORDER_DETAIL_MGR_FORM"
												 property="actionCode"
												 type="java.lang.String"/>
									<%
										messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.action." + action.toUpperCase());
										if (messageKey != null) {
									%>
									<%=messageKey%>
									<%} else {%>
									<bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="actionCode"/>
									<%}%>
									<% } %>
								</logic:present>
							</td>
						</tr>

						<tr>
							<td><span class="shopassetdetailtxt"><b><app:storeMessage key="userWorkOrder.text.type"/>:</b>&nbsp;<span class="reqind">*</span></span></td>
							<td colspan="3"><logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="typeCd">
								<% if(editingAuthorized) { %>
								<html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="typeCd" onchange="ajaxconnect('userWorkOrderDetail.do', 'action=changeWorkOrderType&newWorkOrderTypeCd='+this.value, '', serviceProviderDynamicBox.populateAndReDrawSP);">
									<html:option value="">
										<app:storeMessage  key="admin.select"/>
									</html:option>
									<logic:present name="WorkOrder.user.type.vector">
										<logic:iterate id="serviceProviderType" name="WorkOrder.user.type.vector"
													   type="com.cleanwise.service.api.value.BusEntityData">
											<%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.type." + (serviceProviderType.getShortDesc()).toUpperCase());%>
											<html:option value="<%=serviceProviderType.getShortDesc()%>">
												<% if (messageKey != null) {%>
												<%=messageKey%>
												<%} else {%>
												<%=serviceProviderType.getShortDesc()%>
												<%}%>
											</html:option>
										</logic:iterate>
									</logic:present>
								</html:select>
								<% } else  { %>
								<bean:define id="type"
											 name="USER_WORK_ORDER_DETAIL_MGR_FORM"
											 property="typeCd"
											 type="java.lang.String"/>
								<%
									messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.action." + type.toUpperCase());
									if (messageKey != null) {
								%>
								<%=messageKey%>
								<%} else {%>
								<bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="typeCd"/>
								<%}%>
								<% } %>
							</logic:present>
							</td>
						</tr>

						<tr>
							<td><span class="shopassetdetailtxt"><b><app:storeMessage key="userWorkOrder.text.priority"/>:</b>&nbsp;<span class="reqind">*</span></span></td>
							<td colspan="3"><logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="priority">
								<% if(editingAuthorized) { %>
								<html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="priority">
									<html:option value="">
										<app:storeMessage  key="admin.select"/>
									</html:option>
									<logic:present name="WorkOrder.user.priority.vector">
										<logic:iterate id="priorityCd" name="WorkOrder.user.priority.vector"
													   type="com.cleanwise.service.api.value.RefCdData">
											<%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.priority." + ((String)priorityCd.getValue()).toUpperCase());%>
											<html:option value="<%=priorityCd.getValue()%>">
												<% if (messageKey != null) {%>
												<%=messageKey%>
												<%} else {%>
												<%=priorityCd.getValue()%>
												<%}%>
											</html:option>
										</logic:iterate>
									</logic:present>
								</html:select>
								<% } else  { %>
								<bean:define id="priority"
											 name="USER_WORK_ORDER_DETAIL_MGR_FORM"
											 property="priority"
											 type="java.lang.String"/>
								<%
									messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.action." + priority.toUpperCase());
									if (messageKey != null) {
								%>
								<%=messageKey%>
								<%} else {%>
								<bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="priority"/>
								<%}%>
								<% } %>
							</logic:present>
							</td>
						</tr>
						<tr>
							<td>
                                <logic:notEmpty name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="displayCostCenters">
                                    <span class="shopassetdetailtxt">
                                        <b><app:storeMessage key="userWorkOrder.text.costCenter"/>:</b>
                                    </span>
                                </logic:notEmpty>
                            </td>
							<td colspan="3">
                                <logic:notEmpty name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="displayCostCenters">
								<% if(editingAuthorized) { %>
								<html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="costCenterId">
									<html:option value="">
										<app:storeMessage  key="admin.select"/>
									</html:option>
										<logic:iterate id="costCenter"
													   name="USER_WORK_ORDER_DETAIL_MGR_FORM" 
													   property="displayCostCenters"
													   type="com.cleanwise.service.api.value.CostCenterData">
										  <html:option value="<%=String.valueOf(costCenter.getCostCenterId())%>">
												  <%=costCenter.getShortDesc()%>
											</html:option>
									  </logic:iterate>
								</html:select>
								<% } else  { %>
									<%String ccName = "";%>
									<bean:define id="costCenters"
												 name="USER_WORK_ORDER_DETAIL_MGR_FORM"
												 property="displayCostCenters"
												 type="com.cleanwise.service.api.value.CostCenterDataVector"/>
									<% Iterator it = costCenters.iterator();
										while (it.hasNext()) {
											CostCenterData cc = (CostCenterData) it.next();
											if (String.valueOf(cc.getCostCenterId()).equals(theForm.getCostCenterId())) {
												ccName = cc.getShortDesc();
												break;
											}
										} %>
									<%=ccName%>
								<%}%>
                                </logic:notEmpty>
							</td>
						</tr>
						<tr>
							<td>
								<span class="shopassetdetailtxt">
									  <b><app:storeMessage key="userWorkOrder.text.NTE"/>:</b>
								</span>
								<% if(editingAuthorized) { %>
									</td>
									<td>
										<html:text size="8" maxlength="32" name="theForm" property="nteStr">
										</html:text>
									</td>
								<% } else  { %>
									<td>
										<bean:write name="theForm" property="nteStr"/>
									</td>
								<% } %>
							<td colspan="2">
							</td>
						</tr>

						</table>
					</td>
					<td width="1%" valign="top">
					</td>
					<td width="46%" valign="top">
						<table width="100%" border="0" cellpadding="2" cellspacing="1">
						<tr>
							<td>
								<span class="shopassetdetailtxt">
									<b><app:storeMessage key="userWorkOrder.text.AddDateTime"/>:</b>
								</span>
							</td>
							<td>
								<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="addDate">
									<bean:define id="addDate" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="addDate"
												 type="java.util.Date"/>
									<%
									String dateTimeFormat = "MMM dd yyyy HH:mm:ss";
									SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat, ClwI18nUtil.getUserLocale(request));	
									%>
									<%=sdf.format(addDate)%>
								</logic:present>
							</td>
							
						</tr>
						
						<tr>
							<td>
								<span class="shopassetdetailtxt">
									<b><app:storeMessage key="userWorkOrder.text.status"/>:</b>
								</span>
							</td>
							<td>
								<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="statusCd">
									<bean:define id="status"
												 name="USER_WORK_ORDER_DETAIL_MGR_FORM"
												 property="statusCd"
												 type="java.lang.String"/>
									<%
										messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.status." + status.toUpperCase());
										if (messageKey != null) {
									%>
									<%=messageKey%>
									<%} else {%>
									<bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="statusCd"/>
									<%}%>

								</logic:present>
							</td>
							
						</tr>
						
						<tr>
							<td>
								<span class="shopassetdetailtxt">
									<b><app:storeMessage key="shop.userProfile.text.userName"/>:</b>
								</span>
							</td>
							<td>
								<bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userWorkOrderCreatorFullName"/>
							</td>
						</tr>
						
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
                        
                        <logic:greaterThan name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderId" value="0">
                        <% if(RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST.equals(workOrderStatus)) {
                            <tr>
                                <td>
                                    <span class="shopassetdetailtxt">
                                        <b><app:storeMessage key="userWorkOrder.text.setWorkOrderStatus"/>:</b>
                                    </span>
                                </td>
                                <td>
                                    <html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="forceStatusChange">
                                            <html:option value="none">
                                                <app:storeMessage  key="admin.select"/>
                                            </html:option>
                                            <html:option value="cancel">
                                                <app:storeMessage  key="userWorkOrder.text.cancelWorkOrder"/>
                                            </html:option>
                                    </html:select>
                                </td>
                            </tr>
                        <%} else if (RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL.equals(workOrderStatus)) { %>
                            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER%>">
                            <tr>
                                <td>
                                    <span class="shopassetdetailtxt">
                                        <b><app:storeMessage key="userWorkOrder.text.setWorkOrderStatus"/>:</b>
                                    </span>
                                </td>
                                <td>
                                    <html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="forceStatusChange">
                                            <html:option value="none">
                                                <app:storeMessage  key="admin.select"/>
                                            </html:option>
                                            <html:option value="cancel">
                                                <app:storeMessage  key="userWorkOrder.text.cancelWorkOrder"/>
                                            </html:option>
                                    </html:select>
                                </td>
                            </tr>
                            </app:authorizedForFunction>
                        <% } else if (RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER.equals(workOrderStatus)) { %>
                            <tr>
                                <td>
                                    <span class="shopassetdetailtxt">
                                        <b><app:storeMessage key="userWorkOrder.text.setWorkOrderStatus"/>:</b>
                                    </span>
                                </td>
                                <td>
                                    <html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="forceStatusChange">
                                            <html:option value="none">
                                                <app:storeMessage  key="admin.select"/>
                                            </html:option>
                                            <html:option value="complete">
                                                <app:storeMessage  key="userWorkOrder.text.completeWorkOrder"/>
                                            </html:option>
                                    </html:select>
                                </td>
                            </tr>
                        <% } else if (RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER.equals(workOrderStatus)) { %>
                            <tr>
                                <td>
                                    <span class="shopassetdetailtxt">
                                        <b><app:storeMessage key="userWorkOrder.text.setWorkOrderStatus"/>:</b>
                                    </span>
                                </td>
                                <td>
                                    <html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="forceStatusChange">
                                            <html:option value="none">
                                                <app:storeMessage  key="admin.select"/>
                                            </html:option>
                                            <html:option value="complete">
                                                <app:storeMessage  key="userWorkOrder.text.completeWorkOrder"/>
                                            </html:option>
                                            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER%>">
                                                <html:option value="close">
                                                    <app:storeMessage  key="userWorkOrder.text.closeWorkOrder"/>
                                                </html:option>
                                            </app:authorizedForFunction>
                                    </html:select>
                                </td>
                            </tr>
                        <% } else if (RefCodeNames.WORK_ORDER_STATUS_CD.REJECTED_BY_PROVIDER.equals(workOrderStatus)) { %>
                            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER%>">
                            <tr>
                                <td>
                                    <span class="shopassetdetailtxt">
                                        <b><app:storeMessage key="userWorkOrder.text.setWorkOrderStatus"/>:</b>
                                    </span>
                                </td>
                                <td>
                                    <html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="forceStatusChange">
                                            <html:option value="none">
                                                <app:storeMessage  key="admin.select"/>
                                            </html:option>
                                            
                                                <html:option value="close">
                                                    <app:storeMessage  key="userWorkOrder.text.closeWorkOrder"/>
                                                </html:option>
                                    </html:select>
                                </td>
                            </tr>
                            </app:authorizedForFunction>
                        <% } else if (RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(workOrderStatus)) { %>
                            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER%>">
                            <tr>
                                <td>
                                    <span class="shopassetdetailtxt">
                                        <b><app:storeMessage key="userWorkOrder.text.setWorkOrderStatus"/>:</b>
                                    </span>
                                </td>
                                <td>
                                    <html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="forceStatusChange">
                                            <html:option value="none">
                                                <app:storeMessage  key="admin.select"/>
                                            </html:option>
                                            <html:option value="previous">
                                                <app:storeMessage  key="userWorkOrder.text.previousWorkOrderStatus"/>
                                            </html:option>
                                            <html:option value="close">
                                                <app:storeMessage  key="userWorkOrder.text.closeWorkOrder"/>
                                            </html:option>
                                    </html:select>
                                </td>
                            </tr>
                            </app:authorizedForFunction>
                        <% } else if (RefCodeNames.WORK_ORDER_STATUS_CD.SENDING_TO_PROVIDER.equals(workOrderStatus)) { %>
                            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER%>">
                            <tr>
                                <td>
                                    <span class="shopassetdetailtxt">
                                        <b><app:storeMessage key="userWorkOrder.text.setWorkOrderStatus"/>:</b>
                                    </span>
                                </td>
                                <td>
                                    <html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="forceStatusChange">
                                            <html:option value="none">
                                                <app:storeMessage  key="admin.select"/>
                                            </html:option>
                                            <html:option value="previous">
                                                <app:storeMessage  key="userWorkOrder.text.previousWorkOrderStatus"/>
                                            </html:option>
                                    </html:select>
                                </td>
                            </tr>
                            </app:authorizedForFunction>
                        <% } %>
                        </logic:greaterThan>
						</table>
					</td>
				</tr>
				
				</table>
				<!-- end of Summary -->
			</td>
		</tr>
		
		<tr><td colspan="3">&nbsp;</td></tr>
		
		<tr>
			<td colspan="3">
				
				<!-- begin of first column group-->
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
				
				<tr align="center">
					
					<!-- begin left column 1 -->
					<td width="53%" valign="top">
						<table width="100%" border="0" cellpadding="2" cellspacing="1">
						
						<tr>
							<td colspan="4">
                                <span class="shopassetdetailtxt">
                                    <b><app:storeMessage key="userWorkOrder.text.longDesc"/>:</b>&nbsp;<span class="reqind">*</span>
                                </span>
							</td>
						</tr>
						
						<tr>
							<td colspan="4">
								<% if(editingAuthorized) { %>
									<html:textarea rows="7" cols="46" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="longDesc"/>
								<% } else { %>
									<bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="longDesc"/>
								<% } %>
							</td>
						</tr>
						
						<tr><td colspan="4"></td></tr>
						
                        <%
                            String displayAssets = "none";
                        %>
                        
                        <%if(editingAuthorized) {%>
                        <tr>
                            <td colspan="4" align="left">
                                <html:button property="action" styleClass="store_fb" onclick="doShowAssetAndWarrantyConfig()">
                                    <app:storeMessage key="global.label.locateWorkOrderAsset"/>
                                </html:button>
                            </td>
                        </tr>
                        <% } %>
                        
                        <tr>
							<td colspan="4">
                                                                
                                <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="activeAsset" >
                                    <logic:greaterThan name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="activeAsset.assetId" value="0">
                                        <%
                                            displayAssets = "";
                                        %>
                                    </logic:greaterThan>
                                </logic:present>
                                
                                <div id="AssetAndWarrantyConfig" style="display: <%=displayAssets%>">
							        <table width="100%" cellpadding="1" cellspacing="5" border="0">
							        <tr>
							            <td width="32%">
							                <span class="shopassetdetailtxt">
							                    <b><app:storeMessage key="userWorkOrder.text.workOrderItems.assets"/>:</b>
							                </span>
							            </td>
							            <td>
							                <%if(editingAuthorized) {%>
											<div id="assetsSelect">
							                <html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="activeAssetIdStr"
							                             onchange="ajaxconnect('userWorkOrderDetail.do', 'action=changeActiveAsset&newActiveAssetId='+this.value, '', woiDynamicBoxes.populateAndReDraw)">
							                    <html:option value="">
							                        <app:storeMessage  key="admin.select"/>
							                    </html:option>
							                    
							                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM"
							                                   property="assetGroups">
							                        <logic:iterate id="assetGroup"
							                                       name="USER_WORK_ORDER_DETAIL_MGR_FORM"
							                                       property="assetGroups"
							                                       type="com.cleanwise.service.api.value.PairView">

							                            <html:option styleClass="optiontextcenter" value="" disabled="true">
							                                <%="----- "+assetGroup.getObject1()+" -----"%>
							                            </html:option>

							                            <% AssetDataVector assets = (AssetDataVector)assetGroup.getObject2();
							                                if (assets != null && !assets.isEmpty()) {
							                                    Iterator assetIt = assets.iterator();
							                                    while (assetIt.hasNext()) {
							                                        AssetData asset = (AssetData) assetIt.next();
							                            %>

							                                        <html:option value="<%=String.valueOf(asset.getAssetId())%>">
							                                            <%=asset.getShortDesc()%>
							                                        </html:option>

							                                <% }
							                                }  %>
							                        </logic:iterate>
							                    </logic:present>
							                </html:select>
											</div>
							                <% } else { %>
							                <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="activeAsset">
							                    <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="activeAsset.shortDesc"/>
							                </logic:present>
							                <% } %>
							            </td>
							        </tr>
							        
									<tr>
										<td width="32%">
											<span class="shopassetdetailtxt">
												<b><app:storeMessage key="userAssets.shop.text.param.modelNumber:"/></b>
											</span>
										</td>
										<td>
											<div id="assetModelNumberValue"></div>
										</td>
									</tr>
									
									<tr>
										<td width="32%">
											<span class="shopassetdetailtxt">
												<b><app:storeMessage key="userAssets.shop.text.param.serialnumber:"/></b>
											</span>
										</td>
										<td>
											<div id="assetModelSerialNumber"></div>
										</td>
									</tr>
							        
							        <tr><td colspan="2"></td></tr>
									
                                    <tr>
                                        <td colspan="2">
                                            <div id="activeWarrantyTable">
                                            </div>
                                        </td>
                                    </tr>
                                    
                                    
							            <script language="JavaScript1.2"><!--
							            <%
											String modelNumberVal   = "";
											String serialNumberVal  = "";
							                
											if(Utility.isSet(((UserWorkOrderDetailMgrForm)theForm).getActiveAssetIdStr())){
							                    String serial    =((UserWorkOrderDetailMgrForm)theForm).getActiveAsset().getSerialNum();
							                    String model     = ((UserWorkOrderDetailMgrForm)theForm).getActiveAsset().getModelNumber();
							                    modelNumberVal   = Utility.strNN(model);
							                    serialNumberVal  = Utility.strNN(serial);
							                }
							            %>
										
										function writeAssetDynamicBox(modelFieldId, modelFieldValue, serialNumFieldId, serialNumFieldValue) {
											assetDynamicBox.init(modelFieldId, modelFieldValue, serialNumFieldId, serialNumFieldValue); 
											assetDynamicBox.redraw();
										}
										
										writeAssetDynamicBox('assetModelNumberValue', '<%=modelNumberVal%>', 'assetModelSerialNumber', '<%=serialNumberVal%>');
										
							            <%
                                            String warrantyTableHeader = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWorkOrder.text.workOrderItems.warranty"));
                                            if (warrantyTableHeader == null) {
                                                warrantyTableHeader = "Warranty";
                                            }
                                            String WarrantyNumber = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWarranty.text.warrantyNumber"));
                                            if (WarrantyNumber == null) {
                                                WarrantyNumber = "Warranty#";
                                            }
                                            String warrantyName = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"storeWarranty.text.warrantyName"));
                                            if (warrantyName == null) {
                                                warrantyName = "Name";
                                            }
                                            String warrantyLongDesc = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWarranty.text.warrantyLongDesc"));
                                            if (warrantyLongDesc == null) {
                                                warrantyLongDesc = "Long Description";
                                            }
                                            String warrantyCost = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWarranty.text.warrantyCost"));
                                            if (warrantyCost == null) {
                                                warrantyCost = "Warranty Cost";
                                            }
                                            String warrantyType = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWarranty.text.warrantyType"));
                                            if (warrantyType == null) {
                                                warrantyType = "Type";
                                            }
                                            String warrantyDurationTypeCd = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWarranty.text.warrantyDurationTypeCd"));
                                            if (warrantyDurationTypeCd == null) {
                                                warrantyDurationTypeCd = "Duration Type";
                                            }
                                            String warrantyDuration = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWarranty.text.warrantyDuration"));
                                            if (warrantyDuration == null) {
                                                warrantyDuration = "Duration";
                                            }
                                            String warrantyAddDate = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWorkOrder.text.note.addDate"));
                                            if (warrantyAddDate == null) {
                                                warrantyAddDate = "Added Date";
                                            }
                                        %>
                                        
                                        var warrantyTableHeaderArray = new Array();
                                            
                                            warrantyTableHeaderArray[0] = '<%=warrantyTableHeader%>';
                                            warrantyTableHeaderArray[1] = '<%=WarrantyNumber%>';
                                            warrantyTableHeaderArray[2] = '<%=warrantyName%>';
                                            warrantyTableHeaderArray[3] = '<%=warrantyLongDesc%>';
                                            warrantyTableHeaderArray[4] = '<%=warrantyCost%>';
                                            warrantyTableHeaderArray[5] = '<%=warrantyType%>';
                                            warrantyTableHeaderArray[6] = '<%=warrantyDurationTypeCd%>';
                                            warrantyTableHeaderArray[7] = '<%=warrantyDuration%>';
                                            warrantyTableHeaderArray[8] = '<%=warrantyAddDate%>';
                                            
                                        var warrantyArray;
                                        var warrantyIdx = 0;
                                        <% if (((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset() != null &&
                                              !((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().isEmpty()) { %>
                                            warrantyArray = new Array();
                                            
                                        <% 	for(int i = 0; i < ((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().size(); i++) { %>
                                                warrantyArray[warrantyIdx] = new Array();
                                                warrantyArray[warrantyIdx][0] ='<%=((WarrantyData)((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getWarrantyId()%>';
                                                warrantyArray[warrantyIdx][1] ='<%=((WarrantyData)((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getWarrantyNumber()%>';
                                                warrantyArray[warrantyIdx][2] ='<%=((WarrantyData)((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getShortDesc()%>';
                                                warrantyArray[warrantyIdx][3] ='<%=((WarrantyData)((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getLongDesc()%>';
                                                <%
                                                    BigDecimal costBD = ((WarrantyData)((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getCost();
                                                    String cost = "";
                                                    if (costBD != null) {
                                                        cost = costBD.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                                                    }
                                                %>
                                                warrantyArray[warrantyIdx][4] ='<%=cost%>';
                                                warrantyArray[warrantyIdx][5] ='<%=((WarrantyData)((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getTypeCd()%>';
                                                warrantyArray[warrantyIdx][6] ='<%=((WarrantyData)((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getDurationTypeCd()%>';
                                                warrantyArray[warrantyIdx][7] ='<%=((WarrantyData)((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getDuration()%>';
                                                <%
                                                    String date = sdFormat.format(((WarrantyData)((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getAddDate());
                                                %>
                                                warrantyArray[warrantyIdx][8] ='<%=date%>';
                                                warrantyIdx++;
                                        <% 	} %>
                                        <% } %>
                                        
                                        
                                        function warrantyDinamicBoxInit(divInsertInto, header, array, editingAuthorized) {
                                            //alert("call warrantyDynamicBox.init: divInsertInto = " + divInsertInto + " header = " + header + " array = " + array);
                                            warrantyDynamicBox.init(divInsertInto, header, array, editingAuthorized);
                                        }

                                        warrantyDinamicBoxInit('activeWarrantyTable', warrantyTableHeaderArray, warrantyArray, '<%=String.valueOf(editingAuthorized)%>');

							            //-->
							            </script>
									</table>
								</div>
							</td>
						</tr>
                        
                        
                        
						</table>
					</td>

					<!-- end left column 1 -->
					
					<td width="1%" valign="top"></td>
					
					<!-- begin right column 1 -->
					<td width="46%" valign="top">
						<table width="100%" border="0" cellpadding="2" cellspacing="1">
						<tr>
							<td>
								<span class="shopassetdetailtxt">
									<b><app:storeMessage key="userWorkOrder.text.WorkOrderSchedule"/>:</b>
								</span>
							</td>
						</tr>
						
						<tr><td></td></tr>
				
						<tr align="center">
							<td>
								<table width="100%" align="center" cellpadding="2" cellspacing="1" border="0" style="border:#D3D3D3 1px solid">
								
								<tr>
									<td class="customerltbkground" vAlign="top" align="center" colSpan="2">
										<span class="shopassetdetailtxt">
											<b><app:storeMessage key="userWorkOrder.text.workOrderQuoted"/></b>
										</span>
									</td>
								</tr>

								<tr>
									<td align="left">
                                        <span class="shopassetdetailtxt">
                                            <b>
                                                <app:storeMessage key="userWorkOrder.text.quotedStartDate"/>:
                                                <small>(<%=ClwI18nUtil.getUIDateFormat(request)%>)</small>
                                            </b>
                                        </span>
                                    </td>
									<td nowrap="nowrap" align="right">
										<% if(editingAuthorized) { %>
										<html:text size="8" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="quotedStartDate"/>
										<% if ("Y".equals(isMSIE)) { %>
										<a href="#"
										   onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ESTIMATED_START_DATE, document.forms[0].quotedStartDate, null, -7300, 7300,null,-1);"
										   title="Choose Date"><img name="ESTIMATED_START_DATE" src="../externals/images/showCalendar.gif" width="19" height="19"
																	border="0"
																	style="position:relative"
																	onmouseover="window.status='Choose Date';return true"
																	onmouseout="window.status='';return true"></a>
										<% } else { %>
                                        <a href="#" onClick="showCalendar('quotedStartDate', event); return false;"
                                            title="Choose Date">
                                            <img name="QUOTEDSTARTDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                                            onmouseover="window.status='Choose Date';return true"
                                            onmouseout="window.status='';return true">
                                        </a>
										<% } %>
										<% } else  { %>
										<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="quotedStartDate">
											<bean:define id="quotedStartDate" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="quotedStartDate" type="java.lang.String"/>
											 <%if (Utility.isSet(quotedStartDate)) {%>
											<%=ClwI18nUtil.formatDate(request,quotedStartDate,DateFormat.DEFAULT)%>
											<%}%>
										</logic:present>
										<% } %>
									</td>
                                </tr>
                                <tr>
									<td align="left">
                                        <span class="shopassetdetailtxt">
                                            <b>
                                                <app:storeMessage key="userWorkOrder.text.quotedFinishDate"/>:
                                                <small>(<%=ClwI18nUtil.getUIDateFormat(request)%>)</small>
                                            </b>
                                        </span>
                                    </td>
									<td nowrap="nowrap" align="right">
										<% if(editingAuthorized) { %>
										<html:text size="8" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="quotedFinishDate"/>
										<% if ("Y".equals(isMSIE)) { %>
										<a href="#"
										   onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ESTIMATED_FINISH_DATE, document.forms[0].quotedFinishDate, null, -7300, 7300,null,-1);"
										   title="Choose Date"><img name="ESTIMATED_FINISH_DATE" src="../externals/images/showCalendar.gif" width=19 height=19
																	border=0
																	style="position:relative"
																	onmouseover="window.status='Choose Date';return true"
																	onmouseout="window.status='';return true"></a>
										<% } else { %>
                                        <a href="#" onClick="showCalendar('quotedFinishDate', event); return false;"
                                            title="Choose Date">
                                            <img name="QUOTEDFINISHDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                                            onmouseover="window.status='Choose Date';return true"
                                            onmouseout="window.status='';return true">
                                        </a>
										<% } %>
										<% } else  { %>
										<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="quotedFinishDate">
											<bean:define id="quotedFinishDate" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="quotedFinishDate" type="java.lang.String"/>
											<%if (Utility.isSet(quotedFinishDate)) {%>
											<%=ClwI18nUtil.formatDate(request, quotedFinishDate, DateFormat.DEFAULT)%>
											<%}%>
										</logic:present>
										<% } %>
									</td>
								</tr>
								</table>
							</td>
						</tr>
						
						<tr><td></td></tr>
						
						<tr align="center">
							<td>
								<table width="100%" align="center" cellpadding="2" cellspacing="1" border="0" style="border:#D3D3D3 1px solid">
								<tr>
								    <td class="customerltbkground" vAlign="top" align="center" colSpan="2">
								        <span class="shopassetdetailtxt">
								            <b><app:storeMessage key="userWorkOrder.text.workOrderActual"/></b>
								        </span>
								    </td>
								</tr>

								<tr>
								    <td align="left">
                                        <span class="shopassetdetailtxt">
                                            <b>
                                                <app:storeMessage key="userWorkOrder.text.actualStartDate"/>:
                                                <small>(<%=ClwI18nUtil.getUIDateFormat(request)%>)</small>
                                            </b>
                                        </span>
                                    </td>
								    <td nowrap="nowrap" align="right">
								        <% if(editingAuthorized || isSendToProviderStatus) { %>
								        <html:text size="8" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="actualStartDate"/>
								        <% if ("Y".equals(isMSIE)) { %>
								        <a href="#"
								           onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ACTUAL_START_DATE, document.forms[0].actualStartDate, null, -7300, 7300,null,-1);"
								           title="Choose Date"><img name="ACTUAL_START_DATE" src="../externals/images/showCalendar.gif" width="19" height="19"
								                                    border="0"
								                                    style="position:relative"
								                                    onmouseover="window.status='Choose Date';return true"
								                                    onmouseout="window.status='';return true"></a>
								        <% } else { %>
                                        <a href="#" onClick="showCalendar('actualStartDate', event); return false;"
                                            title="Choose Date">
                                            <img name="ACTUALSTARTDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                                            onmouseover="window.status='Choose Date';return true"
                                            onmouseout="window.status='';return true">
                                        </a>
								        <% } %>
								        <% } else  { %>
								        <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="actualStartDate">
								            <bean:define id="actualStartDate" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="actualStartDate" type="java.lang.String"/>
								            <%if (Utility.isSet(actualStartDate)) {%>
								            <%=ClwI18nUtil.formatDate(request,actualStartDate,DateFormat.DEFAULT)%>
								            <%}%>
								        </logic:present>
								        <% } %>
								    </td>
                                </tr>
                                <tr>
								    <td align="left">
                                        <span class="shopassetdetailtxt">
                                            <b>
                                                <app:storeMessage key="userWorkOrder.text.actualFinishDate"/>:
                                                <small>(<%=ClwI18nUtil.getUIDateFormat(request)%>)</small>
                                            </b>
                                        </span>
                                    </td>
								    <td nowrap="nowrap" align="right">
								        <% if(editingAuthorized || isSendToProviderStatus) { %>
								        <html:text size="8" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="actualFinishDate"/>
								        <% if ("Y".equals(isMSIE)) { %>
								        <a href="#"
								           onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ACTUAL_FINISH_DATE, document.forms[0].actualFinishDate, null, -7300, 7300,null,-1);"
								           title="Choose Date"><img name="ACTUAL_FINISH_DATE" src="../externals/images/showCalendar.gif" width=19 height=19
								                                    border=0
								                                    style="position:relative"
								                                    onmouseover="window.status='Choose Date';return true"
								                                    onmouseout="window.status='';return true"></a>
								        <% } else { %>
                                        <a href="#" onClick="showCalendar('actualFinishDate', event); return false;"
                                            title="Choose Date">
                                            <img name="ACTUALFINISHDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                                            onmouseover="window.status='Choose Date';return true"
                                            onmouseout="window.status='';return true">
                                        </a>
								        <% } %>
								        <% } else  { %>
								        <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="actualFinishDate">
								            <bean:define id="actualFinishDate" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="actualFinishDate" type="java.lang.String"/>
								            <%if (Utility.isSet(actualFinishDate)) {%>
								            <%=ClwI18nUtil.formatDate(request, actualFinishDate, DateFormat.DEFAULT)%>
								            <%}%>
								        </logic:present>
								        <% } %>
								    </td>
								</tr>
								</table>
							</td>
						</tr>
						
						<tr><td>&nbsp;</td></tr>
						
						<tr>
							<td>
								<span class="shopassetdetailtxt">
									<b><app:storeMessage key="userWorkOrder.text.WorkOrderCostSummary"/>:</b>
								</span>
							</td>
						</tr>
						
						<tr><td></td></tr>
						
						<tr align="center">
							<td>
								<table width="100%"  cellpadding="2" cellspacing="1" border="0" cols="5" rows="5" style="border:#D3D3D3 1px solid">
								<tr class="customerltbkground" align="center">
								    <td>&nbsp;</td>
									<td>
								        <span class="shopassetdetailtxt">
								            <b><app:storeMessage key="userWorkOrder.text.BilledService"/></b>
								        </span>
								    </td>
									<td>
								        <span class="shopassetdetailtxt">
								            <b><app:storeMessage key="userWorkOrder.text.workOrderItems.warranty"/></b>
								        </span>
								    </td>
									<td>
								        <span class="shopassetdetailtxt">
								            <b><app:storeMessage key="userWorkOrder.text.PMContract"/></b>
								        </span>
								    </td>
									<td>
								        <span class="shopassetdetailtxt">
								            <b><app:storeMessage key="userWorkOrder.text.Total"/></b>
								        </span>
								    </td>
								</tr>
                                
								<tr style="background-color: #F0F0F0">
									<td align="left">
										<span class="shopassetdetailtxt">
								            <b><app:storeMessage key="userWorkOrder.text.Parts"/></b>
								        </span>
									</td>
									<td align="right">
								        <div id="partsBilledService">
                                            <bean:write name="theForm" property="partsBilledService"/>
                                        </div>
								    </td>
									<td align="right">
								        <div id="partsWarranty">
                                            <bean:write name="theForm" property="partsWarranty"/>
                                        </div>
								    </td>
									<td align="right">
								        <div id="partsPMContract">
                                            <bean:write name="theForm" property="partsPMContract"/>
                                        </div>
								    </td>
									<td align="right">
								        <div id="partsTotal">
                                            <bean:write name="theForm" property="partsTotal"/>
                                        </div>
								    </td>
								</tr>
								
								<tr style="background-color: #D8D8D8">
									<td align="left">
										<span class="shopassetdetailtxt">
								            <b><app:storeMessage key="userWorkOrder.text.Labor"/></b>
								        </span>
									</td>
									<td align="right">
								        <div id="laborBilledService">
                                            <bean:write name="theForm" property="laborBilledService"/>
                                        </div>
								    </td>
									<td align="right">
								        <div id="laborWarranty">
                                            <bean:write name="theForm" property="laborWarranty"/>
                                        </div>
								    </td>
									<td align="right">
								        <div id="laborPMContract">
                                            <bean:write name="theForm" property="laborPMContract"/>
                                        </div>
								    </td>
									<td align="right">
								        <div id="laborTotal">
                                            <bean:write name="theForm" property="laborTotal"/>
                                        </div>
								    </td>
								</tr>
								
								<tr style="background-color: #F0F0F0">
									<td align="left">
										<span class="shopassetdetailtxt">
								            <b><app:storeMessage key="userWorkOrder.text.Travel"/></b>
								        </span>
									</td>
									<td align="right">
								        <div id="travelBilledService">
                                            <bean:write name="theForm" property="travelBilledService"/>
                                        </div>
								    </td>
									<td align="right">
								        <div id="travelWarranty">
                                            <bean:write name="theForm" property="travelWarranty"/>
                                        </div>
								    </td>
									<td align="right">
								        <div id="travelPMContract">
                                            <bean:write name="theForm" property="travelPMContract"/>
                                        </div>
								    </td>
									<td align="right">
								        <div id="travelTotal">
                                            <bean:write name="theForm" property="travelTotal"/>
                                        </div>
								    </td>
								</tr>
								
								<tr style="background-color: #D8D8D8">
									<td align="left">
										<span class="shopassetdetailtxt">
								            <b><app:storeMessage key="userWorkOrder.text.Total"/></b>
								        </span>
									</td>
									<td align="right">
								        <div id="totalBilledService">
                                            <bean:write name="theForm" property="totalBilledService"/>
                                        </div>
								    </td>
									<td align="right">
								        <div id="totalWarranty">
                                            <bean:write name="theForm" property="totalWarranty"/>
                                        </div>
								    </td>
									<td align="right">
								        <div id="totalPMContract">
                                            <bean:write name="theForm" property="totalPMContract"/>
                                        </div>
								    </td>
									<td align="right">
								        <div id="totalTotal">
                                            <bean:write name="theForm" property="totalTotal"/>
                                        </div>
								    </td>
								</tr>
								
								</table>
							</td>
						</tr>
						
						</table>
					</td>
					<!-- end right column 1 -->
					
				</tr>
				
				</table>
				<!-- end of first column group-->
				
			</td>
		</tr>
		
		<!-- break between 1 columns and 2 columns -->
		<tr><td colspan="3">&nbsp;</td></tr>
		
		<tr>
			<td colspan="3">
				
				<!-- begin of second column group-->
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
				
				<tr align="center">
					
					<!-- begin left column 2 -->
					<td width="53%" valign="top">
						<table width="100%" border="0" cellpadding="2" cellspacing="1">
						
						<!-- begin serviceProvider -->
						<tr align="center">
						    <td>
								<%
								String contactNameMess  = "";
							    String contactNameVal   = "";
							    String contactPhoneMess = "";
							    String contactPhoneVal  = "";
							    String contactEmailMess = "";
							    String contactEmailVal  = "";
							    String contactFaxMess = "";
							    String contactFaxVal  = "";
							    String providerHeaderMess  = "";
							    String providerMess  = "";
							    String serviceProviderValue = "";
							    String serviceProviderElementName ="serviceProviderIdStr";

							    contactNameMess    = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.serviceProvider.contactName:"));
							    contactPhoneMess   = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.serviceProvider.contactPhone:"));
							    contactEmailMess   = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.serviceProvider.contactEmail:"));
							    contactFaxMess     = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.serviceProvider.contactFax:"));
							    providerHeaderMess = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.serviceProvider"));
							    providerMess       = Utility.strNN(ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.serviceProvider:"));

							    if (Utility.isSet(((UserWorkOrderDetailMgrForm) theForm).getServiceProviderIdStr())) {
							        String name1 = Utility.strNN(((UserWorkOrderDetailMgrForm) theForm).getActiveServiceProvider().getPrimaryAddress().getName1());
							        String name2 = Utility.strNN(((UserWorkOrderDetailMgrForm) theForm).getActiveServiceProvider().getPrimaryAddress().getName2());
							        contactNameVal  = name1 + " " + name2;
							        contactPhoneVal = Utility.strNN(((UserWorkOrderDetailMgrForm) theForm).getActiveServiceProvider().getPrimaryPhone().getPhoneNum());
							        contactFaxVal   = Utility.strNN(((UserWorkOrderDetailMgrForm) theForm).getActiveServiceProvider().getPrimaryFax().getPhoneNum());
							        contactEmailVal = Utility.strNN(((UserWorkOrderDetailMgrForm) theForm).getActiveServiceProvider().getPrimaryEmail().getEmailAddress());
							        serviceProviderValue = ((UserWorkOrderDetailMgrForm) theForm).getServiceProviderIdStr();
							    }
								%>

							    <div id="serviceProviderMainBody"></div>


								<script language="JavaScript1.2"><!--

								var providerArray;
								var providerIdx=0;
								<%if(((UserWorkOrderDetailMgrForm)theForm).getActualServiceProviders()!=null
								&& !((UserWorkOrderDetailMgrForm)theForm).getActualServiceProviders().isEmpty()
								|| !editingAuthorized){  %>
								providerArray = new Array();
								<% for(int i=0;i<((UserWorkOrderDetailMgrForm)theForm).getActualServiceProviders().size();i++){%>
								providerArray[providerIdx] = new Array();
								providerArray[providerIdx][0] ="<%=((ServiceProviderData)((UserWorkOrderDetailMgrForm)theForm).getActualServiceProviders().get(i)).getBusEntity().getBusEntityId()%>";
								providerArray[providerIdx][1] ="<%=((ServiceProviderData)((UserWorkOrderDetailMgrForm)theForm).getActualServiceProviders().get(i)).getBusEntity().getShortDesc()%>";
								providerIdx++;
								<%}%>
								<%}%>

								function serviceProviderInit(providerHeaderMess,providerMessage,array,spValue,elName,
														                                    contactNameMess, contactNameVal, contactPhoneMess, contactPhoneVal,
														                                    contactEmailMess, contactEmailVal,contactFaxMess, contactFaxVal,
														                                    editingAuthorized) {

														        woDynamicBoxes.serviceProvider.init(providerHeaderMess,providerMessage,array,spValue,elName,
														                                    contactNameMess, contactNameVal, contactPhoneMess, contactPhoneVal,
														                                    contactEmailMess, contactEmailVal,contactFaxMess, contactFaxVal,
														                                    editingAuthorized);

														        woDynamicBoxes.serviceProvider.initWriter('serviceProviderMainBody');
														        woDynamicBoxes.serviceProvider.write();
								}

								serviceProviderInit("<%=providerHeaderMess%>",
														        "<%=providerMess%>",
														        providerArray,
														        "<%=serviceProviderValue%>",
														        "<%=serviceProviderElementName%>",
														        "<%=contactNameMess%>",
														        "<%=contactNameVal%>",
														        "<%=contactPhoneMess%>",
														        "<%=contactPhoneVal%>",
														        "<%=contactEmailMess%>",
														        "<%=contactEmailVal%>",
														        "<%=contactFaxMess%>",
														        "<%=contactFaxVal%>",
														        "<%=editingAuthorized%>");

								//-->
								</script>
						    </td>
						</tr>
						<!-- end of serviceProvider -->
						
						<tr><td></td></tr>
						
						<!-- begin userContactInformation -->
						<tr align="center">
						    <td>
						        <table width="100%" border="0" cellpadding="2" cellspacing="1" style="border:#000000 1px solid">
					            <tr>
					                <td class="customerltbkground" valign="top" align="center" colSpan="4">
					                     <span class="shopassetdetailtxt">
					                       <b><app:storeMessage key="userWorkOrder.text.userContactInformation"/></b>
					                     </span>
					                </td>
					            </tr>
					            <tr>
					                <td>
					                    <span class="shopassetdetailtxt">
					                    <b><app:storeMessage key="userWorkOrder.text.firstName"/>:</b>&nbsp;<span class="reqind">*</span>
					                    </span>
					               </td>

					                <td colspan="3">
					                    <% if(editingAuthorized) { %>
					                    <html:text name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactFirstName.value" maxlength="40"  size="45"/>
					                    <% } else  { %>
					                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactFirstName.value">
					                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactFirstName.value"/>
					                    </logic:present>
					                    <% } %>
					                </td>
					            </tr>
					            <tr>
					                <td>
					                    <span class="shopassetdetailtxt">
					                        <b><app:storeMessage key="userWorkOrder.text.lastName"/>:</b>&nbsp;<span class="reqind">*</span>
					                    </span>
					               </td>

					                <td colspan="3">
					                    <% if(editingAuthorized) { %>
					                    <html:text name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactLastName.value" maxlength="40" size="45"/>
					                    <% } else  { %>
					                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactLastName.value">
					                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactLastName.value"/>
					                    </logic:present>
					                    <% } %>
					                </td>
					            </tr>
					            <tr>
					                <td><span class="shopassetdetailtxt">
					                    <b><app:storeMessage key="userWorkOrder.text.address1"/>:</b>&nbsp;<span class="reqind">*</span>
					                    </span>
					               </td>

					                <td colspan="3">
					                    <% if(editingAuthorized) { %>
					                    <html:text name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactAddress1.value" maxlength="80" size="45"/>
					                    <% } else  { %>
					                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactAddress1.value">
					                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactAddress1.value"/>
					                    </logic:present>
					                    <% } %>
					                </td>
					            </tr>
					            <tr>
					                <td><span class="shopassetdetailtxt">
					                    <b><app:storeMessage key="userWorkOrder.text.address2"/>:</b>
					                    </span>
					                </td>
					                <td colspan="3">
					                    <% if(editingAuthorized) { %>
					                    <html:text name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactAddress2.value" maxlength="80" size="45"/>
					                   <% } else  { %>
					                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactAddress2.value">
					                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactAddress2.value"/>
					                    </logic:present>
					                    <% } %>
					                </td>
					            </tr>
					            <tr>
					                <td><span class="shopassetdetailtxt">
					                    <b><app:storeMessage key="userWorkOrder.text.conutry"/>:</b>&nbsp;<span class="reqind">*</span>
					                    </span>
					                </td>

					                <td colspan="3">
					                    <% if(editingAuthorized) { %>
					                    <html:select name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactCountry.value">
					                        <html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
					                        <html:options  collection="countries.vector" property="shortDesc" />
					                    </html:select>
					                    <% } else  { %>
					                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactCountry.value">
					                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactCountry.value"/>
					                    </logic:present>
					                    <% } %>
					                </td>
					            </tr>
					            <tr>
					                <td><span class="shopassetdetailtxt">
					                    <b><app:storeMessage key="userWorkOrder.text.city"/>:</b>&nbsp;<span class="reqind">*</span>
					                    </span>
					                </td>
					                <td colspan="3">
					                    <% if(editingAuthorized) { %>
					                    <html:text name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactCity.value" maxlength="40" size="45"/>
					                    <% } else  { %>
					                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactCity.value">
					                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactCity.value"/>
					                    </logic:present>
					                    <% } %>
					                </td>
					            </tr>
					            <tr>
					                <td><span class="shopassetdetailtxt">
					                    <b><app:storeMessage key="userWorkOrder.text.state"/>:</b>&nbsp;<span class="reqind">*</span>
					                    </span>
					                </td>
					                <td>
					                    <% if(editingAuthorized) { %>
					                    <html:text  maxlength="80" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactState.value" size="15"/>
					                    <% } else  { %>
					                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactState.value">
					                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactState.value"/>
					                    </logic:present>
					                    <% } %>
					                </td>
					                <td><span class="shopassetdetailtxt">
					                    <b><app:storeMessage key="userWorkOrder.text.zip"/>:</b>&nbsp;<span class="reqind">*</span>
					                    </span>
					                </td>
					                <td align="right">
					                    <% if(editingAuthorized) { %>
					                    <html:text name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactPostalCode.value" maxlength="15" size="15"/>
					                    <% } else  { %>
					                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactPostalCode.value">
					                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactPostalCode.value"/>
					                    </logic:present>
					                    <% } %>
					                </td>
					            </tr>
					            <tr>
					                <td><span class="shopassetdetailtxt">
					                    <b><app:storeMessage key="userWorkOrder.text.phone"/>:</b>&nbsp;<span class="reqind">*</span>
					                    </span>
					                </td>
					                <td>
					                    <% if(editingAuthorized) { %>
					                    <html:text name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactPhoneNum.value" maxlength="30" size="15"/>
					                    <% } else  { %>
					                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactPhoneNum.value">
					                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactPhoneNum.value"/>
					                    </logic:present>
					                    <% } %>
					                </td>

					                <td><span class="shopassetdetailtxt">
					                    <b><app:storeMessage key="userWorkOrder.text.mobileNum"/>:</b>
					                    </span>
					                </td>

					                <td align="right">
					                    <% if(editingAuthorized) { %>
					                    <html:text name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactMobilePhone.value" maxlength="30" size="15"/>
					                    <% } else  { %>
					                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactMobilePhone.value">
					                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactMobilePhone.value"/>
					                    </logic:present>
					                    <% } %>
					                </td>
					            </tr>

					            <tr>
					                <td><span class="shopassetdetailtxt">
					                    <b><app:storeMessage key="userWorkOrder.text.fax"/>:</b>
					                    </span>
					               </td>
					                <td>
					                    <% if(editingAuthorized) { %>
					                    <html:text name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactFaxNum.value" maxlength="30" size="15"/>
					                    <% } else  { %>
					                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactFaxNum.value">
					                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactFaxNum.value"/>
					                    </logic:present>
					                    <% } %>
					                </td>
					                <td><span class="shopassetdetailtxt">
					                    <b><app:storeMessage key="userWorkOrder.text.email"/>:</b>
					                   </span>
					                </td>
					                <td align="right">
					                    <% if(editingAuthorized) { %>
					                    <html:text name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactEmail.value" maxlength="255" size="15"/>
					                    <% } else  { %>
					                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactEmail.value">
					                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="userContactEmail.value"/>
					                    </logic:present>
					                    <% } %>
					                </td>
					            </tr>

					        </table>
						    </td>
						</tr>
						<!-- end of userContactInformation -->
						
						</table>
					</td>
					<!-- end left column 2 -->
					
					<td width="1%"></td>
					
					<!-- begin right column 2 -->
					<td width="46%" valign="top">
						<table width="100%" border="0" cellpadding="2" cellspacing="1">
						
						<tr align="center">
						    <td>
								<table width="100%" border="0" cellpadding="2" cellspacing="1">
									<tr>
								        <td class="customerltbkground" valign="top" align="center" colspan="3">
								            <span class="shopassetdetailtxt"><B><app:storeMessage key="userWorkOrder.text.note"/></B>
								                <logic:greaterThan name="woid" value="0">
								                    <% if(editingAuthorized || isSendToProviderStatus) { %>
								                    <html:button property="action" styleClass="store_fb" onclick="actionSubmitTb(0,'createNote','t_userWorkOrderNoteDetail','f_userSecondaryToolbar');">
								                        <app:storeMessage key="global.label.addNote"/>
								                    </html:button>
								                    <%}%>
								                </logic:greaterThan>
								            </span>
								        </td>
								    </tr>
								    <tr>
								        <td class="shopcharthead" align="center">
								            <div class="fivemargin">
								                <app:storeMessage key="userWorkOrder.text.note.description"/>
								            </div>
								        </td>
								        <td class="shopcharthead" align="center">
								            <div class="fivemargin">
								                <app:storeMessage key="userWorkOrder.text.note.addDate"/>
								            </div>
								        </td>
								        <td class="shopcharthead" align="center">
								            <div class="fivemargin">
								                <app:storeMessage key="userWorkOrder.text.note.addBy"/>
								            </div>
								        </td>
								    </tr>
								    <logic:present name="workorderdet" property="notes">
								        <logic:iterate id="note" name="workorderdet" property="notes"
								                       type="com.cleanwise.service.api.value.WorkOrderNoteData" indexId="j">
								            <bean:define id="wonId" name="note" property="workOrderNoteId"/>
									<tr id="note<%=((Integer)j).intValue()%>">
										<td>&nbsp;
											<logic:present name="note" property="shortDesc">
												<a href="../userportal/userWorkOrderNote.do?action=noteDetail&workOrderNoteId=<%=wonId%>&display=t_userWorkOrderNoteDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar">
													<bean:write name="note" property="shortDesc"/>
												</a>
											</logic:present>
										</td>
										<td>
											<logic:present name="note" property="addDate">
												<%=ClwI18nUtil.formatDate(request, note.getAddDate(), DateFormat.DEFAULT)%>
											</logic:present>
										</td>
										<td>
											<logic:present name="note" property="addBy">
												<bean:write name="note" property="addBy"/>
											</logic:present>
										</td>
									</tr>
								        </logic:iterate>
								    </logic:present>
								</table>
							</td>
						</tr>
						
						<tr><td></td></tr>
						
						<tr align="center">
						    <td>
								<table width="100%" border="0" cellpadding="2" cellspacing="1">
								<tr>
							        <td class="customerltbkground" vAlign="top" align="center" colSpan="4">
							            <span class="shopassetdetailtxt">
							                <b><app:storeMessage key="userWorkOrder.text.assocDocs"/></b>
							                <logic:greaterThan name="woid" value="0">
							                    <% if(editingAuthorized || isSendToProviderStatus) { %>
							                    <html:button property="action" styleClass="store_fb"onclick="actionSubmitTb(0,'createContent','t_userWorkOrderContentDetail','f_userSecondaryToolbar');">
							                        <app:storeMessage key="global.label.addContent"/>
							                    </html:button>
							                    <%}%>
							                </logic:greaterThan>
							            </span>
							        </td>
							    </tr>

							    <tr>
							        <td class="shopcharthead" align="center">
							            <div class="fivemargin">
							                <app:storeMessage key="userWorkOrder.text.assocDocs.description"/>
							            </div>
							        </td>

							        <td class="shopcharthead" align="center">
							            <div class="fivemargin">
							                <app:storeMessage key="userWorkOrder.text.assocDocs.fileName"/>
							            </div>
							        </td>

							        <td class="shopcharthead" align="center">
							            <div class="fivemargin">
							                <app:storeMessage key="userWorkOrder.text.assocDocs.addDate"/>
							            </div>
							        </td>

							        <td class="shopcharthead" align="center">
							            <div class="fivemargin">
							                <app:storeMessage key="userWorkOrder.text.assocDocs.addBy"/>
							            </div>
							        </td>
							    </tr>
							    <logic:present name="workorderdet" property="contents">
							        <logic:iterate id="contentV" name="workorderdet" property="contents"
							                       type="com.cleanwise.service.api.value.WorkOrderContentView" indexId="j">
							            <logic:present name="contentV" property="content">
							                <logic:present name="contentV" property="workOrderContentData">
							                    <bean:define id="woicId" name="contentV"
							                                 property="workOrderContentData.workOrderContentId"/>
								<tr id="docs<%=((Integer) j).intValue()%>">
									<td>
										<logic:present name="contentV" property="content.shortDesc">
											<a href="../userportal/userWorkOrderContent.do?action=contentDetail&workOrderContentId=<%=woicId%>&display=t_userWorkOrderContentDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar">
												<bean:write name="contentV" property="content.shortDesc"/> </a>
										</logic:present>

									</td>
									<td>
									<%
										String fileName = "";
										if (contentV.getContent().getPath() != null) {
											fileName = contentV.getContent().getPath();
											if (fileName.indexOf("/") > -1) {
												fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
											}
										}
									%>
										<%String docLoc = "../userportal/userWorkOrderContent.do?action=readDoc&workOrderContentId=" + woicId + "&display=t_userWorkOrderContentDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar";%>
										<a href="#" onclick="return viewPrinterFriendly('<%=docLoc%>');">
											<%=fileName%> </a>
									</td>
									<td>
										<logic:present name="contentV" property="content.addDate">
											<%=ClwI18nUtil.formatDate(request, contentV.getContent().getAddDate(), DateFormat.DEFAULT)%>
										</logic:present>
									</td>
									<td>
										<logic:present name="contentV" property="content.addBy">
											<bean:write name="contentV" property="content.addBy"/>
										</logic:present>
									</td>
								</tr>
							                </logic:present>
							            </logic:present>
							        </logic:iterate>
							    </logic:present>
								</table>
							</td>
						</tr>
						
						<tr><td></td></tr>
						
						<tr align="center">
						    <td>
								<table width="100%" border="0" cellpadding="2" cellspacing="1">
								<tr>
							        <td class="customerltbkground" vAlign="top" align="center" colSpan="3">
										<span class="shopassetdetailtxt">
											<B><app:storeMessage key="userWorkOrder.text.workOrderStatusHistory"/></B>
										</span>
									</td>
							    </tr>

							    <tr>
							        <td class="shopcharthead" align="center">
							            <div class="fivemargin">
							                <app:storeMessage key="userWorkOrder.text.workOrderStatusHistory.statusDate"/>
							            </div>
							        </td>
							        <td class="shopcharthead" align="center">
							            <div class="fivemargin">
							                <app:storeMessage key="userWorkOrder.text.workOrderStatusHistory.statusCode"/>
							            </div>
							        </td>
                                    <td class="shopcharthead" align="center">
							            <div class="fivemargin">
							                <app:storeMessage key="userWorkOrder.text.workOrderStatusHistory.userName"/>
							            </div>
							        </td>
							    </tr>

							    <logic:present name="workorderdet" property="statusHistory">
							        <logic:iterate id="statusH" name="workorderdet" property="statusHistory"
							                       type="com.cleanwise.service.api.value.WorkOrderStatusHistData" indexId="j">
								<tr id="statusHistory<%=((Integer)j).intValue()%>">
									<td align="center">
										<%=ClwI18nUtil.formatDate(request, statusH.getStatusDate(), DateFormat.DEFAULT)%>
									</td>
									<td align="center">
										<%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.status."+((String)statusH.getStatusCd()).toUpperCase());
											if(messageKey!=null){%>
										<%=messageKey%>
										<%} else{%>
										<%=statusH.getStatusCd()%>
										<%}%>
									</td>
                                    <td align="center">
                                        <%=statusH.getModBy()%>
                                    </td>
								</tr>
							        </logic:iterate>
							    </logic:present>
								</table>
							</td>
						</tr>
						
						</table>
					</td>
					<!-- end right column 2 -->
					
				</tr>
				
				</table>
				<!-- end of second column group-->
				
			</td>
		</tr>
				
		<tr><td colspan="3">&nbsp;</td></tr>
		
		
		
		<tr align="center">
			<td colspan="3">
				
				<!-- begin of itemized service part -->
				<table width="100%" border="0" cellpadding="2" cellspacing="1">
				
				<tr>
					<td>
						<span class="shopassetdetailtxt">
							<b><app:storeMessage key="userWorkOrder.text.ItemizedService"/>:</b>
						</span>
					</td>				
				</tr>			
				
				<tr><td></td></tr>
				
				<tr align="center">
					<td>
						<table id='itemizedServiceTable' width="100%"  cellpadding="1" cellspacing="1" border="0" cols="13" style="border:#D3D3D3 1px solid">
						
						<tr align="center" class="itemizedservicecharthead">
							<td>
                                <div class="fivemargin">
                                    <app:storeMessage key="userWorkOrder.text.LineNumber"/>
                                </div>
							</td>
							<td>
                                <app:storeMessage key="userWorkOrder.text.BilledService"/>
							</td>
							<td>
                                <app:storeMessage key="userWorkOrder.text.workOrderItems.warranty"/>
							</td>
							<td>
                                <app:storeMessage key="userWorkOrder.text.PMContract"/>
							</td>
							<td>
                                <app:storeMessage key="userWorkOrder.text.Quantity"/>
							</td>
							<td>
                                <app:storeMessage key="userWorkOrder.text.PartNumber"/>
							</td>
							<td>
                                <app:storeMessage key="userWorkOrder.text.Description"/>
							</td>
							<td>
                                <app:storeMessage key="userWorkOrder.text.PriceEach"/>
                            </td>
							<td>
                                <app:storeMessage key="userWorkOrder.text.PartsPriceExtended"/>
							</td>
							<td>
                                <app:storeMessage key="userWorkOrder.text.Labor"/>
							</td>
							<td>
                                <app:storeMessage key="userWorkOrder.text.Travel"/>
							</td>
							<td>
                                <app:storeMessage key="userWorkOrder.text.note.addDate"/>
							</td>
							<td>
                                <app:storeMessage key="global.action.label.delete"/>
							</td>
						</tr>
                        
                        <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemizedService">
                        <logic:greaterThan name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemizedServiceTableActiveCount" value="0">
                        <% int j = 0;
                           int dataLineCount = theForm.getItemizedServiceTableDataLineCount();
                           String bgColor = "#F0F0F0";
                           int previousLineNum = 0;
                           int currentLineNum = 0;
                           WorkOrderDetailData previousLine = null;
                           
                           String paymentTypeServiceButtonColor = bgColor;
                           String paymentTypeWarrantyButtonColor = bgColor;
                           String paymentTypeContractButtonColor = bgColor;
                           String quantityFieldColor = bgColor;
                           String partNumberFieldColor = bgColor;
                           String shortDescrFieldColor = bgColor;
                           String partPriceFieldColor = bgColor;
                           String laborFieldColor = bgColor;
                           String travelFieldColor = bgColor;
                           String commentsFieldColor = bgColor;
                        %>
                        <logic:iterate  id="itemizedServiceLine"
                                        name="USER_WORK_ORDER_DETAIL_MGR_FORM"
                                        property="itemizedService"
                                        indexId="ii"
                                        type="com.cleanwise.service.api.value.WorkOrderDetailData">
                            <%
                                String paymentTypeCd = "paymentTypeCd["+ii+"]";
                                String quantity = "quantity["+ii+"]";
                                String partNumber = "partNumber["+ii+"]";
                                String shortDescr = "shortDescr["+ii+"]";
                                String partPrice = "partPrice["+ii+"]";
                                String labor = "labor["+ii+"]";
                                String travel = "travel["+ii+"]";
                                String comments = "comments["+ii+"]";
                                String delete = "delete["+ii+"]";
                                currentLineNum = itemizedServiceLine.getLineNum();
                                
                                if (previousLineNum != currentLineNum) {
                                    if ((j % 2) == 0) {
                                        bgColor = "#F0F0F0";
                                    } else {
                                        bgColor = "#D8D8D8";
                                    }
                                    j++;
                                    
                                    paymentTypeServiceButtonColor = bgColor;
                                    paymentTypeWarrantyButtonColor = bgColor;
                                    paymentTypeContractButtonColor = bgColor;
                                    quantityFieldColor = bgColor;
                                    partNumberFieldColor = bgColor;
                                    shortDescrFieldColor = bgColor;
                                    partPriceFieldColor = bgColor;
                                    laborFieldColor = bgColor;
                                    travelFieldColor = bgColor;
                                    commentsFieldColor = bgColor;
                                } else {
                                    if (previousLine != null) {
                                        if (itemizedServiceLine.getPaymentTypeCd().equals(RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE) &&
                                            !itemizedServiceLine.getPaymentTypeCd().equals(previousLine.getPaymentTypeCd())) {
                                            paymentTypeServiceButtonColor = "yellow";
                                        } else {
                                            paymentTypeServiceButtonColor = bgColor;
                                        }
                                        if (itemizedServiceLine.getPaymentTypeCd().equals(RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.WARRANTY) &&
                                            !itemizedServiceLine.getPaymentTypeCd().equals(previousLine.getPaymentTypeCd())) {
                                            paymentTypeWarrantyButtonColor = "yellow";
                                        } else {
                                            paymentTypeWarrantyButtonColor = bgColor;
                                        }
                                        if (itemizedServiceLine.getPaymentTypeCd().equals(RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.PM_CONTRACT) &&
                                            !itemizedServiceLine.getPaymentTypeCd().equals(previousLine.getPaymentTypeCd())) {
                                            paymentTypeContractButtonColor = "yellow";
                                        } else {
                                            paymentTypeContractButtonColor = bgColor;
                                        }
                                        if (itemizedServiceLine.getQuantity() != previousLine.getQuantity()) {
                                            quantityFieldColor = "yellow";
                                        } else {
                                            quantityFieldColor = bgColor;
                                        }
                                        if (!Utility.isEqual(itemizedServiceLine.getPartNumber(),previousLine.getPartNumber())) {
                                            partNumberFieldColor = "yellow";
                                        } else {
                                            partNumberFieldColor = bgColor;
                                        }
                                        if (!Utility.isEqual(itemizedServiceLine.getShortDesc(),previousLine.getShortDesc())) {
                                            shortDescrFieldColor = "yellow";
                                        } else {
                                            shortDescrFieldColor = bgColor;
                                        }
                                        if (itemizedServiceLine.getPartPrice().compareTo(previousLine.getPartPrice()) != 0) {
                                            partPriceFieldColor = "yellow";
                                        } else {
                                            partPriceFieldColor = bgColor;
                                        }
                                        if (itemizedServiceLine.getLabor().compareTo(previousLine.getLabor()) != 0) {
                                            laborFieldColor = "yellow";
                                        } else {
                                            laborFieldColor = bgColor;
                                        }
                                        if (itemizedServiceLine.getTravel().compareTo(previousLine.getTravel()) != 0) {
                                            travelFieldColor = "yellow";
                                        } else {
                                            travelFieldColor = bgColor;
                                        }
                                        if (!Utility.isEqual(itemizedServiceLine.getComments(),previousLine.getComments())) {
                                            commentsFieldColor = "yellow";
                                        } else {
                                            commentsFieldColor = bgColor;
                                        }
                                    }
                                }

                                if (RefCodeNames.STATUS_CD.ACTIVE.equals(itemizedServiceLine.getStatusCd())) { %>
                                    <tr align="center" style="background-color: <%=bgColor%>">
                                        <td>
                                            <bean:write name="itemizedServiceLine" property="lineNum"/>
                                        </td>
                                        <td>
                                            <html:radio property="<%=paymentTypeCd%>" value="<%=RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE%>" disabled="<%=!(editingAuthorized || isSendToProviderStatus)%>"/>
                                        </td>
                                        <td>
                                            <html:radio property="<%=paymentTypeCd%>" value="<%=RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.WARRANTY%>" disabled="<%=!(editingAuthorized || isSendToProviderStatus)%>"/>
                                        </td>
                                        <td>
                                            <html:radio property="<%=paymentTypeCd%>" value="<%=RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.PM_CONTRACT%>" disabled="<%=!(editingAuthorized || isSendToProviderStatus)%>"/>
                                        </td>
                                        
                                        <td>
                                            <% if (editingAuthorized || isSendToProviderStatus) { %>
                                                <html:text size="1" maxlength="32" property="<%=quantity%>"/>
                                            <% } else { %>
                                                <bean:write name="itemizedServiceLine" property="quantity"/>
                                            <% } %>
                                        </td>
                                        <td>
                                            <% if (editingAuthorized || isSendToProviderStatus) { %>
                                                <html:text size="4" maxlength="32" property="<%=partNumber%>"/>
                                            <% } else { %>
                                                <bean:write name="itemizedServiceLine" property="partNumber"/>
                                            <% } %>
                                        </td>
                                        <td>
                                            <% if (editingAuthorized || isSendToProviderStatus) { %>
                                                <html:text size="26" maxlength="255" property="<%=shortDescr%>"/>
                                            <% } else { %>
                                                <bean:write name="itemizedServiceLine" property="shortDesc"/>
                                            <% } %>
                                        </td>
                                        <td>
                                            <% if (editingAuthorized || isSendToProviderStatus) { %>
                                                <html:text size="2" maxlength="32" property="<%=partPrice%>"/>
                                            <% } else { %>
                                                <bean:write name="itemizedServiceLine" format="0.00" property="partPrice"/>
                                            <% } %>
                                        </td>
                                        <td>
                                            <%=itemizedServiceLine.getPartPrice().multiply(BigDecimal.valueOf(itemizedServiceLine.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_UP).toString()%>
                                        </td>
                                        <td>
                                            <% if (editingAuthorized || isSendToProviderStatus) { %>
                                                <html:text size="2" maxlength="32" property="<%=labor%>"/>
                                            <% } else { %>
                                                <bean:write name="itemizedServiceLine" format="0.00" property="labor"/>
                                            <% } %>
                                        </td>
                                        <td>
                                            <% if (editingAuthorized || isSendToProviderStatus) { %>
                                                <html:text size="2" maxlength="32" property="<%=travel%>"/>
                                            <% } else { %>
                                                <bean:write name="itemizedServiceLine" format="0.00" property="travel"/>
                                            <% } %>
                                        </td>
                                        <td>
                                            <%=sdFormat.format(itemizedServiceLine.getAddDate())%>
                                        </td>
                                        <td>
                                            <% if (editingAuthorized || isSendToProviderStatus) { %>
                                                <html:checkbox property="<%=delete%>" value="<%=RefCodeNames.STATUS_CD.INACTIVE%>"/>
                                            <% } %>
                                        </td>
                                    </tr>
                                    
                                    <tr style="background-color: <%=bgColor%>">
                                        <td colspan="13" align="left">
                                            <div style="margin-left: 10px;">
                                                <b><app:storeMessage key="userWorkOrder.text.Comments"/>&nbsp;:</b>&nbsp;
                                                <% if (editingAuthorized || isSendToProviderStatus) { %>
                                                    <html:text size="106" maxlength="255" property="<%=comments%>"/>
                                                <% } else { %>
                                                    <bean:write name="itemizedServiceLine" property="comments"/>
                                                <% } %>
                                            </div>
                                        </td>
                                    </tr>
                                <% } else { %>
                                    <tr align="center"  style="background-color: <%=bgColor%>">
                                        <td>
                                            <% if (previousLineNum != currentLineNum) {%>
                                                <bean:write name="itemizedServiceLine" property="lineNum"/>
                                            <% } else { %>
                                                &nbsp;
                                            <% }%>
                                        </td>
                                        <td style="background-color: <%=paymentTypeServiceButtonColor%>">
                                            <logic:equal name="itemizedServiceLine" property="paymentTypeCd" value="<%=RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE%>">
                                                X
                                            </logic:equal>
                                        </td>
                                        <td style="background-color: <%=paymentTypeWarrantyButtonColor%>">
                                            <logic:equal name="itemizedServiceLine" property="paymentTypeCd" value="<%=RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.WARRANTY%>">
                                                X
                                            </logic:equal>
                                        </td>
                                        <td style="background-color: <%=paymentTypeContractButtonColor%>">
                                            <logic:equal name="itemizedServiceLine" property="paymentTypeCd" value="<%=RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.PM_CONTRACT%>">
                                                X
                                            </logic:equal>
                                        </td>
                                        <td style="background-color: <%=quantityFieldColor%>">
                                            <bean:write name="itemizedServiceLine" property="quantity"/>
                                        </td>
                                        <td style="background-color: <%=partNumberFieldColor%>">
                                            <bean:write name="itemizedServiceLine" property="partNumber"/>
                                        </td>
                                        <td style="background-color: <%=shortDescrFieldColor%>" align="left">
                                            <bean:write name="itemizedServiceLine" property="shortDesc"/>
                                        </td>
                                        <td style="background-color: <%=partPriceFieldColor%>">
                                            <bean:write name="itemizedServiceLine" format="0.00" property="partPrice"/>
                                        </td>
                                        <td>
                                            <%=itemizedServiceLine.getPartPrice().multiply(BigDecimal.valueOf(itemizedServiceLine.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_UP).toString()%>
                                        </td>
                                        <td style="background-color: <%=laborFieldColor%>">
                                            <bean:write name="itemizedServiceLine" format="0.00" property="labor"/>
                                        </td>
                                        <td style="background-color: <%=travelFieldColor%>">
                                            <bean:write name="itemizedServiceLine" format="0.00" property="travel"/>
                                        </td>
                                        <td colspan="2">
                                            <%=sdFormat.format(itemizedServiceLine.getAddDate())%>
                                        </td>
                                    </tr>
                                    
                                    <tr id="historyCommentsLine<%=j%>" style="background-color: <%=bgColor%>">
                                        <td colspan="11" align="left" style="background-color: <%=commentsFieldColor%>">
                                            <div style="margin-left: 10px;">
                                                <b><app:storeMessage key="userWorkOrder.text.Comments"/>&nbsp;:</b>&nbsp;
                                                <bean:write name="itemizedServiceLine" property="comments"/>
                                            </div>
                                        </td>
                                        <td colspan="2" align="center">
                                            <bean:write name="itemizedServiceLine" property="modBy"/>
                                        </td>
                                    </tr>
                                <% } 
                                    previousLineNum = currentLineNum;
                                    previousLine = itemizedServiceLine;
                                %>
                        </logic:iterate>
                        </logic:greaterThan>
                        </logic:present>

						</table>
					</td>
				</tr>
                
                <% if(editingAuthorized || isSendToProviderStatus) { %>
                <tr align="left">
					<td>
                        <html:button property="action" styleClass="store_fb" onclick="actionSubmitTb(0,'addItemizedServiceTableLines','t_userWorkOrderDetail','f_userSecondaryToolbar');">
                            <app:storeMessage key="userWorkOrder.text.AddLines"/>
                        </html:button> 
					</td>				
				</tr>	
                <% } %>
				</table>
				<!-- end of itemized service part -->
			</td>
		</tr>
		
		<tr><td colspan="3">&nbsp;</td></tr>
		
		<tr>
			<td colspan="3">
				
				<!-- begin of scheduler part -->
				<table width="100%" border="0" cellpadding="2" cellspacing="1">
				<tr>
					<td align="left">
                        <% if(editingAuthorized) { %>
						<span class="shopassetdetailtxt">
							<b><app:storeMessage key="userWorkOrder.text.schedule"/>:&nbsp;&nbsp;</b>
						</span>
						<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="schedules">
							<bean:size id="schCount" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="schedules"/>
							<logic:greaterThan name="schCount" value="0">
								<logic:iterate id="schedule"
											   name="USER_WORK_ORDER_DETAIL_MGR_FORM"
											   property="schedules"
											   type="com.cleanwise.service.api.value.OrderScheduleData"
											   indexId="si">
								<% String ss = "";
									ss += "( ";
									Date dd = schedule.getEffDate();
									ss += ClwI18nUtil.formatDateInp(request, dd);
									ss += "    -   ";
									dd = schedule.getExpDate();
									if (dd == null) {
										ss += " ... ";
									} else {
										ss += ClwI18nUtil.formatDateInp(request, dd);
									}
									ss += " )";%>
							   <a href="../userportal/userWorkOrderScheduler.do?action=detail&display=t_userWorkOrderScheduler&scheduleId=<%=schedule.getOrderScheduleId()%>"><%=ss%></a>
									<logic:greaterThan name="schCount" value="1">
										<logic:lessThan name="schCount" value="<%=si.toString()%>" >
											,
										</logic:lessThan>
									</logic:greaterThan>
							</logic:iterate>
						</logic:greaterThan>
						<logic:lessThan name="schCount" value="1">
							<a href="../userportal/userWorkOrderScheduler.do?action=init&display=t_userWorkOrderScheduler">
								<app:storeMessage key="global.action.label.create"/>
							</a>
						</logic:lessThan>
						</logic:present>
						<%}%>
					</td>
				</tr>
				</table>
				<!-- end of scheduler part -->
			</td>
		</tr>
		
		<tr><td colspan="3">&nbsp;</td></tr>
		
		</table>
	</td>
	
	<td width="1%">&nbsp;</td>

</TR>

<TR><td colspan="5">&nbsp;</td></TR>

<!-- buttons -->

<%
boolean saveButtonIsShown = false;
%>
<TR>
    <td colspan="5" align="center">
        <table>
            <tr>
                <td align="left" class="fivemargin">
                    <% if (RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST.equals(workOrderStatus) ||
                           RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER.equals(workOrderStatus) ||
                           RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER.equals(workOrderStatus) ||
                           isWorkOrderApprover) { 
                           saveButtonIsShown = true;
                    %>
                        <html:button property="action" styleClass="store_fb" onclick="actionSubmit('0','save');">
                            <app:storeMessage key="global.label.save"/>
                        </html:button>
                    <% } %>
                </td>
                <logic:greaterThan name="woid" value="0">
                <%
                    if (!RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(workOrderStatus) &&
                        !RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED.equals(workOrderStatus)) {
                %>
                <td align="center" class="fivemargin">
                    <%
                    if (saveButtonIsShown) {
                    %>
                        &nbsp;
                        &nbsp;
                        &nbsp;
                        &nbsp;
                        &nbsp;
                        &nbsp;
                    <%
                    }
                    %>
                    <input type="radio" name="sendType" value="<%=acceptMessage%>" checked />
                    <span style="vertical-align: 30%; text-align: center;">
                        <b><app:storeMessage key="shop.orderStatus.text.accept"/></b>
                    </span>
                </td>
                <td align="center" class="fivemargin">
                    <input type="radio" name="sendType" value="<%=rejectMessage%>" />
                    <span style="vertical-align: 30%; text-align: center;">
                        <b><app:storeMessage key="global.action.label.reject"/></b>
                    </span>
                </td>
                <td align="right" class="fivemargin">
                    <html:button property="action"
                                 styleClass="store_fb"
                                 onclick="submitSend();">
                        <app:storeMessage key="global.action.label.send"/>
                    </html:button>
                </td>
                <% } %>
                </logic:greaterThan>
            </tr>            
        </table>
    </td>
</TR>

<html:hidden property="workOrderId" value="<%=((Integer)woid).toString()%>"/>

</logic:present>
</logic:present>

<html:hidden property="action" value="hiddenAction"/>
<html:hidden property="tabs" value="f_userWorkOrderToolbar"/>
<html:hidden property="display" value="t_userWorkOrderDetail"/>
<html:hidden property="secondaryToolbar" value="f_userSecondaryToolbar"/>


</html:form>
</table>
