<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="CONTRACT_DETAIL_FORM" type="com.cleanwise.view.forms.ContractMgrDetailForm"/>
<bean:define id="contractId" name="CONTRACT_DETAIL_FORM" property="detail.contractId" type="java.lang.Integer"/>
<bean:define id="catalogId" name="CONTRACT_DETAIL_FORM" property="detail.catalogId" type="java.lang.Integer"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<%
        String isMSIE = (String)session.getAttribute("IsMSIE");
        if (null == isMSIE) isMSIE = "";
%>

<html:html>

<head>
<title>Application Administrator Home: Contracts</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals//styles.css">
</head>


<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}
//-->
</script>

<% if ("Y".equals(isMSIE)) { %>
<script language="JavaScript" src="../externals/calendar.js"></script>
<% } else {  %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% }  %>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admContractToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>


<% if ("Y".equals(isMSIE)) { %>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
    marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% }  %>

<div class="text">
<font color=red>
<html:errors/>
</font>


<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="CONTRACT_DETAIL_FORM" action="/adminportal/contractdetail.do" focus='elements["detail.shortDesc"]'
        type="com.cleanwise.view.forms.ContractMgrDetailForm">

<%
        if( null != contractId && 0 != contractId.intValue() ) {
 %>
<%   if(theForm.getNonCatalogItems().size()>0){   %>
    <tr>
    <td colspan="4" align="center"><b>Warning. The contract has items, which do not belong the catalog</b></td>
    </tr>
<% } %>
    <tr>
        <td><b>Catalog ID:</b></td>
        <td>
          <html:text size="3" name="CONTRACT_DETAIL_FORM" property="catalogId" />
          <html:button property="action"
                onclick="popLocate('cataloglocate', 'catalogId', 'catalogName');"
                value="Locate Catalog"/>
        </td>
        <td colspan="2">
          <b>Catalog Name:</b>
           <html:text readonly="true" name="CONTRACT_DETAIL_FORM" property="catalogName" size="30" styleClass="mainbodylocatename"/>
        </td>
    </tr>
        <tr><td colspan=4>&nbsp;</td></tr>
<% } 	%>

        <tr>
        <td colspan="4" class="largeheader">Contract Detail</td>
        </tr>

    <tr>
        <td><b>Contract ID:</b><html:hidden name="CONTRACT_DETAIL_FORM" property="detail.contractId" /></td>
        <td><bean:write name="CONTRACT_DETAIL_FORM" property="detail.contractId" filter="true"/></td>
        <td><b>Contract Name:</b></td>
        <td>
                <html:text name="CONTRACT_DETAIL_FORM" property="detail.shortDesc" size="30" maxlength="30"/>
            <span class="reqind">*</span>
        </td>
    </tr>

        <tr>
        <td><b>Contract Active Date:</b></td>
        <td>
<% if ("Y".equals(isMSIE)) { %>
                <html:text name="CONTRACT_DETAIL_FORM" property="effDate" maxlength="10" />
                        <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DEFSADD2, document.forms[0].effDate, null, -7300, 7300);" title="Choose Date"
                ><img name="DEFSADD2" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
            <span class="reqind">*</span>
                        <br><span class="smalltext">(mm/dd/yyyy)</span>
<% } else {  %>
                <html:text name="CONTRACT_DETAIL_FORM" property="effDate" maxlength="10" />
                        <a href="javascript:show_calendar('forms[0].effDate');" onmouseover="window.status='Choose Date';return true;" onmouseout="window.status='';return true;" title="Choose Date"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
            <span class="reqind">*</span>
                        <br><span class="smalltext">(mm/dd/yyyy)</span>
<% }  %>
        </td>
        <td><b>Contract Status:</b></td>
        <td>
                <html:select name="CONTRACT_DETAIL_FORM" property="detail.contractStatusCd">
                                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                <html:options  collection="Contract.status.vector"
                                        property="value" />
            </html:select><span class="reqind">*</span>
        </td>
    </tr>

        <tr>
        <td><b>Contract Inactive Date:</b></td>
        <td>
<% if ("Y".equals(isMSIE)) { %>
                <html:text name="CONTRACT_DETAIL_FORM" property="expDate" maxlength="10"/>
                        <a href="#" onClick="event.cancelBubble=true; return ShowCalendar(document.forms[0].DEFSADD1, document.forms[0].expDate, null, -7300, 7300);" title="Choose Date"
                ><img name="DEFSADD1" src="../externals/images/showCalendar.gif" width=19 height=19 border=0 align=absmiddle style="position:relative" onmouseover="window.status='Choose Date';return true" onmouseout="window.status='';return true"></a>
            <br><span class="smalltext">(mm/dd/yyyy)</span>
<% } else {  %>
                <html:text name="CONTRACT_DETAIL_FORM" property="expDate" maxlength="10"/>
                        <a href="javascript:show_calendar('forms[0].expDate');" onmouseover="window.status='Date Picker';return true;" onmouseout="window.status='';return true;"><img src="../externals/images/showCalendar.gif" width=19 height=19 border=0></a>
            <br><span class="smalltext">(mm/dd/yyyy)</span>
<% }  %>
        </td>
                <td><b>Freight/Handling Table:</b></td>
                <td>
                <html:select name="CONTRACT_DETAIL_FORM" property="freightTableId">
                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                <html:options  collection="FreightTable.vector"
                                        labelProperty="shortDesc" property="freightTableId" />
            </html:select>
                        <html:submit property="action"><app:storeMessage  key="admin.button.createFreightTable"/></html:submit>
                </td>
        </tr>

        <logic:present name="CONTRACT_DETAIL_FORM" property="currentFreightTable">
        <tr>
                <td colspan="4" align="center">
                        <table width="90%" border="1">
                                <tr>
                                        <td colspan="2">
                                                <b>Current Freight/Handling Table:</b> <bean:write name="CONTRACT_DETAIL_FORM" property="currentFreightTable.shortDesc" />
                                                <bean:define id="freightTableType"	name="CONTRACT_DETAIL_FORM" property="currentFreightTable.freightTableTypeCd" />
                                        </td>
                                        <td colspan="2"><b>Type:</b> <bean:write name="CONTRACT_DETAIL_FORM" property="currentFreightTable.freightTableTypeCd" />
                                        </td>
                                </tr>

                        <logic:present name="CONTRACT_DETAIL_FORM" property="currentFreightTableCriteria">
                                <tr>
                                        <td colspan="4"><b>Freight/Handling Table Criteria:</b>
                                                <bean:size id="criteriaCount" name="CONTRACT_DETAIL_FORM" property="currentFreightTableCriteria" />
                                                <bean:write name="criteriaCount" />
                                        </td>
                                </tr>

                        <%
                                String valueLabelSign = new String("");
                                String freightLabelSign = new String("");
                                String valueSign = new String("");
                                String freightSign = new String ("");
                                String percentageSign = new String("");

                                if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS.equals(freightTableType)) {
                                        valueLabelSign = "Dollars $";
                                        freightLabelSign = "$";
                                        valueSign = "$";
                                        freightSign = "$";
                                        percentageSign = "";
                                } /*else if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.WEIGHT.equals(freightTableType)) {
                                        valueLabelSign = "Weight #";
                                        freightLabelSign = "$";
                                        valueSign = "";
                                        freightSign = "$";
                                        percentageSign = "";
                                }*/ else if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE.equals(freightTableType)) {
                                        valueLabelSign = "Dollars $";
                                        freightLabelSign = "%";
                                        valueSign = "$";
                                        freightSign = "";
                                        percentageSign = "%";
                                }/* else if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.WEIGHT_PERCENTAGE.equals(freightTableType)) {
                                        valueLabelSign = "Weight #";
                                        freightLabelSign = "%";
                                        valueSign = "";
                                        freightSign = "";
                                        percentageSign = "%";
                                }	*/
                        %>
                                <tr>
                                        <td colspan="2" align="center"><b><%=valueLabelSign%></b></td>
                                        <td><b>Freight <%=freightLabelSign%></b></td>
                                        <td><b>Handling <%=freightLabelSign%></b></td>
                                </tr>
                                <tr>
                                        <td><b>Begin <%=valueLabelSign%></b></td>
                                        <td><b>End <%=valueLabelSign%></b></td>
                                        <td><b>&nbsp;</b></td>
                                        <td><b>&nbsp;</b></td>
                                </tr>

                          <logic:iterate id="criteriale" indexId="i" name="CONTRACT_DETAIL_FORM" property="currentFreightTableCriteria" scope="session" type="com.cleanwise.service.api.value.FreightTableCriteriaDescData">
                                <tr>
                                        <td><%=valueSign%><bean:write name="criteriale" property="lowerAmount" />
                                        </td>
                                        <td><%=valueSign%><bean:write name="criteriale" property="higherAmount" />
                                        </td>
                                        <td><%=freightSign%><bean:write name="criteriale" property="freightAmount" /><%=percentageSign%>
                                        </td>
                                        <td><%=freightSign%><bean:write name="criteriale" property="handlingAmount" /><%=percentageSign%>
                                        </td>
                                </tr>
                          </logic:iterate>
                        </logic:present>
                        </table>
                </td>
        </tr>

        </logic:present>


<%
        if( null == contractId || 0 == contractId.intValue() ) {
 %>
        <tr>
                <td colspan="4">&nbsp;</td>
        </tr>

        <tr>
                <td colspan="4" class="largeheader">
                        Create a contract from an existing
            <span class="reqind">*</span>

                </td>
        </tr>

    <tr class=rowa>
            <td>
                        <html:radio name="CONTRACT_DETAIL_FORM" property="createFrom"
                                value="Catalog"><b>Catalog</b></html:radio>
        </td>
                <td><b>Catalog Id:</b></td>
                <td colspan="2">
                        <html:text size="6" name="CONTRACT_DETAIL_FORM" property="catalogId" />
                        <html:button property="action"
                                onclick="popLocate('cataloglocate', 'catalogId', 'catalogName');
                document.CONTRACT_DETAIL_FORM.createFrom[0].checked = true; "
                                value="Locate Catalog"/>
                </td>
        </tr>

    <tr class=rowa>
                <td>&nbsp;</td>
                <td><b>Catalog Name:</b></td>
                <td colspan="2">
                        <html:text readonly="true" name="CONTRACT_DETAIL_FORM" property="catalogName" size="30" styleClass="rowalocatename"/>
                </td>
        </tr>

    <tr class=rowb>
        <td><html:radio name="CONTRACT_DETAIL_FORM" property="createFrom"
                                value="Contract"><b>Contract</b></html:radio>
                </td>
                <td><b>Contract Id:</b></td>
                <td colspan="2">
                        <html:text size="6" name="CONTRACT_DETAIL_FORM" property="parentContractId" />
                        <html:button property="action"
                                onclick="popLocate('contractlocate', 'parentContractId', 'parentContractName');
                        document.CONTRACT_DETAIL_FORM.createFrom[1].checked = true; "
                                value="Locate Contract" />
                </td>
        </tr>

        <tr class=rowb>
                <td>&nbsp;</td>
                <td><b>Contract Name:</b></td>
                <td colspan="2">
                        <html:text readonly="true" name="CONTRACT_DETAIL_FORM" property="parentContractName" size="30" styleClass="rowblocatename"/>
                </td>
        </tr>
<% }  %>

    <tr>
        <td colspan="4" align="center">
                        <html:submit property="action"><app:storeMessage  key="global.action.label.delete"/></html:submit>
                        <html:reset><app:storeMessage  key="admin.button.reset"/></html:reset>
                        <html:submit property="action"><app:storeMessage  key="global.action.label.save"/></html:submit>
       </td>
    </tr>

<%
        if( null != contractId && 0 != contractId.intValue() ) {
%>

        <tr>
                <td colspan="4">


<table width="769" border="0" class="results">
<tr>
<td colspan="11"><b>Contract Entries:</b>
<bean:size id="itemCount"
name="CONTRACT_DETAIL_FORM"
property="itemsDetailCollection"
/>
<bean:write name="itemCount" />
</td>
</tr>

<logic:present name="CONTRACT_DETAIL_FORM" property="itemsDetailCollection">
<tr>
<td><a class="tableheader" href="contractdetail.do?action=sortitems&sortField=cwSKU">Sku</a></td>
<td><a class="tableheader" href="contractdetail.do?action=sortitems&sortField=name">Name</a></td>
<td><a class="tableheader" href="contractdetail.do?action=sortitems&sortField=size">Size</a></td>
<td><a class="tableheader" href="contractdetail.do?action=sortitems&sortField=pack">Pack</a></td>
<td><a class="tableheader" href="contractdetail.do?action=sortitems&sortField=uom">UOM</a></td>
<td><a class="tableheader" href="contractdetail.do?action=sortitems&sortField=manufacturer">Mfg</a></td>
<td><a class="tableheader" href="contractdetail.do?action=sortitems&sortField=manufSKU">Mfg&nbsp;Sku</a></td>
<td><a class="tableheader" href="contractdetail.do?action=sortitems&sortField=category">Category</a></td>
<td><a class="tableheader" href="contractdetail.do?action=sortitems&sortField=dist_cost">Dist Cost</a></td>
<td><a class="tableheader" href="contractdetail.do?action=sortitems&sortField=amount">Price</a></td>
<td><a class="tableheader" href="contractdetail.do?action=sortitems&sortField=amount">Dist Base Cost</a></td>
<td class="tableheader">Select</td>
</tr>

<logic:iterate id="itemele" indexId="i" name="CONTRACT_DETAIL_FORM" property="itemsDetailCollection" scope="session" type="com.cleanwise.service.api.value.ContractItemDescData">

<%
String lrc;
if ( ( i.intValue() % 2 ) == 0 ) {
  lrc = "rowa";
} else {
  lrc = "rowb";
}

%>

<tr class="<%=lrc%>">


<!--
<html:hidden property='<%= "itemDesc[" + i + "].contractItemId" %>'/>
-->
<html:hidden name="CONTRACT_DETAIL_FORM" property="<%=\"inputId[\"+i+\"]\"%>" value="<%=\"\"+itemele.getContractItemId()%>"/>
<td><bean:write name="itemele" property="cwSKU"/>&nbsp;</td>
<td><bean:write name="itemele" property="shortDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="sizeDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="packDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="uomDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="manufacturerCd"/>&nbsp;</td>
<td><bean:write name="itemele" property="manufacturerSKU"/>&nbsp;</td>
<td><bean:write name="itemele" property="categoryDesc"/>&nbsp;</td>
<td>
<html:text size="6" name="CONTRACT_DETAIL_FORM" property="<%=\"distCost[\"+i+\"]\"%>"/>
</td>
<td>
<html:text size="6" name="CONTRACT_DETAIL_FORM" property="<%=\"amount[\"+i+\"]\"%>"/>
</td>
<td>
<html:text size="6" name="CONTRACT_DETAIL_FORM" property="<%=\"distBaseCost[\"+i+\"]\"%>"/>
</td>

<td>
<html:multibox property="selectItems">
  <bean:write name="itemele" property="contractItemId"/>
</html:multibox>
</td>
</tr>



</logic:iterate>
</logic:present>

<tr>
<td align=center colspan=11>
      <html:submit property="action">
        <app:storeMessage  key="admin.button.updatePrice"/>
      </html:submit>
      <html:submit property="action">
        <app:storeMessage  key="admin.button.findItems"/>
      </html:submit>
      <html:submit property="action" value="Non Catalog Items"/>
      <html:submit property="action">
        <app:storeMessage  key="admin.button.remove"/>
      </html:submit>
</td>
</tr>


</table>

                </td>
        </tr>

<% } %>


</html:form>
</table>

</div>

<% if ("Y".equals(isMSIE)) { %>
<script for=document event="onclick()">
<!--
document.all.CalFrame.style.display="none";
//-->
</script>
<% }  %>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>
</html:html>
