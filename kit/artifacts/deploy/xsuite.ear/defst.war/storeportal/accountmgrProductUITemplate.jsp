<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@taglib uri="/WEB-INF/application.tld" prefix="app" %>
<app:checkLogon/>
<html:html>
<head>
<title>Store Administrator: Account. Product UI Template.</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>
<bean:define id="theForm" name="ACCOUNT_MGR_PRODUCT_UI_TEMPLATE_FORM"
type="com.cleanwise.view.forms.AccountMgrProductUITemplateForm"/>
<body bgcolor="#cccccc">
<script type="text/javascript" language="Javascript">
function checkForm(frm) {
try {
    if (frm && frm.elements) {
        var wasErrors = false;
        for (var ik = 0; ik < frm.elements.length; ik++) {
            var el = frm.elements[ik];
            if (el.name.indexOf("].sortOrder") != -1 || el.name.indexOf("].width") != -1) {
                el.style.backgroundColor = '';
                if (isNaN(el.value) == true || parseInt(el.value) != el.value) {
                    el.focus();
                    el.style.backgroundColor = '#ffcccc';
                    wasErrors = true;
                }
            }
        }
    }
    if (wasErrors) {
        alert('You need enter correct integer values in marked field(s)!');
        return false;
    }
    return true;
} catch (err) {
    alert("Was JavaScript error:" + err.description);
    return false;
}
}
</script>
<div class = "text">

<jsp:include flush='true' page="/storeportal/storeAdminNonTilesLayout.jsp"/>

<font color=red><html:errors/></font>
<table id="1549" cellspacing="0" border="0" width="769"  class="mainbody">
<tr>
    <td><b>Account&nbsp;Id:</b></td>
    <td><bean:write name="ACCOUNT_MGR_PRODUCT_UI_TEMPLATE_FORM" property="accountId"/></td>
    <td><b>Name:</b></td>
    <td><bean:write name="ACCOUNT_MGR_PRODUCT_UI_TEMPLATE_FORM" property="accountName"/></td>
</tr>
</table>

<html:form styleId="1550" action="/storeportal/accountmgrProductUITemplate.do" onsubmit="return checkForm(this);">
<html:hidden name="ACCOUNT_MGR_PRODUCT_UI_TEMPLATE_FORM" property="accountId"/>
<table id="1551" cellspacing="0" border="0" xwidth="700"  class="results">
<logic:notEmpty name="ACCOUNT_MGR_PRODUCT_UI_TEMPLATE_FORM" property="data">
<tr>
<td colspan="5">Update Product UI Template</td>
</tr>
<tr>
<td class="tableheader" align="center"><b>Product View CD:</b></td>
<td colspan="3"><html:select property="shopUIProductViewCd" onchange="submit();">
<html:option value="<%=RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_DETAIL%>"><%=RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_DETAIL%></html:option>
<html:option value="<%=RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_DEFAULT%>"><%=RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_DEFAULT%></html:option>
<html:option value="<%=RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_MULTI%>"><%=RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_MULTI%></html:option>
</html:select></td>
<td align="right"><input type=submit name="action" class="smalltext" value="Update"/></td>
</tr>
<tr><td colspan="5">&nbsp;</td></tr>
<tr>
    <td><b>Attribute Name</b></td>
    <td><b>Status</b></td>
    <td><b>Sort&nbsp;order</b></td>
    <td><b>Width</b></td>
    <td><b>Style&nbsp;class</b></td>
</tr>
<logic:iterate name="ACCOUNT_MGR_PRODUCT_UI_TEMPLATE_FORM" property="data"
 id="item" type="com.cleanwise.service.api.value.ProductViewDefData" indexId="i">
<%
    int id = item.getProductViewDefId();
%>
<tr>
<td><bean:write name="item" property="attributename"/></td>
<td><html:select property="<%=\"data[\" + i + \"].statusCd\"%>">
<html:option value="">-- Select --</html:option>
<html:option value="ACTIVE">ACTIVE</html:option>
<html:option value="INACTIVE">INACTIVE</html:option>
</html:select></td>
<td><html:text property="<%=\"data[\" + i + \"].sortOrder\"%>" maxlength="5" size="5"/></td>
<td><html:text property="<%=\"data[\" + i + \"].width\"%>" maxlength="5" size="5"/></td>
<td><html:select property="<%=\"data[\" + i + \"].styleClass\"%>">
<html:option value="">-- Select --</html:option>
<html:option value="textLeft">textLeft</html:option>
<html:option value="textRight">textRight</html:option>
<html:option value="textCenter">textCenter</html:option>
</html:select></td>
</tr>
</logic:iterate>
<tr><td colspan="5">&nbsp;</td></tr>
<tr><td colspan="4"></td>
<td align="right"><input type=submit name="action" class="smalltext" value="Update"/></td>
</tr>

</logic:notEmpty>
</table>
</html:form>
</div>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>
