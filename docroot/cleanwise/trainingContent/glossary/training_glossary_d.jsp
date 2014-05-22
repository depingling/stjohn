
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
  <td class="trainingsubhead"><div class="navigatemargin">Damp Mopping</div></td>
</tr>
<tr>
  <td class="text">Cleaning the floor with a mop that has been dipped in a cleaning solution and then tightly wrung out so that very little water is left on the floor after mopping.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Deep Cleaning</div></td>
</tr>
<tr>
  <td class="text">Cleaning a carpet in such way that not just the surfaces of the carpet fibers are cleaned but the entire fiber and the top surface of the backing is cleaned.  Extraction cleaning is a deep cleaning method.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Deep Scrubbing</div></td>
</tr>
<tr>
  <td class="text">Cleaning a floor with a general purpose cleaner, a swing machine, and a green or blue pad to remove a layer or two of finish and any marks, scratches or soil that is on the surface of the finish.  The deep scrubbing step needs to be followed by applying two coats of new floor finish.  This is a maintenance step that will help prolong the stripping cycles and will help restore gloss to the floor.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Defoamer</div></td>
</tr>
<tr>
  <td class="text">Substance added to a cleaning solution or recovery tank that reduces or eliminates foam.  Defoamers are often added to the recovery tank of a carpet extractor or autoscrubber.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Degreaser</div></td>
</tr>
<tr>
  <td class="text">A product specifically formulated to remove grease and oily soils.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Deodorant</div></td>
</tr>
<tr>
  <td class="text">A product specifically formulated to mask or eliminate offensive odors.  Deodorants are most effective when applied to the source of the odor, on a carpet for example, instead of being sprayed into the air.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Detergency</div></td>
</tr>
<tr>
  <td class="text">Cleaning efficiency<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Detergent</div></td>
</tr>
<tr>
  <td class="text">Cleaning agent.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Dilute</div></td>
</tr>
<tr>
  <td class="text">To mix a chemical with water.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Dilution Ratio</div></td>
</tr>
<tr>
  <td class="text">The amount of chemical and water that should be mixed together to get to a specific strength of a product.  Manufactures state recommended dilution ratios on their labels.  Going too strong or too weak on a dilution ratio will effect the performance of the product and may cause either unwanted residue on the surface being cleaned, a damaged surface, or an uncleaned surface.  Examples of dilution ratios are 1:32 or 4oz per gallon of water.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Disinfectant</div></td>
</tr>
<tr>
  <td class="text">A product that when used correctly, kills disease causing bacteria and/or viruses on hard surfaces.  Disinfectants do not kill spores such as mold.   The most common types of disinfectants are quaternary ammonium compounds and phenolics.  Disinfectants are regulated through the Environmental Protection Agency (EPA) and the disinfectant must list the EPA registration number along with all the organisms that they kill on the label.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Disinfectant Kill Claim</div></td>
</tr>
<tr>
  <td class="text">Registered claim with the Environmental Protection Agency (EPA) under the Federal Insecticide, Fungicide, and Rodenticide Act of 1947 of what bacteria and/or viruses a disinfectant kills.  These claims must be listed on the concentrate product container label.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">DOT</div></td>
</tr>
<tr>
  <td class="text">Department of Transportation is government agency that regulates how a product can be shipped, especially hazardous products, in what type of packaging it can be shipped, and by what method it can be shipped.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Dwell Time</div></td>
</tr>
<tr>
  <td class="text">The amount of time that a cleaner, disinfectant, or stripping solution must sit on or have contact with a surface.  Dwell times should always be followed and can have a direct impact on the efficiency of the product.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Dyes</div></td>
</tr>
<tr>
  <td class="text">A substance added to a chemicals or fabric to alter the color.  Not all dyes are stable meaning that some can fade or change color over time.</td>
</tr>

            </table>
</td></tr></table>
