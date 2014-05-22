<%--

  Title:        storeAssetMenu.jsp
  Description:  menu page
  Purpose:      view of  asset menu page
  Copyright:    Copyright (c) 2006
  Company:      CleanWise, Inc.
  Date:         25.12.2006
  Time:         21:42:21
  author        Alexander Chickin, TrinitySoft, Inc.

--%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<table ID=543 width="<%=Constants.TABLEWIDTH%>">
<tr bgcolor="#000000"><td>
<logic:present name="STORE_ASSET_DETAIL_FORM">
<bean:define id="theForm" name="STORE_ASSET_DETAIL_FORM"  type="com.cleanwise.view.forms.StoreAssetDetailForm"/>
<logic:present name="STORE_ASSET_DETAIL_FORM"  property="assetData">
<bean:define id="assetId" name="STORE_ASSET_DETAIL_FORM" property="assetData.assetId"/>
 <logic:greaterThan   name="assetId" value="0" >


<app:renderStatefulButton link="storeAssetDetail.do?action=assetdetail"
 name="Details" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
 contains="storeAssetDetail,storeAssetContent"/>

<logic:equal name="STORE_ASSET_DETAIL_FORM" property="assetData.assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
<app:renderStatefulButton link="storeAssetConfig.do?action=initConfig"
 name="Configuration" tabClassOff="tbar" tabClassOn="tbarOn"
 linkClassOff="tbar" linkClassOn="tbarOn"
 contains="storeAssetConfig"/>
</logic:equal>

</logic:greaterThan>
</logic:present>
</logic:present>
</td>
</tr>
</table>
