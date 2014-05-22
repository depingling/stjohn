
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

<p><span class="trainingsubhead"><b>Patient/ Resident Room Daily Cleaning Procedures</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Put on personal protective equipment (refer to your facility’s guidelines)<br></span></li>
	<li class="trainingbullet"><span class="text">Make your presence known to the patient by announcing your presence.<br>
	If the patient is awake, explain to them that you are there to clean the room.</span></li>
	<li class="trainingbullet"><span class="text">Clean with disinfectant cleaner.<br>
	Clean the following:<br>
	Bedside table<br>
	Telephone<br>
	Furniture<br>
	Window sills<br>
	All horizontal surfaces<br>
	When you use a spray bottle, make sure to spray the disinfectant on the cleaning cloth rather than directly on the surfaces to be cleaned.<br>
	If the patient or resident is in bed, do not disturb them. Clean the bed at another time. Follow your facility’s procedures.<br></span></li>
	<li class="trainingbullet"><span class="text">Empty the trash receptacles and replace the trashcan liners.<br>
	When removing the trash liner, gather the ends loosely, don not compact the trash with your hands or feet.<br>
	Seal the liner by closing it at the top.<br>
	Hold the liner away from your body.<br>
	Clean the inside of the trashcan with disinfectant cleaner.<br>
	Replace the liner.</span></li>
	<li class="trainingbullet"><span class="text">Clean the bathroom. Use a facility-approved disinfectant cleaner on hard surfaces, clean the toilet, clean the shower, and use glass cleaner on the mirror.</span></li>
	<li class="trainingbullet"><span class="text">Fill all of the dispensers.</span></li>
	<li class="trainingbullet"><span class="text">Dust mop the floors<br>
	Use a treated dust mop to remove all loose soil and debris.<br>
	Start from the farthest corner of the room and work your way towards the door.</span></li>
	<li class="trainingbullet"><span class="text">Make sure to closely follow the chemical manufacturers label for concentration (dilution) and temperature in order to maximize the effectiveness of the cleaner/disinfectant. Too much cleaner will leave behind residue attracting dirt and causing the floor to quickly resoil. Too little cleaner will not remove soils or properly disinfect.</span></li>
	<li class="trainingbullet"><span class="text">Damp mop floors with disinfectant or general purpose cleaner following your facility’s guidelines.<br>
	Set up a wet floor sign at the entrance of the room. Do not remove the sign until the floor is thoroughly dry.<br>
	Wet the mop in the disinfectant/cleaner and use the wringer to remove the excess. The mop should not be dripping.<br>
	Start at the far end of the area to be mopped and outline a U-shaped section along the baseboard and the edges of the work area. Fill in the U-shaped sections by mopping with an overlapping figure eight pattern.<br>
	If the floors are heavily soiled, increase the time the disinfectant/cleaning solution is on the floor.<br>
	<b>Note: Most disinfectants need time to stay on floors and surfaces in order to kill bacteria and viruses. A general rule of thumb is to leave the disinfectant on the surface for at least 10 minutes. Refer to the directions on the label for correct dwell or stand times.</b><br>
	Use a floor pad, edge tool, or putty knife to loosen stubborn debris.</span></li>
	<li class="trainingbullet"><span class="text">If the room is carpeted, follow your facilities proper procedures for vacuuming. Remove any spots, and then vacuum.</span></li>
	<li class="trainingbullet"><span class="text">Wash your hands with the antiseptic soap.<br>
	Wet your Hands.<br>
	Apply enough soap to create a good lather.<br>
	Scrub all surfaces of your hands including backs of hands, between fingers, under    fingernails for at least 30 seconds. Rinse thoroughly with warm water. Dry hands with paper towel. </span></li>
	</p>
	</OL>
</p>


					</td>
				</tr>
			</table>

