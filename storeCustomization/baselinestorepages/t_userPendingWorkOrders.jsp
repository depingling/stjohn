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

<%
    String ZERO = String.valueOf(0);
    String messageKey;
%>


<html:html>
<body>
<table width="100%" border="0" align=center CELLPADDING=0 CELLSPACING=0>
<bean:define id="theForm" name="USER_WORK_ORDER_MGR_FORM" type="com.cleanwise.view.forms.UserWorkOrderMgrForm"/>
<html:form name="USER_WORK_ORDER_MGR_FORM" action="/userportal/userWorkOrder.do"
           scope="session"
           type="com.cleanwise.view.forms.UserWorkOrderMgrForm">

<logic:present name="<%=Constants.APP_USER%>">
<logic:greaterThan name="<%=Constants.APP_USER%>" property="siteNumber" value="0">
<tr align="center" valign="top">
<td colspan="4">
<logic:present name="USER_WORK_ORDER_MGR_FORM" property="pendingSearchResultView">
<bean:size id="rescount" name="USER_WORK_ORDER_MGR_FORM" property="pendingSearchResultView"/>
<table width="100%" CELLSPACING=0 CELLPADDING=0>
   <tr>
		<td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=SiteName&display=t_userPendingWorkOrders&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userlocate.site.text.siteName"/>
	            </div>
			</a>
        </td>
		<logic:equal name="USER_WORK_ORDER_MGR_FORM"  property="displayDistributorSiteReferenceNumber" value="true">
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=DistributorShipToNumber&display=t_userPendingWorkOrders&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.DistributorShipToNumber"/>
	            </div>
			</a>
        </td>
		</logic:equal>        
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=WorkOrderNum&display=t_userPendingWorkOrders&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.workOrderNumber"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=PoNumber&display=t_userPendingWorkOrders&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.PONumber"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=ShortDesc&display=t_userPendingWorkOrders&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.workOrderName"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=TypeCd&display=t_userPendingWorkOrders&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.type"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=Priority&display=t_userPendingWorkOrders&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.priority"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=StatusCd&display=t_userPendingWorkOrders&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.status"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=ActualStartDate&display=t_userPendingWorkOrders&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.actualStartDate"/>
	            </div>
			</a>
        </td>
        <td class="shopcharthead" align="center">
			<a class="workordersearchlinks" href="../userportal/userWorkOrder.do?action=sort_workorders&sortField=ActualFinishDate&display=t_userPendingWorkOrders&tabs=f_userWorkOrderToolbar">
	            <div class="fivemargin">
	                <app:storeMessage key="userWorkOrder.text.actualFinishDate"/>
	            </div>
			</a>
        </td>
    </tr>
    <logic:iterate id="arrele" name="USER_WORK_ORDER_MGR_FORM" property="pendingSearchResultView"
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
<html:hidden property="display" value="t_userPendingWorkOrders"/>
<html:hidden property="secondaryToolbar" value=""/>
</html:form>
</table>
</body>
</html:html>