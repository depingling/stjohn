<% { %>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.cleanwise.view.forms.LocateUploadItemForm" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>
<%@ page import="com.cleanwise.view.utils.StringUtils" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page contentType="text/html"%>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
String storeDir=ClwCustomizer.getStoreDir();
String jspFormName = request.getParameter("jspFormName");
if(jspFormName == null){
    throw new RuntimeException("jspFormName cannot be null");
}
Object tmpForm = session.getAttribute(jspFormName);
if(tmpForm == null){
    throw new RuntimeException("Could not find bean "+jspFormName+" in the session");
}
if(!(tmpForm instanceof StorePortalForm)){
    throw new RuntimeException("Bean "+jspFormName+" must be of type StorePortalForm");
}
boolean useOnlyIgnoreDistributorMatching = false;
LocateUploadItemForm theForm = ((StorePortalForm) tmpForm).getLocateUploadItemForm();
if (theForm != null) {
    if (theForm.getLocateUploadItemFl()) {
        theForm.setBaseForm((StorePortalForm) tmpForm);
        String jspSubmitIdent = request.getParameter("jspSubmitIdent");
        String jspFormAction = request.getParameter("jspFormAction");
        String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
        jspSubmitIdent += "#"+SessionAttributes.SEARCH_FORM.LOCATE_UPLOAD_ITEM_FORM;
        useOnlyIgnoreDistributorMatching = theForm.getUseOnlyIgnoreDistributorMatchingFl();
%>


<html:html>
<script language="JavaScript1.2">
<!--
function SetChecked(name, val) {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  found = false;
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    fname = dml[i].elements[j].name;
    if (fname.indexOf(name)==0) {
      dml[i].elements[j].checked=val;
      found = true;
    }
  }
  if(found) break;
 }
}

function SetAndSubmit (name, val) {
 dml=document.forms;
 for(i=0; i<dml.length; i++) {
  found = false;
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='action') {
      //alert('action.value='+dml[i].elements[j].value);
    }
    //alert('name='+dml[i].elements[j].name);
    if (dml[i].elements[j].name==name) {
      if(dml[i].elements[j].value==val) {
        found = true;
        dml[i].elements[j].checked=1;
      } else {
        dml[i].elements[j].checked=0;
      }
    }
  }
  //alert(found);
  if(found) {
   dml[i].submit();
  }
 }

}

//-->
</script>

<body>

<jsp:include flush='true' page="locateStoreDistributor.jsp">
   <jsp:param name="jspFormAction" 	value="<%=jspFormAction%>" />
   <jsp:param name="jspFormName"	value="<%=jspFormName%>" />
   <jsp:param name="jspSubmitIdent" 	value="<%=jspSubmitIdent%>" />
   <jsp:param name="jspReturnFilterProperty" value="locateUploadItemForm.distFilter"/>
   <jsp:param name="jspFormNestProperty" value="locateUploadItemForm" />
</jsp:include>

<jsp:include flush='true' page="locateStoreManufacturer.jsp">
   <jsp:param name="jspFormAction" 	value="<%=jspFormAction%>" />
   <jsp:param name="jspFormName"	value="<%=jspFormName%>" />
   <jsp:param name="jspSubmitIdent" 	value="<%=jspSubmitIdent%>" />
   <jsp:param name="jspReturnFilterProperty" value="locateUploadItemForm.manufFilter"/>
   <jsp:param name="jspFormNestProperty" value="locateUploadItemForm" />
</jsp:include>

<script src="../externals/lib.js" language="javascript"></script>

<div class="rptmid">
Upload Items

<table ID=416 border="1"  cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <table ID=417 width="750" border="0"  class="mainbody">
                <html:form styleId="418" action="<%=jspFormAction%>"  scope="session">
                <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
                <html:hidden property="locateUploadItemForm.property" value="<%=jspReturnFilterProperty%>"/>
                <html:hidden property="locateUploadItemForm.name" value="<%=jspFormName%>"/>

                <tr>
                    <td><b>&nbsp;</b></td>
                    <td colspan=4><b>Xls Table:</b>
                        <html:select  property="locateUploadItemForm.uploadId" >
                            <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                            <%
    UploadDataVector uploadDV = theForm.getTableList();
    for(Iterator iter=uploadDV.iterator(); iter.hasNext();) {
       UploadData uD = (UploadData) iter.next();
       String uploadIdS = ""+uD.getUploadId();
   %>
                            <html:option value="<%=uploadIdS%>"><%=uD.getFileName()%></html:option>
                            <% } %>
                        </html:select>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table ID=419 width="750" border="0"  class="mainbody">
                <!-- <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<     -->
                <tr>
                    <td colspan='4'>
                        <b>Distributor Filter:</b>
                        <%
                            DistributorDataVector distDV = theForm.getDistFilter();
                            if(distDV!=null && distDV.size()>0) {
                                for(int ii=0; ii<distDV.size(); ii++) {
                                    DistributorData distD = (DistributorData) distDV.get(ii);
                        %>
                        &lt;<%=distD.getBusEntity().getShortDesc()%>&gt;
                        <% } %>
                        <html:submit property="action" value="Clear Distributor Filter" styleClass='text'/>
                        <% } %>
                        <html:submit property="action" value="Locate Distributor" styleClass='text' />
                    </td>
                </tr>
                <!-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> -->
                <!-- <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<     -->
                <tr>
                    <td colspan='4'>
                        <b>Manufacturer Filter:</b>
                        <%
                            ManufacturerDataVector manufDV = theForm.getManufFilter();
                            if(manufDV!=null && manufDV.size()>0) {
                                for(int ii=0; ii<manufDV.size(); ii++) {
                                    ManufacturerData manufD = (ManufacturerData) manufDV.get(ii);
                        %>
                        &lt;<%=manufD.getBusEntity().getShortDesc()%>&gt;
                        <% } %>
                        <html:submit property="action" value="Clear Manufacturer Filter" styleClass='text'/>
                        <% } %>
                        <html:submit property="action" value="Locate Manufacturer" styleClass='text' />
                    </td>
                </tr>
                <!-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> -->
                <!-- <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<     -->
                <tr>
                    <td colspan='4'>
                        <b>SPL Filter:</b>
                        Any
                        <html:radio property='locateUploadItemForm.splFilter' value='<%=""+LocateUploadItemForm.SPL_FILTER_ANY%>'/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        Yes
                        <html:radio property='locateUploadItemForm.splFilter' value='<%=""+LocateUploadItemForm.SPL_FILTER_YES%>'/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        No
                        <html:radio property='locateUploadItemForm.splFilter' value='<%=""+LocateUploadItemForm.SPL_FILTER_NO%>'/>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
                <!-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> -->
                <%
                    UploadSkuViewVector uploadSkus = theForm.getUploadSkus();
                    int resultCount = 0;
                    if(uploadSkus!=null) {
                        resultCount = uploadSkus.size();
                    }
                %>

            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table ID=420 width="750" border="0"  class="mainbody">
                <tr>
                    <td colspan="4" align="center">
                        <html:submit property="action" value="Search"/>
                        <html:submit property="action" value="Cancel"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

  <%
      if(resultCount>0) {
  %>
<table ID=421 border="1"  cellpadding="0" cellspacing="0">
<tr>
<td>
<table ID=422 width="750" border="0"  class="mainbody">
    <tr>
        <td>
            <b>Update/Insert</b>
            <table ID=423 cellpadding="0" cellspacing="0" border="0" width="100%">
                <tr>
                    <td>
                        Update Only
                    </td>
                    <td>
                        <html:radio property='locateUploadItemForm.updateInsert' value='<%=""+LocateUploadItemForm.UPDATE_ONLY%>'/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Insert Only
                    </td>
                    <td>
                        <html:radio property='locateUploadItemForm.updateInsert' value='<%=""+LocateUploadItemForm.INSERT_ONLY%>'/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Update and Insert
                    </td>
                    <td>
                        <html:radio property='locateUploadItemForm.updateInsert' value='<%=""+LocateUploadItemForm.INSERT_UPDATE%>'/>
                    </td>
                </tr>
            </table>
        </td>
        <td>
            <b>When Update</b>
            <table ID=424 cellpadding="0" cellspacing="0" border="0" width="100%">
                <tr>
                    <td>Throw Exception if Distributor Doesn't Match</td>
                    <td>
                        <%
                        if (!useOnlyIgnoreDistributorMatching) {
                        %>
                        <html:radio property='locateUploadItemForm.distProc' value='<%=""+LocateUploadItemForm.DISTRIBUTOR_EXCEPTION%>'/>
                        <%
                        }
                        %>
                    </td>
                </tr>
                <tr>
                    <td>Update Only if Distributor Matches</td>
                    <td>
                        <%
                        if (!useOnlyIgnoreDistributorMatching) {
                        %>
                        <html:radio property='locateUploadItemForm.distProc' value='<%=""+LocateUploadItemForm.DISTRIBUTOR_MATCH%>'/>
                        <%
                        }
                        %>
                    </td>
                </tr>
                <tr>
                    <td>Ignore Distributor Matching</td>
                    <td>
                        <html:radio property='locateUploadItemForm.distProc' value='<%=""+LocateUploadItemForm.DISTRIBUTOR_IGNORE%>'/>
                    </td>
                </tr>
                <tr>
                    <td>Update Distributor if not Matches</td>
                    <td>
                        <%
                        if (!useOnlyIgnoreDistributorMatching) {
                        %>
                        <html:radio property='locateUploadItemForm.distProc' value='<%=""+LocateUploadItemForm.DISTRIBUTOR_ASSIGN%>'/>
                        <%
                        }
                        %>
                    </td>
                </tr>
            </table>
        </td>
        <td>
            Add to Order Guide:  <html:checkbox property='locateUploadItemForm.addToOrderGuideFl' value='true'/><br>
            <%if(theForm.getDistCostFl()) { %>
            Update Dist. Cost:  <html:checkbox property='locateUploadItemForm.updateDistCostFl' value='true'/><br>
            <% } %>
            <%if(theForm.getCatalogPriceFl()) { %>
            Update Catalog Price:  <html:checkbox property='locateUploadItemForm.updateCatalogPriceFl' value='true'/><br>
            <% } %>
            <%if(theForm.getBaseCostFl()) { %>
            Update Base Cost:  <html:checkbox property='locateUploadItemForm.updateBaseCostFl' value='true'/><br>
            <% } %>
            <%if(theForm.getCategoryFl()) { %>
            Update Item Category:  <html:checkbox property='locateUploadItemForm.updateCategoryFl' value='true'/><br>
            <% } %>
            <%if(theForm.getSplFl()) { %>
            Update Standard Prod. List:  <html:checkbox property='locateUploadItemForm.updateSplFl' value='true'/><br>
            <% } %>
            <%if(theForm.getServiceFeeCodeFl()) { %>
            Update Service Fee Code:  <html:checkbox property='locateUploadItemForm.updateServiceFeeCodeFl' value='true'/><br>
            <% } %>
        </td>
        <td>
            <%if(theForm.getDistSkuFl()) { %>
            Update Dist. Sku:  <html:checkbox property='locateUploadItemForm.updateDistSkuFl' value='true'/><br>
            <% } %>
            <%if(theForm.getDistPackFl() && theForm.getDistUomFl()) { %>
            Update Dist. Uom and Pack:  <html:checkbox property='locateUploadItemForm.updateDistUomPackFl' value='true'/><br>
            <% } %>
            <%if(theForm.getGenManufNameFl()) { %>
            Update Gen. Manufacturer:  <html:checkbox property='locateUploadItemForm.updateGenManufNameFl' value='true'/><br>
            <% } %>
            <%if(theForm.getGenManufSkuFl()) { %>
            Update Gen. Manuf. Sku :  <html:checkbox property='locateUploadItemForm.updateGenManufSkuFl' value='true'/><br>
            <% } %>
            <%if(theForm.getTaxExemptFl()) { %>
            Update Tax Exempt :  <html:checkbox property='locateUploadItemForm.updateTaxExemptFl' value='true'/><br>
            <% } %>
            <%if(theForm.getSpecialPermissionFl()) { %>
            Update Special Permission :  <html:checkbox property='locateUploadItemForm.updateSpecialPermissionFl' value='true'/><br>
            <% } %>
            <%if(theForm.getCustomerSkuFl()) { %>
            Update Customer Sku :  <html:checkbox property='locateUploadItemForm.updateCustomerSkuFl' value='true'/><br>
            <% } %>
        </td>
    </tr>
</table>
</td>
</tr>
<tr>
    <td>
        <table ID=425 width="750" border="0"  class="mainbody">
            <tr align="center">
                <td align="center">
                    <html:submit property="action" value="Return Selected"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
</table>
   <%
       }
   %>


 <%
 if(uploadSkus!=null) {
 %>
Search result count: <%=resultCount%>
<%
  int colQty = 2;
    boolean shortDescFl = theForm.getShortDescFl();
    if(shortDescFl) colQty++;
    boolean skuPackFl = theForm.getSkuPackFl();
    boolean skuSizeFl = theForm.getSkuSizeFl();
    boolean skuUomFl = theForm.getSkuUomFl();
    if(skuPackFl || skuSizeFl || skuUomFl) colQty++;
    boolean categoryFl = theForm.getCategoryFl();
    if(categoryFl) colQty++;
    boolean manufNameFl = theForm.getManufNameFl();
    if(manufNameFl) colQty++;
    boolean manufSkuFl = theForm.getManufSkuFl();
    if(manufSkuFl) colQty++;
    boolean distNameFl = theForm.getDistNameFl();
    if(distNameFl) colQty++;
    boolean distSkuFl = theForm.getDistSkuFl();
    if(distSkuFl) colQty++;
    boolean distCostFl = theForm.getDistCostFl();
    if(distCostFl) colQty++;
    boolean catalogPriceFl = theForm.getCatalogPriceFl();
    if(catalogPriceFl) colQty++;
    boolean baseCostFl = theForm.getBaseCostFl();
    if(baseCostFl) colQty++;
    boolean distUomFl = theForm.getDistUomFl();
    boolean distPackFl = theForm.getDistPackFl();
    boolean distUomMultFl = theForm.getDistUomMultFl();
    if(distUomFl || distPackFl || distUomMultFl) colQty++;
    boolean taxExemptFl = theForm.getTaxExemptFl();
    if(taxExemptFl) colQty++;
    boolean specialPermissionFl = theForm.getSpecialPermissionFl();
    if(specialPermissionFl) colQty++;
    boolean splFl = theForm.getSplFl();
    if(splFl) colQty++;
    boolean genManufNameFl = theForm.getGenManufNameFl();
    if(genManufNameFl) colQty++;
    boolean genManufSkuFl = theForm.getGenManufSkuFl();
    if(genManufSkuFl) colQty++;
    boolean customerSkuFl = theForm.getCustomerSkuFl();
    if (customerSkuFl) colQty++;
    boolean serviceFeeCodeFl = theForm.getServiceFeeCodeFl();
    if (serviceFeeCodeFl) colQty++;
%>
<table ID=426 width="750" class="results">
<tr align=center>
<td class="tableheader"><b>Sku</b> </td>
<td>
<a ID=427 href="javascript:SetChecked('locateUploadItemForm.skuSelectFlag',1)">[Check&nbsp;All]</a><br>
<a ID=428 href="javascript:SetChecked('locateUploadItemForm.skuSelectFlag',0)">[&nbsp;Clear]</a>
</td>
<% if(shortDescFl){ %>
  <td class="tableheader"><b>Name</b> </td>
<% } %>
<% if(skuPackFl || skuSizeFl || skuUomFl) { %>
<td class="tableheader"><b>UOM<br>Pack<br>Size</b></td>
<% } %>
<% if(categoryFl) { %>
<td class="tableheader"><b>Category</b> </td>
<% } %>
<% if(manufNameFl) { %>
<td class="tableheader"><b>Manufacturer</b> </td>
<% } %>
<% if(manufSkuFl) { %>
<td class="tableheader"><b>Manuf. Sku</b> </td>
<% } %>
<% if(distNameFl) { %>
<td class="tableheader"><b>Distributor</b> </td>
<% } %>
<% if(distSkuFl) { %>
<td class="tableheader"><b>Dist. Sku</b> </td>
<% } %>
<% if(distCostFl) { %>
<%    if(!theForm.getShowDistCostFl()) { %>
         <td class="tableheader"><b>Dist.<br>Cost</b> </td>
<%    } %>
<% } %>
<% if(catalogPriceFl) { %>
<td class="tableheader"><b>Catal.<br>Price</b> </td>
<% } %>
<% if(baseCostFl) { %>
<td class="tableheader"><b>Base<br>Cost</b> </td>
<% } %>
<% if(distUomFl || distPackFl || distUomMultFl) { %>
<td class="tableheader"><b>D.Uom<br>D.Pack<%if(distUomMultFl){%><br>Uom&nbsp;Mult.<% } %></b> </td>
<% } %>
<% if(taxExemptFl) { %>
<td class="tableheader"><b>Tax<br>Exempt</b></td>
<% } %>
<% if(specialPermissionFl) { %>
<td class="tableheader"><b>Special<br>Permission</b></td>
<% } %>
<% if(splFl) { %>
<td class="tableheader"><b>Spl</b></td>
<% } %>
<% if(genManufNameFl) { %>
<td class="tableheader"><b>Gen. Manuf.</b></td>
<% } %>
<% if(genManufSkuFl) { %>
<td class="tableheader"><b>Gen. Manuf.<br>Sku#</b></td>
<% } %>
<% if(customerSkuFl) { %>
<td class="tableheader"><b>Cust.<br>Sku#</b></td>
<% } %>
<% if(serviceFeeCodeFl) { %>
<td class="tableheader"><b>Service<br>Fee<br>Code</b></td>
<% } %>
</tr>
<%
 int index = 0;
 for(Iterator iter=uploadSkus.iterator(); iter.hasNext(); ) {
   UploadSkuView usVw = (UploadSkuView) iter.next();
   UploadSkuData usD = usVw.getUploadSku();
   String skuNum = usVw.getSkuNum();
   String shortDesc = usD.getShortDesc();
   String skuPack = usD.getSkuPack();
   String skuUom = usD.getSkuUom();
   String skuSize = usD.getSkuSize();
   String category = (usVw.getCategoryId()>0)? usD.getCategory():"";
   String manufName =  usD.getManufName();
   String manufSku = usD.getManufSku();

   int distId = usD.getDistId();
   String distName = usD.getDistName();
   String distPack = usD.getDistPack();
   String distSku = usD.getDistSku();
   String distUom = usD.getDistUom();
   String distUomMult = usD.getDistUomMult();

   String distCostS = usD.getDistCost();
   int scaleDistCost = StringUtils.getDecimalPoints(distCostS);
   BigDecimal distCost = new BigDecimal(0);
   try {
     double distCostDb = Double.parseDouble(distCostS);
     distCost = new BigDecimal(distCostDb);
   } catch (Exception exc) {}
   distCost = distCost.setScale(scaleDistCost, BigDecimal.ROUND_HALF_UP);

   String catalogPriceS = usD.getCatalogPrice();
   int scaleCatalogPrice = StringUtils.getDecimalPoints(catalogPriceS);
   BigDecimal catalogPrice = new BigDecimal(0);
   try {
     double catalogPriceDb = Double.parseDouble(catalogPriceS);
     catalogPrice = new BigDecimal(catalogPriceDb);
   } catch (Exception exc) {}
   catalogPrice = catalogPrice.setScale(scaleCatalogPrice, BigDecimal.ROUND_HALF_UP);

   String baseCostS = usD.getBaseCost();
   int scaleBaseCost = StringUtils.getDecimalPoints(baseCostS);
   BigDecimal baseCost = new BigDecimal(0);
   try {
     double baseCostDb = Double.parseDouble(baseCostS);
     baseCost = new BigDecimal(baseCostDb);
   } catch (Exception exc) {}
   baseCost = baseCost.setScale(scaleBaseCost, BigDecimal.ROUND_HALF_UP);

   String taxExempt = usD.getTaxExempt(); if(taxExempt==null) taxExempt = "";
   String specialPermission = usD.getSpecialPermission(); if(specialPermission==null) specialPermission = "";
   String spl = usD.getSpl(); if(spl==null) spl = "";
   String genManufName = usD.getGenManufName();
   String genManufSku = usD.getGenManufSku();
   String customerSkuNum = usD.getCustomerSkuNum();
   String serviceFeeCode = usD.getServiceFeeCode();

   String selectCheckbox = "locateUploadItemForm.skuSelectFlag["+index+"]";
   index++;
%>

<tr>
<td><%=skuNum%></td>
<td class='results'><html:checkbox  property='<%=selectCheckbox%>' value='true'/></td>
<% if(shortDescFl){ %>
<td><%=shortDesc%></td>
<% } %>
<% if(skuPackFl || skuSizeFl || skuUomFl) { %>
<td><%=skuUom%><br><%=skuPack%><br><%=skuSize%></td>
<% } %>
<% if(categoryFl) { %>
<td><%=category%></td>
<% } %>
<% if(manufNameFl) { %>
<td><%=manufName%></td>
<% } %>
<% if(manufSkuFl) { %>
<td><%=manufSku%></td>
<% } %>
<% if(distNameFl) { %>
<td><%=distName%></td>
<% } %>
<% if(distSkuFl) { %>
<td><%=distSku%></td>
<% } %>
<% if(distCostFl) { %>
<%    if(!theForm.getShowDistCostFl()) { %>
<td><%=distCost%></td>
<%    }  %>
<% } %>
<% if(catalogPriceFl) { %>
<td><%=catalogPrice%></td>
<% } %>
<% if(baseCostFl) { %>
<td><%=baseCost%></td>
<% } %>
<% if(distUomFl || distPackFl || distUomMultFl) { %>
<td><%=distUom%><br><%=distPack%><br><%if(distUomMultFl){%><%=distUomMult%><%}%></td>
<% } %>
<% if(taxExemptFl) { %>
<td><%=taxExempt%></td>
<% } %>
<% if(specialPermissionFl) { %>
<td><%=specialPermission%></td>
<% } %>
<% if(splFl) { %>
<td><%=spl%></td>
<% } %>
<% if(genManufNameFl) { %>
<td><%=genManufName%></td>
<% } %>
<% if(genManufSkuFl) { %>
<td><%=genManufSku%></td>
<% } %>
<% if(customerSkuFl) { %>
<td><%=customerSkuNum%></td>
<% } %>
<% if(serviceFeeCodeFl) { %>
<td><%=serviceFeeCode%></td>
<% } %>
</tr>
<% } %>           
<% } %>
</table>
</html:form>
</div>
</body>
</html:html>
<% }} %>
<% } %>
