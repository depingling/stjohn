
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
   Degreasers
  </span><br><br></TD></tr>
<tr><!-- question -->
					<td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
					<td class="text"><b><a href="#4">How do I make my degreaser work better?</a></b></td></tr>
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
  <td class="text"><b><a name="4"></a>How do I make my degreaser work better?</b></td>
</tr>
<tr><!-- horizontal space -->
  <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
</tr>				
<tr><!-- answer -->
  <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
  <td class="text">Degreasers work based on several things; alkalinity, dwell time, hot water, strength of dilution and agitation.
    <UL>
      <li class="troubleshootingbullet"><span class="text">Alkalinity:<br>
The more alkaline the degreaser, the more likely it is to work.  Alkalinity means where it falls on the pH scale.  Most degreasers have a very high alkalinity however, some people want to stay away from highly alkaline degreasers for hazard and safety concerns.  However, if proper precautions are taken and the correct person protective equipment (PPE) is used, these degreasers are safe to use.</span></li>
      <li class="troubleshootingbullet"><span class="text">Dwell Time:<br>
Allowing a degreaser to dwell or sit on the floor for several minutes, at least 5, will increase the cleaning actions.  A degreaser that has been on the floor for only a minute has not had much time to break apart the grease.</span></li>
      <li class="troubleshootingbullet"><span class="text">Hot Water:<br>
Most degreaser will work better with hot water.  While you can still use cold water, hot water will help to liquefy the grease.</span></li>
      <li class="troubleshootingbullet"><span class="text">Strength of Dilution:<br>
The more juice the more action.  While more is not always better, with degreasers more sometimes is better.  Stay away from going to strong on dilutions because you may find yourself in quite a bind with excess foam.  Also, don't use a degreaser without diluting it because water helps the cleaning action.</span></li>
      <li class="troubleshootingbullet"><span class="text">Agitation:<br>
The more agitation the better.  Ideally, the floor should be scrubbed with a floor machine with a brush.  But, if that isn't available then use a stiff bristled brush like a deck brush.</span></li>
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
<!--          End Answer   -->
<!--        QA bottom navigation    -->
<tr>
  <td colspan="2">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
     <tr>
      <td class="text" colspan="2"><div class="searchmargin">
          
      </td>
      
     </tr>
<!--    End Bottom Navigation    -->
    </table>
   </td>
</tr>
						
</table>


          </td>
		  <td class="smalltext" valign="up" width="20%"></td>
        </tr>
      </table>
      