
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
			    <td class="text"><span class="subheaders">Shower Room Cleaners</span>


<p>
Shower room cleaners are available in both non-acid and acid based formulations and can be applied by various methods from foaming trigger sprayers and shower room guns to mobile shower room cleaners. Shower room cleaners are often good interim cleaning products to use in other areas of the restroom as well where the soil load is high, e.g. inside the bowl or urinal, sinks, and counter tops. 
</p>
When choosing a shower room product there a couple of points to consider:
<ul>
  <li class="selectorbullet"><span class="text"><a href="#method">Method of dispensing</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#disinfect">Disinfectants</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#acid">Acid vs. non-acid</a></span></li>
</ul> 
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#method"><b>Shower Room Cleaners:  Method of dispensing</b></a></span><br><br><span class="text">
There are a couple different ways to dispense cleaner.  For cleaning a smaller facility, a quart bottle with ready-to-use product and a foaming trigger sprayer will be sufficient.  For shower rooms or large areas, consider shower room guns or mobile shower room units.
</p>

<p>
Guns allow you to pour concentrated cleaner from gallons or 5-gallon pails into a reservoir.  The gun connects to a garden hose and cleaner becomes automatically diluted when the water is turned on.  The guns will dispense the product in a foaming spray to help the product cling to the wall for better cleaning.  These units also contain a rinse setting. 
</p>

<p>
The mobile shower room units contain cartridges of concentrated chemical that dispense ready to use cleaners and/or disinfectants from a through portable dilution systems using equipment similar to garden hose trigger sprayers connected to a water supply.  These units may be cart mounted making it easy to move the equipment and supplies from site to site.
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
<a name="#disinfect"><b>Shower Room Cleaners:  Disinfectants</b></a></span><br><br><span class="text">
Some shower room cleaners are disinfectants.  If you are looking for a disinfectant shower room cleaner you may want to take a close look at what kill claims the disinfectant has.  One in particular that you may want to look for is Trichophyton mentagrophytes (athlete's foot).  If the soil load requires a strong cleaner for the shower room and the disinfectant isn't cutting it, consider using one of the stronger shower room cleaners for cutting through the soap scum and hard water deposits first.  Then, if the facility requires the use of a disinfectant, go back over the area with the disinfectant.
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
<a name="#acid"><b>Shower Room Cleaners:  Acid vs. non-acid</b></a></span><br><br><span class="text">
For heavy soap scum, mold, and rust stains, consider the use of acid-based shower cleaners. Exercise caution when cleaning with these powerful chemicals.  Make sure your employees understand the proper handling and safety procedures associated with these cleaners. Remember, products containing acid should not be used on marble since they will etch the stone.
</p>

<p> 
Non-acid shower cleaners are designed for daily and interim cleaning of the shower area. These products are designed to address less severe build up, and act as preventative measure against soap scum.  If you are concerned about having your workers clean with an acid, consider a non-acid cleaner for use throughout the washroom on a daily basis.  To keep heavy build up in check, an acid cleaner could be used once a week.  
</p>



<br><br>
</td></tr>
</table>

          