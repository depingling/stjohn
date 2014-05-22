<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<style type="text/css">
.widthed{
width:185px;
}
</style>

<script language="JavaScript1.2">
<!--
document.onkeypress = freeKeyHit;

function freeKeyHit(evt) {
    var evt = (evt) ? evt : event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;

    if (charCode == 13) {
        actionSubmit(0,'SearchStagedMasterAsset');
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

dml = document.forms;
for (i = 0; i < dml.length; i++) {
    ellen = dml[i].elements.length;
    for ( j = 0; j < ellen; j++) {
        if (dml[i].elements[j].name == 'searchField') {
            dml[i].elements[j].focus();
            break;
        }
    }
}
//-->
</script>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String selectStr = ClwI18nUtil.getMessageOrNull(request, "global.action.label.select");
    if (selectStr != null) {
        selectStr = "-" + selectStr + "-";
    } else {
        selectStr = "-Select-";
    }
%>

<div class="text">

<table ID="1584" width="<%=Constants.TABLEWIDTH%>" cellspacing="0" border="0" class="mainbody">
<html:form styleId="1583" name="USER_ASSET_MGR_FORM"
                          action="/storeportal/searchStagedMasterAssets.do"
                          scope="session"
                          type="com.cleanwise.view.forms.UserAssetMgrForm">
    <tr>
        <td colspan="5">
            <span style="font-size: 14px; font-weight: bold; line-height: 200%;">
                <app:storeMessage key="global.action.label.search"/>&nbsp;<app:storeMessage key="userAssets.text.assetStagedMasterAsset"/>
            </span>
        </td>
    </tr>
    <tr>
        <td width="20%" align="right">
            <b><app:storeMessage key="userAssets.text.assetCategory"/>:</b>
        </td>
        <td width="2%"></td>
        <td>
            <html:select name="USER_ASSET_MGR_FORM" property="searchCategoryName" styleClass="widthed">
                <html:option value="">
                    <%=selectStr%>
                </html:option>
                <logic:present name="Store.category.vector">
                    <logic:iterate id="category" name="Store.category.vector"
                                   type="com.cleanwise.service.api.value.PairView">
                        <bean:define id="categoryId" name="category" property="object1" type="java.lang.Integer"/>
                        <bean:define id="categoryName" name="category" property="object2" type="java.lang.String"/>
                        <html:option value="<%=categoryName%>">
                            <bean:write name="category" property="object2"/>
                        </html:option>
                    </logic:iterate>
                </logic:present>
            </html:select>
        </td>
        <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
        <td align="right">
            <b><app:storeMessage key="userAssets.text.masterAssetName"/>:</b>
        </td>
        <td></td>
        <td>
            <html:text styleId="searchField" size="25" name="USER_ASSET_MGR_FORM" property="searchField" />
        </td>
        <td align="left" nowrap="nowrap">
            <html:radio name="USER_ASSET_MGR_FORM" property="nameSearchType"
                        value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
            <app:storeMessage key="admin2.search.criteria.label.startsWith"/>
            <html:radio name="USER_ASSET_MGR_FORM" property="nameSearchType"
                        value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
            <app:storeMessage key="admin2.search.criteria.label.contains"/>
        </td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td align="right">
            <b><app:storeMessage key="userAssets.shop.text.param.modelNumber"/>:</b>
        </td>
        <td></td>
        <td>
            <html:text styleId="searchModel" size="25" name="USER_ASSET_MGR_FORM" property="searchModel" />
        </td>
        <td align="left" nowrap="nowrap">
            <html:radio name="USER_ASSET_MGR_FORM" property="modelSearchType"
                        value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
            <app:storeMessage key="admin2.search.criteria.label.startsWith"/>
            <html:radio name="USER_ASSET_MGR_FORM" property="modelSearchType"
                        value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
            <app:storeMessage key="admin2.search.criteria.label.contains"/>
        </td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td align="right">
            <b><app:storeMessage key="userAssets.shop.text.param.manufacturer"/>:</b>
        </td>
        <td></td>
        <td>
            <html:select name="USER_ASSET_MGR_FORM" property="searchManufacturerName">
                <html:option value="">
                    <%=selectStr%>
                </html:option>
                <logic:present name="Store.manufacturer.vector">
                    <logic:iterate id="manufacturer" name="Store.manufacturer.vector"
                                   type="com.cleanwise.service.api.value.PairView">
                        <bean:define id="manufacturerId" name="manufacturer" property="object1" type="java.lang.Integer"/>
                        <bean:define id="manufacturerName" name="manufacturer" property="object2" type="java.lang.String"/>
                        <html:option value="<%=manufacturerName%>">
                            <bean:write name="manufacturer" property="object2"/>
                        </html:option>
                    </logic:iterate>
                </logic:present>
            </html:select>
        </td>
        <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
        <td align="right">
            <b><app:storeMessage key="userAssets.shop.text.param.manufacturerSku"/>:</b>
        </td>
        <td></td>
        <td>
            <html:text styleId="searchManufSku" size="25" name="USER_ASSET_MGR_FORM" property="searchManufSku" />
        </td>
        <td align="left" nowrap="nowrap">
            <html:radio name="USER_ASSET_MGR_FORM" property="manufSkuSearchType"
                        value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
            <app:storeMessage key="admin2.search.criteria.label.startsWith"/>
            <html:radio name="USER_ASSET_MGR_FORM" property="manufSkuSearchType"
                        value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
            <app:storeMessage key="admin2.search.criteria.label.contains"/>
        </td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td align="right">
            <b><app:storeMessage key="userAssets.text.assetMasterAssetID"/>:</b>
        </td>
        <td>&nbsp;</td>
        <td>
            <html:text styleId="searchNumber" size="25" name="USER_ASSET_MGR_FORM" property="searchNumber" />
        </td>
        <td width="45%" align="left" nowrap="nowrap">
            <html:radio name="USER_ASSET_MGR_FORM" property="idSearchType"
                        value="<%=RefCodeNames.SEARCH_TYPE_CD.BEGINS%>"/>
            <app:storeMessage key="admin2.search.criteria.label.startsWith"/>
            <html:radio name="USER_ASSET_MGR_FORM" property="idSearchType"
                        value="<%=RefCodeNames.SEARCH_TYPE_CD.CONTAINS%>"/>
            <app:storeMessage key="admin2.search.criteria.label.contains"/>
        </td>
    </tr>
    <tr>
        <td colspan="5">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="2">&nbsp;</td>
        <td>
            <html:button property="action" onclick="actionSubmit(0,'SearchStagedMasterAsset');">
                <app:storeMessage key="global.action.label.search"/>
            </html:button>
        </td>
        <td>&nbsp;</td>
        <td align="left" width="15%">
            <html:button property="action" onclick="actionSubmit(0,'StagedCreateAll');">
                <app:storeMessage key="admin.button.createAll"/>
            </html:button>
        </td>
    </tr>
    <tr>
        <td colspan="5">&nbsp;</td>
    </tr>
    <tr class="results">
        <td colspan="5"></td>
    </tr>
    <tr>
        <td colspan="5" style="font-size: 3pt">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="4" align="left">
            <logic:present name="USER_ASSET_MGR_FORM" property="stagedAssetSearchResult">
                <bean:size id="rescount" name="USER_ASSET_MGR_FORM" property="stagedAssetSearchResult"/>
                &nbsp;&nbsp;Count:&nbsp;<%=rescount%>
            </logic:present>
            <logic:notPresent name="USER_ASSET_MGR_FORM" property="stagedAssetSearchResult">
                &nbsp;&nbsp;Count:&nbsp;0
            </logic:notPresent>
        </td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td colspan="5" style="font-size: 3pt">&nbsp;</td>
    </tr>
<html:hidden property="assetType" value="<%=RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET%>"/>
<html:hidden property="matchSearch" value="false"/>
<html:hidden property="action" value="hiddenAction"/>
</html:form>
</table>

  <script type="text/javascript" language="JavaScript">
  <!--
     dml=document.forms;
     for(i=0; i<dml.length; i++) {
      ellen = dml[i].elements.length;
      for(j=0; j<ellen; j++) {
        if (dml[i].elements[j].name=='searchField') {
          dml[i].elements[j].focus();
          break;
        }
      }
     }
  // -->
  </script>

<logic:present name="USER_ASSET_MGR_FORM" property="stagedAssetSearchResult">
<logic:greaterThan name="rescount" value="0">
<table ID="9941" width="<%=Constants.TABLEWIDTH%>">
<tr align="left">
    <td>
        <div class="fivemargin">
            <a class="storeassetsearchlinks" href="searchStagedMasterAssets.do?action=sort_staged_assets&sortField=assetid">
                <app:storeMessage key="userAssets.text.assetMasterAssetID"/>
            </a>
        </div>
    </td>
    <td>
        <div class="fivemargin">
            <a class="storeassetsearchlinks" href="searchStagedMasterAssets.do?action=sort_staged_assets&sortField=assetcategory">
                <app:storeMessage key="userAssets.text.assetCategory"/>
            </a>
        </div>
    </td>
    <td>
        <div class="fivemargin">
            <a class="storeassetsearchlinks" href="searchStagedMasterAssets.do?action=sort_staged_assets&sortField=assetname">
                <app:storeMessage key="userAssets.text.masterAssetName"/>
            </a>
        </div>
    </td>
    <td>
        <div class="fivemargin">
            <a class="storeassetsearchlinks" href="searchStagedMasterAssets.do?action=sort_staged_assets&sortField=assetmodel">
                <app:storeMessage key="userAssets.text.modelNum"/>
            </a>
        </div>
    </td>
    <td>
        <div class="fivemargin">
            <a class="storeassetsearchlinks" href="searchStagedMasterAssets.do?action=sort_staged_assets&sortField=assetmanufacturer">
                <app:storeMessage key="userAssets.shop.text.param.manufacturer"/>
            </a>
        </div>
    </td>
    <td>
        <div class="fivemargin">
            <a class="storeassetsearchlinks" href="searchStagedMasterAssets.do?action=sort_staged_assets&sortField=assetmanufacturersku">
                <app:storeMessage key="userAssets.shop.text.param.manufacturerSku"/>
            </a>
        </div>
    </td>
    <td>
        <div class="fivemargin">
            <a class="storeassetsearchlinks" href="searchStagedMasterAssets.do?action=sort_staged_assets&sortField=assetstatus">
                <app:storeMessage key="userAssets.shop.text.param.status"/>
            </a>
        </div>
    </td>
    <td>
        <div class="fivemargin" style="font-weight: bold">
            <app:storeMessage key="userAssets.shop.text.param.action"/>
        </div>
    </td>
</tr>

<logic:iterate id="arrele" name="USER_ASSET_MGR_FORM" property="stagedAssetSearchResult" indexId="i">
    <bean:define id="eleid" name="arrele" property="assetId"/>
    <%
    String bgcolor = "";
    if (i % 2 == 0 ) {
        bgcolor = "rowa";
    } else {
        bgcolor = "rowb";
    }
    %>
    <tr class='<%=bgcolor%>'>
        <td>
            <bean:write name="arrele" property="assetId"/>
        </td>
        <td>
            <bean:write name="arrele" property="assetCategory"/>
        </td>
        <td>
            <bean:write name="arrele" property="assetName"/>
        </td>
        <td>
            <bean:write name="arrele" property="modelNumber"/>
        </td>
        <td>
            <bean:write name="arrele" property="manufName"/>
        </td>
        <td>
            <bean:write name="arrele" property="manufSku"/>
        </td>
        <td>
            <bean:write name="arrele" property="status"/>
        </td>
        <td>
            <logic:equal name="arrele" property="matchedAssetId" value="0">
                <a href="../storeportal/searchStagedMasterAssets.do?action=MasterAssetMatch&masterAssetIndex=<%=i%>">
                    <app:storeMessage key="userAssets.text.match"/>
                </a>
            </logic:equal>
            <logic:greaterThan name="arrele" property="matchedAssetId" value="0">
                <a href="../storeportal/searchStagedMasterAssets.do?action=UnmatchMatchedAsset&masterAssetIndex=<%=i%>">
                    <app:storeMessage key="userAssets.text.unMatch"/>
                </a>
            </logic:greaterThan>
        </td>
        
    </tr>
</logic:iterate>
</table>
 </logic:greaterThan>
</logic:present>

</div>






