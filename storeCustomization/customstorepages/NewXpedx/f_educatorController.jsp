<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%
/* determine the correct area of Training  */

String section = request.getParameter("section");
String pageAddr = "";
if(section == null) {
  section = "productSelector";
}
String content="";
String headerPage="";

// Default
if(section.equals("productSelector")) {
   pageAddr = "productSelector.jsp";
	content="ProductSelector";


// Accessories
 } else if(section.equals("floorMats")) {
   pageAddr = "Accessories/floorMats.jsp";
   content="Floor Mats";
   headerPage="Accessories";
 } else if(section.equals("floorPads")) {
   pageAddr = "Accessories/floorPads.jsp";
   content="Floor Pads";
   headerPage="Accessories";
 } else if(section.equals("wasteReceptacles")) {
   pageAddr = "Accessories/wasteReceptacles.jsp";
   content="Waste Receptacles";
   headerPage="Accessories";

// Chemicals -->
 } else if(section.equals("carpetCleaners")) {
   pageAddr = "Chemicals/carpetCleaners.jsp";
   content="Carpet Cleaners";
   headerPage="Chemicals";
 } else if(section.equals("deodorantsAndDispensers")) {
   pageAddr = "Chemicals/deodorantsAndDispensers.jsp";
   content="Deodorants and Dispensers";
   headerPage="Chemicals";
 } else if(section.equals("drainCleaners")) {
   pageAddr = "Chemicals/drainCleaners.jsp";
   content="Drain Cleaners";
   headerPage="Chemicals";
 } else if(section.equals("floorCare")) {
   pageAddr = "Chemicals/floorCare.jsp";
	content="Floor Care";
   headerPage="Chemicals";
   // subset under Floor Care -->
 } else if(section.equals("cleaners")) {
   pageAddr = "Chemicals/cleaners.jsp";
   content="Cleaners";
   headerPage="Chemicals";
 } else if(section.equals("finishes")) {
   pageAddr = "Chemicals/finishes.jsp";
     content="Finishes";
   headerPage="Chemicals";
   // subset under Finishers -->
	  } else if(section.equals("appearanceStandards")) {
        pageAddr = "Chemicals/appearanceStandards.jsp";
		content="Appearance Standards";
		headerPage="Chemicals";
      } else if(section.equals("machines")) {
        pageAddr = "Chemicals/machines.jsp";
		content="Machines";
		headerPage="Chemicals";
      } else if(section.equals("maintenanceFreq")) {
        pageAddr = "Chemicals/maintenanceFreq.jsp";
		content="Maintenance Frequency";
		headerPage="Chemicals";
      } else if(section.equals("typeFlooring")) {
        pageAddr = "Chemicals/typeFlooring.jsp";
		content="Type Flooring";
		headerPage="Chemicals";
      } else if(section.equals("choosing")) {
        pageAddr = "Chemicals/choosing.jsp";
		content="Choosing";
		headerPage="Chemicals";
      } else if(section.equals("typeFinish")) {
        pageAddr = "Chemicals/typeFinish.jsp";
		content="Type Finish";
		headerPage="Chemicals";
    } else if(section.equals("maintainers")) {
     pageAddr = "Chemicals/maintainers.jsp";
	 content="Maintainers";
		headerPage="Chemicals";
    } else if(section.equals("strippers")) {
     pageAddr = "Chemicals/strippers.jsp";
   content="Strippers";
		headerPage="Chemicals";
 } else if(section.equals("glassCleaners")) {
   pageAddr = "Chemicals/glassCleaners.jsp";
   content="Glass Cleaners";
		headerPage="Chemicals";
 } else if(section.equals("washroomCleaners")) {
   pageAddr = "Chemicals/washroomCleaners.jsp";
   content="Washroom Cleaners";
		headerPage="Chemicals";
   // subset under Washroom Cleaners -->
    } else if(section.equals("bowlCleaners")) {
      pageAddr = "Chemicals/bowlCleaners.jsp";
	  content="Bowl Cleaners";
		headerPage="Chemicals";
    } else if(section.equals("showerRoomCleaners")) {
      pageAddr = "Chemicals/showerRoomCleaners.jsp";
	  content="ShowerRoom Cleaners";
		headerPage="Chemicals";
    } else if(section.equals("specialtyItems")) {
      pageAddr = "Chemicals/specialtyItems.jsp";
	  content="Speciality Items";
		headerPage="Chemicals";
    } else if(section.equals("washroomSurfaceCleaners")) {
      pageAddr = "Chemicals/washroomSurfaceCleaners.jsp";
	   content="Washroom Surface Cleaners";
		headerPage="Chemicals";

// Disposables And Lodging -->
 } else if(section.equals("XXX")) {
   pageAddr = "DisposablesAndLodging/XXX.jsp";

		headerPage="Disposables and Lodging";
   //<a href="Chemicals/floorCare.jsp"></a><a href="educator.jsp">Training</a>
// Machines -->
 } else if(section.equals("burnishers")) {
   pageAddr = "Machines/burnishers.jsp";
	content="Burnishers";
	headerPage="Machines";
 } else if(section.equals("carpetExtractors")) {
   pageAddr = "Machines/carpetExtractors.jsp";
   content="Carpet Extractors";
	headerPage="Machines";
 } else if(section.equals("floorMachines")) {
   pageAddr = "Machines/floorMachines.jsp";
   content="Floor Machines";
	headerPage="Machines";
 } else if(section.equals("sweepers")) {
   pageAddr = "Machines/sweepers.jsp";
   content="Sweepers";
	headerPage="Machines";
 } else if(section.equals("vacuums")) {
   pageAddr = "Machines/vacuums.jsp";
   content="Vacuums";
	headerPage="Machines";
 } else if(section.equals("wetDryVacuums")) {
   pageAddr = "Machines/wetDryVacuums.jsp";
   content="Wet Dry Vacuums";
	headerPage="Machines";
 }
%>

<%--bread crumb --%>
<table class="breadcrumb">
<tr class="breadcrumb">
<td  class="breadcrumb">
	<a class="breadcrumb" href="../userportal/msbsites.do">
	<app:storeMessage key="global.label.Home"/></a>
</td>
<td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
<td class="breadcrumb">
    <% if(!headerPage.equals("")){%>
		<a class="breadcrumb" href="../userportal/newTemplator.do?display=f_educatorController.jsp"><app:storeMessage key="shop.heading.productInfo"/></a>
    <% }else{ %>
		<span class="breadcrumbCurrent"><app:storeMessage key="shop.heading.productInfo"/></span>
	<% } %>
</td>
<%--
<% if(!headerPage.equals("")){ %>
<td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
<td class="breadcrumb">
    <% if(!content.equals("")){%>
	    <a class="breadcrumb" href="#"><%=headerPage%></a>
	<% }else{ %>
	    <span class="breadcrumbCurrent"><%=headerPage%></span>
	<% } %>
</td>
<%}%>
--%>
<% if(!content.equals("")){
      if(content.equals("ProductSelector")){ %>
	  <td class="breadcrumb"> &nbsp; </td>
	  <%}else{%>
		<td  class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
		<td class="breadcrumb">
			<%--<a class="breadcrumb" href="#"><%=content%></a>--%>
			<div class="breadcrumbCurrent"><%=content%></div>
		</td>
		<%}%>
<%}%>
</tr>

</table>

<table border="0" cellpadding="0" cellspacing="0" align="center"
        width="<%=Constants.TABLEWIDTH800%>"
	   style="{border-left: 0; border-right:0;}">

   <jsp:include flush='true' page="<%=pageAddr%>"/>

   </table>

