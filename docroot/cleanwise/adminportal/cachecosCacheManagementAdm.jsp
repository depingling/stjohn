<%@ page import="com.cleanwise.service.api.cachecos.CachecosManager" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<app:setLocaleAndImages/>
<app:checkLogon/>
<bean:define id="Location" value="none" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="ADM_CACHECOS_MGR_FORM" type="com.cleanwise.view.forms.AdmCachecosMgrForm"/>
<bean:define id="cInfo" name="theForm" property="cacheInformation" type="com.cleanwise.service.api.cachecos.CacheInformation"/>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
%>
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
    <title>Cachecos Administrator</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

    <script language="JavaScript1.2">
        <!--
       function actionSubmit(formNum, action) {
        var actions;
        actions=document.forms[formNum]["action"];
        if(actions.length){
            for(ii=actions.length-1; ii>=0; ii--) {
                 if(actions[ii].value=='hiddenAction') {
                      actions[ii].value=action;
                      document.forms[formNum].submit();
                      break;
                 }
           }
       } else {
         if(actions.value=='hiddenAction') {
            actions.value=action;
            document.forms[formNum].submit();
          }
        }
      return false;
 }
        --></script>
</head>

<body>

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
            <jsp:include flush='true' page="ui/admCachecosToolbar.jsp"/>
        </td>
    </tr>
</table>
<html:form  name="ADM_CACHECOS_MGR_FORM" action="/adminportal/cachecosAdm" scope="session">
<table bgcolor="#cccccc" width="769" class="mainbody">
<tr><td class="largeheader" colspan="4">Cache Management</td></tr>
<tr>
    <td><b>Request Time</b>:</td>
    <td>
        <%
            String reqTimeStr = "";
            long reqTime = cInfo.getRequestTime();
            if (reqTime > 0) {
                reqTimeStr = sdf.format(new Date(reqTime));
            }%>
        <%=reqTimeStr%>
    </td>
    <td><b>Cache state</b>:</td>
    <td><%
        int state = cInfo.getState();
        String stateStr;
        switch (state) {
            case CachecosManager.STARTED:stateStr = "<span style=\"BACKGROUND-COLOR:green;color:white\"><b>&nbsp;STARTED&nbsp;</b></span>"; break;
            case CachecosManager.STOPPED:stateStr = "<span style=\"BACKGROUND-COLOR:#ff9900;color:white\"><b>&nbsp;STOPPED&nbsp;</b></span>"; break;
            case CachecosManager.BROKEN:stateStr = "<span style=\"BACKGROUND-COLOR:red;color:white\"><b>&nbsp;BROKEN&nbsp;</b></span>"; break;
            default:stateStr = "<span style=\"BACKGROUND-COLOR:black;color:white\"><b>&nbsp;UNKNOWN&nbsp;</b></span>"; break;
        }
    %>
        <%=stateStr%>
    </td>
</tr>

<tr>
    <td><b>Cache size</b>:</td>
    <td><bean:write name="cInfo" property="size"/></td>
    <td><b>Last Access Time</b>:</td>
    <td><%
        String lAccessTimeStr = "";
        long lAccessTime = cInfo.getLastAccessTime();
        if (lAccessTime > 0) {
            lAccessTimeStr = sdf.format(new Date(lAccessTime));
        }%>
        <%=lAccessTimeStr%>
    </td>
</tr>


<tr>
    <td><b>Cache Memory</b>: </td>
    <td><%=new BigDecimal(((double) (cInfo.getCacheMemo())) / (double) (1024 * 1024)).setScale(4, BigDecimal.ROUND_HALF_UP)%> Mb</td>
    <td><b>Cache Access Counter</b>:</td>
    <td><b>Get</b>: <%=cInfo.getAccessCounterGet()%> <b>Put</b>: <%=cInfo.getAccessCounterPut()%> <b>Remove</b>: <%=cInfo.getAccessCounterRemove()%></td>

</tr>

<tr>
    <td><b>Descriptor Memory</b>: </td>
    <td><%=new BigDecimal(((double) (cInfo.getDescriptorMemo())) / (double) (1024 * 1024)).setScale(4, BigDecimal.ROUND_HALF_UP)%> Mb</td>
    <td></td>
    <td></td>  </tr>

<tr>
    <td><b>Access History Memory</b>: </td>
    <td><%=new BigDecimal(((double) (cInfo.getAccessHistoryMemo())) / (double) (1024 * 1024)).setScale(4, BigDecimal.ROUND_HALF_UP)%> Mb</td>
    <td></td>
    <td></td>
</tr>

<tr><td colspan="4">&nbsp;</td></tr>

<tr><td class="largeheader" colspan="4">Garbage Collector</td></tr>

<tr>
    <td><b>Interval</b>:</td>
    <td><html:text property="cacheInformation.garbageCollectionInterval" size="10"/> ms.</td>
    <td><b>Obj.Lifetime</b>:</td>
    <td><html:text property="cacheInformation.objectLifetime" size="10"/> ms.</td>
</tr>

<tr>
    <%
        String lastGarbageCollectorRunStr = "";
        String nextGarbageCollectorRunStr = "";

        if(cInfo.getLastGarbageCollectorRun()!=null){
            lastGarbageCollectorRunStr = sdf.format(cInfo.getLastGarbageCollectorRun());
            if(cInfo.getGarbageCollectionInterval()>0){
                nextGarbageCollectorRunStr = sdf.format(new Date(cInfo.getLastGarbageCollectorRun().getTime()+cInfo.getGarbageCollectionInterval()));
            }
        }

    %>
    <td><b>Last Run</b>:</td>
    <td><%=lastGarbageCollectorRunStr%></td>
    <td><b>Next Run</b>:</td>
    <td><%=nextGarbageCollectorRunStr%></td>
</tr>

<tr><td colspan="4">&nbsp;</td></tr>


<tr>
    <td valign="top" colspan="4">
        <table><tr>
            <td><a class="littleButton" style="text-decoration:none;cursor:hand" href="cachecosAdm.do?action=executeOp&op=clear"><font color=black>&nbsp;Clear&nbsp;</font></a></td>
            <td><a class="littleButton" style="text-decoration:none;cursor:hand" href="cachecosAdm.do?action=executeOp&op=stop"><font color=black>&nbsp;Stop&nbsp;</font></a></td>
            <td><a class="littleButton" style="text-decoration:none;cursor:hand" href="cachecosAdm.do?action=executeOp&op=start"><font color=black>&nbsp;Start&nbsp;</font></a></td>
            <td><a class="littleButton" style="text-decoration:none;cursor:hand" href="#" onclick="actionSubmit('0','executeOp')"><font color=black>&nbsp;Set&nbsp;</font></a></td>
        </tr>

        </table>
    </td>
</tr>
<tr><td colspan="4">&nbsp;</td></tr>

</table>
<html:hidden property="action" value="hiddenAction"/>
</html:form>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>
