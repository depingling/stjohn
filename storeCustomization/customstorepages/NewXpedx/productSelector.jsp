
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


        <tr>
		<td class="smalltext" valign="up" width="20%">
          
            <div class="twotopmargin">
		      <p>

              </p>
          
		      <table width="100%"><tr valign="top"><td class="text" width="33%">
				<span class="subheaders">Accessories</span><br>
				<div class="indent">
                  <a href="<%=currUri%>section=floorMats">Floor Mats</a><br>
                  <a href="<%=currUri%>section=floorPads">Floor Pads</a><br>
                  <a href="<%=currUri%>section=wasteReceptacles">Waste Receptacles</a>
				</div>
              </td>
			  <td class="text" width="33%">
                <span class="subheaders">Chemicals</span><br>
				<div class="indent">
                  <a href="<%=currUri%>section=carpetCleaners">Carpet Cleaners</a><br>
                  <a href="<%=currUri%>section=deodorantsAndDispensers">Deodorants and Dispensers</a><br>
                  <a href="<%=currUri%>section=drainCleaners">Drain Cleaners</a><br>
                  <a href="<%=currUri%>section=floorCare">Floor Care</a><br>
				  <a href="<%=currUri%>section=glassCleaners">Glass Cleaners</a><br>                  
                  <a href="<%=currUri%>section=washroomCleaners">Washroom Cleaners</a><br>
                  
				</div>
		      </td>
			  <td class="text" width="33%">
                <span class="subheaders">Machines</span><br>
				<div class="indent">
				  <a href="<%=currUri%>section=burnishers">Burnishers</a><br>
				  <a href="<%=currUri%>section=carpetExtractors">Carpet Extractors</a><br>
				  <a href="<%=currUri%>section=floorMachines">Floor Machines</a><br>
				  <a href="<%=currUri%>section=sweepers">Sweepers</a><br>
				  <a href="<%=currUri%>section=vacuums">Vacuums</a><br>
				  <a href="<%=currUri%>section=wetDryVacuums">Wet/Dry Vacuums</a><br>
				</div>
			  </td></table>
			  
			  </td></tr>

</div></td>
</tr>
			
          