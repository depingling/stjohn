<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
    String display = (String) request.getParameter("display");
    
    String secondaryToolbar = (String) request.getParameter("secondaryToolbar");
    String t_templatorToolBar = ClwCustomizer.getStoreFilePath(request, "t_userTemplatorToolBar.jsp");
    String rootDir = (String) session.getAttribute("store.dir");
    String toolLinkWorkOrder = "/" + rootDir + "/serviceproviderportal/workOrderSearch.do?action=startSearch";
    String toolLinkWorkOrderItem = "/" + rootDir + "/serviceproviderportal/userWorkOrderItem.do";
    String toolLinkWorkOrderNote = "/" + rootDir + "/serviceproviderportal/userWorkOrderNote.do";
    String toolLinkWorkOrderContent="/" + rootDir + "/serviceproviderportal/userWorkOrderContent.do";
    String toolLinkWorkOrderDetail="/" + rootDir + "/serviceproviderportal/userWorkOrderDetail.do?action=view_detail";
    String toolLinkWorkOrderItems=toolLinkWorkOrderItem+"?action=init";
    String toolLinkPendingsOrers=toolLinkWorkOrder+"?action=searchPendingOrders&tabs=f_serviceProviderWorkOrderToolbar&display=t_serviceProviderPendingWorkOrders";

%>
<%if ("f_serviceProviderSecondaryToolbar".equals(secondaryToolbar)) {%>
<app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER%>">

    <jsp:include flush='true' page="t_serviceProviderTemplatorToolBar.jsp">

        <jsp:param name="display" value="<%=display%>"/>

        <jsp:param name="breadCrumbBar" value="<%=(request.getSession().getAttribute(Constants.BREAD_CRUMB_NAVIGATOR)!=null)%>"/>

        <jsp:param name="actionLink01" value="<%=toolLinkWorkOrder%>"/>
        <jsp:param name="item01" value="t_serviceProviderWorkOrder"/>
        <jsp:param name="itemLable01" value="userWorkOrder.text.findWorkOrder"/>
        <jsp:param name="tabName01" value="f_serviceProviderWorkOrderToolbar"/>
        <jsp:param name="secondaryItems01" value="t_serviceProviderWorkOrder,
                                                  t_serviceProviderWorkOrderDetail,
                                                  t_serviceProviderWorkOrderItems,
                                                  t_serviceProviderWorkOrderItemDetail,
                                                  t_serviceProviderWorkOrderNote,
                                                  t_serviceProviderWorkOrderNoteDetail,
                                                  t_serviceProviderWorkOrderContent,
                                                  t_serviceProviderWorkOrderContent,
                                                  t_serviceProviderPartsOrder"/>


        <jsp:param name="toolSecondaryToolBar01"  value="f_serviceProviderSecondaryToolbar.jsp"/>

        <jsp:param name="secondaryItem01" value="t_serviceProviderWorkOrderDetail"/>
        <jsp:param name="secondaryItemLable01" value="global.label.workOrderSummary"/>
        <jsp:param name="secondaryActionLink01" value="<%=toolLinkWorkOrderDetail%>"/>
        <jsp:param name="renderLink01" value="true"/>

        <jsp:param name="variantB" value="true"/>

        <jsp:param name="toolSecondaryToolBar02"  value="f_serviceProviderSecondaryToolbar.jsp"/>

        <jsp:param name="secondaryItem02" value="t_serviceProviderWorkOrderItems"/>
        <jsp:param name="secondaryItemLable02" value="userWorkOrder.text.toolbar.workOrderItems"/>
        <jsp:param name="secondaryActionLink02" value="<%=toolLinkWorkOrderItems%>"/>
        <jsp:param name="renderLink02" value="false"/>
        <jsp:param name="secondaryChildItems02" value="t_serviceProviderWorkOrderItems,t_serviceProviderWorkOrderItemDetail"/>

        <jsp:param name="secondaryItem03" value="t_serviceProviderWorkOrderNote"/>
        <jsp:param name="secondaryItemLable03" value="userWorkOrder.text.toolbar.workOrderNotes"/>
        <jsp:param name="secondaryActionLink03" value="<%=toolLinkWorkOrderNote%>"/>
        <jsp:param name="renderLink03" value="false"/>
        <jsp:param name="secondaryChildItems03" value="t_serviceProviderWorkOrderNote,t_serviceProviderWorkOrderNoteDetail"/>

        <jsp:param name="secondaryItem04" value="t_serviceProviderWorkOrderContent"/>
        <jsp:param name="secondaryItemLable04" value="userWorkOrder.text.toolbar.workOrderContent"/>
        <jsp:param name="secondaryActionLink04" value="<%=toolLinkWorkOrderContent%>"/>
        <jsp:param name="renderLink04" value="false"/>
        <jsp:param name="secondaryChildItems04" value="t_serviceProviderWorkOrderContent,t_serviceProviderWorkOrderContentDetail"/>

        <jsp:param name="color01" value="#FFFFFF"/>
        <jsp:param name="color02" value="#F5F13F"/>
        <jsp:param name="color03" value="#000000"/>

        <jsp:param name="headerLabel" value="userWorkOrder.text.toolbar"/>
    </jsp:include>
</app:notAuthorizedForFunction>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER%>">
    <jsp:include flush='true' page="t_serviceProviderTemplatorToolBar.jsp">

        <jsp:param name="display" value="<%=display%>"/>

        <jsp:param name="breadCrumbBar" value="<%=(request.getSession().getAttribute(Constants.BREAD_CRUMB_NAVIGATOR)!=null)%>"/>

        <jsp:param name="actionLink01" value="<%=toolLinkWorkOrder%>"/>
        <jsp:param name="item01" value="t_serviceProviderWorkOrder"/>
        <jsp:param name="itemLable01" value="userWorkOrder.text.findWorkOrder"/>
        <jsp:param name="tabName01" value="f_serviceProviderWorkOrderToolbar"/>
        <jsp:param name="secondaryItems01" value="t_serviceProviderWorkOrder,t_serviceProviderWorkOrderDetail,t_serviceProviderWorkOrderItems,t_serviceProviderWorkOrderItemDetail,t_serviceProviderWorkOrderNote,t_serviceProviderWorkOrderNoteDetail,t_serviceProviderWorkOrderContent,t_serviceProviderWorkOrderContentDetail"/>

        <jsp:param name="actionLink02" value="<%=toolLinkPendingsOrers%>"/>
        <jsp:param name="item02" value="t_serviceProviderPendingWorkOrders"/>
        <jsp:param name="itemLable02" value="userWorkOrder.text.pendingWorkOrders"/>
        <jsp:param name="tabName02" value="f_serviceProviderAssetToolbar"/>

        <jsp:param name="toolSecondaryToolBar01"  value="f_serviceProviderSecondaryToolbar.jsp"/>

        <jsp:param name="secondaryItem01" value="t_serviceProviderWorkOrderDetail"/>
        <jsp:param name="secondaryItemLable01" value="global.label.workOrderSummary"/>
        <jsp:param name="secondaryActionLink01" value="<%=toolLinkWorkOrderDetail%>"/>
        <jsp:param name="renderLink01" value="true"/>

        <jsp:param name="variantB" value="true"/>

        <jsp:param name="toolSecondaryToolBar02"  value="f_serviceProviderSecondaryToolbar.jsp"/>

        <jsp:param name="secondaryItem02" value="t_serviceProviderWorkOrderItems"/>
        <jsp:param name="secondaryItemLable02" value="userWorkOrder.text.toolbar.workOrderItems"/>
        <jsp:param name="secondaryActionLink02" value="<%=toolLinkWorkOrderItems%>"/>
        <jsp:param name="renderLink02" value="false"/>
        <jsp:param name="secondaryChildItems02" value="t_serviceProviderWorkOrderItems,t_serviceProviderWorkOrderItemDetail"/>

        <jsp:param name="secondaryItem03" value="t_serviceProviderWorkOrderNote"/>
        <jsp:param name="secondaryItemLable03" value="userWorkOrder.text.toolbar.workOrderNotes"/>
        <jsp:param name="secondaryActionLink03" value="<%=toolLinkWorkOrderNote%>"/>
        <jsp:param name="renderLink03" value="false"/>
        <jsp:param name="secondaryChildItems03" value="t_serviceProviderWorkOrderNote,t_serviceProviderWorkOrderNoteDetail"/>

        <jsp:param name="secondaryItem04" value="t_serviceProviderWorkOrderContent"/>
        <jsp:param name="secondaryItemLable04" value="userWorkOrder.text.toolbar.workOrderContent"/>
        <jsp:param name="secondaryActionLink04" value="<%=toolLinkWorkOrderContent%>"/>
        <jsp:param name="renderLink04" value="false"/>
        <jsp:param name="secondaryChildItems04" value="t_serviceProviderWorkOrderContent,t_serviceProviderWorkOrderContentDetail"/>

        <jsp:param name="color01" value="#FFFFFF"/>
        <jsp:param name="color02" value="#F5F13F"/>
        <jsp:param name="color03" value="#000000"/>

        <jsp:param name="headerLabel" value="userWorkOrder.text.toolbar"/>
    </jsp:include>
</app:authorizedForFunction>
<%} else {%>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER%>">

    <jsp:include flush='true' page="t_serviceProviderTemplatorToolBar.jsp">
        <jsp:param name="display" value="<%=display%>"/>

        <jsp:param name="breadCrumbBar" value="<%=(request.getSession().getAttribute(Constants.BREAD_CRUMB_NAVIGATOR)!=null)%>"/>

        <jsp:param name="actionLink01" value="<%=toolLinkWorkOrder%>"/>
        <jsp:param name="item01" value="t_serviceProviderWorkOrder"/>
        <jsp:param name="itemLable01" value="userWorkOrder.text.findWorkOrder"/>
        <jsp:param name="tabName01" value="f_serviceProviderWorkOrderToolbar"/>

        <jsp:param name="actionLink02" value="<%=toolLinkPendingsOrers%>"/>
        <jsp:param name="item02" value="t_serviceProviderPendingWorkOrders"/>
        <jsp:param name="itemLable02" value="userWorkOrder.text.pendingWorkOrders"/>
        <jsp:param name="tabName02" value="f_serviceProviderAssetToolbar"/>

        <jsp:param name="color01" value="#FFFFFF"/>
        <jsp:param name="color02" value="#F5F13F"/>
        <jsp:param name="color03" value="#000000"/>

        <jsp:param name="headerLabel" value="userWorkOrder.text.toolbar"/>
    </jsp:include>

</app:authorizedForFunction>
<app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER%>">
    <jsp:include flush='true' page="t_serviceProviderTemplatorToolBar.jsp">
        <jsp:param name="display" value="<%=display%>"/>

        <jsp:param name="breadCrumbBar" value="<%=(request.getSession().getAttribute(Constants.BREAD_CRUMB_NAVIGATOR)!=null)%>"/>

        <jsp:param name="actionLink01" value="<%=toolLinkWorkOrder%>"/>
        <jsp:param name="item01" value="t_serviceProviderWorkOrder"/>
        <jsp:param name="itemLable01" value="userWorkOrder.text.findWorkOrder"/>
        <jsp:param name="tabName01" value="f_serviceProviderWorkOrderToolbar"/>

        <jsp:param name="color01" value="#FFFFFF"/>
        <jsp:param name="color02" value="#F5F13F"/>
        <jsp:param name="color03" value="#000000"/>

        <jsp:param name="headerLabel" value="userWorkOrder.text.toolbar"/>
    </jsp:include>
</app:notAuthorizedForFunction>
<%}%>




