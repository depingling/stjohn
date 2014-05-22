
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
<bean:define id="section" value="training_tips.jsp" type="java.lang.String" toScope="session"/>




      <table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
        <tr>
          
          <td>
            <table align="center" border="0" cellpadding="15" cellspacing="0" width="100%" class="text"><tr>
 <tr><td>
<span class="trainingsubhead">New Employee Orientation</span><br>
<span class="text">When employees arrive on the job for the very first time there are basic procedures and policies that they should be aware of.  Your employees should know what is expected of them from the very beginning.  Each employee should be familiar with:</span>

<ul><li class="trainingbullet"><span class="text">How to interact with a customer</span></li>
<li class="trainingbullet"><span class="text">How to report an incident (i.e. accidental damage to customer property)</span></li>
<li class="trainingbullet"><span class="text">What is the security for the building(s)</span></li>
<li class="trainingbullet"><span class="text">What to do when a burglar or fire alarm goes off</span></li>
<li class="trainingbullet"><span class="text">Accountability for equipment</span></li>
<li class="trainingbullet"><span class="text">How to use supplies conservatively</span></li>
<li class="trainingbullet"><span class="text">Health and safety guidelines</span></li>
<li class="trainingbullet"><span class="text">Where to find and how to use a MSDS</span></li>
<li class="trainingbullet"><span class="text">Proper dress code</span></li>
<li class="trainingbullet"><span class="text">Using personal protective equipment </span></li>
<li class="trainingbullet"><span class="text">Respecting the supervisor</span></li>
<li class="trainingbullet"><span class="text">Ordering supplies</span></li>
<li class="trainingbullet"><span class="text">Sexual harassment policy</span></li>
<li class="trainingbullet"><span class="text">Smoking policy</span></li>
<li class="trainingbullet"><span class="text">Alcohol and illegal drug policy</span></li>
<li class="trainingbullet"><span class="text">Theft policy</span></li>
<li class="trainingbullet"><span class="text">Proper work etiquette (no fighting, profanity, threatening, arguing, harassing, weapons)</span></li>
<li class="trainingbullet"><span class="text">What happens when the employee doesn't show up for work or is late to work. No sleeping on the job, visits from friends, or breaks in undesignated areas</span></li></ul>
<span class="text"><p>Need more information and tools for these important topics?  Return to the Training Center and select the New Employee Orientation button for a variety of training information and support.
</p>
<p>Still looking for more information, click here to visit the Building Service Contractors Association International's online store for training tools: <a href="http://www.bscai.org">www.bscai.org.</a></p>

 <table border=0 class="trainingrulecolor"> 
           <tr><td class="text"><span class="tiptext">Note:  If you decide to visit the BSCAI web site, you will be leaving our site.  Why not put us on your favorites list now so it will be easy to return!</span></td></tr>
          </table>
</td></tr>
</table>

		

