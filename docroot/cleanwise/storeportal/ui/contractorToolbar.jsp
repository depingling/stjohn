
<% 
String cl1 = "tbarOn", cl2 = "tbar";
if ( request.getQueryString() != null ){
if ( request.getQueryString().indexOf("contractorConfig") > 0 ) {
	cl2 = "tbarOn"; cl1 = "tbar";
}
}
%>

<table border="0" cellpadding="0" cellspacing="0" width="769">
<tr>
  <tr bgcolor="#000000"> 
<td class="tbartext">
<% 
String bscid = request.getParameter("searchField");
if ( null == bscid ) {
  bscid = request.getParameter("bscid");
}
 
%>
&nbsp;
<a class="<%=cl1%>" href="contractordet.do?action=contractordetail&searchType=id&searchField=<%=bscid%>">
Contractor Detail</a>
 | 
<a class="<%=cl2%>" href="contractordet.do?action=contractorConfig&bscid=<%=bscid%>">
Configuration</a>
</td>
</tr>
</table>
