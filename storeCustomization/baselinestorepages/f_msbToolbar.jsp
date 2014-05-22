<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<tr width="<%=Constants.TABLEWIDTH%>">
    <td class="msbdef">
        <img src="<%=IMGPath%>/cw_spacer.gif" height="10" width="1"/>
    </td>
</tr>

<tr>
    <td class="msbon_bar">

        <table width=700>
            <tr>
                <td align=left class="subheadergeneric">
                    <app:storeMessage key="msbSites.text.addShipToLocations"/>
                </td>

                <td align=center class="subheadergeneric">
                    <a href="../store/orderSearch.do?action=pending_orders"><app:storeMessage key="msbSites.text.pendingOrders"/></a>
                </td>

                <td align=center class="subheadergeneric">
                    <a href="../store/orderSearch.do?action=search_all_sites_init">
                        <app:storeMessage key="msbSites.text.allOrders"/></a>
                </td>
            </tr>
        </table>

    </td>
</tr>

<tr>
    <td class="msbon_bar"><img src="<%=IMGPath%>/cw_spacer.gif" height="5" width="1"></td>
</tr>

<tr>
    <td class="msb_bar" width="<%=Constants.TABLEWIDTH%>">&nbsp;</td>
</tr>

