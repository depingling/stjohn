<%--
  Date: 05.10.2007
  Time: 16:53:58
--%>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table ID=1506 class="stpTable" border="0" cellspacing="0">
    <tr  valign="bottom">
        <td class=stpLabel>Store&nbsp;Id:</td>
        <td>
            <bean:write name="<%=Constants.APP_USER%>" property="userStore.storeId"/>
        </td>
        <td class=stpLabel>Store&nbsp;Name:</td>
        <td>
            <bean:write name="<%=Constants.APP_USER%>" property="userStore.busEntity.shortDesc"/>
        </td>
        <logic:present name="STORE_WARRANTY_DETAIL_FORM" property="warrantyData">
            <td class=stpLabel>Warranty&nbsp;Id:</td>
            <td>
                <bean:write name="STORE_WARRANTY_DETAIL_FORM" property="warrantyData.warrantyId"/>
            </td>
            <td class=stpLabel>Warranty&nbsp;Name:</td>
            <td>
                <bean:write name="STORE_WARRANTY_DETAIL_FORM" property="warrantyData.shortDesc"/>
            </td>            
        </logic:present>

    </tr>


</table>