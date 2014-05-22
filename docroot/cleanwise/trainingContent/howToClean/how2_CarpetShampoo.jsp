
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



<p><span class="trainingsubhead"><b>Shampooing Procedures</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span>
	<OL type="1">
	<p>
        <li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity
            and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Use appropriate personal protective gear (refer to your facility’s guidelines for proper equipment)</span></li>
	<li class="trainingbullet"><span class="text">Remove any debris on the carpet. </span></li>
	<li class="trainingbullet"><span class="text">Remove any easily moveable furniture from the area to be pre sprayed. Use foil pads or blocks under remaining furniture.</span></li>
	<li class="trainingbullet"><span class="text">Vacuum the carpet thoroughly (refer to vacuuming procedures section)</span></li>
	<li class="trainingbullet"><span class="text">Mix the shampoo solution according to the manufacturer. (refer to label for correct dilution)<br>
	<b>Helpful Hint: Too strong a dilution is as bad as a weak dilution. Overly strong dilutions will leave behind residue that acts like a magnet, trapping dirt resulting in fast resoil times. Weak dilutions will not clean effectively.</b></span></li>
	<li class="trainingbullet"><span class="text">Post wet floor signs. Use more than one sign on the job. Post the signs in a logical manner so passing traffic knows what area you are working on.</span></li>
	<li class="trainingbullet"><span class="text">Remove spots from the carpet. (Refer to spot removal procedures)</span></li>
	<li class="trainingbullet"><span class="text">Pre spray traffic lanes or other heavily soiled areas if needed. (refer to pre spray procedures)</span></li>
	<li class="trainingbullet"><span class="text">Shampoo carpet according to equipment manufacturers recommended instructions. Do not soak carpet or allow machine to stay on one spot when in use.</span></li>
	<li class="trainingbullet"><span class="text">Use a wet vac or extractor to remove shampoo and excess water. When using an extractor for this procedure be sure to turn off solution pump and use only the vacuum</span></li>
	<li class="trainingbullet"><span class="text">To speed up the drying process use one of the following methods:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Use carpet-drying fan.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Restore pile with pile rake<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Open windows and doors where appropriate<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Turn on air conditioning or heat where appropriate<br></span></li>
	<li class="trainingbullet"><span class="text">Remove wet floor signs only when the carpet is completely dry. Make sure to move furniture back into position only when the carpet is thoroughly dry to the touch.</span></li>
	</p>
	</OL>
</p>
	
					</td>
				</tr>
			</table>

