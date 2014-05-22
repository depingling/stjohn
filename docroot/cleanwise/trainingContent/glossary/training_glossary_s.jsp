
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
  <td class="trainingsubhead"><div class="navigatemargin"><i>Salmonella choleraesuis</i></div></td>
</tr>
<tr>
  <td class="text">Bacteria that causes food poisoning.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Sanitize</div></td>
</tr>
<tr>
  <td class="text">To reduce the number of bacteria on a surface.  Sanitizing is a three step process in which a surface is first cleaned, then rinsed with potable water, and then sanitized with a chemical.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Sanitizer</div></td>
</tr>
<tr>
  <td class="text">A product that reduces the amount of bacteria but does not kill all of the bacteria.  Sanitizers are used primarily in the food service industry.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">SARA Title III</div></td>
</tr>
<tr>
  <td class="text">Superfund Amendments and Reauthorization Act of 1986 states that certain chemicals must be tracked if their usage goes over a specified limit.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Scrub</div></td>
</tr>
<tr>
  <td class="text">To clean a surface with a pad or brush in conjunction with a cleaner.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Scuff</div></td>
</tr>
<tr>
  <td class="text">A black mark on floor finish caused by soles of shoes.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Seal</div></td>
</tr>
<tr>
  <td class="text">A coating used on floors to help prepare the floor for floor finish and increase leveling of the finish.  Sealers should be used on worn or porous floors.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Sealing</div></td>
</tr>
<tr>
  <td class="text">Applying a seal.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Sediment</div></td>
</tr>
<tr>
  <td class="text">Insoluble material that settles to the bottom of a liquid.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Shelf Life</div></td>
</tr>
<tr>
  <td class="text">The amount of time that a product is good for.  A product has a self life for the concentrate form and a different shelf life for the diluted form.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Slip Coefficient of Friction</div></td>
</tr>
<tr>
  <td class="text">A measurement of the angle at a which a slip could happen on floor finish.  Finishes, seals, restorers, and spray buffs are all tested for slip coefficient.  Popular test method is the James Machine test.  The UL considers a reading of 0.5 or above as a safe limit.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Slip Resistance</div></td>
</tr>
<tr>
  <td class="text">The ability of floor finish, mat, or floor to prevent a foot from slipping.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Slurry</div></td>
</tr>
<tr>
  <td class="text">The solution that results from the stripping of finish and seals in which the emulsified finish is suspended in the stripping solution.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Soap Scum</div></td>
</tr>
<tr>
  <td class="text">An insoluble film that is formed from soap.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Sodium Metasilicate</div></td>
</tr>
<tr>
  <td class="text">Salt used as a builder in cleaners and strippers to prevent dirt or finish from reattaching to a surface.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Soil Load</div></td>
</tr>
<tr>
  <td class="text">The amount of soil on a surface.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Soil Retardant</div></td>
</tr>
<tr>
  <td class="text">A chemical applied to upholstery and carpets to repel dirt.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Solids</div></td>
</tr>
<tr>
  <td class="text">Refers to the matter left behind in a floor finish after all of the volatile ingredients have evaporated.  Referred to as percentages such as 16% or 20% for example.  Does not designate the durability of a finish.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Soluble</div></td>
</tr>
<tr>
  <td class="text">The ability of a substance to be dissolved.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Solvent</div></td>
</tr>
<tr>
  <td class="text">A substance used to dissolve.  Butyl, alcohol, and d-limonene are some examples of solvents. Solvents are used in finishes to help control dry times.  They are used in cleaners and degreasers to dissolve oil and grease and in strippers to remove finish.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Spalling</div></td>
</tr>
<tr>
  <td class="text">The breaking apart of concrete or terrazzo into dust.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Spore</div></td>
</tr>
<tr>
  <td class="text">The inactive form of bacteria with a hard shell like cell structure.  Spores can only be killed with a bleach type product.  Mold in the spore form is black.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Spotter</div></td>
</tr>
<tr>
  <td class="text">A cleaning product that is used on carpets and is specifically designed to remove different types of stains.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Spray Buff</div></td>
</tr>
<tr>
  <td class="text">A cleaning chemical used to restore gloss to floor finish.  The product is sprayed on the finish and then the finish is buffed or burnished using a floor machine and the appropriate floor pad.  Also used as a verb such as the action of spray buffing a floor.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin"><i>Staphylococcus aureus</i></div></td>
</tr>
<tr>
  <td class="text">Bacteria causing skin infections.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Streaking</div></td>
</tr>
<tr>
  <td class="text">Mop lines in the finish that appear just below the surface of the finish.  Other terms that are used to describe streaking are "lawn mower lines" and "alligator".  Streaking is almost always caused by a procedural error.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Stripper</div></td>
</tr>
<tr>
  <td class="text">A chemical that is designed to remove floor finish and seals.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Stripping</div></td>
</tr>
<tr>
  <td class="text">Removing floor finish or seal with a stripper, a swing machine or autoscrubber, and the appropriate pad.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Substrate</div></td>
</tr>
<tr>
  <td class="text">Flooring.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Surfactant</div></td>
</tr>
<tr>
  <td class="text">Surface active ingredient that increases the cleaning or emulsifying ability of a cleaner.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Suspension</div></td>
</tr>
<tr>
  <td class="text">The holding up of a dirt or emulsified finish in a liquid to prevent it from settling back to the surface.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Synthetic</div></td>
</tr>
<tr>
  <td class="text">Man made.</td>
</tr>

            </table>
</td></tr></table>
