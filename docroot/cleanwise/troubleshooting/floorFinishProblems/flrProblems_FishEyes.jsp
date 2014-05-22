
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
  <tr><td></td><td class="text"><span class="subheaders">
    Fish Eyes/ Bird's Eyes
  </span><br><br></td></tr>
  <tr><!-- question -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
    <td class="text"><b>Why did the finish dry uneven with crater type circles in it?</b></td>
  </tr>
  <tr><!-- horizontal space -->
    <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
  </tr>				
  <tr><!-- answer -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
    <td class="text">
	<p>Finish that has leveled poorly while drying may appear to have small, raised, circular craters formed in the finish.  These craters are often referred to as fish eyes or bird's eyes.  Fish eyes are almost always caused by a procedural error in the daily maintenance so try to pinpoint what has caused the fish eyes and train everyone on how to avoid it.  Since this problem can escalate labor and chemical costs it is cost effective to correct the procedure that created the problem.</p>
	The following can cause fish eyes or bird's eyes:
	   <ul class="text">
	    <li class="troubleshootingbullet"><span class="text"><a href="#1"><span class="subheaders">Stripper residue</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#2"><span class="subheaders">Contaminated mops and/or buckets</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#3"><span class="subheaders">Applying the finish too thin</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#4"><span class="subheaders">Not completely removing a restorer or dust mop treatment before applying the finish</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#5"><span class="subheaders">Old finish on the floor or dried back finish still on the floor</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#6"><span class="subheaders">Recoating too soon</span></a></span></li>
	   </ul><br>
     </td>
   </tr>
   <tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
     <td><br>
<!-- How to with prevention text -->
	 <ul class="text">
          <li class="troubleshootingbullet"><span class="text"><a name="1"><span class="subheaders">Stripper residue</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish so you will need to strip the floor again.
             <p><b>How to Prevent:</b><br>Rinse! Make sure to rinse the floor. Many strippers on the market today say that they are a "no rinse" stripper.  This term means only that a flood rinse is not required.  However, a damp mop rinse still must be done.</p>
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
          <li class="troubleshootingbullet"><span class="text"><a name="2"><span class="subheaders">Contaminated mops and/or buckets</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the floor was stripped and the problem appeared right away then another strip must be done.  If the problem appeared on the last coat then scrub with a general purpose cleaner using a green or blue floor pad and then apply new finish.
	     <p>If the floor was only scrubbed and the problem appeared when reapplying the finish, then scrub with a general purpose cleaner using a green or blue floor pad and apply new finish.</p>
             <p><b>How to Prevent:</b><br>Always line the bucket with a plastic liner before pouring the finish into it.
Unused finish should be disposed of and not poured back into the container. Use a color-coded mop system to identify finish mops and stripper mops. Always use clean mops specifically designed for finish to apply the finish with.</p>
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
          <li class="troubleshootingbullet"><span class="text"><a name="3"><span class="subheaders">Applying the finish too thin</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the floor was stripped and the problem appeared right away then another strip must be done.  If the problem appeared on the last coat then scrub with a general purpose cleaner using a green or blue floor pad and then apply new finish.
	     <p>If the floor was only scrubbed and the problem appeared when reapplying the finish, then scrub with a general purpose cleaner using a green or blue floor pad and apply new finish.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="4"><span class="subheaders">Not completely removing a restorer or dust mop treatment before applying the finish</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  The floor will have to be scrubbed with a general purpose cleaner using a green or blue floor pad and then new finish can be applied.
	     <p><b>How to Prevent:</b><br>Make sure to use the right product when scrubbing the floor.  Neutral cleaners are not strong enough to remove dust mop treatments or restorers let alone any finish.  Use the Product Selector to help you choose the right general purpose cleaner.</p>
             <p>Once you have the correct cleaner for scrubbing, make sure to use the correct dilutions.  If diluted too weak, it may not remove all of the dust mop treatment or restorer.  If your staff tends to guess at how much an ounce is or you find it difficult to train them on how to use the correct dilutions then it may be time to consider a dilution control system.  Dilution control systems will save you money in time, labor, and chemical costs!</p>
	     <p>Make sure to use a green or blue pad when scrubbing the floor.  Red pads are not aggressive enough.  In addition, make sure to flip or change the pad often.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="5"><span class="subheaders">Old finish on the floor or dried back finish still on the floor</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish so the floor will have to be stripped.
	     <p><b>How to Prevent:</b><br>Make sure to follow the manufacturer's recommendations when stripping the floor.</p>
                  <OL>
                    <li class="troubleshootingbullet"><span class="text">Use the correct dilution</span></li>
                    <li class="troubleshootingbullet"><span class="text">Use the right temperature of water.  Some strippers are designed for use in cold water while others are designed for use in hot water.</span></li>
                    <li class="troubleshootingbullet"><span class="text">Use the recommended dwell time; the amount of time the stripping solution sits on the floor.</span></li>
                    <li class="troubleshootingbullet"><span class="text">Use the right stripping pad.  In most cases this will be a black stripping pad but there are some exceptions.  Know your flooring type before choosing a stripping pad.</span></li></ol>
             <p>Make sure to really flood the floor with the stripping solution to prevent dry back and make sure to keep the floor wet at all times.  If the floor appears to be drying during the dwell time then add more stripping solution to the floor.</p>
             <p>Only apply the stripping solution to an area that you can go back to and scrub and wet vac up within five minutes.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="6"><span class="subheaders">Recoating too soon</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the floor was stripped and the problem appeared right away then another strip must be done.  If the problem appeared on the last coat then scrub with a general purpose cleaner using a green or blue floor pad and then apply new finish.
             <p>If the floor was only scrubbed and the problem appeared when reapplying the finish, then scrub with a general purpose cleaner using a green or blue floor pad and apply new finish</p>
             <p><b>How to Prevent:</b><br>Be patient!  Do not rush the dry time of the finish.  You will save much more time waiting an extra 10 minutes for the finish to dry completely than you will re-stripping a floor.  Many manufacturers will give recommended dry times.  The key word is recommended - always take into account the climate.  A humid climate will require more dry time than a dry climate.  No matter what the recommended dry time is, make sure the finish is dry to the touch and then wait another 15 minutes no matter.</p>
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
      