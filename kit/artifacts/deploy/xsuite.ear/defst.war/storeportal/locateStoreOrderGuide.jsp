<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.LocateStoreOrderGuideForm" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="com.cleanwise.service.api.util.LocatePropertyNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<% 
String jspFormName = request.getParameter("jspFormName");
String jspFormNestProperty = request.getParameter("jspFormNestProperty");

if (jspFormName == null) {
    throw new RuntimeException("jspFormName cannot be null");
}
if (jspFormNestProperty != null) {
    jspFormNestProperty = jspFormNestProperty + ".locateStoreOrderGuideForm";
}
else {
    jspFormNestProperty = "locateStoreOrderGuideForm";
}
%>
<logic:present name="<%=jspFormName%>" property="<%=jspFormNestProperty%>">
<bean:define id="theForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" type="LocateStoreOrderGuideForm"/>
<%
if (theForm != null && theForm.getLocateOrderGuideFl()) {
    String jspFormAction = request.getParameter("jspFormAction");  
    if (jspFormAction == null) {
        throw new RuntimeException("jspFormAction cannot be null");
    }
    String jspSubmitIdent = request.getParameter("jspSubmitIdent");
    if (jspSubmitIdent == null) {
        throw new RuntimeException("jspSubmitIdent cannot be null");
    }
    String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
    if (jspReturnFilterProperty == null) {
        throw new RuntimeException("jspReturnFilterProperty cannot be null");
    }
    jspSubmitIdent += "#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_ORDER_GUIDE_FORM;
%>

    <html:html>
    <script language="JavaScript1.2">
    <!--
    function SetChecked(val) {
        dml=document.forms; 
        for(i=0; i<dml.length; i++) {
            found = false;
            ellen = dml[i].elements.length;
            //alert('next_form='+ellen);
            for(j=0; j<ellen; j++) {
                if (dml[i].elements[j].name=='<%=jspFormNestProperty%>.selectedObjectIds') {
                    dml[i].elements[j].checked=val;
                    found = true;
                }
            }
            if(found)
                break;
        }
    }

    function SetAndSubmit (name, val) {
        var dml=document.forms[0]; 
        var ellen = dml[name].length;
        if(ellen>0) {
            for(j=0; j<ellen; j++) {
                if(dml[name][j].value==val) {
                    found = true;      
                    dml[name][j].checked=1;
                } 
                else {
                    dml[name][j].checked=0;
                }
            }
        } 
        else {
            dml[name].checked=1;
        }
        var iiLast = dml['action'].length-1;
        dml['action'][iiLast].value='<%=LocatePropertyNames.RETURN_SELECTED_ACTION%>';
        dml.submit();
    }

    //-->
    </script>

    <body>
    <script src="../externals/lib.js" language="javascript"></script>
    <div class="rptmid">

    <html:form styleId="325" action="<%=jspFormAction%>" scope="session">
    <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
    <%String prop = jspFormNestProperty + ".property";%>
    <html:hidden property="<%=prop%>" value="<%=jspReturnFilterProperty%>"/>
    <%prop = jspFormNestProperty + ".name";%>
    <html:hidden property="<%=prop%>" value="<%=jspFormName%>"/>

    Find Order Guides
    <table ID=324 width="750" border="0" class="mainbody">
        <tr> 
            <td>Find Order Guide:</td>
            <td colspan="3">
                <%prop = jspFormNestProperty + ".searchObjectField";%>
                <html:text property="<%=prop%>"/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <%prop = jspFormNestProperty + ".searchObjectType";%>
                <html:radio property="<%=prop%>" value="<%=LocatePropertyNames.ID_SEARCH_TYPE%>"/>
                ID
                <html:radio property="<%=prop%>" value="<%=LocatePropertyNames.NAME_BEGINS_SEARCH_TYPE%>"/>
                Name(starts with)
                <html:radio property="<%=prop%>" value="<%=LocatePropertyNames.NAME_CONTAINS_SEARCH_TYPE%>"/>
                Name(contains)
            </td>
        </tr>
        <tr>
            <td colspan='4'>
                <html:submit property="action" value="<%=LocatePropertyNames.SEARCH_ACTION%>"/>
                <%prop = jspFormNestProperty + ".showInactiveObjectFl";%>
                Show Inactive: <html:checkbox property="<%=prop%>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <html:submit property="action" value="<%=LocatePropertyNames.CANCEL_ACTION%>"/>
                <html:submit property="action" value="<%=LocatePropertyNames.RETURN_SELECTED_ACTION%>"/></td>
            </td>
        </tr>
    </table>
    <% 
    int resCount = 0;
    if (theForm.getObjectsSelected() != null) {
        resCount = theForm.getObjectsSelected().size();
    }
    %>
    Search result count: 
    <% 
    if (resCount >= Constants.MAX_ORDER_GUIDES_TO_RETURN) {
    %>
    (request limit)
    <%
    }
    %>
    <%=resCount%>
    <% 
    if (theForm.getObjectsSelected() != null) { 
        if (resCount > 0) {
    %>
    <table ID=326 width="750" border="0" class="results">
        <tr align=left>
            <td><a ID=327 class="tableheader">Id</td>
            <td><a ID=328 class="tableheader">Order Guide Name</a></td>
            <td><a ID=329 class="tableheader">Catalog Name</a></td>
            <td>
                <a ID=330 href="javascript:SetChecked(1)">[Check&nbsp;All]</a><br>
                <a ID=331 href="javascript:SetChecked(0)">[&nbsp;Clear]</a>
            </td>
            <td><a ID=332 class="tableheader">Type</a></td>
            <td><a ID=333 class="tableheader">Status</a></td>
        </tr>

    <%
        String propName = jspFormNestProperty + ".objectsSelected";
        prop = jspFormNestProperty + ".objectsSelected";
        String selectBoxProp = jspFormNestProperty + ".selectedObjectIds";
    %>
    <logic:iterate id="orderGuideObject" name="<%=jspFormName%>" property="<%=prop%>" type="com.cleanwise.service.api.value.OrderGuideDescData">
        <bean:define id="key" name="orderGuideObject" property="orderGuideId"/>
        <%String linkHref = "javascript:SetAndSubmit('" + selectBoxProp + "'," + key + ");";%>
        <tr>
            <td><bean:write name="orderGuideObject" property="orderGuideId"/></td>
            <td><a ID=334 href="<%=linkHref%>"><bean:write name="orderGuideObject" property="orderGuideName"/></a></td>
            <td><bean:write name="orderGuideObject" property="catalogName"/></td>
            <td><html:multibox property="<%=selectBoxProp%>" value="<%=key.toString()%>"/></td>
            <td><bean:write name="orderGuideObject" property="orderGuideTypeCd"/></td>
            <td><bean:write name="orderGuideObject" property="status"/></td>
        </tr>
    </logic:iterate>    
    </table>
    <%
        }
    }
    %>
    <html:hidden property="action" value="<%=LocatePropertyNames.SEARCH_ACTION%>"/>
    </html:form>
    </div>
    </body>
    </html:html>
<%
} //if (theForm != null && theForm.getLocateAccountFl())
%>
</logic:present>
