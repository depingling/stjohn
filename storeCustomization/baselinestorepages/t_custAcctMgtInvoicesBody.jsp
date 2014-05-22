<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script src="../externals/table-sort.js" language="javascript"></script>
<%
    String browser = (String) request.getHeader("User-Agent");
    String isMSIE = "";
    if (browser != null && browser.indexOf("MSIE") >= 0) {
        isMSIE = "Y";
%>
<script language="JavaScript" src="../externals/calendar.js"></script>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
        marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% } else { %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% } %>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
String myURI = SessionTool.getActualRequestedURIPage(request);
String submitURI = SessionTool.getActualRequestedStrutsMapping(request);
if(submitURI.endsWith("submit")){
	submitURI = submitURI + ".do";
}else{
	submitURI = submitURI + "-submit.do";
}
String poDetailAction = request.getParameter("poDetailAction");
String invDetailAction = request.getParameter("invDetailAction");
String detailURI = request.getParameter("detailURI");
String invoiceDisply = request.getParameter("invoiceDisply");
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
String userType = appUser.getUser().getUserTypeCd();

String distributorId = null;
if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userType)){
	distributorId = ((BusEntityData)appUser.getDistributors().get(0)).getBusEntityId()+"";
}
%>

<table align=center CELLSPACING=0 CELLPADDING=5 width="100%"><tr><td>
<form name="CUST_ACCT_MGT_INVOICE_SEARCH_FORM" method="post" action=""  >
<table width="60%" >
<bean:define id="columnCount" value="13"/>
  <!--  PO Number -->
  <tr>
       <td align="right"><b><app:storeMessage key="invoice.text.form.poNumber" /></b></td>
       <td>
         <html:text  name="CUST_ACCT_MGT_INVOICE_SEARCH_FORM" property="erpPONum" size="14" />
       </td>
       <td colspan="2">Invoice Status: &nbsp;
       <html:select name="CUST_ACCT_MGT_INVOICE_SEARCH_FORM" property="searchStatus">
       <html:option value="">All</html:option>
       <html:option value="<%=RefCodeNames.INVOICE_STATUS_CD.CANCELLED%>">
	          Cancelled
	   </html:option>
       <html:option value="<%=RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_RELEASED%>">
	          Released
	   </html:option>
       <html:option value="<%=RefCodeNames.INVOICE_STATUS_CD.DUPLICATE%>">
	          Duplicated
	   </html:option>	   
       <html:option value="<%=RefCodeNames.INVOICE_STATUS_CD.INVOICE_HISTORY%>">
	          History
	   </html:option>
       <html:option value="<%=RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW%>">
	          Pending Review
	   </html:option>
       </html:select>
       </td>
  </tr>
  <!--  Invoice Number -->
  <tr>
       <td align="right"><b><app:storeMessage key="invoice.text.form.invoiceNumber" /></b></td>
       <td>
         <html:text  name="CUST_ACCT_MGT_INVOICE_SEARCH_FORM" property="invoiceDistNum" size="14" />
       </td>
       <td colspan="2">&nbsp;</td>
  </tr>
  <!--  Invoice Date (Begin / End) -->
  <tr>
    <td>&nbsp;</td>
    <td ><app:storeMessage key="invoice.text.form.begin" /></td>
    <td ><app:storeMessage key="invoice.text.form.end" /></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
       <td align="right"><b><app:storeMessage key="invoice.text.form.invoiceDate" /></b><br>(mm/dd/yyyy)</td>
      <td align="left"><nobr>
          <html:text name="CUST_ACCT_MGT_INVOICE_SEARCH_FORM" property="invoiceDistDateRangeBegin" maxlength="10" size="10" />
          <% if ("Y".equals(isMSIE)) { %>
          <a href="#"
             onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DATEFROM, document.forms[0].invoiceDistDateRangeBegin, null, -7300, 7300, null, false);"
             title="Choose Date"><img name="DATEFROM" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                        align="absmiddle" style="position:relative"
                        onmouseover="window.status='Choose Date';return true"
                        onmouseout="window.status='';return true"></a>
          <% } else { %>
          <a href="javascript:show_calendar('forms[0].invoiceDistDateRangeBegin');"
             onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;"
             title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
          <% } %></nobr>
      </td>
      <td align="left"><nobr>
          <html:text name="CUST_ACCT_MGT_INVOICE_SEARCH_FORM" property="invoiceDistDateRangeEnd" maxlength="10" size="10" />
          <% if ("Y".equals(isMSIE)) { %>
          <a href="#"
             onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DATETO, document.forms[0].invoiceDistDateRangeEnd, null, -7300, 7300, null, false);"
             title="Choose Date"><img name="DATETO" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                        align="absmiddle" style="position:relative"
                        onmouseover="window.status='Choose Date';return true"
                        onmouseout="window.status='';return true"></a>
          <% } else { %>
          <a href="javascript:show_calendar('forms[0].invoiceDistDateRangeEnd');"
             onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;"
             title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
          <% } %></nobr>
      </td>


       <td  valign="bottom" align="left" >
                  <html:submit property="action"  >
                    <app:storeMessage  key="global.action.label.search"/>
                  </html:submit>
       </td>
  </tr>
  <tr> <td colspan="4">&nbsp;</td>  </tr>
</table>

<table width="100%">
<tr>
    <td>
    <app:storeMessage key="invoice.text.form.resultCount" />&nbsp;<bean:write name="CUST_ACCT_MGT_INVOICE_SEARCH_FORM" property="listCount" filter="true"/>
    </td>

</tr>
</table>
<!-- TBD Result table -->
<logic:greaterThan name="CUST_ACCT_MGT_INVOICE_SEARCH_FORM" property="listCount" value="0">

<table align=center width="100%" id="ts1" > <%--class="stpTable_sortable" --%>
<thead>
<tr>
<bean:define name="CUST_ACCT_MGT_INVOICE_SEARCH_FORM" id="pagesize"  property="listCount"/>
<%-- display invoice results --%>
<%if("true".equalsIgnoreCase(invoiceDisply)){%>
  <th class="shopcharthead"><app:storeMessage key="invoice.text.result.invoiceNumber" /></th>
  <th class="shopcharthead"><app:storeMessage key="invoice.text.result.invoiceDate" /><br /><app:storeMessage key="invoice.text.result.recvdDate" /></th>
  <th width="50" class="shopcharthead"><app:storeMessage key="invoice.text.result.poNumber" /></th>
<%--  <th class="shopcharthead">PO&nbsp;Date</th>  --%>
  <th class="shopcharthead"><app:storeMessage key="invoice.text.result.siteName" /></th>
  <th class="shopcharthead"><app:storeMessage key="invoice.text.result.city" /></th>
  <th class="shopcharthead"><app:storeMessage key="invoice.text.result.state" /></th>
  <th class="shopcharthead"><app:storeMessage key="invoice.text.result.zipCode" /></th>
  <th class="shopcharthead"><app:storeMessage key="invoice.text.result.status" /></th>

  </tr>
</thead>
<tbody id="resTblBdy">

	<logic:iterate name="CUST_ACCT_MGT_INVOICE_SEARCH_FORM" id="inv"  property="resultList"
		 offset="0" length="<%=pagesize.toString()%>" indexId="i" type="com.cleanwise.service.api.value.InvoiceDistData">
<%try{%>
	 <%-- Deine some variables --%>
	 <%String invLinkHref = null;%>
	 <%String poLinkHref = null;%>
	 <logic:present name="inv" property="invoiceDistId">
		<bean:define id="key"  name="inv" property="invoiceDistId"/>
		<%invLinkHref = new String(detailURI+"?action="+invDetailAction+"&id=" + key);%>
	 </logic:present>

      <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
	  <td>
		  <logic:present name="inv" property="invoiceNum">
		  	  <%if(invLinkHref != null){%>
				<a href="<%=invLinkHref%>"><bean:write name="inv" property="invoiceNum"/></a>
			  <%}else{%>
				<bean:write name="inv" property="invoiceNum"/>
			  <%}%>
		  </logic:present>
	  </td>
	  <td>
		<logic:present name="inv" property="invoiceDate">
		  <bean:define id="invDate"  name="inv" property="invoiceDate"/>
		  <i18n:formatDate value="<%=invDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
		</logic:present>
		/<br>
		<logic:present name="inv" property="addDate">
		  <bean:define id="addDate"  name="inv" property="addDate"/>
		  <i18n:formatDate value="<%=addDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
		</logic:present>
	  </td>
	  <td width="50">
                  <logic:present name="inv" property="erpPoNum">
                    <bean:write name="inv" property="erpPoNum"/>
                  </logic:present>
	  </td>
<%--
	  <td>
		<logic:present name="inv" property="purchaseOrderData.poDate">
		  <bean:define id="poDate"  name="inv" property="purchaseOrderData.poDate"/>
		  <i18n:formatDate value="<%=poDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
		</logic:present>
	  </td>
 --%>
	  <%
	  boolean useInvoiceShippingInfo = false;
	  if(inv!= null){
		if(inv.getShipToName() != null || inv.getShipToAddress1() != null||
			inv.getShipToCity() != null || inv.getShipToState() != null ||
			inv.getShipToPostalCode() != null){
            %>
            <logic:notPresent name="inv" property="shipToAddress">
            <%
				useInvoiceShippingInfo = true;
            %>
            </logic:notPresent>
            <%
		}
	  }
	  if(useInvoiceShippingInfo){%>
             <td><bean:write name="inv" property="shipToName"/><br />
               <logic:present name="inv" property="shipToAddress.shortDesc">
                 <bean:write name="inv" property="shipToAddress.shortDesc" ignore="true"/>
               </logic:present>
             </td>
	     <td><bean:write name="inv" property="shipToCity"/></td>
	     <td><bean:write name="inv" property="shipToState"/></td>
	     <td><bean:write name="inv" property="shipToPostalCode"/></td>
	  <%}else{%>
		  <logic:present name="inv" property="shipToAddress">
			  <td><bean:write name="inv" property="shipToAddress.shortDesc" ignore="true"/></td>
			  <td><bean:write name="inv" property="shipToAddress.city" ignore="true"/></td>
			  <td><bean:write name="inv" property="shipToAddress.stateProvinceCd" ignore="true"/></td>
			  <td><bean:write name="inv" property="shipToAddress.postalCode" ignore="true"/></td>
		  </logic:present>
		  <logic:notPresent name="inv" property="shipToAddress">
			<td colspan="4">&nbsp;</td>
		  </logic:notPresent>
	  <%}%>
	  <td>
		<logic:present name="inv" property="invoiceStatusCd">
		   <% 
		   String invStatus = inv.getInvoiceStatusCd();
		   String invStatusUI = invStatus;
           if(RefCodeNames.INVOICE_STATUS_CD.CANCELLED.equals(invStatus)) {
	          invStatusUI = "Cancelled";
		   }
           else if(RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_RELEASED.equals(invStatus)) {
	          invStatusUI = "Released";
		   }
           else if(RefCodeNames.INVOICE_STATUS_CD.DUPLICATE.equals(invStatus)) {
	          invStatusUI = "Duplicated";
		   }
           else if(RefCodeNames.INVOICE_STATUS_CD.INVOICE_HISTORY.equals(invStatus)) {
	          invStatusUI = "History";
		   }
           else if(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW.equals(invStatus)) {
              invStatusUI = "Pending Review";
		   }
		   %>
            <%=invStatusUI%>
		</logic:present>
	  </td>
    </tr>
<%}catch(Exception e){e.printStackTrace();}%>
	 </logic:iterate>
</tbody>
<%}%>
</table>
</logic:greaterThan>
<!-- end of Result table -->
</form>
</td></tr></table>
</div>




