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

<table ID=886 width="<%=Constants.TABLEWIDTH%>">
  <tr bgcolor="#000000">
        <app:renderStatefulButton link="itemcontract.do" name="Item-Catalogs" tabClassOff="tbar" 
                tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="itemcontract"/>
        <app:renderStatefulButton link="cat-item.do" name="Catalog-Items" tabClassOff="tbar" 
                tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="cat-item"/>
        <app:renderStatefulButton link="orderguide-item.do" name="Order Guide-Items" tabClassOff="tbar" 
                tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="orderguide-item"/>
        <app:renderStatefulButton link="service-catalog.do" name="Service-Catalogs" tabClassOff="tbar"
                      tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="service-catalog"/>

    </tr>
</table>
