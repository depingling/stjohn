
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
			    <td class="text"><span class="subheaders">Deodorants and Dispensers</span>

<p>
Deodorants are used to tackle odor problems and are available in a wide variety of formats such as aerosols, solids, liquids, powders and blocks. Each type of deodorant is usually available in different fragrances to accommodate most senses; a few even match the cleaners used in the restroom. Before choosing a deodorant and dispenser, be aware that certain deodorants have more 'staying power' or shelf life than others. For example, an aerosol deodorant will quickly deodorize the air or a surface, but won't provide deodorizing benefits over an extended period of time. A deodorant solid on the other hand, will tend to last several days to a month and can provide round the clock protection even when the cleaning crews are off shift.
</p>
<p>
Deodorants are good solutions to tough odor problems but should not be used as a substitute for cleaning.  Also, never add a deodorant to a disinfectant cleaner, it could cause the disinfectant to be ineffective as a disinfectant and it violates the federal law on the use of a disinfectant.
</p>
<p>
Following is some information to help you choose the deodorants and dispensers for your facility:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#counteractants">Counteractants</a></span><span class="text"></li>
  <li class="selectorbullet"><span class="text"><a href="#enzymes">Enzymes</a></span><span class="text"></li>
  <li class="selectorbullet"><span class="text"><a href="#aerosols">Aerosols and liquid deodorants</a></span><span class="text"></li>
  <li class="selectorbullet"><span class="text"><a href="#concentrates">Deodorant concentrates</a></span><span class="text"></li>
  <li class="selectorbullet"><span class="text"><a href="#systems">Deodorant systems</a></span><span class="text"></li>
  <li class="selectorbullet"><span class="text"><a href="#powders">Deodorant powders</a></span><span class="text"></li>
  <li class="selectorbullet"><span class="text"><a href="#urinal">Urinal blocks, bowl blocks, urinal screens, rim cages and rim sticks</a></span><span class="text"></li>
  <li class="selectorbullet"><span class="text"><a href="#drip">Urinal Drip Units</a></span><span class="text"></li>
  <li class="selectorbullet"><span class="text"><a href="#wall">Wall blocks, wall block holders and wall disks</a></span><span class="text"></li>
  <li class="selectorbullet"><span class="text"><a href="#packets">Deodorant packets</a></span><span class="text"></li>
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#counteractants">Deodorants and Dispensers:  Counteractants<br></a></span><span class="text">
In the world of odor management, some products are classified as counteractants. This special odor control product neutralizes the cause of the odor, and does not act exclusively like an olfactory band-aid. Counteractants work at addressing the toughest odor problems.
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

<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#enzymes">Deodorants and Dispensers:  Enzymes<br></a></span><span class="text">
Some deodorants contain enzymes.  Enzymes actually go after the odor causing bacteria and eliminate the odor.  Deodorants without enzymes simply mask the odor.  Enzyme containing deodorants are especially effective against odors caused by urine, animal fats, and food.  If you choose to use a deodorant with enzymes you should be aware of the following:</span>
<ul>
  <li class="selectorbullet"><span class="text">Always apply the enzyme deodorant on the source of the odor.  Never spray the product into the air.</span></li>
  <li class="selectorbullet"><span class="text">Make sure to follow the dilution recommendation on the label.  It is easy to want to go full strength with these products to really kill the strong odors.  But, the fact is that the dilution process is what activates the enzymes to make the product work.  Going full strength will actually be less productive and more costly!</span></li> 
  <li class="selectorbullet"><span class="text">The longer the surface stays wet with the diluted enzyme product, the longer the enzymes have to work.  The enzymes stop working once the surface is dry.</span></li>
  <li class="selectorbullet"><span class="text">Do not use enzymes on wool carpets.  Wool is protein and the little enzymes love to eat protein.</span></li>
  <li class="selectorbullet"><span class="text">Never mix enzyme deodorants with disinfectants or sanitizers.  The disinfectants and enzymes work against each other.  The disinfectants will kill the enzymes and the enzymes will adversely affect the efficacy of the disinfectant.  The result is that you won't kill the germs and you won't deodorize!  In addition, using a registered disinfectant other than the way it described on the label is a violation of the federal law regulating the use of a disinfectant.</span></li>
  <li class="selectorbullet"><span class="text">Enzyme deodorants are great to use in grease traps, dumpsters and smelly drains.  The enzymes will not harm the septic systems because the soil load in the septic systems is too high for the enzymes to adversely affect it.</span></li>
</ul>
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

<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#aerosols">Deodorants and Dispensers:  Aerosols and liquid deodorants<br></a></span><span class="text">
These are good for quick deodorizing of air and surfaces. Aerosols and liquid deodorants provide ready-to-use deodorizing, especially in smaller facilities.  These are most effective if they are applied directly on the source of the odor as opposed to simply being sprayed in the air.
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
<a name="#concentrates">Deodorants and Dispensers:  Deodorant concentrates<br></a></span><span class="text">
Deodorant concentrates provide more economy than the ready to use formulas.  Many manufacturers are including deodorant concentrates in their dilution control dispensing systems enabling your facility to get both economy and easy to mix and use products.  Some deodorant concentrates can also be added to extraction cleaners for deodorizing carpets while cleaning.  Be careful to make sure that the deodorant and extraction cleaner fragrances coordinate before mixing the products together.  
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
<a name="#systems">Deodorants and Dispensers:  Deodorant systems<br></a></span><span class="text">
These are combinations of deodorants and dispensers.  The deodorants are comprised of solids or gels and fit into a wall-mounted dispenser for round the clock coverage.  These systems are especially helpful in keeping the air smelling fresh in public restrooms, around waste receptacles, and dirty linen areas.  Often deodorant systems contain fans that are battery powered to assist in moving the deodorant throughout the area.  For the most effective coverage, create a schedule and periodically replace the battery and the deodorant.  Many good systems will include the batteries along with the deodorant refill.  This convenience eliminates the need to source batteries separately.  Each dispenser will cover a pre-determined area such as 1000 square feet per unit so for best coverage, install enough dispensers to cover the area.  Place dispensers near toilet and urinal areas but away from air vents and doorways.  This keeps deodorant where it's needed. Dispensers have a variety of features so check manufacturers description for details.
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
<a name="#powders">Deodorants and Dispensers:  Deodorant powders<br></a></span><span class="text">
Deodorant powders are used for deodorizing carpets.  Sprinkle on the area and vacuum for quick refreshing of carpets and rooms.  Powders are for use only on dry carpets.
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
<a name="#urinal">Deodorants and Dispensers:  Urinal blocks, bowl blocks, urinal screens, rim cages and rim sticks<br></a></span><span class="text">
These items are used to clean and deodorize urinals and bowls on an ongoing basis.  Many have pleasing fragrances and will last a month or so.  They are good companion products to the washroom program, and like dispenser systems will help to keep the restroom smelling clean and fresh between cleanings.  These types of products are often used in areas with lower volume or the need for a more 'upscale' appearance such as using rim cages in the bathrooms for the company President.
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
<a name="#drip"> Deodorants and Dispensers:  Urinal Drip Units<br></a></span><span class="text">
These units fit above a urinal or toilet bowl and periodically dispense a small amount of deodorant protection inside the bowl.  Deodorant refill cartridges are replaced approximately once a month.  Often the dispenser will also allow air to flow through and deodorize the air in the restroom as well.  Urinal drip systems are popular for use in areas with higher volume.
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
<a name="#wall"> Deodorants and Dispensers:  Wall blocks, wall block holders and wall disks<br></a></span><span class="text">
These deodorant programs are more passive than the deodorant systems and do not use batteries and fans to distribute the deodorized air.  Instead, they rely on the airflow in the area to move the air and the deodorant.  Often they are applied by removing the back of the applied double face tape before being pressed into place on any 'tape safe' surface.  These types of deodorants are good to use in smaller spaces where some deodorizing is needed.  Units are usually disposed of once the deodorant benefits are no longer effective.  Also, since the dispenser is simply thrown away, they are considered disposable and therefore are usually lower in cost.
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
<a name="#packets"> Deodorants and Dispensers:  Deodorant packets<br></a></span><span class="text">
Deodorant packets do not require a dispenser and usually last about a week.  These are good for tough to deodorize areas such as diaper pails, under sinks and out of the way areas.  To use, simply activate the packet, remove the backing of the tape strip and apply to a 'tape safe' surface.
</p>

<br><br>
</td></tr>
</table>
</td></tr>

</div></td>
</tr>
          