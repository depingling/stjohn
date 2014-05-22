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

<html:form name="CALL_OP_SEARCH_FORM" action="/console/callOp.do" focus="accountName"
    scope="session" type="com.cleanwise.view.forms.CallOpSearchForm">


  <tr> <td><b>Search Calls:</b></td>
       <td colspan="4">&nbsp;</td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Account Name:</b></td>
	   <td colspan="3"> 
			<html:text name="CALL_OP_SEARCH_FORM" property="accountName" />
			<html:button property="action"
   				onclick="popLocate('../adminportal/accountlocate', '', 'accountName');"
   				value="Locate Account"/>
       </td>
  </tr>
  
  <tr> <td>&nbsp;</td>
   <td><b>Account(s)</b></td>
       <td colspan='3'>
       <% String onKeyPress="return submitenter(this,event,'Submit');"; %>
       <% String onClick = "popLocate('../adminportal/accountLocateMulti', 'accountIdList', '');";%>
       <html:text size='50' onkeypress="<%=onKeyPress%>" name="CALL_OP_SEARCH_FORM"   
             property="accountIdList" />
       <html:button property="locateAccount"
                    onclick="<%=onClick%>"
                    value="Locate Account(s)"/>
        </td>
  
  </tr>


  
  <tr> <td>&nbsp;</td>
       <td><b>Site Name:</b></td>
	   <td colspan="3"> 
			<html:text name="CALL_OP_SEARCH_FORM" property="siteName" />
			<html:button property="action"
   				onclick="popLocate('../adminportal/sitelocate', '', 'siteName');"
   				value="Locate Site"/>			
       </td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Order Date:</b><br>(mm/dd/yyyy)</td>
	   <td colspan="3"> 
	   		Begin Date Range
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			End Date Range<br>
			<html:text name="CALL_OP_SEARCH_FORM" property="orderDateRangeBegin" /></span>
			
			<html:text name="CALL_OP_SEARCH_FORM" property="orderDateRangeEnd" /></span>
	   </td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Contact Name:</b></td>
	   <td> 
			<html:text name="CALL_OP_SEARCH_FORM" property="contactName" />
       </td>
       <td><b>Site Zip Code:</b></td>
	   <td> 
			<html:text name="CALL_OP_SEARCH_FORM" property="siteZipCode" />
       </td>	   
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Contact Phone:</b></td>
	   <td> 
			<html:text name="CALL_OP_SEARCH_FORM" property="contactPhone" />
       </td>
       <td><b>ERP Order #:</b></td>
	   <td> 
			<html:text name="CALL_OP_SEARCH_FORM" property="erpOrderNum" />
       </td>
  </tr>
    
  <tr> <td>&nbsp;</td>
       <td><b>Contact Email:</b></td>
	   <td> 
			<html:text name="CALL_OP_SEARCH_FORM" property="contactEmail" />
       </td>
       <td><b>Web Order # / Confirmation #:</b></td>
	   <td> 
			<html:text name="CALL_OP_SEARCH_FORM" property="webOrderConfirmationNum" />
       </td>	   
  </tr>
    
  <tr> <td>&nbsp;</td>
       <td><b>Product Name:</b></td>
	   <td> 
			<html:text name="CALL_OP_SEARCH_FORM" property="productName" />
       </td>
       <td><b>Customer PO #:</b></td>
	   <td> 
			<html:text name="CALL_OP_SEARCH_FORM" property="custPONum" />
       </td>
  </tr>
	
  <tr> <td>&nbsp;</td>
       <td><b>Customer Field 1:</b></td>
	   <td> 
			<html:text name="CALL_OP_SEARCH_FORM" property="customerField1" />
       </td>
       <td><b>Outbound PO #:</b></td>
	   <td> 
			<html:text name="CALL_OP_SEARCH_FORM" property="erpPONum" />
       </td>  		
  </tr>
	
  <tr> <td>&nbsp;</td>
       <td><b>Call Type:</b></td>
	   <td> 
			<html:select name="CALL_OP_SEARCH_FORM" property="callTypeCd">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:options  collection="Call.type.vector" property="value" />
			</html:select>			
       </td>
       <td><b>Call Severity:</b></td>
	   <td> 
			<html:select name="CALL_OP_SEARCH_FORM" property="callSeverityCd">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:options  collection="Call.severity.vector" property="value" />
			</html:select>			
       </td>	   
  </tr>

  <tr> <td>&nbsp;</td>
	   <bean:define id="userlist" name="CALL_OP_SEARCH_FORM" property="customerServiceUserList" />
       <td><b>Opened By:</b></td>
	   <td> 
			<html:select name="CALL_OP_SEARCH_FORM" property="openedById">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:options collection="userlist" property="userId" labelProperty="userName"/>
			</html:select>						
       </td>  		
       <td><b>Assigned To:</b></td>
	   <td> 
			<html:select name="CALL_OP_SEARCH_FORM" property="assignedToId">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:options collection="userlist" property="userId" labelProperty="userName"/>
			</html:select>									
       </td>
  </tr>
  
  <tr> <td>&nbsp;</td>
       <td><b>Status:</b></td>
	   <td> 
			<html:select name="CALL_OP_SEARCH_FORM" property="callStatusCd">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:options  collection="Call.status.vector" property="value" />				
			</html:select>
       </td>
       <td><b>Site Data:</b></td>
       <td><html:text name="CALL_OP_SEARCH_FORM" property="siteData" /></td>
  </tr>
    
  <tr>
       <td colspan="5" align="center">
<!--       <html:hidden property="action" value="search"/>   -->
       <html:submit property="action" value="Search"/>
	   <html:submit property="action" value="My Calls" />
     </td>
  </tr>
  <tr><td colspan="5">&nbsp;</td>
  </tr>

</html:form>  
</table>

Search results count:&nbsp;<bean:write name="CALL_OP_SEARCH_FORM" property="listCount" filter="true"/>

<logic:greaterThan name="CALL_OP_SEARCH_FORM" property="listCount" value="0">

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a href="callOp.do?action=sort&sortField=acctname"><b>Acct Name</b></a></td>
<td class="resultscolumna"><a href="callOp.do?action=sort&sortField=sitename"><b>Site Name</b></a></td>
<td><a href="callOp.do?action=sort&sortField=contactname"><b>Contact Name</b></a></td>
<td class="resultscolumna"><a href="callOp.do?action=sort&sortField=calldesc"><b>Call Description</b></a></td>
<td><a href="callOp.do?action=sort&sortField=city"><b>City</b></a></td>
<td class="resultscolumna"><a href="callOp.do?action=sort&sortField=state"><b>State</b></a></td>
<td><a href="callOp.do?action=sort&sortField=zip"><b>Zip</b></a></td>
<td class="resultscolumna"><a href="callOp.do?action=sort&sortField=opendate"><b>Date</b></a></td>
<td><a href="callOp.do?action=sort&sortField=status"><b>Status</b></a></td>
<td class="resultscolumna"><a href="callOp.do?action=sort&sortField=openedbyname"><b>Opened By</b></a></td>
<td><a href="callOp.do?action=sort&sortField=assignedtoname"><b>Assigned To</b></a></td>
</tr>

 <bean:define id="pagesize" name="CALL_OP_SEARCH_FORM" property="listCount"/>
	  
<logic:iterate id="callDesc" name="CALL_OP_SEARCH_FORM" property="resultList"
     offset="0" length="<%=pagesize.toString()%>" type="com.cleanwise.service.api.value.CallDescData"> 
 <pg:item>
 <bean:define id="key"  name="callDesc" property="callDetail.callId"/>
 <bean:define id="openedDate"  name="callDesc" property="callDetail.addDate"/>
 <% String linkHref = new String("callOpDetail.do?action=view&id=" + key);%>


 <tr>
  <td><bean:write name="callDesc" property="accountName"/></td>
  <td class="resultscolumna"><bean:write name="callDesc" property="siteName"/></td>
  <td><a href="<%=linkHref%>"><bean:write name="callDesc" property="callDetail.contactName"/></a></td>
  <td class="resultscolumna"><bean:write name="callDesc" property="callDetail.longDesc"/></td>
  <td><bean:write name="callDesc" property="siteCity"/></td>
  <td class="resultscolumna"><bean:write name="callDesc" property="siteState"/></td>
  <td><bean:write name="callDesc" property="siteZip"/></td>
  <td class="resultscolumna"><i18n:formatDate value="<%=openedDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/></td>
  <td><bean:write name="callDesc" property="callDetail.callStatusCd"/></td>
  <td class="resultscolumna"><bean:write name="callDesc" property="callDetail.addBy"/></td>
  <td><bean:write name="callDesc" property="assignedTo"/></td>
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




