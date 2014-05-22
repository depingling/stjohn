
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



<p><span class="trainingsubhead"><b>Bonnet Buffing Procedures / Spin Bonnet</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
        <li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity
            and minimize down time.
	</span></li>
	<li class="trainingbullet"><span class="text">Use appropriate personal protective gear.</span></li>
	<li class="trainingbullet"><span class="text">Remove any debris on the carpet. </span></li>
	<li class="trainingbullet"><span class="text">Remove any easily moveable furniture from the area to be cleaned. Use foil pads or blocks under remaining furniture.</span></li>
	<li class="trainingbullet"><span class="text">Vacuum the carpet thoroughly (refer to vacuuming procedures section)</span></li>
	<li class="trainingbullet"><span class="text">Post wet floor signs.</span></li>
	<li class="trainingbullet"><span class="text">Remove spots from the carpet. (Refer to spot removal procedures)</span></li>
	<li class="trainingbullet"><span class="text">Mix the bonnet buff solution according to the manufacturer. (refer to label for correct dilution)<br>
	<b>Helpful Hint: Too strong a dilution is as bad as a weak dilution. Overly strong dilutions will leave behind residue that acts like a magnet, trapping dirt resulting in fast resoil times. Weak dilutions will not clean effectively.</b></span></li>
	<li class="trainingbullet"><span class="text"><b>For drier method</b>, spray carpet and yarn pad with sprayer. <b>For Wet method</b>, soak yarn pad in bucket and wring out completely.</span></li>
	<li class="trainingbullet"><span class="text">Center pad under the floor machine.</span></li>
	<li class="trainingbullet"><span class="text">Move machine slowly over the carpet. Focus on highly soiled areas.</span></li>
	<li class="trainingbullet"><span class="text">Check the pad for dirt. When the pad becomes soiled, turn it over. When both sides become soiled rinse in the bonnet solution and re-apply using the wet method. If the pad is heavily soiled and wont come clean in the solution use another pad.</span></li>
	<li class="trainingbullet"><span class="text">Place the carpet-drying fan near the bonnet buffed area and turn on.</span></li>
	<li class="trainingbullet"><span class="text">Remove wet floor signs only when the carpet is completely dry.<br><br>
	<b>Helpful Hints:<br>
	Never use the pad on two different colored carpets without laundering between uses.<br>
	Make sure the pad does not dry during cleaning.<br>
	To speed the drying process and reduce odors, open windows and doors as well as turning on the air conditioning or heat as necessary.<br>
	</b></span></li>
        </p>
	</OL>
</p>
					</td>
				</tr>
			</table>

