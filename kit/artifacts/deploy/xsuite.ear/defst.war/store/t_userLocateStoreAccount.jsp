<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.LocateStoreAccountForm" %>
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
  if(jspFormName == null) {
    throw new RuntimeException("jspFormName cannot be null");
  }
  
  //LocateStoreAccountForm theForm = null;
  String jspFormNestProperty = request.getParameter("jspFormNestProperty");
  if(jspFormNestProperty != null) {
    jspFormNestProperty = jspFormNestProperty + ".locateStoreAccountForm";
  } else {
    jspFormNestProperty = "locateStoreAccountForm";
  }  
  %>
  <logic:present name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" >
  <bean:define id="theForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" type="LocateStoreAccountForm"/>
  
  <%
  if(theForm != null && theForm.getLocateAccountFl()) {
    String jspFormAction = request.getParameter("jspFormAction");  
  if(jspFormAction == null) {
    throw new RuntimeException("jspFormAction cannot be null");
  }
  String jspSubmitIdent = request.getParameter("jspSubmitIdent");
  if(jspSubmitIdent == null) {
    throw new RuntimeException("jspSubmitIdent cannot be null");
  }
  String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
  if(jspReturnFilterProperty == null) {
    throw new RuntimeException("jspReturnFilterProperty cannot be null");
  }
  jspSubmitIdent += "#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM;

%>



<script src="../externals/lib.js" language="javascript"></script>
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

function SetAndSubmit (form_id, val) {
  var dml = document.getElementById(form_id);
  var name = '<%=jspFormNestProperty%>.selected';
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
  actionSubmit(form_id,'Return Selected');
}

//-->
</script>

<html>
<body>

<html:form styleId="22222" action="<%=jspFormAction%>"  scope="session">
    <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
        <%String prop=jspFormNestProperty+".property";%>
    <html:hidden property="<%=prop%>" value="<%=jspReturnFilterProperty%>"/>
        <%prop=jspFormNestProperty+".name";%>
    <html:hidden property="<%=prop%>" value="<%=jspFormName%>"/>

<div style="border:#D3D3D3 1px solid;
            text-align: center;
            padding: 0px 8px 10px 8px;">
    Find Accounts
<table ID="324" align="center" class="mainbody" cellspacing="0" cellpadding="2" width="97%">
    <tr>
        <td width="15%" align="right">
            <b><app:storeMessage key="userlocate.site.text.accountName"/>:</b>
        </td>
        <td width="30%">
            <%prop=jspFormNestProperty+".searchField";%>
            <html:text property="<%=prop%>" onkeypress="javascript:enterKey(event,'22222','Search');"/>
        </td>
        <td align="left">
        <%prop=jspFormNestProperty+".searchType";%>
        <html:radio property="<%=prop%>" value="id" />
            ID&nbsp;
        <html:radio property="<%=prop%>" value="nameBegins" />
            Name(starts with)&nbsp;
        <html:radio property="<%=prop%>" value="nameContains" />
            Name(contains)
        </td>
    </tr>
  
    <% GroupDataVector accountGroups =  theForm.getAccountGroups();
        if (accountGroups != null && accountGroups.size() > 0) { %>
            <tr>
                <td width="15%" align="right">
                    <b>Groups:</b>
                </td>
                <td width="30%" align="left" colspan="2">
                    <% prop = jspFormNestProperty + ".searchGroupId"; %>
                    <html:radio property='<%=prop%>' value='0'>None</html:radio>
                </td>
            </tr>
            <% for (Iterator iter = accountGroups.iterator(); iter.hasNext();) { %>
                <tr>
                    <td width="15%" align="right">&nbsp;
                    </td>
                    <td width="30%" align="left" colspan="2">
                        <%
                            GroupData gD = (GroupData) iter.next();
                            String grIdS = "" + gD.getGroupId();
                            String grName = gD.getShortDesc();
                        %>
                        <html:radio property='<%=prop%>' value='<%=grIdS%>'><%=grName%></html:radio>
                    </td>
                </tr>
            <% } %>
    <% } %>
    
    <tr>
        <td width="15%">&nbsp;</td>
        <td width="30%" align="left">
            <html:button property="action" styleClass="store_fb" onclick="actionSubmit('22222','Search');">
                <app:storeMessage key="global.action.label.search"/>
            </html:button>
            <%prop=jspFormNestProperty+".showInactiveFl";%>
            &nbsp;
            <html:checkbox property="<%=prop%>"/>
            Show Inactive
        </td>
        <td align="left">
            <html:button property="action" styleClass="store_fb" onclick="actionSubmit('22222','Cancel');">
                <app:storeMessage key="global.action.label.cancel"/>
            </html:button>
            &nbsp;
            <html:button property="action" styleClass="store_fb" onclick="actionSubmit('22222','Return Selected');">
                <app:storeMessage key="userlocate.account.text.returnSelected"/>
            </html:button>
        </td>
    </tr>
</table>

<!-- logic:present name="locateStoreAccountForm.accounts" -->
    <% if(theForm.getAccountSearchResult() != null) {     
        int rescount = theForm.getAccountSearchResult().size();
    %>
        Search result count: <%=rescount%> 
        <% if ( rescount >= Constants.MAX_ACCOUNTS_TO_RETURN ){ %>
        (request limit)
        <%}%>

        <% if(rescount > 0) { %>
            <table ID="326" align="center" border="0" width="97%" style="background-color: #E4F3CF">
                <tr align="left">
                    <td><a ID="327" class="tableheader">Account Id</td>
                    <td><a ID="328" class="tableheader">Name </a></td>
                    <td>
                        <a ID="329" href="javascript:SetChecked(1)">[Check&nbsp;All]</a><br>
                        <a ID="330" href="javascript:SetChecked(0)">[&nbsp;Clear]</a>
                    </td>
                    <td><a ID="331" class="tableheader">City</a></td>
                    <td><a ID="332" class="tableheader">State/Province</a></td>
                    <td><a ID="333" class="tableheader">Type</a></td>
                    <td><a ID="334" class="tableheader">Status</a></td>
                </tr>
                
                <%
                  String propName = jspFormNestProperty + ".accountSearchResult";
                  prop = jspFormNestProperty+".accountSearchResult";
                  String selectBoxProp = jspFormNestProperty+".selected";
                %>
                
                <logic:iterate id="arrele" name="<%=jspFormName%>" property="<%=prop%>" 
                    type="com.cleanwise.service.api.value.AccountSearchResultView">
                <bean:define id="key"  name="arrele" property="accountId"/>
                <bean:define id="name" name="arrele" property="shortDesc" type="String"/>
                <% String linkHref = "javascript: SetAndSubmit ('22222', "+key+");";%>

                    <tr>
                        <td><bean:write name="arrele" property="accountId"/></td>
                        <td><a ID="335" href="<%=linkHref%>"><bean:write name="arrele" property="shortDesc"/></a></td>
                        <td><html:multibox property="<%=selectBoxProp%>" value="<%=key.toString()%>"/></td>
                        <td><bean:write name="arrele" property="city"/></td>
                        <td><bean:write name="arrele" property="stateProvinceCd"/></td>
                        <td><bean:write name="arrele" property="value"/></td>
                        <td><bean:write name="arrele" property="busEntityStatusCd"/></td>
                    </tr>
                    
                </logic:iterate>
            </table>
        <% } %>
    <% } %>
    
<!-- /logic:present -->

</div>

    <html:hidden property="action" value="hiddenAction"/>
    <html:hidden property="action" value="Search"/>
    <html:hidden property="tabs" value="f_userAssetToolbar"/>
    <html:hidden property="display" value="t_userAssetSearch"/>
</html:form>

<body>
<html>

<% } %>
</logic:present>

<%--main form--%>