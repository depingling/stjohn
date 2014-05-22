<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<%
    String onKeyPress="return submitenter(this,event,'Submit');";
%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="REPORT_SCHEDULE_FORM" type="com.cleanwise.view.forms.ReportScheduleForm"/>

<script language="JavaScript1.2">
    <!--
    function popLocate(pLoc, name, pDesc) {
        var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
        locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
        locatewin.focus();
        return false;
    }

    //-->
</script>

<%
    ReportScheduleJoinView reportJoin = theForm.getReportScheduleJoin();
    GenericReportData report = reportJoin.getReport();
    ReportScheduleData schedule = reportJoin.getSchedule();
    ReportScheduleDetailDataVector scheduleDetails = reportJoin.getScheduleDetails();
    GenericReportControlViewVector controls = reportJoin.getReportControls();

%>
<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
    <tr>
        <td>&nbsp;</td>
        <td>Report Id:&nbsp;<b><%=report.getGenericReportId()%></b></td>
        <td>Report Schedule Id:&nbsp;<b><%=schedule.getReportScheduleId()%></b></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>Category:&nbsp;<b><%=report.getCategory()%></b></td>
        <td>Report Name:&nbsp;<b><%=report.getName()%></b></td>
    </tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="769" class="mainbody">
<tr>
    <!-- Parameter Control  -->
    <%
   if(controls!=null && controls.size()>0) {
   for(int ii=0; ii<controls.size(); ii++) {
   GenericReportControlView grc = (GenericReportControlView) controls.get(ii);
   String name = grc.getName();
   String label = grc.getLabel();
   if(label!=null && label.length()==0) label=null;
   boolean mandatoryFl=true;
   String mf = grc.getMandatoryFl();
   if(mf!=null) mf = mf.trim().toUpperCase();
   if("N".equals(mf) || "NO".equals(mf) ||"0".equals(mf) ||"F".equals(mf) ||"FALSE".equals(mf)) mandatoryFl = false;
   if(name.endsWith("_OPT")){
        mandatoryFl = false;
   }
   String controlEl = "genericControlValue["+ii+"]";

 if("BEG_DATE".equalsIgnoreCase(name)||"BEG_DATE_OPT".equalsIgnoreCase(name)) {
 %>
<tr>
    <td>&nbsp</td>
    <%
        mandatoryFl=true;
        if(name.endsWith("_OPT")){
            mandatoryFl=false;
        }
    %>
    <td><b><%=(label==null)?"Begin Date:":label%></b></td>
    <td>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
        <% for(int jj=0; jj<controls.size(); jj++) {
            GenericReportControlView grc1 = (GenericReportControlView) controls.get(jj);
            String name1 = grc1.getName();
            if("END_DATE".equalsIgnoreCase(name1) || "END_DATE_OPT".equalsIgnoreCase(name1)) {
                String label1 = grc.getLabel();
                if(label1!=null && label1.length()==0) label1=null;
                boolean mandatoryFl1=true;
                if(name1.endsWith("OPT")){
                    mandatoryFl1=false;
                }
                String mf1 = grc1.getMandatoryFl();
                if(mf!=null) mf1 = mf1.trim().toUpperCase();
                if("N".equals(mf1) || "NO".equals(mf1) ||"0".equals(mf1) ||"F".equals(mf1) ||"FALSE".equals(mf1)) mandatoryFl1 = false;
                String controlEl1 = "genericControlValue["+jj+"]"; %>
        <b><%=(label==null)?"End Date:":label1%></b>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl1%>" /><%if(mandatoryFl1) { %><span class="reqind">*</span> <%}%>
        <%
                    break;
                }
            }
        %>
    </td>
</tr>
<%
} else if("END_DATE".equalsIgnoreCase(name)) {
} else if("STORE".equalsIgnoreCase(name)) {
%>
<!-- Store -->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Store:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/storelocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
        <html:button property="locateStore"
                     onclick="<%=onClick%>"
                     value="Locate Store"/>
    </td>
</tr>
<%
} else if("STORE_OPT".equalsIgnoreCase(name)) {
%>
<!-- Store Optional-->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Store:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/storelocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" />
        <html:button property="locateStore"
                     onclick="<%=onClick%>"
                     value="Locate Store"/>
    </td>
</tr>
<%
} else if("ALLOW_UPDATES".equalsIgnoreCase(name)) {
%>
<html:hidden name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" value="false" />
<%
} else if("GROUP_OPT".equalsIgnoreCase(name) || "GROUP".equalsIgnoreCase(name)) {
%>
<!-- Group -->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Group:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/grouplocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
        <html:button property="locateGroup"
                     onclick="<%=onClick%>"
                     value="Locate Group"/>
    </td>
</tr>
<% } else if("ACCOUNT".equalsIgnoreCase(name)) { %>
<!-- Account -->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Account:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/accountlocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
        <html:button property="locateAccount"
                     onclick="<%=onClick%>"
                     value="Locate Account"/>
    </td>
</tr>
<%
} else if("ACCOUNT_OPT".equalsIgnoreCase(name)) {
%>
<!-- Account Optional-->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Account:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/accountlocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" />
        <html:button property="locateAccount"
                     onclick="<%=onClick%>"
                     value="Locate Account"/>
    </td>
</tr>
<%
} else if("ACCOUNT_MULTI_OPT".equalsIgnoreCase(name)) {
%>
<!-- Account Optional-->
<tr> <td>&nbsp;</td>
    <td><b><%=(label==null)?"Account(s):":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/accountLocateMulti', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" />
        <html:button property="locateAccount"
                     onclick="<%=onClick%>"
                     value="Locate Account(s)"/>
    </td>
</tr>
<%
} else if("DISTRIBUTOR".equalsIgnoreCase(name)) {
%>
<!-- Distributor -->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Distributor:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/distlocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
        <html:button property="locateDistributor"
                     onclick="<%=onClick%>"
                     value="Locate Distributor"/>
    </td>
</tr>

<%
} else if("DISTRIBUTOR_OPT".equalsIgnoreCase(name)) {
%>
<!-- Distributor Opt-->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Distributor:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/distlocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" />
        <html:button property="locateDistributor"
                     onclick="<%=onClick%>"
                     value="Locate Distributor"/>
    </td>
</tr>

<%
} else if("DISTRIBUTOR_MULTI_OPT".equalsIgnoreCase(name)) {
%>
<!-- Distributor Multi Opt-->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Distributor(s):":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/distLocateMulti', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" />
        <html:button property="locateDistributor(s)"
                     onclick="<%=onClick%>"
                     value="Locate Distributor(s)"/>
    </td>
</tr>
<%
} else if("MANUFACTURER".equalsIgnoreCase(name)) {
%>
<!-- Manufacturer -->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Manufacturer:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/manuflocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
        <html:button property="locateManufacturer"
                     onclick="<%=onClick%>"
                     value="Locate Manufacturer"/>
    </td>
</tr>
<%
} else if("MANUF_MULTI_OPT".equalsIgnoreCase(name)) {
%>
<!-- Manufacturer Multi Opt-->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Manufacturer(s):":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/manufLocateMulti', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" />
        <html:button property="locateManufacturer(s)"
                     onclick="<%=onClick%>"
                     value="Locate Manufacturer(s)"/>
    </td>
</tr>
<%
} else if("ITEM".equalsIgnoreCase(name)) {
%>
<!-- Item -->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Item:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/itemlocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
        <html:button property="locateItem"
                     onclick="<%=onClick%>"
                     value="Locate Item"/><br>
    </td>
</tr>
<%
} else if("ITEM_OPT".equalsIgnoreCase(name)) {
%>
<!-- Item Opt-->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Item:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/itemlocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" />
        <html:button property="locateItem"
                     onclick="<%=onClick%>"
                     value="Locate Item"/><br>
    </td>
</tr>
<%
} else if("CONTRACT".equalsIgnoreCase(name)||"CONTRACT_OPT".equalsIgnoreCase(name)) {
%>
<!-- Contract -->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Contract:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/contractlocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
        <html:button property="locateContract"
                     onclick="<%=onClick%>"
                     value="Locate Contract"/><br>
    </td>
</tr>
<%
} else if("CATALOG".equalsIgnoreCase(name)) {
%>
<!-- Catalog -->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Catalog:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/cataloglocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
        <html:button property="locateCatalog"
                     onclick="<%=onClick%>"
                     value="Locate Catalog"/><br>
    </td>
</tr>
<%
} else if("SITE".equalsIgnoreCase(name)) {
%>
<!-- Site -->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"Site:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/sitelocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
        <html:button property="locateSite"
                     onclick="<%=onClick%>"
                     value="Locate Site"/><br>
    </td>
</tr>
<%
} else if("COUNTY_OPT".equalsIgnoreCase(name)) {
%>
<!-- County Opt -->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"County:":label%></b></td>
    <td>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" />
    </td>
</tr>
<%
} else if("STATE_OPT".equalsIgnoreCase(name)) {
%>
<!-- State Opt -->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?"State:":label%></b></td>
    <td>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" />
    </td>
</tr>
<%
} else if("CUSTOMER".equalsIgnoreCase(name)) {
%>
<!-- User to run report as -->
<tr> <td>&nbsp;</td>
    <td><b><%=(label==null)?"User:":label%></b></td>
    <td>
        <% String onClick = "popLocate('../adminportal/usermgrLocate', '"+controlEl+"', '');";%>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" />
        <html:button property="locateUser"
                     onclick="<%=onClick%>"
                     value="Locate User"/>
    </td>
</tr>
<%
} else  {
%>
<!-- Generic Opt -->
<tr>
    <td>&nbsp;</td>
    <td><b><%=(label==null)?name:label%></b></td>
    <td>
        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_SCHEDULE_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
    </td>
</tr>
<% } %>
<% }} //End of generic report controls %>
</table>





