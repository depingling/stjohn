<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_ADMIN_CATALOG_FORM" type="com.cleanwise.view.forms.StoreCatalogMgrForm"/>

<jsp:include flush='true' page="locateStoreAccount.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/catalogs.do" /> 
   <jsp:param name="jspFormName" 	value="STORE_ADMIN_CATALOG_FORM" /> 
   <jsp:param name="jspSubmitIdent" 	value="" /> 
   <jsp:param name="jspReturnFilterProperty" 	value="filterAccounts" /> 
</jsp:include>

<div class="text">
<script language="JavaScript1.2">
<!--
function SetCatChecked(val) {
 dml=document.forms; 
 for(i=0; i<dml.length; i++) {
  found = false;
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    //alert(dml[i].elements[j].name);
    if (dml[i].elements[j].name=='selectedCatalogIds') {
      dml[i].elements[j].checked=val;
      found = true;
    }
  }
  if(found) break;
 }
}

//-->
</script>

  <table ID=649 cellspacing="0" border="0" width="769" class="mainbody">
  <html:form styleId="650"  action="/storeportal/catalogs.do"
            scope="session">
  <tr> <td colspan='4'>
     <jsp:include flush='true' page="locateStoreAccountButtons.jsp">
		 <jsp:param name="jspFormName" 	        value="STORE_ADMIN_CATALOG_FORM" /> 
		 <jsp:param name="jspReturnFilterProperty" 	value="filterAccounts" /> 
	 </jsp:include>
  </td></tr>
  <tr> <td><b>Find Catalog:</b></td>
     <td colspan="3">
 	  <html:text name="STORE_ADMIN_CATALOG_FORM" property="searchField"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <html:radio name="STORE_ADMIN_CATALOG_FORM" property="searchType" value="catalogId" />
         ID
         <html:radio name="STORE_ADMIN_CATALOG_FORM" property="searchType" value="catalogNameStarts" />
         Name(starts with)
         <html:radio name="STORE_ADMIN_CATALOG_FORM" property="searchType" value="catalogNameContains" />
         Name(contains)
         
         </td>
  </tr>
  <tr> <td><b>Catalog Type:</b></td>
     <td colspan="3">
 	  <html:select name="STORE_ADMIN_CATALOG_FORM" property="catalogType">
        <html:option value="">&nbsp;</html:option>
        <html:option value="<%=RefCodeNames.CATALOG_TYPE_CD.ACCOUNT%>">
        <%=RefCodeNames.CATALOG_TYPE_CD.ACCOUNT%></html:option>
        <html:option value="<%=RefCodeNames.CATALOG_TYPE_CD.SHOPPING%>">
        <%=RefCodeNames.CATALOG_TYPE_CD.SHOPPING%></html:option>
        <html:option value="<%=RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING%>">
        <%=RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING%></html:option>
        <html:option value="<%=RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR%>">
        <%=RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR%></html:option>
    </html:select>    
         </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
       <html:submit property="action" value="Search"/>
       <html:button property="action" value="Create New" onclick="document.location='storecatalogdet.do?action=Create New';"/>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Show Inactive Catalogs
       <html:checkbox name="STORE_ADMIN_CATALOG_FORM" property="showInactiveFlag"/>
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

<table ID=651 cellspacing="0" border="0" width="769" class="results">
<tr>
<td colspan=4>Search Results Count: 
<bean:write name="STORE_ADMIN_CATALOG_FORM" property="listCount"/> 
</td>
</tr>
<logic:greaterThan name="STORE_ADMIN_CATALOG_FORM" property="listCount" value="0">


<tr align=left>
<td><a ID=652 class="tableheader" href="catalogs.do?action=sort&sortField=id">Id </td>
<td><a ID=653 class="tableheader" href="catalogs.do?action=sort&sortField=name">Name </td>
<td>
<a ID=654 href="javascript:SetCatChecked(1)">[Check&nbsp;All]</a><br>
<a ID=655 href="javascript:SetCatChecked(0)">[&nbsp;Clear]</a>
</td>
<td><a ID=656 class="tableheader" href="catalogs.do?action=sort&sortField=type">Type </td>
<td><a ID=657 class="tableheader" href="catalogs.do?action=sort&sortField=status">Status </td>
</tr>
   <bean:define id="pagesize" name="STORE_ADMIN_CATALOG_FORM" property="listCount"/>
   <logic:iterate id="catalog" name="STORE_ADMIN_CATALOG_FORM" property="resultList"
    offset="0" length="<%=pagesize.toString()%>" type="com.cleanwise.service.api.value.CatalogData">
    <bean:define id="key"  name="catalog" property="catalogId"/>
    <bean:define id="name" name="catalog" property="shortDesc"/>
    <% String linkHref = new String("storecatalogdet.do?action=edit&id=" + key);%>
    <tr>
  <td><bean:write name="catalog" property="catalogId"/></td>
  <td><html:link href="<%=linkHref%>"><bean:write name="catalog" property="shortDesc" filter="true"/></html:link></td>
  <td><html:multibox property="selectedCatalogIds" value="<%=key.toString()%>"/></td>
  <td><bean:write name="catalog" property="catalogTypeCd"/></td>
  <td><bean:write name="catalog" property="catalogStatusCd"/></td>
 </tr>

 </logic:iterate>

 <tr align=center>
 <td colspan="4">&nbsp;</td>
 </tr>
 </table>
 </logic:greaterThan>
</html:form>


</div>


