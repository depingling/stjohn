
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
  <td class="trainingsubhead"><div class="navigatemargin">UL</div></td>
</tr>
<tr>
  <td class="text">Underwriters Laboratories is an organization that tests products for safety requirements.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Universal Precautions</div></td>
</tr>
<tr>
  <td class="text">A procedure in which all spills of blood and bodily fluids are treated as if it is infected with HIV, HBV or other bloodborne pathogens.  Results in all blood and bodily fluid spills being handled using proper PPE, disinfections, and disposal methods.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">USDA</div></td>
</tr>
<tr>
  <td class="text">United States Department of Agriculture regulates products used in food processing plants.  They assigned a rating system that determined if a product was allowed in the processing plant, if so in what area and under what conditions.   They no longer rate chemicals but instead they leave it to the manufacturer to assign a USDA equivalent rating to products and guarantee that the product meets the rating requirements previously established.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Urethane</div></td>
</tr>
<tr>
  <td class="text">A synthetic resin used in coatings for concrete and wood, and is sometimes used to strengthen floor finishes.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Use Dilution</div></td>
</tr>
<tr>
  <td class="text">The concentration of a cleaning product once it has been mixed with water and is ready to be used.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Use Dilution Test</div></td>
</tr>
<tr>
  <td class="text">A method for testing disinfectants and sanitizers to determine what bacteria, viruses, and fungi the product can kill after it has been mixed with water and is ready to be used.</div></td>
</tr>
<tr>
  <td class="text"></td>
</tr>

            </table>
</td></tr></table>
