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

%>

<script language="JavaScript1.2">
<!--

function passIdAndName(id, name) {
	var feedBackFieldName = document.CATALOG_SEARCH_FORM.feedField.value;
	var feedDesc = document.CATALOG_SEARCH_FORM.feedDesc.value;

	if(feedBackFieldName && ""!= feedBackFieldName) {
  	  window.opener.document.forms[0].elements[feedBackFieldName].value = id;
    }
	if(feedDesc && ""!= feedDesc) {
  	  window.opener.document.forms[0].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
    }
	self.close();
	return true;
}

//-->
</script>




  <table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
  <tr> <td><b>Find Catalog:</b></td>
       <td colspan="3">
			<html:text name="CATALOG_SEARCH_FORM" property="searchField"/>
			<input type="hidden" name="feedField" value="<%=feedField%>">
            <input type="hidden" name="feedDesc" value="<%=feedDesc%>">
       </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
         <html:radio name="CATALOG_SEARCH_FORM" property="searchType" value="catalogId" />
         ID
         <html:radio name="CATALOG_SEARCH_FORM" property="searchType" value="catalogNameStarts" />
         Name(starts with)
         <html:radio name="CATALOG_SEARCH_FORM" property="searchType" value="catalogNameContains" />
         Name(contains)
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
       <html:submit property="action" value="Search"/>
       <html:submit property="action" value="View All Catalogs"/>
    </td>
  </tr>
</table>

Search result count: 
<bean:write name="CATALOG_SEARCH_FORM" property="listCount" />
<logic:greaterThan name="CATALOG_SEARCH_FORM" property="listCount" value="0">



<table border="0" cellpadding="1" cellspacing="0" width="769" class="results">
<tr align=center>
<td><a class="tableheader" href="cataloglocate.do?action=sort&sortField=id&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Catalog Id </td>
<td><a class="tableheader" href="cataloglocate.do?action=sort&sortField=name&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Name </td>
<td><a class="tableheader" href="cataloglocate.do?action=sort&sortField=type&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Type </td>
<td><a class="tableheader" href="cataloglocate.do?action=sort&sortField=status&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>">Status </td>
</tr>
   <logic:iterate id="catalog" name="CATALOG_SEARCH_FORM" property="resultList">
    <bean:define id="key"  name="catalog" property="catalogId"/>
    <bean:define id="name" name="catalog" property="shortDesc" type="String"/>
    <% String linkHref = new String("catalogdetail.do?action=edit&id=" + key);%>
    <tr>
    <td><bean:write name="catalog" property="catalogId" filter="true"/></td>
    <td>
    <% String onClick = new String("return passIdAndName('"+key+"', '"+ java.net.URLEncoder.encode(name) +"');");%>
    <a href="javascript:void(0);" onclick="<%=onClick%>">
    <bean:write name="catalog" property="shortDesc"/>
    </a>
    </td>
  <td><bean:write name="catalog" property="catalogTypeCd" filter="true"/></td>
  <td><bean:write name="catalog" property="catalogStatusCd" filter="true"/></td>
 </tr>

 </logic:iterate>
 
</table>

 </logic:greaterThan>

<tr align=center>
<td colspan="4">&nbsp;</td>
</tr>


