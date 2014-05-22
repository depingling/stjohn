
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
                <span class="subheaders">Specialty Items</span><br>
                
<p>
There are a few products that can be categorized as specialty items in the washroom line of cleaning solutions.  These products aren't mandatory in the collection of chemicals for the washroom, but are good troubleshooting products to have on hand in the event the standard line of defense does not do the trick.
<ul>
  <li class="selectorbullet"><span class="text"><a href="#tile">Tile and grout rejuvenators</a></span></li>
  <li class="selectorbullet"><span class="text"><a href="#grout">Grout sealers</a></span></li>
</ul> 
</p>

<HR class="selectorrulecolor"></HR><p><ul><li class="selectorbullet"><span class="subheaders">
<a name="#tile"><b>Specialty Items:  Tile and grout rejuvenators</b></a></span><br><br><span class="text">
If your grout is discolored with mold and mildew and stains, and you have tried unsuccessfully to scrub away tough soap scum, it's time for a tile and grout rejuvinator.  Tile and grout rejuvenators will attack tough soil loads but are aggressive products to work with, so reserve them for the tough jobs and the heavy cleaning.  Some of these products will be bleach-based so be sure to use the products in well-ventilated areas and use the appropriate protective equipment.  To avoid unexpected results, be careful not to mix them with any other chemicals such as a disinfectant.  To use, simply spray onto the surface of the tile and grout.  Allow to dwell for a few minutes, agitate, and then rinse well.  
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
<a name="#grout"><b>Specialty Items:  Grout sealers</b></a></span><br><br><span class="text">
Before using a grout sealer, be certain the area is thoroughly cleaned.  Sealers instantly make a dramatic change in the appearance of the grout.  They can be great troubleshooters and will help prevent the grouted areas from quickly resoiling and trapping dirt.  Make sure that the tiles and the grout are completely clean and dry before applying the sealer; otherwise the sealer will trap in the soil.  When choosing a grout sealer, select a color that will most closely match the grout.  Note that wall grout and floor grout can often be different colors in the same room.  Since the mission of the grout sealer is to fill in the pores of the grout, be certain to use a sealer only on glazed tiles and not unglazed tiles.  Otherwise the sealer will attach itself to the face of the tile and the tile will become the color of the grout.  
</p>

<p>
Because grout sealers reduce the porosity of the grout, they will help prevent soils from attaching to the surface of the grout.  However, sealers are not permanent and will need to be reapplied periodically as wear and soil begin to attack them.  Look for products that are easy to work with, preferably those with soap and water cleanup.  Before applying grout sealer, plan to allow the work area to remain dry 3-6 hours or more.  This time is important to allow the sealer to dry properly.
</p>




<br><br>
</td></tr>
</table>

 </td></tr>

</div></td>
</tr>         