
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%!private String fh_select="";%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="amdf" name="STORE_ACCOUNT_DETAIL_FORM" type="com.cleanwise.view.forms.StoreAccountMgrDetailForm"/>

<%

    String catalogId = (String)request.getParameter("catalogId");
    if(null == catalogId) {
        catalogId = new String("");
    }
%>


<script language="JavaScript">
    <!--
    function passIdAndNameFH(id,v1,v2) {

	document.forms[3].select.value=id;
    document.forms[3].submit();
    return true;
}
function hideLocate_fh(id)
{
eval("document.getElementById(id)").style.display='none';
}         //-->
</script>
<tr><td>
    <html:form styleId="800" name="STORE_FH_SEARCH_FORM" action="/storeportal/storeFreightHandlerLocate.do" scope="session" type="com.cleanwise.view.forms.StoreFhMgrSearchForm" focus="searchField">

        <table ID=801 width="769" border="0"  class="mainbody">
            <tr> <td><b>Find Freight Handlers:</b></td>
                <td colspan="3">
                    <html:text name="STORE_FH_SEARCH_FORM" property="searchField"/>
                </td>
            </tr>
            <tr> <td></td>
                <td colspan="3">
                    <html:radio name="STORE_FH_SEARCH_FORM" property="searchType" value="id" />
                    ID
                    <html:radio name="STORE_FH_SEARCH_FORM" property="searchType" value="nameBegins" />
                    Name(starts with)
                    <html:radio name="STORE_FH_SEARCH_FORM" property="searchType" value="nameContains" />
                    Name(contains)
                </td>
            </tr>
            <tr> <td></td>
                <td colspan="3">
                    <html:submit property="action">
                        <app:storeMessage  key="global.action.label.search"/>
                    </html:submit>
                    <html:submit property="action">

                        <app:storeMessage  key="admin.button.viewall"/>
                    </html:submit>
                    <html:button property="action" onclick="hideLocate_fh('storeFreightHandlerLocate');" value="Hide"/>
                </td>
            </tr>
        </table>
    </html:form>
</td></tr>
<tr><td>
    <html:form styleId="802" name="STORE_FH_SEARCH_FORM" action="/storeportal/storeFreightHandlerLocate.do" scope="session" type="com.cleanwise.view.forms.StoreFhMgrSearchForm" focus="searchField">
        <input type="hidden" name="action" value="setSelect">
        <input type="hidden" name="select" value="<%=fh_select%>">
        <logic:present name="freight_handler.vector">
            <bean:size id="rescount"  name="freight_handler.vector"/>
            Search result count:  <bean:write name="rescount" />
            <logic:greaterThan name="rescount" value="0">


                <table ID=803 width="769" border="0" class="results">

                    <tr align=left>
                        <td><a ID=804 class="tableheader" href="storeFreightHandlerLocate.do?action=sort&sortField=id">Id</td>
                        <td><a ID=805 class="tableheader" href="storeFreightHandlerLocate.do?action=sort&sortField=name">Name</td>
                        <td><a ID=806 class="tableheader" href="storeFreightHandlerLocate.do?action=sort&sortField=address">Address 1</td>
                        <td><a ID=807 class="tableheader" href="storeFreightHandlerLocate.do?action=sort&sortField=city">City</td>
                        <td><a ID=808 class="tableheader" href="storeFreightHandlerLocate.do?action=sort&sortField=state">State</td>
                        <td><a ID=809 class="tableheader" href="storeFreightHandlerLocate.do?action=sort&sortField=status">Status</td>
                        <td><a ID=810 class="tableheader" href="storeFreightHandlerLocate.do?action=sort&sortField=ediRoutingCd">EDI Routing Code</td>
                    </tr>

                    <logic:iterate id="arrele" name="freight_handler.vector">
                        <tr>
                            <td><bean:write name="arrele" property="busEntityData.busEntityId"/></td>
                            <td>
                                <bean:define id="key"  name="arrele" property="busEntityData.busEntityId"/>
                                <bean:define id="name" name="arrele" property="busEntityData.shortDesc" type="String"/>

                                <% String []fh_param = {key.toString(),"",""};%>
                                <app:JSCall f_name="passIdAndNameFH" param="<%=fh_param%>">
                                    <bean:write name="arrele" property="busEntityData.shortDesc"/>
                                </app:JSCall>
                            </td>
                            <td><bean:write name="arrele" property="primaryAddress.address1"/></td>
                            <td><bean:write name="arrele" property="primaryAddress.city"/></td>
                            <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
                            <td><bean:write name="arrele" property="busEntityData.busEntityStatusCd"/></td>
                            <td><bean:write name="arrele" property="ediRoutingCd"/></td>
                        </tr>
                    </logic:iterate>
                </table>


            </logic:greaterThan>
        </logic:present></html:form>
</td> </tr>


