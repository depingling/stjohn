<%--
  Date: 01.10.2007
  Time: 15:47:04
  @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.value.AssetWarrantyView" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.text.DateFormat" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">
<jsp:include flush='true' page="storeWarrantyCtx.jsp"/>
    <table ID=1482 width="<%=Constants.TABLEWIDTH%>" border="0" cellspacing="0" cellpadding="0" class="mainbody">
        <html:form styleId="1483" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                   action="storeportal/storeAssetWarrantyRelationship.do"
                   scope="session" type="com.cleanwise.view.forms.StoreAssetWarrantyRelationshipForm">


        <tr>
            <td colspan="5"><h3>Relationships</h3></td>
        </tr>
    </table>

    <logic:present name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM" property="configResult">
        <bean:size id="rescount" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM" property="configResult"/>
        Search result count:
        <bean:write name="rescount"/>
        <logic:greaterThan name="rescount" value="0">
            <table class="stpTable_sortable" id="ts1">
                <thead>
                    <tr class=stpTH align=left>
                        <th class="stpTH">Asset Warranty Id</th>
                        <th class="stpTH">Asset Number</th>
                        <th class="stpTH">Asset Name</th>
                        <th class="stpTH">Add By</th>
                        <th class="stpTH">Add Date</th>
                    </tr>
                </thead>
                <tbody>
                    <logic:iterate id="arrele" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM" property="configResult">
                        <bean:define id="eleid" name="arrele" property="assetWarrantyData.assetWarrantyId"/>
                        <tr class=stpTD>
                            <td class=stpTD>
                                <a ID=1484 href="storeAssetWarrantyRelationship.do?action=detail&assetWarrantyId=<%=eleid%>">
                                    <bean:write name="arrele" property="assetWarrantyData.assetWarrantyId"/>
                                </a></td>
                            <td class=stpTD>
                                <bean:write name="arrele" property="asset.assetNum"/>
                            </td>
                            <td class=stpTD>
                                <bean:write name="arrele" property="asset.shortDesc"/>
                            </td>
                            <td class=stpTD>
                                <logic:present name="arrele" property="assetWarrantyData.addBy">
                                    <bean:write name="arrele" property="assetWarrantyData.addBy"/>
                                </logic:present>
                            </td>
                            <td class=stpTD>
                                <logic:present name="arrele" property="assetWarrantyData.addDate">
                                    <%=ClwI18nUtil.formatDate(request, ((AssetWarrantyView) arrele).getAssetWarrantyData().getAddDate(), DateFormat.DEFAULT)%>
                                </logic:present>
                            </td>
                        </tr>
                    </logic:iterate>
                </tbody>
            </table>
        </logic:greaterThan>
    </logic:present>
    <tr>
        <td colspan="5" align="right">
            <html:submit property="action">
                <app:storeMessage  key="admin.button.create"/>
            </html:submit>
        </td>
    </tr>
    </html:form>
</div>