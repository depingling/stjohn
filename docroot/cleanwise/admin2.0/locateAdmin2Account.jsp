<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.LocateStoreAccountForm" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
  String jspFormName = request.getParameter("jspFormName");
  if(jspFormName == null){
	throw new RuntimeException("jspFormName cannot be null");
  }

  //LocateStoreAccountForm theForm = null;
  String jspFormNestProperty = request.getParameter("jspFormNestProperty");
  if(jspFormNestProperty != null){
	jspFormNestProperty = jspFormNestProperty + ".locateStoreAccountForm";
  }else{
	jspFormNestProperty = "locateStoreAccountForm";
  }
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
%>


<html:html>
<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  found = false;
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='<%=jspFormNestProperty%>.selected') {
      dml[i].elements[j].checked=val;
      found = true;
    }
  }
  if(found) break;
 }
}

function SetAndSubmit (name, val, act) {
  var dml=document.forms[0];
  var el = dml[name];
  if('undefined' != typeof el ) {
    var ellen = el.length;
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
  }
  var iiLast = dml['action'].length-1;
  dml['action'][iiLast].value=act;
  dml.submit();
}

//-->
</script>



<body>

<div class="rptmid">
<app:storeMessage key="locateAccount.label.findAccounts"/>
<table ID=324 width="750" border="0"  class="mainbody">
  <html:form styleId="325" action="<%=jspFormAction%>"  scope="session">
  <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
  <%String prop=jspFormNestProperty+".property";%>
  <html:hidden property="<%=prop%>" value="<%=jspReturnFilterProperty%>"/>
  <%prop=jspFormNestProperty+".name";%>
  <html:hidden property="<%=prop%>" value="<%=jspFormName%>"/>

  <tr> <td>&nbsp;</td>
       <td colspan="3">
	   <%prop=jspFormNestProperty+".searchField";%>
      <html:text  property="<%=prop%>"/>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <%prop=jspFormNestProperty+".searchType";%>
      <html:radio property="<%=prop%>" value="id" />
      <app:storeMessage key="locateAccount.radio.text.id"/>
      <html:radio property="<%=prop%>" value="nameBegins" />
      <app:storeMessage key="locateAccount.radio.text.nameStartsWith"/>
      <html:radio property="<%=prop%>" value="nameContains" />
      <app:storeMessage key="locateAccount.radio.text.nameContains"/>
     </td>
  </tr>
  <% GroupDataVector accountGroups =  theForm.getAccountGroups();
     if(accountGroups!=null && accountGroups.size()>0) {
  %>
  <tr><td colspan='4'><b><app:storeMessage key="locateAccount.label.groups"/>:</b>
  <%prop=jspFormNestProperty+".searchGroupId";%>
   <html:radio property='<%=prop%>' value='0'><app:storeMessage key="locateAccount.radio.text.none"/>&nbsp;</html:radio>
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
        <html:submit property="action"
              onclick="SetAndSubmit('','','Search')"
              styleClass='text'>
              <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
	<%prop=jspFormNestProperty+".showInactiveFl";%>
	<app:storeMessage key="locateAccount.label.showInactive"/>: <html:checkbox property="<%=prop%>"/>
   </td>
  </tr>
</table>


<!-- logic:present name="locateStoreAccountForm.accounts" -->
<% if(theForm.getAccountSearchResult()!=null) {
 int rescount = theForm.getAccountSearchResult().size();
%>
<app:storeMessage key="locateAccount.label.searchResultCount"/>:
<% if ( rescount >= Constants.MAX_ACCOUNTS_TO_RETURN ){ %>
(request limit)
<%}%>
<%=rescount%>
<% if(rescount>0) { %>

<table ID=326 width="750" border="0" class="results">
<tr align=left>
<td><a ID=327 class="tableheader"><app:storeMessage key="locateAccount.text.accountId"/></td>
<td><a ID=328 class="tableheader"><app:storeMessage key="locateAccount.text.name"/> </a></td>
<td>
<a ID=329 href="javascript:SetChecked(1)"><app:storeMessage key="admin2.label.selectCheckAll"/></a><br>
<a ID=330 href="javascript:SetChecked(0)"><app:storeMessage key="admin2.label.clear"/></a>
</td>
<td><a ID=331 class="tableheader"><app:storeMessage key="locateAccount.text.city"/></a></td>
<td><a ID=332 class="tableheader"><app:storeMessage key="locateAccount.text.stateProvince"/></a></td>
<td><a ID=333 class="tableheader"><app:storeMessage key="locateAccount.text.type"/></a></td>
<td><a ID=334 class="tableheader"><app:storeMessage key="locateAccount.text.status"/></a></td>
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
    <% String linkHref = "javascript: SetAndSubmit ('"+selectBoxProp+"',"+key+", 'Return Selected');";%>

<tr>
<td><bean:write name="arrele" property="accountId"/></td>
<td><a ID=335 href="<%=linkHref%>"><bean:write name="arrele" property="shortDesc"/></a></td>
<td><html:multibox property="<%=selectBoxProp%>" value="<%=key.toString()%>"/></td>
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
