
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
                <td class="text"><span class="subheaders">Vacuums & Accessories</span>

<p>
What would the world be without vacuums?  Good daily maintenance of a carpet with a good quality vacuum will help determine the life of the carpet as well as the appearance.  When choosing the best vacuum for your facility, make sure to review the accessories and warranties available on the machine.    
</p>

<p>
When selecting an upright vacuum, consider the following:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#upright">Upright vacuums</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#canister">Canister vacuums</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#back">Back-pack vacuums</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#size">Size of the area to be cleaned</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#tools">Tools and Accessories</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#2">2-Motor vacuums</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#filtration">Filtration and HEPA filters</a></span></li>
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#upright"><b>Vacuums & Accessories:  Upright vacuums</b></a></span><br><br><span class="text"> 
For cleaning carpets, the most popular machine of choice continues to be the upright vacuum.  Uprights use a combination of brush agitation and suction to effectively remove dry soils from carpets.  Upright vacuums are also available in wide sizes, which are very convenient when cleaning large carpeted areas!  These types of vacuums move back and forth with ease and do not require pulling along a canister.  However, because they are more compact they tend to not have as much suction as canister type vacuums
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
<a name="#canister"><b>Vacuums & Accessories:  Canister vacuums</b></a></span><br><br><span class="text"> 
Canister vacuums are usually more powerful than upright vacuums and have better suction so they clean faster.  The downside is that the canister has to be dragged behind the operator.  When working with a canister, the job will be easier if the canister is kept behind the operator where the operator can vacuum a circular area around them and then move the canister.  Canister vacuums also have the ability to reach smaller nooks and crannies that the uprights may not be able to reach.
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
<a name="#back"><b>Vacuums & Accessories:  Back-pack vacuums</b></a></span><br><br><span class="text"> 
Back-pack vacuums are ideal for cleaning tight spaces.  Think of the ease of vacuuming stairways!  Two things to consider before making the purchase is that back-pack vacuums can get heavy for an operator who spends many hours vacuuming.  In addition, when vacuums are used for a long period of time, they get hot.  Definitely take this into consideration since the back-packs rest on the operator's back.  Some back- pack vacuums take measures to reduce the amount of heat released with spacers.
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
<a name="#size"><b>Vacuums & Accessories:  Size of the area to be cleaned</b></a></span><br><br><span class="text"> 
Upright vacuums are available in a wide variety of sizes, measured by their cleaning paths.  12"-14" models are usually used for offices, households and hotel guestrooms where maneuverability around desks, beds and furniture is necessary.  15"-18" models are best for larger offices and hallways, where a greater efficiency is needed (up to 4000 sq. ft./hour).  Wide-area vacuums have cleaning paths between 20" and 30", and are used for large areas, such as banquet rooms, convention halls and long corridors (up to 8000 sq ft./hour).
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
<a name="#tools"><b>Vacuums & Accessories:  Tools and Accessories</b></a></span><br><br><span class="text"> 
Many vacuums are sold complete with on-board tools for overhead and detail cleaning, while other models offer upright vac tool kits that can be purchased separately.  Some examples of these tools are extension wands, crevice tools, dusting brushes, and upholstery tools.
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
<a name="#2"><b>Vacuums & Accessories:  2-Motor vacuums</b></a></span><br><br><span class="text"> 
Premium 2-motor upright vacuums use separate motors to drive the brush and vacuum, maximizing the action of each.  These machines are available with a variety of features; including enclosed vacuum belts, automatic shut-off protection, and top fill bags.
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
<a name="#filtration"><b>Vacuums & Accessories:  Filtration and HEPA filters</b></a></span><br><br><span class="text"> 
Traditional upright vacuums use paper filter bags inside of cloth bags to collect the dust and dirt picked up by the machine.  Newer style, multi-stage filter vacuums use multiple filters in addition to paper filter bags to contain dust inside the machine.  These machines are the best choice where indoor air quality is a concern.
</p>

<p>
HEPA filters are High Efficiency Particulate Arresting filters that are 99.97% efficient on particles of 0.3 microns in size.  HEPA filters should be considered where allergies are an issue or air particle counts are a concern, like in cleanrooms.  However, HEPA filters tend to reduce the efficiency of the vacuum and are an additional cost.  So, if your facility does not require a HEPA filter, you may want to pass on this feature.
</p>


<br><br>
</td></tr>
</table>

          