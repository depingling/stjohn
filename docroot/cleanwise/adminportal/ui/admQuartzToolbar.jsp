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
            <a class="<%=style%>" href="quartzAdm.do">Schedule Control</a>
            <%
                if ("jobSearch".equals(s)) {
                    style = "tbarOn";
                } else {
                    style = "tbar";
                }
            %>
            | <a class="<%=style%>" href="quartzAdm.do?action=jobSearch">Job list</a>
            <%
                if ("jobAddEdit".equals(s)) {
                    style = "tbarOn";
                } else {
                    style = "tbar";
                }
            %>
            | <a class="<%=style%>" href="quartzAdm.do?action=jobAddEdit">Create job</a>
            <%
                if ("triggerSearch".equals(s)) {
                    style = "tbarOn";
                } else {
                    style = "tbar";
                }
            %>
            | <a class="<%=style%>" href="quartzAdm.do?action=triggerSearch">Trigger list</a>
            <%
                if ("cronTriggerAddEdit".equals(s)) {
                    style = "tbarOn";
                } else {
                    style = "tbar";
                }
            %>
            | <a class="<%=style%>" href="quartzAdm.do?action=cronTriggerAddEdit&func=newJob">Create trigger</a>
        </td>
    </tr>
</table>
