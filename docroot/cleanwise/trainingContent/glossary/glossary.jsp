
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
  <td class="trainingsubhead"><div class="navigatemargin">Abrasion</div></td>
</tr>
<tr>
  <td class="text">Wearing away or cleaning by rubbing or scraping.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Abrasive</div></td>
</tr>
<tr>
  <td class="text">A substance used to scour, scrub, smooth or polish.  Abrasive particles are found in such products as cleansers, pumice stones, scouring pads and hand cleansers and on floor pads.  Do we want to say anything about selecting the right tool for the job...for example not hand pads and floor pads so that surface damage is avoided?<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Absorbent</div></td>
</tr>
<tr>
  <td class="text">A material that is capable of absorbing moisture.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Absorption</div></td>
</tr>
<tr>
  <td class="text">The passage of a material through the skin.  Or an absorbent material.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Accessories</div></td>
</tr>
<tr>
  <td class="text">Various tools that may be used in conjunction with cleaning machines and equipment; i.e. a dusting tool with a wet and dry vacuum.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Acid</div></td>
</tr>
<tr>
  <td class="text">A water soluble substance with a pH less than 7.  Acidic products are used to remove mineral deposits, rust, and to neutralize alkaline type deposits or residue.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Acrylic</div></td>
</tr>
<tr>
  <td class="text">A type of polymer used in some floor finishes.  Also, acrylic is a man made synthetic fiber occasionally used in carpet.  Acrylic carpet fibers tend to be easily worn by abrasion.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Acute Effect</div></td>
</tr>
<tr>
  <td class="text">An adverse effect that develops rapidly from a short term high level exposure to a material.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Adhesion</div></td>
</tr>
<tr>
  <td class="text">The ability to hold together two different substances.  Floor finishes and seals must have good adhesion characteristics to stick to a flooring surface to prevent the finish or seal from powdering or peeling off.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Aerosol</div></td>
</tr>
<tr>
  <td class="text">An extremely fine mist consisting of solid or liquid particles suspended in air.  Containers used to dispense the fine mist are also referred to as aerosols.  Disinfectants, glass cleaners, furniture polishes, stainless steel cleaners, and baseboard strippers can all be found packaged in aerosol containers.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">AHA</div></td>
</tr>
<tr>
  <td class="text">The American Hospital Association<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">AIDS</div></td>
</tr>
<tr>
  <td class="text">Acquired Immunodeficiency Syndrome<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Alcohol</div></td>
</tr>
<tr>
  <td class="text">Used in cleaners and degreasers to control viscosity, to act as a solvent for other ingredients and to provide resistance to low and freezing temperatures.  Cleaners and degreasers containing a larger percentage of alcohol will be flammable.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">ALF</div></td>
</tr>
<tr>
  <td class="text">Assisted Living Facility<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Algae</div></td>
</tr>
<tr>
  <td class="text">Microscopic single cell plants that grow in water and damp places.  Algae contains chlorophyll so they require sunlight to live.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Algaecide</div></td>
</tr>
<tr>
  <td class="text">Product which destroys algae.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Algistat</div></td>
</tr>
<tr>
  <td class="text">Product used to inhibit algae growth.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Alkali</div></td>
</tr>
<tr>
  <td class="text">A water soluble substance with a pH greater than 7.  Alkaline products are used to clean dirt, grease, strip finish, and to neutralize acids.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Alkalinity</div></td>
</tr>
<tr>
  <td class="text">Refers to the pH of a water soluble substance being greater than 7.  The alkalinity of a cleaner helps to determine its ability to clean acidic soils like fat, grease, oil and soap scum and helps strippers to remove floor finish.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Allergic Reaction</div></td>
</tr>
<tr>
  <td class="text">A condition in which a specific chemical causes an adverse condition such as a skin rash, hives, asthma, difficulty breathing, headaches, etc.  Because every person is different there is no predetermined way to know if someone will be allergic to a specific chemical or what the reaction may be.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">All Purpose Cleaner</div></td>
</tr>
<tr>
  <td class="text">A cleaner, usually alkaline, that is designed to remove a wide range of soils on various types of surfaces.  Also general purpose cleaner.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Antimicrobial</div></td>
</tr>
<tr>
  <td class="text">Substance which inhibits or destroys disease causing bacteria or germs.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Antistatic Agent</div></td>
</tr>
<tr>
  <td class="text">A substance that reduces static electricity by preventing friction.  Friction causes fabric (especially man made fabrics such as nylon and polyester) to produce static electricity discharge.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Antiseptic</div></td>
</tr>
<tr>
  <td class="text">Any substance that inhibits the action of microorganisms.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Antistat</div></td>
</tr>
<tr>
  <td class="text">Substance which reduces static electricity.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">AOAC</div></td>
</tr>
<tr>
  <td class="text">The Association of Official Agricultural Chemists<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">AOAC Test Method</div></td>
</tr>
<tr>
  <td class="text">The Association of Official Agricultural Chemists' method is a common test used to determine the ability of a disinfectant or sanitizer to kill specific viruses, bacteria, or fungi.  This test method is performed using the in-use dilution of the disinfectant or sanitizer.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Aqueous</div></td>
</tr>
<tr>
  <td class="text">Containing water.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">ASCR</div></td>
</tr>
<tr>
  <td class="text">The Association of Specialists in Cleaning and Restoration is a non-profit trade association that specializes in technical knowledge and support around cleaning carpets, upholstery, and draperies in addition to restoration of smoke, fire, and water damage.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Asepsis</div></td>
</tr>
<tr>
  <td class="text">Being free from disease causing microorganisms.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Autoclave</div></td>
</tr>
<tr>
  <td class="text">A container used for sterilizing under superheated steam under pressure.  Usually used in hospitals to sterilize equipment and utensils.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Automatic Scrubber</div></td>
</tr>
<tr>
  <td class="text">Labor saving powered floor cleaning machine that dispenses cleaning solution to the floor, scrubs it and vacuums it up into a recovery tank.  They are often referred to as "autoscrubbers" and are used mostly for daily cleaning and occasionally stripping of floors.  Automatic scrubbers can be either electric or battery powered and come in a wide range of widths to fit through small or wide openings.  They usually have one to two pad drives.</td>
</tr>

            </table>
</td></tr></table>
