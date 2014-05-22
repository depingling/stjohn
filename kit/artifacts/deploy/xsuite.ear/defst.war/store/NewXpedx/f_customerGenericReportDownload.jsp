<td align="right">
    <table>
        <tr>
            <td>
                <table>
                    <tr>
                        <td  class="linkButtonBorder">
                            <a class="linkButton"
                               href="custAcctMgtReportResult.do?action=<%=dnwlcmd%>">
                                <%=tab2Name%>
                            </a>
                        </td>
                    </tr>
                </table>
            </td>
            <% if (correctPdf == true) { %>
            <td>
                <table>
                    <tr>
                        <td  class="linkButtonBorder">
                            <a class="linkButton"
                               href="custAcctMgtReportResult.do?action=<%=dnwlcmd2%>">
                                <%=tab2Name2%>
                            </a>
                        </td>
                    </tr>
                </table>
            </td>
            <%}%>

        </tr>
    </table>
 </td>
