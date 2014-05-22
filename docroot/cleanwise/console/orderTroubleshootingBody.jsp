<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>



	
<table cellpadding="0" cellspacing="0" border="0" width="769" class="mainbody">

<html:form name="ORDER_TROUBLESHOOTING_FORM" action="/console/orderTroubleshooting.do" 
    scope="session" type="com.cleanwise.view.forms.OrderTroubleshootingForm">
	
  <tr><td width="15%">&nbsp;</td><td>&nbsp;</td></tr>  
  <tr>  
    <td width="15%">&nbsp;</td>  
	<td align="left"><a class="tbar2" href="/<%=storeDir%>/console/orderTroubleshooting.do?action=run&type=currentBackorders">Current Backorders</a></td>
  </tr>
  <tr>    
    <td width="15%">&nbsp;</td> 
	<td align="left"><a class="tbar2" href="/<%=storeDir%>/console/orderTroubleshooting.do?action=run&type=poNotConfirmed">PO Not Confirmed from Distributor</a></td>
  </tr>
 <tr>     
    <td width="15%">&nbsp;</td>
	<td align="left"><a class="tbar2" href="/<%=storeDir%>/console/orderTroubleshooting.do?action=run&type=poAckNotSent">PO Acknowledgement Not Sent to Customer</a></td>
  </tr>
  <tr>   
    <td width="15%">&nbsp;</td>
	<td align="left"><a class="tbar2" href="/<%=storeDir%>/console/orderTroubleshooting.do?action=run&type=shipNoticeNotSent">Ship Notice Not Sent to Customer</a></td>
  </tr>
  <tr> 
    <td width="15%">&nbsp;</td>
	<td align="left"><a class="tbar2" href="/<%=storeDir%>/console/orderTroubleshooting.do?action=run&type=poNotReceived">PO Not Received by Distributor</td>
  </tr>
  <tr>  
    <td width="15%">&nbsp;</td>
	<td align="left"><a class="tbar2" href="/<%=storeDir%>/console/orderTroubleshooting.do?action=run&type=poRejected">PO Rejected from Distributor</td>
  </tr>
  <tr>    
    <td width="15%">&nbsp;</td>
	<td align="left"><a class="tbar2" href="/<%=storeDir%>/console/orderTroubleshooting.do?action=run&type=invoiceNotSent">Invoice Not Sent to Customer</td>
  </tr>
  <tr><td width="15%">&nbsp;</td><td>&nbsp;</td></tr>
  <tr><td width="15%">&nbsp;</td><td>&nbsp;</td></tr>

</html:form>  
</table>






