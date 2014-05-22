
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
                <span class="subheaders">Washroom Surface Cleaners</span><br>
                <p>
Washrooms often present very stubborn cleaning jobs like cleaning hard water deposits, soap scum, dried hairspray, etc.  To top it off, the washroom is one of the smallest areas of the facility yet it is visited more frequently than even the lobby!  You may have noticed that a great deal of the complaints in a facility are washroom related.  So it is imperative that it is clean and odor free.  When choosing the surface cleaning products for a washroom system consider the following:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#hard">Hard water deposits vs. soap scum</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#acid">Acid vs. non-acid cleaners</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#creme">Crème cleansers</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#fix">Fixtures</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#types">Types of surfaces</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#floor">Floor Cleaners</a></span></li>
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#hard"><b>Washroom Surface Cleaners:  Hard water deposits vs. soap scum</b></a></span><br><br><span class="text">
Knowing if you have hard water issues will help you in choosing the best cleaners for your washroom system.  Hard water deposits typically appear as a white film.  You will need an acidic product to get rid of hard water deposits.  It is very common to use an acidic product once a week to get rid of the hard water deposits and use a non-acid cleaner the rest of the week.  
</p>

<p>
If soap scum is your washroom's dirt of choice then opt for a non-acid cleaner.  These are typically alkaline in nature and will tackle the difficult soap scum jobs.  Beware though, you may find that you have hard water deposits on top of your soap scum!  If you find that the soap scum just isn't coming clean, use an acidic product.  The abrasion from cleaning away the hard water deposits may be enough to remove the soap scum.  If not, follow up with your non-acid washroom cleaner.
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
<a name="#acid"><b>Washroom Surface Cleaners:  Acid vs. non-acid cleaners</b></a></span><br><br><span class="text">
When putting a washroom program together, think of a system approach.  Generally non-acid products are used for daily cleaning and the acid products are used perhaps once a week if required for the tough soil loads.  If you have a particularly tough job, you may choose to use a strong acidic cleaner to restore the surface.  Then start your program of using a non-acid product for daily cleaning and use the acidic product once a week, once a month, or whatever your maintenance schedule requires.  A word of caution here that acidic bowl cleaners, especially those with high percentages of hydrochloric acid should be used only inside the bowl.  Using an acid bowl cleaner for other areas of the washroom will result in damages to many of the surfaces.
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
<a name="#creme"><b>Washroom Surface Cleaners:  Crème cleansers</b></a></span><br><br><span class="text">
Crème cleansers are usually applied with a sponge, allowed to dwell, and then the surface is agitated with a sponge or brush, and then rinsed away.  These cleaners work great on soap scum and soft water deposits (those ugly blue-green stains).  Crème cleansers are ready to use and are not economical to use on all surfaces but they are a terrific problem solver for tough areas.  If your facility has a heavy soil load inside the toilet bowl and prefer not to use an acid bowl cleaner, a crème cleanser can be an alternative.  To use in the bowl, lower the water level in the bowl, apply the cleanser to the bowl mop, apply, scrub and flush. 
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
<a name="#fix"><b>Washroom Surface Cleaners:  Fixtures</b></a></span><br><br><span class="text">
Don't forget about those fixtures!  Most washroom products, both acid and non-acid, that are on the market are safe to use on the washroom fixtures but there may be a few exceptions, such as an acid based bowl cleaner.  Check out the label to be safe.  Typically you can use whatever product used to clean the vanity, tub, sink, or shower to also clean all of the fixtures.
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
<a name="#types"><b>Washroom Surface Cleaners:  Types of surfaces</b></a></span><br><br><span class="text">
Before choosing your washroom cleaner make sure to know what kind of surface you will be cleaning.  Pay particular attention to any marble surfaces, as acidic products will damage marble.  Most products will state on the label if they should not be used on certain surfaces.  Keep in mind that a product that is non-acid is not necessarily a neutral product but in fact is typically alkaline.  So non-acid does not automatically mean that it is safe for all surfaces.   
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
<a name="#floor"><b>Washroom Surface Cleaners:  Floor Cleaners</b></a></span><br><br><span class="text">
When you enter a washroom one of the first things you notice is the appearance of the floor. Keeping the floor looking clean requires the use of a good quality cleaner.  Disinfectants, neutral floor cleaners and general-purpose cleaners can all be used to clean the floor.  If the bathroom is susceptible to heavy soils, consider using a stronger general purpose cleaner periodically.  Some floor cleaners function as a general restroom cleaner and can be used to deodorize and clean all surfaces in the washroom saving you from buying multiple chemicals for different areas of the washroom.  Also, some washroom floor cleaners contain enzymes, which will help to get rid of those hard to remove bathroom odors.
</p>

<br><br>
</td></tr>
</table>

          