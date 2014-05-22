
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
<h3>Bloodborne Pathogens</h3>

Facilitators Guide for Bloodborne Pathogen In-Service
<br><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Who needs it:</b></span><br> 

Bloodborne pathogen training is important for any worker who may come into contact with blood or bodily fluid spills while on the job. 
<br><br>
<p><b>When should training occur:</b><br>Workers should be trained annually.  We recommended that trainees sign-in at each training session.  Be sure to store the sign-in log in your training file.  This documents attendance.
</p>
<p><b>What should a trainee be able to do following training:</b><br>
Following the training, a trainee should be able to answer the following questions and perform these tasks:
<ul><li class="trainingbullet"><span class="text">What is a bloodborne pathogen?
    </span></li>
    <li class="trainingbullet"><span class="text">What are Universal Precautions?
    </span></li>
    <li class="trainingbullet"><span class="text">What can I do to prevent the spread of infection?
    </span></li>
    <li class="trainingbullet"><span class="text">What action does a worker need to take to cleanup a minor spill, a major spill or a spill on carpet?
    </span></li>
    <li class="trainingbullet"><span class="text">What action should be taken if an incident needs to be reported?
    </span></li>
    <li class="trainingbullet"><span class="text">Determine if they accept the Hepatitis B vaccine, if needed.
    </span></li>
</ul>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Employer In-service Pre-Work:</b></span>
<ul><li class="trainingbullet"><span class="text">In addition to the in-service preparation, we recommend reviewing the facilities Exposure Control Plan.  Be able to discuss the plan and the steps an employee should take if they believe they have been involved in an exposure incident.  
        The Exposure Control Plan will usually include the following:
    </span>
    <ul><li class="trainingbullet"><span class="text">Detailed steps the facility will follow should an exposure incident occur.
    </span></li>
    <li class="trainingbullet"><span class="text">Job classifications where employees have the potential for exposure.
    </span></li>
    <li class="trainingbullet"><span class="text">Identify the tasks that could expose an employee to a bloodborne pathogen.
    </span></li>
    <li class="trainingbullet"><span class="text">How the employee will be informed of potential hazards and the protection they can take to be safe.
    </span></li>
    </ul>
    </span></li>
    <li class="trainingbullet"><span class="text">Hepatitis B vaccines must be offered at no charge to employees with possible job-related exposures.  The employee should be offered the vaccine following training and within 10 working days of a job assignment that has potential exposure.  
        If an employee declines the vaccine, the employee should complete and return a form indicating that they have been offered but declined the vaccine.  We recommend reviewing your facilities vaccination plan prior to the training.   
        The training would be a good time to schedule vaccines or review the vaccination decline form.  Be prepared that some employees may ask to check with their personal doctors before accepting or declining the vaccine.
    </span></li>
    <li class="trainingbullet"><span class="text">Personal protective equipment (PPE) required by the facility.  These always include gloves and goggles.  An area such as the operating room will require a gown, booties and headwear as well but check with your facility for guidance.
    </span></li>
    <li class="trainingbullet"><span class="text">Meet with infection control and determine your facilities procedures for proper disposal of contaminated blood spill materials such as paper towels, gloves, mop heads and mop water.  Locate red bags and sharps containers.
    </span></li>
</ul>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>In-Service Facilitators Guide</b></span><br>
<span class="text"><b>Props</b> - Prior to the In-Service, prepare for or gather the following:</span><br><br>
<ul><li class="trainingbullet"><span class="text">Exposure Control Plan - be able to discuss.
	</span></li>
	<li class="trainingbullet"><span class="text">Hepatitis B vaccine plan - be able to discuss and schedule vaccinations.
	</span></li>
	<li class="trainingbullet"><span class="text">Infection control hand washing requirements.  Healthcare facilities usually have requirements for washing hands between patients/residents.
	</span></li>
	<li class="trainingbullet"><span class="text">Personal Protective Equipment (PPE) Samples of: Gloves, 3 pair Goggles, Gowns, booties & headwear (If appropriate).
	</span></li>
	<li class="trainingbullet"><span class="text">Determine the PPE's required in the facility and be able to discuss.
	</span></li>
	<li class="trainingbullet"><span class="text">Determine facilities procedures for proper disposal of contaminated blood spill materials such as paper towels, gloves, mop heads and mop water.
	</span></li>
	<li class="trainingbullet"><span class="text">Wet floor signs
	</span></li>
	<li class="trainingbullet"><span class="text">Mop head with handle
	</span></li>
	<li class="trainingbullet"><span class="text">Hand soap dispenser with antiseptic soap for hand washing demo.  Locate training near sink if possible.  Demonstrate proper procedure.
	</span></li>
	<li class="trainingbullet"><span class="text">Paper towels
	</span></li>
	<li class="trainingbullet"><span class="text">Red disposal bag
	</span></li>
	<li class="trainingbullet"><span class="text">Ready to use Disinfectant cleaner with TB claim in:Trigger bottle, mop and bucket
	</span></li>
	<li class="trainingbullet"><span class="text">Mop bucket with wringer
	</span></li>
	<li class="trainingbullet"><span class="text">Carpet extractor or wet dry vac.
	</span></li>
	<li class="trainingbullet"><span class="text">Biohazard waste container
	</span></li>
	<li class="trainingbullet"><span class="text">Secondary labels for trigger bottles
	</span></li>
</ul>


<span class="text">Have all trainees sign a sign-in log</span><br><br>


<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Review the following agenda for the session with the trainees:</b></span><br>
<ul><li class="trainingbullet"><span class="text">What is a bloodborne pathogen?
	</span></li>
	<li class="trainingbullet"><span class="text">What are Universal Precautions?
	</span></li>
	<li class="trainingbullet"><span class="text">Preventing the spread of infection: proper hand washing and use of gloves
	</span></li>
	<li class="trainingbullet"><span class="text">Performing a blood or bodily fluid spill pickup for a minor spill, a major spill or a spill on carpet.
	</span></li>
	<li class="trainingbullet"><span class="text">What action should be taken if an exposure incident needs to be reported?
	</span></li>
	<li class="trainingbullet"><span class="text">Next Steps: Schedule Hepatitis B vaccine, if needed.
	</span></li>
</ul>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>What is a bloodborne pathogen?  Discuss.
  </b></span><br>
<p class="text"><u>HIV</u> - causes AIDS.  AIDS attacks the body's immune system making the body susceptible to disease.<br>
   <u>HBV</u> - causes a liver infection that can lead to liver cancer, cirrhosis, or chronic liver disease.<br>
   <u>Bodily Fluids</u> - for the purpose of bloodborne pathogens, body fluids include semen and vaginal fluids, cerebrospinal fluids (CSF), synovial fluid, pleural fluid, 
      peritoneal fluid, pericardial fluid and amniotic fluid.  Unless they contain blood, other bodily fluids, secretions such as mucus do not apply to Universal Precautions.<br>
</p>
<p class="text">Although there is only a small risk of contracting a bloodborne illness on the job, it's important to work smart.  An improperly handled spill can result in an exposure incident. 
   Today's training will teach you how to handle a blood or bodily fluid spill.
</p>
  <br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>What are Universal Precautions?  Discuss.
  </b></span><br>
<span class="text">When a blood or bodily fluid spill occurs, workers should follow the CDC's cleanup procedures for Universal Precautions.  Universal Precautions are the infection control steps that need to be used to cleanup a spill.
Since there is no way of knowing if a spill contains a bloodborne pathogen, all blood and bodily fluid spills are assumed to be contaminated.  This eliminates the guesswork.  It's important that all employees learn and follow Universal Precautions. 
Steps to pick up a spill following Universal Precautions will be reviewed later during the in-service.</span><br><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Preventing the spread of infection:  Proper hand washing and use of gloves
  </b></span><br>
<span class="text">Proper hand washing is the best prevention against the spread of infection.  All employees must wear gloves when appropriate and participate in proper hand washing procedures.</span>

<p class="text"><b>Demonstrate hand-washing procedures:
  </b></p>
<ul><li class="trainingbullet"><span class="text">Wet hands
    </span></li>
    <li class="trainingbullet"><span class="text">Apply antiseptic hand soap and lather
    </span></li>
    <li class="trainingbullet"><span class="text">Scrub hands for a minimum of 30 seconds including between fingers, under fingernails, and the backs of hands.  (Have someone time this to demonstrate how long 30 seconds is)
    </span></li>
    <li class="trainingbullet"><span class="text">Rinse hands well with water
    </span></li>
    <li class="trainingbullet"><span class="text">Dry hands with paper towels
    </span></li>
    <li class="trainingbullet"><span class="text">Turn faucet off using the paper towel.  Hands should not directly touch the faucet.  This prevents recontamination of hands.
    </span></li>
    <li class="trainingbullet"><span class="text">Dry hands with paper towels
    </span></li>
    <li class="trainingbullet"><span class="text">Dispose of paper towels
    </span></li>
</ul>
</p>
<p class="text">Hands should be washed minimally at the beginning of each shift, before and after breaks including stops to the restroom, and at the end of the shift.  In heath care environments, your infection control department may 
   have specific guidelines such as washing between each patient or resident room.  
</p>

<p class="text">Good hygiene procedures require the use of gloves.  Gloves are an important part of the protection against bloodborne pathogens and must be worn if there is a possibility of contact with a blood or bodily fluid spill. While using gloves is important, it is also important to remove soiled gloves properly.  Proper glove removal assists in infection control.
</p>
<table align="center"><tr>
    <td class="trainingrulecolor"><span class="tiptext">Reminder:  Gloves should be properly disposed of following a Universal Precaution cleaning or sooner if they become damaged.  Cuts or wounds should be bandaged before putting gloves on.
</span></td>
  </tr></table>


<p class="text">Review proper procedures for removing used gloves:
   <ul><li class="trainingbullet"><span class="text">To remove the first glove, begin removing glove at the wrist.  Turn the glove inside out as you peel it toward your fingers.
       </span></li>
       <li class="trainingbullet"><span class="text">Hold the removed glove in your gloved hand.
       </span></li>
       <li class="trainingbullet"><span class="text">Use the ungloved hand to remove the remaining glove.  Again, start at the wrist and peel the glove inside out toward your fingers.  As you remove the second glove, pull the second glove over the first glove so both gloves are gathered together.
       </span></li>
       <li class="trainingbullet"><span class="text">Dispose of gloves in approved biohazard containers or as outlined by your facility.
       </span></li>
   </ul>
</p>
<p class="text">Do <i>not</i> plan to wash or decontaminate gloves for reuse.
</p>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Performing a blood or bodily fluid spill pickup for a minor spill, a major spill or a spill on carpet.
  </b></span><br>
<span class="text">Since blood and bodily fluid spills come in a variety of ways, it's important to know how to properly clean up each type of spill.  These are the procedures following Universal Precautions.</span>

<p class="text"><b>Demonstrate the procedure for cleaning minor spills:</b>
   <ul>
       <li class="trainingbullet"><span class="text">Wash Hands using proper hand washing procedures as reviewed.
       </span></li>
       <li class="trainingbullet"><span class="text">Put on Personal Protective Equipment (PPE): gloves, goggles.  (Additional equipment such as a gown and booties and headwear may also be required by the facility.)
       </span></li>
       <li class="trainingbullet"><span class="text">Mix disinfectant cleaner with TB claim at proper dilution or use a ready to use disinfectant cleaner with a TB claim.  Tip:  be sure that all trigger bottles of disinfectant are properly labeled
       </span></li>
       <li class="trainingbullet"><span class="text">Spray disinfectant onto spills following label instructions.
       </span></li>
       <li class="trainingbullet"><span class="text">Cover spill with a barrier such as a paper towel.  This reduces the chance of a splash.
       </span></li>
       <li class="trainingbullet"><span class="text">Once the towel has absorbed the fluid, pick up the material and place in approved biohazard waste container.  Tip:  avoid contacting the outside of the disposal container with the contaminated materials.  Do not allow contaminated materials to contact your body or clothing.
       </span></li>
       <li class="trainingbullet"><span class="text">Clean the spill area again with disinfectant.  Be sure to read and follow label instructions.
       </span></li>
       <li class="trainingbullet"><span class="text">Dispose of contaminated materials following your facilities guidelines.
       </span></li>
   </ul>
</p>
<p class="text"><b>Demonstrate the procedure for cleaning major spills:</b>
   <ul>
       <li class="trainingbullet"><span class="text">Wash Hands using proper hand washing procedures as reviewed.
       </span></li>
       <li class="trainingbullet"><span class="text">Put on Personal Protective Equipment (PPE): gloves, goggles.  (Additional equipment such as a gown and booties and headwear may also be required by the facility.)
       </span></li>
       <li class="trainingbullet"><span class="text">Place wet floor signs around spill.
       </span></li>
       <li class="trainingbullet"><span class="text">Mix disinfectant cleaner with TB claim at proper dilution or use a ready to use disinfectant cleaner with a TB claim.  
       </span></li>
       <li class="trainingbullet"><span class="text">Soak mop in disinfectant cleaner.  Without wringing, remove mop from bucket and allow disinfectant to flood over spill.  Avoid splashing. This step disinfects the spill.  Do not allow the mop to come onto contact with the spill right now.  Follow label directions for contact time.
       </span></li>
       <li class="trainingbullet"><span class="text">Resoak mop in disinfectant cleaner in bucket and wring it out.  Damp mop spill area and allow to dry.  Rewet and wring the mop head as needed.
       </span></li>
       <li class="trainingbullet"><span class="text">Clean the spill area again with disinfectant.  Be sure to read and follow label instructions.
       </span></li>
       <li class="trainingbullet"><span class="text">Dispose of contaminated materials such as paper towels, gloves and mop heads and mop water following your facilities guidelines.
       </span></li>
       <li class="trainingbullet"><span class="text">Once floor is dry, remove wet floor signs.
       </span></li>
       <li class="trainingbullet"><span class="text">Rinse mop & bucket.
       </span></li>
   </ul>
</p>
<p class="text"><b>Demonstrate the procedure for cleaning spills on carpets:</b>
   <ul>
       <li class="trainingbullet"><span class="text">Wash Hands using proper hand washing procedures as reviewed.
       </span></li>
       <li class="trainingbullet"><span class="text">Put on Personal Protective Equipment (PPE): gloves, goggles.  (Additional equipment such as a gown and booties and headwear may also be required by the facility.)
       </span></li>
       <li class="trainingbullet"><span class="text">Place wet floor signs around spill.
       </span></li>
       <li class="trainingbullet"><span class="text">Mix disinfectant cleaner with TB claim at proper dilution or use a ready to use disinfectant cleaner with a TB claim.  
       </span></li>
       <li class="trainingbullet"><span class="text">Soak mop in disinfectant cleaner.  Without wringing, remove mop from bucket and allow disinfectant to flood over spill.  Avoid splashing. This step disinfects the spill.  Do not allow the mop to come onto contact with the spill right now.  Follow label directions for contact time.
       </span></li>
       <li class="trainingbullet"><span class="text">Pick up decontaminated spill with a carpet extractor or a wet dry vac.
       </span></li>
       <li class="trainingbullet"><span class="text">Re-apply disinfectant cleaner to spill area following label directions.
       </span></li>
       <li class="trainingbullet"><span class="text">Again use the carpet extractor or a wet dry vac to remove as much disinfectant as possible from the carpet.
       </span></li>
       <li class="trainingbullet"><span class="text">Dispose of contaminated materials such as paper towels, gloves and mop heads and mop water following your facilities guidelines.
       </span></li>
       <li class="trainingbullet"><span class="text">As soon as possible, we recommend a clear water rinse using a carpet extractor.  This removes any disinfectant residue and eliminates early re-soiling of the spot caused by sticky residues.
       </span></li>
       <li class="trainingbullet"><span class="text">Rinse the extractor or vac, bucket and wringer.
       </span></li>
       <li class="trainingbullet"><span class="text">Once carpet is dry, remove wet floor signs.
       </span></li>
   </ul>
<span class="text">The final step in all spill cleanup is glove disposal and proper hand washing.</span>
</p>

<table align="center"><tr>
    <td class="trainingrulecolor"><span class="tiptext">Professional Tip:  
   <ul>
      <li class="reversebullet">If the spill contains any sharp objects that may cut gloves or prevent spill clean up, use tongs to remove sharp objects from the site.  
      </li>
      <li class="reversebullet">Dispose of contaminated objects following your facilities hazardous waster procedures.
      </li>
      <li class="reversebullet">To avoid an unexpected exposure to a bloodborne pathogen, avoid squeezing trash bags and hazardous waste containers.  Doing so could result in a cut or injury.
      </li>
      </span>
   </ul>
   </td>
  </tr></table>
<br>
<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>What action should be taken if an incident needs to be reported?
  </b></span><br>

   <span class="text">An exposure incident occurs if a worker is in direct contact with blood or bodily fluid spills.  This would include being cut or punctured during a cleanup.  Should an exposure incident occur follow the procedures of the facility.</span>

<p class="text">Some quick next steps could include:
   <ul>
      <li class="trainingbullet"><span class="text">Remove gloves or clothing from affected area and wash area with soap and water.  If necessary, flush eyes and nose with water.
      </span></li>
      <li class="trainingbullet"><span class="text">Immediately notify your supervisor of the exposure.  Your supervisor will want a description of the accident and offer you a medical consultation, at no charge.  With your permission, your blood will be tested to see if there is an infection.  All medical information will be kept confidential.
      </span></li>
   </ul>
</p>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Review Next Steps: Hepatitis B vaccine
  </b></span><br>

     <span class="text"> If your job puts you in possible contact with bloodborne pathogens on a regular basis, your employer will offer you the option to have an HBV vaccine, at no charge.  This simply provides an additional level of safety for the worker and should not be a substitute for safe work practices and following Universal Precautions.</span>

<p class="text">Those employees who decline the free vaccination will be asked to sign a form that shows the offer for the shot was declined.
</p>
<p class="text">Note to trainer:  This is a good time to review vaccination dates and schedule appointments.  Those employees who decline the vaccine need to sign the <u>vaccination decline form</u>.  This form needs to be maintained with the facilities administrative records.
</p>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Questions
  </b></span><br>

   <span class="text">Before closing the session, address any remaining questions that trainees may have.
   Ensure all attendees have signed a sign-in log.</span>

<br><br>
<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Would you like more information on the Bloodborne Pathogen Standard?
  </b></span><br>

<span class="text">Additional information is available by clicking on the following site: 
<br>
<a href="http://www.cdc.gov/niosh/nasd/docs2/oacomp.html">Bloodborne Pathogen Information</a><br><br>
Please note that the link above will take you away form our site.  For a quick, easy return, why not add us to your favorites list now!<br>
Once at the site, select the document Standard 1920.1030, Bloodborne Pathogens.  You will require Adobe Acrobat reader to read.</span>

<br><br>
<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="param:cw_spacer_gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Do you have a specific problem to solve?
  </b></span><br>
<p class="text">
Visit our <a href="../troubleshooting/troubleshooter.jsp" >Troubleshooter</a> for help.
</p>
</td></tr>

</table>

