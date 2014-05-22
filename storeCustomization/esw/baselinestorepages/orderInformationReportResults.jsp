<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.cleanwise.view.forms.CustAcctMgtReportForm"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>



<bean:define id="myForm" name="esw.ReportingForm"  type="com.espendwise.view.forms.esw.ReportingForm"/>

<%
	CustAcctMgtReportForm customerReportingForm = myForm.getCustomerReportingForm();
	String currencyLocale = (String) session.getAttribute(Constants.CATALOG_LOCALE);
	CleanwiseUser user = ShopTool.getCurrentUser(request);
	String userLocale = null;
	if(user.getPrefLocale()!=null) {
		userLocale = user.getPrefLocale().toString();
	}
	
%>
                
				<!-- Start Box -->
                <div class="boxWrapper tabbed bottom">
                    <div class="tabs clearfix">
                        <ul>
                       
                        </ul>
                    </div>
                    <div class = "content">
                        <div class="left clearfix">
                        <h2><%= customerReportingForm.getReportTypeCd()%></h2>
						<div class="tableWrapperHorz">
                        	<table class="order">
                                <colgroup>
                        			<col class="Order"/>
									<col class="Store"/>
									<col class="Actual"/>
									<col class="Rank"/>
									<col class="NSF"/>
									<col class="BSC"/>
									<col class="Requested"/>
									<col class="YTD Remaining Budget"/>
									<col class="Approved"/>
									<col class="Committed"/>
									<col class="Date Approved"/>
                                </colgroup>
                                <thead>
                                    <tr>
											<th>Date</th>
											<th>Store</th>
											<th>YTD <br>Actual </th>
											<th>Rank </th>
											<th>NSF</th>
											<th>BSC </th>
											<th>Requested </th>
											<th>YTD Remaining <br>Budget </th>
											<th>Approved </th>
											<th>Committed </th>
											<th>Date Approved</th>
                                    </tr>
                                </thead>
                                <tbody>
								<logic:iterate id="resultList" name="esw.ReportingForm"  property="customerReportingForm.orderReport"
 											   type="com.cleanwise.service.api.dao.UniversalDAO.dbrow" >
								<tr>
										<td><%=ClwI18nUtil.formatDateInp(request, new java.util.Date((String)resultList.getStringValue(0)))%></td>
										<td><%=resultList.getStringValue(1)%></td>
										<td align=right>
											<%=ClwI18nUtil.formatAmount(new BigDecimal(resultList.getStringValue(2)),currencyLocale,userLocale) %>
										</td>
										<td><%=resultList.getStringValue(3)%></td>
										<td><%=resultList.getStringValue(4)%></td>
										<td><%=resultList.getStringValue(5)%></td>
										<td align=right>
											<%=ClwI18nUtil.formatAmount(new BigDecimal(resultList.getStringValue(6)),currencyLocale,userLocale) %>
										</td>
										<td align=right>
											<%=ClwI18nUtil.formatAmount(new BigDecimal(resultList.getStringValue(7)),currencyLocale,userLocale) %>
										</td>
										
										<td align=right>
											<%=ClwI18nUtil.formatAmount(new BigDecimal(resultList.getColumn("APPROVED_AMT").colVal),currencyLocale,userLocale) %>
										</td>
										
										<td align=right>
											<%=ClwI18nUtil.formatAmount(new BigDecimal(resultList.getStringValue(8)),currencyLocale,userLocale) %>
										</td>
										<td>
											<% 
												java.math.BigDecimal n3 = new java.math.BigDecimal((String)resultList.getStringValue(8));
												if (n3.doubleValue() > 0 && resultList.getColumn("REVISED_DATE").colVal != null) { %>
												<%=ClwI18nUtil.formatDateInp(request, new java.util.Date((String)resultList.getColumn("REVISED_DATE").colVal))%>
											<% } else if (n3.doubleValue() > 0 ) { 
												/* This order was modified, set the approval date
												   equal to the order date. */
												%>
												<%=ClwI18nUtil.formatDateInp(request, new java.util.Date((String)resultList.getStringValue(0)))%>
											<% } else { 
												%> &nbsp; <% 
											} %>
										</td>
								</tr>
								  
								</logic:iterate>
								</tbody>
                            </table>    
						</div>      
                        </div>
                    </div>    
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>                    
                </div> 
                 
                 <!-- End Box -->
         
    