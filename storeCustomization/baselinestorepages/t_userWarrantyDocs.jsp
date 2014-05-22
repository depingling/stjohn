<%--
  Date: 03.10.2007
  Time: 23:57:16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.service.api.value.WarrantyContentView" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.text.DateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table width="100%" cellpadding="0" cellspacing="0">
    <tr>
        <td> &nbsp;  </td>
    </tr>
    <tr>
        <td>
            <logic:present name="USER_WARRANTY_MGR_FORM" property="contents">
                <bean:size id="rescount" name="USER_WARRANTY_MGR_FORM" property="contents"/>
                <logic:greaterThan name="rescount" value="0">
                    <table width="99%" align=center cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWarranty.text.assocDocs.description"/>
                                </div>
                            </td>
                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWarranty.text.assocDocs.fileName"/>
                                </div>
                            </td>
                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWarranty.text.assocDocs.version"/>
                                </div>
                            </td>
                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWarranty.text.assocDocs.contentTypeCd"/>
                                </div>
                            </td>

                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWarranty.text.assocDocs.addDate"/>
                                </div>
                            </td>

                            <td class="shopcharthead">
                                <div class="fivemargin">
                                    <app:storeMessage key="userWarranty.text.assocDocs.addBy"/>
                                </div>
                            </td>

                        </tr>

                        <logic:iterate id="arrele" name="USER_WARRANTY_MGR_FORM" property="contents" indexId="i">
                            <logic:present name="arrele" property="warrantyContentData">
                                <logic:present name="arrele" property="content">
                                    <bean:define id="eleid" name="arrele"
                                                 property="warrantyContentData.warrantyContentId"/>
                                    <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
                                        <td>

                                            <logic:present name="arrele" property="content.shortDesc">
                                                <a href="../userportal/userWarranty.do?action=docsDetail&warrantyContentId=<%=eleid%>&display=t_userWarrantyDocsDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar">
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
                                            <logic:present name="arrele" property="warrantyContentData.addDate">
                                                <%=ClwI18nUtil.formatDate(request, ((WarrantyContentView) arrele).getWarrantyContentData().getAddDate(), DateFormat.DEFAULT)%>
                                            </logic:present>
                                        </td>
                                        <td>
                                            <logic:present name="arrele" property="warrantyContentData.addBy">
                                                <bean:write name="arrele" property="warrantyContentData.addBy"/>
                                            </logic:present>
                                        </td>
                                    </tr>
                                </logic:present>
                            </logic:present>
                        </logic:iterate>
                    </table>
                </logic:greaterThan>
            </logic:present>
        </td>
    </tr>
</table>