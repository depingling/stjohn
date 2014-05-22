<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="SERVICE_PROVIDER_TYPE_MGR_FORM" type="com.cleanwise.view.forms.ServiceProviderTypeMgrForm"/>

<script type="text/javascript" language="Javascript">
    function delConf() {
        return confirm("Are you sure to delete this servise provider type?");
    }
</script>

<div class = "text">

<html:form styleId="1550" action="/storeportal/serviceProviderType.do">
<html:hidden name="theForm" property="storeId"/>
<table id="1551" cellspacing="0" border="0" width="769"  class="mainbody">
<% 
    BusEntityData serviceProviderTypeToEdit = theForm.getServiceProviderTypeToEdit();
    int serviceProviderTypeToEditId = 0;
    if(serviceProviderTypeToEdit != null) {
        serviceProviderTypeToEditId = serviceProviderTypeToEdit.getBusEntityId();
    }
%>
<tr>
    <td class="tableheader">Store Service Provider Types</td>
    <td>&nbsp;</td>
</tr>
<logic:present name="theForm" property="serviceProviderTypes">
<% int sptSize = theForm.getServiceProviderTypes().size(); %>
<tr>
    <td colspan="2">&nbsp;count:&nbsp;<%=sptSize%></td>
</tr>
<tr>
    <td colspan="2">&nbsp;</td>
</tr>
</logic:present>
<logic:notEmpty name="theForm" property="serviceProviderTypes">

<logic:iterate name="theForm" property="serviceProviderTypes" id="serviceProviderType" type="com.cleanwise.service.api.value.BusEntityData">
<% 
    int serviceProviderTypeId = serviceProviderType.getBusEntityId();
    if(serviceProviderTypeId != serviceProviderTypeToEditId) {
        String delLink = "serviceProviderType.do?action=Delete Service Provider Type&serviceProviderTypeId=" + serviceProviderType.getBusEntityId();
        String editLink = "serviceProviderType.do?action=Edit Service Provider Type&serviceProviderTypeToEditId=" + serviceProviderType.getBusEntityId();
%>
<tr>
    <td>&nbsp;<bean:write name="serviceProviderType" property="shortDesc"/></td>
    <td><a id="1553" href="<%=editLink%>">[Edit]</a>&nbsp;<a id=1554 href="<%=delLink%>" onclick="return delConf();">[Delete]</a></td>
</tr>
<% } else { %>
<tr>
    <td><html:text name="theForm" property="serviceProviderTypeToEdit.shortDesc" size="70" maxlength="1000"/></td>
    <td><input type=submit name="action" class="smalltext" value="Update Service Provider Type"/></td>
</tr>
<% } %>

</logic:iterate>
</logic:notEmpty>
<tr>
    <td><html:text  name="theForm" property="serviceProviderTypeName" size="70" maxlength="1000"/></td>
    <td><input type=submit name="action" class="smalltext" value="Add Service Provider Type"/></td>    
</tr>
</html:form>
</table>
</div>





