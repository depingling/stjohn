
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
/* determine the correct area of Troubleshooting  */

String section = (String)request.getParameter("section");
if (section == null){
  section="troubleshooter";
  }
%>


<!-- default to main page -->
<% if(section.equals("troubleshooter")) { %>
   <jsp:include flush='true' page="/troubleshooting/troubleshooter.jsp"/>
<% } else if(section.equals("QA_flooring_AsphaltTile")) { %>
   <jsp:include flush='true' page="/troubleshooting/flooring/QA_flooring_AsphaltTile.jsp"/>
<% } else if(section.equals("QA_flooring_Linoleum")) { %>
   <jsp:include flush='true' page="/troubleshooting/flooring/QA_flooring_Linoleum.jsp"/>
<% } else if(section.equals("QA_flooring_Marble")) { %>
   <jsp:include flush='true' page="/troubleshooting/flooring/QA_flooring_Marble.jsp"/>
<% } else if(section.equals("QA_flooring_QuarryTile")) { %>
   <jsp:include flush='true' page="/troubleshooting/flooring/QA_flooring_QuarryTile.jsp"/>
<% } else if(section.equals("QA_flooring_Rubber")) { %>
   <jsp:include flush='true' page="/troubleshooting/flooring/QA_flooring_Rubber.jsp"/>
<% } else if(section.equals("QA_flooring_Slate")) { %>
   <jsp:include flush='true' page="/troubleshooting/flooring/QA_flooring_Slate.jsp"/>
<% } else if(section.equals("QA_flooring_Terrazzo")) { %>
   <jsp:include flush='true' page="/troubleshooting/flooring/QA_flooring_Terrazzo.jsp"/>
<% } else if(section.equals("QA_flooring_VAT")) { %>
   <jsp:include flush='true' page="/troubleshooting/flooring/QA_flooring_VAT.jsp"/>
<% } else if(section.equals("QA_flooring_VCT")) { %>
   <jsp:include flush='true' page="/troubleshooting/flooring/QA_flooring_VCT.jsp"/>                  
   
<!-- Floor Finish Problems -->
<% } else if(section.equals("flrProblems_Cracking")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_Cracking.jsp"/>    
<% } else if(section.equals("flrProblems_DullGloss")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_DullGloss.jsp"/>
<% } else if(section.equals("flrProblems_DullGloss")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/QA_flooring_VCT.jsp"/>
<% } else if(section.equals("flrProblems_FishEyes")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_FishEyes.jsp"/>
<% } else if(section.equals("flrProblems_Flaking")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_Flaking.jsp"/>           
<% } else if(section.equals("flrProblems_FoamMarks")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_FoamMarks.jsp"/>
<% } else if(section.equals("flrProblems_Ghosting")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_Ghosting.jsp"/>
<% } else if(section.equals("flrProblems_Hazing")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_Hazing.jsp"/>
<% } else if(section.equals("flrProblems_NoInitialGloss")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_NoInitialGloss.jsp"/>
<% } else if(section.equals("flrProblems_Powdering")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_Powdering.jsp"/>
<% } else if(section.equals("flrProblems_RidgingOrRipples")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_RidgingOrRipples.jsp"/>
<% } else if(section.equals("flrProblems_SlipperyFloors")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_SlipperyFloors.jsp"/>
<% } else if(section.equals("flrProblems_StickyFloors")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_StickyFloors.jsp"/>
<% } else if(section.equals("flrProblems_Streaking")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_Streaking.jsp"/>
<% } else if(section.equals("flrProblems_ScratchesAndSwirlMark")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_ScratchesAndSwirlMark.jsp"/>
<% } else if(section.equals("flrProblems_WhiteFilm")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_WhiteFilm.jsp"/>
<% } else if(section.equals("flrProblems_Yellowing")) { %>
   <jsp:include flush='true' page="/troubleshooting/floorFinishProblems/flrProblems_Yellowing.jsp"/>
                              
<!-- General -->
<% } else if(section.equals("qa_general_Degreasers")) { %>
   <jsp:include flush='true' page="/troubleshooting/general/qa_general_Degreasers.jsp"/>
<% } else if(section.equals("qa_general_Ingredients")) { %>
   <jsp:include flush='true' page="/troubleshooting/general/qa_general_Ingredients.jsp"/>
<% } else if(section.equals("qa_general_USDA")) { %>
   <jsp:include flush='true' page="/troubleshooting/general/qa_general_USDA.jsp"/>   		
<% } else { %>   
<% } %>






