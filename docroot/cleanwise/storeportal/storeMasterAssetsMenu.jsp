<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="USER_ASSET_MGR_FORM" type="com.cleanwise.view.forms.UserAssetMgrForm"/>

<table ID=991 width="<%=Constants.TABLEWIDTH%>">
    <tr bgcolor="#000000">
        <app:renderStatefulButton link="searchMasterAssets.do" name="Master Asset" tabClassOff="tbar"
                                  tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="searchMasterAssets"/>

        <app:renderStatefulButton link="searchMasterAssetCategory.do" name="Category" tabClassOff="tbar"
                                  tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="searchMasterAssetCategory,masterAssetCategoryProfile"/>

        <app:renderStatefulButton link="searchStagedMasterAssets.do" name="Staged" tabClassOff="tbar"
                                  tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="searchStagedMasterAssets,searchMatchMasterAssets"/>
    </tr>
</table>
