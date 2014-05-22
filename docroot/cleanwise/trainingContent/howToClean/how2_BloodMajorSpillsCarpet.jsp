
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
                  <p><span class="trainingsubhead"><b>Major Spills on Carpet</b></span></p>
                  <span class="trainingsubhead"><b>Procedures:</b></span>
                  <ol>
				    <li class="trainingbullet"><span class="text">Wash your hands with the antiseptic soap. Wet your Hands. Apply enough soap to create a good lather. Scrub all surfaces of your hands including backs of hands, between fingers, under fingernails. Rinse thoroughly with warm water. Dry hands with paper towel. Turn off the faucet with the paper towel so as not to re-contaminate your hands.</span></li>
                    <li class="trainingbullet"><span class="text">Put on rubber gloves and any other protective equipment required by your facility.</span></li>
                  	<li class="trainingbullet"><span class="text">Post wet floor signs.</span></li>
                	<li class="trainingbullet"><span class="text">Dispense or mix the approved disinfectant cleaner. Wear safety glasses if required by   the MSDS (material safety data sheet) to help avoid chemical contact with the eyes.
            If you are mixing your own cleaner, be sure and follow the directions on the label. Using too much cleaner can leave behind residue that acts like a magnet trapping undesirable soils and bacteria. Too little cleaner will not be effective against removing dirt, contaminants, bacteria, and viruses.
            Fill spray bottles and bucket with appropriate cleaner.
            Make sure that the bottle labels correctly represent what is in each bottle. 
	<br><br>

	<b>Note:
	If you encounter any broken glass or a needle on the floor or furniture do not 
	pick it up with your hands. Use tongs or a dustpan and brush.</b><br><br></span></li>
	                <li class="trainingbullet"><span class="text">Wet the mop in the disinfectant cleaning solution. Lift the mop out of the bucket without using the wringer and let it drain over and around the spill area. Do not let the mop head touch the spill. Completely cover the spill. Take precautions not to splash while you are applying the disinfectant over the area. </span></li>
             	    <li class="trainingbullet"><span class="text">Completely pick up all visible signs of the spill area with a wet dry vac, extraction   equipment, or specific equipment determined by your facility. </span></li>
 	                <li class="trainingbullet"><span class="text">Apply new disinfectant to the area with a clean mop. Repeat step 5.</span></li>
	                <li class="trainingbullet"><span class="text">Use the wet dry vac or extractor to pick up as much moisture as possible.</span></li>
	                <li class="trainingbullet"><span class="text">Remove wet floor signs only when the carpet is dry.</span></li>
	                <li class="trainingbullet"><span class="text">Empty the bucket and the wet dry vac or extractor. Follow your facility’s blood pathogen procedures to properly dispose of the used disinfectant cleaner.</span></li>
	                <li class="trainingbullet"><span class="text">Thoroughly rinse out your equipment and store it the proper locations.</span></li>
	                <li class="trainingbullet"><span class="text">At the earliest time possible extract the carpet with just hot water to help avoid dirt build up over the cleaned area.</span></li>
	                <li class="trainingbullet"><span class="text">Remove personal protection equipment and place in designated receptacles.</span></li>
	                <li class="trainingbullet"><span class="text">Carefully remove your gloves.<br><br>
		<b>Do not touch the outside area of the gloves with your bare hands.</b>
		To remove the first glove, begin removing glove at the wrist.<br><br>
		Turn the glove inside out as you peel it toward your fingers.<br><br>
		Hold the removed glove in your gloved hand. Use the ungloved hand to remove the remaining glove.<br><br>
		Again, start at the wrist and peel the glove inside out toward your fingers. As you remove the second glove, pull the second glove over the first glove so both gloves are gathered together.<br><br>
		<b>Dispose of gloves in approved biohazard containers or as outlined by your facility.</b><br><br></span></li>

	                <li class="trainingbullet"><span class="text">Wash your hands with the antiseptic soap.<br>
	Wet your Hands.<br>
	Apply enough soap to create a good lather.<br>
	Scrub all surfaces of your hands including backs of hands, between fingers, and under fingernails for at least 30 seconds.<br>
	Rinse thoroughly with warm water.<br>
	Dry hands with paper towel.<br>
	Turn off the faucet with the paper towel so as not to re-contaminate your hands.<br></span></li>
	
	              </ol>
                  </p>
				</td>
			  </tr>
			</table>

