<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" value="/<%=storeDir%>/en/images" scope="session"/>


<html lang="en">
  <head>
    <title>Cleanwise.com</title>
    <link rel="stylesheet" type="text/css" href="/<%=storeDir%>/externals/pubstyles.css">
  </head>

  <body class="bodyalt">
        <div class="text">
        <%/*The below comments indicate to the client program what errors occured and should not be
        removed or modified or the estore client program will not be able to keep track of what errors
        occured.*/%>
        <font color=red>
        <!--DO NOT EDIT THIS COMMENT. error messages displayed below.-->
                <html:errors/>
        <!--end errors.  DO NOT EDIT THIS COMMENT-->
        </font>
        <br>
        <a href="../logoff.do" alt="logoff">logoff</a>
        <br>
        <a href="estoreclienthome.do?action=download_orders" alt="download orders">download orders</a>
        <br>
        <a href="estoreclienthome.do?action=run_loader" alt="run loader">run loader</a>
        <br>
        <a href="estoreclienthome.do?action=acknowledge_transaction" alt="acknowledge transaction">acknowledge transaction</a>
        <br>
        <html:form action="/estoreclientportal/estoreclienthome.do?action=Load_File" enctype="multipart/form-data">
                <b>Load File:</b>
                <html:file name="ESTORE_CLIENT_COMM_FORM" property="theFile" accept="text/plain"/>
                <input type=submit name=action value="Load_File">
                <br>
                Encrypted File:
                <html:select name="ESTORE_CLIENT_COMM_FORM" property="encrypted">
                        <html:option value="true">yes</html:option>
                        <html:option value="false">no</html:option>
                </html:select>
        </html:form>

        <%--Display a list of all the files we know about--%>
        <table>
                <logic:present name="manualInterventionDirectoryEncrypted">
                        <bean:size id="size" name="manualInterventionDirectoryEncrypted"/>
                        <logic:greaterThan name="size" value="0">
                                <tr><td><b>Manual Intervention Directory Encrypted</b></td></tr>
                        </logic:greaterThan>

                        <logic:iterate indexId="i" id="id" name="manualInterventionDirectoryEncrypted" type="java.io.File" >
                                <%String link = "estoreclienthome.do?action=download_file&type=manualInterventionDirectoryEncrypted&index=" + i;%>
                                <tr><td><font color=red>FAILED: </font><a href="<%=link%>"><bean:write name="id" property="path"/></a></td></tr>
                        </logic:iterate>
                </logic:present>
                <br>

                <logic:present name="manualInterventionDirectoryDecrypted">
                        <bean:size id="size" name="manualInterventionDirectoryDecrypted"/>
                        <logic:greaterThan name="size" value="0">
                                <tr><td><b>Manual Intervention Directory Decrypted</b></td></tr>
                        </logic:greaterThan>

                        <logic:iterate indexId="i" id="id" name="manualInterventionDirectoryDecrypted" type="java.io.File" >
                                <%String link = "estoreclienthome.do?action=download_file&type=manualInterventionDirectoryDecrypted&index=" + i;%>
                                <tr><td><font color=red>FAILED: </font><a href="<%=link%>"><bean:write name="id" property="path"/></a></td></tr>
                        </logic:iterate>
                </logic:present>
                <br>

                <logic:present name="toProcessDirectoryDecrypted">
                        <bean:size id="size" name="toProcessDirectoryDecrypted"/>
                        <logic:greaterThan name="size" value="0">
                                <tr><td><b>To Process Directory (Decrypted)</b></td></tr>
                        </logic:greaterThan>

                        <logic:iterate indexId="i" id="id" name="toProcessDirectoryDecrypted" type="java.io.File" >
                                <%String link = "estoreclienthome.do?action=download_file&type=toProcessDirectoryDecrypted&index=" + i;%>
                                <tr><td><a href="<%=link%>"><bean:write name="id" property="path"/></a></td></tr>
                        </logic:iterate>
                </logic:present>
                <br><br><br>

                <logic:present name="archiveDirectoryEncrypted">
                        <bean:size id="size" name="archiveDirectoryEncrypted"/>
                        <logic:greaterThan name="size" value="0">
                                <tr><td><b>Archive Directory Encrypted</b></td></tr>
                        </logic:greaterThan>

                        <logic:iterate indexId="i" id="id" name="archiveDirectoryEncrypted" type="java.io.File" >
                                <%String link = "estoreclienthome.do?action=download_file&type=archiveDirectoryEncrypted&index=" + i;%>
                                <tr><td><a href="<%=link%>"><bean:write name="id" property="path"/></a></td></tr>
                        </logic:iterate>
                </logic:present>
                <br>

                <logic:present name="archiveToProcessDirectoryDecrypted">
                        <bean:size id="size" name="archiveToProcessDirectoryDecrypted"/>
                        <logic:greaterThan name="size" value="0">
                                <tr><td><b>Archive To Processes Directory (Decrypted and Processed)</b></td></tr>
                        </logic:greaterThan>

                        <logic:iterate indexId="i" id="id" name="archiveToProcessDirectoryDecrypted" type="java.io.File" >
                                <%String link = "estoreclienthome.do?action=download_file&type=archiveToProcessDirectoryDecrypted&index=" + i;%>
                                <tr><td><a href="<%=link%>"><bean:write name="id" property="path"/></a></td></tr>
                        </logic:iterate>
                </logic:present>
                <br>
        </table>
  </body>
</html>
