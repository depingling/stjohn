
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

<p><span class="trainingsubhead"><b>Operating Room Cleaning Between Cases</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Put on personal protective equipment. </span></li>
	<li class="trainingbullet"><span class="text">Move furniture and carts to one side of the room.<br>
	Reposition only items you have been directed to move.</span></li>
	<li class="trainingbullet"><span class="text">Place linens in designated receptacles.</span></li>
	<li class="trainingbullet"><span class="text">Remove debris stuck on the floor with a putty knife or edge tool. Be careful not to scratch or gouge the floor with the tools.</span></li>
	<li class="trainingbullet"><span class="text">Dust mop areas where the floor is dry and place any debris in specially marked bags or containers.</span></li>
	<li class="trainingbullet"><span class="text">Empty the trash and replace can liners.<br>
	When removing the trash liner, gather the ends loosely, do not compact the trash with your hands or feet.<br>
	Seal the liner by closing it at the top.<br>
	Hold the liner away from your body.<br>
	Clean the inside of the trashcan with disinfectant cleaner.<br>
	Replace the liner.<br><br>
	<b>Note: If you encounter any broken glass or a needle on the floor or furniture do not pick it up with your hands. Use tongs or a dustpan and brush and dispose of it the designated container.</b><br><br></span></li>
	<li class="trainingbullet"><span class="text">Dilute the disinfectant according to the directions on the label follow recommended water temperature as well.</span></li>
	<li class="trainingbullet"><span class="text">Clean the furniture, carts, and casters with disinfectant cleaner.</span></li>
	<li class="trainingbullet"><span class="text">Wet mop the floor with disinfectant cleaner.<br>
	Set up a wet floor sign in the doorway. Do not remove the sign until the floor is thoroughly dry.<br>
	Follow the manufacturer’s instructions on the disinfectant label for proper, dilution, application, and temperature in order to achieve maximum results.<br>
	When mopping make sure the floor is wet but not over saturated. There should be no areas of standing water (puddles).<br>
	Disinfectant cleaners need to saturate the surface and air dry for 10 minutes in order to be effective.<br>
	Apply the disinfectant in a figure eight motion.</span></li>
	<li class="trainingbullet"><span class="text">Move furniture and carts back to their assigned positions<br>
	Move furniture while the floor is still wet so all the wheels make contact with the disinfectant.</span></li>
	<li class="trainingbullet"><span class="text">Throw away all cloths and solutions use in the cleaning process in designated waste receptacles.</span></li>
	<li class="trainingbullet"><span class="text">Clean and store the equipment in the proper locations.<br>
	Thoroughly rinse out all buckets, pails, and spray bottles.<br>
	Place the mop head in the designated bin for the laundry.</span></li>
	<li class="trainingbullet"><span class="text">Remove your protective clothing in your facility’s designated area and place in designated receptacles.</span></li>
	<li class="trainingbullet"><span class="text">Carefully remove your gloves.<br>
	<b>Do not touch the outside area of the gloves with your bare hands.</b> To remove the first glove, begin removing glove at the wrist.<br>
	Turn the glove inside out as you peel it toward your fingers.<br>
	Hold the removed glove in your gloved hand. Use the ungloved hand to remove the remaining glove.<br>
	Again, start at the wrist and peel the glove inside out toward your fingers. As you remove the second glove, pull the second glove over the first glove so both gloves are gathered together.<br>
	<b>Dispose of gloves in approved biohazard containers or as outlined by your facility.</b><br></span></li>
	<li class="trainingbullet"><span class="text">Wash your hands with the antiseptic soap.<br>
	Wet your Hands.<br>
	Apply enough soap to create a good lather.<br>
	Scrub all surfaces of your hands including backs of hands, between fingers, under fingernails for at least 30 seconds.<br>
	Rinse thoroughly with warm water.<br>
	Dry hands with paper towel.</span></li>
	</p>
	</OL>
</p>
					</td>
				</tr>
			</table>

