<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.logic.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<logic:present	name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.customerShipTo">
	<table>
<tr>
<td><b>Customer Shipping Name:</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.customerShipTo.shortDesc" filter="true"/>
</td>
</tr>
<tr>
<td><b>Customer Shipping Address:</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.customerShipTo.address1" filter="true"/>
</td>
</tr>
<tr>
<td><b>&nbsp;</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.customerShipTo.address2" filter="true"/>
</td>
</tr>
<tr>
<td><b>&nbsp;</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.customerShipTo.address3" filter="true"/>
</td>
</tr>
<tr>
<td><b>&nbsp;</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.customerShipTo.address4" filter="true"/>
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.customerShipTo.city" filter="true"/>
</td>
</tr>
<tr>
<td><b>State:</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.customerShipTo.stateProvinceCd" filter="true"/>
</td>
</tr>
<tr>
<td><b>ZIP Code:</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.customerShipTo.postalCode" filter="true"/>
</td>
</tr>
	</table>

</logic:present>

