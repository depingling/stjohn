<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.LocateStoreAccountForm" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

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

    //LocateStoreAccountForm theForm = null;
    String jspFormNestProperty = request.getParameter("jspFormNestProperty");
    if (jspFormNestProperty != null) {
        jspFormNestProperty = jspFormNestProperty + ".locateStoreAccountForm";
    } else {
        jspFormNestProperty = "locateStoreAccountForm";
    }

    boolean doNotShowCheckBoxes = Utility.isTrue(request.getParameter("doNotShowCheckboxes"), false);
%>
  <logic:present name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" >
  <bean:define id="theForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" type="LocateStoreAccountForm"/>

  <%
  if(theForm != null && theForm.getLocateAccountFl()){
  String jspFormAction = request.getParameter("jspFormAction");
  if(jspFormAction == null){
	throw new RuntimeException("jspFormAction cannot be null");
  }
  String jspSubmitIdent = request.getParameter("jspSubmitIdent");
  if(jspSubmitIdent == null){
	throw new RuntimeException("jspSubmitIdent cannot be null");
  }
  String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
  if(jspReturnFilterProperty == null){
	throw new RuntimeException("jspReturnFilterProperty cannot be null");
  }
  jspSubmitIdent += "#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM;

   //bug # 4601
  String fiscalYearColumn = request.getParameter("fiscalYearColumnRequied");
  boolean showFiscalYearColumn = false;
  if(fiscalYearColumn!=null && fiscalYearColumn.equals("true")) {
		showFiscalYearColumn = true;
  }
%>


<html:html>
<script language="JavaScript1.2">
<!--
function SetAndSubmit (name, val) {
  var dml=document.forms[1];
  var ellen = dml[name].length;
  if(ellen>0) {
    for(j=0; j<ellen; j++) {
        if(dml[name][j].value==val) {
          found = true;
            dml[name][j].checked=1;
        } else {
            dml[name][j].checked=0;
        }
    }
  } else {
    dml[name].checked=1;
  }
  var iiLast = dml['action'].length-1;
  dml['action'][iiLast].value='Return Selected';
  dml.submit();
}

 function setFiscalYear(selectedYear,accountId) {
	document.getElementById("selectedYearValue").value= document.getElementById(selectedYear).value;
	document.getElementById("selectedAccountIdValue").value = accountId;
	var dml=document.forms[1];
	 var iiLast = dml['action'].length-1;
	dml['action'][iiLast].value='setFiscalYear';
	dml.submit();	
  }

//-->
</script>



<body>

<script src="../externals/lib.js" language="javascript"></script>

<div class="rptmid">
Find Accounts
<table ID=324 width="750" border="0"  class="mainbody">
  <html:form styleId="325" action="<%=jspFormAction%>"  scope="session">
  <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
  <%String prop=jspFormNestProperty+".property";%>
  <html:hidden property="<%=prop%>" value="<%=jspReturnFilterProperty%>"/>
  <%prop=jspFormNestProperty+".name";%>
  <html:hidden property="<%=prop%>" value="<%=jspFormName%>"/>
  <%--html:hidden property="accountIdForExport"/--%>

  <tr> <td>&nbsp;</td>
       <td colspan="3">
	   <%prop=jspFormNestProperty+".searchField";%>
      <html:text  property="<%=prop%>"/>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <%prop=jspFormNestProperty+".searchType";%>
      <html:radio property="<%=prop%>" value="id" />
      ID
      <html:radio property="<%=prop%>" value="nameBegins" />
      Name(starts with)
      <html:radio property="<%=prop%>" value="nameContains" />
      Name(contains)
     </td>
  </tr>
  <% GroupDataVector accountGroups =  theForm.getAccountGroups();
     if(accountGroups!=null && accountGroups.size()>0) {
  %>
  <tr><td colspan='4'><b>Groups:</b>
  <%prop=jspFormNestProperty+".searchGroupId";%>
   <html:radio property='<%=prop%>' value='0'>None&nbsp;</html:radio>
  <% for (Iterator iter=accountGroups.iterator(); iter.hasNext();) {
     GroupData gD = (GroupData) iter.next();
     String grIdS = ""+gD.getGroupId();
     String grName = gD.getShortDesc();
  %>
   <html:radio property='<%=prop%>' value='<%=grIdS%>'><%=grName%>&nbsp;
   </html:radio>
  <% } %>
  </td></tr>
  <% } %>
  <tr><td colspan='4'>
	<html:submit property="action" value="Search"/>
	<%prop=jspFormNestProperty+".showInactiveFl";%>
	Show Inactive: <html:checkbox property="<%=prop%>"/>
   </td>
  </tr>
</table>


<!-- logic:present name="locateStoreAccountForm.accounts" -->
<% if(theForm.getAccountSearchResult()!=null) {
 int rescount = theForm.getAccountSearchResult().size();
%>
Search result count:
<% if ( rescount >= Constants.MAX_ACCOUNTS_TO_RETURN ){ %>
(request limit)
<%}%>
<%=rescount%>
<% if(rescount>0) { %>

<table ID=326 width="750" border="0" class="results">
<tr align=left>
<td><a ID=327 class="tableheader">Account Id</a></td>
<td><a ID=328 class="tableheader">Name </a></td>
<% int count=0;
	if(showFiscalYearColumn) {%>
	<td><a ID=335 class="tableheader">Fiscal Year </a>
	<html:hidden property="selectedAccountIdValue" styleId="selectedAccountIdValue" value=""/>
	<html:hidden property="selectedYearValue" styleId="selectedYearValue" value=""/>
	</td>
<%}%>
<td>
<% if (!doNotShowCheckBoxes) { %>
    <a ID=329 href="javascript:SetChecked(1)">[Check&nbsp;All]</a><br>
    <a ID=330 href="javascript:SetChecked(0)">[&nbsp;Clear]</a>
<% } %>
</td>
<td><a ID=331 class="tableheader">City</a></td>
<td><a ID=332 class="tableheader">State/Province</a></td>
<td><a ID=333 class="tableheader">Type</a></td>
<td><a ID=334 class="tableheader">Status</a></td>
</tr>

<%
  String propName = jspFormNestProperty + ".accountSearchResult";
  prop=jspFormNestProperty+".accountSearchResult";
  String selectBoxProp = jspFormNestProperty+".selected";
%>
<logic:iterate id="arrele" name="<%=jspFormName%>" property="<%=prop%>"
       type="com.cleanwise.service.api.value.AccountSearchResultView">
    <bean:define id="key"  name="arrele" property="accountId"/>
    <bean:define id="name" name="arrele" property="shortDesc" type="String"/>
    <% String linkHref = "javascript: SetAndSubmit ('"+selectBoxProp+"',"+key+");";%>

<tr>
<td><bean:write name="arrele" property="accountId"/></td>
<td><a ID=335 href="<%=linkHref%>"><bean:write name="arrele" property="shortDesc"/></a></td>
<% if(showFiscalYearColumn) {
	String dropdownId = "selectedYear_"+count;
	count++;
	String styleIdValue = "javascript: setFiscalYear ('"+dropdownId+"',"+key+");";
	%>
	<td>
	<html:select styleId="<%=dropdownId %>" name="arrele" property="selectedFiscalYear" onchange="<%=styleIdValue %>" >
		<logic:iterate id="fiscalYear" name="arrele" property="fiscalYearsList">
			<% if(new Integer(arrele.getSelectedFiscalYear()).equals(fiscalYear)) { %>
						<option selected value="<bean:write name='fiscalYear' />" ><bean:write name="fiscalYear" /></option>
					<% } else { %>
						<option value="<bean:write name='fiscalYear' />" ><bean:write name="fiscalYear" /></option>
					<% } %>
		</logic:iterate>
	</html:select>
	</td>
<%}%>
<% if (doNotShowCheckBoxes) { %>
<td style="visibility:hidden">
<% } else { %>
<td>
<% } %>
    <html:multibox property="<%=selectBoxProp%>" value="<%=key.toString()%>"/>
</td>
<td><bean:write name="arrele" property="city"/></td>
<td><bean:write name="arrele" property="stateProvinceCd"/></td>
<td><bean:write name="arrele" property="value"/></td>
<td><bean:write name="arrele" property="busEntityStatusCd"/></td>
</tr>
</logic:iterate>

</table>


<%}%>
<%}%>
<!-- /logic:present -->
  <html:hidden  property="action" value="Search"/>
</html:form>

</div>
</body>

</html:html>

<%}//if(theForm.getLocateAccountFl())%>
</logic:present> <%--main form--%>