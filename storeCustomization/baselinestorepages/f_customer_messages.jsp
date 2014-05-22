<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<table align=center CELLSPACING=0 CELLPADDING=5 width="769" class="tbstd">
<tr>
<td width="5%">&nbsp;</td>
<td class="text" width="80%">
<app:custom pageElement="pages.main.text"/>
</td>

<logic:present name="pages.tips.text">
<td class="customer_home_tiptext">
<app:custom pageElement="pages.tips.text"/>
</td>
</logic:present>
</tr>

</table>


