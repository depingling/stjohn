
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

<span class="trainingsubhead"><b>Top Scrubbing with a Swing Machine</b></span></p>

<span class="trainingsubhead"><b>Procedures:</b></span>
	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Remove any objects that are in your way. Vacuum or clean the walk-of mats and set aside.</span></li>
	<li class="trainingbullet"><span class="text">Dust mop the entire floor. As you mop, look for any potential problems areas. Remove any material on the floor such as gum. <b>Note: Be careful not to scratch or gouge the floor surface with the putty knife.</b></span></li>
	<li class="trainingbullet"><span class="text">Protect carpeted spaces by taping plastic sheeting to areas where carpet borders the floor. </span></li>
	<li class="trainingbullet"><span class="text">Put on appropriate protective equipment.</span></li>
	<li class="trainingbullet"><span class="text">If you are not using an automatic dilution system, mix the general purpose cleaner according to the instructions on the label. Make sure to follow the specified water temperature. </span></li>
	<li class="trainingbullet"><span class="text">Post wet floor signs. Use more than one sign on the job. Post the signs in a logical manner so passing traffic knows what area you are working on.</span></li>
	<li class="trainingbullet"><span class="text">Liberally apply the general purpose solution to the floor. Under most circumstances do not try to work an area larger than10’ x 10’. Start at the far end of the area to be stripped and outline a U-shaped section along the baseboard and the edges of the work area. Apply the general purpose cleaner in manageable sections. Fill in the U-shaped sections by mopping with an overlapping figure eight pattern.</span></li>
	<li class="trainingbullet"><span class="text">For heavy build-up allow the cleaning solution to stay on the floor for 5-minutes. <b>Caution: Do not allow the general purpose cleaner to dry on the floor. Rewet the floor with general purpose cleaner if it dries.</b></span></li>
	<li class="trainingbullet"><span class="text">Make 2-3 passes over each area with the machine.</span></li>
	<li class="trainingbullet"><span class="text">Pick up the cleaning solution and solids (slurry) with the wet vacuum.</span></li>
	<li class="trainingbullet"><span class="text">Rinse the floor with clean water and a clean mophead. Apply liberal amounts of water and pick up with wet vacuum or mop.<br>
	Apply 2 coats of new finish. <b>(refer to finishing procedure)</b></span></li>
	<li class="trainingbullet"><span class="text">Thoroughly clean your equipment and store it.</span></li>
	<li class="trainingbullet"><span class="text">Remove the wet floor signs once the floor is completely dry.</span></li>
	</p>
	</OL>
</p>

					</td>
				</tr>
			</table>

