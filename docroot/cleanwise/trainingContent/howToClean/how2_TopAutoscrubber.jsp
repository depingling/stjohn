
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

<span class="trainingsubhead"><b>Top Scrub and Recoat with an Autoscrubber</p></b></span>

<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Remove any objects that are in your way. Vacuum or clean the walk-of mats and set aside.</span></li>
	<li class="trainingbullet"><span class="text">Dust mop the entire floor. As you mop, look for any potential problems areas. Remove any material on the floor such as gum. <b>Note: Be careful not to scratch or gouge the floor surface with the putty knife.</b></span></li>
	<li class="trainingbullet"><span class="text">Put on appropriate protective equipment</span></li>
	<li class="trainingbullet"><span class="text">Fill the solution tank of the autoscrubber with general cleaning solution. If you are not using an automatic dilution system, mix them according to the instructions on the label with cool water to luke-warm water. Post wet floor signs. Use more than one sign on the job. </span></li>
	<li class="trainingbullet"><span class="text">Post the signs in a logical manner so passing traffic knows what area you are working on.</span></li>
	<li class="trainingbullet"><span class="text">Make one pass over the area with solution valve open and pads (green-blue) down with squeegee up and vacuum off.<br>
	Make a second pass with the vacuum on, squeegee and pads lowered and the solution valve open.</span></li>
	<li class="trainingbullet"><span class="text">If you use the autoscrubber to rinse, you must go back over the floor with a mop to pick up anything left behind by the autoscrubber. Remember to check the rinse water making sure it is clean. Use cold water for the rinsing. <b>Note: Be sure to clean the autoscrubber squeegee blade before rinsing.</b></span></li>
	<li class="trainingbullet"><span class="text">Recoat the floor with finish. (refer to the recoating section in How to Clean)</span></li>
	<li class="trainingbullet"><span class="text">Thoroughly clean your equipment and store it.</span></li>
	<li class="trainingbullet"><span class="text">Remove the wet floor signs only when the floor is completely dry.</span></li>
	</p>
	</OL>
</p>

					</td>
				</tr>
			</table>

