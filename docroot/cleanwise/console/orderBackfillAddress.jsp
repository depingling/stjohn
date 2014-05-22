<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<bean:define id="theForm" name="ORDER_BACKFILL_FORM" type="com.cleanwise.view.forms.OrderBackfillForm"/>

	<tr>
	  <td><b>Addresses:</b></td>
	  <td colspan="5">&nbsp;</td>
    </tr>
	<tr>
	  <td colspan="6">
      <table border="0" cellpadding="1" cellspacing="0" width="900" class="mainbody">
      <tr>
      <td><b>&nbsp;</b></td>
      <td><b>Shipto Before</b></td>
      <td><b>Shipto After</b></td>
      <td><b>Billto Before</b></td>
      <td><b>Billto After</b></td>
      </tr>
      <%
      String shiptoShortDesc = "";
      String shiptoAddress1 = "";
      String shiptoAddress2 = "";
      String shiptoAddress3 = "";
      String shiptoAddress4 = "";
      String shiptoCity = "";
      String shiptoState = "";
      String shiptoZip = "";
      String shiptoCountry = "";
      if(theForm.getOrderStatusDetail().getShipTo()!=null) {
        shiptoShortDesc = theForm.getOrderStatusDetail().getShipTo().getShortDesc();
        if(shiptoShortDesc==null) shiptoShortDesc="";
        shiptoAddress1 = theForm.getOrderStatusDetail().getShipTo().getAddress1();
        if(shiptoAddress1==null) shiptoAddress1="";
        shiptoAddress2 = theForm.getOrderStatusDetail().getShipTo().getAddress2();
        if(shiptoAddress2==null) shiptoAddress2="";
        shiptoAddress3 = theForm.getOrderStatusDetail().getShipTo().getAddress3();
        if(shiptoAddress3==null) shiptoAddress3="";
        shiptoAddress4 = theForm.getOrderStatusDetail().getShipTo().getAddress4();
        if(shiptoAddress4==null) shiptoAddress4="";
        shiptoCity = theForm.getOrderStatusDetail().getShipTo().getCity();
        if(shiptoCity==null) shiptoCity="";
        shiptoState = theForm.getOrderStatusDetail().getShipTo().getStateProvinceCd();
        if(shiptoState==null) shiptoState="";
        shiptoZip = theForm.getOrderStatusDetail().getShipTo().getPostalCode();
        if( shiptoZip==null)  shiptoZip="";
        shiptoCountry = theForm.getOrderStatusDetail().getShipTo().getCountryCd();
        if( shiptoCountry==null)  shiptoCountry="";
      }
      String shiptoShortDescAfter = "";
      String shiptoAddress1After = "";
      String shiptoAddress2After = "";
      String shiptoAddress3After = "";
      String shiptoAddress4After = "";
      String shiptoCityAfter = "";
      String shiptoStateAfter = "";
      String shiptoZipAfter = "";
      String shiptoCountryAfter = "";
      if(theForm.getOrderStatusDetailAfter().getShipTo()!=null) {
        shiptoShortDescAfter = theForm.getOrderStatusDetailAfter().getShipTo().getShortDesc();
        if(shiptoShortDescAfter==null) shiptoShortDescAfter="";
        shiptoAddress1After = theForm.getOrderStatusDetailAfter().getShipTo().getAddress1();
        if(shiptoAddress1After==null) shiptoAddress1After="";
        shiptoAddress2After = theForm.getOrderStatusDetailAfter().getShipTo().getAddress2();
        if(shiptoAddress2After==null) shiptoAddress2After="";
        shiptoAddress3After = theForm.getOrderStatusDetailAfter().getShipTo().getAddress3();
        if(shiptoAddress3After==null) shiptoAddress3After="";
        shiptoAddress4After = theForm.getOrderStatusDetailAfter().getShipTo().getAddress4();
        if(shiptoAddress4After==null) shiptoAddress4After="";
        shiptoCityAfter = theForm.getOrderStatusDetailAfter().getShipTo().getCity();
        if(shiptoCityAfter==null) shiptoCityAfter="";
        shiptoStateAfter = theForm.getOrderStatusDetailAfter().getShipTo().getStateProvinceCd();
        if(shiptoStateAfter==null) shiptoStateAfter="";
        shiptoZipAfter = theForm.getOrderStatusDetailAfter().getShipTo().getPostalCode();
        if( shiptoZipAfter==null)  shiptoZipAfter="";
        shiptoCountryAfter = theForm.getOrderStatusDetailAfter().getShipTo().getCountryCd();
        if( shiptoCountryAfter==null)  shiptoCountryAfter="";
      }
      String billtoShortDesc = "";
      String billtoAddress1 = "";
      String billtoAddress2 = "";
      String billtoAddress3 = "";
      String billtoAddress4 = "";
      String billtoCity = "";
      String billtoState = "";
      String billtoZip = "";
      String billtoCountry = "";
      if(theForm.getOrderStatusDetail().getBillTo()!=null) {
        billtoShortDesc = theForm.getOrderStatusDetail().getBillTo().getShortDesc();
        if(billtoShortDesc==null) billtoShortDesc="";
        billtoAddress1 = theForm.getOrderStatusDetail().getBillTo().getAddress1();
        if(billtoAddress1==null) billtoAddress1="";
        billtoAddress2 = theForm.getOrderStatusDetail().getBillTo().getAddress2();
        if(billtoAddress2==null) billtoAddress2="";
        billtoAddress3 = theForm.getOrderStatusDetail().getBillTo().getAddress3();
        if(billtoAddress3==null) billtoAddress3="";
        billtoAddress4 = theForm.getOrderStatusDetail().getBillTo().getAddress4();
        if(billtoAddress4==null) billtoAddress4="";
        billtoCity = theForm.getOrderStatusDetail().getBillTo().getCity();
        if(billtoCity==null) billtoCity="";
        billtoState = theForm.getOrderStatusDetail().getBillTo().getStateProvinceCd();
        if(billtoState==null) billtoState="";
        billtoZip = theForm.getOrderStatusDetail().getBillTo().getPostalCode();
        if( billtoZip==null)  billtoZip="";
        billtoCountry = theForm.getOrderStatusDetail().getBillTo().getCountryCd();
        if( billtoCountry==null)  billtoCountry="";
      }
      String billtoShortDescAfter = "";
      String billtoAddress1After = "";
      String billtoAddress2After = "";
      String billtoAddress3After = "";
      String billtoAddress4After = "";
      String billtoCityAfter = "";
      String billtoStateAfter = "";
      String billtoZipAfter = "";
      String billtoCountryAfter = "";
      if(theForm.getOrderStatusDetailAfter().getBillTo()!=null) {
        billtoShortDescAfter = theForm.getOrderStatusDetailAfter().getBillTo().getShortDesc();
        if(billtoShortDescAfter==null) billtoShortDescAfter="";
        billtoAddress1After = theForm.getOrderStatusDetailAfter().getBillTo().getAddress1();
        if(billtoAddress1After==null) billtoAddress1After="";
        billtoAddress2After = theForm.getOrderStatusDetailAfter().getBillTo().getAddress2();
        if(billtoAddress2After==null) billtoAddress2After="";
        billtoAddress3After = theForm.getOrderStatusDetailAfter().getBillTo().getAddress3();
        if(billtoAddress3After==null) billtoAddress3After="";
        billtoAddress4After = theForm.getOrderStatusDetailAfter().getBillTo().getAddress4();
        if(billtoAddress4After==null) billtoAddress4After="";
        billtoCityAfter = theForm.getOrderStatusDetailAfter().getBillTo().getCity();
        if(billtoCityAfter==null) billtoCityAfter="";
        billtoStateAfter = theForm.getOrderStatusDetailAfter().getBillTo().getStateProvinceCd();
        if(billtoStateAfter==null) billtoStateAfter="";
        billtoZipAfter = theForm.getOrderStatusDetailAfter().getBillTo().getPostalCode();
        if( billtoZipAfter==null)  billtoZipAfter="";
        billtoCountryAfter = theForm.getOrderStatusDetailAfter().getBillTo().getCountryCd();
        if( billtoCountryAfter==null)  billtoCountryAfter="";
      }
      String errColor = "red";
      String regColor = "black";
      String color;
      %>
      <tr>
      <td><b>Name:</b></td>
<% color = (shiptoShortDesc.equals(shiptoShortDescAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=shiptoShortDesc%></font></td>
      <td><font color="<%=color%>"><%=shiptoShortDescAfter%></font></td>
<% color = (billtoShortDesc.equals(billtoShortDescAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=billtoShortDesc%></font></td>
      <td><font color="<%=color%>"><%=billtoShortDescAfter%></font></td>
      </tr>
      <tr>
      <td><b>Address1:</b></td>
<% color = (shiptoAddress1.equals(shiptoAddress1After))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=shiptoAddress1%></font></td>
      <td><font color="<%=color%>"><%=shiptoAddress1After%></font></td>
<% color = (billtoAddress1.equals(billtoAddress1After))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=billtoAddress1%></font></td>
      <td><font color="<%=color%>"><%=billtoAddress1After%></font></td>
      </tr>
      <tr>
      <td><b>Address2:</b></td>
<% color = (shiptoAddress2.equals(shiptoAddress2After))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=shiptoAddress2%></font></td>
      <td><font color="<%=color%>"><%=shiptoAddress2After%></font></td>
<% color = (billtoAddress2.equals(billtoAddress2After))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=billtoAddress2%></font></td>
      <td><font color="<%=color%>"><%=billtoAddress2After%></font></td>
      </tr>
      <tr>
      <td><b>Address3:</b></td>
<% color = (shiptoAddress3.equals(shiptoAddress3After))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=shiptoAddress3%></font></td>
      <td><font color="<%=color%>"><%=shiptoAddress3After%></font></td>
<% color = (billtoAddress3.equals(billtoAddress3After))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=billtoAddress3%></font></td>
      <td><font color="<%=color%>"><%=billtoAddress3After%></font></td>
      </tr>
      <tr>
      <td><b>Address4:</b></td>
<% color = (shiptoAddress4.equals(shiptoAddress4After))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=shiptoAddress4%></font></td>
      <td><font color="<%=color%>"><%=shiptoAddress4After%></font></td>
<% color = (billtoAddress4.equals(billtoAddress4After))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=billtoAddress4%></font></td>
      <td><font color="<%=color%>"><%=billtoAddress4After%></font></td>
      </tr>
      <tr>
      <td><b>City:</b></td>
<% color = (shiptoCity.equals(shiptoCityAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=shiptoCity%></font></td>
      <td><font color="<%=color%>"><%=shiptoCityAfter%></font></td>
<% color = (billtoCity.equals(billtoCityAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=billtoCity%></font></td>
      <td><font color="<%=color%>"><%=billtoCityAfter%></font></td>
      </tr>
      <tr>
      <td><b>State:</b></td>
<% color = (shiptoState.equals(shiptoStateAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=shiptoState%></font></td>
      <td><font color="<%=color%>"><%=shiptoStateAfter%></font></td>
<% color = (billtoState.equals(billtoStateAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=billtoState%></font></td>
      <td><font color="<%=color%>"><%=billtoStateAfter%></font></td>
      </tr>
      <tr>
      <td><b>Zip Code:</b></td>
<% color = (shiptoZip.equals(shiptoZipAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=shiptoZip%></font></td>
      <td><font color="<%=color%>"><%=shiptoZipAfter%></font></td>
<% color = (billtoZip.equals(billtoZipAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=billtoZip%></font></td>
      <td><font color="<%=color%>"><%=billtoZipAfter%></font></td>
      </tr>
      <tr>
      <td><b>Country:</b></td>
<% color = (shiptoCountry.equals(shiptoCountryAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=shiptoCountry%></font></td>
      <td><font color="<%=color%>"><%=shiptoCountryAfter%></font></td>
<% color = (billtoCountry.equals(billtoCountryAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=billtoCountry%></font></td>
      <td><font color="<%=color%>"><%=billtoCountryAfter%></font></td>
      </tr>
      <tr>
      <td><b>&nbsp;</b></td>
      <td><b>Customer Shipto Before</b></td>
      <td><b>Customer Shipto After</b></td>
      <td><b>&nbsp;</b></td>
      <td><b>&nbsp;</b></td>
      </tr>
      <%
      String custShiptoShortDesc = "";
      String custShiptoAddress1 = "";
      String custShiptoAddress2 = "";
      String custShiptoAddress3 = "";
      String custShiptoAddress4 = "";
      String custShiptoCity = "";
      String custShiptoState = "";
      String custShiptoZip = "";
      String custShiptoCountry = "";
      if(theForm.getOrderStatusDetail().getCustomerShipTo()!=null) {
        custShiptoShortDesc = theForm.getOrderStatusDetail().getCustomerShipTo().getShortDesc();
        if(custShiptoShortDesc==null) custShiptoShortDesc="";
        custShiptoAddress1 = theForm.getOrderStatusDetail().getCustomerShipTo().getAddress1();
        if(custShiptoAddress1==null) custShiptoAddress1="";
        custShiptoAddress2 = theForm.getOrderStatusDetail().getCustomerShipTo().getAddress2();
        if(custShiptoAddress2==null) custShiptoAddress2="";
        custShiptoAddress3 = theForm.getOrderStatusDetail().getCustomerShipTo().getAddress3();
        if(custShiptoAddress3==null) custShiptoAddress3="";
        custShiptoAddress4 = theForm.getOrderStatusDetail().getCustomerShipTo().getAddress4();
        if(custShiptoAddress4==null) custShiptoAddress4="";
        custShiptoCity = theForm.getOrderStatusDetail().getCustomerShipTo().getCity();
        if(custShiptoCity==null) custShiptoCity="";
        custShiptoState = theForm.getOrderStatusDetail().getCustomerShipTo().getStateProvinceCd();
        if(custShiptoState==null) custShiptoState="";
        custShiptoZip = theForm.getOrderStatusDetail().getCustomerShipTo().getPostalCode();
        if( custShiptoZip==null)  custShiptoZip="";
        custShiptoCountry = theForm.getOrderStatusDetail().getCustomerShipTo().getCountryCd();
        if( custShiptoCountry==null)  custShiptoCountry="";
      }
      String custShiptoShortDescAfter = "";
      String custShiptoAddress1After = "";
      String custShiptoAddress2After = "";
      String custShiptoAddress3After = "";
      String custShiptoAddress4After = "";
      String custShiptoCityAfter = "";
      String custShiptoStateAfter = "";
      String custShiptoZipAfter = "";
      String custShiptoCountryAfter = "";
      if(theForm.getOrderStatusDetailAfter().getCustomerShipTo()!=null) {
        custShiptoShortDescAfter = theForm.getOrderStatusDetailAfter().getCustomerShipTo().getShortDesc();
        if(custShiptoShortDescAfter==null) custShiptoShortDescAfter="";
        custShiptoAddress1After = theForm.getOrderStatusDetailAfter().getCustomerShipTo().getAddress1();
        if(custShiptoAddress1After==null) custShiptoAddress1After="";
        custShiptoAddress2After = theForm.getOrderStatusDetailAfter().getCustomerShipTo().getAddress2();
        if(custShiptoAddress2After==null) custShiptoAddress2After="";
        custShiptoAddress3After = theForm.getOrderStatusDetailAfter().getCustomerShipTo().getAddress3();
        if(custShiptoAddress3After==null) custShiptoAddress3After="";
        custShiptoAddress4After = theForm.getOrderStatusDetailAfter().getCustomerShipTo().getAddress4();
        if(custShiptoAddress4After==null) custShiptoAddress4After="";
        custShiptoCityAfter = theForm.getOrderStatusDetailAfter().getCustomerShipTo().getCity();
        if(custShiptoCityAfter==null) custShiptoCityAfter="";
        custShiptoStateAfter = theForm.getOrderStatusDetailAfter().getCustomerShipTo().getStateProvinceCd();
        if(custShiptoStateAfter==null) custShiptoStateAfter="";
        custShiptoZipAfter = theForm.getOrderStatusDetailAfter().getCustomerShipTo().getPostalCode();
        if( custShiptoZipAfter==null)  custShiptoZipAfter="";
        custShiptoCountryAfter = theForm.getOrderStatusDetailAfter().getCustomerShipTo().getCountryCd();
        if( custShiptoCountryAfter==null)  custShiptoCountryAfter="";
      }
      %>
      <tr>
      <td><b>Name:</b></td>
<% color = (custShiptoShortDesc.equals(custShiptoShortDescAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=custShiptoShortDesc%></font></td>
      <td><font color="<%=color%>"><%=custShiptoShortDescAfter%></font></td>
      </tr>
      <tr>
      <td><b>Address1:</b></td>
<% color = (custShiptoAddress1.equals(custShiptoAddress1After))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=custShiptoAddress1%></font></td>
      <td><font color="<%=color%>"><%=custShiptoAddress1After%></font></td>
      </tr>
      <tr>
      <td><b>Address2:</b></td>
<% color = (custShiptoAddress2.equals(custShiptoAddress2After))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=custShiptoAddress2%></font></td>
      <td><font color="<%=color%>"><%=custShiptoAddress2After%></font></td>
      </tr>
      <tr>
      <td><b>Address3:</b></td>
<% color = (custShiptoAddress3.equals(custShiptoAddress3After))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=custShiptoAddress3%></font></td>
      <td><font color="<%=color%>"><%=custShiptoAddress3After%></font></td>
      </tr>
      <tr>
      <td><b>Address4:</b></td>
<% color = (custShiptoAddress4.equals(custShiptoAddress4After))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=custShiptoAddress4%></font></td>
      <td><font color="<%=color%>"><%=custShiptoAddress4After%></font></td>
      </tr>
      <tr>
      <td><b>City:</b></td>
<% color = (custShiptoCity.equals(custShiptoCityAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=custShiptoCity%></font></td>
      <td><font color="<%=color%>"><%=custShiptoCityAfter%></font></td>
      </tr>
      <tr>
      <td><b>State:</b></td>
<% color = (custShiptoState.equals(custShiptoStateAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=custShiptoState%></font></td>
      <td><font color="<%=color%>"><%=custShiptoStateAfter%></font></td>
      </tr>
      <tr>
      <td><b>Zip Code:</b></td>
<% color = (custShiptoZip.equals(custShiptoZipAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=custShiptoZip%></font></td>
      <td><font color="<%=color%>"><%=custShiptoZipAfter%></font></td>
      </tr>
      <tr>
      <td><b>Country:</b></td>
<% color = (custShiptoCountry.equals(custShiptoCountryAfter))?regColor:errColor; %>
      <td><font color="<%=color%>"><%=custShiptoCountry%></font></td>
      <td><font color="<%=color%>"><%=custShiptoCountryAfter%></font></td>

      </tr>
      </table>
      </td>
    </tr>
