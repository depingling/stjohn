
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




      <table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
        <tr>
          
          <td>
            <table align="center" border="0" cellpadding="15" cellspacing="15" width="100%">
 <tr>
  <td class="text">

<P>
The following information is designed to be presented in an in-service format by a supervisor to new or Existing employees.
</p>
<h3>Personal Protective Equipment - PPE</h3>
Facilitators Guide for Personal Protective Equipment In-Service
<br><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Who needs it:</b></span><br> 

Personal Protective Equipment training is important for any worker who may be injured on the job while performing a task such as mixing chemicals, moving a drum or operating a machine.  The employer needs to provide equipment and training to ensure worker safety from accidents such as chemical spills, direct contact with bloodborne pathogens or flying debris.  The exact safety equipment required by an employee will depend on the job they are required to do.  While personal protective equipment is required on the job, it is not a replacement for working safely.
<br><br>
<p><b>When should training occur?</b><br>Workers should be trained annually.  We recommended that trainees sign-in at each training session.  Be sure to store the sign-in log in your training file.  This documents attendance.  
</p>

<p><b>What should a trainee be able to do following training: </b><br>
Following the training, a trainee should be able to answer the following questions and perform these tasks:<br>
<ul><li class="trainingbullet"><span class="text">What is personal protective equipment (PPE)?
    </span></li>
    <li class="trainingbullet"><span class="text">Why is PPE important to wear?
    </span></li>
    <li class="trainingbullet"><span class="text">When do I wear Personal Protective Equipment?
    </span></li>
    <li class="trainingbullet"><span class="text">Where can I locate PPE?
    </span></li>
    <li class="trainingbullet"><span class="text">What should I do if I have questions regarding my safety?
    </span></li>
    <li class="trainingbullet"><span class="text">What action should be taken if an incident needs to be reported?
    </span></li>
</ul></p>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Employer In-service Pre-Work:</b></span><br>
<ul><li class="trainingbullet"><span class="text">While preparing for the in-service, employers should conduct a site inspection to ensure an understanding of the safety needs of workers so that proper equipment can be selected and used.  Check with your safety department to determine the proper completion of any required paperwork that documents your efforts.  OHSA may require a walk through inspection of the facility(s) to determine if the appropriate program is in place.
    </span></li>
    <li class="trainingbullet"><span class="text">Select the appropriate Personal protective equipment (PPE) required by the facility.  These always include gloves and goggles.  An area such as the operating room will require a gown, booties and a hairnet as well but check with your facility for guidance.  When selecting your equipment, ensure that the products meetNIOSH orANSI standards.  Ensure that the equipment is comfortable and usable.  Uncomfortable goggles that don't get worn can't prevent a splash.
    </span></li>
    <li class="trainingbullet"><span class="text">Meet with the safety department to determine your facilities Personal Protective Equipment (PPE) procedures and exposure control plan.  For example, there should be gloves and goggles available to all workers who mix chemicals.  In a manufacturing plant, protective eyewear should be available to workers and visitors alike.  If there is a risk of flying debris, you will need to consider a facemask or if feet could be injured, boots may be in order.  (Note:  If protective equipment prevents the use of required prescription eyewear, it is the employer's responsibility to source prescription PPE so the employee can work safely.)  If necessary, make arrangements for eyewash stations.  Eyewash stations should be provided in any area an employee could be exposed to corrosive materials and stations must be easily accessible.  For those facilities where employees mix chemicals, it may be helpful to evaluate chemicals both at concentrate and ready to use dilutions.  For many highly concentrated dilution control products, the safety profile drops considerably once diluted.  So be sure to look for the use dilution MSDS for these products in the MSDS area of our site.  In preparation of an accident, determine what your exposure control plan should be.  These are the steps that employees and management will need to follow including logging the accident and arranging medical follow up if necessary.
   </span></li>
   <li class="trainingbullet"><span class="text">You may want to team this training session with other training topics such as <a href="<%=currUri%>section=univPrecaut4BBP">Bloodborne Pathogens.</a>
   </span></li>

<table align="center"><tr>
    <td class="trainingrulecolor"><span class="tiptext">Professional Tip:  Place copies of your MSDS in a binder and distribute to key locations throughout your facility.  Remember to update these notebooks periodically.
</span></td>
  </tr></table>



<li class="trainingbullet"><span class="text">Employee training - All employees in contact with chemicals need to be trained annually.  Remember to have employees sign a sign-in log.
</span></li>
<li class="trainingbullet"><span class="text">
While preparing for the in-service, employers should conduct a site inspection to ensure an understanding of the safety needs of workers so that proper equipment can be selected and used.  Check with your safety department to determine the proper completion of any required paperwork that documents your efforts.  OHSA may require a walk through inspection of the facility(s) to determine if the appropriate program is in place.
</span></li>
<li class="trainingbullet"><span class="text">
Meet with the safety department to determine your facilities Personal Protective Equipment (PPE) procedures and exposure control plan.  For example, there should be gloves and goggles available to all workers who mix chemicals.  In a manufacturing plant, protective eyewear should be available to workers and visitors alike.  If there is a risk of flying debris, you will need to consider a facemask or if feet could be injured, boots may be in order.  (Note:  If protective equipment prevents the use of required prescription eyewear, it is the employer's responsibility to source prescription PPE so the employee can work safely.)  If necessary, make arrangements for eyewash stations.  Eyewash stations should be provided in any area an employee could be exposed to corrosive materials and stations must be easily accessible.  For those facilities where employees mix chemicals, it may be helpful to evaluate chemicals both at concentrate and ready to use dilutions.  For many highly concentrated dilution control products, the safety profile drops considerably once diluted.  So be sure to look for the use dilution MSDS for these products in the MSDS area of our site.  In preparation of an accident, determine what your exposure control plan should be.  These are the steps that employees and management will need to follow including logging the accident and arranging medical follow up if necessary.
</span></li>
<li class="trainingbullet"><span class="text">Select the appropriate Personal protective equipment (PPE) required by the facility.  These always include gloves and goggles.  While the use of chemicals requires personal protective equipment, work tasks will also require PPE.  Areas such as the operating room will require a gown, booties and a hairnet but check with your facility for guidance.  When selecting your equipment, ensure that the products meetNIOSH orANSI standards and that the equipment is comfortable and usable.  Uncomfortable goggles that don't get worn can't prevent a splash.
</span></li>
</ul></p>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Do you have a specific problem to solve?</b></span><br>   
<span class="text">
Visit our <a href="../troubleshooting/troubleshooter.jsp" >Troubleshooter</a> for help.</span>



  </td>
</tr>
</table>

