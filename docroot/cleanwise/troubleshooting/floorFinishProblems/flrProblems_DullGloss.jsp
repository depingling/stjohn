
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
   Dull Finish
  </span><br><br></td></tr>
  <tr><!-- question -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
    <td class="text"><b>How do I get the gloss back to the floor finish?</b></td>
  </tr>
  <tr><!-- horizontal space -->
    <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
  </tr>				
  <tr><!-- answer -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
    <td class="text">
	<p>Occasionally, you may find that your floors are not very glossy after your general or daily maintenance.  If it has not been very long since you did a scrub and recoat on the floor, then the dull floors are most likely caused by a procedural error in the daily maintenance.   Try to pinpoint what has caused the dull gloss and train everyone on how to avoid it.  Since this problem can escalate labor and chemical costs it is cost effective to correct the procedure that created the problem.  First look at where you are in your maintenance cycle.  If buffing or burnishing your floor is not bringing back the gloss then it may be time to for a scrub and recoat.</p>

<p>If you feel that the floor is losing its gloss too soon and you have to scrub and recoat often, then you may need to look at your general maintenance procedures to prevent the problem from occurring again.  </p>
	The following can cause your floors to have a dull gloss:
	   <ul class="text">
	    <li class="troubleshootingbullet"><span class="text"><a href="#1"><span class="subheaders">Wrong Pads</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#2"><span class="subheaders">Dirty pads, mops and water</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#3"><span class="subheaders">Wrong dilutions</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#4"><span class="subheaders">Bleach or ammonia</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#5"><span class="subheaders">Wrong cleaner</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#6"><span class="subheaders">Infrequent buffing or burnishing</span></a></span></li>
	   </ul><br>
     </td>
   </tr>
   <tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
     <td><br><ul class="text">
	  <li class="troubleshootingbullet"><span class="text"><a name="1"><span class="subheaders">Wrong Pads</span></a>
             <br><b>How to Fix:</b><br>Try buffing or burnishing with the correct pad.  Use the Product Selector to help you choose the best pad for your situation.  If the gloss still does not come back then you will have to scrub with a general purpose cleaner using a green or blue floor pad and then recoat with new finish.
             <p><b>How to Prevent:</b><br>Make sure that the finish, the pad, the machine, and the maintenance procedures match.  A pad that is too aggressive or not aggressive enough might prevent the finish from becoming glossy.  In addition, some finishes are designed for high speed maintenance while others are designed for low speed maintenance.  Try the Product Selector to help you choose the right pad for the job.</p>
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
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  A floor that is routinely not glossy due to using dirty pads, mops, and/or water during daily maintenance will most likely have a lot of soil built up on the floor.  To get the gloss back you will need to scrub with a general purpose cleaner using a green or blue floor pad and the recoat with new finish.  In more severe cases, it may be necessary to strip the floor.
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
	  <li class="troubleshootingbullet"><span class="text"><a name="3"><span class="subheaders">Wrong dilutions</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  Scrub with a general purpose cleaner using a green or blue floor pad and then recoat with new floor finish.
             <p><b>How to Prevent:</b><br>Make sure to use the correct dilutions.  Do not try to save money here by using a weak dilution of the product because in the long run you will end up spending more money in labor.  On the flip side, do not make a super strong dilution of the product because you may harm the floor finish and will most likely leave behind residue.  Residue will become tacky and attract dirt like a magnet.</p>

<p>If your staff tends to guess at how much an ounce is or you find it difficult to train them on how to use the correct dilutions then it may be time to consider a dilution control system.  Dilution control systems will save you money in time, labor, and chemical costs! </p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="4"><span class="subheaders">Bleach or ammonia</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  Scrub with a general purpose cleaner using a green or blue floor pad and then recoat with new floor finish.
             <p><b>How to Prevent:</b><br>Never add anything, especially bleach or ammonia, to cleaners.  Not only will you get an unexpected result, you will be out of OSHA compliance for not having the correct MSDS, and will most likely cause a lot of damage to the floor, the finish, and whatever else the mixture comes in contact with.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="5"><span class="subheaders">Wrong cleaner</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  Scrub with a general purpose cleaner using a green or blue floor pad and then recoat with new floor finish.
             <p><b>How to Prevent:</b><br>Make sure that you are using the right cleaner for the job.  Neutral cleaners are best to use for daily cleaning.  The stronger general purpose cleaners will dull the floor finish and are too alkaline to use even at a very light dilution.  Use the Product Selector to help you choose the right daily cleaner.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="6"><span class="subheaders">Infrequent buffing or burnishing</span></a>
             <br><b>How to Fix:</b><br>Try buffing or burnishing first.  If the gloss still does not come back then you will have to scrub with a general purpose cleaner using a green or blue floor pad and then recoat with new finish.  
             <p><b>How to Prevent:</b><br>Make sure to buff or burnish the floor as often as possible.  Some finishes require more frequent burnishings then other finishes.  If it is not realistic for you to burnish your floor three times a week then it may be time to consider a different finish.  Use the Product Selector to help you choose the best finish for your situation. </p>
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
      