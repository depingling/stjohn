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

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<script language="JavaScript1.2">
    <!--
    
     
function actionSubmit(form_id, action) {
    var form = document.getElementById(form_id);
    var actions = form["action"];
    //alert(actions.length  + " form_id = " + form_id + " action = " + action + " form:" + form);
    for(ii = actions.length-1; ii >= 0; ii--) {
        if(actions[ii].value == 'hiddenAction') {
            actions[ii].value = action;
            form.submit();
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
<% } %>

<html:html>
<body>
<bean:define id="theForm"
                 name="USER_WORK_ORDER_MGR_FORM"
                 type="com.cleanwise.view.forms.UserWorkOrderMgrForm"/>
                 
<table  id="TopWorkOrderSearchTable" width="100%"
        align="center"
        border="0"
        cellpadding="0"
        cellspacing="0">
<tr>
    <td width="80%" align="center" valign="top">
        <table border="0" cellpadding="0" cellspacing="4" width="100%">
            <tr>
                <td colspan="5">
                    <jsp:include flush='true' page="t_serviceProviderSiteLocate.jsp">
                       <jsp:param name="jspFormAction" value="/serviceproviderportal/workOrderSearch.do" /> 
                       <jsp:param name="jspFormName" value="USER_WORK_ORDER_MGR_FORM" /> 
                       <jsp:param name="jspSubmitIdent" value="" /> 
                       <jsp:param name="jspReturnFilterProperty" value="filterSites" /> 
                    </jsp:include>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
    <html:form  name="USER_WORK_ORDER_MGR_FORM" action="/serviceproviderportal/workOrderSearch.do"
                styleId="11111"
                scope="session"
                type="com.cleanwise.view.forms.UserWorkOrderMgrForm">

    <logic:present name="<%=Constants.APP_USER%>">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td colspan="5">&nbsp;</td>
            </tr>
            
            <tr>
                <td>&nbsp;</td>
                <td width="15"></td>
                <td>
                    <jsp:include flush='true' page="t_serviceProviderSiteLocateButtons.jsp">
                        <jsp:param name="jspFormName" value="USER_WORK_ORDER_MGR_FORM" /> 
                        <jsp:param name="jspReturnFilterProperty" value="locateStoreSiteForm.sitesToReturn" />
                    </jsp:include>
                </td>
                <td colspan="2"></td>
            </tr>
            
            <tr>
                <td colspan="5">&nbsp;</td>
            </tr>
            
            <tr>
                <td align="right">
                    <b><app:storeMessage key="userWorkOrder.text.requestedService"/>:</b>
                </td>
                <td width="15"></td>
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
                <td align="right">
                    <b><app:storeMessage key="userWorkOrder.text.workOrderNumber"/>:</b>
                </td>
                <td width="15"></td>
                <td>
                    <html:text name="USER_WORK_ORDER_MGR_FORM" property="workOrderNumber"/>
                </td>
                <td colspan="2">
                </td>
            </tr>
            <tr>
                <td align="right">
                    <b><app:storeMessage key="userWorkOrder.text.PONumber"/>:</b>
                </td>
                <td width="15"></td>
                <td>
                    <html:text name="USER_WORK_ORDER_MGR_FORM" property="poNumber"/>
                </td>
                <td colspan="2">
                </td>
            </tr>
            
            <tr>
                <td align="right">
                    <b><app:storeMessage key="userWorkOrder.text.type"/>:</b>
                </td>
                <td width="15"></td>
                <td>
                    <html:select name="USER_WORK_ORDER_MGR_FORM" property="type">
                        <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.type.ANY");%>
                        <html:option value="">
                            <% if (messageKey != null) { %>
                                <%=messageKey%>
                            <% } else { %>
                                Any
                            <% } %>
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
                <td colspan="2">
                </td>
            </tr>
            
            <tr>
                <td align="right">
                    <b><app:storeMessage key="userWorkOrder.text.status"/>:</b>
                </td>
                <td width="15"></td>
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
                <td colspan="2">
                </td>
            </tr>
            
            <tr>
                <td align="right">
                    <b><app:storeMessage key="userWorkOrder.text.priority"/>:</b>
                </td>
                <td width="15"></td>
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
                <td colspan="2">
                </td>
            </tr>
            
            <tr>
                <td align="right">
                    <b>
                        <nobr>
                            <app:storeMessage key="userWorkOrder.text.actualBeginDate"/>:
                            (<%=ClwI18nUtil.getUIDateFormat(request)%>)
                        </nobr>
                    </b>
                </td>
                <td width="15"></td>
                <td>
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
                <td colspan="2">
                </td>
            </tr>
            
            <tr>
                <td align="right">
                    <b>
                        <nobr>
                            <app:storeMessage key="userWorkOrder.text.actualEndDate"/>:
                            (<%=ClwI18nUtil.getUIDateFormat(request)%>)
                        </nobr>
                    </b>
                </td>
                <td width="15"></td>
                <td> <html:text size="15" name="USER_WORK_ORDER_MGR_FORM" property="actualEndDate"/>
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
                <td colspan="2">
                </td>
            </tr>
            
            <tr>
                <td align="right">
                    <b>
                        <nobr>
                            <app:storeMessage key="userWorkOrder.text.estimateBeginDate"/>:
                            (<%=ClwI18nUtil.getUIDateFormat(request)%>)
                        </nobr>
                    </b>
                </td>
                <td width="15"></td>
                <td>
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
                <td colspan="2">
                </td>
            </tr>
            
            <tr>
                <td align="right">
                    <b>
                        <nobr>
                            <app:storeMessage key="userWorkOrder.text.estimateEndDate"/>:
                            (<%=ClwI18nUtil.getUIDateFormat(request)%>)
                        </nobr>
                    </b>
                </td>
                <td width="15"></td>
                <td> <html:text size="15" name="USER_WORK_ORDER_MGR_FORM" property="estimateEndDate"/>
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
                <td colspan="2">
                </td>
            </tr>
            
            <tr>
                <td colspan="5"><br></td>
            </tr>
            <tr>
                <td colspan="5" align="center">
                    <html:button property="action" styleClass="store_fb" onclick="actionSubmit('11111','WorkOrderSearch');">
                        <app:storeMessage key="global.action.label.search"/>
                    </html:button>
                </td>
            </tr>
            <tr>
                <td colspan="5">&nbsp;</td>
            </tr>
        </table>
    </logic:present>
    <html:hidden property="action" value="hiddenAction"/>
    <html:hidden property="tabs" value="f_serviceProviderWorkOrderToolbar"/>
    <html:hidden property="display" value="t_serviceProviderWorkOrder"/>
    <html:hidden property="status" value="Send To Provider"/>
    </html:form>
    </td>
</tr>

<tr align="center" valign="top">
    <td colspan="4">
    <logic:present name="USER_WORK_ORDER_MGR_FORM" property="searchResult">
    <bean:size id="rescount" name="USER_WORK_ORDER_MGR_FORM" property="searchResult"/>
        <table width="100%" CELLSPACING=0 CELLPADDING=0>
        <tr>
			<td class="shopcharthead" align="center">
				<a class="workordersearchlinks" href="../serviceproviderportal/workOrderSearch.do?action=sort_workorders&sortField=woSite&tabs=f_serviceProviderWorkOrderToolbar&display=t_serviceProviderWorkOrder">
		            <div class="fivemargin">
		                <app:storeMessage key="userlocate.site.text.siteName"/>
		            </div>
				</a>
			</td>
            <td class="shopcharthead" align="center">
				<a class="workordersearchlinks" href="../serviceproviderportal/workOrderSearch.do?action=sort_workorders&sortField=woNumber&tabs=f_serviceProviderWorkOrderToolbar&display=t_serviceProviderWorkOrder">
	                <div class="fivemargin">
	                    <app:storeMessage key="userWorkOrder.text.workOrderNumber"/>
	                </div>
				</a>
            </td>
            <td class="shopcharthead" align="center">
				<a class="workordersearchlinks" href="../serviceproviderportal/workOrderSearch.do?action=sort_workorders&sortField=woPoNumber&tabs=f_serviceProviderWorkOrderToolbar&display=t_serviceProviderWorkOrder">
	                <div class="fivemargin">
	                    <app:storeMessage key="userWorkOrder.text.PONumber"/>
	                </div>
				</a>
            </td>
            <td class="shopcharthead" align="center">
				<a class="workordersearchlinks" href="../serviceproviderportal/workOrderSearch.do?action=sort_workorders&sortField=woRequestedService&tabs=f_serviceProviderWorkOrderToolbar&display=t_serviceProviderWorkOrder">
	                <div class="fivemargin">
	                    <app:storeMessage key="userWorkOrder.text.workOrderName"/>
	                </div>
				</a>
            </td>
            <td class="shopcharthead" align="center">
				<a class="workordersearchlinks" href="../serviceproviderportal/workOrderSearch.do?action=sort_workorders&sortField=woType&tabs=f_serviceProviderWorkOrderToolbar&display=t_serviceProviderWorkOrder">
	                <div class="fivemargin">
	                    <app:storeMessage key="userWorkOrder.text.type"/>
	                </div>
				</a>
            </td>
            <td class="shopcharthead" align="center">
				<a class="workordersearchlinks" href="../serviceproviderportal/workOrderSearch.do?action=sort_workorders&sortField=woPriority&tabs=f_serviceProviderWorkOrderToolbar&display=t_serviceProviderWorkOrder">
	                <div class="fivemargin">
	                    <app:storeMessage key="userWorkOrder.text.priority"/>
	                </div>
				</a>
            </td>
            <td class="shopcharthead" align="center">
				<a class="workordersearchlinks" href="../serviceproviderportal/workOrderSearch.do?action=sort_workorders&sortField=woStatus&tabs=f_serviceProviderWorkOrderToolbar&display=t_serviceProviderWorkOrder">
	                <div class="fivemargin">
	                    <app:storeMessage key="userWorkOrder.text.status"/>
	                </div>
				</a>
            </td>
            <td class="shopcharthead" align="center">
				<a class="workordersearchlinks" href="../serviceproviderportal/workOrderSearch.do?action=sort_workorders&sortField=woActualStartDate&tabs=f_serviceProviderWorkOrderToolbar&display=t_serviceProviderWorkOrder">
	                <div class="fivemargin">
	                    <app:storeMessage key="userWorkOrder.text.actualStartDate"/>
	                </div>
				</a>
            </td>
            <td class="shopcharthead" align="center">
				<a class="workordersearchlinks" href="../serviceproviderportal/workOrderSearch.do?action=sort_workorders&sortField=woActualFinishDate&tabs=f_serviceProviderWorkOrderToolbar&display=t_serviceProviderWorkOrder">
	                <div class="fivemargin">
	                    <app:storeMessage key="userWorkOrder.text.actualFinishDate"/>
	                </div>
				</a>
            </td>
        </tr>
        <logic:iterate  id="arrele"
                        name="USER_WORK_ORDER_MGR_FORM"
                        property="searchResult"
                        indexId="i"
                        type="com.cleanwise.service.api.value.WorkOrderSiteNameView">
        <bean:define id="eleid" name="arrele" property="workOrder.workOrderId"/>
        <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
			<td align="center" class="fivemargin">
                <bean:write name="arrele" property="siteName"/>
            </td>
            <td align="center" class="fivemargin">
                <bean:write name="arrele" property="workOrder.workOrderNum"/>
            </td>
            <td align="center" class="fivemargin">
                <bean:write name="arrele" property="workOrder.poNumber"/>
            </td>
            <td align="center" class="fivemargin">
                <logic:present name="arrele" property="workOrder.shortDesc">
                    <a href="../serviceproviderportal/workOrderDetail.do?action=detail&workOrderId=<%=eleid%>&tabs=f_serviceProviderToolbar&display=t_serviceProviderWorkOrder">
                        <bean:write name="arrele" property="workOrder.shortDesc"/>
                    </a>
                </logic:present>
            </td>
            <td align="center" class="fivemargin">
                <logic:present name="arrele" property="workOrder.typeCd">
                    <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.type."+((String)arrele.getWorkOrder().getTypeCd()).toUpperCase());
                        if(messageKey!=null){%>
                    <%=messageKey%>
                    <%} else{%>
                    <%=arrele.getWorkOrder().getTypeCd()%>
                    <%}%>
                </logic:present>
            </td>
            <td align="center" class="fivemargin">
                <logic:present name="arrele" property="workOrder.priority">
                    <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.priority."+((String)arrele.getWorkOrder().getPriority()).toUpperCase());
                        if(messageKey!=null){%>
                    <%=messageKey%>
                    <%} else{%>
                    <%=arrele.getWorkOrder().getPriority()%>
                    <%}%>
                </logic:present>
            </td>
            <td align="center" class="fivemargin">
                <logic:present name="arrele" property="workOrder.statusCd">
                    <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWorkOrder.text.status."+((String)arrele.getWorkOrder().getStatusCd()).toUpperCase());
                        if(messageKey!=null){%>
                    <%=messageKey%>
                    <%} else{%>
                    <%=arrele.getWorkOrder().getStatusCd()%>
                    <%}%>
                </logic:present>
            </td>
            <td align="center" class="fivemargin">
                <logic:present name="arrele" property="workOrder.actualStartDate">
                    <%=ClwI18nUtil.formatDate(request, arrele.getWorkOrder().getActualStartDate(), DateFormat.DEFAULT)%>
                </logic:present>
            </td>
            <td align="center" class="fivemargin">
                <logic:present name="arrele" property="workOrder.actualFinishDate">
                    <%=ClwI18nUtil.formatDate(request, arrele.getWorkOrder().getActualFinishDate(), DateFormat.DEFAULT)%>
                </logic:present>
            </td>
        </tr>
        </logic:iterate>
        </table>
    </logic:present>
    </td>
</tr>
</table>
</body>
</html:html>