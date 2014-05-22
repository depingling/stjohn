
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

<p><span class="trainingsubhead"><b>Applying Restorer</b></span></p>

<span class="trainingsubhead"><b>Step by Step Procedures:</b></span>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Post wet floor signs. Use more than one sign on the job. Post the signs in a logical manner so passing traffic knows what area you are working on.</span></li>
	<li class="trainingbullet"><span class="text">Use appropriate personal protective equipment. (refer to your facility’s guidelines for recommended gear)</span></li>
	<li class="trainingbullet"><span class="text">You may need to dilute the restorer. Make sure to follow the direction on the label of the restorer.</span></li>
	<li class="trainingbullet"><span class="text">Presoak the mophead by dipping the mop in the solution and wringing out all of the excess water.</span></li>
	<li class="trainingbullet"><span class="text">Line the bucked with the trash liner to avoid contaminating the restorer with previously used chemicals. Dilute the restorer. </span></li>
	<li class="trainingbullet"><span class="text">To achieve an even medium coat of restorer, dip the presoaked mophead into the restorer, rotate the mophead ½ turn and press down in the wringer. The mop should not be steadily dripping. An occasional drip is acceptable.</span></li>
	<li class="trainingbullet"><span class="text">Apply the restorer in manageable sections. Outline U-shaped sections about as wide as mop swing from left to right. Fill in the U-shaped section by mopping with a figure eight pattern. Don’t worry about mopping to the baseboards.</span></li>
	<li class="trainingbullet"><span class="text">Allow the coat to dry. Under normal conditions this usually takes about 34-45 minutes. <b>Tip: Do not recoat until the finish is dry to the touch-plus an additional 15 minutes.</b></span></li>
	<li class="trainingbullet"><span class="text">Burnish the restored area with a high-speed burnisher and the proper pad.</span></li>
	<li class="trainingbullet"><span class="text">Dust mop the area</span></li>
	<li class="trainingbullet"><span class="text">Thoroughly mop clean the equipment</span></li>
	<li class="trainingbullet"><span class="text">Remove the wet floor signs only after the floor is completely dry.</span></li>
	</p>
	</OL>
</p>

					</td>
				</tr>
			</table>

