
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
			    <td>
                <span class="subheaders">Finish:  Types of Finish</span><br>
                <<p>
Back in the 'old days' we used to refer to floor coatings as 'wax' since the products contained a great deal of carnauba wax.  Today the correct term is 'Finish'.  The 'wax' is mostly gone in today's finishes.  Since the finish is the heart and soul of the floor care program, when selecting a finish, it's important to match the finish with the available equipment, appearance standards and maintenance frequency.  All of these things will directly affect the performance of the finish.  Also determine what category the finish performs in.  Cost and solid content are two other areas that some people feel are important but read on to understand why these should not be of great concern.   
<ul>
  <li class="selectorbullet"><span class="text"><a href="#con">Conventional Finishes</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#high">High-Speed Finishes</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#ultra">Ultra High-Speed (UHS) Finishes</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#cost">Cost</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#solid">Solid Content</a></span></li> 
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#con"><b>Types of Finish:  Conventional Finishes</b></a></span><br><br><span class="text">
Conventional finishes tend to refer to the ease of scrubbing or stripping and refinishing, and spray buffing.  This type of finish is for use primarily with 175-300 rpm swing machine.
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
<a name="#high"><b>Types of Finish:  High-Speed Finishes</b></a></span><br><br><span class="text">
High-Speed finishes tend to refer to high gloss, ease of scrubbing or stripping and refinishing.  The ability of the floor to bounce back quickly when burnished may also be mentioned.  This type of finish is for use primarily with 1000+ rpm floor machines.
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
<a name="#ultra"><b>Types of Finishes:  Ultra High-Speed (UHS) Finishes</b></a></span><br><br><span class="text">
Ultra High-Speed Finishes are usually defined with terms such as 'wet look' gloss, UHS, excellent durability and burnish response, great resistance to scuffing.  This type of finish is for use primarily with 1500+ rpm floor machines.
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
</table><br>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#cost"><b>Types of Finishes:  Cost</b></a></span><br><br><span class="text">
Cost is always a concern.  Typically higher priced finishes will be more durable finishes.  However, this rule is not set in stone.  Keep in mind that the better daily and weekly floor care maintenance is adhered to, the longer the finish will last.  Ultimately this is what will truly affect the bottom line, not the initial price of the finish.
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
<a name="#solid"><b>Types of Finishes:  Solid Content</b></a></span><br><br><span class="text">
Solid content of a finish can sometimes be a confusing thing.  One point of view is that the more solids in a finish, the stronger the finish.  However, there is no real evidence of that in today's finishes.  Yes, a 20% solids finish will be more durable than a 16% solids finish.  However, that is about the limit.  A 25% solids finish will tend to be less durable and stable than a 20% solids finish.  More is not always better.  What really matters is the formula as a whole, so don't let the amount of solids be the decision maker.
</p>


<br><br>
</td></tr>
</table>

          