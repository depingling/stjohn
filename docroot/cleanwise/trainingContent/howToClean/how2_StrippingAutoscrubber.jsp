
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

<span class="trainingsubhead"><b>Stripping with an Autoscrubber</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Put on personal protective equipment.</span></li>
	<li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Remove any objects that are in your way. Vacuum or clean the walk-of mats and set aside.</span></li>
	<li class="trainingbullet"><span class="text">Dust mop the area you are going to strip. As you mop, look for any potential problems areas. Remove any material on the floor such as gum. <b>Note: Be careful not to scratch or gouge the floor surface with the putty knife.</b></span></li>
	<li class="trainingbullet"><span class="text">Protect carpeted spaces by taping plastic sheeting to areas where carpet borders the floor. </span></li>
	<li class="trainingbullet"><span class="text">Use all necessary safety gear. (refer to your facility’s guidelines)</span></li>
	<li class="trainingbullet"><span class="text">Refer to the chemical’s (stripper) label for correct dilution ratio and water temperature.</span></li>
	<li class="trainingbullet"><span class="text">Post wet floor signs. Use more than one sign on the job. Post the signs in a logical manner so passing traffic knows what area you are working on.</span></li>
	<li class="trainingbullet"><span class="text">Dip presoaked mophead into the stripper. Liberally apply the stripping solution to the floor. Under most circumstances do not try to work an area larger than 10’ x 10’. <b>Note: If you are stripping problem floors like grouted tile or heavy buildup, you may need to work in a smaller area.</b> Start at the far end of the area to be stripped and outline a U-shaped section along the baseboard and the edges of the work area. Apply the stripper in manageable sections. Fill in the U-shaped sections by mopping with an overlapping figure eight pattern.</span></li>
	<li class="trainingbullet"><span class="text">Allow the stripping solution to stay on the floor for 7-10 minutes. <b>Caution: Do not allow the stripper to dry on the floor. If the stripper solution begins to dry apply more to the floor.</b></span></li>
	<li class="trainingbullet"><span class="text">Use the edging tool to remove any finish next to the baseboards and in the corners.</span></li>
	<li class="trainingbullet"><span class="text">Make sure the black stripping pads are attached to the autoscrubber. Make a pass over the stripping solution with the vacuum off, squeegee up and pads or brushes down.</span></li>
	<li class="trainingbullet"><span class="text">Use the hand-held floor squeegee to remove stripping solution from the corners and nooks and crannies.</span></li>
	<li class="trainingbullet"><span class="text">With the vacuum on, squeegee and brushes/pads down, make a second pass over the floor. <b>Note: If the floor has excessive build up, you will need to repeat steps 7-13.</b></span></li>
	<li class="trainingbullet"><span class="text">Make a third pass with the vacuum off, brushes/pads down, squeegee up, and the solution valve wide open.</span></li>
	<li class="trainingbullet"><span class="text">Lower the squeegee, turn on the vacuum and pick up the rinse water. If you are using the autoscrubber to rinse, repeat step 14 and 15. Clean off the squeegee blade and remove any stripping solution before the second rinse. <b>Note: Make sure you use clean pads. If you are using a mop, be sure the rinse water stays clean. Rinsing twice is a must.</b></span></li>
	<li class="trainingbullet"><span class="text">If you use the autoscrubber to rinse, you must follow with a mop to pick up any residue left behind the autoscrubber. Check your rinse tank to make sure the water is clean.</span></li>
	<li class="trainingbullet"><span class="text">After the floor has dried, check to see if you have completely removed the stripper solution by rubbing your hand on the floor. If you see any residue on our hand, you must rinse again until you don’t see any when you pass your hand over the floor again. When your rinse water is foam-free you have successfully removed the stripper solution.</span></li>
	<li class="trainingbullet"><span class="text">Clean your equipment thoroughly and store it.</span></li>
	<li class="trainingbullet"><span class="text">Remove wet floor signs.</span></li>
	</p>
	</OL>
</p>
<table border=0 class="trainingrulecolor"> 
           <tr><td class="text"><span class="tiptext">Note: If the floor is open to traffic before a sealer or finish is applied, make sure to clean the floor with a general purpose cleaner and thoroughly rinse before laying down the new sealer or finish.</font></td></tr>
          </table>
		
					</td>
				</tr>
			</table>

