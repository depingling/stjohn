
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
  <td class="trainingsubhead"><div class="navigatemargin">Efflorescent</div></td>
</tr>
<tr>
  <td class="text">White powder that can develop on the surface of concrete or brick.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Emollient</div></td>
</tr>
<tr>
  <td class="text">A substance that softens or soothes, usually refers to skin care.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Emulsified</div></td>
</tr>
<tr>
  <td class="text">Usually refers to finish, dirt, or grease that has been broken up into small particles by a stripper or degreaser solution.  The finish, dirt, or grease is then suspended, or held up, in that solution preventing it from settling back down on the surface.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Emulsion</div></td>
</tr>
<tr>
  <td class="text">A solution of small particles held in a liquid.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Entry Mat</div></td>
</tr>
<tr>
  <td class="text">A mat that is used usually on the outside of a building in front of the entry doorways to trap soil to prevent the soil from entering the building.  The entry or outside mat should ideally be made of nylon or other water repellent type surface and be at least 10' long.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Enzyme</div></td>
</tr>
<tr>
  <td class="text">Protein molecules that break down soils, particularly odor causing soils, which are easily removed.  Enzymes are sometimes added to cleaners or deodorants.  Deodorants with enzymes actually eliminate the odor and do not just mask, or hide the odor.   Products with enzymes should not be used with disinfectants or sanitizers because they will inactivate the protein molecules.  Also, products with enzymes should not be used on wool carpets.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">EPA</div></td>
</tr>
<tr>
  <td class="text">The Environmental Protection Agency is a government agency who's responsibility it is to protect the environment.  Included in this is the regulation on chemicals and their effect on the environment especially regarding pollution.  The EPA also regulates disinfectants and their labels, kill claims, and testing methods.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Epoxy</div></td>
</tr>
<tr>
  <td class="text">A very strong, hard, chemical resistant synthetic resin often used in floor finishes, paints and sealers.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Etch</div></td>
</tr>
<tr>
  <td class="text">The pitting or scratching of a surface by a chemical that causes the surface to become rough.  Some floor surfaces are etched to improve adhesion of floor finishes.  Also, cleaning products can sometimes damage a surface by etching it, for examples some cleaners can etch glass.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Ethyl Alcohol</div></td>
</tr>
<tr>
  <td class="text">Common type of alcohol that is water soluble and is used as a solvent.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Eutrophication</div></td>
</tr>
<tr>
  <td class="text">Excessive plant growth caused by nitrates and phosphates that deplete the oxygen in water destroying the underwater life.  Some states do not allow cleaning products that contain phosphates to be sold in their states due to possibility that the product could enter the ground and then the water systems from ground water runoff.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Exposure Limit</div></td>
</tr>
<tr>
  <td class="text">The limit set to minimize an employee's exposure to a hazardous material.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Extractor</div></td>
</tr>
<tr>
  <td class="text">A machine used in carpet cleaning to deep clean the carpet.  Extractors pull water and suspended soil out of carpet and upholstery.  Extractors are also useful in spot removal on carpets to help rinse and pull out the residue left by the spotting chemical.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Eye Protection</div></td>
</tr>
<tr>
  <td class="text">Safety glasses or goggles that are worn to protect the eyes from potential hazards such as chemicals and flying objects.</td>
</tr>

            </table>
</td></tr></table>
