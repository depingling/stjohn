<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.forms.StoreCostCenterMgrForm" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<% 
StoreCostCenterMgrForm theForm = (StoreCostCenterMgrForm) session.getAttribute("STORE_ADMIN_COST_CENTER_FORM");
%>

<jsp:include flush='true' page="locateStoreCatalog.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/costcenters.do" />
   <jsp:param name="jspFormName" 	value="STORE_ADMIN_COST_CENTER_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="catalogFilter"/>
</jsp:include>

<div class="text">
<script language="JavaScript1.2">
<!--
function sortSubmit(val) {
 var actions;
 actions=document.all["action"];
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='BBBBBBB') {
     actions[ii].value="sort";
     break;
   }
 }
 sortField=document.all["sortField"];
 sortField.value = val;

 document.forms[0].submit();
 return false;
 }

//-->
</script>

  <table ID=699 cellspacing="0" border="0" width="769" class="mainbody">
  <html:form styleId="700"  action="/storeportal/costcenters.do" scope="session">
  <tr> <td colspan='4'>
  <%
   CatalogDataVector catalogDV = theForm.getFilterCatalogs();
   if(catalogDV!=null && catalogDV.size()>0) {
     for(int ii=0; ii<3 && ii<catalogDV.size(); ii++) {
       CatalogData catalogD = (CatalogData) catalogDV.get(ii);
  %>
 &nbsp;&nbsp;&lt<b><%=catalogD.getShortDesc()%></b>&gt;
   <% } %>
   <% if(catalogDV.size()>3) {%> ... (<%=catalogDV.size()%> catalogs) <% } %>
  <html:submit property="action" value="Clear Catalog Filter" styleClass='text'/>
   <% } %>
  <html:submit property="action" value="Locate Catalog" styleClass='text'/>
  </td></tr>
  
  <tr> <td><b>Find CostCenter:</b></td>
     <td colspan="3">
 	  <html:text name="STORE_ADMIN_COST_CENTER_FORM" property="searchField"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <html:radio name="STORE_ADMIN_COST_CENTER_FORM" property="searchType" value="costCenterId" />
         ID
         <html:radio name="STORE_ADMIN_COST_CENTER_FORM" property="searchType" value="costCenterNameStarts" />
         Name(starts with)
         <html:radio name="STORE_ADMIN_COST_CENTER_FORM" property="searchType" value="costCenterNameContains" />
         Name(contains)
         
         </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
       <html:submit property="action" value="Search"/>
       <html:button property="action" value="Create New" onclick="document.location='costcenterdet.do?action=Create New';"/>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Show Inactive Cost Centers
       <html:checkbox name="STORE_ADMIN_COST_CENTER_FORM" property="showInactiveFlag"/>
    </td>
  </tr>

  <script type="text/javascript" language="JavaScript">
  <!--
     dml=document.forms; 
     for(i=0; i<dml.length; i++) {
      ellen = dml[i].elements.length;
      //alert('next_form='+ellen);
      for(j=0; j<ellen; j++) {
        //alert(dml[i].elements[j].name);
        if (dml[i].elements[j].name=='searchField') {
          dml[i].elements[j].focus();
          break;
        }
      }
     } 
  // -->
  </script>

</table>
<% if(theForm.getSelectedCostCenters()!=null) { %>
<table ID=701 cellspacing="0" border="0" width="769" class="stpTable">
<tr>
<td colspan=6>Search Results Count: 
<bean:write name="STORE_ADMIN_COST_CENTER_FORM" property="listCount"/> 
</td>
</tr>
<logic:greaterThan name="STORE_ADMIN_COST_CENTER_FORM" property="listCount" value="0">
<!--    Found Cost Centers -->
<tr align=left>
<td class="stpTH"><a ID=702 href="#pgsort" onclick="sortSubmit('id');">Id</a></td>
<td class="stpTH"><a ID=703 href="#pgsort" onclick="sortSubmit('name');">Name</a></td>
<td class="stpTH"><a ID=704 href="#pgsort" onclick="sortSubmit('status');">Status</a></td>
<td class="stpTH"><a ID=705 href="#pgsort" onclick="sortSubmit('type');">Type</a></td>
<td class="stpTH"><a ID=706 href="#pgsort" onclick="sortSubmit('tax');">Tax Type</a></td>
<td class="stpTH"><a ID=707 href="#pgsort" onclick="sortSubmit('freight');">Allocate Freight</a></td>
<td class="stpTH"><a href="#pgsort" onclick="sortSubmit('discount');">Allocate Discount</a></td>
</tr>
 <logic:iterate id="costCenter" 
                  name="STORE_ADMIN_COST_CENTER_FORM" property="selectedCostCenters"
                  type="com.cleanwise.service.api.value.CostCenterData">
    <bean:define id="key"  name="costCenter" property="costCenterId"/>
    <bean:define id="name" name="costCenter" property="shortDesc"/>
    <% String linkHref = "costcenterdet.do?action=edit&id=" + key;%>
  <tr>
  <td class="stpTD"><%=key%></td>
  <td class="stpTD"><html:link href="<%=linkHref%>"><%=name%></html:link></td>
  <td class="stpTD"><bean:write name="costCenter" property="costCenterStatusCd"/></td>
  <td class="stpTD"><bean:write name="costCenter" property="costCenterTypeCd"/></td>
  <td class="stpTD"><bean:write name="costCenter" property="costCenterTaxType"/></td>
  <td class="stpTD"><bean:write name="costCenter" property="allocateFreight"/></td>
  <td class="stpTD"><bean:write name="costCenter" property="allocateDiscount"/></td>
 </tr>

 </logic:iterate>

 </table>
 </logic:greaterThan>
 <% } %>

  <html:hidden  property="action" value="BBBBBBB"/>
  <html:hidden  property="sortField" value="BBBBBBB"/>

</html:form>
</div>


