<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<bean:define id="catalog" name="USER_DETAIL_FORM" property="detail" />

<table border="0" cellpadding="0" cellspacing="0" width="769" bgcolor="#cccccc">
  <tr><td><b>User ID:</b></td>
  <td><bean:write name="USER_DETAIL_FORM" property="detail.userData.userId" filter="true"/>
  </td>
  <td> <b>User Name:</b> </td>
  <td> <bean:write name="USER_DETAIL_FORM" property="detail.userData.userName" filter="true"/>
  </td>
<td><b>Mod By:</b></td>
<td><bean:write name="USER_DETAIL_FORM" property="detail.userData.modBy"/></td>
  </tr>
  <tr>
  <td><b>User Type:</b></td>
  <td><bean:write name="USER_DETAIL_FORM" property="detail.userData.userTypeCd" filter="true"/></td>
    <td><b>User Status:</b></td>
  <td><bean:write name="USER_DETAIL_FORM" property="detail.userData.userStatusCd" filter="true"/>
  </td>
<td><b>Mod Date:</b></td>
<td><bean:write name="USER_DETAIL_FORM" property="detail.userData.modDate"/>
</td>
  </tr>  
</table>
  
<bean:define id="userTypeCd" name="USER_DETAIL_FORM" property="detail.userData.userTypeCd" />

