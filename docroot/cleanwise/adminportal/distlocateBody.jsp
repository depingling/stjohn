<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
        String feedField =  (String)request.getParameter("feedField");
        if(null == feedField) {
                feedField = new String("");
        }
        String feedDesc =  (String)request.getParameter("feedDesc");
        if(null == feedDesc) {
                feedDesc = new String("");
        }
        String locateFilter =  (String)request.getParameter("locateFilter");
        if(null == locateFilter) {
                locateFilter = new String("");
        }
%>


<script language="JavaScript1.2">
<!--

function passIdAndName(id, name) {
  var feedBackFieldName = document.DIST_SEARCH_FORM.feedField.value;
  var feedDesc = document.DIST_SEARCH_FORM.feedDesc.value;

  if(feedBackFieldName && ""!= feedBackFieldName) {
    window.opener.document.forms[0].elements[feedBackFieldName].value = id;
  }
  if(feedDesc && ""!= feedDesc) {
    window.opener.document.forms[0].elements[feedDesc].value = name;
  }
  self.close();
}


function popLocateSubFeed(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"SubLocate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

//-->
</script>


<div class="text">

<html:form name="DIST_SEARCH_FORM"
action="/adminportal/distlocate.do" focus="searchField"
type="com.cleanwise.view.forms.DistMgrSearchForm">
<input type="hidden" name="feedField" value="<%=feedField%>">
<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
<input type="hidden" name="locateFilter" value="<%=locateFilter%>">


<table width="769" border="0"  class="mainbody">
  <html:form name="DIST_SEARCH_FORM" action="adminportal/distmgr.do"
    scope="session" type="com.cleanwise.view.forms.DistMgrSearchForm">
  <tr> <td><b>Find Distributors:</b></td>
       <td colspan="3">
                        <html:text name="DIST_SEARCH_FORM" property="searchField"/>
       </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
         <html:radio name="DIST_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="DIST_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="DIST_SEARCH_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>
    <!-- Territory search -->
  <tr><td><b>State:</b></td>
      <td colspan="3"><html:text name="DIST_SEARCH_FORM" property="searchState" size="3"/>
      <b>County</b> (starts with):
      <html:text name="DIST_SEARCH_FORM" property="searchCounty" size="20"/>
      <b>Zip Code:</b>
      <html:text name="DIST_SEARCH_FORM" property="searchPostalCode" size="5"/>
      </td>
  </tr>
  <!-- Group Search -->
  <tr><td><b>Group:</b></td>
       <td colspan="3">
       <html:text name="DIST_SEARCH_FORM" property="searchGroupId" size="20"/>
       <html:button property="locateGroup" onclick="popLocateSubFeed('../adminportal/grouplocate', 'searchGroupId', '');" value="Locate Group"/>
       </td>
<!---                 -->
  <tr> <td></td>
       <td colspan="3">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.viewall"/>
        </html:submit>
     </html:form>
    </td>
  </tr>
</table>

</div>

<div>

<logic:present name="Dist.found.vector">
<bean:size id="rescount"  name="Dist.found.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">


<table width="769" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="distlocate.do?action=sort&sortField=id&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&locateFilter=<%=locateFilter%>">Distributor&nbsp;Id</td>
<td><a class="tableheader" href="distlocate.do?action=sort&sortField=name&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&locateFilter=<%=locateFilter%>">Name</td>
<td><a class="tableheader" href="distlocate.do?action=sort&sortField=address&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&locateFilter=<%=locateFilter%>">Address 1</td>
<td><a class="tableheader" href="distlocate.do?action=sort&sortField=city&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&locateFilter=<%=locateFilter%>">City</td>
<td><a class="tableheader" href="distlocate.do?action=sort&sortField=state&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&locateFilter=<%=locateFilter%>">State</td>
<td><a class="tableheader" href="distlocate.do?action=sort&sortField=status&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&locateFilter=<%=locateFilter%>">Status</td>
</tr>

<logic:iterate id="arrele" name="Dist.found.vector">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
  <bean:define id="key"  name="arrele" property="busEntity.busEntityId"/>
  <bean:define id="name" name="arrele" property="busEntity.shortDesc"/>
  <!-- If filtering, only allow selection of those on filter list -->
  <script type="text/javascript">
       var name<%=key%> = "<%=name%>";
  </script>
  <% if (locateFilter.equals("itemCatalog") ) { %>
  <%     IdVector ids = (IdVector)session.getAttribute("Dist.filter.vector"); %>
  <%     Integer id = (Integer)key; %>
  <%     if (ids.indexOf(id) >= 0 ) { %>
  <%         String onClick = new String("return passIdAndName('"+key+"',name"+key+");");%>
             <a href="javascript:void(0);" onclick='<%=onClick%>'>
             <bean:write name="arrele" property="busEntity.shortDesc"/>
             </a>
  <%     } else { %>
             <bean:write name="arrele" property="busEntity.shortDesc"/>
  <%     } %>
  <% } else { %>
  <%         String onClick = new String("return passIdAndName('"+key+"',name"+key+");");%>
             <a href="javascript:void(0);" onclick="<%=onClick%>">
             <bean:write name="arrele" property="busEntity.shortDesc"/>
             </a>
  <% } %>
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
<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</tr>

</logic:iterate>
</table>

</logic:greaterThan>
</logic:present>

</html:form>
</div>


