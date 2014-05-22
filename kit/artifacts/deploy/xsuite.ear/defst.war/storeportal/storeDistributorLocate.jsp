<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.SessionTool"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="saaf" name="STORE_ADMIN_ACCOUNT_FORM" type="com.cleanwise.view.forms.StoreAccountMgrForm"/>
<bean:define id="sadf" name="STORE_ACCOUNT_DETAIL_FORM" type="com.cleanwise.view.forms.StoreAccountMgrDetailForm"/>
<%! private String distributor_select="";  %>
<%    String locateFilter =  (String)request.getParameter("locateFilter");
   if(locateFilter==null) {
   locateFilter = new String("");
        }
%>


<script language="JavaScript">
    <!--
function passIdAndNameD(id,v1,v2) {
    document.forms[5].select.value=id;
    document.forms[5].submit();
    return true;
}
function hideLocate_d(id)
{
eval("document.getElementById(id)").style.display='none';
}
//-->
</script>





<tr><td>
<html:form styleId="786" name="STORE_DIST_SEARCH_FORM"
action="/storeportal/storeDistributorLocate.do" focus="searchField"
type="com.cleanwise.view.forms.StoreDistMgrSearchForm">

<table ID=787 width="749" border="0"  class="mainbody">
<tr> <td><b>Find Distributors:</b></td>
       <td colspan="3">
                        <html:text name="STORE_DIST_SEARCH_FORM" property="searchField"/>
       </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
         <html:radio name="STORE_DIST_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="STORE_DIST_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="STORE_DIST_SEARCH_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>
    <!-- Territory search -->
  <tr><td><b>State:</b></td>
      <td colspan="3"><html:text name="STORE_DIST_SEARCH_FORM" property="searchState" size="3"/>
      <b>County</b> (starts with):
      <html:text name="STORE_DIST_SEARCH_FORM" property="searchCounty" size="20"/>
      <b>Zip Code:</b>
      <html:text name="STORE_DIST_SEARCH_FORM" property="searchPostalCode" size="5"/>
      </td>
  </tr> 

  <tr> <td></td>
       <td colspan="3">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.viewall"/>
        </html:submit>
         <html:button property="action" onclick="hideLocate_d('storeDistributorLocate');" value="Hide"/>

    </td>
  </tr>
</table>
</html:form>
</td></tr>
<tr><td>
<html:form styleId="788" name="STORE_DIST_SEARCH_FORM"
action="/storeportal/storeDistributorLocate.do" focus="searchField"
type="com.cleanwise.view.forms.StoreDistMgrSearchForm">
    <input type="hidden" name="action" value="setSelect">
     <input type="hidden" name="select" value="<%=distributor_select%>">

<logic:present name="StoreDist.found.vector">
<bean:size id="rescount"  name="StoreDist.found.vector"/>
Search result count:  <bean:write name="rescount" />

<logic:greaterThan name="rescount" value="0">

<table ID=789 width="749" border="0" class="results">
<tr align=left>
<td><a ID=790 class="tableheader" href="storeDistributorLocate.do?action=sort&sortField=id&&locateFilter=<%=locateFilter%>">Distributor&nbsp;Id</td>
<td><a ID=791 class="tableheader" href="storeDistributorLocate.do?action=sort&sortField=name&locateFilter=<%=locateFilter%>">Name</td>
<td><a ID=792 class="tableheader" href="storeDistributorLocate.do?action=sort&sortField=address&locateFilter=<%=locateFilter%>">Address 1</td>
<td><a ID=793 class="tableheader" href="storeDistributorLocate.do?action=sort&sortField=city&locateFilter=<%=locateFilter%>">City</td>
<td><a ID=794 class="tableheader" href="storeDistributorLocate.do?action=sort&sortField=state&locateFilter=<%=locateFilter%>">State</td>
<td><a ID=795 class="tableheader" href="storeDistributorLocate.do?action=sort&sortField=status&locateFilter=<%=locateFilter%>">Status</td>
</tr>

<logic:iterate id="arrele" name="StoreDist.found.vector">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
  <bean:define id="key"  name="arrele" property="busEntity.busEntityId"/>
  <bean:define id="name" name="arrele" property="busEntity.shortDesc"/>
  <!-- If filtering, only allow selection of those on filter list -->
  <%String [] distributor_param =  {key.toString(),"",""};
  if (locateFilter.equals("itemCatalog") ) { %>
  <%     IdVector ids = (IdVector)session.getAttribute("Dist.filter.vector"); %>
  <%     Integer id = (Integer)key; %>
  <%     if (ids.indexOf(id) >= 0 ) { %>
            <app:JSCall f_name="passIdAndNameD" param="<%=distributor_param%>">
             <bean:write name="arrele" property="busEntity.shortDesc"/>
              </app:JSCall>

  <%     } else { %>
             <bean:write name="arrele" property="busEntity.shortDesc"/>
  <%     } %>
  <% } else { %>

            <app:JSCall f_name="passIdAndNameD" param="<%=distributor_param%>">
             <bean:write name="arrele" property="busEntity.shortDesc"/>
              </app:JSCall>
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
</logic:present></html:form>
</td></tr>


