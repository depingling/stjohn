<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="ADMIN2_SHOPPING_CONTROL_LOADER_MGR_FORM" type="com.cleanwise.view.forms.Admin2ShoppingControlLoaderMgrForm"/>
<%
    String action = request.getParameter("action");
%>

<div class="text">

  <table ID=1015 cellspacing="0" border="0" width="769" class="mainbody">
  <html:form styleId="1016"  action="/admin2.0/shoppingcontrolloader.do"
            scope="session">

  <script type="text/javascript" language="JavaScript">
  <!--
     dml=document.forms;
     for(i=0; i<dml.length; i++) {
      ellen = dml[i].elements.length;
      for(j=0; j<ellen; j++) {
        if (dml[i].elements[j].name=='xlsFile') {
          dml[i].elements[j].focus();
          break;
        }
      }
     }

    function showProcessing() {
        var el = document.getElementById("processing");
        el.style.visibility = "visible";
    }
  // -->

  </script>

</table>
<style type="text/css">
    .submitButton150 { width: 150px; }
    .rederrorbig { font-size: 10pt; color: red; font-weight: bold;  }
    .redmessagebig { font-size: 12pt; color: red; font-weight: bold; text-align: center; }
    .processing { visibility: hidden; }
</style>
<table ID=1010 cellspacing="0" border="0" width="769" class="mainbody">
<tr>
    <td width="170">&nbsp;<html:submit property="action" value="Import" styleClass="submitButton150" /></td>
    <td width="170"><html:submit property="action" value="Export" styleClass="submitButton150"  /></td>
    <td>
      <% if (theForm.getDownloadErrorButton()) { %>
        <html:submit property="action" value="Download Errors" styleClass="submitButton150"  />
      <% } %>
    </td>

</tr>
<tr><td colspan='3' align='center'>&nbsp;</td></tr>
</table>
</html:form>

<% if ("Import".equals(action)) { %>
<table ID=1010 cellspacing="0" border="0" width="769" class="mainbody">
      <html:form styleId="1016"  action="/admin2.0/shoppingcontrolloader.do"
                scope="session" enctype="multipart/form-data" onsubmit="showProcessing()">

    <tr>
    <td colspan='4'><b>Select File:</b>
        <html:file name="ADMIN2_SHOPPING_CONTROL_LOADER_MGR_FORM" property="xlsFile" size='80'/>
        <html:submit property="action" value="Upload"   />
    </td>
 </tr>
 <tr><td colspan='4' align='center'>&nbsp;</td></tr>
</html:form>
</table>
<%}%>
<% if (theForm.getSuccessfullyLoaded()) { %>
  <br>
    <table ID=1010 cellspacing="0" border="0" width="769">
        <tr><td class="redmessagebig"><app:storeMessage key="accAdmin.loaders.successfullyLoaded"/></td></tr>
    </table>
  <br>
<%}%>

<%
    ArrayList errors = theForm.getErrors();
    for (int j=0; j< errors.size(); j++) {
        String[] rowErrors = (String[])errors.get(j) ;
        for (int i = 0; i < rowErrors.length; i++) {

            if (Utility.isSet(rowErrors[i])) {
%>
                <p class=rederrorbig><app:storeMessage key="accAdmin.loaders.line"/> <%=j+2%>, <%=rowErrors[i]%></p>
<%          }
        }
    }
%>
<% if (theForm.getExportFlag()) { %>

<jsp:include flush='true' page="locateStoreAccount.jsp">
   <jsp:param name="jspFormAction" 	value="/admin2.0/shoppingcontrolloader.do" />
   <jsp:param name="jspFormName" 	value="ADMIN2_SHOPPING_CONTROL_LOADER_MGR_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
   <jsp:param name="doNotShowCheckboxes" 	value="true" />
</jsp:include>

<%}%>

  <div id="processing" class="processing">
      <br>
      <table ID=1010 cellspacing="0" border="0" width="769">
          <tr><td class="redmessagebig"><app:storeMessage key="accAdmin.loaders.processing"/></td></tr>
      </table>
      <br>
  </div>

</div>