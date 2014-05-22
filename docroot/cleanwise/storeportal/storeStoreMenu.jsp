<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="STORE_STORE_DETAIL_FORM"
  type="com.cleanwise.view.forms.StoreStoreMgrDetailForm"/>
<%
  int storeId = theForm.getIntId();
%>
<table ID=1310 width="<%=Constants.TABLEWIDTH%>">
  <tr bgcolor="#000000">
  <% if(storeId>0) { %>
  <td>
 <app:renderStatefulButton link="storeStoreMgrDetail.do?action=storedetail"
 name="Detail" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
 contains="storeStoreMgrDetail,storeStoreMgr"/>

<app:renderStatefulButton link="storeStoreUIConfig.do"
 name="UI" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
 contains="storeStoreUIConfig"/>

 <app:renderStatefulButton link="storeStoreAccountData.do?action=initAccountFields"
 name="Account Data" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
 contains="storeStoreAccountData"/>

<logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd" value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
<app:renderStatefulButton link="storeStoreResources.do?action=initResourceConfig"
 name="Resource Strings" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
 contains="storeStoreResources"/>
 </logic:equal>

 <app:renderStatefulButton link="storeStoreMasterItemData.do?action=initMasterItemFields"
 name="Master Item Data" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
 contains="storeStoreMasterItemData"/>
 
 <logic:equal name="theForm" property="parentStore" value="true">
 <app:renderStatefulButton link="storeStoreConfiguration.do"
 name="Configuration" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
 contains="storeStoreConfiguration"/>
 </logic:equal>

<%
StringBuilder link = new StringBuilder(100);
link.append("storeTemplate.do?action=init&templateData.type=");
link.append(Constants.TEMPLATE_TYPE_EMAIL);
%>

<app:renderStatefulButton link="<%=link.toString()%>"
 name="Templates" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
 contains="storeTemplate"/>

 </td>

    <% }else{ %>

<td class="tbartext">&nbsp;</td>
  <% } %>
  
<app:renderStatefulButton link="storeStoreMyProfile.do?action=initStoreProfile"
 name="My Profile Page" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
 contains="storeStoreMyProfile"/>

</tr>
</table>
