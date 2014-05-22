<%@ page language="java" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.EstimatorMgrForm" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% EstimatorMgrForm theForm = (EstimatorMgrForm) session.getAttribute("ESTIMATOR_MGR_FORM"); %>


<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.ESTIMATOR_MGR_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='productSelector') {
     dml.elements[i].checked=val;
   }
 }
}

function popLocate() {
  var loc = "estimatorMgrProductAdd.do";
//alert(loc);
  locatewin = window.open(loc,"tickersearch", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

function popManufLocate(name,name1) {
  var loc = "manuflocate.do?feedField=" + name+"&feedDesc="+name1;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

function popDistLocate(name,name1) {
  var loc = "distlocate.do?feedField=" + name+"&feedDesc="+name1;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}
//-->
</script>

<html:html>
<head>
<title>Search Products</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals//styles.css">
</head>

<body bgcolor="#cccccc">
<div class = "text">
<html:form action="/adminportal/estimatorMgr.do" >

  <table border="0" width="750" class="results">
  <tr><td><b>Category:</b>
  <html:text name="ESTIMATOR_MGR_FORM" property="categoryFilter"/> </td>
  </tr>
  <tr>
  <td><b>Short Description:<b>
  <html:text name="ESTIMATOR_MGR_FORM" property="shortDescFilter"/></td>
  </tr>
  <tr>
  <td><b>Manufacturer Name:</b>
  <html:text name="ESTIMATOR_MGR_FORM" property="manuFilter" /> </td>
  </tr>
  <tr><td><b>Sku:</b><html:text name="ESTIMATOR_MGR_FORM" property="skuFilter"/>
       <html:radio name="ESTIMATOR_MGR_FORM" property="skuTypeFilter" value="System"/>
       System
       <html:radio name="ESTIMATOR_MGR_FORM" property="skuTypeFilter" value="Manufacturer"/>
       Manufacturer
       <html:radio name="ESTIMATOR_MGR_FORM" property="skuTypeFilter" value="Id"/>
       Item Id
       </td>
  </tr>
  <tr> <td>
     <html:hidden property="itemIdAdd" value='0' />
     <html:submit property="action" value="Search"/>
     <html:button property="action" value="Add Product" onclick="return popLocate();"/>
    </td>
  </tr>
</html:form>
</table>
</div>
<% 
  ProdUomPackJoinViewVector prodUomPackJVwV = theForm.getFilteredProducts(); 
  if(prodUomPackJVwV!=null) {
  int size = prodUomPackJVwV.size();
%>
<!-------------               ----------------------->
<div>
<table width="100%" class="results">
<html:form action="/adminportal/estimatorMgr.do" >
<tr align=center>
<td colspan='12' align='left'>Search result count: <%=size%></td>
</tr>



<tr align=center>
<td class="tableheader"><b>Id</b> </td>
<td class="tableheader"><b>Sku</b> </td>
<td class="tableheader"><b>Name</b> </td>
<td class="tableheader"><b>Size</b> </td>
<td class="tableheader"><b>Pack</b> </td>
<td class="tableheader"><b>UOM</b> </td>
<td class="tableheader"><b>Mfg.</b> </td>
<td class="tableheader"><b>Model<br>Size</b> </td>
<td class="tableheader"><b>Size<br>Code</b> </td>
<td class="tableheader"><b>Model<br>Pack</b> </td>
<td class="tableheader"><b>&nbsp;</b> </td>
<% 
  int ind = -1;
  String categoryPrev = "@@@@@@@@@";
  for (Iterator iter=prodUomPackJVwV.iterator(); iter.hasNext(); ) {
    ind++;
    ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) iter.next();
    OrderGuideItemDescData ogidD = pupJVw.getOrderGuideItem();
    ProductUomPackData pupD = pupJVw.getProductUomPack();
    int itemId = ogidD.getItemId();
    String itemIdS = ""+itemId;
    String category = ogidD.getCategoryDesc();
    String sku = ogidD.getCwSKU();
    String sizeDesc = ogidD.getSizeDesc();
    String pack = ogidD.getPackDesc();
    String name = ogidD.getShortDesc();
    String manuf = ogidD.getManufacturerCd();
    String uom = ogidD.getUomDesc();
    String unitSizeInp = "unitSizeInp["+ind+"]";     
    String unitCdInp = "unitCdInp["+ind+"]";     
    String packQtyInp = "packQtyInp["+ind+"]";     

    if(category==null) {
      category = "";
    } else {
      category = category.trim();
    }
    if(ind==0 || !category.equalsIgnoreCase(categoryPrev)) {
       categoryPrev = category;
    
%>
<tr>
 <td colspan='12'  bgcolor='#cccccc'><b>
<% if(category.length()==0) { %>
  No category products</td>
<% } else {%>
 <%=category%></td>
<% } %>
</b></td>
</tr>
<% } %>
<tr>
<td><%=itemIdS%></td>
<td><%=sku%></td>
<td><%=name%></td>
<td><%=sizeDesc%></td>
<td><%=pack%></td>
<td><%=uom%></td>
<td><%=manuf%></td>
<td><html:text name='ESTIMATOR_MGR_FORM' property='<%=unitSizeInp%>' size='7'/> </td>
<td>
  <html:select name='ESTIMATOR_MGR_FORM' property='<%=unitCdInp%>' >
  <html:option value='<%=""%>'>&nbsp;</html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.GAL%>'><%=RefCodeNames.UNIT_CD.GAL%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.OZ%>'><%=RefCodeNames.UNIT_CD.OZ%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.FEET%>'><%=RefCodeNames.UNIT_CD.FEET%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.UNIT%>'><%=RefCodeNames.UNIT_CD.UNIT%></html:option>
  <html:option value='<%=RefCodeNames.UNIT_CD.LB%>'><%=RefCodeNames.UNIT_CD.LB%></html:option>
  </html:select>
</td>  
<td><html:text name='ESTIMATOR_MGR_FORM' property='<%=packQtyInp%>' size='4'/> <td>
<td>
<html:multibox name='ESTIMATOR_MGR_FORM' property='productSelector' value='<%=itemIdS%>'/>
</td>                                                       
</td>
</tr>
<% } %>
  <tr>
  <td colspan='9'>
  <html:submit property="action" value="Save Products"/>
  <html:submit property="action" value="Assign To Actions" />
  <html:submit property="action" value="Remove Selected" />
  </td>
  <td colspan='3' align='right'>
<a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
  </td>
</tr>
</html:form>
</table>
</td>
</tr>
<!-------------               ----------------------->
<% } %>
</body>

</html:html>


