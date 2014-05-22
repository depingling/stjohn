

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
        <li>Cleaning Safely
            <ul>
                <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/workingsafely.pdf")%>'>Working Safely Wall Chart</a></li>
            </ul>
        </li>
        <li>Slip & Fall Support
            <ul>
            <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/SAFEBrochure.pdf")%>'>S.A.F.E. Brochure</a></li>
            <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/SAFETrainingGuide.pdf")%>'>S.A.F.E. Training Guide</a></li>
            <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/SAFEQuiz.doc")%>'>S.A.F.E. Review Quiz</a></li>
            <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/SafeFloorsCertificate.ppt")%>'>S.A.F.E. Certificate</a></li>
            <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/ExpertWitness.pdf")%>'>Expert Witness Brochure</a></li>
            <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/Accident_Report.doc")%>'>Slip/Fall Accident Report</a></li>
            <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/SlipResistanceLetter.doc")%>'>Slip Resistance Letter</a></li>
            </ul>
            </li>
        <li>Bloodborne Pathogens</li>
        <li>Right-To-Know</li>
        <li>Target Zero</li>
        <li>Medical Emergency</li>
    </ul>
</td>
</tr>
</table>
