<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="STORE_ADMIN_ITEM_FORM" type="com.cleanwise.view.forms.StoreItemMgrForm"/>

<table ID=991 width="<%=Constants.TABLEWIDTH%>">
    <tr bgcolor="#000000">
        <app:renderStatefulButton link="searchItems.do" name="Items" tabClassOff="tbar"
                                  tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="searchItems,item-edit"/>

        <app:renderStatefulButton link="crossStoreItemLinkSearch.do" name="Cross Store Links" tabClassOff="tbar"
                                  tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="crossStoreItemLinkSearch,crossStoreItemLinkEdit"/>

        <app:renderStatefulButton link="storeServiceMgr.do?action=initService" name="Services" tabClassOff="tbar"
                                  tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="services"/>

        <app:renderStatefulButton link="itemCategories.do" name="Categories" tabClassOff="tbar"
                                  tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn"/>
        <app:renderStatefulButton link="itemMultiproducts.do" name="Multi Products" tabClassOff="tbar"
                                  tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn"/>								  
        <%
            if(theForm.getAllowImporFl()) {
        %>
        <app:renderStatefulButton link="importItems.do" name="Import Items" tabClassOff="tbar"
                                  tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn"/>
        <%
            }
        %>
        <app:renderStatefulButton link="storeitemupload.do" name="Item Loader" tabClassOff="tbar"
                                  tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="storeitemupload"/>

        <app:renderStatefulButton link="searchStagedMasterItems.do" name="Staged" tabClassOff="tbar"
                                  tabClassOn="tbarOn" linkClassOff="tbar" linkClassOn="tbarOn" contains="searchStagedMasterItems,searchMatchMasterItems"/>
    </tr>
</table>
