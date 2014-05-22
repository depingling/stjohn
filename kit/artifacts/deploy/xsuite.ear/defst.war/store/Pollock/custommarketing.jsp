

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
        <li>Healthy High Performance Cleaning Program
            <ul>
			<li>Cleaning and Disinfection
				<ul>
						<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/Cleaning and Disinfection Glossary.doc")%>'>Cleaning and Disinfection Glossary</a></li>
						<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/Cleaning & Disinfection.ppt")%>'>Cleaning & Disinfection</a></li>
						<li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/Staph infection article.doc")%>'>Staph infection article</a></li>
				</ul>
			</li>
            </ul>
            <ul>
                <li>Marketing Tools
                    <ul>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/hhpcbookmark.pdf")%>'>Bookmark</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/hhpcdoorhanger.pdf")%>'>Door Hanger</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/hhpcletter.pdf")%>'>Letter</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/hhpcmagnet.pdf")%>'>Magnet</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/hhpcemphealth.pdf")%>'>Newsletter Employee Health</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/hhpcnewsletterguidelines.pdf")%>'>Newsletter Guidelines</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/hhpchealthierorg.pdf")%>'>Newsletter Healthier Organization</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/hhpcplain.pdf")%>'>Plain Letterhead</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/hhpcposter.pdf")%>'>Poster</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/hhpctentcard.pdf")%>'>Tent Card</a></li>
                    </ul>
                </li>
                <li>Procedures
                    <ul>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/carpetcare.pdf")%>'>Carpet Care</a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/dusting.pdf")%>'>Dusting </a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/entryways.pdf")%>'>Entryways </a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/floorcare.pdf")%>'>Floor Care </a></li>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/restrooms.pdf")%>'>Restrooms </a></li>
                    </ul>
                </li>
            </ul>
        </li>
        <li>Programs
            <ul>
                <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/education.pdf")%>'>Education Brochure</a></li>
            </ul>
        </li>
    </ul>
</td>
</tr>
</table>
