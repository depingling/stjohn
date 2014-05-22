<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<%
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
%>
<script language="JavaScript1.2">
<!--

function f_showUpdatePane(e,o) {

	for (i=0;i<o.childNodes.length;i++) {
		// Find all div having the CSS class zpane
		if (o.childNodes[i].className=="zpane") {
			// If the div is hidden, make it visible
			if (o.childNodes[i].style.display=="none") {
				o.childNodes[i].style.display="block"
			} else if (o.childNodes[i].style.display=="block") {
				// If the div is visible, hide it
				o.childNodes[i].style.display="none"
			}
			
		}
	}

	// Stop click event propagation in upper div hierarchy	
	if (document.all) {
		// Code for IE browsers
		window.event.cancelBubble=true
	} else if (!document.all && document.getElementById) {
		// Code for Mozilla browsers
		e.stopPropagation()
	}
}


-->
</script>

<html:html>

<head>
<title>Application Administrator: Delivery Schedules</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admAccountToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/accountInfo.jsp"/>
<div class="text">
<center><font color=red><html:errors/></font></center>

<table width=769 class="results" color="gray"> <tr>

<td width=150 class="tableheader">
<a href="deliverySchedules.do?action=sort&sortField=sl_site_name">Name</a>
</td>
<td class="tableheader" width=50>
<a href="deliverySchedules.do?action=sort&sortField=sl_site_status_cd">Site Status</a>
</td>
<td width=100 class="tableheader">
<a href="deliverySchedules.do?action=sort&sortField=sl_curr_sched">Current Schedule</a>
</td>
<td width=100 class="tableheader">
<a href="deliverySchedules.do?action=sort&sortField=sl_city">City</a>
</td><td width=50 class="tableheader">
<a href="deliverySchedules.do?action=sort&sortField=sl_state">State</a>
</td><td width=50 class="tableheader">
<a href="deliverySchedules.do?action=sort&sortField=sl_postal_code">Postal Code</a>
</td><td width=100 class="tableheader">
<a href="deliverySchedules.do?action=sort&sortField=sl_county">County</a>
</td></tr>

<logic:iterate id="sds" name="account.delivery.schedules" indexId="idx"
  type="com.cleanwise.service.api.value.SiteDeliveryScheduleView">
<form action="deliverySchedules.do" name="SD_<%=sds.getSiteId()%>" method="GET">

<input type="hidden" name="storeId" 
value="<%=appUser.getUserStore().getStoreId()%>"/>

	<% if ((idx.intValue() % 2) == 0 ) { %>
<tr class="rowa">
	<% } else { %>
<tr class="rowb">
	<% } %>
<td width=150>
<%=sds.getSiteName()%>
</td>
<td width=50><%=sds.getSiteStatusCd()%></td>
<td width=100>
<%=sds.getSiteScheduleType()%>
</td><td width=100>
<%=sds.getCity()%>
</td><td width=50>
<%=sds.getState()%>
</td><td width=50>
<%=sds.getPostalCode()%>
</td><td width=100>
<%=sds.getCounty()%>
</td>
</tr>

	<% if ((idx.intValue() % 2) == 0 ) { %>
<tr class="rowa">
	<% } else { %>
<tr class="rowb">
	<% } %>

<td colspan=2> </td>
<td colspan=5> 


<select name="sched_type"/>
<option> none </option>
<option
<% if ( sds.getSiteScheduleType().startsWith("Mon")) { %>
SELECTED
<% } %>
> Monthly </option>
<option
<% if ( sds.getSiteScheduleType().startsWith("Bi")) { %>
SELECTED
<% } %>
> Bi-Monthly </option>
<option
<% if ( sds.getSiteScheduleType().startsWith("Spe")) { %>
SELECTED
<% } %>
> Specify weeks </option>
</select>
 Week 
<input type="checkbox" name="sched_week" value="1"
<% if ( sds.getWeek1ofMonth()) { %>CHECKED<% } %> >1
 <input type="checkbox" name="sched_week" value="2"
<% if ( sds.getWeek2ofMonth()) { %>CHECKED<% } %> >2
 <input type="checkbox" name="sched_week" value="3"
<% if ( sds.getWeek3ofMonth()) { %>CHECKED<% } %> >3
 <input type="checkbox" name="sched_week" value="4"
<% if ( sds.getWeek4ofMonth()) { %>CHECKED<% } %> >4
 <input type="checkbox" name="sched_week" value="Last week of month"
<% if ( sds.getLastWeekofMonth()) { %>CHECKED<% } %> >Last week of month
 
 <input name="site_id" type="hidden" value="<%=sds.getSiteId()%>"/>
 <input name="action"  type="hidden" value="update_site_del_sched"/>
 <input type="submit" value="Update"/>


 </td>
</tr>
	<% if ((idx.intValue() % 2) == 0 ) { %>
<tr class="rowa">
	<% } else { %>
<tr class="rowb">
	<% } %>
<td colspan=3> </td>
<td colspan=4>
Number of weeks between orders (1-52):
<input type="text" name="intervWeek" size="2" maxlength = "2"
value="<%=sds.getIntervWeek()%>"/>
</td>
</tr>
</form>

</logic:iterate>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>
