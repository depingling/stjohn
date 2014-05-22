<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<%
	String title = (String) request.getParameter("title");
    String content = (String) request.getParameter("content");
  %>
<html language='en_US'>
<head>
<title><%=title%></title>
</head>

<link rel="stylesheet" href='../../externals/newxpedx_styles.css'>
<body marginheight="0" topmargin="0" marginwidth="0" leftmargin="0">
<table width="100%" height="100%" align="center" border="0" cellpadding="0" cellspacing="0">
<tr valign="top">
<td width="20" background='images/leftBorder.gif'>&nbsp;</td>
<td bgcolor="white">&nbsp;&nbsp;&nbsp;</td>
<td bgcolor="white"> 
<style type="text/css">
.xpdexGradientButton {
        background: 
        url( '<%=ClwCustomizer.getSIP(request,"buttonMiddle.png")%>' ) repeat-x top;
}
.xpdexGradientButton:hover {
        color:#FFFFFF;
        background: 
        url( '<%=ClwCustomizer.getSIP(request,"buttonMiddle.png")%>' ) repeat-x top;

}
a.xpdexGradientButton:link, a.xpdexGradientButton:visited, a.xpdexGradientButton:active {
        background: 
        url( '<%=ClwCustomizer.getSIP(request,"buttonMiddle.png")%>' ) repeat-x top;
}
a.xpdexGradientButton:hover {
        color:#FFFFFF;
        background: 
        url( '<%=ClwCustomizer.getSIP(request,"buttonMiddle.png")%>' ) repeat-x top;
}

.title {
    font-weight: bold;
    font-size: 18px;
}
</style>

<table width="100%"  cellspacing="0" cellpadding="0">
<tr><td align="left">&nbsp;</td></tr>
<tr><td align="left">&nbsp;</td></tr>
<tr>
	<td align="left">
		<table>
			<tr>
				<td class="title"><%=title%></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table cellspacing="10">
					<tr>
					<td>
					<SCRIPT LANGUAGE="JavaScript">
					<!--
					document.write(window.opener.document.getElementById('content').value)
					//-->
					</script>
					</td>
					</tr>
					</table>
				</td>
			</tr>
		</table>	
	</td>
</tr>
</table>
<table align = "left" border="0" cellpadding="0" cellspacing="0" >
<tr><td>&nbsp;</td></tr>
<tr>
	<td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"></td>
	<td align="center" valign="middle" nowrap class="xpdexGradientButton">
        <a class="xpdexGradientButton" href="#" onclick=window.print();>&nbsp;&nbsp;Print&nbsp;&nbsp;</a>
    </td>
    <td align="left" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
</tr>
</table>
</td>
<td bgcolor="white">&nbsp;&nbsp;&nbsp;</td>
<td width="20" background='images/rightBorder.gif'>&nbsp;</td>
</tr>
</table>

</body>
</html>