
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
   Slate
  </span><br><br></TD></tr>
<tr><!-- question -->
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#1">What is Slate?</a></b></td></tr>
<tr><!-- question -->
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#2">How do I maintain a slate floor?</a></b></td></tr>
<tr><!-- question -->
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#3">Will any cleaning products damage a slate floor?</a></b></td></tr>
<tr><!-- question -->
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#4">Why does it look like the slate floor is wearing away down the middle?</a></b></td></tr>
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
  <td class="text"><b><a name="1"></a>What is Slate?</b></td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer -->
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Slate is a hard, fine-grained rock that naturally separates into thin, smooth-surfaced layers.  However, the surface is usually uneven.  Slate is not very porous and it is very chemical resistant so just about any maintenance and cleaner can be used on this type of flooring.  Slate floors are used both indoors and outdoors.  Our's maintenance recommendations refer only to interior slate floors.  Because slate floors are very chemical resistant, almost any product and maintenance procedure can be used on this type of flooring.</td>
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
  <td class="text"><b><a name="2"></a>How do I maintain a slate floor?</b></td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer -->
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">
    <UL>
      <li class="troubleshootingbullet"><span class="text">Matting:  Always install and maintain proper walk off mats.  Walk off mats can reduce the amount of soil carried onto the floor by as much as 80%!  To be effective, a minimum of 20 feet of walk off matting should be installed.  However, 30 feet of walk off matting will offer the most protection.  Try to allow for 15' of outside matting, 5' of foyer matting, and 10' of inside matting.  The outside and foyer matting should be a vinyl type mat that scrapes the dirt off the feet while the inside matting can be vinyl or carpet type matting.  If this much matting is not realistic then keep in mind that the first thing a person walks on should be a vinyl type mat to scrape away dirt and then a carpet type mat to absorb any water.</span></li>
      <li class="troubleshootingbullet"><span class="text">Daily Cleaner:  Use any neutral cleaner for the daily cleaner.  The one exception to this is if an impregnator seal has been applied to the slate, then the seal manufacturer may recommend that a special cleaner be used on the floor.  Impregnator seals tend to make water bead up on the surface of the floor, which prevents standard cleaners from fully wetting the surface of the floor.</span></li>
      <li class="troubleshootingbullet"><span class="text">Finish:  Finish and seals will help to make a slate floor glossy and easier to keep clean, especially the grout lines.  Before applying any finish or seal make sure that there is good adhesion to the floor by testing the adhesion on a small (couple of inches) section of the floor.    Also, remember that finishes will have to be buffed or burnished.  If your slate floor has a lot of uneven surfaces it may be difficult to uniformly buff the floor.  Impregnator seals can also be used on slate but remember that most of these seals do not have a glossy appearance.</span></li>
      <li class="troubleshootingbullet"><span class="text">Stripper:  Any stripper can be used on a slate floor.</span></li>
      <li class="troubleshootingbullet"><span class="text">Pads:  For cleaning use red pads or a medium grade brush.  Using a brush during daily cleaning may help to clean the grout lines and uneven surfaces.  However, brushes will dull floor finish.   When stripping use a black stripping pad or a brush suitable for stripping floors.</span></li>
      <li class="troubleshootingbullet"><span class="text">Floor machines: Any</span></li>
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
  <td class="text"><b><a name="3"></a>Will any cleaning products damage a slate floor?</b></td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer -->
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Slate is very chemical resistant so just about any product and maintenance procedure can be used on a slate floor.</td>
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
  <td class="text"><b><a name="4"></a>Why does it look like the slate floor is wearing away down the middle?</b></td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer -->
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">While slate could wear away over a long period of time, it stands up to traffic very well.  If you notice a dulling or wearing away where the traffic patterns are, then this is most likely caused by the finish or seal wearing.  Try stripping the finish and then reapply new finish.  If a permanent seal was used then it may take several stripping sessions before the seal is removed.  Permanent seals are very difficult to remove but they are necessary to remove because they do wear with the traffic patterns.</td>
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
      