<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<BR><BR><BR>
<table width="100%">
<tr>
<td width="20%">&nbsp;</td>
<td width="80%" class="contenttext">
    
<%String tutURL=ClwCustomizer.getRootFilePath(request, "customtrainingflash.jsp");%>
        <a class=mainheadernav href="#" onclick="javascript:window.open('<%=tutURL%>','','location=0,status=0,scrollbars=0,width=800,height=600');return false;">
		<app:storeMessage key="template.jd.iss.launch.flash"/>
		</a>
	
</td>
</tr>
</table>
