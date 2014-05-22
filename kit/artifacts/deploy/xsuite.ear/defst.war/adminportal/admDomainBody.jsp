<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js" 
  CHARSET="ISO-8859-1"></SCRIPT>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

  <table width="769" cellspacing="0" border="0" class="mainbody">
  <tr> <td><b>Domains Listing</b></td>
  <td>
<%
/* Disable the create freight handler funtionality for
   now.  durval 1/20/2003*/
%>

  <html:form name="DOMAIN_ADM_DETAIL_FORM" action="adminportal/domainAdmDetail.do"
    scope="session" type="com.cleanwise.view.forms.DomainAdmDetailForm">
    
<html:submit>
<app:storeMessage  key="admin.button.create"/>
</html:submit>
<html:hidden property="action" value="createDomain"/>
</html:form>
  <!--  <html:submit property="action" value="Create New"/> -->

</td>
</tr>
</table>

<logic:present name="domain.data.vector">
<bean:size id="rescount"  name="domain.data.vector"/>
 Count:  <bean:write name="rescount" />

<table cellspacing="0" border="0" width="769"  class="results">
<thead>
<tr align=left>
<th  class="tt">
<a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);" title="-- Sort by Id">Id</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);" title="-- Sort by Name">Name</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);" title="-- Sort by Default Store">Default Store</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);" title="-- Sort by Status">Status</a></th>
<!--<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);" title="-- Sort by State">State</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 5, false);" title="-- Sort by Status">Status</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 6, false);" title="-- Sort by EDI Routing Code">EDI Routing Code</a></th>
--></tr>
</thead>

<tbody id="resTblBdy">
<logic:iterate id="arrele" name="domain.data.vector">


<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>

<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a href="domainAdmDetail.do?action=detail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="busEntity.shortDesc"/>
</td>

<td><bean:write name="arrele" property="defaultStoreName"/></td>
<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>

</tr>

</logic:iterate>

</tbody></table>

</logic:present>

</div>





