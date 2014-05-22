<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.GregorianCalendar" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<%
 String loginName = (String)request.getSession().getAttribute(Constants.USER_NAME);
%>
<script src="../externals/lib.js" language="javascript"></script>
<script language="JavaScript1.2">
</script>


<html:html>

<head>
<title>Operations Console: Order Credit Card</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">

<script language="JavaScript">
<!--
function setValue(value) {
	var aaa = document.forms[0].elements['invCustIDToProcess'];
  aaa.value=value;
  return true;
}
-->
</script>

</head>

<body>
  <div class="text">
  <center>
    <font color=red>
      <html:errors/>
    </font>
  </center>
  
  <%
  	boolean showCC = false;
  	boolean viewOnly = true;
  %>
  <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.VIEW_PARTIAL_ORD_CREDIT_CARD%>">
    <%
    	showCC=true;
    %>
  </app:authorizedForFunction>
  <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.MODIFY_ORD_CREDIT_CARD%>">
    <%
    	showCC=true;
    	viewOnly=false;
    %>
  </app:authorizedForFunction>
  
  <%
	  if(showCC){
  %>
  
 <%-- <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.VIEW_PARTIAL_ORD_CREDIT_CARD%>"> --%>
  <html:form name="ORDER_CREDIT_CARD_FORM" action="/console/orderCreditCard.do" type="com.cleanwise.view.forms.OrderCreditCardForm">
  <table>
  <tr>
  	<table class="mainbody">
		<tr>
			<td><b>Order Credit Card Id:</b></td>
			<td colspan="5"><bean:write name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.orderCreditCardId"/></td>
		</tr>
		<tr>
<td><b>Auth Status:</b></td>
<td><bean:write name="ORDER_CREDIT_CARD_FORM" 
property="orderCreditCardDescData.orderCreditCardData.authStatusCd"/></td>
<td><b>Authorization Code:</b></td>
			<td>
<logic:present name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.approvalTransactionData">
<bean:write name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.approvalTransactionData.authCode"/>
</logic:present>
			</td>
			<td><b>Auth Address Status:</b></td>
			<td><bean:write name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.authAddressStatusCd"/></td>
		</tr>
		<tr>	
			
			<td><b>Credit Card Ends With:</b></td>
			<td><bean:write name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.creditCardNumberDisplay"/></td>

			<td><b>New Credit Card Number:</b></td>
			<td><html:text disabled="<%=viewOnly%>" name="ORDER_CREDIT_CARD_FORM" property="newCreditCardNumber" size="16" maxlength="16"/></td>
			
			<td><b>Credit Card Type:</b></td>
			<td>
			  <html:select disabled="<%=viewOnly%>" name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.creditCardType">
			    <html:option value="<%=RefCodeNames.PAYMENT_TYPE_CD.VISA%>"><%=RefCodeNames.PAYMENT_TYPE_CD.VISA%></html:option>
			    <html:option value="<%=RefCodeNames.PAYMENT_TYPE_CD.MASTER_CARD%>"><%=RefCodeNames.PAYMENT_TYPE_CD.MASTER_CARD%></html:option>
		          </html:select>
			</td>

		</tr>
		<tr>
			<td><b>Exp Month:</b></td>
			<td>
			      <html:select  disabled="<%=viewOnly%>" name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.expMonth">
				<html:option value="<%=\"\"+GregorianCalendar.JANUARY%>">Jan</html:option>
				<html:option value="<%=\"\"+GregorianCalendar.FEBRUARY%>">Feb</html:option>
				<html:option value="<%=\"\"+GregorianCalendar.MARCH%>">Mar</html:option>
				<html:option value="<%=\"\"+GregorianCalendar.APRIL%>">Apr</html:option>
				<html:option value="<%=\"\"+GregorianCalendar.MAY%>">May</html:option>
				<html:option value="<%=\"\"+GregorianCalendar.JUNE%>">Jun</html:option>
				<html:option value="<%=\"\"+GregorianCalendar.JULY%>">Jul</html:option>
				<html:option value="<%=\"\"+GregorianCalendar.AUGUST%>">Aug</html:option>
				<html:option value="<%=\"\"+GregorianCalendar.SEPTEMBER%>">Sep</html:option>
				<html:option value="<%=\"\"+GregorianCalendar.OCTOBER%>">Oct</html:option>
				<html:option value="<%=\"\"+GregorianCalendar.NOVEMBER%>">Nov</html:option>
				<html:option value="<%=\"\"+GregorianCalendar.DECEMBER%>">Dec</html:option>
			      </html:select>
			</td>
			<td><b>Exp Year:</b></td>
			<td>
			      <%
			       GregorianCalendar cal = new GregorianCalendar();
			       cal.setTime(Constants.getCurrentDate());
			      %>
			      <html:select  disabled="<%=viewOnly%>" name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.expYear">
				<html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
				<% cal.add(GregorianCalendar.YEAR,1);%>
				<html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
				<% cal.add(GregorianCalendar.YEAR,1);%>
				<html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
				<% cal.add(GregorianCalendar.YEAR,1);%>
				<html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
				<% cal.add(GregorianCalendar.YEAR,1);%>
				<html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
				<% cal.add(GregorianCalendar.YEAR,1);%>
				<html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
				<% cal.add(GregorianCalendar.YEAR,1);%>
				<html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
				<% cal.add(GregorianCalendar.YEAR,1);%>
				<html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
				<% cal.add(GregorianCalendar.YEAR,1);%>
				<html:option value="<%=\"\"+cal.get(GregorianCalendar.YEAR)%>"><%=cal.get(GregorianCalendar.YEAR)%></html:option>
			      </html:select>
			</td>
		</tr>
		<tr valign="top">
			<td><b>Name:</b></td>
			<td><html:text disabled="<%=viewOnly%>" name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.name"/></td>
		<!--</tr>
		<tr valign="top">
			<td>&nbsp;</td>-->
			<td colspan="4">
				<table>
					<tr>
					   <td><b>Address:</b></td>
					   <td colspan="3"><html:text disabled="<%=viewOnly%>"name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.address1"/></td>
					</tr>
					<tr>
					   <td>&nbsp;</td>
					   <td colspan="3">
					   		<html:text disabled="<%=viewOnly%>" name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.address2"/></td>
					</tr>
					<tr>
					   <td>&nbsp;</td>
					   <td colspan="3">
					   		<html:text disabled="<%=viewOnly%>" name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.address3"/></td>
					</tr>
					<tr>
					   <td>&nbsp;</td>
					   <td colspan="3"><html:text disabled="<%=viewOnly%>" name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.address4"/></td>
					</tr>
					<tr>
					   <td><b>City:</b></td>
					   <td colspan="3"><html:text disabled="<%=viewOnly%>" name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.city"/></td>
					</tr>
					<tr>
					   <td><b>State:</b></td>
					   <td><html:text disabled="<%=viewOnly%>" name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.stateProvinceCd" maxlength="2" size="2"/></td>

					   <td><b>Postal Code:</b></td>
					   <td><html:text disabled="<%=viewOnly%>" name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.postalCode" maxlength="9" size="9"/></td>
					</tr>
				</table>
			</td>
		</tr>
		
		<%if(!viewOnly){ %>
		
		<tr>
		<td align="center" colspan="2">
		   <html:submit property="action">
			<app:storeMessage  key="global.action.label.save"/>
		   </html:submit>
		   (without reauthorizing)
		</td>
		<logic:equal name="ORDER_CREDIT_CARD_FORM" property="orderCreditCardDescData.orderCreditCardData.authStatusCd" value="AUTH_FAILED">
		<td align="center">
		   <html:submit property="action">
			<app:storeMessage  key="admin.button.reauthorize"/>
		   </html:submit>
		</td>
		</logic:equal>
		</tr>
		<%} %>
		
		</table>
		</tr>
		
		<logic:present name="ORDER_CREDIT_CARD_FORM" property="invCCTransList">
		<tr>
		<table width="750" border="0" class="results">
		<tr>
			<td colspan="6">&nbsp;</td>
		</tr>
		
		<tr>
			<td><b>Invoice Num</b></td>
			<td><b>Date</b></td>
			<td><b>Transaction</b></td>
			<td><b>Amount</b></td>
			<td><b>Transaction Reference</b></td>
			<td><b>Re-Submit
				<html:hidden property="invCustIDToProcess" />
			</b></td>
		</tr>
		
		<tr><td colspan="6"><hr></td></tr>
		
		<logic:iterate id="itemele" indexId="i" name="ORDER_CREDIT_CARD_FORM" 
			property="invCCTransList" 
			type="com.cleanwise.service.api.value.InvoiceCreditCardTransView">
		
		<% String style = ((i)%2)==0?"resultscolumna":"resultscolumnb"; %>
		<tr class="<%=style%>">
		
		<td><bean:write name="itemele" property="invoiceNum"/></td>
		<td><bean:write name="itemele" property="addDate"/></td>
		<td><bean:write name="itemele" property="transactionTypeCd"/></td>
		<td><bean:write name="itemele" property="amount"/></td>
		<td><bean:write name="itemele" property="transactionReference"/></td>
		<td>
			<%
			if(!viewOnly){ 
				if(itemele.getTransactionTypeCd().equals("SALE") && 
						!Utility.isSet(itemele.getAuthCode())){
					int invCustID = itemele.getInvoiceCustId();
					String str = "javascript: setValue('"+invCustID+"');";
			%>
			 		<html:submit onclick="<%=str%>" property="action"> 
						<app:storeMessage  key="admin.button.resubmit" />
		   		</html:submit>
			<%
				}
			}
			%>
		</td>
		</tr>
		
		</logic:iterate>
		</table>
		</tr>
		</logic:present>
		
	</table>
	
  </html:form>
  <%--</app:authorizedForFunction> --%>
  
   <%
   }
   %>
</body>
</html:html>
