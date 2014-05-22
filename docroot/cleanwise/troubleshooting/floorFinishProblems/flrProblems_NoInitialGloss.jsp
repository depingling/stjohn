
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
   No Initial Gloss
  </span><br><br></td></tr>
  <tr><!-- question -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
    <td class="text"><b>I just applied new finish.  Why didn't it dry with a gloss?</b></td>
  </tr>
  <tr><!-- horizontal space -->
    <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
  </tr>				
  <tr><!-- answer -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
    <td class="text">
	<p>Occasionally you may find that the finish has no gloss after the initial application.  No initial gloss is almost always caused by a procedural error in the daily maintenance so try to pinpoint what has caused the problem and train everyone on how to avoid it.  Since this problem can escalate labor and chemical costs it is cost effective to correct the procedure that created the problem.</p>
	The following can cause no initial gloss of new finish:
	   <ul class="text">
	    <li class="troubleshootingbullet"><span class="text"><a href="#1"><span class="subheaders">Contamination</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#2"><span class="subheaders">Thin coats</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#3"><span class="subheaders">Old finish left on the floor</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#4"><span class="subheaders">Recoating too soon</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#5"><span class="subheaders">Not rinsing after stripping or scrubbing</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#6"><span class="subheaders">Low floor temperature</span></a></span></li>
            <li class="troubleshootingbullet"><span class="text"><a href="#7"><span class="subheaders">Not applying enough finish</span></a></span></li>
	   </ul><br>
     </td>
   </tr>
   <tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
     <td><br><ul class="text">
	  <li class="troubleshootingbullet"><span class="text"><a name="1"><span class="subheaders">Contamination</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the problem occurred right after the initial application then you will need to scrub with a general purpose cleaner and a green or blue floor pad and apply new finish.  In more severe cases, it may be necessary to strip the floor.
             <p><b>How to Prevent:</b><br>Always line the bucket with a plastic liner before pouring the finish into it. Unused finish should be disposed of and not poured back into the container. Use a color-coded mop system to identify finish mops and stripper mops. Always use clean mops specifically designed for finish to apply the finish with.</p>
             <ul class="text">
               <li class="troubleshootingbullet"><span class="text">Never use the mop that was used for applying the stripper to apply finish.</span></li>
               <li class="troubleshootingbullet"><span class="text">Never use the mop that was used for damp rinsing the floor after stripping</span></li>
               <li class="troubleshootingbullet"><span class="text">Never use the mop that was used to apply a restorer.</span></li>
            </ul>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="2"><span class="subheaders">Thin coats</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the problem occurred right after the initial application then you will need to scrub with a general purpose cleaner and a green or blue floor pad and apply new finish.  In more severe cases, it may be necessary to strip the floor. 
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
	  <li class="troubleshootingbullet"><span class="text"><a name="3"><span class="subheaders">Old finish left on the floor</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish so you will have to strip the floor and reapply finish.
             <p><b>How to Prevent:</b><br>Make sure to follow the manufacturer's recommendations when stripping the floor.</p>
             <ol>
               <li class="troubleshootingbullet"><span class="text">Use the correct dilution</span></li>
               <li class="troubleshootingbullet"><span class="text">Use the right temperature of water.  Some strippers are designed for use in cold water while others are designed for use in hot water.</span></li>
               <li class="troubleshootingbullet"><span class="text">Use the recommended dwell time; the amount of time the stripping solution sits on the floor.</span></li>
               <li class="troubleshootingbullet"><span class="text">Use the right stripping pad.  In most cases this will be a black stripping pad but there are some exceptions.  Know your flooring type before choosing a stripping pad.</span></li>
             </ol>
             Make sure to really flood the floor with the stripping solution to prevent dry back and make sure to keep the floor wet at all times.  If the floor appears to be drying during the dwell time, add more stripping solution to the floor.

             <p>Only apply the stripping solution to an area that you can go back to and scrub and wet vac within five minutes.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="4"><span class="subheaders">Recoating too soon</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the problem occurred right after the initial application then you will need to scrub with a general purpose cleaner and a green or blue floor pad and apply new finish.  In more severe cases, it may be necessary to strip the floor.
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
	  <li class="troubleshootingbullet"><span class="text"><a name="5"><span class="subheaders">Not rinsing after stripping or scrubbing</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the problem occurred right after the initial application then you will need to scrub with a general purpose cleaner and a green or blue floor pad and apply new finish.  In more severe cases, it may be necessary to strip the floor.
             <p><b>How to Prevent:</b><br>Rinse!  Make sure to rinse the floor.  Many strippers on the market today are labeled as "no rinse" strippers.  This term means only that a flood rinse is not required.  However, a damp mop rinse still must be done.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="6"><span class="subheaders">Low floor temperature</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If the problem occurred right after the initial application then you will need to scrub with a general purpose cleaner and a green or blue floor pad and apply new finish.  In more severe cases, it may be necessary to strip the floor. 
             <p><b>How to Prevent:</b><br>Try to raise the floor temperature by warming up the room.  The room temperature and the floor temperature should be above 50 degrees.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="7"><span class="subheaders">Not applying enough finish</span></a>
             <br><b>How to Fix:</b><br>Apply more finish.  If there has been any foot traffic on the floor you may need to do a scrub with a general purpose cleaner and a green or blue floor pad before applying any more finish.
             <p><b>How to Prevent:</b><br>Make sure to apply enough finish.  With most flooring types at least 4 coats will be needed.  However, there are some flooring types like quarry tile or brick that will require more finish.</p>
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
      