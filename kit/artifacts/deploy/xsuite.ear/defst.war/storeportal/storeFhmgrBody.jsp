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

<logic:present name="freight_handler.vector">

<table ID=1589 width="<%=Constants.TABLEWIDTH%>" cellspacing="0" border="0" class="mainbody">
  <tr> 
	<td><b>Freight Handlers Listing</b></td>
  </tr>
</table>

<bean:size id="rescount"  name="freight_handler.vector"/>
 Count:  <bean:write name="rescount" />

<table ID=1590 cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH%>"  class="results">
<thead>
<tr align=left>
<th  class="tt">
<a ID=1591 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);" title="-- Sort by Id">Id</a></th>
<th  class="tt"><a ID=1592 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);" title="-- Sort by Name">Name</a></th>
<th  class="tt"><a ID=1593 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);" title="-- Sort by Address 1">Address 1</a></th>
<th  class="tt"><a ID=1594 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);" title="-- Sort by City">City</a></th>
<th  class="tt"><a ID=1595 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);" title="-- Sort by State">State</a></th>
<th  class="tt"><a ID=1596 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 5, false);" title="-- Sort by Status">Status</a></th>
<th  class="tt"><a ID=1597 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 6, false);" title="-- Sort by EDI Routing Code">EDI Routing Code</a></th>
</tr>
</thead>

<tbody id="resTblBdy">
<logic:iterate id="arrele" name="freight_handler.vector">
<tr>
<td><bean:write name="arrele" property="busEntityData.busEntityId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="busEntityData.busEntityId"/>
<a ID=1598 href="fhmgrDetail.do?action=fhdetail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="busEntityData.shortDesc"/>
</a>
</td>
<td>
<bean:write name="arrele" property="primaryAddress.address1"/>
</td>
<td>
<bean:write name="arrele" property="primaryAddress.city"/>
</td>
<td>
<bean:write name="arrele" property="primaryAddress.stateProvinceCd"/>
</td>

<td><bean:write name="arrele" property="busEntityData.busEntityStatusCd"/></td>
<td><bean:write name="arrele" property="ediRoutingCd"/></td>
</tr>

</logic:iterate>

</tbody></table>

</logic:present>

</div>





