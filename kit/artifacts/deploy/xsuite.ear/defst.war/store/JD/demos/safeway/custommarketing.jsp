

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
        <li>Food Safety
            <ul>
                <li>Programs
                    <ul>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/EliminineXGuide.pdf")%>'>ElimineX Guide</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/SinkFillingStationProcedures.pdf")%>'>Sink Filling Station </a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/FoodSafetyProgram.pdf")%>'>Food Safety Program</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/SanitizerTestProcedures.pdf")%>'>Sanitizer Test Procedures</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/KitchHygienePLANNERV5.pdf")%>'>Kitchen Hygiene Planner</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/FruitVegWash.pdf")%>'>Safeway Antimicrobial Fruit and Vegetable Wash</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/FoodSafetySanitationSprayer.pdf")%>'>Food Safety Sanitation Sprayer</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/3171711CHT187.pdf")%>'>Safeway UHS Floor Care Procedures</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/CHT602Restroom.pdf")%>'>Restroom Cleaning Procedures</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/ElimineXPromotionalVideo.wmv")%>'>ElimineX video</a></li>
                    </ul>
                </li>
            </ul>
        </li>
    </ul>
</td>
</tr>
</table>
