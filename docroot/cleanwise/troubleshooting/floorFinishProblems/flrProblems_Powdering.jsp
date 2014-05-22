
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
   Powdering
  </span><br><br></td></tr>
  <tr><!-- question -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
    <td class="text"><b>Why is my finish powdering, flaking, and walking off?</b></td>
  </tr>
  <tr><!-- horizontal space -->
    <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
  </tr>				
  <tr><!-- answer -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
    <td class="text">
	<p>Powdering can be described as a dust or powdery film on the surface of the finish.  The dusting often occurs while burnishing the floor.  In severe cases you may notice dust on the lower shelves of a store or other furniture close to the floor.  To help reduce the likelihood of powdering, install and maintain proper walk off mats.  </p>
	The following can cause powdering:
	   <ul class="text">
	    <li class="troubleshootingbullet"><span class="text"><a href="#1"><span class="subheaders">Poor adhesion of the finish to the floor or previous finish</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#2"><span class="subheaders">Too aggressive a pad or machine for the finish</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#3"><span class="subheaders">Dust or dirt on the floor not removed before burnishing</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#4"><span class="subheaders">Use of dirty pads</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#5"><span class="subheaders">Thin coats</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#6"><span class="subheaders">Not rinsing after stripping or scrubbing</span></a></span></li>
            <li class="troubleshootingbullet"><span class="text"><a href="#7"><span class="subheaders">Factory finish on new flooring not removed</span></a></span></li>
            <li class="troubleshootingbullet"><span class="text"><a href="#8"><span class="subheaders">Low humidity (less than 20%) or low floor temperature (less than 50)</span></a></span></li>
	   </ul><br>
     </td>
   </tr>
   <tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
     <td><br><ul class="text">
	  <li class="troubleshootingbullet"><span class="text"><a name="1"><span class="subheaders">Poor adhesion of the finish to the floor or previous finish</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the affected finish.  The floor will have to be stripped.  Before applying new finish, test for good adhesion of the new finish to the floor.
             <p>To test for good adhesion apply four coats of the finish to the floor in a very small area no more than several inches big.  Ideally the finish should cure for 24 hours but this is not absolutely necessary if time is critical.  Attach a strip of packing tape to the finish and then score the tape with a safety blade.  Score the tape in a "tic tac toe" type pattern.  Rip the tape up.  Examine the tape to see if any finish is on the sticky side of the tape.  If finish is on the tape then the finish failed the adhesion test and you will need to look at an alternative finish or maintenance plan.</p>
             <p><b>How to Prevent:</b><br>Before applying new finish to a floor make sure that the finish will adhere to the floor.  Typically finish may not adhere well to polished marble, plastic, static dissipative, and some painted floors to name a few.  In addition there are some types of finishes and seals out there that don't adhere to other finishes. If there is any doubt or question simply do an adhesion test on a small area before applying the finish to the entire floor.</p>

<p>To test for good adhesion apply four coats of the finish to the floor in a very small area no more than several inches big.  Ideally the finish should cure for 24 hours but this is not absolutely necessary if time is critical.  Attach a strip of packing tape to the finish and then score the tape with a safety blade.  Score the tape in a "tic tac toe" type pattern.  Rip the tape up.  Examine the tape to see if any finish is on the sticky side of the tape.  If finish is on the tape then the finish failed the adhesion test and you will need to look at an alternative finish or maintenance plan.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="2"><span class="subheaders">Too aggressive a pad or machine for the finish</span></a>
             <br><b>How to Fix:</b><br>Use a less aggressive pad.  For the future you may want to reconsider the finish or the machine you are using.  For example, if you have a propane burnisher and are using a finish designed for low speed you will always get powdering.  Use the Product Selector to help you choose the best machine and finish for your situation. 
             <p><b>How to Prevent:</b><br>Make sure that the finish, the pad, the machine, and the maintenance procedures match.  Some finishes are designed for high speed maintenance while others are designed for low speed maintenance.  Try the Product Selector to help you choose the right pad for the job.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="3"><span class="subheaders">Dust or dirt on the floor not removed before burnishing</span></a>
             <br><b>How to Fix:</b><br>Sweep and damp mop the floor then burnish.
             <p><b>How to Prevent:</b><br>Make sure to sweep AND damp mop with a neutral cleaner before burnishing the floor.  Dirt on the floor can cause additional friction between the pad and the finish causing the finish to powder.</p>

<p>Make sure to have enough matting and don't forget to clean the matting.  Water, salt, sand, and soil can be detrimental to floor finish and your maintenance system.  Walk off mats can reduce the amount of soil carried onto the floor by as much as 80%!  To be effective, a minimum of 20 feet of walk off matting should be installed.  However, 30 feet of walk off matting will offer the most protection.  Try to allow for 15' of outside matting, 5' of foyer matting, and 10' of inside matting.  The outside and foyer matting should be a vinyl type mat that scrapes the dirt off the feet while the inside matting can be vinyl or carpet type matting.  If this much matting is not realistic then keep in mind that the first thing a person walks on should be a vinyl type mat to scrape away dirt and then a carpet type mat to absorb any water.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="4"><span class="subheaders">Use of dirty pads</span></a>
             <br><b>How to Fix:</b><br>Change or clean the pads.  Flip the pad often.  If the pad is excessively dirty it may have also caused scratches or swirl marks in the finish.  If this has happened you may be able to burnish the scratches out, if not then you may have to scrub with a general purpose cleaner and a green or blue floor pad and then recoat with new finish.
             <p><b>How to Prevent:</b><br>Use clean pads and flip and change them often.  Dirt loaded on a pad can cause additional friction causing the finish to powder.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="5"><span class="subheaders">Thin coats</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the affected finish.  If the problem occurred right after the initial application then you will need to scrub with a general purpose cleaner and a green or blue floor pad and apply new finish.  In more severe cases, it may be necessary to strip the floor.
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
	  <li class="troubleshootingbullet"><span class="text"><a name="6"><span class="subheaders">Not rinsing after stripping or scrubbing</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the affected finish.  If the problem occurred right after the initial application then you will need to scrub with a general purpose cleaner and a green or blue floor pad and apply new finish.  In more severe cases, it may be necessary to strip the floor. 
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
	  <li class="troubleshootingbullet"><span class="text"><a name="7"><span class="subheaders">Factory finish on new flooring not removed</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the affected finish.  You will have to strip the floor.  Before stripping be aware of the flooring manufacturer's recommendations for stripping procedures on a new floor.   Some adhesives will fail on new floors if stripped too soon!
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
	  <li class="troubleshootingbullet"><span class="text"><a name="8"><span class="subheaders">Low humidity (less than 20%) or low floor temperature (less than 50)</span></a>
             <br><b>How to Fix:</b><br>Burnish the floor on days when the humidity is above 20%.  If you have climate control then adjust the humidity to 50%.  Raise the floor temperature to above 50 degrees F by warming up the room.  Applying a restorer may help to reduce powdering.
             <p><b>How to Prevent:</b><br>Burnish the floor on days when the humidity is above 20%.  If you have climate control then adjust the humidity to 50%.  Raise the floor temperature to above 50 degrees F by warming up the room.</p>
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
      