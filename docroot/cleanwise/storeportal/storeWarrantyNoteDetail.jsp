<%--
  Date: 02.10.2007
  Time: 20:29:11
  Alexander Chickin,Evgeny Vlasov, TrinitySoft, Inc.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<html:html>
    <div class="text">
    <jsp:include flush='true' page="storeWarrantyCtx.jsp"/>
    <table ID=1523 class="mainbody" width="<%=Constants.TABLEWIDTH%>">
    <bean:define id="theForm" name="STORE_WARRANTY_NOTE_MGR_FORM"
                 type="com.cleanwise.view.forms.StoreWarrantyNoteMgrForm"/>
    <html:form styleId="1524" name="STORE_WARRANTY_NOTE_MGR_FORM" action="/storeportal/storeWarrantyNote" scope="session">
        <logic:present name="STORE_WARRANTY_NOTE_MGR_FORM" property="warrantyData">
            <logic:present name="STORE_WARRANTY_NOTE_MGR_FORM" property="noteDetail">
                <bean:define id="wnId" name="STORE_WARRANTY_NOTE_MGR_FORM" property="noteDetail.warrantyNoteId"/>
                <bean:define id="wId" name="STORE_WARRANTY_NOTE_MGR_FORM" property="noteDetail.warrantyId"/>
                <bean:define id="awId" name="STORE_WARRANTY_NOTE_MGR_FORM" property="noteDetail.assetWarrantyId"/>

                <tr>
                    <td><b>Short Description</b>:<span class="reqind">*</span></td>
                    <td>
                        <html:text name="STORE_WARRANTY_NOTE_MGR_FORM" property="noteDetail.shortDesc"/>
                    </td>
                    <td><b>Type Code</b>:<span class="reqind">*</span></td>
                    <td>
                        <html:select name="STORE_WARRANTY_NOTE_MGR_FORM" property="noteDetail.typeCd">
                            <html:option value="">
                                <app:storeMessage  key="admin.select"/>
                            </html:option>
                            <html:options collection="Warranty.note.type.cds" property="value"/>
                        </html:select>
                    </td>
                </tr>

                <tr>
                    <td valign="top"><b>Note</b>:<span class="reqind">*</span></td>
                    <td colspan="3">
                        <html:textarea rows="7" cols="62" name="STORE_WARRANTY_NOTE_MGR_FORM"
                                       property="noteDetail.note"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="4" align="center">
                        <html:submit property="action">
                            <app:storeMessage  key="global.action.label.save"/>
                        </html:submit>
                        <logic:greaterThan name="STORE_WARRANTY_NOTE_MGR_FORM"
                                           property="noteDetail.warrantyNoteId" value="0">
                            <html:submit property="action">
                                <app:storeMessage  key="global.action.label.delete"/>
                            </html:submit>
                        </logic:greaterThan>
                    </td>
                </tr>
                <html:hidden property="warrantyNoteId" value="<%=((Integer)wnId).toString()%>"/>
                <html:hidden property="warrantyId" value="<%=((Integer)wId).toString()%>"/>
                <html:hidden property="assetWarrantyId" value="<%=((Integer)awId).toString()%>"/>
            </logic:present>
        </logic:present>
        </table>
    </html:form>
</html:html>
</div>