<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.logic.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.math.BigDecimal" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<%
String taxreadonlyStr=(String)request.getParameter("taxreadonly");
boolean taxreadonly=false;
if(taxreadonlyStr!=null&&taxreadonlyStr.equals(Boolean.TRUE.toString()))
{
  taxreadonly=true;  
}
%>
<script language="JavaScript1.2">

function calcTotal2() {

  var calcTotal = 0.0;

  if (document.STORE_ORDER_DETAIL_FORM.elements["subTotal"] &&
      document.STORE_ORDER_DETAIL_FORM.elements["subTotal"].value != "") {
        calcTotal += document.STORE_ORDER_DETAIL_FORM.elements["subTotal"].value * 1.0;
  }
  if (document.STORE_ORDER_DETAIL_FORM.elements["totalFreightCostS"] &&
      document.STORE_ORDER_DETAIL_FORM.elements["totalFreightCostS"].value != "") {
        calcTotal += document.STORE_ORDER_DETAIL_FORM.elements["totalFreightCostS"].value * 1.0;
  }
  if (document.STORE_ORDER_DETAIL_FORM.elements["totalTaxCostStr"] &&
      document.STORE_ORDER_DETAIL_FORM.elements["totalTaxCostStr"].value != "") {
        calcTotal += document.STORE_ORDER_DETAIL_FORM.elements["totalTaxCost"].value * 1.0;
  }
  if (document.STORE_ORDER_DETAIL_FORM.elements["totalMiscCostS"] &&
      document.STORE_ORDER_DETAIL_FORM.elements["totalMiscCostS"].value != "") {
        calcTotal += document.STORE_ORDER_DETAIL_FORM.elements["totalMiscCostS"].value * 1.0;
  }
  if (document.STORE_ORDER_DETAIL_FORM.elements["rushOrderChargeS"] &&
      document.STORE_ORDER_DETAIL_FORM.elements["rushOrderChargeS"].value != "") {
        calcTotal += document.STORE_ORDER_DETAIL_FORM.elements["rushOrderChargeS"].value * 1.0;
  }

  if (document.STORE_ORDER_DETAIL_FORM.elements["fuelSurchargeStr"] &&
      document.STORE_ORDER_DETAIL_FORM.elements["fuelSurchargeStr"].value != "") {
        calcTotal += document.STORE_ORDER_DETAIL_FORM.elements["fuelSurchargeStr"].value * 1.0;
  }
  if (document.STORE_ORDER_DETAIL_FORM.elements["smallOrderFeeStr"] &&
      document.STORE_ORDER_DETAIL_FORM.elements["smallOrderFeeStr"].value != "") {
        calcTotal += document.STORE_ORDER_DETAIL_FORM.elements["smallOrderFeeStr"].value * 1.0;
  }
  if (document.STORE_ORDER_DETAIL_FORM.elements["discountStr"] &&
      document.STORE_ORDER_DETAIL_FORM.elements["discountStr"].value != "") {
        var rawDiscount = document.STORE_ORDER_DETAIL_FORM.elements["discountStr"].value * 1.0;
        if (rawDiscount > 0) {
            calcTotal = calcTotal - rawDiscount;
        } else {
            calcTotal = calcTotal + rawDiscount;
        }
  }

  if ( 0.0 != calcTotal )  {
        document.STORE_ORDER_DETAIL_FORM.elements["totalAmount"].value = calcTotal;
  }
  else {
        document.STORE_ORDER_DETAIL_FORM.elements["totalAmount"].value = 0;
  }

  return true;
}

</script>


<bean:define id="theForm" name="STORE_ORDER_DETAIL_FORM"
  type="com.cleanwise.view.forms.StoreOrderDetailForm"/>

<%
boolean allowMod = false;
String p = request.getParameter("allowEdits");
if ( p != null && p.equals("true")) {
  allowMod = true;
}
%>

<!-- order charges 0 -->

<tr>
<td><b>Sub-Total:</b></td>
<td><bean:define id="subtotal"  name="STORE_ORDER_DETAIL_FORM"
  property="subTotal"/>
<i18n:formatCurrency value="<%=subtotal%>" locale="<%=Locale.US%>"/>
<html:hidden name="STORE_ORDER_DETAIL_FORM" property="subTotal"/>
</td>
</tr>

<tr>
<td><b>Freight:</b></td>
<% if (allowMod) { %>
<td><html:text styleClass="text" size="7" maxlength="13"
  name="STORE_ORDER_DETAIL_FORM" property="totalFreightCostS"
  onchange="return calcTotal2();"/>
<% } else { %>
<td>
<logic:present name="STORE_ORDER_DETAIL_FORM"
  property="orderStatusDetail.orderDetail.totalFreightCost">
<bean:define id="freight"  name="STORE_ORDER_DETAIL_FORM"
  property="orderStatusDetail.orderDetail.totalFreightCost"/>
<i18n:formatCurrency value="<%=freight%>" locale="<%=Locale.US%>"/>
</logic:present>
</td>
<% }  %>
</td>
</tr>

<tr>
    <td><b>Handling:</b></td>
    <% if (allowMod) { %>
    <td>
        <html:text styleClass="text" size="7" maxlength="13"
            name="STORE_ORDER_DETAIL_FORM" property="totalMiscCostS"
            onchange="return calcTotal2();"/>
    <% } else { %>
    <td>
        <logic:present name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.totalMiscCost">
            <bean:define id="misc"  name="STORE_ORDER_DETAIL_FORM" property="orderStatusDetail.orderDetail.totalMiscCost"/>
            <i18n:formatCurrency value="<%=misc%>" locale="<%=Locale.US%>"/>
        </logic:present>
    </td>
    <% } %>
    </td>
</tr>

<% if ( theForm.getRushOrderCharge() != null ) { %>
<tr><td><b>Rush Order Charge:</b>
</td>
<% if (allowMod) { %>
<td><html:text styleClass="text" size="7" maxlength="13"
  name="STORE_ORDER_DETAIL_FORM" property="rushOrderChargeS"
  onchange="return calcTotal2();"/>
  </td>
<% } else { %>
<td>
<i18n:formatCurrency value="<%=theForm.getRushOrderCharge()%>"
  locale="<%=Locale.US%>"/>
</td>
</tr>
<%
}


}
else { // null rush charge %>
<html:hidden  name="STORE_ORDER_DETAIL_FORM" property="rushOrderChargeS"/>
<% } /* Hidden value for rush charge. */ %>


<tr>
    <td><b>Small Order Fee:</b></td>
    <td>
        <logic:present name="STORE_ORDER_DETAIL_FORM" property="smallOrderFeeAmt">
            <% if (allowMod) { %>
            <html:text styleClass="text"
                       size="7"
                       maxlength="13"
                       name="STORE_ORDER_DETAIL_FORM"
                       property="smallOrderFeeStr"
                       onchange="return calcTotal2();"/>
            <%} else {%>
            <bean:define id="smallOrderFee" name="STORE_ORDER_DETAIL_FORM" property="smallOrderFeeAmt"/>
            <i18n:formatCurrency value="<%=smallOrderFee%>" locale="<%=Locale.US%>"/>
            <%}%>
        </logic:present>
        <logic:notPresent name="STORE_ORDER_DETAIL_FORM" property="smallOrderFeeAmt">
            <% if (allowMod) { %>
            <html:text styleClass="text"
                       size="7"
                       maxlength="13"
                       name="STORE_ORDER_DETAIL_FORM"
                       property="smallOrderFeeStr"
                       onchange="return calcTotal2();"/>
            <%} else {%>
            <i18n:formatCurrency value="<%=new BigDecimal(0)%>" locale="<%=Locale.US%>"/>
            <%}%>
        </logic:notPresent>

    </td>
</tr>

<tr>
    <td><b>Fuel Surcharge:</b></td>
    <td>
        <logic:present name="STORE_ORDER_DETAIL_FORM" property="fuelSurchargeAmt">
            <% if (allowMod) { %>
            <html:text styleClass="text"
                       size="7"
                       maxlength="13"
                       name="STORE_ORDER_DETAIL_FORM"
                       property="fuelSurchargeStr"
                       onchange="return calcTotal2();"/>
            <%} else {%>
            <bean:define id="fuelSurcharge" name="STORE_ORDER_DETAIL_FORM" property="fuelSurchargeAmt"/>
            <i18n:formatCurrency value="<%=fuelSurcharge%>" locale="<%=Locale.US%>"/>
            <%}%>
        </logic:present>
        <logic:notPresent name="STORE_ORDER_DETAIL_FORM" property="fuelSurchargeAmt">
            <% if (allowMod) { %>
            <html:text styleClass="text"
                       size="7"
                       maxlength="13"
                       name="STORE_ORDER_DETAIL_FORM"
                       property="fuelSurchargeStr"
                       onchange="return calcTotal2();"/>
            <%} else {%>
            <i18n:formatCurrency value="<%=new BigDecimal(0)%>" locale="<%=Locale.US%>"/>
            <%}%>
        </logic:notPresent>
    </td>
</tr>

<tr>
    <td><b>Discount:</b></td>
    <td>
        <logic:present name="STORE_ORDER_DETAIL_FORM" property="discountAmt">
            <% if (allowMod) { %>
            <html:text styleClass="text"
                       size="7"
                       maxlength="13"
                       name="STORE_ORDER_DETAIL_FORM"
                       property="discountStr"
                       onchange="return calcTotal2();"/>
            <%} else {%>
            <bean:define id="discountAmt" name="STORE_ORDER_DETAIL_FORM" property="discountAmt"/>
            <i18n:formatCurrency value="<%=discountAmt%>" locale="<%=Locale.US%>"/>
            <%}%>
        </logic:present>
        <logic:notPresent name="STORE_ORDER_DETAIL_FORM" property="discountAmt">
            <% if (allowMod) { %>
            <html:text styleClass="text"
                       size="7"
                       maxlength="13"
                       name="STORE_ORDER_DETAIL_FORM"
                       property="discountStr"
                       onchange="return calcTotal2();"/>
            <%} else {%>
            <i18n:formatCurrency value="<%=new BigDecimal(0)%>" locale="<%=Locale.US%>"/>
            <%}%>
        </logic:notPresent>
    </td>
</tr>

<tr>
<td><b>Tax:</b></td>
<td>
  <%if(taxreadonly) {%>
   <%
       BigDecimal taxBD = new BigDecimal(theForm.getTotalTaxCostStr()); %>
       <i18n:formatCurrency value = "<%=taxBD%>"  locale="<%=Locale.US%>"/>
  
    <%} else {%>

    <html:text  readonly="<%=taxreadonly%>" styleClass="text" size="7" maxlength="13"
              name="STORE_ORDER_DETAIL_FORM"  property="totalTaxCostStr" />

    <%}%>
</td>
</tr>


<tr>
<td><b>Total:</b></td>
<td><html:text styleClass="textreadonly" size="7"
 maxlength="13" name="STORE_ORDER_DETAIL_FORM"
 property="totalAmount" readonly="true"/>
</td>
</tr>

<!-- order charges 1 -->



