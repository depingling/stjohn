<%
    String s = (String) request.getParameter("action");
    if (s == null){
        s = "init";
    }
    String style = "tbar";
%>


<table border="0" cellpadding="0" cellspacing="0" width="769">
    <tr bgcolor="#000000">
        <td class="tbartext">

            <%
                if ("init".equals(s)) {
                    style = "tbarOn";
                } else {
                    style = "tbar";
                }
            %>
            <a class="<%=style%>" href="eventAdm.do">Statistic</a>

            <%
                if ("search".equals(s)) {
                    style = "tbarOn";
                } else {
                    style = "tbar";
                }
            %>
            | <a class="<%=style%>" href="eventAdm.do?action=search">Search & Edit</a>

            <%
                if ("creating".equals(s)) {
                    style = "tbarOn";
                } else {
                    style = "tbar";
                }
            %>
            | <a class="<%=style%>" href="eventAdm.do?action=creating">Creating</a>

            <%
                if ("control".equals(s)) {
                    style = "tbarOn";
                } else {
                    style = "tbar";
                }
            %>
            | <a class="<%=style%>" href="eventAdm.do?action=control">Control</a>

        </td>
    </tr>
</table>
