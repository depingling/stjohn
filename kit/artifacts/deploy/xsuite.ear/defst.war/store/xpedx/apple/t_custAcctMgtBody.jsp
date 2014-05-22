
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.i18n.*" %>
<%@ page import="com.cleanwise.service.api.reporting.ReportingUtils" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="IMGPath" type="java.lang.String"  name="pages.store.images"/>

<div class="text"><font color=red><html:errors/></font></div>

<TABLE cellSpacing=0 cellPadding=0 width=769 align=center border=0 
style=
"background-image: url(<%=ClwCustomizer.getSIP(request,"apple_opaque.JPG")%>);
background-position: 50% 50%;
background-repeat: no-repeat"
>
  <TBODY  >
  <TR>
    <TD class=text vAlign=top width=757>
    <br><br>
      <SPAN class=fivemargin>
<html:form name="CUSTOMER_REPORT_FORM" action="/userportal/customerAccountManagement.do?action=next"
type="com.cleanwise.view.forms.CustAcctMgtReportForm">
	  
      <TABLE cellSpacing=0 cellPadding=0 border=0>
        <TBODY >
		

<bean:define id="appUser" name="ApplicationUser"
  type="com.cleanwise.view.utils.CleanwiseUser"/>

<!-- start or reports list -->

<logic:present name="CustomerReport.type.vector">
<TR>
<TD>&nbsp;</TD>
<TD class="text" colspan="3"><b><app:storeMessage key="report.text.chooseReport"/></b></TD>
</TR>

<%--
<TD>&nbsp;</TD>
<TD class="text" bgcolor="#AEDEE4" colspan="3"><a href="http://reporting.xpedx.com">ePortfolio reports</a></TD>
--%>
	
<% 
  String lastCat = null;
  GenericReportViewVector reports = 
     (GenericReportViewVector) session.getAttribute("CustomerReport.type.vector");
  for(int ii=0; ii<reports.size(); ii++) {
     GenericReportView rep = (GenericReportView) reports.get(ii);
	 Integer i = new Integer(ii);
     String cat = rep.getReportCategory();
     cat = cat.replaceFirst("Customer*","").trim();
     if(!cat.equals(lastCat)){
       lastCat = cat;
%>

<TR>
<TD>&nbsp;</TD>
<TD class="text" colspan="3"><b>
<%
if(!cat.equals("")){

  // See if there is a translation for the report category.
  String xlateReportcat = ClwI18nUtil.getMessageOrNull(request, 
    ReportingUtils.makeReportCategoryKey(cat)
	);

  if ( xlateReportcat == null ) {
    // No translation found, use the default
    xlateReportcat = cat;
    if ( ! appUser.getPrefLocale().getLanguage().equals("en") ) {
      continue;
    }
  }

%>--<%=xlateReportcat%>--
<%}%></b></TD>
</TR>
<%}%>

<%
if(( (i.intValue()) % 2) != 0 ){ %>
<tr bgcolor="#AEDEE4">
<% }else{ %>
<%--<tr bgcolor="#FFFFFF">--%>
<tr>
<% } %>

<%--<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(request.getSession(),i)%>">--%>

<TD width="5%" bgcolor="#FFFFFF">&nbsp;</TD>
  <% String repName = rep.getReportName(); 
   if ( appUser.isaCustomer() || appUser.isaMSB() 
    || (     repName.trim().equals("Order Totals") == false 
         &&  repName.trim().equals("Invoice Totals") == false 
         &&  repName.trim().equals("Account Category Orders") == false) 
  ) { 
%>

<%
  // See if there is a translation for the report name.
  String xlateReportname = ClwI18nUtil.getMessageOrNull(request, 
    ReportingUtils.makeReportNameKey(repName)
	);

  if ( xlateReportname == null ) {
    // No translation found, use the default
    xlateReportname = repName;
    if ( ! appUser.getPrefLocale().getLanguage().equals("en") ) {
      continue;
    }

  }

%>

    <TD class="text" width="34%" valign="top"><b>
<a href="customerAccountManagement.do?action=next&reportTypeCd=<%=rep.getReportName()%>">
<%=xlateReportname%>
</a>

</b></TD>
<% } else { %>
    <td>
        Please access report <b><%=repName%></b> from the
        <% String repUrl = "/cleanwise/reporting/analyticRep.do?action=report&id=" + rep.getGenericReportId();%><a href="<%=repUrl%>"> reporting portal</a>.
    </td>
<% } %>


<TD class="text" width="54%">
<%
  // See if there is a translation for the report desc.
  String xlateReportdesc = ClwI18nUtil.getMessageOrNull(request, 
    ReportingUtils.makeReportDescriptionKey(repName)
	);

  if ( xlateReportdesc == null ) {
    // No translation found, use the default
    xlateReportdesc = rep.getLongDesc();
  }
%>
<%=xlateReportdesc%>

<!--
Orig desc: <%=rep.getLongDesc()%>
-->

</TD>
</TR>
<% } %>
<!-- /logic:iterate -->
</logic:present>


<!-- end of reports list -->

<logic:notPresent name="CustomerReport.type.vector">
<TR><TD>&nbsp;</TD>
<TD  colspan="3" class="text"><app:storeMessage key="report.text.noReports"/></TD>
</TR>
</logic:notPresent>

</TBODY>
</TABLE>

</html:form>

      
</TABLE>

