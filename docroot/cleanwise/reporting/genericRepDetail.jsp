<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% String onKeyPress="return submitenter(this,event,'Submit');"; %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="GENERIC_REPORT_FORM"
  type="com.cleanwise.view.forms.GenericReportForm"/>
<bean:define id="theUserCd" name="<%=Constants.APP_USER%>"
  property="user.userTypeCd"  type="java.lang.String"/>
<%
/*
    private int mGenericReportId;// SQL type:NUMBER, not null
    private String mCategory;// SQL type:VARCHAR2, not null
    private String mName;// SQL type:VARCHAR2, not null
    private String mLongDesc;// SQL type:VARCHAR2

    private String mGenericReportType;// SQL type:VARCHAR2
    private String mReportSchemaCd;// SQL type:VARCHAR2
    private String mParameterToken;// SQL type:VARCHAR2
    private String mSupplementaryControls;// SQL type:VARCHAR2
    private String mRuntimeEnabled;// SQL type:VARCHAR2

 private String mAddBy;// SQL type:VARCHAR2
 private String mModBy;// SQL type:VARCHAR2

    private String mClassname;// SQL type:VARCHAR2
    private String mInterfaceTable;// SQL type:VARCHAR2
    private Clob mScriptText;// SQL type:CLOB
    private Clob mSqlText;// SQL type:CLOB
 */
%>

<script language="JavaScript1.2">
<!--

<%--
submits the form when tehre are multiple buttons present (locate buttons)
--%>
function submitSearch(){
	document.forms[0].setFilter.value='setFilter'; document.forms[0].submit();
}
function keyDown(e)
{
    var keycode = document.all ? event.keyCode : e.which;
    var realkey = String.fromCharCode(keycode)
    if ((""+keycode)=="13")
    {
       submitSearch();
    }
}

document.onkeydown = keyDown;
if ( document.layers )
{
  document.captureEvents(Event.KEYDOWN);
}


//-->
</script>

<!-- SSS -->
<table cellpadding="0" cellspacing="0" width="769" bgcolor="#cccccc">

<!-- action="reporting/genericRep"-->
<html:form name="GENERIC_REPORT_FORM" action="reporting/genericRep"
    scope="session" type="com.cleanwise.view.forms.GenericReportForm">

  <tr>
  <td>
    <b>Report Id:</b>&nbsp;&nbsp;
     <bean:write name="GENERIC_REPORT_FORM" property='report.genericReportId'/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <b>Category:</b>&nbsp;&nbsp;
    <html:text name="GENERIC_REPORT_FORM" property="report.category" size='20'/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <b>Runtime Enabled:</b>&nbsp;&nbsp;
  <html:text name="GENERIC_REPORT_FORM" property="report.runtimeEnabled" size='5'/>
  </td>
  </tr>
  <tr>
  <td><b>Report Name:</b>&nbsp;&nbsp;
    <html:text name="GENERIC_REPORT_FORM" property="report.name" size='50'/>
  </td>
  </tr>
  <tr>
  <td><b>Report Description:</b>&nbsp;&nbsp;
  <html:text name="GENERIC_REPORT_FORM" property="report.longDesc" size='100'/>
  </td>
  </tr>
  <tr>
  <td><b>DB Name:</b>&nbsp;&nbsp;
    <html:text name="GENERIC_REPORT_FORM" property="report.dbName" size='50'/>
  </td>
  </tr>
  <tr>
  <td><b>Report Type:</b>&nbsp;&nbsp;
    <html:select name="GENERIC_REPORT_FORM" property="report.genericReportType" >
    <html:option value=""/>
    <html:option value="<%=RefCodeNames.GENERIC_REPORT_TYPE_CD.JAVA_CLASS%>"/>
    <html:option value="<%=RefCodeNames.GENERIC_REPORT_TYPE_CD.JAVA_CLASS_2%>"/>
    <html:option value="<%=RefCodeNames.GENERIC_REPORT_TYPE_CD.JAVA_CLASS_MULTI%>"/>
    <html:option value="<%=RefCodeNames.GENERIC_REPORT_TYPE_CD.SQL%>"/>
    <html:option value="<%=RefCodeNames.GENERIC_REPORT_TYPE_CD.XSQL%>"/>
    </html:select>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <b>Report Schema(s):</b>&nbsp;&nbsp;
  <html:text name="GENERIC_REPORT_FORM" property="report.reportSchemaCd" size='25'/>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <b>Parameter Token:</b>&nbsp;&nbsp;
  <html:text name="GENERIC_REPORT_FORM" property="report.parameterToken" size='2'/>
  </td>
</tr>
  <tr>
  <td><b>Controls():</b>&nbsp;&nbsp;
  </td>
  </tr>
  <tr>
  <td><html:textarea name="GENERIC_REPORT_FORM" property="report.supplementaryControls" cols='90' rows='5'/>
  </td>
  </tr>
 <tr><td>&nbsp;&nbsp;
     <%@ include file="genericRepDetailUserTypes.jsp"  %>
   </td>
</tr>
  <tr>
  <td><b>Class Name:</b>&nbsp;&nbsp;
  <html:text name="GENERIC_REPORT_FORM" property="report.classname" size='100'/>
  </td>
  </tr>
  <tr>
  <td><b>Interface Table:</b>&nbsp;&nbsp;
  <html:text name="GENERIC_REPORT_FORM" property="report.interfaceTable" size='30'/>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <html:submit property="action" value="Save"/>&nbsp;&nbsp;
        <html:submit property="action" value="Clone"/>&nbsp;&nbsp;
        <html:submit property="action" value="Delete"/>
  </td>
  </tr>
  <tr>
  <td><b>Sql:</b>&nbsp;&nbsp;
  </td>
  </tr>
  <tr>
  <td>
  <html:textarea name="GENERIC_REPORT_FORM" property="sqlText" cols='90' rows='30'/>
  </td>
  </tr>
  <tr>
  <td><b>Pl/Sql Script:</b>&nbsp;&nbsp;
  </td>
  </tr>
  <tr>
  <td>
  <html:textarea name="GENERIC_REPORT_FORM" property="scriptText"  cols='90' rows='30'/>
  </td>
  </tr>
</html:form>

<% GenericReportData reportD = theForm.getReport();
%>
</table>



