
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
  Slippery Floors
  </span><br><br></td></tr>
  <tr><!-- question -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
    <td class="text"><b>Why is the floor slippery?</b></td>
  </tr>
  <tr><!-- horizontal space -->
    <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
  </tr>				
  <tr><!-- answer -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
    <td class="text">
	<p>Slippery floors are a serious safety concern.  While many people assume that the finish on the floor is slippery, especially when there is high gloss, it is almost always a contaminate on the floor that is slippery.  Most floor finish manufacturers ensure all of their finishes meet or exceed the American Society for Testing Materials (ASTM) standard D-2047-93 for slip coefficient for friction.</p>
	Following are some of the contaminates that may make a floor slippery:
	   <ul class="text">
	    <li class="troubleshootingbullet"><span class="text"><a href="#1"><span class="subheaders">Water</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#2"><span class="subheaders">Furniture polish</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#3"><span class="subheaders">Grease</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#4"><span class="subheaders">Oil dust mop treatment</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#5"><span class="subheaders">Powdering</span></a></span></li>
	   </ul><br>
     </td>
   </tr>
   <tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
     <td><br><ul class="text">
	  <li class="troubleshootingbullet"><span class="text"><a name="1"><span class="subheaders">Water</span></a>
             <br><b>How to Fix:</b><br>Water on floor finish is extremely slippery.  Make sure to post wet floor signs and remove the water immediately.  Water that sits on floor finish can also damage the finish and may even leave white spots on the finish.  If burnishing does not remove the spots then you will need to remove the damaged finish either by scrubbing the finish or stripping the finish.
             <p><b>How to Prevent:</b><br>Finish should not be used in areas where water will likely be on the floor, such as shower rooms.  Instead, opt for a strong daily maintenance plan that will ensure clean, safe floors.</p>

             <p>If it is a rainy day make sure to replace the walk off matting frequently to prevent water being tracked on the floor.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="2"><span class="subheaders">Furniture polish</span></a>
             <br><b>How to Fix:</b><br>If furniture polish has contaminated the finish and created a slippery situation then the only way to fix the problem is to remove the effected finish.  You will need to scrub and recoat the floor.  Simply cleaning the floor with a neutral cleaner will not remove the furniture polish.  A general purpose cleaner and a green or blue pad with a floor machine will need to be used to scrub the finish and remove the furniture polish.
             <p><b>How to Prevent:</b><br>Furniture polish can make floor finish seem like a skating rink so the best course of action is to avoid any over spray from getting on the floor when spraying the furniture polish.  So, always spray it onto a rag over a trashcan. </p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="3"><span class="subheaders">Grease</span></a>
             <br><b>How to Fix:</b><br>The only way to fix the problem is to remove the grease.  Many degreasers tend to dull floor finish.  If your floor often has grease on it you should not use a finish on that floor due to slip hazards.  When cleaning the floor, be sure to empty your water often and change mop heads often to prevent spreading the grease around the floor.  If using a floor machine, make sure to change the pad often.  You may find that a brush will help to clean up the grease especially if your floor is grouted or rough.
             <p><b>How to Prevent:</b><br>Finish should not be applied in areas where grease is likely to soil the floor, such as kitchens and automotive shop floors.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="4"><span class="subheaders">Oil dust mop treatment</span></a>
             <br><b>How to Fix:</b><br>Oil treated dust mops can cause slippery conditions on floor finishes.  If a dust mop treatment has contaminated the finish and created a slippery situation then the only way to fix the problem is to remove the effected finish.  You will need to scrub and recoat the floor.  Simply cleaning the floor with a neutral cleaner will not remove the furniture polish.  A general purpose cleaner and a green or blue pad with a floor machine will need to be used to scrub the finish and remove the treatment.  
             <p><b>How to Prevent:</b><br>Always use paraffin treated dust mops and stay away from oil treatments on floor finish.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="5"><span class="subheaders">Powdering</span></a>
             <br><b>How to Fix:</b><br>If powdering has caused the slippery floor then dry mop the floor.  If possible also wet mop.
             <p><b>How to Prevent:</b><br>Remember to dry mop the floor after burnishing.  If powdering is an ongoing problem for your facility then ask the Troubleshooter "Why is my finish powdering?"  Powdering can be caused by poor adhesion of finish, too aggressive a pad or machine for the finish, dust or dirt on the floor, and dirty pads among other things.</p>
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
      