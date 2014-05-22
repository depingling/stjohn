
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>



<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% 
String query=request.getQueryString();
String currUri=SessionTool.getActualRequestedURI(request) +"?"; 
if(query!=null){
    currUri+=query+"&";
}
%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>




      <table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
        <tr>
          <td class="smalltext" valign="up" width="20%">
          <td>
		  
		  
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
              <!--    Heading     --> 	
  <tr><td></td><td class="text">
<span class="subheaders">
  Ingredients
  </span><br><br></TD></tr>
<tr> <!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#1">What is d-Limonene?</a></b></td></tr>
<tr> <!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#2">Is d-Limonene safe to use?</a></b></td></tr>
<tr> <!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#3">What is butyl?</a></b></td></tr>
<tr> <!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#4">Is butyl safe to use?</a></b></td></tr>
<tr> <!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#5">What is a solvent?</a></b></td></tr>
<tr> <!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#7">What is the difference between a general purpose cleaner and a degreaser?</a></b></td></tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
<tr> <!-- horizontal rule --> 
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
				
<tr> <!-- question --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
  <td class="text"><b><a name="1"></a>What is d-Limonene?</b></td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr> <!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">d-Limonene is a solvent that comes from the oils of citrus fruits.  (A solvent is an ingredient that helps dissolve or break down soil.)   d-Limonene will work on soils like grease, tire marks, and adhesives.</td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
 <!--          End Answer   --> 
<tr> <!-- horizontal rule --> 
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr> <!-- question --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
  <td class="text"><b><a name="2"></a>Is d-Limonene safe to use?</b></td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr> <!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">D-Limonene comes from oils of citrus fruits but that does not mean that it is not hazardous or harmful to some things. It can eat away at rubber and plastic.  So be careful when using a d-Limonene product on machines.

    <p>Always follow your facility's guidelines for using Personal Protection Equipment (PPE) when using any chemical.  Refer to the Material Safety Data Sheet (MSDS) for recommended PPE and hazard warnings on any product.</p>

Not all products are safe to use on all surfaces.  Always refer to the label if you are not sure if the product will be safe to use for your surface.</td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
 <!--          End Answer   --> 
<tr> <!-- horizontal rule --> 
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr> <!-- question --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
  <td class="text"><b><a name="3"></a>What is butyl?</b></td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr> <!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Butyl is a type of solvent.  (A solvent is an ingredient that helps dissolve or break down soil.)  If looking at a Material Safety Data Sheet (MSDS) you may see butyl listed as ethylene glycol monobutyl ether, EB, or 2-butoxyethanol.  </td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
 <!--          End Answer   --> 
<tr> <!-- horizontal rule --> 
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr> <!-- question --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
  <td class="text"><b><a name="4"></a>Is butyl safe to use?</b></td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr> <!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">It is safe to use a cleaning product with butyl when used according to the label directions.  

    <p>Always follow your facility's guidelines for using Personal Protection Equipment (PPE) when using any chemical.  Refer to the Material Safety Data Sheet (MSDS) for recommended PPE and hazard warnings on any product.</p>

Not all products are safe to use on all surfaces.  Always refer to the label if you are not sure if the product will be safe to use for your surface.</td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
 <!--          End Answer   --> 
<tr> <!-- horizontal rule --> 
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr> <!-- question --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
  <td class="text"><b><a name="5"></a>What is a solvent?</b></td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr> <!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">A solvent is an ingredient that helps dissolve or break down soil.  Cleaners, degreasers, and strippers may have solvents in them to help break down the soil and carry it away.   Some examples of solvents are butyl and d-Limonene.</td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
 <!--          End Answer   --> 
<tr> <!-- horizontal rule --> 
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr> <!-- question --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
  <td class="text"><b><a name="7"></a>What is the difference between a general purpose cleaner and a degreaser?</b></td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr> <!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Degreasers tend to be stronger than a general purpose cleaner.  General purpose cleaners should be used when scrubbing floor finish to prepare it for a new coat, to tackle the daily dirt on an unfinished floor, or to clean objects like desks and chairs.  Degreasers should be used anytime grease, oil, road dirt, or adhesives need to be cleaned away.</td>
</tr>
<tr> <!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
 <!--          End Answer   --> 

<tr> <!-- horizontal rule --> 
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
 <!--          End Answer   --> 
 <!--        QA bottom navigation    --> 
<tr>
  <td colspan="2">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
     <tr>
      <td class="text" colspan="2"><div class="searchmargin">          
      </td>      
     </tr>
 <!--    End Bottom Navigation    --> 
    </table>
   </td>
</tr>
						
</table>

          </td>
		  <td class="smalltext" valign="up" width="20%"></td>
        </tr>
      </table>
      