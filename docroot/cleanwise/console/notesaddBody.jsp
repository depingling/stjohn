<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<%

 Date currd = new Date();  
   String date =  currd.toString();

   String loginName = (String)request.getSession().getAttribute(Constants.USER_NAME);
%>


<html:html>

<head>
<title>Operations Console Home: Order Status</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body>
  <div class="text">
  <center>
    <font color=red>
      <html:errors/>
    </font>
  </center>



  <html:form name="ORDER_OP_DETAIL_FORM" action="/console/orderOpDetail.do"
	type="com.cleanwise.view.forms.OrderOpDetailForm">	
  <table>	
	<tr>
	  <td valign="top"><b>Notes:</b></td>
	  <td><html:textarea name="ORDER_OP_DETAIL_FORM" property="orderPropertyDetail.value" rows="10" cols="30" value="" /></td>	
	</tr>
	<tr>
	  <td><html:hidden name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderId"/>
          <html:hidden name="ORDER_OP_DETAIL_FORM" property="orderPropertyDetail.addBy" value="<%= loginName %>"/></td>
	</tr>
	<tr>
	  <td><html:submit onclick="javascript:window.close()" property="action">
            <app:storeMessage  key="global.action.label.save"/>
          </html:submit>
      </td>
	  <td><html:button onclick="javascript:window.close()" property="action">
            <app:storeMessage  key="global.action.label.cancel"/>
          </html:button>
      </td>
	</tr>
  </table>	
  </html:form>
</body>
</html:html>
	