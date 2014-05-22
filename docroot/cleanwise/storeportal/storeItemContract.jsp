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
          //alert (document.forms[0]) ;
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
  ProductData currManagingItem = theForm.getCurrManagingItem();
  boolean viewOGFl=theForm.getControlMultipleCatalogFl()?false:true;
  int colQty = 0;
  int rowQty = 0;
  int rowNum = 0;
  int genMfgNameIndex = 0;
  int multiproductNameIndex = 0;
%>


<div class="text">

<jsp:include flush='true' page="locateStoreCatalog.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/itemcontract.do" />
   <jsp:param name="jspFormName" 	value="STORE_ITEM_CATALOG_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="catalogFilter"/>
</jsp:include>


<jsp:include flush='true' page="locateStoreAccount.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/itemcontract.do" />
   <jsp:param name="jspFormName" 	value="STORE_ITEM_CATALOG_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
</jsp:include>



<jsp:include flush='true' page="locateStoreItem.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/itemcontract.do" />
   <jsp:param name="jspFormName" 	value="STORE_ITEM_CATALOG_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="itemFilter"/>
   <jsp:param name="jspCatalogListProperty" value="catalogListSelectedItemContract"/>
   <jsp:param name="checkBoxShowInactive"  value="hide"/>
</jsp:include>


<jsp:include flush='true' page="locateStoreDistributor.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/itemcontract.do" />
   <jsp:param name="jspFormName" 	value="STORE_ITEM_CATALOG_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterPropertyAlt" value="distDummyConvert"/>
   <jsp:param name="jspReturnFilterProperty" value="distFilter"/>
</jsp:include>

  <table ID=887 cellspacing="0" border="0" width="769" class="mainbody">
  <html:form styleId="888"  action="/storeportal/itemcontract.do"
            scope="session">
  <tr> <td colspan='4'>
  <%
   boolean oneCatalogFl = false;
   boolean filterFl = false;
   CatalogDataVector catalogDV = theForm.getCatalogFilter();
   if(catalogDV!=null && catalogDV.size()>0) {
     filterFl = true;
     if(catalogDV.size()==1) oneCatalogFl = true;
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
   ItemViewVector itemVwV = theForm.getItemFilter();
   if(itemVwV!=null && itemVwV.size()>0) {
      filterFl = true;
  %>
  <b>Items:</b>
  <%
     for(int ii=0; ii<itemVwV.size(); ii++) {
        ItemView itemVw = (ItemView) itemVwV.get(ii);
  %>
   &lt;<%=itemVw.getSku()%>&gt;
   <% } %>
  <html:submit property="action" value="Clear Item Filter" styleClass='text'/>
   <% } %>
  <html:submit property="action" value="Locate Item" styleClass='text'/>
<% if(currManagingItem!=null || filterFl) { %>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <html:submit property="action" value="Clear Filters" styleClass='text'/>
<% } %>
 </td></tr>
  </table>
<!-- /////////////////////////////////////////////////////////////////////////////////////////////////// -->
<% if(currManagingItem!=null) { %>
<table ID=889 border="0" width="769" class="mainbody">
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
        <b>Sku:</b>&nbsp;<bean:write name="STORE_ITEM_CATALOG_FORM" property="currManagingItem.skuNum"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Name:</b>&nbsp;<bean:write name="STORE_ITEM_CATALOG_FORM" property="currManagingItem.shortDesc"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Size:</b>&nbsp;<bean:write name="STORE_ITEM_CATALOG_FORM" property="currManagingItem.size"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Uom:</b>&nbsp;<bean:write name="STORE_ITEM_CATALOG_FORM" property="currManagingItem.uom"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Pack:</b>&nbsp;<bean:write name="STORE_ITEM_CATALOG_FORM" property="currManagingItem.pack"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Item Id:</b>&nbsp;<bean:write name="STORE_ITEM_CATALOG_FORM" property="currManagingItem.productId"/>
        </td>
  </tr>
  <tr class="results">
        <td>&nbsp;</td>
        <td colspan='4'>
        <b>Manufacturer:</b>&nbsp;<bean:write name="STORE_ITEM_CATALOG_FORM" property="currManagingItem.manufacturerName"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Manufacturer Sku:</b>&nbsp;<bean:write name="STORE_ITEM_CATALOG_FORM" property="currManagingItem.manufacturerSku"/>
        </td>
  </tr>
     <tr class="results">
    <td>&nbsp;</td>
       <td colspan='4'><b>View  Order Guides:</b> &nbsp;&nbsp;<html:radio styleClass="text" onclick="Submit('Set View Order Guides');" property="controlMultipleCatalogFl" value="false"/>Yes
     <html:radio onclick="Submit('Set View Order Guides');" styleClass="text" property="controlMultipleCatalogFl" value="true"/>No
       </td>
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
        <!--
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Cost&nbsp;Center:</b>&nbsp;<html:text name="STORE_ITEM_CATALOG_FORM" property="costCenterIdDummy" size="6"/>
        <html:button property="action" value="Set" styleClass="text" onclick="Submit('CostCenterIdInp')"/>
        -->
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <% if(theForm.getShowCustSkuNumFl()) {%>
        <b>Cust&nbsp;Sku#:</b>&nbsp;<html:text name="STORE_ITEM_CATALOG_FORM" property="catalogSkuNumDummy" size="6"/>
        <html:button property="action" value="Set" styleClass="text" onclick="Submit('CatalogSkuNumInp')"/>
        <% } %>

        </td>
  </tr>

   <tr>
        <td>&nbsp;</td>
        <td colspan='5'>
          <b>Distr.:</b>
      <logic:present name="STORE_ITEM_CATALOG_FORM" property="distDummy">
                <bean:write  name="STORE_ITEM_CATALOG_FORM" property="distDummy.busEntity.shortDesc"/>(<bean:write  name="STORE_ITEM_CATALOG_FORM" property="distDummy.busEntity.busEntityId"/>)
      </logic:present>
       <html:button property="action" value="Locate" styleClass='text' onclick="Submit('LocateAssignDistributor');"/>
       <html:button property="action" value="Clean" styleClass='text' onclick="Submit('CleanAssignDistributor');"/>
       <html:button property="action" value="Set" styleClass="text" onclick="Submit('DistIdInp')"/>
       <html:button property="action" value="Set default" styleClass="text" onclick="Submit('SetDefaultDist')"/>
        &nbsp;&nbsp;
        <b>Dist.Cost:</b>&nbsp;<html:text
            name="STORE_ITEM_CATALOG_FORM" property="costDummy" size="4"/>&nbsp;<html:button
            property="action" value="Set" styleClass="text" onclick="Submit('CostInp')"/>

       &nbsp;&nbsp;
        <b>Price:</b>&nbsp;<html:text  name="STORE_ITEM_CATALOG_FORM"
        property="priceDummy" size="4"/>&nbsp;<html:button property="action" value="Set" styleClass="text" onclick="Submit('PriceInp')"/>

       &nbsp;&nbsp;
       <% if(theForm.getShowBaseCostFl()) {%>
        <nobr><b>Base&nbsp;Cost:</b>&nbsp;<html:text
            name="STORE_ITEM_CATALOG_FORM"
            property="baseCostDummy" size="4"/>&nbsp;<html:button property="action"
            value="Set" styleClass="text" onclick="Submit('BaseCostInp')"/></nobr>
       <% } %>
  </tr>
  <% if(theForm.getShowDistSkuNumFl() || theForm.getShowDistUomPackFl()) {%>
  <tr>
        <td>&nbsp;</td>
        <td colspan='4'>
        <% if(theForm.getShowDistSkuNumFl()) {%>
          <b>Dist.&nbsp;Sku#:</b>
                <html:text  name="STORE_ITEM_CATALOG_FORM" property="distSkuNumDummy" size="9"/>
       <html:button property="action" value="Set" styleClass="text" onclick="Submit('DistSkuNumInp')"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <% } %>
        <% if(theForm.getShowDistUomPackFl()) {%>
        <b>Dist.&nbsp;Pack:</b>
                <html:text  name="STORE_ITEM_CATALOG_FORM" property="distSkuPackDummy" size="5"/>
       <html:button property="action" value="Set" styleClass="text" onclick="Submit('DistSkuPackInp')"/>

       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Dist.&nbsp;Uom:</b>
       <html:select  name="STORE_ITEM_CATALOG_FORM" property="distSkuUomDummy">
           <html:option value=""></html:option>
           <html:option value="BX">BX</html:option>
           <html:option value="CS">CS</html:option>
           <html:option value="CT">CT</html:option>
           <html:option value="DP">DP</html:option>
           <html:option value="DR">DR</html:option>
           <html:option value="DZ">DZ</html:option>
           <html:option value="EA">EA</html:option>
           <html:option value="GA">GA</html:option>
           <html:option value="PA">PA</html:option>
           <html:option value="PK">PK</html:option>
           <html:option value="PL">PL</html:option>
           <html:option value="PR">PR</html:option>
           <html:option value="TB">TB</html:option>
       </html:select>
       <html:button property="action" value="Set" styleClass="text" onclick="Submit('DistSkuUomInp')"/>

       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>Dist.&nbsp;Uom&nbsp;Multiplier:</b>
                <html:text  name="STORE_ITEM_CATALOG_FORM" property="distConversDummy" size="3"/>
       <html:button property="action" value="Set" styleClass="text" onclick="Submit('DistConversInp')"/>
       <% } %>
  </tr>
   <% } %>

 <% if(theForm.getShowGenManufFl() || theForm.getShowGenManufSkuNumFl()) {%>
 <tr>
        <td>&nbsp;</td>
        <td colspan='4'>
        <% if(theForm.getShowGenManufFl()) {%>
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
       <% } %>
        <% if(theForm.getShowGenManufSkuNumFl()) {%>
        <b>Gen. Manuf. Sku#:</b>
                <html:text  name="STORE_ITEM_CATALOG_FORM" property="genManufSkuNumDummy" size="5"/>
       <html:button property="action" value="Set" styleClass="text" onclick="Submit('GenManufSkuNumInp')"/>
       <% } %>

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
        <table ID=890><tr>
          <td>
          <table ID=891>
             <tr><td colspan='3'>
               <html:button property="action" value="Set All" styleClass="text" onclick="Submit('SetAll')"/>
             </td>
             </tr>
            <tr>
           <td>
           <tr><td>

          <table ID=892>
              <tr> <td class='tableheader'><b>Action:</b></td></tr>
          </table>
          </td><td>
          <table ID=893>
             <tr>
                <td><b>Items - Catalog:</b></td>
                <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="tickItemsToCatalog" value="true"/></td>
             </tr>
             <tr>
                 <td><b>Items - Price:</b></td>
                 <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="tickItemsToContract" value="true"/></td>
             </tr>

             <tr>
                <% if(viewOGFl){ %>
                 <td><b>Items - Order Guide:</b></td>
                 <td><html:multibox name="STORE_ITEM_CATALOG_FORM" property="tickItemsToOrderGuide" value="true"/></td>
                 <%} else { %>
                 <td colspan="2">&nbsp;</td>
                 <%}%>
             </tr>
          </table>
          </td><td>
          <table ID=894>
             <tr><td><html:submit styleClass="text" property="action" value="Add"/></td></tr>
             <tr><td><html:submit styleClass="text" property="action" value="Remove"/></td></tr>
          </table>
          </td>
            </tr>
          </table>
          </td><td>
          <table ID=895>
              <tr> <td class='tableheader'><b>View:</b></td></tr>
          </table>
          </td><td>
          <table ID=896>
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
          <table ID=897>
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
          <table ID=898>
             <tr><td><html:submit property="action" value="Set View" styleClass='text'/></td></tr>
          </table>
          </td><td>
          <table ID=899>
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


<table  id="res" width="100%" class="results">
<tr align=center>
<td class="tableheader"><% colQty++;%>
<a ID=900 class="text" href="javascript:SetCheckedGlobal(1,'selectFl')">[Chk.&nbsp;All]</a>
<br>
<a ID=901 class="text" href="javascript:SetCheckedGlobal(0,'selectFl')">[&nbsp;Clear]</a>
<br>
<a ID=902 class="text" href="javascript:setCheckBetween('selectFl')">[Chk&nbsp;Zone]</a>
</td>
<th><% colQty++;%><a ID=903 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogId');">Cat Id</a></th>
<th><% colQty++;%><a ID=904 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogName');">Catalog</a></th>
<th><% colQty++;%><a ID=905 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogType');">Type</a></th>
<th><% colQty++;%><a ID=906 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogStatus');">Status</a></th>
<th><% colQty++;%><a ID=907 href="#pgsort" class="tableheader" onclick="sortSubmit('OrderGuideName');">Order Guide</a></th>
<th><% colQty++;%><a ID=908 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogFlInp');">C</a></th>
<th><% colQty++;%><a ID=909 href="#pgsort" class="tableheader" onclick="sortSubmit('ContractFlInp');">P</a></th>
<th><% colQty++;%><a ID=910 href="#pgsort" class="tableheader" onclick="sortSubmit('OrderGuideFlInp');">O</a></th>
<th><% colQty++;%><a ID=911 href="#pgsort" class="tableheader" onclick="sortSubmit('CategoryName');">Categ</a></th>
<th><% colQty++;%><a ID=912 href="#pgsort" class="tableheader" onclick="sortSubmit('DistIdInp');">Dist.Id</a></th>
<th><% colQty++;%><a ID=913 href="#pgsort" class="tableheader" onclick="sortSubmit('DistName');">Dist Name</a></th>
<% if (theForm.getShowDistCostFl()) { %>
<th><% colQty++;%><a ID=914 href="#pgsort" class="tableheader" onclick="sortSubmit('CostInp');">Dist<br>Cost</a></th>
<% } %>
<th><% colQty++;%><a ID=915 href="#pgsort" class="tableheader" onclick="sortSubmit('PriceInp');">Cust.<br>Price </a></th>
<% if(theForm.getShowMultiproductsFl()) {%>
<th><% colQty++;%><a ID=916 href="#pgsort" class="tableheader" onclick="sortSubmit('MultiproductIdInp');">Multi<br>Product Id</th>
<th><% multiproductNameIndex=colQty; colQty++;%>
<a ID=937 href="#pgsort" class="tableheader" onclick="sortSubmit('MultiproductName');">Multiproduct</th>
<% } %>
<% if(theForm.getShowBaseCostFl()) {%>
<th><% colQty++;%><a ID=9161 href="#pgsort" class="tableheader" onclick="sortSubmit('BaseCostInp');">Base<br>Cost</a></th>
<% } %>
<% if(theForm.getShowCustSkuNumFl()) {%>
<th><% colQty++;%><a ID=917 href="#pgsort" class="tableheader" onclick="sortSubmit('CatalogSkuNumInp');">Cust.<br>Sku#</a></th>
<% } %>
<% if(theForm.getShowDistSkuNumFl()) {%>
<th><% colQty++;%><a ID=918 href="#pgsort" class="tableheader" onclick="sortSubmit('DistSkuNumInp');">Dist.<br>Sku#</a></th>
<% } %>
<% if(theForm.getShowDistUomPackFl()) {%>
<th><% colQty++;%><a ID=920 href="#pgsort" class="tableheader" onclick="sortSubmit('DistSkuPackInp');">Dist.<br>Pack</a></th>
<th><% colQty++;%><a ID=921 href="#pgsort" class="tableheader" onclick="sortSubmit('DistSkuUomInp');">Dist.<br>UOM</a></th>
<% } %>
<% if(theForm.getShowDistUomPackFl()) {%>
<th><% colQty++;%><a ID=923 href="#pgsort" class="tableheader" onclick="sortSubmit('DistConversInp');">Dist. UOM Mult.</a></th>
<% } %>
<% if(theForm.isShowTaxExemptFl()){%>
<th><% colQty++;%><a ID=924 href="#pgsort" class="tableheader" onclick="sortSubmit('taxExemptFlInp');">Tax&nbsp;Ex.</a><br><a
 ID=925  class="text" href="javascript:setChecked(true,'taxExemptFl');">[Chk.&nbsp;All]</a><br><a
 ID=926  class="text" href="javascript:setChecked(false,'taxExemptFl');">[&nbsp;Clear]</a>
</th>
<% } %>

<% if(theForm.isShowDistSplFl()){%>
<th><% colQty++;%><a ID=927 href="#pgsort" class="tableheader" onclick="sortSubmit('distSPLFlInp');">SPL</a><br><a
 ID=928  class="text" href="javascript:setChecked(true,'distSPLFl');">[Chk.&nbsp;All]</a><br><a
 ID=929  class="text" href="javascript:setChecked(false,'distSPLFl');">[&nbsp;Clear]</a>
</th>
<% } %>

<% if(theForm.getShowGenManufFl()) {%>
<th><% colQty++;%><a ID=930 href="#pgsort" class="tableheader" onclick="sortSubmit('GenManufIdInp');">Gen.<br>Manuf Id</th>
<th><% genMfgNameIndex=colQty; colQty++;%><a ID=931 href="#pgsort" class="tableheader" onclick="sortSubmit('GenManufName');">Gen.<br>Manuf</th>
<% } %>
<% if(theForm.getShowGenManufSkuNumFl()) {%>
<th><% colQty++;%><a ID=932 href="#pgsort" class="tableheader" onclick="sortSubmit('GenManufSkuNumInp');">Gen.<br>Manuf Sku#</th>
<% } %>
<!-- th><a ID=933 href="#pgsort" class="tableheader" onclick="sortSubmit('CostCenterIdInp');">Cost<br>Center</a></a></th -->
<% if(theForm.getShowServiceFeeCodeFl()) {%>
<th><% colQty++;%><a ID=934 href="#pgsort" class="tableheader" onclick="sortSubmit('ServiceFeeCodeInp');">Serv.<br>Fee<br>Code</a></th>
<% } %>
</tr>

<tbody id="resTblBdy">
   <logic:iterate id="elle" name="STORE_ITEM_CATALOG_FORM" property="itemAggrVector" indexId="ii" type="ItemCatalogAggrView">
<!-- ////////////////////////////////   -->
   <%
     String prop = "selectFl["+ii+"]";
     String itemIdS = ""+elle.getItemId();
     String catalogIdS = ""+elle.getCatalogId();
     String ogIdS = ""+elle.getOrderGuideId();
     boolean catalogFl = elle.getCatalogFlInp();
     String catalogSign = (catalogFl)?"X":"-";
     boolean contractFl = elle.getContractFlInp();
     String contractSign = (contractFl)?"X":"-";
     boolean ogFl = elle.getOrderGuideFlInp();
     String ogSign = (ogFl)?"X":"-";
     String catalogType = elle.getCatalogType().substring(0,4);
     String catalogStatus = elle.getCatalogStatus().substring(0,3);
     String categoryName = Utility.strNN(elle.getCategoryName());
     String distIdInp = "distIdInp["+ii+"]";
     String distName = Utility.strNN(elle.getDistName());
     String costInp = "costInp["+ii+"]";
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
        <th>Cat.Id</th>
        <th>Catalog</th>
        <th>Type</th>
        <th>Stat.</th>
        <th>O.Guide</th>
        <th>C</th>
        <th>P</th>
        <th>O</th>
        <th>Categ</th>
        <th>D.Id</th>
        <th>D.Name</th>
       <% if (theForm.getShowDistCostFl()) { %>
        <th>Cost</th>
       <% } %>
        <th>Price</th>
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
        Tax&nbs;Ex.
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
        <th>Serv.<br>Fee<br>Code</th>
        <% } %>
        </tr>
    <% } %>
   <tr>
    <% String distIdChange = "requestDistSkuInfo("+itemIdS+","+ii+","+rowNum+")"; %>
   <td align="center"><html:multibox name="STORE_ITEM_CATALOG_FORM" property="<%=prop%>" value="true"/></td>
    <td><bean:write name="elle" property="catalogId"/></td>
    <td><bean:write name="elle" property="catalogName"/></td>
    <td><%=catalogType%></td>
    <td><%=catalogStatus%></td>

    <td><%if(viewOGFl) {%><%=ogName%><%}%></td>

    <td><a ID=934 href="#cpo"  onclick="<%=cpoSubmitC%>"><%=catalogSign%></a></td>
    <td><a ID=935 href="#cpo"  onclick="<%=cpoSubmitP%>"><%=contractSign%></a></td>

    <td><%if(viewOGFl) {%><a ID=936 href="#cpo"  onclick="<%=cpoSubmitOg%>"><%=ogSign%></a><%}%></td>

    <td><% if(catalogFl) {%><%=categoryName%><% } else {%>&nbsp;<% } %></td>
    <td><% if(catalogFl) {%><html:text property="<%=distIdInp%>"  onkeydown="navigate(this);" onchange='<%=distIdChange%>' size="5"/><% } else {%>&nbsp;<% } %></td>
    <td><% if(catalogFl) {%><%=distName%><% } else {%>&nbsp;<% } %></td>
<% if(theForm.getShowDistCostFl()) {%>
    <td><% if(contractFl) {%><html:text property="<%=costInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="6"/><% } else {%>&nbsp;<% } %></td>
<% } %>
    <td><% if(contractFl) {%><html:text property="<%=priceInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="6"/><% } else {%>&nbsp;<% } %></td>
<% if(theForm.getShowMultiproductsFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=multiproductIdInp%>" onkeydown="navigate(this);" onchange='setRedSave()' size="7"/><% } else {%>&nbsp;<% } %></td>
    <td><% if(catalogFl) {%><%=multiproductName%><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowBaseCostFl()) {%>
    <td><% if(contractFl) {%><html:text property="<%=baseCostInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="6"/><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowCustSkuNumFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=catalogSkuNumInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="8"/><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowDistSkuNumFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=distSkuNumInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="10"/><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowDistUomPackFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=distSkuPackInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="2"/><% } else {%>&nbsp;<% } %></td>
    <td><% if(catalogFl) {%><html:text property="<%=distSkuUomInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="2"/><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowDistUomPackFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=distConversInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="2"/><% } else {%>&nbsp;<% } %></td>
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
    <td><% if(catalogFl) {%><html:text property="<%=genManufIdInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="7"/><% } else {%>&nbsp;<% } %></td>
    <td><% if(catalogFl) {%><%=genManufName%><% } else {%>&nbsp;<% } %></td>
<% } %>
<% if(theForm.getShowGenManufSkuNumFl()) {%>
    <td><% if(catalogFl) {%><html:text property="<%=genManufSkuNumInp%>"  onkeydown="navigate(this);" onchange='setRedSave()' size="10"/><% } else {%>&nbsp;<% } %></td>
<% } %>
    <!-- td><% if(catalogFl) {%><%=costCenterIdInp%><% } else {%>&nbsp;<% } %></td -->
<!-- ////////////////////////////////////-->
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

           //May be use later
           //Dist Name
           var  distNameObj = req.responseXML.getElementsByTagName("DistName")[0];
           var  distName = "";
           if(distNameObj!=null && ('undefined' != typeof distNameObj)) {
             distName = distNameObj.childNodes[0].nodeValue;
           }
           document.all.res.rows[rowNum].cells[11].innerHTML = distName ;

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

function requestDistSkuInfo(pItemId, pIndex, pRowNum) {
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
