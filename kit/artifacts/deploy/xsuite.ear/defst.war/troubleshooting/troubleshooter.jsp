
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>



<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% 
String query=request.getQueryString();
String currUri=SessionTool.getActualRequestedURI(request) +"?"; 
if(query!=null){
    currUri+=query+"&";
}
%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>




      <table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
        <tr>
          <td class="smalltext" valign="up" width="20%">
          <td>
            <div class="twotopmargin">
		      <p>Use the Troubleshooter to get answers to your most common cleaning questions by selecting a topic below.</p>
		      <table width="100%"><tr valign="top"><td class="text" width="33%">
				<span class="subheaders">Flooring Types</span><br>
				<div class="indent">
                  <a href="<%=currUri%>section=QA_flooring_AsphaltTile">Asphalt Tile</a><br>
                  <a href="<%=currUri%>section=QA_flooring_Linoleum">Linoleum</a><br>
                  <a href="<%=currUri%>section=QA_flooring_Marble">Marble</a><br>
                  <a href="<%=currUri%>section=QA_flooring_QuarryTile">Quarry Tile</a><br>
                  <a href="<%=currUri%>section=QA_flooring_Rubber">Rubber Floor</a><br>
                  <a href="<%=currUri%>section=QA_flooring_Slate">Slate</a><br>
                  <a href="<%=currUri%>section=QA_flooring_Terrazzo">Terrazzo</a><br>
                  <a href="<%=currUri%>section=QA_flooring_VAT">Vinyl Asbestos Tile</a><br>
                  <a href="<%=currUri%>section=QA_flooring_VCT">Vinyl Composite Tile</a><br>
				</div>
              </td>
			  <td class="text" width="33%">
                <span class="subheaders">Floor Finish</span><br>
				<div class="indent">
                  <a href="<%=currUri%>section=flrProblems_Cracking">Cracking</a><br>
                  <a href="<%=currUri%>section=flrProblems_DullGloss">Dull Finish</a><br>
                  <a href="<%=currUri%>section=flrProblems_FishEyes">Fish Eyes/ Bird's Eyes</a><br>
                  <a href="<%=currUri%>section=flrProblems_Flaking">Flaking</a><br>
                  <a href="<%=currUri%>section=flrProblems_FoamMarks">Foam Marks</a><br>
                  <a href="<%=currUri%>section=flrProblems_Ghosting">Ghosting</a><br>
                  <a href="<%=currUri%>section=flrProblems_Hazing">Hazing</a><br>
                  <a href="<%=currUri%>section=flrProblems_NoInitialGloss">No Initial Gloss</a><br>
                  <a href="<%=currUri%>section=flrProblems_Powdering">Powdering</a><br>
                  <a href="<%=currUri%>section=flrProblems_RidgingOrRipples">Ridging or Ripples</a><br>
                  <a href="<%=currUri%>section=flrProblems_SlipperyFloors">Slippery Floors</a><br>
                  <a href="<%=currUri%>section=flrProblems_StickyFloors">Sticky Floors</a><br>
                  <a href="<%=currUri%>section=flrProblems_Streaking">Streaking</a><br>
                  <a href="<%=currUri%>section=flrProblems_ScratchesAndSwirlMark">Swirl Marks</a><br>
                  <a href="<%=currUri%>section=flrProblems_WhiteFilm">White Film</a><br>
                  <a href="<%=currUri%>section=flrProblems_Yellowing">Yellowing</a><br>
				</div>
		      </td>
			  <td class="text" width="33%">
                <span class="subheaders">General</span><br>
				<div class="indent">
				  <a href="<%=currUri%>section=qa_general_Degreasers">Degreasers</a><br>
				  <a href="<%=currUri%>section=qa_general_Ingredients">Ingredients</a><br>
				  <a href="<%=currUri%>section=qa_general_USDA">USDA</a><br>
				</div>
			  </td></tr></table>
			</div>

          </td>
		  <td class="smalltext" valign="up" width="20%"></td>
        </tr>
      </table>
      

