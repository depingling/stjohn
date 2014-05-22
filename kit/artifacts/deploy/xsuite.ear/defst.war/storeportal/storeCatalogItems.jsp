<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CategoryUtil" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.LocateUploadItemForm" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js"
  CHARSET="ISO-8859-1"></SCRIPT>
<SCRIPT TYPE="text/javascript" SRC="../externals/lib.js"
  CHARSET="ISO-8859-1"></SCRIPT>

<script language="JavaScript1.2">

function Submit (val) {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=ellen-1; j>=0; j--) {
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
  for(j=ellen-1; j>=0; j--) {
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
          document.forms[i].ogCpoId.value=ogId;
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
<bean:define id="theForm" name="STORE_ITEM_CATALOG_FORM" type="com.cleanwise.view.forms.StoreItemCatalogMgrForm"/>
<%
  int colQty = 0;
  int rowQty = 0;
  int rowNum = 0;
  int genMfgNameIndex = 0;
  int multiproductNameIndex = 0;
%>

<div class="text">

<jsp:include flush='true' page="locateStoreCatalog.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/cat-item.do" />
   <jsp:param name="jspFormName" 	value="STORE_ITEM_CATALOG_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="catalogFilter"/>
</jsp:include>

<jsp:include flush='true' page="locateStoreItem.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/cat-item.do" />
   <jsp:param name="jspFormName" 	value="STORE_ITEM_CATALOG_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="itemFilter"/>
   <jsp:param name="jspCatalogListProperty" value="catalogListSelectedCatItems"/>
</jsp:include>

<jsp:include flush='true' page="locateStoreDistributor.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/cat-item.do" />
   <jsp:param name="jspFormName" 	value="STORE_ITEM_CATALOG_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterPropertyAlt" value="distDummyConvert"/>
   <jsp:param name="jspReturnFilterProperty" value="distFilter"/>
</jsp:include>

<jsp:include flush='true' page="locateStoreManufacturer.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/cat-item.do" />
   <jsp:param name="jspFormName" 	value="STORE_ITEM_CATALOG_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterPropertyAlt" value="manufDummyConvert"/>
   <jsp:param name="jspReturnFilterProperty" value="manufFilter"/>
</jsp:include>

<!-- Show catalog attributes -->
<%
   CatalogData catalogD = null;
   CatalogDataVector catalogDV = theForm.getCatalogFilter();
   if(catalogDV!=null && catalogDV.size()>0) {
     catalogD = (CatalogData) catalogDV.get(0);
   }
   LocateUploadItemForm locateUploadItemForm = theForm.getLocateUploadItemForm();

   boolean isShowSpecialPermission =
        theForm.isShowSpecialPermissionFl() &&
        (theForm.getStore() != null && theForm.getStore().isAllowSpecialPermission()) &&
        (catalogD != null && RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(catalogD.getCatalogTypeCd()));

   if(locateUploadItemForm!=null && locateUploadItemForm.getLocateUploadItemFl()){
%>
  <table ID=594 cellspacing="0" border="0" width="769" class="mainbody">
  <tr class="results">
        <td>&nbsp;</td><td colspan="4">
        <b>Catalog Id:</b>&nbsp;<%=catalogD.getCatalogId()%>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Catalog:</b>&nbsp;<%=catalogD.getShortDesc()%>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Type:</b>&nbsp;<%=catalogD.getCatalogTypeCd()%>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Status:</b>&nbsp;<%=catalogD.getCatalogStatusCd()%>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
  </tr>
  </table>
<% } %>

<jsp:include flush='true' page="locateUploadItem.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/cat-item.do" />
   <jsp:param name="jspFormName" 	value="STORE_ITEM_CATALOG_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="uploadItemFilter"/>
</jsp:include>

  <table ID=595 cellspacing="0" border="0" width="769" class="mainbody">
  <html:form styleId="596" action="/storeportal/cat-item.do" scope="session">

  <tr> <td colspan='4'>
  <b>Item Filter:</b>
  <%
   boolean filterFl = false;
   boolean itemFilterFl = false;
   ItemViewVector itemViewV = theForm.getItemFilter();
   if(itemViewV!=null && itemViewV.size()>0) {
      filterFl = true;
      itemFilterFl = true;
  %>
   <%=itemViewV.size()%> skus selected
  <html:submit property="action" value="Clear Item Filter" styleClass='text'/>
   <% } %>
  <html:submit property="action" value="Locate Item" styleClass='text'/>
  </td></tr>
  <% if(!itemFilterFl) { %>
  <tr> <td colspan='4'>
  <b>Distributor Filter:</b>
  <%
   DistributorDataVector distDV = theForm.getDistFilter();
   if(distDV!=null && distDV.size()>0) {
      filterFl = true;
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
  <b>Manufacturer Filter:</b>
  <%
   ManufacturerDataVector manufDV = theForm.getManufFilter();
   if(manufDV!=null && manufDV.size()>0) {
      filterFl = true;
      for(int ii=0; ii<manufDV.size(); ii++) {
        ManufacturerData manufD = (ManufacturerData) manufDV.get(ii);
  %>
   &lt;<%=manufD.getBusEntity().getShortDesc()%>&gt;
   <% } %>
  <html:submit property="action" value="Clear Manufacturer Filter" styleClass='text'/>
   <% } %>
  <html:submit property="action" value="Locate Manufacturer" styleClass='text' />
  </td></tr>
<% } %>

  <tr> <td colspan='4'>
  <b>Catalog:&nbsp;</b>
  <%
   if(catalogD!=null) {
     filterFl = true;
  %>
 <%=catalogD.getShortDesc()%>
  <html:submit property="action" value="Clear Catalog Filter" styleClass='text'/>
   <% } %>
  <html:submit property="action" value="Locate Catalog" styleClass='text'/>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <% if(filterFl){ %>
  <html:submit property="action" value="Clear Filters" styleClass='text'/>
  <% } %>
  </td></tr>

  </table>

<!-- /////////////////////////////////////////////////////////////////////////////////////////////////// -->
<% if(catalogD!=null) { %>
<table ID=597 border="0" width="769" class="mainbody">
   <tr>
        <td>&nbsp;</td>

        <td colspan='4'>

                <b>All Categories:</b>
               <html:select name="STORE_ITEM_CATALOG_FORM" property="storeCategoryId" styleClass="text">
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
        <b>Catalog Id:</b>&nbsp;<%=catalogD.getCatalogId()%>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Catalog:</b>&nbsp;<%=catalogD.getShortDesc()%>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Type:</b>&nbsp;<%=catalogD.getCatalogTypeCd()%>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Status:</b>&nbsp;<%=catalogD.getCatalogStatusCd()%>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
  </tr>
  <tr class="results">
        <td>&nbsp;</td><td colspan="4">
        <b>Order Guide:</b>
        <html:select name="STORE_ITEM_CATALOG_FORM" property="selectedOrderGuideId" onchange="Submit ('Reload')">
           <html:option value="-1">-- All Order Guides --</html:option>
          <% OrderGuideDescDataVector ogdDV = theForm.getOrderGuides();
             if(ogdDV!=null) {
             for(Iterator iter=ogdDV.iterator(); iter.hasNext(); ) {
               OrderGuideDescData ogD = (OrderGuideDescData) iter.next();
               String ogIdS = ""+ogD.getOrderGuideId();
               String ogName = ogD.getOrderGuideName();
          %>
           <html:option value="<%=ogIdS%>"><%=ogName%></html:option>
          <% }} %>
        </html:select>
        </td>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <html:submit property="action" value="Xls Update" />
  </tr>
  <tr>
        <td colspan="5">&nbsp;</td>
  </tr>
   <tr>
        <td>&nbsp;</td>

        <td colspan='4'>

                <b>Catalog Category:</b>
               <html:select name="STORE_ITEM_CATALOG_FORM" property="categoryIdDummy">
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

        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
  </tr>
   <tr>
        <td>&nbsp;</td>
        <td colspan='4'>
          <b>Distr.:</b>
      <logic:present name="STORE_ITEM_CATALOG_FORM" property="distDummy">
                <bean:write  name="STORE_ITEM_CATALOG_FORM" property="distDummy.busEntity.shortDesc"/>(<bean:write  name="STORE_ITEM_CATALOG_FORM" property="distDummy.busEntity.busEntityId"/>)
      </logic:present>
       <html:button property="action" value="Locate" styleClass='text' onclick="Submit('LocateAssignDistributor');"/>
       <html:button property="action" value="Clean" styleClass='text' onclick="Submit('CleanAssignDistributor');"/>
       <html:button property="action" value="Set" styleClass="text" onclick="Submit('DistIdInp')"/>

        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </tr>

 <% if(theForm.getShowGenManufFl()) {%>
 <tr>
        <td>&nbsp;</td>
        <td colspan='4'>
          <b>Gen. Manuf:</b>
         <html:select name="STORE_ITEM_CATALOG_FORM" property="genManufIdDummy">
                <html:option value="0">&nbsp;</html:option>
                <logic:iterate id="manuf" name="STORE_ITEM_CATALOG_FORM" property="storeManufBusEntitys"
                    type="com.cleanwise.service.api.value.BusEntityData">
                <bean:define id="manufId" name="manuf" property="busEntityId"/>
                <html:option value="<%=manufId.toString()%>"><bean:write name="manuf" property="shortDesc"/></html:option>
             </logic:iterate>
        </html:select>
       <html:button property="action" value="Set" styleClass="text" onclick="Submit('GenManufIdInp')"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

  </tr>
   <% } %>

 <% if(theForm.getShowMultiproductsFl()) {%>
 <tr>
        <td>&nbsp;</td>
        <td colspan='4'>
          <b>Multi Product:</b>
         <html:select name="STORE_ITEM_CATALOG_FORM" property="multiproductIdDummy">
                <html:option value="0"><app:storeMessage  key="admin.select"/></html:option>
                <logic:iterate id="m" name="STORE_ITEM_CATALOG_FORM" property="storeMultiproducts"
                    type="com.cleanwise.service.api.value.MultiproductView">
                <bean:define id="multiproductId" name="m" property="multiproductId"/>
                <html:option value="<%=multiproductId.toString()%>"><bean:write name="m" property="multiproductName"/></html:option>
             </logic:iterate>
        </html:select>
       <html:button property="action" value="Set" styleClass="text" onclick="Submit('MultiproductIdInp')"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

  </tr>
   <% } %>

  <tr>
         <td colspan="5">
        <table ID=598><tr>
          <td>
          <table ID=599>
             <tr><td colspan='3'>
               <html:button property="action" value="Set All" styleClass="text" onclick="Submit('SetAll')"/>
             </td>
             </tr>
            <tr>
           <td>
           <tr><td>

          <table ID=600>
              <tr> <td class='tableheader'><b>Action:</b></td></tr>
          </table>
          </td><td>
          <table ID=601>
             <tr>
                <td><b>Items - Catalog:</b></td>
                <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="tickItemsToCatalog" value="true"/></td>
             </tr>
             <tr>
                 <td><b>Items - Price:</b></td>
                 <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="tickItemsToContract" value="true"/></td>
             </tr>
             <tr>
                 <td><b>Items - Order Guide:</b></td>
                 <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="tickItemsToOrderGuide" value="true"/></td>
             </tr>
          </table>
          </td><td>
          <table ID=602>
             <tr><td><html:submit styleClass="text" property="action" value="Add"/></td></tr>
             <tr><td><html:submit styleClass="text" property="action" value="Remove"/></td></tr>
          </table>
          </td>
            </tr>
          </table>
          </td><td>
          <table ID=603>
              <tr> <td class='tableheader'><b>View:</b></td></tr>
          </table>
          </td><td>
          <table ID=604>
             <tr>
                <td><b>Dist Sku#:</b></td>
                <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="showDistSkuNumFl" value="true"/></td>
             </tr>
             <tr>
                 <td><b>Dist Uom & Pack:</b></td>
                 <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="showDistUomPackFl" value="true"/></td>
             </tr>
             <tr>
                 <td><b>Tax Exempt:</b></td>
                 <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="showTaxExemptFl" value="true"/></td>
             </tr>
              <logic:present name="STORE_ITEM_CATALOG_FORM" property="store">
                  <logic:equal name="STORE_ITEM_CATALOG_FORM"  property="store.allowSpecialPermission" value="true">
                      <%if(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(catalogD.getCatalogTypeCd())){%>
                      <tr>
                          <td><b>Special Permission:</b></td>
                          <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="showSpecialPermissionFl" value="true"/></td>
                      </tr>
                      <%}%>
                  </logic:equal>
              </logic:present>
             <tr>
                 <td><b>Cust Sku#:</b></td>
                 <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="showCustSkuNumFl" value="true"/></td>
             </tr>
             <tr>
                 <td><b>Service Fee Code:</b></td>
                 <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="showServiceFeeCodeFl" value="true"/></td>
             </tr>
           </table>
          </td><td>
          <table ID=605>
             <tr>
                 <td><b>Standard Product List:</b></td>
                 <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="showDistSplFl" value="true"/></td>
             </tr>
             <tr>
                <td><b>Multi Product:</b></td>
                <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="showMultiproductsFl" value="true"/></td>
            </tr>
            <tr>
                 <td><b>Base Cost:</b></td>
                 <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="showBaseCostFl" value="true"/></td>
			</tr>
             <tr>
                 <td><b>Generic Manuf:</b></td>
                 <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="showGenManufFl" value="true"/></td>
             </tr>
             <tr>
                 <td><b>Gen Manuf Sku#</b></td>
                 <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="showGenManufSkuNumFl" value="true"/></td>
             </tr>
          </table>
          </td><td>
          <table ID=606>
             <tr><td><html:submit property="action" value="Set View" styleClass='text'/></td></tr>
          </table>
          </td><td>
          <table ID=607>
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
<logic:present name="STORE_ITEM_CATALOG_FORM" property="itemAggrVector">
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

<table id="res" width="100%" class="results">
<tr align=center>
<td class="tableheader"><% colQty++;%>
<a ID=608 class="text" href="javascript:SetCheckedGlobal(1,'selectFl')">[Chk.&nbsp;All]</a>
<br>
<a ID=609 class="text" href="javascript:SetCheckedGlobal(0,'selectFl')">[&nbsp;Clear]</a>
<br>
<a ID=610 class="text" href="javascript:setCheckBetween('selectFl')">[Chk&nbsp;Zone]</a>

</td>
<th><% colQty++;%><a ID=611 href="#pgsort" class="tableheader" onclick="sortSubmit('SkuNum');">Sku #</a></th>
<th><% colQty++;%><a ID=612 href="#pgsort" class="tableheader" onclick="sortSubmit('ItemName');">Name</a></th>
<th><% colQty++;%>
  <a ID=613 href="#pgsort" class="tableheader" onclick="sortSubmit('SkuSize');">Size</a>
  <br><a ID=614 href="#pgsort" class="tableheader" onclick="sortSubmit('SkuUom');">Uom</a>
  <br><a ID=615 href="#pgsort" class="tableheader" onclick="sortSubmit('SkuPack');">Pack</a>
</th>
<th><% colQty++;%><a ID=616 href="#pgsort" class="tableheader" onclick="sortSubmit('ManufName');">Manufacturer</a></th>
<th><% colQty++;%><a ID=617 href="#pgsort" class="tableheader" onclick="sortSubmit('ManufSku');">Manuf Sku</a></th>
<th><% colQty++;%><a ID=618 href="#pgsort" class="tableheader" onclick="sortSubmit('OrderGuideName');">Order Guide</a></th>
<th><% colQty++;%><a ID=619 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogFlInp');">C</a></th>
<th><% colQty++;%><a ID=620 href="#pgsort" class="tableheader" onclick="sortSubmit('ContractFlInp');">P</a></th>
<th><% colQty++;%><a ID=621 href="#pgsort" class="tableheader" onclick="sortSubmit('OrderGuideFlInp');">O</a></th>
<th><% colQty++;%><a ID=622 href="#pgsort" class="tableheader" onclick="sortSubmit('CategoryName');">Categ</a></th>
<th><% colQty++;%><a ID=623 href="#pgsort" class="tableheader" onclick="sortSubmit('DistIdInp');">Dist.Id</a></th>
<th><% colQty++;%><a ID=624 href="#pgsort" class="tableheader" onclick="sortSubmit('DistName');">Dist Name</a></th>
<% if (theForm.getShowDistCostFl()) { %>
<th><% colQty++;%><a ID=625 href="#pgsort" class="tableheader" onclick="sortSubmit('CostInp');">Dist<br>Cost</a></th>
<% } %>
<th><% colQty++;%><a ID=626 href="#pgsort" class="tableheader" onclick="sortSubmit('PriceInp');">Cust.<br>Price </a></th>
<% if(theForm.getShowMultiproductsFl()) {%>
<th><% colQty++;%><a ID=627 href="#pgsort" class="tableheader" onclick="sortSubmit('MultiproductIdInp');">Multi<br>Product Id</th>
<th><% multiproductNameIndex=colQty; colQty++;%>
  <a ID=648 href="#pgsort" class="tableheader" onclick="sortSubmit('MultiproductName');">Multiproduct</th>
<% } %>
 <% if(theForm.getShowBaseCostFl()) {%>
 <th><% colQty++;%><a ID=6271 href="#pgsort" class="tableheader" onclick="sortSubmit('BaseCostInp');">Base<br>Cost</a></th>
<% } %>
<% if(theForm.getShowCustSkuNumFl()) {%>
<th><% colQty++;%><a ID=628 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogSkuNumInp');">Cust.<br>Sku#</a></th>
<% } %>
<% if(theForm.getShowDistSkuNumFl()) {%>
<th><% colQty++;%><a ID=629 href="#pgsort" class="tableheader" onclick="sortSubmit('DistSkuNumInp');">Dist.<br>Sku#</a></th>
<% } %>
<% if(theForm.getShowDistUomPackFl()) {%>
<th><% colQty++;%><a ID=631 href="#pgsort" class="tableheader" onclick="sortSubmit('DistSkuPackInp');">Dist.<br>Pack</a></th>
<th><% colQty++;%><a ID=632 href="#pgsort" class="tableheader" onclick="sortSubmit('DistSkuUomInp');">Dist.<br>UOM</a></th>
<% } %>
<% if(theForm.getShowDistUomPackFl()) {%>
<th><% colQty++;%><a ID=634 href="#pgsort" class="tableheader" onclick="sortSubmit('DistConversInp');">Dist. UOM Mult.</a></th>
<% } %>
<% if(theForm.isShowTaxExemptFl()){%>
<th><% colQty++;%><a ID=635 href="#pgsort" class="tableheader" onclick="sortSubmit('taxExemptFlInp');">Tax&nbsp;Ex.</a><br><a
 ID=636  class="text" href="javascript:setChecked(true,'taxExemptFl');">[Chk.&nbsp;All]</a><br><a
 ID=637  class="text" href="javascript:setChecked(false,'taxExemptFl');">[&nbsp;Clear]</a>
</th>
<% } %>
<% if(isShowSpecialPermission){%>
<th><% colQty++;%><a ID=635 href="#pgsort" class="tableheader" onclick="sortSubmit('specialPermissionFlInp');">Special Permission</a><br><a
 class="text" href="javascript:setChecked(true,'specialPermissionFl');">[Chk.&nbsp;All]</a><br><a
 class="text" href="javascript:setChecked(false,'specialPermissionFl');">[&nbsp;Clear]</a>
</th>
<% } %>
<% if(theForm.isShowDistSplFl()){%>
<th><% colQty++;%><a ID=638 href="#pgsort" class="tableheader" onclick="sortSubmit('distSPLFlInp');">SPL</a><br><a
 ID=639  class="text" href="javascript:setChecked(true,'distSPLFl');">[Chk.&nbsp;All]</a><br><a
 ID=640  class="text" href="javascript:setChecked(false,'distSPLFl');">[&nbsp;Clear]</a>
</th>
<% } %>
<% if(theForm.getShowGenManufFl()) {%>
<th><% colQty++;%><a ID=641 href="#pgsort" class="tableheader" onclick="sortSubmit('GenManufIdInp');">Gen.<br>Manuf Id</th>
<th><% genMfgNameIndex=colQty; colQty++;%>
  <a ID=642 href="#pgsort" class="tableheader" onclick="sortSubmit('GenManufName');">Gen.<br>Manuf</th>
<% } %>
<% if(theForm.getShowGenManufSkuNumFl()) {%>
<th><% colQty++;%><a ID=643 href="#pgsort" class="tableheader" onclick="sortSubmit('GenManufSkuNumInp');">Gen.<br>Manuf Sku#</th>
<% } %>
<!-- th><a ID=644 href="#pgsort" class="tableheader" onclick="sortSubmit('CostCenterIdInp');">Cost<br>Center</a></a></th -->
<% if(theForm.getShowServiceFeeCodeFl()) {%>
<th><% colQty++;%><a ID=645 href="#pgsort" class="tableheader" onclick="sortSubmit('ServiceFeeCodeInp');">Service<br>Fee</a></th>
<% } %>
</tr>

<tbody id="resTblBdy">
   <logic:iterate id="elle" name="STORE_ITEM_CATALOG_FORM" property="itemAggrVector" indexId="ii" type="ItemCatalogAggrView">
   <%
     String prop = "selectFl["+ii+"]";
     int itemId = elle.getItemId();
     String itemIdS = ""+itemId;
     String skuNum = elle.getSkuNum();
     String skuName = elle.getItemName();
     String skuSize = elle.getSkuSize();
     String skuUom = elle.getSkuUom();
     String skuPack = elle.getSkuPack();
     String manufName = elle.getManufName();
     String manufSku = elle.getManufSku();


     String catalogIdS = ""+elle.getCatalogId();
     String ogIdS = ""+elle.getOrderGuideId();
     boolean catalogFl = elle.getCatalogFlInp();
     String catalogSign = (catalogFl)?"X":"-";
     boolean contractFl = elle.getContractFlInp();
     String contractSign = (contractFl)?"X":"-";
     boolean ogFl = elle.getOrderGuideFlInp();
     String ogSign = (ogFl)?"X":"-";
     String categoryName = Utility.strNN(elle.getCategoryName());
     String distIdInp = "distIdInp["+ii+"]";
     String distName = Utility.strNN(elle.getDistName());
     String costInp = "costInp["+ii+"]";
     String costInpNav = "keyDown("+ii+",'costInp')";
     //String costInpNav = "keyDown("+ii+",'costInp')";
     String priceInp = "priceInp["+ii+"]";
     String baseCostInp = "baseCostInp["+ii+"]";
     String multiproductIdInp = "multiproductIdInp["+ii+"]";
     String multiproductName = Utility.strNN(elle.getMultiproductName());
     String catalogSkuNumInp = "catalogSkuNumInp["+ii+"]";
     String distSkuNumInp = "distSkuNumInp["+ii+"]";
     String distErpSkuNum = Utility.strNN(elle.getDistErpSkuNum());
     String distSkuPackInp = "distSkuPackInp["+ii+"]";
     String distSkuUomInp = "distSkuUomInp["+ii+"]";
     String distErpUom = Utility.strNN(elle.getDistErpUom());
     String distConversInp = "distConversInp["+ii+"]";
     String taxExemptFlInp = "taxExemptFlInp["+ii+"]";
     String taxExemptFlContr = "taxExemptFlContr["+ii+"]";
     String toggleTaxExemptFl = "toggle(this,'"+taxExemptFlInp+"')";
     String specialPermissionFlInp = "specialPermissionFlInp["+ii+"]";
     String specialPermissionFlContr = "specialPermissionFlContr["+ii+"]";
     String toggleSpecialPermissionFl = "toggle(this,'"+specialPermissionFlInp+"')";


     String distSPLFlInp = "distSPLFlInp["+ii+"]";
     String distSPLFlContr = "distSPLFlContr["+ii+"]";
     String toggleSPLFl = "toggle(this,'"+distSPLFlInp+"')";
     String genManufIdInp = "genManufIdInp["+ii+"]";
     String genManufName = Utility.strNN(elle.getGenManufName());
     String genManufSkuNumInp = "genManufSkuNumInp["+ii+"]";
     String ogName = Utility.strNN(elle.getOrderGuideName());
     //String costCenterIdInp = "costCenterIdInp["+ii+"]";
     String costCenterIdInp = Utility.strNN(elle.getCostCenterIdInp());
     String cpoSubmitC = "cpoSubmit('"+itemIdS+"','"+catalogIdS+"','0','cpoC');";
     String cpoSubmitP = "cpoSubmit('"+itemIdS+"','"+catalogIdS+"','0','cpoP');";
     String cpoSubmitOg = "cpoSubmit('"+itemIdS+"','"+catalogIdS+"','"+ogIdS+"','cpoOg');";
     String serviceFeeCodeInp = "serviceFeeCodeInp["+ii+"]";
     int index = ii.intValue();
     rowQty = index+1;
     rowNum++;
    %>

    <% if(index%25==0 && index>0) {
       rowNum++;
    %>
        <tr align=center>
        <td class="tableheader">&nbsp;</td>
        <th>Sku&nbsp;#</th>
        <th>Name</th>
        <th>S&nbsp;U&nbsp;P</th>
        <th>Manuf.</th>
        <th>Manuf.&nbsp;Sku</th>
        <th>O.Guide</th>
        <th>C</th>
        <th>P</th>
        <th>O</th>
        <th>Categ</th>
        <th>D.Id</th>
        <th>D.Name</th>
        <% if(theForm.getShowDistCostFl()) { %>
        <th>Dist<br>Cost</th>
        <% } %>
        <th>Cust.<br>Price</th>
        <% if(theForm.getShowMultiproductsFl()) {%>
        <th>Mult.Id</th>
        <th>Multipr.</th>
        <% } %>
		<% if(theForm.getShowBaseCostFl()) {%>
        <th>B.Cost</th>
        <% } %>
        <% if(theForm.getShowCustSkuNumFl()) {%>
        <th>C.Sku#</th>
        <% } %>
        <% if(theForm.getShowDistSkuNumFl()) {%>
        <th>D.Sku#</th>
        <% } %>
        <% if(theForm.getShowDistUomPackFl()) {%>
        <th>D.Pack</th>
        <th>D.UOM</th>
        <% } %>
        <% if(theForm.getShowDistUomPackFl()) {%>
        <th>Mult</th>
        <% } %>
        <% if(theForm.isShowTaxExemptFl()){%>
        <th>
        Tax&nbsp;Ex.
        </th>
        <% } %>
        <% if(isShowSpecialPermission){%>
        <th>
        Special Permission
        </th>
        <% } %>
        <% if(theForm.isShowDistSplFl()){%>
        <th>
        SPL
        </th>
        <% } %>
        <% if(theForm.getShowGenManufFl()) {%>
        <th>G.M.Id</th>
        <th>G.M</th>
        <% } %>
        <% if(theForm.getShowGenManufSkuNumFl()) {%>
        <th>G.M.Sku#</th>
        <% } %>
        <% if(theForm.getShowServiceFeeCodeFl()) {%>
        <th>Service<br>Fee</th>
        <% } %>
        </tr>
    <% } %>

   <tr>
   <% String distIdChange = "requestDistSkuInfo("+itemId+","+ii+","+rowNum+")"; %>

    <td align="center"><html:multibox name="STORE_ITEM_CATALOG_FORM" property="<%=prop%>" value="true"/></td>
    <td><%=skuNum%></td>
    <td><%=skuName%></td>
    <td><%=skuSize%><br><%=skuUom%><br><%=skuPack%></td>
    <td><%=manufName%></td>
    <td><%=manufSku%></td>
    <td><%=ogName%></td>
    <td><a ID=645 href="#cpo"  onclick="<%=cpoSubmitC%>"><%=catalogSign%></a></td>
    <td><a ID=646 href="#cpo"  onclick="<%=cpoSubmitP%>"><%=contractSign%></a></td>
    <td><a ID=647 href="#cpo"  onclick="<%=cpoSubmitOg%>"><%=ogSign%></a></td>
    <td><% if(catalogFl) {%><%=categoryName%><% } else {%>&nbsp;<% } %></td>
    <td><% if(catalogFl) {%><html:text  property="<%=distIdInp%>" onkeydown="navigate(this);" onchange='<%=distIdChange%>' size="5"/><% } else {%>&nbsp;<% } %></td>
    <td><% if(catalogFl) {%><%=distName%><% } else {%>&nbsp;<% } %></td>
<% if(theForm.getShowDistCostFl()) { %>
    <td><% if(contractFl) {%><html:text property="<%=costInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="6"/><% } else {%>&nbsp;<% } %></td>
    <% } %>
    <td><% if(contractFl) {%><html:text property="<%=priceInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="6"/><% } else {%>&nbsp;<% } %></td>
<% if(theForm.getShowMultiproductsFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=multiproductIdInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="7"/><% } else {%>&nbsp;<% } %></td>
    <td><% if(catalogFl) {%><%=multiproductName%><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowBaseCostFl()) {%>
    <td><% if(contractFl) {%><html:text property="<%=baseCostInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="6"/><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowCustSkuNumFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=catalogSkuNumInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="8"/><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowDistSkuNumFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=distSkuNumInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="10"/><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowDistUomPackFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=distSkuPackInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="2"/><% } else {%>&nbsp;<% } %></td>
    <td><% if(catalogFl) {%><html:text property="<%=distSkuUomInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="2"/><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowDistUomPackFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=distConversInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="2"/><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.isShowTaxExemptFl()){%>
    <td align='center'>
      <% if(catalogFl) {%>
        <% if (elle.getTaxExemptFlInp()) { %>
        <input name="<%=taxExemptFlContr%>" type='checkbox' checked  onchange="<%=toggleTaxExemptFl%>"/>
        <% } else { %>
        <input name="<%=taxExemptFlContr%>" type='checkbox' onchange="<%=toggleTaxExemptFl%>"/>
        <% } %>
        <html:hidden property="<%=taxExemptFlInp%>"  value='<%=""+elle.getTaxExemptFlInp()%>'/>
      <% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(isShowSpecialPermission){%>
    <td align='center'>
        <% if(catalogFl) {%>  <% if (elle.getSpecialPermissionFlInp()) { %>
        <input name="<%=specialPermissionFlContr%>" type='checkbox' checked  onchange="<%=toggleSpecialPermissionFl%>"/>
        <% } else { %>
        <input name="<%=specialPermissionFlContr%>" type='checkbox' onchange="<%=toggleSpecialPermissionFl%>"/>
        <% } %>
        <html:hidden property="<%=specialPermissionFlInp%>"  value='<%=""+elle.getSpecialPermissionFlInp()%>'/>
        <% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.isShowDistSplFl()){%>
    <td align='center'>
      <% if(catalogFl) {%>
        <% if (elle.getDistSPLFlInp()) { %>
        <input name="<%=distSPLFlContr%>" type='checkbox' checked  onchange="<%=toggleSPLFl%>"/>
        <% } else { %>
        <input name="<%=distSPLFlContr%>" type='checkbox'  onchange="<%=toggleSPLFl%>"/>
        <% } %>
        <html:hidden property="<%=distSPLFlInp%>"  value='<%=""+elle.getDistSPLFlInp()%>'/>
      <% } else {%>&nbsp;<% } %>
    </td>
<% } %>
<% if(theForm.getShowGenManufFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=genManufIdInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="7"/><% } else {%>&nbsp;<% } %></td>
    <td><% if(catalogFl) {%><%=genManufName%><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowGenManufSkuNumFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=genManufSkuNumInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="10"/><% } else {%>&nbsp;<% } %></td>
<% } %>
    <!-- td><% if(catalogFl) {%><%=costCenterIdInp%><% } else {%>&nbsp;<% } %></td -->
<% if(contractFl && theForm.getShowServiceFeeCodeFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=serviceFeeCodeInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="8"/><% } else {%>&nbsp;<% } %></td>
<% } %>
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

           //Set dist item pack
           var distSkuPackInpElemName = "distSkuPackInp["+index+"]";
           var distSkuPackInpElemOjb = document.all[distSkuPackInpElemName];
           if('undefined' != typeof distSkuPackInpElemOjb) {
             var  distItemPackObj = req.responseXML.getElementsByTagName("DistItemPack")[0];
             var  distItemPack = "";
             if(distItemPackObj!=null && 'undefined' != typeof distItemPackObj) {
               distItemPack = distItemPackObj.childNodes[0].nodeValue;
             }
             distSkuPackInpElemOjb.value = distItemPack;
           }

           //Set dist item uom
           var distSkuUomInpElemName = "distSkuUomInp["+index+"]";
           var distSkuUomInpElemObj = document.all[distSkuUomInpElemName];
           if('undefined' != typeof distSkuUomInpElemObj) {
             var  distItemUomObj = req.responseXML.getElementsByTagName("DistItemUom")[0];
             var  distItemUom = "";
             if(distItemUomObj!=null && 'undefined' != typeof distItemUomObj) {
               distItemUom = distItemUomObj.childNodes[0].nodeValue;
             }
             distSkuUomInpElemObj.value = distItemUom;
           }

           //Set dist uom convert
           var distConversInpElemName = "distConversInp["+index+"]";
           var distConversInpElemObj = document.all[distConversInpElemName];
           if('undefined' != typeof distConversInpElemObj) {
             var  distUomConvMultiplierObj = req.responseXML.getElementsByTagName("DistUomConvMultiplier")[0];
             var  distUomConvMultiplier = "";
             if(distUomConvMultiplierObj!=null && 'undefined' != typeof distUomConvMultiplierObj) {
               distUomConvMultiplier = distUomConvMultiplierObj.childNodes[0].nodeValue;
             }
             distConversInpElemObj.value = distUomConvMultiplier;
           }

           var multiproductIdInpElemName = "multiproductIdInp["+index+"]";
           var multiproductIdInpElemObj = document.all[multiproductIdInpElemName];
           if('undefined' != typeof multiproductIdInpElemObj) {
             var  mult1IdObj = req.responseXML.getElementsByTagName("Mult1Id")[0];
             var  mult1Id = "";
             if(mult1IdObj!=null && 'undefined' != typeof mult1IdObj) {
               mult1Id = mult1IdObj.childNodes[0].nodeValue;
               if(mult1Id=='0') mult1Id = "";
             }
             multiproductIdInpElemObj.value = mult1Id;
           }

           <% if(multiproductNameIndex>0) {%>
           var  mult1NameObj = req.responseXML.getElementsByTagName("Mult1Name")[0];
           var  mult1Name = "";
           if(mult1NameObj!=null && ('undefined' != typeof mult1NameObj)) {
             mult1Name = mult1NameObj.childNodes[0].nodeValue;
           }
           document.all.res.rows[rowNum].cells[<%=multiproductNameIndex%>].innerHTML = mult1Name ;
           <% } %>

           //Generic manuf id
           var genManufIdInpElemName = "genManufIdInp["+index+"]";
           var genManufIdInpElemObj = document.all[genManufIdInpElemName];
           if('undefined' != typeof genManufIdInpElemObj) {
             var  mfg1IdObj = req.responseXML.getElementsByTagName("Mfg1Id")[0];
             var  mfg1Id = "";
             if(mfg1IdObj!=null && 'undefined' != typeof mfg1IdObj) {
               mfg1Id = mfg1IdObj.childNodes[0].nodeValue;
               if(mfg1Id=='0') mfg1Id = "";
             }
             genManufIdInpElemObj.value = mfg1Id;
           }

           //Generic manuf itme sku
           var genManufSkuNumInpElemName = "genManufSkuNumInp["+index+"]";
           var genManufSkuNumInpElemObj = document.all[genManufSkuNumInpElemName];
           if('undefined' != typeof genManufSkuNumInpElemObj) {
             var  mfg1ItemSkuObj = req.responseXML.getElementsByTagName("Mfg1ItemSku")[0];
             var  mfg1ItemSku = "";
             if(mfg1ItemSkuObj!=null && 'undefined' != typeof mfg1ItemSkuObj) {
               mfg1ItemSku =  mfg1ItemSkuObj.childNodes[0].nodeValue;
             }
             genManufSkuNumInpElemObj.value = mfg1ItemSku;
           }

           //Dist Name
           var  distNameObj = req.responseXML.getElementsByTagName("DistName")[0];
           var  distName = "";
           if(distNameObj!=null && ('undefined' != typeof distNameObj)) {
             distName = distNameObj.childNodes[0].nodeValue;
           }
           document.all.res.rows[rowNum].cells[12].innerHTML = distName ;

           //Generic manuf name
           <% if(genMfgNameIndex>0) {%>
           var  mfg1NameObj = req.responseXML.getElementsByTagName("Mfg1Name")[0];
           var  mfg1Name = "";
           if(mfg1NameObj!=null && ('undefined' != typeof mfg1NameObj)) {
             mfg1Name = mfg1NameObj.childNodes[0].nodeValue;
           }
           document.all.res.rows[rowNum].cells[<%=genMfgNameIndex%>].innerHTML = mfg1Name ;
           <% } %>
        }
    }
}

function requestDistSkuInfo(pItemId, pIndex,pRowNum) {
   var firefox = navigator.userAgent.toLowerCase().indexOf("firefox") != -1;
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

   var url = "cat-item.do?action=DistSkuInfo&distId="+distId+"&itemId="+pItemId+"&index="+pIndex;
   if (window.XMLHttpRequest) {
     req = new XMLHttpRequest();
   } else if (window.ActiveXObject) {
     req = new ActiveXObject("Microsoft.XMLHTTP");
   }
   req.open("GET", url, true);
    if (firefox){
      req.onload = req.onerror = req.onabort = setDistSkuInfo;
   } else {
      req.onreadystatechange = setDistSkuInfo;
   }
   req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
   req.send(null);
}
-->
</script>
