<%--
  Date: 01.11.2007
  Time: 2:55:29
--%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<script language="JavaScript1.2">
    <!--
    function viewPrinterFriendly(loc) {
        var prtwin = window.open(loc, "view_docs",
                "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
        prtwin.focus();
        return false;
    }

   function actionSubmit(formNum, action) {
        var actions;
        actions = document.forms[formNum]["action"];
        for (ii = actions.length - 1; ii >= 0; ii--) {
            if (actions[ii].value == 'hiddenAction') {
                actions[ii].value = action;
                document.forms[formNum].submit();
                break;
            }
        }
        return false;
    }
    //-->
</script>
<%
    String browser = (String) request.getHeader("User-Agent");
    String isMSIE = "";
    if (browser != null && browser.indexOf("MSIE") >= 0) {
        isMSIE = "Y";
%>
<script language="JavaScript" src="../externals/calendar.js"></script>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
        marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% } else { %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% } %>
<jsp:include flush='true' page="storeAssetCtx.jsp"/>
<div class="text">
<table ID=1560 width="<%=Constants.TABLEWIDTH%>" class="stpTable" cellpadding="0" cellspacing="0">
<tr>
<td>

<bean:define id="theForm" name="STORE_ASSET_CONTENT_MGR_FORM"     type="com.cleanwise.view.forms.StoreAssetContentMgrForm"/>
<logic:present name="theForm">
<html:form styleId="1561" name="STORE_ASSET_CONTENT_MGR_FORM" action="/storeportal/storeAssetContent.do" scope="session"  enctype="multipart/form-data">
<bean:define id="acId" name="STORE_ASSET_CONTENT_MGR_FORM" property="assetContentId"/>
<bean:define id="aId" name="STORE_ASSET_CONTENT_MGR_FORM" property="asset.assetId"/>

<table ID=1562 width="100%" border="0" cellpadding="0" cellspacing="0" class="mainbody">

<tr>
<td width="3%">&nbsp;</td>
<td width="94%">
<table ID=1563 width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
    <td width="10%">&nbsp;</td>
    <td width="18%">&nbsp;</td>
    <td width="24%">&nbsp;</td>
    <td width="14%">&nbsp;</td>
    <td width="36%">&nbsp;</td>
    <td width="10%">&nbsp;</td>

</tr>

<tr>
    <td></td>
    <td>
            <b>
                Short Descriprion
            </b>:
    </td>
    <td colspan="3">
        <logic:present name="STORE_ASSET_CONTENT_MGR_FORM" property="shortDesc">
            <html:text size="55" name="STORE_ASSET_CONTENT_MGR_FORM" property="shortDesc"/>
        </logic:present>
    </td>
    <td></td>

 </tr>
 <tr>
    <td></td>

    <td>
            <b>
                Version
            </b>:
    </td>
    <td>
        <logic:present name="STORE_ASSET_CONTENT_MGR_FORM" property="version">
            <html:text size="1" name="STORE_ASSET_CONTENT_MGR_FORM" property="version"/>
        </logic:present>
    </td>
    <td  colspan="3"></td>
 </tr>
<tr>
    <td></td>
    <td>
            <b>
                Eff.Date
            </b>:
    </td>
    <td>
        <logic:present name="STORE_ASSET_CONTENT_MGR_FORM" property="effDate">
            <html:text size="10" name="STORE_ASSET_CONTENT_MGR_FORM" property="effDate"/>
             <% if ("Y".equals(isMSIE)) { %>
                <a ID=1564 href="#"
                   onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].S_DATEFROM_CONT_DET_EFF_DATE, document.forms[0].effDate, null, -7300, 7300);"
                   title="Choose Date"><img ID=1565 name="S_DATEFROM_CONT_DET_EFF_DATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                              style="position:relative"
                              onmouseover="window.status='Choose Date';return true"
                              onmouseout="window.status='';return true"></a>
                <% } else { %>
                <a ID=1566 href="javascript:show_calendar('forms[0].effDate');"
                   onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;"
                   title="Choose Date"><img ID=1567 src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
                <% } %>
        </logic:present>
    </td>
    <td>
        <span>
            <b>
                Exp.Date
            </b>:
        </span>
    </td>
    <td>
        <logic:present name="STORE_ASSET_CONTENT_MGR_FORM" property="expDate">
            <html:text size="10" name="STORE_ASSET_CONTENT_MGR_FORM" property="expDate"/>
             <% if ("Y".equals(isMSIE)) { %>
                <a ID=1568 href="#"
                   onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].S_DATEFROM_CONT_DET_EXP_DATE, document.forms[0].expDate, null, -7300, 7300);"
                   title="Choose Date"><img ID=1569 name="S_DATEFROM_CONT_DET_EXP_DATE" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                              style="position:relative"
                              onmouseover="window.status='Choose Date';return true"
                              onmouseout="window.status='';return true"></a>
                <% } else { %>
                <a ID=1570 href="javascript:show_calendar('forms[0].expDate');"
                   onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;"
                   title="Choose Date"><img ID=1571 src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
                <% } %>
        </logic:present>
    </td>
    <td>&nbsp;</td>
</tr>

<tr>
    <td></td>
    <td  style="padding-top: .7em;padding-bottom: .7em">
            <b>
             File Name
            </b>:
    </td>
    <td colspan="3">
        <logic:present name="STORE_ASSET_CONTENT_MGR_FORM" property="path">
            <%String loc = "../storeportal/storeAssetContent.do?action=readDoc&assetContentId=" + acId;%>
            <a ID=1572 href="#" onclick="viewPrinterFriendly('<%=loc%>');">
                <bean:write name="STORE_ASSET_CONTENT_MGR_FORM" property="path"/>
            </a>
        </logic:present>
    </td>
    <td>&nbsp;</td>
</tr>
<tr>
    <td colspan="2">
        &nbsp;
    </td>
    <td colspan="3">
        <html:file name="STORE_ASSET_CONTENT_MGR_FORM" property="uploadNewFile"/>
    </td>
    <td>&nbsp;</td>
</tr>
<tr>
    <td colspan="6"> &nbsp; </td>
</tr>
<tr>
    <td colspan="6" width="100%">
        <table ID=1573 align="center" width="100%" cellpadding="1" cellspacing="0">
            <tr valign="top">
                <div align="center">
                    <span >
                         <td width="3%">&nbsp;</td>
                         <td align="center">
                             <b>
                                 Long Decription
                             </b>
                         </td>
                         <td width="3%">&nbsp;</td>
                    </span>
                </div>
            </tr>

            <tr>
                <td width="3%">&nbsp;</td>
                <td align="center">
                    <b>
                        <logic:present name="STORE_ASSET_CONTENT_MGR_FORM" property="longDesc">
                            <html:textarea rows="7" cols="76" name="STORE_ASSET_CONTENT_MGR_FORM"
                                           property="longDesc"/>
                        </logic:present>
                    </b>
                </td>
                <td width="3%">&nbsp;</td>
            </tr>
        </table>
    </td>
</tr>

<tr>
    <td align="center" colspan="6">&nbsp;</td>
</tr>
<tr>
    <td align="center" colspan="6">
        <html:button property="action"onclick="actionSubmit(0,'save');">
            <app:storeMessage key="global.action.label.save"/>
        </html:button>
        <logic:greaterThan name="acId" value="0">
          <html:button property="action"  onclick="actionSubmit(0,'remove');">
            <app:storeMessage key="global.action.label.delete"/>
        </html:button>
        </logic:greaterThan>
    </td>
</tr>
</table>
</td>
<td width="3%">&nbsp;</td>
</tr>

</table>
<html:hidden property="action" value="hiddenAction"/>
</html:form>
</logic:present>
</td>
</tr>
</table>
</div>
