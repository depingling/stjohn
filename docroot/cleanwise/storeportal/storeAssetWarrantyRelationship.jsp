<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.AssetView" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.text.DateFormat" %>
<%--
  Date: 01.10.2007
  Time: 0:56:02
  @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<html:html>
<div class="text">
<jsp:include flush='true' page="storeWarrantyCtx.jsp"/>

<jsp:include flush='true' page="locateStoreAsset.jsp">
    <jsp:param name="jspFormAction" value="/storeportal/storeAssetWarrantyRelationship.do"/>
    <jsp:param name="jspFormName" value="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"/>
    <jsp:param name="jspSubmitIdent" value=""/>
    <jsp:param name="jspReturnFilterProperty" value="assetFilter"/>
</jsp:include>
<table ID=1477 class="mainbody" width="<%=Constants.TABLEWIDTH%>">

<bean:define id="theForm" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
             type="com.cleanwise.view.forms.StoreAssetWarrantyRelationshipForm"/>
<html:form styleId="1478" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM" action="/storeportal/storeAssetWarrantyRelationship"
           scope="session">
<logic:present name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM" property="warrantyData">
<logic:present name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM" property="editAssetWarranty">

<bean:define id="aid" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
             property="editAssetWarranty.assetWarrantyData.assetWarrantyId"/>
<bean:define id="wid" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM" property="warrantyData.warrantyId"/>

<%AssetView filteredAsset = null;%>
<tr>
    <logic:present name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM" property="locateStoreAssetForm">

        <logic:present name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                       property="locateStoreAssetForm.assetToReturn">
            <bean:define id="filteredAssets" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                         property="locateStoreAssetForm.assetToReturn"
                         type="com.cleanwise.service.api.value.AssetViewVector"/>

            <%
                if (filteredAssets.size() > 0) {
                    filteredAsset = (AssetView) filteredAssets.get(0);
            %>
            <%}%>
        </logic:present>
    </logic:present>

    <td><b>Asset Number</b>:<span class="reqind">*</span></td>
    <td><%if (filteredAsset != null) {%>
        <html:text size="15" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                   property="editAssetWarranty.asset.assetNum" value="<%=filteredAsset.getAssetNumber()%>"/>
        <html:submit property="action" value="Locate Asset" styleClass='smallbutton'/>
        <%} else { %>
        <html:text size="15" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                   property="editAssetWarranty.asset.assetNum"/>
        <html:submit property="action" value="Locate Asset" styleClass='smallbutton'/>
        <%}%>
    </td>
    <td><b>Asset Name</b>:<span class="reqind">*</span></td>
    <td><%if (filteredAsset != null) {%>
        <html:text size="15" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                   property="editAssetWarranty.asset.shortDesc" value="<%=filteredAsset.getAssetName()%>"/>
        <%} else { %>
        <html:text size="15" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                   property="editAssetWarranty.asset.shortDesc"/>
        <%}%>
    </td>
    <td></td>
</tr>
<tr>
    <td><b>Warranty Code</b>:<span class="reqind">*</span></td>
    <td>
        <html:select name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                     property="editAssetWarranty.assetWarrantyData.warrantyCd">
            <html:option value="">
                <app:storeMessage  key="admin.select"/>
            </html:option>
            <html:options collection="Warranty.cds.vector" property="value"/>
        </html:select>
    </td>
    <td><b>Coverage</b>:<span class="reqind">*</span></td>
    <td>
        <html:select name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                     property="editAssetWarranty.assetWarrantyData.coverageInd">
            <html:option value="">
                <app:storeMessage  key="admin.select"/>
            </html:option>
            <html:options collection="Coverage.cds.vector" property="value"/>
        </html:select>
    </td>
</tr>
<tr>
    <td><b>Terms</b>:</td>
    <td colspan="3">
        <html:textarea rows="7" cols="56" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                       property="editAssetWarranty.assetWarrantyData.term"/>
    </td>
</tr>
<logic:greaterThan name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                   property="editAssetWarranty.assetWarrantyData.assetWarrantyId" value="0">
    <tr>
        <td colspan="4" align="center">
            <table ID=1479>
                <tr>
                    <td colspan="2"><b>Note</b>:</td>
                    <td>
                        <a ID=1480 href="../storeportal/storeWarrantyNote.do?action=create&assetWarrantyId=<%=aid%>&warrantyId=<%=wid%>">[Add
                            Note]</a></td>
                </tr>
                <tr bgcolor="#CC3300">
                    <td width="50%">Description</td>
                    <td width="25%">Date Added</td>
                    <td width="25%">Added By</td>
                </tr>

                <logic:present name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                               property="editAssetWarranty.assetWarrantyNotes">
                    <logic:iterate id="note" name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                                   property="editAssetWarranty.assetWarrantyNotes"
                                   type="com.cleanwise.service.api.value.WarrantyNoteData" indexId="j">
                        <bean:define id="wnId" name="note" property="warrantyNoteId"/>
                        <tr id="note<%=((Integer)j).intValue()%>">
                            <td>
                                <a ID=1481 href="../storeportal/storeWarrantyNote.do?action=detail&warrantyNoteId=<%=wnId%>">
                                    <bean:write name="note" property="shortDesc"/>
                                </a>
                            </td>
                            <td><%=ClwI18nUtil.formatDate(request, note.getAddDate(), DateFormat.DEFAULT)%>
                            </td>
                            <td>
                                <bean:write name="note" property="addBy"/>
                            </td>
                        </tr>
                    </logic:iterate>
                </logic:present>
            </table>
        </td>
    </tr>
</logic:greaterThan>
<tr>
    <td colspan="4" align="center">
        <html:submit property="action">
            <app:storeMessage  key="global.action.label.save"/>
        </html:submit>
        <logic:greaterThan name="STORE_ASSET_WARRANTY_RELATIONSHIP_FORM"
                           property="editAssetWarranty.assetWarrantyData.assetWarrantyId" value="0">
            <html:submit property="action">
                <app:storeMessage  key="global.action.label.delete"/>
            </html:submit>
        </logic:greaterThan>
    </td>
</tr>
</logic:present>
</logic:present>
</html:form>
</table>
</div>
</html:html>