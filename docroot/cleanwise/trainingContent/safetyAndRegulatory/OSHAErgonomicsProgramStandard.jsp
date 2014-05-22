
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


<h3>OSHA Ergonomics Program Standard</h3>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>What is the OSHA Ergonomics Standard?</b></span><br> 

<span class="text">OSHA's standard requires employers to respond to employee reports of work-related musculoskeletal disorders (MSDs) or signs and symptoms of MSDs that last seven days after you report them. If your employer determines that your MSD, or MSD signs or symptoms, can be connected to your job, your employer must provide you with an opportunity to contact a health care professional and receive work restrictions, if necessary. Your wages and benefits must be protected for a period of time while on light duty or temporarily off work to recover. Your employer must analyze the job and if MSD hazards are found, must take steps to reduce those hazards.</span><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Why did OSHA Create the Ergonomics Program Standard?</b></span><br> 

<span class="text">OSHA has issued an ergonomics standard to minimize the incidence of musculoskeletal disorders (MSDs) in workers who are exposed to repetitive motions, awkward postures, contact stress, and vibration. By modifying the work environment to accommodate the worker and matching the right person to specific tasks, MSDs can be reduced and eventually eliminated.</span><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>Who is covered by the standard?</b></span><br> 

<span class="text">All general industry employers are required to follow the rule. The standard does not apply to employers whose primary operations are covered by OSHA's construction, maritime, agricultural standards, or employers who operate a railroad.</span><br>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>What does the rule require employers to do?</b></span><br> 

<span class="text">The rule requires employers to educate their employees about common MSDs, symptoms, signs, and the importance of early diagnosis and reporting. When a worker informs an employer of signs or symptoms of an MSDs, the employer must evaluate the claim and decide whether the injury meets the definition of an MSD incident - a work related MSD that requires medical treatment beyond first aid, assignment to a light duty job or temporary removal from work to recover, or work-related MSD signs or MSD symptoms that last for more than seven consecutive days.</span>

<p class="text">If the incident meets the MSD definition, the employer must check the job using a Basic Screening Tool to determine whether the job exposes the worker to risk factors that could trigger MSD problems. The rule providers a Basic Screening Tool that identifies risk factors that could lead to MSD hazards. If the risk factors on the job meet the levels of exposure in the Basic Screening Tool, then the job will have met the standard's Action Trigger.</p>

<table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="trainingrulecolor"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td></tr></table><br>
<span class="trainingsubhead"><b>What happens when the worker's job meets the standard's Action Trigger?</b></span><br><br>

<span class="text">If the job meets the action trigger the employer must adhere to the following OSHA guidelines:</span>
<ul><li class="trainingbullet"><span class="text">Management Leadership and Employee Participation: The employer must set up an MSD and reporting and response system and an ergonomics program and provide supervisors with resources to run the program. The employer must also assure that polices encourage and do not discourage employee participation in the program, or the reporting of MSDs, MSD signs and symptoms, and MSD hazards.</span>
<p class="text">Employees and their representatives must have ways to report MSDs, MSD signs and symptoms and MSD hazards in the workplace, and receive prompt responses to those reports. Employees must also be given the opportunity to participate in the development, implementation, and evaluation of the ergonomics program.</li>
    <li class="trainingbullet"><span class="text">Job Hazard Analysis and Control: If a job meets the Action Trigger, the employer must conduct a job hazard analysis to determine whether the MSD hazards exist on the job. If hazards are found, the employer must implement control procedures to minimize the hazards. Employees must be involved in the identification and control of hazards.</span></li>
    <li class="trainingbullet"><span class="text">Training: The employer must provide training to employees in jobs that meet the Action Trigger, their supervisors or team leaders and other employees involved in setting up and managing your ergonomics program.</span></li>
    <li class="trainingbullet"><span class="text">MSD Management: Employees must be provided, at no cost, with prompt access to a Health Care Professional (HCP), evaluation and follow-up of an MSD incident, and any temporary work restrictions that the employer or the HCP determine to be necessary. Temporary work restrictions include limitations on the work activities of the employee in his or her current job, transfer of the employee to a temporary alternative duty, job, or temporary removal from work.</span></li>
    <li class="trainingbullet"><span class="text">Work Restriction Protection: Employers must provide Work Restriction Protection (WRP) to employees who receive temporary work restrictions. This means maintaining 100 percent of earnings and full benefits for employees who receive limitations on the work activities in their current job, and 90 percent of earnings and full benefits to employees who are removed from work. WRP is good for 90 days, or until the employee is able to safely return to the job, or until an HCP determines that the employee is too disabled to ever return to the job, which ever comes first.</span></li>
    <li class="trainingbullet"><span class="text">Second Opinion: The standard also contains a process permitting the employee to use his or her own HCP as well as the employer's HCP to determine whether work restrictions are required. A third HCP nay be chosen by the employee and the employer if the first two disagree.</span></li>
    <li class="trainingbullet"><span class="text">Program Evaluation: The employer must evaluate the ergonomics program to make sure it is effective. The employer must ask employees what they think of it, check to see if hazards are being addressed, and make any necessary changes.</span></li>
    <li class="trainingbullet"><span class="text">Record keeping: Employers with 11 or more employees, including part-time employees, must keep written or electronic records of employee reports of MSDs, MSD signs and symptoms and MSD hazards, responses to such reports, job hazard analysis, hazard control measures, ergonomics program evaluations, and records of work restrictions and the HCP's opinions. Employees and their representatives must be provided access to these records.</span></li>
    <li class="trainingbullet"><span class="text">Dates: Employers must begin to distribute information, and receive and respond to employee reports by October 15, 2001. Employers must implement permanent control by November 14, 2004 or two years following determination that a job meets an action Trigger, whichever comes later. Initial controls must be implemented within 90 days after the employer determines that the job meets the Action Trigger. Other obligations are triggered by the employer's determination that the job has met the Action Trigger. </span></li>
</ul>

<p class="text">For the complete ergonomics standard visit OSHA's website <a href="http://www.osha-slc.gov/OshStd_data/1910_0900.html" target="#"> http://www.osha-slc.gov/OshStd_data/1910_0900.html</a><sup>i</sup></p><br>

  <span class="text"><sup>i</sup>http://www.osha-slc.gov/OshStd_data/1910_0900_APP_B.html</span>
  </td>
</tr>
</table>

