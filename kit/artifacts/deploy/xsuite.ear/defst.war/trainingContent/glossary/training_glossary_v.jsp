
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
  <td class="trainingsubhead"><div class="navigatemargin">Varnish</div></td>
</tr>
<tr>
  <td class="text">A protective coating made of a solvent and a vegetable oil or resin.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Ventilation</div></td>
</tr>
<tr>
  <td class="text">The circulation of fresh air.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Vinyl Asbestos Tile</div></td>
</tr>
<tr>
  <td class="text">Type of flooring that is very similar to vinyl composite tile with the exception of the use of asbestos fibers as the filler.  Vinyl asbestos tiles are usually 12"x12" in size.  Extreme caution must be taken when maintaining a vinyl asbestos tile floor.   Always refer to the OSHA and EPA guidelines for maintaining asbestos containing floors before performing any type of maintenance.  Dry stripping, aggressive pads, and aggressive floor machines can all potentially damage the floor and release hazardous asbestos fibers into the air.  Always use both a seal and a floor finish on vinyl asbestos tile.  Also referred to as VAT, however VAT is also used to refer to vinyl asphalt tile.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Virus</div></td>
</tr>
<tr>
  <td class="text">Disease causing organisms or complex proteins that require living cells for growth.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Virucide</div></td>
</tr>
<tr>
  <td class="text">A substance that kills viruses.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Viscosity</div></td>
</tr>
<tr>
  <td class="text">A measurement of the thickness of a liquid.  The viscosity of a cleaning product in conjunction with the gpm flow of the water and the dilution tip will determine the dilution of that product through a dilution control unit.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">VOC</div></td>
</tr>
<tr>
  <td class="text">Volatile organic compounds are organic compounds that readily evaporate into the environment.  They include organic compounds containing chlorine, sulfur, or nitrogen.  There is a push to limit the VOC's in cleaning products because VOC's are linked to the greenhouse effect and air pollution.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Volatile</div></td>
</tr>
<tr>
  <td class="text">Part of a substance that will evaporate under normal temperatures.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">VRE</div></td>
</tr>
<tr>
  <td class="text"><i>Vancomycin Resistant Enterococcus</i> is bacteria that is resistant to the antibiotic Vancomycin.  Many hospitals require that disinfectants have a claim against both VRE and MRSA.</td>
</tr>

            </table>
</td></tr></table>
