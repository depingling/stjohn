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

<html:form name="USER_WORK_ORDER_ITEM_MGR_FORM" action="/serviceproviderportal/workOrderItem.do" scope="session">

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

<% boolean editingAuthorized = appUser.canEditWorkOrder(detForm.getWorkOrderDetail().getWorkOrder().getStatusCd());%>

<tr>
    <td colspan="5"><br></td>
</tr>

<tr>
    <td width="1%">&nbsp;</td>

    <td width="<%=mainTablePercent%>" valign="top">
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td colspan="4">
                    <table width=100% cellpadding="2" cellspacing=1 style="border:#000000 1px solid">
                        <tr>
                            <td class="customerltbkground" valign="top" align="middle" colSpan="4">
                                <span class=shopassetdetailtxt>
                                    <b><app:storeMessage key="userWorkOrder.text.workOrderPlan"/></b>
                                </span>
                            </td>
                        </tr>
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
                            <td width="20%"></td>
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
                                <logic:present name="theForm" property="activeAsset">
                                    <bean:write name="theForm" property="activeAsset.shortDesc"/>
                                </logic:present>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <div id="assetDynamicBox"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="shopassetdetailtxt">
                                    <b><app:storeMessage key="userWorkOrder.text.workOrderItems.warranty"/>:</b>
                                </span>
                            </td>
                            <td colspan="3">
                                <logic:present name="theForm" property="activeWarrantyIdStr">
                                    <div id="warrantyDynamicBox"/>
                                </logic:present>
                            </td>
                        </tr>

<script language="JavaScript1.2">
<!--
<%
                String modelNumberMess  = "";
                String modelNumberVal   = "";
                String serialNumberMess = "";
                String serialNumberVal  = "";

                modelNumberMess  = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userAssets.shop.text.param.modelNumber:"));
                serialNumberMess = Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userAssets.shop.text.param.serialnumber:"));

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

function assetDynamicBoxInit(modelNumberMess, modelNumberVal, serialNumberMess, serialNumberVal) {
                assetDynamicBox.init(modelNumberMess, modelNumberVal, serialNumberMess, serialNumberVal);
                assetDynamicBox.initWriter('assetDynamicBox');
                assetDynamicBox.write();
}

function warrantyDinamicBoxInit(selectElementName,warrantyArray,currentVal,editingAuthorized) {
                warrantyDynamicBox.init(selectElementName,warrantyArray,currentVal,editingAuthorized);
                warrantyDynamicBox.initWriter('warrantyDynamicBox');
                warrantyDynamicBox.write();
}

assetDynamicBoxInit('<%=modelNumberMess%>','<%=modelNumberVal%>','<%=serialNumberMess%>','<%=serialNumberVal%>');
warrantyDinamicBoxInit("activeWarrantyIdStr",warrantyArray,'<%=((UserWorkOrderItemMgrForm)theForm).getActiveWarrantyIdStr()%>','<%=String.valueOf(editingAuthorized)%>');
//-->
</script>
                        </logic:equal>
                        <tr>
                            <td>
                                <span class="shopassetdetailtxt">
                                    <b><app:storeMessage key="userWorkOrder.text.workOrderItems.description"/>:</b>
                                </span>
                            </td>
                            <td colspan="3">
                                <logic:present name="theForm" property="shortDesc">
                                    <bean:write name="theForm" property="shortDesc"/>
                                </logic:present>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" valign="top">
                                <span class="shopassetdetailtxt">
                                    <b><app:storeMessage key="userWorkOrder.text.longDesc"/>:</b>
                                 </span>
                            </td>
                        </tr>
                        <tr>
                            <td width="98%" colspan="4" align="center">
                                <logic:present name="theForm" property="longDesc">
                                    <bean:write name="theForm" property="longDesc"/>
                                </logic:present>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
               
            <tr><td>&nbsp;</td></tr>
                
            <tr>
                <td colspan="4">
                    <table width=100% cellpadding="2" cellspacing=1 style="border:#000000 1px solid">
                        <tr>
                            <td class="customerltbkground" valign="top" align="middle" colSpan="4">
                                <span class="shopassetdetailtxt">
                                    <b><app:storeMessage key="userWorkOrder.text.workOrderQuoted"/></b>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td width="30%">
                                <span class="shopassetdetailtxt">
                                    <b><app:storeMessage key="userWorkOrder.text.quotedLabor"/>:</b>
                                </span>
                            </td>
                            <td width="20%">
                                <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="estimatedLabor">
                                    <bean:define id="estimatedLabor"
                                                 name="USER_WORK_ORDER_ITEM_MGR_FORM"
                                                 property="estimatedLabor"
                                                 type="java.lang.String"/>
                                    <html:text size="12"
                                               name="USER_WORK_ORDER_ITEM_MGR_FORM"
                                               property="estimatedLabor" />
                                </logic:present>
                            </td>
                            <td width="30%">
                                <span class="shopassetdetailtxt">
                                    <b><app:storeMessage key="userWorkOrder.text.quotedPartsCost"/>:</b>
                                </span>
                            </td>
                            <td width="20%">
                                <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="estimatedPart">
                                    <bean:define id="estimatedPart"
                                                 name="USER_WORK_ORDER_ITEM_MGR_FORM"
                                                 property="estimatedPart"
                                                 type="java.lang.String"/>
                                    <html:text size="12"
                                               name="USER_WORK_ORDER_ITEM_MGR_FORM"
                                               property="estimatedPart" />
                                </logic:present>
                            </td>
                        </tr>
                        <logic:greaterThan name="workOrderItem" property="workOrderItemId" value="0">
                        <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <span class="shopassetdetailtxt">
                                    <b><app:storeMessage key="userWorkOrder.text.quotedTotalCost"/>:</b>
                                </span>
                            </td>
                            <td>
                                <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="estimateTotalCost">
                                    <bean:define id="estimateTotalCost"
                                                 name="USER_WORK_ORDER_ITEM_MGR_FORM"
                                                 property="estimateTotalCost"
                                                 type="java.lang.String"/>
                                    <%if (Utility.isSet(estimateTotalCost)) { %>
                                        <%=ClwI18nUtil.getPriceShopping(request, estimateTotalCost)%>
                                    <%}%>
                                </logic:present>
                                
                            </td>
                        </tr>
                        </logic:greaterThan>
                    </table>
                </td>
            </tr>
            
            <tr><td>&nbsp;</td></tr>
            
            <tr>
            <td colspan="4">
                <table width=100% cellpadding="2" cellspacing=1 style="border:#000000 1px solid">
                    <tr>
                        <td class="customerltbkground" valign="top" align="middle" colSpan="4">
                            <span class=shopassetdetailtxt>
                                <b><app:storeMessage key="userWorkOrder.text.workOrderActual"/></b>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="userWorkOrder.text.workOrderItems.actualLabor"/>:</b>
                            </span>
                        </td>
                        <td>
                            <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="actualLabor">
                                <bean:define id="actualLabor"
                                             name="USER_WORK_ORDER_ITEM_MGR_FORM"
                                             property="actualLabor"
                                             type="java.lang.String"/>
                                <html:text size="12"
                                           name="USER_WORK_ORDER_ITEM_MGR_FORM"
                                           property="actualLabor" />
                            </logic:present>
                        </td>
                        <td>
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="userWorkOrder.text.workOrderItems.actualPart"/>:</b>
                            </span>
                        </td>
                        <td>
                            <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="actualPart">
                                <bean:define id="actualPart"
                                             name="USER_WORK_ORDER_ITEM_MGR_FORM"
                                             property="actualPart"
                                             type="java.lang.String"/>
                                <html:text size="12"
                                           name="USER_WORK_ORDER_ITEM_MGR_FORM"
                                           property="actualPart" />
                            </logic:present>
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
                            <logic:present name="USER_WORK_ORDER_ITEM_MGR_FORM" property="actualTotalCost">
                            <bean:define id="actualTotalCost"
                                         name="USER_WORK_ORDER_ITEM_MGR_FORM"
                                         property="actualTotalCost"
                                         type="java.lang.String"/>
                            <%if (Utility.isSet(actualTotalCost)) { %>
                                <%=ClwI18nUtil.getPriceShopping(request, actualTotalCost)%>
                            <%}%>
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

<logic:greaterThan name="woiId" value="0">
<td width="38%" valign="top" align="center" rowspan="3">
    <table width="100%" border="0" cellspacing="1" cellpadding="2">
        <tr>
            <td colspan="2" align="center" class="customerltbkground">
                <span class="shopassetdetailtxt">
                    <b><app:storeMessage key="userWorkOrder.text.workOrderItems.statusHistory"/></b>
                </span>
            </td>
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
                           type="com.cleanwise.service.api.value.WoiStatusHistData"
                           indexId="j">
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

    <br>

    <table width="100%" border="0" cellspacing="1" cellpadding="2">
        <tr>
            <td colspan="4" align="center" class="customerltbkground">
                <span class="shopassetdetailtxt">
                    <b><app:storeMessage key="userWorkOrder.text.assocDocs"/></b>
                </span>
                <html:button  property="action" styleClass="store_fb" onclick="actionSubmitTb(0,'createContent','t_serviceProviderWorkOrder','');">
                    <app:storeMessage key="global.label.addContent"/>
                </html:button>
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
                                <a href="../serviceproviderportal/workOrderContent.do?action=contentDetail&workOrderContentId=<%=woicId%>&tabs=f_serviceProviderToolbar&display=t_serviceProviderWorkOrder">
                                    <bean:write name="contentV" property="content.shortDesc"/>
                                </a>
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
                            <%String loc = "../serviceproviderportal/workOrderContent.do?action=readDoc&workOrderContentId=" + woicId + "&display=t_serviceProviderWorkOrder&tabs=f_serviceProviderToolbar";%>
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

    <br>

    <table width="100%" border="0" cellspacing="1" cellpadding="2">
        <tr>
            <td colspan="3" align="center" class="customerltbkground">
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
                            String orderLink = "workOrderItem.do?action=partsOrder" + "&amp;orderId=" + oid + "&amp;tabs=f_serviceProviderToolbar&amp;display=t_serviceProviderWorkOrder";
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

<tr>
    <td colspan="5"><br></td>
</tr>

<tr>
    <td colspan="5" align="center">
        <table>
            <tr>
                <td align="right">
                    <html:button property="action" styleClass="store_fb" onclick="actionSubmit('0','save');">
                        <app:storeMessage key="global.action.label.save"/>
                    </html:button>
                    &nbsp;
                </td>
            </tr>
        </table>
    </td>
</tr>
    <html:hidden property="workOrderItemId" value="<%=((Integer)woiId).toString()%>"/>
    <html:hidden property="workOrderId" value="<%=((Integer)woId).toString()%>"/>
</logic:present>
</logic:present>

    <html:hidden property="action" value="hiddenAction"/>
    <html:hidden property="tabs" value="f_serviceProviderWorkOrderToolbar"/>
    <html:hidden property="display" value="t_serviceProviderWorkOrder"/>
    <html:hidden property="secondaryToolbar" value=""/>

</html:form>
</table>
