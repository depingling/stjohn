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

        &nbsp;

    </td>
</tr>

<tr>
    <td class="msbon_bar"><img src="<%=IMGPath%>/cw_spacer.gif" height="5" width="1"></td>
</tr>

<tr>
    <td class="msb_bar" width="<%=Constants.TABLEWIDTH%>">&nbsp;</td>
</tr>

