<%--

  Title:        storeWarrantyDetail.jsp
  Description:  page detail
  Purpose:      view of  warranty detail page
  Copyright:    Copyright (c) 2006
  Company:      CleanWise, Inc.
  Date:         20.12.2006
  Time:         20:14:56
  author        Alexander Chickin, TrinitySoft, Inc.

--%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.cleanwise.view.forms.StoreWarrantyDetailForm" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
<!--

function viewPrinterFriendly(loc) {
var prtwin = window.open(loc,"view_docs",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();
  return false;
}

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


-->


</script>

<bean:define id="theForm" name="STORE_WARRANTY_DETAIL_FORM"
             type="com.cleanwise.view.forms.StoreWarrantyDetailForm"/>


<html:html>

<head>
    <link rel="stylesheet" href="../externals/styles.css">
    <title>Application Administrator Home: Warranty</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>


<div class="text">
<jsp:include flush='true' page="storeWarrantyCtx.jsp"/>

<table ID=1507 width="1000" border="0" cellpadding="0" cellspacing="0" class="mainbody">
<html:form styleId="1508" name="STORE_WARRANTY_DETAIL_FORM" action="/storeportal/storeWarrantyDetail" scope="session"
           enctype="multipart/form-data">
<logic:present name="STORE_WARRANTY_DETAIL_FORM" property="warrantyData">
<bean:define id="wid" name="STORE_WARRANTY_DETAIL_FORM" property="warrantyData.warrantyId"/>

<tr>
<td width="52%" valign="top">
<table ID=1509 width="100%">
<tr>
    <td width="3%">&nbsp;</td>
    <td width="30%">&nbsp;</td>
    <td width="24%">&nbsp;</td>
    <td width="16%">&nbsp;</td>
    <td width="22%">&nbsp;</td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><b>Warranty Id </b>:</td>
    <td>
        <bean:write name="STORE_WARRANTY_DETAIL_FORM" property="warrantyData.warrantyId"/>
    </td>

    <td><b>Status</b><span class="reqind">*</span></td>
    <td>    <html:select name="STORE_WARRANTY_DETAIL_FORM" property="statusCd">
        <html:option value="">
            <app:storeMessage  key="admin.select"/>
        </html:option>
        <html:options collection="Warranty.status.vector" property="value"/>
    </html:select></td>
</tr>
<tr>
    <td colspan="5">&nbsp;</td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><b>Warranty Number:</b><span class="reqind">*</span></td>

    <td>
        <html:text name="STORE_WARRANTY_DETAIL_FORM" property="warrantyNumber"/>
    </td>
    <td colspan="2">&nbsp;</td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><b>Warranty Provider:</b><span class="reqind">*</span></td>
    <td colspan="3">
        <html:select name="STORE_WARRANTY_DETAIL_FORM" property="warrantyProviderId">
            <html:option value="">
                <app:storeMessage  key="admin.select"/>
            </html:option>
            <logic:iterate id="provider" name="Warranty.provider.vector"
                           type="com.cleanwise.service.api.value.BusEntityData">
                <html:option value="<%=String.valueOf(provider.getBusEntityId())%>">
                    <bean:write name="provider" property="shortDesc"/>
                </html:option>
            </logic:iterate>
        </html:select>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><b>Service Provider:</b><span class="reqind">*</span></td>
    <td colspan="3">
        <html:select name="STORE_WARRANTY_DETAIL_FORM" property="serviceProviderId">
            <html:option value="">
                <app:storeMessage  key="admin.select"/>
            </html:option>
            <logic:iterate id="provider" name="Warranty.serviceProvider.vector"
                           type="com.cleanwise.service.api.value.BusEntityData">
                <html:option value="<%=String.valueOf(provider.getBusEntityId())%>">
                    <bean:write name="provider" property="shortDesc"/>
                </html:option>
            </logic:iterate>
        </html:select>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><b>Warranty Name:</b><span class="reqind">*</span></td>
    <td>
        <html:text name="STORE_WARRANTY_DETAIL_FORM" property="warrantyName"/>
    </td>
    <td align="center"><b>Type:</b><span class="reqind">*</span></td>
    <td>
        <html:select name="STORE_WARRANTY_DETAIL_FORM" property="typeCd">
            <html:option value="">
                <app:storeMessage  key="admin.select"/>
            </html:option>
            <html:options collection="Warranty.type.vector" property="value"/>
        </html:select>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><b>Duration</b></td>
    <td>
        <html:text size="3" name="STORE_WARRANTY_DETAIL_FORM" property="duration"/>
        <html:select name="STORE_WARRANTY_DETAIL_FORM" property="durationTypeCd">
            <html:option value="">
                <app:storeMessage  key="admin.select"/>
            </html:option>
            <html:options collection="Warranty.duration.type.vector" property="value"/>
        </html:select>
    </td>
    <td><b>Warranty Cost:</b><span class="reqind">*</span></td>
    <td>
        <html:text name="STORE_WARRANTY_DETAIL_FORM" size="7" property="cost"/>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td valign="top"><b>Long Description:</b></td>
    <td colspan="3">
        <html:textarea rows="7" cols="45" name="STORE_WARRANTY_DETAIL_FORM"
                       property="longDesc"/>
    </td>
</tr>
<tr>
    <td></td>
    <td colspan="4" valign="top">&nbsp; </td>
</tr>
<tr>
    <td></td>
    <td align="center" valign="top" colspan="4">
    <table ID=1510 width="100%">
    <tr>
    <td width="50%" valign="top">

<table ID=1511 width="100%" cellpadding="0" cellspacing="2">
            <tr>
                <td colspan="2" align="center"><strong>Status History</strong></td>
            </tr>

            <tr class="tableheaderinfo">
                <td width="50%">Status Date</td>
                <td width="50%">Status Code</td>
            </tr><tr><td colspan="2"> <div style="height:200;width:100%; scrollbar-3dlight-color:white;
	scrollbar-arrow-color:white;
	scrollbar-highlight-color: white;
	scrollbar-face-color:black;
	scrollbar-shadow-color:black;
	scrollbar-darkshadow-color:white;
	scrollbar-track-color: white;
overflow: auto;"> <table ID=1512 width="100%"  border=0 cellpadding="0" cellspacing="2">
            <logic:present name="STORE_WARRANTY_DETAIL_FORM" property="statusHistory">
                <logic:iterate id="statusH" name="STORE_WARRANTY_DETAIL_FORM" property="statusHistory"
                               type="com.cleanwise.service.api.value.WarrantyStatusHistData" indexId="j">
                    <tr id="statusHistory<%=((Integer)j).intValue()%>">
                        <td width="50%" align="center"><%=ClwI18nUtil.formatDate(request, statusH.getStatusDate(), DateFormat.DEFAULT)%>
                        </td>
                        <td width="50%" align="center">
                            <bean:write name="statusH" property="statusCd"/>
                        </td>
                    </tr>
                </logic:iterate>
            </logic:present></table></div></td></tr>
        </table>
    </td>
    <td  width="50%" align="top" valign="top">
          <table ID=1513 width="100%" cellpadding="0" cellspacing="2">
                    <tr>
                        <td  width="55%" align="right"><strong>Assets</strong></td>

                    <td align="right"><a ID=1514 href="storeWarrantyConfig.do?action=initConfig">[Add Category]</a></td>
                </tr>
                    <tr class="tableheaderinfo">
                        <td colspan="2">Categories</td>
                    </tr>
              <logic:present name="STORE_WARRANTY_DETAIL_FORM" property="assetWarrantyViewVector">
                         <bean:size id="rescount" name="STORE_WARRANTY_DETAIL_FORM" property="assetWarrantyViewVector"/>
            <logic:greaterThan name="rescount" value="0">
                    <tbody>
                        <logic:iterate id="arrele" name="STORE_WARRANTY_DETAIL_FORM" property="assetWarrantyCategoryAssoc">
                            <tr>
                                <td align="center" colspan="2">
                                    <bean:define id="aId" name="arrele"  property="assetId"/>
                                    <a ID=1515 href="storeWarrantyConfig.do?action=config&assetCategoryId=<%=aId%>&manufFilter=true">
                                    <bean:write name="arrele" property="shortDesc"/>
                                    </a>
                                </td>
                        </tr>
                        </logic:iterate>
                    </tbody>
            </logic:greaterThan>     </logic:present>
                </table>    </td>
    </tr>
    </table>
</tr>
<tr>
    <td width="3%">&nbsp;</td>
    <td width="30%">&nbsp;</td>
    <td width="24%">&nbsp;</td>
    <td width="16%">&nbsp;</td>
    <td width="22%">&nbsp;</td>
</tr>
<tr>
    <td colspan="5" align="center">
        <html:submit property="action">
            <app:storeMessage  key="global.action.label.save"/>
        </html:submit>
    </td>
    <td>&nbsp;</td>

</tr>
<tr>
    <td colspan="5">&nbsp;</td>
</tr>
</table>
</td>
<td width="48%" valign="top">
<table ID=1516 width="100%" border="0" cellpadding="0">
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>

<tr>
    <td valign="top">
        <logic:greaterThan name="STORE_WARRANTY_DETAIL_FORM"
                           property="warrantyData.warrantyId" value="0">
            <table ID=1517 width="100%" border="0">
                <tr>
                    <td colspan="3" align="center"><b>Notes</b></td>

                </tr>

                <tr class="tableheaderinfo">
                    <td width="50%">Description</td>
                    <td width="25%">Date Added</td>
                    <td width="25%">Added By</td>
                </tr>
                <logic:present name="STORE_WARRANTY_DETAIL_FORM" property="warrantyNotes">
                    <logic:iterate id="note" name="STORE_WARRANTY_DETAIL_FORM" property="warrantyNotes"
                                   type="com.cleanwise.service.api.value.WarrantyNoteData" indexId="j">
                        <bean:define id="wnId" name="note" property="warrantyNoteId"/>
                        <tr id="note<%=((Integer)j).intValue()%>">
                            <td>
                                <a ID=1518 href="../storeportal/storeWarrantyNote.do?action=detail&warrantyNoteId=<%=wnId%>">
                                    <bean:write name="note" property="shortDesc"/>
                                </a>
                            </td>
                            <td>
                                <%=ClwI18nUtil.formatDate(request, note.getAddDate(), DateFormat.DEFAULT)%>
                            </td>
                            <td>
                                <bean:write name="note" property="addBy"/>
                            </td>
                        </tr>
                    </logic:iterate>
                </logic:present>
                <tr>
                    <td height="20">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </logic:greaterThan>
    </td>
</tr>
<tr>
    <td valign="top">
        <table ID=1519 width="100%" border="0" cellspacing="2">
            <tr>
                <td colspan="4" align="center"><b>Associated Documents</b></td>
            </tr>

            <tr class="tableheaderinfo">
                <td>Description</td>
                <td>File Name</td>
                <td>Date Added</td>
                <td>Added By</td>
            </tr>
            <logic:present name="STORE_WARRANTY_DETAIL_FORM" property="contents">
                <logic:iterate id="contentV" name="STORE_WARRANTY_DETAIL_FORM" property="contents"
                               type="com.cleanwise.service.api.value.WarrantyContentView" indexId="j">
                    <logic:present name="contentV" property="warrantyContentData">
                        <logic:present name="contentV" property="content">
                            <bean:define id="wcId" name="contentV" property="warrantyContentData.warrantyContentId"/>

                            <tr id="docs<%=((Integer)j).intValue()%>">
                                <td>
                                    <a ID=1520 href="storeWarrantyContent.do?action=detail&warrantyContentId=<%=wcId%>">
                                        <bean:write name="contentV" property="content.shortDesc"/>
                                    </a>
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
                                    <%String loc="storeWarrantyContent.do?action=readDocs&warrantyContentId="+wcId+"&retCd=detail";%>
                                    <a ID=1521 href="#" onclick="viewPrinterFriendly('<%=loc%>');"><%=fileName%> </a>
                                </td>
                                <td><%=ClwI18nUtil.formatDate(request, contentV.getContent().getAddDate(), DateFormat.DEFAULT)%>
                                </td>
                                <td>
                                    <bean:write name="contentV" property="content.addBy"/>
                                </td>
                            </tr></logic:present>
                    </logic:present>
                </logic:iterate>
            </logic:present>
        </table>
    </td>
</tr>
</table>
</td>
</tr>
<html:hidden property="action" value="hiddenAction"/>
</logic:present>
</html:form>
</table>
</div>
</body>
</html:html>
