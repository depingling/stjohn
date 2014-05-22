
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
					
<span class="trainingsubhead"><b>Dusting and Damp Mopping</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Remove any objects that are in your way. Vacuum or clean the walk-of mats and set aside.</span></li>
	<li class="trainingbullet"><span class="text">Dust mop the entire floor. As you mop, look for any potential problems areas. Remove any material on the floor such as gum. <b>Note: Be careful not to scratch or gouge the floor surface with the putty knife.</b></span></li>
	<li class="trainingbullet"><span class="text">Post wet floor signs. Use more than one sign on the job. Post the signs in a logical manner so passing traffic knows what area you are working on.</span></li>
	<li class="trainingbullet"><span class="text">If you are not using an automatic dilution system, mix the neutral cleaner according to the instructions on the label with cool water. <b>Note: Using hot water can soften and or discolor the finish.</b></span></li>
	<li class="trainingbullet"><span class="text">Dip the mophead into the cleaning solution and wring out. Start at the far end of the area to be mopped and outline a U-shaped section along the baseboard and the edges of the work area. Fill in the U-shaped sections by mopping with an overlapping figure eight pattern. Don’t forget the corners and edges. Be sure to change your cleaning solution frequently as it becomes dirty.</span></li>
	<li class="trainingbullet"><span class="text">You may have to go over the heavier soiled areas more than once. If the soil load is heavy, you may want to consider using a general purpose cleaner. If you choose a general purpose cleaner you will have to recoat the floor with two coats of finish.</span></li>
	<li class="trainingbullet"><span class="text">At the end of your mopping be sure to thoroughly rinse out the mopheads, buckets, wringers, and any other equipment you have used. Store your equipment after you are done.</span></li>
	<li class="trainingbullet"><span class="text">Remove the wet floor signs after the floor is completely dry.</span></li>
	</p>
	</OL>
</p>
<table border=0 class="trainingrulecolor"> 
           <tr><td class="text"><span class="tiptext">Note: If the procedure for heavy soil does not work with a mop you will need to use a floor machine instead. (Refer to the product selector for proper pad selection)</font></td></tr>
          </table>
	

					</td>
				</tr>
			</table>

