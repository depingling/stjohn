
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
<%@ page import="java.util.HashMap" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>



<bean:define id="IMGPath" type="java.lang.String"
  name="pages.store.images"/>
<bean:define id="thisReportCd" name="CUSTOMER_REPORT_FORM"
 type="java.lang.String" property="reportTypeCd"/>
<bean:define id="theForm" name="CUSTOMER_REPORT_FORM"
 type="com.cleanwise.view.forms.CustAcctMgtReportForm"/>
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

  if ( xlateReportname == null ) {
    // No translation found, use the default
    xlateReportname = thisReportCd;
  }
%>
<%if(!xlateReportname.equals("Daily Back Order Report")){%>
<div class="subheadergeneric"><%=xlateReportname%></div>
<%}%>
</td>

</tr>

<tr>
<td align="left">

  <%// <table border= 1 > <tr > %>
<table border= 0 > <tr ><td>
<%
// Names for Download controls
    String dnwlcmd = "Download to Excel";
    String tab2Name = ClwI18nUtil.getMessageOrNull
            (request, ReportingUtils.makeTabKey(dnwlcmd));
    if ( null == tab2Name ) tab2Name = dnwlcmd;

    String dnwlcmd2 = "Download to PDF";
    String tab2Name2 = ClwI18nUtil.getMessageOrNull(request, ReportingUtils.makeTabKey(dnwlcmd2));
    if (null == tab2Name2) tab2Name2 = dnwlcmd2;
%>
<%
// There is a current assumption that one account will transact all
// purchasing in one currency.
//
CleanwiseUser appUser = ShopTool.getCurrentUser(request);
Locale thisUserLocale = appUser.getPrefLocale();
AccountData accountData = appUser.getUserAccount();
SiteData siteData = appUser.getSite();
ContractData contractData = siteData.getContractData();
String purchasesLocale = null;
boolean isEmpty = false;
if (siteData != null &&
    siteData.getContractData() != null ) {
  purchasesLocale = contractData.getLocaleCd();
}

if ( null == purchasesLocale ) { purchasesLocale = "en_US"; }
GenericReportResultViewVector results = theForm.getReportResults();
boolean boldStyle = false;
if(results!=null && results.size()>0) {
 GenericReportResultView currPage = (GenericReportResultView) results.get(0);
 boolean correctPdf = true;
 int tabWidSum = 0;
 int tabLines = 0;
 %>
  <table><tr>
 <%
 for(int ii=results.size()-1; ii>=0; ii--) {
  String disable = "true";
  GenericReportResultView result = (GenericReportResultView) results.get(ii);
  correctPdf = correctPdf && PdfReportWritter.isCorrectReport(result);
  String actionValue = "";
  String classType = "";

//  if(result.getTable().size()==0){
//    isEmpty= "true";
//  }

  if(ii==theForm.getGenericReportPageNum()) {
    currPage = result;
    disable = "true";
    classType = "store_fb";
  } else {
   disable = "false";
   classType = "wkrule";

  }
  actionValue = result.getName();
  // See if there is a translation for the report tab.
  String tabName = ClwI18nUtil.getMessageOrNull(request,
    ReportingUtils.makeTabKey(actionValue));
  if ( null == tabName ) tabName = actionValue;
  if(actionValue==null || actionValue.trim().length()==0) {
   actionValue = "Page "+ii;
  }
  tabWidSum += tabName.trim().length();
  if (tabWidSum >= 100 ) {
    tabWidSum = tabName.trim().length();
    tabLines++;
  %>
  </tr></table>
  <table><tr>
  <% }
  if(tabName!=null && tabName.trim().length()>0) {

%>
  <td>
            <table>
                <tr>
                    <td  class="linkButtonBorder">
                        <a class="linkButton"
                           href="custAcctMgtReportResult.do?action=<%=actionValue%>">
                            <%=tabName%>
                        </a>
                        &nbsp;
                    </td>
                </tr>
            </table>
</td>

<%
        }
if ( ii==0){
  %>
  </tr></table>
  <%  }
}
%>

<%//  </tr ></table> %>
</td></tr ></table>
</td>

<%
   isEmpty = (currPage != null && currPage.getTable()!= null && currPage.getTable().size()==0);
   if( isEmpty &&
       (   xlateReportname.equals("Forecast Order Summary by Item") ||
           xlateReportname.equals("Forecast Order Summary by Location") ||
           xlateReportname.equals("Spend vs Budget by Category / Subcategory")
        )
      ) { %>
<tr>
<td colspan="3" align="center">
<div class="text"><font color=red><b><app:storeMessage key="report.text.noData"/></b></font></div>
</td></tr>
<%} else { %>
<%tabWidSum += tab2Name.length() + tab2Name2.length();%>
<%if ( tabLines > 1 || tabWidSum >= 100  ){ %>
    <tr><%@ include file="f_customerGenericReportDownload.jsp"%></tr>
 <%} else {%>
    <%@ include file="f_customerGenericReportDownload.jsp"%>
 <% } %>
</tr>
<%--Display the date parameters for xpedx--%>
<tr><td><b>
<%
    GenericReportControlViewVector grcVV = theForm.getReportControls();
    String beginDate = null;
    String endDate = null;
    String endMonth = null;
    String endYear = null;

    if(grcVV!=null && grcVV.size()>0) {
       for(int ii=0; ii<grcVV.size(); ii++) {
            GenericReportControlView grc = (GenericReportControlView) grcVV.get(ii);
            String name = grc.getName();
            if("BEG_DATE".equals(name) || "BEG_DATE_OPT".equals(name)){
                 beginDate = grc.getValue();
            }else if("END_DATE".equals(name) || "END_DATE_OPT".equals(name)){
                endDate = grc.getValue();
            }else if("endYear".equals(name) || "endYear_OPT".equals(name)){
            	endYear = grc.getValue();
            }else if("endMonth".equals(name) || "endMonth_OPT".equals(name)){
            	endMonth = grc.getValue();
            }
       }
    }
    if(Utility.isSet(beginDate) && Utility.isSet(endDate)){%>
Date Range: <%=beginDate%> - <%=endDate%>
    <%}else if(Utility.isSet(beginDate)){%>
Begin Date: <%=beginDate%>
    <%}else if(Utility.isSet(endDate)){%>
End Date: <%=endDate%>
    <%}else if(Utility.isSet(endMonth)){
    	if(Utility.isSet(endYear)){%>
End Date: <%=endMonth%>/<%=endYear%>
		<%}else{%>
End Date: <%=endMonth%>
		<%}%>
    <%}
%>
</b></td></tr>
 <%
    if( isEmpty && xlateReportname.equals("Daily Back Order Report") ) { %>
 <tr>
 <td colspan="3" align="center">
 <div class="text"><font color=red><b><app:storeMessage key="report.text.noData"/></b></font></div>
 </td></tr>
 <%} %>
 <%--------------params-------%>
<%--Displaying title will cause the Date Range parameters to show up 2x times for forecast summary reports --%>

<%--------------params-------%>
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
<%
    String tableWidth = currPage.getWidth();

    String tableWidth1 = "100%";
    int tableWidthInt = 0;

    boolean fancyDisplay = currPage.getFancyDisplay();
    try {
        tableWidthInt = Integer.parseInt(tableWidth);
        int tw = tableWidthInt - 10;
        tableWidth1 = "" + tw;
    } catch (Exception exc) {
    }
    if (tableWidth == null) tableWidth = "100%";

    int defaultTableWidth;
    try {
        defaultTableWidth = Integer.parseInt(Constants.TABLEWIDTH);
    } catch (NumberFormatException e) {
        defaultTableWidth = 749;
    }

    if (tableWidthInt > 0 && tableWidthInt < defaultTableWidth) {
        tableWidthInt = defaultTableWidth;
    }
%>

<link rel="stylesheet" type="text/css" href="../externals/dojo_1.1.0/dojox/grid/_grid/Grid.css"/>
<%
     if (currPage ==null){
       currPage = (GenericReportResultView)results.get(0);
     }

    HashMap styleCollection = (HashMap)ReportingUtils.createStyles( currPage.getUserStyle() );

    GenericReportColumnViewVector header = currPage.getHeader();

    int allColumnsWidth = 0;
    int cWidthInt[] = new int[header.size()];
    for (int ii = 0; ii < header.size(); ii++) {
        GenericReportColumnView column = (GenericReportColumnView) header.get(ii);
        try {
            cWidthInt[ii] = Integer.parseInt(column.getColumnWidth());
            allColumnsWidth += cWidthInt[ii];
        } catch (NumberFormatException e) {
            allColumnsWidth = 0;
            cWidthInt = new int[header.size()];
            break;
        }
    }


%>
<%
ArrayList boldStyleRowNumList = new ArrayList();
for (int ii = 0; ii < currPage.getTable().size(); ii++) {
  ArrayList repRow = (ArrayList) currPage.getTable().get(ii);
  Object obj = repRow.get(0);
  if (obj != null && obj instanceof HashMap && ((HashMap)obj).get("BOLD")!=null){
    boldStyleRowNumList.add(""+ii);
   } else if (obj != null && obj instanceof GenericReportCellView ) {
     GenericReportStyleView style = (GenericReportStyleView)styleCollection.get(((GenericReportCellView)obj).getStyleName());
     if (style!= null && style.getFontType()!= null && style.getFontType().toUpperCase().equals("BOLD")){
        boldStyleRowNumList.add(""+ii);
     }
   }
}
%>
<script language="JavaScript1.2">

    dojo.require("dojox.grid.Grid");
    dojo.require("dojox.data.HtmlStore");

    function getRow(inRowIndex) {
        return ' ' + inRowIndex;
    }
    function fitHeight() {
       <%
        String auto = null;
        if (currPage.getTable().size() < 7){
           auto = "true";
         } else {
           auto = "false";
        } %>
        dijit.byId("reportGrid").autoWeight = false;
        dijit.byId("reportGrid").autoHeight = <%=auto%>;
        dijit.byId("reportGrid").update();
    }
	
    function onStyleRow(inRow) {
        with (inRow) {
            var i = index;

            var oCell=document.getElementById('reportable').rows[i+1].cells;
            var special = (oCell[0].className == 'specialRow');
            var right = false;
            if (special){
                customClasses += ' specialRow';
            }
            if (right){
                customClasses += ' rightRow';
            }
            dojox.Grid.prototype.onStyleRow.apply(this, arguments);

        }
    }
    var layoutHtmlTable = [
    { type: 'dojox.GridRowView', width: '20px' },

    { cells: [[
            <%
                int pxWidth;
                header = currPage.getHeader();
                for(int ii=0,j=0; ii<header.size(); ii++) {

                      GenericReportColumnView column = (GenericReportColumnView) header.get(ii);
                      String colName = column.getColumnName();
                      String colClass = column.getColumnClass();

                      GenericReportStyleView styleView = null;
                      String colDataStyle = column.getColumnDataStyleName();
                      if (Utility.isSet(colDataStyle) && styleCollection.containsKey(colDataStyle)){
                        styleView = (GenericReportStyleView)styleCollection.get(colDataStyle);
                      }
                      if (!Utility.isSet(colClass) ){
                        colClass= ( styleView != null) ? styleView.getDataClass() : "java.lang.String";
                      }

                      pxWidth = 0;

                      if(allColumnsWidth > 0 && tableWidthInt > 0 && cWidthInt[ii] > 0){
                         pxWidth = (int) (tableWidthInt*((double)cWidthInt[ii])/(double)allColumnsWidth);
                      }

                      String colStyle = "text-align: right;";
                      if (colClass.equals("java.lang.String")) {
                        colStyle = "text-align: left;";
                      }

                     //apply color to the column in case if color exists and can be converted to html color
                     if(styleView != null && styleView.getFillColor() != null) {
                        String bgColor = ReportingUtils.convertPOIColor(styleView.getFillColor());
                        if(Utility.isSet(bgColor)){
                            colStyle+="background-color:"+bgColor+";";
                        }
                     }

                      if (colClass.equals("java.math.BigDecimal")) {
                        if (ReportingUtils.isColumnForMoney(colName)) {
                            colName = ReportingUtils.extractColumnName(colName);
                        } else if (ReportingUtils.isColumnForPercent(colName)) {
                            colName = ReportingUtils.extractColumnName(colName);
                        }
                    }

             String finalColNameS = colName;
             if (ReportingUtils.isColumnForMoney(colName)) {
                finalColNameS=ReportingUtils.extractColumnName(colName);
             }

             if ( ! colName.toLowerCase().startsWith("rowinfo_")){

               String tColKey = ReportingUtils.makeColumnKey(colName);
               String finalColName = ClwI18nUtil.getMessageOrNull(request, tColKey);

               if ( null == finalColName ) {
               finalColName = colName;
              }
            finalColName += ii; //nessesary if report has 2 or more equal column names with different values
            if(j>0){%> ,<%}%>

            <%

            if(pxWidth>0){ %>

             { name: '<%=finalColNameS%>', field: '<%=finalColName%>' ,width:'<%=pxWidth%>px', styles: '<%=colStyle%>'}

            <%} else { %>

             { name: '<%=finalColNameS%>', field: '<%=finalColName%>' ,width:'100px', styles: '<%=colStyle%>'}


            <%}%>
            <%
            j++;
            }
            }
 %>
            ]]}];


        dojo.addOnLoad(function(){
        dijit.byId("reportGrid").update();
        setTimeout(function() {
          fitHeight();
        },1);
     });


</script>
<style type="text/css">

.scrollTable {
	width: 748;
	overflow: auto;
}
#reportGrid {
	width: 100%;
	height: 300px;

}

td{
 font-size: 11px;
}

th {
 font-size: 11px;
}

</style>
<span dojoType="dojox.data.HtmlStore" jsId="htmlStore" dataId="reportable"></span>

	<span dojoType="dojox.grid.data.DojoData"
          jsId="dataModel"
          rowsPerPage="10"
          store="htmlStore"
          query="{}">
	</span>

<div id="reportGrid"
     dojoType="dojox.Grid"
     autoWidth="false" autoHeight="false"
     elasticView="1"
     model="dataModel"
     structure="layoutHtmlTable"
     onStyleRow="onStyleRow">
</div>

<div class="scrollTable" id="testScrollTable">
   <table id="reportable" cellpadding="2" style="display:none;" cellspacing="2" width="<%=tableWidth%>" <%if(fancyDisplay){%> style="display:none;border-collapse: collapse;" border="0" cellspacing="0" cellpadding="0"<%}%>>
  <thead>
      <tr>
 <%
     header = currPage.getHeader();
    //     HashMap styleCollection = (HashMap)ReportingUtils.createStyles( currPage.getUserStyle() );
     String[] colFormatS = new String[header.size()];
     boolean[] isColFormated = new boolean[header.size()];

     int[] colFormat = new int[header.size()];
     int[] colScale = new int[header.size()];
     Object[] total = new Object[header.size()];
     String[] colFormatPattern = new String[header.size()];
     boolean[] totalFl = new boolean[header.size()];
     boolean totalRowFl = false;
     String[] colWidth = new String[header.size()];
     for (int ii = 0; ii < header.size(); ii++) {
         GenericReportColumnView column = (GenericReportColumnView) header.get(ii);

         String colName = column.getColumnName();
         String type = column.getColumnType();
         String colClass = column.getColumnClass();
         String cWidth = column.getColumnWidth();
         int scale = column.getColumnScale();
         int precision = column.getColumnPrecision();
         colFormatPattern[ii] = Utility.strNN(column.getColumnFormat());

         // Column data style analizing---------
        GenericReportStyleView styleView = null;
        String colDataStyle = column.getColumnDataStyleName();
          if (Utility.isSet(colDataStyle) && styleCollection.containsKey(colDataStyle)){
              styleView = (GenericReportStyleView)styleCollection.get(colDataStyle);
              colFormatS[ii] = styleView.getDataFormat();
              isColFormated[ii] = true;
         }
         if (!Utility.isSet(colClass) ){
            colClass=( styleView != null) ? styleView.getDataClass() : "java.lang.String";
            colFormatPattern[ii] =( styleView != null) ? Utility.strNN(styleView.getDataFormat()) : "";
            scale = ( styleView != null) ? styleView.getScale() : 0;
         }
         //---------------------------------
         totalFl[ii] = column.getTotalRequestFlag();
         if (!Utility.isSet(cWidth) || cWidth.indexOf("*")>-1) cWidth = "15";
         colWidth[ii] = cWidth;
         colScale[ii] = scale;
         if (colClass.equals("java.math.BigDecimal")) {

             if (totalFl[ii]) totalRowFl = true;
             total[ii] = new BigDecimal(0);
             if (ReportingUtils.isColumnForMoney(colName)) {
                 colName = ReportingUtils.extractColumnName(colName);
                 colFormat[ii] = 1; //accountingFormat;
             } else if (ReportingUtils.isColumnForPercent(colName)||
                        colFormatPattern[ii].indexOf("%")>=0) {
                 colName = ReportingUtils.extractColumnName(colName);
                 colFormat[ii] = 2; //percentFormat;
             } else if (scale == 0) {
                 colFormat[ii] = 3; //integerFormat;
             } else {
                 colFormat[ii] = 4; //floatFormat;
             }
         } else if (colClass.equals("java.lang.Integer")) {
             if (totalFl[ii]) totalRowFl = true;
             total[ii] = new Integer(0);
             colFormat[ii] = 3; //integerFormat;
         } else if (colClass.equals("java.sql.Timestamp")) {
             if (ReportingUtils.isColumnForTime(colName)) {
                 colFormat[ii] = 5; //timeFormat;
             } else {
                 colFormat[ii] = 6; //dateFormat;
             }
         } else if (colClass.equals("java.math.BigDecimal.Separator")) {
            if ( precision == 0) {
                    colFormat[ii] = 7;//thousndsIntegerFormat;
            } else {
                    colFormat[ii] = 8;//thousndsFloatFormat;
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
        finalColName += ii; //nessesary if report has 2 or more equal column names with different values
%>
      <th width="<%=colWidth[ii]%>" align="center"><%=finalColName%></th>
<%    }  %>

 <%} //column loop %>
      </tr>
   </thead>

 <tbody>
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
<% continue;
} %>

<%
if(value instanceof java.lang.String){
  String t = (java.lang.String)value;
  if ( t.toLowerCase().startsWith("rowinfo_")){
    continue;
  }
}
%>
<%
String format = colFormatS[jj];
boolean isFormated = isColFormated[jj];
if(value instanceof HashMap){
  HashMap t = (HashMap)value;
  if ( ((HashMap)value).get("BOLD") != null ){
    value =  ((HashMap)value).get("BOLD");
    tdStyle = " class=\"specialRow\" " ;
    boldStyle = true;
   }
}

if (value instanceof GenericReportCellView){
     String styleName = ((GenericReportCellView)value).getStyleName();
     value = ((GenericReportCellView)value).getDataValue();

     if (Utility.isSet(styleName) && styleCollection.containsKey(styleName)){
        GenericReportStyleView style = (GenericReportStyleView)styleCollection.get(styleName);
        format = (style!= null) ? style.getDataFormat() : null;
     }
     isFormated = Utility.isSet(format);
}
%>

<% if(colFormat[jj]==1 && (value instanceof java.math.BigDecimal)) { %>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<%=tdRight%>

<%=ClwI18nUtil.formatCurrencyAmount(request, value, thisCurrencyCode)%>
</td>
<% } else if (colFormat[jj]==2 && (value instanceof java.math.BigDecimal)) {%>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<%  value = ((BigDecimal)value).multiply(new BigDecimal(100.0)).setScale(1, BigDecimal.ROUND_HALF_UP);%>
<%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>"  />%&nbsp;</td>
<%--<%=tdRight%><i18n:formatPercent value="<%=value%>" locale="<%=thisUserLocale%>" />&nbsp;</td>--%>
<% } else if (colFormat[jj]==3 && (value instanceof java.math.BigDecimal) ) {%>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<% if (isFormated) {%>
  <%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" pattern="<%=format%>"/>&nbsp;</td>
<%} else {%>
  <%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" />&nbsp;</td>
<%}%>
<% } else if (colFormat[jj]==4 && (value instanceof java.math.BigDecimal) ) {%>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<% if (isFormated) {%>
  <%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" pattern="<%=format%>"/>&nbsp;</td>
<%} else {%>
  <%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" />&nbsp;</td>
<%}%>
<% } else if(colFormat[jj]==3 && (value instanceof java.lang.Integer)){ %>
<% if(totalFl[jj]) total[jj] = new Integer(((Integer) total[jj]).intValue()+((Integer)value).intValue());%>
<%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" />&nbsp;</td>
<% } else if(colFormat[jj]==5 && (value instanceof java.util.Date)){ %>
<%=tdRight%><i18n:formatTime value="<%=value%>" locale="<%=thisUserLocale%>"/>&nbsp;</td>
<% } else if (colFormat[jj]==6 && (value instanceof java.util.Date)) {%>
<%=tdRight%>
<%=ClwI18nUtil.formatDate(request, (java.util.Date)value)%>
<%--
<i18n:formatDate value="<%=value%>" locale="<%=thisUserLocale%>"/>
 --%>
&nbsp;</td>

<% } else if (colFormat[jj]==7 && (value instanceof java.math.BigDecimal) ) {%>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<% if (isFormated) {%>
  <% if (format.indexOf("(")>=0){%>
    <%=tdRight%>(<i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" pattern="<%=format%>"/>)&nbsp;</td>
  <%} else {%>
    <%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" pattern="<%=format%>"/>&nbsp;</td>
  <%}%>
<%} else {%>
  <%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" pattern="##,##0" />&nbsp;</td>
<%}%>

<% } else if (colFormat[jj]==8 && (value instanceof java.math.BigDecimal) ) {%>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<% if (isFormated) {%>
  <%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" pattern="<%=format%>"/>&nbsp;</td>
<%} else {%>
  <%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" pattern="##,###.##"/>&nbsp;</td>
<%}%>

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
</td>
<%} // end if empty results%>

</tr>
</table>
<!------------------------------------->
</td>
</tr>





