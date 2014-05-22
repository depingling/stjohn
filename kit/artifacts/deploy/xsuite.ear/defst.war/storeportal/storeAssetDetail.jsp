<%--

  Title:        storeAssetDetail.jsp
  Description:  page detail
  Purpose:      view of  asset detail page
  Copyright:    Copyright (c) 2006
  Company:      CleanWise, Inc.
  Date:         20.12.2006
  Time:         20:14:56
  author        Alexander Chickin, TrinitySoft, Inc.

--%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.WarrantyData" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.cleanwise.view.forms.StoreAssetDetailForm" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<style type="text/css">
.widthed{
width:365px;
}
</style>

<script language="JavaScript1.2">
    <!--
    function actionSubmit(formNum, action) {
     var actions;
     actions=document.forms[formNum]["action"];
     //alert(actions.length);
     for(ii=actions.length-1; ii>=0; ii--) {
       if(actions[ii].value=='hiddenAction') {
         actions[ii].value=action;
         document.forms[formNum].submit();
         break;
       }
     }
     return false;
     }

function viewPrinterFriendly(loc) {
var prtwin = window.open(loc,"store_view_docs",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();
  return false;
}

 var docs_bi=-1;
 var docs_range=5;
 var docs_ei=docs_bi+docs_range;

function up_range_docs(docs_size,docs_idParam) {

  docs_bi=docs_bi+1;
  docs_ei=docs_ei+1;

  if(docs_ei>docs_size){
     docs_ei=docs_size;
     if(docs_range>docs_size){
      docs_bi=0;
     }else{
     docs_bi=docs_size-docs_range;
     }

  }

  block_range(docs_bi,docs_ei,docs_idParam);
  none_range(docs_bi,docs_ei,docs_idParam,docs_size);

}

 function down_range_docs(size,idParam) {

  docs_bi=docs_bi-1;
  docs_ei=docs_ei-1;

  if(0>docs_bi){
     docs_bi=0;
     if(docs_range>size){docs_ei=size;}else{
     docs_ei=docs_range;}
 }
  block_range(docs_bi,docs_ei,idParam);
  none_range(docs_bi,docs_ei,idParam,size);
}

function block_range(bi,ei,idParam){
for(var i=bi;i<ei;i++){
var id=idParam+i;
eval("document.getElementById(id)").style.display='block';
}
}

function none_range(bi,ei,idParam,size){
for(var i=0;i<bi;i++){
var id=idParam+i;
eval("document.getElementById(id)").style.display='none';
}
for(var i=ei;i<size;i++){
var id=idParam+i;
eval("document.getElementById(id)").style.display='none';
}
}
    -->
</script>
<html:html>

<bean:define id="theForm" name="STORE_ASSET_DETAIL_FORM" type="com.cleanwise.view.forms.StoreAssetDetailForm"/>

<%
    int docsCount = 0;
    String loadFun = null;

    if (((StoreAssetDetailForm) theForm).getContents() != null) {
        docsCount = ((StoreAssetDetailForm) theForm).getContents().size();
        loadFun = "up_range_docs(" + docsCount + ",'docs')";
    }

    if (loadFun != null) {
        loadFun += ";";
    }
    String assetType = RefCodeNames.ASSET_TYPE_CD.ASSET;
    if (theForm.getAssetData() != null) {
        assetType = theForm.getAssetData().getAssetTypeCd();
    }
%>

<head>
    <link rel="stylesheet" href="../externals/styles.css">
    <title>Application Administrator Home: Assets</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<%if (loadFun != null) { %>
<body onLoad="<%=loadFun%>">
<%} else {%>
<body>
<%}%>

<div class="text">
<table ID=532 class="stpTable">
    <tr>
        <td class=stpLabel>Store&nbsp;Id:</td>
        <td>
            <bean:write name="<%=Constants.APP_USER%>" property="userStore.storeId"/>
        </td>
        <td class=stpLabel>Store&nbsp;Name:</td>
        <td>
            <bean:write name="<%=Constants.APP_USER%>" property="userStore.busEntity.shortDesc"/>
        </td>
    </tr>
</table>

<table ID=533 width="<%=Constants.TABLEWIDTH%>" border="0" cellpadding="0" cellspacing="0" class="mainbody">
<html:form styleId="534" name="STORE_ASSET_DETAIL_FORM" action="/storeportal/storeAssetDetail" scope="session"
           enctype="multipart/form-data">
<logic:present name="STORE_ASSET_DETAIL_FORM" property="assetData">
<bean:define id="aId" name="STORE_ASSET_DETAIL_FORM" property="assetData.assetId"/>
<tr>
    <td>&nbsp;</td>
    <td>
        <logic:equal name="STORE_ASSET_DETAIL_FORM" property="assetData.assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
            <b><app:storeMessage key="userAssets.text.assetID"/> :</b>
        </logic:equal>
        <logic:equal name="STORE_ASSET_DETAIL_FORM" property="assetData.assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET%>">
            <b><app:storeMessage key="userAssets.text.assetMasterAssetID"/> :</b>
        </logic:equal>
        <logic:equal name="STORE_ASSET_DETAIL_FORM" property="assetData.assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>">
            <b><app:storeMessage key="userAssets.text.assetCategoryID"/> :</b>
        </logic:equal>
        <bean:write name="STORE_ASSET_DETAIL_FORM" property="assetData.assetId"/>
    </td>
    <td colspan="2" align="right">
        <logic:equal name="STORE_ASSET_DETAIL_FORM" property="assetData.assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
            <b><app:storeMessage key="userAssets.text.assetName"/> :</b>
        </logic:equal>
        <logic:equal name="STORE_ASSET_DETAIL_FORM" property="assetData.assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET%>">
            <b><app:storeMessage key="userAssets.text.masterAssetName"/> :</b>
        </logic:equal>
        <logic:equal name="STORE_ASSET_DETAIL_FORM" property="assetData.assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.CATEGORY%>">
            <b><app:storeMessage key="userAssets.text.assetCategoryName"/> :</b>
        </logic:equal>
        <span class="reqind">*</span>&nbsp;
    </td>
    <td colspan="2">
        <html:text name="STORE_ASSET_DETAIL_FORM" size="55" property="assetData.shortDesc"/>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><b>Asset Type :</b>
        <logic:greaterThan name="STORE_ASSET_DETAIL_FORM" property="assetData.assetId" value="0">
            <bean:write name="STORE_ASSET_DETAIL_FORM" property="assetData.assetTypeCd"/>
        </logic:greaterThan>
        <logic:lessThan name="STORE_ASSET_DETAIL_FORM" property="assetData.assetId" value="1">
            <html:select name="STORE_ASSET_DETAIL_FORM" property="assetData.assetTypeCd"
                         onchange="actionSubmit('0','changeAssetType')">
                <html:options collection="Asset.type.vector" property="value"/>
            </html:select>
        </logic:lessThan>
    </td>
    <% if (RefCodeNames.ASSET_TYPE_CD.ASSET.equals(assetType) ||
          RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetType))
       { %>
        <td align="right"><b>Category</b> :</td>
        <td></td>
        <td>
            <html:select name="STORE_ASSET_DETAIL_FORM" property="assetData.parentId" styleClass="widthed">
                <html:option value="">
                    <app:storeMessage  key="admin.select"/>
                </html:option>
                <logic:present name="STORE_ASSET_DETAIL_FORM" property="parentIdNamePairs">
                    <logic:iterate id="parent" name="STORE_ASSET_DETAIL_FORM" property="parentIdNamePairs"
                                   type="com.cleanwise.service.api.value.PairView">
                        <bean:define id="parentId" name="parent" property="object1" type="java.lang.Integer"/>
                        <html:option value="<%=parentId.toString()%>">
                            <bean:write name="parent" property="object2"/>
                        </html:option>
                    </logic:iterate>
                </logic:present>
            </html:select>
        </td>
    <% } else { %>
        <td colspan="3">&nbsp;</td>
    <% } %>
    <td>&nbsp;</td>
</tr>
<tr>
    <td width="3%">&nbsp;</td>
    <td width="13%">&nbsp;</td>
    <td width="10%">&nbsp;</td>
    <td width="4%">&nbsp;</td>
    <td width="22%">&nbsp;</td>
    <td width="38%" ></td>
</tr>
<% if (RefCodeNames.ASSET_TYPE_CD.ASSET.equals(assetType) ||
          RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetType))
   { %>
<tr>
    <td colspan="3">&nbsp;</td>
    <td align="right">&nbsp;</td>
    <td colspan="2"><b>Long Description</b>: <span class="reqind">*</span></td>
</tr>
<tr>
    <td colspan="3">
        <bean:define id="imageFileName" name="STORE_ASSET_DETAIL_FORM" property="imagePath" type="java.lang.String"/>
        <%  String img;
            if(((StoreAssetDetailForm)theForm).getAssetImageDir()!=null&&
                    ((StoreAssetDetailForm)theForm).getAssetImageDir().exists()&&
                    Utility.isSet(imageFileName)){
 //              img = imageFileName;
               img = new String("../en/assetimg/"+imageFileName.substring(imageFileName.lastIndexOf("\\")+1));
            } else{
               img = "../en/images/noMan.gif";
            }
        %>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <html:img styleId="535" border="1" src="<%=img%>"/>

    </td>
    <td>&nbsp;</td>
    <td colspan="2">
        <html:textarea rows="15" cols="45" name="STORE_ASSET_DETAIL_FORM" property="longDesc.value"/>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td colspan="2">Path:<%=(String) imageFileName%>
    </td>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp;</td>
    <td>&nbsp;</td>
</tr> <tr>
    <td>&nbsp;</td>
    <td colspan="2"><html:file name="STORE_ASSET_DETAIL_FORM" property="imageFile" size="35"/>

    </td>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp;</td>
</tr>
<tr>
    <td colspan="6">&nbsp;</td>
</tr>
<% } %>
<logic:equal name="STORE_ASSET_DETAIL_FORM" property="assetData.assetTypeCd" value="<%=RefCodeNames.ASSET_TYPE_CD.ASSET%>">
<tr>
    <td>&nbsp;</td>
    <td><b>Asset Number</b>:</td>
    <td>
        <html:text name="STORE_ASSET_DETAIL_FORM" property="assetData.assetNum"/>
    </td>
    <td></td>
    <td>
        <b>Status</b>:
        <span class="reqind">*</span>
    </td>
    <td>
        <html:select onchange="actionSubmit('0','changeAssetStatus')" name="STORE_ASSET_DETAIL_FORM"
                     property="assetData.statusCd">
            <html:option value="">
                <app:storeMessage  key="admin.select"/>
            </html:option>
            <html:options collection="Asset.status.vector" property="value"/>
        </html:select>
        <logic:equal name="STORE_ASSET_DETAIL_FORM" property="assetData.statusCd"
                     value="<%=RefCodeNames.ASSET_STATUS_CD.INACTIVE%>">
            <html:text name="STORE_ASSET_DETAIL_FORM" property="inactiveReason.value"/>
        </logic:equal>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><b>Serial Number</b>:</td>
    <td>
        <html:text name="STORE_ASSET_DETAIL_FORM" property="assetData.serialNum"/>
    </td>
    <td>&nbsp;</td>
    <td><b>Model Number</b>:</td>
    <td>
        <html:text name="STORE_ASSET_DETAIL_FORM" property="assetData.modelNumber"/>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><b>Sku</b> :<span class="reqind">*</span></td>
    <td>
        <html:text name="STORE_ASSET_DETAIL_FORM" property="assetData.manufSku"/>
    </td>
    <td>&nbsp;</td>
    <td><b>Manufacturer</b>:</td>
    <td>
        <html:select name="STORE_ASSET_DETAIL_FORM" property="assetData.manufId">
            <logic:present name="STORE_ASSET_DETAIL_FORM" property="manufIdNamePairs">
                <logic:iterate id="manuf" name="STORE_ASSET_DETAIL_FORM" property="manufIdNamePairs"
                               type="com.cleanwise.service.api.value.PairView">
                    <bean:define id="manufId" name="manuf" property="object1" type="java.lang.Integer"/>
                    <html:option value="<%=manufId.toString()%>">
                        <bean:write name="manuf" property="object2"/>
                    </html:option>
                </logic:iterate>
            </logic:present>
        </html:select>
    </td>
</tr>

<tr>
    <td>&nbsp;</td>
    <td><b>Acquisition Date</b>:<br>(mm/dd/yyyy)</td>
    <td>
        <html:text name="STORE_ASSET_DETAIL_FORM" property="acquisitionDate.value"/>
    </td>
    <td>&nbsp;</td>
    <td><b>Acquisition Cost</b>:</td>
    <td>
        <html:text name="STORE_ASSET_DETAIL_FORM" property="acquisitionCost.value"/>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><b>Date Last Hour Meter Reading</b>:<br>(mm/dd/yyyy)</td>
    <td>
        <html:text name="STORE_ASSET_DETAIL_FORM" property="dateLastHMR.value"/>
    </td>
    <td>&nbsp;</td>
    <td><b>Last Hour Meter Reading</b>:</td>
    <td>
        <html:text name="STORE_ASSET_DETAIL_FORM" property="lastHMR.value"/>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><b>Date In Service</b>:<br>(mm/dd/yyyy)</td>
    <td>
        <html:text name="STORE_ASSET_DETAIL_FORM" property="dateInService.value"/>
    </td>
    <td colspan="3">&nbsp;</td>
</tr>
<tr>
    <td colspan="6">&nbsp;</td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td colspan="5" align="center"><b>Warranty</b></td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td colspan="5" align="center">
        <logic:present name="STORE_ASSET_DETAIL_FORM" property="assetWarrantyAssoc">
            <bean:size id="rescount" name="STORE_ASSET_DETAIL_FORM" property="assetWarrantyAssoc"/>
            <table ID=536 width="100%" CELLSPACING="2">
                <tr class="tableheaderinfo">
                    <td width="33%">
                        Warranty Number
                    </td>

                    <td  width="33%">
                        Warranty Type
                    </td>
                    <td  width="33%">
                        Duration
                    </td>
                </tr>
                <logic:iterate id="arrele" name="STORE_ASSET_DETAIL_FORM" property="assetWarrantyAssoc" indexId="i">
                    <tr>
                        <td align="center">
                            <bean:define id="eleid" name="arrele" property="warrantyId"/>
                            <a ID=537 href="../storeportal/storeWarrantyDetail.do?action=detail&warrantyId=<%=eleid%>">
                                <bean:write name="arrele" property="warrantyNumber"/>
                            </a>
                        </td>
                        <td align="center">
                            <bean:write name="arrele" property="typeCd"/>
                        </td>
                        <td align="center">
                            <bean:write name="arrele" property="duration"/>
                        </td>
                    </tr>
                </logic:iterate>
            </table>
        </logic:present>
    </td>
</tr>
<tr>
    <td colspan="6">&nbsp;</td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td colspan="5" align="center"><b>Work Orders</b></td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td colspan="5" align="center">
        <logic:present name="STORE_ASSET_DETAIL_FORM" property="assetWorkOrderAssoc">
            <bean:size id="rescount" name="STORE_ASSET_DETAIL_FORM" property="assetWorkOrderAssoc"/>
            <table ID=538 width="100%" CELLSPACING="2">
                <tr class="tableheaderinfo">
                    <td  width="33%">
                        Work Order Number
                    </td>
                    <td  width="33%">
                        Work Order Name
                    </td>
                    <td  width="33%">
                        Work Order Type
                    </td>
                </tr>
                <logic:iterate id="arrele" name="STORE_ASSET_DETAIL_FORM" property="assetWorkOrderAssoc" indexId="i">
                    <tr>
                        <td align="center">
                            <bean:define id="eleid" name="arrele" property="workOrderId"/>
                            <bean:write name="arrele" property="workOrderId"/>
                        </td>
                        <td align="center">
                            <bean:write name="arrele" property="shortDesc"/>
                        </td>
                        <td align="center">
                            <bean:write name="arrele" property="typeCd"/>
                        </td>
                    </tr>
                </logic:iterate>
            </table>
        </logic:present>
    </td>
</tr>
<tr>
    <td colspan="6">&nbsp;</td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td colspan="5" valign="top">
        <table ID="539" width="100%" border="0" cellspacing="2">
            <tr>
                <td colspan="4" align="center"><b>Associated Documents</b></td>
            </tr>
            <logic:present name="STORE_ASSET_DETAIL_FORM" property="contents">
                <tr>
                    <td colspan="2" align="left">
                        <span style="cursor:hand" onClick="down_range_docs(<%=docsCount%>,'docs');">[<]</span>
                        &nbsp;<span style="cursor:hand" onClick="up_range_docs(<%=docsCount%>,'docs');">[>]</span>
                    </td>
                    <td></td>
                    <td align="right">
                        <a ID=540 href="../storeportal/storeAssetContent.do?action=Create New&assetId=<%=aId%>">[Add Doc]</a>
                    </td>
                </tr>
            </logic:present>
            <tr class="tableheaderinfo">
                <td>Description</td>
                <td>File Name</td>
                <td>Date Added</td>
                <td>Added By</td>
            </tr>
            <logic:present name="STORE_ASSET_DETAIL_FORM" property="contents">
                <logic:iterate id="contentV" name="STORE_ASSET_DETAIL_FORM" property="contents"
                               type="com.cleanwise.service.api.value.AssetContentView" indexId="j">
                    <logic:present name="contentV" property="assetContentData">
                        <logic:present name="contentV" property="content">
                            <bean:define id="acId" name="contentV" property="assetContentData.assetContentId"/>

                            <tr>
                                <td align="center">
                                    <a ID="541" href="storeAssetContent.do?action=detail&assetContentId=<%=acId%>">
                                        <bean:write name="contentV" property="content.shortDesc"/>
                                    </a>
                                </td>
                                <%
                                    String fileName = "";
                                    if (contentV.getContent().getPath() != null) {
                                        fileName = contentV.getContent().getPath();
                                        if (fileName.indexOf("/") > -1) {
                                            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                                        }
                                    }
                                    String loc = "storeAssetContent.do?action=readDoc&assetContentId=" + acId + "&retCd=detail";
                                %>
                                <td align="center">
                                    <a ID="542" href="#" onclick="viewPrinterFriendly('<%=loc%>');"><%=fileName%></a>
                                </td>
                                <td align="center"><%=ClwI18nUtil.formatDate(request, contentV.getContent().getAddDate(), DateFormat.DEFAULT)%></td>
                                <td align="center">
                                    <bean:write name="contentV" property="content.addBy"/>
                                </td>
                            </tr>
                        </logic:present>
                    </logic:present>
                </logic:iterate>
            </logic:present>
        </table>
    </td>
</tr>
</logic:equal>

<% if (RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET.equals(assetType)) { %>
<tr>
    <td colspan="2"><b>Manufacturer</b>:</td>
    <td>
        <html:select name="STORE_ASSET_DETAIL_FORM" property="assetData.manufId">
            <logic:present name="STORE_ASSET_DETAIL_FORM" property="manufIdNamePairs">
                <logic:iterate id="manuf" name="STORE_ASSET_DETAIL_FORM" property="manufIdNamePairs"
                               type="com.cleanwise.service.api.value.PairView">
                    <bean:define id="manufId" name="manuf" property="object1" type="java.lang.Integer"/>
                    <html:option value="<%=manufId.toString()%>">
                        <bean:write name="manuf" property="object2"/>
                    </html:option>
                </logic:iterate>
            </logic:present>
        </html:select>
    </td>
    <td colspan="3">&nbsp;</td>
</tr>
<tr>
    <td colspan="2"><b>Manufacturer Sku</b> :<span class="reqind">*</span></td>
    <td>
        <html:text name="STORE_ASSET_DETAIL_FORM" property="assetData.manufSku"/>
    </td>
    <td colspan="3">&nbsp;</td>
</tr>
<tr>
    <td colspan="2"><b>Model Number</b>:</td>
    <td>
        <html:text name="STORE_ASSET_DETAIL_FORM" property="assetData.modelNumber"/>
    </td>
    <td colspan="3">&nbsp;</td>
</tr>
<tr>
    <td colspan="2"><b>Status</b>:
        <span class="reqind">*</span>
    </td>
    <td>
        <html:select name="STORE_ASSET_DETAIL_FORM" property="assetData.statusCd">
            <html:options collection="Asset.status.vector" property="value"/>
        </html:select>
    </td>
    <td colspan="3">&nbsp;</td>
</tr>
<% } %>

<tr>
    <td colspan="6">&nbsp;</td>
</tr>
<tr>
    <td colspan="6" align="center">
        <html:submit property="action">
            <app:storeMessage  key="global.action.label.save"/>
        </html:submit>
    </td>
</tr>
<tr>
    <td colspan="6">&nbsp;</td>
</tr>
<html:hidden property="action" value="hiddenAction"/>
</logic:present>
</html:form>
</table>
</div>
</body>
</html:html>
