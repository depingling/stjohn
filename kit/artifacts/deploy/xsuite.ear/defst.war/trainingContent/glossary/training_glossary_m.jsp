
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
  <td class="trainingsubhead"><div class="navigatemargin">Marble</div></td>
</tr>
<tr>
  <td class="text">Crystallized limestone used as flooring and countertops.   Will appear with either polished or honed surfaces and may have dark swirls throughout.  Easily damaged by acidic products.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Metal Interlock</div></td>
</tr>
<tr>
  <td class="text">A technique in which metal is used to link the polymer and resin in floor finish and sealers to create a stronger film and is easier to remove with the proper stripper.  Most finishes and sealers are metal interlocked today.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Microorganism</div></td>
</tr>
<tr>
  <td class="text">Microscopic animal or vegetable; includes bacteria, viruses, and fungi.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Mildew</div></td>
</tr>
<tr>
  <td class="text">A fungus that grows on a surface in dark, damp places.  Appears as a white furry coating and creates an unpleasant odor.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Mill Finish</div></td>
</tr>
<tr>
  <td class="text">The finish that is applied to VCT and linoleum while in the factory before it is sent out for distribution or installations.  Mill finishes usually need to be removed before a floor finish can be applied.  Also known as factory finish.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Mist</div></td>
</tr>
<tr>
  <td class="text">Fine liquid droplets suspended in air.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Mold</div></td>
</tr>
<tr>
  <td class="text">A fungus that grows on a surface in dark, damp places.  Appears as a downy, furry substance and creates an unpleasant odor.  Mold spores appear black and can only be removed by bleach and scrubbing.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">MSDS</div></td>
</tr>
<tr>
  <td class="text">Material Safety Data Sheet is a document that contains all of the information on a product regarding the chemical and physical properties and hazards, how to safely use, handle, and store the product, along with a listing of all the hazardous ingredients in the product.  An MSDS for every cleaning product must be available at all times to all employees.  Failure to have the MSDS available could result in fines from OSHA.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Muriatic Acid</div></td>
</tr>
<tr>
  <td class="text">Commercial name for hydrochloric acid which is used to remove mortar and in toilet bowl cleaners.</td>
</tr>

            </table>
</td></tr></table>
