<%@ page language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.StringUtils" %>
<%@ page import="com.cleanwise.service.api.session.Event" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<app:setLocaleAndImages/>
<app:checkLogon/>
<bean:define id="Location" value="none" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="INBOUND_FILES_MGR_FORM" type="com.cleanwise.view.forms.InboundFilesMgrForm"/>
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
    <title>Inbound Files</title>
    <%
        String browser = (String) request.getHeader("User-Agent");
        String isMSIE = "";
        if (browser != null && browser.indexOf("MSIE") >= 0) {
            isMSIE = "Y";
    %>
    <script language="JavaScript" src="../externals/calendar.js"></script>
    <iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
            marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
    <% } else { %>
    <script language="JavaScript" src="../externals/calendarNS.js"></script>
    <% } %>
    <script src="../externals/table-sort.js" language="javascript"></script>
    <script src="../externals/lib.js" language="javascript"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <script type="text/javascript">
        function download(id) {
            var frm = document.getElementById('downloadForm');
            var fn = document.getElementById('inboundFileName_' + id);
            if (frm && fn) {
                frm.elements['id'].value = id;
                frm.elements["filename"].value = fn.value;
                frm.submit();
            }
        }
    </script>
</head>
<body>
    <%@include file="/externals/calendar/calendar.jsp"%>
    <%
        String action = request.getParameter("action");
        if (action == null) {
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
        <tr>
            <td>
                <jsp:include flush='true' page="ui/admInboundFilesToolbar.jsp"/>
            </td>
        </tr>
    </table>
    <html:errors/>
    <html:form action="/adminportal/inboundFilesAdm.do?action=search" enctype="multipart/form-data" method="post">
        <table bgcolor="#cccccc" width="769">
            <tr>
                <td align="left" width="1%">Inbound Id:</td>
                <td align="left" width="1%" colspan="2">
                    <html:text name="INBOUND_FILES_MGR_FORM" property="searchInboundId"/>
                </td>
            </tr>
            <tr>
                <td align="left"><nobr>From Date:</nobr></td>
                <td align="left">
                    <nobr>
                        <html:text name="INBOUND_FILES_MGR_FORM" styleId="searchDateFrom" property="searchDateFrom" maxlength="10"/>
                        <a href="#"
                           onClick="showCalendar('searchDateFrom', event);return false;"
                           title="Choose Date"><img name="DATEFROM" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                                      align="absmiddle" style="position:relative"
                                      onmouseover="window.status='Choose Date';return true"
                                      onmouseout="window.status='';return true">
                        </a>
                    </nobr>
                </td>
                <td align="left"><nobr>To Date:</nobr></td>
                <td align="left">
                    <nobr>
                        <html:text name="INBOUND_FILES_MGR_FORM" styleId="searchDateTo" property="searchDateTo" maxlength="10"/>
                        <a href="#"
                           onClick="showCalendar('searchDateTo', event);return false;"
                           title="Choose Date"><img name="DATETO" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                                      align="absmiddle" style="position:relative"
                                      onmouseover="window.status='Choose Date';return true"
                                      onmouseout="window.status='';return true">
                        </a>
                    </nobr>
                </td>
            </tr>
            <tr>
                <td align="left"><nobr>File Name Search:</nobr></td>
                <td align="left" colspan="2">
                    <html:text name="INBOUND_FILES_MGR_FORM" property="searchFileName" style="width:175px;"/>
                </td>
            </tr>
            <tr>
                <td align="left"><nobr>Partner Key Search:</nobr></td>
                <td align="left" colspan="2">
                    <html:text name="INBOUND_FILES_MGR_FORM" property="searchPartnerKey" style="width:175px;"/>
                </td>
            </tr>
            <tr>
                <td align="left"><nobr>Url Search:</nobr></td>
                <td align="left" colspan="2">
                    <html:text name="INBOUND_FILES_MGR_FORM" property="searchUrl" style="width:175px;"/>
                </td>
            </tr>
            <tr>
                <td align="left"><nobr>Blob Text Search (Slow search):</td>
                <td align="left" colspan="2">
                    <html:text name="INBOUND_FILES_MGR_FORM" property="searchTextInFile" style="width:175px;"/>
                </td>
            </tr>
            <tr>
                <td><input type="submit" name="action" value="Search"></td>
                <td colspan="2"></td>
            </tr>
        </table>
    </html:form>
    <html:form styleId="downloadForm" action="/adminportal/inboundFilesAdm.do?action=download" method="post" target="_self">
        <input type="hidden" name="filename" value=""/>
        <input type="hidden" name="id" value=""/>
    </html:form>
    <table  class="stpTable_sortable" id="ts1" bgcolor="#cccccc" width="769" border="0">
        <thead>
            <tr class="stpTH">
                <th class=stpTH>Inbound ID</th>
                <th class=stpTH>File Name</th>
                <th class=stpTH>Partner Key</th>
                <th class=stpTH>Url</th>
                <th class=stpTH>Add Date</th>
                <th class=stpTH>Mod Date</th>
            </tr>
        </thead>
        <tbody>
        <logic:notEmpty name="INBOUND_FILES_MGR_FORM" property="inboundFiles">
            <logic:iterate id="inboundFile" name="INBOUND_FILES_MGR_FORM" property="inboundFiles" indexId="i">
                <%
                InboundData inboundData = (InboundData)theForm.getInboundFiles().get(i);
                String extractedFileName = StringUtils.extractFileName(inboundData.getFileName());
                %>
                <tr class="stpTD">
                    <td class="stpTD"><bean:write name="inboundFile" property="inboundId"/></td>
                    <td class="stpTD">
                        <input class="stpTD" type="text" id="inboundFileName_<bean:write name="inboundFile" property="inboundId"/>" size="21" value="<%=extractedFileName%>"/>
                        <input class="stpTD" type="button" value="Download" onclick="download(<bean:write name="inboundFile" property="inboundId"/>);">
                    </td>
                    <td class="stpTD"><bean:write name="inboundFile" property="partnerKey"/></td>
                    <td class="stpTD"><bean:write name="inboundFile" property="url"/></td>
                    <td class="stpTD"><bean:write name="inboundFile" property="addDate"/></td>
                    <td class="stpTD"><bean:write name="inboundFile" property="modDate"/></td>
                </tr>
            </logic:iterate>
        </logic:notEmpty>
            <tr><td height="5" colspan="6"></td></tr>
            <tr><td class="stpTD" colspan="6" align="left">Count: <bean:write name="INBOUND_FILES_MGR_FORM" property="searchInboundFilesCount"/></td></tr>
            <tr><td height="5" colspan="6"></td></tr>
        </tbody>
    </table>
    <div class="admres">
        Inbound Files(s) total count in system: <bean:write name="INBOUND_FILES_MGR_FORM" property="inboundFilesCount"/>
    </div>
</body>
</html:html>
