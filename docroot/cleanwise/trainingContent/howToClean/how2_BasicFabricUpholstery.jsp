
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
<bean:define id="section" value="training_howtoclean.jsp" type="java.lang.String" toScope="session"/>




      <table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
        <tr>
          <td class="smalltext" valign="up" width="20%">
          <td>
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td class="text">

<p><span class="trainingsubhead"><b>Basic Fabric and Upholstery Procedures</b></span></p>

<span class="trainingsubhead"><b>Procedures:</b></span>

	<OL type="1">
	<p>
        <li class="trainingbullet"><span class="text">Collect your equipment. Having the right tools for the job will increase your productivity
            and minimize down time.</span></li>

	<li class="trainingbullet"><span class="text">Use appropriate personal protective gear.</span></li>
	<li class="trainingbullet"><span class="text">Mix the upholstery cleaning solution
            according to the manufacturer. (Refer to label for correct dilution).<br>
		Note: Too strong a dilution is as bad as a weak dilution. Overly
		strong dilutions will leave behind residue that acts like a magnet, trapping
		dirt resulting in fast resoil times. Weak dilutions will not clean effectively.</span></li>

	<li class="trainingbullet"><span class="text">Test all products that
	will be used on the fabric for color fastness in an inconspicuous area and let
	dry. If the test yields unfavorable results, use another product.</span></li>

	<li class="trainingbullet"><span class="text">Place a waterproof
	drop cloth under the furniture to be cleaned.</span></li>

	<li class="trainingbullet"><span class="text">Vacuum the carpet thoroughly.</span></li>

	<li class="trainingbullet"><span class="text">Spot clean fabric.
	Apply appropriate amounts of spotter to a clean white towel. Start by blotting
	the spot from the outside edges and working your way in toward the center so as
	not to spread the spot. Rinse with extractor or a clean cloth.</span></li>

	<li class="trainingbullet"><span class="text">Clean the cushions
	first. Using an extractor will provide the best results. After cleaning the
	cushions place some white paper such as butcher type paper between the cushions
	and place them in an “A” configuration to facilitate drying. The paper will
	help to eliminate water marks, browning or color transfers of the fabric.</span></li>

	<li class="trainingbullet"><span class="text">Next, clean all other
	surfaces of the furniture such as the top, sides, arms, and the back, both
	inside and out. The recommended order of cleaning the furniture would be to
	start from the dirtiest area and work your way up from the bottom to the top.</span></li>

	<li class="trainingbullet"><span class="text">Let the furniture
	completely dry before replacing the cushions.</span></li>

	<li class="trainingbullet"><span class="text">Thoroughly clean all of the equipment.</span></li></OL>
</p>
<table border=0 class="trainingrulecolor"> 
           <tr><td class="text"><span class="tiptext">Note:
	These steps are the basic foundation for spot removal and upholstery cleaning.
	Due to a wide range of fabrics and variation within these fabrics, the spot
	removal steps may not completely remove all soils. We strongly recommend
	additional comprehensive training especially if you are going to be cleaning
	upholstery on a regular basis.</font></td></tr>
          </table>

</td></tr>
</table>

