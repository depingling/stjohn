<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<app:setLocaleAndImages/>
<app:checkLogon/>

<bean:define id="Location" value="none" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="QUARTZ_ADM_CONFIG_FORM" type="com.cleanwise.view.forms.QuartzAdmMgrForm"/>

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
    <title>Quartz Administrator - Add/Edit CronTrigger</title>
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
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript1.2" >
function saveTrigger() {
  if ("" == document.getElementsByName("cronTrigger.name")[0].value ||
      "" == document.getElementsByName("cronTrigger.group")[0].value ||
      "" == document.getElementsByName("cronTrigger.jobName")[0].value ||
      "" == document.getElementsByName("cronTrigger.jobGroup")[0].value) {
        alert("Job name, job group ,trigger name and group cannot be empty");
        return false;
  } else if ("" == document.getElementsByName("cronTrigger.cronExpression")[0].value) {
        alert("Cron expression cannot be empty");
        return false;
  } else if (document.getElementsByName("cronTrigger.description")[0].value.length > 120) {
        alert("Description cannot be more than 120 symbols. Actual size is " +
              document.getElementsByName("cronTrigger.description")[0].value.length);
        return false;
  } else {
    document.getElementsByName("userAction")[0].value = "saveTrigger";
    document.forms[0].action += "triggerSearch";
    document.forms[0].submit();
    return false;
  }
}
</script>

</head>

<body>
<%
    String action = request.getParameter("action");
    if (action == null) {
        action = "init";
    }
    String func = request.getParameter("func");
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
            <jsp:include flush='true' page="ui/admQuartzToolbar.jsp"/>
        </td>
    </tr>
</table>
<table bgcolor="#cccccc" width="769">
<html:form
           action="/adminportal/quartzAdm.do?action=">
<html:hidden property="userAction"/>
  <tr>
    <td>
      <table bgcolor="#cccccc" width="100%">
        <tr>
          <td align="left">
          Job Group:
          </td>
          <td align="left" colspan="3">
<% if ("newJob".equals(func)) {%>
          <html:text name="QUARTZ_ADM_CONFIG_FORM" property="cronTrigger.jobGroup"/>
<%} else {%>
          <html:text name="QUARTZ_ADM_CONFIG_FORM" property="cronTrigger.jobGroup" disabled="true"/>
<%}%>
          </td>
        </tr>
        <tr>
          <td align="left">
          Job Name:
          </td>
          <td align="left" colspan="3">
<% if ("newJob".equals(func)) {%>
          <html:text name="QUARTZ_ADM_CONFIG_FORM" property="cronTrigger.jobName"/>
<%} else {%>
          <html:text name="QUARTZ_ADM_CONFIG_FORM" property="cronTrigger.jobName" disabled="true"/>
<%}%>
          </td>
        </tr>
        <tr>
          <td align="left">
          Trigger Group:
          </td>
          <td align="left" colspan="3">
            <html:text name="QUARTZ_ADM_CONFIG_FORM" property="cronTrigger.group"/>
          </td>
        </tr>
        <tr>
          <td align="left">
          Trigger Name:
          </td>
          <td align="left" colspan="3">
            <html:text name="QUARTZ_ADM_CONFIG_FORM" property="cronTrigger.name"/>
          </td>
        </tr>
        <tr>
          <td align="left">
          Description:
          </td>
          <td align="left" colspan="3">
            <html:textarea name="QUARTZ_ADM_CONFIG_FORM" property="cronTrigger.description" rows="3" style="width:540px;"/>
          </td>
        </tr>
        <tr>
          <td align="left">
          Start Time:
          </td>
          <td align="left">
            <html:text name="QUARTZ_ADM_CONFIG_FORM" property="triggerStartDate" size="6"/>
                <% if ("Y".equals(isMSIE)) { %>
                <a href="#"
                   onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DATESTART, document.forms[0].triggerStartDate, null, -7300, 7300, null, 0);"
                   title="Choose Date"><img name="DATESTART" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                              align="absmiddle" style="position:relative"
                              onmouseover="window.status='Choose Date';return true"
                              onmouseout="window.status='';return true"></a>
                <% } else { %>
                <a href="javascript:show_calendar('forms[0].triggerStartDate');"
                   onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;"
                   title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
                <% } %>
            <html:text name="QUARTZ_ADM_CONFIG_FORM" property="triggerStartTime" size="6"/>
          </td>
          <td align="left">
          Stop Time:
          </td>
          <td align="left">
            <html:text name="QUARTZ_ADM_CONFIG_FORM" property="triggerEndDate" size="6"/>
                <% if ("Y".equals(isMSIE)) { %>
                <a href="#"
                   onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DATEEND, document.forms[0].triggerEndDate, null, -7300, 7300);"
                   title="Choose Date"><img name="DATEEND" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                              align="absmiddle" style="position:relative"
                              onmouseover="window.status='Choose Date';return true"
                              onmouseout="window.status='';return true"></a>
                <% } else { %>
                <a href="javascript:show_calendar('forms[0].triggerEndDate');"
                   onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;"
                   title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
                <% } %>
            <html:text name="QUARTZ_ADM_CONFIG_FORM" property="triggerEndTime" size="6"/>
          </td>
        </tr>
        <tr>
          <td align="left">
          Cron Expression:
          </td>
          <td align="left" colspan="3">
            <html:text name="QUARTZ_ADM_CONFIG_FORM" property="cronTrigger.cronExpression"/>&nbsp;&nbsp;
            <A HREF="http://www.quartz-scheduler.org/docs/tutorials/crontrigger.html" title="class in org.quartz" target="classFrame">Need Help?</A>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td>
       <html:button value="Save" onclick="saveTrigger();" property="buttonSaveTrigger" style="width:90px;"/>
    </td>
  </tr>
</html:form>
</table>
    <jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>
