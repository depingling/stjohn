
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
  <td class="trainingsubhead"><div class="navigatemargin">Fading</div></td>
</tr>
<tr>
  <td class="text">A gradual loss of color caused by direct sunlight, various soils, age or cleaning products.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Fastness</div></td>
</tr>
<tr>
  <td class="text">The ability of carpets or other materials to retain color or dyes.  Sometimes referred to as color fastness.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Fatty Acid</div></td>
</tr>
<tr>
  <td class="text">An organic acid substance that is typically found in soil loads containing animal and vegetable fats and oils.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">FDA</div></td>
</tr>
<tr>
  <td class="text">Food and Drug Administration is a government agency that has the responsibility to protect the public health by monitoring and regulating the availability and safety of products.  A common misconception is that the FDA regulates disinfectants but it is in fact the Environmental Protection Agency (EPA) that regulates disinfectants.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Feces</div></td>
</tr>
<tr>
  <td class="text">Waste matter discharged from the intestines.  Disease causing bacteria can be transmitted through feces.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Fibers</div></td>
</tr>
<tr>
  <td class="text">Natural or man-made material that fabric is made from.  Carpet fibers are the materials that make up the carpet surface.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Filler</div></td>
</tr>
<tr>
  <td class="text">Ingredients added to a product, like cleaners, that do not improve the performance of the cleaner.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Film</div></td>
</tr>
<tr>
  <td class="text">A thin covering or coating that can be caused by moisture or hard water deposits for example.  Sometimes floor finishes are referred to as films in the way they coat and protect the floor from traffic and soils.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Flagged</div></td>
</tr>
<tr>
  <td class="text">Refers to bristles on a broom that are flared out.  Flagged bristles are better at picking up fine dirt particles.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Flammability</div></td>
</tr>
<tr>
  <td class="text">The ability of a chemical or material to ignite and burn.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Flash Point</div></td>
</tr>
<tr>
  <td class="text">The lowest temperature at which the vapor from a chemical will ignite.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Flash Rusting</div></td>
</tr>
<tr>
  <td class="text">An immediate layer of corrosion that can appear when bare metal is cleaned.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Flood Coating</div></td>
</tr>
<tr>
  <td class="text">A procedure for applying a finish or a seal to a grouted floor by pouring the product on the floor and then dragging a squeegee across the floor to remove the finish from the floor while leaving finish on the grout lines.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Flood Mopping</div></td>
</tr>
<tr>
  <td class="text">A procedure for cleaning a floor by putting a mop in a cleaning solution and the putting the mop directly on the floor without ringing out the mop.  Sometimes referred to as flooding the floor as when applying stripper, for example.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Flood Rinse</div></td>
</tr>
<tr>
  <td class="text">A procedure for rinsing a floor by dipping a clean mop in clean rinse water and then putting the mop directly on the floor without ringing out the mop.  Flood rinse is typically performed after stripping to remove any stripper residue.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Floor Finish</div></td>
</tr>
<tr>
  <td class="text">A coating applied to a floor that will bring either a glossy or satin appearance to the floor.  Floor finishes are not permanent and need to be maintained regularly and stripped occasionally.  Finishes provide a barrier against wear so the floor will last longer.  They also help to improve the appearance of the floor in addition to making the floor easier to keep clean.  Using special techniques, floor finish can be used to protect the grout on a grouted floor.  See also, Floor Coating.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Floor Machine</div></td>
</tr>
<tr>
  <td class="text">A machine that is used to clean, buff, scrub, or strip a floor or floor finish.   Buffers, swing machines, and burnishers are all referred to as floor machines.  The various pads, speed (rpm) of the machine, and chemicals used with the machine will determine the job it will do.  For example, a black pad on a 175 rpm swing machine is used for stripping.  A white pad on a 1500 rpm burnisher is for restoring gloss to floor finish.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Foam</div></td>
</tr>
<tr>
  <td class="text">Suds or bubbles that are produced from a liquid if it is agitated.  High foaming products are sometimes preferred in shower room and degreasing applications because the foam helps to increase the contact time, or dwell time of the cleaner on the surface.  Low foaming products are preferred in carpet cleaners, autoscrubber applications, and floor finish.  Foam can be a problem in dilution control units when air gets into the system sometimes causing products to foam more than normal.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Foam Gun</div></td>
</tr>
<tr>
  <td class="text">A dilution device that is used to increase the amount of foam from a product.  Foam guns are used most often in shower room cleaning or degreasing.  Sometimes these units are also referred to as hose end sprayers.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Fungicide</div></td>
</tr>
<tr>
  <td class="text">A substance that kills fungi.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Fungistat</div></td>
</tr>
<tr>
  <td class="text">A substance that prevents the growth of fungi but does not kill the fungi.</td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Fungus</div></td>
</tr>
<tr>
  <td class="text">A type of parasite that feeds on organic material and does not contain chlorophyll so it does not need sunlight to live.  Fungi thrive in moist, dark places.  Different fungi include mold, mildew, and trichophyton mentagrphytes (athlete's foot) to name a few.<br><br></td>
</tr>


            </table>
</td></tr></table>
