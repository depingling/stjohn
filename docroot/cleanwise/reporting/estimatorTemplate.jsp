<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="SPENDING_ESTIMATOR_FORM" type="com.cleanwise.view.forms.SpendingEstimatorForm"/>

<table width='735'>

<html:form name="SPENDING_ESTIMATOR_FORM" action="reporting/estimator"
    scope="session" type="com.cleanwise.view.forms.SpendingEstimatorForm">
 <tbody>
<%
 CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER); 
 String userType = user.getUser().getUserTypeCd(); 
 boolean adminFl = false;
 if ( RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType) ) {
   adminFl = true;
 }
 EstimatorFacilityDataVector estimatorFacilityDV = theForm.getTemplates();
 if(estimatorFacilityDV!=null && estimatorFacilityDV.size()>0 ) {
%>
<tr>
<td class='tableheader' colspan='3'>Select Template</td>
</tr>
<tr>
<td><b>Catalog</b></td>
<td><b>Name</b></td>
<td><b>Facility Type</b></td>
</tr>
<%
 for(Iterator iter=estimatorFacilityDV.iterator(); iter.hasNext();) {
   EstimatorFacilityData efD = (EstimatorFacilityData) iter.next();
   String linkHref = "estimator.do?action=initNewModel&templateId=" + efD.getEstimatorFacilityId()+"&selectedPage=1";

%>
<tr>
<td><%=theForm.getCatalogName(efD.getCatalogId())%></td>
<td><a href='<%=linkHref%>'><%=efD.getName()%></a></td>
<td><%=efD.getFacilityTypeCd()%></td>
</tr>
<%}}%>

</tbody></html:form>
</table>



