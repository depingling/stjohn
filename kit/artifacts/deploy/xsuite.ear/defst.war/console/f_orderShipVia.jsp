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

<logic:present	name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipVia">
	<table>
<tr>
<td><b>Ship Via:</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipVia.busEntityData.shortDesc" filter="true"/>
</td>
</tr>
<tr>
<td><b>Address:</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipVia.primaryAddress.address1" filter="true"/>
</td>
</tr>
<tr>
<td><b>&nbsp;</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipVia.primaryAddress.address2" filter="true"/>
</td>
</tr>
<tr>
<td><b>&nbsp;</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipVia.primaryAddress.address3" filter="true"/>
</td>
</tr>
<tr>
<td><b>&nbsp;</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipVia.primaryAddress.address4" filter="true"/>
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipVia.primaryAddress.city" filter="true"/>
</td>
</tr>
<tr>
<td><b>State:</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipVia.primaryAddress.stateProvinceCd" filter="true"/>
</td>
</tr>
<tr>
<td><b>ZIP Code:</b></td>
<td><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipVia.primaryAddress.postalCode" filter="true"/>
</td>
</tr>
	</table>

</logic:present>

