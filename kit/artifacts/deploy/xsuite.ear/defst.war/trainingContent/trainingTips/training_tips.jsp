
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
 <td>
<span class="trainingsubhead">Training Tips</span>
<p class="text">Everyday you are challenged to increase employee productivity, produce quality work, reduce customer complaints and minimize damage to customer property.  In addition, you are faced with reducing employee turnover and minimizing employee accidents.  You can improve effectiveness through training and workforce development. You need your business to work smarter and more profitably. To get started here is a basic training curriculum...</p>
<span class="trainingsubhead">Training Curriculum</span>
<table class="text" border="0" cellpadding="0" cellspacing="0" width="100%">
<tr><td colspan="2" class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr>
<tr><td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="6" width="1"></td></tr>
<td class="text">TOPIC</td><td class="text">FREQUENCY</td>
</td>
</tr>
<tr><td class="text">
<a href="<%=currUri%>section=newEmployeeOrientation">New Employee Orientation</a></td>
    <td class="text">Initial hire and as needed</td>
</tr>
<tr><td class="text"><a href="<%=currUri%>section=safetyTraining">Safety Training</a></td>
    <td class="text">Initial hire and as needed</td>
</tr>
<tr><td class="text"><a href="<%=currUri%>section=stepByStepCleaningPrac">Step-By-Step Cleaning Practices</a></td>
    <td class="text">As needed</td>
</tr>
<tr><td class="text"><a href="<%=currUri%>section=supervisorTraining">Supervisor Training</a></td>
    <td class="text">Ongoing</td>
</tr>
<tr><td>&nbsp;</td></tr>
</td></tr>
<tr><td colspan="2" ><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="4" width="1"></td></tr>
<tr><td colspan="2" class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr>
</table>
<br>
<span class="trainingsubhead"><h4>Best Practices</h4></span></hr>
<p class="text">Use several different training approaches to deliver training.</p>
<span class="trainingsubhead">Classroom Training</span>
<ul><li class="trainingbullet"><span class="text">
Classroom training with video can be an efficient and effective means of delivering annual safety/regulatory training (i.e. Hazard Communication).  A large group can be reached and the message delivered is consistent (video).<br><br>
</span></li></ul>

<span class="trainingsubhead">On-The-Job Buddy System</span>
<ul><li class="trainingbullet"><span class="text">
One-on-one training with a designated training professional is the most effective means of providing task-oriented learning, i.e. step-by-step cleaning practices like cleaning a public restroom.
</span></li></ul>
<span class="trainingsubhead">Training Tools</span>
<ul><li class="trainingbullet"><span class="text">
Use tools that present information in different ways; pictures, text, audio, hands-on, etc. Look for training tools in the native language of your trainees.
</span></li></ul>
<span class="trainingsubhead">Training Tip</span>
<ul><li class="trainingbullet"><span class="text">
Training maximizes the opportunity for learning to take place.  You cannot force an individual to learn, but you can create an environment where learning takes place.  Nurture learning and you will reap the rewards.<br><br>
</span></li></ul>

          <table border=0 class="trainingrulecolor"> 
           <tr><td class="text"><span class="tiptext">Learn More About Training Practices by visiting <br>
The American Society of Training and Development at <a href="http://www.astd.org"><span class="tiptext">www.astd.org</span></a>
	  </span></td></tr>
          </table>
</td></tr>
</table>
		

