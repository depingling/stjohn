

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
        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/CarpetCareSpottingGuideCards.pdf")%>'>Carpet Care Spotting Guide Cards</a></li>
		<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/ChemicalConversionChart.pdf")%>'>Chemical Conversion Chart</a></li>
		<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/Cht052SignatureEng.pdf")%>'>Cht052 Signature Eng</a></li>
		<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/RTDApplicationWallChartEngSpan.pdf")%>'>RTD Application Wall Chart Eng Span</a></li>
		<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/SignatureWallChartKorean.pdf")%>'>Signature Wall Chart Korean</a></li>
		<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/SignatureWallChartPortugese.pdf")%>'>Signature Wall Chart Portugese</a></li>
    </ul>
</td>
</tr>
</table>
