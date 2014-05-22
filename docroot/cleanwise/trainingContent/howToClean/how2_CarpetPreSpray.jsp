
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



<p><span class="trainingsubhead"><b>Pre-Spray Procedures</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
        <li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity
            and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Use appropriate personal protective gear.</span></li>
	<li class="trainingbullet"><span class="text">Remove any debris on the carpet. </span></li>
	<li class="trainingbullet"><span class="text">Remove any easily moveable furniture from the area to be pre sprayed. Use foil pads or blocks under remaining furniture.</span></li>
	<li class="trainingbullet"><span class="text">Vacuum the carpet thoroughly (refer to vacuuming procedures section)</span></li>
	<li class="trainingbullet"><span class="text">Mix the pre-spray according to the manufacturer (refer to label for correct dilution)<br>
	<b>Helpful Hint: Too strong a dilution is as bad as a weak dilution. Overly strong dilutions will leave behind residue that acts like a magnet, trapping dirt resulting in fast resoil times. Weak dilutions will not clean effectively.</b></span></li>
	<li class="trainingbullet"><span class="text">Post wet floor signs. Use more than one sign on the job. Post the signs in a logical manner so passing traffic knows what area you are working on.</span></li>
	<li class="trainingbullet"><span class="text">Spray the product over the entire area</span></li>
	<li class="trainingbullet"><span class="text">Let the product remain on the carpet. Follow the instructions on the label for proper dwell (stand) times.</span></li>
	<li class="trainingbullet"><span class="text">If time allows, agitate the carpet with a carpet groomer or pile rake to loosen dirt for more effective cleaning.</span></li>
	<li class="trainingbullet"><span class="text">Bonnet buff/ spin bonnet or extract the carpet (refer to bonnet buff/ spin bonnet or extraction cleaning procedures)</span></li>
	<li class="trainingbullet"><span class="text">Remove the wet floor signs only when the carpet is completely dry.</span></li>
	</p>
	</OL>
</p>
	
					</td>
				</tr>
			</table>

