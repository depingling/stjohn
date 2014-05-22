
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
                <span class="subheaders">Burnishers</span><br>
                <span class="text">When looking for a way to restore gloss to dull finish quickly and efficiently, burnishers are the way to go.  High-speed burnishers, sometimes referred to as buffers, refer to any machine that spins 1,500 revolutions per minute (rpm) or more.  These machines are available in a wide variety of speeds, sizes and power sources, which are all factors that effect productivity.  Burnishers restore a brilliant shine to floor finishes by smoothing the surface with a high-speed pad matched to the floor finish.  Burnishers can be used to restore gloss by either using a chemical restorer with the burnisher or by dry burnishing.  Using the chemical restorer with the burnisher will bring on a nicer gloss but dry burnishing still does a great job and takes a step out of the maintenance.  
</span>
<p>
When selecting a burnisher, consider the following:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#size">Size of the area to be burnished</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#power">Type of power - electric, battery, or propane</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#dust">Dust control</a></span></li>  
  <li class="selectorbullet"><span class="text"><a href="#floor">Type of flooring</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#drive">Type of driving force: wheel drive vs. pad drive</a></span></li>
</ul>
</p>

<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#size">Burnishers:  Size of the area to be burnished</a></span><br><br>
<span class="text"> 
The square footage of the area is the first factor to be considered.  After all, the machine must be able to effectively burnish the floors in the allotted amount of time given for the task.  The size of the pad used on a machine has an effect on productivity, as does pad pressure and speed.  In general, the higher the speed of the machine, the faster a shine can be restored to the floor.  But one word of caution, it also means that the floor finish can be damaged faster if the machine is held in one place for too long.  Remember to consider the amount of unobstructed, cleanable square footage.  So, make sure to deduct the space taken up by gondolas, racks, shelves, or furniture
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
<a name="#power"><b>Burnishers:  Type of power - electric, battery, or propane</b></a></span><br><br><span class="text"> 
Generally, cord electric machines cost the least, but they do need to be connected to a wall socket.  Also working around the cord can be a hindrance.  Most workers opt to drape the cord over their shoulder as they work to keep it out of the way.    
</p>

<p>
Battery burnishers are more productive because you have no cord to worry about, but they have a limited run-time and the batteries make them very heavy.   When choosing a battery burnisher keep in mind that the batteries will have to be charged.
</p>

<p>
Propane burnishers are the most productive, but they are very noisy and you have to be concerned with filling tanks, changing the oil, and storing propane.  In addition, propane burnishers are very loud, produce an odor and have emissions that may not be right for all settings.  However, propane burnishers produce a beautiful gloss and are extremely efficient.
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
<a name="#dust"><b>Burnishers:  Dust control</b></a></span><br><br><span class="text"> 
Because burnishing smoothes out the surface of the floor to produce a shine, a small amount of powdering is to be expected.  To account for this, many burnishers are equipped with dust control features that collect the dust and prevent it from being deposited back into the workplace.  Passive dust control systems use the spinning of the pad to direct the dust into a collector bag, while active systems utilize a vacuum to suck the dust into a collector.
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
<a name="#floor"><b>Burnishers:  Type of flooring</b></a></span><br><br><span class="text"> 
Some flooring types such as studded rubber and asbestos floors should not have high speed machines used on them.  There are a couple other exceptions but basically, just know the facilities' particular flooring types before choosing a machine.
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
<a name="#drive"><b>Burnishers:  Type of driving force - Wheel Drive vs. Pad Drive</b></a></span><br><br><span class="text"> 
Wheel driven propulsion systems are easier to push, especially on inclines; think of a self-propelled lawnmower.  The machine will do some of the work for you.
</p>

<p>
Pad driven propulsion systems use the spin of the pad drives to move the machine along; think of a manual push lawnmower.  You will do all of the pushing.
</p>


<br><br>
</td></tr>
</table>
</td></tr>

</div></td>
</tr>
          