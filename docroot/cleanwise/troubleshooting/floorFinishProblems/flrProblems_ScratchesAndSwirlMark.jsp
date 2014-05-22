
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
   Scratches and Swirl Marks
  </span><br><br></td></tr>
  <tr><!-- question -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
    <td class="text"><b>Why are there swirl marks in my finish?</b></td>
  </tr>
  <tr><!-- horizontal space -->
    <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
  </tr>				
  <tr><!-- answer -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
    <td class="text">
	<p>Scratches and swirl marks in finish can be described as circular patterns appearing on the surface of the finish.  To help reduce the likelihood of scratches and swirl marks, install and maintain proper walk off mats.  Scratches and swirl marks are almost always caused by a procedural error in the daily maintenance so try to pinpoint what has caused the problem and train everyone on how to avoid it.  Since this problem can escalate labor and chemical costs it is cost effective to correct the procedure that created the problem.</p>
	The following can cause scratches and swirl marks:
	   <ul class="text">
	    <li class="troubleshootingbullet"><span class="text"><a href="#1"><span class="subheaders">Too aggressive a pad</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#2"><span class="subheaders">Dust or dirt on the floor not removed before burnishing</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#3"><span class="subheaders">Use of dirty pads</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#4"><span class="subheaders">Not using a restorer or spray buff</span></a></span></li>
	   </ul><br>
     </td>
   </tr>
   <tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
     <td><br><ul class="text">
	  <li class="troubleshootingbullet"><span class="text"><a name="1"><span class="subheaders">Too aggressive a pad</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  In less severe cases you can try using a slightly more aggressive pad to burnish out the marks.  If you try this method the finish will not be glossy afterwards.  You will have to go back and burnish again with the appropriate pad for the finish and machine you are using.  Use our Product Selector to help you choose the right pad for your finish and machine type.  

             <p>Or Scrub with a general purpose cleaner using a green or blue floor pad and apply new finish.   Make sure to use the correct pad for the finish and machine you are using next time you burnish.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="2"><span class="subheaders">Dust or dirt on the floor not removed before burnishing</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  In less severe cases you can try using a slightly more aggressive pad to burnish out the marks.  If you try this method the finish will not be glossy afterwards.  You will have to go back and burnish again with the appropriate pad for the finish and machine you are using.  Use our Product Selector to help you choose the right pad for your finish and machine type.  

             <p>Or Scrub with a general purpose cleaner using a green or blue floor pad and apply new finish.   Make sure to use the correct pad for the finish and machine you are using next time you burnish.</p>
             <p><b>How to Prevent:</b><br>Make sure to sweep AND damp mop with a neutral cleaner before burnishing the floor.  Dirt on the floor will grind into the finish if not removed before burnishing.</p>

             <p>Make sure to use enough walk of matting and don't forget to clean the matting.  Water, salt, sand, and soil can be detrimental to floor finish and your maintenance system.  Walk off mats can reduce the amount of soil carried onto the floor by as much as 80%!  To be effective, a minimum of 20 feet of walk off matting should be installed.  However, 30 feet of walk off matting will offer the most protection.  Try to allow for 15' of outside matting, 5' of foyer matting, and 10' of inside matting.  The outside and foyer matting should be a vinyl type mat that scrapes the dirt off the feet while the inside matting can be vinyl or carpet type matting.  If this much matting is not realistic then keep in mind that the first thing a person walks on should be a vinyl type mat to scrape away dirt and then a carpet type mat to absorb any water.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="3"><span class="subheaders">Use of dirty pads</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  In less severe cases you can try using a slightly more aggressive pad to burnish out the marks.  If you try this method the finish will not be glossy afterwards.  You will have to go back and burnish again with the appropriate pad for the finish and machine you are using.  Use our Product Selector to help you choose the right pad for your finish and machine type.  

             <p>Or Scrub with a general purpose cleaner using a green or blue floor pad and apply new finish.   Make sure to use the correct pad for the finish and machine you are using next time you burnish.</p>
             <p><b>How to Prevent:</b><br>Use clean pads and flip and change them often.  Under high speed conditions, dirt particles can scratch the surface of the finish, allowing finish to build up on the dirt.  These particles load up in the pad and will cause severe swirl marks.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="4"><span class="subheaders">Not using a restorer or spray buff</span></a>
             <br><b>How to Fix:</b><br>Over time finish will get scratches in it.  If a restorer or spray buff is not used you may find that the scratches are not being removed.  While you can get good glossy results from dry burnishing a floor, meaning burnishing without using a chemical restorer, you will not get any results from dry spray buffing.  You are burnishing your floor if you are using a floor machine over 1500 rpm.  You are spray buffing if you are using a machine under 1500 rpm.
             <p><b>How to Prevent:</b><br>When using a floor machine under 1500 rpm always use a spray buff product on the floor during the buffing process.</p>

             <p>While it is not necessary, it will help to fill in the scratches if you use a floor finish restorer when burnishing your floor with a machine over 1500 rpm.</p>
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
      