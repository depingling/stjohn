<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.List" %>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
<!--
function f_st() {
  var f = (document.forms.length > 1) ? f = document.forms[1] : document.forms[0];
  if (!f.skuType[1].checked && !f.skuType[2].checked) {
    f.skuType[0].checked='true';
  }
}
function popManufLocate(form) {
  for (var i=0;i<form.elements.length;i++) {
    if (form.elements[i].name == 'action') {
      form.elements[i].value = "Locate Manufacturer";
    }
  }
  form.submit();
}

//-->
</script>


<style>
#UOMCD  { position: relative;}
#DISTUOMCD  { position: relative;}
</style>
<script language="JavaScript1.2">
<!--

function setReadOnly(element, selectElement) {
  if(selectElement.options[selectElement.selectedIndex].value=="OTHER") {
    return true;
  } else {
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

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js"
  CHARSET="ISO-8859-1"></SCRIPT>
<bean:define id="theForm" name="STORE_DIST_ITEM_MGR_FORM" type="com.cleanwise.view.forms.StoreItemMgrSearchForm"/>



<div class = "text">
<font color=red><html:errors/></font>

<table ID=717 cellspacing="0" border="0" width="769"  class="mainbody">

<%
Integer lDistId = new Integer("0");
com.cleanwise.view.forms.StoreDistMgrDetailForm distForm =
(com.cleanwise.view.forms.StoreDistMgrDetailForm)session.getAttribute("STORE_DIST_DETAIL_FORM");
if ( null != distForm ) {
  lDistId = new Integer(distForm.getId());
}
%>

<tr>
<td><b>Distributor&nbsp;Id:</b></td>
<td><%=lDistId%></td>
 <td><b>Name:</b></td>
<td>
<bean:write name="STORE_DIST_DETAIL_FORM" property="name"/>
</td>
</tr>
<tr><td colspan='4'>
    <jsp:include flush='true' page="locateStoreManufacturer.jsp">
      <jsp:param name="jspFormAction" 	value="/storeportal/distitem.do" />
      <jsp:param name="jspFormName" 	value="STORE_DIST_ITEM_MGR_FORM" />
      <jsp:param name="jspSubmitIdent" 	value="" />
      <jsp:param name="jspReturnFilterProperty" value="manufFilter"/>
      <jsp:param name="isSingleSelect" 	value="true" />
      <jsp:param name="jspReturnFormNumProperty" 	value="returnFormNum" />
    </jsp:include>
  </td></tr>
<html:form styleId="718" action="storeportal/distitem.do">
<html:hidden name="STORE_DIST_ITEM_MGR_FORM" property="distributorId" value="<%=lDistId.toString()%>"/>

<html:hidden name="STORE_DIST_ITEM_MGR_FORM" property="returnFormNum"/>
<html:hidden name="STORE_DIST_ITEM_MGR_FORM" property="feedField"/>
<html:hidden name="STORE_DIST_ITEM_MGR_FORM" property="fieldDesc"/>


  <tr><td><b>Short Description:</b></td>
      <td><html:text name="STORE_DIST_ITEM_MGR_FORM" property="shortDescTempl"/></td>
      <td><b>Item Size Description:</b></td>
      <td><html:text name="STORE_DIST_ITEM_MGR_FORM" property="sizeTempl"/></td>
  </tr>
  <tr><td><b>Long Description:</b></td>
  <td colspan="3"><html:text name="STORE_DIST_ITEM_MGR_FORM" property="longDescTempl" size="80"/>
  </td>
  </tr>

  <tr>
    <td><b>Manufacturer Id:</b></td>
    <td>
      <html:text name="STORE_DIST_ITEM_MGR_FORM" property="manuId" size="10"/>
      <html:button onclick="return popManufLocate(this.form);"
        property="action" value="Locate Manufacturer" styleClass='text' />
    </td>
    <td><b>Manufacturer Name:</b></td>
    <td>
        <html:text name="STORE_DIST_ITEM_MGR_FORM" property="manuName"
          size="30" readonly="true" styleClass="resultslocatename"/>
    </td>
   </tr>

  <tr><td><b>Sku:</b></td>
       <td colspan="2">
       <html:text name="STORE_DIST_ITEM_MGR_FORM" property="skuTempl"
         onfocus="f_st();"/>
       <html:radio name="STORE_DIST_ITEM_MGR_FORM" property="skuType"
         value="System"/> System
       <html:radio name="STORE_DIST_ITEM_MGR_FORM" property="skuType"
         value="Manufacturer"/> Manufacturer
       <html:radio name="STORE_DIST_ITEM_MGR_FORM" property="skuType"
         value="Distributor"/> Distributor
       </td>
       <td>
       </td>
  </tr>
  <tr class="results"> <td></td>
       <td colspan="3">
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
       <html:hidden name="STORE_DIST_ITEM_MGR_FORM" property="action" value="SearchItem"/>
       <html:submit property="action">
         <app:storeMessage  key="global.action.label.search"/>
       </html:submit>
    </td>
  </tr>

</html:form>

</table>

<div>
<logic:greaterThan name="STORE_DIST_ITEM_MGR_FORM" property="listSize" value="0">
Count: <bean:write name="STORE_DIST_ITEM_MGR_FORM" property="listSize"/>

<table ID=719 width="950">
<tr>
<th class="stpTH"><a ID=720 href="distitem.do?action=sort&sortField=id"><b>Id</b></a></th>
<th class="stpTH"><a ID=721 href="distitem.do?action=sort&sortField=sku"><b>Sku</b></a></th>
<th class="stpTH"><a ID=722 href="distitem.do?action=sort&sortField=name"><b>Name</b></a></th>
<th class="stpTH"><a ID=723 href="distitem.do?action=sort&sortField=size"><b>Size</b></a></th>
<th class="stpTH"><a ID=724 href="distitem.do?action=sort&sortField=pack"><b>Pack</b></a></th>
<th class="stpTH"><a ID=725 href="distitem.do?action=sort&sortField=uom"><b>UOM</b></a></th>
<th class="stpTH"><a ID=726 href="distitem.do?action=sort&sortField=manufacturer"><b>Mfg.</b></a></th>
<th class="stpTH"><a ID=727 href="distitem.do?action=sort&sortField=msku"><b>Mfg Sku</b></a></th>
<th class="stpTH"><b>Dist Sku</b></th>
<th class="stpTH"><b>Dist Pack</b></th>
<th class="stpTH"><b>Dist UOM</b></th>
<th class="stpTH"><b>Dist UOM conv</b></th>
<th class="stpTH"><b>Mfg1 Id</b></th>
<th class="stpTH"><b>Mfg1 Name</b></th>
<th class="stpTH"><b>Mfg1 Sku</b></th>
<th class="stpTH"><b>&nbsp;</b></th>
</tr>
<%
 int formNum = 0;
%>

   <logic:iterate id="distItemVw" name="STORE_DIST_ITEM_MGR_FORM" property="distItems"
    type="com.cleanwise.service.api.value.DistItemView"
    indexId="itemIdx">

<%

int indd = itemIdx.intValue();
String oldDistSku = distItemVw.getDistItemSku();
String oldDistPack = distItemVw.getDistItemPack();
String oldDistUom = distItemVw.getDistItemUom();
String oldDistUomConvMult;
if(distItemVw.getDistUomConvMultiplier() == null){
        oldDistUomConvMult = "1";
}else{
        oldDistUomConvMult = distItemVw.getDistUomConvMultiplier().toString();
}
int oldMfg1IdInt = distItemVw.getMfg1Id();
String oldMfg1Id = (oldMfg1IdInt==0)? "":""+oldMfg1IdInt;
String oldMfg1Name = distItemVw.getMfg1Name();
if(oldMfg1Name==null) oldMfg1Name = "";
String oldMfg1ItemSku = distItemVw.getMfg1ItemSku();
if(oldMfg1ItemSku==null) oldMfg1ItemSku = "";
%>


<%/* (true /*distItemVw.getDistId()>0) */{ %>

    <bean:define id="key"  name="distItemVw" property="itemId"/>
    <bean:define id="sku" name="distItemVw" property="sku"/>
    <bean:define id="name" name="distItemVw" property="name"/>
    <bean:define id="size" name="distItemVw" property="size"/>
    <bean:define id="pack" name="distItemVw" property="pack"/>
    <bean:define id="uom" name="distItemVw" property="uom"/>
    <bean:define id="manuName" name="distItemVw" property="mfgName"/>
    <bean:define id="manuSku" name="distItemVw" property="mfgItemSku"/>

<tr>
<% formNum++; %>
<html:form styleId="728" action="storeportal/distitem.do">
<input type="hidden" name="itemId" value=<bean:write name="key"/> >
<input type="hidden" name="distId" value="<%=lDistId.toString()%>" >
    <td class="stpTD"><bean:write name="key"/></td>
    <td class="stpTD"><bean:write name="sku"/></td>
    <td class="stpTD" width=200><span class="smalltext"><bean:write name="name"/></span></td>
    <td class="stpTD" bgcolor=white><bean:write name="size"/></td>
    <td class="stpTD"><bean:write name="pack"/></td>
    <td class="stpTD" bgcolor=white><bean:write name="uom"/></td>
    <td class="stpTD"><bean:write name="manuName"/></td>
    <td class="stpTD" bgcolor=white><bean:write name="manuSku"/></td>
<td class="stpTD"><html:text size="6" styleClass="smalltext" property='<%= "distItems[" + itemIdx + "].distItemSku"%>'   /></td>
<td class="stpTD"><html:text   size="3" styleClass="smalltext" property='<%= "distItems[" + itemIdx + "].distItemPack"%>'  /></td>
<td class="stpTD">
<html:select name="STORE_DIST_ITEM_MGR_FORM" property='<%="distItems[" + itemIdx + "].distItemUom"%>'>
  <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
  <logic:iterate id="uomCollection"  name="Item.dist.uom.vector" type="java.lang.String">
  <html:option  value="<%=uomCollection%>"/>
 </logic:iterate>
</html:select>
</td>

<td class="stpTD">
 <html:text size="4" styleClass="smalltext"
            property='<%="distItems[" + itemIdx + "].distUomConvMultiplier"%>'
            value="<%=oldDistUomConvMult%>" />
</td>

<td class="stpTD">
  <span class="smalltext">
  <html:text size="6"  styleClass="smalltext"
             property='<%="distItems[" + itemIdx + "].mfg1Id"%>'
                value="<%=oldMfg1Id%>" />
  <input onclick="return popManufLocate(this.form);" type="submit" name="action" class="smalltext" value="Loc">
  <input type="hidden" name="feedField" value='<%="distItems[" + itemIdx + "].mfg1Id"%>' />
  <input type="hidden" name="fieldDesc" value='<%="distItems[" + itemIdx + "].mfg1Name"%>'  />
  <input type="hidden" name="formNum" value="<%=formNum%>"/>
  </span>
</td>

<td class="stpTD">
  <cpan class="smalltext">
  <html:textarea  property='<%="distItems[" + itemIdx + "].mfg1Name"%>'  value="<%=oldMfg1Name%>" readonly="true" styleClass="smalltext" rows="3" cols="20"/>
  </cpan>
</td>
<td class="stpTD"><html:text size="12"  styleClass="smalltext" property='<%="distItems[" + itemIdx + "].mfg1ItemSku"%>' value="<%=oldMfg1ItemSku%>" /></td>
<td class="stpTD">
  <input type=submit name="action" class="smalltext" value="Update">
  <input type=submit name="action" class="smalltext" value="Clear">
</td>

<html:hidden  property="action" value="hiddenAction"/>
</html:form>
 </tr>
<% } %>

 </logic:iterate>
</table>
<br><br>
</logic:greaterThan>  <% /* end of results listing. */ %>



</div>



