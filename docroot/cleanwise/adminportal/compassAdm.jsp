<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.io.File" %>
<%@ page import="com.cleanwise.service.api.util.ClwApiCustomizer"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<app:setLocaleAndImages/>
<app:checkLogon/>

<bean:define id="Location" value="none" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="JASPER_TEST_FORM" type="com.cleanwise.view.forms.JasperTestForm"/>

<html:html>
    <head>
        <link rel="stylesheet" href="../externals/styles.css">
        <style>
            .tt {
                color: white;
                background-color: black;
            }

            .tt1 {
                border-right: solid 1px black;
            }

        </style>
        <title>Jasper Report Test</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    </head>
<% boolean debug = true; %>
    <body>
<jsp:include flush='true' page="/storeportal/locateStoreAccount.jsp">
    <jsp:param name="jspFormAction" 	value="adminportal/compassAdm.do" />
    <jsp:param name="jspFormName" 	value="JASPER_TEST_FORM" />
    <jsp:param name="jspSubmitIdent" value="" />
    <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
</jsp:include>
<jsp:include flush='true' page="/storeportal/locateStoreSite.jsp">
    <jsp:param name="jspFormAction" 	value="adminportal/compassAdm.do" />
    <jsp:param name="jspFormName" 	value="JASPER_TEST_FORM" />
    <jsp:param name="jspSubmitIdent" 	        value="" />
    <jsp:param name="jspReturnFilterProperty" 	value="siteFilter" />
</jsp:include>



    <%
        String action = request.getParameter("action");
        if (action==null) {
            action = "init";
        }
    %>
    <table border=0 width="769" cellpadding="0" cellspacing="0">
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
     </table>
    <table bgcolor="#cccccc" width="769">
        <html:form  action="/adminportal/compassAdm.do"  enctype="multipart/form-data">
        <tr>
            <td align="left"><b>Account :  </b></td>
            <td>
              <html:text  name="JASPER_TEST_FORM" property="accountId" />
              <html:submit property="action" value="Locate Account" styleClass='text'/>
            </td>
        </tr>
        <tr>
            <td align="left"><b>Site : </b></td>
            <td>
                <html:text  name="JASPER_TEST_FORM" property="siteId" />
                <html:submit property="action" value="Locate Site" styleClass='text'/>
            </td>
        </tr>
        <tr>
            <td align="left"><b>Cost Center Id: </b></td>
            <td>
              <html:text  name="JASPER_TEST_FORM" property="costCenterId"  />
            </td>
        </tr>
        <tr><td colspan="2" align="center">
            <html:submit property="action">Show Report</html:submit>
        </td></tr>
 <%
//   String repName = "JasperHtmlReport";
//   String destFile = ClwApiCustomizer.getCustomizeElement("jsreports/" + repName + ".html");
//   destFile = "../jsreports/" +repName + ".html";
    String destFile=theForm.getDestFileName();
   %>
 <tr>
   <td colspan="2" >
 <%--    <app:jasperReport repName="<%=repName% >" name="JASPER_TEST_FORM" property="reportModel"/>  --%>
<%    if (destFile != null){ %>
     <iframe name="jasperReport" src="<%=destFile%>" frameborder="0" scrolling="no" align="middle" height="200" width="270"></iframe>
 <%   } else {   %>
      <p>No Data found for Jasper Report</p>
 <%   } %>
   </td>

 </tr>
        </html:form>
    </table>

    <jsp:include flush='true' page="ui/admFooter.jsp"/>
    </body>
</html:html>
