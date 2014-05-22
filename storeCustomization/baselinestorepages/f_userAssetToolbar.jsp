<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.BreadCrumbNavigator" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
    String display = Utility.strNN((String) request.getParameter("display"));
    String secondaryToolbar = (String) request.getParameter("secondaryToolbar");
    String t_templatorToolBar = ClwCustomizer.getStoreFilePath(request, "t_userAssetTemplatorToolBar.jsp");
    String rootDir = (String) session.getAttribute("store.dir");
    String toolLinkAsset = "/" + rootDir + "/userportal/userAssets.do";
    String toolLinkAssetProfile = "/" + rootDir + "/userportal/userAssetProfile.do?action=detail";
    String toolLinkAssetDcumentDetail = "/" + rootDir + "/userportal/userAssetContent.do?action=detail";
    String toolLinkWarranty = "/" + rootDir + "/userportal/userWarranty.do";
    String toolLinkWarrantyDetail = toolLinkWarranty + "?action=detail";
    String toolLinkWarrantyNote = toolLinkWarranty + "?action=noteInitSearch";
    String toolLinkAssetWarranty = toolLinkWarranty + "?action=assetWarrantyInit";
    String toolLinkWarrantyDocs = toolLinkWarranty + "?action=warrantyDocsInit";
%>


<%if ("f_assetDetailToolbar".equals(secondaryToolbar)) {%>


<%} else if ("f_userSecondaryToolbar".equals(secondaryToolbar)) {%>


<% if (display.indexOf("Asset") > -1 && display.indexOf("Warranty") == -1) {%>

<jsp:include flush='true' page="<%=t_templatorToolBar%>">

    <jsp:param name="display" value="<%=display%>"/>

    <jsp:param name="breadCrumbBar" value="<%=(request.getSession().getAttribute(Constants.BREAD_CRUMB_NAVIGATOR)!=null)%>"/>

    <jsp:param name="actionLink01" value="<%=toolLinkAsset%>"/>
    <jsp:param name="item01" value="t_userAssetSearch"/>
    <jsp:param name="itemLable01" value="userAssets.text.asset"/>
    <jsp:param name="tabName01" value="f_userAssetToolbar"/>
    <jsp:param name="secondaryItems01" value="t_userAssetSearch,
                                            t_userAssetProfile,
                                            t_userAssetContentDetail,
                                            t_userAssetWarrantiesConfig"/>

    <jsp:param name="actionLink02" value="<%=toolLinkWarranty%>"/>
    <jsp:param name="item02" value="t_userWarrantySearch"/>
    <jsp:param name="itemLable02" value="userWarranty.text.toolbar.warranty"/>
    <jsp:param name="tabName02" value="f_userAssetToolbar"/>
    <jsp:param name="secondaryItems02"
               value="t_userWarrantySearch,
                      t_userWarrantyDocs,
                      t_userWarrantyDocsDetail,
                      t_userWarrantyDetail,
                      t_userWarrantyNote,
                      t_userAssetWarranty,
                      t_userAssetWarrantyDetail,
                      t_userWarrantyNoteDetail"/>
    
    <jsp:param name="actionLink03" value="<%=toolLinkAsset%>"/>
    <jsp:param name="item03" value="t_userMasterAssetSearch"/>
    <jsp:param name="itemLable03" value="userAssets.text.assetMasterAsset"/>
    <jsp:param name="tabName03" value="f_userAssetToolbar"/>
    <jsp:param name="secondaryItems03"
               value="t_userMasterAssetSearch,
                      t_userMasterAssetProfile"/>
                     
    <jsp:param name="actionLink04" value="<%=toolLinkAsset%>"/>
    <jsp:param name="item04" value="t_userAssetCategorySearch"/>
    <jsp:param name="itemLable04" value="userAssets.text.assetCategory"/>
    <jsp:param name="tabName04" value="f_userAssetToolbar"/>
    <jsp:param name="secondaryItems04"
               value="t_userAssetCategorySearch,
                      t_userAssetCategoryProfile"/>
    
    <jsp:param name="toolSecondaryToolBar01" value="f_userSecondaryToolbar.jsp"/>

    <jsp:param name="secondaryItem01" value="t_userAssetProfile"/>
    <jsp:param name="secondaryItemLable01" value="userAssets.shop.toolbar.header"/>
    <jsp:param name="secondaryActionLink01" value="<%=toolLinkAssetProfile%>"/>
    <jsp:param name="renderLink01" value="true"/>

    <jsp:param name="secondaryItem02" value="t_userAssetContentDetail"/>
    <jsp:param name="secondaryItemLable02" value="userAssets.text.toolbar.assetDoc"/>
    <jsp:param name="secondaryActionLink02" value="<%=toolLinkAssetDcumentDetail%>"/>
    <jsp:param name="renderLink02" value="false"/>

    <jsp:param name="variantB" value="true"/>


    <jsp:param name="color01" value="#FFFFFF"/>
    <jsp:param name="color02" value="#9ACD32"/>
    <jsp:param name="color03" value="#6B8E23"/>
    <jsp:param name="color04" value="#555555"/>

    <jsp:param name="headerLabel" value="userAssets.text.toolbar"/>
</jsp:include>

<%} else if (display.indexOf("Warranty") >= -1) {%>

<jsp:include flush='true' page="<%=t_templatorToolBar%>">

    <jsp:param name="breadCrumbBar" value="<%=(request.getSession().getAttribute(Constants.BREAD_CRUMB_NAVIGATOR)!=null)%>"/>

    <jsp:param name="actionLink01" value="<%=toolLinkAsset%>"/>
    <jsp:param name="item01" value="t_userAssetSearch"/>
    <jsp:param name="itemLable01" value="userAssets.text.asset"/>
    <jsp:param name="tabName01" value="f_userAssetToolbar"/>
    <jsp:param name="secondaryItems01" value="t_userAssetWarrantiesConfig"/>


    <jsp:param name="actionLink02" value="<%=toolLinkWarranty%>"/>
    <jsp:param name="item02" value="t_userWarrantySearch"/>
    <jsp:param name="itemLable02" value="userWarranty.text.toolbar.warranty"/>
    <jsp:param name="tabName02" value="f_userAssetToolbar"/>
    <jsp:param name="secondaryItems02"
               value="t_userWarrantySearch,
                      t_userWarrantyDocs,
                      t_userWarrantyDocsDetail,
                      t_userWarrantyDetail,
                      t_userWarrantyNote,
                      t_userAssetWarranty,
                      t_userAssetWarrantyDetail,
                      t_userWarrantyNoteDetail"/>
    
    <jsp:param name="actionLink03" value="<%=toolLinkAsset%>"/>
    <jsp:param name="item03" value="t_userMasterAssetSearch"/>
    <jsp:param name="itemLable03" value="userAssets.text.assetMasterAsset"/>
    <jsp:param name="tabName03" value="f_userAssetToolbar"/>
    <jsp:param name="secondaryItems03"
               value="t_userMasterAssetSearch"/>
                     
    <jsp:param name="actionLink04" value="<%=toolLinkAsset%>"/>
    <jsp:param name="item04" value="t_userAssetCategorySearch"/>
    <jsp:param name="itemLable04" value="userAssets.text.assetCategory"/>
    <jsp:param name="tabName04" value="f_userAssetToolbar"/>
    <jsp:param name="secondaryItems04"
               value="t_userAssetCategorySearch"/>
    
    <jsp:param name="secondaryItem01" value="t_userWarrantyDetail"/>
    <jsp:param name="secondaryItemLable01" value="userWarranty.text.toolbar.warrantyDetails"/>
    <jsp:param name="secondaryActionLink01" value="<%=toolLinkWarrantyDetail%>"/>
    <jsp:param name="renderLink01" value="true"/>


    <jsp:param name="toolSecondaryToolBar02" value="f_userSecondaryToolbar.jsp"/>

    <jsp:param name="variantB" value="true"/>


    <jsp:param name="secondaryItem02" value="t_userWarrantyNote"/>
    <jsp:param name="secondaryItemLable02" value="userWarranty.text.toolbar.warrantyNotes"/>
    <jsp:param name="secondaryActionLink02" value="<%=toolLinkWarrantyNote%>"/>

    <jsp:param name="secondaryChildItems02" value="t_userWarrantyNote,t_userWarrantyNoteDetail"/>
    <jsp:param name="renderLink02" value="false"/>


    <jsp:param name="secondaryItem03" value="t_userAssetWarranty"/>
    <jsp:param name="secondaryItemLable03" value="userWarranty.text.toolbar.assetWarranty"/>
    <jsp:param name="secondaryActionLink03" value="<%=toolLinkAssetWarranty%>"/>
    <jsp:param name="renderLink03" value="false"/>

    <jsp:param name="secondaryChildItems03" value="t_userAssetWarranty,t_userAssetWarrantyDetail"/>

    <jsp:param name="secondaryItem04" value="t_userWarrantyDocs"/>
    <jsp:param name="secondaryItemLable04" value="userWarranty.text.toolbar.warrantyDocs"/>
    <jsp:param name="secondaryActionLink04" value="<%=toolLinkWarrantyDocs%>"/>
    <jsp:param name="renderLink04" value="false"/>

    <jsp:param name="secondaryChildItems04" value="t_userWarrantyDocs,t_userWarrantyDocsDetail"/>

    <jsp:param name="color01" value="#FFFFFF"/>
    <jsp:param name="color02" value="#9ACD32"/>
    <jsp:param name="color03" value="#6B8E23"/>
    <jsp:param name="color04" value="#555555"/>

    <jsp:param name="headerLabel" value="userAssets.text.toolbar"/>
</jsp:include>
<%}%>

<%} else {%>

<jsp:include flush='true' page="<%=t_templatorToolBar%>">

    <jsp:param name="display" value="<%=display%>"/>

    <jsp:param name="breadCrumbBar" value="<%=(request.getSession().getAttribute(Constants.BREAD_CRUMB_NAVIGATOR)!=null)%>"/>

    <jsp:param name="actionLink01" value="<%=toolLinkAsset%>"/>
    <jsp:param name="item01" value="t_userAssetSearch"/>
    <jsp:param name="itemLable01" value="userAssets.text.asset"/>
    <jsp:param name="tabName01" value="f_userAssetToolbar"/>

    <jsp:param name="actionLink02" value="<%=toolLinkWarranty%>"/>
    <jsp:param name="item02" value="t_userWarrantySearch"/>
    <jsp:param name="itemLable02" value="userWarranty.text.toolbar.warranty"/>
    <jsp:param name="tabName02" value="f_userAssetToolbar"/>
    
    <jsp:param name="actionLink03" value="<%=toolLinkAsset%>"/>
    <jsp:param name="item03" value="t_userMasterAssetSearch"/>
    <jsp:param name="itemLable03" value="userAssets.text.assetMasterAsset"/>
    <jsp:param name="tabName03" value="f_userAssetToolbar"/>
    
    <jsp:param name="actionLink04" value="<%=toolLinkAsset%>"/>
    <jsp:param name="item04" value="t_userAssetCategorySearch"/>
    <jsp:param name="itemLable04" value="userAssets.text.assetCategory"/>
    <jsp:param name="tabName04" value="f_userAssetToolbar"/>


    <jsp:param name="color01" value="#FFFFFF"/>
    <jsp:param name="color02" value="#9ACD32"/>
    <jsp:param name="color03" value="#6B8E23"/>
    <jsp:param name="color04" value="#555555"/>

    <jsp:param name="headerLabel" value="userAssets.text.toolbar"/>
</jsp:include>

<%}%>




