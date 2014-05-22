
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
   Vinyl Asbestos Tile
  </span><br><br></TD></tr>
<tr><!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#1">What is vinyl asbestos tile?</a></b></td></tr>
<tr><!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#2">How do I maintain a vinyl asbestos tile floor?</a></b></td></tr>
<tr><!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#3">Do I need to seal a vinyl asbestos tile floor?</a></b></td></tr>
<tr><!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#4">Can a vinyl asbestos tile floor be stripped?</a></b></td></tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
<tr><!-- horizontal rule --> 
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- question --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
  <td class="text"><b><a name="1"></a>What is vinyl asbestos tile?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Vinyl asbestos tile is very similar to vinyl composite tile with the exception to the use of asbestos fibers as the filler.  Vinyl asbestos tiles are usually 12"x12" in size.  Extreme caution must be taken when maintaining a vinyl asbestos tile floor.
    <UL>
      <li class="troubleshootingbullet"><span class="text">Always refer to the OSHA and EPA guidelines for maintaining asbestos containing floors before performing any type of maintenance.  Dry stripping, aggressive pads, and aggressive floor machines can all potentially damage the floor and release hazardous asbestos fibers into the air.  Always use both a seal and a floor finish on vinyl asbestos tile</span></li>
      <li class="troubleshootingbullet"><span class="text">There is no way to tell the difference between a vinyl asbestos tile floor and a vinyl composite tile floor by just looking at it.  The asbestos floors were installed up to around 1970-1980.  So, if in doubt, assume the floor is asbestos.</span></li>
    </UL>
  </td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
<!--          End Answer   --> 
<tr><!-- horizontal rule --> 
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- question --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
  <td class="text"><b><a name="2"></a>How do I maintain a vinyl asbestos tile floor?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">
    <UL>
     <li class="troubleshootingbullet"><span class="text">Matting:  Always install and maintain proper walk off mats.  Walk off mats can reduce the amount of soil carried onto the floor by as much as 80%!  To be effective, a minimum of 20 feet of walk off matting should be installed.  However, 30 feet of walk off matting will offer the most protection.  Try to allow for 15' of outside matting, 5' of foyer matting, and 10' of inside matting.  The outside and foyer matting should be a vinyl type mat that scrapes the dirt off the feet while the inside matting can be vinyl or carpet type matting.  If this much matting is not realistic then keep in mind that the first thing a person walks on should be a vinyl type mat to scrape away dirt and then a carpet type mat to absorb any water.</span></li>
     <li class="troubleshootingbullet"><span class="text">Daily Cleaner:  Use any neutral cleaner for the daily cleaner.</span></li>
     <li class="troubleshootingbullet"><span class="text">Finish:  Any finish can be used.  Always use both a seal and a finish and apply multiple coats of finish.  Use at least two coats of the seal and six coats of the finish.   The more coats that are on the floor the more you will be able to scrub and recoat instead of strip the finish.</span></li>
     <li class="troubleshootingbullet"><span class="text">Stripper:   Use the least aggressive stripper possible.  Because of the asbestos you should always follow the OSHA recommendations for maintaining this type of floor.  When stripping, always make sure to keep the floor very wet.  The best course of action for and asbestos tile is to use the least aggressive method possible for maintaining the floor.  Here are some guidelines when stripping the floor.
        <UL>
          <li class="troubleshootingbullet"><span class="text">Always refer to the OSHA guidelines for stripping asbestos before stripping asphalt tile because asphalt tiles contain asbestos.</span></li>
          <li class="troubleshootingbullet"><span class="text">Avoid stripping as much as possible.  Instead, make sure to apply plenty of coats of finish and scrub and recoat the floor instead.</span></li>
          <li class="troubleshootingbullet"><span class="text">Use the least aggressive stripper possible and the least aggressive pad possible.  Never use a high productivity pad which are the really aggressive black stripping pads.</span></li>
          <li class="troubleshootingbullet"><span class="text">Never use a solvenated stripper.</span></li>
          <li class="troubleshootingbullet"><span class="text">Always keep the floor very wet when stripping</span></li>
        </UL>
     </span></li>
     <li class="troubleshootingbullet"><span class="text">Pads:  Red pad for spray buffing and green or blue pad for stripping or scrubbing.  If absolutely necessary you may use a black stripping pad but this is not typically recommended.  Never user a high productivity pad on vinyl asbestos flooring.</span></li>
     <li class="troubleshootingbullet"><span class="text">Floor machines:   Use low speed floor machines as high speed machines could damage or loosen tiles.</span></li>
    </UL>
  </td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
<!--          End Answer   --> 
<tr><!-- horizontal rule --> 
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- question --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
  <td class="text"><b><a name="3"></a>Do I need to seal a vinyl asbestos tile floor?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Yes.  Always use both a seal and a floor finish on VAT.   The EPA recommends that multiple coats of seal and finish both be used on floors containing asbestos to minimize the release of asbestos fibers into the air.</td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
<!--          End Answer   --> 	
<tr><!-- horizontal rule --> 
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- question --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
  <td class="text"><b><a name="4"></a>Can a vinyl asbestos tile floor be stripped?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Only when absolutely necessary.   Always refer to the OSHA and EPA guidelines for maintaining asbestos containing floors before performing any type of maintenance.  Dry stripping, aggressive pads, and aggressive floor machines can all potentially damage the floor and release hazardous asbestos fibers into the air.

<p>To put off stripping for as long as possible try to follow these guidelines.  Keeping up with these maintenance guidelines will help to prolong the life of the finish.  If done diligently you may never have to strip your vinyl asbestos floor.</p>
    <UL>
      <li class="troubleshootingbullet"><span class="text">Apply several coats of seal and at least 6 coats of finish.</span></li>
      <li class="troubleshootingbullet"><span class="text">Sweep and damp mop the floor often to prevent dirt from damaging the finish.</span></li>
      <li class="troubleshootingbullet"><span class="text">Scrub the floor with a general purpose cleaner and a green or blue pad when the finish won't come clean or you are not able to bring a gloss back after spray buffing.</span></li>
    </UL>
  </td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
<!--          End Answer   --> 

<tr><!-- horizontal rule -->
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
</table>




          </td>
		  <td class="smalltext" valign="up" width="20%"></td>
        </tr>
      </table>
      