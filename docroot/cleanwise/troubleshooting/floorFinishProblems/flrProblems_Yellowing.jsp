
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
   Yellowing
  </span><br><br></td></tr>
  <tr><!-- question -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
    <td class="text"><b>Why is my floor finish turning yellow?</b></td>
  </tr>
  <tr><!-- horizontal space -->
    <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
  </tr>				
  <tr><!-- answer -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
    <td class="text">
	<p>Yellowing is often caused by a build up of soil. Yellowing finish is almost always caused by a procedural error or tracking of asphalt seal from a newly paved parking lot or sidewalk.  Try to pinpoint what has caused the yellowing and train everyone on how to avoid it.  Since yellowing can escalate labor and chemical costs it is cost effective to correct the procedure that created the problem.</p>
	The following can cause yellowing:
	   <ul class="text">
	    <li class="troubleshootingbullet"><span class="text"><a href="#1"><span class="subheaders">Incorrectly diluting the daily cleaner</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#2"><span class="subheaders">Dirty pads, mops and water</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#3"><span class="subheaders">Infrequent cleaning and dry mopping of the floor</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#4"><span class="subheaders">Using hot water when daily cleaning the floor</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#5"><span class="subheaders">Tracking in of seal from a new asphalt parking lot</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#6"><span class="subheaders">Oil treated dust mops</span></a></span></li>
            <li class="troubleshootingbullet"><span class="text"><a href="#7"><span class="subheaders">Applying new finish without scrubbing and rinsing the floor</span></a></span></li>
            <li class="troubleshootingbullet"><span class="text"><a href="#8"><span class="subheaders">Buildup of finish around the edges</span></a></span></li>
            <li class="troubleshootingbullet"><span class="text"><a href="#9"><span class="subheaders">New cotton mops used to apply finish</span></a></span></li>
            <li class="troubleshootingbullet"><span class="text"><a href="#10"><span class="subheaders">Stripping (if linoleum floor or an old rubber floor)</span></a></span></li>
	   </ul><br>
     </td>
   </tr>
   <tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
     <td><br><ul class="text">
	  <li class="troubleshootingbullet"><span class="text"><a name="1"><span class="subheaders">Incorrectly diluting the daily cleaner</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the affected finish.  Scrub with a general purpose cleaner using a green or blue floor pad and then recoat with new floor finish.  In more severe cases you will need to completely strip the finish before applying new finish.
             <p><b>How to Prevent:</b><br>Make sure to use the correct dilutions.  Do not try to save money here by using a weak dilution of the product because in the long run you will end up spending more money in labor.  On the flip side, do not make a super strong dilution of the product because you may harm the floor finish and will most likely leave behind residue.  Residue will become tacky and attract dirt like a magnet.</p>

             <p>If your staff tends to guess at how much an ounce is or you find it difficult to train them on how to use the correct dilutions then it may be time to consider a dilution control system.  Dilution control systems will save you money in time, labor, and chemical costs!</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="2"><span class="subheaders">Dirty pads, mops and water</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the affected finish.  Scrub with a general purpose cleaner using a green or blue floor pad and then recoat with new floor finish.  In more severe cases you will need to completely strip the finish before applying new finish.
             <p><b>How to Prevent:</b><br>Use clean pads and flip or change them often.  Always use clean mops and change the mop water frequently.  Using dirty mops and mop water only spread the dirt around the floor.  The best rule of thumb is to change the mop water when it appears dirty.   When cleaning a heavily soiled area you will have to change the water more frequently than a lightly soiled area.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="3"><span class="subheaders">Infrequent cleaning and dry mopping of the floor</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the affected finish.  Scrub with a general purpose cleaner using a green or blue floor pad and then recoat with new floor finish.  In more severe cases you will need to completely strip the finish before applying new finish.
             <p><b>How to Prevent:</b><br>Dry mop and wet mop the floor as frequently as possible.  While it may not be realistic or possible to do it several times a day you should try to do it at least once a day or every other day.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="4"><span class="subheaders">Using hot water when daily cleaning the floor</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the affected finish.  Scrub with a general purpose cleaner using a green or blue floor pad and then recoat with new floor finish.  In more severe cases you will need to completely strip the finish before applying new finish.
             <p><b>How to Prevent:</b><br>Make sure to always damp mop the floor with cool water to prevent softening the finish.  Softened finish is more susceptible to dirt.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="5"><span class="subheaders">Tracking in of seal from a new asphalt parking lot</span></a>
             <br><b>How to Fix:</b><br>If the parking lot or sidewalk was recently repaved you may notice yellow traffic patterns.  The seal being tracked onto the finish causes the discoloration.  Quick action is your best defense because the seal can permanently damage the flooring!!
             <p>Immediately scrub or strip the floor to remove the seal.  If scrubbing with a general purpose cleaner does not remove all of the yellowing then immediately strip the floor.  If the discoloration is still visible then try scrubbing with a degreaser.  Don't forget to rinse!</p>

             <p>It is extremely important to reapply new finish immediately before any traffic is allowed on the floor.  You should apply more coats than normal to help protect the floor from the seal.</p>  

             <p>If the seal has already penetrated the flooring you can try stripping the floor again.   This may help to reduce the amount of discoloration but you may not be able to restore the floor to its original appearance.</p>
             <p><b>How to Prevent:</b><br>Install extra walk off mats that are a minimum of at least 20'.  Make sure to clean the mats very often during the first few weeks after the repaving.
Increase the frequency of autoscrubbing the floor during the fist few weeks and scrub and recoat the floor as often as possible during the first few week after the repaving.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="6"><span class="subheaders">Oil treated dust mops</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the affected finish.  Scrub with a general purpose cleaner using a green or blue floor pad and then recoat with new floor finish.  In more severe cases you will need to completely strip the finish before applying new finish.
             <p><b>How to Prevent:</b><br>Try to stay away from oil treated dust mops.  Instead, opt for a paraffin dust mop treatment.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="7"><span class="subheaders">Applying new finish without scrubbing and rinsing the floor</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the affected finish.  Scrub with a general purpose cleaner using a green or blue floor pad and then recoat with new floor finish.  In more severe cases you will need to completely strip the finish before applying new finish.
             <p><b>How to Prevent:</b><br>Dirt can become trapped in by floor finish so make sure to always scrub and rinse the floor before applying any finish.  Remember that the top coat will only look as good as the coats underneath it.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="8"><span class="subheaders">Buildup of finish around the edges</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the affected finish.  Scrub with a general purpose cleaner using a green or blue floor pad and then recoat with new floor finish.  In more severe cases you will need to completely strip the finish before applying new finish.
             <p><b>How to Prevent:</b><br>To prevent buildup of finish around edges try alternating coats around the edges.  The first coat of finish should be applied all the way to the edge of the wall, the second coat should be applied about 6-8 inches away from the way, the third coat should be applied all the way to the wall, and so on.  Finish in traffic patterns will tend to be worn away much faster than finish near the edges so, old finish builds up around the edges and begins to yellow.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="9"><span class="subheaders">New cotton mops used to apply finish</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the affected finish.  If the floor was stripped and the problem appeared right away then another strip must be done.  If the problem appeared on the last coat then scrub with a general purpose cleaner using a green or blue floor pad and then apply new finish.
             <p>If the floor was only scrubbed and the problem appeared when reapplying the finish, then scrub with a general purpose cleaner using a green or blue floor pad and apply new finish.</p>
             <p><b>How to Prevent:</b><br>Always use rayon finish mops to apply finish with.  If you absolutely must use a cotton mop then launder it first before using it to apply finish and make sure to soak it and wring it out thoroughly before dipping the mop in the finish.  Sizing from new cotton mops can be released into the finish and cause it to yellow.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="10"><span class="subheaders">Stripping (if linoleum floor or an old rubber floor)</span></a>
             <br><b>How to Fix:</b><br>Determine if the yellowing is of the finish or of the floor itself.  If the finish is yellow, then you can use a general purpose cleaner and a green or blue pad to remove the finish.  You may need to scrub the floor several times to remove the finish but stay away from using a stripper on the floor. 

             <p>If the yellowing is on the flooring and not the finish then you will not be able to remove the yellowing.</p>
             <p><b>How to Prevent:</b><br>Strippers will damage linoleum flooring.  It may not happen on the first time the floor is stripped but it will inevitably happen.  The floor may yellow or it may start to crack.  Try to stay away from stripping a linoleum floor, instead opt to scrub and recoat the finish using a general purpose cleaner instead of a stripper.</p>

             <p>Strippers can also yellow old rubber flooring.  The newer rubber flooring is very chemical resistant and can easily withstand strippers.  If you have an old rubber floor than opt to scrub and recoat the finish using a general purpose cleaner instead of a stripper.</p>
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
      