<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.UiSiteMgrForm" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
    String property;
    String controlName;
    String elementName;
%>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="UI_SITE_MGR_FORM" type="com.cleanwise.view.forms.UiSiteMgrForm"/>

<html:html>

<head>
    <link rel="stylesheet" type="text/css" href='../externals/dojo_1.1.0/dijit/themes/dijit.css'>
    <link rel="stylesheet" type="text/css" href='../externals/dojo_1.1.0/dijit/themes/tundra/Menu.css'>
    <script language="JavaScript" type="text/javascript">
        dojo.require("clw.admin2.UiStatusControlMenu");
    </script>
</head>

<style type="text/css">

    .targetBoxHover {
        border-top: 3px solid blue;
        border-bottom: 3px solid blue;
        border-left: 3px solid blue;
        border-right: 3px solid blue;
        cursor: pointer;
        cursor: hand;
    }

    .targetBoxUnhover {
        border-top: 0px solid #cccccc;
        border-bottom: 0px solid #cccccc;
        border-left: 0px solid #cccccc;
        border-right: 0px solid #cccccc;
        cursor: pointer;
        cursor: hand;  }

    .targetBoxUnhoverCenter {
        border-top: 0px solid #cccccc;
        border-bottom: 0px solid #cccccc;
        cursor: pointer;
        cursor: hand;}

    .targetBoxUnhoverRight {
        border-top: 0px solid #cccccc;
        border-bottom: 0px solid #cccccc;
        border-right: 0px solid #cccccc;
        cursor: pointer;
        cursor: hand; }

    .targetBoxUnhoverLeft {
        border-top: 0px solid #cccccc;
        border-bottom: 0px solid #cccccc;
        border-left: 0px solid #cccccc;
        cursor: pointer;
        cursor: hand; }

    .targetBoxHoverCenter {
        border-top: 3px solid blue;
        border-bottom: 3px solid blue;
        cursor: pointer;
        cursor: hand;  }

    .targetBoxHoverRight {
        border-top: 3px solid blue;
        border-bottom: 3px solid blue;
        border-right: 3px solid blue;
        cursor: pointer;
        cursor: hand;}

    .targetBoxHoverLeft {
        border-top: 3px solid blue;
        border-bottom: 3px solid blue;
        border-left: 3px solid blue;
        cursor: pointer;
        cursor: hand; }
</style>

<body class="tundra">

<div class="text">

<table cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH800%>"  class="mainbody">

<html:form name="UI_SITE_MGR_FORM"
           scope="session"
           action="uimanager/uiSite.do"
           scope="session"
           type="com.cleanwise.view.forms.UiSiteMgrForm">


<tr>
    <!--   START  UI_SITE_ACCOUNT_INF -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.ACCOUNT_INF;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.ACCOUNT_INF%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden  styleId="<%=RefCodeNames.UI_CONTROL.ACCOUNT_INF%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td colspan="2" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ID%>" height="30px"><b>Account Id:</b> &lt No Default &gt
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ID;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ID%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Account&nbsp;Id\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
    </td>
    <td colspan="2" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NAME%>"><b>Account&nbsp;Name:</b> &lt No Default &gt
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NAME;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NAME%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Account&nbsp;Name\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
    </td>
    <!--   END UI_SITE_ACCOUNT_INFE -------------------------------------------------------------------------------------->
</tr>
<tr>
    <td class="largeheader" colspan="4">Site Detail</td>
</tr>
<!--   START  SITE ID AND _NAME -------------------------------------------------------------------------------------->
<% controlName = RefCodeNames.UI_CONTROL.ID_AND_NAME;%>
<% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
<html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.ID_AND_NAME%>"/>
<% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
<html:hidden styleId="<%=RefCodeNames.UI_CONTROL.ID_AND_NAME%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

<tr>
    <td colspan="2" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ID_LABEL%>"><b>Site&nbsp;Id:</b> &lt No Default &gt
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ID_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ID_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Site&nbsp;Id\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/> </td>
    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.NAME_LABEL%>"><b>Site Name:</b><span class="reqind">*</span>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.NAME_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.NAME_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Site&nbsp;Name\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/> </td>
    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.NAME_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.NAME_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.NAME_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:text  name="UI_SITE_MGR_FORM" property="<%=property%>"/>
    </td>
</tr>
<!--   END SITE ID AND _NAME -------------------------------------------------------------------------------------->
<tr>

    <!--   START  UI_SITE_STATUS) -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.STATUS;%>

    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.STATUS%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden  styleId="<%=RefCodeNames.UI_CONTROL.STATUS%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_LABEL%>"><b>Status:</b><span class="reqind">*</span>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.STATUS_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Status\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
    </td>


    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_VALUE%>">

        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.STATUS_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:select name="UI_SITE_MGR_FORM" property="<%=property%>">
            <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
            <html:options  collection="ui.site.status.vector" property="value" />
        </html:select>

    </td>
    <!--   END UI_SITE_STATUS) -------------------------------------------------------------------------------------->


    <!--   START  UI_SITE_BSC-------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.BSC;%>

    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.BSC%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.BSC%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.BSC_LABEL%>"><b>Building Services Contractor:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.BSC_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.BSC_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Building Services Contractor\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/></td>


    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.BSC_VALUE%>">

        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.BSC_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.BSC_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:select name="UI_SITE_MGR_FORM" property="<%=property%>">
            <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
            <html:options  collection="ui.list.all.bsc" property="busEntityData.shortDesc" />
        </html:select>

    </td>
    <!--   END UI_SITE_BSC -------------------------------------------------------------------------------------->


</tr>
<tr>

    <!--   START  UI_SITE_EFF_DaTE -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.EFF_DATE;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.EFF_DATE%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.EFF_DATE%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.EFF_DATE_LABEL%>"><b>Effective Date:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.EFF_DATE_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.EFF_DATE_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Effective Date\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/> </td>
    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.EFF_DATE_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.EFF_DATE_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.EFF_DATE_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:text  name="UI_SITE_MGR_FORM" property="<%=property%>" size="30"/>
    </td>
    <!--   END  UI_SITE_EFF_DaTE -------------------------------------------------------------------------------------->


    <!--   START  UI_SITE_EXP_DaTE -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.EXP_DATE;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.EXP_DATE%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.EXP_DATE%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.EXP_DATE_LABEL%>"><b>Expiration Date:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.EXP_DATE_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.EXP_DATE_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Expiration Date\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/> </td>
    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.EXP_DATE_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.EXP_DATE_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.EXP_DATE_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:text  name="UI_SITE_MGR_FORM" property="<%=property%>" size="30"/>
    </td>
    <!--   END  UI_SITE_EXP_DaTE -------------------------------------------------------------------------------------->
</tr>
<tr>


    <!--   START  UI_SITE_BUDGET_REF_NUM -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.BUD_REF_NUM;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.BUD_REF_NUM%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.BUD_REF_NUM%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.BUD_REF_NUM_LABEL%>"><b>Site&nbsp;Budget&nbsp;Reference&nbsp;Number:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.BUD_REF_NUM_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.BUD_REF_NUM_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Site&nbsp;Budget&nbsp;Reference&nbsp;Number\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/> </td>
    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.BUD_REF_NUM_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.BUD_REF_NUM_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.BUD_REF_NUM_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:text  name="UI_SITE_MGR_FORM" property="<%=property%>" size="30"/>
    </td>
    <!--   END  UI_SITE_BUDGET_REF_NUM  -------------------------------------------------------------------------------------->


    <!--   START  UI_SITE_BUDGET_REF_NUM -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.DISTR_BUD_REF_NUM;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.DISTR_BUD_REF_NUM%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.DISTR_BUD_REF_NUM%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.DISTR_BUD_REF_NUM_LBL%>"><b>Distributor&nbsp;Site&nbsp;Reference&nbsp;Number:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.DISTR_BUD_REF_NUM_LBL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.DISTR_BUD_REF_NUM_LBL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Distributor&nbsp;Site&nbsp;Reference&nbsp;Number\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/> </td>
    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.DISTR_BUD_REF_NUM_VAL%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.DISTR_BUD_REF_NUM_VAL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.DISTR_BUD_REF_NUM_VAL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:text  name="UI_SITE_MGR_FORM" property="<%=property%>" size="30"/>
    </td>
    <!--   END  UI_SITE_DISTR_BUDGET_REF_NUM  -------------------------------------------------------------------------------------->

</tr>
<!--   START  UI_SITE_ERP_NUMBER -------------------------------------------------------------------------------------->
<% controlName = RefCodeNames.UI_CONTROL.ERP_NUMBER;%>
<% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
<html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.ERP_NUMBER%>"/>
<% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
<html:hidden styleId="<%=RefCodeNames.UI_CONTROL.ERP_NUMBER%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>
<tr>
    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ERP_NUMBER_LABEL%>"><b>Site Erp Number:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ERP_NUMBER_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ERP_NUMBER_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Site Erp Number\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/> </td>
    <td colspan="3" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ERP_NUMBER_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ERP_NUMBER_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ERP_NUMBER_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:text  name="UI_SITE_MGR_FORM" property="<%=property%>" size="30"/>
    </td>

</tr>
<!--   END  SITE_ERP_NUMBER -------------------------------------------------------------------------------------->
<tr>
    <!--   START  UI_SITE_TAXABLE -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.TAXABLE;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.TAXABLE%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.TAXABLE%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.TAXABLE_LABEL%>"><b>Taxable:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.TAXABLE_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.TAXABLE_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Taxable\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/> </td>
    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.TAXABLE_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.TAXABLE_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.TAXABLE_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.RADIO%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        Yes&nbsp;<html:radio name="UI_SITE_MGR_FORM" property="<%=property%>" value="Y"/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="UI_SITE_MGR_FORM" property="<%=property%>" value="N"/>
    </td>
    <!--   END  UI_SITE_TAXABLE  -------------------------------------------------------------------------------------->

    <!--   START  UI_SITE_ENABLE_INV -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.ENABLE_INV;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.ENABLE_INV%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.ENABLE_INV%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>


    <td colspan="2" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.UI_ENABLE_INV_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.UI_ENABLE_INV_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.UI_ENABLE_INV_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:checkbox name="UI_SITE_MGR_FORM" property="<%=property%>" />
        <b>Enable Inventory Shopping/Shared cart:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ENABLE_INV_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ENABLE_INV_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Enable Inventory Shopping/Shared cart\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
    </td>
    <!--   END  UI_SITE_ENABLE_INV  -------------------------------------------------------------------------------------->

</tr>
<tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <!--   START  .INV_SHOP_TYPE -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.INV_SHOP_TYPE;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.INV_SHOP_TYPE%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.INV_SHOP_TYPE%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>


    <td colspan="2" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_TYPE_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_TYPE_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_TYPE_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:checkbox name="UI_SITE_MGR_FORM" property="<%=property%>" />
        <b>Inventory Shopping(Modern/Classic)</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_TYPE_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_TYPE_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Inventory Shopping(Modern/Classic)\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
    </td>
    <!--   END  .INV_SHOP_TYPE  -------------------------------------------------------------------------------------->

</tr>
<tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <!--   START  .INV_SHOP_HOLD_ORDER_UDD -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.INV_SHOP_HOLD_ORDER_UDD;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.INV_SHOP_HOLD_ORDER_UDD%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.INV_SHOP_HOLD_ORDER_UDD%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>


    <td colspan="2" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_HOLD_ORDER_UDD_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_HOLD_ORDER_UDD_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_HOLD_ORDER_UDD_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:checkbox name="UI_SITE_MGR_FORM" property="<%=property%>" />
        <b>Hold processed order until delivery date:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_HOLD_ORDER_UDD_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_HOLD_ORDER_UDD_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Hold processed order until delivery date\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
    </td>
    <!--   END  .INV_SHOP_HOLD_ORDER_UDD  -------------------------------------------------------------------------------------->

</tr>
<tr>
    <!--   START  TARGET_FACILITY_RANK -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.TARGET_FACILITY_RANK;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.TARGET_FACILITY_RANK%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.TARGET_FACILITY_RANK%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.TARGET_FACILITY_RANK_LABEL%>"><b>Target Facility Rank:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.TARGET_FACILITY_RANK_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.TARGET_FACILITY_RANK_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Target Facility Rank\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/> </td>
    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.TARGET_FACILITY_RANK_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.TARGET_FACILITY_RANK_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.TARGET_FACILITY_RANK_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:text  name="UI_SITE_MGR_FORM" property="<%=property%>" size="10" maxlength="30"/>
    </td>
    <!--   END  TARGET_FACILITY_RANK -------------------------------------------------------------------------------------->


    <!--   START  BYPASS_ORDER_ROUTING -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.BYPASS_ORDER_ROUTING;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.BYPASS_ORDER_ROUTING%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.BYPASS_ORDER_ROUTING%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>


    <td colspan="2" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.BYPASS_ORDER_ROUTING_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.BYPASS_ORDER_ROUTING_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.BYPASS_ORDER_ROUTING_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:checkbox name="UI_SITE_MGR_FORM" property="<%=property%>" />
        <b>Bypass Order Routing:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.BYPASS_ORDER_ROUTING_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.BYPASS_ORDER_ROUTING_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Bypass Order Routing\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
    </td>
    <!--   END  .BYPASS_ORDER_ROUTING  -------------------------------------------------------------------------------------->

</tr>


<tr>
    <!--   SITE_LINE_LEVEL_CODE -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.SITE_LINE_LEVEL_CODE;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.SITE_LINE_LEVEL_CODE%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.SITE_LINE_LEVEL_CODE%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.SITE_LINE_LEVEL_CODE_LABEL%>"><b>Site Line Level Code:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.SITE_LINE_LEVEL_CODE_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.SITE_LINE_LEVEL_CODE_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Site Line Level Code\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/> </td>
    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.SITE_LINE_LEVEL_CODE_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.SITE_LINE_LEVEL_CODE_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.SITE_LINE_LEVEL_CODE_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:text  name="UI_SITE_MGR_FORM" property="<%=property%>" size="10" maxlength="30"/>
    </td>
    <!--   END  SITE_LINE_LEVEL_CODE -------------------------------------------------------------------------------------->


    <!--   START CONSOLIDATED_ORDER_WH -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.CONSOLIDATED_ORDER_WH;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.CONSOLIDATED_ORDER_WH%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.CONSOLIDATED_ORDER_WH%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>


    <td colspan="2" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.CONSOLIDATED_ORDER_WH_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.CONSOLIDATED_ORDER_WH_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.CONSOLIDATED_ORDER_WH_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.CHECKBOX%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:checkbox name="UI_SITE_MGR_FORM" property="<%=property%>" />
        <b>Consolidated Order Warehouse:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.CONSOLIDATED_ORDER_WH_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.CONSOLIDATED_ORDER_WH_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Consolidated Order Warehouse\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
    </td>
    <!--   END  .CONSOLIDATED_ORDER_WH  -------------------------------------------------------------------------------------->

</tr>


<tr>
    <!--   START  BLANKET_PO_NUMBER -------------------------------------------------------------------------------------->
    <% controlName = RefCodeNames.UI_CONTROL.BLANKET_PO_NUMBER;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.BLANKET_PO_NUMBER%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden  styleId="<%=RefCodeNames.UI_CONTROL.BLANKET_PO_NUMBER%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td colspan="4" height="30px" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.BLANKET_PO_NUMBER_LABEL%>"><b>Blanket Po Number:</b> &lt No Default &gt
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.BLANKET_PO_NUMBER_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.BLANKET_PO_NUMBER_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Blanket Po Number\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
    </td>
    <!--   END BLANKET_PO_NUMBER -------------------------------------------------------------------------------------->
</tr>


<tr>    <!--   START  SITE_DATA_FIELDS -------------------------------------------------------------------------------------->
        <% controlName = RefCodeNames.UI_CONTROL.SITE_DATA_FIELDS;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.SITE_DATA_FIELDS%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
        <html:hidden  styleId="<%=RefCodeNames.UI_CONTROL.SITE_DATA_FIELDS%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td colspan="4" height="70px" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.SITE_DATA_FIELDS_LABEL%>" align ="center" valign="middle">
        <b>Site Data Fields</b>
    </td> <tr>
    <!--   START  SITE_DATA_FIELDS -------------------------------------------------------------------------------------->

</tr>

<tr>


    <tr>
        <!--   START  UI_SITE_TAXABLE -------------------------------------------------------------------------------------->
        <% controlName = RefCodeNames.UI_CONTROL.SHARE_BUYER_ORDER_GUIDES;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.SHARE_BUYER_ORDER_GUIDES%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
        <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.SHARE_BUYER_ORDER_GUIDES%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.SHARE_BUYER_ORDER_GUIDES_LBL%>"><b>Share buyer order guides:</b>
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.SHARE_BUYER_ORDER_GUIDES_LBL;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.SHARE_BUYER_ORDER_GUIDES_LBL%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Share buyer order guides\"%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/> </td>
        <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.SHARE_BUYER_ORDER_GUIDES_VAL%>">
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.SHARE_BUYER_ORDER_GUIDES_VAL;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.SHARE_BUYER_ORDER_GUIDES_VAL%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.RADIO%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            Yes&nbsp;<html:radio name="UI_SITE_MGR_FORM" property="<%=property%>" value="Y"/>
            &nbsp;&nbsp;&nbsp;
            No&nbsp;<html:radio name="UI_SITE_MGR_FORM" property="<%=property%>" value="N"/>
        </td>
        <!--   END  UI_SITE_SHARE_BUYER_ORDER_GUIDES  -------------------------------------------------------------------------------------->
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>

<tr>
    <!--   START  SITE_ADDRESS -------------------------------------------------------------------------------------->
        <% controlName = RefCodeNames.UI_CONTROL.ADDRESS;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.ADDRESS%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
        <html:hidden styleId="<%=RefCodeNames.UI_CONTROL.ADDRESS%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>
        <% String tdHeight = "24"; %>
<tr>
    <td colspan="4" class="largeheader"><br>Address</td>
</tr>

<tr>
<td valign="top" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_LABELS1%>"><!-- headers1 -->
    <table cellpadding=0 cellspacing=0 border="0">
        <tr>
            <td height="<%=tdHeight%>"><b>First Name:</b>
                <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_FIRST_NAME_LABEL;%>
                <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
                <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_FIRST_NAME_LABEL%>"/>
                <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
                <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"First Name\"%>"/>
                <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
                <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
            </td>
        </tr>

        <tr><td height="<%=tdHeight%>"><b>Last Name:</b>
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_LAST_NAME_LABEL;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_LAST_NAME_LABEL%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Last Name\"%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
        </td>
        </tr>

        <tr>
            <td height="<%=tdHeight%>"><b>Street Address 1:</b><span class="reqind">*</span>
                <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS1_LABEL;%>
                <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
                <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS1_LABEL%>"/>
                <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
                <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Street Address 1\"%>"/>
                <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
                <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
            </td>

        </tr>
        <tr><td height="<%=tdHeight%>"><b>Street Address 2:</b>
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS2_LABEL;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS2_LABEL%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Street Address 2\"%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
        </td></tr>
        <tr><td height="<%=tdHeight%>"><b>Street Address 3:</b>
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS3_LABEL;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS3_LABEL%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Street Address 3\"%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
        </td></tr>
        <tr><td height="<%=tdHeight%>"><b>Street Address 4:</b>
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS4_LABEL;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS4_LABEL%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Street Address 4\"%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
        </td></tr>
        <tr><td height="<%=tdHeight%>"><b>City:</b><span class="reqind">*</span>
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_CITY_LABEL;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_CITY_LABEL%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"City\"%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
        </td></tr>
        <tr><td height="<%=tdHeight%>"><b>County:</b>
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_COUNTY_LABEL;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_COUNTY_LABEL%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"County\"%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
        </td></tr>
    </table>
</td>
<td valign="top" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_VALUES1%>"><!-- fields1 -->
    <table  cellpadding=0 cellspacing=0 border="0">
        <tr><td height="<%=tdHeight%>">
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_FIRST_NAME_VALUE;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_FIRST_NAME_VALUE%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:text name="UI_SITE_MGR_FORM" property="<%=property%>" size="30"/>
        </td></tr>
        <tr><td height="<%=tdHeight%>">
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_LAST_NAME_VALUE;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_LAST_NAME_VALUE%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:text name="UI_SITE_MGR_FORM" property="<%=property%>" size="30"/></td></tr>
        <tr><td height="<%=tdHeight%>">
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS1_VALUE;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS1_VALUE%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:text name="UI_SITE_MGR_FORM" property="<%=property%>" size="30"/></td></tr>
        <tr><td height="<%=tdHeight%>">
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS2_VALUE;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS2_VALUE%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:text name="UI_SITE_MGR_FORM" property="<%=property%>" size="30"/>
        </td></tr>
        <tr><td height="<%=tdHeight%>">
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS3_VALUE;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS3_VALUE%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:text name="UI_SITE_MGR_FORM" property="<%=property%>" size="30"/></td></tr>
        <tr><td height="<%=tdHeight%>">
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS4_VALUE;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STREET_ADDRESS4_VALUE%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:text name="UI_SITE_MGR_FORM" property="<%=property%>" size="30"/></td></tr>
        <tr><td height="<%=tdHeight%>">
            <% elementName =  RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_CITY_VALUE;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_CITY_VALUE%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:text name="UI_SITE_MGR_FORM" property="<%=property%>" size="30"/></td></tr>
        <tr><td height="<%=tdHeight%>">&lt No Default &gt</td></tr>
    </table>
</td>
<td valign="top" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_LABELS2%>"><!-- headers2 -->
    <table cellpadding=0 cellspacing=0 border="0">
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>"><b>State/Province:</b><span class="reqind">*</span>
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STATE_LABEL;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STATE_LABEL%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"State/Province\"%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
        </td></tr>
        <tr><td height="<%=tdHeight%>"><b>Zip/Postal Code:</b><span class="reqind">*</span></td>
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_ZIP_LABEL;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_ZIP_LABEL%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Zip/Postal Code\"%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
        </tr>
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>"><b>Country:</b><span class="reqind">*</span>
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_COUNTRY_LABEL;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_COUNTRY_LABEL%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Country\"%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/>
        </td></tr>
    </table>
</td>
<td valign="top" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_VALUES2%>"><!-- fields2 -->
    <table cellpadding=0 cellspacing=0 border="0">
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr><td height="<%=tdHeight%>">
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STATE_VALUE;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_STATE_VALUE%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:text name="UI_SITE_MGR_FORM" property="<%=property%>" size="20" maxlength="2" size="2" /></td></tr>
        <tr><td height="<%=tdHeight%>">
            <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_ZIP_VALUE;%>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_ZIP_VALUE%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
            <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>"/>
            <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
            <html:text name="UI_SITE_MGR_FORM" property="<%=property%>"/></td></tr>
        <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
        <tr>
            <td height="<%=tdHeight%>">
                <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_COUNTRY_VALUE;%>
                <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
                <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_COUNTRY_VALUE%>"/>
                <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
                <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>"/>
                <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
                <html:select name="UI_SITE_MGR_FORM" property="<%=property%>">
                    <html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
                    <html:options  collection="ui.country.vector" labelProperty="uiName" property="shortDesc" />
                </html:select>
            </td>
        </tr>
    </table>
</td>
</tr>

<tr>
    <!--   START  OG_COMMENTS -------------------------------------------------------------------------------------->

    <% controlName = RefCodeNames.UI_CONTROL.OG_COMMENTS;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.OG_COMMENTS%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden  styleId="<%=RefCodeNames.UI_CONTROL.OG_COMMENTS%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.OG_COMMENTS_LABEL%>" height="30px"><b>Order Guide Comments:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.OG_COMMENTS_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.OG_COMMENTS_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Order Guide Comments\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/></td>
    <td colspan="3" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.OG_COMMENTS_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.OG_COMMENTS_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.OG_COMMENTS_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXTAREA%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:textarea name="UI_SITE_MGR_FORM" cols="60" property="<%=property%>"/>
    </td>

    <!--   END OG_COMMENTS -------------------------------------------------------------------------------------->
</tr>


<tr>
    <!--   START  SHIPPING_MESSAGE -------------------------------------------------------------------------------------->

    <% controlName = RefCodeNames.UI_CONTROL.SHIPPING_MESSAGE;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.SHIPPING_MESSAGE%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden  styleId="<%=RefCodeNames.UI_CONTROL.SHIPPING_MESSAGE%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>

    <td id="<%=RefCodeNames.UI_CONTROL_ELEMENT.SHIPPING_MESSAGE_LABEL%>" height="30px"><b>Shipping Message:</b>
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.SHIPPING_MESSAGE_LABEL;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.SHIPPING_MESSAGE_LABEL%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=\"Shipping Message\"%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>"/></td>
    <td colspan="3" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.SHIPPING_MESSAGE_VALUE%>">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.SHIPPING_MESSAGE_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.SHIPPING_MESSAGE_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXTAREA%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:textarea name="UI_SITE_MGR_FORM" cols="60" property="<%=property%>"/>
    </td>

    <!--   END SHIPPING_MESSAGE -------------------------------------------------------------------------------------->
</tr>

<tr>
     <% controlName = RefCodeNames.UI_CONTROL.CLONE_WITH_RELATIONSHIPS;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.CLONE_WITH_RELATIONSHIPS%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden  styleId="<%=RefCodeNames.UI_CONTROL.CLONE_WITH_RELATIONSHIPS%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>
    <td colspan="2" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.CLONE_WITH_RELATIONSHIPS_VALUE%>" align="right">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.CLONE_WITH_RELATIONSHIPS_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.CLONE_WITH_RELATIONSHIPS_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.BUTTON%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.CLONE_WITH_RELATIONSHIPS%>"/>
        <html:button property="action">Create Clone (with)</html:button>
    </td>
      <% controlName = RefCodeNames.UI_CONTROL.CLONE_WITHOUT_RELATIONSHIPS;%>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.shortDesc";%>
    <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.CLONE_WITHOUT_RELATIONSHIPS%>"/>
    <% property = "uiPage.uiControlWrapper("+controlName+").uiControlData.statusCd";%>
    <html:hidden  styleId="<%=RefCodeNames.UI_CONTROL.CLONE_WITHOUT_RELATIONSHIPS%>" name="UI_SITE_MGR_FORM" property="<%=property%>"/>
    <td colspan="2" id="<%=RefCodeNames.UI_CONTROL_ELEMENT.CLONE_WITHOUT_RELATIONSHIPS_VALUE%>" align="left">
        <% elementName = RefCodeNames.UI_CONTROL_ELEMENT.CLONE_WITHOUT_RELATIONSHIPS_VALUE;%>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").shortDesc";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_ELEMENT.CLONE_WITHOUT_RELATIONSHIPS_VALUE%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").typeCd";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL_TYPE_CD.BUTTON%>"/>
        <% property = "uiPage.uiControlWrapper("+controlName+").uiControlElement("+elementName+").value";%>
        <html:hidden  name="UI_SITE_MGR_FORM" property="<%=property%>" value="<%=RefCodeNames.UI_CONTROL.CLONE_WITHOUT_RELATIONSHIPS%>"/>
        <html:button property="action">Create Clone (without)</html:button>
    </td>
</tr><tr>
    <td colspan="4">&nbsp;</td>
</tr>

<tr>
<td colspan="4">&nbsp;</td>
</tr><tr><td colspan="4">&nbsp;</td></tr>

<tr>
    <td colspan="4" align="center"><html:submit property="action" value="Save Page Interface"></html:submit></td>
</tr>
</html:form>
</table>

</div>
<script language="Javascript" type="text/javascript">
function createMenu() {
    // create a menu programmatically

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:["<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_LABEL%>","<%=RefCodeNames.UI_CONTROL_ELEMENT.STATUS_VALUE%>"], functionId:"<%=RefCodeNames.UI_CONTROL.STATUS%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu .addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.STATUS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"} } ));
    menu .addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.STATUS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu .addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.STATUS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu .startup();

    menu  = new clw.admin2.UiStatusControlMenu({targetNodeIds:["<%=RefCodeNames.UI_CONTROL_ELEMENT.ID_LABEL%>","<%=RefCodeNames.UI_CONTROL_ELEMENT.NAME_LABEL%>","<%=RefCodeNames.UI_CONTROL_ELEMENT.NAME_VALUE%>"], functionId:"<%=RefCodeNames.UI_CONTROL.ID_AND_NAME%>",targetTabStyle:"targetBox", targetView:"group"});
    menu .addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ID_AND_NAME%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu .addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ID_AND_NAME%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu .addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ID_AND_NAME%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu .startup();

    menu  = new clw.admin2.UiStatusControlMenu({targetNodeIds:["<%=RefCodeNames.UI_CONTROL_ELEMENT.BSC_LABEL%>","<%=RefCodeNames.UI_CONTROL_ELEMENT.BSC_VALUE%>"], functionId:"<%=RefCodeNames.UI_CONTROL.BSC%>",targetTabStyle:"targetBox", targetView:"group"});
    menu .addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.BSC%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu .addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.BSC%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu .addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.BSC%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu .startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_ID%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.ACCOUNT_NAME%>], functionId:"<%=RefCodeNames.UI_CONTROL.ACCOUNT_INF%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ACCOUNT_INF%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ACCOUNT_INF%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.EFF_DATE_LABEL%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.EFF_DATE_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.EFF_DATE%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.EFF_DATE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.EFF_DATE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.EFF_DATE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.EXP_DATE_LABEL%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.EXP_DATE_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.EXP_DATE%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.EXP_DATE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.EXP_DATE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.EXP_DATE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.BUD_REF_NUM_LABEL%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.BUD_REF_NUM_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.BUD_REF_NUM%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.BUD_REF_NUM%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.BUD_REF_NUM%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.BUD_REF_NUM%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    uiSiteDistrBudRefNum = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.DISTR_BUD_REF_NUM_LBL%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.DISTR_BUD_REF_NUM_VAL%>], functionId:"<%=RefCodeNames.UI_CONTROL.DISTR_BUD_REF_NUM%>" , targetTabStyle:"targetBox", targetView:"group"});
    uiSiteDistrBudRefNum.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.DISTR_BUD_REF_NUM%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    uiSiteDistrBudRefNum.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.DISTR_BUD_REF_NUM%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    uiSiteDistrBudRefNum.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.DISTR_BUD_REF_NUM%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    uiSiteDistrBudRefNum.startup();


    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.ERP_NUMBER_LABEL%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.ERP_NUMBER_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.ERP_NUMBER%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ERP_NUMBER%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ERP_NUMBER%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ERP_NUMBER%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.UI_ENABLE_INV_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.ENABLE_INV%>" , targetTabStyle:"targetBox"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ENABLE_INV%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ENABLE_INV%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ENABLE_INV%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.TAXABLE_LABEL%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.TAXABLE_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.TAXABLE%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.TAXABLE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.TAXABLE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.TAXABLE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_TYPE_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.INV_SHOP_TYPE%>" , targetTabStyle:"targetBox"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.INV_SHOP_TYPE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.INV_SHOP_TYPE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.INV_SHOP_TYPE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.INV_SHOP_HOLD_ORDER_UDD_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.INV_SHOP_HOLD_ORDER_UDD%>" , targetTabStyle:"targetBox"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.INV_SHOP_HOLD_ORDER_UDD%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.INV_SHOP_HOLD_ORDER_UDD%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.INV_SHOP_HOLD_ORDER_UDD%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.BYPASS_ORDER_ROUTING_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.BYPASS_ORDER_ROUTING%>" , targetTabStyle:"targetBox"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.BYPASS_ORDER_ROUTING%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.BYPASS_ORDER_ROUTING%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.BYPASS_ORDER_ROUTING%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.TARGET_FACILITY_RANK_LABEL%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.TARGET_FACILITY_RANK_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.TARGET_FACILITY_RANK%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.TARGET_FACILITY_RANK%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.TARGET_FACILITY_RANK%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.TARGET_FACILITY_RANK%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.SITE_LINE_LEVEL_CODE_LABEL%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.SITE_LINE_LEVEL_CODE_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.SITE_LINE_LEVEL_CODE%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.SITE_LINE_LEVEL_CODE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.SITE_LINE_LEVEL_CODE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.SITE_LINE_LEVEL_CODE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();


    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.CONSOLIDATED_ORDER_WH_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.CONSOLIDATED_ORDER_WH%>" , targetTabStyle:"targetBox"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.CONSOLIDATED_ORDER_WH%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.CONSOLIDATED_ORDER_WH%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.CONSOLIDATED_ORDER_WH%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.BLANKET_PO_NUMBER_LABEL%>], functionId:"<%=RefCodeNames.UI_CONTROL.BLANKET_PO_NUMBER%>" , targetTabStyle:"targetBox"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.BLANKET_PO_NUMBER%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.BLANKET_PO_NUMBER%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.SITE_DATA_FIELDS_LABEL%>], functionId:"<%=RefCodeNames.UI_CONTROL.SITE_DATA_FIELDS%>" , targetTabStyle:"targetBox"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.SITE_DATA_FIELDS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.SITE_DATA_FIELDS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.SHARE_BUYER_ORDER_GUIDES_LBL%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.SHARE_BUYER_ORDER_GUIDES_VAL%>], functionId:"<%=RefCodeNames.UI_CONTROL.SHARE_BUYER_ORDER_GUIDES%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.SHARE_BUYER_ORDER_GUIDES%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.SHARE_BUYER_ORDER_GUIDES%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.SHARE_BUYER_ORDER_GUIDES%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_LABELS1%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_VALUES1%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_LABELS2%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.ADDRESS_VALUES2%>], functionId:"<%=RefCodeNames.UI_CONTROL.ADDRESS%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ADDRESS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ADDRESS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.ADDRESS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.OG_COMMENTS_LABEL%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.OG_COMMENTS_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.OG_COMMENTS%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.OG_COMMENTS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.OG_COMMENTS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.OG_COMMENTS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();


    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.SHIPPING_MESSAGE_LABEL%>,<%=RefCodeNames.UI_CONTROL_ELEMENT.SHIPPING_MESSAGE_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.SHIPPING_MESSAGE%>" , targetTabStyle:"targetBox", targetView:"group"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.SHIPPING_MESSAGE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.SHIPPING_MESSAGE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"DEFAULT&nbsp;&nbsp;&nbsp;&nbsp;",  onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.SHIPPING_MESSAGE%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.DEFAULT%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.CLONE_WITH_RELATIONSHIPS_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.CLONE_WITH_RELATIONSHIPS%>" , targetTabStyle:"targetBox"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.CLONE_WITH_RELATIONSHIPS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.CLONE_WITH_RELATIONSHIPS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.startup();

    menu = new clw.admin2.UiStatusControlMenu({targetNodeIds:[<%=RefCodeNames.UI_CONTROL_ELEMENT.CLONE_WITHOUT_RELATIONSHIPS_VALUE%>], functionId:"<%=RefCodeNames.UI_CONTROL.CLONE_WITHOUT_RELATIONSHIPS%>" , targetTabStyle:"targetBox"});
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"ACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.CLONE_WITHOUT_RELATIONSHIPS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.ACTIVE%>"}}));
    menu.addChild(new clw.admin2.UiStatusControlMenuItem({label:"INACTIVE&nbsp;&nbsp;&nbsp;&nbsp;", onClick:function(){ document.getElementById("<%=RefCodeNames.UI_CONTROL.CLONE_WITHOUT_RELATIONSHIPS%>").value="<%=RefCodeNames.UI_CONTROL_STATUS_CD.INACTIVE%>"}}));
    menu.startup();
}

createMenu();

</script>
</body>

</html:html>


