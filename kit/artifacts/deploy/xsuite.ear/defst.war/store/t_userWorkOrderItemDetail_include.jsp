<%--
  Date: 15.09.2008
  Time: 18:27:44
--%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.AssetData" %>
<%@ page import="com.cleanwise.service.api.value.AssetDataVector" %>
<%@ page import="com.cleanwise.service.api.value.WarrantyData" %>
<%@ page import="com.cleanwise.view.forms.UserWorkOrderDetailMgrForm" %>
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

<script language="JavaScript" src="../externals/ajaxutil.js"></script>
<script language="JavaScript" src="../externals/workOrderUtil.js"></script>

<script language="JavaScript1.2">
<!--

function onServiceProviderTypeChange() {
	
	var divEl = document.getElementById('AssetAndWarrantyConfig');
    var selectTypeEl = document.getElementsByName('typeCd');
	
    if (selectTypeEl[0].value == '<%=RefCodeNames.WORK_ORDER_TYPE_CD.EQUIPMENT%>') {
		divEl.style.display = "";
    } else {
        divEl.style.display = "none";
    }
}

-->
</script>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="USER_WORK_ORDER_DETAIL_MGR_FORM"  type="com.cleanwise.view.forms.UserWorkOrderDetailMgrForm"/>

<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderItemDetail">
<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderItemDetail.workOrderItem">

        <bean:define id="workOrderItem" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderItemDetail.workOrderItem"/>
        <bean:define id="woiId" name="workOrderItem" property="workOrderItemId"/>
        <bean:define id="woId" name="workOrderItem" property="workOrderId"/>

<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);%>

<% String mainTablePercent = "60%";%>
<logic:lessThan name="woiId" value="1">
    <% mainTablePercent = "98%";%>
</logic:lessThan>

<%
    String workOrderStatus = theForm.getWorkOrderDetail().getWorkOrder().getStatusCd();
    boolean editingAuthorized = appUser.canEditWorkOrder(workOrderStatus);
    boolean isSendToProviderStatus = RefCodeNames.WORK_ORDER_STATUS_CD.SEND_TO_PROVIDER.equals(workOrderStatus);
%>

<tr><td colspan="3">&nbsp;</td></tr>
<tr>
    <td width="1%">&nbsp;</td>

    <td width="<%=mainTablePercent%>" valign="top">
		
		
		<% String displayAssetWarranty = "none"; %>
        <logic:equal name="USER_WORK_ORDER_DETAIL_MGR_FORM"
                     property="workOrderDetail.workOrder.typeCd"
                     value="<%=RefCodeNames.WORK_ORDER_TYPE_CD.EQUIPMENT%>">
            <% displayAssetWarranty = "";%>
        </logic:equal>
        
        <div id="AssetAndWarrantyConfig" style="display: <%=displayAssetWarranty%>">
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
        
        <tr>
            <td width="32%">
                <span class="shopassetdetailtxt">
					<b><app:storeMessage key="userWorkOrder.text.workOrderItems.warranty"/>:</b>
               </span>
            </td>
            <td>
				<%if(editingAuthorized) {%>
	                <div id="warrantyDynamicBox">
						<select name="activeWarrantyIdStr"></select>
					</div>
				<%} else {%>
					<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderItemDetail.warranty.shortDesc">
						<bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderItemDetail.warranty.shortDesc"/>
					</logic:present>
				<%}%>
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
			
            var warrantyArray;
            var warrantyIdx = 0;
            <% if (((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset() != null &&
				  !((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().isEmpty()) { %>
				warrantyArray = new Array();
	        <% 	for(int i = 0; i < ((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().size(); i++) { %>
		            warrantyArray[warrantyIdx] = new Array();
		            warrantyArray[warrantyIdx][0] ='<%=((WarrantyData)((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getWarrantyId()%>';
		            warrantyArray[warrantyIdx][1] ='<%=((WarrantyData)((UserWorkOrderDetailMgrForm)theForm).getWarrantyForActiveAsset().get(i)).getShortDesc()%>';
		            warrantyIdx++;
            <% 	} %>
            <% } %>

            function warrantyDinamicBoxInit(selectElementName, warrantyArray, currentVal, editingAuthorized) {
                warrantyDynamicBox.init(selectElementName, warrantyArray, currentVal, editingAuthorized);
            }

            warrantyDinamicBoxInit('activeWarrantyIdStr', warrantyArray, '<%=((UserWorkOrderDetailMgrForm)theForm).getActiveWarrantyIdStr()%>', '<%=String.valueOf(editingAuthorized)%>');
			
            //-->
            </script>
        
		</table>
		</div>
		
		
		<table width="100%" cellpadding=1 cellspacing=5 border="0">
        <tr>
            <td width="32%">
                <span class="shopassetdetailtxt">
                    <b><app:storeMessage key="userWorkOrder.text.workOrderItems.description"/>:</b>&nbsp;<span class="reqind">*</span>
                </span>
            </td>
            <td>
                <% if(editingAuthorized) { %>
                    <html:text size="45" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemShortDesc"/>
                <% } else { %>
                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemShortDesc">
                        <bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemShortDesc"/>
                    </logic:present>
                <% } %>
            </td>
        </tr>
        <tr>
            <td colspan="2" valign="top">
                <span class="shopassetdetailtxt">
                    <b><app:storeMessage key="userWorkOrder.text.longDesc"/>:</b>
                </span>
            </td>
        </tr>
        <tr>
            <td width="98%" colspan="2" align="left">
                <% if(editingAuthorized) { %>
                    <html:textarea rows="7" cols="72" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemLongDesc"/>
                <% } else { %>
                    <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemLongDesc">
                        &nbsp;&nbsp;<bean:write name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemLongDesc"/>
						<br>
                    </logic:present>
                <% } %>
            </td>
        </tr>
        <tr>
            <td colspan="4">
                <table>
                    <tr>
                        <td width="30%">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="userWorkOrder.text.quotedLabor"/>:</b>
                            </span>
                        </td>
                        <td width="20%">
                            <% if(editingAuthorized) { %>
                                <html:text size="8" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemQuotedLabor"/>
                            <% } else { %>
                                <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemQuotedLabor">
                                    <bean:define id="itemQuotedLabor" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemQuotedLabor" type="java.lang.String"/>
                                    <% if (Utility.isSet(itemQuotedLabor)) { %>
                                    <%=ClwI18nUtil.getPriceShopping(request,itemQuotedLabor)%>
                                    <% } %>
                                </logic:present>
                            <% } %>
                        </td>
                        <td width="30%">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="userWorkOrder.text.quotedPartsCost"/>:</b>
                            </span>
                        </td>
                        <td width="20%">
                            <% if(editingAuthorized) { %>
                                <html:text size="8" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemQuotedPart"/>
                            <% } else { %>
                                <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemQuotedPart">
                                    <bean:define id="itemQuotedPart" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemQuotedPart" type="java.lang.String"/>
                                    <% if (Utility.isSet(itemQuotedPart)) { %>
                                    <%=ClwI18nUtil.getPriceShopping(request,itemQuotedPart)%>
                                    <% } %>
                                </logic:present>
                            <% } %>
                        </td>
                    </tr>
                    
                    <logic:greaterThan name="workOrderItem" property="workOrderItemId" value="0">
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <span class="shopassetdetailtxt">
                                    <b><app:storeMessage key="userWorkOrder.text.workOrderItems.estimateTotalCost"/>:</b>
                               </span>
                            </td>
                            <td>
                                <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemQuotedTotalCost">
                                    <bean:define id="itemQuotedTotalCost" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemQuotedTotalCost" type="java.lang.String"/>
                                    <% if (Utility.isSet(itemQuotedTotalCost)) { %>
                                    <%=ClwI18nUtil.getPriceShopping(request,itemQuotedTotalCost)%>
                                    <% } %>
                                </logic:present>
                            </td>
                        </tr>
                    </logic:greaterThan>
                    
                    <tr>
                        <td>
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="userWorkOrder.text.workOrderItems.actualLabor"/>:</b>
                            </span>
                        </td>
                        <td>
                            <%if (editingAuthorized || isSendToProviderStatus) { %>
                                <html:text size="8" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemActualLabor"/>
                            <% } else { %>
                                <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemActualLabor">
                                    <bean:define id="itemActualLabor" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemActualLabor"
                                                 type="java.lang.String"/>
                                    <% if (Utility.isSet(itemActualLabor)) { %>
                                        <%=ClwI18nUtil.getPriceShopping(request, itemActualLabor)%>
                                    <% } %>
                                </logic:present>
                            <% } %>
                        </td>
                        <td>
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="userWorkOrder.text.workOrderItems.actualPart"/>:</b>
                            </span>
                        </td>
                        <td>
                            <%if (editingAuthorized || isSendToProviderStatus) { %>
                                <html:text size="8" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemActualPart"/>
                            <% } else { %>
                                <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemActualPart">
                                    <bean:define id="itemActualPart" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemActualPart"
                                                 type="java.lang.String"/>
                                    <% if (Utility.isSet(itemActualPart)) { %>
                                        <%=ClwI18nUtil.getPriceShopping(request, itemActualPart)%>
                                    <% } %>
                                </logic:present>
                            <% } %>
                        </td>
                    </tr>
                    
                    <logic:greaterThan name="workOrderItem" property="workOrderItemId" value="0">
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <span class="shopassetdetailtxt">
                                    <b><app:storeMessage key="userWorkOrder.text.workOrderItems.actualTotalCost"/>:</b>
                                </span>
                            </td>
                            <td>
                                <logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemActualTotalCost">
                                    <bean:define id="itemActualTotalCost" name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemActualTotalCost" type="java.lang.String"/>
                                    <%if (Utility.isSet(itemActualTotalCost)) { %>
                                        <%=ClwI18nUtil.getPriceShopping(request,itemActualTotalCost)%>
                                    <% } %>
                                </logic:present>
                            </td>
                        </tr>
                    </logic:greaterThan>

                </table>
            </td>
        </tr>
        </table>
    </td>
    
    <td width="1%">&nbsp;</td>
</tr>

<logic:greaterThan name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderItemId" value="0">
<logic:present name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemOrders">
    <bean:size id="itemOrdersSize"  name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="itemOrders"/>
    <logic:greaterThan name="itemOrdersSize" value="0">
    <tr>
        <td colspan="3">
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
            <logic:iterate  id="ordersForItem"
                            name="USER_WORK_ORDER_DETAIL_MGR_FORM"
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
        </table>
        </td>
    </tr>
    </logic:greaterThan>
</logic:present>
</logic:greaterThan>
<tr>
    <td colspan="3">
        <div align="center">
            <%if (editingAuthorized) { %>
            <logic:greaterThan name="workOrderItem" property="workOrderItemId" value="0">
                <html:button property="action" styleClass="store_fb"
                             onclick="actionSubmitTb(0,'remove','t_userWorkOrderDetail','f_userSecondaryToolbar');">
                    <app:storeMessage key="global.action.label.delete"/>
                </html:button>
            </logic:greaterThan>
            <% } %>
            <logic:greaterThan name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="workOrderItemId" value="0">
                <logic:equal name="USER_WORK_ORDER_DETAIL_MGR_FORM" property="allowBuyWorkOrderParts" value="true">
                <%String redirectShop = "javascript:window.location='../userportal/userWorkOrderItem.do?action=orderParts'";%>
                    <html:button property="action" styleClass="store_fb" onclick='<%=redirectShop%>'>
                        <app:storeMessage key="global.label.orderParts"/>
                    </html:button>
                </logic:equal>
            </logic:greaterThan>
        </div>
    </td>
</tr>
<tr><td colspan="3">&nbsp;</td></tr>
    <html:hidden property="workOrderItemId" value="<%=((Integer)woiId).toString()%>"/>
</logic:present>
</logic:present>

