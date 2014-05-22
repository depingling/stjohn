<%@ page contentType="text/html"%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="theForm" name="STORE_ADMIN_ITEM_FORM" type="com.cleanwise.view.forms.StoreItemMgrForm"/>

<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.STORE_ADMIN_ITEM_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectorBox') {
     dml.elements[i].checked=val;
   }
 }
}

function popLocate(name) {
  var loc = "catalogitemadd.do?feedField=" + name;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
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

function setObjVisibility(id, value) {
    var panel = document.getElementById(id);
    if (panel) {
        panel.style.visibility = value;
    }
}

function showSearchNumType(val) {
    
    if(val=='hidden') {
        setObjVisibility('panel1', 'hidden');
    } else {
        setObjVisibility('panel1', 'visible');
    }
}

function actionSubmit(formNum, action) {
    var actions;
    actions=document.forms[formNum]["action"];
    //alert(actions.length);
    for (ii=actions.length-1; ii>=0; ii--) {
        if (actions[ii].value=='hiddenAction') {
            actions[ii].value=action;
            document.forms[formNum].submit();
        break;
        }
    }
return false;
}

//-->
</script>

<% String catalogIdS = ""+theForm.getCatalogId(); 
   String visibility = "visible";
%>
<logic:equal name="STORE_ADMIN_ITEM_FORM" property="skuType" value="<%=SearchCriteria.STORE_SKU_NUMBER%>">
    <%visibility = "hidden";%>
</logic:equal>

<html:hidden name="STORE_ADMIN_ITEM_FORM" property="catalogId" value="catalogIdS"/>

<div class="text">

<table ID="992" width="<%=Constants.TABLEWIDTH%>" cellspacing="0" border="0" class="mainbody">
    <html:form styleId="993"
               action="storeportal/searchStagedMasterItems.do"
               focus="categoryTempl">

<tr>
    <td colspan="5" class="largeheader">
        <app:storeMessage key="global.action.label.search"/>&nbsp;<app:storeMessage key="userAssets.text.assetStagedMasterItemToMatchWith"/>
    </td>
    <td>
        <html:hidden name="STORE_ADMIN_ITEM_FORM" property="whereToSearch" value="this"/>
    </td>
</tr>
<logic:present name="STORE_ADMIN_ITEM_FORM" property="stagedItemMatch">
<tr>
    <td colspan="5">
        <table ID="9954" width="100%">
        <tr align="left">
            <td>
                <div class="fivemargin">
                    <b><app:storeMessage key="userItems.text.itemMasterItemID"/></b>
                </div>
            </td>
            <td>
                <div class="fivemargin">
                    <b><app:storeMessage key="userItems.text.itemMasterItemName"/></b>
                </div>
            </td>
            <td>
                <div class="fivemargin">
                    <b><app:storeMessage key="userAssets.text.assetCategory"/></b>
                </div>
            </td>
            <td>
                <div class="fivemargin">
                    <b><app:storeMessage key="userAssets.shop.text.param.manufacturer"/></b>
                </div>
            </td>
            <td>
                <div class="fivemargin">
                    <b><app:storeMessage key="userAssets.shop.text.param.manufacturerSku"/></b>
                </div>
            </td>
        </tr>
        <tr align="left" class="rowb">
            <td>
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="stagedItemMatch.itemId"/>
            </td>
            <td>
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="stagedItemMatch.name"/>
            </td>
            <td>
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="stagedItemMatch.category"/>
            </td>
            <td>
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="stagedItemMatch.manufName"/>
            </td>
            <td>
                <bean:write name="STORE_ADMIN_ITEM_FORM" property="stagedItemMatch.manufSku"/>
            </td>
        </tr>
        </table>
    </td>
</tr>
<tr>
    <td colspan="4" align="right">&nbsp;</td>
    <td width="10%" align="left">
        <logic:lessEqual name="STORE_ADMIN_ITEM_FORM" property="stagedItemMatch.matchedItemId" value="0">
            <html:button property="action" onclick="actionSubmit(0,'CreateNotMatchedItem');">
                <app:storeMessage key="global.action.label.create"/>
            </html:button>
        </logic:lessEqual>
        <logic:greaterThan name="STORE_ADMIN_ITEM_FORM" property="stagedItemMatch.matchedItemId" value="0">
            <html:button property="action" onclick="actionSubmit(0,'UnmatchMatchedItem');">
                <app:storeMessage key="userAssets.text.unMatch"/>
            </html:button>
        </logic:greaterThan>
    </td>
</tr>
</logic:present>
<tr>
    <td colspan="5" style="font-size: 3pt">&nbsp;</td>
</tr>

<tr>
    <td width="20%" align="right">
        <b><app:storeMessage key="userAssets.text.assetCategory"/>:</b>
    </td>
    <td width="2%"></td>
    <td colspan="3">
        <html:text name="STORE_ADMIN_ITEM_FORM" property="categoryTempl"/>
    </td>
</tr>

<tr>
    <td align="right">
        <b><app:storeMessage key="locateItem.label.shortDesc"/>:</b>
    </td>
    <td></td>
    <td colspan="3">
        <html:text name="STORE_ADMIN_ITEM_FORM" property="shortDescTempl"/>
    </td>
</tr>

<tr>
    <td align="right">
        <b><app:storeMessage key="locateItem.label.longDesc"/>:</b>
    </td>
    <td></td>
    <td colspan="3">
        <html:text name="STORE_ADMIN_ITEM_FORM" property="longDescTempl" size="80"/>
    </td>
</tr>
<tr>
    <td align="right">
        <b><app:storeMessage key="locateItem.label.manuf"/>:</b>
    </td>
    <td></td>
    <td colspan="3">
        <html:text name="STORE_ADMIN_ITEM_FORM" property="manuNameTempl" size="20"/>
    </td>
</tr>

<logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">  
<tr>
    <td align="right">
        <b><app:storeMessage key="locateItem.label.distr"/>:</b>
    </td>
    <td></td>
    <td colspan="3">
        <html:text name="STORE_ADMIN_ITEM_FORM" property="distNameTempl" size="20"/>
    </td>
</tr>
</logic:notEqual>  

<tr>
    <td align="right">
        <b><app:storeMessage key="locateItem.label.sku"/>:</b>
    </td>
    <td></td>
    <td align="left">
        <html:text name="STORE_ADMIN_ITEM_FORM" property="skuTempl"/>
    </td>
    <td>
        <html:radio name="STORE_ADMIN_ITEM_FORM"
                    property="skuType"
                    value="<%=SearchCriteria.STORE_SKU_NUMBER%>"
                    onclick="return showSearchNumType('hidden');"/>
        <app:storeMessage key="locateItem.radio.text.store"/>
        <html:radio name="STORE_ADMIN_ITEM_FORM"
                    property="skuType"
                    value="<%=SearchCriteria.MANUFACTURER_SKU_NUMBER%>"
                    onclick="return showSearchNumType('visible');"/>
        <app:storeMessage key="locateItem.label.manuf"/>
        <logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">       
            <html:radio name="STORE_ADMIN_ITEM_FORM"
                        property="skuType"
                        value="<%=SearchCriteria.DISTRIBUTOR_SKU_NUMBER%>"
                        onclick="return showSearchNumType('visible');"/>
            <app:storeMessage key="locateItem.label.distr"/>
        </logic:notEqual> 
        <span id="panel1" style="visibility:<%=visibility%>;">     
            <html:radio name="STORE_ADMIN_ITEM_FORM"
                        property="searchNumType"
                        value="nameBegins"/>
            (<app:storeMessage key="admin2.search.criteria.label.startsWith"/>)
            <html:radio name="STORE_ADMIN_ITEM_FORM"
                        property="searchNumType"
                        value="nameContains"/>
            (<app:storeMessage key="admin2.search.criteria.label.contains"/>)
        </span>
    </td>
    <td>&nbsp;</td>
</tr>

<tr>
    <td align="right">
        <b><app:storeMessage key="locateItem.label.itemProp"/>:</b>
    </td>
    <td></td>
    <td>
        <html:select name="STORE_ADMIN_ITEM_FORM" property="itemPropertyName" >
            <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
            <html:option value="SIZE">Size</html:option>
            <html:option value="COLOR">Color</html:option>
            <html:option value="UOM">Uom</html:option>
            <html:option value="HAZMAT">Hazmat (true)</html:option>
            <html:option value="OTHER_DESC">Other Desc</html:option>
            <html:option value="PACK">Pack</html:option>
            <html:option value="PKG_UPC_NUM">Pkg UPC Number</html:option>
            <html:option value="PSN">PSN</html:option>
            <html:option value="SCENT">Scent</html:option>
            <html:option value="SHIP_WEIGHT">Ship Weight</html:option>
            <html:option value="UNSPSC_CD">UNSPSC</html:option>
            <html:option value="UPC_NUM">UPC Number</html:option>
            <html:option value="PACK_PROBLEM_SKU">Pack Problem (true)</html:option>
        </html:select>
    </td>
    <td>
        <html:text name="STORE_ADMIN_ITEM_FORM" property="itemPropertyTempl"/>
    </td>
    <td>&nbsp;</td>
</tr>

<tr>
    <td align="right">
        <b><app:storeMessage key="shop.og.text.certifiedCompany"/>:</b>
    </td>
    <td></td>
    <td colspan="2">
        <html:radio name="STORE_ADMIN_ITEM_FORM" property="searchGreenCertifiedFl" value="true"/>
            <app:storeMessage key="shop.og.search.certifiedCompanyFilter.text.yes"/>
        <html:radio name="STORE_ADMIN_ITEM_FORM" property="searchGreenCertifiedFl" value="false"/>
            <app:storeMessage key="shop.og.search.certifiedCompanyFilter.text.no"/>
    </td>
    <td>&nbsp;</td>
</tr>

<tr>
    <td colspan="5" style="font-size: 3pt">&nbsp;</td>
</tr>

<tr>
    <td colspan="2"></td>
    <td>
        <html:button property="action" onclick="actionSubmit(0,'SearchStoreItemMatch');">
            <app:storeMessage key="global.action.label.search"/>
        </html:button>
    </td>
    <td align="left" width="65%">
        <app:storeMessage key="global.label.showInactive"/>:
        <html:checkbox property="showInactiveFl"/>
        <logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">
            &nbsp;<app:storeMessage key="locateItem.label.showDistrInfo"/>:
            <html:checkbox name="STORE_ADMIN_ITEM_FORM" property="distInfoFlag"/>
        </logic:notEqual>
    </td>
    <td align="left" width="15%">
        <html:button property="action" onclick="actionSubmit(0,'BackItemMatch');">
            <app:storeMessage key="admin.button.back"/>
        </html:button>
    </td>
</tr>

<tr>
    <td colspan="5" style="font-size: 3pt">&nbsp;</td>
</tr>

<tr class="results">
    <td colspan="5"></td>
</tr>
<tr>
    <td colspan="5" style="font-size: 2pt">&nbsp;</td>
</tr>
<tr>
    <td colspan="5" align="left">
        <logic:present name="STORE_ADMIN_ITEM_FORM" property="itemMatchSearchResult">
            <bean:size id="rescount" name="STORE_ADMIN_ITEM_FORM" property="itemMatchSearchResult"/>
            &nbsp;&nbsp;Count:&nbsp;<%=rescount%>
        </logic:present>
        <logic:notPresent name="STORE_ADMIN_ITEM_FORM" property="itemMatchSearchResult">
            &nbsp;&nbsp;Count:&nbsp;0
        </logic:notPresent>
    </td>
</tr>
<tr>
    <td colspan="5" style="font-size: 2pt">&nbsp;</td>
</tr>
<html:hidden property="action" value="hiddenAction"/>
<html:hidden property="matchSearch" value="true"/>
</html:form>
</table>

<logic:present name="STORE_ADMIN_ITEM_FORM" property="itemMatchSearchResult">
<bean:define id="distInfoFlag"  name="STORE_ADMIN_ITEM_FORM" property="distInfoFlag"/>
<table ID="1994" width="<%=Constants.TABLEWIDTH%>">
    <tr align="center">
        <td>
            <a ID="1995" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=id&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.id"/></b>
            </a>
        </td>
        <td>
            <a ID="1996" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=sku&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.sku"/></b>
            </a>
        </td>
        <td>
            <a ID="1997" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=name&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.name"/></b>
            </a>
        </td>
        <td>
            <a ID="1998" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=size&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.size"/></b>
            </a>
        </td>
        <td>
            <a ID="2000" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=pack&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.pack"/></b>
            </a>
        </td>
        <td>
            <a ID="1000" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=uom&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.uom"/></b>
            </a>
        </td>
        <td>
            <a ID="1001" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=color&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.color"/></b>
            </a>
        </td>
        <td>
            <a ID="1002" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=manufacturer&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.mfg"/></b>
            </a>
        </td>
        <td>
            <a ID="1003" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=msku&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.mfgSku"/></b>
            </a>
        </td>
        <td>
            <a ID="1004" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=category&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.category"/></b>
            </a>
        </td>

    <logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">
        <% if(theForm.getDistInfoFlag()) { %>
        <td>
            <a ID="1005" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=dist&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.distributor"/></b>
            </a>
        </td>
        <td>
            <a ID="1006" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=dsku&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.distSku"/></b>
            </a>
        </td>
        <% } %>
     </logic:notEqual>
        <td>
            <a ID="1007" class="fivemargin" href="searchStagedMasterItems.do?action=sort_match&sortField=statusCd&distInfoFlag=<%=distInfoFlag%>">
                <b><app:storeMessage key="locateItem.text.status"/></b>
            </a>
        </td>
        <td>
            <div class="fivemargin">
                <b><app:storeMessage key="userWorkOrder.text.actionCd"/></b>
            </div>
        </td>
    </tr>

    <logic:iterate id="item" name="STORE_ADMIN_ITEM_FORM" property="itemMatchSearchResult" indexId="indId">
        <bean:define id="key" name="item" property="itemId"/>
        <bean:define id="sku" name="item" property="sku"/>
        <bean:define id="name" name="item" property="name"/>
        <bean:define id="size" name="item" property="size"/>
        <bean:define id="pack" name="item" property="pack"/>
        <bean:define id="uom" name="item" property="uom"/>
        <bean:define id="color" name="item" property="color"/>
        <bean:define id="manuName" name="item" property="manufName"/>
        <bean:define id="manuSku" name="item" property="manufSku"/>
        <bean:define id="category" name="item" property="category"/>
        <bean:define id="statusCd" name="item" property="statusCd"/>
           
    <logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">
        <% if(theForm.getDistInfoFlag()) { %>
            <bean:define id="distName" name="item" property="distName"/>
            <bean:define id="distSku" name="item" property="distSku"/>
        <% } %>
    </logic:notEqual>

        <%
        String linkHref = "item-edit.do?action=edit&retaction=finditem&itemId=" + key;
        String bgcolor = "";
        if (indId % 2 == 0 ) {  
            bgcolor = "rowa";
        } else {
            bgcolor = "rowb";
        } 
        %>
    <tr class='<%=bgcolor%>'>
        <td>
            <bean:write name="key"/>
        </td>
        <td>
            <bean:write name="sku"/>
        </td>
        <td>
            <html:link href="<%=linkHref%>"><bean:write name="name"/></html:link>
        </td>
        <td>
            <bean:write name="size"/>
        </td>
        <td>
            <bean:write name="pack"/>
        </td>
        <td>
            <bean:write name="uom"/>
        </td>
        <td>
            <bean:write name="color"/>
        </td>
        <td>
            <bean:write name="manuName"/>
        </td>
        <td>
            <bean:write name="manuSku"/>
        </td>
        <td>
            <bean:write name="category"/>
        </td>
    <logic:notEqual name="<%=Constants.APP_USER%>" property="userStore.storeType.value" value="<%=RefCodeNames.STORE_TYPE_CD.ENTERPRISE%>">    
        <% if(theForm.getDistInfoFlag()) { %>
            <td>
                <bean:write name="distName"/>
            </td>
            <td>
                <bean:write name="distSku"/>
            </td>
        <% } %>
    </logic:notEqual>
        <td>
            <bean:write name="statusCd"/>
        </td>
        <td>
            <logic:equal name="statusCd" value="<%=RefCodeNames.ASSET_STATUS_CD.ACTIVE%>">
                <a href="../storeportal/searchStagedMasterItems.do?action=MatchStagedItem&selectedItemId=<%=key%>">
                    <app:storeMessage key="global.action.label.select"/>
                </a>
            </logic:equal>
            <logic:notEqual name="statusCd" value="<%=RefCodeNames.ASSET_STATUS_CD.ACTIVE%>">
                &nbsp;
            </logic:notEqual>
        </td>
    </tr>
    </logic:iterate>
</table>
</logic:present>

</div>

