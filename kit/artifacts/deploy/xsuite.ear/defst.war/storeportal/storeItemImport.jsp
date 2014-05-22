<%@ page contentType="text/html"%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="STORE_ADMIN_ITEM_FORM" type="com.cleanwise.view.forms.StoreItemMgrForm"/>

<script language="JavaScript1.2">
    <!--
    function SetChecked(val) {
        dml=document.STORE_ADMIN_ITEM_FORM;
        len = dml.elements.length;
        var i=0;
        for( i=0 ; i<len ; i++) {
            if (dml.elements[i].name=='selectorBox') {
                dml.elements[i].checked=val;
            }
        }
    }
    //-->
</script>

<jsp:include flush='true' page="locateStoreItem.jsp">
    <jsp:param name="jspFormAction" 	value="/storeportal/importItems.do" />
    <jsp:param name="jspFormName" 	value="STORE_ADMIN_ITEM_FORM" />
    <jsp:param name="jspSubmitIdent" 	value="" />
    <jsp:param name="jspReturnFilterProperty" 	value="itemsToImport" />
</jsp:include>

<% String catalogIdS = ""+theForm.getCatalogId(); %>

<html:hidden name="STORE_ADMIN_ITEM_FORM" property="catalogId" value="catalogIdS"/>

<table ID=965 border="0" width="769" class="mainbody">
<html:form styleId="966" action="storeportal/importItems.do">
<tr>
    <td colspan='4' class="largeheader">Import Items</td>
</tr>
<tr>
    <td><b>Select Store:</b></td>
    <td colspan='3'>
        <html:select name="STORE_ADMIN_ITEM_FORM" property="importStoreId" >
            <html:option value="">&nbsp;</html:option>
            <%
                BusEntityDataVector beDV = theForm.getUserStoreEntities();
                if(beDV!=null) {
                    for(int ii=0; ii<beDV.size(); ii++) {
                        BusEntityData beD = (BusEntityData) beDV.get(ii);
            %>
            <html:option value='<%=""+beD.getBusEntityId()%>'><%=beD.getShortDesc()%></html:option>
            <%
                    }
                }
            %>
        </html:select>
    </td>
</tr>
<tr>
    <td></td>
    <td colspan="3">
        <html:submit property="action" value="Import Items"/>
    </td>
</tr>
</table>
<%
    ItemViewVector itemVwV = theForm.getItemsToImport();
    if(itemVwV!=null) {
%>
Search result count: <%=itemVwV.size()%>
<%
    if(itemVwV.size()>0) {
%>
<div>

<bean:define id="distInfoFlag"  name="STORE_ADMIN_ITEM_FORM" property="distInfoFlag"/>
<table ID=967 width="100%" class="results">
    <tr align=center>
        <td><a ID=968 class="tableheader" href="items.do?action=sort&sortField=id"><b>Id</b></a> </td>
        <td><a ID=969 class="tableheader" href="items.do?action=sort&sortField=sku"><b>Sku</b></a> </td>
        <td><a ID=970 class="tableheader" href="items.do?action=sort&sortField=name"><b>Name</b></a> </td>
        <td><a ID=971 class="tableheader" href="items.do?action=sort&sortField=size"><b>Size</b></a> </td>
        <td><a ID=972 class="tableheader" href="items.do?action=sort&sortField=pack"><b>Pack</b></a> </td>
        <td><a ID=973 class="tableheader" href="items.do?action=sort&sortField=uom"><b>UOM</b></a></td>
        <td><a ID=974 class="tableheader" href="items.do?action=sort&sortField=color"><b>Color</b></a></td>
        <td><a ID=975 class="tableheader" href="items.do?action=sort&sortField=manufacturer"><b>Mfg.</b></a> </td>
        <td><a ID=976 class="tableheader" href="items.do?action=sort&sortField=msku"><b>Mfg.&nbsp;Sku</b></a> </td>
        <td><a ID=977 class="tableheader" href="items.do?action=sort&sortField=category"><b>Category</b></a> </td>
    </tr>
    <logic:iterate id="item" name="STORE_ADMIN_ITEM_FORM" property="itemsToImport" indexId="indId">
        <bean:define id="key"  name="item" property="itemId"/>
        <bean:define id="sku" name="item" property="sku"/>
        <bean:define id="name" name="item" property="name"/>
        <bean:define id="size" name="item" property="size"/>
        <bean:define id="pack" name="item" property="pack"/>
        <bean:define id="uom" name="item" property="uom"/>
        <bean:define id="color" name="item" property="color"/>
        <bean:define id="manuName" name="item" property="manufName"/>
        <bean:define id="manuSku" name="item" property="manufSku"/>
        <bean:define id="category" name="item" property="category"/>

        <%
            String linkHref = new String("importItems.do?action=cloneImported&retaction=importItems&itemId=" + key);
            if (( indId.intValue() % 2 ) == 0 ) {
        %>
        <tr class="rowa">
                <%
                } else { 
                %>
        <tr class="rowb">
            <%
                }
            %>

            <td><bean:write name="key"/></td>
            <td><bean:write name="sku"/></td>
            <td><html:link href="<%=linkHref%>"><bean:write name="name"/></html:link></td>
            <td><bean:write name="size"/></td>
            <td><bean:write name="pack"/></td>
            <td><bean:write name="uom"/></td>
            <td><bean:write name="color"/></td>
            <td><bean:write name="manuName"/></td>
            <td><bean:write name="manuSku"/></td>
            <td><bean:write name="category"/></td>

            <td/>
        </tr>
    </logic:iterate>
</table>
<%
        } //if(itemVwV.size()>0)
    }//if(itemVwV!=null)
%>
</html:form>

