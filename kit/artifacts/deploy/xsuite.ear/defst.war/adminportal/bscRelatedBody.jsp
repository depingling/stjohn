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

<logic:present name="list.bsc.sites">
<bean:size id="rescount"  name="list.bsc.sites"/>
 Related sites count:  <bean:write name="rescount" />

<table cellspacing="0" border="0" width="769"  class="results">
<thead>
<tr align=left>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);" title="-- Sort by Id">Id</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);" title="-- Sort by Name">Name</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);" title="-- Sort by Account">Account</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);" title="-- Sort by Address">Address</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);" title="-- Sort by City">City</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 5, false);" title="-- Sort by State">State</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 6, false);" title="-- Sort by Zip">Zip</a></th>
<th  class="tt"><a href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 7, false);" title="-- Sort by Status">Status</a></th>
</tr>
</thead>

<tbody id="resTblBdy">
<logic:iterate id="arrele" name="list.bsc.sites"
  type="com.cleanwise.service.api.value.SiteView">

<tr>
<td><bean:write name="arrele" property="id"/></td>

<td>
<a href="sitemgr.do?action=sitedetail&searchType=id&searchField=<%=arrele.getId()%>">
<bean:write name="arrele" property="name"/>
</a>
</td>

<td><bean:write name="arrele" property="accountName"/></td>
<td><bean:write name="arrele" property="address"/></td>
<td><bean:write name="arrele" property="city"/></td>
<td><bean:write name="arrele" property="state"/></td>
<td><bean:write name="arrele" property="postalCode"/></td>
<td><bean:write name="arrele" property="status"/></td>

</tr>

</logic:iterate>
</tbody>
</table>


</logic:present>

</div>
