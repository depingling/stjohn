<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.*"%>

<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.TimeZone" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>



<%
   CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
   SiteData siteData = appUser.getSite();
   Date cutoffDate = Utility.getDateTime(siteData.getNextOrdercutoffDate(), siteData.getNextOrdercutoffTime());
   Date nextDeliveryDate = siteData.getNextDeliveryDate();
   SimpleDateFormat sdtf = new SimpleDateFormat("MMM dd, yyyy h a");
   SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

   String cutoffMess = ClwI18nUtil.getMessage(request, "shop.header.text.orderCutOff",null);
   String deliveryMess = ClwI18nUtil.getMessage(request, "shop.header.text.schedDeliveryDate",null);

   String cutoffDateS ="";
   String nextDeliveryDateS = "";
   
   if (siteData != null){
     if (cutoffDate != null){
       cutoffDateS = sdtf.format(cutoffDate);
	   
     }
     if (nextDeliveryDate != null){
       nextDeliveryDateS = sdf.format(nextDeliveryDate);
     }
   }

%>


<table  cellpadding="5" width="100%"  border="0">
  <%
  if (siteData.hasInventoryShoppingOn()) {%>

  <% } %>
<tr>
  <td  style="text-transform:capitalize;" align="left">
  <% if ( cutoffDate != null ) { %>
     <b><%=cutoffMess.toLowerCase()%></b>
     <br>
     <%=cutoffDateS%> EST
  <% } %>
  </td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
  <td style="text-transform:capitalize;" align="left">

  <% if ( nextDeliveryDate != null) { %>
     <b><%=deliveryMess.toLowerCase()%></b>
     <br>
   <%=nextDeliveryDateS%> before 4 PM EST
   <% } %>
</td>
</tr>
</table>
