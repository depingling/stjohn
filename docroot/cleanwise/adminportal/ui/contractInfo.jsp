<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<bean:define id="catalog" name="CONTRACT_DETAIL_FORM" property="detail" />

<table border="0" cellpadding="0" cellspacing="0" width="769" bgcolor="#cccccc">
  <tr><td width="20%"><b>Contract ID:</b></td>
  <td width="30%"><bean:write name="CONTRACT_DETAIL_FORM" property="detail.contractId" filter="true"/>
  </td>
  <td width="20%"> <b>Contract Name:</b> </td>
  <td width="30%"> <bean:write name="CONTRACT_DETAIL_FORM" property="detail.shortDesc" />
  </td>
  </tr>
  
  <tr><td colspan="4">&nbsp;</td></tr>
  <tr><td width="20%"><b>Store Id:</b></td>
  <td width="30%"><bean:write name="CONTRACT_DETAIL_FORM" property="storeId" filter="true"/>
  </td>
  <td width="20%"> <b>Store Name:</b> </td>
  <td width="30%"> <bean:write name="CONTRACT_DETAIL_FORM" property="storeName" />
  </td>
  </tr>
  
  <tr><td width="20%"><b>Catalog Id:</b></td>
  <td width="30%"><bean:write name="CONTRACT_DETAIL_FORM" property="detail.catalogId" filter="true"/>
  </td>
  <td width="20%"> <b>Catalog Name:</b> </td>
  <td width="30%"> <bean:write name="CONTRACT_DETAIL_FORM" property="catalogName" />
  </td>
  </tr>
  <tr><td width="20%"><b>Contract Status:</b></td>
  <td width="30%"><bean:write name="CONTRACT_DETAIL_FORM" property="detail.contractStatusCd" filter="true"/>
  </td>
  <td width="20%">&nbsp;</td>
  <td width="30%">&nbsp;
  </td>
  </tr>
  
  <tr><td colspan="4">&nbsp;</td></tr>
</table>
