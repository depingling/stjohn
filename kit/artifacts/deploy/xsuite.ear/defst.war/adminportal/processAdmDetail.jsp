<%! private String href;
    private String prop;
%>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.TaskPropertyData" %>
<%@ page import="com.cleanwise.service.api.value.TaskData" %>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
    <!--
    function actionSubmit(val) {
        actions=document.forms['PROCESS_ADM_CONFIG_FORM']['hiddenAction'];
        actions.value=val;
        document.forms['PROCESS_ADM_CONFIG_FORM'].submit();
        return false;
    }
    function actionSubmitParam(val,paramName,paramVal) {
        if(val!=null && val=="addCustomLink")  {
            alert("action is under constructing");
            return false;
        }
        actions=document.forms['PROCESS_ADM_CONFIG_FORM']['hiddenAction'];
        params=document.forms['PROCESS_ADM_CONFIG_FORM'][paramName];
        actions.value=val;
        params.value=paramVal;
        document.forms['PROCESS_ADM_CONFIG_FORM'].submit();
        return false;
    }
    function show_workflow_rule(id) {

        if(eval("document.getElementById(id)").style.display=='none'){
            eval("document.getElementById(id)").style.display='block';
            eval("document.getElementById('viewdetail'+id)").innerText="[-]";
            eval("document.getElementById(id+'Style')").value="display:block";
        }

        else {
            eval("document.getElementById(id)").style.display='none';
            eval("document.getElementById('viewdetail'+id)").innerText="[+]";
            eval("document.getElementById(id+'Style')").value="display:none";
        }

    }
    function newXMLHttpRequest() {
        var xmlreq = false;
        if (window.XMLHttpRequest) {
            xmlreq = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            try {
                xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e1) {
                try {
                    xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e2) { }
            }
        }
        return xmlreq;
    }
    function addUp() {
        var req = newXMLHttpRequest();
        var handlerFunction = getReadyStateHandler(req, responseXmlBuilder);
        alert("handlerFunction"+handlerFunction);
        req.onreadystatechange = handlerFunction;
        req.open("POST", "processAdmDetail.do", true);
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send("action=addUp");
    }
    function getReadyStateHandler(req, responseXmlHandler) {
        if (req.readyState == 4) {
            if (req.status == 200) {
                responseXmlHandler(req.responseXML);
            } else {
                alert("HTTP error: "+req.status);
            }
        }
    }
    function responseXmlBuilder(objectXML) {
        var cart = objectXML.getElementsByTagName("jbject")[0];
        var generated = cart.getAttribute("generated");
        if (generated > lastUpdate) {
            lastUpdate = generated;
            var contents = document.getElementById("object-contents");
            contents.innerHTML = "";

        }
    }

    //-->
</script>


<html:html>

<head>
    <link rel="stylesheet" href="../externals/styles.css">
    <link rel="stylesheet" href="../externals/pubstyles.css">
    <title>Process Administrator</title>
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
            <jsp:include flush='true' page="ui/admProcessToolbar.jsp"/>
        </td>
    </tr>
</table>

<html:form styleId="PROCESS_ADM_CONFIG_FORM" name="PROCESS_ADM_CONFIG_FORM" action="/adminportal/processAdm.do">
<table width="<%=Constants.TABLEWIDTH%>" height="149" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
<tr>
    <td>

        <b>Process Name:</b> <html:select name="PROCESS_ADM_CONFIG_FORM"
                                          property="currentTemplateProcessId" onchange="actionSubmit('changeCurrentTemplateProcess')">
        <html:option value="">--Select--</html:option>
        <logic:iterate id="tProcesses" name="Process.template.collection">
            <bean:define id="pId" name="tProcesses" property="processId"/>
            <html:option  value="<%=((Integer)pId).toString()%>">
                <bean:write name="tProcesses" property="processName"/>
            </html:option>
        </logic:iterate>
    </html:select>

    </td>
</tr>
<tr>
    <td>&nbsp;</td></tr>

<logic:present name="PROCESS_ADM_CONFIG_FORM" property="templateProcess">
<logic:greaterThan name="PROCESS_ADM_CONFIG_FORM" property="templateProcess.processId" value="0">
<bean:define id="tInfo" name="PROCESS_ADM_CONFIG_FORM" property="taskDetails"/>
<bean:size id="taskAllCount" name="tInfo" />
<bean:size id="currTaskCount" name="PROCESS_ADM_CONFIG_FORM" property="taskTemplateDetails.tasks" />
<tr>
    <td> <h5>Process Details: </h5></td></tr>
<tr><td><table width="80%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td><b>Process Name</b> </td>
        <td><b>Type Code</b></td>
        <td><b>Status</b></td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td><bean:write name="PROCESS_ADM_CONFIG_FORM" property="templateProcess.processName"/></td>
        <td>
            <bean:write name="PROCESS_ADM_CONFIG_FORM" property="templateProcess.processTypeCd"/>
        </td>
        <td>
            <html:select name="PROCESS_ADM_CONFIG_FORM"
                         property="templateProcess.processStatusCd">
                <html:option  value="<%=RefCodeNames.PROCESS_STATUS_CD.ACTIVE%>">
                    <%=RefCodeNames.PROCESS_STATUS_CD.ACTIVE%>
                </html:option>
                <html:option  value="<%=RefCodeNames.PROCESS_STATUS_CD.INACTIVE%>">
                    <%=RefCodeNames.PROCESS_STATUS_CD.INACTIVE%>
                </html:option>
            </html:select></td>
        <td><b>Tasks count:</b><bean:write name="taskAllCount"/></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>

</table>
</td>
</tr>
<tr>
    <td> <h5>Task Details: </h5>	</td>
</tr>
<%int taskIdx=0;%>
<logic:iterate id="ttDetails" name="PROCESS_ADM_CONFIG_FORM" property="taskTemplateDetails.tasks" indexId="i">


<%prop="indexStyle["+taskIdx+"]";%>
<bean:define id="idxstyle" name="PROCESS_ADM_CONFIG_FORM" property="<%=prop%>"/>
<%String styleId="tId"+taskIdx+"Style";%>
<html:hidden styleId="<%=styleId%>" name="PROCESS_ADM_CONFIG_FORM" property="<%=prop%>"/>

<logic:present name="ttDetails" property="task">

<bean:define id="tDetails" name="ttDetails" property="task"/>
<tr>
<td>
<logic:present name="tDetails" property="taskData">
<bean:define id="tData" name="tDetails" property="taskData"/>

<table width="100%" border="1">
<tr bgcolor="#FFFFFF">
    <td width="28%"><b><bean:write name="tData" property="taskName"/></b></td>
    <td width="51%"><b><bean:write name="tData" property="varClass"/></b></td>
    <td width="16%"><b><bean:write name="tData" property="method"/></b></td>
    <%String visualTxt= (((String)idxstyle).equals("display:none")?"[+]":"[-]");%>
    <td width="3%"align="center"><div id="viewdetailtId<%=taskIdx%>" class="styleText" style="cursor:hand" onClick="show_workflow_rule('tId<%=taskIdx%>');"><%=visualTxt%></div></td>
</tr>
<tr>
<td colspan="4"><table width="100%" border="2" align="center"  cellspacing=0 bgcolor="#CCCCCC" id="tId<%=taskIdx%>" style="<%=idxstyle%>">
<tr>
    <td colspan="2"><b>TaskName:</b></td>
    <%prop="taskTemplateDetails.tasks["+i+"].task.taskData.taskName";%>
    <td width="46%"><html:text name="PROCESS_ADM_CONFIG_FORM" property="<%=prop%>"/></td>
    <td colspan="2" rowspan="3" align="center">
        <table border=0 cellspacing=0 bgcolor="#FFFFFF">
            <tr>
                <td align="center"><div align="center" class="littleButtonGreen" style="cursor:hand" onClick="actionSubmitParam('addUpLink','taskIdx','<%=taskIdx%>');">add up</div></td>
                <td align="center"><div align="center" class="littleButtonBlue" style="cursor:hand" onClick="actionSubmitParam('addInputParam','taskIdx','<%=taskIdx%>');">add input</div></td>
            </tr>
            <tr>
                <td align="center"><div align="center" class="littleButtonGray" style="cursor:hand" onClick="actionSubmitParam('addCustomLink','taskIdx','<%=taskIdx%>');">add custom</div> </td>
                <td align="center"><div align="center" class="littleButtonBlue" style="cursor:hand" onClick="actionSubmitParam('addOutputParam','taskIdx','<%=taskIdx%>');">add output</div></td>
            </tr>
            <tr>
                <td><div align="center" class="littleButtonGreen" style="cursor:hand" onClick="actionSubmitParam('addDownLink','taskIdx','<%=taskIdx%>');">add down</div></td>
                <td><div align="center" class="littleButtonBlue" style="cursor:hand" onClick="actionSubmitParam('deleteProps','taskIdx','<%=taskIdx%>');">delete props</div></td>
            </tr>
            <tr><td align="center" colspan="2"><div align="center" class="littleButtonRedDblLong" style="cursor:hand" onClick="actionSubmitParam('deleteTask','taskIdx','<%=taskIdx%>');">delete task</div></td></tr>
        </table></td>
</tr>
<tr>
    <td colspan="2"><b>Class:</b></td>
    <%prop="taskTemplateDetails.tasks["+i+"].task.taskData.varClass";%>
    <td><html:text name="PROCESS_ADM_CONFIG_FORM" size="64" property="<%=prop%>"/></td>
</tr>
<tr>
    <td colspan="2"><b>Method:</b></td>
    <%prop="taskTemplateDetails.tasks["+i+"].task.taskData.method";%>
    <td><html:text name="PROCESS_ADM_CONFIG_FORM" property="<%=prop%>"/></td>
</tr>
<tr>
    <td width="11%"><b>Pos:</b></td>
    <td width="22%"><b>Name:</b></td>
    <td width="57%"><b>Class:</b> </td>
    <td colspan="2" width="10%"><b>Type Cd</b></td>

</tr>
<logic:present name="tDetails" property="taskPropertyDV">
    <tr>
        <td colspan="5"><b>Input Params:</b></td>
    </tr>
    <logic:iterate id="tProps" name="tDetails" property="taskPropertyDV" indexId="j">
        <% if (RefCodeNames.TASK_PROPERTY_TYPE_CD.INPUT.equals(((TaskPropertyData) tProps).getPropertyTypeCd())
                || RefCodeNames.TASK_PROPERTY_TYPE_CD.MANDATORY.equals(((TaskPropertyData) tProps).getPropertyTypeCd())
                || RefCodeNames.TASK_PROPERTY_TYPE_CD.OPTIONAL.equals(((TaskPropertyData) tProps).getPropertyTypeCd())) {%>
        <tr>
            <td>
                <%prop="taskTemplateDetails.tasks["+i+"].task.taskPropertyDV["+j+"].position";%>
                <html:text styleClass="smalltext" name = "PROCESS_ADM_CONFIG_FORM" size="2" property="<%=prop%>" />
            </td>
            <td>
                <%prop="taskTemplateDetails.tasks["+i+"].task.taskPropertyDV["+j+"].varName";%>
                <html:text styleClass="smalltext" name ="PROCESS_ADM_CONFIG_FORM" property="<%=prop%>"/>
            </td>
            <td>
                <%prop="taskTemplateDetails.tasks["+i+"].task.taskPropertyDV["+j+"].varType";%>
                <html:text styleClass="smalltext" name ="PROCESS_ADM_CONFIG_FORM"  size="64" property="<%=prop%>"/>
            </td>
            <td>
                <%prop="taskTemplateDetails.tasks["+i+"].task.taskPropertyDV["+j+"].propertyTypeCd";%>
                <html:select styleClass="smalltext" name="PROCESS_ADM_CONFIG_FORM" property="<%=prop%>">
                    <html:options collection="Task.property.type.status.cds" property="value"/>
                </html:select>
            </td>
            <td>
                <%prop = "propertyTaskSelectBox["+taskIdx+"].selected[" + j + "]";%>
                <html:multibox name="PROCESS_ADM_CONFIG_FORM" property="<%=prop%>"value="true"/>

            </td>
        </tr>
        <%}%>
    </logic:iterate>
    <tr>
        <td colspan="5"><b>Output Params:</b></td>
    </tr>        <logic:iterate id="tProps" name="tDetails" property="taskPropertyDV" indexId="j">
    <% if (RefCodeNames.TASK_PROPERTY_TYPE_CD.OUTPUT.equals(((TaskPropertyData) tProps).getPropertyTypeCd())) {%>
    <tr>
        <td>
            <%prop="taskTemplateDetails.tasks["+i+"].task.taskPropertyDV["+j+"].position";%>
            <html:text styleClass="smalltext" name = "PROCESS_ADM_CONFIG_FORM" size="2" property="<%=prop%>" />
        </td>
        <td>
            <%prop="taskTemplateDetails.tasks["+i+"].task.taskPropertyDV["+j+"].varName";%>
            <html:text styleClass="smalltext" name ="PROCESS_ADM_CONFIG_FORM" property="<%=prop%>"/>
        </td>
        <td>
            <%prop="taskTemplateDetails.tasks["+i+"].task.taskPropertyDV["+j+"].varType";%>
            <html:text styleClass="smalltext" name ="PROCESS_ADM_CONFIG_FORM"  size="64" property="<%=prop%>"/>
        </td>
        <td>
            <%prop="taskTemplateDetails.tasks["+i+"].task.taskPropertyDV["+j+"].propertyTypeCd";%>
            <html:select styleClass="smalltext" name="PROCESS_ADM_CONFIG_FORM" property="<%=prop%>">
                <html:options collection="Task.property.type.status.cds" property="value"/>
            </html:select>
        </td>
        <td>
            <%prop = "propertyTaskSelectBox["+taskIdx+"].selected[" + j + "]";%>

            <html:multibox name="PROCESS_ADM_CONFIG_FORM" property="<%=prop%>"value="true"/>

        </td>
    </tr>
    <%}%>
</logic:iterate>
</logic:present> </table></td>
</tr>
</table>
</logic:present>
</td>
</tr>
</logic:present>
<%taskIdx++;%>
</logic:iterate>
<logic:greaterThan name="currTaskCount" value="1">
    <tr>
        <td align="center">
            <html:submit property="action" value="Save All"/>
        </td>
    </tr>
</logic:greaterThan>
<logic:equal name="currTaskCount" value="1">
<tr><td align="center">
     <html:submit property="action" value="Add New Task"/>
</td></tr>
</logic:equal>
<tr><td>&nbsp;

</td></tr>
</logic:greaterThan>
</logic:present>
</table>
<html:hidden styleId = "hiddenAction"  property = "action"   value = "hiddenAction"/>
<html:hidden styleId = "taskIdx"       property = "taskIdx"  value = "-1"/>
</html:form>
</body>
</html:html>
