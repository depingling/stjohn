<%--
  Date: 15.10.2007
  Time: 18:27:44
--%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.AssetData" %>
<%@ page import="com.cleanwise.service.api.value.AssetDataVector" %>
<%@ page import="com.cleanwise.service.api.value.WarrantyData" %>
<%@ page import="com.cleanwise.view.forms.UserWorkOrderItemMgrForm" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<script language="JavaScript1.2">
    <!--
function actionSubmit(formNum, action) {
 var actions;
 actions=document.forms[formNum]["action"];
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='hiddenAction') {
     actions[ii].value=action;
     document.forms[formNum].submit();
     break;
   }
 }
 return false;
 }

 function viewPrinterFriendly(loc) {
 var prtwin = window.open(loc,"view_docs",
     "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
   prtwin.focus();
   return false;
 }

 function actionSubmitTb(formNum, action,fwdPage,sbar) {

 var actions;
 var disp
 var secBar;

 actions = document.forms[formNum]["action"];
 disp    = document.forms[formNum]["display"];
 secBar  = document.forms[formNum]["secondaryToolbar"];

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
-->
</script>

<script language="JavaScript" src="../externals/ajaxutil.js"></script>
<script language="JavaScript" src="../externals/workOrderUtil.js"></script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="USER_WORK_ORDER_ITEM_MGR_FORM"  type="com.cleanwise.view.forms.UserWorkOrderItemMgrForm"/>
<bean:define id="detForm" name="USER_WORK_ORDER_DETAIL_MGR_FORM"  type="com.cleanwise.view.forms.UserWorkOrderDetailMgrForm"/>

<html:form name="USER_WORK_ORDER_ITEM_MGR_FORM" action="/userportal/userWorkOrderItem.do" scope="session">

<logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="workOrderItemDetail">
<logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="workOrderItemDetail.workOrderItem">

<bean:define id="workOrderItem" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="workOrderItemDetail.workOrderItem"/>
<bean:define id="woiId" name="workOrderItem" property="workOrderItemId"/>
<bean:define id="woId" name="workOrderItem" property="workOrderId"/>

<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);%>

<% String mainTablePercent = "60%";%>
<logic:lessThan name="woiId" value="1">
    <% mainTablePercent = "98%";%>
</logic:lessThan>

<%
    String workOrderStatus = detForm.getWorkOrderDetail().getWorkOrder().getStatusCd();
    boolean editingAuthorized = appUser.canEditWorkOrder(workOrderStatus);
    boolean isSendToProviderStatus = RefCodeNames.WORK_ORDER_STATUS_CD.SEND_TO_PROVIDER.equals(workOrderStatus);
%>

<tr><td>&nbsp;</td>
<tr>
<td width="1%">&nbsp;</td>

<td width="<%=mainTablePercent%>" valign="top">

<table width="100%" cellpadding=1 cellspacing=5 border="0">

<tr>
    <td colspan="4">
        <logic:greaterThan name="woid" value="0">
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
                <TD class=customerltbkground vAlign=top align=center colSpan=3>
                    <SPAN class=shopassetdetailtxt><B>
                        <app:storeMessage key="userWorkOrder.text.note"/>
                    </b>
                    </span></td>
            </tr>
            <tr>
                <td class="shopcharthead" align="center">
                      <span class="fivemargin">
                        <b>
                            <app:storeMessage key="userWorkOrder.text.note.description"/>
                        </b>
                      </span>
                </td>
                <td class="shopcharthead" align="center">
                      <span class="fivemargin">
                        <b>
                            <app:storeMessage key="userWorkOrder.text.note.addDate"/>
                        </b>
                      </span>
                </td>
                <td class="shopcharthead" align="center">
                        <span class="fivemargin">
                           <b>
                               <app:storeMessage key="userWorkOrder.text.note.addBy"/>
                           </b>
                        </span>
                </td>
            </tr>
            <logic:present name="workorderdet" property="notes">
                <logic:iterate id="note" name="workorderdet" property="notes"
                               type="com.cleanwise.service.api.value.WorkOrderNoteData" indexId="j">
                    <bean:define id="woiId" name="note" property="workOrderNoteId"/>
                    <tr id="note<%=((Integer)j).intValue()%>">
                        <td width="3%">&nbsp;</td>
                        <td>&nbsp;
                            <logic:present name="note" property="shortDesc">
                                <bean:write name="note" property="shortDesc"/>
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

</logic:greaterThan>


<tr>
    <td width="30%">
      <span class="shopassetdetailtxt">
         <b><app:storeMessage key="userWorkOrder.text.workOrderItems.number"/>:</b>
     </span>
    </td>
    <td width="20%">
        <bean:write name="workOrderItem" property="workOrderItemId"/>
    </td>
    <td width="30%"></td>
    <td  width="20%"></td>
</tr>
<logic:equal name="USER_WORK_ORDER_ITEM_MGR_FORM"
             property="workOrder.typeCd"
             value="<%=RefCodeNames.WORK_ORDER_TYPE_CD.EQUIPMENT%>">
<tr>
    <td>
        <span class="shopassetdetailtxt">
         <b><app:storeMessage key="userWorkOrder.text.workOrderItems.assets"/>:</b>
        </span>
    </td>
    <td colspan="3">
        <%if(editingAuthorized) {%>
        <html:select name="USER_WORK_ORDER_ITEM_MGR_FORM" property="activeAssetIdStr"
                     onchange="ajaxconnect('userWorkOrderItem.do', 'action=changeActiveAsset&newActiveAssetId='+this.value, '', woiDynamicBoxes.populateAndReDraw)">
            <html:option value="">
                <app:storeMessage  key="admin.select"/>
            </html:option>
            <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="assetGroups">
                <logic:iterate id="assetGroup"
                               name="USER_WORK_ORDER_ITEM_MGR_FORM"
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
                    }   %>
                </logic:iterate>
            </logic:present>
        </html:select>
        <%} else {%>
        <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="activeAsset">
            <bean:write name="USER_WORK_ORDER_ITEM_MGR_FORM" property="activeAsset.shortDesc"/>
        </logic:present>
        <%}%>
    </td>
</tr>
<tr>
	<td width="32%">
		<span class="shopassetdetailtxt">
			<b><app:storeMessage key="userAssets.shop.text.param.modelNumber:"/></b>
		</span>
	</td>
	<td colspan="3">
		<div id="assetModelNumberValue"></div>
	</td>
</tr>

<tr>
	<td width="32%">
		<span class="shopassetdetailtxt">
			<b><app:storeMessage key="userAssets.shop.text.param.serialnumber:"/></b>
		</span>
	</td>
	<td colspan="3">
		<div id="assetModelSerialNumber"></div>
	</td>
</tr>


<tr>
    <td>
        <span class="shopassetdetailtxt">
        <b><app:storeMessage key="userWorkOrder.text.workOrderItems.warranty"/>:</b>
       </span>
    </td>
    <td colspan="3">
        <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="activeWarrantyIdStr">
            <div id="warrantyDynamicBox">
				<select name="activeWarrantyIdStr"></select>
			</div>
        </logic:present>
    </td>
</tr>

<script language="JavaScript1.2"><!--

<%
    String modelNumberVal   = "";
    String serialNumberVal  = "";

    if(Utility.isSet(((UserWorkOrderItemMgrForm)theForm).getActiveAssetIdStr())){
        String serial    =((UserWorkOrderItemMgrForm)theForm).getActiveAsset().getSerialNum();
        String model     = ((UserWorkOrderItemMgrForm)theForm).getActiveAsset().getModelNumber();
        modelNumberVal   = Utility.strNN(model);
        serialNumberVal  = Utility.strNN(serial);
    }
%>
var warrantyArray;
var warrantyIdx=0;
<%if(((UserWorkOrderItemMgrForm)theForm).getWarrantyForActiveAsset()!=null
&& !((UserWorkOrderItemMgrForm)theForm).getWarrantyForActiveAsset().isEmpty()){  %>
warrantyArray = new Array();
<% for(int i=0;i<((UserWorkOrderItemMgrForm)theForm).getWarrantyForActiveAsset().size();i++){%>
warrantyArray[warrantyIdx] = new Array();
warrantyArray[warrantyIdx][0] ='<%=((WarrantyData)((UserWorkOrderItemMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getWarrantyId()%>';
warrantyArray[warrantyIdx][1] ='<%=((WarrantyData)((UserWorkOrderItemMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getShortDesc()%>';
warrantyIdx++;
<%}%>
<%}%>

function warrantyDinamicBoxInit(selectElementName, warrantyArray, currentVal, editingAuthorized) {
	warrantyDynamicBox.init(selectElementName, warrantyArray, currentVal, editingAuthorized);
}

warrantyDinamicBoxInit('activeWarrantyIdStr', warrantyArray, '<%=((UserWorkOrderItemMgrForm)theForm).getActiveWarrantyIdStr()%>', '<%=String.valueOf(editingAuthorized)%>');

function writeAssetDynamicBox(modelFieldId, modelFieldValue, serialNumFieldId, serialNumFieldValue) {
	assetDynamicBox.init(modelFieldId, modelFieldValue, serialNumFieldId, serialNumFieldValue); 
	assetDynamicBox.redraw();
}

writeAssetDynamicBox('assetModelNumberValue', '<%=modelNumberVal%>', 'assetModelSerialNumber', '<%=serialNumberVal%>');
//-->
</script>
</logic:equal>

<tr>
    <td><span class="shopassetdetailtxt"><b>
        <app:storeMessage key="userWorkOrder.text.workOrderItems.description"/>:
    </b></span></td>
    <td colspan="3">
        <%if(editingAuthorized) {%>
        <html:text size="45" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="shortDesc"/>
        <%} else {%>
        <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="shortDesc">
            <bean:write name="USER_WORK_ORDER_ITEM_MGR_FORM" property="shortDesc"/>
        </logic:present>
        <%}%>
    </td>
</tr>
<tr>
    <td colspan="4" valign="top">
         <span class="shopassetdetailtxt">
         <b>
             <app:storeMessage key="userWorkOrder.text.longDesc"/>:
         </b>
         </span>
    </td>
</tr>
<tr>
    <td width="98%" colspan="4" align="center">
        <%if(editingAuthorized) {%>
        <html:textarea rows="7" cols="52" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="longDesc"/>
        <%} else {%>
        <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="longDesc">
            <bean:write name="USER_WORK_ORDER_ITEM_MGR_FORM" property="longDesc"/>
        </logic:present>
        <%}%>
    </td>
</tr>
<tr><td colspan="4">
    <table>
        <tr>
            <td width="30%"><span class="shopassetdetailtxt"><b>
                <app:storeMessage key="userWorkOrder.text.quotedLabor"/>:
            </b></span></td>
            <td width="20%">
                <%if(editingAuthorized) {%>
                <html:text size="8" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="estimatedLabor"/>
                <%} else {%>
                <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="estimatedLabor">
                    <bean:define id="estimatedLabor" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="estimatedLabor" type="java.lang.String"/>
                    <% if (Utility.isSet(estimatedLabor)) { %>
                    <%=ClwI18nUtil.getPriceShopping(request,estimatedLabor)%>
                    <%}%>
                </logic:present>
                <%}%>
            </td>
            <td width="30%"><span class="shopassetdetailtxt"><b>
                <app:storeMessage key="userWorkOrder.text.quotedPartsCost"/>:
            </b></span></td>
            <td width="20%">
                <%if(editingAuthorized) {%>
                <html:text size="8" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="estimatedPart"/>
                <%} else {%>
                <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="estimatedPart">
                    <bean:define id="estimatedPart" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="estimatedPart" type="java.lang.String"/>
                    <% if (Utility.isSet(estimatedPart)) { %>
                    <%=ClwI18nUtil.getPriceShopping(request,estimatedPart)%>
                    <%}%>
                </logic:present>
                <%}%>
            </td>
        </tr>
        <logic:greaterThan name="workOrderItem" property="workOrderItemId" value="0">
            <tr>
                <td></td>
                <td></td>
                <td><span class="shopassetdetailtxt"><b>
                    <app:storeMessage key="userWorkOrder.text.workOrderItems.estimateTotalCost"/>:
                </b></span></td>
                <td>
                    <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="estimateTotalCost">
                        <bean:define id="estimatedTotalCost" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="estimateTotalCost" type="java.lang.String"/>
                        <%if (Utility.isSet(estimatedTotalCost)) { %>
                        <%=ClwI18nUtil.getPriceShopping(request,estimatedTotalCost)%>
                        <%}%>
                    </logic:present>
                </td>
            </tr>
        </logic:greaterThan>
        <tr>
            <td><span class="shopassetdetailtxt"><b>
                <app:storeMessage key="userWorkOrder.text.workOrderItems.actualLabor"/>
                :
            </b></span></td>
            <td>
                <%if (editingAuthorized || isSendToProviderStatus) {%>
                <html:text size="8" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="actualLabor"/>
                <%} else {%>
                <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="actualLabor">
                    <bean:define id="actualLabor" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="actualLabor"
                                 type="java.lang.String"/>
                    <% if (Utility.isSet(actualLabor)) { %>
                        <%=ClwI18nUtil.getPriceShopping(request, actualLabor)%>
                    <% } %>
                </logic:present>
                <%}%>
            </td>
            <td><span class="shopassetdetailtxt"><b>
                <app:storeMessage key="userWorkOrder.text.workOrderItems.actualPart"/>:
            </b></span></td>
            <td>
                <%if (editingAuthorized || isSendToProviderStatus) {%>
                    <html:text size="8" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="actualPart"/>
                <%} else {%>
                <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="actualPart">
                    <bean:define id="actualPart" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="actualPart"
                                 type="java.lang.String"/>
                    <% if (Utility.isSet(actualPart)) { %>
                        <%=ClwI18nUtil.getPriceShopping(request, actualPart)%>
                    <% } %>
                </logic:present>
                <%}%>
            </td>
        </tr>
        <logic:greaterThan name="workOrderItem" property="workOrderItemId" value="0">
            <tr>
                <td></td>
                <td></td>
                <td><span class="shopassetdetailtxt"><b>
                    <app:storeMessage key="userWorkOrder.text.workOrderItems.actualTotalCost"/>:
                </b></span></td>
                <td>
                    <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="actualTotalCost">
                        <bean:define id="actualTotalCost" name="USER_WORK_ORDER_ITEM_MGR_FORM" property="actualTotalCost" type="java.lang.String"/>
                        <%if (Utility.isSet(actualTotalCost)) { %>
                        <%=ClwI18nUtil.getPriceShopping(request,actualTotalCost)%>
                        <%}%>
                    </logic:present>
                </td>
            </tr> </logic:greaterThan>

    </table>
</td></tr>

</table>

</td>

<td width="1%">&nbsp;</td>

<logic:greaterThan name="woiId" value="0">
<td width="38%" valign="top" align="center" rowspan="3">

<table CELLSPACING=1 CELLPADDING=2 border="0">
    <tr>
        <td colspan="2" align="center" class=customerltbkground><span class="shopassetdetailtxt"><b>
            <app:storeMessage key="userWorkOrder.text.workOrderItems.statusHistory"/>
        </b></span></td>
    </tr>

    <tr>
        <td class="shopcharthead" align="center">
            <div class="fivemargin">
                <app:storeMessage
                        key="userWorkOrder.text.workOrderItems.statusHistory.statusDate"/>
            </div>
        </td>
        <td class="shopcharthead" align="center">
            <div class="fivemargin">
                <app:storeMessage key="userWorkOrder.text.workOrderItems.statusHistory.statusCode"/>
            </div>
        </td>
    </tr>

    <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM"
                   property="workOrderItemDetail.statusHistories">
        <logic:iterate id="statusH" name="USER_WORK_ORDER_ITEM_MGR_FORM"
                       property="workOrderItemDetail.statusHistories"
                       type="com.cleanwise.service.api.value.WoiStatusHistData" indexId="j">
            <tr id="statusHistory<%=((Integer)j).intValue()%>">
                <td>
                    <%=ClwI18nUtil.formatDate(request, statusH.getStatusDate(), DateFormat.DEFAULT)%>
                </td>
                <td>
                    <bean:write name="statusH" property="statusCd"/>
                </td>
            </tr>
        </logic:iterate>
    </logic:present>
</table>

<br />

<table width="100%" border="0" cellspacing="1" cellpadding="2">
    <tr>
        <td colspan="4" align="center" class=customerltbkground><span class="shopassetdetailtxt"><b>
            <app:storeMessage key="userWorkOrder.text.assocDocs"/>
        </b></span>
            <%if (editingAuthorized || isSendToProviderStatus) {%>
            <html:button  property="action" styleClass="store_fb" onclick="actionSubmitTb(0,'createContent','t_userWorkOrderContentDetail','f_userSecondaryToolbar');">
                <app:storeMessage key="global.label.addContent"/>
            </html:button>
            <%}%>
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
    <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM"
                   property="workOrderItemDetail.contents">
        <logic:iterate id="contentV" name="USER_WORK_ORDER_ITEM_MGR_FORM"
                       property="workOrderItemDetail.contents"
                       type="com.cleanwise.service.api.value.WorkOrderContentView" indexId="j">
            <logic:present name="contentV" property="content">
                <logic:present name="contentV" property="workOrderContentData">
                    <bean:define id="woicId" name="contentV"
                                 property="workOrderContentData.workOrderContentId"/>
                    <tr id="docs<%=((Integer) j).intValue()%>">
                    <td>
                        <logic:present name="contentV" property="content.shortDesc">
                            <a href="../userportal/userWorkOrderContent.do?action=contentDetail&workOrderContentId=<%=woicId%>&display=t_userWorkOrderContentDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                <bean:write name="contentV" property="content.shortDesc"/>
                            </a>
                        </logic:present>

                    </td>
                    <td><%
                        String fileName = "";
                        if (contentV.getContent().getPath() != null) {
                            fileName = contentV.getContent().getPath();
                            if (fileName.indexOf("/") > -1) {
                                fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                            }
                        }
                    %>
                        <%String loc = "../userportal/userWorkOrderContent.do?action=readDoc&workOrderContentId=" + woicId + "&display=t_userWorkOrderContentDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar";%>
                        <a href="#" onclick="viewPrinterFriendly('<%=loc%>');">
                            <%=fileName%>
                        </a>
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
                </logic:present>
            </logic:present>
            </tr>
        </logic:iterate>
    </logic:present>
</table>

<br />

<table width="100%" border="0" cellspacing="1" cellpadding="2">
    <tr>
        <td colspan="3" align="center" class=customerltbkground>
            <span class="shopassetdetailtxt">
                <b><app:storeMessage key="userWorkOrder.text.partsOrder"/></b>
            </span>
        </td>
    </tr>

    <tr>
        <td class="shopcharthead" align="center">
            <div class="fivemargin">
                <app:storeMessage key="userWorkOrder.text.partsOrder.orderNum"/>
            </div>
        </td>
        <td class="shopcharthead" align="center">
            <div class="fivemargin">
                <app:storeMessage key="userWorkOrder.text.partsOrder.orderDate"/>
            </div>
        </td>
        <td class="shopcharthead" align="center">
            <div class="fivemargin">
                <app:storeMessage key="userWorkOrder.text.partsOrder.orderStatus"/>
            </div>
        </td>
    </tr>
    <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="itemOrders">
        <logic:iterate  id="ordersForItem"
                        name="USER_WORK_ORDER_ITEM_MGR_FORM"
                        property="itemOrders"
                        type="com.cleanwise.service.api.value.OrderData" indexId="j">
            <tr id="partsOrderForItem<%=((Integer)j).intValue()%>">
                <td>
                    <bean:define id="oid" name="ordersForItem" property="orderId"/>
                    <%
                        String orderLink = "partsOrder.do?action=view" + "&amp;orderId=" + oid;
                    %>
                    <a href="<%=orderLink%>">
                        <bean:write name="ordersForItem" property="orderNum"/>
                    </a>
                </td>
                <td>
                    <%=ClwI18nUtil.formatDate(request, ordersForItem.getAddDate(), DateFormat.DEFAULT)%>
                </td>
                <td>
                    <bean:write name="ordersForItem" property="orderStatusCd"/>
                </td>
            </tr>
        </logic:iterate>
    </logic:present>
</table>

</td>
</logic:greaterThan>
<td width="1%">&nbsp;</td>
</tr>

<tr><td colspan="5">
    <div align="center">
        <%if (editingAuthorized || isSendToProviderStatus) { %>
        <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'save');">
            <app:storeMessage key="global.action.label.save"/>
        </html:button>
        <% } %>
        <%if (editingAuthorized) { %>
        <logic:greaterThan name="workOrderItem" property="workOrderItemId" value="0">
            <html:button property="action" styleClass="store_fb"
                         onclick="actionSubmitTb(0,'remove','t_userWorkOrderItems','f_userSecondaryToolbar');">
                <app:storeMessage key="global.action.label.delete"/>
            </html:button>
        </logic:greaterThan>
        <%}%>
        <logic:greaterThan name="USER_WORK_ORDER_ITEM_MGR_FORM" property="workOrderItemId" value="0">
            <logic:equal name="USER_WORK_ORDER_ITEM_MGR_FORM" property="allowBuyWorkOrderParts" value="true">
            <%String redirectShop = "javascript:window.location='../userportal/userWorkOrderItem.do?action=orderParts'";%>
                <html:button property="action" styleClass="store_fb" onclick='<%=redirectShop%>'>
                    <app:storeMessage key="global.label.orderParts"/>
                </html:button>
            </logic:equal>
        </logic:greaterThan>
        <html:button  property="action" styleClass="store_fb" onclick="actionSubmitTb(0,'view_detail','t_userWorkOrderDetail','f_userSecondaryToolbar');">
            <app:storeMessage key="global.label.workOrderSummary"/>
        </html:button>
    </div>
</td>
</tr>
    <html:hidden property="workOrderItemId" value="<%=((Integer)woiId).toString()%>"/>
    <html:hidden property="workOrderId" value="<%=((Integer)woId).toString()%>"/>
</logic:present>
</logic:present>

    <html:hidden property="action" value="hiddenAction"/>
    <html:hidden property="tabs" value="f_userWorkOrderToolbar"/>
    <html:hidden property="display" value="t_userWorkOrderItemDetail"/>
    <html:hidden property="secondaryToolbar" value="f_userSecondaryToolbar"/>

</html:form>
</table>
