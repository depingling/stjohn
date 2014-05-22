<%--
 Date: 16.09.2007
 Time: 19:06:29
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.ServiceProviderData" %>
<%@ page import="com.cleanwise.service.api.value.WorkOrderData" %>
<%@ page import="com.cleanwise.view.forms.UserWorkOrderMgrForm" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%@include file="/externals/calendar/calendar.jsp"%>

<script language="JavaScript1.2">
    <!--
    function actionSubmit(formNum, action,fwdPage,sbar) {
     var actions;
     var disp
     var secBar;
     actions=document.forms[formNum]["action"];
     disp=document.forms[formNum]["display"];
     secBar=document.forms[formNum]["secondaryToolbar"];

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
<%
    String ZERO = String.valueOf(0);
    String messageKey;
	
	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	boolean showSiteNames = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER);
%>
<%
    String browser = (String) request.getHeader("User-Agent");
    String isMSIE = "";
    if (browser != null && browser.indexOf("MSIE") >= 0) {
        isMSIE = "Y";
%>
<script language="JavaScript" src="../externals/calendar.js"></script>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
        marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% } else { %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% } %>
<html:html>
<body>
<table width="100%" border="0" align=center CELLPADDING=0 CELLSPACING=0>
<bean:define id="theForm" name="USER_WORK_ORDER_MGR_FORM" type="com.cleanwise.view.forms.UserWorkOrderMgrForm"/>
<html:form name="USER_WORK_ORDER_MGR_FORM" action="/userportal/userWorkOrder.do"
           scope="session"
           type="com.cleanwise.view.forms.UserWorkOrderMgrForm">

<logic:present name="<%=Constants.APP_USER%>">
<logic:greaterThan name="<%=Constants.APP_USER%>" property="siteNumber" value="0">

<tr>
<td width="80%" align="center" valign="top">
<table border="0" cellpadding="0" cellspacing="1" width="100%">
<tr>
    <td style="padding-left: 5px;">
        <b><app:storeMessage key="userWorkOrder.text.requestedService"/>:</b>
    </td>
    <td>
        <html:text styleId="searchField" name="USER_WORK_ORDER_MGR_FORM" property="searchField"/>
    </td>
    <td colspan="2" nowrap="nowrap">
        <html:radio name="USER_WORK_ORDER_MGR_FORM" property="searchType"
                    value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
        <app:storeMessage key="global.label.nameStartsWith"/>
        <html:radio name="USER_WORK_ORDER_MGR_FORM" property="searchType"
                    value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
        <app:storeMessage key="global.label.nameContains"/>
    </td>
</tr>
<tr>
    <td style="padding-left: 5px;">
        <b><app:storeMessage key="userWorkOrder.text.workOrderNumber"/>:</b>
    </td>
    <td>
        <html:text name="USER_WORK_ORDER_MGR_FORM" property="workOrderNumber"/>
    </td>
    <td>
        <b><app:storeMessage key="userWorkOrder.text.status"/>:</b>
    </td>
    <td>
        <html:select name="USER_WORK_ORDER_MGR_FORM" property="status">
            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.status.ANY");%>
            <html:option value="">
                <% if (messageKey != null) {%>
                <%=messageKey%>
                <%} else {%>
                Any
                <%}%>
            </html:option>
            <logic:present name="WorkOrder.user.status.vector">
                <logic:iterate id="statusCd" name="WorkOrder.user.status.vector"
                               type="com.cleanwise.service.api.value.RefCdData">
                    <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.status." + ((String)statusCd.getValue()).toUpperCase());%>
                    <html:option value="<%=statusCd.getValue()%>">
                        <% if (messageKey != null) {%>
                        <%=messageKey%>
                        <%} else {%>
                        <%=statusCd.getValue()%>
                        <%}%>
                    </html:option>
                </logic:iterate>
            </logic:present>
        </html:select>
    </td>
</tr>
<tr>
    <td style="padding-left: 5px;">
        <b><app:storeMessage key="userWorkOrder.text.PONumber"/>:</b>
    </td>
    <td>
        <html:text name="USER_WORK_ORDER_MGR_FORM" property="poNumber"/>
    </td>
    <td>
        <b><app:storeMessage key="userWorkOrder.text.priority"/>:</b>
    </td>
    <td>
        <html:select name="USER_WORK_ORDER_MGR_FORM" property="priority">
            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.priority.ANY");%>
            <html:option value="">
                <% if (messageKey != null) {%>
                <%=messageKey%>
                <%} else {%>
                Any
                <%}%>
            </html:option>
            <logic:present name="WorkOrder.user.priority.vector">
                <logic:iterate id="priorityCd" name="WorkOrder.user.priority.vector"
                               type="com.cleanwise.service.api.value.RefCdData">
                    <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.priority." + ((String)priorityCd.getValue()).toUpperCase());%>
                    <html:option value="<%=priorityCd.getValue()%>">
                        <% if (messageKey != null) {%>
                        <%=messageKey%>
                        <%} else {%>
                        <%=priorityCd.getValue()%>
                        <%}%>
                    </html:option>
                </logic:iterate>
            </logic:present>
        </html:select>
    </td>
</tr>

<logic:equal name="USER_WORK_ORDER_MGR_FORM"  property="displayDistributorAccountReferenceNumber" value="true">
	<tr>
	    <td style="padding-left: 5px;">
	        <b><app:storeMessage key="userWorkOrder.text.DistributorAccountNumber"/>:</b>
	    </td>
	    <td>
	        <html:text name="USER_WORK_ORDER_MGR_FORM" property="distributorAccountNumber"/>
	    </td>
	</tr>
</logic:equal>

<logic:equal name="USER_WORK_ORDER_MGR_FORM"  property="displayDistributorSiteReferenceNumber" value="true">
	<tr>
	    <td style="padding-left: 5px;">
	        <b><app:storeMessage key="userWorkOrder.text.DistributorShipToLocationNumber"/>:</b>
	    </td>
	    <td>
	        <html:text name="USER_WORK_ORDER_MGR_FORM" property="distributorShipToLocationNumber"/>
	    </td>
	</tr>
</logic:equal>
<tr>
    <td style="padding-left: 5px;">
        <b><app:storeMessage key="userWorkOrder.text.type"/>:</b>
    </td>
    <td>
        <html:select name="USER_WORK_ORDER_MGR_FORM" property="type">
            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.type.ANY");%>
            <html:option value="">
                <% if (messageKey != null) {%>
                <%=messageKey%>
                <%} else {%>
                Any
                <%}%>
            </html:option>
            <logic:present name="WorkOrder.user.type.vector">
                <logic:iterate id="serviceProviderType" name="WorkOrder.user.type.vector"
                               type="com.cleanwise.service.api.value.BusEntityData">
                    <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.type." + (serviceProviderType.getShortDesc()).toUpperCase());%>
                    <html:option value="<%=serviceProviderType.getShortDesc()%>">
                        <% if (messageKey != null) {%>
                        <%=messageKey%>
                        <%} else {%>
                        <%=serviceProviderType.getShortDesc()%>
                        <%}%>
                    </html:option>
                </logic:iterate>
            </logic:present>
        </html:select>
    </td>
    <td colspan="2"></td>
</tr>
<tr>
    <td style="padding-left: 5px;">
        <b><app:storeMessage key="userWorkOrder.text.serviceProvider"/>:</b>
    </td>
    <td colspan="3">
        <html:select name="USER_WORK_ORDER_MGR_FORM" property="providerIdStr">
            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.search.text.provider.ANY");%>
            <html:option value="">
                <% if (messageKey != null) {%>
                <%=messageKey%>
                <%} else {%>
                Any
                <%}%>
            </html:option>
            <logic:present name="WorkOrder.user.service.provider.vector">
                <logic:iterate id="provider" name="WorkOrder.user.service.provider.vector"
                               type="com.cleanwise.service.api.value.ServiceProviderData">
                    <html:option value="<%=String.valueOf(((ServiceProviderData)provider).getBusEntity().getBusEntityId())%>">
                        <%=((ServiceProviderData)provider).getBusEntity().getShortDesc()%>
                    </html:option>
                </logic:iterate>
            </logic:present>
        </html:select>
    </td>
</tr>
<tr>
    <td style="padding-left: 5px;">
        <b>
            <nobr>
                <app:storeMessage key="userWorkOrder.text.actualStartDate"/>:
                (<%=ClwI18nUtil.getUIDateFormat(request)%>)
            </nobr>
        </b>
    </td>
    <td colspan="3" align="left">
        <html:text size="15" name="USER_WORK_ORDER_MGR_FORM" property="actualBeginDate"/>
        <% if ("Y".equals(isMSIE)) { %>
        <a href="#"
           onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ACTUALBEGINDATE, document.forms[0].actualBeginDate, null, -7300, 7300,null,-1);"
           title="Choose Date"><img name="ACTUALBEGINDATE" src="../externals/images/showCalendar.gif" width=19 height=19
                                    border=0
                                    style="position:relative"
                                    onmouseover="window.status='Choose Date';return true"
                                    onmouseout="window.status='';return true"></a>
        <% } else { %>
            <a href="#" onClick="showCalendar('actualBeginDate', event); return false;"
                        title="Choose Date">
                <img name="ACTUALBEGINDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                     onmouseover="window.status='Choose Date';return true"
                     onmouseout="window.status='';return true">
            </a>
        <% } %>
    </td>
</tr>

<tr>
    <td style="padding-left: 5px;">
        <b>
            <nobr>
                <app:storeMessage key="userWorkOrder.text.actualFinishDate"/>:
                (<%=ClwI18nUtil.getUIDateFormat(request)%>)
            </nobr>
        </b>
    </td>
    <td colspan="3" align="left">
        <html:text size="15" name="USER_WORK_ORDER_MGR_FORM" property="actualEndDate"/>
        <% if ("Y".equals(isMSIE)) { %>
        <a href="#"
           onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ACTUALENDDATE, document.forms[0].actualEndDate, null, -7300, 7300,null,-1);"
           title="Choose Date"><img name="ACTUALENDDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                                    style="position:relative"
                                    onmouseover="window.status='Choose Date';return true"
                                    onmouseout="window.status='';return true"></a>
        <% } else { %>
            <a href="#" onClick="showCalendar('actualEndDate', event); return false;"
               title="Choose Date">
                <img name="ACTUALENDDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                     onmouseover="window.status='Choose Date';return true"
                     onmouseout="window.status='';return true">
            </a>
        <% } %>
    </td>
</tr>

<tr>
    <td style="padding-left: 5px;">
        <b>
            <nobr>
                <app:storeMessage key="userWorkOrder.text.quotationStartDate"/>:
                (<%=ClwI18nUtil.getUIDateFormat(request)%>)
            </nobr>
        </b>
    </td>
    <td colspan="3" align="left">
        <html:text size="15" name="USER_WORK_ORDER_MGR_FORM" property="estimateBeginDate"/>
        <% if ("Y".equals(isMSIE)) { %>
        <a href="#"
           onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ESTIMATEBEGINDATE, document.forms[0].estimateBeginDate, null, -7300, 7300,null,-1);"
           title="Choose Date"><img name="ESTIMATEBEGINDATE" src="../externals/images/showCalendar.gif" width=19 height=19
                                    border=0
                                    style="position:relative"
                                    onmouseover="window.status='Choose Date';return true"
                                    onmouseout="window.status='';return true"></a>
        <% } else { %>
            <a href="#" onClick="showCalendar('estimateBeginDate', event); return false;"
               title="Choose Date">
                <img name="ESTIMATEBEGINDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                     onmouseover="window.status='Choose Date';return true"
                     onmouseout="window.status='';return true">
            </a>
        <% } %>
    </td>
</tr>

<tr>
    <td style="padding-left: 5px;">
        <b>
            <nobr>
                <app:storeMessage key="userWorkOrder.text.poReceivedDate"/>:
                (<%=ClwI18nUtil.getUIDateFormat(request)%>)
            </nobr>
        </b>
    </td>
    <td colspan="3" align="left">
        <html:text size="15" name="USER_WORK_ORDER_MGR_FORM" property="estimateEndDate"/>
        <% if ("Y".equals(isMSIE)) { %>
        <a href="#"
           onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].ESTIMATEENDDATE, document.forms[0].estimateEndDate, null, -7300, 7300,null,-1);"
           title="Choose Date"><img name="ESTIMATEENDDATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                                    style="position:relative"
                                    onmouseover="window.status='Choose Date';return true"
                                    onmouseout="window.status='';return true"></a>
        <% } else { %>
            <a href="#" onClick="showCalendar('estimateEndDate', event); return false;"
               title="Choose Date">
                    <img name="ESTIMATEENDDATE" src="../externals/images/showCalendar.gif" width="19" height="19" border="0" align="absmiddle" style="position:relative"
                         onmouseover="window.status='Choose Date';return true"
                         onmouseout="window.status='';return true">
            </a>
        <% } %>
    </td>
</tr>
<tr>
    <td style="padding-left: 5px;">&nbsp;</td>
    <td colspan="3" align="left">
        <html:checkbox name="USER_WORK_ORDER_MGR_FORM" property="showCurrentSiteOnly"/>
        &nbsp;
        <app:storeMessage key="userAssets.text.currentSiteOnly"/>
    </td>
</tr>
<tr>
    <td colspan="4"><br></td>
</tr>
<tr>
    <td colspan="4" align="center">
        <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'Search','t_userWorkOrder','');">
            <app:storeMessage key="global.action.label.search"/>
        </html:button>
        <html:button property="action" styleClass="store_fb" onclick="actionSubmit(0,'create','t_userWorkOrderDetail','f_userSecondaryToolbar');">
            <app:storeMessage key="global.label.createNewWorkOrder"/>
        </html:button>
    </td>
</tr>
<tr>
    <td colspan="4">&nbsp;</td>
</tr>
</table></td>
<td width="20%" align="left" valign="middle">
    <table width="100%" border="0" align="left" cellpadding="0" cellspacing="1">

        <tr>
            <td align="center"><b>
                <app:storeMessage key="userWorkOrder.text.workOrderQueue"/>
            </b></td>
        </tr>

        <logic:present name="USER_WORK_ORDER_MGR_FORM" property="queueStatistic">

            <%
                Integer low    = (Integer) ((HashMap) ((UserWorkOrderMgrForm) theForm).getQueueStatistic()).get(RefCodeNames.WORK_ORDER_PRIORITY_CD.LOW);
                Integer medium = (Integer) ((HashMap) ((UserWorkOrderMgrForm) theForm).getQueueStatistic()).get(RefCodeNames.WORK_ORDER_PRIORITY_CD.MEDIUM);
                Integer high   = (Integer) ((HashMap) ((UserWorkOrderMgrForm) theForm).getQueueStatistic()).get(RefCodeNames.WORK_ORDER_PRIORITY_CD.HIGH);
            %>

            <tr>
                <td align="center">
                    <div class="queueStatisticPriorityLow"
                         title="<%=(Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWorkOrder.text.priority.LOW"))+
                                                  " "+Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWorkOrder.text.priority")).toLowerCase())%>">
                        <%if (low != null && low.intValue() > 0) {%>
                        <a href="../userportal/userWorkOrder.do?action=searchByPriority&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar&priority=<%=RefCodeNames.WORK_ORDER_PRIORITY_CD.LOW%>"> <%=low%> </a>
                        <%} else {%>
                        <%=ZERO%>
                        <%}%>
                    </div></td>
            </tr>
            <tr>
                <td align="center">
                    <div class="queueStatisticPriorityMedium" title="<%=(Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWorkOrder.text.priority.MEDIUM"))+
                                                  " "+Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWorkOrder.text.priority")).toLowerCase())%>">
                        <%if (medium != null && medium.intValue() > 0) {%>
                        <a href="../userportal/userWorkOrder.do?action=searchByPriority&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar&priority=<%=RefCodeNames.WORK_ORDER_PRIORITY_CD.MEDIUM%>"> <%=medium%> </a>
                        <%} else {%>
                        <%=ZERO%>
                        <%}%>
                    </div></td>
            </tr>
            <tr>
                <td align="center">
                    <div class="queueStatisticPriorityHigh" title="<%=(Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWorkOrder.text.priority.HIGH"))+
                                                  " "+Utility.strNN(ClwI18nUtil.getMessageOrNull(request,"userWorkOrder.text.priority")).toLowerCase())%>">
                        <%if (high != null && high.intValue() > 0) {%>
                        <a href="../userportal/userWorkOrder.do?action=searchByPriority&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar&priority=<%=RefCodeNames.WORK_ORDER_PRIORITY_CD.HIGH%>"> <%=high%> </a>
                        <%} else {%>
                        <%=ZERO%>
                        <%}%>
                    </div></td>
            </tr>
        </logic:present>
    </table>  </td>
</tr>
<tr align="center" valign="top">
<td colspan="4">
<logic:present name="USER_WORK_ORDER_MGR_FORM" property="searchResultView">
<bean:size id="rescount" name="USER_WORK_ORDER_MGR_FORM" property="searchResultView"/>
<table width="100%" CELLSPACING="0" CELLPADDING="0">
    <tr>
		<td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=SiteName&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userlocate.site.text.siteName"/>
	            </div>
			</a>
        </td>
		<logic:equal name="USER_WORK_ORDER_MGR_FORM"  property="displayDistributorSiteReferenceNumber" value="true">
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=DistributorShipToNumber&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.DistributorShipToNumber"/>
	            </div>
			</a>
        </td>
		</logic:equal>        
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=WorkOrderNum&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.workOrderNumber"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=PoNumber&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.PONumber"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=ShortDesc&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.workOrderName"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=TypeCd&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.type"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=Priority&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.priority"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=StatusCd&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.status"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=ActualStartDate&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.actualStartDate"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=ActualFinishDate&display=t_userWorkOrder&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.actualFinishDate"/>
	            </div>
			</a>
        </td>
    </tr>
    <logic:iterate id="arrele" name="USER_WORK_ORDER_MGR_FORM" property="searchResultView"
                   indexId="i" type="com.cleanwise.service.api.value.WorkOrderSearchResultView">
        <bean:define id="eleid" name="arrele" property="workOrderId"/>
        <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
			<td align="center" class="fivemargin">
                <bean:write name="arrele" property="siteName"/>
            </td>
			<logic:equal name="USER_WORK_ORDER_MGR_FORM"  property="displayDistributorSiteReferenceNumber" value="true">
            <td align="center" class="fivemargin">
                <bean:write name="arrele" property="distributorShipToNumber"/>
            </td>
            </logic:equal>
            <td align="center" class="fivemargin">
                <bean:write name="arrele" property="workOrderNum"/>
            </td>
            <td align="center" class="fivemargin">
                <bean:write name="arrele" property="poNumber"/>
            </td>
            <td align="center" class="fivemargin">
                <logic:present name="arrele" property="shortDesc">
                    <a href="../userportal/userWorkOrderDetail.do?action=detail&workOrderId=<%=eleid%>&display=t_userWorkOrderDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar">
                        <bean:write name="arrele" property="shortDesc"/>
                    </a>
                </logic:present>
            </td>
            <td align="center" class="fivemargin">
                <logic:present name="arrele" property="typeCd">
                    <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.type."+((String)arrele.getTypeCd()).toUpperCase());
                        if(messageKey!=null){%>
                    <%=messageKey%>
                    <%} else{%>
                    <%=arrele.getTypeCd()%>
                    <%}%>
                </logic:present>
            </td>
            <td align="center" class="fivemargin">
                <logic:present name="arrele" property="priority">
                    <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.priority."+((String)arrele.getPriority()).toUpperCase());
                        if(messageKey!=null){%>
                    <%=messageKey%>
                    <%} else{%>
                    <%=arrele.getPriority()%>
                    <%}%>
                </logic:present>
            </td>
            <td align="center" class="fivemargin">
                <logic:present name="arrele" property="statusCd">
                    <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.status."+((String)arrele.getStatusCd()).toUpperCase());
                        if(messageKey!=null){%>
                    <%=messageKey%>
                    <%} else{%>
                    <%=arrele.getStatusCd()%>
                    <%}%>
                </logic:present>
            </td>
            <td align="center" class="fivemargin">
                <logic:present name="arrele" property="actualStartDate">
                    <%=ClwI18nUtil.formatDate(request, arrele.getActualStartDate(), DateFormat.DEFAULT)%>
                </logic:present>
            </td>
            <td align="center" class="fivemargin">
                <logic:present name="arrele" property="actualFinishDate">
                    <%=ClwI18nUtil.formatDate(request, arrele.getActualFinishDate(), DateFormat.DEFAULT)%>
                </logic:present>
            </td>
        </tr>
    </logic:iterate>
</table>
</logic:present>
</td>
</tr>
</logic:greaterThan></logic:present>
<html:hidden property="action" value="hiddenAction"/>
<html:hidden property="tabs" value="f_userWorkOrderToolbar"/>
<html:hidden property="display" value="t_userWorkOrder"/>
<html:hidden property="secondaryToolbar" value=""/>
</html:form>
</table>
</body>
</html:html>