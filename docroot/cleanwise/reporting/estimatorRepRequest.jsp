<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<% String storeDir=ClwCustomizer.getStoreDir(); 
   String onKeyPress="return submitenter(this,event,'Submit');"; 
   CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER); 
   String userType = user.getUser().getUserTypeCd();
   String siteIdS = "0"; 
   String accountIdS = "0";
   String userIdS = ""+user.getUser().getUserId();
   boolean adminFl = false, fReportUser = false;
   if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)
   ) {
   adminFl = true;
   }

%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="SPENDING_ESTIMATOR_FORM" type="com.cleanwise.view.forms.SpendingEstimatorForm"/>

<script language="JavaScript1.2">
<!--

function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

//-->
</script>

<div class="text">
<table cellpadding="2" cellspacing="0" border="0" width="750" class="mainbody">
<html:form name="SPENDING_ESTIMATOR_FORM" action="reporting/estimator"
    scope="session" type="com.cleanwise.view.forms.SpendingEstimatorForm">
 <tbody>
  <tr>
       <td colspan="3" align="left" class='rptmid_blue'>
       <%=theForm.getReport().getName()%>
       </td>
  </tr>
    <% String longDesc = theForm.getReport().getLongDesc();
       if(longDesc!=null) {
    %>
    <tr>
       <td colspan="3" align="left">
       <%=longDesc%>
       </td>
    </tr>
  <% } %>

 <!-- Parameter Control  -->
 <%String controls = ""; %>
 <% GenericReportControlViewVector grcVV = theForm.getGenericControls();
   if(grcVV!=null && grcVV.size()>0) {
   for(int ii=0; ii<grcVV.size(); ii++) {
   GenericReportControlView grc = (GenericReportControlView) grcVV.get(ii);
   String name = grc.getName();
   String label = grc.getLabel();
   if(label!=null && label.length()==0) label=null;
   boolean mandatoryFl=true;
   String mf = grc.getMandatoryFl();
   if(mf!=null) mf = mf.trim().toUpperCase();
   if("N".equals(mf) || "NO".equals(mf) ||"0".equals(mf) ||"F".equals(mf) ||"FALSE".equals(mf)) mandatoryFl = false;
   if(name.endsWith("_OPT")){
        mandatoryFl = false;
   }
   String controlEl = "genericControlValue["+ii+"]";
%>

<% if("BEG_DATE".equalsIgnoreCase(name)||"BEG_DATE_OPT".equalsIgnoreCase(name)){ %>
  
 <tr> <td>&nbsp</td>
      <%mandatoryFl=true;
      if(name.endsWith("_OPT")){
        mandatoryFl=false;
      }%>
       <td><b><%=(label==null)?"Begin Date:":label%></b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="SPENDING_ESTIMATOR_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
       </td>
  </tr>

    <% for(int jj=0; jj<grcVV.size(); jj++) {
    GenericReportControlView grc1 = (GenericReportControlView) grcVV.get(jj);
    String name1 = grc1.getName();
    if("END_DATE".equalsIgnoreCase(name1) || "END_DATE_OPT".equalsIgnoreCase(name1)) {
      String label1 = grc.getLabel();
      if(label1!=null && label1.length()==0) label1=null;
      boolean mandatoryFl1=true;
      if(name1.endsWith("OPT")){
        mandatoryFl1=false;
      }
      String mf1 = grc1.getMandatoryFl();
      if(mf!=null) mf1 = mf1.trim().toUpperCase();
      if("N".equals(mf1) || "NO".equals(mf1) ||"0".equals(mf1) ||"F".equals(mf1) ||"FALSE".equals(mf1)) mandatoryFl1 = false;
      String controlEl1 = "genericControlValue["+jj+"]"; %>
 <tr> <td>&nbsp</td>
       <td><b><%=(label==null)?"End Date:":label1%></b></td><td>
       <html:text onkeypress="<%=onKeyPress%>" name="SPENDING_ESTIMATOR_FORM" property="<%=controlEl1%>" /><%if(mandatoryFl1) { %><span class="reqind">*</span> <%}%></td></tr>
     <% break; }}%>
<% } else if("END_DATE".equalsIgnoreCase(name)) { %>
<% } else if("END_DATE_OPT".equalsIgnoreCase(name)) { %>
<% } else if("CUSTOMER".equalsIgnoreCase(name)) { 
   if(adminFl) {
%>
<!-- User to run report as -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"User:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/usermgrLocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="SPENDING_ESTIMATOR_FORM" property="<%=controlEl%>" />
       <html:button property="locateUser"
                    onclick="<%=onClick%>"
                    value="Locate User"/>
        </td>
  </tr>
<% } %>  
<% } else if("FACILITY_MULTI_OPT".equalsIgnoreCase(name)) { %>
<% 
 EstimatorFacilityDataVector estimatorFacilityDV = theForm.getFacilities();
 if(estimatorFacilityDV!=null && estimatorFacilityDV.size()>0 ) {
%>
  <tr> <td>&nbsp;</td>
       <td><b>Models:</b></td>
       <td>
<%
 for(Iterator iter=estimatorFacilityDV.iterator(); iter.hasNext();) {
   EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
   String estimatorIdS = ""+ efD.getEstimatorFacilityId();
%>
  <html:multibox name="SPENDING_ESTIMATOR_FORM"   property="runForModels"
  value="<%=estimatorIdS%>"/><%=efD.getName()%><br>
<% } %>
</td>
</tr>
<% } %>
<% } else if("FACILITY_GROUP_OPT".equalsIgnoreCase(name)) { %>
<% 
 EstimatorFacilityDataVector estimatorFacilityDV = theForm.getFacilities();
 if(estimatorFacilityDV!=null && estimatorFacilityDV.size()>0 ) {
 Object[] estimatorFacilityA = estimatorFacilityDV.toArray();
 String[] catalogA = new String[estimatorFacilityA.length];
 int prevCatalogId=-1;
 String catalogName=null;
 for(int aa=0; aa<estimatorFacilityA.length; aa++) {
   EstimatorFacilityData efD = (EstimatorFacilityData) estimatorFacilityA[aa];
   int catalogId = efD.getCatalogId();
   if(aa==0 || prevCatalogId!=catalogId) {
     prevCatalogId = catalogId;
     catalogName = theForm.getCatalogName(catalogId);
   }
   catalogA[aa] = catalogName;   
 }
 //Order by group and catalog nane
 if(estimatorFacilityA.length>1) {
   for(int aa=0; aa<estimatorFacilityA.length-1; aa++) {
     boolean exitFl = true;
     for(int jj=0; jj<estimatorFacilityA.length-aa-1; jj++) {
       EstimatorFacilityData efD1 = (EstimatorFacilityData) estimatorFacilityA[jj];
       EstimatorFacilityData efD2 = (EstimatorFacilityData) estimatorFacilityA[jj+1];
       String catalogName1 = catalogA[jj];
       String catalogName2 = catalogA[jj+1];
       int comp = catalogName1.compareTo(catalogName2);
       if(comp>0) {
         estimatorFacilityA[jj] = efD2;
         estimatorFacilityA[jj+1] = efD1;
         catalogA[jj] = catalogName2;
         catalogA[jj+1] = catalogName2;
         exitFl = false;
       } else if(comp==0) {
         String groupName1 = efD1.getFacilityGroup();
         if(groupName1==null) groupName1 = "";
         String groupName2 = efD2.getFacilityGroup();
         if(groupName2==null) groupName2 = "";
         int comp1 = groupName1.compareTo(groupName2);
         if(comp1>0) {
           estimatorFacilityA[jj] = efD2;
           estimatorFacilityA[jj+1] = efD1;
           exitFl = false;
         } else if(comp1==0) {
           String modelName1 = efD1.getName();
           String modelName2 = efD2.getName();
           int comp2 = modelName1.compareTo(modelName2);
           if(comp2>0){
             estimatorFacilityA[jj] = efD2;
             estimatorFacilityA[jj+1] = efD1;
             exitFl = false;
           }
         }
       }
     }
     if(exitFl) break;
   }
 }

%>
  <tr> <td>&nbsp;</td>
       <td><b>Model Groups:</b></td>
       <td>
<%
 String facilityIdList = null;
 String namePrev = null;
 int count = 0;
 for(int aa=0; aa<estimatorFacilityA.length; aa++) {
   EstimatorFacilityData efD = (EstimatorFacilityData) estimatorFacilityA[aa];
   String groupName = efD.getFacilityGroup();
   if(groupName==null || groupName.trim().length()==0) continue;
   name = catalogA[aa]+". "+groupName;
   if(namePrev==null || !namePrev.equals(name)) {
     if(namePrev!=null) {
       count++;
%>
<html:radio name="SPENDING_ESTIMATOR_FORM"   property="<%=controlEl%>"
  value="<%=facilityIdList%>"/><%=namePrev%><br>
<% } 
   namePrev = name;
   facilityIdList = ""+efD.getEstimatorFacilityId();
   } else {
   facilityIdList += ","+efD.getEstimatorFacilityId();
   } 
  }
  if(namePrev!=null) {
   count++;
%>
<html:radio name="SPENDING_ESTIMATOR_FORM"   property="<%=controlEl%>"
  value="<%=facilityIdList%>"/><%=namePrev%><br>
<% } if (count==0) {%>
<<<< No facility groups found >>>>>
<% } %>
</td>
</tr>
<% } %>
<% } else  { %>
<!-- Generic Opt -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?name:label%></b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="SPENDING_ESTIMATOR_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
        </td>
  </tr>
<% } %>
<% }} //End of generic report controls %>
  <tr>
       <td colspan="3" align="center">
        <html:submit  styleClass="store_fb" property="action" value="Run Report"/>
       </td>
  </tr>


</tbody></html:form>
</table>



</div>




