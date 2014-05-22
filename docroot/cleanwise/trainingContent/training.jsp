
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.forms.TrainingForm"%>
<%@ page import="com.cleanwise.service.api.value.*"%>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<% TrainingForm theForm = (TrainingForm) session.getAttribute("TRAINING_FORM");%>
<html:html>
<head>

  <title>Training</title>
  <jsp:include flush='true' page="../userportal/htmlHeaderIncludes.jsp"/>
</head>
<body marginheight="0" topmargin="0" marginwidth="0" leftmargin="0">


<app:checkLogon/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<%
/* determine the correct area of Training  */

String section = (String)request.getParameter("section");
// sub will be the letter of the glossary selected
String sub = (String)request.getParameter("sub");
if(section == null) {
  NoteViewVector nVwV = theForm.getNoteList();
  section =(nVwV != null && nVwV.size()>0)?"how":"tips";
}
if(sub == null)
  sub = "A";    

%>

<% 
String hs = ClwCustomizer.getFilePath(session,"t_cwHeader.jsp");
String fs = ClwCustomizer.getFilePath(session,"t_footer.jsp");
%>
<jsp:include flush='true' page="<%=hs%>"/>

<!-- How To Clean -->
<% if(section.equals("how")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/training_howtoclean.jsp"/>
<% } else if(section.equals("tips")) { %>
   <jsp:include flush='true' page="/trainingContent/trainingTips/training_tips.jsp"/>
<% } else if(section.equals("sar")) { %>
   <jsp:include flush='true' page="/trainingContent/safetyAndRegulatory/safety_and_regulatory.jsp"/>   
   
<% } else if(section.equals("generalBlood")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_GeneralBlood.jsp"/>
<% } else if(section.equals("generalCarpet")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_GeneralCarpet.jsp"/>
<% } else if(section.equals("generalFabric")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_GeneralFabric.jsp"/>
<% } else if(section.equals("generalFloor")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_GeneralFloor.jsp"/>
<% } else if(section.equals("generalHospital")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_GeneralHospital.jsp"/>   

<% } else if(section.equals("BloodBodyFluidSpills")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_BloodBodyFluidSpills.jsp"/>   
<% } else if(section.equals("BodyFluidFinal")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_BodyFluidFinal.jsp"/>   
<% } else if(section.equals("BloodMajorSpillsCarpet")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_BloodMajorSpillsCarpet.jsp"/>   
<% } else if(section.equals("BloodMajorSpillsFloor")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_BloodMajorSpillsFloor.jsp"/>   
<% } else if(section.equals("BloodBorneMinorSpills")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_BloodBorneMinorSpills.jsp"/> 

<% } else if(section.equals("CarpetBonnetBuffing")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_CarpetBonnetBuffing.jsp"/>    
<% } else if(section.equals("CarpetExtraction")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_CarpetExtraction.jsp"/> 
<% } else if(section.equals("CarpetPreSpray")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_CarpetPreSpray.jsp"/> 
<% } else if(section.equals("CarpetShampoo")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_CarpetShampoo.jsp"/> 
<% } else if(section.equals("CarpetSoil")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_CarpetSoil.jsp"/> 
<% } else if(section.equals("CarpetSpot")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_CarpetSpot.jsp"/> 
<% } else if(section.equals("CarpetVac")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_CarpetVac.jsp"/> 
   
<% } else if(section.equals("BasicFabricUpholstery")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_BasicFabricUpholstery.jsp"/> 
        

<% } else if(section.equals("HighSpeed")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_HighSpeed.jsp"/>
<% } else if(section.equals("SprayBuffing")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_SprayBuffing.jsp"/>
<% } else if(section.equals("BurnishingRestorer")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_BurnishingRestorer.jsp"/>
<% } else if(section.equals("BurnishingSpray")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_BurnishingSpray.jsp"/>
<% } else if(section.equals("DryBurnishing")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_DryBurnishing.jsp"/>
<% } else if(section.equals("Dusting")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_Dusting.jsp"/>
<% } else if(section.equals("RestoringProcedures")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_RestoringProcedures.jsp"/> 
<% } else if(section.equals("StrippingAutoscrubber")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_StrippingAutoscrubber.jsp"/>
<% } else if(section.equals("StrippingSwing")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_StrippingSwing.jsp"/>
<% } else if(section.equals("TopAutoscrubber")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_TopAutoscrubber.jsp"/>
<% } else if(section.equals("TopSwing")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_TopSwing.jsp"/>                        


<% } else if(section.equals("CriticalCare")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_CriticalCare.jsp"/>
<% } else if(section.equals("Isolation")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_Isolation.jsp"/>
<% } else if(section.equals("OperatingBetween")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_OperatingBetween.jsp"/>
<% } else if(section.equals("OperatingWashDown")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_OperatingWashDown.jsp"/>
<% } else if(section.equals("OperatingEndDay")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_OperatingEndDay.jsp"/>
<% } else if(section.equals("OperatingEndShift")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_OperatingEndShift.jsp"/>
<% } else if(section.equals("OperatingPrep")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_OperatingPrep.jsp"/> 
<% } else if(section.equals("PatientPrep")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_PatientPrep.jsp"/>
<% } else if(section.equals("PatientDaily")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_PatientDaily.jsp"/>
<% } else if(section.equals("PatientDismissal")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_PatientDismissal.jsp"/>
<% } else if(section.equals("PatientEnd")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_PatientEnd.jsp"/> 
<% } else if(section.equals("PatientWeekly")) { %>
   <jsp:include flush='true' page="/trainingContent/howToClean/how2_PatientWeekly.jsp"/> 
   
<!-- Training Tips -->   
<% } else if(section.equals("newEmployeeOrientation")) { %>
   <jsp:include flush='true' page="/trainingContent/trainingTips/newEmployeeOrientation.jsp"/> 
<% } else if(section.equals("safetyTraining")) { %>
   <jsp:include flush='true' page="/trainingContent/trainingTips/safetyTraining.jsp"/>   
<% } else if(section.equals("stepByStepCleaningPrac")) { %>
   <jsp:include flush='true' page="/trainingContent/trainingTips/stepByStepCleaningPrac.jsp"/>   
<% } else if(section.equals("supervisorTraining")) { %>
   <jsp:include flush='true' page="/trainingContent/trainingTips/supervisorTraining.jsp"/>
   
<!-- Safety And Regulatory -->   
<% } else if(section.equals("backInjuryPrevention")) { %>
   <jsp:include flush='true' page="/trainingContent/safetyAndRegulatory/backInjuryPrevention.jsp"/> 
<% } else if(section.equals("univPrecaut4BBP")) { %>
   <jsp:include flush='true' page="/trainingContent/safetyAndRegulatory/univPrecaut4BBP.jsp"/>
<% } else if(section.equals("hazardCommunication")) { %>
   <jsp:include flush='true' page="/trainingContent/safetyAndRegulatory/hazardCommunication.jsp"/>
<% } else if(section.equals("personalProtectiveEquip")) { %>
   <jsp:include flush='true' page="/trainingContent/safetyAndRegulatory/personalProtectiveEquip.jsp"/>
<% } else if(section.equals("OSHAErgonomicsProgramStandard")) { %>
   <jsp:include flush='true' page="/trainingContent/safetyAndRegulatory/OSHAErgonomicsProgramStandard.jsp"/>

<!-- Glossary -->
<% } else if(section.equals("gloss")) { %>
     
     <%  if(sub.equals("A")){ %>	 
       <jsp:include flush='true' page="/trainingContent/glossary/glossary.jsp"/> 
     <% } else if(sub.equals("B")) { %>	 
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_b.jsp"/>
     <% } else if(sub.equals("C")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_c.jsp"/>
	 <% } else if(sub.equals("D")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_d.jsp"/>
	 <% } else if(sub.equals("E")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_e.jsp"/>
	 <% } else if(sub.equals("F")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_f.jsp"/>
	 <% } else if(sub.equals("G")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_g.jsp"/>
	 <% } else if(sub.equals("H")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_h.jsp"/>
	 <% } else if(sub.equals("I")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_i.jsp"/>
	 <% } else if(sub.equals("J")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_j.jsp"/>
	 <% } else if(sub.equals("K")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_k.jsp"/>
	 <% } else if(sub.equals("L")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_l.jsp"/>
	 <% } else if(sub.equals("M")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_m.jsp"/>
	 <% } else if(sub.equals("N")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_n.jsp"/>
	 <% } else if(sub.equals("O")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_o.jsp"/>
	 <% } else if(sub.equals("P")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_p.jsp"/>
	 <% } else if(sub.equals("Q")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_q.jsp"/>
	 <% } else if(sub.equals("R")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_r.jsp"/>
	 <% } else if(sub.equals("S")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_s.jsp"/>
	 <% } else if(sub.equals("T")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_t.jsp"/>
	 <% } else if(sub.equals("U")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_u.jsp"/>
	 <% } else if(sub.equals("V")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_v.jsp"/>
	 <% } else if(sub.equals("W")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_w.jsp"/>
	 <% } else if(sub.equals("X")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_x.jsp"/>
	 <% } else if(sub.equals("Y")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_y.jsp"/>
     <% } else if(sub.equals("Z")) { %>
	   <jsp:include flush='true' page="/trainingContent/glossary/training_glossary_z.jsp"/>
	 <% } else { %>
	 <% } %>    				
<% } else { %>   
<% } %>

<jsp:include flush='true' page="<%=fs%>"/>


</body>
</html:html>






