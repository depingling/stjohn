<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
    String orderSearchLink = "userportal/esw/orders.do";
    String ORDER_SEARCH_VALUE_DISPLAY_SIZE = "50";
    String activeTab = Utility.getSessionDataUtil(request).getSelectedSubTab();
    String orientation = request.getParameter(Constants.PARAMETER_ORIENTATION);
    if (!Utility.isSet(orientation)) {
    	orientation = Constants.ORIENTATION_HORIZONTAL;
    }
    if (Constants.ORIENTATION_VERTICAL.equalsIgnoreCase(orientation)) {
%>
                    <h3>
                    	<app:storeMessage key="orders.filterPane.label.orderNumber" />
                    </h3>
                    <div class="search">
                    	<html:form styleId="orderSearchForm" action="<%=orderSearchLink%>">
                    		<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    			value="<%=Constants.PARAMETER_OPERATION_VALUE_SEARCH_ORDERS%>"/>
                    	    <html:hidden property="activeTab" value="<%=activeTab%>"/>
	                        <div class="textBox">
	                        	<html:text property="orderNumSearchValue"
	                        		size="<%=ORDER_SEARCH_VALUE_DISPLAY_SIZE%>"/>
	                        </div>
	                        <html:link href="javascript:submitForm('orderSearchForm')">
	                        	<app:storeMessage key="global.action.label.search" />
	                        </html:link>
	                	</html:form>
                    </div>
<%
    }
	else {
%>
                        <div class="searchBoxWrapper">
                            <div class="searchBox">
                                <table>
                                    <tr>
                                        <td class="title">
                                            <h3>
                                            	<app:storeMessage key="orders.filterPane.label.orderNumber" />
                                            </h3>
                                        </td>
				                    	<html:form styleId="orderSearchForm" action="<%=orderSearchLink%>">
				                    		<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
				                    			value="<%=Constants.PARAMETER_OPERATION_VALUE_SEARCH_ORDERS%>"/>
	                                        <td class="search">
	                                            <div class="inputWrapper">
						                        	<html:text property="ordersSearchInfo.orderNumber"/>
	                                            </div>
	                                        </td>
	                                    </html:form>
                                        <td class="button">
					                        <html:link styleClass="btn greyBtn" href="javascript:submitForm('orderSearchForm')">
					                        	<span>
						                        	<app:storeMessage key="global.action.label.search" />
						                        </span>
					                        </html:link>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
<%
	}
%>
<p>
&nbsp;
</p>
 <p>&nbsp;<br>
&nbsp;
 </p>                  