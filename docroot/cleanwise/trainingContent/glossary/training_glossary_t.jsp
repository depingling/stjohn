
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
  <td class="trainingsubhead"><div class="navigatemargin">Tacky</div></td>
</tr>
<tr>
  <td class="text">Sticky, not completely dried.  Can either refer to the feel of floor finishes when they are not completely dry or to the feel of a floor that has a lot of residue on it that has attracted dirt.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Telescope Handle</div></td>
</tr>
<tr>
  <td class="text">Extendable pole<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Terrazzo</div></td>
</tr>
<tr>
  <td class="text">Type of flooring that is a mixture of concrete and marble pieces that is poured into place between brass divider strips.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Toilet Bowl Cleaner</div></td>
</tr>
<tr>
  <td class="text">Usually an acid based cleaner specially designed to clean the inside of the toilet bowl.  Toilet bowl cleaners often contain hydrochloric acid.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Top Coating</div></td>
</tr>
<tr>
  <td class="text">Maintenance procedure for restoring floor finish in which new finish is applied to a floor that already has finish on it.  The floor is cleaned before the new finish is applied.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Top Scrubbing</div></td>
</tr>
<tr>
  <td class="text">Maintenance procedure for cleaning floors in which a general purpose cleaner is used, along with a swing machine and a green or blue pad, to remove ground in dirt on floor finish.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Toxic</div></td>
</tr>
<tr>
  <td class="text">Substance that can cause damage to skin, a severe illness, or death when ingested, inhaled or absorbed.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Toxin</div></td>
</tr>
<tr>
  <td class="text">Poisonous substance produced by bacterial cells<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Traffic Lane</div></td>
</tr>
<tr>
  <td class="text">Area of a floor that is used most often by people walking on it.  Traffic lanes will appear dirtier than the rest of the floor and may become worn much faster.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">TSP</div></td>
</tr>
<tr>
  <td class="text">Tri-sodium phosphate is used a water softener and as a detergent in cleaners.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Tuberculosis</div></td>
</tr>
<tr>
  <td class="text">Commonly referred to as TB, it is a highly infectious disease that is spread through the breathing in of the tubercle bacteria contained in the sputum ejected when an infected person coughs, sneezes, or talks.  TB is curable but if not treated correctly is deadly.  It effects the lungs and in prolonged cases, the bloodstream and organs.  Listed on disinfectant labels and efficacy sheets as <i>Mycobacterium tuberculosis</i>.  <i>M. bovis</i> is a rarer form of tuberculosis that effects the bones and joints and is usually transmitted through the ingestion of unpasturized milk in developing countries.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Tuberculocidal</div></td>
</tr>
<tr>
  <td class="text">A disinfectant that can kill the tubercle bacteria on hard surfaces.  The tubercle bacteria are very hardy and difficult to kill.  It is assumed by OSHA, the CDC, and the EPA that tuberculocidal products will kill most other bacteria and viruses and therefore can be used to disinfect hard surfaces contaminated with HIV and HBV.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Tufted</div></td>
</tr>
<tr>
  <td class="text">Carpet pile that has been created by yarn that is threaded through the backing.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Turn Over</div></td>
</tr>
<tr>
  <td class="text">The amount of employees that leave or are fired from a company.  High turn over can quickly escalate costs if quality training materials and skills are not available or utilized.</td>
</tr>

            </table>
</td></tr></table>
