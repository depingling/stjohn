<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table width="<%=Constants.TABLEWIDTH%>">
<tr bgcolor="#000000"><td>

<logic:present name="STORE_ENTERPRISE_MGR_FORM">

<bean:define id="theForm"
             name="STORE_ENTERPRISE_MGR_FORM"
             type="com.cleanwise.view.forms.StoreEnterpriseMgrForm"/>

<app:renderStatefulButton link="storeEnterpriseMfg.do?action=init"
 name="Manufacturer Association" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
 contains="storeEnterpriseMfg"/>

</logic:present>

</td>
</tr>
</table>