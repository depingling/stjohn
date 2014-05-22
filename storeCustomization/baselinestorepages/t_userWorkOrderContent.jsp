<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.WorkOrderContentView" %>
<%@ page import="java.text.DateFormat" %>
<%--
 Date: 16.10.2007
 Time: 6:09:16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table width="100%" cellpadding="0" cellspacing="0">
    <tr>
        <td><jsp:include flush='true' page="<%=ClwCustomizer.getStoreFilePath(request,\"t_userWorkOrderCtx.jsp\")%>"/></td>
    </tr>
    <tr>
        <td>
            <logic:present name="USER_WORK_ORDER_CONTENT_MGR_FORM" property="workOrderContentAllTypes">
                <bean:size id="rescount" name="USER_WORK_ORDER_CONTENT_MGR_FORM" property="workOrderContentAllTypes"/>
                  <table width="99%" align=center cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWorkOrder.text.assocDocs.description"/>
                                </div>
                            </td>
                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWorkOrder.text.assocDocs.fileName"/>
                                </div>
                            </td>
                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWorkOrder.text.assocDocs.version"/>
                                </div>
                            </td>
                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWorkOrder.text.assocDocs.contentTypeCd"/>
                                </div>
                            </td>

                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWorkOrder.text.assocDocs.addDate"/>
                                </div>
                            </td>

                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWorkOrder.text.assocDocs.addBy"/>
                                </div>
                            </td>

                        </tr>

                        <logic:iterate id="arrele" name="USER_WORK_ORDER_CONTENT_MGR_FORM" property="workOrderContentAllTypes" indexId="i">
                            <logic:present name="arrele" property="workOrderContentData">
                                <logic:present name="arrele" property="content">
                                    <bean:define id="eleid" name="arrele"
                                                 property="workOrderContentData.workOrderContentId"/>
                                    <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
                                        <td>

                                            <logic:present name="arrele" property="content.shortDesc">
                                                <a href="../userportal/userWorkOrderContent.do?action=contentDetail&workOrderContentId=<%=eleid%>&display=t_userWorkOrderContentDetail&tabs=f_userWorkOrderToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                                    <bean:write name="arrele" property="content.shortDesc"/>
                                                </a>
                                            </logic:present>

                                        </td>
                                        <td>
                                            <logic:present name="arrele" property="content.path">
                                                <bean:write name="arrele" property="content.path"/>
                                            </logic:present>
                                        </td>
                                        <td>
                                            <logic:present name="arrele" property="content.version">
                                                <bean:write name="arrele" property="content.version"/>
                                            </logic:present>
                                        </td>
                                        <td>
                                            <logic:present name="arrele" property="content.contentTypeCd">
                                                <bean:write name="arrele" property="content.contentTypeCd"/>
                                            </logic:present>
                                        </td>
                                        <td>
                                            <logic:present name="arrele" property="workOrderContentData.addDate">
                                                <%=ClwI18nUtil.formatDate(request, ((WorkOrderContentView) arrele).getWorkOrderContentData().getAddDate(), DateFormat.DEFAULT)%>
                                            </logic:present>
                                        </td>
                                        <td>
                                            <logic:present name="arrele" property="workOrderContentData.addBy">
                                                <bean:write name="arrele" property="workOrderContentData.addBy"/>
                                            </logic:present>
                                        </td>
                                    </tr>
                                </logic:present>
                            </logic:present>
                        </logic:iterate>

                    </table>

            </logic:present>
        </td>
    </tr>
</table>
