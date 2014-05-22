<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.text.DateFormat" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<style type="text/css">
.widthed{
width:230px;
}
</style> 

<script language="JavaScript1.2">
<!--
document.onkeypress = freeKeyHit;

function freeKeyHit(evt) {
    var evt = (evt) ? evt : event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;

    if (charCode == 13) {
        actionSubmit(0, 'SearchAsset');
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

function checkDataAndSend(formNum, action) {
    var fileName = document.getElementById("imageId").value;
    if (fileName.length > 0) {
        if (fileName.indexOf(':') > -1 && 
            (
             (fileName.indexOf('\\') > -1 && (fileName.length - 1) > fileName.lastIndexOf('\\')) || 
             (fileName.indexOf('/') > -1  && (fileName.length - 1) > fileName.lastIndexOf('/'))
            )
           ) {
            actionSubmit(formNum, action);
        } else {
            deleteNode('adminLayoutErrorSection');
            alert("File '" + fileName + "' cannot be found.  Please enter a valid file name.");
            document.getElementById("uploadFile_div").innerHTML = document.getElementById("uploadFile_div").innerHTML;
        }
    } else {
        actionSubmit(formNum, action);
    }
}

function deleteNode(elementId){
  var label=document.getElementById(elementId);
  while( label.hasChildNodes() ) { label.removeChild( label.lastChild ); }
}

function viewPrinterFriendly(loc) {
    var prtwin = window.open(loc, "view_docs",
            "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
    prtwin.focus();
return false;
}
-->
</script>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="USER_ASSET_PROFILE_MGR_FORM" type="com.cleanwise.view.forms.UserAssetProfileMgrForm"/>


<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String selectStr = ClwI18nUtil.getMessageOrNull(request, "global.action.label.select");
    if (selectStr != null) {
        selectStr = "-" + selectStr + "-";
    } else {
        selectStr = "-Select-";
    }
    String messageKey = "";
    
    boolean canEdit = theForm.getIsEditable();
    String categoryName = "";
    int parentId = theForm.getAssetData().getParentId();
    if (!canEdit && parentId > 0) {
        AssetDataVector assetCategoryV = theForm.getAssetCategories();
        if (assetCategoryV != null) {
            AssetData currCategory;
            for (int i = 0; i < assetCategoryV.size(); i++) {
                currCategory = (AssetData)assetCategoryV.get(i);
                if (currCategory.getAssetId() == parentId) {
                    categoryName = currCategory.getShortDesc();
                    break;
                }
            }
        }
    }
    String browser = (String) request.getHeader("User-Agent");
    boolean isMSIE = false;
    if (browser != null && browser.indexOf("MSIE") >= 0) {
        isMSIE = true;
    }
%>

<bean:define id="maid" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.assetId"/>

<div class="text">
    <table ID=1582 width="769" cellspacing="0" border="0" class="mainbody">
    <html:form styleId="1583" name="USER_ASSET_PROFILE_MGR_FORM" action="/storeportal/masterAssetProfile.do"
                                    scope="session" type="com.cleanwise.view.forms.UserAssetProfileMgrForm"
                                    enctype="multipart/form-data">
        <tr>
            <td colspan="3">
                <span style="font-size: 14px; font-weight: bold; line-height: 200%;">
                    <app:storeMessage key="userAssets.text.typeCd.masterAssetDetail"/>
                </span>
            </td>
        </tr>
        <tr>
            <td width="60%">
                <table>
                    <tr align="top">
                        <td width="30%" align="right">
                            <b><app:storeMessage key="userAssets.text.masterAssetName"/>:</b>
                        </td>
                        <td width="2%">&nbsp;</td>
                        <td width="68%">
                            <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                            <html:text size="43" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc"/><span class="reqind">*</span>
                            </logic:equal>
                            <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                                <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.shortDesc"/>
                            </logic:notEqual>
                        </td>
                    <tr>
                    <tr>
                        <td align="right">
                            <b><app:storeMessage key="userAssets.text.assetCategory"/>:</b>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                            <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                            <html:select name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.parentId" styleClass="widthed">
                                <html:option value="">
                                    <app:storeMessage  key="admin.select"/>
                                </html:option>
                                <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="assetCategories">
                                    <logic:iterate id="category" name="USER_ASSET_PROFILE_MGR_FORM" property="assetCategories"
                                                   type="com.cleanwise.service.api.value.AssetData">
                                        <bean:define id="categoryId" name="category" property="assetId" type="java.lang.Integer"/>
                                       <logic:equal name="category" property="statusCd" value="<%=RefCodeNames.ASSET_STATUS_CD.ACTIVE%>">
                                        <html:option value="<%=categoryId.toString()%>">
                                            <bean:write name="category" property="shortDesc"/>
                                        </html:option>
                                        </logic:equal>
                                    </logic:iterate>
                                </logic:present>
                            </html:select>
                            </logic:equal>
                            <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                                <%=categoryName%>
                            </logic:notEqual>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <b><app:storeMessage key="userAssets.shop.text.param.manufacturer"/>:</b>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                            <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                            <html:select styleClass="bugel" name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufId">
                                <html:option value="">
                                    <app:storeMessage  key="admin.select"/>
                                </html:option>
                                <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="manufIdNamePairs">
                                    <logic:iterate id="manuf" name="USER_ASSET_PROFILE_MGR_FORM" property="manufIdNamePairs"
                                                              type="com.cleanwise.service.api.value.PairView">
                                        <bean:define id="manufId" name="manuf" property="object1" type="java.lang.Integer"/>
                                        <html:option value="<%=manufId.toString()%>">
                                            <bean:write name="manuf" property="object2"/>
                                        </html:option>
                                    </logic:iterate>
                                </logic:present>
                            </html:select><span class="reqind">*</span>
                            </logic:equal>
                            <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                                <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufName"/>
                            </logic:notEqual>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <b><app:storeMessage key="userAssets.shop.text.param.manufacturerSku"/>:</b>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                            <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                            <html:text name="USER_ASSET_PROFILE_MGR_FORM" size="40" property="assetData.manufSku"/><span class="reqind">*</span>
                            </logic:equal>
                            <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                                <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.manufSku"/>
                            </logic:notEqual>
                        </td>
                    <tr>
                    <tr>
                        <td align="right">
                            <b><app:storeMessage key="userAssets.shop.text.param.modelNumber"/>:</b>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                            <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                            <html:text name="USER_ASSET_PROFILE_MGR_FORM" size="40" property="assetData.modelNumber"/>
                            </logic:equal>
                            <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                                <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.modelNumber"/>
                            </logic:notEqual>
                        </td>
                    <tr>
                    <tr>
                        <td align="right" style="vertical-align: top">
                            <b><app:storeMessage key="userAssets.text.assocDocs.description"/>:</b>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                            <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                            <html:textarea rows="6" cols="30" name="USER_ASSET_PROFILE_MGR_FORM" property="longDesc.value"/>
                            <span class="reqind" style="vertical-align: top">*</span>
                            </logic:equal>
                            <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                                <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="longDesc.value"/>
                            </logic:notEqual>
                        </td>
                    <tr>
                    <tr>
                    </tr>
                    <tr>
                        <td align="right">
                            <b><app:storeMessage key="userAssets.shop.text.param.status"/>:</b>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                            <bean:define id="statusCd" name="USER_ASSET_PROFILE_MGR_FORM"
                                                       property="assetData.statusCd" type="java.lang.String"/>
                            <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                                <html:select name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.statusCd">
                                    <logic:present name="Asset.status.vector">
                                        <logic:iterate id="statusRefCd" name="Asset.status.vector" type="com.cleanwise.service.api.value.RefCdData">
                                            <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userAssets.shop.text.param.status." + ((String)statusRefCd.getValue()).toUpperCase());%>
                                            <html:option value="<%=statusRefCd.getValue()%>">
                                                <% if (messageKey != null) {%>
                                                    <%=messageKey%>
                                                <%} else {%>
                                                    <%=statusRefCd.getValue()%>
                                                <%}%>
                                            </html:option>
                                        </logic:iterate>
                                    </logic:present>
                                </html:select><span class="reqind">*</span>
                            </app:authorizedForFunction>
                            </logic:equal>
                            <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                                <bean:write name="USER_ASSET_PROFILE_MGR_FORM" property="assetData.statusCd"/>
                            </logic:notEqual>
                        </td>
                    </tr>
                </table>
            </td>
            <td width="5%">
            </td>
            <td width="35%" valign="top">
                <table width="100%">
                    <tr>
                        <td>
                            <% String img = ClwCustomizer.getImgRelativePath() + "noMan.gif";%>
                            <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="mainAssetImageName">
                                <bean:define id="imageFileName" name="USER_ASSET_PROFILE_MGR_FORM"
                                             property="mainAssetImageName" type="java.lang.String"/>
                                <% if (Utility.isSet(imageFileName)) {
                                    img = ClwCustomizer.getTemplateImgRelativePath() + "/" + imageFileName;
                                }
                                %>
                            </logic:present>
                            <div style="width: 200; height: 200; overflow-x:hidden; overflow-y:hidden;">
                                <html:img border="1" src="<%=img%>"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                                <div id="uploadFile_div">
                                    <html:file styleId="imageId" name="USER_ASSET_PROFILE_MGR_FORM" property="imageFile" size="29"/>
                                </div>
                            </logic:equal>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="3">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="3">
                <table width="100%" border="0" cellpadding="2" cellspacing="1">
                    <tr>
                        <td class="customerltbkground" vAlign="top" align="middle" colspan="4">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="userWarranty.text.assocDocs"/></b>
                            </SPAN>
                            <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                                <logic:greaterThan name="maid" value="0">
                                <html:submit property="action">
                                    <app:storeMessage key="global.label.addContent"/>
                                </html:submit>
                                </logic:greaterThan>
                            </app:authorizedForFunction>
                            </logic:equal>
                        </td>
                    </tr>
                    <tr>
                        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWarranty.text.assocDocs.description"/>
                                </div>
                            </td>
                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWarranty.text.assocDocs.fileName"/>
                                </div>
                            </td>
                        </app:authorizedForFunction>
                        <td class="shopcharthead">
                            <div class="fivemargin">
                                <app:storeMessage key="userWarranty.text.assocDocs.addDate"/>
                            </div>
                        </td>
                        <td class="shopcharthead">
                            <div class="fivemargin">
                                <app:storeMessage key="userWarranty.text.assocDocs.addBy"/>
                            </div>
                        </td>
                    </tr>
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="contents">
                        <logic:iterate id="contentV"
                                       name="USER_ASSET_PROFILE_MGR_FORM"
                                       property="contents"
                                       type="com.cleanwise.service.api.value.AssetContentView"
                                       indexId="j">
                            <logic:present name="contentV" property="content">
                            <logic:present name="contentV" property="assetContentData">
                                <bean:define id="acId" name="contentV" property="assetContentData.assetContentId"/>
                                <%String loc = "";%>
                                <tr id="docs<%=((Integer) j).intValue()%>">
                                <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                                    <td>
                                        <logic:present name="contentV" property="content.shortDesc">
                                            <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                                            <a href="../storeportal/masterAssetContent.do?action=detail&assetContentId=<%=acId%>">
                                                <bean:write name="contentV" property="content.shortDesc"/>
                                            </a>
                                            </logic:equal>
                                            <logic:notEqual name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                                                <bean:write name="contentV" property="content.shortDesc"/>
                                            </logic:notEqual>
                                        </logic:present>
                                    </td>
                                    <td><%
                                        String fileName = "";
                                        if (contentV.getContent().getPath() != null) {
                                            fileName = contentV.getContent().getPath();
                                            if (fileName.indexOf("/") > -1) {
                                                fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                                            }
                                        }
                                       %>
                                        <% loc = "../storeportal/masterAssetContent.do?action=readDocs&assetContentId=" + acId;%>
                                            <a href="#" onclick="viewPrinterFriendly('<%=loc%>');"><%=fileName%></a>
                                    </td>
                                </app:authorizedForFunction>
                                <td>
                                    <logic:present name="contentV" property="content.addDate">
                                        <%= ClwI18nUtil.formatDate(request, contentV.getContent().getAddDate(), DateFormat.DEFAULT)%>
                                    </logic:present>
                                </td>
                                <td>
                                    <logic:present name="contentV" property="content.addBy">
                                        <bean:write name="contentV" property="content.addBy"/>
                                    </logic:present>
                                </td>
                            </logic:present>
                            </logic:present>
                            </tr>
                        </logic:iterate>
                    </logic:present>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="3">&nbsp;</tr>
        </tr>
        <tr>
            <td colspan="3" align="center">
                <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="isEditable" value="true">
                    <%if (isMSIE) {%>
                        <html:button property="action" onclick="checkDataAndSend(0,'saveStoreMasterAsset');">
                            <app:storeMessage key="global.action.label.save"/>
                        </html:button>
                    <%} else {%>    
                        <html:button property="action" onclick="actionSubmit(0,'saveStoreMasterAsset');">
                            <app:storeMessage key="global.action.label.save"/>
                        </html:button>
                    <%}%>
                </logic:equal>
            </td>
        </tr>
        <tr>
            <td colspan="3">&nbsp;</tr>
        </tr>
        <logic:equal name="USER_ASSET_PROFILE_MGR_FORM" property="showLinkedStores" value="true">
        <tr>
            <td colspan="3">
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
                    <logic:present name="USER_ASSET_PROFILE_MGR_FORM" property="linkedStores">
                    <logic:iterate id="storeInfo"
                                   name="USER_ASSET_PROFILE_MGR_FORM"
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
            </td>
        </tr>
        <tr>
            <td colspan="3">&nbsp;</tr>
        </tr>
        </logic:equal>
    <html:hidden property="action" value="hiddenAction"/>
    </html:form>
    </table>

</div>





