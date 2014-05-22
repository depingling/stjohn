
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
            <table align="center" border="0" cellpadding="15" cellspacing="0" width="100%">
 <tr>
  <td class="text">

<p>The following information should be presented in an in-service format by a supervisor to new or existing employees.  This guide can be printed and referred to as a guide to prep and deliver your training. 
</p>
<h3>Back Injury Prevention</h3>

Facilitators Guide for Back Injury Prevention In-Service
<br><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Who needs it:</b></span><br> 

All employees who lift or may lift heavy objects on the job should be trained in the proper way to handle large and heavy objects.
<br><br>
<p><b>When should training occur:</b><br>Workers should be trained periodically, with annually preferred.  We recommended that trainees sign-in at each training session.  Be sure to store the sign-in log in your training file.  This documents attendance.</p>

<p><b>What should a trainee be able to do following training:</b><br>
Following the training, a trainee should be able to answer the following questions and perform these tasks:
<ul><li class="trainingbullet"><span class="text">Determine whether they should lift a heavy item.
    </span></li>
    <li class="trainingbullet"><span class="text">Be able to know and use safe lifting techniques.
    </span></li>
    <li class="trainingbullet"><span class="text">Be able to know and use tools and Personal Protective Equipment  (PPE) for safe lifting.
    </span></li>
    <li class="trainingbullet"><span class="text">Why is PPE important to wear?  Stress the importance of wearing PPE with the employees.
    </span></li>
    <li class="trainingbullet"><span class="text">When do I wear Personal Protective Equipment?  
    </span></li>
    <li class="trainingbullet"><span class="text">Where can I locate PPE?  
    </span></li>
    <li class="trainingbullet"><span class="text">Know the procedure for reporting an accident.
    </span></li>
    <li class="trainingbullet"><span class="text">Know how to report unsafe conditions on the job.
    </span></li>
    <li class="trainingbullet"><span class="text">Next Steps?: <i>supervisor to determine</i>
    </span></li>
</ul>



<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Employer In-service Pre-Work:</b></span>
<ul><li class="trainingbullet"><span class="text">While preparing for the in-service, employers should conduct a site inspection to ensure an understanding of the lifting needs of workers so that proper equipment can be selected and used.  Check with your safety department to determine the proper completion of any required paperwork that documents your efforts.  
        OHSA may require a walk through inspection of the facility(s) to determine if the appropriate program is in place.
    </span></li>
    <li class="trainingbullet"><span class="text">Meet with the safety department to determine your facilities Personal Protective Equipment (PPE) requirements.  Select the appropriate Personal protective equipment (PPE) required by the facility.  When selecting your equipment, ensure that the products meet NIOSH or ANSI standards and that the equipment is comfortable and usable.  Uncomfortable equipment that doesn't get worn can't prevent an injury.
    </span></li>
    <li class="trainingbullet"><span class="text">In preparation of a potential accident, determine what your accident reporting plan should be.  The plan should outline the steps that employees and management will need to follow including logging the accident and arranging medical follow up if necessary.
    </span></li>
    <li class="trainingbullet"><span class="text">It may be helpful to refer to the additional training guides in our Training Center, New Employee Orientation.  Or click on the link to view this section now: <a href="<%=currUri%>section=newEmployeeOrientation">New Employee Orientation</a>
    </span></li>
    <li class="trainingbullet"><span class="text">Employee training - All employees who may lift heavy objects will require training.  Those employees who may lift during their work day would benefit from training.  Remember to have employees sign a sign-in log. 
    </span></li></ul>

<table align="center"><tr>
    <td class="trainingrulecolor"><span class="tiptext">Professional Tip: Post signs and posters in appropriate 
      areas to remind workers to lift safely.</span></td>
  </tr></table>


<p>
   <ul> <li class="trainingbullet"><span class="text">You may want to team this training session with other topics.  If you would like more information on the training we offer, visit our Training Center!  Or click on the link: 
        <br><a href="<%=currUri%>section=newEmployeeOrientation">New Employee Orientation</a>
    </span></li>
</ul>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>In-Service Facilitators Guide</b></span><br>
<span class="text"><b>Props</b> - Prior to the In-Service prepare for, or gather the following:</span><br><br>
<ul><li class="trainingbullet"><span class="text">Accident Reporting Plan - Should an employee become injured on the job, know both the immediate and follow up steps as outlined by your facility.</span></li>
  
   <li class="trainingbullet"><span class="text">Review the Employer in-service pre-work for follow up items.</span></li>

<li class="trainingbullet"><span class="text">Personal Protective Equipment (PPE) Samples as appropriate for your facility such as: 
                    2 wheel trucks, back belts</span></li>

    <li class="trainingbullet"><span class="text">Prepare a training sign-in log.</span></li>
</ul>


<span class="text">Prior to the in-service, have all trainees sign a sign-in log.</span><br><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Review the following agenda for the session with the trainees:</b></span><br>
<ul><li class="trainingbullet"><span class="text">Determine whether they should lift a heavy item.
    </span></li>
    <li class="trainingbullet"><span class="text">Be able to know and use safe lifting techniques.
    </span></li>
    <li class="trainingbullet"><span class="text">Be able to know and use tools and Personal Protective Equipment  (PPE) for safe lifting.
    </span></li>
    <li class="trainingbullet"><span class="text">Why is PPE important to wear?  Stress the importance of wearing PPE with the employees.
    </span></li>
    <li class="trainingbullet"><span class="text">When do I wear Personal Protective Equipment?  
    </span></li>
    <li class="trainingbullet"><span class="text">Where can I locate PPE?  
    </span></li>
    <li class="trainingbullet"><span class="text">Know the procedure for reporting an accident.
    </span></li>
    <li class="trainingbullet"><span class="text">Know how to report unsafe conditions on the job.
    </span></li>
    <li class="trainingbullet"><span class="text">Next Steps?: <i>supervisor to determine</i>
    </span></li>
</ul>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>What is personal protective equipment (PPE)? Demonstrate the proper wearing
  of the safety item(s) each employee needs to wear by work area. When reviewing 
  the use of disposable work gloves, be sure to cover the proper way to remove 
  a used glove.</b></span><br>
  <span class = "text">Personal protective equipment is often referred to as PPE.  PPE is safety clothing that is worn by workers to keep them safe and protect them from injury while working.  At the minimum, personal protective equipment includes gloves and goggles or face masks.  In health care environments, it can also include gowns, booties and hairnets for cleaning isolation areas, critical care and the operating room.  In a manufacturing facility, it can include work gloves, goggles or facemasks; protective head wear and foot wear and sometimes respiratory and hearing protection.  For your discussion, focus on the key PPE found in your facility such as disposable gloves, goggles, disposable work clothing (gowns, booties and hairnets), work boots and back belts and safety vests may be a few to consider.  Remind employees that PPE is there to enhance their safety on the job and is not a replacement for working safely and smartly everyday!</span>
  <br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Why is PPE important to wear?  Stress the importance of wearing PPE with the employees.</b></span><br>

<span class="text">PPE is important to wear because it protects the worker from workplace injury and in some cases disease.  For example, goggles protect the eyes from splashes and debris; gloves protect the hands from chemicals and abuse.  Employers are required to train employees on the use of PPE and provide the appropriate equipment.  Employees are responsible for wearing the equipment, taking care of it and reporting any incidents or special needs to their supervisor such as replacing broken equipment.</span>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>When do I wear Personal Protective Equipment? Discuss and give examples of
PPE for each category or worker in attendance.</b></span><br>
<span class="text">PPE should be worn as outlined by each employee's supervisor. But generally, PPE 
should be worn in any area(s) an employee could become injured while performing 
a required task. Some typical examples are as follows:</span>

<ul>
  <li class="trainingbullet"><span class="text">Housekeepers and custodians should wear gloves and goggles or masks while 
    mixing and using chemicals. Goggles should be large enough to minimally cover 
    the eye area. Many chemical manufacturers will specifically recommend safety 
    guidelines as well. This information can usually be found on the product label. 
  </span></li>
  <li class="trainingbullet"><span class="text">At the minimum, all personnel should wear gloves and goggles while cleaning 
    up blood or bodily fluid spills. </span></li>
  <li class="trainingbullet"><span class="text">Some areas require special cleaning procedures such as the operating room, 
    critical care and isolation areas. These workers will most likely need to 
    'gown up' with disposable clothing such as one-piece jumpsuits, shoe covers 
    or booties and a hair net. This is in addition to using gloves and goggles 
    as required. </span></li>
  <li class="trainingbullet"><span class="text">Manufacturing facilities may require the use of a hardhat, heavy-duty work 
    gloves and face protection. </span></li>
</ul>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Where can I locate PPE?</b></span><br>
  <span class="text">Discuss where the PPE is stored and how employees can access the equipment. 
  Since employees will need easy access to equipment, it is usually stored near 
  or in the work area. </span>
<ul>
  <li class="trainingbullet"><span class="text">For housekeepers, goggles and disposable gloves are usually stored in the 
    custodial closet or on rolling carts that move with the employee throughout 
    the work site. For sure gloves and goggles should be available in locations 
    where chemicals are mixed. This protects the worker from splashes to the eye 
    and skin. </span></li>
  <li class="trainingbullet"><span class="text">Disposable clothing for cleaning special areas such as the operating room 
    and critical care/isolation areas should be stored close to each work site 
    along with receptacles for disposing the used items. </span></li>
  <li class="trainingbullet"><span class="text">In a manufacturing environment, boots, goggles or facemasks and heavy duty 
    work gloves are usually stored in the employee locker between shifts. </span></li>
  <li class="trainingbullet"><span class="text">From time to time, workers need replacement equipment. Be sure to review 
    with them how they can get the equipment they need. </span></li>
</ul>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>If an accident occurs, what next?</b></span><br>
  <span class="text">Know what to do in the event of an accident. Discuss the preferred procedure 
  outlined by your facility. No one ever expects an emergency but it's important 
  to be ready. For example should an emergency occur, the employee should address 
  their immediate first aid need first. In a back injury it's important to get 
  comfortable, perhaps lie down. The employee should report the accident to their 
  supervisor so that it may be logged in and any required follow up can be scheduled. </span>

<p class="text"><b>Know how to report unsafe conditions on the job.</b></p>
<p class="text"><b>What should I do if I have questions regarding my safety?</b><br>
  Discuss where employees can get answers to training following the in-service.<br>
  Most facilities will ask employees to contact their immediate supervisor. Should 
  your facility require a special procedure, outline it for the employees now. 
</p>
<p class="text"><b><b>Next Steps:</b><i>supervisor to determine</i><br>
Do you have any special instructions to share with the employees?  </b><br>
This would be a good time to distribute any required PPE to employees who may be new or require additional equipment.  Poll employees to determine if they have the correct bottles and labeling for their secondary bottles.
</p>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Questions</b></span><br>
<span class="text">Before closing the session, address any remaining questions that trainees may have or schedule a time to follow up.
It may be helpful to ask the trainees a few 'checking questions' of your discussion.  This will serve as a review of your discussion and helps clear up any misunderstandings.  Ensure all attendees have signed the sign-in log. </span>

<p class="text">Would you like more information on the Hazard Communication Standard?
Additional information is available by clicking on the following site: 
<br>
<a href="http://www.cdc.gov/niosh/nasd/docs2/as01000.html">Hazard Communication Information</a><br><br>
Please note that the link above will take you away form our site.  For a quick, easy return, why not add us to your favorites list now!<br>
</p>
<p class="text">Do you have a specific problem to solve?<br>  
Visit our <a href="../troubleshooting/troubleshooter.jsp" >Troubleshooter</a> for help.
</p>
</td></tr>

</table>

