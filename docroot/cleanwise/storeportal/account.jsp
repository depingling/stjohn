<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<SCRIPT TYPE="text/javascript">
<!--
function submitenter(myfield,e)
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
else return true;

if (keycode == 13)
   {
   var s=document.forms[0].elements['search'];
   if(s!=null) s.click();
   return false;
   }
else
   return true;
}

//-->
</SCRIPT>
<bean:define id="theForm" name="STORE_ADMIN_ACCOUNT_FORM"
  type="com.cleanwise.view.forms.StoreAccountMgrForm"/>
<html:form styleId="204" name="STORE_ADMIN_ACCOUNT_FORM"
	action="/storeportal/accountsearch"  scope="session">
<table ID=205 width="<%=Constants.TABLEWIDTH%>" border="0"  class="mainbody">
<tr>
    <td align="right"><b>Find Account</b></td>
	<td>
		<html:text  styleId="mainSearchField" property="searchField"
                  onkeypress="return submitenter(this,event)"/>
		<html:radio property="searchType" value="id" />  ID
		<html:radio property="searchType" value="nameBegins" />  Name(starts with)
		<html:radio property="searchType" value="nameContains" />  Name(contains)
	</td>
</tr>
<tr>
    <td align="right"><b>Reference Number</b></td>
	<td>
		<html:text  styleId="mainSearchField" property="searchRefNum" />
		<html:radio property="refNumSearchType" value="nameBegins" />  (starts with)
		<html:radio property="refNumSearchType" value="nameContains" />  (contains)
	</td>
</tr>

  <%
SessionTool st = new SessionTool(request);
CleanwiseUser appUser = st.getUserData();
GroupDataVector accountGroups =  theForm.getAccountGroups();

if(accountGroups!=null && accountGroups.size()>0
       && (appUser != null)
     ) {
  %>


  <tr><td align=right><b>Groups</b></td><td>

   <html:radio property="searchGroupId" value='0'>None&nbsp;</html:radio><br>
  <% for (Iterator iter=accountGroups.iterator(); iter.hasNext();) {
     GroupData gD = (GroupData) iter.next();
     String grIdS = ""+gD.getGroupId();
     String grName = gD.getShortDesc();
  %>
   <html:radio property="searchGroupId" value='<%=grIdS%>'><%=grName%>&nbsp;
   </html:radio><br>
  <% } %>
</td>
<tr>
  <% }  /* end of groups */ %>


<td></td>  <td colspan="3">
        <html:submit styleId="search"  property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.create"/>
        </html:submit>
         Show Inactive <html:checkbox property="showInactiveFl" value="true" />
    </td>
</tr>
</table>
  </html:form>



<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.getElementById("mainSearchField");
  if (focusControl != undefined && focusControl.type != "hidden" && !focusControl.disabled) {
     focusControl.focus();
  }
  // -->
</script>

<logic:present name="Account.search.result">
<bean:size id="rescount"  name="Account.search.result"/>
Count:<bean:write name="rescount" />
<% if ( rescount.intValue() >= Constants.MAX_ACCOUNTS_TO_RETURN ){ %>
(request limit)
<%}%>

<logic:greaterThan name="rescount" value="0">

<table class="stpTable_sortable" id="ts1">
<thead>
<tr class=stpTH>
<th class="stpTH">Account&nbsp;Id</a></th>
<th class="stpTH">Erp#</th>
<th class="stpTH">Name</th>
<th class="stpTH">City</th>
<th class="stpTH">State/Province</th>
<th class="stpTH">Type</th>
<th class="stpTH">Status</th>
</tr>
</thead>

<tbody>
<logic:iterate id="arrele" name="Account.search.result"
  type="com.cleanwise.service.api.value.AccountSearchResultView">
<tr class=stpTD>
<td class=stpTD><bean:write name="arrele" property="accountId"/></td>
<td class=stpTD><bean:write name="arrele" property="erpNum"/></td>
<td class=stpTD>

<bean:define id="eleid" name="arrele" property="accountId"/>
<a ID=206 href="storeAccountDetail.do?action=accountdetail&accountId=<%=eleid%>">

<bean:write name="arrele" property="shortDesc"/>
</a>
</td>
<td class=stpTD><bean:write name="arrele" property="city"/></td>
<td class=stpTD><bean:write name="arrele" property="stateProvinceCd"/></td>
<td class=stpTD><bean:write name="arrele" property="value"/></td>
<td class=stpTD><bean:write name="arrele" property="busEntityStatusCd"/></td>
</tr>

</logic:iterate>
</tbody>
</table>

</logic:greaterThan>
</logic:present>

</div>

<br><br>

