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

<html:form name="CONTRACT_SEARCH_FORM" 
  action="/adminportal/contractmgr.do" 
  focus="searchField" scope="session" 
  type="com.cleanwise.view.forms.ContractMgrSearchForm">


<tr> <td><b>Find Contracts:</b></td>
<td colspan="3"> 
<html:text name="CONTRACT_SEARCH_FORM" property="searchField"/>	
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
<html:hidden property="action" value="search"/>
<html:submit property="action" value="Search"/>
<html:button property="act" onclick="document.location='contractmgr.do?action=viewall';">
<app:storeMessage  key="admin.button.viewall"/>
</html:button>
<html:button property="act" onclick="document.location='contractdetail.do?action=add';">
<app:storeMessage  key="admin.button.create"/>
</html:button>	   
</td>
</tr>
<tr><td colspan="4">&nbsp;</td>
</tr>

</html:form>  
</table>

Search results count:&nbsp;<bean:write name="CONTRACT_SEARCH_FORM" property="listCount" filter="true"/>

<logic:greaterThan name="CONTRACT_SEARCH_FORM" property="listCount" value="0">


<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a class="tableheader" href="contractmgr.do?action=sort&sortField=id">Contract&nbsp;Id </td>
<td><a class="tableheader" href="contractmgr.do?action=sort&sortField=name">Contract Name </td>
<td><a class="tableheader" href="contractmgr.do?action=sort&sortField=catalog">Catalog Name </td>
<td><a class="tableheader" href="contractmgr.do?action=sort&sortField=status">Contract Status </td>
</tr>

 <bean:define id="pagesize" name="CONTRACT_SEARCH_FORM" property="listCount"/>
	  
<logic:iterate id="contract" name="CONTRACT_SEARCH_FORM" property="resultList"
     offset="0" length="<%=pagesize.toString()%>" type="com.cleanwise.service.api.value.ContractDescData"> 
 <bean:define id="key"  name="contract" property="contractId"/>
 <bean:define id="name" name="contract" property="contractName"/>
 <% String linkHref = new String("contractdetail.do?action=edit&id=" + key);%>
 <tr>
  <td><bean:write name="contract" property="contractId" filter="true"/></td>
  <td><html:link href="<%=linkHref%>"><bean:write name="contract" property="contractName" filter="true"/></html:link></td>
  <td><bean:write name="contract" property="catalogName" filter="true"/></td>
  <td><bean:write name="contract" property="status" filter="true"/></td>
 </tr>
 </logic:iterate>
	  
</table>

</logic:greaterThan>

</div>




