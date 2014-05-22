<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.Admin2Tool" %>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/app-interface-config.tld" prefix="ui" %>

<%String configParam = request.getParameter("configMode");%>
<%String formName = request.getParameter("formName");%>
<%String messageKey;%>

<ui:page name="<%=RefCodeNames.UI_PAGE_CD.USER_DETAIL%>" type="<%=RefCodeNames.UI_PAGE_TYPE_CD.USER%>" bean="<%=formName%>" property="uiPage" configMode="<%=configParam%>">

<ui:control name="<%=RefCodeNames.UI_CONTROL.USER_DISTRIBUTOR_LABELS%>" targets="USER_DISTRIBUTOR_LABELS_TARGET">
    <tr>
        <td id="USER_DISTRIBUTOR_LABELS_TARGET" colspan="4">
            <table cellpadding="2" cellspacing="0" border="0" width="100%">
                <tr>

                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.LABEL_HEIGHT_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                        <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.labelHeight"/>:</ui:label></b></td>
                    </ui:element>

                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.LABEL_HEIGHT_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                        <td><ui:text name="<%=formName%>" property="manifestLabelHeight"/></td>
                    </ui:element>


                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.LABEL_WEITH_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                        <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.labelWeith"/>:</ui:label></b></td>
                    </ui:element>

                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.LABEL_WEITH_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.TEXT%>">
                        <td><ui:text name="<%=formName%>" property="manifestLabelWidth"/></td>
                    </ui:element>

                </tr>

                <tr>

                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.LABEL_TYPE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                        <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.labelType"/>:</ui:label></b></td>
                    </ui:element>

                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.LABEL_TYPE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
                        <td>
                            <ui:select name="<%=formName%>" property="manifestLabelType">
                                <html:option value=""><app:storeMessage  key="admin2.select"/></html:option>
                                <logic:present name="<%=Admin2Tool.FORM_VECTORS.MANIFEST_LABEL_TYPE_CD%>">
                                    <logic:iterate id="refCd" name="<%=Admin2Tool.FORM_VECTORS.MANIFEST_LABEL_TYPE_CD%>" type="com.cleanwise.service.api.value.RefCdData">
                                        <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.MANIFEST_LABEL_TYPE_CD." + ((String) refCd.getValue()).toUpperCase());%>
                                        <html:option value="<%=refCd.getValue()%>">
                                            <% if (messageKey != null) {%> <%=messageKey%> <%} else {%> <%=refCd.getValue()%> <%}%>
                                        </html:option>
                                    </logic:iterate>
                                </logic:present>
                            </ui:select>
                        </td>
                    </ui:element>

                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.LABEL_PRINT_MODE_LABEL%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.LABEL%>">
                        <td><b><ui:label><app:storeMessage key="admin2.user.detail.label.labelPrintMode"/>:</ui:label></b></td>
                    </ui:element>

                    <ui:element name="<%=RefCodeNames.UI_CONTROL_ELEMENT.LABEL_PRINT_MODE_VALUE%>" type="<%=RefCodeNames.UI_CONTROL_TYPE_CD.SELECT%>">
                        <td>
                            <ui:select name="<%=formName%>" property="manifestLabelPrintMode">
                                <html:option value=""><app:storeMessage  key="admin2.select"/></html:option>
                                <logic:present name="admin2.manifest_label_print_mode_cd.vector">
                                    <logic:iterate id="refCd"  name="admin2.manifest_label_print_mode_cd.vector" type="com.cleanwise.service.api.value.RefCdData">
                                        <%messageKey = ClwI18nUtil.getMessageOrNull(request, "refcode.MANIFEST_LABEL_MODE_CD." + ((String) refCd.getValue()).toUpperCase());%>
                                        <html:option value="<%=refCd.getValue()%>">
                                            <% if (messageKey != null) {%> <%=messageKey%> <%} else {%> <%=refCd.getValue()%> <%}%>
                                        </html:option>
                                    </logic:iterate>
                                </logic:present>
                            </ui:select>
                        </td>
                    </ui:element>
                </tr>
            </table>
        </td>
    </tr>

</ui:control>
</ui:page>