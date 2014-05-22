
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

<p><span class="trainingsubhead"><b>Operating Room End of Day Cleaning</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Put on personal protective equipment (refer to your facility’s guidelines)</span></li>
	<li class="trainingbullet"><span class="text">Place linens in designated receptacles.</span></li>
	<li class="trainingbullet"><span class="text">Remove debris stuck on the floor with a putty knife or edge tool. Be careful not to  scratch or gouge the floor with the tools. </span></li>
	<li class="trainingbullet"><span class="text">Dust mop areas where the floor is dry and place any debris in specially marked bags or containers.</span></li>
	<li class="trainingbullet"><span class="text">Empty the trash and replace can liners.<br>
	Empty the trash receptacles and replace the trashcan liners.<br>
	When removing the trash liner, gather the ends loosely, do not compact the trash with your hands or feet due to the possibility of needles or sharp objects.<br>
	Seal the liner by closing it at the top.<br>
	Hold the liner away from your body.<br>
	Clean the inside of the trashcan with disinfectant cleaner.<br>
	Replace the liner.<br>
	<b>Note: If you encounter any broken glass or a needle on the floor or furniture do not pick it up with your hands. Use tongs or a dustpan and brush and dispose of it in the designated container.</b></span></li>
	<li class="trainingbullet"><span class="text">Clean all lights and fixtures attached to the walls with disinfectant cleaner.<br>
	Electrical equipment in the room poses a potential electrical hazard. To avoid this risk turn off electrical equipment where appropriate before cleaning.<br>
	<b>Note: Check with your supervisor for specific equipment that should not be cleaned with the disinfectant.</b></span></li>
	<li class="trainingbullet"><span class="text">Spot clean walls with disinfectant cleaner<br>
	Set up a wet floor sign in the doorway. Do not remove the sign until the floor is thoroughly dry.<br>
	Follow the manufacturer’s instructions on the disinfectant label for proper, dilution, application, and temperature in order to achieve maximum results.<br>
	Flood the floor liberally with the disinfectant solution.<br>
	Disinfectant cleaners need to saturate the surface and air dry for several minutes in order to be effective. (refer to the directions on the label of the disinfectant for proper stand or dwell times<br>
	Apply the disinfectant in a figure eight motion.</span></li>
	<li class="trainingbullet"><span class="text">Move the furniture and carts to one side of the room.</span></li>
	<li class="trainingbullet"><span class="text">Clean all equipment and furniture. <br>
	Move only the equipment you have been directed to move.</span></li>
	<li class="trainingbullet"><span class="text">Post a wet floor sign in the doorway.<br>
	Clean the floor following your facilities recommended method: mop and bucket, swing machine, or autoscrubber.<br>
	Follow the manufacturer’s instructions on the disinfectant label for proper, dilution, application, and temperature in order to achieve maximum results.<br>
	Disinfectant cleaners need to saturate the surface and air dry for several minutes in order to be effective.<br>
	Apply disinfectant in a figure eight motion.<br>
	If you are using a swing machine or autoscrubber:<br>
	Scrub the areas that are stained with a more aggressive pad (green or blue). Exercise care when cleaning keeping cleaning solution off of walls. Refer to the machine’s instructions or supervisor for the proper pad.</span></li>
	<li class="trainingbullet"><span class="text">Move all of the equipment to the wet side of the floor.<br>
	Move furniture while the floor is still wet so all the wheels make contact with the disinfectant.</span></li>
	<li class="trainingbullet"><span class="text">Mop or scrub the second half of the floor with disinfectant cleaner.</span></li>
	<li class="trainingbullet"><span class="text">Follow the instructions on the disinfectant cleaner’s label for amount of time the cleaner should stay on the floor. After the specified amount of time on floor, use the squeegee or a wet vac to pick up the excess cleaner. Use a mop to pickup the trails left behind by the vac or squeegee.</span></li>
	<li class="trainingbullet"><span class="text">Move furniture and carts back to their assigned positions.</span></li>
	<li class="trainingbullet"><span class="text">Throw away all cloths and cleaning solutions used in the cleaning procedures in appropriate receptacles.</span></li>
	<li class="trainingbullet"><span class="text">Thoroughly clean and store the equipment in its proper locations.<br>
	Clean and store the equipment in the proper locations.<br>
	Thoroughly rinse out all buckets, pails, and spray bottles.<br>
	Place the mop head in the designated bin for the laundry.</span></li>
	<li class="trainingbullet"><span class="text">Remove your protective clothing, and place in your facility’s designated area for this type of cleaning procedure.</span></li>
	<li class="trainingbullet"><span class="text">Wash your hands with the antiseptic soap.<br>
	Wet your Hands.<br>
	Apply enough soap to create a good lather.<br>
	Scrub all surfaces of your hands including backs of hands, between fingers, under fingernails for at least 30 seconds.<br>
	Rinse thoroughly with warm water.<br>
	Dry hands with paper towel.<br>
	Turn off the faucet with the paper towel so as not to re-contaminate your hands.</span></li>
</p>
	</OL>
</p>
					</td>
				</tr>
			</table>

