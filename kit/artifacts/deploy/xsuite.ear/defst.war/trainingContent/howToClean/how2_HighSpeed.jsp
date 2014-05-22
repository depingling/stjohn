
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
					
<p><span class="trainingsubhead"><b>High Speed Spray Buffing (1500 + rpm)</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span><br><br>
Before spray buffing make sure you complete the maintenance procedures as described in the dusting and damp mopping or top scrubbing sections. Failure to perform these steps prior to spray buffing will grind soils into the finish damaging the floor surface, as well as creating unnecessary additional work. After completing these tasks follow the following steps:

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Put on personal protective equipment.</span></li>
	<li class="trainingbullet"><span class="text">Post wet floor signs. Use more than one sign on the job. Post the signs in a logical manner so passing traffic knows what area you are working on.</span></li>
	<li class="trainingbullet"><span class="text">Attach the appropriate pad to the buffer. Use a pad that is suited for your finish and the type of machine. (refer to the product selector to help you choose the correct pad)</span></li>
	<li class="trainingbullet"><span class="text">Apply the spray buffing solution in a fine mist with a labeled spray bottle. Do not use excessive amounts of the buffer solution. Spraying too much solution on the floor will result in a hazy appearance instead of a glossy shine.</span></li>
	<li class="trainingbullet"><span class="text">Make 1 pass over the spray buff. Check your pad frequently. If you notice dried buffing or particles on the pad, it is time to flip it over or get a clean pad.</span></li>
	<li class="trainingbullet"><span class="text">Always dust mop your floors after spray buffing or dry buffing.</span></li>
	<li class="trainingbullet"><span class="text">Thoroughly clean equipment and store it.</span></li>
	<li class="trainingbullet"><span class="text">Remove wet floor signs only after the floor is completely dry.</span></li>
	</p>
	</OL>
</p>

					</td>
				</tr>
			</table>

