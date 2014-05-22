
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
			    <td>
                <td class="text"><span class="subheaders">Carpet Extractors</span>

<p>
Carpet extractors are available in two basic types: "box and wand" types and self-contained units.   Extractors are an essential tool in carpet maintenance and spotting.  Extractors efficiently spray cleaning solution down, scrub the carpet, and vacuum up the solution.  
</p>

<p>
When choosing a self-contained extractor, consider the following:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#self">Self-contained vs. box and wand extractors</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#size">Size of the area to be cleaned</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#width">Width of doorways and floor obstructions</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#sol">Size of solution and recovery tanks</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#type">Type of driving force: automatic vs standard</a></span></li>
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#self"><b>Carpet Extractors:  Self-contained vs. box and wand extractors</b></a></span><br><br><span class="text"> 
Box and wand extractors utilize a carpet wand that sprays on cleaning solution and vacuums it up while the operator pulls the wand over the carpet.  The machine consists of a solution tank with pump and a recovery tank with a strong vacuum.  The wand is attached by a combination solution/vacuum hose, and is available in a variety of lengths.  Accessory tools can be attached to the hose for cleaning upholstery, stairs, or hard to reach areas.  Special spotting machines are miniature box and hand-tool machines that allow efficient extraction of spots.  
</p>

<p>
Self-contained carpet extractors are machines that provide one-pass cleaning without the effort of a hand wand.  In one pass, these machines spray cleaning solution down, scrub the carpet with a brush, and vacuum up the solution.  In addition, many machines offer the best of both worlds by allowing accessory tools to be attached for cleaning hard to reach areas.  
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
<a name="#size"><b>Carpet Extractors:  Size of the area to be cleaned</b></a></span><br><br><span class="text"> 
The square footage of the carpeted area is the first factor to be considered.  The machine chosen must be able to handle the amount of work needed.  For example, most large hotels use self-propelled extractors with 18"-24" cleaning paths to clean their banquet rooms and other large open areas (up to 8,000 sq. ft. /hour).  For cleaning a typical classroom or large office, a 14"-18" machine is recommended (up to 2,500 sq. ft./hour), and for smaller areas like hotel guestrooms, a 10"-14" machine is recommended (up to 1,200 sq. ft./hour).  
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
<a name="#width"><b>Carpet Extractors:  Width of doorways and floor obstructions</b></a></span><br><br><span class="text"> 
Keep in mind that the machine chosen must be able to maneuver around fixtures that cannot be moved for cleaning.  Make sure that the machine purchased can fit through and around tight areas in the facility including essential doorways. 
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
<a name="#sol"><b>Carpet Extractors:  Size of solution and recovery tanks</b></a></span><br><br><span class="text"> 
The cleaning solution is the lifeblood of the carpet extractor.  When the solution runs out or the recovery tank needs to be emptied, the operator is forced to return to the closet to dump and re-load.  For this reason, most self-contained extractors increase the tank size to accommodate a large cleaning path.
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
<a name="#type"><b>Carpet Extractors:  Type of driving force: automatic vs. standard</b></a></span><br><br><span class="text"> 
Automatic extractors basically propel themselves like a self-propelled lawn mower.  The machine will do some of the work for you.  However, with standard extractors you will need to do all of the pushing.
</p>



<br><br>
</td></tr>
</table>

</td></tr>

</div></td>
</tr>
          