
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

<span class="trainingsubhead"><b>Stripping with a Swing Machine</b></span></p>

<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Remove any objects that are in your way. Vacuum or clean the walk-of mats and set aside.</span></li>
	<li class="trainingbullet"><span class="text">Dust mop the area you are going to strip. As you mop, look for any potential problems areas. Remove any material on the floor such as gum. <b>Note: Be careful not to scratch or gouge the floor surface with the putty knife.</b></span></li>
	<li class="trainingbullet"><span class="text">Protect carpeted spaces by taping plastic sheeting to areas where carpet borders the floor. </span></li>
	<li class="trainingbullet"><span class="text">Use all necessary protective equipment.</span></li>
	<li class="trainingbullet"><span class="text">Mix stripper solution. Be sure to follow directions on label for the correct dilution and temperature.</span></li>
	<li class="trainingbullet"><span class="text">Post wet floor signs. Use more than one sign on the job. The signs must indicate which areas are being stripped.</span></li>
	<li class="trainingbullet"><span class="text">Dip presoaked mophead into the stripper solution. Do not wring out the mophead. Note: Some floors, especially new floors should not be flood stripped.</span></li>
	<li class="trainingbullet"><span class="text">Liberally apply the stripping solution to the floor. Under most circumstances do not try to work an area larger than 10’ x 10’. <b>Note: If you are stripping problem floors like grouted tile or heavy buildup, you may need to work in a smaller area.</b> Start at the far end of the area to be stripped and outline a U-shaped section along the baseboard and the edges of the work area. Apply the stripper in manageable sections. Fill in the U-shaped sections by mopping with an overlapping figure eight pattern.</span></li>
	<li class="trainingbullet"><span class="text">Allow the stripping solution to stay on the floor for 7-10 minutes. <b>Caution: Do not allow the stripper to dry on the floor.</b> If the stripping solution dries on the floor, apply more solution to rewet the floor. </span></li>
	<li class="trainingbullet"><span class="text">Use the edging tool to strip close to the baseboards and in the corners.</span></li>
	<li class="trainingbullet"><span class="text">Install the appropriate stripping pad on the floor machine.</span></li>
	<li class="trainingbullet"><span class="text">Scrub the floor with the machine. Follow machines manual for operation. If there is heavy build-up, reapply the stripper before scrubbing to increase contact time, allow it to stay on floor, and then scrub.<br>
	<b>Caution: Stripping solution makes the floor extremely slippery. Exercise caution when walking especially on areas that have not been stripped.</b></span></li>
	<li class="trainingbullet"><span class="text">Pick up the cleaning solution and solids (slurry) with the wet vacuum, or with a mop. </span></li>
	<li class="trainingbullet"><span class="text">Rinse the floor with clean water and a clean mophead. Apply liberal amounts of water and pick up with wet vacuum or damp mop. Do not use the same mop that was used for applying the stripping solution. <br>
	Note: No rinse strippers still require damp mopping. The term no rinse means that only flood rinsing is not necessary.</span></li>
	<li class="trainingbullet"><span class="text">Repeat step 13.</span></li>
	</p>
	</OL>
</p>

					</td>
				</tr>
			</table>

