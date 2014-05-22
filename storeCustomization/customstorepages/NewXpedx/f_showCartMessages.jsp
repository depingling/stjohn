<%--Confirmation/Errors/Warnings message Area--%>


<%
if (errors!=null && errors.trim().length() > 0) {
  mess = errors;
  messStyle = "color:#FF0000; white-space: normal; ";

}else if ((ShopTool.cartContainsWarnings(request))){
  mess = warnings;
  messStyle = "color:#FF0000; white-space: normal;";
} else if ( !(ShopTool.cartContainsWarnings(request)) && (errors.trim().length() == 0) && (confirmation!=null && confirmation.trim().length() > 0)) {
  mess= confirmation;
  messStyle = "color:#003399; ";
}
%>
<%--<tr> <td>
<div id="BOX_messageText" style="visibility:visible" >  --%>
  <table   align = "center" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td   align="center" style="<%=messStyle%>" id="scerrors">
      <%if (mess.equals("errors")) {%>
        <html:errors/>
      <%} else if (mess.equals("warnings")) {%>
        <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'/>
      <%} else {%>
        <b><%=mess%></b>
      <%} %>
      </td>
      <td style="padding-right:40px">&nbsp;</td>
    </tr>
  </table>
<%--</div>
</td></tr>--%>
<%-------------------------------------%>
