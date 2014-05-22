
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
   Walking off or Flaking
  </span><br><br></td></tr>
  <tr><!-- question -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterQ.gif" vspace="3" hspace="3" WIDTH="8" HEIGHT="12"></td>
    <td class="text"><b>Why is the finish flaking and walking off?</b></td>
  </tr>
  <tr><!-- horizontal space -->
    <td colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="5" width="1"></td>
  </tr>				
  <tr><!-- answer -->
    <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_troubleshooterA.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
    <td class="text">
	<p>Finish may sometimes appear that it is "walking off" or flaking.  This is caused by poor adhesion of the finish to the floor.  Spending the labor to strip and finish a floor only to have the finish walk off in a week's time (or sometimes within hours) is a frustrating experience for anyone not to mention costly and labor intensive.  This is often caused by a procedural error so pinpoint the cause and train everyone on how to avoid it.  Also, always be aware of the type of floor you are applying finish to and any other finishes, paints, or stains that may already be on that floor.</p>
	The following can cause walking off and flaking:
	   <ul class="text">
	    <li class="troubleshootingbullet"><span class="text"><a href="#1"><span class="subheaders">Stripper residue</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#2"><span class="subheaders">Contamination</span></a></span></li>
	    <li class="troubleshootingbullet"><span class="text"><a href="#3"><span class="subheaders">Applying finish on a floor that the finish can not adhere to</span></a></span></li>
	   </ul><br>
     </td>
   </tr>
   <tr><!-- horizontal rule -->
     <td class="troubleshooterrulecolor" colspan="2"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" height="1" width="1"></td>
   </tr>
   <tr>
     <td valign="top"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" vspace="0" hspace="3" WIDTH="8" HEIGHT="11"></td>
     <td><br>
         <ul class="text">
	  <li class="troubleshootingbullet"><span class="text"><a name="1"><span class="subheaders">Stripper residue</span></a>
             <br><b>How to Fix:</b><br>The only way to fix the problem is to remove all of the effect finish by stripping the floor.  You will then need to reapply new finish.
             <p><b>How to Prevent:</b><br>Rinse!  Make sure to rinse the floor.  Many strippers on the market today say that they are a "no rinse" stripper.  This term means only that a flood rinse is not required.  However, a damp mop rinse still must be done.</p>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="2"><span class="subheaders">Contamination</span></a>
             <br><b>How to Fix:</b><br>The problem can only be fixed by removing the effected finish.  If you know that the floor was stripped as opposed to being scrubbed and recoated then you will need to strip the floor again.  If the floor was scrubbed and recoated last before the problem appeared then you will need to scrub the floor again.  Then you will need to apply new finish.
             <p><b>How to Prevent:</b><br>Always line the bucket with a plastic liner before pouring the finish into it. Unused finish should be disposed of and not poured back into the container.
Use a color-coded mop system to identify finish mops and stripper mops.
Always use clean mops specifically designed for finish to apply the finish with.</p>
                <ul class="text"><li class="troubleshootingbullet"><span class="text">Never use the mop that was used for applying the stripper to apply finish.</span></li>
                    <li class="troubleshootingbullet"><span class="text">Never use the mop that was used for damp rinsing the floor after stripping</span></li>
                    <li class="troubleshootingbullet"><span class="text">Never use the mop that was used for damp rinsing the floor after stripping </span></li>
                    <li class="troubleshootingbullet"><span class="text">Never use the mop that was used to apply a restorer.</span></li></ul>
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
	  <li class="troubleshootingbullet"><span class="text"><a name="3"><span class="subheaders">Applying finish on a floor that the finish can not adhere to</span></a>
             <br><b>How to Fix:</b><br>The only way to fix the problem is to remove all of the effect finish by stripping the floor.  Reevaluate your floor before applying new finish.
             <p><b>How to Prevent:</b><br>Check to see if there is something else on the floor aside from the finish - a stain, paint, or impregnator seal for example.  While floor finishes will normally adhere to each other fine, they may not always adhere to other types of products.  In addition you may find that the finish is adhering to the paint, for example, but the paint is not adhering to the floor.</p>

<p>Finish will not adhere to all flooring types.  Standard finishes tend to not adhere to Static dissipative floors and polished marble floors well, for example.  However, adherence should not be a problem on most flooring types.  So if you have a vinyl tile floor, concrete, rubber, or linoleum floor (to name a few) this should not be an issue.</p>
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
</td>
   </tr>														
</table>



          </td>
		  <td class="smalltext" valign="up" width="20%"></td>
        </tr>
      </table>
      