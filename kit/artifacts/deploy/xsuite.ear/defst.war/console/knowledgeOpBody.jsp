<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}
//-->
</script>

<div class="text">

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">

<html:form name="KNOWLEDGE_OP_SEARCH_FORM" action="/console/knowledgeOp.do" focus="productName"
    scope="session" type="com.cleanwise.view.forms.KnowledgeOpSearchForm">


  <tr> <td colspan="2"><b>Search Knowledge Base:</b></td>
       <td colspan="3">&nbsp;</td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Product Name:</b></td>
	   <td> 
			<html:text name="KNOWLEDGE_OP_SEARCH_FORM" property="productName" />
       </td>
       <td><b>Sku:</b></td>
	   <td> 
			<html:text name="KNOWLEDGE_OP_SEARCH_FORM" property="skuNum" />
       </td>	   
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Description:</b></td>
	   <td> 
			<html:text name="KNOWLEDGE_OP_SEARCH_FORM" property="description" />
       </td>
       <td><b>Category:</b></td>
	   <td> 
			<html:select name="KNOWLEDGE_OP_SEARCH_FORM" property="categoryCd">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:options  collection="Knowledge.category.vector" property="value" />
			</html:select>			
       </td>	   
  </tr>  
  
  <tr> <td>&nbsp;</td>
       <td><b>Distributor ID:</b></td>
	   <td colspan="3"> 
			<html:text name="KNOWLEDGE_OP_SEARCH_FORM" property="distributorId" />
			<html:button property="action"
   				onclick="popLocate('../adminportal/distlocate', 'distributorId', '');"
   				value="Locate Distributor"/>
       </td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Manufacturer ID:</b></td>
	   <td colspan="3"> 
			<html:text name="KNOWLEDGE_OP_SEARCH_FORM" property="manufacturerId" />
			<html:button property="action"
   				onclick="popLocate('../adminportal/manuflocate', 'manufacturerId', '');"
   				value="Locate Manufacturer"/>
       </td>
  </tr>
  
  
  <tr> <td>&nbsp;</td>
       <td><b>Date:</b><br>(mm/dd/yyyy)</td>
	   <td colspan="3"> 
	   		Begin Date Range
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			End Date Range<br>
			<html:text name="KNOWLEDGE_OP_SEARCH_FORM" property="dateRangeBegin" /></span>
			
			<html:text name="KNOWLEDGE_OP_SEARCH_FORM" property="dateRangeEnd" /></span>
	   </td>
  </tr>

  <tr>
       <td colspan="5" align="center">
       <html:submit property="action" value="Search"/>
	   <html:submit property="action" value="My Pending" />
     </td>
  </tr>
  <tr><td colspan="5">&nbsp;</td>
  </tr>

</html:form>  
</table>

Search results count:&nbsp;<bean:write name="KNOWLEDGE_OP_SEARCH_FORM" property="listCount" filter="true"/>

<logic:greaterThan name="KNOWLEDGE_OP_SEARCH_FORM" property="listCount" value="0">

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a href="knowledgeOp.do?action=sort&sortField=category"><b>Category</b></a></td>
<td class="resultscolumna"><a href="knowledgeOp.do?action=sort&sortField=productname"><b>Product Name</b></a></td>
<td><a href="knowledgeOp.do?action=sort&sortField=sku"><b>SKU</b></a></td>
<td class="resultscolumna"><a href="knowledgeOp.do?action=sort&sortField=desc"><b>Description</b></a></td>
<td><a href="knowledgeOp.do?action=sort&sortField=date"><b>Date</b></a></td>
<td class="resultscolumna"><a href="knowledgeOp.do?action=sort&sortField=status"><b>Status</b></a></td>
<td><a href="knowledgeOp.do?action=sort&sortField=createdby"><b>Created By</b></a></td>
</tr>

 <bean:define id="pagesize" name="KNOWLEDGE_OP_SEARCH_FORM" property="listCount"/>
	  
<logic:iterate id="knowledgeDesc" name="KNOWLEDGE_OP_SEARCH_FORM" property="resultList"
     offset="0" length="<%=pagesize.toString()%>" type="com.cleanwise.service.api.value.KnowledgeDescData"> 
 <pg:item>
 <bean:define id="key"  name="knowledgeDesc" property="knowledgeDetail.knowledgeId"/>
 <bean:define id="openedDate"  name="knowledgeDesc" property="knowledgeDetail.addDate"/>
 <% String linkHref = new String("knowledgeOpDetail.do?action=view&id=" + key);%>

 <tr>
  <td><bean:write name="knowledgeDesc" property="knowledgeDetail.knowledgeCategoryCd"/></td>
  <td class="resultscolumna"><bean:write name="knowledgeDesc" property="productName"/></td>
  <td><bean:write name="knowledgeDesc" property="skuNum"/></td>
  <td class="resultscolumna"><a href="<%=linkHref%>"><bean:write name="knowledgeDesc" property="knowledgeDetail.longDesc"/></a></td>
  <td><i18n:formatDate value="<%=openedDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/></td>
  <td class="resultscolumna"><bean:write name="knowledgeDesc" property="knowledgeDetail.knowledgeStatusCd"/></td>
  <td><bean:write name="knowledgeDesc" property="knowledgeDetail.addBy"/></td>
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




