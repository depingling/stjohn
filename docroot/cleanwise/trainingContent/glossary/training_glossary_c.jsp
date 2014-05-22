
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
  <td class="trainingsubhead"><div class="navigatemargin">Calcium Carbonate</div></td>
</tr>
<tr>
  <td class="text">A white powder found in limestone, marble, and chalk.  Calcium carbonate and magnesium are two of the main things that cause hard water.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Carnauba</div></td>
</tr>
<tr>
  <td class="text">A wax that is used in floor wax, mainly paste wax, that comes from the leaves of the carnauba palm tree in Brazil.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Carcinogenic</div></td>
</tr>
<tr>
  <td class="text">A substance that is cancer causing.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Carpet Freshener</div></td>
</tr>
<tr>
  <td class="text">A product that hides or disguises a bad odor in a carpet but does not remove the odor.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Carpet Groomer</div></td>
</tr>
<tr>
  <td class="text">A tool, like a rake, that is used to restore the carpet pile after cleaning or used to agitate the carpet during pre-spotting.  Also referred to as a carpet rake<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Carrier</div></td>
</tr>
<tr>
  <td class="text">A person or animal that carries and transmits germs but does not appear to be affected by those germs.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">CAS Number</div></td>
</tr>
<tr>
  <td class="text">Chemical Abstract Registry numbering system that classifies substances, matching them to their molecular structure and the different names that those substances can be called.  The CAS number often appears on MSDS.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Catalyst</div></td>
</tr>
<tr>
  <td class="text">A substance that accelerates a chemical action.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Cationic Surfactant</div></td>
</tr>
<tr>
  <td class="text">A positively charged ingredient that improves the cleaning ability of a product.  Quaternary ammonium compounds found in many disinfectants and sanitizers are cationic surfactants.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Caustic</div></td>
</tr>
<tr>
  <td class="text">Strong alkaline substance that can burn, eat away, or destroy tissue such as skin.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">CDC</div></td>
</tr>
<tr>
  <td class="text">Centers for Disease Control is an organization that develops information and guidelines to identify and stop the spread of diseases.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Cellulose</div></td>
</tr>
<tr>
  <td class="text">A natural substance that is used in the manufacturing of paper, textiles and carpet among others.  Found in cotton and jute carpet fibers and it is these fibers that may turn brown during a wet cleaning process.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Ceramic tile</div></td>
</tr>
<tr>
  <td class="text">A clay tile that can be either glazed or unglazed.  Often found in bathrooms.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Chelating Agent</div></td>
</tr>
<tr>
  <td class="text">An agent that combines with insoluble materials such as hard water salts (calcium and magnesium) making them soluble so that they don't settle out leaving a film that is difficult to remove.  Also helps to prevent the hard water salts from forming soap scum.  It is usually recommended that products with chelating agents should not be used on marble and limestone.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Chemical Resistance</div></td>
</tr>
<tr>
  <td class="text">The ability of a surface such as a floor or floor finish to withstand any potentially damaging effects of chemicals.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Chlorine</div></td>
</tr>
<tr>
  <td class="text">A bleaching agent with a strong, disagreeable odor that is sometimes used as a germicide.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Chlorine Bleach</div></td>
</tr>
<tr>
  <td class="text">A group of strong oxidizing agents.  Household bleach contains about 5% of sodium hypochlorite.  Chlorine bleach is used to whiten stains and as a disinfectant to kill mold and mildew among other things.  Chlorine bleach is not a cleaner so in heavily soiled situation, the surface should first be cleaned and then the bleach can be applied.  Chlorine Bleach will blush or dull floor finish.  Chlorine Bleach should never be mixed with any type of acidic product because of the hazardous gases that mixture would create.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Chronic Effect</div></td>
</tr>
<tr>
  <td class="text">An adverse reaction that may or may not be the result of long-term exposure to a product or a repetitive task.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Cidal or "Cide"</div></td>
</tr>
<tr>
  <td class="text">Suffix that refers to agents with the ability to kill microorganisms.  Often used when referring to disinfectants.  For example: germicidal or tuberculocidal.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Cleaning Head</div></td>
</tr>
<tr>
  <td class="text">A tool used in carpet extraction cleaning, which sprays solution and vacuums it up.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Cleanser</div></td>
</tr>
<tr>
  <td class="text">A cleaning product that contains abrasives, surfactants and bleach.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Cocci</div></td>
</tr>
<tr>
  <td class="text">Disease causing spherical shaped bacteria.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Communicable Disease</div></td>
</tr>
<tr>
  <td class="text">A disease that can be directly or indirectly transmitted from one person to another.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Concentrate</div></td>
</tr>
<tr>
  <td class="text">The undiluted form of a cleaning product.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Concrete</div></td>
</tr>
<tr>
  <td class="text">A mixture of Portland cement, sand, gravel, and water.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Contaminate</div></td>
</tr>
<tr>
  <td class="text">To taint or damage.  For example, stripper residue will contaminate floor finish.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Contamination</div></td>
</tr>
<tr>
  <td class="text">The process of contaminating.  Bacteria or microorganisms that may infect a person, animal, or substance.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Corrode</div></td>
</tr>
<tr>
  <td class="text">To eat away by chemical action or rust.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Corrosion Inhibitor</div></td>
</tr>
<tr>
  <td class="text">Substance added to cleaners that protect metal from being eaten away or rusting.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Corrosives</div></td>
</tr>
<tr>
  <td class="text">A substance that damages another substance it contacts, especially the skin and eyes.    (with contact.)<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Coverage</div></td>
</tr>
<tr>
  <td class="text">How much a product it will to take to clean or coat a surface.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Critical Device</div></td>
</tr>
<tr>
  <td class="text">Medical items that are inserted into a person, such as surgical instrument.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Cross Contamination</div></td>
</tr>
<tr>
  <td class="text">Transferring bacteria or microorganism from one person or object to another person or object.  In floor care, cross contamination can be the transferring of residue from one object to another.   For example, using a stripping mop to lay floor finish creates cross contamination since the stripper becomes mixed with the floor finish.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Cure</div></td>
</tr>
<tr>
  <td class="text">The proper hardening of concrete, mortar, finishes, seals, etc.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Cure Time</div></td>
</tr>
<tr>
  <td class="text">The amount of time it takes for the proper hardening of concrete, mortar, finish, seals, etc.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Cut Pile</div></td>
</tr>
<tr>
  <td class="text">A carpet fabric that has cut yarn instead of loops.</td>
</tr>


            </table>
</td></tr></table>
