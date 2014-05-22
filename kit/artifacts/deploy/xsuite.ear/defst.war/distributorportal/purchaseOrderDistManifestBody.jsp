<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">

<font color=red>
<html:errors/>
</font>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">

<%
String currentURI = request.getServletPath();
currentURI = currentURI.substring(0,(currentURI.lastIndexOf(".jsp"))) + ".do";
String fullCurrentURI = request.getContextPath() + currentURI;
String detatilAction = "view";
request.setAttribute("detailURI",request.getContextPath() + "/distributorportal/purchaseOrderDistDetail.do");
%>

<html:form name="PO_OP_SEARCH_FORM" action="<%= currentURI %>"
    scope="session" type="com.cleanwise.view.forms.PurchseOrderOpSearchForm">

<bean:define id="columnCount" value="5"/>
<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td colspan="2"><b>Purchase Order Number</b></td>
<td><b>Cubic Size (cubic feet)</b></td>
<td><b>Weight (pounds)</b></td>
</tr>





<%int poIndex = 0;%>
<logic:iterate id="po" name="PO_OP_SEARCH_FORM" property="toManifestList" type="com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView">
 <bean:define id="key"  name="po" property="purchaseOrderData.purchaseOrderId"/>
 <% String linkHref = new String(request.getAttribute("detailURI")+"?action="+detatilAction+"&id=" + key);%>
 <tr>
 <td colspan="<%=columnCount%>"><a href="<%=linkHref%>"><bean:write name="po" property="purchaseOrderData.erpPoNum"/></a></td>
 </tr>
<%int packageIndex = 0;%>
        <logic:iterate id="myManifest" name="po" property="manifestItems" type="com.cleanwise.service.api.value.ManifestItemView">
                 <tr>
                        <td>&nbsp;</td>
                        <td>Package <%=(packageIndex + 1)%></td>
                        <td><html:text name="PO_OP_SEARCH_FORM" property='<%="toManifestListEle["+poIndex+"].manifestItemsElement["+packageIndex+"].cubicSizeString"%>'/></td>
                        <td><html:text name="PO_OP_SEARCH_FORM" property='<%="toManifestListEle["+poIndex+"].manifestItemsElement["+packageIndex+"].weightString"%>'/></td>
                </tr>
                <%packageIndex++;%>
        </logic:iterate>
<%poIndex++;%>
</logic:iterate>
<tr>
 <td colspan="<%=columnCount%>">
        <html:submit property="action">
            <app:storeMessage  key="distributor.button.print.label"/>
        </html:submit>
  </td>
</tr>

</table>
</html:form>
</div>




