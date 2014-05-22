
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
           <span class="subheaders">Asphalt Tile</span><br><br></TD></tr>
         <tr><!-- question -->
			<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#1">What is asphalt tile?</a></b></td></tr>
<tr><!-- question -->
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#2">How should an asphalt tile floor be maintained?</a></b></td></tr>
<tr><!-- question -->
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#3">Will a stripper harm asphalt tile?</a></b></td></tr>
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
  <td class="text"><b><a name="1"></a>What is asphalt tile?</b></td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer -->
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Asphalt tile is made of resin, fillers such as asbestos and limestone, asphalt and plasticizers.  Asphalt tiles are 9"x9" in size.  Post offices may have 1'x2' black blocks.  They are usually found in darker colors that will bleed easily.  If you aren't sure if your tile is asphalt then try rubbing a cloth with mineral spirits on it.  If the color rubs off then it is asphalt.   Because asphalt tiles have asbestos you should always follow the OSHA recommendations for maintaining this type of floor.</td>
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
  <td class="text"><b><a name="2"></a>How should an asphalt tile floor be maintained?</b></td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer -->
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">
    <ul>
      <li class="troubleshootingbullet"><span class="text">Matting:  Always install and maintain proper walk off mats.  Walk off mats can reduce the amount of soil carried onto the floor by as much as 80%!  To be effective, a minimum of 20 feet of walk off matting should be installed.  However, 30 feet of walk off matting will offer the most protection.  Try to allow for 15' of outside matting, 5' of foyer matting, and 10' of inside matting.  The outside and foyer matting should be a vinyl type mat that scrapes the dirt off the feet while the inside matting can be vinyl or carpet type matting.  If this much matting is not realistic then keep in mind that the first thing a person walks on should be a vinyl type mat to scrape away dirt and then a carpet type mat to absorb any water.</span></li>
      <li class="troubleshootingbullet"><span class="text">Daily Cleaner:  Any neutral cleaner.</span></li>
      <li class="troubleshootingbullet"><span class="text">Finish:   Any.  Asphalt tile should always be sealed.  Make sure to use plenty of coats of finish to protect the flooring.  The more coats that are on the floor the more you will be able to scrub and recoat instead of strip the finish.</span></li>
      <li class="troubleshootingbullet"><span class="text">Stripper:    Aggressive strippers can damage asphalt tiles.  Because asphalt tiles have asbestos you should always follow the OSHA recommendations for maintaining this type of floor.  When stripping, always make sure to keep the floor very wet.  The best course of action for asphalt tile is to use the least aggressive method possible for maintaining the floor.  Here are some guidelines when stripping the floor.
        <ul>
         <li class="troubleshootingbullet"><span class="text">Always refer to the OSHA guidelines for stripping asbestos before stripping asphalt tile because asphalt tiles contain asbestos.</span></li>
<li class="troubleshootingbullet"><span class="text">Avoid stripping as much as possible.  Instead, make sure to apply plenty of coats of finish and scrub and recoat the floor instead.</span></li>
<li class="troubleshootingbullet"><span class="text">Use the least aggressive stripper possible and the least aggressive pad possible.  Never use a high productivity pad which are the really aggressive black stripping pads.</span></li>
<li class="troubleshootingbullet"><span class="text">Never use a solvenated stripper.</span></li>
<li class="troubleshootingbullet"><span class="text">Always keep the floor very wet when stripping</span></li>
        </ul>      
</span></li>
      <li class="troubleshootingbullet"><span class="text">Pads:  Aggressive pads will damage asphalt tiles.  Never use a high productivity pad.  The best course of action for asphalt tile is to use the least aggressive pad possible.  </span></li>
      <li class="troubleshootingbullet"><span class="text">Floor machines:   Use low speed floor machines as high speed machines could damage or loosen tiles.</span></li>
   </ul>
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
  <td class="text"><b><a name="3"></a>Will a stripper harm asphalt tile?</b></td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer -->
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Stripper can easily damage and asphalt tile.  The tile may bleed it's color and may crack.  Also, be weary of using aggressive pads and high speed floor machines on asphalt as they may damage the surface of the tile and release asbestos fibers into the air.  Here are some guidelines when stripping the floor.
    <UL>
       <li class="troubleshootingbullet"><span class="text">Always refer to the OSHA guidelines for stripping asbestos before stripping asphalt tile because asphalt tiles contain asbestos.</span></li>
       <li class="troubleshootingbullet"><span class="text">Use the least aggressive stripper possible and the least aggressive pad possible.  Never use a high productivity pad which are the really aggressive black stripping pads.</span></li>
       <li class="troubleshootingbullet"><span class="text">Never use a solvenated stripper.</span></li>
       <li class="troubleshootingbullet"><span class="text">Always keep the floor very wet when stripping</span></li>
  </td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>
<tr><!-- horizontal rule -->
  <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
</tr>
</table>



          </td>
		  <td class="smalltext" valign="up" width="20%"></td>
        </tr>
      </table>
      