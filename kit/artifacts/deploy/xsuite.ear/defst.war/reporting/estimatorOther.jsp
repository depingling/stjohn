<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.CurrencyFormat" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="SPENDING_ESTIMATOR_FORM" type="com.cleanwise.view.forms.SpendingEstimatorForm"/>

<table>
<html:form name="SPENDING_ESTIMATOR_FORM" action="reporting/estimator"
    scope="session" type="com.cleanwise.view.forms.SpendingEstimatorForm">
 <tbody>

<tr>
</tr>
<tr>
<!--       ------------------------------->
<%
String action = (String) request.getAttribute("action");
ActionErrors ae = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
int otherProcessStep = theForm.getOtherProcessStep();
boolean errorFl = (ae!=null && ae.size()>0)? true:false;
%>
<% if(otherProcessStep<=2) { %>
<tr><td><%@ include file="estimatorProfilePaperPlusInfo.jsp" %></td></tr>
<tr><td><%@ include file="estimatorOtherProdSelect.jsp" %></td></tr>
<% } else if( otherProcessStep>=3) { %>
<tr><td><%@ include file="estimatorProfilePaperPlusInfo.jsp" %></td></tr>
<tr><td><%@ include file="estimatorOtherProdInfo.jsp" %></td></tr>
<tr><td><%@ include file="estimatorOtherProdResult.jsp" %></td></tr>
<% } %>

</tbody></html:form>
</table>



