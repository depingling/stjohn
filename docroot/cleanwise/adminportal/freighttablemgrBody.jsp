<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">

<html:form name="FREIGHT_TABLE_SEARCH_FORM" action="/adminportal/freighttablemgr.do" focus="searchField"
    scope="session" type="com.cleanwise.view.forms.FreightTableMgrSearchForm">


  <tr> <td><b>Find Freight Tables:</b></td>
       <td colspan="3"> 
			<html:text name="FREIGHT_TABLE_SEARCH_FORM" property="searchField"/>	
       </td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio name="FREIGHT_TABLE_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="FREIGHT_TABLE_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="FREIGHT_TABLE_SEARCH_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>  
  
  <tr> <td>&nbsp;</td>
       <td colspan="3">
       <html:hidden property="action" value="search"/>
       <html:submit property="action" value="Search"/>
	   <html:button property="act" onclick="document.location='freighttablemgr.do?action=viewall';">
			<app:storeMessage  key="admin.button.viewall"/>
		</html:button>
	   	<html:button property="act" onclick="document.location='freighttabledetail.do?action=add';">
			<app:storeMessage  key="admin.button.create"/>
		</html:button>	   
     </td>
  </tr>
  <tr><td colspan="4">&nbsp;</td>
  </tr>

</html:form>  
</table>

Search results count:&nbsp;<bean:write name="FREIGHT_TABLE_SEARCH_FORM" property="listCount" filter="true"/>

<logic:greaterThan name="FREIGHT_TABLE_SEARCH_FORM" property="listCount" value="0">

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a class="tableheader" href="freighttablemgr.do?action=sort&sortField=id">Freight Table&nbsp;Id </td>
<td><a class="tableheader" href="freighttablemgr.do?action=sort&sortField=name">Freight Table Name </td>
<td><a class="tableheader" href="freighttablemgr.do?action=sort&sortField=type">Freight Table Type </td>
<td><a class="tableheader" href="freighttablemgr.do?action=sort&sortField=status">Freight Table Status </td>
</tr>

 <bean:define id="pagesize" name="FREIGHT_TABLE_SEARCH_FORM" property="listCount"/>
	  
<logic:iterate id="freightTable" name="FREIGHT_TABLE_SEARCH_FORM" property="resultList"
     offset="0" length="<%=pagesize.toString()%>" type="com.cleanwise.service.api.value.FreightTableData"> 
 <pg:item>
 <bean:define id="key"  name="freightTable" property="freightTableId"/>
 <bean:define id="name" name="freightTable" property="shortDesc"/>
 <% String linkHref = new String("freighttabledetail.do?action=edit&id=" + key);%>
 <tr>
  <td><bean:write name="freightTable" property="freightTableId" filter="true"/></td>
  <td><html:link href="<%=linkHref%>"><bean:write name="freightTable" property="shortDesc" filter="true"/></html:link></td>
  <td><bean:write name="freightTable" property="freightTableTypeCd" filter="true"/></td>
  <td><bean:write name="freightTable" property="freightTableStatusCd" filter="true"/></td>
 </tr>
 </pg:item>
 </logic:iterate>
	  
</table>
  <pg:index>
    Result Pages:
    <pg:prev>&nbsp;<a href="<%= pageUrl %>">[<< Prev]</a></pg:prev>
    <pg:pages><%
      if (pageNumber.intValue() < 10) { 
        %>&nbsp;<%
      }
      if (pageNumber == pagerPageNumber) { 
        %><b><%= pageNumber %></b><%
      } else { 
        %><a href="<%= pageUrl %>"><%= pageNumber %></a><%
      }
    %>
    </pg:pages>
    <pg:next>&nbsp;<a href="<%= pageUrl %>">[Next >>]</a></pg:next>
    <br></font>
  </pg:index>
</pg:pager>
</logic:greaterThan>

</div>




