
<% 
String cl2 = "tbarOn", cl1 = "tbar";
if ( request.getQueryString().indexOf("bscdetail") > 0 ) {
 cl1 = "tbarOn"; cl2 = "tbar";
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
<a class="<%=cl1%>" href="bscmgr.do?action=bscdetail&searchType=id&searchField=<%=bscid%>">
Contractor Detail</a>
 | 
<a class="<%=cl2%>" href="bscmgr.do?action=bscRelationships&bscid=<%=bscid%>">
Contractor Relationships</a>
</td>
</tr>
</table>
