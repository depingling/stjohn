
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



<p>
<span class="trainingsubhead"><b>Spot Removal</span></b></p>


<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Use appropriate personal protective gear.</span></li>
	<li class="trainingbullet"><span class="text">Remove any debris on the carpet. </span></li>
	<li class="trainingbullet"><span class="text">Determine where the spot is and try to identify it by specific properties such as color, smell, and feel.</span></li>
	<li class="trainingbullet"><span class="text">Once you have identified the spot’s composition, select an appropriate spotter. Never use a spotter without testing it an in inconspicuous area first.</span></li>
	<li class="trainingbullet"><span class="text">Apply the spotting agent to carpet and work your way from the outside towards the center. Do not completely soak the carpet.</span></li>
	<li class="trainingbullet"><span class="text">After applying the spotter, immediately tamp the area with a soft brush or agitate with a blunt scraper working from the outside of the spot towards the center. This will prevent the spot from spreading.</span></li>
	<li class="trainingbullet"><span class="text">Let the spotter stay on the carpet. Consult the label on the spotter for proper time and directions.</span></li>
	<li class="trainingbullet"><span class="text">Blot the spot with a clean white absorbent cloth. Rinse with water and repeat blotting. Alternatively you can use an extractor with the hand tool accessory. Be sure to set the extractor to the rinse position.</span></li>
	<li class="trainingbullet"><span class="text">If the spot is still present after following these steps, repeat steps 6-9, or try another spotter. Check with your spotting guide for other solutions. Many spots will require more than one application of the spotter.
	<br><br>
	<b>Helpful Hints:
	<br><br>
	Always test the spotter on an inconspicuous area of the carpet before applying. Certain spotters may adversely affect rug carpet color and composition.<br>
	Make sure to thoroughly rinse to avoid leaving behind residue that contributes to resoiling.<br>
	For better removal of older spots, let the spotting agent stay in contact with the carpet for a longer period of time.<br>
	Try not to scrub the carpet too hard. Brushing the carpet too briskly can damage the carpet fibers resulting in an unnatural look.<br>
	Always treat a spot from the outside edge to the center, you will contain it and avoid spreading the spot to other areas of the carpet.<br>
	Do not soak the carpet.<br>
	Exercise extra caution when using agents that contain flammable solvents.<br>
	Refer to the instruction on the spotter label to ensure your safety as well as gain maximum benefit from the spotter.</b></span></li>
	</p>
	</OL>
</p>

					</td>
				</tr>
			</table>

