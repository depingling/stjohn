<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="theForm" name="STORE_ADMIN_ITEM_FORM" type="com.cleanwise.view.forms.StoreItemMgrForm"/>
<!-- for ENTERPRISE store, just update the linked item info -->
<logic:equal name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">
   <%boolean linkedItemFl=false;
int licount=0;%>
 <logic:present name="STORE_ADMIN_ITEM_FORM" property="linkedProductItems">

        <bean:size id="linkedItemCount"  name="STORE_ADMIN_ITEM_FORM" property="linkedProductItems"/>
        <logic:greaterThan name="linkedItemCount" value="0">
        <%linkedItemFl=true;
licount=((java.lang.Integer)linkedItemCount).intValue();%>
   </logic:greaterThan>
    </logic:present>
<table ID=1322 bgcolor="white" cellpadding=0 cellspacing=0 border=1 width="100%">
        <thead>
            <tr>
                <td>
                    <table ID=1323 border="1" cellpadding="0" cellspacing="0" width="1000">
                        <tr>
                            <td rowspan="6" width="10%"><b>Store</b><BR><a ID=1324 href="javascript:SetCheckedAllItemProperty('linkedItem',-1,1);">[Check&nbsp;All]</a><BR><a ID=1325 href="javascript:SetCheckedAllItemProperty('linkedItem',-1,0);">[Clear]</a></td>
                            <td colspan="3"><b>Item Name</b><br><a ID=1330 href="javascript:SetCheckedItemProperty('linkedItem','updateShortDesc',<%=licount%>,1);">[Check&nbsp;All]</a><a ID=1331 href="javascript:SetCheckedItemProperty('linkedItem','updateShortDesc',<%=licount%>,0);">[Clear]</a></td>
                            <td><b>Sku</b></td>
                            <td width="20%"><b>Image</b> <br> <a ID=1332 href="javascript:SetCheckedItemProperty('linkedItem','updateImage',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1333 href="javascript:SetCheckedItemProperty('linkedItem','updateImage',<%=licount%>,0);">[Clear]</a></td>
                            <td rowspan="3" width="20%"><b>Long Desc</b> <br> <a ID=1334 href="javascript:SetCheckedItemProperty('linkedItem','updateLongDesc',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1335 href="javascript:SetCheckedItemProperty('linkedItem','updateLongDesc',<%=licount%>,0);">[Clear]</a></td>
                        </tr>
                        <tr>
                            <td width="14%"><b >Manufacturer</b> <br> <a ID=1336 href="javascript:SetCheckedItemProperty('linkedItem','updateManufacturer',<%=licount%>,1);">[Check&nbsp;All]</a>
                             <a ID=1337 href="javascript:SetCheckedItemProperty('linkedItem','updateManufacturer',<%=licount%>,0);">[Clear]</a>
                            <td width="12%"><b>Manufacturer Sku</b> <br> <a ID=1338 href="javascript:SetCheckedItemProperty('linkedItem','updateManufacturerSku',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1339 href="javascript:SetCheckedItemProperty('linkedItem','updateManufacturerSku',<%=licount%>,0);">[Clear]</a></td>
                            <td width="12%"><b>Product UPC</b> <br> <a ID=1340 href="javascript:SetCheckedItemProperty('linkedItem','updateUpc',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1341 href="javascript:SetCheckedItemProperty('linkedItem','updateUpc',<%=licount%>,0);">[Clear]</a></td>
                            <td width="12%"><b >Pack UPC</b> <br> <a ID=1342 href="javascript:SetCheckedItemProperty('linkedItem','updatePkgUpc',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1343 href="javascript:SetCheckedItemProperty('linkedItem','updatePkgUpc',<%=licount%>,0);">[Clear]</a></td>
                            <td width="20%"><b>MSDS</b> <br> <a ID=1344 href="javascript:SetCheckedItemProperty('linkedItem','updateMsds',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1345 href="javascript:SetCheckedItemProperty('linkedItem','updateMsds',<%=licount%>,0);">[Clear]</a></td>
                        <tr>
                            <td><b>UNSPSC Code</b> <br> <a ID=1346 href="javascript:SetCheckedItemProperty('linkedItem','updateUnspscCd',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1347 href="javascript:SetCheckedItemProperty('linkedItem','updateUnspscCd',<%=licount%>,0);">[Clear]</a></td>
                            <td><b>Color</b> <br> <a ID=1348 href="javascript:SetCheckedItemProperty('linkedItem','updateColor',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1349 href="javascript:SetCheckedItemProperty('linkedItem','updateColor',<%=licount%>,0);">[Clear]</a></td>
                            <td><b>Item Size</b> <br> <a ID=1350 href="javascript:SetCheckedItemProperty('linkedItem','updateSize',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1351 href="javascript:SetCheckedItemProperty('linkedItem','updateSize',<%=licount%>,0);">[Clear]</a></td>
                            <td><b>Scent</b> <br> <a ID=1352 href="javascript:SetCheckedItemProperty('linkedItem','updateScent',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1353 href="javascript:SetCheckedItemProperty('linkedItem','updateScent',<%=licount%>,0);">[Clear]</a></td>
                            <td><b>DED</b> <br> <a ID=1354 href="javascript:SetCheckedItemProperty('linkedItem','updateDed',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1355 href="javascript:SetCheckedItemProperty('linkedItem','updateDed',<%=licount%>,0);">[Clear]</a></td>
                        </tr>
                        <tr>
                            <td><b>Shipping Weight</b> <br> <a ID=1356 href="javascript:SetCheckedItemProperty('linkedItem','updateShipWeight',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1357 href="javascript:SetCheckedItemProperty('linkedItem','updateShipWeight',<%=licount%>,0);">[Clear]</a></td>
                            <td><b>Shipping Cubic Size</b> <br> <a ID=1358 href="javascript:SetCheckedItemProperty('linkedItem','updateCubeSize',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1359 href="javascript:SetCheckedItemProperty('linkedItem','updateCubeSize',<%=licount%>,0);">[Clear]</a></td>
                            <td><b>List Price</b> <br> <a ID=1360 href="javascript:SetCheckedItemProperty('linkedItem','updateListPrice',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1361 href="javascript:SetCheckedItemProperty('linkedItem','updateListPrice',<%=licount%>,0);">[Clear]</a></td>
                            <td>&nbsp;</td>
                            <td><b>Prod Spec</b> <br> <a ID=1364 href="javascript:SetCheckedItemProperty('linkedItem','updateSpec',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1365 href="javascript:SetCheckedItemProperty('linkedItem','updateSpec',<%=licount%>,0);">[Clear]</a></td>
                            <td rowspan="2" width="20%"><b>Certified Companies</b> <br> <a ID=1366 href="javascript:SetCheckedItemProperty('linkedItem','updateCertifiedCompanies',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1367 href="javascript:SetCheckedItemProperty('linkedItem','updateCertifiedCompanies',<%=licount%>,0);">[Clear]</a></td>
                        </tr>
                        <tr>
                            <td><b>NSN</b> <br> <a ID=1368 href="javascript:SetCheckedItemProperty('linkedItem','updateNsn',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1369 href="javascript:SetCheckedItemProperty('linkedItem','updateNsn',<%=licount%>,0);">[Clear]</a></td>
                            <td><b>UOM</b> <br> <a ID=1370 href="javascript:SetCheckedItemProperty('linkedItem','updateUom',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1371 href="javascript:SetCheckedItemProperty('linkedItem','updateUom',<%=licount%>,0);">[Clear]</a></td>
                            <td><b>UOM and Pack vary by Geograpghy</b><br><a ID=1372 href="javascript:SetCheckedItemProperty('linkedItem','updatePackProblemSku',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1373 href="javascript:SetCheckedItemProperty('linkedItem','updatePackProblemSku',<%=licount%>,0);">[Clear]</a></td>
                            <td><b>Pack</b> <br> <a ID=1374 href="javascript:SetCheckedItemProperty('linkedItem','updatePack',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1375 href="javascript:SetCheckedItemProperty('linkedItem','updatePack',<%=licount%>,0);">[Clear]</a></td>
                            <td><b>HAZMAT</b> <br> <a ID=1376 href="javascript:SetCheckedItemProperty('linkedItem','updateHazmat',<%=licount%>,1);">[Check&nbsp;All]</a>
                            <a ID=1377 href="javascript:SetCheckedItemProperty('linkedItem','updateHazmat',<%=licount%>,0);">[Clear]</a></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </thead>

        <tbody>
   <%if(linkedItemFl){%>

        <logic:iterate id="linkedItem" name="STORE_ADMIN_ITEM_FORM" property="linkedProductItems"
          type="com.cleanwise.service.api.value.StoreProductView" indexId="kkk">
            <tr>
                <td>
                    <table ID=1378 border="1" cellpadding="0" cellspacing="0" width="1000">
                        <tr>
                            <td rowspan="6" width="10%"><b>Store</b><BR><bean:write name="linkedItem" property="store.busEntity.shortDesc" />
                       <BR><a ID=1379 href="javascript:SetCheckedAllItemProperty('linkedItem',<%=kkk%>,1);">[Check&nbsp;All]</a><BR><a ID=1380 href="javascript:SetCheckedAllItemProperty('linkedItem',<%=kkk%>,0);">[Clear]</a><br> 
                     <% String function="unlinkItem(0,'Unlink Items','unlinkedItemId',"+linkedItem.getProduct().getProductId()+");";%>
                    <br><br><html:button styleClass='text'  property="action"  value ="Break Link" onclick='<%=function%>'/></td>

                            <td colspan="3"><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateShortDesc" %>'/><b>Item Name</b><br><bean:write name="linkedItem" property="product.shortDesc" /></td>
                            <td><b>Sku</b><br><bean:write name="linkedItem" property="product.skuNum"/></td>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateImage" %>'/><b>Image</b><br><bean:write name="linkedItem" property="product.image" /></td>
                            <td rowspan="3" width="20%"><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateLongDesc" %>'/><b>Long Desc</b><br><bean:write name="linkedItem" property="product.longDesc" /></td>
                        </tr>
                        <tr>
                            <td width="14%"><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateManufacturer" %>' /><b>Manufacturer</b><br><bean:write name="linkedItem" property="product.manufacturerName" /></td>
                            <td width="12%"><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateManufacturerSku" %>'/><b>Manufacture Sku</b><br><bean:write name="linkedItem" property="product.manufacturerSku" /></td>
                            <td width="12%"><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateUpc" %>'/><b>Product UPC</b><br><bean:write name="linkedItem" property="product.upc" /></td>
                            <td width="12%"><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updatePkgUpc" %>'/><b>Pack UPC</b><br><bean:write name="linkedItem" property="product.pkgUpc" /></td>
                            <td width="20%"><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateMsds" %>'/><b>MSDS</b><br><bean:write name="linkedItem" property="product.msds" /></td>
                        <tr>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateUnspscCd" %>'/><b>UNSPSC Code</b><br><bean:write name="linkedItem" property="product.unspscCd" /></td>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateColor" %>'/><b>Color</b><br><bean:write name="linkedItem" property="product.color" /></td>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateSize" %>'/><b>Item Size</b><br><bean:write name="linkedItem" property="product.size" /></td>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateScent" %>'/><b>Scent</b><br><bean:write name="linkedItem" property="product.scent" /></td>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateDed" %>'/><b>DED</b><br><bean:write name="linkedItem" property="product.ded" /></td>
                        </tr>
                        <tr>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateShipWeight" %>'/><b>Shipping Weight</b><br><bean:write name="linkedItem" property="product.shipWeight" /></td>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateCubeSize" %>'/><b>Shipping Cubic Size</b><br><bean:write name="linkedItem" property="product.cubeSize" /></td>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateListPrice" %>'/><b>List Price</b><br><bean:write name="linkedItem" property="product.listPrice" /></td>
                            <td>&nbsp;</td>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateSpec" %>'/><b>Prod Spec</b><br><bean:write name="linkedItem" property="product.spec" /></td>
                            <td rowspan="2" width="20%"><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateCertifiedCompanies" %>'/><b>Certified Companies</b><br>
                                <logic:present name="linkedItem" property="product.certCompaniesBusEntityDV">
                                    <bean:size id="certCompSize" name="linkedItem"
                                               property="product.certCompaniesBusEntityDV"/>
                                    <logic:greaterThan name="certCompSize" value="0">
                                        <logic:iterate id="certCompShortDesc" name="linkedItem" indexId="idx"  property="product.certCompaniesBusEntityDV">
                                            <logic:greaterThan name="idx"  value="0">
                                                <br>
                                            </logic:greaterThan>
                                            <bean:write name="certCompShortDesc" property="shortDesc"/>
                                        </logic:iterate>
                                    </logic:greaterThan>
                                </logic:present>
                                  </td>
                        </tr>
                        <tr>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateNsn" %>'/><b>NSN</b><br><bean:write name="linkedItem" property="product.nsn" /></td>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateUom" %>'/><b>UOM</b><br><bean:write name="linkedItem" property="product.uom" /></td>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updatePackProblemSku" %>'/><b>UOM and Pack vary by Geograpghy</b><br><bean:write name="linkedItem" property="product.packProblemSku" /></td>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updatePack" %>'/><b>Pack</b><br><bean:write name="linkedItem" property="product.pack" /></td>
                            <td><html:checkbox name="STORE_ADMIN_ITEM_FORM" property='<%= "linkedItem[" + kkk + "].updateHazmat" %>'/><b>HAZMAT</b><br><bean:write name="linkedItem" property="product.hazmat" /></td>
                        </tr>

                    </table>
                </td>
            </tr>
        </logic:iterate>

            <tr bgcolor="#cccccc">
                <td colspan=6 align=left>
                    <bean:define id="changesFl"   name="STORE_ADMIN_ITEM_FORM" property="changesOnItemEditPageFl"/>
                    <%if(((Boolean)changesFl).booleanValue()){%>
                    <html:submit property="action" value="Update Linked Items" styleClass='reqind' />
                    <%} else {%>
                    <html:submit property="action" value="Update Linked Items" />
                    <%}%>
                </td>
            </tr>
<%}%>

        </tbody>
    </table>
                          
                      
 <html:hidden  name="STORE_ADMIN_ITEM_FORM"  property="unlinkedItemId" value="0"/>
</logic:equal>
