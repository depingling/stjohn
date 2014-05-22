<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.utils.RequestPropertyNames" %>
<%@ page import="com.cleanwise.view.forms.LocateReportDistributorForm" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
String jspFormName = request.getParameter("jspFormName");
if (jspFormName == null) {
    throw new RuntimeException("jspFormName cannot be null");
}

String jspFormNestProperty = request.getParameter("jspFormNestProperty");

if (jspFormNestProperty != null) {
    jspFormNestProperty = jspFormNestProperty + ".locateReportDistributorForm";
}
else {
    jspFormNestProperty = "locateReportDistributorForm";
}

String jspHtmlFormId = request.getParameter("jspFormName");

if (jspHtmlFormId == null) {
    jspHtmlFormId = "LOCATE_DISTRIBUTOR_FORM_ID";
}

String jspReportIdName = request.getParameter("jspReportIdName");
String jspReportIdValue = request.getParameter("jspReportIdValue");


%>
<logic:present name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" >

<% com.cleanwise.view.forms.StorePortalBaseForm baseForm = null;
  if (jspFormName.toUpperCase().startsWith("ANALYTIC")) {
  baseForm = (com.cleanwise.view.forms.AnalyticReportForm)session.getAttribute(jspFormName);
 } else if (jspFormName.toUpperCase().startsWith("CUSTOMER")) {
  baseForm = (com.cleanwise.view.forms.CustAcctMgtReportForm)session.getAttribute(jspFormName);
 } %>
<bean:define id="theForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" type="LocateReportDistributorForm"/>
<%
if (theForm != null && theForm.getLocateReportDistributorFl()) {
    String jspFormAction = request.getParameter("jspFormAction");
    if (jspFormAction == null) {
        throw new RuntimeException("jspFormAction cannot be null");
    }
    String jspSubmitIdent = request.getParameter("jspSubmitIdent");
    if (jspSubmitIdent == null) {
        throw new RuntimeException("jspSubmitIdent cannot be null");
    }
    String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
    if (jspReturnFilterProperty == null) {
        throw new RuntimeException("jspReturnFilterProperty cannot be null");
    }
    // To be Used for Data Werehouse
    String jspDataSourceType = request.getParameter("jspDataSourceType");
    if (jspDataSourceType == null) {
        jspDataSourceType = "";
    }
    jspSubmitIdent += "#" + SessionAttributes.SEARCH_FORM.LOCATE_REPORT_DISTRIBUTOR_FORM;

%>
 <%
String titleLabel = null;
String label = null;
if (baseForm != null ){
  GenericReportControlViewVector reportControls = null;
  if (baseForm instanceof com.cleanwise.view.forms.AnalyticReportForm) {
     reportControls = ((com.cleanwise.view.forms.AnalyticReportForm)baseForm).getGenericControls();
  } else if (baseForm instanceof com.cleanwise.view.forms.CustAcctMgtReportForm){
     reportControls = ((com.cleanwise.view.forms.CustAcctMgtReportForm)baseForm).getReportControls();
  }
  if (reportControls != null && reportControls.size()>0) {
    for (int i = 0; i < reportControls.size(); ++i) {
        GenericReportControlView reportControl = (GenericReportControlView) reportControls.get(i);
        if (reportControl.getName().contains("DISTRIBUTOR_MULTI")) {
           label = reportControl.getLabel();
           if (label != null && label.length()==0){
             label = null;
           }
           titleLabel = (label!=null) ? label.replace(":", "") : label;
           break;
        }
    }
  }
}
  %>


<html:html>
       <script language="JavaScript1.2">
            <!--
            function SetChecked(val) {
                dml = document.forms;
                for (i = 0; i < dml.length; i++) {
                    found = false;
                    ellen = dml[i].elements.length;
                    for (j = 0; j < ellen; j++) {
                        if (dml[i].elements[j].name == '<%=jspFormNestProperty%>.selected') {
                            dml[i].elements[j].checked = val;
                            found = true;
                        }
                    }
                    if (found)
                        break;
                }
            }

            function SetAndSubmit(name, val) {
                var dml = document.forms[0];
                var ellen = dml[name].length;
                if (ellen>0) {
                    for (j = 0; j < ellen; j++) {
                        if (dml[name][j].value == val) {
                            found = true;
                            dml[name][j].checked = 1;
                        }
                        else {
                            dml[name][j].checked = 0;
                        }
                    }
                }
                else {
                    dml[name].checked = 1;
                }
                var iiLast = dml['action'].length - 1;
                dml['action'][iiLast].value = 'Return Selected';
                dml.submit();
            }
            //-->
        </script>


<body>

<script src="../externals/lib.js" language="javascript"></script>
  <html:form styleId="locateReportDistributorForm" action="<%=jspFormAction%>"  scope="session">
<div class="rptmid">
Find Distributors
<table ID=342 width="750" border="0"  class="mainbody">
<%//  theForm.setDataSourceType(jspDataSourceType);  %>
    <html:hidden property="<%=RequestPropertyNames.PROPERTY_NAME_LOCATE_DISTRIBUTOR_TYPE%>"
                    value="<%=RequestPropertyNames.PROPERTY_VALUE_LOCATE_DISTRIBUTOR_REPORT%>"/>
    
  <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
  <%
                theForm.setDataSourceType(jspDataSourceType);
                String prop = jspFormNestProperty + ".property";
                %>
                <html:hidden property="<%=prop%>" value="<%=jspReturnFilterProperty%>"/>
                <%
                prop = jspFormNestProperty + ".name";
                %>
                <html:hidden property="<%=prop%>" value="<%=jspFormName%>"/>
                <%
                if (jspReportIdName != null && jspReportIdValue != null) {
                %>
                <html:hidden property="<%=jspReportIdName%>" value="<%=jspReportIdValue%>"/>
                <%
                }
                %>
  <tr>
       <td colspan="4">
      <%String searchFieldName = "Id/Name:";
        if (jspDataSourceType.equals("DW")) {
          searchFieldName ="Name:";
        } %>
      <b><%=searchFieldName%></b>
      <%prop=jspFormNestProperty+".searchField";%>
      <html:text  property="<%=prop%>"/>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <%prop=jspFormNestProperty+".searchType";%>
      <% if (!jspDataSourceType.equals("DW")) { %>
      <html:radio property="<%=prop%>" value="id" />
      ID
      <%}%>
      <html:radio property="<%=prop%>" value="nameBegins" />
      Name(starts with)
      <html:radio property="<%=prop%>" value="nameContains" />
      Name(contains)
     </td>
  </tr>
  <% if (!jspDataSourceType.equals("DW")) { %>
  <tr><td colspan='4'>
    <b>Serviced Territory &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b> State:
    <%prop=jspFormNestProperty+".searchState";%>
    <html:text  property="<%=prop%>" size='3'/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    County (starts with):
    <%prop=jspFormNestProperty+".searchCounty";%>
    <html:text  property="<%=prop%>" size='10'/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    Zip Code:
    <%prop=jspFormNestProperty+".searchPostalCode";%>
    <html:text  property="<%=prop%>" size='6'/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </td></tr>
  <% } %>
  <% GroupDataVector distGroups =  theForm.getDistGroups();
     if(distGroups!=null && distGroups.size()>0) {
  %>
  <tr><td colspan='4'><b>Groups:</b>
      <%prop=jspFormNestProperty+".searchGroupId";%>
   <html:radio property='<%=prop%>' value='0'>None&nbsp;</html:radio>
  <% for (Iterator iter=distGroups.iterator(); iter.hasNext();) {
     GroupData gD = (GroupData) iter.next();
     String grIdS = ""+gD.getGroupId();
     String grName = gD.getShortDesc();
  %>
      <%prop=jspFormNestProperty+".searchGroupId";%>
   <html:radio property='<%=prop%>' value='<%=grIdS%>'><%=grName%>&nbsp;
   </html:radio>
  <% } %>
  </td></tr>
  <% } %>
  <tr><td colspan='4'>
	<html:submit property="action" value="Search"/>
      <%prop=jspFormNestProperty+".showInactiveFl";%>
	Show Inactive: <html:checkbox property="<%=prop%>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<html:submit property="action" value="Cancel"/>
	<html:submit property="action" value="Return Selected"/>
  </td>
</tr>
</table>


<!-- logic:present name="locateReportDistributorForm.distributors" -->
<% if(theForm.getDistributors()!=null) {
 int rescount = theForm.getDistributors().size();
%>
Search result count: <%=rescount%>
<% if(rescount>0) { %>

<table ID=343 width="750" border="0" class="results">
<tr align=left>
<% if (!jspDataSourceType.equals("DW")) { %>
<td><a ID=344 class="tableheader">Dist Id</a></td>
<%} %>
<td>
<a ID=345 href="javascript:SetChecked(1)">[Check&nbsp;All]</a><br>
<a ID=346 href="javascript:SetChecked(0)">[&nbsp;Clear]</a>
</td>
<td><a ID=347 class="tableheader">Name </a></td>
<td><a ID=348 class="tableheader">Address</a></td>
<td><a ID=349 class="tableheader">City</a></td>
<td><a ID=350 class="tableheader">State/Province</a></td>
<td><a ID=351 class="tableheader">Status</a></td>
</tr>


<%prop=jspFormNestProperty+".distributors";
String selectBoxProp = jspFormNestProperty + ".selected";
%>
<logic:iterate id="arrele" name="<%=jspFormName%>" property="<%=prop%>"
       type="com.cleanwise.service.api.value.DistributorData">
    <bean:define id="key"  name="arrele" property="busEntity.busEntityId"/>
    <bean:define id="name" name="arrele" property="busEntity.shortDesc" type="String"/>

      <%prop=jspFormNestProperty+".selected";%>
    <%
        //String linkHref = "javascript: SetAndSubmit ('"+prop+"',"+key+");";
       String linkHref = "javascript: SetAndSubmit ('" + selectBoxProp + "'," + key + ");";
    %>

<tr>
<% if (!jspDataSourceType.equals("DW")) { %>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<% } %>
      <%prop=jspFormNestProperty+".selected";%>
<td><html:multibox property="<%=prop%>" value="<%=key.toString()%>"/></td>
<td><a ID=352 href="<%=linkHref%>"><bean:write name="arrele" property="busEntity.shortDesc"/></a></td>
<td><bean:write name="arrele" property="primaryAddress.address1"/></td>
<td><bean:write name="arrele" property="primaryAddress.city"/></td>
<td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</tr>
</logic:iterate>

</table>


<%}%>
<%}%>



</div>
<html:hidden  property="action" value="Search"/>
</html:form>
</body>

</html:html>
<%}//if(theForm.getLocateDistFl())

%>
</logic:present>
