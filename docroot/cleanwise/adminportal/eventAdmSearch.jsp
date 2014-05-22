<%@ page language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.session.Event" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>
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

    </style>
    <title>Event Administrator</title>

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
</head>
<body>
<%@include file="/externals/calendar/calendar.jsp"%>
<script language="JavaScript">
function refreshControls() {
    var objSearchParamType = document.getElementById("searchParamType");
    if (objSearchParamType) {
        var item = objSearchParamType[objSearchParamType.selectedIndex];
        var filterArea = document.getElementById("searchDisplay");
        var filterType = document.getElementById(item.javaType);
        if (filterArea) {
            filterArea.innerHTML = (filterType) ? filterType.innerHTML : "";
        }
    }
}

function refreshProps() {
    var objSearchEventType = document.getElementById("searchEventType");
    var item = objSearchEventType[objSearchEventType.selectedIndex];
    var id = item.value;
    var objSearchParamType = document.getElementById("searchParamType");
    if (objSearchParamType) {
        objSearchParamType.selectedIndex  = 0;
        objSearchParamType.options.length = 1;
        refreshControls();
        if (id && eventProps[id]) {
            for (var ik = 0; eventProps[id] && ik < eventProps[id].length; ik++) {
                objSearchParamType.options[ik + 1] = eventProps[id][ik];
                if (searchParamType == objSearchParamType.options[ik + 1].value) {
                    objSearchParamType.selectedIndex = ik + 1;
                    refreshControls();
                }
            }
        }
    }
}

function f_setCheckClearArray(pForm, arrayName, count, onOff) {
    for (var i = 0; i < count; i++) {
        var element = document.getElementById(pForm)[(arrayName + "[" + i + "]")];
        if (element != null && 'undefined' != typeof element) {
            element.checked = onOff;
        }
    }
    return false;
}

var searchParamType = '<%=theForm.getSearchParamType()%>';
var eventProps = new Object();
eventProps["<%=Event.TYPE_EMAIL%>"] = new Array();
eventProps["<%=Event.TYPE_EMAIL%>"][0] = new Option("Email To","EMAIL_TO");
eventProps["<%=Event.TYPE_EMAIL%>"][0].javaType = "java.lang.String";
eventProps["<%=Event.TYPE_EMAIL%>"][1] = new Option("Email From","EMAIL_FROM");
eventProps["<%=Event.TYPE_EMAIL%>"][1].javaType = "java.lang.String";
eventProps["<%=Event.TYPE_EMAIL%>"][2] = new Option("Email Subject","EMAIL_SUBJECT");
eventProps["<%=Event.TYPE_EMAIL%>"][2].javaType = "java.lang.String";
eventProps["<%=Event.TYPE_EMAIL%>"][3] = new Option("Email Text","EMAIL_TEXT");
eventProps["<%=Event.TYPE_EMAIL%>"][3].javaType = "java.lang.String";
<%
Iterator it = theForm.getTaskProperties().entrySet().iterator();
while (it.hasNext()) {
    Map.Entry entry = (Map.Entry) it.next();
    Integer key = (Integer) entry.getKey();
    List val = (List) entry.getValue();
    if (val != null && val.size() > 0) {
%>
eventProps["<%=key%>"] = new Array();
<%
Set alreadyUsed = new HashSet();
Set visibleTypes = new HashSet();
visibleTypes.add("java.lang.String");
visibleTypes.add("java.lang.Integer");
visibleTypes.add("java.util.Date");
int ik = 0;
for (int i = 0; i < val.size(); i++) {
    TaskPropertyData item = (TaskPropertyData) val.get(i);
    String key2 = key + "_" + item.getVarName();
    boolean isCorrectType = visibleTypes.contains(item.getVarType());
    if (alreadyUsed.contains(key2) || isCorrectType == false) {
        continue;
    }
    alreadyUsed.add(key2);%>
eventProps["<%=key%>"][<%=ik%>] = new Option("<%=item.getVarName()%>", "<%=item.getTaskPropertyId()%>");
eventProps["<%=key%>"][<%=ik%>].javaType = "<%=item.getVarType()%>";<%
ik++;
}
%><%
    }
}
%>
</script>
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
    if ("search".equals(action)) {
%>
<html:form action="/adminportal/eventAdm.do?action=search&eventSearchAllowReset=true"
           styleId="EVENT_ADM_CONFIG_FORM_ID"
           enctype="multipart/form-data">
<input type="hidden" name="type" value="search"/>
<table bgcolor="#cccccc" width="769" cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td align="left" width="1%">Event Id:</td>
            <td align="left" width="30%">
                <html:text name="EVENT_ADM_CONFIG_FORM" property="searchEventNumber"/>
            </td>
            <td align="left" width="10%">Active:</td>
            <td align="left">
                <html:select name="EVENT_ADM_CONFIG_FORM" property="searchActive">
                    <html:option value=""></html:option>
                    <html:option value="1">ACTIVE</html:option>
                    <html:option value="0">NOT ACTIVE</html:option>
                </html:select>
            </td>
            <%
                Integer statusCount = null;
                if (theForm.getSearchEventStatuses() != null) {
                    if (theForm.getSearchEventStatuses().getValues() != null) {
                        statusCount = theForm.getSearchEventStatuses().getValues().size();
                    }
                }
            %>
            <td rowspan="6" style="{vertical-align:top; text-align:top;}">
                <table cellspacing="0" cellpadding="0" border="0">
                    <tr>
                        <td align="left">Status:</td>
                        <td>
                            <a href="#" onclick="f_setCheckClearArray('EVENT_ADM_CONFIG_FORM_ID', 'searchEventStatuses.selected', <%=statusCount%>, true);">
                                <nobr>[Select All]</nobr><br>
                            </a>
                            <a href="#" onclick="f_setCheckClearArray('EVENT_ADM_CONFIG_FORM_ID', 'searchEventStatuses.selected', <%=statusCount%>, false);">
                                <nobr>[Clear All]</nobr>
                            </a>
                        </td>
                    </tr>
                    <logic:iterate id="arrele"
                           name = "EVENT_ADM_CONFIG_FORM"
                           property="searchEventStatuses.values"
                           indexId="i"
                           type="java.lang.String">
                        <tr>
                            <td><%=arrele%></td>
                            <%String selectedStr = "searchEventStatuses.selected[" + i + "]"; %>
                            <td align="center">
                                <html:multibox name="EVENT_ADM_CONFIG_FORM" property="<%=selectedStr%>" value="true"/>
                            </td>
                        </tr>
                    </logic:iterate>
                </table>
            </td>
        </tr>
        <tr>
            <td align="left">Type:</td>
            <td align="left" colspan="3">
                <html:select name="EVENT_ADM_CONFIG_FORM" property="searchEventType" styleId="searchEventType" onchange="refreshProps();">
                    <html:option value=""></html:option>
                    <%  ProcessDataVector processVector = theForm.getTemplateProcessesAlphabetized();
                        if (Utility.isSet(processVector)) {
                            boolean emailInserted = false;
                            for (int i = 0; i < processVector.size(); ) {
                                ProcessData processD = (ProcessData) processVector.get(i);
                                if (!emailInserted && Event.TYPE_EMAIL.compareToIgnoreCase(processD.getProcessName()) < 0) { %>
                                    <html:option value="<%=Event.TYPE_EMAIL%>"><%=Event.TYPE_EMAIL%></html:option>
                                <%
                                    emailInserted = true;
                                } else { %>
                                    <html:option value="<%=\"\" + processD.getProcessId()%>"><%=processD.getProcessName()%></html:option>
                        <%          i++;
                                }
                            }
                    %>
                    
                    <% } else { %>
                        <html:option value="<%=Event.TYPE_EMAIL%>"><%=Event.TYPE_EMAIL%></html:option>
                    <% } %>
                </html:select>
            </td>
        </tr>
        <tr>
            <td align="left"><nobr>From Date:</nobr></td>
            <td align="left">
                <nobr>
                    <html:text name="EVENT_ADM_CONFIG_FORM" styleId="searchDateFrom" property="searchDateFrom" maxlength="10"/>
                    <a href="#"
                       onClick="showCalendar('searchDateFrom', event);return false;"
                       title="Choose Date"><img name="DATEFROM" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                                  align="absmiddle" style="position:relative"
                                  onmouseover="window.status='Choose Date';return true"
                                  onmouseout="window.status='';return true"></a>
                </nobr>
            </td>
            <td align="left"><nobr>To Date:</nobr></td>
            <td align="left">
                <nobr>
                    <html:text name="EVENT_ADM_CONFIG_FORM" styleId="searchDateTo" property="searchDateTo" maxlength="10"/>
                    <a href="#"
                       onClick="showCalendar('searchDateTo', event);return false;"
                       title="Choose Date"><img name="DATEFROM" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                                  align="absmiddle" style="position:relative"
                                  onmouseover="window.status='Choose Date';return true"
                                  onmouseout="window.status='';return true"></a>
                </nobr>
            </td>
        </tr>
        <tr>
            <td align="left"><nobr>From Time:</nobr><br>(hh:mm)</td>
            <td align="left">
                <html:text name="EVENT_ADM_CONFIG_FORM" styleId="searchTimeFrom" property="searchTimeFrom" maxlength="10"/>
            </td>
            <td align="left"><nobr>To Time:</nobr><br>(hh:mm)</td>
            <td align="left">
                <html:text name="EVENT_ADM_CONFIG_FORM" styleId="searchTimeTo" property="searchTimeTo" maxlength="10"/>
            </td>
        </tr>
        <tr>
            <td align="left"><nobr>Attribute:</nobr></td>
            <td>
                <html:select name="EVENT_ADM_CONFIG_FORM"
                             property="searchParamType"
                             styleId="searchParamType"
                             onchange="refreshControls();"
                             style="width:175px;">
                    <html:option value=""></html:option>
                </html:select>
            </td>
            <td colspan="2" id="searchDisplay">&nbsp;</td>
        </tr>
        <%--
		<tr>
			<td align="left"><nobr>Contains Text:</td>
			<td><html:text name="EVENT_ADM_CONFIG_FORM" property="searchContainsText" styleId="searchParamType" style="width:175px;"/></td>
		</tr>
        <tr>
            <td align="left"><nobr>Full Text Search:</td>
            <td align="left"colspan="2">
                <html:text name="EVENT_ADM_CONFIG_FORM" property="searchFullText" style="width:175px;"/>
            </td>
        </tr>
        --%>
        <tr>
            <td align="left"><nobr>Blob Text Search:</nobr><br>(Slow search)</td>
            <td align="left">
                <html:text name="EVENT_ADM_CONFIG_FORM" property="searchBlobText" style="width:175px;" />
                <html:hidden name="EVENT_ADM_CONFIG_FORM" property="searchBlobTextMaxRows" value="4000" />
            </td>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><input type="submit" name="action" value="Search"></td>
            <td colspan="4">&nbsp;</td>
        </tr>
</table>
</html:form>
<html:form action="/adminportal/eventAdm.do?action=search">
<div style="display:none;">
<div id="java.lang.String">
<html:hidden property="attrType" value="string"/>
<table cellpadding="2" cellspacing="0" border="0">
    <tr>
        <td width="1%">
            <div>
                <nobr>
                    <html:radio name="EVENT_ADM_CONFIG_FORM" property="attrFilterString" value="<%=\"\"+SearchCriteria.BEGINS_WITH_IGNORE_CASE%>" />
                    Starts with
                </nobr>
            </div>
            <div>
                <nobr>
                    <html:radio name="EVENT_ADM_CONFIG_FORM" property="attrFilterString" value="<%=\"\"+SearchCriteria.CONTAINS_IGNORE_CASE%>" />
                    Contains
                </nobr>
            </div>
        </td>
        <td><html:text name="EVENT_ADM_CONFIG_FORM" property="attrFilterStringValue1" size="20" maxlength="100"/></td>
    </tr>
</table>
</div>
<div id="java.lang.Integer">
<html:hidden property="attrType" value="integer"/>
<html:select name="EVENT_ADM_CONFIG_FORM" property="attrFilterInteger">
<html:option value=">=">&gt;=</html:option>
<html:option value=">">&gt;</html:option>
<html:option value="=">=</html:option>
<html:option value="<">&lt;</html:option>
<html:option value="<=">&lt;=</html:option>
</html:select>
<html:text name="EVENT_ADM_CONFIG_FORM" property="attrFilterIntegerValue1" size="20" maxlength="100"/>
</div>
<div id="java.util.Date">
<html:hidden property="attrType" value="date"/>
<nobr>
From:&nbsp;<html:text name="EVENT_ADM_CONFIG_FORM" property="attrFilterDateValue1" size="20" maxlength="100"/>
&nbsp;To:&nbsp;<html:text name="EVENT_ADM_CONFIG_FORM" property="attrFilterDateValue2" size="20" maxlength="100"/>
</nobr>
</div>
</div>
</html:form>
<%
boolean useAdditional = theForm.getSearchEvents() != null && theForm.getSearchEvents().isUseAdditional();
java.util.Properties sysProps = System.getProperties();
boolean eventAllowProcess = sysProps.getProperty("event.allow.process.now", "false").equalsIgnoreCase("true");
SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
%>
Count: <bean:write name="EVENT_ADM_CONFIG_FORM" property="searchCount"/>
<table  class="stpTable_sortable" id="ts1" bgcolor="#cccccc" width="769" border="0">
<thead>
    <tr class="stpTH">
        <th class=stpTH>Event ID</th>
        <th class=stpTH>Type</th>
        <th class=stpTH>Status</th>
        <th class=stpTH>Add Date</th>
        <th class=stpTH>Mod Date</th>
        <%if (useAdditional) { %><th class=stpTH>Attribute</th><%} %>
        <% if (eventAllowProcess) { %>
        <th class=stpTH></th>
        <% } %>
    </tr>
</thead>
<tbody>
<logic:notEmpty name="EVENT_ADM_CONFIG_FORM" property="searchEvents">
    <logic:iterate id="event" name="EVENT_ADM_CONFIG_FORM" property="searchEvents" indexId="i" type="com.cleanwise.service.api.eventsys.EventData">
        <tr class="stpTD">
            <td class="stpTD"><a href="eventAdm.do?action=edit&func=editinit&eventid=<bean:write name="event" property="eventId"/>"><bean:write name="event" property="eventId"/></a></td>
            <td class="stpTD">
                <% if (Utility.isSet(event.getProcessName())) { %>
                    <bean:write name="event" property="processName"/>
                <% } else { %> 
                    <bean:write name="event" property="type"/>
                <% } %>
            </td>
            <td class="stpTD">
                <% if (Utility.isSet(event.getStatus())) { %>
                    <bean:write name="event" property="status"/>
                    <% if (RefCodeNames.EVENT_STATUS_CD.STATUS_HOLD.equals(event.getStatus())) { 
                    	String dateS = (event.getProcessTime()==null)?"":sdf.format(event.getProcessTime());%>                    	
	                    <br>
	                    <%=dateS %>
	                <% } %>                    
                <% } else { %>
                    &nbsp; 
                <% } %>
            </td>
            <td class="stpTD"><% String dateS = (event.getAddDate()==null)?"":sdf1.format(event.getAddDate());%><%=dateS %></td>
            <td class="stpTD"><% dateS = (event.getModDate()==null)?"":sdf1.format(event.getModDate());%><%=dateS %></td>
            <% if (useAdditional) { %>
                <td class="stpTD">
                    <% if (Utility.isSet(event.getAdditinalProperty())) { %>
                        <bean:write name="event" property="additinalProperty"/>
                    <% } else { %>
                        &nbsp; 
                    <% } %>
                </td>
            <% } %>            
            <% if (eventAllowProcess) { %>
                <% 
                    boolean showProcessNow = false;
                	boolean showSetReady = false;
                    String currentEventStatus = event.getStatus();
                    if (RefCodeNames.EVENT_STATUS_CD.STATUS_LIMITED.equals(currentEventStatus) ||
                        RefCodeNames.EVENT_STATUS_CD.STATUS_READY.equals(currentEventStatus) ||
                        RefCodeNames.EVENT_STATUS_CD.STATUS_REJECTED.equals(currentEventStatus)) {
                            showProcessNow = true;
                    }else if (RefCodeNames.EVENT_STATUS_CD.STATUS_HOLD.equals(currentEventStatus)) {
                    	showSetReady = true;
                    }
                %>

                <td class="stpTD">
                    <%if (showProcessNow) {%>
                        <a href="eventAdm.do?action=search&func=process_event&eventid=<bean:write name="event" property="eventId"/>">PROCESS&nbsp;NOW</a>
                    <% } else if (showSetReady) {%>
                        <a href="eventAdm.do?action=search&func=set_ready&eventid=<bean:write name="event" property="eventId"/>">SET&nbsp;READY</a>
                    <% } else { %>
                        &nbsp;
                    <% } %>
                </td>
            <% } %>
        </tr>
    </logic:iterate>
</tbody>
</logic:notEmpty>
</table>
<div class="admres">
    Event(s) total count in system:<bean:write name="EVENT_ADM_CONFIG_FORM" property="eventCount"/>
</div>
<%
    }
%>
<script>
refreshProps();
</script>
</body>
</html:html>
