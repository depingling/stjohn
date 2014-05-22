
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" 
  name="pages.store.images"/>
<bean:define id="thisReportCd" name="CUSTOMER_REPORT_FORM" 
 type="java.lang.String" property="reportTypeCd"/>
<bean:define id="printMsg" value="<br><br>You may print this page by selecting File &gt; Print from your browser's menu bar.  <br><br>" 
  type="java.lang.String"/>


<tr>
<td>

<div class="fivemargin"><img src="<%=IMGPath%>/cw_spacer.gif" height="5"><br>
<div class="subheadergeneric"><%=thisReportCd%></div>
<%=printMsg%>

<!-- xml report start -->

<% int idx = 0; %>
<table border="0" width="750" cellpadding=2 cellspacing=0 >

<tr class="top3dk" valign="top">
<td class="columntitle" colspan="4"><%=thisReportCd%></td>
<td class="columntitle" colspan=8>
&nbsp;
</td>

</tr>

<logic:iterate id="arrele" name="CUSTOMER_REPORT_FORM" 
 property="orderReport"
 type="com.cleanwise.service.api.dao.UniversalDAO.dbrow" >
<% if ( idx == 0 ) { %> 
<tr class="changeshippinglt" valign="top">
<td class="cityhead">Order<br>Date</td>
<td class="cityhead">Store  </td>
<td class="cityhead">YTD <br>Actual </td>
<td class="cityhead">Rank </td>
<td class="cityhead">NSF</td>
<td class="cityhead">BSC </td>
<td class="cityhead">Requested </td>
<td class="cityhead">YTD Remaining <br>Budget </td>
<td class="cityhead">Approved </td>
<td class="cityhead">Committed </td>
<td class="cityhead">Date<br>Approved</td>
</tr>
<% } idx++; %>
<tr>
<td class="rtext2">
<%=ClwI18nUtil.formatDate(request, new java.util.Date(arrele.getStringValue(0)))%>
</td>
<td class="rtext2"><%=arrele.getStringValue(1)%></td>
<td  class="rtext2" align=right>
<% java.math.BigDecimal ytdactual = 
  new java.math.BigDecimal(arrele.getStringValue(2)); %>
<i18n:formatCurrency value="<%=ytdactual%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext2"><%=arrele.getStringValue(3)%></td>
<td class="rtext2"><%=arrele.getStringValue(4)%></td>
<td class="rtext2"><%=arrele.getStringValue(5)%></td>
<td  class="rtext2" align=right>
<% java.math.BigDecimal n1 = 
  new java.math.BigDecimal(arrele.getStringValue(6)); %>
<i18n:formatCurrency value="<%=n1%>" locale="<%=Locale.US%>"/>
</td>
<td  class="rtext2" align=right>
<% java.math.BigDecimal n2 = 
  new java.math.BigDecimal(arrele.getStringValue(7)); %>
<i18n:formatCurrency value="<%=n2%>" locale="<%=Locale.US%>"/>
</td>

<td  class="rtext2" align=right>
<% java.math.BigDecimal n4 = 
  new java.math.BigDecimal(arrele.getColumn("APPROVED_AMT").colVal); %>
<i18n:formatCurrency value="<%=n4%>" locale="<%=Locale.US%>"/>
</td>

<td  class="rtext2" align=right>
<% java.math.BigDecimal n3 = 
  new java.math.BigDecimal(arrele.getStringValue(8)); %>
<i18n:formatCurrency value="<%=n3%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext2">
<% if (n3.doubleValue() > 0 &&
  arrele.getColumn("REVISED_DATE").colVal != null
  ) { %>
  <%=ClwI18nUtil.formatDate(request, new java.util.Date(arrele.getColumn("REVISED_DATE").colVal))%>
<%--arrele.getColumn("REVISED_DATE").colVal --%>
<% } else if (n3.doubleValue() > 0 ) { 
/* This order was modified, set the approval date
   equal to the order date. */
%>
<%=ClwI18nUtil.formatDate(request, new java.util.Date(arrele.getStringValue(0)))%>
<% } else { %> &nbsp; <% } %>
</td>
</td>
</tr>
</logic:iterate>
</table>


<!-- xml report end -->

<% if ( idx > 0 ) { %>
<tr><td align=right colspan=12>
<input type="submit" class="store_fb"
 name="action" value="Export"/>
</td></tr>
<% } %>

</td>
</tr>


