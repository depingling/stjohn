
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

<p class="text">The following information should be presented in an in-service format by a supervisor to new or existing employees.  
   This guide can be printed and referred to as a guide to prep and deliver your training.
</p>
<h3>The Hazard Communication / Right to Know</h3>

Facilitators Guide for Hazard Communication In-Service
<br><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Who needs it:</b></span><br>

All employees who may come into contact with a chemical require training on Hazard Communication.  Sometimes Hazard Communication is referred to as Right to Know. 

<br>
<p><b>When should training occur:</b><br>Workers should be trained annually.  We recommended that trainees sign-in at each training session.  Be sure to store the sign-in log in your training file.  This documents attendance.
</p>
<p><b>What should a trainee be able to do following training:</b><br>
Following the training, a trainee should be able to answer the following questions and perform these tasks:
<ul><li class="trainingbullet"><span class="text">What is the importance of Hazard Communication and what has the facility has done to comply?
    </span></li>
    <li class="trainingbullet"><span class="text">What is an MSDS?
    </span></li>
    <li class="trainingbullet"><span class="text">Be able to locate and read an MSDS.
    </span></li>
    <li class="trainingbullet"><span class="text">Be able to locate and read product labels and know the importance of secondary labels.
    </span></li>
    <li class="trainingbullet"><span class="text">Review the correct way to safely use all the products the worker will work with.
    </span></li>
    <li class="trainingbullet"><span class="text">Know what to do in the event of an exposure incident.
    </span></li>
    <li class="trainingbullet"><span class="text">What is personal protective equipment (PPE)?
    </span></li>
    <li class="trainingbullet"><span class="text">Why is PPE important to wear?
    </span></li>
    <li class="trainingbullet"><span class="text">When do I wear Personal Protective Equipment?
    </span></li>
    <li class="trainingbullet"><span class="text">Where can I locate PPE?
    </span></li>
    <li class="trainingbullet"><span class="text">Proper hand washing and use of gloves
    </span></li>
    <li class="trainingbullet"><span class="text">What should I do if I have questions regarding my safety?
    </span></li>
</ul>
</p>

<p class="text">Note:  MSDS is short for <u>M</u>aterial <u>S</u>afety <u>D</u>ata <u>S</u>heet or <u>MSD</u> <u>S</u>heet.
</p>


<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Employer In-service Pre-Work:</b></span>
<ul><li class="trainingbullet"><span class="text">Written Hazard Communication Program - OSHA requires employers to have a written 'Haz Com' program.  
        The written program needs to contain several items.  Among them an inventory of the chemicals housed in the facility 
        by work area and a labeling program that allows employees to read and understand product labels.  Another important area of labeling includes secondary labels, or labels applied to portable bottles such as trigger bottles and squirt bottles.  
        Employers must also collect MSDS for all products that will be used in the facility, keep them up to date and make them easily available to workers at each work site.  Employers must plan to train employees annually, and document the follow through on the training.  
        For any chemical you use, be sure to understand the potential hazards of a particular chemical and be ready to train employees on its safe use.  Remember that hazardous chemicals require personal protective equipment, or PPE.  Be sure to distribute and train employees on their proper use.  
        It may be helpful to refer to the additional training guides in our Training Center, New Employee Orientation.  Or click on the link to view this section now:  <br>
        <a href="<%=currUri%>section=newEmployeeOrientation">New Employee Orientation</a>
    </span></li>
    <li class="trainingbullet"><span class="text">Ensure that all products used are properly labeled.  This includes the original container as well as the trigger bottles or any container used to hold chemical that is not dumped at the end of the work shift.  An example of a chemical container that does not require labeling is the mop bucket.  
        Except for the mop bucket, containers with ready to use product require secondary labels.  Secondary labels are usually self-sticking labels that are applied to a container holding ready to use product, such as a trigger bottle.  Many manufacturers offer labels in 2-3 languages and the better companies will use pictures on the labels to aid in understanding.  
        Pre-printed bottles are also available from some manufacturers however; there may be a charge.
    </span></li>
    <li class="trainingbullet"><span class="text">MSDS - Collect copies of MSDS for all chemicals used.  Develop and maintain a file for MSDS.  In the event of an emergency or worker questions, all MSDS should be quickly accessible by workers on all shifts at each work site.  Be able to communicate the location of the MSDS to employees during the training.


<table align="center"><tr>
    <td class="trainingrulecolor"><span class="tiptext">Professional Tip:  Place copies of your MSDS in a binder and distribute to key locations throughout your facility.  Remember to update these notebooks periodically. For all the products you order, find the MSDS in our MSDS section above - 24 hours a day, 7 days a week.
</span></td>
  </tr></table>
    </span></li>

    <li class="trainingbullet"><span class="text">Employee training - All employees in contact with chemicals need to be trained annually.  Remember to have employees sign a sign-in log.  To find a training sign-in log visit our Training Center, New Employee Orientation department or, click on the link! 
        <br><a href="<%=currUri%>section=newEmployeeOrientation">New Employee Orientation</a>
    </span></li>
    <li class="trainingbullet"><span class="text">While preparing for the in-service, employers should conduct a site inspection to ensure an understanding of the safety needs of workers so that proper equipment can be selected and used.  Check with your safety department to determine the proper completion of any required paperwork that documents your efforts.  OHSA may require a walk through inspection of the facility(s) to determine if the appropriate program is in place.
    </span></li>
    <li class="trainingbullet"><span class="text">Meet with the safety department to determine your facilities Personal Protective Equipment (PPE) procedures and exposure control plan.  For example, there should be gloves and goggles available to all workers who mix chemicals.  In a manufacturing plant, protective eyewear should be available to workers and visitors alike.  If there is a risk of flying debris, you will need to consider a facemask or if feet could be injured, boots may be in order.  
        (Note:  If protective equipment prevents the use of required prescription eyewear, it is the employer's responsibility to source prescription PPE so the employee can work safely.)  If necessary, make arrangements for eyewash stations.  Eyewash stations should be provided in any area an employee could be exposed to corrosive materials and stations must be easily accessible.  For those facilities where employees mix chemicals, it may be helpful to evaluate chemicals both at concentrate and ready to use dilutions.  
        For many highly concentrated dilution control products, the safety profile drops considerably once diluted.  So be sure to look for the use dilution MSDS for these products in the MSDS area of our site.  In preparation of an accident, determine what your exposure control plan should be.  These are the steps that employees and management will need to follow including logging the accident and arranging medical follow up if necessary.
    </span></li>
    <li class="trainingbullet"><span class="text">Select the appropriate Personal Protective Equipment (PPE) required by the facility.  These always include gloves and goggles.  While the use of chemicals requires personal protective equipment, work tasks will also require PPE.  Areas such as the operating room will require a gown, booties and a hairnet but check with your facility for guidance.  When selecting your equipment, ensure that the products meetNIOSH orANSI standards and that the equipment is comfortable and usable.  Uncomfortable goggles that don't get worn can't prevent a splash.
    </span></li>
    <li class="trainingbullet"><span class="text">This training module is a good companion-training module to include with a Personal Protective Equipment or Bloodborne Pathogen in-service.
    </span></li>
</ul>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>In-Service Facilitators Guide</b></span><br>
<span class="text"><b>Props</b> - Prior to the In-Service prepare for, or gather the following:</span><br><br>

<ul><li class="trainingbullet"><span class="text">Exposure Control Plan - Should an employee become injured on the job, know both the immediate and follow up steps as outlined by your facility.
	</span></li>
	<li class="trainingbullet"><span class="text">Personal Protective Equipment (PPE) Samples as appropriate for your facility: Gloves, Goggles, disposable gowns, booties & headwear are a few ideas
	</span></li>
	<li class="trainingbullet"><span class="text">Handouts of a sample MSDS for a product used in the facility by your staff.  Be able to identify and discuss the key sections.
	</span></li>
	<li class="trainingbullet"><span class="text">Review the Employer in-service pre-work for follow up items.
	</span></li>
	<li class="trainingbullet"><span class="text">Prepare a training sign-in log.
	</span></li>
	<li class="trainingbullet"><span class="text">Samples of secondary labels on portable containers or pre-printed bottles.
	</span></li>
</ul>

<span class="text">Prior to the in-service, have all trainees sign a sign-in log.<br><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Review the following agenda for the session with the trainees:</b></span><br>
<ul><li class="trainingbullet"><span class="text">What is the importance of Hazard Communication and what has the facility has done to comply?
    </span></li>
    <li class="trainingbullet"><span class="text">What is an MSDS?
    </span></li>
    <li class="trainingbullet"><span class="text">Be able to locate and read an MSDS
    </span></li>
    <li class="trainingbullet"><span class="text">Be able to locate and read product labels and know the importance of secondary labels.
    </span></li>
    <li class="trainingbullet"><span class="text">Review the correct way to safely use all the products the worker will work with.
    </span></li>
    <li class="trainingbullet"><span class="text">Know what to do in the event of an exposure incident.
    </span></li>
    <li class="trainingbullet"><span class="text">What is Personal Protective Equipment (PPE)?
    </span></li>
    <li class="trainingbullet"><span class="text">Why is PPE important to wear?
    </span></li>
    <li class="trainingbullet"><span class="text">When do I wear Personal Protective Equipment?
    </span></li>
    <li class="trainingbullet"><span class="text">Where can I locate PPE?
    </span></li>
    <li class="trainingbullet"><span class="text">Proper hand washing and use of gloves
    </span></li>
    <li class="trainingbullet"><span class="text">What should I do if I have questions regarding my safety?
    </span></li>
    <li class="trainingbullet"><span class="text">Next Steps?: <i>supervisor to determine</i>
    </span></li>
</ul>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>What is the importance of Hazard Communication?  What has the facility done to comply?
  </b></span><br>
<span class="text">Discuss why employees should be aware of hazards and need a working knowledge of the Hazard Communication Standard.<br>
For employers, obeying the Hazard Communication Standard is the law.  For employees, it's an important part of ensuring their safety on the job.  If workers run any potential risk from a chemical hazard, the Hazard Communication Standard dictates that employees know about the standard and what their rights are.  
Their rights include requesting to see an MSDS at any time, to have questions about the use of the product(s) answered and to be trained on the safe and proper use of each product they work with.</span>  

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>What is an MSDS?
  </b></span><br>
<p class="text">An MSDS is formally known as a <u>M</u>aterial <u>S</u>afety <u>D</u>ata <u>S</u>heet or <u>MSD</u> <u>S</u>heet.  The mission of the MSDS is to document specific information as required by law about the product.  Many different people such as housekeeping staff, management, health and safety personnel and firefighters could potentially use the information on the MSDS.  Because of this, the MSDS contains a variety of information.  All suppliers are required by law to provide an MSDS for the products they provide including new products.  If your facility is using a dilution control program, your manufacturer may have 'use dilution' MSDS for the ready to use products in the system.  While it's required to keep the concentrate MSDS in the facility, many managers will also refer to the 'use dilution'.  Should a splash or spill accident occur with the ready to use formula, the 'use dilution' MSDS are especially helpful.  This is because it's likely the employee could be treated following the less aggressive medical treatment following the use dilution first aid instead of the first aid for the concentrate.
</p>
<p class="text"><b>Be able to locate and read an MSDS.  <Br>
Handout a sample to review.  Spend time reviewing the major sections of the MSDS.</b><br>
Law requires that all employees have quick access to the MSDS at each work site. Handout the sample MSDS and review the key sections with the trainees.  Take this time to review where the MSDS are stored in the facility.
</p>
<p class="text">Basic sections in an MSDS...  Please note:  While the law requires that manufacturers present basic information on an MSDS, some manufacturers include additional information.  The actual look of the MSDS will vary from manufacturer to manufacturer.
</p>
<p class="text">At the minimum, an MSDS must be in English.
</p>

<table border=1  cellspacing=0 width=100%>
<tr> <td><b><span class="text">MSDS Section</span></b>
     </td>
     <td><span class="text"><b>Description</b></span>
     </td>
</tr>
<tr> <td valign=top><span class="text"><b>Name of preparer</b></span>
     </td>
     <td valign=top><span class="text"><b>This generally appears first.  It includes the company name address and contact phone numbers in case the manufacturer of the chemical needs to be identified.  Many of the better manufacturers have a toll free emergency hotline that is available for help 24 hours a day. </b></span>
     </td>
</tr>
<tr> <td valign=top><span class="text"><b>Chemical Identification</b></span>
     </td>
     <td valign=top><span class="text"><b>Lists the product name(s).</b></span>
     </td>
</tr>
<tr> <td valign=top><span class="text"><b>Hazardous Ingredients</b></span>
     </td>
     <td valign=top><span class="text"><b>Lists the hazardous chemicals in the product and their threshold limits for exposure, if any.  For example, if the contact time breathing the chemical had a limit, it would be listed here.  </b></span>
     </td>
</tr>
<tr> <td valign=top><span class="text"><b>Physical Data</b></span>
     </td>
     <td valign=top><span class="text"><b>This section describes the chemical so that it is easy to identify.  It often includes the color and odor or fragrance of the product.  For example, if the product description on the MSDS of the product is yellow and the product involved in the accident is blue, it would be a signal to health care professionals that the either the wrong product or the wrong MSDS was being presented.</b></span>
     </td>
</tr>
<tr> <td valign=top><span class="text"><b>Health Hazards and First Aid</b></span>
     </td>
     <td valign=top><span class="text"><b>This section describes any ill effects that may occur with overexposure of the chemical and gives some quick first aid suggestions.  </b></span>
     </td>
</tr>
<tr> <td valign=top><span class="text"><b>Special Directions for Protection</b></span>
     </td>
     <td valign=top><span class="text"><b>This section lists any special steps to be taken to prevent overexposure to the chemical.  It may include gloves, goggles, masks, and extra ventilation.  </b></span>
     </td>
</tr>
<tr> <td valign=top><span class="text"><b>Explosion & fire data</b></span>
     </td>
     <td valign=top><span class="text"><b>This section lists information the fire department would be especially interested in such as how to put the fire out should the product be involved in a fire.  </b></span>
     </td>
</tr>
<tr> <td valign=top><span class="text"><b>Reactivity Data</b></span>
     </td>
     <td valign=top><span class="text"><b>This section describes if the chemical will react with other chemicals it may become mixed with.  While chemicals should never be mixed together, this data could be of help to firefighters as they determine what to use when putting out a fire.</b></span>
     </td>
</tr>
<tr> <td valign=top><span class="text"><b>Steps for handling leaks or spills</b></span>
     </td>
     <td valign=top><span class="text"><b>This section advises the reader how to properly pickup a spill should it occur.</b></span>
     </td>
</tr></table><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Be able to locate and read product labels and know the importance of secondary labels
  </b></span><br>
<p class="text">For their part in the Hazard Communication Standard, manufacturers are required to put specific information on the product label.  The minimal information required is there to help employees correctly identify the product and includes the product name, the manufacturers name and address, hazard warnings if any and hazardous ingredients.  It's very important that the manufacturers labels remain on the product so that all workers know the correct identity of the product.
</p>
<p class="text">Another important labeling aspect is the secondary label.  Secondary labels are required for use on all containers that will hold chemical longer than one eight-hour shift.  These labels are often referred to as 'crack and peel' labels since they are self-sticking and are usually applied to trigger bottles or squirt bottles.  It is critically important that all employees use bottles that are properly labeled and that bottles contain the chemical listed on the label.  Instruct trainees how to get additional labels if necessary.  Manufacturers often offer secondary labels at no charge.  It's smart to keep a few extra on hand.  
</p>
<p class="text">Properly labeled bottles are a safety feature.  Here's why.  Imagine that Mary decides she needs a bottle of cleaner.  She can't find the correctly labeled bottle so she simply fills the bottle from her closet with the cleaner.  At the end of the shift, she places the bottle back in the closet and goes home for the day.  John works second shift and together Mary and John share the same closet.  When John starts his shift he grabs the bottle of cleaner that Mary prepared and starts work.  Shortly into Johns shift he accidentally gets some cleaner into his eye.  He remembered the emergency procedure his supervisor outlined and grabs the bottle and the MSDS and proceeds to get first aid for the eye.  There is one small problem for John though.  The product he thinks he got into his eye is not the one in the bottle and when the doctor looks at the MSDS and the product, this problem is clear.  The color and the fragrance are all wrong.  The doctor will have to determine the best next step to treat John.  It may be more aggressive than John needs but the doctor needs to be safe.    In any case he's lost time trying to figure out what really got into his eye.  
</p>
<p><span class="trainingsubhead"><b>Keys to remember:</b></span>
<ul><li class="trainingbullet"><span class="text">Never use an unlabeled or mislabeled product. 
    </span></li>
    <li class="trainingbullet"><span class="text">Never put the wrong product into a prelabeled bottle.  
    </span></li>
    <li class="trainingbullet"><span class="text">Always ensure your bottles contain the proper information so the product can be identified.  Magic marker is not acceptable.
    </span></li>
    <li class="trainingbullet"><span class="text">If you need a product label, see your supervisor.
    </span></li>
</ul>
</p>

<table align="center"><tr>
    <td class="trainingrulecolor"><span class="tiptext">Professional Tip:  Be sure to review the manufacturers product labels and review them with your employees.  The label is a great source of information on the safe and effective use of the product.
</span></td>
  </tr></table>



<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Review the correct way to safely use all the products the worker will work with.
  </b></span><br>
<br><span class="text">Take this time to review each product the employees use.  If appropriate discuss how they should be mixed and how each should be used.<br>
Generally, this part of the training is best covered by showing actual product or at the minimum the product label.  It must be recognizable by the employee when he gets back to the job site.  This is also a good time to reinforce special procedures in your facility.  For example, the use of an acid bowl cleaner once a week, a non-acid cleaner the other days.</span>

<p class="text">Take this time to remind employees to never mix chemicals together.  When two products are mixed two things happen.
   <ul>
       <li class="trainingbullet"><span class="text">There could be an unexpected result if there is a reaction.  The stories you hear about poisonous gases floating through a building are true.  Don't become a statistic.  Manufacturers have specially trained chemists creating products that will work according to label instructions.  Let them do their jobs and keep you safe.
       </span></li>
       <li class="trainingbullet"><span class="text">When two products are mixed, there is no MSDS.  Remember Mary and John from a minute ago?  What happens if medical assistance is required and the product is totally unrecognizable?  Oops.
       </span></li>
   </ul>
   <center class="text">Work Smart and Work Safe!</center>
</p>
<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>If an accident occurs, what next?
  </b></span><br>
     <p class="text"> Know what to do in the event of an exposure incident.  Discuss the preferred procedure outlined by your facility.<br>
No one ever expects an emergency but it's important to be ready.  For example should an emergency occur, the employee should address their immediate first aid need first.  This could include removing gloves and goggles and rushing to an eye wash station to flush the eyes from a splash.  If an exposure incident occurs from a blood spill, the employee should immediately remove clothing and safety equipment from the affected area and wash the injury.  You may have special guidelines by work area.  Regardless, once the immediate first aid is addressed, the employee should report the accident to their supervisor so that it may be logged in and any required follow up can be scheduled.
</p>
<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>What is personal protective equipment (PPE)?
  </b></span><br>
      <p class="text">Demonstrate the proper wearing of the safety item(s) each employee needs to wear by work area.  When reviewing the use of disposable work gloves, be sure to cover the proper way to remove a used glove.<br>
Personal protective equipment is often referred to as PPE.  PPE is safety clothing that is worn by workers to keep them safe and protect them from injury while working.  At the minimum, personal protective equipment includes gloves and goggles or face masks.  In health care environments, it can also include gowns, booties and hairnets for cleaning isolation areas, critical care and the operating room.  In a manufacturing facility, it can include work gloves, goggles or facemasks; protective head wear and foot wear and sometimes respiratory and hearing protection.  For your discussion, focus on the key PPE found in your facility such as disposable gloves, goggles, disposable work clothing (gowns, booties and hairnets), work boots and back belts and safety vests may be a few to consider.  Remind employees that PPE is there to enhance their safety on the job and is not a replacement for working safely and smartly everyday!
</p>
<p class="text"><b>Why is PPE important to wear?</b>  Stress the importance of wearing PPE with the employees.<br>
PPE is important to wear because it protects the worker from workplace injury and in some cases disease.  For example, goggles protect the eyes from splashes and debris; gloves protect the hands from chemicals and abuse.  Employers are required to train employees on the use of PPE and provide the appropriate equipment.  Employees are responsible for wearing the equipment, taking care of it and reporting any incidents or special needs to their supervisor such as replacing broken equipment.
</p>
<p class="text"><b>When do I wear Personal Protective Equipment?  Discuss and give examples of PPE for each category or worker in attendance.<br>
PPE should be worn as outlined by each employee's supervisor.  But generally, PPE should be worn in any area(s) an employee could become injured while performing a required task.  Some typical examples are as follows:
<ul>
    <li class="trainingbullet"><span class="text">Housekeepers and custodians should wear gloves and goggles or masks while mixing and using chemicals.  Goggles should be large enough to minimally cover the eye area.  Many chemical manufacturers will specifically recommend safety guidelines as well.  This information can usually be found on the product label.
    </span></li>
    <li class="trainingbullet"><span class="text">At the minimum, all personnel should wear gloves and goggles while cleaning up blood or bodily fluid spills.  
    </span></li>
    <li class="trainingbullet"><span class="text">Some areas require special cleaning procedures such as the operating room, critical care and isolation areas.  These workers will most likely need to 'gown up' with disposable clothing such as one-piece jumpsuits, shoe covers or booties and a hair net.  This is in addition to using gloves and goggles as required.
    </span></li>
    <li class="trainingbullet"><span class="text">Manufacturing facilities may require the use of a hardhat, heavy-duty work gloves and face protection.
    </span></li>
</ul>
</p>
<p class="text"><b>Where can I locate PPE? <br> 
Discuss where the PPE is stored and how employees can access the equipment.
</b><br>
Since employees will need easy access to equipment, it is usually stored near or in the work area.  
<ul>
    <li class="trainingbullet"><span class="text">For housekeepers, goggles and disposable gloves are usually stored in the custodial closet or on rolling carts that move with the employee throughout the work site.  For sure gloves and goggles should be available in locations where chemicals are mixed.  This protects the worker from splashes to the eye and skin.
    </span></li>
    <li class="trainingbullet"><span class="text">Disposable clothing for cleaning special areas such as the operating room and critical care/isolation areas should be stored close to each work site along with receptacles for disposing the used items.
    </span></li>
    <li class="trainingbullet"><span class="text">In a manufacturing environment, boots, goggles or facemasks and heavy duty work gloves are usually stored in the employee locker between shifts.
    </span></li>
    <li class="trainingbullet"><span class="text">From time to time, workers need replacement equipment.  Be sure to review with them how they can get the equipment they need.
    </span></li>
</ul>
</p>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Proper hand washing and use of gloves.
  </b></span><br>
<p class="text">Preventing the spread of infection and employee safety are so important and rely on each other, it pays to review hand washing procedures and glove removal at every opportunity.  
</p>
<span class="text">Proper hand washing is the best prevention against the spread of infection.  Hands should be washed minimally at the beginning of each shift including before gloves are worn, before and after breaks including stops to the restroom, and at the end of the shift.  In heath care environments, your infection control department may have specific guidelines such as washing between each patient or resident room.  
<br><b>All</b> employees must wear gloves when appropriate and participate in proper hand washing procedures.  
<br><br>Demonstrate hand-washing procedures:</span>
<ul>
     <li class="trainingbullet"><span class="text">Wet hands
     </span></li>
     <li class="trainingbullet"><span class="text">Apply hand soap and lather
     </span></li>
     <li class="trainingbullet"><span class="text">Scrub hands for a minimum of 30 seconds including between fingers, under fingernails, and the backs of hands.  (Have someone time this to demonstrate how long 30 seconds is.  It's longer than they think!)
     </span></li>
     <li class="trainingbullet"><span class="text">Rinse hands well with water
     </span></li>
     <li class="trainingbullet"><span class="text">Dry hands with paper towels
     </span></li>
     <li class="trainingbullet"><span class="text">Turn faucet off using the paper towel.  Hands should not directly touch the faucet.  This prevents recontamination of hands.
     </span></li>
     <li class="trainingbullet"><span class="text">Dispose of paper towels
     </span></li>
</ul>
<p class="text">Good hygiene procedures require the use of gloves.  Gloves are an important part of the protection against bloodborne pathogens too and must be worn if there is a possibility of contact with a blood or bodily fluid spill.  While using gloves is important, it is also important to remove soiled gloves properly.  Proper glove removal assists in infection control.
</p>
<table align="center"><tr>
    <td class="trainingrulecolor"><span class="tiptext">Reminder:  Gloves should be properly disposed of following a Universal Precaution cleaning or sooner if they become damaged.  Cuts or wounds should be bandaged before putting gloves on.
</span></td>
  </tr></table>
<br><br><span class="text">Review proper procedures for removing used gloves:</span><ul>
     <li class="trainingbullet"><span class="text">Wet hands
     </span></li>
     <li class="trainingbullet"><span class="text">To remove the first glove, begin removing glove at the wrist.  Turn the glove inside out as you peel it toward your fingers.
     </span></li>
     <li class="trainingbullet"><span class="text">Hold the removed glove in your gloved hand.
     </span></li>
     <li class="trainingbullet"><span class="text">Use the ungloved hand to remove the remaining glove.  Again, start at the wrist and peel the glove inside out toward your fingers.  As you remove the second glove, pull the second glove over the first glove so both gloves are gathered together.
     </span></li>
     <li class="trainingbullet"><span class="text">Dispose of gloves in approved biohazard containers or as outlined by your facility.
     </span></li>
</ul>
<p class="text">Do <i>not</i> plan to wash or decontaminate gloves for reuse.
</p>
<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>What should I do if I have questions regarding my safety?
  </b></span><br>
<span class="text">Discuss where employees can get answers to training following the in-service.</span>
<br>
<p class="text">Most facilities will ask employees to contact their immediate supervisor.  Should your facility require a special procedure, outline it for the employees now.
</p>
<p class="text"><b>Next Steps?: <i>supervisor to determine</i><br>
Do you have any special instructions to share with the employees?  
</b><br>
This would be a good time to distribute any required PPE to employees who may be new or require additional equipment.  Poll employees to determine if they have the correct bottles and labeling for their secondary bottles.
</p>
<p class="text"><b>Questions</b><br>
Before closing the session, address any remaining questions that trainees may have or schedule a time to follow up.
It may be helpful to ask the trainees a few 'checking questions' of your discussion.  This will serve as a review of your discussion and helps clear up any misunderstandings.  Ensure all attendees have signed the sign-in log. 
</p>



<p class="text"><b>Would you like more information on the Hazard Communication Standard?</b><br>
Additional information is available by clicking on the following site: 
<br>
<a href="http://www.cdc.gov/niosh/nasd/docs2/as01000.html">Hazard Communication Information</a><br><br>
Please note that the link above will take you away form our site.  For a quick, easy return, why not add us to your favorites list now!<br>
</p>
<p class="text"><b>Do you have a specific problem to solve?</b><br>  
Visit our <a href="../troubleshooting/troubleshooter.jsp" >Troubleshooter</a> for help.
</p>
</td></tr>
</table>

