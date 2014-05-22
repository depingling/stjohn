<app:checkLogon/>

<script language="JavaScript1.2">
<!--
//-->
</script>
<bean:define id="theForm" name="GENERIC_REPORT_FORM" type="com.cleanwise.view.forms.GenericReportForm"/>

  <!-- User Types -->
  <%
 // GenericReportData grD = theForm.getReport();
 // String userTypes= grD.getUserTypes();
 // String[] userTypesList = userTypes.split(",");

  ArrayList allTypes = theForm.getUserTypes();
   %>
 <table  align="left" cellpadding="0" cellspacing="0" border="0" width="40%" >
  <tr>
   <tr class="tableheader">
    <td colspan="2">User Types: </td>
    <td>Select</td>
   </tr>
  <% for (int i = 0; i < allTypes.size(); i++) {
     String userType = (String)allTypes.get(i);
  %>
  <tr>
    <td width="25%">&nbsp;</td>
    <td><%=(String)allTypes.get(i)%></td>
    <td><html:multibox name="GENERIC_REPORT_FORM" property="typeSelected" value="<%=userType%>" /></td>
  </tr>
  <%}%>
 </table>




