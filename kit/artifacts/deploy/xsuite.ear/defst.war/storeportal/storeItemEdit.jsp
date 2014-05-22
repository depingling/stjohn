<%@ page language="java" %>

<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.utils.CategoryUtil" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.StringBuffer" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>

<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="com.cleanwise.view.logic.StoreItemMgrLogic" %>

<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="STORE_ADMIN_ITEM_FORM" type="com.cleanwise.view.forms.StoreItemMgrForm"/>
<%
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
boolean autoSkuFl = theForm.getAutoSkuFlag();
boolean hasFullDistList = (theForm.getDistItemsFullList() != null && theForm.getDistItemsFullList().size() > 0);
boolean[] multipleAccounts = theForm.getMultipleAccount();
String showPricingLink = "item-edit.do?showPricing=true&action=edit&itemId=" + request.getParameter("itemId");
boolean canEdit = theForm.getIsEditable();
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

<script type="text/javascript">
<!--
function doSubmit(target) {
	//We can do it by submitting the from: Begin
	
    //alert("Inside doSubmit(target) js method ! ");
    //alert("target = " + target);
    //var theForm = document.getElementById('StoreAdminItemForm');
    //alert("theForm = " + theForm);
    //theForm.action = target;
    //theForm.submit(); 
    
    //We can do it by submitting the from: End
    
    // we can do it by opening an new window (see below)
    window.open(target);
}
// -->
</script>

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
    var prodUOMElement = document.getElementById('product.uom');
    if (prodUOMElement != null) {
        var prodUom = prodUOMElement.value;
	showHideUOMCD(document.getElementById('uom'),prodUom);
    }
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

<style type="text/css">
    .UOMCDstyle {
    	width:50px;
    	margin:0px auto;
    	visibility:hidden;
    }
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


function showHideUOMCD(element,prodUom)
{
	  var UOMCDstyle = new String();
    var UOMValue = element.options[element.selectedIndex].value;
    if(element.options[element.selectedIndex].value=="OTHER") 
    {
    	 document.getElementById('UOMCD').style.visibility = "visible";
    	 document.getElementById('product.uom').value = prodUom;
    }else{
       document.getElementById('UOMCD').style.visibility = "hidden";
    	 document.getElementById('product.uom').value = UOMValue;
    }
}


function actionSubmitOrig(action) {
	/***
    var actions = document.getElementsByName('action');
    alert("actions.length:  " + actions.length);
    for(ii = 0; ii < actions.length; ii++) {
        alert("value: " + actions[ii].value);

            alert("action: " + action);
            actions[ii].value = action;
            actions[ii].form.submit();

    }    
    return false;
    ***/
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


<body bgcolor="#cccccc" onLoad="refresh(document.getElementById('useUrls'));refMSDS();">

<% if ("Y".equals(isMSIE)) { %>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
    marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% }  %>

<div class = "text">
<logic:equal name="STORE_ADMIN_ITEM_FORM"   property="openItemLinkManagedFl" value="true">
<jsp:include flush='true' page="locateItem.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/item-edit.do" />
   <jsp:param name="jspFormName" 	value="STORE_ADMIN_ITEM_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" 	value="itemsToLink" />
   <jsp:param name="checkBoxShowInactive" value="hide" />
</jsp:include>
</logic:equal>

<table ID=937 border="0"cellpadding="0" cellspacing="1" width="769">
<html:form styleId="StoreAdminItemForm" action="/storeportal/item-edit.do" enctype="multipart/form-data">
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
           <html:text name="STORE_ADMIN_ITEM_FORM" property="skuNum"/><span class="reqind">*</span>
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
         <td>  
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text name="STORE_ADMIN_ITEM_FORM" property="product.shortDesc" size="75" maxlength="255"/><span class="reqind">*</span>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.shortDesc"/>
            </logic:notEqual>
         </td>
         </tr>
		 <logic:equal name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">
<logic:present name="STORE_ADMIN_ITEM_FORM" property="linkedProductItems">
<bean:size id="linkedCount"  name="STORE_ADMIN_ITEM_FORM" property="linkedProductItems"/>
<tr><td colspan=5><b>Stores :</b>
<logic:greaterThan name="linkedCount" value="0">
<%int idx=0;%>
<logic:iterate id="linkedItem" name="STORE_ADMIN_ITEM_FORM" property="linkedProductItems">
<bean:define id="storeShortDesc" name="linkedItem" property="store.busEntity.shortDesc" />
<%StringTokenizer st=new StringTokenizer(((String)(storeShortDesc))," ");
int i=0;
String s="&lt";
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
</tr></logic:equal>
       </table>
       <div align="left"><br>
 <table ID=940 bgcolor="#ccffcc">
	<!--Checking for storeId=1 to show specific lawson fields only for cleanwise store (BUG 4995)-->
         <% if(appUser.getUserStore().getStoreId() == 1) { %> 
         <tr>
         <td> <b>Other Description:</b></td>
         <td colspan="5">
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text name="STORE_ADMIN_ITEM_FORM" property="product.otherDesc" maxlength="80" size="95"/>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.otherDesc"/>
            </logic:notEqual>
         </td>
         </tr>
         <% } %>
         <tr>
         <td colspan="4"> <b>Long Description:</b></td>
         </tr>
         <tr>
         <td colspan="2">
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:textarea name="STORE_ADMIN_ITEM_FORM" property="product.longDesc" cols="40" rows="14"/>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.longDesc"/>
            </logic:notEqual>
         </td>
         <td valign="top">
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <span class="reqind">*</span>
            </logic:equal>
        </td>
         <td colspan="2" >
           <div align="center">           
           <% 
           String imageStorageTypeCd = theForm.getImageStorageTypeCd(); 
           %>
           <bean:define id="image" value="<%=new String(\"../\"+imageName) %>"/>
		   <% if (imageStorageTypeCd==null || imageStorageTypeCd.equals("") || imageStorageTypeCd.equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)){ %>
                   
           <%
                  if((image.indexOf(".jpg") < 0) && (image.indexOf(".gif") < 0)){

                      image = "../en/images/noMan.gif";
                  
           %>
                  <html:img styleId="941" src="<%=image%>"/>
<br>Path:<%=image%>&nbsp;&nbsp;&nbsp;&nbsp;
               <% } else { %>
           <%                      
                     String actionImageSubmitStringForDb = "/storeportal/item-edit.do?action=itemDocumentFromDb&path=" + imageName;                             
           %>
                     <html:img styleId="941" action="<%=actionImageSubmitStringForDb%>"/>
                     <br>Database
               <% } %>                 
           <% } else { //E3 Storage %>
                <% 
                       String actionImageSubmitStringForE3Storage = "/storeportal/item-edit.do?action=itemDocumentFromE3Storage&path=" + imageName;                      
                       if (imageName==null || imageName.equals("")) {
                    	   image = "../en/images/noMan.gif";
                %>
                           <html:img styleId="941" src="<%=image%>"/>
                           <br>Path:<%=image%>&nbsp;&nbsp;&nbsp;&nbsp;
                <%     } else { %>                 
                       <html:img styleId="941" action="<%=actionImageSubmitStringForE3Storage%>"/>
                       <br>E3 Storage
                <%     } %>
            <% } %>                                                  
                <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                    <html:submit styleClass="text" value="Delete Image" property="action"/>
                </logic:equal>
           </div>
         </td>
         </tr>

         <tr bgcolor="#cccccc">
            <td colspan=5 align=center>
            <logic:equal name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">
                <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                    <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.productId" value="0">
                        <html:submit property="action" value="Find Items To Link"/>
                    </logic:notEqual>
                </logic:equal>
            </logic:equal>
            
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:submit property="action" value="Save Item"/>
            </logic:equal>
            
            <logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">
                <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                    <logic:equal name="STORE_ADMIN_ITEM_FORM" property="managedItemFlag" value="true">
                        <html:submit property="action" value="Break Managed Item Link"/>
                    </logic:equal>
                </logic:equal>
            </logic:notEqual>
            
            <html:submit property="action" value="Back"/>
            </td>
        </tr>
<tr>
<td colspan="4" align="center">
        <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
            <html:checkbox name="STORE_ADMIN_ITEM_FORM" property="useUrls" styleId="useUrls" onclick="refresh(this);">
                <b>Load data from URL</b>
            </html:checkbox>
        </logic:equal>
        <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
            <html:checkbox name="STORE_ADMIN_ITEM_FORM" property="useUrls" styleId="useUrls" disabled="true" onclick="refresh(this);">
                <b>Load data from URL</b>
            </html:checkbox>
        </logic:notEqual>
</td>
</tr>
<tr>
         <td><b>Status:</b></td>
            <td>
                <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                    <html:select name="STORE_ADMIN_ITEM_FORM" property="statusCd">
                        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                        <html:options  collection="Item.status.vector" property="value" />
                    </html:select>
                    <span class="reqind">*</span>
                </logic:equal>
                <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                    <bean:write name="STORE_ADMIN_ITEM_FORM" property="statusCd"/>
                </logic:notEqual>
             </td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <b>Image:</b>
            </logic:equal>
         </td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                 <html:file styleId="imageFile" name="STORE_ADMIN_ITEM_FORM" property="imageFile"
                  accept="image/jpeg,image/gif"/>
                 <html:text styleId="imageUrl" name="STORE_ADMIN_ITEM_FORM" property="imageUrl"
                   style="display:none;"/>
            </logic:equal>
         </td>
        </tr>
        <tr>
         <td colspan="2">&nbsp;</td>
        <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <b>Thumbnail:</b>
            </logic:equal>
        </td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                 <html:file name="STORE_ADMIN_ITEM_FORM" property="thumbnailFile"
                  accept="image/jpeg,image/gif"/>
                 <html:text styleId="thumbnailUrl" name="STORE_ADMIN_ITEM_FORM" property="thumbnailUrl"
                   style="display:none;"/>
            </logic:equal>
         </td>
        </tr>
        <tr>
         <td colspan="3"/>
         <td>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.thumbnail" value="">
            <bean:define id="thumbnailImage" name="STORE_ADMIN_ITEM_FORM" property="product.thumbnail" scope="session"/>
             <% String thumbnailClick = "window.open('/"+storeDir+"/"+thumbnailImage+"','THUMBNAIL');";%>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <b>Thumbnail:&nbsp;&nbsp;</b>
            </logic:notEqual>
            <% String thumbnailStorageTypeCd = theForm.getThumbnailStorageTypeCd(); %>
			<% if (thumbnailStorageTypeCd==null || thumbnailStorageTypeCd.equals("") || thumbnailStorageTypeCd.equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)){ 
				String thumbnailFromDbTypeLink = "item-edit.do?action=itemDocumentFromDb&path=" + thumbnailImage;   
				String actionThumbnailSubmitStringForDb = "window.open('" + thumbnailFromDbTypeLink +"');";                                  
            %>      
                   Database<br>
                      <html:button styleClass="text" onclick="<%=actionThumbnailSubmitStringForDb%>" value="View Thumbnail" property="action"/>
            <% } else { %>
                   E3 Storage<br>
                <% 
                       String thumbnailTypeLink = "item-edit.do?action=itemDocumentFromE3Storage&path=" + thumbnailImage;
                       String actionThumbnailSubmitString = "window.open('" + thumbnailTypeLink +"');";
   	            %>
   	                   <html:button styleClass="text" onclick="<%=actionThumbnailSubmitString%>" value="View Thumbnail" property="action"/>   	                  
            <% } %>                            
             <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                    <html:submit styleClass="text" value="Delete Thumbnail" property="action"/>
             </logic:equal>
            </logic:notEqual>
             <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="product.thumbnail" value="">
                 <html:button styleClass="text" value="View Thumbnail" property="action" disabled="true"/>
             </logic:equal>
             </logic:equal>
         </td>
        </tr>
        <tr>
         <td valign="top"><b>Manufacturer&nbsp;Id:</b></td>
         <td valign="top">
         <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
             <html:select name="STORE_ADMIN_ITEM_FORM" property="selectedManufId" styleId="manufacturer"
             	onchange="refMSDS();">
                    <html:option value="0">--Select Manufacturer--</html:option>
                    <logic:iterate id="manuf" name="STORE_ADMIN_ITEM_FORM" property="storeManufacturers"
                        type="com.cleanwise.service.api.value.ManufacturerData"><bean:define
                        id="manufId" name="manuf" property="busEntity.busEntityId"/>
                    <html:option value="<%=manufId.toString()%>"><bean:write name="manuf" property="busEntity.shortDesc"/></html:option>
                 </logic:iterate>
            </html:select>
         </logic:equal>
         <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
             <html:select name="STORE_ADMIN_ITEM_FORM" property="selectedManufId" disabled="true">
                    <html:option value="0">--Select Manufacturer--</html:option>
                    <logic:iterate id="manuf" name="STORE_ADMIN_ITEM_FORM" property="storeManufacturers"
                        type="com.cleanwise.service.api.value.ManufacturerData">
                    <bean:define id="manufId" name="manuf" property="busEntity.busEntityId"/>
                    <html:option value="<%=manufId.toString()%>"><bean:write name="manuf" property="busEntity.shortDesc"/></html:option>
                 </logic:iterate>
            </html:select>
         </logic:notEqual>
         </td>
         <td valign="top">
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <b>MSDS:</b>
            </logic:equal>
        </td>
         <td rowspan="2">
<%@include file="storeItemEditMSDS.jsp"%>
         </td>
         </tr>
         <tr>
         <td><b>Manufacturer SKU:</b></td>
         <td colspan="2">
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text name="STORE_ADMIN_ITEM_FORM" property="manufacturerSku" maxlength="30"/><span class="reqind">*</span>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="manufacturerSku"/>
            </logic:notEqual>
         </td><%--
             <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.msds" value="">
                <bean:define id="msdsName" name="STORE_ADMIN_ITEM_FORM" property="product.msds" scope="session"/>
                <% String msdsClick = new String("window.open('/"+storeDir+"/"+msdsName+"','MSDS');");%>


                <td>
				<logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                 <b>MSDS:&nbsp;&nbsp;&nbsp;</b>
                </logic:notEqual>
                Path:<bean:write name="STORE_ADMIN_ITEM_FORM" property="product.msds"/><br>
                <html:button styleClass="text" onclick="<%=msdsClick%>" value="View MSDS" property="action"/>
                <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                    <html:submit styleClass="text" value="Delete MSDS" property="action"/>
                </logic:equal>
                </td>
             </logic:notEqual>
             
             <logic:equal name="STORE_ADMIN_ITEM_FORM" property="product.msds" value="">
                <td>
                    <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                        <html:button styleClass="text" value="View MSDS" property="action" disabled="true"/>
                    </logic:equal>
                </td>
             </logic:equal>--%>
         </tr>
         <tr>
         <td><b>Category:</b><span class="reqind">*</span>
</td>
        <td colspan=3>
        <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
            <html:select name="STORE_ADMIN_ITEM_FORM" property="selectedCategoryId">
                <html:option value="0">--Select Category--</html:option>
                <logic:iterate id="category" name="STORE_ADMIN_ITEM_FORM" property="storeCategories"
                    type="com.cleanwise.service.api.value.CatalogCategoryData">
                    <bean:define id="categoryId" name="category" property="itemData.itemId"/>
                    <html:option value="<%=categoryId.toString()%>">
                        <logic:greaterThan name="category" property="treeLevel" value="1">
                          <bean:define id="level" type= "Integer" name="category" property="treeLevel" />
                          <%
                          StringBuffer buf=new StringBuffer();
                          for (int i=1; i < level.intValue(); i++) {
                            buf.append(">");
                          }
                          String treeLevS = buf.toString();
                          %>
                          <%=treeLevS%>
                        </logic:greaterThan>
                        <bean:write name="category" property="itemData.shortDesc"/>
                        <logic:present name="category" property="itemData.longDesc">
                            (<bean:write name="category" property="itemData.longDesc"/>)
                        </logic:present>

                    </html:option>
                </logic:iterate>
            </html:select>
        </logic:equal>
        <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
            <html:select name="STORE_ADMIN_ITEM_FORM" property="selectedCategoryId" disabled="true">
                <html:option value="0">--Select Category--</html:option>
                <logic:iterate id="category" name="STORE_ADMIN_ITEM_FORM" property="storeCategories"
                    type="com.cleanwise.service.api.value.CatalogCategoryData">
                    <bean:define id="categoryId" name="category" property="itemData.itemId"/>
                    <html:option value="<%=categoryId.toString()%>">
                        <logic:greaterThan name="category" property="treeLevel" value="1">
                          <bean:define id="level" type= "Integer" name="category" property="treeLevel" />
                          <%
                          StringBuffer buf=new StringBuffer();
                          for (int i=1; i < level.intValue(); i++) {
                            buf.append(">");
                          }
                          String treeLevS = buf.toString();
                          %>
                          <%=treeLevS%>
                        </logic:greaterThan>
                        <bean:write name="category" property="itemData.shortDesc"/>
                        <logic:present name="category" property="itemData.longDesc">
                            (<bean:write name="category" property="itemData.longDesc"/>)
                        </logic:present>

                    </html:option>
                </logic:iterate>
            </html:select>
        </logic:notEqual>
        </td>
</tr>

<tr>
<!--
         <td>
           <logic:greaterThan name="STORE_ADMIN_ITEM_FORM" property="categoryListSize" value="0">
             <logic:iterate id="category" name="STORE_ADMIN_ITEM_FORM" property="product.catalogCategories"
              type="com.cleanwise.service.api.value.CatalogCategoryData">
                <bean:write name="category" property="catalogCategoryShortDesc"/>
             </logic:iterate>
            </logic:greaterThan>
         </td>
-->
<td></td><td></td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <b>DED:</b>
            </logic:equal>
         </td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
             <html:file name="STORE_ADMIN_ITEM_FORM" property="dedFile"
              accept="application/pdf"/>
             <html:text styleId="dedUrl" name="STORE_ADMIN_ITEM_FORM" property="dedUrl"
               style="display:none;"/>
            </logic:equal>
         </td>
         </tr>
         <tr>
         <td><b>Product UPC:</b></td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text name="STORE_ADMIN_ITEM_FORM" property="product.upc"  maxlength="15"/>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.upc"/>
            </logic:notEqual>
         </td>
             <td></td>
             <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.ded" value="">
                <bean:define id="dedName" name="STORE_ADMIN_ITEM_FORM" property="product.ded" scope="session"/>
                <% //String dedClick = new String("window.open('/"+storeDir+"/"+dedName+"','DED');");%>               
                <% String dedClick = new String("window.open('/"+storeDir+"/"+dedName+"','DED');");%>
                <td>
				<logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <b>DED:&nbsp;&nbsp;</b>
				</logic:notEqual>
				<% String dedStorageTypeCd = theForm.getDedStorageTypeCd(); %>
				<% if (dedStorageTypeCd==null || dedStorageTypeCd.equals("") || dedStorageTypeCd.equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)){ %>
                <%    String dedFromDbTypeLink = "item-edit.do?action=itemDocumentFromDb&path=" + dedName;   
				      String actionDedSubmitStringForDb = "window.open('" + dedFromDbTypeLink +"');";                  
                %>
                Database<br>
                      <html:button styleClass="text" onclick="<%=actionDedSubmitStringForDb%>" value="View DED" property="action"/>
                <% } else { %>
                E3 Storage<br>
                <% 
                       String documentTypeLink = "item-edit.do?action=itemDocumentFromE3Storage&path=" + dedName; //instead of dedClick it was docUrl
                       String actionSubmitString = "window.open('" + documentTypeLink +"');";
   	            %>
   	                   <html:button styleClass="text" onclick="<%=actionSubmitString%>" value="View DED" property="action"/>   	                  
                <% } %>               
                    <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                        <html:submit styleClass="text" value="Delete DED" property="action"/>
                    </logic:equal>
                </td>
             </logic:notEqual>

             <logic:equal name="STORE_ADMIN_ITEM_FORM" property="product.ded" value="">
                <td>
                    <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                            <html:button styleClass="text" value="View DED" property="action" disabled="true"/>
                    </logic:equal>
                </td>
             </logic:equal>
         </tr>
         <tr>
         <td><b>Pack UPC:</b></td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text name="STORE_ADMIN_ITEM_FORM" property="product.pkgUpc"  maxlength="15"/>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.pkgUpc"/>
            </logic:notEqual>
         </td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <b>Prod Spec:</b>
            </logic:equal>
         </td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
             <html:file name="STORE_ADMIN_ITEM_FORM" property="specFile"
              accept="application/pdf"/>
             <html:text styleId="specUrl" name="STORE_ADMIN_ITEM_FORM" property="specUrl"
               style="display:none;"/>
            </logic:equal>
         </td>
         </tr>
         <tr>
         <td><b>UNSPSC Code:</b></td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text size="10" maxlength="10" name="STORE_ADMIN_ITEM_FORM" property="product.unspscCd"/>[10 digits]
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
				<bean:write name="STORE_ADMIN_ITEM_FORM" property="product.unspscCd"/>
            </logic:notEqual>
         </td>
         <td></td>
                <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="product.spec" value="">
                <bean:define id="specName" name="STORE_ADMIN_ITEM_FORM" property="product.spec" scope="session"/>
                <% //String specClick = new String("window.open('/"+storeDir+"/"+specName+"','Spec');");%>
                <td>
				<logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <b>Prod Spec:&nbsp;&nbsp;</b>
				</logic:notEqual>
				<% String specStorageTypeCd = theForm.getSpecStorageTypeCd(); %>
				<% if (specStorageTypeCd==null || specStorageTypeCd.equals("") || specStorageTypeCd.equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)){ %>
                <%    String specFromDbTypeLink = "item-edit.do?action=itemDocumentFromDb&path=" + specName;   
				      String actionSpecSubmitStringForDb = "window.open('" + specFromDbTypeLink +"');";                  
                %>
                Database<br>
                      <html:button styleClass="text" onclick="<%=actionSpecSubmitStringForDb%>" value="View Product Spec" property="action"/>                
                <% } else { %>
                E3 Storage<br>
                <% 
                       String documentTypeLink = "item-edit.do?action=itemDocumentFromE3Storage&path=" + specName; //instead of dedClick it was docUrl
                       String actionSubmitString = "window.open('" + documentTypeLink +"');";
   	            %>
   	                   <html:button styleClass="text" onclick="<%=actionSubmitString%>" value="View Product Spec" property="action"/>   	                  
                <% } %>                               
                    <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                        <html:submit styleClass="text" value="Delete Product Spec" property="action"/>
                    </logic:equal>
                </td>
                </logic:notEqual>
                <logic:equal name="STORE_ADMIN_ITEM_FORM" property="product.spec" value="">
                    <td>
                        <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                            <html:button styleClass="text" value="View Product Spec" property="action" disabled="true"/>
                        </logic:equal>
                    </td>
                </logic:equal>
         </tr>

         <tr>
         <td><b>Color:</b></td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">  
                <html:text name="STORE_ADMIN_ITEM_FORM" property="product.color" maxlength="30"/>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.color"/>
            </logic:notEqual>
         </td>
         <td><b>Item Size:</b></td>
         <td colspan="2">
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text name="STORE_ADMIN_ITEM_FORM" property="product.size"  maxlength="30"/>
                <%--<span class="reqind">*</span>--%>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.size"/>
            </logic:notEqual>
         </td>
         </tr>

         <tr>
         <td><b>Scent:</b></td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text name="STORE_ADMIN_ITEM_FORM" property="product.scent" maxlength="30"/>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.scent"/>
            </logic:notEqual>
         </td>
         <td><b>Shipping Weight:</b></td>
         <td width="30%">
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
               <nobr>
                <html:text name="STORE_ADMIN_ITEM_FORM" property="product.shipWeight"  maxlength="30"/>
                <b>Weight Unit:</b>
                <html:select name="STORE_ADMIN_ITEM_FORM" property="product.weightUnit">
                   <html:option value="<%=RefCodeNames.WEIGHT_UNIT.OUNCE%>"><%=RefCodeNames.WEIGHT_UNIT.OUNCE%></html:option>
                   <html:option value="<%=RefCodeNames.WEIGHT_UNIT.POUND%>"><%=RefCodeNames.WEIGHT_UNIT.POUND%></html:option>
                   <html:option value="<%=RefCodeNames.WEIGHT_UNIT.KILOGRAMME%>"><%=RefCodeNames.WEIGHT_UNIT.KILOGRAMME%></html:option>
                   <html:option value="<%=RefCodeNames.WEIGHT_UNIT.GRAMME%>"><%=RefCodeNames.WEIGHT_UNIT.GRAMME%></html:option>
                </html:select>
                </nobr>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
               <nobr>
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.shipWeight"/>
                <b>Weight Unit:</b>
                <html:select name="STORE_ADMIN_ITEM_FORM" property="product.weightUnit" disabled="true">
                   <html:option value="<%=RefCodeNames.WEIGHT_UNIT.OUNCE%>"><%=RefCodeNames.WEIGHT_UNIT.OUNCE%></html:option>
                   <html:option value="<%=RefCodeNames.WEIGHT_UNIT.POUND%>"><%=RefCodeNames.WEIGHT_UNIT.POUND%></html:option>
                   <html:option value="<%=RefCodeNames.WEIGHT_UNIT.KILOGRAMME%>"><%=RefCodeNames.WEIGHT_UNIT.KILOGRAMME%></html:option>
                   <html:option value="<%=RefCodeNames.WEIGHT_UNIT.GRAMME%>"><%=RefCodeNames.WEIGHT_UNIT.GRAMME%></html:option>
                </html:select>
                </nobr>
            </logic:notEqual>
         </td>
         </tr>

         <tr>
         </td>
         <td><b>Shipping Cubic Size:</b></td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text name="STORE_ADMIN_ITEM_FORM" property="product.cubeSize"  maxlength="30"/>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.cubeSize"/>
            </logic:notEqual>
         </td>
         <td><b>List Price (MSRP):</b></td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text name="STORE_ADMIN_ITEM_FORM" property="listPrice" maxlength="10"/>
                <span class="reqind">*</span>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="listPrice"/>
            </logic:notEqual>
         </td>
         </tr>
         <tr>
       <tr>
      <% if(appUser.getUserStore().getStoreId() == 1) { %>
        <td><b>PSN:</b></td>
        <td>
        <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
            <html:text name="STORE_ADMIN_ITEM_FORM" property="product.psn" maxlength="13" size="13"/>
        </logic:equal>
        <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
            <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.psn"/>
        </logic:notEqual>
        </td>
      <% } else { %>
        <td colspan="2">&nbsp;</td>
       <% } %>
      <td><b>NSN:</b></td>
       <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text name="STORE_ADMIN_ITEM_FORM" property="product.nsn" maxlength="13" size="13"/>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.nsn"/>
            </logic:notEqual>
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
           <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
               <%--<html:select name="STORE_ADMIN_ITEM_FORM" property="uom" onchange="return changeUomCd(this, document.forms[0].elements['product.uom'], 'UOMCD');"> --%>
               <html:select name="STORE_ADMIN_ITEM_FORM" styleId="uom" property="uom" onchange="return showHideUOMCD(this,'');"> 
                 <html:option value="">
                    <app:storeMessage  key="admin.select"/>
                 </html:option>
                 <html:options  collection="Item.uom.vector" property="value" />
               </html:select>
           </logic:equal>
           <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:select name="STORE_ADMIN_ITEM_FORM" styleId="uom" property="uom" disabled="true" onchange="return showHideUOMCD(this,'');"> 
                 <html:option value="">
                    <app:storeMessage  key="admin.select"/>
                 </html:option>
                 <html:options  collection="Item.uom.vector" property="value" />
               </html:select>
            </logic:notEqual>
          </td>
          
          <td>
            <div id="UOMCD" class="UOMCDStyle">
                <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                    <html:text name="STORE_ADMIN_ITEM_FORM" styleId="product.uom" property="product.uom" size="4" maxlength="30"/>
                    <span class="reqind">*</span>
                </logic:equal>
                <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                    <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.uom"/>
                </logic:notEqual>
             </div>
          </td>
          
          <td></td>
          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
           </tr>
          </table>
        </td>
          <td><b>UOM and Pack<BR>vary by<BR>geography:</b></td>
        <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
               <html:select name="STORE_ADMIN_ITEM_FORM" property="product.packProblemSku">
                <html:option value="true">yes</html:option>
                <html:option value="false">no</html:option>
               </html:select>
           </logic:equal>
           <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
               <html:select name="STORE_ADMIN_ITEM_FORM" property="product.packProblemSku" disabled="true">
                <html:option value="true">yes</html:option>
                <html:option value="false">no</html:option>
               </html:select>
            </logic:notEqual>
        </td>
        <td><b>Pack:</b></td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text name="STORE_ADMIN_ITEM_FORM" property="product.pack" maxlength="30" size="4"/>
                <span class="reqind">*</span>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="product.pack"/>
            </logic:notEqual>
         </td>
        <% if(appUser.getUserStore().getStoreId() == 1) { %>
<td><b>Distributor Target<br>Cost Price
(MFCP):
</b></td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:text name="STORE_ADMIN_ITEM_FORM" property="costPrice" maxlength="10" size="8"/>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="costPrice"/>
            </logic:notEqual>
         </td>
         <% } else { %>
          <td colspan="2">&nbsp;</td>
         <% } %>
      </tr>
      <tr>
         <td><b>HAZMAT:</b></td>
         <td>
            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
               <html:select name="STORE_ADMIN_ITEM_FORM" property="hazmat">
                 <html:option value="true">yes</html:option>
                 <html:option value="false">no</html:option>
               </html:select>
            </logic:equal>
            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                <html:select name="STORE_ADMIN_ITEM_FORM" property="hazmat" disabled="true">
                 <html:option value="true">yes</html:option>
                 <html:option value="false">no</html:option>
               </html:select>
            </logic:notEqual>
         </td>
      </tr>
           <tr>
             <logic:present name="STORE_ADMIN_ITEM_FORM" property="selectCertCompanies.values">
                 <bean:size id="ccsize" name="STORE_ADMIN_ITEM_FORM" property="selectCertCompanies.values"/>
                 <logic:greaterThan name="ccsize" value="0">
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
                                            <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                                                <html:multibox name="STORE_ADMIN_ITEM_FORM" property="<%=selectedStr%>" value="true"/>
                                            </logic:equal>
                                            <logic:notEqual name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                                                <html:multibox name="STORE_ADMIN_ITEM_FORM" property="<%=selectedStr%>" disabled="true" value="true"/>
                                            </logic:notEqual>
                                             <bean:write name="arrele" property="shortDesc"/>
                                         </td>
                                     </tr>
                                 </logic:iterate>
                             </table>

                         </td>
                 </logic:greaterThan>
             </logic:present>
             <logic:present name="STORE_ADMIN_ITEM_FORM" property="dataFieldProperties">
                <td colspan="2">&nbsp;</td>
                <td colspan="4" valign=top>
                    <table>
                        <logic:iterate name="STORE_ADMIN_ITEM_FORM" property="dataFieldProperties" id="dataF"
                                       indexId="i">
                            <%String prop = "dataFieldProperty["+i+"].value";%>
                            <tr>
                                <td><b><bean:write name="dataF" property="nameValue"/></b></td>
                                <td><b><html:text name="STORE_ADMIN_ITEM_FORM" property="<%=prop%>"/></b></td>
                            </tr>
                        </logic:iterate>
                    </table>
                 </td>
              </logic:present>
           </tr>
      </table>
         </tr>

<!-- for ENTERPRISE store, just ignore the distributor info -->
<logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">

<bean:size id="mappedDistCount"  name="STORE_ADMIN_ITEM_FORM" property="product.mappedDistributors"/>
<logic:greaterThan name="mappedDistCount" value="0">


<table ID=945 bgcolor="white" cellpadding=0 cellspacing=0 border=1 width="100%">
<thead>
<tr>
  <th colspan="3"></th>
  <th><input type="text" name="setAllDistSku" size="4"/> <a ID=946 href="#" onClick="return f_setAllDistValue(document.forms['STORE_ADMIN_ITEM_FORM'], 'setAllDistSku', 'sku')">Set All</a></th>
  <th><input type="text" name="setAllDistUom" size="4"/> <a ID=947 href="#" onClick="return f_setAllDistValue(document.forms['STORE_ADMIN_ITEM_FORM'], 'setAllDistUom', 'uom');">Set All</a></th>
  <th><input type="text" name="setAllDistPack" size="4"/> <a ID=948 href="#" onClick="return f_setAllDistValue(document.forms['STORE_ADMIN_ITEM_FORM'], 'setAllDistPack', 'pack');">Set All</a></th>
  <% if (hasFullDistList) { %>
  <th>
    <a ID=949 href="#" onclick="return f_setCheckClearAll(document.forms['STORE_ADMIN_ITEM_FORM'], 'newMapping', true)">Check All</a><br />
    <a ID=950 href="#" onclick="return f_setCheckClearAll(document.forms['STORE_ADMIN_ITEM_FORM'], 'newMapping', false)">Clear</a>
  </th>
 <% } %>
</tr>
<tr>
<th>
  <a id="pgsort"></a>
  <b><a ID=951 href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 0, false);"
    title="-- Sort by Distributor Id"> Distributor Id</a></b>
</th>
<th>
  <b><a ID=952 href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 1, false);"
    title="-- Sort by Distributor ERP Num">Distributor ERP Num</a></b>
</th>
<th>
  <b><a ID=953 href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 2, false);"
    title="-- Sort by Distributor Name">Distributor Name</a></b>
</th>
<th>
  <b><a ID=954 href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 3, false);"
    title="-- Sort by Distributor Sku">Distributor Sku</a></b>
</th>
<th>
  <b><a ID=955 href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 4, false);"
    title="-- Sort by Distributor UOM">Distributor UOM</a></b>
</th>
<th>
  <b><a ID=956 href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 5, false);"
    title="-- Sort by Distributor Pack">Distributor Pack</a></b>
</th>
<% if (hasFullDistList) { %>
  <th><b>Add New Mapping</b></th>
 <% } %>
</tr>
</thead>

<tbody id="itemTblBdy">
<!--  List of Distributors  -->
<logic:iterate id="distributor" name="STORE_ADMIN_ITEM_FORM" property="product.mappedDistributors"
          type="com.cleanwise.service.api.value.BusEntityData" indexId="kkk">
<tr>
  <td><bean:write name="distributor" property="busEntityId"/></td>
  <td><bean:write name="distributor" property="erpNum"/></td>
            <% int distId = distributor.getBusEntityId();
               String distSku=((ProductData)product).getDistributorSku(distId);
               String link = "distitem.do?action=showItem&distSku=";
               link+=distSku;
               link+="&searchField=";
               link+=distId;
               String distUom = ((ProductData)product).getDistributorUom(distId);
               String distPack = ((ProductData)product).getDistributorPack(distId);
            %>
  <td><a ID=957 href="<%=link%>"><bean:write name="distributor" property="shortDesc"/></a></td>
  <td><input type="text" name="distributor[<%=distId%>].sku" value="<%=distSku%>" size="13"/></td>
  <td><input type="text" name="distributor[<%=distId%>].uom" value="<%=distUom%>" size="13"/></td>
  <td><input type="text" name="distributor[<%=distId%>].pack" value="<%=distPack%>" size="10"/></td>
  <% if (hasFullDistList) { %>
  <td>&nbsp;</td>
  <% } %>
</tr>
</logic:iterate>


<!--  Full List of Distributors (if the store is a distributor strore) -->
<logic:equal name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR%>">
<logic:iterate id="fulldist" name="STORE_ADMIN_ITEM_FORM" property="distItemsFullList"
          type="com.cleanwise.service.api.value.DistributorData" indexId="kkk">
<tr>
  <td><bean:write name="fulldist" property="busEntity.busEntityId"/></td>
  <td><bean:write name="fulldist" property="busEntity.erpNum"/></td>
            <% int distId = fulldist.getBusEntity().getBusEntityId();
               String distUom = ((ProductData)product).getUom();;
               String distPack = ((ProductData)product).getPack();
            %>
  <td><bean:write name="fulldist" property="busEntity.shortDesc"/></td>
  <td><input type="text" name="newMapping[<%=distId%>].sku" value="" size="13"/></td>
  <td><input type="text" name="newMapping[<%=distId%>].uom" value="<%=distUom%>" size="13"/></td>
  <td><input type="text" name="newMapping[<%=distId%>].pack" value="<%=distPack%>" size="10"/></td>
  <td align="center"><input type="checkbox" name="newMapping" value="<%=distId%>" /></td>
</tr>
</logic:iterate>
</logic:equal>

</tbody>
</table>

<html:submit property="action" value="Save Distributor Item Information"/>
<br/>

</logic:greaterThan>
</logic:notEqual>

<br/>

<tr>
    <td colspan="4" align="center">
<%
    String qrCode = "&nbsp;";
    if (theForm.getProduct() != null) {
        int id = theForm.getProduct().getProductId();
        if (id > 0) {
            qrCode = "<img src='../storeportal/item-edit.do?action=QRcode' border='0'/>";
        }
    }
%>
<logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">
<% if (theForm.getShowPricing())  { %>
<table ID=958 bgcolor="white" cellpadding=2 cellspacing=0 border=1 width="100%">
<tr>
  <th>&nbsp;</th>
  <th><input type="text" name="setAllCustPrice" size="4"/> <a ID=959 href="#" onClick="return f_setAllPriceValue(document.forms['STORE_ADMIN_ITEM_FORM'], 'setAllCustPrice', 'custPrice');">Set All</a></th>
  <% if (!theForm.getShowDistCostFl()) { %>
        <th><input type="text" name="setAllDistCost" size="4"/> <a ID=960 href="#" onClick="return f_setAllPriceValue(document.forms['STORE_ADMIN_ITEM_FORM'], 'setAllDistCost', 'distCost');">Set All</a></th>
  <% } %>
  <th>
    <a ID=961 href="#" onclick="return f_setCheckClearAll(document.forms['STORE_ADMIN_ITEM_FORM'], 'savePrice', true)">Check All</a><br>
    <a ID=962 href="#" onclick="return f_setCheckClearAll(document.forms['STORE_ADMIN_ITEM_FORM'], 'savePrice', false)">Clear</a><br>
  </th>
</tr>
<tr>
  <th><br><b>Account</b></th>
  <th><b>Customer Price</b></th>
  <% if (!theForm.getShowDistCostFl()) { %>
        <th><b>Distributor Cost</b></th> 
  <% } %>
  <th><b>Save Price</b></th>
</tr>

<logic:iterate id="acc" name="STORE_ADMIN_ITEM_FORM" property="accounts"
          type="com.cleanwise.service.api.value.BusEntityData" indexId="kkk">
<%
  int accountId = acc.getBusEntityId();
  int index = kkk.intValue();
  ContractItemPriceViewVector prices = theForm.getItemPriceList(index);
  boolean multiplePrices = (prices.size() > 1);
  boolean multipleAccount;
  if(multipleAccounts.length >= index){
     multipleAccount = multipleAccounts[index];
  }else{
     multipleAccount=false;
  }
%>
<tr>
  <td><bean:write name="acc" property="shortDesc"/></td>
  <%if (multipleAccount) { %>
  <td colspan="3"><b>Multiple Accounts for these contracts</b></td>
  <% } else if (multiplePrices) { %>
  <td colspan="3"><b>Multiple price</b></td>
  <% } else {
     if(prices.isEmpty()){
        %>
  <td>Not Set</td>
  <td>Not Set</td>
  <td>&nbsp;</td>
        <%
     }else{
        ContractItemPriceView price = (ContractItemPriceView)prices.get(0);
        %>
  <td><input type="text" name="custPrice[<%=accountId%>]" value="<%=price.getPrice() %>"/></td>
  <% if (!theForm.getShowDistCostFl()) { %>
  <td><input type="text" name="distCost[<%=accountId%>]" value="<%=price.getDistCost() %>"/></td>
  <%}%>
  <td align="center"><input type="checkbox" name="savePrice" value="<%=accountId%>"/></td>
        <%
     }
   %>


  <% } %>
</tr>

</logic:iterate>
</table>
    </td>
</tr>

<br />&nbsp;<br />
<tr bgcolor="#cccccc">
    <td colspan="4" align="center">
        <table border="0" cellpadding="2" cellspacing="1" width="100%">
            <tr>
                <td width="45%" align="left">
                    <table cellpadding="0" cellspacing="0" width="100%" align="left">
                        <tr>
                            <td><h3>Product QR Code</h3></td>
                        </tr>
                        <tr>
                            <td>
                                <table cellpadding="0" cellspacing="0" width="100%" align="left">
                                    <tr>
                                        <td width="25%"><b>Product Name:</b></td>
                                        <td width="75%"><bean:write name="STORE_ADMIN_ITEM_FORM" property="product.shortDesc"/></td>
                                    </tr>
                                    <tr>
                                        <td><b>SKU:</b></td>
                                        <td><bean:write name="STORE_ADMIN_ITEM_FORM" property="product.skuNum"/></td>
                                    </tr>
                                    <tr>
                                        <td><b>MFG SKU:</b></td>
                                        <td><bean:write name="STORE_ADMIN_ITEM_FORM" property="manufacturerSku"/></td>
                                    </tr>
                                    <tr>
                                        <td><b>Pack:</b></td>
                                        <td><bean:write name="STORE_ADMIN_ITEM_FORM" property="product.pack"/></td>
                                    </tr>
                                    <tr>
                                        <td><b>UOM:</b></td>
                                        <td><bean:write name="STORE_ADMIN_ITEM_FORM" property="uom"/></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td><b>Instructions</b></td></tr>
                        <tr><td>1.Print</td></tr>
                        <tr><td>2.Affix to Product</td></tr>
                        <tr><td>3.Scan<span class="reqind">*</span></td></tr>
                        <tr><td>4.Enter On-Hand Quantity</td></tr>
                        <tr><td>5.Submit</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td><span class="reqind">*</span>Hold device approximately 1 foot away from the QR code</td></tr>
                    </table>
                </td>
                <td width="25%" align="center">
                    <%=qrCode%>
                </td>
                <td width="30%" align="center" valign="bottom">
                    <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                        <html:submit property="action" value="Save Item"/>
                    </logic:equal>
                    <html:submit property="action" value="Save Pricing"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<% } else { %>
    <tr bgcolor="#cccccc">
        <td colspan="4">
            <table border="0" cellpadding="2" cellspacing="1" width="100%">
                <tr>
                    <td width="45%" align="left">
                        <table cellpadding="0" cellspacing="0" width="100%" align="left">
                            <tr>
                                <td><h3>Product QR Code</h3></td>
                            </tr>
                            <tr>
                                <td>
                                    <table cellpadding="0" cellspacing="0" width="100%" align="left">
                                        <tr>
                                            <td width="25%"><b>Product Name:</b></td>
                                            <td width="75%"><bean:write name="STORE_ADMIN_ITEM_FORM" property="product.shortDesc"/></td>
                                        </tr>
                                        <tr>
                                            <td><b>SKU:</b></td>
                                            <td><bean:write name="STORE_ADMIN_ITEM_FORM" property="product.skuNum"/></td>
                                        </tr>
                                        <tr>
                                            <td><b>MFG SKU:</b></td>
                                            <td><bean:write name="STORE_ADMIN_ITEM_FORM" property="manufacturerSku"/></td>
                                        </tr>
                                        <tr>
                                            <td><b>Pack:</b></td>
                                            <td><bean:write name="STORE_ADMIN_ITEM_FORM" property="product.pack"/></td>
                                        </tr>
                                        <tr>
                                            <td><b>UOM:</b></td>
                                            <td><bean:write name="STORE_ADMIN_ITEM_FORM" property="uom"/></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr><td><b>Instructions</b></td></tr>
                            <tr><td>1.Print</td></tr>
                            <tr><td>2.Affix to Product</td></tr>
                            <tr><td>3.Scan<span class="reqind">*</span></td></tr>
                            <tr><td>4.Enter On-Hand Quantity</td></tr>
                            <tr><td>5.Submit</td></tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr><td><span class="reqind">*</span>Hold device approximately 1 foot away from the QR code</td></tr>
                        </table>
                    </td>
                    <td width="25%" align="center">
                        <%=qrCode%>
                    </td>
                    <td width="30%" align="center" valign="bottom">
                        <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
                            <html:submit property="action" value="Save Item"/>
                        </logic:equal>
                        <html:submit property="action" value="Save Pricing"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
<% } %>

</logic:notEqual>

<html:hidden name="STORE_ADMIN_ITEM_FORM" property="showPricing"/>

<logic:equal name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">
<tr bgcolor="#cccccc">
<td colspan=5 align=center>
<html:submit property="action" value="Save Item"/>
<bean:define id="changesFl"   name="STORE_ADMIN_ITEM_FORM" property="changesOnItemEditPageFl"/>
  <%if(((Boolean)changesFl).booleanValue()){%>
    <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
        <html:submit property="action" value="Update Linked Items" styleClass='reqind' />
    </logic:equal>
 <%} else {%>
    <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
        <html:submit property="action" value="Update Linked Items"/>
    </logic:equal>
      <%}%>
</td></tr>
</logic:equal>

<tr>
<td colspan="5">
    <logic:equal name="STORE_ADMIN_ITEM_FORM" property="showLinkedStores" value="true">
        <table width="100%" border="0" cellpadding="2" cellspacing="1">
        <tr>
            <td class="customerltbkground" vAlign="top" align="middle" colspan="2">
                <span class="shopassetdetailtxt">
                    <b><app:storeMessage key="store.text.linkedStores"/></b>
                </span>
            </td>
        </tr>
        <tr>
            <td class="shopcharthead">
                <div class="fivemargin">
                    <app:storeMessage key="admin2.account.detail.label.storeId"/>
                </div>
            </td>
            <td class="shopcharthead">
                <div class="fivemargin">
                    <app:storeMessage key="admin2.account.detail.label.storeName"/>
                </div>
            </td>
        </tr>
        <logic:present name="STORE_ADMIN_ITEM_FORM" property="linkedStores">
        <logic:iterate id="storeInfo"
                       name="STORE_ADMIN_ITEM_FORM"
                       property="linkedStores"
                       type="com.cleanwise.service.api.value.BusEntityData"
                       indexId="i">
            <%
            String bgcolor = "";
            if (i % 2 == 0 ) {  
                bgcolor = "rowa";
            } else {
                bgcolor = "rowd";
            } 
            %>
            <tr class='<%=bgcolor%>'>
                <td width="10%">
                    <logic:present name="storeInfo" property="busEntityId">
                        <bean:write name="storeInfo" property="busEntityId"/>
                    </logic:present>
                </td>
                <td>
                    <logic:present name="storeInfo" property="shortDesc">
                        <bean:write name="storeInfo" property="shortDesc"/>
                    </logic:present>
                </td>
            </tr>
        </logic:iterate>
        </logic:present>
        </table>
    </logic:equal>
</td>
            
 <jsp:include page="storeUpdateLinkItem.jsp" flush="true"/>
  </td></tr>
        </table>

        <!-- Control buttons -->
        <logic:equal name="STORE_ADMIN_ITEM_FORM" property="isEditable" value="true">
            <html:submit property="action" value="Save Item"/>
        </logic:equal>
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
 <html:hidden  property="action" value="hiddenAction"/>
</html:form>
</table>
<p align="center">&nbsp;</p>
<p>&nbsp;</p>
</div>


<% if ("Y".equals(isMSIE)) { %>
<script for=document event="onclick()">
<!--
document.all.CalFrame.style.display="none";
//-->
</script>
<% }  %>







