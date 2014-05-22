
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>




<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<% 
String query=request.getQueryString();
String currUri=SessionTool.getActualRequestedURI(request) +"?"; 
if(query!=null){
    currUri+=query+"&";
}
currUri = SessionTool.removeRequestParameter(currUri,"section");
%>
<bean:define id="section" value="glossary.jsp" type="java.lang.String" toScope="session"/>




      <table align="center" border="0" cellpadding="15" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
        <tr>
          
          <td>
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Bacilli</div></td>
</tr>
<tr>
  <td class="text">Disease causing rod shaped bacteria that produces spores.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Backing</div></td>
</tr>
<tr>
  <td class="text">Backing generally refers to the materials on the back of a carpet that hold the carpet fibers together and makes the carpet stronger.  Backing material can be but is not limited to wool, cotton, jute, and polypropylene or olefin.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Bacteria</div></td>
</tr>
<tr>
  <td class="text">A division of single cell microorganisms that include bacilli (rod-shaped) spherical (cocci), and spiral (spirilla).  Bacteria do not contain chlorophyll so they do not need sunlight to survive.  Bacteria thrive in dark, warm, moist environments.  Not all bacteria are disease forming.  The disease causing bacteria are also known as germs.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Bactericide</div></td>
</tr>
<tr>
  <td class="text">A substance that kills bacteria.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Bacteriostat</div></td>
</tr>
<tr>
  <td class="text">A substance that prevents the growth of bacteria but does not kill the bacteria.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Beater Bar</div></td>
</tr>
<tr>
  <td class="text">A rigid bar on a vacuum cleaner brush that agitates and loosens soil from the carpet.  Machines with beater bars are often not recommended for use on tile and wood floors.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Berber</div></td>
</tr>
<tr>
  <td class="text">A type of carpet that has a level loop, thick yarn.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Binder bar</div></td>
</tr>
<tr>
  <td class="text">A strip installed over a carpet edge to prevent unraveling.  Usually metal or vinyl.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Biodegradable</div></td>
</tr>
<tr>
  <td class="text">Capable of being readily decomposed by microbial action.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Black Heel Marks</div></td>
</tr>
<tr>
  <td class="text">Black marks on floor finish caused from the rubber soles of shoes.  Can usually be buffed or burnished out.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Bleed</div></td>
</tr>
<tr>
  <td class="text">Refers to the term used when color is removed from or is lifted and runs.  Bleeding can occur on both carpet and floor tiles like asphalt and rubber.  High alkaline products and hot water can increase bleeding.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Blush</div></td>
</tr>
<tr>
  <td class="text">Dull floor finish.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Browning</div></td>
</tr>
<tr>
  <td class="text">A discoloration of carpet fibers that can occasionally occur after cleaning with high alkaline type products or by over wetting the carpet while cleaning.  Browning is more common in carpets with cotton or jute backing.  Acidic carpet products (known as tannin treatments or brown out products) will remove the browning.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Buffer</div></td>
</tr>
<tr>
  <td class="text">Low speed floor machine that swings from side to side.  Buffers are also referred to as "swing machines."   They are used to bring gloss back to  finish and to strip finishes.  They job they do depends on the pads used on the machine and the chemical products used on the floor in conjunction with the machine.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Buffing</div></td>
</tr>
<tr>
  <td class="text">Polishing or restoring the gloss to a floor finish with a low speed floor machine that swings from side to side.  Buffing can be time consuming due to the need to make several passes with the swing machine.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Builder</div></td>
</tr>
<tr>
  <td class="text">A chemical that increases the cleaning efficiency of cleaners and degreasers.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Build-up</div></td>
</tr>
<tr>
  <td class="text">Floor Finish or dirt that has accumulated on a floor over time.  Build-up floor finish can be very difficult to remove and has often turned yellow due to dirt being ground into it.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Burnish</div></td>
</tr>
<tr>
  <td class="text">Restore the gloss to a floor finish with a high speed floor machine and the appropriate pad.  The burnishing process can sometimes include the use of a restorer, spray buff.  Burnishing can also be done dry or without the use of any chemicals.  Burnishing is less time consuming than buffing due to the use of a high speed machine which requires less passes.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Burnisher</div></td>
</tr>
<tr>
  <td class="text">A high speed floor machine that is 1500 rpms and up.  Brunishers can be either battery powered, electric, or propane.  Burnishers are used to restore the gloss to floor finishes.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Butyl</div></td>
</tr>
<tr>
  <td class="text">A water soluble solvent used in degreasers and other cleaners.</td>
</tr>

            </table>
</td></tr></table>
