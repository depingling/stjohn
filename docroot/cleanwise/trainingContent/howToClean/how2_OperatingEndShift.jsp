
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

<p><span class="trainingsubhead"><b>Operating Room End of Shift Cleaning</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span>
<br><br>
When completing end of shift cleaning, keep in mind that you may have come in contact with blood or bodily fluids. Exercise caution when cleaning equipment. Follow your facility’s guidelines for cleaning up blood or bodily fluid spills.
<br><br>
	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Clean your equipment thoroughly with disinfectant approved for use with blood and bodily fluid spills. Follow your facility’s guidelines. After cleaning store it in the proper locations.<br>
	<b>Note: Whenever cleaning your equipment wear your rubber gloves to prevent cleaning solution from coming in contact with your skin.<br>
	Thoroughly rinse out all buckets, pails, and spray bottles.<br>
	Place the mop head in the designated bin for the laundry.</b></span></li>
	<li class="trainingbullet"><span class="text">Carefully remove your utility gloves.<br>
	<b>Do not touch the outside area of the gloves with your bare hands.</b> To remove the first glove, begin removing glove at the wrist.<br>
	Turn the glove inside out as you peel it toward your fingers.<br>
	Hold the removed glove in your gloved hand. Use the ungloved hand to remove the remaining glove.<br>
	Again, start at the wrist and peel the glove inside out toward your fingers. As you remove the second glove, pull the second glove over the first glove so both gloves are gathered together.<br>
	<b>Dispose of gloves in approved biohazard containers or as outlined by your facility.</b></span></li>
	<li class="trainingbullet"><span class="text">Wash your hands with the antiseptic soap.<br>
	Wet your Hands.<br>
	Apply enough soap to create a good lather.<br>
	Scrub all surfaces of your hands including backs of hands, between fingers, under fingernails for at least 30 seconds.<br>
	Rinse thoroughly with warm water.<br>
	Dry hands with paper towel.</span></li>
	</p>
	</OL>
</p>
					</td>
				</tr>
			</table>
