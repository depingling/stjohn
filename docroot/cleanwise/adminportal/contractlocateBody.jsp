<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
	String feedField =  (String)request.getParameter("feedField");
	if(null == feedField) {
		feedField = new String("");
	}
  	String feedDesc =  (String)request.getParameter("feedDesc");
	if(null == feedDesc) {
		feedDesc = new String("");
	}

  	String reload =  (String)request.getParameter("reload");
	if(null == reload) {
		reload = new String("");
	}
	
	String catalogId = (String)request.getParameter("catalogId");
	if(null == catalogId) {
		catalogId = new String("");
	}
%>

<script language="JavaScript1.2">
<!--

function passIdAndName(id, name, reload) {

	var feedBackFieldName = document.CONTRACT_SEARCH_FORM.feedField.value;
	var feedDesc = document.CONTRACT_SEARCH_FORM.feedDesc.value;

	if(feedBackFieldName && ""!= feedBackFieldName) {
  	  window.opener.document.forms[0].elements[feedBackFieldName].value = id;
    }
	if(feedDesc && ""!= feedDesc) {
  	  window.opener.document.forms[0].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
    }

<% if ( ! "".equals(reload)) {  %>
      
	  window.opener.document.forms[0].elements["viewMode"].value= reload;	
	  window.opener.document.forms[0].submit();	
<% } %>	

	self.close();
	
	return true;
}

//-->
</script>


<div class="text">

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">

<html:form name="CONTRACT_SEARCH_FORM" action="/adminportal/contractlocate.do" focus="searchField"
    scope="session" type="com.cleanwise.view.forms.ContractMgrSearchForm">


  <tr> <td><b>Find Contracts:</b></td>
       <td colspan="3"> 
			<html:text name="CONTRACT_SEARCH_FORM" property="searchField"/>	
			<input type="hidden" name="feedField" value="<%=feedField%>">			
            <input type="hidden" name="feedDesc" value="<%=feedDesc%>">
		    <input type="hidden" name="catalogId" value="<%=catalogId%>">
                    <input type="hidden" name="reload" value="<%=reload%>">                    
       </td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio name="CONTRACT_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="CONTRACT_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="CONTRACT_SEARCH_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>  
  
  <tr> <td>&nbsp;</td>
       <td colspan="3">
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
     </td>
  </tr>
  <tr><td colspan="4">&nbsp;</td>
  </tr>

</table>

Search results count:&nbsp;<bean:write name="CONTRACT_SEARCH_FORM" property="listCount" filter="true"/>

<logic:greaterThan name="CONTRACT_SEARCH_FORM" property="listCount" value="0">

<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a class="tableheader" href="contractlocate.do?action=sort&sortField=id&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&catalogId=<%=catalogId%>">Contract&nbsp;Id </td>
<td><a class="tableheader" href="contractlocate.do?action=sort&sortField=name&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&catalogId=<%=catalogId%>">Contract Name </td>
<td><a class="tableheader" href="contractlocate.do?action=sort&sortField=catalog&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&catalogId=<%=catalogId%>">Catalog Name </td>
<td><a class="tableheader" href="contractlocate.do?action=sort&sortField=status&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&catalogId=<%=catalogId%>">Contract Status </td>
</tr>

<logic:iterate id="contract" name="CONTRACT_SEARCH_FORM" property="resultList" >
 <bean:define id="key"  name="contract" property="contractId"/>
 <bean:define id="rel" value="<%=reload%>" />
 <bean:define id="name" name="contract" property="contractName" type="String"/> 
 <bean:define id="catalogkey"  name="contract" property="catalogId" />
 <% String catalogkeyS = catalogkey.toString();
 	if ( "".equals(catalogId) || catalogId.equals(catalogkeyS) ) {  
    String linkHref = new String("contractdetail.do?action=edit&id=" + key);%>
 <tr>
  <td><bean:write name="contract" property="contractId" filter="true"/></td>
  <td>
    <% String onClick = new String("return passIdAndName('"+key+"','"+ java.net.URLEncoder.encode(name) +"','"+rel+"');");%>
    <a href="javascript:void(0);" onclick="<%=onClick%>">
    <bean:write name="contract" property="contractName" filter="true"/>     
    </a>

  </td>
  <td><bean:write name="contract" property="catalogName" filter="true"/></td>
  <td><bean:write name="contract" property="status" filter="true"/></td>
 </tr>
 <% }  %> 
 
 </logic:iterate>

 <tr>
 	<td colspan="4">&nbsp;	</td>
 </tr>
	  
</table>
</logic:greaterThan>

</html:form>  

</div>




