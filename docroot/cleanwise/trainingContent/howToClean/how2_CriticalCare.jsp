
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

<span class="trainingsubhead"><b>Critical Care Area Cleaning</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span><br>
Follow the same procedures for cleaning patient rooms. In addition to the daily tasks complete the following.


	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Put on personal protective equipment (refer to your facility’s guidelines)
	<li class="trainingbullet"><span class="text">Do not touch any equipment and be careful not to disturb any operational medical devices e.g. IV’s, monitors, etc. If you are not sure whether you can clean around equipment ask hospital personnel.
	<li class="trainingbullet"><span class="text">Do not disturb the patient.
	Wash your hands with the antiseptic soap.<br>
	Wet your Hands.<br>
	Apply enough soap to create a good lather.<br>
	Scrub all surfaces of your hands including backs of hands, between fingers, under fingernails for at least 30 seconds.<br>
	Rinse thoroughly with warm water.<br>
	Dry hands with paper towel.
	</p>
	</OL>
</p>


					</td>
				</tr>
			</table>

