
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
          <td class="smalltext" valign="up" width="20%"></td>
          <td>
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td class="text">Choose from one of the topics below:
					<br>
						<ul>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=CriticalCare">Critical Care Area Cleaning</a></span></li>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=Isolation">Isolation Area Cleaning</a></span></li>                    
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=OperatingBetween">Operating Room Between Cases Cleaning</a></span></li>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=OperatingWashDown">Operating Room Complete Washdown Cleaning</a></span></li>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=OperatingEndDay">Operating Room End of Day Cleaning</a></span></li>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=OperatingEndShift">Operating Room End of Shift Cleaning</a></span></li>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=OperatingPrep">Operating Room Cleaning Preparation</a></span></li>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=PatientPrep">Patient Cleaning Preparation</a></span></li>                    
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=PatientDaily">Patient Resident Room Daily Cleaning</a></span></li>                    
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=PatientDismissal">Patient Resident Room Dismissal Cleaning</a></span></li>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=PatientEnd">Patient Resident Room End of Shift Cleaning</a></span></li>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=PatientWeekly">Patient Resident Room Weekly Cleaning</a></span></li>
                    
						</ul>
					</td>
				</tr>
			</table>

