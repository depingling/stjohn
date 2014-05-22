<%--
  Date: 03.10.2007
  Time: 20:22:14
--%>
<%@ page import="com.cleanwise.service.api.value.WarrantyNoteData" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<bean:define id="theForm" name="USER_WARRANTY_MGR_FORM" type="com.cleanwise.view.forms.UserWarrantyMgrForm"/>

<table width="100%" cellpadding="0" cellspacing="0">
    <tr>
        <td> &nbsp;  </td>
    </tr>
    <tr>
        <td>
            <logic:present name="USER_WARRANTY_MGR_FORM" property="warrantyNotesAllTypes">
                <bean:size id="rescount" name="USER_WARRANTY_MGR_FORM" property="warrantyNotesAllTypes"/>
                <table width="99%" align=center cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="shopcharthead">
                            <div class="fivemargin">
                                <app:storeMessage key="userWarranty.text.note.description"/>
                            </div>
                        </td>
                        <td class="shopcharthead">
                            <div class="fivemargin">
                                <app:storeMessage key="userWarranty.text.note.typeCd"/>
                            </div>
                        </td>
                        <td class="shopcharthead">
                            <div class="fivemargin">
                                <app:storeMessage key="userWarranty.text.note.addDate"/>
                            </div>
                        </td>
                        <td class="shopcharthead">
                            <div class="fivemargin">
                                <app:storeMessage key="userWarranty.text.note.addBy"/>
                            </div>
                        </td>
                    </tr>


                    <logic:iterate id="arrele" name="USER_WARRANTY_MGR_FORM" property="warrantyNotesAllTypes"
                                   indexId="i">
                        <bean:define id="eleid" name="arrele" property="warrantyNoteId" type="java.lang.Integer"/>
                        <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
                            <td>
                                <logic:present name="arrele" property="shortDesc">
                                    <a href="../userportal/userWarranty.do?action=noteDetail&warrantyNoteId=<%=eleid%>&display=t_userWarrantyNoteDetail&tabs=f_userAssetToolbar&secondaryToolbar=f_userSecondaryToolbar">
                                        <bean:write name="arrele" property="shortDesc"/>
                                    </a>
                                </logic:present>
                            </td>

                            <td>
                                <logic:present name="arrele" property="typeCd">
                                    <bean:write name="arrele" property="typeCd"/>
                                </logic:present>
                            </td>
                            <td>
                                <logic:present name="arrele" property="addBy">
                                    <bean:write name="arrele" property="addBy"/>
                                </logic:present>
                            </td>
                            <td>
                                <logic:present name="arrele" property="addDate">
                                    <%=ClwI18nUtil.formatDate(request, ((WarrantyNoteData) arrele).getAddDate(), DateFormat.DEFAULT)%>
                                </logic:present>
                            </td>
                        </tr>
                    </logic:iterate>

                </table>
            </logic:present>
        </td>
    </tr>
</table>