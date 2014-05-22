
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
                  <p><span class="trainingsubhead"><b>Blood and Body Fluid Spills Exposure Incident</b></span></p>
                  <p><span class="trainingsubhead"><b>Procedures:</b></span>
                  <ol>
				    <li class="trainingbullet"><span class="text">Wash the exposed area with soap and water as soon as possible. Flush any mucous membrane such as the eyes, nose, or mouth that have come in contact with any fluids.</span></li>
                	<li class="trainingbullet"><span class="text">Notify your supervisor of the accident. Depending on your facility’s guidelines you may have to fill out an incident report.</span></li>
                 	<li class="trainingbullet"><span class="text">Your employer may be responsible for taking the following actions:
                      Drafting a written description of the accident.
                      Testing your blood for infection. This will only be done with your permission.
                      Arranging an appointment with a healthcare professional.<br><br>

                   	  <b>Note:
                      During this procedure, all information gathered from your medical evaluation e.g. blood tests; hospital or clinic visits will remain confidential.</span></li>
        	      </ol>
                  </p>
				</td>
			  </tr>
            </table>
