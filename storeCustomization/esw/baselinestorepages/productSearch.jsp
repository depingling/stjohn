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
    String productSearchLink = "userportal/esw/shopping.do";
    String PRODUCT_SEARCH_VALUE_DISPLAY_SIZE = "50";
    String orientation = request.getParameter(Constants.PARAMETER_ORIENTATION);
    if (!Utility.isSet(orientation)) {
    	orientation = Constants.ORIENTATION_HORIZONTAL;
    }
    
    if (Constants.ORIENTATION_VERTICAL.equalsIgnoreCase(orientation)) {
%>
                    <h3>
                    	<app:storeMessage key="product.search.label.productSearch" />
                    </h3>
                    <div class="search">
                    	<html:form styleId="productSearchForm" action="<%=productSearchLink%>">
                    		<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    			value="<%=Constants.PARAMETER_OPERATION_VALUE_SEARCH_PRODUCTS%>"/>
	                        <div class="textBox">
	                        	<html:text property="productSearchValue"
	                        		size="<%=PRODUCT_SEARCH_VALUE_DISPLAY_SIZE%>"/>
	                        </div>
	                        <html:link href="javascript:submitForm('productSearchForm')">
	                        	<app:storeMessage key="global.action.label.search" />
	                        </html:link>
	                        <p>
	                        	<app:storeMessage key="product.search.label.searchBy" />
	                        	<html:select property="productSearchField">
	                            	<html:optionsCollection name="<%=Constants.ACTION_FORM%>"
	                            		property="productSearchFieldChoices" label="label" value="value"/>
	                        	</html:select>
	                        </p>
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
                                            	<app:storeMessage key="product.search.label.productSearch" />
                                            </h3>
                                        </td>
				                    	<html:form styleId="productSearchForm" action="<%=productSearchLink%>">
				                    		<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
				                    			value="<%=Constants.PARAMETER_OPERATION_VALUE_SEARCH_PRODUCTS%>"/>
	                                        <td class="search">
	                                            <div class="inputWrapper">
						                        	<html:text property="productSearchValue"/>
	                                            </div>
	                                        </td>
	                                        <td class="limit">
					                        	<html:select property="productSearchField">
					                            	<html:optionsCollection name="<%=Constants.ACTION_FORM%>"
					                            		property="productSearchFieldChoices" label="label" value="value"/>
					                        	</html:select>
	                                        </td>
	                                       <td>
												<label>
													<html:checkbox property ="greenCertified" styleClass="chkBox right" /> 
													 <app:storeMessage key="product.search.label.greenCertified" />
												</label>
											</td>
	                                    </html:form>
                                        <td class="button">
					                        <html:link styleClass="btn greyBtn" href="javascript:submitForm('productSearchForm')">
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
                    