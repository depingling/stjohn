<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<html:html>

    <head>
        <link rel="stylesheet" href="../externals/styles.css">
        <title>Domain Administrator</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    </head>

    <body>

    <table border=0 width="<%=Constants.TABLEWIDTH%>" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <jsp:include flush='true' page="ui/systemToolbar.jsp"/>
            </td>
        </tr>
        <tr>
            <td>
                <jsp:include flush='true' page="ui/loginInfo.jsp"/>
            </td>
        </tr>
        <tr>
            <td>
                <jsp:include flush='true' page="ui/admDomainToolbar.jsp"/>
            </td>
        </tr>
    </table>


    <html:form name="DOMAIN_ADM_CONFIG_FORM" action="/adminportal/domainAdm.do?action=Save">
        <logic:present name="DOMAIN_ADM_CONFIG_FORM" property="templateDomain">

            <table width="<%=Constants.TABLEWIDTH%>" border="0" cellpadding="0" cellspacing="0">

                <tr>
                    <td bgcolor="#CCCCCC">
                        <table width="75%" border="0">
                            <logic:greaterThan name="DOMAIN_ADM_CONFIG_FORM" property="templateDomain.domainId" value="0">

                                <tr>
                                    <td colspan="2" bgcolor="#CCCCCC">
                                        <h3>The domain template was created successfull.</h3>
                                    </td>
                                </tr>

                                 <tr>
                                    <td colspan="2" bgcolor="#CCCCCC">
                                       &nbsp;
                                    </td>
                                </tr>

                                <tr>
                                    <td width="25%"><b>Domain Id:</b></td>
                                    <td width="67%">
                                        <bean:write name="DOMAIN_ADM_CONFIG_FORM" property="templateDomain.domainId"/>
                                    </td>
                                </tr>

                            </logic:greaterThan>

                            <tr>
                                <td width="25%"><b>Domain Name:</b></td>
                                <td width="67%">
                                    <html:text name="DOMAIN_ADM_CONFIG_FORM" property="templateDomain.domainName"/>
                                </td>
                                <td width="8%">&nbsp;</td>
                            </tr>
                            <tr>
                                <td><b>Status:</b></td>
                                <td>
                                    <html:select name="DOMAIN_ADM_CONFIG_FORM"
                                                 property="templateDomain.domainStatusCd">
                                        <html:option  value="<%=RefCodeNames.DOMAIN_STATUS_CD.ACTIVE%>">
                                            <%=RefCodeNames.DOMAIN_STATUS_CD.ACTIVE%>
                                        </html:option>
                                         <html:option  value="<%=RefCodeNames.DOMAIN_STATUS_CD.INACTIVE%>">
                                            <%=RefCodeNames.DOMAIN_STATUS_CD.INACTIVE%>
                                        </html:option>
                                    </html:select>
                                </td>
                                <td>&nbsp;</td>
                            </tr>
                            <html:hidden  name="DOMAIN_ADM_CONFIG_FORM"
                                          property="templateDomain.domainTypeCd"
                                          value="<%=RefCodeNames.PROCESS_TYPE_CD.TEMPLATE%>"/>

                            <logic:equal name="DOMAIN_ADM_CONFIG_FORM" property="templateDomain.domainId" value="0">

                                <tr>
                                    <td colspan="2">
                                        <html:submit property="action">
                                            Save
                                        </html:submit>
                                    </td>
                                </tr>
                            </logic:equal>
                        </table>
                    </td>
                </tr>
            </table>

        </logic:present>
        <logic:notPresent name="DOMAIN_ADM_CONFIG_FORM" property="templateDomain">
            <table width="75%" border="0">
                <tr>
                    <td bgcolor="#CCCCCC">
                        Can'not getting template data
                    </td>
                </tr>
            </table>
        </logic:notPresent>

    </html:form>
    </body>
</html:html>

