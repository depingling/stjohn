
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
          <td class="smalltext" valign="up" width="20%"></td>
          <td>
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%" class="text">
              <tr>
			    <td class="text"><span class="subheaders">Finish:  Choosing the right system</span>

<p>
Believe it or not, even with the wide variety of products on the market today, floor Care Systems fall into these basic categories:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#con">Conventional</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#high">High Speed</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#ultra">Ultra High Speed (UHS)</a></span></li>
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#con"><b>Choosing the right system:  Conventional</b></a></span><br><br><span class="text"> 
You're a candidate for this program if the floor care budget is very limited or it's not important the floor shines like a diamond all the time.  Clearly everyone has a limited budget these days, but a conventionally maintained floor is done so on a shoestring.  Typically floors on this program are refinished after long intervals of use or simply stripped and recoated.  Interim maintenance can include spray buffing and scrub and recoating.  This floor care program requires a 175 rotary machine.  
</span></li></ul><table width="100%">
  <tr>
	<td align="right" class="text">
	<div class="searchmargin">
	  <a href="#">
		[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top
      </a>
	</div>
	</td>
  </tr>
</table>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#high"><b>Choosing the right system:  High-Speed</b></a></span><br><br><span class="text"> 
A high-speed program is used when there is a high appearance standard.  These floor care programs require burnishing on a regular basis.  Once the floor no longer responds to burnishing, the floor will be scrubbed and recoated.  This floor care program requires a 1000-1500 rpm burnisher.
</span></li></ul><table width="100%">
  <tr>
	<td align="right" class="text">
	<div class="searchmargin">
	  <a href="#">
		[<img src="/<%=storeDir%>/<%=ip%>images/cw_greentop.gif" border="0" WIDTH="6" HEIGHT="6">]&nbsp;Top
      </a>
	</div>
	</td>
  </tr>
</table>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#ultra"><b>Choosing the right system:  Ultra High-Speed (UHS) </b></a></span><br><br><span class="text"> 
A UHS program is typically used by facilities with very high appearance standards.  These programs are very labor intensive but deliver a terrific look if properly maintained.  As with the high-speed programs, once a UHS floor no longer responds to burnishing and maintaining, it should be scrubbed and recoated.  If the dirt and scratches are deep in the finish foundation, the floor will need to be stripped.  This floor care program requires a 1500 rpm and up burnisher to restore gloss.  A 175 swing machine will also be needed for the scrubbing and stripping jobs.
</p>


<br><br>
</td></tr>
</table>

          