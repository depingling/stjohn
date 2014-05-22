
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

<p><span class="trainingsubhead"><b>Isolation Area Cleaning</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span><br>
Follow the same procedures for cleaning patient rooms. In addition to the daily tasks complete the following.

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Wear the appropriate cleaning attire. Follow your facility’s isolation area procedures.</span></li>
	<li class="trainingbullet"><span class="text">After cleaning the area, throw away cloths cleaning solutions, and any other cleaning materials in designated receptacles according to your facility’s guidelines.</span></li>
	<li class="trainingbullet"><span class="text">Wash your hands thoroughly before leaving the room.<br>
	Wash your hands with the antiseptic soap.<br>
	Wet your Hands<br>
	Apply enough soap to create a good lather.<br>
	Scrub all surfaces of your hands including backs of hands, between fingers, and under fingernails for at least 30 seconds.<br>
	Rinse thoroughly with warm water.<br>
	Dry hands with paper towel.<br>
	Turn off the faucet with the paper towel so as not to re-contaminate your hands.</span></li>
</p>
	</OL>
</p>


					</td>
				</tr>
			</table>

