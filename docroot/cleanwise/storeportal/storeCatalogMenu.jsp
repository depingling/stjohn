<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="STORE_ADMIN_CATALOG_FORM" type="com.cleanwise.view.forms.StoreCatalogMgrForm"/>
<% 
  CatalogData catalogD = theForm.getCatalogDetail();
  boolean catalogDetFl = theForm.getCatalogProcessFl();
%>
<table ID=648 width="<%=Constants.TABLEWIDTH%>">
  <tr bgcolor="#000000">
    <% if(catalogDetFl) { %>
		<app:renderStatefulButton link="storecatalogdet.do" name="Catalog Detail" tabClassOff="tbar" 
				tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="storecatalogdet"/>
		<app:renderStatefulButton link="storecatalogconf.do" name="Catalog Configuration" tabClassOff="tbar" 
				tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn"/>
		<%if(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(catalogD.getCatalogTypeCd())) { %>        
		  <app:renderStatefulButton link="storecostcenter.do" name="Cost Centers" tabClassOff="tbar" 
				tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn"/>
		<% } %>
		<% } else { %>
		<app:renderStatefulButton link="catalogs.do" name="Catalog Search" tabClassOff="tbar" 
				tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="catalogs"/>
		<% } %>
		<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.FREIGHT_TABLE_ADMINISTRATION%>">
		<app:renderStatefulButton link="storefreightsearch.do" name="Freight Tables" tabClassOff="tbar" 
				tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="storefreight"/>
        </app:authorizedForFunction>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.DISCOUNT_ADMINISTRATION%>">
            <app:renderStatefulButton link="storeDiscountFreightSearch.do" name="Discount Tables" tabClassOff="tbar" 
                tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="storeDiscountFreight"/>
        </app:authorizedForFunction>
	</tr>
</table>
