
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
  <td class="trainingsubhead"><div class="navigatemargin">Pack-type Vacuum</div></td>
</tr>
<tr>
  <td class="text">A compact machine that is worn on the back like a back pack which vacuums up dirt.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Par</div></td>
</tr>
<tr>
  <td class="text">Minimum inventory level.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Paste Wax</div></td>
</tr>
<tr>
  <td class="text">Thick wax that is applied with a cloth and then buffed.  Paste wax has a satin finish.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Pathogen</div></td>
</tr>
<tr>
  <td class="text">Disease producing organism<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Pathogenic</div></td>
</tr>
<tr>
  <td class="text">Disease-producing<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Permanent Seal</div></td>
</tr>
<tr>
  <td class="text">A coating applied to floors that does not need to be burnished or buffed to retain a gloss and does not need stripping.  Permanent seals do wear away especially in traffic patterns and are very difficult to remove.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Pesticide</div></td>
</tr>
<tr>
  <td class="text">Substance used to kill pests.  Disinfectants and sanitizers are considered pesticides.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">pH</div></td>
</tr>
<tr>
  <td class="text">The degree of alkalinity or acidity of a water soluble substance.  A O rating is the most acidic, a 7 neutral and a 14 the most alkaline.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Phenol</div></td>
</tr>
<tr>
  <td class="text">A substance used as a disinfectant.  Also known as carbolic acid.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Phenolic</div></td>
</tr>
<tr>
  <td class="text">A disinfectant that contains phenol.  Phenolics should not be used in neonatal intensive care units as phenolics are sometimes associated with jaundice in small infants.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Phosphate</div></td>
</tr>
<tr>
  <td class="text">A substance that is sometimes added to cleaners to help build the detergent and increase the water softening ability.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Phosphoric Acid</div></td>
</tr>
<tr>
  <td class="text">An acid added to bathroom cleaners.  Tends to have a stale odor.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Physical Properties</div></td>
</tr>
<tr>
  <td class="text">Characteristics of a substance such as smell, taste, color, density, etc.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Pile</div></td>
</tr>
<tr>
  <td class="text">Carpet fabric that makes up the surface of a carpet.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Pile Height</div></td>
</tr>
<tr>
  <td class="text">The length of the fibers that make up the surface of a carpet.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Pile Rake</div></td>
</tr>
<tr>
  <td class="text">Tool used to lift carpet pile after cleaner or for agitation during cleaning.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Pine Oil</div></td>
</tr>
<tr>
  <td class="text">An oil from pine trees that is used as a disinfectant on hard surfaces.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Pitting</div></td>
</tr>
<tr>
  <td class="text">The damaging of a surface by a chemical that results in small craters.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Plasticizer</div></td>
</tr>
<tr>
  <td class="text">Organic substance added to polymer to increase flexibility.  Found in floor finishes and resilient flooring.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Plasticizer Migration</div></td>
</tr>
<tr>
  <td class="text">Movement of the plasticizer from its intended location.  Plasticizer migration can happen with vinyl tile and floor finish.  The surface of the finish will be tacky.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Polyester</div></td>
</tr>
<tr>
  <td class="text">A synthetic polymer used as a carpet fiber, especially in shag carpets, and in brushes.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Polymer</div></td>
</tr>
<tr>
  <td class="text">A natural or synthetic resin that has been formed by the chaining together of molecules.  Polymers include acrylic, styrene, polyethylene, vinyl, and urethane.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Polypropylene</div></td>
</tr>
<tr>
  <td class="text">Olefin carpet fibers and backing.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Pop</div></td>
</tr>
<tr>
  <td class="text">The action of a floor finish becoming glossy.  Burnishing will help a floor finish pop.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Porcelain Enamel</div></td>
</tr>
<tr>
  <td class="text">Coating used on sinks and bathtubs.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Porous</div></td>
</tr>
<tr>
  <td class="text">A measurement of how many tiny openings a surface has.   Used to describe a surface of a floor and helps determine if a sealer is needed or not.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Potable Water</div></td>
</tr>
<tr>
  <td class="text">Water suitable for drinking.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Powder</div></td>
</tr>
<tr>
  <td class="text">Dry substance that appears as very fine, dustlike particles.  Some cleaners come in the form of a powder.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Powdering</div></td>
</tr>
<tr>
  <td class="text">A condition of a floor finish breaking down into dustlike particles.  Can be caused by poor adhesion or abrasion.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">PPB</div></td>
</tr>
<tr>
  <td class="text">Parts per billion.  Usually used to help measure the amount of enzymes in a product.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">PPM</div></td>
</tr>
<tr>
  <td class="text">Parts per million.  Used to help measure the hardness of water.  Also used to measure the amount of quaternary ammonium chloride in a disinfectant.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Precautionary Statement</div></td>
</tr>
<tr>
  <td class="text">Statement that warns people of hazards associated with a product.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Preservative</div></td>
</tr>
<tr>
  <td class="text">A substance that inhibits the growth of bacteria.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Prespray</div></td>
</tr>
<tr>
  <td class="text">Cleaning product used on carpets to help loosen soil before extraction or shampooing.  Also referred to as a verb as in the act of prespraying a carpet.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Propellant</div></td>
</tr>
<tr>
  <td class="text">Substance used to expel contents from an aerosol under pressure.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Protein</div></td>
</tr>
<tr>
  <td class="text">Nitrogen-containing organic matter in animal substances.  Wool is a protein fiber.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">PSI</div></td>
</tr>
<tr>
  <td class="text">Pounds per square inch.  Used to measure water pressure.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Pumice</div></td>
</tr>
<tr>
  <td class="text">Small porous rock used as an abrasive, often found in industrial strength hand soaps.</td>
</tr>

            </table>
</td></tr></table>
