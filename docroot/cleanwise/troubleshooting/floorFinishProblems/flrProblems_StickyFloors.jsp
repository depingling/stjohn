
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
  Sticky Floors
  </span><br><br></td></tr>
  <tr><!-- question -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
    <td class="text"><b>Why is my floor finish sticky?</b></td>
  </tr>
  <tr><!-- horizontal space -->
    <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
  </tr>				
  <tr><!-- answer -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
    <td class="text">
	<p>A sticky floor is a floor that is tacky to the touch or a floor where you may feel a grab on the soles of your shoes.  Sticky floors will attract dirt and residue and the floor will quickly appear dirty.  Sticky floors are almost always caused by a procedural error in the daily maintenance so try to pinpoint what has caused the sticky situation and train everyone on how to avoid it.  Since this problem can escalate labor and chemical costs it is cost effective to correct the procedure that created the problem.</p>
	The following can cause sticky floors:
	   <ul class="text">
	    <li class="troubleshootingbullet"><span class="text"><a href="#1"><span class="subheaders">Incorrect dilutions</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#2"><span class="subheaders">Disinfectant cleaners</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#3"><span class="subheaders">Dirty mops and dirty mop water</span></a></span></li>
          </ul><br>
     </td>
   </tr>
   <tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
     <td><br><ul class="text">
	  <li class="troubleshootingbullet"><span class="text"><a name="1"><span class="subheaders">Incorrect dilutions</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  In most cases, scrubbing with a general purpose cleaner and a green or blue pad, and then recoating with new finish will be sufficient enough to correct the problem.  In severe cases you may need to strip all of the finish.
             <p><b>How to Prevent:</b><br>Make sure to use the correct dilutions.  Do not try to save money here by using a weak dilution of the product because in the long run you will end up spending more money in labor.  On the flip side, do not make a super strong dilution of the product because you may harm the floor finish and will most likely leave behind residue.  Residue will become tacky and attract dirt like a magnet.</p>
             <table align="right"><tr><td align="right" class="text"><div class="searchmargin"><a href="#">[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top</a></div></td></tr></table></span></li>
          </ul><br>
	</td>
       </tr>
<tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="1" WIDTH="8" HEIGHT="11"></td>
     <td><br>
         <ul class="text">

	  <li class="troubleshootingbullet"><span class="text"><a name="2"><span class="subheaders">Disinfectant cleaners</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  In most cases, scrubbing with a general purpose cleaner and a green or blue pad, and then recoating with new finish will be sufficient enough to correct the problem.  In severe cases you may need to strip all of the finish. 
             <p><b>How to Prevent:</b><br>While disinfectant cleaners are great at disinfecting, they may not always do a great job of cleaning heavy soil loads.  Continue to use the disinfectant but try using a stronger general purpose cleaner at least once a week to prevent buildup of dirt or residue not picked up with the disinfectant cleaner.</p>
             <table align="right"><tr><td align="right" class="text"><div class="searchmargin"><a href="#">[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top</a></div></td></tr></table></span></li>
          </ul><br>
	</td>
       </tr>
<tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="1" WIDTH="8" HEIGHT="11"></td>
     <td><br>
         <ul class="text">
	  <li class="troubleshootingbullet"><span class="text"><a name="3"><span class="subheaders">Dirty mops and dirty mop water</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  In most cases, scrubbing with a general purpose cleaner and a green or blue pad, and then recoating with new finish will be sufficient enough to correct the problem.  In severe cases you may need to strip all of the finish. 
             <p><b>How to Prevent:</b><br>Use clean pads and flip or change them often.  Always use clean mops and change the mop water frequently.  Using dirty mops and mop water only spread the dirt around the floor.  The best rule of thumb is to change the mop water when it appears dirty.   When cleaning a heavily soiled area you will have to change the water more frequently than a lightly soiled area.</p>
             </span></li>
          </ul><br>
	</td>
       </tr>
<tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="1" WIDTH="8" HEIGHT="11"></td>
     <td><br>
         <ul class="text">
     </ul>
    </td>
   </tr>														
</table>


          </td>
		  <td class="smalltext" valign="up" width="20%"></td>
        </tr>
      </table>
      