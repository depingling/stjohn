
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
<bean:define id="section" value="safety_and_regulatory.jsp" type="java.lang.String" toScope="session"/>




      <table align="center" border="0" cellpadding="5" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
        <tr>
          
          <td>
            <table align="center" border="0" cellpadding="15" cellspacing="15" width="100%">
				<tr>
					<td class="text">Please Choose a topic below:
					<br>
						<ul>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=backInjuryPrevention">Back Injury Prevention</a></span></li>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=univPrecaut4BBP">Bloodborne Pathogens</a></span></li>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=hazardCommunication">Hazard Communication Standard</a></span></li>
							<li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=personalProtectiveEquip">Personal Protective Equipment</a></span></li>
                            <li class="trainingbullet"><span class="text"><a href="<%=currUri%>section=OSHAErgonomicsProgramStandard">OSHA Ergonomics Program Standard -1910.900 APP B</a></span></li>
						</ul>
					</td>
				</tr>

			</table>
        </td>
       </table>
