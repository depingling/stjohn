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
            <a class="<%=style%>" href="processAdm.do">Monitoring</a>

            <%
                if ("catalog".equals(s)) {
                    style = "tbarOn";
                } else {
                    style = "tbar";
                }
            %>
            | <a class="<%=style%>" href="processAdm.do?action=catalog">Template Catalog</a>

            <%
                if ("creating".equals(s)) {
                    style = "tbarOn";
                } else {
                    style = "tbar";
                }
            %>
            | <a class="<%=style%>" href="processAdm.do?action=creating">Template Creating</a>

        </td>
    </tr>
</table>
