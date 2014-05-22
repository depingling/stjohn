
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
         <tr><td></td><td class="text">
<span class="subheaders">
    Vinyl Composition Tile (VCT)
  </span><br><br></TD></tr>
<tr><!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#1">What is VCT?</a></b></td></tr>
<tr><!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#2">How do I maintain VCT flooring?</a></b></td></tr>
<tr><!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#3">Will stripper harm my VCT floor?</a></b></td></tr>
<tr><!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#4">Do I need to apply finish to my new VCT floor?</a></b></td></tr>
<tr><!-- question --> 
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#5">Do I need to use seal on a VCT floor?</a></b></td></tr>

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
  <td class="text"><b><a name="1"></a>What is VCT?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Vinyl composition tile is commonly called VCT.  VCT is made of vinyl resins and synthetic fibers.  The synthetic fibers replaced the asbestos previously used in floor tile.   VCT is made in 12" x 12" tiles and is found in almost any facility.</td>
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
  <td class="text"><b><a name="2"></a>How do I maintain VCT flooring?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Matting:  Always install and maintain proper walk off mats.  Walk off mats can reduce the amount of soil carried onto the floor by as much as 80%!  To be effective, a minimum of 20 feet of walk off matting should be installed.  However, 30 feet of walk off matting will offer the most protection.  Try to allow for 15' of outside matting, 5' of foyer matting, and 10' of inside matting.  The outside and foyer matting should be a vinyl type mat that scrapes the dirt off the feet while the inside matting can be vinyl or carpet type matting.  If this much matting is not realistic then keep in mind that the first thing a person walks on should be a vinyl type mat to scrape away dirt and then a carpet type mat to absorb any water.
    <UL>
      <li class="troubleshootingbullet"><span class="text">Daily Cleaner:  Any neutral cleaner can be used.</span></li>
      <li class="troubleshootingbullet"><span class="text">Finish:   Any finish can be used.</span></li>
      <li class="troubleshootingbullet"><span class="text">Stripper: Any stripper can be used.</span></li>
      <li class="troubleshootingbullet"><span class="text">Pads:  For buffing use a red pad.  For burnishing use a pad matched to the finish and speed of the floor machine.  For scrubbing and recoating use a green or blue pad.  For stripping use a black stripping pad or a high productivity pad.</span></li>
      <li class="troubleshootingbullet"><span class="text">Floor machines: Any.</span></li>
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
  <td class="text"><b><a name="3"></a>Will stripper harm my VCT floor?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">With the exception of newly installed floors, you can use any stripper on your VCT floor.  VCT is very durable and chemical resistant.  However, extra precautions should be used on any new VCT floors.  Always refer to the flooring manufacturer's recommendations before beginning any maintenance procedure on a new floor.  Some manufacturers recommend that you wait 2 days before stripping, while others recommend that you wait 30 days to 2 years before stripping.  The concern with strippers is that it may cause the adhesive to fail on the flooring.  In many cases going against the manufacturer's recommendations will void the warranty, so play it safe and follow the recommendations for your particular floor.</td>
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
  <td class="text"><b><a name="4"></a>Do I need to apply finish to my new VCT floor?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">New VCT flooring will have a factory seal on it.  In most cases you do not have to apply finish to the floor but it is recommended unless the floor is in an area where it will be subject to grease and/or water.  Applying finish will help protect the floor and extend the life in addition to enhancing the appearance of the floor.  Keep in mind that you will have to remove the factory seal before applying finish by scrubbing with a general purpose cleaner and a green or blue pad.</td>
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
  <td class="text"><b><a name="5"></a>Do I need to use seal on a VCT floor?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">No.  Finishes on the market today have excellent leveling capabilities and will have a great appearance without a seal.  However, consider using a seal if:
    <UL>
      <li class="troubleshootingbullet"><span class="text">You are looking to stretch your budget.  Seal is more economical than finish.  Consider applying 2 coats of seal and 4 coats of finish instead of the standard 6 coats of finish.</span></li>
      <li class="troubleshootingbullet"><span class="text">You have an older, worn floor.  Finishes alone may not be able to give a smooth glossy appearance to this type of floor.</span></li>
      <li class="troubleshootingbullet"><span class="text">Your floor is subject to heavy traffic and or soil and you want additional protection under the finish.</span></li>
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
      