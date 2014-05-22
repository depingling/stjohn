<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="catalogId" name="CATALOG_DETAIL_FORM" property="detail.catalogId" />
<bean:define id="masterCatalogId" name="CATALOG_DETAIL_FORM" property="masterCatalogId" />
<bean:define id="Location" value="catalog" type="java.lang.String" toScope="session"/>

<%
com.cleanwise.view.forms.CatalogMgrDetailForm catForm =
  (com.cleanwise.view.forms.CatalogMgrDetailForm)
    session.getAttribute("CATALOG_DETAIL_FORM");
int thisCatalogId = catForm.getDetail().getCatalogId();
%>

<html:hidden name="CATALOG_DETAIL_FORM" property="catalogId"
  value="<%= String.valueOf(thisCatalogId) %>" />

<script language="JavaScript1.2">
<!--
function popCatalogLocate(name, pDesc) {
  var loc = "cataloglocate.do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

function popStoreLocate(name,name1) {
  var loc = "storelocate.do?feedField=" + name+"&feedDesc="+name1;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}


function popLocate(pLoc) {
var loc = pLoc;
locatewin = window.open(loc,"Detail", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();

return false;
}
//-->

</script>


<html:html>

<head>
<title>Catalog Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals//styles.css">
</head>

<body bgcolor="#cccccc">
<div>
<html:form action="/adminportal/catalogdetail.do" focus='elements["detail.shortDesc"]'>
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<logic:notEqual name="CATALOG_DETAIL_FORM" property="detail.catalogId" value="0">
  <jsp:include flush='true' page="ui/catalogToolbar.jsp"/>
</logic:notEqual>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<font color=red>
<html:errors/>
</font>
<!--
<font color=green>
<%
   org.apache.struts.action.ActionErrors errors =
     (org.apache.struts.action.ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
   if(errors!=null) {
   java.util.Iterator iter = errors.get();
   while(iter.hasNext()){
   org.apache.struts.action.ActionError error =
      (org.apache.struts.action.ActionError) iter.next();
   String errorMessage =error.getKey();
%>
<%=errorMessage%> <p>
<%
   }
   }
 %>
</font>
-->

<table border="0"cellpadding="2" cellspacing="1" width="769"  bgcolor="#cccccc">

  <tr>
                <td><b>Catalog ID:</b>
                </td>
                <td><bean:write name="catalogId" filter="true"/></td>
                <td> <b>Catalog Name:</b> </td>
                <td>
                  <html:text size="30" maxlength="30" name="CATALOG_DETAIL_FORM" property="detail.shortDesc" /><span class="reqind">*</span>
                  </td>
              </tr>
              <tr>
                <td> <b>Catalog Type:</b> </td>
                <td>
                    <logic:equal name="CATALOG_DETAIL_FORM" property="detail.catalogId" value="0">
					<html:select name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Catalog.type.vector" property="value" />
                     </html:select><span class="reqind">*</span>
                     </logic:equal>
                     <logic:notEqual name="CATALOG_DETAIL_FORM" property="detail.catalogId" value="0">
                       <bean:write name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd"/>
                     </logic:notEqual>
                </td>
                <td><b>Catalog Status: </b> </td>
                <td>
                  <html:select name="CATALOG_DETAIL_FORM" property="detail.catalogStatusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Catalog.status.vector" property="value" />
                  </html:select><span class="reqind">*</span>
                </td>
              </tr>
              <logic:equal name="CATALOG_DETAIL_FORM" property="detail.catalogId" value="0">
              <tr>
                <td><b>Store Id: </b> </td>
                <td>
                    <html:text name="CATALOG_DETAIL_FORM" property="storeId" size="10"/>
                  <span class="reqind">*</span> <html:button onclick="return popStoreLocate('storeId','storeName');" value="Locate Store" property="action" />
                </td>
                <td><b>Store Name: </b> </td>
                <td>
                    <html:text name="CATALOG_DETAIL_FORM" property="storeName" size="30" readonly="true" styleClass="mainbodylocatename"/>
                </td>
              </tr>
              <tr>
                <td><b>Parent Catalog Id: </b></td>
                <td><html:text name="CATALOG_DETAIL_FORM" property="foundCatalogId" size="8"/>
                <html:button onclick="return popCatalogLocate('foundCatalogId', 'foundCatalogName');" value="Locate Catalog" property="action" />
                </td>
                <td><b>Parent Catalog Name: </b> </td>
                <td>
                    <html:text name="CATALOG_DETAIL_FORM" property="foundCatalogName" size="30" readonly="true" styleClass="mainbodylocatename"/>
                </td>
              </tr>
              </logic:equal>
              <logic:notEqual name="CATALOG_DETAIL_FORM" property="detail.catalogId" value="0">
               <tr>
                <td> <b>Store Id: </b> </td>
                <td> <bean:write name="CATALOG_DETAIL_FORM" property="storeId" filter="true"/>
                </td>
                <td> <b>Store Name: </b> </td>
                <td> <bean:write name="CATALOG_DETAIL_FORM" property="storeName" filter="true"/>
                </td>
                </tr>
               <tr>
                <td> <b>Master Catalog Id: </b> </td>
                <td> <bean:write name="CATALOG_DETAIL_FORM" property="masterCatalogId" filter="true"/>
                </td>
                <td> <b>&nbsp;</b> </td>
                <td>&nbsp;
                </td>
                </tr>
<tr>
<td><b>Shipping Message:</b> </td>
<td colspan=3>
<html:textarea name="CATALOG_DETAIL_FORM" cols="60"
  property="detail.shippingMessage"/>
</td>
</tr> 
             </logic:notEqual>

            <logic:equal name="CATALOG_DETAIL_FORM" property="detail.catalogId" value="0">
                <tr><td colspan="4"> <html:submit property="action" value="Create Catalog"/>
                </td>
                </tr>
            </logic:equal>
            <logic:notEqual name="CATALOG_DETAIL_FORM" property="detail.catalogId" value="0">
                  <tr>
                    <td colspan="4" align="center">
                      <html:submit property="action" value="Save Catalog"/>
                      <logic:equal name="CATALOG_DETAIL_FORM" property="mayDelete" value="true">
                        <html:submit property="action" value="Delete Catalog"/>
                      </logic:equal>
                      <html:submit property="action" value="Validate Catalog"/>                      
                    </td>
                  </tr>
            </logic:notEqual>
</table>

<logic:equal name="validated" value="true">
<table>
  <tr>
    <td class="subheaders">There are <bean:write name="CATALOG_DETAIL_FORM" property="numItems" filter="true"/>  items in this catalog
    </td>
  </tr>
  <tr>
    <td class="subheaders">
      <logic:greaterThan name="CATALOG_DETAIL_FORM" property="activeContracts" value="1">
        <span class="trainingsubhead">
          There are <bean:write name="CATALOG_DETAIL_FORM" property="activeContracts" filter="true"/>  active contracts for this catalog
        </span>
      </logic:greaterThan>
      <logic:lessThan name="CATALOG_DETAIL_FORM" property="activeContracts" value="1">
        <span class="trainingsubhead">
          There are <bean:write name="CATALOG_DETAIL_FORM" property="activeContracts" filter="true"/>  active contracts for this catalog
        </span>
      </logic:lessThan>      
      <logic:equal name="CATALOG_DETAIL_FORM" property="activeContracts" value="1">        
          There are <bean:write name="CATALOG_DETAIL_FORM" property="activeContracts" filter="true"/> active contracts for this catalog
       
      </logic:equal>
    </td>
  </tr>
  <logic:equal name="CATALOG_DETAIL_FORM" property="activeContracts" value="1">
  <tr>
    <td class="subheaders">There are <bean:write name="CATALOG_DETAIL_FORM" property="numDistributors" filter="true"/>  distributors for this catalog
    </td>
  </tr> 
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td class="subheaders">
      Items Without A Distributor: <bean:write name="CATALOG_DETAIL_FORM" property="badDistSize"/>
      <logic:greaterThan name="CATALOG_DETAIL_FORM" property="badDistSize" value="0">

      <pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
      <pg:param name="pg"/>
      <pg:param name="q"/>

      <table width="100%" class="results">
        <tr align=center>
          <td width="5%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=id"><b>Id</b></a> </td>
          <td width="8%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=sku"><b>Sku</b></a> </td>
          <td width="30%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=name"><b>Name</b></a> </td>
          <td width="8%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=size"><b>Size</b></a> </td>
          <td width="5%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=pack"><b>Pack</b></a> </td>
          <td width="5%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=uom"><b>UOM</b> </td>
          <td width="18%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=manufacturer"><b>Mfg.</b></a> </td>
          <td width="8%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=msku"><b>Mfg.&nbsp;Sku</b></a> </td>
          <logic:notEqual name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd" value="SYSTEM">
            <td bgcolor="#cccccc"><b>Category</b> </td>
          </logic:notEqual>
        </tr>
        <logic:iterate id="product" name="CATALOG_DETAIL_FORM" property="badDist" 
    indexId="prodIdx">
        <pg:item>

        <bean:define id="key"  name="product" property="productId"/>
        <bean:define id="sku" name="product" property="skuNum"/>
        <bean:define id="name" name="product" property="shortDesc"/>
        <bean:define id="size" name="product" property="size"/>
        <bean:define id="pack" name="product" property="pack"/>
        <bean:define id="uom" name="product" property="uom"/>
        <bean:define id="manuName" name="product" property="manufacturerName"/>
        <bean:define id="manuSku" name="product" property="manufacturerSku"/>

        <% String linkHref = new String("itemmaster.do?action=edit&retaction=finditem&itemId=" + key);%>
        <logic:notEqual name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd" value="SYSTEM">
          <% linkHref = new String("itemcatalog.do?action=edit&retaction=finditem&itemId=" + key);%>
        </logic:notEqual>

        <% if (( prodIdx.intValue() % 2 ) == 0 ) { %> <tr class="rowa">
        <% } else { %> <tr class="rowb"> <% } %>

          <td><bean:write name="key"/></td>
          <td><bean:write name="sku"/></td>
          <td><a href="<%=linkHref%>" onclick="return popLocate('<%=linkHref%>');"  property="action"><bean:write name="name"/></a></td>
          <td><bean:write name="size"/></td>
          <td><bean:write name="pack"/></td>
          <td><bean:write name="uom"/></td>
          <td><bean:write name="manuName"/></td>
          <td><bean:write name="manuSku"/></td>
          <td>

            <% /* Start - List the categories for this product. */ %>

            <table>

            <bean:size id="categs_len" name="product" property="catalogCategories"/>

            <logic:greaterThan name="categs_len" value="0">

            <logic:iterate id="categs" name="product" property="catalogCategories">

              <tr bgcolor="#cccccc">

              <bean:define id="I_prodId" type="java.lang.Integer" name="product" property="productId"/>
              <bean:define id="I_categId" type="java.lang.Integer" name="categs" property="catalogCategoryId"/>

              <%
              String rmurl = "catalogdetail.do?action=rm_item_category&amp;" +
                             "itemId=" + I_prodId.intValue() + "&amp;" + 
                             "catalogId=" + thisCatalogId + "&amp;" + 
                             "categoryId=" + I_categId.intValue();
              %>

                <td bgcolor="#cccccc" >
                  <a style="tbar" href="<%=rmurl%>">
                    <img border="0" alt="[Remove from this category.]" src="/<%=storeDir%>/en/images/button_x.gif"
                  </a>
                </td>
                <td bgcolor="#cccccc" >
                  <bean:write name="categs" property="catalogCategoryShortDesc"/>
                </td>
              </tr>
            </logic:iterate>

            </logic:greaterThan>

            </table>
          </td>        
        </tr>
        </pg:item>
        </logic:iterate>
      </table>
    </logic:greaterThan>
    </td>
  </tr>  
  <tr>
    <td class="subheaders">
      Items With Missing Data: <bean:write name="CATALOG_DETAIL_FORM" property="missingDataSize"/>
      <logic:greaterThan name="CATALOG_DETAIL_FORM" property="missingDataSize" value="0">

      <pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
      <pg:param name="pg"/>
      <pg:param name="q"/>

      <table width="100%" class="results">
        <tr align=center>
          <td width="5%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=id"><b>Id</b></a> </td>
          <td width="8%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=sku"><b>Sku</b></a> </td>
          <td width="30%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=name"><b>Name</b></a> </td>
          <td width="8%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=size"><b>Size</b></a> </td>
          <td width="5%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=pack"><b>Pack</b></a> </td>
          <td width="5%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=uom"><b>UOM</b> </td>
          <td width="18%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=manufacturer"><b>Mfg.</b></a> </td>
          <td width="8%"><a class="tableheader" href="catalogdetail.do?action=sort&sortField=msku"><b>Mfg.&nbsp;Sku</b></a> </td>
          <logic:notEqual name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd" value="SYSTEM">
            <td bgcolor="#cccccc"><b>Category</b> </td>
          </logic:notEqual>
        </tr>
        <logic:iterate id="missing" name="CATALOG_DETAIL_FORM" property="missingData" 
    indexId="prodIdx">
        <pg:item>

        <bean:define id="key"  name="missing" property="productId"/>
        <bean:define id="sku" name="missing" property="skuNum"/>
        <bean:define id="name" name="missing" property="shortDesc"/>
        <bean:define id="size" name="missing" property="size"/>
        <bean:define id="pack" name="missing" property="pack"/>
        <bean:define id="uom" name="missing" property="uom"/>
        <bean:define id="manuName" name="missing" property="manufacturerName"/>
        <bean:define id="manuSku" name="missing" property="manufacturerSku"/>

        <% 
          CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

          String type = appUser.getUser().getUserTypeCd();  
          String linkHref = null;
          if(type.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR)){
            linkHref = new String("itemmaster.do?action=edit&retaction=finditem&itemId=" + key);}
          else{
            linkHref = new String("itemcatalog.do?action=edit&retaction=finditem&itemId=" + key);
}

        %>       

        <% if (( prodIdx.intValue() % 2 ) == 0 ) { %> <tr class="rowa">
        <% } else { %> <tr class="rowb"> <% } %>

          <td><bean:write name="key"/></td>
          <td><bean:write name="sku"/></td>
          <td><a href="<%=linkHref%>" onclick="return popLocate('<%=linkHref%>');"  property="action"><bean:write name="name"/></a></td>
          <td><bean:write name="size"/></td>
          <td><bean:write name="pack"/></td>
          <td><bean:write name="uom"/></td>
          <td><bean:write name="manuName"/></td>
          <td><bean:write name="manuSku"/></td>
          <td>

            <% /* Start - List the categories for this product. */ %>

            <table bgcolor="#cccccc">

            <bean:size id="categs_len" name="missing" property="catalogCategories"/>

            <logic:greaterThan name="categs_len" value="0">

            <logic:iterate id="categs" name="missing" property="catalogCategories">

              <tr bgcolor="#cccccc">

              <bean:define id="I_prodId" type="java.lang.Integer" name="missing" property="productId"/>
              <bean:define id="I_categId" type="java.lang.Integer" name="categs" property="catalogCategoryId"/>

              <%
              String rmurl = "catalogdetail.do?action=rm_item_category&amp;" +
                             "itemId=" + I_prodId.intValue() + "&amp;" + 
                             "catalogId=" + thisCatalogId + "&amp;" + 
                             "categoryId=" + I_categId.intValue();
              %>

                <td bgcolor="#cccccc" ><a style="tbar" href="<%=rmurl%>" >
                <img border="0" alt="[Remove from this category.]" src="/<%=storeDir%>/en/images/button_x.gif"</a>
                </td>
                <td bgcolor="#cccccc" >
                  <bean:write name="categs" property="catalogCategoryShortDesc"/>
                </td>
              </tr>
            </logic:iterate>

            </logic:greaterThan>

            </table>
          </td>              
        </tr>
        </pg:item>
        </logic:iterate>
      </table>
      </logic:greaterThan>
    </td>
  </tr>
  </logic:equal>
  <tr align=center><td colspan="11">&nbsp;</td></tr>
  </table>
</logic:equal>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</html:form>
</div>
</body>
</html:html>
