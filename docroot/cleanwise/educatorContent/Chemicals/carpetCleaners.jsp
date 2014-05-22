
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
		<td class="text"><div class="subheaders">Carpet Products</div>

<p>
Most carpets ugly out before they wear out.  Practicing good maintenance and using quality products will increase the life of the carpet.  Remember to always test for color fastness in an out of the way area before applying any product to a carpet.  Carpets can vary from room to room in a facility so this test should be done in each room.  This will ensure that the product will not cause your carpet to bleed its color.  Also, always be certain to measure chemicals if a dilution control system is not being used.  Over diluted chemicals can cause premature re-soiling of carpets.  Re-soiling results in extra wear to the carpet fiber and more labor to re-clean traffic lanes and soiled areas.  If wool carpets are in the facility then make sure to look for the Woolsafe logo on all carpet products used on the wool carpets.
<br>
For more information, select a topic from the list:  
<ul>
  <li class="selectorbullet"><span class="text"><a href="#bonnet">Bonnet/ Spin Cleaners</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#dry">Dry Carpet Cleaning Products</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#sanitizers">Sanitizers/ Disinfectants</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#cleaners">Extraction Cleaners </a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#rinse">Extraction Rinse</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#post">Post Treatments</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#pre">Pre-Sprays</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#shampoo">Shampoos</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#spec">Specialty Products</a></span></li>
    <ul>
      <li class="selectorbullet"><span class="text"><a href="#defoam">Defoamers</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#liquid">Liquid deodorants, powder deodorants, and mal odor counteractants</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#color">Color boosters/ protectors</a></span></li>
    </ul>
  <li class="selectorbullet"><span class="text"><a href="#spot">Spotters</a></span></li>
    <ul>
      <li class="selectorbullet"><span class="text"><a href="#general">General-purpose spotters</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#protein">Protein or enzyme-based spotters</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#tannin">Tannin or brown spot removers</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#paint">Paint, oil and grease removers</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#iodine">Iodine spotters</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#gum">Gum removers</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#red">Red juice spotters</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#delible">Delible ink remover</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#rust">Rust removers</a></span></li>
      <li class="selectorbullet"><span class="text"><a href="#alkaline">Alkaline conditioners</a></span></li>
    </ul>
</ul>
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#bonnet"><b>Carpet Products:  Bonnet/ Spin Cleaners</b></a></span><br><br><span class="text">
Bonnet / Spin Bonnet cleaning is used to clean carpet for several reasons.  Primarily bonnet cleaning is used as an interim maintenance cleaning method.  It’s’ mission is to remove moderate soils and restore a uniform appearance to the carpet.  Bonnet cleaning is also chosen because it offers a quick way to clean carpet.  The carpet dries quickly following the procedure.  So it allows the maintenance staff to clean a floor and open it up to traffic again rather quickly.  
</p>

<p>
When selecting a bonnet cleaner look for a product that has a fast drying formula so the carpet will dry more quickly.  Some formulas clean both oil and water-based soils.  If you use a dilution control system for mixing the chemicals, be on the look out for a bonnet cleaner that is available through the dilution control program, it will help ensure the cleaner is mixed properly.  This can be a big advantage since often times carpet care products are over mixed resulting in a sticky residue that increases resoiling.  
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
<a name="#dry"><b>Carpet Products:  Dry Carpet Cleaning Products</b></a></span><br><br><span class="text">
Dry carpet cleaning products are used as interim cleaners to clean and deodorize carpeted areas.  These products are typically treated with solvent cleaners and deodorants and have a slightly moist feel to the touch.  The beauty of a dry cleaning system is that it doesn’t require a lot of down time while the carpet dries.  Since the area can be freshened up quickly, dry cleaners are well suited to jobs requiring quick turn around.  A good candidate for dry carpet cleaning would be a conference room or office requiring a last minute cleaning before a meeting.  Guest rooms in hotels are another example.
</p>

<p>
Some dry cleaning systems require special equipment so check the product information for details.  Remember that dry cleaning systems don’t replace programs such as extraction cleaning.  But they can remove light soil loads, freshen the carpet and allow foot traffic onto the floor immediately following the treatment.  When looking for a dry, carpet-cleaning product, determine what equipment is required by reading the product description.  Remember, some require specialized equipment others simply require a vacuum and a carpet rake.  Look for any special claims for use such as safe for use on wool.
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
<a name="#sanitizers"><b>Carpet Products:  Sanitizers / Disinfectants</b></a></span><br><br><span class="text">
To date there are no Environmental Protection Agency (EPA) approved carpet disinfectants on the market.  Disinfectant cleaners today are for use on environmental surfaces, or hard surfaces, but do not carry any disinfectant kill claims when used on carpets.  This is because the EPA does not have an approved test method for testing the ability of a disinfectant to kill bacteria, viruses, or fungi on carpet fibers yet.  When a need for decontaminating a carpet arises, choose a carpet sanitizer, which will kill 99.9% of the bugs on the label and the carpet when used as directed.  
</p>

<p>
Are blood spills a concern?  A recommendation sometimes made by the Centers for Disease Control (CDC) where blood spills are a concern, is to clean the carpet with a product that has been approved for cleaning blood spills on hard surfaces.  Be very careful if taking this approach because these types of products have not been tested for damaging effects on carpets and their kill claims have not been proven to be effective on carpets.  If a disinfectant is used to decontaminate a blood spill, immediately extract the carpet, rinse well and dry quickly.
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
<a name="#cleaners"><b>Carpet Products:  Extraction Cleaners </b></a></span><br><br><span class="text">
Extraction is a popular method for cleaning carpets.  In fact, carpet mills prefer extraction when it comes time to clean.  Extracting a carpet leaves less residue than other carpet cleaning options because the whole carpet fiber is cleaned, not just the surface of the carpet.  Extract carpets for optimum soil and residue removal.  
</p>

<p>
When looking for an extraction cleaner, look for low foaming solutions that claim to be low in residue.  Some formulations contain corrosion inhibitors to help protect the extractor from corrosion.  Many of today’s formulations are free of optical brighteners.  Optical brighteners can cause problems with the optical scanners on the automated delivery systems such as mail carts.  If you live in an area with hard water, look for a cleaner that has been formulated to withstand hard water and will not clog the extractor jets on your extractor.  Clogged jets cause uneven cleaning results.  If odor is an ongoing problem, or if sanitizing the carpet is important, look for a carpet sanitizer that can be used in an extractor.  Also odor controlling deodorants or enzyme-based odor counteractants can sometimes be added to extraction cleaners.  However, make sure to never add anything other than water to any carpet sanitizer or any other product unless specifically directed to do so.  
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
<a name="#pre"><b>Carpet Products:  Pre-sprays </b></a></span><br><br><span class="text">
Want to know one of the best-kept secrets to cleaner carpets?  Use a Pre-spray!  Pre-sprays are used to pre-treat the carpet prior to extracting.  A pre-spray is applied to the carpet being cleaned using a pressure sprayer (garden sprayer).  The mission of the pre-spray is to loosen the soil load so it can be easily lifted by the extractor during the agitation and rinse process thereby getting the carpet cleaner with less labor.  A great trade secret!
</p>

<p>
Pre-sprays come in a variety of formulations and packaging sizes to meet any need.  Some pre-sprays are enzyme boosted to assist in removing carpet odors but these may require more dwell time before the carpet is extracted.  Enzyme products should never be used on wool carpets.  However, there are pre-sprays that have been approved for use on wool.  
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
<a name="#rinse"><b>Carpet Products:  Extraction Rinses</b></a></span><br><br><span class="text">
Extraction rinses are designed to clean the carpet, flush out soapy residues, and reset the pH of the carpet back to neutral.  Flushing the carpet of residues helps to minimize resoiling of carpet fibers.  Resoiling results from sticky residues left on carpet fibers that attract other soils from the air and the bottom of shoes and carts.  The result is a carpet that quickly becomes dirty again.  By preventing sticky residues, early resoiling can be prevented.  
</p>

<p>
Extraction rinse products are also helpful in long term nursing areas for urine spills.  The acidic nature of the rinse will help to dissolve urine salts so the carpet can be flushed clean. Extraction rinse products help keep extractor jets free and clear of hard water and chemical deposits that can clog jets and cause uneven cleaning results.
</p>

<p>
Extraction rinses are especially useful for carpets that are loaded up with soapy residue from previous cleanings.  The extraction rinse will help to flush the residue from the carpet and brighten the color.  Good candidates for this troubleshooting procedure are those that find the carpet extractor recovery tank loaded with foam after cleaning.  Note that using extraction rinses alone is a technique for troubleshooting and should not be used as a stand-alone approach to extracting carpets.  When choosing an extraction rinse, look for product with a long dilution such as 1:128 or greater.  Be certain the product is compatible with advanced generation and fibers such as wool or wool containing fibers.  
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
<a name="#post"><b>Carpet Products:  Post Treatments</b></a></span><br><br><span class="text">
Post treatments are used following cleaning of the carpets to help repel soils and spills.  The goal here is to minimize how often the carpets need to be cleaned and to cut labor costs as a result.  Post treatments are specified as needing to be applied to either wet or dry carpets, so check product directions for product use.  To maintain maximum effectiveness, post treatments are best done after each wet cleaning.  
</p>

<p>
When purchasing post treatments, plan on approximately 200 square feet of coverage per ready-to-use gallon of product.  Look for products that help repel both water and oil based stains and that will not change the color of the carpet in any way.
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
<a name="#shampoo"><b>Carpet Products:  Shampoos</b></a></span><br><br><span class="text">
Shampooing is a cleaning method that includes both rotary shampooing and dry foam shampooing.  The advantage to shampooing is that it creates foam that suspends the dirt until it can be cleaned away.  When selecting a shampoo for either application, look for products that do not leave any residues that cause resoiling. 
</p>

<p> 
Rotary shampooing is done using a floor machine outfitted with brush instead of a pad and a solution tank that dispenses shampoo cleaner onto the carpet.  This method is typically used as a step in restorative carpet maintenance since it will deep clean the carpet.  Extraction cleaning often follows rotary cleaning.  
</p>

<p>
Dry Foam shampooing is used today but to a lesser extent.  This method dries more quickly than the rotary shampoo method and is done using a dry foam machine especially designed for this cleaning method.  Cleaner is mixed with air to create dry foam.  As the foam is dispensed onto the carpet, a brush works the foam through the carpet fiber.  The dirt gets suspended in the foam.  Some machines have a vacuum built into the dry foam machine for pick up right away.  If the machine does not have a vacuum, a standard vacuum can be used to pick up the loosen dirt once the carpet dries.
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
<a name="#spec"><b>Carpet Products:  Specialty Products</b></a></span><br><br><span class="text">
Specialty products include items that address special problems with carpet.  Since each of these products has a particular problem to solve, select the products as needed based on the problem they solve.  These include:

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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#defoam"><b>Carpet Specialty Products:  Defoamers</b></a></span><br><br><span class="text">
These products are used in the recovery tank and the intake hose to cut foam before it fills the recovery tank and turns the machine off.  Excess foam is the result of over-mixed carpet cleaners and cleaners that leave lots of residue behind.  Since most folks choose to apply the defoamer to the inside of the recovery hose, choose a product that makes this application safe and easy such as a 32oz squirt bottle.  
</span></li></ul></ul><table width="100%">
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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#liquid"><b>Carpet Specialty Products:  Liquid deodorants, powder deodorants and mal odor counteractants</b></a></span><br><br><span class="text">
These products help to freshen carpet either during the cleaning process or treat carpet between cleanings.  Some products are offered in a selection of fragrances as well.  Powders are meant to be applied and vacuumed up immediately.  Choose these only for use on dry carpet.  Mal odor counteractants with enzymes or bacteria will need to stay wet for a period of time to allow the enzymes or bacteria to work.  So before choosing these products plan to allow the carpet to remain wet without floor traffic.  Deodorants are used in the extractor to address sour, musty or damp odors.  For better economy, look for a product with the ability to work effectively at 1-2 oz per gallon of water.  Often these products have fragrances that match the fragrance of the other carpet cleaners.  This is a nice feature that promotes fragrance matching throughout the carpet program. 
</span></li></ul></ul><table width="100%">
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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#color"><b>Carpet Specialty Products:  Color boosters/ protectors</b></a></span><br><br><span class="text">
Color boosters/ protectors are powders added to carpet cleaning solution to brighten colors and remove yellowing from carpets.  These may help to protect against fading.  Color boosters/protectors can be used in extraction, spin bonnet cleaning, and shampooing.  Look for products that offer better economy in a long dilution such as 1:256.
</span></li></ul></ul><table width="100%">
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
<a name="#spot"><b>Carpet Products:  Spotters</b></a></span><br><br><span class="text">
An important part of a good carpet care program is the daily maintenance program of vacuuming and spotting.  By removing spots as they appear, carpets remain looking good longer.  The best way to attack spots is to put a spotting program together.  Spots that are left too long or are mistreated can become a permanent stain.  Since most carpets ugly out before they wear out, a spotting program can prolong the life of your carpet and prevent early replacement of this expensive asset.  For tips on removing spots, visit How to Clean.
</p>

<p>
There are as many spotters as there are spots.  Choosing spotters that are available in convenient packaging sizes such as ready-to-use quarts will make it easy for workers to quickly clean a spot without mixing or using large containers that apply too much spotter to the carpet.
</p>

</p>
Below is are the types of spotters that are available on the market today along with some things to know:

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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#general"><b>Carpet Spotters:  General purpose spotters</b></a></span><br><br><span class="text">
If you’re not sure what types of spots you will need to remove, or if you want to keep just one or two spotters in the facility, make one a water-based, general purpose spotter.  General-purpose spotters are designed to address a wide variety of spots.  They are typically neutral in their pH.  For spots on wool or wool containing products, look for a product that has the Woolsafe™ logo.
</span></li></ul></ul><table width="100%">
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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#protein"><b>Carpet Spotters:  Protein or enzyme-based spotters</b></a></span><br><br><span class="text">
Protein or enzyme-based spotters are used to remove organically based spots such as urine, vomit and blood.  Enzyme boosted products can provide additional help with organic spots since the enzymes literally digest the spots.  Do not use these on wool carpets.
</span></li></ul></ul><table width="100%">
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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#tannin"><b>Carpet Spotters:  Tannin or brown spot removers</b></a></span><br><br><span class="text">
Tannin or brown spot removers are used to remove water, coffee and tea spots.  In addition, these will work on old carpets with jute backings where the jute color may wick to the surface of the carpet as it dries.  Sometimes tannin spotters can also be helpful to remove red wine spots.
</span></li></ul></ul><table width="100%">
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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#paint"><b>Carpet Spotters:  Paint, oil and grease removers</b></a></span><br><br><span class="text">
To be effective, paint, oil and grease removers are typically solvent-based.  Use these product sparingly on paint, oil, grease, shoe polish, asphalt and tar.  
</span></li></ul></ul><table width="100%">
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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#iodine"><b>Carpet Spotters:  Iodine spotters</b></a></span><br><br><span class="text">
Iodine spotters are terrific problem solvers for carpet and hard floors alike.  These spotters will remove Iodine and Betadine ™ spots quickly from both carpet and flooring without damaging finish.
</span></li></ul></ul><table width="100%">
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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#gum"><b>Carpet Spotters:  Gum removers</b></a></span><br><br><span class="text">
Gum removers remove not only gum but also tar and candle wax from carpet.  The aerosol removers freeze the gum in place so it can be chipped off.  Many aerosol gum removers are very flammable which presents a safety and storage issue.  Look for products that are clearly labeled as non-flammable.  
</span></li></ul></ul><table width="100%">
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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#red"><b>Carpet Spotters:  Red juice spotters</b></a></span><br><br><span class="text">
Red juice spotter address spills such as red wine, fruit juice, and soda.  Some products will offer directions for using the product as a standard spotter and as a hot transfer spotter.
</span></li></ul></ul><table width="100%">
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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#delible"><b>Carpet Spotters:  Delible ink removers </b></a></span><br><br><span class="text">
Like their brother the iodine spotter, a delible ink spotter can be used on carpet as well as hard floors and water safe surfaces to remove spots from non-permanent inks, markers and crayons.
</span></li></ul></ul><table width="100%">
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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#rust"><b>Carpet Spotters:  Rust removers</b></a></span><br><br><span class="text">
As their name implies, rust removers remove rust spots.  Choose a rust remover to remove spots from carpets, ceramic tiles hard floors and any acid safe surface.  Since a rust remover is acid-based, never choose a rust remover for marble.  Most of these products require a follow up treatment with an alkaline conditioner.  The alkaline conditioner will neutralize any residue left from the rust remover.  The alkaline conditioner is a very important step.
</span></li></ul></ul><table width="100%">
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

<HR class="selectorrulecolor"></HR><p><ul><ul><li class="selectorbullet"><span class="subheaders">
<a name="#alkaline"><b>Carpet Spotters:  Alkaline conditioners</b></a></span><br><br><span class="text">
Alkaline conditioners’ main mission in life is to neutralize any residue remaining on a surface that has been cleaned with a rust remover.  If directed by the manufacturer to do so, it’s very important to use an alkaline spotter.  
</p>


<br><br>
</td></tr>
</table>

          