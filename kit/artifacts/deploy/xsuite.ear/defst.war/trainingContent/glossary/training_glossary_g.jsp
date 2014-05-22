
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
  <td class="trainingsubhead"><div class="navigatemargin">Gel</div></td>
</tr>
<tr>
  <td class="text">A substance that has a thick, jelly-like consistency.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Generation</div></td>
</tr>
<tr>
  <td class="text">Refers to the types of nylon carpet fibers.  First generation carpet fibers tend to make carpets look dirtier than they actually are by magnifying the dirt.  Second generation carpet fibers are cross-sectioned for hiding soil better and preventing the magnification of the dirt.  Third generation carpet fibers are a modification of second generation with the addition of substances to prevent static electricity.  Fourth generation carpet fibers are a modification of third generation with the addition of substances to repel stains.  Fifth generation carpet fibers are a modification of fourth generation with the addition of substances to prevent staining from acidic dyes.  Sixth generation carpet fibers are a modification of fifth generation with the addition of substances to help prevent the carpet pile from being crushed or matted down.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Germ</div></td>
</tr>
<tr>
  <td class="text">Disease causing bacteria.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Germicide</div></td>
</tr>
<tr>
  <td class="text">Any substance that kills bacteria.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Gloss</div></td>
</tr>
<tr>
  <td class="text">The shiny, glass like appearance produced by floor finish or other smooth polished surface such as polished marble.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">GPM</div></td>
</tr>
<tr>
  <td class="text">Gallons per minute.  This term is used to refer to how fast a dilution control unit or faucet will allow water to flow through in one minute.  This is different from the pressure of the water.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Gram Positive</div></td>
</tr>
<tr>
  <td class="text">A class of bacteria that retain the crystal violet gram stain.  <i>Staphylococcus aureus</i> (a skin infection) is a gram-positive bacteria.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Gram Negative</div></td>
</tr>
<tr>
  <td class="text">A class of bacteria that does not retain the crystal violet gram stain.  <i>Salmonella cholerausuis</i> (food poisoning) is a gram-negative bacteria.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Grinding</div></td>
</tr>
<tr>
  <td class="text">The mechanical wearing away of stone floors to either make them even or as part of the process of refurbishing the stone floor to restore a polished surface.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Grout</div></td>
</tr>
<tr>
  <td class="text">A mortar that is used to fill cracks between floor tiles, in bathrooms to seal cracks around the ceramic tiles, and to fill crack between the tub or shower and the wall.  Grout can be difficult to keep clean and often requires the use of brushes and agitation to get clean.  If dirt or mold is embedded deep into the grout then the dirt and mold may not be able to be removed.  Grout can be latex based or cementacious.</td>
</tr>

            </table>
</td></tr></table>
