<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.service.api.value.OrderStatusDescData"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="theForm" name="esw.OrdersForm" type="com.espendwise.view.forms.esw.OrdersForm"/>
<% 
  OrderStatusDescData orderStatus = theForm.getOrderOpDetailForm().getOrderStatusDetail();

	String orderDetailPage = ""; 
  if(orderStatus.getOrderDetail().getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)) { 
	  orderDetailPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "contentViewOrderDetailPending.jsp");
  }
  else{
	  orderDetailPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "contentViewOrderDetailProcessed.jsp");
  }
  %>
	  <jsp:include page="<%=orderDetailPage %>"/>
  
 