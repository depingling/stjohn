<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="SPENDING_ESTIMATOR_FORM" type="com.cleanwise.view.forms.SpendingEstimatorForm"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Select a Group</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<center>




<script language="JavaScript1.2">
<!--


//-->
</script>
<%
String action = (String) request.getAttribute("action");
ActionErrors ae = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
boolean errorFl = (ae!=null && ae.size()>0)? true:false;
int selectedPage = theForm.getSelectedPage();
String style = "tbar";
String linkProfile = "estimator.do?action=Profile&selectedPage=1";
String linkProductFliter = "estimator.do?action=ProductFilter&selectedPage=6";
String linkPaperPlus = "estimator.do?action=PaperPlus&selectedPage=2";
String linkFloorCare = "estimator.do?action=FloorCare&selectedPage=3";
String linkRestroomCare = "estimator.do?action=RestroomCare&selectedPage=4";
String linkOther = "estimator.do?action=Other&selectedPage=7";
String linkAllocatedCategory = "estimator.do?action=AllocatedCategory&selectedPage=5";

String linkDefaultProfile = "estimator.do?action=DefaultProfile&selectedPage=101";
String linkProcedures = "estimator.do?action=DefaultProcedures&selectedPage=102";
String linkCleaningCare = "estimator.do?action=DefaultCleaningCare&selectedPage=103";
String linkProducts = "estimator.do?action=DefaultProducts&selectedPage=104";
String linkDefaultAllocatedCategory = "estimator.do?action=DefaultAllocatedCategory&selectedPage=105";
String linkModel = "estimator.do?action=init&selectedPage=0";
String linkReport = "estimator.do?action=reports&selectedPage=9";
%>
<jsp:include flush='true' page="ui/reportingToolbar.jsp"/>
<%
 EstimatorFacilityJoinView facilityJoin = theForm.getFacilityJoin();
 EstimatorFacilityData facility = null;
 int facilityId = 0;
 int catalogId = 0;
 boolean templateFl = false;
 String facilityName = "Model";
 if( facilityJoin!=null) {
   facility = facilityJoin.getEstimatorFacility();
   if(facility!=null) {
     facilityId = facility.getEstimatorFacilityId();
     catalogId = facility.getCatalogId();
     facilityName = facility.getName();
     if("T".equals(facility.getTemplateFl())) templateFl = true;
   }
 }
 if(facilityId>0) {
%>
    <table  cellpadding="0" cellspacing="0" width="769" border='1'  bordercolor='#00000'>
    <tr bgcolor="#000000"> 
    <td class="tbartext" align='center'>&nbsp;&nbsp;&nbsp;</td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==0)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkModel%>">Models</a> 
    </td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==1)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkProfile%>">Profile</a>  
    </td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==6 || selectedPage==2 || selectedPage==3 || 
                selectedPage==4 || selectedPage==5 || selectedPage==7)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkPaperPlus%>">Configuration</a>  
    </td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==9)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkReport%>">Reports</a> 
    </td>
    </tr>
    </table>
    <!-- Estimator toolbar -->
    <%if(selectedPage==6 || selectedPage==2 || selectedPage==3 || 
            selectedPage==4 || selectedPage==5 || selectedPage==7) { %>
    <table  cellpadding="0" cellspacing="0" width="769" border='1'  bordercolor='#006699'>
    <tr bgcolor="#006699"> 
    <td class="tbartext" align='center'>&nbsp;&nbsp;&nbsp;</td>
   
    <!--td class="tbartext" align='center'>
    <% style = (selectedPage==1)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkProfile%>">Profile</a>  
    </td -->
    <td class="tbartext" align='center'>
    <% style = (selectedPage==2)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkPaperPlus%>">Paper, Soap, Liners</a> 
    </td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==3)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkFloorCare%>">Floor Care</a> 
    </td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==4)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkRestroomCare%>">Restroom Care</a> 
    </td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==7)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkOther%>">Other</a> 
    </td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==5)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkAllocatedCategory%>">Allocated Categories</a> 
    </td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==6)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkProductFliter%>">Products</a> 
    </td>
    </tr>
    </table>
    <% } %>
    <!--                   -->
<% } else { %>
    <!-- Default settings  toolbar -->
    <table  cellpadding="0" cellspacing="0" width="769" border='1'  bordercolor='#00000'>
    <tr bgcolor="#000000"> 
    <td class="tbartext" align='center'>&nbsp;&nbsp;&nbsp;</td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==0)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkModel%>">Models</a> 
    </td>
    <td class="tbartext" align='center'>
    <% style = (selectedPage==9)? "tbarOn":"tbar"; %>
    <a class=<%= style %> href="<%=linkReport%>">Reports</a> 
    </td>
    </tr>
    </table>
<% } %>

<div class="rptmid">
<font color=red>
<html:errors/>
</font>
<% if(selectedPage==9) {%>
<jsp:include flush='true' page="estimatorRep.jsp"/>
<%} else if(
  theForm.getFacilityJoin()!=null
) { %>
  <% if(selectedPage==1) {%>
<jsp:include flush='true' page="estimatorProfile.jsp"/>
  <% } %>
  <% if(selectedPage==2) {%>
<jsp:include flush='true' page="estimatorPaper.jsp"/>
  <% } %>
  <% if(selectedPage==3) {%>
<jsp:include flush='true' page="estimatorFloor.jsp"/>
  <% } %>
  <% if(selectedPage==4) {%>
<jsp:include flush='true' page="estimatorRestroom.jsp"/>
  <% } %>
  <% if(selectedPage==5) {%>
<jsp:include flush='true' page="estimatorAllocated.jsp"/>
  <% } %>
  <% if(selectedPage==6) {%>
<jsp:include flush='true' page="estimatorProduct.jsp"/>
  <% } %>
  <% if(selectedPage==7) {%>
<jsp:include flush='true' page="estimatorOther.jsp"/>
  <% } %>
  <%  if(selectedPage==8) {%>
<jsp:include flush='true' page="estimatorProfileInfo.jsp"/>
  <% } %>
<% } else { %>
  <% if(selectedPage==7) {%>
<jsp:include flush='true' page="estimatorTemplate.jsp"/>
  <% } else if(selectedPage==8) {%>
<jsp:include flush='true' page="estimatorProfileInfo.jsp"/>
  <% } else { %>
<jsp:include flush='true' page="estimatorBody.jsp"/>
<% }} %>
</div>
<jsp:include flush='true' page="/console/ui/consoleFooter.jsp"/>

</body>

</html:html>




