<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<table border="0" cellpadding="0" cellspacing="0" width="769" bgcolor="#cccccc">
  <tr><td width="20%"><b>Store ID:</b></td>
  <td width="30%"><bean:write name="STORE_DETAIL_FORM" property="id" filter="true"/>
  </td>
  <td width="20%"> <b>Store Name:</b> </td>
  <td width="30%"> <bean:write name="STORE_DETAIL_FORM" property="name" filter="true"/>
  </td>
  </tr>
  
  <tr><td colspan="4">&nbsp;</td></tr>
  <tr><td width="20%"><b>Store Type:</b></td>
  <td width="30%"><bean:write name="STORE_DETAIL_FORM" property="typeDesc" filter="true"/>
  </td>
  <td width="20%"> <b>Store Status:</b> </td>
  <td width="30%"> <bean:write name="STORE_DETAIL_FORM" property="statusCd" filter="true"/>
  </td>
  </tr>
  
  <tr><td width="20%"><b>Store Prefix:</b></td>
  <td width="30%"><bean:write name="STORE_DETAIL_FORM" property="prefix" filter="true"/>
  </td>
  <td width="20%"> <b>Store Description:</b> </td>
  <td width="30%"> <bean:write name="STORE_DETAIL_FORM" property="description" filter="true"/>
  </td>
  </tr>
<%--  <tr><td width="20%"><b>Store Prefix New:</b></td>
  <td width="30%"><bean:write name="STORE_DETAIL_FORM" property="prefixNew" filter="true"/>
  </td>
  <td width="50%" colspan="2">&nbsp;</td>
  </tr> --%>
  
  <tr><td colspan="4">&nbsp;</td></tr>
</table>
