<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.ContractItemLocateForm" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="theForm" name="CONTRACT_ITEM_LOCATE_FORM" type="com.cleanwise.view.forms.ContractItemLocateForm"/>

<%

    String feedField = (String) request.getParameter("feedField");
    if (null == feedField) {
        feedField = "";
    }

    String feedDesc = (String) request.getParameter("feedDesc");
    if (null == feedDesc) {
        feedDesc = "";
    }

    String assetId = (String) request.getParameter("assetid");
    if (null == assetId) {
        assetId = "";
    }

    String siteId = (String) request.getParameter("siteid");
    if (null == siteId) {
        siteId = "";
    }

    String custUserId = (String) request.getParameter("custuserid");
    if (null == custUserId) {
        custUserId = "";
    }

    String contractId = (String) request.getParameter("contractid");
    if (null == contractId) {
        contractId = "";
    }

%>

<script language="JavaScript1.2">
    <!--

    function passIdAndName(item_id, name, desc) {

        var feedBackFieldName = document.CONTRACT_ITEM_LOCATE_FORM.feedField.value;
        var feedDesc = document.CONTRACT_ITEM_LOCATE_FORM.feedDesc.value;

        if (feedBackFieldName && "" != feedBackFieldName) {
            window.opener.document.forms[0].elements[feedBackFieldName].value = item_id;
        }

        if (feedDesc && "" != feedDesc) {
            window.opener.document.forms[0].elements[feedDesc].value = unescape(desc.replace(/\+/g, ' '));
        }

        var fbele = window.opener.document.getElementById(feedBackFieldName);
        if (fbele) {
            fbele.value = item_id;
        }

        self.close();
    }

    function actionSubmit(formNum, action) {
        var actions;
        actions = document.forms[formNum]["action"];
        //alert(actions.length);
        for (ii = actions.length - 1; ii >= 0; ii--) {
            if (actions[ii].value == 'hiddenAction') {
                actions[ii].value = action;
                document.forms[formNum].submit();
                break;
            }
        }
        return
    }
    //-->
</script>


<html:html>

    <head>
        <title>Search Service</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../externals//styles.css">
    </head>

    <body class="results">
    <div class="text">
        <table width="100%">
            <tr>
                <td>
                    <html:form name="CONTRACT_ITEM_LOCATE_FORM" action="/adminportal/contractServiceLocate.do"
                               type="com.cleanwise.view.forms.ContractItemLocateForm">

                        <table width="100%" bgcolor="#CCCCCC">
                            <tr>
                                <td colspan="3" class="largeheader"><b>Find Service</b></td>
                            </tr>
                            <tr>
                                <td><b>Short Description:</b></td>
                                <td>
                                    <html:text property="shortDescTempl"/>
                                    <html:radio property="searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.ID%>"/>Id
                                    <html:radio property="searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/> Name(starts with)
                                    <html:radio property="searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/> Name(contains)
                                </td>
                                <td></td>
                            </tr>

                            <input type="hidden" name="feedField" value="<%=feedField%>">
                            <input type="hidden" name="feedDesc" value="<%=feedDesc%>">
                            <html:hidden property="contractid" value="<%=contractId%>"/>
                            <html:hidden property="custuserid" value="<%=custUserId%>"/>
                            <html:hidden property="assetid" value="<%=assetId%>"/>
                            <html:hidden property="siteid" value="<%=siteId%>"/>
                            <tr>
                                <td colspan="3"></td>
                            </tr>

                            <tr>
                                <td height="20" colspan="3">
                                        <html:button property="action" value="Search"
                                                     onclick="actionSubmit(0,'searchService');"/>
                            </tr>

                        </table>
                        <logic:present name="CONTRACT_ITEM_LOCATE_FORM" property="services">
                            <bean:define id="services" name="CONTRACT_ITEM_LOCATE_FORM" property="services"/>
                            <%
                                int rescount = ((ServiceViewVector) services).size();
                            %>
                            Search result count: <%=rescount%>
                            <% if (rescount > 0) { %>
                            <table width="750" border="0" class="results">
                                <tr align=left>
                                    <td><a class="tableheader">Id</a></td>
                                    <td><a class="tableheader">Name</a></td>
                                    <td><a class="tableheader">Status</a></td>
                                </tr>
                                <logic:iterate id="arrele" name="CONTRACT_ITEM_LOCATE_FORM" property="services"   type="com.cleanwise.service.api.value.ServiceView">
                                    <bean:define id="key" name="arrele" property="serviceId"/>
                                    <bean:define id="name" name="arrele" property="serviceName" type="String"/>
                                    <tr>
                                        <td>
                                            <bean:write name="arrele" property="serviceId"/>
                                        </td>
                                        <td><%
                                            String onClick = new String("return passIdAndName('" + key + "', '"
                                                    + java.net.URLEncoder.encode(name.toString())
                                                    +"' , '" + java.net.URLEncoder.encode(name.toString()) + "')");
                                            %>

                                            <a href="javascript:void(0);" onclick="<%=onClick%>">
                                                <bean:write name="arrele" property="serviceName"/>
                                            </a>
                                        </td>

                                        <td>
                                            <bean:write name="arrele" property="statusCd"/>
                                        </td>

                                    </tr>
                                </logic:iterate>
                            </table>
                            <%}%>
                        </logic:present>

                        <html:hidden property="action" value="hiddenAction"/>

                    </html:form>

                </td>

            </tr>

        </table>
        <jsp:include flush='true' page="ui/admFooter.jsp"/>
    </div>
    </body>

</html:html>


