<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.session.Event" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<app:setLocaleAndImages/>
<app:checkLogon/>

<bean:define id="Location" value="none" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="EVENT_ADM_CONFIG_FORM" type="com.cleanwise.view.forms.EventAdmMgrForm"/>

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
        <title>Event Administrator</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript1.2" >
function doStart() {
    document.forms[0].action += "&func=start";
    document.forms[0].submit();
    return false;
}
function doStop() {
    document.forms[0].action += "&func=stop";
    document.forms[0].submit();
    return false;
}
function doRefresh() {
    document.forms[0].action += "&func=refresh";
    document.forms[0].submit();
    return false;
}
function switchExt(obj) {
	var el = document.getElementsByName(obj);
        for(var i=0;i<el.length;i++) {
          if (el[i].tagName == 'SPAN') {
            if (el[i].innerHTML == "-") {
              el[i].innerHTML = "+"
            } else {
              el[i].innerHTML = "-"
            }
          } else {
            if (el[i].style.display != "none") {
              el[i].style.display = "none";
            } else {
              el[i].style.display = 'block';
            }
          }
        }
}
</script>

    </head>

    <body>
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
        <tr>
            <td>
                <jsp:include flush='true' page="ui/admEventToolbar.jsp"/>
            </td>
        </tr>
    </table>
    <table bgcolor="#cccccc" width="769">
        <html:form
                action="/adminportal/eventAdm.do?action=control"
                enctype="multipart/form-data">

           <%
           boolean startDisable = false;
           boolean stopDisable = false;
           String statusLabel;
           if (theForm.getSystemRunning()) {
             statusLabel = "Event processing is running";
             startDisable = true;
           } else {
             statusLabel = "Event processing is stopped";
             stopDisable = true;
             if (theForm.getStoppingFile()) {
               statusLabel += " (by a stopping file)";
             }
           }
           %>
           <tr>
             <td align="center" colspan="7" height="50"><font class='trainingsubhead'><%=statusLabel%></font></td>
           </tr>
           <tr><td align="center" colspan="7"><hr/></td></tr>

            <tr>
             <td align="center" colspan="3">
               <html:button value="Refresh" onclick="doRefresh();" property="buttonRefresh" style="width:140px;"/>
             </td>
             <td align="center" colspan="2">
               <html:button value="Start Processing" onclick="doStart();" property="buttonStart" style="width:140px;" disabled="<%=startDisable%>"/>
             </td>
              <td align="center" colspan="2">
                <html:button value="Stop Processing" onclick="doStop();" property="buttonStop" style="width:140px;" disabled="<%=stopDisable%>"/>
             </td>
            </tr>
           <%
           if (theForm.getSystemRunning()) {
           %>
           <tr><td align="center" colspan="7"><hr/></td></tr>
            <tr>
              <td width="20"/>
              <td width="80"/>
              <td width="80"/>
              <td width="60%"/>
              <td width="40%"/>
              <td nowrap="nowrap" colspan="2" align="center" width="140"><b>Elapsed Time(sec)</b></td>
            </tr>
            <tr>
              <td align="center"/>
              <td nowrap="nowrap" align="center"><b>EventId</b></td>
              <td nowrap="nowrap" align="center"><b>ThreadId</b></td>
              <td nowrap="nowrap" align="center"><b>Thread Name</b></td>
              <td nowrap="nowrap" align="center"><b>State</b></td>
              <td nowrap="nowrap" align="center"><b>Abs</b></td>
              <td nowrap="nowrap" align="center"><b>Cpu</b></td>
            </tr>


            <logic:iterate id="thread" name="EVENT_ADM_CONFIG_FORM" property="threadStatistic" indexId="i">
              <tr>
                <td><a onclick="switchExt('ext<%=i%>');" style="cursor:pointer;"><span id="ext<%=i%>">+</span></a></td>
                <td nowrap="nowrap" align="center"><bean:write name="thread" property="eventId"/></td>
                <td nowrap="nowrap" align="center"><bean:write name="thread" property="threadId"/></td>
                <td nowrap="nowrap" align="center"><bean:write name="thread" property="name"/></td>
                <td nowrap="nowrap" align="center"><bean:write name="thread" property="state"/></td>
                <td nowrap="nowrap" align="center"><bean:write name="thread" property="absTime"/></td>
                <td nowrap="nowrap" align="center"><bean:write name="thread" property="cpuTime"/></td>
              </tr>
                <logic:iterate id="extention" name="thread" property="ext" indexId="j">
                  <tr id="ext<%=i%>" style="display:none;">
                    <td width="20"/>
                    <td nowrap="nowrap" colspan="6"><bean:write name="extention"/></td>
                  </tr>
                </logic:iterate>
            </logic:iterate>
           <%
           }
           %>

        </html:form>
    </table>
    <div class="admres">

        Event(s) total count in system:<bean:write name="EVENT_ADM_CONFIG_FORM" property="eventCount"/>

    </div>

    </body>
</html:html>
