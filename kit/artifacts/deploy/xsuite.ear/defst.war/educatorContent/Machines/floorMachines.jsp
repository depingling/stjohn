
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
                <td class="text"><span class="subheaders">Floor Machines</span>

<p>
Floor machines are the most versatile piece of equipment in any building.  The term floor machine usually refers to low speed floor machines that run under 1500 rpm.  They are also referred to as swing machines.  Whether you are stripping tile floors, cleaning grout, scrubbing concrete or bonnet cleaning carpets, your floor machine is always ready.  In addition to the machine itself, there are many floor machine accessories to consider which may or may not be included with the machine.  
</p>

<p>
When selecting a floor machine, consider the following:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#size">Size</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#speed">Speed</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#horse">Horsepower</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#brush">Floor Brushes</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#pad">Floor Pads</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#hold">Pad Holders</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#plate">Brush Plates</a></span></li>
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#size"><b>Floor Machines:  Size</b></a></span><br><br><span class="text"> 
Although 20"machines are the most popular, 13'', 15", and 17" machines are also available for cleaning those hard to reach areas.  
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
<a name="#speed"><b>Floor Machines:  Speed</b></a></span><br><br><span class="text"> 
Low speed floor machines have different speed options.  One-speed floor machines typically run at 175 rpm or 1500 rpm.  Two-speed floor machines will run at either 175 rpm or 300 rpm.  Variable speed machines can run anywhere in between 175 and 300 rpm.  Rpm stands for pad rotations per minute.  The faster the rotation, the faster the job is done.
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
<a name="#horse"><b>Floor Machines:  Horsepower</b></a></span><br><br><span class="text"> 
The big question here is whether or not the machine will be used for spin bonneting.  Consider a 1.5 horsepower model if the primary job will be spin cleaning carpets or sanding hardwood floors.  For standard stripping and scrubbing a 1 horsepower machine will generally suffice.
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
<a name="#brush"><b>Floor Machines:  Floor Brushes</b></a></span><br><br><span class="text"> 
Brushes are necessary for cleaning uneven surfaces, such as grouted tile floors, unsealed concrete or rough stonework, and are available in a variety of stiffness and fibers.  Note that when choosing brushes for the floor machine, it is important to choose a brush that is 2" less in diameter than your machine for proper fit.  For example, a 20" machine would use an 18" brush.
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
<a name="#pad"><b>Floor Machines:  Floor Pads</b></a></span><br><br><span class="text"> 
Floor pads are used on smooth and level surfaces, such as vinyl tile, terrazzo and smooth concrete, and require a pad holder.   Floor pads should be purchased to match the size of the machine.  Pads are available in variety of aggressiveness.  The aggressiveness of the pad will determine what job the machine does, whether it will buff, scrub, or strip a finish.
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
<a name="#hold"><b>Floor Machines:  Pad Holders</b></a></span><br><br><span class="text"> 
When ordering pad holders, match the diameter of the pad holder with the machine.  For example, a 20" machine would use a 20" pad holder.  
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
<a name="#plate"><b>Brush Plates</b></a></span><br><br><span class="text"> 
Both brushes and pad holders require a brush plate to connect to the floor machine.
</p>

<br><br>
</td></tr>
</table>

          