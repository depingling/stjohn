
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
                <span class="subheaders">Bowl Cleaners</span><br>
                <span class="text">Bowl cleaners are available in both acid and non-acid formulations.   These products are specifically designed to tackle one of the toughest cleaning jobs in the bathroom.  Consider the following when choosing the bowl cleaner for your washroom cleaning system:
</span>

<ul><li class="selectorbullet"><a href="#bonnet"><span class="text">Non-acid vs. acid</span></a></li>
<li class="selectorbullet"><a href="#dry"><span class="text">Hydrochloric acid vs. phosphoric acid</span></a></li>
<li class="selectorbullet"><a href="#san"><span class="text">% of acid</span></a></li>
<li class="selectorbullet"><a href="#clean"><span class="text">Disinfecting</span></a></li>
<li class="selectorbullet"><a href="#rinse"><span class="text">Packaging</span></a></li>
</ul>
	

<HR class="selectorrulecolor"></HR>
<p><ul><li class="selectorbullet"><span class="subheaders">
<a name="bonnet">Bowl Cleaners:  Non-acid vs. acid </span></a><br>
Acid bowl cleaners are used to remove heavy build up such as hard water deposits, uric acid, and rust from inside the toilet bowl or urinal.  Because of the nature of the product, acid bowl cleaners should only be used inside the bowl or urinal.  Using an acid bowl cleaner to clean surfaces outside the bowl or urinal can result in damage to washroom surfaces.  Non-acid bowl cleaners can be used as a daily cleaner or for areas where hard water is not a concern and the traffic flow is low.
</p>
<p>
When putting a washroom program together, think of a system approach.  Generally non-acid products are used for daily cleaning and the acid products are used perhaps once a week if required for the tough soil loads.  If you feel you need the strongest acid product to clean your bowls, it may be helpful to use the strong product to restore the bowl first.  Then start your program of using a non-acid product for daily cleaning and a lower level acid product for the weekly cleaning. 
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
<a name="dry">Bowl Cleaners:  Hydrochloric acid vs. phosphoric acid</span></a><br>
Acid bowl cleaners are available in a variety of formulas.  Acid bowl cleaners are usually made with either phosphoric acid or hydrochloric acid.  Phosphoric acid is characterized by a slightly stale odor.  This acid is safe for a wide range of surfaces but is not quite as strong as the hydrochloric acid.  Also note that phosphoric acid cannot be sold in some states because it is considered a phosphate.  Some studies show that phosphates in large quantities deplete the oxygen in waterways, which in turn can harm aquatic life. 
</p>
<p>
Hydrochloric acid is the most common acid used in bowl cleaners and it is stronger than the phosphoric acid.  However, hydrochloric acid can be damaging to many surfaces so it should only be used in the toilet bowl.  Always make sure to wear the proper personal protective equipment when handling these products!
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
<a name="san">Bowl Cleaners:  % of acid
 </span></a><br>
Typically, acid bowl cleaners will contain a specified percentage of acid usually ranging from 9.5% to 25 %.  This information is readily available on the product label and in product descriptions.  Acid bowl cleaners' containing 9.5% hydrochloric acid tends to be the standard.  Products with higher acid content should be limited to jobs that require extra cleaning power such as heavy build up and deposits.  
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
<a name="clean">Bowl Cleaners:  Disinfecting </span></a><br>
Some bowl cleaners are also disinfectants.  Typically the disinfectant kill claims on bowl cleaners are very limited.  In addition, keep in mind that not many people come in contact with the inside of the bowl so the need for disinfecting this area is generally based on a particular need such as long term nursing care or odor control.  However, some facilities still require that disinfectant bowl cleaner be used so they are available if needed.
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
<a name="rinse">Bowl Cleaners:  Packaging</span></a><br>
ECheck to see how a selected product is packaged: quarts, gallons, 5's or through a dilution control unit.  Typically quarts packages contain ready-to-use products and may be best suited for use in smaller facilities.  Gallons and 5's will provide more economy but may require some dilution prior to use.  Dilution control programs allow you to mix ready-to-use product from concentrates and will provide a good selection of products to choose from as well.  Remember that you will need the correct dilution control equipment to dilute the products and a water source.  
</p>
<p>
If you are considering a dispensing program or dilution control for your chemicals, you will find non-acid bowl cleaners readily available through the dispensing systems.  However, acid bowl cleaners are usually a ready to use product packed in quart sizes, which are poured directly into the bowl or onto a bowl mop.
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

<br><br>
</td></tr>
</table>

