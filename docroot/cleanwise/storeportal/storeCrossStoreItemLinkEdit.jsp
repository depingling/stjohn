<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CategoryUtil" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.*" %>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="STORE_ADMIN_ITEM_FORM" type="com.cleanwise.view.forms.StoreItemMgrForm"/>
<%
boolean autoSkuFl = theForm.getAutoSkuFlag();
boolean hasFullDistList = (theForm.getDistItemsFullList() != null && theForm.getDistItemsFullList().size() > 0);
boolean[] multipleAccounts = theForm.getMultipleAccount();
String showPricingLink = "crossStoreItemLinkEdit.do?showPricing=true&action=edit&itemId=" + request.getParameter("itemId");
%>

<bean:define id="product" name="STORE_ADMIN_ITEM_FORM" property="product" scope="session"/>
<bean:define id="imageName" name="STORE_ADMIN_ITEM_FORM" property="product.image" scope="session"/>
<bean:define id="uomCd" name="product" property="uom"/>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

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

var dualElements = ["image", "thumbnail", "msds", "ded", "spec"];
function refresh(useUrls) {
    if (useUrls != null) {
        for (var i = 0; i < dualElements.length; i++) {
            showOrHide(dualElements[i] + "Url",  useUrls.checked == true);
            showOrHide(dualElements[i] + "File", useUrls.checked == false);
        }
    }
}
function showOrHide(objId, show) {
    var obj = document.getElementById(objId);
    if (obj != null) {
        obj.style.display = (show) ? 'block' : 'none';
        obj.disabled = (show == false);
    }
}
//-->
</script>


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
                var htmln = '<input type="text" name="' + targetName + '" value="" size="4" maxlength="30">';
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


function SetCheckedAllItemProperty(nameCollection,num,val) {
var dml=document.forms['StoreAdminItemForm'];
 var ellen = dml.elements.length;
var s;
if(num>=0)
s=nameCollection+"["+num+"]";
else s=nameCollection+"[";
var i=0;
  for(j=0; j<ellen; j++) 
if (dml.elements[j].name.indexOf(s)>-1) {
      dml.elements[j].checked=val;
     }
}

function SetCheckedItemProperty(nameCollection,property,size,val) {
//alert('SETCHECK');
if(size>0){
var dml=document.forms['StoreAdminItemForm'];
 var ellen = dml.elements.length;
var s;
var i=0;
 for(j=0; j<ellen; j++) {
    s=s+" , "+dml.elements[j].name;
//alert(nameCollection+"["+i+"]."+property+"="+dml.elements[j].name);
    if (dml.elements[j].name==nameCollection+"["+i+"]."+property) {
      dml.elements[j].checked=val;
      if(i==size) break;
      i++;
    }

  }
//alert(s);
}
 }
function unlinkItem(formNum,action,nameEl,valueEl) {
 var actions;
 var param;
 actions=document.forms[formNum]["action"];
 param=document.forms[formNum][nameEl] ;
 param.value=valueEl;
 //alert(actions.length);
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='hiddenAction') {
     actions[ii].value=action;
     document.forms[formNum].submit();
     break;
   }
 }
 return false
 }


//-->
</script>


<% if ("Y".equals(isMSIE)) { %>
<script language="JavaScript" src="../externals/calendar.js"></script>
<% } else {  %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% }  %>


<body bgcolor="#cccccc" onLoad="refresh(document.getElementById('useUrls'));">

<% if ("Y".equals(isMSIE)) { %>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
    marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% }  %>

<div class = "text">
<jsp:include flush='true' page="locateItem.jsp">
   	<jsp:param name="jspFormAction"                   value="/storeportal/crossStoreItemLinkEdit.do" /> 
   	<jsp:param name="jspFormName" 	                  value="STORE_ADMIN_ITEM_FORM" />
   	<jsp:param name="jspSubmitIdent" 	              value="" />
   	<jsp:param name="jspReturnFilterProperty"         value="itemsToLink" />
   	<jsp:param name="checkBoxShowInactive"            value="hide" />
	<jsp:param name="useItemsLinksForAllStores"       value="true"/>
</jsp:include>

<table ID=937 border="0"cellpadding="0" cellspacing="1" width="769">
<html:form styleId="StoreAdminItemForm_CrossStoreItemLinks" action="/storeportal/crossStoreItemLinkEdit.do" enctype="multipart/form-data">
  <html:hidden property="useItemsLinksForAllStores" value="true"/>
  <tr bgcolor="#cccccc">
    <table ID=938 width="767" border="0" bgcolor="#cccccc">
    <tr>
    <td width="10"></td>
    <td width="744">

       <table ID=939 border="0" width="722" bgcolor="#cccccc">
         <tr>
           <td colspan="4">
          <b>SKU:</b>&nbsp;&nbsp;
           <% if( autoSkuFl) { %>
           <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.skuNum" value="0">
            <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.skuNum"/>
           </logic:notEqual>
           <% } else { %>
           <html:text name="STORE_ADMIN_ITEM_FORM" property="skuNum" readonly="true"/>
            <% } %>
         </tr>

         <tr>
         <td><b>Item ID:</b>
         <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.productId"/>
         </td>
		  </td>
		  <td > &nbsp;&nbsp;&nbsp;
          </td>
         <td > <b>Item Name:</b>
         <td>  <html:text name="STORE_ADMIN_ITEM_FORM" property="product.shortDesc" size="75" maxlength="255" readonly="true"/>
         </td>
         </tr>

<logic:present name="STORE_ADMIN_ITEM_FORM" property="linkedProductItemsBetweenStores">
<bean:size id="linkedCount"  name="STORE_ADMIN_ITEM_FORM" property="linkedProductItemsBetweenStores"/>
<tr><td colspan=5><b>Stores :</b>
<logic:greaterThan name="linkedCount" value="0">
<%int idx=0;%>
<logic:iterate id="linkedItemBetweenStores" name="STORE_ADMIN_ITEM_FORM" property="linkedProductItemsBetweenStores">
<bean:define id="storeShortDesc" name="linkedItemBetweenStores" property="store.busEntity.shortDesc" />
<%StringTokenizer st=new StringTokenizer(((String)(storeShortDesc))," ");
int i=0;
String s="&lt;";
while(st.hasMoreElements())
{
s=s+(i!=0?"&nbsp;":"")+st.nextElement();
i++;
}%>
<%if(idx!=0){%><%=", "%><%}%>
<%=s+"&gt;"%>
<%idx++;%>
</logic:iterate>
</logic:greaterThan></td>
</tr>
</logic:present>
</td>
</tr>
       </table>
       <div align="left"><br>
 <table ID=940 bgcolor="#ccffcc">
         <tr>
         <td colspan="4"> <b>Long Description:</b></td>
         </tr>
         <tr>
         <td colspan="2">
         <html:textarea name="STORE_ADMIN_ITEM_FORM" property="product.longDesc" cols="40" rows="14" readonly="true"/>
         </td>
         <td valign="top"></td>
         <td colspan="2" >
           <div align="center">
           <bean:define id="image" value="<%=new String(\"../\"+imageName) %>"/>
           <%
              if((image.indexOf(".jpg") < 0) && (image.indexOf(".gif") < 0)){

                  image = "../en/images/noMan.gif";
              }
           %>
           <html:img styleId="941" src="<%=image%>"/>
<br>Path:<%=image%>&nbsp;&nbsp;&nbsp;&nbsp;
           </div>
         </td>
         </tr>

         <tr bgcolor="#cccccc"><td colspan=5 align=center>

    <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.productId" value="0">
        <html:submit property="action" value="Find Items To Link"/>
    </logic:notEqual>

        <html:submit property="action" value="Back"/>
</td>
</tr>
<tr>
<td colspan="2"></td>
</tr>        
<tr>
         <td><b>Status:</b></td>
            <td>
                <html:text styleId="statusCd" name="STORE_ADMIN_ITEM_FORM" property="statusCd" maxlength="30" readonly="true"/>
             </td>
         <td><b>Image:</b></td>
         <td>
             <html:text styleId="imageUrl" name="STORE_ADMIN_ITEM_FORM" property="imageUrl" readonly="true"/>
         </td>
        </tr>
        <tr>
         <td colspan="2">&nbsp;</td>
         <td><b>Thumbnail:</b></td>
         <td>
             <html:text styleId="thumbnailUrl" name="STORE_ADMIN_ITEM_FORM" property="thumbnailUrl" readonly="true"/>
         </td>
        </tr>
        <tr>
         <td colspan="3"/>
         <td>
           <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.thumbnail" value="">
              <bean:define id="thumbnailImage" name="STORE_ADMIN_ITEM_FORM" property="product.thumbnail" scope="session"/>
<% String thumbnailClick = "window.open('/"+storeDir+"/"+thumbnailImage+"','THUMBNAIL');";%>
             Path:<bean:write name="STORE_ADMIN_ITEM_FORM" property="product.thumbnail"/><br>
             <html:button styleClass="text" onclick="<%=thumbnailClick%>" value="View Thumbnail" property="action"/>
             <html:submit styleClass="text" value="Delete Thumbnail" property="action"/>
           </logic:notEqual>
           <logic:equal name="STORE_ADMIN_ITEM_FORM" property="product.thumbnail" value="">
             <html:button styleClass="text" value="View Thumbnail" property="action" disabled="true"/>
           </logic:equal>
         </td>
        </tr>
        <tr>
         <td><b>Manufacturer&nbsp;Id:</b></td>
         <td>
         <html:text styleId="selectedManufId" name="STORE_ADMIN_ITEM_FORM" property="selectedManufId" maxlength="30" readonly="true"/>
         </td>
         <td><b>MSDS:</b></td>
         <td>
             <html:text styleId="msdsUrl" name="STORE_ADMIN_ITEM_FORM" property="msdsUrl" readonly="true"/>
         </td>
         </tr>
         </tr>
         <tr>
         <td><b>Manufacturer SKU:</b></td>
         <td colspan="2">
            <html:text name="STORE_ADMIN_ITEM_FORM" property="manufacturerSku" maxlength="30" readonly="true"/>
         </td>
             <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.msds" value="">
             <bean:define id="msdsName" name="STORE_ADMIN_ITEM_FORM" property="product.msds" scope="session"/>
             <% String msdsClick = new String("window.open('/"+storeDir+"/"+msdsName+"','MSDS');");%>

            <td>
            Path:<bean:write name="STORE_ADMIN_ITEM_FORM" property="product.msds"/><br>
            <html:button styleClass="text" onclick="<%=msdsClick%>" value="View MSDS" property="action"/>
            <html:submit styleClass="text" value="Delete MSDS" property="action"/>
            </td>
             </logic:notEqual>
             <logic:equal name="STORE_ADMIN_ITEM_FORM" property="product.msds" value="">
             <td><html:button styleClass="text" value="View MSDS" property="action" disabled="true"/></td>
             </logic:equal>
         </tr>
         <tr>
         <td><b>Category:</b>
</td>
        <td colspan=3>
        <html:text styleId="selectedCategoryId" name="STORE_ADMIN_ITEM_FORM" property="selectedCategoryId" maxlength="30" readonly="true"/>
        </td>
</tr>

<tr>
<td></td><td></td>
         <td><b>DED:</b></td>
         <td>
             <html:text styleId="dedUrl" name="STORE_ADMIN_ITEM_FORM" property="dedUrl" style="display:none;" readonly="true"/>
         </td>
         </tr>
         <tr>
         <td><b>Product UPC:</b></td>
         <td>
            <html:text name="STORE_ADMIN_ITEM_FORM" property="product.upc"  maxlength="15" readonly="true"/>
         </td>
             <td></td>
             <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.ded" value="">
             <bean:define id="dedName" name="STORE_ADMIN_ITEM_FORM" property="product.ded" scope="session"/>
             <% String dedClick = new String("window.open('/"+storeDir+"/"+dedName+"','DED');");%>

             <td>
             Path:<bean:write name="STORE_ADMIN_ITEM_FORM" property="product.ded"/><br>
             <html:button styleClass="text" onclick="<%=dedClick%>" value="View DED" property="action"/>
             <html:submit styleClass="text" value="Delete DED" property="action"/>
             </td>
             </logic:notEqual>

             <logic:equal name="STORE_ADMIN_ITEM_FORM" property="product.ded" value="">
             <td>
             <html:button styleClass="text" value="View DED" property="action" disabled="true"/></td>
             </logic:equal>
         </tr>
         <tr>
         <td><b>Pack UPC:</b></td>
         <td>
            <html:text name="STORE_ADMIN_ITEM_FORM" property="product.pkgUpc"  maxlength="15" readonly="true"/>
         </td>
         <td><b>Prod Spec:</b></td>
         <td>
             <html:text styleId="specUrl" name="STORE_ADMIN_ITEM_FORM" property="specUrl" readonly="true"/>
         </td>
         </tr>
         <tr>
         <td><b>UNSPSC Code:</b></td>
         <td>
            <html:text size="10" maxlength="10" name="STORE_ADMIN_ITEM_FORM" property="product.unspscCd" readonly="true"/>[10 digits]
         </td>
             <td></td>
             <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.spec" value="">
             <bean:define id="specName" name="STORE_ADMIN_ITEM_FORM" property="product.spec" scope="session"/>
             <% String specClick = new String("window.open('/"+storeDir+"/"+specName+"','Spec');");%>
             <td>
             Path:<bean:write name="STORE_ADMIN_ITEM_FORM" property="product.spec" /><br>
             <html:button styleClass="text" onclick="<%=specClick%>" value="View Product Spec" property="action"/>
             <html:submit styleClass="text" value="Delete Product Spec" property="action"/>
             </td>
             </logic:notEqual>
             <logic:equal name="STORE_ADMIN_ITEM_FORM" property="product.spec" value="">
             <td><html:button styleClass="text" value="View Product Spec" property="action" disabled="true"/></td>
             </logic:equal>
         </tr>

         <tr>
         <td><b>Color:</b></td>
         <td>
            <html:text name="STORE_ADMIN_ITEM_FORM" property="product.color" maxlength="30" readonly="true"/>
         </td>
         <td><b>Item Size:</b></td>
         <td colspan="2">
            <html:text name="STORE_ADMIN_ITEM_FORM" property="product.size"  maxlength="30" readonly="true"/>
         </td>
         </tr>

         <tr>
         <td><b>Scent:</b></td>
         <td>
            <html:text name="STORE_ADMIN_ITEM_FORM" property="product.scent" maxlength="30" readonly="true"/>
         </td>
         <td><b>Shipping Weight:</b></td>
         <td>
           <nobr>
            <html:text name="STORE_ADMIN_ITEM_FORM" property="product.shipWeight"  maxlength="30" readonly="true"/>
            <b>Weight Unit:</b>
            <html:text styleId="product.weightUnit" name="STORE_ADMIN_ITEM_FORM" property="product.weightUnit" maxlength="20" readonly="true"/>
            </nobr>
         </td>
         </tr>

         <tr>
         </td>
         <td><b>Shipping Cubic Size:</b></td>
         <td>
            <html:text name="STORE_ADMIN_ITEM_FORM" property="product.cubeSize"  maxlength="30" readonly="true"/>
         </td>
         <td><b>List Price (MSRP):</b></td>
         <td>
            <html:text name="STORE_ADMIN_ITEM_FORM" property="listPrice" maxlength="10" readonly="true"/>
         </td>
         </tr>
         <tr>
       <tr>
        <td colspan="2">&nbsp;</td>
      <td><b>NSN:</b></td>
       <td>
       <html:text name="STORE_ADMIN_ITEM_FORM" property="product.nsn" maxlength="13" size="13" readonly="true"/>
      </td>
     </tr>
     <%-- add in smaller props here to make UI--%>
         <td colspan="4">
         <table ID=942>
       <tr>
         <td><b>UOM:</b></td>
         <td>
          <table ID=943 cellpadding=0 cellspacing=0 border=0>
           <tr>
           <td>
           <html:text styleId="uom" name="STORE_ADMIN_ITEM_FORM" property="uom" maxlength="20" readonly="true"/>
          </td>
          <td>
            <div id="UOMCD">
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="uom" value="OTHER">
              <html:text name="STORE_ADMIN_ITEM_FORM" property="product.uom" size="4" maxlength="30" readonly="true"/>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="uom" value="OTHER">
              <html:hidden name="STORE_ADMIN_ITEM_FORM" property="product.uom"/>
            </logic:notEqual>
            </div>
          </td>
          <td></td>
           </tr>
          </table>
        </td>
          <td><b>UOM and Pack<BR>vary by<BR>geography:</b></td>
        <td>
           <html:text styleId="product.packProblemSku" name="STORE_ADMIN_ITEM_FORM" property="product.packProblemSku" maxlength="20" readonly="true"/>
        </td>
        <td><b>Pack:</b></td>
         <td>
          <html:text name="STORE_ADMIN_ITEM_FORM" property="product.pack" maxlength="30" size="4" readonly="true"/>
         </td>
          <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
         <td><b>HAZMAT:</b></td>
         <td>
           <html:text styleId="hazmat" name="STORE_ADMIN_ITEM_FORM" property="hazmat" maxlength="20" readonly="true"/>
         </td>
      </tr>
             <logic:present name="STORE_ADMIN_ITEM_FORM" property="selectCertCompanies.values">
                 <bean:size id="ccsize" name="STORE_ADMIN_ITEM_FORM" property="selectCertCompanies.values"/>
                 <logic:greaterThan name="ccsize" value="0">
                     <tr>
                         <td><b>Certified Companies :</b></td>
                         <td>
                             <table ID=944>
                                 <logic:iterate id="arrele" name="STORE_ADMIN_ITEM_FORM"
                                                property="selectCertCompanies.values" indexId="i">
                                     <bean:define id="key" name="arrele" property="busEntityId"/>
                                     <tr>
                                         <%
                                             String selectedStr = "selectCertCompanies.selected[" + i + "]";
                                         %>
                                         <td class="stpTD">
                                             <html:multibox name="STORE_ADMIN_ITEM_FORM" property="<%=selectedStr%>"
                                                            value="true"/>
                                             <bean:write name="arrele" property="shortDesc"/>
                                         </td>
                                     </tr>
                                 </logic:iterate>
                             </table>

                         </td>
                     </tr>
                 </logic:greaterThan>
             </logic:present>
      </table>
         </tr>


<br/>


<html:hidden name="STORE_ADMIN_ITEM_FORM" property="showPricing"/>


<tr bgcolor="#cccccc">
<td colspan=5 align=center>
<bean:define id="changesFl" name="STORE_ADMIN_ITEM_FORM" property="changesOnItemEditPageFl"/>
</td></tr>

<jsp:include page="storeCrossStoreItemLinkDetail.jsp" flush="true">
    <jsp:param name="jspSubmitActionName"     value="Unlink Items"/> 
    <jsp:param name="jspSubmitFormName"       value="StoreAdminItemForm_CrossStoreItemLinks"/>
    <jsp:param name="jspSubmitCollectionName" value="unlinkedItemIdBetweenStores"/>
</jsp:include>

  </td></tr>
        </table>

        <!-- Control buttons -->
        <!--<html:submit property="action" value="Save Item"/>-->
         </div>
         <p align="center">&nbsp;</p>
        </td>
        <td width="0"></td>
        </tr>
      </table>
    </td>
  </tr>
 <html:hidden  property="action" value="hiddenAction"/> 
</html:form>
</table>
<p align="center">&nbsp;</p>
<p>&nbsp;</p>
</div>


<% if ("Y".equals(isMSIE)) { %>
<script for=document event="onclick()">
</script>
<% }  %>







