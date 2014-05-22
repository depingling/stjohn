<%--
  Date: 04.10.2007
  Time: 14:04:25
  @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.value.WarrantyContentView" %>
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
    <table ID=1489 width="<%=Constants.TABLEWIDTH%>" border="0" cellspacing="0" cellpadding="0" class="mainbody">
        <html:form styleId="1490" name="STORE_WARRANTY_CONTENT_MGR_FORM"
                   action="storeportal/storeWarrantyContent.do"
                   scope="session" type="com.cleanwise.view.forms.StoreWarrantyContentMgrForm">


        <tr>
            <td colspan="5"><h3>Content</h3></td>
        </tr>
    </table>

    <logic:present name="STORE_WARRANTY_CONTENT_MGR_FORM" property="findResult">
        <bean:size id="rescount" name="STORE_WARRANTY_CONTENT_MGR_FORM" property="findResult"/>
        Search result count:
        <bean:write name="rescount"/>
        <logic:greaterThan name="rescount" value="0">
            <table class="stpTable_sortable" id="ts1">
                <thead>
                    <tr class=stpTH align=left>
                        <th class="stpTH">Warranty Content Id</th>
                        <th class="stpTH">Content Id</th>
                        <th class="stpTH">Description</th>
                        <th class="stpTH">Type Cd</th>
                        <th class="stpTH">Add By</th>
                        <th class="stpTH">Add Date</th>
                    </tr>
                </thead>
                <tbody>
                    <logic:iterate id="arrele" name="STORE_WARRANTY_CONTENT_MGR_FORM" property="findResult">
                        <logic:present name="arrele" property="warrantyContentData">
                            <logic:present name="arrele" property="content">
                                <bean:define id="eleid" name="arrele" property="warrantyContentData.warrantyContentId"/>
                                <tr class=stpTD>
                                    <td class=stpTD>
                                        <bean:write name="arrele" property="warrantyContentData.warrantyContentId"/>
                                    </td>
                                    <td class=stpTD>
                                        <bean:write name="arrele" property="content.contentId"/>
                                    </td>
                                    <td class=stpTD>
                                        <a ID=1491 href="storeWarrantyContent.do?action=detail&warrantyContentId=<%=eleid%>">
                                            <bean:write name="arrele" property="content.shortDesc"/>
                                        </a>
                                    </td>
                                    <td class=stpTD>
                                        <bean:write name="arrele" property="content.contentTypeCd"/>
                                    </td>
                                    <td class=stpTD>
                                        <logic:present name="arrele" property="warrantyContentData.addDate">
                                            <%=ClwI18nUtil.formatDate(request, ((WarrantyContentView) arrele).getWarrantyContentData().getAddDate(), DateFormat.DEFAULT)%>
                                        </logic:present>
                                    </td>
                                    <td class=stpTD>
                                        <bean:write name="arrele" property="warrantyContentData.addBy"/>
                                    </td>
                                </tr>  </logic:present>
                        </logic:present>
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