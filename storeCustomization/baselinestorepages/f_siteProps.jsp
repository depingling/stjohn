<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%
CleanwiseUser u = (CleanwiseUser)
  request.getSession().getAttribute("ApplicationUser");
SiteData v_currSite = u.getSite();

if ( null != v_currSite ) {

PropertyData 
p = v_currSite.getMiscProp("Gross Square Footage"); 
String gsf = null;
if ( p != null ) gsf = p.getValue();

p = v_currSite.getMiscProp("Net Square Footage:"); 
String nsf = null;
if ( p != null ) nsf = p.getValue();

p = v_currSite.getMiscProp("Rank Index");
String rankIdx = null;
if ( p != null ) rankIdx = p.getValue();

%>

<table>
<% if ( gsf != null && gsf.length() > 0 ) { %>
<tr>
<td class="address">Gross Square Footage</td> 
<td><%=gsf%></td>
</tr>

<% } %>
<% if ( nsf != null && nsf.length() > 0 ) { %>
<tr>
<td  class="address">Net Square Footage</td>
<td><%=nsf%></td>
</tr>

<% } %>
<% if ( rankIdx != null && rankIdx.length() > 0 ) { %>
<tr>
<td  class="address">Rank Index</td>
<td><%=rankIdx%></td>
</tr>

<% } %>

</table>

<%
} /* check on null site variable */
%>
