
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
                  <p><span class="trainingsubhead"><b>Minor Spills</b></span></p>
                  <span class="trainingsubhead"><b>Procedures:</b></span>
                  <ol>
				    <li class="trainingbullet"><span class="text">Wash your hands with the antiseptic soap.<br>
		Wet your Hands.<br>
		Apply enough soap to create a good lather.<br>
		Scrub all surfaces of your hands including backs of hands, between fingers, under fingernails.<br>
		Rinse thoroughly with warm water.<br>
		Dry hands with paper towel.<br>
		Turn off the faucet with the paper towel so as not to re-contaminate your hands.</span></li>
	                <li class="trainingbullet"><span class="text">Put on rubber gloves and any other protective equipment required by your   facility.</span></li>
	                <li class="trainingbullet"><span class="text">Wear safety glasses if required by the MSDS (material safety data sheet) to help      avoid chemical contact with the eyes.<br>
	If you are mixing your own cleaner, be sure and follow the directions on the label. Using too much cleaner can leave behind residue that acts like a magnet trapping undesirable soils and bacteria. Too little cleaner will not be effective against removing dirt, contaminants, bacteria, and viruses.<br>
	Fill spray bottles and bucket with appropriate cleaner.<br>
	Make sure that the bottle labels correctly represent what is in each bottle.<br><br>
	<b>Note:
	If you encounter any broken glass or a needle on the floor or furniture do not pick it up with your hands. Use tongs or a dustpan and brush and dispose of it the designated container.</b><br><br></span></li>
	                <li class="trainingbullet"><span class="text">Apply the approved disinfectant.<br>
	Make sure to follow the instructions on the disinfectant label. </span></li>
	                <li class="trainingbullet"><span class="text">Contain the spill.<br>
	Use an absorbent barrier approved by your supervisor such as a paper towel, or cloth. Using a barrier will help to control the spreading of the spill.</span></li>
	                <li class="trainingbullet"><span class="text">After the fluid has soaked into the barrier, gather the contaminated material and place it in the biohazard waste receptacle or according to your facility’s blood borne pathogen procedures.<br>
	In order to minimize being contact with the contaminated material, hold it well away from your body and protective equipment as you transport it to the approved disposal container.<br>
	Place the soiled material in the approved container making sure you do not contaminate the outside of the disposal container. <br>
	Search the area for any contaminated material and dispose of properly.</span></li>
	                <li class="trainingbullet"><span class="text">Clean the spill area again with an approved disinfectant.<br>
	Follow the directions on the label for correct results. <br>
	As you apply the disinfectant to the spill, exercise caution. Be careful not to splash.</span></li>
	                <li class="trainingbullet"><span class="text">Throw away any contaminated material by following the guidelines in your facility’s blood borne pathogen procedures.</span></li>
               	    <li class="trainingbullet"><span class="text">Carefully remove your gloves.<br>
	Do not touch the outside area of the gloves with your bare hands. To remove the gloves, hold the glove you are going to take off with your other hand. Slowly pull the glove down over the thumb and towards the tips of your finger and roll the glove off. Place the glove you removed in the palm of the gloved hand. Use the inside out glove you removed to carefully grasp the remaining glove and remove it by peeling it toward the fingertips.</span></li>
	 <li class="trainingbullet"><span class="text">Wash your hands with antiseptic soap.<br>
  	Wet your Hands<br>
	Apply enough soap to create a good lather<br>
	Scrub all surfaces of your hands including backs of hands, between fingers, and under fingernails<br>
	Rinse thoroughly with warm water<br>
	Dry hands with paper towel<br>
	Turn off the faucet with the paper towel so as not to re-contaminate your hands<br>
	Throw paper towel in the trash.</span></li>
        
	</OL>
</p>


					</td>
				</tr>
			</table></td>
          <td class="smalltext" valign="up" width="20%"></td>
        </tr>
      </table>
      <jsp:include flush='true' page="/trainingContent/training_bottom.jsp"/>