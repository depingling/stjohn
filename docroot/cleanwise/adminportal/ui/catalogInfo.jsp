<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<bean:define id="catalog" name="CATALOG_DETAIL_FORM" property="detail" />
<bean:define id="type" name="CATALOG_DETAIL_FORM"  property="detail.catalogTypeCd"/>


<table border="0" cellpadding="0" cellspacing="0" width="769" bgcolor="#cccccc">
  <tr><td width="20%"><b>Catalog ID:</b></td>
  <td width="30%"><bean:write name="CATALOG_DETAIL_FORM" property="detail.catalogId" filter="true"/>
  </td>
  <td width="20%"> <b>Catalog Name:</b> </td>
  <td width="30%"> <bean:write name="CATALOG_DETAIL_FORM" property="detail.shortDesc" />
  </td>
<%  
  session.setAttribute("Catalog", catalog);
%>
  </tr>
  <tr><td width="20%"><b>Catalog Type:</b></td>
  <td width="30%"><bean:write name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd" filter="true"/>
  </td>
  <td width="20%"> <b>Catalog Status:</b> </td>
  <td width="30%"> <bean:write name="CATALOG_DETAIL_FORM" property="detail.catalogStatusCd" />
  </td>
  </tr>
  <tr><td width="20%"><b>Store Id:</b></td>
  <td width="30%"><bean:write name="CATALOG_DETAIL_FORM" property="storeId" filter="true"/>
  </td>
  <td width="20%"> <b>Store Name:</b> </td>
  <td width="30%"> <bean:write name="CATALOG_DETAIL_FORM" property="storeName" />
  </td>
  </tr>
</table>
