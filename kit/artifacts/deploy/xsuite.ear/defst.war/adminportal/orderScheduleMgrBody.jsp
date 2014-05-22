<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}
//-->
</script>
<%
  String browser = (String)  request.getHeader("User-Agent");
	String isMSIE = "";
  if(browser!=null && browser.indexOf("MSIE")>=0) {
	   isMSIE = "Y";
%>
    <script language="JavaScript" src="../externals/calendar.js"></script>
    <iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
       marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% } else {  %>
    <script language="JavaScript" src="../externals/calendarNS.js"></script>
<% }  %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="ORDER_SCHEDULE_MGR_FORM" type="com.cleanwise.view.forms.OrderScheduleMgrForm"/>

<div class="text">
  <table width="769" cellspacing="0" border="0" class="mainbody">
  <html:form name="ORDER_SCHEDULE_MGR_FORM" action="adminportal/orderschedulemgr.do"
    scope="session" type="com.cleanwise.view.forms.OrderScheduleMgrForm">
   <tr> <td><b>Account Id:</b></td>
   <td>
   <html:text name="ORDER_SCHEDULE_MGR_FORM" property="searchAccountId" size="5"/>
   <html:button onclick="return popLocate('accountlocate', 'searchAccountId', 'searchAccountName');"
    value="Locate Account" property="action" />
   </td>
   <td><b>Account&nbsp;Name:</b></td>
    <td>
    <html:text name="ORDER_SCHEDULE_MGR_FORM" property="searchAccountName" readonly="true" styleClass="mainbodylocatename"/>
    </td>
   </tr>
   <tr> <td><b>Site Id:</b></td>
   <td>
   <html:text name="ORDER_SCHEDULE_MGR_FORM" property="searchSiteId" size="5"/>
   <html:button onclick="return popLocate('sitelocate', 'searchSiteId', 'searchSiteName');"
    value="Locate Site" property="action" />
   </td>
   <td><b>Site&nbsp;Name:</b></td>
    <td>
    <html:text name="ORDER_SCHEDULE_MGR_FORM" property="searchSiteName"/>
    </td>
   </tr>
   <tr>
   <td><b>Order Guide Name:</b></td>
   <td>
   <html:text name="ORDER_SCHEDULE_MGR_FORM" property="searchOrderGuideName"/>
   </td>
   <td><b>Schedule Action:</b></td>
   <td>
   <html:select name="ORDER_SCHEDULE_MGR_FORM" property="searchOrderScheduleType">
   <html:option value=" "></html:option>
   <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY%>">Notify</html:option>
   <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_CD.PLACE_ORDER%>">Place Order</html:option>
   </html:select>
   </td>
   </tr>
   <tr>
   <td><b>Schedule Type:</b></td>
   <td>
   <html:select name="ORDER_SCHEDULE_MGR_FORM" property="searchOrderScheduleRuleType">
   <html:option value=" "></html:option>
   <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK%>">Weekly Schedule</html:option>
   <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH%>">Monthly Day Schedule</html:option>
   <html:option value="<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH%>">Monthly Week Schedule</html:option>
   </html:select>
   </td>
   <td><b>Scheduled Order Date:</b></td>
   <td>
   <html:text name="ORDER_SCHEDULE_MGR_FORM" property="searchDateFor" size="10"/>
    <% if ("Y".equals(isMSIE)) { %>
			<a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].STARTDATE, document.forms[0].searchDateFor, null, -7300, 7300);" title="Choose Date"
     	><img name="STARTDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
    <% } else {  %>
	  	<a href="javascript:show_calendar('forms[0].searchDateFor');" onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;" title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
    <% }  %>
   </td>
  <tr> <td>&nbsp;</td>
    <td colspan="3">
    <html:radio name="ORDER_SCHEDULE_MGR_FORM" property="searchType" value="<%=\"\"+SearchCriteria.BEGINS_WITH_IGNORE_CASE%>" />
    Starts with
    <html:radio name="ORDER_SCHEDULE_MGR_FORM" property="searchType" value="<%=\"\"+SearchCriteria.CONTAINS_IGNORE_CASE%>" />
    Contains
    </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.create"/>
	</html:submit> (Store id must be provided to create new order schedule)
     </html:form>
    </td>
  </tr>
</table>
<logic:present name="ORDER_SCHEDULE_MGR_FORM" property="orderSchedules">
<bean:size id="rescount"  name="ORDER_SCHEDULE_MGR_FORM" property="orderSchedules"/>
<% Integer n = (Integer)rescount; %>
<% if (n.intValue() < Constants.MAX_ORDER_SCHEDULES_TO_RETURN) { %>
Search result count:  <bean:write name="rescount" />
<% } else { %>
Search results restricted to maximum of <bean:write name="rescount" />.
Narrow search criteria.
<% } %>

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>

<table cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td class="subheaders"><a href="orderschedulemgr.do?action=sort&sortField=orderScheduleId"><b>Id</b></td>
<td class="subheaders"><a href="orderschedulemgr.do?action=sort&sortField=accountName"><b>Account</b></td>
<td class="subheaders"><a href="orderschedulemgr.do?action=sort&sortField=siteName"><b>Site</b></td>
<td class="subheaders"><a href="orderschedulemgr.do?action=sort&sortField=orderGuideName"><b>Order Guide</b></td>
<td class="subheaders"><a href="orderschedulemgr.do?action=sort&sortField=effDate"><b>Effective Date</b></td>
<td class="subheaders"><a href="orderschedulemgr.do?action=sort&sortField=expDate"><b>Expiration Date</b></td>
<td class="subheaders"><a href="orderschedulemgr.do?action=sort&sortField=orderScheduleCd"><b>Action</b></td>
<td class="subheaders"><a href="orderschedulemgr.do?action=sort&sortField=orderScheduleRuleCd"><b>Schedule Type</b></td>
</tr>
<logic:iterate id="arrele" name="ORDER_SCHEDULE_MGR_FORM" property="orderSchedules"
 type="com.cleanwise.service.api.value.OrderScheduleView">
<pg:item>
<bean:define id="eleid" name="arrele" property="orderScheduleId"/>
<tr>
<td><bean:write name="arrele" property="orderScheduleId"/></td>
<td><bean:write name="arrele" property="accountName"/></td>
<td><bean:write name="arrele" property="siteName"/></td>
<td>
<a href="orderschedulemgr.do?action=detail&scheduleId=<%=eleid%>">
<bean:write name="arrele" property="orderGuideName"/>
</a>
</td>
<td><bean:write name="arrele" property="effDate"/></td>
<td><bean:write name="arrele" property="expDate"/></td>
<td><bean:write name="arrele" property="orderScheduleCd"/></td>
<td><bean:write name="arrele" property="orderScheduleRuleCd"/></td>
</tr>

</pg:item>
</logic:iterate>
</table>
  <pg:index>
    Result Pages:
    <pg:prev>&nbsp;<a href="<%= pageUrl %>">[<< Prev]</a></pg:prev>
    <pg:pages><%
      if (pageNumber.intValue() < 10) {
        %>&nbsp;<%
      }
      if (pageNumber == pagerPageNumber) {
        %><b><%= pageNumber %></b><%
      } else {
        %><a href="<%= pageUrl %>"><%= pageNumber %></a><%
      }
    %>
    </pg:pages>
    <pg:next>&nbsp;<a href="<%= pageUrl %>">[Next >>]</a></pg:next>
    <br></font>
  </pg:index>
</pg:pager>
</logic:present>
</div>


