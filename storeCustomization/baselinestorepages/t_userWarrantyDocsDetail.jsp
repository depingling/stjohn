<%--
  Date: 05.10.2007
  Time: 1:10:19
--%>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.text.DateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript1.2">
    <!--
    function viewPrinterFriendly(loc) {
        var prtwin = window.open(loc, "view_docs", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
        prtwin.focus();
        return false;
    }
    //-->
</script>



<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<html:html>

<logic:present name="USER_WARRANTY_CONTENT_MGR_FORM">

<bean:define id="wcId" name="USER_WARRANTY_CONTENT_MGR_FORM" property="warrantyContentId"/>
<bean:define id="wId" name="USER_WARRANTY_CONTENT_MGR_FORM" property="warrantyData.warrantyId"/>
<bean:define id="theForm"
             name="USER_WARRANTY_CONTENT_MGR_FORM"
             type="com.cleanwise.view.forms.UserWarrantyContentMgrForm"/>

<html:form name="USER_WARRANTY_CONTENT_MGR_FORM"
           action="/userportal/userWarrantyContent.do"
           scope="session"
           enctype="multipart/form-data">


<table width="100%" border="0" cellpadding="0" cellspacing="0">

<tr>
<td width="3%">&nbsp;</td>
<td width="94%">
<table width="100%" border="0" cellpadding="0" cellspacing="0">

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
        <span class="shopassetdetailtxt">
            <b>
                <app:storeMessage key="userWorkOrder.text.assocDocs.description"/>
            </b>:
        </span>
    </td>
    <td colspan="3">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:text size="55" name="USER_WARRANTY_CONTENT_MGR_FORM" property="shortDesc"/>

        </app:authorizedForFunction>
        <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <logic:present name="USER_WARRANTY_CONTENT_MGR_FORM" property="shortDesc">
                <bean:write name="USER_WARRANTY_CONTENT_MGR_FORM" property="shortDesc"/>
            </logic:present>
        </app:notAuthorizedForFunction>

    </td>
    <td></td>

</tr>
<tr>
    <td></td>
    <td style="padding-top: .7em;padding-bottom: .7em">
        <span class="shopassetdetailtxt">
            <b>
                <app:storeMessage key="userWorkOrder.text.assocDocs.fileName"/>
            </b>:
        </span>
    </td>
    <td colspan="3">
        <logic:present name="USER_WARRANTY_CONTENT_MGR_FORM" property="path">
            <%String loc = "../userportal/userWarrantyContent.do?action=readDocs&warrantyContentId=" + wcId;%>
            <a href="#" onclick="viewPrinterFriendly('<%=loc%>');">
                <bean:write name="USER_WARRANTY_CONTENT_MGR_FORM" property="path"/>
            </a>
        </logic:present>

    </td>
    <td></td>
</tr>

<tr>
    <td colspan="2">
        &nbsp;
    </td>
    <td colspan="3">
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:file name="USER_WARRANTY_CONTENT_MGR_FORM" property="uploadNewFile"/>
        </app:authorizedForFunction>
    </td>
    <td></td>
</tr>

<tr>
    <td colspan="6"> &nbsp; </td>
</tr>

<tr>
    <td colspan="6" width="100%">
        <table align="center" width="100%" cellpadding="1" cellspacing="0">
            <tr class="customerltbkground" valign="top">
                <div align="center" class="itemheadmargin">
                    <span class="shopitemhead">
                         <td width="3%">&nbsp;</td>
                         <td align="center">
                             <b>
                                 <app:storeMessage key="userWorkOrder.text.assocDocs.longDesc"/>
                             </b>
                         </td>
                         <td width="3%">&nbsp;</td>
                    </span>
                </div>
            </tr>

            <tr>
                <td width="3%">&nbsp;</td>
                <td align="center">
                    <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                        <html:textarea rows="7" cols="76" name="USER_WARRANTY_CONTENT_MGR_FORM"
                                       property="longDesc"/>
                    </app:authorizedForFunction>
                    <app:notAuthorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
                        <logic:present name="USER_WARRANTY_CONTENT_MGR_FORM" property="longDesc">
                            <bean:write name="USER_WARRANTY_CONTENT_MGR_FORM" property="longDesc"/>
                        </logic:present>
                    </app:notAuthorizedForFunction>

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
        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR%>">
            <html:submit property="action" styleClass="store_fb">
                <app:storeMessage key="global.action.label.save"/>
            </html:submit>
            <logic:greaterThan name="wcId" value="0">
                <html:submit property="action" styleClass="store_fb">
                    <app:storeMessage key="global.action.label.delete"/>
                </html:submit>
            </logic:greaterThan>
        </app:authorizedForFunction>
    </td>
</tr>

</table>
</td>
<td width="3%">&nbsp;</td>
</tr>

</table>
<html:hidden property="action" value="hiddenAction"/>
<html:hidden property="tabs" value="f_userAssetToolbar"/>
<html:hidden property="display" value="t_userWarrantyDocsDetail"/>
<html:hidden property="secondaryToolbar" value="f_userSecondaryToolbar"/>
</html:form>
</logic:present>

</html:html>
















