<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="STORE_DISCOUNT_FREIGHT_FORM" type="com.cleanwise.view.forms.StoreDiscountFreightMgrForm"/>

<jsp:include flush='true' page="locateStoreCatalog.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/storeDiscountFreightSearch.do" />
   <jsp:param name="jspFormName" 	value="STORE_DISCOUNT_FREIGHT_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="catalogFilter"/>
</jsp:include>

<jsp:include flush='true' page="locateStoreDistributor.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/storeDiscountFreightSearch.do" />
   <jsp:param name="jspFormName" 	value="STORE_DISCOUNT_FREIGHT_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterPropertyAlt" value="distDummyConvert"/>
   <jsp:param name="jspReturnFilterProperty" value="distFilter"/>
</jsp:include>



<div class="text">

<table ID=811 cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
    <html:form styleId="812" name="STORE_DISCOUNT_FREIGHT_FORM" action="/storeportal/storeDiscountFreightSearch.do" focus="freightSearchField"
        scope="session" type="com.cleanwise.view.forms.StoreDiscountFreightMgrSearchForm">
  <%
  boolean oneCatalogFl = false;
  boolean filterFl = false;
  CatalogDataVector catalogDV = theForm.getCatalogFilter();
   if(catalogDV!=null && catalogDV.size()>0) {
     for(int ii=0; ii<3 && ii<catalogDV.size(); ii++) {
       CatalogData catalogD = (CatalogData) catalogDV.get(ii);
       if(catalogDV.size()==1) oneCatalogFl = true;
  %>
 &nbsp;&nbsp;&lt<b><%=catalogD.getShortDesc()%></b>&gt;
   <% } %>
   <% if(catalogDV.size()>3) {%> ... (<%=catalogDV.size()%> catalogs) <% } %>
  <html:submit property="action" value="Clear Catalog Filter" styleClass='text'/>
   <% } %>
  <html:submit property="action" value="Locate Catalog" styleClass='text'/>
  </td></tr>
  
    <tr> <td colspan='4'>
  <%
   DistributorDataVector distDV = theForm.getDistFilter();
   if(distDV!=null && distDV.size()>0) {
      filterFl = true;
  %>
  <b>Distributor:</b>
  <%
     for(int ii=0; ii<distDV.size(); ii++) {
        DistributorData distD = (DistributorData) distDV.get(ii);
  %>
   &lt;<%=distD.getBusEntity().getShortDesc()%>&gt;
   <% } %>
  <html:submit property="action" value="Clear Distributor Filter" styleClass='text'/>
   <% } %>
  <html:submit property="action" value="Locate Distributor" styleClass='text' />
  </td></tr>

       
        
        <tr>
            <td><b>Find Discount Tables:</b></td>
            <td colspan="3">
                <html:text name="STORE_DISCOUNT_FREIGHT_FORM" property="freightSearchField"/>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td colspan="3">
                <html:radio name="STORE_DISCOUNT_FREIGHT_FORM" property="freightSearchType" value="id" />
                ID
                <html:radio name="STORE_DISCOUNT_FREIGHT_FORM" property="freightSearchType" value="nameBegins" />
                Name(starts with)
                <html:radio name="STORE_DISCOUNT_FREIGHT_FORM" property="freightSearchType" value="nameContains" />
                Name(contains)
            </td>
        </tr>
        <tr> 
            <td>&nbsp;</td>
            <td colspan="3">
                <html:hidden property="action" value="search"/>
                <html:submit property="action" value="Search"/>
                <html:button property="act" onclick="document.location='storeDiscountFreightDet.do?action=add';">
                    <app:storeMessage  key="admin.button.create"/>
                </html:button>	   
            </td>
        </tr>
        <tr>
            <td colspan="4">&nbsp;</td>
        </tr>
    </html:form>  
</table>

Search results count:&nbsp;<bean:write name="STORE_DISCOUNT_FREIGHT_FORM" property="listCount" filter="true"/>

<logic:greaterThan name="STORE_DISCOUNT_FREIGHT_FORM" property="listCount" value="0">

<table ID=813 cellpadding="2" cellspacing="0" border="0" width="769" class="results">
    <tr align=left>
        <td><a ID=814 class="tableheader" href="storeDiscountFreightSearch.do?action=sort&sortField=id">Discount Table&nbsp;Id </td>
        <td><a ID=815 class="tableheader" href="storeDiscountFreightSearch.do?action=sort&sortField=name">Discount Table Name </td>
        <td><a ID=816 class="tableheader" href="storeDiscountFreightSearch.do?action=sort&sortField=type">Discount Table Type </td>
        <td><a ID=817 class="tableheader" href="storeDiscountFreightSearch.do?action=sort&sortField=status">Discount Table Status </td>
        <td><a ID=818 class="tableheader" href="storeDiscountFreightSearch.do?action=sort&sortField=distributorName">Distributor</td>
    </tr>
    <bean:define id="pagesize" name="STORE_DISCOUNT_FREIGHT_FORM" property="listCount"/>
    <logic:iterate id="freightTable" name="STORE_DISCOUNT_FREIGHT_FORM" property="resultList"
        offset="0" length="<%=pagesize.toString()%>" type="com.cleanwise.service.api.value.FreightTableView"> 
        <bean:define id="key"  name="freightTable" property="freightTableId"/>
        <bean:define id="name" name="freightTable" property="shortDesc"/>
        <% String linkHref = new String("storeDiscountFreightDet.do?action=edit&id=" + key);%>
    <tr>
        <td><bean:write name="freightTable" property="freightTableId" filter="true"/></td>
        <td><html:link href="<%=linkHref%>"><bean:write name="freightTable" property="shortDesc" filter="true"/></html:link></td>
        <td><bean:write name="freightTable" property="freightTableTypeCd" filter="true"/></td>
        <td><bean:write name="freightTable" property="freightTableStatusCd" filter="true"/></td>
		<td><bean:write name="freightTable" property="distributorName" filter="true"/></td>
    </tr>
    </logic:iterate>
</table>

</logic:greaterThan>

</div>
