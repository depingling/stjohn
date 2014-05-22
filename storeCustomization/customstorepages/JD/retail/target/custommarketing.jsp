

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
        <li>Operations
            <ul>
                <li>Programs
                    <ul>
                     <%--   <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/CHT602Restroom.pdf")%>'>Restroom Cleaning Procedures</a></li> --%>
                        <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "content/HousekeepingVendorBestPractices.ppt")%>'>Housekeeping Vendor Best Practices</a></li>
                    </ul>
                </li>
            </ul>
        </li>
    </ul>
</td>
</tr>
</table>
