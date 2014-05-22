
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>




<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<% 
String query=request.getQueryString();
String currUri=SessionTool.getActualRequestedURI(request) +"?"; 
if(query!=null){
    currUri+=query+"&";
}
currUri = SessionTool.removeRequestParameter(currUri,"section");
%>
<bean:define id="section" value="training_howtoclean.jsp" type="java.lang.String" toScope="session"/>


<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <tr>
    <td class="tableoutline" width="1" bgcolor="black">
      <img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1">
    </td>
    <td>
      <jsp:include flush='true' page="/trainingContent/trainingToolbar.jsp"/>
    </td>
    <td class="tableoutline" width="1" bgcolor="black">
      <img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1">
    </td>
  </tr>

  <tr>
    <td class="tableoutline" width="1" bgcolor="black">
      <img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1">
    </td>
    <td>
      &nbsp;
      <!----- begin catalog navigation  -->

      <table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
        <tr>
          <td class="smalltext" valign="up" width="20%">
          <td>
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
			  <tr>
				<td class="text">Choose from one of the topics below:
				  <ul>
					<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=BasicFabricUpholstery">Basic Fabric and Upholstery Cleaning</a></span></li>
				  </ul>
				</td>
			  </tr>
			</table>

