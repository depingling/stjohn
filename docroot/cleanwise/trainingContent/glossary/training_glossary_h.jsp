
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
  <td class="trainingsubhead"><div class="navigatemargin">Hard Water</div></td>
</tr>
<tr>
  <td class="text">Water containing dissolved calcium and magnesium and sometimes iron.  The hardness of water is measured in parts per million (ppm).  Hard water can leave behind a white powdering film that can only be removed with acidic products.  Also, hard water deposits can cover other soils, such as soap scum, making them harder to clean.  Hard water can also change the effectiveness of a disinfectant therefore all disinfectants are usually tested in hard water when determining their ability to kill bacteria, fungi, or viruses.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Hazing</div></td>
</tr>
<tr>
  <td class="text">A problem that can happen when applying floor finish where the finish dries with a steamy appearance instead of a clear glossy appearance.  Occasionally hazing can be burnished off but usually a scrub and recoat or strip is required.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">HBV</div></td>
</tr>
<tr>
  <td class="text">Hepatitis B virus<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Heeling</div></td>
</tr>
<tr>
  <td class="text">Applying pressure to the side of a floor machine to remove scuffs or black marks.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">High Speed Floor Machine</div></td>
</tr>
<tr>
  <td class="text">A burnisher that runs at speeds over 1500 rpms.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">HIV</div></td>
</tr>
<tr>
  <td class="text">Human Immunodeficiency Virus is the virus that causes AIDs.  To disinfect a surface where HIV is a concern use a product with an HBV kill claim, a tuberculocidal kill claim, or bleach.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">HMIS</div></td>
</tr>
<tr>
  <td class="text">Hazardous Materials Identification System is a numerical (0-4) rating system to identify the hazard level of chemicals.  It includes ratings for health, flammability, and reactivity.  A rating of 0 is the least hazardous while a rating of 4 is the most hazardous.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Holiday</div></td>
</tr>
<tr>
  <td class="text">A gap or spot that was not cleaned or an area of a floor that finish was not overlapped on.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Humidity</div></td>
</tr>
<tr>
  <td class="text">Moisture in the air.  Humidity is referred to as a percentage, 60% humidity for example.  Humidity is sometimes confused with temperatures but the two are separate.  Arizona, for example, can be very hot but have very low humidity.  Applying finish under very humid conditions can cause problems with the finish drying, such as ghosting and hazing.<br><br></td>
</tr>
<tr>
  <td class="trainingrulecolor"><img src="param:cw_spacer_gif" WIDTH="1" HEIGHT="1"></td>
</tr>
<tr>
  <td class="trainingsubhead"><div class="navigatemargin">Hydrochloric Acid</div></td>
</tr>
<tr>
  <td class="text">A strong acid that is used in toilet bowl cleaners.  It is also know as muriatic acid.</td>
</tr>

            </table>
</td></tr></table>
