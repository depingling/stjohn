
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
			    <td class="text"><span class="subheaders">Floor Mats</span>

<p>
Floor mats or matting is one of the best-kept keys to success in the business.  In fact, a properly developed entrance matting program can actually trap 80% of the dirt at the door.   Entrance matting is your first line of defense in keeping dirt, moisture and debris outside your facility where it belongs.  Work and wet area matting provides a safer work environment for employees reducing the risk of slip and fall accidents while promoting higher productivity.  To reap the best advantage from a matting program, position matting in strategic areas in and around your building.  Consider the following when selecting the mats for your facility:

<ul>
  <li class="selectorbullet"><span class="text"><a href="#length">Length</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#outdoor">Outdoor </a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#indoor">Indoor </a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#scraper">Scraper vs. carpet </a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#backed">Backed vs. non-backed </a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#material">Material – vinyl, olefin, and nylon</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#anti">Anti-fatigue</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#slip">Slip resistant </a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#wet">Wet area </a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#logos">Logos</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#special">Special widths</a></span></li>  
</ul>	

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#length"><b>Floor Mats:  Length</b></a></span><br><br><span class="text"> 
To be effective, matting needs to properly cover a particular area for a specific task.  For example, for entrance matting to be effective, a minimum of 20 feet of walk off matting should be installed at all entrances, especially the key entrances.  However, 30 feet of walk off matting will offer the most protection.  Try to allow for 15’ of outside matting, 5’ of foyer matting, and 10’ of inside matting.  If this much matting is not realistic then keep in mind that the first thing a person walks on should be a vinyl type mat to scrape away dirt and then a carpet type mat to absorb any water.  
</p>

<p>
Also, consider the climate.  In an area where there is a lot of rain and snow use more carpet matting to help dry the bottoms of feet more effectively.  For a program in this climate, consider installing 10’ of scraper type matting and then 20’ of carpet matting so less water will be tracked onto the floor.  In a very dry climate?  Then consider using 20’ of scraper matting and 10’ of carpet matting.  Just remember that more matting means less dirt and abuse on the floors.  And every little bit helps.  Some research has shown that that even 6’ of matting will pay for itself in one week’s time.  
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
<a name="#outdoor"><b>Floor Mats:  Outdoor</b></a></span><br><br><span class="text"> 
When choosing the right mat for outside the entrance doors to your facility, look for a non-carpeted, non-backed, scraper type of mat.  Outdoor mats are specifically manufactured to withstand the wrath of Mother Nature.  In addition, they are constructed to keep water and debris away from the surface of the mat to minimize either from being tracked into the facility. Be certain to choose a mat specified for outdoor use.  If carpet top mats are used outdoors, they tend to stay wet when exposed to the weather and in turn will inevitably grow mold and mildew.  Also, try to go with a non-backed, open weave mat that will allow the dirt and water to fall through the mat so the surface of the mat stays drier and clear of debris longer.  This will save on labor to clean the mat.
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
<a name="#indoor"><b>Floor Mats:  Indoor </b></a></span><br><br><span class="text">
With indoor matting there are many more options on what type of mat to go with.  If it’s important to protect the floor under the mat then stick with a backed mat.  Carpet type mats work great indoors because they will absorb any water on the soles of feet in addition to adding a little more scrapping action.  In fact, a carpet type mat, when properly maintained, can help minimize slip and fall accidents as walkers move from the mat to a hard surface with wet shoes.  Remember to change mats as they become too wet to properly absorb moisture.  It may be helpful to keep a set of backup mats on hand for use when primary mats become too wet or require cleaning.
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
<a name="#scraper"><b>Floor Mats:  Scraper vs. carpet </b></a></span><br><br><span class="text">
Scraper matting does just what the name implies. It is designed to scrape off debris and moisture from the bottom of footwear. Scraper matting can be placed outside before an entrance or in foyers with recessed wells. The scraper mat should be the first mat the walker uses when entering a building from the outside.  If you are in an area that has a lot of sand or snow and ice, scraper mats are an essential part of the floor maintenance program. Using scrapers for outside use with no backing will allow debris to fall through the mat, keeping shoes dry and making cleanup easier. Conversely, indoor matting should have a backing to trap the dirt and keep moisture from the floor.  Just like conventional carpet, this type of matting is available in different colors to match the décor and varying thickness for different levels of traffic.  
</p>

<p>
To keep floors looking their best in addition to creating safer common areas, carpet matting is the transition between more aggressive scraper mats and the bare floor. Carpet matting will typically provide better absorbency than the scraper mats and will help to also trap dirt and other debris. Look for carpets that have vinyl backing. The backing will prevent sand, mildew, and moisture from penetrating below the carpet fibers to floor where it can cause the floor to be stained or damaged or even worse, a dangerous and slippery situation.
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
<a name="#backed"><b>Floor Mats:  Backed vs. non-backed </b></a></span><br><br><span class="text">
When deciding on a mat, choices include backed and non-backed matting.  There are pros and cons to both types.  Backed matting will trap all of the dirt, debris, and liquid in the mat.  The mat can then be vacuumed or rolled up and shaken or dried outside.  Non-backed matting allows the dirt to fall through to the floor.  The mat itself will help prevent the dirt from being carried elsewhere onto the floor.  These types of mats will not need to be shaken, but you will need to sweep the surface underneath.
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
<a name="#material"><b>Floor Mats:  Material </b></a></span><br><br><span class="text">
Matting is available in various materials but the most common is vinyl, olefin, nylon, and rubber.   Here is a little bit of information on the different materials
<ul>
  <li class="selectorbullet"><span class="text">Olefin is a popular fiber for mats because it is less expensive.  Olefin is also used to help enhance the scraping/ soil removal action in carpet mats.</span></li>
  <li class="selectorbullet"><span class="text">Nylon is about double the cost of olefin and has better crush resistance and abrasion resistance than olefin. </span></li>
  <li class="selectorbullet"><span class="text">Vinyl is a great fiber to look for in outdoor scraper matting because it has a heavier filament for better scraper and dirt removal.  In either indoor or outdoor use, vinyl has good memory which means it will spring back into shape.  </span></li>
  <li class="selectorbullet"><span class="text">Rubber backing may leave a black spot on the floor.  So, rubber in the backing at least, should be avoided if possible.  Instead look for a vinyl-backed mat. </span></li>
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

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#anti"><b>Floor Mats:  Anti-fatigue</b></a></span><br><br><span class="text">
Workers will definitely appreciate anti-fatigue matting.  Employers will appreciate the increased productivity.  Most of these are foam mats designed to provide cushion for the legs and feet.  Ideally the mat should have closed-cell foam to prevent the mat from collapsing.  Look for a manufacturer that warranties the mat for defects and performance.  Chances are if there is no warranty, the mat will collapse quickly.  
</p>

<p>
Assembly plants, especially, may be interested in the grease and oil resistant anti-fatigue matting.  This type of anti-fatigue mat is made with vinyl to withstand the grease and oil present in those types of facilities.
</p>

<p>
Non-backed anti-fatigue matting is available for those areas where water is a concern so that the water will run through the mat.  Also, use non-backed anti-fatigue matting if it is desired that the dirt or debris flow through the mat.  This type of mat may be helpful in heavy soil load areas where it is difficult to frequently stop to clean the mat surface.  However, if you are using a very long mat, it may be wise to choose a mat with backing that will prevent anything from falling through the mat.  The very long mats can be a hassle to remove and clean up under.
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
<a name="#slip"><b>Floor Mats:  Slip resistant</b></a></span><br><br><span class="text"> 
These types of mats are ideal for greasy kitchens and any other place where there is a higher chance of slip and falls.  Slip resistant matting is like laminated sandpaper.  Portable slip resistant mats are ideal for their ease in cleaning.  The cost of these mats far outweighs possible consequences of slippery areas.  These mats however are not a substitute for standard safety precautions including wet floor signs!
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
<a name="#wet"><b>Floor Mats:  Wet area</b></a></span><br><br><span class="text"> 
When choosing a wet area mat, make sure that the mat has some type of antimicrobial agent that will resist the growth of mold and mildew.  Wet area mats allow water to flow through to keep water on the surface at a minimum.  These types of mats are designed for use in barefoot traffic areas like pools, shower rooms, etc.  These mats however, are not a substitute for standard safety precautions including wet floor signs.
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
</table></p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#logoa"><b>Floor Mats:  Logos</b></a></span><br><br><span class="text">
Many matting manufacturers have the ability to apply just about any logo to a mat.  There is usually a choice between having the logo embossed into the mat, or having the logo simply appear in different color fibers.
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
<a name="#special"><b>Floor Mats:  Special widths</b></a></span><br><br><span class="text">
Not all things are created equal.  This is why many matting manufacturers will allow special orders for different width matting.  Matting can cover as big an area as required by connecting several lengths of mat with special adhesive tapes designed especially for this use.  To select a mat with a special width, choose the correct mat based on the location of the mat first, determine if a custom width/cut is available and be certain the mat has a blunt edge that can be taped from the backing side.
</p>


</td></tr>
</table>

          