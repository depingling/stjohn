
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
			   <td class="text"><span class="subheaders">Glass Cleaners</span>

<p>
They're not just for windows anymore!  Advances in chemical science have made it possible to use glass cleaners on a wide selection of materials.  Now your glass cleaner can be a light duty surface cleaner too!  Consider the following when choosing a glass cleaner:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#small">Small area cleaning</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#large">Large area cleaning</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#light">Light-duty surface cleaning</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#streak">Streaking</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#cost">Cost</a></span></li>
</ul>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#small"><b>Glass Cleaners:  Small area cleaning</b></a></span><br><br><span class="text">
If you are cleaning a small area, using ready-to-use or premixed products make a lot of sense. You do not have to worry about improperly mixed dilutions that cost you money in extra material costs, damaged building assets, and labor.  Ready-to-use or rtu products come in either refillable spray bottles or aerosol cans. 
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
<a name="#large"><b>Glass Cleaners:  Large area cleaning</b></a></span><br><br><span class="text">
Large areas that are cleaned on a frequent basis require significantly more glass cleaner to get the job done.  Ready to use products will do the job but they are more expensive, especially when you have a big workload.  Consider purchasing cleaner concentrates or using a dilution control system to make up your own bottles.  This method saves money and virtually eliminates human error in diluting product.  Remember when you mix any chemical and store it in spray bottles, you must have a secondary label clearly identifying the contents.
</p>

<p>
If you want to avoid the expense of a dilution control unit, and you have easily manageable areas, you may want to consider portion-controlled packets that contain pre-measured cleaner concentrates. When the packets are added to water, they provide effective cleaning solutions in larger quantities.
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
<a name="#light"><b>Glass Cleaners:  Light-duty surface cleaning </b></a></span><br><br><span class="text">
Glass cleaners make great light-duty surface cleaners!  There are different formulations of glass cleaners available.  Some are ammonia based, while others have different cleaning agents.  Some glass cleaners are designed to clean a host of surfaces from stainless steel to plastic glass (like Plexiglas®.)  Before using glass cleaner on surfaces other than glass, check the product label for proper use.  Also, be aware that many glass cleaners contain ammonia, which can damage some surfaces.  
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
<a name="#streak"><b>Glass Cleaners:  Streaking  </b></a></span><br><br><span class="text">
Streaking is always a big concern when deciding what glass cleaner to use.  For dependable results, purchase a quality glass cleaner by a reputable company.  The old philosophy of more expensive is better quality is usually true when it comes to glass cleaners.  A good quality glass cleaner will quickly clean a variety of soils and dry quickly, leaving the glass streak free.  To minimize streaking make sure to use a clean white, lint-free cloth when cleaning glass.  Hard water can cause a glass cleaner to streak.  If the facility has hard water, consider using a ready-to-use product rather than blending on-site with a dilution control program.  
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
<a name="#cost"><b>Glass Cleaners:  Cost</b></a></span><br><br><span class="text">
Controlling costs is always a high priority.  To effectively manage inventory, know how much solution is needed to do the job.  Once the material usage has been calculated, it will be easier to make an informed decision whether or not rtu (ready to use) glass cleaners, concentrates dispensed via a dilution system, or portion controlled packets are the right answer. 
</p>



<br><br>
</td></tr>
</table>
</td></tr>

</div></td>
</tr>
          