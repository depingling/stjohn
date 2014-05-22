
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

<tr>
		<td class="smalltext" valign="up" width="20%">
          
            <div class="twotopmargin">
		      <p>

              </p>
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%" class="text">
              <tr>
			    <td class="text"><span class="subheaders">Floor Care: Cleaners</span>

<p>
Years ago, all cleaners were created equal.  Today, it’s important to select the right cleaner for the job.  When determining if a neutral cleaner, general-purpose cleaner, or specialty cleaner should be used, consider the following:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#neutral">Neutral Cleaners</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#general">General-Purpose Cleaners</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#spec">Specialty Cleaners</a></span></li>
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#neutral"><b>Floor Care Cleaners:  Neutral Cleaners</b></a></span><br><br><span class="text"> 
Neutral cleaners are the best cleaners for daily cleaning normal soil loads.  These cleaners are typically described as being non-dulling and rinse free since they do not require a rinse.  When used properly, a good neutral cleaner will not leave a dulling haze on the floor.
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
<a name="#general"><b>Floor Care Cleaners:  General-Purpose cleaners</b></a></span><br><br><span class="text"> 
Choose a general-purpose cleaner to deep scrub floors prior to a recoat.  If a floor, such as a grade school corridor, has a heavy soil load, a general-purpose cleaner can be used if needed but should not be used as the daily cleaner. General-purpose cleaners are often described as GP cleaners or products that work well in scrub and recoat programs.
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
<a name="#spec"><b>Floor Care Cleaners:  Specialty Cleaners</b></a></span><br><br><span class="text"> 
Some cleaners are used for specialized applications.  These products should be chosen carefully.  You may need a specialized cleaner for an ultra high-speed (UHS) floor care program.  These cleaners can be identified by key words to define the product such as ‘specially formulated’, ‘for UHS floors’.
</p>


<br><br>
</td></tr>
</table>
</td></tr>

</div></td>
</tr>
          