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

<% 
  String storeDir=ClwCustomizer.getStoreDir(); 
  boolean clwSwitch=ClwCustomizer.getClwSwitch(); 
  String portal = request.getParameter("portal");
  boolean adminPortalFl = ("adminportal".equalsIgnoreCase(portal))?true:false;
  String actionStr = (adminPortalFl)?"/adminportal/distItemMgr.do":"/console/crcdistItem.do";
  boolean readOnlyFl = (adminPortalFl)?false:true;
  String pg = request.getRequestURI();
  if ( pg.indexOf("adminportal") >= 0 ) {
    readOnlyFl = false;
  }  
%>  

<script language="JavaScript1.2">
<!--
function f_st() {

  if (  !document.forms[0].skuType[1].checked &&
        !document.forms[0].skuType[2].checked ) {
        document.forms[0].skuType[0].checked='true';
        }
}

function popManufLocate(name,name1,formNum) {
  var loc = "manuflocate.do?feedField=" + name+"&feedDesc="+name1+"&returnFormNum="+formNum;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
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

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js"
  CHARSET="ISO-8859-1"></SCRIPT>
<bean:define id="theForm" name="DIST_ITEM_MGR_FORM" type="com.cleanwise.view.forms.ItemMgrSearchForm"/>



<div class = "text">
<font color=red><html:errors/></font>

<table cellspacing="0" border="0" width="769"  class="mainbody">

<%
Integer lDistId = new Integer("0");
com.cleanwise.view.forms.DistMgrDetailForm distForm =
  (com.cleanwise.view.forms.DistMgrDetailForm)
  session.getAttribute("DIST_DETAIL_FORM");
if ( null != distForm ) {
  lDistId = new Integer(distForm.getId());
}
%>


<tr>
<td><b>Distributor&nbsp;Id:</b></td>
<td><%=lDistId%></td>
 <td><b>Name:</b></td>
<td>
<bean:write name="DIST_DETAIL_FORM" property="name"/>
</td>
<html:form action="<%=actionStr%>" focus="skuTempl" >
<html:hidden name="DIST_ITEM_MGR_FORM" property="distributorId" value="<%=lDistId.toString()%>"/>

  <tr><td><b>Short Description:<b></td>
      <td><html:text name="DIST_ITEM_MGR_FORM" property="shortDescTempl"/></td>
      <td><b>Item Size Description:<b></td>
      <td><html:text name="DIST_ITEM_MGR_FORM" property="sizeTempl"/></td>
  </tr>
  <tr><td><b>Long Description:</b></td>
  <td colspan="3"><html:text name="DIST_ITEM_MGR_FORM" property="longDescTempl" size="80"/>
  </td>
  </tr>
  <tr><td><b>Manufacturer Id:<b></td>
      <td><html:text name="DIST_ITEM_MGR_FORM" property="manuId" size="10"/>
      <html:button onclick="return popManufLocate('manuId','manuName');" value="Locate Manufacturer" property="action"/>
      </td>
     <td><b>Manufacturer Name:<b></td>
     <td>
        <html:text name="DIST_ITEM_MGR_FORM" property="manuName"
          size="30" readonly="true" styleClass="resultslocatename"/>
     </td>
   </tr>

  <tr><td><b>Sku:</b></td>
       <td colspan="2">
       <html:text name="DIST_ITEM_MGR_FORM" property="skuTempl"
         onfocus="f_st();"/>
       <html:radio name="DIST_ITEM_MGR_FORM" property="skuType"
         value="System"/> System
       <html:radio name="DIST_ITEM_MGR_FORM" property="skuType"
         value="Manufacturer"/> Manufacturer
       <html:radio name="DIST_ITEM_MGR_FORM" property="skuType"
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
       <html:submit property="action" value="Search"/>

    </td>
  </tr>

</html:form>

</table>

<div>
Search result count: <bean:write name="DIST_ITEM_MGR_FORM" property="listSize"/>
<logic:greaterThan name="DIST_ITEM_MGR_FORM" property="listSize" value="0">

<table class="results" width="950">
<tr align=center>
<td><a class="tableheader" href="<%=actionStr%>?action=sort&sortField=id"><b>Id</b></a> </td>
<td><a class="tableheader" href="<%=actionStr%>?action=sort&sortField=sku"><b>Sku</b></a> </td>
<td><a class="tableheader" href="<%=actionStr%>?action=sort&sortField=name"><b>Name</b></a> </td>
<td><a class="tableheader" href="<%=actionStr%>?action=sort&sortField=size"><b>Size</b></a> </td>
<td><a class="tableheader" href="<%=actionStr%>?action=sort&sortField=pack"><b>Pack</b></a> </td>
<td><a class="tableheader" href="<%=actionStr%>?action=sort&sortField=uom"><b>UOM</b></a> </td>
<td><a class="tableheader" href="<%=actionStr%>?action=sort&sortField=manufacturer"><b>Mfg.</b></a> </td>
<td><a class="tableheader" href="<%=actionStr%>?action=sort&sortField=msku"><b>Mfg Sku</b></a> </td>
<td class="tableheader"><b>Dist Sku</b> </td>
<td class="tableheader"><b>Dist Pack</b> </td>
<td class="tableheader"><b>Dist UOM</b> </td>
<td class="tableheader"><b>Dist UOM conv</b> </td>
<td class="tableheader"><b>Mfg1 Id</b> </td>
<td class="tableheader"><b>Mfg1 Name</b> </td>
<td class="tableheader"><b>Mfg1 Sku</b> </td>

</tr>
<%
 int formNum = 0;
%>

   <logic:iterate id="distItemVw" name="DIST_ITEM_MGR_FORM" property="distItems"
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

<%
if ( itemIdx.intValue()%2==0 )
{ %>
    <tr class="rowa">
<% } else { %>
    <tr class="rowb">
<% } %>
<% formNum++; %>
<html:form action="/adminportal/distItemMgr.do" >
<input type="hidden" name="itemId" value=<bean:write name="key"/> >
<input type="hidden" name="distId" value="<%=lDistId.toString()%>" >
    <td><bean:write name="key"/></td>
    <td bgcolor=white><bean:write name="sku"/></td>
    <td width=200><bean:write name="name"/></td>
    <td bgcolor=white><bean:write name="size"/></td>
    <td><bean:write name="pack"/></td>
    <td bgcolor=white><bean:write name="uom"/></td>
    <td><bean:write name="manuName"/></td>
    <td bgcolor=white><bean:write name="manuSku"/></td>

<td>
<input type="text" size=12  class="smalltext" name=newDistSku value="<%=oldDistSku%>" <%=(readOnlyFl)?"readonly":""%> >
</td>
<td>
<input type="text" size=3  class="smalltext" name=newDistPack value="<%=oldDistPack%>" <%=(readOnlyFl)?"readonly":""%> >
</td>
<td>
<% if(!readOnlyFl) { %>
<SELECT  class="smalltext" NAME="newDistUom">
<% if ( oldDistUom.length() == 0 ) {%>
<option value=""><app:storeMessage  key="admin.select"/></option>
<% } %>
<!-- old uom <%=oldDistUom%> -->
<%
List uomVector = (List) session.getAttribute("Item.dist.uom.vector");

if ( uomVector != null ) {
  for ( int idx = 0; idx < uomVector.size(); idx++ ) {
    String uomentry = (String)uomVector.get(idx);
    if ( oldDistUom.equals(uomentry) ) {
%>
    <option selected><%=uomentry%></option>
<%
        }
    else {
%>
    <option><%=uomentry%></option>
<%
    }
  }
}
%>
</SELECT>
<% } else { %>
<%=oldDistUom%>
<% } %>



</td>

<td>
<input type="text" size="4" class="smalltext" name="newDistUomConvMult" 
 value="<%=oldDistUomConvMult%>" 
 <%=(readOnlyFl)?"readonly":""%> >
</td>

<td class="smalltext">
<input type="text" size="6"  class="smalltext" 
  name="mfg1Id" value="<%=oldMfg1Id%>" 
 <%=(readOnlyFl)?"readonly":""%> 
>

<% if(!readOnlyFl) { %>   
<input onclick="return popManufLocate('mfg1Id','mfg1Name',<%=formNum%>);"
                                   type="submit" name="action" class="smalltext" value="Loc">
<% } %>                                   
</td>

<td class="smalltext">
<%  if ( itemIdx.intValue()%2==0) { %>
    <textarea name="mfg1Name"  readonly="true" class="boxa"><%=oldMfg1Name%></textarea>
<%  }else{ %>
    <textarea name="mfg1Name"  readonly="true" class="boxb"><%=oldMfg1Name%></textarea>
<% } %>

</td>
<td>
<input type="text" size="12"  class="smalltext" name="mfg1ItemSku"
   value="<%=oldMfg1ItemSku%>" 
 <%=(readOnlyFl)?"readonly":""%> 
>
</td>
<td>
<% if(!readOnlyFl) { %>
<input type=submit name="action" class="smalltext" value="Update">
<input type=submit name="action" class="smalltext" value="Clear">
<% } else { %>
&nbsp;
<% } %>
</td>
</html:form>
 </tr>
<% } %>

 </logic:iterate>

</table>
<br><br>

</logic:greaterThan>  <% /* end of results listing. */ %>

</div>



