<%@ page session="false" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<html>
<head>
<title>Pager Tag Library Demo</title>
<%
/*
 *  Pager Tag Library
 *
 *  Copyright (C) 2000  James Klicman <james@jsptags.com>
 *
 *  The latest release of this tag library can be found at
 *  http://jsptags.com/tags/navigation/pager/
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
%>
<style type="text/css">
A.nodec { text-decoration: none; }
</style>
</head>
<body bgcolor="#ffffff">
<table bgcolor="#ffcc00" width="100%" border="0"
	cellspacing="0" cellpadding="2">
<tr>
<td><table bgcolor="#3366cc" width="100%" border="0"
	cellspacing="0" cellpadding="4">
	<tr>
	<td width="379"><a href="http://jsptags.com/"><img src="jsptags.gif"
		alt="&lt;jsptags.com&gt; logo" width="379" height="65" border="0"></a></td>
	<td width="100%" align="center"><a href="http://jsptags.com/tags/navigation/pager/" class="nodec"><font color="#ffffff" size="+2"><b>
		Pager Tag Library v1.1</b></font></a></td>
	</tr>
	</table></td>
</tr>
</table>

<%
	String requestUri = request.getRequestURI();
	int indexOfqm = requestUri.indexOf('?');
	if (indexOfqm != -1)
		requestUri = requestUri.substring(0, indexOfqm);

	String style = request.getParameter("style");
%>
<center>

<table width="90%" cellpadding="4">
<tr>
<td>
<ul>
<b>Style</b><br>
<li><a href="<%= requestUri %>?style=simple">Simple</a>
<li><a href="<%= requestUri %>?style=jsptags">JSPTags.com</a>
<li><a href="<%= requestUri %>?style=altavista">AltaVista&reg;</a>
<li><a href="<%= requestUri %>?style=google"><font color="#0000cc">G</font><font color="#cccc00">oo</font><font color="#0000cc">g</font><font color="#00cc00">l</font><font color="#cc0000">e</font><small><sup>SM</sup></small></a>
</ul>
</td>
<td width="100%">
This demo of the Pager Tag Library displays the web safe color palette as
its data source. You can choose among the different sample index styles
by clicking on the style name.
</td>
</tr>
</table>

<pg:pager maxIndexPages="<%= 20 %>">
  <pg:param name="style"/> <% /* keep track of style */ %>

  <hr>
  <table width="90%" cellspacing="4" cellpadding="4">
  <%
	for (int i = 0; i < webPalette.length; i++) {
		%><pg:item>
<tr><th bgcolor="<%= webPalette[i][0] %>"><font color="<%= webPalette[i][1] %>"><%= i + 1 %></font></th></tr></pg:item><%
	}
  %>
  </table>
  <hr>

  <pg:index>

    <% if ("jsptags".equals(style)) { %>

    <table bgcolor="#ffcc00" border="0" cellspacing="0"
    cellpadding="2"><tr><td><table bgcolor="#003399" width="100%"
    border="0" cellspacing="0" cellpadding="6"><tr><td
    align="center"><table border="0" cellspacing="0"
    cellpadding="0"><tr><td width="15" height="21"><img
    src="http://jsptags.com/images/pager/left.gif" width="15" height="21"
    border="0"></td>
    <th height="21" bgcolor="#3366cc"
    background="http://jsptags.com/images/pager/bg.gif"
    nowrap><font face="Lucida,San-Serif,Arial,Helvetica">
    <pg:prev>
      <a href="<%= pageUrl %>" class="nodec"><font
        color="#ffcc00">&lt;&lt;</font></a>
    </pg:prev>
    <pg:pages>&nbsp;<%
      if (pageNumber == pagerPageNumber) {
        %><font color="#ffffff"><%= pageNumber %></font><%
      } else {
        %><a href="<%= pageUrl %>" class="nodec"><font
          color="#ffcc00"><%= pageNumber %></font></a><%
      }
    %>&nbsp;</pg:pages>
    <pg:next>
      <a href="<%= pageUrl %>" class="nodec"><font
        color="#ffcc00">&gt;&gt;</font></a>
    </pg:next>
    </font></th><td width="17" height="21"><img
    src="http://jsptags.com/images/pager/right.gif" width="17" height="21"
    border="0"></td></tr></table></td></tr></table></td></tr>
    </table>

    <% } else if ("google".equals(style)) { %>

    <table border=0 cellpadding=0 width=10% cellspacing=0>
    <tr align=center valign=top>
    <td valign=bottom><font face=arial,sans-serif
      size=-1>Result&nbsp;Page:&nbsp;</font></td>
    <pg:prev ifnull="<%= true %>">
      <% if (pageUrl != null) { %>
        <td align=right><A HREF="<%= pageUrl %>"><IMG
          SRC=http://www.google.com/nav_previous.gif alt="" border=0><br>
        <b>Previous</b></A></td>
      <% } else { %>
        <td><IMG SRC=http://www.google.com/nav_first.gif alt="" border=0></td>
      <% } %>
    </pg:prev>
    <pg:pages>
      <% if (pageNumber == pagerPageNumber) { %>
        <td><IMG SRC=http://www.google.com/nav_current.gif alt=""><br>
        <font color=#A90A08><%= pageNumber %></font></td>
      <% } else { %>
        <td><A HREF="<%= pageUrl %>"><IMG
          SRC=http://www.google.com/nav_page.gif alt="" border=0><br>
        <%= pageNumber %></A></td>
      <% } %>
    </pg:pages>
    <pg:next ifnull="<%= true %>">
      <% if (pageUrl != null) { %>
        <td><A HREF="<%= pageUrl %>"><IMG
          SRC=http://www.google.com/nav_next.gif alt="" border=0><br>
        <b>Next</b></A></td>
      <% } else { %>
        <td><IMG SRC=http://www.google.com/nav_last.gif alt="" border=0></td>
      <% } %>
    </pg:next>
    </tr>
    </table>


    <% } else if ("altavista".equals(style)) { %>

    <font face=Helvetica size=-1>Result Pages:
    <pg:prev>&nbsp;<a href="<%= pageUrl %>">[<< Prev]</a></pg:prev>
    <pg:pages><%
      if (pageNumber.intValue() < 10) {
        %>&nbsp;<%
      }
      if (pageNumber == pagerPageNumber) {
        %><b><%= pageNumber %></b><%
      } else {
        %><a href="<%= pageUrl %>"><%= pageNumber %></a><%
      }
    %>
    </pg:pages>
    <pg:next>&nbsp;<a href="<%= pageUrl %>">[Next >>]</a></pg:next>
    <br></font>


    <% } else { %>

    <pg:prev>
      <a href="<%= pageUrl %>">[ (<%= pageNumber %>) << Previous ]</a>
    </pg:prev>
    <pg:pages>
       <a href="<%= pageUrl %>"><%= pageNumber %></a> 
    </pg:pages>
    <pg:next>
      <a href="<%= pageUrl %>">[ Next >> (<%= pageNumber %>) ]</a>
    </pg:next>

    <% } %>

  </pg:index>
</pg:pager>
</center>
</body>
</html>
<%!
  static final String[][] webPalette = {
    {"#ffffff","#000000"},
    {"#cccccc","#000000"},
    {"#999999","#000000"},
    {"#666666","#ffffff"},
    {"#333333","#ffffff"},
    {"#000000","#ffffff"},
    {"#ffcc00","#000000"},
    {"#ff9900","#000000"},
    {"#ff6600","#000000"},
    {"#ff3300","#ffffff"},
    {"#99cc00","#000000"},
    {"#cc9900","#000000"},
    {"#ffcc33","#000000"},
    {"#ffcc66","#000000"},
    {"#ff9966","#000000"},
    {"#ff6633","#000000"},
    {"#cc3300","#ffffff"},
    {"#cc0033","#ffffff"},
    {"#ccff00","#000000"},
    {"#ccff33","#000000"},
    {"#333300","#ffffff"},
    {"#666600","#ffffff"},
    {"#999900","#000000"},
    {"#cccc00","#000000"},
    {"#ffff00","#000000"},
    {"#cc9933","#000000"},
    {"#cc6633","#ffffff"},
    {"#330000","#ffffff"},
    {"#660000","#ffffff"},
    {"#990000","#ffffff"},
    {"#cc0000","#ffffff"},
    {"#ff0000","#ffffff"},
    {"#ff3366","#ffffff"},
    {"#ff0033","#ffffff"},
    {"#99ff00","#000000"},
    {"#ccff66","#000000"},
    {"#99cc33","#000000"},
    {"#666633","#ffffff"},
    {"#999933","#000000"},
    {"#cccc33","#000000"},
    {"#ffff33","#000000"},
    {"#996600","#ffffff"},
    {"#993300","#ffffff"},
    {"#663333","#ffffff"},
    {"#993333","#ffffff"},
    {"#cc3333","#ffffff"},
    {"#ff3333","#ffffff"},
    {"#cc3366","#ffffff"},
    {"#ff6699","#000000"},
    {"#ff0066","#ffffff"},
    {"#66ff00","#000000"},
    {"#99ff66","#000000"},
    {"#66cc33","#000000"},
    {"#669900","#ffffff"},
    {"#999966","#000000"},
    {"#cccc66","#000000"},
    {"#ffff66","#000000"},
    {"#996633","#ffffff"},
    {"#663300","#ffffff"},
    {"#996666","#ffffff"},
    {"#cc6666","#000000"},
    {"#ff6666","#000000"},
    {"#990033","#ffffff"},
    {"#cc3399","#ffffff"},
    {"#ff66cc","#000000"},
    {"#ff0099","#ffffff"},
    {"#33ff00","#000000"},
    {"#66ff33","#000000"},
    {"#339900","#ffffff"},
    {"#66cc00","#000000"},
    {"#99ff33","#000000"},
    {"#cccc99","#000000"},
    {"#ffff99","#000000"},
    {"#cc9966","#000000"},
    {"#cc6600","#ffffff"},
    {"#cc9999","#000000"},
    {"#ff9999","#000000"},
    {"#ff3399","#ffffff"},
    {"#cc0066","#ffffff"},
    {"#990066","#ffffff"},
    {"#ff33cc","#000000"},
    {"#ff00cc","#ffffff"},
    {"#00cc00","#ffffff"},
    {"#33cc00","#000000"},
    {"#336600","#ffffff"},
    {"#669933","#ffffff"},
    {"#99cc66","#000000"},
    {"#ccff99","#000000"},
    {"#ffffcc","#000000"},
    {"#ffcc99","#000000"},
    {"#ff9933","#000000"},
    {"#ffcccc","#000000"},
    {"#ff99cc","#000000"},
    {"#cc6699","#000000"},
    {"#993366","#ffffff"},
    {"#660033","#ffffff"},
    {"#cc0099","#ffffff"},
    {"#330033","#ffffff"},
    {"#33cc33","#000000"},
    {"#66cc66","#000000"},
    {"#00ff00","#000000"},
    {"#33ff33","#000000"},
    {"#66ff66","#000000"},
    {"#99ff99","#000000"},
    {"#ccffcc","#000000"},
    {"#cc99cc","#000000"},
    {"#996699","#ffffff"},
    {"#993399","#ffffff"},
    {"#990099","#ffffff"},
    {"#663366","#ffffff"},
    {"#660066","#ffffff"},
    {"#006600","#ffffff"},
    {"#336633","#ffffff"},
    {"#009900","#ffffff"},
    {"#339933","#ffffff"},
    {"#669966","#000000"},
    {"#99cc99","#000000"},
    {"#ffccff","#000000"},
    {"#ff99ff","#000000"},
    {"#ff66ff","#000000"},
    {"#ff33ff","#000000"},
    {"#ff00ff","#ffffff"},
    {"#cc66cc","#000000"},
    {"#cc33cc","#ffffff"},
    {"#003300","#ffffff"},
    {"#00cc33","#ffffff"},
    {"#006633","#ffffff"},
    {"#339966","#ffffff"},
    {"#66cc99","#000000"},
    {"#99ffcc","#000000"},
    {"#ccffff","#000000"},
    {"#3399ff","#000000"},
    {"#99ccff","#000000"},
    {"#ccccff","#000000"},
    {"#cc99ff","#000000"},
    {"#9966cc","#000000"},
    {"#663399","#ffffff"},
    {"#330066","#ffffff"},
    {"#9900cc","#ffffff"},
    {"#cc00cc","#ffffff"},
    {"#00ff33","#000000"},
    {"#33ff66","#000000"},
    {"#009933","#ffffff"},
    {"#00cc66","#000000"},
    {"#33ff99","#000000"},
    {"#99ffff","#000000"},
    {"#99cccc","#000000"},
    {"#0066cc","#ffffff"},
    {"#6699cc","#000000"},
    {"#9999ff","#000000"},
    {"#9999cc","#000000"},
    {"#9933ff","#ffffff"},
    {"#6600cc","#ffffff"},
    {"#660099","#ffffff"},
    {"#cc33ff","#ffffff"},
    {"#cc00ff","#ffffff"},
    {"#00ff66","#000000"},
    {"#66ff99","#000000"},
    {"#33cc66","#000000"},
    {"#009966","#ffffff"},
    {"#66ffff","#000000"},
    {"#66cccc","#000000"},
    {"#669999","#000000"},
    {"#003366","#ffffff"},
    {"#336699","#ffffff"},
    {"#6666ff","#ffffff"},
    {"#6666cc","#ffffff"},
    {"#666699","#ffffff"},
    {"#330099","#ffffff"},
    {"#9933cc","#ffffff"},
    {"#cc66ff","#000000"},
    {"#9900ff","#ffffff"},
    {"#00ff99","#000000"},
    {"#66ffcc","#000000"},
    {"#33cc99","#000000"},
    {"#33ffff","#000000"},
    {"#33cccc","#000000"},
    {"#339999","#ffffff"},
    {"#336666","#ffffff"},
    {"#006699","#ffffff"},
    {"#003399","#ffffff"},
    {"#3333ff","#ffffff"},
    {"#3333cc","#ffffff"},
    {"#333399","#ffffff"},
    {"#333366","#ffffff"},
    {"#6633cc","#ffffff"},
    {"#9966ff","#000000"},
    {"#6600ff","#ffffff"},
    {"#00ffcc","#000000"},
    {"#33ffcc","#000000"},
    {"#00ffff","#000000"},
    {"#00cccc","#000000"},
    {"#009999","#ffffff"},
    {"#006666","#ffffff"},
    {"#003333","#ffffff"},
    {"#3399cc","#000000"},
    {"#3366cc","#ffffff"},
    {"#0000ff","#ffffff"},
    {"#0000cc","#ffffff"},
    {"#000099","#ffffff"},
    {"#000066","#ffffff"},
    {"#000033","#ffffff"},
    {"#6633ff","#ffffff"},
    {"#3300ff","#ffffff"},
    {"#00cc99","#000000"},
    {"#0099cc","#ffffff"},
    {"#33ccff","#000000"},
    {"#66ccff","#000000"},
    {"#6699ff","#000000"},
    {"#3366ff","#ffffff"},
    {"#0033cc","#ffffff"},
    {"#3300cc","#ffffff"},
    {"#00ccff","#000000"},
    {"#0099ff","#ffffff"},
    {"#0066ff","#ffffff"},
    {"#0033ff","#ffffff"}
  };
%>
