<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

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
    <title>Quartz Administrator - Create Job</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript1.2" >
function saveJob() {
  if ("" == document.getElementsByName("jobDetail.name")[0].value ||
      ("" == document.getElementsByName("jobClassName")[0].value && "" == document.getElementsByName("jobClassNameSelect")[0].value) ||
      "" == document.getElementsByName("jobDetail.group")[0].value) {
        alert("Job name, group and class cannot be empty");
        return false;
  } else if (document.getElementsByName("jobDetail.description")[0].value.length > 120) {
        alert("Description cannot be more than 120 symbols. Actual size is " +
              document.getElementsByName("jobDetail.description")[0].value.length);
        return false;
  } else {
    document.getElementsByName("userAction")[0].value = "saveJob";
    document.forms[0].action += "jobSearch";
    document.forms[0].submit();
    return false;
  }
}
function addTrigger() {
  document.getElementsByName("userAction")[0].value = "addTrigger";
  document.forms[0].action += "cronTriggerAddEdit";
  document.forms[0].submit();
  return false;
}
function addParameter() {
  if ("" == document.getElementsByName("jobDetail.name")[0].value ||
      ("" == document.getElementsByName("jobClassName")[0].value && "" == document.getElementsByName("jobClassNameSelect")[0].value) ||
      "" == document.getElementsByName("jobDetail.group")[0].value) {
        alert("Job name, group and class cannot be empty");
        return false;
  }
  document.getElementsByName("userAction")[0].value = "addParameter";
  document.forms[0].action += "jobAddEdit";
  document.forms[0].submit();
  return false;
}
function cloneJob() {
	  document.getElementsByName("userAction")[0].value = "jobCopyAddEdit";
	  document.forms[0].action += "jobAddEdit";
	  document.forms[0].submit();
	  return false;
}
function deleteJob() {
	  document.getElementsByName("userAction")[0].value = "jobDelete";
	  document.forms[0].action += "jobSearch";
	  document.forms[0].submit();
	  return false;
}

function changeName(o) {
  var oldEl = document.getElementsByName("parValue" + o.name.substring(7, o.name.length))[0];
  if ("password" == o.value.toLowerCase() && "password" != oldEl.type.toLowerCase()) {
    type = "password";
  } else if ("password" != o.value.toLowerCase() && "text" != oldEl.type.toLowerCase()) {
    type = "text";
  }
  if (type != null) {
    eval("var newEl = document.createElement('<input type=' + type + ' name=' + oldEl.name + '>')");
    newEl.value = oldEl.value;
    if("password" == type) {
      newEl.size = 31;
    } else {
      newEl.size = 65;
    }
    newEl.onchange = function () {changePassword(this);};
    var parent = oldEl.parentElement;
    parent.removeChild(oldEl);
    parent.appendChild(newEl);
    newEl.focus();
    newEl.select();
  }
return false;
}

function changePassword(o) {
    var name = "Confirm" + o.name;
    var type = "password";
    eval("var newEl = document.createElement('<input type=' + type + ' name=' + name + '>')");
    newEl.onblur = function () {confirmPassword(this);};
    newEl.size = 31;
    var parent = o.parentElement;
    parent.appendChild(newEl);
    newEl.focus();
    newEl.select();
return false;
}

function confirmPassword(o) {
  var psw = document.getElementsByName(o.name.substring(7, o.name.length))[0];
  if (o.value != psw.value) {
    alert("The password confirmation does not match");
    psw.value = "";
    psw.focus();
    psw.select();
  }
  var parent = o.parentElement;
  parent.removeChild(o);
return false;
}
</script>

</head>

<body>
<%
    String action = request.getParameter("action");
    if (action == null) {
        action = "init";
    }
%>
<html:errors/>
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
<table bgcolor="#cccccc" width="769" border="0">
<html:form
           action="/adminportal/quartzAdm.do?action=">
<html:hidden property="userAction"/>
<html:hidden property="jobFullName" value='<%=theForm.getJobDetail().getFullName()%>'/>
  <tr>
    <td colspan="2">
      <table bgcolor="#cccccc" width="100%">
        <tr>
          <td align="left">
          Job Group:
          </td>
          <td align="left" colspan="3">
            <html:text name="QUARTZ_ADM_CONFIG_FORM" property="jobDetail.group"/>
          </td>
        </tr>
        <tr>
          <td align="left">
          Job Name:
          </td>
          <td align="left" colspan="3">
            <html:text name="QUARTZ_ADM_CONFIG_FORM" property="jobDetail.name"/>
          </td>
        </tr>
        <tr>
          <td align="left">
          Job Class:
          </td>
          <td align="left">
                <html:select name="QUARTZ_ADM_CONFIG_FORM" property="jobClassNameSelect">
                	<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                	<html:options collection="jobClassNames" property="value" labelProperty="label"/>
          		</html:select>
          </td>
          <td align="right">Manual Job Class:</td>
          <td align="left">
            <html:text name="QUARTZ_ADM_CONFIG_FORM" property="jobClassName"/>
          </td>
        </tr>
        <tr>
          <td align="left">
          Description:
          </td>
          <td align="left" colspan="3">
            <html:textarea name="QUARTZ_ADM_CONFIG_FORM" property="jobDetail.description" rows="3" style="width:540px;"/>
          </td>
        </tr>
        <tr>
          <td align="left">
          Req. Recovery:
          </td>
          <td align="left">
            <html:checkbox name="QUARTZ_ADM_CONFIG_FORM" property="requestsRecovery"/>
          </td>
          <td align="left" colspan="2" style="padding-left:50px;">
          If a job "requests recovery", and it is executing during the time of a 'hard shutdown' of the scheduler (i.e. the process it is running within crashes, or the machine is shut off), then it is re-executed when the scheduler is started again.
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
	<td>

	<b>Help:</b><br>
<b>Sending Outbound Files:</b>
	Job Class (above) should be: <i>com.cleanwise.service.apps.dataexchange.OutboundTranslate</i>
<br>
<br>
Paramters are:<br>
<ul>
<li>One of erpnum, tradingPartnerList, or busEntityIdList is requiered
<li><i>erpnum</i> *Deprecated*  This would be the erp number of the entity that is being used.</li>
<li><i>tradingPartnerList</i> List of trading partner ids to use (comma seperated list).  This is the prefered option.</li>
<li><i>busEntityIdList</i> List of busEnitity ids to use, ie distributor ids, account ids, etc (comma seperated list).  Use trading partner ids when possible.</li>
<li><i>ofile</i> *Optional* *Deprecated* Output file name to generate.  Not always used if the underlying integration overides this.</li>
<li><i>setType</i> *Requiered* Type of transaction to generate (850, 810, 855, 856, etc)</li>
</ul>

<br>
<br>
<b>ftpmode</b>
This is the main mechanism for choosing how data is to be sent.  There are a variety of different ways, some are documented here:
<br><br>
<b>Mail options:</b><br>
for sending files via email set the ftpmode: "email-attachment" or "email-inline"<br>
<ul>
	<li><i>email-inline</i> will render the contents of the file as part of the body of the email...this only works in text based file transfers.</li>
	<li><i>email-attachment</i> will generate an email with an attachment</li>
	<li>&nbsp;</li>
	<li><i>email-attachment-type</i> *Optional* (used for email-attachment) should be the mime encoding of the attachment.  Defaults to application/octet-stream.</li>
	<li><i>emailfrom</i> *Optional (but encoraged)* who the message should be from</li>
	<li><i>emailsubject</i> *Optional* subject fo the email message</li>
	<li><i>emailtext</i> *Optional* (not used for email-inline)</li>
	<li><i>emailto</i> *Requiered* tohost also works but is discoraged.</li>
</ul>
<br><br>
<b>FTP options:</b>
for sending files via ftp use active or passive<br>
<ul>
	<li><i>active</i> will send files to an actie ftp server.  This is not the norm, and unless trading partner directs us as having an active server passive should be assumed.</li>
	<li><i>passive</i> will send files to a passive ftp server.</li>
    <li>&nbsp;</li>
	<li><i>password</i> *requiered* password on remote host</li>
	<li><i>username</i> *requiered* username on remote host</li>
	<li><i>tohost</i> *requiered* remote host, may be server name or ip address</li>
	<li><i>todir</i> *optional* directory on remote host (defaults to whatever the users home directory).  Not used in active connection.</li>
    <li><i>transfer_type</i> *requiered* either binary or ascii</li>
	<li><i>individualTransaction</i> *optional* used for finiky servers, will send each file in its own ftp session, i.e. logs out and back in after sending each file.</li>
	<li><i>makeDirs</i> *optional* if reote directories do not exists should we attempt to create them (true/false)</li>
	<li><i>flatten</i> Not used anymore, but would flatten directory structure when sending files.</li>
	<li><i>exceptionOnFileExists</i> Defaults to true.  If file is already there will not overwrite it.  Results in a failed event. </li>
</ul>
<br><br>
<b>HTTP Post:</b>
for sending files via Http or Https<br>
<ul>

	<li><i>http</i> will send files using an http post.</li>
	<li>&nbsp;</li>
	<li><i>password</i> *optional* password on remote host (simple authentication only)</li>
	<li><i>username</i> *optional* username on remote host (simple authentication only)</li>
	<li><i>tohost</i> *requiered* remote host, may be server name or ip address.  Include full http or https (ex http://store.cleanwise.com).</li>
	<li><i>responseCheck</i> *optional* Check if this string exists in the responseText from the server.  If it does not will fail the event.</li>
</ul>
	
<br><br>
<b>SOAP:</b>
for sending files via SOAP<br>
<ul>

	<li><i>soap</i> will send files using the soap protocol.  Currently supports *fake* soap using http post.</li>
	<li>&nbsp;</li>
	<li><i>password</i> *optional* password on remote host (simple authentication only)</li>
	<li><i>username</i> *optional* username on remote host (simple authentication only)</li>
	<li><i>tohost</i> *requiered* remote host, may be server name or ip address.  Include full http or https (ex http://www.example.com).</li>
	<li><i>responseCheck</i> *optional* Check if this string exists in the responseText from the server.  If it does not will fail the event.</li>
	<li><i>soapfilenameparam</i> *requiered* name of file, this is the soap name and not the actual name of the file.  Should be supplied by trading partner.</li>
	<li><i>soapoperation</i> *requiered* name of soap operation.  Should be supplied by trading partner./li>
	<li><i>soapParam1Name</i> *optional* additional parameter used in integration.  Should be supplied by trading partner.</li>
	<li><i>soapParam1Val</i> *optional* additional parameter value used in integration.  Should be supplied by trading partner.</li>
	<li><i>soapParam<n>Name</i> *optional* additional parameters used in integration.  Should be supplied by trading partner.</li>
	<li><i>soapParam<n>Val</i> *optional* additional parameters value used in integration.  Should be supplied by trading partner.</li>
</ul>

<br><br>
<b>ChunkSize:</b>
for processing inbound files in chunks. (E.g. ChunkSize 10, Number of records to process 20, 2 new events will be created)

	</pre>
  </tr>
  <tr>
    <td align="left" colspan="4">
      <table bgcolor="#cccccc" width="100%" border="0">
        <tr>
          <td align="left" width="250"><b>Job Parameters</b></td>
          <td align="right" width="100%">
            <%// if (theForm.isExists()) {%>
            <html:button value="Add parameter" onclick="addParameter();" property="buttonAddPar" style="width:110px;"/>
            <%//}%>
          </td>
        </tr>
        <tr>
          <td width="250" align="left">Name</td>
          <td align="left">Value</td>
        </tr>
        <logic:iterate id="parval" indexId="i" name="QUARTZ_ADM_CONFIG_FORM" property="parVals"
        type="com.cleanwise.view.forms.QuartzAdmMgrForm.ParValData">
        <tr>
          <td width="250" align="left"><html:text name="QUARTZ_ADM_CONFIG_FORM" property='<%="parName["+i+"]"%>' onchange="changeName(this);" size='20' /></td>
          <td align="left">
            <% if ("password".equalsIgnoreCase(theForm.getParName(i.intValue()))) {%>
              <html:password name="QUARTZ_ADM_CONFIG_FORM" property='<%="parValue["+i+"]"%>' onchange="changePassword(this)" size='31'/>
            <%} else {%>
              <html:text name="QUARTZ_ADM_CONFIG_FORM" property='<%="parValue["+i+"]"%>' size='65'/>
            <%}%>
          </td>
        </tr>
        </logic:iterate>
       </table>
  <tr>
    <td colspan="2" align="left"><hr/></td>
  </tr>
  <tr>
    <td align="left">
       <html:button value="Save" onclick="saveJob();" property="buttonSaveJob" style="width:110px;"/>
		<%if (theForm.isExists()) { %>
 	      <html:button value="Clone" onclick="cloneJob();" property="buttonCloneJob" style="width:110px;"/>
 	      <html:button value="Delete" onclick="deleteJob();" property="buttonDeleteJob" style="width:110px;"/>
		<% } %>
    </td>
    <td align="right">
<% if (theForm.isExists()) {%>
       <html:button value="Add Trigger" onclick="addTrigger();" property="buttonAddTrigger" style="width:110px;"/>
<%}%>
    </td>
  </tr>
</html:form>
</table>

<% if (theForm.getSearchTriggers().size() > 0) {%>
<table bgcolor="#cccccc" width="769">
    <tr>
        <td colspan="7" align="left"><hr/></td>
    </tr>
    <tr>
        <td colspan="7" align="left" valign="top"><b>Triggers</b></td>
    </tr>
    <tr>
        <td>Name</td>
        <td>Group</td>
        <td>Last Fire</td>
        <td>Fires Next</td>
        <td>Description</td>
        <td colspan="2"/>
    </tr>

    <logic:iterate id="trigger" name="QUARTZ_ADM_CONFIG_FORM" property="searchTriggers" indexId="i">
        <tr>
            <td><bean:write name="trigger" property="name"/></td>
            <td><bean:write name="trigger" property="group"/></td>
            <td>
              <logic:present name="trigger" property="previousFireTime">
                <bean:define id="prev" name="trigger" property="previousFireTime"/>
                <i18n:formatDate value="<%=prev%>" pattern="MM/dd/yyyy HH:mm:ss" locale="<%=Locale.US%>"/>
              </logic:present>
            </td>
            <td>
              <logic:present name="trigger" property="nextFireTime">
                <bean:define id="next" name="trigger" property="nextFireTime"/>
                <i18n:formatDate value="<%=next%>" pattern="MM/dd/yyyy HH:mm:ss" locale="<%=Locale.US%>"/>
              </logic:present>
            </td>
            <td><bean:write name="trigger" property="description"/></td>
            <td>
                <a href="quartzAdm.do?action=cronTriggerAddEdit&fullName=<bean:write name="trigger" property="fullName"/>">Edit</a>
            </td>

            <td>
                <a href="quartzAdm.do?action=jobAddEdit&func=delete&fullName=<bean:write name="trigger" property="fullName"/>">Delete</a>
            </td>
        </tr>
    </logic:iterate>
</table>
<%}%>
    <jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>
