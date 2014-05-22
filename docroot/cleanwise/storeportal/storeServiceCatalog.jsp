<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CategoryUtil" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js"
  CHARSET="ISO-8859-1"></SCRIPT>
<SCRIPT TYPE="text/javascript" SRC="../externals/lib.js"
  CHARSET="ISO-8859-1"></SCRIPT>

<script language="JavaScript1.2">
<!--
function Submit (val) {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action' &&
        dml[i].elements[j].value=='BBBBBBB') {
      //alert('action.value='+dml[i].elements[j].value+' -> '+val);
      dml[i].elements[j].value=val;
      dml[i].submit();
      break;
    }
  }
 }
}
function sortSubmit(val) {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  actionFl = false;
  fieldFl = false;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action' &&
        dml[i].elements[j].value=='BBBBBBB') {
          dml[i].elements[j].value="sort";
          actionFl = true;
        }
      //alert('action.value='+dml[i].elements[j].value+' -> '+val);
    if (dml[i].elements[j].name=='sortField') {
      dml[i].elements[j].value=val;
      fieldFl = true;
    }
    if(actionFl && fieldFl) {
      dml[i].submit();
      break;
    }
  }
 }
}

function cpoSubmit(itemId, catId, ogId, cpoAction) {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  actionFl = false;
  itemFl = false;
  catalogFl = false;
  ogFl = false;
  //alert('next_form='+ellen);
  for(j=ellen-1; j>=0; j--) {
    if (dml[i].elements[j].name.match('action') &&
        dml[i].elements[j].value=='BBBBBBB') {
          dml[i].elements[j].value=cpoAction;
          document.forms[i].itemCpoId.value=itemId;
          document.forms[i].catalogCpoId.value=catId;
          document.forms[i].submit();
          return;
        }
  }
 }
}

function setRedSave() {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  actionFl = false;
  fieldFl = false;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action' &&
        dml[i].elements[j].value=='Save') {
          // alert('value='+dml[i].elements[j].value);
          dml[i].elements[j].style.color='red';
          dml[i].elements[j].style.fontWeight = 'bold'
          document.forms[0].needToSaveFl.value='true';
          return;
        }
    }
  }
 }

-->
</script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_SERVICE_CATALOG_MGR_FORM" type="com.cleanwise.view.forms.StoreServiceCatalogMgrForm"/>
<%
  ServiceData currManagingService = theForm.getCurrManagingService();
  int colQty = 0;
  int rowQty = 0;
  int rowNum = 0;
  int genMfgNameIndex = 0;
%>


<div class="text">

<jsp:include flush='true' page="locateStoreCatalog.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/service-catalog.do" />
   <jsp:param name="jspFormName" 	value="STORE_SERVICE_CATALOG_MGR_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="catalogFilter"/>
</jsp:include>


<jsp:include flush='true' page="locateStoreAccount.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/service-catalog.do" />
   <jsp:param name="jspFormName" 	value="STORE_SERVICE_CATALOG_MGR_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
</jsp:include>


<jsp:include flush='true' page="locateStoreService.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/service-catalog.do" />
   <jsp:param name="jspFormName" 	value="STORE_SERVICE_CATALOG_MGR_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="serviceFilter"/>
   <jsp:param name="jspCatalogListProperty" value="catalogListSelectedItemContract"/>
   <jsp:param name="checkBoxShowInactive"  value="show"/>
</jsp:include>

<jsp:include flush='true' page="locateStoreDistributor.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/service-catalog.do" />
   <jsp:param name="jspFormName" 	value="STORE_SERVICE_CATALOG_MGR_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterPropertyAlt" value="distDummyConvert"/>
   <jsp:param name="jspReturnFilterProperty" value="distFilter"/>
</jsp:include>

  <table ID=1151 cellspacing="0" border="0" width="769" class="mainbody">
  <html:form styleId="1152"  action="/storeportal/service-catalog.do"
            scope="session">
  <tr> <td colspan='4'>
  <%
   boolean filterFl = false;
   CatalogDataVector catalogDV = theForm.getCatalogFilter();
   if(catalogDV!=null && catalogDV.size()>0) {
     filterFl = true;
  %>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b><%=catalogDV.size()%></b> catalogs selected
  <html:submit property="action" value="Clear Catalog Filter" styleClass='text'/>
   <% } %>
  <html:submit property="action" value="Locate Catalog" styleClass='text'/>
  </td></tr>

  <tr> <td colspan='4'>
  <%
   AccountDataVector accountDV = theForm.getAccountFilter();
   if(accountDV!=null && accountDV.size()>0) {
      filterFl = true;
  %>
  <b>Account:</b>
  <%
     for(int ii=0; ii<accountDV.size(); ii++) {
        AccountData accountD = (AccountData) accountDV.get(ii);
  %>
   &lt;<%=accountD.getBusEntity().getShortDesc()%>&gt;
   <% } %>
  <html:submit property="action" value="Clear Account Filter" styleClass='text'/>
   <% } %>
  <html:submit property="action" value="Locate Account" styleClass='text' />
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

  <tr> <td colspan='4'>
  <%
   ServiceViewVector serviceVwV = theForm.getServiceFilter();
   if(serviceVwV!=null && serviceVwV.size()>0) {
      filterFl = true;
  %>
  <b>Services:</b>
  <%
     for(int ii=0; ii<serviceVwV.size(); ii++) {
        ServiceView serviceVw = (ServiceView) serviceVwV.get(ii);
  %>
   &lt;<%=serviceVw.getServiceName()%>&gt;
   <% } %>
  <html:submit property="action" value="Clear Service Filter" styleClass='text'/>
   <% } %>
 <html:submit property="action" value="Locate Service" styleClass='text'/>
<% if(currManagingService!=null || filterFl) { %>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <html:submit property="action" value="Clear Filters" styleClass='text'/>
<% } %>
 </td>
 </tr>    <tr>
      <td colspan='4'>


      </td>
      </tr>
  </table>
<!-- /////////////////////////////////////////////////////////////////////////////////////////////////// -->
<% if(currManagingService!=null) { %>
<table ID=1153 border="0" width="769" class="mainbody">
   <tr>
        <td>&nbsp;</td>

        <td colspan='4'>

                <b>All Categories:</b>
               <html:select name="STORE_SERVICE_CATALOG_MGR_FORM" property="storeCategoryId" styleClass="text">
                   <html:option value="0">&nbsp;</html:option>
                  <% CatalogCategoryDataVector categDV = theForm.getStoreCategories();
                     if(categDV!=null) {
                     for(Iterator iter=categDV.iterator(); iter.hasNext(); ) {
                       CatalogCategoryData categD = (CatalogCategoryData) iter.next();
                       String categIdS = ""+categD.getCatalogCategoryId();
                       String shortDesc = categD.getCatalogCategoryShortDesc();
                       String longDesc = categD.getCatalogCategoryLongDesc();
                       String fullDesc = CategoryUtil.buildFullCategoryName(shortDesc, longDesc);
                  %>
                   <html:option value="<%=categIdS%>"><%=fullDesc%></html:option>
                  <% }} %>
                </html:select>

        <html:submit property="action" value="Add To Catalogs" styleClass="text"/>
        </td>
  </tr>
  <tr>
        <td colspan="5">&nbsp;</td>
  </tr>
  <tr class="results">
        <td>&nbsp;</td><td colspan="4">
        <b>Name:</b>&nbsp;<bean:write name="STORE_SERVICE_CATALOG_MGR_FORM" property="currManagingService.itemData.shortDesc"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Item Id:</b>&nbsp;<bean:write name="STORE_SERVICE_CATALOG_MGR_FORM" property="currManagingService.itemData.itemId"/>
        </td>
  </tr>
  <tr class="results">
        <td>&nbsp;</td>
        <td colspan='4'>
        </td>
  </tr>
  <tr>
        <td colspan="5">&nbsp;</td>
  </tr>

   <tr>
        <td>&nbsp;</td>

        <td colspan='4'>

                <b>Catalog Category:</b>
               <html:select name="STORE_SERVICE_CATALOG_MGR_FORM" property="categoryIdDummy">
                   <html:option value="0"><app:storeMessage  key="admin.select"/></html:option>
                  <% ItemDataVector categDV = theForm.getCategories();
                     if(categDV!=null) {
                     for(Iterator iter=categDV.iterator(); iter.hasNext(); ) {
                       ItemData categD = (ItemData) iter.next();
                       String categIdS = ""+categD.getItemId();
                       String shortDesc = categD.getShortDesc();
                       String longDesc = categD.getLongDesc();
                       String fullDesc = CategoryUtil.buildFullCategoryName(shortDesc, longDesc);
                  %>
                   <html:option value="<%=categIdS%>"><%=fullDesc%></html:option>
                  <% }} %>
                </html:select>


        <html:button property="action" value="Set" styleClass="text" onclick="Submit('CategoryIdInp')"/>
        <html:submit property="action" value="Remove From Catalogs" styleClass="text"/>
        <!--
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Cost&nbsp;Center:</b>&nbsp;<html:text name="STORE_SERVICE_CATALOG_MGR_FORM" property="costCenterIdDummy" size="6"/>
        <html:button property="action" value="Set" styleClass="text" onclick="Submit('CostCenterIdInp')"/>
        -->
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <% if(theForm.getShowCustSkuNumFl()) {%>
        <b>Cust&nbsp;Sku#:</b>&nbsp;<html:text name="STORE_SERVICE_CATALOG_MGR_FORM" property="catalogSkuNumDummy" size="6"/>
        <html:button property="action" value="Set" styleClass="text" onclick="Submit('CatalogSkuNumInp')"/>
        <% } %>

        </td>
  </tr>

   <tr>
        <td>&nbsp;</td>
        <td colspan='5'>
          <b>Distr.:</b>
      <logic:present name="STORE_SERVICE_CATALOG_MGR_FORM" property="distDummy">
                <bean:write  name="STORE_SERVICE_CATALOG_MGR_FORM" property="distDummy.busEntity.shortDesc"/>(<bean:write  name="STORE_SERVICE_CATALOG_MGR_FORM" property="distDummy.busEntity.busEntityId"/>)
      </logic:present>
       <html:button property="action" value="Locate" styleClass='text' onclick="Submit('LocateAssignDistributor');"/>
       <html:button property="action" value="Clean" styleClass='text' onclick="Submit('CleanAssignDistributor');"/>
       <html:button property="action" value="Set" styleClass="text" onclick="Submit('DistIdInp')"/>
       <html:button property="action" value="Set default" styleClass="text" onclick="Submit('SetDefaultDist')"/>

        <b>Price:</b>
                <html:text  name="STORE_SERVICE_CATALOG_MGR_FORM" property="priceDummy" size="5"/>
       <html:button property="action" value="Set" styleClass="text" onclick="Submit('PriceInp')"/>
        </td>
  </tr>
  <% if(theForm.getShowDistSkuNumFl()) {%>
  <tr>
        <td>&nbsp;</td>
        <td colspan='4'>
         <b>Dist.&nbsp;Sku#:</b>
                <html:text  name="STORE_SERVICE_CATALOG_MGR_FORM" property="distSkuNumDummy" size="9"/>
       <html:button property="action" value="Set" styleClass="text" onclick="Submit('DistSkuNumInp')"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
  </tr>
   <% } %>

  <tr>
         <td colspan="5">
        <table ID=1154><tr>
          <td>
          <table ID=1155>
             <tr><td colspan='3'>
               <html:button property="action" value="Set All" styleClass="text" onclick="Submit('SetAll')"/>
             </td>
             </tr>
            <tr>
           <td>
           <tr><td>

          <table ID=1156>
              <tr> <td class='tableheader'><b>Action:</b></td></tr>
          </table>
          </td><td>
          <table ID=1157>
             <tr>
                <td><b>Items - Catalog:</b></td>
                <td><html:multibox name="STORE_SERVICE_CATALOG_MGR_FORM" property="tickItemsToCatalog" value="true"/></td>
             </tr>
             <tr>
                 <td><b>Items - Price:</b></td>
                 <td><html:multibox name="STORE_SERVICE_CATALOG_MGR_FORM" property="tickItemsToContract" value="true"/></td>
             </tr>
          </table>
          </td><td>
          <table ID=1158>
             <tr><td><html:submit styleClass="text" property="action" value="Add"/></td></tr>
             <tr><td><html:submit styleClass="text" property="action" value="Remove"/></td></tr>
          </table>
          </td>
            </tr>
          </table>
          </td><td>
          <table ID=1159>
              <tr> <td class='tableheader'><b>View:</b></td></tr>
          </table>
          </td><td>
          <table ID=1160>
             <tr>
                <td><b>Dist Sku#:</b></td>
                <td><html:multibox name="STORE_SERVICE_CATALOG_MGR_FORM" property="showDistSkuNumFl" value="true"/></td>
             </tr>
              <tr>
                 <td><b>Cust Sku#:</b></td>
                 <td><html:multibox name="STORE_SERVICE_CATALOG_MGR_FORM" property="showCustSkuNumFl" value="true"/></td>
             </tr>
          </table>
          </td><td>

          <table ID=1161>
             <tr><td><html:submit property="action" value="Set View" styleClass='text'/></td></tr>
          </table>
          </td><td>
          <table ID=1162>
             <tr><td><html:submit property="action" value="Reload"/></td></tr>
             <%if(theForm.getNeedToSaveFl()) {%>
             <tr><td><html:submit property="action" value="Save" styleClass='reqind'/></td></tr>
             <% } else { %>
             <tr><td><html:submit property="action" value="Save"/></td></tr>
             <% } %>
          </table>
          </td>
         </tr></table>
        </td>
  </tr>
</table>

<logic:present name="STORE_SERVICE_CATALOG_MGR_FORM" property="itemAggrVector">
<%
  int listSize = theForm.getItemAggrVector().size();
%>
  count:<%=listSize%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Sort direction:&nbsp;&nbsp;<html:radio styleClass="text" property="ascSortOrderFl" value="true"/> Ascending
<html:radio styleClass="text" property="ascSortOrderFl" value="false"/> Descending
<%
 if(listSize>0) {
%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Distr. Sku Auto Completion:
<html:radio styleClass="text" property="distSkuAutoFl" value="true"/>Yes
<html:radio styleClass="text" property="distSkuAutoFl" value="false"/>No


<table  id="res" width="100%" class="results">
<tr align=center>
<td class="tableheader"><% colQty++;%>
<a ID=1163 class="text" href="javascript:SetCheckedGlobal(1,'selectFl')">[Chk.&nbsp;All]</a>
<br>
<a ID=1164 class="text" href="javascript:SetCheckedGlobal(0,'selectFl')">[&nbsp;Clear]</a>
<br>
<a ID=1165 class="text" href="javascript:setCheckBetween('selectFl')">[Chk&nbsp;Zone]</a>
</td>
<th><% colQty++;%><a ID=1166 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogId');">Cat Id</a></th>
<th><% colQty++;%><a ID=1167 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogName');">Catalog</a></th>
<th><% colQty++;%><a ID=1168 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogType');">Type</a></th>
<th><% colQty++;%><a ID=1169 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogStatus');">Status</a></th>
<th><% colQty++;%><a ID=1170 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogFlInp');">C</a></th>
<th><% colQty++;%><a ID=1171 href="#pgsort" class="tableheader" onclick="sortSubmit('ContractFlInp');">P</a></th>
<th><% colQty++;%><a ID=1172 href="#pgsort" class="tableheader" onclick="sortSubmit('CategoryName');">Categ</a></th>
<th><% colQty++;%><a ID=1173 href="#pgsort" class="tableheader" onclick="sortSubmit('DistIdInp');">Dist.Id</a></th>
<th><% colQty++;%><a ID=1174 href="#pgsort" class="tableheader" onclick="sortSubmit('DistName');">Dist Name</a></th>
<th><% colQty++;%><a ID=1175 href="#pgsort" class="tableheader" onclick="sortSubmit('PriceInp');">Price</a></th>
<% if(theForm.getShowCustSkuNumFl()) {%>
<th><% colQty++;%><a ID=1176 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogSkuNumInp');">Cust.<br>Sku#</a></th>
<% } %>
<% if(theForm.getShowDistSkuNumFl()) {%>
<th><% colQty++;%><a ID=1177 href="#pgsort" class="tableheader" onclick="sortSubmit('DistSkuNumInp');">Dist.<br>Sku#</a></th>
<% } %>
</tr>

<tbody id="resTblBdy">
   <logic:iterate id="elle" name="STORE_SERVICE_CATALOG_MGR_FORM" property="itemAggrVector" indexId="ii" type="ItemCatalogAggrView">
<!-- ////////////////////////////////   -->
   <%
     String prop = "selectFl["+ii+"]";
     String itemIdS = ""+elle.getItemId();
     String catalogIdS = ""+elle.getCatalogId();
     boolean catalogFl = elle.getCatalogFlInp();
     String catalogSign = (catalogFl)?"X":"-";
     boolean contractFl = elle.getContractFlInp();
     String contractSign = (contractFl)?"X":"-";
     String catalogType = elle.getCatalogType().substring(0,4);
     String catalogStatus = elle.getCatalogStatus().substring(0,3);
     String categoryName = Utility.strNN(elle.getCategoryName());
     String distIdInp = "distIdInp["+ii+"]";
     String distName = Utility.strNN(elle.getDistName());
     String priceInp = "priceInp["+ii+"]";
     String catalogSkuNumInp = "catalogSkuNumInp["+ii+"]";
     String distSkuNumInp = "distSkuNumInp["+ii+"]";
     String cpoSubmitC = "cpoSubmit('"+itemIdS+"','"+catalogIdS+"','0','cpoC');";
     String cpoSubmitP = "cpoSubmit('"+itemIdS+"','"+catalogIdS+"','0','cpoP');";
     int index = ii.intValue();
     rowQty = index+1;
     rowNum++;
    %>
    <% if(index%25==0 && index>0) {
       rowNum++;
    %>
        <tr align=center>
        <td class="tableheader">&nbsp;</td>
        <th>Cat.Id</th>
        <th>Catalog</th>
        <th>Type</th>
        <th>Stat.</th>
        <th>C</th>
        <th>P</th>
        <th>Categ</th>
        <th>D.Id</th>
        <th>D.Name</th>
        <th>Price</th>

        <% if(theForm.getShowCustSkuNumFl()) {%>
        <th>C.Sku#</th>
        <% } %>
        <% if(theForm.getShowDistSkuNumFl()) {%>
        <th>D.Sku#</th>
        <% } %>
         </tr>
    <% } %>
   <tr>
    <% String distIdChange = "requestDistSkuInfo("+itemIdS+","+ii+","+rowNum+")"; %>
   <td align="center"><html:multibox name="STORE_SERVICE_CATALOG_MGR_FORM" property="<%=prop%>" value="true"/></td>
    <td><bean:write name="elle" property="catalogId"/></td>
    <td><bean:write name="elle" property="catalogName"/></td>
    <td><%=catalogType%></td>
    <td><%=catalogStatus%></td>


    <td><a ID=1178 href="#cpo"  onclick="<%=cpoSubmitC%>"><%=catalogSign%></a></td>
    <td><a ID=1179 href="#cpo"  onclick="<%=cpoSubmitP%>"><%=contractSign%></a></td>


    <td><% if(catalogFl) {%><%=categoryName%><% } else {%>&nbsp;<% } %></td>
    <td><% if(catalogFl) {%><html:text property="<%=distIdInp%>"  onkeydown="navigate(this);" onchange='<%=distIdChange%>' size="5"/><% } else {%>&nbsp;<% } %></td>
    <td><% if(catalogFl) {%><%=distName%><% } else {%>&nbsp;<% } %></td>
    <td><% if(contractFl) {%><html:text property="<%=priceInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="6"/><% } else {%>&nbsp;<% } %></td>

<% if(theForm.getShowCustSkuNumFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=catalogSkuNumInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="8"/><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowDistSkuNumFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=distSkuNumInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="10"/><% } else {%>&nbsp;<% } %></td>
<% } %>
</tr>
<!-- ////////////////////////////////////-->

   </logic:iterate>
</tbody>
</table>
<% } %>
</logic:present>


<% } %>
  <html:hidden  property="action" value="BBBBBBB"/>
  <html:hidden  property="sortField" value="BBBBBBB"/>
  <html:hidden  property="itemCpoId" value="BBBBBBB"/>
  <html:hidden  property="catalogCpoId" value="BBBBBBB"/>
  <html:hidden  property="ogCpoId" value="BBBBBBB"/>
  <html:hidden  property="needToSaveFl" value='<%=""+theForm.getNeedToSaveFl()%>'/>

  </html:form>
</body>

</div>
<script language="JavaScript1.2">
<!--
function setCheckBetween(pName) {
   var rowQty = <%=rowQty%>;
   var ii=0;
   for(; ii < rowQty; ii++) {
     var name1 = pName + '['+ii+']';
     var contrObj = document.forms[0][name1];
     if('undefined' != typeof contrObj) {
       if(contrObj.checked) {
         break;
       }
     }
   }
   for(ii+=1; ii < rowQty; ii++) {
     var name1 = pName + '['+ii+']';
     var contrObj = document.forms[0][name1];
     if('undefined' != typeof contrObj) {
       if(contrObj.checked) {
         break;
       } else {
         contrObj.checked = true;
       }
     }
   }
 }

function setChecked(pChecked,pName) {
   setRedSave();
   var rowQty = <%=rowQty%>;
   for(ii=0; ii < rowQty; ii++) {
     var contrName = pName + 'Contr['+ii+']';
     var hiddenName = pName + 'Inp['+ii+']';
     var contrObj = document.forms[0][contrName];
     var hiddenObj = document.forms[0][hiddenName];
     if('undefined' != typeof hiddenObj && 'undefined' != typeof contrObj) {
       contrObj.checked = pChecked;
       if(pChecked) {
         hiddenObj.value = 'true';
       } else {
         hiddenObj.value = 'false';
       }
     }
   }
 }

 function toggle(pObject, pHiddenObjName) {
   setRedSave();
   var checked = pObject.checked;
   if(checked) {
     document.all[pHiddenObjName].value = 'true';
   } else {
     document.all[pHiddenObjName].value = 'false';
   }
 }

function navigate(pObject) {
  var keycode = window.event.keyCode;
  //  alert(keycode);
  if(keycode==38) {
    var inpName = pObject.name;
    var ind = inpName.indexOf('[');
    var len = inpName.length;
    var prevRowNum = parseInt(inpName.substring(ind+1,len-1))-1;
    var inpContr = inpName.substring(0,ind)+'['+prevRowNum+']';
    while(prevRowNum >= 0) {
      var navObj = document.all[inpContr];
      if('undefined' != typeof navObj) {
        navObj.focus();
        navObj.select();
        break;
      }
      prevRowNum--;
      inpContr = inpName.substring(0,ind)+'['+prevRowNum+']';
    }
  }
  if(keycode==40) {
    var rowQty = <%=rowQty%>;
    var inpName = pObject.name;
    var ind = inpName.indexOf('[');
    var len = inpName.length;
    var nextRowNum = parseInt(inpName.substring(ind+1,len-1))+1;
    var inpContr = inpName.substring(0,ind)+'['+nextRowNum+']';
    while(nextRowNum < rowQty) {
      var navObj = document.all[inpContr];
      if('undefined' != typeof navObj) {
        navObj.focus();
        navObj.select();
        break;
      }
      nextRowNum++;
      inpContr = inpName.substring(0,ind)+'['+nextRowNum+']';
    }
  }
}

function getkey(e)
{
if (window.event)
   return window.event.keyCode;
else if (e)
   return e.which;
else
   return null;
}
var rowNum;
var req;
function setDistSkuInfo() {
    if (req.readyState == 4) {
        if (req.status == 200) {
           //alert("req.responseText="+req.responseText);

           var index = req.responseXML.getElementsByTagName("index")[0].childNodes[0].nodeValue;

           var  skuNumObj = req.responseXML.getElementsByTagName("Sku")[0];
           var  skuNum = "";
           if('undefined' != typeof skuNumObj) {
             skuNum = skuNumObj.childNodes[0].nodeValue;
           }

           var  distIdObj = req.responseXML.getElementsByTagName("DistId")[0];
           var  distId = "";
           if(distIdObj!=null && 'undefined' != typeof distIdObj) {
             distId = distIdObj.childNodes[0].nodeValue;
           }

           var distIdInpElemName = "distIdInp["+index+"]";
           distIdInpElemObj = document.all[distIdInpElemName];
           if(distId!=distIdInpElemObj.value) {
             alert("returned and requested distr. do not match: "+ distId +" != " + distIdInpElemObj.value);
           }


           //Set dist item sku
           var distSkuNumInpElemName = "distSkuNumInp["+index+"]";
           var distSkuNumInpElemObj = document.all[distSkuNumInpElemName];
           if('undefined' != typeof distSkuNumInpElemObj) {
             var  distItemSkuObj = req.responseXML.getElementsByTagName("DistItemSku")[0];
             var  distItemSku = "";
             if(distItemSkuObj!=null && 'undefined' != typeof distItemSkuObj) {
               distItemSku = distItemSkuObj.childNodes[0].nodeValue;
             }
             distSkuNumInpElemObj.value = distItemSku;
           }
           //May be use later
           //Dist Name
           var  distNameObj = req.responseXML.getElementsByTagName("DistName")[0];
           var  distName = "";
           if(distNameObj!=null && ('undefined' != typeof distNameObj)) {
             distName = distNameObj.childNodes[0].nodeValue;
           }
           document.all.res.rows[rowNum].cells[11].innerHTML = distName ;

       }
    }
}

function requestDistSkuInfo(pItemId, pIndex, pRowNum) {
   setRedSave();
   rowNum = pRowNum;
   var autoCompl = document.all["distSkuAutoFl"][0].checked;
   if(!autoCompl) {
     return;
   }

   var objName = "distIdInp["+pIndex+"]";
   var pObject = document.getElementById(objName);
   if('undefined' == typeof pOject) {
      pObject = document.all[objName];
   }
   var distId = pObject.value;

   var url = "service-catalog.do?action=DistSkuInfo&distId="+distId+"&itemId="+pItemId+"&index="+pIndex;
   if (window.XMLHttpRequest) {
     req = new XMLHttpRequest();
   } else if (window.ActiveXObject) {
     req = new ActiveXObject("Microsoft.XMLHTTP");
   }
   req.open("GET", url, true);
   req.onreadystatechange = setDistSkuInfo;
   req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
   req.send(null);
}
-->
</script>
