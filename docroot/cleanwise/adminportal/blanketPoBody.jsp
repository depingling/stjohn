<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table cellspacing="0" border="0" class="mainbody" width="769">
<html:form action="/adminportal/blanketPo.do">
  <tr>
  	<td align="right"><b>Search:</b>
  	</td>
<td>
<html:text name="BLANKET_PO_SEARCH_FORM" property="searchField"/>

<html:radio name="BLANKET_PO_SEARCH_FORM" property="searchType" 
    value="id" />  ID
<html:radio name="BLANKET_PO_SEARCH_FORM" property="searchType" 
    value="nameBegins" />  Name(starts with)
<html:radio name="BLANKET_PO_SEARCH_FORM" property="searchType" 
    value="nameContains" />  Name(contains)
</td>
</tr>


  <tr> <td>&nbsp;</td>
       <td>
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
        <html:submit property="action">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
        <html:submit property="action">
		<app:storeMessage  key="admin.button.create"/>
	</html:submit>
    </td>
  </tr>
</table>


<logic:present name="BLANKET_PO_SEARCH_FORM" property="results">
<bean:size id="rescount"  name="BLANKET_PO_SEARCH_FORM" property="results"/>
Search result count:  <bean:write name="rescount" />


<table cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);">ID</a></td>
<td><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);">Name</a></td>
<td><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);">Base Po Value</a></td>
<td><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);">Status</a></td>
</tr>

<logic:iterate id="arrele" name="BLANKET_PO_SEARCH_FORM" property="results">
<bean:define id="eleid" name="arrele" property="blanketPoNumId"/>
<tr>
<td><bean:write name="arrele" property="blanketPoNumId"/></td>
<td>
  <a href="blanketPoDetail.do?action=detail&id=<%=eleid%>">
    <bean:write name="arrele" property="shortDesc"/>
  </a>
</td>
<td><bean:write name="arrele" property="poNumber"/></td>
<td><bean:write name="arrele" property="statusCd"/></td>
</tr>


</logic:iterate>
</table>

</logic:present>
</html:form>

</div>
 




