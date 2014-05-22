<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="AUTO_ORDER_FORM" type="com.cleanwise.view.forms.AutoOrderMgrForm"/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">
  <table cellspacing="0" border="0" width="769" class="mainbody">
  <html:form action="/adminportal/autoordermgr.do">
  <tr>
    <td>
       Batch Date:
       <html:text name="AUTO_ORDER_FORM" property="batchDate" size="10" maxlength="10"/>
       <html:submit property="action" value="Run Batch"/>
    </td>
  </tr>
  <tr>
    <td>
    <table cellspacing="0" border="0" width="100%" class="mainbody">
    <tr align="center">
      <td> <b> Site Id</b></td>
      <td> <b> Order Schedule Id</b></td>
      <td> <b> Order Status</b></td>
      <td> <b> Order&nbsp;Date&nbsp;For</b></td>
      <td> <b> Processing&nbsp;Date</b></td>
      <td> <b> Message</b></td>
    </tr>
    <% OrderBatchLogDataVector oblDV = theForm.getOrderBatchLog();
       for(int ii=0; ii<oblDV.size(); ii++) {
         OrderBatchLogData oblD = (OrderBatchLogData) oblDV.get(ii);
    %>
    <tr>
      <td> <%=oblD.getBusEntityId()%></td>
      <td> <%=oblD.getOrderSourceId()%></td>
      <td> <%=oblD.getOrderBatchStatusCd()%></td>
      <td> <%=oblD.getOrderForDate()%></td>
      <td> <%=oblD.getOrderDate()%></td>
      <td> <%=oblD.getMessage()%></td>
    </tr>
    <% } %>
    </table>
    </td>
  </tr>

</html:form>
</table>
</div>


