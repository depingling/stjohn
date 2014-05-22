<%--

  Title:        storeAssetCtx.jsp
  Description:  info page of asset
  Purpose:      views of the  info 
  Copyright:    Copyright (c) 2006
  Company:      CleanWise, Inc.
  Date:         27.12.2006
  Time:         16:45:22
  author        Alexander Chickin, TrinitySoft, Inc.

--%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<logic:present name="STORE_ASSET_DETAIL_FORM">
<bean:define id="theForm" name="STORE_ASSET_DETAIL_FORM"  type="com.cleanwise.view.forms.StoreAssetDetailForm"/>
<logic:present name="STORE_ASSET_DETAIL_FORM"  property="assetData">

    <table ID=531 border="0" cellpadding="3" cellspacing="1" width="<%=Constants.TABLEWIDTH%>" class="mainbody">
      <tr>
      <td><b>Store&nbsp;Id:</b></td><td><bean:write name="<%=Constants.APP_USER%>" property="userStore.storeId"/></td>
      <td><b>Store&nbsp;Name:</b></td><td><bean:write name="<%=Constants.APP_USER%>" property="userStore.busEntity.shortDesc"/></td>
      </tr>
      <tr>
      <td><b>Asset&nbsp;Id:</b></td><td><bean:write name="STORE_ASSET_DETAIL_FORM" property="assetData.assetId"/></td>
      <td><b>Asset&nbsp;Name:</b></td><td><bean:write name="STORE_ASSET_DETAIL_FORM" property="assetData.shortDesc"/></td>
      </tr>
    </table>
</logic:present>
</logic:present>
