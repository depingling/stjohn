
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

<p><span class="trainingsubhead"><b>Operating Room Cleaning Preparation</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Wash your hands with the antiseptic soap.<br>
	Wet your Hands.<br>
	Apply enough soap to create a good lather.<br>
	Scrub all surfaces of your hands including backs of hands, between fingers, under fingernails for at least 30 seconds.<br>
	Rinse thoroughly with warm water.<br>
	Dry hands with paper towel.</span></li>
	<li class="trainingbullet"><span class="text">Put on gloves and personal protective equipment.</span></li>
	<li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Completely rinse out your equipment. You want to avoid contaminating the new cleaning solution with previously used chemicals.</span></li>
	<li class="trainingbullet"><span class="text">Mix or dispense the chemicals for cleaning.<br>
	Wear safety glasses if required by the MSDS (material safety data sheet) to help avoid chemical contact with the eyes.<br>
	<b>Note: If you are mixing your own cleaner, be sure and follow the directions on the label. Using too much cleaner can leave behind residue that acts like a magnet trapping undesirable soils and bacteria. Too little cleaner will not be effective against removing dirt and other contaminants, as well as eliminating bacteria and viruses.</b></span></li>
	<li class="trainingbullet"><span class="text">Fill spray bottles and bucket with appropriate cleaner. Most disinfectants require a new dilution be made on a daily basis. Refer to the label for specific instructions.<br>
	Make sure that the bottle labels correctly represent what is in each bottle.</span></li>
	</p>
	</OL>
</p>

					</td>
				</tr>
			</table>

