
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
   Cracking
  </span><br><br></td></tr>
  <tr><!-- question -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
    <td class="text"><b>Why is my floor finish cracking and how do I fix it?</b></td>
  </tr>
  <tr><!-- horizontal space -->
    <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
  </tr>				
  <tr><!-- answer -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
    <td class="text">
	<p>Cracking can be described as a small maze of cracks throughout the floor finish.  Cracks allow for dirt to get trapped into the finish and you may notice that the floor soils quickly and doesn't seem to come clean after daily cleaning.</p>
	The following can cause cracking:
	   <ul class="text">
	    <li class="troubleshootingbullet"><span class="text"><a href="#1"><span class="subheaders">Low Temperature</span></a></span></li>
	   </ul><br>
     </td>
   </tr>
   <tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
     <td><br><ul class="text">
	  <li class="troubleshootingbullet"><span class="text"><a name="1"><span class="subheaders">Cause</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  First scrub the floor with a general purpose cleaner and a green or blue floor pad.  If after scrubbing, the floor still appears dirty or appears to have cracks, you will need to strip the floor before applying new finish.  Apply the new finish at temperatures above 50 degrees F.
             <p><b>How to Prevent:</b><br>To prevent cracking make sure to apply the finish in a situation where the room temperature and floor temperature are at or above 50 degrees f.  Note that open doors and windows during the colder winter months can cause cracking to occur.   This can be an issue in warehouse application where dock doors are often open.</p>
             </span></li>
          </ul><br>
	</td>
       </tr>
<tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="1" WIDTH="8" HEIGHT="11">
    </td>
   </tr>														
</table>



          </td>
		  <td class="smalltext" valign="up" width="20%"></td>
        </tr>
      </table>
      