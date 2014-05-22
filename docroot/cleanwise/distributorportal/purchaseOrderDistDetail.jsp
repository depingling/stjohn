<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.logic.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.struts.util.RequestUtils" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="orderStatus" type="java.lang.String"
                                name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderStatusCd"/>
<bean:define id="purchaseOrderStatusDescDataView" type="com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView"
                                name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView"/>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, parm) {
  var loc = pLoc + ".jsp?parm=" + parm;
  if(parm != "print"){
    locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  }else{
    locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  }
  locatewin.focus();
  return false;
}

function popLocate2(pLoc, name, pDesc) {
    var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
    locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
    locatewin.focus();
    return false;
}

function popPrint(pLoc) {
  var loc = pLoc;
  locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function popLink(pLoc) {
  var loc = pLoc;
  locatewin = window.open(loc,"Note", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function popUpdate(pLoc, parm) {
  var loc = pLoc + parm;
  locatewin = window.open(loc,"Update", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

//-->
</script>

<html:html>

<head>
<title>Distributor Portal: Purchase Orders</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/distributorToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<font color=red>
<html:errors/>
</font>

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="PO_OP_DETAIL_FORM" action="/distributorportal/purchaseOrderDistDetail.do"
        type="com.cleanwise.view.forms.PurchaseOrderOpDetailForm">

        <tr>
          <td colspan="2" class="mediumheader">PO Header Information</td>
          <td colspan="2">&nbsp;</td>
        </tr>
        <logic:equal name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.billingOnlyOrder" value="true">
            <tr>
              <td colspan="4" class="mediumheader" align="center">
                  <BR>THIS PO IS A CONFIRMATION ONLY PO AND INTENTIONALLY NOT SENT TO YOU
                  <BR>
                  <BR>
              </td>
            </tr>
        </logic:equal>
        <tr>
                <td colspan="2">
                        <table width="100%">
                                <tr>
                                   <td><b>PO#:</b></td>
                                   <td>
                                     <bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.erpPoNum" filter="true"/>
                                   </td>
                                </tr>
                                <tr>
                                   <td><b>PO Date:</b></td>
                                   <logic:present name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.poDate">
                                     <td>
                                       <bean:define id="erppodate" name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.poDate"/>
                                       <i18n:formatDate value="<%=erppodate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
                                     </td>
                                   </logic:present>
                                   <logic:notPresent name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.poDate">
                                     <td>&nbsp;</td>
                                   </logic:notPresent>
                                </tr>
                                <tr>
                                  <td><b>Customer PO#:</b></td>
                                  <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.requestPoNum" filter="true"/></td>
                                </tr>

                                <tr>
                                  <td><b>Customer Requisition#:</b></td>
                                  <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.refOrderNum" filter="true"/></td>
                                </tr>
								<logic:equal name="PO_OP_DETAIL_FORM" property="distributorData.printCustContactOnPurchaseOrder" value="true">
									<tr>
									  <td><b>Contact Name:</b></td>
									  <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderContactName" filter="true"/></td>
									</tr>
									<tr>
									  <td><b>Contact Phone:</b></td>
									  <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderContactPhoneNum" filter="true"/></td>
									</tr>
								</logic:equal>
                                <tr>
                                      <td><b>Status:</b></td>
                                      <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatus" filter="true"/></td>
                                    </tr>
                                    <tr>
                                      <td>&nbsp;</td>
                                      <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                      <td><b>Freight Terms:</b></td>
                                      <td>
                                        <logic:present name="PO_OP_DETAIL_FORM" property="distributorData.purchaseOrderFreightTerms">
                                        <logic:present name="PO_OP_DETAIL_FORM" property="distributorData.purchaseOrderFreightTerms.value">
                                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.purchaseOrderFreightTerms.value" filter="true"/>
                                        </logic:present>
                                        </logic:present>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td><b>Due Days:</b></td>
                                      <td>
                                        <logic:present name="PO_OP_DETAIL_FORM" property="distributorData.purchaseOrderDueDays">
                                        <logic:present name="PO_OP_DETAIL_FORM" property="distributorData.value">
                                        <bean:write name="PO_OP_DETAIL_FORM" property="distributorData.purchaseOrderDueDays.value" filter="true"/>
                                        </logic:present>
                                        </logic:present>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td><b>Subtotal:</b></td>
                                      <td>
                                       <bean:define id="purchaseOrderLnTotal"  name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.lineItemTotal"/>
                                       <i18n:formatCurrency value="<%=purchaseOrderLnTotal%>" locale="<%=Locale.US%>"/>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td><b>Misc:</b></td>
                                      <td>
                                         <bean:define id="purchaseOrderMisc"  name="PO_OP_DETAIL_FORM" property="miscellaneousAmount"/>
                                         <i18n:formatCurrency value="<%=purchaseOrderMisc%>" locale="<%=Locale.US%>"/>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td><b>Total:</b></td>
                                      <td>
                                        <bean:define id="purchaseOrderTotal"  name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.purchaseOrderTotal"/>
                                        <i18n:formatCurrency value="<%=purchaseOrderTotal%>" locale="<%=Locale.US%>"/>
                                      </td>
                                    </tr>
                        </table>
                </td>

                <td colspan="2" valign="top">
                        <table width="100%">
                                <tr>
                                <td><b>Account Name:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.poAccountName" filter="true"/>
                                </td>
                                </tr>
                                <tr>
                                <td><b>Site Name:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.orderData.orderSiteName" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>Shipping Address:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.address1" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>&nbsp;</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.address2" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>&nbsp;</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.address3" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>City:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.city" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>State:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.stateProvinceCd" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                <td><b>ZIP Code:</b></td>
                                <td><bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.shipToAddress.postalCode" filter="true"/>
                                        </td>
                                </tr>
                                <tr>
                                   <td colspan="2">&nbsp;</td>
                                </tr>
                                <tr>
                                  <td><b>PO Manifest Status:</b></td>
                                  <td>
                                     <bean:write name="PO_OP_DETAIL_FORM" property="purchaseOrderStatusDescDataView.purchaseOrderData.purchOrdManifestStatusCd"/>
                                  </td>
                                </tr>
                        </table>
                </td>
        </tr>
    <tr><td colspan="4">&nbsp;</td></tr>
    <tr>
        <td colspan="3">
            <table width="100%">
                <tr>
                    <td><html:submit property="action" value="Set Shipping Status For All"/></td>
                    <td>
                        <html:select name="PO_OP_DETAIL_FORM" property="shippingStatus">
                                        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                        <html:options  collection="OrderProperty.ShipStatus.vector" property="value" />
                                </html:select>
                    </td>
                 </tr>
                 <tr>
                    <td><html:submit property="action" value="Set Target Ship Date For All"/></td>
                    <td><html:text name="PO_OP_DETAIL_FORM" property="targetShipDate"/></td>
                </tr>
		 <tr>
                    <td><html:submit property="action" value="Set All Quantities"/></td>
                    <td></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
       <td colspan="4" align="center">
         <input type="button" onclick="popPrint('purchaseOrderDistDetail.do?action=print');" value="Print PO Detail">
	 
	 <html:submit property="action">
	   <app:storeMessage  key="global.action.label.save"/>
	 </html:submit>
	 
	 <html:submit property="action">
	   <app:storeMessage  key="button.delete.order.item.actions"/>
	 </html:submit>
       </td>
    </tr>

    <tr>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4">
        <jsp:include flush='true' page="purchaseOrderDistItemInc.jsp"/>
        </table>
      </td>
    </tr>
</html:form>
</table>

</div>

<jsp:include flush='true' page="ui/distributorFooter.jsp"/>

</body>
</html:html>

