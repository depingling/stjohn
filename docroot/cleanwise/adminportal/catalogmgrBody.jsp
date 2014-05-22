<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">
  <table cellspacing="0" border="0" width="769" class="mainbody">
<html:form name="CATALOG_SEARCH_FORM" 
action="/adminportal/catalogmgr.do" focus="searchField" 
scope="session" 
type="com.cleanwise.view.forms.CatalogMgrSearchForm">

  <tr> <td>
<b>Find Catalog:</b></td>
     <td colspan="3">
 	  <html:text name="CATALOG_SEARCH_FORM" property="searchField"/>
     </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio name="CATALOG_SEARCH_FORM" property="searchType" value="catalogId" />
         ID
         <html:radio name="CATALOG_SEARCH_FORM" property="searchType" value="catalogNameStarts" />
         Name(starts with)
         <html:radio name="CATALOG_SEARCH_FORM" property="searchType" value="catalogNameContains" />
         Name(contains)
         </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
       <html:submit property="action" value="Search"/>
       <html:submit property="action" value="View All Catalogs"/>
       <html:submit property="action" value="Create New"/>
    </td>
  </tr>
</html:form>
</table>

<div>
<table cellspacing="0" border="0" width="769" class="results">
<tr>
<td colspan=4>Search Results Count: 
<bean:write name="CATALOG_SEARCH_FORM" property="listCount"/> 
</td>
</tr>
<logic:greaterThan name="CATALOG_SEARCH_FORM" property="listCount" value="0">


<tr align=left>
<td><a class="tableheader" href="catalogmgr.do?action=sort&sortField=id">Id </td>
<td><a class="tableheader" href="catalogmgr.do?action=sort&sortField=name">Name </td>
<td><a class="tableheader" href="catalogmgr.do?action=sort&sortField=type">Type </td>
<td><a class="tableheader" href="catalogmgr.do?action=sort&sortField=status">Status </td>
</tr>
   <bean:define id="pagesize" name="CATALOG_SEARCH_FORM" property="listCount"/>
   <logic:iterate id="catalog" name="CATALOG_SEARCH_FORM" property="resultList"
    offset="0" length="<%=pagesize.toString()%>" type="com.cleanwise.service.api.value.CatalogData">
    <bean:define id="key"  name="catalog" property="catalogId"/>
    <bean:define id="name" name="catalog" property="shortDesc"/>
    <% String linkHref = new String("catalogdetail.do?action=edit&id=" + key);%>
    <tr>
  <td><bean:write name="catalog" property="catalogId"/></td>
  <td><html:link href="<%=linkHref%>"><bean:write name="catalog" property="shortDesc" filter="true"/></html:link></td>
  <td><bean:write name="catalog" property="catalogTypeCd"/></td>
  <td><bean:write name="catalog" property="catalogStatusCd"/></td>
 </tr>

 </logic:iterate>

 <tr align=center>
 <td colspan="4">&nbsp;</td>
 </tr>
 </table>
 </logic:greaterThan>

</div>
</div>


