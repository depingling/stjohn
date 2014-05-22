<%@page contentType="text/html; charset=utf-8"%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.session.Event" %>
<%@ page import="com.cleanwise.service.api.eventsys.FileAttach" %>
<%@ page import="org.apache.struts.upload.FormFile" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.text.SimpleDateFormat" %>

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

        .logMessage {
            margin:0;
            font-size:9pt;
            text-align:left;
            vertical-align:top;
        }

        .verticalAlign {
            vertical-align:top;
        }

    </style>
    <script type="text/javascript">
        function checkForLast() {
            var btns = document.getElementsByName('drop');
            for (var i = 0; i < btns.length; i++) {
                btns[i].disabled = (btns.length == 2);
            }
        }

        function updateTabContent(response) {

            var content = document.getElementById("tabContent");
            if (content && content.tBodies[0]) {
                for (var i = 0; content.tBodies[0].rows.length>0; i++) {
                    content.tBodies[0].deleteRow(0)
                }
            }

            var root = response.getElementsByTagName("Response")[0];

            var requstedProcessId = root.getElementsByTagName("RequstedProcessId")[0].firstChild.nodeValue;
            var activeProcessId = root.getElementsByTagName("ActiveProcessId")[0].firstChild.nodeValue;
            var logginig = root.getElementsByTagName("Logging");

            for (var i = 0; i < logginig.length; i++) {

                var log = logginig.item(i);

                var processId = log.getElementsByTagName("ProcessId")[0].firstChild.nodeValue;
                var message = log.getElementsByTagName("Message")[0];
                var throwable = log.getElementsByTagName("Throwable")[0];
                var addDate = log.getElementsByTagName("AddDate")[0].firstChild.nodeValue;

                var tr = document.createElement("tr");
                var td;
                td = document.createElement("td");
                td.appendChild(document.createTextNode(i+1));
                td.className="verticalAlign";
                tr.appendChild(td);

                td = document.createElement("td");
                td.appendChild(document.createTextNode(processId));
                td.className="verticalAlign";
                tr.appendChild(td);

                td = document.createElement("td");

                if(message!= undefined){
                    var b = document.createElement("b")
                    b.appendChild(document.createTextNode(message.firstChild.nodeValue));
                    td.appendChild(b);
                }

                if(throwable != undefined){
                    var parts = throwable.getElementsByTagName("Part");
                    for(var j=0;j<parts.length;j++) {
                        td.appendChild(document.createTextNode(parts.item(j).firstChild.nodeValue));
                    }
                }
                td.className="logMessage"
                tr.appendChild(td);

                td = document.createElement("td");
                td.appendChild(document.createTextNode(addDate));
                td.className="verticalAlign";
                tr.appendChild(td)
                content.tBodies[0].appendChild(tr)
            }


            document.getElementById("processTab_"+activeProcessId).className="dijitTab";
            document.getElementById("processTab_"+requstedProcessId).className="dijitTab dijitTabChecked dijitChecked";

        }
        function goToNextLog(processId) {
            dojo.xhrGet({load:updateTabContent,url:'eventAdm.do?action=changeProccessLog&requestedLog=' + processId,handleAs:'xml'});
        }



        function dropFile(btn) {
            var tr;
            if (document.getElementById) {
                tr = btn;
                while (tr.tagName != 'TR')tr = tr.parentNode;
                tr.parentNode.removeChild(tr);
                checkForLast();
            }
        }
        function addFile(btn) {
            if (document.getElementById) {
                if (document.getElementById('addfile')) {
                    var template = document.getElementById('addfile').firstChild;
                    var resultNode = template.cloneNode(true);
                    resultNode.className = '';
                    resultNode = template.parentNode.appendChild(resultNode)
                    addChooseEvt(resultNode.getElementsByTagName('input')[0]);
                }
                checkForLast();
            }
        }
        function addChooseEvt(element) {
            function _do() {
                if (element.value.length && !element.parentNode.parentNode.nextSibling) {
                    addFile(element);
                }
            }
        }
        function edit(index) {
            var button  = document.getElementById('button' + index);
            var edit    = document.getElementById('edit' + index);
            var preview = document.getElementById('preview' + index);
            if (button && edit && preview) {
               if (button.value == 'Preview...') {
                   button.value = 'Edit...';
                   edit.style.display = 'none';
                   preview.style.display = 'block';
               } else {
                   button.value = 'Preview...';
                   preview.style.display = 'none';
                   edit.style.display = 'block';
               }
            }
        }
        function update(id, classType) {
            var frm = document.getElementById('sendForm');
            if (frm) {
                frm.elements['id'].value = id;
                frm.elements['data'].value = document.getElementById('blob' + id).value;
                frm.elements['class'].value = classType;
                frm.submit();
            }
        }
        function download(id, index) {
            var frm = document.getElementById('downloadForm');
            var fn = document.getElementById('fileName' + index);
            if (frm && fn) {
                frm.elements['id'].value = id;
                frm.elements["name"].value = fn.value;
                frm.submit();
            }
        }
        function updateEvent() {
             var frm = document.getElementById('updateForm');
             var v1 = document.getElementById('updateEventData.attempt');
             var v2 = document.getElementById('updateEventData.status');
             var v3 = document.getElementById('processOnDate');
             var v4 = document.getElementById('processOnTime');
             var v5 = document.getElementById('eventComment');
             if (frm && v1 && v2) {
                  frm.elements['attempt'].value = v1.value;
                  frm.elements['status'].value = v2.value;
                  frm.elements['processOnDate'].value = v3.value;
                  frm.elements['processOnTime'].value = v4.value;
                  frm.elements['comment'].value = v5.value;
                  frm.submit();
             }
        }
        function upload(propId, index) {
            var frm = document.getElementById('mainForm');
            if (frm) {
                frm.action = '<%=request.getContextPath()%>/adminportal/eventAdm.do?action=edit&func=uploadBlob';
                frm.elements["propId"].value = propId;
                frm.elements["index"].value = index;
                frm.submit();
            }
        }
        function updateEmail(index) {
            var frm = document.getElementById('mainForm');
            var frm2 = document.getElementById('updateEmailForm');
            if (frm && frm2) {
                var i = 1;
                var prefix = "updateEventData.eventEmailDataViewVector[" + index;
                frm2["id"].value = frm[prefix +"].eventEmailId"].value;
                frm2["to"].value = frm[prefix +"].toAddress"].value;
                frm2["cc"].value = frm[prefix +"].ccAddress"].value;
                frm2["subj"].value = frm[prefix +"].subject"].value;
                frm2["body"].value = frm[prefix +"].text"].value;
                frm2["from"].value = frm[prefix +"].fromAddress"].value;
                frm2.submit();
            }
        }
        function uploadEmailAttach(index) {
            var frm = document.getElementById('mainForm');
            if (frm) {
                frm.action = '<%=request.getContextPath()%>/adminportal/eventAdm.do?action=edit&func=uploadEmailAttach';
                var id = frm["updateEventData.eventEmailDataViewVector[" + index
                    + "].eventEmailId"].value;
                frm.elements["propId"].value = id;
                frm.elements["index"].value = index;
                frm.submit();
            }
            return false;
        }
        function deleteAttach(index,index2) {
            var frmMain = document.getElementById('mainForm');
            var frm = document.getElementById('deleteAttachForm');
            if (frmMain && frm &&
                 window.confirm("Are you sure to delete " + (index2 + 1) + " attachment?")) {
                var id = frmMain["updateEventData.eventEmailDataViewVector[" + index
                    + "].eventEmailId"].value;
                frm.elements['id'].value = id;
                frm.elements['index'].value = index2;
                frm.submit();
            }
            return false;
        }
        function downloadAttach(index,index2) {
            var frmMain = document.getElementById('mainForm');
            var frm = document.getElementById('downloadAttachForm');
            if (frm && frmMain) {
                var id = frmMain["updateEventData.eventEmailDataViewVector[" + index
                    + "].eventEmailId"].value;
                frm.elements['id'].value = id;
                frm.elements['index'].value = index2;
                frm.submit();
            }
            return false;
        }
        
        function hideProcessDateAndTime(holdStatus) {
		  var eventStatus = document.getElementById("updateEventData.status").value;
		  if(eventStatus == holdStatus) {
		    document.getElementById("processDate").style.display = 'block';
		    document.getElementById("processTime").style.display = 'block';
		  }else{
		  	document.getElementById("processDate").style.display = 'none';
		  	document.getElementById("processTime").style.display = 'none';
		  }
		}
		
		function showHide(divId){
		    var theDiv = document.getElementById(divId);
		    if(theDiv.style.display=="none"){
		        theDiv.style.display="";
		    }else{
		        theDiv.style.display="none";
		    }    
		}
		
    </script><%
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String prop;
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
    <title>Event Administrator - Edit Event</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="hideProcessDateAndTime('<%=Event.STATUS_HOLD%>')">
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
            <jsp:include flush='true' page="ui/admEventToolbar.jsp"/>
        </td>
    </tr>
</table>
<%
    if ("edit".equals(action) || "changeProccessLog".equals(action)) {
%>
<html:form styleId="mainForm" action="/adminportal/eventAdm.do?action=edit&func=editcommit" enctype="multipart/form-data" method="post">
<input type="hidden" name="eventid" value="<%=theForm.getUpdateEventData().getEventId()%>"/>
<input type="hidden" name="propId" value=""/>
<input type="hidden" name="index" value=""/>
<logic:present name="EVENT_ADM_CONFIG_FORM" property="updateEventData">
<table bgcolor="#cccccc" width="769">
<tr>
    <td>
        <table bgcolor="#cccccc" width="100%">
            <tr>
                <td align="right"><b>Event Id:</b></td>
                <td align="left">
                    <bean:write name="EVENT_ADM_CONFIG_FORM" property="updateEventData.eventId"/>
                </td>
                <td align="right"><b>Add Date:</b></td>
                <td align="left">
                    <bean:write name="EVENT_ADM_CONFIG_FORM" property="updateEventData.addDate"
                             format="MM/dd/yyyy HH:mm:ss"/>
                </td>
                <td align="right"><b>Attempt:</b></td>
                <td align="left">
                    <html:text name="EVENT_ADM_CONFIG_FORM" property="updateEventData.attempt" size="5"
                     styleId="updateEventData.attempt"/>
                </td>
                <td align="left" colspan="2">
                <div id="processDate" style="display: none">                
                <table>
                	<tr>
                	<td align="left"><nobr>Process Date: 
                	<td align="left">
		                <nobr>
		                    <html:text name="EVENT_ADM_CONFIG_FORM" styleId="processOnDate" property="processOnDate" maxlength="10"/>
		                    <a href="#"
		                       onClick="showCalendar('processOnDate', event);return false;"
		                       title="Choose Date"><img name="DATEFROM" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
		                                  align="absmiddle" style="position:relative"
		                                  onmouseover="window.status='Choose Date';return true"
		                                  onmouseout="window.status='';return true"></a>
		                </nobr>
	            	</td>
                	</tr>
                </table>
                </div>
                </td>
            </tr>
            <tr>
                <td align="right"><b>Type:</b></td>
                <td align="left"><bean:write name="EVENT_ADM_CONFIG_FORM" property="updateEventData.type"/></td>
                <td align="right"><b>Mod Date:</b></td>
                <td align="left">
                    <bean:write name="EVENT_ADM_CONFIG_FORM" property="updateEventData.modDate"
                              format="MM/dd/yyyy HH:mm:ss"/>
                </td>
                <td align="right"><b>Status:</b></td>
                <td align="left">
                <% String hideProcessDateAndTime = "hideProcessDateAndTime('" + Event.STATUS_HOLD + "')"; %>
                    <html:select name="EVENT_ADM_CONFIG_FORM" property="updateEventData.status"
                            styleId="updateEventData.status" onchange="<%=hideProcessDateAndTime%>">
                        <html:option value="<%=Event.STATUS_FAILED%>"><%=Event.STATUS_FAILED%></html:option>
                        <html:option value="<%=Event.STATUS_HOLD%>"><%=Event.STATUS_HOLD%></html:option>
                        <html:option value="<%=Event.STATUS_IGNORE%>"><%=Event.STATUS_IGNORE%></html:option>
                        <html:option value="<%=Event.STATUS_IN_PROGRESS%>"><%=Event.STATUS_IN_PROGRESS%></html:option>
                        <html:option value="<%=Event.STATUS_LIMITED%>"><%=Event.STATUS_LIMITED%></html:option>
                        <html:option value="<%=Event.STATUS_PROC_ERROR%>"><%=Event.STATUS_PROC_ERROR%></html:option>
                        <html:option value="<%=Event.STATUS_PROCESSED%>"><%=Event.STATUS_PROCESSED%></html:option>
                        <html:option value="<%=Event.STATUS_READY%>"><%=Event.STATUS_READY%></html:option>
                        <html:option value="<%=Event.STATUS_REJECTED%>"><%=Event.STATUS_REJECTED%></html:option>
                        <html:option value="<%=Event.STATUS_DELETED%>"><%=Event.STATUS_DELETED%></html:option>
                    </html:select>
                </td>
                <td align="left" colspan="2">
                <div id="processTime" style="display: none">                
                <table>
                	<tr>
                	<td align="left"><nobr>Process Time:</nobr><br>(hh:mm)</td>
	            	<td align="left">
	                <html:text name="EVENT_ADM_CONFIG_FORM" styleId="processOnTime" property="processOnTime" maxlength="10"/>
	            	</td>
                	</tr>
                </table>
                </div>
                </td>
            </tr>
            <tr><td colspan="7"><html:textarea name="EVENT_ADM_CONFIG_FORM" styleId="processOnTime" property="eventComment"
                 styleId="eventComment" style="width:100%;height:80px;"/></td>
                 <td valign="bottom"><input type="button" onclick="updateEvent();" value="Update Event"/></td>
            </tr>
        </table>
        <hr />
    </td>
</tr>
<logic:notEmpty name="EVENT_ADM_CONFIG_FORM" property="eventProcesses">
<tr><td>
<table border="1" width="100%">
<tr><th colspan="8">PROCESSES</th></tr>
<tr>
<th>#</th>
<th>Process ID</th>
<th>Process Template Id</th>
<th>Process Name</th>
<th>Process Type Cd</th>
<th>Process Status Cd</th>
<th>Add Date</th>
<th>Mod Date</th>
</tr>
<logic:iterate name="EVENT_ADM_CONFIG_FORM" property="eventProcesses" id="item"
    indexId="i" type="com.cleanwise.service.api.value.ProcessData">
<tr>
    <td><%= i.intValue() + 1%></td>
    <td><bean:write name="item" property="processId"/></td>
    <td><bean:write name="item" property="processTemplateId"/></td>
    <td><bean:write name="item" property="processName"/></td>
    <td><bean:write name="item" property="processTypeCd"/></td>
    <td><bean:write name="item" property="processStatusCd"/></td>
    <td><bean:write name="item" property="addDate" format="MM/dd/yyyy HH:mm:ss"/></td>
    <td><bean:write name="item" property="modDate" format="MM/dd/yyyy HH:mm:ss"/></td>
</tr>
</logic:iterate>
</table><hr/>
</td></tr>
</logic:notEmpty>
<tr>
    <td>
        <logic:present name="EVENT_ADM_CONFIG_FORM" property="updateEventData.stackTrace">
            <b>Stack Trace</b>
            <table bgcolor="azure" width="769" title="StackTrace">
                <title><b>Stack Trace</b></title>
                <tr><td><div class="text"><font color="red">
                    <bean:write name="EVENT_ADM_CONFIG_FORM" property="updateEventData.stackTrace"/>
                </font></div>
                </td></tr>
            </table>
        </logic:present>
        <table bgcolor="#cccccc" width="769">
            <logic:equal name="EVENT_ADM_CONFIG_FORM" property="updateEventData.type" value="<%=Event.TYPE_EMAIL%>">
            <logic:present name="EVENT_ADM_CONFIG_FORM" property="updateEventData.eventEmailDataViewVector">
            <logic:iterate id="propery" name="EVENT_ADM_CONFIG_FORM" property="updateEventData.eventEmailDataViewVector" indexId="i">
            <input type="hidden" name="iemail" value="<%=i%>">
            <tr>
                    <td>
<%prop = "updateEventData.eventEmailDataViewVector["+i+"].eventEmailId";%>
<html:hidden name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>" />
                        <table bgcolor="#cccccc" width="100%">
                            <tr>
                                <h1>Edit Email</h1>
                                    <table width="100%" cellspacing="0" cellpadding="4" style="font-size: 80%;" border="0">
                                        <colgroup>
                                            <col width="8%">
                                            <col width="17%">
                                            <col width="50%">
                                            <col width="8%">
                                            <col width="17%">
                                        </colgroup>
                                        <tbody>
                                            <tr class="spacer">
                                                <td width="8%" nowrap rowspan="15">
                                                    <spacer width="44" height="1" type="block"></spacer>
                                                </td>
                                                <td width="17%" nowrap>
                                                    <spacer width="88" height="1" type="block"></spacer>
                                                </td>
                                                <td width="50%" nowrap>
                                                    <spacer width="220" height="1" type="block"></spacer>
                                                </td>
                                                <td width="8%" nowrap rowspan="3">
                                                    <spacer width="44" height="1" type="block"></spacer>
                                                </td>
                                                <td width="17%" nowrap rowspan="8">
                                                    <spacer width="88" height="1" type="block"></spacer>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td colspan="3"></td>
                                            </tr>
                                            <tr>
                                                <td><b>From</b></td>
                                                <td colspan="2">
                                                    <div class="compose-input">
                                                        <%
                                                            prop = "updateEventData.eventEmailDataViewVector["+i+"].fromAddress";
                                                        %>
                                                        <html:text name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>" tabindex="<%=i + \"1\"%>"
                                                                  size="40" maxlength="255" style="width:100%;"/>
                                                    </div>
                                                </td>
                                                <td>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><b>To</b></td>
                                                <td colspan="2">
                                                    <div class="compose-input">
                                                        <%
                                                            prop = "updateEventData.eventEmailDataViewVector["+i+"].toAddress";
                                                        %>
                                                        <html:text name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>" tabindex="<%=i + \"1\"%>"
                                                                  size="40" maxlength="3000" style="width:100%;"/>
                                                    </div>
                                                </td>
                                                <td>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>CC</td>
                                                <td colspan="2">
                                                    <div class="compose-input">
                                                        <%
                                                            prop = "updateEventData.eventEmailDataViewVector["+i+"].ccAddress";
                                                        %>
                                                        <html:text name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>"
                                                        	 maxlength="255"
                                                             tabindex="<%=i + \"2\"%>" size="40" style="width:100%;"/>
                                                    </div>
                                                </td>
                                                <td>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><b>Subject</b></td>
                                                <td colspan="2">
                                                    <div class="compose-input">
                                                        <%
                                                            prop = "updateEventData.eventEmailDataViewVector["+i+"].subject";
                                                        %>
                                                        <html:text name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>" tabindex="<%=i + \"3\"%>"
                                                        	maxlength="255" style="width:100%;"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td rowspan="3"></td>
                                                <td colspan="2" style="padding-bottom:0"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" class="message">
                                                    <div class="message-content">
                                                        <%
                                                            prop = "updateEventData.eventEmailDataViewVector["+i+"].text";
                                                        %>
                                                        <html:textarea name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>" tabindex="<%=i + \"4\"%>" cols="78"
                                                                                           rows="14"/>
                                                    </div>
                                                </td>
                                                <td valign="top" style="padding-top: 0pt;" rowspan="4"></td>
                                            </tr>
                                            <tr>
                                               <td colspan="2" align="right"><input type="button" onclick="updateEmail(<%=i%>);" value="Update Email" /></td>
                                            </tr>
                                            <tr valign="top">
                                                <td colspan="2">&nbsp;&nbsp;&nbsp;<b>Attachment(s):</b><br/><%
try{
    prop = "updateEventData.eventEmailDataViewVector["+i+"].fileAttachments";
%>
<logic:present name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>">
<bean:define id="fileAttach" name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>"/>
<%
FileAttach[] emailAtt = (FileAttach[]) fileAttach;
if (emailAtt != null && emailAtt.length > 0) {%>
<br/>&nbsp;<b>Attached:</b>
<table width="100%" cellspacing="0" cellpadding="0" border="0"><%
    for(int iA=0; iA<emailAtt.length; iA++){
        String fileName = emailAtt[iA].getName();
%>
    <tr>
        <td><a href="#" onclick="return downloadAttach(<%=i%>,<%=iA%>);"><%=fileName%></a></td>
        <td align="right"><a href="#" onclick="return deleteAttach(<%=i%>,<%=iA%>);">delete</a></td>
    </tr><tr bgcolor="#ffffff"><td colspan="2"></td></tr><%
}
%>
</table>
<%}%>
</logic:present><%
} catch (Exception e) {
    e.printStackTrace();
}
prop = "newattachments";
%>
<logic:present name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>">
<br/>&nbsp;<b>New:</b>
<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr><%
prop = "newattachments["+ i +"]";
%>
                                                                <td><html:file name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>"  style="width:100%;"/></td>
                                                                <td align="left" width="1%"><nobr>
                                                                    <a href="#" onclick="return uploadEmailAttach(<%=i%>);">Add</a>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </logic:present>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                            </tr>
                        </table>
                    </td>
                </tr>
            </logic:iterate>
            </logic:present>
            </logic:equal>
            <logic:equal name="EVENT_ADM_CONFIG_FORM" property="updateEventData.type" value="<%=Event.TYPE_PROCESS%>">
            <logic:iterate id="propery" name="EVENT_ADM_CONFIG_FORM" property="updateEventData.eventPropertyDataVector" indexId="i"
                     type="com.cleanwise.service.api.value.EventPropertyData">
                <tr>
                    <td>
                        <table bgcolor="#cccccc" width="100%">
                            <tr>
                                <%prop = "updateEventData.eventPropertyDataVector["+i+"].eventPropertyId";%>
                                <td>Property&nbsp;ID:&nbsp;<b><bean:write name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>"/></b></td>
                                <%prop = "updateEventData.eventPropertyDataVector["+i+"].type";%>
                                <td>Type:&nbsp;<b><bean:write name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>"/></b></td>
                                <%prop = "updateEventData.eventPropertyDataVector["+i+"].num";%>
                                <td width="70%">Num:&nbsp;<b><bean:write name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>"/></b></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
<%
    EventPropertyData item = propery;
    boolean isBlob = isBlob(item);
    boolean isDownload = isBlob ? isDownload(item) : false;
    String className = item.getVarClass();
    String styleWidth = isBlob ? "height:400px;overflow:scroll;" : "height:30px";
%>
                        <table bgcolor="#cccccc" width="100%" border="1">
                            <tr>
                                <td width="30%">
                                    <%prop = "updateEventData.eventPropertyDataVector["+i+"].shortDesc";%>
                                    <b><bean:write name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>"/></b>
                                    <%prop = "updateEventData.eventPropertyDataVector["+i+"].varClass";%>
                                    &nbsp;(<bean:write name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>"/>)&nbsp;
                                </td>
<%
if (isBlob == false && isDownload == false) {
    String blobString = getValue(theForm, i.intValue());
    String textAreaSize = "height:" + ((blobString.length() / 90) + 1) * 25 + "px; overflow:scroll";
%>
                                <td>
                                    <input type="button" id="button<%=i%>" value="Edit..." onclick="edit(<%=i%>);"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div id="preview<%=i%>">
                                        <%if ("java.lang.String".equals(className) && blobString.length() > 90) {%>
                                            <textarea id="field<%=item.getEventPropertyId()%>" style="width:100%;<%=textAreaSize%>"; readonly="readonly"><%=blobString%></textarea>
                                        <%}else{%>
                                            <b><%=blobString%></b>
                                        <%}%>
                                    </div>
                                    <div id="edit<%=i%>" style="display:none;">
                                        <%if ("java.lang.String".equals(className) && blobString.length() > 90) {%>
                                            <textarea id="field<%=item.getEventPropertyId()%>" style="width:100%;<%=textAreaSize%>";><%=blobString%></textarea><br>
                                        <%}else{%>
                                            <textarea id="blob<%=item.getEventPropertyId()%>" style="width:100%;<%=styleWidth%>;"><%=blobString%></textarea><br>
                                        <%}%>
                                        <input type="button" value="Update" onclick="update(<%=item.getEventPropertyId()%>, '<%=className%>');">
                                    </div>
                                </td>
                            </tr>
<%} else { %>
                                <td>
                                    <%prop = "updateEventData.eventPropertyDataVector["+i+"].shortDesc";%>
                                    <input type="text" id="fileName<%=i%>" style="width:50%;" value="<bean:write name="EVENT_ADM_CONFIG_FORM" property="<%=prop%>"/><%=theForm.
                                    getUpdateEventData().getEventId()%>.<%if (ObjectUtil.BYTE_ARRAY_CLASS.getName().equals(item.getVarClass()) || ObjectUtil.INBOUND_CONTENT.getName().equals(item.getVarClass())) {%>bin<%}else{%>xml<%}%>"/>
                                    <input type="button" value="Download" onclick="download(<%=item.getEventPropertyId()%>,<%=i%>);">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <table cellpadding="0" cellspacing="0" border="0">
                                        <tr>
                                        <td>
                                            <html:file name="EVENT_ADM_CONFIG_FORM" property="<%=\"uploadBlobs[\" + i + \"]\"%>" value="Choose file..." style="width:100%;"/>
                                        </td>
                                        <td width="1%">
                                            <input type="button" value="Upload" onclick="upload(<%=item.getEventPropertyId()%>, <%=i%>);"/>
                                        </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
<%}%>
                        </table>
                    </td>
                </tr>
            </logic:iterate>
            </logic:equal>
<logic:notEmpty name="EVENT_ADM_CONFIG_FORM" property="eventLogs">
<link rel="stylesheet" href="../externals/dojo_1.1.0/clw/css/TabContainer.css">
<script language="JavaScript1.2">
    if (typeof dojo == "undefined") {
        document.write("<script type=\"text/javascript\" src=\"<%=request.getContextPath()%>/externals/dojo_1.1.0/dojo/dojo.js\"><" + "/script>")
        }
    </script>
                     <tr><td>
        <hr/>
        <table border="1" width="100%">
            <tr><th colspan="4">LOGS</th></tr>

            <tr>
                <td colspan="4">
                    <table border="0" width="100%">
                        <tr>
                            <td valign="top">



            <table style="WIDTH:100%" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table cellspacing="0" cellpadding="0" WIDTH="100%">

                            <tr>
                                <td>


                                    <% HashMap<Integer, LoggingDataVector> processLogs = (HashMap<Integer, LoggingDataVector>) theForm.getEventProcessLogs();%>
                                    <%
                                        Integer activeLog =  new Integer(theForm.getActiveLog());

                                        IdVector processIds = Utility.getProcessIdOnly(theForm.getEventLogs());


                                        for (Object oProcessId : processIds) {
                                            Integer processId = (Integer)oProcessId;
                                    %>
                                    <%if(processId.intValue() != activeLog.intValue() && processId > 0){ %>


                                    <DIV class="dijitTab" id="processTab_<%=processId%>">
                                        <DIV class=dijitTabInnerDiv onclick="goToNextLog('<%=processId%>')">
                                            <DIV class=dijitTabContent>
                                                <SPAN class=tabLabel>ProcID.<%=processId%></SPAN>
                                            </DIV>
                                        </DIV>
                                    </DIV>



                                    <%} else if (processId > 0){%>

                                    <DIV class="dijitTab dijitTabChecked dijitChecked" id="processTab_<%=processId%>">
                                        <DIV class=dijitTabInnerDiv  onclick="goToNextLog('<%=processId%>')">
                                            <DIV class=dijitTabContent>
                                                <SPAN class=tabLabel>ProcID.<%=processId%></SPAN>
                                            </DIV>
                                        </DIV>
                                    </DIV>

                                    <%}%>

                                    <%}%>

                                <%if(processIds==null || processIds.size()==0 ||
                                     ( processIds.size()==1 && ((Integer)processIds.get(0)).intValue() == 0)){ %>
                                    <DIV class="dijitTab dijitTabChecked dijitChecked" id="processTab_0">
                                <%} else {%>
                                    <DIV class="dijitTab" id="processTab_0">
                                <%}%>
                                        <DIV class=dijitTabInnerDiv onclick="goToNextLog('0')">
                                            <DIV class=dijitTabContent>
                                                <SPAN class=tabLabel>ALL</SPAN>
                                            </DIV>
                                        </DIV>
                                    </DIV>

                                </td>
                            </tr>
                        </table>

                    </td>
                </tr>
                <tr>
                    <td class="dijitTabPaneWrapper" width="100%">
                        <DIV class="dijitContentPane dijitTabPane" title="" >
                            <table  id="tabContent">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Proc.&nbsp;ID</th>
                                        <th align="left">Message / Throwable</th>
                                        <th><nobr>Add Date</nobr></th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <%
                                        LoggingDataVector processLog = processLogs.get(activeLog);
                                      if (processLog != null) {
                                          for (int i = 0; i < processLog.size(); i++) {

                                                LoggingData logData = (LoggingData) processLog.get(i); %>

                                    <tr>
                                        <td class="verticalAlign"><%=(i + 1)%>
                                        </td>
                                        <td class="verticalAlign"><%=logData.getProcessId()%>
                                        </td>

                                        <%
                                            String stack = logData.getThrowable();
                                            boolean useStack = (stack != null && stack.trim().length() > 0);
                                        %>

                                        <td valign="top" class="logMessage verticalAlign">
                                            <%
                                                if (Utility.isSet(logData.getMessage())) {%>
                                            <b><%=logData.getMessage()%>
                                            </b>
                                            <% }
                                                if (useStack) {
                                                    out.write(stack.trim());
                                                }%>
                                        </td>
                                        <td class="verticalAlign">
                                            <%=sdf.format(logData.getAddDate())%>
                                        </td>
                                    </tr>

                                    <% }
                                    } %>

                                </tbody>
                            </table>
                        </DIV>
                    </td>
                </tr>
            </table>
            </td>
            </tr>
            </table>
            </td>

            </tr>
        </table>
    </td></tr>
</logic:notEmpty>
        </table>
    </td>
</tr>
</table>
</logic:present>
</html:form>
<html:form styleId="sendForm" action="/adminportal/eventAdm.do?action=edit&func=editblob" method="post">
<input type="hidden" name="eventid" value="<%=theForm.getUpdateEventData().getEventId()%>"/>
<input type="hidden" name="id" value=""/>
<input type="hidden" name="data" value=""/>
<input type="hidden" name="class" value=""/>
</html:form>
<html:form styleId="downloadForm" action="/adminportal/eventAdm.do?action=edit&func=download" method="post" target="_self">
<input type="hidden" name="eventid" value="<%=theForm.getUpdateEventData().getEventId()%>"/>
<input type="hidden" name="name" value=""/>
<input type="hidden" name="id" value=""/>
</html:form>
<html:form styleId="updateForm" action="/adminportal/eventAdm.do?action=edit&func=editevent" method="post">
<input type="hidden" name="eventid" value="<bean:write name="EVENT_ADM_CONFIG_FORM" property="updateEventData.eventId"/>"/>
<input type="hidden" name="attempt" value=""/>
<input type="hidden" name="status" value=""/>
<input type="hidden" name="processOnDate" value=""/>
<input type="hidden" name="processOnTime" value=""/>
<input type="hidden" name="comment" value=""/>
</html:form>
<html:form styleId="updateEmailForm" action="/adminportal/eventAdm.do?action=edit&func=editemail" method="post">
<input type="hidden" name="eventid" value="<bean:write name="EVENT_ADM_CONFIG_FORM" property="updateEventData.eventId"/>"/>
<input type="hidden" name="id" value="" />
<input type="hidden" name="to" value="" />
<input type="hidden" name="cc" value="" />
<input type="hidden" name="subj" value="" />
<input type="hidden" name="body" value="" />
<input type="hidden" name="from" value="" />
</html:form>
<html:form styleId="deleteAttachForm" action="/adminportal/eventAdm.do?action=edit&func=deleteEmailAttach" method="post" target="_self">
<input type="hidden" name="eventid" value="<%=theForm.getUpdateEventData().getEventId()%>"/>
<input type="hidden" name="id" value=""/>
<input type="hidden" name="index" value=""/>
</html:form>
<html:form styleId="downloadAttachForm" action="/adminportal/eventAdm.do?action=edit&func=downloadEmailAttach" method="post" target="_self">
<input type="hidden" name="eventid" value="<%=theForm.getUpdateEventData().getEventId()%>"/>
<input type="hidden" name="id" value=""/>
<input type="hidden" name="index" value=""/>
</html:form>
<%
    }
%>
<div class="admres">
    Event(s) total count in system:<bean:write name="EVENT_ADM_CONFIG_FORM" property="eventCount"/>
</div>
</body>
<iframe name="downloadFrame" height="0" width="0"></iframe>
</html:html>
<%@page import="com.cleanwise.service.api.value.*,
                com.cleanwise.service.api.process.variables.ExtProcessVariable,
                com.cleanwise.view.forms.*,
                com.cleanwise.view.utils.*,
                java.beans.*,
                java.io.*,
                java.util.*"%>
<%!
    private final static Set types = new HashSet();

    static {
        types.add("java.lang.Integer");
        types.add("java.lang.String");
        types.add("java.util.Date");
    }

    private final static boolean isDownload(EventPropertyData item) {
        try {
            if (item.getBlobValue().length > EventAdmMgrForm.MAX_PREVIEW_BLOB_LENGTH) {
                return true;
            }
            Object obj = ObjectUtil.bytesToObject(item.getBlobValue());
            if (obj instanceof ExtProcessVariable) {
                obj = ((ExtProcessVariable) obj).getValue();
            }
            return obj instanceof byte[];
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private final static boolean isBlob(EventPropertyData item) {
        String className = item.getVarClass();
        if (className != null && types.contains(className)) {
            return false;
        }
        return true;
    }

    private final static String getValue(EventAdmMgrForm form, int index) {
        EventPropertyData item = (EventPropertyData) form.getUpdateEventData().
                getEventPropertyDataVector().get(index);
        String className = item.getVarClass();
        StringBuffer sb = new StringBuffer();
        if ("java.lang.Integer".equals(className)) {
            sb.append(item.getNumberVal());
        } else if ("java.lang.String".equals(className)) {
            sb.append(item.getStringVal());
        } else if ("java.util.Date".equals(className)) {
            if (item.getDateVal() != null) {
                sb.append(com.cleanwise.service.api.util.Utility.getFormatTime(item.getDateVal().getTime(), 1));
            }
        } else {
            try {
                byte[] data = item.getBlobValue();
                Object obj = ObjectUtil.bytesToObject(data);
                if (obj instanceof ExtProcessVariable) {
                    Object objVal = ((ExtProcessVariable) obj).getValue();
                    sb.append(getValue(objVal.getClass().getName(), objVal));
                } else if (!"[B".equals(className)) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    XMLEncoder decode = new XMLEncoder(baos);
                    decode.setPersistenceDelegate(java.math.BigDecimal.class,
                            decode.getPersistenceDelegate(Integer.class));
                    decode.writeObject(obj);
                    decode.flush();
                    decode.close();
                    String xxx = new String(baos.toByteArray(), "utf-8");
                    sb.append(xxx.replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
                } else {
                    java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream(data);
                    java.io.ObjectInputStream is = new java.io.ObjectInputStream(iStream);
                    data = (byte[]) is.readObject();
                    is.close();
                    iStream.close();
                    sb.append(new String(data));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private static String getValue(String className, Object obj) {

        StringBuffer sb = new StringBuffer();

        if ("java.lang.Integer".equals(className)) {
            sb.append(obj);
        } else if ("java.lang.String".equals(className)) {
            sb.append(obj);
        } else if ("java.util.Date".equals(className)) {
            if (obj != null) {
                sb.append(com.cleanwise.service.api.util.Utility.getFormatTime((Long) obj, 1));
            }
        } else {
            try {
                if (obj instanceof ExtProcessVariable) {
                    Object objVal = ((ExtProcessVariable) obj).getValue();
                    sb.append(getValue(objVal.getClass().getName(), objVal));
                } else if (!"[B".equals(className)) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    XMLEncoder decode = new XMLEncoder(baos);
                    decode.setPersistenceDelegate(java.math.BigDecimal.class, decode.getPersistenceDelegate(Integer.class));
                    decode.writeObject(obj);
                    decode.flush();
                    decode.close();
                    String xxx = new String(baos.toByteArray(), "utf-8");
                    sb.append(xxx.replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
                } else {
                    java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream((byte[]) obj);
                    java.io.ObjectInputStream is = new java.io.ObjectInputStream(iStream);
                    byte[] data = (byte[]) is.readObject();
                    is.close();
                    iStream.close();
                    sb.append(new String(data));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
%>
