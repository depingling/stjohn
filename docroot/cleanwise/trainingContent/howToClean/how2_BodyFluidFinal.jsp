
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
                  <p><span class="trainingsubhead"><b>Final Procedures</b></span></p>
				  <span class="trainingsubhead"><b>Procedures:</b></span><br><br>
&nbsp;&nbsp;&nbsp;&nbsp;When you are done with your cleaning procedure:
            	  <ol>
	                <li class="trainingbullet"><span class="text">Carefully remove your gloves.<br><br>
                     <b>Do not touch the outside area of the gloves with your bare hands.</b> To remove the first glove, begin removing glove at the wrist.<br><br>
                 	 Turn the glove inside out as you peel it toward your fingers.<br><br>
                	 Hold the removed glove in your gloved hand. Use the ungloved hand to remove the remaining glove.<br><br>
                 	 Again, start at the wrist and peel the glove inside out toward your fingers. As you remove the second glove, pull the second glove over the first glove so both gloves are gathered together.<br><br>
                 	 <b>Dispose of gloves in approved biohazard containers or as outlined by your facility.</b><br><br>
                 	 If your gloves are disposable or cracked, peeled, or damaged in any way, dispose of them. Follow your facility’s plan for discarding contaminated material.<br><br>
                  	 If your gloves are reusable and they are not damaged, they can be reused but only after they have been decontaminated. Follow your facility’s guidelines for this procedure.<br><br></span></li>
                    <li class="trainingbullet"><span class="text">Wash your hands with the antiseptic soap.<br>
	                  Wet your Hands.<br>
                	  Apply enough soap to create a good lather.<br>
                 	  Scrub all surfaces of your hands including backs of hands, between fingers, under fingernails for at least 30 seconds.<br>
                      Rinse thoroughly with warm water.<br>
                   	  Dry hands with paper towel.</span></li>
                  </OL>
                  </p>
				</td>
			  </tr>
			</table>

