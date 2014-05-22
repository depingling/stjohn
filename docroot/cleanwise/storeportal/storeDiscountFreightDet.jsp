<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.CatalogData" %>
<%@ page import="com.cleanwise.service.api.value.CatalogDataVector" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_DISCOUNT_FREIGHT_FORM" type="com.cleanwise.view.forms.StoreDiscountFreightMgrForm"/>
<bean:define id="freightTableType" name="STORE_DISCOUNT_FREIGHT_FORM" property="detail.freightTableTypeCd" />
<bean:define id="freightTableId" name="STORE_DISCOUNT_FREIGHT_FORM" property="detail.freightTableId" type="Integer" />
<bean:define id="mlaStore" name="STORE_DISCOUNT_FREIGHT_FORM" property="mlaStore" type="Boolean" />

<script language="JavaScript1.2">
function deleteLine(lineId){

    document.forms[0].actionOveride.value = "delLine";
    document.forms[0].lineToDelete.value=lineId;
    document.forms[0].submit();
}
</script>

<%
    String createfrom = new String("");
    createfrom = request.getParameter("createfrom");
    if(null == createfrom) {
        createfrom = new String("");
    }

    boolean isaMLAStore = true;
    if (null != mlaStore) {
        isaMLAStore = mlaStore.booleanValue();
    }

%>


<div class="text">

<% if (! isaMLAStore) {  %>
<jsp:include flush='true' page="locateStoreDistributor.jsp">
    <jsp:param name="jspFormAction"              value="/storeportal/storeDiscountFreightDet.do" />
    <jsp:param name="jspFormName"                value="STORE_DISCOUNT_FREIGHT_FORM" />
    <jsp:param name="jspSubmitIdent"             value="" />
    <jsp:param name="jspReturnFilterPropertyAlt" value="distDummyConvert"/>
    <jsp:param name="jspReturnFilterProperty"    value="distFilter"/>
</jsp:include>
<% }  // end if%>


<table ID=796 border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form styleId="797" name="STORE_DISCOUNT_FREIGHT_FORM" action="/storeportal/storeDiscountFreightDet.do"
    scope="session" type="com.cleanwise.view.forms.StoreDiscountFreightMgrSearchForm">

    <html:hidden property="actionOveride" value=""/>
    <html:hidden property="lineToDelete" value=""/>

    <tr>
        <td><b>Table ID:</b><html:hidden name="STORE_DISCOUNT_FREIGHT_FORM" property="detail.freightTableId" /></td>
        <td>
            <bean:write name="STORE_DISCOUNT_FREIGHT_FORM" property="detail.freightTableId" filter="true"/>
            <input type="hidden" name="createfrom" value="<%=createfrom%>">
        </td>
        <td><b>Table Name:</b></td>
        <td>
            <html:text name="STORE_DISCOUNT_FREIGHT_FORM" property="detail.shortDesc" size="30" maxlength="30"/>
            <span class="reqind">*</span>
        </td>
    </tr>

    <% if (! isaMLAStore) {  %>
    <tr>
        <td><b>Distributor:</b></td>
        <td colspan='3'>
        <jsp:include flush='true' page="locateStoreDistributorButtons.jsp">
            <jsp:param name="jspFormName" value="STORE_DISCOUNT_FREIGHT_FORM" />
            <jsp:param name="jspReturnFilterProperty" value="distFilter" />
        </jsp:include>
        </td>
    </tr>
    <tr>
        <td><b>Catalogs:</b></td>
        <td colspan='3'>
            <%
            final int maxAssocCatalogsToDisplay = 25;
            StringBuilder assocCatalogsDisplay = new StringBuilder();
            int assocCatalogsCount = 0;
            CatalogDataVector assocCatalogs = theForm.getCatalogsAssociatedWithFreightTable();
            if (assocCatalogs != null) {
                assocCatalogsCount = assocCatalogs.size();
                for (int i = 0; i < assocCatalogsCount && i < maxAssocCatalogsToDisplay; ++i) {
                    CatalogData assocCatalog = (CatalogData)assocCatalogs.get(i);
                    if (i > 0) {
                        assocCatalogsDisplay.append(",");
                    }
                    assocCatalogsDisplay.append("&#60;");
                    assocCatalogsDisplay.append(assocCatalog.getShortDesc());
                    assocCatalogsDisplay.append("&#62;");
                }
            }
            if (assocCatalogsCount > maxAssocCatalogsToDisplay) {
                assocCatalogsDisplay.append("...");
            }
            assocCatalogsDisplay.append("(");
            assocCatalogsDisplay.append(String.valueOf(assocCatalogsCount));
            assocCatalogsDisplay.append(" catalogs configured)");
            %>
            <%=assocCatalogsDisplay.toString()%>
        </td>
    </tr>
    <% } // end if%>

    <tr>
        <td><b>Table Type:</b></td>
        <td>
            <html:hidden property="change" value="" />
            <html:select name="STORE_DISCOUNT_FREIGHT_FORM" property="detail.freightTableTypeCd" onchange="document.forms[0].change.value='type'; document.forms[0].submit();">
                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                <html:options collection="Discount.FreightTable.type.vector" property="value" />
            </html:select>
            <span class="reqind">*</span>
        </td>
        <td><b>Table Status:</b></td>
        <td>
            <html:select name="STORE_DISCOUNT_FREIGHT_FORM" property="detail.freightTableStatusCd">
                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                <html:options collection="Discount.FreightTable.status.vector" property="value" />
            </html:select>
            <span class="reqind">*</span>
        </td>
    </tr>
    <tr>
        <td colspan="4">
            <table ID=798 width="769" border="1" class="results">
                <tr>
                    <td colspan="<%=(isaMLAStore ? 5 : 11)%>"><b>Table Criteria:</b>
                        <bean:size id="criteriaCount" name="STORE_DISCOUNT_FREIGHT_FORM" property="orgCriteriaList" />
                        <bean:write name="criteriaCount" />
                    </td>
                </tr>
                <% if (!isaMLAStore) {  %>
                <logic:present name="STORE_DISCOUNT_FREIGHT_FORM" property="criteriaList">
                <%
                        String valueLabelSign = new String("");
                        String discountLabelSign = new String("");
                        String valueSign = new String("");
                        String discountSign = new String("");

                        if (!"".equals(freightTableType)) {
                            if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS.equals(freightTableType)) {
                                valueLabelSign = "Dollars $";
                                discountLabelSign = "Discount $";
                                valueSign = "$";
                                discountSign = "$";
                            } else if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE.equals(freightTableType)) {
                                valueLabelSign = "Dollars $";
                                discountLabelSign = "Discount %";
                                valueSign = "$";
                                discountSign = "%";
                            }
                %>
                <tr>
                    <td rowspan="2"><b>Description</b></td>
                    <td colspan="2" align="center"><b><%=valueLabelSign%></b></td>
                    <td rowspan="2"><b><%=discountLabelSign%></b></td>
                </tr>
                <tr>
                    <td><b>Begin <%=valueLabelSign%></b></td>
                    <td><b>End <%=valueLabelSign%></b></td>
                </tr>
                <% } else  {
                    valueSign = "";
                %>
                <tr>
                    <td><b>Description</b></td>
                    <td><b>Begin #</b></td>
                    <td><b>End #</b></td>
                    <td><b>Discount #</b></td>
                </tr>
                <% }  %>

                <logic:iterate id="criteriale" indexId="i" name="STORE_DISCOUNT_FREIGHT_FORM" property="criteriaList" scope="session" type="com.cleanwise.service.api.value.FreightTableCriteriaDescData">
                <tr>
                    <html:hidden property='<%= "criteria[" + i + "].freightTableCriteriaId" %>'/>
                    <td>
                        <html:text size="35" maxlength="255" property='<%= "criteria[" + i + "].shortDesc" %>'/>
                    </td>
                    <td>
                        <b><%=valueSign%></b>
                        <html:text size="6" maxlength="12" property='<%= "criteria[" + i + "].lowerAmount" %>'/>
                    </td>
                    <td>
                        <b><%=valueSign%></b>
                        <html:text size="6" maxlength="12" property='<%= "criteria[" + i + "].higherAmount" %>'/>
                    </td>
                    <td>
                        <b><%=discountSign%></b>
                        <html:text size="5" maxlength="12" property='<%= "criteria[" + i + "].discount" %>'/>
                    </td>
                </tr>
                </logic:iterate>
                </logic:present>

                <% } else {  %>
                <logic:present name="STORE_DISCOUNT_FREIGHT_FORM" property="criteriaList">
                <%
                String valueLabelSign = new String("");
                String discountLabelSign = new String("");
                String valueSign = new String("");
                String discountSign = new String("");

                if (!"".equals(freightTableType)) {
                    if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS.equals(freightTableType)) {
                        valueLabelSign = "Dollars $";
                        discountLabelSign = "Discount $";
                        valueSign = "$";
                        discountSign = "$";
                    } else if (RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE.equals(freightTableType)) {
                        valueLabelSign = "Dollars $";
                        discountLabelSign = "Discount %";
                        valueSign = "$";
                        discountSign = "%";
                    }
                %>
                <tr>
                    <td colspan="2" align="center"><b><%=valueLabelSign%></b></td>
                    <td><b>Discount <%=valueSign%></b></td>
                </tr>
                <tr>
                    <td><b>Begin <%=valueLabelSign%></b></td>
                    <td><b>End <%=valueLabelSign%></b></td>
                    <td><b>&nbsp;</b></td>
                </tr>
                <% } else  {
                    valueSign = "";
                %>
                <tr>
                    <td><b>Begin #</b></td>
                    <td><b>End #</b></td>
                    <td><b>Discount #</b></td>
                </tr>
                <% } %>

                <logic:iterate id="criteriale" indexId="i" name="STORE_DISCOUNT_FREIGHT_FORM" property="criteriaList" scope="session" type="com.cleanwise.service.api.value.FreightTableCriteriaDescData">

                <tr>
                    <html:hidden property='<%= "criteria[" + i + "].freightTableCriteriaId" %>'/>
                    <td>
                        <b><%=valueSign%></b>
                        <html:text size="15" maxlength="12" property='<%= "criteria[" + i + "].lowerAmount" %>'/>
                    </td>
                    <td>
                        <b><%=valueSign%></b>
                        <html:text size="15" maxlength="12" property='<%= "criteria[" + i + "].higherAmount" %>'/>
                    </td>
                    <td>
                        <b><%=discountSign%></b>
                        <html:text size="5" maxlength="12" property='<%= "criteria[" + i + "].discount" %>'/>
                    </td>
                    <td>
                        <a ID=799 href="javascript:deleteLine('<%=i%>')" alt="delete this freight handling criteria">Del</a>
                    </td>
                </tr>

                </logic:iterate>
                </logic:present>

            <% } // end if-else %>

            </table>
        </td>
    </tr>
    <tr>
        <td colspan="4" align="center">
        <% if(freightTableId.intValue()>0) { %>
            <html:submit property="action">Reload</html:submit>
            <% } %>
            <html:submit property="action"><app:storeMessage  key="global.action.label.save"/></html:submit>
            <html:submit property="action"><app:storeMessage  key="admin.button.addMoreCriteria"/></html:submit>
       </td>
    </tr>

</html:form>
</table>
</div>
