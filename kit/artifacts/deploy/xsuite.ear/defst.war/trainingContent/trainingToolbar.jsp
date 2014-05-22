
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.io.File" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>

<!-- adjust images to current state -->
<%
String section = (String)request.getSession().getAttribute("section");
String sub = (String)request.getParameter("sub");
if (sub == null){
  sub = "A";
}

int tabi = 0;

if (section.equals("training_howtoclean.jsp")) {
  // turn on the how to clean button
  tabi = 1;
}
else if (section.equals("training_tips.jsp")) {
  //turn on the training tips button
  tabi = 2;
}
else if (section.equals("safety_and_regulatory.jsp")) {
  // turn on the safety and regulatory button
  tabi = 3;
}
else { 
  // turn on the glossary button
  tabi = 4;
}


String img_row2 = "";

String cw_trainingsecnav1 ="cw_trainingsecnav1off.gif";
String cw_trainingsecnav2 ="cw_trainingsecnav2off.gif";
String cw_trainingsecnav3 ="cw_trainingsecnav3off.gif";
String cw_trainingsecnav4 ="cw_trainingsecnav4off.gif";
String cw_trainingsecnavspacer1 = "cw_trainingsecnavoffspacer.gif";
String cw_trainingsecnavspacer2 = "cw_trainingsecnavoffspacer.gif";
String cw_trainingsecnavspacer3 = "cw_trainingsecnavoffspacer.gif";

switch (tabi) {
  case 4:
    cw_trainingsecnavspacer3 = "cw_trainingsecnavonspacer.gif";
    cw_trainingsecnav4="cw_trainingsecnav4on.gif";    
    img_row2 = ClwCustomizer.getImagePath(session,"cw_glossary.gif");
  break;
  
  case 3:
    cw_trainingsecnav3="cw_trainingsecnav3on.gif";    
	cw_trainingsecnavspacer2 = "cw_trainingsecnavonspacer.gif";
	cw_trainingsecnavspacer3 = "cw_trainingsecnavonspacer.gif";
    img_row2 = ClwCustomizer.getImagePath(session,"cw_safety_and_regulatory.gif");
  break;
  
  case 2:
    cw_trainingsecnav1 = "cw_trainingsecnav1on.gif";
	cw_trainingsecnavspacer1 = "cw_trainingsecnavonspacer.gif";
	cw_trainingsecnavspacer2 = "cw_trainingsecnavonspacer.gif";	
    img_row2 = ClwCustomizer.getImagePath(session,"cw_trainingtips.gif");
  break;
  

  default:
    cw_trainingsecnav2 = "cw_trainingsecnav2on.gif";    
	cw_trainingsecnavspacer1 = "cw_trainingsecnavonspacer.gif";
    img_row2 = ClwCustomizer.getImagePath(session,"cw_traininghowtoclean.gif");
  break;
}

%>

<!-- pickup images -->
<%
String  cw_spacerPath=ClwCustomizer.getImagePath(session, "cw_spacer.gif");
String  cw_trainingsecnavleftPath=ClwCustomizer.getImagePath(session, "cw_trainingsecnavleft.gif");
String  cw_trainingsecnav1Path=ClwCustomizer.getImagePath(session, cw_trainingsecnav1);
String  cw_trainingsecnav2Path=ClwCustomizer.getImagePath(session, cw_trainingsecnav2);
String  cw_trainingsecnav3Path=ClwCustomizer.getImagePath(session, cw_trainingsecnav3);
String  cw_trainingsecnav4Path=ClwCustomizer.getImagePath(session, cw_trainingsecnav4);
String  cw_trainingsecnavspacer1Path=ClwCustomizer.getImagePath(session, cw_trainingsecnavspacer1);
String  cw_trainingsecnavspacer2Path=ClwCustomizer.getImagePath(session, cw_trainingsecnavspacer2);
String  cw_trainingsecnavspacer3Path=ClwCustomizer.getImagePath(session, cw_trainingsecnavspacer3);
%>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
<% Object errors =request.getAttribute("org.apache.struts.action.ERROR");
   if(errors!=null) {
%>
<tr><td><font color=red><html:errors/></font></td></tr>
<%
}
%>

<tr>
<% String longRow1 =
  "<td class=\"trainingdk\" colspan=\"2\" width=\"+Constants.TABLEWIDTH1+\">"+
  "<img src="+cw_trainingsecnavleftPath+" WIDTH=\"102\" HEIGHT=\"25\">"+
  "<a href=\"training.do?section=how\" >"+
  "<img src="+cw_trainingsecnav2Path+" border=\"0\" WIDTH=\"76\" HEIGHT=\"25\">"+
  "</a>"+
  "<img src="+cw_trainingsecnavspacer1Path+" WIDTH=\"4\" HEIGHT=\"25\">"+
  "<a href=\"training.do?section=tips\" >"+
  "<img src="+cw_trainingsecnav1Path+" border=\"0\" WIDTH=\"70\" HEIGHT=\"25\">"+
  "</a>"+
  "<img src="+cw_trainingsecnavspacer2Path+" border=\"0\" WIDTH=\"4\" HEIGHT=\"24\">"+
  "<a href=\"training.do?section=sar\" >"+
  "<img src="+cw_trainingsecnav3Path+" border=\"0\" WIDTH=\"123\" HEIGHT=\"25\">"+
  "</a>"+
  "<img src="+cw_trainingsecnavspacer3Path+" border=\"0\" WIDTH=\"4\" HEIGHT=\"24\">"+
  "<a href=\"training.do?section=gloss\" >"+
  "<img src="+cw_trainingsecnav4Path+" border=\"0\" WIDTH=\"59\" HEIGHT=\"25\">"+
  "</a>"+
  "</td>";  
%>
<%=longRow1%>
</tr>

<tr>
<td class="trainingmed" valign="top" width="<%=Constants.TABLEWIDTH1%>">
<% if(tabi==4) { %>
   <img src="<%=img_row2%>">
   <bean:define id="index" value="<%=sub%>" type="java.lang.String" toScope="request"/>
   <jsp:include flush='true' page="glossary/training_glossary_alpha_nav.jsp"/>      
<% } else { %>
   <img src="<%=img_row2%>">
<% } %>
</td>
</tr>
</table>
  <table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
    <tr>
    <td class="trainingdk"><img src="/<%=storeDir%>/en/images/cw_spacer.gif" WIDTH="<%=Constants.TABLEWIDTH1%>" HEIGHT="3"></td>
    </tr>
 </table>
