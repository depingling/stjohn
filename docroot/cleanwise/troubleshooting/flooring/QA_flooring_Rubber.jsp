
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
   Rubber Flooring
  </span><br><br></TD></tr>
<tr>
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#1">What is rubber flooring?</a></b></td></tr>
<tr>
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#2">How do I maintain a rubber floor?</a></b></td></tr>
<tr>
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#3">Can finish be applied to rubber flooring?</a></b></td></tr>
<tr>
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#4">Can a rubber floor be stripped?</a></b></td></tr>
<tr>
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#5">Can a rubber floor be burnished?</a></b></td></tr>
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
  <td class="text"><b><a name="1"></a>What is rubber flooring?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Rubber comes in tiles and sheets although the sheets seem to be more popular.  Rubber flooring is often assumed to be very flexible but this is not always true.  Also, rubber flooring often comes in raised patterns, sometimes very simple patterns and sometimes very detailed patterns.  Aggressive stripping pads and aggressive brushes can easily damage the raised pattern type rubber flooring.  While the newer rubber floors are very chemical resistant, older rubber floors can be easily damaged so some precautions should be taken when caring for rubber flooring.</td>
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
  <td class="text"><b><a name="2"></a>How do I maintain a rubber floor?</b></td>
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
       <li class="troubleshootingbullet"><span class="text">Finish:  Any finish can be used however, consider the floor before applying finish.  If your rubber flooring has an intricate raised pattern it will be difficult to apply the finish and extremely difficult to maintain the finish.  Finish can also build up around the valleys of the raised design while the finish will wear off more quickly on the peaks of the design.  If the floor is very flexible then you may consider using a softer finish that will give with the floor.</span></li>
       <li class="troubleshootingbullet"><span class="text">Stripper:   New rubber flooring is very chemical resistant, however older rubber flooring is not and can be easily damaged by strippers.  Strippers may yellow or crack the older rubber flooring.  So, if you are not sure about your floor then test a small out of sight area first.</span></li>
       <li class="troubleshootingbullet"><span class="text">Pads:  Never use high productivity stripping pads or aggressive brushes on raised rubber flooring.  When cleaning or stripping a raised rubber floor use brushes instead of pads.  Remember to avoid very aggressive brushes however.  Any type of pad can be used on flat rubber flooring.</span></li>
       <li class="troubleshootingbullet"><span class="text">Floor machines:  Do not use high speed floor machines on raised rubber flooring.  Otherwise, any floor machine can be used.</span></li>
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
  <td class="text"><b><a name="3"></a>Can finish be applied to rubber flooring?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Yes.  However, consider the floor before applying finish.  If your rubber flooring has an intricate raised pattern it will be difficult to apply the finish and extremely difficult to maintain the finish.
   <p>Finish can also build up around the valleys of the raised design while the finish will wear off more quickly on the peaks, or high portion of the design.</p>
   <p>When cleaning or stripping a rubber floor with a raised pattern try to use brushes instead of pads so that all the peaks and valleys are cleaned.</p>
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
  <td class="text"><b><a name="4"></a>Can a rubber floor be stripped?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">New rubber floors can be stripped without much problem because they tend to be very chemical resistant.  However, older rubber flooring tends to yellow and crack with stripper.  If you are not sure about your floor then test a small out of sight area first.

    <p>When cleaning or stripping a rubber floor with a raised pattern try to use brushes instead of pads so that all the peaks and valleys are cleaned.</p>

    <p>When cleaning or stripping a rubber floor with a raised pattern try to use brushes instead of pads so that all the peaks and valleys are cleaned.</p></td>
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
  <td class="text"><b><a name="5"></a>Can a rubber floor be burnished?</b></td>
</tr>
<tr><!-- horizontal space --> 
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer --> 
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Raised rubber flooring should not be burnished with a high speed machine (1500-2000+) because it might damage the floor.  You can however use a low speed machine (175-1500 rpm) to spray buff the floor.</td>
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
      