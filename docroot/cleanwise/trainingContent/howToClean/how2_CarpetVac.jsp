
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



<p><span class="trainingsubhead"><b>Vacuuming Procedures</b></span></p>


<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
	<li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity and minimize down time.</span></li>
	<li class="trainingbullet"><span class="text">Remove any debris on the carpet that may foul the vacuum cleaner, e.g. large pieces of paper, metal objects, etc.</span></li>
	<li class="trainingbullet"><span class="text">Vacuum the carpet making sure you clean all accessible areas. Follow the vacuum manufactures instructions for correct operation to ensure maximum results.</span></li>
	<li class="trainingbullet"><span class="text">Following the correct pattern of vacuuming will increase your productivity and efficiency. Start vacuuming at one end of the room and run the vacuum horizontally across the carpet in a diagonal pattern. On your return pass make sure you overlap the first stroke.  See diagram below.
	<br><br>
	<img src="/<%=storeDir%>/<%=ip%>images/vacuum.gif">
	<br><br>
	<b>Helpful Hints:
	<br><br>
	Vacuuming is the most important step in carpet care.<br>
	Vacuuming can eliminate 85% of soil.<br>
	Make sure to adhere to a daily vacuuming schedule.<br>
	Check the recovery bag or canister frequently. Replace the bag before it is completely full for maximum dirt removal. The fuller the bag the less efficient the vacuum is at picking up dirt.<br>
	Inspect belts and brush assemblies on an ongoing basis and replace as necessary.</b></span></li>
	</p>
	</OL>
</p>

					</td>
				</tr>
			</table>

