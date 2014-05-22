
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
  <tr></td><TD><td class="text">
<span class="subheaders">
   Linoleum Floor
  </span><br><br></TD></tr>
<tr><!-- question -->
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#1">What is linoleum?</a></b></td></tr>
<tr><!-- question -->
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#2">How do I maintain a linoleum floor?</a></b></td></tr>
<tr><!-- question -->
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#3">How do I fix a linoleum floor that has turned yellow?</a></b></td></tr>
<tr><!-- question -->
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#4">How do I fix a linoleum floor that has cracked?</a></b></td></tr>
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
  <td class="text"><b><a name="1"></a>What is linoleum?</b></td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer -->
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Linoleum floors are made of linseed oil, cork, and fillers like wood flour on a jute or a fiberglass backing.  Linoleum is often confused with sheet vinyl.  Many people may think that their floor is linoleum when it is actually a sheet vinyl.  There is no way to look at these types of floors and identify which one they are.  However, there is a major difference in how these types of floors should be maintained.  Linoleum is very sensitive to cleaners and strippers and can be easily damaged whereas sheet vinyl is not.  Strippers can turn a linoleum floor yellow and crack it so they should not be used.

    <p>Note that many people confuse linoleum floors with sheet vinyl because it is very difficult to tell the difference by just looking at the floor.  Sheet vinyl is very chemical resistant and can have almost any maintenance procedure performed on it whereas linoleum is extremely sensitive to chemicals.</p></td>
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
  <td class="text"><b><a name="2"></a>How do I maintain a linoleum floor?</b></td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer -->
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">
   <UL>
     <li class="troubleshootingbullet"><span class="text">Matting:  Always install and maintain proper walk off mats.  Walk off mats can reduce the amount of soil carried onto the floor by as much as 80%!  To be effective, a minimum of 20 feet of walk off matting should be installed.  However, 30 feet of walk off matting will offer the most protection.  Try to allow for 15' of outside matting, 5' of foyer matting, and 10' of inside matting.  The outside and foyer matting should be a vinyl type mat that scrapes the dirt off the feet while the inside matting can be vinyl or carpet type matting.  If this much matting is not realistic then keep in mind that the first thing a person walks on should be a vinyl type mat to scrape away dirt and then a carpet type mat to absorb any water.
	</span></li>
     <li class="troubleshootingbullet"><span class="text">Daily Cleaner: Any neutral cleaner.</span></li>
     <li class="troubleshootingbullet"><span class="text">Finish: Most finishes will adhere to most linoleum floors.  You will need to match the finish to the frequency and type of maintenance as well as they kind of floor machines you have.  However, before applying finish to a new floor make sure to refer to the manufacturer's recommendations for maintenance. </span></li>
     <li class="troubleshootingbullet"><span class="text">Strippers:  Strippers should not be used on linoleum unless they have been specifically designed for linoleum.   Strippers are very high in alkalinity and will react with the linseed oil in the linoleum and eventually cause the floor to crack and yellow.  Instead scrub the floor with a general purpose cleaner and then recoat with new finish.  The best rule of thumb for linoleum is to use the least aggressive maintenance possible to keep the floor looking good.  This means keep up good daily maintenance such as sweeping and mopping and scrub and recoat the floor often to virtually eliminate the need to strip.</span></li>
     <li class="troubleshootingbullet"><span class="text">Pads: Any</span></li>
     <li class="troubleshootingbullet"><span class="text">Floor machines:   Use low speed floor machines as high speed machines could be too aggressive and damage the floor. Do not use high alkaline products like strippers and degreasers that will react with the linseed oil in the linoleum and will eventually cause the floor to crack.  Instead, use neutral cleaners and general purpose floor cleaners.</span></li>
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
  <td class="text"><b><a name="3"></a>How do I fix a linoleum floor that has turned yellow?</b></td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer -->
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Unfortunately if a linoleum floor has turned yellow it is most likely because a stripper was used on the floor.   There is no way to remove the yellow so the flooring may need to be replaced depending on the severity of the discoloration.</td>
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
  <td class="text"><b><a name="4"></a>How do I fix a linoleum floor that has cracked?</b></td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer -->
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Linoleum floors that have cracked have been damaged and are not repairable.</td>
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
      