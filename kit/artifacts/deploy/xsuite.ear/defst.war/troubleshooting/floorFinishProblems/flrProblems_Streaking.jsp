
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
   Streaking
  </span><br><br></td></tr>
  <tr><!-- question -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
    <td class="text"><b>Why is my floor finish streaking?</b></td>
  </tr>
  <tr><!-- horizontal space -->
    <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
  </tr>				
  <tr><!-- answer -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
    <td class="text">
	<p>Streaking is described as mop lines in the finish that appear just below the surface of the finish.  Other terms that used to describe streaking are "lawn mower lines" and "alligator".  Streaking is almost always caused by a procedural error in the daily maintenance so try to pinpoint what has caused the streaking and train everyone on how to avoid it.  Since this problem can escalate labor and chemical costs it is cost effective to correct the procedure that created the problem.</p>
	The following can cause streaking:
	   <ul class="text">
	    <li class="troubleshootingbullet"><span class="text"><a href="#1"><span class="subheaders">Stripper residue</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#2"><span class="subheaders">Recoating too soon</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#3"><span class="subheaders">Applying the finish too heavy</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#4"><span class="subheaders">High Humidity</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#5"><span class="subheaders">Contamination</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#6"><span class="subheaders">Factory finish not removed</span></a></span></li>
            <li class="troubleshootingbullet"><span class="text"><a href="#7"><span class="subheaders">Use of cotton mops</span></a></span></li>
	   </ul><br>
     </td>
   </tr>
   <tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
     <td><br><ul class="text">
	  <li class="troubleshootingbullet"><span class="text"><a name="1"><span class="subheaders">Stripper residue</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish so you will need to strip the floor again.
             <p><b>How to Prevent:</b><br>Rinse!  Make sure to rinse the floor.  Many strippers on the market today say that they are a "no rinse" stripper.  This term means only that a flood rinse is not required.  However, a damp mop rinse still must be done.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="2"><span class="subheaders">Recoating too soon</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the floor was stripped and the problem appeared right away then another strip must be done.  If the problem appeared on the last coat then scrub with a general purpose cleaner using a green or blue floor pad and then apply new finish.

             <P>If the floor was only scrubbed and the problem appeared when reapplying the finish, then scrub with a general purpose cleaner using a green or blue floor pad and apply new finish.</P>
             <p><b>How to Prevent:</b><br>Be patient!  Do not rush the dry time of the finish.  You will save much more time waiting an extra 10 minutes for the finish to dry completely than you will re-stripping a floor.  Many manufacturers will give recommended dry times.  The key word is recommended - always take into account the climate.  A humid climate will require more dry time than a dry climate.  No matter what the recommended dry time is, make sure the finish is dry to the touch and then wait another 15 minutes no matter.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="3"><span class="subheaders">Applying the finish too heavy</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the floor was stripped and the problem appeared right away then another strip must be done.  If the problem appeared on the last coat then scrub with a general purpose cleaner using a green or blue floor pad and then apply new finish.

             <P>If the floor was only scrubbed and the problem appeared when reapplying the finish, then scrub with a general purpose cleaner using a green or blue floor pad and apply new finish.</P>
             <p><b>How to Prevent:</b><br>Make sure you are applying an even medium coat.  The finish should not be applied too thickly or too thinly.  To ensure the right kind of coat, try wringing the mop out halfway.  Or, try giving the mop a half twist and tamping (pressing down) on the mop until only an occasional drip of finish comes off the mop.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="4"><span class="subheaders">High Humidity</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the floor was stripped and the problem appeared right away then another strip must be done.  If the problem appeared on the last coat then scrub with a general purpose cleaner using a green or blue floor pad and then apply new finish.

             <P>If the floor was only scrubbed and the problem appeared when reapplying the finish, then scrub with a general purpose cleaner using a green or blue floor pad and apply new finish.</P>
             <p><b>How to Prevent:</b><br>Apply finish on days when the humidity is below 70%.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="5"><span class="subheaders">Contamination</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the floor was stripped and the problem appeared right away then another strip must be done.  If the problem appeared on the last coat then scrub with a general purpose cleaner using a green or blue floor pad and then apply new finish.

              <P>If the floor was only scrubbed and the problem appeared when reapplying the finish, then scrub with a general purpose cleaner using a green or blue floor pad and apply new finish.</P>
             <p><b>How to Prevent:</b><br>Always line the bucket with a plastic liner before pouring the finish into it.
Unused finish should be disposed of and not poured back into the container.
Use a color-coded mop system to identify finish mops and stripper mops.
Always use clean mops specifically designed for finish to apply the finish with.</p>
             <ul class="text">
               <li class="troubleshootingbullet"><span class="text">Never use the mop that was used for applying the stripper to apply finish.</span></li>
               <li class="troubleshootingbullet"><span class="text">Never use the mop that was used for damp rinsing the floor after stripping.</span></li>
               <li class="troubleshootingbullet"><span class="text">Never use the mop that was used to apply a restorer.</span></li></ul>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="6"><span class="subheaders">Factory finish not removed</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  You will have to strip the floor.  Before stripping be aware of the flooring manufacturer's recommendations for stripping procedures on a new floor.   Some adhesives will fail on new floors if stripped too soon!
             <p><b>How to Prevent:</b><br>All flooring manufacturers have different recommendations on how to care for a new floor and these recommendations should always be followed.  Keep in mind that most new flooring comes with a factory seal already on.  This seal may or may not need to be removed before applying finish.  If the flooring manufacturer recommends not to remove the finish then test for adhesion of the new finish to the factory seal before applying the new finish.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="7"><span class="subheaders">Use of cotton mops</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the floor was stripped and the problem appeared right away then another strip must be done.  If the problem appeared on the last coat then scrub with a general purpose cleaner using a green or blue floor pad and then apply new finish.

             <p>If the floor was only scrubbed and the problem appeared when reapplying the finish, then scrub with a general purpose cleaner using a green or blue floor pad and apply new finish.</p>
             <p><b>How to Prevent:</b><br>Rayon finish mops are recommended for applying finish.  If a cotton mop must be used make sure it has been soaked and laundered before use.</p>
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
      