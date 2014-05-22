
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

<p><span class="trainingsubhead"><b>Dry Burnishing</b></span></p>

<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Use appropriate protective equipment. (refer to your facility’s guidelines for specific gear)</span></li>
	<li class="trainingbullet"><span class="text">Post wet floor signs. Use more than one sign on the job. Post the signs in a logical manner so passing traffic knows what area you are working on.</span></li>
	<li class="trainingbullet"><span class="text">Dust mop the are to be burnished</span></li>
	<li class="trainingbullet"><span class="text">Wet mop the area to be burnished with the neutral cleaner or autoscrub with a red pad.</span></li>
	<li class="trainingbullet"><span class="text">Attach the appropriate pad to the burnisher. Keep extra pads on hand.</span></li>
	<li class="trainingbullet"><span class="text">Make a smooth and steady pass over the floor. Do not let the burnisher sit in one place while it is running. <br>
	Check your pad frequently. If you notice finish, dirt or other particles on the pad, it is time to flip it over or get a clean pad. Failure to change your pad could result in scratching the finish.<br>
	<b>Tip: If you notice powdering, you are probably using too aggressive a pad. Switch to a less abrasive pad.</b></span></li>
	<li class="trainingbullet"><span class="text">Always dust mop your floors after spray buffing or dry buffing.</span></li>
	<li class="trainingbullet"><span class="text">Thoroughly clean equipment and store it.</span></li>
	<li class="trainingbullet"><span class="text">Remove wet floor signs only after the floor is completely dry.</span></li>
	</p>
	</OL>
</p>

					</td>
				</tr>
			</table>

