
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

<p><span class="trainingsubhead"><b>Patient/ Resident Room Weekly Cleaning Procedures</b></span></p>

<span class="trainingsubhead"><b>Procedures:</b></span>
	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Clean with disinfectant cleaner or general purpose cleaner. (according to your facility’s guidelines) Use appropriate personal protective gear. Clean the following:<br>
	-Walls (bottom to top)<br>
	-Ceiling vents<br>
	-T.V. cabinet and brackets<br>
	When you use a spray bottle, make sure to spray the disinfectant on the cleaning cloth rather than directly on the surfaces to be cleaned.
	<li class="trainingbullet"><span class="text">Clean the blinds with a duster
	<li class="trainingbullet"><span class="text">Clean along the baseboards and don’t forget the corners.<br>
	When cleaning the corners use an abrasive pad or a cloth dampened with cleaner. Use your index finger to get into the tight spaces.
	<li class="trainingbullet"><span class="text">If you encounter stubborn deposits in the corner, use a putty knife or edge tool to remove them.
	</p>
	</OL>
</p>
					</td>
				</tr>
			</table>

