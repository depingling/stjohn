<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table width="769" class="mainbody">

<tr>
<td><b>Store&nbsp;Id:</b> </td>
<td>
<bean:write name="ACCOUNT_DETAIL_FORM" property="storeId"/>
</td>
<td><b>Store&nbsp;Name:</b> </td>
<td>
<bean:write name="ACCOUNT_DETAIL_FORM" property="storeName"/>
</td>
</tr>

<tr>
<td><b>Account&nbsp;Id:</b> </td>
<td>
<bean:write name="ACCOUNT_DETAIL_FORM" property="id"/>
</td>
<td><b>Account&nbsp;Name:</b> </td>
<td>
<bean:write name="ACCOUNT_DETAIL_FORM" property="name"/>
</td>
</tr>

</table>
