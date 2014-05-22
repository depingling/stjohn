<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.reporting.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.NumberFormat" %>
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
 <bean:define id="theForm" name="ANALYTIC_REPORT_FORM"
  type="com.cleanwise.view.forms.AnalyticReportForm"/>
<tr>
<td>
<table align="center" border="0" cellspacing="2" cellpadding="2" width="746">

<tr><td align="left" cplspan=25>
<div class="fivemargin"><img src="<%=IMGPath%>/cw_spacer.gif" height="5"><br></div>
<% if(theForm.getReport()!=null) {%>
<div class="subheadergeneric"><%=theForm.getReport().getName()%></div>
 <%}%>
</td>

</tr>

<tr>
<td align="left">


<table border= 0 > <tr ><td>

<%


// There is a current assumption that one account will transact all
// purchasing in one currency.
//
CleanwiseUser appUser = ShopTool.getCurrentUser(request);
Locale thisUserLocale = appUser.getPrefLocale();
String purchasesLocale = "en_US";

GenericReportResultViewVector results = theForm.getReportResults();
GenericReportResultView currPage = null;
boolean boldStyle = false;
if(results!=null && results.size()>0) {
boolean correctPdf = true;
int tabWidSum = 0;
%> <table><tr>  <%
for(int ii=results.size()-1; ii>=0; ii--) {
//for(int ii=0; ii<results.size(); ii++) {

  String disable = "true";
  GenericReportResultView result = (GenericReportResultView) results.get(ii);

  correctPdf = correctPdf && PdfReportWritter.isCorrectReport(result);
  String actionValue = "";
  String classType = "";

  if(ii==theForm.getGenericReportPageNum()) {
//  if(ii==results.size()-1) {
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
                        <a class="linkButton" href="#"
                           onclick="
                              document.forms['ANALYTIC_REPORT_FORM_ID'].elements['genericReportPageNum'].value = pageNum = <%=ii%>;
                              setAndSubmit('ANALYTIC_REPORT_FORM_ID','command','View Report');
                              return false;
                           "><%=tabName%></a>
                        &nbsp;
                    </td>
                </tr>
            </table>
</td>

<%
        }
 if ( ii==0 ){  %>
    </tr></table>
  <%  }
}
%>

</td></tr ></table>


</td>

<td align="right">
<%--    <table>
        <tr>
            <%
                String dnwlcmd = "Download to Excel";
                String tab2Name = ClwI18nUtil.getMessageOrNull
                        (request, ReportingUtils.makeTabKey(dnwlcmd));
                if ( null == tab2Name ) tab2Name = dnwlcmd;

                String dnwlcmd2 = "Download to PDF";
                String tab2Name2 = ClwI18nUtil.getMessageOrNull(request, ReportingUtils.makeTabKey(dnwlcmd2));
                if (null == tab2Name2) tab2Name2 = dnwlcmd2;
            %>
            <td>
                <table>
                    <tr>
                        <td  class="linkButtonBorder">
                            <a class="linkButton"
                               href="custAcctMgtReportResult.do?action=<%=dnwlcmd%>">
                                <%=tab2Name%>
                            </a>
                        </td>
                    </tr>
                </table>
            </td>
            <% if (correctPdf == true) { %>
            <td>
                <table>
                    <tr>
                        <td  class="linkButtonBorder">
                            <a class="linkButton"
                               href="custAcctMgtReportResult.do?action=<%=dnwlcmd2%>">
                                <%=tab2Name2%>
                            </a>
                        </td>
                    </tr>
                </table>
            </td>
            <%}%>

        </tr>
    </table> --%>
</td>
</tr>
<%--------------params-------%>
<%
     if (currPage ==null){
       currPage = (GenericReportResultView)results.get(0);
     }
     GenericReportColumnViewVector title = currPage.getTitle();
      if (title != null)  {
        int columnCountTitle = title.size();
        for (int ii = 1; ii < columnCountTitle; ii++) {
                GenericReportColumnView column = (GenericReportColumnView) title.get(ii);
                String colName = column.getColumnName();
%>
       <tr><td><b><%= colName%></b></td></tr>
<%
        }
      }
%>

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
    tableWidth= "748";
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
     if (style != null && style.getFontType() != null && style.getFontType().toUpperCase().equals("BOLD")){
        boldStyleRowNumList.add(""+ii);
     }
  }

}
%>

<script language="JavaScript1.2">

    if(typeof dojo == "undefined"){
        var djConfig = {parseOnLoad: true, isDebug: false}
        document.write("<script type=\"text/javascript\" src=\"<%=request.getContextPath()%>/externals/dojo_1.1.0/dojo/dojo.js\"><"+"/script>")
    }
</script>
<script src="<%=request.getContextPath()%>/externals/lib.js" language="javascript"></script>
<style type="text/css">
    .specialRow .dojoxGrid-cell {
        font-weight: bold;
        text-align:right;
    }
    .rightRow .dojoxGrid-cell {
        text-align:right;
    }
</style>
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

 //           var special = (i == <%=currPage.getTable().size() - 1%> );
            var special = false;
            if (<%=currPage.getTable().size()%> > 0) {
                var oCell=document.getElementById('reportable').rows[i+1].cells;
                special = (oCell[0].className == 'specialRow');
            }
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
                        colClass=  ( styleView != null) ? styleView.getDataClass() : "java.lang.String";
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
            finalColName += ii;  //nessesary if report has 2 or more equal column names with different values
            if(j>0){%> ,<%}%>

            <%  if(pxWidth>0){ %>

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
     onStyleRow="onStyleRow"
     structure="layoutHtmlTable" >

</div>

<div class="scrollTable" id="testScrollTable">
   <table id="reportable" cellpadding="2" style="display:none;" cellspacing="2" width="<%=tableWidth%>" <%if(fancyDisplay){%> style="display:none;border-collapse: collapse;" border="0" cellspacing="0" cellpadding="0"<%}%>>
  <thead>
      <tr>
 <%
     header = currPage.getHeader();
//     HashMap styleCollection = (HashMap)ReportingUtils.createStyles( currPage.getUserStyle() );
     String[] colFormatS = new String[header.size()];
     boolean[]  isColFormated = new boolean[header.size()];

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
         //--------------------------------------
         totalFl[ii] = column.getTotalRequestFlag();
         String cWidth = column.getColumnWidth();
         if (!Utility.isSet(cWidth) || cWidth.indexOf("*")>-1) cWidth = "15";
         colWidth[ii] = cWidth;

         colScale[ii] = scale;
         if (colClass.equals("java.math.BigDecimal")) {

             if (totalFl[ii]) totalRowFl = true;
             total[ii] = new BigDecimal(0);
             if (ReportingUtils.isColumnForMoney(colName) ){
               colName = ReportingUtils.extractColumnName(colName);
             }
             if (ReportingUtils.isColumnForMoney(colName) && !isColFormated[ii] ){
//               colName = ReportingUtils.extractColumnName(colName);
               colFormat[ii] = 1; //accountingFormat;
             } else if (ReportingUtils.isColumnForPercent(colName) ||
                        colFormatPattern[ii].indexOf("%")>=0 ) {
               colName = ReportingUtils.extractColumnName(colName);
               colFormat[ii] = 2; //percentFormat;
             } else if ( scale == 0 /* precision== 0*/) {
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
      finalColName += ii;  //nessesary if report has 2 or more equal column names with different values
%>
      <th width="<%=colWidth[ii]%>" align="center"><%=finalColName%></th>
<%    }  %>

 <%} //column loop %>
      </tr>
   </thead>

 <tbody>
<%
  String tdStyle = " ";
  if(fancyDisplay){
	tdStyle = " class=\"rtext2\" ";
  }
  int colNums = header.size();
  ArrayList table = currPage.getTable();
//----------  the beginning of table--------------------
  for(int ii=0; ii<table.size(); ii++) {
    ArrayList row = (ArrayList) table.get(ii);
    if(!fancyDisplay){
	tdStyle = " ";
    }

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

<%//  for(int jj=0; jj<row.size(); jj++) {%>
<%
int columns = Math.min(row.size(), colWidth.length);
for(int jj=0; jj<colWidth.length; jj++) {%>
<%
//    String tdRight = "<td"+tdStyle+"width=\""+colWidth[jj]+"\" align=\"right\">";
//    String tdLeft  = "<td "+tdStyle+"width=\""+colWidth[jj]+"\" align=\"left\">";
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
     Object dataVal = ((GenericReportCellView)value).getDataValue();
     if (Utility.isSet(styleName) && styleCollection.containsKey(styleName)){
        GenericReportStyleView style = (GenericReportStyleView)styleCollection.get(styleName);
        format = (style!= null) ? style.getDataFormat() : null;
     }
     isFormated = Utility.isSet(format);
     value = dataVal;
}

String tdRight = "<td"+tdStyle+"width=\""+colWidth[jj]+"\" align=\"right\">";
String tdLeft  = "<td "+tdStyle+"width=\""+colWidth[jj]+"\" align=\"left\">";
%>

<%
if(colFormat[jj]==1 && (value instanceof java.math.BigDecimal)) { %>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<%=tdRight%>

<%=ClwI18nUtil.formatCurrencyAmount(request, value, thisCurrencyCode)%>
</td>
<% } else if (colFormat[jj]==2 && (value instanceof java.math.BigDecimal)) {%>
  <% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
  <%  value = ((BigDecimal)value).multiply(new BigDecimal(100.0)); %>
<%--<%=tdRight%><i18n:formatPercent value="<%=value%>" locale="<%=thisUserLocale%>" pattern="<%=colFormatPattern[jj] %>"/>&nbsp;</td>--%>
  <% if (isFormated) {
    format = format.replace("%","");
    int scale = format.length()- format.indexOf(".")-1;
    value = ((BigDecimal)value).setScale(scale, BigDecimal.ROUND_HALF_UP);
   }
  %>
  <%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" />%&nbsp;</td>
<% } else if (colFormat[jj]==3 && (value instanceof java.math.BigDecimal) ) {%>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<% if (isFormated) {
%>
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
<% if (isFormated) {
%>
  <%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" pattern="<%=format%>"/>&nbsp;</td>
<%} else {%>
  <%=tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" />&nbsp;</td>
<%}%>
<% } else if(colFormat[jj]==5 && (value instanceof java.util.Date)){ %>
<%=tdRight%><i18n:formatTime value="<%=value%>" locale="<%=thisUserLocale%>"/>&nbsp;</td>
<% } else if (colFormat[jj]==6 && (value instanceof java.util.Date)) {%>
<%=tdRight%><i18n:formatDate value="<%=value%>" locale="<%=thisUserLocale%>"/>&nbsp;</td>

<% } else if (colFormat[jj]==7 && (value instanceof java.math.BigDecimal) ) {%>
<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);%>
<% if (isFormated) {  %>
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
</td></tr>
</table>
<!------------------------------------->
</td>
</tr>
