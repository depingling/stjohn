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

// Default 
if(section.equals("productSelector")) { 
   pageAddr = "/educatorContent/productSelector.jsp";

// Accessories 
 } else if(section.equals("floorMats")) { 
   pageAddr = "/educatorContent/Accessories/floorMats.jsp";
 } else if(section.equals("floorPads")) { 
   pageAddr = "/educatorContent/Accessories/floorPads.jsp";
 } else if(section.equals("wasteReceptacles")) { 
   pageAddr = "/educatorContent/Accessories/wasteReceptacles.jsp"; 
   
// Chemicals -->   
 } else if(section.equals("carpetCleaners")) { 
   pageAddr = "/educatorContent/Chemicals/carpetCleaners.jsp";
 } else if(section.equals("deodorantsAndDispensers")) { 
   pageAddr = "/educatorContent/Chemicals/deodorantsAndDispensers.jsp";
 } else if(section.equals("drainCleaners")) { 
   pageAddr = "/educatorContent/Chemicals/drainCleaners.jsp";
 } else if(section.equals("floorCare")) { 
   pageAddr = "/educatorContent/Chemicals/floorCare.jsp";   
   // subset under Floor Care -->
 } else if(section.equals("cleaners")) { 
   pageAddr = "/educatorContent/Chemicals/cleaners.jsp";
 } else if(section.equals("finishes")) { 
   pageAddr = "/educatorContent/Chemicals/finishes.jsp";
     
   // subset under Finishers -->
	  } else if(section.equals("appearanceStandards")) { 
        pageAddr = "/educatorContent/Chemicals/appearanceStandards.jsp";
      } else if(section.equals("machines")) { 
        pageAddr = "/educatorContent/Chemicals/machines.jsp";
      } else if(section.equals("maintenanceFreq")) { 
        pageAddr = "/educatorContent/Chemicals/maintenanceFreq.jsp";
      } else if(section.equals("typeFlooring")) { 
        pageAddr = "/educatorContent/Chemicals/typeFlooring.jsp";
      } else if(section.equals("choosing")) { 
        pageAddr = "/educatorContent/Chemicals/choosing.jsp";
      } else if(section.equals("typeFinish")) { 
        pageAddr = "/educatorContent/Chemicals/typeFinish.jsp";
	 
    } else if(section.equals("maintainers")) { 
     pageAddr = "/educatorContent/Chemicals/maintainers.jsp";
    } else if(section.equals("strippers")) { 
     pageAddr = "/educatorContent/Chemicals/strippers.jsp";
   
 } else if(section.equals("glassCleaners")) { 
   pageAddr = "/educatorContent/Chemicals/glassCleaners.jsp";
 } else if(section.equals("washroomCleaners")) { 
   pageAddr = "/educatorContent/Chemicals/washroomCleaners.jsp";
   
   // subset under Washroom Cleaners -->
    } else if(section.equals("bowlCleaners")) { 
      pageAddr = "/educatorContent/Chemicals/bowlCleaners.jsp";
    } else if(section.equals("showerRoomCleaners")) { 
      pageAddr = "/educatorContent/Chemicals/showerRoomCleaners.jsp";
    } else if(section.equals("specialtyItems")) { 
      pageAddr = "/educatorContent/Chemicals/specialtyItems.jsp";
    } else if(section.equals("washroomSurfaceCleaners")) { 
      pageAddr = "/educatorContent/Chemicals/washroomSurfaceCleaners.jsp";
	     
   
// Disposables And Lodging -->
 } else if(section.equals("XXX")) { 
   pageAddr = "/educatorContent/DisposablesAndLodging/XXX.jsp";                     
   //<a href="Chemicals/floorCare.jsp"></a><a href="educator.jsp">Training</a>
// Machines -->
 } else if(section.equals("burnishers")) { 
   pageAddr = "/educatorContent/Machines/burnishers.jsp";   
 } else if(section.equals("carpetExtractors")) { 
   pageAddr = "/educatorContent/Machines/carpetExtractors.jsp";
 } else if(section.equals("floorMachines")) { 
   pageAddr = "/educatorContent/Machines/floorMachines.jsp";
 } else if(section.equals("sweepers")) { 
   pageAddr = "/educatorContent/Machines/sweepers.jsp";
 } else if(section.equals("vacuums")) { 
   pageAddr = "/educatorContent/Machines/vacuums.jsp";
 } else if(section.equals("wetDryVacuums")) { 
   pageAddr = "/educatorContent/Machines/wetDryVacuums.jsp";
 } 
%>
   <jsp:include flush='true' page="<%=pageAddr%>"/>

