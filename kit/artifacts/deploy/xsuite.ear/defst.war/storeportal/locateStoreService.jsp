<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.forms.LocateStoreServiceForm" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page contentType="text/html"%>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
    String storeDir = ClwCustomizer.getStoreDir();
    //hide or show CheckBox <Show Inactive>
    String checkBoxShowInactiveStatus = request.getParameter("checkBoxShowInactive");
    boolean checkBoxShowInactiveFl = true;  //if true show
    if (checkBoxShowInactiveStatus != null)
        if (checkBoxShowInactiveStatus.equalsIgnoreCase("hide"))
            checkBoxShowInactiveFl = false; //hide

    String jspFormName = request.getParameter("jspFormName");
    String jspCatalogListProperty = request.getParameter("jspCatalogListProperty");
    String jspRestrictCatalog = request.getParameter("jspRestrictCatalog");

    boolean showCatalogCheckBox = false;
    boolean restrictCatalogList = false;
    String selectedCatalogList = "";
    if (jspCatalogListProperty != null) {
        CatalogDataVector catalogDV = (CatalogDataVector) session.getAttribute(jspCatalogListProperty);
        if (catalogDV != null) {
            showCatalogCheckBox = catalogDV.size() > 0;
            for (int i = 0; i < catalogDV.size(); i++) {
                if (i > 0) selectedCatalogList += ",";
                selectedCatalogList += ((CatalogData) catalogDV.get(i)).getCatalogId();
            }
        }
        if (jspRestrictCatalog != null) {
            if (jspRestrictCatalog.equalsIgnoreCase("yes")) {
                restrictCatalogList = true;
            }
        }
    }

    if (jspFormName == null) {
        throw new RuntimeException("jspFormName cannot be null");
    }
    LocateStoreServiceForm theForm = null;
    if (session.getAttribute(jspFormName) != null) {
        Object tmpForm = session.getAttribute(jspFormName);
        if (tmpForm instanceof StorePortalForm) {
            theForm = ((StorePortalForm) session.getAttribute(jspFormName)).getLocateStoreServiceForm();
        } else {
            throw new RuntimeException("Bean " + jspFormName + " must be of type StorePortalForm");
        }
    } else {
        throw new RuntimeException("Could not find bean " + jspFormName + " in the session");
    }
    if (theForm != null && theForm.getLocateServiceFl()) {
        String jspSubmitIdent = request.getParameter("jspSubmitIdent");
        String jspFormAction = request.getParameter("jspFormAction");
        String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
        if (jspFormName == null) {
            throw new RuntimeException("jspReturnFilterProperty cannot be null");
        }
        jspSubmitIdent += "#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_SERVICE_FORM;
        if (theForm != null && restrictCatalogList) {
            theForm.setSearchInSelectedCatalogs(true);
        }
  String jspFormNestProperty = request.getParameter("jspFormNestProperty");
  if(jspFormNestProperty != null){
	jspFormNestProperty = jspFormNestProperty + ".locateStoreServiceForm";
  }else{
	jspFormNestProperty = "locateStoreServiceForm";
  }
%>


<html:html>
<script language="JavaScript1.2">
<!--
function SetCheckedService(name, val) {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  found = false;
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name==name) {
      dml[i].elements[j].checked=val;
      found = true;
    }
  }
  if(found) break;
 }
}

function SetAndSubmitService(name, val) {
  var dml=document.forms[0];
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

//-->
</script>



<body>

<script src="../externals/lib.js" language="javascript"></script>

<div class="rptmid">
Find Services


<table ID=383 width="750" border="0"  class="mainbody">
  <html:form styleId="384" action="<%=jspFormAction%>"  scope="session">
  <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
  <html:hidden property="locateStoreServiceForm.property" value="<%=jspReturnFilterProperty%>"/>
  <html:hidden property="locateStoreServiceForm.name" value="<%=jspFormName%>"/>

  <tr>
    <td colspan="3">&nbsp; <html:hidden  property="locateStoreServiceForm.whereToSearch" value="this"/></td>
  </tr>
  <tr>
    <td><b>Short Description:</b></td>
    <td><html:text  property="locateStoreServiceForm.searchField"/>
        <html:radio property="locateStoreServiceForm.searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.ID%>"/>
        Id
        <html:radio property="locateStoreServiceForm.searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
        Name(starts with)
        <html:radio property="locateStoreServiceForm.searchType" value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
        Name(contains)
     </td>
    <td>
    </td>
  </tr>
  <tr>
    <td><b>Category:</b></td>
    <td><html:text  property="locateStoreServiceForm.categoryTempl"/> </td>
  </tr>
      <tr><td>
      <% if (showCatalogCheckBox) { %>
      <html:hidden property="locateStoreServiceForm.selectedCatalogList" value="<%=selectedCatalogList%>"/>
      <b>Search within selected catalogs</b>&nbsp;<html:checkbox property="locateStoreServiceForm.searchInSelectedCatalogs" disabled="<%=restrictCatalogList%>" />
      <%}%>
      &nbsp;
    </td>
   <td><%String prop="locateStoreServiceForm.showInactiveFl";
  if(checkBoxShowInactiveFl){%>
    Show Inactive: <html:checkbox property="<%=prop%>"/>
   <%}%></td>
          
  </tr>
  <tr>
  <tr>
    <td colspan="3"></td>
  </tr>

  <tr>
    <td colspan="3">
       <html:submit property="action" value="Search"/>&nbsp;&nbsp;
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <html:submit property="action" value="Cancel"/>
       <html:submit property="action" value="Return Selected"/></td>
 </tr>
</table>

<!-- logic:present name="locateStoreFreightHandlersForm.FreightHandler" -->
<% if(((LocateStoreServiceForm)theForm).getService()!=null) {
 int rescount = theForm.getService().size();
 %>
Search result count: <%=rescount%>
<% if(rescount>0) { %>
<table ID=385  width="750"  border="0" class="results">
<tr align=left>
<td><a ID=386 class="tableheader">Id</a></td>
<td><a ID=387 class="tableheader">Name</a></td>
<td>
<a ID=388 href="javascript:SetCheckedService(1)">[Check&nbsp;All]</a><br>
<a ID=389 href="javascript:SetCheckedService(0)">[&nbsp;Clear]</a>
</td>
<td><a ID=390 class="tableheader">Status</a></td>
</tr>

<%
  prop=jspFormNestProperty+".service";
  String selectBoxProp = jspFormNestProperty+".selected";

 %>


<logic:iterate id="arrele" name="<%=jspFormName%>" property="<%=prop%>"
       type="com.cleanwise.service.api.value.ServiceView">
       <bean:define id="key"  name="arrele" property="serviceId"/>
       <bean:define id="name" name="arrele" property="serviceName" type="String"/>
    <% String linkHref = "javascript: SetAndSubmitService ('"+selectBoxProp+"',"+key+");";%>

<tr>
<td><bean:write name="arrele" property="serviceId"/></td>
<td><a ID=391 href="<%=linkHref%>"><bean:write name="arrele" property="serviceName"/></a></td>
<td><html:multibox property="<%=selectBoxProp%>" value="<%=key.toString()%>"/></td>
<td><bean:write name="arrele" property="statusCd"/></td>


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
<%}%>