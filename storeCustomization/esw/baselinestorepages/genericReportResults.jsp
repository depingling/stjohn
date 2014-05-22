<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.service.api.value.CurrencyData"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.cleanwise.service.api.value.GenericReportColumnView"%>
<%@page import="com.cleanwise.service.api.value.GenericReportColumnViewVector"%>
<%@page import="com.cleanwise.service.api.value.ContractData"%>
<%@page import="com.cleanwise.service.api.value.SiteData"%>
<%@page import="com.cleanwise.service.api.value.AccountData"%>
<%@page import="java.util.Locale"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.service.api.value.GenericReportResultView"%>
<%@page import="com.cleanwise.service.api.value.GenericReportResultViewVector"%>
<%@page import="com.cleanwise.service.api.reporting.ReportingUtils"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript">

	function doSelectTab(tabToSet){
		document.getElementById('selectedTabId').value=tabToSet;
		document.getElementById('operationId').value='<%=Constants.PARAMETER_OPERATION_VALUE_REPORT_REULSTS_SUB_TAB %>';
		submitForm('reportingStandardId');
	}

</script>

<bean:define id="myForm" name="esw.ReportingForm"  type="com.espendwise.view.forms.esw.ReportingForm"/>
<bean:define id="thisReportCd" name="esw.ReportingForm" type="java.lang.String" property="customerReportingForm.reportTypeCd"/>

<%
	String reportingLink = "userportal/esw/reporting.do";
  // See if there is a translation for the report name.
    CleanwiseUser appUser = ShopTool.getCurrentUser(request);
	Locale thisUserLocale = appUser.getPrefLocale();
	AccountData accountData = appUser.getUserAccount();
	SiteData siteData = appUser.getSite();
	ContractData contractData = siteData.getContractData();
	String purchasesLocale = null;
	if (siteData != null && siteData.getContractData() != null ) {
	  purchasesLocale = contractData.getLocaleCd();
	}
	if ( null == purchasesLocale ) 
	{ 
		purchasesLocale = "en_US"; 
	}
	String thisCurrencyCode = "USD";
	CurrencyData currData = ClwI18nUtil.getCurrency(purchasesLocale);
	if ( currData != null && currData.getGlobalCode() != null ) {
		thisCurrencyCode = currData.getGlobalCode();	
	}
	GenericReportResultViewVector results = myForm.getCustomerReportingForm().getReportResults();
	GenericReportResultView currPage = null;
	String tdRight = "<td align = \"right\">";
	String tdLeft = "<td align = \"left\">";
	String tdEmpty = "<td>";
%>
                  
                
				<!-- Start Box -->
                <div class="boxWrapper tabbed bottom">
                    <div class="tabs clearfix">
                    <% 
                        	if(results.size()  > 1){ 
                    %>
                        <div class="top clearfix">
                        
                            <span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span>
                        </div>
                        <% }  %>
                        <ul>
                        <% 
							// There is a current assumption that one account will transact all
							// purchasing in one currency.
							//
							
						
							for(int i = results.size()-1; i >= 0; i--) {
							  boolean hide = false;
							  GenericReportResultView result = (GenericReportResultView) results.get(i);
							  String actionValue = "";
							  String classType = "";
							  String selectedClass = "";
							  if(i == myForm.getCustomerReportingForm().getGenericReportPageNum()) {
							    currPage = result;
							   	selectedClass ="selected";
							  } 
							 
							  actionValue = result.getName();
							  // See if there is a translation for the report tab.
							  String tabName = ClwI18nUtil.getMessageOrNull(request, 
							    ReportingUtils.makeTabKey(actionValue));
							  if (tabName == null){ 
								  tabName = actionValue;
							  }
							  
							  if(tabName != null && tabName.trim().length()>0 && results.size() > 1) {
								  
								%>
								<li class="<%=selectedClass %>" >
								<a onclick="javascript:doSelectTab('<%=actionValue %>')" >
					                  <span><%= tabName%></span>
								</a>
								</li>
								
								<%
								} 
							} //End of loop 
							%>
                        </ul>
                    </div>
                    <div class = "content">
                    <% if(results.size()  > 0){  %> 
                        <div class="left clearfix">
                         <% 
                          String currTabName = ClwI18nUtil.getMessageOrNull(request, 
							    ReportingUtils.makeTabKey(currPage.getName()));
							    
                          if(currTabName == null ){
                        	  currTabName = currPage.getName();
                          }
			  
			            %>
                        <h2><%= currTabName%></h2>
						<div class="tableWrapperHorz">
							<table class="order">
                                <colgroup>
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
                        			      if(colClass == null){
                        			      	colFormat[ii] = 0;
                        			      }else if(colClass.equals("java.math.BigDecimal")) {

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
                        			     <col class="<%= finalColName%>" />
                        			<%    }  %>

                        			 <%} //column loop %>
                                    
	                         <%   %>
                                </colgroup>
                                <thead>
                                    <tr>
                                    <% 
                                    for(int ii=0; ii<header.size(); ii++) {
                      			      GenericReportColumnView column = (GenericReportColumnView) header.get(ii);
                      			      String colName = column.getColumnName();
                      			    if(ReportingUtils.isColumnForMoney(colName)) {
                 			           colName = ReportingUtils.extractColumnName(colName);
                 			         } else if(ReportingUtils.isColumnForPercent(colName)) {
                 			           colName = ReportingUtils.extractColumnName(colName);
                 			         } 
                      			  if ( ! colName.toLowerCase().startsWith("rowinfo_")){
                      			    String tColKey = ReportingUtils.makeColumnKey(colName);
                    				String finalColName = ClwI18nUtil.getMessageOrNull(request, tColKey);
                    				if ( null == finalColName ) finalColName = colName;
	                        		%>
                                        <th>
                                            <%= finalColName%>
                                        </th>
									<% } } %>	
                                    </tr>
                                </thead>
                                <tbody>
                               
								<%
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
								    Object value = row.get(jj);
								%>
								<%
								    if(value==null) { 
								%>
								<td>&nbsp;</td>
								<% continue; } %>
								<% 
								if(value instanceof java.lang.String){ 
								  String t = (java.lang.String)value;
								  if ( t.toLowerCase().startsWith("rowinfo_")){
								    continue;
								  }
								}
								%>
								
								<% if(colFormat[jj]==1 && (value instanceof java.math.BigDecimal)) {  %>
								<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value); {%>
								<%= tdRight%><%=ClwI18nUtil.formatCurrencyAmount(request, value, thisCurrencyCode)%>
								<% }  %>
								</td>	
								<% } else if (colFormat[jj]==2 && (value instanceof java.math.BigDecimal)) {  %>
								<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);{%>
								<%= tdRight%><i18n:formatPercent value="<%=value%>" locale="<%=thisUserLocale%>" />&nbsp;</td>	
								<% }} else if (colFormat[jj]==3 && (value instanceof java.math.BigDecimal) ) {  %>
								<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value); {
								%>
								<%= tdRight%><%=value%>&nbsp;</td>	
								<% }} else if (colFormat[jj]==4 && (value instanceof java.math.BigDecimal) ) {%>
								<% if(totalFl[jj]) total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value); {
								%>
								<%= tdRight%><i18n:formatNumber value="<%=value%>" locale="<%=thisUserLocale%>" />&nbsp;</td>
								<% }} else if(colFormat[jj]==3 && (value instanceof java.lang.Integer)){  %>
								<% if(totalFl[jj]) total[jj] = new Integer(((Integer) total[jj]).intValue()+((Integer)value).intValue()); {
								%>
								<%= tdRight%><%=value%>&nbsp;</td>	
								<% }} else if(colFormat[jj]==5 && (value instanceof java.util.Date)){  %>
								<%= tdRight%><i18n:formatTime value="<%=value%>" locale="<%=thisUserLocale%>"/>&nbsp;</td>
								<% } else if (colFormat[jj]==6 && (value instanceof java.util.Date)) {  %>
								<%--
								<i18n:formatDate value="<%=value%>" locale="<%=thisUserLocale%>" pattern= />
								--%>
								<%= tdRight%><%=ClwI18nUtil.formatDateInp(request, (java.util.Date)value)%>
								&nbsp;</td>
								<% } else {  %>
								 <%= tdLeft%><%=value%>&nbsp;</td>
								<% } %>
								<% } //end of row %>
								</tr>
								<% } //end of table %>    
								<% if(totalRowFl) {%>
									<tr>
									<%  for(int jj=0; jj<totalFl.length; jj++) {
									
									%>
									
									<% if(colFormat[jj]==1 && (total[jj] instanceof java.math.BigDecimal) && totalFl[jj]) {  %>
									<b>
									<%= tdRight%><%=ClwI18nUtil.formatCurrencyAmount(request, total[jj], thisCurrencyCode)%>
									</b>&nbsp;</td>	
									<% } else if (colFormat[jj]==2 && (total[jj] instanceof java.math.BigDecimal) && totalFl[jj]) {  %>
									<%= tdRight%><b><i18n:formatPercent value="<%=total[jj]%>" locale="<%=thisUserLocale%>" /></b>&nbsp;</td>	
									<% } else if (colFormat[jj]==3 && (total[jj] instanceof java.math.BigDecimal) && totalFl[jj]) {%>
									<%= tdRight%><b><i18n:formatNumber value="<%=total[jj]%>" locale="<%=thisUserLocale%>" /></b>&nbsp;</td>	
									<% } else if (colFormat[jj]==4 && (total[jj] instanceof java.math.BigDecimal) && totalFl[jj]) {%>
									<%= tdRight%><b><i18n:formatNumber value="<%=total[jj]%>" locale="<%=thisUserLocale%>" /></b>&nbsp;</td>
									<% } else if(colFormat[jj]==3 && (total[jj] instanceof java.lang.Integer) && totalFl[jj]){  %>
									<%= tdRight%><b>
									<%--
									<i18n:formatNumber value="<%=total[jj]%>" locale="<%=thisUserLocale%>" />
									 --%>
									 <%=ClwI18nUtil.formatDateInp(request, (java.util.Date)total[jj]) %>
									</b>&nbsp;</td>	
									<% } else {  %>
									  <% if(jj==0) {%>
									<%= tdEmpty%><b>Total:</b>&nbsp;</td>
									  <% } else { %>
									<%= tdEmpty%>&nbsp;</td>
									  <% } %>
									<% } %>
									<% } //end of row %>
									</tr>
									<% } %>
                                    <!-- End Repeating Row -->
                                </tbody>
                            </table>
                            </div>
                        </div>
                        <% } %>
                    </div>    
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>                    
                </div> 
                 
                 <!-- End Box -->
                
            
    