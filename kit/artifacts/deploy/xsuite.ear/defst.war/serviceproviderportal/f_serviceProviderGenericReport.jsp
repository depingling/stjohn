
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.reporting.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.math.BigDecimal" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<style type="text/css">

.scrollTable {
	width: 748;
	height: 300;
	overflow: auto;
}

</style>

<bean:define id="IMGPath" type="java.lang.String" 
  name="pages.store.images"/>
<bean:define id="thisReportCd" name="SERVICE_PROVIDER_REPORT_FORM" 
 type="java.lang.String" property="reportTypeCd"/>
<bean:define id="theForm" name="SERVICE_PROVIDER_REPORT_FORM" 
 type="com.cleanwise.view.forms.ServiceProviderMgtReportForm"/>
<tr>
<td>
<table align="center" border="0" cellspacing="2" cellpadding="2" width="746">

<tr><td align="left" cplspan=25>
<div class="fivemargin"><img src="<%=IMGPath%>/cw_spacer.gif" height="5"><br></div>

<%
  // See if there is a translation for the report name.
  String xlateReportname = ClwI18nUtil.getMessageOrNull(request, 
    ReportingUtils.makeReportNameKey(thisReportCd)
  );
String isEmpty = "false";
  if ( xlateReportname == null ) {
    // No translation found, use the default
    xlateReportname = thisReportCd;
  }

%>

<div class="subheadergeneric"><%=xlateReportname%></div>
</td>

</tr>

<tr>
<td align="left">
<% 


// There is a current assumption that one account will transact all
// purchasing in one currency.
//
CleanwiseUser appUser = ShopTool.getCurrentUser(request);
Locale thisUserLocale = appUser.getPrefLocale();
String purchasesLocale = purchasesLocale = "en_US";
   
GenericReportResultViewVector results = theForm.getReportResults();
GenericReportResultView currPage = null;
if(results!=null && results.size()>0) {
for(int ii=results.size()-1; ii>=0; ii--) {
  String disable = "true";
  GenericReportResultView result = (GenericReportResultView) results.get(ii);
  String actionValue = "";
  String classType = "";
  if(ii==theForm.getGenericReportPageNum()) {
    currPage = result;
    disable = "true";
    classType = "store_fb";
  } else {
   disable = "false";
   classType = "wkrule";
   
  }
  if(result.getTable().size()==0){
	isEmpty="true";
  }
  actionValue = result.getName();
  // See if there is a translation for the report tab.
  String tabName = ClwI18nUtil.getMessageOrNull(request, 
    ReportingUtils.makeTabKey(actionValue));
  if ( null == tabName ) tabName = actionValue;
  if(actionValue==null || actionValue.trim().length()==0) {
   actionValue = "Page "+ii;
  }

  if(tabName!=null && tabName.trim().length()>0) {

%>

<span class="linkButtonBorder">
<a class="linkButton"
   href="serviceProviderMgtReportResult.do?action=<%=actionValue%>">
  <%=tabName%> </a>&nbsp; </span>

<% } }%>

</td>
<td align="right">

<%
  String dnwlcmd = "Download Report";
  String tab2Name = ClwI18nUtil.getMessageOrNull
    (request, ReportingUtils.makeTabKey(dnwlcmd));
  if ( null == tab2Name ) tab2Name = dnwlcmd;

%>


<a class="linkButton"
   href="serviceProviderMgtReportResult.do?action=<%=dnwlcmd%>">
<span class="linkButtonBorder">
  <%=tab2Name%>
</span>
</a>



</td>
</tr>
<%if(xlateReportname.equals("Daily Back Order Report") && isEmpty.equals("true")){ %>
 <tr> 
 <td colspan="3" align="center">
 <div class="text"><font color=red><b><app:storeMessage key="report.text.noData"/></b></font></div>
 </td></tr> 
 <%} %>
<tr><td colspan="3">

<%
// Default to the account currency code.
String thisCurrencyCode = "USD";
CurrencyData currData = ClwI18nUtil.getCurrency(purchasesLocale);
if ( currData != null && currData.getGlobalCode() != null ) {
	thisCurrencyCode = currData.getGlobalCode();	
}

%>

<table align="center" cellspacing="0" cellpadding="0" width="748">
<tr><td>
<%    String tableWidth = currPage.getWidth(); 
      String tableWidth1 = "100%";
	  boolean fancyDisplay = currPage.getFancyDisplay();
      try {
        int tw = Integer.parseInt(tableWidth)-10;
        tableWidth1 = ""+tw;
      } catch (Exception exc) {} 
      if(tableWidth==null) tableWidth = "*";
%>
<div class="scrollTable" id="testScrollTable">
   <table  cellpadding="2" cellspacing="2" width="<%=tableWidth%>" <%if(fancyDisplay){%> style="border-collapse: collapse;" border="0" cellspacing="0" cellpadding="0"<%}%>>
              <tr>
 <% 
    GenericReportColumnViewVector header = currPage.getHeader();
    int[] colFormat = new int[header.size()];
    int[] colScale = new int[header.size()];
    Object[] total = new Object[header.size()];
    boolean[] totalFl = new boolean[header.size()];
    boolean totalRowFl = false;
    String[] colWidth = new String[header.size()];
    for(int ii=0; ii<header.size(); ii++) {
      GenericReportColumnView column = (GenericReportColumnView) header.get(ii);
      String colName = column.getColumnName();
      String type = column.getColumnType();
      String colClass = column.getColumnClass();
      String cWidth = column.getColumnWidth();
      totalFl[ii] = column.getTotalRequestFlag();
      if(cWidth==null) cWidth = "*";
      colWidth[ii] = cWidth;
      int scale = column.getColumnScale();
      colScale[ii] = scale;
      if(colClass.equals("java.math.BigDecimal")) {

         if(totalFl[ii]) totalRowFl = true;
         total[ii] = new BigDecimal(0);
         if(ReportingUtils.isColumnForMoney(colName)) {
           colName = ReportingUtils.extractColumnName(colName);
           colFormat[ii] = 1; //accountingFormat;
         } else if(ReportingUtils.isColumnForPercent(colName)) {
           colName = ReportingUtils.extractColumnName(colName);
           colFormat[ii] = 2; //percentFormat;
         } else if(scale==0) {
           colFormat[ii] = 3; //integerFormat;
         } else {
           colFormat[ii] = 4; //floatFormat;
         }
      } else if(colClass.equals("java.lang.Integer")) {
         if(totalFl[ii]) totalRowFl = true;
         total[ii] = new Integer(0);
         colFormat[ii] = 3; //integerFormat;
      } else if(colClass.equals("java.sql.Timestamp")) {
         if(ReportingUtils.isColumnForTime(colName)) {
           colFormat[ii] = 5; //timeFormat;
         } else {
           colFormat[ii] = 6; //dateFormat;
         }
       } else {
          colFormat[ii] = 0; //stringFormat (default);
       }
%>

<%    if ( ! colName.toLowerCase().startsWith("rowinfo_")){  

	// Check for a message equivalent of the column name.
	String tColKey = ReportingUtils.makeColumnKey(colName);
	String finalColName = ClwI18nUtil.getMessageOrNull(request, tColKey);
	if ( null == finalColName ) finalColName = colName;
%>
      <td width="<%=colWidth[ii]%>" align="center"><b><%=finalColName%></b></td>
<%    }  %>

 <%} //column loop %>
      </tr>

<% 
  String tdStyle;
  if(fancyDisplay){
	tdStyle = " class=\"rtext2\" ";
  }else{
	tdStyle = " ";
  }
  int colNums = header.size();
  ArrayList table = currPage.getTable();
  for(int ii=0; ii<table.size(); ii++) {
    ArrayList row = (ArrayList) table.get(ii);
%>
<tr>


<%
// See if a currency code is defined.
for(int i=0; i<row.size(); i++) {
  Object value = row.get(i);
  if(value instanceof java.lang.String){ 
    String t = (java.lang.String)value;
    if ( t.toLowerCase().startsWith("rowinfo_currencycd=")){
      thisCurrencyCode = t.substring(19);
    }
  }
}

%>

<%  for(int jj=0; jj<row.size(); jj++) {%>
<%
	
    String tdRight = "<td"+tdStyle+"width=\""+colWidth[jj]+"\" align=\"right\">";
	String tdLeft = "<td "+tdStyle+"width=\""+colWidth[jj]+"\" align=\"left\">";
	String tdEmpty = "<td>";
    Object value = row.get(jj);
%>
<%
    //Object value = row.get(jj);
    if(value==null) { 
%>
  <%=tdEmpty%>&nbsp;</td>
<% continue; } %>

<% 
if(value instanceof java.lang.String){ 
  String t = (java.lang.String)value;
  if ( t.toLowerCase().startsWith("rowinfo_")){
    continue;
  }
}
%>

<% if(colFormat[jj]==1 && (value instanceof java.math.BigDecimal)) { %>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<%=tdRight%>

<%=ClwI18nUtil.formatCurrencyAmount(request, value, thisCurrencyCode)%>
</td>	
<% } else if (colFormat[jj]==2 && (value instanceof java.math.BigDecimal)) {%>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<%=tdRight%><i18n:formatPercent value="<%=value%>" locale="<%=thisUserLocale%>" />&nbsp;</td>	
<% } else if (colFormat[jj]==3 && (value instanceof java.math.BigDecimal) ) {%>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" />&nbsp;</td>	
<% } else if (colFormat[jj]==4 && (value instanceof java.math.BigDecimal) ) {%>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" />&nbsp;</td>
<% } else if(colFormat[jj]==3 && (value instanceof java.lang.Integer)){ %>
<% if(totalFl[jj]) total[jj] = new Integer(((Integer) total[jj]).intValue()+((Integer)value).intValue());%>
<%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" />&nbsp;</td>	
<% } else if(colFormat[jj]==5 && (value instanceof java.util.Date)){ %>
<%=tdRight%><i18n:formatTime value="<%=value%>" locale="<%=thisUserLocale%>"/>&nbsp;</td>
<% } else if (colFormat[jj]==6 && (value instanceof java.util.Date)) {%>
<%=tdRight%><i18n:formatDate value="<%=value%>" locale="<%=thisUserLocale%>"/>&nbsp;</td>
<% } else { %>
<%=tdLeft%> <%=value%>&nbsp;</td>
<% } %>
<% } //end of row %>
</tr>
<% } //end of table %>             
<% if(totalRowFl) {%>
<tr>
<%  for(int jj=0; jj<totalFl.length; jj++) {
String tdRight = "<td"+tdStyle+"width=\""+colWidth[jj]+"\" align=\"right\">";
String tdLeft = "<td "+tdStyle+"width=\""+colWidth[jj]+"\" align=\"left\">";
String tdEmpty = "<td width=\""+colWidth[jj]+"\">";
%>
<% if(colFormat[jj]==1 && (total[jj] instanceof java.math.BigDecimal) && totalFl[jj]) { %>
<%=tdRight%><b>
<%=ClwI18nUtil.formatCurrencyAmount(request, total[jj], thisCurrencyCode)%>
</b>&nbsp;</td>	
<% } else if (colFormat[jj]==2 && (total[jj] instanceof java.math.BigDecimal) && totalFl[jj]) {%>
<%=tdRight%><b><i18n:formatPercent value="<%=total[jj]%>" locale="<%=thisUserLocale%>" /></b>&nbsp;</td>	
<% } else if (colFormat[jj]==3 && (total[jj] instanceof java.math.BigDecimal) && totalFl[jj]) {%>
<%=tdRight%><b><i18n:formatNumber value="<%=total[jj]%>" locale="<%=thisUserLocale%>" /></b>&nbsp;</td>	
<% } else if (colFormat[jj]==4 && (total[jj] instanceof java.math.BigDecimal) && totalFl[jj]) {%>
<%=tdRight%><b><i18n:formatNumber value="<%=total[jj]%>" locale="<%=thisUserLocale%>" /></b>&nbsp;</td>
<% } else if(colFormat[jj]==3 && (total[jj] instanceof java.lang.Integer) && totalFl[jj]){ %>
<%=tdRight%><b><i18n:formatNumber value="<%=total[jj]%>" locale="<%=thisUserLocale%>" /></b>&nbsp;</td>	
<% } else { %>
  <% if(jj==0) {%>
<%=tdEmpty%><b>Total:</b>&nbsp;</td>
  <% } else { %>
<%=tdEmpty%>&nbsp;</td>
  <% } %>
<% } %>
<% } //end of row %>
</tr>
<% } %>
         </table>
</div>
 </td></tr>
</table>
<%} // end of results%>
</td></tr>
</table>
<!------------------------------------->
</td>
</tr>



