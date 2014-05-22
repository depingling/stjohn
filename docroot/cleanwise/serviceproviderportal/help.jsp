<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Distributor Portal: Purchase Orders</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<div class="text">

<jsp:include flush='true' page="ui/distributorToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>


<font color=red>
<html:errors/>
</font>

<table cellpadding="2" cellspacing="0" border="0" width="769">
<tr>
<td colspan="2">
Welcome to the distributor portal.  This portal will allow you to query your purchase orders by date, po number, your invoice number, status, Cleanwise return number, or your return number (if it has been supplied to Cleanwise).  The purchase order status has the following meaning:
</td>
</tr>

<tr><td><b>CANCELLED:</b></td><td>PO has been cancelled and should not be shipped.</td></tr>
<tr><td><b>DIST_ACKD_PO:</b></td><td>Some form of acknowledgement (verbal or electronic) has been received by Cleanwise.</td></tr>
<tr><td><b>PENDING_FULFILLMENT:</b></td><td>PO is in the queue to be sent to you.</td></tr>
<tr><td><b>SENT_TO_DISTRIBUTOR:</b></td><td>PO has been sent to you.</td></tr>
<%--Don't confuse user with help for things they don't see--%>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.PO_MANIFESTING%>">
	<tr>
		<td colspan="2">
			If you are fulfilling third party freight handler orders (Smart Post, etc) you may simply click the "All Pending Manifest" button, which will bring up a list of all of the orders that are in queue to ship via the freight handler (third party freight handler) unless you have multiple branches fulfilling parcel direct orders in which case you will have to enter the ERP PO# (Cleanwise purchase order number) and click "Search".
			<br>
			In the num packages field enter the number of packages for this PO.  Click the next button when you have filled in this field for all the PO's you will be manifesting (you may do repeated searching and printing if necessary).  A PDF (Adobe Acrobat) document will open (*Note, you must have Adobe Acrobat 5 installed on the computer you will be printing the manifests from, this is free software which you may download from <a href="http://www.adobe.com">www.adobe.com</a>).  You can now print the labels directly from Adobe Acrobat.
			<br>
			You may print labels at any time, but be sure to click the "Manifest Ready To Send" button when you have finished printing all of your labels and are ready to ship them to Smart Post.  If you do not the freight handler will have problems receiving this shipment.  Please only click this button one time per shipment (for example if your shipping day is Wednesday you may print labels on Monday, Tuesday and Wednesday, but only click this button Wednesday after all your packages have been labeled.
			<br>
			Once the "Manifest Ready To Send" button has been pressed an electronic file will be sent to the freight handler for the labels that were printed. Once the label is displayed on the screen it is marked as being manifested, and will no longer show up when you press the "All Pending Manifest" button, but will be displayed when searching.
		</td>
	</tr>
</app:authorizedForFunction>

</table>

</div>

<jsp:include flush='true' page="ui/distributorFooter.jsp"/>
</body>

</html:html>
