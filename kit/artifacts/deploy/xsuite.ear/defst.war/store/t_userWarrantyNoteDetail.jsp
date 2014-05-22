<%--
  Date: 03.10.2007
  Time: 21:18:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table width="100%" cellpadding="0" cellspacing="0">
    <html:form name="USER_WARRANTY_NOTE_MGR_FORM" action="/userportal/userWarrantyNote" scope="session">

        <tr>
            <td> &nbsp;  </td>
        </tr>
        <tr>
            <td>
                <logic:present name="USER_WARRANTY_NOTE_MGR_FORM" property="warrantyData">
                    <logic:present name="USER_WARRANTY_NOTE_MGR_FORM" property="noteDetail">
                        <bean:define id="wnId" name="USER_WARRANTY_NOTE_MGR_FORM" property="noteDetail.warrantyNoteId"/>
                        <bean:define id="wId" name="USER_WARRANTY_NOTE_MGR_FORM" property="noteDetail.warrantyId"/>
                        <bean:define id="awId" name="USER_WARRANTY_NOTE_MGR_FORM"
                                     property="noteDetail.assetWarrantyId"/>
                        <table align="center" width="99%" cellpadding="0" cellspacing="0">


                            <tr>
                                <td class="customerltbkground" valign="top" colspan="3">
                                    <div align="center" class="itemheadmargin">
                    <span class="shopitemhead">
                        <table align="center" width="100%" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <b><app:storeMessage key="userWarranty.text.note.description"/>:</b>
                                    <span class="reqind">*</span>&nbsp;
                                    <html:text name="USER_WARRANTY_NOTE_MGR_FORM" property="noteDetail.shortDesc"/>
                                </td>
                                <!--
                                <td><b>
                                    <app:storeMessage key="userWarranty.text.note.typeCd"/>
                                </b>:
                                    <html:select name="USER_WARRANTY_NOTE_MGR_FORM" property="noteDetail.typeCd">
                                        <html:option value="">
                                            <app:storeMessage  key="admin.select"/>
                                        </html:option>
                                        <html:options collection="Warranty.note.type.cds" property="value"/>
                                    </html:select>
                                </td>
                                -->
                            <td></td>
                            </tr>
                        </table>
                    </span>
                                    </div>
                                </td>

                            </tr>
                            <tr>
                                <td width="3%"></td>
                                
                                <td>
                                    <div style="vertical-align: top;
                                                 text-transform: uppercase;
                                                 font-weight: bold;">
                                            <app:storeMessage key="userWarranty.text.toolbar.warrantyNotes"/>:
                                    <span class="reqind">*</span>
                                    </div>
                                </td>
                                
                                <td align="center">
                                    <html:textarea rows="7" cols="62" name="USER_WARRANTY_NOTE_MGR_FORM"
                                                   property="noteDetail.note"/>
                                </td>
                                <td width="3%"></td>
                            </tr>

                            <tr>
                                <td colspan="4" align="center">
                                    <html:submit property="action" styleClass="store_fb">
                                        <app:storeMessage  key="global.action.label.save"/>
                                    </html:submit>
                                    <logic:greaterThan name="USER_WARRANTY_NOTE_MGR_FORM"
                                                       property="noteDetail.warrantyNoteId" value="0">
                                        <html:submit property="action" styleClass="store_fb">
                                            <app:storeMessage  key="global.action.label.delete"/>
                                        </html:submit>
                                    </logic:greaterThan>
                                </td>
                            </tr>

                        </table>
                    </logic:present>
                </logic:present>
            </td>
        </tr>
        <html:hidden property="noteDetail.typeCd" value="<%=RefCodeNames.WARRANTY_NOTE_TYPE_CD.UNKNOWN%>"/>
        <html:hidden property="action" value="hiddenAction"/>
        <html:hidden property="tabs" value="f_userAssetToolbar"/>
        <html:hidden property="display" value="t_userWarrantyNoteDetail"/>
        <html:hidden property="secondaryToolbar" value="f_userSecondaryToolbar"/>
    </html:form>
</table>