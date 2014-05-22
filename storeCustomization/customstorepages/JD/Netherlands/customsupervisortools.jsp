

<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<table width="100%">
<tr>
<td width="20%">&nbsp;</td>
<td width="80%" class="contenttext">
    <ul>
        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/05-0912 BSC Voorstel opleidingen ISS V3_.doc")%>'>Opleidingen</a></li>
		<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/combimat 300 E.pdf")%>'>Combimat 300 E</a></li>
		<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/combimat 600 BMS.pdf")%>'>Combimat 600 BMS</a></li>
		<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/combimat 1800.pdf")%>'>Combimat 1800</a></li>
		<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/Instructiekaart swingo 450E.pdf")%>'>Swingo 450 E</a></li>
		<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/Instructiekaart TASKI swingo.pdf")%>'>TASKI Swingo</a></li>
		<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/Instructiekaart TASKI swingo 750 E1250.pdf")%>'>TASKI Swingo 750 E1250</a></li>
    </ul>
</td>
</tr>
</table>
