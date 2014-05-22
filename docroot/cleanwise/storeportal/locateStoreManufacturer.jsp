<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.LocateStoreManufForm" %>
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
  if(jspFormName == null){
    throw new RuntimeException("jspFormName cannot be null");
  }
  String jspFormNestProperty = request.getParameter("jspFormNestProperty");
  if(jspFormNestProperty != null){
     jspFormNestProperty = jspFormNestProperty + ".locateStoreManufForm";
  }else{
    jspFormNestProperty = "locateStoreManufForm";
  }
  %>
  <logic:present name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" >
  <bean:define id="theForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" type="LocateStoreManufForm" />
  <%
  if(theForm != null && theForm.getLocateManufFl()){
  String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
  String jspFormAction = request.getParameter("jspFormAction");
  String jspSubmitIdent = request.getParameter("jspSubmitIdent");
  jspSubmitIdent += "#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_MANUF_FORM;
  String jspReturnFilterPropertyAlt = request.getParameter("jspReturnFilterPropertyAlt");
  if(jspReturnFilterPropertyAlt == null){
  jspReturnFilterPropertyAlt = jspReturnFilterProperty;
  }
  boolean isSingleSelect = false;
  String isSingleParam = request.getParameter("isSingleSelect");
  if (isSingleParam != null && isSingleParam.equalsIgnoreCase("true")) {
    isSingleSelect = true;
  }

  String jspReturnFormNumProp = request.getParameter("jspReturnFormNumProperty");
  if (jspReturnFormNumProp == null || jspReturnFormNumProp.length() == 0) {
    jspReturnFormNumProp = "";
  }

// To be Used for Data Werehouse
  String jspDataSourceType = request.getParameter("jspDataSourceType");
  if (jspDataSourceType == null) {
    jspDataSourceType = "";
  }
%>

<html:html>
<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  found = false;
  ellen = dml[i].elements.length;
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='locateStoreManufForm.selected') {
      dml[i].elements[j].checked=val;
      found = true;
    }
  }
  if(found) break;
 }
}

function SetAndSubmit (name, val) {
  var dml=document.forms[0];
  if ( !<%=isSingleSelect%> ) {
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
  } else {
    dml[name].value = val;
  }
  var iiLast = dml['action'].length-1;
  dml['action'][iiLast].value='Return Selected';
  dml.submit();
  return true;
}

function SetManuf(id, name, prop) {
  if ('<%=jspReturnFormNumProp%>' == "") {
    return SetAndSubmit(prop, id);
  }
  var dml=document.forms[1]['returnFormNum'];
  if (dml != null && dml.value != "") {
    var val = parseInt(dml.value, 10);
    var feedField = document.forms[1].feedField.value;
    var fieldDesc = document.forms[1].fieldDesc.value;
    document.forms[val+1][feedField].value = id;
    document.forms[val+1][fieldDesc].value = unescape(name.replace(/\+/g, ' '));
    var actions = document.forms[val+1]['action'];
   for(ii=actions.length-1; ii>=0; ii--) {
    if(actions[ii].value=='hiddenAction') {
      actions[ii].value='Return Selected';
      document.forms[val+1].submit();
     break;
   }
  }
}
 else {
    return SetAndSubmit(prop, id);
  }
}

//-->
</script>



<body>

<script src="../externals/lib.js" language="javascript"></script>

<div class="rptmid">
Find Manufacturers
<table ID=371 width="750" border="0"  class="mainbody">
  <html:form styleId="372" action="<%=jspFormAction%>"  scope="session">

<%  theForm.setDataSourceType(jspDataSourceType);  %>

  <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
  <%String prop=jspFormNestProperty+".property";%>
  <html:hidden property="<%=prop%>" value="<%=jspReturnFilterProperty%>"/>
  <%prop=jspFormNestProperty+".propertyAlt";%>
  <html:hidden property="<%=prop%>" value="<%=jspReturnFilterPropertyAlt%>"/>
  <%prop=jspFormNestProperty+".name";%>
  <html:hidden property="<%=prop%>" value="<%=jspFormName%>"/>


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
  <tr><td colspan='4'>
  <html:submit property="action" value="Search"/>
      <%prop=jspFormNestProperty+".showInactiveFl";%>
  Show Inactive: <html:checkbox property="<%=prop%>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:submit property="action" value="Cancel"/>
  <% if (!isSingleSelect) { %>
  <html:submit property="action" value="Return Selected"/>
 <% } %>
   </td>
  </tr>
</table>


<% if(theForm.getManufacturers()!=null) {
 int rescount = theForm.getManufacturers().size();
%>
Search result count: <%=rescount%>
<% if(rescount>0) { %>

<table ID=373 width="750" border="0" class="results">
<tr align=left>
<% if (!jspDataSourceType.equals("DW")) { %>
<td><a ID=374 class="tableheader">Manuf Id</td>
<% } %>
<td>
<% if (!isSingleSelect) { %>
<a ID=375 href="javascript:SetChecked(1)">[Check&nbsp;All]</a><br>
<a ID=376 href="javascript:SetChecked(0)">[&nbsp;Clear]</a>
<% } %>
</td>
<td><a ID=377 class="tableheader">Name </a></td>
<td><a ID=378 class="tableheader">Address</a></td>
<td><a ID=379 class="tableheader">City</a></td>
<td><a ID=380 class="tableheader">State/Province</a></td>
<td><a ID=381 class="tableheader">Status</a></td>
</tr>


      <%prop=jspFormNestProperty+".manufacturers";%>
    <logic:iterate id="arrele" name="<%=jspFormName%>" property="<%=prop%>"
       type="com.cleanwise.service.api.value.ManufacturerData">

    <bean:define id="key"  name="arrele" property="busEntity.busEntityId"/>
    <bean:define id="name" name="arrele" property="busEntity.shortDesc" type="String"/>

    <%prop=jspFormNestProperty+".selected";%>
    <%String linkHref = "return SetManuf ("+key+", '" + java.net.URLEncoder.encode(name) + "', '"+prop+"');"; %>


<tr>
 <% if (!jspDataSourceType.equals("DW")) { %>
 <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
 <%}%>
      <%prop=jspFormNestProperty+".selected";%>
<td>
  <% if (!isSingleSelect) { %>
  <html:multibox property="<%=prop%>" value="<%=key.toString()%>"/>
  <% } %>
</td>
<td><a ID=382 href="#" onclick="<%=linkHref%>"><bean:write name="arrele" property="busEntity.shortDesc"/></a></td>
<td><bean:write name="arrele" property="primaryAddress.address1"/></td>
<td><bean:write name="arrele" property="primaryAddress.city"/></td>
<td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</tr>
</logic:iterate>
<% if (isSingleSelect) { %>
  <html:hidden  property="<%=prop%>" value=""/>
<% } %>
</table>


<%}%>
<%}%>

  <html:hidden  property="action" value="Search"/>

</html:form>

</div>

</body>

</html:html>

<%}//if(theForm.getLocateManufFl())%></logic:present>
