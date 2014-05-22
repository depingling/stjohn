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

<script language="JavaScript1.2">
<!--
document.onkeypress = freeKeyHit;

function freeKeyHit(evt) {
    var evt = (evt) ? evt : event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;

    if (charCode == 13) {
        actionSubmit(0, 'Save');
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
    var fileName = document.getElementById("documentId").value;
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

<bean:define id="theForm" name="USER_ASSET_CONTENT_MGR_FORM" type="com.cleanwise.view.forms.UserAssetContentMgrForm"/>
<bean:define id="assetcId" name="USER_ASSET_CONTENT_MGR_FORM" property="assetContentId"/>
<bean:define id="assetId" name="USER_ASSET_CONTENT_MGR_FORM" property="assetId"/>


<%
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String selectStr = ClwI18nUtil.getMessageOrNull(request, "global.action.label.select");
    if (selectStr != null) {
        selectStr = "-" + selectStr + "-";
    } else {
        selectStr = "-Select-";
    }
    String messageKey = "";
    String browser = (String) request.getHeader("User-Agent");
    boolean isMSIE = false;
    if (browser != null && browser.indexOf("MSIE") >= 0) {
        isMSIE = true;
    }
%>

<div class="text">
    <table ID=1590 width="769" cellspacing="0" border="0" class="mainbody">
    <html:form styleId="1591" name="USER_ASSET_CONTENT_MGR_FORM" action="/storeportal/masterAssetContent.do"
                                    scope="session" type="com.cleanwise.view.forms.UserAssetContentMgrForm"
                                    enctype="multipart/form-data">
        <tr>
            <td colspan="3">
                <span style="font-size: 14px; font-weight: bold; line-height: 200%;">
                    <app:storeMessage key="userAssets.text.toolbar.assetDoc"/>
                </span>
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">
                <b><app:storeMessage key="userAssets.text.assocDocs.description"/>:</b>
            </td>
            <td width="2%"></td>
            <td>
                <html:text size="80" maxlength="30" name="USER_ASSET_CONTENT_MGR_FORM" property="shortDesc"/>
            </td>
            <td align="left"><span class="reqind">*</span></td>
            <td width="20%" align="left">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">
                <b><app:storeMessage key="userAssets.text.assocDocs.fileName"/>:</b>
            </td>
            <td>&nbsp;</td>           
            <td align="left">
                <logic:present name="USER_ASSET_CONTENT_MGR_FORM" property="path">
                    <% String loc = "../storeportal/masterAssetContent.do?action=readDocs&assetContentId=" + assetcId;%>
                        <a href="#" onclick="viewPrinterFriendly('<%=loc%>');">
                            <bean:write name="USER_ASSET_CONTENT_MGR_FORM" property="path"/>
                        </a>
                </logic:present>
            </td>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
            <td>
                <div id="uploadFile_div">
                    <html:file styleId="documentId" name="USER_ASSET_CONTENT_MGR_FORM" size="60" property="uploadNewFile"/>
                </div>
            </td>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td align="right" style="vertical-align: top">
                <b><app:storeMessage key="userAssets.text.assocDocs.longDesc"/>:</b>
            </td>
            <td>&nbsp;</td>
            <td>
                <html:textarea rows="6" cols="60" name="USER_ASSET_CONTENT_MGR_FORM" property="longDesc"/>
            </td>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="5">&nbsp;</tr>
        </tr>
        <tr>
            <td align="center" colspan="5">
            <%if (isMSIE) {%>
                <html:button property="action" onclick="checkDataAndSend(0,'Save');">
                    <app:storeMessage key="global.action.label.save"/>
                </html:button>
            <%} else {%>    
                <html:button property="action" onclick="actionSubmit(0,'Save');">
                    <app:storeMessage key="global.action.label.save"/>
                </html:button>
            <%}%>
                <logic:greaterThan name="assetcId" value="0">
                    <html:button property="action" onclick="actionSubmit(0,'delete');">
                        <app:storeMessage key="global.action.label.delete"/>
                    </html:button>
                </logic:greaterThan>
            </td>
        </tr>
        
    <html:hidden property="action" value="hiddenAction"/>
    </html:form>
    </table>

</div>





