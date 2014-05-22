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
            <a class="<%=style%>" href="cachecosAdm.do">Info</a>
            <%
                if ("management".equalsIgnoreCase(s) || "executeOp".equalsIgnoreCase(s)) {
                    style = "tbarOn";
                } else {
                    style = "tbar";
                }
            %>
            | <a class="<%=style%>" href="cachecosAdm.do?action=management">Cache Management</a>
             <%
                if ("description".equalsIgnoreCase(s)) {
                    style = "tbarOn";
                } else {
                    style = "tbar";
                }
            %>
            | <a class="<%=style%>" href="cachecosAdm.do?action=description">Cache Description</a>

        </td>
    </tr>
</table>
