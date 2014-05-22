
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
  <td class="trainingsubhead"><div class="navigatemargin">Nap</div></td>
</tr>
<tr>
  <td class="text">The surface fibers of a carpet.  Also known as pile.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Neutral</div></td>
</tr>
<tr>
  <td class="text">A water soluble substance with a pH of 7.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Neutral Cleaner</div></td>
</tr>
<tr>
  <td class="text">A cleaner with a pH of  around 7-9.  Neutral cleaners are typically used for daily damp mopping of floors to remove light to moderate soil loads.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Neutralizer</div></td>
</tr>
<tr>
  <td class="text">An acidic chemical that is used to neutralize any residue left behind from a floor stripper.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">NFPA</div></td>
</tr>
<tr>
  <td class="text">National Fire Protection Association develops codes and standards regarding fire safety.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">NFPA Rating</div></td>
</tr>
<tr>
  <td class="text">System for providing quick, clear, concise information for chemicals under conditions of fire, chemical spills or other emergency situations.  The NFPA numerical rating system is different than the HMIS numerical rating system.  The NFPA label is a diamond shaped label with blue, red, yellow and white colors.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">NIOSH</div></td>
</tr>
<tr>
  <td class="text">National Institute for Occupational Safety and Health make recommendations for exposure limits for various chemicals and assists OSHA in research.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Non-Resilient Flooring</div></td>
</tr>
<tr>
  <td class="text">Type of floor that can be permenatly damaged by shock or pressure.  Non-resilient flooring includes stone floors, concrete, terrazzo, and quarry tile.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Non-Selective</div></td>
</tr>
<tr>
  <td class="text">Disinfectants that kill a wide range of disease causing bacteria.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">No Rinse Stripper</div></td>
</tr>
<tr>
  <td class="text">Industry term used to describe a stripper that does not require a flood rinse after stripping.  A floor must still be damp mop rinsed after using a no rinse striper.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Nosocomial Infection</div></td>
</tr>
<tr>
  <td class="text">An infection that was acquired while in a hospital.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Nylon</div></td>
</tr>
<tr>
  <td class="text">A very durable synthetic fiber that is used in carpets, pads, and brushes.</td>
</tr>

            </table>
</td></tr></table>
