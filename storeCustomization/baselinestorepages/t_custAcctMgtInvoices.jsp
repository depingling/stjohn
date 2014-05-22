<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<jsp:include flush='true' page="t_custAcctMgtInvoicesBody.jsp">
   <jsp:param name="invoiceDisply" 	        value="true" />
   <jsp:param name="poDetailAction"         value="create" />
   <jsp:param name="invDetailAction"        value="view" />
   <jsp:param name="detailURI"              value="customerAccountManagementInvoicesDetail.do" />
</jsp:include>

