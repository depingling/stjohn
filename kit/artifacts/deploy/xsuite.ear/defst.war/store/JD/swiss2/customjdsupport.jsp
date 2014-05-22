<%@ page language="java" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<% 
    Locale userLocale = ClwI18nUtil.getUserLocale(request);
%>
<table width="100%">
<br>
<tr>
<td>
<% if(userLocale.getLanguage().equalsIgnoreCase("DE")){ %>

<ul>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/Verkaufsbedingungen_DE.pdf")%>'><app:storeMessage key="jd.swiss.support.saleTerms"/></a></li>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/DiverseyOnlineShop_DE.pdf")%>'><app:storeMessage key="jd.swiss.support.diverseyOnlineShop"/></a></li>
</ul>

 <ul>
 <b><app:storeMessage key="jd.swiss.support.benefitsOfService"/>:</b>
 </ul>

<ul>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/TrainingCenter_DE.pdf")%>'><app:storeMessage key="jd.swiss.support.trainingCenter"/></a></li>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/PlanungOrganisation_DE.pdf")%>'><app:storeMessage key="jd.swiss.support.cleaningPlansOrganizations"/></a></li>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/Hygieneanalyse_DE.pdf")%>'><app:storeMessage key="jd.swiss.support.hygieneControl"/></a></li>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/HotSpots_DE.pdf")%>'><app:storeMessage key="jd.swiss.support.hotspot"/></a></li>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/Schulungshilfsmittel_DE.pdf")%>'><app:storeMessage key="jd.swiss.support.methodsOfTraining"/></a></li>
</ul>    

  <ul>
	 <app:storeMessage key="jd.swiss.support.customerSupportText"/>
 				 	<a href="mailto:<app:storeMessage key='jd.swiss.support.customerSupportEmail'/>">
				 <app:storeMessage key='jd.swiss.support.customerSupportEmail'/></a>
 
  </ul>
    
<%} else if(userLocale.getLanguage().equalsIgnoreCase("FR")){ %>
<ul>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/Verkaufsbedingungen_FR.pdf")%>'><app:storeMessage key="jd.swiss.support.saleTerms"/></a></li>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/DiverseyOnlineShop_FR.pdf")%>'><app:storeMessage key="jd.swiss.support.diverseyOnlineShop"/></a></li>
</ul>

   <ul>
    <b><app:storeMessage key="jd.swiss.support.benefitsOfService"/>:</b>
   </ul>

<ul>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/TrainingCenter_FR.pdf")%>'><app:storeMessage key="jd.swiss.support.trainingCenter"/></a></li>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/PlanungOrganisation_FR.pdf")%>'><app:storeMessage key="jd.swiss.support.cleaningPlansOrganizations"/></a></li>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/Hygieneanalyse_FR.pdf")%>'><app:storeMessage key="jd.swiss.support.hygieneControl"/></a></li>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/HotSpots_FR.pdf")%>'><app:storeMessage key="jd.swiss.support.hotspot"/></a></li>
    <li><a target="_blank" href='<%=ClwCustomizer.getRootFilePath(request, "/content/Schulungshilfsmittel_FR.pdf")%>'><app:storeMessage key="jd.swiss.support.methodsOfTraining"/></a></li>
</ul>    

 <ul>
 <app:storeMessage key="jd.swiss.support.customerSupportText"/>
 				 	<a href="mailto:<app:storeMessage key='jd.swiss.support.customerSupportEmail'/>">
				 <app:storeMessage key='jd.swiss.support.customerSupportEmail'/></a>
 
 </ul>
  
<% } %>
</td>
</tr>
</table>