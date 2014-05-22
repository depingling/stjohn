
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




      <table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
        <tr>
          <td class="smalltext" valign="up" width="20%">
          <td>
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td class="text">

<p><span class="trainingsubhead"><b>Patient/ Resident Room Dismissal Cleaning Procedures</b></span></p>

<span class="trainingsubhead"><b>Procedures:</b></span><br><br>
Follow the same procedures for cleaning patient/resident rooms. In addition to the daily tasks:
<br><br>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Put on personal protective equipment (refer to your facility’s guidelines)
	<li class="trainingbullet"><span class="text">Strip and clean the bed.
	<li class="trainingbullet"><span class="text">Dilute the disinfectant cleaner according to the label and or your facility’s specific            guidelines.
	<li class="trainingbullet"><span class="text">Wash Walls, Furniture and bed lights with disinfectant cleaner.
	<li class="trainingbullet"><span class="text">Rinse the disinfectant if needed.
	<li class="trainingbullet"><span class="text">Electrical equipment in the room poses a potential electrical hazard. To avoid this risk unplug electrical equipment before cleaning. (If you are unsure about removing a plug from an outlet, ask a member of the staff or consult with your supervisor.
	<li class="trainingbullet"><span class="text">Wash your hands with antiseptic soap.<br>
	Wet your Hands<br>
	Apply enough soap to create a good lather<br>
	Scrub all surfaces of your hands including backs of hands, between fingers, and under fingernails for at least 30 seconds.<br>
	Rinse thoroughly with warm water.<br>
	Dry hands with paper towel.<br>
	Turn off the faucet with the paper towel so as not to re-contaminate your hands.<br>
	Throw paper towel in the trash.
	</p>
	</OL>
</p>
					</td>
				</tr>
			</table>

