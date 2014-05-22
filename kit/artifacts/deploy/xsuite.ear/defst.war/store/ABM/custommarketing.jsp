

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
        <li>Enviro-Complete
            <ul>
                <li><a target="_blank" href='<%=ClwCustomizer.getFilePath(session, "..", "content/ABMEnviroCompleteQuickTips.pdf")%>'>QuickTips</a></li>
                <li><a target="_blank" href='<%=ClwCustomizer.getFilePath(session, "..", "content/abmenvirobrochure.pdf")%>'>ABM Enviro Complete Brochure</a></li>
                <li><a target="_blank" href='<%=ClwCustomizer.getFilePath(session, "..", "content/enviroentryways.pdf")%>'>Entryways</a></li>
                <li><a target="_blank" href='<%=ClwCustomizer.getFilePath(session, "..", "content/envirocarpetcare.pdf")%>'>Carpet Care</a></li>
                <li><a target="_blank" href='<%=ClwCustomizer.getFilePath(session, "..", "content/envirorestroomcare.pdf")%>'>Restroom Care</a></li>
                <li><a target="_blank" href='<%=ClwCustomizer.getFilePath(session, "..", "content/envirofloorcare.pdf")%>'>Floor Care</a></li>
                <li><a target="_blank" href='<%=ClwCustomizer.getFilePath(session, "..", "content/envirodusting.pdf")%>'>Dusting</a></li>
            </ul>
        </li>
        <li>Tag Work Selling
            <ul>
                <li><a target="_blank" href='<%=ClwCustomizer.getFilePath(session, "..", "content/abmfloorbrochure.pdf")%>'>Floor Care Brochure</a></li>
            </ul>
        </li>
        <li>Sector Selling
            <ul>
                <li><a target="_blank" href='<%=ClwCustomizer.getFilePath(session, "..", "content/abmeducationbrochure.pdf")%>'>Education Brochure</a></li>
                <li><a target="_blank" href='<%=ClwCustomizer.getFilePath(session, "..", "content/ldswoodmailer.pdf")%>'>Health Care Brochure</a></li>
            </ul>
        </li>
        <li>Specific User Selling
            <ul>
                <li><a target="_blank" href='<%=ClwCustomizer.getFilePath(session, "..", "content/ldswoodmailer.pdf")%>'>LDS Wood Floor Mailer</a></li>
            </ul>
        </li>
    </ul>
</td>
</tr>
</table>
