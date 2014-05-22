
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

<p><span class="trainingsubhead"><b>Operating Room Complete Wash Down Cleaning</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span><br>
Follow the same procedures for end of day cleaning with the additional steps:<br><br>
	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Put on personal protective equipment (refer to your facility’s guidelines)<br>
	<b>Note: If you encounter any broken glass or a needle on the floor or furniture do not pick it up with your hands. Use tongs or a dustpan and brush and dispose of it the designated container.</b></span></li>
	<li class="trainingbullet"><span class="text">Push all equipment and furniture to the center of the room.</span></li>
	<li class="trainingbullet"><span class="text">Clean all overhead lights and ceiling with disinfectant cleaner.<br>
	Electrical equipment in the room poses a potential electrical hazard. To avoid this risk unplug electrical equipment before cleaning.<br>
	<b>Note: Check with your supervisor for a list of equipment <u>not suited</u> for standard disinfectant cleaning.</b></span></li>
	<li class="trainingbullet"><span class="text">Prepare a two-bucket system to clean the walls.<br>
	One bucket and mop should be used for the disinfectant cleaner and the other for clean water rinsing.</span></li>
	<li class="trainingbullet"><span class="text">Wash the first wall with the disinfectant cleaner using the cleaner mop head and bucket. Apply sufficient pressure to remove any stains or spots on the walls.<br>
	Rinse the mop head in the clear water bucket.<br>
	Be careful not mix up your mops; otherwise you will be spreading the dirty water on the walls and doubling your labor.<br>
	Move from wall to wall until they are all washed.</span></li>
	<li class="trainingbullet"><span class="text">Mop along the baseboards with disinfectant cleaner. Pick up any excess solution that ran down the walls.</span></li>
	<li class="trainingbullet"><span class="text">Clean the following surfaces with a disinfectant cleaner: Door Frames, light switches, fixtures, and outlets.<br>
	<b>Note: When cleaning with a cloth make sure to spray the disinfectant onto a cloth before cleaning. Do not spray the surface to be cleaned including electrical equipment, fixtures, or wall switches. Electrical equipment in the room poses a potential electrical hazard. To avoid this risk turn off electrical equipment where appropriate before cleaning.</b></span></li>
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

