<%--
  Date: 02.10.2007
  Time: 13:38:29
  Alexander Chickin,Evgeny Vlasov, TrinitySoft, Inc.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.cleanwise.service.api.value.WarrantyNoteData" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<SCRIPT TYPE="text/javascript">
    <!--
    function submitenter(myfield, e)
    {
        var keycode;
        if (window.event) keycode = window.event.keyCode;
        else if (e) keycode = e.which;
        else return true;

        if (keycode == 13)
        {
            var s = document.forms[0].elements['search'];
            if (s != null) s.click();
            return false;
        }
        else
            return true;
    }
    var focusControl = document.getElementById("searchField");
    if (focusControl != undefined && focusControl.type != "hidden" && !focusControl.disabled) {
        focusControl.focus();
    }
    // -->
</SCRIPT>
<div class="text">
    <jsp:include flush='true' page="storeWarrantyCtx.jsp"/>
    <table ID=1525 width="<%=Constants.TABLEWIDTH%>" border="0" cellspacing="0">
        <tr>
            <td>
                <bean:define id="theForm" name="STORE_WARRANTY_NOTE_MGR_FORM"
                             type="com.cleanwise.view.forms.StoreWarrantyNoteMgrForm"/>
                <html:form styleId="1526" name="STORE_WARRANTY_NOTE_MGR_FORM" action="/storeportal/storeWarrantyNote" scope="session">
                    <table ID=1527 width="<%=Constants.TABLEWIDTH%>" border="0" cellspacing="0"  class="mainbody">
                        <tr>
                            <td align="right"><b>Find Note:</b></td>
                            <td>
                                <html:text styleId="searchField" property="searchField"
                                           onkeypress="return submitenter(this,event)"/>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td align="right"><b>Type:</b></td>
                            <td>
                                <html:select name="STORE_WARRANTY_NOTE_MGR_FORM" property="warrantyNoteTypeCd">
                                    <html:option value="">
                                        <app:storeMessage  key="admin.select"/>
                                    </html:option>
                                    <html:options collection="Warranty.note.type.cds" property="value"/>
                                </html:select>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                            </td>
                            <td colspan="2">
                                <html:submit styleId="search" property="action">
                                    <app:storeMessage  key="global.action.label.search"/>
                                </html:submit>
                            </td>
                        </tr>
                    </table>
                </html:form>
            </td>
        </tr>
        <tr>
            <td>
                <logic:present name="STORE_WARRANTY_NOTE_MGR_FORM" property="searchResult">
                    <bean:size id="rescount" name="STORE_WARRANTY_NOTE_MGR_FORM" property="searchResult"/>
                    <table class="stpTable_sortable" id="ts1">
                        <thead>
                            <tr class=stpTH>
                                <th class=stpTH>Warranty Id</th>
                                <th class=stpTH>Asset Warranty Id</th>
                                <th class=stpTH>Short Desc</th>
                                <th class=stpTH>Type Cd</th>
                                <th class=stpTH>Add By</th>
                                <th class=stpTH>Add Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <logic:iterate id="arrele" name="STORE_WARRANTY_NOTE_MGR_FORM" property="searchResult"
                                           indexId="i">
                                <tr class=stpTD>
                                    <td class=stpTD>
                                        <bean:define id="eleid" name="arrele" property="warrantyNoteId"/>
                                        <bean:write name="arrele" property="warrantyId"/>

                                    </td>

                                    <td class=stpTD>
                                        <bean:write name="arrele" property="assetWarrantyId"/>
                                    </td>
                                    <td class=stpTD>
                                        <a ID=1528 href="../storeportal/storeWarrantyNote.do?action=detail&warrantyNoteId=<%=eleid%>">
                                            <bean:write name="arrele" property="shortDesc"/>
                                        </a>
                                    </td>

                                    <td class=stpTD>
                                        <bean:write name="arrele" property="typeCd"/>
                                    </td>
                                    <td class=stpTD>
                                        <logic:present name="arrele" property="addBy">
                                            <bean:write name="arrele" property="addBy"/>
                                        </logic:present>
                                    </td>
                                    <td class=stpTD>
                                        <logic:present name="arrele" property="addDate">
                                            <%=ClwI18nUtil.formatDate(request, ((WarrantyNoteData) arrele).getAddDate(), DateFormat.DEFAULT)%>
                                        </logic:present>
                                    </td>
                                </tr>
                            </logic:iterate>
                        </tbody>
                    </table>
                </logic:present>
            </td>
        </tr>
    </table>
</div>