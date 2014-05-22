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

  <table width="769" cellspacing="0" border="0" class="mainbody">
  <html:form name="MANUF_SEARCH_FORM" action="adminportal/manufmgr.do"
    scope="session" type="com.cleanwise.view.forms.ManufMgrSearchForm">
  <tr> <td><b>Find Manufacturer:</b></td>
       <td colspan="3">
			<html:text name="MANUF_SEARCH_FORM" property="searchField"/>
       </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio name="MANUF_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="MANUF_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="MANUF_SEARCH_FORM" property="searchType" value="nameContains" />
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
	<html:submit property="action">
		<app:storeMessage  key="admin.button.create"/>
	</html:submit>
     </html:form>
    </td>
  </tr>
</table>

<logic:present name="Manuf.found.vector">
<bean:size id="rescount"  name="Manuf.found.vector"/>
Search result count:  <bean:write name="rescount" />

<table cellspacing="0" border="0" width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="manufmgr.do?action=sort&sortField=id">Manufacturer&nbsp;Id</td><td><a class="tableheader" href="manufmgr.do?action=sort&sortField=name">Name</td><td><a class="tableheader" href="manufmgr.do?action=sort&sortField=status">Status</td>
</tr>

<logic:iterate id="arrele" name="Manuf.found.vector">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a href="manufmgrDetail.do?action=manufdetail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="busEntity.shortDesc"/>
</a>
</td>
<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</tr>

</logic:iterate>
</table>
</logic:present>

</div>





