<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% boolean clwSwitch = ClwCustomizer.getClwSwitch(); %>

<app:checkLogon/>

<bean:define id="imageName" name="ITEM_MASTER_FORM" property="product.image" scope="session"/>
<bean:define id="product" name="ITEM_MASTER_FORM" property="product" scope="session"/>
<bean:define id="uomCd" name="product" property="uom"/>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="ITEM_MASTER_FORM" type="com.cleanwise.view.forms.ItemMgrMasterForm"/>

<%
        String isMSIE = (String)session.getAttribute("IsMSIE");
        if (null == isMSIE) isMSIE = "";
%>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js" CHARSET="ISO-8859-1"></SCRIPT>

<script language="JavaScript1.2">
<!--
function popLocate(name) {
  var loc = "itemdistrassign.do?feedField=" + name;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}
//-->
</script>

<script language="JavaScript1.2">
<!--
function popManufLocate(name,name1) {
  var loc = "manuflocate.do?feedField=" + name+"&feedDesc="+name1;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}
//-->
</script>

<html:html>
<head>
<title>Search Item</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<style>
th {font-size: 9px; font-weight: normal; 
	background-color: white;
	color: black; }

#UOMCD  { position: relative;}
#DISTUOMCD  { position: relative;}
</style>
<script language="JavaScript1.2">
<!--

function setReadOnly(element, selectElement) {
        if(selectElement.options[selectElement.selectedIndex].value=="OTHER") {
                return true;
        }
        else {
                element.blur();
        }
        return true;
}

function changeUomCdNS(element, targetElement) {
        if(element.options[element.selectedIndex].value=="OTHER") {
                targetElement.value = "";
        }
        else {
                targetElement.value = element.options[element.selectedIndex].value;
        }
        return true;
}


function changeUomCd(element, targetElement, layerId) {
        var targetName = targetElement.name;
        if(element.options[element.selectedIndex].value=="OTHER") {
                var htmln = '<input type="text" name="' + targetName + '" value="" size="2" maxlength="2">';
        }
        else {
                var htmln = '<input type="hidden" name="' + targetName + '" value="' + element.options[element.selectedIndex].value + '">';
        }
        //rewriteLayer('UOMCD', htmln);
        rewriteLayer(layerId, htmln);
        return true;
}

// from www.faqts.com
function rewriteLayer(idOrPath, html) {
  if (document.layers) {
    var l = idOrPath.indexOf('.') != -1 ? eval(document[idOrPath])
             : document[idOrPath];
    if (!l.ol) {
      var ol = l.ol = new Layer (l.clip.width, l);
      ol.clip.width = l.clip.width;
      ol.clip.height = l.clip.height;
      ol.bgColor = l.bgColor;
      l.visibility = 'hide';
      ol.visibility = 'show';
    }
    var ol = l.ol;
    ol.document.open();
    ol.document.write(html);
    ol.document.close();
  }
  else if (document.all || document.getElementById) {
    var p = idOrPath.indexOf('.');
    var id = p != -1 ?
              idOrPath.substring(idOrPath.lastIndexOf('.') + 1)
              : idOrPath;
    if (document.all)
      document.all[id].innerHTML = html;
    else {
      var l = document.getElementById(id);
      var r = document.createRange();
      r.setStartAfter(l);
      var docFrag = r.createContextualFragment(html);
      while (l.hasChildNodes())
        l.removeChild(l.firstChild);
      l.appendChild(docFrag);
    }
  }
}
//-->
</script>


<% if ("Y".equals(isMSIE)) { %>
<script language="JavaScript" src="../externals/calendar.js"></script>
<% } else {  %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% }  %>

<body bgcolor="#cccccc">

<% if ("Y".equals(isMSIE)) { %>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
    marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% }  %>

<div class = "text">
<html:form action="/adminportal/itemmaster.do" enctype="multipart/form-data">
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/catalogToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/catalogInfo.jsp"/>
<font color=red>
<html:errors/>
</font>

<table border="0"cellpadding="0" cellspacing="1" width="769">
  <tr bgcolor="#cccccc">
  <td colspan="9">
<div style="background-color: #ccffcc; font-size: 125%; font-weight: bold;">
SKU:&nbsp;&nbsp;
         <% if(clwSwitch) { %>
           <logic:notEqual name="ITEM_MASTER_FORM" property="product.skuNum" value="0">
            <bean:write name="ITEM_MASTER_FORM" property="product.skuNum"/>
           </logic:notEqual>
         <% }else{ %>
           <html:text name="ITEM_MASTER_FORM" property="skuNum"/><span class="reqind">*</span>
         <% } %>
</div>

    <table width="767" border="0">
    <tr>
    <td width="10"></td>
    <td width="744">

       <table border="0" width="722">
         <tr>
         <td>

<b>Item ID:</b>
    <bean:write name="ITEM_MASTER_FORM" property="product.productId"/>
         </td>
         <td colspan="3" width="80%"> <b>Item Name:</b>
           <html:text name="ITEM_MASTER_FORM" property="product.shortDesc" size="80" maxlength="255"/><td><span class="reqind">*</span></td>
         </td>
         </tr>
         <tr>
         <td><b>Active Date:</b> </td>
         <td>
<% if ("Y".equals(isMSIE)) { %>
                <html:text name="ITEM_MASTER_FORM" property="effDate" maxlength="10" /><span class="reqind">*</span>
                        <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DEFSADD2, document.forms[0].effDate, null, -7300, 7300);" title="Choose Date"
                ><img name="DEFSADD2" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
                        <br><span class="smalltext">(mm/dd/yyyy)</span>
<% } else {  %>
                <html:text name="ITEM_MASTER_FORM" property="effDate" maxlength="10" /><span class="reqind">*</span>
                        <a href="javascript:show_calendar('forms[0].effDate');" onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;" title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
                        <br><span class="smalltext">(mm/dd/yyyy)</span>
<% }  %>
         </td>
         <td><b>Inactive Date:</b></td>
         <td>
<% if ("Y".equals(isMSIE)) { %>
                <html:text name="ITEM_MASTER_FORM" property="expDate" maxlength="10"/>
                        <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DEFSADD1, document.forms[0].expDate, null, -7300, 7300);" title="Choose Date"
                ><img name="DEFSADD1" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
            <br><span class="smalltext">(mm/dd/yyyy)</span>
<% } else {  %>
                <html:text name="ITEM_MASTER_FORM" property="expDate" maxlength="10"/>
                        <a href="javascript:show_calendar('forms[0].expDate');" onmouseover="window.status='Date Picker';return true;" onmouseout="window.status='';return true;"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
            <br><span class="smalltext">(mm/dd/yyyy)</span>
<% }  %>
         </td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         </tr>
       </table>
       <div align="left"><br>
       <table bgcolor="#ccffcc">
         <tr>
         <td> <b>Other Description:</b></td>
         <td colspan="3">
           <html:text name="ITEM_MASTER_FORM" property="product.otherDesc" maxlength="80" size="95"/>
         </td>
         </tr>
         <tr>
         <td colspan="4"> <b>Long Description:</b></td>
         </tr>
         <tr>
         <td colspan="2">
         <html:textarea name="ITEM_MASTER_FORM" property="product.longDesc" cols="40" rows="14"/>
         </td>
         <td valign="top"><span class="reqind">*</span></td>
         <td colspan="2" >
           <div align="center">
           <bean:define id="image" value="<%=new String(\"../\"+imageName) %>"/>
           <%
              if((image.indexOf(".jpg") < 0) && (image.indexOf(".gif") < 0)){

                  image = "../en/images/noMan.gif";
              }
           %>
           <html:img src="<%=image%>" width="240" height="240"/>
<br>Path:<%=image%>
           </div>
         </td>
         </tr>

         <tr bgcolor="#cccccc"><td colspan=4 align=center>
        <html:submit property="action" value="Save Item"/>
<input type=button value="Back" onClick="javascript: history.back()">
</td>
</tr>
         <td><b>SKU:</b></td>
         <td>
         <% if(clwSwitch) { %>
           <logic:notEqual name="ITEM_MASTER_FORM" property="product.skuNum" value="0">
            <bean:write name="ITEM_MASTER_FORM" property="product.skuNum"/>
           </logic:notEqual>
         <% }else{ %>
           <html:text name="ITEM_MASTER_FORM" property="skuNum"/><span class="reqind">*</span>
         <% } %>
         </td>
         <td><b>Image:</b></td>
         <td>
             <html:file name="ITEM_MASTER_FORM" property="imageFile"
              accept="image/jpeg,image/gif"/>
         </td>
         </tr>
         <tr>
         <td><b>Manufacturer&nbsp;Id:</b></td>
         <td colspan="3">
            <html:text name="ITEM_MASTER_FORM" property="manufacturerId" size="10" maxlength="10"/><span class="reqind">*</span>
            <html:button onclick="return popManufLocate('manufacturerId','manufacturerName');" value="Locate Manufacturer" property="action" />
         </td>
         </tr>
         <tr>
         <td><b>Manufacturer&nbsp;Name:</b></td>
         <td>
            <html:text name="ITEM_MASTER_FORM" property="manufacturerName" size="30" maxlength="30" readonly="true" styleClass="mainbodylocatename"/>
         </td>
         <td><b>MSDS:</b></td>
         <td>
             <html:file name="ITEM_MASTER_FORM" property="msdsFile"
              accept="application/pdf"/>
         </td>
         </tr>
         </tr>
         <tr>
         <td><b>Manufacturer SKU:</b></td>
         <td colspan="2">
            <html:text name="ITEM_MASTER_FORM" property="manufacturerSku" maxlength="30"/><span class="reqind">*</span>
         </td>
             <logic:notEqual name="ITEM_MASTER_FORM" property="product.msds" value="">
             <bean:define id="msdsName" name="ITEM_MASTER_FORM" property="product.msds" scope="session"/>
             <% String msdsClick = new String("window.open('/"+storeDir+"/"+msdsName+"','MSDS');");%>

<td>
Path:<bean:write name="ITEM_MASTER_FORM" property="product.msds"/><br>
<html:button onclick="<%=msdsClick%>" value="View MSDS" property="action"/>
</td>
             </logic:notEqual>
             <logic:equal name="ITEM_MASTER_FORM" property="product.msds" value="">
             <td><html:button value="View MSDS" property="action" disabled="true"/></td>
             </logic:equal>
         </tr>
         <tr>
         <td><b>Categories:</b></td>
         <td>
           <logic:greaterThan name="ITEM_MASTER_FORM" property="categoryListSize" value="0">
             <logic:iterate id="category" name="ITEM_MASTER_FORM" property="product.catalogCategories"
              type="com.cleanwise.service.api.value.CatalogCategoryData">
                <bean:write name="category" property="catalogCategoryShortDesc"/>
             </logic:iterate>
            </logic:greaterThan>
         </td>
         <td><b>DED:</b></td>
         <td>
             <html:file name="ITEM_MASTER_FORM" property="dedFile"
              accept="application/pdf"/>
         </td>
         </tr>
         <tr>
         <td><b>Product UPC:</b></td>
         <td>
            <html:text name="ITEM_MASTER_FORM" property="product.upc"  maxlength="15"/>
         </td>
             <td></td>
             <logic:notEqual name="ITEM_MASTER_FORM" property="product.ded" value="">
             <bean:define id="dedName" name="ITEM_MASTER_FORM" property="product.ded" scope="session"/>
             <% String dedClick = new String("window.open('/"+storeDir+"/"+dedName+"','DED');");%>

<td>
Path:<bean:write name="ITEM_MASTER_FORM" property="product.ded"/><br>
<html:button onclick="<%=dedClick%>" value="View DED" property="action"/>
</td>
             </logic:notEqual>

             <logic:equal name="ITEM_MASTER_FORM" property="product.ded" value="">
             <td>
<html:button value="View DED" property="action" disabled="true"/></td>
             </logic:equal>
         </tr>
         <tr>
         <td><b>Pack UPC:</b></td>
         <td>
            <html:text name="ITEM_MASTER_FORM" property="product.pkgUpc"  maxlength="15"/>
         </td>
         <td><b>Prod Spec:</b></td>
         <td>
             <html:file name="ITEM_MASTER_FORM" property="specFile"
              accept="application/pdf"/>
         </td>
         </tr>
         <tr>
         <td><b>UNSPSC Code:</b></td>
         <td>
            <html:text size="10" maxlength="10" name="ITEM_MASTER_FORM" property="product.unspscCd"/>[10 digits]
         </td>
             <td></td>
             <logic:notEqual name="ITEM_MASTER_FORM" property="product.spec" value="">
             <bean:define id="specName" name="ITEM_MASTER_FORM" property="product.spec" scope="session"/>
             <% String specClick = new String("window.open('/"+storeDir+"/"+specName+"','Spec');");%>
<td>
Path:<bean:write name="ITEM_MASTER_FORM" property="product.spec" /><br>
<html:button onclick="<%=specClick%>" value="View Product Spec" property="action"/>
</td>
             </logic:notEqual>
             <logic:equal name="ITEM_MASTER_FORM" property="product.spec" value="">
             <td><html:button value="View Product Spec" property="action" disabled="true"/></td>
             </logic:equal>
         </tr>

         <tr>
         <td><b>Color:</b></td>
         <td>
            <html:text name="ITEM_MASTER_FORM" property="product.color" maxlength="30"/>
         </td>
         <td><b>Item Size:</b></td>
         <td colspan="2">
            <html:text name="ITEM_MASTER_FORM" property="product.size"  maxlength="30"/>
            <span class="reqind">*</span>
         </td>
         </tr>

         <tr>
         <td><b>Scent:</b></td>
         <td>
            <html:text name="ITEM_MASTER_FORM" property="product.scent" maxlength="30"/>
         </td>
         <td><b>Shipping Weight:</b></td>
         <td>
            <html:text name="ITEM_MASTER_FORM" property="product.shipWeight"  maxlength="30"/>
         </td>
         </tr>

         <tr>
         </td>
         <td><b>Shipping Cubic Size:</b></td>
         <td>
            <html:text name="ITEM_MASTER_FORM" property="product.cubeSize"  maxlength="30"/>
         </td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         </tr>

         <tr>
         <td><b>List Price (MSRP):</b></td>
         <td>
            <html:text name="ITEM_MASTER_FORM" property="listPrice" maxlength="10"/>
            <span class="reqind">*</span>
         </td>
         <td colspan="2">
         <table>
         <tr><td><b>UOM:</b></td>
         <td>
        <% if ("Y".equals(isMSIE)) { %>
                        <table cellpadding=0 cellspacing=0 border=0>
                          <tr><td>
                <html:select name="ITEM_MASTER_FORM" property="uom" onchange="return changeUomCd(this, document.forms[0].elements['product.uom'], 'UOMCD');">
                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                <html:options  collection="Item.uom.vector"
                                        property="value" />
            </html:select></td>
                        <td>
                        <div id="UOMCD">
                                <logic:equal name="ITEM_MASTER_FORM" property="uom" value="OTHER">
                                <html:text name="ITEM_MASTER_FORM" property="product.uom" size="2" maxlength="2"/>
                                </logic:equal>
                                <logic:notEqual name="ITEM_MASTER_FORM" property="uom" value="OTHER">
                                <html:hidden name="ITEM_MASTER_FORM" property="product.uom"/>
                                </logic:notEqual>
                   </div></td><td><span class="reqind">*</span></td>
                     </tr>
                        </table>
        <% } else { // for NS %>
                <html:select name="ITEM_MASTER_FORM" property="uom" onchange="return changeUomCdNS(this, document.forms[0].elements['product.uom']);">
                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                <html:options  collection="Item.uom.vector"
                                        property="value" />
            </html:select>
                <html:text name="ITEM_MASTER_FORM" property="product.uom" size="2" maxlength="2" onfocus="return setReadOnly(this, document.forms[0].elements['uom']);"/>
        <% }  %>

         <br>UOM and Pack varies by geography
         <html:select name="ITEM_MASTER_FORM" property="product.packProblemSku">
                <html:option value="true">yes</html:option>
                <html:option value="false">no</html:option>
        </html:select>
         </tr></table></td>

         </tr>
         <tr>
         <td><b>Distributor Target Cost Price:</b></td>
         <td>
            <html:text name="ITEM_MASTER_FORM" property="costPrice" maxlength="10"/>
         </td>
         <td><b>Pack:</b></td>
         <td colspan="2">
            <html:text name="ITEM_MASTER_FORM" property="product.pack" maxlength="30"/>
            <span class="reqind">*</span>
         </td>
         </tr>
<tr>
<td></td>
<td><b>HAZMAT :</b>
<html:select name="ITEM_MASTER_FORM" property="hazmat">
<html:option value="true">yes</html:option>
<html:option value="false">no</html:option>
</html:select>
</td>
<td><b>PSN :</b>
<html:text name="ITEM_MASTER_FORM" property="product.psn" maxlength="13" size="13"/>
</td>
<td><b>NSN :</b>
<html:text name="ITEM_MASTER_FORM" property="product.nsn" maxlength="13" size="13"/>
</td>

</tr>


         <tr>
<td colspan="4">

<table bgcolor="white" cellpadding=0 cellspacing=0 border=1 width="100%">
<thead>
<tr>

         <th>
<a id="pgsort"></a>
<b>
<a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 0, false);" title="-- Sort by Distributor Id"       > Distributor Id
</a>
</b>
</th>
<th>
<b> 
<a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 1, false);" title="-- Sort by Distributor ERP Num"       >
Distributor ERP Num </a>
</b>
</th>
         <th><b> 
<a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 2, false);" title="-- Sort by Distributor Name"       >
Distributor Name
</a>
</b></th>
         <th><b> 
<a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 3, false);" title="-- Sort by Distributor Sku"       >
Distributor Sku 
 </a>

</b></th>
         <th><b> 
<a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 4, false);" title="-- Sort by Distributor UOM"       >
Distributor UOM 
 </a>

</b></th>
         <th><b> 
<a href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 5, false);" title="-- Sort by Distributor Pack"       >
Distributor Pack 
 </a>

</b></th>
         <th></th>
                </tr>

</thead>
  <tbody id="itemTblBdy">

         <!--  List of Distributors  -->
         <logic:iterate id="distributor" name="ITEM_MASTER_FORM" property="product.mappedDistributors"
          type="com.cleanwise.service.api.value.BusEntityData" indexId="kkk">
            <tr>
            <td><bean:write name="distributor" property="busEntityId"/></td>
            <td><bean:write name="distributor" property="erpNum"/></td>
            <% int distId = distributor.getBusEntityId();
               String distSku=((ProductData)product).getDistributorSku(distId);
               String link = "distItemMgr.do?action=showItem&distSku=";
               link+=distSku;
               link+="&searchField=";
               link+=distId;
            %>
            <td><a href="<%=link%>"><bean:write name="distributor" property="shortDesc"/></a></td>
            <td><%=distSku%>
            </td>
            <td><%= ((ProductData)product).getDistributorUom(((BusEntityData)distributor).getBusEntityId()) %>
            </td>
            <td><%= ((ProductData)product).getDistributorPack(((BusEntityData)distributor).getBusEntityId()) %>
            </td>
            <td></td>
            </tr>
          </logic:iterate>
</tbody>
                        </table>
                        </td>
                        </tr>

        </table>

        <!-- Control buttons -->
        <html:submit property="action" value="Save Item"/>
        <% if(!clwSwitch) { %>
          <html:submit property="action" value="Store Item Data"/>
        <% } %>
         <!--
              <input type="button" name="Submit3" value="Undo Changes">
              <input type="button" name="Submit2" value="Copy product as new SKU">
              <input type="button" name="Button2" value="Delete Item">
              <input type="button" name="Button22" value="View Item Associations" onClick="parent.location='itemAssociations.htm'">
          -->
         </div>
         <p align="center">&nbsp;</p>
        </td>
        <td width="0"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<p align="center">&nbsp;</p>
</html:form>
<p>&nbsp;</p>
</div>

<% if ("Y".equals(isMSIE)) { %>
<script for=document event="onclick()">
<!--
document.all.CalFrame.style.display="none";
//-->
</script>
<% }  %>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>



