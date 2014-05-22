<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.UserWarrantyDetailMgrForm" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.logic.UserAssetMgrLogic" %>
<%@ page import="java.text.DateFormat" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<style type="text/css">
    .scrollbar {
        scrollbar-3dlight-color: white;
        scrollbar-arrow-color: white;
        scrollbar-highlight-color: white;
        scrollbar-face-color: #8D8E8D;
        scrollbar-shadow-color: #8D8E8D;
        scrollbar-darkshadow-color: white;
        scrollbar-track-color: white;
        overflow: auto;
    }

</style>
<script language="JavaScript1.2"> <!--

function viewPrinterFriendly(loc) {
var prtwin = window.open(loc,"view_docs",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();
  return false;
}

 function actionSubmitTb(formNum, action,fwdPage,sbar) {
     var actions;
     var disp
     var secBar;
     actions=document.forms[formNum]["action"];
     disp=document.forms[formNum]["display"];
     secBar=document.forms[formNum]["secondaryToolbar"];

     for(ii=actions.length-1; ii>=0; ii--) {
       if(actions[ii].value=='hiddenAction') {
         disp.value=fwdPage;
         secBar.value=sbar;
         actions[ii].value=action;
         document.forms[formNum].submit();
         break;
       }
     }
     return false;
     }


-->
</script>

<bean:define id="theForm" name="USER_WARRANTY_DETAIL_MGR_FORM"
             type="com.cleanwise.view.forms.UserWarrantyDetailMgrForm"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0">

<html:form name="USER_WARRANTY_DETAIL_MGR_FORM" action="/userportal/userWarrantyDetail.do" scope="session">
<logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyData">
<bean:define id="warranty" name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyData"/>
<bean:define id="wid" name="warranty" property="warrantyId"/>
<%String messageKey;%>
<tr>
<td width="50%" valign="top">
<table width="100%">
<tr>
    <td width="1%">&nbsp;</td>
    <td width="18%">&nbsp;</td>
    <td width="40%">&nbsp;</td>
    <td width="20%">&nbsp;</td>
    <td width="21%">&nbsp;</td>
</tr>

<tr>
    <td>&nbsp;</td>
    <td>
     <span class="shopassetdetailtxt">
        <b>
            <app:storeMessage key="userWarranty.text.warrantyNumber"/>
        </b>:
    </span>
    </td>
    <td>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:text name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyNumber"/>
        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyNumber">
                <bean:write name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyNumber"/>
            </logic:present>
        </app:notAuthorizedForFunction>

    </td>
    <td><span class="shopassetdetailtxt"><b>
        <app:storeMessage key="userWarranty.text.warrantyType"/>
    </b>:</span></td>
    <td>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:select name="USER_WARRANTY_DETAIL_MGR_FORM" property="typeCd">
                <html:option value="">
                    <app:storeMessage  key="admin.select"/>
                </html:option>
                <logic:present name="Warranty.type.vector">
                    <logic:iterate id="typeCd" name="Warranty.type.vector"
                                   type="com.cleanwise.service.api.value.RefCdData">
                        <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.warrantyType." + ((String)typeCd.getValue()).toUpperCase());%>
                        <html:option value="<%=typeCd.getValue()%>">
                            <% if (messageKey != null) {%>
                            <%=messageKey%>
                            <%} else {%>
                            <%=typeCd.getValue()%>
                            <%}%>
                        </html:option>
                    </logic:iterate>
                </logic:present>
            </html:select>
        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="typeCd">
                <bean:define id="type"  name="USER_WARRANTY_DETAIL_MGR_FORM" property="typeCd" type="java.lang.String"/>
                <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.warrantyType." + type.toUpperCase());
                    if (messageKey != null) {%>
                <%=messageKey%>
                <%} else{%>
                <bean:write name="USER_WARRANTY_DETAIL_MGR_FORM" property="typeCd"/>
                <%}%>
            </logic:present>
        </app:notAuthorizedForFunction>
    </td>

</tr>
<tr>
    <td>&nbsp;</td>
    <td><span class="shopassetdetailtxt"><b>
        <app:storeMessage key="userWarranty.text.warrantyName"/>
    </b>:</span></td>
    <td>

        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:text name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyName"/>
        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyName">
                <bean:write name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyName"/>
            </logic:present>
        </app:notAuthorizedForFunction>

    </td>
    <td><span class="shopassetdetailtxt"><b>
        <app:storeMessage key="userWarranty.text.warrantyStatusCd"/>
    </b>:</span></td>
    <td>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:select name="USER_WARRANTY_DETAIL_MGR_FORM" property="statusCd">
                <html:option value="">
                    <app:storeMessage  key="admin.select"/>
                </html:option>
                <logic:present name="Warranty.status.vector">
                    <logic:iterate id="statusRefCd" name="Warranty.status.vector" type="com.cleanwise.service.api.value.RefCdData">
                        <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.warrantyStatusCd." + ((String)statusRefCd.getValue()).toUpperCase());%>
                        <html:option value="<%=statusRefCd.getValue()%>">
                            <% if (messageKey != null) {%>
                            <%=messageKey%>
                            <%} else {%>
                            <%=statusRefCd.getValue()%>
                            <%}%>
                        </html:option>
                    </logic:iterate>
                </logic:present>
            </html:select>
        </app:authorizedForFunction>

        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="statusCd">
                <bean:define id="status"  name="USER_WARRANTY_DETAIL_MGR_FORM" property="statusCd" type="java.lang.String"/>
                <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.warrantyStatusCd." + status.toUpperCase());
                    if (messageKey != null) {%>
                <%=messageKey%>
                <%} else{%>
                <bean:write name="USER_WARRANTY_DETAIL_MGR_FORM" property="statusCd"/>
                <%}%>
            </logic:present>
        </app:notAuthorizedForFunction>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td colspan="4"><span class="shopassetdetailtxt">
        <b>
            <app:storeMessage key="userWarranty.text.warrantyProvider"/>
        </b>:
       </span>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <%if (((UserWarrantyDetailMgrForm) theForm).managementSource.equals(UserAssetMgrLogic.className)){%>
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyProvider.shortDesc">
                <bean:write name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyProvider.shortDesc"/>
            </logic:present>
            <%} else { %>
            <html:select name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyProviderId">
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
            <%}%>
        </app:authorizedForFunction>

        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyProvider">
                <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyProvider.shortDesc">
                    <bean:write name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyProvider.shortDesc"/>
                </logic:present>
            </logic:present>
        </app:notAuthorizedForFunction>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td colspan="4"><span class="shopassetdetailtxt">
        <b>
            <app:storeMessage key="userWarranty.text.serviceProvider"/>
        </b>:
       </span>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:select name="USER_WARRANTY_DETAIL_MGR_FORM" property="serviceProviderId">
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
        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="serviceProvider">
                <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="serviceProvider.shortDesc">
                    <bean:write name="USER_WARRANTY_DETAIL_MGR_FORM" property="serviceProvider.shortDesc"/>
                </logic:present>
            </logic:present>
        </app:notAuthorizedForFunction>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td><span class="shopassetdetailtxt"><b>
        <app:storeMessage key="userWarranty.text.warrantyDuration"/>
    </b>:</span></td>
    <td colspan="3">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:text size="3" name="USER_WARRANTY_DETAIL_MGR_FORM" property="duration"/>
            <html:select name="USER_WARRANTY_DETAIL_MGR_FORM" property="durationTypeCd">
                <html:option value="">
                    <app:storeMessage  key="admin.select"/>
                </html:option>                  
                <logic:present name="Warranty.duration.type.vector">
                    <logic:iterate id="durationCd" name="Warranty.duration.type.vector"  type="com.cleanwise.service.api.value.RefCdData">
                        <%messageKey = ClwI18nUtil.getMessageOrNull(request, "userWarranty.text.warrantyDurationTypeCd." + ((String) durationCd.getValue()).toUpperCase());%>
                        <html:option value="<%=durationCd.getValue()%>">
                            <% if (messageKey != null) {%>
                            <%=messageKey%>
                            <%} else {%>
                            <%=durationCd.getValue()%>
                            <%}%>
                        </html:option>
                    </logic:iterate>
                </logic:present>
            </html:select>
        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="duration">
                <bean:write name="USER_WARRANTY_DETAIL_MGR_FORM" property="duration"/>
            </logic:present>
            &nbsp;
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="durationTypeCd">
                <bean:define id="durationTypeCd" name="USER_WARRANTY_DETAIL_MGR_FORM" property="durationTypeCd"
                             type="java.lang.String"/>
                <%String durationTypeKey = "userWarranty.text.durationType." + durationTypeCd;%>
                <app:storeMessage key="<%=durationTypeKey%>"/>
            </logic:present>
        </app:notAuthorizedForFunction>
    </td>

</tr>
<tr>
    <td>&nbsp;</td>

    <td colspan="4"><span class="shopassetdetailtxt"><b>
        <app:storeMessage key="userWarranty.text.warrantyCost"/>
    </b>:</span>
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:text name="USER_WARRANTY_DETAIL_MGR_FORM" size="7" property="cost"/>
        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="cost">
                <%=ClwI18nUtil.getPriceShopping(request, ((UserWarrantyDetailMgrForm) theForm).getCost(), "&nbsp;")%>
            </logic:present>
        </app:notAuthorizedForFunction>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <div align="center" class="itemheadmargin">
        <span class="shopitemhead">
    <td colspan="4" valign="top" align="center" class="customerltbkground">
        <span class="shopassetdetailtxt">
        <b>
            <app:storeMessage key="userWarranty.text.warrantyLongDesc"/>
        </b>
        </span>
    </td>
    </span>
    </div>
</tr>
<tr/>
<tr>
    <td>&nbsp;</td>
    <td width="98%" colspan="4">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:textarea rows="7" cols="45" name="USER_WARRANTY_DETAIL_MGR_FORM"
                           property="longDesc"/>
        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM"
                           property="longDesc">
                <bean:write name="USER_WARRANTY_DETAIL_MGR_FORM"
                            property="longDesc"/>
            </logic:present>
        </app:notAuthorizedForFunction>
    </td>
</tr>

<tr>
    <td colspan="5" align="center">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:submit property="action" styleClass="store_fb">
                <app:storeMessage  key="global.action.label.save"/>
            </html:submit>
        </app:authorizedForFunction>
    </td>
</tr>
</table>
</td>
<td width="50%" valign="top" align="center" rowspan="2">
<table width="100%" border="0" cellpadding="0">
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
        <TABLE width="100%" border="0" cellpadding="2" cellspacing=1 style="border:#000000 1px solid">
            <TR>
                <TD class=customerltbkground vAlign=top align=middle colSpan=3>
                   <SPAN class=shopassetdetailtxt>
                        <B><app:storeMessage key="userWarranty.text.note"/></B>
                  </SPAN>
                  <span class="span_img_h_control"><logic:greaterThan name="wid" value="0">
                        <html:button  property="action" styleClass="store_fb" onclick="actionSubmitTb(0,'createNote','t_userWarrantyNoteDetail','f_userSecondaryToolbar');">
                            <app:storeMessage key="global.label.addNote"/>
                        </html:button>
                    </logic:greaterThan></SPAN>
                </TD>
            </tr>
            <tr>
            <tr>
                <td class="shopcharthead" width="40%">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.note.description"/>
                    </div>
                </td>
                <td class="shopcharthead" width="30%">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.note.addDate"/>
                    </div>

                </td>
                <td class="shopcharthead" width="30%">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.note.addBy"/>
                    </div>
                </td>

            </tr>
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyNotes">
                <tr><td colspan="3">
                    <div style="height:200px;width:100%;" class="scrollbar">
                        <table width="100%" border="0" cellpadding="2" cellspacing=1>
                            <logic:iterate id="note" name="USER_WARRANTY_DETAIL_MGR_FORM" property="warrantyNotes"
                                           type="com.cleanwise.service.api.value.WarrantyNoteData" indexId="j">
                                <bean:define id="wnId" name="note" property="warrantyNoteId"/>
                                <tr id="note<%=((Integer)j).intValue()%>">
                                   <td width="42%">&nbsp;
                                        <logic:present name="note" property="shortDesc">
                                            <a href="../userportal/userWarrantyNote.do?action=detail&warrantyNoteId=<%=wnId%>&display=t_userWarrantyNoteDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                                <bean:write name="note" property="shortDesc"/>
                                            </a>
                                        </logic:present>
                                    </td>
                                    <td width="32%">
                                        <logic:present name="note" property="addDate">
                                            <%=ClwI18nUtil.formatDate(request, note.getAddDate(), DateFormat.DEFAULT)%>
                                        </logic:present>
                                    </td>
                                    <td width="26%">
                                        <logic:present name="note" property="addBy">
                                            <bean:write name="note" property="addBy"/>
                                        </logic:present>
                                    </td>
                                </tr>
                            </logic:iterate>
                        </table></div></td></tr>
            </logic:present>
        </table>
    </td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
    <td>

        <TABLE width="100%" border="0" cellpadding="2" cellspacing=1 style="border:#000000 1px solid">
            <TR>
                <TD class=customerltbkground vAlign=top align=middle colSpan=4>
                     <SPAN class=shopassetdetailtxt>
                      <B><app:storeMessage key="userWarranty.text.assocDocs"/></B>
                     </SPAN>
                    <span class="span_img_h_control"><logic:greaterThan name="wid" value="0">
                        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                            <html:button  property="action" styleClass="store_fb" onclick="actionSubmitTb(0,'createContent','t_userWarrantyDocsDetail','f_userSecondaryToolbar');">
                                <app:storeMessage key="global.label.addContent"/>
                            </html:button>
                        </app:authorizedForFunction>
                    </logic:greaterThan></span
                </TD>
            </tr>
            <tr>
                <td class="shopcharthead" width="30%">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.assocDocs.description"/>
                    </div>
                </td>
                <td class="shopcharthead" width="30%">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.assocDocs.fileName"/>
                    </div>
                </td>

                <td class="shopcharthead" width="20%">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.assocDocs.addDate"/>
                    </div>
                </td>

                <td class="shopcharthead" width="20%">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.assocDocs.addBy"/>
                    </div>
                </td>
            </tr>
            <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="contents">
                <tr><td colspan="4">
                    <div style="height:200px;width:100%;" class="scrollbar">
                        <table width="100%" border="0" cellpadding="2" cellspacing=1>

                            <logic:iterate id="contentV" name="USER_WARRANTY_DETAIL_MGR_FORM" property="contents"
                                           type="com.cleanwise.service.api.value.WarrantyContentView" indexId="j">
                                <logic:present name="contentV" property="content">
                                    <logic:present name="contentV" property="warrantyContentData">
                                        <bean:define id="wcId" name="contentV" property="warrantyContentData.warrantyContentId"/>
                                        <tr id="docs<%=((Integer) j).intValue()%>">
                                        <td width="30%">
                                            <logic:present name="contentV" property="content.shortDesc">
                                                <a href="../userportal/userWarrantyContent.do?action=detail&warrantyContentId=<%=wcId%>&display=t_userWarrantyDocsDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                                    <bean:write name="contentV" property="content.shortDesc"/>
                                                </a>
                                            </logic:present>

                                        </td>
                                        <td width="31%"><%
                                            String fileName = "";
                                            if (contentV.getContent().getPath() != null) {
                                                fileName = contentV.getContent().getPath();
                                                if (fileName.indexOf("/") > -1) {
                                                    fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                                                }
                                            }
                                        %>
                                            <%String loc = "../userportal/userWarrantyContent.do?action=readDocs&warrantyContentId=" + wcId + "&display=t_userWarrantyDocsDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar";%>
                                            <a href="#" onclick="viewPrinterFriendly('<%=loc%>');"><%=fileName%>
                                            </a>
                                        </td>
                                        <td width="21%">
                                            <logic:present name="contentV" property="content.addDate">
                                                <%=ClwI18nUtil.formatDate(request, contentV.getContent().getAddDate(), DateFormat.DEFAULT)%>
                                            </logic:present>
                                        </td>
                                        <td width="18%">
                                            <logic:present name="contentV" property="content.addBy">
                                                <bean:write name="contentV" property="content.addBy"/>
                                            </logic:present>
                                        </td>
                                    </logic:present>
                                </logic:present>
                                </tr>
                            </logic:iterate>
                        </table></div></td></tr>
            </logic:present>
        </table>
    </td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<table width="100%" border="0" cellspacing="0">
<tr>
<td width="3%">&nbsp;</td>
<td align="center" valign="top" width="42%">
        <table width="100%" border="0" cellpadding="2" cellspacing="1" style="border:#000000 1px solid">
            <tr>
                <td class="customerltbkground" valign="top" align="middle" colspan="2">
                    <span class="shopassetdetailtxt">
                        <b><app:storeMessage key="userWarranty.text.warrantyStatusHistory"/></b>
                    </span>
                    <span class="span_img_h_control">&nbsp;</span>
                </td>
            </tr>
            <tr>
                <td width="50%" class="shopcharthead" align="center">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.warrantyStatusHistory.statusDate"/>
                    </div>
                </td>
                <td width="50%" class="shopcharthead" align="center">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.warrantyStatusHistory.statusCode"/>
                    </div>
                </td>
            </tr>
            
            <tr>
                <td colspan="2">
                    <div style="height:200;width:100%;" class="scrollbar">
                    <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="statusHistory">
                        <table width="100%" border="0" cellpadding="0" cellspacing="2">
                            <logic:iterate id="statusH" name="USER_WARRANTY_DETAIL_MGR_FORM"
                                           property="statusHistory"
                                           type="com.cleanwise.service.api.value.WarrantyStatusHistData"
                                           indexId="j">
                                <tr id="statusHistory<%=((Integer)j).intValue()%>">
                                    <td width="52%">
                                        <%=ClwI18nUtil.formatDate(request, statusH.getStatusDate(), DateFormat.DEFAULT)%>
                                    </td>
                                    <td width="48%">
                                        <bean:write name="statusH" property="statusCd"/>
                                    </td>
                                </tr>
                            </logic:iterate>
                        </table>
                    </logic:present>
                    </div>
                </td>
            </tr>
    </table>

</td>
<td align="center" width="42%" valign="top">
            <table width="100%" border="0" cellpadding="2" cellspacing="1" style="border:#000000 1px solid">
            <tr>
                <td class="customerltbkground" valign="top" align="middle"  colspan="2"> 
                    <span class="shopassetdetailtxt">
                        <b><app:storeMessage key="userWarranty.text.warrantyAssocAssets"/></b>
                    </span>
                    <span class="span_img_h_control">
                    <logic:greaterThan name="wid" value="0">
                        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                            <html:button  property="action" styleClass="store_fb" onclick="actionSubmitTb(0,'initConfig','t_userAssetWarranty','f_userSecondaryToolbar');">
                                <app:storeMessage key="userWarranty.text.addCategory"/>
                            </html:button>
                        </app:authorizedForFunction>
                    </logic:greaterThan>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="center" class="shopcharthead">
                    <div class="fivemargin">
                        <app:storeMessage key="userWarranty.text.assetCategories"/>
                    </div>
                </td>
            </tr>
        <logic:present name="USER_WARRANTY_DETAIL_MGR_FORM" property="assetWarrantyViewVector">
            <tr>
                <td>
                    <div style="height:200;width:100%;" class="scrollbar">
                        <table width="100%" border="0" cellpadding="0" cellspacing="2">
                            <bean:size id="rescount" name="USER_WARRANTY_DETAIL_MGR_FORM"
                                       property="assetWarrantyViewVector"/>
                            <logic:greaterThan name="rescount" value="0">
                                <logic:iterate id="arrele" name="USER_WARRANTY_DETAIL_MGR_FORM"
                                               property="assetWarrantyCategoryAssoc">
                                    <tr>
                                        <td align="center" width="100%">
                                            <bean:define id="aId" name="arrele" property="assetId"/>
                                            <a href="userWarrantyConfig.do?action=config&assetCategoryId=<%=aId%>&manufFilter=true&display=t_userAssetWarranty&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                                <bean:write name="arrele" property="shortDesc"/>
                                            </a>
                                        </td>
                                    </tr>
                                </logic:iterate>
                            </logic:greaterThan>
                        </table>
                    </div>
                </td>
            </tr>
        </logic:present>
    </table>

</td>
<td width="3%">&nbsp;</td>
</tr>
</table>
</td>
</tr>
<%if(((Integer)wid).intValue()>0){%>
<html:hidden property="warrantyId" value="<%=((Integer)wid).toString()%>"/>
<%}%>
<html:hidden property="action" value="hiddenAction"/>
<html:hidden property="tabs" value="f_userAssetToolbar"/>
<html:hidden property="display" value="t_userWarrantyDetail"/>
<html:hidden property="secondaryToolbar" value="f_userSecondaryToolbar"/>
</logic:present>
</html:form>
</table>
