
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
  <td class="trainingsubhead"><div class="navigatemargin">Recoat</div></td>
</tr>
<tr>
  <td class="text">Apply a new coat of finish to a floor.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Residue</div></td>
</tr>
<tr>
  <td class="text">Small amount of a cleaner or stripper left on a floor or in a carpet.  Residue can cause a floor or carpet to resoil quickly and can cause problems with floor finish.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Resilient Tile</div></td>
</tr>
<tr>
  <td class="text">Flooring type that can withstand pressure without permanent damage.  Resilient Tile includes VCT, linoleum, rubber, asphalt, and asbestos flooring.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Resin</div></td>
</tr>
<tr>
  <td class="text">A type of polymer used in floor finishes and seals.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Resoil</div></td>
</tr>
<tr>
  <td class="text">Become dirty again.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Restorer</div></td>
</tr>
<tr>
  <td class="text">A chemical that is applied to high speed finishes to help bring the gloss back.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Rotary Cleaning</div></td>
</tr>
<tr>
  <td class="text">A method of carpet cleaning where a special brush is attached to a swing machine.  The swing machine holds a tank that dispenses a cleaning solution onto the carpet.  Rotary cleaning is very aggressive and can damage some carpet fibers and will also cause overwetting.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Rubber</div></td>
</tr>
<tr>
  <td class="text">Type of flooring that is made of rubber, natural and synthetic fillers.  It comes in varying degrees of flexibility.  Also, rubber flooring often comes in studded or raised patterns, sometimes very simple patterns and sometimes very detailed patterns.  Aggressive stripping pads and aggressive brushes can easily damage the raised pattern type rubber flooring.  While the newer rubber floors are very chemical resistant, older rubber floors can be easily damaged so some precautions should be taken when caring for rubber flooring.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Rust</div></td>
</tr>
<tr>
  <td class="text">Oxidation of iron or steel that is caused by exposure to humidity or water.</td>
</tr>
            </table>
</td></tr></table>
